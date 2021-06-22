package com.yangzhuo.dozer;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class Doc {
    
    private Header header;
    
    private List<Line> lineList = new ArrayList<>();
}
