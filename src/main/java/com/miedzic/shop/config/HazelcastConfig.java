package com.miedzic.shop.config;


import com.hazelcast.config.*;
import com.miedzic.shop.domain.dao.Product;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class HazelcastConfig {
    @Bean
    public Config configHazelcast() {
        var config = new Config()
                .setInstanceName("hazelcast-instance")
                .addMapConfig(new MapConfig()
                        .setName("product")
                        .setEvictionConfig(new EvictionConfig()
                                .setSize(10)
                                .setEvictionPolicy(EvictionPolicy.LRU)  // LFU - najrzadziej  LRU - najdawniej
                                .setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE))
                        .setTimeToLiveSeconds(60 * 60 * 24));
        config.getSerializationConfig().addDataSerializableFactory
                (1, (int id) -> (id == 1) ? new Product() : null);
        return config;
    }
}
