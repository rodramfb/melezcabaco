package ar.com.syswarp.ejb;

import java.awt.Component;
import java.awt.geom.CubicCurve2D;
import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

@Stateless
public class TesoreriaBean implements Tesoreria {
	/** The session context */
	private SessionContext context;

	/* conexion a la base de datos */
	private Connection dbconn;

	static Logger log = Logger.getLogger(TesoreriaBean.class);

	private Connection conexion;

	private Properties props;

	private String url;

	private String clase;

	private String usuario;

	private String clave;

	public Long idayuda;

	public String referencia;

	public String ayuda;

	public String imagen;

	public TesoreriaBean() {
		super();
		try {
			props = new Properties();
			props.load(TesoreriaBean.class
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

	public TesoreriaBean(Connection dbconn) {
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

	private static List getLista(String query, Connection conn) {
		List vecSalida = new ArrayList();
		try {
			Statement statement = conn.createStatement();
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

	// ---------------------------------------------------------------------------------------------------------------
	// CLEARING
	// para todo (ordena por el segundo campo por defecto)
	public List getCajaclearingAll(long limit, long offset, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT idclearing,horas_cl,dias_cl,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM CAJACLEARING WHERE idempresa=" + idempresa.toString()
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getCajaclearingOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT  idclearing,horas_cl,dias_cl,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM CAJACLEARING" + " where idempresa= "
				+ idempresa.toString() + " and (idclearing::VARCHAR LIKE '%"
				+ ocurrencia + "%' OR " + " (horas_cl::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase() + "%') " + " ORDER BY 2";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public Hashtable getCajaDiasClearingAll(BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idclearing, dias_cl FROM CAJACLEARING WHERE idempresa="
				+ idempresa.toString() + " ORDER BY 2  ;";
		Hashtable htSalida = new Hashtable();
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			while (rsSalida.next()) {
				htSalida.put(rsSalida.getString(1), rsSalida.getString(2));
			}

		} catch (SQLException sqlException) {
			log.error("Error SQL en el metodo : getCajaDiasClearingAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCajaDiasClearingAll()  "
							+ ex);
		}
		return htSalida;
	}

	public List getCajaclearingPK(BigDecimal idclearing, BigDecimal idempresa)
			throws EJBException {

		String cQuery = ""
				+ "SELECT idclearing,horas_cl,dias_cl,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM CAJACLEARING WHERE idclearing="
				+ idclearing.toString() + " AND idempresa="
				+ idempresa.toString();

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String CajaclearingDelete(BigDecimal idclearing, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM CAJACLEARING WHERE idclearing="
				+ idclearing.toString() + " AND idempresa="
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM CAJACLEARING WHERE idclearing="
						+ idclearing.toString() + " AND idempresa="
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : CajaclearingDelete( BigDecimal idclearing, BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: CajaclearingDelete( BigDecimal idclearing, BigDecimal idempresa )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String CajaclearingCreate(String horas_cl, BigDecimal dias_cl,
			String usuarioalt, BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";
		if (horas_cl.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar sin datos (nulo) el campo: Horas ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO CAJACLEARING(horas_cl, dias_cl, usuarioalt, idempresa ) VALUES (?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, horas_cl);
				insert.setBigDecimal(2, dias_cl);
				insert.setString(3, usuarioalt);
				insert.setBigDecimal(4, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String CajaclearingCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error excepcion public String CajaclearingCreate(.....)"
					+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String CajaclearingCreateOrUpdate(BigDecimal idclearing,
			String horas_cl, BigDecimal dias_cl, String usuarioact,
			BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idclearing == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idclearing ";

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM Cajaclearing WHERE idclearing = "
					+ idclearing.toString()
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
					sql = "UPDATE CAJACLEARING SET horas_cl=?, dias_cl=?, usuarioact=?, fechaact=? WHERE idclearing=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, horas_cl);
					insert.setBigDecimal(2, dias_cl);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, idclearing);
					insert.setBigDecimal(6, idempresa);
				} else {
					String ins = "INSERT INTO CAJACLEARING(horas_cl, dias_cl, usuarioalt, idempresa ) VALUES (?, ?, ?, ? )";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, horas_cl);
					insert.setBigDecimal(2, dias_cl);
					insert.setString(3, usuarioalt);
					insert.setBigDecimal(4, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String CajaclearingCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String CajaclearingCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String CajaclearingUpdate(BigDecimal idclearing, String horas_cl,
			BigDecimal dias_cl, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idclearing == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idclearing ";

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM Cajaclearing WHERE idclearing = "
					+ idclearing.toString()
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
					sql = "UPDATE CAJACLEARING SET horas_cl=?, dias_cl=?, usuarioact=?, fechaact=? WHERE idclearing=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, horas_cl);
					insert.setBigDecimal(2, dias_cl);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, idclearing);
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
			log.error("Error SQL public String CajaclearingUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log.error("Error excepcion public String CajaclearingUpdate(.....)"
					+ ex);
		}
		return salida;
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

	// FERIADOS
	// para todo (ordena por el segundo campo por defecto)
	public List getCajaferiadosAll(long limit, long offset, BigDecimal idempresa)
			throws EJBException {

		String cQuery = ""
				+ "SELECT idferiado,feriado,fecha_fer,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM CAJAFERIADOS WHERE idempresa=" + idempresa.toString()
				+ "ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getCajaferiadosOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idferiado,feriado,fecha_fer,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM CAJAFERIADOS " + " where idempresa= "
				+ idempresa.toString() + " and (idferiado::VARCHAR LIKE '%"
				+ ocurrencia + "%' OR " + " UPPER(feriado) LIKE '%"
				+ ocurrencia.toUpperCase() + "%') " + " ORDER BY 2";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// Retorna Hashtable conteniendo los feriados como claves.

	public Hashtable getCajaFeriadosFecha(BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT fecha_fer FROM CAJAFERIADOS WHERE idempresa="
				+ idempresa.toString() + "ORDER BY 1  ;";
		Hashtable htSalida = new Hashtable();
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);

			if (rsSalida != null) {
				while (rsSalida.next()) {
					htSalida.put(rsSalida.getString(1), "");
				}
			} else
				log
						.warn("getCajaFeriadosFecha(): Imposible recuperar feriados.");

		} catch (SQLException sqlException) {
			log.error("Error SQL en el metodo : getCajaferiados() "
					+ sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception: en el metodo: getCajaferiados()  "
					+ ex);
		}
		return htSalida;
	}

	// por primary key (primer campo por defecto)

	public List getCajaferiadosPK(BigDecimal idferiado, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT idferiado,feriado,fecha_fer,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM CAJAFERIADOS " + " WHERE idferiado="
				+ idferiado.toString() + " AND idempresa="
				+ idempresa.toString();

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String CajaferiadosDelete(BigDecimal idferiado, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM CAJAFERIADOS WHERE idferiado="
				+ idferiado.toString() + " AND idempresa="
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM CAJAFERIADOS WHERE idferiado="
						+ idferiado.toString() + " AND idempresa="
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : CajaferiadosDelete( BigDecimal idferiado, BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: CajaferiadosDelete( BigDecimal idferiado, BigDecimal idempresa )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String CajaferiadosCreate(String feriado, java.sql.Date fecha_fer,
			String usuarioalt, BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (feriado == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: Feriado ";
		if (fecha_fer == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fecha_fer ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (feriado.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Feriado ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO CAJAFERIADOS(feriado, fecha_fer, usuarioalt, idempresa ) VALUES (?, ?, ?, ? )";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, feriado);
				insert.setDate(2, fecha_fer);
				insert.setString(3, usuarioalt);
				insert.setBigDecimal(4, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String CajaferiadosCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error excepcion public String CajaferiadosCreate(.....)"
					+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String CajaferiadosCreateOrUpdate(BigDecimal idferiado,
			String feriado, java.sql.Date fecha_fer, String usuarioact,
			BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idferiado == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idferiado ";
		if (feriado == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: Feriado ";
		if (fecha_fer == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: Fecha_fer ";

		// 2. sin nada desde la pagina
		if (feriado.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Feriado ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM Cajaferiados WHERE idferiado = "
					+ idferiado.toString()
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
					sql = "UPDATE CAJAFERIADOS SET feriado=?, fecha_fer=?, usuarioact=?, fechaact=? WHERE idferiado=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, feriado);
					insert.setDate(2, fecha_fer);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, idferiado);
					insert.setBigDecimal(6, idempresa);
				} else {
					String ins = "INSERT INTO CAJAFERIADOS(feriado, fecha_fer, usuarioalt, idempresa ) VALUES (?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, feriado);
					insert.setDate(2, fecha_fer);
					insert.setString(3, usuarioalt);
					insert.setBigDecimal(4, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String CajaferiadosCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String CajaferiadosCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String CajaferiadosUpdate(BigDecimal idferiado, String feriado,
			java.sql.Date fecha_fer, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idferiado == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idferiado ";
		if (feriado == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: Feriado ";
		if (fecha_fer == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fecha_fer ";

		// 2. sin nada desde la pagina
		if (feriado.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Feriado ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM Cajaferiados WHERE idferiado = "
					+ idferiado.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE CAJAFERIADOS SET feriado=?, fecha_fer=?, usuarioact=?, fechaact=? WHERE idferiado=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, feriado);
					insert.setDate(2, fecha_fer);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, idferiado);
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
			log.error("Error SQL public String CajaferiadosUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log.error("Error excepcion public String CajaferiadosUpdate(.....)"
					+ ex);
		}
		return salida;
	}

	// TIPO IDENTIFICADORES
	// para todo (ordena por el segundo campo por defecto)
	public List getCajatipoidentificadoresAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT idtipoidentificador,tipoidentificador,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM CAJATIPOIDENTIFICADORES WHERE idempresa="
				+ idempresa.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getCajatipoidentificadoresOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT idtipoidentificador,tipoidentificador,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM CAJATIPOIDENTIFICADORES " + " where idempresa= "
				+ idempresa.toString()
				+ " and (idtipoidentificador::VARCHAR LIKE '%" + ocurrencia
				+ "%' OR " + " UPPER(tipoidentificador) LIKE '%"
				+ ocurrencia.toUpperCase() + "%') " + " ORDER BY 2";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getCajatipoidentificadoresPK(BigDecimal idtipoidentificador,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT  idtipoidentificador,tipoidentificador,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM CAJATIPOIDENTIFICADORES "
				+ " WHERE idtipoidentificador="
				+ idtipoidentificador.toString() + " AND idempresa="
				+ idempresa.toString();
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String CajatipoidentificadoresDelete(BigDecimal idtipoidentificador,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM CAJATIPOIDENTIFICADORES WHERE idtipoidentificador="
				+ idtipoidentificador.toString()
				+ " AND idempresa="
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM CAJATIPOIDENTIFICADORES WHERE idtipoidentificador="
						+ idtipoidentificador.toString()
						+ " AND idempresa="
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : CajatipoidentificadoresDelete( BigDecimal idtipoidentificador, BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: CajatipoidentificadoresDelete( BigDecimal idtipoidentificador, BigDecimal idempresa )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String CajatipoidentificadoresCreate(String tipoidentificador,
			String usuarioalt, BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (tipoidentificador == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipoidentificador ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (tipoidentificador.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipoidentificador ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO CAJATIPOIDENTIFICADORES(tipoidentificador, usuarioalt, idempresa ) VALUES (?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, tipoidentificador);
				insert.setString(2, usuarioalt);
				insert.setBigDecimal(3, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String CajatipoidentificadoresCreate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String CajatipoidentificadoresCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String CajatipoidentificadoresCreateOrUpdate(
			BigDecimal idtipoidentificador, String tipoidentificador,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idtipoidentificador == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipoidentificador ";
		if (tipoidentificador == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipoidentificador ";

		// 2. sin nada desde la pagina
		if (tipoidentificador.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipoidentificador ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM Cajatipoidentificadores WHERE idtipoidentificador = "
					+ idtipoidentificador.toString()
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
					sql = "UPDATE CAJATIPOIDENTIFICADORES SET tipoidentificador=?, usuarioact=?, fechaact=? WHERE idtipoidentificador=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, tipoidentificador);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idtipoidentificador);
					insert.setBigDecimal(5, idempresa);
				} else {
					String ins = "INSERT INTO CAJATIPOIDENTIFICADORES(tipoidentificador, usuarioalt, idempresa ) VALUES (?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, tipoidentificador);
					insert.setString(2, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String CajatipoidentificadoresCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String CajatipoidentificadoresCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String CajatipoidentificadoresUpdate(BigDecimal idtipoidentificador,
			String tipoidentificador, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idtipoidentificador == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipoidentificador ";
		if (tipoidentificador == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipoidentificador ";

		// 2. sin nada desde la pagina
		if (tipoidentificador.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipoidentificador ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM Cajatipoidentificadores WHERE idtipoidentificador = "
					+ idtipoidentificador.toString()
					+ " idempresa="
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
					sql = "UPDATE CAJATIPOIDENTIFICADORES SET tipoidentificador=?, usuarioact=?, fechaact=? WHERE idtipoidentificador=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, tipoidentificador);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idtipoidentificador);
					insert.setBigDecimal(5, idempresa);
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
					.error("Error SQL public String CajatipoidentificadoresUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String CajatipoidentificadoresUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// FLUJO DE FONDOS
	// para todo (ordena por el segundo campo por defecto)
	public List getCajaflujosAll(long limit, long offset, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT codigo_fl,descri_fl,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM CAJAFLUJOS WHERE idempresa=" + idempresa.toString()
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getCajaflujosOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws EJBException {

		String cQuery = ""
				+ "SELECT codigo_fl,descri_fl,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM CAJAFLUJOS " + " where idempresa= "
				+ idempresa.toString() + " and (codigo_fl::VARCHAR LIKE '%"
				+ ocurrencia + "%' OR " + " UPPER(descri_fl) LIKE '%"
				+ ocurrencia.toUpperCase() + "%') " + " ORDER BY 2";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getCajaflujosPK(BigDecimal codigo_fl, BigDecimal idempresa)
			throws EJBException {

		String cQuery = ""
				+ "SELECT codigo_fl,descri_fl,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM CAJAFLUJOS WHERE codigo_fl=" + codigo_fl.toString()
				+ " AND idempresa=" + idempresa.toString();

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String CajaflujosDelete(BigDecimal codigo_fl, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM CAJAFLUJOS WHERE codigo_fl="
				+ codigo_fl.toString() + " AND idempresa = "
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM CAJAFLUJOS WHERE codigo_fl="
						+ codigo_fl.toString() + " AND idempresa="
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : CajaflujosDelete( BigDecimal codigo_fl, BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: CajaflujosDelete( BigDecimal codigo_fl, BigDecimal idempresa )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String CajaflujosCreate(String descri_fl, String usuarioalt,
			BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (descri_fl == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: descri_fl ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (descri_fl.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Descripcion ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO CAJAFLUJOS(descri_fl, usuarioalt, idempresa ) VALUES (?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, descri_fl);
				insert.setString(2, usuarioalt);
				insert.setBigDecimal(3, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String CajaflujosCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error excepcion public String CajaflujosCreate(.....)"
					+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String CajaflujosCreateOrUpdate(BigDecimal codigo_fl,
			String descri_fl, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (codigo_fl == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_fl ";
		if (descri_fl == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: descri_fl ";

		// 2. sin nada desde la pagina
		if (descri_fl.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Descripcion ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM Cajaflujos WHERE codigo_fl = "
					+ codigo_fl.toString()
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
					sql = "UPDATE CAJAFLUJOS SET descri_fl=?, usuarioact=?, fechaact=? WHERE codigo_fl=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, descri_fl);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, codigo_fl);
					insert.setBigDecimal(5, idempresa);
				} else {
					String ins = "INSERT INTO CAJAFLUJOS(descri_fl, usuarioalt, idempresa ) VALUES (?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, descri_fl);
					insert.setString(2, usuarioalt);
					insert.setBigDecimal(3, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String CajaflujosCreateOrUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String CajaflujosCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String CajaflujosUpdate(BigDecimal codigo_fl, String descri_fl,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (codigo_fl == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_fl ";
		if (descri_fl == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: descri_fl ";

		// 2. sin nada desde la pagina
		if (descri_fl.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Descripcion ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM Cajaflujos WHERE codigo_fl = "
					+ codigo_fl.toString()
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
					sql = "UPDATE CAJAFLUJOS SET descri_fl=?, usuarioact=?, fechaact=? WHERE codigo_fl=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, descri_fl);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, codigo_fl);
					insert.setBigDecimal(5, idempresa);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String CajaflujosUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log.error("Error excepcion public String CajaflujosUpdate(.....)"
					+ ex);
		}
		return salida;
	}

	/**
	 * Metodos para la entidad: vlov_Clientes Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Tue Dec 12 14:43:41 GMT-03:00 2006
	 * 
	 */
	// para todo (ordena por el segundo campo por defecto)
	public List getVlov_ClientesAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {

		String cQuery = "" + "SELECT idcliente,razon,idcobrador,cobrador,"
				+ "       usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM VLOV_CLIENTES WHERE idempresa = "
				+ idempresa.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getVlov_ClientesOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws EJBException {

		String cQuery = ""
				+ "SELECT idcliente, razon, idcobrador, cobrador, usuarioalt,usuarioact,fechaalt,fechaact"
				+ "  FROM VLOV_CLIENTES " + " where idempresa= "
				+ idempresa.toString() + " and (idcliente::VARCHAR LIKE '%"
				+ ocurrencia + "%' OR " + " UPPER(razon) LIKE '%"
				+ ocurrencia.toUpperCase() + "%') " + " ORDER BY 2";
		List vecSalida = getLista(cQuery);
		return vecSalida;

	}

	/**
	 * Metodos para la entidad: clientesCobradores Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Tue Dec 12 15:32:43 GMT-03:00 2006
	 * 
	 */
	// para todo (ordena por el segundo campo por defecto)
	public List getLovClientesCobradoresAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT idcobrador,cobrador,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM CLIENTESCOBRADORES WHERE idempresa="
				+ idempresa.toString() + "ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getLovClientesCobradoresOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {

		String cQuery = ""
				+ "SELECT idcobrador,cobrador,usuarioalt,usuarioact,fechaalt,fechaact"
				+ "  FROM CLIENTESCOBRADORES " + " where idempresa= "
				+ idempresa.toString() + " and (idcobrador::VARCHAR LIKE '%"
				+ ocurrencia + "%' OR " + " UPPER(cobrador) LIKE '%"
				+ ocurrencia.toUpperCase() + "%') " + " ORDER BY 2";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: clientesMovCli Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Wed Dec 13 10:54:31 GMT-03:00 2006
	 * 
	 * 
	 */
	// para todo (ordena por el segundo campo por defecto)
	public List getLovClientesMovCliAll(long limit, long offset,
			BigDecimal idcliente, BigDecimal idempresa) throws EJBException {

		String cQuery = ""
				+ "SELECT mc.cliente,mc.fechamov,mc.sucursal,mc.comprob,mc.comprob_has,mc.tipomov,"
				+ "       mc.tipomovs,mc.saldo,mc.importe,mc.cambio,mc.moneda,mc.unamode,mc.tipocomp,"
				// + " tc.descri_tc ||
				// mc.tipomovs,mc.saldo,mc.importe,mc.cambio,mc.moneda,mc.unamode,mc.tipocomp,"
				+ "       mc.condicion,mc.nrointerno,mc.com_venta,mc.com_cobra,mc.com_vende,mc.anulada,"
				+ "       mc.retoque,mc.expreso,mc.sucucli,mc.remito,mc.credito,"
				+ "       mc.usuarioalt,mc.usuarioact,mc.fechaalt,mc.fechaact "
				+ "  FROM clientesmovcli mc "
				+ "       INNER JOIN  clientestipocomp tc ON mc.tipomov = tc.tipomov_tc AND mc.idempresa = tc.idempresa"
				// 20110621 - EJV -->
				+ "                   AND mc.tipocomp::numeric = tc.idtipocomp "
				// <--
				+ " WHERE mc.cliente =  " + idcliente
				+ "   AND mc.saldo > 0 AND mc.tipomovs <> 'COB' "
				+ "   AND mc.idempresa=" + idempresa.toString()
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getLovClientesMovCliOcu(long limit, long offset,
			String ocurrencia, BigDecimal idcliente, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT mc.cliente,mc.fechamov,mc.sucursal,mc.comprob,mc.comprob_has,mc.tipomov,"
				// + " tc.descri_tc ||
				// mc.tipomovs,mc.saldo,mc.importe,mc.cambio,mc.moneda,mc.unamode,mc.tipocomp,"
				+ "       mc.tipomovs,mc.saldo,mc.importe,mc.cambio,mc.moneda,mc.unamode,mc.tipocomp,"
				+ "       mc.condicion,mc.nrointerno,mc.com_venta,mc.com_cobra,mc.com_vende,mc.anulada,"
				+ "       mc.retoque,mc.expreso,mc.sucucli,mc.remito,mc.credito,"
				+ "       mc.usuarioalt,mc.usuarioact,mc.fechaalt,mc.fechaact "
				+ "  FROM clientesmovcli mc "
				+ "       INNER JOIN  clientestipocomp tc ON mc.tipomov = tc.tipomov_tc AND mc.idempresa = tc.idempresa"
				// 20110621 - EJV -->
				+ "                   AND mc.tipocomp::numeric = tc.idtipocomp "
				// <--
				+ " WHERE (  (mc.comprob::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%'  ) AND mc.cliente = "
				+ idcliente + " AND mc.saldo > 0 AND mc.tipomovs <> 'COB'  "
				+ "   AND (mc.anulada = 'N' OR mc.anulada IS NULL) "
				+ "   AND mc.idempresa=" + idempresa.toString()
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getLovClientesMovCliOne(BigDecimal cliente,
			BigDecimal sucursal, BigDecimal comprob, String tipomovs,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT nrointerno, saldo, importe , fechamov, anulada, remito, saldo as saldocontrol, tipomov, cliente "
				+ "  FROM CLIENTESMOVCLI " + " WHERE cliente="
				+ cliente.toString() + " AND sucursal=" + sucursal
				+ " AND comprob=" + comprob + " AND tipomovs='" + tipomovs
				+ "' AND idempresa=" + idempresa.toString();
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: cajaIdentificadores Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Mon Dec 18 15:14:20 GMT-03:00 2006
	 * 
	 */
	// para todo (ordena por el segundo campo por defecto)
	public List getLovCajaIdentificadoresAll(long limit, long offset,
			String tipomov, String propio, BigDecimal idempresa)
			throws EJBException {

		String cQuery = ""
				+ "SELECT i.ididentificador,ti.tipoidentificador,i.identificador,i.descripcion,"
				+ "       i.cuenta,i.idmoneda,ti.tipomov,ti.propio,i.modcta,i.factura,i.saldo_id,"
				+ "       i.saldo_disp,i.renglones,i.ctacaucion,i.ctatodoc,i.gerencia,i.formula,"
				+ "       i.cuotas,i.presentacion,i.ctacaudoc,i.porcentaje,i.ctadtotar,i.ctatarjeta,"
				+ "       i.comhyper,i.contador,i.afecomicob,i.impri_id,i.subdiventa,i.idcencosto,"
				+ "       i.idcencosto1,i.modicent,i.prox_cheq,i.prox_reserv,i.ulti_cheq,i.modsubcent,"
				+ "       i.res_nro, i.usuarioalt,i.usuarioact,i.fechaalt,i.fechaact "
				+ "  FROM cajaidentificadores i"
				+ "       INNER JOIN cajatipoidentificadores ti ON i.idtipoidentificador = ti.idtipoidentificador AND i.idempresa = ti.idempresa  "
				+ " WHERE UPPER(ti.tipomov) = '" + tipomov.trim().toUpperCase()
				+ "'  AND UPPER(ti.propio) = '" + propio.trim().toUpperCase()
				+ "'  AND i.idempresa=" + idempresa.toString()
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getLovCajaIdentificadoresOcu(long limit, long offset,
			String ocurrencia, String tipomov, String propio,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT i.ididentificador,ti.tipoidentificador,i.identificador,i.descripcion,"
				+ "       i.cuenta,i.idmoneda,ti.tipomov,ti.propio,i.modcta,i.factura,i.saldo_id,"
				+ "       i.saldo_disp,i.renglones,i.ctacaucion,i.ctatodoc,i.gerencia,i.formula,"
				+ "       i.cuotas,i.presentacion,i.ctacaudoc,i.porcentaje,i.ctadtotar,i.ctatarjeta,"
				+ "       i.comhyper,i.contador,i.afecomicob,i.impri_id,i.subdiventa,i.idcencosto,"
				+ "       i.idcencosto1,i.modicent,i.prox_cheq,i.prox_reserv,i.ulti_cheq,i.modsubcent,"
				+ "       i.res_nro,i.usuarioalt,i.usuarioact,i.fechaalt,i.fechaact "
				+ "  FROM cajaidentificadores i"
				+ "       INNER JOIN cajatipoidentificadores ti ON i.idtipoidentificador = ti.idtipoidentificador AND i.idempresa = ti.idempresa "
				+ " WHERE UPPER(ti.tipomov) = '" + tipomov.trim().toUpperCase()
				+ "'  AND UPPER(ti.propio) = '" + propio.trim().toUpperCase()
				+ "'  AND (UPPER(i.descripcion) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(i.identificador) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR (i.ididentificador::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(ti.tipoidentificador) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%') "
				+ " AND i.idempresa = " + idempresa.toString()
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	//

	// para todo (ordena por el segundo campo por defecto)
	public List getLovCajaIdentificadoresConParAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT i.ididentificador,ti.tipoidentificador,i.identificador,i.descripcion,"
				+ "       i.cuenta,i.idmoneda,ti.tipomov,ti.propio,i.modcta,i.factura,i.saldo_id,"
				+ "       i.saldo_disp,i.renglones,i.ctacaucion,i.ctatodoc,i.gerencia,i.formula,"
				+ "       i.cuotas,i.presentacion,i.ctacaudoc,i.porcentaje,i.ctadtotar,i.ctatarjeta,"
				+ "       i.comhyper,i.contador,i.afecomicob,i.impri_id,i.subdiventa,i.idcencosto,"
				+ "       i.idcencosto1,i.modicent,i.prox_cheq,i.prox_reserv,i.ulti_cheq,i.modsubcent,"
				+ "       i.res_nro, CASE WHEN ti.tipomov IN ('S','T','D') THEN 'Z' ELSE 'A' END,"
				+ "       i.usuarioalt,i.usuarioact,i.fechaalt,i.fechaact "
				+ "  FROM cajaidentificadores i"
				+ "       INNER JOIN cajatipoidentificadores ti ON i.idtipoidentificador = ti.idtipoidentificador AND i.idempresa = ti.idempresa"
				+ " WHERE (UPPER(ti.tipomov) IN ('S','O','G')"
				+ "    OR  (ti.tipomov = 'T' AND ti.propio = 'N')"
				+ "    OR  (ti.tipomov = 'D' AND ti.propio = 'N')) AND i.idempresa= "
				+ idempresa.toString() + " ORDER BY 37, 2, 3  LIMIT " + limit
				+ " OFFSET " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getLovCajaIdentificadoresConParOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT i.ididentificador,ti.tipoidentificador,i.identificador,i.descripcion,"
				+ "       i.cuenta,i.idmoneda,ti.tipomov,ti.propio,i.modcta,i.factura,i.saldo_id,"
				+ "       i.saldo_disp,i.renglones,i.ctacaucion,i.ctatodoc,i.gerencia,i.formula,"
				+ "       i.cuotas,i.presentacion,i.ctacaudoc,i.porcentaje,i.ctadtotar,i.ctatarjeta,"
				+ "       i.comhyper,i.contador,i.afecomicob,i.impri_id,i.subdiventa,i.idcencosto,"
				+ "       i.idcencosto1,i.modicent,i.prox_cheq,i.prox_reserv,i.ulti_cheq,i.modsubcent,"
				+ "       i.res_nro, CASE WHEN ti.tipomov IN ('S','T','D') THEN 'Z' ELSE 'A' END,"
				+ "       i.usuarioalt,i.usuarioact,i.fechaalt,i.fechaact "
				+ "  FROM cajaidentificadores i"
				+ "       INNER JOIN cajatipoidentificadores ti ON i.idtipoidentificador = ti.idtipoidentificador  AND i.idempresa = ti.idempresa "
				+ " WHERE ((UPPER(ti.tipomov) IN ('S','O','G')"
				+ "    OR (ti.tipomov = 'T' AND ti.propio = 'N')"
				+ "    OR (ti.tipomov = 'D' AND ti.propio = 'N'))"
				+ "   AND ((UPPER(i.descripcion) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(i.identificador) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%') )) AND i.idempresa="
				+ idempresa.toString() + " ORDER BY 37, 2, 3  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	//
	public List getLovCajaIdentificadoresPropioAll(long limit, long offset,
			String propio, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT i.ididentificador,ti.tipoidentificador,i.identificador,i.descripcion,"
				+ "       i.cuenta,i.idmoneda,ti.tipomov,ti.propio,i.modcta,i.factura,i.saldo_id,"
				+ "       i.saldo_disp,i.renglones,i.ctacaucion,i.ctatodoc,i.gerencia,i.formula,"
				+ "       i.cuotas,i.presentacion,i.ctacaudoc,i.porcentaje,i.ctadtotar,i.ctatarjeta,"
				+ "       i.comhyper,i.contador,i.afecomicob,i.impri_id,i.subdiventa,i.idcencosto,"
				+ "       i.idcencosto1,i.modicent,i.prox_cheq,i.prox_reserv,i.ulti_cheq,i.modsubcent,"
				+ "       i.res_nro, i.usuarioalt,i.usuarioact,i.fechaalt,i.fechaact "
				+ "  FROM cajaidentificadores i"
				+ "       INNER JOIN cajatipoidentificadores ti ON i.idtipoidentificador = ti.idtipoidentificador AND i.idempresa = ti.idempresa "
				+ " WHERE (UPPER(ti.propio) = '" + propio.trim().toUpperCase()
				+ "') AND i.idempresa=" + idempresa.toString()
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	//

	public List getLovCajaIdentificadoresPropioOcu(long limit, long offset,
			String ocurrencia, String propio, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT i.ididentificador,ti.tipoidentificador,i.identificador,i.descripcion,"
				+ "       i.cuenta,i.idmoneda,ti.tipomov,ti.propio,i.modcta,i.factura,i.saldo_id,"
				+ "       i.saldo_disp,i.renglones,i.ctacaucion,i.ctatodoc,i.gerencia,i.formula,"
				+ "       i.cuotas,i.presentacion,i.ctacaudoc,i.porcentaje,i.ctadtotar,i.ctatarjeta,"
				+ "       i.comhyper,i.contador,i.afecomicob,i.impri_id,i.subdiventa,i.idcencosto,"
				+ "       i.idcencosto1,i.modicent,i.prox_cheq,i.prox_reserv,i.ulti_cheq,i.modsubcent,"
				+ "       i.res_nro,i.usuarioalt,i.usuarioact,i.fechaalt,i.fechaact "
				+ "  FROM cajaidentificadores i"
				+ "       INNER JOIN cajatipoidentificadores ti ON i.idtipoidentificador = ti.idtipoidentificador AND i.idempresa = ti.idempresa "
				+ " WHERE (UPPER(ti.propio) = '" + propio.trim().toUpperCase()
				+ "'  AND (UPPER(i.descripcion) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(i.identificador) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%')) AND i.idempresa="
				+ idempresa.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getLovCajaIdentOtrosMovINAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		/**
		 * Recuperar todos los registros correspondientes a identificadores de
		 * ENTRADA de Otros Movimientos de Caja.
		 * 
		 * @DATE:20090731
		 * @AUTOR: ejv
		 * */

		String cQuery = ""

				+ "SELECT i.ididentificador,ti.tipoidentificador,i.identificador,i.descripcion, "
				+ "       i.cuenta,i.idmoneda,ti.tipomov,ti.propio,i.modcta,i.factura,i.saldo_id, "
				+ "       i.saldo_disp,i.renglones,i.ctacaucion,i.ctatodoc,i.gerencia,i.formula, "
				+ "       i.cuotas,i.presentacion,i.ctacaudoc,i.porcentaje,i.ctadtotar,i.ctatarjeta, "
				+ "       i.comhyper,i.contador,i.afecomicob,i.impri_id,i.subdiventa,i.idcencosto, "
				+ "       i.idcencosto1,i.modicent,i.prox_cheq,i.prox_reserv,i.ulti_cheq,i.modsubcent, "
				+ "       i.res_nro, i.usuarioalt,i.usuarioact,i.fechaalt,i.fechaact "
				+ "  FROM cajaidentificadores i "
				+ "       INNER JOIN cajatipoidentificadores ti ON i.idtipoidentificador = ti.idtipoidentificador AND i.idempresa = ti.idempresa "
				+ " WHERE ti.idtipoidentificador NOT IN( (SELECT idtipoidentificador FROM cajatipoidentificadores WHERE tipomov = 'T' OR (tipomov = 'D' AND propio = 'S') )) "
				+ "   AND i.idempresa= " + idempresa.toString()
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET " + offset + ";";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	//

	public List getLovCajaIdentOtrosMovINOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		/**
		 * Recuperar todos los registros correspondientes a identificadores de
		 * ENTRADA de Otros Movimientos de Caja por seg�n un criterio de
		 * b�squeda.
		 * 
		 * @DATE:20090731
		 * @AUTOR: ejv
		 * */

		String cQuery = ""
				+ "SELECT i.ididentificador,ti.tipoidentificador,i.identificador,i.descripcion, "
				+ "       i.cuenta,i.idmoneda,ti.tipomov,ti.propio,i.modcta,i.factura,i.saldo_id, "
				+ "       i.saldo_disp,i.renglones,i.ctacaucion,i.ctatodoc,i.gerencia,i.formula, "
				+ "       i.cuotas,i.presentacion,i.ctacaudoc,i.porcentaje,i.ctadtotar,i.ctatarjeta, "
				+ "       i.comhyper,i.contador,i.afecomicob,i.impri_id,i.subdiventa,i.idcencosto, "
				+ "       i.idcencosto1,i.modicent,i.prox_cheq,i.prox_reserv,i.ulti_cheq,i.modsubcent, "
				+ "       i.res_nro, i.usuarioalt,i.usuarioact,i.fechaalt,i.fechaact "
				+ "  FROM cajaidentificadores i "
				+ "       INNER JOIN cajatipoidentificadores ti ON i.idtipoidentificador = ti.idtipoidentificador AND i.idempresa = ti.idempresa "
				+ " WHERE (ti.idtipoidentificador NOT IN( (SELECT idtipoidentificador FROM cajatipoidentificadores WHERE tipomov = 'T' OR (tipomov = 'D' AND propio = 'S') )) "
				+ "   AND (UPPER(i.descripcion) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(i.identificador) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%')) AND i.idempresa="
				+ idempresa.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para todo (ordena por el segundo campo por defecto)
	public List getLovCajaIdentOtrosMovOUTAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {

		/**
		 * Recuperar todos los registros correspondientes a identificadores de
		 * SALIDA de Otros Movimientos de Caja por seg�n un criterio de
		 * b�squeda.
		 * 
		 * @DATE:20090731
		 * @AUTOR: ejv
		 * */

		// TODO:20090807 eliminar filtro_TODO, momentaneamente para terminar
		// el desarrollo mas rapido, tiene que ver con documenteos
		// caucionados, los que actualmente tienen una inexistente
		// relevancia en el manejo de caja.
		String filtro_TODO = " AND (ti.idtipoidentificador NOT IN (SELECT idtipoidentificador FROM cajatipoidentificadores WHERE tipomov = 'D' AND propio = 'N')) ";

		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT i.ididentificador,ti.tipoidentificador,i.identificador,i.descripcion,"
				+ "       i.cuenta,i.idmoneda,ti.tipomov,ti.propio,i.modcta,i.factura,i.saldo_id,"
				+ "       i.saldo_disp,i.renglones,i.ctacaucion,i.ctatodoc,i.gerencia,i.formula,"
				+ "       i.cuotas,i.presentacion,i.ctacaudoc,i.porcentaje,i.ctadtotar,i.ctatarjeta,"
				+ "       i.comhyper,i.contador,i.afecomicob,i.impri_id,i.subdiventa,i.idcencosto,"
				+ "       i.idcencosto1,i.modicent,i.prox_cheq,i.prox_reserv,i.ulti_cheq,i.modsubcent,"
				+ "       i.res_nro, CASE WHEN ti.tipomov IN ('S','T','D') THEN 'Z' ELSE 'A' END,"
				+ "       i.usuarioalt,i.usuarioact,i.fechaalt,i.fechaact "
				+ "  FROM cajaidentificadores i"
				+ "       INNER JOIN cajatipoidentificadores ti ON i.idtipoidentificador = ti.idtipoidentificador AND i.idempresa = ti.idempresa"
				+ " WHERE ti.tipomov <>  'T' "
				// TODO: 20090807 -- Eliminar
				+ filtro_TODO + "   AND i.idempresa= " + idempresa.toString()
				+ " ORDER BY 37, 2, 3  LIMIT " + limit + " OFFSET " + offset
				+ ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getLovCajaIdentOtrosMovOUTOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		/**
		 * Recuperar todos los registros correspondientes a identificadores de
		 * SALIDA de Otros Movimientos de Caja por seg�n un criterio de
		 * b�squeda.
		 * 
		 * @DATE:20090731
		 * @AUTOR: ejv
		 * */

		// TODO:20090807 eliminar filtro_TODO, momentaneamente para terminar
		// el desarrollo mas rapido, tiene que ver con documenteos
		// caucionados, los que actualmente tienen una inexistente
		// relevancia en el manejo de caja.
		String filtro_TODO = " AND (ti.idtipoidentificador NOT IN (SELECT idtipoidentificador FROM cajatipoidentificadores WHERE tipomov = 'D' AND propio = 'N')) ";

		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT i.ididentificador,ti.tipoidentificador,i.identificador,i.descripcion,"
				+ "       i.cuenta,i.idmoneda,ti.tipomov,ti.propio,i.modcta,i.factura,i.saldo_id,"
				+ "       i.saldo_disp,i.renglones,i.ctacaucion,i.ctatodoc,i.gerencia,i.formula,"
				+ "       i.cuotas,i.presentacion,i.ctacaudoc,i.porcentaje,i.ctadtotar,i.ctatarjeta,"
				+ "       i.comhyper,i.contador,i.afecomicob,i.impri_id,i.subdiventa,i.idcencosto,"
				+ "       i.idcencosto1,i.modicent,i.prox_cheq,i.prox_reserv,i.ulti_cheq,i.modsubcent,"
				+ "       i.res_nro, CASE WHEN ti.tipomov IN ('S','T','D') THEN 'Z' ELSE 'A' END,"
				+ "       i.usuarioalt,i.usuarioact,i.fechaalt,i.fechaact "
				+ "  FROM cajaidentificadores i"
				+ "       INNER JOIN cajatipoidentificadores ti ON i.idtipoidentificador = ti.idtipoidentificador  AND i.idempresa = ti.idempresa "
				+ " WHERE ti.tipomov <>  'T' "
				// TODO: 20090807 -- ELiminar
				+ filtro_TODO + "   AND ((UPPER(i.descripcion) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(i.identificador) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%') )) AND i.idempresa="
				+ idempresa.toString() + " ORDER BY 37, 2, 3  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;

	}

	//
	public List getLovCajaMovimientosCancelarAll(long limit, long offset,
			String cartera_mt, String tipcart_mt, Timestamp fecha_mt,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT mt.idmovteso, mt.fecha_mt, mt.detalle_mt, mt.importe_mt::numeric(18,2), mt.nrodoc_mt "
				+ "  FROM cajamovteso mt  " + " WHERE mt.usado_mt IS NULL "
				+ "   AND mt.cartera_mt = '" + cartera_mt + "' "
				+ "   AND mt.tipcart_mt = '" + tipcart_mt + "' "
				+ "   AND mt.fecha_mt::DATE <= '"
				+ fecha_mt
				+ "'::DATE " // TODO: verificar
				// contra que fecha
				+ "   AND idempresa=" + idempresa + " ORDER BY 2  LIMIT "
				+ limit + " OFFSET " + offset + ";";
		List vecSalida = getLista(cQuery);

		return vecSalida;
	}

	//

	public List getLovCajaMovimientosCancelarOcu(long limit, long offset,
			String ocurrencia, String cartera_mt, String tipcart_mt,
			Timestamp fecha_mt, BigDecimal idempresa) throws EJBException {
		// TODO: verificar fecha_mt
		String cQuery = ""
				+ "SELECT mt.idmovteso, mt.fecha_mt, mt.detalle_mt, mt.importe_mt::numeric(18,2), mt.nrodoc_mt "
				+ "  FROM cajamovteso mt  " + " WHERE mt.usado_mt IS NULL "
				+ "   AND mt.cartera_mt = '" + cartera_mt + "' "
				+ "   AND mt.tipcart_mt =  '" + tipcart_mt + "' "
				+ "   AND mt.fecha_mt::DATE <=  '" + fecha_mt + "'::DATE "
				+ " AND  ( UPPER(mt.detalle_mt) LIKE '%"
				+ ocurrencia.toUpperCase() + "%' "
				+ " OR (mt.nrodoc_mt::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase() + "%' ) AND idempresa= " + idempresa
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	//

	public List getLovCajaIdentificadoresOne(String identificador,
			BigDecimal idempresa) throws EJBException {
		// id / descripcion / cuenta cont / moneda / tipomov / propio
		String cQuery = ""
				+ "SELECT i.ididentificador,ti.tipoidentificador,i.identificador,i.descripcion,"
				+ "       i.cuenta,i.idmoneda,ti.tipomov,ti.propio, 0 as importe, ctacaucion, ctatodoc "
				+ "  FROM cajaidentificadores i"
				+ "       INNER JOIN cajatipoidentificadores ti ON i.idtipoidentificador = ti.idtipoidentificador AND i.idempresa = ti.idempresa "
				+ " WHERE i.identificador='"
				+ identificador.trim().toUpperCase() + "' AND i.idempresa="
				+ idempresa.toString();
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// 20090803 EJV

	public List getLovCajaDocTercerosCancAll(long limit, long offset,
			String usado_mt, String cartera_mt, String tipcart_mt,
			Timestamp fecha_mt, BigDecimal idempresa) throws EJBException {

		String cQuery = ""
				+ "SELECT mt.idmovteso, mt.fecha_mt, mt.detalle_mt, mt.importe_mt::numeric(18,2), mt.nrodoc_mt, pr.idproveedor, pr.razon_social "
				+ "  FROM cajamovteso mt  "
				+ "       LEFT JOIN proveedoproveed pr ON mt.idcliente = pr.idproveedor AND mt.idempresa = pr.idempresa "
				+ " WHERE mt.usado_mt = '" + usado_mt + "' "
				+ "   AND mt.cartera_mt = '" + cartera_mt + "'  "
				+ "   AND mt.tipcart_mt = '" + tipcart_mt + "' "
				+ "   AND mt.fecha_mt::DATE <= '"
				+ fecha_mt
				+ "'::DATE " // TODO: verificar
				// contra que fecha
				+ "   AND mt.idempresa=" + idempresa
				+ " ORDER BY 6, 2   LIMIT " + limit + " OFFSET " + offset + ";";
		List vecSalida = getLista(cQuery);

		return vecSalida;
	}

	//

	public List getLovCajaDocTercerosCancOcu(long limit, long offset,
			String ocurrencia, String usado_mt, String cartera_mt,
			String tipcart_mt, Timestamp fecha_mt, BigDecimal idempresa)
			throws EJBException {
		// TODO: verificar fecha_mt
		String cQuery = ""
				+ "SELECT mt.idmovteso, mt.fecha_mt, mt.detalle_mt, mt.importe_mt::numeric(18,2), mt.nrodoc_mt, pr.idproveedor, pr.razon_social "
				+ "  FROM cajamovteso mt"
				+ "       LEFT JOIN proveedoproveed pr ON mt.idcliente = pr.idproveedor AND mt.idempresa = pr.idempresa "
				+ " WHERE mt.usado_mt = '"
				+ usado_mt
				+ "' "
				+ "   AND mt.cartera_mt = '"
				+ cartera_mt
				+ "' "
				+ "   AND mt.tipcart_mt = '"
				+ tipcart_mt
				+ "' "
				+ "   AND mt.fecha_mt::DATE <=  '"
				+ fecha_mt
				+ "'::DATE "
				+ " AND  ( UPPER(mt.detalle_mt) LIKE '%"
				+ ocurrencia.toUpperCase()
				+ "%' "
				+ " OR (mt.nrodoc_mt::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase()
				+ "%' ) AND mt.idempresa="
				+ idempresa
				+ " ORDER BY 6, 2  LIMIT "
				+ limit
				+ " OFFSET  "
				+ offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getLovCajaIdentificadoresPropioOne(String identificador,
			BigDecimal ejercicio, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		// id / descripcion / cuenta cont / moneda / tipomov / propio
		String cQuery = ""
				+ "SELECT i.ididentificador,ti.tipoidentificador,i.identificador,i.descripcion,"
				+ "       i.cuenta, i.idmoneda, ti.tipomov, ti.propio, "
				+ "       i.modcta, i.factura, i.saldo_id, i.contador,"
				+ "       i.ctacaudoc,i.saldo_disp, i.ctacaucion, i.ctatodoc,  "
				+ "       i.cuotas,i.ctadtotar, i.ctatarjeta, i.subdiventa, "
				+ "       i.idcencosto, i.idcencosto1, i.modicent, i.modsubcent, "
				+ "       i.prox_cheq, i.prox_reserv,i.ulti_cheq,  i.res_nro, "
				+ "       0.0 as importe, '' as detalle, '' as chequenro, '' as fecha, "
				+ "       0 as clearing, 1 as cuotaspaga, "
				+ "       COALESCE(c.cuenta, 'CUENTA INVALIDA PARA EL EJERCICIO') AS cuenta"
				+ "  FROM cajaidentificadores i"
				+ "       INNER JOIN cajatipoidentificadores ti ON i.idtipoidentificador = ti.idtipoidentificador AND i.idempresa = ti.idempresa "
				+ "       LEFT  JOIN contableinfiplan c ON i.cuenta = c.idcuenta AND i.idempresa=c.idempresa  "
				+ "              AND c.ejercicio=" + ejercicio.toString()

				+ " WHERE i.identificador='"
				+ identificador.trim().toUpperCase() + "'  AND i.idempresa= "
				+ idempresa.toString();

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getLovCajaContraPartidaOne(String identificador,
			BigDecimal ejercicio, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		// id / descripcion / cuenta cont / moneda / tipomov / propio
		String cQuery = ""
				+ "SELECT i.ididentificador,ti.tipoidentificador,i.identificador,i.descripcion,"
				+ "       i.cuenta, i.idmoneda, ti.tipomov, ti.propio, "
				+ "       i.modcta, i.factura, i.saldo_id, i.contador,"
				+ "       i.ctacaudoc,i.saldo_disp, i.ctacaucion, i.ctatodoc,  "
				+ "       i.cuotas,i.ctadtotar, i.ctatarjeta, i.subdiventa, "
				+ "       i.idcencosto, i.idcencosto1, i.modicent, i.modsubcent, "
				+ "       i.prox_cheq, i.prox_reserv,i.ulti_cheq,  i.res_nro, "
				+ "       0.0 as importe, '' as detalle, '' as chequenro, '' as fecha, "
				+ "       0 as clearing, 1 as cuotaspaga, -1 as idmovteso, 0 , "
				+ "       0, COALESCE(c.cuenta, 'CUENTA INVALIDA PARA EL EJERCICIO') AS cuenta "
				+ "  FROM cajaidentificadores i"
				+ "       INNER JOIN cajatipoidentificadores ti ON i.idtipoidentificador = ti.idtipoidentificador AND i.idempresa=ti.idempresa "
				+ "       LEFT  JOIN contableinfiplan c ON i.cuenta = c.idcuenta AND i.idempresa=c.idempresa  "
				+ "              AND c.ejercicio=" + ejercicio.toString()
				+ " WHERE identificador='" + identificador.trim().toUpperCase()
				+ "'  AND i.idempresa=" + idempresa.toString();

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getLovCajaMovimientosCancelarOne(BigDecimal idmovteso,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		// id / descripcion / cuenta cont / moneda / tipomov / propio
		String cQuery = ""

				+ "SELECT i.ididentificador,ti.tipoidentificador,i.identificador,i.descripcion,"
				+ "       i.cuenta, i.idmoneda, ti.tipomov, ti.propio, "
				+ "       i.modcta, i.factura, i.saldo_id, i.contador,"
				+ "       i.ctacaudoc,i.saldo_disp, i.ctacaucion, i.ctatodoc,  "
				+ "       i.cuotas,i.ctadtotar, i.ctatarjeta, i.subdiventa, "
				+ "       i.idcencosto, i.idcencosto1, i.modicent, i.modsubcent, "
				+ "       i.prox_cheq, i.prox_reserv,i.ulti_cheq,  i.res_nro, "
				+ "       mt.importe_mt, mt.detalle_mt , nroint_mt, to_char(fecha_mt, 'dd/mm/yyyy') as fecha, "
				+ "       COALESCE(mt.idclearing, 0) as clearing, 1 as cuotaspaga,  mt.idmovteso, mt.impori_mt::numeric(18,2), "
				+ "       mt.importe_mt::numeric(18,2) as importevalidar, mt.nrodoc_mt, mt.cuenta_mt, i.ctacaucion "
				+ "  FROM cajaidentificadores i"
				+ "       INNER JOIN cajatipoidentificadores ti ON i.idtipoidentificador = ti.idtipoidentificador AND i.idempresa=ti.idempresa"
				+ "       INNER JOIN cajamovteso mt ON mt.cartera_mt = i.identificador "
				+ "              AND mt.idempresa= mt.idempresa AND mt.idempresa=ti.idempresa "
				// TODO: 20080527 - EJV - Junta debil para calcular clearing ...
				// controlar.
				+ "       LEFT JOIN cajaclearing cc ON mt.idclearing = cc.idclearing "
				+ "             AND mt.idempresa = cc.idempresa "
				// TODO: 20080428 - EJV - Controlar, cambio por egresos
				// directos, ENTRADA.
				// + " AND mt.tipcart_mt = ti.tipomov"
				+ " WHERE mt.idmovteso=" + idmovteso.toString()
				+ " AND mt.idempresa=" + idempresa.toString();

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getLovCajaDocIIICancelarOne(BigDecimal idmovteso,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		// id / descripcion / cuenta cont / moneda / tipomov / propio
		String cQuery = ""

				+ "SELECT i.ididentificador,ti.tipoidentificador,i.identificador,i.descripcion,"
				+ "       i.cuenta, i.idmoneda, ti.tipomov, ti.propio, "
				+ "       i.modcta, i.factura, i.saldo_id, i.contador,"
				+ "       i.ctacaudoc,i.saldo_disp, i.ctacaucion, i.ctatodoc,  "
				+ "       i.cuotas,i.ctadtotar, i.ctatarjeta, i.subdiventa, "
				+ "       i.idcencosto, i.idcencosto1, i.modicent, i.modsubcent, "
				+ "       i.prox_cheq, i.prox_reserv,i.ulti_cheq,  i.res_nro, "
				+ "       mt.importe_mt, mt.detalle_mt , nroint_mt, to_char(fecha_mt, 'dd/mm/yyyy') as fecha, "
				+ "       COALESCE(mt.idclearing, 0) as clearing, 1 as cuotaspaga,  mt.idmovteso, mt.impori_mt::numeric(18,2), "
				+ "       mt.importe_mt::numeric(18,2) as importevalidar, mt.nrodoc_mt, mt.cuenta_mt, i.ctacaucion, "
				+ "       pr.idproveedor, pr.razon_social "
				+ "  FROM cajaidentificadores i"
				+ "       INNER JOIN cajatipoidentificadores ti ON i.idtipoidentificador = ti.idtipoidentificador AND i.idempresa=ti.idempresa"
				+ "       INNER JOIN cajamovteso mt ON mt.cartera_mt = i.identificador "
				+ "              AND mt.idempresa= mt.idempresa AND mt.idempresa=ti.idempresa "
				// TODO: 20080527 - EJV - Junta debil para calcular clearing ...
				// controlar.
				+ "       LEFT JOIN cajaclearing cc ON mt.idclearing = cc.idclearing "
				+ "             AND mt.idempresa = cc.idempresa "

				// 20090811 - EJV - Junta debil con proveedores
				+ "       LEFT JOIN proveedoproveed pr ON mt.idcliente = pr.idproveedor "
				+ "             AND mt.idempresa = pr.idempresa "

				// TODO: 20080428 - EJV - Controlar, cambio por egresos
				// directos, ENTRADA.
				// + " AND mt.tipcart_mt = ti.tipomov"
				+ " WHERE mt.idmovteso=" + idmovteso.toString()
				+ " AND mt.idempresa=" + idempresa.toString();

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getRetencionesProveedor(BigDecimal idproveedor,
			BigDecimal ejercicio, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		// id / descripcion / cuenta cont / moneda / tipomov / propio
		String cQuery = ""
				+ "SELECT i.ididentificador,ti.tipoidentificador,i.identificador,i.descripcion, "
				+ "       i.cuenta, i.idmoneda, ti.tipomov, ti.propio,  "
				+ "       i.modcta, i.factura, i.saldo_id, i.contador, "
				+ "       i.ctacaudoc,i.saldo_disp, i.ctacaucion, i.ctatodoc,   "
				+ "       i.cuotas,i.ctadtotar, i.ctatarjeta, i.subdiventa,  "
				+ "       i.idcencosto, i.idcencosto1, i.modicent, i.modsubcent,  "
				+ "       i.prox_cheq, i.prox_reserv,i.ulti_cheq,  i.res_nro,  "
				+ "       0.0 as importe, '' as detalle, '' as chequenro, '' as fecha,  "
				+ "       0 as clearing, 1 as cuotaspaga, -1 as idmovteso, 0 ,  "
				+ "       0, COALESCE(c.cuenta, 'CUENTA INVALIDA PARA EL EJERCICIO') AS cuenta  "
				+ "  FROM( "
				+ "	SELECT idretencion1 AS ididentificador, idproveedor, idempresa  "
				+ "       FROM proveedoproveed "
				+ "	 UNION ALL "
				+ "	SELECT idretencion2 AS ididentificador, idproveedor, idempresa  "
				+ "       FROM proveedoproveed   "
				+ "	 UNION ALL "
				+ "	SELECT idretencion3 AS ididentificador, idproveedor, idempresa FROM proveedoproveed   "
				+ "	 UNION ALL "
				+ "	SELECT idretencion4 AS ididentificador, idproveedor, idempresa  "
				+ "       FROM proveedoproveed   "
				+ "	 UNION ALL "
				+ "	SELECT idretencion5 AS ididentificador, idproveedor, idempresa  "
				+ "       FROM proveedoproveed   "
				+ "      ) ret  "
				+ "           INNER JOIN cajaidentificadores i ON ret.ididentificador = i.ididentificador  "
				+ "                  AND ret.idempresa = i.idempresa "
				+ "           INNER JOIN cajatipoidentificadores ti ON i.idtipoidentificador = ti.idtipoidentificador  "
				+ "                  AND i.idempresa = ti.idempresa "
				+ "                  AND ti.tipomov = 'R' "
				+ " 	       LEFT JOIN contableinfiplan c ON i.cuenta = c.idcuenta AND i.idempresa=c.idempresa    "
				+ "                  AND c.ejercicio=" + ejercicio.toString()
				+ " WHERE idproveedor = " + idproveedor.toString()
				+ "   AND i.idempresa = " + idempresa.toString();

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: contableInfiPlan Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Thu Dec 21 12:03:24 GMT-03:00 2006
	 */
	// para todo (ordena por el segundo campo por defecto)
	public List getLovContableInfiPlanAll(long limit, long offset,
			int ejercicioActivo, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT idcuenta,cuenta,inputable,nivel,ajustable,resultado,cent_cost1,"
				+ "       cent_cost2,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM CONTABLEINFIPLAN WHERE idempresa="
				+ idempresa.toString() + " AND ejercicio=" + ejercicioActivo
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getLovContableInfiPlanOcu(long limit, long offset,
			String ocurrencia, int ejercicioActivo, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idcuenta,cuenta,inputable,nivel,ajustable,resultado,cent_cost1,"
				+ "       cent_cost2,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM CONTABLEINFIPLAN "
				+ " WHERE ((idcuenta::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(cuenta) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%') AND idempresa = "
				+ idempresa.toString() + " AND ejercicio=" + ejercicioActivo
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: clientesClientes Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Fri Dec 29 09:29:05 GMT-03:00 2006
	 */

	public List getClientesClientesPK(BigDecimal idcliente, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT idcliente,razon,'domicilio' AS domicilio,-1 AS idlocalidad, -1 AS postal, -1 AS cuit,brutos,"
				+ "       idtipoiva,-1 AS idvendedor,idcondicion,descuento1,descuento2,descuento3,"
				+ "       '' AS contacto,'' aS cargo,idctaneto,idmoneda,idlista,-1 AS idzona,idtipoclie,"
				+ "       -1 AS idexpreso,'' AS telefonos,'' AS fax, '' AS email, '' AS web,observacion, -1 AS idcobrador,"
				+ "       lcredito,idtipocomp,autorizado,idcredcate,usuarioalt,usuarioact,fechaalt,fechaact"
				+ "  FROM CLIENTESCLIENTES WHERE idcliente="
				+ idcliente.toString() + " AND idempresa="
				+ idempresa.toString();

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * INICIA METODOS PARA GENERARAR PAGOS POR PROVEEDOR TODO: caja - auxteso :
	 * 
	 */

	public String[] pagosMovProveedorCreate(BigDecimal idproveedor,
			java.sql.Date fechamov, BigDecimal importe,
			Hashtable htComprobantesProv, Hashtable htIdentificaSalidaPagos,
			Hashtable htMovimientosCancelar, String observaciones,
			String usuarioalt, BigDecimal idempresa) throws EJBException,
			SQLException {

		String[] resultado = new String[] { "", "" };
		String salida = "OK";
		String[] datosProveedor = null;
		BigDecimal nrointernomovproveedor = null;
		BigDecimal nropago = null;
		BigDecimal nroint_mt = null;
		Enumeration en = null;
		String key = "";
		String usado_mt = "";
		String tipcart_mt = "";
		boolean isFirst = true;
		// AUTOR: EJV
		// DATE : 20070131
		// TODO: ESTA PENDIENTE DE ANALIZAR - DESARROLLAR: Descuentos en la
		// Preparaci�n de Pagos. Esta funcionalidad depende de una variable de
		// entorno (correspondiente al modulo de tesoreria), la cual permite o
		// no la carga de descuentos / retenciones en el
		// ingreso de pagos. Actualmente la aplicacion funciona sin contemplar
		// dicha funcionalidad.

		try {
			dbconn.setAutoCommit(false);
			// EJV - 20090512
			// nropago = GeneralBean.getContador(new BigDecimal(10), idempresa,
			// dbconn);
			nropago = GeneralBean.getContador("nrointernopago", idempresa,
					dbconn);

			// EJV - 20090512
			// nrointernomovproveedor = GeneralBean.getContador(new
			// BigDecimal(9),
			// idempresa, dbconn);
			nrointernomovproveedor = GeneralBean.getContador(
					"norinternocompprov", idempresa, dbconn);

			datosProveedor = (String[]) getProveedoProveedPK(idproveedor,
					idempresa).get(0);

			// --- --- Creando movimiento de proveedor
			// TODO: EJV 20071106 - Saldo de pagos se genera en 0 ....
			// ser� necesario validar que el cambio no afecte alguna consulta
			// hasta hoy el saldo se generaba con el valor por el importe, lo
			// que es un aparente error conceptual.
			// Ahora se esta generando con saldo en 0.
			// TODO: EJV 20081015 - el error aparentemente esta en no
			// discriminar si es adelanto o pago aplicado, a partir de hoy se
			// hace de esta forma.

			salida = proveedoMovProvCreate(nrointernomovproveedor, idproveedor,
					new Timestamp(fechamov.getTime()), new BigDecimal(0),
					nropago, new BigDecimal(4), "PA", importe,
					new BigDecimal(0), null, null, null, fechamov,
					observaciones, usuarioalt, idempresa);

			if (salida.equalsIgnoreCase("OK")) {// A

				salida = cajaAplicaciCreate("PAG", nropago,
						nrointernomovproveedor, "", new BigDecimal(0),
						usuarioalt, idempresa, dbconn);

				if (salida.equalsIgnoreCase("OK")) {// B

					en = htComprobantesProv.keys();
					while (en.hasMoreElements()) {
						// --- --- Actualizar saldo comprobantes.
						// --- -- Generar cancelaciones

						key = en.nextElement().toString();

						String[] datosComp = (String[]) htComprobantesProv
								.get(key);

						// Es un adelanto, no impacta saldo - no cancela
						// comprobante.
						if (key.equals("ADELANTO")) {

							salida = proveedoMovProvUpdateSaldo(
									nrointernomovproveedor, new BigDecimal(
											datosComp[1]), usuarioalt,
									idempresa);

							if (!salida.equalsIgnoreCase("OK"))
								break;

							continue;

						}

						salida = proveedoMovProvUpdateSaldo(new BigDecimal(
								datosComp[0]), new BigDecimal(datosComp[1])
								.negate(), usuarioalt, idempresa);

						if (!salida.equalsIgnoreCase("OK"))
							break;

						salida = proveedoCancProvCreateOrUpdate(new BigDecimal(
								datosComp[0]), nrointernomovproveedor,
								new BigDecimal(datosComp[1]), usuarioalt,
								idempresa);

						if (!salida.equalsIgnoreCase("OK"))
							break;
					}

					if (salida.equalsIgnoreCase("OK")) {// C

						/* ========== DETALLE DE INGRESOS ========== */
						// --- -- Generar movimiento de tesoreria
						salida = cajaMovTesoCreate("PRO", nropago,
								new Timestamp(fechamov.getTime()), "PAG", "O",
								nrointernomovproveedor, null, null,
								new Timestamp(fechamov.getTime()), null,
								new Timestamp(fechamov.getTime()), importe,
								importe, null, new BigDecimal(1), null,
								new BigDecimal(1), null, null, null,
								new BigDecimal(datosProveedor[10]),
								idproveedor, new BigDecimal(1), "S", null,
								null, null, usuarioalt, idempresa);

						if (salida.equalsIgnoreCase("OK")) {// D

							en = htIdentificaSalidaPagos.keys();
							while (en.hasMoreElements()) {

								key = (String) en.nextElement();
								String[] datosIngresos = (String[]) htIdentificaSalidaPagos
										.get(key);

								/*
								 * if (isFirst) {
								 * 
								 * salida = cajaMovTesoCreate("PRO", nropago,
								 * new Timestamp(fechamov.getTime()), "PAG",
								 * "O", nrointernomovproveedor, null, null, new
								 * Timestamp(fechamov .getTime()), null, new
								 * Timestamp(fechamov.getTime()), importe,
								 * importe, null, new BigDecimal( 1), null, new
								 * BigDecimal(1), null, null, null, new
								 * BigDecimal(datosProveedor[10]), idproveedor,
								 * new BigDecimal(1), "S", null, null, null,
								 * usuarioalt);
								 * 
								 * isFirst = false;
								 * 
								 * if (!salida.equalsIgnoreCase("OK")) break; }
								 */
								// TODO: calcular fecha clearing
								nroint_mt = new BigDecimal(0);
								BigDecimal nrodoc_mt = (datosIngresos[30] == null
										|| datosIngresos[30].trim().equals("") ? null
										: new BigDecimal(datosIngresos[30]));

								switch (datosIngresos[6].trim().charAt(0)) {
								case 'E':
								case 'G':
									usado_mt = "S";
									tipcart_mt = datosIngresos[6];
									salida = cajaSaldoBcoCreateOrUpdate(
											datosIngresos[2], fechamov,
											(new BigDecimal(datosIngresos[28])
													.negate()),
											new BigDecimal(datosIngresos[28])
													.negate(), usuarioalt,
											idempresa);

									break;
								case 'C':

									// TODO: Revisar logica aplicada segun
									// variable
									// global 'MARCA_CHEQUES_A_MANO'
									usado_mt = "D";
									tipcart_mt = "P";
									salida = cajaSaldoBcoCreateOrUpdate(
											datosIngresos[2], fechamov,
											(new BigDecimal(datosIngresos[28])
													.negate()),
											(new BigDecimal(0)), usuarioalt,
											idempresa);

									break;
								case 'D':
									usado_mt = null;
									tipcart_mt = "M";

									// EJV - 20090512
									// nroint_mt = GeneralBean.getContador(
									// new BigDecimal(11), idempresa,
									// dbconn);

									nroint_mt = GeneralBean.getContador(
											"nrointernodocumentos", idempresa,
											dbconn);

									salida = cajaSaldoBcoCreateOrUpdate(
											datosIngresos[2], fechamov,
											(new BigDecimal(datosIngresos[28])
													.negate()),
											(new BigDecimal(0)), usuarioalt,
											idempresa);

									break;
								case 'T':
									usado_mt = null;
									tipcart_mt = "J";
									break;

								default:
									usado_mt = "S";
									tipcart_mt = datosIngresos[6];
									break;
								}

								if (!salida.equalsIgnoreCase("OK"))
									break;

								// 20100428 - EJV -->
								java.sql.Date fecha_mt = new java.sql.Date(
										fechamov.getTime());
								java.sql.Date fclear_mt = fecha_mt;
								// <--
								if (datosIngresos[31] != null
										&& !datosIngresos[31].trim().equals("")) {

									try {
										SimpleDateFormat sdf = new SimpleDateFormat(
												"dd/MM/yyyy");
										fecha_mt = new java.sql.Date(sdf.parse(
												datosIngresos[31].replaceAll(
														"-", "/")).getTime());

										// 20100428 - EJV -->
										fclear_mt = fecha_mt;
										// <--

									} catch (Exception e) {
										salida = "Error al asignar fecha de cheque.";
									}

								}
								// 20100428 - EJV -->
								Hashtable htFeriados = new Hashtable();
								htFeriados = getCajaFeriadosFecha(idempresa);
								fclear_mt = GeneralBean.setFechaClearing(
										fecha_mt, 0, htFeriados);
								// <--

								if (!salida.equalsIgnoreCase("OK"))
									break;

								salida = cajaMovTesoCreate(datosIngresos[2],
										nropago, new Timestamp(fechamov
												.getTime()), "PAG", tipcart_mt,
										nroint_mt, datosIngresos[29],
										nrodoc_mt, new Timestamp(fecha_mt
												.getTime()), new BigDecimal(
												datosIngresos[32]),
										new Timestamp(fclear_mt.getTime()),
										new BigDecimal(datosIngresos[28]),
										new BigDecimal(datosIngresos[28]),
										null, new BigDecimal(datosIngresos[5]),
										null, new BigDecimal(1), null, null,
										null, new BigDecimal(datosIngresos[4]),
										idproveedor, new BigDecimal(2),
										usado_mt, null, new BigDecimal(
												datosIngresos[20]),
										new BigDecimal(datosIngresos[21]),
										usuarioalt, idempresa);

								if (!salida.equalsIgnoreCase("OK"))
									break;

							}

							if (salida.equalsIgnoreCase("OK")) {// E

								en = htMovimientosCancelar.keys();
								while (en.hasMoreElements()) {
									key = en.nextElement().toString();
									String[] datosMovimientosCancelar = (String[]) htMovimientosCancelar
											.get(key);
									nroint_mt = new BigDecimal(0);

									//

									for (int f = 0; f < datosMovimientosCancelar.length; f++)
										log.info("ELEM " + f + ": "
												+ datosMovimientosCancelar[f]);

									//

									BigDecimal nrodoc_mt = (datosMovimientosCancelar[30] == null
											|| datosMovimientosCancelar[30]
													.trim().equals("") ? null
											: new BigDecimal(
													datosMovimientosCancelar[30]));

									// 20090803 EJV -
									// Agregado: usado_mt_saldo / antes usaba
									// usado_mt. Estaba marcando mal los
									// documentos que se saldan. Hay que revisar
									// la actualizacion de estos campos.

									String usado_mt_saldo = "";
									switch (datosMovimientosCancelar[6].trim()
											.charAt(0)) {
									case 'C':
									case 'S':
										usado_mt = usado_mt_saldo = "S";
										tipcart_mt = datosMovimientosCancelar[6];
										break;
									case 'D':

										usado_mt_saldo = "S";
										usado_mt = "O";
										tipcart_mt = "D";// 20070201
										nroint_mt = new BigDecimal(
												datosMovimientosCancelar[30]);
										nrodoc_mt = new BigDecimal(
												datosMovimientosCancelar[37]);
										break;

									default:
										usado_mt = usado_mt_saldo = "S";
										tipcart_mt = datosMovimientosCancelar[6];
										break;
									}

									salida = cajaSaldaMovimientosTeso(
											new BigDecimal(key), "PAG",
											new BigDecimal(0), nropago,
											usado_mt_saldo, null, usuarioalt,
											idempresa, dbconn);

									if (!salida.equalsIgnoreCase("OK"))
										break;

									salida = cajaMovTesoCreate(
											datosMovimientosCancelar[2],
											nropago,
											new Timestamp(fechamov.getTime()),
											"PAG",
											tipcart_mt,
											nroint_mt,
											datosMovimientosCancelar[29],
											nrodoc_mt,
											new Timestamp(fechamov.getTime()),
											new BigDecimal(
													datosMovimientosCancelar[32]),
											new Timestamp(fechamov.getTime()),
											new BigDecimal(
													datosMovimientosCancelar[28]),
											new BigDecimal(
													datosMovimientosCancelar[28]),
											null,
											new BigDecimal(
													datosMovimientosCancelar[5]),
											null,
											new BigDecimal(1),
											null,
											null,
											null,
											new BigDecimal(
													datosMovimientosCancelar[4]),
											idproveedor,
											new BigDecimal(2),
											usado_mt,
											null,
											new BigDecimal(
													datosMovimientosCancelar[20]),
											new BigDecimal(
													datosMovimientosCancelar[21]),
											usuarioalt, idempresa);

									if (!salida.equalsIgnoreCase("OK"))
										break;

								}
							}// E
						}// D
					}// C
				}// B
			}// A
		} catch (Exception e) {
			salida = "Error al generar Movimiento de Tesoreria.";
			log.error("pagosMovProveedorCreate(...): " + e);
		}

		if (!salida.equalsIgnoreCase("OK")) {
			log.warn("pagosMovProveedorCreate() - Transaccion cancelada: "
					+ salida);
			dbconn.rollback();
		} else {
			/**
			 * @date: 20080115
			 * @autor:ejv
			 * @comentarios: Impresion a PDF, hasta la fecha retornaba nropago
			 *               (comprobante), ahora retorna
			 *               nrointernomovproveedor.
			 */

			// salida = nropago.toString();
			salida = nrointernomovproveedor.toString();
		}

		resultado[0] = salida;
		resultado[1] = nropago + "";

		dbconn.setAutoCommit(true);
		return resultado;

	}

	/**
	 * @DATE: 20080429 INICIA METODOS PARA GENERARAR PAGOS DIRECTOS TODO: caja -
	 *        auxteso (Pendiente de realizar pagos directos + anexo + factura):
	 * 
	 */

	public String pagosEgresosDirectosCreate(BigDecimal idproveedor,
			java.sql.Date fechamov, BigDecimal importe,
			Hashtable htIdentificaSalidaPagos, Hashtable htMovimientosCancelar,
			Hashtable htIdentificaEntradaPagosDirect,
			Hashtable htMovimientosEntradaCancelar, String observaciones,
			String usuarioalt, BigDecimal idempresa) throws EJBException,
			SQLException {
		String salida = "OK";

		BigDecimal nropago = null;
		BigDecimal nroint_mt = null;
		Enumeration en = null;
		String key = "";
		String usado_mt = "";
		String tipcart_mt = "";
		boolean isFirst = true;

		// 20100428 - EJV -->
		Hashtable htFeriados = new Hashtable();
		java.sql.Date fecha_mt = new java.sql.Date(fechamov.getTime());
		java.sql.Date fclear_mt = fecha_mt;
		// <--

		/*
		 * @AUTOR: EJV..........................................................
		 * 
		 * @DATE : 20070131.....................................................
		 * TODO: ESTA PENDIENTE DE ANALIZAR - DESARROLLAR: Descuentos en la.....
		 * Preparaci�n de Pagos. Esta funcionalidad depende de una variable de
		 * entorno (correspondiente al modulo de tesoreria), la cual permite o..
		 * no la carga de descuentos / retenciones en el........................
		 * ingreso de pagos. Actualmente la aplicacion funciona sin contemplar..
		 * dicha funcionalidad..................................................
		 */

		try {
			dbconn.setAutoCommit(false);

			htFeriados = getCajaFeriadosFecha(idempresa);
			// EJV - 20090512
			// nropago = GeneralBean.getContador(new BigDecimal(10), idempresa,
			// dbconn);

			nropago = GeneralBean.getContador("nrointernopago", idempresa,
					dbconn);

			//
			if (observaciones != null && observaciones.trim().length() > 0)
				salida = cajaMovTesObsCreate("PAG", new BigDecimal(0), nropago,
						observaciones, idempresa, usuarioalt);

			//

			if (salida.equalsIgnoreCase("OK")) {

				// SALIDA
				en = htIdentificaSalidaPagos.keys();
				while (en.hasMoreElements()) {

					fclear_mt = fecha_mt = fechamov;

					key = (String) en.nextElement();
					String[] datosIngresos = (String[]) htIdentificaSalidaPagos
							.get(key);

					log.info("--> SALIDA IDENTIF: " + key);

					// TODO: calcular fecha clearing
					nroint_mt = new BigDecimal(0);
					BigDecimal nrodoc_mt = (datosIngresos[30] == null
							|| datosIngresos[30].trim().equals("") ? null
							: new BigDecimal(datosIngresos[30]));

					switch (datosIngresos[6].trim().charAt(0)) {
					case 'E':
					case 'G':
						usado_mt = "S";
						tipcart_mt = datosIngresos[6];
						salida = cajaSaldoBcoCreateOrUpdate(datosIngresos[2],
								fechamov, (new BigDecimal(datosIngresos[28])
										.negate()), new BigDecimal(
										datosIngresos[28]).negate(),
								usuarioalt, idempresa);

						break;
					case 'C':

						// TODO: Revisar logica aplicada segun
						// variable
						// global 'MARCA_CHEQUES_A_MANO'
						usado_mt = "D";
						tipcart_mt = "P";
						salida = cajaSaldoBcoCreateOrUpdate(datosIngresos[2],
								fechamov, (new BigDecimal(datosIngresos[28])
										.negate()), (new BigDecimal(0)),
								usuarioalt, idempresa);

						// 20100428 - EJV -->
						if (datosIngresos[31] != null
								&& !datosIngresos[31].trim().equals("")) {

							try {
								SimpleDateFormat sdf = new SimpleDateFormat(
										"dd/MM/yyyy");
								fecha_mt = new java.sql.Date(sdf.parse(
										datosIngresos[31].replaceAll("-", "/"))
										.getTime());

								fclear_mt = fecha_mt;

							} catch (Exception e) {
								salida = "Error al asignar fecha de cheque.";
							}

						}

						fclear_mt = GeneralBean.setFechaClearing(fecha_mt, 0,
								htFeriados);
						// <--

						break;
					case 'D':
						usado_mt = null;
						tipcart_mt = "M";
						// EJV - 20090512
						// nroint_mt = GeneralBean.getContador(new
						// BigDecimal(11),
						// idempresa, dbconn);

						nroint_mt = GeneralBean.getContador(
								"nrointernodocumentos", idempresa, dbconn);
						salida = cajaSaldoBcoCreateOrUpdate(datosIngresos[2],
								fechamov, (new BigDecimal(datosIngresos[28])
										.negate()), (new BigDecimal(0)),
								usuarioalt, idempresa);

						break;
					case 'T':
						usado_mt = null;
						tipcart_mt = "J";
						break;

					default:
						usado_mt = "S";
						tipcart_mt = datosIngresos[6];
						break;
					}

					if (!salida.equalsIgnoreCase("OK"))
						break;

					log.info("A - datosIngresos[2]: " + datosIngresos[2]);

					salida = cajaMovTesoCreate(datosIngresos[2], nropago,
							new Timestamp(fechamov.getTime()), "PAG",
							tipcart_mt, nroint_mt, datosIngresos[29],
							nrodoc_mt, new Timestamp(fecha_mt.getTime()),
							new BigDecimal(datosIngresos[32]), new Timestamp(
									fclear_mt.getTime()), new BigDecimal(
									datosIngresos[28]), new BigDecimal(
									datosIngresos[28]), null, new BigDecimal(
									datosIngresos[5]), null, new BigDecimal(1),
							null, null, null, new BigDecimal(datosIngresos[4]),
							idproveedor, new BigDecimal(2), usado_mt, null,
							new BigDecimal(datosIngresos[20]), new BigDecimal(
									datosIngresos[21]), usuarioalt, idempresa);

					if (!salida.equalsIgnoreCase("OK"))
						break;

				}

				if (salida.equalsIgnoreCase("OK")) {// E

					en = htMovimientosCancelar.keys();
					while (en.hasMoreElements()) {
						key = en.nextElement().toString();
						String[] datosMovimientosCancelar = (String[]) htMovimientosCancelar
								.get(key);
						nroint_mt = new BigDecimal(0);

						log.info("--> SALIDA MC: " + key);

						BigDecimal nrodoc_mt = (datosMovimientosCancelar[30] == null
								|| datosMovimientosCancelar[30].trim().equals(
										"") ? null : new BigDecimal(
								datosMovimientosCancelar[30]));

						switch (datosMovimientosCancelar[6].trim().charAt(0)) {
						case 'C':
						case 'S':
							usado_mt = "S";
							tipcart_mt = datosMovimientosCancelar[6];
							break;
						case 'D':
							usado_mt = "O";
							tipcart_mt = "D";// 20070201
							nroint_mt = new BigDecimal(
									datosMovimientosCancelar[30]);
							nrodoc_mt = new BigDecimal(
									datosMovimientosCancelar[37]);
							break;

						default:
							usado_mt = "S";
							tipcart_mt = datosMovimientosCancelar[6];
							break;
						}

						salida = cajaSaldaMovimientosTeso(new BigDecimal(key),
								"PAG", new BigDecimal(0), nropago, usado_mt,
								null, usuarioalt, idempresa, dbconn);

						if (!salida.equalsIgnoreCase("OK"))
							break;

						log.info("B - datosMovimientosCancelar[2]: "
								+ datosMovimientosCancelar[2]);

						salida = cajaMovTesoCreate(datosMovimientosCancelar[2],
								nropago, new Timestamp(fechamov.getTime()),
								"PAG", tipcart_mt, nroint_mt,
								datosMovimientosCancelar[29], nrodoc_mt,
								new Timestamp(fechamov.getTime()),
								new BigDecimal(datosMovimientosCancelar[32]),
								new Timestamp(fechamov.getTime()),
								new BigDecimal(datosMovimientosCancelar[28]),
								new BigDecimal(datosMovimientosCancelar[28]),
								null, new BigDecimal(
										datosMovimientosCancelar[5]), null,
								new BigDecimal(1), null, null, null,
								new BigDecimal(datosMovimientosCancelar[4]),
								idproveedor, new BigDecimal(2), usado_mt, null,
								new BigDecimal(datosMovimientosCancelar[20]),
								new BigDecimal(datosMovimientosCancelar[21]),
								usuarioalt, idempresa);

						if (!salida.equalsIgnoreCase("OK"))
							break;

					}

					// ---------------------------------------------------------------------
					// ENTRADA -->

					en = htIdentificaEntradaPagosDirect.keys();
					while (en.hasMoreElements()) {

						key = (String) en.nextElement();
						String[] datosIdentifEntrada = (String[]) htIdentificaEntradaPagosDirect
								.get(key);

						log.info("--> ENTRADA IDENTIF: " + key);

						// TODO: calcular fecha clearing
						nroint_mt = new BigDecimal(0);
						BigDecimal nrodoc_mt = (datosIdentifEntrada[30] == null
								|| datosIdentifEntrada[30].trim().equals("") ? null
								: new BigDecimal(datosIdentifEntrada[30]));

						switch (datosIdentifEntrada[6].trim().charAt(0)) {
						case 'E':
						case 'G':
							usado_mt = "S";
							tipcart_mt = datosIdentifEntrada[6];
							salida = cajaSaldoBcoCreateOrUpdate(
									datosIdentifEntrada[2], fechamov,
									(new BigDecimal(datosIdentifEntrada[28])
											.negate()), new BigDecimal(
											datosIdentifEntrada[28]).negate(),
									usuarioalt, idempresa);
							break;
						case 'O':
							usado_mt = "S";
							tipcart_mt = datosIdentifEntrada[6];

							break;
						default:
							usado_mt = "S";
							tipcart_mt = datosIdentifEntrada[6];
							break;
						}

						if (!salida.equalsIgnoreCase("OK"))
							break;

						log.info("C - datosMovimientosCancelar[2]: "
								+ datosIdentifEntrada[2]);

						salida = cajaMovTesoCreate(datosIdentifEntrada[2],
								nropago, new Timestamp(fechamov.getTime()),
								"PAG", tipcart_mt, nroint_mt,
								datosIdentifEntrada[29], nrodoc_mt,
								new Timestamp(fechamov.getTime()),
								new BigDecimal(datosIdentifEntrada[32]),
								new Timestamp(fechamov.getTime()),
								new BigDecimal(datosIdentifEntrada[28]),
								new BigDecimal(datosIdentifEntrada[28]), null,
								new BigDecimal(datosIdentifEntrada[5]), null,
								new BigDecimal(1), null, null, null,
								new BigDecimal(datosIdentifEntrada[4]),
								idproveedor, new BigDecimal(1), usado_mt, null,
								new BigDecimal(datosIdentifEntrada[20]),
								new BigDecimal(datosIdentifEntrada[21]),
								usuarioalt, idempresa);

						if (!salida.equalsIgnoreCase("OK"))
							break;

					}

					if (salida.equalsIgnoreCase("OK")) {// Z

						en = htMovimientosEntradaCancelar.keys();
						while (en.hasMoreElements()) {
							key = en.nextElement().toString();
							String[] datosMovEntradaCancelar = (String[]) htMovimientosEntradaCancelar
									.get(key);
							nroint_mt = new BigDecimal(0);

							log.info("--> ENTRADA MC: " + key);

							BigDecimal nrodoc_mt = (datosMovEntradaCancelar[30] == null
									|| datosMovEntradaCancelar[30].trim()
											.equals("") ? null
									: new BigDecimal(
											datosMovEntradaCancelar[30]));

							switch (datosMovEntradaCancelar[6].trim().charAt(0)) {
							case 'D':

								usado_mt = "S";
								tipcart_mt = "D";// 20070201

								/*
								 * nroint_mt = new BigDecimal(
								 * datosMovEntradaCancelar[30]);
								 */

								nroint_mt = new BigDecimal(0);
								nrodoc_mt = new BigDecimal(
										datosMovEntradaCancelar[37] == null ? "0"
												: datosMovEntradaCancelar[37]);

								salida = cajaSaldaMovimientosTeso(
										new BigDecimal(key), "PAG",
										new BigDecimal(0), nropago, usado_mt,
										null, usuarioalt, idempresa, dbconn);

								break;

							case 'T':

								BigDecimal importeactual = getTotalCartera(
										datosMovEntradaCancelar[2], "J",
										fechamov, idempresa);

								if (importeactual.compareTo(new BigDecimal(0)) < 1
										|| importeactual
												.subtract(
														new BigDecimal(
																datosMovEntradaCancelar[28]))
												.compareTo(new BigDecimal(0)) < 0) {

									salida = "Saldo de cartera inexistente o fue modificado desde otra sesion.";
									break;

								}

								usado_mt = "S";
								tipcart_mt = datosMovEntradaCancelar[6];

								salida = cajaSaldaMovimientosXFechaTeso("PAG",
										new BigDecimal(0), nropago, usado_mt,
										datosMovEntradaCancelar[2], "J",
										fechamov, usuarioalt, idempresa);

								if (!salida.equalsIgnoreCase("OK"))
									break;

								if (importeactual.subtract(
										new BigDecimal(
												datosMovEntradaCancelar[28]))
										.compareTo(new BigDecimal(0)) > 0) {

									log.info("D - datosMovEntradaCancelar[2]: "
											+ datosMovEntradaCancelar[2]);

									salida = cajaMovTesoCreate(
											datosMovEntradaCancelar[2],
											nropago,
											new Timestamp(fechamov.getTime()),
											"PAG",
											"J",
											nroint_mt,
											"TRANSPORTE",
											nrodoc_mt,
											new Timestamp(fechamov.getTime()),
											new BigDecimal(
													datosMovEntradaCancelar[32]),
											new Timestamp(fechamov.getTime()),
											importeactual
													.subtract(new BigDecimal(
															datosMovEntradaCancelar[28])),
											importeactual
													.subtract(new BigDecimal(
															datosMovEntradaCancelar[28])),
											null,
											new BigDecimal(
													datosMovEntradaCancelar[5]),
											null,
											new BigDecimal(1),
											null,
											null,
											null,
											new BigDecimal(
													datosMovEntradaCancelar[4]),
											new BigDecimal(0),
											new BigDecimal(2),
											null,
											null,
											new BigDecimal(
													datosMovEntradaCancelar[20]),
											new BigDecimal(
													datosMovEntradaCancelar[21]),
											usuarioalt, idempresa);
								}

								break;

							default:

								usado_mt = "S";
								tipcart_mt = datosMovEntradaCancelar[6];
								salida = cajaSaldaMovimientosTeso(
										new BigDecimal(key), "PAG",
										new BigDecimal(0), nropago, usado_mt,
										null, usuarioalt, idempresa, dbconn);
								break;
							}

							if (!salida.equalsIgnoreCase("OK"))
								break;

							log.info("E - datosMovEntradaCancelar[2]: "
									+ datosMovEntradaCancelar[2]);

							salida = cajaMovTesoCreate(
									datosMovEntradaCancelar[2],
									nropago,
									new Timestamp(fechamov.getTime()),
									"PAG",
									tipcart_mt,
									nroint_mt,
									datosMovEntradaCancelar[29],
									nrodoc_mt,
									new Timestamp(fechamov.getTime()),
									new BigDecimal(datosMovEntradaCancelar[32]),
									new Timestamp(fechamov.getTime()),
									new BigDecimal(datosMovEntradaCancelar[28]),
									new BigDecimal(datosMovEntradaCancelar[28]),
									null,
									new BigDecimal(datosMovEntradaCancelar[5]),
									null,
									new BigDecimal(1),
									null,
									null,
									null,
									new BigDecimal(datosMovEntradaCancelar[4]),
									idproveedor,
									new BigDecimal(1),
									usado_mt,
									null,
									new BigDecimal(datosMovEntradaCancelar[20]),
									new BigDecimal(datosMovEntradaCancelar[21]),
									usuarioalt, idempresa);

							if (!salida.equalsIgnoreCase("OK"))
								break;

						}

					}

					// Z
					// ENTRADA <--
					// ---------------------------------------------------------------------

				}// E

			}

		} catch (Exception e) {
			salida = "Error al generar Movimiento de Tesoreria.";
			log.error("pagosEgresosDirectosCreate(...): " + e);
		}

		if (!salida.equalsIgnoreCase("OK")) {
			log.warn("pagosEgresosDirectosCreate() - Transaccion cancelada: "
					+ salida);
			dbconn.rollback();
		} else {
			salida = nropago.toString();
		}

		dbconn.setAutoCommit(true);
		return salida;
	}

	/**
	 * 20090513 INICIA METODOS PARA ELIMINAR - ANULAR PAGOS A PROVEEDORES /
	 * EGRESOS. DIRECTOS TODO: caja - auxteso : no existe en el modelo nuevo.
	 * 
	 */

	public String pagosCajaMovTesoAnular(BigDecimal sucursal,
			BigDecimal comprobante, java.sql.Timestamp fechateso,
			java.sql.Timestamp fultcierreprov, BigDecimal idproveedor,
			String usuarioact, BigDecimal idempresa) throws EJBException,
			SQLException {

		String salida = "OK";
		String tipomov = "PAG";
		String tipo_an = "ED";
		BigDecimal nrointerno = new BigDecimal(-1);
		String anexo_ap = "";
		BigDecimal factorNegPos = new BigDecimal(1);
		BigDecimal totalPago = new BigDecimal(0);
		Iterator iterAplicaciones;
		Iterator iterCancelaciones;

		dbconn.setAutoCommit(false);

		try {

			salida = cajaMovTesoAnulaSaldadosUpdate(tipomov, sucursal,
					comprobante, idempresa, usuarioact);

			if (salida.equalsIgnoreCase("OK")) {// - A

				iterAplicaciones = getCajaAplicaciPK(tipomov, sucursal,
						comprobante, idempresa).iterator();

				// Es un comprobante de proveedores

				if (idproveedor.longValue() > 0) {// - A1

					tipo_an = "PRO";

					while (iterAplicaciones.hasNext()) { // - B1.1

						String[] datos = (String[]) iterAplicaciones.next();
						nrointerno = new BigDecimal(datos[0]);

						iterCancelaciones = ProveedoresBean
								.getComprobantesAplicadosProveedo(nrointerno,
										dbconn, idempresa).iterator();

						// Actualizando FA's aplicadas por el comprobante de
						// conbranza.
						while (iterCancelaciones.hasNext()) { // - B1.2
							String[] datosCanc = (String[]) iterCancelaciones
									.next();
							BigDecimal saldo = new BigDecimal(datosCanc[0]);
							BigDecimal nrointerno_canc = new BigDecimal(
									datosCanc[1]);

							salida = ProveedoresBean
									.proveedoMovProvSaldoUpdateOrDelete(
											nrointerno_canc, idproveedor,
											saldo, dbconn, usuarioact,
											idempresa);

							if (!salida.equalsIgnoreCase("OK"))
								break;
							// Eliminar PROVEEDOCANCPROV
							salida = ProveedoresBean.proveedoAnulaAplicaciones(
									nrointerno_canc, nrointerno, dbconn,
									idempresa);

							if (!salida.equalsIgnoreCase("OK"))
								break;

						} // - B1.2

						iterCancelaciones = ProveedoresBean
								.getComprobantesQueAplicanProveedo(nrointerno,
										dbconn, idempresa).iterator();

						while (iterCancelaciones.hasNext()
								&& salida.equalsIgnoreCase("OK")) { // -
							// B1.3

							String[] datosCanc = (String[]) iterCancelaciones
									.next();
							BigDecimal saldo = new BigDecimal(datosCanc[0]);
							BigDecimal nrointerno_q_canc = new BigDecimal(
									datosCanc[1]);

							salida = ProveedoresBean
									.proveedoMovProvSaldoUpdateOrDelete(
											nrointerno_q_canc, idproveedor,
											saldo, dbconn, usuarioact,
											idempresa);

							if (!salida.equalsIgnoreCase("OK"))
								break;
							// Eliminar PROVEEDOCANCPROV
							salida = ProveedoresBean.proveedoAnulaAplicaciones(
									nrointerno, nrointerno_q_canc, dbconn,
									idempresa);

						} // - B1.3

						if (!salida.equalsIgnoreCase("OK"))
							break;

						// Eliminar pago de proveedores (PROVEEDOMOVPROV)
						salida = ProveedoresBean.proveedoMovProvDelete(
								nrointerno, dbconn, idempresa);

						if (!salida.equalsIgnoreCase("OK"))
							break;

					} // - B1.1

					// -------------

					// Eliminar CAJAAPLICACI

					if (salida.equalsIgnoreCase("OK")) { // - B2

						salida = cajaAplicaciDelete(tipomov, sucursal,
								comprobante, idempresa);

						if (salida.equalsIgnoreCase("OK")) { // - B2.1

							Iterator iter = getCajaMovTesoXComprobante(
							// 20120716-EJV-Mantis 858-->
									tipomov,
									// <--
									sucursal, comprobante, idempresa)
									.iterator();

							// Eliminar CAJAAMOVTESO ()
							salida = cajaMovTesoXComprobanteDelete(
							// 20120716 - EJV - Mantis 858 -->
									tipomov,
									// <--
									sucursal, comprobante, idempresa);

							while (iter.hasNext()
									&& salida.equalsIgnoreCase("OK")) { // -
								// B2.2

								String[] datosMovTeso = (String[]) iter.next();
								char tipcart_mt = datosMovTeso[5].charAt(0);
								int tipo_mt = Integer
										.parseInt(datosMovTeso[23]);
								// BigDecimal idmovteso = new
								// BigDecimal(datosMovTeso[0]);
								BigDecimal importe_mt = new BigDecimal(
										datosMovTeso[13]);
								String banco = datosMovTeso[1];
								java.sql.Date fechamo_mt = java.sql.Date
										.valueOf(datosMovTeso[3]);
								java.sql.Date fecha_mt = java.sql.Date
										.valueOf(datosMovTeso[9]);

								if (tipo_mt == 1)
									totalPago = totalPago.add(importe_mt);

								switch (tipcart_mt) {
								case 'E':
								case 'G':
									salida = cajaSaldoBcoCreateOrUpdate(banco,
											fechamo_mt, importe_mt, importe_mt,
											usuarioact, idempresa);

									break;
								case 'P':
									BigDecimal importe = new BigDecimal(0);

									// TODO: verificar esta validacion, es
									// posible que genere problemas en los
									// saldos de bancos.
									if (fechamo_mt.equals(fecha_mt))
										importe = importe_mt;

									salida = cajaSaldoBcoCreateOrUpdate(banco,
											fechamo_mt, importe_mt, importe,
											usuarioact, idempresa);

									break;
								default:
									continue;
								}

								if (!salida.equalsIgnoreCase("OK"))
									break;

							} // - B2.2

						} // - B2.1

					}// - B2

					// --------------

				} else { // - A1

					long totaldocs = getTotalDocsAntesFCierre(sucursal,
							comprobante, fultcierreprov, idempresa);

					if (totaldocs == -1l) {
						salida = "No se pudo verificar documentos saldados anteriores a fecha de ultimo cierre de proveedores.";
					} else if (totaldocs > 0) {
						salida = "El comprobante tiene facturas de compras con fecha anterior al ultimo cierre de proveedores.";
					}

					if (salida.equalsIgnoreCase("OK")) { // - A2.1

						Iterator iter = getCajaMovTesoXComprobanteIdent(
						// 20120716 - EJV - Mantis 858 -->
								tipomov,
								// <--
								sucursal, comprobante, idempresa).iterator();

						// Eliminar CAJAAMOVTESO ()
						// TODO: el programa migrado no hace un delete fisico,
						// marca como anulado ...
						salida = cajaMovTesoXComprobanteDelete(
						// 20120716 - EJV - Mantis 858 -->
								tipomov,
								// <--
								sucursal, comprobante, idempresa);

						while (iter.hasNext() && salida.equalsIgnoreCase("OK")) { // -
							// A2.2

							String[] datosMovTeso = (String[]) iter.next();
							char tipcart_mt = datosMovTeso[5].charAt(0);
							int tipo_mt = Integer.parseInt(datosMovTeso[23]);
							String factura = datosMovTeso[29];
							// BigDecimal idmovteso = new
							// BigDecimal(datosMovTeso[0]);
							BigDecimal importe_mt = new BigDecimal(
									datosMovTeso[13]);
							String banco = datosMovTeso[1];
							java.sql.Date fechamo_mt = java.sql.Date
									.valueOf(datosMovTeso[3]);
							java.sql.Date fecha_mt = java.sql.Date
									.valueOf(datosMovTeso[9]);

							switch (tipcart_mt) {
							case 'E':
							case 'G':

								if (tipo_mt == 2)
									factorNegPos = factorNegPos.abs().negate();
								else
									factorNegPos = factorNegPos.abs();

								salida = cajaSaldoBcoCreateOrUpdate(banco,
										fechamo_mt, importe_mt
												.multiply(factorNegPos),
										importe_mt.multiply(factorNegPos),
										usuarioact, idempresa);
								break;

							case 'P':
								BigDecimal importe = new BigDecimal(0);

								// TODO: verificar esta validacion, es
								// posible que genere problemas en los
								// saldos de bancos.
								if (fechamo_mt.equals(fecha_mt))
									importe = importe_mt;

								salida = cajaSaldoBcoCreateOrUpdate(banco,
										fechamo_mt, importe_mt, importe,
										usuarioact, idempresa);

								break;
							default:
								continue;
							}

							if (!salida.equalsIgnoreCase("OK"))
								break;

							if (tipo_mt == 1) {

								totalPago = totalPago.add(importe_mt);

								if (factura.equalsIgnoreCase("S")) {

									nrointerno = new BigDecimal(6);

									anexo_ap = getCajaAplicaciAnexoPK(
											nrointerno, tipomov, sucursal,
											comprobante, idempresa);

									// -->
									// EJV - 20090526
									// ************************************
									// TODO: Esto no esta desarrollado, pagos x
									// anexo, de todos modos es tenido en cuenta
									// para cuando sea necesario. Llegado el
									// momento realizar las pruebas
									// correspondientes.
									if (anexo_ap.equalsIgnoreCase("S")) { // -

										salida = ProveedoresBean
												.proveedoAnexopDelete(
														nrointerno, dbconn,
														idempresa);

										if (!salida.equalsIgnoreCase("OK"))
											break;

										salida = ProveedoresBean
												.proveedoContProvDelete(
														nrointerno, dbconn,
														idempresa);
										if (!salida.equalsIgnoreCase("OK"))
											break;

									} else if (anexo_ap.equalsIgnoreCase("N")) { // -

										iterCancelaciones = ProveedoresBean
												.getComprobantesAplicadosProveedo(
														nrointerno, dbconn,
														idempresa).iterator();

										while (iterCancelaciones.hasNext()) { // -
											// B1.2
											String[] datosCanc = (String[]) iterCancelaciones
													.next();

											BigDecimal nrointerno_canc = new BigDecimal(
													datosCanc[1]);

											// Eliminar PROVEEDOCANCPROV
											salida = ProveedoresBean
													.proveedoAnulaAplicaciones(
															nrointerno_canc,
															nrointerno, dbconn,
															idempresa);
											if (!salida.equalsIgnoreCase("OK"))
												break;

											salida = ProveedoresBean
													.proveedoContProvDelete(
															nrointerno_canc,
															dbconn, idempresa);

											if (!salida.equalsIgnoreCase("OK"))
												break;

											salida = ProveedoresBean
													.proveedoMovProvDelete(
															nrointerno, dbconn,
															idempresa);

											if (!salida.equalsIgnoreCase("OK"))
												break;

											salida = ProveedoresBean
													.proveedoMovProvDelete(
															nrointerno_canc,
															dbconn, idempresa);

											if (!salida.equalsIgnoreCase("OK"))
												break;

										} // - B1.2

									} else {
										salida = "No existe movimiento aplicado en cajaaplicaci.";
										break;
									}
									// <--
									// *********************************************

								}

							}

						} // - A2.2

					} // - A2.1

					if (salida.equalsIgnoreCase("OK")) {

						salida = cajaAplicaciDelete(tipomov, sucursal,
								comprobante, idempresa);
					}

					// ---------------------------

				}// - A1

				if (salida.equalsIgnoreCase("OK")) {
					salida = cajaAnuladasCreate(tipomov, comprobante,
							fechateso, totalPago, sucursal, idproveedor, null,
							tipo_an, idempresa, usuarioact);
				}

			} // - A

		} catch (Exception e) {
			salida = "No se pudo eliminar comprobante de pagos, ocurrio una excepcion.";
			log.error("pagosCajaMovTesoAnular(): " + e);
		}

		if (salida.equalsIgnoreCase("OK")) {
			dbconn.commit();
		} else {
			dbconn.rollback();
		}

		dbconn.setAutoCommit(true);

		return salida;

	}

	// --------------------------------------------------------------------------

	/**
	 * INICIA METODOS PARA GENERARAR COBRANZAS POR CLIENTES TODO: caja - auxteso
	 * : no existe en el modelo nuevo.
	 */

	public String cobranzasMovClienteCreate(BigDecimal idcliente,
			BigDecimal idcobrador, java.sql.Date fechamov,
			BigDecimal totalCobranza, Hashtable htComprobantes,
			Hashtable htIdentificadores, Hashtable htIdentificadoresIngesos,
			String observaciones, String usuarioalt, BigDecimal idempresa)
			throws EJBException, SQLException {
		String salida = "OK";
		String[] datosCliente = null;
		BigDecimal nrointernomovcliente = null;
		BigDecimal nrocobranza = null;
		BigDecimal nroint_mt = null;
		Enumeration en = null;
		String key = "";
		String[] datosCobrador = null;
		BigDecimal com_cobra = new BigDecimal(0);
		boolean isFirst = true;
		// TODO: descuentos.

		// 20100427 - EJV -->
		Hashtable htFeriados = new Hashtable();
		java.sql.Date fecha_mt = fechamov;
		java.sql.Date fclear_mt = fechamov;
		BigDecimal idclearing = new BigDecimal(-1);
		// <--
		// 20120911 - EJV - Mantis 869 - Manual -- >
		BigDecimal saldoCob = new BigDecimal(0);
		boolean incluyeAdelanto = false;
		// <--

		// 20130711 - EJV -->
		Connection conn = GeneralBean.getTransaccionConn(this.clase, this.url,
				this.usuario, this.clave);
		// <--
		// <--

		if (conn == null)
			return "E1000-No fue posible generar vinculo a base de datos, intente nuevamente o avise a sistemas.";

		try {
			log.info("I.COB.");

			conn.setAutoCommit(false);
			// 20110622 - EJV - Factuaracion FE-CF-MA -->
			String condicionletra = "";
			condicionletra = getClienteLetraIva(idcliente, idempresa);
			// <--

			datosCliente = (String[]) getClientesClientesPK(idcliente,
					idempresa).get(0);

			// 20100427 - EJV -->
			htFeriados = getCajaFeriadosFecha(idempresa);
			// <--

			// EJV - 20090512
			// nrocobranza = GeneralBean.getContador(new BigDecimal(7),
			// idempresa,
			// dbconn);

			nrocobranza = GeneralBean.getContador("nrointernocobranza",
					idempresa, conn);

			// EJV - 20090512
			// nrointernomovcliente = GeneralBean.getContador(new BigDecimal(8),
			// idempresa, dbconn);
			nrointernomovcliente = GeneralBean.getContador(
					"nrointernomovcliente", idempresa, conn);

			/*
			 * TODO: Definir con CEP que entidad domina: clientesCobradores /
			 * clientesVendedor if(idcobrador != null){ // Si no existe cobrador
			 * sale por exception ... datosCobrador =
			 * (String[])getClientesVendedorPK(idcobrador).get(0); com_cobra =
			 * new BigDecimal(datosCobrador[3]); }
			 */

			/* ========== COMPROBANTES PARA APLICAR ========== */
			log.info("I.COB.: COMP APL");

			// 20120911 - EJV - Mantis 869 - Manual -- >
			if (htComprobantes == null || htComprobantes.isEmpty())
				salida = "Es necesario seleccionar comprobantes o ingresar un Adelanto.";
			else {
				en = htComprobantes.keys();
				incluyeAdelanto = (htComprobantes.containsKey("ADELANTO"));

				// 20130711 - EJV -->

				// if (esAdelanto && htComprobantes.size() != 1)
				// salida =
				// "No es posible generar un Adelanto y Cancelar Comprobantes al mismo tiempo.";

				// <--

			}
			// <--

			while (salida.equalsIgnoreCase("OK") && en.hasMoreElements()) {
				// --- --- Actualizar saldo comprobantes.
				// --- -- Generar cancelaciones

				key = en.nextElement().toString();

				String[] datosComp = (String[]) htComprobantes.get(key);

				// Es un adelanto, no impacta saldo - no cancela comprobante.
				if (key.equals("ADELANTO")) {
					// if (incluyeAdelanto) {

					// 20130711 - EJV -->
					// saldoCob = totalCobranza;
					// break;
					saldoCob = new BigDecimal(datosComp[1]);
					continue;
					// <--

				}

				// 20130711 - EJV -->
				// String[] datosComp = (String[]) htComprobantes.get(key);
				// <--

				salida = clientesMovCliUpdateSaldo(
						new BigDecimal(datosComp[0]), new BigDecimal(
								datosComp[1]).negate(), usuarioalt, idempresa,
						conn);

				if (!salida.equalsIgnoreCase("OK"))
					break;

				salida = clientesCancClieCreate(new BigDecimal(datosComp[0]),
						nrointernomovcliente, new BigDecimal(datosComp[1]),
						new Timestamp(fechamov.getTime()), usuarioalt,
						idempresa, conn);

				if (!salida.equalsIgnoreCase("OK"))
					break;
			}

			if (salida.equalsIgnoreCase("OK")) { // A
				// --- -- Generar movimiento de cliente
				log.info("I.COB.: GEN MOV CL");

				salida = clientesMovCliCreate(
						idcliente,
						new Timestamp(fechamov.getTime()),
						new BigDecimal(0),
						nrocobranza,
						null,
						new BigDecimal(4),
						"COB",
						// 20120911 - EJV - Mantis 869 - Manual -- >
						// new BigDecimal(0),
						saldoCob,
						// <--
						totalCobranza, new BigDecimal(1), new BigDecimal(1),
						null, null, null, nrointernomovcliente, null,
						com_cobra, idcobrador, null, null, null, null, null,
						null, observaciones, condicionletra, usuarioalt,
						idempresa, conn);

				if (salida.equalsIgnoreCase("OK")) { // B

					// --- -- Generar aplicaciones
					log.info("I.COB.: GEN AP");

					salida = cajaAplicaciCreate("COB", nrocobranza,
							nrointernomovcliente, "", new BigDecimal(0),
							usuarioalt, idempresa, conn);

					if (salida.equalsIgnoreCase("OK")) { // C

						/* ========== DETALLE DE INGRESOS ========== */
						// --- -- Generar movimiento de tesoreria
						log.info("I.COB.: GEN MOV TE");
						salida = cajaMovTesoCreate("CLI", nrocobranza,
								new Timestamp(fechamov.getTime()), "COB", "O",
								new BigDecimal(0), null, null, new Timestamp(
										fechamov.getTime()), null,
								new Timestamp(fechamov.getTime()),
								totalCobranza, totalCobranza, null,
								new BigDecimal(1), null, new BigDecimal(1),
								null, null, null, new BigDecimal(
										datosCliente[15]), idcliente,
								new BigDecimal(2), "S", null, null, null,
								usuarioalt, idempresa, conn);

						if (salida.equalsIgnoreCase("OK")) { // D

							en = htIdentificadoresIngesos.keys();
							while (en.hasMoreElements()) {

								// 20100427 - EJV -->
								int diasClear = 0;
								fecha_mt = fechamov;
								fclear_mt = fechamov;
								// <--
								key = (String) en.nextElement();
								String[] datosIngresos = (String[]) htIdentificadoresIngesos
										.get(key);
								/*
								 * if (isFirst) {
								 * 
								 * salida = cajaMovTesoCreate("CLI",
								 * nrocobranza, new
								 * Timestamp(fechamov.getTime()), "COB", "O",
								 * new BigDecimal(0), null, null, new
								 * Timestamp(fechamov.getTime()), null, new
								 * Timestamp(fechamov.getTime()), totalCobranza,
								 * totalCobranza, null, new
								 * BigDecimal(datosIngresos[5]), null, new
								 * BigDecimal(1), null, null, null, new
								 * BigDecimal(datosCliente[15]), idcliente, new
								 * BigDecimal(2), "S", null, null, null,
								 * usuarioalt);
								 * 
								 * isFirst = false; if
								 * (!salida.equalsIgnoreCase("OK")) break; }
								 */

								// TODO: calcular fecha clearing
								if (datosIngresos[6].equalsIgnoreCase("C")) {
									// EJV - 20090512
									// nroint_mt = GeneralBean.getContador(
									// "nrointernocheque", idempresa,
									// dbconn);

									// 20100427 - EJV -->

									if (datosIngresos[31] != null
											&& datosIngresos[31].length() == 10) {

										SimpleDateFormat sdf = new SimpleDateFormat(
												"dd/MM/yyyy");
										java.sql.Date jsd = new java.sql.Date(
												sdf.parse(
														datosIngresos[31]
																.toString()
																.replaceAll(
																		"-",
																		"/"))
														.getTime());
										fecha_mt = jsd;

									}

									nroint_mt = GeneralBean
											.getContador("nrointernocheque",
													idempresa, conn);

									idclearing = new BigDecimal(
											datosIngresos[32]);

									List listClear = getCajaclearingPK(
											idclearing, idempresa);

									if (listClear != null
											&& !listClear.isEmpty()) {

										String[] datosClear = (String[]) listClear
												.get(0);
										diasClear = Integer
												.parseInt(datosClear[2]);

									}

									fclear_mt = GeneralBean.setFechaClearing(
											fecha_mt, diasClear, htFeriados);

									// <--

								} else if (datosIngresos[6]
										.equalsIgnoreCase("D")) {
									// EJV - 20090812
									// nroint_mt = GeneralBean.getContador(
									// "nrointernodocumentos", idempresa,
									// dbconn);
									nroint_mt = GeneralBean.getContador(
											"nrointernodocumentos", idempresa,
											conn);

								} else {
									nroint_mt = new BigDecimal(0);
								}

								BigDecimal nrodoc_mt = datosIngresos[30].trim()
										.equals("") ? null : new BigDecimal(
										datosIngresos[30]);

								salida = cajaMovTesoCreate(datosIngresos[2],
										nrocobranza, new Timestamp(fechamov
												.getTime()), "COB",
										datosIngresos[6], nroint_mt,
										datosIngresos[29], nrodoc_mt,
										new Timestamp(fecha_mt.getTime()),
										new BigDecimal(datosIngresos[32]),
										new Timestamp(fclear_mt.getTime()),
										new BigDecimal(datosIngresos[28]),
										new BigDecimal(datosIngresos[28]),
										null, new BigDecimal(datosIngresos[5]),
										null, new BigDecimal(1), null, null,
										null, new BigDecimal(datosIngresos[4]),
										idcliente, new BigDecimal(1), null,
										null,
										new BigDecimal(datosIngresos[20]),
										new BigDecimal(datosIngresos[21]),
										usuarioalt, idempresa, conn);

								if (!salida.equalsIgnoreCase("OK"))
									break;

								switch (datosIngresos[6].trim().charAt(0)) {
								case 'E':
								case 'G':
									salida = cajaSaldoBcoCreateOrUpdate(
											datosIngresos[2],
											fechamov,
											(new BigDecimal(datosIngresos[28])),
											(new BigDecimal(datosIngresos[28])),
											usuarioalt, idempresa);

									break;
								default:
									break;
								}

								if (!salida.equalsIgnoreCase("OK"))
									break;

							}

							if (salida.equalsIgnoreCase("OK")) { // E

								/* ========== DESCUENTOS CONCEDIDOS ========== */

								en = htIdentificadores.keys();

								// TODO: Esta pendiente el impacto en la entidad
								// CLIENTESCONTCLI, cuando se aplica un
								// descuento,
								// es necesario analizar la logica, que aunque
								// no es
								// muy extensa, posee cierta complicacion.

								while (en.hasMoreElements()) {

									// EJV - 20090512
									// BigDecimal nrointdescuento = GeneralBean
									// .getContador(new BigDecimal(8),
									// idempresa, dbconn);

									BigDecimal nrointdescuento = GeneralBean
											.getContador(
													"nrointernomovcliente",
													idempresa, conn);

									key = (String) en.nextElement();
									String[] datosDescuentos = (String[]) htIdentificadores
											.get(key);

									// --- -- Generar movimiento de cliente
									salida = clientesMovCliCreate(idcliente,
											new Timestamp(fechamov.getTime()),
											new BigDecimal("0"), nrocobranza,
											null, new BigDecimal(3), "DTO",
											new BigDecimal(0), new BigDecimal(
													datosDescuentos[8]),
											new BigDecimal(1),
											new BigDecimal(1), null,
											datosDescuentos[2], null,
											nrointdescuento, null, com_cobra,
											idcobrador, null, null, null, null,
											null, null, observaciones,
											condicionletra, usuarioalt,
											idempresa, conn);

									if (!salida.equalsIgnoreCase("OK"))
										break;

									salida = cajaAplicaciCreate("COB",
											nrocobranza, nrointdescuento, "",
											new BigDecimal(0), usuarioalt,
											idempresa, conn);

									if (!salida.equalsIgnoreCase("OK"))
										break;

									salida = clientesCancClieCreate(
											nrointdescuento,
											nrointernomovcliente,
											new BigDecimal(datosDescuentos[8]),
											new Timestamp(fechamov.getTime()),
											usuarioalt, idempresa, conn);

									if (!salida.equalsIgnoreCase("OK"))
										break;
									// EJV
									// INICIA GENERAR DATOS CONTABLES

									String[] datosIvaCliente = null;
									List listaIvaCliente = getClientesTablaIvaPK(
											new BigDecimal(datosCliente[7]),
											idempresa);

									salida = clientesContCliCreate(
											new BigDecimal(datosCliente[15]),
											new BigDecimal(datosDescuentos[8]),
											"T", nrointdescuento, "", "",
											usuarioalt, idempresa, conn);

									if (!salida.equalsIgnoreCase("OK"))
										break;

									if (!listaIvaCliente.isEmpty()) {

										datosIvaCliente = (String[]) listaIvaCliente
												.get(0);

										if ((new BigDecimal(datosIvaCliente[2]))
												.compareTo(new BigDecimal(0)) < 1) {

											salida = clientesContCliCreate(
													new BigDecimal(
															datosDescuentos[4]),
													new BigDecimal(
															datosDescuentos[8]),
													"E", nrointdescuento, "",
													"", usuarioalt, idempresa,
													conn);

											if (!salida.equalsIgnoreCase("OK"))
												break;

										} else {

											BigDecimal totalGravado = new BigDecimal(
													0);
											try {
												totalGravado = new BigDecimal(
														new BigDecimal(
																datosDescuentos[8])
																.divide(
																		(new BigDecimal(
																				1F + (Float
																						.parseFloat(datosIvaCliente[2]) + Float
																						.parseFloat(datosIvaCliente[5])) / 100F)),
																		2)
																.toString());

											} catch (Exception e) {
												log
														.warn("Calculo total gravado: "
																+ e);
												salida = "Error al generar datos contables descuentos.";
											}
											BigDecimal totalAplicado = new BigDecimal(
													0);
											try {

												salida = clientesContCliCreate(
														new BigDecimal(
																datosDescuentos[4]),
														totalGravado, "G",
														nrointdescuento, "",
														"", usuarioalt,
														idempresa, conn);

											} catch (Exception e) {
												log
														.warn("Calculo total aplicado: "
																+ e);
												salida = "Error al generar datos contables descuentos.";
											}

											if (!salida.equalsIgnoreCase("OK"))
												break;

											totalAplicado = new BigDecimal(
													datosDescuentos[8])
													.subtract(totalGravado);

											if ((new BigDecimal(
													datosIvaCliente[2]))
													.compareTo(new BigDecimal(0)) < 1) {

												totalAplicado = new BigDecimal(
														0);
												try {

													totalAplicado = totalAplicado
															.subtract((totalGravado
																	.multiply(new BigDecimal(
																			datosDescuentos[8])))
																	.divide(
																			new BigDecimal(
																					100),
																			2));
													salida = clientesContCliCreate(
															new BigDecimal(
																	datosDescuentos[9]),
															totalAplicado, "N",
															nrointdescuento,
															"", "", usuarioalt,
															idempresa, conn);

												} catch (Exception e) {
													log
															.warn("Calculo total aplicado (2): "
																	+ e);
													salida = "Error al generar datos contables descuentos.";
												}

												if (!salida
														.equalsIgnoreCase("OK"))
													break;
											}

											salida = clientesContCliCreate(
													new BigDecimal(
															datosDescuentos[10]),
													totalAplicado, "I",
													nrointdescuento, "", "",
													usuarioalt, idempresa, conn);

											if (!salida.equalsIgnoreCase("OK"))
												break;
										}

									} else {
										salida = "No existen datos para el iva asociado al cliente.";
										break;
									}

									// FINALIZA GENERAR DATOS CONTABLES

								}
							}// E
						}// D
					}// C
				}// B
			}// A

			log.info("F.COB.");

		} catch (Exception e) {
			salida = "Error al generar Movimiento de Tesoreria.";
			log.error("cobranzasMovClienteCreate(...): " + e);
		}

		if (!salida.equalsIgnoreCase("OK")) {
			log.warn("cobranzasMovClienteCreate() - Transaccion cancelada: "
					+ salida);
			conn.rollback();
		} else {
			salida = nrocobranza.toString();
			conn.commit();
		}

		conn.setAutoCommit(true);
		conn.close();

		return salida;
	}

	/**
	 * INICIA METODOS PARA GENERAR INGRESOS DIRECTOS NO ASOCIADOS A CLIENTES
	 * TODO: caja - auxteso : no existe en el modelo nuevo.
	 * 
	 * 
	 * 
	 * 
	 * TODO:....................................................................
	 * TODO:....................................................................
	 * TODO:....................................................................
	 * TODO: 20080429 - CONTROLAR ACTUALIZACION DE SALDOS PARA CARTERAS 'T' ....
	 * TODO: TARJETAS DE CREDITO.................getTotalCartera................
	 * TODO:....................................................................
	 * TODO:....................................................................
	 * TODO:....................................................................
	 * TODO:....................................................................
	 * 
	 * 
	 */

	public String cobranzasIngresosDirectosCreate(
			Hashtable htIdentificadoresIngresos,
			Hashtable htIdentificadoresContrapartida,
			Hashtable htMovimientosCancelar, java.sql.Date fechamov,
			String observaciones, String usuarioalt, BigDecimal idempresa)
			throws EJBException, SQLException {
		String salida = "OK";
		Enumeration en = null;
		String key = "";
		BigDecimal nrocobranza = null;
		BigDecimal idcliente = new BigDecimal(0);
		String usado_mt = null;
		BigDecimal tipo_mt = new BigDecimal(1);
		Hashtable htTotalesTarjetas = new Hashtable();

		// 20100427 - EJV -->
		Hashtable htFeriados = new Hashtable();
		java.sql.Date fecha_mt = fechamov;
		java.sql.Date fclear_mt = fechamov;
		BigDecimal idclearing = new BigDecimal(-1);
		// <--

		try {
			log.info("IID");
			dbconn.setAutoCommit(false);

			// 20100427 - EJV -->
			htFeriados = getCajaFeriadosFecha(idempresa);
			// <--

			// EJV - 20090512
			// nrocobranza = GeneralBean.getContador(new BigDecimal(7),
			// idempresa,
			// dbconn);

			nrocobranza = GeneralBean.getContador("nrointernocobranza",
					idempresa, dbconn);

			//
			if (observaciones != null && observaciones.trim().length() > 0)
				salida = cajaMovTesObsCreate("COB", new BigDecimal(0),
						nrocobranza, observaciones, idempresa, usuarioalt);

			//

			if (salida.equalsIgnoreCase("OK")) {

				en = htIdentificadoresIngresos.keys();
				while (en.hasMoreElements() && salida.equalsIgnoreCase("OK")) {

					log.info("ID:I");
					// crear movteso tipo_mt = 1, usado_mt = null
					key = en.nextElement().toString();
					String[] datosIngresos = (String[]) htIdentificadoresIngresos
							.get(key);

					// 20100427 - EJV -->
					int diasClear = 0;
					fecha_mt = fechamov;
					fclear_mt = fechamov;

					if (datosIngresos[6].equalsIgnoreCase("C")) {
						// EJV - 20090512
						// nroint_mt = GeneralBean.getContador(
						// "nrointernocheque", idempresa,
						// dbconn);

						// 20100427 - EJV -->

						if (datosIngresos[31] != null
								&& datosIngresos[31].length() == 10) {

							SimpleDateFormat sdf = new SimpleDateFormat(
									"dd/MM/yyyy");
							java.sql.Date jsd = new java.sql.Date(sdf.parse(
									datosIngresos[31].toString().replaceAll(
											"-", "/")).getTime());
							fecha_mt = jsd;

						}

						idclearing = new BigDecimal(datosIngresos[32]);

						List listClear = getCajaclearingPK(idclearing,
								idempresa);

						if (listClear != null && !listClear.isEmpty()) {

							String[] datosClear = (String[]) listClear.get(0);
							diasClear = Integer.parseInt(datosClear[2]);

						}

						fclear_mt = GeneralBean.setFechaClearing(fecha_mt,
								diasClear, htFeriados);

					}

					// <--

					log.info("ID: M.T");
					salida = cajaMovTesoCreate(datosIngresos[2], nrocobranza,
							new Timestamp(fechamov.getTime()), "COB",
							datosIngresos[6], new BigDecimal(0),
							datosIngresos[29], new BigDecimal(0),
							new Timestamp(fecha_mt.getTime()), new BigDecimal(
									datosIngresos[32]), new Timestamp(fclear_mt
									.getTime()), new BigDecimal(
									datosIngresos[28]), new BigDecimal(
									datosIngresos[28]), null, new BigDecimal(
									datosIngresos[5]), null, new BigDecimal(1),
							null, null, null, new BigDecimal(datosIngresos[4]),
							idcliente, tipo_mt, usado_mt, null, new BigDecimal(
									datosIngresos[20]), new BigDecimal(
									datosIngresos[21]), usuarioalt, idempresa);

					if (!salida.equalsIgnoreCase("OK"))
						break;

					switch (datosIngresos[6].trim().charAt(0)) {
					case 'E':
					case 'G':
						salida = cajaSaldoBcoCreateOrUpdate(datosIngresos[2],
								fechamov, (new BigDecimal(datosIngresos[28])),
								(new BigDecimal(datosIngresos[28])),
								usuarioalt, idempresa);

						break;
					default:
						break;
					}

					if (!salida.equalsIgnoreCase("OK"))
						break;
				}

				// CONTRAPARTIDA.

				tipo_mt = new BigDecimal(2);
				usado_mt = "S";
				en = htIdentificadoresContrapartida.keys();
				while (en.hasMoreElements() && salida.equalsIgnoreCase("OK")) {
					// crear movteso tipo_mt = 2, usado_mt = S
					log.info("ID:CA");
					key = en.nextElement().toString();
					String[] datosIngresos = (String[]) htIdentificadoresContrapartida
							.get(key);

					salida = cajaMovTesoCreate(datosIngresos[2], nrocobranza,
							new Timestamp(fechamov.getTime()), "COB",
							datosIngresos[6], new BigDecimal(0),
							datosIngresos[29], new BigDecimal(0),
							new Timestamp(fechamov.getTime()), new BigDecimal(
									datosIngresos[32]), new Timestamp(fechamov
									.getTime()), new BigDecimal(
									datosIngresos[28]), new BigDecimal(
									datosIngresos[28]), null, new BigDecimal(
									datosIngresos[5]), null, new BigDecimal(1),
							null, null, null, new BigDecimal(datosIngresos[4]),
							idcliente, tipo_mt, usado_mt, null, new BigDecimal(
									datosIngresos[20]), new BigDecimal(
									datosIngresos[21]), usuarioalt, idempresa);

					if (!salida.equalsIgnoreCase("OK"))
						break;

					switch (datosIngresos[6].trim().charAt(0)) {
					case 'G':
						salida = cajaSaldoBcoCreateOrUpdate(datosIngresos[2],
								fechamov, (new BigDecimal(datosIngresos[28]))
										.negate(), (new BigDecimal(
										datosIngresos[28])).negate(),
								usuarioalt, idempresa);
						break;
					default:
						break;
					}

					if (!salida.equalsIgnoreCase("OK"))
						break;
				}

				en = htMovimientosCancelar.keys();
				while (en.hasMoreElements() && salida.equalsIgnoreCase("OK")) {
					log.info("ID:CB");
					key = en.nextElement().toString();
					String[] datosIngresos = (String[]) htMovimientosCancelar
							.get(key);

					switch (datosIngresos[6].trim().charAt(0)) {
					case 'D':

						salida = cajaMovTesoCreate(datosIngresos[2],
								nrocobranza, new Timestamp(fechamov.getTime()),
								"COB", datosIngresos[6], new BigDecimal(0),
								datosIngresos[29], new BigDecimal(0),
								new Timestamp(fechamov.getTime()),
								new BigDecimal(datosIngresos[32]),
								new Timestamp(fechamov.getTime()),
								new BigDecimal(datosIngresos[28]),
								new BigDecimal(datosIngresos[28]), null,
								new BigDecimal(datosIngresos[5]), null,
								new BigDecimal(1), null, null, null,
								new BigDecimal(datosIngresos[4]), idcliente,
								tipo_mt, usado_mt, null, new BigDecimal(
										datosIngresos[20]), new BigDecimal(
										datosIngresos[21]), usuarioalt,
								idempresa);

						if (!salida.equalsIgnoreCase("OK"))
							break;

						salida = cajaSaldaMovimientosTeso(new BigDecimal(key),
								"COB", new BigDecimal(0), nrocobranza,
								usado_mt, null, usuarioalt, idempresa, dbconn);
						break;
					case 'S':

						salida = cajaMovTesoCreate(datosIngresos[2],
								nrocobranza, new Timestamp(fechamov.getTime()),
								"COB", datosIngresos[6], new BigDecimal(0),
								datosIngresos[29], new BigDecimal(0),
								new Timestamp(fechamov.getTime()),
								new BigDecimal(datosIngresos[32]),
								new Timestamp(fechamov.getTime()),
								new BigDecimal(datosIngresos[28]),
								new BigDecimal(datosIngresos[28]), null,
								new BigDecimal(datosIngresos[5]), null,
								new BigDecimal(1), null, null, null,
								new BigDecimal(datosIngresos[4]), idcliente,
								tipo_mt, usado_mt, null, new BigDecimal(
										datosIngresos[20]), new BigDecimal(
										datosIngresos[21]), usuarioalt,
								idempresa);

						if (!salida.equalsIgnoreCase("OK"))
							break;

						salida = cajaSaldaMovimientosTeso(new BigDecimal(key),
								"COB", new BigDecimal(0), nrocobranza,
								usado_mt, null, usuarioalt, idempresa, dbconn);
						break;

					// TODO: 20080429 EJV CONTROLAR getTotalCartera logica
					// aplicada

					case 'T':

						BigDecimal importeactual = getTotalCartera(
								datosIngresos[2], "T", fechamov, idempresa);

						if (importeactual.compareTo(new BigDecimal(0)) < 1
								|| importeactual.subtract(
										new BigDecimal(datosIngresos[28]))
										.compareTo(new BigDecimal(0)) < 0) {

							salida = "Saldo de cartera inexistente o fue modificado desde otra sesion.";
							break;

						}

						salida = cajaMovTesoCreate(datosIngresos[2],
								nrocobranza, new Timestamp(fechamov.getTime()),
								"COB", datosIngresos[6], new BigDecimal(0),
								datosIngresos[29], new BigDecimal(0),
								new Timestamp(fechamov.getTime()),
								new BigDecimal(datosIngresos[32]),
								new Timestamp(fechamov.getTime()),
								new BigDecimal(datosIngresos[28]),
								new BigDecimal(datosIngresos[28]), null,
								new BigDecimal(datosIngresos[5]), null,
								new BigDecimal(1), null, null, null,
								new BigDecimal(datosIngresos[4]), idcliente,
								tipo_mt, usado_mt, null, new BigDecimal(
										datosIngresos[20]), new BigDecimal(
										datosIngresos[21]), usuarioalt,
								idempresa);

						if (!salida.equalsIgnoreCase("OK"))
							break;

						salida = cajaSaldaMovimientosXFechaTeso("COB",
								new BigDecimal(0), nrocobranza, usado_mt,
								datosIngresos[2], "T", fechamov, usuarioalt,
								idempresa);

						if (!salida.equalsIgnoreCase("OK"))
							break;

						if (importeactual.subtract(
								new BigDecimal(datosIngresos[28])).compareTo(
								new BigDecimal(0)) > 0) {
							BigDecimal nroint_mt = new BigDecimal(0);

							salida = cajaMovTesoCreate(datosIngresos[2],
									nrocobranza, new Timestamp(fechamov
											.getTime()), "COB", "T", nroint_mt,
									"TRANSPORTE", null, new Timestamp(fechamov
											.getTime()), new BigDecimal(
											datosIngresos[32]), new Timestamp(
											fechamov.getTime()), importeactual
											.subtract(new BigDecimal(
													datosIngresos[28])),
									importeactual.subtract(new BigDecimal(
											datosIngresos[28])), null,
									new BigDecimal(datosIngresos[5]), null,
									new BigDecimal(1), null, null, null,
									new BigDecimal(datosIngresos[4]),
									new BigDecimal(0), new BigDecimal(2), null,
									null, new BigDecimal(datosIngresos[20]),
									new BigDecimal(datosIngresos[21]),
									usuarioalt, idempresa);
						}

						break;

					/*
					 * case 'G': salida =
					 * cajaSaldoBcoCreateOrUpdate(datosIngresos[2], fechamov,
					 * (new BigDecimal(datosIngresos[28])) .negate(), (new
					 * BigDecimal( datosIngresos[28])).negate(), usuarioalt);
					 * break;
					 */

					default:
						break;
					}
					if (!salida.equalsIgnoreCase("OK"))
						break;
				}

				if (salida.equalsIgnoreCase("OK"))
					salida = cajaAplicaciCreate("COB", nrocobranza,
							new BigDecimal(0), "", new BigDecimal(0),
							usuarioalt, idempresa, dbconn);
			}

			log.info("FID");

		} catch (Exception e) {
			salida = "Error al generar movimiento de ingreso directo.";
			dbconn.rollback();
			log.error("cobranzasIngresosDirectosCreate(): " + e);
		}

		if (!salida.equalsIgnoreCase("OK")) {
			dbconn.rollback();
		} else {
			salida = nrocobranza.toString();
			dbconn.commit();
		}
		dbconn.setAutoCommit(true);
		return salida;
	}

	/**
	 * 
	 * 
	 */

	/**
	 * 20090513 INICIA METODOS PARA ELIMINAR - ANULAR COBRANZAS POR CLIENTES /
	 * ING. DIRECTOS TODO: caja - auxteso : no existe en el modelo nuevo.
	 */

	public String cobranzasCajaMovTesoAnular(BigDecimal sucursal,
			BigDecimal comprobante, java.sql.Timestamp fechateso,
			BigDecimal idcliente, String usuarioact, BigDecimal idempresa)
			throws EJBException, SQLException {

		String salida = "OK";
		String tipomov = "COB";
		String tipo_an = "ID";
		BigDecimal nrointerno = new BigDecimal(-1);
		BigDecimal factorNegPos = new BigDecimal(1);
		BigDecimal totalCobranza = new BigDecimal(0);
		Iterator iterAplicaciones;
		Iterator iterCancelaciones;

		dbconn.setAutoCommit(false);

		try {

			iterAplicaciones = getCajaMovTesoComprobAfectados(sucursal,
					comprobante, idempresa).iterator();

			if (iterAplicaciones.hasNext()) {
				String[] datos = (String[]) iterAplicaciones.next();
				salida = "No es posible anular el comprobante, el mismo tiene "
						+ datos[0] + "-" + datos[1] + "-" + datos[2]
						+ ", aplicado a los valores cobrados.";

			}

			if (salida.equalsIgnoreCase("OK")) {// - A

				salida = cajaMovTesoAnulaSaldadosUpdate(tipomov, sucursal,
						comprobante, idempresa, usuarioact);

				if (salida.equalsIgnoreCase("OK")) { // - B

					iterAplicaciones = getCajaAplicaciPK(tipomov, sucursal,
							comprobante, idempresa).iterator();

					// TODO: Buscar comprobante en cajamovteso que esten
					// saldados
					// con
					// este ...

					if (idcliente.longValue() > 0) { // - B1

						tipo_an = "CLI";
						factorNegPos = factorNegPos.negate();

						// Deberia iterar solo una vez ...
						while (iterAplicaciones.hasNext()) { // - B1.1

							String[] datos = (String[]) iterAplicaciones.next();
							nrointerno = new BigDecimal(datos[0]);

							iterCancelaciones = getComprobantesAplicadosClientes(
									nrointerno, idempresa).iterator();

							// Actualizando FA's aplicadas por el comprobante de
							// conbranza.
							while (iterCancelaciones.hasNext()) { // - B1.2
								String[] datosCanc = (String[]) iterCancelaciones
										.next();
								BigDecimal saldo = new BigDecimal(datosCanc[0]);
								BigDecimal nrointerno_canc = new BigDecimal(
										datosCanc[1]);

								salida = clientesMovCliUpdateSaldo(
										nrointerno_canc, saldo, usuarioact,
										idempresa, dbconn);

								if (!salida.equalsIgnoreCase("OK"))
									break;
								// Eliminar CLIENTESCANCCLI
								salida = clientesCancClieDelete(
										nrointerno_canc, nrointerno, idempresa);

								if (!salida.equalsIgnoreCase("OK"))
									break;

							} // - B1.2

							iterCancelaciones = getComprobantesQueAplicanClientes(
									nrointerno, idempresa).iterator();

							while (iterCancelaciones.hasNext()
									&& salida.equalsIgnoreCase("OK")) { // -
								// B1.3

								String[] datosCanc = (String[]) iterCancelaciones
										.next();
								BigDecimal saldo = new BigDecimal(datosCanc[0]);
								BigDecimal nrointerno_q_canc = new BigDecimal(
										datosCanc[1]);

								salida = clientesMovCliUpdateSaldo(
										nrointerno_q_canc, saldo, usuarioact,
										idempresa, dbconn);

								if (!salida.equalsIgnoreCase("OK"))
									break;
								// Eliminar CLIENTESCANCCLI
								salida = clientesCancClieDelete(nrointerno,
										nrointerno_q_canc, idempresa);

							} // - B1.3

							if (!salida.equalsIgnoreCase("OK"))
								break;

							// Eliminar cobranza de clientes (CLIENTESMOVCLI)
							salida = clientesMovCliDelete(idcliente,
									nrointerno, idempresa);

							if (!salida.equalsIgnoreCase("OK"))
								break;

						} // - B1.1

					} // - B1

					// Eliminar CAJAAPLICACI

					if (salida.equalsIgnoreCase("OK")) { // - B2

						salida = cajaAplicaciDelete(tipomov, sucursal,
								comprobante, idempresa);

						if (salida.equalsIgnoreCase("OK")) { // - B2.1

							Iterator iter = getCajaMovTesoXComprobante(
							// 20120716- EJV-Mantis 858-->
									tipomov,
									// <--
									sucursal, comprobante, idempresa)
									.iterator();

							// Eliminar CAJAAMOVTESO ()
							salida = cajaMovTesoXComprobanteDelete(
							// 20120716 - EJV - Mantis 858 -->
									tipomov,
									// <--
									sucursal, comprobante, idempresa);

							while (iter.hasNext()
									&& salida.equalsIgnoreCase("OK")) { // -
								// B2.2

								String[] datosMovTeso = (String[]) iter.next();
								char tipcart_mt = datosMovTeso[5].charAt(0);
								int tipo_mt = Integer
										.parseInt(datosMovTeso[23]);
								// BigDecimal idmovteso = new
								// BigDecimal(datosMovTeso[0]);
								BigDecimal importe_mt = new BigDecimal(
										datosMovTeso[13]);
								String banco = datosMovTeso[1];
								java.sql.Date fechamo_mt = java.sql.Date
										.valueOf(datosMovTeso[3]);

								if (tipo_mt == 1)
									totalCobranza = totalCobranza
											.add(importe_mt);

								switch (tipcart_mt) {
								case 'E':
								case 'G':
									salida = cajaSaldoBcoCreateOrUpdate(banco,
											fechamo_mt, importe_mt
													.multiply(factorNegPos),
											importe_mt.multiply(factorNegPos),
											usuarioact, idempresa);

									break;
								default:
									continue;
								}

								if (!salida.equalsIgnoreCase("OK"))
									break;

							} // - B2.2

						} // - B2.1

					}// - B2

				}// - B

			}// - A

			if (salida.equalsIgnoreCase("OK")) {
				salida = cajaAnuladasCreate(tipomov, comprobante, fechateso,
						totalCobranza, sucursal, idcliente, null, tipo_an,
						idempresa, usuarioact);
			}

		} catch (Exception e) {
			salida = "Se produjo una excepcion al intentar eliminar cobranza.";
			log.error("cobranzasCajaMovTesoAnular (): " + e);
		}

		if (salida.equalsIgnoreCase("OK")) {
			dbconn.commit();
		} else {
			dbconn.rollback();
		}

		dbconn.setAutoCommit(true);

		return salida;

	}

	/**
	 * INICIA METODOS PARA GENERAR OTROS MOVIMIENTOS 20090810 - EJV
	 * 
	 */

	public String cajaOtrosMovimientosCreate(BigDecimal idproveedor,
			BigDecimal totalMovimiento, Hashtable htIdentificadoresIngresos,
			Hashtable htOMDocTercerosCancelarIN,
			Hashtable htIdentificadoresContrapartida,
			Hashtable htMovimientosCancelar, String tipodoc,
			java.sql.Date fechamov, String observaciones, String usuarioalt,
			BigDecimal idempresa) throws EJBException, SQLException {
		String salida = "OK";
		Enumeration en = null;
		String key = "";
		BigDecimal nrointdeb = null;
		BigDecimal idcliente = new BigDecimal(0);

		String usado_mt = null;
		String tipcart_mt = "";
		BigDecimal nroint_mt = null;
		BigDecimal nrodoc_mt = null;
		String tipomov_mt = "OTR";

		BigDecimal tipo_mt = new BigDecimal(1);
		// Hashtable htTotalesTarjetas = new Hashtable();

		try {

			log.info("OI");
			dbconn.setAutoCommit(false);

			nrointdeb = GeneralBean.getContador("nrointdeb", idempresa, dbconn);
			log.info("nrointdeb: " + nrointdeb);

			//
			if (observaciones != null && observaciones.trim().length() > 0)
				salida = cajaMovTesObsCreate(tipomov_mt, new BigDecimal(0),
						nrointdeb, observaciones, idempresa, usuarioalt);

			//

			if (salida.equalsIgnoreCase("OK")) {

				if ((htOMDocTercerosCancelarIN == null || htOMDocTercerosCancelarIN
						.isEmpty())
						&& tipodoc.equals("")) {

					// -------------->

					en = htIdentificadoresIngresos.keys();
					while (en.hasMoreElements()
							&& salida.equalsIgnoreCase("OK")) {
						log.info("IDENTIFICADORES DE ENTRADA.");
						key = en.nextElement().toString();
						String[] datosIngresos = (String[]) htIdentificadoresIngresos
								.get(key);
						nroint_mt = new BigDecimal(0);

						log.info("ID: M.T");

						switch (datosIngresos[6].trim().charAt(0)) {
						case 'C':

							if (datosIngresos[7].equalsIgnoreCase("S")) {

								salida = cajaSaldoBcoCreateOrUpdate(
										datosIngresos[2], fechamov,
										(new BigDecimal(datosIngresos[28])),
										(new BigDecimal(datosIngresos[28])),
										usuarioalt, idempresa);

								tipcart_mt = "B";
								usado_mt = "S";

							} else if (datosIngresos[7].equalsIgnoreCase("N")) {

								tipcart_mt = "B";
								usado_mt = null;
								nroint_mt = GeneralBean.getContador(
										"nrointernocheque", idempresa, dbconn);

							}

							break;
						case 'E':
						case 'G':

							salida = cajaSaldoBcoCreateOrUpdate(
									datosIngresos[2], fechamov,
									(new BigDecimal(datosIngresos[28])),
									(new BigDecimal(datosIngresos[28])),
									usuarioalt, idempresa);
							tipcart_mt = datosIngresos[6];
							usado_mt = "S";

							break;

						case 'S':

							tipcart_mt = datosIngresos[6];
							usado_mt = null;
							nroint_mt = GeneralBean.getContador(
									"nrointernosaldos", idempresa, dbconn);

							break;

						default:

							tipcart_mt = datosIngresos[6];
							usado_mt = "S";

							break;
						}

						if (!salida.equalsIgnoreCase("OK"))
							break;

						salida = cajaMovTesoCreate(datosIngresos[2], nrointdeb,
								new Timestamp(fechamov.getTime()), tipomov_mt,
								tipcart_mt, nroint_mt, datosIngresos[29],
								new BigDecimal(0), new Timestamp(fechamov
										.getTime()), new BigDecimal(
										datosIngresos[32]), new Timestamp(
										fechamov.getTime()), new BigDecimal(
										datosIngresos[28]), new BigDecimal(
										datosIngresos[28]), null,
								new BigDecimal(datosIngresos[5]), null,
								new BigDecimal(1), null, null, null,
								new BigDecimal(datosIngresos[4]), idcliente,
								tipo_mt, usado_mt, null, new BigDecimal(
										datosIngresos[20]), new BigDecimal(
										datosIngresos[21]), usuarioalt,
								idempresa);

						if (!salida.equalsIgnoreCase("OK"))
							break;

					}

					// <-------------------| Hasta aca esta OK

					// CONTRAPARTIDA.

					log.info("ANTES DE CONTRAPARTIDA.");
					tipo_mt = new BigDecimal(2);
					usado_mt = "S";
					en = htIdentificadoresContrapartida.keys();
					while (en.hasMoreElements()
							&& salida.equalsIgnoreCase("OK")) {
						// crear movteso tipo_mt = 2, usado_mt = S
						log.info("DENTRO DE CONTRAPARTIDA");
						key = en.nextElement().toString();
						String[] datosIngresos = (String[]) htIdentificadoresContrapartida
								.get(key);

						tipcart_mt = datosIngresos[6];
						usado_mt = datosIngresos[7];
						nrodoc_mt = new BigDecimal(0);

						switch (datosIngresos[6].trim().charAt(0)) {

						case 'C':

							if (datosIngresos[7].equalsIgnoreCase("S")) {

								salida = cajaSaldoBcoCreateOrUpdate(
										datosIngresos[2], fechamov,
										(new BigDecimal(datosIngresos[28]))
												.negate(), (new BigDecimal(
												datosIngresos[28])),
										usuarioalt, idempresa);

								tipcart_mt = "P";
								usado_mt = "D";
								nrodoc_mt = new BigDecimal(datosIngresos[30]);

								// TODO: es necesario relevar MARCA_CHEQ
								// TODO: ... Si se paga con cheque a la fecha,
								// es necesario actualizar el saldo del banco.

							} else
								salida = "Identificador no corresponde al tipo (Banco propio).";

							break;

						case 'G':
						case 'E':

							salida = cajaSaldoBcoCreateOrUpdate(
									datosIngresos[2], fechamov,
									(new BigDecimal(datosIngresos[28]))
											.negate(), (new BigDecimal(
											datosIngresos[28])).negate(),
									usuarioalt, idempresa);
							break;

						case 'S':

							if (datosIngresos[7].equalsIgnoreCase("S")) {

								tipcart_mt = "M";
								usado_mt = null;

							} else
								salida = "Identificador no corresponde al tipo (Documentos de saldos propios)";

							break;

						default:
							break;
						}

						if (!salida.equalsIgnoreCase("OK"))
							break;

						salida = cajaMovTesoCreate(datosIngresos[2], nrointdeb,
								new Timestamp(fechamov.getTime()), tipomov_mt,
								tipcart_mt, nroint_mt, datosIngresos[29],
								nrodoc_mt, new Timestamp(fechamov.getTime()),
								new BigDecimal(datosIngresos[32]),
								new Timestamp(fechamov.getTime()),
								new BigDecimal(datosIngresos[28]),
								new BigDecimal(datosIngresos[28]), null,
								new BigDecimal(datosIngresos[5]), null,
								new BigDecimal(1), null, null, null,
								new BigDecimal(datosIngresos[4]), idcliente,
								tipo_mt, usado_mt, null, new BigDecimal(
										datosIngresos[20]), new BigDecimal(
										datosIngresos[21]), usuarioalt,
								idempresa);

						if (!salida.equalsIgnoreCase("OK"))
							break;

					}

					en = htMovimientosCancelar.keys();
					while (en.hasMoreElements()
							&& salida.equalsIgnoreCase("OK")) {
						log.info("ID:CB");
						key = en.nextElement().toString();
						String[] datosIngresos = (String[]) htMovimientosCancelar
								.get(key);

						tipcart_mt = datosIngresos[6];
						nroint_mt = new BigDecimal(0);
						nrodoc_mt = new BigDecimal(0);

						switch (datosIngresos[6].trim().charAt(0)) {

						case 'C':

							nrodoc_mt = new BigDecimal(datosIngresos[30]);
							salida = cajaSaldaMovimientosTeso(new BigDecimal(
									key), tipomov_mt, new BigDecimal(0),
									nrointdeb, "S", null, usuarioalt,
									idempresa, dbconn);

							break;

						case 'S':

							salida = cajaSaldaMovimientosTeso(new BigDecimal(
									key), tipomov_mt, new BigDecimal(0),
									nrointdeb, "S", null, usuarioalt,
									idempresa, dbconn);
							break;

						case 'D':
							// TODO: 20090811 - EJV - PENDIENTE, NO ES ESTABLE
							// FUNCIONAMIENTO DETECTADO Y EN BACO PRACTICAMENTE
							// NO APLICA ... [ Al menos hasta ahora ;~) ]

							salida = cajaSaldaMovimientosTeso(new BigDecimal(
									key), tipomov_mt, new BigDecimal(0),
									nrointdeb, "L", null, usuarioalt,
									idempresa, dbconn);

							break;

						case 'T':

							// TODO: 20090810 - EJV / SUMMA Se maneja de forma
							// inestable ... en principio no contemplo esta
							// condicion, hasta ver funcionamiento en forma mas
							// detallada.

							break;

						default:
							break;
						}

						if (!salida.equalsIgnoreCase("OK"))
							break;

						if (tipcart_mt.equalsIgnoreCase("P")) {
							// TODO: igualar todas las fechas a como Fecha de
							// Tesoreria.
						}

						idcliente = new BigDecimal(0);

						salida = cajaMovTesoCreate(datosIngresos[2], nrointdeb,
								new Timestamp(fechamov.getTime()), tipomov_mt,
								datosIngresos[6], nroint_mt, datosIngresos[29],
								nrodoc_mt, new Timestamp(fechamov.getTime()),
								new BigDecimal(datosIngresos[32]),
								new Timestamp(fechamov.getTime()),
								new BigDecimal(datosIngresos[28]),
								new BigDecimal(datosIngresos[28]), null,
								new BigDecimal(datosIngresos[5]), null,
								new BigDecimal(1), null, null, null,
								new BigDecimal(datosIngresos[4]), idcliente,
								tipo_mt, usado_mt, null, new BigDecimal(
										datosIngresos[20]), new BigDecimal(
										datosIngresos[21]), usuarioalt,
								idempresa);

						if (!salida.equalsIgnoreCase("OK"))
							break;

					}

				} else if ((htOMDocTercerosCancelarIN != null && !htOMDocTercerosCancelarIN
						.isEmpty())
						&& !tipodoc.equals("")) {
					// .... Aca es cancelacion de documentos de terceros ...
					// ENDOSADOS o DESCONTADOS

					log.info("DOCUMENTOS ");

					if (tipodoc.equalsIgnoreCase("D")) {
						// TODO:DESCONTADOS... Por ahora queda pendiente.
						log.info("DOCUMENTOS DESCONTADOS ");

					} else if (tipodoc.equalsIgnoreCase("E")) {
						// ENDOSADOS
						log.info("DOCUMENTOS ENDOSADOS ");
						// -----------------
						// -----------------

						// TODO: 20090810 - EJV - El metodo deber�a devolver
						// la
						// CTADOCUMEN, esta se asigna en los pagos a proveedores
						// [cuando se elige un identificador de terceros como
						// pago (endoso)],
						// esto no se esta haciendo.
						BigDecimal ctadocumen = getCtaDocumProv(idproveedor,
								idempresa);
						usado_mt = "A";
						tipo_mt = new BigDecimal(1);
						tipcart_mt = "D";
						salida = cajaMovTesoCreate("PRO", nrointdeb,
								new Timestamp(fechamov.getTime()), tipomov_mt,
								tipcart_mt, new BigDecimal(0), "",
								new BigDecimal(0), new Timestamp(fechamov
										.getTime()), new BigDecimal(0),
								new Timestamp(fechamov.getTime()),
								totalMovimiento, totalMovimiento, null,
								new BigDecimal(1), null, new BigDecimal(1),
								null, null, null, ctadocumen, idcliente,
								tipo_mt, usado_mt, null, new BigDecimal(0),
								new BigDecimal(0), usuarioalt, idempresa);

						en = htOMDocTercerosCancelarIN.keys();
						while (en.hasMoreElements()
								&& salida.equalsIgnoreCase("OK")) {
							log.info("ID:CB");
							tipo_mt = new BigDecimal(2);
							key = en.nextElement().toString();
							String[] datosIngresos = (String[]) htOMDocTercerosCancelarIN
									.get(key);

							tipcart_mt = datosIngresos[6];

							salida = cajaSaldaMovimientosTeso(new BigDecimal(
									key), tipomov_mt, new BigDecimal(0),
									nrointdeb, "S", null, usuarioalt,
									idempresa, dbconn);

							if (!salida.equalsIgnoreCase("OK"))
								break;

							idcliente = new BigDecimal(0);
							salida = cajaMovTesoCreate(datosIngresos[2],
									nrointdeb,
									new Timestamp(fechamov.getTime()),
									tipomov_mt, datosIngresos[6],
									new BigDecimal(datosIngresos[30]),
									datosIngresos[29], new BigDecimal(0),
									new Timestamp(fechamov.getTime()),
									new BigDecimal(datosIngresos[32]),
									new Timestamp(fechamov.getTime()),
									new BigDecimal(datosIngresos[28]),
									new BigDecimal(datosIngresos[28]), null,
									new BigDecimal(datosIngresos[5]), null,
									new BigDecimal(1), null, null, null,
									new BigDecimal(datosIngresos[4]),
									idcliente, tipo_mt, usado_mt, null,
									new BigDecimal(datosIngresos[20]),
									new BigDecimal(datosIngresos[21]),
									usuarioalt, idempresa);

							if (!salida.equalsIgnoreCase("OK"))
								break;

						}

						// -----------------
						// -----------------

					}

				} else {

					salida = "Error: Operaci�n de Otros Movimientos inconsistente.";

				}

			}

			log.info("FID");

		} catch (Exception e) {
			salida = "Error al generar otros movimientos.";
			log.error("cajaOtrosMovimientosCreate(): " + e);
		}

		if (!salida.equalsIgnoreCase("OK")) {
			dbconn.rollback();
		} else {
			salida = nrointdeb.toString();
			dbconn.commit();
		}
		dbconn.setAutoCommit(true);
		return salida;
	}

	public BigDecimal getCtaDocumProv(BigDecimal idproveedor,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		BigDecimal ctadocumen = new BigDecimal(0);
		String cQuery = ""
				+ "SELECT CASE WHEN COALESCE(ctadocumen, 0) = 0 THEN ctapasivo ELSE ctadocumen END AS ctadocumen  "
				+ "  FROM proveedoproveed pr  " + " WHERE pr.idproveedor="
				+ idproveedor.toString() + "   AND pr.idempresa = "
				+ idempresa.toString();

		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);

			if (rsSalida != null && rsSalida.next()) {
				ctadocumen = rsSalida.getBigDecimal(1);
			} else {
				log
						.warn("getCtaDocumProv()- Error al recuperar cuenta documentos proveedor: "
								+ idproveedor);
			}

		} catch (SQLException sqlException) {
			log
					.error("Error SQL en el metodo : getCtaDocumProv( BigDecimal idproveedor ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCtaDocumProv( BigDecimal idproveedor )  "
							+ ex);
		}
		return ctadocumen;
	}

	/*
	 * Recupera comprobante e importe para documentos aplicados por uno en
	 * perticular.
	 */

	public List getComprobantesAplicadosClientes(BigDecimal nrointerno,
			BigDecimal idempresa) throws EJBException {

		String cQuery = "                  "
				+ "SELECT importe, comp_canc  FROM clientescancclie  WHERE comp_q_can ="
				+ nrointerno.toString() + " AND idempresa="
				+ idempresa.toString() + " ORDER BY 2   ";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/*
	 * Recupera comprobante e importes que aplican a uno en perticular.
	 */

	public List getComprobantesQueAplicanClientes(BigDecimal nrointerno,
			BigDecimal idempresa) throws EJBException {

		String cQuery = "SELECT importe, comp_q_can FROM clientescancclie WHERE comp_canc = "
				+ nrointerno.toString()
				+ " AND idempresa="
				+ idempresa.toString() + " ORDER BY 2   ";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * 
	 * 
	 */

	public String[] getIdentificadorTipoPropietario(String identificador,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String[] salida = new String[] { "", "" };

		String cQuery = ""
				+ "SELECT ti.tipomov, ti.propio "
				+ "   FROM cajatipoidentificadores ti "
				+ "     INNER JOIN cajaidentificadores i ON ti.idtipoidentificador = i.idtipoidentificador "
				+ "            AND ti.idempresa = i.idempresa AND UPPER(i.identificador)='"
				+ identificador.toUpperCase() + "' AND i.idempresa="
				+ idempresa.toString();

		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);

			if (rsSalida != null && rsSalida.next()) {

				salida = new String[] { rsSalida.getString(1),
						rsSalida.getString(2) };

			}
		} catch (SQLException sqlException) {
			log
					.error("Error SQL en el metodo : getIdentificadorTipoPropietario( ... ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getIdentificadorTipoPropietario( ... )  "
							+ ex);
		}
		return salida;
	}

	/**
	 * Metodos para la entidad: cajaMovTeso Copyrigth(r) sysWarp S.R.L. Fecha de
	 * creacion: Thu Dec 28 16:28:32 GMT-03:00 2006
	 */

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt
	// 20120921 - EJV - Sobrecarga -->
	public String cajaMovTesoCreate(String cartera_mt, BigDecimal comprob_mt,
			Timestamp fechamo_mt, String tipomov_mt, String tipcart_mt,
			BigDecimal nroint_mt, String detalle_mt, BigDecimal nrodoc_mt,
			Timestamp fecha_mt, BigDecimal idclearing, Timestamp fclear_mt,
			BigDecimal impori_mt, BigDecimal importe_mt, String deposi_mt,
			BigDecimal idmoneda, String unamone_mt, BigDecimal cambio_mt,
			String movsal_mt, BigDecimal sucsal_mt, BigDecimal nrosal_mt,
			BigDecimal cuenta_mt, BigDecimal idcliente, BigDecimal tipo_mt,
			String usado_mt, String anulada_mt, BigDecimal idcencosto,
			BigDecimal idcencosto1, String usuarioalt, BigDecimal idempresa)
			throws EJBException {

		String salida = "OK";

		try {

			salida = cajaMovTesoCreate(cartera_mt, comprob_mt, fechamo_mt,
					tipomov_mt, tipcart_mt, nroint_mt, detalle_mt, nrodoc_mt,
					fecha_mt, idclearing, fclear_mt, impori_mt, importe_mt,
					deposi_mt, idmoneda, unamone_mt, cambio_mt, movsal_mt,
					sucsal_mt, nrosal_mt, cuenta_mt, idcliente, tipo_mt,
					usado_mt, anulada_mt, idcencosto, idcencosto1, usuarioalt,
					idempresa, dbconn);

		} catch (Exception e) {

			log.error("cajaMovTesoCreate(sobrecarga): " + e);

		}

		return salida;
	}

	// <--

	public String cajaMovTesoCreate(String cartera_mt, BigDecimal comprob_mt,
			Timestamp fechamo_mt, String tipomov_mt, String tipcart_mt,
			BigDecimal nroint_mt, String detalle_mt, BigDecimal nrodoc_mt,
			Timestamp fecha_mt, BigDecimal idclearing, Timestamp fclear_mt,
			BigDecimal impori_mt, BigDecimal importe_mt, String deposi_mt,
			BigDecimal idmoneda, String unamone_mt, BigDecimal cambio_mt,
			String movsal_mt, BigDecimal sucsal_mt, BigDecimal nrosal_mt,
			BigDecimal cuenta_mt, BigDecimal idcliente, BigDecimal tipo_mt,
			String usado_mt, String anulada_mt, BigDecimal idcencosto,
			BigDecimal idcencosto1, String usuarioalt, BigDecimal idempresa,
			Connection conn) throws EJBException {
		String salida = "OK";
		try {
			// validaciones de datos:
			// 1. nulidad de campos
			if (cartera_mt == null)
				salida = "Error: No se puede dejar sin datos (nulo) el campo: cartera_mt ";
			if (comprob_mt == null)
				salida = "Error: No se puede dejar sin datos (nulo) el campo: comprob_mt ";
			if (fechamo_mt == null)
				salida = "Error: No se puede dejar sin datos (nulo) el campo: fechamo_mt ";
			if (tipomov_mt == null)
				salida = "Error: No se puede dejar sin datos (nulo) el campo: tipomov_mt ";
			if (tipcart_mt == null)
				salida = "Error: No se puede dejar sin datos (nulo) el campo: tipcart_mt ";
			if (nroint_mt == null)
				salida = "Error: No se puede dejar sin datos (nulo) el campo: nroint_mt ";
			if (fecha_mt == null)
				salida = "Error: No se puede dejar sin datos (nulo) el campo: fecha_mt ";
			if (fclear_mt == null)
				salida = "Error: No se puede dejar sin datos (nulo) el campo: fclear_mt ";
			if (impori_mt == null)
				salida = "Error: No se puede dejar sin datos (nulo) el campo: impori_mt ";
			if (importe_mt == null)
				salida = "Error: No se puede dejar sin datos (nulo) el campo: importe_mt ";
			if (idmoneda == null)
				salida = "Error: No se puede dejar sin datos (nulo) el campo: idmoneda ";
			if (cuenta_mt == null)
				salida = "Error: No se puede dejar sin datos (nulo) el campo: cuenta_mt ";

			if (!ContableBean.isCuentaActiva(cuenta_mt, conn, idempresa))
				salida = "Error: La cuenta " + cuenta_mt
						+ " asociada a la cartera " + cartera_mt
						+ ",  no pertenece al ejercicio activo. ";

			if (usuarioalt == null)
				salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
			// 2. sin nada desde la pagina
			if (cartera_mt.equalsIgnoreCase(""))
				salida = "Error: No se puede dejar vacio el campo: cartera_mt ";
			if (tipomov_mt.equalsIgnoreCase(""))
				salida = "Error: No se puede dejar vacio el campo: tipomov_mt ";
			if (tipcart_mt.equalsIgnoreCase(""))
				salida = "Error: No se puede dejar vacio el campo: tipcart_mt ";
			if (usuarioalt.equalsIgnoreCase(""))
				salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

			if (salida.equalsIgnoreCase("OK")) {
				String ins = "INSERT INTO CAJAMOVTESO"
						+ "(cartera_mt, comprob_mt, fechamo_mt, tipomov_mt, tipcart_mt, nroint_mt, detalle_mt, "
						+ "  nrodoc_mt, fecha_mt, idclearing, fclear_mt, impori_mt, importe_mt, deposi_mt, idmoneda,"
						+ "   unamone_mt, cambio_mt, movsal_mt, sucsal_mt, nrosal_mt, cuenta_mt, idcliente, tipo_mt, "
						+ "  usado_mt, anulada_mt, idcencosto, idcencosto1, usuarioalt, idempresa ) "
						+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = conn.prepareStatement(ins);
				// seteo de campos:

				insert.setString(1, cartera_mt);
				insert.setBigDecimal(2, comprob_mt);
				insert.setTimestamp(3, fechamo_mt);
				insert.setString(4, tipomov_mt);
				insert.setString(5, tipcart_mt);
				insert.setBigDecimal(6, nroint_mt);
				insert.setString(7, detalle_mt);
				insert.setBigDecimal(8, nrodoc_mt);
				insert.setTimestamp(9, fecha_mt);
				insert.setBigDecimal(10, idclearing);
				insert.setTimestamp(11, fclear_mt);
				insert.setBigDecimal(12, impori_mt);
				insert.setBigDecimal(13, importe_mt);
				insert.setString(14, deposi_mt);
				insert.setBigDecimal(15, idmoneda);
				insert.setString(16, unamone_mt);
				insert.setBigDecimal(17, cambio_mt);
				insert.setString(18, movsal_mt);
				insert.setBigDecimal(19, sucsal_mt);
				insert.setBigDecimal(20, nrosal_mt);
				insert.setBigDecimal(21, cuenta_mt);
				insert.setBigDecimal(22, idcliente);
				insert.setBigDecimal(23, tipo_mt);
				insert.setString(24, usado_mt);
				insert.setString(25, anulada_mt);
				insert.setBigDecimal(26, idcencosto);
				insert.setBigDecimal(27, idcencosto1);
				insert.setString(28, usuarioalt);
				insert.setBigDecimal(29, idempresa);
				int n = insert.executeUpdate();
				if (n != 1) {
					salida = "Imposible crear movimiento de tesoreria.";
				}
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String cajaMovTesoCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error excepcion public String cajaMovTesoCreate(.....)"
					+ ex);
		}
		return salida;
	}

	// Recuperar registro por primary key

	// EJV 20090514
	public List getCajaMovTesoPK(BigDecimal idmovteso, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT idmovteso,cartera_mt,comprob_mt,fechamo_mt,tipomov_mt,tipcart_mt,nroint_mt,"
				+ "       detalle_mt,nrodoc_mt,fecha_mt,idclearing,fclear_mt,impori_mt,importe_mt,"
				+ "       deposi_mt,idmoneda,unamone_mt,cambio_mt,movsal_mt,sucsal_mt,nrosal_mt,cuenta_mt,"
				+ "       idcliente,tipo_mt,usado_mt,anulada_mt,idcencosto,idcencosto1,sucursa_mt,"
				+ "       idempresa,usuarioalt,usuarioact,fechaalt,fechaact"
				+ "  FROM CAJAMOVTESO WHERE idmovteso=" + idmovteso.toString()
				+ " AND idempresa = " + idempresa.toString() + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// EJV 20090514
	public List getCajaMovTesoXComprobante(String tipomov_mt,
			BigDecimal sucursa_mt, BigDecimal comprob_mt, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT idmovteso,cartera_mt,comprob_mt,fechamo_mt::date,tipomov_mt,tipcart_mt,nroint_mt,"
				+ "       detalle_mt,nrodoc_mt,fecha_mt::date,idclearing,fclear_mt::date,impori_mt,importe_mt,"
				+ "       deposi_mt,idmoneda,unamone_mt,cambio_mt,movsal_mt,sucsal_mt,nrosal_mt,cuenta_mt,"
				+ "       idcliente,tipo_mt,usado_mt,anulada_mt,idcencosto,idcencosto1,sucursa_mt,"
				+ "       idempresa,usuarioalt,usuarioact,fechaalt,fechaact"
				+ "  FROM cajamovteso WHERE sucursa_mt ="
				+ sucursa_mt.toString() + " AND comprob_mt = "
				+ comprob_mt.toString()

				// 20120716 - EJV - Mantis 858 -->
				+ " AND tipomov_mt='" + tipomov_mt
				// <--
				+ "' AND idempresa = " + idempresa.toString() + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// EJV 20090520
	public List getCajaMovTesoXComprobanteIdent(String tipomov_mt,
			BigDecimal sucursa_mt, BigDecimal comprob_mt, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT mt.idmovteso, mt.cartera_mt, mt.comprob_mt, mt.fechamo_mt::date, mt.tipomov_mt, mt.tipcart_mt, mt.nroint_mt, "
				+ "       mt.detalle_mt, mt.nrodoc_mt, mt.fecha_mt::date, mt.idclearing, mt.fclear_mt::date, mt.impori_mt, mt.importe_mt,  "
				+ "       mt.deposi_mt, mt.idmoneda, mt.unamone_mt, mt.cambio_mt, mt.movsal_mt, mt.sucsal_mt, mt.nrosal_mt, mt.cuenta_mt, "
				+ "       mt.idcliente, mt.tipo_mt, mt.usado_mt, mt.anulada_mt, mt.idcencosto, mt.idcencosto1, mt.sucursa_mt,COALESCE(id.factura, 'N') AS factura, "
				+ "       mt.idempresa, mt.usuarioalt, mt.usuarioact, mt.fechaalt, mt.fechaact "
				+ "  FROM cajamovteso mt "
				+ "       LEFT JOIN cajaidentificadores id ON mt.cartera_mt = id.identificador AND mt.idempresa = id.idempresa "
				+ " WHERE mt.sucursa_mt = " + sucursa_mt.toString()
				+ "   AND mt.comprob_mt = " + comprob_mt.toString()
				// 20120716 - EJV - Mantis 858 -->
				+ " AND tipomov_mt='" + tipomov_mt
				// <--
				+ "'   AND mt.idempresa = " + idempresa.toString() + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// EJV 20090514
	public List getCajaMovTesoComprobAfectados(BigDecimal sucursa_mt,
			BigDecimal comprob_mt, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT movsal_mt,LPAD(sucsal_mt::VARCHAR, 4, '0'),LPAD(nrosal_mt::VARCHAR, 8, '0') "
				+ "  FROM cajamovteso WHERE sucursa_mt ="
				+ sucursa_mt.toString() + " AND comprob_mt = "
				+ comprob_mt.toString() + " AND idempresa = "
				+ idempresa.toString()
				+ "  AND nrosal_mt > 0 AND nrosal_mt IS NOT NULL ;";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// EJV 20090514
	public String cajaMovTesoXComprobanteDelete(
	// 20120716 - EJV - Mantis 858 -->
			String tipomov_mt,
			// <--
			BigDecimal sucursa_mt, BigDecimal comprob_mt, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM CAJAMOVTESO WHERE sucursa_mt="
				+ sucursa_mt.toString() + " AND comprob_mt="
				+ comprob_mt.toString()
				// 20120716 - EJV - Mantis 858 -->
				+ " AND tipomov_mt='" + tipomov_mt
				// <--
				+ "' AND idempresa=" + idempresa.toString();
		String salida = "OK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM CAJAMOVTESO WHERE sucursa_mt="
						+ sucursa_mt.toString().toString() + " AND comprob_mt="
						+ comprob_mt.toString()
						// 20120716 - EJV - Mantis 858 -->
						+ " AND tipomov_mt='" + tipomov_mt
						// <--
						+ "' AND idempresa=" + idempresa.toString();
				statement.execute(cQuery);

			} else {
				salida = "Comprobante de tesoreria inexistente, imposible eliminar.";
			}
		} catch (SQLException sqlException) {
			salida = "(SQLE)Imposible eliminarComprobante de tesoreria.";
			log
					.error("Error SQL en el metodo : cajaMovTesoXComprobanteDelete( ... ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "(EX)Imposible eliminarComprobante de tesoreria.";
			log
					.error("Salida por exception: en el metodo: cajaMovTesoXComprobanteDelete( ... )  "
							+ ex);
		}
		return salida;
	}

	// EJV 20090514

	public String cajaMovTesoAnulaSaldadosUpdate(String movsal_mt,
			BigDecimal sucsal_mt, BigDecimal nrosal_mt, BigDecimal idempresa,
			String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos

		// 2. sin nada desde la pagina

		// fin validaciones

		try {
			ResultSet rsSalida = null;
			String cQuery = ""
					+ "SELECT COUNT(*) FROM cajaMovTeso WHERE movsal_mt = '"
					+ movsal_mt.toString() + "' AND sucsal_mt="
					+ sucsal_mt.toString() + " AND nrosal_mt="
					+ nrosal_mt.toString() + " AND idempresa="
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

					sql = ""
							+ "UPDATE CAJAMOVTESO SET  movsal_mt=?, sucsal_mt=?, nrosal_mt=? , usado_mt=?, usuarioact=?, fechaact=? "
							+ " WHERE movsal_mt=? AND sucsal_mt=? AND nrosal_mt=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);

					insert.setString(1, null);
					insert.setBigDecimal(2, null);
					insert.setBigDecimal(3, null);
					insert.setString(4, null);
					insert.setString(5, usuarioact);
					insert.setTimestamp(6, fechaact);
					insert.setString(7, movsal_mt);
					insert.setBigDecimal(8, sucsal_mt);
					insert.setBigDecimal(9, nrosal_mt);
					insert.setBigDecimal(10, idempresa);

					int i = insert.executeUpdate();
					if (i != 1)
						salida = "No fue posible cancelar movimientos saldados.";
				}

			}

		} catch (SQLException sqlException) {
			salida = "(SQLE)No fue posible cancelar movimientos saldados.";
			log
					.error("Error SQL public String cajaMovTesoAnulaSaldadosUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "(EX)No fue posible cancelar movimientos saldados.";
			log
					.error("Error excepcion public String cajaMovTesoAnulaSaldadosUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	/*
	 * Metodos para la entidad: cajaMovTesObs Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Wed Jun 24 16:48:24 GYT 2009 - EJV
	 */

	public String cajaMovTesObsCreate(String tipomov_ob, BigDecimal sucursa_ob,
			BigDecimal comprob_ob, String observaciones, BigDecimal idempresa,
			String usuarioalt) throws EJBException {
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (tipomov_ob == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipomov_ob ";
		if (sucursa_ob == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: sucursa_ob ";
		if (comprob_ob == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: comprob_ob ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (tipomov_ob.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipomov_ob ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones

		try {
			if (salida.equalsIgnoreCase("OK")) {
				String ins = ""
						+ "INSERT INTO CAJAMOVTESOBS(tipomov_ob, sucursa_ob, comprob_ob, observaciones, idempresa, usuarioalt )"
						+ "    VALUES (?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, tipomov_ob);
				insert.setBigDecimal(2, sucursa_ob);
				insert.setBigDecimal(3, comprob_ob);
				insert.setString(4, observaciones);
				insert.setBigDecimal(5, idempresa);
				insert.setString(6, usuarioalt);
				int n = insert.executeUpdate();
				if (n != 1)
					salida = "No se pudo generar observaciones para moviimiento de tesoreria.";
			}
		} catch (SQLException sqlException) {
			salida = "(SQLE)Imposible dar de alta el registro.";
			log.error("Error SQL public String cajaMovTesObsCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "(EX)Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String cajaMovTesObsCreate(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * Metodos para la entidad: clientesMovCli Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Thu Dec 28 16:47:10 GMT-03:00 2006
	 * 
	 */

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt
	/**
	 * TODO: REPLICADO EN ClientesBean ......................................
	 * EJV - 20070822 - COMENTARIOS: analizar la posibilidad de concentrar ..
	 * metodo en una clase, recibiendo como nuevo parametro la coneccion, y .
	 * haciendo estatico al mismo. ..........................................
	 */

	public String clientesMovCliCreate(BigDecimal cliente, Timestamp fechamov,
			BigDecimal sucursal, BigDecimal comprob, BigDecimal comprob_has,
			BigDecimal tipomov, String tipomovs, BigDecimal saldo,
			BigDecimal importe, BigDecimal cambio, BigDecimal moneda,
			String unamode, String tipocomp, BigDecimal condicion,
			BigDecimal nrointerno, BigDecimal com_venta, BigDecimal com_cobra,
			BigDecimal com_vende, String anulada, BigDecimal retoque,
			BigDecimal expreso, BigDecimal sucucli, BigDecimal remito,
			BigDecimal credito, String observaciones,
			// 20110622 - EJV - Factuaracion FE-CF-MA -->
			String condicionletra,
			// <--
			String usuarioalt, BigDecimal idempresa, Connection conn)
			throws EJBException {
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		// 2. sin nada desde la pagina

		if (condicionletra == null
				|| condicionletra.trim().equalsIgnoreCase(""))
			salida = "Es necesario definir letra de condicion de iva para el cliente.";

		try {
			if (salida.equalsIgnoreCase("OK")) {
				String ins = ""
						+ "INSERT INTO CLIENTESMOVCLI(cliente, fechamov, sucursal, comprob, comprob_has, tipomov, tipomovs, "
						+ "   saldo, importe, cambio, moneda, unamode, tipocomp, condicion, nrointerno, com_venta, "
						+ "   com_cobra, com_vende, anulada, retoque, expreso, sucucli, remito, credito, observaciones, letraiva, usuarioalt, idempresa )"
						+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = conn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, cliente);
				insert.setTimestamp(2, fechamov);
				insert.setBigDecimal(3, sucursal);
				insert.setBigDecimal(4, comprob);
				insert.setBigDecimal(5, comprob_has);
				insert.setBigDecimal(6, tipomov);
				insert.setString(7, tipomovs);
				insert.setBigDecimal(8, saldo);
				insert.setBigDecimal(9, importe);
				insert.setBigDecimal(10, cambio);
				insert.setBigDecimal(11, moneda);
				insert.setString(12, unamode);
				insert.setString(13, tipocomp);
				insert.setBigDecimal(14, condicion);
				insert.setBigDecimal(15, nrointerno);
				insert.setBigDecimal(16, com_venta);
				insert.setBigDecimal(17, com_cobra);
				insert.setBigDecimal(18, com_vende);
				insert.setString(19, anulada);
				insert.setBigDecimal(20, retoque);
				insert.setBigDecimal(21, expreso);
				insert.setBigDecimal(22, sucucli);
				insert.setBigDecimal(23, remito);
				insert.setBigDecimal(24, credito);
				insert.setString(25, observaciones);
				// 20110622 - EJV - Factuaracion FE-CF-MA -->
				insert.setString(26, condicionletra);
				// <--
				insert.setString(27, usuarioalt);
				insert.setBigDecimal(28, idempresa);
				int n = insert.executeUpdate();
				if (n != 1) {
					salida = "Imposible crear movimiento clientes. ";

				}
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String clientesMovCliCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String clientesMovCliCreate(.....)"
							+ ex);
		}
		return salida;
	}

	public String clientesMovCliUpdateSaldo(BigDecimal nrointerno,
			BigDecimal saldo, String usuarioact, BigDecimal idempresa,
			Connection conn) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "OK";

		try {
			ResultSet rsSalida = null;
			// String cQuery =
			// "SELECT COUNT(*) FROM clientesMovCli WHERE nrointerno = " +
			// nrointerno.toString() + " AND idempresa=" + idempresa.toString();
			// Statement statement = dbconn.createStatement();
			// rsSalida = statement.executeQuery(cQuery);
			// int total = 0;
			// if (rsSalida != null && rsSalida.next()) total =
			// rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (salida.equalsIgnoreCase("OK")) {
				// if (total > 0) { // si existe hago update

				sql = "UPDATE CLIENTESMOVCLI SET saldo=saldo + ?, usuarioact=?, fechaact=? WHERE nrointerno=? AND idempresa=?;";
				insert = conn.prepareStatement(sql);

				// TODO: EJV 20090513
				// La condicion siguiente no tiene logica valida, reveer
				// porque
				// esta o que se pretendio hacer en el momento de crear el
				// metodo.

				// 20070830
				// En el caso que se NC
				// if (saldo.signum() != -1)
				// saldo = saldo.negate();

				insert.setBigDecimal(1, saldo);
				insert.setString(2, usuarioact);
				insert.setTimestamp(3, fechaact);
				insert.setBigDecimal(4, nrointerno);
				insert.setBigDecimal(5, idempresa);

				int i = insert.executeUpdate();
				if (i != 1) // {
					salida = "No fue posible actualizar saldo de documento de clientes nro: "
							+ nrointerno;
				// }

			}

			// }

		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log
					.error("SQLError SQL public String clientesMovCliUpdateSaldo(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String clientesMovCliUpdateSaldo(.....)"
							+ ex);
		}
		return salida;
	}

	public String clientesMovCliDelete(BigDecimal cliente,
			BigDecimal nrointerno, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM CLIENTESMOVCLI WHERE cliente="
				+ cliente.toString() + " AND nrointerno="
				+ nrointerno.toString() + " AND idempresa="
				+ idempresa.toString();
		String salida = "OK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM CLIENTESMOVCLI WHERE cliente="
						+ cliente.toString().toString() + " AND nrointerno="
						+ nrointerno.toString() + " AND idempresa="
						+ idempresa.toString();
				statement.execute(cQuery);

			} else {
				salida = "Movimiento de cliente inexistente, imposible eliminar.";
			}
		} catch (SQLException sqlException) {
			salida = "(SQLE)Movimiento de cliente inexistente, imposible eliminar.";
			log
					.error("Error SQL en el metodo : clientesMovCliDelete( BigDecimal cliente, BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "(EX)Movimiento de cliente inexistente, imposible eliminar.";
			log
					.error("Salida por exception: en el metodo: clientesMovCliDelete( BigDecimal cliente, BigDecimal idempresa )  "
							+ ex);
		}
		return salida;
	}

	/**
	 * Metodos para la entidad: clientesCancClie Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Thu Dec 28 17:02:06 GMT-03:00 2006
	 */

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt
	public String clientesCancClieCreate(BigDecimal comp_canc,
			BigDecimal comp_q_can, BigDecimal importe, Timestamp fecha,
			String usuarioalt, BigDecimal idempresa, Connection conn)
			throws EJBException {
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		// 2. sin nada desde la pagina

		try {
			if (salida.equalsIgnoreCase("OK")) {
				String ins = "INSERT INTO CLIENTESCANCCLIE(comp_canc, comp_q_can, importe, fecha, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = conn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, comp_canc);
				insert.setBigDecimal(2, comp_q_can);
				insert.setBigDecimal(3, importe);
				insert.setTimestamp(4, fecha);
				insert.setString(5, usuarioalt);
				insert.setBigDecimal(6, idempresa);
				int n = insert.executeUpdate();
				if (n != 1) {
					salida = "Imposible crear cancelacion de comprobantes.";
				}
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String clientesCancClieCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String clientesCancClieCreate(.....)"
							+ ex);
		}
		return salida;

	}

	public String clientesCancClieDelete(BigDecimal comp_canc,
			BigDecimal comp_q_can, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM clientescancclie WHERE comp_canc="
				+ comp_canc.toString() + " AND comp_q_can="
				+ comp_q_can.toString() + " AND idempresa="
				+ idempresa.toString();
		String salida = "OK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM clientescancclie WHERE comp_canc="
						+ comp_canc.toString().toString() + " AND comp_q_can="
						+ comp_q_can.toString() + " AND idempresa="
						+ idempresa.toString();
				statement.execute(cQuery);

			} else {
				salida = "No existe aplicacion del documento.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar aplicacion.";
			log
					.error("Error SQL en el metodo : clientesCancClieDelete( BigDecimal comp_canc, BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar aplicacion.";
			log
					.error("Salida por exception: en el metodo: clientesCancClieDelete( BigDecimal comp_canc, BigDecimal idempresa )  "
							+ ex);
		}
		return salida;
	}

	/**
	 * Metodos para la entidad: cajaAplicaci Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Thu Dec 28 17:08:03 GMT-03:00 2006
	 */

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt
	public String cajaAplicaciCreate(String tipomov_ap, BigDecimal comprob_ap,
			BigDecimal nrointe_ap, String anexo_ap, BigDecimal sucursa_ap,
			String usuarioalt, BigDecimal idempresa, Connection conn)
			throws EJBException {
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		// 2. sin nada desde la pagina

		// fin validaciones
		try {
			if (salida.equalsIgnoreCase("OK")) {
				String ins = "INSERT INTO CAJAAPLICACI(tipomov_ap, comprob_ap, nrointe_ap, anexo_ap, sucursa_ap, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = conn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, tipomov_ap);
				insert.setBigDecimal(2, comprob_ap);
				insert.setBigDecimal(3, nrointe_ap);
				insert.setString(4, anexo_ap);
				insert.setBigDecimal(5, sucursa_ap);
				insert.setString(6, usuarioalt);
				insert.setBigDecimal(7, idempresa);
				int n = insert.executeUpdate();
				if (n != 1)
					salida = "Imposible crear aplicaciones.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String cajaAplicaciCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error excepcion public String cajaAplicaciCreate(.....)"
					+ ex);
		}
		return salida;
	}

	public List getCajaAplicaciPK(String tipomov_ap, BigDecimal sucursa_ap,
			BigDecimal comprob_ap, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT nrointe_ap, anexo_ap  FROM cajaaplicaci WHERE tipomov_ap='"
				+ tipomov_ap.toString() + "' AND sucursa_ap = "
				+ sucursa_ap.toString() + "  AND comprob_ap = "
				+ comprob_ap.toString() + "  AND idempresa = "
				+ idempresa.toString() + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public String getCajaAplicaciAnexoPK(BigDecimal nrointerno,
			String tipomov_ap, BigDecimal sucursa_ap, BigDecimal comprob_ap,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String anexo_ap = "";
		String cQuery = ""
				+ "SELECT CASE WHEN anexo_ap IS NULL OR TRIM(anexo_ap) = '' "
				+ "            THEN 'N' ELSE anexo_ap "
				+ "       END AS anexo_ap  FROM cajaaplicaci WHERE nrointe_ap = "
				+ nrointerno.toString() + " AND tipomov_ap='"
				+ tipomov_ap.toString() + "' AND sucursa_ap = "
				+ sucursa_ap.toString() + "  AND comprob_ap = "
				+ comprob_ap.toString() + "  AND idempresa = "
				+ idempresa.toString() + ";";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida != null && rsSalida.next()) {

				anexo_ap = rsSalida.getString(1);

			}

		} catch (SQLException sqlException) {
			log.error("Error SQL en el metodo : getCajaAplicaciAnexoPK( ... ) "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCajaAplicaciAnexoPK( ... )  "
							+ ex);
		}
		return anexo_ap;
	}

	public String cajaAplicaciDelete(String tipomov_ap, BigDecimal sucursa_ap,
			BigDecimal comprob_ap, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM CAJAAPLICACI WHERE tipomov_ap='"
				+ tipomov_ap.toString() + "' AND sucursa_ap="
				+ sucursa_ap.toString() + " AND comprob_ap="
				+ comprob_ap.toString() + " AND idempresa="
				+ idempresa.toString();
		String salida = "OK";

		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM CAJAAPLICACI WHERE tipomov_ap='"
						+ tipomov_ap.toString() + "' AND sucursa_ap="
						+ sucursa_ap.toString() + " AND comprob_ap="
						+ comprob_ap.toString() + " AND idempresa="
						+ idempresa.toString();
				statement.execute(cQuery);

			}

		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log.error("Error SQL en el metodo : cajaAplicaciDelete( ... ) "
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: cajaAplicaciDelete( ... )  "
							+ ex);
		}
		return salida;
	}

	/**
	 * Metodos para la entidad: cajaSaldoBco Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Mon Jan 15 17:36:17 GMT-03:00 2007
	 * 
	 */

	public String cajaSaldoBcoCreateOrUpdate(String banco, java.sql.Date fecha,
			BigDecimal saldo_cont, BigDecimal saldo_disp, String usuarioact,
			BigDecimal idempresa) throws EJBException {

		String salida = "OK";

		try {

			salida = cajaSaldoBcoCreateOrUpdate(banco, fecha, saldo_cont,
					saldo_disp, usuarioact, idempresa, dbconn);

		} catch (Exception e) {

			log.error("cajaSaldoBcoCreateOrUpdate(sobrecarga): " + e);

		}

		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria
	public String cajaSaldoBcoCreateOrUpdate(String banco, java.sql.Date fecha,
			BigDecimal saldo_cont, BigDecimal saldo_disp, String usuarioact,
			BigDecimal idempresa, Connection conn) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "OK";
		BigDecimal saldoContAcumulado = new BigDecimal(0);
		BigDecimal saldoDispAcumulado = new BigDecimal(0);
		// validaciones de datos:
		// 1. nulidad de campos

		// 2. sin nada desde la pagina
		// fin validaciones

		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM cajaSaldoBco WHERE banco = ? AND fecha::date = ? AND idempresa=?";
			PreparedStatement statement = conn.prepareStatement(cQuery);
			statement.setString(1, banco);
			statement.setDate(2, fecha);
			statement.setBigDecimal(3, idempresa);
			rsSalida = statement.executeQuery();

			int total = 0;

			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);

			PreparedStatement insert = null;
			String sql = "";
			if (salida.equalsIgnoreCase("OK")) {

				if (total > 0) { // si existe hago update

					sql = "UPDATE CAJASALDOBCO SET  saldo_cont=saldo_cont + ?, saldo_disp=saldo_disp + ?, usuarioact=?, fechaact=? WHERE banco=? AND fecha::date = ? AND idempresa=?;";
					insert = conn.prepareStatement(sql);

					insert.setBigDecimal(1, saldo_cont);
					insert.setBigDecimal(2, saldo_disp);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setString(5, banco);
					insert.setDate(6, fecha);
					insert.setBigDecimal(7, idempresa);

				} else {

					cQuery = "SELECT saldo_cont, saldo_disp "
							+ "   FROM cajaSaldoBco WHERE banco = ?  AND idempresa=?"
							+ "    AND fecha = ( SELECT MAX(fecha)  "
							+ "                    FROM cajaSaldoBco "
							+ "                   WHERE banco = ?  AND idempresa=?)";
					statement = conn.prepareStatement(cQuery);
					statement.setString(1, banco);
					statement.setBigDecimal(2, idempresa);
					statement.setString(3, banco);
					statement.setBigDecimal(4, idempresa);
					rsSalida = statement.executeQuery();

					if (rsSalida != null) {

						if (rsSalida.next()) {
							saldoContAcumulado = rsSalida.getBigDecimal(1);
							saldoDispAcumulado = rsSalida.getBigDecimal(2);

						}
					} else {
						throw new SQLException(
								"RSSALIDA:NULO - IMPOSIBLE RECUPERAR SALDOS.");
					}

					String ins = "INSERT INTO CAJASALDOBCO(banco, fecha, saldo_cont , saldo_disp, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?, ?)";
					insert = conn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, banco);
					insert.setDate(2, fecha);
					insert.setBigDecimal(3, saldo_cont.add(saldoContAcumulado));
					insert.setBigDecimal(4, saldo_disp.add(saldoDispAcumulado));
					insert.setString(5, usuarioalt);
					insert.setBigDecimal(6, idempresa);

				}
				int i = insert.executeUpdate();
				if (i <= 0)
					salida = "Imposible actualizar saldo para banco: " + banco;

			}

		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro [saldo - " + banco
					+ "].";
			log
					.error("Error SQL public String cajaSaldoBcoCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String cajaSaldoBcoCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * Metodos para la entidad: vCajaMovTeso Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Tue Jan 16 09:31:12 GMT-03:00 2007
	 */

	public String cajaSaldaMovimientosTeso(BigDecimal idmovteso,
			String movsal_mt, BigDecimal sucsal_mt, BigDecimal nrosal_mt,
			String usado_mt, String deposi_mt, String usuarioact,
			BigDecimal idempresa, Connection conn) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "OK";

		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM CajaMovTeso WHERE idmovteso = "
					+ idmovteso.toString()
					+ " AND idempresa = "
					+ idempresa.toString();
			Statement statement = conn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (salida.equalsIgnoreCase("OK")) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE CAJAMOVTESO SET movsal_mt=?, sucsal_mt=?, nrosal_mt=?, usado_mt=?, deposi_mt=?, usuarioact=?, fechaact=? "
							+ " WHERE idmovteso=? AND idempresa=?;";
					insert = conn.prepareStatement(sql);
					insert.setString(1, movsal_mt);
					insert.setBigDecimal(2, sucsal_mt);
					insert.setBigDecimal(3, nrosal_mt);
					insert.setString(4, usado_mt);
					insert.setString(5, usado_mt);
					insert.setString(6, usuarioact);
					insert.setTimestamp(7, fechaact);
					insert.setBigDecimal(8, idmovteso);
					insert.setBigDecimal(9, idempresa);
				}

				int i = insert.executeUpdate();
				if (i <= 0)
					salida = "Imposible saldar movimientos de tesoreria.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String cajaSaldaMovimientosTeso(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String cajaSaldaMovimientosTeso(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * Metodos para la entidad: vCajaMovTeso Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Tue Jan 16 09:31:12 GMT-03:00 2007 EJV ...
	 */

	public String cajaMovTesoActualizaImportes(BigDecimal idmovteso,
			BigDecimal importe, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "OK";

		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM CajaMovTeso WHERE idmovteso = "
					+ idmovteso.toString()
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
					sql = "UPDATE CAJAMOVTESO SET  impori_mt =  (impori_mt + ?), importe_mt = (importe_mt + ?), usuarioact=?, fechaact=? WHERE idmovteso=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, importe);
					insert.setBigDecimal(2, importe);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, idmovteso);
					insert.setBigDecimal(6, idempresa);
				}

				int i = insert.executeUpdate();
				if (i <= 0)
					salida = "Imposible actualizar importes movimientos de tesoreria.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error SQL public String cajaMovTesoActualizaImportes(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String cajaMovTesoActualizaImportes(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * Metodos para la entidad: vCajaMovTeso Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Tue Apr 29 09:31:12 GMT-03:00 2008
	 */

	public String cajaSaldaMovimientosXFechaTeso(String movsal_mt,
			BigDecimal sucsal_mt, BigDecimal nrosal_mt, String usado_mt,
			String cartera_mt, String tipcart_mt, java.sql.Date fecha_mt,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "OK";

		try {
			ResultSet rsSalida = null;
			String cQuery = ""
					+ "SELECT COUNT(*) FROM CajaMovTeso  "
					+ " WHERE cartera_mt = ? AND tipcart_mt = ? "
					+ "   AND usado_mt IS NULL and fecha_mt::date <= ?  AND idempresa=?";
			PreparedStatement pstatement = dbconn.prepareStatement(cQuery);
			pstatement = dbconn.prepareStatement(cQuery);
			pstatement.setString(1, cartera_mt);
			pstatement.setString(2, tipcart_mt);
			pstatement.setDate(3, fecha_mt);
			pstatement.setBigDecimal(4, idempresa);
			rsSalida = pstatement.executeQuery();

			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (salida.equalsIgnoreCase("OK")) {
				if (total > 0) { // si existe hago update

					sql = "   UPDATE CAJAMOVTESO "
							+ "  SET movsal_mt=?, sucsal_mt=?, nrosal_mt=?, usado_mt=?, usuarioact=?, fechaact=? "
							+ "WHERE cartera_mt = ? AND tipcart_mt = ? "
							+ "  AND usado_mt IS NULL and fecha_mt::date <= ?  AND idempresa=?;";

					insert = dbconn.prepareStatement(sql);
					insert.setString(1, movsal_mt);
					insert.setBigDecimal(2, sucsal_mt);
					insert.setBigDecimal(3, nrosal_mt);
					insert.setString(4, usado_mt);
					insert.setString(5, usuarioact);
					insert.setTimestamp(6, fechaact);
					insert.setString(7, cartera_mt);
					insert.setString(8, tipcart_mt);
					insert.setDate(9, fecha_mt);
					insert.setBigDecimal(10, idempresa);

				}

				int i = insert.executeUpdate();
				if (i <= 0)
					salida = "Imposible saldar movimientos de tesoreria.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error SQL public String cajaSaldaMovimientosXFechaTeso(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String cajaSaldaMovimientosXFechaTeso(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * Metodo para recuperar totales por cartera.
	 * 
	 */

	public BigDecimal getTotalCartera(String cartera_mt, String tipcart_mt,
			java.sql.Date fecha_mt, BigDecimal idempresa) throws EJBException {
		BigDecimal total = new BigDecimal(-1);
		ResultSet rsTotal = null;
		String cQuery = "";
		cQuery = "SELECT COALESCE(SUM(importe_mt), -1)::numeric(18,2) AS  importe"
				+ " FROM cajamovteso "
				+ "WHERE cartera_mt = ? AND tipcart_mt = ? "
				+ "  AND usado_mt IS NULL and fecha_mt::date <= ?  AND idempresa=?";

		PreparedStatement pstatement = null;
		try {

			pstatement = dbconn.prepareStatement(cQuery);
			pstatement.setString(1, cartera_mt);
			pstatement.setString(2, tipcart_mt);
			pstatement.setDate(3, fecha_mt);
			pstatement.setBigDecimal(4, idempresa);

			rsTotal = pstatement.executeQuery();

			if (rsTotal != null) {
				if (rsTotal.next()) {
					total = rsTotal.getBigDecimal(1);
				} else
					log
							.warn("getTotalCartera(): Error al recuperar total [rs EOF].");

			} else
				log
						.warn("getTotalCartera(): Error al recuperar total [rs nulo].");

		} catch (Exception e) {
			log.error("getTotalCartera()" + e);
		}
		return total;
	}

	/**
	 * Metodos para la entidad: clientesContCli Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Thu Jan 18 10:29:30 GMT-03:00 2007
	 */

	public String clientesContCliCreate(BigDecimal cuenta_con,
			BigDecimal impor_con, String nroiva_con, BigDecimal nroint_con,
			String centr1_con, String centr2_con, String usuarioalt,
			BigDecimal idempresa) throws EJBException {

		String salida = "OK";

		try {

			salida = clientesContCliCreate(cuenta_con, impor_con, nroiva_con,
					nroint_con, centr1_con, centr2_con, usuarioalt, idempresa,
					dbconn);

		} catch (Exception e) {
			log.error("Error SQL public String clientesContCliCreate(.. 0 ..)"
					+ e);
		}

		return salida;
	}

	public String clientesContCliCreate(BigDecimal cuenta_con,
			BigDecimal impor_con, String nroiva_con, BigDecimal nroint_con,
			String centr1_con, String centr2_con, String usuarioalt,
			BigDecimal idempresa, Connection conn) throws EJBException {
		String salida = "OK";

		try {
			if (salida.equalsIgnoreCase("OK")) {
				String ins = "INSERT INTO CLIENTESCONTCLI(cuenta_con, impor_con, nroiva_con, nroint_con, centr1_con, centr2_con, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = conn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, cuenta_con);
				insert.setBigDecimal(2, impor_con);
				insert.setString(3, nroiva_con);
				insert.setBigDecimal(4, nroint_con);
				insert.setString(5, centr1_con);
				insert.setString(6, centr2_con);
				insert.setString(7, usuarioalt);
				insert.setBigDecimal(8, idempresa);
				int n = insert.executeUpdate();
				if (n != 1)
					salida = "Error al generar movimiento contable para cliente.";
			}
		} catch (SQLException sqlException) {
			salida = "(SQLE)Imposible generar contabilidad cliente.";
			log.error("Error SQL public String clientesContCliCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "(EX)Imposible generar contabilidad cliente.";
			log
					.error("Error excepcion public String clientesContCliCreate(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * Metodos para la entidad: clientesTablaIva Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Thu Jan 18 12:43:16 GMT-03:00 2007
	 */
	// por primary key (primer campo por defecto)
	public List getClientesTablaIvaPK(BigDecimal idtipoiva, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT idtipoiva,tipoiva,porcent1,descrimina,desglosa,porcent2,letra,ctapromo,"
				+ "       usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM CLIENTESTABLAIVA WHERE idtipoiva="
				+ idtipoiva.toString() + " AND idempresa="
				+ idempresa.toString();

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: clientesVendedor Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Fri Jan 19 10:25:04 GMT-03:00 2007
	 */

	// por primary key (primer campo por defecto)
	public List getClientesVendedorPK(BigDecimal idvendedor,
			BigDecimal idempresa) throws EJBException {

		String cQuery = ""
				+ "SELECT idvendedor,vendedor,comision1,comision2,domicilio,"
				+ "       usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM CLIENTESVENDEDOR WHERE idvendedor="
				+ idvendedor.toString() + " AND idempresa="
				+ idempresa.toString();

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/*
	 * FINALIZA METODOS PARA GENERARAR COBRANZAS POR CLIENTES
	 */

	/**
	 * Metodos para la entidad: proveedoMovProv Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Thu Jan 24 14:30:44 GMT-03:00 2007
	 */
	// para todo (ordena por el segundo campo por defecto)
	public List getLovCajaMovProvAll(long limit, long offset,
			BigDecimal idproveedor, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT nrointerno,idproveedor,fechamov,sucursal,comprob,tipomov,tipomovs,"
				+ "       importe::NUMERIC(18,2),saldo::NUMERIC(18,2),idcondicionpago,fecha_subd,retoque,fechavto,"
				+ "       usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM PROVEEDOMOVPROV" + " WHERE idproveedor = "
				+ idproveedor + " AND saldo > 0 AND tipomov < 3 AND idempresa="
				+ idempresa.toString() + " ORDER BY sucursal, comprob  LIMIT "
				+ limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getLovCajaMovProvOcu(long limit, long offset,
			BigDecimal idproveedor, String ocurrencia, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT nrointerno,idproveedor,fechamov,sucursal,comprob,tipomov,"
				+ "       tipomovs,importe::NUMERIC(18,2),saldo::NUMERIC(18,2),idcondicionpago,fecha_subd,retoque,fechavto,"
				+ "       usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM PROVEEDOMOVPROV " + " WHERE IDPROVEEDOR = "
				+ idproveedor
				+ " AND saldo > 0  and tipomov < 4 AND comprob::VARCHAR LIKE '"
				+ ocurrencia + "%'  AND idempresa = " + idempresa.toString()
				+ " ORDER BY sucursal, comprob  LIMIT " + limit + " OFFSET  "
				+ offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**/
	public List getLovCajaMovProvPK(BigDecimal nrointerno, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT nrointerno, saldo::NUMERIC(18,2),importe::NUMERIC(18,2), fechamov, sucursal, comprob, "
				+ "       saldo::NUMERIC(18,2) as saldocontrol, tipomov, idproveedor, tipomovs "
				+ "  FROM PROVEEDOMOVPROV" + " WHERE nrointerno = "
				+ nrointerno + " AND saldo > 0  AND idempresa="
				+ idempresa.toString();
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para todo (ordena por el segundo campo por defecto)
	public List getLovCajaIdentificaSalidaPagosAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT i.ididentificador,ti.tipoidentificador,i.identificador,i.descripcion,"
				+ "       i.cuenta,i.idmoneda,ti.tipomov,ti.propio,i.modcta,i.factura,i.saldo_id,"
				+ "       i.saldo_disp,i.renglones,i.ctacaucion,i.ctatodoc,i.gerencia,i.formula,"
				+ "       i.cuotas,i.presentacion,i.ctacaudoc,i.porcentaje,i.ctadtotar,i.ctatarjeta,"
				+ "       i.comhyper,i.contador,i.afecomicob,i.impri_id,i.subdiventa,i.idcencosto,"
				+ "       i.idcencosto1,i.modicent,i.prox_cheq,i.prox_reserv,i.ulti_cheq,i.modsubcent,"
				+ "       i.res_nro, CASE WHEN ti.tipomov IN ('C','T','D') THEN 'Z' ELSE 'A' END,"
				+ "       i.usuarioalt,i.usuarioact,i.fechaalt,i.fechaact "
				+ "  FROM cajaidentificadores i"
				+ "       INNER JOIN cajatipoidentificadores ti ON i.idtipoidentificador = ti.idtipoidentificador  AND i.idempresa=ti.idempresa "
				+ " WHERE i.ididentificador NOT IN "
				+ "      ( SELECT ididentificador "
				+ "          FROM videntificadorestipo "
				+ "         WHERE ( (UPPER(tipomov) = 'T' AND  propio = 'N') OR UPPER(tipomov) = 'X') AND idempresa="
				+ idempresa.toString() + "  )" + " AND i.idempresa = "
				+ idempresa.toString() + " ORDER BY 37, 2, 3  LIMIT " + limit
				+ " OFFSET " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getLovCajaIdentificaSalidaPagosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT i.ididentificador,ti.tipoidentificador,i.identificador,i.descripcion,"
				+ "       i.cuenta,i.idmoneda,ti.tipomov,ti.propio,i.modcta,i.factura,i.saldo_id,"
				+ "       i.saldo_disp,i.renglones,i.ctacaucion,i.ctatodoc,i.gerencia,i.formula,"
				+ "       i.cuotas,i.presentacion,i.ctacaudoc,i.porcentaje,i.ctadtotar,i.ctatarjeta,"
				+ "       i.comhyper,i.contador,i.afecomicob,i.impri_id,i.subdiventa,i.idcencosto,"
				+ "       i.idcencosto1,i.modicent,i.prox_cheq,i.prox_reserv,i.ulti_cheq,i.modsubcent,"
				+ "       i.res_nro, CASE WHEN ti.tipomov IN ('C','T','D') THEN 'Z' ELSE 'A' END,"
				+ "       i.usuarioalt,i.usuarioact,i.fechaalt,i.fechaact "
				+ "  FROM cajaidentificadores i"
				+ "       INNER JOIN cajatipoidentificadores ti ON i.idtipoidentificador = ti.idtipoidentificador AND i.idempresa=ti.idempresa"
				+ " WHERE i.ididentificador NOT IN "
				+ "      ( SELECT ididentificador "
				+ "          FROM videntificadorestipo "
				+ "         WHERE ((UPPER(tipomov) = 'T' AND  propio = 'N') OR UPPER(tipomov) = 'X') AND idempresa="
				+ idempresa.toString() + ")"
				+ "   AND ((UPPER(i.descripcion) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(i.identificador) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%') ) AND i.idempresa="
				+ idempresa.toString() + " ORDER BY 37, 2, 3  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para todo (ordena por el segundo campo por defecto)
	public List getLovCajaIdentificaEntradaPagosAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT i.ididentificador,ti.tipoidentificador,i.identificador,i.descripcion,"
				+ "       i.cuenta,i.idmoneda,ti.tipomov,ti.propio,i.modcta,i.factura,i.saldo_id,"
				+ "       i.saldo_disp,i.renglones,i.ctacaucion,i.ctatodoc,i.gerencia,i.formula,"
				+ "       i.cuotas,i.presentacion,i.ctacaudoc,i.porcentaje,i.ctadtotar,i.ctatarjeta,"
				+ "       i.comhyper,i.contador,i.afecomicob,i.impri_id,i.subdiventa,i.idcencosto,"
				+ "       i.idcencosto1,i.modicent,i.prox_cheq,i.prox_reserv,i.ulti_cheq,i.modsubcent,"
				+ "       i.res_nro, CASE WHEN ti.tipomov IN ('C','T','D') THEN 'Z' ELSE 'A' END,"
				+ "       i.usuarioalt,i.usuarioact,i.fechaalt,i.fechaact "
				+ "  FROM cajaidentificadores i"
				+ "       INNER JOIN cajatipoidentificadores ti ON i.idtipoidentificador = ti.idtipoidentificador  AND i.idempresa=ti.idempresa "
				+ " WHERE i.ididentificador NOT IN "
				+ "      ( SELECT ididentificador "
				+ "          FROM videntificadorestipo "
				+ "         WHERE ( (UPPER(tipomov) = 'T' AND  propio = 'N')"
				+ "            OR (UPPER(tipomov) = 'D' AND  propio = 'N' ) "
				+ "            OR UPPER(tipomov) = 'C'  "
				+ "            OR UPPER(tipomov) = 'S'  "
				+ "            OR UPPER(tipomov) = 'R'  "
				+ "            OR UPPER(tipomov) = 'X') AND idempresa="
				+ idempresa.toString() + "  )" + " AND i.idempresa = "
				+ idempresa.toString() + " ORDER BY 37, 2, 3  LIMIT " + limit
				+ " OFFSET " + offset + ";";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getLovCajaIdentificaEntradaPagosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT i.ididentificador,ti.tipoidentificador,i.identificador,i.descripcion,"
				+ "       i.cuenta,i.idmoneda,ti.tipomov,ti.propio,i.modcta,i.factura,i.saldo_id,"
				+ "       i.saldo_disp,i.renglones,i.ctacaucion,i.ctatodoc,i.gerencia,i.formula,"
				+ "       i.cuotas,i.presentacion,i.ctacaudoc,i.porcentaje,i.ctadtotar,i.ctatarjeta,"
				+ "       i.comhyper,i.contador,i.afecomicob,i.impri_id,i.subdiventa,i.idcencosto,"
				+ "       i.idcencosto1,i.modicent,i.prox_cheq,i.prox_reserv,i.ulti_cheq,i.modsubcent,"
				+ "       i.res_nro, CASE WHEN ti.tipomov IN ('C','T','D') THEN 'Z' ELSE 'A' END,"
				+ "       i.usuarioalt,i.usuarioact,i.fechaalt,i.fechaact "
				+ "  FROM cajaidentificadores i"
				+ "       INNER JOIN cajatipoidentificadores ti ON i.idtipoidentificador = ti.idtipoidentificador AND i.idempresa=ti.idempresa"
				+ " WHERE i.ididentificador NOT IN "
				+ "      ( SELECT ididentificador "
				+ "          FROM videntificadorestipo "
				+ "         WHERE ((UPPER(tipomov) = 'T' AND  propio = 'N') "
				+ "            OR (UPPER(tipomov) = 'D' AND  propio = 'N' ) "
				+ "            OR UPPER(tipomov) = 'C'  "
				+ "            OR UPPER(tipomov) = 'S'  "
				+ "            OR UPPER(tipomov) = 'R'  "
				+ "            OR UPPER(tipomov) = 'X') AND idempresa="
				+ idempresa.toString() + ")"
				+ "   AND ((UPPER(i.descripcion) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(i.identificador) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%') ) AND i.idempresa="
				+ idempresa.toString() + " ORDER BY 37, 2, 3  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: proveedoMovProv Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Tue Jan 30 14:07:49 GMT-03:00 2007
	 * 
	 */

	public String proveedoMovProvCreate(BigDecimal nrointerno,
			BigDecimal idproveedor, Timestamp fechamov, BigDecimal sucursal,
			BigDecimal comprob, BigDecimal tipomov, String tipomovs,
			BigDecimal importe, BigDecimal saldo, BigDecimal idcondicionpago,
			Timestamp fecha_subd, BigDecimal retoque, java.sql.Date fechavto,
			String observaciones, String usuarioalt, BigDecimal idempresa)
			throws EJBException {
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idproveedor == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idproveedor ";
		if (fechamov == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechamov ";
		if (sucursal == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: sucursal ";
		if (comprob == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: comprob ";
		if (tipomov == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipomov ";
		if (tipomovs == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipomovs ";
		if (fechavto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechavto ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (tipomovs.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipomovs ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		try {
			if (salida.equalsIgnoreCase("OK")) {
				String ins = "INSERT INTO PROVEEDOMOVPROV(nrointerno,idproveedor, fechamov, sucursal, comprob, tipomov, tipomovs, importe, saldo, idcondicionpago, fecha_subd, retoque, fechavto, observaciones, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, nrointerno);
				insert.setBigDecimal(2, idproveedor);
				insert.setTimestamp(3, fechamov);
				insert.setBigDecimal(4, sucursal);
				insert.setBigDecimal(5, comprob);
				insert.setBigDecimal(6, tipomov);
				insert.setString(7, tipomovs);
				insert.setBigDecimal(8, importe);
				insert.setBigDecimal(9, saldo);
				insert.setBigDecimal(10, idcondicionpago);
				insert.setTimestamp(11, fecha_subd);
				insert.setBigDecimal(12, retoque);
				insert.setDate(13, fechavto);
				insert.setString(14, observaciones);
				insert.setString(15, usuarioalt);
				insert.setBigDecimal(16, idempresa);
				int n = insert.executeUpdate();
				if (n != 1)
					salida = "Imposible generear movimiento de proveedor.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String proveedoMovProvCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String proveedoMovProvCreate(.....)"
							+ ex);
		}
		return salida;
	}

	public String proveedoMovProvUpdateSaldo(BigDecimal nrointerno,
			BigDecimal saldo, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (nrointerno == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nrointerno ";

		// fin validaciones

		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM proveedoMovProv WHERE nrointerno = "
					+ nrointerno.toString()
					+ " AND idempresa = "
					+ idempresa.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			else
				salida = "Movimiento de Proveedores Inexistente: ["
						+ nrointerno + "]";

			PreparedStatement insert = null;
			String sql = "";
			if (salida.equalsIgnoreCase("OK")) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE PROVEEDOMOVPROV SET saldo = saldo + ?, usuarioact=?, fechaact=? WHERE nrointerno=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					// 20081015 EJV - Valor +/- viene desde invocacion
					// insert.setBigDecimal(1, saldo.negate());
					insert.setBigDecimal(1, saldo);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, nrointerno);
					insert.setBigDecimal(5, idempresa);
				}

				int i = insert.executeUpdate();
				if (i != 1)
					salida = "Imposible actualizar saldo de proveedores: ["
							+ nrointerno + "]";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error SQL public String proveedoMovProvUpdateSaldo(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String proveedoMovProvUpdateSaldo(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * Metodos para la entidad: proveedoCancProv Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Tue Jan 30 14:40:17 GMT-03:00 2007
	 */

	public String proveedoMovProvAplicacionesCreate(BigDecimal nrointerno_canc,
			BigDecimal nrointerno_q_can, BigDecimal importe, String usuarioalt,
			BigDecimal idempresa) throws EJBException, SQLException {
		String salida = "OK";
		BigDecimal auxsaldos = new BigDecimal(0);

		dbconn.setAutoCommit(false);

		try {

			auxsaldos = GeneralBean.getSaldoMovProveed(nrointerno_q_can,
					idempresa, dbconn);

			if (auxsaldos.compareTo(importe) >= 0) {

				auxsaldos = GeneralBean.getSaldoMovProveed(nrointerno_canc,
						idempresa, dbconn);

				if (auxsaldos.compareTo(importe) >= 0) {

					salida = proveedoCancProvCreateOrUpdate(nrointerno_canc,
							nrointerno_q_can, importe, usuarioalt, idempresa);

					if (salida.equalsIgnoreCase("OK")) {

						salida = proveedoMovProvUpdateSaldo(nrointerno_canc,
								importe.negate(), usuarioalt, idempresa);

						if (salida.equalsIgnoreCase("OK")) {
							salida = proveedoMovProvUpdateSaldo(
									nrointerno_q_can, importe.negate(),
									usuarioalt, idempresa);
						}

					}

				} else
					salida = "Saldo a cancelar menor a importe, por favor verificar.";
			} else
				salida = "Saldo a aplicar menor a importe, por favor verificar.";

		} catch (Exception e) {
			salida = "Excepcion al aplicar movimientos.";
			log.error("proveedoMovProvAplicacionesCreate(...): " + e);
		}

		if (salida.equalsIgnoreCase("OK"))
			dbconn.commit();
		else
			dbconn.rollback();

		dbconn.setAutoCommit(true);

		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt
	public String proveedoCancProvCreateOrUpdate(BigDecimal nrointerno_canc,
			BigDecimal nrointerno_q_can, BigDecimal importe, String usuarioalt,
			BigDecimal idempresa) throws EJBException {
		String salida = "OK";
		String qDML = "";
		Timestamp fechaact = new Timestamp(Calendar.getInstance().getTime()
				.getTime());
		String cQuery = "SELECT COUNT(1) FROM proveedocancprov "
				+ "        WHERE nrointerno_canc=? AND nrointerno_q_can=? AND idempresa=?";
		// validaciones de datos:
		// 1. nulidad de campos
		if (nrointerno_canc == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nrointerno_canc ";
		if (nrointerno_q_can == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nrointerno_q_can ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones

		try {
			if (salida.equalsIgnoreCase("OK")) {

				PreparedStatement pstatement = dbconn.prepareStatement(cQuery);
				PreparedStatement insert = null;
				pstatement.setBigDecimal(1, nrointerno_canc);
				pstatement.setBigDecimal(2, nrointerno_q_can);
				pstatement.setBigDecimal(3, idempresa);
				ResultSet rs = pstatement.executeQuery();

				if (rs != null && rs.next()) {

					if (rs.getInt(1) == 0) {

						qDML = "INSERT INTO proveedocancprov(nrointerno_canc, nrointerno_q_can, importe, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?)";
						insert = dbconn.prepareStatement(qDML);
						// seteo de campos:
						insert.setBigDecimal(1, nrointerno_canc);
						insert.setBigDecimal(2, nrointerno_q_can);
						insert.setDouble(3, importe.doubleValue());
						insert.setString(4, usuarioalt);
						insert.setBigDecimal(5, idempresa);

					} else {

						qDML = "  UPDATE proveedocancprov "
								+ "  SET importe=importe+?, "
								+ "      usuarioact=?, fechaact=? "
								+ "WHERE nrointerno_canc=? AND nrointerno_q_can=? AND idempresa=?;";
						insert = dbconn.prepareStatement(qDML);
						// seteo de campos:
						insert.setDouble(1, importe.doubleValue());
						insert.setString(2, usuarioalt);
						insert.setTimestamp(3, fechaact);
						insert.setBigDecimal(4, nrointerno_canc);
						insert.setBigDecimal(5, nrointerno_q_can);
						insert.setBigDecimal(6, idempresa);

					}

					int n = insert.executeUpdate();
					if (n != 1)
						salida = "Imposible cancelar documentos de proveedor.";

				} else
					salida = "Imposible verificar existencia de cancelacion.";

			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String proveedoCancProvCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String proveedoCancProvCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * Metodos para la entidad: proveedoProveed Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Wed Jan 31 12:35:05 GMT-03:00 2007
	 * 
	 */
	// por primary key (primer campo por defecto)
	public List getProveedoProveedPK(BigDecimal idproveedor,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idproveedor,razon_social,domicilio,idlocalidad,idprovincia,postal,contacto,telefono,"
				+ "       cuit,brutos,ctapasivo,ctaactivo1,ctaactivo2,ctaactivo3,ctaactivo4,ctaiva,ctaretiva,"
				+ "       letra_iva,ctadocumen,ret_gan,idretencion1,idretencion2,idretencion3,idretencion4,"
				+ "       idretencion5,ctades,stock_fact,idcondicionpago,cent1,cent2,cent3,cent4,cents1,cents2,"
				+ "       cents3,cents4,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM PROVEEDOPROVEED WHERE idproveedor="
				+ idproveedor.toString() + " AND idempresa= "
				+ idempresa.toString();

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: vreImpresionRecibos Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Thu Sep 27 14:09:34 ART 2007
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getVreImpresionRecibosAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT comprobante,fechamov,importe,razon,cuit,fechaalt,fechaact,usuarioalt,usuarioact "
				+ "  FROM VREIMPRESIONRECIBOS  WHERE idempresa = "
				+ idempresa.toString() + "  " + "ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getVreImpresionRecibosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT comprobante,fechamov,importe,razon,cuit,fechaalt,fechaact,usuarioalt,usuarioact "
				+ "  FROM VREIMPRESIONRECIBOS WHERE ((comprobante::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%' OR UPPER(cuit) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%')  AND idempresa = "
				+ idempresa.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * - INFORMES TESORERIA
	 */

	/*
	 * Informe de Saldos en Efectivo entre fechas
	 */

	public List getSaldosEfectivoFecha(Timestamp fechaDesde,
			Timestamp fechaHasta, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;

		String cQuery = ""
				+ "SELECT i.descripcion,sb.banco, sb.fecha, sb.saldo_cont, sb.saldo_disp "
				+ "  FROM cajasaldobco sb "
				+ "       INNER JOIN cajaidentificadores i ON sb.banco = i.identificador AND sb.idempresa = i.idempresa "
				+ "       INNER JOIN cajatipoidentificadores ti ON i.idtipoidentificador = ti.idtipoidentificador "
				+ "              AND i.idempresa = ti.idempresa AND ti.tipomov IN ('E', 'G')"
				+ " WHERE i.idempresa = ? AND sb.fecha BETWEEN ? AND ? ORDER BY sb.fecha DESC, sb.banco;";
		List vecSalida = new ArrayList();
		try {
			PreparedStatement statement = dbconn.prepareStatement(cQuery);
			statement.setBigDecimal(1, idempresa);
			statement.setTimestamp(2, fechaDesde);
			statement.setTimestamp(3, fechaHasta);

			rsSalida = statement.executeQuery();
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
			log.error("Error SQL en el metodo : getSaldosEfectivoFecha() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getSaldosEfectivoFecha()  "
							+ ex);
		}

		return vecSalida;
	}

	/*
	 * Informe de Movimientos de Cobranzas entre fechas
	 */

	public List getMovCobranzaFecha(Timestamp fechaDesde, Timestamp fechaHasta,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "	SELECT mt.fechamo_mt, LPAD(mt.sucursa_mt::varchar, 4, '0')AS sucursa_mt, LPAD(mt.comprob_mt::varchar, 8, '0') AS comprob_mt, "
				+ "	       cl.razon, mt.cartera_mt, id.descripcion, "
				+ "	       (CASE WHEN mt.tipo_mt = 1 THEN mt.impori_mt ELSE -mt.impori_mt END)::NUMERIC(18,2) AS impori_mt, "
				+ "	       mt.detalle_mt, mt.nrodoc_mt, cg.horas_cl, mt.cuenta_mt, mt.movsal_mt, mt.nrosal_mt, mt.tipo_mt "
				+ "   FROM cajamovteso mt "
				+ "	       LEFT JOIN clientesclientes cl ON mt.idcliente = cl.idcliente AND mt.idempresa = cl.idempresa "
				+ "	       LEFT JOIN cajaclearing cg ON mt.idclearing = cg.idclearing  AND mt.idempresa = cg.idempresa "
				+ "	       LEFT JOIN cajaidentificadores id ON mt.cartera_mt = id.identificador  AND mt.idempresa = id.idempresa "
				+ "	 WHERE mt.tipomov_mt = 'COB'  AND mt.idempresa = ? AND mt.fechamo_mt BETWEEN ? AND ? "
				+ "	 ORDER BY cl.idcliente, mt.sucursa_mt, mt.comprob_mt, 7 DESC ";

		List vecSalida = new ArrayList();
		try {
			PreparedStatement statement = dbconn.prepareStatement(cQuery);
			statement.setBigDecimal(1, idempresa);
			statement.setTimestamp(2, fechaDesde);
			statement.setTimestamp(3, fechaHasta);

			rsSalida = statement.executeQuery();
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
			log.error("Error SQL en el metodo : getMovCobranzaFecha() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getMovCobranzaFecha()  "
							+ ex);
		}

		return vecSalida;
	}

	/*
	 * Informe de Movimientos de Cobranzas entre fechas
	 */

	public List getMovPagoFecha(Timestamp fechaDesde, Timestamp fechaHasta,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "	SELECT mt.fechamo_mt, LPAD(mt.sucursa_mt::varchar, 4, '0')AS sucursa_mt, LPAD(mt.comprob_mt::varchar, 8, '0') AS comprob_mt,  "
				+ "	       pr.razon_social, mt.cartera_mt, id.descripcion,  "
				+ "	       (CASE WHEN mt.tipo_mt = 1 THEN mt.impori_mt ELSE -mt.impori_mt END)::NUMERIC(18,2) AS impori_mt, "
				+ "	       mt.detalle_mt, mt.nrodoc_mt, cg.horas_cl, mt.cuenta_mt, mt.movsal_mt, mt.nrosal_mt, mt.tipo_mt "
				+ "	  FROM cajamovteso mt "
				+ "	       LEFT JOIN proveedoproveed pr ON mt.idcliente = pr.idproveedor AND mt.idempresa = pr.idempresa "
				+ "	       LEFT JOIN cajaclearing cg ON mt.idclearing = cg.idclearing  AND mt.idempresa = cg.idempresa "
				+ "	       LEFT JOIN cajaidentificadores id ON mt.cartera_mt = id.identificador  AND mt.idempresa = id.idempresa "
				+ "	 WHERE mt.tipomov_mt = 'PAG' AND mt.idempresa = ? AND mt.fechamo_mt BETWEEN ?  AND  ? "
				+ "	 ORDER BY pr.idproveedor, mt.sucursa_mt, mt.comprob_mt, 7 DESC		 ";

		List vecSalida = new ArrayList();
		try {
			PreparedStatement statement = dbconn.prepareStatement(cQuery);
			statement.setBigDecimal(1, idempresa);
			statement.setTimestamp(2, fechaDesde);
			statement.setTimestamp(3, fechaHasta);

			rsSalida = statement.executeQuery();
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
			log.error("Error SQL en el metodo : getMovPagoFecha() "
					+ sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception: en el metodo: getMovPagoFecha()  "
					+ ex);
		}

		return vecSalida;
	}

	/**
	 * DEPOSITOS ... INICIA
	 * 
	 * 
	 */

	/*
	 * Recupera identificadores por valor de identificador
	 */
	public List getCajaIdentificadoresXIdentificador(String identificador,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT ididentificador,idtipoidentificador,identificador,descripcion,cuenta,"
				+ "       idmoneda,modcta,factura,saldo_id,saldo_disp,renglones,ctacaucion,ctatodoc,"
				+ "       gerencia,formula,cuotas,presentacion,ctacaudoc,porcentaje,ctadtotar,ctatarjeta,"
				+ "       comhyper,contador,afecomicob,impri_id,subdiventa,idcencosto,idcencosto1,modicent,"
				+ "       prox_cheq,prox_reserv,ulti_cheq,modsubcent,res_nro,"
				+ "       idempresa,usuarioalt,usuarioact,fechaalt,fechaact"
				+ "  FROM cajaidentificadores" + " WHERE identificador='"
				+ identificador + "' AND idempresa = " + idempresa.toString()
				+ ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/*
	 * Recuperar movimientos de cheques pendientes de deposito.
	 */

	//
	public List getLovChequesPendientesAll(long limit, long offset,
			String cartera_mt, String tipcart_mt, Timestamp fecha_mt,
			String filtroExtra, String operador, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""

				+ "SELECT mt.idmovteso, mt.idclearing, fecha_mt, detalle_mt, impori_mt, nrodoc_mt, deposi_mt, ci.identificador, ci.descripcion "
				+ "  FROM cajamovteso mt "
				+ "       INNER JOIN cajaidentificadores ci ON mt.cartera_mt = ci.identificador "
				+ "         AND mt.idempresa = ci.idempresa "
				+ "       INNER JOIN cajatipoidentificadores cti ON ci.idtipoidentificador = cti.idtipoidentificador "
				+ "         AND mt.tipcart_mt = cti.tipomov AND ci.idempresa = cti.idempresa "
				+ " WHERE  mt.cartera_mt = '" + cartera_mt
				+ "'  AND mt.tipcart_mt = '" + tipcart_mt
				+ "'   AND mt.fecha_mt::DATE " + operador + " '" + fecha_mt
				+ "' " + "  AND cti.propio = 'N'    " + " AND mt.idempresa= "
				+ idempresa + filtroExtra + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET " + offset + ";";

		List vecSalida = getLista(cQuery);

		return vecSalida;
	}

	//

	public List getLovChequesPendientesOcu(long limit, long offset,
			String ocurrencia, String cartera_mt, String tipcart_mt,
			Timestamp fecha_mt, String filtroExtra, String operador,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		// TODO: verificar fecha_mt
		String cQuery = ""
				+ "SELECT mt.idmovteso, mt.idclearing, fecha_mt, detalle_mt, impori_mt, nrodoc_mt, deposi_mt, ci.identificador, ci.descripcion "
				+ "  FROM cajamovteso mt "
				+ "       INNER JOIN cajaidentificadores ci ON mt.cartera_mt = ci.identificador "
				+ "         AND mt.idempresa = ci.idempresa AND mt.usado_mt IS NULL "
				+ "       INNER JOIN cajatipoidentificadores cti ON ci.idtipoidentificador = cti.idtipoidentificador "
				+ "         AND mt.tipcart_mt = cti.tipomov AND ci.idempresa = cti.idempresa "
				+ " WHERE  mt.cartera_mt = ?  AND mt.tipcart_mt = ?   "
				+ "   AND mt.fecha_mt::DATE " + operador
				+ " ?  AND cti.propio = 'N'    "
				+ "   AND  ( UPPER(mt.detalle_mt) LIKE ? "
				+ " OR (mt.nrodoc_mt::VARCHAR) LIKE ? ) AND mt.idempresa=? "
				+ filtroExtra + " ORDER BY 2  LIMIT " + limit + " OFFSET  "
				+ offset + ";";

		List vecSalida = new ArrayList();
		try {
			PreparedStatement statement = dbconn.prepareStatement(cQuery);
			statement.setString(1, cartera_mt);
			statement.setString(2, tipcart_mt);
			statement.setDate(3, new java.sql.Date(fecha_mt.getTime()));
			statement.setString(4, "%" + ocurrencia.toUpperCase().trim() + "%");
			statement.setString(5, "%" + ocurrencia.toUpperCase().trim() + "%");
			statement.setBigDecimal(6, idempresa);

			rsSalida = statement.executeQuery();
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
					.error("Error SQL en el metodo : getLovChequesPendientesOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getLovChequesPendientesOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// -----------------------------------------------------------------

	/*
	 * EFECTIVO
	 */
	public String generarDepositoEfectivo(String identificadorDeposito,
			String identificadorBanco, BigDecimal importe,
			java.sql.Timestamp fecha_teso, String cc1Dep, String cc2Dep,
			String cc1Banco, String cc2Banco, BigDecimal idempresa,
			String usuarioalt) throws EJBException, SQLException {

		String salida = "OK";
		BigDecimal nrodeposito = new BigDecimal(0);
		BigDecimal cambio_mt = new BigDecimal(1);
		BigDecimal sucsal_mt = null;
		BigDecimal idmoneda = new BigDecimal(0);
		BigDecimal idmoneda_aux = new BigDecimal(0);
		BigDecimal cuenta_mt = new BigDecimal(0);

		String modicent = "";

		String[] datos = new String[] {};
		List listDatos = new ArrayList();

		dbconn.setAutoCommit(false);

		try {

			// EJV - 20090512
			// nrodeposito = GeneralBean.getContador(new BigDecimal(20),
			// idempresa, dbconn);

			nrodeposito = GeneralBean.getContador("nrodeposito", idempresa,
					dbconn);

			// --- Banco

			/*
			 * 
			 * ididentificador,idtipoidentificador,identificador,descripcion,cuenta
			 * ,
			 * idmoneda,modcta,factura,saldo_id,saldo_disp,renglones,ctacaucion
			 * ,ctatodoc,
			 * gerencia,formula,cuotas,presentacion,ctacaudoc,porcentaje
			 * ,ctadtotar,ctatarjeta,
			 * comhyper,contador,afecomicob,impri_id,subdiventa
			 * ,idcencosto,idcencosto1,modicent,
			 * prox_cheq,prox_reserv,ulti_cheq,modsubcent,res_nro,
			 * idempresa,usuarioalt,usuarioact,fechaalt,fechaact
			 */

			listDatos = getCajaIdentificadoresXIdentificador(
					identificadorBanco, idempresa);

			if (listDatos != null && !listDatos.isEmpty()) {

				datos = (String[]) listDatos.get(0);
				idmoneda = new BigDecimal(datos[5]);
				idmoneda_aux = idmoneda;
				cuenta_mt = new BigDecimal(datos[4]);
				modicent = datos[30];

				if (modicent.equalsIgnoreCase("N")) {
					cc1Banco = datos[28];
					cc1Banco = datos[29];
				}

				salida = cajaMovTesoCreate(identificadorBanco, nrodeposito,
						fecha_teso, "DEP", "B", new BigDecimal(0), "",
						new BigDecimal(0), fecha_teso, null, fecha_teso,
						importe, importe, null, idmoneda, "*", cambio_mt, null,
						sucsal_mt, null, cuenta_mt, new BigDecimal(0),
						new BigDecimal(1), "S", null, new BigDecimal(cc1Banco),
						new BigDecimal(cc1Banco), usuarioalt, idempresa);

				if (salida.equalsIgnoreCase("OK")) {

					salida = cajaSaldoBcoCreateOrUpdate(identificadorBanco,
							new java.sql.Date(fecha_teso.getTime()), importe,
							importe, usuarioalt, idempresa);

					if (salida.equalsIgnoreCase("OK")) {

						// -- Deposito Efectivo

						listDatos = getCajaIdentificadoresXIdentificador(
								identificadorDeposito, idempresa);

						if (listDatos != null && !listDatos.isEmpty()) {

							datos = (String[]) listDatos.get(0);
							idmoneda = new BigDecimal(datos[5]);
							cuenta_mt = new BigDecimal(datos[4]);
							modicent = datos[30];

							if (idmoneda.compareTo(idmoneda_aux) == 0) {

								if (modicent.equalsIgnoreCase("N")) {
									cc1Dep = datos[28];
									cc1Dep = datos[29];
								}

								salida = cajaMovTesoCreate(
										identificadorDeposito, nrodeposito,
										fecha_teso, "DEP", "E", new BigDecimal(
												0), "", new BigDecimal(0),
										fecha_teso, null, fecha_teso, importe,
										importe, null, idmoneda, "*",
										cambio_mt, null, sucsal_mt, null,
										cuenta_mt, new BigDecimal(0),
										new BigDecimal(2), "S", null,
										new BigDecimal(cc1Dep), new BigDecimal(
												cc2Dep), usuarioalt, idempresa);
								if (salida.equalsIgnoreCase("OK")) {

									salida = cajaSaldoBcoCreateOrUpdate(
											identificadorDeposito,
											new java.sql.Date(fecha_teso
													.getTime()), importe
													.negate(),
											importe.negate(), usuarioalt,
											idempresa);

								}

							} else
								salida = "Identificador y Banco a Depositar deben corresponder a la misma moneda.";

						} else
							salida = "E:02- Imposible recuperar datos de identificador.";
					}

				}

			} else
				salida = "E:01- Imposible recuperar datos de identificador.";

		} catch (Exception e) {
			salida = "E:00- Hubo una excepcion al generar deposito en efectivo.";
			log.error("generarDepositoEfectivo(...): " + e);
		}

		if (!salida.equalsIgnoreCase("OK")) {
			dbconn.rollback();
		} else {
			dbconn.commit();
			salida = nrodeposito.toString();
		}

		dbconn.setAutoCommit(true);
		return salida;

	}

	/*
	 * CHEQUES
	 */

	// 20120619 - EJV - Mantis 850 -->
	public static BigDecimal getCuentaIdentificador(String identificador,
			BigDecimal idempresa, Connection conn) throws EJBException {

		BigDecimal cuenta = new BigDecimal(-9999);
		String cQuery = ""
				+ "SELECT cuenta FROM cajaidentificadores  WHERE identificador = '"
				+ identificador + "' AND idempresa = " + idempresa;

		List listCuenta = new ArrayList();

		try {

			listCuenta = getLista(cQuery, conn);

			if (listCuenta != null) {

				if (!listCuenta.isEmpty()) {

					String[] datos = (String[]) listCuenta.get(0);
					cuenta = new BigDecimal(datos[0]);

				} else {
					cuenta = new BigDecimal(-9980);
					log
							.warn("(E1)getCuentaIdentificador(): No fue posible recuperar cuenta identificador "
									+ identificador);
				}

			} else {
				cuenta = new BigDecimal(-9990);
				log
						.warn("(E0)getCuentaIdentificador(): No fue posible recuperar cuenta identificador "
								+ identificador);
			}

		} catch (Exception e) {

			log.error("getCuentaIdentificador(): " + e);

		}

		return cuenta;
	}

	// <--

	// 1- CARTERA
	public String[] generarDepositosCHQCartera(String identificadorDeposito,
			String identificadorBanco, BigDecimal importe,
			java.sql.Timestamp fecha_teso, Hashtable htCheques, String cc1Dep,
			String cc2Dep, String cc1Banco, String cc2Banco,
			BigDecimal idempresa, String usuarioalt) throws EJBException,
			SQLException {

		String salida = "OK";
		String[] resultado = new String[] { "", "" };
		Enumeration en;

		Hashtable htAgrupaXClearing = new Hashtable();
		Hashtable htAgrupaXCuentaDeposito = new Hashtable();
		Hashtable htFeriados = new Hashtable();
		Hashtable htClearing = new Hashtable();
		int dias_clearing = 0;

		String cuentaDeposito = "";
		BigDecimal nrodeposito = null;

		BigDecimal cero = new BigDecimal(0);
		BigDecimal cambio_mt = new BigDecimal(1);
		BigDecimal sucsal_mt = null;
		BigDecimal idmoneda = new BigDecimal(0);
		BigDecimal cuenta_mt = new BigDecimal(0);
		java.sql.Date fclear_mt = null;
		BigDecimal saldo_cont = new BigDecimal(0);
		BigDecimal saldo_disp = new BigDecimal(0);

		/*
		 * 
		 * i.ididentificador,ti.tipoidentificador,i.identificador,i.descripcion,
		 * i.cuenta, i.idmoneda, ti.tipomov, ti.propio, i.modcta, i.factura,
		 * i.saldo_id, i.contador, i.ctacaudoc,i.saldo_disp, i.ctacaucion,
		 * i.ctatodoc, i.cuotas,i.ctadtotar, i.ctatarjeta, i.subdiventa,
		 * i.idcencosto, i.idcencosto1, i.modicent, i.modsubcent, i.prox_cheq,
		 * i.prox_reserv,i.ulti_cheq, i.res_nro, mt.importe_mt, mt.detalle_mt ,
		 * nroint_mt, to_char(fecha_mt, 'dd/mm/yyyy') as fecha,
		 * COALESCE(mt.idclearing, 0) as clearing, 1 as cuotaspaga,
		 * mt.idmovteso, mt.impori_mt::numeric(18,2),
		 * mt.importe_mt::numeric(18,2) as importevalidar, mt.nrodoc_mt,
		 * mt.cuenta_mt
		 */

		dbconn.setAutoCommit(false);

		try {

			en = htCheques.keys();
			htFeriados = getCajaFeriadosFecha(idempresa);
			htClearing = getCajaDiasClearingAll(idempresa);

			while (en.hasMoreElements()) {
				String key = en.nextElement().toString();
				String[] datos = (String[]) htCheques.get(key);
				BigDecimal importeCheque = null;
				importeCheque = new BigDecimal(datos[28]);

				// -- Inicia: Generar datos del banco.
				// TODO: marcar como depositados ....

				dias_clearing = Integer.parseInt(htClearing.get(datos[32])
						.toString());
				fclear_mt = GeneralBean.setFechaClearing(new java.sql.Date(
						fecha_teso.getTime()), dias_clearing, htFeriados);

				// Discriminar depositos por tipo de Clearing: distinto
				// clearing, nuevo deposito.

				saldo_cont = saldo_cont.add(importeCheque);

				if (dias_clearing == 0)
					saldo_disp = saldo_disp.add(importeCheque);

				if (!htAgrupaXClearing.containsKey(datos[32])) {

					// 20120619 - EJV - Mantis 850 -- Hasta la fecha estaba
					// guardando la cuenta correspondiente al identificador del
					// cheque, y no la del banco. Es posible que haya que
					// verificar la cuenta del cheque, ya que esta guardando la
					// que se selecciono en el ingreso del documento. Ver [#1#]
					// -->
					// cuenta_mt = new BigDecimal(datos[4]);
					cuenta_mt = getCuentaIdentificador(identificadorBanco,
							idempresa, dbconn);

					if (cuenta_mt == null || cuenta_mt.intValue() < 0) {
						salida = "No fue posible recuperar cuenta para identificador "
								+ identificadorBanco + " - " + cuenta_mt;
						break;
					}

					// <--
					idmoneda = new BigDecimal(datos[5]);

					// EJV - 20090512
					// nrodeposito = GeneralBean.getContador(new BigDecimal(20),
					// idempresa, dbconn);

					nrodeposito = GeneralBean.getContador("nrodeposito",
							idempresa, dbconn);

					salida = cajaMovTesoCreate(identificadorBanco, nrodeposito,
							fecha_teso, "DEP", "B", cero, "", cero, fecha_teso,
							new BigDecimal(datos[32]), new Timestamp(fclear_mt
									.getTime()), importeCheque, importeCheque,
							null, idmoneda, "*", cambio_mt, null, sucsal_mt,
							cero, cuenta_mt, cero, new BigDecimal(1), "S",
							null, new BigDecimal(cc1Banco), new BigDecimal(
									cc2Banco), usuarioalt, idempresa);

					if (!salida.equalsIgnoreCase("OK"))
						break;

					BigDecimal idmovteso = GeneralBean.getValorSequencia(
							"seq_cajamovteso", dbconn);

					if (idmovteso == null) {
						salida = "Imposible capturar el nro. de movimiento de tesoreria.";
						break;
					}

					salida = cajaSaldaMovimientosTeso(
							new BigDecimal(datos[34]), "DEP", sucsal_mt,
							nrodeposito, "S", "S", usuarioalt, idempresa,
							dbconn);

					htAgrupaXClearing.put(datos[32], new BigDecimal[] {
							idmovteso, nrodeposito });

					resultado[1] += nrodeposito + "-";

				} else {

					BigDecimal[] movTesoDeposito = (BigDecimal[]) htAgrupaXClearing
							.get(datos[32]);
					BigDecimal idmovteso = movTesoDeposito[0];
					nrodeposito = movTesoDeposito[1];

					salida = cajaSaldaMovimientosTeso(
							new BigDecimal(datos[34]), "DEP", sucsal_mt,
							nrodeposito, "S", "S", usuarioalt, idempresa,
							dbconn);

					if (!salida.equalsIgnoreCase("OK"))
						break;

					salida = cajaMovTesoActualizaImportes(idmovteso,
							importeCheque, usuarioalt, idempresa);

				}

				// -- Finaliza: Generar datos del banco.

				if (!salida.equalsIgnoreCase("OK"))
					break;

				// -- Inicia: Generar datos de contrapartida.

				cuentaDeposito = datos[38] + "-" + nrodeposito.toString();

				// Genera de forma independiente la contrapartida, cuando cambia
				// cuenta_mt o deposito.
				// Distinta cuenta_mt => nueva contrapartida.
				// Distinto deposito => nueva contrapartida.
				if (!htAgrupaXCuentaDeposito.containsKey(cuentaDeposito)) {
					// 20120619 [#1#] Es posible que no sea la correcta -->
					cuenta_mt = new BigDecimal(datos[38]);
					// <--
					salida = cajaMovTesoCreate(identificadorDeposito,
							nrodeposito, fecha_teso, "DEP", "C", cero, "",
							cero, fecha_teso, new BigDecimal(datos[32]),
							fecha_teso, importeCheque, importeCheque, null,
							idmoneda, "*", cambio_mt, null, sucsal_mt, cero,
							cuenta_mt, cero, new BigDecimal(2), "S", null,
							new BigDecimal(cc1Dep), new BigDecimal(cc2Dep),
							usuarioalt, idempresa);

					if (!salida.equalsIgnoreCase("OK"))
						break;

					BigDecimal idmovteso = GeneralBean.getValorSequencia(
							"seq_cajamovteso", dbconn);

					if (idmovteso == null) {
						salida = "Imposible capturar el nro. de movimiento de tesoreria II.";
						break;
					}

					htAgrupaXCuentaDeposito.put(cuentaDeposito,
							new BigDecimal[] { idmovteso, nrodeposito });

				} else {

					BigDecimal[] movTesoDeposito = (BigDecimal[]) htAgrupaXCuentaDeposito
							.get(cuentaDeposito);
					BigDecimal idmovteso = movTesoDeposito[0];
					nrodeposito = movTesoDeposito[1];

					salida = cajaMovTesoActualizaImportes(idmovteso,
							importeCheque, usuarioalt, idempresa);

				}
				if (!salida.equalsIgnoreCase("OK"))
					break;
				// -- Finaliza: Generar datos de contrapartida.

			}

			// Actualizar saldos ...
			if (salida.equalsIgnoreCase("OK"))
				salida = cajaSaldoBcoCreateOrUpdate(identificadorBanco,
						new java.sql.Date(fecha_teso.getTime()), saldo_cont,
						saldo_disp, usuarioalt, idempresa);

		} catch (Exception e) {
			salida = "E:0 - Error al generar deposito de cheques en cartera.";
			log.error("generarDepositosCHQCartera():" + e);
		}

		if (!salida.equalsIgnoreCase("OK")) {
			dbconn.rollback();
		} else {
			dbconn.commit();
		}

		resultado[0] = salida;
		dbconn.setAutoCommit(true);
		return resultado;

	}

	// 2- CAUCIONADOS
	public String[] generarDepositosCHQCaucionados(
			String identificadorDeposito, String identificadorBanco,
			BigDecimal importe, java.sql.Timestamp fecha_teso,
			Hashtable htCheques, String cc1Dep, String cc2Dep, String cc1Banco,
			String cc2Banco, BigDecimal idempresa, String usuarioalt)
			throws EJBException, SQLException {

		String salida = "OK";
		String[] resultado = new String[] { "", "" };
		Enumeration en;

		Hashtable htAgrupaXClearing = new Hashtable();
		Hashtable htAgrupaXCuentaDeposito = new Hashtable();
		Hashtable htFeriados = new Hashtable();
		Hashtable htClearing = new Hashtable();
		int dias_clearing = 0;

		String cuentaDeposito = "";
		BigDecimal nrodeposito = null;

		BigDecimal cero = new BigDecimal(0);
		BigDecimal cambio_mt = new BigDecimal(1);
		BigDecimal sucsal_mt = null;
		BigDecimal idmoneda = new BigDecimal(0);
		BigDecimal cuenta_mt = new BigDecimal(0);
		java.sql.Date fclear_mt = null;
		BigDecimal saldo_cont = new BigDecimal(0);
		BigDecimal saldo_disp = new BigDecimal(0);

		/*
		 * 
		 * i.ididentificador,ti.tipoidentificador,i.identificador,i.descripcion,
		 * i.cuenta, i.idmoneda, ti.tipomov, ti.propio, i.modcta, i.factura,
		 * i.saldo_id, i.contador, i.ctacaudoc,i.saldo_disp, i.ctacaucion,
		 * i.ctatodoc, i.cuotas,i.ctadtotar, i.ctatarjeta, i.subdiventa,
		 * i.idcencosto, i.idcencosto1, i.modicent, i.modsubcent, i.prox_cheq,
		 * i.prox_reserv,i.ulti_cheq, i.res_nro, mt.importe_mt, mt.detalle_mt ,
		 * nroint_mt, to_char(fecha_mt, 'dd/mm/yyyy') as fecha,
		 * COALESCE(mt.idclearing, 0) as clearing, 1 as cuotaspaga,
		 * mt.idmovteso, mt.impori_mt::numeric(18,2),
		 * mt.importe_mt::numeric(18,2) as importevalidar, mt.nrodoc_mt,
		 * mt.cuenta_mt
		 */

		dbconn.setAutoCommit(false);

		try {

			en = htCheques.keys();
			htFeriados = getCajaFeriadosFecha(idempresa);
			htClearing = getCajaDiasClearingAll(idempresa);

			while (en.hasMoreElements()) {
				String key = en.nextElement().toString();
				String[] datos = (String[]) htCheques.get(key);
				BigDecimal importeCheque = null;
				importeCheque = new BigDecimal(datos[28]);

				// -- Inicia: Generar datos del banco.
				// TODO: marcar como depositados ....

				dias_clearing = Integer.parseInt(htClearing.get(datos[32])
						.toString());
				fclear_mt = GeneralBean.setFechaClearing(new java.sql.Date(
						fecha_teso.getTime()), dias_clearing, htFeriados);

				// Discriminar depositos por tipo de Clearing: distinto
				// clearing, nuevo deposito.

				saldo_cont = saldo_cont.add(importeCheque);

				if (dias_clearing == 0)
					saldo_disp = saldo_disp.add(importeCheque);

				if (!htAgrupaXClearing.containsKey(datos[32])) {

					cuenta_mt = new BigDecimal(datos[4]);
					idmoneda = new BigDecimal(datos[5]);

					// EJV - 20090512
					// nrodeposito = GeneralBean.getContador(new BigDecimal(20),
					// idempresa, dbconn);

					nrodeposito = GeneralBean.getContador("nrodeposito",
							idempresa, dbconn);

					salida = cajaMovTesoCreate(identificadorBanco, nrodeposito,
							fecha_teso, "DEP", "B", cero, "", cero, fecha_teso,
							new BigDecimal(datos[32]), new Timestamp(fclear_mt
									.getTime()), importeCheque, importeCheque,
							null, idmoneda, "*", cambio_mt, null, sucsal_mt,
							cero, cuenta_mt, cero, new BigDecimal(1), "S",
							null, new BigDecimal(cc1Banco), new BigDecimal(
									cc2Banco), usuarioalt, idempresa);

					if (!salida.equalsIgnoreCase("OK"))
						break;

					BigDecimal idmovteso = GeneralBean.getValorSequencia(
							"seq_cajamovteso", dbconn);

					if (idmovteso == null) {
						salida = "Imposible capturar el nro. de movimiento de tesoreria.";
						break;
					}

					salida = cajaSaldaMovimientosTeso(
							new BigDecimal(datos[34]), "DEP", sucsal_mt,
							nrodeposito, "S", "S", usuarioalt, idempresa,
							dbconn);

					htAgrupaXClearing.put(datos[32], new BigDecimal[] {
							idmovteso, nrodeposito });

					resultado[1] += nrodeposito + "-";

				} else {

					BigDecimal[] movTesoDeposito = (BigDecimal[]) htAgrupaXClearing
							.get(datos[32]);
					BigDecimal idmovteso = movTesoDeposito[0];
					nrodeposito = movTesoDeposito[1];

					salida = cajaSaldaMovimientosTeso(
							new BigDecimal(datos[34]), "DEP", sucsal_mt,
							nrodeposito, "S", "S", usuarioalt, idempresa,
							dbconn);

					if (!salida.equalsIgnoreCase("OK"))
						break;

					salida = cajaMovTesoActualizaImportes(idmovteso,
							importeCheque, usuarioalt, idempresa);

				}

				// -- Finaliza: Generar datos del banco.

				if (!salida.equalsIgnoreCase("OK"))
					break;

				// -- Inicia: Generar datos de contrapartida.

				cuentaDeposito = datos[39] + "-" + nrodeposito.toString();

				if (datos[39] == null || datos[39].trim().equals("")
						|| datos[39].trim().equals("0")) {
					salida = "Cuenta caucion no definida para el identificador: "
							+ identificadorBanco + ".";
					break;
				}

				// Genera de forma independiente la contrapartida, cuando cambia
				// cuenta_mt o deposito.
				// Distinta cuenta_mt => nueva contrapartida.
				// Distinto deposito => nueva contrapartida.
				if (!htAgrupaXCuentaDeposito.containsKey(cuentaDeposito)) {

					cuenta_mt = new BigDecimal(datos[39]);

					salida = cajaMovTesoCreate(identificadorBanco, nrodeposito,
							fecha_teso, "DEP", "C", cero, "", cero, fecha_teso,
							new BigDecimal(datos[32]), fecha_teso,
							importeCheque, importeCheque, null, idmoneda, "*",
							cambio_mt, null, sucsal_mt, cero, cuenta_mt, cero,
							new BigDecimal(2), "S", null,
							new BigDecimal(cc1Dep), new BigDecimal(cc2Dep),
							usuarioalt, idempresa);

					if (!salida.equalsIgnoreCase("OK"))
						break;

					BigDecimal idmovteso = GeneralBean.getValorSequencia(
							"seq_cajamovteso", dbconn);

					if (idmovteso == null) {
						salida = "Imposible capturar el nro. de movimiento de tesoreria II.";
						break;
					}

					htAgrupaXCuentaDeposito.put(cuentaDeposito,
							new BigDecimal[] { idmovteso, nrodeposito });

				} else {

					BigDecimal[] movTesoDeposito = (BigDecimal[]) htAgrupaXCuentaDeposito
							.get(cuentaDeposito);
					BigDecimal idmovteso = movTesoDeposito[0];
					nrodeposito = movTesoDeposito[1];

					salida = cajaMovTesoActualizaImportes(idmovteso,
							importeCheque, usuarioalt, idempresa);

				}
				if (!salida.equalsIgnoreCase("OK"))
					break;
				// -- Finaliza: Generar datos de contrapartida.

			}

			// Actualizar saldos ...

			if (salida.equalsIgnoreCase("OK"))
				salida = cajaSaldoBcoCreateOrUpdate(identificadorBanco,
						new java.sql.Date(fecha_teso.getTime()), saldo_cont,
						saldo_disp, usuarioalt, idempresa);

		} catch (Exception e) {
			salida = "E:0 - Error al generar deposito de cheques caucionados.";
			log.error("generarDepositosCHQCaucionados():" + e);
		}

		if (!salida.equalsIgnoreCase("OK")) {
			dbconn.rollback();
		} else {
			dbconn.commit();
		}

		resultado[0] = salida;
		dbconn.setAutoCommit(true);
		return resultado;
	}

	// 3- CAUCION

	public String[] generarDepositosCHQCaucion(String identificadorDeposito,
			String identificadorBanco, BigDecimal importe,
			java.sql.Timestamp fecha_teso, Hashtable htCheques, String cc1Dep,
			String cc2Dep, String cc1Banco, String cc2Banco,
			BigDecimal idempresa, String usuarioalt) throws EJBException,
			SQLException {

		String salida = "OK";
		String[] resultado = new String[] { "", "" };
		Enumeration en;

		BigDecimal nrodeposito = null;

		BigDecimal cero = new BigDecimal(0);
		BigDecimal cambio_mt = new BigDecimal(1);
		BigDecimal sucsal_mt = null;
		BigDecimal idmoneda = new BigDecimal(0);
		BigDecimal cuenta_mt = new BigDecimal(0);

		/*
		 * 
		 * i.ididentificador,ti.tipoidentificador,i.identificador,i.descripcion,
		 * i.cuenta, i.idmoneda, ti.tipomov, ti.propio, i.modcta, i.factura,
		 * i.saldo_id, i.contador, i.ctacaudoc,i.saldo_disp, i.ctacaucion,
		 * i.ctatodoc, i.cuotas,i.ctadtotar, i.ctatarjeta, i.subdiventa,
		 * i.idcencosto, i.idcencosto1, i.modicent, i.modsubcent, i.prox_cheq,
		 * i.prox_reserv,i.ulti_cheq, i.res_nro, mt.importe_mt, mt.detalle_mt ,
		 * nroint_mt, to_char(fecha_mt, 'dd/mm/yyyy') as fecha,
		 * COALESCE(mt.idclearing, 0) as clearing, 1 as cuotaspaga,
		 * mt.idmovteso, mt.impori_mt::numeric(18,2),
		 * mt.importe_mt::numeric(18,2) as importevalidar, mt.nrodoc_mt,
		 * mt.cuenta_mt
		 */

		dbconn.setAutoCommit(false);

		try {

			en = htCheques.keys();
			// EJV - 20090512
			// nrodeposito = GeneralBean.getContador(new BigDecimal(20),
			// idempresa, dbconn);

			nrodeposito = GeneralBean.getContador("nrodeposito", idempresa,
					dbconn);

			if (nrodeposito != null) {

				while (en.hasMoreElements()) {
					String key = en.nextElement().toString();
					String[] datos = (String[]) htCheques.get(key);
					BigDecimal importeCheque = null;
					importeCheque = new BigDecimal(datos[28]);

					// -- Inicia: Generar datos del banco.
					// TODO: marcar como depositados ....
					BigDecimal idmovteso = new BigDecimal(datos[34]);
					String[] datosMov = (String[]) getCajaMovTesoPK(idmovteso,
							idempresa).get(0);
					Timestamp fecha_mt = Timestamp.valueOf(datosMov[9]);
					Timestamp fclear_mt = Timestamp.valueOf(datosMov[11]);
					cuenta_mt = new BigDecimal(datosMov[21]);

					/*
					 * 
					 * idmovteso,cartera_mt,comprob_mt,fechamo_mt,tipomov_mt,tipcart_mt
					 * ,nroint_mt,
					 * detalle_mt,nrodoc_mt,fecha_mt,idclearing,fclear_mt
					 * ,impori_mt,importe_mt,
					 * deposi_mt,idmoneda,unamone_mt,cambio_mt
					 * ,movsal_mt,sucsal_mt,nrosal_mt,cuenta_mt,
					 * idcliente,tipo_mt
					 * ,usado_mt,anulada_mt,idcencosto,idcencosto1,sucursa_mt,
					 */

					// TODO: 20090807
					// EJV
					// Revisar el arrastre de los datos ... por ej nrodoc_mt no
					// esta respetando el del registro origen, lo graba en 0
					salida = cajaMovTesoCreate(identificadorDeposito,
							nrodeposito, fecha_teso, "DEP", "C", cero,
							datosMov[7], cero, fecha_mt, null, fclear_mt,
							importeCheque, importeCheque, null, idmoneda, "*",
							cambio_mt, identificadorBanco, sucsal_mt, null,
							cuenta_mt, cero, new BigDecimal(2), "U", null,
							new BigDecimal(cc1Dep), new BigDecimal(cc2Dep),
							usuarioalt, idempresa);

					if (!salida.equalsIgnoreCase("OK"))
						break;

					salida = cajaSaldaMovimientosTeso(idmovteso, "DEP",
							sucsal_mt, nrodeposito, "S", "S", usuarioalt,
							idempresa, dbconn);

					if (!salida.equalsIgnoreCase("OK"))
						break;

					resultado[1] += nrodeposito + "-";

				}

				if (salida.equalsIgnoreCase("OK")) {

					String[] datosIdent = (String[]) getCajaIdentificadoresXIdentificador(
							identificadorBanco, idempresa).get(0);
					cuenta_mt = new BigDecimal(datosIdent[11]);

					if (datosIdent[5] != null) {

						idmoneda = new BigDecimal(datosIdent[5]);

						salida = cajaMovTesoCreate(identificadorBanco,
								nrodeposito, fecha_teso, "DEP", "U", cero, "",
								cero, fecha_teso, null, fecha_teso, importe,
								importe, null, idmoneda, "*", cambio_mt,
								identificadorBanco, sucsal_mt, null, cuenta_mt,
								cero, new BigDecimal(1), "S", null,
								new BigDecimal(cc1Dep), new BigDecimal(cc2Dep),
								usuarioalt, idempresa);

					} else {
						salida = "El Banco " + identificadorBanco
								+ ", no tiene definida la cuenta de caucion.";
					}

				}

			} else {
				salida = "Imposible recuperar proximo deposito.";
			}

		} catch (Exception e) {
			salida = "E:0 - Error al generar deposito de cheques en caucion.";
			log.error("generarDepositosCHQCaucion():" + e);
		}

		if (!salida.equalsIgnoreCase("OK")) {
			dbconn.rollback();
		} else {
			dbconn.commit();
		}

		resultado[0] = salida;
		dbconn.setAutoCommit(true);

		return resultado;
	}

	/*
	 * TARJETA
	 */

	/*
	 * DOCUMENTOS
	 */

	/**
	 * DEPOSITOS ... FINALIZA
	 */

	/**
	 * Metodos para la entidad: cajaIdentificadores Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Fri Oct 17 10:32:33 GMT-03:00 2008
	 */
	// para todo (ordena por el segundo campo por defecto)
	public List getCajaIdentificadoresAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT i.ididentificador,ti.tipoidentificador,i.identificador,i.descripcion,"
				+ "       i.cuenta,i.idmoneda,i.modcta,i.factura,i.saldo_id::numeric(18, 2),i.saldo_disp::numeric(18, 2),i.renglones,"
				+ "       i.ctacaucion,i.ctatodoc,i.gerencia,i.formula,i.cuotas,i.presentacion,"
				+ "       i.ctacaudoc,i.porcentaje::numeric(18, 2),i.ctadtotar,i.ctatarjeta,i.comhyper,i.contador,i.afecomicob,"
				+ "       i.impri_id,i.subdiventa,i.idcencosto,i.idcencosto1,i.modicent,i.prox_cheq,"
				+ "       i.prox_reserv,i.ulti_cheq,i.modsubcent,i.res_nro,ti.propio,ti.tipomov,"
				+ "       i.idempresa,i.usuarioalt,i.usuarioact,i.fechaalt,i.fechaact "
				+ "  FROM cajaidentificadores i "
				+ "       INNER JOIN cajatipoidentificadores ti  ON i.idtipoidentificador = ti.idtipoidentificador AND i.idempresa = ti.idempresa "
				+ " WHERE i.idempresa = " + idempresa.toString()
				+ "  ORDER BY 2, 3  LIMIT " + limit + " OFFSET  " + offset
				+ ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getCajaIdentificadoresOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT i.ididentificador,ti.tipoidentificador,i.identificador,i.descripcion,"
				+ "       i.cuenta,i.idmoneda,i.modcta,i.factura,i.saldo_id::numeric(18, 2),i.saldo_disp::numeric(18, 2),i.renglones,"
				+ "       i.ctacaucion,i.ctatodoc,i.gerencia,i.formula,i.cuotas,i.presentacion,"
				+ "       i.ctacaudoc,i.porcentaje::numeric(18, 2),i.ctadtotar,i.ctatarjeta,i.comhyper,i.contador,i.afecomicob,"
				+ "       i.impri_id,i.subdiventa,i.idcencosto,i.idcencosto1,i.modicent,i.prox_cheq,"
				+ "       i.prox_reserv,i.ulti_cheq,i.modsubcent,i.res_nro,ti.propio,ti.tipomov,"
				+ "       i.idempresa,i.usuarioalt,i.usuarioact,i.fechaalt,i.fechaact "
				+ "  FROM cajaidentificadores i "
				+ "       INNER JOIN cajatipoidentificadores ti  ON i.idtipoidentificador = ti.idtipoidentificador AND i.idempresa = ti.idempresa "
				+ " WHERE (UPPER(i.identificador) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(i.descripcion) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%')  AND i.idempresa = "
				+ idempresa.toString() + " ORDER BY 2, 3  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getCajaIdentificadoresPK(BigDecimal ididentificador,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT i.ididentificador,ti.idtipoidentificador,ti.tipoidentificador,i.identificador,i.descripcion,"
				+ "       i.cuenta,i.idmoneda,gm.moneda,i.modcta,i.factura,i.saldo_id::numeric(18, 2),i.saldo_disp::numeric(18, 2),i.renglones,"
				+ "       i.ctacaucion,i.ctatodoc,i.gerencia,i.formula,i.cuotas,i.presentacion,"
				+ "       i.ctacaudoc,i.porcentaje::numeric(18, 2),i.ctadtotar,i.ctatarjeta,i.comhyper,i.contador,i.afecomicob,"
				+ "       i.impri_id,i.subdiventa,i.idcencosto,i.idcencosto1,i.modicent,i.prox_cheq,"
				+ "       i.prox_reserv,i.ulti_cheq,i.modsubcent,i.res_nro,"
				+ "       i.idempresa,i.usuarioalt,i.usuarioact,i.fechaalt,i.fechaact "
				+ "  FROM cajaidentificadores i "
				+ "       INNER JOIN cajatipoidentificadores ti  ON i.idtipoidentificador = ti.idtipoidentificador AND i.idempresa = ti.idempresa "
				+ "       INNER JOIN globalmonedas gm  ON i.idmoneda = gm.idmoneda "
				+ " WHERE i.ididentificador=" + ididentificador.toString()
				+ "   AND i.idempresa = " + idempresa.toString() + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String cajaIdentificadoresDelete(BigDecimal ididentificador,
			String identificador, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM CAJAIDENTIFICADORES WHERE ididentificador="
				+ ididentificador.toString()
				+ " AND idempresa="
				+ idempresa.toString();
		String salida = "OK";
		try {

			if (!GeneralBean.hasMovTesoIdentificador(identificador, idempresa,
					dbconn)) {

				Statement statement = dbconn.createStatement();
				rsSalida = statement.executeQuery(cQuery);
				if (rsSalida.next()) {

					salida = cajaValorTarDelete(identificador, "P", idempresa);

					salida = cajaValorTarDelete(identificador, "C", idempresa);

					cQuery = "DELETE FROM CAJAIDENTIFICADORES WHERE ididentificador="
							+ ididentificador.toString().toString()
							+ " AND idempresa=" + idempresa.toString();
					statement.execute(cQuery);
					salida = "Baja Correcta.";
				} else {
					salida = "Error: Registro inexistente";
				}

			} else {
				salida = "Imposible eliminar identificador, tiene movimientos asociados.";
			}

		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : cajaIdentificadoresDelete( BigDecimal ididentificador, BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: cajaIdentificadoresDelete( BigDecimal ididentificador, BigDecimal idempresa )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String cajaIdentificadoresCreate(BigDecimal idtipoidentificador,
			String identificador, String descripcion, BigDecimal cuenta,
			BigDecimal idmoneda, String modcta, String factura,
			Double saldo_id, Double saldo_disp, BigDecimal renglones,
			BigDecimal ctacaucion, BigDecimal ctatodoc, String gerencia,
			String formula, BigDecimal cuotas, BigDecimal presentacion,
			BigDecimal ctacaudoc, Double porcentaje, BigDecimal ctadtotar,
			BigDecimal ctatarjeta, BigDecimal comhyper, BigDecimal contador,
			String afecomicob, String impri_id, String subdiventa,
			String idcencosto, String idcencosto1, String modicent,
			BigDecimal prox_cheq, BigDecimal prox_reserv, BigDecimal ulti_cheq,
			String modsubcent, BigDecimal res_nro, Hashtable htCuotas,
			Hashtable htPresentacion, BigDecimal idempresa, String usuarioalt)
			throws EJBException, SQLException {
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idtipoidentificador == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipoidentificador ";
		if (identificador == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: identificador ";
		if (descripcion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: descripcion ";
		if (idmoneda == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idmoneda ";
		if (modcta == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: modcta ";
		if (factura == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: factura ";
		if (renglones == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: renglones ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (identificador.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: identificador ";
		if (descripcion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: descripcion ";
		if (modcta.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: modcta ";
		if (factura.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: factura ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones

		try {
			if (salida.equalsIgnoreCase("OK")) {
				String ins = ""
						+ "INSERT INTO CAJAIDENTIFICADORES"
						+ " (idtipoidentificador, identificador, descripcion, cuenta, idmoneda, modcta, "
						+ "  factura, saldo_id, saldo_disp, renglones, ctacaucion, ctatodoc, gerencia, "
						+ "  formula, cuotas, presentacion, ctacaudoc, porcentaje, ctadtotar, ctatarjeta, "
						+ "  comhyper, contador, afecomicob, impri_id, subdiventa, idcencosto, idcencosto1, "
						+ "  modicent, prox_cheq, prox_reserv, ulti_cheq, modsubcent, res_nro, "
						+ "  idempresa, usuarioalt )"
						+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, idtipoidentificador);
				insert.setString(2, identificador);
				insert.setString(3, descripcion);
				insert.setBigDecimal(4, cuenta);
				insert.setBigDecimal(5, idmoneda);
				insert.setString(6, modcta);
				insert.setString(7, factura);
				insert.setDouble(8, saldo_id.doubleValue());
				insert.setDouble(9, saldo_disp.doubleValue());
				insert.setBigDecimal(10, renglones);
				insert.setBigDecimal(11, ctacaucion);
				insert.setBigDecimal(12, ctatodoc);
				insert.setString(13, gerencia);
				insert.setString(14, formula);
				insert.setBigDecimal(15, cuotas);
				insert.setBigDecimal(16, presentacion);
				insert.setBigDecimal(17, ctacaudoc);
				insert.setDouble(18, porcentaje.doubleValue());
				insert.setBigDecimal(19, ctadtotar);
				insert.setBigDecimal(20, ctatarjeta);
				insert.setBigDecimal(21, comhyper);
				insert.setBigDecimal(22, contador);
				insert.setString(23, afecomicob);
				insert.setString(24, impri_id);
				insert.setString(25, subdiventa);
				insert.setString(26, idcencosto);
				insert.setString(27, idcencosto1);
				insert.setString(28, modicent);
				insert.setBigDecimal(29, prox_cheq);
				insert.setBigDecimal(30, prox_reserv);
				insert.setBigDecimal(31, ulti_cheq);
				insert.setString(32, modsubcent);
				insert.setBigDecimal(33, res_nro);
				insert.setBigDecimal(34, idempresa);
				insert.setString(35, usuarioalt);
				int n = insert.executeUpdate();

				if (n == 1) {

					if (idtipoidentificador.longValue() == 7) {

						if (htCuotas != null && !htCuotas.isEmpty()) {

							String[] dias = (String[]) htCuotas.get("dias");
							String[] numero = (String[]) htCuotas.get("numero");

							for (int f = 0; f < dias.length
									&& salida.equalsIgnoreCase("OK"); f++) {
								salida = cajaValorTarCreate(identificador, "C",
										new BigDecimal(numero[f]),
										new BigDecimal(dias[f]), idempresa,
										usuarioalt);

							}

							if (htPresentacion != null
									&& !htPresentacion.isEmpty()) {

								dias = (String[]) htPresentacion.get("dias");
								numero = (String[]) htPresentacion
										.get("numero");

								for (int f = 0; f < dias.length
										&& salida.equalsIgnoreCase("OK"); f++) {
									salida = cajaValorTarCreate(identificador,
											"P", new BigDecimal(numero[f]),
											new BigDecimal(dias[f]), idempresa,
											usuarioalt);

								}

							}

							// TODO: presentacion de tarjetas
							// cajatarjpre !!!!!

						} else
							salida = "Detalle de cuotas no definido.";

					}

				} else
					salida = "Imposible generar identificador.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String cajaIdentificadoresCreate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String cajaIdentificadoresCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String cajaIdentificadoresCreateOrUpdate(BigDecimal ididentificador,
			BigDecimal idtipoidentificador, String identificador,
			String descripcion, BigDecimal cuenta, BigDecimal idmoneda,
			String modcta, String factura, Double saldo_id, Double saldo_disp,
			BigDecimal renglones, BigDecimal ctacaucion, BigDecimal ctatodoc,
			String gerencia, String formula, BigDecimal cuotas,
			BigDecimal presentacion, BigDecimal ctacaudoc, Double porcentaje,
			BigDecimal ctadtotar, BigDecimal ctatarjeta, BigDecimal comhyper,
			BigDecimal contador, String afecomicob, String impri_id,
			String subdiventa, String idcencosto, String idcencosto1,
			String modicent, BigDecimal prox_cheq, BigDecimal prox_reserv,
			BigDecimal ulti_cheq, String modsubcent, BigDecimal res_nro,
			BigDecimal idempresa, String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (ididentificador == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: ididentificador ";
		if (idtipoidentificador == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipoidentificador ";
		if (identificador == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: identificador ";
		if (descripcion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: descripcion ";
		if (idmoneda == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idmoneda ";
		if (modcta == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: modcta ";
		if (factura == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: factura ";
		if (renglones == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: renglones ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (identificador.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: identificador ";
		if (descripcion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: descripcion ";
		if (modcta.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: modcta ";
		if (factura.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: factura ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM cajaIdentificadores WHERE ididentificador = "
					+ ididentificador.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE CAJAIDENTIFICADORES SET idtipoidentificador=?, identificador=?, descripcion=?, cuenta=?, idmoneda=?, modcta=?, factura=?, saldo_id=?, saldo_disp=?, renglones=?, ctacaucion=?, ctatodoc=?, gerencia=?, formula=?, cuotas=?, presentacion=?, ctacaudoc=?, porcentaje=?, ctadtotar=?, ctatarjeta=?, comhyper=?, contador=?, afecomicob=?, impri_id=?, subdiventa=?, idcencosto=?, idcencosto1=?, modicent=?, prox_cheq=?, prox_reserv=?, ulti_cheq=?, modsubcent=?, res_nro=?, idempresa=?, usuarioact=?, fechaact=? WHERE ididentificador=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, idtipoidentificador);
					insert.setString(2, identificador);
					insert.setString(3, descripcion);
					insert.setBigDecimal(4, cuenta);
					insert.setBigDecimal(5, idmoneda);
					insert.setString(6, modcta);
					insert.setString(7, factura);
					insert.setDouble(8, saldo_id.doubleValue());
					insert.setDouble(9, saldo_disp.doubleValue());
					insert.setBigDecimal(10, renglones);
					insert.setBigDecimal(11, ctacaucion);
					insert.setBigDecimal(12, ctatodoc);
					insert.setString(13, gerencia);
					insert.setString(14, formula);
					insert.setBigDecimal(15, cuotas);
					insert.setBigDecimal(16, presentacion);
					insert.setBigDecimal(17, ctacaudoc);
					insert.setDouble(18, porcentaje.doubleValue());
					insert.setBigDecimal(19, ctadtotar);
					insert.setBigDecimal(20, ctatarjeta);
					insert.setBigDecimal(21, comhyper);
					insert.setBigDecimal(22, contador);
					insert.setString(23, afecomicob);
					insert.setString(24, impri_id);
					insert.setString(25, subdiventa);
					insert.setString(26, idcencosto);
					insert.setString(27, idcencosto1);
					insert.setString(28, modicent);
					insert.setBigDecimal(29, prox_cheq);
					insert.setBigDecimal(30, prox_reserv);
					insert.setBigDecimal(31, ulti_cheq);
					insert.setString(32, modsubcent);
					insert.setBigDecimal(33, res_nro);
					insert.setBigDecimal(34, idempresa);
					insert.setString(35, usuarioact);
					insert.setTimestamp(36, fechaact);
					insert.setBigDecimal(37, ididentificador);
				} else {
					String ins = "INSERT INTO CAJAIDENTIFICADORES(idtipoidentificador, identificador, descripcion, cuenta, idmoneda, modcta, factura, saldo_id, saldo_disp, renglones, ctacaucion, ctatodoc, gerencia, formula, cuotas, presentacion, ctacaudoc, porcentaje, ctadtotar, ctatarjeta, comhyper, contador, afecomicob, impri_id, subdiventa, idcencosto, idcencosto1, modicent, prox_cheq, prox_reserv, ulti_cheq, modsubcent, res_nro, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setBigDecimal(1, idtipoidentificador);
					insert.setString(2, identificador);
					insert.setString(3, descripcion);
					insert.setBigDecimal(4, cuenta);
					insert.setBigDecimal(5, idmoneda);
					insert.setString(6, modcta);
					insert.setString(7, factura);
					insert.setDouble(8, saldo_id.doubleValue());
					insert.setDouble(9, saldo_disp.doubleValue());
					insert.setBigDecimal(10, renglones);
					insert.setBigDecimal(11, ctacaucion);
					insert.setBigDecimal(12, ctatodoc);
					insert.setString(13, gerencia);
					insert.setString(14, formula);
					insert.setBigDecimal(15, cuotas);
					insert.setBigDecimal(16, presentacion);
					insert.setBigDecimal(17, ctacaudoc);
					insert.setDouble(18, porcentaje.doubleValue());
					insert.setBigDecimal(19, ctadtotar);
					insert.setBigDecimal(20, ctatarjeta);
					insert.setBigDecimal(21, comhyper);
					insert.setBigDecimal(22, contador);
					insert.setString(23, afecomicob);
					insert.setString(24, impri_id);
					insert.setString(25, subdiventa);
					insert.setString(26, idcencosto);
					insert.setString(27, idcencosto1);
					insert.setString(28, modicent);
					insert.setBigDecimal(29, prox_cheq);
					insert.setBigDecimal(30, prox_reserv);
					insert.setBigDecimal(31, ulti_cheq);
					insert.setString(32, modsubcent);
					insert.setBigDecimal(33, res_nro);
					insert.setBigDecimal(34, idempresa);
					insert.setString(35, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String cajaIdentificadoresCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String cajaIdentificadoresCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String cajaIdentificadoresUpdate(BigDecimal ididentificador,
			BigDecimal idtipoidentificador, String identificador,
			String descripcion, BigDecimal cuenta, BigDecimal idmoneda,
			String modcta, String factura, Double saldo_id, Double saldo_disp,
			BigDecimal renglones, BigDecimal ctacaucion, BigDecimal ctatodoc,
			String gerencia, String formula, BigDecimal cuotas,
			BigDecimal presentacion, BigDecimal ctacaudoc, Double porcentaje,
			BigDecimal ctadtotar, BigDecimal ctatarjeta, BigDecimal comhyper,
			BigDecimal contador, String afecomicob, String impri_id,
			String subdiventa, String idcencosto, String idcencosto1,
			String modicent, BigDecimal prox_cheq, BigDecimal prox_reserv,
			BigDecimal ulti_cheq, String modsubcent, BigDecimal res_nro,
			Hashtable htCuotas, Hashtable htPresentacion, BigDecimal idempresa,
			String usuarioact) throws EJBException, SQLException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (ididentificador == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: ididentificador ";
		if (idtipoidentificador == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipoidentificador ";
		if (identificador == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: identificador ";
		if (descripcion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: descripcion ";
		if (idmoneda == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idmoneda ";
		if (modcta == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: modcta ";
		if (factura == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: factura ";
		if (renglones == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: renglones ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (identificador.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: identificador ";
		if (descripcion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: descripcion ";
		if (modcta.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: modcta ";
		if (factura.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: factura ";
		// fin validaciones

		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM cajaIdentificadores WHERE ididentificador = "
					+ ididentificador.toString()
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
					sql = ""
							+ "UPDATE CAJAIDENTIFICADORES "
							+ "   SET idtipoidentificador=?, identificador=?, descripcion=?, cuenta=?, "
							+ "       idmoneda=?, modcta=?, factura=?, saldo_id=?, saldo_disp=?, renglones=?, "
							+ "       ctacaucion=?, ctatodoc=?, gerencia=?, formula=?, cuotas=?, presentacion=?, "
							+ "       ctacaudoc=?, porcentaje=?, ctadtotar=?, ctatarjeta=?, comhyper=?, contador=?, "
							+ "       afecomicob=?, impri_id=?, subdiventa=?, idcencosto=?, idcencosto1=?, modicent=?, "
							+ "       prox_cheq=?, prox_reserv=?, ulti_cheq=?, modsubcent=?, res_nro=?, idempresa=?, "
							+ "       usuarioact=?, fechaact=? "
							+ " WHERE ididentificador=? AND idempresa=?;";

					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, idtipoidentificador);
					insert.setString(2, identificador);
					insert.setString(3, descripcion);
					insert.setBigDecimal(4, cuenta);
					insert.setBigDecimal(5, idmoneda);
					insert.setString(6, modcta);
					insert.setString(7, factura);
					insert.setDouble(8, saldo_id.doubleValue());
					insert.setDouble(9, saldo_disp.doubleValue());
					insert.setBigDecimal(10, renglones);
					insert.setBigDecimal(11, ctacaucion);
					insert.setBigDecimal(12, ctatodoc);
					insert.setString(13, gerencia);
					insert.setString(14, formula);
					insert.setBigDecimal(15, cuotas);
					insert.setBigDecimal(16, presentacion);
					insert.setBigDecimal(17, ctacaudoc);
					insert.setDouble(18, porcentaje.doubleValue());
					insert.setBigDecimal(19, ctadtotar);
					insert.setBigDecimal(20, ctatarjeta);
					insert.setBigDecimal(21, comhyper);
					insert.setBigDecimal(22, contador);
					insert.setString(23, afecomicob);
					insert.setString(24, impri_id);
					insert.setString(25, subdiventa);
					insert.setString(26, idcencosto);
					insert.setString(27, idcencosto1);
					insert.setString(28, modicent);
					insert.setBigDecimal(29, prox_cheq);
					insert.setBigDecimal(30, prox_reserv);
					insert.setBigDecimal(31, ulti_cheq);
					insert.setString(32, modsubcent);
					insert.setBigDecimal(33, res_nro);
					insert.setBigDecimal(34, idempresa);
					insert.setString(35, usuarioact);
					insert.setTimestamp(36, fechaact);
					insert.setBigDecimal(37, ididentificador);
					insert.setBigDecimal(38, idempresa);
				}

				int n = insert.executeUpdate();
				if (n == 1) {

					if (idtipoidentificador.longValue() == 7) {

						cajaValorTarDelete(identificador, "C", idempresa);

						if (htCuotas != null && !htCuotas.isEmpty()) {

							String[] dias = (String[]) htCuotas.get("dias");
							String[] numero = (String[]) htCuotas.get("numero");
							cajaValorTarDelete(identificador, "C", idempresa);

							for (int f = 0; f < dias.length
									&& salida.equalsIgnoreCase("OK"); f++) {
								salida = cajaValorTarCreate(identificador, "C",
										new BigDecimal(numero[f]),
										new BigDecimal(dias[f]), idempresa,
										usuarioact);

							}

							if (htPresentacion != null
									&& !htPresentacion.isEmpty()) {

								dias = (String[]) htPresentacion.get("dias");
								numero = (String[]) htPresentacion
										.get("numero");
								cajaValorTarDelete(identificador, "P",
										idempresa);

								for (int f = 0; f < dias.length
										&& salida.equalsIgnoreCase("OK"); f++) {
									salida = cajaValorTarCreate(identificador,
											"P", new BigDecimal(numero[f]),
											new BigDecimal(dias[f]), idempresa,
											usuarioact);

								}

							}

							// TODO: presentacion de tarjetas
							// cajatarjpre !!!!!

						} else
							salida = "Detalle de cuotas no definido.";

					}

				} else
					salida = "Imposible actualizar identificador.";

			}

		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error SQL public String cajaIdentificadoresUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String cajaIdentificadoresUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public List getCajaLovIdentifTipoAll(long limit, long offset, String tipo,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT i.ididentificador,ti.tipoidentificador,i.identificador,i.descripcion,"
				+ "       i.cuenta,i.idmoneda,i.modcta,i.factura,i.saldo_id::numeric(18, 2),i.saldo_disp::numeric(18, 2),i.renglones,"
				+ "       i.ctacaucion,i.ctatodoc,i.gerencia,i.formula,i.cuotas,i.presentacion,"
				+ "       i.ctacaudoc,i.porcentaje::numeric(18, 2),i.ctadtotar,i.ctatarjeta,i.comhyper,i.contador,i.afecomicob,"
				+ "       i.impri_id,i.subdiventa,i.idcencosto,i.idcencosto1,i.modicent,i.prox_cheq,"
				+ "       i.prox_reserv,i.ulti_cheq,i.modsubcent,i.res_nro,ti.propio,ti.tipomov,"
				+ "       i.idempresa,i.usuarioalt,i.usuarioact,i.fechaalt,i.fechaact "
				+ "  FROM cajaidentificadores i "
				+ "       INNER JOIN cajatipoidentificadores ti  ON i.idtipoidentificador = ti.idtipoidentificador AND i.idempresa = ti.idempresa "
				+ " WHERE ti.tipomov = '" + tipo + "' AND i.idempresa = "
				+ idempresa.toString() + " ORDER BY 2, 3  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getCajaLovIdentifTipoOcu(long limit, long offset, String tipo,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT i.ididentificador,ti.tipoidentificador,i.identificador,i.descripcion,"
				+ "       i.cuenta,i.idmoneda,i.modcta,i.factura,i.saldo_id::numeric(18, 2),i.saldo_disp::numeric(18, 2),i.renglones,"
				+ "       i.ctacaucion,i.ctatodoc,i.gerencia,i.formula,i.cuotas,i.presentacion,"
				+ "       i.ctacaudoc,i.porcentaje::numeric(18, 2),i.ctadtotar,i.ctatarjeta,i.comhyper,i.contador,i.afecomicob,"
				+ "       i.impri_id,i.subdiventa,i.idcencosto,i.idcencosto1,i.modicent,i.prox_cheq,"
				+ "       i.prox_reserv,i.ulti_cheq,i.modsubcent,i.res_nro,ti.propio,ti.tipomov,"
				+ "       i.idempresa,i.usuarioalt,i.usuarioact,i.fechaalt,i.fechaact "
				+ "  FROM cajaidentificadores i "
				+ "       INNER JOIN cajatipoidentificadores ti  ON i.idtipoidentificador = ti.idtipoidentificador AND i.idempresa = ti.idempresa "
				+ " WHERE (UPPER(i.identificador) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(i.descripcion) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%') AND ti.tipomov = '"
				+ tipo + "'  AND i.idempresa = " + idempresa.toString()
				+ " ORDER BY 2, 3  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: cajaValorTar Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Tue Oct 21 10:01:01 GMT-03:00 2008
	 */

	// por primary key (primer campo por defecto)
	public List getCajaValorTarPK(String valor_id, String tipo,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT valor_id,tipo,numero,dias,idempresa,usuarioalt,usuarioact,fechaalt,fechaact"
				+ " FROM CAJAVALORTAR WHERE valor_id='"
				+ valor_id.toUpperCase() + "' AND tipo = '"
				+ tipo.toUpperCase() + "' AND idempresa = "
				+ idempresa.toString() + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String cajaValorTarDelete(String valor_id, String tipo,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM CAJAVALORTAR WHERE valor_id='"
				+ valor_id.toUpperCase() + "' AND tipo='" + tipo.toUpperCase()
				+ "' AND idempresa=" + idempresa.toString();
		String salida = "OK";
		try {

			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM CAJAVALORTAR WHERE valor_id='"
						+ valor_id.toUpperCase() + "' AND tipo='"
						+ tipo.toUpperCase() + "'  AND idempresa="
						+ idempresa.toString();
				int r = statement.executeUpdate(cQuery);
				if (r == 0)
					salida = "Error: Registro inexistente";

			} else {
				salida = "Error: Registro inexistente";
			}

		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : cajaValorTarDelete( String valor_id, BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: cajaValorTarDelete( String valor_id, BigDecimal idempresa )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String cajaValorTarCreate(String valor_id, String tipo,
			BigDecimal numero, BigDecimal dias, BigDecimal idempresa,
			String usuarioalt) throws EJBException {
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		try {
			if (salida.equalsIgnoreCase("OK")) {
				String ins = ""
						+ "INSERT INTO CAJAVALORTAR(valor_id, tipo, numero, dias, idempresa, usuarioalt )"
						+ "     VALUES (?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, valor_id);
				insert.setString(2, tipo);
				insert.setBigDecimal(3, numero);
				insert.setBigDecimal(4, dias);
				insert.setBigDecimal(5, idempresa);
				insert.setString(6, usuarioalt);
				int n = insert.executeUpdate();
				if (n != 1)
					salida = "Error al generar datos de cuotas / presentacion.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String cajaValorTarCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error excepcion public String cajaValorTarCreate(.....)"
					+ ex);
		}
		return salida;
	}

	/**
	 * Metodos para la entidad: vcajaMovTesoCobranzas Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Wed May 13 10:18:02 GYT 2009
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getCajaMovTesoCobranzasAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		// --mt.movsal_mt, mt.sucsal_mt, mt.nrosal_mt, mt.usado_mt,
		String cQuery = ""
				+ "SELECT LPAD(sucursa_mt::VARCHAR, 4, '0' ) AS sucursal, LPAD(comprob_mt::VARCHAR, 8, '0') AS comprobante, importe_mt::NUMERIC(18, 2) AS importe, "
				+ "       COALESCE(nrointerno, 0) AS nrointerno, idcliente, razon,  fechamo_mt, observaciones, tipomov_mt,fecha_mt, "
				+ "       tipo_mt, anulada_mt, idcencosto,idcencosto1, idempresa  "
				+ "  FROM vcajamovtesocobranzas " + " WHERE idempresa = "
				+ idempresa.toString()
				+ " ORDER BY sucursa_mt, comprob_mt LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getCajaMovTesoCobranzasOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT LPAD(sucursa_mt::VARCHAR, 4, '0' ) AS sucursal, LPAD(comprob_mt::VARCHAR, 8, '0') AS comprobante, (importe_mt::NUMERIC(18, 2))AS importe, "
				+ "       COALESCE(nrointerno, 0) AS nrointerno, idcliente,  razon,  fechamo_mt, observaciones, tipomov_mt,fecha_mt, "
				+ "       tipo_mt, anulada_mt, idcencosto,idcencosto1, idempresa  "
				+ "  FROM vcajamovtesocobranzas "
				+ " WHERE (cobranza::VARCHAR LIKE '%" + ocurrencia
				+ "%' OR UPPER(razon) LIKE '%" + ocurrencia.toUpperCase()
				+ "%')  AND idempresa = " + idempresa.toString()
				+ " ORDER BY sucursa_mt, comprob_mt LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getCajaMovTesoCobranzasFiltro(long limit, long offset,
			String filtro, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT LPAD(sucursa_mt::VARCHAR, 4, '0' ) AS sucursal, LPAD(comprob_mt::VARCHAR, 8, '0') AS comprobante, (importe_mt::NUMERIC(18, 2))AS importe, "
				+ "             COALESCE(nrointerno, 0) AS nrointerno, idcliente,  razon,  fechamo_mt, observaciones, tipomov_mt,fecha_mt, "
				+ "             tipo_mt, anulada_mt, idcencosto,idcencosto1, idempresa  "
				+ "  FROM vcajamovtesocobranzas " + " WHERE " + filtro
				+ "     AND idempresa = " + idempresa.toString()
				+ " ORDER BY sucursa_mt, comprob_mt LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: cajaAnuladas Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Thu May 14 12:56:07 GYT 2009
	 */

	public String cajaAnuladasCreate(String tipomov_an, BigDecimal comprob_an,
			Timestamp fechaan_an, BigDecimal importe_an, BigDecimal sucursa_an,
			BigDecimal idclipro_an, String cuit_an, String tipo_an,
			BigDecimal idempresa, String usuarioalt) throws EJBException {
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones

		try {
			if (salida.equalsIgnoreCase("OK")) {
				String ins = ""
						+ "INSERT INTO cajaanuladas "
						+ "            (tipomov_an, comprob_an, fechaan_an, importe_an, sucursa_an, "
						+ "             idclipro_an, cuit_an, tipo_an, idempresa, usuarioalt ) "
						+ "     VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, tipomov_an);
				insert.setBigDecimal(2, comprob_an);
				insert.setTimestamp(3, fechaan_an);
				insert.setBigDecimal(4, importe_an);
				insert.setBigDecimal(5, sucursa_an);
				insert.setBigDecimal(6, idclipro_an);
				insert.setString(7, cuit_an);
				insert.setString(8, tipo_an);
				insert.setBigDecimal(9, idempresa);
				insert.setString(10, usuarioalt);
				int n = insert.executeUpdate();
				if (n != 1)
					salida = "Imposible generar registro de comprobante anulado.";
			}
		} catch (SQLException sqlException) {
			salida = "(SQLE)Imposible generar registro de comprobante anulado.";
			log.error("Error SQL public String cajaAnuladasCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "(EX)Imposible generar registro de comprobante anulado.";
			log.error("Error excepcion public String cajaAnuladasCreate(.....)"
					+ ex);
		}
		return salida;
	}

	/**
	 * Metodos para la entidad: vcajaAnuladas Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Wed Jun 10 10:04:09 GYT 2009
	 * 
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getVcajaAnuladasAll(long limit, long offset, String tipomov_an,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT tipomov_an,comprobante,comprob_an,fechaan_an,importe_an,sucursa_an,idclipro_an,razon,cuit_an,tipo_an,"
				+ "       idempresa,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM vcajaanuladas "
				+ " WHERE idempresa = "
				+ idempresa.toString()
				+ (tipomov_an.equals("") ? "" : " AND tipomov_an = '"
						+ tipomov_an + "'") + "  ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getVcajaAnuladasOcu(long limit, long offset, String tipomov_an,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT tipomov_an,comprobante,comprob_an,fechaan_an,importe_an,sucursa_an,idclipro_an,razon,cuit_an,tipo_an,"
				+ "       idempresa,usuarioalt,usuarioact,fechaalt,fechaact"
				+ "  FROM vcajaanuladas "
				+ " WHERE (UPPER(comprobante) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(razon) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR TO_CHAR(fechaan_an, 'DD/MM/YYYY') LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%')  AND idempresa = "
				+ idempresa.toString()
				+ (tipomov_an.equals("") ? "" : " AND tipomov_an = '"
						+ tipomov_an + "'") + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: vcajaMovTesoPagos Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Mon May 18 13:11:14 GYT 2009
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getCajaMovTesoPagosAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT pago,sucursa_mt,comprob_mt,importe_mt::NUMERIC(18,2) AS importe_mt, "
				+ "       saldo::NUMERIC(18,2) AS saldo ,nrointerno,COALESCE(idpreoveedor, 0) AS idpreoveedor,"
				+ "       razon_social,fechamo_mt,observaciones,tipomov_mt,fecha_mt,"
				+ "       tipo_mt,anulada_mt,idcencosto,idcencosto1,idempresa "
				+ " FROM vcajamovtesopagos WHERE idempresa = "
				+ idempresa.toString() + "  ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getCajaMovTesoPagosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT pago,sucursa_mt,comprob_mt,importe_mt::NUMERIC(18,2) AS importe_mt, "
				+ "       saldo::NUMERIC(18,2) AS saldo,nrointerno,COALESCE(idpreoveedor, 0) AS idpreoveedor,"
				+ "       razon_social,fechamo_mt,observaciones,tipomov_mt,fecha_mt,"
				+ "       tipo_mt,anulada_mt,idcencosto,idcencosto1,idempresa "
				+ "  FROM vcajamovtesopagos WHERE ( (pago) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(razon_social) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%')  AND idempresa = "
				+ idempresa.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public long getTotalDocsAntesFCierre(BigDecimal sucursal,
			BigDecimal comprobante, java.sql.Timestamp fcierreprov,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT SUM(total)AS total "
				+ "  FROM( "
				+ "	SELECT COUNT(1) AS total "
				+ "	  FROM cajamovteso mt "
				+ "	       INNER JOIN cajaidentificadores id ON mt.cartera_mt = id.identificador"
				+ "               AND mt.idempresa = id.idempresa AND id.factura = 'S'  AND mt.tipo_mt = 1  "
				+ "	       INNER JOIN cajaaplicaci ap ON mt.nroint_mt = ap.nrointe_ap "
				+ "               AND mt.tipomov_mt = ap.tipomov_ap AND ap.anexo_ap = 'S' AND mt.idempresa = ap.idempresa "
				+ "	       INNER JOIN proveedoanexop ax ON ap.nrointe_ap = ax.nroint_ant AND ap.idempresa = ax.idempresa "
				+ "	 WHERE mt.sucursa_mt = "
				+ sucursal.toString()
				+ " AND mt.comprob_mt = "
				+ comprobante.toString()
				+ " AND mt.tipomov_mt = 'PAG' AND ax.fecha_ant::date <=  '"
				+ fcierreprov
				+ "'::date AND mt.idempresa = "
				+ idempresa.toString()
				+ "	UNION ALL "
				+ "	SELECT COUNT(1) AS total "
				+ "	  FROM cajamovteso mt "
				+ "	       INNER JOIN cajaidentificadores id ON mt.cartera_mt = id.identificador "
				+ "               AND mt.idempresa = id.idempresa AND id.factura = 'S'  AND mt.tipo_mt = 1 "
				+ "	       INNER JOIN cajaaplicaci ap ON mt.nroint_mt = ap.nrointe_ap "
				+ "               AND mt.tipomov_mt = ap.tipomov_ap AND ap.anexo_ap = 'N' AND mt.idempresa = ap.idempresa "
				+ "	       INNER JOIN proveedocancprov ca ON mt.nroint_mt = ca.nrointerno_q_can AND mt.idempresa = ca.idempresa "
				+ "	       INNER JOIN proveedomovprov mp ON ca.nrointerno_canc = mp.nrointerno AND ca.idempresa = mp.idempresa "
				+ "	 WHERE mt.sucursa_mt = "
				+ sucursal.toString()
				+ " AND mt.comprob_mt = "
				+ comprobante.toString()
				+ " AND mt.tipomov_mt = 'PAG' AND mp.fecha_subd::date <=  '"
				+ fcierreprov
				+ "'::date AND mt.idempresa = "
				+ idempresa.toString() + " ) documentos	 ";
		long total = -1l;

		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);

			if (rsSalida != null && rsSalida.next()) {
				total = rsSalida.getLong(1);
			}

		} catch (SQLException sqlException) {
			log.error("Error SQL en el metodo : getTotalDocsAntesFCierre(...) "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getTotalDocsAntesFCierre(...)  "
							+ ex);
		}
		return total;
	}

	// libro diario de caja
	public List getCosnultaLibroDiariodeCaja(String fechadesde,
			String fechahasta, BigDecimal idempresa) throws EJBException {
		String cQuery = "select * from vlibrocaja" + " where fecha between '"
				+ fechadesde.toString() + "' and '" + fechahasta.toString()
				+ "' and idempresa =" + idempresa.toString();
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: cajaSucursales Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Thu Jun 16 11:12:47 ART 2011
	 * 
	 * public List getCajaSucursalesAll(long limit, long offset, BigDecimal
	 * idempresa) throws RemoteException;
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getCajaSucursalesAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT nrosucursal,observaciones,idsucutipo,habilitada,idempresa,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "   FROM cajasucursales WHERE idempresa = "
				+ idempresa.toString() + "  ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = new ArrayList();
		try {
			vecSalida = getLista(cQuery);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCajaSucursalesAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// 20110622 - EJV - Factuaracion FE-CF-MA -->
	public String getClienteLetraIva(BigDecimal idcliente, BigDecimal idempresa)
			throws EJBException {

		String cQuery = ""
				+ "SELECT ti.letra "
				+ "  FROM clientestablaiva ti "
				+ "       INNER JOIN clientesclientes cl ON ti.idtipoiva = cl.idtipoiva AND ti.idempresa = cl.idempresa AND cl.idcliente = "
				+ idcliente.toString() + "and cl.idempresa="
				+ idempresa.toString();

		String letra = "";
		Iterator it;
		try {

			it = getLista(cQuery).iterator();
			if (it.hasNext()) {
				letra = ((String[]) it.next())[0];
			}

		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getClienteLetraIva( BigDecimal idtipoiva )  "
							+ ex);
		}
		return letra;
	}

	public String getPorPerIIBB(String cuit) throws EJBException {

		String cQuery = "SELECT por_per FROM padroniibb WHERE cuit = '" + cuit
				+ "'";

		String por_per = "0";
		Iterator it;
		try {

			it = getLista(cQuery).iterator();
			if (it.hasNext()) {
				por_per = ((String[]) it.next())[0];
			}

		} catch (Exception ex) {
			por_per = "-1";
			log
					.error("Salida por exception: en el metodo: getPorPerIIBB( BigDecimal idtipoiva )  "
							+ ex);
		}
		return por_per;
	}

	// <--

	/**
	 * Metodos para la entidad: vCajaSucursalesContadores Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Tue Mar 06 10:48:55 ART 2012
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getVCajaSucursalesContadoresAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT sucutipo,nrosucursal,observaciones,idtipomov,tipomov,condicionletra,proximo,idempresa,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "   FROM vcajasucursalescontadores WHERE idempresa = "
				+ idempresa.toString() + "  ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = new ArrayList();
		try {
			vecSalida = getLista(cQuery);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getVCajaSucursalesContadoresAll()  "
							+ ex);
		}
		return vecSalida;
	}

	public List getLovClientesAplicacionMovimientosAll(BigDecimal cliente,
			BigDecimal idempresa) throws EJBException {
		// para comprobantes a aplicar

		// Select cliente,fechamov,sucursal,comprob,comprob_has,tipomov,
		// tipomovs,saldo,importe,cambio,moneda,unamode,tipocomp from
		// clientesmovcli where saldo >0.0000 and (tipomov = 1 or tipomov = 2)
		// order by tipomov
		// Son facturas y notas de debito.
		String cQuery = "Select cliente,fechamov,sucursal,comprob,comprob_has,tipomov,"
				+ "       tipomovs,saldo,importe,cambio,moneda,unamode,tipocomp "
				+ "from clientesmovcli  where cliente = "
				+ cliente.toString()
				+ "  and saldo  >0.0000 and (tipomov  = 1 or tipomov = 2) order by  tipomov";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getLovClientesAplicacionMovimientosOne(BigDecimal cliente,
			BigDecimal sucursal, BigDecimal comprob, String tipomovs,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT nrointerno, saldo, importe , fechamov, anulada, remito, saldo as saldocontrol, tipomov, cliente "
				+ "  FROM CLIENTESMOVCLI " + " WHERE cliente="
				+ cliente.toString() + " AND sucursal=" + sucursal
				+ " AND comprob=" + comprob + " AND tipomovs='" + tipomovs
				+ "' AND idempresa=" + idempresa.toString();
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getVCajaSucursalesContadoresOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT  sucutipo,nrosucursal,observaciones,idtipomov,tipomov,condicionletra,proximo,idempresa,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM vcajasucursalescontadores WHERE (UPPER(nrosucursal::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%')  AND idempresa = "
				+ idempresa.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = new ArrayList();
		try {
			vecSalida = getLista(cQuery);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getVCajaSucursalesContadoresOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// 20120921 - EJV - REINTEGROS -->

	public String[] cajaClientesCobranzasReintegro(BigDecimal idcliente,
			BigDecimal nrointerno_cob, java.sql.Date fechamov,
			Hashtable htIdentificaSalidaPagos, Hashtable htMovimientosCancelar,
			String usuarioact, BigDecimal idempresa) throws EJBException,
			SQLException {
		String salida = "OK";
		String[] resultado = new String[] { "", "", "" };

		Connection conn = null;
		String cQuery = "";
		String entidad = "clientescancclie";
		String filtro = "";
		long totalAplicacionesCancelaciones = 0;

		BigDecimal nrointernomovcliente = null;
		BigDecimal nroreintegro = null;
		BigDecimal nroint_mt = null;
		String condicionletra = "";

		conn = GeneralBean.getTransaccionConn(this.clase, this.url,
				this.usuario, this.clave);
		// <--

		if (conn == null)
			return new String[] {
					"E1000-No fue posible generar vinculo a base de datos, intente nuevamente o avise a sistemas.",
					"" };

		try {

			conn.setAutoCommit(false);

			filtro = " WHERE comp_canc = " + nrointerno_cob;
			totalAplicacionesCancelaciones = getTotalEntidadFiltro(entidad,
					filtro, idempresa);

			if (totalAplicacionesCancelaciones == 0) {

				filtro = " WHERE comp_q_can = " + nrointerno_cob;
				totalAplicacionesCancelaciones = getTotalEntidadFiltro(entidad,
						filtro, idempresa);

				if (totalAplicacionesCancelaciones == 0) {
					// --
					condicionletra = getClienteLetraIva(idcliente, idempresa);
					// --
					// 20121127 - EJV -->
					// nrocobranza =
					// GeneralBean.getContador("nrointernocobranza",
					// idempresa, conn);
					nroreintegro = GeneralBean.getContador(
							"nrointernoreintegro", idempresa, conn);
					// <--
					// --
					nrointernomovcliente = GeneralBean.getContador(
							"nrointernomovcliente", idempresa, conn);
					// --

					// 1-Generar Movcli

					BigDecimal totalCobranza = new BigDecimal(0);
					BigDecimal com_cobra = null;
					BigDecimal idcobrador = null;
					String observaciones = "REINTEGRO ";

					cQuery = "SELECT importe, tipomovs || '-' ||LPAD(sucursal::varchar, 4,'0') || '-' || LPAD(comprob::varchar, 8,'0')AS comprobante FROM clientesmovcli WHERE nrointerno = "
							+ nrointerno_cob + " AND idempresa = " + idempresa;

					Iterator iterDatosCob = getLista(cQuery, conn).iterator();

					if (iterDatosCob != null && iterDatosCob.hasNext()) {

						String[] totalCob = (String[]) iterDatosCob.next();

						totalCobranza = new BigDecimal(totalCob[0]);
						observaciones += " X COMPROBANTE: " + totalCob[1];

						salida = clientesMovCliCreate(
								idcliente,
								new Timestamp(fechamov.getTime()),
								new BigDecimal(0),
								nroreintegro,
								null,
								// 20121127 - EJV -->
								// new BigDecimal(4),
								new BigDecimal(6),
								// "COB",
								"REI",
								// <--
								new BigDecimal(0), totalCobranza,
								new BigDecimal(1), new BigDecimal(1), null,
								null, null, nrointernomovcliente, null,
								com_cobra, idcobrador, null, null, null, null,
								null, null, observaciones, condicionletra,
								usuarioact, idempresa, conn);

						if (salida.equalsIgnoreCase("OK")) {
							// 2-Actualizar saldo cobranza

							salida = clientesMovCliUpdateSaldo(nrointerno_cob,
									totalCobranza.negate(), usuarioact,
									idempresa, conn);

							// 3-Generar MovTeso

							if (salida.equalsIgnoreCase("OK")) {

								cQuery = ""

										+ "SELECT mt.cartera_mt, mt.comprob_mt, mt.fechamo_mt, mt.tipomov_mt, mt.tipcart_mt, mt.nroint_mt, mt.detalle_mt, mt.nrodoc_mt, mt.fecha_mt, mt.idclearing, "
										+ "             mt.fclear_mt, mt.impori_mt, mt.importe_mt, mt.deposi_mt, mt.idmoneda, mt.unamone_mt, mt.cambio_mt, mt.movsal_mt, mt.sucsal_mt, mt.nrosal_mt,  "
										+ "             cuenta_mt, idcliente, tipo_mt, usado_mt, anulada_mt, idcencosto, idcencosto1, sucursa_mt,  "
										+ "             mt.idempresa, mt.usuarioalt, mt.usuarioact, mt.fechaalt, mt.fechaact "
										+ "   FROM cajamovteso mt "
										+ "             INNER JOIN clientesmovcli mc ON mt.comprob_mt = mc.comprob AND mt.sucursa_mt =  mc.sucursal "
										+ "                        AND mt.idcliente = mc.cliente AND mt.idempresa = mc.idempresa "
										+ " WHERE mc.nrointerno =   "
										+ nrointerno_cob
										+ "      AND mc.idempresa = "
										+ idempresa
										+ " AND mt.cartera_mt = 'CLI' ORDER BY tipo_mt DESC ";

								Iterator iterMovTeso = getLista(cQuery, conn)
										.iterator();

								if (iterMovTeso != null) {

									if (iterMovTeso.hasNext()) {

										// while (iterMovTeso.hasNext()) {

										String[] datosMovTeso = (String[]) iterMovTeso
												.next();

										BigDecimal tipo_mt = tipo_mt = new BigDecimal(
												1);
										String cartera_mt = datosMovTeso[0];
										String tipcart_mt = datosMovTeso[4];
										BigDecimal impori_mt = new BigDecimal(
												datosMovTeso[11]);
										BigDecimal importe_mt = new BigDecimal(
												datosMovTeso[12]);
										BigDecimal cuenta_mt = new BigDecimal(
												datosMovTeso[20]);
										BigDecimal idmoneda = new BigDecimal(
												datosMovTeso[14]);
										BigDecimal cambio_mt = new BigDecimal(
												datosMovTeso[16]);

										if (salida.equalsIgnoreCase("OK")) {

											salida = cajaMovTesoCreate(
													cartera_mt,
													nroreintegro,
													new Timestamp(fechamov
															.getTime()),
													// 20121127 - EJV -->
													// "COB",
													"REI",
													// <--
													tipcart_mt, new BigDecimal(
															0), null, null,
													new Timestamp(fechamov
															.getTime()), null,
													new Timestamp(fechamov
															.getTime()),
													impori_mt, importe_mt,
													null, idmoneda, null,
													cambio_mt, null, null,
													null, cuenta_mt, idcliente,
													tipo_mt, "S", null, null,
													null, usuarioact,
													idempresa, conn);

											if (salida.equalsIgnoreCase("OK")) {

												/*
												 * (cartera_mt, comprob_mt,
												 * fechamo_mt, tipomov_mt,
												 * tipcart_mt, nroint_mt,
												 * detalle_mt, nrodoc_mt,
												 * fecha_mt, idclearing,
												 * fclear_mt, impori_mt,
												 * importe_mt, deposi_mt,
												 * idmoneda, unamone_mt,
												 * cambio_mt, movsal_mt,
												 * sucsal_mt, nrosal_mt,
												 * cuenta_mt, idcliente,
												 * tipo_mt, usado_mt,
												 * anulada_mt, idcencosto,
												 * idcencosto1, usuarioalt,
												 * idempresa, dbconn)
												 */

												// ///////////////////////////////////////////////
												// 20120927
												String key = "";
												String usado_mt = "";
												if (salida
														.equalsIgnoreCase("OK")) {// D

													Enumeration en = htIdentificaSalidaPagos
															.keys();
													while (en.hasMoreElements()) {

														key = (String) en
																.nextElement();
														String[] datosIngresos = (String[]) htIdentificaSalidaPagos
																.get(key);

														// TODO: calcular fecha
														// clearing
														nroint_mt = new BigDecimal(
																0);
														BigDecimal nrodoc_mt = (datosIngresos[30] == null
																|| datosIngresos[30]
																		.trim()
																		.equals(
																				"") ? null
																: new BigDecimal(
																		datosIngresos[30]));

														switch (datosIngresos[6]
																.trim().charAt(
																		0)) {
														case 'E':
														case 'G':
															usado_mt = "S";
															tipcart_mt = datosIngresos[6];
															salida = cajaSaldoBcoCreateOrUpdate(
																	datosIngresos[2],
																	fechamov,
																	(new BigDecimal(
																			datosIngresos[28])
																			.negate()),
																	new BigDecimal(
																			datosIngresos[28])
																			.negate(),
																	usuarioact,
																	idempresa,
																	conn);

															break;
														case 'C':

															// TODO: Revisar
															// logica
															// aplicada segun
															// variable
															// global
															// 'MARCA_CHEQUES_A_MANO'
															usado_mt = "D";
															tipcart_mt = "P";
															salida = cajaSaldoBcoCreateOrUpdate(
																	datosIngresos[2],
																	fechamov,
																	(new BigDecimal(
																			datosIngresos[28])
																			.negate()),
																	(new BigDecimal(
																			0)),
																	usuarioact,
																	idempresa,
																	conn);

															break;
														case 'D':
															usado_mt = null;
															tipcart_mt = "M";

															// EJV - 20090512
															// nroint_mt =
															// GeneralBean.getContador(
															// new
															// BigDecimal(11),
															// idempresa,
															// dbconn);

															nroint_mt = GeneralBean
																	.getContador(
																			"nrointernodocumentos",
																			idempresa,
																			conn);

															salida = cajaSaldoBcoCreateOrUpdate(
																	datosIngresos[2],
																	fechamov,
																	(new BigDecimal(
																			datosIngresos[28])
																			.negate()),
																	(new BigDecimal(
																			0)),
																	usuarioact,
																	idempresa,
																	conn);

															break;
														case 'T':
															usado_mt = null;
															tipcart_mt = "J";
															break;

														default:
															usado_mt = "S";
															tipcart_mt = datosIngresos[6];
															break;
														}

														if (!salida
																.equalsIgnoreCase("OK"))
															break;

														// 20100428 - EJV -->
														java.sql.Date fecha_mt = new java.sql.Date(
																fechamov
																		.getTime());
														java.sql.Date fclear_mt = fecha_mt;
														// <--
														if (datosIngresos[31] != null
																&& !datosIngresos[31]
																		.trim()
																		.equals(
																				"")) {

															try {
																SimpleDateFormat sdf = new SimpleDateFormat(
																		"dd/MM/yyyy");
																fecha_mt = new java.sql.Date(
																		sdf
																				.parse(
																						datosIngresos[31]
																								.replaceAll(
																										"-",
																										"/"))
																				.getTime());

																// 20100428 -
																// EJV
																// -->
																fclear_mt = fecha_mt;
																// <--

															} catch (Exception e) {
																salida = "Error al asignar fecha de cheque.";
															}

														}
														// 20100428 - EJV -->
														Hashtable htFeriados = new Hashtable();
														htFeriados = getCajaFeriadosFecha(idempresa);
														fclear_mt = GeneralBean
																.setFechaClearing(
																		fecha_mt,
																		0,
																		htFeriados);
														// <--

														if (!salida
																.equalsIgnoreCase("OK"))
															break;

														salida = cajaMovTesoCreate(
																datosIngresos[2],
																// nropago,
																nroreintegro,
																new Timestamp(
																		fechamov
																				.getTime()),
																// "PAG",
																// 20121127 -
																// EJV -->
																// "COB",
																"REI",
																// <--
																tipcart_mt,
																nroint_mt,
																datosIngresos[29],
																nrodoc_mt,
																new Timestamp(
																		fecha_mt
																				.getTime()),
																new BigDecimal(
																		datosIngresos[32]),
																new Timestamp(
																		fclear_mt
																				.getTime()),
																new BigDecimal(
																		datosIngresos[28]),
																new BigDecimal(
																		datosIngresos[28]),
																null,
																new BigDecimal(
																		datosIngresos[5]),
																null,
																new BigDecimal(
																		1),
																null,
																null,
																null,
																new BigDecimal(
																		datosIngresos[4]),
																// idproveedor,
																idcliente,
																new BigDecimal(
																		2),
																usado_mt,
																null,
																new BigDecimal(
																		datosIngresos[20]),
																new BigDecimal(
																		datosIngresos[21]),
																usuarioact,
																idempresa, conn);

														if (!salida
																.equalsIgnoreCase("OK"))
															break;

													}

													if (salida
															.equalsIgnoreCase("OK")) {// E

														en = htMovimientosCancelar
																.keys();
														while (en
																.hasMoreElements()) {
															key = en
																	.nextElement()
																	.toString();
															String[] datosMovimientosCancelar = (String[]) htMovimientosCancelar
																	.get(key);
															nroint_mt = new BigDecimal(
																	0);

															//

															for (int f = 0; f < datosMovimientosCancelar.length; f++)
																log
																		.info("ELEM "
																				+ f
																				+ ": "
																				+ datosMovimientosCancelar[f]);

															//

															BigDecimal nrodoc_mt = (datosMovimientosCancelar[30] == null
																	|| datosMovimientosCancelar[30]
																			.trim()
																			.equals(
																					"") ? null
																	: new BigDecimal(
																			datosMovimientosCancelar[30]));

															// 20090803 EJV -
															// Agregado:
															// usado_mt_saldo
															// / antes usaba
															// usado_mt. Estaba
															// marcando
															// mal los
															// documentos que se
															// saldan.
															// Hay que revisar
															// la actualizacion
															// de
															// estos
															// campos.

															String usado_mt_saldo = "";
															switch (datosMovimientosCancelar[6]
																	.trim()
																	.charAt(0)) {
															case 'C':
															case 'S':
																usado_mt = usado_mt_saldo = "S";
																tipcart_mt = datosMovimientosCancelar[6];
																break;
															case 'D':

																usado_mt_saldo = "S";
																usado_mt = "O";
																tipcart_mt = "D";// 20070201
																nroint_mt = new BigDecimal(
																		datosMovimientosCancelar[30]);
																nrodoc_mt = new BigDecimal(
																		datosMovimientosCancelar[37]);
																break;

															default:
																usado_mt = usado_mt_saldo = "S";
																tipcart_mt = datosMovimientosCancelar[6];
																break;
															}

															salida = cajaSaldaMovimientosTeso(
																	new BigDecimal(
																			key),
																	// "PAG",
																	// 20121127
																	// - EJV -->
																	// "COB",
																	"REI",
																	// <--
																	new BigDecimal(
																			0),
																	// nropago,
																	nroreintegro,
																	usado_mt_saldo,
																	null,
																	usuarioact,
																	idempresa,
																	conn);

															if (!salida
																	.equalsIgnoreCase("OK"))
																break;

															salida = cajaMovTesoCreate(
																	datosMovimientosCancelar[2],
																	// nropago,
																	nroreintegro,
																	new Timestamp(
																			fechamov
																					.getTime()),
																	// "PAG",
																	// 20121127
																	// - EJV -->
																	// "COB",
																	"REI",
																	// <--
																	tipcart_mt,
																	nroint_mt,
																	datosMovimientosCancelar[29],
																	nrodoc_mt,
																	new Timestamp(
																			fechamov
																					.getTime()),
																	new BigDecimal(
																			datosMovimientosCancelar[32]),
																	new Timestamp(
																			fechamov
																					.getTime()),
																	new BigDecimal(
																			datosMovimientosCancelar[28]),
																	new BigDecimal(
																			datosMovimientosCancelar[28]),
																	null,
																	new BigDecimal(
																			datosMovimientosCancelar[5]),
																	null,
																	new BigDecimal(
																			1),
																	null,
																	null,
																	null,
																	new BigDecimal(
																			datosMovimientosCancelar[4]),
																	// idproveedor,
																	idcliente,
																	new BigDecimal(
																			2),
																	usado_mt,
																	null,
																	new BigDecimal(
																			datosMovimientosCancelar[20]),
																	new BigDecimal(
																			datosMovimientosCancelar[21]),
																	usuarioact,
																	idempresa);

															if (!salida
																	.equalsIgnoreCase("OK"))
																break;

														}
													}// E
												}// D

												// ///////////////////////////////////////////////

												// }

												// 4-Generar Cancli

												salida = clientesCancClieCreate(
														nrointerno_cob,
														nrointernomovcliente,
														totalCobranza,
														new Timestamp(fechamov
																.getTime()),
														usuarioact, idempresa,
														conn);

												// 6-Generar relacion aplicaci

												if (salida
														.equalsIgnoreCase("OK")) {

													salida = cajaAplicaciCreate(
															// 20121127 - EJV
															// -->
															// "COB",
															"REI",
															// <--
															nroreintegro,
															nrointernomovcliente,
															"", new BigDecimal(
																	0),
															usuarioact,
															idempresa, conn);

												}

											}

										}

									}

								}

							}

						}

					} else
						salida = "No fue posible recuperar total de comprobante.";

				} else
					salida = "El comprobante seleccionado  aplica a otros, verifique y deaplique en forma manual.";

			} else
				salida = "El comprobante seleccionado se encuentra aplicado por otros, verifique y deaplique en forma manual.";

		} catch (SQLException e) {
			salida = "(SQLE) No fue posible generar reintegro.";
			log.error("cajaClientesCobranzasReintegro():" + e);
		} catch (Exception e) {
			salida = "(EX) No fue posible generar reintegro.";
			log.error("cajaClientesCobranzasReintegro():" + e);
		}

		if (salida.equalsIgnoreCase("OK")) {
			conn.commit();
			resultado[1] = nrointernomovcliente.toString();
			resultado[2] = nroreintegro.toString();
		} else {
			conn.rollback();
		}

		resultado[0] = salida;

		conn.close();

		return resultado;

	}

	// <--

	// 20120926 - EJV -->
	public List getLovClientesMovCliCobReintegroAll(long limit, long offset,
			BigDecimal idcliente, BigDecimal idempresa) throws EJBException {

		String cQuery = ""
				+ "SELECT mc.cliente,mc.fechamov,mc.sucursal,mc.comprob,mc.comprob_has,mc.tipomov,"
				+ "       mc.tipomovs,mc.saldo,mc.importe,mc.cambio,mc.moneda,mc.unamode,mc.tipocomp,"
				+ "       mc.condicion,mc.nrointerno,mc.com_venta,mc.com_cobra,mc.com_vende,mc.anulada,"
				+ "       mc.retoque,mc.expreso,mc.sucucli,mc.remito,mc.credito,"
				+ "       mc.usuarioalt,mc.usuarioact,mc.fechaalt,mc.fechaact "
				+ "  FROM clientesmovcli mc "
				// +
				// "       INNER JOIN  clientestipocomp tc ON mc.tipomov = tc.tipomov_tc AND mc.idempresa = tc.idempresa"
				// +
				// "                  AND mc.tipocomp::numeric = tc.idtipocomp "
				+ " WHERE mc.cliente =  " + idcliente
				+ "     AND mc.tipomovs = 'COB' " + "     AND mc.idempresa="
				+ idempresa.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getLovClientesMovCliCobReintegroOne(BigDecimal cliente,
			BigDecimal sucursal, BigDecimal comprob, String tipomovs,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT nrointerno, saldo, importe , fechamov, anulada, remito, saldo as saldocontrol, tipomov, cliente, sucursal, comprob, tipomovs "
				+ "   FROM clientesmovcli " + " WHERE cliente="
				+ cliente.toString() + " AND sucursal=" + sucursal
				+ " AND comprob=" + comprob + " AND tipomovs='" + tipomovs
				+ "' AND idempresa=" + idempresa.toString();
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// <--

}
