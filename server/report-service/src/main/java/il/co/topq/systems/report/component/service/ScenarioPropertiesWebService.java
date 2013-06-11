package il.co.topq.systems.report.component.service;

import il.co.topq.systems.report.common.infra.log.ReportLogger;
import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.model.ScenarioProperty;
import il.co.topq.systems.report.component.jaxbWrappers.PropertyList;
import il.co.topq.systems.report.service.infra.PropertyService;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/scenarioProperties")
public class ScenarioPropertiesWebService {

	private Logger log = ReportLogger.getInstance().getLogger(this.getClass());

	@Autowired
	private PropertyService<ReportProperty> scenarioPropertyService;

	/**
	 * This method will get all Scenario unique keys. using a jaxBaseList object
	 * as a wrapper; from the user side need to call the getList method to get
	 * the list of <T> from the object;
	 * 
	 * @return Response wrapping jaxBaseList;
	 */
	@Consumes(MediaType.APPLICATION_XML)
	@RequestMapping(value = "/uniqueKeys", method = RequestMethod.GET)
	public @ResponseBody
	PropertyList getUniqueScenarioPropertyKeyList() throws Exception {
		List<String> scenarioPropertyUniqueKeyList;
		List<ReportProperty> scenarioPropList = new ArrayList<ReportProperty>();
		try {
			scenarioPropertyUniqueKeyList = scenarioPropertyService
					.getUniquePropertyKeyList();
			for (String key : scenarioPropertyUniqueKeyList) {
				scenarioPropList.add(new ScenarioProperty(key, null));
			}
			return new PropertyList(scenarioPropList);
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	// /**
	// * This method will return the highest value of the version property; null
	// * if none exist or this is not even 1 value represented as double
	// * http://host
	// * :port/report-service/report/scenarioProperties/lastVersionExecution
	// * Method: GET
	// *
	// * @return Double the max value of version property; see the <a
	// * href="{@docRoot} /doc-files/double.xml">Produced XML file</a>.
	// */
	// @Path("/lastVersionExecution")
	// @POST
	// @Produces(MediaType.APPLICATION_XML)
	// @Consumes(MediaType.APPLICATION_XML)
	// public JAXBElement<Double> getLastVersionExecution() {
	// Double max;
	// max = scenarioPropertyService.getLatestVersionProperty();
	// if (max != null) {
	// return new JAXBElement<Double>(new QName("Double"), Double.class,
	// max);
	// } else {
	// return new JAXBElement<Double>(new QName("Double"), Double.class,
	// 0.0);
	// }
	// }

}
