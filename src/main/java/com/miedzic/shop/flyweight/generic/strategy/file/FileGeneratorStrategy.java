package com.miedzic.shop.flyweight.generic.strategy.file;

import com.miedzic.shop.flyweight.generic.strategy.GenericStrategy;
import com.miedzic.shop.flyweight.model.FileType;

public interface FileGeneratorStrategy extends GenericStrategy<FileType> {
    byte[] generateFile();
}
