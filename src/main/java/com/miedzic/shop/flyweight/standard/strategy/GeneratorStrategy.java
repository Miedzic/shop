package com.miedzic.shop.flyweight.standard.strategy;

import com.miedzic.shop.flyweight.model.FileType;

public interface GeneratorStrategy {
    FileType getType();
    byte[] generateFile();
}
