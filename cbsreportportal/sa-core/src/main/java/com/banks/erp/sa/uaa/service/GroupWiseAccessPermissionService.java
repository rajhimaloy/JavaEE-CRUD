package com.banks.erp.sa.uaa.service;

import java.util.List;

import javax.ejb.Asynchronous;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import com.banks.erp.library.util.persistence.CollectionDao;
import com.banks.erp.library.util.persistence.PersistenceDao;
import com.banks.erp.library.util.util.CommonUtilService;
import com.banks.erp.sa.uaa.idao.IGroupWiseAccessPermissionDao;
import com.banks.erp.sa.uaa.iservice.IGroupWiseAccessPermissionService;
import com.banks.erp.sa.uaa.model.GroupWiseAccessPermission;
import com.banks.erp.sa.uaa.model.UserInfo;
import com.banks.erp.sa.uaa.model.GroupWiseAccessPermission.GroupWiseAccessPermissionPK;

/**
 * @author Rajib Kumer Ghosh
 *
 */

@RequestScoped
@Transactional(TxType.SUPPORTS)
public class GroupWiseAccessPermissionService implements IGroupWiseAccessPermissionService {
	
	@Inject
	private IGroupWiseAccessPermissionDao iGroupWiseAccessPermissionDao;

	@Inject
	private PersistenceDao persistenceDao;

	@Inject
	private CollectionDao collectionDao;

	@Override
	public List<GroupWiseAccessPermission> getGroupWiseAccessPermissionList(String groupID) {
		List<GroupWiseAccessPermission> groupWiseAccessPermissionList = iGroupWiseAccessPermissionDao.getGroupWiseAccessPermissionList(groupID);
		return groupWiseAccessPermissionList;
	}

	@Override
	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Transactional(TxType.REQUIRED)
	public String update(List<GroupWiseAccessPermission> groupWiseAccessPermissionList) throws Exception {
		
		// Message on Method Return 
		String successMessage = "Groups Access Permission has been Updated Successfully";
		String failedMessage = "Wrong Information, Operation has been Failed";
		
		try {
			UserInfo loggedInUser = CommonUtilService.getCurrentUserInfo();
			
			// Penetration checking
			for(GroupWiseAccessPermission groupWiseAccessPermission : groupWiseAccessPermissionList) {
				GroupWiseAccessPermissionPK groupWiseAccessPermissionPK = new GroupWiseAccessPermissionPK();
				groupWiseAccessPermissionPK.setGroupID(groupWiseAccessPermission.getGroupID());
				groupWiseAccessPermissionPK.setScreenID(groupWiseAccessPermission.getScreenID());
				
				GroupWiseAccessPermission groupWiseAccessPermission1 = collectionDao.find(GroupWiseAccessPermission.class, groupWiseAccessPermissionPK);
				groupWiseAccessPermission1.setHasViewPermission(groupWiseAccessPermission.getHasViewPermission());
				groupWiseAccessPermission1.setHasSavePermission(groupWiseAccessPermission.getHasSavePermission());
				groupWiseAccessPermission1.setHasUpdatePermission(groupWiseAccessPermission.getHasUpdatePermission());
				groupWiseAccessPermission1.setHasDeletePermission(groupWiseAccessPermission.getHasDeletePermission());
				groupWiseAccessPermission1.setStatusId("true");
				groupWiseAccessPermission1.setModifiedBy(loggedInUser.getUserName());
				groupWiseAccessPermission1.setModificationDate(CommonUtilService.getSystemOpenDate());
				
				persistenceDao.update(groupWiseAccessPermission1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			return failedMessage;
		}

		return successMessage;
	}

}
