package net.luisduarte.pixelscamp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableRedisHttpSession
@EnableWebMvc
@ComponentScan
public class BookServiceApplication {
  public static void main(String[] args) {
      SpringApplication.run(BookServiceApplication.class, args);
  }
}
