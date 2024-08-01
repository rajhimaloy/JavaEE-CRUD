package com.banks.erp.sa.uaa.iservice;

import java.util.List;

import javax.ejb.Local;

import com.banks.erp.sa.menu.model.MenuDetails;
import com.banks.erp.sa.uaa.model.GroupWiseAccessPermission;
import com.banks.erp.sa.uaa.model.UserGroup;
import com.banks.erp.sa.uaa.model.UserInfo;
import com.banks.erp.sa.uaa.model.UserWiseGroupMap;

/**
 * @author Rajib Kumer Ghosh
 *
 */

@Local
public interface IUserGroupSetupService {
	
	public UserGroup getUserGroupDetails(UserGroup userGroup);

	public UserGroup getUserGroupDetails(String userGroupID);

	public List<UserGroup> getUserGroupList();

	public String save(UserGroup userGroup, List<UserInfo> userInfoList, List<MenuDetails> menuDetailsList) throws Exception;

	public String update(UserGroup userGroup) throws Exception;

	public String delete(List<GroupWiseAccessPermission> groupWiseAccessPermissionList,
			List<UserWiseGroupMap> userWiseGroupMapList, UserGroup userGroup) throws Exception;
}

