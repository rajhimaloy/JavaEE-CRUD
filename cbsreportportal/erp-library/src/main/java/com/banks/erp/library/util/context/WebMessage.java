package com.banks.erp.library.util.context;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * @author Rajib Kumer Ghosh
 *
 */

public class WebMessage implements Serializable {

	private static final long serialVersionUID = -8956052769465382551L;
	
	public void successMessage(String message) {
		FacesMessage facesMessage = new FacesMessage();
		facesMessage.setSeverity(FacesMessage.SEVERITY_INFO);
		facesMessage.setSummary(message);
		facesMessage.setDetail(message);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}

	public void errorMessage(String message) {
		FacesMessage facesMessage = new FacesMessage();
		facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
		facesMessage.setSummary(message);
		facesMessage.setDetail(message);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}
	
	public void warningMessage(String message) {
		FacesMessage facesMessage = new FacesMessage();
		facesMessage.setSeverity(FacesMessage.SEVERITY_WARN);
		facesMessage.setSummary(message);
		facesMessage.setDetail(message);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}
	
	public void fatalMessage(String message) {
		FacesMessage facesMessage = new FacesMessage();
		facesMessage.setSeverity(FacesMessage.SEVERITY_FATAL);
		facesMessage.setSummary(message);
		facesMessage.setDetail(message);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}
}
