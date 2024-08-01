/**
 * 
 */
package com.banks.erp.sa.uaa.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Rajib Kumer Ghosh
 *
 */

public class UserInfoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String userId;
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String newPassword;
	private String reEnterNewPassword;
	private String defaultBranchCode;
	private String email;
	private String employeeID;
	private Date userCreationDate;
	private Boolean passwordChangedYN;
	private Boolean userForcePasswordStrengthYN;
	private Boolean userLockStatus;
	private String remarks;
	private String fullName;
	private String statusId;
	private String createdBy;
	private Date creationDate;
	private String modifiedBy;
	private Date modificationDate;
	private String groupId;
	private String groupName;

	public UserInfoDTO() {
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

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getReEnterNewPassword() {
		return reEnterNewPassword;
	}

	public void setReEnterNewPassword(String reEnterNewPassword) {
		this.reEnterNewPassword = reEnterNewPassword;
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

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
