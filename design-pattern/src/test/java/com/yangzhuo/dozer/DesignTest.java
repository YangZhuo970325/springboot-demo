package com.yangzhuo.dozer;


import com.yangzhuo.design.composite.component.Component;
import com.yangzhuo.design.composite.component.Employee;
import com.yangzhuo.design.composite.component.Manager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DesignTest {
    
    @Test
    public void test() {
        Component boss = new Manager("老板", "唱怒放的生命");
        Component HR = new Employee("人力资源", "聊微信");
        Component PM = new Manager("产品经理", "不知道干啥");
        Component CFO = new Manager("财务主管", "看剧");
        Component CTO = new Manager("技术主管", "划水");
        Component UI = new Employee("设计师", "画画");
        Component operator = new Employee("运营人员", "兼职客服");
        Component webProgrammer = new Employee("程序员", "学习设计模式");
        Component backgroundProgrammer = new Employee("后台程序员", "CRUD");
        Component accountant = new Employee("会计", "背九九乘法表");
        Component clerk = new Employee("文员", "给老板递麦克风");
        
        boss.addComonpent(HR);
        boss.addComonpent(PM);
        boss.addComonpent(CFO);
        
        PM.addComonpent(CTO);
        PM.addComonpent(UI);
        PM.addComonpent(operator);
        
        CTO.addComonpent(webProgrammer);
        CTO.addComonpent(backgroundProgrammer);

        CFO.addComonpent(accountant);
        CFO.addComonpent(clerk);
        
        boss.check();
    }
    
    @Test
    public void test1() {
        Doc doc = new Doc();
        Header header = new Header();
        header.setHeaderName("headerName1");
        header.setDoc(doc);
        List<Line> lines = new ArrayList<>();
        Line line = new Line();
        line.setLineName("lineName1");
        line.setDoc(doc);
        lines.add(line);
        doc.setHeader(header);
        doc.setLineList(lines);
        System.out.println(doc.getHeader().getDoc().getHeader().getHeaderName());
    }
}
