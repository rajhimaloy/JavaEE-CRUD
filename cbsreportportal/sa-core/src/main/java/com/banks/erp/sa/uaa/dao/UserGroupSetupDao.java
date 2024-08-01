package com.banks.erp.sa.uaa.dao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.banks.erp.sa.uaa.idao.IUserGroupSetupDao;
import com.banks.erp.sa.uaa.model.UserGroup;

/**
 * @author Rajib Kumer Ghosh
 *
 */

@Dependent
public class UserGroupSetupDao implements IUserGroupSetupDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public UserGroup getUserGroupDetails(String groupId) {
		Query query = em.createNativeQuery("SELECT * FROM SYS_USERGROUP WHERE GROUPID = :groupId", UserGroup.class);
		query.setParameter("groupId", groupId);
		UserGroup userGroup = (UserGroup) query.getSingleResult();
		return userGroup;
	}	
	
	@Override
	public UserGroup getUserGroupDetails(UserGroup userGroup) {
		UserGroup userGroup1 = em.find(UserGroup.class, userGroup.getGroupId());
		return userGroup1;
	}

	@Override
	public void delete(UserGroup userGroup) throws Exception {
		userGroup = em.find(UserGroup.class, userGroup.getGroupId());
		em.remove(userGroup);
		em.flush();
	}
}
