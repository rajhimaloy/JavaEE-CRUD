package com.banks.erp.sa.uaa.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.banks.erp.sa.uaa.idao.IUserSetupDao;
import com.banks.erp.sa.uaa.model.UserInfo;

/**
 * @author Rajib Kumer Ghosh
 *
 */
public class UserSetupDao implements IUserSetupDao {

	@PersistenceContext
	private EntityManager em;

	// Here in SELECT query(JPA query) TABLE name in Entity class name and column name is Entity class's variable name.(Like:- UserInfo, u.userName)
	@Override
	public UserInfo getUserinfo(String userName) throws Exception {
		String queryString = "SELECT u FROM UserInfo u where u.userName=:userName";
		Query query = em.createQuery(queryString);
		query.setParameter("userName", userName);
		UserInfo userInfo = (UserInfo) query.getSingleResult();
		return userInfo;

	}

	public UserInfo getUserSetupDetails(String userName) {
		Query query = em.createNativeQuery("SELECT * FROM SYS_USERINFO WHERE USERNAME = :userName", UserInfo.class);
		query.setParameter("userName", userName);
		UserInfo userInfo = (UserInfo) query.getSingleResult();
		return userInfo;
	}

	public void delete(UserInfo userInfo) throws Exception {
		userInfo = em.find(UserInfo.class, userInfo.getUserName());
		em.remove(userInfo);
		em.flush();
	}

}
