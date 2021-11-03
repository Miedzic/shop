package com.miedzic.shop.flyweight.generic.strategy.email.impl;

import com.miedzic.shop.flyweight.generic.strategy.email.EmailGeneratorStrategy;
import com.miedzic.shop.flyweight.model.EmailType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NewSubscriberEmailGenerator implements EmailGeneratorStrategy {
    @Override
    public void generateEmail() {
        log.info("podziękowanie za wpisanie się na liste i zapowiedź na najbliższe tygodnie");
    }

    @Override
    public EmailType getType() {
        return EmailType.NEW_SUBSCRIBER;
    }
}
