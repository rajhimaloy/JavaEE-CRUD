package com.banks.erp.sa.uaa.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author Rajib Kumer Ghosh
 *
 */

@Entity
@Table(name = "SYS_USERS")
/*@NamedQueries({ @NamedQuery(name = "UserSetup.findAll", query = "SELECT u FROM UserSetup u"),
		@NamedQuery(name = "UserSetup.findByUserName", query = "SELECT u FROM UserSetup u WHERE u.SYSUSERNAME = :userName") })*/
public class UserSetup implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	private String remark;

	@Column(name = "SYSEMPID")
	private String employeeID;
	
	@Column(name = "SYSCREATEDATE")
	private Date createDate;
	
	@Version
	@Basic(optional = false)
	@NotNull
	@Column(name = "SYSVERSION")
	private Short version;
	

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((defaultBranchCode == null) ? 0 : defaultBranchCode.hashCode());
		result = prime * result + ((employeeID == null) ? 0 : employeeID.hashCode());
		result = prime * result + ((isActive == null) ? 0 : isActive.hashCode());
		result = prime * result + ((isPasswordChanged == null) ? 0 : isPasswordChanged.hashCode());
		result = prime * result + ((isPasswordStrength == null) ? 0 : isPasswordStrength.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((roleID == null) ? 0 : roleID.hashCode());
		result = prime * result + ((userFullName == null) ? 0 : userFullName.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		UserSetup other = (UserSetup) obj;
		if (createDate == null) {
			if (other.createDate != null) {
				return false;
			}
		} else if (!createDate.equals(other.createDate)) {
			return false;
		}
		if (defaultBranchCode == null) {
			if (other.defaultBranchCode != null) {
				return false;
			}
		} else if (!defaultBranchCode.equals(other.defaultBranchCode)) {
			return false;
		}
		if (employeeID == null) {
			if (other.employeeID != null) {
				return false;
			}
		} else if (!employeeID.equals(other.employeeID)) {
			return false;
		}
		if (isActive == null) {
			if (other.isActive != null) {
				return false;
			}
		} else if (!isActive.equals(other.isActive)) {
			return false;
		}
		if (isPasswordChanged == null) {
			if (other.isPasswordChanged != null) {
				return false;
			}
		} else if (!isPasswordChanged.equals(other.isPasswordChanged)) {
			return false;
		}
		if (isPasswordStrength == null) {
			if (other.isPasswordStrength != null) {
				return false;
			}
		} else if (!isPasswordStrength.equals(other.isPasswordStrength)) {
			return false;
		}
		if (password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!password.equals(other.password)) {
			return false;
		}
		if (roleID == null) {
			if (other.roleID != null) {
				return false;
			}
		} else if (!roleID.equals(other.roleID)) {
			return false;
		}
		if (userFullName == null) {
			if (other.userFullName != null) {
				return false;
			}
		} else if (!userFullName.equals(other.userFullName)) {
			return false;
		}
		if (userName == null) {
			if (other.userName != null) {
				return false;
			}
		} else if (!userName.equals(other.userName)) {
			return false;
		}
		if (remark == null) {
			if (other.remark != null) {
				return false;
			}
		} else if (!remark.equals(other.remark)) {
			return false;
		}
		if (version == null) {
			if (other.version != null) {
				return false;
			}
		} else if (!version.equals(other.version)) {
			return false;
		}
		return true;
	}

	

	
	
	

}
