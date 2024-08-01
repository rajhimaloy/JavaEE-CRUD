package com.banks.erp.sa.uaa.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.banks.erp.library.util.model.BaseEntity;
/**
 * @author Rajib Kumer Ghosh
 *
 */

@Entity
@Cacheable(value = true)
@Table(name = "SYS_USERINFO")
public class UserInfo extends BaseEntity {

	private static final long serialVersionUID = 5342069823479464585L;

	@Id
	@NotNull
	@Basic(optional = false)
	@Column(name = "USERID")
	private String userId;		

	@Basic(optional = false)
	@Column(name = "FIRSTNAME")
	private String firstName;		

	@Basic(optional = false)
	@Column(name = "LASTNAME")
	private String lastName;

	@Basic(optional = false)
	@Column(name = "USERNAME")
	private String userName;

	@NotNull
	@Size(max = 100)
	@Basic(optional = false)
	@Column(name = "PASSWORD")
	private String password;

	@Basic(optional = false)
	@Column(name = "DEFAULTBRANCHCODE")
	private String defaultBranchCode;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "EMPLOYEEID")
	private String employeeID;

	@Basic(optional = false)
	@Column(name = "USRCREATIONDATE")
	@Temporal(TemporalType.DATE)
	private Date userCreationDate;

	@Basic(optional = false)
	@Column(name = "PWDCHANGEDYN")
	private Boolean passwordChangedYN;

	@Basic(optional = false)
	@Column(name = "USRFORCEPWDSTRENGTHYN")
	private Boolean userForcePasswordStrengthYN;

	@Basic(optional = false)
	@Column(name = "USERLOCKSTATUS")
	private Boolean userLockStatus;

	@Column(name = "REMARKS")
	private String remarks;

	@Transient
	private String fullName;

	public UserInfo() {
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDefaultBranchCode() {
		return defaultBranchCode;
	}

	public void setDefaultBranchCode(String defaultBranchCode) {
		this.defaultBranchCode = defaultBranchCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	public Date getUserCreationDate() {
		return userCreationDate;
	}

	public void setUserCreationDate(Date userCreationDate) {
		this.userCreationDate = userCreationDate;
	}

	public Boolean getPasswordChangedYN() {
		return passwordChangedYN;
	}

	public void setPasswordChangedYN(Boolean passwordChangedYN) {
		this.passwordChangedYN = passwordChangedYN;
	}

	public Boolean getUserForcePasswordStrengthYN() {
		return userForcePasswordStrengthYN;
	}

	public void setUserForcePasswordStrengthYN(Boolean userForcePasswordStrengthYN) {
		this.userForcePasswordStrengthYN = userForcePasswordStrengthYN;
	}

	public Boolean getUserLockStatus() {
		return userLockStatus;
	}

	public void setUserLockStatus(Boolean userLockStatus) {
		this.userLockStatus = userLockStatus;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}	

}
