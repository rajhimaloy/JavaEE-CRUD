/**
 * 
 */
package com.banks.erp.sa.uaa.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.banks.erp.library.util.model.BaseEntity;

/**
 * @author Rajib Kumer Ghosh
 *
 */

@Entity
@Cacheable(value = true)
@Table(name = "SYS_USERWISEGROUPMAP")
@IdClass(UserWiseGroupMap.UserWiseGroupMapPK.class)
public class UserWiseGroupMap extends BaseEntity {
	
	private static final long serialVersionUID = 5342069823479464585L;

	public static class UserWiseGroupMapPK implements Serializable {

		private static final long serialVersionUID = 1L;
		
		private String userId;
		private String groupId;

		public UserWiseGroupMapPK() {
		}		

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getGroupId() {
			return groupId;
		}

		public void setGroupId(String groupId) {
			this.groupId = groupId;
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
	@Column(name = "GROUPID")
	private String groupId;
	

	@Basic(optional = false)
	@Column(name = "GROUPNAME")
	private String groupName;

	public UserWiseGroupMap() {
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
