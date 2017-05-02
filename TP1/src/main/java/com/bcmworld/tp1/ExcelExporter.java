package com.bcmworld.tp1;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;

public class ExcelExporter {
    public static HSSFWorkbook export(List<List<Object>> objects) {

        //Blank workbook
        HSSFWorkbook workbook = new HSSFWorkbook();

        //Create a blank sheet
        HSSFSheet sheet = workbook.createSheet("Clients");

        int rowNum = 0;
        for (List<Object> objectRow : objects) {
            Row row = sheet.createRow(rowNum++);
            int cellNum = 0;
            for (Object obj : objectRow) {
                Cell cell = row.createCell(cellNum++);
                if (obj instanceof String) cell.setCellValue((String) obj);
                else if (obj instanceof Double) cell.setCellValue((Double) obj);
            }
        }

        return workbook;
    }
}