package ar.com.syswarp.entitymanager;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

import ar.com.syswarp.entity.AbstractEntity;

public abstract class AbstractEntityManager<T extends AbstractEntity> {

	protected Logger logger;

	public AbstractEntityManager(Logger logger) {
		super();
		this.logger = logger;
	}

	protected abstract String getBaseQuery();

	protected abstract String getTableName();

	public abstract boolean exists(Connection connection, T entity);
	
	public abstract String delete(Connection connection, T entity);

	public abstract String createOrUpdate(Connection connection, T entity);

	public abstract String create(Connection connection, T entity);

	public abstract String update(Connection connection, T entity);

	public abstract List<String[]> getList(Connection connection, T entity);

	public abstract List<String[]> getAll(Connection connection, T entity,
			long limit, long offset);

	public abstract List<String[]> getByOcurrence(Connection connection,
			T entity, String ocurrence, long limit, long offset);

	public abstract List<String[]> getByPK(Connection connection, T entity);

}
