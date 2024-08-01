/**
 * 
 */
package com.banks.erp.sa.dashboard.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Rajib Kumer Ghosh
 *
 */
@Entity
@Cacheable(value = true)
public class TmrAttendance implements Serializable {
	private static final long serialVersionUID = 6064327482776204887L;
	
	@Id
	private String createDate;
	private Integer tmrAttendanceLast7Days;
	
	public TmrAttendance() {
	}

	public TmrAttendance(String createDate, Integer tmrAttendanceLast7Days) {
		this.createDate = createDate;
		this.tmrAttendanceLast7Days = tmrAttendanceLast7Days;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Integer getTmrAttendanceLast7Days() {
		return tmrAttendanceLast7Days;
	}

	public void setTmrAttendanceLast7Days(Integer tmrAttendanceLast7Days) {
		this.tmrAttendanceLast7Days = tmrAttendanceLast7Days;
	}

}
