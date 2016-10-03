package net.luisduarte.pixelscamp.web.security;

import lombok.extern.slf4j.Slf4j;
import net.luisduarte.pixelscamp.web.security.model.JwtAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends GenericFilterBean {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                       FilterChain filterChain) throws IOException, ServletException {
      HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
    HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

    String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

    if (!StringUtils.isEmpty(header) && header.startsWith("Bearer ")) {
      // I hate magic numbers, but i just want to keep it simple
      String authToken = header.substring(7);

      JwtAuthenticationToken authRequest = new JwtAuthenticationToken(authToken);

      Authentication result = authenticationManager.authenticate(authRequest);
      SecurityContextHolder.getContext().setAuthentication(result);
    }

    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }
}
