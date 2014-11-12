package ar.com.syswarp.entitymanager;

import java.sql.Connection;
import java.util.List;

import ar.com.syswarp.entity.AbstractEntity;

interface IEntityManager<T extends AbstractEntity> {

	String getBaseQuery();

	String getTableName();

	boolean exists(Connection connection, T entity);

	String delete(Connection connection, T entity);

	String createOrUpdate(Connection connection, T entity);

	String create(Connection connection, T entity);

	String update(Connection connection, T entity);

	List<String[]> getList(Connection connection, T entity);

	List<String[]> getAll(Connection connection, T entity, long limit,
			long offset);

	List<String[]> getByOcurrence(Connection connection, T entity,
			String ocurrence, long limit, long offset);

	List<String[]> getByPK(Connection connection, T entity);

	List<T> list(Connection connection, T entity);

}
