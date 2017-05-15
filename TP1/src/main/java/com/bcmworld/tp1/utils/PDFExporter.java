package com.bcmworld.tp1.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.ServletOutputStream;
import java.util.List;

public class PDFExporter {

    public static void export(List<List<Object>> objects, ServletOutputStream fos) {

        Document pdf = new Document();
        try {
            PdfWriter.getInstance(pdf, fos);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        pdf.open();

        PdfPTable my_table = new PdfPTable(objects.get(0).size());

        PdfPCell table_cell;
        for (List<Object> objectRow : objects) {
            for (Object obj : objectRow) {
                table_cell = new PdfPCell(new Phrase(obj.toString()));
                my_table.addCell(table_cell);
            }
        }
        try {
            pdf.add(my_table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        pdf.close();
    }
}