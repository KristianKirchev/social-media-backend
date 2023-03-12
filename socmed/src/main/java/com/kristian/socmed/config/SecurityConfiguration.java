package com.kristian.socmed.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.AllArgsConstructor;

@EnableWebSecurity
@AllArgsConstructor
@Configuration
public class SecurityConfiguration {
  private UserDetailsService userDetailsService;
  private RsaKeyProperties rsaKeyProperties;

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .csrf()
        .disable()
        .authorizeHttpRequests()
        .requestMatchers("/api/auth/**")
        .permitAll()
        .requestMatchers(HttpMethod.OPTIONS, "/api/**")
        .permitAll()
        .requestMatchers(HttpMethod.GET, "/api/topic/**")
        .permitAll()
        .requestMatchers("/api/post/authAll")
        .hasAuthority("SCOPE_USER")
        .requestMatchers(HttpMethod.GET, "/api/post/{id}")
        .permitAll()
        .requestMatchers(HttpMethod.GET, "/api/post/all")
        .permitAll()
        .requestMatchers("/api/post/secured/**")
        .hasAuthority("SCOPE_ADMIN")
        .requestMatchers("/sba-websocket/**")
        .permitAll()
        .requestMatchers(HttpMethod.GET, "/api/comment/**")
        .permitAll()
        .requestMatchers(HttpMethod.GET, "/api/user/**")
        .permitAll()
//        .requestMatchers(HttpMethod.GET, "/api/react/**")
//        .permitAll()
        .requestMatchers("/api/admin/**")
        .hasAuthority("SCOPE_ADMIN")
        .requestMatchers("/api/role/**")
        .permitAll()
        .requestMatchers("/api/user/{username}/assign/{rolename}")
        .permitAll()
        .requestMatchers(HttpMethod.DELETE, "/api/user/**")
        .hasAuthority("SCOPE_ADMIN")
        .requestMatchers("/api/message/**")
        .hasAuthority("SCOPE_USER")
        .anyRequest()
        .authenticated()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

    return http.build();
  }

  @Bean
  JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withPublicKey(rsaKeyProperties.getPublicKey()).build();
  }

  @Bean
  JwtEncoder jwtEncoder() {
    JWK jwk = new RSAKey.Builder(rsaKeyProperties.getPublicKey()).privateKey(rsaKeyProperties.getPrivateKey()).build();
    JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));

    return new NimbusJwtEncoder(jwks);
  }

  @Bean
  WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().requestMatchers(HttpMethod.OPTIONS, "/**");
  }

  @Bean(BeanIds.AUTHENTICATION_MANAGER)
  AuthenticationManager authManager(HttpSecurity http) throws Exception {
    return http.getSharedObject(AuthenticationManagerBuilder.class)
        .userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder())
        .and()
        .build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
