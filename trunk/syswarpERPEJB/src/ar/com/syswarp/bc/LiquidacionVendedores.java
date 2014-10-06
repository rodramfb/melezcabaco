package ar.com.syswarp.bc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

/*
 * Clase para liquidacion de vendedores. Esto es propio de bacoclub.
 */
//import ar.com.syswarp.ejb.EvalVerificacionBean;
//import ar.com.syswarp.ejb.Evaluador;
public class LiquidacionVendedores {
	static Logger log = Logger.getLogger(LiquidacionVendedores.class);

	private Properties props;

	// -- conexion al sistema core
	private Connection conexion;

	private String url;

	private String clase;

	private String usuario;

	private String clave;

	private Connection dbconn;

	// -- conexion al sistema antiguo
	private Connection sconexion;

	private String surl;

	private String sclase;

	private String susuario;

	private String sclave;

	private Connection sdbconn;

	public LiquidacionVendedores() {
		super();
		try {
			props = new Properties();
			props.load(LiquidacionVendedores.class
					.getResourceAsStream("system.properties"));

			// conexion al sistema core
			url = props.getProperty("conn.url").trim();
			clase = props.getProperty("conn.clase").trim();
			usuario = props.getProperty("conn.usuario").trim();
			clave = props.getProperty("conn.clave").trim();
			Class.forName(clase);
			conexion = DriverManager.getConnection(url, usuario, clave);
			this.dbconn = conexion;

			// conexion al sistema historico
			surl = props.getProperty("sconn.url").trim();
			sclase = props.getProperty("sconn.clase").trim();
			susuario = props.getProperty("sconn.usuario").trim();
			sclave = props.getProperty("sconn.clave").trim();
			Class.forName(sclase);
			sconexion = DriverManager.getConnection(surl, susuario, sclave);

		} catch (java.lang.ClassNotFoundException cnfException) {
			log.error("Error driver : " + cnfException);
		} catch (SQLException sqlException) {
			log.error("Error SQL: " + sqlException);
		} catch (IOException IOException) {
			log.error("Error SQL: " + IOException);
		} catch (Exception ex) {
			log.error("Salida por exception: " + ex);
		}

	}

	// set de metodos para la regla de negocio.
	// uno de prueba

}
