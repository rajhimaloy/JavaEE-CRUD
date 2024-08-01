package com.banks.erp.sa.menu.iservice;

import java.util.List;

import com.banks.erp.sa.menu.model.MenuDetails;

/**
 * @author Rajib Kumer Ghosh
 *
 */
public interface IMenuManagedBeanService {
	public List<MenuDetails> getMenuDetailsListForLevelOne(String userID);
	
	public List<MenuDetails> getMenuDetailsListForLevelTwo(String userID);
	
	public List<MenuDetails> getMenuDetailsListForLevelThree(String userID);
	
	public List<MenuDetails> getMenuDetailsListForLevelFour(String userID);
	
	public List<MenuDetails> getMenuDetailsListForLevelFour();
	
}
