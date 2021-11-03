package com.miedzic.shop.flyweight.generic.strategy.email.impl;

import com.miedzic.shop.flyweight.generic.strategy.email.EmailGeneratorStrategy;
import com.miedzic.shop.flyweight.model.EmailType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GOEmailGenerator implements EmailGeneratorStrategy {
    @Override
    public void generateEmail() {
        log.info("wysłanie informacji o specjalnej okazji");
    }

    @Override
    public EmailType getType() {
        return EmailType.GOLDEN_OPPORTUNITY;
    }
}
