package com.banks.erp.sa.uaa.iservice;

import java.util.List;

import com.banks.erp.sa.uaa.model.GroupWiseAccessPermission;

/**
 * @author Rajib Kumer Ghosh
 *
 */

public interface IGroupWiseAccessPermissionService {

	public List<GroupWiseAccessPermission> getGroupWiseAccessPermissionList(String groupID);

	public String update(List<GroupWiseAccessPermission> groupWiseAccessPermissionList) throws Exception;

}
