package il.co.topq.systems.report.common.exception;

public class ErrorMessage {

	private ErrorMessage() {
	}

	public static final String USER_EXIST = "Error: user already exist";
	public static final String USER_INVALID = "Error: invalid user";
	public static final String INVALID_USER_PERMISSIONS = "Error: user have invalid permissions";
	public static final String USER_NOT_FOUND_IN_CONTEXT= "Error: user not found in context";

}
