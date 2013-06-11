package il.co.topq.systems.report.component.service;

import il.co.topq.systems.report.common.infra.log.ReportLogger;
import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.model.Scenario;
import il.co.topq.systems.report.common.model.Test;
import il.co.topq.systems.report.common.obj.TestQuery;
import il.co.topq.systems.report.common.obj.TestSummaryQuery;
import il.co.topq.systems.report.component.jaxbWrappers.TestList;
import il.co.topq.systems.report.component.jaxbWrappers.TestSummaryList;
import il.co.topq.systems.report.service.infra.PropertyService;
import il.co.topq.systems.report.service.infra.TestService;

import java.util.List;

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
@RequestMapping(value = "/test")
public class TestWebService {

	private Logger log = ReportLogger.getInstance().getLogger(this.getClass());

	@Autowired
	private TestService testService;

	@Autowired
	private PropertyService<ReportProperty> testPropertyService;

	/**
	 * This method counts all the Tests in the DB. URL:http://host:port/report-service/report/test/count Method: GET
	 * 
	 * @return Long - number of Tests in DB see the <a href="{@docRoot} /doc-files/long.xml">Produced XML file</a>.
	 * @throws Exception
	 */
	@RequestMapping(value = "/count", method = RequestMethod.POST)
	public void countTests(ServletResponse response) throws Exception {

		try {
			Long numOfTests = testService.countAll(null);
			response.getWriter().print(numOfTests);
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	/**
	 * Get test object from DB by the given ID
	 * 
	 * @param testId
	 *            a tests ID
	 * @return The test with the given ID.
	 * @throws Exception
	 *             -
	 */
	@RequestMapping(value = "/{testId}", method = RequestMethod.GET)
	public @ResponseBody
	Test getTestById(ServletResponse response, @PathVariable("testId") Integer testId) throws Exception {
		log.info("in get test by id web service");
		try {
			Test test = testService.get(testId);
			return test;
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	/**
	 * This method gets a TestQuery and return List of Tests from Database according to TestQuery.
	 * URL:http://host:port/report-service/report/test Method: POST
	 * 
	 * @param testQuery
	 *            -
	 * @return List of Test answering the TestQuery. see the <a href="{@docRoot} /doc-files/listOfTest.xml">Consumed XML
	 *         file</a>.
	 * @throws Exception
	 *             -
	 */
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	TestList getTestsByFilter(@RequestBody TestQuery testQuery) throws Exception {

		log.info("in get test by filter web service");
		List<Test> testsList = testService.getTestsByFiltersWithSortingCol(testQuery);
		return new TestList(testsList);
	}

	/**
	 * This method gets a TestQuery and return the size of its resultSet; URL:
	 * http://host:port/report-service/report/test/querySize Method: POST
	 * 
	 * @param testQuery
	 *            -
	 * @return Integer - size of result set according to TestQuery received as parameter. see the <a href="{@docRoot}
	 *         /doc-files/integer.xml">Produced XML file</a>.
	 * @throws Exception
	 *             -
	 */
	@RequestMapping(value = "/querySize", method = RequestMethod.POST)
	public void getSizeOfTestQueryByFilter(ServletResponse response, @RequestBody TestQuery testQuery) throws Exception {

		log.info("in get size of test query by filter web service");
		Integer querySize = testService.getSizeOfTestQuery(testQuery);
		response.getWriter().print(querySize);
	}

	@RequestMapping(value = "/testScenario/{testId}", method = RequestMethod.GET)
	public void getTestScenarioID(ServletResponse response, @PathVariable("testId") Integer testId) throws Exception {
		log.info("in get test scenario ID web service");
		Test test = testService.get(testId);
		if (test == null) {
			throw new Exception("test returned null from attaching object method");
		}
		Scenario scenario = test.getScenario();
		response.getWriter().print(scenario.getId());
	}

	@RequestMapping(value = "/scenarioOfTest/{testId}", method = RequestMethod.GET)
	public @ResponseBody
	Scenario getScenarioOfTest(@PathVariable("testId") Integer testId) throws Exception {
		log.info("in get Scenario Of Test web service");
		Test test = testService.get(testId);
		if (test == null) {
			throw new Exception("test returned null from attaching object method");
		}
		return test.getScenario();
	}

	@RequestMapping(value = "/testLogFolder/{testId}", method = RequestMethod.GET)
	public void getTestLogFolder(ServletResponse response, @PathVariable("testId") Integer testId) throws Exception {
		log.info("in get test log folder");
		Test test = testService.get(testId);
		if (test == null) {
			throw new Exception("test returned null from attaching object method");
		}
		Scenario scenario = test.getScenario();
		response.getWriter().print(scenario.getHtmlDir());
	}

	/**
	 * This method will get a Scenario id and deletes the matching Scenario from Database.
	 * URL:http://host:port/report-service/report/scenario/delete/scenarioId Method: GET
	 * 
	 * @param testId
	 *            -
	 * @return Boolean representing the success of the operation. see the <a href="{@docRoot}
	 *         /doc-files/boolean.xml">Consumed XML file</a>.
	 */
	@RequestMapping(value = "/delete/{testId}", method = RequestMethod.GET)
	public void deleteTest(ServletResponse response, @PathVariable("testId") String testIds) throws Exception {

		log.info("in delete test web service");
		boolean deleted;
		String[] testsIDs = testIds.split(",");
		for (String testID : testsIDs) {
			testService.delete(Integer.parseInt(testID));
			log.info("Deleted Succefully, testID: " + testID);
		}
		deleted = true;
		response.getWriter().print(deleted);
	}

	/**
	 * This method will delete all test orphan properties
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteOrphan", method = RequestMethod.GET)
	public void deleteOrphanTestProperties(ServletResponse response) throws Exception {

		log.info("In Delete Orphan Test Properties Web Service");
		testPropertyService.deleteOrphanProperties();
		response.getWriter().print(true);
	}

	/**
	 * Update a test
	 * 
	 * @param test
	 *            -
	 * @return Boolean representing the success of the operation. see the <a href="{@docRoot}
	 *         /doc-files/boolean.xml">Consumed XML file</a>.
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void updateTest(ServletResponse response, @RequestBody Test test) throws Exception {
		log.info("in update test web service");
		testService.update(test);
		response.getWriter().print(true);
	}

	/**
	 * This method will get a test query and retrieve a test summary that match<br>
	 * 
	 * @param testQuery
	 * @return List<TestSummary>
	 * @throws Exception
	 */
	@RequestMapping(value = "/group", method = RequestMethod.POST)
	public @ResponseBody
	TestSummaryList getTestSummary(@RequestBody TestQuery testQuery) throws Exception {
		log.info("in get test summary web service");
		return new TestSummaryList(testService.getTestSummary(testQuery));
	}

	/**
	 * This method will get a test query and return the size of the result TestSummart List.
	 * 
	 * @param testQuery
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/group/size", method = RequestMethod.POST)
	public void getTestSummarySize(ServletResponse response, @RequestBody TestQuery testQuery) throws Exception {
		log.info("in get test summary size web service");
		testQuery.setChunk(null);
		Integer size = testService.getTestSummary(testQuery).size();
		response.getWriter().print(size);
	}

	/**
	 * This method will get a List of tests by a test Summary query
	 * 
	 * @param testSummaryQuery
	 * @return List<Test>
	 * @throws Exception
	 */
	@RequestMapping(value = "/group/tests", method = RequestMethod.POST)
	public @ResponseBody
	TestList getTestsByTestSummary(@RequestBody TestSummaryQuery testSummaryQuery) throws Exception {
		log.info("at getTestsByTestSummary web service");
		return new TestList(testService.getTestsByTestSummary(testSummaryQuery));
	}

	@RequestMapping(value = "/group/tests/size", method = RequestMethod.POST)
	public void getTestsByTestSummarySize(ServletResponse response, @RequestBody TestSummaryQuery testSummaryQuery)
			throws Exception {
		testSummaryQuery.getTestQuery().setChunk(null);
		Integer size = testService.getTestsByTestSummary(testSummaryQuery).size();
		response.getWriter().print(size);
	}
}