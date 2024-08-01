/**
 * 
 */
package com.banks.erp.sa.uaa.dao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.banks.erp.sa.uaa.idao.IUserAdditionalBranchMappingDao;
import com.banks.erp.sa.uaa.model.UserAdditionalBranchMapping;
import com.banks.erp.sa.uaa.model.UserAdditionalBranchMapping.UserAdditionalBranchMappingPK;

/**
 * @author Rajib Kumer Ghosh
 *
 */

@Dependent
public class UserAdditionalBranchMappingDao implements IUserAdditionalBranchMappingDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public UserAdditionalBranchMapping getUserAdditionalBranchMappingDetails(String userId) {
		Query query = em.createNativeQuery("SELECT * FROM SYS_USERADDITIONALBRANCHMAP WHERE USERID = :userId", UserAdditionalBranchMapping.class);
		query.setParameter("userId", userId);
		UserAdditionalBranchMapping userAdditionalBranchMapping = (UserAdditionalBranchMapping) query.getSingleResult();
		return userAdditionalBranchMapping;
	}	
	
	@Override
	public UserAdditionalBranchMapping getUserAdditionalBranchMappingDetails(UserAdditionalBranchMappingPK userAdditionalBranchMappingPK) {
		UserAdditionalBranchMapping userAdditionalBranchMapping1 = em.find(UserAdditionalBranchMapping.class, userAdditionalBranchMappingPK);
		return userAdditionalBranchMapping1;
	}

}
