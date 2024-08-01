package com.banks.erp.sa.dashboard.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Rajib Kumer Ghosh
 *
 */
public class LoanProduct implements Serializable {
	private static final long serialVersionUID = 6064327482776204887L;
	
	private String productCode;
	private String productName;
	private String status;
	private BigDecimal outstanding;
	
	
	public LoanProduct(String productCode, String productName, String status, BigDecimal outstanding) {
		super();
		this.productCode = productCode;
		this.productName = productName;
		this.status = status;
		this.outstanding = outstanding;
	}
	
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public BigDecimal getOutstanding() {
		return outstanding;
	}
	public void setOutstanding(BigDecimal outstanding) {
		this.outstanding = outstanding;
	}
	
	

}
