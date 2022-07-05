package com.paiyuan.tableinterval.service;

import com.alibaba.fastjson.JSON;
import com.paiyuan.tableinterval.entity.Result;
import com.paiyuan.tableinterval.util.Counter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.ArrayList;

/*
 * 读取上传的Excel文件，提取数据并递交给Counter进行计算
 */

@Service
public class ExcelService {

    //表单序号，从0开始
    @Value("${sheetNum}")
    private int sheetNum;

    //列序号，从0开始
    @Value("${columnNum}")
    private int columnNum;

    //起始行序号，从0开始
    @Value("${rowStartPos}")
    private int rowStartPos;

    //终止行序号，从0开始
    @Value("${rowEndPos}")
    private int rowEndPos;

    @Autowired
    private Counter counter;

    public String excelImport(MultipartFile excelFilePath) throws Exception {

        System.out.println("ExcelService: " + excelFilePath.getOriginalFilename() + " " + excelFilePath.getSize());

        String fileName = excelFilePath.getOriginalFilename();
        InputStream is = excelFilePath.getInputStream();

        Workbook workbook;

        assert fileName != null;
        if (fileName.endsWith("xls")) {
            workbook = new HSSFWorkbook(is);
        } else if (fileName.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(is);
        } else {
            System.out.println("[Error] ExcelService: wrong file format.");
            return "ERROR: wrong file format.";
        }

        Sheet sheet = workbook.getSheetAt(sheetNum);

        Row row;
        ArrayList<Double> dataList = new ArrayList<>();

        int endPos = rowEndPos == -1 ? sheet.getLastRowNum() : rowEndPos;

        try {
            for (int i = rowStartPos; i <= endPos; i++) {
                row = sheet.getRow(i);
                dataList.add(row.getCell(columnNum).getNumericCellValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[ERROR] ExcelService: fail reading. Please check your configuration file and Excel Sheet.");
            return "ERROR: fail reading.";
        }

        Result res = counter.calculate(dataList, rowStartPos);
        System.out.println("ExcelService: " + JSON.toJSONString(res));

        return JSON.toJSONString(res);
    }
}
