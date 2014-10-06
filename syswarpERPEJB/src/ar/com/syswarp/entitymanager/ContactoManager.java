package ar.com.syswarp.entitymanager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ar.com.syswarp.db.IBacoConstants.IBacoBaseQuerys;
import ar.com.syswarp.db.IBacoConstants.IBacoTables;
import ar.com.syswarp.entity.Contacto;
import ar.com.syswarp.entitymanager.utils.QueryManager;
import ar.com.syswarp.utils.LogUtils;

public class ContactoManager extends
		AbstractEntityManager<Contacto> {

	public ContactoManager(Logger logger) {
		super(logger);
	}

	@Override
	protected String getBaseQuery() {
		return IBacoBaseQuerys.BASE_QUERY_CONTACTO;
	}

	@Override
	protected String getTableName() {
		return IBacoTables.TABLE_CONTACTO;
	}

	@Override
	public boolean exists(Connection connection, Contacto entity) {

		List<String[]> byPK = getByPK(connection, entity);
		return byPK != null && !byPK.isEmpty();
	}

	@Override
	public String delete(Connection connection, Contacto entity) {

		if (!exists(connection, entity)) {
			return "Error: Registro inexistente";
		}

		String cQuery = "DELETE FROM " 
			+ getTableName() 
			+ " WHERE idcontacto = ? AND idempresa = ? ";

		try {

			int rowsaffected = QueryManager.getInstance(logger).executeQuery(connection, cQuery, 
					entity.getIdcontacto(), 
					entity.getIdempresa());

			if (rowsaffected == 1) {
				return "Baja Correcta";
			}

		} catch (SQLException e) {
			LogUtils.logSQLException(logger, "ContactoManager :: delete()", e);
		} catch (Exception e) {
			LogUtils.logException(logger, "ContactoManager :: delete()", e);
		}

		return "Imposible eliminar el registro";
	}

	@Override
	public String createOrUpdate(Connection connection, Contacto entity) {

		if (!exists(connection, entity)) {
			return create(connection, entity);
		}

		return update(connection, entity);
	}

	@Override
	public String create(Connection connection, Contacto entity) {

		String cQuery = "INSERT INTO " 
			+ getTableName() 
			+ " ( "
			+ "   descripcion, "
			+ "   idtipocontacto, "
			+ "   idcanalcontacto, "
			+ "   idmotivocontacto, "
			+ "   idaccioncontacto, "
			+ "   idresultadocontacto, "
			+ "   usuarioalt, "
			+ "   fechaalt, "
			+ "   idempresa, "
			+ "   idcliente "
			+ " ) "
			+ " VALUES ("
			+ "   ?, ?, ?, ?, ?, ?, ?, ?, ?, ? "
			+ " ) ";

		try {

			String error = entity.validateCreate();
			if (error != null) {
				return error;
			}
			
			int rowsaffected = QueryManager.getInstance(logger).executeQuery(connection, cQuery, 
					entity.getDescripcion(), 
					entity.getIdtipocontacto(), 
					entity.getIdcanalcontacto(), 
					entity.getIdmotivocontacto(),
					entity.getIdaccioncontacto(),
					entity.getIdresultadocontacto(),
					entity.getUsuarioalt(), 
					entity.getFechaalt(), 
					entity.getIdempresa(), 
					entity.getIdcliente());

			if (rowsaffected == 1) {
				return "Alta Correcta";
			}

		} catch (SQLException e) {
			LogUtils.logSQLException(logger, "ContactoManager :: create()", e);
		} catch (Exception e) {
			LogUtils.logException(logger, "ContactoManager :: create()", e);
		}

		return "Imposible dar el alta del registro";
	}

	@Override
	public String update(Connection connection, Contacto entity) {

		String cQuery = "UPDATE " 
			+ getTableName() 
			+ " SET "
			+ "   descripcion = ?, "
			+ "   idtipocontacto = ?, "
			+ "   idcanalcontacto = ?, "
			+ "   idmotivocontacto = ?, "
			+ "   idaccioncontacto = ?, "
			+ "   idresultadocontacto = ?, "
			+ "   usuarioact = ?, "
			+ "   fechaact = ?, "
			+ "   idempresa = ? "
			+ " WHERE idcontacto = ? ";
		
		try {

			String error = entity.validateUpdate();
			if (error != null) {
				return error;
			}
			
			int rowsaffected = QueryManager.getInstance(logger).executeQuery(connection, cQuery, 
					entity.getDescripcion(), 
					entity.getIdtipocontacto(), 
					entity.getIdcanalcontacto(), 
					entity.getIdmotivocontacto(),
					entity.getIdaccioncontacto(),
					entity.getIdresultadocontacto(),
					entity.getUsuarioact(), 
					entity.getFechaact(), 
					entity.getIdempresa(), 
					entity.getIdcontacto());

			if (rowsaffected > 0) {
				return "Actualizacion Correcta";
			}

		} catch (SQLException e) {
			LogUtils.logSQLException(logger, "ContactoManager :: update()", e);
		} catch (Exception e) {
			LogUtils.logException(logger, "ContactoManager :: update()", e);
		}

		return "Imposible actualizar el registro";
	}

	@Override
	public List<String[]> getList(Connection connection, Contacto entity) {

		String cQuery = getBaseQuery();

		List<String[]> list = new ArrayList<String[]>();
		try {

			list = QueryManager.getInstance(logger).asList(connection, cQuery,
					entity.getIdempresa());

		} catch (SQLException e) {
			LogUtils.logException(logger, "ContactoManager :: getList()", e);
		} catch (Exception e) {
			LogUtils.logException(logger, "ContactoManager :: getList()", e);
		}

		return list;
	}

	@Override
	public List<String[]> getAll(Connection connection, Contacto entity,
			long limit, long offset) {

		String cQuery = getBaseQuery()
			+ " AND sc.idcliente = ? "
			+ " ORDER BY 1 "
			+ " LIMIT ? OFFSET ? ";

		List<String[]> list = new ArrayList<String[]>();
		try {

			list = QueryManager.getInstance(logger).asList(connection, cQuery,
					entity.getIdempresa(), 
					limit, 
					offset);

		} catch (SQLException e) {
			LogUtils.logException(logger, "ContactoManager :: getAll()", e);
		} catch (Exception ex) {
			LogUtils.logException(logger, "ContactoManager :: getAll()", ex);
		}

		return list;
	}

	@Override
	public List<String[]> getByOcurrence(Connection connection,
			Contacto entity, String ocurrence, long limit, long offset) {

		String cQuery = getBaseQuery() 
			+ " AND ("
			+ "   UPPER(sc.descripcion) LIKE '%?%' "
			+ "   OR UPPER(tc.tipocontacto) like '%?%' "
			+ "   OR UPPER(cl.razon) like '%?%' "
			+ " ) "
			+ " AND sc.idcliente = ? "
			+ " ORDER BY 1 "
			+ " LIMIT ? OFFSET ? ";

		List<String[]> list = new ArrayList<String[]>();
		try {

			list = QueryManager.getInstance(logger).asList(connection, cQuery,
					entity.getIdempresa(), 
					ocurrence.toUpperCase(), 
					ocurrence.toUpperCase(),
					ocurrence.toUpperCase(),
					entity.getIdcliente(),
					limit, 
					offset);

		} catch (SQLException e) {
			LogUtils.logException(logger,
					"ContactoManager :: getByOcurrence()", e);
		} catch (Exception e) {
			LogUtils.logException(logger,
					"ContactoManager :: getByOcurrence()", e);
		}

		return list;
	}

	@Override
	public List<String[]> getByPK(Connection connection, Contacto entity) {

		String cQuery = getBaseQuery()
			+ " AND sc.idcontacto = ? ";

		List<String[]> list = new ArrayList<String[]>();
		try {

			list = QueryManager.getInstance(logger).asList(connection, cQuery,
					entity.getIdempresa(), 
					entity.getIdcontacto());

		} catch (SQLException e) {
			LogUtils.logException(logger, "ContactoManager :: getByPK()", e);
		} catch (Exception e) {
			LogUtils.logException(logger, "ContactoManager :: getByPK()", e);
		}

		return list;
	}

}