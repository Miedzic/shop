package com.miedzic.shop.controller;

import com.miedzic.shop.flyweight.generic.GenericFactory;
import com.miedzic.shop.flyweight.generic.strategy.file.FileGeneratorStrategy;
import com.miedzic.shop.flyweight.model.FileType;
import com.miedzic.shop.flyweight.standard.GeneratorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
 public class FileController {
    private final GeneratorFactory generatorFactory;
    private final GenericFactory<FileType, FileGeneratorStrategy> genericFactory;
    @GetMapping
    public void generateFile(@RequestParam FileType fileType){
        generatorFactory.getStrategyByType(fileType).generateFile();
    }
    @GetMapping("/generator")
    public void fileGeneratorTest(@RequestParam FileType fileType){
        genericFactory.getStrategyByType(fileType).generateFile();
    }
}
