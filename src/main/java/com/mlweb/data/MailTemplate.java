package com.mlweb.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MailTemplate {

	KINTAI_RENRAKU_SIYO("01","私用のため"),
	KINTAI_RENRAKU_DENSYATIEN("02","電車遅延による遅刻"),
	KINTAI_RENRAKU_TAICHOFURYO("03","体調不良(当日休)"),
	;
	private final String value;

	private final String text;
}
