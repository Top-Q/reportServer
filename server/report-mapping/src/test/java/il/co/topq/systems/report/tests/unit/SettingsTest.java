//package il.co.topq.systems.report.tests.unit;
//
//import il.co.topq.systems.report.beans.SettingsBean;
//import il.co.topq.systems.report.common.infra.ReportLogger;
//import il.co.topq.systems.report.common.model.Settings;
//import il.co.topq.systems.report.common.obj.GridConfiguration;
//import il.co.topq.systems.report.common.obj.GridConfiguration.Type;
//import il.co.topq.systems.report.tests.players.AdminUser;
//import il.co.topq.systems.report.utils.EntityBean;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import junit.framework.Assert;
//
//import org.apache.log4j.Logger;
//import org.junit.Before;
//import org.junit.Test;
//
//public class SettingsTest {
//	private Logger log = ReportLogger.getInstance().getLogger(this.getClass());
//	private SettingsBean settingsService = new SettingsBean();
//	private EntityBean<Settings> entityBean = new EntityBean<Settings>(Settings.class);
//
//	@Before
//	public void setup() {
//		new AdminUser().deleteAllRecordsInTable("settings");
//	}
//
//	/**
//	 * This method will create a grid configuration settings object and will
//	 * assert all it of its data.<br>
//	 * 
//	 * @assumption List<String> columns keeps the original order of columns
//	 * @throws Exception
//	 */
//	@org.junit.Test
//	public void getGridConfigurationSettings() throws Exception {
//		Integer id = GridConfiguration.NO_ID;
//		Integer settingsID;
//		try {
//			Type[] types = Type.values();
//			for (Type type : types) {
//				GridConfiguration mockGridConfiguration = getMockGridConfiguration(type, id);
//				log.info("Creating mock grid configuration");
//				settingsID = settingsService.createGridConfiguration(mockGridConfiguration);
//				Assert.assertNotNull(settingsID);
//				log.info("mock grid configuration created");
//
//				GridConfiguration retrievedGC = settingsService.getGridConfiguration(type, id);
//				Assert.assertEquals(id, retrievedGC.getId());
//				Assert.assertEquals(getGridColumns().size(), retrievedGC.getColumns().size());
//				List<String> columns = retrievedGC.getColumns();
//				List<String> expectedColumns = getGridColumns();
//				for (int i = 0; i < expectedColumns.size(); i++) {
//					Assert.assertEquals(expectedColumns.get(i), retrievedGC.getColumns().get(i));
//				}
//				log.info("Columns in created grid match the columns in retrieved grid by name and order");
//			}
//
//		} catch (Exception e) {
//			log.error(e);
//			throw e;
//		}
//	}
//
//	/**
//	 * This method will try and retrieve a gridConfiguration from an empty
//	 * settings table.<br>
//	 * 
//	 * @throws SQLException
//	 * @throws ClassNotFoundException
//	 * @throws IOException
//	 */
//	@Test
//	public void checkGridNotFoundInEmptyDB() throws IOException, ClassNotFoundException, SQLException {
//		GridConfiguration gridConfiguration = settingsService.getGridConfiguration(Type.SCENARIO_CUSTOM_REPORT, 3);
//		Assert.assertNull(gridConfiguration);
//	}
//
//	/**
//	 * This method will create a gridConfiguration in empty DB by type x and
//	 * will try and retrieve a gridConfiguratio with same id by type y.
//	 * 
//	 * @throws IOException
//	 * @throws SQLException
//	 * @throws ClassNotFoundException
//	 * @Assumption x!=y
//	 */
//	@Test
//	public void checkGridNotFoundByType() throws IOException, ClassNotFoundException, SQLException {
//		Integer id = 2;
//		GridConfiguration mockGridConfiguration = getMockGridConfiguration(Type.SCENARIO_CUSTOM_REPORT, id);
//		Integer settingsID = settingsService.createGridConfiguration(mockGridConfiguration);
//		Assert.assertNotNull(settingsID);
//		GridConfiguration retrievedGC = settingsService.getGridConfiguration(Type.SCENARIO_DETAILS, id);
//		Assert.assertNull(retrievedGC);
//	}
//
//	/**
//	 * This method will create an empty GridConfiguration object and persist it
//	 * to DB.<br>
//	 * 
//	 * @throws Exception
//	 */
//	@SuppressWarnings("deprecation")
//	@Test
//	public void getEmptyGridConfigurationSettings() throws Exception {
//
//		GridConfiguration emptyGridConfiguration = new GridConfiguration();
//
//		try {
//			log.info("Creating empty Grid Configuration object");
//			Integer gridConfigurationID = settingsService.createGridConfiguration(emptyGridConfiguration);
//			log.info("Created Empty Grid Configuration successfully");
//
//			// Assert Default chunk size.
//			int chunkSize = emptyGridConfiguration.getChunkSize();
//			int defaultChunkSize = emptyGridConfiguration.getChunkSize();
//			log.info("Expecting: Chunk Size = Default Chunk Size(" + defaultChunkSize + ")");
//			Assert.assertEquals("Expecting chunk Size of " + defaultChunkSize, defaultChunkSize, chunkSize);
//			log.info("Result: Chunk Size = " + chunkSize);
//
//			// Assert scenario custom report Colum List == null;
//			log.info("Expecting grid columns == null");
//			List<String> scenarioCustomReportColumns = emptyGridConfiguration.getColumns();
//			Assert.assertNull(scenarioCustomReportColumns);
//			log.info("Result: grid columns = null");
//		} catch (Exception e) {
//			log.error(e);
//			throw e;
//		}
//	}
//
//	/**
//	 * This method will return a static mock column names.
//	 * 
//	 * @return List<String> represents the column names of a grid.
//	 */
//	private List<String> getGridColumns() {
//		List<String> gridCols = new ArrayList<String>();
//		gridCols.add("#");// represents the number column.
//		gridCols.add("Name");
//		gridCols.add("Duration");
//		gridCols.add("Total");
//		gridCols.add("Pass");
//		gridCols.add("Fail");
//		gridCols.add("Warning");
//
//		return gridCols;
//	}
//
//	/**
//	 * This method will create a mock grid configuration.<br>
//	 * All grid has the same columns by same order.
//	 * 
//	 * @return GridConfiguration: MOCK. for testing purpose
//	 */
//	private GridConfiguration getMockGridConfiguration(Type type, Integer id) {
//		int chunkSize = 50;
//		GridConfiguration gridConfiguration = new GridConfiguration();
//
//		gridConfiguration.setChunkSize(chunkSize);
//		gridConfiguration.setColumns(getGridColumns());
//		gridConfiguration.setGridConfigurationType(type);
//		gridConfiguration.setId(id);
//		return gridConfiguration;
//	}
//
//}
