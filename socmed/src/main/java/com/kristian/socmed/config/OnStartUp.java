package com.kristian.socmed.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.kristian.socmed.model.entity.Role;
import com.kristian.socmed.repository.RoleRepository;
import com.kristian.socmed.repository.UserRepository;
import com.kristian.socmed.service.AuthService;
import com.kristian.socmed.service.RoleService;
import com.kristian.socmed.service.UserService;
import com.kristian.socmed.service.dto.RegisterRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OnStartUp {

    private final RoleService roleService;
    private final RoleRepository roleRepository;
    private final AuthService authService;
    private final UserService userService;
    private final UserRepository userRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() {
        if (!roleRepository.existsByName("USER")) {
            roleService.addRole(new Role(null, "USER", "Social media registered user"));
        }
        if (!roleRepository.existsByName("ADMIN")) {
            roleService.addRole(new Role(null, "ADMIN", "Social media administrator"));
        }
        if (!userRepository.existsByUsername("kris123")) {
            authService.signup(new RegisterRequest("kris.kirchev@gmail.com", "kris123", "kris123"));
            userService.enableUser("kris123");
            userService.assignRole("kris123", "ADMIN");
        }
    }
}