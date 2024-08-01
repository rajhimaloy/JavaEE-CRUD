package com.banks.web.sa.uaa.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.banks.erp.library.util.context.WebMessage;
import com.banks.erp.sa.uaa.iservice.IUserSetupService;
import com.banks.erp.sa.uaa.iservice.IUserWiseGroupMapService;
import com.banks.erp.sa.uaa.model.UserInfo;
import com.banks.erp.sa.uaa.model.UserWiseGroupMap;

/**
 * @author Rajib Kumer Ghosh
 *
 */

@Named
@ViewScoped
public class UserWiseGroupMapController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IUserWiseGroupMapService iUserWiseGroupMapService;

	@Inject
	private IUserSetupService iUserSetupService;

	@Inject
	private UserWiseGroupMap userWiseGroupMap;

	@Inject
	private UserInfo userInfo;
	
	@Inject
	private WebMessage webMessage;	

	private List<UserWiseGroupMap> userWiseGroupMapList;

	private List<UserInfo> userSetupList;

	
	public UserWiseGroupMap getUserWiseGroupMap() {
		return userWiseGroupMap;
	}

	public void setUserWiseGroupMap(UserWiseGroupMap userWiseGroupMap) {
		this.userWiseGroupMap = userWiseGroupMap;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public List<UserWiseGroupMap> getUserWiseGroupMapList() {
		return userWiseGroupMapList;
	}

	public void setUserWiseGroupMapList(List<UserWiseGroupMap> userWiseGroupMapList) {
		this.userWiseGroupMapList = userWiseGroupMapList;
	}

	public List<UserInfo> getUserSetupList() {
		return userSetupList;
	}

	public void setUserSetupList(List<UserInfo> userSetupList) {
		this.userSetupList = userSetupList;
	}

	public WebMessage getWebMessage() {
		return webMessage;
	}

	public void setWebMessage(WebMessage webMessage) {
		this.webMessage = webMessage;
	}	
	

	@PostConstruct
	public void init() {
		// System.out.println("===============User Group Rajib Details===============");
		userSetupList = iUserSetupService.getUserSetupList();
	}

	public void handleAjaxEvent() {
		String userId = userWiseGroupMap.getUserId();
		userWiseGroupMapList = iUserWiseGroupMapService.getUserWiseGroupMapList(userId);
	}

	@RequiresPermissions("10010004:UPDATE")
	public void update() {

		try {
			String messageDetail = iUserWiseGroupMapService.update(userWiseGroupMapList);

			// Message on Edit Data
			webMessage.successMessage(messageDetail);
		} catch (Exception e) {
			e.printStackTrace();
		}

		userWiseGroupMapList.clear();
		userWiseGroupMap.setUserId(null);		
	}

}
