package ar.com.syswarp.bc;

import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
/*
 * Clase para liquidacion de vendedores. Esto es propio de bacoclub.
 */
//import ar.com.syswarp.ejb.EvalVerificacionBean;
//import ar.com.syswarp.ejb.Evaluador;
import bsh.EvalError;
import bsh.Interpreter;

//import ar.com.syswarp.ejb.Evaluador;

public class BCFormulas {
	static Logger log = Logger.getLogger(BCFormulas.class);

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

	public BCFormulas() {
		super();
		try {
			props = new Properties();
			props.load(BCFormulas.class
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

	public String setFormula(String formulaCruda, String valorParametro) {
		/*
		 * Objetivo: recibir una formula en crudo y hacer los replaces
		 * necesarios Puntualmente para el caso de vtv se van a reemplazar los
		 * numeros de vehiculos Retorna : la formula en texto con todos sus
		 * metodos cambiados por los valores resultantes Despues de ejecutar
		 * este metodo hay que evaluar la formula con beanshell
		 */
		String outString = "";
		ResultSet rsToken = null;
		String cQuery = "Select metodo from tokenizer";
		BigDecimal vParametro = new BigDecimal(valorParametro);
		try {
			Statement statement = dbconn.createStatement();
			rsToken = statement.executeQuery(cQuery);
			String salida = formulaCruda;
			while (rsToken.next()) {
				String metodo = rsToken.getString(1).trim();
				int indice = formulaCruda.indexOf(metodo);
				if (indice >= 0) {
					int indiceFinMetodo = indice + metodo.length();
					if (indiceFinMetodo >= formulaCruda.length())
						formulaCruda += " ";

					if (!isCharValido(formulaCruda.charAt(indice
							+ metodo.length()))
							|| (indice > 0 && !isCharValido(formulaCruda
									.charAt(indice - 1)))) {
						log.warn("setFormula: Metodo " + metodo
								+ " ilegal o condicion mal formada");

					} else {
						formulaCruda = formulaCruda.trim();
						String resultado = "";
						Pattern p = Pattern.compile(metodo);
						Matcher m = p.matcher(salida);
						try {
							BCFormulas verificacion = new BCFormulas();
							Method met = verificacion.getClass().getMethod(
									metodo, new Class[] { BigDecimal.class });
							resultado = (String) met.invoke(verificacion,
									new Object[] { vParametro });
							outString = m.replaceAll(resultado);
							salida = outString;
						} catch (NoSuchMethodException e) {
							log.error("Error en el metodo. " + e);
						} catch (ClassCastException e) {
							log.error("Errror de casteo. " + e);
						}
					}
				}
			}
		} catch (Exception ex) {
			log
					.error("setFormula(String formulaCruda, String parametro, String valorParametro) "
							+ ex);
		}

		return outString;
	}

	public boolean isCharValido(char inChar) {
		boolean existeChar = false;
		try {

			char[] caracter = new char[] { ' ', '&', '|', '=', '<', '>', '(',
					')', '!', '?', ':' };
			for (int i = 0; i < caracter.length; i++)
				if (caracter[i] == inChar) {
					existeChar = true;
					break;
				}
		} catch (Exception e) {
			log.error("isCharValido(char inChar): " + e);
		}
		return existeChar;
	}

	public String evaluarCondicion(String condicion) {
		String salida = "nada";
		try {

			Interpreter i = new Interpreter();
			i.eval(condicion);
			salida = i.get("bar").toString();
		} catch (EvalError ex) {
			log
					.error("salida por excepcion  evaluarCondicion(String condicion: "
							+ condicion + "):" + ex);
		}
		return salida;
	}

	public BigDecimal geCurCanon(BigDecimal idvehiculo,
			BigDecimal idtipovehiculo) {
		BigDecimal salida = new BigDecimal("-9999");
		try {
			ResultSet rsToken = null;

			String cQuery = "select idcanon, formula from canones where idtipovehiculo = "
					+ idtipovehiculo.toString() + " order by precedencia desc "; // CEP:

			Statement statement = dbconn.createStatement();
			rsToken = statement.executeQuery(cQuery);
			while (rsToken != null && rsToken.next()) {
				String formula = rsToken.getString("formula");
				String resultadoFormula = evaluarCondicion(setFormula(formula,
						idvehiculo.toString()));
				if (resultadoFormula.equalsIgnoreCase("TRUE")) {
					salida = rsToken.getBigDecimal("idcanon");
					break;
				}
			}
		} catch (Exception ex) {
			log
					.error("geCurCanon(BigDecimal idvehiculo, BigDecimal idtipovehiculo) "
							+ ex);
		}
		return salida;
	}

	
	// seteo de formula para recursos humanos RRHH
	
	public String setFormulaRRHH(String formulaCruda, String valorParametro) {
		/*
		 * Objetivo: recibir una formula en crudo y hacer los replaces
		 * necesarios Puntualmente para el caso de vtv se van a reemplazar los
		 * numeros de vehiculos Retorna : la formula en texto con todos sus
		 * metodos cambiados por los valores resultantes Despues de ejecutar
		 * este metodo hay que evaluar la formula con beanshell
		 */
		String outString = "";
		ResultSet rsToken = null;
		String cQuery = "Select tokenkizer from rrrhhtokenkizer";
		BigDecimal vParametro = new BigDecimal(valorParametro);
		try {
			Statement statement = dbconn.createStatement();
			rsToken = statement.executeQuery(cQuery);
			String salida = formulaCruda;
			while (rsToken.next()) {
				String metodo = rsToken.getString(1).trim();
				int indice = formulaCruda.indexOf(metodo);
				if (indice >= 0) {
					int indiceFinMetodo = indice + metodo.length();
					if (indiceFinMetodo >= formulaCruda.length())
						formulaCruda += " ";

					if (!isCharValido(formulaCruda.charAt(indice
							+ metodo.length()))
							|| (indice > 0 && !isCharValido(formulaCruda
									.charAt(indice - 1)))) {
						log.warn("setFormula: Metodo " + metodo
								+ " ilegal o condicion mal formada");

					} else {
						formulaCruda = formulaCruda.trim();
						String resultado = "";
						Pattern p = Pattern.compile(metodo);
						Matcher m = p.matcher(salida);
						try {
							BCFormulas verificacion = new BCFormulas();
							Method met = verificacion.getClass().getMethod(
									metodo, new Class[] { BigDecimal.class });
							resultado = (String) met.invoke(verificacion,
									new Object[] { vParametro });
							outString = m.replaceAll(resultado);
							salida = outString;
						} catch (NoSuchMethodException e) {
							log.error("Error en el metodo. " + e);
						} catch (ClassCastException e) {
							log.error("Errror de casteo. " + e);
						}
					}
				}
			}
		} catch (Exception ex) {
			log
					.error("setFormula(String formulaCruda, String parametro, String valorParametro) "
							+ ex);
		}

		return outString;
	}

	
}
