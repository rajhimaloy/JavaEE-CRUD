package com.banks.erp.library.util.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

/**
 * @author Rajib Kumer Ghosh
 *
 */

@Transactional(TxType.SUPPORTS)
public class CollectionDao {

	// @PersistenceContext(unitName = "rajibkgtrans-pu")
	@PersistenceContext
	private EntityManager entityManager;
	
	@Resource(name = "rajibkg_ds_oracle_rpt")
	private DataSource ds;

	@SuppressWarnings("unchecked")
	public <T> T selectSingle(String queryName, Map<String, Object> params) throws Exception {
		T t = null;
		Query query = entityManager.createNamedQuery(queryName);
		if (params != null) {
			for (Entry<String, Object> e : params.entrySet()) {
				query.setParameter(e.getKey(), e.getValue());
			}
		}
		t = (T) query.getSingleResult();
		entityManager.flush();
		entityManager.clear();
		return t;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> executeNamedQuery(String queryName, Map<String, Object> params) throws Exception {
		List<T> result = new ArrayList<>();
		Query query = entityManager.createNamedQuery(queryName);
		if (params != null) {
			for (Entry<String, Object> e : params.entrySet()) {
				query.setParameter(e.getKey(), e.getValue());
			}
		}
		result = query.getResultList();
		entityManager.flush();
		entityManager.clear();
		return result;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> executeQueryWithParam(String queryName, Map<String, Object> params) throws Exception {
		List<T> result = new ArrayList<>();
		Query query = entityManager.createQuery(queryName);
		if (params != null) {
			for (Entry<String, Object> e : params.entrySet()) {
				query.setParameter(e.getKey(), e.getValue());
			}
		}
		result = query.getResultList();
		entityManager.flush();
		entityManager.clear();
		return result;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> executeQuery(String queryName) throws Exception {
		List<T> result = new ArrayList<>();
		Query query = entityManager.createQuery(queryName);
		result = (List<T>) query.getResultList();
		// entityManager.flush();
		// entityManager.clear();
		return result;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> executeNativeQuery(String nativeName) throws Exception {
		List<T> result = new ArrayList<>();
		Query query = entityManager.createNativeQuery(nativeName);
		result = query.getResultList();
		entityManager.flush();
		entityManager.clear();
		return result;
	}

	@SuppressWarnings({ "unchecked" })
	public <T> List<T> executeNativeQueryWithParam(String nativeQuery, Map<String, Object> params) throws Exception {
		List<T> result = new ArrayList<>();
		Query query = entityManager.createNativeQuery(nativeQuery);
		if (params != null) {
			for (Entry<String, Object> e : params.entrySet()) {
				query.setParameter(e.getKey(), e.getValue());
			}
		}
		result = (List<T>) query.getResultList(); 
		entityManager.clear();
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> executeNativeQueryWithParamResultingClass(String nativeQuery, Map<String, Object> params, Class T) throws Exception {
		List<T> result = new ArrayList<>();
		Query query = entityManager.createNativeQuery(nativeQuery, T);
		if (params != null) {
			for (Entry<String, Object> e : params.entrySet()) {
				query.setParameter(e.getKey(), e.getValue());
			}
		}
		result = (List<T>) query.getResultList(); 
		entityManager.clear();
		return result;
	}

	public <T> T find(final Class<T> entityClass, final Object id) throws Exception {
		final T entity = entityManager.find(entityClass, id);
		entityManager.clear();

		if (entity != null) {
			return entity;
		} else {
			throw new Exception();
		}
	}

	public <T> Boolean findByID(final Class<T> entityClass, final Object id) throws Exception {
		final T entity = (T) entityManager.find(entityClass, id);
		entityManager.clear();
		if (entity == null) {
			return false;
		} else {
			return true;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T loadById(long id, Class c) {
		T t = (T) entityManager.find(c, id);
		entityManager.clear();
		return t;
	}
	
	//TODO this is not required because executeNativeQueryWithParamResultingClass() can be used for it.
	public List<Number> executeQueryNumberList(String sql, String key) throws Exception {		
		Connection conn = ds.getConnection();
		Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet resultSet = statement.executeQuery(sql);	
		
		List<Number> numberList = new ArrayList<Number>();		
		while(resultSet.next()) {
			numberList.add(resultSet.getInt(key));
		}
		
		resultSet.close();
		statement.close();
		conn.close();
		return numberList;
	}

	//TODO this is not required because executeNativeQueryWithParamResultingClass() can be used for it.
	public Number executeQueryGetIntegerValue(String nativeQuery, String key) throws SQLException {		
		Connection conn = ds.getConnection();
		Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet resultSet = statement.executeQuery(nativeQuery);	
		
		Number value = null; 		
		while (resultSet.next()) {
			value = resultSet.getInt(key);
		}		

		resultSet.close();
		statement.close();
		conn.close();
		return value;
	}	

	/*
	 *  ResultSet default type TYPE_FORWARD_ONLY does not allow first(), last(), previous(), absolute or relative()
	 *  Hence use ResultSet.TYPE_SCROLL_INSENSITIVE or ResultSet.TYPE_SCROLL_SENSITIVE, but slow performance.
	 *  ResultSet default concurrency mode CONCUR_READ_ONLY does not allow first(), last(), previous(), absolute or relative() in JDBC java
	 *  Hence use ResultSet.CONCUR_UPDATABLE, but slow performance.
	 */
	/*public ResultSet executeQueryResultSet(String sql) throws Exception {		
		Connection conn = ds.getConnection();
		Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet resultSet = statement.executeQuery(sql);
		return resultSet;
	}*/
	
	//TODO this is not required because executeNativeQueryWithParamResultingClass() can be used for it.
	public ResultSet executeQueryResultSetWithParam(String sql, Map<Integer, Object> params) throws Exception {		
		Connection conn = ds.getConnection();
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		if (params != null) {
			for (Entry<Integer, Object> e : params.entrySet()) {
				preparedStatement.setObject(e.getKey(), e.getValue());
			}
		}
		ResultSet resultSet = preparedStatement.executeQuery();	
		//conn.close();	
		return resultSet;
	}
	
	
}