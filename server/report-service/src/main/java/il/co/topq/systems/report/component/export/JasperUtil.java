package il.co.topq.systems.report.component.export;

import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.model.Scenario;
import il.co.topq.systems.report.common.model.ScenarioCustomReport;
import il.co.topq.systems.report.common.model.Test;
import il.co.topq.systems.report.common.model.TestCustomReport;
import il.co.topq.systems.report.common.obj.ScenarioQuery;
import il.co.topq.systems.report.common.obj.SortingColumn;
import il.co.topq.systems.report.common.obj.TestQuery;
import il.co.topq.systems.report.service.infra.CustomReportService;
import il.co.topq.systems.report.service.infra.ScenarioService;
import il.co.topq.systems.report.service.infra.TestService;

import java.awt.Color;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.ImageScaleMode;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;

@Service
public class JasperUtil {

	@Autowired
	private ScenarioService scenarioService;

	@Autowired
	private CustomReportService<ScenarioCustomReport> scenarioCustomReportService;

	@Autowired
	private CustomReportService<TestCustomReport> testCustomReportService;

	@Autowired
	private TestService testService;

	/**
	 * This method will get a duration represented in form of double<br>
	 * and will convert it to string
	 * 
	 * @param duration
	 * @return
	 */
	public static String formatDuration(double duration) {

		String result = "";
		int dayValue = 24 * 60 * 60 * 1000;
		int hrValue = 60 * 60 * 1000;
		int minValue = 60 * 1000;
		int secValue = 1000;

		duration *= 1000;

		int hrLeft = (int) duration % dayValue;
		dayValue = ((int) duration - hrLeft) / dayValue;
		int minLeft = hrLeft % hrValue;
		hrValue = (hrLeft - minLeft) / hrValue;
		int secLeft = minLeft % minValue;
		minValue = (minLeft - secLeft) / minValue;
		int milliSecLeft = secLeft % secValue;
		secValue = (secLeft - milliSecLeft) / secValue;

		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(0);

		if (dayValue != 0) {
			result += nf.format(dayValue) + "d:" + hrValue + "h:" + minValue + "m:" + secValue + "s:"
					+ nf.format(milliSecLeft) + "ms";
		} else if (hrValue != 0) {
			result += hrValue + "h:" + minValue + "m:" + secValue + "s:" + nf.format(milliSecLeft) + "ms";
		} else if (minValue != 0) {
			result += minValue + "m:" + secValue + "s:" + nf.format(milliSecLeft) + "ms";
		} else if (secValue != 0) {
			result += secValue + "s:" + nf.format(milliSecLeft) + "ms";
		} else if (milliSecLeft != 0) {
			result += nf.format(milliSecLeft) + "ms";
		}
		return result;
	}

	private DynamicReport dr;
	private JasperReport jr;
	private JasperPrint jp;
	private Map params = new HashMap();
	private Collection<ReportProperty> scenarioProperties;
	private Collection<ReportProperty> testProperties;
	private ScenarioQuery scenarioQuery = null;
	private TestQuery testQuery = null;

	private JasperPrint buildJasperFromTemplate(String reportType, int id, String templateFile, List<String> colList)
			throws Exception {

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setTemplateFile(templateFile, true, false, false, false); // import
																		// fields

		params.put("subreportsDataSourceProperties", createDataSourceForProperties(reportType));
		params.put("subreportsDataSourceData", createDataSource(reportType, id));

		DynamicReport properties = createProperties();

		boolean startOnNewPage = true;
		if (params.get("subreportsDataSourceProperties") != null)
			drb.addConcatenatedReport(properties, new ClassicLayoutManager(), "subreportsDataSourceProperties",
					DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_JRDATASOURCE, false);
		else if (!reportType.contains("custom"))
			startOnNewPage = false;

		drb.addConcatenatedReport(createData(colList), new ClassicLayoutManager(), "subreportsDataSourceData",
				DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_JRDATASOURCE, startOnNewPage);

		dr = drb.build();

		try {
			jr = DynamicJasperHelper.generateJasperReport(dr, new ClassicLayoutManager(), params);
		} catch (JRException e) {
			e.printStackTrace();
		}

		JRDataSource ds = createDataSource(reportType, id);

		/**
		 * Creates the JasperPrint object, we pass as a Parameter the JasperReport object, and the JRDataSource
		 */
		try {
			jp = JasperFillManager.fillReport(jr, params, ds);
		} catch (JRException e) {
			e.printStackTrace();
		}
		// This line will show the Jasper view at the end of the export - and
		// will cause exit(0) if the user close it manually.
		// don't enable this feature.
		// JasperViewer.viewReport(jp);

		return jp;
	}

	private String prettyPrint(String str) {
		for (int i = 1; i < str.length(); i++) {
			char curr = str.charAt(i);
			if (Character.isUpperCase(curr)) {
				if (!Character.isUpperCase(str.charAt(i - 1))) {
					str = str.substring(0, i) + " " + str.substring(i);
					i++;
				}
			}
		}
		str = str.replaceFirst("" + str.charAt(0), "" + Character.toUpperCase(str.charAt(0)));
		return str;
	}

	private DynamicReport createData(List<String> list) throws Exception {

		FastReportBuilder drb = new FastReportBuilder();
		drb.setTemplateFile(ExportService.getTemplateFile(ExportService.STYLE).getAbsolutePath());
		// drb.setTemplateFile("C:\\Users\\eran_g\\reportServerWorkspace\\report-service\\src\\main\\webapp\\WEB-INF\\resources\\table_template.jrxml");
		drb.setWhenNoData("no data", new Style("Row"));

		Style oddRowStyle = new Style();
		oddRowStyle.setBorder(Border.NO_BORDER);
		// oddRowStyle.setBackgroundColor(new Color(112, 169, 198, 50));
		oddRowStyle.setBackgroundColor(new Color(240, 240, 240));

		Integer margin = new Integer(20);
		drb.setDetailHeight(margin).setLeftMargin(margin).setRightMargin(margin).setTopMargin(margin)
				.setBottomMargin(margin).setPrintBackgroundOnOddRows(true)
				.setDefaultStyles(new Style("title"), new Style("subtitle"), new Style("TableName"), new Style("Row"))
				.setOddRowBackgroundStyle(oddRowStyle);

		for (String curr : list) {
			if (curr.equals("status")) {
				try {
					drb.addImageColumn(prettyPrint(curr), curr, 5, false, ImageScaleMode.NO_RESIZE);
				} catch (Exception e) {
				}
			} else {
				String colName = curr;
				if (curr.contains("config:"))
					colName = curr.replace("config:", "");
				colName = prettyPrint(colName);
				AbstractColumn column = ColumnBuilder.getNew().setColumnProperty(curr, String.class.getName())
						.setTitle(colName).setWidth(curr.length() < 20 ? curr.length() + 5 : 25)
						.setStyle(new Style("Row")).setHeaderStyle(new Style("TableName")).build();
				drb.addColumn(column);
			}
		}

		drb.setUseFullPageWidth(true);
		drb.setAllowDetailSplit(true);

		DynamicReport dr = drb.build();
		return dr;
	}

	private DynamicReport createProperties() throws Exception {

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setTemplateFile(ExportService.getTemplateFile(ExportService.STYLE).getAbsolutePath());
		// drb.setTemplateFile("C:\\Users\\eran_g\\reportServerWorkspace\\report-service\\src\\main\\webapp\\WEB-INF\\resources\\table_template.jrxml");

		for (String curr : new String[] { "Property", "Value" }) {
			AbstractColumn column = ColumnBuilder.getNew().setColumnProperty(curr, String.class.getName())
					.setTitle(curr).setStyle(new Style("row")).setHeaderStyle(new Style("TableName"))
					.setPrintRepeatedValues(false).build();
			drb.addColumn(column);
		}

		drb.setUseFullPageWidth(true);
		DynamicReport dr = drb.build();
		return dr;
	}

	/**
	 * Compile in run time the given jrxml file & insert scenarioId's data
	 * 
	 * @param scenarioId
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public JasperPrint compileJarxml(int scenarioId, String filePath) throws Exception {

		JasperDesign jasperDesign = JRXmlLoader.load(filePath);
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

		Scenario scenario = scenarioService.get(scenarioId);

		JasperPrint jasperPrint;
		try {

			Map<String, String> map = new HashMap<String, String>();

			TestQuery testQuery = new TestQuery();
			testQuery.setSortingColumn(new SortingColumn("startTime", true));
			List<Test> tests = testService.getTestsByScenarioId(scenario.getId(), testQuery);
			jasperPrint = JasperFillManager.fillReport(jasperReport, map, new ScenarioDetailedReportSource(scenario,
					tests));

		} catch (Exception ex) {
			String connectMsg = "Could not create the report " + ex.getMessage() + " " + ex.getLocalizedMessage();
			throw new Exception(connectMsg);
		}

		return jasperPrint;
	}

	public static void main(String[] args) throws Exception {

		List<String> list = new ArrayList<String>();
		list.add("config:simUserAnimK");
		list.add("config:successTransperSec");
		list.add("config:unsuccessTransperSec");

		JasperUtil util = new JasperUtil();
		// util.buildCustomReport("customTest", 6,
		// "C:\\ReportServer\\report-service\\src\\main\\webapp\\WEB-INF\\resources\\empty_custom_template.jrxml");
		// util.buildCustomReport("customScenario", 1,
		// "C:\\ReportServer\\report-service\\src\\main\\webapp\\WEB-INF\\resources\\empty_custom_template.jrxml");
		util.buildDetailsReport(
				"Details",
				1233,
				"C:\\Users\\eran_g\\reportServerWorkspace\\report-service\\src\\main\\webapp\\WEB-INF\\resources\\empty_scenario_details_template.jrxml",
				list);
		// try {
		// compileJarxml(3,
		// "C:\\ReportServer\\report-service\\src\\main\\webapp\\WEB-INF\\resources\\scenario_details_no_fc_template.jrxml");
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	public JasperPrint buildReport(String reportType, String filePath) throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		list.add("#");
		if (reportType.equals("Test"))
			list.add("testName");
		list.add("scenarioName");
		list.add("startTime");
		// list.add("duration");
		if (reportType.equals("Test")) {
			list.add("status");
			list.add("failCause");
		} else {
			list.add("pass");
			list.add("failed");
			list.add("warning");
			list.add("total");
		}

		return buildJasperFromTemplate(reportType, -1, filePath, list);
	}

	public JasperPrint buildDetailsReport(String reportType, int id, String filePath, List<String> configProperties)
			throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		list.add("#");
		list.add("testName");
		list.add("startTime");
		list.add("duration");
		list.add("status");
		list.add("failCause");

		for (int i = 0; i < configProperties.size(); i++)
			list.add(configProperties.get(i));

		return buildJasperFromTemplate(reportType, id, filePath, list);
	}

	public JasperPrint buildCustomReport(String reportType, int id, String filePath) throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		list.add("#");
		if (reportType.equals("customTest"))
			list.add("testName");
		list.add("scenarioName");
		list.add("startTime");
		// list.add("duration");
		if (reportType.equals("customTest")) {
			list.add("status");
			list.add("failCause");
		} else {
			list.add("pass");
			list.add("failed");
			list.add("warning");
			list.add("total");
		}
		return buildJasperFromTemplate(reportType, id, filePath, list);
	}

	private JRDataSource createDataSource(String type, int id) {

		if (type.equals("customScenario")) {
			if (scenarioQuery == null)
				scenarioQuery = new ScenarioQuery();

			List<Scenario> scenarios = scenarioService.getScenariosByFiltersWithSortingCol(scenarioQuery);

			ScenarioCustomReport scenarioCustomReport = scenarioCustomReportService.get(id);

			ScenarioCustomReportSource scenarioCustomReportSource = new ScenarioCustomReportSource(
					scenarioCustomReport, scenarios);
			return scenarioCustomReportSource;
		} else if (type.equals("customTest")) {
			if (testQuery == null)
				testQuery = new TestQuery();
			List<Test> tests = testService.getTestsByFiltersWithSortingCol(testQuery);

			TestCustomReport testCustomReport = testCustomReportService.get(id);

			TestCustomReportSource testCustomReportSource = new TestCustomReportSource(tests, testCustomReport);

			return testCustomReportSource;
		} else if (type.equals("Details")) {
			Scenario scenario = scenarioService.get(id);
			TestQuery testQuery = new TestQuery();
			testQuery.setSortingColumn(new SortingColumn("startTime", true));
			List<Test> tests = testService.getTestsByScenarioId(scenario.getId(), testQuery);
			return new ScenarioDetailedReportSource(scenario, tests);
		} else if (type.equals("Test")) {
			if (testQuery == null)
				testQuery = new TestQuery();
			List<Test> tests = testService.getTestsByFiltersWithSortingCol(testQuery);
			return new TestReportSource(tests);
		} else if (type.equals("Scenario")) {
			if (scenarioQuery == null)
				scenarioQuery = new ScenarioQuery();
			List<Scenario> scenarios = scenarioService.getScenariosByFiltersWithSortingCol(scenarioQuery);
			return new ScenarioReportSource(scenarios);
		}
		return null;
	}

	private JRDataSource createDataSourceForProperties(String reportType) {

		if (reportType.equals("customScenario") || reportType.equals("Details")) {
			if (scenarioProperties == null)
				return null;
			Collection<ReportProperty> properties = scenarioProperties;
			return new ScenarioPropertiesSource(properties);
		} else if (reportType.equals("customTest")) {
			if (testProperties == null)
				return null;
			Collection<ReportProperty> properties = testProperties;
			return new TestPropertiesSource(properties);
		}
		return null;
	}

	public void setScenarioQuery(ScenarioQuery scenarioQuery) {
		this.scenarioQuery = scenarioQuery;
		this.scenarioProperties = scenarioQuery.getProperties();
	}

	public void setTestQuery(TestQuery testQuery) {
		this.testQuery = testQuery;
		this.testProperties = testQuery.getProperties();
	}

	public void setScenarioProperties(int scenarioId) {
		Scenario scenario = scenarioService.get(scenarioId);
		Collection<ReportProperty> propSet = scenario.getProperties();
		scenarioProperties = new ArrayList<ReportProperty>();
		for (ReportProperty curr : propSet) {
			scenarioProperties.add(curr);
		}
	}
}
