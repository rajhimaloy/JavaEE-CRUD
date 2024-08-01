package com.banks.erp.sa.uaa.dao;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.banks.erp.sa.uaa.idao.IGroupWiseAccessPermissionDao;
import com.banks.erp.sa.uaa.model.GroupWiseAccessPermission;

/**
 * @author Rajib Kumer Ghosh
 *
 */

@Dependent
public class GroupWiseAccessPermissionDao implements IGroupWiseAccessPermissionDao {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<GroupWiseAccessPermission> getGroupWiseAccessPermissionList(String groupID) {
		String queryString = "SELECT u FROM GroupWiseAccessPermission u where u.groupID = :groupID";
		Query query = em.createQuery(queryString);
		query.setParameter("groupID", groupID);
		List<GroupWiseAccessPermission> accessPermissionList = (List<GroupWiseAccessPermission>) query.getResultList();
		return accessPermissionList;
	}

}
