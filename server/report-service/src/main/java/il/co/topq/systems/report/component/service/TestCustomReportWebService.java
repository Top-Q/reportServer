package il.co.topq.systems.report.component.service;

import il.co.topq.systems.report.common.infra.log.ReportLogger;
import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.model.TestCustomReport;
import il.co.topq.systems.report.common.obj.TestQuery;
import il.co.topq.systems.report.component.jaxbWrappers.PropertyValuesWrapperList;
import il.co.topq.systems.report.component.jaxbWrappers.TestCustomReportList;
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
@RequestMapping(value = "/testCustomReport")
public class TestCustomReportWebService {

	private Logger log = ReportLogger.getInstance().getLogger(this.getClass());

	@Autowired
	private CustomReportService<TestCustomReport> testCustomReportService;

	@Autowired
	private PropertyService<ReportProperty> testPropertyService;

	/**
	 * This method creates a TestCustomReport in Database. URL: http://host:port/report-service/report/testCustomReport.
	 * Method: POST
	 * 
	 * @param testCustomReport
	 *            -
	 * @return Integer - The created TestCustomReport ID; see the <a href="{@docRoot} /doc-files/integer.xml">Produced
	 *         XML file</a>.
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void createTestCustomReport(ServletResponse response, @RequestBody TestCustomReport testCustomReport)
			throws Exception {
		testCustomReport.createDate();
		TestCustomReport customReport = testCustomReportService.create(testCustomReport);
		if (customReport != null) {
			response.getWriter().print(customReport.getId());
		} else {
			throw new Exception("custom report could be created");
		}
	}

	/**
	 * This method gets a TestCustomReport object from DB by the given ID URL: http
	 * ://host:port/report-service/report/testCustomReport/{testCustomReportId} Method: GET
	 * 
	 * @param testCustomReportId
	 *            -
	 * @return TestCustomReport with the given ID. see the <a href="{@docRoot} /doc-files/testCustomReport.xml">Produced
	 *         XML file</a>.
	 */
	@RequestMapping(value = "/{testCustomReportId}", method = RequestMethod.GET)
	public @ResponseBody
	TestCustomReport getTestCustomReport(@PathVariable("testCustomReportId") Integer testCustomReportId)
			throws Exception {

		log.info("TestCustomReport ID received: " + testCustomReportId);
		TestCustomReport testCustomReport;
		try {
			testCustomReport = testCustomReportService.get(testCustomReportId);
			return testCustomReport;
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	/**
	 * This method gets a testCustomReport id and returns all of its property key values; URL:
	 * http://host:port/report-service/report/testCustomReport/values /testCustomReportId Method: GET
	 * 
	 * @param testCustomReportId
	 *            -
	 * @return List<PropertyValuesWrapper> see the <a href="{@docRoot}
	 *         /doc-files/listOfPropertyValuesWrapper.xml">Produced XML file</a>.
	 */
	@RequestMapping(value = "/values/{testCustomReportId}", method = RequestMethod.GET)
	public @ResponseBody
	PropertyValuesWrapperList getTestCustomReportPropertiesValues(
			@PathVariable("testCustomReportId") Integer testCustomReportId) throws Exception {

		log.info("In getTestCustomReportPropertiesValues webservice");
		log.info("ID: " + testCustomReportId);
		log.info("TestCustomReport ID received: " + testCustomReportId);
		TestCustomReport customReport = testCustomReportService.get(testCustomReportId);
		if (customReport != null) {
			return new PropertyValuesWrapperList(testPropertyService.getAllPropertiesValues(customReport
					.getProperties()));
		}
		throw new Exception("failed to get property keys values");
	}

	/**
	 * This method will get a TestCustomReport ID and will delete the matching TestCustomReport from Database; URL:
	 * http://host:port//report-service/report /testCustomReport/delete/customReportId Method: GET
	 * 
	 * @param customReportId
	 *            -
	 * @return Boolean represents the success of the operation. see the <a href="{@docRoot}
	 *         /doc-files/boolean.xml">Produced XML file</a>.
	 */
	@RequestMapping(value = "/delete/{customReportId}", method = RequestMethod.GET)
	public void deleteTestCustomReport(ServletResponse response, @PathVariable("customReportId") Integer customReportId)
			throws Exception {
		try {
			testCustomReportService.delete(customReportId);
			response.getWriter().print(true);
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	/**
	 * This method will get a TestCustomReport and will Update it in Database. URL:
	 * http://host:port//report-service/report/testCustomReport/update Method: POST
	 * 
	 * @param testCustomReport
	 *            -
	 * @return Boolean represents the success of the operation. see the <a href="{@docRoot}
	 *         /doc-files/boolean.xml">Produced XML file</a>.
	 * @throws Exception
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void updateCustomReport(ServletResponse response, @RequestBody TestCustomReport testCustomReport)
			throws Exception {
		testCustomReportService.update(testCustomReport);
		response.getWriter().print(true);
	}

	/**
	 * This method gets a Chunk of TestCustomReport from Database by descending creation time. and between timeRange in
	 * case timeRange!= null within testQuery object received. URL:
	 * http://host:port/report-service/report/testCustomReport/chunk Method: POST
	 * 
	 * @param testQuery
	 *            -
	 * @return a List of TestCustomReport. see the <a href="{@docRoot} /doc-files/listOfTestCustomReport.xml">Produced
	 *         XML file</a>.
	 */
	@RequestMapping(value = "/chunk", method = RequestMethod.POST)
	public @ResponseBody
	TestCustomReportList getTestCustomReportByChunk(@RequestBody TestQuery testQuery) throws Exception {
		try {
			return new TestCustomReportList(testCustomReportService.getCustomReport(testQuery.getChunk(),
					testQuery.getTimeRange()));
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	/**
	 * This method gets the size of the request of TestCustomReport from Database. filters date received in
	 * TestQuery.timeRange. URL:http://host:port/report-service/report/testCustomReport/querySize Method:POST
	 * 
	 * @param testQuery
	 *            -
	 * @return Integer - size of result set according to TestQuery received as parameter. see the <a href="{@docRoot}
	 *         /doc-files/integer.xml">Produced XML file</a>.
	 */
	@RequestMapping(value = "/querySize", method = RequestMethod.POST)
	public void getSizeOfTestCustomReportByChunk(ServletResponse response, @RequestBody TestQuery testQuery)
			throws Exception {
		Integer querySize;
		try {
			querySize = testCustomReportService.getSizeOfCustomReportByFilter(testQuery.getTimeRange());
			response.getWriter().print(querySize);
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}
}
