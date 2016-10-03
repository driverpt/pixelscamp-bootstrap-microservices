package net.luisduarte.pixelscamp;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("book-service")
public class BookServiceProperties {
    private String foo = "bar";
}
