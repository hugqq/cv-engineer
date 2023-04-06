package com.hugqq.word.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import com.hugqq.word.entity.Goods;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.images.ByteArrayImageProvider;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import fr.opensagres.xdocreport.template.formatter.NullImageBehaviour;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class WordController {
    // 在线图片
    private final String ONLINE_IMG_PATH = "https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png";

    @SneakyThrows
    @RequestMapping(value = "exportWord")
    public void exportWord(HttpServletResponse response) {
        InputStream ins = this.getClass().getResourceAsStream("/static/demo.docx");
        //注册XDocReport实例并加载FreeMarker模板引擎
        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(ins,
                TemplateEngineKind.Freemarker);
        //创建XDocReport上下文对象
        IContext context = report.createContext();
        List<Goods> lists = new ArrayList<>();
        lists.add(new Goods("苹果",10.00,1L));
        lists.add(new Goods("香蕉",20.00,2L));
        lists.add(new Goods("橘子",30.00,3L));
        FieldsMetadata fm = report.createFieldsMetadata();
        fm.load("lists", Goods.class, true);
        context.put("city", "北京市");
        context.put("date", DateUtil.now());
        context.put("money",100.00);
        context.put("lists", lists);

        // 在线图片
        byte[] bytes = HttpUtil.createGet(ONLINE_IMG_PATH).execute().bodyBytes();
        context.put("img1", new ByteArrayImageProvider(bytes));
        context.put("img2", new ByteArrayImageProvider(bytes));
        fm.addFieldAsImage("img1");
        fm.addFieldAsImage("img2");
        //生成饼图
        PieChart chart = new PieChartBuilder().width(800).height(600)
                .title("饼图").build();
        chart.addSeries("苹果", 10);
        chart.addSeries("香蕉", 20);
        chart.addSeries("橘子", 30);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BitmapEncoder.saveBitmap(chart, baos, BitmapEncoder.BitmapFormat.JPG);
        context.put("chart", new ByteArrayImageProvider(baos.toByteArray()));
        fm.addFieldAsImage("chart");
        report.setFieldsMetadata(fm);
        // 读到流中
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/msword");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        String fileName = "demo.docx";
        response.setHeader("Content-Disposition", "attachment;filename="+
                new String(fileName.getBytes(StandardCharsets.UTF_8)));
        report.process(context, response.getOutputStream());
        ins.close();
    }
}
