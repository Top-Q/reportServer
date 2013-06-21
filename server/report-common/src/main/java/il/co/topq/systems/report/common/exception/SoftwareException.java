package il.co.topq.systems.report.common.exception;

public class SoftwareException extends RuntimeException {

	private static final long serialVersionUID = 4296748214860999419L;
	private final static String DESCRIPTION = "Code that should never be reached: ";

	public SoftwareException(String message) {
		this(DESCRIPTION + message, null);
	}

	public SoftwareException(StringBuilder message) {
		this(DESCRIPTION + message.toString(), null);
	}

	public SoftwareException(String message, Throwable cause) {
		super(message, cause);
	}
}
