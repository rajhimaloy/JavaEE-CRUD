package com.banks.web.sa.uaa.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.banks.erp.library.util.context.WebMessage;
import com.banks.erp.sa.uaa.dto.UserInfoDTO;
import com.banks.erp.sa.uaa.iservice.IResetPasswordService;
import com.banks.erp.sa.uaa.iservice.IUserSetupService;
import com.banks.erp.sa.uaa.model.UserInfo;

/**
 * @author Rajib Kumer Ghosh
 *
 */

@Named
@ViewScoped
public class ResetPasswordController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private IResetPasswordService iResetPasswordService;	
	
	@Inject
	private IUserSetupService iUserSetupService;
	
	@Inject
	private UserInfoDTO userInfoDTO;
	
	@Inject
	private UserInfo userInfo;
	
	@Inject
	private WebMessage webMessage;	
	
	
	
	public UserInfoDTO getUserInfoDTO() {
		return userInfoDTO;
	}

	public void setUserInfoDTO(UserInfoDTO userInfoDTO) {
		this.userInfoDTO = userInfoDTO;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	public WebMessage getWebMessage() {
		return webMessage;
	}

	public void setWebMessage(WebMessage webMessage) {
		this.webMessage = webMessage;
	}
	

	@PostConstruct
	public void init() {
		System.out.println("===============Change Password Details===============");
		//userSetupList = iChangePasswordService.getUserSetupList();
	}
	
	@RequiresPermissions("10010009:UPDATE")
	public void update() {		
		
		try {
			userInfo = iUserSetupService.getUserSetupDetails(userInfoDTO.getUserName());
			
			String messageDetail = iResetPasswordService.update(userInfoDTO, userInfo);

			// Message on Edit Data
			webMessage.successMessage(messageDetail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
