package il.co.topq.systems.report.tests.unit;

import il.co.topq.systems.report.common.infra.log.ReportLogger;

import org.apache.log4j.Logger;
import org.junit.Ignore;

@Ignore
public class ExportServiceTest {

    Logger log = ReportLogger.getInstance().getLogger(this.getClass());

	// @org.junit.Test
	// public void exportScenarioPDF() throws UnknownHostException,
	// JAXBException {
	//
	// Client c = Client.create();
	// // WebResource r = c.resource(ServiceUtils.getHost()
	// // + "/report-service/report/export/scenario/xls");
	//
	// ScenarioQuery scenarioQuery = new ScenarioQuery();
	//
	// JAXBContext ctx = JAXBContext.newInstance(ScenarioQuery.class);
	// StringWriter writer = new StringWriter();
	// ctx.createMarshaller().marshal(scenarioQuery, writer);
	// String customString = writer.toString();
	// log.info(customString);
	//
	// try {
	// r.accept(MediaType.APPLICATION_XML)
	// .type(MediaType.APPLICATION_XML)
	// .post(scenarioQuery);
	//
	// } catch (Exception ex) {
	// log.error(ex);
	// }
	// }
    


}
