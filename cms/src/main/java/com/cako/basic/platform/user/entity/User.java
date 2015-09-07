package com.cako.basic.platform.user.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.cako.basic.platform.department.entity.Department;
import com.cako.basic.platform.role.entity.Role;
import com.orm.commons.utils.IdEntity;

@Entity
@Table(name = "system_user")
public class User extends IdEntity {
	private String loginName;
	private String password;
	private String realName;
	private String address;
	private String phone;
	private String QQ;
	private Boolean isDelete = Boolean.FALSE;
	private String sex;
	private String email;
	public User.Status status = Status.INIT;

	/**
	 * 角色
	 */
	private List<Role> roles = new ArrayList<Role>();

	/**
	 * 部门
	 */
	private List<Department> departments = new ArrayList<Department>();

	public User() {

	}

	public User(String loginName, String password) {
		this.loginName = loginName;
		this.password = password;
	}

	public Status getStatus() {
		return status;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginName() {
		return loginName;
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

	public void setQQ(String qQ) {
		QQ = qQ;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name = "system_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	@Fetch(FetchMode.SUBSELECT)
	public List<Role> getRoles() {
		return roles;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name = "system_user_dept", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "dept_id"))
	public List<Department> getDepartments() {
		return departments;
	}

	public interface ParameterUtils {
		public final static String MAN = "man";
		public final static String WOMEN = "women";
	}

	public static enum Status {
		LOCKED, INIT, NORMAL;

		private Status() {
		}
	}
}
