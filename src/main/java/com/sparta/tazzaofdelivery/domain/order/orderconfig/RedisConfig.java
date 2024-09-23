package com.sparta.tazzaofdelivery.domain.order.orderconfig;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


// Redis를 사용하기 위해 Redis 클라이언트 라이브러리 중 Lettuce를 사용하여 Redis 설정 클래스
@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;



    // Redis를 데이터 베이스와 연결 설정하는 메서드
     @Bean
     public RedisConnectionFactory redisConnectionFactory() {
         RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
         redisStandaloneConfiguration.setHostName(host);
         redisStandaloneConfiguration.setPort(port);
         return new LettuceConnectionFactory(redisStandaloneConfiguration);
     }


     @Bean
     public RedisTemplate<String, Object> redisTemplate() {
         RedisTemplate<String,Object> template = new RedisTemplate<>();
         template.setConnectionFactory(redisConnectionFactory());

         template.setKeySerializer(new StringRedisSerializer());
         template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
         return template;
     }


}
