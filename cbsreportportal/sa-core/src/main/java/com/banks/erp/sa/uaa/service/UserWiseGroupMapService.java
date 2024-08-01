package com.banks.erp.sa.uaa.service;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import com.banks.erp.library.util.persistence.CollectionDao;
import com.banks.erp.library.util.persistence.PersistenceDao;
import com.banks.erp.library.util.util.CommonUtilService;
import com.banks.erp.sa.uaa.idao.IUserWiseGroupMapDao;
import com.banks.erp.sa.uaa.iservice.IUserWiseGroupMapService;
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
public class UserWiseGroupMapService implements IUserWiseGroupMapService {

	@Inject
	private IUserWiseGroupMapDao iUserWiseGroupMapDao;

	@Inject
	private PersistenceDao persistenceDao;

	@Inject
	private CollectionDao collectionDao;

	@Override
	public List<UserWiseGroupMap> getUserWiseGroupMapList(String userId) {
		List<UserWiseGroupMap> userWiseGroupMapList = iUserWiseGroupMapDao.getUserWiseGroupMapList(userId);
		return userWiseGroupMapList;
	}

	@Override
	public List<UserWiseGroupMap> getUserWiseGroupMapActiveList(String userId) {
		List<UserWiseGroupMap> userWiseGroupMapList = iUserWiseGroupMapDao.getUserWiseGroupMapActiveList(userId);
		return userWiseGroupMapList;
	}

	@Override
	public List<UserWiseGroupMap> getUserWiseGroupMapListByGroupId(String groupId) {
		List<UserWiseGroupMap> userWiseGroupMapList = iUserWiseGroupMapDao.getUserWiseGroupMapListByGroupId(groupId);
		return userWiseGroupMapList;
	}

	@Transactional(TxType.REQUIRED)
	@Override
	public String update(List<UserWiseGroupMap> userWiseGroupMapList) throws Exception {

		// Message on Method Return
		String successMessage = "Groups Mapped with User has been Updated Successfully";
		String failedMessage = "Wrong Information, Operation has been Failed";

		try {
			UserInfo loggedInUser = CommonUtilService.getCurrentUserInfo();
			
			// Penetration checking
			for (UserWiseGroupMap userWiseGroupMap : userWiseGroupMapList) {
				UserWiseGroupMapPK userWiseGroupMapPK = new UserWiseGroupMapPK();
				userWiseGroupMapPK.setGroupId(userWiseGroupMap.getGroupId());
				userWiseGroupMapPK.setUserId(userWiseGroupMap.getUserId());

				UserWiseGroupMap userWiseGroupMap1 = collectionDao.find(UserWiseGroupMap.class, userWiseGroupMapPK);
				userWiseGroupMap1.setStatusId(userWiseGroupMap.getStatusId());
				userWiseGroupMap1.setModifiedBy(loggedInUser.getUserName());
				userWiseGroupMap1.setModificationDate(CommonUtilService.getSystemOpenDate());
				
				persistenceDao.update(userWiseGroupMap1);
			}

			//persistenceDao.update(userWiseGroupMapList);
		} catch (Exception e) {
			e.printStackTrace();

			return failedMessage;
		}

		return successMessage;
	}

	/*
	 * @Transactional(TxType.REQUIRED)
	 * 
	 * @Override public String delete(UserGroup userGroup) throws Exception {
	 * 
	 * // Message on Method Return String successMessage = "Group " +
	 * userGroup.getGroupId() + " Unmaped with Users"; String failedMessage =
	 * "Group Unmap has been Failed";
	 * 
	 * try { // Penetration checking for hacking List<UserWiseGroupMap>
	 * userWiseGroupMapList =
	 * getUserWiseGroupMapListByGroupId(userGroup.getGroupId());
	 * 
	 * persistenceDao.delete(userWiseGroupMapList); } catch (Exception e) {
	 * e.printStackTrace();
	 * 
	 * return failedMessage; }
	 * 
	 * return successMessage; }
	 */
}
