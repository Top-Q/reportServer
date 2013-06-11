package il.co.topq.systems.report.service.infra;

import il.co.topq.systems.report.common.model.Scenario;
import il.co.topq.systems.report.common.obj.ScenarioComparator;
import il.co.topq.systems.report.common.obj.ScenarioQuery;

import java.util.List;

/**
 * 
 * @author Eran Golan & Tomer Gafner
 */
public interface ScenarioService extends BaseService<Scenario> {

	/**
	 * Get test list of the scenario with the given ID
	 * 
	 * @param id
	 *            Scenario's ID
	 * @return the number of tests for a specific scenario.
	 */
	int getSizeOfTestsSetByScenarioID(final Integer id);

	/**
	 * This method will get a list of scenario ids and will compare them.<br>
	 * it assumes that scenarios are equal by:<br>
	 * 1. tests ran<br>
	 * 2. tests order<br>
	 * <p/>
	 * return ScenarioComparator object. this object holds a list of all compared scenarios, and a list of
	 * TestComparator object which has test name and an array of tests. arr[i] represents the result of the test(test
	 * name) run of scenario[i] in compared scenario lists.
	 * 
	 * @param scenariosIDArray
	 *            -
	 * @return -
	 */
	ScenarioComparator compareScenarios(Integer[] scenariosIDArray);

	List<Scenario> getScenariosByFiltersWithSortingCol(ScenarioQuery scenarioQuery);

	/**
	 * This method will get the size of a query according to the Query object received as parameter.
	 * 
	 * @param scenarioQuery
	 *            -
	 * @return -
	 */
	int getSizeOfScenarioQuery(ScenarioQuery scenarioQuery);

	

}
