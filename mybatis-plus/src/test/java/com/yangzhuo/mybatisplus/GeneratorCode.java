package com.yangzhuo.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;

public class GeneratorCode {

    public static void main(String[] args) {
        //需要构建一个代码自动生成器 对象
        AutoGenerator mpg = new AutoGenerator();
        
        //配置策略
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("yangzhuo");
        gc.setOpen(false);
        gc.setFileOverride(false);
        gc.setServiceName("%sService"); //去掉Service的I前缀
        gc.setIdType(IdType.ASSIGN_ID);
        gc.setDateType(DateType.ONLY_DATE);
        mpg.setGlobalConfig(gc);
        
        //设置数据源
        DataSourceConfig dsc = new DataSourceConfig();
        
        dsc.setUrl("jdbc:mysql://localhost:3306/trade?useSSL=false&useUnicode=true");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);
        
        //设置包
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("user");
        pc.setParent("com.yangzhuo.rocketmq");
        pc.setEntity("entity");
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setController("controller");
        mpg.setPackageInfo(pc);
        
        //策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude(new String[] { "trade_coupon", "trade_goods", "trade_goods_number_log", "trade_mq_consumer_log", "trade_mq_producer_log", "trade_order", "trade_pay", "trade_user", "trade_user_money_log" }); //设置要映射的表名
        strategy.setTablePrefix("trade_");
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setLogicDeleteFieldName("delete");
        //自动填充配置
        TableFill gmt_create = new TableFill("gmt_create", FieldFill.INSERT);
        TableFill gmt_modified = new TableFill("gmt_modified", FieldFill.INSERT_UPDATE);
        ArrayList<TableFill> tableFills = new ArrayList<>();
        tableFills.add(gmt_create);
        tableFills.add(gmt_modified);
        //strategy.setTableFillList(tableFills);
        
        mpg.setStrategy(strategy);
        
        mpg.execute();
        
        
    }
}
