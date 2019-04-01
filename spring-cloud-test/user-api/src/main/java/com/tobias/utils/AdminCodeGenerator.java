package com.tobias.utils;

import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.tobias.common.Application;
import java.util.ArrayList;
import java.util.List;

public class AdminCodeGenerator extends CodeGenerator {
    private AdminCodeGenerator(Application app) {
        super(app);
    }

    public static void main(String[] args) {
        AdminCodeGenerator adminCodeGenerator = new AdminCodeGenerator(Application.COMMON);
        adminCodeGenerator.generateByTables("course_live_details");
    }


    @Override
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
                return getProjectPath() + "/src/main/resources/mapper/"
                        +tableInfo.getEntityName().replace("Entity","") + "Mapper.xml";
            }
        });

        cfg.setFileOutConfigList(focList);

        return cfg;
    }

    @Override
    public GlobalConfig getGlobalConfig() {
        return super.getGlobalConfig()
                .setMapperName("%sDao");
    }
}
