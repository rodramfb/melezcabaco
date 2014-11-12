package ar.com.syswarp.ejb;

import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.ejb.CreateException;
import javax.ejb.Stateless;

import java.io.*;
import java.sql.*;
import java.util.*;
import org.apache.log4j.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.math.*;

/**
 * XDoclet-based session bean. The class must be declared public according to
 * the EJB specification.
 * 
 * To generate the EJB related files to this EJB: - Add Standard EJB module to
 * XDoclet project properties - Customize XDoclet configuration for your
 * appserver - Run XDoclet
 * 
 * Below are the xdoclet-related tags needed for this EJB.
 * 
 * @ejb.bean name="Produccion" display-name="Name for Produccion"
 *           description="Description for Produccion" jndi-name="ejb/Produccion"
 *           type="Stateless" view-type="remote"
 */
@Stateless
public class ProduccionBean implements Produccion {

	/** The session context */
	private SessionContext context;

	GeneralBean gb = new GeneralBean();

	/* conexion a la base de datos */

	private Connection dbconn;

	static Logger log = Logger.getLogger(ProduccionBean.class);

	private Connection conexion;

	private Properties props;

	private String url;

	private String clase;

	private String usuario;

	private String clave;

	public ProduccionBean() {
		super();
		try {
			props = new Properties();
			props.load(ProduccionBean.class
					.getResourceAsStream("system.properties"));

			url = props.getProperty("conn.url").trim();
			clase = props.getProperty("conn.clase").trim();
			usuario = props.getProperty("conn.usuario").trim();
			clave = props.getProperty("conn.clave").trim();

			Class.forName(clase);
			conexion = DriverManager.getConnection(url, usuario, clave);
			this.dbconn = conexion;
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

	public ProduccionBean(Connection dbconn) {
		this.dbconn = dbconn;
	}

	/**
	 * Set the associated session context. The container calls this method after
	 * the instance creation.
	 * 
	 * The enterprise bean instance should store the reference to the context
	 * object in an instance variable.
	 * 
	 * This method is called with no transaction context.
	 * 
	 * @throws EJBException
	 *             Thrown if method fails due to system-level error.
	 */
	public void setSessionContext(SessionContext newContext)
			throws EJBException {
		context = newContext;
	}

	public void ejbRemove() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	public void ejbCreate() throws CreateException {
		// TODO Add ejbCreate method implementation
	}

	/**
	 * Reglas de negocio Produccion:
	 * 
	 * @ejb.interface-method view-type = "remote"
	 * 
	 * @throws EJBException
	 *             Thrown if method fails due to system-level error. Entidades
	 *             que intervienen: Produccion
	 * 
	 */

	public long getTotalEntidad(String entidad, BigDecimal idempresa)
			throws EJBException {

		/**
		 * Entidad: Total Registros de Una Entidad en Particular
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : recuperar total de registros.
		 */
		long total = 0l;
		ResultSet rsSalida = null;
		String cQuery = "SELECT count(1)AS total FROM " + entidad
				+ " WHERE idempresa=" + idempresa.toString();
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				total = rsSalida.getLong("total");
			} else {
				log.warn("getTotalEntidad()- Error al recuperar total: "
						+ entidad);
			}
		} catch (SQLException sqlException) {
			log.error("getTotalEntidad()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getTotalEntidad()- Salida por exception: " + ex);
		}
		return total;
	}

	public long getTotalEntidadOcu(String entidad, String[] campos,
			String ocurrencia, BigDecimal idempresa) throws EJBException {

		/**
		 * Entidad: ??????
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer cantidad por ocurrencia-criterio.
		 */
		long total = 0l;
		ResultSet rsSalida = null;
		String cQuery = "SELECT count(1)AS total FROM " + entidad + " WHERE ";
		String like = "";
		int len = campos.length;

		try {
			for (int i = 0; i < len; i++) {
				like += "UPPER(" + campos[i] + "::VARCHAR) LIKE '%"
						+ ocurrencia.toUpperCase() + "%' ";
				if (i + 1 < len)
					like += " OR ";
			}
			cQuery += "(" + like + ") AND idempresa = " + idempresa.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				total = rsSalida.getLong("total");
			} else {
				log.warn("getTotalEntidadOcu()- Error al recuperar total: "
						+ entidad);
			}
		} catch (SQLException sqlException) {
			log.error("getTotalEntidadOcu()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getTotalEntidadOcu()- Salida por exception: " + ex);
		}
		return total;
	}

	public long getTotalEntidadRelacion(String entidad, String[] campos,
			String[] ocurrencia, BigDecimal idempresa) throws EJBException {

		/**
		 * Entidad: ???????
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer cantidad de registros desde una tabla de
		 *             relacion para un id en particualar.
		 */
		long total = 0l;
		ResultSet rsSalida = null;
		String cQuery = "SELECT count(1)AS total FROM " + entidad + " WHERE ";
		String filtro = "";
		int len = campos.length;

		try {
			for (int i = 0; i < len; i++) {
				filtro += campos[i] + " = " + ocurrencia[i] + " ";
				if (i + 1 < len)
					filtro += " AND ";
			}
			cQuery += filtro + " AND idempresa=" + idempresa.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				total = rsSalida.getLong("total");
			} else {
				log
						.warn("getTotalEntidadRelacion()- Error al recuperar total: "
								+ entidad);
			}
		} catch (SQLException sqlException) {
			log.error("getTotalEntidadRelacion()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getTotalEntidadRelacion()- Salida por exception: " + ex);
		}
		return total;
	}

	public long getTotalEntidadFiltro(String entidad, String filtro,
			BigDecimal idempresa) throws EJBException {

		/**
		 * Entidad: Total Registros de Una Entidad en Particular
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : recuperar total de registros.
		 */
		long total = 0l;
		ResultSet rsSalida = null;
		String cQuery = "SELECT count(1)AS total FROM " + entidad + " "
				+ filtro + " AND idempresa = " + idempresa.toString();
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				total = rsSalida.getLong("total");
			} else {
				log.warn("getTotalEntidadFiltro()- Error al recuperar total: "
						+ entidad);
			}
		} catch (SQLException sqlException) {
			log.error("getTotalEntidadFiltro()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getTotalEntidadFiltro()- Salida por exception: " + ex);
		}
		return total;
	}

	public long getTotalEntidadRelacionOcu(String entidad,
			String[] camposFiltro, String[] valorFiltro, String[] camposOcu,
			String[] valorOcu, BigDecimal idempresa) throws EJBException {

		/**
		 * Entidad: ???????
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer cantidad de registros desde una tabla de
		 *             relacion para un id en particualar.
		 */
		long total = 0l;
		ResultSet rsSalida = null;
		String cQuery = "SELECT count(1)AS total FROM " + entidad + " WHERE ";
		String filtro = "";
		int len = camposFiltro.length;

		try {
			for (int i = 0; i < len; i++) {
				filtro += camposFiltro[i] + " = " + valorFiltro[i] + " ";
				filtro += " AND ";
			}

			len = camposOcu.length;
			filtro += "(";
			for (int i = 0; i < len; i++) {
				filtro += "UPPER(" + camposOcu[i] + "::VARCHAR) LIKE '%"
						+ valorOcu[i].toUpperCase() + "%' ";
				if (i + 1 < len)
					filtro += " OR ";
			}

			filtro += ") ";
			cQuery += filtro + " AND idempresa=" + idempresa.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);

			if (rsSalida.next()) {
				total = rsSalida.getLong("total");
			} else {
				log
						.warn("getTotalEntidadRelacionOcu()- Error al recuperar total: "
								+ entidad);
			}
		} catch (SQLException sqlException) {
			log.error("getTotalEntidadRelacionOcu()- Error SQL: "
					+ sqlException);
		} catch (Exception ex) {
			log.error("getTotalEntidadRelacionOcu()- Salida por exception: "
					+ ex);
		}
		return total;
	}

	// private List getLista(String query) throws EJBException {
	private List getLista(String query) {
		List vecSalida = new ArrayList();
		try {
			Statement statement = dbconn.createStatement();
			ResultSet rsSalida = statement.executeQuery(query);
			ResultSetMetaData md = rsSalida.getMetaData();
			// log.info("A-METODO LLAMADA:" + getCallingMethodName());
			while (rsSalida.next()) {
				int totCampos = md.getColumnCount() - 1;
				String[] sSalida = new String[totCampos + 1];
				int i = 0;
				while (i <= totCampos) {
					sSalida[i] = rsSalida.getString(++i);
				}
				vecSalida.add(sSalida);
			}
			if (rsSalida != null) {
				rsSalida.close();
				rsSalida = null;
				md = null;
			}
		} catch (SQLException sqlException) {
			log
					.error("(SQLE) getLista(String query) - INVOCADO POR "
							+ GeneralBean.getCallingMethodName() + " : "
							+ sqlException);
			// throw new EJBException(
			// "getLista() - SQLException:  -- >: throw new EJBException ");
		} catch (Exception ex) {
			log.error("(EX) getLista(String query) - INVOCADO POR "
					+ GeneralBean.getCallingMethodName() + " : " + ex);

			// throw new EJBException(
			// "getLista() - Exception:  -- >: throw new EJBException ");
		}
		return vecSalida;
	}

	// PRODUCCION CONCEPTO
	// para todo (ordena por el segundo campo por defecto)
	public List getProduccionconceptosAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT idconcepto,concepto,formula,margen_error,costo,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM PRODUCCIONCONCEPTOS WHERE idempresa="
				+ idempresa.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getProduccionconceptosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT idconcepto,concepto,formula,margen_error,costo,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM PRODUCCIONCONCEPTOS "
				+ " where (idconcepto::VARCHAR LIKE '%" + ocurrencia + "%' OR "
				+ " UPPER(CONCEPTO) LIKE '%" + ocurrencia.toUpperCase().trim()
				+ "%') AND idempresa=" + idempresa.toString()
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getProduccionconceptosPK(BigDecimal idconcepto,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT idconcepto,concepto,formula,margen_error,costo,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM PRODUCCIONCONCEPTOS WHERE idconcepto="
				+ idconcepto.toString() + " AND idempresa="
				+ idempresa.toString();
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String ProduccionconceptosDelete(BigDecimal idconcepto,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM PRODUCCIONCONCEPTOS WHERE idconcepto="
				+ idconcepto.toString() + " AND idempresa="
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM PRODUCCIONCONCEPTOS WHERE idconcepto="
						+ idconcepto.toString() + " AND idempresa="
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : ProduccionconceptosDelete( BigDecimal idconcepto, BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: ProduccionconceptosDelete( BigDecimal idconcepto, BigDecimal idempresa )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String ProduccionconceptosCreate(String concepto, String formula,
			Double margen_error, Double costo, String usuarioalt,
			BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (concepto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: concepto ";
		if (formula == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: formula ";
		if (margen_error == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: margen_error ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (concepto.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Concepto ";
		if (formula.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Formula ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO PRODUCCIONCONCEPTOS(concepto, formula, margen_error,costo, usuarioalt, idempresa ) VALUES (?,?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, concepto);
				insert.setString(2, formula);
				insert.setDouble(3, margen_error.doubleValue());
				insert.setDouble(4, costo.doubleValue());
				insert.setString(5, usuarioalt);
				insert.setBigDecimal(6, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String ProduccionconceptosCreate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String ProduccionconceptosCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String ProduccionconceptosCreateOrUpdate(BigDecimal idconcepto,
			String concepto, String formula, Double margen_error, Double costo,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idconcepto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idconcepto ";
		if (concepto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: concepto ";
		if (formula == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: formula ";
		if (margen_error == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: margen_error ";

		// 2. sin nada desde la pagina
		if (concepto.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Concepto ";
		if (formula.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Formula ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM Produccionconceptos WHERE idconcepto = "
					+ idconcepto.toString()
					+ " AND idempresa="
					+ idempresa.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE PRODUCCIONCONCEPTOS SET concepto=?, formula=?, margen_error=?, usuarioact=?, fechaact=? WHERE idconcepto=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, concepto);
					insert.setString(2, formula);
					insert.setDouble(3, margen_error.doubleValue());
					insert.setString(4, usuarioact);
					insert.setTimestamp(5, fechaact);
					insert.setBigDecimal(6, idconcepto);
					insert.setBigDecimal(7, idempresa);
				} else {
					String ins = "INSERT INTO PRODUCCIONCONCEPTOS(concepto, formula, margen_error, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, concepto);
					insert.setString(2, formula);
					insert.setDouble(3, margen_error.doubleValue());
					insert.setString(4, usuarioalt);
					insert.setBigDecimal(5, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String ProduccionconceptosCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String ProduccionconceptosCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String ProduccionconceptosUpdate(BigDecimal idconcepto,
			String concepto, String formula, Double margen_error, Double costo,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idconcepto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idconcepto ";
		if (concepto.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Concepto ";
		if (formula.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Formula ";
		// if(margen_error == null )
		// salida = "Error: No se puede dejar sin datos (nulo) el campo:
		// margen_error ";

		// 2. sin nada desde la pagina
		if (concepto.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: concepto ";
		if (formula.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: formula ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM Produccionconceptos WHERE idconcepto = "
					+ idconcepto.toString()
					+ " AND idempresa="
					+ idempresa.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE PRODUCCIONCONCEPTOS SET concepto=?, formula=?, margen_error=?,costo=?, usuarioact=?, fechaact=? WHERE idconcepto=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, concepto);
					insert.setString(2, formula);
					insert.setDouble(3, margen_error.doubleValue());
					insert.setDouble(4, costo.doubleValue());
					insert.setString(5, usuarioact);
					insert.setTimestamp(6, fechaact);
					insert.setBigDecimal(7, idconcepto);
					insert.setBigDecimal(8, idempresa);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error SQL public String ProduccionconceptosUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String ProduccionconceptosUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * Metodos para la entidad: produccionEsquemas_Cabe -
	 * produccionEsquemas_Deta Copyrigth(r) sysWarp S.R.L. Fecha de creacion:
	 * Tue Feb 14 14:32:00 GMT-03:00 2007
	 */

	// Crear esquema: cabecera y detalle
	public String produccionEsquemasCreate(String esquema,
			BigDecimal codigo_md, String observaciones,
			Hashtable htDetalleEsquema, String usuarioalt, BigDecimal idempresa)
			throws EJBException, SQLException {
		String salida = "OK";
		BigDecimal idesquema = null;
		BigDecimal renglon = null;
		String tipo = null;
		String codigo_st = null;
		// String descrip_st = null;
		BigDecimal cantidad = null;
		String entsal = null;
		BigDecimal codigo_dt = null;
		BigDecimal margen_error = null;
		String imprime = null;
		String edita = null;
		String formula = null;
		String reutiliza = null;
		Enumeration en;

		try {
			dbconn.setAutoCommit(false);

			salida = produccionEsquemas_CabeCreate(esquema, codigo_md,
					observaciones, usuarioalt, idempresa);

			if (salida.equalsIgnoreCase("OK")) {

				idesquema = GeneralBean.getValorSequencia(
						"seq_produccionesquemas_cabe", dbconn);

				if (idesquema != null) {

					en = htDetalleEsquema.keys();
					while (en.hasMoreElements()) {

						String key = en.nextElement().toString();
						String[] datos = (String[]) htDetalleEsquema.get(key);

						renglon = new BigDecimal(key);
						tipo = datos[2];
						codigo_st = datos[3];
						// descrip_st = datos[4];
						cantidad = new BigDecimal(datos[5]);
						entsal = datos[6];
						codigo_dt = new BigDecimal(datos[7]);
						margen_error = new BigDecimal(datos[8]);
						imprime = datos[9];
						edita = datos[10];
						formula = null;
						reutiliza = datos[12];

						salida = produccionEsquemas_DetaCreate(idesquema,
								renglon, tipo, codigo_st, cantidad, entsal,
								codigo_dt, margen_error, imprime, edita,
								formula, reutiliza, usuarioalt, idempresa);

						if (!salida.equalsIgnoreCase("OK"))
							break;

					}
				} else {
					salida = "Error al recuperar secuencia asociada al nuevo esquema.";
				}
			}

		} catch (Exception e) {
			log.error("produccionEsquemasCreate: " + e);
			salida = "Error al crear esquema.";
		}
		if (!salida.equalsIgnoreCase("OK"))
			dbconn.rollback();
		else
			salida = idesquema.toString();

		dbconn.setAutoCommit(true);
		return salida;
	}

	// Actualizacion de esquema: actualiza cabecera, detalle y borra detalles.
	public String produccionEsquemasUpdate(BigDecimal idesquema,
			String esquema, BigDecimal codigo_md, String observaciones,
			Hashtable htDetalleEsquema, Hashtable htDeleteDetalle,
			String usuarioalt, BigDecimal idempresa) throws EJBException,
			SQLException {
		String salida = "OK";

		BigDecimal renglon = null;
		String tipo = null;
		String codigo_st = null;
		// String descrip_st = null;
		BigDecimal cantidad = null;
		String entsal = null;
		BigDecimal codigo_dt = null;
		BigDecimal margen_error = null;
		String imprime = null;
		String edita = null;
		String formula = null;
		String reutiliza = null;
		Enumeration en;

		try {
			dbconn.setAutoCommit(false);

			BigDecimal totalMovimientos = esquemaHaveMovProd(idesquema,
					idempresa);

			if (totalMovimientos.longValue() == 0) {

				salida = produccionEsquemas_CabeUpdate(idesquema, esquema,
						codigo_md, observaciones, usuarioalt, idempresa);

				if (salida.equalsIgnoreCase("OK")) {

					if (htDeleteDetalle != null) {

						en = htDeleteDetalle.keys();
						while (en.hasMoreElements()) {

							String key = en.nextElement().toString();
							String[] datos = (String[]) htDetalleEsquema
									.get(key);

							renglon = new BigDecimal(key);
							salida = produccionEsquemas_DetaDelete(idesquema,
									renglon, idempresa);

							if (!salida.equalsIgnoreCase("OK"))
								break;

						}
					}

					if (salida.equalsIgnoreCase("OK")) {
						en = htDetalleEsquema.keys();

						while (en.hasMoreElements()) {

							String key = en.nextElement().toString();
							String[] datos = (String[]) htDetalleEsquema
									.get(key);
							renglon = new BigDecimal(key);
							tipo = datos[2];
							codigo_st = datos[3];
							// descrip_st = datos[4];
							cantidad = new BigDecimal(datos[5]);
							entsal = datos[6];
							codigo_dt = new BigDecimal(datos[7]);
							margen_error = new BigDecimal(datos[8]);
							imprime = datos[9];
							edita = datos[10];
							formula = null;
							reutiliza = datos[12];

							salida = produccionEsquemas_DetaCreateOrUpdate(
									idesquema, renglon, tipo, codigo_st,
									cantidad, entsal, codigo_dt, margen_error,
									imprime, edita, formula, reutiliza,
									usuarioalt, idempresa);

							if (!salida.equalsIgnoreCase("OK"))
								break;

						}

					}

				}
			} else if (totalMovimientos.longValue() > 0) {
				salida = "El esquema no puede ser modificado, posee movimientos de produccion asociados.";
			} else if (totalMovimientos.longValue() < 0) {
				salida = "No se pudo contabilizar si el esquema posee movimientos de produccion.";
			}

		} catch (Exception e) {
			log.error("produccionEsquemasUpdate: " + e);
			salida = "Error al actualizar esquema.";
		}

		if (!salida.equalsIgnoreCase("OK"))
			dbconn.rollback();
		else
			salida = "Esquema actualizado correctamente.";

		dbconn.setAutoCommit(true);
		return salida;
	}

	/**
	 * Metodos para la entidad: produccionEsquemas_Cabe Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Tue Feb 13 09:18:39 GMT-03:00 2007
	 */

	public List getProduccionEsquemas_CabeAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {

		String cQuery = ""
				+ "SELECT ec.idesquema, ec.esquema, ec.codigo_md, sm.descrip_md,ec.observaciones,"
				+ "       ec.usuarioalt,ec.usuarioact,ec.fechaalt,ec.fechaact "
				+ "  FROM produccionesquemas_cabe ec "
				+ "       INNER JOIN stockmedidas sm ON sm.codigo_md = ec.codigo_md AND sm.idempresa=ec.idempresa "
				+ "WHERE ec.idempresa=" + idempresa.toString()
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getProduccionEsquemas_CabeOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {

		String cQuery = ""
				+ "SELECT ec.idesquema, ec.esquema, ec.codigo_md, sm.descrip_md,ec.observaciones,"
				+ "       ec.usuarioalt,ec.usuarioact,ec.fechaalt,ec.fechaact "
				+ "  FROM produccionesquemas_cabe ec "
				+ "       INNER JOIN stockmedidas sm ON sm.codigo_md = ec.codigo_md AND sm.idempresa = ec.idempresa "
				+ " where ec.idempresa = " + idempresa.toString()
				+ " and (ec.idesquema::VARCHAR LIKE '%" + ocurrencia + "%' OR "
				+ " UPPER(ec.esquema) LIKE '%" + ocurrencia.toUpperCase()
				+ "%') " + " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset
				+ ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getProduccionEsquemas_CabePK(BigDecimal idesquema,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT ec.idesquema, ec.esquema, ec.codigo_md, sm.descrip_md,ec.observaciones,"
				+ "       ec.usuarioalt,ec.usuarioact,ec.fechaalt,ec.fechaact "
				+ "  FROM produccionesquemas_cabe ec "
				+ "       INNER JOIN stockmedidas sm "
				+ "          ON sm.idempresa = ec.idempresa AND sm.codigo_md = ec.codigo_md "
				+ " WHERE  ec.idesquema = " + idesquema.toString()
				+ "   AND ec.idempresa=" + idempresa.toString();
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public BigDecimal esquemaHaveMovProd(BigDecimal idesquema,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "";
		BigDecimal totalMov = new BigDecimal(-1);

		try {
			Statement statement = dbconn.createStatement();
			cQuery = "SELECT count(1) FROM produccionmovprodu WHERE idesquema ="
					+ idesquema.toString()
					+ " AND idempresa = "
					+ idempresa.toString();
			rsSalida = statement.executeQuery(cQuery);

			if (rsSalida != null && rsSalida.next()) {

				totalMov = rsSalida.getBigDecimal(1);

			} else
				log
						.error("esquemaHaveMovProd: Imposible determinar movimientos de produccion para esquema: "
								+ idesquema.toString());

		} catch (SQLException sqlException) {
			log
					.error("Error SQL en el metodo : esquemaHaveMovProd( BigDecimal idesquema , BigDecimal idempresa) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: esquemaHaveMovProd( BigDecimal idesquema , BigDecimal idempresa)  "
							+ ex);
		}
		return totalMov;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String produccionEsquemas_CabeDelete(BigDecimal idesquema,
			BigDecimal idempresa) throws EJBException, SQLException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM produccionesquemas_cabe WHERE idesquema="
				+ idesquema.toString()
				+ " AND idempresa = "
				+ idempresa.toString();
		String salida = "OK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {

				cQuery = "SELECT count(1) FROM produccionmovprodu WHERE idesquema ="
						+ idesquema.toString()
						+ " AND idempresa = "
						+ idempresa.toString();
				rsSalida = statement.executeQuery(cQuery);

				if (rsSalida != null && rsSalida.next()) {

					if (rsSalida.getLong(1) == 0) {
						cQuery = "SELECT count(1) FROM stockstock WHERE esquema_st="
								+ idesquema.toString()
								+ " AND idempresa = "
								+ idempresa.toString();
						rsSalida = statement.executeQuery(cQuery);

						if (rsSalida != null && rsSalida.next()) {

							if (rsSalida.getLong(1) == 0) {

								dbconn.setAutoCommit(false);

								cQuery = "DELETE FROM PRODUCCIONESQUEMAS_DETA WHERE idesquema="
										+ idesquema.toString()
										+ " AND idempresa = "
										+ idempresa.toString();
								statement.execute(cQuery);

								cQuery = "DELETE FROM PRODUCCIONESQUEMAS_CABE WHERE idesquema="
										+ idesquema.toString()
										+ " AND idempresa = "
										+ idempresa.toString();
								statement.execute(cQuery);

							} else
								salida = "Imposible eliminar el esquema, el mismo posee articulos asociados.";

						} else
							salida = "Error al contabilizar articulos asociados al esquema.";

					} else
						salida = "Imposible eliminar el esquema, el mismo posee ordenes de produccion asociadas.";

				} else
					salida = "Error al contabilizar ordenes de produccion asociadas al esquema.";

			} else
				salida = "Error: Registro inexistente";

		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : produccionEsquemas_CabeDelete( BigDecimal idesquema, BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: produccionEsquemas_CabeDelete( BigDecimal idesquema, BigDecimal idempresa )  "
							+ ex);
		}
		if (!salida.equalsIgnoreCase("OK"))
			dbconn.rollback();
		else
			salida = "Baja de esquema Correcta.";

		dbconn.setAutoCommit(true);
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String produccionEsquemas_CabeCreate(String esquema,
			BigDecimal codigo_md, String observaciones, String usuarioalt,
			BigDecimal idempresa) throws EJBException {
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (esquema == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: esquema ";
		if (codigo_md == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_md ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (esquema.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: esquema ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones

		try {
			if (salida.equalsIgnoreCase("OK")) {
				String ins = "INSERT INTO PRODUCCIONESQUEMAS_CABE(esquema, codigo_md,  observaciones, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, esquema);
				insert.setBigDecimal(2, codigo_md);
				insert.setString(3, observaciones);
				insert.setString(4, usuarioalt);
				insert.setBigDecimal(5, idempresa);
				int n = insert.executeUpdate();
				if (n != 1)
					salida = "Error al generar esquemas cabecera.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String produccionEsquemas_CabeCreate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String produccionEsquemas_CabeCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String produccionEsquemas_CabeCreateOrUpdate(BigDecimal idesquema,
			String esquema, BigDecimal codigo_md, String observaciones,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idesquema == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idesquema ";
		if (esquema == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: esquema ";
		if (codigo_md == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_md ";

		// 2. sin nada desde la pagina
		if (esquema.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: esquema ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM produccionEsquemas_Cabe WHERE idesquema = "
					+ idesquema.toString()
					+ " AND idempresa = "
					+ idempresa.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE PRODUCCIONESQUEMAS_CABE SET esquema=?, codigo_md=?, observaciones=?, usuarioact=?, fechaact=? WHERE idesquema=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, esquema);
					insert.setBigDecimal(2, codigo_md);
					insert.setString(3, observaciones);
					insert.setString(4, usuarioact);
					insert.setTimestamp(5, fechaact);
					insert.setBigDecimal(6, idesquema);
					insert.setBigDecimal(7, idempresa);
				} else {
					String ins = "INSERT INTO PRODUCCIONESQUEMAS_CABE(esquema, codigo_md, observaciones, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, esquema);
					insert.setBigDecimal(2, codigo_md);
					insert.setString(3, observaciones);
					insert.setString(4, usuarioalt);
					insert.setBigDecimal(5, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String produccionEsquemas_CabeCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String produccionEsquemas_CabeCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String produccionEsquemas_CabeUpdate(BigDecimal idesquema,
			String esquema, BigDecimal codigo_md, String observaciones,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idesquema == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idesquema ";
		if (esquema == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: esquema ";
		if (codigo_md == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_md ";

		// 2. sin nada desde la pagina
		if (esquema.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: esquema ";
		// fin validaciones

		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM produccionEsquemas_Cabe WHERE idesquema = "
					+ idesquema.toString()
					+ " AND idempresa="
					+ idempresa.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (salida.equalsIgnoreCase("OK")) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE PRODUCCIONESQUEMAS_CABE SET esquema=?, codigo_md=?, observaciones=?, usuarioact=?, fechaact=? WHERE idesquema=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, esquema);
					insert.setBigDecimal(2, codigo_md);
					insert.setString(3, observaciones);
					insert.setString(4, usuarioact);
					insert.setTimestamp(5, fechaact);
					insert.setBigDecimal(6, idesquema);
					insert.setBigDecimal(7, idempresa);
				}

				int i = insert.executeUpdate();
				if (i != 1)
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error SQL public String produccionEsquemas_CabeUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String produccionEsquemas_CabeUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// ESTO SE HIZO PARA TRAER EL LOV DE MEDIDAS
	public List getProduccionMedidasLOV(String ocurrencia, BigDecimal idempresa)
			throws EJBException {
		String cQuery = "SELECT * FROM stockmedidas WHERE (UPPER(descrip_md) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%') AND idempresa =  "
				+ idempresa.toString() + " ORDER BY codigo_md";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ESTO SE HIZO PARA TRAER EL LOV CONTADOR
	public List getProduccionContadorLOV(String ocurrencia, BigDecimal idempresa)
			throws EJBException {
		String cQuery = "SELECT * FROM globalcontadores WHERE (UPPER(contador) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%') AND idempresa = "
				+ idempresa.toString() + " ORDER BY idcontador";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: produccionEsquemas_Deta Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Tue Feb 13 09:19:28 GMT-03:00 2007
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getProduccionEsquemas_DetaAll(long limit, long offset,
			BigDecimal idesquema, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				/*
				 * Autor: EJV - Fecha: 20071218 - stockstock se reemplazo por
				 * varticulosesquemas, ya que solo mostraba articulos y se
				 * omitian esquemas, conceptos y recursos.
				 */

				+ "SELECT ed.idesquema,ed.renglon,ed.tipo,ed.codigo_st,st.descripcion,ed.cantidad::numeric(18) AS cantidad,"
				+ "       ed.entsal,ed.codigo_dt, ed.margen_error::numeric(18) AS margen_error,"
				+ "       ed.imprime,ed.edita,ed.formula,ed.reutiliza,ed.usuarioalt,ed.usuarioact,ed.fechaalt,ed.fechaact "
				+ "  FROM produccionesquemas_deta ed "
				+ "       INNER JOIN varticulosesquemas st ON ed.codigo_st = st.codigo AND ed.tipo = st.tipo AND ed.idempresa = st.idempresa"
				+ " WHERE ed.idesquema = " + idesquema + " AND ed.idempresa = "
				+ idempresa.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getProduccionEsquemas_DetaOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idesquema,renglon,tipo,codigo_st,cantidad,entsal,codigo_dt,margen_error,imprime,edita,formula,"
				+ "       usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM PRODUCCIONESQUEMAS_DETA "
				+ " WHERE (UPPER(RENGLON::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%') AND idempresa = "
				+ idempresa.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = new ArrayList();
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			ResultSetMetaData md = rsSalida.getMetaData();
			while (rsSalida.next()) {
				int totCampos = md.getColumnCount() - 1;
				String[] sSalida = new String[totCampos + 1];
				int i = 0;
				while (i <= totCampos) {
					sSalida[i] = rsSalida.getString(++i);
				}
				vecSalida.add(sSalida);
			}
		} catch (SQLException sqlException) {
			log
					.error("Error SQL en el metodo : getProduccionEsquemas_Deta(String ocurrencia, BigDecimal idempresa) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getProduccionEsquemas_Deta(String ocurrencia, BigDecimal idempresa)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getProduccionEsquemas_DetaPK(BigDecimal idesquema,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idesquema,renglon,tipo,codigo_st,cantidad,entsal,codigo_dt,margen_error,imprime,edita,formula,"
				+ "       usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM PRODUCCIONESQUEMAS_DETA " + " WHERE idesquema="
				+ idesquema.toString() + " AND idempresa="
				+ idempresa.toString();
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String produccionEsquemas_DetaDelete(BigDecimal idesquema,
			BigDecimal renglon, BigDecimal idempresa) throws EJBException {
		// ResultSet rsSalida = null;
		// String cQuery = "SELECT * FROM PRODUCCIONESQUEMAS_DETA WHERE
		// idesquema="
		// + idesquema.toString() + " AND renglon = " + renglon.toString();
		String salida = "OK";
		try {
			Statement statement = dbconn.createStatement();

			String cQuery = "DELETE FROM PRODUCCIONESQUEMAS_DETA WHERE idesquema="
					+ idesquema.toString()
					// 20111011 - EJV - Mantis 794 -->
					// + " AND renglon = "	+ renglon.toString()
					// <--
					+ " AND idempresa = "
					+ idempresa.toString();
			statement.execute(cQuery);

		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : produccionEsquemas_DetaDelete( BigDecimal idesquema , BigDecimal idempresa) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: produccionEsquemas_DetaDelete( BigDecimal idesquema )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String produccionEsquemas_DetaCreate(BigDecimal idesquema,
			BigDecimal renglon, String tipo, String codigo_st,
			BigDecimal cantidad, String entsal, BigDecimal codigo_dt,
			BigDecimal margen_error, String imprime, String edita,
			String formula, String reutiliza, String usuarioalt,
			BigDecimal idempresa) throws EJBException {
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idesquema == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idesquema ";
		if (renglon == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: renglon ";
		if (tipo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipo ";
		if (codigo_st == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_st ";
		if (cantidad == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cantidad ";
		if (entsal == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: entsal ";
		if (codigo_dt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_dt ";
		if (margen_error == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: margen_error ";
		if (imprime == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: imprime ";
		if (edita == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: edita ";
		if (reutiliza == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: reutiliza ";
		// if (formula == null)
		// salida = "Error: No se puede dejar sin datos (nulo) el campo: formula
		// ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (tipo.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipo ";
		if (codigo_st.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: codigo_st ";
		if (entsal.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: entsal ";
		if (imprime.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: imprime ";
		if (edita.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: edita ";
		// if (formula.equalsIgnoreCase(""))
		// salida = "Error: No se puede dejar vacio el campo: formula ";
		if (reutiliza.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: reutiliza ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones

		try {
			if (salida.equalsIgnoreCase("OK")) {
				String ins = "INSERT INTO PRODUCCIONESQUEMAS_DETA(idesquema, renglon, tipo, codigo_st, cantidad, entsal, codigo_dt, margen_error, imprime, edita, formula, reutiliza, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, idesquema);
				insert.setBigDecimal(2, renglon);
				insert.setString(3, tipo);
				insert.setString(4, codigo_st);
				insert.setBigDecimal(5, cantidad);
				insert.setString(6, entsal);
				insert.setBigDecimal(7, codigo_dt);
				insert.setBigDecimal(8, margen_error);
				insert.setString(9, imprime);
				insert.setString(10, edita);
				insert.setString(11, formula);
				insert.setString(12, reutiliza);
				insert.setString(13, usuarioalt);
				insert.setBigDecimal(14, idempresa);
				int n = insert.executeUpdate();
				if (n != 1)
					salida = "Error al generar detalle de esquemas.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String produccionEsquemas_DetaCreate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String produccionEsquemas_DetaCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String produccionEsquemas_DetaCreateOrUpdate(BigDecimal idesquema,
			BigDecimal renglon, String tipo, String codigo_st,
			BigDecimal cantidad, String entsal, BigDecimal codigo_dt,
			BigDecimal margen_error, String imprime, String edita,
			String formula, String reutiliza, String usuarioact,
			BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idesquema == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idesquema ";
		if (renglon == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: renglon ";
		if (tipo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipo ";
		if (codigo_st == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_st ";
		if (cantidad == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cantidad ";
		if (entsal == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: entsal ";
		if (codigo_dt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_dt ";
		if (margen_error == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: margen_error ";
		if (imprime == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: imprime ";
		if (edita == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: edita ";
		if (reutiliza == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: reutiliza ";
		// if (formula == null)
		// salida = "Error: No se puede dejar sin datos (nulo) el campo: formula
		// ";

		// 2. sin nada desde la pagina
		if (tipo.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipo ";
		if (codigo_st.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: codigo_st ";
		if (entsal.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: entsal ";
		if (imprime.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: imprime ";
		if (edita.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: edita ";
		if (reutiliza.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: reutiliza ";
		// if (formula.equalsIgnoreCase(""))
		// salida = "Error: No se puede dejar vacio el campo: formula ";
		// fin validaciones

		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM produccionEsquemas_Deta WHERE idesquema = "
					+ idesquema.toString()
					+ " AND renglon = "
					+ renglon.toString()
					+ " AND idempresa = "
					+ idempresa.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (salida.equalsIgnoreCase("OK")) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE PRODUCCIONESQUEMAS_DETA SET renglon=?, tipo=?, codigo_st=?, cantidad=?, entsal=?, codigo_dt=?, margen_error=?, imprime=?, edita=?, formula=?, reutiliza=?, usuarioact=?, fechaact=? WHERE idesquema=? AND renglon = ? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, renglon);
					insert.setString(2, tipo);
					insert.setString(3, codigo_st);
					insert.setBigDecimal(4, cantidad);
					insert.setString(5, entsal);
					insert.setBigDecimal(6, codigo_dt);
					insert.setBigDecimal(7, margen_error);
					insert.setString(8, imprime);
					insert.setString(9, edita);
					insert.setString(10, formula);
					insert.setString(11, reutiliza);
					insert.setString(12, usuarioact);
					insert.setTimestamp(13, fechaact);
					insert.setBigDecimal(14, idesquema);
					insert.setBigDecimal(15, renglon);
					insert.setBigDecimal(16, idempresa);
				} else {
					String ins = "INSERT INTO PRODUCCIONESQUEMAS_DETA(idesquema, renglon, tipo, codigo_st, cantidad, entsal, codigo_dt, margen_error, imprime, edita, formula, reutiliza, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setBigDecimal(1, idesquema);
					insert.setBigDecimal(2, renglon);
					insert.setString(3, tipo);
					insert.setString(4, codigo_st);
					insert.setDouble(5, cantidad.doubleValue());
					insert.setString(6, entsal);
					insert.setBigDecimal(7, codigo_dt);
					insert.setDouble(8, margen_error.doubleValue());
					insert.setString(9, imprime);
					insert.setString(10, edita);
					insert.setString(11, formula);
					insert.setString(12, reutiliza);
					insert.setString(13, usuarioalt);
					insert.setBigDecimal(14, idempresa);
				}
				int i = insert.executeUpdate();
				if (i != 1)
					salida = "Error al actualizar detalle de esquema.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String produccionEsquemas_DetaCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String produccionEsquemas_DetaCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String produccionEsquemas_DetaUpdate(BigDecimal idesquema,
			BigDecimal renglon, String tipo, String codigo_st, Double cantidad,
			String entsal, BigDecimal codigo_dt, Double margen_error,
			String imprime, String edita, String formula, String usuarioact,
			BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idesquema == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idesquema ";
		if (renglon == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: renglon ";
		if (tipo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipo ";
		if (codigo_st == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_st ";
		if (cantidad == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cantidad ";
		if (entsal == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: entsal ";
		if (codigo_dt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_dt ";
		if (margen_error == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: margen_error ";
		if (imprime == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: imprime ";
		if (edita == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: edita ";
		if (formula == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: formula ";

		// 2. sin nada desde la pagina
		if (tipo.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipo ";
		if (codigo_st.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: codigo_st ";
		if (entsal.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: entsal ";
		if (imprime.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: imprime ";
		if (edita.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: edita ";
		if (formula.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: formula ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM produccionEsquemas_Deta WHERE idesquema = "
					+ idesquema.toString()
					+ " AND idempresa = "
					+ idempresa.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE PRODUCCIONESQUEMAS_DETA SET renglon=?, tipo=?, codigo_st=?, cantidad=?, entsal=?, codigo_dt=?, margen_error=?, imprime=?, edita=?, formula=?, usuarioact=?, fechaact=? WHERE idesquema=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, renglon);
					insert.setString(2, tipo);
					insert.setString(3, codigo_st);
					insert.setDouble(4, cantidad.doubleValue());
					insert.setString(5, entsal);
					insert.setBigDecimal(6, codigo_dt);
					insert.setDouble(7, margen_error.doubleValue());
					insert.setString(8, imprime);
					insert.setString(9, edita);
					insert.setString(10, formula);
					insert.setString(11, usuarioact);
					insert.setTimestamp(12, fechaact);
					insert.setBigDecimal(13, idesquema);
					insert.setBigDecimal(14, idempresa);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error SQL public String produccionEsquemas_DetaUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String produccionEsquemas_DetaUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// ESTO SE HIZO PARA TRAER EL LOV DE RECETA
	public List getProduccionRecetaLOV(String ocurrencia, BigDecimal idempresa)
			throws EJBException {
		String cQuery = "SELECT * FROM produccionrecetas_cabe WHERE (UPPER(receta) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%') AND idempresa = "
				+ idempresa.toString() + " ORDER BY codigo_st";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ESTO SE HIZO PARA TRAER EL LOV DE DEPOSITO
	public List getProduccionDepositoLOV(String ocurrencia, BigDecimal idempresa)
			throws EJBException {
		String cQuery = "SELECT * FROM stockdepositos WHERE (UPPER(descrip_dt) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%') AND idempresa= "
				+ idempresa.toString() + " ORDER BY codigo_dt";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// PRODUCCION RECURSOS
	// para todo (ordena por el segundo campo por defecto)
	public List getProduccionrecursosAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT pr.idrecurso, pr.recurso, sm.descrip_md, pr.costo, pr.usuarioalt, pr.usuarioact, pr.fechaalt, pr.fechaact "
				+ "  FROM PRODUCCIONRECURSOS pr "
				+ "       INNER JOIN stockmedidas sm ON sm.codigo_md = pr.codigo_md AND sm.idempresa = pr.idempresa "
				+ " WHERE sm.idempresa = " + idempresa.toString()
				+ " ORDER BY 2 LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getProduccionrecursosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT pr.idrecurso, pr.recurso, sm.descrip_md, pr.costo, pr.usuarioalt, pr.usuarioact, pr.fechaalt, pr.fechaact "
				+ "  FROM PRODUCCIONRECURSOS pr "
				+ "       INNER JOIN stockmedidas sm ON sm.codigo_md = pr.codigo_md AND sm.idempresa = pr.idempresa "
				+ " where pr.idempresa= " + idempresa.toString()
				+ " and (pr.idrecurso::VARCHAR LIKE '%" + ocurrencia + "%' OR "
				+ " UPPER(pr.recurso) LIKE '%" + ocurrencia.toUpperCase()
				+ "%') " + " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset
				+ ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getProduccionrecursosPK(BigDecimal idrecurso,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT pr.idrecurso, pr.recurso, pr.codigo_md, sm.descrip_md, pr.costo, "
				+ "       pr.usuarioalt, pr.usuarioact, pr.fechaalt, pr.fechaact "
				+ "  FROM PRODUCCIONRECURSOS pr INNER JOIN stockmedidas sm ON pr.codigo_md = sm.codigo_md AND pr.idempresa = sm.idempresa "
				+ " WHERE pr.idrecurso=" + idrecurso.toString()
				+ "   AND pr.idempresa = " + idempresa.toString();

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String ProduccionrecursosDelete(BigDecimal idrecurso,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM PRODUCCIONRECURSOS WHERE idrecurso="
				+ idrecurso.toString() + " AND idempresa = "
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM PRODUCCIONRECURSOS WHERE idrecurso="
						+ idrecurso.toString() + " AND idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : ProduccionrecursosDelete( BigDecimal idrecurso, BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: ProduccionrecursosDelete( BigDecimal idrecurso, BigDecimal idempresa )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String ProduccionrecursosCreate(String recurso,
			BigDecimal codigo_md, Double costo, String usuarioalt,
			BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (recurso == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: recurso ";
		if (codigo_md == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_md ";
		if (costo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: costo ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (recurso.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Recurso ";
		if (codigo_md.compareTo(new BigDecimal(0)) == 0)
			salida = "Error: No se puede dejar vacio el campo: Medida";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO PRODUCCIONRECURSOS(recurso, codigo_md, costo, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, recurso);
				insert.setBigDecimal(2, codigo_md);
				insert.setDouble(3, costo.doubleValue());
				insert.setString(4, usuarioalt);
				insert.setBigDecimal(5, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String ProduccionrecursosCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String ProduccionrecursosCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String ProduccionrecursosCreateOrUpdate(BigDecimal idrecurso,
			String recurso, BigDecimal codigo_md, Double costo,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idrecurso == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idrecurso ";
		if (recurso == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: recurso ";
		if (codigo_md == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_md ";
		if (costo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: costo ";

		// 2. sin nada desde la pagina
		if (recurso.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Recurso ";
		if (codigo_md.compareTo(new BigDecimal(0)) == 0)
			salida = "Error: No se puede dejar vacio el campo: Medida";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM Produccionrecursos WHERE idrecurso = "
					+ idrecurso.toString()
					+ " AND idempresa = "
					+ idempresa.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE PRODUCCIONRECURSOS SET recurso=?, codigo_md=?, costo=?, usuarioact=?, fechaact=? WHERE idrecurso=? AND idempresa = ?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, recurso);
					insert.setBigDecimal(2, codigo_md);
					insert.setDouble(3, costo.doubleValue());
					insert.setString(4, usuarioact);
					insert.setTimestamp(5, fechaact);
					insert.setBigDecimal(6, idrecurso);
					insert.setBigDecimal(7, idempresa);
				} else {
					String ins = "INSERT INTO PRODUCCIONRECURSOS(recurso, codigo_md, costo, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, recurso);
					insert.setBigDecimal(2, codigo_md);
					insert.setDouble(3, costo.doubleValue());
					insert.setString(4, usuarioalt);
					insert.setBigDecimal(5, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String ProduccionrecursosCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String ProduccionrecursosCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String ProduccionrecursosUpdate(BigDecimal idrecurso,
			String recurso, BigDecimal codigo_md, Double costo,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idrecurso == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idrecurso ";
		if (recurso == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: recurso ";
		if (codigo_md == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_md ";
		if (costo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: costo ";

		// 2. sin nada desde la pagina
		if (recurso.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Recurso ";
		if (codigo_md.compareTo(new BigDecimal(0)) == 0)
			salida = "Error: No se puede dejar vacio el campo: Medida";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM Produccionrecursos WHERE idrecurso = "
					+ idrecurso.toString()
					+ " AND idempresa = "
					+ idempresa.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE PRODUCCIONRECURSOS SET recurso=?, codigo_md=?, costo=?, usuarioact=?, fechaact=? WHERE idrecurso=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, recurso);
					insert.setBigDecimal(2, codigo_md);
					insert.setDouble(3, costo.doubleValue());
					insert.setString(4, usuarioact);
					insert.setTimestamp(5, fechaact);
					insert.setBigDecimal(6, idrecurso);
					insert.setBigDecimal(7, idempresa);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String ProduccionrecursosUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String ProduccionrecursosUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// RECETAS DE PRODUCCION
	/*
	 * A tener en cuenta: Son dos entidades _ cabecera y detalle, la idea es
	 * modificar los metodos de forma tal de que trabajen juntos en uno
	 */

	public List getProduccionRecetas_cabeAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT PR.codigo_st,ST.descrip_st,PR.receta,MED.descrip_md,"
				+ "       PR.usuarioalt,PR.usuarioact,PR.fechaalt,PR.fechaact,  "
				+ "       (SELECT COUNT(*)  FROM PRODUCCIONRECETAS_DETA "
				+ "         WHERE codigo_st_cabe = PR.codigo_st AND idempresa = PR.idempresa) as detalle "
				+ "  FROM PRODUCCIONRECETAS_CABE PR "
				+ "       INNER JOIN STOCKSTOCK ST ON ST.codigo_st = PR.codigo_st AND ST.idempresa = PR.idempresa "
				+ "       INNER JOIN STOCKMEDIDAS MED ON PR.codigo_md = MED.codigo_md  AND PR.idempresa = MED.idempresa "
				+ " WHERE PR.idempresa = " + idempresa.toString()
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)
	public List getProduccionRecetas_cabeOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT PR.codigo_st,ST.descrip_st,PR.receta,MED.descrip_md,"
				+ "       PR.usuarioalt,PR.usuarioact,PR.fechaalt,PR.fechaact,  "
				+ "       (SELECT COUNT(*)  FROM PRODUCCIONRECETAS_DETA "
				+ "         WHERE codigo_st_cabe = PR.codigo_st AND idempresa = PR.idempresa) as detalle "
				+ "  FROM PRODUCCIONRECETAS_CABE PR "
				+ "       INNER JOIN STOCKSTOCK ST ON ST.codigo_st = PR.codigo_st AND ST.idempresa = PR.idempresa "
				+ "       INNER JOIN STOCKMEDIDAS MED ON PR.codigo_md = MED.codigo_md  AND PR.idempresa = MED.idempresa "
				+ " WHERE PR.idempresa = " + idempresa.toString()
				+ " UPPER(PR.codigo_st) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%'  ORDER BY 2  LIMIT "
				+ limit + "  OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)
	public List getProduccionRecetas_cabePK(String codigo_st,
			BigDecimal idempresa) throws EJBException {

		String cQuery = ""
				+ "SELECT st.codigo_st,pc.receta,pc.codigo_md,sm.descrip_md,"
				+ "       pc.usuarioalt,pc.usuarioact,pc.fechaalt,pc.fechaact "
				+ "  FROM PRODUCCIONRECETAS_CABE pc "
				+ "       INNER JOIN stockstock st  ON  st.codigo_st = pc.codigo_st AND st.idempresa = pc.idempresa "
				+ "       INNER JOIN stockmedidas sm  ON sm.codigo_md = pc.codigo_md AND sm.idempresa = pc.idempresa "
				+ " WHERE and st.codigo_st= '" + codigo_st.toString()
				+ "' AND st.idempresa = " + idempresa.toString();
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String produccionRecetas_cabeDelete(String codigo_st,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM PRODUCCIONRECETAS_CABE WHERE codigo_st='"
				+ codigo_st.toString()
				+ "' AND idempresa="
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM PRODUCCIONRECETAS_CABE WHERE codigo_st='"
						+ codigo_st.toString() + "' AND idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : produccionRecetas_cabeDelete( String codigo_st, BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: produccionRecetas_cabeDelete( String codigo_st, BigDecimal idempresa )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String produccionRecetas_cabeCreate(String codigo_st, String receta,
			BigDecimal codigo_md, String usuarioalt, BigDecimal idempresa)
			throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (receta == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: receta ";
		if (codigo_md == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_md ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina

		// if (codigo_st.equalsIgnoreCase(""))
		// salida = "Error: No se puede dejar vacio el campo: Articulo ";
		// if (receta.equalsIgnoreCase(""))
		// salida = "Error: No se puede dejar vacio el campo: Receta ";

		// if (codigo_md.compareTo(new BigDecimal(0)) == 0)
		// salida = "Error: No se puede dejar vacio el campo: U.Medida";

		// if (usuarioalt.equalsIgnoreCase(""))
		// salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO PRODUCCIONRECETAS_CABE(codigo_st, receta, codigo_md, usuarioalt, idempresa ) VALUES (?,?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, codigo_st);
				insert.setString(2, receta);
				insert.setBigDecimal(3, codigo_md);
				insert.setString(4, usuarioalt);
				insert.setBigDecimal(5, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String produccionRecetas_cabeCreate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String produccionRecetas_cabeCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String produccionRecetas_cabeCreateOrUpdate(String codigo_st,
			String receta, BigDecimal codigo_md, String usuarioact,
			BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (codigo_st == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_st ";
		if (receta == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: receta ";
		if (codigo_md == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_md ";

		// 2. sin nada desde la pagina
		// if (codigo_st.equalsIgnoreCase(""))
		// salida = "Error: No se puede dejar vacio el campo: Articulo ";
		// if (receta.equalsIgnoreCase(""))
		// salida = "Error: No se puede dejar vacio el campo: Receta ";

		// if (codigo_md.compareTo(new BigDecimal(0)) == 0)
		// salida = "Error: No se puede dejar vacio el campo: U.Medida";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM produccionRecetas_cabe WHERE codigo_st = "
					+ codigo_st.toString()
					+ " AND idempresa = "
					+ idempresa.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE PRODUCCIONRECETAS_CABE SET receta=?, codigo_md=?, usuarioact=?, fechaact=? WHERE codigo_st=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, receta);
					insert.setBigDecimal(2, codigo_md);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setString(5, codigo_st);
					insert.setBigDecimal(6, idempresa);
				} else {
					String ins = "INSERT INTO PRODUCCIONRECETAS_CABE(codigo_st, receta, codigo_md, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, codigo_st);
					insert.setString(2, receta);
					insert.setBigDecimal(3, codigo_md);
					insert.setString(4, usuarioalt);
					insert.setBigDecimal(5, idempresa);

				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String produccionRecetas_cabeCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String produccionRecetas_cabeCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String produccionRecetas_cabeUpdate(String codigo_st, String receta,
			BigDecimal codigo_md, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";

		// validaciones de datos:
		// 1. nulidad de campos
		if (codigo_st == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_st ";
		if (receta == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: receta ";
		if (codigo_md == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_md ";

		// 2. sin nada desde la pagina
		// if (codigo_st.equalsIgnoreCase(""))
		// salida = "Error: No se puede dejar vacio el campo: Articulo ";
		// if (receta.equalsIgnoreCase(""))
		// salida = "Error: No se puede dejar vacio el campo: Receta ";

		// if (codigo_md.compareTo(new BigDecimal(0)) == 0)
		// salida = "Error: No se puede dejar vacio el campo: U.Medida";

		// fin validaciones
		boolean bError = true;

		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM produccionRecetas_cabe WHERE codigo_st = '"
					+ codigo_st.toString()
					+ "' AND idempresa="
					+ idempresa.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE PRODUCCIONRECETAS_CABE SET receta=?, codigo_md=?, usuarioact=?, fechaact=? WHERE codigo_st=? AND idempresa=?;";

					insert = dbconn.prepareStatement(sql);
					insert.setString(1, receta);
					insert.setBigDecimal(2, codigo_md);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setString(5, codigo_st);
					insert.setBigDecimal(6, idempresa);

				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error SQL public String produccionRecetas_cabeUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String produccionRecetas_cabeUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// detalle de la receta.
	// para todo (ordena por el segundo campo por defecto)
	// cep: modificado para que traiga por codigo de cabecera
	public List getProduccionRecetas_detaAll(String codigo_st_cabe, long limit,
			long offset, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "";
		cQuery += "SELECT ";
		cQuery += "D.codigo_st,";
		cQuery += "ST.descrip_st,";
		cQuery += "D.tipo_rd,";
		cQuery += "D.cantidad_rd,";
		cQuery += "D.imprime,";
		cQuery += "D.margen_error_rd,";
		cQuery += "D.usuarioalt,";
		cQuery += "D.usuarioact,";
		cQuery += "D.fechaalt,";
		cQuery += "D.fechaact ";
		cQuery += "  FROM  ";
		cQuery += "PRODUCCIONRECETAS_DETA D INNER JOIN ";
		cQuery += " STOCKstock ST  ON D.codigo_st = ST.codigo_st AND D.idempresa = ST.idempresa ";
		cQuery += " WHERE D.idempresa = " + idempresa.toString();
		cQuery += "   AND D.codigo_st_cabe = '" + codigo_st_cabe.toString()
				+ "'";
		cQuery += " ORDER BY 2";
		cQuery += "  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getProduccionRecetas_detaOcu(String codigo_st_cabe, long limit,
			long offset, String ocurrencia, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT D.codigo_st, ST.descrip_st,D.tipo_rd,D.cantidad_rd,D.imprime,D.margen_error_rd,D.usuarioalt,D.usuarioact,D.fechaalt,D.fechaact FROM PRODUCCIONRECETAS_DETA D,STOCKstock ST WHERE D.codigo_st = ST.codigo_st "
				+ "and codigo_st_cabe ='" + codigo_st_cabe.toString() + "'"
				+ "and UPPER(d.CODIGO_ST) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%' AND d.idempresa = "
				+ idempresa.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getProduccionRecetas_detaPK(String codigo_st_cabe,
			String codigo_st, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT codigo_st_cabe,codigo_st,tipo_rd,cantidad_rd,imprime,margen_error_rd,"
				+ "       usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM PRODUCCIONRECETAS_DETA WHERE codigo_st_cabe='"
				+ codigo_st_cabe.toString() + "' AND codigo_st='"
				+ codigo_st.toString() + "' AND idempresa = "
				+ idempresa.toString();
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String produccionRecetas_detaDelete(String codigo_st_cabe,
			String codigo_st, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String salida = "NOOK";

		String cQuery = "SELECT * FROM PRODUCCIONRECETAS_DETA WHERE codigo_st_cabe='"
				+ codigo_st_cabe.toString()
				+ "' AND codigo_st='"
				+ codigo_st.toString()
				+ "' AND idempresa="
				+ idempresa.toString();

		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida != null && rsSalida.next()) {
				cQuery = "DELETE FROM PRODUCCIONRECETAS_DETA  WHERE codigo_st_cabe='"
						+ codigo_st_cabe.toString()
						+ "' AND codigo_st='"
						+ codigo_st.toString()
						+ "' AND idempresa="
						+ idempresa.toString();

				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : produccionRecetas_detaDelete( String codigo_st_cabe ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: produccionRecetas_detaDelete( String codigo_st_cabe )  "
							+ ex);
		}

		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String produccionRecetas_detaCreate(String codigo_st_cabe,
			String codigo_st, String tipo_rd, Double cantidad_rd,
			String imprime, Double margen_error_rd, String usuarioalt,
			BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (codigo_st == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: Articulo ";
		if (tipo_rd == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: Tipo ";
		if (cantidad_rd == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: Cantidad ";
		if (imprime == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: Imprime ";
		if (margen_error_rd == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: Margen de Error ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (codigo_st.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Articulo ";
		if (tipo_rd.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Tipo ";
		if (imprime.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Imprime ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO PRODUCCIONRECETAS_DETA(codigo_st_cabe, codigo_st, tipo_rd, cantidad_rd, imprime, margen_error_rd, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, codigo_st_cabe);
				insert.setString(2, codigo_st);
				insert.setString(3, tipo_rd);
				insert.setDouble(4, cantidad_rd.doubleValue());
				insert.setString(5, imprime);
				insert.setDouble(6, margen_error_rd.doubleValue());
				insert.setString(7, usuarioalt);
				insert.setBigDecimal(8, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String produccionRecetas_detaCreate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String produccionRecetas_detaCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String produccionRecetas_detaCreateOrUpdate(String codigo_st_cabe,
			String codigo_st, String tipo_rd, Double cantidad_rd,
			String imprime, Double margen_error_rd, String usuarioact,
			BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (codigo_st_cabe == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_st_cabe ";
		if (codigo_st == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_st ";
		if (tipo_rd == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipo_rd ";
		if (cantidad_rd == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cantidad_rd ";
		if (imprime == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: imprime ";
		if (margen_error_rd == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: margen_error_rd ";

		// 2. sin nada desde la pagina
		if (codigo_st_cabe.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: codigo_st_cabe ";
		if (codigo_st.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: codigo_st ";
		if (tipo_rd.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipo_rd ";
		if (imprime.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: imprime ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM produccionRecetas_deta WHERE codigo_st_cabe = "
					+ codigo_st_cabe.toString()
					+ " AND idempresa ="
					+ idempresa.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE PRODUCCIONRECETAS_DETA SET codigo_st=?, tipo_rd=?, cantidad_rd=?, imprime=?, margen_error_rd=?, usuarioact=?, fechaact=? WHERE codigo_st_cabe=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, codigo_st);
					insert.setString(2, tipo_rd);
					insert.setDouble(3, cantidad_rd.doubleValue());
					insert.setString(4, imprime);
					insert.setDouble(5, margen_error_rd.doubleValue());
					insert.setString(6, usuarioact);
					insert.setTimestamp(7, fechaact);
					insert.setString(8, codigo_st_cabe);
					insert.setBigDecimal(9, idempresa);
				} else {
					String ins = "INSERT INTO PRODUCCIONRECETAS_DETA(codigo_st, tipo_rd, cantidad_rd, imprime, margen_error_rd, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, codigo_st);
					insert.setString(2, tipo_rd);
					insert.setDouble(3, cantidad_rd.doubleValue());
					insert.setString(4, imprime);
					insert.setDouble(5, margen_error_rd.doubleValue());
					insert.setString(6, usuarioalt);
					insert.setBigDecimal(7, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String produccionRecetas_detaCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String produccionRecetas_detaCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String produccionRecetas_detaUpdate(String codigo_st_cabe,
			String codigo_st, String tipo_rd, Double cantidad_rd,
			String imprime, Double margen_error_rd, String usuarioact,
			BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (codigo_st_cabe == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_st_cabe ";
		if (codigo_st == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_st ";
		if (tipo_rd == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipo_rd ";
		if (cantidad_rd == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cantidad_rd ";
		if (imprime == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: imprime ";
		if (margen_error_rd == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: margen_error_rd ";

		// 2. sin nada desde la pagina
		if (codigo_st_cabe.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: codigo_st_cabe ";
		if (codigo_st.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: codigo_st ";
		if (tipo_rd.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipo_rd ";
		if (imprime.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: imprime ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM produccionRecetas_deta WHERE codigo_st_cabe = '"
					+ codigo_st_cabe.toString()
					+ "' and codigo_st = '"
					+ codigo_st.toString()
					+ "' AND idempresa="
					+ idempresa.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE PRODUCCIONRECETAS_DETA SET codigo_st=?, tipo_rd=?, cantidad_rd=?, imprime=?, margen_error_rd=?, usuarioact=?, fechaact=? WHERE codigo_st_cabe=? and codigo_st=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, codigo_st);
					insert.setString(2, tipo_rd);
					insert.setDouble(3, cantidad_rd.doubleValue());
					insert.setString(4, imprime);
					insert.setDouble(5, margen_error_rd.doubleValue());
					insert.setString(6, usuarioact);
					insert.setTimestamp(7, fechaact);
					insert.setString(8, codigo_st_cabe);
					insert.setString(9, codigo_st);
					insert.setBigDecimal(10, idempresa);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error SQL public String produccionRecetas_detaUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String produccionRecetas_detaUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// ORDENES DE PRODUCCION
	// ---------------------------------------

	/**
	 * Metodos para la entidad: produccionMovProdu Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Wed Feb 21 13:30:16 GMT-03:00 2007
	 */

	public String produccionOrdenProdCreate(BigDecimal idesquema,
			BigDecimal idcliente, BigDecimal cantre_op, BigDecimal cantest_op,
			java.sql.Date fecha_prometida, java.sql.Date fecha_emision,
			String observaciones, String codigo_st, BigDecimal idcontador,
			BigDecimal nrointerno, BigDecimal idpedido_regalos_cabe,
			String usuarioalt, BigDecimal idempresa) throws EJBException,
			SQLException {
		String salida = "OK";
		BigDecimal idop = null;
		try {
			dbconn.setAutoCommit(false);

			salida = produccionMovProduCreate(idesquema, idcliente, cantre_op,
					cantest_op, fecha_prometida, fecha_emision, observaciones,
					codigo_st, idcontador, nrointerno, idpedido_regalos_cabe,
					usuarioalt, idempresa);

			if (salida.equalsIgnoreCase("OK")) {

				idop = GeneralBean.getValorSequencia("seq_produccionmovprodu",
						dbconn);

				if (idop != null) {

					salida = produccionOrdenProdDetaCreate(idesquema, idop,
							cantest_op, new Timestamp(fecha_emision.getTime()),
							usuarioalt, idempresa);

				} else
					salida = "Error al recuperar Id. Orden de Producci�n.";

			}

		} catch (Exception e) {
			log.error("produccionOrdenProdCreate(...)" + e);
		}

		if (!salida.equalsIgnoreCase("OK")) {
			dbconn.rollback();
		} else
			salida = idop.toString();

		dbconn.setAutoCommit(true);
		return salida;

	}

	public String produccionOrdenProdUpdate(BigDecimal idop,
			BigDecimal idesquema, BigDecimal idcliente, BigDecimal cantre_op,
			BigDecimal cantest_op, java.sql.Date fecha_prometida,
			java.sql.Date fecha_emision, String observaciones,
			String codigo_st, BigDecimal idcontador, BigDecimal nrointerno,
			String usuarioalt, BigDecimal idempresa) throws EJBException,
			SQLException {
		String salida = "OK";

		try {
			dbconn.setAutoCommit(false);

			// log.info("Es mismo esquema: " + isMismoEsquemaOP(idop,
			// idesquema));
			if (!isOrdenProdAnulada(idop, idempresa)) {

				// 20101116 - EJV -
				// if (!isMismoEsquemaOP(idop, idesquema, idempresa)) {

				salida = produccionMovProdu_DetaDelete(idop, idempresa);

				if (salida.equalsIgnoreCase("OK")) {

					salida = produccionOrdenProdDetaCreate(idesquema, idop,
							cantest_op, new Timestamp(fecha_emision.getTime()),
							usuarioalt, idempresa);
				} else
					salida = "Error al actualizar detalle de  Orden de Produccion.";

				// }
				if (salida.equalsIgnoreCase("OK")) {
					salida = produccionMovProduUpdate(idop, idesquema,
							idcliente, cantre_op, cantest_op, fecha_prometida,
							fecha_emision, observaciones, codigo_st,
							idcontador, nrointerno, usuarioalt, idempresa);
				}

			} else {
				salida = "No se puede modificar la orden de produccion, la misma ya fue procesada.";
			}

		} catch (Exception e) {
			log.error("produccionOrdenProdUpdate(...)" + e);
		}

		if (!salida.equalsIgnoreCase("OK")) {
			dbconn.rollback();
		}

		dbconn.setAutoCommit(true);
		return salida;

	}

	public boolean isMismoEsquemaOP(BigDecimal idop, BigDecimal idesquema,
			BigDecimal idempresa) throws EJBException {

		/**
		 * Entidad: Verificar si en la actualizacion de una OP cambia el
		 * esquema, para modificar el detalle de la misma (delete-insert).
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : recuperar total de registros.
		 */
		boolean ismismoesquema = false;
		ResultSet rsSalida = null;
		String cQuery = "SELECT count(1)AS total FROM produccionmovprodu WHERE idop = ? AND idesquema = ? AND idempresa = ?";
		try {

			PreparedStatement pstatement = dbconn.prepareStatement(cQuery);
			pstatement.setBigDecimal(1, idop);
			pstatement.setBigDecimal(2, idesquema);
			pstatement.setBigDecimal(3, idempresa);
			rsSalida = pstatement.executeQuery();

			if (rsSalida.next()) {
				if (rsSalida.getLong("total") > 0)
					ismismoesquema = true;
			} else {
				log.warn("isMismoEsquemaOP()- Error al recuperar total. ");
			}
		} catch (SQLException sqlException) {
			log.error("isMismoEsquemaOP(..)- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("isMismoEsquemaOP(..)- Salida por exception: " + ex);
		}
		return ismismoesquema;
	}

	// para todo (ordena por el segundo campo por defecto)
	public List getProduccionMovProduAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT mp.idop,mp.idesquema,mp.idcliente,mp.cantre_op,mp.cantest_op,mp.fecha_prometida,mp.fecha_emision,"
				+ "       mp.observaciones,mp.codigo_st,st.descrip_st,mp.idcontador,mp.nrointerno,"
				+ "       CASE  WHEN fbaja IS NOT NULL "
				+ "             THEN 'ANULADA'"
				+ "             ELSE "
				+ "             CASE "
				+ "                  WHEN mp.cantre_op = 0 THEN 'PENDIENTE' "
				+ "                  WHEN mp.cantre_op < mp.cantest_op THEN 'EN PROCESO' "
				+ "                  WHEN mp.cantre_op >= mp.cantest_op THEN 'FINALIZADA' "
				+ "             END"
				+ "       END AS estado, "
				+ "       mp.usuarioalt,mp.usuarioact,mp.fechaalt,mp.fechaact "
				+ "  FROM produccionmovprodu mp "
				+ "       INNER JOIN stockstock st ON mp.codigo_st = st.codigo_st AND mp.idempresa = st.idempresa"
				+ " WHERE mp.idempresa = "
				+ idempresa.toString()
				+ "   AND mp.idop NOT IN (SELECT idop FROM interfacesopregalos WHERE idempresa = "
				+ idempresa
				+ ")  AND mp.idpedido_regalos_cabe IS  NULL  ORDER BY 1 LIMIT "
				+ limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getProduccionMovProduOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT mp.idop,mp.idesquema,mp.idcliente,mp.cantre_op,mp.cantest_op,mp.fecha_prometida,mp.fecha_emision,"
				+ "       mp.observaciones,mp.codigo_st,st.descrip_st,mp.idcontador,mp.nrointerno,"
				+ "       CASE  WHEN fbaja IS NOT NULL "
				+ "             THEN 'ANULADA'"
				+ "             ELSE "
				+ "             CASE "
				+ "                  WHEN mp.cantre_op = 0 THEN 'PENDIENTE' "
				+ "                  WHEN mp.cantre_op < mp.cantest_op THEN 'EN PROCESO' "
				+ "                  WHEN mp.cantre_op >= mp.cantest_op THEN 'FINALIZADA' "
				+ "             END"
				+ "       END AS estado, "
				+ "       mp.usuarioalt,mp.usuarioact,mp.fechaalt,mp.fechaact "
				+ "  FROM produccionmovprodu mp "
				+ "       INNER JOIN stockstock st ON mp.codigo_st = st.codigo_st AND mp.idempresa = st.idempresa"
				+ " WHERE (UPPER(mp.idop::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(st.codigo_st) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%') AND st.idempresa = "
				+ idempresa.toString()
				+ "   AND mp.idop NOT IN (SELECT idop FROM interfacesopregalos WHERE idempresa = "
				+ idempresa
				+ ")  AND mp.idpedido_regalos_cabe IS  NULL  ORDER BY 1 LIMIT "
				+ limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getProduccionMovProduFiltro(long limit, long offset,
			String filtro, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT idop,idesquema,idcliente,cantre_op,cantest_op,fecha_prometida,fecha_emision,"
				+ "       observaciones,codigo_st,descrip_st,idcontador,nrointerno,estado, "
				+ "       usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM ("
				+ "       SELECT mp.idop,mp.idesquema,mp.idcliente,mp.cantre_op,mp.cantest_op,mp.fecha_prometida,mp.fecha_emision,"
				+ "              mp.observaciones,mp.codigo_st,st.descrip_st,mp.idcontador,mp.nrointerno,"
				+ "              CASE  WHEN fbaja IS NOT NULL "
				+ "                    THEN 'ANULADA'"
				+ "                    ELSE "
				+ "                    CASE "
				+ "                         WHEN mp.cantre_op = 0 THEN 'PENDIENTE' "
				+ "                         WHEN mp.cantre_op < mp.cantest_op THEN 'EN PROCESO' "
				+ "                         WHEN mp.cantre_op >= mp.cantest_op THEN 'FINALIZADA' "
				+ "                    END"
				+ "              END AS estado, "
				+ "              mp.idempresa,mp.usuarioalt,mp.usuarioact,mp.fechaalt,mp.fechaact "
				+ "         FROM produccionmovprodu mp "
				+ "              INNER JOIN stockstock st ON mp.codigo_st = st.codigo_st AND mp.idempresa = st.idempresa "
				+ " ) mov " + filtro + " AND idempresa = " + idempresa
				+ " ORDER BY 1 LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getProduccionMovProduPendientesAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT mp.idop,mp.idesquema,mp.idcliente,mp.cantre_op,mp.cantest_op,mp.fecha_prometida,mp.fecha_emision,"
				+ "       mp.observaciones,mp.codigo_st,st.descrip_st,mp.idcontador,mp.nrointerno,mp.usuarioalt,mp.usuarioact,mp.fechaalt,mp.fechaact "
				+ "  FROM produccionmovprodu mp "
				+ "       INNER JOIN stockstock st ON mp.codigo_st = st.codigo_st AND st.idempresa = mp.idempresa "
				+ "WHERE mp.cantre_op < mp.cantest_op AND fbaja IS NULL AND mp.idempresa = "
				+ idempresa.toString()
				+ "  AND mp.idop NOT IN (SELECT idop FROM interfacesopregalos WHERE idempresa = "
				+ idempresa
				+ ")  AND mp.idpedido_regalos_cabe IS  NULL  ORDER BY 2  LIMIT "
				+ limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getProduccionMovProduPendientesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT mp.idop,mp.idesquema,mp.idcliente,mp.cantre_op,mp.cantest_op,mp.fecha_prometida,mp.fecha_emision,"
				+ "       mp.observaciones,mp.codigo_st,st.descrip_st,mp.idcontador,mp.nrointerno,mp.usuarioalt,mp.usuarioact,mp.fechaalt,mp.fechaact "
				+ "  FROM produccionmovprodu mp "
				+ "       INNER JOIN stockstock st ON mp.codigo_st = st.codigo_st AND mp.idempresa = st.idempresa "
				+ " WHERE ((idop::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(mp.codigo_st) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' ) AND cantre_op < cantest_op AND fbaja IS NULL AND mp.idempresa = "
				+ idempresa.toString()
				+ "   AND mp.idop NOT IN (SELECT idop FROM interfacesopregalos WHERE idempresa = "
				+ idempresa
				+ ")  AND mp.idpedido_regalos_cabe IS  NULL  ORDER BY 2  LIMIT "
				+ limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getProduccionMovProduRegalosPendientesAll(long limit,
			long offset, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT mp.idop,mp.idesquema,mp.idcliente,mp.cantre_op,mp.cantest_op,mp.fecha_prometida,mp.fecha_emision,"
				+ "       mp.observaciones,mp.codigo_st,st.descrip_st,mp.idcontador,mp.nrointerno, mp.idpedido_regalos_cabe, mp.usuarioalt,mp.usuarioact,mp.fechaalt,mp.fechaact "
				+ "  FROM produccionmovprodu mp "
				+ "       INNER JOIN stockstock st ON mp.codigo_st = st.codigo_st AND st.idempresa = mp.idempresa "
				+ "WHERE mp.cantre_op < mp.cantest_op AND fbaja IS NULL AND mp.idempresa = "
				+ idempresa.toString()
				+ "  AND mp.idop NOT IN (SELECT idop FROM interfacesopregalos WHERE idempresa = "
				+ idempresa
				+ ")  AND mp.idpedido_regalos_cabe IS NOT  NULL  ORDER BY 2  LIMIT "
				+ limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getProduccionMovProduRegalosPendientesOcu(long limit,
			long offset, String ocurrencia, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT mp.idop,mp.idesquema,mp.idcliente,mp.cantre_op,mp.cantest_op,mp.fecha_prometida,mp.fecha_emision,"
				+ "       mp.observaciones,mp.codigo_st,st.descrip_st,mp.idcontador,mp.nrointerno, mp.idpedido_regalos_cabe, mp.usuarioalt,mp.usuarioact,mp.fechaalt,mp.fechaact "
				+ "  FROM produccionmovprodu mp "
				+ "       INNER JOIN stockstock st ON mp.codigo_st = st.codigo_st AND mp.idempresa = st.idempresa "
				+ " WHERE ((idop::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(mp.codigo_st) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' ) AND cantre_op < cantest_op AND fbaja IS NULL AND mp.idempresa = "
				+ idempresa.toString()
				+ "   AND mp.idop NOT IN (SELECT idop FROM interfacesopregalos WHERE idempresa = "
				+ idempresa
				+ ")  AND mp.idpedido_regalos_cabe IS NOT  NULL  ORDER BY 2  LIMIT "
				+ limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getProduccionMovProduPK(BigDecimal idop, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT mp.idop,mp.idesquema,ec.esquema,mp.idcliente,mp.cantre_op::NUMERIC(18) AS cantre_op,mp.cantest_op::NUMERIC(18) AS cantest_op,mp.fecha_prometida,"
				+ "       mp.fecha_emision,mp.observaciones,mp.codigo_st,sk.descrip_st,mp.idcontador,mp.nrointerno,ed.codigo_dt, mp.idpedido_regalos_cabe,"
				+ "       mp.usuarioalt,mp.usuarioact,mp.fechaalt,mp.fechaact "
				+ "  FROM produccionmovprodu mp  "
				+ "       INNER JOIN produccionesquemas_cabe ec ON mp.idesquema = ec.idesquema AND mp.idempresa = ec.idempresa "
				+ "       INNER JOIN produccionesquemas_deta ed ON ec.idesquema = ed.idesquema AND ec.idempresa = ed.idempresa AND ed.entsal = 'P' and ed.tipo = 'A'"
				+ "       INNER JOIN stockstock sk ON ed.codigo_st = sk.codigo_st AND ed.idempresa = sk.idempresa "
				+ " WHERE mp.idop=" + idop.toString()
				+ "   AND mp.idempresa = " + idempresa.toString()
		// + " AND mp.idop NOT IN (SELECT idop FROM interfacesopregalos WHERE
		// idempresa = "
		// + idempresa + " ) "
		;

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	//

	public List getProduccionMovProduParcialesPK(BigDecimal idop,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT mpc.idop,mpc.idesquema,mpc.idcliente,mpc.fecha_prometida, "
				+ "       mpc.fecha_emision,mpc.codigo_st, "
				+ "       mpc.fbaja,mpd.codigo_dt,mpd.fechaaltaorden,mpd.stockbis, "
				+ "       ms.comprob_ms,ms.fecha_ms,ms.articu_ms,ms.canti_ms, "
				+ "       ms.usuarioalt,ms.usuarioact,ms.fechaalt,ms.fechaact "
				+ "  FROM produccionmovprodu mpc "
				+ "       INNER JOIN produccionmovprodu_deta mpd ON mpc.idop = mpd.idop AND mpc.idempresa = mpd.idempresa "
				+ "       INNER JOIN stockmovstock ms ON mpd.idop = ms.comprob_ms  AND mpd.idempresa = ms.idempresa "
				+ "              AND mpd.codigo = ms.articu_ms "
				+ "              AND mpd.idempresa = ms.idempresa "
				+ "              AND ms.tipomov_ms = 'E' "
				+ "              AND ms.sistema_ms = 'U' "
				+ " WHERE mpc.idop =" + idop.toString()
				+ "   AND mpc.idempresa = " + idempresa.toString()
				+ " ORDER BY ms.fecha_ms ";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/*
	 * 
	 * 
	 */

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)
	public String produccionMovProduDelete(BigDecimal idop, BigDecimal idempresa)
			throws EJBException, SQLException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM PRODUCCIONMOVPRODU WHERE idop="
				+ idop.toString() + " AND idempresa = " + idempresa.toString();
		String salida = "OK";
		try {

			dbconn.setAutoCommit(false);

			if (!isOrdenProdInProduccion(idop, idempresa)) {

				if (!isOrdenProdAnulada(idop, idempresa)) {

					Statement statement = dbconn.createStatement();
					rsSalida = statement.executeQuery(cQuery);

					salida = produccionMovProdu_DetaDelete(idop, idempresa);

					if (salida.equalsIgnoreCase("OK")) {
						if (rsSalida.next()) {
							cQuery = "DELETE FROM PRODUCCIONMOVPRODU WHERE idop="
									+ idop.toString()
									+ " AND idempresa="
									+ idempresa.toString();
							statement.execute(cQuery);

						} else {
							salida = "Error: Registro inexistente";
						}
					}

				} else {
					salida = "No se puede eliminar la orden de produccion, la misma ya fue procesada.";
				}

			} else {
				salida = "No se puede eliminar la orden de produccion, la misma ya fue procesada.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : produccionMovProduDelete( BigDecimal idop, BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: produccionMovProduDelete( BigDecimal idop, BigDecimal idempresa )  "
							+ ex);
		}

		if (!salida.equalsIgnoreCase("OK"))
			dbconn.rollback();
		else
			salida = "Baja Correcta.";

		dbconn.setAutoCommit(true);

		return salida;
	}

	public boolean isOrdenProdInProduccion(BigDecimal idop, BigDecimal idempresa)
			throws EJBException {

		/**
		 * OBJETIVO: Verificar si la orden de produccion no fue procesada para
		 * poder eliminarla ...
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : recuperar total de registros.
		 */
		boolean isordeninproduccion = false;
		ResultSet rsSalida = null;
		String cQuery = "SELECT count(1)AS total FROM produccionmovprodu WHERE idop = ? AND cantre_op > 0 AND idempresa = ?";
		try {

			PreparedStatement pstatement = dbconn.prepareStatement(cQuery);
			pstatement.setBigDecimal(1, idop);
			pstatement.setBigDecimal(2, idempresa);
			rsSalida = pstatement.executeQuery();

			if (rsSalida.next()) {
				if (rsSalida.getLong("total") > 0)
					isordeninproduccion = true;
			} else {
				log
						.warn("isOrdenProdInProduccion(..)- Error al recuperar total. ");
			}
		} catch (SQLException sqlException) {
			log
					.error("isOrdenProdInProduccion(..)- Error SQL: "
							+ sqlException);
		} catch (Exception ex) {
			log.error("isOrdenProdInProduccion(..)- Salida por exception: "
					+ ex);
		}
		return isordeninproduccion;
	}

	public boolean isOrdenProdAnulada(BigDecimal idop, BigDecimal idempresa)
			throws EJBException {

		/**
		 * OBJETIVO: Verificar si la orden de produccion no fue anulada para
		 * poder eliminarla ...
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : recuperar total de registros.
		 */
		boolean isordenanulada = false;
		ResultSet rsSalida = null;
		String cQuery = "SELECT count(1)AS total FROM produccionmovprodu WHERE idop = ? AND fbaja IS NOT NULL AND idempresa = ?";
		try {

			PreparedStatement pstatement = dbconn.prepareStatement(cQuery);
			pstatement.setBigDecimal(1, idop);
			pstatement.setBigDecimal(2, idempresa);
			rsSalida = pstatement.executeQuery();

			if (rsSalida.next()) {
				if (rsSalida.getLong("total") > 0)
					isordenanulada = true;
			} else {
				log.warn("isOrdenProdAnulada(..)- Error al recuperar total. ");
			}
		} catch (SQLException sqlException) {
			log.error("isOrdenProdAnulada(..)- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("isOrdenProdAnulada(..)- Salida por exception: " + ex);
		}
		return isordenanulada;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String produccionMovProduCreate(BigDecimal idesquema,
			BigDecimal idcliente, BigDecimal cantre_op, BigDecimal cantest_op,
			java.sql.Date fecha_prometida, java.sql.Date fecha_emision,
			String observaciones, String codigo_st, BigDecimal idcontador,
			BigDecimal nrointerno, BigDecimal idpedido_regalos_cabe,
			String usuarioalt, BigDecimal idempresa) throws EJBException {
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idesquema == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idesquema ";
		// if (idcliente == null)
		// salida = "Error: No se puede dejar sin datos (nulo) el campo:
		// idcliente ";
		if (cantest_op == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cantest_op ";
		if (fecha_prometida == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fecha_prometida ";
		if (fecha_emision == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fecha_emision ";
		if (codigo_st == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_st ";
		if (idcontador == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idcontador ";
		if (nrointerno == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nrointerno ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (codigo_st.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: codigo_st ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones

		try {
			if (salida.equalsIgnoreCase("OK")) {
				String ins = "INSERT INTO PRODUCCIONMOVPRODU(idesquema, idcliente, cantre_op, cantest_op, fecha_prometida, fecha_emision, observaciones, codigo_st, idcontador, nrointerno, idpedido_regalos_cabe, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, idesquema);
				insert.setBigDecimal(2, idcliente);
				insert.setDouble(3, cantre_op.doubleValue());
				insert.setDouble(4, cantest_op.doubleValue());
				insert.setDate(5, fecha_prometida);
				insert.setDate(6, fecha_emision);
				insert.setString(7, observaciones);
				insert.setString(8, codigo_st);
				insert.setBigDecimal(9, idcontador);
				insert.setBigDecimal(10, nrointerno);
				insert.setBigDecimal(11, idpedido_regalos_cabe);
				insert.setString(12, usuarioalt);
				insert.setBigDecimal(13, idempresa);
				int n = insert.executeUpdate();
				if (n != 1)
					throw new SQLException(
							"Error al generar registro en PRODUCCIONMOVPRODU.");
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String produccionMovProduCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String produccionMovProduCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String produccionMovProduCreateOrUpdate(BigDecimal idop,
			BigDecimal idesquema, BigDecimal idcliente, BigDecimal cantre_op,
			BigDecimal cantest_op, java.sql.Date fecha_prometida,
			java.sql.Date fecha_emision, String observaciones,
			String codigo_st, BigDecimal idcontador, BigDecimal nrointerno,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idop == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idop ";
		if (idesquema == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idesquema ";
		if (idcliente == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idcliente ";
		if (cantest_op == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cantest_op ";
		if (fecha_prometida == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fecha_prometida ";
		if (fecha_emision == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fecha_emision ";
		if (codigo_st == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_st ";
		if (idcontador == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idcontador ";
		if (nrointerno == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nrointerno ";

		// 2. sin nada desde la pagina
		if (codigo_st.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: codigo_st ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM produccionMovProdu WHERE idop = "
					+ idop.toString()
					+ " AND idempresa = "
					+ idempresa.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE PRODUCCIONMOVPRODU SET idesquema=?, idcliente=?, cantre_op=?, cantest_op=?, fecha_prometida=?, fecha_emision=?, observaciones=?, codigo_st=?, idcontador=?, nrointerno=?, usuarioact=?, fechaact=? WHERE idop=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, idesquema);
					insert.setBigDecimal(2, idcliente);
					insert.setDouble(3, cantre_op.doubleValue());
					insert.setDouble(4, cantest_op.doubleValue());
					insert.setDate(5, fecha_prometida);
					insert.setDate(6, fecha_emision);
					insert.setString(7, observaciones);
					insert.setString(8, codigo_st);
					insert.setBigDecimal(9, idcontador);
					insert.setBigDecimal(10, nrointerno);
					insert.setString(11, usuarioact);
					insert.setTimestamp(12, fechaact);
					insert.setBigDecimal(13, idop);
					insert.setBigDecimal(14, idempresa);
				} else {
					String ins = "INSERT INTO PRODUCCIONMOVPRODU(idesquema, idcliente, cantre_op, cantest_op, fecha_prometida, fecha_emision, observaciones, codigo_st, idcontador, nrointerno, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setBigDecimal(1, idesquema);
					insert.setBigDecimal(2, idcliente);
					insert.setDouble(3, cantre_op.doubleValue());
					insert.setDouble(4, cantest_op.doubleValue());
					insert.setDate(5, fecha_prometida);
					insert.setDate(6, fecha_emision);
					insert.setString(7, observaciones);
					insert.setString(8, codigo_st);
					insert.setBigDecimal(9, idcontador);
					insert.setBigDecimal(10, nrointerno);
					insert.setString(11, usuarioalt);
					insert.setBigDecimal(12, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String produccionMovProduCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String produccionMovProduCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String produccionMovProduUpdate(BigDecimal idop,
			BigDecimal idesquema, BigDecimal idcliente, BigDecimal cantre_op,
			BigDecimal cantest_op, java.sql.Date fecha_prometida,
			java.sql.Date fecha_emision, String observaciones,
			String codigo_st, BigDecimal idcontador, BigDecimal nrointerno,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idop == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idop ";
		if (idesquema == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idesquema ";
		// if (idcliente == null)
		// salida = "Error: No se puede dejar sin datos (nulo) el campo:
		// idcliente ";
		if (cantest_op == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cantest_op ";
		if (fecha_prometida == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fecha_prometida ";
		if (fecha_emision == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fecha_emision ";
		if (codigo_st == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_st ";
		if (idcontador == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idcontador ";
		if (nrointerno == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nrointerno ";

		// 2. sin nada desde la pagina
		if (codigo_st.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: codigo_st ";
		// fin validaciones

		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM produccionMovProdu WHERE idop = "
					+ idop.toString()
					+ " AND idempresa = "
					+ idempresa.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			int indice = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (salida.equalsIgnoreCase("OK")) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE PRODUCCIONMOVPRODU SET idesquema=?, idcliente=?, cantre_op=cantre_op, cantest_op=?, fecha_prometida=?, fecha_emision=?, observaciones=?, codigo_st=?, idcontador=?, nrointerno=?, usuarioact=?, fechaact=? WHERE idop=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(++indice, idesquema);
					insert.setBigDecimal(++indice, idcliente);
					// insert.setDouble(++indice, cantre_op.doubleValue());
					// cantre_op=?,
					insert.setDouble(++indice, cantest_op.doubleValue());
					insert.setDate(++indice, fecha_prometida);
					insert.setDate(++indice, fecha_emision);
					insert.setString(++indice, observaciones);
					insert.setString(++indice, codigo_st);
					insert.setBigDecimal(++indice, idcontador);
					insert.setBigDecimal(++indice, nrointerno);
					insert.setString(++indice, usuarioact);
					insert.setTimestamp(++indice, fechaact);
					insert.setBigDecimal(++indice, idop);
					insert.setBigDecimal(++indice, idempresa);
				}

				int i = insert.executeUpdate();
				if (i != 1)
					salida = "Imposible actualizar el registro cabecera op.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String produccionMovProduUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String produccionMovProduUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public static String produccionMovProduAnulaOp(BigDecimal idop,
			BigDecimal idempresa, String usuarioact, Connection dbconn)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "OK";
		// validaciones de datos:

		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM produccionMovProdu WHERE idop = "
					+ idop.toString()
					+ " AND idempresa = "
					+ idempresa.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			int indice = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (salida.equalsIgnoreCase("OK")) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE PRODUCCIONMOVPRODU SET fbaja=?, usuarioact=?, fechaact=? WHERE idop=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setTimestamp(++indice, fechaact);
					insert.setString(++indice, usuarioact);
					insert.setTimestamp(++indice, fechaact);
					insert.setBigDecimal(++indice, idop);
					insert.setBigDecimal(++indice, idempresa);
				}

				int i = insert.executeUpdate();
				if (i != 1)
					salida = "Imposible anular orden de produccion.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error SQL public String produccionMovProduAnulaOp(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String produccionMovProduAnulaOp(.....)"
							+ ex);
		}
		return salida;
	}

	// ALTA DE ORDENES DE PRODUCCION (PROCESO AUXILIAR).
	// para todo (ordena por el segundo campo por defecto)
	public List getProduccionprogramaauxAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idopaux,codigo_st,cantidad,fecha_prom,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM PRODUCCIONPROGRAMAAUX " + " WHERE idempresa = "
				+ idempresa.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getProduccionprogramaauxOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT  idopaux,codigo_st,cantidad,fecha_prom,usuarioalt,usuarioact,fechaalt,fechaact"
				+ "  FROM PRODUCCIONPROGRAMAAUX "
				+ " WHERE UPPER(CODIGO_ST) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%' AND idempresa = "
				+ idempresa.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getProduccionprogramaauxPK(BigDecimal idopaux,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT idopaux,codigo_st,cantidad,fecha_prom,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM PRODUCCIONPROGRAMAAUX WHERE idopaux="
				+ idopaux.toString() + " AND idempresa = "
				+ idempresa.toString();
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String produccionprogramaauxDelete(BigDecimal idopaux,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM PRODUCCIONPROGRAMAAUX WHERE idopaux="
				+ idopaux.toString() + " AND idempresa = "
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM PRODUCCIONPROGRAMAAUX WHERE idopaux="
						+ idopaux.toString() + " AND idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : produccionprogramaauxDelete( BigDecimal idopaux, BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: produccionprogramaauxDelete( BigDecimal idopaux, BigDecimal idempresa )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String produccionprogramaauxCreate(String codigo_st,
			Double cantidad, Timestamp fecha_prom, String usuarioalt,
			BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (codigo_st == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_st ";
		if (cantidad == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cantidad ";
		if (fecha_prom == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fecha_prom ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (codigo_st.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: codigo_st ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO PRODUCCIONPROGRAMAAUX(codigo_st, cantidad, fecha_prom, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, codigo_st);
				insert.setDouble(2, cantidad.doubleValue());
				insert.setTimestamp(3, fecha_prom);
				insert.setString(4, usuarioalt);
				insert.setBigDecimal(5, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String produccionprogramaauxCreate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String produccionprogramaauxCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String produccionprogramaauxCreateOrUpdate(BigDecimal idopaux,
			String codigo_st, Double cantidad, Timestamp fecha_prom,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idopaux == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idopaux ";
		if (codigo_st == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_st ";
		if (cantidad == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cantidad ";
		if (fecha_prom == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fecha_prom ";

		// 2. sin nada desde la pagina
		if (codigo_st.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: codigo_st ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM produccionprogramaaux WHERE idopaux = "
					+ idopaux.toString()
					+ " AND idempresa = "
					+ idempresa.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE PRODUCCIONPROGRAMAAUX SET codigo_st=?, cantidad=?, fecha_prom=?, usuarioact=?, fechaact=? WHERE idopaux=? AND idempresa =?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, codigo_st);
					insert.setDouble(2, cantidad.doubleValue());
					insert.setTimestamp(3, fecha_prom);
					insert.setString(4, usuarioact);
					insert.setTimestamp(5, fechaact);
					insert.setBigDecimal(6, idopaux);
					insert.setBigDecimal(7, idempresa);
				} else {
					String ins = "INSERT INTO PRODUCCIONPROGRAMAAUX(codigo_st, cantidad, fecha_prom, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, codigo_st);
					insert.setDouble(2, cantidad.doubleValue());
					insert.setTimestamp(3, fecha_prom);
					insert.setString(4, usuarioalt);
					insert.setBigDecimal(5, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String produccionprogramaauxCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String produccionprogramaauxCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String produccionprogramaauxUpdate(BigDecimal idopaux,
			String codigo_st, Double cantidad, Timestamp fecha_prom,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idopaux == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idopaux ";
		if (codigo_st == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_st ";
		if (cantidad == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cantidad ";
		if (fecha_prom == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fecha_prom ";

		// 2. sin nada desde la pagina
		if (codigo_st.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: codigo_st ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM produccionprogramaaux WHERE idopaux = "
					+ idopaux.toString()
					+ " AND idempresa = "
					+ idempresa.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE PRODUCCIONPROGRAMAAUX SET codigo_st=?, cantidad=?, fecha_prom=?, usuarioact=?, fechaact=? WHERE idopaux=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, codigo_st);
					insert.setDouble(2, cantidad.doubleValue());
					insert.setTimestamp(3, fecha_prom);
					insert.setString(4, usuarioact);
					insert.setTimestamp(5, fechaact);
					insert.setBigDecimal(6, idopaux);
					insert.setBigDecimal(7, idempresa);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error SQL public String produccionprogramaauxUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String produccionprogramaauxUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public long getTotalReceteDetalle(String codigo_st_cabe,
			BigDecimal idempresa) throws EJBException {

		/**
		 * Entidad: Total Registros de Una Entidad en Particular
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : recuperar total de registros.
		 */
		long total = 0l;
		ResultSet rsSalida = null;
		String cQuery = "SELECT count(1)AS total FROM PRODUCCIONRECETAS_DETA D, STOCKstock ST WHERE D.codigo_st = ST.codigo_st "
				+ "and D.codigo_st_cabe = '"
				+ codigo_st_cabe.toString()
				+ "' AND idempresa = " + idempresa.toString();
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				total = rsSalida.getLong("total");
			} else {
				log.warn("getTotalReceteDetalle()- Error al recuperar total: "
						+ codigo_st_cabe);
			}
		} catch (SQLException sqlException) {
			log.error("getTotalReceteDetalle()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getTotalReceteDetalle()- Salida por exception: " + ex);
		}
		return total;
	}

	/**
	 * Metodos para la entidad: vArticulosEsquemas Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Wed Feb 14 10:31:16 GMT-03:00 2007
	 */
	// para todo (ordena por el segundo campo por defecto)
	public List getVArticulosEsquemasAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT codigo,descripcion,tipo,descripciontipo,usuarioalt,usuarioact,fechaalt,fechaact,esserializable AS serializable, '' "
				+ "  FROM varticulosesquemas WHERE idempresa = "
				+ idempresa.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getVArticulosEsquemasOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT  codigo,descripcion,tipo,descripciontipo,usuarioalt,usuarioact,fechaalt,fechaact, esserializable AS serializable, '' "
				+ "  FROM varticulosesquemas WHERE ((codigo::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(descripcion) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%') AND idempresa = "
				+ idempresa.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: vLovArticulosProduccion Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Mon Feb 19 11:56:26 GMT-03:00 2007
	 * 
	 */
	// para todo (ordena por el segundo campo por defecto)
	public List getVLovArticulosProduccionAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT codigo_st,descrip_st,idesquema,esquema,codigo_dt,"
				// +
				// "       usuarioalt,usuarioact,fechaalt,fechaact,serializable,numeroserie "
				+ "       usuarioalt,usuarioact,fechaalt,fechaact,esserializable AS serializable, '' "
				+ "  FROM VLOVARTICULOSPRODUCCION WHERE idempresa = "
				+ idempresa.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getVLovArticulosProduccionOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT codigo_st,descrip_st,idesquema,esquema,codigo_dt,"
				+ "       usuarioalt,usuarioact,fechaalt,fechaact,esserializable AS serializable, '' "
				+ "  FROM VLOVARTICULOSPRODUCCION WHERE (UPPER(codigo_st) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(descrip_st) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%') AND idempresa= "
				+ idempresa.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: vCalculoNecesidadTotales Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Mon Feb 19 17:09:56 GMT-03:00 2007
	 * 
	 */
	// por primary key (primer campo por defecto)
	public List getVCalculoNecesidadTotalesPK(BigDecimal idesquema,
			BigDecimal cantidad, BigDecimal nivel, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idesquema,esquema,codigo,descripcion,tipo,descripciontipo,(costo)::numeric(18,2),(costo*cantidadinsume*?)::numeric(18,2) AS costototal,"
				+ "       cantidadinsume::numeric(18,2),(cantidadinsume*?)::numeric(18,2) AS cantidadtotalinsume,(cantiddeposito)::numeric(18,2) AS cantiddeposito,"
				+ "       (CASE WHEN tipo = 'A' "
				+ "            THEN cantiddeposito-(cantidadinsume*?) "
				+ "            ELSE cantiddeposito "
				+ "        END)::numeric(18,2) AS remanente, ? AS nivel, codigo_dt"
				+ "  FROM VCALCULONECESIDADTOTALES WHERE  idesquema=? AND idempresa=?";

		List vecSalida = new ArrayList();
		int indice = 0;
		try {

			PreparedStatement pstatement = dbconn.prepareStatement(cQuery);

			pstatement.setBigDecimal(++indice, cantidad);
			pstatement.setBigDecimal(++indice, cantidad);
			pstatement.setBigDecimal(++indice, cantidad);
			pstatement.setBigDecimal(++indice, nivel);
			pstatement.setBigDecimal(++indice, idesquema);
			pstatement.setBigDecimal(++indice, idempresa);

			rsSalida = pstatement.executeQuery();
			ResultSetMetaData md = rsSalida.getMetaData();
			while (rsSalida.next()) {
				int totCampos = md.getColumnCount() - 1;
				String[] sSalida = new String[totCampos + 1];
				int i = 0;
				while (i <= totCampos) {
					sSalida[i] = rsSalida.getString(++i);
				}
				vecSalida.add(sSalida);
			}
		} catch (SQLException sqlException) {
			log
					.error("Error SQL en el metodo : getVCalculoNecesidadTotalesPK( BigDecimal idesquema ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getVCalculoNecesidadTotalesPK( BigDecimal idesquema )  "
							+ ex);
		}
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: vCalculoNecesidadTotalesRegalos Copyrigth(r)
	 * sysWarp S.R.L. Fecha de creacion: Wed Nov 18 13:51:43 GMT-03:00 2010
	 * 
	 */
	// por primary key (primer campo por defecto)
	public List getVCalculoNecesidadTotalesRegalosPK(BigDecimal idesquema,
			BigDecimal cantidad, BigDecimal nivel, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;

		log.info("Llamada a getVCalculoNecesidadTotalesRegalosPK: ");

		String cQuery = ""
				+ "SELECT idesquema,esquema,codigo,descripcion,tipo,descripciontipo,(costo)::numeric(18,2),(costo*cantidadinsume*?)::numeric(18,2) AS costototal,"
				+ "       cantidadinsume::numeric(18,2),(cantidadinsume*?)::numeric(18,2) AS cantidadtotalinsume,(cantidreservadeposito)::numeric(18,2) AS cantidreservadeposito,"
				+ "       (CASE WHEN tipo = 'A' "
				+ "            THEN cantidreservadeposito-(cantidadinsume*?) "
				+ "            ELSE cantidreservadeposito "
				+ "        END)::numeric(18,2) AS remanente, ? AS nivel, codigo_dt"
				+ "  FROM vcalculonecesidadtotalesregalos WHERE  idesquema=? AND idempresa=?";

		List vecSalida = new ArrayList();
		int indice = 0;
		try {

			PreparedStatement pstatement = dbconn.prepareStatement(cQuery);

			pstatement.setBigDecimal(++indice, cantidad);
			pstatement.setBigDecimal(++indice, cantidad);
			pstatement.setBigDecimal(++indice, cantidad);
			pstatement.setBigDecimal(++indice, nivel);
			pstatement.setBigDecimal(++indice, idesquema);
			pstatement.setBigDecimal(++indice, idempresa);

			rsSalida = pstatement.executeQuery();
			ResultSetMetaData md = rsSalida.getMetaData();
			while (rsSalida.next()) {
				int totCampos = md.getColumnCount() - 1;
				String[] sSalida = new String[totCampos + 1];
				int i = 0;
				while (i <= totCampos) {
					sSalida[i] = rsSalida.getString(++i);
				}
				vecSalida.add(sSalida);
			}
		} catch (SQLException sqlException) {
			log
					.error("Error SQL en el metodo : getVCalculoNecesidadTotalesRegalosPK( BigDecimal idesquema ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getVCalculoNecesidadTotalesRegalosPK( BigDecimal idesquema )  "
							+ ex);
		}
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: vCalculoNecesidadTotales Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Mon Feb 19 17:09:56 GMT-03:00 2007.
	 * Descripcion: devuelve para un esquema todos los items que lo componen,
	 * recuperando de forma anidada los datos que correspondan a esquemas que
	 * compongan al esquema en cuestion.
	 */

	public List getRecursividadEsquema(BigDecimal idesquema,
			BigDecimal cantidad, int nivel, String tipoPedido,
			BigDecimal idempresa) throws EJBException {
		List auxList = new ArrayList();
		List retornoList = new ArrayList();
		List recursivaList = new ArrayList();
		Iterator auxIter;
		try {

			// EJV-20101117-Mantis-602 -->
			// Se agrego el parametro tipo de pedido, ya que los pedidos de
			// regalos empresarios (tipo 'R'), efectuan las validaciones contra
			// la resrva y no contra el disponible. Agregando este parametro y
			// la condicion, se logra un importante ahorro de codigo y
			// duplicidad del mismo.
			// auxList = getVCalculoNecesidadTotalesPK(idesquema, cantidad,
			// new BigDecimal(nivel), idempresa);

			if (tipoPedido.equalsIgnoreCase("N")) {
				auxList = getVCalculoNecesidadTotalesPK(idesquema, cantidad,
						new BigDecimal(nivel), idempresa);

			} else if (tipoPedido.equalsIgnoreCase("R")) {
				auxList = getVCalculoNecesidadTotalesRegalosPK(idesquema,
						cantidad, new BigDecimal(nivel), idempresa);
			}

			// <--

			auxIter = auxList.iterator();
			while (auxIter.hasNext()) {
				String[] datos = (String[]) auxIter.next();
				retornoList.add(datos);
				if (datos[4].equalsIgnoreCase("E")) {
					recursivaList = getRecursividadEsquema(new BigDecimal(
							datos[2]), cantidad, ++nivel, tipoPedido, idempresa);
					Iterator recursivaIter = recursivaList.iterator();
					while (recursivaIter.hasNext()) {
						retornoList.add(recursivaIter.next());
					}
				}
			}
		} catch (Exception e) {
			log.error("getRecursividadEsquema(....): " + e);
		}
		return retornoList;
	}

	/**
	 * Metodos para la entidad: vEsquemaProducto Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Wed Feb 21 16:58:59 GMT-03:00 2007
	 */
	// para todo (ordena por el segundo campo por defecto)
	public List getVEsquemaProductoAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idesquema,esquema,codigo_st,descrip_st,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM VESQUEMAPRODUCTO WHERE idempresa = "
				+ idempresa.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = new ArrayList();
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			ResultSetMetaData md = rsSalida.getMetaData();
			while (rsSalida.next()) {
				int totCampos = md.getColumnCount() - 1;
				String[] sSalida = new String[totCampos + 1];
				int i = 0;
				while (i <= totCampos) {
					sSalida[i] = rsSalida.getString(++i);
				}
				vecSalida.add(sSalida);
			}
		} catch (SQLException sqlException) {
			log.error("Error SQL en el metodo : getVEsquemaProducto() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getVEsquemaProducto()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getVEsquemaProductoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT idesquema,esquema,codigo_st,descrip_st,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM VESQUEMAPRODUCTO " + " where idempresa = "
				+ idempresa.toString() + " and (idesquema::VARCHAR LIKE '%"
				+ ocurrencia + "%' OR " + " esquema LIKE '%" + ocurrencia
				+ "%' OR " + " codigo_st LIKE '%" + ocurrencia + "%' OR "
				+ " UPPER(descrip_st) LIKE '%" + ocurrencia.toUpperCase()
				+ "%') " + " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset
				+ ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: produccionMovProdu_Deta Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Wed Feb 21 13:31:14 GMT-03:00 2007
	 * 
	 */
	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)
	public String produccionMovProdu_DetaDelete(BigDecimal idop,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM PRODUCCIONMOVPRODU_DETA WHERE idop="
				+ idop.toString() + " AND idempresa = " + idempresa.toString();
		String salida = "OK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM PRODUCCIONMOVPRODU_DETA WHERE idop="
						+ idop.toString() + " AND idempresa="
						+ idempresa.toString();
				statement.execute(cQuery);
			} else {
				salida = "Error: Registro inexistente";
				log
						.warn("produccionMovProdu_DetaDelete( BigDecimal idop, BigDecimal idempresa ): No existe detalle para la orden de produccion "
								+ idop);
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : produccionMovProdu_DetaDelete( BigDecimal idop, BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: produccionMovProdu_DetaDelete( BigDecimal idop, BigDecimal idempresa )  "
							+ ex);
		}
		return salida;
	}

	// por primary key (primer campo por defecto)

	public List getProduccionMovProdu_DetaPK(BigDecimal idop,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT mpd.idop,mpd.renglon,mpd.tipo,mpd.codigo,ae.descripcion,"
				+ "            mpd.cantidad_cal::NUMERIC(18) AS cantidad_cal,mpd.cantidad_rea::NUMERIC(18) AS cantidad_rea,mpd.cantidad_stb ::NUMERIC(18) AS cantidad_stb,"
				+ "            mpd.margen,mpd.entsal,mpd.codigo_dt,sd.descrip_dt,mpd.edita,mpd.fechaaltaorden,mpd.stockbis,mpd.abrcer,mpd.improd,"
				+ "            mpd.usuarioalt,mpd.usuarioact,mpd.fechaalt,mpd.fechaact "
				+ "  FROM produccionmovprodu_deta mpd"
				+ "           INNER JOIN varticulosesquemas ae ON mpd.codigo = ae.codigo AND mpd.idempresa=ae.idempresa  AND mpd.tipo = ae.tipo "
				+ "           INNER JOIN stockdepositos sd ON mpd.codigo_dt = sd.codigo_dt AND mpd.idempresa=sd.idempresa "
				+ " WHERE mpd.idop=" + idop.toString() + " AND mpd.idempresa="
				+ idempresa.toString()

		// + " AND mpd.idop NOT IN (SELECT idop FROM interfacesopregalos WHERE
		// idempresa = "
		// + idempresa + ") "

		;

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// Generacion del detalle de una orden de produccion a partir de un esquema

	public String produccionOrdenProdDetaCreate(BigDecimal idesquema,
			BigDecimal idop, BigDecimal cantidad_cal, Timestamp fechaaltaorden,
			String usuarioalt, BigDecimal idempresa) throws EJBException {
		String salida = "OK";
		int indice = 0;
		// validaciones de datos:
		// 1. nulidad de campos

		if (cantidad_cal == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cantidad_cal ";
		if (fechaaltaorden == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechaaltaorden ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		try {
			if (salida.equalsIgnoreCase("OK")) {
				String qEsquema = ""
						+ "SELECT ed.idesquema, ed.renglon, ed.tipo, ed.codigo_st, cantidad * ? AS cantidad_cal,"
						+ "       COALESCE(sb.canti_sb, 0) AS canti_sb, ed.margen_error , ed.entsal, ed.codigo_dt, ed.edita, CURRENT_DATE AS fechaaltaorden,"
						+ "       CASE WHEN ed.entsal = 'C' THEN 'S' ELSE NULL END AS stockbis, 'A' AS abrcer, ? "
						+ "  FROM produccionesquemas_deta ed "
						+ "       LEFT JOIN stockstockbis sb ON ed.codigo_st = sb.articu_sb AND ed.idempresa = sb.idempresa AND ed.codigo_dt = sb.deposi_sb  "
						+ " WHERE ed.idesquema = ?  AND ed.idempresa = ?";

				PreparedStatement pstmtEsq = dbconn.prepareStatement(qEsquema);
				pstmtEsq.setBigDecimal(++indice, cantidad_cal);
				pstmtEsq.setString(++indice, usuarioalt);
				pstmtEsq.setBigDecimal(++indice, idesquema);
				pstmtEsq.setBigDecimal(++indice, idempresa);
				ResultSet rsEsquema = pstmtEsq.executeQuery();

				while (rsEsquema.next()) {
					indice = 0;
					if (!rsEsquema.getString("tipo").equalsIgnoreCase("E")) {

						String ins = ""
								+ "INSERT INTO PRODUCCIONMOVPRODU_DETA"
								+ "  (idop, renglon, tipo, codigo, cantidad_cal, cantidad_rea, cantidad_stb, "
								+ "   margen, entsal, codigo_dt, edita, fechaaltaorden, stockbis, abrcer, improd, usuarioalt, idempresa ) "
								+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
						PreparedStatement insert = dbconn.prepareStatement(ins);
						// seteo de campos:
						insert.setBigDecimal(++indice, idop);
						insert.setBigDecimal(++indice, rsEsquema
								.getBigDecimal("renglon")); // renglon
						insert.setString(++indice, rsEsquema.getString("tipo")); // tipo
						insert.setString(++indice, rsEsquema
								.getString("codigo_st")); // codigo
						insert.setBigDecimal(++indice, rsEsquema
								.getBigDecimal("cantidad_cal"));// cantidad_cal
						insert.setBigDecimal(++indice, new BigDecimal(0));// cantidad_rea
						insert.setBigDecimal(++indice, rsEsquema
								.getBigDecimal("canti_sb")); // canti_sb
						insert.setBigDecimal(++indice, rsEsquema
								.getBigDecimal("margen_error")); // margen
						insert.setString(++indice, rsEsquema
								.getString("entsal")); // entsal
						insert.setBigDecimal(++indice, rsEsquema
								.getBigDecimal("codigo_dt")); // codigo_dt
						insert
								.setString(++indice, rsEsquema
										.getString("edita")); // edita
						insert.setTimestamp(++indice, fechaaltaorden);
						insert.setString(++indice, rsEsquema
								.getString("stockbis")); // stockbis
						insert.setString(++indice, rsEsquema
								.getString("abrcer")); // abrcer
						insert.setBigDecimal(++indice, new BigDecimal(0)); // improd
						insert.setString(++indice, usuarioalt);
						insert.setBigDecimal(++indice, idempresa);
						int n = insert.executeUpdate();
						if (n != 1)
							salida = "ERROR AL GENERAR DETALLE DE ORDEN DE PRODUCCION ...";
					} else {
						salida = produccionOrdenProdDetaCreate(rsEsquema
								.getBigDecimal("codigo_st"), idop,
								cantidad_cal, fechaaltaorden, usuarioalt,
								idempresa);

					}
					if (!salida.equalsIgnoreCase("OK"))
						break;
				}
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String produccionOrdenProdDetaCreate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String produccionOrdenProdDetaCreate(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * Metodo:getEstadoStockEsquema Autor: EJV. DATE:20060221.
	 * Descripcion:Metodo de validacion de existencias en stock de articulos que
	 * conforman un esquema. El mismo tiene impacto en la 'EXPLOSION' de una
	 * orden de produccion.
	 * 
	 */

	public Object getEstadoStockEsquema(BigDecimal idesquema,
			BigDecimal cantidad, int nivel, String tipoPedido,
			BigDecimal idempresa) throws EJBException {
		String salida = "";
		Object obj = null;
		List listaEsquema = new ArrayList();
		Iterator iterEsquema;
		try {

			listaEsquema = getRecursividadEsquema(idesquema, cantidad, nivel,
					tipoPedido, idempresa);
			iterEsquema = listaEsquema.iterator();

			while (iterEsquema.hasNext()) {
				String[] datos = (String[]) iterEsquema.next();
				log.info("getEstadoStockEsquema(TS)- " + datos[2] + " : "
						+ datos[11]);
				if (new BigDecimal(datos[11]).compareTo(new BigDecimal(0)) < 0) {
					salida += "Stock insuficiente para articulo " + datos[2]
							+ ": " + datos[11] + ".\n";
				}
			}

			if (salida.equals(""))
				obj = listaEsquema;
			else
				obj = salida;

		} catch (Exception e) {
			log.error("getEstadoStockEsquema()" + e);
		}
		return obj;
	}

	public String produccionGenerarExplosionOP(String[] idop,
			String[] cantidadparcial, String usuarioalt, BigDecimal idempresa)
			throws EJBException, SQLException {
		String salida = "OK";
		int i = 0;
		Object obj = null;
		BigDecimal nrointerno_ms = null;

		BigDecimal idordenproduccion = null;
		String articulo = "";
		BigDecimal deposito = null;
		BigDecimal cantidadMov = null;
		BigDecimal cantidadInsume = null;
		BigDecimal cantidadEstimada = null;
		BigDecimal cantidadParcialRealizada = null;

		BigDecimal cantidadArtDep = new BigDecimal(0);
		boolean existeArticuloDeposito = false;
		try {
			dbconn.setAutoCommit(false);
			for (i = 0; i < idop.length; i++) {
				idordenproduccion = new BigDecimal(idop[i]);
				List listaOP = getProduccionMovProduPK(idordenproduccion,
						idempresa);
				Iterator iterOP = listaOP.iterator();
				if (iterOP.hasNext()) {
					String[] datosOP = (String[]) iterOP.next();

					articulo = datosOP[9];
					deposito = new BigDecimal(datosOP[13]);
					cantidadEstimada = new BigDecimal(datosOP[5]);
					cantidadParcialRealizada = new BigDecimal(datosOP[4]);
					cantidadMov = new BigDecimal(cantidadparcial[i]);

					if (cantidadEstimada.compareTo(cantidadParcialRealizada
							.add(cantidadMov)) < 0) {
						salida = "Cantidades invalidas. Estimado ("
								+ cantidadParcialRealizada
								+ ")  menor a [Realizado("
								+ cantidadParcialRealizada + ")+ Ingresado:("
								+ cantidadMov + ")]";
						break;
					}

					nrointerno_ms = GeneralBean.getContador(new BigDecimal(5),
							idempresa, dbconn);
					// MOVIMIENTO DE ENTRADA ... PARA EL ARTICULO QUE REPRESENTA
					// EL ESQUEMA

					/*
					 * ([1]BigDecimal nrointerno_ms, [2] String sistema_ms,
					 * [3]String tipomov_ms, [4] BigDecimal comprob_ms,
					 * [5]Timestamp fechamov, [6]String articu_ms, [7]BigDecimal
					 * canti_ms, [8]BigDecimal moneda_ms, [9]Double cambio_ms,
					 * [10]Double venta_ms, [11]BigDecimal costo_ms, [12]String
					 * tipoaux_ms, [13]String destino_ms, [14] Double comis_ms,
					 * [15]BigDecimal remito_ms, [16]Double impint_ms,
					 * [17]Double impifl_ms, [18]Double impica_ms, [19]Double
					 * prelis_ms, [20]BigDecimal unidad_ms, [21]Double merma_ms,
					 * [22]Double saldo_ms, [23]BigDecimal medida_ms, [24]String
					 * observaciones, [25]String usuarioalt)
					 */
					// ------------------------------------------------
					existeArticuloDeposito = GeneralBean
							.getExisteArticuloDeposito(articulo, deposito,
									idempresa, dbconn);

					if (!existeArticuloDeposito) {
						salida = produccionStockBisCreate(articulo, deposito,
								cantidadMov, null, null, null, usuarioalt,
								idempresa);
					} else {
						salida = produccionStockBisUpdate(articulo, deposito,
								cantidadMov, null, null, null, usuarioalt,
								idempresa);
					}

					if (!salida.equalsIgnoreCase("OK"))
						break;

					salida = produccionMovStockCreate(nrointerno_ms, "U", "E",
							idordenproduccion, new Timestamp(Calendar
									.getInstance().getTimeInMillis()),
							articulo, cantidadMov, new BigDecimal(1),
							new Double(0), new Double(0), new BigDecimal(0),
							"", null, new Double(0), new BigDecimal(0),
							new Double(0), new Double(1), new Double(0),
							new Double(0), new BigDecimal(0), null, null,
							nrointerno_ms, "PRODUCCION", usuarioalt, idempresa);

					if (!salida.equalsIgnoreCase("OK"))
						break;

					salida = produccionStockHisCreate(nrointerno_ms, articulo,
							deposito, null, null, cantidadMov, null, null,
							null, usuarioalt, idempresa);

					if (!salida.equalsIgnoreCase("OK"))
						break;

					/*
					 * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> TODO: PENDIENTE
					 * DE PRUEBA !!!!!!!!!!!!! FECHA:
					 * 20071227........................ AUTOR:
					 * EJV............................. MOTIVO: Se estaban
					 * generando........... .......moviemientos de entrada y
					 * salida .......con el mismo nro. interno....... TODO:
					 * PENDIENTE DE PRUEBA !!!!!!!!!!!!!
					 * <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
					 */

					nrointerno_ms = GeneralBean.getContador(new BigDecimal(5),
							idempresa, dbconn);

					obj = getEstadoStockEsquema(new BigDecimal(datosOP[1]),
							cantidadMov, 1, "N", idempresa);

					if (obj instanceof List) {

						log.info("GenerarExplosionOP: INSTANCIA LIST");

						Iterator iterEsquema = ((List) obj).iterator();

						while (iterEsquema.hasNext()) {
							String[] datosEsquema = (String[]) iterEsquema
									.next();
							// MOVIMIENTO DE SALIDA ... PARA CADA ARTICULO DE
							// CONSUMO ASOCIADO AL
							// ESQUEMA

							articulo = datosEsquema[2];
							deposito = new BigDecimal(datosEsquema[13]);
							cantidadInsume = new BigDecimal(datosEsquema[9]);

							if (!datosEsquema[4].equalsIgnoreCase("A"))
								continue;
							// EJV 20070228
							// ------------------------------------------------
							cantidadArtDep = GeneralBean
									.getCantidadArticuloDeposito(articulo,
											deposito, idempresa, dbconn);
							// ------------------------------------------------
							existeArticuloDeposito = GeneralBean
									.getExisteArticuloDeposito(articulo,
											deposito, idempresa, dbconn);
							if (existeArticuloDeposito) {

								if (cantidadArtDep.compareTo(cantidadInsume) >= 0) {
									salida = produccionStockBisUpdate(articulo,
											deposito, cantidadInsume.negate(),
											null, null, null, usuarioalt,
											idempresa);

									if (!salida.equalsIgnoreCase("OK"))
										break;
									/*
									 * Por ahora es todo un mismo movimiento
									 * nrointerno_ms = GeneralBean.getContador(
									 * new BigDecimal(5), dbconn);
									 */

									salida = produccionMovStockCreate(
											nrointerno_ms, "U", "S",
											idordenproduccion,
											new Timestamp(Calendar
													.getInstance()
													.getTimeInMillis()),
											articulo, cantidadInsume,
											new BigDecimal(1), new Double(0),
											new Double(0), new BigDecimal(0),
											"", null, new Double(0),
											new BigDecimal(0), new Double(0),
											new Double(1), new Double(0),
											new Double(0), new BigDecimal(0),
											null, null, nrointerno_ms,
											"PRODUCCION", usuarioalt, idempresa);

									if (!salida.equalsIgnoreCase("OK"))
										break;

									salida = produccionStockHisCreate(
											nrointerno_ms, articulo, deposito,
											null, null, cantidadInsume, null,
											null, null, usuarioalt, idempresa);

									if (!salida.equalsIgnoreCase("OK"))
										break;

									salida = produccionCantReMovProdu_detaUpdate(
											idordenproduccion, articulo,
											cantidadInsume, usuarioalt,
											idempresa);

									if (!salida.equalsIgnoreCase("OK"))
										break;

								} else {
									salida = "Cantidad insuficiente art. "
											+ articulo + "  deposito "
											+ deposito + ".";
									break;
								}

							} else {
								salida = "Articulo " + articulo
										+ " inexistente en deposito "
										+ deposito;
								break;
							}

						}

					} else if (obj instanceof String) {

						log.info("GenerarExplosionOP: INSTANCIA STRING ");

						salida = obj.toString() + "Orden: " + idop[i];
						break;
					}

				} else {
					salida = "Imposible recuperar datos de la orden de produccion: "
							+ idop[i];
					break;
				}

				if (!salida.equalsIgnoreCase("OK"))
					break;

				salida = produccionCantReMovProduUpdate(idordenproduccion,
						cantidadMov, usuarioalt, idempresa);

				if (!salida.equalsIgnoreCase("OK"))
					break;
			}

		} catch (Exception e) {
			salida = "Imposible generar explosion de ordenes de proudccion.";
			log.error("produccionGenerarExplosionOP(): " + e);
		}

		if (!salida.equalsIgnoreCase("OK"))
			dbconn.rollback();
		else
			dbconn.commit();

		dbconn.setAutoCommit(true);
		return salida;
	}

	// EJV-20101117-Mantis-602 -->

	public String produccionGenerarExplosionOPRegalos(String[] idop,
			String[] cantidadparcial, String usuarioalt, BigDecimal idempresa)
			throws EJBException, SQLException {
		String salida = "OK";
		int i = 0;
		Object obj = null;
		BigDecimal nrointerno_ms = null;

		BigDecimal idordenproduccion = null;
		String articulo = "";
		BigDecimal deposito = null;
		BigDecimal cantidadMov = null;
		BigDecimal cantidadInsume = null;
		BigDecimal cantidadEstimada = null;
		BigDecimal cantidadParcialRealizada = null;

		log.info("INICIA EXPLOSION DESDE REGALOS.");

		BigDecimal cantidadEnReservaArtDep = new BigDecimal(0);
		boolean existeArticuloDeposito = false;
		try {
			dbconn.setAutoCommit(false);
			for (i = 0; i < idop.length; i++) {
				idordenproduccion = new BigDecimal(idop[i]);
				List listaOP = getProduccionMovProduPK(idordenproduccion,
						idempresa);
				Iterator iterOP = listaOP.iterator();
				if (iterOP.hasNext()) {
					String[] datosOP = (String[]) iterOP.next();

					articulo = datosOP[9];
					deposito = new BigDecimal(datosOP[13]);
					cantidadEstimada = new BigDecimal(datosOP[5]);
					cantidadParcialRealizada = new BigDecimal(datosOP[4]);
					cantidadMov = new BigDecimal(cantidadparcial[i]);

					if (cantidadEstimada.compareTo(cantidadParcialRealizada
							.add(cantidadMov)) < 0) {
						salida = "Cantidades invalidas. Estimado ("
								+ cantidadParcialRealizada
								+ ")  menor a [Realizado("
								+ cantidadParcialRealizada + ")+ Ingresado:("
								+ cantidadMov + ")]";
						break;
					}

					nrointerno_ms = GeneralBean.getContador(new BigDecimal(5),
							idempresa, dbconn);
					// MOVIMIENTO DE ENTRADA ... PARA EL ARTICULO QUE REPRESENTA
					// EL ESQUEMA

					/*
					 * ([1]BigDecimal nrointerno_ms, [2] String sistema_ms,
					 * [3]String tipomov_ms, [4] BigDecimal comprob_ms,
					 * [5]Timestamp fechamov, [6]String articu_ms, [7]BigDecimal
					 * canti_ms, [8]BigDecimal moneda_ms, [9]Double cambio_ms,
					 * [10]Double venta_ms, [11]BigDecimal costo_ms, [12]String
					 * tipoaux_ms, [13]String destino_ms, [14] Double comis_ms,
					 * [15]BigDecimal remito_ms, [16]Double impint_ms,
					 * [17]Double impifl_ms, [18]Double impica_ms, [19]Double
					 * prelis_ms, [20]BigDecimal unidad_ms, [21]Double merma_ms,
					 * [22]Double saldo_ms, [23]BigDecimal medida_ms, [24]String
					 * observaciones, [25]String usuarioalt)
					 */
					// ------------------------------------------------
					existeArticuloDeposito = GeneralBean
							.getExisteArticuloDeposito(articulo, deposito,
									idempresa, dbconn);

					if (!existeArticuloDeposito) {
						salida = produccionStockBisCreate(articulo, deposito,
								cantidadMov, null, null, null, usuarioalt,
								idempresa);
					} else {
						salida = produccionStockBisUpdate(articulo, deposito,
								cantidadMov, null, null, null, usuarioalt,
								idempresa);
					}

					if (!salida.equalsIgnoreCase("OK"))
						break;

					salida = produccionMovStockCreate(nrointerno_ms, "U", "E",
							idordenproduccion, new Timestamp(Calendar
									.getInstance().getTimeInMillis()),
							articulo, cantidadMov, new BigDecimal(1),
							new Double(0), new Double(0), new BigDecimal(0),
							"", null, new Double(0), new BigDecimal(0),
							new Double(0), new Double(1), new Double(0),
							new Double(0), new BigDecimal(0), null, null,
							nrointerno_ms, "PRODUCCION[" + idordenproduccion
									+ "]-REGALOS", usuarioalt, idempresa);

					if (!salida.equalsIgnoreCase("OK"))
						break;

					salida = produccionStockHisCreate(nrointerno_ms, articulo,
							deposito, null, null, cantidadMov, null, null,
							null, usuarioalt, idempresa);

					if (!salida.equalsIgnoreCase("OK"))
						break;

					/*
					 * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
					 * TODO:.PENDIENTE.DE PRUEBA.!!!!!!!!!!!!!
					 * FECHA:.20071227.................................
					 * AUTOR:.EJV...........................................
					 * MOTIVO:Se estaban.generando.moviemientos de entrada.y.
					 * salida.con el mismo nro. interno.......
					 * TODO:PENDIENTE.DE.PRUEBA.!!!!!!!!!!!!!
					 * <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
					 */

					nrointerno_ms = GeneralBean.getContador(new BigDecimal(5),
							idempresa, dbconn);

					obj = getEstadoStockEsquema(new BigDecimal(datosOP[1]),
							cantidadMov, 1, "R", idempresa);

					if (obj instanceof List) {

						log.info("GenerarExplosionOP: INSTANCIA LIST");

						Iterator iterEsquema = ((List) obj).iterator();

						while (iterEsquema.hasNext()) {
							String[] datosEsquema = (String[]) iterEsquema
									.next();
							// MOVIMIENTO DE SALIDA ... PARA CADA ARTICULO DE
							// CONSUMO ASOCIADO AL
							// ESQUEMA

							articulo = datosEsquema[2];
							deposito = new BigDecimal(datosEsquema[13]);
							cantidadInsume = new BigDecimal(datosEsquema[9]);

							if (!datosEsquema[4].equalsIgnoreCase("A"))
								continue;

							// EJV-20101117-Mantis-602-->
							// EJV 20070228
							// ------------------------------------------------
							// cantidadEnReservaArtDep = GeneralBean
							// .getCantidadArticuloDeposito(articulo,
							// deposito, idempresa, dbconn);
							// ------------------------------------------------
							cantidadEnReservaArtDep = GeneralBean
									.getCantidadReservaArticuloDeposito(
											articulo, deposito, idempresa,
											dbconn);

							// <--

							existeArticuloDeposito = GeneralBean
									.getExisteArticuloDeposito(articulo,
											deposito, idempresa, dbconn);
							if (existeArticuloDeposito) {

								if (cantidadEnReservaArtDep
										.compareTo(cantidadInsume) >= 0) {

									// EJV-20101117-Mantis-602-->
									// salida =
									// produccionStockBisUpdate(articulo,
									// deposito, cantidadInsume.negate(),
									// null, null, null, usuarioalt,
									// idempresa);
									salida = StockBean
											.stockStockBisPedid_sbUpdate(
													articulo, deposito,
													new BigDecimal(0), null,
													null, cantidadInsume.negate(),
													dbconn, usuarioalt,
													idempresa);
									// <--

									if (!salida.equalsIgnoreCase("OK"))
										break;
									/*
									 * Por ahora es todo un mismo movimiento
									 * nrointerno_ms = GeneralBean.getContador(
									 * new BigDecimal(5), dbconn);
									 */

									salida = produccionMovStockCreate(
											nrointerno_ms, "U", "S",
											idordenproduccion,
											new Timestamp(Calendar
													.getInstance()
													.getTimeInMillis()),
											articulo, cantidadInsume,
											new BigDecimal(1), new Double(0),
											new Double(0), new BigDecimal(0),
											"", null, new Double(0),
											new BigDecimal(0), new Double(0),
											new Double(1), new Double(0),
											new Double(0), new BigDecimal(0),
											null, null, nrointerno_ms,
											"PRODUCCION[" + idordenproduccion
													+ "]-REGALOS", usuarioalt,
											idempresa);

									if (!salida.equalsIgnoreCase("OK"))
										break;

									salida = produccionStockHisCreate(
											nrointerno_ms, articulo, deposito,
											null, null, cantidadInsume, null,
											null, null, usuarioalt, idempresa);

									if (!salida.equalsIgnoreCase("OK"))
										break;

									salida = produccionCantReMovProdu_detaUpdate(
											idordenproduccion, articulo,
											cantidadInsume, usuarioalt,
											idempresa);

									if (!salida.equalsIgnoreCase("OK"))
										break;

								} else {

									// salida = "Cantidad ("
									// + cantidadEnReservaArtDep
									// + ") en reserva insuficiente art. "
									// + articulo + "  deposito "
									// + deposito + ".";

									salida = "Existencia en reserva ["
											+ cantidadEnReservaArtDep
											+ "] de articulo [ "
											+ articulo
											+ "] en deposito ["
											+ deposito
											+ "], inferior a la cantidad que insume ["
											+ cantidadInsume + "].";

									break;
								}

							} else {
								salida = "Articulo " + articulo
										+ " inexistente en deposito "
										+ deposito;
								break;
							}

						}

					} else if (obj instanceof String) {

						log
								.info("GenerarExplosionOPREGALOS: INSTANCIA STRING ");

						salida = obj.toString() + "Orden: " + idop[i];
						break;
					}

				} else {
					salida = "Imposible recuperar datos de la orden de produccion: "
							+ idop[i];
					break;
				}

				if (!salida.equalsIgnoreCase("OK"))
					break;

				salida = produccionCantReMovProduUpdate(idordenproduccion,
						cantidadMov, usuarioalt, idempresa);

				if (!salida.equalsIgnoreCase("OK"))
					break;
			}

		} catch (Exception e) {
			salida = "Imposible generar explosion de ordenes de proudccion para regalos.";
			log.error("produccionGenerarExplosionOPRegalos(): " + e);
		}

		log.info("FINALIZA EXPLOSION DESDE REGALOS.");

		if (!salida.equalsIgnoreCase("OK"))
			dbconn.rollback();
		else
			dbconn.commit();

		dbconn.setAutoCommit(true);
		return salida;
	}

	// <--

	public String produccionMovStockCreate(BigDecimal nrointerno_ms,
			String sistema_ms, String tipomov_ms, BigDecimal comprob_ms,
			Timestamp fechamov, String articu_ms, BigDecimal canti_ms,
			BigDecimal moneda_ms, Double cambio_ms, Double venta_ms,
			BigDecimal costo_ms, String tipoaux_ms, String destino_ms,
			Double comis_ms, BigDecimal remito_ms, Double impint_ms,
			Double impifl_ms, Double impica_ms, Double prelis_ms,
			BigDecimal unidad_ms, Double merma_ms, Double saldo_ms,
			BigDecimal medida_ms, String observaciones, String usuarioalt,
			BigDecimal idempresa) throws EJBException {
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos

		if (sistema_ms == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: sistema_ms ";
		if (tipomov_ms == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipomov_ms ";
		if (canti_ms == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: canti_ms ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (sistema_ms.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: sistema_ms ";
		if (tipomov_ms.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipomov_ms ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones

		try {
			if (salida.equalsIgnoreCase("OK")) {
				String qDML = "";

				qDML = "INSERT INTO stockmovstock (nrointerno_ms,sistema_ms, tipomov_ms, comprob_ms, fecha_ms, articu_ms, canti_ms,";
				qDML += " moneda_ms,cambio_ms,venta_ms,costo_ms,tipoaux_ms,destino_ms,";
				qDML += " comis_ms,remito_ms,impint_ms,impifl_ms,impica_ms,prelis_ms,";
				qDML += " unidad_ms,merma_ms,saldo_ms,medida_ms,observaciones,usuarioalt, idempresa)";
				qDML += " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				PreparedStatement statement = dbconn.prepareStatement(qDML);
				// seteo de campos:
				statement.setBigDecimal(1, nrointerno_ms);
				statement.setString(2, sistema_ms);
				// TODO: Discriminar si entra o sale.
				statement.setString(3, tipomov_ms);
				statement.setBigDecimal(4, comprob_ms);
				statement.setTimestamp(5, fechamov);
				statement.setString(6, articu_ms);
				statement.setBigDecimal(7, canti_ms);
				// HARCODE POR FUENTE
				statement.setInt(8, 1);
				statement.setInt(9, 1);
				statement.setBigDecimal(10, new BigDecimal(0));
				statement.setBigDecimal(11, costo_ms);
				statement.setString(12, tipoaux_ms);
				statement.setString(13, destino_ms);
				statement.setBigDecimal(14, new BigDecimal("0"));
				statement.setBigDecimal(15, new BigDecimal("0"));
				statement.setBigDecimal(16, new BigDecimal("0"));
				statement.setBigDecimal(17, new BigDecimal("0"));
				statement.setBigDecimal(18, new BigDecimal("0"));
				statement.setBigDecimal(19, new BigDecimal("0"));
				statement.setBigDecimal(20, new BigDecimal("0"));
				statement.setBigDecimal(21, new BigDecimal("0"));
				statement.setBigDecimal(22, new BigDecimal("0"));
				statement.setBigDecimal(23, new BigDecimal("0"));
				statement.setString(24, observaciones);
				statement.setString(25, usuarioalt);
				statement.setBigDecimal(26, idempresa);
				int n = statement.executeUpdate();
				if (n != 1)
					salida = "Imposible generar movimiento de stock.";
			}

		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String produccionMovStockCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String produccionMovStockCreate(.....)"
							+ ex);
		}
		log.info("produccionMovStockCreate: " + salida);
		return salida;
	}

	public String produccionStockBisCreate(String articu_sb,
			BigDecimal deposi_sb, BigDecimal canti_sb, String serie_sb,
			String despa_sb, BigDecimal pedid_sb, String usuarioalt,
			BigDecimal idempresa) throws EJBException {
		String salida = "OK";
		String qDML = "";
		int n = -1;
		// validaciones de datos:
		// 1. nulidad de campos
		if (deposi_sb == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: deposi_sb ";
		if (canti_sb == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: canti_sb ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		try {
			if (salida.equalsIgnoreCase("OK")) {

				PreparedStatement statement;
				qDML = "INSERT INTO stockstockbis (articu_sb,deposi_sb,canti_sb,serie_sb,despa_sb,pedid_sb,usuarioalt, idempresa)";
				qDML += " VALUES (?,?,?,?,?,?,?,?)";
				statement = dbconn.prepareStatement(qDML);
				statement.clearParameters();
				statement.setString(1, articu_sb);
				statement.setBigDecimal(2, deposi_sb);
				statement.setBigDecimal(3, canti_sb);
				statement.setString(4, null);
				statement.setString(5, null);
				statement.setBigDecimal(6, new BigDecimal("0"));
				statement.setString(7, usuarioalt);
				statement.setBigDecimal(8, idempresa);
				n = statement.executeUpdate();
				if (n != 1)
					salida = "Imposible actualizar cantidad en dep�sito.";

			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String produccionStockBisCreate(.....)"
					+ sqlException);
			n = -2;
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String produccionStockBisCreate(.....)"
							+ ex);

		}
		log.info("produccionStockBisCreate:" + salida);
		return salida;
	}

	public String produccionStockBisUpdate(String articu_sb,
			BigDecimal deposi_sb, BigDecimal canti_sb, String serie_sb,
			String despa_sb, BigDecimal pedid_sb, String usuarioact,
			BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "OK";
		String qDML = "";
		// validaciones de datos:
		// 1. nulidad de campos
		if (articu_sb == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: articu_sb ";
		if (deposi_sb == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: deposi_sb ";
		if (canti_sb == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: canti_sb ";

		// 2. sin nada desde la pagina
		if (articu_sb.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: articu_sb ";
		// fin validaciones

		try {

			PreparedStatement statement = null;
			if (salida.equalsIgnoreCase("OK")) {

				qDML = "UPDATE stockstockbis ";
				qDML += "   SET canti_sb=( canti_sb +( ? )),serie_sb=?,despa_sb=?,pedid_sb=pedid_sb+?,usuarioact=?,fechaact=?";
				qDML += " WHERE articu_sb=? AND deposi_sb=? AND idempresa=? ";
				statement = dbconn.prepareStatement(qDML);
				statement.clearParameters();

				statement.clearParameters();
				statement.setBigDecimal(1, canti_sb);
				statement.setString(2, null);
				statement.setString(3, null);
				statement.setBigDecimal(4, new BigDecimal("0"));
				statement.setString(5, usuarioact);
				statement.setTimestamp(6, fechaact);
				statement.setString(7, articu_sb);
				statement.setBigDecimal(8, deposi_sb);
				statement.setBigDecimal(9, idempresa);

				int i = statement.executeUpdate();
				if (i != 1) {
					salida = "Imposible actualizar stock en deposito.";
					log.warn("filas     : " + i);
					log.warn("deposi_sb : " + deposi_sb);
					log.warn("canti_sb  : " + canti_sb);
					log.warn("articu_sb : " + articu_sb);
				}

			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String produccionStockBisUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String produccionStockBisUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String produccionStockHisCreate(BigDecimal nromov_sh,
			String articu_sh, BigDecimal deposi_sh, String serie_sh,
			String despa_sh, BigDecimal canti_sh, String estamp1_sh,
			String estamp2_sh, String aduana_sh, String usuarioalt,
			BigDecimal idempresa) throws EJBException {
		String salida = "OK";
		String qDML = "";
		PreparedStatement statement;
		// validaciones de datos:
		// 1. nulidad de campos
		if (articu_sh == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: articu_sh ";
		if (deposi_sh == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: deposi_sh ";
		if (canti_sh == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: canti_sh ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (articu_sh.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: articu_sh ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones

		try {
			if (salida.equalsIgnoreCase("OK")) {
				// INSERTAR STOCKHIS
				qDML = "INSERT INTO stockhis (nromov_sh,articu_sh,deposi_sh,serie_sh,despa_sh, ";
				qDML += " canti_sh,estamp1_sh,estamp2_sh,aduana_sh,usuarioalt,idempresa) ";
				qDML += " VALUES (?,?,?,?,?,?,?,?,?,?,?)";

				statement = dbconn.prepareStatement(qDML);
				statement.clearParameters();
				statement.setBigDecimal(1, nromov_sh);
				statement.setString(2, articu_sh);
				statement.setBigDecimal(3, deposi_sh);
				statement.setString(4, null);
				statement.setString(5, null);
				statement.setBigDecimal(6, canti_sh);
				statement.setString(7, null);
				statement.setString(8, null);
				statement.setString(9, null);
				statement.setString(10, usuarioalt);
				statement.setBigDecimal(11, idempresa);
				int n = statement.executeUpdate();
				if (n != 1)
					salida = "Imposible generar hist�rico.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String produccionStockHisCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String produccionStockHisCreate(.....)"
							+ ex);
		}
		log.info("stockHisCreate:" + salida);
		return salida;
	}

	public String produccionCantReMovProduUpdate(BigDecimal idop,
			BigDecimal cantre_op, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "OK";
		String qDML = "";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idop == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: articu_sb ";

		if (cantre_op == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cantre_op ";

		// fin validaciones

		try {

			PreparedStatement statement = null;
			if (salida.equalsIgnoreCase("OK")) {

				qDML = "UPDATE produccionmovprodu ";
				qDML += "   SET cantre_op=( cantre_op +( ? )),usuarioact=?,fechaact=?";
				qDML += " WHERE idop=?  AND idempresa=? ";
				statement = dbconn.prepareStatement(qDML);

				statement.clearParameters();
				statement.setBigDecimal(1, cantre_op);
				statement.setString(2, usuarioact);
				statement.setTimestamp(3, fechaact);
				statement.setBigDecimal(4, idop);
				statement.setBigDecimal(5, idempresa);

				int i = statement.executeUpdate();
				if (i != 1)
					salida = "Imposible actualizar cantidad realizada en orden de produccion[cabecera].";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error SQL public String produccionCantReMovProduUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String produccionCantReMovProduUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String produccionCantReMovProdu_detaUpdate(BigDecimal idop,
			String codigo, BigDecimal cantidad_rea, String usuarioact,
			BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "OK";
		String qDML = "";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idop == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: articu_sb ";

		if (cantidad_rea == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cantre_op ";

		// fin validaciones

		try {

			PreparedStatement statement = null;
			if (salida.equalsIgnoreCase("OK")) {

				qDML = "UPDATE produccionmovprodu_deta ";
				qDML += "   SET cantidad_rea =( cantidad_rea +( ? )),usuarioact=?,fechaact=?";
				qDML += " WHERE idop=?  AND codigo=? AND idempresa=?";
				statement = dbconn.prepareStatement(qDML);

				statement.clearParameters();
				statement.setBigDecimal(1, cantidad_rea);
				statement.setString(2, usuarioact);
				statement.setTimestamp(3, fechaact);
				statement.setBigDecimal(4, idop);
				statement.setString(5, codigo);
				statement.setBigDecimal(6, idempresa);

				log.info(qDML);
				log.info("cantidad_rea: " + cantidad_rea);
				log.info("idop: " + idop);
				log.info("codigo: " + codigo);
				log.info("idempresa: " + idempresa);
				int i = statement.executeUpdate();
				if (i != 1)
					salida = "Imposible actualizar cantidad realizada en orden de produccion[detalle].";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error SQL public String produccionCantReMovProdu_detaUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String produccionCantReMovProdu_detaUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * AUTOR: EJV ............................................................
	 * FECHA: 20071213 .......................................................
	 * MOTIVO:DESARMADO DE PRODUCTOS ELABORADOS...............................
	 */

	public List getEsquemasProductoFinal(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT ec.idesquema, ec.esquema, ec.observaciones, st.codigo_st, st.descrip_st,"
				+ "       ec.usuarioalt, ec.usuarioact, ec.fechaalt, ec.fechaact, st.id_indi_st AS serializable, ''  "
				+ "  FROM produccionesquemas_cabe ec "
				+ "       INNER JOIN produccionesquemas_deta ed ON ec.idesquema = ed.idesquema "
				+ "         AND ec.idempresa = ed.idempresa AND ed.entsal = 'P' "
				+ "       INNER JOIN stockstock st ON  ed.codigo_st = st.codigo_st AND ed.idempresa = st.idempresa "
				+ " WHERE ec.idempresa = " + idempresa.toString()
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// --------------------------------------------------------------

	public List getEsquemasProductoFinalOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT ec.idesquema, ec.esquema, ec.observaciones, st.codigo_st, st.descrip_st,"
				+ "       ec.usuarioalt, ec.usuarioact, ec.fechaalt, ec.fechaact, st.id_indi_st AS serializable, ''  "
				+ "  FROM produccionesquemas_cabe ec "
				+ "       INNER JOIN produccionesquemas_deta ed ON ec.idesquema = ed.idesquema "
				+ "         AND ec.idempresa = ed.idempresa AND ed.entsal = 'P' "
				+ "       INNER JOIN stockstock st ON  ed.codigo_st = st.codigo_st AND ed.idempresa = st.idempresa "
				+ " WHERE ec.idempresa = " + idempresa.toString()
				+ " AND st.codigo_st LIKE '%" + ocurrencia.toUpperCase() + "%'"
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// --------------------------------------------------------------

	public List getOrdenProduccionAnular(BigDecimal idop, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""

				+ "SELECT oc.idop, oc.idesquema, ec.esquema, oc.codigo_st, st.descrip_st, od.codigo_dt, de.descrip_dt, oc.cantre_op, oc.cantest_op, oc.fbaja  "
				+ "  FROM produccionmovprodu oc  "
				+ "       INNER JOIN produccionmovprodu_deta od ON oc.idop = od.idop AND oc.idempresa = od.idempresa AND od.entsal = 'P'  "
				+ "       INNER JOIN produccionesquemas_cabe ec ON oc.idesquema = ec.idesquema AND oc.idesquema = ec.idesquema AND oc.idempresa = ec.idempresa  "
				+ "       INNER JOIN produccionesquemas_deta ed ON ec.idesquema = ed.idesquema AND ed.idempresa = ec.idempresa  "
				+ "              AND od.codigo = ed.codigo_st AND od.entsal =  ed.entsal    "
				+ "       INNER JOIN stockdepositos de ON od.codigo_dt = de.codigo_dt AND od.idempresa = de.idempresa  "
				+ "       INNER JOIN stockstock st ON od.codigo  = st.codigo_st AND od.idempresa = st.idempresa  "
				+ "	WHERE oc.idop = " + idop.toString()
				+ "   AND oc.idempresa = " + idempresa.toString();

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getConsulataEsquema(String esquemadesde, BigDecimal idempresa)
			throws EJBException {
		/*
		 * Objetivo: Traer el saldo anterior a la fecha desde para un producto
		 * en el informe solicitado
		 */
		String cQuery = "" + " select * from vconsultaesquemadetalle "
				+ " where idesquema " + "  between " + esquemadesde + " and "
				+ esquemadesde + " and idempresa = " + idempresa.toString();
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// lov esquemas
	public List getEsquemasLovAll(long limit, long offset, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT idesquema,esquema,usuarioalt,usuarioact,fechaalt,fechaact from produccionesquemas_cabe"
				+ " WHERE idempresa = " + idempresa.toString()
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getEsquemasLovOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT idesquema,esquema,usuarioalt,usuarioact,fechaalt,fechaact from produccionesquemas_cabe "
				+ " WHERE ((idesquema::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(esquema) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%') AND idempresa= "
				+ idempresa.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getOPs(String articulo, BigDecimal idempresa)
			throws EJBException {
		/*
		 * Objetivo: Consulta correspondiente al mantis 502 (mostrar oP donde
		 * figue un determinado producto.
		 */
		String cQuery = ""
				+ "SELECT cabe.idop AS op, cabe.idesquema, "
				+ "       CASE WHEN deta.entsal = 'P' THEN 'PRODUCCION' ELSE 'CONSUMO' END AS tipo_producto,"
				+ "       deta.cantidad_cal::NUMERIC(18,2) AS cantidad_calculada, "
				+ "       deta.cantidad_rea::NUMERIC(18,2) AS cantidad_realizada,"
				+ "       deta.cantidad_stb::NUMERIC(18,2) AS cantidad_stock,"
				+ "       TO_CHAR(deta.fechaaltaorden ,'dd/mm/yyyy') AS fechaaltaorden,"
				+ "       COALESCE(cabe.idcliente,0) AS idcliente,"
				+ "       COALESCE(clientes.razon, 'NO TIENE ASOCIACION') AS cliente,"
				+ "       cabe.cantre_op::NUMERIC(18,2) AS reservada,"
				+ "       cabe.cantest_op::NUMERIC(18,2) AS estimada,"
				+ "       TO_CHAR( fecha_prometida ,'dd/mm/yyyy') AS fecha_prometida,"
				+ "       TO_CHAR( fecha_emision ,'dd/mm/yyyy') AS fecha_emision,"
				+ "       cabe.observaciones "
				+ "  FROM produccionmovprodu_deta deta "
				+ "       INNER JOIN stockstock stock ON (deta.codigo = stock.codigo_st AND deta.idempresa = stock.idempresa ) "
				+ "       INNER JOIN produccionmovprodu cabe ON (deta.idop = cabe.idop AND deta.idempresa = cabe.idempresa) "
				+ "        LEFT JOIN clientesclientes clientes ON ( cabe.idcliente = clientes.idcliente AND cabe.idempresa = clientes.idempresa ) "
				+ "  WHERE cabe.fbaja IS NULL  " + "    AND deta.codigo = '"
				+ articulo + "' " + "    AND cabe.idempresa = " + idempresa
				+ " order by 1; ";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// EJV - 20101115 - MANTIS 602

	public List getProduccionMovProduRegalosAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT mp.idop,mp.idesquema,mp.idcliente,mp.cantre_op,mp.cantest_op,mp.fecha_prometida,mp.fecha_emision,"
				+ "       mp.observaciones,mp.codigo_st,st.descrip_st,mp.idcontador,mp.nrointerno,"
				+ "       CASE  WHEN fbaja IS NOT NULL "
				+ "             THEN 'ANULADA'"
				+ "             ELSE "
				+ "             CASE "
				+ "                  WHEN mp.cantre_op = 0 THEN 'PENDIENTE' "
				+ "                  WHEN mp.cantre_op < mp.cantest_op THEN 'EN PROCESO' "
				+ "                  WHEN mp.cantre_op >= mp.cantest_op THEN 'FINALIZADA' "
				+ "             END"
				+ "       END AS estado, "
				+ "       mp.idpedido_regalos_cabe, mp.usuarioalt,mp.usuarioact,mp.fechaalt,mp.fechaact "
				+ "  FROM produccionmovprodu mp "
				+ "       INNER JOIN stockstock st ON mp.codigo_st = st.codigo_st AND mp.idempresa = st.idempresa"
				+ " WHERE mp.idempresa = "
				+ idempresa.toString()
				+ "   AND mp.idop NOT IN (SELECT idop FROM interfacesopregalos WHERE idempresa = "
				+ idempresa
				+ ") AND mp.idpedido_regalos_cabe IS NOT NULL ORDER BY 1 LIMIT "
				+ limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getProduccionMovProduRegalosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT mp.idop,mp.idesquema,mp.idcliente,mp.cantre_op,mp.cantest_op,mp.fecha_prometida,mp.fecha_emision,"
				+ "       mp.observaciones,mp.codigo_st,st.descrip_st,mp.idcontador,mp.nrointerno,"
				+ "       CASE  WHEN fbaja IS NOT NULL "
				+ "             THEN 'ANULADA'"
				+ "             ELSE "
				+ "             CASE "
				+ "                  WHEN mp.cantre_op = 0 THEN 'PENDIENTE' "
				+ "                  WHEN mp.cantre_op < mp.cantest_op THEN 'EN PROCESO' "
				+ "                  WHEN mp.cantre_op >= mp.cantest_op THEN 'FINALIZADA' "
				+ "             END"
				+ "       END AS estado, "
				+ "       mp.idpedido_regalos_cabe, mp.usuarioalt,mp.usuarioact,mp.fechaalt,mp.fechaact "
				+ "  FROM produccionmovprodu mp "
				+ "       INNER JOIN stockstock st ON mp.codigo_st = st.codigo_st AND mp.idempresa = st.idempresa"
				+ " WHERE (UPPER(mp.idop::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(st.codigo_st) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%') AND st.idempresa = "
				+ idempresa.toString()
				+ "   AND mp.idop NOT IN (SELECT idop FROM interfacesopregalos WHERE idempresa = "
				+ idempresa
				+ ")  AND mp.idpedido_regalos_cabe IS NOT NULL ORDER BY 1 LIMIT "
				+ limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

}