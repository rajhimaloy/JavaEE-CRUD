package com.banks.erp.sa.uaa.iservice;

import javax.ejb.Local;

import com.banks.erp.sa.uaa.dto.UserInfoDTO;
import com.banks.erp.sa.uaa.model.UserInfo;

/**
 * @author Rajib Kumer Ghosh
 *
 */

@Local
public interface IResetPasswordService {

	public String update(UserInfoDTO userInfoDTO, UserInfo userInfo) throws Exception;

}
