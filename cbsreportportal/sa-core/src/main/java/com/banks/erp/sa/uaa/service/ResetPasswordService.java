package com.banks.erp.sa.uaa.service;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.banks.erp.library.util.crypto.PasswordUtil;
import com.banks.erp.library.util.persistence.PersistenceDao;
import com.banks.erp.library.util.util.CommonUtilService;
import com.banks.erp.sa.uaa.dto.UserInfoDTO;
import com.banks.erp.sa.uaa.iservice.IResetPasswordService;
import com.banks.erp.sa.uaa.model.UserInfo;

/**
 * @author Rajib Kumer Ghosh
 *
 */

@RequestScoped
@Transactional(TxType.SUPPORTS)
public class ResetPasswordService implements IResetPasswordService {

	@Inject
	private PersistenceDao persistenceDao;

	public void resetEntityUserInfoDTO(UserInfoDTO userInfoDTO) throws Exception {
		userInfoDTO.setUserId(null);
		userInfoDTO.setUserName(null);
		userInfoDTO.setPassword(null);
	}

	@Transactional(TxType.REQUIRED)
	public String update(UserInfoDTO userInfoDTO, UserInfo userInfo) throws Exception {

		// Message on Method Return
		String successMessage = "User password has been Reset successfully";
		String failedMessageOnExist = "User does not exist, operation has been failed";
		String failedMessageOnOwn = "Password is not possible to Reset by own, operation has been failed";
		String failedMessageOnActive = "User is In-Active, hence operation has been failed";
		String failedMessageOnLock = "User is Locked, hence operation has been failed";
		String failedMessage = "Wrong information, Operation has been failed";

		Subject currentUser = SecurityUtils.getSubject();
		UserInfo loggedinUser = (UserInfo) currentUser.getPrincipals().getPrimaryPrincipal();

		if (!userInfoDTO.getUserName().equals(userInfo.getUserName())) {
			
			resetEntityUserInfoDTO(userInfoDTO);
			return failedMessageOnExist;
		}
		
		if (userInfoDTO.getUserName().equals(loggedinUser.getUserName())) {
			
			resetEntityUserInfoDTO(userInfoDTO);
			return failedMessageOnOwn;
		}

		if (userInfo.getStatusId().equals("false")) {
			
			resetEntityUserInfoDTO(userInfoDTO);
			return failedMessageOnActive;
		}

		if (userInfo.getUserLockStatus().equals(Boolean.TRUE)) {
			
			resetEntityUserInfoDTO(userInfoDTO);
			return failedMessageOnLock;
		}

		if (loggedinUser.getDefaultBranchCode().equals("00000")) {
			
			//userInfo.setPassword(userInfoDTO.getPassword());
			userInfo.setPassword(PasswordUtil.digestPassword(userInfoDTO.getPassword()));
			userInfo.setPasswordChangedYN(Boolean.TRUE);
			userInfo.setModifiedBy(loggedinUser.getUserName());
			userInfo.setModificationDate(CommonUtilService.getSystemOpenDate());
		} else {
			
			resetEntityUserInfoDTO(userInfoDTO);
			return failedMessage;
		}
		
		try {
			persistenceDao.update(userInfo);
		} catch (Exception e) {
			e.printStackTrace();

			resetEntityUserInfoDTO(userInfoDTO);
			return failedMessage;
		}

		resetEntityUserInfoDTO(userInfoDTO);
		return successMessage;
	}
}
