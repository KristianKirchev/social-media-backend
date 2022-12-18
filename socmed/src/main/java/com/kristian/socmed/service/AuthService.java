package com.kristian.socmed.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kristian.socmed.config.AppConfiguration;
import com.kristian.socmed.exception.MyRuntimeException;
import com.kristian.socmed.model.entity.Role;
import com.kristian.socmed.model.entity.User;
import com.kristian.socmed.model.entity.VerificationEmail;
import com.kristian.socmed.model.entity.VerificationToken;
import com.kristian.socmed.provider.JwtProvider;
import com.kristian.socmed.repository.RoleRepository;
import com.kristian.socmed.repository.UserRepository;
import com.kristian.socmed.repository.VerificationTokenRepository;
import com.kristian.socmed.service.dto.AuthResponse;
import com.kristian.socmed.service.dto.LoginRequest;
import com.kristian.socmed.service.dto.RegisterRequest;

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
    private RoleRepository roleRepository;
    private MailService mailService;
    
    @Transactional
    public void signup(RegisterRequest registerRequest){
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setDate(Instant.now());
        user.setEnabled(false);
        
        Role role = roleRepository.findByName("USER").orElseThrow(()->new MyRuntimeException(("Role not found")));
        user.addRole(role);
        userRepository.save(user);
        
        String token = generateVerificationToken(user);
        
        mailService.sendMail(new VerificationEmail("Please Activate your Account",
                user.getEmail(), "Thank you for signing up to Spring Social Network, " +
                "please click on the below url to activate your account : " +
                appConfig.getUrl()+"/api/auth/activate/" + token));
    }
    
    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken vt = new VerificationToken(null, token, user);
        verificationTokenRepository.save(vt);
        return token;
    }
    
    @Transactional
    public AuthResponse login(LoginRequest loginRequest) {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            String token = jwtProvider.generateToken(authenticate);
            return  new AuthResponse(token, loginRequest.getUsername(),isAdmin());
    }
    private String isAdmin() {
        User user = getCurrentUser();
        for (Role role : user.getRoles()){
            if(role.getName().equals("ADMIN"))
                return "yes";
        }
        return "no";
    }

    @Transactional
    public User getCurrentUser(){
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> currentUser = userRepository.findByUsername(username);
        return currentUser.orElseThrow(()->new MyRuntimeException("Current user not found"));
    }

    public void activateAccount(String token) {
        VerificationToken verificationToken
                = verificationTokenRepository.findByToken(token)
                .orElseThrow(()->new MyRuntimeException("Token not found"));
        User user = verificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
    }
}
