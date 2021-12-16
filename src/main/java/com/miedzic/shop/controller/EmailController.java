package com.miedzic.shop.controller;

import com.miedzic.shop.flyweight.generic.GenericFactory;
import com.miedzic.shop.flyweight.generic.strategy.email.EmailGeneratorStrategy;
import com.miedzic.shop.flyweight.model.EmailType;
import com.miedzic.shop.flyweight.model.FileType;
import com.miedzic.shop.flyweight.standard.GeneratorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {
    private final GenericFactory<EmailType, EmailGeneratorStrategy> genericFactory;

    @GetMapping
    public void generateEmailTest(@RequestParam EmailType emailType){
        genericFactory.getStrategyByType(emailType).generateEmail();
    }
}
