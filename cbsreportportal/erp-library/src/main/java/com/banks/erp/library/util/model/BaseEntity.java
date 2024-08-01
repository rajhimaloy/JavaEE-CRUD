package com.banks.erp.library.util.model;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * @author Rajib Kumer Ghosh
 *
 */

@MappedSuperclass
@SuppressWarnings("serial")
public class BaseEntity implements Serializable {

	@NotNull
	@Basic(optional = false)
	@Column(name = "STATUSID")
	private String statusId;

	@Basic(optional = false)
	@Column(name = "CREATIONDATE")
	@Temporal(TemporalType.DATE)
	private Date creationDate;

	@Column(name = "MODIFICATIONDATE")
	@Temporal(TemporalType.DATE)
	private Date modificationDate;

	@Column(name = "CREATEDBY")
	private String createdBy;

	@Column(name = "MODIFIEDBY")
	private String modifiedBy;

	@PrePersist
	private void persist() {
		creationDate = currentDate();
	}

	@PreUpdate
	@PreRemove
	private void update() {
		modificationDate = currentDate();
	}

	private Date currentDate() {
		final ZoneId zoneId = ZoneId.of("Asia/Almaty");
		final LocalDateTime localDateTime = LocalDateTime.now();
		final ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
		final Instant instant = zonedDateTime.toInstant();
		return Date.from(instant);
	}

	public BaseEntity() {
	}

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}
