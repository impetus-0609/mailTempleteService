package com.mlweb.form;

import com.mlweb.data.MailTemplate;

import lombok.Data;

@Data
public class MailInputForm {

  private MailTemplate templeteCd;

  private String fromAd;

  private String toAd;

  private String ccAd;

  private String title;

  private String content;

}
