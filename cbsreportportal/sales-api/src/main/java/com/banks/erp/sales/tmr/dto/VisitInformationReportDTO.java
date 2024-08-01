/**
 * 
 */
package com.banks.erp.sales.tmr.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Rajib_Ghosh
 *
 */
public class VisitInformationReportDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String clusterName;
	private String regionName;
	private String areaName;
	private String teritoryName;
	private String tmWalet;
	private String dhWalet;
	private String dsoWalet;	
	private String uddoktaWalet;
	private String status;
	private Date fromDate;
	private Date toDate;
	private Integer reportSelection;
	private String reportName;
	private Integer reportExportFormat;
	
	public VisitInformationReportDTO() {
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getTeritoryName() {
		return teritoryName;
	}

	public void setTeritoryName(String teritoryName) {
		this.teritoryName = teritoryName;
	}

	public String getTmWalet() {
		return tmWalet;
	}

	public void setTmWalet(String tmWalet) {
		this.tmWalet = tmWalet;
	}

	public String getDhWalet() {
		return dhWalet;
	}

	public void setDhWalet(String dhWalet) {
		this.dhWalet = dhWalet;
	}

	public String getDsoWalet() {
		return dsoWalet;
	}

	public void setDsoWalet(String dsoWalet) {
		this.dsoWalet = dsoWalet;
	}

	public String getUddoktaWalet() {
		return uddoktaWalet;
	}

	public void setUddoktaWalet(String uddoktaWalet) {
		this.uddoktaWalet = uddoktaWalet;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Integer getReportSelection() {
		return reportSelection;
	}

	public void setReportSelection(Integer reportSelection) {
		this.reportSelection = reportSelection;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public Integer getReportExportFormat() {
		return reportExportFormat;
	}

	public void setReportExportFormat(Integer reportExportFormat) {
		this.reportExportFormat = reportExportFormat;
	}	

}
