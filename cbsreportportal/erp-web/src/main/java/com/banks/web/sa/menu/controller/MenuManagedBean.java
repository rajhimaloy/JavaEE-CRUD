package com.banks.web.sa.menu.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSeparator;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuModel;

import com.banks.erp.sa.menu.iservice.IMenuManagedBeanService;
import com.banks.erp.sa.menu.model.MenuDetails;
import com.banks.erp.sa.uaa.model.UserInfo;

/**
 * @author Rajib Kumer Ghosh (raj.himaloy@gmail.com) (+8801825051885)
 */

@Named
@ViewScoped
public class MenuManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IMenuManagedBeanService menuManagedBeanService;

	@Inject
	private MenuDetails menuDetails;

	private List<MenuDetails> menuDetailsLevelOneList;
	private List<MenuDetails> menuDetailsLevelTwoList;
	private List<MenuDetails> menuDetailsLevelThreeList;
	private List<MenuDetails> menuDetailsLevelFourList;

	private MenuModel menu = new DefaultMenuModel();

	public MenuDetails getMenuDetails() {
		return menuDetails;
	}

	public void setMenuDetails(MenuDetails menuDetails) {
		this.menuDetails = menuDetails;
	}

	public MenuModel getMenu() {
		return menu;
	}

	public void setMenu(MenuModel menu) {
		this.menu = menu;
	}

	public List<MenuDetails> getMenuDetailsLevelOneList() {
		return menuDetailsLevelOneList;
	}

	public void setMenuDetailsLevelOneList(List<MenuDetails> menuDetailsLevelOneList) {
		this.menuDetailsLevelOneList = menuDetailsLevelOneList;
	}

	public List<MenuDetails> getMenuDetailsLevelTwoList() {
		return menuDetailsLevelTwoList;
	}

	public void setMenuDetailsLevelTwoList(List<MenuDetails> menuDetailsLevelTwoList) {
		this.menuDetailsLevelTwoList = menuDetailsLevelTwoList;
	}

	public List<MenuDetails> getMenuDetailsLevelThreeList() {
		return menuDetailsLevelThreeList;
	}

	public void setMenuDetailsLevelThreeList(List<MenuDetails> menuDetailsLevelThreeList) {
		this.menuDetailsLevelThreeList = menuDetailsLevelThreeList;
	}

	public List<MenuDetails> getMenuDetailsLevelFourList() {
		return menuDetailsLevelFourList;
	}

	public void setMenuDetailsLevelFourList(List<MenuDetails> menuDetailsLevelFourList) {
		this.menuDetailsLevelFourList = menuDetailsLevelFourList;
	}
	
	
	@PostConstruct
    public void init() {
		/*For Dynamic Menu Load*/
		getMenuManagedBean();
	}

	//@SuppressWarnings("deprecation")
	public void MenuManagedBean10000() {
		// Create submenu
		DefaultSubMenu admin = new DefaultSubMenu("Administration");
		admin.setIcon("ui-icon-home");
		DefaultSubMenu cif = new DefaultSubMenu("CIF");
		cif.setIcon("ui-icon-home");
		DefaultSubMenu loan = new DefaultSubMenu("Loan");
		loan.setIcon("ui-icon-home");
		DefaultSubMenu ccod = new DefaultSubMenu("CCOD");
		ccod.setIcon("ui-icon-home");

		// Create menuitem for Admin
		DefaultMenuItem roleSetup = new DefaultMenuItem("Role Setup");
		roleSetup.setUrl("/view/sa/uaa/UserGroupSetup.bank");
		DefaultMenuItem assPermToRole = new DefaultMenuItem("Assign Permission to Role");
		assPermToRole.setUrl("/view/sa/uaa/UserGroupSetup.bank");
		DefaultMenuItem useretup = new DefaultMenuItem("User Setup");
		useretup.setUrl("/view/sa/userSetup.bank");
		DefaultMenuItem assRoleToUser = new DefaultMenuItem("Assign Role to User");
		assRoleToUser.setUrl("/view/sa/userSetup.bank");
		DefaultMenuItem activateUser = new DefaultMenuItem("Activate User");
		activateUser.setUrl("/view/sa/userSetup.bank");
		DefaultMenuItem changePassword = new DefaultMenuItem("Change Password");
		changePassword.setUrl("/view/sa/uaa/UserGroupSetup.bank");

		// Create menuitem for CIF
		DefaultMenuItem customerSetup = new DefaultMenuItem("Customer Setup");
		customerSetup.setUrl("/view/cif/customer.bank");
		DefaultMenuItem customerInfoReport = new DefaultMenuItem("Customer Information Report");
		customerInfoReport.setUrl("/view/cif/reportPreview.bank");
		DefaultMenuItem customerDetailReport = new DefaultMenuItem("Customer Detail Report");
		customerDetailReport.setUrl("/view/cif/reportPreview.bank");

		// Create menuitem for CIF
		DefaultMenuItem loanSetup = new DefaultMenuItem("Loan Setup");
		customerSetup.setUrl("/view/sa/userSetup.bank");
		DefaultMenuItem loanInfoReport = new DefaultMenuItem("Loan Information Report");
		customerInfoReport.setUrl("/view/lon/loanInformationReport.bank");
		DefaultMenuItem loanTranReport = new DefaultMenuItem("Loan Transaction Report");
		customerDetailReport.setUrl("/view/lon/loanInformationReport.bank");

		roleSetup.setCommand("#{menuManagedBean.openAction}");

		// Associate menuitem with submenu
		((MenuModel) admin).addElement(roleSetup);
		((MenuModel) admin).addElement(assPermToRole);
		((MenuModel) admin).addElement(useretup);
		((MenuModel) admin).addElement(assRoleToUser);
		((MenuModel) admin).addElement(activateUser);
		((MenuModel) admin).addElement(new DefaultSeparator());
		((MenuModel) admin).addElement(changePassword);

		((MenuModel) cif).addElement(customerSetup);
		((MenuModel) cif).addElement(new DefaultSeparator());
		((MenuModel) cif).addElement(customerInfoReport);
		((MenuModel) cif).addElement(customerDetailReport);

		((MenuModel) loan).addElement(loanSetup);
		((MenuModel) loan).addElement(new DefaultSeparator());
		((MenuModel) loan).addElement(loanInfoReport);
		((MenuModel) loan).addElement(loanTranReport);
		((MenuModel) loan).addElement(ccod);

		((MenuModel) ccod).addElement(loanInfoReport);
		((MenuModel) ccod).addElement(loanTranReport);

		// Associate submenu with menu
		this.menu.addElement(admin);
		this.menu.addElement(cif);
		this.menu.addElement(loan);
	}

	public String openAction() {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("Open action has activiated asynchrounsly !"));
		return "";
	}

	public MenuModel getMenuManagedBean() {

		Subject currentUser = SecurityUtils.getSubject();
		UserInfo user = (UserInfo) currentUser.getPrincipals().getPrimaryPrincipal();

		menuDetailsLevelOneList = getMenuDetailsListForLevelOne(user.getUserId());
		menuDetailsLevelTwoList = getMenuDetailsListForLevelTwo(user.getUserId());
		menuDetailsLevelThreeList = getMenuDetailsListForLevelThree(user.getUserId());
		menuDetailsLevelFourList = getMenuDetailsListForLevelFour(user.getUserId());

		MenuModel menu = new DefaultMenuModel();

		// Create first level menu - Rajib
		for (MenuDetails menuDetailsLevelOne : menuDetailsLevelOneList) {
			DefaultSubMenu defaultSubMenuLevelOne = new DefaultSubMenu(menuDetailsLevelOne.getMenuName());
			defaultSubMenuLevelOne.setIcon("ui-icon-home");
			menu.addElement(defaultSubMenuLevelOne);

			// Create second level menu - Rajib Kumer Ghosh
			for (MenuDetails menuDetailsLevelTwo : menuDetailsLevelTwoList) {
				if (menuDetailsLevelTwo.getParentID().equals(menuDetailsLevelOne.getMenuID())) {
					DefaultSubMenu defaultSubMenuLevelTwo = new DefaultSubMenu(menuDetailsLevelTwo.getMenuName());
					defaultSubMenuLevelTwo.setIcon("ui-icon-home");
					defaultSubMenuLevelOne.addElement(defaultSubMenuLevelTwo);

					// Create third level menu - Rajib Kumer Ghosh
					if (!menuDetailsLevelThreeList.isEmpty()) {
						for (MenuDetails menuDetailsLevelThree : menuDetailsLevelThreeList) {
							if (menuDetailsLevelThree.getParentID().equals(menuDetailsLevelTwo.getMenuID())) {
								DefaultSubMenu defaultSubMenuLevelThird = new DefaultSubMenu(
										menuDetailsLevelThree.getMenuName());
								defaultSubMenuLevelThird.setIcon("ui-icon-home");
								defaultSubMenuLevelTwo.addElement(defaultSubMenuLevelThird);

								// Create fourth level menu Item - Rajib Kumer Ghosh(raj.himaloy@gmail.com)
								for (MenuDetails menuDetailsLevelFour : menuDetailsLevelFourList) {
									if (menuDetailsLevelFour.getParentID().equals(menuDetailsLevelThree.getMenuID())) {

										DefaultMenuItem defaultMenuItemLevelFourth = new DefaultMenuItem(
												menuDetailsLevelFour.getMenuName());
										defaultMenuItemLevelFourth.setUrl(menuDetailsLevelFour.getUrl());

										List<MenuElement> menuElements = new ArrayList<MenuElement>();
										menuElements = defaultSubMenuLevelThird.getElements();
										if (!menuElements.contains(defaultMenuItemLevelFourth)) {
											defaultSubMenuLevelThird.addElement(defaultMenuItemLevelFourth);
										}
									}
								}
							} else {
								// Create fourth level menu Item - Rajib Kumer Ghosh(+8801825051885)
								Set<MenuElement> hash_Set = new CopyOnWriteArraySet<MenuElement>();
								for (MenuDetails menuDetailsLevelFour : menuDetailsLevelFourList) {
									if (menuDetailsLevelFour.getParentID().equals(menuDetailsLevelTwo.getMenuID())) {

										DefaultMenuItem defaultMenuItemLevelFourth = new DefaultMenuItem(
												menuDetailsLevelFour.getMenuName());
										defaultMenuItemLevelFourth.setUrl(menuDetailsLevelFour.getUrl());
										defaultMenuItemLevelFourth.setId(menuDetailsLevelFour.getMenuID().toString());

										if (hash_Set.size() > 0) {
											for (MenuElement menuElement : hash_Set) {
												if (!menuElement.getId()
														.equals(menuDetailsLevelFour.getMenuID().toString())) {
													hash_Set.add(defaultMenuItemLevelFourth);
												}
											}
										} else {
											defaultSubMenuLevelTwo.addElement(defaultMenuItemLevelFourth);
											hash_Set.add(defaultMenuItemLevelFourth);
										}
										defaultSubMenuLevelTwo.addElement(defaultMenuItemLevelFourth);
									}
								}
								
								if (hash_Set.size() > 0) {
									List<MenuElement> menuElementListNew = new ArrayList<MenuElement>(hash_Set);
									defaultSubMenuLevelTwo.setElements(menuElementListNew);
								}
							}
						}
					} else {
						// Create fourth level menu Item - Rajib Kumer Ghosh(+8801825051885)
						Set<MenuElement> hash_Set = new CopyOnWriteArraySet<MenuElement>();
						for (MenuDetails menuDetailsLevelFour : menuDetailsLevelFourList) {
							if (menuDetailsLevelFour.getParentID().equals(menuDetailsLevelTwo.getMenuID())) {

								DefaultMenuItem defaultMenuItemLevelFourth = new DefaultMenuItem(
										menuDetailsLevelFour.getMenuName());
								defaultMenuItemLevelFourth.setUrl(menuDetailsLevelFour.getUrl());
								defaultMenuItemLevelFourth.setId(menuDetailsLevelFour.getMenuID().toString());

								if (hash_Set.size() > 0) {
									for (MenuElement menuElement : hash_Set) {
										if (!menuElement.getId().equals(menuDetailsLevelFour.getMenuID().toString())) {
											hash_Set.add(defaultMenuItemLevelFourth);
										}
									}
								} else {
									defaultSubMenuLevelTwo.addElement(defaultMenuItemLevelFourth);
									hash_Set.add(defaultMenuItemLevelFourth);
								}
							}
						}
						
						if (hash_Set.size() > 0) {
							List<MenuElement> menuElementListNew = new ArrayList<MenuElement>(hash_Set);
							defaultSubMenuLevelTwo.setElements(menuElementListNew);
						}
					}

				}
			}
		}
		this.menu = menu;
		return menu;
	}

	public List<MenuDetails> getMenuDetailsListForLevelOne(String userID) {
		List<MenuDetails> menuDetailsLevelOneList = menuManagedBeanService.getMenuDetailsListForLevelOne(userID);
		return menuDetailsLevelOneList;
	}

	public List<MenuDetails> getMenuDetailsListForLevelTwo(String userID) {
		List<MenuDetails> menuDetailsLevelTwoList = menuManagedBeanService.getMenuDetailsListForLevelTwo(userID);
		return menuDetailsLevelTwoList;
	}

	public List<MenuDetails> getMenuDetailsListForLevelThree(String userID) {
		List<MenuDetails> menuDetailsLevelThreeList = menuManagedBeanService.getMenuDetailsListForLevelThree(userID);
		return menuDetailsLevelThreeList;
	}

	public List<MenuDetails> getMenuDetailsListForLevelFour(String userID) {
		List<MenuDetails> menuDetailsLevelFourList = menuManagedBeanService.getMenuDetailsListForLevelFour(userID);
		return menuDetailsLevelFourList;
	}

}
