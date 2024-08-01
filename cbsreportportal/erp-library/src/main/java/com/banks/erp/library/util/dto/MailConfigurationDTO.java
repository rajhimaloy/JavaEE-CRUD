/**
 * 
 */
package com.banks.erp.library.util.dto;

import java.io.Serializable;

/**
 * @author Rajib Kumer Ghosh
 *
 */

public class MailConfigurationDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String reportID;
	private String reportName;
	private String mailGroupID;
	private String mailGroupName;
	private String mailUserName;
	private String mailPassword;
	private String fromMail;
	private String toMail;
	private String ccMail;
	
	public MailConfigurationDTO() {
	}

	public String getReportID() {
		return reportID;
	}

	public void setReportID(String reportID) {
		this.reportID = reportID;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getMailGroupID() {
		return mailGroupID;
	}

	public void setMailGroupID(String mailGroupID) {
		this.mailGroupID = mailGroupID;
	}

	public String getMailGroupName() {
		return mailGroupName;
	}

	public void setMailGroupName(String mailGroupName) {
		this.mailGroupName = mailGroupName;
	}

	public String getMailUserName() {
		return mailUserName;
	}

	public void setMailUserName(String mailUserName) {
		this.mailUserName = mailUserName;
	}

	public String getMailPassword() {
		return mailPassword;
	}

	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}

	public String getFromMail() {
		return fromMail;
	}

	public void setFromMail(String fromMail) {
		this.fromMail = fromMail;
	}

	public String getToMail() {
		return toMail;
	}

	public void setToMail(String toMail) {
		this.toMail = toMail;
	}

	public String getCcMail() {
		return ccMail;
	}

	public void setCcMail(String ccMail) {
		this.ccMail = ccMail;
	}	
	
}
