package il.co.topq.systems.report.dao.infra;

import il.co.topq.systems.report.common.model.Scenario;
import il.co.topq.systems.report.common.model.Test;
import il.co.topq.systems.report.common.obj.ScenarioComparator;
import il.co.topq.systems.report.common.obj.ScenarioQuery;
import il.co.topq.systems.report.common.obj.TestQuery;

import java.util.List;

/**
 * @authors Eran Golan & Tomer Gafner
 */
public interface ScenarioDao extends GenericDao<Scenario> {

	/**
	 * Get test list of the scenario with the given ID
	 * 
	 * @param id
	 *            Scenario's ID
	 * @return the number of tests for a specific scenario.
	 */
	int getScenarioNumberOfTests(final Integer scenarioId);

	List<Scenario> getScenariosByFiltersWithSortingCol(
			ScenarioQuery scenarioQuery);

	/**
	 * This method will get the size of a query according to the Query object
	 * received as parameter.
	 * 
	 * @param scenarioQuery
	 *            -
	 * @return -
	 */
	int getSizeOfScenarioQuery(ScenarioQuery scenarioQuery);

	ScenarioComparator compareScenarios(Integer[] scenariosIDArray);

	List<Test> getTestsByScenarioID(Integer scenarioID, TestQuery testQuery);

	void updateTestScenarioStatus(Scenario scenario, short oldStatus,
			short newStatus);

}
