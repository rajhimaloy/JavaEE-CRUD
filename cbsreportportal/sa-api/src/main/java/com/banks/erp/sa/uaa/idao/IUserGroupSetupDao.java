/**
 * 
 */
package com.banks.erp.sa.uaa.idao;

import com.banks.erp.sa.uaa.model.UserGroup;

/**
 * @author Rajib Kumer Ghosh
 *
 */
public interface IUserGroupSetupDao {

	public UserGroup getUserGroupDetails(String userGroupID);
	
	public UserGroup getUserGroupDetails(UserGroup userGroup);
	
	public void delete(UserGroup userGroup) throws Exception;
}
