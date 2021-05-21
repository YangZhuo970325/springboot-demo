package com.yangzhuo.design.strategy.service.impl;

import com.yangzhuo.design.strategy.service.SilkBag;
import org.springframework.stereotype.Service;

@Service
public class EndYearSilkBag implements SilkBag {

    @Override
    public String solveProblem() {
        return "年底锦囊打开：赵云向刘备报告：曹操兴兵50 万报赤壁之仇，荆州危急，主公要赶快回去；刘备大惊，准备回荆州。";
    }

    @Override
    public String getChannel() {
        return "EndYearSilkBag";
    }
}
