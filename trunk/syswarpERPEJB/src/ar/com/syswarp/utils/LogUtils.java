package ar.com.syswarp.utils;

import java.sql.SQLException;

import org.apache.log4j.Logger;

public class LogUtils {

	public static void logSQLException(Logger logger,
			String methodIdentification, SQLException sqlException) {
		logger.error(String.format("SQLException in %s :  %s",
				methodIdentification, sqlException.toString()));
	}

	public static void logSQLExceptionExtended(Logger logger,
			String methodIdentification, SQLException sqlException,
			String... params) {
		logger.error(String.format("SQLException in %s :  %s - %s",
				methodIdentification, StringUtils.join(params), sqlException
						.toString()));
	}

	public static void logException(Logger logger, String methodIdentification,
			Exception exception) {
		logger.error(String.format("Exception in %s :  %s",
				methodIdentification, exception.toString()));
	}

	public static void logExceptionExtended(Logger logger,
			String methodIdentification, Exception exception, String... params) {
		logger.error(String.format("Exception in %s :  %s - %s",
				methodIdentification, StringUtils.join(params), exception
						.toString()));
	}

}
