package com.mlweb.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlweb.form.MailInputForm;
import com.mlweb.model.MailModel;
import com.mlweb.service.MailSenderService;


@Controller
public class MailInputHelper {


  @Autowired
  MailSenderService mailSenderService;

  @Autowired
  StringRedisTemplate redisTemplate;

  @Autowired
  ObjectMapper objectMapper;

  /**
   * MailInputFormをMailModelにマッピングする。<br/>
   *
   * @param MailInputForm
   * @return MailModel
   */
  public MailModel mailModelMapper(MailInputForm form) {

    // FIXME org.dozer.Mapper導入したい。
    if (Objects.isNull(form)) {
      return MailModel.builder().build();
    }
    return MailModel.builder()
        .fromAd(form.getFromAd())
        .toAd(form.getToAd())
        .ccAd(form.getCcAd())
        .title(form.getTitle())
        .content(form.getContent())
        .build();
  }

  public boolean identifyAddress(String checkAd , String sAd) {
    if (Objects.isNull(checkAd) || Objects.isNull(sAd)) {
      return false;
    }
    if (sAd.equals(checkAd)) {
      return true;
    }
    return false;
  }

  public String registerMailModel(MailModel mailModel) {
    String hash = mailSenderService.generateHash();
    try {
      redisTemplate.opsForValue().set(hash, convertMailModelToJson(mailModel));
    } catch (JsonProcessingException e) {
      System.out.println("JSONオブジェクトへの変換にとちりました");
      return null;
    }
    return hash;
  }

  public String convertMailModelToJson(MailModel mailModel) throws JsonProcessingException {
    if (Objects.isNull(mailModel)) {
      return null;
    }
    return objectMapper.writeValueAsString(mailModel);
  }

  public MailModel convertJsonToMailModel(String json) {
    if (Objects.isNull(json) || json.isEmpty()) {
      return null;
    }
    try {
      return objectMapper.readValue(json, MailModel.class);
    }catch(JsonProcessingException ex) {
      return null;
    }
  }
}
