//package il.co.topq.systems.report.tests.players;
//
//import il.co.topq.systems.report.beans.SettingsBean;
//import il.co.topq.systems.report.common.obj.SystemSettings;
//
//import java.io.IOException;
//import java.sql.SQLException;
//
//import javax.sql.rowset.serial.SerialException;
//
//public class SettingsReportSimulator {
//
//	private SettingsBean settingsBean = new SettingsBean();
//
//	public void saveSystemSettings(SystemSettings settings) throws IOException, SerialException, SQLException {
//		settingsBean.createSystemSettings(settings);
//	}
//
//	public SystemSettings getSystemSettings() throws IOException, ClassNotFoundException, SerialException, SQLException {
//		return settingsBean.getSystemSettings();
//	}
//
//	public void updateSystemSettings(SystemSettings settings) throws IOException, ClassNotFoundException, SQLException {
//		settingsBean.updateSystemSettings(settings);
//	}
//
//}
