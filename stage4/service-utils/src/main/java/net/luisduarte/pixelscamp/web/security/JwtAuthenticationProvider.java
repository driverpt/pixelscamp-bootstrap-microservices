package net.luisduarte.pixelscamp.web.security;

import net.luisduarte.pixelscamp.web.security.model.JwtAuthenticationToken;
import net.luisduarte.pixelscamp.web.security.model.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.List;

@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

  @Autowired
  private JwtTokenValidator jwtTokenValidator;

  @Override
  public boolean supports(Class<?> authentication) {
    return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
  }

  @Override
  protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
  }

  @Override
  protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
    String token = jwtAuthenticationToken.getToken();

    // Sorry about this, had to drop the "hammer" on this just to get the demo working.
    // It's easier this way
    if ( !StringUtils.isEmpty(token) && token.startsWith("\"") && token.endsWith("\"") ) {
      token = token.substring(1, token.length() - 1);
      logger.info(MessageFormat.format("New Token: {0}", token));
    }

    JwtUser parsedUser = jwtTokenValidator.parseToken(token);

    if (parsedUser == null) {
      throw new IllegalArgumentException(MessageFormat.format("JWT token is not valid: {0}", token));
    }

    List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(parsedUser.getRole());

    return new AuthenticatedUser(parsedUser.getId(), parsedUser.getUsername(), token, authorityList);
  }



}
