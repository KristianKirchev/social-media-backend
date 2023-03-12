package com.kristian.socmed.websockettest;

import java.security.Principal;
import java.util.Map;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
// import com.kristian.socmed.service.AuthService;
import com.sun.security.auth.UserPrincipal;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserHandshakeHandler extends DefaultHandshakeHandler {

  // @Autowired
  // private AuthService authService;

  @Override
  protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
      Map<String, Object> attributes) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth != null ? auth.getName() : null;
    // Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
    log.info("User with username: {} opened page", username);
    
    return new UserPrincipal(username);
  }
}
