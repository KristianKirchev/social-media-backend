package com.kristian.socmed.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kristian.socmed.config.AppConfiguration;
import com.kristian.socmed.model.entity.User;
import com.kristian.socmed.model.entity.VerificationToken;
import com.kristian.socmed.provider.JwtProvider;
import com.kristian.socmed.repository.UserRepository;
import com.kristian.socmed.repository.VerificationTokenRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
	private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;
    private VerificationTokenRepository verificationTokenRepository;
    private AppConfiguration appConfig;
    //private RoleRepository roleRepository;
    //private MailService mailService;
    
    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken vt = new VerificationToken(null,token,user);
        verificationTokenRepository.save(vt);
        return token;
    }
}
