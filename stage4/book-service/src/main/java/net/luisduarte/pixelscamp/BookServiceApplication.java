package net.luisduarte.pixelscamp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.bootstrap.BootstrapConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableRedisHttpSession
@EnableWebMvc
@ComponentScan
@EnableDiscoveryClient
@EnableConfigurationProperties
@BootstrapConfiguration
public class BookServiceApplication {
  // This needs to be here because of the Configuration Parsing Order
  @Bean(name = {"defaultRedisSerializer", "springSessionDefaultRedisSerializer"})
  @Primary
  public RedisSerializer<Object> defaultRedisSerializer() {
    return new GenericJackson2JsonRedisSerializer();
  }

  @Value("${security:jwt:signing:key}")
  String key;

  public static void main(String[] args) {
      SpringApplication.run(BookServiceApplication.class, args);
  }
}
