/**
 * 
 */
package com.banks.web.sales.tmr.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.joda.time.DateTime;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.banks.erp.library.util.util.JsfUtil;

/**
 * @author Rajib_Ghosh
 *
 */

@Named
//@ViewScoped
@RequestScoped
public class BigDataReportController implements Serializable {

	private static final long serialVersionUID = 1L;

	private StreamedContent exportReportFile;

	//Report File path
	String reportSrcFileLocation = getReportSrcFileLocation();
	String reportSrcFileExtension = ".zip";
	String reportSrcFileName1 = "Uddokta-Audit-Report";
	String reportSrcFileName2 = "POSM-Management-Report";
	String reportSrcFileName3 = "Competitor-POSM-Information-Report";
	private Date reportSrcFileDate1;
	private Date reportSrcFileDate2;	
	private Date reportSrcFileDate3;	
	private Date minDate;	
	private Date maxDate;

	public Date getReportSrcFileDate1() {
		return reportSrcFileDate1;
	}

	public void setReportSrcFileDate1(Date reportSrcFileDate1) {
		this.reportSrcFileDate1 = reportSrcFileDate1;
	}

	public Date getReportSrcFileDate2() {
		return reportSrcFileDate2;
	}

	public void setReportSrcFileDate2(Date reportSrcFileDate2) {
		this.reportSrcFileDate2 = reportSrcFileDate2;
	}

	public Date getReportSrcFileDate3() {
		return reportSrcFileDate3;
	}

	public void setReportSrcFileDate3(Date reportSrcFileDate3) {
		this.reportSrcFileDate3 = reportSrcFileDate3;
	}

	public Date getMinDate() {
		return minDate;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

	public String getReportSrcFileLocation() {		
		String propertiesFilePath = "/properties/resourcepath.properties";
		Properties props = JsfUtil.getPropertiesFile(propertiesFilePath);
		String reportSrcFileLocation = props.getProperty("reportSrcFileLocation");
		
		return reportSrcFileLocation;
	}

	public StreamedContent getUddoktaAuditReport() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");  
	    String strDate= "-"+formatter.format(reportSrcFileDate1);

		String exportFilename = reportSrcFileName1 + strDate + reportSrcFileExtension;
		Path fullExportPath = Paths.get(reportSrcFileLocation + reportSrcFileName1 + strDate + reportSrcFileExtension);

		try {
			exportReportFile = new DefaultStreamedContent(new FileInputStream(fullExportPath.toFile()),
					FacesContext.getCurrentInstance().getExternalContext().getMimeType(exportFilename),
					exportFilename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return exportReportFile;
	}

	public StreamedContent getPOSMManagementReport() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");  
	    String strDate= "-"+formatter.format(reportSrcFileDate2);

		String exportFilename = reportSrcFileName2 + strDate + reportSrcFileExtension;
		Path fullExportPath = Paths.get(reportSrcFileLocation + reportSrcFileName2 + strDate + reportSrcFileExtension);

		try {
			exportReportFile = new DefaultStreamedContent(new FileInputStream(fullExportPath.toFile()),
					FacesContext.getCurrentInstance().getExternalContext().getMimeType(exportFilename),
					exportFilename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return exportReportFile;
	}

	public StreamedContent getCompetitorPOSMInformationReport() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");  
	    String strDate= "-"+formatter.format(reportSrcFileDate3);

		String exportFilename = reportSrcFileName3 + strDate + reportSrcFileExtension;
		Path fullExportPath = Paths.get(reportSrcFileLocation + reportSrcFileName3 + strDate + reportSrcFileExtension);

		try {
			exportReportFile = new DefaultStreamedContent(new FileInputStream(fullExportPath.toFile()),
					FacesContext.getCurrentInstance().getExternalContext().getMimeType(exportFilename),
					exportFilename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return exportReportFile;
	}

	@PostConstruct
	public void init() {
		//System.out.println("=============== Details Test - Rajib Kumer Ghosh===============" + reportSrcFileDate1);

		// For - Logged In User
		// currentUser = commonUtilService.getCurrentUser();
		
		//Restrict the user to select Date Range
		Date today = new Date();
		long oneDay = 24 * 60 * 60 * 1000;
		minDate = new Date(today.getTime() - (31 * oneDay));
        maxDate = new Date(today.getTime() - (1 * oneDay));
		
		
		/*SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String strDate = dateFormat.format(cal.getTime());
		System.out.println("=============== Yesterday - Rajib Kumer Ghosh===============" + strDate);*/
		
	}

}
