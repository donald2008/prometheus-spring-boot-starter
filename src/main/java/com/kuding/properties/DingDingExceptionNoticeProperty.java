package com.kuding.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.kuding.properties.enums.DingdingTextType;

@ConfigurationProperties(prefix = "exceptionnotice.dingding")
public class DingDingExceptionNoticeProperty {

	/**
	 * 电话信息
	 */
	private String[] phoneNum;

	/**
	 * 钉钉机器人web钩子
	 * 
	 * @deprecated 已废弃，用accessToken替代，不再是整个网址
	 */
	@Deprecated
	private String webHook;

	/**
	 * 钉钉机器人的accessToken
	 */
	private String accessToken;

	/**
	 * 是否开启验签
	 */
	private boolean enableSignatureCheck;

	/**
	 * 验签秘钥
	 */
	private String signSecret;

	/**
	 * 钉钉通知文本类型
	 */
	private DingdingTextType dingdingTextType = DingdingTextType.TEXT;

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

//
//	/**
//	 * @return the webHook
//	 */
	public String getWebHook() {
		return webHook;
	}

//	/**
//	 * @param webHook the webHook to set
//	 */
//	public void setWebHook(String webHook) {
//		this.webHook = webHook;
//	}

	public boolean isEnableSignatureCheck() {
		return enableSignatureCheck;
	}

	public void setEnableSignatureCheck(boolean enableSignatureCheck) {
		this.enableSignatureCheck = enableSignatureCheck;
	}

	public String getSignSecret() {
		return signSecret;
	}

	public void setSignSecret(String signSecret) {
		this.signSecret = signSecret;
	}

	public DingdingTextType getDingdingTextType() {
		return dingdingTextType;
	}

	public void setDingdingTextType(DingdingTextType dingdingTextType) {
		this.dingdingTextType = dingdingTextType;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
