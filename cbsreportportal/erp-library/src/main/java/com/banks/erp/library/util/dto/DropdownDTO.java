package com.banks.erp.library.util.dto;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Rajib Kumer Ghosh
 *
 */

@Entity
@Cacheable(value = true)
public class DropdownDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String stringValue;
	
	private String stringLabel;
	
	public DropdownDTO() {
	}

	public DropdownDTO(String stringValue, String stringLabel) {
		this.stringValue = stringValue;
		this.stringLabel = stringLabel;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public String getStringLabel() {
		return stringLabel;
	}

	public void setStringLabel(String stringLabel) {
		this.stringLabel = stringLabel;
	}	

	

}
