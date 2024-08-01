package com.banks.erp.library.util.util;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.banks.erp.library.util.persistence.CollectionDao;
import com.it.soul.lab.sql.SQLExecutor;
import com.it.soul.lab.sql.entity.Entity;
import com.it.soul.lab.sql.query.models.Table;

@RequestScoped
@Transactional(TxType.SUPPORTS)
public class CommonUtilService {

	@Inject
	private CollectionDao collectionDao;

	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public static Date getSystemOpenDate() {
		// LocalDate currentDate = LocalDate.now();
		Date currentDate = new Date();
		return currentDate;
	}

	public static String getUserName() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		return session.getAttribute("username").toString();
	}

	public static String getUserId() {
		HttpSession session = getSession();
		if (session != null)
			return (String) session.getAttribute("userid");
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getCurrentUserInfo() {
		Subject loggedInUser = SecurityUtils.getSubject();
		T currentUser = (T) loggedInUser.getPrincipals().getPrimaryPrincipal();
		return currentUser;
	}

	@SuppressWarnings("unchecked")
	public <T> T getCurrentUser() {
		Subject loggedInUser = SecurityUtils.getSubject();
		T currentUser = (T) loggedInUser.getPrincipals().getPrimaryPrincipal();
		return currentUser;
	}
	
	public Integer getUserMaxInactiveInterval(String userName) {		
		String nativeQuery = "SELECT MaxInactiveInterval FROM ";
		String key = "MaxInactiveInterval";
		Integer maxInactiveInterval = null;
		
		try {
			maxInactiveInterval = collectionDao.executeQueryGetIntegerValue(nativeQuery, key).intValue();			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return maxInactiveInterval;		
	}

	@SuppressWarnings("rawtypes")
	@Transactional(TxType.REQUIRED)
	public <T> List<T> getResultList(String nativeQuery, Map<String, Object> params, Class T) {
		List<T> dropdownList = null;
		try {
			dropdownList = collectionDao.executeNativeQueryWithParamResultingClass(nativeQuery, params, T);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dropdownList;
	}

	@SuppressWarnings("rawtypes")
	public <T> List<T> getBranchDropdownList(String userName, String defaultBranchCode, Class T) {
		List<T> branchDropdownList = null;
		String nativeQuery = null;
		Map<String, Object> params = new HashMap<String, Object>();

		try {
			if (defaultBranchCode.equalsIgnoreCase("00000")) {
				nativeQuery = "SELECT B.BICBRANCHCODE AS stringValue, B.BICBRANCHCODE||' : '||B.BICBRANCHNAME AS stringLabel FROM SYS_BRANCHINFOANDCONFIG B";
			} else {
				nativeQuery = "SELECT A.BRANCHCODE AS stringValue, B.BICBRANCHCODE||' : '||B.BICBRANCHNAME AS stringLabel FROM SYS_USERADDITIONALBRANCHMAP A INNER JOIN SYS_BRANCHINFOANDCONFIG B ON A.BRANCHCODE = B.BICBRANCHCODE WHERE A.USERID = :userName UNION SELECT I.DEFAULTBRANCHCODE AS stringValue, B.BICBRANCHCODE||' : '||B.BICBRANCHNAME AS stringLabel FROM SYS_USERINFO I INNER JOIN SYS_BRANCHINFOANDCONFIG B ON I.DEFAULTBRANCHCODE = B.BICBRANCHCODE WHERE I.USERID = :userName";
				params.put("userName", userName);
			}

			branchDropdownList = getResultList(nativeQuery, params, T);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return branchDropdownList;
	}

	@SuppressWarnings("rawtypes")
	public <T> List<T> get21990001InstrumentTypeDropdownList(Integer reportSelection, Class T) {
		List<T> instrumentTypeDropdownList = null;
		String nativeQuery = null;
		Map<String, Object> params = new HashMap<String, Object>();

		try {
			switch (reportSelection) {

			case 1:
				nativeQuery = "SELECT INSTRUMENTTYPEID AS stringValue, INSTRUMENTTYPENAME AS stringLabel FROM VPIS_INSTRUMENTTYPE WHERE INSTRUMENTTYPEID IN ('03','04') AND STATUSID = 'true'";
				break;

			case 2:
				nativeQuery = "SELECT INSTRUMENTTYPEID AS stringValue, INSTRUMENTTYPENAME AS stringLabel FROM VPIS_INSTRUMENTTYPE WHERE INSTRUMENTTYPEID IN ('03','04') AND STATUSID = 'true'";
				break;

			case 3:
				nativeQuery = "SELECT INSTRUMENTTYPEID AS stringValue, INSTRUMENTTYPENAME AS stringLabel FROM VPIS_INSTRUMENTTYPE WHERE INSTRUMENTTYPEID IN ('03','04') AND STATUSID = 'true'";
				break;

			case 7:
				nativeQuery = "SELECT INSTRUMENTTYPEID AS stringValue, INSTRUMENTTYPENAME AS stringLabel FROM VPIS_INSTRUMENTTYPE WHERE INSTRUMENTTYPEID = ('01') AND STATUSID = 'true'";
				break;

			case 8:
				nativeQuery = "SELECT INSTRUMENTTYPEID AS stringValue, INSTRUMENTTYPENAME AS stringLabel FROM VPIS_INSTRUMENTTYPE WHERE INSTRUMENTTYPEID IN ('05','06','07') AND STATUSID = 'true'";
				break;

			case 9:
				nativeQuery = "SELECT INSTRUMENTTYPEID AS stringValue, INSTRUMENTTYPENAME AS stringLabel FROM VPIS_INSTRUMENTTYPE WHERE INSTRUMENTTYPEID = ('02') AND STATUSID = 'true'";
				break;

			default:
				System.out.println("Invalid Report Selection!");
			}

			instrumentTypeDropdownList = getResultList(nativeQuery, params, T);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return instrumentTypeDropdownList;
	}

	@SuppressWarnings("rawtypes")
	public <T> List<T> get21990001InstrumentDropdownList(String instrumentType, Class T) {
		List<T> instrumentDropdownList = null;
		String nativeQuery = null;
		Map<String, Object> params = new HashMap<String, Object>();

		try {
			switch (instrumentType) {

			case "01":
				nativeQuery = "SELECT INSTRUMENTID AS stringValue, INSTRUMENTNAME AS stringLabel FROM VPIS_INSTRUMENT WHERE INSTRUMENTTYPEID = '01' AND STATUSID = 'true'";
				break;

			case "02":
				nativeQuery = "SELECT INSTRUMENTID AS stringValue, INSTRUMENTNAME AS stringLabel FROM VPIS_INSTRUMENT WHERE INSTRUMENTTYPEID = '02' AND STATUSID = 'true'";
				break;

			case "03":
				nativeQuery = "SELECT INSTRUMENTID AS stringValue, INSTRUMENTNAME AS stringLabel FROM VPIS_INSTRUMENT WHERE INSTRUMENTTYPEID = '03' AND STATUSID = 'true'";
				break;

			case "04":
				nativeQuery = "SELECT INSTRUMENTID AS stringValue, INSTRUMENTNAME AS stringLabel FROM VPIS_INSTRUMENT WHERE INSTRUMENTTYPEID = '04' AND STATUSID = 'true'";
				break;

			case "05":
				nativeQuery = "SELECT INSTRUMENTID AS stringValue, INSTRUMENTNAME AS stringLabel FROM VPIS_INSTRUMENT WHERE INSTRUMENTTYPEID = '05' AND STATUSID = 'true'";
				break;

			case "06":
				nativeQuery = "SELECT INSTRUMENTID AS stringValue, INSTRUMENTNAME AS stringLabel FROM VPIS_INSTRUMENT WHERE INSTRUMENTTYPEID = '06' AND STATUSID = 'true'";
				break;

			case "07":
				nativeQuery = "SELECT INSTRUMENTID AS stringValue, INSTRUMENTNAME AS stringLabel FROM VPIS_INSTRUMENT WHERE INSTRUMENTTYPEID = '07' AND STATUSID = 'true'";
				break;

			default:
				System.out.println("Invalid Report Selection!");
			}

			instrumentDropdownList = getResultList(nativeQuery, params, T);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return instrumentDropdownList;
	}

	@SuppressWarnings("rawtypes")
	public <T> List<T> getClusterDropdownList(String userName, String defaultBranchCode, Class T) {
		List<T> clusterDropdownList = null;
		String nativeQuery = null;
		Map<String, Object> params = new HashMap<String, Object>();

		try {
			nativeQuery = "SELECT DISTINCT B.CLUSTER_NAME AS stringValue, B.CLUSTER_NAME AS stringLabel FROM SYS_CLUSTERREGIONAREATERTRYMAP B ORDER BY B.CLUSTER_NAME";
			clusterDropdownList = getResultList(nativeQuery, params, T);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clusterDropdownList;
	}

	@SuppressWarnings("rawtypes")
	public <T> List<T> getRegionDropdownList(String clusterName, Class T) {
		List<T> regionDropdownList = null;
		String nativeQuery = null;
		Map<String, Object> params = new HashMap<String, Object>();

		try {
			if (clusterName == null || clusterName.isEmpty()) {
				nativeQuery = "SELECT DISTINCT B.REGION_NAME AS stringValue, B.REGION_NAME AS stringLabel FROM SYS_CLUSTERREGIONAREATERTRYMAP B ORDER BY B.REGION_NAME";
			} else {
				nativeQuery = "SELECT DISTINCT B.REGION_NAME AS stringValue, B.REGION_NAME AS stringLabel FROM SYS_CLUSTERREGIONAREATERTRYMAP B WHERE B.CLUSTER_NAME = :clusterName ORDER BY B.REGION_NAME";
				params.put("clusterName", clusterName);
			}
			
			regionDropdownList = getResultList(nativeQuery, params, T);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return regionDropdownList;
	}

	@SuppressWarnings("rawtypes")
	public <T> List<T> getAreaDropdownList(String regionName, Class T) {
		List<T> areaDropdownList = null;
		String nativeQuery = null;
		Map<String, Object> params = new HashMap<String, Object>();

		try {
			if (regionName == null || regionName.isEmpty()) {
				nativeQuery = "SELECT DISTINCT B.AREA_NAME AS stringValue, B.AREA_NAME AS stringLabel FROM SYS_CLUSTERREGIONAREATERTRYMAP B ORDER BY B.AREA_NAME";
			} else {
				nativeQuery = "SELECT DISTINCT B.AREA_NAME AS stringValue, B.AREA_NAME AS stringLabel FROM SYS_CLUSTERREGIONAREATERTRYMAP B WHERE B.REGION_NAME = :regionName ORDER BY B.AREA_NAME";
				params.put("regionName", regionName);
			}
			
			areaDropdownList = getResultList(nativeQuery, params, T);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return areaDropdownList;
	}

	@SuppressWarnings("rawtypes")
	public <T> List<T> getTeritoryDropdownList(String areaName, Class T) {
		List<T> teritoryDropdownList = null;
		String nativeQuery = null;
		Map<String, Object> params = new HashMap<String, Object>();

		try {
			if (areaName == null || areaName.isEmpty()) {
				nativeQuery = "SELECT DISTINCT B.TERITORY_NAME AS stringValue, B.TERITORY_NAME AS stringLabel FROM SYS_CLUSTERREGIONAREATERTRYMAP B ORDER BY B.TERITORY_NAME";
			} else {
				nativeQuery = "SELECT DISTINCT B.TERITORY_NAME AS stringValue, B.TERITORY_NAME AS stringLabel FROM SYS_CLUSTERREGIONAREATERTRYMAP B WHERE B.AREA_NAME = :areaName ORDER BY B.TERITORY_NAME";
				params.put("areaName", areaName);
			}
			
			teritoryDropdownList = getResultList(nativeQuery, params, T);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return teritoryDropdownList;
	}

	@SuppressWarnings("rawtypes")
	public <T> List<T> getVisitCountsList(Class T) {
		List<T> visitCountList = null;
		String nativeQuery = null;
		Map<String, Object> params = new HashMap<String, Object>();

		try {
			nativeQuery = "SELECT \r\n" + 
    				" (SELECT COUNT(*) AS totalUddokta FROM PRISM_UDDOKTAS) AS totalUddokta, \r\n" + 
    				" (SELECT COUNT(*) AS totalUddoktaVisitedMTD FROM TMR_DATAS WHERE CREATE_DATE BETWEEN TO_CHAR(ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -0), 'DD-MON-YY') AND SYSDATE) AS  totalUddoktaVisitedMTD, \r\n" + 
    				" (SELECT COUNT(DISTINCT fk_uddokta_id) AS uniqueUddoktaVisitedMTD FROM TMR_DATAS WHERE CREATE_DATE BETWEEN TO_CHAR(ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -0), 'DD-MON-YY') AND SYSDATE) AS uniqueUddoktaVisitedMTD, \r\n" + 
    				" (SELECT COUNT(*) AS lastMonthTotalUddoktaVisited FROM TMR_DATAS WHERE CREATE_DATE BETWEEN TO_CHAR(ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -1), 'DD-MON-YY') AND TO_CHAR(LAST_DAY(ADD_MONTHS(SYSDATE, -1)), 'DD-MON-YY')) AS lastMonthTotalUddoktaVisited, \r\n" + 
    				" (SELECT COUNT(DISTINCT fk_uddokta_id) AS lastMonthUniqueUddoktaVisited FROM TMR_DATAS WHERE CREATE_DATE BETWEEN TO_CHAR(ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -1), 'DD-MON-YY') AND TO_CHAR(LAST_DAY(ADD_MONTHS(SYSDATE, -1)), 'DD-MON-YY')) AS lastMonthUniqueUddoktaVisited, \r\n" + 
    				" (SELECT COUNT(DISTINCT fk_uddokta_id) AS last2ndMonthUniqueUddoktaVisited FROM TMR_DATAS WHERE CREATE_DATE BETWEEN TO_CHAR(ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -2), 'DD-MON-YY') AND TO_CHAR(LAST_DAY(ADD_MONTHS(SYSDATE, -2)), 'DD-MON-YY')) AS last2ndMonthUniqueUddoktaVisited, \r\n" + 
    				" (SELECT COUNT(DISTINCT fk_uddokta_id) AS last3rdMonthUniqueUddoktaVisited FROM TMR_DATAS WHERE CREATE_DATE BETWEEN TO_CHAR(ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -3), 'DD-MON-YY') AND TO_CHAR(LAST_DAY(ADD_MONTHS(SYSDATE, -3)), 'DD-MON-YY')) AS last3rdMonthUniqueUddoktaVisited \r\n" + 
    				" FROM DUAL ";
			visitCountList = getResultList(nativeQuery, params, T);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return visitCountList;
	}
	
	@SuppressWarnings("rawtypes")
	public <T> List<T> getUniqueUddoktaVisitCountsLast7DaysList(Class T) {
		List<T> visitCountList = null;
		String nativeQuery = null;
		Map<String, Object> params = new HashMap<String, Object>();

		try {
			nativeQuery = "SELECT TO_CHAR(CREATE_DATE, 'DD-Mon') AS createDate, COUNT(DISTINCT fk_uddokta_id) AS uniqueUddoktaVisitedLast7Days \r\n" + 
    				" FROM TMR_DATAS \r\n" + 
    				" WHERE CREATE_DATE BETWEEN SYSDATE-8 AND SYSDATE \r\n" + 
    				" GROUP BY CREATE_DATE ORDER BY CREATE_DATE ASC ";
			visitCountList = getResultList(nativeQuery, params, T);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return visitCountList;
	}
	
	@SuppressWarnings("rawtypes")
	public <T> List<T> getTmrAttendanceLast7DaysList(Class T) {
		List<T> visitCountList = null;
		String nativeQuery = null;
		Map<String, Object> params = new HashMap<String, Object>();

		try {
			nativeQuery = "SELECT TO_CHAR(CREATE_DATE, 'DD-Mon') AS createDate, COUNT(DISTINCT fk_tmo_id) AS tmrAttendanceLast7Days \r\n" + 
    				" FROM TMR_DATAS \r\n" + 
    				" WHERE CREATE_DATE BETWEEN SYSDATE-8 AND SYSDATE \r\n" + 
    				" GROUP BY CREATE_DATE ORDER BY CREATE_DATE ASC ";
			visitCountList = getResultList(nativeQuery, params, T);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return visitCountList;
	}
	
	@SuppressWarnings("rawtypes")
	public <T> List<T> getTmrAttendancesRegionWiseList(Class T, Integer roleID, String userStatus) {
		List<T> countList = null;
		String nativeQuery = null;
		Map<String, Object> params = new HashMap<String, Object>();

		try {
			nativeQuery = "SELECT tm.regions AS groupName, COUNT(tm.tmr_id) AS tmrTotal, COUNT(d.tmr_id) AS tmrPresence \r\n" + 
					" FROM (SELECT tmo.pk_user_id AS tmr_id, regions.u_firstname AS regions \r\n" + 
					" FROM PRISM_USERS tmo \r\n" + 
					" INNER JOIN PRISM_USERS dh ON dh.pk_user_id = tmo.fk_user_id \r\n" + 
					" INNER JOIN PRISM_USERS tm ON tm.pk_user_id = dh.fk_user_id \r\n" + 
					" INNER JOIN PRISM_USERS regions ON regions.pk_user_id = tm.fk_user_id \r\n" + 
					" WHERE tmo.U_STATUS = 'active' AND tmo.U_ROLE = 5) tm \r\n" + 
					" LEFT JOIN (SELECT DISTINCT fk_tmo_id AS tmr_id FROM TMR_DATAS WHERE CREATE_DATE BETWEEN SYSDATE-2 AND SYSDATE-1) d ON d.tmr_id = tm.tmr_id \r\n" + 
					" GROUP BY tm.regions ORDER BY tm.regions ASC ";
			countList = getResultList(nativeQuery, params, T);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return countList;
	}
	
	@SuppressWarnings("rawtypes")
	public <T> List<T> getTmrAttendancesClusterWiseList(Class T, Integer roleID, String userStatus) {
		List<T> countList = null;
		String nativeQuery = null;
		Map<String, Object> params = new HashMap<String, Object>();

		try {
			nativeQuery = "SELECT tm.clusters AS groupName, COUNT(tm.tmr_id) AS tmrTotal, COUNT(d.tmr_id) AS tmrPresence \r\n" + 
					" FROM (SELECT tmo.pk_user_id AS tmr_id, clusters.u_firstname AS clusters \r\n" + 
					" FROM PRISM_USERS tmo \r\n" + 
					" INNER JOIN PRISM_USERS dh ON dh.pk_user_id = tmo.fk_user_id \r\n" + 
					" INNER JOIN PRISM_USERS tm ON tm.pk_user_id = dh.fk_user_id \r\n" + 
					" INNER JOIN PRISM_USERS regions ON regions.pk_user_id = tm.fk_user_id \r\n" + 
					" INNER JOIN PRISM_USERS clusters ON clusters.pk_user_id = regions.fk_user_id \r\n" + 
					" WHERE tmo.U_STATUS = 'active' AND tmo.U_ROLE = 5) tm \r\n" + 
					" LEFT JOIN (SELECT DISTINCT fk_tmo_id AS tmr_id FROM TMR_DATAS WHERE CREATE_DATE BETWEEN SYSDATE-2 AND SYSDATE-1) d ON d.tmr_id = tm.tmr_id \r\n" + 
					" GROUP BY tm.clusters ORDER BY tm.clusters ASC ";
			countList = getResultList(nativeQuery, params, T);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return countList;
	}

	public List<Number> getNumberValueList(String nativeQuery, String key) {
		List<Number> numberList = null;
		try {
			numberList = collectionDao.executeQueryNumberList(nativeQuery, key);			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return numberList;
	}
	
	public <T extends Entity> List<T> getGoogleMapDTOList(Class<T> type) {
		Map<Integer, Object> params = new HashMap<Integer, Object>();
		
		/*String nativeQuery = "SELECT d.PRISM_START_LAT AS lattitude, d.PRISM_START_LONG AS longitude, (u.U_FIRSTNAME||' '||u.U_LASTNAME) AS title, "
				+ "'rajib1.png' AS imageName, 'https://maps.google.com/mapfiles/ms/micons/green-dot.png' AS imagePath "
				+ " FROM PRISM_DAY_START_END d "
				+ " INNER JOIN PRISM_USERS u ON u.PK_USER_ID = d.FK_USER_ID WHERE u.U_STATUS = 'active' ";*/
		
		String nativeQuery = "SELECT lattitude, longitude, title, imageName, imagePath, PK_USER_ID, dayStarted FROM " + 
				" (SELECT CASE WHEN PRISM_CURRENT_HOME_LAT IS NULL THEN 23.728472987337593 ELSE CAST(PRISM_CURRENT_HOME_LAT AS NUMBER) END AS lattitude, " + 
				" CASE WHEN PRISM_CURRENT_HOME_LONG IS NULL THEN 90.4111834696704 ELSE CAST(PRISM_CURRENT_HOME_LONG AS NUMBER) END AS longitude, " + 
				" (U_FIRSTNAME||' '||U_LASTNAME) AS title, 'rajib2.png' AS imageName, 'https://maps.google.com/mapfiles/ms/micons/red-dot.png' AS imagePath ,PK_USER_ID, 0 AS dayStarted " + 
				" FROM PRISM_USERS WHERE U_STATUS = 'active' AND U_ROLE = 5 " + 
				" MINUS " + 
				" SELECT d.PRISM_START_LAT AS lattitude, d.PRISM_START_LONG AS longitude, (u.U_FIRSTNAME||' '||u.U_LASTNAME) AS title, " + 
				" 'rajib1.png' AS imageName, 'https://maps.google.com/mapfiles/ms/micons/green-dot.png' AS imagePath ,u.PK_USER_ID, 1 AS dayStarted " + 
				" FROM PRISM_DAY_START_END d INNER JOIN PRISM_USERS u ON u.PK_USER_ID = d.FK_USER_ID WHERE u.U_STATUS = 'active') "
				+ " UNION "
				+ " SELECT d.PRISM_START_LAT AS lattitude, d.PRISM_START_LONG AS longitude, (u.U_FIRSTNAME||' '||u.U_LASTNAME) AS title, " + 
				" 'rajib1.png' AS imageName, 'https://maps.google.com/mapfiles/ms/micons/green-dot.png' AS imagePath ,u.PK_USER_ID, 1 AS dayStarted " + 
				" FROM PRISM_DAY_START_END d INNER JOIN PRISM_USERS u ON u.PK_USER_ID = d.FK_USER_ID WHERE u.U_STATUS = 'active' ";
		/*String nativeQuery = "SELECT 22.94707010 AS lattitude, 91.11956490 AS longitude, 'Rajib Kumer Ghosh' AS title, 'rajib1.png' AS imageName, 'https://maps.google.com/mapfiles/ms/micons/blue-dot.png' AS imagePath FROM DUAL "
				+ " UNION "
				+ "SELECT 22.27930060 AS lattitude, 91.77933850 AS longitude, 'Towhidul Islam' AS title, 'rajib2.png' AS imageName, 'https://maps.google.com/mapfiles/ms/micons/green-dot.png' AS imagePath FROM DUAL "
				+ " UNION "
				+ "SELECT 22.66135400 AS lattitude, 89.78713180 AS longitude, 'Moshfiqur Rahman' AS title, 'rajib3.png' AS imageName, 'https://maps.google.com/mapfiles/ms/micons/pink-dot.png' AS imagePath FROM DUAL "
				+ " UNION "
				+ "SELECT 23.55452930 AS lattitude, 90.65403740 AS longitude, 'Md. Sharif Newaz Chowdhury' AS title, 'rajib4.png' AS imageName, 'https://maps.google.com/mapfiles/ms/micons/yellow-dot.png' AS imagePath FROM DUAL "
				+ " UNION "
				+ "SELECT 23.01086200 AS lattitude, 91.39623060 AS longitude, 'Moinuddin Ahmed' AS title, 'rajib5.png' AS imageName, 'https://maps.google.com/mapfiles/ms/micons/red-dot.png' AS imagePath FROM DUAL ";*/

		try (SQLExecutor executor = new SQLExecutor(null)) {
			ResultSet resultSet = collectionDao.executeQueryResultSetWithParam(nativeQuery, params);
			Table tbl = executor.collection(resultSet);
			List<T> res = tbl.inflate(type, Entity.mapColumnsToProperties(type));
			//resultSet.close();
			executor.close();
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
}
