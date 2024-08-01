/**
 * 
 */
package com.banks.erp.sa.uaa.service;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.banks.erp.library.util.persistence.PersistenceDao;
import com.banks.erp.sa.uaa.dto.UserInfoDTO;
import com.banks.erp.sa.uaa.iservice.IUserLockUnlockService;
import com.banks.erp.sa.uaa.model.UserInfo;

/**
 * @author Rajib Kumer Ghosh
 *
 */

@RequestScoped
@Transactional(TxType.SUPPORTS)
public class UserLockUnlockService implements IUserLockUnlockService {

	@Inject
	private PersistenceDao persistenceDao;

	public void resetEntityUserInfoDTO(UserInfoDTO userInfoDTO) throws Exception {
		userInfoDTO.setUserId(null);
		userInfoDTO.setUserName(null);
		userInfoDTO.setUserLockStatus(null);
	}

	@Transactional(TxType.REQUIRED)
	public String update(UserInfoDTO userInfoDTO, UserInfo userInfo) throws Exception {

		// Message on Method Return
		String successMessage = "User Lock Status has been Changed Successfully";
		String failedMessage = "Wrong Information, Operation has been Failed";

		Subject currentUser = SecurityUtils.getSubject();
		UserInfo loggedinUser = (UserInfo) currentUser.getPrincipals().getPrimaryPrincipal();

		if (!userInfoDTO.getUserName().equals(userInfo.getUserName())) {
			
			resetEntityUserInfoDTO(userInfoDTO);
			return failedMessage;
		}
		
		userInfo.setUserLockStatus(userInfoDTO.getUserLockStatus());
		
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
