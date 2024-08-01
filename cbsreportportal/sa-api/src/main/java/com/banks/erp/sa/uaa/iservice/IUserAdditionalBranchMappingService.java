/**
 * 
 */
package com.banks.erp.sa.uaa.iservice;

import java.util.List;

import javax.ejb.Local;

import com.banks.erp.sa.uaa.model.UserAdditionalBranchMapping;


/**
 * @author Rajib Kumer Ghosh
 *
 */

@Local
public interface IUserAdditionalBranchMappingService {
	
	public UserAdditionalBranchMapping getUserAdditionalBranchMappingDetails(UserAdditionalBranchMapping userAdditionalBranchMapping);

	public UserAdditionalBranchMapping getUserAdditionalBranchMappingDetails(String userID);

	public List<UserAdditionalBranchMapping> getUserAdditionalBranchMappingList();

	public String save(UserAdditionalBranchMapping userAdditionalBranchMapping) throws Exception;

	public String update(UserAdditionalBranchMapping userAdditionalBranchMapping) throws Exception;

	public String delete(UserAdditionalBranchMapping userAdditionalBranchMapping) throws Exception;
}
