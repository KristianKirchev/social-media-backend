package com.kristian.socmed.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificationEmail {
  private String subject;
  private String receiver;
  private String content;
}
