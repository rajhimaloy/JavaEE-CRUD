/**
 * 
 */
package com.banks.erp.sa.uaa.service;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import com.banks.erp.library.util.persistence.CollectionDao;
import com.banks.erp.library.util.persistence.PersistenceDao;
import com.banks.erp.library.util.util.CommonUtilService;
import com.banks.erp.sa.uaa.idao.IUserAdditionalBranchMappingDao;
import com.banks.erp.sa.uaa.iservice.IUserAdditionalBranchMappingService;
import com.banks.erp.sa.uaa.model.UserAdditionalBranchMapping;
import com.banks.erp.sa.uaa.model.UserAdditionalBranchMapping.UserAdditionalBranchMappingPK;
import com.banks.erp.sa.uaa.model.UserInfo;

/**
 * @author Rajib Kumer Ghosh
 *
 */

@RequestScoped
@Transactional(TxType.SUPPORTS)
public class UserAdditionalBranchMappingService implements IUserAdditionalBranchMappingService {

	@Inject
	private IUserAdditionalBranchMappingDao iUserAdditionalBranchMappingDao;

	@Inject
	private PersistenceDao persistenceDao;

	@Inject
	private CollectionDao collectionDao;

	@Override
	public UserAdditionalBranchMapping getUserAdditionalBranchMappingDetails(UserAdditionalBranchMapping userAdditionalBranchMapping) {
		UserAdditionalBranchMappingPK userAdditionalBranchMappingPK = new UserAdditionalBranchMappingPK();
		userAdditionalBranchMappingPK.setUserId(userAdditionalBranchMapping.getUserId());
		userAdditionalBranchMappingPK.setAdditionalBranchCode(userAdditionalBranchMapping.getAdditionalBranchCode());
		return iUserAdditionalBranchMappingDao.getUserAdditionalBranchMappingDetails(userAdditionalBranchMappingPK);
	}

	@Override
	public UserAdditionalBranchMapping getUserAdditionalBranchMappingDetails(String userId) {
		return iUserAdditionalBranchMappingDao.getUserAdditionalBranchMappingDetails(userId);
	}

	@Override
	public List<UserAdditionalBranchMapping> getUserAdditionalBranchMappingList() {
		List<UserAdditionalBranchMapping> userAdditionalBranchMappingList = null;
		try {
			userAdditionalBranchMappingList = collectionDao.executeQuery("SELECT u FROM UserAdditionalBranchMapping u");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userAdditionalBranchMappingList;
	}

	private void resetEntityUserAdditionalBranchMapping(UserAdditionalBranchMapping userAdditionalBranchMapping) throws Exception {
		userAdditionalBranchMapping.setUserId(null);
		userAdditionalBranchMapping.setAdditionalBranchCode(null);
		userAdditionalBranchMapping.setAdditionalBranchMappingDate(null);
		userAdditionalBranchMapping.setStatusId(null);
		userAdditionalBranchMapping.setCreationDate(null);
		userAdditionalBranchMapping.setCreatedBy(null);
		userAdditionalBranchMapping.setModifiedBy(null);
		userAdditionalBranchMapping.setModificationDate(null);
		//userAdditionalBranchMapping.setVersion(null);
	}

	@Transactional(TxType.REQUIRED)
	@Override
	public String save(UserAdditionalBranchMapping userAdditionalBranchMapping) throws Exception {

		// Message on Method Return
		String successMessage = "User Additional Branch has been Saved Successfully";
		String failedMessage = "Wrong Information, Operation has been Failed";
		String validationMessage = "Branch allready exist, Operation has been Failed";

		try {
			UserInfo loggedInUser = CommonUtilService.getCurrentUserInfo();
			
			UserAdditionalBranchMappingPK userAdditionalBranchMappingPK = new UserAdditionalBranchMappingPK();
			userAdditionalBranchMappingPK.setUserId(userAdditionalBranchMapping.getUserId());
			userAdditionalBranchMappingPK.setAdditionalBranchCode(userAdditionalBranchMapping.getAdditionalBranchCode());

			// Checking for Already Exist or Not
			Boolean userAdditionalBranchMappingIsExist = collectionDao.findByID(UserAdditionalBranchMapping.class, userAdditionalBranchMappingPK);

			if (userAdditionalBranchMappingIsExist) {
				resetEntityUserAdditionalBranchMapping(userAdditionalBranchMapping);
				return validationMessage;
			}

			userAdditionalBranchMapping.setCreatedBy(loggedInUser.getUserName());
			userAdditionalBranchMapping.setCreationDate(CommonUtilService.getSystemOpenDate());

			persistenceDao.save(userAdditionalBranchMapping);

		} catch (Exception e) {
			e.printStackTrace();

			resetEntityUserAdditionalBranchMapping(userAdditionalBranchMapping);
			return failedMessage;
		}

		resetEntityUserAdditionalBranchMapping(userAdditionalBranchMapping);
		return successMessage;
	}

	@Transactional(TxType.REQUIRED)
	@Override
	public String update(UserAdditionalBranchMapping userAdditionalBranchMapping) throws Exception {

		// Message on Method Return
		String successMessage = "User Additional Branch has been Updated Successfully";
		String failedMessage = "Wrong Information, Operation has been Failed";
		String validationMessage = "Branch doesn't exist, Operation has been Failed";

		try {
			UserInfo loggedInUser = CommonUtilService.getCurrentUserInfo();

			UserAdditionalBranchMappingPK userAdditionalBranchMappingPK = new UserAdditionalBranchMappingPK();
			userAdditionalBranchMappingPK.setUserId(userAdditionalBranchMapping.getUserId());
			userAdditionalBranchMappingPK.setAdditionalBranchCode(userAdditionalBranchMapping.getAdditionalBranchCode());

			// Checking for Already Exist or Not
			Boolean userAdditionalBranchMappingIsExist = collectionDao.findByID(UserAdditionalBranchMapping.class, userAdditionalBranchMappingPK);

			if (!userAdditionalBranchMappingIsExist) {
				resetEntityUserAdditionalBranchMapping(userAdditionalBranchMapping);
				return validationMessage;
			}

			// Penetration checking for hacking
			UserAdditionalBranchMapping userAdditionalBranchMappingEdit = collectionDao.find(UserAdditionalBranchMapping.class, userAdditionalBranchMappingPK);
			userAdditionalBranchMappingEdit.setUserId(userAdditionalBranchMapping.getUserId());
			userAdditionalBranchMappingEdit.setAdditionalBranchCode(userAdditionalBranchMapping.getAdditionalBranchCode());
			userAdditionalBranchMappingEdit.setAdditionalBranchMappingDate(userAdditionalBranchMapping.getAdditionalBranchMappingDate());
			userAdditionalBranchMappingEdit.setStatusId(userAdditionalBranchMapping.getStatusId());
			userAdditionalBranchMappingEdit.setModifiedBy(loggedInUser.getUserName());
			userAdditionalBranchMappingEdit.setModificationDate(CommonUtilService.getSystemOpenDate());
			//userAdditionalBranchMappingEdit.setVersionNo(userAdditionalBranchMappingEdit.getVersionNo() + 1);

			persistenceDao.update(userAdditionalBranchMappingEdit);
		} catch (Exception e) {
			e.printStackTrace();

			resetEntityUserAdditionalBranchMapping(userAdditionalBranchMapping);
			return failedMessage;
		}

		resetEntityUserAdditionalBranchMapping(userAdditionalBranchMapping);
		return successMessage;
	}

	@Override
	@Transactional(TxType.REQUIRED)
	public String delete(UserAdditionalBranchMapping userAdditionalBranchMapping) throws Exception {

		// Message on Method Return
		String successMessage = "User Additional Branch has been Deleted Successfully";
		String failedMessage = "Wrong Information, Operation has been Failed";
		String validationMessage = "Branch doesn't exist, Operation has been Failed";

		try {
			UserAdditionalBranchMappingPK userAdditionalBranchMappingPK = new UserAdditionalBranchMappingPK();
			userAdditionalBranchMappingPK.setUserId(userAdditionalBranchMapping.getUserId());
			userAdditionalBranchMappingPK.setAdditionalBranchCode(userAdditionalBranchMapping.getAdditionalBranchCode());

			// Checking for Already Exist or Not
			Boolean userAdditionalBranchMappingIsExist = collectionDao.findByID(UserAdditionalBranchMapping.class, userAdditionalBranchMappingPK);

			if (!userAdditionalBranchMappingIsExist) {
				resetEntityUserAdditionalBranchMapping(userAdditionalBranchMapping);
				return validationMessage;
			}

			// Penetration checking
			UserAdditionalBranchMapping userAdditionalBranchMappingDelete = collectionDao.find(UserAdditionalBranchMapping.class, userAdditionalBranchMappingPK);
			persistenceDao.delete(userAdditionalBranchMappingDelete);
		} catch (Exception e) {
			e.printStackTrace();

			resetEntityUserAdditionalBranchMapping(userAdditionalBranchMapping);
			return failedMessage;
		}

		resetEntityUserAdditionalBranchMapping(userAdditionalBranchMapping);
		return successMessage;

	}

}
