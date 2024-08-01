/**
 * 
 */
package com.banks.web.sales.tmr.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.banks.erp.library.util.dto.DropdownDTO;
import com.banks.erp.library.util.util.CommonUtilService;
import com.banks.erp.sa.uaa.model.UserInfo;
import com.banks.erp.sales.tmr.dto.PosmInformationReportDTO;
import com.banks.erp.sales.tmr.iservice.IPosmInformationReportService;

import net.sf.jasperreports.engine.JRException;

/**
 * @author Rajib_Ghosh
 *
 */
@Named
@ViewScoped
public class PosmInformationReportController implements Serializable {

	private static final long serialVersionUID = 1L;

	// Report JRXML path
	String reportSrcFileLocation = "/reports/RPT_DEV/sales/tmr/posm/";
	String reportSrcFileExtension = ".jrxml";
	String reportSrcFileName1 = "BP Registration Audit Report";
	String reportSrcFileName2 = "DSO Monthly Attendance Summery Report";
	String reportSrcFileName3 = "STR Audit Report";

	@Inject
	private IPosmInformationReportService iPosmInformationReportService;
	
	@Inject
	private CommonUtilService commonUtilService; 
	
	@Inject
	private PosmInformationReportDTO posmInformationReportDTO;
	
	private UserInfo currentUser;
	
	private List<DropdownDTO> dropdownDTOClusterList;
	
	private List<DropdownDTO> dropdownDTORegionList;
	
	private List<DropdownDTO> dropdownDTOAreaList;
	
	private List<DropdownDTO> dropdownDTOTeritoryList;
	

	public PosmInformationReportDTO getPosmInformationReportDTO() {
		return posmInformationReportDTO;
	}

	public void setPosmInformationReportDTO(PosmInformationReportDTO posmInformationReportDTO) {
		this.posmInformationReportDTO = posmInformationReportDTO;
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
		posmInformationReportDTO.setReportSelection(1);
		posmInformationReportDTO.setReportExportFormat(1);
		
		//For - Logged In User
		currentUser = commonUtilService.getCurrentUser();
		
		//For - Branch Code Dropdown List		
		//dropdownDTOBranchList = commonUtilService.getBranchDropdownList(currentUser.getUserName(), currentUser.getDefaultBranchCode(), DropdownDTO.class);
		
		//For - Cluster Code Dropdown List		
		dropdownDTOClusterList = commonUtilService.getClusterDropdownList(currentUser.getUserName(), currentUser.getDefaultBranchCode(), DropdownDTO.class);
		
	}	

	public void getRegionDropdownList() {
		dropdownDTORegionList = commonUtilService.getRegionDropdownList(posmInformationReportDTO.getClusterName(), DropdownDTO.class);
	}	

	public void getAreaDropdownList() {
		dropdownDTOAreaList = commonUtilService.getAreaDropdownList(posmInformationReportDTO.getRegionName(), DropdownDTO.class);
	}

	public void getTeritoryDropdownList() {
		dropdownDTOTeritoryList = commonUtilService.getTeritoryDropdownList(posmInformationReportDTO.getAreaName(), DropdownDTO.class);
	}
	
	public void exportReport() throws JRException, IOException {
		
	}

}
