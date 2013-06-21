package il.co.topq.systems.report.component.service;

import java.util.List;

import il.co.topq.systems.report.common.infra.log.ReportLogger;
import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.model.ScenarioCustomReport;
import il.co.topq.systems.report.common.obj.ScenarioQuery;
import il.co.topq.systems.report.component.jaxbWrappers.PropertyValuesWrapperList;
import il.co.topq.systems.report.component.jaxbWrappers.ScenarioCustomReportList;
import il.co.topq.systems.report.service.infra.CustomReportService;
import il.co.topq.systems.report.service.infra.PropertyService;

import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/scenarioCustomReport")
public class ScenarioCustomReportWebService {

	private Logger log = ReportLogger.getInstance().getLogger(this.getClass());

	@Autowired
	private CustomReportService<ScenarioCustomReport> scenarioCustomReportService;

	@Autowired
	private PropertyService<ReportProperty> scenarioPropertyService;

	/**
	 * This method creates a ScenarioCustomReport in Database. URL:
	 * http://host:port/report-service/report/scenarioCustomReport Method: POST
	 * 
	 * @param scenarioCustomReport
	 *            -
	 * @return Integer - The created ScenarioCustomReport ID; see the <a href="{@docRoot}
	 *         /doc-files/integer.xml">Produced XML file</a>.
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void createScenarioCustomReport(ServletResponse response,
			@RequestBody ScenarioCustomReport scenarioCustomReport) throws Exception {
		scenarioCustomReport.createDate();
		ScenarioCustomReport customReport = scenarioCustomReportService.create(scenarioCustomReport);
		if (customReport != null) {
			response.getWriter().print(customReport.getId());
		} else {
			throw new Exception("custom report could not be created");
		}
	}

	/**
	 * This method gets a ScenarioCustomReport id and returns the matching ScenarioCustomReport from Database. URL:
	 * http://host:port//report-service/ report/scenarioCustomReport/scenarioCustomReportId Method: GET
	 * 
	 * @param scenarioCustomReportId
	 *            -
	 * @return ScenarioCustomReport see the <a href="{@docRoot} /doc-files/scenarioCustomReport.xml">Produced XML
	 *         file</a>.
	 */
	@RequestMapping(value = "/{scenarioCustomReportId}", method = RequestMethod.GET)
	public @ResponseBody
	ScenarioCustomReport getScenarioCustomReport(@PathVariable("scenarioCustomReportId") Integer scenarioCustomReportId)
			throws Exception {
		try {
			return scenarioCustomReportService.get(scenarioCustomReportId);
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	/**
	 * This method gets a ScenarioCustomReport id and returns all of its property key values; URL:
	 * http://host:port/report-service/report/scenarioCustomReport /values/scenarioCustomReportId Method: GET
	 * 
	 * @param scenarioCustomReportId
	 *            -
	 * @return a List of PropertyValuesWrapper see the <a href="{@docRoot}
	 *         /doc-files/listOfPropertyValuesWrapper.xml">Produced XML file</a>.
	 */
	@RequestMapping(value = "/values/{scenarioCustomReportId}", method = RequestMethod.GET)
	public @ResponseBody
	PropertyValuesWrapperList getScenarioCustomReportPropertiesValues(
			@PathVariable("scenarioCustomReportId") Integer scenarioCustomReportId) throws Exception {

		ScenarioCustomReport scenarioCustomReport = scenarioCustomReportService.get(scenarioCustomReportId);
		if (scenarioCustomReport != null) {
			return new PropertyValuesWrapperList(scenarioPropertyService.getAllPropertiesValues(scenarioCustomReport
					.getProperties()));
		} else {
			throw new Exception("failed to get property keys values");
		}
	}

	/**
	 * This method gets a Chunk of ScenarioCustomReport from Database by descending creation time. and timeRange URL:
	 * http://host:port/report-service/report/scenarioCustomReport/chunk Method: POST
	 * <p/>
	 * rep chunk, timeRange, Prop = null
	 * 
	 * @param scenarioQuery
	 *            -
	 * @return a List of ScenarioCustomReport. see the <a href="{@docRoot}
	 *         /doc-files/listOfScenarioCustomReport.xml">Produced XML file</a>.
	 */
	@RequestMapping(value = "/chunk", method = RequestMethod.POST)
	public @ResponseBody
	ScenarioCustomReportList getScenarioCustomReportByChunk(@RequestBody ScenarioQuery scenarioQuery) throws Exception {
		try {
			List<ScenarioCustomReport> customReports = scenarioCustomReportService.getCustomReport(
					scenarioQuery.getChunk(), scenarioQuery.getTimeRange());
			return new ScenarioCustomReportList(customReports);
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	/**
	 * This method will get a ScenarioCustomReport ID and will delete the matching ScenarioCustomReport from Database;
	 * URL: http://host:port//report -service/report/scenarioCustomReport/delete/scenarioCustomReportId Method: GET
	 * 
	 * @param scenarioCustomReportId
	 *            -
	 * @return true false
	 */
	@RequestMapping(value = "/delete/{scenarioCustomReportId}", method = RequestMethod.GET)
	public void deleteCustomReport(ServletResponse response,
			@PathVariable("scenarioCustomReportId") Integer scenarioCustomReportId) throws Exception {
		try {
			scenarioCustomReportService.delete(scenarioCustomReportId);
			response.getWriter().print(true);
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	/**
	 * This method will get a ScenarioCustomReport and will Update it in Database. URL:
	 * http://host:port//report-service/report/scenarioCustomReport/update Method: POST
	 * 
	 * @param scenarioCustomReport
	 *            -
	 * @return Boolean represents the success of the operation. see the <a href="{@docRoot}
	 *         /doc-files/boolean.xml">Produced XML file</a>.
	 * @throws Exception
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void updateCustomReport(ServletResponse response, @RequestBody ScenarioCustomReport scenarioCustomReport)
			throws Exception {
		scenarioCustomReportService.update(scenarioCustomReport);
		response.getWriter().print(true);
	}

	/**
	 * This method gets the size of the request of ScenarioCustomReport from Database. filters date received in
	 * ScenarioQuery. timeRange. URL:http://host:port/report-service/report/scenarioCustomReport/querySize Method:POST
	 * 
	 * @param scenarioQuery
	 *            -
	 * @return Integer - size of result set according to ScenarioQuery received as parameter. see the <a
	 *         href="{@docRoot} /doc-files/integer.xml">Produced XML file</a>.
	 */
	@RequestMapping(value = "/querySize", method = RequestMethod.POST)
	public void getSizeOfScenarioCustomReportByChunk(ServletResponse response, @RequestBody ScenarioQuery scenarioQuery)
			throws Exception {
		try {
			Integer querySize = scenarioCustomReportService.getSizeOfCustomReportByFilter(scenarioQuery.getTimeRange());
			response.getWriter().print(querySize);
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}
}
