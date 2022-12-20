package com.kristian.socmed.websockettest;

import java.security.Principal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.kristian.socmed.service.AuthService;
import com.sun.security.auth.UserPrincipal;

public class UserHandshakeHandler extends DefaultHandshakeHandler {

    @SuppressWarnings("unused")
	@Autowired
    private AuthService authService;
    private Logger log = LoggerFactory.getLogger(UserHandshakeHandler.class);

    @SuppressWarnings("unused")
	@Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        String username = "pera";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("User with username: "+username+" opened page");
        return new UserPrincipal(username);
    }
}