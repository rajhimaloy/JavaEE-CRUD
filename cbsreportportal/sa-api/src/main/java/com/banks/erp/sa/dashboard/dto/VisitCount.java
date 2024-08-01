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
public class VisitCount implements Serializable {
	private static final long serialVersionUID = 6064327482776204887L;
	
	@Id
	private Integer totalUddokta;
	private Integer totalUddoktaVisitedMTD;
	private Integer uniqueUddoktaVisitedMTD;
	private Integer lastMonthTotalUddoktaVisited;
	private Integer lastMonthUniqueUddoktaVisited;
	private Integer last2ndMonthUniqueUddoktaVisited;
	private Integer last3rdMonthUniqueUddoktaVisited;
	
	public VisitCount() {
	}

	public VisitCount(Integer totalUddokta, Integer totalUddoktaVisitedMTD, Integer uniqueUddoktaVisitedMTD,
			Integer lastMonthTotalUddoktaVisited, Integer lastMonthUniqueUddoktaVisited,
			Integer last2ndMonthUniqueUddoktaVisited, Integer last3rdMonthUniqueUddoktaVisited) {
		this.totalUddokta = totalUddokta;
		this.totalUddoktaVisitedMTD = totalUddoktaVisitedMTD;
		this.uniqueUddoktaVisitedMTD = uniqueUddoktaVisitedMTD;
		this.lastMonthTotalUddoktaVisited = lastMonthTotalUddoktaVisited;
		this.lastMonthUniqueUddoktaVisited = lastMonthUniqueUddoktaVisited;
		this.last2ndMonthUniqueUddoktaVisited = last2ndMonthUniqueUddoktaVisited;
		this.last3rdMonthUniqueUddoktaVisited = last3rdMonthUniqueUddoktaVisited;
	}

	public Integer getTotalUddokta() {
		return totalUddokta;
	}

	public void setTotalUddokta(Integer totalUddokta) {
		this.totalUddokta = totalUddokta;
	}

	public Integer getTotalUddoktaVisitedMTD() {
		return totalUddoktaVisitedMTD;
	}

	public void setTotalUddoktaVisitedMTD(Integer totalUddoktaVisitedMTD) {
		this.totalUddoktaVisitedMTD = totalUddoktaVisitedMTD;
	}

	public Integer getUniqueUddoktaVisitedMTD() {
		return uniqueUddoktaVisitedMTD;
	}

	public void setUniqueUddoktaVisitedMTD(Integer uniqueUddoktaVisitedMTD) {
		this.uniqueUddoktaVisitedMTD = uniqueUddoktaVisitedMTD;
	}

	public Integer getLastMonthTotalUddoktaVisited() {
		return lastMonthTotalUddoktaVisited;
	}

	public void setLastMonthTotalUddoktaVisited(Integer lastMonthTotalUddoktaVisited) {
		this.lastMonthTotalUddoktaVisited = lastMonthTotalUddoktaVisited;
	}

	public Integer getLastMonthUniqueUddoktaVisited() {
		return lastMonthUniqueUddoktaVisited;
	}

	public void setLastMonthUniqueUddoktaVisited(Integer lastMonthUniqueUddoktaVisited) {
		this.lastMonthUniqueUddoktaVisited = lastMonthUniqueUddoktaVisited;
	}

	public Integer getLast2ndMonthUniqueUddoktaVisited() {
		return last2ndMonthUniqueUddoktaVisited;
	}

	public void setLast2ndMonthUniqueUddoktaVisited(Integer last2ndMonthUniqueUddoktaVisited) {
		this.last2ndMonthUniqueUddoktaVisited = last2ndMonthUniqueUddoktaVisited;
	}

	public Integer getLast3rdMonthUniqueUddoktaVisited() {
		return last3rdMonthUniqueUddoktaVisited;
	}

	public void setLast3rdMonthUniqueUddoktaVisited(Integer last3rdMonthUniqueUddoktaVisited) {
		this.last3rdMonthUniqueUddoktaVisited = last3rdMonthUniqueUddoktaVisited;
	}
}
