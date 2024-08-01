/**
 * 
 */
package com.banks.erp.sa.dashboard.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.it.soul.lab.sql.entity.Column;
import com.it.soul.lab.sql.entity.Entity;

/**
 * @author Rajib_Ghosh
 *
 */
public class GoogleMapDTO extends Entity implements Serializable {
	private static final long serialVersionUID = 6064327482776204887L;
	
	@Column(name="LATTITUDE")
	private BigDecimal lattitude;
	@Column(name="LONGITUDE")
	private BigDecimal longitude;
	@Column(name="TITLE")
	private String title;
	@Column(name="IMAGENAME")
	private String imageName;
	@Column(name="IMAGEPATH")
	private String imagePath;
		
	public GoogleMapDTO() {
	}

	public GoogleMapDTO(BigDecimal lattitude, BigDecimal longitude) {
		super();
		this.lattitude = lattitude;
		this.longitude = longitude;
	}

	public BigDecimal getLattitude() {
		return lattitude;
	}

	public void setLattitude(BigDecimal lattitude) {
		this.lattitude = lattitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
