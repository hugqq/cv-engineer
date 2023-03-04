package com.hugqq;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import com.hugqq.entity.BaseEntity;

import java.util.Collections;

public class CodeGenerator {

    public static void main(String[] args) {



        DataSourceConfig dataSourceConfig = new DataSourceConfig.Builder("jdbc:mysql://127.0.0.1:3306/demo", "root", "root")
                .dbQuery(new MySqlQuery())
                .schema("demo")
                .typeConvert(new MySqlTypeConvert())
                .keyWordsHandler(new MySqlKeyWordsHandler()).build();


        GlobalConfig globalConfig = new GlobalConfig.Builder()
                .disableOpenDir()
                .fileOverride()
                .author("hugqq")
                .outputDir("C:\\Users\\33047\\IdeaProjects\\cv-engineer\\11-mybatisplus\\src\\main\\java").build();

        PackageConfig packageConfig =
                new PackageConfig.Builder()
                        .parent("com.hugqq")
                        .moduleName("user")
                        .entity("entity")
                        .service("service")
                        .serviceImpl("service.impl")
                        .mapper("mapper")
                        .xml("mapper.xml")
                        .controller("controller")
                        .pathInfo(Collections.singletonMap(OutputFile.xml, "C:\\Users\\33047\\IdeaProjects\\cv-engineer\\11-mybatisplus\\src\\main\\resources\\mapper")).build();

        TemplateConfig templateConfig =
                new TemplateConfig.Builder()
//                    .disable(TemplateType.ENTITY)
                        .controller("templates/controller.java")
                        .entity("templates/entity.java")
                        .service("templates/service.java")
                        .serviceImpl("templates/serviceImpl.java")
                        .mapper("templates/mapper.java")
                        .xml("/templates/mapper.xml")
                        .controller("/templates/controller.java").build();
        ;

        StrategyConfig strategyConfig =
                new StrategyConfig.Builder()
                        .enableCapitalMode()
                        .enableSkipView()
                        .disableSqlFilter()
                        .addInclude("t_user")
                        .addTablePrefix("t_")
                        .entityBuilder()
                        .superClass(BaseEntity.class)
                        .disableSerialVersionUID()
                        .enableChainModel()
                        .enableLombok()
                        .enableRemoveIsPrefix()
                        .enableTableFieldAnnotation()
                        .logicDeleteColumnName("deleted")
                        .naming(NamingStrategy.underline_to_camel)
                        .columnNaming(NamingStrategy.underline_to_camel)
                        .addSuperEntityColumns("id", "create_by", "create_time", "update_by", "update_time", "remark")
                        .addIgnoreColumns()
                        .addTableFills(new Column("create_time", FieldFill.INSERT))
                        .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))
                        .idType(IdType.AUTO)
//                        .formatFileName("%sEntity")
                        .controllerBuilder()
                        .enableHyphenStyle()
                        .enableRestStyle()
                        .formatFileName("%sController")
                        .serviceBuilder()
                        .formatServiceFileName("%sService")
                        .formatServiceImplFileName("%sServiceImpl")
                        .mapperBuilder()
                        .enableMapperAnnotation()
                        .enableBaseResultMap()
                        .enableBaseColumnList()
                        .formatMapperFileName("%sMapper")
                        .formatXmlFileName("%sMapper")
                        .build();

        InjectionConfig injectionConfig = new InjectionConfig.Builder().build();


        AutoGenerator autoGenerator = new AutoGenerator(dataSourceConfig);
        autoGenerator.strategy(strategyConfig);
        autoGenerator.packageInfo(packageConfig);
        autoGenerator.global(globalConfig);
        autoGenerator.template(templateConfig);
        autoGenerator.injection(injectionConfig);
        autoGenerator.execute(new FreemarkerTemplateEngine());

//        FastAutoGenerator.create(builder)
//                .globalConfig(globalConfigConsumer)
//                .packageConfig(packageConfigConsumer)
//                .strategyConfig(strategyConfigConsumer)
//                .templateConfig(templateConfigConsumer)
//                .templateEngine(new FreemarkerTemplateEngine())
//                .execute();
    }
}

