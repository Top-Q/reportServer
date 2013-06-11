//package il.co.topq.systems.report.component.settings;
//
//import il.co.topq.systems.report.beans.SettingsBean;
//import il.co.topq.systems.report.common.infra.ReportLogger;
//import il.co.topq.systems.report.common.obj.GridConfiguration;
//import il.co.topq.systems.report.common.obj.GridConfiguration.Type;
//import il.co.topq.systems.report.common.obj.SystemSettings;
//
//import java.io.IOException;
//import java.sql.SQLException;
//
//import javax.sql.rowset.serial.SerialException;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import javax.xml.bind.JAXBElement;
//import javax.xml.namespace.QName;
//
//import org.apache.log4j.Logger;
//
//@Path("/settings/system")
//public class SettingsWebService {
//
//	private Logger log = ReportLogger.getInstance().getLogger(this.getClass());
//	private SettingsBean settingsService = new SettingsBean();
//
//	@GET
//	@Produces(MediaType.APPLICATION_XML)
//	@Consumes(MediaType.APPLICATION_XML)
//	public SystemSettings getSystemSettings() throws IOException, ClassNotFoundException, SerialException, SQLException {
//		return settingsService.getSystemSettings();
//	}
//
//	@Path("/create")
//	@POST
//	@Produces(MediaType.APPLICATION_XML)
//	@Consumes(MediaType.APPLICATION_XML)
//	public JAXBElement<Boolean> createSystemSettings(SystemSettings systemSettings) throws IOException,
//			SerialException, SQLException {
//		settingsService.createSystemSettings(systemSettings);
//		return new JAXBElement<Boolean>(new QName("Boolean"), Boolean.class, true);
//	}
//
//	@Path("/update")
//	@POST
//	@Produces(MediaType.APPLICATION_XML)
//	@Consumes(MediaType.APPLICATION_XML)
//	public JAXBElement<Boolean> updateSystemSettings(SystemSettings systemSettings) throws IOException,
//			SerialException, SQLException, ClassNotFoundException {
//		settingsService.updateSystemSettings(systemSettings);
//		return new JAXBElement<Boolean>(new QName("Boolean"), Boolean.class, true);
//	}
//
//	@GET
//	@Produces(MediaType.APPLICATION_XML)
//	@Path("/getGridConfiguration/{typeStr}/{id}")
//	public GridConfiguration getGridConfiguration(@PathParam("typeStr") String typeStr, @PathParam("id") Integer id)
//			throws Exception {
//		try {
//			log.info("in getGridConfiguration web service");
//			Type type = Type.valueOf(typeStr);
//			log.info("type rcvd: " + type.toString());
//			log.info("id rcvd: " + id);
//
//			GridConfiguration gridConfiguration = settingsService.getGridConfiguration(type, id);
//			return gridConfiguration;
//		} catch (Exception e) {
//			log.error(e);
//			e.printStackTrace();
//			throw e;
//		}
//	}
//
//	/**
//	 * This method will get a gridConfiguration and write it to DB<br>
//	 * if grid configuration already exist it will call update<br>
//	 * else will create a new grid configuration.
//	 * 
//	 * @param gridConfiguration
//	 * @return Status OK if created.<br>
//	 * @return Status NOT_ACCEPTABLE if not created<br>
//	 * @throws Exception
//	 */
//	@POST
//	@Produces(MediaType.APPLICATION_XML)
//	@Path("/createGridConfiguration/")
//	public Response createGridConfiguration(GridConfiguration gridConfiguration) throws Exception {
//
//		try {
//			GridConfiguration retrievedConfiguration = settingsService.getGridConfiguration(
//					gridConfiguration.getGridConfigurationType(), gridConfiguration.getId());
//			if (retrievedConfiguration != null) {
//				settingsService.updateGridConfiguration(gridConfiguration);
//			} else {
//				Integer settingsID = settingsService.createGridConfiguration(gridConfiguration);
//				if (settingsID == null) {
//					return Response.ok().entity(false).build();
//				}
//			}
//			return Response.ok().entity(true).build();
//		} catch (Exception e) {
//			log.error(e);
//			throw e;
//		}
//	}
//
// }
