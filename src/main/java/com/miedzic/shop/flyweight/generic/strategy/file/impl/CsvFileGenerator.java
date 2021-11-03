package com.miedzic.shop.flyweight.generic.strategy.file.impl;

import com.miedzic.shop.flyweight.generic.strategy.file.FileGeneratorStrategy;
import com.miedzic.shop.flyweight.model.FileType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CsvFileGenerator implements FileGeneratorStrategy {
    @Override
    public byte[] generateFile() {
        log.info("generated csv");
        return new byte[0];
    }

    @Override
    public FileType getType() {
        return FileType.CSV;
    }
}
