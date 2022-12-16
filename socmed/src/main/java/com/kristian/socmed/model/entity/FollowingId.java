package com.kristian.socmed.model.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FollowingId implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long following;
    private Long followed;
}
