package net.luisduarte.pixelscamp.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import net.luisduarte.pixelscamp.web.security.model.JwtUser;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.Clock;
import java.time.Instant;
import javax.xml.bind.DatatypeConverter;

@Slf4j
@UtilityClass
public class JwtTokenGenerator {

  public static String generateToken(JwtUser jwtUser, String secret) {
    Claims claims = Jwts.claims().setSubject(jwtUser.getUsername());
    log.info(jwtUser.toString());
    claims.put("userId", jwtUser.getId());
    claims.put("role", jwtUser.getRole());

    return Jwts.builder()
        .setClaims(claims)
        .signWith(SignatureAlgorithm.HS256,
            DatatypeConverter.printBase64Binary(secret.getBytes(StandardCharsets.UTF_8)))
        .setIssuedAt(Date.from(Instant.now(Clock.systemUTC())))
        .setIssuer("Books App")
        .compact();
  }
}
