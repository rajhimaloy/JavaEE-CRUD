package com.banks.web.sa.auth.controller;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;

import org.omnifaces.util.Faces;

import com.banks.erp.library.util.context.WebMessage;
import com.banks.erp.library.util.util.CommonUtilService;
import com.banks.erp.sa.uaa.iservice.IOnlineRegistrationService;
import com.banks.erp.sa.uaa.model.UserInfo;

/**
 * @author Rajib Kumer Ghosh
 *
 */

// @Model
@Named
@RequestScoped
public class UserAuthenticationController {

	private static final String LOCKED = "User is locked.";
	private static final String UNKNOWN = "Wrong User Id or Password.";
	// private static final String HOME_URL = "/view/sa/HomePage.xhtml";
	private static final String HOME_URL = "/view/index.bank";
	private static final String REGISTRATION_URL = "/onlineregistration.bank";
	// private static final String LOGIN_URL =
	// "/view/sa/uaa/UserAuthentication.bank";
	private static final String LOGIN_URL = "/login.bank";
	
	@Inject
	private IOnlineRegistrationService iOnlineRegistrationService;

	@Inject
	private WebMessage message;

	@NotNull
	private String userName;

	@NotNull
	private String password;

	@NotNull
	private String email;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void login() {
		try {
			AuthenticationToken token = null;
			token = new UsernamePasswordToken(userName, password, false);

			Subject currentUser = SecurityUtils.getSubject();
			currentUser.login(token);

			setSessionPolicy();

			Faces.responseComplete();
			HttpServletRequest req = Faces.getRequest();
			HttpServletResponse res = Faces.getResponse();
			WebUtils.redirectToSavedRequest(req, res, HOME_URL);
		} catch (AuthenticationException | IOException ex) {
			if (ex instanceof LockedAccountException) {
				message.errorMessage(LOCKED);
			} else {
				message.errorMessage(UNKNOWN);
			}
		}
	}

	public void logout() throws IOException {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		Faces.redirect(Faces.getRequest().getContextPath() + LOGIN_URL);
		Faces.invalidateSession();
	}

	public void registration() {
		try {
			String messageDetail = iOnlineRegistrationService.createOnlineRegistrationUserAndSendMail(email);
			
			// Message on Save Data
			message.successMessage(messageDetail);

			Faces.responseComplete();
			HttpServletRequest req = Faces.getRequest();
			HttpServletResponse res = Faces.getResponse();
			WebUtils.redirectToSavedRequest(req, res, REGISTRATION_URL);
		} catch (AuthenticationException | IOException ex) {
			if (ex instanceof LockedAccountException) {
				message.errorMessage(LOCKED);
			} else {
				message.errorMessage(UNKNOWN);
			}
		}
	}

	public static UserInfo getCurrentUser() {
		// return
		// FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();

		Subject currentUser = SecurityUtils.getSubject();
		UserInfo user = (UserInfo) currentUser.getPrincipals().getPrimaryPrincipal();
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

	@SuppressWarnings("unused")
	public void getSessionPolicy() throws IOException {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();

		Object username = session.getAttribute("username");
		String hostIP = session.getHost();
		Date loginTime = session.getStartTimestamp();
		Date timestamp = session.getLastAccessTime();
		long logoutTime = session.getTimeout(); 
		Serializable origSessionId = session.getId();

		Map<String, Object> fields = new HashMap();
		fields.put("session_id", origSessionId);
		fields.put("host", hostIP);
		fields.put("start_timestamp", loginTime);
		fields.put("last_access_time", timestamp);
		fields.put("timeout", logoutTime);

		/*
		 * String hex = new Md5Hash(myFile).toHex(); String encodedPassword = new
		 * Sha512Hash(password, salt, count).toBase64;
		 */
	}

	public void setSessionPolicy() throws IOException {
		Subject currentUser = SecurityUtils.getSubject();

		/* ***Shiro Session***
			Session session = currentUser.getSession();
			session.setAttribute("username", "userName");

			long millis = 1800000;
			Date start = session.getStartTimestamp();
			Date timestamp = session.getLastAccessTime();
			session.setTimeout(millis);
			
			//session.setTimeout(-1);
		*/

		// ***Javax.Servlet Session***
		// get non-transacted HTTP session from facescontext
		HttpSession sessions = CommonUtilService.getSession();
		// set maximum interval time period (In seconds) for client interacting with session. can be set in web.xml also
		if (userName.equalsIgnoreCase("rajib")) {
			sessions.setMaxInactiveInterval(1800);
		} else {
			sessions.setMaxInactiveInterval(180);
		}
		
		//TODO MaxInactiveInterval from DB
		/*CommonUtilService commonUtilService = new CommonUtilService();
		sessions.setMaxInactiveInterval(commonUtilService.getUserMaxInactiveInterval(userName));*/
		
		//sessions.setMaxInactiveInterval(180);
		// put the username in session variable
		sessions.setAttribute("username", userName);

		getSessionPolicy();
	}

	/*
	 * @Override public boolean forceLogout(String sessionId) { Session session =
	 * sessionDAO.readSession(sessionId); session.setTimeout(0); session.stop();
	 * sessionDAO.delete(session); return true; }
	 */

}
