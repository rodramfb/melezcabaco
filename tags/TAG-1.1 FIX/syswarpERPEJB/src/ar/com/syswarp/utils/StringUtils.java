package ar.com.syswarp.utils;

public class StringUtils {

	private static final String DEFAULT_CONJUNCTION_STRING = " ";
	private static final String DEFAULT_NULL_REPLACEMENT = "NULL";
	private static final boolean DEFAULT_SKIP_NULLS = false;

	public static Object join(Object[] strings) {
		return join(strings, DEFAULT_CONJUNCTION_STRING, DEFAULT_SKIP_NULLS, DEFAULT_NULL_REPLACEMENT);
	}

	public static Object join(Object[] strings, String conjunction) {
		return join(strings, conjunction, DEFAULT_SKIP_NULLS, DEFAULT_NULL_REPLACEMENT);
	}
	
	public static Object join(Object[] strings, String conjunction, String onNull) {
		return join(strings, conjunction, DEFAULT_SKIP_NULLS, onNull);
	}
	
	public static Object join(Object[] strings, String conjunction, boolean skipNulls) {
		return join(strings, conjunction, skipNulls, DEFAULT_NULL_REPLACEMENT);
	}
	
	public static String join(Object[] strings, String conjunction, boolean skipNulls, String onNull) {
		
		if (strings == null) {
			return null;
		}
		
		conjunction = processConjunctionString(conjunction);
		int limit = calculateLimit(strings);

		Object current = null;
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < limit; i++) {
			current = strings[i];
			if (current == null) {
				if (skipNulls) {
					continue;
				}
				current = processNullReplacement(onNull);
			}
			builder.append(current.toString());
			if (i < limit - 1) {
				builder.append(conjunction);
			}
		}
		
		return builder.toString();
	}

	private static String processNullReplacement(String onNull) {
		if (onNull == null) {
			return DEFAULT_NULL_REPLACEMENT;
		}
		return onNull;
	}

	private static String processConjunctionString(String conjunction) {
		if (conjunction == null) {
			return DEFAULT_CONJUNCTION_STRING;
		}
		return conjunction;
	}
	
	private static int calculateLimit(Object[] strings) {
		return strings.length;
	}

}
