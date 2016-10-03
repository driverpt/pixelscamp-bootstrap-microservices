package net.luisduarte.pixelscamp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.ExpiringSession;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;

import javax.annotation.PostConstruct;

@Configuration
public class RedisConfig {

  @Autowired
  private RedisOperationsSessionRepository redisOperationsSessionRepository;

  @Primary
  @Bean
  public RedisTemplate<String, ExpiringSession> redisTemplate(RedisConnectionFactory connectionFactory) {
    RedisTemplate<String, ExpiringSession> template = new RedisTemplate<>();

    template.setKeySerializer(new StringRedisSerializer());
    template.setHashKeySerializer(new StringRedisSerializer());
    template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
    template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
    template.setConnectionFactory(connectionFactory);
    return template;
  }

  @PostConstruct
  public void redisTemplate() {
    redisOperationsSessionRepository.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
  }
}
