package il.co.topq.systems.report.component.service;

import il.co.topq.systems.report.common.infra.log.ReportLogger;
import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.model.TestCustomReport;
import il.co.topq.systems.report.common.model.TestProperty;
import il.co.topq.systems.report.component.jaxbWrappers.PropertyList;
import il.co.topq.systems.report.service.infra.CustomReportService;
import il.co.topq.systems.report.service.infra.PropertyService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/testProperties")
public class TestPropertiesWebService {

	private Logger log = ReportLogger.getInstance().getLogger(this.getClass());
	@Autowired
	private CustomReportService<TestCustomReport> testCustomReportService;

	@Autowired
	private PropertyService<ReportProperty> testPropertyService;

	/**
	 * This method will get all Test unique keys. using a jaxBaseList object as
	 * a wrapper; from the user side need to call the getList method to get the
	 * list of <T> from the object;
	 * 
	 * @return Response wrapping jaxBaseList;
	 */
	@RequestMapping(value = "/uniqueKeys", method = RequestMethod.GET)
	public @ResponseBody
	PropertyList getUniqueTestPropertyKeyList() throws Exception {
		List<ReportProperty> testPropList = new ArrayList<ReportProperty>();
		try {
			List<String> testPropertyUniqueKeyList = testPropertyService
					.getUniquePropertyKeyList();
			for (String key : testPropertyUniqueKeyList) {
				testPropList.add(new TestProperty(key, null));
			}
			return new PropertyList(testPropList);
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	/**
	 * This method will get all Test unique keys. using a jaxBaseList object as
	 * a wrapper; from the user side need to call the getList method to get the
	 * list of <T> from the object;
	 * 
	 * @param testCustomReportId
	 *            -
	 * @return Response wrapping jaxBaseList;
	 */
	@RequestMapping(value = "/uniqueKeysInEdit/{testCustomReportId}", method = RequestMethod.GET)
	public @ResponseBody
	PropertyList getUniqueTestPropertyKeyListInEdit(
			@PathVariable("testCustomReportId") Integer testCustomReportId)
			throws Exception {
		List<ReportProperty> testPropList = new ArrayList<ReportProperty>();
		try {
			List<String> testPropertyUniqueKeyList = testPropertyService
					.getUniquePropertyKeyList();
			TestCustomReport testCustomReport = testCustomReportService
					.get(testCustomReportId);
			Collection<ReportProperty> testCustomReportPropertiesList = new ArrayList<ReportProperty>(
					testCustomReport.getProperties());

			for (ReportProperty aTestCustomReportPropertiesList : testCustomReportPropertiesList) {
				testPropertyUniqueKeyList
						.remove(aTestCustomReportPropertiesList
								.getPropertyKey());
			}
			for (String key : testPropertyUniqueKeyList) {
				testPropList.add(new TestProperty(key, null));
			}

			return new PropertyList(testPropList);
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	/**
	 * This method will get all Test unique keys. using a jaxBaseList object as
	 * a wrapper; from the user side need to call the getList method to get the
	 * list of <T> from the object;
	 * 
	 * @return Response wrapping jaxBaseList;
	 */
	@RequestMapping(value = "/uniqueKeysString", method = RequestMethod.GET)
	public void getUniqueTestPropertyKeyStringList(ServletResponse response)
			throws Exception {
		String testPropertyKeys = "";
		try {
			List<String> testPropertyUniqueKeyList = testPropertyService
					.getUniquePropertyKeyList();

			for (String key : testPropertyUniqueKeyList) {
				testPropertyKeys += key + ";";
			}
			response.getWriter().print(testPropertyKeys);
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}
}
