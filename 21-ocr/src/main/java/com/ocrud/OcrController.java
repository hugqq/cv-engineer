package com.ocrud;

import com.ocrud.util.FileUploadUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Slf4j
@RestController
public class OcrController {

    @Autowired
    private ResourceLoader resourceLoader;


    @SneakyThrows
    @PostMapping("ocr")
    public ResponseEntity<String> test(MultipartFile file) {
        String path = FileUploadUtils.upload(file);
        path = path.replaceAll("/profile/", FileUploadUtils.getDefaultBaseDir());
        Resource resource = resourceLoader.getResource("classpath:tessdata/chi_sim.traineddata");
        String absolutePath = resource.getFile().getParent();
        ITesseract instance = new Tesseract();
        //设置语言库所在的文件夹位置，最好是绝对的，不然加载不到就直接报错了
        instance.setDatapath(absolutePath);
        //设置使用的语言库类型：chi_sim 中文简体
        instance.setLanguage("chi_sim");
        String result = instance.doOCR(new File(path));
        return ResponseEntity.ok(result);
    }
}
