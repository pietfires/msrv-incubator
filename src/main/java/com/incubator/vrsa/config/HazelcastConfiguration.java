package com.incubator.vrsa.config;

import com.hazelcast.config.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration {
    @Value("${hazelcast.movies.ttl}")
    private int timeToLiveSeconds;
    @Bean
    public Config hazelcastConfig() {
        return new Config()
                .setInstanceName("hazelcastInstance")
                .addMapConfig(
                        new MapConfig()
                                .setName("movies")
                                .setEvictionConfig(
                                        new EvictionConfig()
                                                .setEvictionPolicy(EvictionPolicy.LRU)
                                                .setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE)
                                                .setSize(200)
                                )
                                .setTimeToLiveSeconds(timeToLiveSeconds)
                );
    }
}
