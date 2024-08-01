package com.banks.web.sa.uaa.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.banks.erp.library.util.context.WebMessage;
import com.banks.erp.sa.uaa.iservice.IGroupWiseAccessPermissionService;
import com.banks.erp.sa.uaa.model.GroupWiseAccessPermission;

/**
 * @author Rajib Kumer Ghosh
 *
 */

@Named
@ViewScoped
public class GroupWiseAccessPermissionController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IGroupWiseAccessPermissionService iGroupWiseAccessPermissionService;

	@Inject
	private WebMessage webMessage;

	@Inject
	private GroupWiseAccessPermission groupWiseAccessPermission;
	
	private List<GroupWiseAccessPermission> groupWiseAccessPermissionList;

	
	public WebMessage getWebMessage() {
		return webMessage;
	}

	public void setWebMessage(WebMessage webMessage) {
		this.webMessage = webMessage;
	}

	public GroupWiseAccessPermission getGroupWiseAccessPermission() {
		return groupWiseAccessPermission;
	}

	public void setGroupWiseAccessPermission(GroupWiseAccessPermission groupWiseAccessPermission) {
		this.groupWiseAccessPermission = groupWiseAccessPermission;
	}
		
	public List<GroupWiseAccessPermission> getGroupWiseAccessPermissionList() {
		return groupWiseAccessPermissionList;
	}

	public void setGroupWiseAccessPermissionList(List<GroupWiseAccessPermission> groupWiseAccessPermissionList) {
		this.groupWiseAccessPermissionList = groupWiseAccessPermissionList;
	}
	

	@PostConstruct
	public void init() {
		// System.out.println("===============User Group Rajib Details===============");
		//groupWiseAccessPermissionList = iGroupWiseAccessPermissionService.getGroupWiseAccessPermissionList(groupID);
	}

	public void handleAjaxEvent() {
		String groupID = groupWiseAccessPermission.getGroupID();
		groupWiseAccessPermissionList = iGroupWiseAccessPermissionService.getGroupWiseAccessPermissionList(groupID);
	}
	
	@RequiresPermissions("10010002:UPDATE")
	public void update() {

		try {			
			String messageDetail = iGroupWiseAccessPermissionService.update(groupWiseAccessPermissionList);

			// Message on Edit Data
			webMessage.successMessage(messageDetail);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//groupWiseAccessPermissionList = iGroupWiseAccessPermissionService.getGroupWiseAccessPermissionList(groupID);
		groupWiseAccessPermissionList.clear();
		groupWiseAccessPermission.setGroupID(null);		
	}

}
