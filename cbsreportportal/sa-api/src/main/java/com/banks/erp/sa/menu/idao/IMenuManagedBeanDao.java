package com.banks.erp.sa.menu.idao;

import java.util.List;

import com.banks.erp.sa.menu.model.MenuDetails;

/**
 * @author Rajib Kumer Ghosh
 *
 */
public interface IMenuManagedBeanDao {
	public List<MenuDetails> getMenuDetailsListForLevelOne(String userID);
	
	public List<MenuDetails> getMenuDetailsListForLevelTwo(String userID);
}
