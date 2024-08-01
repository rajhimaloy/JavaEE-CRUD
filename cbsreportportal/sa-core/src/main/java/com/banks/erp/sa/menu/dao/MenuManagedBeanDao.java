package com.banks.erp.sa.menu.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.banks.erp.sa.menu.idao.IMenuManagedBeanDao;
import com.banks.erp.sa.menu.model.MenuDetails;

/**
 * @author Rajib Kumer Ghosh
 * (raj.himaloy@gmail.com)
 * (+8801825051885)
 */

public class MenuManagedBeanDao implements IMenuManagedBeanDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<MenuDetails> getMenuDetailsListForLevelOne(String userID) {
		List<MenuDetails> result = new ArrayList<>();
		Query query = em.createNativeQuery("SELECT * FROM (SELECT m.* FROM sys_menu_details m WHERE m.url = '#' AND m.ParentID IS NULL AND m.levels = 1\r\n" + 
				"AND m.menuID IN(SELECT m.ParentID FROM sys_menu_details m WHERE m.url = '#' AND m.ParentID IS NOT NULL AND m.levels = 2\r\n" + 
				"AND m.menuID IN(SELECT p.ParentID FROM sys_menu_details p WHERE p.menuID IN(\r\n" + 
				"SELECT DISTINCT a.screenID FROM SYS_GROUPWISEACCESSPERMISSION a	WHERE a.hasViewPermission = 1\r\n" + 
				"AND a.groupID IN(SELECT g.groupId FROM sys_userwisegroupmap g WHERE g.statusId = 'true' AND g.userId = :userID))))\r\n" + 
				"UNION\r\n" + 
				"SELECT m.* FROM sys_menu_details m WHERE m.url = '#' AND m.ParentID IS NULL AND m.levels = 1\r\n" + 
				"AND m.menuID IN(SELECT m.ParentID FROM sys_menu_details m WHERE m.url = '#' AND m.levels = 2\r\n" + 
				"AND m.menuID IN(SELECT m.ParentID FROM sys_menu_details m WHERE m.url = '#' AND m.levels = 3\r\n" + 
				"AND m.menuID IN(SELECT p.ParentID FROM sys_menu_details p WHERE p.menuID IN(\r\n" + 
				"SELECT DISTINCT a.screenID FROM SYS_GROUPWISEACCESSPERMISSION a	WHERE a.hasViewPermission = 1\r\n" + 
				"AND a.groupID IN(SELECT g.groupId FROM sys_userwisegroupmap g WHERE g.statusId = 'true' AND g.userId = :userID)))))) ORDER BY menuid", MenuDetails.class);
		query.setParameter("userID", userID);
		result = query.getResultList();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<MenuDetails> getMenuDetailsListForLevelTwo(String userID) {
		List<MenuDetails> result = new ArrayList<>();
		Query query = em.createNativeQuery("SELECT * FROM (SELECT m.* FROM sys_menu_details m WHERE m.url = '#' AND m.ParentID IS NOT NULL AND m.levels = 2\r\n" + 
				"AND m.MenuID IN(SELECT p.ParentID FROM sys_menu_details p WHERE p.MenuID IN(\r\n" + 
				"		SELECT DISTINCT a.SCREENID FROM SYS_GROUPWISEACCESSPERMISSION a WHERE a.hasviewpermission = 1\r\n" + 
				"		AND a.GROUPID IN(SELECT g.GROUPID FROM sys_userwisegroupmap g WHERE g.STATUSID = 'true' AND g.USERID = :userID)))\r\n" + 
				"UNION\r\n" + 
				"SELECT m.* FROM sys_menu_details m WHERE m.url = '#' AND m.ParentID IS NOT NULL AND m.levels = 2\r\n" + 
				"AND m.MenuID IN(SELECT m.ParentID FROM sys_menu_details m WHERE m.url = '#' AND m.levels = 3\r\n" + 
				"	AND m.MenuID IN(SELECT p.ParentID FROM sys_menu_details p WHERE p.MenuID IN(\r\n" + 
				"			SELECT DISTINCT a.SCREENID FROM SYS_GROUPWISEACCESSPERMISSION a WHERE a.hasviewpermission = 1\r\n" + 
				"			AND a.GROUPID IN(SELECT g.GROUPID FROM sys_userwisegroupmap g WHERE g.STATUSID = 'true' AND g.USERID = :userID))))) ORDER BY menuid", MenuDetails.class);
		query.setParameter("userID", userID);
		result = query.getResultList();
		return result;
	}
}
