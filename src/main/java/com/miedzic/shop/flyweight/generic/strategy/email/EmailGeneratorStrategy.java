package com.miedzic.shop.flyweight.generic.strategy.email;

import com.miedzic.shop.flyweight.generic.strategy.GenericStrategy;
import com.miedzic.shop.flyweight.model.EmailType;

public interface EmailGeneratorStrategy extends GenericStrategy<EmailType> {
    void generateEmail();
}
