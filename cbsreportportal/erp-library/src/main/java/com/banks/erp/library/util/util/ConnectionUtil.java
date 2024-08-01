/**
 * 
 */
package com.banks.erp.library.util.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.ejb.Stateful;

/**
 * @author Rajib Kumer Ghosh
 *
 */

@Stateful
public class ConnectionUtil {
	public Connection getMySQLConnection() throws ClassNotFoundException, SQLException {
		String hostName = "localhost";
		String dbName = "test";
		String userName = "root";
		String password = "Abcd@1234";
		String url = "?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=GMT";
		return getMySQLConnection(hostName, dbName, userName, password, url);
	}

	public Connection getMySQLConnection(String hostName, String dbName, String userName, String password, String url)
			throws SQLException, ClassNotFoundException {

		// If you use Java> 5, then this line is not needed.
		// Class.forName("com.mysql.cj.jdbc.Driver");
		Class.forName("com.mysql.jdbc.Driver");
		String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName + url;

		Connection conn = DriverManager.getConnection(connectionURL, userName, password);
		return conn;
	}

	public Connection getOracleConnection() throws ClassNotFoundException, SQLException {
		String hostName = "localhost";
		String sid = "bankdb";
		String userName = "erprptdb";
		String password = "erprptdb";

		return getOracleConnection(hostName, sid, userName, password);
	}

	public Connection getOracleConnection(String hostName, String sid, String userName, String password)
			throws ClassNotFoundException, SQLException {

		// If you use Java> 5, then this line is not needed.
		Class.forName("oracle.jdbc.driver.OracleDriver");

		String connectionURL = "jdbc:oracle:thin:@" + hostName + ":1521:" + sid;

		Connection conn = DriverManager.getConnection(connectionURL, userName, password);
		return conn;
	}

	// Connect to SQLServer
	// (Using JDBC Driver: SQLJDBC)
	public Connection getSQLServerConnection_SQLJDBC() throws ClassNotFoundException, SQLException {
		String hostName = "localhost";
		String sqlInstanceName = "SQLEXPRESS";
		String database = "bank";
		String userName = "sa";
		String password = "Abcd@1234";

		return getSQLServerConnection_SQLJDBC(hostName, sqlInstanceName, database, userName, password);
	}

	// Connect to SQLServer & using JTDS library
	public Connection getSQLServerConnection_JTDS() throws SQLException, ClassNotFoundException {
		String hostName = "Rajib-PC";
		String sqlInstanceName = "SQLEXPRESS";
		String database = "bank";
		String userName = "sa";
		String password = "Abcd@1234";

		return getSQLServerConnection_JTDS(hostName, sqlInstanceName, database, userName, password);
	}

	// Connect to SQLServer
	// (Using JDBC Driver: SQLJDBC)
	private Connection getSQLServerConnection_SQLJDBC(String hostName, String sqlInstanceName, String database,
			String userName, String password) throws ClassNotFoundException, SQLException {

		Connection conn = null;
		try {
			// If you use Java> 5, then this line is not needed.
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			// jdbc:sqlserver://ServerIp:1433/SQLEXPRESS;databaseName=simplehr
			String connectionURL = "jdbc:sqlserver://" + hostName + ":1433" + ";instance=" + sqlInstanceName
					+ ";databaseName=" + database;

			conn = DriverManager.getConnection(connectionURL, userName, password);
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}

		return conn;
	}

	// Connect to SQLServer & using JTDS library
	private Connection getSQLServerConnection_JTDS(String hostName, String sqlInstanceName, String database,
			String userName, String password) throws ClassNotFoundException, SQLException {

		// If you use Java> 5, then this line is not needed.
		Class.forName("net.sourceforge.jtds.jdbc.Driver");

		// jdbc:jtds:sqlserver://localhost:1433/simplehr;instance=SQLEXPRESS
		String connectionURL = "jdbc:jtds:sqlserver://" + hostName + ":1433/" + database + ";instance="
				+ sqlInstanceName;

		Connection conn = DriverManager.getConnection(connectionURL, userName, password);
		return conn;
	}
}
