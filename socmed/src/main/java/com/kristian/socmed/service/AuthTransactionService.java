package com.kristian.socmed.service;

import java.util.Optional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kristian.socmed.exception.MyRuntimeException;
import com.kristian.socmed.model.entity.Role;
import com.kristian.socmed.model.entity.User;
import com.kristian.socmed.model.entity.VerificationToken;
import com.kristian.socmed.repository.RoleRepository;
import com.kristian.socmed.repository.UserRepository;
import com.kristian.socmed.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthTransactionService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final VerificationTokenRepository verificationTokenRepository;
	private final AuthenticationManager authenticationManager;

	public void signup(User user) {
		Role role = roleRepository.findByName("USER").orElseThrow(() -> new MyRuntimeException(("Role not found")));
		user.addRole(role);
		userRepository.save(user);
	}

	public void saveVerificationToken(VerificationToken vt) {
		verificationTokenRepository.save(vt);
	}

	@Transactional(readOnly = true)
	public Authentication login(String username, String password) {
		return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}
	
	@Transactional(readOnly = true)
	public User getUser(String username) {
		Optional<User> currentUser = userRepository.findByUsername(username);
		return currentUser.orElseThrow(() -> new MyRuntimeException("Current user not found"));
	}
	
	public void activateAccount(String token) {
    VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
				.orElseThrow(() -> new MyRuntimeException("Token not found"));
		User user = verificationToken.getUser();
		user.setEnabled(true);
		userRepository.save(user);
	}
}
