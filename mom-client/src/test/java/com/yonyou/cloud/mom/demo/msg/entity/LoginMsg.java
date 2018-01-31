package com.yonyou.cloud.mom.demo.msg.entity;

public class LoginMsg {
	
	private String  loginName;
	
	private Long loginTime;

 

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Long loginTime) {
		this.loginTime = loginTime;
	}
}
