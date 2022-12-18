package com.kristian.socmed.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
	
    private String name;
    private String details;
}
