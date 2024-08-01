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
public class UniqueUddoktaVisitCount implements Serializable {
	private static final long serialVersionUID = 6064327482776204887L;
	
	@Id
	private String createDate;
	private Integer uniqueUddoktaVisitedLast7Days;
	
	public UniqueUddoktaVisitCount() {
	}

	public UniqueUddoktaVisitCount(String createDate, Integer uniqueUddoktaVisitedLast7Days) {
		this.createDate = createDate;
		this.uniqueUddoktaVisitedLast7Days = uniqueUddoktaVisitedLast7Days;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Integer getUniqueUddoktaVisitedLast7Days() {
		return uniqueUddoktaVisitedLast7Days;
	}

	public void setUniqueUddoktaVisitedLast7Days(Integer uniqueUddoktaVisitedLast7Days) {
		this.uniqueUddoktaVisitedLast7Days = uniqueUddoktaVisitedLast7Days;
	}	

}
