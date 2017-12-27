package com.yonyou.cloud.mom.demo.msg.entity;

public class LoginMsg {
	
	private String  LoginName;
	
	private Long loginTime;

	public String getLoginName() {
		return LoginName;
	}

	public void setLoginName(String loginName) {
		LoginName = loginName;
	}

	public Long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Long loginTime) {
		this.loginTime = loginTime;
	}
}
