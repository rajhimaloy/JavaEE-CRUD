package com.banks.web.sa.uaa.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.banks.erp.library.util.context.WebMessage;
import com.banks.erp.sa.uaa.iservice.IUserGroupSetupService;
import com.banks.erp.sa.uaa.iservice.IUserSetupService;
import com.banks.erp.sa.uaa.iservice.IUserWiseGroupMapService;
import com.banks.erp.sa.uaa.model.UserGroup;
import com.banks.erp.sa.uaa.model.UserInfo;
import com.banks.erp.sa.uaa.model.UserProfile;
import com.banks.erp.sa.uaa.model.UserWiseGroupMap;

/**
 * @author Rajib Kumer Ghosh
 *
 */

@Named
@ViewScoped
public class UserSetupController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IUserSetupService iUserSetupService;

	@Inject
	private IUserGroupSetupService iUserGroupSetupService;

	@Inject
	private IUserWiseGroupMapService iUserWiseGroupMapService;

	@Inject
	private UserInfo userInfo;

	@Inject
	private UserProfile userProfile;
	
	/*@Inject
	private UserWiseGroupMap userWiseGroupMap;*/

	@Inject
	private WebMessage webMessage;

	private List<UserInfo> userSetupList;

	private List<UserInfo> userSetupFilterList;
	
	//private List<UserWiseGroupMap> userWiseGroupMapList;

		
	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}	

	/*public UserWiseGroupMap getUserWiseGroupMap() {
		return userWiseGroupMap;
	}

	public void setUserWiseGroupMap(UserWiseGroupMap userWiseGroupMap) {
		this.userWiseGroupMap = userWiseGroupMap;
	}*/	

	public WebMessage getWebMessage() {
		return webMessage;
	}

	public void setWebMessage(WebMessage webMessage) {
		this.webMessage = webMessage;
	}

	public List<UserInfo> getUserSetupList() {
		return userSetupList;
	}

	public void setUserSetupList(List<UserInfo> userSetupList) {
		this.userSetupList = userSetupList;
	}

	/*public List<UserWiseGroupMap> getUserWiseGroupMapList() {
		return userWiseGroupMapList;
	}

	public void setUserWiseGroupMapList(List<UserWiseGroupMap> userWiseGroupMapList) {
		this.userWiseGroupMapList = userWiseGroupMapList;
	}*/

	public List<UserInfo> getUserSetupFilterList() {
		return userSetupFilterList;
	}

	public void setUserSetupFilterList(List<UserInfo> userSetupFilterList) {
		this.userSetupFilterList = userSetupFilterList;
	}

	@RequiresPermissions("10010003:VIEW")
	@PostConstruct
	public void init() {
		// System.out.println("===============Customer Details===============");
		userSetupList = iUserSetupService.getUserSetupList();
	}
	
	@RequiresPermissions("10010003:SAVE")
	public void saveData() {		
		
		List<UserGroup> userGroupList = iUserGroupSetupService.getUserGroupList();

		try {
			String messageDetail = iUserSetupService.save(userInfo, userGroupList);

			// Message on Save Data
			webMessage.successMessage(messageDetail);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		userSetupList = iUserSetupService.getUserSetupList();
	}

	@RequiresPermissions("10010003:UPDATE")
	public void update() {

		try {
			String messageDetail = iUserSetupService.update(userInfo);
			
			// Message on Edit Data
			webMessage.successMessage(messageDetail);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		userSetupList = iUserSetupService.getUserSetupList();
	}

	@RequiresPermissions("10010003:DELETE")
	public void delete() {
		// System.out.println("data " + userInfo);
		List<UserWiseGroupMap> userWiseGroupMapList = iUserWiseGroupMapService.getUserWiseGroupMapList(userInfo.getUserName());

		try {
			String messageDetail = iUserSetupService.delete(userInfo, userWiseGroupMapList);	
			
			// Message on Delete Data
			webMessage.successMessage(messageDetail);		
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		userSetupList = iUserSetupService.getUserSetupList();
	}

}
