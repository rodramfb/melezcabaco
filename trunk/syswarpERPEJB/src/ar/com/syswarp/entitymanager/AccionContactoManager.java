package ar.com.syswarp.entitymanager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ar.com.syswarp.db.IBacoConstants.IBacoBaseQuerys;
import ar.com.syswarp.db.IBacoConstants.IBacoTables;
import ar.com.syswarp.entity.AccionContacto;
import ar.com.syswarp.entitymanager.utils.QueryManager;
import ar.com.syswarp.utils.LogUtils;

public class AccionContactoManager extends
		AbstractEntityManager<AccionContacto> {

	public AccionContactoManager(Logger logger) {
		super(logger);
	}

	@Override
	protected String getBaseQuery() {
		return IBacoBaseQuerys.BASE_QUERY_ACCION_CONTACTO;
	}

	@Override
	protected String getTableName() {
		return IBacoTables.TABLE_ACCION_CONTACTO;
	}

	@Override
	public boolean exists(Connection connection, AccionContacto entity) {

		List<String[]> byPK = getByPK(connection, entity);
		return byPK != null && !byPK.isEmpty();
	}

	public boolean existsExactMatch(Connection connection, AccionContacto entity) {

		String cQuery = getBaseQuery()
			+ " AND ac.idmotivocontacto = ? "
			+ " AND ac.idcanalcontacto = ? "
			+ " AND ac.idtipocontacto = ? "
			+ " AND ac.accioncontacto = ? "
			;

		List<String[]> list = new ArrayList<String[]>();
		try {
	
			list = QueryManager.getInstance(logger).asList(connection, cQuery,
					entity.getIdempresa(), 
					entity.getIdmotivocontacto(), 
					entity.getIdcanalcontacto(), 
					entity.getIdtipocontacto(), 
					entity.getAccioncontacto().trim());
	
		} catch (SQLException e) {
			LogUtils.logException(logger, "AccionContactoManager :: existsExactMatch()", e);
		} catch (Exception e) {
			LogUtils.logException(logger, "AccionContactoManager :: existsExactMatch()", e);
		}
	
		return list != null && !list.isEmpty();
	}
	
	public boolean isInUse(Connection connection, AccionContacto entity) {

		String cQuery = ""
			+ " SELECT c.idresultadocontacto, c.idaccioncontacto "
			+ " FROM SASCONTACTOS c "
			+ " WHERE c.idempresa = ? AND c.idaccioncontacto = ? "
			+ " UNION "
			+ " SELECT rc.idresultadocontacto, rc.idaccioncontacto "
			+ " FROM SASRESULTADOSCONTACTOS rc "
			+ " WHERE rc.idempresa = ? AND rc.idaccioncontacto = ? ";
	
		List<String[]> list = new ArrayList<String[]>();
		try {
	
			list = QueryManager.getInstance(logger).asList(connection, cQuery,
					entity.getIdempresa(), 
					entity.getIdaccioncontacto(), 
					entity.getIdempresa(), 
					entity.getIdaccioncontacto());
	
		} catch (SQLException e) {
			LogUtils.logException(logger, "AccionContactoManager :: isInUse()", e);
		} catch (Exception e) {
			LogUtils.logException(logger, "AccionContactoManager :: isInUse()", e);
		}
	
		return list != null && !list.isEmpty();
	}
	
	@Override
	public String delete(Connection connection, AccionContacto entity) {

		if (!exists(connection, entity)) {
			return "Error: Registro inexistente";
		}

		if (isInUse(connection, entity)) {
			return "Error: El registro se encuentra asociado a un contacto existente";
		}
		
		String cQuery = "DELETE FROM " 
			+ getTableName() 
			+ " WHERE idaccioncontacto = ? AND idempresa = ? ";

		try {

			int rowsaffected = QueryManager.getInstance(logger).executeQuery(connection, cQuery, 
					entity.getIdaccioncontacto(), 
					entity.getIdempresa());

			if (rowsaffected == 1) {
				return "Baja Correcta";
			}

		} catch (SQLException e) {
			LogUtils.logSQLException(logger, "AccionContactoManager :: delete()", e);
		} catch (Exception e) {
			LogUtils.logException(logger, "AccionContactoManager :: delete()", e);
		}

		return "Imposible eliminar el registro";
	}

	@Override
	public String createOrUpdate(Connection connection, AccionContacto entity) {

		if (!exists(connection, entity)) {
			return create(connection, entity);
		}

		return update(connection, entity);
	}

	@Override
	public String create(Connection connection, AccionContacto entity) {

		if (existsExactMatch(connection, entity)) {
			return "Error: Ya existe un registro con los mismos valores";
		}
		
		String cQuery = "INSERT INTO " 
			+ getTableName() 
			+ " ( "
			+ "   accioncontacto, "
			+ "   idtipocontacto, "
			+ "   idcanalcontacto, "
			+ "   idmotivocontacto, "
			+ "   usuarioalt, "
			+ "   fechaalt, "
			+ "   idempresa "
			+ " ) "
			+ " VALUES ("
			+ "   ?, ?, ?, ?, ?, ?, ? "
			+ " ) ";

		try {

			String error = entity.validateCreate();
			if (error != null) {
				return error;
			}
			
			int rowsaffected = QueryManager.getInstance(logger).executeQuery(connection, cQuery, 
					entity.getAccioncontacto(), 
					entity.getIdtipocontacto(), 
					entity.getIdcanalcontacto(), 
					entity.getIdmotivocontacto(), 
					entity.getUsuarioalt(), 
					entity.getFechaalt(), 
					entity.getIdempresa());

			if (rowsaffected == 1) {
				return "Alta Correcta";
			}

		} catch (SQLException e) {
			LogUtils.logSQLException(logger, "AccionContactoManager :: create()", e);
		} catch (Exception e) {
			LogUtils.logException(logger, "AccionContactoManager :: create()", e);
		}

		return "Imposible dar el alta del registro";
	}

	@Override
	public String update(Connection connection, AccionContacto entity) {

		if (existsExactMatch(connection, entity)) {
			return "Error: Ya existe un registro con los mismos valores";
		}
		
		String cQuery = "UPDATE " 
			+ getTableName() 
			+ " SET "
			+ "   accioncontacto = ?, "
			+ "   idtipocontacto = ?, "
			+ "   idcanalcontacto = ?, "
			+ "   idmotivocontacto = ?, "
			+ "   usuarioact = ?, "
			+ "   fechaact = ?, "
			+ "   idempresa = ? "
			+ " WHERE idaccioncontacto = ? ";

		try {

			String error = entity.validateUpdate();
			if (error != null) {
				return error;
			}
			
			int rowsaffected = QueryManager.getInstance(logger).executeQuery(connection, cQuery, 
					entity.getAccioncontacto(), 
					entity.getIdtipocontacto(), 
					entity.getIdcanalcontacto(), 
					entity.getIdmotivocontacto(), 
					entity.getUsuarioact(), 
					entity.getFechaact(), 
					entity.getIdempresa(), 
					entity.getIdaccioncontacto());

			if (rowsaffected > 0) {
				return "Actualizacion Correcta";
			}

		} catch (SQLException e) {
			LogUtils.logSQLException(logger, "AccionContactoManager :: update()", e);
		} catch (Exception e) {
			LogUtils.logException(logger, "AccionContactoManager :: update()", e);
		}

		return "Imposible actualizar el registro";
	}

	@Override
	public List<String[]> getList(Connection connection, AccionContacto entity) {

		String cQuery = getBaseQuery()
			+ " AND ("
			+ "   ac.idmotivocontacto = ? AND "
			+ "   ac.idcanalcontacto = ? AND "
			+ "   ac.idtipocontacto = ? "
			+ " ) "
			+ " ORDER BY 2 ";

		List<String[]> list = new ArrayList<String[]>();
		try {

			list = QueryManager.getInstance(logger).asList(connection, cQuery,
					entity.getIdempresa(), 
					entity.getIdmotivocontacto(),
					entity.getIdcanalcontacto(), 
					entity.getIdtipocontacto());

		} catch (SQLException e) {
			LogUtils.logException(logger, "AccionContactoManager :: getList()", e);
		} catch (Exception e) {
			LogUtils.logException(logger, "AccionContactoManager :: getList()", e);
		}

		return list;
	}

	@Override
	public List<String[]> getAll(Connection connection, AccionContacto entity,
			long limit, long offset) {

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
			LogUtils.logException(logger, "AccionContactoManager :: getAll()", e);
		} catch (Exception ex) {
			LogUtils.logException(logger, "AccionContactoManager :: getAll()", ex);
		}

		return list;
	}

	@Override
	public List<String[]> getByOcurrence(Connection connection,
			AccionContacto entity, String ocurrence, long limit, long offset) {

		String cQuery = getBaseQuery() 
			+ " AND ("
			+ "   UPPER(ac.accioncontacto) LIKE '%?%' "
			+ "   OR UPPER(tc.tipocontacto) like '%?%' "
			+ " ) "
			+ " ORDER BY 2 "
			+ " LIMIT ? OFFSET ? ";

		List<String[]> list = new ArrayList<String[]>();
		try {

			list = QueryManager.getInstance(logger).asList(connection, cQuery,
					entity.getIdempresa(), 
					ocurrence.toUpperCase(), 
					ocurrence.toUpperCase(), 
					limit, 
					offset);

		} catch (SQLException e) {
			LogUtils.logException(logger,
					"AccionContactoManager :: getByOcurrence()", e);
		} catch (Exception e) {
			LogUtils.logException(logger,
					"AccionContactoManager :: getByOcurrence()", e);
		}

		return list;
	}

	@Override
	public List<String[]> getByPK(Connection connection, AccionContacto entity) {

		String cQuery = getBaseQuery()
			+ " AND ac.idaccioncontacto = ? ";

		List<String[]> list = new ArrayList<String[]>();
		try {

			list = QueryManager.getInstance(logger).asList(connection, cQuery,
					entity.getIdempresa(), 
					entity.getIdaccioncontacto());

		} catch (SQLException e) {
			LogUtils.logException(logger, "AccionContactoManager :: getByPK()", e);
		} catch (Exception e) {
			LogUtils.logException(logger, "AccionContactoManager :: getByPK()", e);
		}

		return list;
	}

}