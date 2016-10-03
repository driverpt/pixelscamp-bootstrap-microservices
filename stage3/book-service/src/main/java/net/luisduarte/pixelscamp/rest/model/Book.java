package net.luisduarte.pixelscamp.rest.model;

import lombok.Data;
import lombok.Value;

@Data
@Value(staticConstructor = "of")
public class Book {
  private String isbn;
  private String name;
}
