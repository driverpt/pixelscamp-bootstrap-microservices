package net.luisduarte.pixelscamp.web.security;

import com.auth0.jwt.Algorithm;
import com.auth0.jwt.JWTSigner;
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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.xml.bind.DatatypeConverter;

@Slf4j
@UtilityClass
public class JwtTokenGenerator {

  private static final JWTSigner.Options jwtSignerOptions = new JWTSigner.Options();

  static {
    jwtSignerOptions.setExpirySeconds((int) TimeUnit.MINUTES.toSeconds(20));
    jwtSignerOptions.setIssuedAt(true);
    jwtSignerOptions.setAlgorithm(Algorithm.HS256);
  }

  public static String generateTokenAuth0(JwtUser jwtUser, String secret) {
    final JWTSigner signer = new JWTSigner(secret);
    final Map<String, Object> claims = new HashMap<>();
    Instant now = Instant.now(Clock.systemUTC());
    claims.put("iss", "Foo");
    claims.put("userId", jwtUser.getId());
    claims.put("role", jwtUser.getRole());

    return signer.sign(claims);
  }

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
