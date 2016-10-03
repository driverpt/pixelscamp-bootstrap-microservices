package net.luisduarte.pixelscamp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
@Slf4j
public class BookServiceApplication {
  public static void main(String[] args) {
      SpringApplication.run(BookServiceApplication.class, args);
  }
}
