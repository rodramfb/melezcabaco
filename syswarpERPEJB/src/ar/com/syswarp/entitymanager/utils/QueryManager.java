package ar.com.syswarp.entitymanager.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ar.com.syswarp.utils.StringUtils;

public class QueryManager {

	private static QueryManager _instance = new QueryManager();
	private Logger logger;

	private QueryManager() {

	}

	public static QueryManager getInstance(Logger logger) {
		_instance.logger = logger;
		return _instance;
	}

	protected PreparedStatement fillStatement(PreparedStatement stmt,
			Object... params) throws SQLException {
		for (int i = 0; i < params.length; i++) {
			if (params[i] != null) {
				stmt.setObject(i + 1, params[i]);
			} else {
				stmt.setNull(i + 1, Types.VARCHAR);
			}
		}
		return stmt;
	}

	public List<String[]> asList(Connection connection, String query,
			Object... params) throws SQLException, Exception {

		List<String[]> list = new ArrayList<String[]>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;

		try {
			preparedStatement = fillStatement(connection
					.prepareStatement(query), params);
			resultSet = preparedStatement.executeQuery();
			resultSetMetaData = resultSet.getMetaData();
			while (resultSet.next()) {
				int totalFields = resultSetMetaData.getColumnCount() - 1;
				String[] line = new String[totalFields + 1];
				int i = 0;
				while (i <= totalFields) {
					line[i] = resultSet.getString(++i);
				}
				list.add(line);
			}
		} finally {
			if (resultSet != null) {
				resultSet.close();
				resultSet = null;
				if (resultSetMetaData != null) {
					resultSetMetaData = null;
				}
			}
		}
		return list;

	}
	
	public int executeQuery(Connection connection, String query,
			Object... params) throws SQLException, Exception {

		PreparedStatement preparedStatement = null;
		int rowsAffected = 0;

		System.out.println("Query: " + query);
		System.out.println("Params: " + StringUtils.join(params, ", ", false, "null"));
		
		preparedStatement = fillStatement(connection.prepareStatement(query),
				params);
		rowsAffected = preparedStatement.executeUpdate();

		return rowsAffected;

	}

}
