package com.tobias.utils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.tobias.common.Application;
import java.util.ArrayList;
import java.util.List;

public abstract class CodeGenerator {

  private static final String DB_URL = "jdbc:mysql://10.0.98.5:3306/hq_school2018?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&useSSL=false";

  private static final String USER_NAME = "root";

  private static final String PASSWORD = "root";

  private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";

  private Application app;
  private String projectPath;

  protected CodeGenerator(Application app) {
    this.app = app;
    projectPath = System.getProperty("user.dir") + "\\" + app.getPath()[1];
  }

  String getProjectPath() {
    return projectPath;
  }

  private void generateByTables(Application app, String... tableNames) {

    // 数据源配置
    DataSourceConfig dsc = getDataSourceConfig();

    // 策略配置
    StrategyConfig strategy = getStrategyConfig(tableNames);

    // 全局配置
    GlobalConfig gc = getGlobalConfig();

    // 包配置
    PackageConfig pc = getPackageConfig(app);

    // 自定义配置
    InjectionConfig cfg = getInjectionConfig();

    // 代码生成器
    new AutoGenerator()
        .setDataSource(dsc)
        .setStrategy(strategy)
        .setGlobalConfig(gc)
        .setPackageInfo(pc)
        .setCfg(cfg)
        .setTemplate(new TemplateConfig().setXml("/templates/mapper.xml"))
        .setTemplateEngine(new FreemarkerTemplateEngine())
        .execute();
  }

  private DataSourceConfig getDataSourceConfig() {
    DataSourceConfig dsc = new DataSourceConfig();
    dsc.setUrl(DB_URL)
        .setDbType(DbType.MYSQL)
        .setDriverName(DRIVER_NAME)
        .setUsername(USER_NAME)
        .setPassword(PASSWORD);

    return dsc;
  }

  private StrategyConfig getStrategyConfig(String... tableNames) {
    StrategyConfig strategy = new StrategyConfig();
    strategy.setNaming(NamingStrategy.underline_to_camel)
        .setColumnNaming(NamingStrategy.underline_to_camel)
        .setEntityLombokModel(true)
        .setRestControllerStyle(true)
        .setInclude(tableNames)
        .setTablePrefix("tb_")
        .setControllerMappingHyphenStyle(true);

    return strategy;
  }

  public GlobalConfig getGlobalConfig() {
    GlobalConfig gc = new GlobalConfig();
    gc.setOutputDir(projectPath + "/src/main/java")
        .setAuthor("hzr")
        .setOpen(false)
        // .setFileOverride(true)
        .setEnableCache(false)
        .setMapperName("%sMapper")
        .setServiceName("%sService");

    return gc;
  }

  private PackageConfig getPackageConfig(Application app) {
    PackageConfig pc = new PackageConfig();
    pc.setParent(app.getPath()[0])
        .setEntity("entity")
        .setController("controller")
        .setService("service")
        .setServiceImpl("service.impl")
        .setMapper("dao");

    return pc;
  }

  public InjectionConfig getInjectionConfig() {
    InjectionConfig cfg = new InjectionConfig() {
      @Override
      public void initMap() {
        // to do nothing
      }
    };
    List<FileOutConfig> focList = new ArrayList<>();
    focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
      @Override
      public String outputFile(TableInfo tableInfo) {
        // 自定义输入文件名称
        return projectPath + "/src/main/resources/mapper/"
            + tableInfo.getEntityName().replace("Entity", "") + "Mapper.xml";
      }
    });

    cfg.setFileOutConfigList(focList);

    return cfg;
  }


  void generateByTables(String... tableNames) {
    generateByTables(app, tableNames);
  }

}
