package com.miedzic.shop.controller;

import com.miedzic.shop.flyweight.generic.GenericFactory;
import com.miedzic.shop.flyweight.generic.strategy.file.FileGeneratorStrategy;
import com.miedzic.shop.flyweight.model.FileType;
import com.miedzic.shop.flyweight.standard.GeneratorFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public void generateFile(@RequestParam FileType fileType) {
        generatorFactory.getStrategyByType(fileType).generateFile();
    }

    @GetMapping("/generator")
    @PreAuthorize("isAuthenticated()")
    @Operation(security = @SecurityRequirement(name = "bearer token"))
    public ResponseEntity<byte[]> fileGeneratorTest(@RequestParam FileType fileType) {
        byte[] file = genericFactory.getStrategyByType(fileType).generateFile();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        httpHeaders.set(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length));
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=report." + fileType.name().toLowerCase());
        return ResponseEntity.ok().headers(httpHeaders).body(file);
    }
}
