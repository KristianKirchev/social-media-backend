package com.kristian.socmed.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kristian.socmed.model.entity.Role;
import com.kristian.socmed.service.RoleService;
import lombok.AllArgsConstructor;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping({"/api/role", "/api/role/"})
@AllArgsConstructor
public class RoleController {

  private RoleService roleService;

  @PostMapping
  public ResponseEntity addRole(@RequestBody Role role) {
    roleService.addRole(role);
    return new ResponseEntity(HttpStatus.OK);
  }
}
