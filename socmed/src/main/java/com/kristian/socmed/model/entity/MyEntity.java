package com.kristian.socmed.model.entity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.kristian.socmed.repository.MyRepository;

public interface MyEntity {
	public default List<MyRepository> returnChildRepositories(@Autowired ApplicationContext context){
        throw new UnsupportedOperationException();
    }
}
