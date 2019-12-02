package com.kuding.properties;

import java.util.Arrays;

import com.kuding.properties.enums.DingdingTextType;

public class DingDingExceptionNoticeProperty {

	/**
	 * 电话信息
	 */
	private String[] phoneNum;

	/**
	 * 钉钉机器人web钩子
	 */
	private String webHook;

	/**
	 * 钉钉通知文本类型
	 */
	private DingdingTextType textType = DingdingTextType.TEXT;

	/**
	 * @return the phoneNum
	 */
	public String[] getPhoneNum() {
		return phoneNum;
	}

	/**
	 * @param phoneNum the phoneNum to set
	 */
	public void setPhoneNum(String[] phoneNum) {
		this.phoneNum = phoneNum;
	}

	/**
	 * @return the webHook
	 */
	public String getWebHook() {
		return webHook;
	}

	/**
	 * @param webHook the webHook to set
	 */
	public void setWebHook(String webHook) {
		this.webHook = webHook;
	}

	public DingdingTextType getTextType() {
		return textType;
	}

	public void setTextType(DingdingTextType textType) {
		this.textType = textType;
	}

	@Override
	public String toString() {
		return "DingDingExceptionNoticeProperty [phoneNum=" + Arrays.toString(phoneNum) + ", webHook=" + webHook
				+ ", textType=" + textType + "]";
	}
}
