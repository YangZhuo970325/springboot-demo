package com.yangzhuo.design.composite.component;

import java.util.ArrayList;
import java.util.List;

public class Manager extends Component {
    
    // 管理的组件
    private List<Component> components = new ArrayList<>();
    
    public Manager(String position, String job) {
        super(position, job);
    }

    @Override
    public void addComonpent(Component component) {
        components.add(component);
    }

    @Override
    public void removeComonpent(Component component) {
        components.remove(component);
    }

    //检查下属
    @Override
    public void check() {
        work();
        for (Component component : components) {
            component.check();
        }
    }
}
