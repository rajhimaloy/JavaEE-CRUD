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
@Table(name = "SYS_GROUPWISEACCESSPERMISSION")
@IdClass(GroupWiseAccessPermission.GroupWiseAccessPermissionPK.class)
public class GroupWiseAccessPermission extends BaseEntity {

	private static final long serialVersionUID = -8058681456816330019L;

	public static class GroupWiseAccessPermissionPK implements Serializable {

		private static final long serialVersionUID = -2118900844409356388L;

		private String groupID;
		private Integer screenID;

		public GroupWiseAccessPermissionPK() {
		}

		public String getGroupID() {
			return groupID;
		}

		public void setGroupID(String groupID) {
			this.groupID = groupID;
		}

		public Integer getScreenID() {
			return screenID;
		}

		public void setScreenID(Integer screenID) {
			this.screenID = screenID;
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
	@Column(name = "GROUPID")
	private String groupID;

	@Id
	@NotNull
	@Basic(optional = false)
	@Column(name = "SCREENID")
	private Integer screenID;	

	@Column(name = "SCREENNAME")
	private String screenName;

	@NotNull
	@Basic(optional = false)
	@Column(name = "hasviewpermission")
	private Boolean hasViewPermission;

	@Basic(optional = false)
	@Column(name = "HASSAVEPERMISSION")
	private Boolean hasSavePermission;

	@Basic(optional = false)
	@Column(name = "HASUPDATEPERMISSION")
	private Boolean hasUpdatePermission;

	@Basic(optional = false)
	@Column(name = "HASDELETEPERMISSION")
	private Boolean hasDeletePermission;

	public GroupWiseAccessPermission() {
	}

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public Integer getScreenID() {
		return screenID;
	}

	public void setScreenID(Integer screenID) {
		this.screenID = screenID;
	}	

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public Boolean getHasViewPermission() {
		return hasViewPermission;
	}

	public void setHasViewPermission(Boolean hasViewPermission) {
		this.hasViewPermission = hasViewPermission;
	}

	public Boolean getHasSavePermission() {
		return hasSavePermission;
	}

	public void setHasSavePermission(Boolean hasSavePermission) {
		this.hasSavePermission = hasSavePermission;
	}

	public Boolean getHasUpdatePermission() {
		return hasUpdatePermission;
	}

	public void setHasUpdatePermission(Boolean hasUpdatePermission) {
		this.hasUpdatePermission = hasUpdatePermission;
	}

	public Boolean getHasDeletePermission() {
		return hasDeletePermission;
	}

	public void setHasDeletePermission(Boolean hasDeletePermission) {
		this.hasDeletePermission = hasDeletePermission;
	}

}
