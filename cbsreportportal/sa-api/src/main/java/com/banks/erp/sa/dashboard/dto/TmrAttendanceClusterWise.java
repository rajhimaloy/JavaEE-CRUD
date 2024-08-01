/**
 * 
 */
package com.banks.erp.sa.dashboard.dto;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Rajib Kumer Ghosh
 *
 */
@Entity
@Cacheable(value = true)
public class TmrAttendanceClusterWise implements Serializable {
	private static final long serialVersionUID = 6064327482776204887L;
	
	@Id
	private String groupName;
	private Integer tmrTotal;
	private Integer tmrPresence;
	
	public TmrAttendanceClusterWise() {
	}

	public TmrAttendanceClusterWise(String groupName, Integer tmrTotal, Integer tmrPresence) {
		this.groupName = groupName;
		this.tmrTotal = tmrTotal;
		this.tmrPresence = tmrPresence;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getTmrTotal() {
		return tmrTotal;
	}

	public void setTmrTotal(Integer tmrTotal) {
		this.tmrTotal = tmrTotal;
	}

	public Integer getTmrPresence() {
		return tmrPresence;
	}

	public void setTmrPresence(Integer tmrPresence) {
		this.tmrPresence = tmrPresence;
	}

}
