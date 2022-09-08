package com.ssafy.rideus.config.data;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class RedisCacheConfig {

    @Bean(name = "cacheManager")
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory){

        // 기본 expireTime 180초로 설정
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig(Thread.currentThread().getContextClassLoader())
                .disableCachingNullValues()
                .entryTtl(Duration.ofSeconds(CacheKey.DEFAULT_EXPIRE_SEC))
                .computePrefixWith(CacheKeyPrefix.simple())
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer<>(Object.class)));
        Map<String,RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

//        // personal_auction_board의 경우 300초로 설정
//        cacheConfigurations.put(CacheKey.PERSONAL_AUCTION_BOARD,RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofSeconds(CacheKey.PERSONAL_AUCTION_EXPIRE_SEC)));
//        // special_auction_board의 경우 300초로 설정
//        cacheConfigurations.put(CacheKey.SPECIAL_AUCTION_BOARD,RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofSeconds(CacheKey.SPECIAL_AUCTION_BOARD_EXPIRE_SEC)));

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory)
                .cacheDefaults(configuration).withInitialCacheConfigurations(cacheConfigurations).build();
    }

}
