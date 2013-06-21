//package il.co.topq.systems.report.tests.players;
//
//import il.co.topq.systems.report.beans.ScenarioBean;
//import il.co.topq.systems.report.beans.TestBean;
//import il.co.topq.systems.report.common.model.Scenario;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.List;
//
//public class AdminUser {
//
//	// JDBC driver name and database URL
//	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
//	static final String DB_NAME = "jsystem";
//	static final String DB_URL = "jdbc:mysql://localhost:3306/";
//	// Database credentials
//	static final String USER = "root";
//	static final String PASS = "root";
//	Connection conn = null;
//	Statement stmt = null;
//
//	@org.junit.Test
//	public void cleanDatabase() {
//
//		try {
//			// STEP 2: Register JDBC driver
//			Class.forName(JDBC_DRIVER);
//
//			// STEP 3: Open a connection
//			System.out.println("Connecting to a selected database...");
//			conn = DriverManager.getConnection(DB_URL, USER, PASS);
//			System.out.println("Connected database successfully...");
//
//			// STEP 4: Execute a query
//			System.out.println("Deleting database...");
//			stmt = conn.createStatement();
//			try {
//				String sql = "DROP DATABASE " + DB_NAME;
//				stmt.executeUpdate(sql);
//				System.out.println("Database " + DB_NAME + " deleted successfully...");
//			} catch (SQLException ex) {
//				System.out.println("Database probably does not exists therefore nothing to delete");
//			}
//		} catch (SQLException se) {
//			// Handle errors for JDBC
//			se.printStackTrace();
//		} catch (Exception e) {
//			// Handle errors for Class.forName
//			e.printStackTrace();
//		} finally {
//			// finally block used to close resources
//			try {
//				if (stmt != null)
//					conn.close();
//			} catch (SQLException se) {
//			}// do nothing
//			try {
//				if (conn != null)
//					conn.close();
//			} catch (SQLException se) {
//				se.printStackTrace();
//			}// end finally try
//		}// end try
//	}// end main
//
//	public void deleteAllRecords() {
//		ScenarioBean scenarioBean = new ScenarioBean();
//		TestBean testBean = new TestBean();
//		List<Scenario> allScenarios = scenarioBean.getAllScenarios();
//		for (Scenario scenario : allScenarios) {
//			scenarioBean.deleteScenario(scenario.getId());
//		}
//		scenarioBean.deleteOrphanProperties();
//		testBean.deleteOrphanProperties();
//	}
//
//	public void deleteAllRecordsInTable(String table) {
//		try {
//			// STEP 2: Register JDBC driver
//			Class.forName(JDBC_DRIVER);
//
//			// STEP 3: Open a connection
//			System.out.println("Connecting to a selected database...");
//			conn = DriverManager.getConnection(DB_URL, USER, PASS);
//			System.out.println("Connected database successfully...");
//
//			// STEP 4: Execute a query
//			System.out.println("Deleting " + table + " table...");
//			stmt = conn.createStatement();
//			try {
//				String sql = "use " + DB_NAME;
//				stmt.executeUpdate(sql);
//				sql = "truncate table " + table;
//				stmt.executeUpdate(sql);
//				System.out.println(table + " table deleted successfully...");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}
