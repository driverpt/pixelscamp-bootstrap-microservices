package net.luisduarte.pixelscamp.web.security.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class JwtUser {
  private String id;
  private String username;
  private String role;
}
