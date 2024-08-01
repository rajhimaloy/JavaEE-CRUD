package com.banks.erp.library.util.enums;

public enum Permission {

	VIEW("01", "View"), SAVE("02", "Save"), UPDATE("03", "Update"), DELETE("04", "Delete");

	private final String value;
	private final String label;

	private Permission(final String value, final String label) {
		this.value = value;
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public String getLabel() {
		return label;
	}

}
