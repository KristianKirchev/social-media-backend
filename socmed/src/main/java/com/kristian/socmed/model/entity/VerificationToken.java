package com.kristian.socmed.model.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class VerificationToken {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String token;
    @OneToOne(fetch = FetchType.LAZY)
    User user;
}
