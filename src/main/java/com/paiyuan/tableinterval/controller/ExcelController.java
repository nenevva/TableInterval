package com.paiyuan.tableinterval.controller;

import com.paiyuan.tableinterval.service.ExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ExcelController {

    private final ExcelService excelService;

    @RequestMapping("/upload")
    public String excelUpload(@RequestParam("uploadFile") MultipartFile uploadFile) throws Exception {

        System.out.println("ExcelController: file uploading...");
        return excelService.excelImport(uploadFile);
    }
}
