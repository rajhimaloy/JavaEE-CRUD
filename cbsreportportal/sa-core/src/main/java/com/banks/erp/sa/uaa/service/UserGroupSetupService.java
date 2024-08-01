package com.banks.erp.sa.uaa.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import com.banks.erp.library.util.persistence.CollectionDao;
import com.banks.erp.library.util.persistence.PersistenceDao;
import com.banks.erp.library.util.util.CommonUtilService;
import com.banks.erp.sa.menu.model.MenuDetails;
import com.banks.erp.sa.uaa.idao.IUserGroupSetupDao;
import com.banks.erp.sa.uaa.iservice.IUserGroupSetupService;
import com.banks.erp.sa.uaa.model.GroupWiseAccessPermission;
import com.banks.erp.sa.uaa.model.GroupWiseAccessPermission.GroupWiseAccessPermissionPK;
import com.banks.erp.sa.uaa.model.UserGroup;
import com.banks.erp.sa.uaa.model.UserInfo;
import com.banks.erp.sa.uaa.model.UserWiseGroupMap;
import com.banks.erp.sa.uaa.model.UserWiseGroupMap.UserWiseGroupMapPK;

/**
 * @author Rajib Kumer Ghosh
 *
 */

@RequestScoped
@Transactional(TxType.SUPPORTS)
public class UserGroupSetupService implements IUserGroupSetupService {

	@Inject
	private IUserGroupSetupDao iUserGroupSetupDao;

	@Inject
	private PersistenceDao persistenceDao;

	@Inject
	private CollectionDao collectionDao;

	public UserGroup getUserGroupDetails1(UserGroup userGroup) {
		// return iUserGroupSetupDao.getUserGroupDetails(userGroup);

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userName", userGroup.getGroupName());
		UserGroup userGroupRs = null;
		try {
			// userGroupRs = collectionDao.selectSingle("UserGroup.findByUserName", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userGroupRs;
	}

	@Override
	public UserGroup getUserGroupDetails(String userGroupID) {
		return iUserGroupSetupDao.getUserGroupDetails(userGroupID);
	}

	@Override
	public UserGroup getUserGroupDetails(UserGroup userGroup) {
		return iUserGroupSetupDao.getUserGroupDetails(userGroup);
	}

	@Override
	public List<UserGroup> getUserGroupList() {
		List<UserGroup> userGroupList = null;
		try {
			userGroupList = collectionDao.executeQuery("SELECT u FROM UserGroup u");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userGroupList;
	}

	private void resetEntityUserGroup(UserGroup userGroup) throws Exception {
		userGroup.setGroupId(null);
		userGroup.setGroupName(null);
		userGroup.setGroupCreationDate(null);
		userGroup.setStatusId(null);
		userGroup.setCreationDate(null);
		userGroup.setCreatedBy(null);
		userGroup.setModifiedBy(null);
		userGroup.setModificationDate(null);
		// userGroup.setVersion(null);
	}

	@Transactional(TxType.REQUIRED)
	@Override
	public String save(UserGroup userGroup, List<UserInfo> userInfoList, List<MenuDetails> menuDetailsList) throws Exception {

		// Message on Method Return
		String successMessage = "User Group has been Saved Successfully";
		String failedMessage = "Wrong Information, Operation has been Failed";
		String validationMessage = "Group allready exist, Operation has been Failed";

		try {
			UserInfo loggedInUser = CommonUtilService.getCurrentUserInfo();
			
			// Checking for Already Exist or Not
			Boolean userGroupIsExist = collectionDao.findByID(UserGroup.class, userGroup.getGroupId());
			
			if (userGroupIsExist) {
				resetEntityUserGroup(userGroup);
				return validationMessage;
			} 
			
			List<GroupWiseAccessPermission> groupWiseAccessPermissionList = new ArrayList<>();
			for(MenuDetails menuDetails: menuDetailsList) {
				GroupWiseAccessPermission groupWiseAccessPermission = new GroupWiseAccessPermission();
				groupWiseAccessPermission.setGroupID(userGroup.getGroupId());
				groupWiseAccessPermission.setScreenID(menuDetails.getMenuID());
				groupWiseAccessPermission.setScreenName(menuDetails.getMenuName());
				groupWiseAccessPermission.setHasDeletePermission(false);
				groupWiseAccessPermission.setHasSavePermission(false);
				groupWiseAccessPermission.setHasUpdatePermission(false);
				groupWiseAccessPermission.setHasViewPermission(false);
				groupWiseAccessPermission.setStatusId("true");
				groupWiseAccessPermission.setCreatedBy(loggedInUser.getUserName());
				groupWiseAccessPermission.setCreationDate(CommonUtilService.getSystemOpenDate());
				
				groupWiseAccessPermissionList.add(groupWiseAccessPermission);
			}
			
			List<UserWiseGroupMap> userWiseGroupMapList = new ArrayList<>();
			for(UserInfo userInfo : userInfoList) {
				UserWiseGroupMap userWiseGroupMap = new UserWiseGroupMap();
				userWiseGroupMap.setUserId(userInfo.getUserId());
				userWiseGroupMap.setGroupId(userGroup.getGroupId());
				userWiseGroupMap.setGroupName(userGroup.getGroupName());
				userWiseGroupMap.setStatusId("false");
				userWiseGroupMap.setCreatedBy(loggedInUser.getUserName());
				userWiseGroupMap.setCreationDate(CommonUtilService.getSystemOpenDate());
				
				userWiseGroupMapList.add(userWiseGroupMap);
			}

			userGroup.setCreatedBy(loggedInUser.getUserName());
			userGroup.setCreationDate(CommonUtilService.getSystemOpenDate());
			
			persistenceDao.save(groupWiseAccessPermissionList);
			persistenceDao.save(userWiseGroupMapList);			
			persistenceDao.save(userGroup);
			
		} catch (Exception e) {
			e.printStackTrace();

			resetEntityUserGroup(userGroup);
			return failedMessage;
		}

		resetEntityUserGroup(userGroup);
		return successMessage;
	}

	@Transactional(TxType.REQUIRED)
	@Override
	public String update(UserGroup userGroup) throws Exception {

		// Message on Method Return
		String successMessage = "User Group has been Updated Successfully";
		String failedMessage = "Wrong Information, Operation has been Failed";
		String validationMessage = "User Group doesn't exist, Operation has been Failed";

		try {
			UserInfo loggedInUser = CommonUtilService.getCurrentUserInfo();
			
			// Checking for Already Exist or Not
			Boolean userGroupIsExist = collectionDao.findByID(UserGroup.class, userGroup.getGroupId());
			
			if (!userGroupIsExist) {
				resetEntityUserGroup(userGroup);
				return validationMessage;
			} 

			// Penetration checking for hacking
			UserGroup userGroupEdit = collectionDao.find(UserGroup.class, userGroup.getGroupId());
			userGroupEdit.setGroupName(userGroup.getGroupName());
			userGroupEdit.setStatusId(userGroup.getStatusId());
			userGroupEdit.setGroupCreationDate(userGroup.getGroupCreationDate());
			userGroupEdit.setModifiedBy(loggedInUser.getUserName());
			userGroupEdit.setModificationDate(CommonUtilService.getSystemOpenDate());
			// userGroup.setVersionNo(userGroupExisting.getVersionNo() + 1);

			persistenceDao.update(userGroupEdit);
		} catch (Exception e) {
			e.printStackTrace();

			resetEntityUserGroup(userGroup);
			return failedMessage;
		}

		resetEntityUserGroup(userGroup);
		return successMessage;
	}

	@Override
	@Transactional(TxType.REQUIRED)
	public String delete(List<GroupWiseAccessPermission> groupWiseAccessPermissionList, List<UserWiseGroupMap> userWiseGroupMapList, UserGroup userGroup) throws Exception {

		// Message on Method Return
		String successMessage = "User Group has been Deleted Successfully";
		String failedMessage = "Wrong Information, Operation has been Failed";
		String validationMessage = "User Group doesn't exist, Operation has been Failed";

		try {			
			// Checking for Already Exist or Not
			Boolean userGroupIsExist = collectionDao.findByID(UserGroup.class, userGroup.getGroupId());
			
			if (!userGroupIsExist) {
				resetEntityUserGroup(userGroup);
				return validationMessage;
			} 
			// Penetration checking
			for(GroupWiseAccessPermission groupWiseAccessPermission : groupWiseAccessPermissionList) {
				GroupWiseAccessPermissionPK groupWiseAccessPermissionPK = new GroupWiseAccessPermissionPK();
				groupWiseAccessPermissionPK.setGroupID(groupWiseAccessPermission.getGroupID());
				groupWiseAccessPermissionPK.setScreenID(groupWiseAccessPermission.getScreenID());
				
				GroupWiseAccessPermission groupWiseAccessPermission1 = collectionDao.find(GroupWiseAccessPermission.class, groupWiseAccessPermissionPK);
				persistenceDao.delete(groupWiseAccessPermission1);
			}
			
			// Penetration checking
			for(UserWiseGroupMap userWiseGroupMap : userWiseGroupMapList) {
				UserWiseGroupMapPK userWiseGroupMapPK = new UserWiseGroupMapPK();
				userWiseGroupMapPK.setGroupId(userWiseGroupMap.getGroupId());
				userWiseGroupMapPK.setUserId(userWiseGroupMap.getUserId());
				
				UserWiseGroupMap userWiseGroupMap1 = collectionDao.find(UserWiseGroupMap.class, userWiseGroupMapPK);
				persistenceDao.delete(userWiseGroupMap1);
			}
			
			// Penetration checking
			UserGroup userGroupDelete = collectionDao.find(UserGroup.class, userGroup.getGroupId());
			//iUserGroupSetupDao.delete(userGroupDelete);
			persistenceDao.delete(userGroupDelete);
		} catch (Exception e) {
			e.printStackTrace();

			resetEntityUserGroup(userGroup);
			return failedMessage;
		}

		resetEntityUserGroup(userGroup);
		return successMessage;
	}
}
