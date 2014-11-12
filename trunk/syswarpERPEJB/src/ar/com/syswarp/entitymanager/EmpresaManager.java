package ar.com.syswarp.entitymanager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ar.com.syswarp.db.IBacoConstants.IBacoBaseQuerys;
import ar.com.syswarp.db.IBacoConstants.IBacoTables;
import ar.com.syswarp.entity.Empresa;
import ar.com.syswarp.entitymanager.utils.EntityQueryManager;
import ar.com.syswarp.entitymanager.utils.QueryObjectTransformer;
import ar.com.syswarp.utils.LogUtils;

public class EmpresaManager implements IEntityManager<Empresa> {

	private Logger logger;

	public EmpresaManager(Logger logger) {
		this.logger = logger;
	}

	@Override
	public String create(Connection connection, Empresa entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createOrUpdate(Connection connection, Empresa entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(Connection connection, Empresa entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(Connection connection, Empresa entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String[]> getAll(Connection connection, Empresa entity,
			long limit, long offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getBaseQuery() {
		return IBacoBaseQuerys.BASE_QUERY_EMPRESA;
	}

	@Override
	public List<String[]> getByOcurrence(Connection connection, Empresa entity,
			String ocurrence, long limit, long offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String[]> getByPK(Connection connection, Empresa entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String[]> getList(Connection connection, Empresa entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTableName() {
		return IBacoTables.TABLE_EMPRESAS;
	}

	@Override
	public List<Empresa> list(Connection connection, Empresa entity) {
		String cQuery = getBaseQuery();

		List<Empresa> list = new ArrayList<Empresa>();
		try {
			EntityQueryManager<Empresa> manager = new EntityQueryManager<Empresa>();
			list = manager.asList(connection, cQuery,
					new QueryObjectTransformer<Empresa>() {
						@Override
						public Empresa transform(ResultSet input)
								throws Exception {
							Empresa empresa = new Empresa();
							empresa.setIdempresa(input
									.getBigDecimal("idempresa"));
							empresa.setEmpresa(input.getString("empresa"));
							empresa
									.setUsuarioact(input
											.getString("usuarioact"));
							empresa
									.setUsuarioalt(input
											.getString("usuarioalt"));
							empresa.setFechaact(input.getTimestamp("fechaact"));
							empresa.setFechaalt(input.getTimestamp("fechaalt"));
							return empresa;
						}
					});
		} catch (SQLException e) {
			LogUtils.logException(logger, "EmpresaManager :: list()", e);
		} catch (Exception ex) {
			LogUtils.logException(logger, "EmpresaManager :: list()", ex);
		}

		return list;
	}

	@Override
	public String update(Connection connection, Empresa entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
