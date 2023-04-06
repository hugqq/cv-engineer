package com.hugqq.pdf.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.hugqq.excel.entity.User;
import com.hugqq.pdf.utils.MyHeaderFooterPageEventHelper;
import com.hugqq.pdf.utils.PdfUtils;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


@RestController
public class PdfController {


    @SneakyThrows
    @ApiOperation("exportPdf")
    @GetMapping("/exportPdf")
    public void exportPdf(HttpServletResponse response) {
        response.reset();
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition",
                "attachment;filename=pdf_" + IdUtil.getSnowflakeNextId() + ".pdf");
        OutputStream os = response.getOutputStream();
        generateWordXWPFDocument(os);
        os.close();
    }

    @SneakyThrows
    private Document generateWordXWPFDocument(OutputStream os) {
        Document document = new Document(PageSize.A4);
        PdfWriter pdfWriter = PdfWriter.getInstance(document, os);
        // 开启水印
        pdfWriter.setPageEvent(new MyHeaderFooterPageEventHelper("我是一个水印",
                "SpringBoot集成ItextPDF导出",
                "https://ocrud.com",
                "https://ocrud.com"));
        document.open();
        // 添加元数据
        document.addAuthor("测试作者");
        document.addCreationDate();
        document.addTitle("测试标题");
        document.addKeywords("测试关键词");
        document.addCreator("测试创建者");
        document.add(PdfUtils.createTitle("测试正文标题"));
        document.add(PdfUtils.createChapterH1("1. 标题1"));
        document.add(PdfUtils.createChapterH2("1.1 标题1-1"));
        document.add(PdfUtils.createParagraph("我真帅 我真帅 我真帅 我真帅 我真帅 我真帅 我真帅 我真帅 我真帅"));
        document.add(PdfUtils.createChapterH2("1.2 标题1-2"));
        document.add(PdfUtils.createParagraph("我真帅 我真帅 我真帅 我真帅 我真帅 我真帅 我真帅 我真帅 我真帅"));
        document.add(PdfUtils.createChapterH1("2. 标题2"));
        document.add(PdfUtils.createChapterH2("2.1 表格导出示例"));
        document.add(PdfUtils.createParagraph("下面是表格内容"));
        // 表格
        List<User> userList = getUserList();
        PdfPTable table = new PdfPTable(new float[]{10, 20, 10, 40, 60, 70});
        table.setTotalWidth(500);
        table.setLockedWidth(true);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setBorder(1);
        for (User user : userList) {
            table.addCell(PdfUtils.createCell(String.valueOf(user.getId()+1)));
            table.addCell(PdfUtils.createCell(user.getName()));
            table.addCell(PdfUtils.createCell(user.getSex() == 1 ? "男" : "女"));
            table.addCell(PdfUtils.createCell(user.getPhone()));
            table.addCell(PdfUtils.createCell(user.getEmail()));
            table.addCell(PdfUtils.createCell(user.getCard()));
        }
        document.add(table);
        document.add(PdfUtils.createChapterH2("2.2 图片导出示例"));
        document.add(PdfUtils.createParagraph("下面是导出的图片"));
        // 图片
        Resource resource = new ClassPathResource("static/demo.png");
        Image image = Image.getInstance(resource.getURL());
        image.setAlignment(Element.ALIGN_CENTER);
        image.scalePercent(5.0f); // 缩放
        document.add(image);
        document.close();
        return document;
    }
    private List<User> getUserList() {
        List<User> class1 = new ArrayList<>();
        IntStream.range(0,10).forEach(e->{
            class1.add(User.builder().id(e).name("张三"+e).sex(1).phone("13333333333")
                    .email("123456789@qq.com").birthday(LocalDateTime.now())
                    .card(RandomUtil.randomNumbers(18))
                    .build());
        });
        return class1;
    }


}

