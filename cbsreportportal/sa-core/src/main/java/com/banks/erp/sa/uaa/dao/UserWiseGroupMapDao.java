package com.banks.erp.sa.uaa.dao;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.banks.erp.sa.uaa.idao.IUserWiseGroupMapDao;
import com.banks.erp.sa.uaa.model.UserWiseGroupMap;

/**
 * @author Rajib Kumer Ghosh
 *
 */

@Dependent
public class UserWiseGroupMapDao implements IUserWiseGroupMapDao {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<UserWiseGroupMap> getUserWiseGroupMapList(String userId) {
		String queryString = "SELECT u FROM UserWiseGroupMap u where u.userId = :userId";
		Query query = em.createQuery(queryString);
		query.setParameter("userId", userId);
		List<UserWiseGroupMap> userWiseGroupMapList = (List<UserWiseGroupMap>) query.getResultList();
		return userWiseGroupMapList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserWiseGroupMap> getUserWiseGroupMapActiveList(String userId) {
		String queryString = "SELECT u FROM UserWiseGroupMap u where u.userId = :userId AND u.statusId = 'true'";
		Query query = em.createQuery(queryString);
		query.setParameter("userId", userId);
		List<UserWiseGroupMap> userWiseGroupMapList = (List<UserWiseGroupMap>) query.getResultList();
		return userWiseGroupMapList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserWiseGroupMap> getUserWiseGroupMapListByGroupId(String groupId) {
		String queryString = "SELECT u FROM UserWiseGroupMap u where u.groupId = :groupId";
		Query query = em.createQuery(queryString);
		query.setParameter("groupId", groupId);
		List<UserWiseGroupMap> userWiseGroupMapList = (List<UserWiseGroupMap>) query.getResultList();
		return userWiseGroupMapList;
	}
}
