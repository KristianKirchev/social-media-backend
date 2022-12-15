package com.kristian.socmed.repository;

import org.springframework.stereotype.Repository;

import com.kristian.socmed.model.entity.MyEntity;

@Repository
public interface MyRepository {
	public default void deleteByParent(MyEntity parent){}
}
