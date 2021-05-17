package com.yangzhuo.design.composite.component;

import java.util.ArrayList;
import java.util.List;

public class Employee extends Component {
    
    // 管理的组件
    private List<Component> components = new ArrayList<>();
    
    public Employee(String position, String job) {
        super(position, job);
    }
    
    public void addComonpent(Component component) {
        System.out.println("职员没有管理权限");
    }

    @Override
    public void removeComonpent(Component component) {
        System.out.println("职员没有管理权限");
    }

    //检查下属
    @Override
    public void check() {
        work();
    }
}
