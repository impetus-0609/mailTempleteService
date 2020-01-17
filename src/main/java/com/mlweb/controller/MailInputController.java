package com.mlweb.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mlweb.form.MailInputForm;
import com.mlweb.model.MailModel;
import com.mlweb.service.MailSenderService;

@Controller
@RequestMapping("/mail")
public class MailInputController {

  @Autowired
  MailInputHelper mailInputHelper;

  @Autowired
  MailSenderService mailSenderService;

  @Autowired
  StringRedisTemplate redisTemplate;

  @RequestMapping("/input")
  public ModelAndView input(MailInputForm form, ModelAndView mv) {
    MailModel mailModel = mailInputHelper.mailModelMapper(form);
    // FIXME そのうち入力チェック実装する
    // FIXME FROMアドレスはリクエストから取得してはいけない。
    mv.addObject("mailModel", mailModel);
    mv.setViewName("mail/input");
    return mv;
  }

  @RequestMapping("/confirm")
  public ModelAndView confirm(@ModelAttribute MailInputForm form, ModelAndView mv){
    MailModel mailModel = mailInputHelper.mailModelMapper(form);
    mailModel.setHash(mailInputHelper.registerMailModel(mailModel));
    mv.setViewName("mail/confirm");
    mv.addObject("mailModel", mailModel);
    return mv;
  }

  @PostMapping("/complete")
  public ModelAndView complete(@ModelAttribute MailInputForm form, ModelAndView mv){
    MailModel mailModel = mailInputHelper
        .convertJsonToMailModel(redisTemplate.opsForValue().get(form.getHash()));
    if(Objects.nonNull(mailModel) && mailSenderService.sendMail(mailModel)) {
      mv.setViewName("redirect:/mail/complete_view");
    } else {
      mv.setViewName("redirect:/mail/input");
    }
    return mv;
  }

  @GetMapping("/complete_view")
  public ModelAndView completeView(ModelAndView mv){
    mv.setViewName("mail/complete");
    return mv;
  }

}
