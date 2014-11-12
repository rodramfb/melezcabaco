package ar.com.syswarp.ejb;

import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.ejb.CreateException;
import javax.ejb.Stateless;
import javax.naming.Context;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.sql.Date;

import org.postgresql.*; //import javax.sql.*;
import java.util.*;

import org.apache.log4j.*;
import org.postgresql.jdbc2.TimestampUtils;

import ar.com.syswarp.db.Postgres;
import ar.com.syswarp.ejb.*;

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
 * @ejb.bean name="Contable" display-name="Name for Contable"
 *           description="Description for Contable" jndi-name="ejb/Contable"
 *           type="Stateless" view-type="remote"
 */
@Stateless
public class ContableBean implements Contable {

	/** The session context */
	private SessionContext context;

	/* conexion a la base de datos */

	private Connection dbconn;;

	static Logger log = Logger.getLogger(ContableBean.class);

	private Connection conexion;

	private Properties props;

	private String url;

	private String clase;

	private String usuario;

	private String clave;

	public ContableBean() {
		super();
		try {
			props = new Properties();
			props.load(ContableBean.class
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

	public ContableBean(Connection dbconn) {
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

	}

	// -- resumir los Arraylist.
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

	/**
	 * Reglas de negocio contable:
	 * 
	 * @ejb.interface-method view-type = "remote"
	 * 
	 * @throws EJBException
	 *             Thrown if method fails due to system-level error. Entidades
	 *             que intervienen: contablecencosto : cencosto
	 * 
	 */

	// todos los centros de costos
	public List getCenCosto(BigDecimal idempresa) throws EJBException {
		/**
		 * Entidad: Centros de costo
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer todos los centros de costos existentes
		 */

		String cQuery = "SELECT * FROM contablecencosto where idempresa ="
				+ idempresa.toString() + "ORDER BY 2";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// todos los centros de costos por alguna ocurrencia
	public List getCenCosto(String ocurrencia, BigDecimal idempresa)
			throws EJBException {
		/**
		 * Entidad: Centros de costo
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer todos los centros de costos por algun
		 *             tipo de ocurrenca
		 */
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM contablecencosto" + " where idempresa= "
				+ idempresa.toString() + " and (idcencosto::VARCHAR LIKE '%"
				+ ocurrencia + "%' OR " + " UPPER(descripcion) LIKE '%"
				+ ocurrencia.toUpperCase() + "%') " + " ORDER BY 2";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// centros de costos por PK
	public List getCenCostoPK(String pk, BigDecimal idempresa)
			throws EJBException {
		/**
		 * Entidad: Centros de costo
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer centro de costos existentes por PK
		 */
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM contablecencosto WHERE idcencosto = "
				+ pk.toString() + "and idempresa = " + idempresa.toString();
		;
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// Eliminar centros de costos por PK
	public String delCenCosto(Integer pk, BigDecimal idempresa)
			throws EJBException {
		/**
		 * Entidad: Centros de costo
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : eliminar centro costos existentes por PK
		 */
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM contablecencosto WHERE idcencosto = "
				+ pk.toString() + "and idempresa = " + idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE from contablecencosto where idcencosto = "
						+ pk.toString();
				statement.execute(cQuery);
				salida = "OK";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			log.error("Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception: " + ex);
		}
		return salida;
	}

	// insertar un centro de costo
	public String cenCostoSave(String descripcion, String usuarioalt,
			BigDecimal idempresa) throws EJBException {
		/**
		 * Entidad: Centros de costo
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : Insertar un nuevo centro de costo
		 */
		String salida = "NOOK";
		// validaciones de datos:
		if (descripcion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacia la descripcion del codigo de costo";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar el usuario de alta vacio";
		// fin validaciones
		try {
			PreparedStatement insert = dbconn
					.prepareStatement("INSERT INTO CONTABLEcencosto(descripcion, usuarioalt,idempresa) VALUES(?,?,?)");
			insert.setString(1, descripcion);
			insert.setString(2, usuarioalt);
			insert.setBigDecimal(3, idempresa);

			int n = insert.executeUpdate();
			if (n == 1)
				salida = "OK";

		} catch (SQLException sqlException) {
			log.error("Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception: " + ex);
		}
		return salida;
	}

	// update de un centro de costo
	public String cenCostoSaveOrUpdate(String descripcion, String usuarioalt,
			Integer idcencosto, BigDecimal idempresa) throws EJBException {
		/**
		 * Entidad: Centros de costo
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : Insertar un nuevo centro de costo
		 */
		Calendar hoy = new GregorianCalendar();
		Timestamp fecha = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		if (descripcion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacia la descripcion del codigo de costo";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar el usuario de alta vacio";
		// fin validaciones
		try {
			ResultSet rsSalida = null;
			String cQuery = "Select count(*) from contablecencosto where idcencosto = "
					+ idcencosto.toString()
					+ "and idempresa = "
					+ idempresa.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			if (total > 0) { // Existe asi que updeteo
				insert = dbconn
						.prepareStatement("UPDATE CONTABLEcencosto  SET descripcion=?,  usuarioact=?, fechaact=? where idcencosto=? and idempresa=?");
				insert.setString(1, descripcion);
				insert.setString(2, usuarioalt);
				insert.setTimestamp(3, fecha);
				insert.setInt(4, idcencosto.intValue());
				insert.setBigDecimal(5, idempresa);
			} else {
				insert = dbconn
						.prepareStatement("INSERT INTO CONTABLEcencosto(descripcion, usuarioalt,idempresa) VALUES(?,?,?)");
				insert.setString(1, descripcion);
				insert.setString(2, usuarioalt);
				insert.setBigDecimal(3, idempresa);
			}
			insert.executeUpdate();
			salida = "OK";

		} catch (SQLException sqlException) {
			log.error("Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception: " + ex);
		}
		return salida;
	}

	/**
	 * Metodos para la entidad: contableInfiPlan Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Thu Jun 14 15:59:43 ART 2007. - Creo: EJV - Llamada
	 * desde Lov
	 * 
	 */
	// plan de cuentas contables
	// todas las cuentas contables
	public List getCuentasTod(int idEjercicio, BigDecimal idempresa)
			throws EJBException {
		/**
		 * Entidad: Cuentas Contables
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer todos las Cuentas Contables existentes
		 */

		// String tableName = "contableinfiplan" + idEjercicio;
		String cQuery = ""
				+ "SELECT ip.idcuenta,ip.cuenta,ip.inputable,"
				+ "       ip.nivel,ip.ajustable,ip.resultado,cc1.descripcion,cc2.descripcion,"
				+ "       ip.usuarioalt,ip.usuarioact,ip.fechaalt,ip.fechaact"
				+ "  FROM contableinfiplan ip "
				+ "       LEFT JOIN contablecencosto cc1  ON ("
				+ "                 ip.cent_cost1  = cc1.idcencosto and ip.idempresa = cc1.idempresa )  "
				+ "       LEFT JOIN contablecencosto cc2  ON ("
				+ "                 ip.cent_cost2  = cc2.idcencosto and "
				+ "                 ip.idempresa = cc2.idempresa )"
				+ " WHERE ip.ejercicio=" + idEjercicio + " AND ip.idempresa= "
				+ idempresa.toString() + " ORDER BY 2";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// todos los Plan de Cuenta por alguna ocurrencia
	public List getCuentasOcu(int idEjercicio, String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		/*
		 * Entidad: Plan de Cuentas @ejb.interface-method view-type = "remote"
		 * 
		 * @throws SQLException Thrown if method fails due to system-level
		 * error. Utilidad : traer todos los Plan de Cuentas por algun tipo de
		 * ocurrencia
		 */
		String cQuery = ""
				+ "SELECT ip.idcuenta,ip.cuenta,ip.inputable,"
				+ "       ip.nivel,ip.ajustable,ip.resultado,cc1.descripcion,cc2.descripcion,"
				+ "       ip.usuarioalt,ip.usuarioact,ip.fechaalt,ip.fechaact"
				+ "  FROM contableinfiplan ip "
				+ "       LEFT JOIN contablecencosto cc1  ON ("
				+ "                 ip.cent_cost1  = cc1.idcencosto and ip.idempresa = cc1.idempresa )  "
				+ "       LEFT JOIN contablecencosto cc2  ON ("
				+ "                 ip.cent_cost2  = cc2.idcencosto and "
				+ "                 ip.idempresa = cc2.idempresa )"
				+ " WHERE ip.ejercicio=" + idEjercicio + " AND ip.idempresa= "
				+ idempresa.toString() + " and (ip.idcuenta::VARCHAR LIKE '%"
				+ ocurrencia + "%' OR " + " UPPER(ip.cuenta) LIKE '%"
				+ ocurrencia.toUpperCase() + "%') " + " ORDER BY idcuenta";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// Plan de Cuentas por PK
	public List getCuentasPK(int idEjercicio, Long pk, BigDecimal idempresa)
			throws EJBException {
		/*
		 * Entidad: Plan de Cuentas @ejb.interface-method view-type = "remote"
		 * 
		 * @throws SQLException Thrown if method fails due to system-level
		 * error. Utilidad : traer Plan de Cuenta existentes por PK
		 */
		List vecSalida = new ArrayList();
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idcuenta,cuenta,inputable,nivel,ajustable,resultado,cent_cost1,cent_cost2,ejercicio,"
				+ "       idempresa,usuarioalt,usuarioact,fechaalt,fechaact"
				+ "  FROM contableinfiplan WHERE idCuenta = " + pk.toString()
				+ "   AND idempresa = " + idempresa.toString()
				+ "   AND ejercicio = " + idEjercicio + " ORDER BY idcuenta ";

		log.info("getCuentasPK: " + cQuery);
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

	// indicar si una cuenta existe para un determinado ejercicio.

	public static boolean isCuentaActiva(BigDecimal idcuenta, Connection conn,
			BigDecimal idempresa) {
		boolean isActiva = false;
		ResultSet rsSalida = null;
		int ejercicio = getEjercicioActivo(conn, idempresa);
		String cQuery = "SELECT COUNT(1)FROM contableinfiplan "
				+ " WHERE idcuenta = " + idcuenta.toString()
				+ "   AND idempresa = " + idempresa.toString()
				+ "   AND ejercicio = " + ejercicio;
		try {

			if (ejercicio > 0) {
				Statement statement = conn.createStatement();
				rsSalida = statement.executeQuery(cQuery);
				if (rsSalida != null) {
					if (rsSalida.next()) {
						if (rsSalida.getInt(1) == 1)
							isActiva = true;
					}
				}
			} else
				log.warn("isCuentaActiva(): No exixte ejercicio activo !!!");

		} catch (SQLException sqlException) {
			log.error("Error SQL isCuentaActiva: " + sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception isCuentaActiva: " + ex);
		}

		return isActiva;
	}

	// indicar si una cuenta es imputable

	public boolean isImputable(int idEjercicio, Long idCuenta,
			BigDecimal idempresa) {
		boolean salida = false;
		try {
			List cuenta = getCuentasPK(idEjercicio, idCuenta, idempresa);
			// idcuenta,cuenta,inputable,nivel,ajustable,resultado,cent_cost1,cent_cost2,ejercicio
			Iterator c = cuenta.iterator();
			while (c.hasNext()) {
				String[] sCampos = (String[]) c.next();
				String cmp_imputable = sCampos[2];
				if (cmp_imputable.equalsIgnoreCase("S")) {
					salida = true;
				}
			}
		} catch (Exception ex) {
			log.error("Salida por exception isImputable( Integer idCuenta ): "
					+ ex);
		}
		return salida;
	}

	public boolean isAjustable(int idEjercicio, Long idCuenta,
			BigDecimal idempresa) {
		boolean salida = false;
		try {
			List cuenta = getCuentasPK(idEjercicio, idCuenta, idempresa);
			// idcuenta,cuenta,inputable,nivel,ajustable,resultado,cent_cost1,cent_cost2,ejercicio
			Iterator c = cuenta.iterator();
			while (c.hasNext()) {
				String[] sCampos = (String[]) c.next();
				String cmp_ajustable = sCampos[4];
				if (cmp_ajustable.equalsIgnoreCase("S")) {
					salida = true;
				}
			}
		} catch (Exception ex) {
			log.error("Salida por exception  isAjustable(Integer idCuenta) : "
					+ ex);
		}
		return salida;
	}

	public boolean isResultado(int idEjercicio, Long idCuenta,
			BigDecimal idempresa) {
		boolean salida = false;
		try {
			List cuenta = getCuentasPK(idEjercicio, idCuenta, idempresa);
			// idcuenta,cuenta,inputable,nivel,ajustable,resultado,cent_cost1,cent_cost2,ejercicio
			Iterator c = cuenta.iterator();
			while (c.hasNext()) {
				String[] sCampos = (String[]) c.next();
				String cmp_resultado = sCampos[5];
				if (cmp_resultado.equalsIgnoreCase("S")) {
					salida = true;
				}
			}
		} catch (Exception ex) {
			log.error("Salida por exception  isResultado(Integer idCuenta) : "
					+ ex);
		}
		return salida;
	}

	// traer el centro de costos1 de una cuenta dada
	public Long getCC1Cuenta(int idEjercicio, Long idCuenta,
			BigDecimal idempresa) {
		Long salida = null;
		try {
			List cuenta = getCuentasPK(idEjercicio, idCuenta, idempresa);
			// idcuenta,cuenta,inputable,nivel,ajustable,resultado,cent_cost1,cent_cost2,ejercicio
			Iterator c = cuenta.iterator();
			while (c.hasNext()) {
				String[] sCampos = (String[]) c.next();
				String cmp_cc1 = sCampos[6];
				if (cmp_cc1 != null) {
					salida = new Long(cmp_cc1);
				}
			}
		} catch (Exception ex) {
			log.error("Long getCC1Cuenta(int idEjercicio,Long idCuenta) : "
					+ ex);
		}
		return salida;
	}

	// traer el centro de costos1 de una cuenta dada
	public Long getCC2Cuenta(int idEjercicio, Long idCuenta,
			BigDecimal idempresa) {
		Long salida = null;
		try {
			List cuenta = getCuentasPK(idEjercicio, idCuenta, idempresa);
			// idcuenta,cuenta,inputable,nivel,ajustable,resultado,cent_cost1,cent_cost2,ejercicio
			Iterator c = cuenta.iterator();
			while (c.hasNext()) {
				String[] sCampos = (String[]) c.next();
				String cmp_cc2 = sCampos[7];
				if (cmp_cc2 != null) {
					salida = new Long(cmp_cc2);
				}
			}
		} catch (Exception ex) {
			log.error("Long getCC2Cuenta(int idEjercicio,Long idCuenta) : "
					+ ex);
		}
		return salida;
	}

	// Eliminar Cuenta Contable por PK
	public String delContable(int idEjercicio, Long pk, BigDecimal idempresa)
			throws EJBException {
		/*
		 * Entidad: Cuenta Contable @ejb.interface-method view-type = "remote"
		 * 
		 * @throws SQLException Thrown if method fails due to system-level
		 * error. Utilidad : eliminar Cuenta Contable por PK
		 */

		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM contableinfiplan WHERE ejercicio = "
				+ idEjercicio + " AND idcuenta = " + pk.toString()
				+ "and idempresa = " + idempresa.toString();

		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE from contableinfiplan WHERE ejercicio = "
						+ idEjercicio + " AND idcuenta = " + pk.toString()
						+ " and idempresa = " + idempresa.toString();
				statement.execute(cQuery);
				salida = "OK";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			log.error("Error SQL: " + sqlException);
			salida = "Error SQL: " + sqlException;
		} catch (Exception ex) {
			log.error("Salida por exception: " + ex);
			salida = "Salida por exception: " + ex;
		}
		return salida;
	}

	// impresion (prueba)

	// Contable Ajuste
	// todas las Contable Ajuste
	public List getAjusteTod(BigDecimal idempresa) throws EJBException {
		/**
		 * Entidad: Contable Ajuste
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer todos las Contable Ajuste existentes
		 */

		ResultSet rsSalida = null;
		String cQuery = "SELECT contableajuste.cod_ajuste,contableajuste.anio,globalmeses.mes,contableajuste.indice,contableajuste.usuarioalt,contableajuste.usuarioact,contableajuste.fechaalt,contableajuste.fechaact FROM contableajuste, globalmeses "
				+ " where globalmeses.idmes =  contableajuste.mes "
				+ " and contableajuste.idempresa = "
				+ idempresa.toString()
				+ " ORDER BY anio, mes ";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// todos los Contable Ajuste por alguna ocurrencia
	public List getAjusteOcu(String ocurrencia, BigDecimal idempresa)
			throws EJBException {
		/*
		 * Entidad: Contable Ajuste @ejb.interface-method view-type = "remote"
		 * 
		 * @throws SQLException Thrown if method fails due to system-level
		 * error. Utilidad : traer todos los Contable Ajuste por algun tipo de
		 * ocurrenca
		 */

		ResultSet rsSalida = null;
		String cQuery = "SELECT contableajuste.cod_ajuste,contableajuste.anio,globalmeses.mes,contableajuste.indice,contableajuste.usuarioalt,contableajuste.usuarioact,contableajuste.fechaalt,contableajuste.fechaact FROM contableajuste,globalmeses"
				+ " where globalmeses.idmes =  contableajuste.mes "
				+ " and contableajuste.idempresa = "
				+ idempresa.toString()
				+ " and (contableajuste.cod_ajuste::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " UPPER(contableajuste.anio::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase() + "%') " + " ORDER BY anio, mes";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// Contable Ajuste por PK
	public List getAjustePK(Integer pk, BigDecimal idempresa)
			throws EJBException {
		/*
		 * Entidad: Contable Ajuste @ejb.interface-method view-type = "remote"
		 * 
		 * @throws SQLException Thrown if method fails due to system-level
		 * error. Utilidad : traer Contable Ajuste existentes por PK
		 */

		ResultSet rsSalida = null;
		String cQuery = "Select * from contableajuste where cod_ajuste = "
				+ pk.toString() + "and idempresa = " + idempresa.toString();
		;
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// Eliminar Contable Ajuste por PK
	public String delAjuste(Integer pk, BigDecimal idempresa)
			throws EJBException {
		/*
		 * Entidad: Contable Ajuste @ejb.interface-method view-type = "remote"
		 * 
		 * @throws SQLException Thrown if method fails due to system-level
		 * error. Utilidad : eliminar Contable Ajuste por PK
		 */

		ResultSet rsSalida = null;
		String cQuery = "Select * from contableajuste where cod_ajuste = "
				+ pk.toString() + "and idempresa = " + idempresa.toString();
		;
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE from contableajuste where cod_ajuste = "
						+ pk.toString() + "and idempresa = "
						+ idempresa.toString();
				;
				statement.execute(cQuery);
				salida = "OK";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			log.error("Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception: " + ex);
		}
		return salida;
	}

	// insertar un Contable Ajuste
	public String AjusteSave(int anio, int mes, Float indice,
			String usuarioalt, BigDecimal idempresa) throws EJBException {
		/**
		 * Entidad: Contable Ajuste
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : Insertar un nuevo Contable Ajuste
		 */

		String salida = "NOOK";
		// validaciones de datos:
		if (anio == 0)
			salida = "Error: No se puede dejar vacio Año";
		if (mes == 0)
			salida = "Error: No se puede dejar vacio Mes";
		if (indice.compareTo(new Float(0)) == 0)
			salida = "Error: No se puede dejar vacio el Indice";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar el usuario de alta vacio";
		// fin validaciones

		try {

			PreparedStatement insert = dbconn
					.prepareStatement("INSERT INTO contableajuste(anio, mes, indice, usuarioalt, idempresa) VALUES(?,?,?,?,?)");
			insert.setInt(1, anio);
			insert.setInt(2, mes);
			insert.setFloat(3, indice.floatValue());
			insert.setString(4, usuarioalt);
			insert.setBigDecimal(5, idempresa);
			int n = insert.executeUpdate();
			if (n == 1)
				salida = "OK";

		} catch (SQLException sqlException) {
			log.error("Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception: " + ex);
		}
		return salida;
	}

	// update de una Cuenta Ajuste
	public String AjusteSaveOrUpdate(int anio, int mes, Float indice,
			String usuarioalt, Integer cod_ajuste, BigDecimal idempresa)
			throws EJBException {
		/**
		 * Entidad: Cuenta Ajuste
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : Insertar una nueva Cuenta Ajuste
		 */

		// seteo de la fecha del server para guardar
		// revisar si graba minutos y segundos sino en vez de usar java.sql.date
		// probar con java.sql.Timestamp
		Calendar hoy = new GregorianCalendar();
		Timestamp fecha = new Timestamp(hoy.getTime().getTime());

		String salida = "NOOK";
		// validaciones de datos:
		if (anio == 0)
			salida = "Error: No se puede dejar vacio Año";
		if (mes == 0)
			salida = "Error: No se puede dejar vacio Mes";
		if (indice.compareTo(new Float(0)) == 0)
			salida = "Error: No se puede dejar vacia la descripcion del codigo de costo";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar el usuario de alta vacio";
		// fin validaciones
		try {
			ResultSet rsSalida = null;
			String cQuery = "Select count(*) from contableajuste where cod_ajuste = "
					+ cod_ajuste.toString()
					+ "and idempresa = "
					+ idempresa.toString();
			;
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			if (total > 0) { // Existe asi que updeteo
				insert = dbconn
						.prepareStatement("UPDATE contableajuste  SET anio=?, mes = ?, indice=?, usuarioact=?, fechaact=? where cod_ajuste=? and idempresa =?");
				insert.setInt(1, anio);
				insert.setInt(2, mes);
				insert.setFloat(3, indice.floatValue());
				insert.setString(4, usuarioalt);
				insert.setTimestamp(5, fecha);
				insert.setInt(6, cod_ajuste.intValue());
				insert.setBigDecimal(7, idempresa);
			} else {
				insert = dbconn
						.prepareStatement("INSERT INTO contableajuste(anio, mes, indice, usuarioalt,idempresa) VALUES(?,?,?,?,?)");
				insert.setInt(1, anio);
				insert.setInt(2, mes);
				insert.setFloat(3, indice.floatValue());
				insert.setString(4, usuarioalt);
				insert.setBigDecimal(5, idempresa);
			}
			insert.executeUpdate();
			salida = "OK";

		} catch (SQLException sqlException) {
			log.error("Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception: " + ex);
		}
		return salida;
	}

	// todos los Contable Plan Ajuste
	public List getPlanAjusTod(int idEjercicio, BigDecimal idempresa)
			throws EJBException {
		/**
		 * Entidad: Contable Plan Ajuste
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer todos los Contable Plan Ajuste
		 *             existentes
		 */

		String cQuery = ""
				+ "SELECT contableplanajus.cuenta_pl as Cuenta, ip.cuenta as Descripcion, "
				+ "       contableplanajus.indice_pl as Indice, contableajuste.anio as Anio,"
				+ "       contableplanajus.usuarioalt,contableplanajus.usuarioact,contableplanajus.fechaalt,"
				+ "       contableplanajus.fechaact"
				+ "  FROM contableplanajus,contableinfiplan ip, contableajuste"
				+ " WHERE ip.idcuenta = contableplanajus.cuenta_pl "
				+ "   AND contableajuste.cod_ajuste = contableplanajus.indice_pl"
				+ "   AND ip.idempresa = contableplanajus.idempresa "
				+ "   AND contableajuste.idempresa = contableplanajus.idempresa"
				+ "   AND ip.idempresa = " + idempresa.toString();

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// todos los plan de cuenta por alguna ocurrencia
	public List getPlanAjusOcu(int idEjercicio, String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT contableplanajus.cuenta_pl as Cuenta, ip.cuenta as Descripcion, "
				+ "       contableplanajus.indice_pl as Indice, contableajuste.anio as Anio,"
				+ "       contableplanajus.usuarioalt,contableplanajus.usuarioact,contableplanajus.fechaalt,"
				+ "       contableplanajus.fechaact"
				+ "  FROM contableplanajus,contableinfiplan ip, contableajuste"
				+ " WHERE ip.idcuenta = contableplanajus.cuenta_pl "
				+ "   AND contableajuste.cod_ajuste = contableplanajus.indice_pl"
				+ "   AND ip.idempresa = contableplanajus.idempresa "
				+ "   AND contableajuste.idempresa = contableplanajus.idempresa"
				+ "   AND ip.idempresa = " + idempresa.toString()
				+ "   AND (contableplanajus.cuenta_pl::VARCHAR LIKE '%"
				+ ocurrencia + "%' OR "
				+ " UPPER(contableplanajus.indice_pl::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase() + "%') " + " ORDER BY 2";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// Contable Plan Ajuste por PK
	public List getPlanAjusPK(Integer pk, BigDecimal idempresa)
			throws EJBException {
		/**
		 * Entidad: Contable Plan Ajuste
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer Contable Plan Ajuste existentes por PK
		 */
		ResultSet rsSalida = null;
		String cQuery = "Select * from contableplanajus where cuenta_pl = and indice_pl = "
				+ pk.toString() + "and idempresa = " + idempresa.toString();
		;
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// Eliminar Contable Plan Ajuste por PK
	public String delPlanAjus(Long pk, Long pk2, BigDecimal idempresa)
			throws EJBException {
		/**
		 * Entidad: Contable Plan Ajuste
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : eliminar Contable Plan Ajuste existentes por
		 *             PK
		 */
		ResultSet rsSalida = null;
		String cQuery = "Select * from contableplanajus where cuenta_pl ="
				+ pk.longValue() + " AND  indice_pl  = " + pk2.longValue()
				+ " and idempresa = " + idempresa.toString();
		;
		log.info("Mensaje : " + cQuery);

		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE from contableplanajus where cuenta_pl ="
						+ pk.longValue() + " AND  indice_pl  = "
						+ pk2.longValue() + " and idempresa = "
						+ idempresa.toString();
				;
				statement.execute(cQuery);
				salida = "OK";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			log.error("Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception: " + ex);
		}
		return salida;
	}

	// insertar un Contable Plan Ajuste
	public String PlanAjusSave(Long cuenta_pl, Long indice_pl,
			String usuarioalt, BigDecimal idempresa) throws EJBException {
		/**
		 * Entidad: Contable Plan Ajuste
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : Insertar un nuevo Contable Plan Ajuste
		 */
		String salida = "NOOK";
		// validaciones de datos:
		if (cuenta_pl == new Long(0))
			salida = "Error: No se puede dejar vacia la cuenta";
		if (indice_pl == new Long(0))
			salida = "Error: No se puede dejar vacia el Indice";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar el usuario de alta vacio";
		// fin validaciones

		try {

			PreparedStatement insert = dbconn
					.prepareStatement("INSERT INTO contableplanajus(cuenta_pl, indice_pl, usuarioalt,idempresa) VALUES(?,?,?,?)");
			insert.setLong(1, cuenta_pl.longValue());
			insert.setLong(2, indice_pl.longValue());
			insert.setString(3, usuarioalt);
			insert.setBigDecimal(4, idempresa);
			ResultSet rsSalida = null;
			//		
			String cQuery = "select count(*) from contableplanajus where cuenta_pl ="
					+ cuenta_pl.longValue()
					+ " AND indice_pl  = "
					+ indice_pl.longValue()
					+ " and idempresa = "
					+ idempresa.toString();
			;
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);

			if (total > 0) {
				salida = "Error: Esta combinacion ya Existe por favor verifique!";
			} else {
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "OK";
			}

		} catch (SQLException sqlException) {
			log.error("Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception: " + ex);
		}
		return salida;
	}

	public boolean hasEjercicio(int idEjercicio, BigDecimal idempresa)
			throws EJBException {
		boolean salida = true;
		try {
			// TODO: discriminar para todos los driver.
			Statement statement = dbconn.createStatement();
			// postres
			if (clase.equalsIgnoreCase("org.postgresql.Driver")) {
				Postgres db = new Postgres();
				if (!db.hasEjercicioTablas(idEjercicio)) {
					salida = false;
				}
			}
		} catch (SQLException sqlException) {
			log.error("hasEjercicio: Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("hasEjercicio Salida por exception: " + ex);
		}
		return salida;
	}

	// setear un ejercicio= crearle todas las tablas.
	public boolean setEjercicio(int idEjercicio, Timestamp fdesde,
			Timestamp fhasta, String usuarioalt, BigDecimal idempresa)
			throws EJBException {
		boolean salida = false;
		boolean bError = false;
		try {
			// TODO: discriminar para todos los driver.
			Statement statement = dbconn.createStatement();
			log.info("comienza instanciamiento de la clase " + clase);
			// postres
			if (clase.equalsIgnoreCase("org.postgresql.Driver")) {
				Postgres db = new Postgres();
				if (db.setEjercicioTablas(idEjercicio).equalsIgnoreCase("OK")) {
					log
							.info("Atencion: se crearon las tablas para el nuevo ejercicio contable : "
									+ idEjercicio);
					ejercicioCreate(idEjercicio, fdesde, fhasta, usuarioalt,
							idempresa);
					salida = true;
				}

			}
		} catch (SQLException sqlException) {
			log.error("hasEjercicio: Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("hasEjercicio Salida por exception: " + ex);
		}
		return salida;
	}

	public void ejercicioCreate(int idEjercicio, Timestamp fdesde,
			Timestamp fhasta, String usuarioalt, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaalt = new Timestamp(hoy.getTime().getTime());
		try {
			PreparedStatement insert = dbconn
					.prepareStatement("INSERT INTO contableejercicios(ejercicio, fechadesde, fechahasta,  usuarioalt,fechaalt,idempresa) VALUES(?,?,?,?,?,? )");
			insert.setInt(1, idEjercicio);
			insert.setTimestamp(2, fdesde);
			insert.setTimestamp(3, fhasta);
			insert.setString(4, usuarioalt);
			insert.setTimestamp(5, fechaalt);
			insert.setBigDecimal(6, idempresa);
			int n = insert.executeUpdate();
		} catch (SQLException sqlException) {
			log.error("ejercicioCreate( int idEjercicio ): Error SQL: "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("ejercicioCreate( int idEjercicio ) Salida por exception: "
							+ ex);
		}
	}

	// RCUPERA TODOS LOS EJERCICIOS
	public List getEjerciciosAll(BigDecimal idempresa) throws EJBException {

		String cQuery = "SELECT ejercicio, fechadesde, fechahasta, activo, usuarioalt, usuarioact,fechaalt, fechaact "
				+ "        FROM contableejercicios  where idempresa = "
				+ idempresa.toString() + "ORDER BY 1";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// Activar un ejercicio contable
	// poner activo en S
	public String activarEjercicio(int ejerciciodesde, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaalt = new Timestamp(hoy.getTime().getTime());
		boolean bError = false;
		String salida = "NOOK";
		try {
			if (getEjercicioActivo(idempresa) == ejerciciodesde) {
				bError = true;
				salida = "Atencion: El ejercicio que intenta activar ya se encuentra activo";

			}

			// verifico que exista el ejercio pedido
			if (!bError) {
				Statement statement = dbconn.createStatement();
				String cQuery = "select count(*) as total from contableejercicios where ejercicio= "
						+ ejerciciodesde
						+ "and idempresa = "
						+ idempresa.toString();
				;
				ResultSet rsExiste = statement.executeQuery(cQuery);
				if (rsExiste != null && rsExiste.next()) {
					if (rsExiste.getInt(1) < 1) {
						bError = true;
						salida = "Error: No existe el ejercicio seleccionado";
					}
				}
			}
			if (!bError) {
				// anulo cualquier activacion ya existente y la reemplazo por lo
				// que venga
				PreparedStatement limpiar = dbconn
						.prepareStatement("UPDATE contableejercicios SET activo = null "
								+ " where idempresa = " + idempresa.toString());
				int n = limpiar.executeUpdate();
				// ahora activo el ejercicio que llega como parametro
				PreparedStatement insert = dbconn
						.prepareStatement("UPDATE contableejercicios SET activo = 'S' where ejercicio  = ? and idempresa = ?");
				insert.setInt(1, ejerciciodesde);
				insert.setBigDecimal(2, idempresa);

				int r = insert.executeUpdate();
				salida = "OK";
			}
		} catch (SQLException sqlException) {
			log.error("activarEjercicio( int idEjercicio ): Error SQL: "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("activarEjercicio( int idEjercicio ) Salida por exception: "
							+ ex);
		}
		return salida;
	}

	// levantar el ejercicio activo
	public int getEjercicioActivo(BigDecimal idempresa) throws EJBException {
		int salida = -1;
		try {
			Statement statement = dbconn.createStatement();
			String cQuery = "select ejercicio from contableejercicios where activo = 'S'"
					+ "and idempresa = " + idempresa.toString();

			ResultSet rsSalida = statement.executeQuery(cQuery);
			if (rsSalida != null && rsSalida.next()) {
				salida = rsSalida.getInt(1);
			}
		} catch (SQLException sqlException) {
			log.error("getEjercicioActivo() : Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getEjercicioActivo()  Salida por exception: " + ex);
		}
		return salida;
	}

	// levantar el ejercicio activo
	public static int getEjercicioActivo(Connection conn, BigDecimal idempresa)
			throws EJBException {
		int salida = -1;
		try {
			Statement statement = conn.createStatement();
			String cQuery = "select ejercicio from contableejercicios where activo = 'S'"
					+ "and idempresa = " + idempresa.toString();

			ResultSet rsSalida = statement.executeQuery(cQuery);
			if (rsSalida != null && rsSalida.next()) {
				salida = rsSalida.getInt(1);
			}
		} catch (SQLException sqlException) {
			log
					.error("getEjercicioActivo(Connection conn, BigDecimal idempresa) : Error SQL: "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("getEjercicioActivo(Connection conn, BigDecimal idempresa)  Salida por exception: "
							+ ex);
		}
		return salida;
	}

	public Timestamp getFechaEjercicioActivoDesde(BigDecimal idempresa) {
		int ejerActivo = getEjercicioActivo(idempresa);
		Timestamp salida = null;
		boolean bError = false;
		try {
			if (ejerActivo == -1) {
				log
						.info("Timestamp getFechaEjercicioActivoDesde(): falla a causa de que no encuentra un ejercicio activo");
				bError = true;
			}
			if (!bError) {
				Statement statement = dbconn.createStatement();
				String cQuery = "SELECT * from contableejercicios where activo = 'S'"
						+ " and idempresa = " + idempresa.toString();

				ResultSet rsSalida = statement.executeQuery(cQuery);
				if (rsSalida != null && rsSalida.next()) {
					salida = rsSalida.getTimestamp(2);
				}
			}
		} catch (SQLException sqlException) {
			log.error("Timestamp getFechaEjercicioActivoDesde(): Error SQL: "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Timestamp getFechaEjercicioActivoDesde():  Salida por exception: "
							+ ex);
		}
		return salida;
	}

	public Timestamp getFechaEjercicioActivoHasta(BigDecimal idempresa) {
		int ejerActivo = getEjercicioActivo(idempresa);
		Timestamp salida = null;
		boolean bError = false;
		try {
			if (ejerActivo == -1) {
				log
						.info("Timestamp getFechaEjercicioActivoHasta(): falla a causa de que no encuentra un ejercicio activo");
				bError = true;
			}
			if (!bError) {
				Statement statement = dbconn.createStatement();
				String cQuery = "SELECT * from contableejercicios where activo = 'S'"
						+ "and idempresa = " + idempresa.toString();
				;
				ResultSet rsSalida = statement.executeQuery(cQuery);
				if (rsSalida != null && rsSalida.next()) {
					salida = rsSalida.getTimestamp(3);
				}
			}
		} catch (SQLException sqlException) {
			log.error("Timestamp getFechaEjercicioActivoHasta(): Error SQL: "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Timestamp getFechaEjercicioActivoHasta():  Salida por exception: "
							+ ex);
		}
		return salida;
	}

	// falta copiar plan de cuentas contables.
	//
	// public String copyPlanCuentas(int idEjercicioOrigen,
	// int idEjercicioDestino, BigDecimal idempresa) throws EJBException {
	// String salida = "NOOK";
	// boolean bError = false;
	// try {
	// // verifico que no sean iguales origen y destino
	// if (idEjercicioOrigen == idEjercicioDestino) {
	// salida = "Error: Origen y destino no pueden ser iguales";
	// bError = true;
	// }
	//
	// Statement statement = dbconn.createStatement();
	// String tableNameOrigen = "contableinfiplan" + idEjercicioOrigen;
	// String tableNameDestino = "contableinfiplan" + idEjercicioDestino;
	// // pregunto por la existencia de las tablas
	// if (!bError) {
	// if (clase.equalsIgnoreCase("org.postgresql.Driver")) {
	// Postgres db = new Postgres();
	// if (!db.existeTabla(tableNameOrigen)) {
	// salida = "Error: no existe el ejercicio origen";
	// bError = true;
	// }
	// }
	// }
	// if (!bError) {
	// if (clase.equalsIgnoreCase("org.postgresql.Driver")) {
	// Postgres db = new Postgres();
	// if (!db.existeTabla(tableNameDestino)) {
	// salida = "Error: no existe el ejercicio destino";
	// bError = true;
	// }
	// }
	// }
	//
	// // me aseguro que el origen tenga datos
	// if (!bError) {
	// String cQuery = "select count(*) as total from "
	// + tableNameOrigen + " where " + tableNameOrigen
	// + ".idempresa = " + idempresa.toString();
	// ResultSet rsSalida = statement.executeQuery(cQuery);
	// if (rsSalida.next() && rsSalida.getInt(1) <= 0) {
	// salida = "Error: No existen datos en el origen para ser copiados";
	// bError = true;
	// }
	// }
	// // me aseguro que el destino no tenga datos
	// if (!bError) {
	// String cQuery = "select count(*) as total from "
	// + tableNameDestino + " where " + tableNameDestino
	// + ".idempresa = " + idempresa.toString();
	// ResultSet rsSalida = statement.executeQuery(cQuery);
	// if (rsSalida.next() && rsSalida.getInt(1) > 0) {
	// salida = "Error: La tabla destino ya posee datos";
	// bError = true;
	// }
	// }
	// // miro los centros de costos del origen
	// if (!bError) {
	// String cQuery = "select count(*) as total from "
	// + tableNameOrigen + " ";
	// cQuery += "where cent_cost1 not in (select idcencosto from
	// contablecencosto where contablecencosto.idempresa = "
	// + idempresa.toString() + ")";
	// cQuery += " or cent_cost2 not in (select idcencosto from contablecencosto
	// where contablecencosto.idempresa = "
	// + idempresa.toString() + ")";
	// cQuery += " and idempresa = " + idempresa.toString();
	// ResultSet rsSalida = statement.executeQuery(cQuery);
	// if (rsSalida.next() && rsSalida.getInt(1) > 0) {
	// salida = "Error: El origen posee centros de costos no declarados, por
	// favor verfique";
	// bError = true;
	// }
	// }
	// // si esta bien entonces hago la copia
	// if (!bError) {
	// PreparedStatement copiar = dbconn
	// .prepareStatement("INSERT INTO " + tableNameDestino
	// + " SELECT * FROM " + tableNameOrigen
	// + " where " + tableNameOrigen + ".idempresa = "
	// + idempresa.toString());
	// int n = copiar.executeUpdate();
	// if (n > 0)
	// salida = "OK";
	// else {
	// bError = true;
	// salida = "Error: no se copiaron datos entre los ejercicios
	// seleccionados";
	// }
	// log
	// .info("Resultado de la copia de plan de cuentas entre ejercicios: "
	// + salida);
	// }
	//
	// } catch (SQLException sqlException) {
	// log
	// .error("copyPlanCuentas( int idEjercicioOrigen, int idEjercicioDestino ):
	// Error SQL: "
	// + sqlException);
	// } catch (Exception ex) {
	// log
	// .error("copyPlanCuentas( int idEjercicioOrigen, int idEjercicioDestino )
	// Salida por exception: "
	// + ex);
	// }
	// return salida;
	// }
	//
	public void dropEjercicioPostgres(int idEjercicio, BigDecimal idempresa) {
		try {
			Postgres db = new Postgres();
			db.dropEjercicioPostgres(idEjercicio, idempresa);
		} catch (Exception ex) {
			log
					.error(" dropEjercicioPostgres(int idEjercicio) Salida por exception: "
							+ ex);
		}
	}

	/**
	 * DEVUELVE EL PROXIMO NUMERO DE CONTADOR
	 */

	/*
	 * EJV - 20090706 | Reemplazado por GeneralBean.getContador
	 */

	// public Long getContador(String contador, BigDecimal idempresa)
	// throws EJBException {
	// String tableName = "globalcontadores";
	// ResultSet rsSalida = null;
	// PreparedStatement update = null;
	// Long valor = null;
	// String cQuery = " Select valor  from  " + tableName
	// + " where contador =  '" + contador.toLowerCase() + "'"
	// + " and idempresa = " + idempresa.toString();
	// try {
	// Statement statement = dbconn.createStatement();
	// rsSalida = statement.executeQuery(cQuery);
	// if (rsSalida.next()) {
	// valor = new Long(rsSalida.getLong(1));
	// update = dbconn.prepareStatement("UPDATE  " + tableName
	// + " SET valor =  ?  "
	// + " WHERE contador=? and idempresa= ? ");
	//
	// update.setLong(1, valor.longValue() + 1);
	// update.setString(2, contador);
	// update.setBigDecimal(3, idempresa);
	// update.executeUpdate();
	// } else
	// valor = new Long(-1);
	//
	// } catch (SQLException sqlException) {
	// log.error("Error SQL: " + sqlException);
	// } catch (Exception ex) {
	// log.error("Salida por exception: " + ex);
	// }
	//
	// return valor;
	// }
	/**
	 * Metodos para la entidad: contableAsietip1 Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Mon Sep 15 13:54:44 GMT-03:00 2008
	 */

	public String contableAsientosTipoCreate(BigDecimal codigo, String leyenda,
			Hashtable htDetalle, BigDecimal idempresa, String usuarioalt)
			throws EJBException, SQLException {

		String salida = "OK";
		Enumeration en;
		dbconn.setAutoCommit(false);
		try {

			if (codigo == null || codigo.longValue() < 1) {

				codigo = GeneralBean.getNextValorSequencia(
						"seq_contableasietip1", dbconn);

				if (codigo == null)
					salida = "Imposible asignar codigo para asiento tipo.";

			} else {

				salida = contableAsietip1Delete(codigo, idempresa);

			}

			if (salida.equalsIgnoreCase("OK")) {

				salida = contableAsietip1Create(codigo, leyenda, idempresa,
						usuarioalt);

				en = htDetalle.keys();

				while (en.hasMoreElements() && salida.equalsIgnoreCase("OK")) {

					String[] datos = (String[]) htDetalle.get(en.nextElement());

					BigDecimal cuenta = new BigDecimal(datos[0]);
					String detalle = datos[2];
					String cent_cost = datos[3];
					String cent_cost1 = datos[4];

					salida = contableAsietip2Create(codigo, cuenta, detalle,
							cent_cost, cent_cost1, idempresa, usuarioalt);

				}

			}

		} catch (Exception e) {
			salida = "Excepcion al generar asiento tipo.";
			log.error("contableAsientosTipoCreate(...):" + e);
		}

		if (salida.equalsIgnoreCase("OK")) {
			dbconn.commit();
		} else {
			dbconn.rollback();

		}

		dbconn.setAutoCommit(true);

		return salida;
	}

	// para todo (ordena por el segundo campo por defecto)
	public List getContableAsietip1All(long limit, long offset,
			BigDecimal idempresa) throws EJBException {

		String cQuery = ""
				+ "SELECT codigo,leyenda,idempresa,usuarioalt,usuarioact,fechaalt,fechaact"
				+ "  FROM CONTABLEASIETIP1 WHERE idempresa = "
				+ idempresa.toString() + "  ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getContableAsietip1Ocu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {

		String cQuery = ""
				+ "SELECT  codigo,leyenda,idempresa,usuarioalt,usuarioact,fechaalt,fechaact"
				+ "  FROM CONTABLEASIETIP1 WHERE ( (codigo::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(leyenda) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%')  AND idempresa = "
				+ idempresa.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getContableAsietip1PK(BigDecimal codigo, BigDecimal idempresa)
			throws EJBException {

		String cQuery = ""
				+ "SELECT codigo,leyenda,idempresa,usuarioalt,usuarioact,fechaalt,fechaact"
				+ "  FROM CONTABLEASIETIP1 WHERE codigo=" + codigo.toString()
				+ " AND idempresa = " + idempresa.toString() + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String contableAsietip1Delete(BigDecimal codigo, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM CONTABLEASIETIP1 WHERE codigo="
				+ codigo.toString() + " AND idempresa=" + idempresa.toString();
		String salida = "OK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {

				salida = contableAsietip2Delete(codigo, idempresa);

				if (salida.equalsIgnoreCase("OK")) {

					cQuery = "DELETE FROM CONTABLEASIETIP1 WHERE codigo="
							+ codigo.toString().toString() + " AND idempresa="
							+ idempresa.toString();
					int i = statement.executeUpdate(cQuery);

					if (i != 1)
						salida = "Asiento inexistente.";

				}

			} else {
				salida = "Error: Asiento inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : contableAsietip1Delete( BigDecimal codigo, BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: contableAsietip1Delete( BigDecimal codigo, BigDecimal idempresa )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String contableAsietip1Create(BigDecimal codigo, String leyenda,
			BigDecimal idempresa, String usuarioalt) throws EJBException {
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		// 2. sin nada desde la pagina

		// fin validaciones

		try {
			if (salida.equalsIgnoreCase("OK")) {
				String ins = "INSERT INTO CONTABLEASIETIP1(codigo, leyenda, idempresa, usuarioalt ) VALUES (?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, codigo);
				insert.setString(2, leyenda);
				insert.setBigDecimal(3, idempresa);
				insert.setString(4, usuarioalt);
				int n = insert.executeUpdate();
				if (n != 1)
					salida = "No se genero asiento tipo.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el asiento tipo (E1).";
			log.error("Error SQL public String contableAsietip1Create(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el asiento tipo (E2).";
			log
					.error("Error excepcion public String contableAsietip1Create(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String contableAsietip1CreateOrUpdate(BigDecimal codigo,
			String leyenda, BigDecimal idempresa, String usuarioact)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM contableAsietip1 WHERE codigo = "
					+ codigo.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE CONTABLEASIETIP1 SET leyenda=?, idempresa=?, usuarioact=?, fechaact=? WHERE codigo=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, leyenda);
					insert.setBigDecimal(2, idempresa);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, codigo);
				} else {
					String ins = "INSERT INTO CONTABLEASIETIP1(leyenda, idempresa, usuarioalt ) VALUES (?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, leyenda);
					insert.setBigDecimal(2, idempresa);
					insert.setString(3, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String contableAsietip1CreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String contableAsietip1CreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String contableAsietip1Update(BigDecimal codigo, String leyenda,
			BigDecimal idempresa, String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM contableAsietip1 WHERE codigo = "
					+ codigo.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE CONTABLEASIETIP1 SET leyenda=?, idempresa=?, usuarioact=?, fechaact=? WHERE codigo=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, leyenda);
					insert.setBigDecimal(2, idempresa);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, codigo);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String contableAsietip1Update(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String contableAsietip1Update(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * Metodos para la entidad: contableAsietip2 Copyrigth(r) sysWarp S.R.L.
	 */

	// por primary key (primer campo por defecto)
	public List getContableAsietip2PK(BigDecimal codigo, BigDecimal ejercicio,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT a2.codigo,ip.idcuenta,ip.cuenta,a2.detalle,a2.cent_cost,a2.cent_cost1,"
				+ "       a2.idempresa,a2.usuarioalt,a2.usuarioact,a2.fechaalt,a2.fechaact"
				+ "  FROM CONTABLEASIETIP2 a2"
				+ "       INNER JOIN contableinfiplan ip ON a2.cuenta = ip.idcuenta AND a2.idempresa = ip.idempresa"
				+ " WHERE a2.codigo=" + codigo.toString()
				+ "   AND a2.idempresa = " + idempresa.toString()
				+ "   AND ip.ejercicio =  " + ejercicio.toString() + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String contableAsietip2Delete(BigDecimal codigo, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM CONTABLEASIETIP2 WHERE codigo="
				+ codigo.toString() + " AND idempresa=" + idempresa.toString();
		String salida = "OK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM CONTABLEASIETIP2 WHERE codigo="
						+ codigo.toString().toString() + " AND idempresa="
						+ idempresa.toString();
				int i = statement.executeUpdate(cQuery);

			} else {
				salida = "Error: Detalle de asiento tipo inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar detalle de asiento tipo.";
			log
					.error("Error SQL en el metodo : contableAsietip2Delete( BigDecimal codigo, BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar detalle de asiento tipo.";
			log
					.error("Salida por exception: en el metodo: contableAsietip2Delete( BigDecimal codigo, BigDecimal idempresa )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String contableAsietip2Create(BigDecimal codigo, BigDecimal cuenta,
			String detalle, String cent_cost, String cent_cost1,
			BigDecimal idempresa, String usuarioalt) throws EJBException {
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (codigo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo ";
		if (cuenta == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cuenta ";
		if (detalle == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: detalle ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (detalle.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: detalle ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		try {
			if (salida.equalsIgnoreCase("OK")) {
				String ins = "INSERT INTO CONTABLEASIETIP2(codigo, cuenta, detalle, cent_cost, cent_cost1, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, codigo);
				insert.setBigDecimal(2, cuenta);
				insert.setString(3, detalle);
				insert.setString(4, cent_cost);
				insert.setString(5, cent_cost1);
				insert.setBigDecimal(6, idempresa);
				insert.setString(7, usuarioalt);
				int n = insert.executeUpdate();
				if (n != 1)
					salida = "Error al generar detalle de asiento tipo.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String contableAsietip2Create(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String contableAsietip2Create(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * METODOS CORRESPONDIENTES A ASIENTOS TIPO
	 * ******************************************************************
	 * RECUPERA TODOS LOS ASIENTOS TIPO
	 */

	public List getAsientosTipoAll(BigDecimal idempresa) throws EJBException {

		ResultSet rsSalida = null;
		String cQuery = "SELECT  codigo, leyenda, usuarioalt, usuarioact, fechaalt, fechaact  FROM contableasietip1 "
				+ " where idempresa = " + idempresa.toString() + " ORDER BY 2 ";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getAsientosTipoOcu(String ocurrencia, BigDecimal idempresa)
			throws EJBException {

		ResultSet rsSalida = null;
		String cQuery = "SELECT  codigo, leyenda, usuarioalt, usuarioact, fechaalt, fechaact  FROM contableasietip1 "
				+ " where idempresa = "
				+ idempresa.toString()
				+ " and (codigo::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " UPPER(leyenda) LIKE '%"
				+ ocurrencia.toUpperCase()
				+ "%') "
				+ " ORDER BY 2";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// EJV
	/**
	 * METODOS CORRESPONDIENTES A ASIENTOS
	 * **************************************
	 * ************************************* RECUPERA ASIENTOS POR OCURRENCIA
	 */

	public List getAsientosOcu(int idEjercicio, String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		// 20080911 - EJV - contableasientos reemplazo a contableleyendas
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT mov.idasiento, mov.renglon, mov.tipomov,mov.fecha, mov.detalle, ley.leyenda, mov.cuenta, mov.importe  "
				+ "  FROM contableinfimov"
				+ " mov INNER JOIN contableasientos"
				+ "  ley on mov.idasiento = ley.idasiento and mov.idempresa = ley.idempresa "
				+ "  WHERE mov.idempresa = " + idempresa.toString()
				+ " AND ley.ejercicio = " + idEjercicio
				+ " and mov.detalle LIKE '" + ocurrencia.toLowerCase()
				+ "%'  OR mov.idasiento::VARCHAR LIKE  '"
				+ ocurrencia.toLowerCase() + "%'   OR ley.leyenda LIKE  '"
				+ ocurrencia.toLowerCase() + "%'  "
				+ " ORDER BY mov.idasiento ";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getLibroDiario(int idEjercicio, Long idAsientoDesde,
			Long idAsisentoHasta, BigDecimal idempresa) throws EJBException {

		// 20080911 - EJV - contableasientos reemplazo a contableleyendas
		String cQuery = ""
				+ "SELECT ley.nroasiento, mov.renglon, mov.tipomov,to_char(ley.fecha,'dd/mm/yyyy') as fecha, mov.detalle, ley.leyenda,"
				+ "       mov.cuenta, mov.importe::numeric(18,2) as importe, plan.cuenta  "
				+ "  FROM contableinfimov mov "
				+ "       INNER JOIN contableasientos ley "
				+ "               ON mov.idasiento = ley.idasiento AND mov.idempresa = ley.idempresa "
				+ "       LEFT JOIN contableinfiplan plan "
				+ "               ON mov.cuenta = plan.idcuenta AND mov.idempresa = plan.idempresa AND plan.ejercicio = "
				+ idEjercicio + " WHERE mov.idempresa = "
				+ idempresa.toString() + "   AND ley.ejercicio = "
				+ idEjercicio + "   AND ley.nroasiento BETWEEN "
				+ idAsientoDesde + " AND  " + idAsisentoHasta
				+ " ORDER BY ley.nroasiento ASC ";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getLibroDiario(int idEjercicio, Timestamp fechaDesde,
			Timestamp fechaHasta, BigDecimal idempresa) throws EJBException {
		// 20080911 - EJV - contableasientos reemplazo a contableleyendas

		String cQuery = ""
				+ "SELECT ley.nroasiento, mov.renglon, mov.tipomov,to_char(ley.fecha,'dd/mm/yyyy') as fecha, mov.detalle, ley.leyenda, mov.cuenta, mov.importe::numeric(18,2) as importe, plan.cuenta  "
				+ "  FROM contableinfimov mov "
				+ "       INNER JOIN contableasientos ley "
				+ "               ON mov.idasiento = ley.idasiento AND mov.idempresa = ley.idempresa "
				+ "       LEFT JOIN contableinfiplan plan"
				+ "               ON mov.cuenta = plan.idcuenta AND mov.idempresa = plan.idempresa AND plan.ejercicio = "
				+ idEjercicio + " WHERE mov.idempresa = "
				+ idempresa.toString() + "   AND ley.ejercicio=" + idEjercicio
				+ "   AND ley.fecha::date BETWEEN '" + fechaDesde
				+ "'::date AND  '" + fechaHasta + "'::date   ";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getLibroDiarioSC(int idEjercicio, String fechaDesde,
			String fechaHasta, BigDecimal idempresa, String vista)
			throws EJBException {
		// 20080911 - CEP - Objetivo: Mostrar Subcontabilidad de un modulo
		List vecSalida = new ArrayList();
		String cQuery = ""
				+ "select "
				+ " SC.comprobante as idasiento, "
				+ " 0 as renglon, "
				+ " SC.tipomov,"
				+ " SC.fechamov as fecha,"
				+ " SC.detalle,"
				+ " SC.detalle as leyenda,"
				+ " SC.cuenta,"
				+ " SC.importe,"
				+ " PLAN.cuenta as cuentadesc, SC.fechav "
				+ "   from "
				+ " "
				+ vista
				+ " SC "
				+ "    left join contableinfiplan PLAN on ( SC.cuenta = PLAN.idcuenta and SC.idempresa = PLAN.idempresa and PLAN.ejercicio = "
				+ idEjercicio
				+ ")"
				+ " where SC.idempresa = "
				+ idempresa.toString()
				+ "  and SC.fechamov::date between to_date('"
				+ fechaDesde
				+ "','dd/mm/yyyy') and to_date('"
				+ fechaHasta
				+ "','dd/mm/yyyy') order by SC.comprobante, SC.tipomov, SC.importe desc";

		try {
			Statement statement = dbconn.createStatement();
			ResultSet rsSalida = statement.executeQuery(cQuery);
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
					.error("(SQLE) getLibroDiarioSC - INVOCADO POR "
							+ GeneralBean.getCallingMethodName() + " : "
							+ sqlException);
			// throw new EJBException(
			// "getLista() - SQLException:  -- >: throw new EJBException ");
		} catch (Exception ex) {
			log.error("(EX) getLibroDiarioSC - INVOCADO POR "
					+ GeneralBean.getCallingMethodName() + " : " + ex);

			// throw new EJBException(
			// "getLista() - Exception:  -- >: throw new EJBException ");
		}

		return vecSalida;
	}

	public String setLibroDiarioSC_BACKUP_20090706(int idEjercicio,
			String fechaDesde, String fechaHasta, BigDecimal idempresa,
			String vista) throws EJBException {
		// 20081011 - CEP - Objetivo: Pasar la SC a la contabilidad general
		// todo: evaluar el ejercicio contable de la siguiente forma
		// . Que este activo
		// . que los asientos que vienen esten en ese rango de fechas.
		// . CUIDADO. En las vistas de SC no tengo forma de tener ejercicio.
		// Arreglar curasiento.
		// 2012-09-17 : se agrega el pasaje de clientes (sin asociacion, dudo que incluso sea un TODO).		
		
		ResultSet rsSalida = null;
		String salida = "OK";

		BigDecimal proximoAsiento = new BigDecimal(0);
		try {

			ResultSet rsProximoAsiento = null;
			ResultSet rsCurAsiento = null;
			String auxQuery = "select COALESCE(max(nroasiento),0)+1::numeric as proximoasiento from contableasientos where idempresa = "
					+ idempresa.toEngineeringString()
					+ " and ejercicio = "
					+ idEjercicio;
			Statement statement = dbconn.createStatement();
			rsProximoAsiento = statement.executeQuery(auxQuery);
			while (rsProximoAsiento != null && rsProximoAsiento.next()) {
				proximoAsiento = rsProximoAsiento
						.getBigDecimal("proximoasiento");
			}
			String cQuery = "select "
					+ "  fechamov, cartera, comprobante, tipomov, cuenta, importe, cencosto, subcencosto, detalle, idempresa  "
					+ " from "
					+ vista
					+ " where idempresa = "
					+ idempresa.toString()
					+ "  and fechamov between  to_date(?,'dd/mm/yyyy') and  to_date(?,'dd/mm/yyyy') order by comprobante, tipomov, importe desc";
			PreparedStatement ps = dbconn.prepareStatement(cQuery);
			ps.setString(1, fechaDesde);
			ps.setString(2, fechaHasta);
			ps.execute();
			rsSalida = ps.getResultSet();
			BigDecimal curcomp = new BigDecimal("-999999999999");
			if (rsSalida.next()) {
				while (!rsSalida.isLast()) {
					if (rsSalida.getBigDecimal("comprobante")
							.compareTo(curcomp) != 0) {
						// -- grabo cabecera

						// modificacion para tener una asociacion por id
						String queryAso = "select nextval('seq_contableasociaciones') as nro;";
						Statement statementAso = dbconn.createStatement();
						ResultSet rsNroAso = statementAso
								.executeQuery(queryAso);
						BigDecimal idAsociacion = new BigDecimal(-1);
						while (rsNroAso.next()) {
							idAsociacion = rsNroAso.getBigDecimal("nro");
						}

						curcomp = rsSalida.getBigDecimal("comprobante");
						System.out.println("Grabo cabecera nro: "
								+ curcomp.toString());
						PreparedStatement insert = dbconn
								.prepareStatement("insert into contableasientos ( idasiento, ejercicio, fecha, leyenda, nroasiento, asiento_nuevo, idempresa, usuarioalt,sistema,idasociacion) values (default,?,?,?,?,?,?,?,?,?) ");
						insert.setInt(1, idEjercicio);
						insert.setTimestamp(2, rsSalida
								.getTimestamp("fechamov"));
						insert.setString(3, rsSalida.getString("detalle"));
						insert.setBigDecimal(4, proximoAsiento);
						insert.setInt(5, -1);
						insert.setBigDecimal(6, idempresa);
						insert.setString(7, "SC");

						String sistema = "NOOK"; // varchar 10
						if (vista.equalsIgnoreCase("vcajasubcontabilidad")) { // tesoreria
							sistema = "TESORERIA";
						}


						if (vista
								.equalsIgnoreCase("vproveedoressubcontabilidad")) { // proveedores
							sistema = "COMPRAS";

							// actualizo fecha de pasaje
							String cQueryFechaPasaje = "update setupvariables set valor = ? where variable = ? and idempresa = ? ;";
							PreparedStatement insertFecha = dbconn
									.prepareStatement(cQueryFechaPasaje);
							insertFecha.setString(1, fechaHasta);
							insertFecha.setString(2, "provFechaUltimoCierre");
							insertFecha.setBigDecimal(3, idempresa);
							int x = insertFecha.executeUpdate();
						}
						
						
						
						if (vista
								.equalsIgnoreCase("vclientessubcontabilidad")) { // clientes 
							sistema = "VENTAS";

							// actualizo fecha de pasaje
							String cQueryFechaPasaje = "update setupvariables set valor = ? where variable = ? and idempresa = ? ;";
							PreparedStatement insertFecha = dbconn
									.prepareStatement(cQueryFechaPasaje);
							insertFecha.setString(1, fechaHasta);
							insertFecha.setString(2, "clienFechaUltimoCierre");
							insertFecha.setBigDecimal(3, idempresa);
							int x = insertFecha.executeUpdate();
						}
						

						if (vista.equalsIgnoreCase("vstocksubcontabilidad")) { // stock
							sistema = "STOCK";
						}

						insert.setString(8, sistema);
						insert.setBigDecimal(9, idAsociacion);
						// proximoAsiento = proximoAsiento.add(new
						// BigDecimal(1));
						int n = insert.executeUpdate();
						if (n == 1)
							salida = "OK";

						// actualizacion del origen

						if (vista.equalsIgnoreCase("vcajasubcontabilidad")) { // tesoreria
							PreparedStatement origen = dbconn
									.prepareStatement("update cajamovteso set idasociacion=? where comprob_mt=? and idempresa=?");
							origen.setBigDecimal(1, idAsociacion);
							origen.setBigDecimal(2, rsSalida
									.getBigDecimal("comprobante"));
							origen.setBigDecimal(3, idempresa);
							int o = origen.executeUpdate();
						}

						if (vista
								.equalsIgnoreCase("vproveedoressubcontabilidad")) { // proveedores
							PreparedStatement origen = dbconn
									.prepareStatement("update proveedocontprov set idasociacion=? where compr_con=? and idempresa=?");
							origen.setBigDecimal(1, idAsociacion);
							origen.setBigDecimal(2, rsSalida
									.getBigDecimal("comprobante"));
							origen.setBigDecimal(3, idempresa);
							int o = origen.executeUpdate();
						}

						if (vista.equalsIgnoreCase("vstocksubcontabilidad")) { // stock
							PreparedStatement origen = dbconn
									.prepareStatement("update stockcontstock set idasociacion=? where nint_ms_cs=? and idempresa=?");
							origen.setBigDecimal(1, idAsociacion);
							origen.setBigDecimal(2, rsSalida
									.getBigDecimal("comprobante"));
							origen.setBigDecimal(3, idempresa);
							int o = origen.executeUpdate();
						}

						auxQuery = "select max(idasiento) as curasiento from contableasientos where idempresa = "
								+ idempresa.toString()
								+ " and ejercicio = "
								+ idEjercicio;
						statement = dbconn.createStatement();
						rsCurAsiento = statement.executeQuery(auxQuery);

						BigDecimal curAsiento = new BigDecimal(0);
						int renglon = 0;
						while (rsCurAsiento != null && rsCurAsiento.next()) {
							curAsiento = rsCurAsiento
									.getBigDecimal("curasiento");
						}

						while (rsSalida.getBigDecimal("comprobante").compareTo(
								curcomp) == 0
								&& !rsSalida.isLast()) {
							int tipomov = 1;
							BigDecimal curCC = new BigDecimal(0);
							BigDecimal curSubCC = new BigDecimal(0);
							if (rsSalida.getString("cencosto") != null)
								curCC = rsSalida.getBigDecimal("cencosto");
							if (rsSalida.getString("subcencosto") != null)
								curSubCC = rsSalida
										.getBigDecimal("subcencosto");
							System.out.println("----Grabo detalle nro: "
									+ curcomp.toString());
							if (rsSalida.getString("tipomov").equalsIgnoreCase(
									"H"))
								tipomov = 2;
							PreparedStatement insert2 = dbconn
									.prepareStatement("insert into contableinfimov(idasiento, renglon, cuenta, tipomov, importe, detalle, asie_nue, cent_cost, cent_cost1, idempresa, usuarioalt) values(?,?,?,?,?,?,?,?,?,?,?) ");
							insert2.setBigDecimal(1, curAsiento);
							insert2.setInt(2, ++renglon);
							insert2.setBigDecimal(3, rsSalida
									.getBigDecimal("cuenta"));
							insert2.setInt(4, tipomov);
							insert2.setBigDecimal(5, rsSalida
									.getBigDecimal("importe"));
							insert2.setString(6, rsSalida.getString("detalle"));
							insert2.setInt(7, -1);
							insert2.setBigDecimal(8, curCC);
							insert2.setBigDecimal(9, curSubCC);
							insert2.setBigDecimal(10, idempresa);
							insert2.setString(11, "SC");
							n = insert2.executeUpdate();
							if (n == 1)
								salida = "OK";
							rsSalida.next();
						}

						proximoAsiento = proximoAsiento.add(new BigDecimal(1));
					}

				}

			}
		} catch (SQLException sqlException) {
			log.error("setLibroDiarioSC_BACKUP_20090706(FECHAS)/Error SQL: "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("setLibroDiarioSC_BACKUP_20090706(FECHAS)/Salida por exception: "
							+ ex);
		}
		return salida;
	}

	public String setLibroDiarioSC(int idEjercicio, String fechaDesde,
			String fechaHasta, String vista, String usuarioalt,
			BigDecimal idempresa) throws EJBException, SQLException {
		// 20081011 - CEP - Objetivo: Pasar la SC a la contabilidad general
		// todo: evaluar el ejercicio contable de la siguiente forma
		// . Que este activo
		// . que los asientos que vienen esten en ese rango de fechas.
		// . CUIDADO. En las vistas de SC no tengo forma de tener ejercicio.
		// Arreglar curasiento.
		// 2012-09-17: incorporo ventas
		
		ResultSet rsSalida = null;
		String salida = "OK";
		BigDecimal proximoAsiento = new BigDecimal(0);

		dbconn.setAutoCommit(false);

		try {

			proximoAsiento = GeneralBean.getContador("nroasiento", idempresa,
					dbconn);
			if (proximoAsiento == null || proximoAsiento.longValue() < 1)
				throw new Exception(
						"No se pudo recuperar Proximo numero de Asiento.");
			// < --

			String cQuery = ""
					+ " SELECT fechamov, cartera, comprobante, tipomov, cuenta, importe, cencosto, subcencosto, detalle, idempresa  "
					+ "   FROM "
					+ vista
					+ "  WHERE idempresa = "
					+ idempresa.toString()
					+ "    AND fechamov BETWEEN  TO_DATE(?,'dd/mm/yyyy') AND  TO_DATE(?,'dd/mm/yyyy')"
					+ "  ORDER BY comprobante, tipomov, importe desc";

			HashMap mapDH = new HashMap();
			List vecSalida = new ArrayList();
			Iterator iterSalida;
			String datos[] = null;
			PreparedStatement ps = dbconn.prepareStatement(cQuery);
			ps.setString(1, fechaDesde);
			ps.setString(2, fechaHasta);
			ps.execute();
			rsSalida = ps.getResultSet();
			int totalTuplas = 0;

			ResultSetMetaData md = rsSalida.getMetaData();
			while (rsSalida.next()) {
				++totalTuplas;
				int totCampos = md.getColumnCount() - 1;
				String[] sSalida = new String[totCampos + 1];
				int i = 0;
				while (i <= totCampos) {
					sSalida[i] = rsSalida.getString(++i);
				}
				vecSalida.add(sSalida);
			}

			iterSalida = vecSalida.iterator();
			BigDecimal curcomp = new BigDecimal("-999999999999");
			// if (rsSalida.next()) {
			if (iterSalida.hasNext()) {

				log.debug("INICIA.");

				datos = (String[]) iterSalida.next();
				--totalTuplas;

				// while (!rsSalida.isLast()) {
				while (totalTuplas > 0) {

					String sistema = "";
					int renglon = 0;
					BigDecimal curAsiento = new BigDecimal(-1);

					// if
					// (rsSalida.getBigDecimal("comprobante").compareTo(curcomp)
					// != 0)

					if (new BigDecimal(datos[2]).compareTo(curcomp) != 0) {
						// -- grabo cabecera

						BigDecimal idAsociacion = GeneralBean
								.getNextValorSequencia(
										"seq_contableasociaciones", dbconn);
						if (idAsociacion == null
								|| idAsociacion.longValue() < 1) {
							salida = "No se pudo recuperar Proximo numero de Asociacion.";
							break;
						}
						// < --
						// curcomp = rsSalida.getBigDecimal("comprobante");
						curcomp = new BigDecimal(datos[2]);
						log.debug("=========================================");
						log.debug("Cabecera nro: " + curcomp.toString());
						PreparedStatement psAsiento = dbconn
								.prepareStatement(""
										+ "INSERT INTO contableasientos "
										+ "        ( idasiento, ejercicio, fecha, leyenda, nroasiento, asiento_nuevo, "
										+ "          idempresa, usuarioalt,sistema,idasociacion)"
										+ " VALUES (default,?,?,?,?,?,?,?,?,?) ");
						psAsiento.setInt(1, idEjercicio);
						// insert.setTimestamp(2, rsSalida
						// .getTimestamp("fechamov"));
						psAsiento.setTimestamp(2, Timestamp.valueOf(datos[0]));
						// insert.setString(3, rsSalida.getString("detalle"));
						psAsiento.setString(3, datos[8]);
						psAsiento.setBigDecimal(4, proximoAsiento);
						psAsiento.setInt(5, -1);
						psAsiento.setBigDecimal(6, idempresa);
						psAsiento.setString(7, "SC");

						// actualizacion del origen
						PreparedStatement psOrigen = null;
						String letraSistema = "";
						String nombreVar = "";

						if (vista.equalsIgnoreCase("VCAJASUBCONTABILIDAD")) { // tesoreria
							sistema = "TESORERIA";
							letraSistema = "T";
							nombreVar = "tesoFechaUltimoCierreTesoreria";
							psOrigen = dbconn
									.prepareStatement("UPDATE cajamovteso SET idasociacion=? WHERE comprob_mt=? AND idempresa=?");
							psOrigen.setBigDecimal(1, idAsociacion);
							// psOrigen.setBigDecimal(2, rsSalida
							// .getBigDecimal("comprobante"));
							psOrigen.setBigDecimal(2, curcomp);
							psOrigen.setBigDecimal(3, idempresa);

						} else if (vista
								.equalsIgnoreCase("VPROVEEDORESSUBCONTABILIDAD")) { // proveedores
							sistema = "COMPRAS";
							letraSistema = "P";
							nombreVar = "provFechaUltimoCierre";
							psOrigen = dbconn
									.prepareStatement("UPDATE proveedocontprov SET idasociacion=? WHERE compr_con=? AND idempresa=?");
							psOrigen.setBigDecimal(1, idAsociacion);
							// psOrigen.setBigDecimal(2, rsSalida
							// .getBigDecimal("comprobante"));
							psOrigen.setBigDecimal(2, curcomp);
							psOrigen.setBigDecimal(3, idempresa);

							
						} else if (vista
								.equalsIgnoreCase("VCLIENTESSUBCONTABILIDAD")) { // proveedores
							sistema = "VENTAS";
							letraSistema = "C";
							nombreVar = "clienFechaUltimoCierre";
							psOrigen = dbconn
									.prepareStatement("UPDATE clientescontcli SET idasociacion=? WHERE nroint_con=? AND idempresa=?");
							psOrigen.setBigDecimal(1, idAsociacion);
							// psOrigen.setBigDecimal(2, rsSalida
							// .getBigDecimal("comprobante"));
							psOrigen.setBigDecimal(2, curcomp);
							psOrigen.setBigDecimal(3, idempresa);
							
							
						} else if (vista
								.equalsIgnoreCase("VSTOCKSUBCONTABILIDAD")) { // stock
							sistema = "STOCK";
							letraSistema = "S";
							nombreVar = "stockFechaUltimoCierre";
							psOrigen = dbconn
									.prepareStatement("UPDATE stockcontstock SET idasociacion=? WHERE nint_ms_cs=? AND idempresa=?");
							psOrigen.setBigDecimal(1, idAsociacion);
							// psOrigen.setBigDecimal(2, rsSalida
							// .getBigDecimal("comprobante"));
							psOrigen.setBigDecimal(2, curcomp);
							psOrigen.setBigDecimal(3, idempresa);

						}

						psAsiento.setString(8, sistema);
						psAsiento.setBigDecimal(9, idAsociacion);

						salida = GeneralBean.setupVariablesSetValor(nombreVar,
								fechaHasta, letraSistema, usuarioalt,
								idempresa, dbconn);

						if (!salida.equalsIgnoreCase("OK"))
							break;

						int n = psAsiento.executeUpdate();
						if (n != 1) {
							salida = "No se pudo generar asiento.";
							break;
						}

						curAsiento = GeneralBean.getValorSequencia(
								"seq_contableasientos", dbconn);
						if (curAsiento == null || curAsiento.longValue() < 1) {
							salida = "No se pudo recuperar Identificacion Interna de Asiento.";
							break;
						}

						int o = psOrigen.executeUpdate();
						if (o < 1) {
							salida = "Inconsistencia al actualizar origen: "
									+ sistema;
							break;
						}

						// while
						// (rsSalida.getBigDecimal("comprobante").compareTo(
						// curcomp) == 0
						// && !rsSalida.isLast()
						// && salida.equalsIgnoreCase("OK")) {
						log.debug(" ---- ---- ---- ---- ");
						mapDH.clear();
						while (new BigDecimal(datos[2]).compareTo(curcomp) == 0
						// && totalTuplas > 0
								&& salida.equalsIgnoreCase("OK")) {

							log.debug("Detalle: " + curcomp);

							int tipomov = 1;
							BigDecimal curCC = new BigDecimal(0);
							BigDecimal curSubCC = new BigDecimal(0);

							// if (rsSalida.getString("cencosto") != null)
							if (datos[6] != null)
								curCC = new BigDecimal(datos[6]);
							// if (rsSalida.getString("subcencosto") != null)
							if (datos[7] != null)
								curSubCC = new BigDecimal(datos[7]);

							// if
							// (rsSalida.getString("tipomov").equalsIgnoreCase(
							// "H"))

							mapDH.put(datos[3], datos[3]);
							if (datos[3].equalsIgnoreCase("H"))
								tipomov = 2;
							PreparedStatement psInfimov = dbconn
									.prepareStatement(""
											+ "INSERT INTO contableinfimov"
											+ "           (idasiento, renglon, cuenta, tipomov, importe, detalle, asie_nue, cent_cost, cent_cost1, idempresa, usuarioalt)"
											+ "     VALUES(?,?,?,?,?,?,?,?,?,?,?) ");
							psInfimov.setBigDecimal(1, curAsiento);
							psInfimov.setInt(2, ++renglon);
							// insert2.setBigDecimal(3, rsSalida
							// .getBigDecimal("cuenta"));
							psInfimov
									.setBigDecimal(3, new BigDecimal(datos[4]));
							psInfimov.setInt(4, tipomov);
							// insert2.setBigDecimal(5, rsSalida
							// .getBigDecimal("importe"));
							psInfimov
									.setBigDecimal(5, new BigDecimal(datos[5]));
							// insert2.setString(6,
							// rsSalida.getString("detalle"));
							psInfimov.setString(6, datos[8]);
							psInfimov.setInt(7, -1);
							psInfimov.setBigDecimal(8, curCC);
							psInfimov.setBigDecimal(9, curSubCC);
							psInfimov.setBigDecimal(10, idempresa);
							psInfimov.setString(11, "SC");

							n = psInfimov.executeUpdate();

							if (n != 1) {
								salida = "Error al generar asiento ...";
								break;
							}

							// rsSalida.next();
							if (totalTuplas == 0)
								break;
							datos = (String[]) iterSalida.next();
							--totalTuplas;

						}

						if (!salida.equalsIgnoreCase("OK"))
							break;

						if (mapDH.isEmpty() || mapDH.size() < 2) {
							salida = "Comprobante: " + curcomp
									+ ", no conforma un asiento consistente.";
							break;
						}

						proximoAsiento = GeneralBean.getContador("nroasiento",
								idempresa, dbconn);
						if (proximoAsiento == null
								|| proximoAsiento.longValue() < 1) {
							salida = "No se pudo recuperar Proximo numero de Asiento.";
							break;
						}

						log.debug("LOOP.");
						// < --
					}

					if (!salida.equalsIgnoreCase("OK"))
						break;

				}

				log.debug("SALE.");

			}

		} catch (SQLException sqlException) {
			salida = "(SQLE) No se pudo efectuar pasaje a la contabilidad.";
			log.error("setLibroDiarioSC(FECHAS)/Error SQL: " + sqlException);
		} catch (Exception ex) {
			salida = "(EX) No se pudo efectuar pasaje a la contabilidad.";
			log.error("setLibroDiarioSC(FECHAS)/Salida por exception: " + ex);
		}

		log.debug("SALIDA: " + salida);
		if (salida.equalsIgnoreCase("OK")) {
			dbconn.commit();
		} else
			dbconn.rollback();

		dbconn.setAutoCommit(false);

		return salida;
	}

	/**
	 * OBJETIVO: MANEJAR ASIENTOS. Metodos para la entidad: contableAsientos
	 * Copyrigth(r) sysWarp S.R.L. Fecha de creacion: Wed Sep 10 11:42:24
	 * GMT-03:00 2008 RELEASE
	 */
	// para todo (ordena por el segundo campo por defecto)
	public List getContableAsientosAll(long limit, long offset,
			BigDecimal ejercicioactivo, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT idasiento,ejercicio,fecha,leyenda,nroasiento,tipo_asiento,asiento_nuevo,"
				+ "       idempresa,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM contableasientos WHERE idempresa = "
				+ idempresa.toString() + " AND ejercicio="
				+ ejercicioactivo.toString() + "  ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getContableAsientosOcu(long limit, long offset,
			String ocurrencia, BigDecimal ejercicioactivo, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idasiento,ejercicio,fecha,leyenda,nroasiento,tipo_asiento,asiento_nuevo,"
				+ "       idempresa,usuarioalt,usuarioact,fechaalt,fechaact"
				+ "  FROM contableasientos WHERE (nroasiento::VARCHAR LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%')  AND idempresa = "
				+ idempresa.toString() + " AND ejercicio="
				+ ejercicioactivo.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getContableAsientosPK(BigDecimal idasiento,
			BigDecimal ejercicioactivo, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idasiento,ejercicio,fecha,leyenda,nroasiento,tipo_asiento,asiento_nuevo,"
				+ "       idempresa,usuarioalt,usuarioact,fechaalt,fechaact"
				+ "  FROM contableasientos WHERE idasiento="
				+ idasiento.toString() + " AND idempresa = "
				+ idempresa.toString() + "AND ejercicio="
				+ ejercicioactivo.toString() + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public BigDecimal getNumeroAsiento(BigDecimal idasiento,
			BigDecimal ejercicioactivo, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT nroasiento FROM contableasientos WHERE idasiento="
				+ idasiento.toString()
				+ " AND idempresa = "
				+ idempresa.toString()
				+ " AND ejercicio = "
				+ ejercicioactivo.toString() + ";";

		BigDecimal nroasiento = new BigDecimal(-1);
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);

			if (rsSalida != null) {

				if (rsSalida.next()) {
					nroasiento = rsSalida.getBigDecimal(1);
				} else
					log.warn("NO next - getNumeroAsiento : " + nroasiento);
			} else
				log.warn("NULO - getNumeroAsiento : " + nroasiento);

		} catch (SQLException sqlException) {
			log.error("Error SQL en el metodo : getNumeroAsiento( ... ) "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getNumeroAsiento( ... )  "
							+ ex);
		}

		return nroasiento;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String contableAsientosDelete(BigDecimal idasiento,
			BigDecimal ejercicioactivo, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM contableasientos WHERE idasiento="
				+ idasiento.toString() + " AND ejercicio = "
				+ ejercicioactivo.toString() + " AND idempresa = "
				+ idempresa.toString();
		String salida = "OK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM contableasientos WHERE idasiento="
						+ idasiento.toString() + " AND ejercicio = "
						+ ejercicioactivo.toString() + " AND idempresa = "
						+ idempresa.toString();
				int i = statement.executeUpdate(cQuery);
				if (i != 1)
					salida = "Error al eliminar cabecera de asiento.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log.error("Error SQL en el metodo : contableLeyendasDelete( ... ) "
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: contableLeyendasDelete( ... )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String contableAsientosCreate(BigDecimal idasiento,
			BigDecimal ejercicio, Timestamp fecha, String leyenda,
			BigDecimal nroasiento, String tipo_asiento,
			BigDecimal asiento_nuevo, BigDecimal idempresa, String usuarioalt)
			throws EJBException {
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (ejercicio == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: ejercicio ";
		if (nroasiento == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nroasiento ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones

		try {
			if (salida.equalsIgnoreCase("OK")) {
				String ins = "INSERT INTO contableasientos"
						+ "(idasiento, ejercicio, fecha, leyenda, nroasiento, tipo_asiento, asiento_nuevo, "
						+ "idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, idasiento);
				insert.setBigDecimal(2, ejercicio);
				insert.setTimestamp(3, fecha);
				insert.setString(4, leyenda);
				insert.setBigDecimal(5, nroasiento);
				insert.setString(6, tipo_asiento);
				insert.setBigDecimal(7, asiento_nuevo);
				insert.setBigDecimal(8, idempresa);
				insert.setString(9, usuarioalt);
				int n = insert.executeUpdate();
				if (n != 1)
					salida = "Imposible generar ";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String contableAsientosCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String contableAsientosCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String contableAsientosCreateOrUpdate(BigDecimal idasiento,
			BigDecimal ejercicio, Timestamp fecha, String leyenda,
			BigDecimal nroasiento, String tipo_asiento,
			BigDecimal asiento_nuevo, BigDecimal idempresa, String usuarioact)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idasiento == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idasiento ";
		if (ejercicio == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: ejercicio ";
		if (nroasiento == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nroasiento ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM contableasientos WHERE idasiento = "
					+ idasiento.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE contableasientos SET ejercicio=?, fecha=?, leyenda=?, nroasiento=?, tipo_asiento=?, asiento_nuevo=?, idempresa=?, usuarioact=?, fechaact=? WHERE idasiento=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, ejercicio);
					insert.setTimestamp(2, fecha);
					insert.setString(3, leyenda);
					insert.setBigDecimal(4, nroasiento);
					insert.setString(5, tipo_asiento);
					insert.setBigDecimal(6, asiento_nuevo);
					insert.setBigDecimal(7, idempresa);
					insert.setString(8, usuarioact);
					insert.setTimestamp(9, fechaact);
					insert.setBigDecimal(10, idasiento);
				} else {
					String ins = "INSERT INTO contableasientos(ejercicio, fecha, leyenda, nroasiento, tipo_asiento, asiento_nuevo, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setBigDecimal(1, ejercicio);
					insert.setTimestamp(2, fecha);
					insert.setString(3, leyenda);
					insert.setBigDecimal(4, nroasiento);
					insert.setString(5, tipo_asiento);
					insert.setBigDecimal(6, asiento_nuevo);
					insert.setBigDecimal(7, idempresa);
					insert.setString(8, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String contableAsientosCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String contableAsientosCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String contableAsientosUpdate(BigDecimal idasiento,
			BigDecimal ejercicio, Timestamp fecha, String leyenda,
			BigDecimal nroasiento, String tipo_asiento,
			BigDecimal asiento_nuevo, BigDecimal idempresa, String usuarioact)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idasiento == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idasiento ";
		if (ejercicio == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: ejercicio ";
		if (nroasiento == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nroasiento ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM contableasientos WHERE idasiento = "
					+ idasiento.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE contableasientos SET ejercicio=?, fecha=?,  leyenda=?, nroasiento=?, tipo_asiento=?, asiento_nuevo=?, idempresa=?, usuarioact=?, fechaact=? WHERE idasiento=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, ejercicio);
					insert.setTimestamp(2, fecha);
					insert.setString(3, leyenda);
					insert.setBigDecimal(4, nroasiento);
					insert.setString(5, tipo_asiento);
					insert.setBigDecimal(6, asiento_nuevo);
					insert.setBigDecimal(7, idempresa);
					insert.setString(8, usuarioact);
					insert.setTimestamp(9, fechaact);
					insert.setBigDecimal(10, idasiento);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String contableAsientosUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String contableAsientosUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * RECUPERA ASIENTO POR PK
	 */
	public List getAsientosPK(int idEjercicio, Long idAsiento,
			BigDecimal idempresa) throws EJBException {
		// 20080911 - EJV - contableasientos reemplazo a contableleyendas
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT mov.idasiento, mov.renglon, mov.tipomov,ley.fecha, mov.detalle, ley.leyenda,"
				+ "       mov.cuenta, mov.importe::numeric(18,2)  "
				+ "  FROM contableinfimov"
				+ " mov INNER JOIN contableasientos"
				+ "  ley on mov.idasiento = ley.idasiento and mov.idempresa = ley.idempresa "
				+ "  WHERE  mov.idasiento =   " + idAsiento.longValue()
				+ " and ley.idempresa = " + idempresa.toString()
				+ " AND ley.ejercicio = " + idEjercicio
				+ " ORDER BY mov.renglon ";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * ALTA ASIENTO
	 */

	public String contableGenerarAsiento(BigDecimal idasiento,
			BigDecimal ejercicio, Timestamp fecha, String leyenda,
			String tipo_asiento, BigDecimal asiento_nuevo, Hashtable htAsiento,
			String usuarioalt, BigDecimal idempresa) throws EJBException,
			SQLException {

		String salida = "OK";
		BigDecimal nroasiento = new BigDecimal(-1);
		try {

			dbconn.setAutoCommit(false);

			nroasiento = new BigDecimal(GeneralBean.getContador("nroasiento",
					idempresa, dbconn)
					+ "");

			if (nroasiento != null && nroasiento.longValue() > 0) {

				salida = contableNuevoAsiento(idasiento, nroasiento, ejercicio,
						fecha, leyenda, tipo_asiento, asiento_nuevo, htAsiento,
						usuarioalt, idempresa);

			}

			else
				salida = "Imposible recuperar nro. nuevo asiento.";

		} catch (SQLException sqlException) {
			log.error("Error SQL: " + sqlException);
			salida = "Error sql al insertar registro para asiento nro.: "
					+ nroasiento;
		} catch (Exception ex) {
			log.error("Salida por exception : " + ex);
			salida = "Error inesperado al insertar registro para asiento  nro.: "
					+ nroasiento;
		}

		if (salida.equalsIgnoreCase("OK")) {
			dbconn.commit();
			salida = nroasiento.toString();
		} else {
			dbconn.rollback();
		}

		dbconn.setAutoCommit(true);
		return salida;
	}

	public String contableNuevoAsiento(BigDecimal idasiento,
			BigDecimal nroasiento, BigDecimal ejercicio, Timestamp fecha,
			String leyenda, String tipo_asiento, BigDecimal asiento_nuevo,
			Hashtable htAsiento, String usuarioalt, BigDecimal idempresa)
			throws EJBException, SQLException {

		Enumeration keysAsiento = htAsiento.keys();
		String[] datosAsiento = null;

		BigDecimal cuenta = null;
		String detalle = "";
		BigDecimal importe = new BigDecimal(0);
		BigDecimal tipomov = new BigDecimal(0);
		int renglon = 0;
		String salida = "OK";

		if (idasiento == null || idasiento.longValue() < 1) {
			idasiento = GeneralBean.getNextValorSequencia(
					"seq_contableasientos", dbconn);

			if (idasiento == null) {
				salida = "Imposible recuperar idasiento.";
			}

		}

		BigDecimal cent_cost = null;
		BigDecimal cent_cost1 = null;

		try {
			/** INICIA TRANSACCION * */

			if (salida.equalsIgnoreCase("OK")) {

				salida = contableAsientosCreate(idasiento, ejercicio, fecha,
						leyenda, nroasiento, tipo_asiento, asiento_nuevo,
						idempresa, usuarioalt);

				if (htAsiento == null || htAsiento.isEmpty())
					salida = "Error: No existen datos para generar asiento. ";

				// TODO : validar que tenga elementos ....

				if (salida.equalsIgnoreCase("OK")) {

					Vector vecSort = new Vector(htAsiento.keySet());
					Collections.sort(vecSort);
					keysAsiento = vecSort.elements();

					// 

					while (keysAsiento.hasMoreElements()
							&& salida.equalsIgnoreCase("OK")) {

						String key = (String) keysAsiento.nextElement();

						datosAsiento = (String[]) htAsiento.get(key);

						cuenta = new BigDecimal(datosAsiento[0]);
						detalle = (String) datosAsiento[2];
						importe = new BigDecimal(datosAsiento[3]);
						tipomov = new BigDecimal(datosAsiento[4]);

						Long cc0 = getCC1Cuenta(ejercicio.intValue(), new Long(
								cuenta.longValue()), idempresa);
						Long cc1 = getCC2Cuenta(ejercicio.intValue(), new Long(
								cuenta.longValue()), idempresa);

						cent_cost = cc0 != null ? new BigDecimal(cc0
								.longValue()) : null;
						cent_cost1 = cc1 != null ? new BigDecimal(cc1
								.longValue()) : null;

						/**
						 * Validaciones
						 */

						if (cuenta.longValue() == 0)
							salida = "Error: No se puede dejar vacia la cuenta";
						if (!isImputable(ejercicio.intValue(), new Long(cuenta
								.longValue()), idempresa))
							salida = "Error: la cuenta nro. " + cuenta
									+ " no es imputable";
						if (detalle.equalsIgnoreCase(""))
							salida = "Error: No se puede dejar en blanco el detalle";
						if (importe.compareTo(new BigDecimal(0)) <= 0)
							salida = "Error: Importe debe ser mayor a cero";
						// if(tipomov < 1) salida = "Error: VERIFICAR";
						if (cent_cost == null)
							salida = "Error: No hay centro de costo asociado a la cuenta: "
									+ cuenta;
						if (cent_cost1 == null)
							salida = "Error: No hay centro de costo asociado a la cuenta: "
									+ cuenta;
						// Pregunta por renglon para no validar siempre, solo la
						// primera
						// vez

						if (salida.equalsIgnoreCase("OK")) {

							salida = contableInfimovCreate(idasiento,
									new BigDecimal(++renglon), cuenta, tipomov,
									importe, detalle, asiento_nuevo, cent_cost,
									cent_cost1, idempresa, usuarioalt);

						}

					}

				}

			}

		} catch (Exception ex) {
			log.error("contableNuevoAsiento()- Salida por exception : " + ex);
			salida = "Error inesperado al insertar registro para asiento  nro.: "
					+ nroasiento + " , cuenta nro :   " + cuenta;
		}

		return salida;

	}

	public String contableEliminarAsiento(BigDecimal idasiento,
			BigDecimal ejercicio, BigDecimal idempresa) throws SQLException,
			EJBException {

		String salida = "";

		try {

			dbconn.setAutoCommit(false);

			salida = contableEliminarAsiento(idasiento, ejercicio, dbconn,
					idempresa);

		} catch (SQLException sqlException) {
			log.error("Error SQL: " + sqlException);
			salida = "Error sql al eliminar asiento nro.: " + idasiento;
		} catch (Exception ex) {
			log.error("Salida por exception : " + ex);
			salida = "Error inesperado al eliminar asiento  nro.: " + idasiento;
		}

		if (salida.equalsIgnoreCase("OK"))
			dbconn.commit();
		else
			dbconn.rollback();

		dbconn.setAutoCommit(true);

		return salida;
	}

	public String contableEliminarAsiento(BigDecimal idasiento,
			BigDecimal ejercicio, Connection conn, BigDecimal idempresa)
			throws SQLException, EJBException {

		String salida = "";

		try {

			salida = contableInfimovDelete(idasiento, idempresa);

			if (salida.equalsIgnoreCase("OK")) {

				salida = contableAsientosDelete(idasiento, ejercicio, idempresa);

			}

		} catch (Exception ex) {
			log.error("Salida por exception : " + ex);
			salida = "Error inesperado al eliminar asiento  nro.: " + idasiento;
		}

		return salida;
	}

	public String contableActualizarAsiento(BigDecimal idasiento,
			BigDecimal ejercicio, Timestamp fecha, String leyenda,
			String tipo_asiento, BigDecimal asiento_nuevo, Hashtable htAsiento,
			String usuarioalt, BigDecimal idempresa) throws SQLException,
			EJBException {

		String salida = "";
		BigDecimal nroasiento = new BigDecimal(-1);

		try {

			dbconn.setAutoCommit(false);

			nroasiento = getNumeroAsiento(idasiento, ejercicio, idempresa);

			if (nroasiento.longValue() > 0) {

				salida = contableEliminarAsiento(idasiento, ejercicio, dbconn,
						idempresa);

				if (salida.equalsIgnoreCase("OK")) {

					salida = contableNuevoAsiento(idasiento, nroasiento,
							ejercicio, fecha, leyenda, tipo_asiento,
							asiento_nuevo, htAsiento, usuarioalt, idempresa);

				}
			} else {
				salida = "Imposible capturar nro. asiento.";
			}

		} catch (Exception e) {

		}

		if (salida.equalsIgnoreCase("OK")) {
			salida = nroasiento.toString();
			dbconn.commit();

		} else {
			dbconn.rollback();
		}

		dbconn.setAutoCommit(true);

		return salida;
	}

	/**
	 * Metodos para la entidad: contableInfimov Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Fri Sep 12 10:02:05 GMT-03:00 2008
	 * 
	 */

	// por primary key (primer campo por defecto)
	public List getContableInfimovPK(BigDecimal idasiento,
			BigDecimal ejercicio, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT im.idasiento,im.renglon,ip.idcuenta,ip.cuenta,im.tipomov,im.importe,im.detalle,im.asie_nue,im.cent_cost,im.cent_cost1,"
				+ "       im.idempresa,im.usuarioalt,im.usuarioact,im.fechaalt,im.fechaact"
				+ "  FROM contableinfimov im "
				+ "       INNER JOIN contableinfiplan ip ON im.cuenta = ip.idcuenta AND im.idempresa = ip.idempresa "
				+ " WHERE im.idasiento=" + idasiento.toString()
				+ " AND im.idempresa = " + idempresa.toString()
				+ " AND ip.ejercicio = " + ejercicio.toString()
				+ " ORDER BY 2;";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt
	public String contableInfimovCreate(BigDecimal idasiento,
			BigDecimal renglon, BigDecimal cuenta, BigDecimal tipomov,
			BigDecimal importe, String detalle, BigDecimal asie_nue,
			BigDecimal cent_cost, BigDecimal cent_cost1, BigDecimal idempresa,
			String usuarioalt) throws EJBException {
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (renglon == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: renglon ";
		if (cuenta == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cuenta ";
		if (tipomov == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipomov ";
		if (importe == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: importe ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones

		try {
			if (salida.equalsIgnoreCase("OK")) {
				String ins = ""
						+ "INSERT INTO contableinfimov"
						+ "   (idasiento, renglon, cuenta, tipomov, importe, detalle, asie_nue, cent_cost, cent_cost1, idempresa, usuarioalt )"
						+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, idasiento);
				insert.setBigDecimal(2, renglon);
				insert.setBigDecimal(3, cuenta);
				insert.setBigDecimal(4, tipomov);
				insert.setBigDecimal(5, importe);
				insert.setString(6, detalle);
				insert.setBigDecimal(7, asie_nue);
				insert.setBigDecimal(8, cent_cost);
				insert.setBigDecimal(9, cent_cost1);
				insert.setBigDecimal(10, idempresa);
				insert.setString(11, usuarioalt);
				int n = insert.executeUpdate();
				if (n != 1)
					salida = "Imposible generar detalle de asiento.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String contableInfimovCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String contableInfimovCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String contableInfimovDelete(BigDecimal idasiento,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM contableinfimov WHERE idasiento="
				+ idasiento.toString() + " AND idempresa = "
				+ idempresa.toString();
		String salida = "OK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM CONTABLEINFIMOV WHERE idasiento="
						+ idasiento.toString() + " AND idempresa = "
						+ idempresa.toString();
				int i = statement.executeUpdate(cQuery);

				if (i < 1)
					salida = "Error: No se existe detalle para el asiento";

			} else {
				salida = "Error: Registro detalle inexistente. ";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : contableInfimovDelete( BigDecimal idasiento ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: contableInfimovDelete( BigDecimal idasiento )  "
							+ ex);
		}
		return salida;
	}

	// /**
	// * ELIMINAR ASIENTO
	// */
	//

	/**
	 * ACTUALIZA ASIENTO
	 */

	/**
	 * RENUMERA ASIENTOS. 20060609. se agrego campo nroasiento en la tabla
	 * leyendas, el mismo tiene solo un fin de orden contable, no tiene efectos
	 * sobre la logica del programa, la unica restriccion es que un campo
	 * unique. 20080917. Reemplazo de version anterior de renumaracion.
	 */

	public String contableAsientosRenumeracion(BigDecimal ejercicio,
			String usuarioact, BigDecimal idempresa) throws EJBException,
			SQLException {
		String salida = "OK";
		PreparedStatement pstatment = null;
		String sql = "";

		dbconn.setAutoCommit(false);
		int j = -1;

		try {
			sql = ""
					+ "CREATE TEMPORARY TABLE tmp_renumerar_asientos "
					+ "(idasiento numeric(18), ejercicio numeric(18), "
					+ " idempresa numeric(18), nroasiento numeric(18), nuevonroasiento bigserial) ;";

			pstatment = dbconn.prepareStatement(sql);
			j = pstatment.executeUpdate();

			if (j == 0) {

				sql = " INSERT INTO tmp_renumerar_asientos (idasiento, ejercicio, idempresa, nroasiento)"
						+ "  SELECT idasiento, ejercicio, idempresa, nroasiento"
						+ "    FROM contableasientos WHERE ejercicio = ? AND idempresa = ? "
						+ "   ORDER BY fecha;";

				pstatment = dbconn.prepareStatement(sql);
				pstatment.setBigDecimal(1, ejercicio);
				pstatment.setBigDecimal(2, idempresa);
				j = pstatment.executeUpdate();

				if (j > 0) {

					sql = "     UPDATE contableasientos"
							+ "    SET nroasiento = nroasiento*(-1)"
							+ "  WHERE ejercicio = ? AND idempresa = ? ; ";

					pstatment = dbconn.prepareStatement(sql);
					pstatment.setBigDecimal(1, ejercicio);
					pstatment.setBigDecimal(2, idempresa);

					j = pstatment.executeUpdate();

					if (j > 0) {

						sql = "    UPDATE contableasientos "
								+ "   SET nroasiento = tmp.nuevonroasiento,"
								+ "       usuarioact = ?, "
								+ "       fechaact   = now() "
								+ "  FROM tmp_renumerar_asientos tmp "
								+ " WHERE contableasientos.idasiento = tmp.idasiento "
								+ "   AND contableasientos.idempresa = tmp.idempresa "
								+ "   AND contableasientos.ejercicio = tmp.ejercicio;";

						pstatment = dbconn.prepareStatement(sql);
						pstatment.setString(1, usuarioact);
						j = pstatment.executeUpdate();

						if (j != 0) {

							sql = "   UPDATE globalcontadores "
									+ "  SET valor =  ("
									+ "                SELECT MAX(nroasiento) + 1  "
									+ "                  FROM contableasientos"
									+ "                 WHERE ejercicio = ? AND idempresa = ?"
									+ "               ) "
									+ "    WHERE LOWER(contador) = 'nroasiento' and idempresa = ? ";

							pstatment = dbconn.prepareStatement(sql);
							pstatment.setBigDecimal(1, ejercicio);
							pstatment.setBigDecimal(2, idempresa);
							pstatment.setBigDecimal(3, idempresa);

							j = pstatment.executeUpdate();

							if (j == 0) {
								salida = "Renumeracion: E5 - Asignar nro contador empresa.";
							}

						} else
							salida = "Renumeracion: E4 - Asignar nuevo nro.";

					} else
						salida = "Renumeracion: E3 - Negar nroasiento.";

				} else
					salida = "Renumeracion: E2 - Cargar tabla TMP. Posiblemente no haya asientos para actualizar.";

				sql = "DROP TABLE tmp_renumerar_asientos CASCADE;";
				pstatment = dbconn.prepareStatement(sql);
				pstatment.executeUpdate();

			} else
				salida = "Renumeracion: E1 - Crear tabla TMP.";

		} catch (Exception e) {
			salida = "E0 - Excepcion al renumerar asientos.";
			log.error("contableAsientosRenumeracion(...):" + e);
		}

		if (salida.equalsIgnoreCase("OK")) {
			dbconn.commit();
		} else {
			dbconn.rollback();
		}

		dbconn.setAutoCommit(true);
		return salida;

	}

	public List getLibroMayor(int idEjercicio, Long cuenta, int anio, int mes,
			Long cc1, Long cc2, BigDecimal idempresa) throws EJBException {
		/**
		 * Utilidad : Metodo que conforma el libro mayor
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer todos los centros de costos existentes
		 */
		ResultSet rsSalida = null;
		List vecSalida = new ArrayList();
		try {
			Statement statement = dbconn.createStatement();
			if (clase.equalsIgnoreCase("org.postgresql.Driver")) {
				Postgres db = new Postgres();
				rsSalida = db.getLibroMayor(idEjercicio, cuenta, anio, mes,
						cc1, cc2, idempresa);
			}
			// todo : trabajar el resto de los drivers
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
			log.error("Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception: " + ex);
		}
		return vecSalida;
	}

	public List getLibroMayorSinCC(BigDecimal idcuenta_desde,
			BigDecimal idcuenta_hasta, java.sql.Date fecha_desde,
			java.sql.Date fecha_hasta, BigDecimal anio, BigDecimal idempresa)
			throws EJBException {
		/**
		 * Utilidad : Metodo que conforma el libro mayor
		 * 
		 * Utilidad : traer todos los centros de costos existentes
		 */
		ResultSet rsSalida = null;
		List vecSalida = new ArrayList();
		try {
			Statement statement = dbconn.createStatement();

			/*
			 * 
			 * String cQuery = ""; cQuery +=
			 * "SELECT lm.parcuenta, ip.cuenta, lm.paranio, lm.parmes, lm.parcc, lm.parcc1,  "
			 * ; cQuery +=
			 * "       lm.fecha, lm.nroasiento, lm.renglon, lm.debe::numeric(18,2) as debe, "
			 * ; cQuery +=
			 * "       lm.haber::numeric(18,2) as haber, lm.centrocosto, lm.subcentrocosto, lm.detalle, lm.idempresa "
			 * ; cQuery += "  FROM contablelibromayor lm "; cQuery +=
			 * "       LEFT JOIN contableinfiplan ip ON lm.parcuenta = ip.idcuenta AND lm.paranio = ip.ejercicio"
			 * ; cQuery += "              AND lm.idempresa = ip.idempresa ";
			 * cQuery += " WHERE lm.parcuenta BETWEEN " +
			 * idcuenta_desde.toString() + " AND " + idcuenta_hasta.toString();
			 * cQuery += "   AND lm.fecha BETWEEN '" + fecha_desde.toString() +
			 * "'::DATE AND '" + fecha_hasta.toString() + "'::DATE"; cQuery +=
			 * "   AND lm.paranio   = " + anio + " "; cQuery +=
			 * "   AND lm.idempresa = " + idempresa.toString(); cQuery +=
			 * " UNION ALL "; cQuery +=
			 * "SELECT lm.parcuenta, ip.cuenta, lm.paranio, 0 AS parmes, 0 AS parcc, 0 AS parcc1,  "
			 * ; cQuery += "       '" + fecha_desde +
			 * "'::DATE  AS fecha, 0 AS nroasiento, 0 AS renglon, SUM(lm.debe::numeric(18,2)) as debe, "
			 * ; cQuery +=
			 * "       SUM(lm.haber::numeric(18,2)) as haber, '0' AS centrocosto, '0' AS subcentrocosto, 'SALDOINICIAL' AS detalle, lm.idempresa "
			 * ; cQuery += "  FROM contablelibromayor lm "; cQuery +=
			 * "       INNER JOIN contableinfiplan ip ON lm.parcuenta = ip.idcuenta AND lm.paranio = ip.ejercicio"
			 * ; cQuery += "              AND lm.idempresa = ip.idempresa ";
			 * cQuery += " WHERE lm.parcuenta BETWEEN " +
			 * idcuenta_desde.toString() + " AND " + idcuenta_hasta.toString();
			 * cQuery += "   AND lm.fecha < '" + fecha_desde.toString() +
			 * "'::DATE "; cQuery += "   AND lm.paranio   = " + anio + " ";
			 * cQuery += "   AND lm.idempresa = " + idempresa.toString(); cQuery
			 * +=
			 * " GROUP BY lm.parcuenta, ip.cuenta, lm.paranio,  lm.idempresa";
			 * cQuery += " ORDER BY parcuenta, fecha ASC, nroasiento";
			 */

			String cQuery = "";
			cQuery += "SELECT lm.parcuenta, ip.cuenta, lm.paranio, lm.parmes, 0 AS parcc, 0 AS parcc1,  ";
			cQuery += "       lm.fecha, 0, 0, SUM(lm.debe::numeric(18,2)) as debe, ";
			cQuery += "       SUM(lm.haber::numeric(18,2)) as haber, '0' AS centrocosto, '0' AS subcentrocosto, '', lm.idempresa ";
			cQuery += "  FROM contablelibromayor lm ";
			cQuery += "       LEFT JOIN contableinfiplan ip ON lm.parcuenta = ip.idcuenta AND lm.paranio = ip.ejercicio";
			cQuery += "              AND lm.idempresa = ip.idempresa ";
			cQuery += " WHERE lm.parcuenta BETWEEN "
					+ idcuenta_desde.toString() + " AND "
					+ idcuenta_hasta.toString();
			cQuery += "   AND lm.fecha BETWEEN '" + fecha_desde.toString()
					+ "'::DATE AND '" + fecha_hasta.toString() + "'::DATE";
			cQuery += "   AND lm.paranio   = " + anio + " ";
			cQuery += "   AND lm.idempresa = " + idempresa.toString();
			cQuery += " GROUP BY lm.parcuenta, ip.cuenta, lm.paranio, lm.parmes, lm.idempresa, lm.fecha";
			cQuery += " UNION ALL ";
			cQuery += "SELECT lm.parcuenta, ip.cuenta, lm.paranio, 0 AS parmes, 0 AS parcc, 0 AS parcc1,  ";
			cQuery += "       '"
					+ fecha_desde
					+ "'::DATE  AS fecha, 0 AS nroasiento, 0 AS renglon, SUM(lm.debe::numeric(18,2)) as debe, ";
			cQuery += "       SUM(lm.haber::numeric(18,2)) as haber, '0' AS centrocosto, '0' AS subcentrocosto, 'SALDOINICIAL' AS detalle, lm.idempresa ";
			cQuery += "  FROM contablelibromayor lm ";
			cQuery += "       INNER JOIN contableinfiplan ip ON lm.parcuenta = ip.idcuenta AND lm.paranio = ip.ejercicio";
			cQuery += "              AND lm.idempresa = ip.idempresa ";
			cQuery += " WHERE lm.parcuenta BETWEEN "
					+ idcuenta_desde.toString() + " AND "
					+ idcuenta_hasta.toString();
			cQuery += "   AND lm.fecha < '" + fecha_desde.toString()
					+ "'::DATE ";
			cQuery += "   AND lm.paranio   = " + anio + " ";
			cQuery += "   AND lm.idempresa = " + idempresa.toString();
			cQuery += " GROUP BY lm.parcuenta, ip.cuenta, lm.paranio,  lm.idempresa";
			cQuery += " ORDER BY parcuenta, fecha ASC";
			// cQuery += " ORDER BY parcuenta, fecha ASC, nroasiento";

			log.info("CQUERY: " + cQuery);

			rsSalida = statement.executeQuery(cQuery);

			ResultSetMetaData md = rsSalida.getMetaData();
			BigDecimal acumulado = new BigDecimal(0);
			String cuenta = "";

			while (rsSalida.next()) {
				int totCampos = md.getColumnCount() - 1;
				String[] sSalida = new String[totCampos + 2];
				int i = 0;
				while (i <= totCampos) {
					sSalida[i] = rsSalida.getString(++i);
				}
				if (!cuenta.equals(sSalida[0])) {
					cuenta = sSalida[0];
					acumulado = new BigDecimal(0);
				}
				acumulado = acumulado.add(new BigDecimal(sSalida[9]));
				acumulado = acumulado.add(new BigDecimal(sSalida[10]).negate());

				// acumulado.add( new BigDecimal(sSalida[9]).negate() );
				sSalida[totCampos + 1] = acumulado.toString();
				vecSalida.add(sSalida);
			}
		} catch (SQLException sqlException) {
			log.error("Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception: " + ex);
		}
		return vecSalida;
	}

	public List getLibroMayorConDetalleSinCC(BigDecimal idcuenta_desde,
			BigDecimal idcuenta_hasta, java.sql.Date fecha_desde,
			java.sql.Date fecha_hasta, BigDecimal anio, BigDecimal idempresa)
			throws EJBException {
		/**
		 * Utilidad : Metodo que conforma el libro mayor
		 * 
		 * Utilidad : traer todos los centros de costos existentes
		 */
		ResultSet rsSalida = null;
		List vecSalida = new ArrayList();
		try {
			Statement statement = dbconn.createStatement();

			String cQuery = "";
			cQuery += "SELECT lm.parcuenta, ip.cuenta, lm.paranio, lm.parmes, lm.parcc, lm.parcc1,  ";
			cQuery += "       lm.fecha, lm.nroasiento, lm.renglon, lm.debe::numeric(18,2) as debe, ";
			cQuery += "       lm.haber::numeric(18,2) as haber, lm.centrocosto, lm.subcentrocosto, lm.detalle, lm.idempresa ";
			cQuery += "  FROM contablelibromayor lm ";
			cQuery += "       LEFT JOIN contableinfiplan ip ON lm.parcuenta = ip.idcuenta AND lm.paranio = ip.ejercicio";
			cQuery += "              AND lm.idempresa = ip.idempresa ";
			cQuery += " WHERE lm.parcuenta BETWEEN "
					+ idcuenta_desde.toString() + " AND "
					+ idcuenta_hasta.toString();
			cQuery += "   AND lm.fecha BETWEEN '" + fecha_desde.toString()
					+ "'::DATE AND '" + fecha_hasta.toString() + "'::DATE";
			cQuery += "   AND lm.paranio   = " + anio + " ";
			cQuery += "   AND lm.idempresa = " + idempresa.toString();
			cQuery += " UNION ALL ";
			cQuery += "SELECT lm.parcuenta, ip.cuenta, lm.paranio, 0 AS parmes, 0 AS parcc, 0 AS parcc1,  ";
			cQuery += "       '"
					+ fecha_desde
					+ "'::DATE  AS fecha, 0 AS nroasiento, 0 AS renglon, SUM(lm.debe::numeric(18,2)) as debe, ";
			cQuery += "       SUM(lm.haber::numeric(18,2)) as haber, '0' AS centrocosto, '0' AS subcentrocosto, 'SALDOINICIAL' AS detalle, lm.idempresa ";
			cQuery += "  FROM contablelibromayor lm ";
			cQuery += "       INNER JOIN contableinfiplan ip ON lm.parcuenta = ip.idcuenta AND lm.paranio = ip.ejercicio";
			cQuery += "              AND lm.idempresa = ip.idempresa ";
			cQuery += " WHERE lm.parcuenta BETWEEN "
					+ idcuenta_desde.toString() + " AND "
					+ idcuenta_hasta.toString();
			cQuery += "   AND lm.fecha < '" + fecha_desde.toString()
					+ "'::DATE ";
			cQuery += "   AND lm.paranio   = " + anio + " ";
			cQuery += "   AND lm.idempresa = " + idempresa.toString();
			cQuery += " GROUP BY lm.parcuenta, ip.cuenta, lm.paranio,  lm.idempresa";
			cQuery += " ORDER BY parcuenta, fecha ASC, nroasiento";

			rsSalida = statement.executeQuery(cQuery);

			ResultSetMetaData md = rsSalida.getMetaData();
			BigDecimal acumulado = new BigDecimal(0);
			String cuenta = "";

			while (rsSalida.next()) {
				int totCampos = md.getColumnCount() - 1;
				String[] sSalida = new String[totCampos + 2];
				int i = 0;
				while (i <= totCampos) {
					sSalida[i] = rsSalida.getString(++i);
				}
				if (!cuenta.equals(sSalida[0])) {
					cuenta = sSalida[0];
					acumulado = new BigDecimal(0);
				}
				acumulado = acumulado.add(new BigDecimal(sSalida[9]));
				acumulado = acumulado.add(new BigDecimal(sSalida[10]).negate());

				// acumulado.add( new BigDecimal(sSalida[9]).negate() );
				sSalida[totCampos + 1] = acumulado.toString();
				vecSalida.add(sSalida);
			}
		} catch (SQLException sqlException) {
			log.error("Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception: " + ex);
		}

		return vecSalida;
	}

	// para poner en el bean contable

	// todas las monedas
	public List getMonedas(BigDecimal idempresa) throws EJBException {
		String cQuery = "Select * from contablemonedas "
				+ " where idempresa = " + idempresa.toString() + "ORDER BY 2";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// todos las monedas por alguna ocurrencia
	public List getMonedas(String ocurrencia, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "Select * from contablemonedas" + " where idempresa = "
				+ idempresa.toString() + " and (idmoneda::VARCHAR LIKE '%"
				+ ocurrencia + "%' OR " + " UPPER(moneda) LIKE '%"
				+ ocurrencia.toUpperCase() + "%') " + " ORDER BY 2";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// monedas por PK
	public List getMonedasPK(Integer pk, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "Select * from contablemonedas where idmoneda = "
				+ pk.toString() + " and idempresa = " + idempresa.toString()
				+ "ORDER BY 2";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// Eliminar monedas por PK
	public String delMonedas(Integer pk, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "Select * from contablemonedas where idmoneda = "
				+ pk.toString() + " and idempresa = " + idempresa.toString()
				+ "ORDER BY 2";
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE from contablemonedas where idmoneda = "
						+ pk.toString() + " and idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "OK";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			log.error("public String delMonedas(Integer pk) Error SQL: "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("public String delMonedas(Integer pk) Salida por exception: "
							+ ex);
		}
		return salida;
	}

	// insertar una moneda

	public String monedasSave(String moneda, Timestamp duracion_hasta,
			int cantidad_ceros, String detalle, String usuarioalt,
			BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";

		// validaciones de datos:
		if (moneda.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacia la descripcion de la moneda";
		if (duracion_hasta == null)
			salida = "Error: No se puede dejar vacia la duracion hasta de la moneda";
		if (cantidad_ceros < 1)
			salida = "Error: La cantidad de ceros es erronea";
		if (detalle.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el detalle";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar el usuario de alta vacio"; // fin
		// validaciones
		try {
			if (salida.equalsIgnoreCase("NOOK")) { // quiere decir que no
				// detecto ninguno de los/
				// errores de validacion
				PreparedStatement insert = dbconn
						.prepareStatement("INSERT INTO contablemonedas(moneda, hasta_mo, ceros_mo,detalle, usuarioalt, idempresa) VALUES(?,?,?,?,?,?)");
				insert.setString(1, moneda);
				insert.setTimestamp(2, duracion_hasta);
				insert.setInt(3, cantidad_ceros);
				insert.setString(4, detalle);
				insert.setString(5, usuarioalt);
				insert.setBigDecimal(6, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "OK";
			}
		} catch (SQLException sqlException) {
			log.error("public String monedasSave Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("public String monedasSave Salida por exception: " + ex);
		}
		return salida;
	}

	// update de una moneda
	public String monedasSaveOrUpdate(long idmoneda, String moneda,
			Timestamp duracion_hasta, int cantidad_ceros, String detalle,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fecha = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:

		if (moneda.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacia la descripcion de la moneda";
		if (duracion_hasta == null)
			salida = "Error: No se puede dejar vacia la duracion hasta de la moneda";
		if (cantidad_ceros < 1)
			salida = "Error: La cantidad de ceros es erronea";
		if (detalle.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el detalle";
		if (usuarioact.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar el usuario de actualizacion vacio"; // fin
		// validaciones
		// fin validaciones
		try {
			ResultSet rsSalida = null;
			String cQuery = "Select count(*) from contablemonedas where idmoneda = "
					+ idmoneda + "and idempresa = " + idempresa.toString();
			;
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			if (total > 0) { // Existe asi que updeteo
				// if (salida.equalsIgnoreCase("OK")) {
				insert = dbconn
						.prepareStatement("UPDATE contablemonedas  SET moneda=?, hasta_mo=?, ceros_mo=?, detalle=?, usuarioact=?, fechaact=? where idmoneda=? and idempresa=?");
				insert.setString(1, moneda);
				insert.setTimestamp(2, duracion_hasta);
				insert.setInt(3, cantidad_ceros);
				insert.setString(4, detalle);
				insert.setString(5, usuarioact);
				insert.setTimestamp(6, fecha);
				insert.setLong(7, idmoneda);
				insert.setBigDecimal(8, idempresa);
			} else {
				insert = dbconn
						.prepareStatement("INSERT INTO contablemonedas(moneda, hasta_mo, ceros_mo,detalle, usuarioalt, idempresa) VALUES(?,?,?,?,?,?)");
				insert.setString(1, moneda);
				insert.setTimestamp(2, duracion_hasta);
				insert.setInt(3, cantidad_ceros);
				insert.setString(4, detalle);
				insert.setString(5, usuarioact);
				insert.setBigDecimal(6, idempresa);
			}
			insert.executeUpdate();
			salida = "OK";
			// }
		} catch (SQLException sqlException) {
			log.error("public String monedasSaveOrUpdate Error SQL: "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("public String monedasSaveOrUpdate Salida por exception: "
							+ ex);
		}
		return salida;
	}

	public List getSaldoCuentasPeriodoAll(int idEjercicio, int mesDesde,
			int mesHasta, BigDecimal idempresa) throws EJBException {

		ResultSet rsSalida = null;
		// String tableName = "contablesaldocuexmes" + idEjercicio;

		String cQuery = ""
				// + "SELECT idcuenta, cuenta, inputable, nivel, resultado,
				// cent_cost1, "
				// + " cent_cost2, sum(debe) AS debe, sum(haber) AS haber,
				// sum(saldo) AS saldo "
				// + " FROM contablesaldocuexmes "
				// + " WHERE idempresa = "
				// + idempresa.toString()
				// + " AND ejercicio = "
				// + idEjercicio
				// + " AND parmes BETWEEN "
				// + mesDesde
				// + " AND "
				// + mesHasta
				// + " GROUP BY idcuenta, cuenta,inputable, nivel, resultado,
				// cent_cost1, cent_cost2"
				// + " ORDER BY idcuenta DESC";

				/*
				 * 20090529 - EJV ...
				 */

				+ "SELECT p.idcuenta, "
				+ "       '-' || repeat('  '::text, p.nivel::integer - 1) || p.cuenta, "
				+ "       p.inputable, p.nivel, p.resultado,  0 AS cc1, 0 AS cc2, "
				+ "       SUM(COALESCE(v.sumadebe, 0)::NUMERIC(18, 2)) AS sumadebe, "
				+ "       SUM(COALESCE(v.sumahaber,0)::NUMERIC(18, 2)) AS sumahaber,  "
				+ "       SUM(COALESCE(v.saldosdebe,0)::NUMERIC(18, 2))AS saldodebe,  "
				+ "       SUM(COALESCE(v.saldoshaber,0)::NUMERIC(18, 2))AS saldoshaber "
				+ "  FROM contableinfiplan p "
				+ "       LEFT JOIN  "
				+ "		( "
				+ "		 SELECT pl.idempresa, pl.ejercicio, pl.idcuenta,  mayor.parmes AS mes, "
				+ "			sum(COALESCE(mayor.debe, 0::numeric))::numeric(18,2) AS sumadebe,  "
				+ "			sum(COALESCE(mayor.haber, 0::numeric))::numeric(18,2) AS sumahaber,  "
				+ "			CASE "
				+ "			    WHEN (sum(COALESCE(mayor.debe, 0::numeric))::numeric(18,2) - sum(COALESCE(mayor.haber, 0::numeric))) >= 0::numeric "
				+ "                            THEN sum(COALESCE(mayor.debe, 0::numeric))::numeric(18,2) - sum(COALESCE(mayor.haber, 0::numeric))::numeric(18,2) "
				+ "			    ELSE 0::numeric "
				+ "			END AS saldosdebe,  "
				+ "			CASE "
				+ "			    WHEN (sum(COALESCE(mayor.debe, 0::numeric))::numeric(18,2) - sum(COALESCE(mayor.haber, 0::numeric))) <= 0::numeric "
				+ "                            THEN sum(COALESCE(mayor.debe, 0::numeric))::numeric(18,2) - sum(COALESCE(mayor.haber, 0::numeric) * (-1)::numeric)::numeric(18,2) "
				+ "			    ELSE 0::numeric "
				+ "			END AS saldoshaber "
				+ "		   FROM contableinfiplan pl "
				+ "		   INNER JOIN contablelibromayor mayor ON pl.idempresa = mayor.idempresa AND pl.ejercicio = mayor.ejercicio AND pl.idcuenta = mayor.parcuenta "
				// + " --AND mayor.parmes BETWEEN 1 AND 12 and pl.ejercicio =
				// 2008 "
				+ "		  GROUP BY pl.idcuenta, pl.idempresa, pl.ejercicio,  mayor.parmes "
				+ "		) v ON  v.mes BETWEEN  "
				+ mesDesde
				+ " AND "
				+ mesHasta
				+ " and  p.ejercicio = "
				+ idEjercicio
				+ " AND p.idcuenta = v.idcuenta "
				+ "             AND p.ejercicio = v.ejercicio AND v.idempresa = p.idempresa  "
				+ " WHERE p.ejercicio = "
				+ idEjercicio
				+ " AND p.idempresa = "
				+ idempresa.toString()
				+ " GROUP BY p.idcuenta, p.cuenta, p.inputable, p.nivel, p.resultado, p.ejercicio "
				+ "ORDER BY idcuenta DESC; ";

		List vecSalida = new ArrayList();
		List vecOrden = new ArrayList();
		float parcialDebe = 0;
		float parcialHaber = 0;
		float parcialTotal = 0;
		boolean flagPrimerN = true;
		int totalList = 0;
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

				if (sSalida[2].equalsIgnoreCase("s")) {
					parcialDebe += Float.parseFloat(sSalida[7]);
					parcialHaber += Float.parseFloat(sSalida[8]);
					parcialTotal += Float.parseFloat(sSalida[9]);
					flagPrimerN = true;
				} else {
					if (flagPrimerN) {
						sSalida[7] = parcialDebe + "";
						sSalida[8] = parcialHaber + "";
						sSalida[9] = parcialTotal + "";
						parcialDebe = 0;
						parcialHaber = 0;
						parcialTotal = 0;
						flagPrimerN = false;

					}
				}
				vecOrden.add(sSalida);
			}
			totalList = vecOrden.size();
			while (totalList != 0) {
				vecSalida.add(vecOrden.get(totalList - 1));
				totalList--;
			}
		} catch (SQLException sqlException) {
			log.error("Error SQL(getSaldoCuentasPeriodoAll): " + sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception(getSaldoCuentasPeriodoAll): " + ex);
		}
		return vecSalida;
	}

	public String getUsuario(BigDecimal idempresa) {
		return usuario;
	}

	public void setUsuario(String usuario, BigDecimal idempresa) {
		this.usuario = usuario;
	}

	// lov centro de costo traigo todo
	public List getCentrosdecostoAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idcencosto,descripcion,usuarioalt,usuarioact,fechaalt,fechaact FROM contablecencosto where idempresa= "
				+ idempresa.toString()
				+ "ORDER BY 2  LIMIT "
				+ limit
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
			log.error("Error SQL en el metodo : getCentrosdecostoAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCentrosdecostoAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// lov centro de costo traigo x ocurrencia
	public List getCentrosdecostoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idcencosto,descripcion,usuarioalt,usuarioact,fechaalt,fechaact FROM contablecencosto WHERE idempresa = "
				+ idempresa.toString()
				+ " and (idcencosto::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " UPPER(descripcion) LIKE '%"
				+ ocurrencia.toUpperCase()
				+ "%') "
				+ " ORDER BY 2  LIMIT "
				+ limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// //////////---> generar asientos contables.
	//
	// public String geneasie(String sistema, String leyenda,
	// java.sql.Timestamp fechadesde, java.sql.Timestamp fechahasta,
	// BigDecimal idempresa) {
	// String salida = "OK";
	// boolean bError = false;
	// int totalRegistros = 0;
	// int ejercicioActivo = 0;
	// try {
	// // primer control : verificar que las fechas de pasaje se encuentren
	// // dentro del ejercicio
	// ejercicioActivo = getEjercicioActivo(idempresa);
	// if (fechadesde.compareTo(getFechaEjercicioActivoDesde(idempresa)) < 0
	// || fechahasta
	// .compareTo(getFechaEjercicioActivoHasta(idempresa)) > 0) {
	// log
	// .error("Error pasaje Contable: las fechas solicitadas estan fuera del
	// ejercicio contable activo.");
	// salida = "Error pasaje Contable: las fechas solicitadas estan fuera del
	// ejercicio contable activo.";
	// bError = true;
	// }
	// if (!bError) {
	// ResultSet rsControl = null;
	// ResultSet rsProceso = null;
	// Statement statement = dbconn.createStatement();
	// PreparedStatement select = dbconn
	// .prepareStatement(" select * from vstockcontabilidad where fecha between
	// ? and ? and idempresa = ? ");
	// select.setTimestamp(1, fechadesde);
	// select.setTimestamp(2, fechahasta);
	// select.setBigDecimal(3, idempresa);
	// rsControl = select.executeQuery();
	// rsProceso = select.executeQuery();
	// while (rsControl.next()) {
	// ++totalRegistros;
	// Long cuentaContable = new Long(rsControl.getLong("cuenta"));
	// String tipoCuenta = rsControl.getString("tipo");
	// List listaCuentas = getCuentasPK(ejercicioActivo,
	// cuentaContable, idempresa);
	// Iterator iterCuentas = listaCuentas.iterator();
	// if (!bError && !iterCuentas.hasNext()) {
	// log.error("Error pasaje Contable: La cuenta "
	// + cuentaContable.toString()
	// + " no existe en el plan de cuentas activo. ");
	// salida = "Error pasaje Contable: La cuenta "
	// + cuentaContable.toString()
	// + " no existe en el plan de cuentas activo. ";
	// bError = true;
	// } else {
	// // aprovecho para verificar si la cuenta es imputable
	// String[] sCampos = (String[]) iterCuentas.next();
	// String isImputable = sCampos[2];
	// if (!bError && !isImputable.equalsIgnoreCase("S")) {
	// log
	// .error("Error pasaje Contable: Existe una cuenta en el pasaje que no
	// figura como imputable, por favor verifique");
	// salida = "Error pasaje Contable: Existe una cuenta en el pasaje que no
	// figura como imputable, por favor verifique.";
	// bError = true;
	// }
	// if (!bError
	// && (!tipoCuenta.equalsIgnoreCase("D") || !tipoCuenta
	// .equalsIgnoreCase("H"))) {
	// log
	// .error("Error pasaje Contable: Error en los registros del pasaje, existen
	// tipos de cuentas diferentes a D o H, verifique");
	// salida = "Error pasaje Contable: Error en los registros del pasaje,
	// existen tipos de cuentas diferentes a D o H, verifique.";
	// bError = true;
	// }
	//
	// }// -- end while
	// // -- todo: verificar que un asiento balancee.
	//
	// }
	// if (!bError && totalRegistros == 0) {
	// log
	// .error("Error pasaje Contable: No existen datos entre los parametros
	// solicitados.");
	// salida = "Error pasaje Contable: No existen datos entre los parametros
	// solicitados.";
	// bError = true;
	// }
	//
	// // -- no existen mas validaciones posibles.
	// if (!bError) {
	//
	// }
	//
	// }
	//
	// } catch (Exception ex) {
	// log
	// .error("Salida por exception: en el metodo: geneasie(..) "
	// + ex);
	// }
	// return salida;
	// }
	//
	// total All contablecencosto
	public long getTotalcencostoAll(BigDecimal idempresa) throws EJBException {

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
		String cQuery = "SELECT count(1)AS total FROM  contablecencosto where idempresa = "
				+ idempresa.toString();
		;
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				total = rsSalida.getLong("total");
			} else {
				log.warn("getTotalcencostoAll()- Error al recuperar total: ");
			}
		} catch (SQLException sqlException) {
			log.error("getTotalcencostoAll()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getTotalcencostoAll()- Salida por exception: " + ex);
		}
		return total;
	}

	// total Ocu contablecencosto
	public long getTotalcencostoOcu(BigDecimal idempresa, String[] campos,
			String ocurrencia) throws EJBException {
		/**
		 * Entidad: contablecencosto
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer usuario por ocurrencia.
		 */
		long total = 0l;
		ResultSet rsSalida = null;
		String cQuery = "SELECT count(1)AS total FROM contablecencosto WHERE idempresa = "
				+ idempresa + "";
		String like = "";
		int len = campos.length;
		try {
			for (int i = 0; i < len; i++) {
				like += " UPPER(" + campos[i] + "::VARCHAR) LIKE '%"
						+ ocurrencia.toUpperCase() + "%' ";
				if (i + 1 < len)
					like += " OR ";
			}
			cQuery += len > 0 ? " AND (" + like + ")" : "";
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				total = rsSalida.getLong("total");
			} else {
				log.warn("getTotalcencostoOcu()- Error al recuperar total: ");
			}
		} catch (SQLException sqlException) {
			log.error("getTotalcencostoOcu()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getTotalcencostoOcu()- Salida por exception: " + ex);
		}
		return total;
	}

	// Centros de Costos con Bean autor:fgp 03/01/08
	// para todo (ordena por el segundo campo por defecto)
	public List getContablecencostoAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " SELECT idcencosto,descripcion,usuarioalt,usuarioact,fechaalt,fechaact,idempresa "
				+ " FROM CONTABLECENCOSTO WHERE idempresa = "
				+ idempresa.toString()
				+ "  ORDER BY 2  LIMIT "
				+ limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getContablecencostoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idcencosto,descripcion,usuarioalt,usuarioact,fechaalt,fechaact,idempresa "
				+ " FROM CONTABLECENCOSTO WHERE (UPPER(DESCRIPCION) "
				+ " LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%')  "
				+ " AND idempresa = "
				+ idempresa.toString()
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getContablecencostoPK(BigDecimal idcencosto,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idcencosto,descripcion,usuarioalt,usuarioact,fechaalt,fechaact,idempresa "
				+ " FROM CONTABLECENCOSTO "
				+ " WHERE idcencosto = "
				+ idcencosto.toString()
				+ " "
				+ " AND idempresa = "
				+ idempresa.toString() + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String contablecencostoDelete(BigDecimal idcencosto,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM CONTABLECENCOSTO WHERE idcencosto="
				+ idcencosto.toString() + " AND idempresa = "
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM CONTABLECENCOSTO WHERE idcencosto="
						+ idcencosto.toString() + " AND idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : contablecencostoDelete( BigDecimal idcencosto ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: contablecencostoDelete( BigDecimal idcencosto )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String contablecencostoCreate(String descripcion, String usuarioalt,
			BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (descripcion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: descripcion ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (descripcion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: descripcion ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO CONTABLECENCOSTO(descripcion, usuarioalt,idempresa ) VALUES (?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, descripcion);
				insert.setString(2, usuarioalt);
				insert.setBigDecimal(3, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String contablecencostoCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String contablecencostoCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String contablecencostoCreateOrUpdate(BigDecimal idcencosto,
			String descripcion, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idcencosto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idcencosto ";
		if (descripcion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: descripcion ";

		// 2. sin nada desde la pagina
		if (descripcion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: descripcion ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM contablecencosto WHERE idcencosto = "
					+ idcencosto.toString()
					+ " and idempresa = "
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
					sql = "UPDATE CONTABLECENCOSTO SET descripcion=?, usuarioact=?,fechaalt=?, idempresa=? WHERE idemrpesa= ? and idcencosto=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, descripcion);
					insert.setString(2, usuarioact);
					insert.setBigDecimal(3, idempresa);
					insert.setBigDecimal(4, idcencosto);
				} else {
					String ins = "INSERT INTO CONTABLECENCOSTO(descripcion, usuarioalt,idempresa ) VALUES (?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, descripcion);
					insert.setString(2, usuarioalt);
					insert.setBigDecimal(3, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String contablecencostoCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String contablecencostoCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String contablecencostoUpdate(BigDecimal idcencosto,
			String descripcion, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idcencosto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idcencosto ";
		if (descripcion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: descripcion ";

		// 2. sin nada desde la pagina
		if (descripcion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: descripcion ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM contablecencosto WHERE idcencosto = "
					+ idcencosto.toString()
					+ " and idempresa = "
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
					sql = "UPDATE CONTABLECENCOSTO SET descripcion=?, usuarioact=?, fechaact=? WHERE idempresa=? and idcencosto=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, descripcion);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idempresa);
					insert.setBigDecimal(5, idcencosto);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String contablecencostoUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String contablecencostoUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// Indice de Ajuste autor:fgp 08/01/08
	// para todo (ordena por el segundo campo por defecto)
	public List getContableajusteAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " SELECT CONTABLEAJUSTE.cod_ajuste,CONTABLEAJUSTE.anio,globalmeses.mes,CONTABLEAJUSTE.indice,CONTABLEAJUSTE.usuarioalt,CONTABLEAJUSTE.usuarioact,CONTABLEAJUSTE.fechaalt,CONTABLEAJUSTE.fechaact,CONTABLEAJUSTE.idempresa FROM CONTABLEAJUSTE,globalmeses WHERE globalmeses.idmes = CONTABLEAJUSTE.mes and idempresa = "
				+ idempresa.toString()
				+ "  ORDER BY 2  LIMIT "
				+ limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getContableajusteOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT CONTABLEAJUSTE.cod_ajuste,CONTABLEAJUSTE.anio,globalmeses.mes,CONTABLEAJUSTE.indice,CONTABLEAJUSTE.usuarioalt,CONTABLEAJUSTE.usuarioact,CONTABLEAJUSTE.fechaalt,CONTABLEAJUSTE.fechaact,CONTABLEAJUSTE.idempresa FROM CONTABLEAJUSTE,globalmeses "
				+ " where globalmeses.idmes = CONTABLEAJUSTE.mes "
				+ " and idempresa = "
				+ idempresa.toString()
				+ " and (cod_ajuste::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " UPPER(anio::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase()
				+ "%') "
				+ " ORDER BY 2  LIMIT "
				+ limit
				+ " OFFSET  "
				+ offset
				+ ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getContableajustePK(BigDecimal cod_ajuste, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT CONTABLEAJUSTE.cod_ajuste,CONTABLEAJUSTE.anio,CONTABLEAJUSTE.mes,globalmeses.mes,CONTABLEAJUSTE.indice,CONTABLEAJUSTE.usuarioalt,CONTABLEAJUSTE.usuarioact,CONTABLEAJUSTE.fechaalt,CONTABLEAJUSTE.fechaact,CONTABLEAJUSTE.idempresa FROM CONTABLEAJUSTE,globalmeses WHERE cod_ajuste="
				+ cod_ajuste.toString()
				+ " and globalmeses.idmes = CONTABLEAJUSTE.mes "
				+ " AND idempresa = " + idempresa.toString() + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String contableajusteDelete(BigDecimal cod_ajuste,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM CONTABLEAJUSTE "
				+ " WHERE cod_ajuste = " + cod_ajuste.toString()
				+ " and idempresa = " + idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM CONTABLEAJUSTE " + " WHERE cod_ajuste="
						+ cod_ajuste.toString() + " and idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : contableajusteDelete( BigDecimal cod_ajuste ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: contableajusteDelete( BigDecimal cod_ajuste )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String contableajusteCreate(Integer anio, String mes, Double indice,
			String usuarioalt, BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (anio == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: anio ";
		if (mes == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: mes ";
		if (indice == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: indice ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO CONTABLEAJUSTE(anio, mes, indice, usuarioalt,idempresa ) VALUES (?,?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setInt(1, anio.intValue());
				if (mes != "" && !mes.equalsIgnoreCase("")) {
					insert.setBigDecimal(2, new BigDecimal(mes));
				} else {
					insert.setBigDecimal(2, null);
				}
				insert.setDouble(3, indice.doubleValue());
				insert.setString(4, usuarioalt);
				insert.setBigDecimal(5, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String contableajusteCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String contableajusteCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String contableajusteCreateOrUpdate(BigDecimal cod_ajuste,
			Integer anio, String mes, Double indice, String usuarioact,
			BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (cod_ajuste == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cod_ajuste ";
		if (anio == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: anio ";
		if (mes == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: mes ";
		if (indice == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: indice ";

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM contableajuste WHERE cod_ajuste = "
					+ cod_ajuste.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE CONTABLEAJUSTE SET anio=?, mes=?, indice=?, usuarioact=?, fechaact=?idempresa=? WHERE cod_ajuste=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setInt(1, anio.intValue());
					insert.setString(2, mes);
					insert.setDouble(3, indice.doubleValue());
					insert.setString(4, usuarioact);
					insert.setTimestamp(5, fechaact);
					insert.setBigDecimal(6, idempresa);
					insert.setBigDecimal(7, cod_ajuste);
				} else {
					String ins = "INSERT INTO CONTABLEAJUSTE(anio, mes, indice, usuarioalt, fechaalt,idempresa ) VALUES (?,?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setInt(1, anio.intValue());
					insert.setString(2, mes);
					insert.setDouble(3, indice.doubleValue());
					insert.setString(4, usuarioalt);
					insert.setBigDecimal(5, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String contableajusteCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String contableajusteCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String contableajusteUpdate(BigDecimal cod_ajuste, Integer anio,
			String mes, Double indice, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (cod_ajuste == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cod_ajuste ";
		if (anio == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: anio ";
		if (mes == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: mes ";
		if (indice == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: indice ";

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM contableajuste "
					+ " WHERE cod_ajuste = " + cod_ajuste.toString()
					+ " and idempresa = " + idempresa.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE CONTABLEAJUSTE SET anio=?, mes=?, indice=?, usuarioact=?, fechaact=? WHERE idempresa=? and cod_ajuste=?  ;";
					insert = dbconn.prepareStatement(sql);
					insert.setInt(1, anio.intValue());
					if (mes != null && !mes.equalsIgnoreCase("null")) {
						insert.setBigDecimal(2, new BigDecimal(mes));
					} else {
						insert.setBigDecimal(2, null);
					}
					insert.setDouble(3, indice.doubleValue());
					insert.setString(4, usuarioact);
					insert.setTimestamp(5, fechaact);
					insert.setBigDecimal(6, idempresa);
					insert.setBigDecimal(7, cod_ajuste);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String contableajusteUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String contableajusteUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public List getGlobalMesesAll(long limit, long offset) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idmes,mes,usuarioalt,usuarioact,fechaalt,fechaact FROM globalmeses "
				+ "ORDER BY 1  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getGlobalMesesOcu(long limit, long offset, String ocurrencia)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idmes,mes,usuarioalt,usuarioact,fechaalt,fechaact FROM globalmeses "
				+ " where (idmes::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " UPPER(mes) LIKE '%"
				+ ocurrencia.toUpperCase()
				+ "%') "
				+ " ORDER BY 1  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public long getTotalEntidadSinEmpresa(String entidad) throws EJBException {

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
		String cQuery = "SELECT count(1)AS total FROM " + entidad;
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
			log
					.error("getTotalEntidadSinEmpresa()- Error SQL: "
							+ sqlException);
		} catch (Exception ex) {
			log.error("getTotalEntidadSinEmpresa()- Salida por exception: "
					+ ex);
		}
		return total;
	}

	public long getTotalEntidadOcuSinEmpresa(String entidad, String[] campos,
			String ocurrencia) throws EJBException {

		/**
		 * Entidad: Usuarios
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer usuario por ocurrencia.
		 */
		long total = 0l;
		ResultSet rsSalida = null;
		String cQuery = "SELECT count(1)AS total FROM " + entidad + " where ";
		String like = "";
		int len = campos.length;

		try {
			for (int i = 0; i < len; i++) {
				like += " UPPER(" + campos[i] + "::VARCHAR) LIKE '%"
						+ ocurrencia.toUpperCase() + "%' ";
				if (i + 1 < len)
					like += " OR ";
			}
			cQuery += "(" + like + ")";
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

	// contable monedas
	public List getContablemonedasAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idmoneda,moneda,hasta_mo,ceros_mo,detalle,usuarioalt,usuarioact,fechaalt,fechaact,idempresa FROM CONTABLEMONEDAS WHERE idempresa = "
				+ idempresa.toString()
				+ "  ORDER BY 2  LIMIT "
				+ limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getContablemonedasOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idmoneda,moneda,hasta_mo,ceros_mo,detalle,usuarioalt,usuarioact,fechaalt,fechaact,idempresa FROM CONTABLEMONEDAS "
				+ " where idempresa = "
				+ idempresa.toString()
				+ " and (idmoneda::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " UPPER(moneda) LIKE '%"
				+ ocurrencia.toUpperCase()
				+ "%') "
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getContablemonedasPK(BigDecimal idmoneda, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idmoneda,moneda,hasta_mo,ceros_mo,detalle,usuarioalt,usuarioact,fechaalt,fechaact,idempresa FROM CONTABLEMONEDAS WHERE idmoneda="
				+ idmoneda.toString()
				+ " AND idempresa = "
				+ idempresa.toString() + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String contablemonedasDelete(BigDecimal idmoneda,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM CONTABLEMONEDAS WHERE idmoneda="
				+ idmoneda.toString() + " and idempresa ="
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM CONTABLEMONEDAS WHERE idmoneda="
						+ idmoneda.toString() + " and idempresa ="
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : contablemonedasDelete( BigDecimal idmoneda ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: contablemonedasDelete( BigDecimal idmoneda )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String contablemonedasCreate(String moneda, Timestamp hasta_mo,
			BigDecimal ceros_mo, String detalle, String usuarioalt,
			BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO CONTABLEMONEDAS(moneda, hasta_mo, ceros_mo, detalle,usuarioalt,idempresa ) VALUES (?,?, ?, ?, ?,?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, moneda);
				insert.setTimestamp(2, hasta_mo);
				insert.setBigDecimal(3, ceros_mo);
				insert.setString(4, detalle);
				insert.setString(5, usuarioalt);
				insert.setBigDecimal(6, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String contablemonedasCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String contablemonedasCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String contablemonedasCreateOrUpdate(BigDecimal idmoneda,
			String moneda, Timestamp hasta_mo, BigDecimal ceros_mo,
			String detalle, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idmoneda == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idmoneda ";

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM contablemonedas WHERE idmoneda = "
					+ idmoneda.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE CONTABLEMONEDAS SET moneda=?, hasta_mo=?, ceros_mo=?, detalle=?, usuarioact=?, fechaact=?idempresa=? WHERE idmoneda=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, moneda);
					insert.setTimestamp(2, hasta_mo);
					insert.setBigDecimal(3, ceros_mo);
					insert.setString(4, detalle);
					insert.setString(5, usuarioact);
					insert.setTimestamp(6, fechaact);
					insert.setBigDecimal(8, idmoneda);
				} else {
					String ins = "INSERT INTO CONTABLEMONEDAS(moneda, hasta_mo, ceros_mo, detalle, usuarioalt, fechaalt ) VALUES (?, ?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, moneda);
					insert.setTimestamp(2, hasta_mo);
					insert.setBigDecimal(3, ceros_mo);
					insert.setString(4, detalle);
					insert.setString(5, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String contablemonedasCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String contablemonedasCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String contablemonedasUpdate(BigDecimal idmoneda, String moneda,
			Timestamp hasta_mo, BigDecimal ceros_mo, String detalle,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idmoneda == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idmoneda ";

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM contablemonedas WHERE idmoneda = "
					+ idmoneda.toString()
					+ " and idempresa ="
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
					sql = "UPDATE CONTABLEMONEDAS SET moneda=?, hasta_mo=?, ceros_mo=?, detalle=?, usuarioact=?, fechaact=? WHERE idempresa=? and idmoneda=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, moneda);
					insert.setTimestamp(2, hasta_mo);
					insert.setBigDecimal(3, ceros_mo);
					insert.setString(4, detalle);
					insert.setString(5, usuarioact);
					insert.setTimestamp(6, fechaact);
					insert.setBigDecimal(7, idempresa);
					insert.setBigDecimal(8, idmoneda);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String contablemonedasUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String contablemonedasUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// cuentas contables
	// para todo (ordena por el segundo campo por defecto)
	public List getContableinfiplannAll(BigDecimal idejercicio, long limit,
			long offset, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT ip.idcuenta, ip.cuenta, ip.inputable, ip.nivel,"
				+ "       ip.ajustable, ip.resultado, cc1.descripcion,"
				+ "       cc2.descripcion, ip.usuarioalt, ip.usuarioact,"
				+ "       ip.fechaalt, ip.fechaact, ip.idempresa"
				+ "  FROM contablecencosto cc1, contablecencosto cc2, CONTABLEINFIPLAN ip"
				+ " WHERE ip.idempresa  = "
				+ idempresa.toString()
				+ "   AND ip.ejercicio  =  "
				+ idejercicio.toString()
				+ "   AND ip.cent_cost1  = cc1.idcencosto  and ip.idempresa   = cc1.idempresa "
				+ " and ip.cent_cost2  = cc2.idcencosto  and ip.idempresa   = cc2.idempresa"
				+ "  ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getContableinfiplannOcu(BigDecimal idejercicio, long limit,
			long offset, String ocurrencia, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT ip.idcuenta, ip.cuenta,  ip.inputable, ip.nivel,"
				+ "       ip.ajustable,  ip.resultado,  ip.cent_cost1,  cc1.descripcion, ip.cent_cost2, "
				+ "       cc2.descripcion,ip.usuarioalt,ip.usuarioact,ip.fechaalt, ip.fechaact,ip.idempresa "
				+ "  FROM contablecencosto cc1, contablecencosto cc2, contableinfiplan ip "
				+ " WHERE ip.idempresa = " + idempresa.toString()
				+ "   AND ip.ejercicio = " + idejercicio.toString()
				+ "   AND ip.cent_cost1 = cc1.idcencosto "
				+ "   AND ip.idempresa = cc1.idempresa "
				+ "   AND ip.cent_cost2 = cc2.idcencosto "
				+ "   AND ip.idempresa = cc2.idempresa"
				+ "   AND (idcuenta::VARCHAR LIKE '%" + ocurrencia + "%' OR "
				+ "       UPPER(cuenta) LIKE '%" + ocurrencia.toUpperCase()
				+ "%') " + " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset
				+ ";";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// contable plan ajuste
	// para todo (ordena por el segundo campo por defecto)
	public List getContableplanajusAll(BigDecimal idejercicio, long limit,
			long offset, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT contableplanajus.cuenta_pl as Cuenta, ip.cuenta as Descripcion, "
				+ "       contableplanajus.indice_pl as Indice, contableajuste.anio as Anio,"
				+ "       contableplanajus.usuarioalt,contableplanajus.usuarioact,contableplanajus.fechaalt,contableplanajus.fechaact"
				+ "  FROM contableplanajus,contableinfiplan ip, contableajuste"
				+ " WHERE ip.idcuenta = contableplanajus.cuenta_pl "
				+ "   AND contableajuste.cod_ajuste = contableplanajus.indice_pl"
				+ "   AND ip.idempresa = contableplanajus.idempresa "
				+ "   AND contableajuste.idempresa = contableplanajus.idempresa"
				+ "   AND ip.idempresa = " + idempresa.toString()
				+ "   AND ip.ejercicio = " + idejercicio.toString()
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getContableplanajusOcu(BigDecimal idejercicio, long limit,
			long offset, String ocurrencia, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		// String tableName = "contableinfiplan" + idejercicio;
		String cQuery = ""
				+ "SELECT contableplanajus.cuenta_pl as Cuenta, ip.cuenta as Descripcion, "
				+ "       contableplanajus.indice_pl as Indice, contableajuste.anio as Anio,"
				+ "       contableplanajus.usuarioalt,contableplanajus.usuarioact,contableplanajus.fechaalt,contableplanajus.fechaact"
				+ "  FROM contableplanajus,contableinfiplan ip, contableajuste"
				+ " WHERE ip.idcuenta = contableplanajus.cuenta_pl "
				+ "   AND contableajuste.cod_ajuste = contableplanajus.indice_pl"
				+ "   AND ip.idempresa = contableplanajus.idempresa "
				+ "   AND contableajuste.idempresa = contableplanajus.idempresa"
				+ "   AND ip.idempresa = " + idempresa.toString()
				+ "   AND ip.ejercicio = " + idejercicio.toString()
				+ "   AND (contableplanajus.cuenta_pl::VARCHAR LIKE '%"
				+ ocurrencia + "%' OR "
				+ " UPPER(contableplanajus.indice_pl::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase() + "%') " + " ORDER BY 2";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)
	//
	// public List getContableplanajusPK(BigDecimal idejercicio, String
	// cuenta_pl,
	// BigDecimal idempresa) throws EJBException {
	// ResultSet rsSalida = null;
	// // String tableName = "contableplanajus" + idejercicio;
	// String cQuery = ""
	// + "Select contableplanajus.cuenta_pl as Cuenta, "
	// + tableName
	// + ".cuenta as Descripcion, contableplanajus.indice_pl as Indice,
	// contableajuste.anio as
	// Anio,contableplanajus.usuarioalt,contableplanajus.usuarioact,contableplanajus.fechaalt,contableplanajus.fechaact"
	// + " from contableplanajus," + tableName
	// + ", contableajuste where " + tableName
	// + ".idcuenta = contableplanajus.cuenta_pl "
	// + " AND contableajuste.cod_ajuste = contableplanajus.indice_pl"
	// + " and " + tableName
	// + ".idempresa = contableplanajus.idempresa "
	// + " and contableajuste.idempresa = contableplanajus.idempresa"
	// + " and " + tableName + ".idempresa = " + idempresa.toString()
	// + "and cuenta_pl=" + cuenta_pl.toString();
	// List vecSalida = new ArrayList();
	// try {
	// Statement statement = dbconn.createStatement();
	// rsSalida = statement.executeQuery(cQuery);
	// ResultSetMetaData md = rsSalida.getMetaData();
	// while (rsSalida.next()) {
	// int totCampos = md.getColumnCount() - 1;
	// String[] sSalida = new String[totCampos + 1];
	// int i = 0;
	// while (i <= totCampos) {
	// sSalida[i] = rsSalida.getString(++i);
	// }
	// vecSalida.add(sSalida);
	// }
	// } catch (SQLException sqlException) {
	// log
	// .error("Error SQL en el metodo : getContableplanajusPK( BigDecimal
	// cuenta_pl ) "
	// + sqlException);
	// } catch (Exception ex) {
	// log
	// .error("Salida por exception: en el metodo: getContableplanajusPK(
	// BigDecimal cuenta_pl ) "
	// + ex);
	// }
	// return vecSalida;
	// }
	//
	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String contableplanajusDelete(String cuenta_pl, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM CONTABLEPLANAJUS"
				+ " WHERE cuenta_pl = " + cuenta_pl.toString()
				+ " and idempresa = " + idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM CONTABLEPLANAJUS WHERE cuenta_pl="
						+ cuenta_pl.toString() + " and idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : contableplanajusDelete( BigDecimal cuenta_pl ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: contableplanajusDelete( BigDecimal cuenta_pl )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String contableplanajusCreate(String cuenta_pl, String indice_pl,
			String usuarioalt, BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (indice_pl == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: indice_pl ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO CONTABLEPLANAJUS (cuenta_pl, indice_pl, usuarioalt, idempresa ) VALUES (?,?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				if (cuenta_pl != "" && !cuenta_pl.equalsIgnoreCase("")) {
					insert.setBigDecimal(1, new BigDecimal(cuenta_pl));
				} else {
					insert.setBigDecimal(1, null);
				}
				if (indice_pl != "" && !indice_pl.equalsIgnoreCase("")) {
					insert.setBigDecimal(2, new BigDecimal(indice_pl));
				} else {
					insert.setBigDecimal(2, null);
				}
				insert.setString(3, usuarioalt);
				insert.setBigDecimal(4, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String contableplanajusCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String contableplanajusCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String contableplanajusCreateOrUpdate(String cuenta_pl,
			String indice_pl, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (cuenta_pl == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cuenta_pl ";
		if (indice_pl == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: indice_pl ";

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM contableplanajus "
					+ " WHERE cuenta_pl = " + cuenta_pl.toString()
					+ " and idempresa = " + idempresa.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE CONTABLEPLANAJUS SET indice_pl=?, usuarioact=?, fechaact=? WHERE idempresa=? and cuenta_pl=? ;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, indice_pl);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idempresa);
					insert.setString(5, cuenta_pl);
				} else {
					String ins = "INSERT INTO CONTABLEPLANAJUS(cuenta_pl, indice_pl, usuarioalt, idempresa ) VALUES (?,?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, cuenta_pl);
					insert.setString(2, indice_pl);
					insert.setString(3, usuarioalt);
					insert.setBigDecimal(4, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String contableplanajusCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String contableplanajusCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String contableplanajusUpdate(String cuenta_pl, String indice_pl,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (cuenta_pl == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cuenta_pl ";
		if (indice_pl == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: indice_pl ";

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM contableplanajus"
					+ " WHERE cuenta_pl = " + cuenta_pl.toString()
					+ " and idempresa = " + idempresa.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE CONTABLEPLANAJUS SET indice_pl=?, usuarioact=?, fechaact=? WHERE idempresa=? and cuenta_pl=?;";
					insert = dbconn.prepareStatement(sql);
					if (indice_pl != null
							&& !indice_pl.equalsIgnoreCase("null")) {
						insert.setBigDecimal(1, new BigDecimal(indice_pl));
					} else {
						insert.setBigDecimal(1, null);
					}
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idempresa);
					insert.setString(5, cuenta_pl);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String contableplanajusUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String contableplanajusUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// lov cuentas contables all
	public List getContableCuentasLovAll(BigDecimal idejercicio, long limit,
			long offset, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idcuenta,cuenta,inputable,nivel,usuarioalt,usuarioact,fechaalt,fechaact"
				+ "  FROM contableinfiplan WHERE idempresa = "
				+ idempresa.toString() + " AND ejercicio="
				+ idejercicio.toString() + " ORDER BY 1  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// lov cuentas contables ocu
	public List getContableCuentasLovOcu(BigDecimal idejercicio, long limit,
			long offset, String ocurrencia, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT  idcuenta,cuenta,inputable,nivel,usuarioalt,usuarioact,fechaalt,fechaact"
				+ " FROM contableinfiplan WHERE ejercicio = " + idejercicio
				+ "  AND idempresa = " + idempresa.toString()
				+ "  AND (idcuenta::VARCHAR LIKE '%" + ocurrencia + "%' OR "
				+ "       UPPER(cuenta) LIKE '%" + ocurrencia.toUpperCase()
				+ "%') " + " ORDER BY 1  LIMIT " + limit + " OFFSET  " + offset
				+ ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// lov cuentas contables all
	public List getContableAjusteLovAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT contableajuste.cod_ajuste,contableajuste.anio,globalmeses.mes,"
				+ "       contableajuste.indice,contableajuste.usuarioalt,contableajuste.usuarioact, "
				+ "       contableajuste.fechaalt,contableajuste.fechaact"
				+ "  FROM contableajuste, globalmeses "
				+ " WHERE globalmeses.idmes =  contableajuste.mes "
				+ "   AND contableajuste.idempresa = " + idempresa.toString()
				+ " ORDER BY anio, mes ";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// lov cuentas contables ocu
	public List getContableAjusteLovOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT contableajuste.cod_ajuste,contableajuste.anio,globalmeses.mes,contableajuste.indice,"
				+ "       contableajuste.usuarioalt,contableajuste.usuarioact,contableajuste.fechaalt,contableajuste.fechaact"
				+ "  FROM contableajuste, globalmeses"
				+ " WHERE globalmeses.idmes =  contableajuste.mes "
				+ "   AND contableajuste.idempresa = " + idempresa.toString()
				+ "   AND (contableajuste.cod_ajuste::VARCHAR LIKE '%"
				+ ocurrencia + "%' OR "
				+ " UPPER(contableajuste.anio::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase() + "%') " + " ORDER BY anio, mes";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/*
	 * // contable rubros // para todo (ordena por el segundo campo por defecto)
	 * public List getContablerubrosAll(long limit, long offset, BigDecimal
	 * idempresa) throws EJBException { ResultSet rsSalida = null; String cQuery
	 * = "SELECT idrubro,rubro,usuarioalt,usuarioact,fechaalt,fechaact,idempresa
	 * FROM CONTABLERUBROS WHERE idempresa = " + idempresa.toString() + " ORDER
	 * BY 2 LIMIT " + limit + " OFFSET " + offset + ";"; List vecSalida = new
	 * ArrayList(); try { Statement statement = dbconn.createStatement();
	 * rsSalida = statement.executeQuery(cQuery); ResultSetMetaData md =
	 * rsSalida.getMetaData(); while (rsSalida.next()) { int totCampos =
	 * md.getColumnCount() - 1; String[] sSalida = new String[totCampos + 1];
	 * int i = 0; while (i <= totCampos) { sSalida[i] = rsSalida.getString(++i);
	 * } vecSalida.add(sSalida); } } catch (SQLException sqlException) {
	 * log.error("Error SQL en el metodo : getContablerubrosAll() " +
	 * sqlException); } catch (Exception ex) { log .error("Salida por exception:
	 * en el metodo: getContablerubrosAll() " + ex); } return vecSalida; }
	 */
	/*
	 * // para una ocurrencia (ordena por el segundo campo por defecto)
	 * 
	 * public List getContablerubrosOcu(long limit, long offset, String
	 * ocurrencia, BigDecimal idempresa) throws EJBException { ResultSet
	 * rsSalida = null; String cQuery = "SELECT
	 * idrubro,rubro,usuarioalt,usuarioact,fechaalt,fechaact,idempresa FROM
	 * CONTABLERUBROS " + " where CONTABLERUBROS.idempresa = " +
	 * idempresa.toString() + " and (CONTABLERUBROS.idrubro::VARCHAR LIKE '%" +
	 * ocurrencia + "%' OR " + " UPPER(CONTABLERUBROS.rubro) LIKE '%" +
	 * ocurrencia.toUpperCase() + "%') " + " ORDER BY idrubro, rubro"; List
	 * vecSalida = new ArrayList(); try { Statement statement =
	 * dbconn.createStatement(); rsSalida = statement.executeQuery(cQuery);
	 * ResultSetMetaData md = rsSalida.getMetaData(); while (rsSalida.next()) {
	 * int totCampos = md.getColumnCount() - 1; String[] sSalida = new
	 * String[totCampos + 1]; int i = 0; while (i <= totCampos) { sSalida[i] =
	 * rsSalida.getString(++i); } vecSalida.add(sSalida); } } catch
	 * (SQLException sqlException) { log .error("Error SQL en el metodo :
	 * getContablerubrosOcu(String ocurrencia) " + sqlException); } catch
	 * (Exception ex) { log .error("Salida por exception: en el metodo:
	 * getContablerubrosOcu(String ocurrencia) " + ex); } return vecSalida; }
	 */
	/*
	 * // por primary key (primer campo por defecto)
	 * 
	 * public List getContablerubrosPK(BigDecimal idrubro, BigDecimal idempresa)
	 * throws EJBException { ResultSet rsSalida = null; String cQuery = "SELECT
	 * idrubro,rubro,usuarioalt,usuarioact,fechaalt,fechaact,idempresa FROM
	 * CONTABLERUBROS WHERE idrubro=" + idrubro.toString() + " AND idempresa = "
	 * + idempresa.toString() + ";"; List vecSalida = new ArrayList(); try {
	 * Statement statement = dbconn.createStatement(); rsSalida =
	 * statement.executeQuery(cQuery); ResultSetMetaData md =
	 * rsSalida.getMetaData(); while (rsSalida.next()) { int totCampos =
	 * md.getColumnCount() - 1; String[] sSalida = new String[totCampos + 1];
	 * int i = 0; while (i <= totCampos) { sSalida[i] = rsSalida.getString(++i);
	 * } vecSalida.add(sSalida); } } catch (SQLException sqlException) { log
	 * .error
	 * ("Error SQL en el metodo : getContablerubrosPK( BigDecimal idrubro ) " +
	 * sqlException); } catch (Exception ex) { log .error("Salida por exception:
	 * en el metodo: getContablerubrosPK( BigDecimal idrubro ) " + ex); } return
	 * vecSalida; }
	 */
	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)
	/*
	 * public String contablerubrosDelete(BigDecimal idrubro, BigDecimal
	 * idempresa) throws EJBException { ResultSet rsSalida = null; String cQuery
	 * = "SELECT * FROM CONTABLERUBROS " + " WHERE idrubro = " +
	 * idrubro.toString() + " and idempresa = " + idempresa; String salida =
	 * "NOOK"; try { Statement statement = dbconn.createStatement(); rsSalida =
	 * statement.executeQuery(cQuery); if (rsSalida.next()) { cQuery = "DELETE
	 * FROM CONTABLERUBROS " + " WHERE idrubro = " + idrubro.toString() + " and
	 * idempresa = " + idempresa; statement.execute(cQuery); salida = "Baja
	 * Correcta."; } else { salida = "Error: Registro inexistente"; } } catch
	 * (SQLException sqlException) { salida = "Imposible eliminar el registro.";
	 * log .error("Error SQL en el metodo : contablerubrosDelete( BigDecimal
	 * idrubro ) " + sqlException); } catch (Exception ex) { salida = "Imposible
	 * eliminar el registro."; log .error("Salida por exception: en el metodo:
	 * contablerubrosDelete( BigDecimal idrubro ) " + ex); } return salida; } //
	 * grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer //
	 * registro por PK y los datos de auditoria solo usuarioalt
	 * 
	 * public String contablerubrosCreate(String rubro, String usuarioalt,
	 * BigDecimal idempresa) throws EJBException { String salida = "NOOK"; //
	 * validaciones de datos: // 1. nulidad de campos if (rubro == null) salida
	 * = "Error: No se puede dejar sin datos (nulo) el campo: rubro "; // 2. sin
	 * nada desde la pagina if (rubro.equalsIgnoreCase("")) salida = "Error: No
	 * se puede dejar vacio el campo: rubro "; // fin validaciones boolean
	 * bError = true; if (salida.equalsIgnoreCase("NOOK")) bError = false; try {
	 * if (!bError) { String ins = "INSERT INTO CONTABLERUBROS(rubro,
	 * usuarioalt, idempresa ) VALUES (?, ?, ?)"; PreparedStatement insert =
	 * dbconn.prepareStatement(ins); // seteo de campos: insert.setString(1,
	 * rubro); insert.setString(2, usuarioalt); insert.setBigDecimal(3,
	 * idempresa); int n = insert.executeUpdate(); if (n == 1) salida = "Alta
	 * Correcta"; } } catch (SQLException sqlException) { salida = "Imposible
	 * dar de alta el registro."; log.error("Error SQL public String
	 * contablerubrosCreate(.....)" + sqlException); } catch (Exception ex) {
	 * salida = "Imposible dar de alta el registro."; log .error("Error
	 * excepcion public String contablerubrosCreate(.....)" + ex); } return
	 * salida; } // actualizacion de un registro por PK NOTA: no se tiene en
	 * cuenta el primer // registro por PK y los datos de auditoria
	 * 
	 * public String contablerubrosCreateOrUpdate(BigDecimal idrubro, String
	 * rubro, String usuarioact, BigDecimal idempresa) throws EJBException {
	 * Calendar hoy = new GregorianCalendar(); Timestamp fechaact = new
	 * Timestamp(hoy.getTime().getTime()); String salida = "NOOK"; //
	 * validaciones de datos: // 1. nulidad de campos if (idrubro == null)
	 * salida = "Error: No se puede dejar sin datos (nulo) el campo: idrubro ";
	 * if (rubro == null) salida = "Error: No se puede dejar sin datos (nulo) el
	 * campo: rubro "; // 2. sin nada desde la pagina if
	 * (rubro.equalsIgnoreCase("")) salida = "Error: No se puede dejar vacio el
	 * campo: rubro "; // fin validaciones boolean bError = true; if
	 * (salida.equalsIgnoreCase("NOOK")) bError = false; try { ResultSet
	 * rsSalida = null; String cQuery = "SELECT COUNT(*) FROM contablerubros
	 * WHERE idrubro = " + idrubro.toString(); Statement statement =
	 * dbconn.createStatement(); rsSalida = statement.executeQuery(cQuery); int
	 * total = 0; if (rsSalida != null && rsSalida.next()) total =
	 * rsSalida.getInt(1); PreparedStatement insert = null; String sql = ""; if
	 * (!bError) { if (total > 0) { // si existe hago update sql = "UPDATE
	 * CONTABLERUBROS SET rubro=?, usuarioact=?, fechaact=?idempresa=? WHERE
	 * idrubro=?;"; insert = dbconn.prepareStatement(sql); insert.setString(1,
	 * rubro); insert.setString(2, usuarioact); insert.setTimestamp(3,
	 * fechaact); insert.setBigDecimal(4, idempresa); insert.setBigDecimal(5,
	 * idrubro); } else { String ins = "INSERT INTO CONTABLERUBROS(rubro,
	 * usuarioalt, fechaalt ) VALUES (?, ?, ?)"; insert =
	 * dbconn.prepareStatement(ins); // seteo de campos: String usuarioalt =
	 * usuarioact; // esta variable va a // proposito insert.setString(1,
	 * rubro); insert.setString(2, usuarioalt); insert.setBigDecimal(3,
	 * idempresa); } insert.executeUpdate(); salida = "Alta Correcta."; } }
	 * catch (SQLException sqlException) { salida = "Imposible dar de alta el
	 * registro."; log .error("Error SQL public String
	 * contablerubrosCreateOrUpdate(.....)" + sqlException); } catch (Exception
	 * ex) { salida = "Imposible dar de alta el registro."; log .error("Error
	 * excepcion public String contablerubrosCreateOrUpdate(.....)" + ex); }
	 * return salida; }
	 * 
	 * public String contablerubrosUpdate(BigDecimal idrubro, String rubro,
	 * String usuarioact, BigDecimal idempresa) throws EJBException { Calendar
	 * hoy = new GregorianCalendar(); Timestamp fechaact = new
	 * Timestamp(hoy.getTime().getTime()); String salida = "NOOK"; //
	 * validaciones de datos: // 1. nulidad de campos if (idrubro == null)
	 * salida = "Error: No se puede dejar sin datos (nulo) el campo: idrubro ";
	 * if (rubro == null) salida = "Error: No se puede dejar sin datos (nulo) el
	 * campo: rubro "; // 2. sin nada desde la pagina if
	 * (rubro.equalsIgnoreCase("")) salida = "Error: No se puede dejar vacio el
	 * campo: rubro "; // fin validaciones boolean bError = true; if
	 * (salida.equalsIgnoreCase("NOOK")) bError = false; try { ResultSet
	 * rsSalida = null; String cQuery = " SELECT COUNT(*) FROM contablerubros "
	 * + " WHERE idrubro = " + idrubro.toString() + " and idempresa = " +
	 * idempresa.toString(); Statement statement = dbconn.createStatement();
	 * rsSalida = statement.executeQuery(cQuery); int total = 0; if (rsSalida !=
	 * null && rsSalida.next()) total = rsSalida.getInt(1); PreparedStatement
	 * insert = null; String sql = ""; if (!bError) { if (total > 0) { // si
	 * existe hago update sql = "UPDATE CONTABLERUBROS SET rubro=?,
	 * usuarioact=?, fechaact=? WHERE idempresa=? and idrubro=?;"; insert =
	 * dbconn.prepareStatement(sql); insert.setString(1, rubro);
	 * insert.setString(2, usuarioact); insert.setTimestamp(3, fechaact);
	 * insert.setBigDecimal(4, idempresa); insert.setBigDecimal(5, idrubro); }
	 * 
	 * int i = insert.executeUpdate(); if (i > 0) salida = "Actualizacion
	 * Correcta"; else salida = "Imposible actualizar el registro."; } } catch
	 * (SQLException sqlException) { salida = "Imposible actualizar el
	 * registro."; log.error("Error SQL public String
	 * contablerubrosUpdate(.....)" + sqlException); } catch (Exception ex) {
	 * salida = "Imposible actualizar el registro."; log .error("Error excepcion
	 * public String contablerubrosUpdate(.....)" + ex); } return salida; }
	 */
	public List getCcontablesAll(BigDecimal idEjercicio, long limit,
			long offset, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idcuenta, cuenta, inputable, nivel, ajustable, resultado"
				+ "  FROM  contableinfiplan             "
				+ " WHERE idempresa = " + idempresa + " AND ejercicio = "
				+ idEjercicio + " ORDER BY 1  LIMIT " + limit + " OFFSET  "
				+ offset + ";";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getStockgruposOcu_NOMBRECOMOELORTO(BigDecimal idEjercicio,
			long limit, long offset, String ocurrencia, BigDecimal idempresa)
			throws EJBException {
		String cQuery = " "
				+ "SELECT idcuenta, cuenta, inputable, nivel, ajustable, resultado"
				+ "  FROM contableinfiplan             " + " WHERE idempresa= "
				+ idempresa.toString() + "   AND ejercicio = " + idEjercicio
				+ "   AND (idcuenta::VARCHAR LIKE '%" + ocurrencia + "%' OR "
				+ "       UPPER(cuenta) LIKE '%" + ocurrencia.toUpperCase()
				+ "%') " + " ORDER BY 1";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para todo (ordena por el segundo campo por defecto)
	public List getContableinfiplanAll(long limit, long offset,
			BigDecimal ejercicio, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT cip.idcuenta, lpad(cip.cuenta::varchar, length(cip.cuenta) + cip.nivel::int, ' ') as cuenta, "
				+ "       cip.inputable,cip.nivel,cip.ajustable,cip.resultado,cc1.descripcion,"
				+ "       cc2.descripcion,cip.idempresa,cip.usuarioalt,cip.usuarioact,cip.fechaalt,cip.fechaact"
				+ "  FROM CONTABLEINFIPLAN cip"
				+ "       LEFT JOIN contablecencosto cc1 ON cip.cent_cost1 = cc1.idcencosto and cip.idempresa = cc1.idempresa"
				+ "       LEFT JOIN contablecencosto cc2 ON cip.cent_cost2 = cc2.idcencosto and cip.idempresa = cc2.idempresa  "
				+ " WHERE cip.idempresa = " + idempresa.toString()
				+ "   AND cip.ejercicio =  " + ejercicio.toString()
				+ " ORDER BY 1  LIMIT " + limit + " OFFSET  " + offset + ";";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getContableinfiplanOcu(long limit, long offset,
			BigDecimal ejercicio, String ocurrencia, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT cip.idcuenta, lpad(cip.cuenta::varchar, length(cip.cuenta) + cip.nivel::int, ' ') as cuenta, "
				+ "       cip.inputable,cip.nivel,cip.ajustable,cip.resultado,cc1.descripcion,cc2.descripcion,cip.idempresa,cip.usuarioalt,cip.usuarioact,cip.fechaalt,cip.fechaact"
				+ "  FROM CONTABLEINFIPLAN cip"
				+ "       LEFT JOIN contablecencosto cc1 ON cip.cent_cost1 = cc1.idcencosto and cip.idempresa = cc1.idempresa"
				+ "       LEFT JOIN contablecencosto cc2 ON cip.cent_cost2 = cc2.idcencosto and cip.idempresa = cc2.idempresa  "
				+ " WHERE cip.ejercicio = " + ejercicio.toString()
				+ "   AND (cip.idcuenta::VARCHAR LIKE '%" + ocurrencia
				+ "%' OR " + "       UPPER(cip.cuenta) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%')  AND cip.idempresa = "
				+ idempresa.toString() + " ORDER BY 1  LIMIT " + limit
				+ " OFFSET  " + offset + ";";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getContableinfiplanpk(BigDecimal ejercicio,
			BigDecimal idcuenta, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT cip.ejercicio,cip.idcuenta,cip.cuenta,cip.inputable,cip.nivel,cip.ajustable,"
				+ "       cip.resultado,cc1.idcencosto,cc1.descripcion,cc2.idcencosto,cc2.descripcion,"
				+ "       cip.idempresa,cip.usuarioalt,cip.usuarioact,cip.fechaalt,cip.fechaact"
				+ "  FROM CONTABLEINFIPLAN cip"
				+ "       LEFT JOIN contablecencosto cc1 ON cip.cent_cost1 = cc1.idcencosto and cip.idempresa = cc1.idempresa"
				+ "       LEFT JOIN contablecencosto cc2 ON cip.cent_cost2 = cc2.idcencosto and cip.idempresa = cc2.idempresa  "
				+ " WHERE cip.ejercicio = " + ejercicio.toString()
				+ "   AND cip.idcuenta = " + idcuenta.toString()
				+ "   AND cip.idempresa = " + idempresa.toString() + ";";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * 
	 * metodos privados para cuidar la integridad de las cuentas contables. (no
	 * mira en que ejercicio!) EJV 20090609 : agregar BigDecimal ejercicio, para
	 * que MIRE en que ejercicio esta la cuenta.
	 * 
	 */

	private boolean hasCuentaEnContStock(BigDecimal idcuenta,
			BigDecimal ejercicio, BigDecimal idempresa) throws EJBException {
		// indicar si tiene movimientos en la contabilidad de stock
		ResultSet rsSalida = null;
		boolean salida = true;
		try {
			String cQuery = "                          "
					+ "SELECT COUNT(1) AS total "
					+ "  FROM stockcontstock cs "
					+ "       INNER JOIN stockmovstock ms ON cs.nint_ms_cs = ms.nrointerno_ms AND cs.idempresa = ms.idempresa "
					+ " WHERE cs.idempresa = " + idempresa
					+ "   AND cs.cuenta_cs = " + idcuenta
					+ "   AND DATE_PART('YEAR', fecha_ms) = " + ejercicio;
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida != null && rsSalida.next()) {
				if (rsSalida.getInt("total") > 0)
					salida = true;
				else
					salida = false;
			}
		} catch (SQLException sqlException) {
			salida = true;
			log
					.error("Error SQL en el metodo : hasCuentaEnContStock(BigDecimal idcuenta, BigDecimal idempresa) ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = true;
			log
					.error("Salida por exception: en el metodo: hasCuentaEnContStock(BigDecimal idcuenta, BigDecimal idempresa))  "
							+ ex);
		}
		return salida;
	}

	private boolean hasCuentaEnMovTeso(BigDecimal idcuenta,
			BigDecimal ejercicio, BigDecimal idempresa) throws EJBException {
		// indicar si tiene movimientos en la contabilidad de stock
		ResultSet rsSalida = null;
		boolean salida = true;
		try {
			String cQuery = "SELECT COUNT(*) AS total "
					+ "        FROM cajamovteso WHERE idempresa = " + idempresa
					+ "         AND cuenta_mt = " + idcuenta
					+ "         AND DATE_PART('YEAR', fechamo_mt) = "
					+ ejercicio;
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida != null && rsSalida.next()) {
				if (rsSalida.getInt("total") > 0)
					salida = true;
				else
					salida = false;
			}
		} catch (SQLException sqlException) {
			salida = true;
			log
					.error("Error SQL en el metodo : hasCuentaEnMovTeso(BigDecimal idcuenta, BigDecimal idempresa) ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = true;
			log
					.error("Salida por exception: en el metodo: hasCuentaEnMovTeso(BigDecimal idcuenta, BigDecimal idempresa))  "
							+ ex);
		}
		return salida;
	}

	private boolean hasCuentaEnContcli(BigDecimal idcuenta,
			BigDecimal ejercicio, BigDecimal idempresa) throws EJBException {
		// indicar si tiene movimientos en la contabilidad de stock
		ResultSet rsSalida = null;
		boolean salida = true;
		try {
			String cQuery = ""
					+ "SELECT COUNT(*) AS total "
					+ "  FROM clientescontcli cc "
					+ "       INNER JOIN clientesmovcli mc ON cc.nroint_con = mc.nrointerno AND cc.idempresa = mc.idempresa "
					+ " WHERE cc.idempresa = " + idempresa
					+ "   AND cc.cuenta_con = " + idcuenta
					+ "   AND DATE_PART('YEAR', mc.fechamov) = " + ejercicio;
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida != null && rsSalida.next()) {
				if (rsSalida.getInt("total") > 0)
					salida = true;
				else
					salida = false;
			}
		} catch (SQLException sqlException) {
			salida = true;
			log
					.error("Error SQL en el metodo : hasCuentaEnContcli(BigDecimal idcuenta, BigDecimal ejercicio, BigDecimal idempresa) ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = true;
			log
					.error("Salida por exception: en el metodo: hasCuentaEnContcli(BigDecimal idcuenta, BigDecimal ejercicio, BigDecimal idempresa))  "
							+ ex);
		}
		return salida;
	}

	private boolean hasCuentaEnClientes(BigDecimal idcuenta,
			BigDecimal idempresa) throws EJBException {
		// indicar si tiene movimientos en la contabilidad de stock
		ResultSet rsSalida = null;
		boolean salida = true;
		try {
			String cQuery = "SELECT COUNT(*) AS total FROM clientesclientes WHERE idempresa ="
					+ idempresa + " AND idctaneto = " + idcuenta;
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida != null && rsSalida.next()) {
				if (rsSalida.getInt("total") > 0)
					salida = true;
				else
					salida = false;
			}
		} catch (SQLException sqlException) {
			salida = true;
			log
					.error("Error SQL en el metodo : hasCuentaEnMovTeso(BigDecimal idcuenta, BigDecimal idempresa) ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = true;
			log
					.error("Salida por exception: en el metodo: hasCuentaEnMovTeso(BigDecimal idcuenta, BigDecimal idempresa))  "
							+ ex);
		}
		return salida;
	}

	private boolean hasCuentaEnContProveedores(BigDecimal idcuenta,
			BigDecimal ejercicio, BigDecimal idempresa) throws EJBException {
		// indicar si tiene movimientos en la contabilidad de stock
		ResultSet rsSalida = null;
		boolean salida = true;
		try {

			String cQuery = "                         "
					+ "SELECT COUNT(1) AS total "
					+ "  FROM proveedocontprov cp "
					+ "       INNER JOIN proveedomovprov mp ON cp.compr_con = mp.nrointerno AND cp.idempresa = mp.idempresa "
					+ " WHERE cp.idempresa =" + idempresa
					+ "   AND cp.cuenta_con = " + idcuenta
					+ "   AND DATE_PART('YEAR', mp.fechamov) = " + ejercicio;

			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida != null && rsSalida.next()) {
				if (rsSalida.getInt("total") > 0)
					salida = true;
				else
					salida = false;
			}
		} catch (SQLException sqlException) {
			salida = true;
			log
					.error("Error SQL en el metodo : hasCuentaEnContProveedores(BigDecimal idcuenta, BigDecimal ejercicio, BigDecimal idempresa) ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = true;
			log
					.error("Salida por exception: en el metodo: hasCuentaEnContProveedores(BigDecimal idcuenta, BigDecimal ejercicio, BigDecimal idempresa))  "
							+ ex);
		}
		return salida;
	}

	private boolean hasCuentaEnStock(BigDecimal idcuenta, BigDecimal idempresa)
			throws EJBException {
		// indicar si tiene movimientos en la contabilidad de stock
		ResultSet rsSalida = null;
		boolean salida = true;
		try {
			String cQuery = ""
					+ "SELECT COUNT(*) AS total FROM stockstock WHERE idempresa = "
					+ idempresa + " AND (" + "cuencom_st = " + idcuenta
					+ " OR " + "cuenven_st = " + idcuenta + " OR "
					+ "cuenve2_st = " + idcuenta + " OR " + "cuencos_st = "
					+ idcuenta + " OR " + "cuenaju_st = " + idcuenta + " "
					+ ");";
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida != null && rsSalida.next()) {
				if (rsSalida.getInt("total") > 0)
					salida = true;
				else
					salida = false;
			}
		} catch (SQLException sqlException) {
			salida = true;
			log
					.error("Error SQL en el metodo : hasCuentaEnStock(BigDecimal idcuenta, BigDecimal idempresa) ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = true;
			log
					.error("Salida por exception: en el metodo: hasCuentaEnStock(BigDecimal idcuenta, BigDecimal idempresa))  "
							+ ex);
		}
		return salida;
	}

	private boolean hasCuentaEnProveedores(BigDecimal idcuenta,
			BigDecimal idempresa) throws EJBException {
		// indicar si tiene movimientos en la contabilidad de stock
		ResultSet rsSalida = null;
		boolean salida = true;
		try {
			String cQuery = ""
					+ "SELECT COUNT(*) AS total FROM proveedoproveed WHERE idempresa = "
					+ idempresa
					+ " AND ("
					+ "ctapasivo = "
					+ idcuenta
					+ " OR "
					+ "ctaactivo1 = "
					+ idcuenta
					+ " OR "
					+ "ctaactivo2 = "
					+ idcuenta
					+ " OR "
					+ "ctaactivo3 = "
					+ idcuenta
					+ " OR "
					+ "ctaactivo4 = "
					+ idcuenta
					+ " OR "
					+ "ctaiva = "
					+ idcuenta
					+ " OR "
					+ "ctaretiva = "
					+ idcuenta
					+ " OR "
					+ "ctadocumen = " + idcuenta + " " + ");";
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida != null && rsSalida.next()) {
				if (rsSalida.getInt("total") > 0)
					salida = true;
				else
					salida = false;
			}
		} catch (SQLException sqlException) {
			salida = true;
			log
					.error("Error SQL en el metodo : hasCuentaEnProveedores(BigDecimal idcuenta, BigDecimal idempresa) ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = true;
			log
					.error("Salida por exception: en el metodo: hasCuentaEnProveedores(BigDecimal idcuenta, BigDecimal idempresa))  "
							+ ex);
		}
		return salida;
	}

	/**
	 * */

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)
	// cep: 21/10/2008 - Verificacion para cuidar integridad
	public String contableinfiplanDelete(BigDecimal ejercicio,
			BigDecimal idcuenta, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM CONTABLEINFIPLAN "
				+ " WHERE ejercicio = " + ejercicio.toString()
				+ " and idcuenta  = " + idcuenta.toString()
				+ " and idempresa = " + idempresa.toString();
		String salida = "";
		try {
			// validaciones
			// -- STOCK
			if (hasCuentaEnStock(idcuenta, idempresa)) {
				salida = "Error: La cuenta contable posee productos del stock asociados a ella";
			}
			if (hasCuentaEnContStock(idcuenta, ejercicio, idempresa)) {
				salida = "Error: La cuenta contable posee movimientos en la subcontabilidad de stock";
			}
			// -- TESORERIA
			if (hasCuentaEnMovTeso(idcuenta, ejercicio, idempresa)) {
				salida = "Error: La cuenta contable posee movimientos en la subcontabilidad de administracion financiera";
			}
			// -- CLIENTES
			if (hasCuentaEnContcli(idcuenta, ejercicio, idempresa)) {
				salida = "Error: La cuenta contable posee movimientos en la subcontabilidad de gestion de ventas.";
			}
			if (hasCuentaEnClientes(idcuenta, idempresa)) {
				salida = "Error: La cuenta contable esta asociada a clientes.";
			}
			// -- PROVEEDORES
			if (hasCuentaEnContProveedores(idcuenta, ejercicio, idempresa)) {
				salida = "Error: La cuenta contable esta asociada a la subcontabilidad de compras.";
			}

			if (hasCuentaEnProveedores(idcuenta, idempresa)) {
				salida = "Error: La cuenta contable esta asociada proveedores.";
			}

			if (salida.equals("")) {
				Statement statement = dbconn.createStatement();
				rsSalida = statement.executeQuery(cQuery);
				if (rsSalida.next()) {
					cQuery = "DELETE FROM CONTABLEINFIPLAN "
							+ " WHERE ejercicio = " + ejercicio.toString()
							+ " and idcuenta = " + idcuenta.toString()
							+ " and idempresa = " + idempresa.toString();
					statement.execute(cQuery);
					salida = "Baja Correcta.";
				} else {
					salida = "Error: Registro inexistente";
				}
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : contableinfiplanDelete( BigDecimal ejercicio ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: contableinfiplanDelete( BigDecimal ejercicio )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String contableinfiplanCreate(BigDecimal ejercicio,
			BigDecimal idcuenta, String cuenta, String inputable,
			BigDecimal nivel, String ajustable, String resultado,
			BigDecimal cent_cost1, BigDecimal cent_cost2, BigDecimal idempresa,
			String usuarioalt) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos

		// 20090623 - EJV
		int contMascaraCContables = -1;

		String pictureCuenta = GeneralBean.getValorSetupVariables(
				"contMascaraCContables", idempresa, dbconn);

		// 20090623 - EJV
		if (pictureCuenta != null || pictureCuenta.trim().equals(""))
			pictureCuenta = pictureCuenta.trim();
		else
			salida = "Error: esta definida la longitud de la mascara para idcuenta: contMascaraCContables ";

		// 20090623 - EJV
		try {
			contMascaraCContables = Integer.parseInt(pictureCuenta);
		} catch (Exception e) {
			salida = "Error: variable de longitud de la mascara para idcuenta debe se n�merica: contMascaraCContables ";
		}

		if (idcuenta == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idcuenta ";

		// 20090623 - EJV
		if (idcuenta.toString().length() > contMascaraCContables)
			salida = "Error: La longitud del total de caracteres del campo idcuenta es mayor a la definida por: contMascaraCContables";

		if (cuenta == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cuenta ";
		if (inputable == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: inputable ";
		if (nivel == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nivel ";
		if (ajustable == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: ajustable ";
		if (resultado == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: resultado ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (cuenta.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: cuenta ";
		if (inputable.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: inputable ";
		if (ajustable.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: ajustable ";
		if (resultado.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: resultado ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {

				// modifico la cuenta para rellenarla con ceros al final hasta
				// 15

				/*
				 * EJV: 20090623 **: CEPEADA: generaba siempre el idcuenta con
				 * 10 caracteres, sin tener en cuenta lo definido en
				 * contMascaraCContables.
				 * 
				 * String cIdCuenta = idcuenta.toString().trim(); int pc = 10;
				 * if (cIdCuenta.length() < pc) { int x = pc -
				 * cIdCuenta.length(); for (int i = 0; i < x; i++) { cIdCuenta
				 * += "0"; } }
				 * 
				 * idcuenta = new BigDecimal(cIdCuenta);
				 */

				String ins = ""
						+ "INSERT INTO CONTABLEINFIPLAN "
						+ "        (ejercicio, idcuenta, cuenta, inputable, nivel, ajustable, "
						+ "         resultado, cent_cost1, cent_cost2, idempresa, usuarioalt )"
						+ " VALUES (?, SUBSTR(RPAD(? , ?, '0'),1 , ?)::NUMERIC, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, ejercicio);

				// 20090623 - EJV
				insert.setString(2, idcuenta.toString());
				insert.setInt(3, contMascaraCContables);
				insert.setInt(4, contMascaraCContables);
				insert.setString(5, cuenta);

				insert.setString(6, inputable);
				insert.setBigDecimal(7, nivel);
				insert.setString(8, ajustable);
				insert.setString(9, resultado);
				insert.setBigDecimal(10, cent_cost1);
				insert.setBigDecimal(11, cent_cost2);
				insert.setBigDecimal(12, idempresa);
				insert.setString(13, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String contableinfiplanCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String contableinfiplanCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String contableinfiplanCreateOrUpdate(BigDecimal ejercicio,
			BigDecimal idcuenta, String cuenta, String inputable,
			BigDecimal nivel, String ajustable, String resultado,
			BigDecimal cent_cost1, BigDecimal cent_cost2, BigDecimal idempresa,
			String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (ejercicio == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: ejercicio ";
		if (idcuenta == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idcuenta ";
		if (cuenta == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cuenta ";
		if (inputable == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: inputable ";
		if (nivel == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nivel ";
		if (ajustable == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: ajustable ";
		if (resultado == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: resultado ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (cuenta.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: cuenta ";
		if (inputable.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: inputable ";
		if (ajustable.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: ajustable ";
		if (resultado.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: resultado ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM contableinfiplan WHERE ejercicio = "
					+ ejercicio.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE CONTABLEINFIPLAN SET idcuenta=?, cuenta=?, inputable=?, nivel=?, ajustable=?, resultado=?, cent_cost1=?, cent_cost2=?, idempresa=?, usuarioact=?, fechaact=? WHERE ejercicio=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, idcuenta);
					insert.setString(2, cuenta);
					insert.setString(3, inputable);
					insert.setBigDecimal(4, nivel);
					insert.setString(5, ajustable);
					insert.setString(6, resultado);
					insert.setBigDecimal(7, cent_cost1);
					insert.setBigDecimal(8, cent_cost2);
					insert.setBigDecimal(9, idempresa);
					insert.setString(10, usuarioact);
					insert.setTimestamp(11, fechaact);
					insert.setBigDecimal(12, ejercicio);
				} else {
					String ins = "INSERT INTO CONTABLEINFIPLAN(idcuenta, cuenta, inputable, nivel, ajustable, resultado, cent_cost1, cent_cost2, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setBigDecimal(1, idcuenta);
					insert.setString(2, cuenta);
					insert.setString(3, inputable);
					insert.setBigDecimal(4, nivel);
					insert.setString(5, ajustable);
					insert.setString(6, resultado);
					insert.setBigDecimal(7, cent_cost1);
					insert.setBigDecimal(8, cent_cost2);
					insert.setBigDecimal(9, idempresa);
					insert.setString(10, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String contableinfiplanCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String contableinfiplanCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String contableinfiplanUpdate(BigDecimal ejercicio,
			BigDecimal idcuenta, String cuenta, String inputable,
			BigDecimal nivel, String ajustable, String resultado,
			BigDecimal cent_cost1, BigDecimal cent_cost2, BigDecimal idempresa,
			String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (ejercicio == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: ejercicio ";
		if (idcuenta == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idcuenta ";
		if (cuenta == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cuenta ";
		if (inputable == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: inputable ";
		if (nivel == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nivel ";
		if (ajustable == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: ajustable ";
		if (resultado == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: resultado ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (cuenta.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: cuenta ";
		if (inputable.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: inputable ";
		if (ajustable.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: ajustable ";
		if (resultado.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: resultado ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM contableinfiplan "
					+ " WHERE ejercicio = " + ejercicio.toString()
					+ " and  idcuenta = " + idcuenta.toString()
					+ " and  idempresa = " + idempresa.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE CONTABLEINFIPLAN SET idcuenta=?, cuenta=?, inputable=?, nivel=?, ajustable=?, resultado=?, cent_cost1=?, cent_cost2=?, idempresa=?, usuarioact=?, fechaact=? WHERE ejercicio=? and idcuenta =? ;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, idcuenta);
					insert.setString(2, cuenta);
					insert.setString(3, inputable);
					insert.setBigDecimal(4, nivel);
					insert.setString(5, ajustable);
					insert.setString(6, resultado);
					insert.setBigDecimal(7, cent_cost1);
					insert.setBigDecimal(8, cent_cost2);
					insert.setBigDecimal(9, idempresa);
					insert.setString(10, usuarioact);
					insert.setTimestamp(11, fechaact);
					insert.setBigDecimal(12, ejercicio);
					insert.setBigDecimal(13, idcuenta);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String contableinfiplanUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String contableinfiplanUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// lov ejercicio
	public List getEjercicioLovAll(long limit, long offset, BigDecimal idempresa)
			throws EJBException {
		String cQuery = "SELECT distinct(ejercicio),activo"
				+ " FROM contableejercicios" + " where " + " idempresa = "
				+ idempresa.toString() + "ORDER BY 1  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// lov cuentas contables ocu
	public List getEjercicioLovOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		String cQuery = "SELECT distinct(ejercicio),activo"
				+ " FROM contableejercicios" + " where " + " idempresa = "
				+ idempresa.toString() + " and (ejercicio::VARCHAR LIKE '%"
				+ ocurrencia + "%' OR " + " UPPER(ejercicio::VARCHAR) LIKE '%"
				+ ocurrencia + "%') " + " ORDER BY 1  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	//

	public String contableCopiarPlanCuentas(BigDecimal ejercicioOrigen,
			BigDecimal ejercicioDestino, BigDecimal idempresa)
			throws EJBException {

		String salida = "OK";
		int i = 0;

		try {
			String cQuery = ""
					+ "INSERT INTO contableinfiplan "
					+ " SELECT ?, idcuenta, cuenta, inputable, nivel, ajustable, resultado,"
					+ "        0, 0, idempresa, 'replica-plan',null, now(), null "
					+ "   FROM contableinfiplan WHERE ejercicio = ? AND idempresa = ? ;";

			PreparedStatement pstatement = dbconn.prepareStatement(cQuery);
			pstatement.setBigDecimal(1, ejercicioDestino);
			pstatement.setBigDecimal(2, ejercicioOrigen);
			pstatement.setBigDecimal(3, idempresa);

			i = pstatement.executeUpdate();

			if (i < 1)
				salida = "No fue posible copiar plan de cuentas.";

		} catch (SQLException sqlException) {
			salida = "Imposible copiar plan de cuentas(0).";
			log
					.error("Error SQL en el metodo : contableCopiarPlanCuentas( ... ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible copiar plan de cuentas(1).";
			log
					.error("Salida por exception: en el metodo: contableCopiarPlanCuentas( ... )  "
							+ ex);
		}

		return salida;

	}

	// contable ejercicios
	// para todo (ordena por el segundo campo por defecto)
	public List getContableejerciciosAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		String cQuery = "SELECT ejercicio,fechadesde,fechahasta,activo,usuarioalt,usuarioact,fechaalt,fechaact,idempresa FROM CONTABLEEJERCICIOS "
				+ " WHERE idempresa = "
				+ idempresa.toString()
				+ "  ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getContableejerciciosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  ejercicio,fechadesde,fechahasta,activo,usuarioalt,usuarioact,fechaalt,fechaact,idempresa FROM CONTABLEEJERCICIOS "
				+ " WHERE (ejercicio::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%')  "
				+ " AND idempresa = "
				+ idempresa.toString()
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getContableejerciciosPK(BigDecimal ejercicio,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  ejercicio,fechadesde,fechahasta,activo,usuarioalt,usuarioact,fechaalt,fechaact,idempresa FROM CONTABLEEJERCICIOS WHERE ejercicio="
				+ ejercicio.toString()
				+ " AND idempresa = "
				+ idempresa.toString() + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String contableejerciciosDelete(BigDecimal ejercicio,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM CONTABLEEJERCICIOS WHERE ejercicio="
				+ ejercicio.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM CONTABLEEJERCICIOS WHERE ejercicio="
						+ ejercicio.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : contableejerciciosDelete( BigDecimal ejercicio ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: contableejerciciosDelete( BigDecimal ejercicio )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String contableejerciciosCreate(BigDecimal ejercicio,
			Timestamp fechadesde, Timestamp fechahasta, String activo,
			String usuarioalt, BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (fechadesde == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechadesde ";
		if (fechahasta == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechahasta ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO CONTABLEEJERCICIOS(ejercicio,fechadesde, fechahasta, activo, usuarioalt,idempresa ) VALUES (?,?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, ejercicio);
				insert.setTimestamp(2, fechadesde);
				insert.setTimestamp(3, fechahasta);
				insert.setString(4, activo);
				insert.setString(5, usuarioalt);
				insert.setBigDecimal(6, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String contableejerciciosCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String contableejerciciosCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria
	/*
	 * public String contableejerciciosCreateOrUpdate(BigDecimal ejercicio,
	 * Timestamp fechadesde, Timestamp fechahasta, String activo, String
	 * usuarioact, Timestamp fechaact) throws EJBException { Calendar hoy = new
	 * GregorianCalendar(); Timestamp fechaact = new
	 * Timestamp(hoy.getTime().getTime()); String salida = "NOOK"; //
	 * validaciones de datos: // 1. nulidad de campos if(ejercicio == null )
	 * salida =
	 * "Error: No se puede dejar sin datos (nulo) el campo: ejercicio ";
	 * if(fechadesde == null ) salida = "Error: No se puede dejar sin datos
	 * (nulo) el campo: fechadesde "; if(fechahasta == null ) salida = "Error:
	 * No se puede dejar sin datos (nulo) el campo: fechahasta "; // 2. sin nada
	 * desde la pagina // fin validaciones boolean bError=true;
	 * if(salida.equalsIgnoreCase("NOOK")) bError = false; try { ResultSet
	 * rsSalida = null; String cQuery = "SELECT COUNT(*) FROM contableejercicios
	 * WHERE ejercicio = " + ejercicio.toString(); Statement statement =
	 * dbconn.createStatement(); rsSalida = statement.executeQuery(cQuery); int
	 * total = 0; if (rsSalida != null && rsSalida.next()) total =
	 * rsSalida.getInt(1); PreparedStatement insert = null; String sql = "";
	 * if(!bError){ if (total > 0) { // si existe hago update sql="UPDATE
	 * CONTABLEEJERCICIOS SET fechadesde=?, fechahasta=?, activo=?,
	 * usuarioact=?, fechaact=?idempresa=? WHERE ejercicio=?;"; insert =
	 * dbconn.prepareStatement(sql); insert.setTimestamp(1,fechadesde);
	 * insert.setTimestamp(2,fechahasta); insert.setString(3,activo);
	 * insert.setString(4,usuarioact); insert.setTimestamp(5,fechaact);
	 * insert.setBigDecimal(6,idempresa); insert.setBigDecimal(7,ejercicio); }
	 * else { String ins = "INSERT INTO CONTABLEEJERCICIOS(fechadesde,
	 * fechahasta, activo, usuarioalt, fechaalt ) VALUES (?, ?, ?, ?, ?)";
	 * insert = dbconn.prepareStatement(ins); //seteo de campos: String
	 * usuarioalt=usuarioact; // esta variable va a proposito
	 * insert.setTimestamp(1,fechadesde); insert.setTimestamp(2,fechahasta);
	 * insert.setString(3,activo); insert.setString(4,usuarioalt);
	 * insert.setTimestamp(5,fechaalt); } insert.executeUpdate(); salida = "Alta
	 * Correcta."; } } catch (SQLException sqlException) { salida = "Imposible
	 * dar de alta el registro."; log.error("Error SQL public String
	 * contableejerciciosCreateOrUpdate(.....)" + sqlException); } catch
	 * (Exception ex) { salida = "Imposible dar de alta el registro.";
	 * log.error("Error excepcion public String
	 * contableejerciciosCreateOrUpdate(.....)" + ex); } return salida; }
	 */
	public String contableejerciciosUpdate(BigDecimal ejercicio,
			Timestamp fechadesde, Timestamp fechahasta, String activo,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (ejercicio == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: ejercicio ";
		if (fechadesde == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechadesde ";
		if (fechahasta == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechahasta ";

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM contableejercicios "
					+ " WHERE ejercicio = " + ejercicio.toString()
					+ " and idempresa = " + idempresa.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE CONTABLEEJERCICIOS SET fechadesde=?, fechahasta=?, activo=?, usuarioact=?, fechaact=?,idempresa=? WHERE ejercicio=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setTimestamp(1, fechadesde);
					insert.setTimestamp(2, fechahasta);
					insert.setString(3, activo);
					insert.setString(4, usuarioact);
					insert.setTimestamp(5, fechaact);
					insert.setBigDecimal(6, idempresa);
					insert.setBigDecimal(7, ejercicio);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String contableejerciciosUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String contableejerciciosUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// //////////////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////////////

	public List getSumasSaldos(int idEjercicio, int mesDesde, int mesHasta,
			BigDecimal idempresa) throws EJBException, SQLException {

		ResultSet rsSalida = null;
		List vecSalida = new ArrayList();
		boolean isImputableLastCta = false;
		int nivelAnterior = 0;

		double[] saldoAnteriorNivel = new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0 };
		double[] saldoActualNivel = new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0 };
		double[] saldoDebeNivel = new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0 };
		double[] saldoHaberNivel = new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0 };

		String cQueryDDL = ""
				+ "CREATE TEMPORARY TABLE _tmp_sumasysaldos AS "
				+ "SELECT p.idcuenta,  "
				+ "       ( repeat('-- '::text, p.nivel::integer - 1) || p.cuenta)::varchar AS cuenta, "
				+ "       p.inputable, p.nivel, p.resultado, "
				+ "       SUM(COALESCE(v2.sumadebe,0)::NUMERIC(18, 2)) -  SUM(COALESCE(v2.sumahaber,0)::NUMERIC(18, 2))AS saldoanterior, "
				+ "       SUM(COALESCE(v.sumadebe, 0)::NUMERIC(18, 2)) AS sumadebe,  "
				+ "       SUM(COALESCE(v.sumahaber,0)::NUMERIC(18, 2)) AS sumahaber, "
				+ "       (SUM(COALESCE(v2.sumadebe,0)::NUMERIC(18, 2)) -  SUM(COALESCE(v2.sumahaber,0)::NUMERIC(18, 2))) +  "
				+ "       SUM(COALESCE(v.sumadebe,0)::NUMERIC(18, 2)) - SUM(COALESCE(v.sumahaber,0)::NUMERIC(18, 2))AS saldoactual, "
				+ "       p.ejercicio, p.idempresa "
				+ "  FROM contableinfiplan p "
				+ "       LEFT JOIN  "
				+ "		vsumasysaldos v ON p.idcuenta = v.idcuenta AND v.mes BETWEEN ? AND ? AND  p.ejercicio = ?"
				+ "             AND p.ejercicio = v.ejercicio AND v.idempresa = p.idempresa "
				+ "       LEFT JOIN  "
				+ "		vsumasysaldos v2 ON p.idcuenta = v2.idcuenta AND v2.mes <  ?  AND  p.ejercicio = ? "
				+ "             AND p.ejercicio = v2.ejercicio AND v2.idempresa = p.idempresa "
				+ " WHERE p.ejercicio = ? AND  p.idempresa = ?  "
				// DEBUG ... ELIMINAR CONDICION
				// + " AND p.idcuenta <= 11131100"
				+ " GROUP BY p.idcuenta, p.cuenta, p.inputable, p.nivel, p.resultado, p.ejercicio, p.idempresa "
				+ " ORDER BY idcuenta ASC, nivel ASC ";

		String cQueryDML = "UPDATE _tmp_sumasysaldos SET saldoanterior=?, sumadebe=?, sumahaber=?, saldoactual=? WHERE idcuenta=?; ";

		String cQuery = ""
				+ "SELECT idcuenta, cuenta, inputable, nivel, resultado,"
				+ "       saldoanterior, sumadebe, sumahaber, saldoactual, ejercicio, idempresa "
				+ "  FROM _tmp_sumasysaldos ";

		dbconn.setAutoCommit(false);

		try {

			PreparedStatement ps = dbconn.prepareStatement(cQueryDDL);
			ps.setInt(1, mesDesde);
			ps.setInt(2, mesHasta);
			ps.setInt(3, idEjercicio);
			ps.setInt(4, mesDesde);
			ps.setInt(5, idEjercicio);
			ps.setInt(6, idEjercicio);
			ps.setInt(7, idempresa.intValue());
			ps.execute();

			ps.clearBatch();
			ps.clearParameters();

			ps = dbconn.prepareStatement(cQuery
					+ " ORDER BY idcuenta DESC, nivel DESC; ");
			rsSalida = ps.executeQuery();

			if (rsSalida != null) {

				ps = dbconn.prepareStatement(cQueryDML);

				if (rsSalida.next()) {

					BigDecimal saldoAnterior = new BigDecimal(0);
					BigDecimal saldoDebe = new BigDecimal(0);
					BigDecimal saldoHaber = new BigDecimal(0);
					BigDecimal saldoActual = new BigDecimal(0);

					do {

						if (rsSalida.getString("inputable").equalsIgnoreCase(
								"N")) {

							if (isImputableLastCta) {

								saldoDebeNivel[rsSalida.getInt("nivel")] += saldoDebe
										.doubleValue();
								saldoHaberNivel[rsSalida.getInt("nivel")] += saldoHaber
										.doubleValue();
								// --
								saldoActualNivel[rsSalida.getInt("nivel")] += saldoActual
										.doubleValue();
								saldoAnteriorNivel[rsSalida.getInt("nivel")] += saldoAnterior
										.doubleValue();
								// --

							} else {

								if (nivelAnterior != rsSalida.getInt("nivel")) {

									saldoDebe = new BigDecimal(
											saldoDebeNivel[nivelAnterior]);
									saldoHaber = new BigDecimal(
											saldoHaberNivel[nivelAnterior]);

									saldoDebeNivel[rsSalida.getInt("nivel")] += saldoDebe
											.doubleValue();
									saldoHaberNivel[rsSalida.getInt("nivel")] += saldoHaber
											.doubleValue();

									// --

									saldoActual = new BigDecimal(
											saldoActualNivel[nivelAnterior]);
									saldoAnterior = new BigDecimal(
											saldoAnteriorNivel[nivelAnterior]);

									saldoActualNivel[rsSalida.getInt("nivel")] += saldoActual
											.doubleValue();
									saldoAnteriorNivel[rsSalida.getInt("nivel")] += saldoAnterior
											.doubleValue();
									// --

								} else {
									saldoDebe = new BigDecimal(0);
									saldoHaber = new BigDecimal(0);
									// --
									saldoActual = new BigDecimal(0);
									saldoAnterior = new BigDecimal(0);
									// --
								}

							}

							ps.clearParameters();
							// 20090602 - EJV - Grabar en cero hasta confirmar
							// que debe totalizarse ...
							// ps.setBigDecimal(1, saldoAnterior);
							ps.setBigDecimal(1, new BigDecimal(0));
							ps.setBigDecimal(2, saldoDebe);
							ps.setBigDecimal(3, saldoHaber);
							// 20090602 - EJV - Grabar en cero hasta confirmar
							// que debe totalizarse ...
							// ps.setBigDecimal(4, saldoActual);
							ps.setBigDecimal(4, new BigDecimal(0));
							ps.setBigDecimal(5, rsSalida
									.getBigDecimal("idcuenta"));

							ps.execute();

							if (rsSalida.getInt("nivel") == 1) {
								for (int x = 0; x < saldoDebeNivel.length; x++)
									saldoAnteriorNivel[x] = saldoDebeNivel[x] = saldoHaberNivel[x] = saldoActualNivel[x] = 0;
							} else {
								for (int x = rsSalida.getInt("nivel") + 1; x < saldoDebeNivel.length; x++)
									saldoAnteriorNivel[x] = saldoDebeNivel[x] = saldoHaberNivel[x] = saldoActualNivel[x] = 0;
							}

							saldoAnterior = saldoDebe = saldoHaber = saldoActual = new BigDecimal(
									00);

							isImputableLastCta = false;

						} else
							isImputableLastCta = true;

						saldoDebe = saldoDebe.add(rsSalida
								.getBigDecimal("sumadebe"));
						saldoHaber = saldoHaber.add(rsSalida
								.getBigDecimal("sumahaber"));
						// --

						saldoActual = saldoDebe.add(rsSalida
								.getBigDecimal("saldoactual"));
						saldoAnterior = saldoHaber.add(rsSalida
								.getBigDecimal("saldoanterior"));

						// --

						nivelAnterior = rsSalida.getInt("nivel");

					} while (rsSalida.next());

				} else
					log
							.warn("getSumasSaldos() - ResultSet vacio desde tambla temporal  : _tmp_sumasysaldos.");

			} else
				log
						.error("getSumasSaldos() - ResultSet nulo desde tambla temporal  : _tmp_sumasysaldos.");

			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery
					+ " ORDER BY idcuenta ASC, nivel ASC; ");
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

			cQueryDDL = "DROP TABLE _tmp_sumasysaldos; ";
			ps = dbconn.prepareStatement(cQueryDML);

		} catch (SQLException sqlException) {
			log.error("Error SQL(getSumasSaldos): " + sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception(getSumasSaldos): " + ex);
		}

		// Forzar rollback siempre.
		dbconn.rollback();

		dbconn.setAutoCommit(true);

		return vecSalida;
	}

	// /////////////////////////////////////////////////////////////////////////////////

	public List getSumasSaldosBACKUP_20090619(int idEjercicio, int mesDesde,
			int mesHasta, BigDecimal idempresa) throws EJBException,
			SQLException {

		ResultSet rsSalida = null;
		List vecSalida = new ArrayList();

		double[] saldoAnteriorNivel = new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0 };
		double[] saldoActualNivel = new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0 };
		double[] saldoDebeNivel = new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0 };
		double[] saldoHaberNivel = new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0 };

		String cQueryDDL = ""
				+ "CREATE TEMPORARY TABLE _tmp_sumasysaldos AS "
				+ "SELECT p.idcuenta,  "
				+ "       ( repeat('-- '::text, p.nivel::integer - 1) || p.cuenta)::varchar AS cuenta, "
				+ "       p.inputable, p.nivel, p.resultado, "
				+ "       SUM(COALESCE(v2.sumadebe,0)::NUMERIC(18, 2)) -  SUM(COALESCE(v2.sumahaber,0)::NUMERIC(18, 2))AS saldoanterior, "
				+ "       SUM(COALESCE(v.sumadebe, 0)::NUMERIC(18, 2)) AS sumadebe,  "
				+ "       SUM(COALESCE(v.sumahaber,0)::NUMERIC(18, 2)) AS sumahaber, "
				+ "       (SUM(COALESCE(v2.sumadebe,0)::NUMERIC(18, 2)) -  SUM(COALESCE(v2.sumahaber,0)::NUMERIC(18, 2))) +  "
				+ "       SUM(COALESCE(v.sumadebe,0)::NUMERIC(18, 2)) - SUM(COALESCE(v.sumahaber,0)::NUMERIC(18, 2))AS saldoactual, "
				+ "       p.ejercicio, p.idempresa "
				+ "  FROM contableinfiplan p "
				+ "       LEFT JOIN  "
				+ "		vsumasysaldos v ON p.idcuenta = v.idcuenta AND v.mes BETWEEN ? AND ? AND  p.ejercicio = ?"
				+ "             AND p.ejercicio = v.ejercicio AND v.idempresa = p.idempresa "
				+ "       LEFT JOIN  "
				+ "		vsumasysaldos v2 ON p.idcuenta = v2.idcuenta AND v2.mes <  ?  AND  p.ejercicio = ? "
				+ "             AND p.ejercicio = v2.ejercicio AND v2.idempresa = p.idempresa "
				+ " WHERE p.ejercicio = ? AND  p.idempresa = ? "
				+ " GROUP BY p.idcuenta, p.cuenta, p.inputable, p.nivel, p.resultado, p.ejercicio, p.idempresa "
				+ " ORDER BY idcuenta ASC, nivel ASC ";

		String cQueryDML = "UPDATE _tmp_sumasysaldos SET saldoanterior=?, sumadebe=?, sumahaber=?, saldoactual=? WHERE idcuenta=?; ";

		String cQuery = ""
				+ "SELECT idcuenta, cuenta, inputable, nivel, resultado,"
				+ "       saldoanterior, sumadebe, sumahaber, saldoactual, ejercicio, idempresa "
				+ "  FROM _tmp_sumasysaldos ";

		dbconn.setAutoCommit(false);

		try {

			PreparedStatement ps = dbconn.prepareStatement(cQueryDDL);
			ps.setInt(1, mesDesde);
			ps.setInt(2, mesHasta);
			ps.setInt(3, idEjercicio);
			ps.setInt(4, mesDesde);
			ps.setInt(5, idEjercicio);
			ps.setInt(6, idEjercicio);
			ps.setInt(7, idempresa.intValue());
			ps.execute();

			ps.clearBatch();
			ps.clearParameters();

			ps = dbconn.prepareStatement(cQuery
					+ " ORDER BY idcuenta DESC, nivel DESC; ");
			rsSalida = ps.executeQuery();

			if (rsSalida != null) {

				ps = dbconn.prepareStatement(cQueryDML);

				if (rsSalida.next()) {

					BigDecimal saldoAnterior = new BigDecimal(0);
					BigDecimal saldoDebe = new BigDecimal(0);
					BigDecimal saldoHaber = new BigDecimal(0);
					BigDecimal saldoActual = new BigDecimal(0);

					do {

						saldoAnterior = saldoDebe = saldoHaber = saldoActual = new BigDecimal(
								00);

						saldoAnterior = saldoAnterior.add(rsSalida
								.getBigDecimal("saldoanterior"));
						saldoDebe = saldoDebe.add(rsSalida
								.getBigDecimal("sumadebe"));
						saldoHaber = saldoHaber.add(rsSalida
								.getBigDecimal("sumahaber"));

						saldoAnteriorNivel[rsSalida.getInt("nivel") - 1] += saldoAnterior
								.doubleValue();
						saldoDebeNivel[rsSalida.getInt("nivel") - 1] += saldoDebe
								.doubleValue();
						saldoHaberNivel[rsSalida.getInt("nivel") - 1] += saldoHaber
								.doubleValue();
						saldoActualNivel[rsSalida.getInt("nivel") - 1] += saldoActual
								.doubleValue();

						if (rsSalida.getString("inputable").equalsIgnoreCase(
								"N")) {

							for (int j = rsSalida.getInt("nivel") - 1; j < 12; j++) {
								saldoAnterior = saldoDebe.add(new BigDecimal(
										saldoAnteriorNivel[j]));
								saldoDebe = saldoDebe.add(new BigDecimal(
										saldoDebeNivel[j]));
								saldoHaber = saldoDebe.add(new BigDecimal(
										saldoHaberNivel[j]));
								saldoActual = saldoDebe.add(new BigDecimal(
										saldoActualNivel[j]));

							}

							ps.clearParameters();
							// 20090602 - EJV - Grabar en cero hasta confirmar
							// que debe totalizarse ...
							// ps.setBigDecimal(1, saldoAnterior);
							ps.setBigDecimal(1, new BigDecimal(0));
							ps.setBigDecimal(2, saldoDebe);
							ps.setBigDecimal(3, saldoHaber);
							// 20090602 - EJV - Grabar en cero hasta confirmar
							// que debe totalizarse ...
							// ps.setBigDecimal(4, saldoActual);
							ps.setBigDecimal(4, new BigDecimal(0));
							ps.setBigDecimal(5, rsSalida
									.getBigDecimal("idcuenta"));

							ps.execute();

							saldoAnteriorNivel[rsSalida.getInt("nivel") - 1] = saldoDebeNivel[rsSalida
									.getInt("nivel") - 1] = saldoHaberNivel[rsSalida
									.getInt("nivel") - 1] = saldoActualNivel[rsSalida
									.getInt("nivel") - 1] = 0;

						}

					} while (rsSalida.next());

				} else
					log
							.warn("getSumasSaldos() - ResultSet vacio desde tambla temporal  : _tmp_sumasysaldos.");

			} else
				log
						.error("getSumasSaldos() - ResultSet nulo desde tambla temporal  : _tmp_sumasysaldos.");

			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery
					+ " ORDER BY idcuenta ASC, nivel ASC; ");
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

			cQueryDDL = "DROP TABLE _tmp_sumasysaldos; ";
			ps = dbconn.prepareStatement(cQueryDML);

		} catch (SQLException sqlException) {
			log.error("Error SQL(getSumasSaldos): " + sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception(getSumasSaldos): " + ex);
		}

		// Forzar rollback siempre.
		dbconn.rollback();

		dbconn.setAutoCommit(true);

		return vecSalida;
	}

	// resumen de movimientos historicos
	public List getResumenMovHistorico(BigDecimal idempresa,
			BigDecimal ejercicio) throws EJBException {

		String cQuery = ""
				+ "SELECT idcuenta,cuenta,inputable,enero,febrero,marzo,abril,mayo,junio,julio,agosto,setiembre,octubre,noviembre,diciembre,total   "
				+ " from vcontableresumenperiodos " + " where  idempresa ="
				+ idempresa.toString() + " and ejercicio = "
				+ ejercicio.toString() + " ORDER BY 2 ";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// contable rubros bienes de uso
	public List getContablerubieusoAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {

		String cQuery = " SELECT idrubro,rubro,anios,usuarioalt,usuarioact,fechaalt,fechaact,idempresa "
				+ " FROM CONTABLERUBIEUSO "
				+ " WHERE idempresa = "
				+ idempresa.toString()
				+ " ORDER BY 2  LIMIT "
				+ limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)
	public List getContablerubieusoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		String cQuery = "SELECT  idrubro,rubro,anios,usuarioalt,usuarioact,fechaalt,fechaact,idempresa "
				+ " FROM CONTABLERUBIEUSO "
				+ " where idempresa= "
				+ idempresa.toString()
				+ " and (idrubro::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " UPPER(rubro) LIKE '%"
				+ ocurrencia.toUpperCase() + "%') " + " ORDER BY 2";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)
	public List getContablerubieusoPK(BigDecimal idrubro, BigDecimal idempresa)
			throws EJBException {
		String cQuery = "SELECT  idrubro,rubro,anios,usuarioalt,usuarioact,fechaalt,fechaact,idempresa "
				+ " FROM CONTABLERUBIEUSO "
				+ " WHERE idrubro = "
				+ idrubro.toString()
				+ " AND idempresa = "
				+ idempresa.toString() + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)
	public String contablerubieusoDelete(BigDecimal idrubro,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " SELECT * FROM CONTABLERUBIEUSO "
				+ " WHERE idrubro = " + idrubro.toString()
				+ " and idempresa = " + idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM CONTABLERUBIEUSO " + " WHERE idrubro = "
						+ idrubro.toString() + " and idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : contablerubieusoDelete( BigDecimal idrubro ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: contablerubieusoDelete( BigDecimal idrubro )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String contablerubieusoCreate(String rubro, BigDecimal anios,
			String usuarioalt, BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// if(fechaalt == null )
		// salida = "Error: No se puede dejar sin datos (nulo) el campo:
		// fechaalt ";
		// 2. sin nada desde la pagina
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO CONTABLERUBIEUSO(rubro, anios, usuarioalt, idempresa ) VALUES (?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, rubro);
				insert.setBigDecimal(2, anios);
				insert.setString(3, usuarioalt);
				insert.setBigDecimal(4, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String contablerubieusoCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String contablerubieusoCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria
	/*
	 * public String contablerubieusoCreateOrUpdate(BigDecimal idrubro, String
	 * rubro, BigDecimal anios, String usuarioact,BigDecimal idempresa) throws
	 * EJBException { Calendar hoy = new GregorianCalendar(); Timestamp fechaact
	 * = new Timestamp(hoy.getTime().getTime()); String salida = "NOOK"; //
	 * validaciones de datos: // 1. nulidad de campos if(idrubro == null )
	 * salida = "Error: No se puede dejar sin datos (nulo) el campo: idrubro ";
	 * // 2. sin nada desde la pagina // fin validaciones boolean bError=true;
	 * if(salida.equalsIgnoreCase("NOOK")) bError = false; try { ResultSet
	 * rsSalida = null; String cQuery = "SELECT COUNT(*) FROM contablerubieuso
	 * WHERE idrubro = " + idrubro.toString(); Statement statement =
	 * dbconn.createStatement(); rsSalida = statement.executeQuery(cQuery); int
	 * total = 0; if (rsSalida != null && rsSalida.next()) total =
	 * rsSalida.getInt(1); PreparedStatement insert = null; String sql = "";
	 * if(!bError){ if (total > 0) { // si existe hago update sql="UPDATE
	 * CONTABLERUBIEUSO SET rubro=?, anios=?, usuarioact=?,
	 * fechaact=?idempresa=? WHERE idrubro=?;"; insert =
	 * dbconn.prepareStatement(sql); insert.setString(1,rubro);
	 * insert.setBigDecimal(2,anios); insert.setString(3,usuarioact);
	 * insert.setTimestamp(4,fechaact); insert.setBigDecimal(5,idempresa);
	 * insert.setBigDecimal(6,idrubro); } else { String ins = "INSERT INTO
	 * CONTABLERUBIEUSO(rubro, anios, usuarioalt, fechaalt ) VALUES (?, ?, ?,
	 * ?)"; insert = dbconn.prepareStatement(ins); //seteo de campos: String
	 * usuarioalt=usuarioact; // esta variable va a proposito
	 * insert.setString(1,rubro); insert.setBigDecimal(2,anios);
	 * insert.setString(3,usuarioalt); //insert.setTimestamp(4,fechaalt); }
	 * insert.executeUpdate(); salida = "Alta Correcta."; } } catch
	 * (SQLException sqlException) { salida = "Imposible dar de alta el
	 * registro."; log.error("Error SQL public String
	 * contablerubieusoCreateOrUpdate(.....)" + sqlException); } catch
	 * (Exception ex) { salida = "Imposible dar de alta el registro.";
	 * log.error("Error excepcion public String
	 * contablerubieusoCreateOrUpdate(.....)" + ex); } return salida; }
	 */
	public String contablerubieusoUpdate(BigDecimal idrubro, String rubro,
			BigDecimal anios, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idrubro == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idrubro ";

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM contablerubieuso "
					+ " WHERE idrubro = " + idrubro.toString()
					+ " and idempresa = " + idempresa.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE CONTABLERUBIEUSO SET rubro=?, anios=?, usuarioact=?, fechaact=? WHERE idrubro=? and idempresa=? ;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, rubro);
					insert.setBigDecimal(2, anios);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, idrubro);
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
			log.error("Error SQL public String contablerubieusoUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String contablerubieusoUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// contable bienes de uso
	public List getContablebienusoAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		String cQuery = "SELECT"
				+ " CON.idbienuso,"
				+ " CON.bienuso,"
				+ " cc1.rubro,"
				+ " CON.fechacompra,"
				+ " CON.valorori,"
				+ " CON.anios,"
				+ " CON.meses,"
				+ " CON.idempresa,"
				+ " CON.usuarioalt,"
				+ " CON.usuarioact,"
				+ " CON.fechaalt,"
				+ " CON.fechaact "
				+ " FROM "
				+ " CONTABLEBIENUSO CON"
				+ " LEFT JOIN contablerubieuso cc1 ON CON.idrubro = cc1.idrubro and CON.idempresa = cc1.idempresa "
				+ " WHERE CON.idempresa = " + idempresa.toString()
				+ " and CON.fechabaja is null " + "  ORDER BY 2  LIMIT "
				+ limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getContablebienusoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		String cQuery = "SELECT "
				+ " CON.idbienuso,"
				+ " CON.bienuso,"
				+ " cc1.rubro,"
				+ " CON.fechacompra,"
				+ " CON.valorori,"
				+ " CON.anios,"
				+ " CON.meses,"
				+ " CON.idempresa,"
				+ " CON.usuarioalt,"
				+ " CON.usuarioact,"
				+ " CON.fechaalt,"
				+ " CON.fechaact "
				+ " FROM "
				+ " CONTABLEBIENUSO CON"
				+ " LEFT JOIN contablerubieuso cc1 ON CON.idrubro = cc1.idrubro and CON.idempresa = cc1.idempresa "
				+ " where CON.idempresa= " + idempresa.toString()
				+ " and CON.fechabaja is null "
				+ " and (CON.idbienuso::VARCHAR LIKE '%" + ocurrencia
				+ "%' OR " + " UPPER(CON.bienuso) LIKE '%"
				+ ocurrencia.toUpperCase() + "%') " + " ORDER BY 2";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getContablebienusoPK(BigDecimal idbienuso, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT "
				+ " CON.idbienuso,"
				+ " CON.bienuso,"
				+ " CON.idrubro,"
				+ " cc1.rubro,"
				+ " CON.fechacompra,"
				+ " CON.valorori,"
				+ " CON.anios,"
				+ " CON.meses,"
				+ " CON.idempresa,"
				+ " CON.usuarioalt,"
				+ " CON.usuarioact,"
				+ " CON.fechaalt,"
				+ " CON.fechaact "
				+ " FROM "
				+ " CONTABLEBIENUSO CON"
				+ " LEFT JOIN contablerubieuso cc1 ON CON.idrubro = cc1.idrubro and CON.idempresa = cc1.idempresa "
				+ " WHERE CON.idbienuso = " + idbienuso.toString()
				+ " and CON.fechabaja is null " + " AND CON.idempresa = "
				+ idempresa.toString() + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String contablebienusoDelete(BigDecimal idbienuso,
			BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechabaja = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM contablebienuso "
					+ " WHERE idbienuso = " + idbienuso.toString()
					+ " and idempresa = " + idempresa.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE CONTABLEBIENUSO SET fechabaja=? WHERE idbienuso=? and idempresa =? ;";
					insert = dbconn.prepareStatement(sql);
					insert.setTimestamp(1, fechabaja);
					insert.setBigDecimal(2, idbienuso);
					insert.setBigDecimal(3, idempresa);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String contablebienusoDelete(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String contablebienusoDelete(.....)"
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String contablebienusoCreate(String bienuso, BigDecimal idrubro,
			Timestamp fechacompra, Double valorori, BigDecimal anios,
			BigDecimal meses, BigDecimal idempresa, String usuarioalt)
			throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (bienuso == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: bienuso ";
		if (idrubro == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idrubro ";
		if (fechacompra == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechacompra ";
		if (valorori == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: valorori ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (bienuso.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: bienuso ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO CONTABLEBIENUSO(bienuso, idrubro, fechacompra, valorori, anios, meses, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, bienuso);
				insert.setBigDecimal(2, idrubro);
				insert.setTimestamp(3, fechacompra);
				insert.setDouble(4, valorori.doubleValue());
				insert.setBigDecimal(5, anios);
				insert.setBigDecimal(6, meses);
				insert.setBigDecimal(7, idempresa);
				insert.setString(8, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String contablebienusoCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String contablebienusoCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria
	/*
	 * public String contablebienusoCreateOrUpdate(BigDecimal idbienuso, String
	 * bienuso, BigDecimal idrubro, Timestamp fechacompra, Double valorori,
	 * Timestamp fechabaja, BigDecimal anios, BigDecimal meses, BigDecimal
	 * idempresa, String usuarioact) throws EJBException { Calendar hoy = new
	 * GregorianCalendar(); Timestamp fechaact = new
	 * Timestamp(hoy.getTime().getTime()); String salida = "NOOK"; //
	 * validaciones de datos: // 1. nulidad de campos if(idbienuso == null )
	 * salida =
	 * "Error: No se puede dejar sin datos (nulo) el campo: idbienuso ";
	 * if(bienuso == null ) salida = "Error: No se puede dejar sin datos (nulo)
	 * el campo: bienuso "; if(idrubro == null ) salida = "Error: No se puede
	 * dejar sin datos (nulo) el campo: idrubro "; if(fechacompra == null )
	 * salida =
	 * "Error: No se puede dejar sin datos (nulo) el campo: fechacompra ";
	 * if(valorori == null ) salida = "Error: No se puede dejar sin datos (nulo)
	 * el campo: valorori "; if(idempresa == null ) salida = "Error: No se puede
	 * dejar sin datos (nulo) el campo: idempresa "; // 2. sin nada desde la
	 * pagina if(bienuso.equalsIgnoreCase("")) salida = "Error: No se puede
	 * dejar vacio el campo: bienuso "; // fin validaciones boolean bError=true;
	 * if(salida.equalsIgnoreCase("NOOK")) bError = false; try { ResultSet
	 * rsSalida = null; String cQuery = "SELECT COUNT(*) FROM contablebienuso
	 * WHERE idbienuso = " + idbienuso.toString(); Statement statement =
	 * dbconn.createStatement(); rsSalida = statement.executeQuery(cQuery); int
	 * total = 0; if (rsSalida != null && rsSalida.next()) total =
	 * rsSalida.getInt(1); PreparedStatement insert = null; String sql = "";
	 * if(!bError){ if (total > 0) { // si existe hago update sql="UPDATE
	 * CONTABLEBIENUSO SET bienuso=?, idrubro=?, fechacompra=?, valorori=?,
	 * fechabaja=?, anios=?, meses=?, idempresa=?, usuarioact=?, fechaact=?
	 * WHERE idbienuso=?;"; insert = dbconn.prepareStatement(sql);
	 * insert.setString(1,bienuso); insert.setBigDecimal(2,idrubro);
	 * insert.setTimestamp(3,fechacompra);
	 * insert.setDouble(4,valorori.doubleValue());
	 * insert.setTimestamp(5,fechabaja); insert.setBigDecimal(6,anios);
	 * insert.setBigDecimal(7,meses); insert.setBigDecimal(8,idempresa);
	 * insert.setString(9,usuarioact); insert.setTimestamp(10,fechaact);
	 * insert.setBigDecimal(11,idbienuso); } else { String ins = "INSERT INTO
	 * CONTABLEBIENUSO(bienuso, idrubro, fechacompra, valorori, fechabaja,
	 * anios, meses, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?, ?, ?, ?,
	 * ?)"; insert = dbconn.prepareStatement(ins); //seteo de campos: String
	 * usuarioalt=usuarioact; // esta variable va a proposito
	 * insert.setString(1,bienuso); insert.setBigDecimal(2,idrubro);
	 * insert.setTimestamp(3,fechacompra);
	 * insert.setDouble(4,valorori.doubleValue());
	 * insert.setTimestamp(5,fechabaja); insert.setBigDecimal(6,anios);
	 * insert.setBigDecimal(7,meses); insert.setBigDecimal(8,idempresa);
	 * insert.setString(9,usuarioalt); } insert.executeUpdate(); salida = "Alta
	 * Correcta."; } } catch (SQLException sqlException) { salida = "Imposible
	 * dar de alta el registro."; log.error("Error SQL public String
	 * contablebienusoCreateOrUpdate(.....)" + sqlException); } catch (Exception
	 * ex) { salida = "Imposible dar de alta el registro."; log.error("Error
	 * excepcion public String contablebienusoCreateOrUpdate(.....)" + ex); }
	 * return salida; }
	 */
	public String contablebienusoUpdate(BigDecimal idbienuso, String bienuso,
			BigDecimal idrubro, Timestamp fechacompra, Double valorori,
			BigDecimal anios, BigDecimal meses, BigDecimal idempresa,
			String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idbienuso == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idbienuso ";
		if (bienuso == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: bienuso ";
		if (idrubro == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idrubro ";
		if (fechacompra == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechacompra ";
		if (valorori == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: valorori ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (bienuso.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: bienuso ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM contablebienuso "
					+ " WHERE idbienuso = " + idbienuso.toString()
					+ " and idempresa = " + idempresa.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE CONTABLEBIENUSO SET bienuso=?, idrubro=?, fechacompra=?, valorori=?, anios=?, meses=?, usuarioact=?, fechaact=? WHERE idbienuso=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, bienuso);
					insert.setBigDecimal(2, idrubro);
					insert.setTimestamp(3, fechacompra);
					insert.setDouble(4, valorori.doubleValue());
					insert.setBigDecimal(5, anios);
					insert.setBigDecimal(6, meses);
					insert.setString(7, usuarioact);
					insert.setTimestamp(8, fechaact);
					insert.setBigDecimal(9, idbienuso);
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
			log.error("Error SQL public String contablebienusoUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String contablebienusoUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public long getTotalEntidadBienesdeUso(String entidad, BigDecimal idempresa)
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
				+ " WHERE idempresa=" + idempresa.toString()
				+ " and fechabaja is null";
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

	public long getTotalEntidadBienesdeUsoOcu(String entidad, String[] campos,
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
		String cQuery = "SELECT count(1)AS total FROM " + entidad
				+ " WHERE fechabaja is null and ";
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

	// contable rubros
	public List getContablerubrosAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " SELECT cod_rubro,desc_rubro,ubicacion,idempresa,usuarioalt,usuarioact,fechaalt,fechaact "
				+ " FROM CONTABLERUBROS "
				+ " WHERE idempresa = "
				+ idempresa.toString()
				+ "  ORDER BY 2  LIMIT "
				+ limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getContablerubrosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " SELECT  cod_rubro,desc_rubro,ubicacion,idempresa,usuarioalt,usuarioact,fechaalt,fechaact "
				+ " FROM CONTABLERUBROS "
				+ " where idempresa= "
				+ idempresa.toString()
				+ " and (cod_rubro::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " UPPER(desc_rubro) LIKE '%"
				+ ocurrencia.toUpperCase() + "%') " + " ORDER BY 2";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getContablerubrosPK(BigDecimal cod_rubro, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  cod_rubro,desc_rubro,ubicacion,idempresa,usuarioalt,usuarioact,fechaalt,fechaact "
				+ " FROM CONTABLERUBROS "
				+ " WHERE cod_rubro = "
				+ cod_rubro.toString()
				+ " AND idempresa = "
				+ idempresa.toString() + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String contablerubrosDelete(BigDecimal cod_rubro,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM CONTABLERUBROS " + " WHERE cod_rubro = "
				+ cod_rubro.toString() + " and idempresa = "
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM CONTABLERUBROS " + " WHERE cod_rubro = "
						+ cod_rubro.toString() + " and idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : contablerubrosDelete( BigDecimal cod_rubro ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: contablerubrosDelete( BigDecimal cod_rubro )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String contablerubrosCreate(String desc_rubro, String ubicacion,
			BigDecimal idempresa, String usuarioalt) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (desc_rubro == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: desc_rubro ";
		if (ubicacion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: ubicacion ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (desc_rubro.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: desc_rubro ";
		if (ubicacion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: ubicacion ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO CONTABLERUBROS(desc_rubro, ubicacion, idempresa, usuarioalt ) VALUES (?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, desc_rubro);
				insert.setString(2, ubicacion);
				insert.setBigDecimal(3, idempresa);
				insert.setString(4, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String contablerubrosCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String contablerubrosCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria
	/*
	 * public String contablerubrosCreateOrUpdate(BigDecimal cod_rubro, String
	 * desc_rubro, String ubicacion, BigDecimal idempresa, String usuarioact)
	 * throws EJBException { Calendar hoy = new GregorianCalendar(); Timestamp
	 * fechaact = new Timestamp(hoy.getTime().getTime()); String salida =
	 * "NOOK"; // validaciones de datos: // 1. nulidad de campos if(cod_rubro ==
	 * null ) salida = "Error: No se puede dejar sin datos (nulo) el campo:
	 * cod_rubro "; if(desc_rubro == null ) salida = "Error: No se puede dejar
	 * sin datos (nulo) el campo: desc_rubro "; if(ubicacion == null ) salida =
	 * "Error: No se puede dejar sin datos (nulo) el campo: ubicacion ";
	 * if(idempresa == null ) salida = "Error: No se puede dejar sin datos
	 * (nulo) el campo: idempresa "; // 2. sin nada desde la pagina
	 * if(desc_rubro.equalsIgnoreCase("")) salida = "Error: No se puede dejar
	 * vacio el campo: desc_rubro "; if(ubicacion.equalsIgnoreCase("")) salida =
	 * "Error: No se puede dejar vacio el campo: ubicacion "; // fin
	 * validaciones boolean bError=true; if(salida.equalsIgnoreCase("NOOK"))
	 * bError = false; try { ResultSet rsSalida = null; String cQuery = "SELECT
	 * COUNT(*) FROM contablerubros WHERE cod_rubro = " + cod_rubro.toString();
	 * Statement statement = dbconn.createStatement(); rsSalida =
	 * statement.executeQuery(cQuery); int total = 0; if (rsSalida != null &&
	 * rsSalida.next()) total = rsSalida.getInt(1); PreparedStatement insert =
	 * null; String sql = ""; if(!bError){ if (total > 0) { // si existe hago
	 * update sql="UPDATE CONTABLERUBROS SET desc_rubro=?, ubicacion=?,
	 * idempresa=?, usuarioact=?, fechaact=? WHERE cod_rubro=?;"; insert =
	 * dbconn.prepareStatement(sql); insert.setString(1,desc_rubro);
	 * insert.setString(2,ubicacion); insert.setBigDecimal(3,idempresa);
	 * insert.setString(4,usuarioact); insert.setTimestamp(5,fechaact);
	 * insert.setBigDecimal(6,cod_rubro); } else { String ins = "INSERT INTO
	 * CONTABLERUBROS(desc_rubro, ubicacion, idempresa, usuarioalt ) VALUES (?,
	 * ?, ?, ?)"; insert = dbconn.prepareStatement(ins); //seteo de campos:
	 * String usuarioalt=usuarioact; // esta variable va a proposito
	 * insert.setString(1,desc_rubro); insert.setString(2,ubicacion);
	 * insert.setBigDecimal(3,idempresa); insert.setString(4,usuarioalt); }
	 * insert.executeUpdate(); salida = "Alta Correcta."; } } catch
	 * (SQLException sqlException) { salida = "Imposible dar de alta el
	 * registro."; log.error("Error SQL public String
	 * contablerubrosCreateOrUpdate(.....)" + sqlException); } catch (Exception
	 * ex) { salida = "Imposible dar de alta el registro."; log.error("Error
	 * excepcion public String contablerubrosCreateOrUpdate(.....)" + ex); }
	 * return salida; }
	 */
	public String contablerubrosUpdate(BigDecimal cod_rubro, String desc_rubro,
			String ubicacion, BigDecimal idempresa, String usuarioact)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (cod_rubro == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cod_rubro ";
		if (desc_rubro == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: desc_rubro ";
		if (ubicacion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: ubicacion ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (desc_rubro.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: desc_rubro ";
		if (ubicacion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: ubicacion ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM contablerubros WHERE cod_rubro = "
					+ cod_rubro.toString()
					+ " and idempresa = "
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
					sql = "UPDATE CONTABLERUBROS SET desc_rubro=?, ubicacion=?, usuarioact=?, fechaact=? WHERE cod_rubro=? and  idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, desc_rubro);
					insert.setString(2, ubicacion);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, cod_rubro);
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
			log.error("Error SQL public String contablerubrosUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String contablerubrosUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * Metodos para la entidad: VCAJASUBCONTABILIDAD - VSTOCKSUBCONTABILIDAD -
	 * VPROVEEDORESSUBCONTABILIDAD -Copyrigth(r) sysWarp S.R.L. Fecha de
	 * creacion: Tue Jul 07 08:34:18 GYT 2009
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getSubcontabilidadAll(String entidad, BigDecimal ejercicio,
			String fDesde, String fHasta, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;

		/*
		 * 
		 * +
		 * " SC.comprobante as idasiento,  0 as renglon, SC.tipomov, SC.fechamov as fecha,"
		 * + " SC.detalle, SC.detalle as leyenda, SC.cuenta," +
		 * " SC.importe, PLAN.cuenta as cuentadesc " + "   from " + vista +
		 * " SC " +
		 * "    left join contableinfiplan PLAN on ( SC.cuenta = PLAN.idcuenta and SC.idempresa = PLAN.idempresa and PLAN.ejercicio = "
		 * + idEjercicio + ")" + " where SC.idempresa = " + idempresa.toString()
		 * +
		 * "  and SC.fechamov::date between to_date(?,'dd/mm/yyyy') and to_date(?,'dd/mm/yyyy') order by SC.comprobante, SC.tipomov, SC.importe desc"
		 * ;
		 */

		String cQuery = ""
				+ "SELECT ent.fechamov, ent.cartera, ent.comprobante, ent.tipomov, "
				+ "       ent.cuenta, ip.cuenta AS descripcion, ent.importe::NUMERIC(18, 2) AS importe,"
				+ "       ent.cencosto, ent.subcencosto, ent.detalle, ent.idempresa, ent.idasociacion"
				+ "  FROM "
				+ entidad
				+ " ent "
				+ "       LEFT JOIN contableinfiplan ip ON ent.cuenta = ip.idcuenta "
				+ "             AND ent.idempresa = ip.idempresa"
				+ "             AND ip.ejercicio = "
				+ ejercicio
				+ " WHERE ent.idempresa = "
				+ idempresa.toString()
				+ " AND ent.fechamov::DATE BETWEEN TO_DATE('"
				+ fDesde
				+ "', 'dd/mm/yyyy') AND TO_DATE('"
				+ fHasta
				+ "', 'dd/mm/yyyy') ORDER BY ent.comprobante, ent.tipomov, ent.importe DESC ;";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getLibroMayorContFinanciera(BigDecimal idcuenta_desde,
			BigDecimal idcuenta_hasta, java.sql.Date fecha_desde,
			java.sql.Date fecha_hasta, BigDecimal anio, BigDecimal idempresa)
			throws EJBException {
		/**
		 * Utilidad : Metodo que conforma el libro mayor
		 * 
		 * Utilidad : traer todos los centros de costos existentes
		 */
		ResultSet rsSalida = null;
		List vecSalida = new ArrayList();
		try {
			Statement statement = dbconn.createStatement();
			String cQuery = "";
			cQuery += "SELECT * ";
			cQuery += "  FROM vcajasubcontabilidadmayor lm ";
			cQuery += " WHERE lm.parcuenta BETWEEN "
					+ idcuenta_desde.toString() + " AND "
					+ idcuenta_hasta.toString();
			cQuery += "   AND lm.fecha BETWEEN '" + fecha_desde.toString()
					+ "'::DATE AND '" + fecha_hasta.toString() + "'::DATE";
			cQuery += "   AND lm.idempresa = " + idempresa.toString();
			cQuery += "   AND lm.ejercicio = " + anio.toString();
			cQuery += " ORDER BY parcuenta, fecha ASC, nroasiento";
			rsSalida = statement.executeQuery(cQuery);
			ResultSetMetaData md = rsSalida.getMetaData();
			BigDecimal acumulado = new BigDecimal(0);
			String cuenta = "";

			while (rsSalida.next()) {
				int totCampos = md.getColumnCount() - 1;
				String[] sSalida = new String[totCampos + 2];
				int i = 0;
				while (i <= totCampos) {
					sSalida[i] = rsSalida.getString(++i);
				}
				if (!cuenta.equals(sSalida[0])) {
					cuenta = sSalida[0];
					acumulado = new BigDecimal(0);
				}
				acumulado = acumulado.add(new BigDecimal(sSalida[9]));
				acumulado = acumulado.add(new BigDecimal(sSalida[10]).negate());

				// acumulado.add( new BigDecimal(sSalida[9]).negate() );
				sSalida[totCampos + 1] = acumulado.toString();
				vecSalida.add(sSalida);
			}
		} catch (SQLException sqlException) {
			log.error("Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception: " + ex);
		}
		return vecSalida;
	}

	public List getLibroMayorContFinancieraProveedores(
			BigDecimal idcuenta_desde, BigDecimal idcuenta_hasta,
			java.sql.Date fecha_desde, java.sql.Date fecha_hasta,
			BigDecimal anio, BigDecimal idempresa) throws EJBException {
		/**
		 * Utilidad : Metodo que conforma el libro mayor
		 * 
		 * Utilidad : traer todos los centros de costos existentes
		 */
		ResultSet rsSalida = null;
		List vecSalida = new ArrayList();
		try {
			Statement statement = dbconn.createStatement();
			String cQuery = "";
			cQuery += "SELECT * ";
			cQuery += "  FROM vproveedoressubcontabilidadmayor lm ";
			cQuery += " WHERE lm.parcuenta BETWEEN "
					+ idcuenta_desde.toString() + " AND "
					+ idcuenta_hasta.toString();
			cQuery += "   AND lm.fecha BETWEEN '" + fecha_desde.toString()
					+ "'::DATE AND '" + fecha_hasta.toString() + "'::DATE";
			cQuery += "   AND lm.idempresa = " + idempresa.toString();
			cQuery += " ORDER BY parcuenta, fecha ASC, nroasiento";
			rsSalida = statement.executeQuery(cQuery);
			ResultSetMetaData md = rsSalida.getMetaData();
			BigDecimal acumulado = new BigDecimal(0);
			String cuenta = "";
			while (rsSalida.next()) {
				int totCampos = md.getColumnCount() - 1;
				String[] sSalida = new String[totCampos + 2];
				int i = 0;
				while (i <= totCampos) {
					sSalida[i] = rsSalida.getString(++i);
				}
				if (!cuenta.equals(sSalida[0])) {
					cuenta = sSalida[0];
					acumulado = new BigDecimal(0);
				}
				acumulado = acumulado.add(new BigDecimal(sSalida[9]));
				acumulado = acumulado.add(new BigDecimal(sSalida[10]).negate());

				// acumulado.add( new BigDecimal(sSalida[9]).negate() );
				sSalida[totCampos + 1] = acumulado.toString();
				vecSalida.add(sSalida);
			}
		} catch (SQLException sqlException) {
			log.error("Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception: " + ex);
		}
		return vecSalida;
	}

	public List getLibroMayorContFinancieraBienesdecambio(
			BigDecimal idcuenta_desde, BigDecimal idcuenta_hasta,
			java.sql.Date fecha_desde, java.sql.Date fecha_hasta,
			BigDecimal anio, BigDecimal idempresa) throws EJBException {
		/**
		 * Utilidad : Metodo que conforma el libro mayor
		 * 
		 * Utilidad : traer todos los centros de costos existentes
		 */
		ResultSet rsSalida = null;
		List vecSalida = new ArrayList();
		try {
			Statement statement = dbconn.createStatement();

			String cQuery = "";
			cQuery += "SELECT * ";
			cQuery += "  FROM vstocksubcontabilidadmayor lm ";
			cQuery += " WHERE lm.parcuenta BETWEEN "
					+ idcuenta_desde.toString() + " AND "
					+ idcuenta_hasta.toString();
			cQuery += "   AND lm.fecha BETWEEN '" + fecha_desde.toString()
					+ "'::DATE AND '" + fecha_hasta.toString() + "'::DATE";
			cQuery += "   AND lm.idempresa = " + idempresa.toString();
			cQuery += " ORDER BY parcuenta, fecha ASC, nroasiento";
			rsSalida = statement.executeQuery(cQuery);
			ResultSetMetaData md = rsSalida.getMetaData();
			BigDecimal acumulado = new BigDecimal(0);
			String cuenta = "";

			while (rsSalida.next()) {
				int totCampos = md.getColumnCount() - 1;
				String[] sSalida = new String[totCampos + 2];
				int i = 0;
				while (i <= totCampos) {
					sSalida[i] = rsSalida.getString(++i);
				}
				if (!cuenta.equals(sSalida[0])) {
					cuenta = sSalida[0];
					acumulado = new BigDecimal(0);
				}
				acumulado = acumulado.add(new BigDecimal(sSalida[9]));
				acumulado = acumulado.add(new BigDecimal(sSalida[10]).negate());

				// acumulado.add( new BigDecimal(sSalida[9]).negate() );
				sSalida[totCampos + 1] = acumulado.toString();
				vecSalida.add(sSalida);
			}
		} catch (SQLException sqlException) {
			log.error("Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception: " + ex);
		}
		return vecSalida;
	}

	// 20111012 - CNI - EJV - Comprobantes especiales -->

	public List getMiniPlanCuentasPK(int idEjercicio, Long pk,
			BigDecimal idempresa) throws EJBException {
		/*
		 * Entidad: Plan de Cuentas @ejb.interface-method view-type = "remote"
		 * 
		 * @throws SQLException Thrown if method fails due to system-level
		 * error. Utilidad : traer Plan de Cuenta existentes por PK
		 */
		List vecSalida = new ArrayList();

		String cQuery = ""
				+ "SELECT idcuenta,cuenta,inputable,nivel,ajustable,resultado,cent_cost1,cent_cost2,ejercicio, -1 as idtipo,'' as tipo, 0 as importe"
				+ "  FROM contableinfiplan WHERE idCuenta = " + pk.toString()
				+ "   AND idempresa = " + idempresa.toString()
				+ "   AND ejercicio = " + idEjercicio + " ORDER BY idcuenta ";
		try {
			vecSalida = getLista(cQuery);

		} catch (Exception ex) {
			log.error("(EX) getMiniPlanCuentasPK(): " + ex);

		}
		return vecSalida;
	}

	// <--

	/**
	 * Metodos para la entidad: vClientesSubContabilidad Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Thu May 31 10:18:14 ART 2012
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getVClientesSubContabilidadAll(int ejercicio,
			String fechadesde, String fechahasta, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT fechamov, cartera, nrointerno as idasiento, tipomov, nrocuenta, cuenta, importe, cencosto, subcencosto, detalle, fechav, ejercicio, idempresa "
				+ "   FROM vclientessubcontabilidad " + " WHERE ejercicio = "
				+ ejercicio + " AND fechamov BETWEEN TO_DATE('" + fechadesde
				+ "', 'dd/mm/yyyy') AND TO_DATE('" + fechahasta
				+ "', 'dd/mm/yyyy') AND idempresa = " + idempresa.toString()
				+ "  ORDER BY 3, 4;";
		List vecSalida = new ArrayList();
		try {

			vecSalida = getLista(cQuery);

		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getVClientesSubContabilidadAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// 20130807 - EJV -->

	public List getMiniPlanCuentasPlantilla(int idEjercicio,
			BigDecimal idplantilla, BigDecimal idempresa) throws EJBException {
		List vecSalida = new ArrayList();

		String cQuery = ""
				+ "SELECT ip.idcuenta, ip.cuenta, ip.inputable, ip.nivel, ip.ajustable, ip.resultado, ip.cent_cost1, ip.cent_cost2, "
				+ "              ip.ejercicio, tip.idtipo, tip.tipo, 0 as importe "
				+ "   FROM contableinfiplan ip "
				+ "              INNER JOIN proveedoplantilladocesp_deta ppd ON ip.idcuenta = ppd.idcuenta AND ip.idempresa = ppd.idempresa "
				+ "              INNER JOIN proveedoplantilladocesp ppc ON ppd.idplantilla = ppc.idplantilla AND ppd.idempresa = ppc.idempresa AND ppc.activa = 'S' "
				+ "              INNER JOIN contabletipificacion tip ON ppd.idtipo = tip.idtipo AND tip.mostrar = 'S' "
				+ "  WHERE ppd.idplantilla = " + idplantilla.toString()
				+ "       AND ppd.idempresa = " + idempresa.toString()
				+ "       AND ip.ejercicio = " + idEjercicio
				+ "  ORDER BY ppd.idcuenta; ";

		try {

			vecSalida = getLista(cQuery);

		} catch (Exception ex) {
			log.error("(EX) getMiniPlanCuentasPlantilla() : " + ex);

		}

		return vecSalida;
	}

	// <--

	
}
