package net.luisduarte.pixelscamp.config;

import net.luisduarte.pixelscamp.web.security.JwtAuthenticationEntryPoint;
import net.luisduarte.pixelscamp.web.security.JwtAuthenticationProvider;
import net.luisduarte.pixelscamp.web.security.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders
    .AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private JwtAuthenticationTokenFilter filter;

  @Autowired
  private JwtAuthenticationProvider authenticationProvider;

  @Autowired
  private JwtAuthenticationEntryPoint authenticationEntryPoint;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .authorizeRequests()
        .anyRequest().authenticated()
        .antMatchers("/health").permitAll()
        .antMatchers("/metrics").permitAll()
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
        .and()
        .authenticationProvider(authenticationProvider)
        .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
        .and()
        .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
    ;
  }

  @Override
  public void init(WebSecurity web) throws Exception {
    web
        .ignoring()
        .antMatchers("/health", "/metrics", "/info");
  }

  @Override
  @Autowired
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .authenticationProvider(authenticationProvider)
    ;
  }
}
