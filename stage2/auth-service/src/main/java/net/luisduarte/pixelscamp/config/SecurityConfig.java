package net.luisduarte.pixelscamp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
//        .sessionManagement()
//        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//        .and()
        .formLogin()
        .loginPage("/login")
        .permitAll()
        .and()
        .authorizeRequests()
          .antMatchers("/login").permitAll()
          .anyRequest().authenticated()
        .and()
        .csrf().disable()

    ;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }

  @Bean
  @Autowired
  public AuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder) {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
    daoAuthenticationProvider.setUserDetailsService(userDetailsService);

    return daoAuthenticationProvider;
  }

  @Bean
  public UserDetailsService inMemoryUserDetailsService() {
    List<UserDetails> defaultUserDetails = new LinkedList<>();

    defaultUserDetails.add(
        new User("foo", "bar", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
    );

    return new InMemoryUserDetailsManager(defaultUserDetails);
  }

  @Autowired
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .userDetailsService(userDetailsService)
        .and()
        .authenticationProvider(authenticationProvider)
    ;
  }
}
