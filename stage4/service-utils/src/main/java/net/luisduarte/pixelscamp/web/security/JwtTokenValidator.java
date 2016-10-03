package net.luisduarte.pixelscamp.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import net.luisduarte.pixelscamp.web.security.model.JwtUser;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

@Component
@Slf4j
public class JwtTokenValidator implements InitializingBean {

  @Value("${security.jwt.signing.key}")
  private String secret;

  public JwtUser parseToken(String token) {
    JwtUser jwtUser = null;

    try {
      Claims body = Jwts.parser()
          .setSigningKey(DatatypeConverter.printBase64Binary(secret.getBytes(StandardCharsets.UTF_8)))
          .parseClaimsJws(token)
          .getBody();

      jwtUser = new JwtUser();
      jwtUser.setUsername(body.getSubject());
      jwtUser.setId(body.get("userId").toString());
      jwtUser.setRole((String) body.get("role"));

    } catch (JwtException e) {
      // Simply print the exception and null will be returned for the userDto
      log.error("Exception while parsing JWT", e);
      e.printStackTrace();
    }

    return jwtUser;
  }

  private SecretKey secretKey;

  @Override
  public void afterPropertiesSet() throws Exception {
    if (!StringUtils.isEmpty(secret)) {
      secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "AES");
    }
  }
}
