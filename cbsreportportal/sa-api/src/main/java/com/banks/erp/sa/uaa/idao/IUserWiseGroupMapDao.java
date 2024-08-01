/**
 * 
 */
package com.banks.erp.sa.uaa.idao;

import java.util.List;

import com.banks.erp.sa.uaa.model.UserWiseGroupMap;

/**
 * @author Rajib Kumer Ghosh
 *
 */
public interface IUserWiseGroupMapDao {
	
	public List<UserWiseGroupMap> getUserWiseGroupMapList(String userId);
	
	public List<UserWiseGroupMap> getUserWiseGroupMapActiveList(String userId);

	public List<UserWiseGroupMap> getUserWiseGroupMapListByGroupId(String groupId);

}
