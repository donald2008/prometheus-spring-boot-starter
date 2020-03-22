package com.kuding.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.kuding.properties.enums.EmailTextType;

@ConfigurationProperties(prefix = "exceptionnotice.email")
public class EmailExceptionNoticeProperty {

	/**
	 * 收件人
	 */
	private String[] to;

	/**
	 * 抄送
	 */
	private String[] cc;

	/**
	 * 密抄送
	 */
	private String[] bcc;

	/**
	 * 邮件的通知文本类型
	 */
	private EmailTextType emailTextType = EmailTextType.TEXT;

	/**
	 * @return the to
	 */
	public String[] getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(String[] to) {
		this.to = to;
	}

	/**
	 * @return the cc
	 */
	public String[] getCc() {
		return cc;
	}

	/**
	 * @param cc the cc to set
	 */
	public void setCc(String[] cc) {
		this.cc = cc;
	}

	/**
	 * @return the bcc
	 */
	public String[] getBcc() {
		return bcc;
	}

	/**
	 * @param bcc the bcc to set
	 */
	public void setBcc(String[] bcc) {
		this.bcc = bcc;
	}

	public EmailTextType getEmailTextType() {
		return emailTextType;
	}

	public void setEmailTextType(EmailTextType emailTextType) {
		this.emailTextType = emailTextType;
	}

}
