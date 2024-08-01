/**
 * 
 */
package com.banks.web.sa.uaa.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.banks.erp.library.util.context.WebMessage;
import com.banks.erp.sa.uaa.iservice.IUserAdditionalBranchMappingService;
import com.banks.erp.sa.uaa.model.UserAdditionalBranchMapping;

/**
 * @author Rajib Kumer Ghosh
 *
 */

@Named
@ViewScoped
public class UserAdditionalBranchMappingController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private IUserAdditionalBranchMappingService iUserAdditionalBranchMappingService;

	@Inject
	private UserAdditionalBranchMapping userAdditionalBranchMapping;

	@Inject
	private WebMessage webMessage;

	private List<UserAdditionalBranchMapping> userAdditionalBranchMappingList;

	private List<UserAdditionalBranchMapping> userAdditionalBranchMappingFilterList;

	public UserAdditionalBranchMapping getUserAdditionalBranchMapping() {
		return userAdditionalBranchMapping;
	}

	public void setUserAdditionalBranchMapping(UserAdditionalBranchMapping userAdditionalBranchMapping) {
		this.userAdditionalBranchMapping = userAdditionalBranchMapping;
	}

	public WebMessage getWebMessage() {
		return webMessage;
	}

	public void setWebMessage(WebMessage webMessage) {
		this.webMessage = webMessage;
	}

	public List<UserAdditionalBranchMapping> getUserAdditionalBranchMappingList() {
		return userAdditionalBranchMappingList;
	}

	public void setUserAdditionalBranchMappingList(List<UserAdditionalBranchMapping> userAdditionalBranchMappingList) {
		this.userAdditionalBranchMappingList = userAdditionalBranchMappingList;
	}

	public List<UserAdditionalBranchMapping> getUserAdditionalBranchMappingFilterList() {
		return userAdditionalBranchMappingFilterList;
	}

	public void setUserAdditionalBranchMappingFilterList(
			List<UserAdditionalBranchMapping> userAdditionalBranchMappingFilterList) {
		this.userAdditionalBranchMappingFilterList = userAdditionalBranchMappingFilterList;
	}
	

	@PostConstruct	
	public void init() {
		//System.out.println("===============User Group Rajib Kumer Ghosh Details===============");
		userAdditionalBranchMappingList = iUserAdditionalBranchMappingService.getUserAdditionalBranchMappingList();
	}

	@RequiresPermissions("10010008:SAVE")
	public void saveData() {
		
		try {
			String messageDetail = iUserAdditionalBranchMappingService.save(userAdditionalBranchMapping);
			
			// Message on Save Data
			webMessage.successMessage(messageDetail);			
		} catch (Exception e) {
			e.printStackTrace();
		}

		userAdditionalBranchMappingList = iUserAdditionalBranchMappingService.getUserAdditionalBranchMappingList();
	}

	@RequiresPermissions("10010008:UPDATE")
	public void update() {

		try {
			String messageDetail = iUserAdditionalBranchMappingService.update(userAdditionalBranchMapping);
			
			// Message on Edit Data
			webMessage.successMessage(messageDetail);
		} catch (Exception e) {
			e.printStackTrace();
		}	

		userAdditionalBranchMappingList = iUserAdditionalBranchMappingService.getUserAdditionalBranchMappingList();
	}

	@RequiresPermissions("10010008:DELETE")
	public void delete() {
		// System.out.println("data " + userGroup);
		
		try {
			
			String messageDetail = iUserAdditionalBranchMappingService.delete(userAdditionalBranchMapping);				
			
			// Message on Delete Data
			webMessage.successMessage(messageDetail);		
		} catch (Exception e) {
			e.printStackTrace();
		}

		userAdditionalBranchMappingList = iUserAdditionalBranchMappingService.getUserAdditionalBranchMappingList();
	}
	

}
