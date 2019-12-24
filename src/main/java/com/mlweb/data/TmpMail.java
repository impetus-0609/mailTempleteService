package com.mlweb.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TmpMail {

  authUsername(""), // impetus mail
  authPassword(""), // impetus pass
  ;

  private String value;

}
