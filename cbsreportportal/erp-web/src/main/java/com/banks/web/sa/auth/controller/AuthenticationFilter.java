package com.banks.web.sa.auth.controller;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

/**
 * @author Rajib Kumer Ghosh
 *
 */

public class AuthenticationFilter extends FormAuthenticationFilter {

	private static final String REDIRECT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "<partial-response><redirect url=\"%s\"></redirect></partial-response>";
	
	public static final String DEFAULT_LOGIN_URL = "/login.bank";

	@Override
	protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
		HttpServletRequest hsr = (HttpServletRequest) request;
		
		setLoginUrl(DEFAULT_LOGIN_URL);
		String loginUrl = hsr.getContextPath() + getLoginUrl();

		if ("partial/ajax".equals(hsr.getHeader("Faces-Request"))) {
			response.setContentType("text/xml");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().printf(REDIRECT, loginUrl);
		} else {
			super.redirectToLogin(request, response);
		}
	}

}
