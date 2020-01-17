package com.mlweb.model;

import java.util.Properties;

import com.mlweb.data.MailTemplate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MailModel {

  private MailTemplate templeteCd;

  private String fromAd;

  private String toAd;

  private String ccAd;

  private String title;

  private String content;

  Properties props;

  private String hash;
}
