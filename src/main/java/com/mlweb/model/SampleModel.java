package com.mlweb.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SampleModel {

  private String title;
  private LocalDateTime entryDate;

}
