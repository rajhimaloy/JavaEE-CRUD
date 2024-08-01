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
import com.banks.erp.sa.uaa.iservice.IChangePasswordService;
import com.banks.erp.sa.uaa.model.UserInfo;

/**
 * @author Rajib Kumer Ghosh
 *
 */

@RequestScoped
@Transactional(TxType.SUPPORTS)
public class ChangePasswordService implements IChangePasswordService {

	@Inject
	private PersistenceDao persistenceDao;

	public void resetEntityUserInfoDTO(UserInfoDTO userInfoDTO) throws Exception {
		userInfoDTO.setUserId(null);
		userInfoDTO.setFirstName(null);
		userInfoDTO.setLastName(null);
		userInfoDTO.setUserName(null);
		// userInfoDTO.setFullName(null);
		userInfoDTO.setPassword(null);
		userInfoDTO.setNewPassword(null);
		userInfoDTO.setReEnterNewPassword(null);
	}

	@Transactional(TxType.REQUIRED)
	public String update(UserInfoDTO userInfoDTO, UserInfo userInfo) throws Exception {

		// Message on Method Return
		String successMessage = "User Password has been Changed Successfully";
		String failedMessage = "Wrong Information, Operation has been Failed";

		Subject currentUser = SecurityUtils.getSubject();
		UserInfo loggedinUser = (UserInfo) currentUser.getPrincipals().getPrimaryPrincipal();

		if (!userInfoDTO.getUserName().equals(userInfo.getUserName())
				&& !userInfoDTO.getPassword().equals(userInfo.getPassword())) {
			
			resetEntityUserInfoDTO(userInfoDTO);
			return failedMessage;
		}

		if (userInfoDTO.getPassword().isEmpty() || !userInfoDTO.getPassword().equals(userInfo.getPassword())
				|| userInfo.getPassword().equals(userInfoDTO.getNewPassword())
				|| !userInfoDTO.getNewPassword().equals(userInfoDTO.getReEnterNewPassword())) {
			
			resetEntityUserInfoDTO(userInfoDTO);
			return failedMessage;
		}

		if (loggedinUser.getUserName().equals(userInfoDTO.getUserName())
				|| loggedinUser.getDefaultBranchCode().equals("00000")) {
			
			//userInfo.setPassword(userInfoDTO.getNewPassword());
			userInfo.setPassword(PasswordUtil.digestPassword(userInfoDTO.getNewPassword()));
			userInfo.setPasswordChangedYN(Boolean.FALSE);
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
