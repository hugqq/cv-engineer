package com.hugqq.pdf.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import lombok.SneakyThrows;

public class PdfUtils {

    @SneakyThrows
    public static Paragraph createTitle(String content){
        Font font = new Font(getBaseFont(), 24, Font.BOLD);
        Paragraph paragraph = new Paragraph(content, font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        return paragraph;
    }

    @SneakyThrows
    public static Paragraph createChapterH1(String content) {
        Font font = new Font(getBaseFont(), 22, Font.BOLD);
        Paragraph paragraph = new Paragraph(content, font);
        paragraph.setAlignment(Element.ALIGN_LEFT);
        return paragraph;
    }
    @SneakyThrows
    public static Paragraph createChapterH2(String content) {
        Font font = new Font(getBaseFont(), 18, Font.BOLD);
        Paragraph paragraph = new Paragraph(content, font);
        paragraph.setAlignment(Element.ALIGN_LEFT);
        return paragraph;
    }
    @SneakyThrows
    public static Paragraph createParagraph(String content) {
        Font font = new Font(getBaseFont(), 12, Font.NORMAL);
        Paragraph paragraph = new Paragraph(content, font);
        paragraph.setAlignment(Element.ALIGN_LEFT);
        paragraph.setIndentationLeft(12); //设置左缩进
        paragraph.setIndentationRight(12); //设置右缩进
        paragraph.setFirstLineIndent(24); //设置首行缩进
        paragraph.setLeading(20f); //行间距
        paragraph.setSpacingBefore(5f); //设置段落上空白
        paragraph.setSpacingAfter(10f); //设置段落下空白
        return paragraph;
    }
    @SneakyThrows
    public  static PdfPCell createCell(String content) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        Font font = new Font(getBaseFont(), 12, Font.NORMAL);
        cell.setPhrase(new Phrase(content, font));
        return cell;
    }

    @SneakyThrows
    private static BaseFont getBaseFont()  {
        return BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
    }

}
