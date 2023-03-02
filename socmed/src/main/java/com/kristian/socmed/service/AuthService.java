package com.kristian.socmed.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kristian.socmed.config.AppProperties;
import com.kristian.socmed.model.entity.Role;
import com.kristian.socmed.model.entity.User;
import com.kristian.socmed.model.entity.VerificationEmail;
import com.kristian.socmed.model.entity.VerificationToken;
import com.kristian.socmed.provider.JwtProvider;
import com.kristian.socmed.service.dto.AuthResponse;
import com.kristian.socmed.service.dto.LoginRequest;
import com.kristian.socmed.service.dto.RegisterRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;
	private final AppProperties appProperties;
	private final MailService mailService;
	private final AuthTransactionService authTransactionService;

	public void signup(RegisterRequest registerRequest) {
		User user = createUser(registerRequest);

		authTransactionService.signup(user);

		String token = generateVerificationToken(user);

		mailService.sendMail(new VerificationEmail("Please Activate your Account", user.getEmail(),
				"Thank you for signing up to Smilenetix, " + "please click on the below url to activate your account : "
						+ appProperties.getUrl() + "/api/auth/activate/" + token));
	}

	private User createUser(RegisterRequest registerRequest) {
		User user = new User();
		user.setEmail(registerRequest.getEmail());
		user.setUsername(registerRequest.getUsername());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setDate(Instant.now());
		user.setEnabled(false);

		return user;
	}

	private String generateVerificationToken(User user) {
		String token = UUID.randomUUID().toString();
		VerificationToken vt = new VerificationToken(null, token, user);

		authTransactionService.saveVerificationToken(vt);

		return token;
	}

	public AuthResponse login(LoginRequest loginRequest) {
		Authentication authenticate = authTransactionService.login(loginRequest.getUsername(),
				loginRequest.getPassword());

		SecurityContextHolder.getContext().setAuthentication(authenticate);
		String token = jwtProvider.generateToken(authenticate);
		
		return new AuthResponse(token, loginRequest.getUsername(), isAdmin());
	}

	private String isAdmin() {
		User user = getCurrentUser();
		for (Role role : user.getRoles()) {
			if (role.getName().equals("ADMIN"))
				return "yes";
		}
		return "no";
	}

	public User getCurrentUser() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		return authTransactionService.getUser(username);
	}
}
