package ar.com.syswarp.ejb;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;


import java.sql.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.lang.reflect.Method;
import java.math.*;
//beanshell
import bsh.*;
import javax.sql.DataSource;

public class Evaluador {
	static Logger log = Logger.getLogger(Evaluador.class);
	private Connection conexion;
	private Properties props;
	private String url;
	private String clase;
	private String usuario;
	private String clave;
	private Connection dbconn;	
	private String pool;
	
	private JdbcConexiones cnx = new JdbcConexiones();
	public Evaluador() {
		super();
	}
	
	
	// set de metodos para la regla de negocio.
	// NOTA: RECORDAR poner los metodos en la tabla tokenizer para su posterios evaluacion.
	
	public String getClientesTipoIVA(BigDecimal idcliente) {
		// recuperar el tipo de IVA de un cliente
		String cQuery = "select idtipoiva FROM clientesclientes where idcliente =  "
				+ idcliente.toString();
		String salida = "-1";
		try {
			this.dbconn = cnx.getConexion(); Statement statement = dbconn.createStatement();
			ResultSet rsQuery = statement.executeQuery(cQuery);
			if (rsQuery != null) {
				while (rsQuery.next()) {
					salida = rsQuery.getString("idtipoiva");
				}
			}
		} catch (Exception ex) {
			log.error("getClientesTipoIVA(BigDecimal idcliente) " + ex);
		}
		cnx.closeConexion(this.dbconn);
		return salida;
	}
	

	public String getClientesTipo(BigDecimal idcliente) {
		// recuperar el tipo de cliente
		String cQuery = "select idtipoclie FROM clientesclientes where idcliente =  "
				+ idcliente.toString();
		String salida = "-1";
		try {
			this.dbconn = cnx.getConexion(); Statement statement = dbconn.createStatement();
			ResultSet rsQuery = statement.executeQuery(cQuery);
			if (rsQuery != null) {
				while (rsQuery.next()) {
					salida = rsQuery.getString("idtipoclie");
				}
			}
		} catch (Exception ex) {
			log.error("getClientesTipo(BigDecimal idcliente) " + ex);
		}
		cnx.closeConexion(this.dbconn);
		return salida;
	}
	
	
	public String getClienteEstado(BigDecimal idcliente) {
		// recuperar el estado del socio
		String cQuery = "select idestado FROM clientesclientes where idcliente =  "
				+ idcliente.toString();
		String salida = "-1";
		try {
			this.dbconn = cnx.getConexion(); Statement statement = dbconn.createStatement();
			ResultSet rsQuery = statement.executeQuery(cQuery);
			if (rsQuery != null) {
				while (rsQuery.next()) {
					salida = rsQuery.getString("idestado");
				}
			}
		} catch (Exception ex) {
			log.error("getClienteEstado(BigDecimal idcliente) " + ex);
		}
		cnx.closeConexion(this.dbconn);
		return salida;
	}
	
	
}
