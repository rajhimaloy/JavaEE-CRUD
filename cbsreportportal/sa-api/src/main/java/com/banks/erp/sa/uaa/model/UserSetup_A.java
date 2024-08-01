/**
 * 
 */
package com.banks.erp.sa.uaa.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

/**
 * @author Rajib Kumer Ghosh
 *
 */

@Entity
@Table(name = "SYS_USERS_A")
public class UserSetup_A implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "SYSAUDITSERIALNO")
	private Integer auditSerialNo;
	
	@Basic(optional = false)
	@Column(name = "SYSUSERNAME")
	private String userName;

	@Basic(optional = false)
	@Column(name = "SYSUSERFULLNAME")
	private String userFullName;

	@Basic(optional = false)
	@Column(name = "SYSPASSWORD")
	private String password;
	
	@Column(name = "SYSISPWDSTRENGTH")
	private Boolean isPasswordStrength;
	
	@Column(name = "SYSISPWDCHANGED")
	private Boolean isPasswordChanged;
	
	@Column(name = "SYSROLEID")
	private String roleID;
	
	@Column(name = "SYSISACTIVE")
	private Boolean isActive;
	
	@Column(name = "SYSDEFAULTBRANCHCODE")
	private String defaultBranchCode;
	
	@Column(name = "SYSREMARK")
	private String userRemark;

	@Column(name = "SYSEMPID")
	private String employeeID;
	
	@Column(name = "SYSCREATEDATE")
	private Date createDate;
	
	@Version
	@Basic(optional = false)
	@NotNull
	@Column(name = "SYSVERSION")
	private Short version;
	
	@NotNull
	private Character operationType;

	public Integer getAuditSerialNo() {
		return auditSerialNo;
	}

	public void setAuditSerialNo(Integer auditSerialNo) {
		this.auditSerialNo = auditSerialNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsPasswordStrength() {
		return isPasswordStrength;
	}

	public void setIsPasswordStrength(Boolean isPasswordStrength) {
		this.isPasswordStrength = isPasswordStrength;
	}

	public Boolean getIsPasswordChanged() {
		return isPasswordChanged;
	}

	public void setIsPasswordChanged(Boolean isPasswordChanged) {
		this.isPasswordChanged = isPasswordChanged;
	}

	public String getRoleID() {
		return roleID;
	}

	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getDefaultBranchCode() {
		return defaultBranchCode;
	}

	public void setDefaultBranchCode(String defaultBranchCode) {
		this.defaultBranchCode = defaultBranchCode;
	}

	public String getUserRemark() {
		return userRemark;
	}

	public void setUserRemark(String userRemark) {
		this.userRemark = userRemark;
	}

	public String getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Short getVersion() {
		return version;
	}

	public void setVersion(Short version) {
		this.version = version;
	}

	public Character getOperationType() {
		return operationType;
	}

	public void setOperationType(Character operationType) {
		this.operationType = operationType;
	}
	
	

}
