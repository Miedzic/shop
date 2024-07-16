package com.miedzic.shop.flyweight.standard;

import com.miedzic.shop.flyweight.model.FileType;
import com.miedzic.shop.flyweight.standard.strategy.GeneratorStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GeneratorFactory {
    private final List<GeneratorStrategy> strategies ;
    private Map<FileType,GeneratorStrategy> strategyMap;
    @PostConstruct
    void init(){
         strategyMap = strategies.stream()
                .collect(Collectors.toMap(GeneratorStrategy::getType, Function.identity()));

    }
    public GeneratorStrategy getStrategyByType(FileType fileType){
        return strategyMap.get(fileType);
    }
}
