package jsystem.publisher.plugin;

import java.io.File;
import java.util.Arrays;

import jsystem.framework.FrameworkOptions;
import jsystem.framework.JSystemProperties;
import jsystem.runner.agent.publisher.Publisher;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

/**
 * Command line interface for the reports server publisher. This allows users to
 * publish report to the reports server while continue the work on JSystem
 * 
 * @author itai_a
 * 
 */
public class ReportsServerCommandLinePublisher extends ReportsServerPublisher {

	private static Option file = new Option("l", "logFolder", true,
			"Log folder destination. e.g c:/jsystem/runner/log/current/");

	private static Option help = new Option("?", "help", false, "Print this usage");

	private static Option host = new Option("h", "host", true, "Host name or IP of the report server. e.g 127.0.0.1");

	private static Option port = new Option("p", "port", true, "Reports Server port. e.g 8080");

	private static File logFolder;

	public static void main(String[] args) {
		CommandLine cmd = parseCommandLine(args);
		if (null == cmd) {
			// There is a problem with the command line arguemnts. existing.
			return;
		}
		configureProperties(cmd);
		Publisher publisher = new ReportsServerCommandLinePublisher();
		System.out.println("About to publish reports");

		try {
			publisher.publish(null, true);
			System.out.println("Finished publishing reports");
		} catch (Throwable e) {
			System.out.println("Failed publishing reports. " + e.getMessage());
		}

	}

	/**
	 * Spreads all the parameters that were specified in the command line to the
	 * proper places
	 * 
	 * @param cmd
	 */
	private static void configureProperties(final CommandLine cmd) {
		if (cmd.hasOption(host.getOpt())) {
			JSystemProperties.getInstance().setPreference(FrameworkOptions.REPORTS_PUBLISHER_HOST, host.getValue());
		}
		if (cmd.hasOption(port.getOpt())) {
			JSystemProperties.getInstance().setPreference(FrameworkOptions.REPORTS_PUBLISHER_PORT, port.getValue());
		}
		if (cmd.hasOption(file.getOpt())) {
			logFolder = new File(file.getValue());
		}

	}

	@Override
	protected File getCurrentLogFolder() {
		return logFolder;
	}

	/**
	 * Parses the command line arguments.
	 * 
	 * @param args
	 * @return null if failed to parse the arguments or if nothings needs to be
	 *         done after (for example, when user asks for the usage screen).
	 */
	private static CommandLine parseCommandLine(String[] args) {
		Options options = new Options();
		options.addOption(file);
		options.addOption(help);
		options.addOption(host);
		options.addOption(port);
		final CommandLineParser parser = new PosixParser();
		CommandLine cmd = null;
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			System.err.println("Failed parsing command line argument: " + Arrays.toString(args));
			return null;
		}
		if (cmd.hasOption(help.getOpt()) || !cmd.hasOption(file.getOpt())) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("JSystem Publisher", options);
			return null;

		}
		return cmd;
	}
}
