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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.banks.erp.library.util.model.BaseEntity;

/**
 * @author Rajib Kumer Ghosh
 *
 */

@Entity
@Cacheable(value = true)
@XmlRootElement(name = "userGroup")
@Table(name = "SYS_USERGROUP")
public class UserGroup extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@Column(name = "GROUPID")
	private String groupId;

	@NotNull
	@Size(max = 50)
	@Basic(optional = false)
	@Column(name = "GROUPNAME")
	private String groupName;

	@Basic(optional = false)
	@Column(name = "GROUPCREATIONDATE")
	@Temporal(TemporalType.DATE)
	private Date groupCreationDate;

	@Column(name = "VERSION")
	private Integer versionNo;

	public UserGroup() {
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

	public Date getGroupCreationDate() {
		return groupCreationDate;
	}

	public void setGroupCreationDate(Date groupCreationDate) {
		this.groupCreationDate = groupCreationDate;
	}

	public Integer getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Integer versionNo) {
		this.versionNo = versionNo;
	}	
}
