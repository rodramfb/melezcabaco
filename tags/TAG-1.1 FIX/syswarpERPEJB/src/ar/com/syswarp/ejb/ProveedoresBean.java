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
import java.util.List;
import java.math.*;

@Stateless
public class ProveedoresBean implements Proveedores {
	/** The session context */
	private SessionContext context;

	/* conexion a la base de datos */
	private Connection dbconn;

	static Logger log = Logger.getLogger(ProveedoresBean.class);

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

	public ProveedoresBean() {
		super();
		try {
			props = new Properties();
			props.load(ProveedoresBean.class
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

	public ProveedoresBean(Connection dbconn) {
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

	/*
	 * de muestra public List getVariables() throws EJBException { ResultSet
	 * rsSalida = null; String cQuery = "Select * from setupvariables"; List
	 * vecSalida = new ArrayList(); try { Statement statement =
	 * dbconn.createStatement(); rsSalida = statement.executeQuery(cQuery);
	 * ResultSetMetaData md = rsSalida.getMetaData(); while (rsSalida.next()) {
	 * int totCampos = md.getColumnCount() - 1; String[] sSalida = new
	 * String[totCampos + 1]; int i = 0; while (i <= totCampos) { sSalida[i] =
	 * rsSalida.getString(++i); } vecSalida.add(sSalida); } } catch
	 * (SQLException sqlException) { log.error("Error SQL: " + sqlException); }
	 * catch (Exception ex) { log.error("Salida por exception: " + ex); } return
	 * vecSalida; }
	 */

	// ---------------------------------------------------------------------------------------------------------------
	// PROVEEDORES CONDICIONES DE PAGO
	// todos las Condiciones de Pago
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

	public List getCondicionPago(BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ " SELECT * FROM proveedocondicio WHERE idempresa = "
				+ idempresa.toString() + " ORDER BY idcondicionpago";
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
			log.error("Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception: " + ex);
		}
		return vecSalida;
	}

	// todos las Condiciones de Pago por alguna ocurrencia
	public List getCondicionPago(String ocurrencia, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM proveedocondicio WHERE (UPPER(condicionpago) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%') AND idempresa =  "
				+ idempresa.toString() + " ORDER BY idcondicionpago";
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
			log.error("Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception: " + ex);
		}
		return vecSalida;
	}

	// Condiciones de Pago por PK
	public List getCondicionPagoPK(Integer pk, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM proveedocondicio WHERE idcondicionpago = "
				+ pk.toString() + " AND idempresa = " + idempresa.toString();
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
			log.error("Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception: " + ex);
		}
		return vecSalida;
	}

	// Eliminar Condiciones de Pago por PK
	public String delCondicionPago(Integer pk, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM proveedocondicio WHERE idcondicionpago = "
				+ pk.toString() + " AND idempresa = " + idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE from proveedocondicio where idcondicionpago = "
						+ pk.toString()
						+ " AND idempresa = "
						+ idempresa.toString();
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

	// insertar una Condicion de Pago
	public String CondicionPagoSave(String condicionpago, Integer cantidaddias,
			String usuarioalt, BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		if (condicionpago.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacia la Condicion de Pago";
		if (cantidaddias.compareTo(new Integer(0)) == 0)
			salida = "Error: No se puede dejar vacio Cantidad de Dias";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar el usuario de alta vacio";
		// fin validaciones

		try {
			PreparedStatement insert = dbconn
					.prepareStatement("INSERT INTO proveedocondicio(condicionpago, cantidaddias, usuarioalt, idempresa) VALUES(?,?,?,?)");
			insert.setString(1, condicionpago);
			insert.setInt(2, cantidaddias.intValue());
			insert.setString(3, usuarioalt);
			insert.setBigDecimal(4, idempresa);
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

	// update de una Condicion de Pago
	public String CondicionPagoOrUpdate(String condicionpago,
			Integer cantidaddias, String usuarioalt, Integer idcondicionpago,
			BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fecha = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		if (condicionpago.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacia la Condicion de Pago";
		if (cantidaddias.compareTo(new Integer(0)) == 0)
			salida = "Error: No se puede dejar vacio Cantidad de Dias";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar el usuario de alta vacio";
		// fin validaciones
		try {
			ResultSet rsSalida = null;
			String cQuery = "Select count(*) from proveedocondicio where idcondicionpago = "
					+ idcondicionpago.toString()
					+ " AND idempresa = "
					+ idempresa.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			if (total > 0) { // Existe asi que updeteo
				insert = dbconn
						.prepareStatement("UPDATE proveedocondicio  SET condicionpago=?, cantidaddias=?, usuarioact=?, fechaact=? where idcondicionpago=? AND idempresa =?");
				insert.setString(1, condicionpago);
				insert.setInt(2, cantidaddias.intValue());
				insert.setString(3, usuarioalt);
				insert.setTimestamp(4, fecha);
				insert.setInt(5, idcondicionpago.intValue());
				insert.setBigDecimal(6, idempresa);
			} else {
				insert = dbconn
						.prepareStatement("INSERT INTO proveedocondicio(condicionpago, cantidaddias, usuarioalt, idempresa) VALUES(?,?,?,?)");
				insert.setString(1, condicionpago);
				insert.setInt(2, cantidaddias.intValue());
				insert.setString(3, usuarioalt);
				insert.setBigDecimal(4, idempresa);
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

	/*
	 * Metodos para la entidad: proveedoRetenciones Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Tue Jul 04 16:49:59 GMT-03:00 2006
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
				+ " WHERE idempresa = " + idempresa.toString();
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
		 * Entidad: Usuarios
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer usuario por ocurrencia.
		 */
		long total = 0l;
		ResultSet rsSalida = null;
		String cQuery = "SELECT count(1)AS total FROM " + entidad + " WHERE ";
		String like = "";
		int len = campos.length;

		try {
			for (int i = 0; i < len; i++) {
				like += "UPPER(" + campos[i] + "::VARCHAR) LIKE '%"
						+ ocurrencia.trim().toUpperCase() + "%' ";
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
		String cQuery = "SELECT count(1)AS total FROM " + entidad + " WHERE  ";
		String filtro = "";
		int len = campos.length;

		try {
			for (int i = 0; i < len; i++) {
				filtro += campos[i] + " = " + ocurrencia[i] + " ";
				if (i + 1 < len)
					filtro += " AND ";
			}
			cQuery += filtro + " AND idempresa = " + idempresa.toString();
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

	// PROVEEDORES RETENCIONES
	public List getProveedoRetencionesAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT idretencion,retencion,impor1_ret,impor2_ret,impor3_ret,impor4_ret,"
				+ "       impor5_ret,impor6_ret,impor7_ret,impor8_ret,impor9_ret,"
				+ "       usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM PROVEEDORETENCIONES WHERE idempresa = "
				+ idempresa.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)
	public List getProveedoRetencionesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT idretencion,retencion,impor1_ret,impor2_ret,impor3_ret,impor4_ret,"
				+ "       impor5_ret,impor6_ret,impor7_ret,impor8_ret,impor9_ret,"
				+ "       usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM PROVEEDORETENCIONES " + " where idempresa= "
				+ idempresa.toString() + " and (idretencion::VARCHAR LIKE '%"
				+ ocurrencia + "%' OR " + " UPPER(retencion) LIKE '%"
				+ ocurrencia.toUpperCase() + "%') " + " ORDER BY 2  LIMIT "
				+ limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// **************************************************************
	// ESTO SE HIZO PARA TRAER EL LOV DE LAS RETENCIONES
	public List getProveedoRetencionesOcuLOV(String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		String cQuery = "SELECT * FROM PROVEEDORETENCIONES WHERE (UPPER(retencion) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%') AND idempresa = "
				+ idempresa.toString() + " ORDER BY idretencion";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// **************************************************************
	// ESTO SE HIZO PARA TRAER EL LOV DE estados
	public List getProveedoEstadosOcuLOV(String ocurrencia, BigDecimal idempresa)
			throws EJBException {
		String cQuery = "SELECT * FROM proveedo_oc_estados WHERE (UPPER(estadooc) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%') AND idempresa = "
				+ idempresa.toString() + " ORDER BY 1";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ESTO SE HIZO PARA TRAER EL LOV DE LAS PROVINCIAS
	public List getProveedoProvinciasOcuLOV(String ocurrencia)
			throws EJBException {
		String cQuery = "SELECT * FROM globalprovincias WHERE UPPER(provincia) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' "
				+ " ORDER BY idprovincia";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ESTO SE HIZO PARA TRAER EL LOV DE LAS Condiciones de Pago
	public List getProveedoConddePagoOcuLOV(String ocurrencia,
			BigDecimal idempresa) throws EJBException {

		String cQuery = "SELECT * FROM proveedocondicio WHERE (UPPER(condicionpago) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%') AND idempresa =  "
				+ idempresa.toString() + " ORDER BY idcondicionpago";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)
	public List getProveedoRetencionesPK(BigDecimal idretencion,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idretencion,retencion,impor1_ret,impor2_ret,impor3_ret,impor4_ret,"
				+ "       impor5_ret,impor6_ret,impor7_ret,impor8_ret,impor9_ret,"
				+ "       usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM PROVEEDORETENCIONES " + " WHERE idretencion="
				+ idretencion.toString() + " AND idempresa = "
				+ idempresa.toString();

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)
	public String proveedoRetencionesDelete(BigDecimal idretencion,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM PROVEEDORETENCIONES WHERE idretencion="
				+ idretencion.toString() + " AND idempresa = "
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM PROVEEDORETENCIONES WHERE idretencion="
						+ idretencion.toString() + " AND idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : proveedoRetencionesDelete( BigDecimal idretencion ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: proveedoRetencionesDelete( BigDecimal idretencion )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt
	public String proveedoRetencionesCreate(String retencion,
			Double impor1_ret, Double impor2_ret, Double impor3_ret,
			Double impor4_ret, Double impor5_ret, Double impor6_ret,
			Double impor7_ret, Double impor8_ret, Double impor9_ret,
			String usuarioalt, BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (retencion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: retencion ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (retencion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: retencion ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO PROVEEDORETENCIONES(retencion, impor1_ret, impor2_ret, impor3_ret, impor4_ret, impor5_ret, impor6_ret, impor7_ret, impor8_ret, impor9_ret, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, retencion);
				insert.setDouble(2, impor1_ret.doubleValue());
				insert.setDouble(3, impor2_ret.doubleValue());
				insert.setDouble(4, impor3_ret.doubleValue());
				insert.setDouble(5, impor4_ret.doubleValue());
				insert.setDouble(6, impor5_ret.doubleValue());
				insert.setDouble(7, impor6_ret.doubleValue());
				insert.setDouble(8, impor7_ret.doubleValue());
				insert.setDouble(9, impor8_ret.doubleValue());
				insert.setDouble(10, impor9_ret.doubleValue());
				insert.setString(11, usuarioalt);
				insert.setBigDecimal(12, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String proveedoRetencionesCreate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String proveedoRetencionesCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria
	public String proveedoRetencionesCreateOrUpdate(BigDecimal idretencion,
			String retencion, Double impor1_ret, Double impor2_ret,
			Double impor3_ret, Double impor4_ret, Double impor5_ret,
			Double impor6_ret, Double impor7_ret, Double impor8_ret,
			Double impor9_ret, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// 1. nulidad de campos
		if (idretencion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idretencion ";
		if (retencion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: retencion ";

		// 2. sin nada desde la pagina
		if (retencion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: retencion ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM proveedoRetenciones WHERE idretencion = "
					+ idretencion.toString()
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
					sql = "UPDATE PROVEEDORETENCIONES SET retencion=?, impor1_ret=?, impor2_ret=?, impor3_ret=?, impor4_ret=?, impor5_ret=?, impor6_ret=?, impor7_ret=?, impor8_ret=?, impor9_ret=?, usuarioact=?, fechaact=? WHERE idretencion=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, retencion);
					insert.setDouble(2, impor1_ret.doubleValue());
					insert.setDouble(3, impor2_ret.doubleValue());
					insert.setDouble(4, impor3_ret.doubleValue());
					insert.setDouble(5, impor4_ret.doubleValue());
					insert.setDouble(6, impor5_ret.doubleValue());
					insert.setDouble(7, impor6_ret.doubleValue());
					insert.setDouble(8, impor7_ret.doubleValue());
					insert.setDouble(9, impor8_ret.doubleValue());
					insert.setDouble(10, impor9_ret.doubleValue());
					insert.setString(11, usuarioact);
					insert.setTimestamp(12, fechaact);
					insert.setBigDecimal(13, idretencion);
					insert.setBigDecimal(14, idempresa);
				} else {
					String ins = "INSERT INTO PROVEEDORETENCIONES(retencion, impor1_ret, impor2_ret, impor3_ret, impor4_ret, impor5_ret, impor6_ret, impor7_ret, impor8_ret, impor9_ret, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, retencion);
					insert.setDouble(2, impor1_ret.doubleValue());
					insert.setDouble(3, impor2_ret.doubleValue());
					insert.setDouble(4, impor3_ret.doubleValue());
					insert.setDouble(5, impor4_ret.doubleValue());
					insert.setDouble(6, impor5_ret.doubleValue());
					insert.setDouble(7, impor6_ret.doubleValue());
					insert.setDouble(8, impor7_ret.doubleValue());
					insert.setDouble(9, impor8_ret.doubleValue());
					insert.setDouble(10, impor9_ret.doubleValue());
					insert.setString(11, usuarioalt);
					insert.setBigDecimal(12, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String proveedoRetencionesCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String proveedoRetencionesCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// update de un registro
	public String proveedoRetencionesUpdate(BigDecimal idretencion,
			String retencion, Double impor1_ret, Double impor2_ret,
			Double impor3_ret, Double impor4_ret, Double impor5_ret,
			Double impor6_ret, Double impor7_ret, Double impor8_ret,
			Double impor9_ret, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idretencion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idretencion ";
		if (retencion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: retencion ";

		// 2. sin nada desde la pagina
		if (retencion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: retencion ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM proveedoRetenciones WHERE idretencion = "
					+ idretencion.toString()
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
					sql = "UPDATE PROVEEDORETENCIONES SET retencion=?, impor1_ret=?, impor2_ret=?, impor3_ret=?, impor4_ret=?, impor5_ret=?, impor6_ret=?, impor7_ret=?, impor8_ret=?, impor9_ret=?, usuarioact=?, fechaact=? WHERE idretencion=? AND idempresa =?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, retencion);
					insert.setDouble(2, impor1_ret.doubleValue());
					insert.setDouble(3, impor2_ret.doubleValue());
					insert.setDouble(4, impor3_ret.doubleValue());
					insert.setDouble(5, impor4_ret.doubleValue());
					insert.setDouble(6, impor5_ret.doubleValue());
					insert.setDouble(7, impor6_ret.doubleValue());
					insert.setDouble(8, impor7_ret.doubleValue());
					insert.setDouble(9, impor8_ret.doubleValue());
					insert.setDouble(10, impor9_ret.doubleValue());
					insert.setString(11, usuarioact);
					insert.setTimestamp(12, fechaact);
					insert.setBigDecimal(13, idretencion);
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
					.error("Error SQL public String proveedoRetencionesUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String proveedoRetencionesUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// PROVEEDORES Condiciones de Pago
	// para todo (ordena por el segundo campo por defecto)
	public List getProveedoCondicioAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT idcondicionpago,condicionpago,cantidaddias,"
				+ "       usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM PROVEEDOCONDICIO WHERE idempresa = "
				+ idempresa.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)
	public List getProveedoCondicioOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {

		String cQuery = ""
				+ "SELECT idcondicionpago,condicionpago,cantidaddias,"
				+ "       usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM PROVEEDOCONDICIO " + " where idempresa= "
				+ idempresa.toString()
				+ " and (idcondicionpago::VARCHAR LIKE '%" + ocurrencia
				+ "%' OR " + " UPPER(condicionpago) LIKE '%"
				+ ocurrencia.toUpperCase() + "%') " + " ORDER BY 2  LIMIT "
				+ limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)
	public List getProveedoCondicioPK(BigDecimal idcondicionpago,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT idcondicionpago,condicionpago,cantidaddias,"
				+ "       usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM PROVEEDOCONDICIO WHERE idcondicionpago="
				+ idcondicionpago.toString() + " AND idempresa = "
				+ idempresa.toString();
		log.info(cQuery);
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)
	public String proveedoCondicioDelete(BigDecimal idcondicionpago,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM PROVEEDOCONDICIO WHERE idcondicionpago="
				+ idcondicionpago.toString() + " AND idempresa = "
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM PROVEEDOCONDICIO WHERE idcondicionpago="
						+ idcondicionpago.toString() + " AND idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : proveedoCondicioDelete( BigDecimal idcondicionpago ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: proveedoCondicioDelete( BigDecimal idcondicionpago )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt
	public String proveedoCondicioCreate(String condicionpago,
			BigDecimal cantidaddias, String usuarioalt, BigDecimal idempresa)
			throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (condicionpago == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: condicionpago ";
		if (cantidaddias == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cantidaddias ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (condicionpago.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: condicionpago ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = ""
						+ "INSERT INTO PROVEEDOCONDICIO(condicionpago, cantidaddias, usuarioalt, idempresa ) "
						+ "     VALUES (?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, condicionpago);
				insert.setBigDecimal(2, cantidaddias);
				insert.setString(3, usuarioalt);
				insert.setBigDecimal(4, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String proveedoCondicioCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String proveedoCondicioCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria
	public String proveedoCondicioCreateOrUpdate(BigDecimal idcondicionpago,
			String condicionpago, BigDecimal cantidaddias, String usuarioact,
			BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idcondicionpago == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idcondicionpago ";
		if (condicionpago == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: condicionpago ";
		if (cantidaddias == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cantidaddias ";

		// 2. sin nada desde la pagina
		if (condicionpago.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: condicionpago ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM proveedoCondicio WHERE idcondicionpago = "
					+ idcondicionpago.toString()
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
					sql = "UPDATE PROVEEDOCONDICIO SET condicionpago=?, cantidaddias=?, usuarioact=?, fechaact=? WHERE idcondicionpago=? AND idempresa = ?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, condicionpago);
					insert.setBigDecimal(2, cantidaddias);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, idcondicionpago);
					insert.setBigDecimal(6, idempresa);
				} else {
					String ins = "INSERT INTO PROVEEDOCONDICIO(condicionpago, cantidaddias, usuarioalt, idempresa ) VALUES (?, ?, ?, ? )";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, condicionpago);
					insert.setBigDecimal(2, cantidaddias);
					insert.setString(3, usuarioalt);
					insert.setBigDecimal(4, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String proveedoCondicioCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String proveedoCondicioCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String proveedoCondicioUpdate(BigDecimal idcondicionpago,
			String condicionpago, BigDecimal cantidaddias, String usuarioact,
			BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idcondicionpago == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idcondicionpago ";
		if (condicionpago == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: condicionpago ";
		if (cantidaddias == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cantidaddias ";

		// 2. sin nada desde la pagina
		if (condicionpago.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: condicionpago ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM proveedoCondicio WHERE idcondicionpago = "
					+ idcondicionpago.toString()
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
					sql = "UPDATE PROVEEDOCONDICIO SET condicionpago=?, cantidaddias=?, usuarioact=?, fechaact=? WHERE idcondicionpago=?  AND idempresa =?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, condicionpago);
					insert.setBigDecimal(2, cantidaddias);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, idcondicionpago);
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
			log.error("Error SQL public String proveedoCondicioUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String proveedoCondicioUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// PROVEEDORES PROVEEDORES
	// PROVEEDORES PROVEEDORES (levanto todos)
	public List getProveedoProveedAll(long limit, long offset, String orden,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT pv.idproveedor, pv.razon_social, pv.domicilio,gl.localidad ,gp.provincia, pv.postal, "
				+ "       pv.contacto, pv.telefono, pv.cuit, pv.brutos, pv.ctapasivo, pv.ctaactivo1, pv.ctaactivo2, "
				+ "       ctaactivo3, ctaactivo4, ctaiva, ctaretiva, letra_iva, ctadocumen, ret_gan, "
				+ "       r1.retencion, r2.retencion, r3.retencion, r4.retencion, r5.retencion, "
				+ "       pv.ctades, pv.stock_fact, cp.condicionpago, cent1, cent2, cent3,"
				+ "       pv.cent4, pv.cents1, pv.cents2, pv.cents3, pv.cents4, pv.email, "
				+ "       pv.usuarioalt, pv.usuarioact, pv.fechaalt, pv.fechaact "
				+ "  FROM PROVEEDOPROVEED pv"
				+ "       LEFT JOIN proveedoretenciones r1 ON (pv.idretencion1  = r1.idretencion AND pv.idempresa = r1.idempresa )"
				+ "       LEFT JOIN proveedoretenciones r2 ON (pv.idretencion2  = r2.idretencion AND pv.idempresa = r2.idempresa )"
				+ "       LEFT JOIN proveedoretenciones r3 ON (pv.idretencion3  = r3.idretencion AND pv.idempresa = r3.idempresa )"
				+ "       LEFT JOIN proveedoretenciones r4 ON (pv.idretencion4  = r4.idretencion AND pv.idempresa = r4.idempresa )"
				+ "       LEFT JOIN proveedoretenciones r5 ON (pv.idretencion5 = r5.idretencion AND pv.idempresa = r5.idempresa )"
				+ "       LEFT JOIN globallocalidades gl ON (pv.idlocalidad = gl.idlocalidad)"
				+ "       LEFT JOIN globalprovincias gp ON (pv.idprovincia = gp.idprovincia)"
				+ "       LEFT JOIN proveedocondicio cp ON (pv.idcondicionpago = cp.idcondicionpago  AND pv.idempresa = cp.idempresa ) "
				+ " WHERE pv.idempresa = " + idempresa.toString()
				+ " ORDER BY " + orden + "  LIMIT " + limit + " OFFSET  "
				+ offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// PROVEEDORES PROVEEDORES (levento por ocurrencia)
	public List getProveedoProveedOcu(long limit, long offset,
			String ocurrencia, String orden, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT pv.idproveedor, pv.razon_social, pv.domicilio,gl.localidad ,gp.provincia, pv.postal, "
				+ "       pv.contacto, pv.telefono, pv.cuit, pv.brutos, pv.ctapasivo, pv.ctaactivo1, pv.ctaactivo2, "
				+ "       ctaactivo3, ctaactivo4, ctaiva, ctaretiva, letra_iva, ctadocumen, ret_gan, "
				+ "       r1.retencion, r2.retencion, r3.retencion, r4.retencion, r5.retencion, "
				+ "       pv.ctades, pv.stock_fact, cp.condicionpago, cent1, cent2, cent3,"
				+ "       pv.cent4, pv.cents1, pv.cents2, pv.cents3, pv.cents4, pv.email, "
				+ "       pv.usuarioalt, pv.usuarioact, pv.fechaalt, pv.fechaact "
				+ "  FROM PROVEEDOPROVEED pv"
				+ "       LEFT JOIN proveedoretenciones r1 ON (pv.idretencion1  = r1.idretencion AND pv.idempresa = r1.idempresa )"
				+ "       LEFT JOIN proveedoretenciones r2 ON (pv.idretencion2  = r2.idretencion AND pv.idempresa = r2.idempresa )"
				+ "       LEFT JOIN proveedoretenciones r3 ON (pv.idretencion3  = r3.idretencion AND pv.idempresa = r3.idempresa )"
				+ "       LEFT JOIN proveedoretenciones r4 ON (pv.idretencion4  = r4.idretencion AND pv.idempresa = r4.idempresa )"
				+ "       LEFT JOIN proveedoretenciones r5 ON (pv.idretencion5 = r5.idretencion AND pv.idempresa = r5.idempresa )"
				+ "       LEFT JOIN globallocalidades gl ON (pv.idlocalidad = gl.idlocalidad)"
				+ "       LEFT JOIN globalprovincias gp ON (pv.idprovincia = gp.idprovincia)"
				+ "       LEFT JOIN proveedocondicio cp ON (pv.idcondicionpago = cp.idcondicionpago  AND pv.idempresa = cp.idempresa ) "
				+ " WHERE (UPPER(pv.RAZON_SOCIAL) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%' "
				+ "OR (pv.idproveedor::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%' "
				+ "OR UPPER(pv.cuit) LIKE '%" + ocurrencia.toUpperCase().trim()
				+ "%' " + "OR UPPER(pv.telefono) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%') AND pv.idempresa = "
				+ idempresa.toString() + " ORDER BY " + orden + "  LIMIT "
				+ limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// PROVEEDORES PROVEEDORES (levento por PK)
	public List getProveedoProveedPK(BigDecimal idproveedor,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT pr.idproveedor, pr.razon_social, pr.domicilio, pr.idlocalidad,"
				+ "       gl.localidad,pr.idprovincia, gp.provincia, pr.postal,"
				+ "       pr.contacto, pr.telefono, pr.cuit, pr.brutos,pr.ctapasivo, pr.ctaactivo1,"
				+ "       pr.ctaactivo2, pr.ctaactivo3, pr.ctaactivo4, pr.ctaiva, "
				+ "       pr.ctaretiva,pr.letra_iva, pr.ctadocumen, pr.ret_gan, "
				+ "       pr.idretencion1,r1.descripcion AS ret1, pr.idretencion2, r2.descripcion AS ret2,"
				+ "       pr.idretencion3, r3.descripcion AS ret3, pr.idretencion4, r4.descripcion AS ret4, "
				+ "       pr.idretencion5, r5.descripcion AS ret5, pr.ctades, pr.stock_fact, pr.idcondicionpago, "
				+ "       pc.condicionpago,pr.cent1,pr.cent2, "
				+ "       pr.cent3, pr.cent4, pr.cents1,pr.cents2, pr.cents3, pr.cents4, email,"
				+ "       pr.usuarioalt,pr.usuarioact, pr.fechaalt, pr.fechaact "
				+ "  FROM PROVEEDOPROVEED pr  "
				+ "       LEFT JOIN cajaidentificadores r1  ON (pr.idretencion1 = r1.ididentificador AND pr.idempresa  = r1.idempresa )"
				+ "       LEFT JOIN cajaidentificadores r2  ON (pr.idretencion2 = r2.ididentificador AND pr.idempresa  = r2.idempresa )"
				+ "       LEFT JOIN cajaidentificadores r3  ON (pr.idretencion3 = r3.ididentificador AND pr.idempresa  = r3.idempresa )"
				+ "       LEFT JOIN cajaidentificadores r4  ON (pr.idretencion4 = r4.ididentificador AND pr.idempresa  = r4.idempresa )"
				+ "       LEFT JOIN cajaidentificadores r5  ON (pr.idretencion5 = r5.ididentificador AND pr.idempresa  = r5.idempresa )"
				+ "       LEFT JOIN globallocalidades gl ON (pr.idlocalidad = gl.idlocalidad)"
				+ "       LEFT JOIN globalprovincias gp ON (pr.idprovincia = gp.idprovincia)"
				+ "       LEFT JOIN proveedocondicio pc ON (pr.idcondicionpago = pc.idcondicionpago  AND pr.idempresa  = pc.idempresa )"
				+ " WHERE pr.idproveedor=" + idproveedor.toString()
				+ "   AND pr.idempresa = " + idempresa.toString();
		log.info(cQuery);

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// PROVEEDORES PROVEEDORES (elimino por PK)
	public String proveedoProveedDelete(BigDecimal idproveedor,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM PROVEEDOPROVEED WHERE idproveedor="
				+ idproveedor.toString() + " AND idempresa = "
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM PROVEEDOPROVEED WHERE idproveedor="
						+ idproveedor.toString() + " AND idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : proveedoProveedDelete( BigDecimal idproveedor ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: proveedoProveedDelete( BigDecimal idproveedor )  "
							+ ex);
		}
		return salida;
	}

	// PROVEEDORES PROVEEDORES (grabacion)
	public String proveedoProveedCreate(BigDecimal idproveedor,
			String razon_social, String domicilio, BigDecimal idlocalidad,
			BigDecimal idprovincia, String postal, String contacto,
			String telefono, String cuit, String brutos, BigDecimal ctapasivo,
			BigDecimal ctaactivo1, BigDecimal ctaactivo2,
			BigDecimal ctaactivo3, BigDecimal ctaactivo4, BigDecimal ctaiva,
			BigDecimal ctaretiva, String letra_iva, BigDecimal ctadocumen,
			String ret_gan, BigDecimal idretencion1, BigDecimal idretencion2,
			BigDecimal idretencion3, BigDecimal idretencion4,
			BigDecimal idretencion5, BigDecimal ctades, String stock_fact,
			BigDecimal idcondicionpago, BigDecimal cent1, BigDecimal cent2,
			BigDecimal cent3, BigDecimal cent4, BigDecimal cents1,
			BigDecimal cents2, BigDecimal cents3, BigDecimal cents4,
			String email, String usuarioalt, BigDecimal idempresa)
			throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (razon_social == null)
			salida = "Error: No se puede dejar vacio el campo: Razon Social!";
		if (razon_social.equalsIgnoreCase(""))
			salida = salida = "Error: No se puede dejar vacio el campo: Razon Social!";
		if (domicilio == null)
			salida = "Error: No se puede dejar vacio el campo: Domicilio!";
		if (domicilio.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Domicilio!";
		if (idlocalidad == null)
			salida = "Error: No se puede dejar vacio el campo: Localidad!";
		// if (idlocalidad.compareTo("0") < 1)
		// salida = "Error: No se puede dejar sin datos (nulo) el campo:
		// Localidad!";
		if (idprovincia == null)
			salida = "Error: Por favor seleccione una Localidad!";
		if (idprovincia.compareTo(new BigDecimal(0)) == 0)
			salida = "Error: Por favor seleccione una Localidad!";
		if (ctapasivo == null)
			salida = "Error: No se puede dejar vacio el campo: Cta pasivo!";
		if (ctapasivo.compareTo(new BigDecimal(0)) == 0)
			salida = "Error: No se puede dejar vacio el campo: Cta pasivo!";
		if (idcondicionpago.compareTo(new BigDecimal(0)) == 0)
			salida = "Error: No se puede dejar vacio el campo: Condicion de Pago!";

		if (idproveedor == null) {
			salida = "Error: No se puede dejar vacio el campo: Codigo!";
		}
		if (idproveedor.compareTo(new BigDecimal(0)) == 0) {
			salida = "Error: No se puede dejar vacio el campo: Codigo!";
		}

		if (usuarioalt == null)
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = ""
						+ "INSERT INTO PROVEEDOPROVEED"
						+ "   (idproveedor, razon_social, domicilio, idlocalidad, idprovincia, postal, "
						+ "    contacto, telefono, cuit, brutos, ctapasivo, ctaactivo1, ctaactivo2,"
						+ "     ctaactivo3, ctaactivo4, ctaiva, ctaretiva, letra_iva, ctadocumen, ret_gan, "
						+ "    idretencion1, idretencion2, idretencion3, idretencion4, idretencion5, ctades, "
						+ "    stock_fact, idcondicionpago, cent1, cent2, cent3, cent4, cents1, cents2, cents3, cents4, email, "
						+ "    usuarioalt, idempresa )"
						+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, idproveedor);
				insert.setString(2, razon_social);
				insert.setString(3, domicilio);
				insert.setBigDecimal(4, idlocalidad);
				insert.setBigDecimal(5, idprovincia);
				insert.setString(6, postal);
				insert.setString(7, contacto);
				insert.setString(8, telefono);
				insert.setString(9, cuit);
				insert.setString(10, brutos);
				insert.setBigDecimal(11, ctapasivo);

				insert.setBigDecimal(12, ctaactivo1);
				insert.setBigDecimal(13, ctaactivo2);
				insert.setBigDecimal(14, ctaactivo3);
				insert.setBigDecimal(15, ctaactivo4);
				insert.setBigDecimal(16, ctaiva);
				insert.setBigDecimal(17, ctaretiva);

				insert.setString(18, letra_iva);
				insert.setBigDecimal(19, ctadocumen);
				insert.setString(20, ret_gan);

				insert.setBigDecimal(21, idretencion1);
				insert.setBigDecimal(22, idretencion2);
				insert.setBigDecimal(23, idretencion3);
				insert.setBigDecimal(24, idretencion4);
				insert.setBigDecimal(25, idretencion5);

				insert.setBigDecimal(26, ctades);
				if (stock_fact.equalsIgnoreCase("")) {
					insert.setString(27, "N");
				} else {
					insert.setString(27, stock_fact);
				}

				insert.setBigDecimal(28, idcondicionpago);
				insert.setBigDecimal(29, cent1);
				insert.setBigDecimal(30, cent2);
				insert.setBigDecimal(31, cent3);
				insert.setBigDecimal(32, cent4);
				insert.setBigDecimal(33, cents1);
				insert.setBigDecimal(34, cents2);
				insert.setBigDecimal(35, cents3);
				insert.setBigDecimal(36, cents4);
				insert.setString(37, email);
				insert.setString(38, usuarioalt);
				insert.setBigDecimal(39, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String proveedoProveedCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String proveedoProveedCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// PROVEEDORES PROVEEDORES (modificacion)
	public String proveedoProveedCreateOrUpdate(BigDecimal idproveedor,
			String razon_social, String domicilio, BigDecimal idlocalidad,
			BigDecimal idprovincia, String postal, String contacto,
			String telefono, String cuit, String brutos, BigDecimal ctapasivo,
			String ctaactivo1, String ctaactivo2, String ctaactivo3,
			String ctaactivo4, String ctaiva, String ctaretiva,
			String letra_iva, BigDecimal ctadocumen, String ret_gan,
			String idretencion1, String idretencion2, String idretencion3,
			String idretencion4, String idretencion5, BigDecimal ctades,
			String stock_fact, BigDecimal idcondicionpago, BigDecimal cent1,
			BigDecimal cent2, BigDecimal cent3, BigDecimal cent4,
			BigDecimal cents1, BigDecimal cents2, BigDecimal cents3,
			BigDecimal cents4, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idproveedor == null)
			salida = "Error: No se puede dejar vacio el campo: idproveedor ";
		if (razon_social == null)
			salida = "Error: No se puede dejar vacio el campo: Razon Social!";
		if (razon_social.equalsIgnoreCase(""))
			salida = salida = "Error: No se puede dejar vacio el campo: Razon Social!";
		if (domicilio == null)
			salida = "Error: No se puede dejar vacio el campo: Domicilio!";
		if (domicilio.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Domicilio!";
		if (idlocalidad == null)
			salida = "Error: No se puede dejar vacio el campo: Localidad!";
		// if (idlocalidad.compareTo("0") < 1)
		// salida = "Error: No se puede dejar sin datos (nulo) el campo:
		// localidad!";
		if (idprovincia == null)
			salida = "Error: Por favor seleccione una Localidad!";
		if (idprovincia.compareTo(new BigDecimal(0)) == 0)
			salida = "Error: Por favor seleccione una Localidad!";
		if (ctapasivo == null)
			salida = "Error: No se puede dejar vacio el campo: Cta Pasivo!";
		if (ctapasivo.compareTo(new BigDecimal(0)) == 0)
			salida = "Error: No se puede dejar vacio el campo: Cta Pasivo!";
		if (idcondicionpago.compareTo(new BigDecimal(0)) == 0)
			salida = "Error: No se puede dejar vacio el campo: Condicion de Pago!";
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM proveedoProveed WHERE idproveedor = "
					+ idproveedor.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE PROVEEDOPROVEED SET razon_social=?, domicilio=?, idlocalidad=?, idprovincia=?, postal=?, contacto=?, telefono=?, cuit=?, brutos=?, ctapasivo=?, ctaactivo1=?, ctaactivo2=?, ctaactivo3=?, ctaactivo4=?, ctaiva=?, ctaretiva=?, letra_iva=?, ctadocumen=?, ret_gan=?, idretencion1=?, idretencion2=?, idretencion3=?, idretencion4=?, idretencion5=?, ctades=?, stock_fact=?, idcondicionpago=?, cent1=?, cent2=?, cent3=?, cent4=?, cents1=?, cents2=?, cents3=?, cents4=?, usuarioact=?, fechaact=? WHERE idproveedor=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);

					insert.setString(1, razon_social);
					insert.setString(2, domicilio);
					insert.setBigDecimal(3, idlocalidad);
					insert.setBigDecimal(4, idprovincia);
					insert.setString(5, postal);
					insert.setString(6, contacto);
					insert.setString(7, telefono);
					insert.setString(8, cuit);
					insert.setString(9, brutos);
					insert.setBigDecimal(10, ctapasivo);

					if (ctaactivo1 != null
							&& !ctaactivo1.equalsIgnoreCase("null")) {
						insert.setBigDecimal(11, new BigDecimal(ctaactivo1));
					} else {
						insert.setBigDecimal(11, null);
					}
					if (ctaactivo2 != null
							&& !ctaactivo2.equalsIgnoreCase("null")) {
						insert.setBigDecimal(12, new BigDecimal(ctaactivo2));
					} else {
						insert.setBigDecimal(12, null);
					}
					if (ctaactivo3 != null
							&& !ctaactivo3.equalsIgnoreCase("null")) {
						insert.setBigDecimal(13, new BigDecimal(ctaactivo3));
					} else {
						insert.setBigDecimal(13, null);
					}
					if (ctaactivo4 != null
							&& !ctaactivo4.equalsIgnoreCase("null")) {
						insert.setBigDecimal(14, new BigDecimal(ctaactivo4));
					} else {
						insert.setBigDecimal(14, null);
					}
					if (ctaiva != null && !ctaiva.equalsIgnoreCase("null")) {
						insert.setBigDecimal(15, new BigDecimal(ctaiva));
					} else {
						insert.setBigDecimal(15, null);
					}
					if (ctaretiva != null
							&& !ctaretiva.equalsIgnoreCase("null")) {
						insert.setBigDecimal(16, new BigDecimal(ctaretiva));
					} else {
						insert.setBigDecimal(16, null);
					}
					insert.setString(17, letra_iva);
					insert.setBigDecimal(18, ctadocumen);
					insert.setString(19, ret_gan);
					if (idretencion1 != null
							&& !idretencion1.equalsIgnoreCase("null")) {
						insert.setBigDecimal(20, new BigDecimal(idretencion1));
					} else {
						insert.setBigDecimal(20, null);
					}
					if (idretencion2 != null
							&& !idretencion2.equalsIgnoreCase("null")) {
						insert.setBigDecimal(21, new BigDecimal(idretencion2));
					} else {
						insert.setBigDecimal(21, null);
					}
					if (idretencion3 != null
							&& !idretencion3.equalsIgnoreCase("null")) {
						insert.setBigDecimal(22, new BigDecimal(idretencion3));
					} else {
						insert.setBigDecimal(22, null);
					}
					if (idretencion4 != null
							&& !idretencion4.equalsIgnoreCase("null")) {
						insert.setBigDecimal(23, new BigDecimal(idretencion4));
					} else {
						insert.setBigDecimal(23, null);
					}
					if (idretencion5 != null
							&& !idretencion5.equalsIgnoreCase("null")) {
						insert.setBigDecimal(24, new BigDecimal(idretencion5));
					} else {
						insert.setBigDecimal(24, null);
					}
					insert.setBigDecimal(25, ctades);
					if (stock_fact.equalsIgnoreCase("")) {
						insert.setString(26, "N");
					} else {
						insert.setString(26, stock_fact);
					}
					insert.setBigDecimal(27, idcondicionpago);
					insert.setBigDecimal(28, cent1);
					insert.setBigDecimal(29, cent2);
					insert.setBigDecimal(30, cent3);
					insert.setBigDecimal(31, cent4);
					insert.setBigDecimal(32, cents1);
					insert.setBigDecimal(33, cents2);
					insert.setBigDecimal(34, cents3);
					insert.setBigDecimal(35, cents4);
					insert.setString(36, usuarioact);
					insert.setTimestamp(37, fechaact);
					insert.setBigDecimal(38, idproveedor);
					insert.setBigDecimal(39, idempresa);
				} else {
					String ins = "INSERT INTO PROVEEDOPROVEED(razon_social, domicilio, idlocalidad, idprovincia, postal, contacto, telefono, cuit, brutos, ctapasivo, ctaactivo1, ctaactivo2, ctaactivo3, ctaactivo4, ctaiva, ctaretiva, letra_iva, ctadocumen, ret_gan, idretencion1, idretencion2, idretencion3, idretencion4, idretencion5, ctades, stock_fact, idcondicionpago, cent1, cent2, cent3, cent4, cents1, cents2, cents3, cents4, usuarioalt, idempresa) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, razon_social);
					insert.setString(2, domicilio);
					insert.setBigDecimal(3, idlocalidad);
					insert.setBigDecimal(4, idprovincia);
					insert.setString(5, postal);
					insert.setString(6, contacto);
					insert.setString(7, telefono);
					insert.setString(8, cuit);
					insert.setString(9, brutos);
					insert.setBigDecimal(10, ctapasivo);
					if (ctaactivo1 != null
							&& !ctaactivo1.equalsIgnoreCase("null")) {
						insert.setBigDecimal(11, new BigDecimal(ctaactivo1));
					} else {
						insert.setBigDecimal(11, null);
					}
					if (ctaactivo2 != null
							&& !ctaactivo2.equalsIgnoreCase("null")) {
						insert.setBigDecimal(12, new BigDecimal(ctaactivo2));
					} else {
						insert.setBigDecimal(12, null);
					}
					if (ctaactivo3 != null
							&& !ctaactivo3.equalsIgnoreCase("null")) {
						insert.setBigDecimal(13, new BigDecimal(ctaactivo3));
					} else {
						insert.setBigDecimal(13, null);
					}
					if (ctaactivo4 != null
							&& !ctaactivo4.equalsIgnoreCase("null")) {
						insert.setBigDecimal(14, new BigDecimal(ctaactivo4));
					} else {
						insert.setBigDecimal(14, null);
					}
					if (ctaiva != "" && !ctaiva.equalsIgnoreCase("")) {
						insert.setBigDecimal(15, new BigDecimal(ctaiva));
					} else {
						insert.setBigDecimal(15, null);
					}
					if (ctaretiva != null
							&& !ctaretiva.equalsIgnoreCase("null")) {
						insert.setBigDecimal(16, new BigDecimal(ctaretiva));
					} else {
						insert.setBigDecimal(16, null);
					}
					insert.setString(17, letra_iva);
					insert.setBigDecimal(18, ctadocumen);
					insert.setString(19, ret_gan);

					if (idretencion1 != null
							&& !idretencion1.equalsIgnoreCase("null")) {
						insert.setBigDecimal(20, new BigDecimal(idretencion1));
					} else {
						insert.setBigDecimal(20, null);
					}
					if (idretencion2 != null
							&& !idretencion2.equalsIgnoreCase("null")) {
						insert.setBigDecimal(21, new BigDecimal(idretencion2));
					} else {
						insert.setBigDecimal(21, null);
					}
					if (idretencion3 != null
							&& !idretencion3.equalsIgnoreCase("null")) {
						insert.setBigDecimal(22, new BigDecimal(idretencion3));
					} else {
						insert.setBigDecimal(22, null);
					}
					if (idretencion4 != null
							&& !idretencion4.equalsIgnoreCase("null")) {
						insert.setBigDecimal(23, new BigDecimal(idretencion4));
					} else {
						insert.setBigDecimal(23, null);
					}
					if (idretencion5 != null
							&& !idretencion5.equalsIgnoreCase("null")) {
						insert.setBigDecimal(24, new BigDecimal(idretencion5));
					} else {
						insert.setBigDecimal(24, null);
					}

					insert.setBigDecimal(25, ctades);
					if (stock_fact.equalsIgnoreCase("")) {
						insert.setString(26, "N");
					} else {
						insert.setString(26, stock_fact);
					}
					insert.setBigDecimal(27, idcondicionpago);
					insert.setBigDecimal(28, cent1);
					insert.setBigDecimal(29, cent2);
					insert.setBigDecimal(30, cent3);
					insert.setBigDecimal(31, cent4);
					insert.setBigDecimal(32, cents1);
					insert.setBigDecimal(33, cents2);
					insert.setBigDecimal(34, cents3);
					insert.setBigDecimal(35, cents4);
					insert.setString(36, usuarioalt);
					insert.setBigDecimal(37, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String proveedoProveedCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String proveedoProveedCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String proveedoProveedUpdate(BigDecimal idproveedor,
			String razon_social, String domicilio, BigDecimal idlocalidad,
			BigDecimal idprovincia, String postal, String contacto,
			String telefono, String cuit, String brutos, BigDecimal ctapasivo,
			BigDecimal ctaactivo1, BigDecimal ctaactivo2,
			BigDecimal ctaactivo3, BigDecimal ctaactivo4, BigDecimal ctaiva,
			BigDecimal ctaretiva, String letra_iva, BigDecimal ctadocumen,
			String ret_gan, BigDecimal idretencion1, BigDecimal idretencion2,
			BigDecimal idretencion3, BigDecimal idretencion4,
			BigDecimal idretencion5, BigDecimal ctades, String stock_fact,
			BigDecimal idcondicionpago, BigDecimal cent1, BigDecimal cent2,
			BigDecimal cent3, BigDecimal cent4, BigDecimal cents1,
			BigDecimal cents2, BigDecimal cents3, BigDecimal cents4,
			String email, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idproveedor == null)
			salida = "Error: No se puede dejar vacio el campo: idproveedor ";
		if (razon_social == null)
			salida = "Error: No se puede dejar vacio el campo: Razon Social!";
		if (razon_social.equalsIgnoreCase(""))
			salida = salida = "Error: No se puede dejar vacio el campo: Razon Social!";
		if (domicilio == null)
			salida = "Error: No se puede dejar vacio) el campo: Domicilio!";
		if (domicilio.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Domicilio!";
		if (idlocalidad == null)
			salida = "Error: No se puede dejar vacio el campo: Localidad!";
		// if (idlocalidad.compareTo("0") < 1)
		// salida = "Error: No se puede dejar sin datos (nulo) el campo:
		// localidad!";
		if (idprovincia == null)
			salida = "Error: Por favor seleccione una Localidad!";
		if (idprovincia.compareTo(new BigDecimal(0)) == 0)
			salida = "Error: Por favor seleccione una Localidad!";
		if (ctapasivo == null)
			salida = "Error: No se puede dejar vacio el campo: Cta Pasivo!";
		if (ctapasivo.compareTo(new BigDecimal(0)) == 0)
			salida = "Error: No se puede dejar vacio el campo: Cta Pasivo!";
		if (idcondicionpago.compareTo(new BigDecimal(0)) == 0)
			salida = "Error: No se puede dejar vacio el campo: Condicion de Pago!";
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM proveedoProveed WHERE idproveedor = "
					+ idproveedor.toString()
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
					sql = ""
							+ "UPDATE PROVEEDOPROVEED SET razon_social=?, domicilio=?, idlocalidad=?, idprovincia=?, postal=?, contacto=?, telefono=?, "
							+ "       cuit=?, brutos=?, ctapasivo=?, ctaactivo1=?, ctaactivo2=?, ctaactivo3=?, ctaactivo4=?, ctaiva=?, ctaretiva=?, letra_iva=?, "
							+ "       ctadocumen=?, ret_gan=?, idretencion1=?, idretencion2=?, idretencion3=?, idretencion4=?, idretencion5=?, ctades=?,"
							+ "        stock_fact=?, idcondicionpago=?, cent1=?, cent2=?, cent3=?, cent4=?, cents1=?, cents2=?, cents3=?, cents4=?, email=?, "
							+ "       usuarioact=?, fechaact=? "
							+ " WHERE idproveedor=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, razon_social);
					insert.setString(2, domicilio);
					insert.setBigDecimal(3, idlocalidad);
					insert.setBigDecimal(4, idprovincia);
					insert.setString(5, postal);
					insert.setString(6, contacto);
					insert.setString(7, telefono);
					insert.setString(8, cuit);
					insert.setString(9, brutos);
					insert.setBigDecimal(10, ctapasivo);

					insert.setBigDecimal(11, ctaactivo1);
					insert.setBigDecimal(12, ctaactivo2);
					insert.setBigDecimal(13, ctaactivo3);
					insert.setBigDecimal(14, ctaactivo4);
					insert.setBigDecimal(15, ctaiva);
					insert.setBigDecimal(16, ctaretiva);

					insert.setString(17, letra_iva);
					insert.setBigDecimal(18, ctadocumen);
					insert.setString(19, ret_gan);

					insert.setBigDecimal(20, idretencion1);
					insert.setBigDecimal(21, idretencion2);
					insert.setBigDecimal(22, idretencion3);
					insert.setBigDecimal(23, idretencion4);
					insert.setBigDecimal(24, idretencion5);

					insert.setBigDecimal(25, ctades);
					if (stock_fact.equalsIgnoreCase("")) {
						insert.setString(26, "N");
					} else {
						insert.setString(26, stock_fact);
					}

					insert.setBigDecimal(27, idcondicionpago);
					insert.setBigDecimal(28, cent1);
					insert.setBigDecimal(29, cent2);
					insert.setBigDecimal(30, cent3);
					insert.setBigDecimal(31, cent4);
					insert.setBigDecimal(32, cents1);
					insert.setBigDecimal(33, cents2);
					insert.setBigDecimal(34, cents3);
					insert.setBigDecimal(35, cents4);
					insert.setString(36, email);
					insert.setString(37, usuarioact);
					insert.setTimestamp(38, fechaact);
					insert.setBigDecimal(39, idproveedor);
					insert.setBigDecimal(40, idempresa);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String proveedoProveedUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String proveedoProveedUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * Metodos para la entidad: proveedoMovProv Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Thu Jul 27 09:30:44 GMT-03:00 2006
	 */

	/**
	 * 20071109 ..............................................................
	 * EJV ...................................................................
	 * Borrado Fisico de :....................................................
	 * proveedomovprov - proveedocancprov - stockmovstock - stockhis - .......
	 * proveedocontprov - stockcontstaj - ....................................
	 * Actualiza: stockbis....................................................
	 */

	public String anularComprobanteProveedor(BigDecimal nrointerno,
			BigDecimal idproveedor, BigDecimal idempresa, String usuarioact)
			throws EJBException, SQLException {
		String salida = "OK";

		dbconn.setAutoCommit(false);
		List listaMovProvCantidades;
		List listaCancProv;
		Iterator iterMovProvCantidades;
		Iterator iterCancProv;
		boolean isComprobConStock = false;
		BigDecimal nrointerno_ms = new BigDecimal(0);

		try {

			/*
			 * 1 - Si esta aplicado, desaplicar, y actualizar saldo de
			 * documentos que lo aplican.PROVEEDOCANCPROV.......................
			 * 2 - Si aplica, desaplicar y actualizar saldo de documentos a los
			 * que aplica.PROVEEDOCANCPROV......................................
			 * 3 - Si afecta stock, eliminar todos los movimientos de Stock y
			 * actualizar cantidades por deposito(si las existentes lo permiten,
			 * caso contrario, se cancela la transaccion) ( STOCKMOVSTOCK -
			 * STOCKHIS ) ......................................................
			 * 4 - Anular los datos contables (PROVEEDOCONTPROV -...............
			 * STOCKCONTSTAJ)
			 */
			isComprobConStock = GeneralBean.isCombrobanteConStock(nrointerno,
					idempresa, dbconn);

			if (isComprobConStock) {

				listaMovProvCantidades = getMovProvCantidades(nrointerno,
						idproveedor, idempresa);
				iterMovProvCantidades = listaMovProvCantidades.iterator();

				while (iterMovProvCantidades.hasNext()) {

					String[] datosMov = (String[]) iterMovProvCantidades.next();
					if (datosMov[6].equalsIgnoreCase("N")) {
						salida = "Imposible Anular. Existencia de Art�culos Relacionados al Comprobante Insuficiente (Art:"
								+ datosMov[2]
								+ "- Dep.:"
								+ datosMov[3]
								+ "- Cant.:" + datosMov[4] + ").";
						break;
					}
					nrointerno_ms = new BigDecimal(datosMov[1]);
					salida = stockHisDelete(nrointerno_ms, datosMov[2],
							datosMov[3], idempresa);

					if (salida.equalsIgnoreCase("OK")) {

						salida = stockStockBisUpdate(datosMov[2],
								new BigDecimal(datosMov[3]), new BigDecimal(
										datosMov[5]).negate(), "", "",
								new BigDecimal("0"), usuarioact, idempresa);

						if (!salida.equalsIgnoreCase("OK"))
							break;

					} else
						break;

				}

				if (salida.equalsIgnoreCase("OK")) {

					salida = stockContStAjDelete(nrointerno_ms, "P", idempresa);

					if (salida.equalsIgnoreCase("OK")) {

						salida = stockMovStockDelete(nrointerno_ms, idempresa);

					}
				}

			}

			if (salida.equalsIgnoreCase("OK")) {
				// TODO: Verificar la necesidad de validar si se ejecuta o no la
				// desaplicacion
				// Inicia Desaplicacion
				listaCancProv = getComprobantesAplicadosProveedo(nrointerno,
						dbconn, idempresa);
				iterCancProv = listaCancProv.iterator();

				while (iterCancProv.hasNext()) {

					String[] datosCancProv = (String[]) iterCancProv.next();

					salida = proveedoMovProvSaldoUpdate(new BigDecimal(
							datosCancProv[1]), idproveedor, new BigDecimal(
							datosCancProv[0]), dbconn, usuarioact, idempresa);

					if (!salida.equalsIgnoreCase("OK"))
						break;

					// 20090519 EJV
					salida = proveedoAnulaAplicaciones(new BigDecimal(
							datosCancProv[1]), nrointerno, dbconn, idempresa);

					if (!salida.equalsIgnoreCase("OK"))
						break;

				}

				// 20090519 EJV
				// salida = proveedoAnulaAplicaciones(nrointerno,
				// "nrointerno_q_can", dbconn, idempresa);

				if (salida.equalsIgnoreCase("OK")) {

					listaCancProv = getComprobantesQueAplicanProveedo(
							nrointerno, dbconn, idempresa);
					iterCancProv = listaCancProv.iterator();

					while (iterCancProv.hasNext()) {

						String[] datosCancProv = (String[]) iterCancProv.next();

						salida = proveedoMovProvSaldoUpdate(new BigDecimal(
								datosCancProv[1]), idproveedor, new BigDecimal(
								datosCancProv[0]), dbconn, usuarioact,
								idempresa);

						if (!salida.equalsIgnoreCase("OK"))
							break;

						// 20090519 EJV
						salida = proveedoAnulaAplicaciones(nrointerno,
								new BigDecimal(datosCancProv[1]), dbconn,
								idempresa);

						if (!salida.equalsIgnoreCase("OK"))
							break;

					}
					// 20090519 EJV
					// salida = proveedoAnulaAplicaciones(nrointerno,
					// "nrointerno_canc", dbconn, idempresa);
				}
				// Fin Desaplicacion

				if (salida.equalsIgnoreCase("OK")) {

					salida = proveedoContProvDelete(nrointerno, dbconn,
							idempresa);

					if (salida.equalsIgnoreCase("OK")) {

						salida = proveedoMovProvDelete(nrointerno, idempresa);
					}

				}

			}

			// log.info("salida: "+salida);
			// throw new Exception("ERROR FORZADO....");

			// getComprobantesAplicados
			// proveedoAnulaAplicaciones campo = nrointerno_q_can
			// getComprobantesQueAplican
			// proveedoAnulaAplicaciones campo = nrointerno_canc
			// GeneralBean.getCantidadArticuloDeposito();
			// proveedoMovProvDelete()
			// proveedoContProv
			//
			// stockHisDelete
			// stockBisUpdate
			// stockMovStockDelete
			//

		} catch (Exception e) {
			salida = "Error al Anular Comprobante.";
			log.error("anularComprobanteProveedor: " + e);
		}

		if (!salida.equalsIgnoreCase("OK")) {
			dbconn.rollback();
		} else {
			dbconn.commit();
		}

		dbconn.setAutoCommit(true);

		return salida;
	}

	/*
	 * Recupera comprobante e importe para documentos aplicados por uno en
	 * perticular.
	 */

	public static List getComprobantesAplicadosProveedo(BigDecimal nrointerno,
			Connection conn, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "                  "
				+ "SELECT importe, nrointerno_canc  FROM proveedocancprov  WHERE nrointerno_q_can ="
				+ nrointerno.toString() + " AND idempresa="
				+ idempresa.toString() + " ORDER BY 2   ";
		List vecSalida = new ArrayList();
		try {
			Statement statement = conn.createStatement();
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
					.error("Error SQL en el metodo : getComprobantesAplicadosProveedo() "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getComprobantesAplicadosProveedo()  "
							+ ex);
		}
		return vecSalida;
	}

	/*
	 * Recupera comprobante e importes que aplican a uno en perticular.
	 */

	public static List getComprobantesQueAplicanProveedo(BigDecimal nrointerno,
			Connection conn, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT importe, nrointerno_q_can FROM proveedocancprov WHERE nrointerno_canc = "
				+ nrointerno.toString()
				+ " AND idempresa="
				+ idempresa.toString() + " ORDER BY 2   ";
		List vecSalida = new ArrayList();
		try {
			Statement statement = conn.createStatement();
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
					.error("Error SQL en el metodo : getComprobantesQueAplicanProveedo() "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getComprobantesQueAplicanProveedo()  "
							+ ex);
		}
		return vecSalida;
	}

	/*
	 * Anula aplicaciones que involucran a un documento, ya sea este quien
	 * aplica o sea aplicado.
	 */

	public static String proveedoAnulaAplicaciones(BigDecimal nrointerno_canc,
			BigDecimal nrointerno_q_can, Connection conn, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " SELECT * FROM PROVEEDOCANCPROV WHERE nrointerno_canc = "
				+ nrointerno_canc.toString()
				+ " AND nrointerno_q_can = "
				+ nrointerno_q_can.toString()
				+ " AND idempresa = "
				+ idempresa.toString();

		String salida = "OK";
		try {
			Statement statement = conn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = " DELETE FROM PROVEEDOCANCPROV WHERE nrointerno_canc = "
						+ nrointerno_canc.toString()
						+ " AND nrointerno_q_can = "
						+ nrointerno_q_can
						+ " AND idempresa = " + idempresa.toString();
				statement.execute(cQuery);
			}

		} catch (SQLException sqlException) {
			salida = "Imposible eliminar cancelaci�n / aplicaci�n.";
			log
					.error("Error SQL en el metodo : proveedoAnulaAplicaciones( BigDecimal nrointerno... ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible  cancelaci�n / aplicaci�n.";
			log
					.error("Salida por exception: en el metodo: proveedoAnulaAplicaciones( BigDecimal nrointerno... )  "
							+ ex);
		}
		return salida;
	}

	/*
	 * Recupera para los comprobantes que afectan stock, las cantidades que
	 * movilizo en un determinado deposito para un determinado articulo, y las
	 * actuales para verificar que pueda anularse el movimiento.
	 */

	public List getMovProvCantidades(BigDecimal nrointerno,
			BigDecimal idproveedor, BigDecimal idempresa) throws EJBException {

		String cQuery = ""
				+ "SELECT mp.nrointerno, ms.nrointerno_ms, sh.articu_sh, sh.deposi_sh, 0 AS canti_ms, "
				+ "       sh.canti_sh, COALESCE(sb.canti_sb, 0) AS canti_sb, "
				+ "       CASE  WHEN sh.canti_sh > COALESCE(sb.canti_sb, 0) THEN 'N' ELSE 'S' END AS stocksuficiente, ms.idempresa "
				+ "  FROM proveedomovprov mp "
				+ "       INNER JOIN "
				+ "               ("
				+ "                SELECT nrointerno_ms, comprob_ms, sistema_ms, idempresa "
				+ "                 FROM stockmovstock "
				+ "                WHERE comprob_ms ="
				+ nrointerno.toString()
				+ " AND idempresa ="
				+ idempresa.toString()
				+ " AND sistema_ms = 'P' LIMIT 1 "
				+ "               ) ms "
				+ "               ON mp.nrointerno = ms.comprob_ms AND mp.idempresa = ms.idempresa  AND ms.sistema_ms = 'P' "
				+ "       INNER JOIN stockhis sh ON ms.nrointerno_ms = sh.nromov_sh "
				+ "             /*AND ms.articu_ms = sh.articu_sh*/ "
				+ "             AND ms.idempresa = sh.idempresa "
				+ "       LEFT  JOIN stockstockbis sb ON sh.articu_sh = sb.articu_sb "
				+ "             AND sh.deposi_sh = sb.deposi_sb "
				+ "             AND sh.idempresa = sb.idempresa "
				+ " WHERE mp.nrointerno = " + nrointerno.toString()
				+ "   AND ms.idempresa = " + idempresa.toString()
				+ "   AND mp.idproveedor = " + idproveedor.toString()
				+ " ORDER BY 1 ";
		log.info(cQuery);
		/*
		 * + "SELECT mp.nrointerno, ms.nrointerno_ms, sh.articu_sh,
		 * sh.deposi_sh, ms.canti_ms, " + " sh.canti_sh, COALESCE(sb.canti_sb,
		 * 0) AS canti_sb, " + " CASE WHEN sh.canti_sh > COALESCE(sb.canti_sb,
		 * 0) THEN 'N' ELSE 'S' END AS stocksuficiente, ms.idempresa " + " FROM
		 * proveedomovprov mp " + " INNER JOIN stockmovstock ms ON mp.nrointerno
		 * = ms.comprob_ms AND mp.idempresa = ms.idempresa AND ms.sistema_ms =
		 * 'P' " + " INNER JOIN stockhis sh ON ms.nrointerno_ms = sh.nromov_sh
		 * " + " AND ms.articu_ms = sh.articu_sh " + " AND ms.idempresa =
		 * sh.idempresa " + " LEFT JOIN stockstockbis sb ON sh.articu_sh =
		 * sb.articu_sb " + " AND sh.deposi_sh = sb.deposi_sb " + " AND
		 * sh.idempresa = sb.idempresa " + " WHERE mp.nrointerno =
		 * " + nrointerno.toString() + " AND ms.idempresa = " +
		 * idempresa.toString() + " AND mp.idproveedor = " +
		 * idproveedor.toString() + " ORDER BY 1 ";
		 */

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/*
	 * Elimina hist�rico de movimientos de Stock.
	 */

	public String stockHisDelete(BigDecimal nromov_sh, String articu_sh,
			String deposi_sh, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM STOCKHIS WHERE nromov_sh="
				+ nromov_sh.toString() + " AND articu_sh = '" + articu_sh
				+ "' AND deposi_sh = '" + deposi_sh + "' AND idempresa = "
				+ idempresa.toString();
		String salida = "OK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {

				cQuery = "DELETE FROM STOCKHIS WHERE nromov_sh="
						+ nromov_sh.toString() + " AND articu_sh = '"
						+ articu_sh + "' AND deposi_sh = '" + deposi_sh
						+ "' AND idempresa = " + idempresa.toString();
				statement.execute(cQuery);

			} else {
				salida = "Error: Iposible eliminar hist�rico - Registro inexistente.";
			}

		} catch (SQLException sqlException) {
			salida = "Imposible eliminar  Hist�rico mov. Stock.";
			log
					.error("Error SQL en el metodo : stockHisDelete( BigDecimal nromov_sh ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar Hist�rico mov. Stock.";
			log
					.error("Salida por exception: en el metodo: stockHisDelete( BigDecimal nromov_sh )  "
							+ ex);
		}
		return salida;
	}

	/*
	 * Elimina movimiento de Stock.
	 */

	public String stockMovStockDelete(BigDecimal nrointerno_ms,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT COUNT(1) FROM STOCKMOVSTOCK WHERE nrointerno_ms="
				+ nrointerno_ms.toString()
				+ " AND idempresa="
				+ idempresa.toString();
		String salida = "OK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM STOCKMOVSTOCK WHERE nrointerno_ms="
						+ nrointerno_ms.toString() + " AND idempresa="
						+ idempresa.toString();
				statement.execute(cQuery);

			} else {
				salida = "Error: Mov. Stock inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar Mov. Stock.";
			log
					.error("Error SQL en el metodo : stockMovStockDelete( BigDecimal nrointerno_ms ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar Mov. Stock.";
			log
					.error("Salida por exception: en el metodo: stockMovStockDelete( BigDecimal nrointerno_ms )  "
							+ ex);
		}
		return salida;
	}

	/*
	 * Elimina Asientos de Ajustes.
	 */

	public String stockContStAjDelete(BigDecimal idcontstaj, String sistema_cj,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT COUNT(1) FROM STOCKCONTSTAJ WHERE idcontstaj="
				+ idcontstaj.toString() + " AND idempresa="
				+ idempresa.toString() + " AND sistema_cj ='" + sistema_cj
				+ "'";
		String salida = "OK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM STOCKCONTSTAJ WHERE idcontstaj="
						+ idcontstaj.toString() + " AND idempresa="
						+ idempresa.toString() + " AND sistema_cj ='"
						+ sistema_cj + "'";
				statement.execute(cQuery);
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar Asiento de Ajuste.";
			log
					.error("Error SQL en el metodo : stockContStAjDelete( BigDecimal idcontstaj ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar Asiento de Ajuste.";
			log
					.error("Salida por exception: en el metodo: stockContStAjDelete( BigDecimal idcontstaj )  "
							+ ex);
		}
		return salida;
	}

	/*
	 * Elimina asiento contable proveedor.
	 */

	public static String proveedoContProvDelete(BigDecimal compr_con,
			Connection conn, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT COUNT(1) FROM PROVEEDOCONTPROV WHERE compr_con="
				+ compr_con.toString()
				+ " AND idempresa="
				+ idempresa.toString();
		String salida = "OK";
		try {
			Statement statement = conn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM PROVEEDOCONTPROV WHERE compr_con="
						+ compr_con.toString() + " AND idempresa="
						+ idempresa.toString();
				statement.execute(cQuery);
			} else {
				salida = "Error: Mov. Contable inexistente";
			}

		} catch (SQLException sqlException) {
			salida = "Imposible eliminar Mov. Contable.";
			log
					.error("Error SQL en el metodo : proveedoContProvDelete( BigDecimal compr_con ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar  Mov. Contable.";
			log
					.error("Salida por exception: en el metodo: proveedoContProvDelete( compr_con cuenta_con )  "
							+ ex);
		}
		return salida;
	}

	/*
	 * Crea asiento contable proveedor
	 */

	public String proveedoContProvCreate(BigDecimal cuenta_con,
			BigDecimal compr_con, BigDecimal import_con, String nroiva_con,
			BigDecimal centcost, BigDecimal centcost2, String detalle,
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
			if (salida.equalsIgnoreCase("OK") && import_con.doubleValue() > 0) {
				String ins = ""
						+ "INSERT INTO proveedocontprov(cuenta_con, compr_con, import_con, nroiva_con, centcost, centcost2, detalle, idempresa, usuarioalt ) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, cuenta_con);
				insert.setBigDecimal(2, compr_con);
				insert.setBigDecimal(3, import_con);
				insert.setString(4, nroiva_con);
				insert.setBigDecimal(5, centcost);
				insert.setBigDecimal(6, centcost2);
				insert.setString(7, detalle);
				insert.setBigDecimal(8, idempresa);
				insert.setString(9, usuarioalt);
				int n = insert.executeUpdate();
				if (n != 1)
					salida = "Imposible generar registro de asiento contable. ";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta registro para asiento contable.";
			log.error("Error SQL public String proveedoContProvCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta registro para asiento contable.";
			log
					.error("Error excepcion public String proveedoContProvCreate(.....)"
							+ ex);
		}
		return salida;
	}

	/*
	 * TODO: EJV - 20071116 METODO DUPLICADO EN STOCK. VER DE UNIFICAR !!!
	 */

	public String stockStockBisUpdate(String articu_sb, BigDecimal deposi_sb,
			BigDecimal canti_sb, String serie_sb, String despa_sb,
			BigDecimal pedid_sb, String usuarioact, BigDecimal idempresa)
			throws EJBException {
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
				qDML += " WHERE articu_sb=? AND deposi_sb=?  AND idempresa=?";
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
					salida = "Articulo (" + articu_sb
							+ ") inexistente en deposito (" + deposi_sb + ").";
				}
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String stockStockBisUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String stockStockBisUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	/*
	 * Actualiza saldo de documento de proveedores, generalmente al desaplicar
	 * documentos.
	 */

	public static String proveedoMovProvSaldoUpdate(BigDecimal nrointerno,
			BigDecimal idproveedor, BigDecimal saldo, Connection conn,
			String usuarioact, BigDecimal idempresa) throws EJBException {
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
					+ " AND idempresa="
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
					sql = "UPDATE PROVEEDOMOVPROV SET  saldo = (saldo + ?) , usuarioact=?, fechaact=? WHERE nrointerno=? AND idempresa=?;";
					insert = conn.prepareStatement(sql);
					insert.setBigDecimal(1, saldo);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, nrointerno);
					insert.setBigDecimal(5, idempresa);
				}

				int i = insert.executeUpdate();
				if (i < 0)
					salida = "Imposible actualizar saldo.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar saldo.";
			log
					.error("Error SQL public String proveedoMovProvSaldoUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar saldo.";
			log
					.error("Error excepcion public String proveedoMovProvSaldoUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// 20090520 - EJV
	public static String proveedoMovProvSaldoUpdateOrDelete(
			BigDecimal nrointerno, BigDecimal idproveedor, BigDecimal saldo,
			Connection conn, String usuarioact, BigDecimal idempresa)
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
			String cQuery = "SELECT tipomov FROM proveedoMovProv WHERE nrointerno = "
					+ nrointerno.toString()
					+ " AND idempresa="
					+ idempresa.toString();
			Statement statement = conn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int tipomov = 0;
			if (salida.equalsIgnoreCase("OK")) {

				if (rsSalida != null && rsSalida.next()) {
					tipomov = rsSalida.getInt(1);
					PreparedStatement instrucciondml = null;
					String sql = "";

					if (tipomov <= 3) { // si existe hago update
						sql = "UPDATE proveedomovprov SET  saldo = (saldo + ?) , usuarioact=?, fechaact=? WHERE nrointerno=? AND idempresa=?;";
						instrucciondml = conn.prepareStatement(sql);
						instrucciondml.setBigDecimal(1, saldo);
						instrucciondml.setString(2, usuarioact);
						instrucciondml.setTimestamp(3, fechaact);
						instrucciondml.setBigDecimal(4, nrointerno);
						instrucciondml.setBigDecimal(5, idempresa);
					} else {
						sql = "DELETE proveedomovprov WHERE nrointerno=? AND idempresa=?;";
						instrucciondml.setBigDecimal(4, nrointerno);
						instrucciondml.setBigDecimal(5, idempresa);
					}

					int i = instrucciondml.executeUpdate();
					if (i < 0)
						salida = "Imposible actualizar saldo.";
				}

			}

		} catch (SQLException sqlException) {
			salida = "Imposible actualizar saldo.";
			log
					.error("Error SQL public String proveedoMovProvSaldoUpdateOrDelete(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar saldo.";
			log
					.error("Error excepcion public String proveedoMovProvSaldoUpdateOrDelete(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * FIN anularComprobanteProveedor
	 * 
	 */

	/**
	 * Modificar Comprobante
	 * 
	 * 
	 */

	public String modificarComprobanteProveedor(BigDecimal nrointerno,
			BigDecimal idproveedor, Timestamp fechamov, BigDecimal sucursal,
			BigDecimal comprob, BigDecimal tipomov, String tipomovs,
			BigDecimal importe, BigDecimal saldo, BigDecimal idcondicionpago,
			Timestamp fecha_subd, BigDecimal retoque, java.sql.Date fechavto,
			Hashtable htAsiento, Hashtable htArticulos, String usuarioact,
			BigDecimal idempresa, String observacionesContables)
			throws EJBException, SQLException {
		String salida = "OK";

		List listaCancProv;
		Iterator iterCancProv;
		boolean isComprobConStock = false;

		dbconn.setAutoCommit(false);
		try {

			tipomovs = getTipoComprobante(tipomov, tipomovs);
			/*
			 * switch (tipomov.intValue()) { case 1: tipomovs = "FA" + tipomovs;
			 * break; case 2: tipomovs = "ND" + tipomovs; break; case 3:
			 * tipomovs = "NC" + tipomovs; break; case 4: tipomovs = "PA" +
			 * tipomovs;// ?? break; default: break; }
			 */

			if (salida.equalsIgnoreCase("OK")) {
				// TODO: Verificar la necesidad de validar si se ejecuta o no la
				// desaplicacion
				// Inicia Desaplicacion
				listaCancProv = getComprobantesAplicadosProveedo(nrointerno,
						dbconn, idempresa);
				iterCancProv = listaCancProv.iterator();

				while (iterCancProv.hasNext()) {

					String[] datosCancProv = (String[]) iterCancProv.next();

					salida = proveedoMovProvSaldoUpdate(new BigDecimal(
							datosCancProv[1]), idproveedor, new BigDecimal(
							datosCancProv[0]), dbconn, usuarioact, idempresa);

					if (!salida.equalsIgnoreCase("OK"))
						break;

					// 20090519 EJV
					salida = proveedoAnulaAplicaciones(new BigDecimal(
							datosCancProv[1]), nrointerno, dbconn, idempresa);
					if (!salida.equalsIgnoreCase("OK"))
						break;

				}

				// 20090519 EJV
				// salida = proveedoAnulaAplicaciones(nrointerno,
				// "nrointerno_q_can", dbconn, idempresa);

				if (salida.equalsIgnoreCase("OK")) {

					listaCancProv = getComprobantesQueAplicanProveedo(
							nrointerno, dbconn, idempresa);
					iterCancProv = listaCancProv.iterator();

					while (iterCancProv.hasNext()) {

						String[] datosCancProv = (String[]) iterCancProv.next();

						salida = proveedoMovProvSaldoUpdate(new BigDecimal(
								datosCancProv[1]), idproveedor, new BigDecimal(
								datosCancProv[0]), dbconn, usuarioact,
								idempresa);

						if (!salida.equalsIgnoreCase("OK"))
							break;

						// 20090519 EJV
						salida = proveedoAnulaAplicaciones(nrointerno,
								new BigDecimal(datosCancProv[1]), dbconn,
								idempresa);
						if (!salida.equalsIgnoreCase("OK"))
							break;

					}
					// 20090519 EJV
					// salida = proveedoAnulaAplicaciones(nrointerno,
					// "nrointerno_canc", dbconn, idempresa);
				}
				// Fin Desaplicacion

				if (salida.equalsIgnoreCase("OK")) {

					salida = proveedoContProvDelete(nrointerno, dbconn,
							idempresa);

					Enumeration en = htAsiento.keys();

					while (en.hasMoreElements()
							&& salida.equalsIgnoreCase("OK")) {

						String key = (String) en.nextElement();
						String[] datosAsiento = (String[]) htAsiento.get(key);

						salida = proveedoContProvCreate(new BigDecimal(
								datosAsiento[0]), nrointerno, new BigDecimal(
								datosAsiento[2]), datosAsiento[3], null, null,
								null, idempresa, usuarioact);

						if (!salida.equalsIgnoreCase("OK")) {
							break;
						}

					}

					if (salida.equalsIgnoreCase("OK")) {

						salida = proveedoMovProvUpdate(nrointerno, idproveedor,
								fechamov, sucursal, comprob, tipomov, tipomovs,
								importe, saldo, idcondicionpago, fecha_subd,
								retoque, fechavto, usuarioact, idempresa,
								observacionesContables);

					}

				}

			}

		} catch (Exception e) {
			log.error("modificarComprobanteProveedor: " + e);
		}

		if (!salida.equalsIgnoreCase("OK"))
			dbconn.rollback();

		dbconn.setAutoCommit(true);

		return salida;
	}

	public List getProveedoContProvImportes(BigDecimal compr_con,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT cuenta_con,compr_con,import_con::numeric(18,2) as import_con,nroiva_con,centcost,centcost2,detalle, "
				+ "       idempresa,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM PROVEEDOCONTPROV WHERE compr_con="
				+ compr_con.toString() + " AND idempresa = "
				+ idempresa.toString() + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getProveedoContProvTotalImportes(BigDecimal compr_con,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT compr_con, SUM(import_con)::numeric(18,2) AS import_con, nroiva_con,idempresa  "
				+ "  FROM PROVEEDOCONTPROV WHERE compr_con="
				+ compr_con.toString() + " AND idempresa = "
				+ idempresa.toString()
				+ " GROUP BY compr_con, nroiva_con, idempresa;";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * Fin Modificar Comprobante
	 * 
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getProveedoMovProvAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT nrointerno,idproveedor,fechamov,sucursal,comprob,tipomov,tipomovs,importe,saldo,"
				+ "       idcondicionpago,fecha_subd,retoque,fechavto,"
				+ "       usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM PROVEEDOMOVPROV WHERE idempresa="
				+ idempresa.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getProveedoMovProvOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT nrointerno,idproveedor,fechamov,sucursal,comprob,tipomov,tipomovs,"
				+ "       importe,saldo,idcondicionpago,fecha_subd,retoque,fechavto,"
				+ "       usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM PROVEEDOMOVPROV "
				+ " WHERE ((IDPROVEEDOR::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%') AND idempresa = "
				+ idempresa.toString() + "  ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getProveedoMovProvPK(BigDecimal nrointerno, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT nrointerno,idproveedor,fechamov,sucursal,comprob,tipomov,tipomovs,importe,"
				+ "       saldo,idcondicionpago,fecha_subd,retoque,fechavto,"
				+ "       usuarioalt,usuarioact,fechaalt,fechaact, obscontable"
				+ "  FROM PROVEEDOMOVPROV WHERE nrointerno="
				+ nrointerno.toString() + " AND idempresa="
				+ idempresa.toString();

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String proveedoMovProvDelete(BigDecimal nrointerno,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String salida = "";

		try {

			salida = proveedoMovProvDelete(nrointerno, dbconn, idempresa);

		} catch (Exception ex) {

			log
					.error("Salida por exception: en el metodo: proveedoMovProvDelete( BigDecimal nrointerno,	BigDecimal idempresa )  "
							+ ex);
		}
		return salida;
	}

	public static String proveedoMovProvDelete(BigDecimal nrointerno,
			Connection conn, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT COUNT(1) FROM PROVEEDOMOVPROV WHERE nrointerno="
				+ nrointerno.toString()
				+ " AND idempresa= "
				+ idempresa.toString();
		String salida = "OK";
		try {
			Statement statement = conn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM PROVEEDOMOVPROV WHERE nrointerno="
						+ nrointerno.toString() + " AND idempresa= "
						+ idempresa.toString();
				statement.execute(cQuery);

			} else {
				salida = "Error: Movimiento de Proveedor Inexistente.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : proveedoMovProvDelete( BigDecimal nrointerno,	Connection conn, BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: proveedoMovProvDelete( BigDecimal nrointerno,	Connection conn, BigDecimal idempresa  )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String proveedoMovProvCreate(BigDecimal idproveedor,
			Timestamp fechamov, BigDecimal sucursal, BigDecimal comprob,
			BigDecimal tipomov, String tipomovs, Double importe, Double saldo,
			BigDecimal idcondicionpago, Timestamp fecha_subd,
			BigDecimal retoque, java.sql.Date fechavto, String usuarioalt,
			BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
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
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO PROVEEDOMOVPROV(idproveedor, fechamov, sucursal, comprob, tipomov, tipomovs, importe, saldo, idcondicionpago, fecha_subd, retoque, fechavto, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, idproveedor);
				insert.setTimestamp(2, fechamov);
				insert.setBigDecimal(3, sucursal);
				insert.setBigDecimal(4, comprob);
				insert.setBigDecimal(5, tipomov);
				insert.setString(6, tipomovs);
				insert.setDouble(7, importe.doubleValue());
				insert.setDouble(8, saldo.doubleValue());
				insert.setBigDecimal(9, idcondicionpago);
				insert.setTimestamp(10, fecha_subd);
				insert.setBigDecimal(11, retoque);
				insert.setDate(12, fechavto);
				insert.setString(13, usuarioalt);
				insert.setBigDecimal(14, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
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

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String proveedoMovProvCreateOrUpdate(BigDecimal nrointerno,
			BigDecimal idproveedor, Timestamp fechamov, BigDecimal sucursal,
			BigDecimal comprob, BigDecimal tipomov, String tipomovs,
			Double importe, Double saldo, BigDecimal idcondicionpago,
			Timestamp fecha_subd, BigDecimal retoque, java.sql.Date fechavto,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (nrointerno == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nrointerno ";
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

		// 2. sin nada desde la pagina
		if (tipomovs.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipomovs ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
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
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE PROVEEDOMOVPROV SET idproveedor=?, fechamov=?, sucursal=?, comprob=?, tipomov=?, tipomovs=?, importe=?, saldo=?, idcondicionpago=?, fecha_subd=?, retoque=?, fechavto=?, usuarioact=?, fechaact=? WHERE nrointerno=? AND idempresa = ?; ";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, idproveedor);
					insert.setTimestamp(2, fechamov);
					insert.setBigDecimal(3, sucursal);
					insert.setBigDecimal(4, comprob);
					insert.setBigDecimal(5, tipomov);
					insert.setString(6, tipomovs);
					insert.setDouble(7, importe.doubleValue());
					insert.setDouble(8, saldo.doubleValue());
					insert.setBigDecimal(9, idcondicionpago);
					insert.setTimestamp(10, fecha_subd);
					insert.setBigDecimal(11, retoque);
					insert.setDate(12, fechavto);
					insert.setString(13, usuarioact);
					insert.setTimestamp(14, fechaact);
					insert.setBigDecimal(15, nrointerno);
					insert.setBigDecimal(16, idempresa);
				} else {
					String ins = "INSERT INTO PROVEEDOMOVPROV(idproveedor, fechamov, sucursal, comprob, tipomov, tipomovs, importe, saldo, idcondicionpago, fecha_subd, retoque, fechavto, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setBigDecimal(1, idproveedor);
					insert.setTimestamp(2, fechamov);
					insert.setBigDecimal(3, sucursal);
					insert.setBigDecimal(4, comprob);
					insert.setBigDecimal(5, tipomov);
					insert.setString(6, tipomovs);
					insert.setDouble(7, importe.doubleValue());
					insert.setDouble(8, saldo.doubleValue());
					insert.setBigDecimal(9, idcondicionpago);
					insert.setTimestamp(10, fecha_subd);
					insert.setBigDecimal(11, retoque);
					insert.setDate(12, fechavto);
					insert.setString(13, usuarioalt);
					insert.setBigDecimal(14, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String proveedoMovProvCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String proveedoMovProvCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String proveedoMovProvUpdate(BigDecimal nrointerno,
			BigDecimal idproveedor, Timestamp fechamov, BigDecimal sucursal,
			BigDecimal comprob, BigDecimal tipomov, String tipomovs,
			BigDecimal importe, BigDecimal saldo, BigDecimal idcondicionpago,
			Timestamp fecha_subd, BigDecimal retoque, java.sql.Date fechavto,
			String usuarioact, BigDecimal idempresa,
			String observacionesContables) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (nrointerno == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nrointerno ";
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

		// 2. sin nada desde la pagina
		if (tipomovs.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipomovs ";
		// fin validaciones
		if (observacionesContables.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Observaciones Contables ";

		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM proveedoMovProv WHERE nrointerno = "
					+ nrointerno.toString()
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
					sql = "UPDATE PROVEEDOMOVPROV SET idproveedor=?, fechamov=?, sucursal=?, comprob=?, tipomov=?, tipomovs=?, importe=?, saldo=?, idcondicionpago=?, fecha_subd=?, retoque=?, fechavto=?, usuarioact=?, fechaact=?, obscontable=? WHERE nrointerno=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, idproveedor);
					insert.setTimestamp(2, fechamov);
					insert.setBigDecimal(3, sucursal);
					insert.setBigDecimal(4, comprob);
					insert.setBigDecimal(5, tipomov);
					insert.setString(6, tipomovs);
					insert.setBigDecimal(7, importe);
					insert.setBigDecimal(8, saldo);
					insert.setBigDecimal(9, idcondicionpago);
					insert.setTimestamp(10, fecha_subd);
					insert.setBigDecimal(11, retoque);
					insert.setDate(12, fechavto);
					insert.setString(13, usuarioact);
					insert.setTimestamp(14, fechaact);
					insert.setString(15, observacionesContables);
					insert.setBigDecimal(16, nrointerno);
					insert.setBigDecimal(17, idempresa);
				}

				int i = insert.executeUpdate();
				if (i != 1)
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar movimiento de proveedor.";
			log.error("Error SQL public String proveedoMovProvUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar movimiento de proveedor.";
			log
					.error("Error excepcion public String proveedoMovProvUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// para todo (ordena por el segundo campo por defecto)
	public List getComprobantesProveedorAll(long limit, long offset,
			BigDecimal idproveedor, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT DISTINCT mp.nrointerno, mp.fechamov, mp.saldo::numeric(18,2) as saldo, mp.importe::numeric(18,2) as importe, mp.tipomovs, "
				+ "       LPAD(mp.sucursal::varchar, 4 , '0') AS sucursal, LPAD(mp.comprob::varchar, 8 , '0') AS comprobante,   "
				+ "       CASE WHEN ms.comprob_ms IS NULL THEN  'N' ELSE  'S' END::CHARACTER VARYING(1)  AS constock, "
				+ "       CASE WHEN cancela.nrointerno_q_can IS NULL THEN  'N' ELSE  'S' END::CHARACTER VARYING(1)  AS aplica, "
				+ "       CASE WHEN cancelado.nrointerno_canc IS NULL THEN  'N' ELSE  'S' END::CHARACTER VARYING(1)  AS aplicado, "
				+ "       mp.sucursal, mp.comprob "
				+ "  FROM proveedomovprov mp  "
				+ "       LEFT JOIN stockmovstock ms ON mp.nrointerno = ms.comprob_ms AND mp.idempresa = ms.idempresa AND ms.sistema_ms = 'P'  "
				+ "       LEFT JOIN proveedocancprov cancela ON mp.nrointerno = cancela.nrointerno_q_can  AND mp.idempresa = cancela.idempresa  "
				+ "       LEFT JOIN proveedocancprov cancelado ON mp.nrointerno = cancelado.nrointerno_canc AND mp.idempresa = cancelado.idempresa  "
				+ " WHERE mp.idempresa = " + idempresa.toString()
				+ " AND mp.idproveedor = " + idproveedor.toString()
				+ " AND mp.tipomov <> 4 "
				+ " ORDER BY  mp.sucursal,  mp.comprob LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getComprobantesProveedorOcu(long limit, long offset,
			BigDecimal idproveedor, String ocurrencia, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT DISTINCT mp.nrointerno, mp.fechamov, mp.saldo::numeric(18, 2) as saldo, mp.importe::numeric(18, 2) as saldo, mp.tipomovs, "
				+ "       LPAD(mp.sucursal::varchar, 4 , '0') AS sucursal, LPAD(mp.comprob::varchar, 8 , '0') AS comprobante,   "
				+ "       CASE WHEN ms.comprob_ms IS NULL THEN  'N' ELSE  'S' END::CHARACTER VARYING(1)  AS constock, "
				+ "       CASE WHEN cancela.nrointerno_q_can IS NULL THEN  'N' ELSE  'S' END::CHARACTER VARYING(1)  AS aplica, "
				+ "       CASE WHEN cancelado.nrointerno_canc IS NULL THEN  'N' ELSE  'S' END::CHARACTER VARYING(1)  AS aplicado, "
				+ "       mp.sucursal, mp.comprob "
				+ "  FROM proveedomovprov mp  "
				+ "       LEFT JOIN stockmovstock ms ON mp.nrointerno = ms.comprob_ms AND mp.idempresa = ms.idempresa AND ms.sistema_ms = 'P'  "
				+ "       LEFT JOIN proveedocancprov cancela ON mp.nrointerno = cancela.nrointerno_q_can  AND mp.idempresa = cancela.idempresa  "
				+ "       LEFT JOIN proveedocancprov cancelado ON mp.nrointerno = cancelado.nrointerno_canc AND mp.idempresa = cancelado.idempresa  "
				+ " WHERE mp.comprob::VARCHAR LIKE '"
				+ ocurrencia.toUpperCase().trim() + "%' AND mp.idempresa = "
				+ idempresa.toString() + " AND mp.idproveedor = "
				+ idproveedor.toString() + " AND mp.tipomov <> 4 "
				+ " ORDER BY  mp.sucursal,  mp.comprob LIMIT " + limit
				+ " OFFSET  " + offset + ";";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// Retorna el saldo de comprobantes de proveedor y total de cuenta
	// corriente.
	public List getComprobantesProveedorSaldoImporte(BigDecimal idproveedor,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT SUM(saldo)as saldo, SUM(importe) as importe, count(1) "
				+ "  FROM proveedomovprov mp  " + " WHERE mp.idempresa = "
				+ idempresa.toString() + "   AND mp.idproveedor = "
				+ idproveedor.toString() + "   AND mp.tipomov <> 4 "
				+ " GROUP BY  mp.idproveedor,  mp.idempresa ;";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: stockstock - desde lov articulos Copyrigth(r)
	 * utilizada para registrar captura de documentos sysWarp S.R.L. Fecha de
	 * creacion: Wed Aug 02 14:39:13 GMT-03:00 2006
	 */

	public List getProveedoArticulosAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT codigo_st,alias_st,descrip_st,descri2_st, "
				+ "       cost_re_st, cost_uc_st, cost_pp_st,precipp_st, ultcomp_st, cuencom_st, "
				+ "       id_indi_st AS serializable, despa_st AS aceptadespacho"
				// "esserializable, numeroserie "
				+ "  FROM stockstock WHERE  inventa_st = 'S' AND  idempresa = "
				+ idempresa.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getProveedoArticulosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT codigo_st,alias_st,descrip_st,descri2_st, "
				+ "       cost_re_st, cost_uc_st, cost_pp_st,precipp_st, ultcomp_st, cuencom_st, "
				+ "       id_indi_st AS serializable, despa_st AS aceptadespacho "
				// "esserializable, numeroserie "
				+ "  FROM STOCKSTOCK "
				+ " WHERE  inventa_st = 'S' AND (UPPER(CODIGO_ST) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%'"
				+ " OR UPPER(ALIAS_ST) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%') AND idempresa = "
				+ idempresa.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getProveedoArticulosPK(String codigo_st, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT codigo_st, alias_st, descrip_st, descri2_st, "
				+ "       cost_re_st, cost_uc_st, cost_pp_st, precipp_st, ultcomp_st, "
				+ "       -1 as deposito, 0 as cantidad, 0 as total, cuencom_st, unimed_st, "
				+ "       cuenven_st, cuenve2_st, cuencos_st, "
				+ "       id_indi_st AS esserializable, despa_st AS aceptadespacho"
				+ "  FROM stockstock " + " WHERE codigo_st='" + codigo_st
				+ "'  AND idempresa = " + idempresa.toString();

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * COMBO STOCK-DEPOSITOS
	 */

	public List getProveedoStockDepositosAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ " SELECT codigo_dt,descrip_dt,direc_dt,factura_dt,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM STOCKDEPOSITOS " + " WHERE idempresa = "
				+ idempresa.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// Seteo - Recupero Variables ...

	public void setSetupVariable(String variable, String sistema, String valor,
			BigDecimal idempresa) throws EJBException {
		String cQuery = "";
		PreparedStatement statement;
		String mensaje = "OK";
		try {
			if (variable == null || variable.trim().length() == 0)
				mensaje = "Variable no informada.";
			if (sistema == null || sistema.trim().length() == 0)
				mensaje = "Sistema no informado.";
			if (valor == null || valor.trim().length() == 0)
				mensaje = "Valor vacio.";
			if (mensaje.equals("OK")) {
				cQuery = "UPDATE setupvariables SET  valor=? WHERE UPPER(variable)=? AND UPPER(sistema)=? AND idempresa=?";
				statement = dbconn.prepareStatement(cQuery);
				statement.setString(1, valor);
				statement.setString(2, variable.toUpperCase());
				statement.setString(3, sistema.toUpperCase());
				statement.setBigDecimal(4, idempresa);
				int i = statement.executeUpdate();
				if (i > 0)
					mensaje = "Actualizacion correcta de variable: " + variable;
				else
					mensaje = "Variable " + variable + " inexistente";
			}

		} catch (Exception e) {
			// TODO: handle exception
			log
					.error("setSetupVariable(String variable, String sistema, String valor): "
							+ e);
		}

	}

	public String getSetupVariable(String variable, String sistema,
			BigDecimal idempresa) throws EJBException {
		String cQuery = "";
		String valor = "";
		PreparedStatement statement;
		ResultSet rsSalida;
		try {
			cQuery = "SELECT valor FROM setupvariables WHERE UPPER(variable)=? AND UPPER(sistema)=? AND idempresa=?";
			statement = dbconn.prepareStatement(cQuery);
			statement.setString(1, variable.toUpperCase());
			statement.setString(2, sistema.toUpperCase());
			statement.setBigDecimal(3, idempresa);
			rsSalida = statement.executeQuery();
			if (rsSalida.next()) {
				valor = rsSalida.getString("valor");
			}
		} catch (Exception e) {
			// TODO: handle exception
			log
					.error("getSetupVariable(String variable, String sistema): "
							+ e);
		}
		return valor;
	}

	/**
	 * Metodos para la entidad: contableInfiPlan Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Thu Aug 17 11:09:27 GMT-03:00 2006
	 * 
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getCuentasInfiPlanAll(long limit, long offset, int ejercicio,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT idcuenta,cuenta,inputable,nivel,ajustable, "
				+ "       resultado,cent_cost1,cent_cost1,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM contableinfiplan  WHERE ejercicio = " + ejercicio
				+ " AND idempresa=" + idempresa.toString()
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getCuentasInfiPlanOcu(long limit, long offset,
			String ocurrencia, int ejercicio, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT idcuenta,cuenta,inputable,nivel,ajustable, "
				+ "       resultado,cent_cost1,cent_cost2,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM contableinfiplan WHERE (idcuenta::varchar LIKE  '"
				+ ocurrencia.trim() + "%' OR UPPER(cuenta) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%') AND idempresa="
				+ idempresa.toString() + " AND ejercicio = " + ejercicio
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getCuentasInfiPlanPK(BigDecimal idcuenta, BigDecimal idempresa,
			int idejercicio) throws EJBException {

		String cQuery = ""
				+ "SELECT idcuenta, cuenta, inputable, nivel, ajustable, resultado, cent_cost1,"
				+ "       cent_cost2, usuarioalt, usuarioact, fechaalt, fechaact "
				+ "  FROM contableinfiplan WHERE idcuenta = "
				+ idcuenta.toString() + "   AND idempresa = "
				+ idempresa.toString() + "   AND ejercicio = " + idejercicio;

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: stockStock Copyrigth(r) sysWarp S.R.L. Fecha de
	 * creacion: Thu Aug 24 13:28:06 GMT-03:00 2006
	 * 
	 */

	// por primary key (primer campo por defecto)
	public List getStockArticuloPK(String codigo_st, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT codigo_st,alias_st,descrip_st,descri2_st,cost_pp_st, "
				+ "       precipp_st,cost_uc_st,ultcomp_st,cost_re_st,reposic_st,nom_com_st, "
				+ "       grupo_st,cantmin_st,unimed_st,bonific_st,impint_st,impcant_st,"
				+ "       cuencom_st,cuenven_st,cuenve2_st,cuencos_st,cuenaju_st,inventa_st,"
				+ "       proveed_st,provart_st,id_indi_st,despa_st,marca_st,cafecga_st,"
				+ "       unialt1_st,unialt2_st,unialt3_st,unialt4_st,factor1_st,factor2_st,"
				+ "       factor3_st,factor4_st,tipoiva_st,venta_st,compra_st,esquema_st,"
				+ "       usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM STOCKSTOCK " + "WHERE codigo_st= '" + codigo_st
				+ "' AND idempresa = " + idempresa.toString();
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// Verifica la existencia de un determinado documento para un proveedor
	// particular en una sucursal determinada.

	public boolean getExisteDocumento(BigDecimal idproveedor,
			BigDecimal sucursal, BigDecimal comprob, BigDecimal tipomov,
			BigDecimal idempresa) throws EJBException {
		PreparedStatement statement;
		String qExiste = "SELECT count(1) ";
		qExiste += "        FROM proveedomovprov ";
		qExiste += "       WHERE idproveedor = ? AND  sucursal = ? AND comprob = ? AND tipomov = ?  AND idempresa = ?";
		ResultSet rsExiste;
		boolean existe = true;
		try {
			statement = dbconn.prepareStatement(qExiste);
			statement.setBigDecimal(1, idproveedor);
			statement.setBigDecimal(2, sucursal);
			statement.setBigDecimal(3, comprob);
			statement.setBigDecimal(4, tipomov);
			statement.setBigDecimal(5, idempresa);
			rsExiste = statement.executeQuery();
			if (rsExiste.next()) {
				if (rsExiste.getString(1).equals("0"))
					existe = false;
			}

		} catch (Exception e) {
			log
					.error("getExisteDocumento(BigDecimal idproveedor, BigDecimal sucursal, BigDecimal comprob, BigDecimal tipomov, BigDecimal idempresa): "
							+ e);
		}

		return existe;
	}

	// Verifica la existencia de un determinado documento para un proveedor
	// particular en una sucursal determinada.

	public boolean getExisteDocumentoUpd(BigDecimal nrointerno,
			BigDecimal idproveedor, BigDecimal sucursal, BigDecimal comprob,
			BigDecimal tipomov, BigDecimal idempresa) throws EJBException {
		PreparedStatement statement;
		String qExiste = "SELECT count(1) ";
		qExiste += "        FROM proveedomovprov ";
		qExiste += "       WHERE nrointerno <> ? AND idproveedor = ? AND  sucursal = ?";
		qExiste += "         AND comprob = ? AND tipomov = ?  AND idempresa = ?";
		ResultSet rsExiste;
		boolean existe = true;
		try {

			statement = dbconn.prepareStatement(qExiste);
			statement.setBigDecimal(1, nrointerno);
			statement.setBigDecimal(2, idproveedor);
			statement.setBigDecimal(3, sucursal);
			statement.setBigDecimal(4, comprob);
			statement.setBigDecimal(5, tipomov);
			statement.setBigDecimal(6, idempresa);
			rsExiste = statement.executeQuery();
			if (rsExiste.next()) {
				if (rsExiste.getString(1).equals("0"))
					existe = false;
			}

		} catch (Exception e) {
			log
					.error("getExisteDocumentoUpd(BigDecimal nrointerno, BigDecimal idproveedor, BigDecimal sucursal, BigDecimal comprob, BigDecimal tipomov, BigDecimal idempresa): "
							+ e);
		}

		return existe;
	}

	/**
	 * Metodo para la entidad: proveedomovprov - proveedocontprov - stockstock -
	 * stockhis - stockstockbis - stockmovstock; Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Thu Aug 23 16:55:00 GMT-03:00 2006
	 * 
	 */
	public String capturaComprobantesProvCreate(BigDecimal idproveedor,
			Timestamp fechamov, BigDecimal sucursal, BigDecimal comprob,
			BigDecimal tipomov, String tipomovs, BigDecimal importe,
			BigDecimal saldo, BigDecimal idcondicionpago, Timestamp fecha_subd,
			BigDecimal retoque, java.sql.Date fechavto, int ejercicioactivo,
			String usuarioalt, Hashtable htAsiento, Hashtable htArticulos,
			BigDecimal idempresa, String obscontable) throws EJBException,
			SQLException {

		String salida = "OK";
		String qDML = "";
		int flagFilasAfectadas = 0;
		PreparedStatement statement;
		BigDecimal nrointerno = BigDecimal.valueOf(-1);
		BigDecimal nrointerno_ms = BigDecimal.valueOf(-1);
		Enumeration en;
		String tipomov_ms = tipomov.intValue() == 3 ? "S" : "E";
		java.util.Date date = new java.util.Date();
		Timestamp fechaact = new Timestamp(date.getTime());
		BigDecimal cantArtDep;
		BigDecimal cantArtMov;

		try {
			log.info("INICIA ACTUALIZACION STOCK. ");
			// TODO: REALIZAR TODAS LAS VALIDACIONES
			// 1.HTASIENTO NO NULO.

			dbconn.setAutoCommit(false);
			if (!getExisteDocumento(idproveedor, sucursal, comprob, tipomov,
					idempresa)) {

				tipomovs = getTipoComprobante(tipomov, tipomovs);

				// agregado para asegurarme que se cargue el numero de
				// comprobante en la observacion

				obscontable = tipomovs + ": Suc: " + sucursal + " Comprob: "
						+ comprob + " Det: " + obscontable;

				/*
				 * switch (tipomov.intValue()) { case 1: tipomovs = "FA" +
				 * tipomovs; break; case 2: tipomovs = "ND" + tipomovs; break;
				 * case 3: tipomovs = "NC" + tipomovs; break; case 4: tipomovs =
				 * "PA" + tipomovs;// ?? break; default: break; }
				 */

				nrointerno = GeneralBean.getContador(new BigDecimal(9),
						idempresa, dbconn);

				if (nrointerno.longValue() == -1)
					throw new SQLException(
							"Imposible recuperar nrointerno movimiento proveedor: ("
									+ nrointerno + ")");

				qDML = "INSERT INTO proveedomovprov (nrointerno,idproveedor,fechamov,sucursal,comprob,tipomov,tipomovs,";
				qDML += " importe,saldo,idcondicionpago,fecha_subd,retoque,fechavto,usuarioalt, idempresa, obscontable )";
				qDML += " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				statement = dbconn.prepareStatement(qDML);
				statement.setBigDecimal(1, nrointerno);
				statement.setBigDecimal(2, idproveedor);
				statement.setTimestamp(3, fechamov);
				statement.setBigDecimal(4, sucursal);
				statement.setBigDecimal(5, comprob);
				statement.setBigDecimal(6, tipomov);
				statement.setString(7, tipomovs);
				statement.setBigDecimal(8, importe);
				statement.setBigDecimal(9, importe);
				statement.setBigDecimal(10, idcondicionpago);
				statement.setTimestamp(11, fecha_subd);
				statement.setBigDecimal(12, retoque);
				statement.setDate(13, fechavto);
				statement.setString(14, usuarioalt);
				statement.setBigDecimal(15, idempresa);
				statement.setString(16, obscontable);

				flagFilasAfectadas = statement.executeUpdate();

				if (flagFilasAfectadas != 0) {
					// nrointerno = getValorSequencia("seq_proveedomovprov");
					qDML = "INSERT INTO proveedocontprov (cuenta_con,compr_con,import_con,nroiva_con,";
					qDML += " centcost,centcost2,detalle,usuarioalt,idempresa, obscontable )";
					qDML += " VALUES (?,?,?,?,?,?,?,?,?, ?)";

					en = htAsiento.keys();
					statement = dbconn.prepareStatement(qDML);
					while (en.hasMoreElements()) {

						statement.clearParameters();
						String key = (String) en.nextElement();
						String[] datosAsiento = (String[]) htAsiento.get(key);
						BigDecimal import_con = new BigDecimal(datosAsiento[2]
								.trim());

						if (import_con.doubleValue() <= 0)
							continue;

						// for (int j = 0; j < datosAsiento.length; j++)
						// log.error("datosAsiento[" + j + "]"
						// + datosAsiento[j]);

						if (!GeneralBean.isExisteCtaImputable(new BigDecimal(
								datosAsiento[0].trim()), ejercicioactivo,
								idempresa, dbconn)) {

							salida = "No existe cuenta imputable "
									+ datosAsiento[0]
									+ ", en ejercicio activo "
									+ ejercicioactivo;
							break;
						}

						statement.setBigDecimal(1, new BigDecimal(
								datosAsiento[0].trim()));
						statement.setBigDecimal(2, nrointerno);
						statement.setBigDecimal(3, import_con);
						statement.setString(4, datosAsiento[3]);
						statement.setBigDecimal(5, null);
						statement.setBigDecimal(6, null);
						statement.setString(7, null);
						statement.setString(8, usuarioalt);
						statement.setBigDecimal(9, idempresa);
						statement.setString(10, obscontable);
						flagFilasAfectadas = statement.executeUpdate();

						if (flagFilasAfectadas == 0) {
							salida = "E-1.0: Transacci�n Abortada Generando Asiento(MOVCONTPROV).";
							log.warn(salida);
							break;
						}

					}

					// ACTUALIZA STOCK
					if (htArticulos != null && !htArticulos.isEmpty()
							&& salida.equalsIgnoreCase("OK")) {
						//
						en = htArticulos.keys();
						nrointerno_ms = GeneralBean.getContador(new BigDecimal(
								5), idempresa, dbconn);

						if (nrointerno_ms.longValue() == -1)
							throw new SQLException(
									"Imposible recuperar nrointerno_ms movimiento stock: ("
											+ nrointerno_ms + ")");

						while (en.hasMoreElements()) {
							qDML = "INSERT INTO stockmovstock (nrointerno_ms, sistema_ms,tipomov_ms,comprob_ms,fecha_ms,articu_ms,canti_ms,";
							qDML += " moneda_ms,cambio_ms,venta_ms,costo_ms,tipoaux_ms,destino_ms,";
							qDML += " comis_ms,remito_ms,impint_ms,impifl_ms,impica_ms,prelis_ms,";
							qDML += " unidad_ms,merma_ms,saldo_ms,medida_ms,usuarioalt, idempresa)";
							qDML += " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
							statement = dbconn.prepareStatement(qDML);
							statement.clearParameters();
							String key = (String) en.nextElement();
							String[] datosArticulo = (String[]) htArticulos
									.get(key);
							// RECUPERAR DATOS ARTICULO
							List listaArticulo = getStockArticuloPK(
									datosArticulo[0], idempresa);
							if (!listaArticulo.isEmpty()) {
								// String[] datosArticuloStock = (String[])
								// listaArticulo.get(0);
								// TODO: Parametrizar, por ahora son solo
								// proveedores.
								statement.setBigDecimal(1, nrointerno_ms);
								statement.setString(2, "P");
								// TODO: Discriminar si entra o sale.
								statement.setString(3, tipomov_ms);
								statement.setBigDecimal(4, nrointerno);
								statement.setTimestamp(5, fechamov);
								statement.setString(6, datosArticulo[0]);
								statement.setBigDecimal(7, new BigDecimal(
										datosArticulo[10]));
								// HARCODE POR FUENTE
								statement.setInt(8, 1);
								statement.setInt(9, 1);
								statement
										.setBigDecimal(10, new BigDecimal("0"));
								statement.setBigDecimal(11, new BigDecimal(
										datosArticulo[5]));
								statement.setString(12, null);
								statement.setString(13, null);
								statement
										.setBigDecimal(14, new BigDecimal("0"));
								statement
										.setBigDecimal(15, new BigDecimal("0"));
								statement
										.setBigDecimal(16, new BigDecimal("0"));
								statement
										.setBigDecimal(17, new BigDecimal("0"));
								statement
										.setBigDecimal(18, new BigDecimal("0"));
								statement
										.setBigDecimal(19, new BigDecimal("0"));
								statement
										.setBigDecimal(20, new BigDecimal("0"));
								statement
										.setBigDecimal(21, new BigDecimal("0"));
								statement
										.setBigDecimal(22, new BigDecimal("0"));
								statement
										.setBigDecimal(23, new BigDecimal("0"));
								statement.setString(24, usuarioalt);
								statement.setBigDecimal(25, idempresa);
								//
								flagFilasAfectadas = statement.executeUpdate();
								if (flagFilasAfectadas != 0) {
									// nrointerno_ms =
									// getValorSequencia("seq_stockmovstock");
									// INSERTAR - ACTUALIZAR: STOCKBIS -
									if (!GeneralBean.getExisteArticuloDeposito(
											datosArticulo[0], new BigDecimal(
													datosArticulo[9]),
											idempresa, dbconn)) {

										if (tipomov_ms.equalsIgnoreCase("E")) {

											qDML = "INSERT INTO stockstockbis (articu_sb,deposi_sb,canti_sb,serie_sb,despa_sb,pedid_sb,usuarioalt,idempresa)";
											qDML += " VALUES (?,?,?,?,?,?,?,?)";
											statement = dbconn
													.prepareStatement(qDML);
											statement.clearParameters();
											statement.setString(1,
													datosArticulo[0]);
											statement.setBigDecimal(2,
													new BigDecimal(
															datosArticulo[9]));
											statement.setBigDecimal(3,
													new BigDecimal(
															datosArticulo[10]));
											statement.setString(4, null);
											statement.setString(5, null);
											statement.setBigDecimal(6,
													new BigDecimal("0"));
											statement.setString(7, usuarioalt);
											statement.setBigDecimal(8,
													idempresa);

										} else {
											salida = "E-2.1: Articulo "
													+ datosArticulo[0]
													+ " Inexistente En Dep�sito "
													+ datosArticulo[9];
											log.warn(salida);
											break;
										}

									} else {
										log.info("EXISTE STOCKBIS");
										cantArtMov = new BigDecimal(
												datosArticulo[10]);
										if (tipomov_ms.equalsIgnoreCase("S")) {
											cantArtDep = GeneralBean
													.getCantidadArticuloDeposito(
															datosArticulo[0],
															new BigDecimal(
																	datosArticulo[9]),
															idempresa, dbconn);

											if (cantArtDep
													.compareTo(cantArtMov) > 0) {
												cantArtMov = cantArtMov
														.negate();
											} else {
												salida = "E-2.2: Cantidad Insuficiente Articulo "
														+ datosArticulo[0]
														+ " En Dep�sito "
														+ datosArticulo[9];
												log.warn(salida);
												break;
											}
										}

										qDML = "UPDATE stockstockbis ";
										qDML += "   SET canti_sb=( canti_sb +( ? )),serie_sb=?,despa_sb=?,pedid_sb=pedid_sb+?,usuarioact=?,fechaact=?";
										qDML += " WHERE articu_sb=? AND deposi_sb=? AND idempresa=?";
										statement = dbconn
												.prepareStatement(qDML);
										statement.clearParameters();

										statement.clearParameters();
										statement.setBigDecimal(1, cantArtMov);
										statement.setString(2, null);
										statement.setString(3, null);
										statement.setBigDecimal(4,
												new BigDecimal("0"));
										statement.setString(5, usuarioalt);
										statement.setTimestamp(6, fechaact);
										statement
												.setString(7, datosArticulo[0]);
										statement
												.setBigDecimal(
														8,
														new BigDecimal(
																datosArticulo[9]));
										statement.setBigDecimal(9, idempresa);
									}

									flagFilasAfectadas = statement
											.executeUpdate();

									// Inicia ACTUALIZAR PRECIOS
									if (flagFilasAfectadas != 0) {
										qDML = "UPDATE stockstock ";
										qDML += "   SET cost_uc_st=? ,cost_re_st=?,usuarioact=?,fechaact=?";
										qDML += " WHERE codigo_st=?  AND idempresa=?";
										statement = dbconn
												.prepareStatement(qDML);
										statement.clearParameters();

										statement.clearParameters();
										statement
												.setBigDecimal(
														1,
														new BigDecimal(
																datosArticulo[5]));
										statement
												.setBigDecimal(
														2,
														new BigDecimal(
																datosArticulo[5]));
										statement.setString(3, usuarioalt);
										statement.setTimestamp(4, fechaact);
										statement
												.setString(5, datosArticulo[0]);
										statement.setBigDecimal(6, idempresa);

										flagFilasAfectadas = statement
												.executeUpdate();

										if (flagFilasAfectadas != 0) {
											// INSERTAR STOCKHIS
											qDML = "INSERT INTO stockhis (nromov_sh,articu_sh,deposi_sh,serie_sh,despa_sh, ";
											qDML += " canti_sh,estamp1_sh,estamp2_sh,aduana_sh,usuarioalt,idempresa) ";
											qDML += " VALUES (?,?,?,?,?,?,?,?,?,?,?)";

											statement = dbconn
													.prepareStatement(qDML);
											statement.clearParameters();
											statement.setBigDecimal(1,
													nrointerno_ms);
											statement.setString(2,
													datosArticulo[0]);
											statement.setBigDecimal(3,
													new BigDecimal(
															datosArticulo[9]));
											statement.setString(4, null);
											statement.setString(5, null);
											statement.setBigDecimal(6,
													new BigDecimal(
															datosArticulo[10]));
											statement.setString(7, null);
											statement.setString(8, null);
											statement.setString(9, null);
											statement.setString(10, usuarioalt);
											statement.setBigDecimal(11,
													idempresa);

											flagFilasAfectadas = statement
													.executeUpdate();

											if (flagFilasAfectadas != 0) {
												log.info("GENERO MOV STOCKHIS");

											} else {
												salida = "E-2.3: Transaccion Abortada Actualizando Stock Art. "
														+ datosArticulo[0]
														+ " - Dep�sito "
														+ datosArticulo[9];
												log.warn(salida);
												break;
											}

										} else {
											salida = "E-2.4: Transaccion Abortada Actualizando Precio Art. "
													+ datosArticulo[0];
											log.warn(salida);
											break;

										}

									} else {
										salida = "E-2.5: Transaccion Abortada Actualizando Stock Art. "
												+ datosArticulo[0]
												+ " - Dep�sito "
												+ datosArticulo[9];
										log.warn(salida);
										break;

									}

								} else {
									salida = "E-2.0: Transaccion Abortada (STOCKMOVSTOCK) "
											+ datosArticulo[0]
											+ " - Dep�sito "
											+ datosArticulo[9];

									log.warn(salida);
									break;
								}

							} else {
								salida = "E-2.0.1: Transaccion Abortada (STOCKMOVSTOCK). Imposible Recuperar Datos Articulo "
										+ datosArticulo[0]
										+ " - Dep�sito "
										+ datosArticulo[9];

								log.warn(salida);
								break;
							}

						}
					}

				} else {
					// no inserta en proveedomovprov
					salida = "E-0.0.1: Transaccion Abortada (PROVEEDOMOVPROV). ";
					log.warn(salida);
				}
			} else {
				salida = "El Nro. de Documento ya fue ingresado para el proveedor. ";
				log.warn(salida);
			}
		} catch (Exception e) {
			// TODO: handle exception
			salida = "E-1000: Ocurri� Excepci�n Mientras Se Actualizaba Stock.";
			log.error("capturaComprobantesProvCreate(...): " + e);
		}
		if (salida.equalsIgnoreCase("OK")) {
			salida = "" + nrointerno;
			dbconn.commit();
		} else
			dbconn.rollback();
		dbconn.setAutoCommit(true);
		return salida;
	}

	/**
	 * Solo contabilidad proveed_art = 'C'
	 */

	public String capturaComprobantesProvContableCreate(BigDecimal idproveedor,
			Timestamp fechamov, BigDecimal sucursal, BigDecimal comprob,
			BigDecimal tipomov, String tipomovs, BigDecimal importe,
			BigDecimal saldo, BigDecimal idcondicionpago, Timestamp fecha_subd,
			BigDecimal retoque, java.sql.Date fechavto, int ejercicioactivo,
			String usuarioalt, String[] idConcepto, String[] idCuenta,
			String[] valor, String[] tipo, BigDecimal idempresa,
			String obscontable) throws EJBException, SQLException {

		String salida = "OK";
		String qDML = "";
		int flagFilasAfectadas = 0;
		PreparedStatement statement;
		Statement st;
		BigDecimal nrointerno = BigDecimal.valueOf(-1);
		Enumeration en;
		try {
			log.info("ENTRANDO EN LA GRABACION CORRECTA DEL COMPROBANTE...");
			dbconn.setAutoCommit(false);
			if (!getExisteDocumento(idproveedor, sucursal, comprob, tipomov,
					idempresa)) {

				tipomovs = getTipoComprobante(tipomov, tipomovs);

				obscontable = tipomovs + ": Suc: " + sucursal + " Comprob: "
						+ comprob + " Det: " + obscontable;

				nrointerno = GeneralBean.getContador(new BigDecimal(9),
						idempresa, dbconn);

				if (nrointerno.longValue() == -1)
					throw new SQLException(
							"Imposible recuperar nrointerno movimiento proveedor: ("
									+ nrointerno + ")");
				// dejar tal cual
				qDML = "INSERT INTO proveedomovprov (nrointerno,idproveedor,fechamov,sucursal,comprob,tipomov,tipomovs,";
				qDML += " importe,saldo,idcondicionpago,fecha_subd,retoque,fechavto,usuarioalt, idempresa, obscontable )";
				qDML += " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				statement = dbconn.prepareStatement(qDML);
				statement.setBigDecimal(1, nrointerno);
				statement.setBigDecimal(2, idproveedor);
				statement.setTimestamp(3, fechamov);
				statement.setBigDecimal(4, sucursal);
				statement.setBigDecimal(5, comprob);
				statement.setBigDecimal(6, tipomov);
				statement.setString(7, tipomovs);
				statement.setBigDecimal(8, importe);
				statement.setBigDecimal(9, importe);
				statement.setBigDecimal(10, idcondicionpago);
				statement.setTimestamp(11, fecha_subd);
				statement.setBigDecimal(12, retoque);
				statement.setDate(13, fechavto);
				statement.setString(14, usuarioalt);
				statement.setBigDecimal(15, idempresa);
				statement.setString(16, obscontable);

				flagFilasAfectadas = statement.executeUpdate();

				if (flagFilasAfectadas != 0) {
					// nrointerno = getValorSequencia("seq_proveedomovprov");
					qDML = "INSERT INTO proveedocontprov (cuenta_con,compr_con,import_con,nroiva_con,";
					qDML += " centcost,centcost2,detalle,usuarioalt,idempresa, obscontable )";
					qDML += " VALUES (?,?,?,?,?,?,?,?,?, ?)";

					statement = dbconn.prepareStatement(qDML);

					// MODIFICAR:bucle por los string[] tengo que cambiar los ht
					// por los string que vienen por parametro y listo.
					//

					for (int i = 0; i < idConcepto.length; i++) {
						BigDecimal import_con = new BigDecimal(valor[i].trim()); // la
						statement.setBigDecimal(1, new BigDecimal(idCuenta[i]
								.trim()));
						statement.setBigDecimal(2, nrointerno);
						statement.setBigDecimal(3, import_con);
						statement.setString(4, tipo[i]);
						statement.setBigDecimal(5, null);
						statement.setBigDecimal(6, null);
						statement.setString(7, null);
						statement.setString(8, usuarioalt);
						statement.setBigDecimal(9, idempresa);
						statement.setString(10, obscontable);
						flagFilasAfectadas = statement.executeUpdate();
						if (flagFilasAfectadas == 0) {
							salida = "E-1.0: Transacci�n Abortada Generando Asiento(MOVCONTPROV).";
							log.warn(salida);
							break;
						}
					}
					// (ctapasivo)
					st = dbconn.createStatement();
					ResultSet rsProveedPK = st
							.executeQuery("Select * from proveedoproveed where idempresa="
									+ idempresa
									+ " and idproveedor = "
									+ idproveedor);
					BigDecimal cuentaTotal = new BigDecimal(0);
					if (rsProveedPK.next()) {
						cuentaTotal = rsProveedPK.getBigDecimal("ctapasivo");
					}
					statement.setBigDecimal(1, cuentaTotal);
					statement.setBigDecimal(2, nrointerno);
					statement.setBigDecimal(3, importe);
					statement.setString(4, "T"); // es el total siempre
					statement.setBigDecimal(5, null);
					statement.setBigDecimal(6, null);
					statement.setString(7, null);
					statement.setString(8, usuarioalt);
					statement.setBigDecimal(9, idempresa);
					statement.setString(10, obscontable);
					flagFilasAfectadas = statement.executeUpdate();

				} else {
					// no inserta en proveedomovprov
					salida = "E-0.0.1: Transaccion Abortada (PROVEEDOMOVPROV). ";
					log.warn(salida);
				}
			} else {
				salida = "El Nro. de Documento ya fue ingresado para el proveedor. ";
				log.warn(salida);
			}
		} catch (Exception e) {
			// TODO: handle exception
			salida = "E-1000: Ocurri� Excepci�n Mientras Se Actualizaba Stock.";
			log.error("capturaComprobantesProvContableCreate(...): " + e);
		}
		if (salida.equalsIgnoreCase("OK")) {
			salida = "" + nrointerno;
			dbconn.commit();
		} else
			dbconn.rollback();
		dbconn.setAutoCommit(true);
		return salida;
	}

	/**
	 * 
	 */

	// Maximo Numero de Proveedor
	public BigDecimal getMaximoProveedor(BigDecimal idempresa)
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
		BigDecimal idproveedor = BigDecimal.valueOf(0);
		ResultSet rsSalida = null;
		String cQuery = "SELECT MAX(idproveedor) + 1 AS idproveedor FROM proveedoproveed WHERE idempresa="
				+ idempresa.toString();
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);

			if (rsSalida.next()) {
				idproveedor = rsSalida.getBigDecimal("idproveedor");
			} else {
				log
						.warn("getMaximoProveedor(BigDecimal idempresa)- Error al recuperar idproveedor: ");
			}
		} catch (SQLException sqlException) {
			log.error("getMaximoProveedor(BigDecimal idempresa)- Error SQL: "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("getMaximoProveedor(BigDecimal idempresa)- Salida por exception: "
							+ ex);
		}
		return idproveedor;
	}

	// proveedores oc Estados
	public List getProveedo_oc_estadosAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {

		String cQuery = ""
				+ "SELECT idestadooc,estadooc,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM PROVEEDO_OC_ESTADOS WHERE idempresa="
				+ idempresa.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getProveedo_oc_estadosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT idestadooc,estadooc,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM PROVEEDO_OC_ESTADOS " + " where idempresa= "
				+ idempresa.toString() + " and (idestadooc::VARCHAR LIKE '%"
				+ ocurrencia + "%' OR " + " UPPER(estadooc) LIKE '%"
				+ ocurrencia.toUpperCase() + "%') " + " ORDER BY 2  LIMIT "
				+ limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getProveedo_oc_estadosPK(BigDecimal idestadooc,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT idestadooc,estadooc,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM PROVEEDO_OC_ESTADOS WHERE idestadooc="
				+ idestadooc.toString() + " AND idempresa="
				+ idempresa.toString();
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String proveedo_oc_estadosDelete(BigDecimal idestadooc,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM PROVEEDO_OC_ESTADOS WHERE idestadooc="
				+ idestadooc.toString() + " AND idempresa="
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM PROVEEDO_OC_ESTADOS WHERE idestadooc="
						+ idestadooc.toString() + " AND idempresa="
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : proveedo_oc_estadosDelete( BigDecimal idestadooc, BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: proveedo_oc_estadosDelete( BigDecimal idestadooc, BigDecimal idempresa )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String proveedo_oc_estadosCreate(String estadooc, String usuarioalt,
			BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (estadooc == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: estadooc ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (estadooc.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: estadooc ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO PROVEEDO_OC_ESTADOS(estadooc, usuarioalt, idempresa ) VALUES (?, ?, ? )";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, estadooc);
				insert.setString(2, usuarioalt);
				insert.setBigDecimal(3, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String proveedo_oc_estadosCreate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String proveedo_oc_estadosCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String proveedo_oc_estadosCreateOrUpdate(BigDecimal idestadooc,
			String estadooc, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idestadooc == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idestadooc ";
		if (estadooc == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: estadooc ";

		// 2. sin nada desde la pagina
		if (estadooc.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: estadooc ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM proveedo_oc_estados WHERE idestadooc = "
					+ idestadooc.toString()
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
					sql = "UPDATE PROVEEDO_OC_ESTADOS SET estadooc=?, usuarioact=?, fechaact=? WHERE idestadooc=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, estadooc);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idestadooc);
					insert.setBigDecimal(5, idempresa);
				} else {
					String ins = "INSERT INTO PROVEEDO_OC_ESTADOS(estadooc, usuarioalt, idempresa ) VALUES (?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, estadooc);
					insert.setString(2, usuarioalt);
					insert.setBigDecimal(3, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String proveedo_oc_estadosCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String proveedo_oc_estadosCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String proveedo_oc_estadosUpdate(BigDecimal idestadooc,
			String estadooc, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idestadooc == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idestadooc ";
		if (estadooc == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: estadooc ";

		// 2. sin nada desde la pagina
		if (estadooc.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: estadooc ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM proveedo_oc_estados WHERE idestadooc = "
					+ idestadooc.toString()
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
					sql = "UPDATE PROVEEDO_OC_ESTADOS SET estadooc=?, usuarioact=?, fechaact=? WHERE idestadooc=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, estadooc);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idestadooc);
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
					.error("Error SQL public String proveedo_oc_estadosUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String proveedo_oc_estadosUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * Metodos para la entidad: proveedo_Oc_Cabe Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Wed Mar 28 09:44:55 CEST 2007
	 */

	public String proveedoOcCreate(BigDecimal idestadooc,
			BigDecimal idproveedor, Timestamp fechaoc,
			BigDecimal idcondicionpago, BigDecimal comision,
			String observaciones, BigDecimal recargo1, BigDecimal recargo2,
			BigDecimal recargo3, BigDecimal recargo4, BigDecimal bonific1,
			BigDecimal bonific2, BigDecimal bonific3, BigDecimal idmoneda,
			BigDecimal cotizacion, BigDecimal idtipoiva, BigDecimal totaliva,
			BigDecimal idgrupooc, BigDecimal idempresa, Hashtable htArticulos,
			String usuarioalt, BigDecimal codigo_dt,
			java.sql.Date fecha_entrega_prevista) throws EJBException,
			SQLException {
		String salida = "OK";
		BigDecimal id_oc_cabe = null;
		int renglon = 0;
		Enumeration en;

		// validaciones de datos:
		// 1. nulidad de campos
		if (idestadooc == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idestadooc ";
		if (idproveedor == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idproveedor ";
		if (fechaoc == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechaoc ";
		if (idcondicionpago == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idcondicionpago ";
		if (idmoneda == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idmoneda ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones

		try {
			dbconn.setAutoCommit(false);
			if (salida.equalsIgnoreCase("OK")) {
				String ins = ""
						+ " INSERT INTO PROVEEDO_OC_CABE "
						+ "  (idestadooc, idproveedor, fechaoc, idcondicionpago, comision, observaciones, "
						+ "   recargo1, recargo2, recargo3, recargo4, bonific1, bonific2, bonific3, idmoneda, "
						+ "   cotizacion, idtipoiva, totaliva, idgrupooc,idempresa, usuarioalt, codigo_dt, fecha_entrega_prevista  ) "
						+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, idestadooc);
				insert.setBigDecimal(2, idproveedor);
				insert.setTimestamp(3, fechaoc);
				insert.setBigDecimal(4, idcondicionpago);
				insert.setBigDecimal(5, comision);
				insert.setString(6, observaciones);
				insert.setBigDecimal(7, recargo1);
				insert.setBigDecimal(8, recargo2);
				insert.setBigDecimal(9, recargo3);
				insert.setBigDecimal(10, recargo4);
				insert.setBigDecimal(11, bonific1);
				insert.setBigDecimal(12, bonific2);
				insert.setBigDecimal(13, bonific3);
				insert.setBigDecimal(14, idmoneda);
				insert.setBigDecimal(15, cotizacion);
				insert.setBigDecimal(16, idtipoiva);
				insert.setBigDecimal(17, totaliva);
				insert.setBigDecimal(18, idgrupooc);
				insert.setBigDecimal(19, idempresa);
				insert.setString(20, usuarioalt);
				insert.setBigDecimal(21, codigo_dt);
				insert.setDate(22, fecha_entrega_prevista);
				int n = insert.executeUpdate();
				if (n != 1)
					salida = "Imposible generar orden de compra.";
				else {
					id_oc_cabe = GeneralBean.getValorSequencia(
							"seq_proveedo_oc_cabe", dbconn);
					if (htArticulos != null && !htArticulos.isEmpty()) {
						en = htArticulos.keys();
						while (en.hasMoreElements()) {
							String key = (String) en.nextElement();
							String[] datosArticulo = (String[]) htArticulos
									.get(key);
							String codigo_st = datosArticulo[0];
							BigDecimal cantidad = new BigDecimal(
									datosArticulo[10]);
							// EJV 20080205
							// BigDecimal precio = new
							// BigDecimal(datosArticulo[4]);
							BigDecimal precio = new BigDecimal(datosArticulo[5]);
							BigDecimal importe = new BigDecimal(
									datosArticulo[11]);
							BigDecimal codigo_md = new BigDecimal(
									datosArticulo[13]);
							salida = proveedo_Oc_DetaCreate(id_oc_cabe,
									codigo_st, fechaoc, new BigDecimal(
											++renglon), precio, new BigDecimal(
											0), cantidad, new BigDecimal(0),
									codigo_md, new BigDecimal(0), "N",
									idempresa, usuarioalt);
							if (!salida.equalsIgnoreCase("OK"))
								break;
						}

					}

				}
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String proveedoOcCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error excepcion public String proveedoOcCreate(.....)"
					+ ex);
		}

		if (!salida.equalsIgnoreCase("OK")) {
			dbconn.rollback();
		} else {
			salida = id_oc_cabe.toString();
		}
		dbconn.setAutoCommit(true);
		return salida;
	}

	public List getProveedo_Oc_CabePK(BigDecimal id_oc_cabe,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT occ.id_oc_cabe, occ.idestadooc, oce.estadooc, occ.idproveedor, occ.fechaoc, occ.idcondicionpago, "
				+ "       cc.condicion, occ.comision, occ.observaciones, occ.recargo1, occ.recargo2, occ.recargo3, occ.recargo4, "
				+ "       occ.bonific1,occ.bonific2,occ.bonific3,occ.idmoneda, gm.moneda, occ.cotizacion,"
				+ "       occ.idtipoiva, cti.tipoiva, occ.totaliva,  "
				+ "       occ.usuarioalt, occ.usuarioact, occ.fechaalt, occ.fechaact "
				+ "  FROM proveedo_oc_cabe occ "
				+ "       INNER JOIN globalmonedas gm ON occ.idmoneda = gm.idmoneda "
				+ "       INNER JOIN clientestablaiva  cti ON occ.idtipoiva = cti.idtipoiva AND occ.idempresa = cti.idempresa "
				+ "       INNER JOIN clientescondicio  cc ON occ.idcondicionpago = cc.idcondicion AND occ.idempresa = cc.idempresa "
				+ "       INNER JOIN proveedo_oc_estados  oce ON occ.idestadooc = oce.idestadooc AND occ.idempresa = oce.idempresa "
				+ " WHERE occ.id_oc_cabe=" + id_oc_cabe.toString()
				+ "   AND occ.idempresa=" + idempresa.toString();
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: proveedo_Oc_Deta Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Wed Mar 28 16:27:50 CEST 2007
	 */

	public String proveedo_Oc_DetaCreate(BigDecimal id_oc_cabe,
			String codigo_st, Timestamp fecha, BigDecimal renglon,
			BigDecimal precio, BigDecimal saldo, BigDecimal cantidad,
			BigDecimal bonific, BigDecimal codigo_md, BigDecimal cantuni,
			String entrega, BigDecimal idempresa, String usuarioalt)
			throws EJBException {
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (id_oc_cabe == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: id_oc_cabe ";
		if (codigo_st == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_st ";
		if (fecha == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fecha ";
		if (renglon == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: renglon ";
		if (precio == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: precio ";
		if (saldo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: saldo ";
		if (cantidad == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cantidad ";
		if (bonific == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: bonific ";
		if (codigo_md == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_md ";
		if (cantuni == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cantuni ";
		if (entrega == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: entrega ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (codigo_st.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: codigo_st ";
		if (entrega.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: entrega ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones

		try {
			if (salida.equalsIgnoreCase("OK")) {
				String ins = "INSERT INTO PROVEEDO_OC_DETA(id_oc_cabe, codigo_st, fecha, renglon, precio, saldo, cantidad, bonific, codigo_md, cantuni, entrega,idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, id_oc_cabe);
				insert.setString(2, codigo_st);
				insert.setTimestamp(3, fecha);
				insert.setBigDecimal(4, renglon);
				insert.setDouble(5, precio.doubleValue());
				insert.setDouble(6, saldo.doubleValue());
				insert.setDouble(7, cantidad.doubleValue());
				insert.setDouble(8, bonific.doubleValue());
				insert.setBigDecimal(9, codigo_md);
				insert.setDouble(10, cantuni.doubleValue());
				insert.setString(11, entrega);
				insert.setBigDecimal(12, idempresa);
				insert.setString(13, usuarioalt);
				int n = insert.executeUpdate();
				if (n != 1)
					salida = "Imposible generar detalle de orden de compra.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String proveedo_Oc_DetaCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String proveedo_Oc_DetaCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// por primary key (primer campo por defecto)

	public List getProveedo_Oc_DetaOc(BigDecimal id_oc_cebe,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT ocd.id_oc_deta, ocd.id_oc_cabe, ocd.codigo_st, st.descrip_st, st.proveed_st, "
				+ "       st.provart_st, ocd.fecha, ocd.renglon, ocd.precio, ocd.saldo,ocd.cantidad, "
				+ "       ocd.bonific, sm.descrip_md, ocd.codigo_md, ocd.cantuni, ocd.entrega, ((ocd.precio * ocd.cantidad)::numeric(18,2)) as totalproducto,  "
				+ "       ocd.usuarioalt, ocd.usuarioact, ocd.fechaalt, ocd.fechaact "
				+ "  FROM proveedo_oc_deta ocd "
				+ "       INNER JOIN stockstock st ON ocd.codigo_st = st.codigo_st AND ocd.idempresa = st.idempresa  "
				+ "       INNER JOIN stockmedidas sm ON ocd.codigo_md = sm.codigo_md  AND ocd.idempresa = sm.idempresa   "
				+ " WHERE ocd.id_oc_cabe=" + id_oc_cebe.toString()
				+ "   AND ocd.idempresa=" + idempresa.toString()
				+ " ORDER BY ocd.renglon";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: proveedo_Oc_Grupos_Cotizaciones Copyrigth(r)
	 * sysWarp S.R.L. Fecha de creacion: Thu Mar 29 15:29:34 ART 2007
	 */
	// para todo (ordena por el segundo campo por defecto)
	public List getProveedo_Oc_Grupos_CotizacionesActivasAll(long limit,
			long offset, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT idgrupooc,grupooc,fechadesde,fechahasta,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM PROVEEDO_OC_GRUPOS_COTIZACIONES "
				+ " WHERE current_date BETWEEN fechadesde AND fechahasta  AND idempresa="
				+ idempresa.toString() + "ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getProveedo_Oc_Grupos_CotizacionesActivasOcu(long limit,
			long offset, String ocurrencia, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT  idgrupooc,grupooc,fechadesde,fechahasta,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM PROVEEDO_OC_GRUPOS_COTIZACIONES "
				+ " WHERE (UPPER(GRUPOOC) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%') AND current_date BETWEEN fechadesde AND fechahasta AND idempresa="
				+ idempresa.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// proveedores grupos cotizaciones
	public List getProveedo_oc_grupos_cotizacionesAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idgrupooc,grupooc,fechadesde,fechahasta,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM PROVEEDO_OC_GRUPOS_COTIZACIONES "
				+ " WHERE idempresa=" + idempresa.toString()
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getProveedo_oc_grupos_cotizacionesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idgrupooc,grupooc,fechadesde,fechahasta,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM PROVEEDO_OC_GRUPOS_COTIZACIONES "
				+ " where idempresa= " + idempresa.toString()
				+ " and (idgrupooc::VARCHAR LIKE '%" + ocurrencia + "%' OR "
				+ " UPPER(grupooc) LIKE '%" + ocurrencia.toUpperCase() + "%') "
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getProveedo_oc_grupos_cotizacionesPK(BigDecimal idgrupooc,
			BigDecimal idempresa) throws EJBException {

		String cQuery = ""
				+ "SELECT idgrupooc,grupooc,fechadesde,fechahasta,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM PROVEEDO_OC_GRUPOS_COTIZACIONES "
				+ " WHERE idgrupooc=" + idgrupooc.toString()
				+ "   AND idempresa=" + idempresa.toString();

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String proveedo_oc_grupos_cotizacionesDelete(BigDecimal idgrupooc,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM PROVEEDO_OC_GRUPOS_COTIZACIONES WHERE idgrupooc="
				+ idgrupooc.toString() + " AND idempresa=" + idempresa;
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM PROVEEDO_OC_GRUPOS_COTIZACIONES WHERE idgrupooc="
						+ idgrupooc.toString()
						+ " AND idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : proveedo_oc_grupos_cotizacionesDelete( BigDecimal idgrupooc ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: proveedo_oc_grupos_cotizacionesDelete( BigDecimal idgrupooc )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String proveedo_oc_grupos_cotizacionesCreate(String grupooc,
			java.sql.Date fechadesde, java.sql.Date fechahasta,
			String usuarioalt, BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (grupooc == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: grupooc ";
		if (fechadesde == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechadesde ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (grupooc.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: grupooc ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO PROVEEDO_OC_GRUPOS_COTIZACIONES(grupooc, fechadesde, fechahasta, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, grupooc);
				insert.setDate(2, fechadesde);
				insert.setDate(3, fechahasta);
				insert.setString(4, usuarioalt);
				insert.setBigDecimal(5, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String proveedo_oc_grupos_cotizacionesCreate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String proveedo_oc_grupos_cotizacionesCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String proveedo_oc_grupos_cotizacionesCreateOrUpdate(
			BigDecimal idgrupooc, String grupooc, java.sql.Date fechadesde,
			java.sql.Date fechahasta, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idgrupooc == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idgrupooc ";
		if (grupooc == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: grupooc ";
		if (fechadesde == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechadesde ";

		// 2. sin nada desde la pagina
		if (grupooc.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: grupooc ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM proveedo_oc_grupos_cotizaciones WHERE idgrupooc = "
					+ idgrupooc.toString()
					+ " AND idempresa= "
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
					sql = "UPDATE PROVEEDO_OC_GRUPOS_COTIZACIONES SET grupooc=?, fechadesde=?, fechahasta=?, usuarioact=?, fechaact=? WHERE idgrupooc=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, grupooc);
					insert.setDate(2, fechadesde);
					insert.setDate(3, fechahasta);
					insert.setString(4, usuarioact);
					insert.setTimestamp(5, fechaact);
					insert.setBigDecimal(6, idgrupooc);
					insert.setBigDecimal(7, idempresa);
				} else {
					String ins = "INSERT INTO PROVEEDO_OC_GRUPOS_COTIZACIONES(grupooc, fechadesde, fechahasta, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, grupooc);
					insert.setDate(2, fechadesde);
					insert.setDate(3, fechahasta);
					insert.setString(4, usuarioalt);
					insert.setBigDecimal(5, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String proveedo_oc_grupos_cotizacionesCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String proveedo_oc_grupos_cotizacionesCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String proveedo_oc_grupos_cotizacionesUpdate(BigDecimal idgrupooc,
			String grupooc, java.sql.Date fechadesde, java.sql.Date fechahasta,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idgrupooc == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idgrupooc ";
		if (grupooc == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: grupooc ";
		if (fechadesde == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechadesde ";

		// 2. sin nada desde la pagina
		if (grupooc.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: grupooc ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM proveedo_oc_grupos_cotizaciones WHERE idgrupooc = "
					+ idgrupooc.toString() + " AND idempresa=" + idempresa;
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE PROVEEDO_OC_GRUPOS_COTIZACIONES SET grupooc=?, fechadesde=?, fechahasta=?, usuarioact=?, fechaact=? WHERE idgrupooc=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, grupooc);
					insert.setDate(2, fechadesde);
					insert.setDate(3, fechahasta);
					insert.setString(4, usuarioact);
					insert.setTimestamp(5, fechaact);
					insert.setBigDecimal(6, idgrupooc);
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
					.error("Error SQL public String proveedo_oc_grupos_cotizacionesUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String proveedo_oc_grupos_cotizacionesUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * Metodos para la entidad: vproveedoOcEstado Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Fri Apr 20 15:02:01 ART 2007
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getVproveedoOcEstadoAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT id_oc_cabe,idestadooc,estadooc,idproveedor,razon_social,fechaoc,"
				+ "       idcondicionpago,condicion,idmoneda,moneda,idtipoiva,tipoiva,idgrupooc,"
				+ "       grupooc,totaliva,observaciones,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM VPROVEEDOOCESTADO WHERE idempresa="
				+ idempresa.toString() + " ORDER BY 1 DESC  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getVproveedoOcEstadoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT id_oc_cabe,idestadooc,estadooc,idproveedor,razon_social,fechaoc,"
				+ "       idcondicionpago,condicion,idmoneda,moneda,idtipoiva,tipoiva,idgrupooc,"
				+ "       grupooc,totaliva,observaciones,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM VPROVEEDOOCESTADO WHERE ((id_oc_cabe::VARCHAR) LIKE '"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(grupooc) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(estadooc)  LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%') AND idempresa="
				+ idempresa.toString() + " ORDER BY 1 DESC  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getVproveedoOcEstadoPK(BigDecimal id_oc_cabe,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT id_oc_cabe,idestadooc,estadooc,idproveedor,razon_social,"
				+ "       fechaoc,idcondicionpago,condicion,idmoneda,moneda,idtipoiva,"
				+ "       tipoiva,idgrupooc,grupooc,totaliva,observaciones,fecha_entrega_prevista,codigo_dt,descrip_dt,"
				+ "       usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM VPROVEEDOOCESTADO WHERE id_oc_cabe="
				+ id_oc_cabe.toString() + " AND idempresa="
				+ idempresa.toString();

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getVproveedoOcEstadoPKGrupo(BigDecimal id_oc_cabe,
			BigDecimal idgrupooc, BigDecimal idempresa) throws EJBException {

		String cQuery = ""
				+ "SELECT id_oc_cabe,idestadooc,estadooc,idproveedor,razon_social,"
				+ "       fechaoc,idcondicionpago,condicion,idmoneda,moneda,idtipoiva,"
				+ "       tipoiva,idgrupooc,grupooc,totaliva,observaciones,"
				+ "       usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM VPROVEEDOOCESTADO WHERE id_oc_cabe <> "
				+ id_oc_cabe.toString()
				+ " AND idgrupooc <> 0 AND idgrupooc IS NOT NULL AND idgrupooc = "
				+ idgrupooc.toString() + " AND idempresa="
				+ idempresa.toString();

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public String proveedoOcCabeEstadoUpdate(BigDecimal id_oc_cabe,
			BigDecimal idestadooc, BigDecimal idgrupooc, String usuarioact,
			BigDecimal idempresa) throws EJBException, SQLException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos

		// 2. sin nada desde la pagina
		// fin validaciones

		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM vproveedoOcEstado WHERE id_oc_cabe = "
					+ id_oc_cabe.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			int i = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			dbconn.setAutoCommit(false);
			if (salida.equalsIgnoreCase("OK")) {
				if (total > 0) { // si existe hago update
					sql = ""
							+ "UPDATE proveedo_oc_cabe SET idestadooc=?, usuarioact=?, fechaact=? "
							+ " WHERE id_oc_cabe=? AND idempresa=?;";

					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, idestadooc);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, id_oc_cabe);
					insert.setBigDecimal(5, idempresa);
					i = insert.executeUpdate();
					if (i != 1)
						salida = "Imposible asignar nuevo estado a la OC..";

					if (salida.equalsIgnoreCase("OK")
							&& idgrupooc.compareTo(new BigDecimal(0)) != 0
							&& idgrupooc.compareTo(new BigDecimal(4)) != 0) {

						sql = ""
								+ "UPDATE proveedo_oc_cabe SET idestadooc=?, usuarioact=?, fechaact=? "
								+ " WHERE idgrupooc = ? AND id_oc_cabe <> ? AND idempresa=?;";

						insert = dbconn.prepareStatement(sql);
						insert.setBigDecimal(1, new BigDecimal(4));
						insert.setString(2, usuarioact);
						insert.setTimestamp(3, fechaact);
						insert.setBigDecimal(4, idgrupooc);
						insert.setBigDecimal(5, id_oc_cabe);
						insert.setBigDecimal(6, idempresa);
						i = insert.executeUpdate();
						if (i < 0)
							salida = "Imposible asignar estado rechazado a las ordenes asociadas al grupo de la OC.";
					}

				}

			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String vproveedoOcEstadoUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String vproveedoOcEstadoUpdate(.....)"
							+ ex);
		}

		if (!salida.equalsIgnoreCase("OK")) {
			dbconn.rollback();
		} else
			salida = "Actualizacion correcta.";

		dbconn.setAutoCommit(true);

		return salida;
	}

	// VER DE RECUPERAR DESDE ENTIDAD, LA MISMA YA ESTA DEFINIDA
	public String getTipoComprobante(BigDecimal tipomov, String tipomovs) {
		String tipocomp = "";
		try {
			// TODO: Por ahora HARCODE ... es necesario definir una entidad
			// con los tipos de documentos.

			switch (tipomov.intValue()) {
			case 1:
				tipocomp = "FA" + tipomovs;
				break;
			case 2:
				tipocomp = "ND" + tipomovs;
				break;
			case 3:
				tipocomp = "NC" + tipomovs;
				break;
			case 4:
				tipocomp = "PA" + tipomovs;// ??
				break;
			default:
				break;
			}

		} catch (Exception e) {
			log
					.error("getTipoComprobante(BigDecimal tipomov, String tipomovs): "
							+ e);
		}
		return tipocomp;
	}

	// -- informes

	// -- cuenta corriente de proveedores

	/*
	 * while (rsSalida.next()) { int totCampos = md.getColumnCount() - 1;
	 * String[] sSalida = new String[totCampos + 1]; int i = 0; while (i <=
	 * totCampos) { sSalida[i] = rsSalida.getString(++i); }
	 * vecSalida.add(sSalida); }
	 * 
	 * 
	 * *
	 */

	public List getProveedoresCtaCteDetalle(BigDecimal idproveedorDesde,
			String tipo,java.sql.Timestamp fechadesde, java.sql.Timestamp fechahasta,
			BigDecimal idempresa) throws EJBException {
		/*
		 * Objetivo: Traer el detalle de la cta cte
		 */
		String cQuery = "";
		List vecSalida = new ArrayList();
		if (tipo.equalsIgnoreCase("P")) {
			cQuery = "select idproveedor, razon_social, fecha,sucursal, "
					+ "       comprob, tipomovs, debe, haber, saldo, fechavto, nrointerno "
					+ " from vproveedoctacte " + "  where idempresa="
					+ idempresa.toString() + "  and idproveedor = "
					+ idproveedorDesde.toString()
					+ " and saldo <> 0 and fecha between '" + fechadesde
					+ "' and '" + fechahasta + "' "
					+ " order by fecha, sucursal,comprob; " + "";
			log.info("cQuery P: " + cQuery);
			vecSalida = getLista(cQuery);
		}
		if (tipo.equalsIgnoreCase("H")) {

			try {
				
				String cQuerySaldoAnterior = " select idproveedor, razon_social, '' as fecha , 0 as sucursal, 'SALDO ANTERIOR "+ fechadesde +"' as comprob, 'SAA' as tipomovs,"
						+ " sum(debe) as debe,"
						+ " sum(haber) as haber,"
						+ " sum(debe) - sum(haber) as saldo,'' as fechavto,"						
						+ " 0 as nrointerno "
						+ " from vproveedoctacte where idempresa="
						+ idempresa.toString()
						+ " and idproveedor = "
						+ idproveedorDesde.toString()						
						+ " and  fecha < '" + fechadesde + "'  "
						+ " group by idproveedor, razon_social		 "
						+ " order by fecha";
				
				Statement statement = dbconn.createStatement();
				ResultSet rsSalida = statement
						.executeQuery(cQuerySaldoAnterior);
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
				if (rsSalida != null) {
					rsSalida.close();
					rsSalida = null;
					md = null;
				}
               
				// ahora agrego la parte del detalle.
				cQuery = " select  idproveedor, razon_social, fecha ,"
						+ " sucursal::varchar(10),  comprob::varchar(10), tipomovs, debe, haber,"
						+ " saldo, fechavto, nrointerno"
						+ " from vproveedoctacte" + " where idempresa="
						+ idempresa.toString() + " and idproveedor = "
						+ idproveedorDesde.toString() + " and "
						+ "fecha between '" + fechadesde + "' and " + "'"
						+ fechahasta + "' order by fecha,comprob;";
				log.info("cQuery H: " + cQuery);
				statement = dbconn.createStatement();
				rsSalida = statement.executeQuery(cQuery);
				md = rsSalida.getMetaData();

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
				log.error("(SQLE) getLista(String query) - INVOCADO POR "
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

		}

		return vecSalida;
	}
	
	public List getProveedoresCtaCteDetalleDesdeHasta(BigDecimal idproveedorDesde,
			BigDecimal idproveedorHasta, String tipo,
			java.sql.Timestamp fechadesde, java.sql.Timestamp fechahasta,
			BigDecimal idempresa) throws EJBException {
		/*
		 * Objetivo: Traer el detalle de la cta cte
		 */
		String cQuery = "";
		List vecSalida = new ArrayList();
		if (tipo.equalsIgnoreCase("P")) {
			cQuery = "select idproveedor, razon_social, fecha,sucursal, "
					+ "       comprob, tipomovs, debe, haber, saldo, fechavto, nrointerno "
					+ " from vproveedoctacte " + "  where idempresa="
					+ idempresa.toString() + "  and idproveedor = "
					+ idproveedorDesde.toString() + "  "					
					+ " and saldo <> 0 and fecha between '" + fechadesde
					+ "' and '" + fechahasta + "' "
					+ " order by fecha, sucursal,comprob; " + "";
			log.info("cQuery P: " + cQuery);
			vecSalida = getLista(cQuery);
		}
		if (tipo.equalsIgnoreCase("H")) {

			try {
				String cQuerySaldoAnterior = " select idproveedor, razon_social, current_date::date as fecha , 0 as sucursal, 'SALDO ANTERIOR "+ fechadesde +"' as comprob, 'SAA' as tipomovs,"
				+ " sum(debe) as debe,"
				+ " sum(haber) as haber,"
				+ " sum(debe) - sum(haber) as saldo, current_date::date as fechavto,"						
				+ " 0 as nrointerno "
				+ " from vproveedoctacte where idempresa="
				+ idempresa.toString()
				+ " and idproveedor = "
				+ idproveedorDesde.toString()						
				+ " and  fecha < '" + fechadesde + "'  "
				+ " group by idproveedor, razon_social		 "
				+ " order by fecha";
				Statement statement = dbconn.createStatement();
				ResultSet rsSalida = statement
						.executeQuery(cQuerySaldoAnterior);
				ResultSetMetaData md = rsSalida.getMetaData();
				
log.info("query saldo anterior " + cQuerySaldoAnterior );

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

				// ahora agrego la parte del detalle.
				cQuery = " select  idproveedor, razon_social, fecha ,"
						+ " sucursal::varchar(10),  comprob::varchar(10), tipomovs, debe, haber,"
						+ " saldo, fechavto, nrointerno"
						+ " from vproveedoctacte" + " where idempresa="
						+ idempresa.toString() + " and idproveedor = "
						+ idproveedorDesde.toString() + " and "
						+ "fecha between '" + fechadesde + "' and " + "'"
						+ fechahasta + "' order by fecha,comprob;";
				log.info("cQuery H: " + cQuery);
				statement = dbconn.createStatement();
				rsSalida = statement.executeQuery(cQuery);
				md = rsSalida.getMetaData();

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
				log.error("(SQLE) getLista(String query) - INVOCADO POR "
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

		}

		return vecSalida;
	}

	public List getProveedoresAplicaciones(BigDecimal idproveedorDesde,
			String tipo, java.sql.Timestamp fechadesde,
			java.sql.Timestamp fechahasta, BigDecimal idempresa)
			throws EJBException {
		/*
		 * Objetivo: Traer las aplicaciones
		 */
		String cQuery = "select  idproveedor, tipomov_doc , to_char(fechadoc,'dd/mm/yyyy') as fecha ,sucu_doc, comprob_doc,"
				+ "tipomovs_doc, debedoc, haberdoc, saldo_doc, to_char(fechavto_doc,'dd/mm/yyyy') as fechavto from vproveedoaplicaciones "
				+ " where "
				+ " idempresa="
				+ idempresa.toString()
				+ " and idproveedor = "
				+ idproveedorDesde.toString()
				+ " and fechadoc between '"
				+ fechadesde
				+ "' and '"
				+ fechahasta + "'";
		if (tipo != null && tipo.equalsIgnoreCase("P")) {
			cQuery += " and saldo_doc <> 0 ";
		}
		log.info("cQuery: " + cQuery);
		// cQuery += " order by 1,vproveedoctacte.fecha; ";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getProveedoresVencimientos(String fechadesde,
			String fechahasta, BigDecimal idempresa) throws EJBException {
		/*
		 * Objetivo: Traer el detalle de la cta cte
		 */

		String cQuery = " select idproveedor, razon_social, sucursal, comprob, nrointerno,to_char(fechamov,'dd/mm/yyyy') as fechamovimiento, to_char(fechavto,'dd/mm/yyyy') as fechavencimiento, importe, saldo "
				+ " from vproveedoresvencimientos "
				+ " where idempresa = "
				+ idempresa.toString()
				+ ""
				+ " and fechavto between to_date( '"
				+ fechadesde
				+ "','DD/MM/YYYY') and to_date( '"
				+ fechahasta
				+ "','DD/MM/YYYY') "
				+ " order by fechavto, razon_social "
				+ "  ";
		List vecSalida = getLista(cQuery);

		return vecSalida;
	}

	public List getProveedoresSaldoaFecha(String fechahasta,
			BigDecimal idempresa) throws EJBException {
		/*
		 * Objetivo: Traer el detalle de la cta cte
		 */
		ResultSet rsSalida = null;
		String cQuery = "SELECT pr.idproveedor, pr.razon_social, sum(  "
				+ " CASE mp.tipomov "
				+ "  WHEN 1 THEN (mp.importe * -1::numeric)::numeric(18,2)"
				+ "  WHEN 2 THEN (mp.importe * -1::numeric)::numeric(18,2)"
				+ "  WHEN 3 THEN mp.importe::numeric(18,2)"
				+ "  WHEN 4 THEN mp.importe::numeric(18,2)"
				+ "  ELSE 0::numeric::numeric(18,2)"
				+ " END) AS saldo "
				+ " FROM proveedoproveed pr join proveedomovprov mp on (pr.idproveedor = mp.idproveedor and pr.idempresa = mp.idempresa) "
				+ " WHERE " + "  mp.idempresa = " + idempresa.toString()
				+ " and mp.fechamov::date <= to_date('" + fechahasta
				+ "','DD/MM/YYYY') "
				+ " GROUP BY pr.idproveedor, pr.razon_social "
				+ "HAVING sum( CASE mp.tipomov "
				+ "WHEN 1 THEN (mp.importe * -1::numeric)::numeric(18,2) "
				+ "WHEN 2 THEN (mp.importe * -1::numeric)::numeric(18,2) "
				+ "WHEN 3 THEN mp.importe::numeric(18,2) "
				+ "WHEN 4 THEN mp.importe::numeric(18,2) "
				+ "ELSE 0::numeric::numeric(18,2) END) <>0 " + " order by 2 ; ";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getProveedoresSubdiarioCompras(BigDecimal idproveedorDesde,
			BigDecimal idproveedorHasta, String fechadesde, String fechahasta,
			BigDecimal idempresa) throws EJBException {
		/*
		 * Objetivo: Subdiario IVA Compras
		 */
		String cQuery = ""
				+ " select idempresa, idproveedor, razon_social, to_char(fechamov,'dd/mm/yyyy') as fechamovimiento, "
				+ " sucursal, comprob, nrointerno, tipomovs, netogravado::numeric(18,2), iva::numeric(18,2), total::numeric(18,2), exento::numeric(18,2) "
				+ " from vproveedoresSubdiarioCompras " + " where "
				+ " idempresa = " + idempresa.toString() + ""
				+ " and idproveedor between " + idproveedorDesde.toString()
				+ " and " + idproveedorHasta.toString() + " "
				+ " and fechamov between to_date( '" + fechadesde
				+ "','DD/MM/YYYY') and to_date( '" + fechahasta
				+ "','DD/MM/YYYY') " + " order by idproveedor, fechamov " + " ";
		List vecSalida = getLista(cQuery);

		return vecSalida;
	}

	public List getProveedoresSubdiarioCompras(java.sql.Date fechadesde,
			java.sql.Date fechahasta, BigDecimal idempresa) throws EJBException {
		/*
		 * Objetivo: Subdiario IVA Compras
		 */
		String cQuery = ""
				+ " select idempresa, idproveedor, razon_social, to_char(fechamov,'dd/mm/yyyy') as fechamovimiento, "
				+ " sucursal, comprob, nrointerno, tipomovs, netogravado::numeric(18,2), iva::numeric(18,2), total::numeric(18,2) , exento::numeric(18,2), ivapercepcion::numeric(18,2) "
				+ " from vproveedoresSubdiarioCompras " + " where "
				+ " idempresa = " + idempresa.toString() + ""
				+ " and fechamov between  '" + fechadesde + "'and '"
				+ fechahasta + "' order by idproveedor, fechamov " + " ";
		log.info("subdiario compras: " + cQuery);

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getProveedoresSubdiarioPagos(String fechadesde,
			String fechahasta, BigDecimal idempresa) throws EJBException {
		/*
		 * Objetivo: Traer el subdiario de pagos
		 */

		String cQuery = " select idproveedor, razon_social, to_char(fechamov,'dd/mm/yyyy') as fechamovimiento, sucursal, comprob, nrointerno, tipomovs, cuit, importe "
				+ " from vproveedoresSubdiarioPagos "
				+ " where idempresa ="
				+ idempresa.toString()
				+ ""
				+ " and fechamov between to_date( '"
				+ fechadesde
				+ "','DD/MM/YYYY') and to_date( '"
				+ fechahasta
				+ "','DD/MM/YYYY')  " + " order by nrointerno " + "  ";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// informe de movimientos de proveedores
	public List getProveedoInformeMovimientos(long limit, long offset,
			String idproveedordesde, String idproveedorhasta,
			Timestamp fechadesde, Timestamp fechahasta, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ " select"
				+ " proveedomovprov.idproveedor,"
				+ " proveedoproveed.razon_social, "
				+ " to_char(proveedomovprov.fechamov,'dd/mm/yyyy') as fechamov, "
				+ " proveedomovprov.tipomovs, "
				+ " proveedomovprov.comprob, "
				+ " CASE proveedomovprov.tipomov WHEN 4 THEN (proveedomovprov.importe * 1::numeric)::numeric(18,2) ELSE 0::numeric::numeric(18,2) END AS debe,"
				+ " CASE proveedomovprov.tipomov WHEN 1 THEN (proveedomovprov.importe * (- 1::numeric))::numeric(18,2) WHEN 2 THEN (proveedomovprov.importe * (- 1::numeric))::numeric(18,2) WHEN 3 THEN (proveedomovprov.importe * (- 1::numeric))::numeric(18,2) ELSE 0::numeric::numeric(18,2) END AS haber,"
				+ " proveedomovprov.saldo::numeric(18,2),  "
				+ " (CASE proveedomovprov.tipomov WHEN 4 THEN (proveedomovprov.importe * 1::numeric)::numeric(18,2) ELSE 0::numeric::numeric(18,2) end ) - (CASE proveedomovprov.tipomov WHEN 1 THEN (proveedomovprov.importe * (- 1::numeric))::numeric(18,2) WHEN 2 THEN (proveedomovprov.importe * (- 1::numeric))::numeric(18,2) WHEN 3 THEN (proveedomovprov.importe * (- 1::numeric))::numeric(18,2) ELSE 0::numeric::numeric(18,2) end ) as saldoacomulado, "
				+ " to_char(proveedomovprov.fechavto,'dd/mm/yyyy') as fechavto  "
				+ " from " + " proveedomovprov," + " proveedoproveed"
				+ " where "
				+ " proveedoproveed.idproveedor = proveedomovprov.idproveedor"
				+ " and proveedoproveed.idempresa = proveedomovprov.idempresa"
				+ " and proveedomovprov.idproveedor between "
				+ idproveedordesde.toString() + " and "
				+ idproveedorhasta.toString()
				+ " and proveedomovprov.fechamov between  '" + fechadesde
				+ "'::timestamp and '" + fechahasta + "'::timestamp  "
				+ "and proveedomovprov.idempresa=" + idempresa.toString()
				+ " ORDER BY 1,3  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// informe de movimientos de proveedores
	public List getInformeAplicaciones(long limit, long offset,
			String idproveedordesde, String idproveedorhasta,
			Timestamp fechadesde, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " select * from vproveedoresaplicaciones "
				+ " where idproveedor between " + idproveedordesde.toString()
				+ " and " + idproveedorhasta.toString() + " and fechamov >=  '"
				+ fechadesde + "'::timestamp " + " and idempresa = "
				+ idempresa.toString() + " ORDER BY 1,3  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		log.info(cQuery);
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// informe de movimientos de proveedores
	public List getProveedoInformeComprasporProveedores(long limit,
			long offset, String idproveedordesde, String idproveedorhasta,
			Timestamp fechadesde, Timestamp fechahasta, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " select"
				+ " proveedomovprov.idproveedor, "
				+ " proveedoproveed.razon_social, "
				+ " to_char(proveedomovprov.fechamov,'dd/mm/yyyy') as fechamov, "
				+ " proveedomovprov.sucursal, " + " proveedomovprov.comprob,  "
				+ " proveedomovprov.tipomovs,  "
				+ " proveedomovprov.importe:: numeric(18,2)  " + " from "
				+ " proveedomovprov," + " proveedoproveed" + " where "
				+ " proveedoproveed.idproveedor = proveedomovprov.idproveedor"
				+ " and proveedoproveed.idempresa = proveedomovprov.idempresa"
				+ " and proveedomovprov.tipomov <> 4 "
				+ " and proveedomovprov.idproveedor between "
				+ idproveedordesde.toString() + " and "
				+ idproveedorhasta.toString()
				+ " and proveedomovprov.fechamov between  '" + fechadesde
				+ "'::timestamp and '" + fechahasta + "'::timestamp  "
				+ "and proveedomovprov.idempresa=" + idempresa.toString()
				+ " ORDER BY 1,3  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getArriboOCDeposito(String codigo_dt, String fechadesde,
			String fechahasta, BigDecimal idEstadoOC, BigDecimal idempresa)
			throws EJBException {
		/*
		 * Objetivo: traer las oc con fecha prevista para una determinada fecha
		 */
		ResultSet rsSalida = null;
		String cQuery = ""
				+ " select  occabe.id_oc_cabe as orden,  estados.estadooc as  estado,  occabe.idproveedor,  "
				+ "   proveed.razon_social,  to_char(occabe.fechaoc,'dd/mm/yyyy') as fechaoc, to_char(occabe.fecha_entrega_prevista,'dd/mm/yyyy') as fecha_entrega_prevista,  depositos.descrip_dt as deposito,  "
				+ "    occabe.observaciones,  occabe.idempresa,  occabe.idestadooc as idestado,  occabe.codigo_dt as coddeposito "
				+ "  from  proveedo_oc_cabe occabe "
				+ "    join proveedo_oc_estados estados on(occabe.idestadooc=estados.idestadooc and occabe.idempresa=estados.idempresa) "
				+ "    join proveedoproveed proveed on (occabe.idproveedor = proveed.idproveedor and occabe.idempresa=proveed.idempresa) "
				+ "      join stockdepositos depositos on ( depositos.codigo_dt = occabe.codigo_dt and occabe.idempresa=depositos.idempresa ) "
				+ "   where  occabe.idestadooc <> 1 "
				+ "   and occabe.fecha_entrega_prevista is not null "
				+ "   and occabe.fecha_entrega_prevista "
				+ "  between to_date('" + fechadesde
				+ "','DD/MM/YYYY') and to_date('" + fechahasta
				+ "','DD/MM/YYYY') " + " and occabe.codigo_dt = '" + codigo_dt
				+ "'" + " and estados.idestadooc = " + idEstadoOC.toString()
				+ " and occabe.idempresa = " + idempresa.toString()
				+ " order by occabe.fecha_entrega_prevista " + "";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getProveedoresEstadosOCLovOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa, String usuario)
			throws EJBException {

		String cQuery = ""
				+ "SELECT  idestadooc,estadooc,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM proveedo_oc_estados " + " where idempresa= "
				+ idempresa.toString();
		cQuery += " and (idestadooc::VARCHAR LIKE '%" + ocurrencia + "%' OR "
				+ " UPPER(estadooc) LIKE '%" + ocurrencia.toUpperCase()
				+ "%') ";
		cQuery += " ORDER BY 1";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getProveedoresEstadosOCLovAll(long limit, long offset,
			BigDecimal idempresa, String usuario) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT  idestadooc,estadooc,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM proveedo_oc_estados WHERE idempresa = "
				+ idempresa.toString();

		cQuery += " ORDER BY 1  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: proveedoMovProv Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Thu Jan 24 14:30:44 GMT-03:00 2007
	 */
	// para todo (ordena por el segundo campo por defecto)
	public List getLovCajaMovProvAll(long limit, long offset,
			BigDecimal idproveedor, String tipomovIN, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT nrointerno,idproveedor,fechamov,sucursal,comprob,tipomov,tipomovs,"
				+ "       importe::numeric(18,2),saldo::numeric(18,2),idcondicionpago,fecha_subd,retoque,fechavto,"
				+ "       usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM PROVEEDOMOVPROV" + " WHERE idproveedor = "
				+ idproveedor + " AND saldo > 0 AND tipomov IN (" + tipomovIN
				+ ") AND idempresa=" + idempresa.toString()
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getLovCajaMovProvOcu(long limit, long offset,
			BigDecimal idproveedor, String tipomovIN, String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT nrointerno,idproveedor,fechamov,sucursal,comprob,tipomov,"
				+ "       tipomovs,importe::numeric(18,2),saldo::numeric(18,2),idcondicionpago,fecha_subd,retoque,fechavto,"
				+ "       usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM PROVEEDOMOVPROV " + " WHERE IDPROVEEDOR = "
				+ idproveedor + " AND saldo > 0  and tipomov IN (" + tipomovIN
				+ ") AND comprob::VARCHAR LIKE '" + ocurrencia
				+ "%'  AND idempresa = " + idempresa.toString()
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getArriboOCAprobadas(BigDecimal idempresa) throws EJBException {
		/*
		 * Objetivo: traer las oc con fecha prevista para una determinada fecha
		 */
		ResultSet rsSalida = null;
		String cQuery = ""
				+ " select  occabe.id_oc_cabe as orden,  estados.estadooc as  estado,  occabe.idproveedor,  "
				+ " proveed.razon_social,  to_char(occabe.fechaoc,'dd/mm/yyyy') as fechaoc, to_char(occabe.fecha_entrega_prevista,'dd/mm/yyyy') as fecha_entrega_prevista,  depositos.descrip_dt as deposito,  "
				+ " occabe.observaciones,  occabe.idempresa,  occabe.idestadooc as idestado,  occabe.codigo_dt as coddeposito "
				+ " from  proveedo_oc_cabe occabe "
				+ " join proveedo_oc_estados estados on(occabe.idestadooc=estados.idestadooc and occabe.idempresa=estados.idempresa) "
				+ " join proveedoproveed proveed on (occabe.idproveedor = proveed.idproveedor and occabe.idempresa=proveed.idempresa) "
				+ " join stockdepositos depositos on ( depositos.codigo_dt = occabe.codigo_dt and occabe.idempresa=depositos.idempresa ) "
				+ " where  occabe.idestadooc <> 1 "
				+ " and occabe.fecha_entrega_prevista is not null "
				+ " and estados.idestadooc = 2 "// aprobada
				+ " and occabe.idempresa = " + idempresa.toString()
				+ " order by occabe.fecha_entrega_prevista ";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: proveedoAnexop Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Fri May 22 09:47:09 GYT 2009
	 * 
	 */

	public static String proveedoAnexopDelete(BigDecimal nroint_ant,
			Connection conn, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM PROVEEDOANEXOP WHERE nroint_ant="
				+ nroint_ant.toString() + " AND idempresa="
				+ idempresa.toString();
		String salida = "OK";
		try {
			Statement statement = conn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM PROVEEDOANEXOP WHERE nroint_ant="
						+ nroint_ant.toString().toString() + " AND idempresa="
						+ idempresa.toString();
				statement.execute(cQuery);
			} else {
				salida = "Error: Registro de anexo de proveedores inexistente( "
						+ nroint_ant + " ) .";
			}
		} catch (SQLException sqlException) {
			salida = "(SQLE) Imposible eliminar anexo de proveedores ( "
					+ nroint_ant + " ) .";
			log
					.error("Error SQL en el metodo : proveedoAnexopDelete( BigDecimal nroint_ant, Connection conn, BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "(EX)Imposible eliminar anexo de proveedores( "
					+ nroint_ant + " ) .";
			log
					.error("Salida por exception: en el metodo: proveedoAnexopDelete( BigDecimal nroint_ant, Connection conn, BigDecimal idempresa )  "
							+ ex);
		}
		return salida;
	}

	public long GetDevuelvoCuentaContable(BigDecimal idempresa,
			BigDecimal Ejercicio, BigDecimal cuenta) throws EJBException {

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
		String cQuery = "SELECT count(1)AS total from contableinfiplan "
				+ " where idempresa = " + idempresa.toString()
				+ " and ejercicio = " + Ejercicio + " and idcuenta = "
				+ cuenta.toString();
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				total = rsSalida.getLong("total");
			} else {
				log
						.warn("GetDevuelvoCuentaContable()- Error al recuperar total: ");
			}
		} catch (SQLException sqlException) {
			log
					.error("GetDevuelvoCuentaContable()- Error SQL: "
							+ sqlException);
		} catch (Exception ex) {
			log.error("GetDevuelvoCuentaContable()- Salida por exception: "
					+ ex);
		}
		return total;
	}

	public long GetDevuelvoCuentaContable2(BigDecimal idempresa,
			BigDecimal Ejercicio, String cuenta) throws EJBException {

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
		String cQuery = " SELECT count(1)AS total from contableinfiplan "
				+ " where idempresa = " + idempresa.toString()
				+ " and ejercicio = " + Ejercicio + " and idcuenta = "
				+ cuenta.toString();
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				total = rsSalida.getLong("total");
			} else {
				log
						.warn("GetDevuelvoCuentaContable2()- Error al recuperar total: ");
			}
		} catch (SQLException sqlException) {
			log.error("GetDevuelvoCuentaContable2()- Error SQL: "
					+ sqlException);
		} catch (Exception ex) {
			log.error("GetDevuelvoCuentaContable2()- Salida por exception: "
					+ ex);
		}
		return total;
	}

	public List getTipoCompCombo(BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "select idtipocomp, tipocomp from proveedotipocomp where idempresa = "
				+ idempresa + " order by 1";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getConceptos4Tipocomp(String letra, BigDecimal idtipocomp,
			BigDecimal ejercicio, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT    "
				+ "   conc.idconcepto, "
				+ "   cuen.idcuenta, "
				+ "   cuen.cuenta, "
				+ "   conc.tipo "
				+ "FROM "
				+ "proveedoconceptoscontables conc "
				+ "join contableinfiplan cuen on (conc.idcuenta = cuen.idcuenta and conc.idempresa = cuen.idempresa and cuen.ejercicio = "
				+ ejercicio + ")  " + "WHERE " + "   conc.idempresa = "
				+ idempresa + " " + "   and conc.letra = '" + letra + "' "
				+ "   and idtipocomp = " + idtipocomp + " " + "order by orden ";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// abm proveedoconceptoscontables fgp 21/12/09

	public List getProveedoConceptoscontablesAll(long limit, long offset,
			BigDecimal idempresa, BigDecimal ejercicio) throws EJBException {
		String cQuery = ""
				+ " SELECT proveedoconceptoscontables.idconcepto,contableinfiplan.cuenta,proveedoconceptoscontables.orden,proveedoconceptoscontables.letra,proveedotipocomp.tipocomp,proveedoconceptoscontables.tipo "
				+ " FROM proveedoconceptoscontables "
				+ " inner join contableinfiplan on proveedoconceptoscontables.idcuenta = contableinfiplan.idcuenta and proveedoconceptoscontables.idempresa = contableinfiplan.idempresa "
				+ " inner join proveedotipocomp on proveedoconceptoscontables.idtipocomp = proveedotipocomp.idtipocomp and proveedoconceptoscontables.idempresa = proveedotipocomp.idempresa "
				+ " WHERE proveedoconceptoscontables.idempresa = "
				+ idempresa.toString() + " and contableinfiplan.ejercicio = "
				+ ejercicio + " ORDER BY 2  LIMIT " + limit + " OFFSET  "
				+ offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia
	public List getProveedoConceptoscontablesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa, BigDecimal ejercicio)
			throws EJBException {
		String cQuery = ""
				+ " SELECT proveedoconceptoscontables.idconcepto,contableinfiplan.cuenta,proveedoconceptoscontables.orden,proveedoconceptoscontables.letra,proveedotipocomp.tipocomp,proveedoconceptoscontables.tipo "
				+ " FROM proveedoconceptoscontables "
				+ " inner join contableinfiplan on proveedoconceptoscontables.idcuenta = contableinfiplan.idcuenta and proveedoconceptoscontables.idempresa = contableinfiplan.idempresa "
				+ " inner join proveedotipocomp on proveedoconceptoscontables.idtipocomp = proveedotipocomp.idtipocomp and proveedoconceptoscontables.idempresa = proveedotipocomp.idempresa "
				+ " WHERE proveedoconceptoscontables.idempresa = "
				+ idempresa.toString()
				+ " and contableinfiplan.ejercicio = "
				+ ejercicio
				+ " and (proveedoconceptoscontables.idconcepto::VARCHAR LIKE '%"
				+ ocurrencia + "%' OR "
				+ " UPPER(proveedoconceptoscontables.letra) LIKE '%"
				+ ocurrencia.toUpperCase() + "%') " + " ORDER BY 2  LIMIT "
				+ limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)
	public List getProveedoConceptoscontablesPK(BigDecimal idconcepto,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ " SELECT proveedoconceptoscontables.idconcepto,contableinfiplan.idcuenta,proveedoconceptoscontables.orden,proveedoconceptoscontables.letra,proveedotipocomp.idtipocomp,proveedotipocomp.tipocomp,proveedoconceptoscontables.tipo "
				+ " FROM proveedoconceptoscontables "
				+ " inner join contableinfiplan on proveedoconceptoscontables.idcuenta = contableinfiplan.idcuenta and proveedoconceptoscontables.idempresa = contableinfiplan.idempresa "
				+ " inner join proveedotipocomp on proveedoconceptoscontables.idtipocomp = proveedotipocomp.idtipocomp and proveedoconceptoscontables.idempresa = proveedotipocomp.idempresa "
				+ " WHERE proveedoconceptoscontables.idempresa = "
				+ idempresa.toString()
				+ " and proveedoconceptoscontables.idconcepto = "
				+ idconcepto.toString();
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)
	public String ProveedoConceptoscontablesDelete(BigDecimal idconcepto,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM proveedoconceptoscontables WHERE idconcepto="
				+ idconcepto.toString()
				+ " AND idempresa = "
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM proveedoconceptoscontables WHERE idconcepto="
						+ idconcepto.toString()
						+ " AND idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : ProveedoConceptoscontablesDelete( BigDecimal idconcepto ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: ProveedoConceptoscontablesDelete( BigDecimal idconcepto )  "
							+ ex);
		}
		return salida;
	}

	// insert
	public String ProveedoConceptoscontablesCreate(BigDecimal idcuenta,
			BigDecimal orden, String letra, BigDecimal idtipocomp, String tipo,
			String usuarioalt, BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = ""
						+ "INSERT INTO proveedoconceptoscontables(idcuenta, orden,letra,idtipocomp,tipo, usuarioalt, idempresa ) "
						+ "     VALUES (?, ?, ?, ?,?,?,?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, idcuenta);
				insert.setBigDecimal(2, orden);
				insert.setString(3, letra);
				insert.setBigDecimal(4, idtipocomp);
				insert.setString(5, tipo);
				insert.setString(6, usuarioalt);
				insert.setBigDecimal(7, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String ProveedoConceptoscontablesCreate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String ProveedoConceptoscontablesCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// update
	public String ProveedoConceptoscontablesUpdate(BigDecimal idconcepto,
			BigDecimal idcuenta, BigDecimal orden, String letra,
			BigDecimal idtipocomp, String tipo, String usuarioact,
			BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM proveedoconceptoscontables WHERE idconcepto = "
					+ idconcepto.toString()
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
					sql = "UPDATE proveedoconceptoscontables SET idcuenta=?, orden=?, letra =?,idtipocomp=?,tipo =?, usuarioact=?, fechaact=? WHERE idconcepto=? AND idempresa = ?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, idcuenta);
					insert.setBigDecimal(2, orden);
					insert.setString(3, letra);
					insert.setBigDecimal(4, idtipocomp);
					insert.setString(5, tipo);
					insert.setString(6, usuarioact);
					insert.setTimestamp(7, fechaact);
					insert.setBigDecimal(8, idconcepto);
					insert.setBigDecimal(9, idempresa);
				}
				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String ProveedoConceptoscontablesUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String ProveedoConceptoscontablesUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public List getProveedoConsultaCtaCteLovValoresAll(long limit, long offset,
			BigDecimal nrocomprobante, BigDecimal idempresa)
			throws EJBException {
		String cQuery = "Select cuenta_con, import_con, detalle from proveedocontprov where compr_con = "
				+ nrocomprobante.toString()
				+ " and idempresa = "
				+ idempresa.toString();
		log.info("CtaCteValores: " + cQuery);
		List vecSalida = getLista(cQuery);
		return vecSalida;

	}

	public List getProveedoConsultaCtaCteLovAplicacionesAll(long limit,
			long offset, BigDecimal nrocomprobante, BigDecimal idempresa)
			throws EJBException {
		String cQuery = "Select nrointerno_q_can,importe from proveedocancprov  where nrointerno_canc = "
				+ nrocomprobante.toString()
				+ " and idempresa ="
				+ idempresa.toString();
		log.info("CtaCteValoresAPP: " + cQuery);
		List vecSalida = getLista(cQuery);
		return vecSalida;

	}

	// 20111012 - CAMI - Comprobantes especiales -->

	public List getContabletipificacion() throws EJBException {

		String cQuery = "Select idtipo,tipo from contabletipificacion where mostrar = 'S' order by 2";
		List vecSalida = getLista(cQuery);
		return vecSalida;

	}

	public List getCuentasInfiPlan2All(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		String cQuery = "Select idcuenta,cuenta,inputable,nivel,ajustable,resultado,ejercicio,idempresa from contableinfiplan "
				+ "where idempresa ="
				+ idempresa.toString()
				+ " and ejercicio = (select ejercicio from contableejercicios where activo = 'S')ORDER BY 2  LIMIT "
				+ limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// 20120528 - CAMI - Agregado el filtro empresa
	public List getCuentasInfiPlan2Ocu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		String cQuery = "Select idcuenta,cuenta,inputable,nivel,ajustable,resultado,ejercicio,idempresa from contableinfiplan "
				+ "where (idcuenta::varchar like ('%"
				+ ocurrencia.toUpperCase().toString()
				+ "%') or upper(cuenta) like ('%"
				+ ocurrencia.toUpperCase().toString()
				+ "%'))  and ejercicio= (select ejercicio from contableejercicios where activo = 'S') and idempresa ="
				+ idempresa.toString()
				+ "ORDER BY 2  LIMIT "
				+ limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;

	}

	public String getTipificacionContable(String idtipo) throws EJBException {

		ResultSet rsSalida = null;
		String tipo = "";
		String cQuery = "Select tipo from contabletipificacion where idtipo = '"
				+ idtipo + "'";
		Statement statement;
		try {
			statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida != null) {
				if (rsSalida.next()) {
					tipo = rsSalida.getString("tipo");
				} else
					log.warn("(RSN)getTipificacionContable: no existe data.");
			} else
				log
						.warn("(RSN)getTipificacionContable: error al capturar data.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error("public String getTipificacionContable(String idtipo): "
					+ e);
		}
		return tipo;
	}

	public String capturaComprobantesProvContableCreate(BigDecimal idproveedor,
			Timestamp fechamov, BigDecimal sucursal, BigDecimal comprob,
			BigDecimal tipomov, String tipomovs, BigDecimal importe,
			BigDecimal saldo, BigDecimal idcondicionpago, Timestamp fecha_subd,
			BigDecimal retoque, java.sql.Date fechavto, int ejercicioactivo,
			String usuarioalt, Hashtable htPlanesContables,
			BigDecimal idempresa, String obscontable) throws EJBException,
			SQLException {

		String salida = "OK";
		String qDML = "";
		int flagFilasAfectadas = 0;
		PreparedStatement statement;
		Statement st;
		BigDecimal nrointerno = BigDecimal.valueOf(-1);
		Enumeration en;
		try {
			log.info("ENTRANDO EN LA GRABACION CORRECTA DEL COMPROBANTE...");
			dbconn.setAutoCommit(false);
			if (!getExisteDocumento(idproveedor, sucursal, comprob, tipomov,
					idempresa)) {

				tipomovs = getTipoComprobante(tipomov, tipomovs);

				obscontable = tipomovs + ": Suc: " + sucursal + " Comprob: "
						+ comprob + " Det: " + obscontable;

				nrointerno = GeneralBean.getContador(new BigDecimal(9),
						idempresa, dbconn);

				if (nrointerno.longValue() == -1)
					throw new SQLException(
							"Imposible recuperar nrointerno movimiento proveedor: ("
									+ nrointerno + ")");
				// dejar tal cual
				log.info("proveedomovprov");
				qDML = "INSERT INTO proveedomovprov (nrointerno,idproveedor,fechamov,sucursal,comprob,tipomov,tipomovs,";
				qDML += " importe,saldo,idcondicionpago,fecha_subd,retoque,fechavto,usuarioalt, idempresa, obscontable )";
				qDML += " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				statement = dbconn.prepareStatement(qDML);
				statement.setBigDecimal(1, nrointerno);
				statement.setBigDecimal(2, idproveedor);
				statement.setTimestamp(3, fechamov);
				statement.setBigDecimal(4, sucursal);
				statement.setBigDecimal(5, comprob);
				statement.setBigDecimal(6, tipomov);
				statement.setString(7, tipomovs);
				statement.setBigDecimal(8, importe);
				statement.setBigDecimal(9, importe);
				statement.setBigDecimal(10, idcondicionpago);
				statement.setTimestamp(11, fecha_subd);
				statement.setBigDecimal(12, retoque);
				statement.setDate(13, fechavto);
				statement.setString(14, usuarioalt);
				statement.setBigDecimal(15, idempresa);
				statement.setString(16, obscontable);
				flagFilasAfectadas = statement.executeUpdate();
				if (flagFilasAfectadas != 0) {
					// nrointerno = getValorSequencia("seq_proveedomovprov");
					qDML = "INSERT INTO proveedocontprov (cuenta_con,compr_con,import_con,nroiva_con,";
					qDML += " centcost,centcost2,detalle,usuarioalt,idempresa, obscontable )";
					qDML += " VALUES (?,?,?,?,?,?,?,?,?, ?)";

					statement = dbconn.prepareStatement(qDML);

					// MODIFICAR:bucle por los string[] tengo que cambiar los ht
					// por los string que vienen por parametro y listo.
					//
					Enumeration enume = htPlanesContables.elements();
					enume = htPlanesContables.keys();
					while (enume.hasMoreElements()) {
						Object clave = enume.nextElement();
						String[] vecValor = (String[]) htPlanesContables
								.get(clave);
						BigDecimal import_con = new BigDecimal(vecValor[11]
								.trim());
						statement.setBigDecimal(1, new BigDecimal(vecValor[0]
								.trim()));
						statement.setBigDecimal(2, nrointerno);
						statement.setBigDecimal(3, import_con);
						statement.setString(4, vecValor[9]);
						statement.setBigDecimal(5, null);
						statement.setBigDecimal(6, null);
						statement.setString(7, null);
						statement.setString(8, usuarioalt);
						statement.setBigDecimal(9, idempresa);
						statement.setString(10, obscontable);
						flagFilasAfectadas = statement.executeUpdate();
						if (flagFilasAfectadas == 0) {
							salida = "E-1.0: Transaccion Abortada Generando Asiento(MOVCONTPROV).";
							log.warn(salida);
							break;
						}

					}
					// (ctapasivo)
					st = dbconn.createStatement();
					ResultSet rsProveedPK = st
							.executeQuery("Select * from proveedoproveed where idempresa="
									+ idempresa
									+ " and idproveedor = "
									+ idproveedor);
					BigDecimal cuentaTotal = new BigDecimal(0);
					if (rsProveedPK.next()) {
						cuentaTotal = rsProveedPK.getBigDecimal("ctapasivo");
					}
					statement.setBigDecimal(1, cuentaTotal);
					statement.setBigDecimal(2, nrointerno);
					statement.setBigDecimal(3, importe);
					statement.setString(4, "T"); // es el total siempre
					statement.setBigDecimal(5, null);
					statement.setBigDecimal(6, null);
					statement.setString(7, null);
					statement.setString(8, usuarioalt);
					statement.setBigDecimal(9, idempresa);
					statement.setString(10, obscontable);
					flagFilasAfectadas = statement.executeUpdate();
					log.info("" + flagFilasAfectadas);

				} else {
					// no inserta en proveedomovprov
					salida = "E-0.0.1: Transaccion Abortada (PROVEEDOMOVPROV). ";
					log.warn(salida);
				}
			} else {
				salida = "El Nro. de Documento ya fue ingresado para el proveedor. ";
				log.warn(salida);
			}
		} catch (Exception e) {
			// TODO: handle exception
			salida = "E-1000: Ocurrio Excepcion Mientras Se Actualizaban las C.Contables.";
			log.error("capturaComprobantesProvContableCreate(...): " + e);
		}
		if (salida.equalsIgnoreCase("OK")) {
			salida = "" + nrointerno;
			dbconn.commit();
		} else
			dbconn.rollback();
		dbconn.setAutoCommit(true);
		return salida;
	}

	// <--

	/**
	 * Metodos para la entidad: proveedoPlantilladocEsp Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Wed Aug 07 11:00:12 ART 2013
	 */

	public List getProveedoPlantilladocEspActivas(BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT idplantilla,plantilla,observaciones,activa,idempresa,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM proveedoplantilladocesp "
				+ "WHERE activa = 'S' AND  idempresa = " + idempresa.toString()
				+ "  ORDER BY 2 ;";
		List vecSalida = new ArrayList();
		try {
			
			vecSalida = getLista(cQuery);
			
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getProveedoPlantilladocEspActivas()  "
							+ ex);
		}
		return vecSalida;
	}


}
