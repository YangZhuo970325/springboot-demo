package com.yangzhuo.sentinel.feign;

import com.yangzhuo.sentinel.feign.fallback.HiRemoteFallbackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "sentinel-hi", fallback = HiRemoteFallbackService.class)
public interface HiRemoteService {

    @GetMapping("/anno")
    String hello();
}
