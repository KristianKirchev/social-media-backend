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
    
    @Transactional
//    public void signup(RegisterRequest registerRequest){
//        User user = new User();
//        user.setEmail(registerRequest.getEmail());
//        user.setUsername(registerRequest.getUsername());
//        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
//        user.setDateOfCreation(Instant.now());
//        user.setEnabled(false);
//        //Role role = roleRepository.findByName("USER").orElseThrow(()->new MyRuntimeException(("Role not found")));
//        //user.addRole(role);
//        userRepository.save(user);
//        String token = generateVerificationToken(user);
//
////        mailService.sendMail(new VerificationEmail("Please Activate your Account",
////                user.getEmail(), "Thank you for signing up to Spring Social Network, " +
////                "please click on the below url to activate your account : " +
////                appConfig.getUrl()+"/api/auth/activate/" + token));
//    }
    
    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken vt = new VerificationToken(null,token,user);
        verificationTokenRepository.save(vt);
        return token;
    }
}
