package ar.com.syswarp.entitymanager.utils;

import java.sql.ResultSet;

public interface QueryObjectTransformer<T> {

	T transform(ResultSet input) throws Exception;

}
