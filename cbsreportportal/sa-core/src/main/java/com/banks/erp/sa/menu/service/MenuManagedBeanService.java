/**
 * 
 */
package com.banks.erp.sa.menu.service;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import com.banks.erp.library.util.persistence.CollectionDao;
import com.banks.erp.sa.menu.idao.IMenuManagedBeanDao;
import com.banks.erp.sa.menu.iservice.IMenuManagedBeanService;
import com.banks.erp.sa.menu.model.MenuDetails;

/**
 * @author Rajib Kumer Ghosh
 * (raj.himaloy@gmail.com)
 * (+8801825051885)
 */

@RequestScoped
@Transactional(TxType.SUPPORTS)
public class MenuManagedBeanService implements IMenuManagedBeanService {
	
	@Inject
	private CollectionDao collectionDao;
	
	@Inject
	private IMenuManagedBeanDao menuManagedBeanDao;
		
	public List<MenuDetails> getMenuDetailsListForLevelOne(String userID) {
		List<MenuDetails> menuDetailsList = null;
		try {			
			menuDetailsList = menuManagedBeanDao.getMenuDetailsListForLevelOne(userID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return menuDetailsList;
	}

	public List<MenuDetails> getMenuDetailsListForLevelTwo(String userID) {
		List<MenuDetails> menuDetailsList = null;
		try {
			menuDetailsList = menuManagedBeanDao.getMenuDetailsListForLevelTwo(userID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return menuDetailsList;
	}

	public List<MenuDetails> getMenuDetailsListForLevelThree(String userID) {
		List<MenuDetails> menuDetailsList = null;
		try {
			menuDetailsList = collectionDao.executeQuery("SELECT m FROM MenuDetails m WHERE m.url = '#' AND m.parentID IS NOT NULL AND m.levels = 3 \r\n" + 
					"AND m.menuID IN(SELECT p.parentID FROM MenuDetails p WHERE p.menuID IN(\r\n" + 
					"SELECT DISTINCT a.screenID FROM GroupWiseAccessPermission a\r\n" + 
					"WHERE a.hasViewPermission = 1 AND a.groupID IN(SELECT g.groupId FROM UserWiseGroupMap g \r\n" + 
					"WHERE g.statusId = 'true' AND g.userId = '" + userID + "'))) ORDER BY m.menuID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return menuDetailsList;
	}

	public List<MenuDetails> getMenuDetailsListForLevelFour(String userID) {
		List<MenuDetails> menuDetailsList = null;
		try {
			menuDetailsList = collectionDao.executeQuery("SELECT m \r\n" + 
					"FROM MenuDetails m \r\n" + 
					"WHERE  m.url != '#' AND m.parentID IS NOT NULL\r\n" + 
					"AND m.menuID IN(\r\n" + 
					"	SELECT DISTINCT a.screenID\r\n" + 
					"	FROM GroupWiseAccessPermission a\r\n" + 
					"	WHERE a.hasViewPermission = 1\r\n" + 
					"	AND a.groupID IN(SELECT g.groupId FROM UserWiseGroupMap g WHERE g.statusId = 'true' AND g.userId = '" + userID + "')) ORDER BY m.menuID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return menuDetailsList;
	}

	public List<MenuDetails> getMenuDetailsListForLevelFour() {
		List<MenuDetails> menuDetailsList = null;
		try {
			menuDetailsList = collectionDao.executeQuery("SELECT m FROM MenuDetails m WHERE m.url != '#' AND m.parentID IS NOT NULL");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return menuDetailsList;
	}

}
