package com.yangzhuo.dozer.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 凭证行表
 *
 * @author zhuo.yang@hand-china.com 2021-06-02 18:07:57
 */
@Data
@ToString
public class JeLineCreateRespDTO {
    
    BigDecimal enteredCr;

    BigDecimal enteredDr;
    
    String currencyCode;
    
    String account;

    private Map<String, String> addFields;
}