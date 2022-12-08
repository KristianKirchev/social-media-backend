package com.kristian.socmed.service.mapper;

import com.kristian.socmed.model.entity.MyEntity;
import com.kristian.socmed.service.dto.Dto;

public interface GenericMapper<D extends Dto, E extends MyEntity> {

    E toEntity(D dto);

    D toDto(E entity);
}