/**
 * 
 */
package com.banks.web.sales.tmr.controller;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.banks.erp.library.util.dto.DropdownDTO;
import com.banks.erp.library.util.util.CommonUtilService;
import com.banks.erp.sa.uaa.model.UserInfo;
import com.banks.erp.sales.tmr.dto.MixTmrInformationReportDTO;
import com.banks.erp.sales.tmr.iservice.IMixTmrInformationReportService;
import com.banks.web.sa.auth.controller.UserAuthenticationController;

import net.sf.jasperreports.engine.JRException;

/**
 * @author Rajib_Ghosh
 *
 */
@Named
@ViewScoped
public class MixTmrInformationReportController implements Serializable {

	private static final long serialVersionUID = 1L;

	// Report JRXML path
	String reportSrcFileLocation = "/reports/RPT_DEV/sales/tmr/mix/";
	String reportSrcFileExtension = ".jrxml";
	String reportSrcFileName1 = "TMR Monthly Attendance Summery Report";
	String reportSrcFileName2 = "TMR Daily Attendance Summery Report";
	String reportSrcFileName3 = "TMR Daily Visit Summery Report";
	String reportSrcFileName4 = "TMR Daily Unique Uddokta Visit Report";
	String reportSrcFileName5 = "TMR Leave Information Report";
	String reportSrcFileName9 = "TMO Stock Report";

	@Inject
	private IMixTmrInformationReportService iMixTmrInformationReportService;
	
	@Inject
	private CommonUtilService commonUtilService; 

	@Inject
	private MixTmrInformationReportDTO mixTmrInformationReportDTO;
	
	private UserInfo currentUser;
	
	private List<DropdownDTO> dropdownDTOClusterList;
	
	private List<DropdownDTO> dropdownDTORegionList;
	
	private List<DropdownDTO> dropdownDTOAreaList;
	
	private List<DropdownDTO> dropdownDTOTeritoryList;

	public MixTmrInformationReportDTO getMixTmrInformationReportDTO() {
		return mixTmrInformationReportDTO;
	}

	public void setMixTmrInformationReportDTO(MixTmrInformationReportDTO mixTmrInformationReportDTO) {
		this.mixTmrInformationReportDTO = mixTmrInformationReportDTO;
	}

	public List<DropdownDTO> getDropdownDTOClusterList() {
		return dropdownDTOClusterList;
	}

	public void setDropdownDTOClusterList(List<DropdownDTO> dropdownDTOClusterList) {
		this.dropdownDTOClusterList = dropdownDTOClusterList;
	}

	public List<DropdownDTO> getDropdownDTORegionList() {
		return dropdownDTORegionList;
	}

	public void setDropdownDTORegionList(List<DropdownDTO> dropdownDTORegionList) {
		this.dropdownDTORegionList = dropdownDTORegionList;
	}

	public List<DropdownDTO> getDropdownDTOAreaList() {
		return dropdownDTOAreaList;
	}

	public void setDropdownDTOAreaList(List<DropdownDTO> dropdownDTOAreaList) {
		this.dropdownDTOAreaList = dropdownDTOAreaList;
	}

	public List<DropdownDTO> getDropdownDTOTeritoryList() {
		return dropdownDTOTeritoryList;
	}

	public void setDropdownDTOTeritoryList(List<DropdownDTO> dropdownDTOTeritoryList) {
		this.dropdownDTOTeritoryList = dropdownDTOTeritoryList;
	}

	@PostConstruct
	public void init() {
		//System.out.println("=============== Details Test - Rajib Kumer Ghosh===============");
		
		//For - Default Value
		mixTmrInformationReportDTO.setReportSelection(1);
		mixTmrInformationReportDTO.setReportExportFormat(1);
		
		//For - Logged In User
		currentUser = commonUtilService.getCurrentUser();
		
		//For - Branch Code Dropdown List		
		//dropdownDTOBranchList = commonUtilService.getBranchDropdownList(currentUser.getUserName(), currentUser.getDefaultBranchCode(), DropdownDTO.class);
		
		//For - Cluster Code Dropdown List		
		dropdownDTOClusterList = commonUtilService.getClusterDropdownList(currentUser.getUserName(), currentUser.getDefaultBranchCode(), DropdownDTO.class);
		
		//For - Power BI iFrame
		mixTmrInformationReportDTO.setStatus("https://app.powerbi.com/view?r=eyJrIjoiNDEyMTljNmEtNDUzNy00YjRjLWFiYzYtOTdjNTg5MWExYmQzIiwidCI6IjQ4MWU4OTJlLWIwZWEtNDIyYS1hMjNlLTA5ZTNkOGUyNTg5OCIsImMiOjEwfQ%3D%3D"); 
	}	

	public void getRegionDropdownList() {
		dropdownDTORegionList = commonUtilService.getRegionDropdownList(mixTmrInformationReportDTO.getClusterName(), DropdownDTO.class);
	}	

	public void getAreaDropdownList() {
		dropdownDTOAreaList = commonUtilService.getAreaDropdownList(mixTmrInformationReportDTO.getRegionName(), DropdownDTO.class);
	}

	public void getTeritoryDropdownList() {
		dropdownDTOTeritoryList = commonUtilService.getTeritoryDropdownList(mixTmrInformationReportDTO.getAreaName(), DropdownDTO.class);
	}
	
	public void exportReport() throws JRException, IOException {

		String reportSrcFilePath = null;
		Integer reportSelection = mixTmrInformationReportDTO.getReportSelection();
		Integer reportExportformat = mixTmrInformationReportDTO.getReportExportFormat();
		
		// Report Specific Parameter
		Integer dateType = null;
		String dateFormat = null;
		String currencyFormat = null;
		String reportID = null;	
		
		String systemOpenDate = UserAuthenticationController.getSystemDate().substring(0,11);   //"20-Jun-2021"
		String firstDate = "01-".concat(systemOpenDate.substring(3,11));  						//"01-Jun-2021"
		Date autoFromDate = null;
		Date autoToDate = null; 
		

		// Report Common Parameter
		String orgID = "003"; // ENUM		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("ORGID", orgID);
		parameters.put("LOGIN_BRANCH_CODE", currentUser.getDefaultBranchCode());
		parameters.put("ReportPrintingUser", currentUser.getUserName());

		try {
			
			autoFromDate = new SimpleDateFormat("dd-MMM-yy").parse(firstDate);
			autoToDate = new SimpleDateFormat("dd-MMM-yy").parse(systemOpenDate);
			
			switch (reportSelection) {
			case 1:
				reportSrcFilePath = reportSrcFileLocation + reportSrcFileName1 + reportSrcFileExtension;

				// Report Specific Parameter
				dateType = 2; // ENUM
				dateFormat = "dd-MMM-yyyy";
				currencyFormat = "##,##,##,###.00##";
				reportID = "1399050301";
				parameters.put("DateType", dateType);
				parameters.put("DateFormat", dateFormat);
				parameters.put("CurrencyFormat", currencyFormat);
				parameters.put("RDOREPORTNUMBER", reportID);
				// parameters.put("RDOREPORTNUMBER", ReportNumber.BDO_Uddokta_Audit_Report.value());
				parameters.put("ExportFormat", reportExportformat);

				if (mixTmrInformationReportDTO.getFromDate() != null) {
					
					parameters.put("FromDate", mixTmrInformationReportDTO.getFromDate());
				} else {
					//parameters.put("FromDate", null);
					parameters.put("FromDate", autoFromDate);					
				}

				if (mixTmrInformationReportDTO.getToDate() != null) {
					parameters.put("ToDate", mixTmrInformationReportDTO.getToDate());
				} else {
					//parameters.put("ToDate", null);
					parameters.put("ToDate", autoToDate);
				}

				if (mixTmrInformationReportDTO.getClusterName() != null) {
					parameters.put("CLUSTER", mixTmrInformationReportDTO.getClusterName());
				} else {
					parameters.put("CLUSTER", null);
				}

				// if (!mixTmrInformationReportDTO.getRegionName().isEmpty() ||
				// mixTmrInformationReportDTO.getRegionName()!=null) {
				if (mixTmrInformationReportDTO.getRegionName() != null) {
					parameters.put("REGION", mixTmrInformationReportDTO.getRegionName());
				} else {
					parameters.put("REGION", null);
				}

				if (mixTmrInformationReportDTO.getAreaName() != null) {
					parameters.put("AREA", mixTmrInformationReportDTO.getAreaName());
				} else {
					parameters.put("AREA", null);
				}

				if (mixTmrInformationReportDTO.getTeritoryName() != null) {
					parameters.put("TERRITORY", mixTmrInformationReportDTO.getTeritoryName());
				} else {
					parameters.put("TERRITORY", null);
				}

				if (mixTmrInformationReportDTO.getDhWalet() != null) {
					parameters.put("DHNumber", mixTmrInformationReportDTO.getDhWalet());
				} else {
					parameters.put("DHNumber", null);
				}

				if (mixTmrInformationReportDTO.getTmoWalet() != null) {
					parameters.put("TMONumber", mixTmrInformationReportDTO.getTmoWalet());
				} else {
					parameters.put("TMONumber", null);
				}

				if (mixTmrInformationReportDTO.getDsoWalet() != null) {
					parameters.put("DSONumber", mixTmrInformationReportDTO.getDsoWalet());
				} else {
					parameters.put("DSONumber", null);
				}

				if (mixTmrInformationReportDTO.getUddoktaWalet() != null) {
					parameters.put("UddoktaNumber", mixTmrInformationReportDTO.getUddoktaWalet());
				} else {
					parameters.put("UddoktaNumber", null);
				}

				iMixTmrInformationReportService.exportReport(reportSrcFilePath, parameters, reportSrcFileName1,
						reportExportformat);		
				break;
				
			case 2:				
				reportSrcFilePath = reportSrcFileLocation + reportSrcFileName2 + reportSrcFileExtension;

				// Report Specific Parameter
				dateType = 2; // ENUM
				dateFormat = "dd-MMM-yyyy";
				currencyFormat = "##,##,##,###.00##";
				reportID = "1399050302";
				parameters.put("DateType", dateType);
				parameters.put("DateFormat", dateFormat);
				parameters.put("CurrencyFormat", currencyFormat);
				parameters.put("RDOREPORTNUMBER", reportID);
				// parameters.put("RDOREPORTNUMBER", ReportNumber.DSO_Uddokta_Audit_Report.value());
				parameters.put("ExportFormat", reportExportformat);

				if (mixTmrInformationReportDTO.getFromDate() != null) {
					
					parameters.put("FromDate", mixTmrInformationReportDTO.getFromDate());
				} else {
					parameters.put("FromDate", autoFromDate);					
				}

				if (mixTmrInformationReportDTO.getToDate() != null) {
					parameters.put("ToDate", mixTmrInformationReportDTO.getToDate());
				} else {
					parameters.put("ToDate", autoToDate);
				}

				if (mixTmrInformationReportDTO.getClusterName() != null) {
					parameters.put("CLUSTER", mixTmrInformationReportDTO.getClusterName());
				} else {
					parameters.put("CLUSTER", null);
				}

				// if (!mixTmrInformationReportDTO.getRegionName().isEmpty() ||
				// mixTmrInformationReportDTO.getRegionName()!=null) {
				if (mixTmrInformationReportDTO.getRegionName() != null) {
					parameters.put("REGION", mixTmrInformationReportDTO.getRegionName());
				} else {
					parameters.put("REGION", null);
				}

				if (mixTmrInformationReportDTO.getAreaName() != null) {
					parameters.put("AREA", mixTmrInformationReportDTO.getAreaName());
				} else {
					parameters.put("AREA", null);
				}

				if (mixTmrInformationReportDTO.getTeritoryName() != null) {
					parameters.put("TERRITORY", mixTmrInformationReportDTO.getTeritoryName());
				} else {
					parameters.put("TERRITORY", null);
				}

				if (mixTmrInformationReportDTO.getTmWalet() != null) {
					parameters.put("TMNumber", mixTmrInformationReportDTO.getTmWalet());
				} else {
					parameters.put("TMNumber", null);
				}

				if (mixTmrInformationReportDTO.getDhWalet() != null) {
					parameters.put("DHNumber", mixTmrInformationReportDTO.getDhWalet());
				} else {
					parameters.put("DHNumber", null);
				}

				if (mixTmrInformationReportDTO.getTmoWalet() != null) {
					parameters.put("TMONumber", mixTmrInformationReportDTO.getTmoWalet());
				} else {
					parameters.put("TMONumber", null);
				}

				if (mixTmrInformationReportDTO.getDsoWalet() != null) {
					parameters.put("DSONumber", mixTmrInformationReportDTO.getDsoWalet());
				} else {
					parameters.put("DSONumber", null);
				}

				if (mixTmrInformationReportDTO.getUddoktaWalet() != null) {
					parameters.put("UddoktaNumber", mixTmrInformationReportDTO.getUddoktaWalet());
				} else {
					parameters.put("UddoktaNumber", null);
				}

				if (mixTmrInformationReportDTO.getTotalMarketHour() != null) {
					parameters.put("TotalMarketHour", mixTmrInformationReportDTO.getTotalMarketHour());
				} else {
					parameters.put("TotalMarketHour", 6);
				}

				iMixTmrInformationReportService.exportReport(reportSrcFilePath, parameters, reportSrcFileName2,
						reportExportformat);
				break;
				
			case 3:				
				reportSrcFilePath = reportSrcFileLocation + reportSrcFileName3 + reportSrcFileExtension;

				// Report Specific Parameter
				dateType = 2; // ENUM
				dateFormat = "dd-MMM-yyyy";
				currencyFormat = "##,##,##,###.00##";
				reportID = "1399050303";
				parameters.put("DateType", dateType);
				parameters.put("DateFormat", dateFormat);
				parameters.put("CurrencyFormat", currencyFormat);
				parameters.put("RDOREPORTNUMBER", reportID);
				// parameters.put("RDOREPORTNUMBER", ReportNumber.DSO_Uddokta_Audit_Report.value());
				parameters.put("ExportFormat", reportExportformat);

				if (mixTmrInformationReportDTO.getFromDate() != null) {
					
					parameters.put("FromDate", mixTmrInformationReportDTO.getFromDate());
				} else {
					parameters.put("FromDate", autoFromDate);					
				}

				if (mixTmrInformationReportDTO.getToDate() != null) {
					parameters.put("ToDate", mixTmrInformationReportDTO.getToDate());
				} else {
					parameters.put("ToDate", autoToDate);
				}

				if (mixTmrInformationReportDTO.getClusterName() != null) {
					parameters.put("CLUSTER", mixTmrInformationReportDTO.getClusterName());
				} else {
					parameters.put("CLUSTER", null);
				}

				// if (!mixTmrInformationReportDTO.getRegionName().isEmpty() ||
				// mixTmrInformationReportDTO.getRegionName()!=null) {
				if (mixTmrInformationReportDTO.getRegionName() != null) {
					parameters.put("REGION", mixTmrInformationReportDTO.getRegionName());
				} else {
					parameters.put("REGION", null);
				}

				if (mixTmrInformationReportDTO.getAreaName() != null) {
					parameters.put("AREA", mixTmrInformationReportDTO.getAreaName());
				} else {
					parameters.put("AREA", null);
				}

				if (mixTmrInformationReportDTO.getTeritoryName() != null) {
					parameters.put("TERRITORY", mixTmrInformationReportDTO.getTeritoryName());
				} else {
					parameters.put("TERRITORY", null);
				}

				if (mixTmrInformationReportDTO.getTmWalet() != null) {
					parameters.put("TMNumber", mixTmrInformationReportDTO.getTmWalet());
				} else {
					parameters.put("TMNumber", null);
				}

				if (mixTmrInformationReportDTO.getDhWalet() != null) {
					parameters.put("DHNumber", mixTmrInformationReportDTO.getDhWalet());
				} else {
					parameters.put("DHNumber", null);
				}

				if (mixTmrInformationReportDTO.getTmoWalet() != null) {
					parameters.put("TMONumber", mixTmrInformationReportDTO.getTmoWalet());
				} else {
					parameters.put("TMONumber", null);
				}

				if (mixTmrInformationReportDTO.getDsoWalet() != null) {
					parameters.put("DSONumber", mixTmrInformationReportDTO.getDsoWalet());
				} else {
					parameters.put("DSONumber", null);
				}

				if (mixTmrInformationReportDTO.getUddoktaWalet() != null) {
					parameters.put("UddoktaNumber", mixTmrInformationReportDTO.getUddoktaWalet());
				} else {
					parameters.put("UddoktaNumber", null);
				}

				if (mixTmrInformationReportDTO.getTotalMarketHour() != null) {
					parameters.put("TotalMarketHour", mixTmrInformationReportDTO.getTotalMarketHour());
				} else {
					parameters.put("TotalMarketHour", 6);
				}

				iMixTmrInformationReportService.exportReport(reportSrcFilePath, parameters, reportSrcFileName3,
						reportExportformat);
				break;
				
			case 4:				
				reportSrcFilePath = reportSrcFileLocation + reportSrcFileName4 + reportSrcFileExtension;

				// Report Specific Parameter
				dateType = 2; // ENUM
				dateFormat = "dd-MMM-yyyy";
				currencyFormat = "##,##,##,###.00##";
				reportID = "1399050304";
				parameters.put("DateType", dateType);
				parameters.put("DateFormat", dateFormat);
				parameters.put("CurrencyFormat", currencyFormat);
				parameters.put("RDOREPORTNUMBER", reportID);
				// parameters.put("RDOREPORTNUMBER", ReportNumber.DSO_Uddokta_Audit_Report.value());
				parameters.put("ExportFormat", reportExportformat);

				if (mixTmrInformationReportDTO.getFromDate() != null) {
					
					parameters.put("FromDate", mixTmrInformationReportDTO.getFromDate());
				} else {
					parameters.put("FromDate", autoFromDate);					
				}

				if (mixTmrInformationReportDTO.getToDate() != null) {
					parameters.put("ToDate", mixTmrInformationReportDTO.getToDate());
				} else {
					parameters.put("ToDate", autoToDate);
				}

				if (mixTmrInformationReportDTO.getClusterName() != null) {
					parameters.put("CLUSTER", mixTmrInformationReportDTO.getClusterName());
				} else {
					parameters.put("CLUSTER", null);
				}

				// if (!mixTmrInformationReportDTO.getRegionName().isEmpty() ||
				// mixTmrInformationReportDTO.getRegionName()!=null) {
				if (mixTmrInformationReportDTO.getRegionName() != null) {
					parameters.put("REGION", mixTmrInformationReportDTO.getRegionName());
				} else {
					parameters.put("REGION", null);
				}

				if (mixTmrInformationReportDTO.getAreaName() != null) {
					parameters.put("AREA", mixTmrInformationReportDTO.getAreaName());
				} else {
					parameters.put("AREA", null);
				}

				if (mixTmrInformationReportDTO.getTeritoryName() != null) {
					parameters.put("TERRITORY", mixTmrInformationReportDTO.getTeritoryName());
				} else {
					parameters.put("TERRITORY", null);
				}

				if (mixTmrInformationReportDTO.getTmWalet() != null) {
					parameters.put("TMNumber", mixTmrInformationReportDTO.getTmWalet());
				} else {
					parameters.put("TMNumber", null);
				}

				if (mixTmrInformationReportDTO.getDhWalet() != null) {
					parameters.put("DHNumber", mixTmrInformationReportDTO.getDhWalet());
				} else {
					parameters.put("DHNumber", null);
				}

				if (mixTmrInformationReportDTO.getTmoWalet() != null) {
					parameters.put("TMONumber", mixTmrInformationReportDTO.getTmoWalet());
				} else {
					parameters.put("TMONumber", null);
				}

				if (mixTmrInformationReportDTO.getDsoWalet() != null) {
					parameters.put("DSONumber", mixTmrInformationReportDTO.getDsoWalet());
				} else {
					parameters.put("DSONumber", null);
				}

				if (mixTmrInformationReportDTO.getUddoktaWalet() != null) {
					parameters.put("UddoktaNumber", mixTmrInformationReportDTO.getUddoktaWalet());
				} else {
					parameters.put("UddoktaNumber", null);
				}

				if (mixTmrInformationReportDTO.getTotalMarketHour() != null) {
					parameters.put("TotalMarketHour", mixTmrInformationReportDTO.getTotalMarketHour());
				} else {
					parameters.put("TotalMarketHour", 6);
				}

				iMixTmrInformationReportService.exportReport(reportSrcFilePath, parameters, reportSrcFileName4,
						reportExportformat);
				break;
				
			case 5:				
				reportSrcFilePath = reportSrcFileLocation + reportSrcFileName5 + reportSrcFileExtension;

				// Report Specific Parameter
				dateType = 2; // ENUM
				dateFormat = "dd-MMM-yyyy";
				currencyFormat = "##,##,##,###.00##";
				reportID = "1399050305";
				parameters.put("DateType", dateType);
				parameters.put("DateFormat", dateFormat);
				parameters.put("CurrencyFormat", currencyFormat);
				parameters.put("RDOREPORTNUMBER", reportID);
				// parameters.put("RDOREPORTNUMBER", ReportNumber.DSO_Uddokta_Audit_Report.value());
				parameters.put("ExportFormat", reportExportformat);

				if (mixTmrInformationReportDTO.getFromDate() != null) {
					
					parameters.put("FromDate", mixTmrInformationReportDTO.getFromDate());
				} else {
					parameters.put("FromDate", autoFromDate);					
				}

				if (mixTmrInformationReportDTO.getToDate() != null) {
					parameters.put("ToDate", mixTmrInformationReportDTO.getToDate());
				} else {
					parameters.put("ToDate", autoToDate);
				}

				if (mixTmrInformationReportDTO.getClusterName() != null) {
					parameters.put("CLUSTER", mixTmrInformationReportDTO.getClusterName());
				} else {
					parameters.put("CLUSTER", null);
				}

				// if (!mixTmrInformationReportDTO.getRegionName().isEmpty() ||
				// mixTmrInformationReportDTO.getRegionName()!=null) {
				if (mixTmrInformationReportDTO.getRegionName() != null) {
					parameters.put("REGION", mixTmrInformationReportDTO.getRegionName());
				} else {
					parameters.put("REGION", null);
				}

				if (mixTmrInformationReportDTO.getAreaName() != null) {
					parameters.put("AREA", mixTmrInformationReportDTO.getAreaName());
				} else {
					parameters.put("AREA", null);
				}

				if (mixTmrInformationReportDTO.getTeritoryName() != null) {
					parameters.put("TERRITORY", mixTmrInformationReportDTO.getTeritoryName());
				} else {
					parameters.put("TERRITORY", null);
				}

				if (mixTmrInformationReportDTO.getTmWalet() != null) {
					parameters.put("TMNumber", mixTmrInformationReportDTO.getTmWalet());
				} else {
					parameters.put("TMNumber", null);
				}

				if (mixTmrInformationReportDTO.getDhWalet() != null) {
					parameters.put("DHNumber", mixTmrInformationReportDTO.getDhWalet());
				} else {
					parameters.put("DHNumber", null);
				}

				if (mixTmrInformationReportDTO.getTmoWalet() != null) {
					parameters.put("TMONumber", mixTmrInformationReportDTO.getTmoWalet());
				} else {
					parameters.put("TMONumber", null);
				}

				if (mixTmrInformationReportDTO.getDsoWalet() != null) {
					parameters.put("DSONumber", mixTmrInformationReportDTO.getDsoWalet());
				} else {
					parameters.put("DSONumber", null);
				}

				if (mixTmrInformationReportDTO.getUddoktaWalet() != null) {
					parameters.put("UddoktaNumber", mixTmrInformationReportDTO.getUddoktaWalet());
				} else {
					parameters.put("UddoktaNumber", null);
				}

				if (mixTmrInformationReportDTO.getTotalMarketHour() != null) {
					parameters.put("TotalMarketHour", mixTmrInformationReportDTO.getTotalMarketHour());
				} else {
					parameters.put("TotalMarketHour", 6);
				}

				iMixTmrInformationReportService.exportReport(reportSrcFilePath, parameters, reportSrcFileName5,
						reportExportformat);
				break;
				
			case 9:
				reportSrcFilePath = reportSrcFileLocation + reportSrcFileName9 + reportSrcFileExtension;

				// Report Specific Parameter
				dateType = 2; // ENUM
				dateFormat = "dd-MMM-yyyy";
				currencyFormat = "##,##,##,###.00##";
				reportID = "1399050309";
				parameters.put("DateType", dateType);
				parameters.put("DateFormat", dateFormat);
				parameters.put("CurrencyFormat", currencyFormat);
				parameters.put("RDOREPORTNUMBER", reportID);
				// parameters.put("RDOREPORTNUMBER", ReportNumber.BDO_Uddokta_Audit_Report.value());
				parameters.put("ExportFormat", reportExportformat);

				if (mixTmrInformationReportDTO.getFromDate() != null) {
					
					parameters.put("FromDate", mixTmrInformationReportDTO.getFromDate());
				} else {
					//parameters.put("FromDate", null);
					parameters.put("FromDate", autoFromDate);					
				}

				if (mixTmrInformationReportDTO.getToDate() != null) {
					parameters.put("ToDate", mixTmrInformationReportDTO.getToDate());
				} else {
					//parameters.put("ToDate", null);
					parameters.put("ToDate", autoToDate);
				}

				parameters.put("CLUSTER", mixTmrInformationReportDTO.getClusterName());

				// if (!mixTmrInformationReportDTO.getRegionName().isEmpty() ||
				// mixTmrInformationReportDTO.getRegionName()!=null) {
				if (mixTmrInformationReportDTO.getRegionName() != null) {
					parameters.put("REGION", mixTmrInformationReportDTO.getRegionName());
				} else {
					parameters.put("REGION", null);
				}

				if (mixTmrInformationReportDTO.getAreaName() != null) {
					parameters.put("AREA", mixTmrInformationReportDTO.getAreaName());
				} else {
					parameters.put("AREA", null);
				}

				if (mixTmrInformationReportDTO.getTeritoryName() != null) {
					parameters.put("TERRITORY", mixTmrInformationReportDTO.getTeritoryName());
				} else {
					parameters.put("TERRITORY", null);
				}

				if (mixTmrInformationReportDTO.getDhWalet() != null) {
					parameters.put("DHNumber", mixTmrInformationReportDTO.getDhWalet());
				} else {
					parameters.put("DHNumber", null);
				}

				if (mixTmrInformationReportDTO.getTmoWalet() != null) {
					parameters.put("TMONumber", mixTmrInformationReportDTO.getTmoWalet());
				} else {
					parameters.put("TMONumber", null);
				}

				if (mixTmrInformationReportDTO.getDsoWalet() != null) {
					parameters.put("DSONumber", mixTmrInformationReportDTO.getDsoWalet());
				} else {
					parameters.put("DSONumber", null);
				}

				if (mixTmrInformationReportDTO.getUddoktaWalet() != null) {
					parameters.put("UddoktaNumber", mixTmrInformationReportDTO.getUddoktaWalet());
				} else {
					parameters.put("UddoktaNumber", null);
				}

				iMixTmrInformationReportService.exportReport(reportSrcFilePath, parameters, reportSrcFileName9,
						reportExportformat);		
				break;
				
			default:
				System.out.println("Invalid Report Selection!");
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}