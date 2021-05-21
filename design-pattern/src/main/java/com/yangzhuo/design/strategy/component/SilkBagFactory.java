package com.yangzhuo.design.strategy.component;

import com.yangzhuo.design.strategy.service.SilkBag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SilkBagFactory {
    
    @Autowired
    private Set<SilkBag> silkBagList;
    
    Map<String, SilkBag> silkBagMap;

    @PostConstruct
    public void init() {
        silkBagMap = silkBagList.stream().collect(Collectors.toMap(SilkBag::getChannel, a -> a));
    }

    public SilkBag getStrategy(String channel) {
        return silkBagMap.get(channel);
    }
}
