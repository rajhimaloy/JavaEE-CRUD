/**
 * 
 */
package com.banks.erp.sa.menu.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Rajib Kumer Ghosh
 *
 */

@Entity
@Cacheable(value = true)
@XmlRootElement(name = "menuDetails")
@Table(name = "SYS_MENU_DETAILS")
public class MenuDetails implements Serializable {
	
	private static final long serialVersionUID = 6064327482776204887L;
	
	@Id
	@Basic(optional = false)
	@Column(name = "MENUID")
	private Integer menuID;

	@NotNull
	@Size(max = 45)
	@Basic(optional = false)
	@Column(name = "MENUNAME")
	private String menuName;
	
	@Size(max = 245)
	@Column(name = "MENUDESC")
	private String menuDesc;
	
	@NotNull
	@Size(max = 150)
	@Basic(optional = false)
	@Column(name = "URL")
	private String url;
	
	@Basic(optional = true)
	@Column(name = "PARENTID")
	private Integer parentID;
	
	@Basic(optional = false)
	@Column(name = "LEVELS")
	private Integer levels;

	public MenuDetails() {
	}

	public Integer getMenuID() {
		return menuID;
	}

	public void setMenuID(Integer menuID) {
		this.menuID = menuID;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuDesc() {
		return menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getParentID() {
		return parentID;
	}

	public void setParentID(Integer parentID) {
		this.parentID = parentID;
	}

	public Integer getLevels() {
		return levels;
	}

	public void setLevels(Integer levels) {
		this.levels = levels;
	}	

}
