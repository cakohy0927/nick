package com.cako.basic.platform.user.dto;

import com.cako.basic.platform.user.entity.User;

/**
 * Created by Administrator on 2014/9/20.
 */
public class UserTree {
	private String id;
	private String loginName;
	private String password;
	private String realName;
	private String address;
	private String phone;
	private String QQ;
	private String sex;
	private String email;

	public UserTree(User user) {
		this.id = user.getId();
		this.loginName = user.getLoginName();
		this.password = user.getPassword();
		this.realName = user.getRealName();
		this.address = user.getAddress();
		this.phone = user.getPhone();
		this.email = user.getEmail();
		this.QQ = user.getQQ();
		this.sex = user.getSex();
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getQQ() {
		return QQ;
	}

	public void setQQ(String QQ) {
		this.QQ = QQ;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
}
