/**
 * 
 */
package com.banks.erp.sa.uaa.iservice;

import java.util.List;

import javax.ejb.Local;

import com.banks.erp.sa.uaa.model.UserWiseGroupMap;

/**
 * @author Rajib Kumer Ghosh
 *
 */

@Local
public interface IUserWiseGroupMapService {

	public List<UserWiseGroupMap> getUserWiseGroupMapList(String userID);

	public List<UserWiseGroupMap> getUserWiseGroupMapActiveList(String userID);

	public List<UserWiseGroupMap> getUserWiseGroupMapListByGroupId(String groupId);

	public String update(List<UserWiseGroupMap> userWiseGroupMapList) throws Exception;

	//public String delete(UserGroup userGroup) throws Exception;

}
