package com.yangzhuo.dozer.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@ToString
public class JeDocCreateRespDTO {
    
   
    private JeHeaderCreateRespDTO jeHeader;
    
    private List<JeLineCreateRespDTO> jeLineList;
    
    @NotBlank
    public String operation;

    /*@Override
    public String toString() {
        return "JeDocCreateRespDTO{" +
                "jeHeader=" + jeHeader.toString() +
                ", jeLines=" + jeLines.toString() +
                ", operation='" + operation + '\'' +
                '}';
    }*/
}
