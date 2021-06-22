package com.yangzhuo.dozer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.Map;

/**
 * 凭证头表
 *
 * @author zhuo.yang@hand-china.com 2021-06-02 18:07:56
 */
@Data
@ToString
public class JeHeaderCreateDTO {
    
    String ledgerCode;
    
    String companyCode;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date glDate;

    private Map<String, String> addFields;
    
}