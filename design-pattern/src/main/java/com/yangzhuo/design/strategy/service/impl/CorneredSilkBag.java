package com.yangzhuo.design.strategy.service.impl;

import com.yangzhuo.design.strategy.service.SilkBag;
import org.springframework.stereotype.Service;

@Service
public class CorneredSilkBag implements SilkBag {

    @Override
    public String solveProblem() {
        return "走投无路锦囊打开：刘备依计向夫人哭诉孙权、周瑜用美人计诱杀自己的阴谋，夫人大怒，命推出坐车，对东吴追赶的几个将军严辞斥骂，刘备得以逃脱。";
    }

    @Override
    public String getChannel() {
        return "CorneredSilkBag";
    }
}
