/**
 * 
 */
package com.banks.erp.sa.uaa.idao;

import com.banks.erp.sa.uaa.model.UserAdditionalBranchMapping;
import com.banks.erp.sa.uaa.model.UserAdditionalBranchMapping.UserAdditionalBranchMappingPK;

/**
 * @author Rajib Kumer Ghosh
 *
 */
public interface IUserAdditionalBranchMappingDao {

	public UserAdditionalBranchMapping getUserAdditionalBranchMappingDetails(String userId);
	
	public UserAdditionalBranchMapping getUserAdditionalBranchMappingDetails(UserAdditionalBranchMappingPK userAdditionalBranchMappingPK);

}
