package ar.com.syswarp.entitymanager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ar.com.syswarp.db.IBacoConstants.IBacoBaseQuerys;
import ar.com.syswarp.db.IBacoConstants.IBacoTables;
import ar.com.syswarp.entity.ResultadoContacto;
import ar.com.syswarp.entitymanager.utils.QueryManager;
import ar.com.syswarp.utils.LogUtils;

public class ResultadoContactoManager extends
		AbstractEntityManager<ResultadoContacto> {

	public ResultadoContactoManager(Logger logger) {
		super(logger);
	}

	@Override
	protected String getBaseQuery() {
		return IBacoBaseQuerys.BASE_QUERY_RESULTADO_CONTACTO;
	}

	@Override
	protected String getTableName() {
		return IBacoTables.TABLE_RESULTADO_CONTACTO;
	}

	@Override
	public boolean exists(Connection connection, ResultadoContacto entity) {

		List<String[]> byPK = getByPK(connection, entity);
		return byPK != null && !byPK.isEmpty();
	}

	public boolean existsExactMatch(Connection connection, ResultadoContacto entity) {

		String cQuery = getBaseQuery()
			+ " AND rc.idaccioncontacto = ? "
			+ " AND rc.idmotivocontacto = ? "
			+ " AND rc.idcanalcontacto = ? "
			+ " AND rc.idtipocontacto = ? "
			+ " AND rc.resultadocontacto = ? "
			;

		List<String[]> list = new ArrayList<String[]>();
		try {
	
			list = QueryManager.getInstance(logger).asList(connection, cQuery,
					entity.getIdempresa(), 
					entity.getIdaccioncontacto(), 
					entity.getIdmotivocontacto(), 
					entity.getIdcanalcontacto(), 
					entity.getIdtipocontacto(), 
					entity.getResultadocontacto().trim());
	
		} catch (SQLException e) {
			LogUtils.logException(logger, "ResultadoContactoManager :: existsExactMatch()", e);
		} catch (Exception e) {
			LogUtils.logException(logger, "ResultadoContactoManager :: existsExactMatch()", e);
		}
	
		return list != null && !list.isEmpty();
	}
	
	public boolean isInUse(Connection connection, ResultadoContacto entity) {

		String cQuery = ""
			+ " SELECT c.idresultadocontacto "
			+ " FROM SASCONTACTOS c "
			+ " WHERE c.idempresa = ? AND c.idresultadocontacto = ? ";
	
		List<String[]> list = new ArrayList<String[]>();
		try {
	
			list = QueryManager.getInstance(logger).asList(connection, cQuery,
					entity.getIdempresa(), 
					entity.getIdresultadocontacto());
	
		} catch (SQLException e) {
			LogUtils.logException(logger, "ResultadoContactoManager :: isInUse()", e);
		} catch (Exception e) {
			LogUtils.logException(logger, "ResultadoContactoManager :: isInUse()", e);
		}
	
		return list != null && !list.isEmpty();
	}
	
	@Override
	public String delete(Connection connection, ResultadoContacto entity) {

		if (!exists(connection, entity)) {
			return "Error: Registro inexistente";
		}
		
		if (isInUse(connection, entity)) {
			return "Error: El registro se encuentra asociado a un contacto existente";
		}

		String cQuery = "DELETE FROM " 
			+ getTableName() 
			+ " WHERE idresultadocontacto = ? AND idempresa = ? ";

		try {

			int rowsaffected = QueryManager.getInstance(logger).executeQuery(connection, cQuery, 
					entity.getIdresultadocontacto(), 
					entity.getIdempresa());

			if (rowsaffected == 1) {
				return "Baja Correcta";
			}

		} catch (SQLException e) {
			LogUtils.logSQLException(logger, "ResultadoContactoManager :: delete()", e);
		} catch (Exception e) {
			LogUtils.logException(logger, "ResultadoContactoManager :: delete()", e);
		}

		return "Imposible eliminar el registro";
	}

	@Override
	public String createOrUpdate(Connection connection, ResultadoContacto entity) {

		if (!exists(connection, entity)) {
			return create(connection, entity);
		}

		return update(connection, entity);
	}

	@Override
	public String create(Connection connection, ResultadoContacto entity) {

		if (existsExactMatch(connection, entity)) {
			return "Error: Ya existe un registro con los mismos valores";
		}
		
		String cQuery = "INSERT INTO " 
			+ getTableName() 
			+ " ( "
			+ "   resultadocontacto, "
			+ "   idtipocontacto, "
			+ "   idcanalcontacto, "
			+ "   idmotivocontacto, "
			+ "   idaccioncontacto, "
			+ "   usuarioalt, "
			+ "   fechaalt, "
			+ "   idempresa "
			+ " ) "
			+ " VALUES ("
			+ "   ?, ?, ?, ?, ?, ?, ?, ? "
			+ " ) ";

		try {

			String error = entity.validateCreate();
			if (error != null) {
				return error;
			}
			
			int rowsaffected = QueryManager.getInstance(logger).executeQuery(connection, cQuery, 
					entity.getResultadocontacto(), 
					entity.getIdtipocontacto(), 
					entity.getIdcanalcontacto(), 
					entity.getIdmotivocontacto(),
					entity.getIdaccioncontacto(),
					entity.getUsuarioalt(), 
					entity.getFechaalt(), 
					entity.getIdempresa());

			if (rowsaffected == 1) {
				return "Alta Correcta";
			}

		} catch (SQLException e) {
			LogUtils.logSQLException(logger, "ResultadoContactoManager :: create()", e);
		} catch (Exception e) {
			LogUtils.logException(logger, "ResultadoContactoManager :: create()", e);
		}

		return "Imposible dar el alta del registro";
	}

	@Override
	public String update(Connection connection, ResultadoContacto entity) {

		if (existsExactMatch(connection, entity)) {
			return "Error: Ya existe un registro con los mismos valores";
		}
		
		String cQuery = "UPDATE " 
			+ getTableName() 
			+ " SET "
			+ "   resultadocontacto = ?, "
			+ "   idtipocontacto = ?, "
			+ "   idcanalcontacto = ?, "
			+ "   idmotivocontacto = ?, "
			+ "   idaccioncontacto = ?, "
			+ "   usuarioact = ?, "
			+ "   fechaact = ?, "
			+ "   idempresa = ? "
			+ " WHERE idresultadocontacto = ? ";

		try {

			String error = entity.validateUpdate();
			if (error != null) {
				return error;
			}
			
			int rowsaffected = QueryManager.getInstance(logger).executeQuery(connection, cQuery, 
					entity.getResultadocontacto(), 
					entity.getIdtipocontacto(), 
					entity.getIdcanalcontacto(), 
					entity.getIdmotivocontacto(),
					entity.getIdaccioncontacto(),
					entity.getUsuarioact(), 
					entity.getFechaact(), 
					entity.getIdempresa(), 
					entity.getIdresultadocontacto());

			if (rowsaffected > 0) {
				return "Actualizacion Correcta";
			}

		} catch (SQLException e) {
			LogUtils.logSQLException(logger, "ResultadoContactoManager :: update()", e);
		} catch (Exception e) {
			LogUtils.logException(logger, "ResultadoContactoManager :: update()", e);
		}

		return "Imposible actualizar el registro";
	}

	@Override
	public List<String[]> getList(Connection connection,
			ResultadoContacto entity) {

		String cQuery = getBaseQuery()
			+ " AND ("
			+ "   rc.idaccioncontacto = ? AND "
			+ "   rc.idmotivocontacto = ? AND "
			+ "   rc.idcanalcontacto = ? AND "
			+ "   rc.idtipocontacto = ? "
			+ " ) "
			+ " ORDER BY 2 ";

		List<String[]> list = new ArrayList<String[]>();
		try {

			list = QueryManager.getInstance(logger).asList(connection, cQuery,
					entity.getIdempresa(), 
					entity.getIdaccioncontacto(), 
					entity.getIdmotivocontacto(), 
					entity.getIdcanalcontacto(), 
					entity.getIdtipocontacto());

		} catch (SQLException e) {
			LogUtils.logException(logger, "ResultadoContactoManager :: getList()", e);
		} catch (Exception e) {
			LogUtils.logException(logger, "ResultadoContactoManager :: getList()", e);
		}

		return list;
	}

	@Override
	public List<String[]> getAll(Connection connection,
			ResultadoContacto entity, long limit, long offset) {

		String cQuery = getBaseQuery()
			+ " ORDER BY 2 "
			+ " LIMIT ? OFFSET ? ";

		List<String[]> list = new ArrayList<String[]>();
		try {

			list = QueryManager.getInstance(logger).asList(connection, cQuery,
					entity.getIdempresa(), 
					limit, 
					offset);

		} catch (SQLException e) {
			LogUtils.logException(logger, "ResultadoContactoManager :: getAll()", e);
		} catch (Exception ex) {
			LogUtils.logException(logger, "ResultadoContactoManager :: getAll()", ex);
		}

		return list;
	}

	@Override
	public List<String[]> getByOcurrence(Connection connection,
			ResultadoContacto entity, String ocurrence, long limit, long offset) {

		String cQuery = getBaseQuery() 
			+ " AND ("
			+ "   UPPER(rc.resultadocontacto) LIKE '%?%' "
			+ "   OR UPPER(tc.tipocontacto) like '%?%' "
			+ " ) "
			+ " ORDER BY 2 "
			+ " LIMIT ? OFFSET ? ";

		List<String[]> list = new ArrayList<String[]>();
		try {

			list = QueryManager.getInstance(logger).asList(connection, cQuery,
					entity.getIdempresa(), 
					ocurrence.toString(), 
					ocurrence.toString(), 
					limit, 
					offset);

		} catch (SQLException e) {
			LogUtils.logException(logger,
					"ResultadoContactoManager :: getByOcurrence()", e);
		} catch (Exception e) {
			LogUtils.logException(logger,
					"ResultadoContactoManager :: getByOcurrence()", e);
		}

		return list;
	}

	@Override
	public List<String[]> getByPK(Connection connection,
			ResultadoContacto entity) {

		String cQuery = getBaseQuery()
			+ " AND rc.idresultadocontacto = ? ";

		List<String[]> list = new ArrayList<String[]>();
		try {

			list = QueryManager.getInstance(logger).asList(connection, cQuery,
					entity.getIdempresa(), 
					entity.getIdresultadocontacto());

		} catch (SQLException e) {
			LogUtils.logException(logger, "ResultadoContactoManager :: getByPK()", e);
		} catch (Exception e) {
			LogUtils.logException(logger, "ResultadoContactoManager :: getByPK()", e);
		}

		return list;
	}

}
