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
import com.banks.erp.sales.tmr.dto.VisitInformationReportDTO;
import com.banks.erp.sales.tmr.iservice.IVisitInformationReportService;
import com.banks.web.sa.auth.controller.UserAuthenticationController;

import net.sf.jasperreports.engine.JRException;

/**
 * @author Rajib_Ghosh
 *
 */
@Named
@ViewScoped
public class VisitInformationReportController implements Serializable {

	private static final long serialVersionUID = 1L;

	// Report JRXML path
	String reportSrcFileLocation = "/reports/RPT_DEV/sales/tmr/visit/";
	String reportSrcFileExtension = ".jrxml";
	String reportSrcFileName1 = "Merchant Ondoard Audit Report";
	String reportSrcFileName2 = "Merchant Regular Visit Audit Report";
	String reportSrcFileName3 = "Merchant Campaign Visit Audit Report";
	String reportSrcFileName4 = "Media Audit Report";

	@Inject
	private IVisitInformationReportService iVisitInformationReportService;
	
	@Inject
	private CommonUtilService commonUtilService; 

	@Inject
	private VisitInformationReportDTO visitInformationReportDTO;
	
	private UserInfo currentUser;
	
	private List<DropdownDTO> dropdownDTOClusterList;
	
	private List<DropdownDTO> dropdownDTORegionList;
	
	private List<DropdownDTO> dropdownDTOAreaList;
	
	private List<DropdownDTO> dropdownDTOTeritoryList;

	public VisitInformationReportDTO getVisitInformationReportDTO() {
		return visitInformationReportDTO;
	}

	public void setVisitInformationReportDTO(VisitInformationReportDTO visitInformationReportDTO) {
		this.visitInformationReportDTO = visitInformationReportDTO;
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
		visitInformationReportDTO.setReportSelection(1);
		visitInformationReportDTO.setReportExportFormat(1);
		
		//For - Logged In User
		currentUser = commonUtilService.getCurrentUser();
		
		//For - Branch Code Dropdown List		
		//dropdownDTOBranchList = commonUtilService.getBranchDropdownList(currentUser.getUserName(), currentUser.getDefaultBranchCode(), DropdownDTO.class);
		
		//For - Cluster Code Dropdown List		
		dropdownDTOClusterList = commonUtilService.getClusterDropdownList(currentUser.getUserName(), currentUser.getDefaultBranchCode(), DropdownDTO.class);
		
		//For - Power BI iFrame
		visitInformationReportDTO.setStatus("https://app.powerbi.com/view?r=eyJrIjoiNDEyMTljNmEtNDUzNy00YjRjLWFiYzYtOTdjNTg5MWExYmQzIiwidCI6IjQ4MWU4OTJlLWIwZWEtNDIyYS1hMjNlLTA5ZTNkOGUyNTg5OCIsImMiOjEwfQ%3D%3D"); 
	}	

	public void getRegionDropdownList() {
		dropdownDTORegionList = commonUtilService.getRegionDropdownList(visitInformationReportDTO.getClusterName(), DropdownDTO.class);
	}	

	public void getAreaDropdownList() {
		dropdownDTOAreaList = commonUtilService.getAreaDropdownList(visitInformationReportDTO.getRegionName(), DropdownDTO.class);
	}

	public void getTeritoryDropdownList() {
		dropdownDTOTeritoryList = commonUtilService.getTeritoryDropdownList(visitInformationReportDTO.getAreaName(), DropdownDTO.class);
	}
	
	public void exportReport() throws JRException, IOException {

		String reportSrcFilePath = null;
		Integer reportSelection = visitInformationReportDTO.getReportSelection();
		Integer reportExportformat = visitInformationReportDTO.getReportExportFormat();
		
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
				reportID = "1399050201";
				parameters.put("DateType", dateType);
				parameters.put("DateFormat", dateFormat);
				parameters.put("CurrencyFormat", currencyFormat);
				parameters.put("RDOREPORTNUMBER", reportID);
				// parameters.put("RDOREPORTNUMBER", ReportNumber.BDO_Uddokta_Audit_Report.value());
				parameters.put("ExportFormat", reportExportformat);

				if (visitInformationReportDTO.getFromDate() != null) {
					
					parameters.put("FromDate", visitInformationReportDTO.getFromDate());
				} else {
					//parameters.put("FromDate", null);
					parameters.put("FromDate", autoFromDate);					
				}

				if (visitInformationReportDTO.getToDate() != null) {
					parameters.put("ToDate", visitInformationReportDTO.getToDate());
				} else {
					//parameters.put("ToDate", null);
					parameters.put("ToDate", autoToDate);
				}
				
				if (visitInformationReportDTO.getClusterName() != null) {
					parameters.put("CLUSTER", visitInformationReportDTO.getClusterName());
				} else {
					parameters.put("CLUSTER", null);
				}

				// if (!visitInformationReportDTO.getRegionName().isEmpty() ||
				// visitInformationReportDTO.getRegionName()!=null) {
				if (visitInformationReportDTO.getRegionName() != null) {
					parameters.put("REGION", visitInformationReportDTO.getRegionName());
				} else {
					parameters.put("REGION", null);
				}

				if (visitInformationReportDTO.getAreaName() != null) {
					parameters.put("AREA", visitInformationReportDTO.getAreaName());
				} else {
					parameters.put("AREA", null);
				}

				if (visitInformationReportDTO.getTeritoryName() != null) {
					parameters.put("TERRITORY", visitInformationReportDTO.getTeritoryName());
				} else {
					parameters.put("TERRITORY", null);
				}

				if (visitInformationReportDTO.getTmWalet() != null) {
					parameters.put("TMNumber", visitInformationReportDTO.getTmWalet());
				} else {
					parameters.put("TMNumber", null);
				}

				if (visitInformationReportDTO.getDhWalet() != null) {
					parameters.put("DHNumber", visitInformationReportDTO.getDhWalet());
				} else {
					parameters.put("DHNumber", null);
				}

				if (visitInformationReportDTO.getDsoWalet() != null) {
					parameters.put("DSONumber", visitInformationReportDTO.getDsoWalet());
				} else {
					parameters.put("DSONumber", null);
				}

				if (visitInformationReportDTO.getUddoktaWalet() != null) {
					parameters.put("UddoktaNumber", visitInformationReportDTO.getUddoktaWalet());
				} else {
					parameters.put("UddoktaNumber", null);
				}

				iVisitInformationReportService.exportReport(reportSrcFilePath, parameters, reportSrcFileName1,
						reportExportformat);		
				break;
				
			case 2:				
				reportSrcFilePath = reportSrcFileLocation + reportSrcFileName2 + reportSrcFileExtension;

				// Report Specific Parameter
				dateType = 2; // ENUM
				dateFormat = "dd-MMM-yyyy";
				currencyFormat = "##,##,##,###.00##";
				reportID = "1399050202";
				parameters.put("DateType", dateType);
				parameters.put("DateFormat", dateFormat);
				parameters.put("CurrencyFormat", currencyFormat);
				parameters.put("RDOREPORTNUMBER", reportID);
				// parameters.put("RDOREPORTNUMBER", ReportNumber.DSO_Uddokta_Audit_Report.value());
				parameters.put("ExportFormat", reportExportformat);

				if (visitInformationReportDTO.getFromDate() != null) {
					
					parameters.put("FromDate", visitInformationReportDTO.getFromDate());
				} else {
					parameters.put("FromDate", autoFromDate);					
				}

				if (visitInformationReportDTO.getToDate() != null) {
					parameters.put("ToDate", visitInformationReportDTO.getToDate());
				} else {
					parameters.put("ToDate", autoToDate);
				}

				if (visitInformationReportDTO.getClusterName() != null) {
					parameters.put("CLUSTER", visitInformationReportDTO.getClusterName());
				} else {
					parameters.put("CLUSTER", null);
				}

				// if (!visitInformationReportDTO.getRegionName().isEmpty() ||
				// visitInformationReportDTO.getRegionName()!=null) {
				if (visitInformationReportDTO.getRegionName() != null) {
					parameters.put("REGION", visitInformationReportDTO.getRegionName());
				} else {
					parameters.put("REGION", null);
				}

				if (visitInformationReportDTO.getAreaName() != null) {
					parameters.put("AREA", visitInformationReportDTO.getAreaName());
				} else {
					parameters.put("AREA", null);
				}

				if (visitInformationReportDTO.getTeritoryName() != null) {
					parameters.put("TERRITORY", visitInformationReportDTO.getTeritoryName());
				} else {
					parameters.put("TERRITORY", null);
				}

				if (visitInformationReportDTO.getTmWalet() != null) {
					parameters.put("TMNumber", visitInformationReportDTO.getTmWalet());
				} else {
					parameters.put("TMNumber", null);
				}

				if (visitInformationReportDTO.getDhWalet() != null) {
					parameters.put("DHNumber", visitInformationReportDTO.getDhWalet());
				} else {
					parameters.put("DHNumber", null);
				}

				if (visitInformationReportDTO.getDsoWalet() != null) {
					parameters.put("DSONumber", visitInformationReportDTO.getDsoWalet());
				} else {
					parameters.put("DSONumber", null);
				}

				if (visitInformationReportDTO.getUddoktaWalet() != null) {
					parameters.put("UddoktaNumber", visitInformationReportDTO.getUddoktaWalet());
				} else {
					parameters.put("UddoktaNumber", null);
				}
				

				iVisitInformationReportService.exportReport(reportSrcFilePath, parameters, reportSrcFileName2,
						reportExportformat);
				break;
				
			case 3:
				reportSrcFilePath = reportSrcFileLocation + reportSrcFileName3 + reportSrcFileExtension;

				// Report Specific Parameter
				dateType = 2; // ENUM
				dateFormat = "dd-MMM-yyyy";
				currencyFormat = "##,##,##,###.00##";
				reportID = "1399050203";
				parameters.put("DateType", dateType);
				parameters.put("DateFormat", dateFormat);
				parameters.put("CurrencyFormat", currencyFormat);
				parameters.put("RDOREPORTNUMBER", reportID);
				// parameters.put("RDOREPORTNUMBER", ReportNumber.BDO_Uddokta_Audit_Report.value());
				parameters.put("ExportFormat", reportExportformat);

				if (visitInformationReportDTO.getFromDate() != null) {
					
					parameters.put("FromDate", visitInformationReportDTO.getFromDate());
				} else {
					//parameters.put("FromDate", null);
					parameters.put("FromDate", autoFromDate);					
				}

				if (visitInformationReportDTO.getToDate() != null) {
					parameters.put("ToDate", visitInformationReportDTO.getToDate());
				} else {
					//parameters.put("ToDate", null);
					parameters.put("ToDate", autoToDate);
				}

				if (visitInformationReportDTO.getClusterName() != null) {
					parameters.put("CLUSTER", visitInformationReportDTO.getClusterName());
				} else {
					parameters.put("CLUSTER", null);
				}

				// if (!visitInformationReportDTO.getRegionName().isEmpty() ||
				// visitInformationReportDTO.getRegionName()!=null) {
				if (visitInformationReportDTO.getRegionName() != null) {
					parameters.put("REGION", visitInformationReportDTO.getRegionName());
				} else {
					parameters.put("REGION", null);
				}

				if (visitInformationReportDTO.getAreaName() != null) {
					parameters.put("AREA", visitInformationReportDTO.getAreaName());
				} else {
					parameters.put("AREA", null);
				}

				if (visitInformationReportDTO.getTeritoryName() != null) {
					parameters.put("TERRITORY", visitInformationReportDTO.getTeritoryName());
				} else {
					parameters.put("TERRITORY", null);
				}

				if (visitInformationReportDTO.getTmWalet() != null) {
					parameters.put("TMNumber", visitInformationReportDTO.getTmWalet());
				} else {
					parameters.put("TMNumber", null);
				}

				if (visitInformationReportDTO.getDhWalet() != null) {
					parameters.put("DHNumber", visitInformationReportDTO.getDhWalet());
				} else {
					parameters.put("DHNumber", null);
				}

				if (visitInformationReportDTO.getDsoWalet() != null) {
					parameters.put("DSONumber", visitInformationReportDTO.getDsoWalet());
				} else {
					parameters.put("DSONumber", null);
				}

				if (visitInformationReportDTO.getUddoktaWalet() != null) {
					parameters.put("UddoktaNumber", visitInformationReportDTO.getUddoktaWalet());
				} else {
					parameters.put("UddoktaNumber", null);
				}

				iVisitInformationReportService.exportReport(reportSrcFilePath, parameters, reportSrcFileName3,
						reportExportformat);		
				break;
				
			case 4:
				reportSrcFilePath = reportSrcFileLocation + reportSrcFileName4 + reportSrcFileExtension;

				// Report Specific Parameter
				dateType = 2; // ENUM
				dateFormat = "dd-MMM-yyyy";
				currencyFormat = "##,##,##,###.00##";
				reportID = "1399050204";
				parameters.put("DateType", dateType);
				parameters.put("DateFormat", dateFormat);
				parameters.put("CurrencyFormat", currencyFormat);
				parameters.put("RDOREPORTNUMBER", reportID);
				// parameters.put("RDOREPORTNUMBER", ReportNumber.BDO_Uddokta_Audit_Report.value());
				parameters.put("ExportFormat", reportExportformat);

				if (visitInformationReportDTO.getFromDate() != null) {
					
					parameters.put("FromDate", visitInformationReportDTO.getFromDate());
				} else {
					//parameters.put("FromDate", null);
					parameters.put("FromDate", autoFromDate);					
				}

				if (visitInformationReportDTO.getToDate() != null) {
					parameters.put("ToDate", visitInformationReportDTO.getToDate());
				} else {
					//parameters.put("ToDate", null);
					parameters.put("ToDate", autoToDate);
				}

				parameters.put("CLUSTER", visitInformationReportDTO.getClusterName());

				// if (!visitInformationReportDTO.getRegionName().isEmpty() ||
				// visitInformationReportDTO.getRegionName()!=null) {
				if (visitInformationReportDTO.getRegionName() != null) {
					parameters.put("REGION", visitInformationReportDTO.getRegionName());
				} else {
					parameters.put("REGION", null);
				}

				if (visitInformationReportDTO.getAreaName() != null) {
					parameters.put("AREA", visitInformationReportDTO.getAreaName());
				} else {
					parameters.put("AREA", null);
				}

				if (visitInformationReportDTO.getTeritoryName() != null) {
					parameters.put("TERRITORY", visitInformationReportDTO.getTeritoryName());
				} else {
					parameters.put("TERRITORY", null);
				}

				if (visitInformationReportDTO.getTmWalet() != null) {
					parameters.put("TMNumber", visitInformationReportDTO.getTmWalet());
				} else {
					parameters.put("TMNumber", null);
				}

				if (visitInformationReportDTO.getDhWalet() != null) {
					parameters.put("DHNumber", visitInformationReportDTO.getDhWalet());
				} else {
					parameters.put("DHNumber", null);
				}

				if (visitInformationReportDTO.getDsoWalet() != null) {
					parameters.put("DSONumber", visitInformationReportDTO.getDsoWalet());
				} else {
					parameters.put("DSONumber", null);
				}

				if (visitInformationReportDTO.getUddoktaWalet() != null) {
					parameters.put("UddoktaNumber", visitInformationReportDTO.getUddoktaWalet());
				} else {
					parameters.put("UddoktaNumber", null);
				}

				iVisitInformationReportService.exportReport(reportSrcFilePath, parameters, reportSrcFileName4,
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