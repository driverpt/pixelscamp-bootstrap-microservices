package net.luisduarte.pixelscamp.auth.handlers;

import lombok.extern.slf4j.Slf4j;
import net.luisduarte.pixelscamp.web.security.JwtTokenGenerator;
import net.luisduarte.pixelscamp.web.security.model.JwtUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.MessageFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
@Slf4j
public class RedisSessionAuthenticationSuccessHandler
    implements AuthenticationSuccessHandler {

  @Value("${security.jwt.signing.key}")
  private String key;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                      HttpServletResponse httpServletResponse,
                                      Authentication authentication)
      throws IOException, ServletException {
    HttpSession session = httpServletRequest.getSession();
    User user = (User) authentication.getPrincipal();
    JwtUser jwtUser = new JwtUser();
    jwtUser.setId(user.getUsername());
    jwtUser.setUsername(user.getUsername());
    jwtUser.setRole(user.getAuthorities().stream().findFirst()
        .orElse(new SimpleGrantedAuthority("ROLE_ANONYMOUS")).getAuthority());
    String jwt = JwtTokenGenerator.generateToken(jwtUser, key);
    session.setAttribute("JWT", jwt);

    log.info(MessageFormat.format("Generating Jwt from Principal: {0}", user));
    log.info(MessageFormat.format("Generated JwtUser: {0}", jwtUser));
    log.info(MessageFormat.format("Generated JWT: {0}", jwt));
  }
}
