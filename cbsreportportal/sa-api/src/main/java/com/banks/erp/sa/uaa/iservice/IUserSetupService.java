/**
 * 
 */
package com.banks.erp.sa.uaa.iservice;

import java.util.List;

import javax.ejb.Local;

import com.banks.erp.sa.uaa.model.UserGroup;
import com.banks.erp.sa.uaa.model.UserInfo;
import com.banks.erp.sa.uaa.model.UserWiseGroupMap;



/**
 * @author Rajib Kumer Ghosh
 *
 */

@Local
public interface IUserSetupService {

	public UserInfo getUserinfo(String userName) throws Exception;

	public UserInfo getUserSetupDetails(UserInfo userInfo);

	public UserInfo getUserSetupDetails(String userName);

	public List<UserInfo> getUserSetupList();

	public String save(UserInfo userInfo, List<UserGroup> userGroupList) throws Exception;

	public String update(UserInfo userInfo) throws Exception;

	public String delete(UserInfo userInfo, List<UserWiseGroupMap> userWiseGroupMapList) throws Exception;
	
	//--------- New One -----------
	/*public List<UserProfile> getUserProfileList(String branchCode, String statusId);

	public List<UserProfile> getUserProfileList(String branchCode, int first, int pageSize);
	
	public void save(UserInfo user) throws Exception;

	public void update(UserInfo user) throws Exception;*/

}
