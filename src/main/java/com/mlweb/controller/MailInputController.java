package com.mlweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mlweb.form.MailInputForm;
import com.mlweb.model.MailModel;

@Controller
@RequestMapping("/mail")
public class MailInputController {

	@Autowired
	MailInputHelper mailInputHelper;

	@RequestMapping("/input")
	public ModelAndView input(MailInputForm form, ModelAndView mv) {
		MailModel mailModel = mailInputHelper.mailModelMapper(form);
		mv.addObject("mailModel", mailModel);
		mv.addObject("msg", "hellow World!");
		mv.setViewName("mail/input");
		return mv;
	}

	@RequestMapping("/confirm")
	public ModelAndView confirm(@ModelAttribute MailInputForm form, ModelAndView mv){
		MailModel mailModel = mailInputHelper.mailModelMapper(form);
		mv.addObject("mailModel", mailModel);
		mv.setViewName("mail/confirm");
		return mv;
	}

	@PostMapping("/complete")
	public ModelAndView complete(@ModelAttribute MailInputForm form, ModelAndView mv){
		MailModel mailModel = mailInputHelper.mailModelMapper(form);
		mv.addObject("mailModel", mailModel);
		mv.setViewName("redirect:/mail/complete_view");
		return mv;
	}

	@GetMapping("/complete_view")
	public ModelAndView completeView(ModelAndView mv){
		mv.setViewName("mail/complete");
		return mv;
	}

}
