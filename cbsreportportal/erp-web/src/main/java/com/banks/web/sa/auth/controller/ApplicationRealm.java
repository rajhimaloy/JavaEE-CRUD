package com.banks.web.sa.auth.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.banks.erp.library.util.crypto.PasswordUtil;
import com.banks.erp.library.util.enums.Permission;
import com.banks.erp.library.util.enums.Status;
import com.banks.erp.sa.uaa.iservice.IGroupWiseAccessPermissionService;
import com.banks.erp.sa.uaa.iservice.IUserSetupService;
import com.banks.erp.sa.uaa.iservice.IUserWiseGroupMapService;
import com.banks.erp.sa.uaa.model.GroupWiseAccessPermission;
import com.banks.erp.sa.uaa.model.UserInfo;
import com.banks.erp.sa.uaa.model.UserWiseGroupMap;

/**
 * @author Rajib Kumer Ghosh
 *
 */

public class ApplicationRealm extends AuthorizingRealm {

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken at) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) at;
		String userName = token.getUsername();
		String password = String.valueOf(token.getPassword());
		String host = token.getHost();

		PasswordService passwordService = new DefaultPasswordService();

		try {
			IUserSetupService userSetupService = userSetupService();
			UserInfo user = userSetupService.getUserinfo(userName);

			AuthenticationInfo authcInfo = null;
			authcInfo = new SimpleAuthenticationInfo(user, password, getName());

			//TODO Password Encryption in DB
			//if (passwordService.passwordsMatch(password, passwordService.encryptPassword(user.getPassword()))) {
			if (passwordService.passwordsMatch(PasswordUtil.digestPassword(password), passwordService.encryptPassword(user.getPassword()))) {
				if (user.getStatusId().equals(Status.TRUE.getValue())) {
					return authcInfo;
				} else {
					throw new LockedAccountException();
				}
			} else {

				throw new IncorrectCredentialsException();

			}
		} catch (Exception ex) {
			System.out.println("IncorrectCredentialsException. Please contact with Rajib Kumer Ghosh");
			throw new AccountException();
		}
	}

	@SuppressWarnings("null")
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
		System.out.println("---- Rajib Kumer Ghosh.........ok-");
		UserInfo user = (UserInfo) pc.getPrimaryPrincipal();
		SimpleAuthorizationInfo authzInfo = new SimpleAuthorizationInfo();
		
		IUserWiseGroupMapService userWiseGroupMapService = userWiseGroupMapService();
		IGroupWiseAccessPermissionService groupWiseAccessPermissionService = groupWiseAccessPermissionService();

		//Multi-Role
		List<UserWiseGroupMap> userWiseGroupMapList = userWiseGroupMapService.getUserWiseGroupMapActiveList(user.getUserId());
		
		List<GroupWiseAccessPermission> groupWiseAccessPermissionList = new ArrayList<GroupWiseAccessPermission>();
		for (UserWiseGroupMap userWiseGroupMap : userWiseGroupMapList) {
			List<GroupWiseAccessPermission> groupWiseAccessPermissionList1 = groupWiseAccessPermissionService.getGroupWiseAccessPermissionList(userWiseGroupMap.getGroupId());
			groupWiseAccessPermissionList.addAll(groupWiseAccessPermissionList1);
		}

		Set<String> roles = new HashSet<>();
		Set<String> permissions = new HashSet<>();

		groupWiseAccessPermissionList.parallelStream().forEach(s -> {
			if (s.getHasViewPermission()) {
				roles.add(s.getScreenID().toString());
				permissions.add(s.getScreenID() + ":" + Permission.VIEW);
			}

			if (s.getHasSavePermission()) {
				permissions.add(s.getScreenID() + ":" + Permission.SAVE);
			}

			if (s.getHasUpdatePermission()) {
				permissions.add(s.getScreenID() + ":" + Permission.UPDATE);
			}

			if (s.getHasDeletePermission()) {
				permissions.add(s.getScreenID() + ":" + Permission.DELETE);
			}
		});

		authzInfo.addRoles(roles);
		authzInfo.addStringPermissions(permissions);
		return authzInfo;
	}

	private IUserSetupService userSetupService() {
		Class<?> serviceClass = IUserSetupService.class;
		BeanManager manager = CDI.current().getBeanManager();
		Set<Bean<?>> beans = manager.getBeans(serviceClass);
		Bean<?> bean = (Bean<?>) manager.resolve(beans);
		CreationalContext<?> cxt = manager.createCreationalContext(bean);
		Object obj = manager.getReference(bean, serviceClass, cxt);
		return (IUserSetupService) obj;
	}

	private IUserWiseGroupMapService userWiseGroupMapService() {
		Class<?> serviceClass = IUserWiseGroupMapService.class;
		BeanManager manager = CDI.current().getBeanManager();
		Set<Bean<?>> beans = manager.getBeans(serviceClass);
		Bean<?> bean = (Bean<?>) manager.resolve(beans);
		CreationalContext<?> cxt = manager.createCreationalContext(bean);
		Object obj = manager.getReference(bean, serviceClass, cxt);
		return (IUserWiseGroupMapService) obj;
	}

	private IGroupWiseAccessPermissionService groupWiseAccessPermissionService() {
		Class<?> serviceClass = IGroupWiseAccessPermissionService.class;
		BeanManager manager = CDI.current().getBeanManager();
		Set<Bean<?>> beans = manager.getBeans(serviceClass);
		Bean<?> bean = (Bean<?>) manager.resolve(beans);
		CreationalContext<?> cxt = manager.createCreationalContext(bean);
		Object obj = manager.getReference(bean, serviceClass, cxt);
		return (IGroupWiseAccessPermissionService) obj;
	}

}
