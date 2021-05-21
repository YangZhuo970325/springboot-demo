package com.yangzhuo.design.strategy.service.impl;

import com.yangzhuo.design.strategy.service.SilkBag;
import org.springframework.stereotype.Service;

@Service
public class NanXuSilkBag implements SilkBag {

    @Override
    public String solveProblem() {
        return "南徐锦囊打开：赵云打开第一个锦囊，即令士兵们去商店购买结婚用品，并大肆张扬：刘备要与孙权妹妹结亲了。并劝刘备去拜见乔国老";
    }

    @Override
    public String getChannel() {
        return "NanXuSilkBag";
    }
}
