package com.kristian.socmed.service;

import java.util.List;
import com.kristian.socmed.repository.MyRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MyRepositoryRegistry {
  private final List<MyRepository> repositories;
}
