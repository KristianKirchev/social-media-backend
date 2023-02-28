package com.kristian.socmed.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kristian.socmed.model.entity.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
	Optional<VerificationToken> findByToken(String token);
}
