package net.luisduarte.pixelscamp;

import net.luisduarte.pixelscamp.web.security.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.bootstrap.BootstrapConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableRedisHttpSession
@EnableWebMvc
@ComponentScan(excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
        value = JwtAuthenticationTokenFilter.class)})
@EnableDiscoveryClient
@EnableConfigurationProperties
@BootstrapConfiguration
public class AuthServiceApplication {

  // This needs to be here because of the Configuration Parsing Order
  @Bean(name = {"defaultRedisSerializer", "springSessionDefaultRedisSerializer"})
  @Primary
  public RedisSerializer<Object> defaultRedisSerializer() {
    return new GenericJackson2JsonRedisSerializer();
  }

  @Value("${security.jwt.signing.key}")
  String secretKey;

  public static void main(String[] args) {
    SpringApplication.run(AuthServiceApplication.class, args);
  }
}
