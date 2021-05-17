package com.yangzhuo.design.composite.component;

public abstract class Component {
    
    // 职位
    private String position;
    
    // 工作内容
    private String job;
    
    public Component(String position, String job) {
        this.position = position;
        this.job = job;
    }
    
    // 做自己的本职工作
    public void work() {
        System.out.println("我是" + position + ",我正在" + job);
    }
    
    public abstract void addComonpent(Component component);

    public abstract void removeComonpent(Component component);

    public abstract void check();
}
