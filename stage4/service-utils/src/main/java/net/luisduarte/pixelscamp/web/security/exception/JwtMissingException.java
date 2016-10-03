package net.luisduarte.pixelscamp.web.security.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtMissingException extends AuthenticationException {

  public JwtMissingException(String msg) {
    super(msg);
  }

}
