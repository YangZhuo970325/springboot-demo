package com.yangzhuo.dozer.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@ToString
public class JeDocCreateDTO {
    
   
    private JeHeaderCreateDTO jeHeader;
    
    
    private List<JeLineCreateDTO> jeLineList;
    
    @NotBlank
    public String operation;
}
