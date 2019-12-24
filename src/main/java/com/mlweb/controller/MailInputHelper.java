package com.mlweb.controller;

import java.util.Objects;

import org.springframework.stereotype.Controller;

import com.mlweb.form.MailInputForm;
import com.mlweb.model.MailModel;


@Controller
public class MailInputHelper {

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
}
