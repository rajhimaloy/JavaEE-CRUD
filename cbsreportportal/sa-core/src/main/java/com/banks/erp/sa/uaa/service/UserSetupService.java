package com.banks.erp.sa.uaa.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;

import com.banks.erp.library.util.crypto.PasswordUtil;
import com.banks.erp.library.util.persistence.CollectionDao;
import com.banks.erp.library.util.persistence.PersistenceDao;
import com.banks.erp.library.util.util.CommonUtilService;
import com.banks.erp.sa.uaa.idao.IUserSetupDao;
import com.banks.erp.sa.uaa.iservice.IUserSetupService;
import com.banks.erp.sa.uaa.model.UserGroup;
import com.banks.erp.sa.uaa.model.UserInfo;
import com.banks.erp.sa.uaa.model.UserWiseGroupMap;
import com.banks.erp.sa.uaa.model.UserWiseGroupMap.UserWiseGroupMapPK;

/**
 * @author Rajib Kumer Ghosh
 *
 */

@RequestScoped
@Transactional(TxType.SUPPORTS)
public class UserSetupService implements IUserSetupService {

	@Inject
	private IUserSetupDao iUserSetupDao;

	@Inject
	private PersistenceDao persistenceDao;

	@Inject
	private CollectionDao collectionDao;

	// Here in SELECT query(JPA query) TABLE name in Entity class name and column
	// name is Entity class's variable name.(Like:- UserInfo, u.userName)
	@Override
	public UserInfo getUserinfo(String userName) throws Exception {
		UserInfo userInfo = iUserSetupDao.getUserinfo(userName);
		return userInfo;
	}

	public UserInfo getUserSetupDetails(UserInfo userInfo) {
		// return iUserSetupDao.getUserSetupDetails(userInfo);

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userName", userInfo.getUserName());
		UserInfo userInfoRs = null;
		try {
			// userSetupRs = collectionDao.selectSingle("UserSetup.findByUserName", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userInfoRs;
	}

	public UserInfo getUserSetupDetails(String userName) {
		return iUserSetupDao.getUserSetupDetails(userName);
	}

	public List<UserInfo> getUserSetupList() {
		List<UserInfo> userInfoList = null;
		try {
			userInfoList = collectionDao.executeQuery("SELECT u FROM UserInfo u");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userInfoList;
	}

	public void resetEntityUserInfo(UserInfo userInfo) throws Exception {
		userInfo.setUserId(null);
		userInfo.setFirstName(null);
		userInfo.setLastName(null);
		userInfo.setUserName(null);		
		userInfo.setPassword(null);
		userInfo.setDefaultBranchCode(null);
		userInfo.setEmail(null);
		userInfo.setEmployeeID(null);
		userInfo.setUserCreationDate(null);
		userInfo.setPasswordChangedYN(null);
		userInfo.setUserForcePasswordStrengthYN(null);
		userInfo.setUserLockStatus(null);
		userInfo.setStatusId(null);
		userInfo.setCreatedBy(null);
		userInfo.setCreationDate(null);
		userInfo.setModifiedBy(null);
		userInfo.setModificationDate(null);
		userInfo.setRemarks(null);
		// userGroup.setVersion(null);
	}

	@Transactional(TxType.REQUIRED)
	public String save(UserInfo userInfo, List<UserGroup> userGroupList) throws Exception {

		// Message on Method Return
		String successMessage = "Data has been Saved Successfully";
		String failedMessage = "Wrong Information, Operation has been Failed";
		String validationMessage = "User allready exist, Operation has been Failed";

		try {
			UserInfo loggedInUser = CommonUtilService.getCurrentUserInfo();

			userInfo.setUserId(userInfo.getUserName()); 
			userInfo.setPassword(PasswordUtil.digestPassword(userInfo.getPassword()));
			userInfo.setUserLockStatus(Boolean.FALSE);
			userInfo.setStatusId("false");
			userInfo.setCreatedBy(loggedInUser.getUserName());
			userInfo.setCreationDate(CommonUtilService.getSystemOpenDate());
			
			// Checking for Already Exist or Not
			Boolean userInfoExist = collectionDao.findByID(UserInfo.class, userInfo.getUserId());
			
			if (userInfoExist) {
				resetEntityUserInfo(userInfo);
				return validationMessage;
			}
			
			List<UserWiseGroupMap> userWiseGroupMapList = new ArrayList<>();
			for(UserGroup userGroup : userGroupList) {
				UserWiseGroupMap userWiseGroupMap = new UserWiseGroupMap();
				userWiseGroupMap.setUserId(userInfo.getUserId());
				userWiseGroupMap.setGroupId(userGroup.getGroupId());
				userWiseGroupMap.setGroupName(userGroup.getGroupName());
				userWiseGroupMap.setStatusId("false");
				userWiseGroupMap.setCreatedBy(loggedInUser.getUserName());
				userWiseGroupMap.setCreationDate(CommonUtilService.getSystemOpenDate());
				
				userWiseGroupMapList.add(userWiseGroupMap);
			}
			
			persistenceDao.save(userInfo);
			persistenceDao.save(userWiseGroupMapList);
		} catch (Exception e) {
			e.printStackTrace();

			resetEntityUserInfo(userInfo);
			return failedMessage;
		}

		resetEntityUserInfo(userInfo);
		return successMessage;
	}

	@Transactional(TxType.REQUIRED)
	public String update(UserInfo userInfo) throws Exception {

		// Message on Method Return
		String successMessage = "Data has been Updated Successfully";
		String failedMessage = "Wrong Information, Operation has been Failed";
		String validationMessage = "User doesn't exist, Operation has been Failed";

		try {
			UserInfo loggedInUser = CommonUtilService.getCurrentUserInfo();
			UserInfo userInfoExisting = getUserSetupDetails(userInfo.getUserName());
			
			// Checking for Already Exist or Not
			Boolean userInfoExist = collectionDao.findByID(UserInfo.class, userInfoExisting.getUserId());
			
			if (!userInfoExist) {
				resetEntityUserInfo(userInfo);
				return validationMessage;
			}


			// Penetration checking for hacking
			UserInfo userInfoEdit = collectionDao.find(UserInfo.class, userInfoExisting.getUserId());
			
			userInfoEdit.setStatusId("false");
			userInfoEdit.setModifiedBy(loggedInUser.getUserName());
			userInfoEdit.setModificationDate(CommonUtilService.getSystemOpenDate());

			if (!userInfo.getFirstName().isEmpty() || !userInfo.getFirstName().equals(null)) {
				userInfoEdit.setFirstName(userInfo.getFirstName());
			}
			
			if (!userInfo.getLastName().isEmpty() || !userInfo.getLastName().equals(null)) {
				userInfoEdit.setLastName(userInfo.getLastName());
			}

			if (!userInfo.getPassword().isEmpty() || !userInfo.getPassword().equals(null)) {
				userInfoEdit.setPassword(userInfo.getPassword());
			}

			if (!userInfo.getDefaultBranchCode().isEmpty() || !userInfo.getDefaultBranchCode().equals(null)) {
				userInfoEdit.setDefaultBranchCode(userInfo.getDefaultBranchCode());
			}

			if (!userInfo.getEmail().isEmpty() || !userInfo.getEmail().equals(null)) {
				userInfoEdit.setEmail(userInfo.getEmail());
			}

			if (!userInfo.getEmployeeID().isEmpty() || !userInfo.getEmployeeID().equals(null)) {
				userInfoEdit.setEmployeeID(userInfo.getEmployeeID());
			}

			if (!userInfo.getUserCreationDate().equals(null)) {
				userInfoEdit.setUserCreationDate(userInfo.getUserCreationDate());
			}

			if (!userInfo.getPasswordChangedYN().equals(null)) {
				userInfoEdit.setPasswordChangedYN(userInfo.getPasswordChangedYN());
			}

			if (!userInfo.getUserForcePasswordStrengthYN().equals(null)) {
				userInfoEdit.setUserForcePasswordStrengthYN(userInfo.getUserForcePasswordStrengthYN());
			}

			if (!userInfo.getRemarks().isEmpty() || !userInfo.getRemarks().equals(null)) {
				userInfoEdit.setRemarks(userInfo.getRemarks());
			}

			persistenceDao.update(userInfoEdit);
		} catch (Exception e) {
			e.printStackTrace();

			resetEntityUserInfo(userInfo);
			return failedMessage;
		}

		resetEntityUserInfo(userInfo);
		return successMessage;
	}

	@Transactional(TxType.REQUIRED)
	public String delete(UserInfo userInfo, List<UserWiseGroupMap> userWiseGroupMapList) throws Exception {

		// Message on Method Return
		String successMessage = "Data has been Deleted Successfully";
		String failedMessage = "Wrong Information, Operation has been Failed";
		String validationMessage = "User doesn't exist, Operation has been Failed";

		try {
			UserInfo userInfoExisting = getUserSetupDetails(userInfo.getUserName());
			
			// Checking for Already Exist or Not
			Boolean userInfoExist = collectionDao.findByID(UserInfo.class, userInfoExisting.getUserId());
			
			if (!userInfoExist) {
				resetEntityUserInfo(userInfo);
				return validationMessage;
			}
			
			persistenceDao.delete(userWiseGroupMapList);
			
			// Penetration checking
			UserInfo userInfoDelete = collectionDao.find(UserInfo.class, userInfoExisting.getUserId());			
			//iUserSetupDao.delete(userInfoDelete);
			persistenceDao.delete(userInfoDelete);
		} catch (Exception e) {
			e.printStackTrace();

			resetEntityUserInfo(userInfo);
			return failedMessage;
		}

		resetEntityUserInfo(userInfo);
		return successMessage;
	}

}
