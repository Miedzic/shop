package com.miedzic.shop.flyweight.generic.strategy.file.impl;

import com.miedzic.shop.domain.dao.Product;
import com.miedzic.shop.flyweight.generic.strategy.file.FileGeneratorStrategy;
import com.miedzic.shop.flyweight.model.FileType;
import com.miedzic.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class XlsFileGenerator implements FileGeneratorStrategy {
    private final ProductRepository productRepository;

    @Override
    public byte[] generateFile() {
        List<Product> allProducts = productRepository.findAll();
        try {
            Workbook workbook = WorkbookFactory.create(false);
            Sheet sheet = workbook.createSheet("report");
            int index = 0;
            Row row = sheet.createRow(index);
            row.createCell(0).setCellValue("id");
            row.createCell(1).setCellValue("name");
            row.createCell(2).setCellValue("category");
            row.createCell(3).setCellValue("cost");
            row.createCell(4).setCellValue("createdDate");
            row.createCell(5).setCellValue("createdBy");
            row.createCell(6).setCellValue("lastModifiedDate");
            row.createCell(7).setCellValue("lastModifiedBy");

            for (final Product allProduct : allProducts) {
                index++;
                row = sheet.createRow(index);
                row.createCell(0).setCellValue(allProduct.getId());
                row.createCell(1).setCellValue(allProduct.getName());
                row.createCell(2).setCellValue(allProduct.getCategory());
                row.createCell(3).setCellValue(allProduct.getCost());
                row.createCell(4).setCellValue(allProduct.getCreatedDate());
                row.createCell(5).setCellValue(allProduct.getCreatedBy());
                row.createCell(6).setCellValue(allProduct.getLastModifiedDate());
                row.createCell(7).setCellValue(allProduct.getLastModifiedBy());
            }
            sheet.setAutoFilter(new CellRangeAddress(0, allProducts.size(), 0, 7));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            workbook.write(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            log.error("error during generating excel", e);
        }
        log.info("generated xls");
        return new byte[0];
    }

    @Override
    public FileType getType() {
        return FileType.XLS;
    }
}
