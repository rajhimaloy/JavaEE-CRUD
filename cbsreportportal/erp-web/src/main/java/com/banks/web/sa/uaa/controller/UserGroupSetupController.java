package com.banks.web.sa.uaa.controller;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.banks.erp.library.util.context.WebMessage;
import com.banks.erp.sa.menu.iservice.IMenuManagedBeanService;
import com.banks.erp.sa.menu.model.MenuDetails;
import com.banks.erp.sa.uaa.iservice.IGroupWiseAccessPermissionService;
import com.banks.erp.sa.uaa.iservice.IUserGroupSetupService;
import com.banks.erp.sa.uaa.iservice.IUserSetupService;
import com.banks.erp.sa.uaa.iservice.IUserWiseGroupMapService;
import com.banks.erp.sa.uaa.model.GroupWiseAccessPermission;
import com.banks.erp.sa.uaa.model.UserGroup;
import com.banks.erp.sa.uaa.model.UserInfo;
import com.banks.erp.sa.uaa.model.UserWiseGroupMap;

/**
 * @author Rajib Kumer Ghosh
 *
 */

@Named
@ViewScoped
public class UserGroupSetupController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private IUserGroupSetupService iUserGroupSetupService;

	@Inject
	private IUserSetupService iUserSetupService;
	
	@Inject
	private IGroupWiseAccessPermissionService iGroupWiseAccessPermissionService;

	@Inject
	private IUserWiseGroupMapService iUserWiseGroupMapService;

	@Inject
	private IMenuManagedBeanService menuManagedBeanService;

	@Inject
	private UserGroup userGroup;

	@Inject
	private WebMessage webMessage;

	private List<UserGroup> userGroupList;

	private List<UserGroup> userGroupFilterList;

	public UserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	public WebMessage getWebMessage() {
		return webMessage;
	}

	public void setWebMessage(WebMessage webMessage) {
		this.webMessage = webMessage;
	}

	public List<UserGroup> getUserGroupList() {
		return userGroupList;
	}

	public void setUserGroupList(List<UserGroup> userGroupList) {
		this.userGroupList = userGroupList;
	}

	public List<UserGroup> getUserGroupFilterList() {
		return userGroupFilterList;
	}

	public void setUserGroupFilterList(List<UserGroup> userGroupFilterList) {
		this.userGroupFilterList = userGroupFilterList;
	}

	@PostConstruct	
	public void init() {
		//System.out.println("===============User Group Rajib Kumer Ghosh Details===============");
		userGroupList = iUserGroupSetupService.getUserGroupList();
	}	

	@RequiresPermissions("10010001:SAVE")
	public void saveData() {
		
		List<UserInfo> userInfoList = iUserSetupService.getUserSetupList();
		List<MenuDetails> menuDetailsList = menuManagedBeanService.getMenuDetailsListForLevelFour();		

		try {
			String messageDetail = iUserGroupSetupService.save(userGroup, userInfoList, menuDetailsList);
			
			// Message on Save Data
			webMessage.successMessage(messageDetail);			
		} catch (Exception e) {
			e.printStackTrace();
		}

		userGroupList = iUserGroupSetupService.getUserGroupList();
	}

	@RequiresPermissions("10010001:UPDATE")
	public void update() {

		try {
			String messageDetail = iUserGroupSetupService.update(userGroup);
			
			// Message on Edit Data
			webMessage.successMessage(messageDetail);
		} catch (Exception e) {
			e.printStackTrace();
		}	

		userGroupList = iUserGroupSetupService.getUserGroupList();
	}

	@RequiresPermissions("10010001:DELETE")
	public void delete() {
		// System.out.println("data " + userGroup);
		
		List<GroupWiseAccessPermission> groupWiseAccessPermissionList =  iGroupWiseAccessPermissionService.getGroupWiseAccessPermissionList(userGroup.getGroupId());
		List<UserWiseGroupMap> userWiseGroupMapList = iUserWiseGroupMapService.getUserWiseGroupMapListByGroupId(userGroup.getGroupId());

		try {
			
			String messageDetail = iUserGroupSetupService.delete(groupWiseAccessPermissionList, userWiseGroupMapList, userGroup);				
			
			// Message on Delete Data
			webMessage.successMessage(messageDetail);		
		} catch (Exception e) {
			e.printStackTrace();
		}

		userGroupList = iUserGroupSetupService.getUserGroupList();
	}

}
