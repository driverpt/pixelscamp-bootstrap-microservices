package net.luisduarte.pixelscamp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;

import javax.annotation.PostConstruct;

@Configuration
public class RedisConfig {

  @Autowired
  private RedisOperationsSessionRepository redisOperationsSessionRepository;

  @PostConstruct
  public void redisTemplate() {
    redisOperationsSessionRepository.setDefaultSerializer(new JdkSerializationRedisSerializer());
  }
}
