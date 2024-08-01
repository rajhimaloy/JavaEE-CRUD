package com.banks.web.sa.auth.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.banks.erp.sa.uaa.model.UserInfo;

/**
 * @author Rajib Kumer Ghosh
 *
 */

@Named
@RequestScoped
public class CurrentUser {
	
	public static UserInfo getCurrentUser() {
		//return FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
		
		Subject currentUser = SecurityUtils.getSubject();
		UserInfo user = (UserInfo) currentUser.getPrincipals().getPrimaryPrincipal();
		//user.setBranchCode("00001");
		return user;
	}
	
	public static String getUserLoginId() {		
		Subject currentUser = SecurityUtils.getSubject();
		UserInfo user = (UserInfo) currentUser.getPrincipals().getPrimaryPrincipal();
		String userName = user.getUserName();
		return userName;
	}
	
	public static String getSystemDate() {	
		DateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date date = new Date();
		String systemDate = sdf.format(date);			
		return systemDate;
	}

}
