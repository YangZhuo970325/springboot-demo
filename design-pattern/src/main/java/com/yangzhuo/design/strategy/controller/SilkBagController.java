package com.yangzhuo.design.strategy.controller;

import com.yangzhuo.design.strategy.component.SilkBagFactory;
import com.yangzhuo.design.strategy.service.SilkBag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SilkBagController {

    @Autowired
    private SilkBagFactory silkBagFactory;

    @GetMapping(value = "test/strategy")
    public Object testStrategy(@RequestParam(value = "channel", defaultValue = "NanXuSilkBag") String channel) {

        SilkBag silkBag = silkBagFactory.getStrategy(channel);

        return silkBag.solveProblem();
    }

}
