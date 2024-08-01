/**
 * 
 */
package com.banks.erp.sa.uaa.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.banks.erp.library.util.model.BaseEntity;

/**
 * @author Rajib Kumer Ghosh
 *
 */

@Entity
@Cacheable(value = true)
@XmlRootElement(name = "userAdditionalBranchMap")
@Table(name = "SYS_USERADDITIONALBRANCHMAP")
@IdClass(UserAdditionalBranchMapping.UserAdditionalBranchMappingPK.class)
public class UserAdditionalBranchMapping extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	public static class UserAdditionalBranchMappingPK implements Serializable {

		private static final long serialVersionUID = 1L;
		
		private String userId;
		private String additionalBranchCode;

		public UserAdditionalBranchMappingPK() {
		}		

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getAdditionalBranchCode() {
			return additionalBranchCode;
		}

		public void setAdditionalBranchCode(String additionalBranchCode) {
			this.additionalBranchCode = additionalBranchCode;
		}

		@Override
		public boolean equals(final Object obj) {
			return super.equals(obj);
		}

		@Override
		public int hashCode() {
			return super.hashCode();
		}

	}

	@Id
	@NotNull
	@Basic(optional = false)
	@Column(name = "USERID")
	private String userId;
	
	@Id
	@NotNull
	@Basic(optional = false)
	@Column(name = "BRANCHCODE")
	private String additionalBranchCode;
	
	@Basic(optional = false)
	@Column(name = "ADDITIONALBRANCHMAPINGDATE")
	@Temporal(TemporalType.DATE)
	private Date additionalBranchMappingDate;

	public UserAdditionalBranchMapping() {
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAdditionalBranchCode() {
		return additionalBranchCode;
	}

	public void setAdditionalBranchCode(String additionalBranchCode) {
		this.additionalBranchCode = additionalBranchCode;
	}

	public Date getAdditionalBranchMappingDate() {
		return additionalBranchMappingDate;
	}

	public void setAdditionalBranchMappingDate(Date additionalBranchMappingDate) {
		this.additionalBranchMappingDate = additionalBranchMappingDate;
	}

}
