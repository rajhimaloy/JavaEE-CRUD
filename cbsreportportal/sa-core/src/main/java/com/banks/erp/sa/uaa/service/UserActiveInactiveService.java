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
import com.banks.erp.sa.uaa.iservice.IUserActiveInactiveService;
import com.banks.erp.sa.uaa.model.UserInfo;

/**
 * @author Rajib Kumer Ghosh
 *
 */

@RequestScoped
@Transactional(TxType.SUPPORTS)
public class UserActiveInactiveService implements IUserActiveInactiveService {

	@Inject
	private PersistenceDao persistenceDao;

	public void resetEntityUserInfoDTO(UserInfoDTO userInfoDTO) throws Exception {
		userInfoDTO.setUserId(null);
		userInfoDTO.setUserName(null);
		userInfoDTO.setStatusId(null);
	}

	@Transactional(TxType.REQUIRED)
	public String update(UserInfoDTO userInfoDTO, UserInfo userInfo) throws Exception {

		// Message on Method Return
		String successMessage = "User Active/In-Active has been Changed Successfully";
		String failedMessage = "Wrong Information, Operation has been Failed";
		String failedMessageOnFisrtUpdate = "You are the Maker, So you can not modify - Cheater";
		String failedMessageOnNextUpdate = "You are the Modifyer, So you can not modify again - Cheater";

		Subject currentUser = SecurityUtils.getSubject();
		UserInfo loggedinUser = (UserInfo) currentUser.getPrincipals().getPrimaryPrincipal();

		if (!userInfoDTO.getUserName().equals(userInfo.getUserName())) {
			
			resetEntityUserInfoDTO(userInfoDTO);
			return failedMessage;
		}
		
		if (userInfo.getModifiedBy() == null) {
			if (userInfo.getCreatedBy().equals(loggedinUser.getUserName())) {		
				
				resetEntityUserInfoDTO(userInfoDTO);
				return failedMessageOnFisrtUpdate;
			}
			
		} else {
			if (userInfo.getModifiedBy().equals(loggedinUser.getUserName())) {
				
				resetEntityUserInfoDTO(userInfoDTO);
				return failedMessageOnNextUpdate;
			}
		}
		
		userInfo.setStatusId(userInfoDTO.getStatusId());
		
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
