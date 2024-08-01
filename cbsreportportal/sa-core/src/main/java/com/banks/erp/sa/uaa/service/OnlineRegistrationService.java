/**
 * 
 */
package com.banks.erp.sa.uaa.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import com.banks.erp.library.util.persistence.CollectionDao;
import com.banks.erp.library.util.persistence.PersistenceDao;
import com.banks.erp.library.util.util.CommonUtilService;
import com.banks.erp.library.util.util.SendEmailUtil;
import com.banks.erp.sa.uaa.iservice.IOnlineRegistrationService;
import com.banks.erp.sa.uaa.model.UserGroup;
import com.banks.erp.sa.uaa.model.UserInfo;
import com.banks.erp.sa.uaa.model.UserWiseGroupMap;

/**
 * @author Rajib Kumer Ghosh
 *
 */
@RequestScoped
@Transactional(TxType.SUPPORTS)
public class OnlineRegistrationService implements IOnlineRegistrationService {

	@Inject
	private SendEmailUtil sendEmailUtil;

	@Inject
	private PersistenceDao persistenceDao;

	@Inject
	private CollectionDao collectionDao;

	@Transactional(TxType.REQUIRED)
	public String createOnlineRegistrationUserAndSendMail(String email) {

		// Message on Method Return
		String successMessage = "Data has been Saved Successfully, Please check mail, then logout and login.";
		String failedMessage = "Wrong Information, Operation has been Failed";
		String validationMessage = "User allready exist, Operation has been Failed";

		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(email);
		userInfo.setFirstName("Online Registration User");
		userInfo.setLastName("Online Registration User");
		userInfo.setUserName(email);
		userInfo.setPassword("A!@#$%^&8");
		userInfo.setDefaultBranchCode("00000");
		userInfo.setEmail(email);
		userInfo.setEmployeeID(email);
		userInfo.setUserCreationDate(CommonUtilService.getSystemOpenDate());
		userInfo.setPasswordChangedYN(Boolean.TRUE);
		userInfo.setUserForcePasswordStrengthYN(Boolean.TRUE);
		userInfo.setUserLockStatus(Boolean.FALSE);
		userInfo.setStatusId("true");
		userInfo.setCreatedBy(email);
		userInfo.setCreationDate(new Date());
		userInfo.setModifiedBy(null);
		userInfo.setModificationDate(null);
		userInfo.setRemarks("Online Registration User");

		try {
			// Checking for Already Exist or Not
			Boolean userInfoExist = collectionDao.findByID(UserInfo.class, userInfo.getUserId());

			if (userInfoExist) {
				return validationMessage;
			}

			List<UserGroup> userGroupList = collectionDao.executeQuery("SELECT u FROM UserGroup u");

			List<UserWiseGroupMap> userWiseGroupMapList = new ArrayList<>();
			for (UserGroup userGroup : userGroupList) {
				UserWiseGroupMap userWiseGroupMap = new UserWiseGroupMap();
				userWiseGroupMap.setUserId(userInfo.getUserId());
				userWiseGroupMap.setGroupId(userGroup.getGroupId());
				userWiseGroupMap.setGroupName(userGroup.getGroupName());
				
				if(userGroup.getGroupId().equals("2")) {
					userWiseGroupMap.setStatusId("true");
				} else {
					userWiseGroupMap.setStatusId("false");
				}
				userWiseGroupMap.setCreatedBy(userInfo.getUserName());
				userWiseGroupMap.setCreationDate(CommonUtilService.getSystemOpenDate());

				userWiseGroupMapList.add(userWiseGroupMap);
			}

			persistenceDao.save(userInfo);
			persistenceDao.save(userWiseGroupMapList);
			
			String mailSubject = "Auto mail - User Created";
			String mailBody = "Dear Concern," 
	                + "\n\n Please do not spam my email!. This is the auto mail from Application."
					+ "\n\n User has been created successfully are:- "
	                + "\n User = " + email
					+ "\n Password = " + userInfo.getPassword();
			
			sendEmailUtil.sendMailWithTextBody(email, mailSubject, mailBody);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
			return failedMessage;
		}
		return successMessage;
	}

}
