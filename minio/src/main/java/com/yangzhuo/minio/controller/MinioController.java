package com.yangzhuo.minio.controller;

import com.yangzhuo.minio.config.MinioProperties;
import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.PutObjectOptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;

@Slf4j
@RestController
public class MinioController {

    @Autowired
    private MinioProperties properties;

    @Autowired
    private MinioClient minioClient;

    private String transFileName(HttpServletRequest request, String fileName) throws Exception {
        if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
            fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
        } else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
            fileName = URLEncoder.encode(fileName, "UTF-8");// IE浏览器
        } else if (request.getHeader("User-Agent").toUpperCase().indexOf("CHROME") > 0) {
            fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
        }
        return fileName;
    }

    @GetMapping("/upload")
    public void upload(@RequestParam(name = "file") MultipartFile file) {
        String filename = file.getOriginalFilename();
        try {
            InputStream in = file.getInputStream();
            String contentType = file.getContentType();
            long size = file.getSize();
            boolean bucketExists = minioClient.bucketExists(properties.getBucketName());
            if (!bucketExists) {
                minioClient.makeBucket(properties.getBucketName());
            }

            PutObjectOptions putObjectOptions = new PutObjectOptions(size, -1);
            putObjectOptions.setContentType(contentType);
            
            minioClient.putObject(properties.getBucketName(), filename, in, putObjectOptions);
        } catch (Exception e) {
            log.error("upload file {} error", filename, e);
        }
    }

    @GetMapping("/view")
    public void view(String fileName, HttpServletResponse response) {
        InputStream in = null;
        try {
            ObjectStat stat = minioClient.statObject(properties.getBucketName(), fileName);
            response.setContentType(stat.contentType());
            in = minioClient.getObject(properties.getBucketName(), fileName);
            IOUtils.copy(in, response.getOutputStream());
            response.getOutputStream().flush();
        } catch (Exception e) {
            log.error("download file {} error", fileName, e);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    @GetMapping("/download")
    public void download(String fileName, HttpServletRequest request,
                         HttpServletResponse response) {
        InputStream in = null;
        try {
            ObjectStat stat = minioClient.statObject(properties.getBucketName(), fileName);
            response.setContentType(stat.contentType());
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + transFileName(request, fileName));
            in = minioClient.getObject(properties.getBucketName(), fileName);
            IOUtils.copy(in, response.getOutputStream());
            response.getOutputStream().flush();
        } catch (Exception e) {
            log.error("download file {} error", fileName, e);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    @GetMapping("/delete")
    public void delete(String fileName) {
        try {
            minioClient.removeObject(properties.getBucketName(), fileName);
        } catch (Exception e) {
            log.error("delete file {} error", fileName, e);
        }
    }

}
