package com.hugqq;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import com.hugqq.entity.BaseEntity;

import java.util.Collections;
import java.util.function.Consumer;

public class CodeGenerator {

    public static void main(String[] args) {


        DataSourceConfig.Builder builder = new DataSourceConfig.Builder("jdbc:mysql://127.0.0.1:3306/demo", "root", "root")
                .dbQuery(new MySqlQuery())
                .schema("demo")
                .typeConvert(new MySqlTypeConvert())
                .keyWordsHandler(new MySqlKeyWordsHandler());


        Consumer<GlobalConfig.Builder> globalConfigConsumer = globalConfig -> {
            new GlobalConfig.Builder()
                    .outputDir("C:\\Users\\33047\\IdeaProjects\\cv-engineer\\11-mybatisplus\\src\\main\\java")
                    .author("hugqq")
                    .dateType(DateType.TIME_PACK)
                    .commentDate("yyyy-MM-dd");
        };

        Consumer<PackageConfig.Builder> packageConfigConsumer = packageConfig -> {
            new PackageConfig.Builder()
                    .parent("com.hugqq")
                    .moduleName("user")
                    .entity("entity")
                    .service("service")
                    .serviceImpl("service.impl")
                    .mapper("mapper")
                    .xml("mapper.xml")
                    .controller("controller")
                    .pathInfo(Collections.singletonMap(OutputFile.xml, "C:\\Users\\33047\\IdeaProjects\\cv-engineer\\11-mybatisplus\\src\\main\\resources\\mapper"));
        };

        Consumer<TemplateConfig.Builder> templateConfigConsumer = templateConfig -> {
            new TemplateConfig.Builder()
                    .disable(TemplateType.ENTITY)
                    .xml("/templates/mapper.xml")
                    .controller("/templates/controller.java");
        };

        Consumer<StrategyConfig.Builder> strategyConfigConsumer = strategyConfig -> {
            new StrategyConfig.Builder()
                    .enableCapitalMode()
                    .enableSkipView()
                    .disableSqlFilter()
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
                    .addSuperEntityColumns("id", "created_by", "created_time", "updated_by", "updated_time")
                    .addIgnoreColumns()
                    .addTableFills(new Column("create_time", FieldFill.INSERT))
                    .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))
                    .idType(IdType.AUTO)
                    .formatFileName("%sEntity")

                    .controllerBuilder()
                    .enableHyphenStyle()
                    .enableRestStyle()
                    .formatFileName("%sController")

                    .serviceBuilder()
                    .formatServiceFileName("%sService")
                    .formatServiceImplFileName("%sServiceImpl");
        };

        InjectionConfig injectionConfig = new InjectionConfig.Builder().build();

        FastAutoGenerator.create(builder)
                .globalConfig(globalConfigConsumer)
                .packageConfig(packageConfigConsumer)
                .strategyConfig(strategyConfigConsumer)
                .templateConfig(templateConfigConsumer)
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}

