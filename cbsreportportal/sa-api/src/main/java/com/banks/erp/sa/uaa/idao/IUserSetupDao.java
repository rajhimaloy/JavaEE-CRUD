/**
 * 
 */
package com.banks.erp.sa.uaa.idao;

import com.banks.erp.sa.uaa.model.UserInfo;

/**
 * @author Rajib Kumer Ghosh
 *
 */
public interface IUserSetupDao {

	public UserInfo getUserinfo(String userName) throws Exception;
	
	public UserInfo getUserSetupDetails(String userName);
	
	public void delete(UserInfo userInfo) throws Exception;

}
