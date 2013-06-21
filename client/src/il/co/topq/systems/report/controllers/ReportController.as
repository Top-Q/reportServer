package il.co.topq.systems.report.controllers
{
	import com.adobe.cairngorm.control.FrontController;
	
	import il.co.topq.systems.report.commands.GetLegacyReportToolCommand;
	import il.co.topq.systems.report.commands.GetSummaryPageCommand;
	import il.co.topq.systems.report.commands.LoginCommand;
	import il.co.topq.systems.report.commands.LogoutCommand;
	import il.co.topq.systems.report.commands.config.CreateGridConfigurationCommand;
	import il.co.topq.systems.report.commands.config.GetGridConfigurationCommand;
	import il.co.topq.systems.report.commands.config.GetUIConfigurationsCommand;
	import il.co.topq.systems.report.commands.config.IsConfigurationAvailableCommand;
	import il.co.topq.systems.report.commands.config.SetConfigCommand;
	import il.co.topq.systems.report.commands.config.SetSystemSettingsCommand;
	import il.co.topq.systems.report.commands.config.SetUIConfigurationsCommand;
	import il.co.topq.systems.report.commands.general.DeleteScenarioCommand;
	import il.co.topq.systems.report.commands.general.DeleteScenarioCustomReportCommand;
	import il.co.topq.systems.report.commands.general.DeleteTestCommand;
	import il.co.topq.systems.report.commands.general.DeleteTestCustomReportCommand;
	import il.co.topq.systems.report.commands.general.GetPropertiesKeysCommand;
	import il.co.topq.systems.report.commands.general.GetScenarioQuerySizeCommand;
	import il.co.topq.systems.report.commands.general.GetTestQuerySizeCommand;
	import il.co.topq.systems.report.commands.scenario.CompareScenarioCommand;
	import il.co.topq.systems.report.commands.scenario.DeleteOrphanPropertiesCommand;
	import il.co.topq.systems.report.commands.scenario.DeleteScenarioByScenarioQueryCommand;
	import il.co.topq.systems.report.commands.scenario.ExportScenarioCommand;
	import il.co.topq.systems.report.commands.scenario.ExportScenarioDetailsCommand;
	import il.co.topq.systems.report.commands.scenario.GetScenarioByIDCommand;
	import il.co.topq.systems.report.commands.scenario.GetScenarioCommand;
	import il.co.topq.systems.report.commands.scenario.GetScenarioPropertyValuesCommand;
	import il.co.topq.systems.report.commands.scenario.GetScenarioTestsCommand;
	import il.co.topq.systems.report.commands.scenario.GetScenarioTestsSizeCommand;
	import il.co.topq.systems.report.commands.scenario.GetScenariosByFilterCommand;
	import il.co.topq.systems.report.commands.scenario.GetScenariosByFilterSizeCommand;
	import il.co.topq.systems.report.commands.scenario.UpdateScenarioCommand;
	import il.co.topq.systems.report.commands.scenarioCustomReport.CreateScenarioCustomReportEventCommand;
	import il.co.topq.systems.report.commands.scenarioCustomReport.ExportScenarioCustomReportCommand;
	import il.co.topq.systems.report.commands.scenarioCustomReport.GetScenarioCustomReportByIDCommand;
	import il.co.topq.systems.report.commands.scenarioCustomReport.GetScenarioCustomReportCommand;
	import il.co.topq.systems.report.commands.scenarioCustomReport.GetScenarioCustomReportPropertiesValuesCommand;
	import il.co.topq.systems.report.commands.states.ChangeReportsStateCommand;
	import il.co.topq.systems.report.commands.states.ChangeSettingsStateCommand;
	import il.co.topq.systems.report.commands.states.ChangeTopLevelCommand;
	import il.co.topq.systems.report.commands.test.ExportTestCommand;
	import il.co.topq.systems.report.commands.test.GetGroupTestSizeCommand;
	import il.co.topq.systems.report.commands.test.GetGroupTestsCommand;
	import il.co.topq.systems.report.commands.test.GetScenarioByTestIdCommand;
	import il.co.topq.systems.report.commands.test.GetTestByIDCommand;
	import il.co.topq.systems.report.commands.test.GetTestCommand;
	import il.co.topq.systems.report.commands.test.GetTestGroupCommand;
	import il.co.topq.systems.report.commands.test.GetTestLogFolderCommand;
	import il.co.topq.systems.report.commands.test.GetTestScenarioCommand;
	import il.co.topq.systems.report.commands.test.GetTestsByFilterCommand;
	import il.co.topq.systems.report.commands.test.GetTestsByFilterSizeCommand;
	import il.co.topq.systems.report.commands.test.UpdateTestCommand;
	import il.co.topq.systems.report.commands.testCustomReport.CreateTestCustomReportEventCommand;
	import il.co.topq.systems.report.commands.testCustomReport.ExportTestCustomReportCommand;
	import il.co.topq.systems.report.commands.testCustomReport.GetTestCustomReportByChunkCommand;
	import il.co.topq.systems.report.commands.testCustomReport.GetTestCustomReportByIDCommand;
	import il.co.topq.systems.report.commands.testCustomReport.GetTestCustomReportPropertiesValuesCommand;
	import il.co.topq.systems.report.commands.user.CreateUserCommand;
	import il.co.topq.systems.report.commands.user.DeleteUserCommand;
	import il.co.topq.systems.report.commands.user.GetUsersCommand;
	import il.co.topq.systems.report.commands.user.UpdateUserCommand;
	import il.co.topq.systems.report.events.GetLegacyReportToolEvent;
	import il.co.topq.systems.report.events.GetSummaryPageEvent;
	import il.co.topq.systems.report.events.LoginEvent;
	import il.co.topq.systems.report.events.LogoutEvent;
	import il.co.topq.systems.report.events.config.CreateGridConfigurationEvent;
	import il.co.topq.systems.report.events.config.GetGridConfigurationEvent;
	import il.co.topq.systems.report.events.config.GetUIConfigurationsEvent;
	import il.co.topq.systems.report.events.config.IsConfigAvailableEvent;
	import il.co.topq.systems.report.events.config.SetConfigEvent;
	import il.co.topq.systems.report.events.config.SetSystemSettingEvent;
	import il.co.topq.systems.report.events.config.SetUIConfigurationsEvent;
	import il.co.topq.systems.report.events.general.DeleteScenarioCustomReportEvent;
	import il.co.topq.systems.report.events.general.DeleteScenarioEvent;
	import il.co.topq.systems.report.events.general.DeleteTestCustomReportEvent;
	import il.co.topq.systems.report.events.general.DeleteTestEvent;
	import il.co.topq.systems.report.events.legacy.LegacyGetScenarioEvent;
	import il.co.topq.systems.report.events.scenario.CompareScenarioEvent;
	import il.co.topq.systems.report.events.scenario.DeleteOrphanPropertiesEvent;
	import il.co.topq.systems.report.events.scenario.DeleteScenarioByScenarioQueryEvent;
	import il.co.topq.systems.report.events.scenario.ExportScenarioDetailsEvent;
	import il.co.topq.systems.report.events.scenario.ExportScenarioReportEvent;
	import il.co.topq.systems.report.events.scenario.GetScenarioByIDEvent;
	import il.co.topq.systems.report.events.scenario.GetScenarioEvent;
	import il.co.topq.systems.report.events.scenario.GetScenarioPropertiesKeysEvent;
	import il.co.topq.systems.report.events.scenario.GetScenarioPropertiesValuesEvent;
	import il.co.topq.systems.report.events.scenario.GetScenarioSizeEvent;
	import il.co.topq.systems.report.events.scenario.GetScenarioTestsEvent;
	import il.co.topq.systems.report.events.scenario.GetScenarioTestsSizeEvent;
	import il.co.topq.systems.report.events.scenario.GetScenariosByFilterEvent;
	import il.co.topq.systems.report.events.scenario.GetScenariosByFilterSizeEvent;
	import il.co.topq.systems.report.events.scenario.UpdateScenarioEvent;
	import il.co.topq.systems.report.events.scenarioCustomReport.CreateScenarioCustomReportEvent;
	import il.co.topq.systems.report.events.scenarioCustomReport.ExportScenarioCustomReportEvent;
	import il.co.topq.systems.report.events.scenarioCustomReport.GetScenarioCustomReportByChunkEvent;
	import il.co.topq.systems.report.events.scenarioCustomReport.GetScenarioCustomReportByIDEvent;
	import il.co.topq.systems.report.events.scenarioCustomReport.GetScenarioCustomReportSizeEvent;
	import il.co.topq.systems.report.events.states.ChangeReportsStateEvent;
	import il.co.topq.systems.report.events.states.ChangeSettingStateEvent;
	import il.co.topq.systems.report.events.states.ChangeTopLevelEvent;
	import il.co.topq.systems.report.events.test.ExportTestReportEvent;
	import il.co.topq.systems.report.events.test.GetGroupTestsEvent;
	import il.co.topq.systems.report.events.test.GetGroupTestsSizeEvent;
	import il.co.topq.systems.report.events.test.GetScenarioByTestIdEvent;
	import il.co.topq.systems.report.events.test.GetTestByIDEvent;
	import il.co.topq.systems.report.events.test.GetTestEvent;
	import il.co.topq.systems.report.events.test.GetTestGroupEvent;
	import il.co.topq.systems.report.events.test.GetTestGroupSizeEvent;
	import il.co.topq.systems.report.events.test.GetTestLogFolderEvent;
	import il.co.topq.systems.report.events.test.GetTestPropertiesKeysEvent;
	import il.co.topq.systems.report.events.test.GetTestScenarioEvent;
	import il.co.topq.systems.report.events.test.GetTestSizeEvent;
	import il.co.topq.systems.report.events.test.GetTestsByFilterEvent;
	import il.co.topq.systems.report.events.test.GetTestsByFilterSizeEvent;
	import il.co.topq.systems.report.events.test.UpdateTestEvent;
	import il.co.topq.systems.report.events.testCustomReport.CreateTestCustomReportEvent;
	import il.co.topq.systems.report.events.testCustomReport.ExportTestCustomReportEvent;
	import il.co.topq.systems.report.events.testCustomReport.GetScenarioCustomReportPropertiesValuesEvent;
	import il.co.topq.systems.report.events.testCustomReport.GetTestCustomReportByChunkEvent;
	import il.co.topq.systems.report.events.testCustomReport.GetTestCustomReportByIDEvent;
	import il.co.topq.systems.report.events.testCustomReport.GetTestCustomReportPropertiesValuesEvent;
	import il.co.topq.systems.report.events.testCustomReport.GetTestCustomReportSizeEvent;
	import il.co.topq.systems.report.events.user.CreateUserEvent;
	import il.co.topq.systems.report.events.user.DeleteUserEvent;
	import il.co.topq.systems.report.events.user.GetUsersEvent;
	import il.co.topq.systems.report.events.user.UpdateUserEvent;
	


	public class ReportController extends FrontController
	{
		public function ReportController()
		{
			initialiseCommands();
		}
		
		public function initialiseCommands() : void
		{
			
			//Users
			addCommand(GetUsersEvent.GET_USERS_EVENT,GetUsersCommand);
			addCommand(UpdateUserEvent.UPDATE_USER_EVENT,UpdateUserCommand);
			addCommand(CreateUserEvent.CREATE_USER_EVENT,CreateUserCommand);
			addCommand(DeleteUserEvent.DELETE_USER_EVENT,DeleteUserCommand);
			
			//Configuration
			addCommand(IsConfigAvailableEvent.IS_CONFIG_AVAILABLE_EVENT, IsConfigurationAvailableCommand);
			addCommand(SetConfigEvent.SET_CONFIG_EVENT, SetConfigCommand);
			addCommand(GetUIConfigurationsEvent.GET_SYSTEM_SETTINGS_EVENT,GetUIConfigurationsCommand);
			addCommand(SetUIConfigurationsEvent.SET_UI_CONFIGURATIONS_EVENT,SetUIConfigurationsCommand);
			addCommand(SetSystemSettingEvent.SET_SYSTEM_SETTINGS_EVENT, SetSystemSettingsCommand);
			addCommand(GetGridConfigurationEvent.GET_GRID_CONFIGURATION_EVENT, GetGridConfigurationCommand);
			addCommand(CreateGridConfigurationEvent.CREATE_GRID_CONFIGURATION_EVENT,CreateGridConfigurationCommand);
			
			//Commands that change application state (navigation)
			addCommand(ChangeTopLevelEvent.CHANGE_TOP_LEVEL_EVENT, ChangeTopLevelCommand);
			addCommand(ChangeReportsStateEvent.CHANGE_REPORTS_STATE_EVENT, ChangeReportsStateCommand);
			addCommand(ChangeSettingStateEvent.CHANGE_SETTINGS_STATE_EVENT, ChangeSettingsStateCommand);
			
			//Commands that are related to the business logic of the application
			addCommand(LoginEvent.LOGIN_EVENT, LoginCommand);
			addCommand(LogoutEvent.LOGOUT_EVENT, LogoutCommand);
			addCommand(GetSummaryPageEvent.GET_SUMMARY_PAGE_EVENT, GetSummaryPageCommand);
			
			//Legacy Report Section
			addCommand(LegacyGetScenarioEvent.LEGACY_GET_SCENARIO_EVENT, GetScenarioCommand);
			
			//Scenario Report Section
			addCommand(DeleteOrphanPropertiesEvent.DELETE_ORPHAN_PROPERTIES_EVENT,DeleteOrphanPropertiesCommand);
			addCommand(DeleteScenarioByScenarioQueryEvent.DELETE_SCENARIO_BY_SCENARIO_QUERY_EVENT,DeleteScenarioByScenarioQueryCommand);
			addCommand(UpdateScenarioEvent.UPDATE_SCENARIO_EVENT,UpdateScenarioCommand);
			addCommand(GetScenarioEvent.EXECUTION_GET_SCENARIO_EVENT, GetScenarioCommand);
			addCommand(GetScenarioSizeEvent.GET_SCENARIO_SIZE_EVENT,GetScenarioQuerySizeCommand);
			addCommand(GetScenarioPropertiesKeysEvent.GET_SCENARIO_PROPERTIES_VALUES_EVENT,GetPropertiesKeysCommand);
			addCommand(DeleteScenarioEvent.DELETE_SCENARIO_EVENT, DeleteScenarioCommand);
			addCommand(GetScenarioTestsEvent.GET_SCENARIO_TESTS_EVENT,GetScenarioTestsCommand);
			addCommand(GetScenariosByFilterEvent.GET_SCENARIOS_BY_FILTER_EVENT,GetScenariosByFilterCommand);
			addCommand(GetScenariosByFilterSizeEvent.GET_SCENARIOS_BY_FILTER_SIZE_EVENT,GetScenariosByFilterSizeCommand);
			addCommand(GetScenarioTestsSizeEvent.GET_SCENARIO_TESTS_SIZE_EVENT,GetScenarioTestsSizeCommand);
			addCommand(ExportScenarioReportEvent.EXPORT_SCENARIO_REPORT_EVENT,ExportScenarioCommand);
			addCommand(ExportScenarioDetailsEvent.EXPORT_SCENARIO_DETAILS_EVENT,ExportScenarioDetailsCommand);
			addCommand(CompareScenarioEvent.COMPARE_SCENARIO_EVENT,CompareScenarioCommand);
			addCommand(GetScenarioByIDEvent.GET_SCENARIO_BY_ID_EVENT,GetScenarioByIDCommand);
			addCommand(GetScenarioPropertiesValuesEvent.GET_SCENARIO_PROPERTIES_VALUES,GetScenarioPropertyValuesCommand);
		
			//Test Command
			addCommand(GetGroupTestsEvent.GET_GROUP_TESTS_EVENT,GetGroupTestsCommand);
			addCommand(GetGroupTestsSizeEvent.GET_GROUP_TESTS_SIZE,GetGroupTestSizeCommand);
			addCommand(GetTestEvent.GET_TEST_EVENT, GetTestCommand);
			addCommand(UpdateTestEvent.UPDATE_TEST_EVENT, UpdateTestCommand);
			addCommand(DeleteTestEvent.DELETE_TEST_EVENT, DeleteTestCommand);
			addCommand(GetTestSizeEvent.GET_TEST_SIZE_EVENT,GetTestQuerySizeCommand);
			addCommand(GetTestPropertiesKeysEvent.GET_TEST_PROPERTIES_VALUES_EVENT,GetPropertiesKeysCommand);
			//addCommand(GetTestPropertiesKeysInEditModeEvent.GET_TEST_PROPERTIES_KEYS_IN_EDIT_MODE_EVENT,GetTestPropertiesKeyInEditModeCommand);
			addCommand(GetTestsByFilterEvent.GET_TESTS_BY_FILTER_EVENT,GetTestsByFilterCommand);
			addCommand(GetTestsByFilterSizeEvent.GET_TESTS_BY_FILTER_SIZE_EVENT,GetTestsByFilterSizeCommand);
			addCommand(ExportTestReportEvent.EXPORT_TEST_REPORT_EVENT,ExportTestCommand);
			addCommand(GetTestByIDEvent.GET_TEST_BY_ID_EVENT,GetTestByIDCommand);
			addCommand(GetTestScenarioEvent.GET_TEST_SCENARIO_EVENT,GetTestScenarioCommand);
			addCommand(GetTestLogFolderEvent.GET_TEST_LOG_FOLDER_EVENT,GetTestLogFolderCommand);
			addCommand(GetScenarioByTestIdEvent.GET_SCENARIO_BY_TEST_ID_EVENT,GetScenarioByTestIdCommand);

			//General Command
			addCommand(GetTestGroupSizeEvent.GET_TEST_GROUP_SIZE_EVENT,GetTestQuerySizeCommand);
			addCommand(GetTestGroupEvent.GET_TEST_GROUP_EVENT,GetTestGroupCommand);
			addCommand(GetLegacyReportToolEvent.GET_LEGACY_REPORT_TOOL_EVENT, GetLegacyReportToolCommand);
			
			//ScenarioCustomReport			
			addCommand(DeleteScenarioCustomReportEvent.DELETE_SCENARIO_CUSTOM_REPORT_EVENT,DeleteScenarioCustomReportCommand);			
			addCommand(GetScenarioCustomReportByChunkEvent.GET_SCENARIO_CUSTOM_REPORT_EVENT, GetScenarioCustomReportCommand);
			addCommand(GetScenarioCustomReportSizeEvent.GET_SCENARIO_CUSTOM_REPORT_SIZE_EVENT,GetScenarioQuerySizeCommand);
			addCommand(CreateScenarioCustomReportEvent.CREATE_SCENARIO_CUSTOM_REPORT_EVENT,CreateScenarioCustomReportEventCommand);
			addCommand(GetScenarioCustomReportByIDEvent.GET_SCENARIO_CUSTOM_REPORT_BY_ID_EVENT,GetScenarioCustomReportByIDCommand);
			addCommand(GetScenarioCustomReportPropertiesValuesEvent.GET_SCENARIO_CUSTOM_REPORT_PROPERTIES_VALUES_EVENT,GetScenarioCustomReportPropertiesValuesCommand);
			addCommand(ExportScenarioCustomReportEvent.EXPORT_SCENARIO_CUSTOM_REPORT_EVENT,ExportScenarioCustomReportCommand);
			
			//TestCustomReport
			addCommand(GetTestCustomReportByChunkEvent.GET_CUSTOM_REPORT_EVENT,GetTestCustomReportByChunkCommand);
			addCommand(DeleteTestCustomReportEvent.DELETE_TEST_CUSTOM_REPORT_EVENT,DeleteTestCustomReportCommand);
			addCommand(GetTestCustomReportSizeEvent.GET_TEST_CUSTOM_REPORT_SIZE_EVENT,GetTestQuerySizeCommand);
			addCommand(CreateTestCustomReportEvent.CREATE_TEST_CUSTOM_REPORT_EVENT,CreateTestCustomReportEventCommand);
			addCommand(GetTestCustomReportByIDEvent.GET_TEST_CUSTOM_REPORT_BY_ID_EVENT,GetTestCustomReportByIDCommand);
			addCommand(GetTestCustomReportPropertiesValuesEvent.GET_TEST_CUSTOM_REPORT_PROPERTIES_VALUES_EVENT,GetTestCustomReportPropertiesValuesCommand);
			addCommand(ExportTestCustomReportEvent.EXPORT_TEST_CUSTOM_REPORT_EVENT,ExportTestCustomReportCommand);
		}
		
	}
}