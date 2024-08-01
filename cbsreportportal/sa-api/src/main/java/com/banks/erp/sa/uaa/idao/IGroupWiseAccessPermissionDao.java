/**
 * 
 */
package com.banks.erp.sa.uaa.idao;

import java.util.List;

import com.banks.erp.sa.uaa.model.GroupWiseAccessPermission;

/**
 * @author Rajib Kumer Ghosh
 *
 */
public interface IGroupWiseAccessPermissionDao {
	
	public List<GroupWiseAccessPermission> getGroupWiseAccessPermissionList(String groupID);

}
