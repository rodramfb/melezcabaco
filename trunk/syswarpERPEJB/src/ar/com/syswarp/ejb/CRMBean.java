package ar.com.syswarp.ejb;

import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.ejb.CreateException;
import javax.ejb.Stateless;

import java.io.*;
import java.math.*;
import java.sql.*;
import java.sql.Date;

import org.postgresql.*;
//import javax.sql.*;
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
 * @ejb.bean name="CRM" display-name="Name for CRM" description="Description for
 *           Clientes" jndi-name="ejb/CRM" type="Stateless" view-type="remote"
 */
@Stateless
public class CRMBean implements CRM {

	/** The session context */
	private SessionContext context;

	GeneralBean gb = new GeneralBean();

	/* conexion a la base de datos */

	private Connection dbconn;;

	static Logger log = Logger.getLogger(CRMBean.class);

	private Connection conexion;

	private Properties props;

	private String url;

	private String clase;

	private String usuario;

	private String clave;

	public CRMBean() {
		super();
		try {
			props = new Properties();
			props.load(CRMBean.class.getResourceAsStream("system.properties"));

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

	public CRMBean(Connection dbconn) {
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

	public long getTotalEntidadFiltroAlias(String entidad, String filtro,
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
				+ entidad + filtro + " AND " + entidad + ".idempresa = "
				+ idempresa.toString();
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				total = rsSalida.getLong("total");
			} else {
				log
						.warn("getTotalEntidadFiltroAlias()- Error al recuperar total: "
								+ entidad);
			}
		} catch (SQLException sqlException) {
			log.error("getTotalEntidadFiltroAlias()- Error SQL: "
					+ sqlException);
		} catch (Exception ex) {
			log.error("getTotalEntidadFiltroAlias()- Salida por exception: "
					+ ex);
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

	// Tipos de Clientes
	public List getCrmtiposclientesAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " SELECT idtipocliente,tipocliente,observaciones,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM CRMTIPOSCLIENTES where idempresa = "
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
			log.error("Error SQL en el metodo : getCrmtiposclientesAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmtiposclientesAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)
	public List getCrmtiposclientesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idtipocliente,tipocliente,observaciones,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM CRMTIPOSCLIENTES "
				+ " where idempresa= "
				+ idempresa.toString()
				+ " and (idtipocliente::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " tipocliente LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " UPPER(observaciones) LIKE '%"
				+ ocurrencia.toUpperCase()
				+ "%') "
				+ " ORDER BY 2  LIMIT "
				+ limit
				+ " OFFSET  "
				+ offset
				+ ";";
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
					.error("Error SQL en el metodo : getCrmtiposclientesOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmtiposclientesOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)
	public List getCrmtiposclientesPK(BigDecimal idtipocliente,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idtipocliente,tipocliente,observaciones,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM CRMTIPOSCLIENTES "
				+ " WHERE idtipocliente = "
				+ idtipocliente.toString()
				+ " and idempresa = " + idempresa.toString();
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
					.error("Error SQL en el metodo : getCrmtiposclientesPK( BigDecimal idtipocliente ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmtiposclientesPK( BigDecimal idtipocliente )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String crmtiposclientesDelete(BigDecimal idtipocliente,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM CRMTIPOSCLIENTES "
				+ " WHERE idtipocliente = " + idtipocliente.toString()
				+ " and idempresa = " + idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM CRMTIPOSCLIENTES "
						+ " WHERE idtipocliente = " + idtipocliente.toString()
						+ " and idempresa = " + idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : crmtiposclientesDelete( BigDecimal idtipocliente ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: crmtiposclientesDelete( BigDecimal idtipocliente )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String crmtiposclientesCreate(String tipocliente,
			String observaciones, BigDecimal idempresa, String usuarioalt)
			throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (tipocliente == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipocliente ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (tipocliente.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipocliente ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO CRMTIPOSCLIENTES(tipocliente, observaciones, idempresa, usuarioalt ) VALUES (?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, tipocliente);
				insert.setString(2, observaciones);
				insert.setBigDecimal(3, idempresa);
				insert.setString(4, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String crmtiposclientesCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String crmtiposclientesCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String crmtiposclientesCreateOrUpdate(BigDecimal idtipocliente,
			String tipocliente, String observaciones, String usuarioact,
			BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idtipocliente == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipocliente ";
		if (tipocliente == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipocliente ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (tipocliente.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipocliente ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM crmtiposclientes "
					+ " WHERE idtipocliente = " + idtipocliente.toString()
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
					sql = "UPDATE CRMTIPOSCLIENTES SET tipocliente=?, observaciones=?, usuarioact=?, fechaact=? WHERE idtipocliente=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, tipocliente);
					insert.setString(2, observaciones);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, idtipocliente);
					insert.setBigDecimal(6, idempresa);
				} else {
					String ins = "INSERT INTO CRMTIPOSCLIENTES(tipocliente, observaciones, idempresa, usuarioalt ) VALUES (?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, tipocliente);
					insert.setString(2, observaciones);
					insert.setBigDecimal(3, idempresa);
					insert.setString(4, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String crmtiposclientesCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String crmtiposclientesCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String crmtiposclientesUpdate(BigDecimal idtipocliente,
			String tipocliente, String observaciones, String usuarioact,
			BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idtipocliente == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipocliente ";
		if (tipocliente == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipocliente ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (tipocliente.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipocliente ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM crmtiposclientes "
					+ " WHERE idtipocliente = " + idtipocliente.toString()
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
					sql = "UPDATE CRMTIPOSCLIENTES SET tipocliente=?, observaciones=?, usuarioact=?, fechaact=? WHERE idtipocliente=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, tipocliente);
					insert.setString(2, observaciones);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, idtipocliente);
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
			log.error("Error SQL public String crmtiposclientesUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String crmtiposclientesUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// clasificacion de clientes
	public List getCrmclasificacionclientesAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " SELECT  cc.idclasificacioncliente,cc.clasificacioncliente,tc.tipocliente,cc.observaciones,cc.usuarioalt,cc.usuarioact,cc.fechaalt,cc.fechaact FROM CRMCLASIFICACIONCLIENTES  cc, CRMTIPOSCLIENTES  tc where tc.idtipocliente =  cc.idtipocliente  and tc.idempresa =  cc.idempresa and cc.idempresa = "
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
			log
					.error("Error SQL en el metodo : getCrmclasificacionclientesAll() "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmclasificacionclientesAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getCrmclasificacionclientesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " SELECT  idclasificacioncliente,clasificacioncliente,tipocliente,observaciones,usuarioalt,usuarioact,fechaalt,fechaact FROM vcrmclasificacionclientes where idempresa = "
				+ idempresa.toString()
				+ " and (idclasificacioncliente::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' or "
				+ " clasificacioncliente LIKE '%"
				+ ocurrencia.toUpperCase()
				+ "%' or "
				+ " tipocliente LIKE '%"
				+ ocurrencia.toUpperCase()
				+ "%' OR "
				+ " UPPER(observaciones) LIKE '%"
				+ ocurrencia.toUpperCase()
				+ "%') "
				+ " ORDER BY 2  LIMIT "
				+ limit + " OFFSET  " + offset + ";";
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
					.error("Error SQL en el metodo : getCrmclasificacionclientesOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmclasificacionclientesOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getCrmclasificacionclientesPK(
			BigDecimal idclasificacioncliente, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  cc.idclasificacioncliente,cc.clasificacioncliente,cc.idtipocliente,tc.tipocliente,cc.observaciones,cc.idempresa,cc.usuarioalt,cc.usuarioact,cc.fechaalt,cc.fechaact FROM CRMCLASIFICACIONCLIENTES  cc, CRMTIPOSCLIENTES  tc where tc.idtipocliente =  cc.idtipocliente  and tc.idempresa =  cc.idempresa "
				+ " and idclasificacioncliente = "
				+ idclasificacioncliente.toString()
				+ " and cc.idempresa = "
				+ idempresa.toString();
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
					.error("Error SQL en el metodo : getCrmclasificacionclientesPK( BigDecimal idclasificacioncliente ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmclasificacionclientesPK( BigDecimal idclasificacioncliente )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String crmclasificacionclientesDelete(
			BigDecimal idclasificacioncliente, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM CRMCLASIFICACIONCLIENTES "
				+ " WHERE idclasificacioncliente = "
				+ idclasificacioncliente.toString() + " and idempresa = "
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM CRMCLASIFICACIONCLIENTES "
						+ " WHERE idclasificacioncliente = "
						+ idclasificacioncliente.toString()
						+ " and idempresa = " + idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : crmclasificacionclientesDelete( BigDecimal idclasificacioncliente ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: crmclasificacionclientesDelete( BigDecimal idclasificacioncliente )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String crmclasificacionclientesCreate(String clasificacioncliente,
			BigDecimal idtipocliente, String observaciones,
			BigDecimal idempresa, String usuarioalt) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (clasificacioncliente == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: clasificacioncliente ";
		if (idtipocliente == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipocliente ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (clasificacioncliente.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: clasificacioncliente ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO CRMCLASIFICACIONCLIENTES(clasificacioncliente, idtipocliente, observaciones, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, clasificacioncliente);
				insert.setBigDecimal(2, idtipocliente);
				insert.setString(3, observaciones);
				insert.setBigDecimal(4, idempresa);
				insert.setString(5, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String crmclasificacionclientesCreate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String crmclasificacionclientesCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String crmclasificacionclientesCreateOrUpdate(
			BigDecimal idclasificacioncliente, String clasificacioncliente,
			BigDecimal idtipocliente, String observaciones, String usuarioact,
			BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idclasificacioncliente == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idclasificacioncliente ";
		if (clasificacioncliente == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: clasificacioncliente ";
		if (idtipocliente == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipocliente ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (clasificacioncliente.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: clasificacioncliente ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM crmclasificacionclientes "
					+ " WHERE idclasificacioncliente = "
					+ idclasificacioncliente.toString() + " and idempresa = "
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
					sql = "UPDATE CRMCLASIFICACIONCLIENTES SET clasificacioncliente=?, idtipocliente=?, observaciones=?, usuarioact=?, fechaact=? WHERE idclasificacioncliente=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, clasificacioncliente);
					insert.setBigDecimal(2, idtipocliente);
					insert.setString(3, observaciones);
					insert.setString(4, usuarioact);
					insert.setTimestamp(5, fechaact);
					insert.setBigDecimal(6, idclasificacioncliente);
					insert.setBigDecimal(7, idempresa);
				} else {
					String ins = "INSERT INTO CRMCLASIFICACIONCLIENTES(clasificacioncliente, idtipocliente, observaciones, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, clasificacioncliente);
					insert.setBigDecimal(2, idtipocliente);
					insert.setString(3, observaciones);
					insert.setBigDecimal(4, idempresa);
					insert.setString(5, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String crmclasificacionclientesCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String crmclasificacionclientesCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String crmclasificacionclientesUpdate(
			BigDecimal idclasificacioncliente, String clasificacioncliente,
			BigDecimal idtipocliente, String observaciones, String usuarioact,
			BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idclasificacioncliente == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idclasificacioncliente ";
		if (clasificacioncliente == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: clasificacioncliente ";
		if (idtipocliente == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipocliente ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (clasificacioncliente.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: clasificacioncliente ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM crmclasificacionclientes "
					+ " WHERE idclasificacioncliente = "
					+ idclasificacioncliente.toString() + " and idempresa = "
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
					sql = "UPDATE CRMCLASIFICACIONCLIENTES SET clasificacioncliente=?, idtipocliente=?, observaciones=?, usuarioact=?, fechaact=? WHERE idclasificacioncliente=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, clasificacioncliente);
					insert.setBigDecimal(2, idtipocliente);
					insert.setString(3, observaciones);
					insert.setString(4, usuarioact);
					insert.setTimestamp(5, fechaact);
					insert.setBigDecimal(6, idclasificacioncliente);
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
					.error("Error SQL public String crmclasificacionclientesUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String crmclasificacionclientesUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// Fuente de Captacion
	public List getCrmfuentecaptacionAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idfuente,fuente,valorpresupuesto,valorunitario,usuarioalt,usuarioact,fechaalt,fechaact FROM CRMFUENTECAPTACION where idempresa = "
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
			log.error("Error SQL en el metodo : getCrmfuentecaptacionAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmfuentecaptacionAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getCrmfuentecaptacionOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idfuente,fuente,valorpresupuesto,valorunitario,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM CRMFUENTECAPTACION "
				+ " where idempresa= "
				+ idempresa.toString()
				+ " and (idfuente::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " fuente LIKE '%"
				+ ocurrencia
				+ "%') "
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
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
					.error("Error SQL en el metodo : getCrmfuentecaptacionOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmfuentecaptacionOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getCrmfuentecaptacionPK(BigDecimal idfuente,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idfuente,fuente,valorpresupuesto,valorunitario,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM CRMFUENTECAPTACION "
				+ " WHERE idfuente = "
				+ idfuente.toString()
				+ " and idempresa = " + idempresa.toString();
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
					.error("Error SQL en el metodo : getCrmfuentecaptacionPK( BigDecimal idfuente ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmfuentecaptacionPK( BigDecimal idfuente )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String crmfuentecaptacionDelete(BigDecimal idfuente,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM CRMFUENTECAPTACION "
				+ " WHERE idfuente = " + idfuente.toString()
				+ " and idempresa = " + idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM CRMFUENTECAPTACION "
						+ " WHERE idfuente = " + idfuente.toString()
						+ " and idempresa = " + idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : crmfuentecaptacionDelete( BigDecimal idfuente ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: crmfuentecaptacionDelete( BigDecimal idfuente )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String crmfuentecaptacionCreate(String fuente,
			BigDecimal valorpresupuesto, BigDecimal valorunitario,
			BigDecimal idempresa, String usuarioalt) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (fuente == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fuente ";
		if (valorpresupuesto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: valorpresupuesto ";
		if (valorunitario == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: valorunitario ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (fuente.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: fuente ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO CRMFUENTECAPTACION(fuente, valorpresupuesto, valorunitario, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, fuente);
				insert.setBigDecimal(2, valorpresupuesto);
				insert.setBigDecimal(3, valorunitario);
				insert.setBigDecimal(4, idempresa);
				insert.setString(5, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String crmfuentecaptacionCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String crmfuentecaptacionCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String crmfuentecaptacionCreateOrUpdate(BigDecimal idfuente,
			String fuente, BigDecimal valorpresupuesto,
			BigDecimal valorunitario, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idfuente == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idfuente ";
		if (fuente == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fuente ";
		if (valorpresupuesto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: valorpresupuesto ";
		if (valorunitario == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: valorunitario ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (fuente.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: fuente ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM crmfuentecaptacion "
					+ " WHERE idfuente = " + idfuente.toString()
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
					sql = "UPDATE CRMFUENTECAPTACION SET fuente=?, valorpresupuesto=?, valorunitario=?, usuarioact=?, fechaact=? WHERE idfuente=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, fuente);
					insert.setBigDecimal(2, valorpresupuesto);
					insert.setBigDecimal(3, valorunitario);
					insert.setString(4, usuarioact);
					insert.setTimestamp(5, fechaact);
					insert.setBigDecimal(6, idfuente);
					insert.setBigDecimal(7, idempresa);
				} else {
					String ins = "INSERT INTO CRMFUENTECAPTACION(fuente, valorpresupuesto, valorunitario, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, fuente);
					insert.setBigDecimal(2, valorpresupuesto);
					insert.setBigDecimal(3, valorunitario);
					insert.setBigDecimal(4, idempresa);
					insert.setString(5, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String crmfuentecaptacionCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String crmfuentecaptacionCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String crmfuentecaptacionUpdate(BigDecimal idfuente, String fuente,
			BigDecimal valorpresupuesto, BigDecimal valorunitario,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idfuente == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idfuente ";
		if (fuente == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fuente ";
		if (valorpresupuesto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: valorpresupuesto ";
		if (valorunitario == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: valorunitario ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (fuente.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: fuente ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM crmfuentecaptacion "
					+ " WHERE idfuente = " + idfuente.toString()
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
					sql = "UPDATE CRMFUENTECAPTACION SET fuente=?, valorpresupuesto=?, valorunitario=?, usuarioact=?, fechaact=? WHERE idfuente=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, fuente);
					insert.setBigDecimal(2, valorpresupuesto);
					insert.setBigDecimal(3, valorunitario);
					insert.setString(4, usuarioact);
					insert.setTimestamp(5, fechaact);
					insert.setBigDecimal(6, idfuente);
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
			log.error("Error SQL public String crmfuentecaptacionUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String crmfuentecaptacionUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// tipo de llamadas
	public List getCrmtiposllamadasAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " SELECT idtipollamada,tipollamada,usuarioalt,usuarioact,fechaalt,fechaact FROM CRMTIPOSLLAMADAS where idempresa =  "
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
			log.error("Error SQL en el metodo : getCrmtiposllamadasAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmtiposllamadasAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getCrmtiposllamadasOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idtipollamada,tipollamada,usuarioalt,usuarioact,fechaalt,fechaact FROM CRMTIPOSLLAMADAS "
				+ " where idempresa = "
				+ idempresa.toString()
				+ " and (idtipollamada::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " UPPER(tipollamada) LIKE '%"
				+ ocurrencia.toUpperCase()
				+ "%') "
				+ " ORDER BY 2  LIMIT "
				+ limit
				+ " OFFSET  "
				+ offset
				+ ";";
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
					.error("Error SQL en el metodo : getCrmtiposllamadasOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmtiposllamadasOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getCrmtiposllamadasPK(BigDecimal idtipollamada,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idtipollamada,tipollamada,usuarioalt,usuarioact,fechaalt,fechaact FROM CRMTIPOSLLAMADAS "
				+ " WHERE idtipollamada = "
				+ idtipollamada.toString()
				+ " and idempresa = " + idempresa.toString();
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
					.error("Error SQL en el metodo : getCrmtiposllamadasPK( BigDecimal idtipollamada ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmtiposllamadasPK( BigDecimal idtipollamada )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String crmtiposllamadasDelete(BigDecimal idtipollamada,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM CRMTIPOSLLAMADAS "
				+ " WHERE idtipollamada = " + idtipollamada.toString()
				+ " and idempresa = " + idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM CRMTIPOSLLAMADAS "
						+ " WHERE idtipollamada = " + idtipollamada.toString()
						+ " and idempresa = " + idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : crmtiposllamadasDelete( BigDecimal idtipollamada ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: crmtiposllamadasDelete( BigDecimal idtipollamada )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String crmtiposllamadasCreate(String tipollamada,
			BigDecimal idempresa, String usuarioalt) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (tipollamada == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipollamada ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (tipollamada.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipollamada ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO CRMTIPOSLLAMADAS(tipollamada, idempresa, usuarioalt ) VALUES (?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, tipollamada);
				insert.setBigDecimal(2, idempresa);
				insert.setString(3, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String crmtiposllamadasCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String crmtiposllamadasCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String crmtiposllamadasCreateOrUpdate(BigDecimal idtipollamada,
			String tipollamada, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idtipollamada == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipollamada ";
		if (tipollamada == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipollamada ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (tipollamada.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipollamada ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM crmtiposllamadas "
					+ " WHERE idtipollamada = " + idtipollamada.toString()
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
					sql = "UPDATE CRMTIPOSLLAMADAS SET tipollamada=?, usuarioact=?, fechaact=? WHERE idtipollamada=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, tipollamada);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idtipollamada);
					insert.setBigDecimal(5, idempresa);
				} else {
					String ins = "INSERT INTO CRMTIPOSLLAMADAS(tipollamada, idempresa, usuarioalt ) VALUES (?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, tipollamada);
					insert.setBigDecimal(2, idempresa);
					insert.setString(3, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String crmtiposllamadasCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String crmtiposllamadasCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String crmtiposllamadasUpdate(BigDecimal idtipollamada,
			String tipollamada, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idtipollamada == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipollamada ";
		if (tipollamada == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipollamada ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (tipollamada.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipollamada ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM crmtiposllamadas "
					+ " WHERE idtipollamada = " + idtipollamada.toString()
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
					sql = "UPDATE CRMTIPOSLLAMADAS SET tipollamada=?, usuarioact=?, fechaact=? WHERE idtipollamada=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, tipollamada);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idtipollamada);
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
			log.error("Error SQL public String crmtiposllamadasUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String crmtiposllamadasUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// individuos
	public List getCrmindividuosxusuarioAll(long limit, long offset,
			BigDecimal idempresa, String usu) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " "
				+ "SELECT idindividuos, razon_nombre, telefonos_part, celular, email, "
				+ "       domicilio_part, fechanacimiento, empresa, domicilio_laboral, "
				+ "       telefonos_empr, profesion, actividad, deportes, hobbies, "
				+ "       actividad_social, diario_lectura, revista_lectura, lugar_veraneo, "
				+ "       obseravaciones, usuario, tipocliente, clasificacioncliente,fuente, "
				+ "       idempresa, usuarioalt, usuarioact, fechaalt, fechaact "
				+ "  FROM vcrmindividuos  " + " WHERE idempresa = "
				+ idempresa.toString() + "   AND usuario = '"
				+ usu.toLowerCase() + "' ORDER BY 2  LIMIT " + limit
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
			log.error("Error SQL en el metodo : getCrmindividuosxusuarioAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmindividuosxusuarioAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getCrmindividuosxusuarioOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa, String usu)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idindividuos,razon_nombre,telefonos_part,celular,email,domicilio_part,fechanacimiento,"
				+ "       empresa,domicilio_laboral,telefonos_empr,profesion,actividad,deportes,hobbies,"
				+ "       actividad_social,diario_lectura,revista_lectura,lugar_veraneo,obseravaciones,usuario,"
				+ "       tipocliente,clasificacioncliente,fuente,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM vcrmindividuos " + " WHERE idempresa = "
				+ idempresa.toString() + " AND razon_nombre LIKE '"
				+ ocurrencia + "%' AND usuario = '" + usu.toLowerCase()
				+ "' ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
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
					.error("Error SQL en el metodo : getCrmindividuosxusuarioOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmindividuosxusuarioOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getCrmindividuosxusuarioFiltro(long limit, long offset,
			String filtro) throws EJBException {
		ResultSet rsSalida = null;
		// Alias necesario por JOIN en filtro ....
		String cQuery = ""
				+ "SELECT vcrmindividuos.idindividuos,vcrmindividuos.razon_nombre,vcrmindividuos.telefonos_part,"
				+ "       vcrmindividuos.celular,vcrmindividuos.email,vcrmindividuos.domicilio_part,vcrmindividuos.fechanacimiento,"
				+ "       vcrmindividuos.empresa,vcrmindividuos.domicilio_laboral,vcrmindividuos.telefonos_empr,"
				+ "       vcrmindividuos.profesion,vcrmindividuos.actividad,vcrmindividuos.deportes,vcrmindividuos.hobbies,"
				+ "       vcrmindividuos.actividad_social,vcrmindividuos.diario_lectura,vcrmindividuos.revista_lectura,"
				+ "       vcrmindividuos.lugar_veraneo,vcrmindividuos.obseravaciones,vcrmindividuos.usuario,"
				+ "       vcrmindividuos.tipocliente,vcrmindividuos.clasificacioncliente,vcrmindividuos.fuente,"
				+ "       vcrmindividuos.usuarioalt,vcrmindividuos.usuarioact,vcrmindividuos.fechaalt,vcrmindividuos.fechaact "
				+ "  FROM vcrmindividuos vcrmindividuos " + filtro
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";

		log.info("QUERY:");
		log.info(cQuery);

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
					.error("Error SQL en el metodo : getCrmindividuosxusuarioFiltro(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmindividuosxusuarioFiltro(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getCrmindividuosxusuarioPK(BigDecimal idindividuos,
			BigDecimal idempresa, String usu) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " " +
				"SELECT ind.idindividuos, ind.razon_nombre, ind.telefonos_part, ind.celular, ind.email, " +
				"       ind.domicilio_part, ind.fechanacimiento, ind.empresa, ind.domicilio_laboral, " +
				"       ind.telefonos_empr, ind.profesion, ind.actividad, ind.deportes, ind.hobbies, " +
				"       ind.actividad_social, ind.diario_lectura, ind.revista_lectura, ind.lugar_veraneo, " +
				"       ind.obseravaciones, ind.idusuario, gu.usuario, ind.idtipocliente, tc.tipocliente, " +
				"       ind.idclasificacioncliente, cc.clasificacioncliente, ind.idfuente, fc.fuente, " +
				"       ind.datosvehiculo, ind.idempresa, ind.usuarioalt, ind.usuarioact, ind.fechaalt, ind.fechaact " +
				"  FROM crmindividuos ind " +
				"       LEFT JOIN globalusuarios gu ON ind.idusuario = gu.idusuario AND gu.idempresa = ind.idempresa " +
				"       LEFT JOIN crmtiposclientes tc ON ind.idtipocliente = tc.idtipocliente AND tc.idempresa = ind.idempresa " +
				"       LEFT JOIN crmclasificacionclientes cc ON ind.idclasificacioncliente = cc.idclasificacioncliente AND cc.idempresa = ind.idempresa " +
				"       LEFT JOIN crmfuentecaptacion fc ON ind.idfuente = fc.idfuente AND fc.idempresa = fc.idempresa  "
				+ " where ind.idindividuos = "
				+ idindividuos.toString()
				+ " and UPPER(gu.usuario) = '"
				+ usu.toString().toUpperCase()
				+ "' and ind.idempresa = " + idempresa.toString();
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
					.error("Error SQL en el metodo : getCrmindividuosxusuarioPK( BigDecimal idindividuos ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmindividuosxusuarioPK( BigDecimal idindividuos )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String crmindividuosxusuarioDelete(BigDecimal idindividuos,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " SELECT * FROM CRMINDIVIDUOS ind LEFT JOIN globalusuarios gu ON ind.idusuario = gu.idusuario AND gu.idempresa = ind.idempresa  "
				+ " WHERE ind.idindividuos = "
				+ idindividuos.toString()
				+ " and ind.idempresa = " + idempresa.toString();
		// + " and gu.usuario = '" + idusuario.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = " DELETE FROM CRMINDIVIDUOS "
						+ " WHERE idindividuos = " + idindividuos.toString()
						+ " and idempresa = " + idempresa.toString();
				// + " and idusuario = " + idusuario.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : crmindividuosxusuarioDelete( BigDecimal idindividuos ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: crmindividuosxusuarioDelete( BigDecimal idindividuos )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String crmindividuosxusuarioCreate(String razon_nombre,
			String telefonos_part, String celular, String email,
			String domicilio_part, Timestamp fechanacimiento, String empresa,
			String domicilio_laboral, String telefonos_empr, String profesion,
			String actividad, String deportes, String hobbies,
			String actividad_social, String diario_lectura,
			String revista_lectura, String lugar_veraneo,
			String obseravaciones, String idusuario, String idtipocliente,
			String idclasificacioncliente, String idfuente,
			String datosvehiculo, BigDecimal idempresa, String usuarioalt)
			throws EJBException {
		String salida = "OK";
		BigDecimal idindividuo = null;
		// validaciones de datos:
		// 1. nulidad de campos
		if (razon_nombre == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: razon_nombre ";
		if (telefonos_part == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: telefonos_part ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (razon_nombre.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: razon_nombre ";
		if (telefonos_part.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: telefonos_part ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones

		try {
			if (salida.equalsIgnoreCase("OK")) {
				String ins = "INSERT INTO CRMINDIVIDUOS(razon_nombre, telefonos_part, celular, email, domicilio_part, fechanacimiento, empresa, domicilio_laboral, telefonos_empr, profesion, actividad, deportes, hobbies, actividad_social, diario_lectura, revista_lectura, lugar_veraneo, obseravaciones, idusuario, idtipocliente, idclasificacioncliente, idfuente, datosvehiculo, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, razon_nombre.toUpperCase());
				insert.setString(2, telefonos_part.toUpperCase());
				insert.setString(3, celular.toUpperCase());
				insert.setString(4, email.toUpperCase());
				insert.setString(5, domicilio_part.toUpperCase());
				insert.setTimestamp(6, fechanacimiento);
				insert.setString(7, empresa.toUpperCase());
				insert.setString(8, domicilio_laboral.toUpperCase());
				insert.setString(9, telefonos_empr.toUpperCase());
				insert.setString(10, profesion.toUpperCase());
				insert.setString(11, actividad.toUpperCase());
				insert.setString(12, deportes.toUpperCase());
				insert.setString(13, hobbies.toUpperCase());
				insert.setString(14, actividad_social.toUpperCase());
				insert.setString(15, diario_lectura.toUpperCase());
				insert.setString(16, revista_lectura.toUpperCase());
				insert.setString(17, lugar_veraneo.toUpperCase());
				insert.setString(18, obseravaciones.toUpperCase());
				if (idusuario != "" && !idusuario.equalsIgnoreCase("")) {
					insert.setBigDecimal(19, new BigDecimal(idusuario));
				} else {
					insert.setBigDecimal(19, null);
				}
				if (idtipocliente != "" && !idtipocliente.equalsIgnoreCase("")) {
					insert.setBigDecimal(20, new BigDecimal(idtipocliente));
				} else {
					insert.setBigDecimal(20, null);
				}
				if (idclasificacioncliente != ""
						&& !idclasificacioncliente.equalsIgnoreCase("")) {
					insert.setBigDecimal(21, new BigDecimal(
							idclasificacioncliente));
				} else {
					insert.setBigDecimal(21, null);
				}
				if (idfuente != "" && !idfuente.equalsIgnoreCase("")) {
					insert.setBigDecimal(22, new BigDecimal(idfuente));
				} else {
					insert.setBigDecimal(22, null);
				}

				insert.setString(23, datosvehiculo.toUpperCase());
				insert.setBigDecimal(24, idempresa);
				insert.setString(25, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					idindividuo = GeneralBean.getValorSequencia(
							"seq_crmindividuos", dbconn);

				salida = idindividuo == null ? "-1" : idindividuo.toString();
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String crmindividuosxusuarioCreate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String crmindividuosxusuarioCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String crmindividuosxusuarioCreateOrUpdate(BigDecimal idindividuos,
			String razon_nombre, String telefonos_part, String celular,
			String email, String domicilio_part, Timestamp fechanacimiento,
			String empresa, String domicilio_laboral, String telefonos_empr,
			String profesion, String actividad, String deportes,
			String hobbies, String actividad_social, String diario_lectura,
			String revista_lectura, String lugar_veraneo,
			String obseravaciones, String idusuario, String idtipocliente,
			String idclasificacioncliente, String idfuente,
			String datosvehiculo, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idindividuos == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idindividuos ";
		if (razon_nombre == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: razon_nombre ";
		if (telefonos_part == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: telefonos_part ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (razon_nombre.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: razon_nombre ";
		if (telefonos_part.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: telefonos_part ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM crmindividuos "
					+ " WHERE idindividuos = " + idindividuos.toString()
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
					sql = "UPDATE CRMINDIVIDUOS SET razon_nombre=?, telefonos_part=?, celular=?, email=?, domicilio_part=?, fechanacimiento=?, empresa=?, domicilio_laboral=?, telefonos_empr=?, profesion=?, actividad=?, deportes=?, hobbies=?, actividad_social=?, diario_lectura=?, revista_lectura=?, lugar_veraneo=?, obseravaciones=?, idusuario=?, idtipocliente=?, idclasificacioncliente=?, idfuente=?, datosvehiculo=?, usuarioact=?, fechaact=? WHERE idindividuos=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, razon_nombre.toUpperCase());
					insert.setString(2, telefonos_part.toUpperCase());
					insert.setString(3, celular.toUpperCase());
					insert.setString(4, email.toUpperCase());
					insert.setString(5, domicilio_part.toUpperCase());
					insert.setTimestamp(6, fechanacimiento);
					insert.setString(7, empresa.toUpperCase());
					insert.setString(8, domicilio_laboral.toUpperCase());
					insert.setString(9, telefonos_empr.toUpperCase());
					insert.setString(10, profesion.toUpperCase());
					insert.setString(11, actividad.toUpperCase());
					insert.setString(12, deportes.toUpperCase());
					insert.setString(13, hobbies.toUpperCase());
					insert.setString(14, actividad_social.toUpperCase());
					insert.setString(15, diario_lectura.toUpperCase());
					insert.setString(16, revista_lectura.toUpperCase());
					insert.setString(17, lugar_veraneo.toUpperCase());
					insert.setString(18, obseravaciones.toUpperCase());
					if (idusuario != "" && !idusuario.equalsIgnoreCase("")) {
						insert.setBigDecimal(19, new BigDecimal(idusuario));
					} else {
						insert.setBigDecimal(19, null);
					}
					if (idtipocliente != ""
							&& !idtipocliente.equalsIgnoreCase("")) {
						insert.setBigDecimal(20, new BigDecimal(idtipocliente));
					} else {
						insert.setBigDecimal(20, null);
					}
					if (idclasificacioncliente != ""
							&& !idclasificacioncliente.equalsIgnoreCase("")) {
						insert.setBigDecimal(21, new BigDecimal(
								idclasificacioncliente));
					} else {
						insert.setBigDecimal(21, null);
					}
					if (idfuente != "" && !idfuente.equalsIgnoreCase("")) {
						insert.setBigDecimal(22, new BigDecimal(idfuente));
					} else {
						insert.setBigDecimal(22, null);
					}
					insert.setString(23, datosvehiculo);
					insert.setString(24, usuarioact);
					insert.setTimestamp(25, fechaact);
					insert.setBigDecimal(26, idindividuos);
					insert.setBigDecimal(27, idempresa);
				} else {
					String ins = "INSERT INTO CRMINDIVIDUOS(razon_nombre, telefonos_part, celular, email, domicilio_part, fechanacimiento, empresa, domicilio_laboral, telefonos_empr, profesion, actividad, deportes, hobbies, actividad_social, diario_lectura, revista_lectura, lugar_veraneo, obseravaciones, idusuario, idtipocliente, idclasificacioncliente, idfuente, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, razon_nombre.toUpperCase());
					insert.setString(2, telefonos_part.toUpperCase());
					insert.setString(3, celular.toUpperCase());
					insert.setString(4, email.toUpperCase());
					insert.setString(5, domicilio_part.toUpperCase());
					insert.setTimestamp(6, fechanacimiento);
					insert.setString(7, empresa.toUpperCase());
					insert.setString(8, domicilio_laboral.toUpperCase());
					insert.setString(9, telefonos_empr.toUpperCase());
					insert.setString(10, profesion.toUpperCase());
					insert.setString(11, actividad.toUpperCase());
					insert.setString(12, deportes.toUpperCase());
					insert.setString(13, hobbies.toUpperCase());
					insert.setString(14, actividad_social.toUpperCase());
					insert.setString(15, diario_lectura.toUpperCase());
					insert.setString(16, revista_lectura.toUpperCase());
					insert.setString(17, lugar_veraneo.toUpperCase());
					insert.setString(18, obseravaciones.toUpperCase());
					if (idusuario != "" && !idusuario.equalsIgnoreCase("")) {
						insert.setBigDecimal(19, new BigDecimal(idusuario));
					} else {
						insert.setBigDecimal(19, null);
					}
					if (idtipocliente != ""
							&& !idtipocliente.equalsIgnoreCase("")) {
						insert.setBigDecimal(20, new BigDecimal(idtipocliente));
					} else {
						insert.setBigDecimal(20, null);
					}
					if (idclasificacioncliente != ""
							&& !idclasificacioncliente.equalsIgnoreCase("")) {
						insert.setBigDecimal(21, new BigDecimal(
								idclasificacioncliente));
					} else {
						insert.setBigDecimal(21, null);
					}
					if (idfuente != "" && !idfuente.equalsIgnoreCase("")) {
						insert.setBigDecimal(22, new BigDecimal(idfuente));
					} else {
						insert.setBigDecimal(22, null);
					}
					insert.setString(23, datosvehiculo);
					insert.setString(24, usuarioalt);
					insert.setBigDecimal(25, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String crmindividuosxusuarioCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String crmindividuosxusuarioCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String crmindividuosxusuarioUpdate(BigDecimal idindividuos,
			String razon_nombre, String telefonos_part, String celular,
			String email, String domicilio_part, Timestamp fechanacimiento,
			String empresa, String domicilio_laboral, String telefonos_empr,
			String profesion, String actividad, String deportes,
			String hobbies, String actividad_social, String diario_lectura,
			String revista_lectura, String lugar_veraneo,
			String obseravaciones, String idusuario, String idtipocliente,
			String idclasificacioncliente, String idfuente,
			String datosvehiculo, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idindividuos == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idindividuos ";
		if (razon_nombre == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: razon_nombre ";
		if (telefonos_part == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: telefonos_part ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (razon_nombre.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: razon_nombre ";
		if (telefonos_part.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: telefonos_part ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM crmindividuos "
					+ " WHERE idindividuos = " + idindividuos.toString()
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
					sql = "UPDATE CRMINDIVIDUOS SET razon_nombre=?, telefonos_part=?, celular=?, email=?, domicilio_part=?, fechanacimiento=?, empresa=?, domicilio_laboral=?, telefonos_empr=?, profesion=?, actividad=?, deportes=?, hobbies=?, actividad_social=?, diario_lectura=?, revista_lectura=?, lugar_veraneo=?, obseravaciones=?, idusuario=?, idtipocliente=?, idclasificacioncliente=?, idfuente=?, datosvehiculo=?, usuarioact=?, fechaact=? WHERE idindividuos=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, razon_nombre.toUpperCase());
					insert.setString(2, telefonos_part.toUpperCase());
					insert.setString(3, celular.toUpperCase());
					insert.setString(4, email.toUpperCase());
					insert.setString(5, domicilio_part.toUpperCase());
					insert.setTimestamp(6, fechanacimiento);
					insert.setString(7, empresa.toUpperCase());
					insert.setString(8, domicilio_laboral.toUpperCase());
					insert.setString(9, telefonos_empr.toUpperCase());
					insert.setString(10, profesion.toUpperCase());
					insert.setString(11, actividad.toUpperCase());
					insert.setString(12, deportes.toUpperCase());
					insert.setString(13, hobbies.toUpperCase());
					insert.setString(14, actividad_social.toUpperCase());
					insert.setString(15, diario_lectura.toUpperCase());
					insert.setString(16, revista_lectura.toUpperCase());
					insert.setString(17, lugar_veraneo.toUpperCase());
					insert.setString(18, obseravaciones.toUpperCase());

					if (idusuario != null
							&& !idusuario.equalsIgnoreCase("null")
							&& !idusuario.equals("")) {
						insert.setBigDecimal(19, new BigDecimal(idusuario));
					} else {
						insert.setBigDecimal(19, null);
					}

					if (idtipocliente != null
							&& !idtipocliente.equalsIgnoreCase("null")
							&& !idtipocliente.equals("")) {
						insert.setBigDecimal(20, new BigDecimal(idtipocliente));
					} else {
						insert.setBigDecimal(20, null);
					}

					if (idclasificacioncliente != null
							&& !idclasificacioncliente.equalsIgnoreCase("null")
							&& !idclasificacioncliente.equals("")) {
						insert.setBigDecimal(21, new BigDecimal(
								idclasificacioncliente));
					} else {
						insert.setBigDecimal(21, null);
					}

					if (idfuente != null && !idfuente.equalsIgnoreCase("null")
							&& !idfuente.equals("")) {
						insert.setBigDecimal(22, new BigDecimal(idfuente));
					} else {
						insert.setBigDecimal(22, null);
					}

					insert.setString(23, datosvehiculo.toUpperCase());
					insert.setString(24, usuarioact);
					insert.setTimestamp(25, fechaact);
					insert.setBigDecimal(26, idindividuos);
					insert.setBigDecimal(27, idempresa);
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
					.error("Error SQL public String crmindividuosxusuarioUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String crmindividuosxusuarioUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// Nexos Familiares
	public List getCrmnexosfamiliaresAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idnexofamiliar,nexofamiliar,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM CRMNEXOSFAMILIARES where idempresa = "
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
			log.error("Error SQL en el metodo : getCrmnexosfamiliaresAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmnexosfamiliaresAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getCrmnexosfamiliaresOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idnexofamiliar,nexofamiliar,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM CRMNEXOSFAMILIARES "
				+ " where idempresa= "
				+ idempresa.toString()
				+ " and (idnexofamiliar::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " UPPER(nexofamiliar) LIKE '%"
				+ ocurrencia.toUpperCase()
				+ "%') "
				+ " ORDER BY 2  LIMIT "
				+ limit
				+ " OFFSET  "
				+ offset
				+ ";";
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
					.error("Error SQL en el metodo : getCrmnexosfamiliaresOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmnexosfamiliaresOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getCrmnexosfamiliaresPK(BigDecimal idnexofamiliar,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idnexofamiliar,nexofamiliar,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM CRMNEXOSFAMILIARES "
				+ " WHERE idnexofamiliar = "
				+ idnexofamiliar.toString()
				+ " and idempresa = " + idempresa.toString();
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
					.error("Error SQL en el metodo : getCrmnexosfamiliaresPK( BigDecimal idnexofamiliar ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmnexosfamiliaresPK( BigDecimal idnexofamiliar )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String crmnexosfamiliaresDelete(BigDecimal idnexofamiliar,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM CRMNEXOSFAMILIARES "
				+ " WHERE idnexofamiliar = " + idnexofamiliar.toString()
				+ " and idempresa = " + idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM CRMNEXOSFAMILIARES "
						+ " WHERE idnexofamiliar = "
						+ idnexofamiliar.toString() + " and idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : crmnexosfamiliaresDelete( BigDecimal idnexofamiliar ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: crmnexosfamiliaresDelete( BigDecimal idnexofamiliar )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String crmnexosfamiliaresCreate(String nexofamiliar,
			BigDecimal idempresa, String usuarioalt) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (nexofamiliar == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nexofamiliar ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (nexofamiliar.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: nexofamiliar ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO CRMNEXOSFAMILIARES(nexofamiliar, idempresa, usuarioalt ) VALUES (?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, nexofamiliar);
				insert.setBigDecimal(2, idempresa);
				insert.setString(3, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String crmnexosfamiliaresCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String crmnexosfamiliaresCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String crmnexosfamiliaresCreateOrUpdate(BigDecimal idnexofamiliar,
			String nexofamiliar, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idnexofamiliar == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idnexofamiliar ";
		if (nexofamiliar == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nexofamiliar ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (nexofamiliar.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: nexofamiliar ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM crmnexosfamiliares WHERE idnexofamiliar = "
					+ idnexofamiliar.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE CRMNEXOSFAMILIARES SET nexofamiliar=?, idempresa=?, usuarioact=?, fechaact=? WHERE idnexofamiliar=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, nexofamiliar);
					insert.setBigDecimal(2, idempresa);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, idnexofamiliar);
				} else {
					String ins = "INSERT INTO CRMNEXOSFAMILIARES(nexofamiliar, idempresa, usuarioalt ) VALUES (?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, nexofamiliar);
					insert.setBigDecimal(2, idempresa);
					insert.setString(3, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String crmnexosfamiliaresCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String crmnexosfamiliaresCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String crmnexosfamiliaresUpdate(BigDecimal idnexofamiliar,
			String nexofamiliar, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idnexofamiliar == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idnexofamiliar ";
		if (nexofamiliar == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nexofamiliar ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (nexofamiliar.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: nexofamiliar ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM crmnexosfamiliares "
					+ " WHERE idnexofamiliar = " + idnexofamiliar.toString()
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
					sql = "UPDATE CRMNEXOSFAMILIARES SET nexofamiliar=?, usuarioact=?, fechaact=? WHERE idnexofamiliar=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, nexofamiliar);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idnexofamiliar);
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
			log.error("Error SQL public String crmnexosfamiliaresUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String crmnexosfamiliaresUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// Familiares
	public List getCrmfamiliaresAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " "
				+ "SELECT fam.idfamiliar,ind.razon_nombre,anf.nexofamiliar,fam.nombre,fam.profesion,"
				+ "       fam.actividad,fam.email,fam.telefonos_part,fam.celular,fam.fechanacimiento,"
				+ "       fam.deportes,fam.hobbies,fam.actividad_social,fam.obseravaciones,fam.idempresa,"
				+ "       fam.usuarioalt,fam.usuarioact,fam.fechaalt,fam.fechaact  "
				+ "  FROM CRMFAMILIARES fam "
				+ "       LEFT JOIN crmindividuos ind ON fam.idindividuos = ind.idindividuos  AND fam.idempresa = ind.idempresa "
				+ "        LEFT JOIN crmnexosfamiliares anf ON fam.idnexofamiliar = anf.idnexofamiliar AND fam.idempresa = anf.idempresa  "
				+ " WHERE ind.idempresa = " + idempresa.toString()
				+ "ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
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
			log.error("Error SQL en el metodo : getCrmfamiliaresAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmfamiliaresAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getCrmfamiliaresOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " SELECT idfamiliar,razon_nombre,nexofamiliar,nombre,profesion,actividad,email,telefonos_part,celular,fechanacimiento,deportes,hobbies,actividad_social,obseravaciones,idempresa,usuarioalt,usuarioact,fechaalt,fechaact  FROM vcrmfamiliares "
				+ " where idempresa= "
				+ idempresa.toString()
				+ " and (idfamiliar::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " razon_nombre LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " nexofamiliar LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " nombre LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " profesion LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " actividad LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " email LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " telefonos_part LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " celular LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " fechanacimiento LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " deportes LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " hobbies LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " actividad_social LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " UPPER(obseravaciones) LIKE '%"
				+ ocurrencia.toUpperCase()
				+ "%') "
				+ " ORDER BY 2  LIMIT "
				+ limit
				+ " OFFSET  "
				+ offset
				+ ";";
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
					.error("Error SQL en el metodo : getCrmfamiliaresOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmfamiliaresOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)
	public List getCrmfamiliaresPK(BigDecimal idfamiliar, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " SELECT fam.idfamiliar, fam.idindividuos, ind.razon_nombre, fam.idnexofamiliar, anf.nexofamiliar, fam.nombre, fam.profesion, fam.actividad, fam.email, fam.telefonos_part, fam.celular, fam.fechanacimiento, fam.deportes, fam.hobbies, fam.actividad_social, fam.obseravaciones, fam.idempresa, fam.usuarioalt, fam.usuarioact, fam.fechaalt, fam.fechaact FROM CRMFAMILIARES fam LEFT JOIN crmindividuos ind ON fam.idindividuos = ind.idindividuos  AND fam.idempresa = ind.idempresa LEFT JOIN crmnexosfamiliares anf ON fam.idnexofamiliar = anf.idnexofamiliar AND fam.idempresa = anf.idempresa "
				+ " WHERE fam.idfamiliar = "
				+ idfamiliar.toString()
				+ " and fam.idempresa = " + idempresa.toString();
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
					.error("Error SQL en el metodo : getCrmfamiliaresPK( BigDecimal idfamiliar ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmfamiliaresPK( BigDecimal idfamiliar )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String crmfamiliaresDelete(BigDecimal idfamiliar,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM CRMFAMILIARES " + " WHERE idfamiliar = "
				+ idfamiliar.toString() + " and idempresa = "
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM CRMFAMILIARES " + " WHERE idfamiliar = "
						+ idfamiliar.toString() + " and idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : crmfamiliaresDelete( BigDecimal idfamiliar ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: crmfamiliaresDelete( BigDecimal idfamiliar )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String crmfamiliaresCreate(BigDecimal idindividuos,
			BigDecimal idnexofamiliar, String nombre, String profesion,
			String actividad, String email, String telefonos_part,
			String celular, Timestamp fechanacimiento, String deportes,
			String hobbies, String actividad_social, String obseravaciones,
			BigDecimal idempresa, String usuarioalt) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idindividuos == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idindividuos ";
		if (idnexofamiliar == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idnexofamiliar ";
		if (nombre == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nombre ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (nombre.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: nombre ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO CRMFAMILIARES(idindividuos, idnexofamiliar, nombre, profesion, actividad, email, telefonos_part, celular, fechanacimiento, deportes, hobbies, actividad_social, obseravaciones, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, idindividuos);
				insert.setBigDecimal(2, idnexofamiliar);
				insert.setString(3, nombre);
				insert.setString(4, profesion);
				insert.setString(5, actividad);
				insert.setString(6, email);
				insert.setString(7, telefonos_part);
				insert.setString(8, celular);
				insert.setTimestamp(9, fechanacimiento);
				insert.setString(10, deportes);
				insert.setString(11, hobbies);
				insert.setString(12, actividad_social);
				insert.setString(13, obseravaciones);
				insert.setBigDecimal(14, idempresa);
				insert.setString(15, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String crmfamiliaresCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String crmfamiliaresCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String crmfamiliaresCreateOrUpdate(BigDecimal idfamiliar,
			BigDecimal idindividuos, BigDecimal idnexofamiliar, String nombre,
			String profesion, String actividad, String email,
			String telefonos_part, String celular, Timestamp fechanacimiento,
			String deportes, String hobbies, String actividad_social,
			String obseravaciones, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idfamiliar == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idfamiliar ";
		if (idindividuos == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idindividuos ";
		if (idnexofamiliar == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idnexofamiliar ";
		if (nombre == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nombre ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (nombre.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: nombre ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM crmfamiliares WHERE idfamiliar = "
					+ idfamiliar.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE CRMFAMILIARES SET idindividuos=?, idnexofamiliar=?, nombre=?, profesion=?, actividad=?, email=?, telefonos_part=?, celular=?, fechanacimiento=?, deportes=?, hobbies=?, actividad_social=?, obseravaciones=?, idempresa=?, usuarioact=?, fechaact=? WHERE idfamiliar=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, idindividuos);
					insert.setBigDecimal(2, idnexofamiliar);
					insert.setString(3, nombre);
					insert.setString(4, profesion);
					insert.setString(5, actividad);
					insert.setString(6, email);
					insert.setString(7, telefonos_part);
					insert.setString(8, celular);
					insert.setTimestamp(9, fechanacimiento);
					insert.setString(10, deportes);
					insert.setString(11, hobbies);
					insert.setString(12, actividad_social);
					insert.setString(13, obseravaciones);
					insert.setBigDecimal(14, idempresa);
					insert.setString(15, usuarioact);
					insert.setTimestamp(16, fechaact);
					insert.setBigDecimal(17, idfamiliar);
				} else {
					String ins = "INSERT INTO CRMFAMILIARES(idindividuos, idnexofamiliar, nombre, profesion, actividad, email, telefonos_part, celular, fechanacimiento, deportes, hobbies, actividad_social, obseravaciones, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setBigDecimal(1, idindividuos);
					insert.setBigDecimal(2, idnexofamiliar);
					insert.setString(3, nombre);
					insert.setString(4, profesion);
					insert.setString(5, actividad);
					insert.setString(6, email);
					insert.setString(7, telefonos_part);
					insert.setString(8, celular);
					insert.setTimestamp(9, fechanacimiento);
					insert.setString(10, deportes);
					insert.setString(11, hobbies);
					insert.setString(12, actividad_social);
					insert.setString(13, obseravaciones);
					insert.setBigDecimal(14, idempresa);
					insert.setString(15, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String crmfamiliaresCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String crmfamiliaresCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String crmfamiliaresUpdate(BigDecimal idfamiliar,
			BigDecimal idindividuos, BigDecimal idnexofamiliar, String nombre,
			String profesion, String actividad, String email,
			String telefonos_part, String celular, Timestamp fechanacimiento,
			String deportes, String hobbies, String actividad_social,
			String obseravaciones, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idfamiliar == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idfamiliar ";
		if (idindividuos == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idindividuos ";
		if (idnexofamiliar == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idnexofamiliar ";
		if (nombre == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nombre ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (nombre.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: nombre ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM crmfamiliares "
					+ " WHERE idfamiliar = " + idfamiliar.toString()
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
					sql = "UPDATE CRMFAMILIARES SET idindividuos=?, idnexofamiliar=?, nombre=?, profesion=?, actividad=?, email=?, telefonos_part=?, celular=?, fechanacimiento=?, deportes=?, hobbies=?, actividad_social=?, obseravaciones=?, usuarioact=?, fechaact=? WHERE idfamiliar=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, idindividuos);
					insert.setBigDecimal(2, idnexofamiliar);
					insert.setString(3, nombre);
					insert.setString(4, profesion);
					insert.setString(5, actividad);
					insert.setString(6, email);
					insert.setString(7, telefonos_part);
					insert.setString(8, celular);
					insert.setTimestamp(9, fechanacimiento);
					insert.setString(10, deportes);
					insert.setString(11, hobbies);
					insert.setString(12, actividad_social);
					insert.setString(13, obseravaciones);
					insert.setString(14, usuarioact);
					insert.setTimestamp(15, fechaact);
					insert.setBigDecimal(16, idfamiliar);
					insert.setBigDecimal(17, idempresa);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String crmfamiliaresUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String crmfamiliaresUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public List getCrmfamiliaresIndividuoAll(long limit, long offset,
			BigDecimal idindividuos, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " "
				+ "SELECT fam.idfamiliar,ind.razon_nombre,anf.nexofamiliar,fam.nombre,fam.profesion,"
				+ "       fam.actividad,fam.email,fam.telefonos_part,fam.celular,fam.fechanacimiento,"
				+ "       fam.deportes,fam.hobbies,fam.actividad_social,fam.obseravaciones,fam.idempresa,"
				+ "       fam.usuarioalt,fam.usuarioact,fam.fechaalt,fam.fechaact  "
				+ "  FROM CRMFAMILIARES fam "
				+ "       LEFT JOIN crmindividuos ind ON fam.idindividuos = ind.idindividuos  AND fam.idempresa = ind.idempresa "
				+ "       LEFT JOIN crmnexosfamiliares anf ON fam.idnexofamiliar = anf.idnexofamiliar AND fam.idempresa = anf.idempresa  "
				+ " WHERE ind.idempresa = " + idempresa.toString()
				+ " AND fam.idindividuos = " + idindividuos.toString()
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
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
					.error("Error SQL en el metodo : getCrmfamiliaresIndividuoAll() "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmfamiliaresIndividuoAll()  "
							+ ex);
		}
		return vecSalida;
	}

	public List getCrmfamiliaresIndividuoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idindividuos, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		ocurrencia = ocurrencia.toUpperCase();
		String cQuery = " "
				+ "SELECT idfamiliar,razon_nombre,nexofamiliar,nombre,profesion,actividad,email,telefonos_part,"
				+ "       celular,fechanacimiento,deportes,hobbies,actividad_social,obseravaciones,idempresa,"
				+ "       usuarioalt,usuarioact,fechaalt,fechaact  "
				+ "  FROM vcrmfamiliares " + " WHERE idempresa= "
				+ idempresa.toString() + " AND idindividuos = "
				+ idindividuos.toString()
				+ " and ( UPPER(razon_nombre) LIKE '%" + ocurrencia + "%' OR "
				+ "  UPPER(nexofamiliar LIKE '%" + ocurrencia + "%' OR "
				+ "  UPPER(nombre) LIKE '%" + ocurrencia + "%' OR "
				+ "  UPPER(profesion) LIKE '%" + ocurrencia + "%' OR "
				+ "  UPPER(actividad) LIKE '%" + ocurrencia + "%' OR "
				+ "  UPPER(email) LIKE '%" + ocurrencia + "%' OR "
				+ "  UPPER(telefonos_part) LIKE '%" + ocurrencia + "%' OR "
				+ "  UPPER(celular) LIKE '%" + ocurrencia + "%' OR "
				+ "  UPPER(fechanacimiento) LIKE '%" + ocurrencia + "%' OR "
				+ "  UPPER(deportes) LIKE '%" + ocurrencia + "%' OR "
				+ "  UPPER(hobbies) LIKE '%" + ocurrencia + "%' OR "
				+ "  UPPER(actividad_social) LIKE '%" + ocurrencia + "%' OR "
				+ "  UPPER(obseravaciones) LIKE '%" + ocurrencia + "%') "
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
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
					.error("Error SQL en el metodo : getCrmfamiliaresIndividuoOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmfamiliaresIndividuoOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	} // LLamados

	public List getCrmllamadosAll(long limit, long offset, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " "
				+ "SELECT ll.idllamado,tll.tipollamada,gu.usuario,ind.razon_nombre,fam.nombre,ll.fechallamada,"
				+ "       ll.obseravaciones, re.idresultadollamada, re.resultadollamada, ll.fecharellamada, "
				+ "       ll.idempresa,ll.usuarioalt,ll.usuarioact,ll.fechaalt,ll.fechaact "
				+ "  FROM CRMLLAMADOS ll "
				+ "       LEFT JOIN crmtiposllamadas tll ON ll.idtipollamada = tll.idtipollamada  AND ll.idempresa = tll.idempresa "
				+ "       LEFT JOIN globalusuarios gu ON ll.idusuario = gu.idusuario  AND ll.idempresa = gu.idempresa "
				+ "       LEFT JOIN crmindividuos ind ON ll.idindividuos = ind.idindividuos  AND ll.idempresa = ind.idempresa "
				+ "       LEFT JOIN crmfamiliares fam ON ll.idfamiliar = fam.idfamiliar  AND ll.idempresa = fam.idempresa "
				+ "       INNER JOIN crmresultadosllamadas re ON ll.idresultadollamada = re.idresultadollamada  AND ll.idempresa = re.idempresa"
				+ " where ll.idempresa = " + idempresa.toString()
				+ "ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
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
			log.error("Error SQL en el metodo : getCrmllamadosAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmllamadosAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getCrmllamadosOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idllamado,tipollamada,usuario,razon_nombre,nombre,fechallamada,obseravaciones, idresultadollamada, resultadollamada,"
				+ "       fecharellamada,idempresa,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM vcrmllamados " + " where idempresa= "
				+ idempresa.toString() + " and (idllamado::VARCHAR LIKE '%" + ocurrencia
				+ "%' OR " + " tipollamada LIKE '%" + ocurrencia + "%' OR "
				+ " usuario LIKE '%" + ocurrencia + "%' OR "
				+ " razon_nombre LIKE '%" + ocurrencia + "%' OR "
				+ " nombre LIKE '%" + ocurrencia + "%' OR "
				+ " fechallamada LIKE '%" + ocurrencia + "%' OR "
				+ " UPPER(obseravaciones) LIKE '%" + ocurrencia.toUpperCase()
				+ "%') " + " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset
				+ ";";
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
					.error("Error SQL en el metodo : getCrmllamadosOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmllamadosOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getCrmllamadosPK(BigDecimal idllamado, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " "
				+ "SELECT ll.idllamado,ll.idtipollamada,tll.tipollamada,ll.idusuario,gu.usuario,ll.idindividuos,"
				+ "       ind.razon_nombre,ll.idfamiliar,fam.nombre,ll.fechallamada,ll.obseravaciones,re.idresultadollamada, re.resultadollamada,"
				+ "       ll.fecharellamada,ll.idempresa,ll.usuarioalt,ll.usuarioact,ll.fechaalt,ll.fechaact "
				+ "  FROM CRMLLAMADOS ll "
				+ "       LEFT JOIN crmtiposllamadas tll ON ll.idtipollamada = tll.idtipollamada  AND ll.idempresa = tll.idempresa "
				+ "       LEFT JOIN globalusuarios gu ON ll.idusuario = gu.idusuario  AND ll.idempresa = gu.idempresa "
				+ "       LEFT JOIN crmindividuos ind ON ll.idindividuos = ind.idindividuos  AND ll.idempresa = ind.idempresa "
				+ "       LEFT JOIN crmfamiliares fam ON ll.idfamiliar = fam.idfamiliar  AND ll.idempresa = fam.idempresa"
				+ "       INNER JOIN crmresultadosllamadas re ON ll.idresultadollamada = re.idresultadollamada  AND ll.idempresa = re.idempresa"
				+ " WHERE ll.idllamado = " + idllamado.toString()
				+ " and ll.idempresa = " + idempresa.toString();
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
					.error("Error SQL en el metodo : getCrmllamadosPK( BigDecimal idllamado ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmllamadosPK( BigDecimal idllamado )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String crmllamadosDelete(BigDecimal idllamado, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM CRMLLAMADOS " + " WHERE idllamado = "
				+ idllamado.toString() + " and idempresa = "
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM CRMLLAMADOS " + " WHERE idllamado = "
						+ idllamado.toString() + " and idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : crmllamadosDelete( BigDecimal idllamado ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: crmllamadosDelete( BigDecimal idllamado )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String crmllamadosCreate(BigDecimal idtipollamada,
			BigDecimal idusuario, String idindividuos, String idfamiliar,
			Timestamp fechallamada, String obseravaciones,
			BigDecimal idresultadollamada, Timestamp fecharellamada,
			BigDecimal idempresa, String usuarioalt) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idtipollamada == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipollamada ";
		if (idusuario == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idusuario ";
		if (fechallamada == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechallamada ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
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
				String ins = ""
						+ "INSERT INTO CRMLLAMADOS(idtipollamada, idusuario, idindividuos, idfamiliar, fechallamada, obseravaciones, idresultadollamada, fecharellamada, idempresa, usuarioalt ) "
						+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, idtipollamada);
				insert.setBigDecimal(2, idusuario);
				if (idindividuos != "" && !idindividuos.equalsIgnoreCase("")) {
					insert.setBigDecimal(3, new BigDecimal(idindividuos));
				} else {
					insert.setBigDecimal(3, null);
				}
				if (idfamiliar != "" && !idfamiliar.equalsIgnoreCase("")) {
					insert.setBigDecimal(4, new BigDecimal(idfamiliar));
				} else {
					insert.setBigDecimal(4, null);
				}
				insert.setTimestamp(5, fechallamada);
				insert.setString(6, obseravaciones);
				insert.setBigDecimal(7, idresultadollamada);
				insert.setTimestamp(8, fecharellamada);
				insert.setBigDecimal(9, idempresa);
				insert.setString(10, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String crmllamadosCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error excepcion public String crmllamadosCreate(.....)"
					+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String crmllamadosCreateOrUpdate(BigDecimal idllamado,
			BigDecimal idtipollamada, BigDecimal idusuario,
			String idindividuos, String idfamiliar, Timestamp fechallamada,
			String obseravaciones, BigDecimal idresultadollamada,
			Timestamp fecharellamada, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idllamado == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idllamado ";
		if (idtipollamada == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipollamada ";
		if (idusuario == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idvendedor ";
		if (fechallamada == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechallamada ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM crmllamados WHERE idllamado = "
					+ idllamado.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE CRMLLAMADOS SET idtipollamada=?, idusuario=?, idindividuos=?, idfamiliar=?, fechallamada=?, obseravaciones=?, idresultadollamada=?, fecharellamada=?, idempresa=?, usuarioact=?, fechaact=? WHERE idllamado=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, idtipollamada);
					insert.setBigDecimal(2, idusuario);
					if (idindividuos != null
							&& !idindividuos.equalsIgnoreCase("null")) {
						insert.setBigDecimal(3, new BigDecimal(idindividuos));
					} else {
						insert.setBigDecimal(3, null);
					}
					if (idfamiliar != null
							&& !idfamiliar.equalsIgnoreCase("null")) {
						insert.setBigDecimal(4, new BigDecimal(idfamiliar));
					} else {
						insert.setBigDecimal(4, null);
					}
					insert.setTimestamp(5, fechallamada);
					insert.setString(6, obseravaciones);
					insert.setBigDecimal(7, idresultadollamada);
					insert.setTimestamp(8, fecharellamada);
					insert.setBigDecimal(9, idempresa);
					insert.setString(10, usuarioact);
					insert.setTimestamp(11, fechaact);
					insert.setBigDecimal(12, idllamado);
				} else {
					String ins = "INSERT INTO CRMLLAMADOS(idtipollamada, idusuario, idindividuos, idfamiliar, fechallamada, obseravaciones, idresultadollamada, fecharellamada, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setBigDecimal(1, idtipollamada);
					insert.setBigDecimal(2, idusuario);
					if (idindividuos != ""
							&& !idindividuos.equalsIgnoreCase("")) {
						insert.setBigDecimal(3, new BigDecimal(idindividuos));
					} else {
						insert.setBigDecimal(3, null);
					}
					if (idfamiliar != "" && !idfamiliar.equalsIgnoreCase("")) {
						insert.setBigDecimal(4, new BigDecimal(idfamiliar));
					} else {
						insert.setBigDecimal(4, null);
					}
					insert.setTimestamp(5, fechallamada);
					insert.setString(6, obseravaciones);
					insert.setBigDecimal(7, idresultadollamada);
					insert.setTimestamp(8, fecharellamada);
					insert.setBigDecimal(9, idempresa);
					insert.setString(10, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String crmllamadosCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String crmllamadosCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String crmllamadosUpdate(BigDecimal idllamado,
			BigDecimal idtipollamada, BigDecimal idusuario,
			String idindividuos, String idfamiliar, Timestamp fechallamada,
			String obseravaciones, BigDecimal idresultadollamada,
			Timestamp fecharellamada, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idllamado == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idllamado ";
		if (idtipollamada == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipollamada ";
		if (idusuario == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idvendedor ";
		if (fechallamada == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechallamada ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM crmllamados WHERE idllamado = "
					+ idllamado.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE CRMLLAMADOS SET idtipollamada=?, idusuario=?, idindividuos=?, idfamiliar=?, fechallamada=?, obseravaciones=?, idresultadollamada=?, fecharellamada=?, idempresa=?, usuarioact=?, fechaact=? WHERE idllamado=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, idtipollamada);
					insert.setBigDecimal(2, idusuario);
					if (idindividuos != null
							&& !idindividuos.equalsIgnoreCase("null")) {
						insert.setBigDecimal(3, new BigDecimal(idindividuos));
					} else {
						insert.setBigDecimal(3, null);
					}
					if (idfamiliar != null
							&& !idfamiliar.equalsIgnoreCase("null")) {
						insert.setBigDecimal(4, new BigDecimal(idfamiliar));
					} else {
						insert.setBigDecimal(4, null);
					}
					insert.setTimestamp(5, fechallamada);
					insert.setString(6, obseravaciones);
					insert.setBigDecimal(7, idresultadollamada);
					insert.setTimestamp(8, fecharellamada);
					insert.setBigDecimal(9, idempresa);
					insert.setString(10, usuarioact);
					insert.setTimestamp(11, fechaact);
					insert.setBigDecimal(12, idllamado);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String crmllamadosUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log.error("Error excepcion public String crmllamadosUpdate(.....)"
					+ ex);
		}
		return salida;
	}

	// Tipo de cotizaciones
	public List getCrmtiposcotizacionesAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idtipocotizacion,tipocotizacion,idempresa, valorunidadsup, "
				+ "       usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM CRMTIPOSCOTIZACIONES " + " WHERE idempresa = "
				+ idempresa.toString() + "ORDER BY 2  LIMIT " + limit
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
			log.error("Error SQL en el metodo : getCrmtiposcotizacionesAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmtiposcotizacionesAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getCrmtiposcotizacionesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idtipocotizacion,tipocotizacion,idempresa,valorunidadsup,"
				+ "       usuarioalt,usuarioact,fechaalt,fechaact"
				+ "  FROM CRMTIPOSCOTIZACIONES " + " where idempresa= "
				+ idempresa.toString() + " and (idtipocotizacion::VARCHAR LIKE '%"
				+ ocurrencia + "%' OR " + " UPPER(tipocotizacion) LIKE '%"
				+ ocurrencia.toUpperCase() + "%') " + " ORDER BY 2  LIMIT "
				+ limit + " OFFSET  " + offset + ";";
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
					.error("Error SQL en el metodo : getCrmtiposcotizacionesOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmtiposcotizacionesOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getCrmtiposcotizacionesPK(BigDecimal idtipocotizacion,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idtipocotizacion,tipocotizacion,idempresa,valorunidadsup,"
				+ "       usuarioalt,usuarioact,fechaalt,fechaact FROM CRMTIPOSCOTIZACIONES "
				+ " WHERE idtipocotizacion = " + idtipocotizacion.toString()
				+ " and idempresa = " + idempresa.toString();
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
					.error("Error SQL en el metodo : getCrmtiposcotizacionesPK( BigDecimal idtipocotizacion ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmtiposcotizacionesPK( BigDecimal idtipocotizacion )  "
							+ ex);
		}
		return vecSalida;
	}

	public BigDecimal getCrmTCValorUnidaSup(BigDecimal idtipocotizacion,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		BigDecimal valorunidadsup = null;
		String cQuery = ""
				+ "SELECT  valorunidadsup FROM CRMTIPOSCOTIZACIONES "
				+ " WHERE idtipocotizacion = " + idtipocotizacion.toString()
				+ "   AND idempresa = " + idempresa.toString();

		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				valorunidadsup = rsSalida.getBigDecimal(1);
			} else {
				log.warn("getCrmTCValorUnidaSup: Tipo cotizacion inexistente-["
						+ idtipocotizacion + "]");
			}
		} catch (SQLException sqlException) {
			log
					.error("Error SQL en el metodo : getCrmtiposcotizacionesPK( BigDecimal idtipocotizacion ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmtiposcotizacionesPK( BigDecimal idtipocotizacion )  "
							+ ex);
		}
		return valorunidadsup;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String crmtiposcotizacionesDelete(BigDecimal idtipocotizacion,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM CRMTIPOSCOTIZACIONES "
				+ " WHERE idtipocotizacion = " + idtipocotizacion.toString()
				+ " and idempresa = " + idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM CRMTIPOSCOTIZACIONES "
						+ " WHERE idtipocotizacion = "
						+ idtipocotizacion.toString() + " and idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : crmtiposcotizacionesDelete( BigDecimal idtipocotizacion ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: crmtiposcotizacionesDelete( BigDecimal idtipocotizacion )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String crmtiposcotizacionesCreate(String tipocotizacion,
			BigDecimal valorunidadsup, BigDecimal idempresa, String usuarioalt)
			throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (tipocotizacion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipocotizacion ";
		if (valorunidadsup == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: valorunidadsup ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (tipocotizacion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipocotizacion ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = ""
						+ "INSERT INTO CRMTIPOSCOTIZACIONES(tipocotizacion, idempresa, valorunidadsup, usuarioalt ) "
						+ "VALUES (?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, tipocotizacion);
				insert.setBigDecimal(2, idempresa);
				insert.setBigDecimal(3, valorunidadsup);
				insert.setString(4, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String crmtiposcotizacionesCreate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String crmtiposcotizacionesCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String crmtiposcotizacionesCreateOrUpdate(
			BigDecimal idtipocotizacion, String tipocotizacion,
			BigDecimal valorunidadsup, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idtipocotizacion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipocotizacion ";
		if (tipocotizacion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipocotizacion ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (tipocotizacion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipocotizacion ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM crmtiposcotizaciones WHERE idtipocotizacion = "
					+ idtipocotizacion.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE CRMTIPOSCOTIZACIONES SET tipocotizacion=?, valorunidadsup=?, idempresa=?, usuarioact=?, fechaact=? WHERE idtipocotizacion=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, tipocotizacion);
					insert.setBigDecimal(2, valorunidadsup);
					insert.setBigDecimal(3, idempresa);
					insert.setString(4, usuarioact);
					insert.setTimestamp(5, fechaact);
					insert.setBigDecimal(6, idtipocotizacion);
				} else {
					String ins = "INSERT INTO CRMTIPOSCOTIZACIONES(tipocotizacion, valorunidadsup, idempresa, usuarioalt ) VALUES (?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, tipocotizacion);
					insert.setBigDecimal(2, valorunidadsup);
					insert.setBigDecimal(3, idempresa);
					insert.setString(4, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String crmtiposcotizacionesCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String crmtiposcotizacionesCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String crmtiposcotizacionesUpdate(BigDecimal idtipocotizacion,
			String tipocotizacion, BigDecimal valorunidadsup,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idtipocotizacion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipocotizacion ";
		if (tipocotizacion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipocotizacion ";
		if (valorunidadsup == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: valorunidadsup ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (tipocotizacion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipocotizacion ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM crmtiposcotizaciones "
					+ " WHERE idtipocotizacion = "
					+ idtipocotizacion.toString() + " and idempresa = "
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
					sql = "UPDATE CRMTIPOSCOTIZACIONES "
							+ "SET tipocotizacion=?, valorunidadsup=?, usuarioact=?, fechaact=? "
							+ "WHERE idtipocotizacion=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, tipocotizacion);
					insert.setBigDecimal(2, valorunidadsup);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, idtipocotizacion);
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
					.error("Error SQL public String crmtiposcotizacionesUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String crmtiposcotizacionesUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// Tipo financiaciones
	public List getCrmtiposfinanciacionesAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " SELECT idtipofinanciacion,tipofinanciacion,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM CRMTIPOSFINANCIACIONES "
				+ " where idempresa = "
				+ idempresa.toString()
				+ "ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
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
					.error("Error SQL en el metodo : getCrmtiposfinanciacionesAll() "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmtiposfinanciacionesAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)
	public List getCrmtiposfinanciacionesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idtipofinanciacion,tipofinanciacion,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM CRMTIPOSFINANCIACIONES "
				+ " where idempresa= "
				+ idempresa.toString()
				+ " and (idtipofinanciacion::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " UPPER(tipofinanciacion) LIKE '%"
				+ ocurrencia.toUpperCase()
				+ "%') "
				+ " ORDER BY 2  LIMIT "
				+ limit
				+ " OFFSET  "
				+ offset
				+ ";";
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
					.error("Error SQL en el metodo : getCrmtiposfinanciacionesOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmtiposfinanciacionesOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getCrmtiposfinanciacionesPK(BigDecimal idtipofinanciacion,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idtipofinanciacion,tipofinanciacion,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM CRMTIPOSFINANCIACIONES "
				+ " WHERE idtipofinanciacion = "
				+ idtipofinanciacion.toString()
				+ " and idempresa = "
				+ idempresa.toString();
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
					.error("Error SQL en el metodo : getCrmtiposfinanciacionesPK( BigDecimal idtipofinanciacion ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmtiposfinanciacionesPK( BigDecimal idtipofinanciacion )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String crmtiposfinanciacionesDelete(BigDecimal idtipofinanciacion,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM CRMTIPOSFINANCIACIONES "
				+ " WHERE idtipofinanciacion = "
				+ idtipofinanciacion.toString() + " and idempresa = "
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM CRMTIPOSFINANCIACIONES "
						+ " WHERE idtipofinanciacion = "
						+ idtipofinanciacion.toString() + " and idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : crmtiposfinanciacionesDelete( BigDecimal idtipofinanciacion ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: crmtiposfinanciacionesDelete( BigDecimal idtipofinanciacion )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String crmtiposfinanciacionesCreate(String tipofinanciacion,
			BigDecimal idempresa, String usuarioalt) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (tipofinanciacion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipofinanciacion ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (tipofinanciacion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipofinanciacion ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO CRMTIPOSFINANCIACIONES(tipofinanciacion, idempresa, usuarioalt ) VALUES (?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, tipofinanciacion);
				insert.setBigDecimal(2, idempresa);
				insert.setString(3, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String crmtiposfinanciacionesCreate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String crmtiposfinanciacionesCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String crmtiposfinanciacionesCreateOrUpdate(
			BigDecimal idtipofinanciacion, String tipofinanciacion,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idtipofinanciacion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipofinanciacion ";
		if (tipofinanciacion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipofinanciacion ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (tipofinanciacion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipofinanciacion ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM crmtiposfinanciaciones WHERE idtipofinanciacion = "
					+ idtipofinanciacion.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE CRMTIPOSFINANCIACIONES SET tipofinanciacion=?, idempresa=?, usuarioact=?, fechaact=? WHERE idtipofinanciacion=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, tipofinanciacion);
					insert.setBigDecimal(2, idempresa);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, idtipofinanciacion);
				} else {
					String ins = "INSERT INTO CRMTIPOSFINANCIACIONES(tipofinanciacion, idempresa, usuarioalt ) VALUES (?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, tipofinanciacion);
					insert.setBigDecimal(2, idempresa);
					insert.setString(3, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String crmtiposfinanciacionesCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String crmtiposfinanciacionesCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String crmtiposfinanciacionesUpdate(BigDecimal idtipofinanciacion,
			String tipofinanciacion, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idtipofinanciacion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipofinanciacion ";
		if (tipofinanciacion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipofinanciacion ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (tipofinanciacion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipofinanciacion ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM crmtiposfinanciaciones "
					+ " WHERE idtipofinanciacion = "
					+ idtipofinanciacion.toString() + " and idempresa = "
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
					sql = "UPDATE CRMTIPOSFINANCIACIONES SET tipofinanciacion=?, usuarioact=?, fechaact=? WHERE idtipofinanciacion=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, tipofinanciacion);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idtipofinanciacion);
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
					.error("Error SQL public String crmtiposfinanciacionesUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String crmtiposfinanciacionesUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// Cotizaciones
	public List getCrmcotizacionesAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " "
				+ "SELECT cot.idcotizacion,gu.usuario,cot.nrolote,tc.tipocotizacion,tf.tipofinanciacion,"
				+ "       cot.superficie,sm.descrip_md,cot.valor_unitario,cot.valor_total,cot.precio_contado,"
				+ "       cot.precio_financiado,ind.razon_nombre,cot.idempresa, rc.idresultadocotizacion, rc.resultadocotizacion,"
				+ "       cot.usuarioalt,cot.usuarioact,cot.fechaalt,cot.fechaact "
				+ "  FROM CRMCOTIZACIONES  cot "
				+ "       LEFT JOIN globalusuarios gu ON cot.idusuario = gu.idusuario AND cot.idempresa = gu.idempresa "
				+ "       LEFT JOIN crmtiposcotizaciones tc ON cot.idtipocotizacion = tc.idtipocotizacion AND cot.idempresa = tc.idempresa "
				+ "       LEFT JOIN crmtiposfinanciaciones tf ON cot.idtipofinanciacion = tf.idtipofinanciacion AND cot.idempresa = tf.idempresa "
				+ "       LEFT JOIN stockmedidas sm ON cot.codigo_md = sm.codigo_md AND cot.idempresa = sm.idempresa "
				+ "       LEFT JOIN crmindividuos ind ON cot.idindividuos = ind.idindividuos AND cot.idempresa = ind.idempresa  "
				+ "       LEFT JOIN crmresultadoscotizaciones rc ON cot.idresultadocotizacion = rc.idresultadocotizacion  AND cot.idempresa = rc.idempresa  "
				+ " WHERE cot.idempresa = " + idempresa.toString()
				+ "ORDER BY 1 desc   LIMIT " + limit + " OFFSET  " + offset
				+ ";";
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
			log.error("Error SQL en el metodo : getCrmcotizacionesAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmcotizacionesAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getCrmcotizacionesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " "
				+ "SELECT idcotizacion,usuario,nrolote,tipocotizacion,tipofinanciacion,superficie,descrip_md,"
				+ "       valor_unitario,valor_total,precio_contado,precio_financiado,razon_nombre,idempresa,"
				+ "       usuarioalt,usuarioact,fechaalt,fechaact FROM vcrmcotizaciones "
				+ " where idempresa= " + idempresa.toString()
				+ " and (idcotizacion::VARCHAR LIKE '%" + ocurrencia + "%' OR "
				+ " usuario LIKE '%" + ocurrencia + "%' OR "
				+ " nrolote::VARCHAR LIKE '%" + ocurrencia + "%' OR "
				+ " tipocotizacion LIKE '%" + ocurrencia + "%' OR "
				+ " tipofinanciacion LIKE '%" + ocurrencia + "%' OR "
				+ " superficie::VARCHAR LIKE '%" + ocurrencia + "%' OR "
				+ " descrip_md LIKE '%" + ocurrencia + "%' OR "
				+ " valor_unitario LIKE '%" + ocurrencia + "%' OR "
				+ " valor_total::VARCHAR LIKE '%" + ocurrencia + "%' OR "
				+ " precio_contado::VARCHAR LIKE '%" + ocurrencia + "%' OR "
				+ " razon_nombre LIKE '%" + ocurrencia + "%' OR "
				+ " (precio_financiado::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase() + "%') "
				+ " ORDER BY 1 desc   LIMIT " + limit + " OFFSET  " + offset
				+ ";";
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
					.error("Error SQL en el metodo : getCrmcotizacionesOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmcotizacionesOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getCrmcotizacionesPK(BigDecimal idcotizacion,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " "
				+ "SELECT cot.idcotizacion, cot.idusuario, gu.usuario, cot.nrolote, cot.idtipocotizacion, "
				+ "       tc.tipocotizacion, cot.idtipofinanciacion, tf.tipofinanciacion, cot.superficie, "
				+ "       cot.codigo_md, sm.descrip_md, cot.valor_unitario, cot.valor_total, cot.precio_contado, "
				+ "       cot.precio_financiado,cot.idindividuos,ind.razon_nombre, cot.idempresa, rc.idresultadocotizacion, rc.resultadocotizacion, "
				+ "       cot.usuarioalt, cot.usuarioact, cot.fechaalt, cot.fechaact "
				+ "  FROM crmcotizaciones cot "
				+ "       LEFT JOIN globalusuarios gu ON cot.idusuario = gu.idusuario AND cot.idempresa = gu.idempresa "
				+ "       LEFT JOIN crmtiposcotizaciones tc ON cot.idtipocotizacion = tc.idtipocotizacion AND cot.idempresa = tc.idempresa "
				+ "       LEFT JOIN crmtiposfinanciaciones tf ON cot.idtipofinanciacion = tf.idtipofinanciacion AND cot.idempresa = tf.idempresa "
				+ "       LEFT JOIN stockmedidas sm ON cot.codigo_md = sm.codigo_md AND cot.idempresa = sm.idempresa "
				+ "       LEFT JOIN crmindividuos ind ON cot.idindividuos = ind.idindividuos AND cot.idempresa = ind.idempresa "
				+ "       LEFT JOIN crmresultadoscotizaciones rc ON cot.idresultadocotizacion = rc.idresultadocotizacion  AND cot.idempresa = rc.idempresa  "
				+ " WHERE cot.idcotizacion = " + idcotizacion.toString()
				+ " and cot.idempresa = " + idempresa.toString();
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
					.error("Error SQL en el metodo : getCrmcotizacionesPK( BigDecimal idcotizacion ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmcotizacionesPK( BigDecimal idcotizacion )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String crmcotizacionesDelete(BigDecimal idcotizacion,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM CRMCOTIZACIONES "
				+ " WHERE idcotizacion = " + idcotizacion.toString()
				+ " and idempresa = " + idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM CRMCOTIZACIONES "
						+ " WHERE idcotizacion = " + idcotizacion.toString()
						+ " and idempresa = " + idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : crmcotizacionesDelete( BigDecimal idcotizacion ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: crmcotizacionesDelete( BigDecimal idcotizacion )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String crmcotizacionesCreate(BigDecimal idusuario,
			BigDecimal nrolote, BigDecimal idtipocotizacion,
			String idtipofinanciacion, Double superficie, BigDecimal codigo_md,
			Double valor_unitario, Double valor_total, Double precio_contado,
			Double precio_financiado, BigDecimal idempresa, String usuarioalt,
			String idindividuos, BigDecimal idresultadocotizacion)
			throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idusuario == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idvendedor ";
		if (nrolote == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nrolote ";
		if (idtipocotizacion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipocotizacion ";
		if (superficie == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: superficie ";
		if (codigo_md == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_md ";
		if (valor_unitario == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: valor_unitario ";
		if (valor_total == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: valor_total ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
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
				String ins = ""
						+ "INSERT INTO CRMCOTIZACIONES(idusuario, nrolote, idtipocotizacion, idtipofinanciacion, superficie, codigo_md, valor_unitario, valor_total, precio_contado, precio_financiado, idempresa, usuarioalt,idindividuos, idresultadocotizacion ) "
						+ "VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, idusuario);
				insert.setBigDecimal(2, nrolote);
				insert.setBigDecimal(3, idtipocotizacion);
				if (idtipofinanciacion != ""
						&& !idtipofinanciacion.equalsIgnoreCase("")) {
					insert.setBigDecimal(4, new BigDecimal(idtipofinanciacion));
				} else {
					insert.setBigDecimal(4, null);
				}
				insert.setDouble(5, superficie.doubleValue());
				insert.setBigDecimal(6, codigo_md);
				insert.setDouble(7, valor_unitario.doubleValue());
				insert.setDouble(8, valor_total.doubleValue());
				insert.setDouble(9, precio_contado.doubleValue());
				insert.setDouble(10, precio_financiado.doubleValue());
				insert.setBigDecimal(11, idempresa);
				insert.setString(12, usuarioalt);
				if (idindividuos != "" && !idindividuos.equalsIgnoreCase("")) {
					insert.setBigDecimal(13, new BigDecimal(idindividuos));
				} else {
					insert.setBigDecimal(13, null);
				}
				insert.setBigDecimal(14, idresultadocotizacion);

				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String crmcotizacionesCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String crmcotizacionesCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String crmcotizacionesCreateOrUpdate(BigDecimal idcotizacion,
			BigDecimal idusuario, BigDecimal nrolote,
			BigDecimal idtipocotizacion, String idtipofinanciacion,
			Double superficie, BigDecimal codigo_md, Double valor_unitario,
			Double valor_total, Double precio_contado,
			Double precio_financiado, String usuarioact, BigDecimal idempresa,
			BigDecimal idresultadocotizacion) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idcotizacion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idcotizacion ";
		if (idusuario == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idvendedor ";
		if (nrolote == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nrolote ";
		if (idtipocotizacion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipocotizacion ";
		if (superficie == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: superficie ";
		if (codigo_md == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_md ";
		if (valor_unitario == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: valor_unitario ";
		if (valor_total == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: valor_total ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM crmcotizaciones WHERE idcotizacion = "
					+ idcotizacion.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE CRMCOTIZACIONES SET idusuario=?, nrolote=?, idtipocotizacion=?, idtipofinanciacion=?, superficie=?, codigo_md=?, valor_unitario=?, valor_total=?, precio_contado=?, precio_financiado=?, idempresa=?, idresultadocotizacion=?, usuarioact=?, fechaact=? WHERE idcotizacion=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, idusuario);
					insert.setBigDecimal(2, nrolote);
					insert.setBigDecimal(3, idtipocotizacion);
					if (idtipofinanciacion != null
							&& !idtipofinanciacion.equalsIgnoreCase("null")) {
						insert.setBigDecimal(4, new BigDecimal(
								idtipofinanciacion));
					} else {
						insert.setBigDecimal(4, null);
					}
					insert.setDouble(5, superficie.doubleValue());
					insert.setBigDecimal(6, codigo_md);
					insert.setDouble(7, valor_unitario.doubleValue());
					insert.setDouble(8, valor_total.doubleValue());
					insert.setDouble(9, precio_contado.doubleValue());
					insert.setDouble(10, precio_financiado.doubleValue());
					insert.setBigDecimal(11, idempresa);
					insert.setBigDecimal(12, idresultadocotizacion);
					insert.setString(13, usuarioact);
					insert.setTimestamp(14, fechaact);
					insert.setBigDecimal(15, idcotizacion);
				} else {
					String ins = "INSERT INTO CRMCOTIZACIONES(idusuario, nrolote, idtipocotizacion, idtipofinanciacion, superficie, codigo_md, valor_unitario, valor_total, precio_contado, precio_financiado, idempresa, usuarioalt, idresultadocotizacion ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setBigDecimal(1, idusuario);
					insert.setBigDecimal(2, nrolote);
					insert.setBigDecimal(3, idtipocotizacion);
					if (idtipofinanciacion != ""
							&& !idtipofinanciacion.equalsIgnoreCase("")) {
						insert.setBigDecimal(4, new BigDecimal(
								idtipofinanciacion));
					} else {
						insert.setBigDecimal(4, null);
					}
					insert.setDouble(5, superficie.doubleValue());
					insert.setBigDecimal(6, codigo_md);
					insert.setDouble(7, valor_unitario.doubleValue());
					insert.setDouble(8, valor_total.doubleValue());
					insert.setDouble(9, precio_contado.doubleValue());
					insert.setDouble(10, precio_financiado.doubleValue());
					insert.setBigDecimal(11, idempresa);
					insert.setString(12, usuarioalt);
					insert.setBigDecimal(13, idresultadocotizacion);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String crmcotizacionesCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String crmcotizacionesCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String crmcotizacionesUpdate(BigDecimal idcotizacion,
			BigDecimal idusuario, BigDecimal nrolote,
			BigDecimal idtipocotizacion, String idtipofinanciacion,
			Double superficie, BigDecimal codigo_md, Double valor_unitario,
			Double valor_total, Double precio_contado,
			Double precio_financiado, BigDecimal idempresa, String usuarioact,
			String idindividuos, BigDecimal idresultadocotizacion)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idcotizacion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idcotizacion ";
		if (idusuario == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idvendedor ";
		if (nrolote == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nrolote ";
		if (idtipocotizacion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipocotizacion ";
		if (superficie == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: superficie ";
		if (codigo_md == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_md ";
		if (valor_unitario == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: valor_unitario ";
		if (valor_total == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: valor_total ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM crmcotizaciones "
					+ " WHERE idcotizacion = " + idcotizacion.toString()
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
					sql = "UPDATE CRMCOTIZACIONES SET idusuario=?, nrolote=?, idtipocotizacion=?, idtipofinanciacion=?, superficie=?, codigo_md=?, valor_unitario=?, valor_total=?, precio_contado=?, precio_financiado=?,idindividuos=?, idresultadocotizacion=?, usuarioact=?, fechaact=? WHERE idcotizacion=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, idusuario);
					insert.setBigDecimal(2, nrolote);
					insert.setBigDecimal(3, idtipocotizacion);
					if (idtipofinanciacion != null
							&& !idtipofinanciacion.equalsIgnoreCase("null")) {
						insert.setBigDecimal(4, new BigDecimal(
								idtipofinanciacion));
					} else {
						insert.setBigDecimal(4, null);
					}
					insert.setDouble(5, superficie.doubleValue());
					insert.setBigDecimal(6, codigo_md);
					insert.setDouble(7, valor_unitario.doubleValue());
					insert.setDouble(8, valor_total.doubleValue());
					insert.setDouble(9, precio_contado.doubleValue());
					insert.setDouble(10, precio_financiado.doubleValue());
					if (idindividuos != null
							&& !idindividuos.equalsIgnoreCase("null")) {
						insert.setBigDecimal(11, new BigDecimal(idindividuos));
					} else {
						insert.setBigDecimal(11, null);
					}
					insert.setBigDecimal(12, idresultadocotizacion);
					insert.setString(13, usuarioact);
					insert.setTimestamp(14, fechaact);
					insert.setBigDecimal(15, idcotizacion);
					insert.setBigDecimal(16, idempresa);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String crmcotizacionesUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String crmcotizacionesUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// individuos x usuarios
	// individuos
	public List getCrmindividuosAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " SELECT ind.idindividuos, ind.razon_nombre, ind.telefonos_part, ind.celular, ind.email, ind.domicilio_part, ind.fechanacimiento, ind.empresa, ind.domicilio_laboral, ind.telefonos_empr, ind.profesion, ind.actividad, ind.deportes, ind.hobbies, ind.actividad_social, ind.diario_lectura, ind.revista_lectura, ind.lugar_veraneo, ind.obseravaciones, gu.usuario, tc.tipocliente, cc.clasificacioncliente, fc.fuente, ind.idempresa, ind.usuarioalt, ind.usuarioact, ind.fechaalt, ind.fechaact FROM crmindividuos ind LEFT JOIN globalusuarios gu ON ind.idusuario = gu.idusuario AND gu.idempresa = ind.idempresa LEFT JOIN crmtiposclientes tc ON ind.idtipocliente = tc.idtipocliente AND tc.idempresa = ind.idempresa LEFT JOIN crmclasificacionclientes cc ON ind.idclasificacioncliente = cc.idclasificacioncliente AND cc.idempresa = ind.idempresa LEFT JOIN crmfuentecaptacion fc ON ind.idfuente = fc.idfuente AND fc.idempresa = fc.idempresa "
				+ " where ind.idempresa = "
				+ idempresa.toString()
				+ "ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
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
			log.error("Error SQL en el metodo : getCrmindividuosAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmindividuosAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getCrmindividuosOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " SELECT idindividuos,razon_nombre,telefonos_part,celular,email,domicilio_part,fechanacimiento,empresa,domicilio_laboral,telefonos_empr,profesion,actividad,deportes,hobbies,actividad_social,diario_lectura,revista_lectura,lugar_veraneo,obseravaciones,usuario,tipocliente,clasificacioncliente,fuente,usuarioalt,usuarioact,fechaalt,fechaact FROM vcrmindividuos "
				+ " where idempresa = "
				+ idempresa.toString()
				+ " and (idindividuos::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " razon_nombre LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " telefonos_part LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " celular LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " email LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " domicilio_part LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " TO_CHAR(fechanacimiento, 'dd/mm/yyyy') LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " empresa LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " domicilio_laboral LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " telefonos_empr LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " profesion LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " actividad LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " deportes LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " hobbies LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " actividad_social LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " diario_lectura LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " revista_lectura LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " lugar_veraneo LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " obseravaciones LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " usuario LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " tipocliente LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " clasificacioncliente LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " UPPER(fuente) LIKE '%"
				+ ocurrencia.toUpperCase()
				+ "%') "
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
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
					.error("Error SQL en el metodo : getCrmindividuosOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmindividuosOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getCrmindividuosPK(BigDecimal idindividuos, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT ind.idindividuos, ind.razon_nombre, ind.telefonos_part, ind.celular, ind.email, ind.domicilio_part, ind.fechanacimiento, ind.empresa, ind.domicilio_laboral, ind.telefonos_empr, ind.profesion, ind.actividad, ind.deportes, ind.hobbies, ind.actividad_social, ind.diario_lectura, ind.revista_lectura, ind.lugar_veraneo, ind.obseravaciones, ind.idusuario, gu.usuario, ind.idtipocliente, tc.tipocliente, ind.idclasificacioncliente, cc.clasificacioncliente, ind.idfuente, fc.fuente, ind.idempresa, ind.usuarioalt, ind.usuarioact, ind.fechaalt, ind.fechaact FROM crmindividuos ind LEFT JOIN globalusuarios gu ON ind.idusuario = gu.idusuario AND gu.idempresa = ind.idempresa LEFT JOIN crmtiposclientes tc ON ind.idtipocliente = tc.idtipocliente AND tc.idempresa = ind.idempresa LEFT JOIN crmclasificacionclientes cc ON ind.idclasificacioncliente = cc.idclasificacioncliente AND cc.idempresa = ind.idempresa LEFT JOIN crmfuentecaptacion fc ON ind.idfuente = fc.idfuente AND fc.idempresa = fc.idempresa "
				+ " where ind.idindividuos = "
				+ idindividuos.toString()
				+ " and ind.idempresa = " + idempresa.toString();
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
					.error("Error SQL en el metodo : getCrmindividuosPK( BigDecimal idindividuos ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmindividuosPK( BigDecimal idindividuos )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String crmindividuosDelete(BigDecimal idindividuos,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM CRMINDIVIDUOS "
				+ " WHERE idindividuos = " + idindividuos.toString()
				+ " and idempresa = " + idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM CRMINDIVIDUOS "
						+ " WHERE idindividuos = " + idindividuos.toString()
						+ " and idempresa = " + idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : crmindividuosDelete( BigDecimal idindividuos ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: crmindividuosDelete( BigDecimal idindividuos )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String crmindividuosCreate(String razon_nombre,
			String telefonos_part, String celular, String email,
			String domicilio_part, Timestamp fechanacimiento, String empresa,
			String domicilio_laboral, String telefonos_empr, String profesion,
			String actividad, String deportes, String hobbies,
			String actividad_social, String diario_lectura,
			String revista_lectura, String lugar_veraneo,
			String obseravaciones, String idusuario, String idtipocliente,
			String idclasificacioncliente, String idfuente,
			BigDecimal idempresa, String usuarioalt) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (razon_nombre == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: razon_nombre ";
		if (telefonos_part == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: telefonos_part ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (razon_nombre.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: razon_nombre ";
		if (telefonos_part.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: telefonos_part ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO CRMINDIVIDUOS(razon_nombre, telefonos_part, celular, email, domicilio_part, fechanacimiento, empresa, domicilio_laboral, telefonos_empr, profesion, actividad, deportes, hobbies, actividad_social, diario_lectura, revista_lectura, lugar_veraneo, obseravaciones, idusuario, idtipocliente, idclasificacioncliente, idfuente, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, razon_nombre);
				insert.setString(2, telefonos_part);
				insert.setString(3, celular);
				insert.setString(4, email);
				insert.setString(5, domicilio_part);
				insert.setTimestamp(6, fechanacimiento);
				insert.setString(7, empresa);
				insert.setString(8, domicilio_laboral);
				insert.setString(9, telefonos_empr);
				insert.setString(10, profesion);
				insert.setString(11, actividad);
				insert.setString(12, deportes);
				insert.setString(13, hobbies);
				insert.setString(14, actividad_social);
				insert.setString(15, diario_lectura);
				insert.setString(16, revista_lectura);
				insert.setString(17, lugar_veraneo);
				insert.setString(18, obseravaciones);
				if (idusuario != "" && !idusuario.equalsIgnoreCase("")) {
					insert.setBigDecimal(19, new BigDecimal(idusuario));
				} else {
					insert.setBigDecimal(19, null);
				}
				if (idtipocliente != "" && !idtipocliente.equalsIgnoreCase("")) {
					insert.setBigDecimal(20, new BigDecimal(idtipocliente));
				} else {
					insert.setBigDecimal(20, null);
				}
				if (idclasificacioncliente != ""
						&& !idclasificacioncliente.equalsIgnoreCase("")) {
					insert.setBigDecimal(21, new BigDecimal(
							idclasificacioncliente));
				} else {
					insert.setBigDecimal(21, null);
				}
				if (idfuente != "" && !idfuente.equalsIgnoreCase("")) {
					insert.setBigDecimal(22, new BigDecimal(idfuente));
				} else {
					insert.setBigDecimal(22, null);
				}

				insert.setBigDecimal(23, idempresa);
				insert.setString(24, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String crmindividuosCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String crmindividuosCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String crmindividuosCreateOrUpdate(BigDecimal idindividuos,
			String razon_nombre, String telefonos_part, String celular,
			String email, String domicilio_part, Timestamp fechanacimiento,
			String empresa, String domicilio_laboral, String telefonos_empr,
			String profesion, String actividad, String deportes,
			String hobbies, String actividad_social, String diario_lectura,
			String revista_lectura, String lugar_veraneo,
			String obseravaciones, String idusuario, String idtipocliente,
			String idclasificacioncliente, String idfuente, String usuarioact,
			BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idindividuos == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idindividuos ";
		if (razon_nombre == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: razon_nombre ";
		if (telefonos_part == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: telefonos_part ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (razon_nombre.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: razon_nombre ";
		if (telefonos_part.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: telefonos_part ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM crmindividuos "
					+ " WHERE idindividuos = " + idindividuos.toString()
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
					sql = "UPDATE CRMINDIVIDUOS SET razon_nombre=?, telefonos_part=?, celular=?, email=?, domicilio_part=?, fechanacimiento=?, empresa=?, domicilio_laboral=?, telefonos_empr=?, profesion=?, actividad=?, deportes=?, hobbies=?, actividad_social=?, diario_lectura=?, revista_lectura=?, lugar_veraneo=?, obseravaciones=?, idusuario=?, idtipocliente=?, idclasificacioncliente=?, idfuente=?, usuarioact=?, fechaact=? WHERE idindividuos=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, razon_nombre);
					insert.setString(2, telefonos_part);
					insert.setString(3, celular);
					insert.setString(4, email);
					insert.setString(5, domicilio_part);
					insert.setTimestamp(6, fechanacimiento);
					insert.setString(7, empresa);
					insert.setString(8, domicilio_laboral);
					insert.setString(9, telefonos_empr);
					insert.setString(10, profesion);
					insert.setString(11, actividad);
					insert.setString(12, deportes);
					insert.setString(13, hobbies);
					insert.setString(14, actividad_social);
					insert.setString(15, diario_lectura);
					insert.setString(16, revista_lectura);
					insert.setString(17, lugar_veraneo);
					insert.setString(18, obseravaciones);
					if (idusuario != "" && !idusuario.equalsIgnoreCase("")) {
						insert.setBigDecimal(19, new BigDecimal(idusuario));
					} else {
						insert.setBigDecimal(19, null);
					}
					if (idtipocliente != ""
							&& !idtipocliente.equalsIgnoreCase("")) {
						insert.setBigDecimal(20, new BigDecimal(idtipocliente));
					} else {
						insert.setBigDecimal(20, null);
					}
					if (idclasificacioncliente != ""
							&& !idclasificacioncliente.equalsIgnoreCase("")) {
						insert.setBigDecimal(21, new BigDecimal(
								idclasificacioncliente));
					} else {
						insert.setBigDecimal(21, null);
					}
					if (idfuente != "" && !idfuente.equalsIgnoreCase("")) {
						insert.setBigDecimal(22, new BigDecimal(idfuente));
					} else {
						insert.setBigDecimal(22, null);
					}
					insert.setString(23, usuarioact);
					insert.setTimestamp(24, fechaact);
					insert.setBigDecimal(25, idindividuos);
					insert.setBigDecimal(26, idempresa);
				} else {
					String ins = "INSERT INTO CRMINDIVIDUOS(razon_nombre, telefonos_part, celular, email, domicilio_part, fechanacimiento, empresa, domicilio_laboral, telefonos_empr, profesion, actividad, deportes, hobbies, actividad_social, diario_lectura, revista_lectura, lugar_veraneo, obseravaciones, idusuario, idtipocliente, idclasificacioncliente, idfuente, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, razon_nombre);
					insert.setString(2, telefonos_part);
					insert.setString(3, celular);
					insert.setString(4, email);
					insert.setString(5, domicilio_part);
					insert.setTimestamp(6, fechanacimiento);
					insert.setString(7, empresa);
					insert.setString(8, domicilio_laboral);
					insert.setString(9, telefonos_empr);
					insert.setString(10, profesion);
					insert.setString(11, actividad);
					insert.setString(12, deportes);
					insert.setString(13, hobbies);
					insert.setString(14, actividad_social);
					insert.setString(15, diario_lectura);
					insert.setString(16, revista_lectura);
					insert.setString(17, lugar_veraneo);
					insert.setString(18, obseravaciones);
					if (idusuario != "" && !idusuario.equalsIgnoreCase("")) {
						insert.setBigDecimal(19, new BigDecimal(idusuario));
					} else {
						insert.setBigDecimal(19, null);
					}
					if (idtipocliente != ""
							&& !idtipocliente.equalsIgnoreCase("")) {
						insert.setBigDecimal(20, new BigDecimal(idtipocliente));
					} else {
						insert.setBigDecimal(20, null);
					}
					if (idclasificacioncliente != ""
							&& !idclasificacioncliente.equalsIgnoreCase("")) {
						insert.setBigDecimal(21, new BigDecimal(
								idclasificacioncliente));
					} else {
						insert.setBigDecimal(21, null);
					}
					if (idfuente != "" && !idfuente.equalsIgnoreCase("")) {
						insert.setBigDecimal(22, new BigDecimal(idfuente));
					} else {
						insert.setBigDecimal(22, null);
					}
					insert.setString(24, usuarioalt);
					insert.setBigDecimal(25, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String crmindividuosCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String crmindividuosCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String crmindividuosUpdate(BigDecimal idindividuos,
			String razon_nombre, String telefonos_part, String celular,
			String email, String domicilio_part, Timestamp fechanacimiento,
			String empresa, String domicilio_laboral, String telefonos_empr,
			String profesion, String actividad, String deportes,
			String hobbies, String actividad_social, String diario_lectura,
			String revista_lectura, String lugar_veraneo,
			String obseravaciones, String idusuario, String idtipocliente,
			String idclasificacioncliente, String idfuente, String usuarioact,
			BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idindividuos == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idindividuos ";
		if (razon_nombre == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: razon_nombre ";
		if (telefonos_part == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: telefonos_part ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (razon_nombre.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: razon_nombre ";
		if (telefonos_part.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: telefonos_part ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM crmindividuos "
					+ " WHERE idindividuos = " + idindividuos.toString()
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
					sql = "UPDATE CRMINDIVIDUOS SET razon_nombre=?, telefonos_part=?, celular=?, email=?, domicilio_part=?, fechanacimiento=?, empresa=?, domicilio_laboral=?, telefonos_empr=?, profesion=?, actividad=?, deportes=?, hobbies=?, actividad_social=?, diario_lectura=?, revista_lectura=?, lugar_veraneo=?, obseravaciones=?, idusuario=?, idtipocliente=?, idclasificacioncliente=?, idfuente=?, usuarioact=?, fechaact=? WHERE idindividuos=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, razon_nombre);
					insert.setString(2, telefonos_part);
					insert.setString(3, celular);
					insert.setString(4, email);
					insert.setString(5, domicilio_part);
					insert.setTimestamp(6, fechanacimiento);
					insert.setString(7, empresa);
					insert.setString(8, domicilio_laboral);
					insert.setString(9, telefonos_empr);
					insert.setString(10, profesion);
					insert.setString(11, actividad);
					insert.setString(12, deportes);
					insert.setString(13, hobbies);
					insert.setString(14, actividad_social);
					insert.setString(15, diario_lectura);
					insert.setString(16, revista_lectura);
					insert.setString(17, lugar_veraneo);
					insert.setString(18, obseravaciones);
					if (idusuario != null
							&& !idusuario.equalsIgnoreCase("null")) {
						insert.setBigDecimal(19, new BigDecimal(idusuario));
					} else {
						insert.setBigDecimal(19, null);
					}
					if (idtipocliente != null
							&& !idtipocliente.equalsIgnoreCase("null")) {
						insert.setBigDecimal(20, new BigDecimal(idtipocliente));
					} else {
						insert.setBigDecimal(20, null);
					}
					if (idclasificacioncliente != null
							&& !idclasificacioncliente.equalsIgnoreCase("null")) {
						insert.setBigDecimal(21, new BigDecimal(
								idclasificacioncliente));
					} else {
						insert.setBigDecimal(21, null);
					}
					if (idfuente != null && !idfuente.equalsIgnoreCase("null")) {
						insert.setBigDecimal(22, new BigDecimal(idfuente));
					} else {
						insert.setBigDecimal(22, null);
					}
					insert.setString(23, usuarioact);
					insert.setTimestamp(24, fechaact);
					insert.setBigDecimal(25, idindividuos);
					insert.setBigDecimal(26, idempresa);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String crmindividuosUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String crmindividuosUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// Familiares x individuo
	public List getCrmfamiliaresxindividuoAll(long limit, long offset,
			BigDecimal idempresa, String idindividuos) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " SELECT fam.idfamiliar,anf.nexofamiliar,fam.nombre,fam.profesion,fam.actividad,fam.email,fam.telefonos_part,fam.celular,fam.fechanacimiento,fam.deportes,fam.hobbies,fam.actividad_social,fam.obseravaciones,fam.idempresa,fam.usuarioalt,fam.usuarioact,fam.fechaalt,fam.fechaact  FROM CRMFAMILIARES fam LEFT JOIN crmindividuos ind ON fam.idindividuos = ind.idindividuos  AND fam.idempresa = ind.idempresa LEFT JOIN crmnexosfamiliares anf ON fam.idnexofamiliar = anf.idnexofamiliar AND fam.idempresa = anf.idempresa  "
				+ " where ind.idempresa = "
				+ idempresa.toString()
				+ " and fam.idindividuos = "
				+ idindividuos.toString()
				+ "ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
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
					.error("Error SQL en el metodo : getCrmfamiliaresxindividuoAll() "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmfamiliaresxindividuoAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getCrmfamiliaresxindividuoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa, String idindividuos)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " SELECT idfamiliar,nexofamiliar,nombre,profesion,actividad,email,telefonos_part,celular,fechanacimiento,deportes,hobbies,actividad_social,obseravaciones,idempresa,usuarioalt,usuarioact,fechaalt,fechaact  FROM vcrmfamiliares "
				+ " where idempresa= "
				+ idempresa.toString()
				+ " and idindividuos = "
				+ idindividuos.toString()
				+ " and (idfamiliar::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " nexofamiliar LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " nombre LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " profesion LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " actividad LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " email LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " telefonos_part LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " celular LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " fechanacimiento LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " deportes LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " hobbies LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " actividad_social LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " UPPER(obseravaciones) LIKE '%"
				+ ocurrencia.toUpperCase()
				+ "%') "
				+ " ORDER BY 2  LIMIT "
				+ limit
				+ " OFFSET  "
				+ offset
				+ ";";
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
					.error("Error SQL en el metodo : getCrmfamiliaresxindividuoOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmfamiliaresxindividuoOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	public long getTotalEntidadxindividuo(String entidad, BigDecimal idempresa,
			String idindividuos) throws EJBException {

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
				+ " WHERE idempresa = " + idempresa.toString()
				+ " and idindividuos = " + idindividuos.toString();
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				total = rsSalida.getLong("total");
			} else {
				log
						.warn("getTotalEntidadxindividuo()- Error al recuperar total: "
								+ entidad);
			}
		} catch (SQLException sqlException) {
			log
					.error("getTotalEntidadxindividuo()- Error SQL: "
							+ sqlException);
		} catch (Exception ex) {
			log.error("getTotalEntidadxindividuo()- Salida por exception: "
					+ ex);
		}
		return total;
	}

	public long getTotalEntidadOcuxindividuo(String entidad, String[] campos,
			String ocurrencia, BigDecimal idempresa, String idindividuos)
			throws EJBException {

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
			cQuery += "(" + like + ") AND idempresa = " + idempresa.toString()
					+ " and idindividuos = " + idindividuos.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				total = rsSalida.getLong("total");
			} else {
				log
						.warn("getTotalEntidadOcuxindividuo()- Error al recuperar total: "
								+ entidad);
			}
		} catch (SQLException sqlException) {
			log.error("getTotalEntidadOcuxindividuo()- Error SQL: "
					+ sqlException);
		} catch (Exception ex) {
			log.error("getTotalEntidadOcuxindividuo()- Salida por exception: "
					+ ex);
		}
		return total;
	}

	// LLamados
	public List getCrmllamadosxusuarioAll(long limit, long offset,
			BigDecimal idempresa, String idindividuos) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " "
				+ "SELECT ll.idllamado,tll.tipollamada,gu.usuario,fam.nombre,ll.fechallamada,ll.obseravaciones,"
				+ "       re.idresultadollamada, re.resultadollamada, ll.fecharellamada,"
				+ "       ll.idempresa,ll.usuarioalt,ll.usuarioact,ll.fechaalt,ll.fechaact "
				+ "  FROM CRMLLAMADOS ll LEFT JOIN crmtiposllamadas tll ON ll.idtipollamada = tll.idtipollamada  AND ll.idempresa = tll.idempresa "
				+ "       LEFT JOIN globalusuarios gu ON ll.idusuario = gu.idusuario  AND ll.idempresa = gu.idempresa "
				+ "       LEFT JOIN crmindividuos ind ON ll.idindividuos = ind.idindividuos  AND ll.idempresa = ind.idempresa "
				+ "       LEFT JOIN crmfamiliares fam ON ll.idfamiliar = fam.idfamiliar  AND ll.idempresa = fam.idempresa "
				+ "       INNER JOIN crmresultadosllamadas re ON ll.idresultadollamada = re.idresultadollamada  AND ll.idempresa = re.idempresa"
				+ " WHERE ll.idempresa = " + idempresa.toString()
				+ "   AND ll.idindividuos = " + idindividuos.toString()
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
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
			log.error("Error SQL en el metodo : getCrmllamadosxusuarioAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmllamadosxusuarioAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getCrmllamadosxusuarioOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa, String idindividuos)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " "
				+ "SELECT idllamado,tipollamada,usuario,nombre,fechallamada,obseravaciones,"
				+ "       idresultadollamada, resultadollamada, fecharellmada "
				+ "       idempresa,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM vcrmllamados " + " WHERE idempresa= "
				+ idempresa.toString() + " and idindividuos = "
				+ idindividuos.toString() + " and (idllamado::VARCHAR LIKE '%"
				+ ocurrencia + "%' OR " + " tipollamada LIKE '%" + ocurrencia
				+ "%' OR " + " usuario LIKE '%" + ocurrencia + "%' OR "
				+ " razon_nombre LIKE '%" + ocurrencia + "%' OR "
				+ " nombre LIKE '%" + ocurrencia + "%' OR "
				+ " fechallamada LIKE '%" + ocurrencia + "%' OR "
				+ " UPPER(obseravaciones) LIKE '%" + ocurrencia.toUpperCase()
				+ "%') " + " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset
				+ ";";
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
					.error("Error SQL en el metodo : getCrmllamadosxusuarioOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmllamadosxusuarioOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// Cotizaciones x individuo
	public List getCrmcotizacionesxusuarioAll(long limit, long offset,
			BigDecimal idempresa, String idindividuos) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " "
				+ "SELECT cot.idcotizacion,gu.usuario,cot.nrolote,tc.tipocotizacion,tf.tipofinanciacion,"
				+ "       cot.superficie,sm.descrip_md,cot.valor_unitario,cot.valor_total,cot.precio_contado,"
				+ "       cot.precio_financiado,ind.razon_nombre,cot.idempresa, rc.idresultadocotizacion, rc.resultadocotizacion, "
				+ "       cot.usuarioalt,cot.usuarioact,cot.fechaalt,cot.fechaact "
				+ "  FROM CRMCOTIZACIONES  cot "
				+ "       LEFT JOIN globalusuarios gu ON cot.idusuario = gu.idusuario AND cot.idempresa = gu.idempresa "
				+ "       LEFT JOIN crmtiposcotizaciones tc ON cot.idtipocotizacion = tc.idtipocotizacion AND cot.idempresa = tc.idempresa "
				+ "       LEFT JOIN crmtiposfinanciaciones tf ON cot.idtipofinanciacion = tf.idtipofinanciacion AND cot.idempresa = tf.idempresa "
				+ "       LEFT JOIN stockmedidas sm ON cot.codigo_md = sm.codigo_md AND cot.idempresa = sm.idempresa "
				+ "       LEFT JOIN crmindividuos ind ON cot.idindividuos = ind.idindividuos AND cot.idempresa = ind.idempresa  "
				+ "       LEFT JOIN crmresultadoscotizaciones rc ON cot.idresultadocotizacion = rc.idresultadocotizacion  AND cot.idempresa = rc.idempresa  "
				+ " where cot.idempresa = " + idempresa.toString()
				+ " and cot.idindividuos =  " + idindividuos.toString()
				+ "ORDER BY 1 desc  LIMIT " + limit + " OFFSET  " + offset
				+ ";";
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
					.error("Error SQL en el metodo : getCrmcotizacionesxusuarioAll() "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmcotizacionesxusuarioAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getCrmcotizacionesxusuarioOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa, String idindividuos)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idcotizacion,usuario,nrolote,tipocotizacion,tipofinanciacion,superficie,descrip_md,"
				+ "       valor_unitario,valor_total,precio_contado,precio_financiado,razon_nombre,idempresa,"
				+ "       usuarioalt,usuarioact,fechaalt,fechaact "
				+ " FROM vcrmcotizaciones " + " where idempresa= "
				+ idempresa.toString() + " and idindividuos = "
				+ idindividuos.toString() + " and (idcotizacion::VARCHAR LIKE '%"
				+ ocurrencia + "%' OR " + " usuario LIKE '%" + ocurrencia
				+ "%' OR " + " nrolote::VARCHAR LIKE '%" + ocurrencia + "%' OR "
				+ " tipocotizacion LIKE '%" + ocurrencia + "%' OR "
				+ " tipofinanciacion LIKE '%" + ocurrencia + "%' OR "
				+ " superficie::VARCHAR LIKE '%" + ocurrencia + "%' OR "
				+ " descrip_md LIKE '%" + ocurrencia + "%' OR "
				+ " valor_unitario::VARCHAR LIKE '%" + ocurrencia + "%' OR "
				+ " valor_total::VARCHAR LIKE '%" + ocurrencia + "%' OR "
				+ " precio_contado::VARCHAR LIKE '%" + ocurrencia + "%' OR "
				+ " razon_nombre LIKE '%" + ocurrencia + "%' OR "
				+ " UPPER(precio_financiado::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase() + "%') "
				+ " ORDER BY 1 desc   LIMIT " + limit + " OFFSET  " + offset
				+ ";";
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
					.error("Error SQL en el metodo : getCrmcotizacionesxusuarioOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmcotizacionesxusuarioOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	public long getTotalEntidadxusuario(BigDecimal idempresa, String idusuario)
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
		String cQuery = " select count(1) AS total from crmindividuos ind LEFT JOIN globalusuarios gu ON ind.idusuario = gu.idusuario AND gu.idempresa = ind.idempresa LEFT JOIN crmtiposclientes tc ON ind.idtipocliente = tc.idtipocliente AND tc.idempresa = ind.idempresa LEFT JOIN crmclasificacionclientes cc ON ind.idclasificacioncliente = cc.idclasificacioncliente AND cc.idempresa = ind.idempresa LEFT JOIN crmfuentecaptacion fc ON ind.idfuente = fc.idfuente AND fc.idempresa = fc.idempresa  "
				+ " where gu.usuario = '"
				+ idusuario.toString()
				+ "' and ind.idempresa = " + idempresa.toString();
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				total = rsSalida.getLong("total");
			} else {
				log
						.warn("getTotalEntidadxusuario()- Error al recuperar total: ");
			}
		} catch (SQLException sqlException) {
			log.error("getTotalEntidadxusuario()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getTotalEntidadxusuario()- Salida por exception: " + ex);
		}
		return total;
	}

	public long getTotalEntidadxusuarioocu(String[] campos, String ocurrencia,
			BigDecimal idempresa, String idusuario) throws EJBException {

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
		String cQuery = " SELECT count(1)AS total FROM vcrmindividuos WHERE ";
		String like = "";
		int len = campos.length;

		try {
			for (int i = 0; i < len; i++) {
				like += "UPPER(" + campos[i] + "::VARCHAR) LIKE '%"
						+ ocurrencia.toUpperCase() + "%' ";
				if (i + 1 < len)
					like += " OR ";
			}
			cQuery += "(" + like + ") AND usuario = '" + idusuario.toString()
					+ "' and idempresa = " + idempresa.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				total = rsSalida.getLong("total");
			} else {
				log
						.warn("getTotalEntidadxusuarioocu()- Error al recuperar total: ");
			}
		} catch (SQLException sqlException) {
			log.error("getTotalEntidadxusuarioocu()- Error SQL: "
					+ sqlException);
		} catch (Exception ex) {
			log.error("getTotalEntidadxusuarioocu()- Salida por exception: "
					+ ex);
		}
		return total;
	}

	/**
	 * Metodos para la entidad: crmProductosStatus Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Fri Aug 03 10:09:41 ART 2007
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getCrmProductosStatusAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idproductostatus,productostatus,idempresa,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM CRMPRODUCTOSSTATUS WHERE idempresa = "
				+ idempresa.toString() + "  ORDER BY 2  LIMIT " + limit
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
			log.error("Error SQL en el metodo : getCrmProductosStatusAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmProductosStatusAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getCrmProductosStatusOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idproductostatus,productostatus,idempresa,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM crmproductosstatus "
				+ " WHERE (UPPER(productostatus) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(idproductostatus::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%')  AND idempresa = "
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
					.error("Error SQL en el metodo : getCrmProductosStatusOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmProductosStatusOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getCrmProductosStatusPK(BigDecimal idproductostatus,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idproductostatus,productostatus,idempresa,usuarioalt,usuarioact,fechaalt,fechaact"
				+ "  FROM CRMPRODUCTOSSTATUS WHERE idproductostatus="
				+ idproductostatus.toString() + " AND idempresa = "
				+ idempresa.toString() + ";";
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
					.error("Error SQL en el metodo : getCrmProductosStatusPK( BigDecimal idproductostatus ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmProductosStatusPK( BigDecimal idproductostatus )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String crmProductosStatusDelete(BigDecimal idproductostatus)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM CRMPRODUCTOSSTATUS WHERE idproductostatus="
				+ idproductostatus.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM CRMPRODUCTOSSTATUS WHERE idproductostatus="
						+ idproductostatus.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : crmProductosStatusDelete( BigDecimal idproductostatus ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: crmProductosStatusDelete( BigDecimal idproductostatus )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String crmProductosStatusCreate(String productostatus,
			BigDecimal idempresa, String usuarioalt) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (productostatus == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: productostatus ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (productostatus.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: productostatus ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = ""
						+ "INSERT INTO CRMPRODUCTOSSTATUS(productostatus, idempresa, usuarioalt ) "
						+ "VALUES (?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, productostatus);
				insert.setBigDecimal(2, idempresa);
				insert.setString(3, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String crmProductosStatusCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String crmProductosStatusCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String crmProductosStatusCreateOrUpdate(BigDecimal idproductostatus,
			String productostatus, BigDecimal idempresa, String usuarioact)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idproductostatus == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idproductostatus ";
		if (productostatus == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: productostatus ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (productostatus.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: productostatus ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM crmProductosStatus WHERE idproductostatus = "
					+ idproductostatus.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE CRMPRODUCTOSSTATUS SET productostatus=?, idempresa=?, usuarioact=?, fechaact=? WHERE idproductostatus=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, productostatus);
					insert.setBigDecimal(2, idempresa);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, idproductostatus);
				} else {
					String ins = "INSERT INTO CRMPRODUCTOSSTATUS(productostatus, idempresa, usuarioalt ) VALUES (?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, productostatus);
					insert.setBigDecimal(2, idempresa);
					insert.setString(3, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String crmProductosStatusCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String crmProductosStatusCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String crmProductosStatusUpdate(BigDecimal idproductostatus,
			String productostatus, BigDecimal idempresa, String usuarioact)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idproductostatus == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idproductostatus ";
		if (productostatus == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: productostatus ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (productostatus.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: productostatus ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM crmProductosStatus WHERE idproductostatus = "
					+ idproductostatus.toString();
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
							+ "UPDATE CRMPRODUCTOSSTATUS "
							+ "   SET productostatus=?, idempresa=?, usuarioact=?, fechaact=?"
							+ " WHERE idproductostatus=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, productostatus);
					insert.setBigDecimal(2, idempresa);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, idproductostatus);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String crmProductosStatusUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String crmProductosStatusUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * Metodos para la entidad: crmGruposProductos Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Fri Aug 03 11:04:08 ART 2007
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getCrmGruposProductosAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idgrupoproducto,grupoproducto,idempresa,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM CRMGRUPOSPRODUCTOS " + " WHERE idempresa = "
				+ idempresa.toString() + "  ORDER BY 2  LIMIT " + limit
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
			log.error("Error SQL en el metodo : getCrmGruposProductosAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmGruposProductosAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getCrmGruposProductosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idgrupoproducto,grupoproducto,idempresa,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM CRMGRUPOSPRODUCTOS "
				+ " WHERE (UPPER(idgrupoproducto::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(grupoproducto) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%')  AND idempresa = "
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
					.error("Error SQL en el metodo : getCrmGruposProductosOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmGruposProductosOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getCrmGruposProductosPK(BigDecimal idgrupoproducto,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idgrupoproducto,grupoproducto,idempresa,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM CRMGRUPOSPRODUCTOS " + " WHERE idgrupoproducto="
				+ idgrupoproducto.toString() + " AND idempresa = "
				+ idempresa.toString() + ";";
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
					.error("Error SQL en el metodo : getCrmGruposProductosPK( BigDecimal idgrupoproducto ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmGruposProductosPK( BigDecimal idgrupoproducto )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String crmGruposProductosDelete(BigDecimal idgrupoproducto)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM CRMGRUPOSPRODUCTOS WHERE idgrupoproducto="
				+ idgrupoproducto.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM CRMGRUPOSPRODUCTOS WHERE idgrupoproducto="
						+ idgrupoproducto.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : crmGruposProductosDelete( BigDecimal idgrupoproducto ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: crmGruposProductosDelete( BigDecimal idgrupoproducto )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String crmGruposProductosCreate(String grupoproducto,
			BigDecimal idempresa, String usuarioalt) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (grupoproducto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: grupoproducto ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (grupoproducto.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: grupoproducto ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = ""
						+ "INSERT INTO CRMGRUPOSPRODUCTOS(grupoproducto, idempresa, usuarioalt )"
						+ "VALUES (?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, grupoproducto);
				insert.setBigDecimal(2, idempresa);
				insert.setString(3, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String crmGruposProductosCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String crmGruposProductosCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String crmGruposProductosCreateOrUpdate(BigDecimal idgrupoproducto,
			String grupoproducto, BigDecimal idempresa, String usuarioact)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idgrupoproducto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idgrupoproducto ";
		if (grupoproducto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: grupoproducto ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (grupoproducto.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: grupoproducto ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM crmGruposProductos WHERE idgrupoproducto = "
					+ idgrupoproducto.toString();
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
							+ "UPDATE CRMGRUPOSPRODUCTOS "
							+ "   SET grupoproducto=?, idempresa=?, usuarioact=?, fechaact=?"
							+ " WHERE idgrupoproducto=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, grupoproducto);
					insert.setBigDecimal(2, idempresa);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, idgrupoproducto);
				} else {
					String ins = ""
							+ "INSERT INTO CRMGRUPOSPRODUCTOS(grupoproducto, idempresa, usuarioalt ) "
							+ "VALUES (?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, grupoproducto);
					insert.setBigDecimal(2, idempresa);
					insert.setString(3, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String crmGruposProductosCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String crmGruposProductosCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String crmGruposProductosUpdate(BigDecimal idgrupoproducto,
			String grupoproducto, BigDecimal idempresa, String usuarioact)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idgrupoproducto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idgrupoproducto ";
		if (grupoproducto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: grupoproducto ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (grupoproducto.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: grupoproducto ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM crmGruposProductos WHERE idgrupoproducto = "
					+ idgrupoproducto.toString();
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
							+ "UPDATE CRMGRUPOSPRODUCTOS "
							+ "   SET grupoproducto=?, idempresa=?, usuarioact=?, fechaact=? "
							+ " WHERE idgrupoproducto=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, grupoproducto);
					insert.setBigDecimal(2, idempresa);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, idgrupoproducto);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String crmGruposProductosUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String crmGruposProductosUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * Metodos para la entidad: crmProductos Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Fri Aug 03 11:44:52 ART 2007
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getCrmProductosAll(long limit, long offset, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT cp.nrolote,cp.idfamiliacotizacion, ctc.tipocotizacion,"
				+ "       cp.idgrupoproducto, cgp.grupoproducto,cp.idproductostatus, cps.productostatus,cp.calificacion,"
				+ "       cp.superficie, cp.precioxmts, cp.precio,cp.valorcontado,cp.boleto,cuotasx36,"
				+ "       cp.idempresa, ctc.valorunidadsup, cp.usuarioalt,cp.usuarioact,cp.fechaalt,cp.fechaact "
				+ "  FROM crmproductos cp "
				+ "       INNER JOIN crmtiposcotizaciones ctc ON cp.idfamiliacotizacion = ctc.idtipocotizacion AND cp.idempresa = ctc.idempresa "
				+ "       INNER JOIN crmgruposproductos cgp ON cp.idgrupoproducto = cgp.idgrupoproducto AND cp.idempresa = cgp.idempresa "
				+ "       INNER JOIN crmproductosstatus cps ON cp.idproductostatus = cps.idproductostatus AND cp.idempresa = cps.idempresa "
				+ " WHERE cp.idempresa = " + idempresa.toString()
				+ "  ORDER BY cp.nrolote  LIMIT " + limit + " OFFSET  "
				+ offset + ";";
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
			log.error("Error SQL en el metodo : getCrmProductosAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmProductosAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getCrmProductosOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT cp.nrolote,cp.idfamiliacotizacion, ctc.tipocotizacion,"
				+ "       cp.idgrupoproducto, cgp.grupoproducto,cp.idproductostatus, cps.productostatus,cp.calificacion,"
				+ "       cp.superficie, cp.precioxmts, cp.precio,cp.valorcontado,cp.boleto,cuotasx36,"
				+ "       cp.idempresa, ctc.valorunidadsup, cp.usuarioalt,cp.usuarioact,cp.fechaalt,cp.fechaact "
				+ "  FROM crmproductos cp "
				+ "       INNER JOIN crmtiposcotizaciones ctc ON cp.idfamiliacotizacion = ctc.idtipocotizacion AND cp.idempresa = ctc.idempresa "
				+ "       INNER JOIN crmgruposproductos cgp ON cp.idgrupoproducto = cgp.idgrupoproducto AND cp.idempresa = cgp.idempresa "
				+ "       INNER JOIN crmproductosstatus cps ON cp.idproductostatus = cps.idproductostatus AND cp.idempresa = cps.idempresa "
				+ " WHERE (UPPER(cp.nrolote::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%')  AND cp.idempresa = "
				+ idempresa.toString() + " ORDER BY cp.nrolote  LIMIT " + limit
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
					.error("Error SQL en el metodo : getCrmProductosOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmProductosOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getCrmProductosPK(BigDecimal nrolote, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT cp.nrolote, cp.idfamiliacotizacion, cp.idgrupoproducto, cp.idproductostatus,"
				+ "       cp.calificacion,cp.superficie, cp.precioxmts,cp.precio,cp.valorcontado,"
				+ "       cp.boleto, cp.cuotasx36, cp.idempresa, ctc.valorunidadsup, "
				+ "       cp.usuarioalt, cp.usuarioact, cp.fechaalt, cp.fechaact "
				+ "  FROM crmproductos cp INNER JOIN crmtiposcotizaciones ctc ON cp.idfamiliacotizacion = ctc.idtipocotizacion  AND cp.idempresa = ctc.idempresa"
				+ " WHERE nrolote=" + nrolote.toString()
				+ " AND cp.idempresa = " + idempresa.toString() + ";";
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
					.error("Error SQL en el metodo : getCrmProductosPK( BigDecimal nrolote ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmProductosPK( BigDecimal nrolote )  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public boolean isExisteCrmProductos(BigDecimal nrolote, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsExiste = null;
		boolean existe = false;
		String cQuery = "" + "SELECT 1 FROM CRMPRODUCTOS " + " WHERE nrolote="
				+ nrolote.toString() + " AND idempresa = "
				+ idempresa.toString() + ";";
		try {
			Statement statement = dbconn.createStatement();
			rsExiste = statement.executeQuery(cQuery);
			if (rsExiste != null) {

				if (rsExiste.next()) {
					existe = true;
				}
			} else {
				log.warn("isExisteCrmProductos(...): rsExiste NULO.");
			}

		} catch (SQLException sqlException) {
			log.error("Error SQL en el metodo : isExisteCrmProductos( ... ) "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: isExisteCrmProductos( ... )  "
							+ ex);
		}
		return existe;
	}

	// por primary key (primer campo por defecto)

	public BigDecimal getProximoNrolote(BigDecimal idempresa)
			throws EJBException {
		ResultSet rsNrolote = null;
		BigDecimal nrolote = new BigDecimal(0);
		String cQuery = ""
				+ "SELECT COALESCE(MAX(nrolote), 0)  + 1 AS nrolote FROM crmproductos"
				+ " WHERE idempresa = " + idempresa.toString() + ";";
		try {
			Statement statement = dbconn.createStatement();
			rsNrolote = statement.executeQuery(cQuery);
			if (rsNrolote != null) {

				if (rsNrolote.next()) {
					nrolote = rsNrolote.getBigDecimal(1);
					;
				}
			} else {
				log.warn("getProximoNrolote(...): rsNrolote NULO.");
			}

		} catch (SQLException sqlException) {
			log.error("Error SQL en el metodo : getProximoNrolote( ... ) "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getProximoNrolote( ... )  "
							+ ex);
		}
		return nrolote;
	}

	//	
	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String crmProductosDelete(BigDecimal nrolote) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM CRMPRODUCTOS WHERE nrolote="
				+ nrolote.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM CRMPRODUCTOS WHERE nrolote="
						+ nrolote.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : crmProductosDelete( BigDecimal nrolote ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: crmProductosDelete( BigDecimal nrolote )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String crmProductosCreate(BigDecimal nrolote,
			BigDecimal idfamiliacotizacion, BigDecimal idgrupoproducto,
			BigDecimal idproductostatus, String calificacion,
			Double superficie, Double precioxmts, Double precio,
			Double valorcontado, Double boleto, Double cuotasx36,
			BigDecimal idempresa, String usuarioalt) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos

		if (nrolote == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nrolote ";
		if (isExisteCrmProductos(nrolote, idempresa)) {
			salida = "Error: El lote ingresado ya existe. ";
		}
		if (idfamiliacotizacion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idfamiliacotizacion ";
		if (idgrupoproducto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idgrupoproducto ";
		if (calificacion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: calificacion ";
		if (superficie == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: superficie ";
		if (precioxmts == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: precioxmts ";
		if (precio == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: precio ";
		if (valorcontado == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: valorcontado ";
		if (boleto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: boleto ";
		if (cuotasx36 == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cuotasx36 ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (calificacion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: calificacion ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = ""
						+ "INSERT INTO CRMPRODUCTOS"
						+ "        (nrolote, idfamiliacotizacion, idgrupoproducto, idproductostatus, calificacion, "
						+ "         superficie, precioxmts, precio, valorcontado, boleto, cuotasx36, idempresa, usuarioalt )"
						+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, nrolote);
				insert.setBigDecimal(2, idfamiliacotizacion);
				insert.setBigDecimal(3, idgrupoproducto);
				insert.setBigDecimal(4, idproductostatus);
				insert.setString(5, calificacion);
				insert.setDouble(6, superficie.doubleValue());
				insert.setDouble(7, precioxmts.doubleValue());
				insert.setDouble(8, precio.doubleValue());
				insert.setDouble(9, valorcontado.doubleValue());
				insert.setDouble(10, boleto.doubleValue());
				insert.setDouble(11, cuotasx36.doubleValue());
				insert.setBigDecimal(12, idempresa);
				insert.setString(13, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String crmProductosCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error excepcion public String crmProductosCreate(.....)"
					+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String crmProductosCreateOrUpdate(BigDecimal nrolote,
			BigDecimal idfamiliacotizacion, BigDecimal idgrupoproducto,
			BigDecimal idproductostatus, String calificacion,
			Double superficie, Double precioxmts, Double precio,
			Double valorcontado, Double boleto, Double cuotasx36,
			BigDecimal idempresa, String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (nrolote == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nrolote ";
		if (idfamiliacotizacion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idfamiliacotizacion ";
		if (idgrupoproducto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idgrupoproducto ";
		if (calificacion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: calificacion ";
		if (superficie == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: superficie ";
		if (precioxmts == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: precioxmts ";
		if (precio == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: precio ";
		if (valorcontado == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: valorcontado ";
		if (boleto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: boleto ";
		if (cuotasx36 == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cuotasx36 ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (calificacion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: calificacion ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM crmProductos WHERE nrolote = "
					+ nrolote.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE CRMPRODUCTOS SET idfamiliacotizacion=?, idgrupoproducto=?, idproductostatus=?, calificacion=?, superficie=?, precioxmts=?, precio=?, valorcontado=?, boleto=?, cuotasx36=?, idempresa=?, usuarioact=?, fechaact=? WHERE nrolote=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, idfamiliacotizacion);
					insert.setBigDecimal(2, idgrupoproducto);
					insert.setBigDecimal(3, idproductostatus);
					insert.setString(4, calificacion);
					insert.setDouble(5, superficie.doubleValue());
					insert.setDouble(6, precioxmts.doubleValue());
					insert.setDouble(7, precio.doubleValue());
					insert.setDouble(8, valorcontado.doubleValue());
					insert.setDouble(9, boleto.doubleValue());
					insert.setDouble(10, cuotasx36.doubleValue());
					insert.setBigDecimal(11, idempresa);
					insert.setString(12, usuarioact);
					insert.setTimestamp(13, fechaact);
					insert.setBigDecimal(14, nrolote);
				} else {
					String ins = "INSERT INTO CRMPRODUCTOS(idfamiliacotizacion, idgrupoproducto, idproductostatus, calificacion, superficie, precioxmts, precio, valorcontado, boleto, cuotasx36, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setBigDecimal(1, idfamiliacotizacion);
					insert.setBigDecimal(2, idgrupoproducto);
					insert.setBigDecimal(3, idproductostatus);
					insert.setString(4, calificacion);
					insert.setDouble(5, superficie.doubleValue());
					insert.setDouble(6, precioxmts.doubleValue());
					insert.setDouble(7, precio.doubleValue());
					insert.setDouble(8, valorcontado.doubleValue());
					insert.setDouble(9, boleto.doubleValue());
					insert.setDouble(10, cuotasx36.doubleValue());
					insert.setBigDecimal(11, idempresa);
					insert.setString(12, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String crmProductosCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String crmProductosCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String crmProductosUpdate(BigDecimal nrolote,
			BigDecimal idfamiliacotizacion, BigDecimal idgrupoproducto,
			BigDecimal idproductostatus, String calificacion,
			Double superficie, Double precioxmts, Double precio,
			Double valorcontado, Double boleto, Double cuotasx36,
			BigDecimal idempresa, String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (nrolote == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nrolote ";
		if (idfamiliacotizacion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idfamiliacotizacion ";
		if (idgrupoproducto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idgrupoproducto ";
		if (calificacion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: calificacion ";
		if (superficie == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: superficie ";
		if (precioxmts == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: precioxmts ";
		if (precio == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: precio ";
		if (valorcontado == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: valorcontado ";
		if (boleto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: boleto ";
		if (cuotasx36 == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cuotasx36 ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (calificacion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: calificacion ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM crmProductos WHERE nrolote = "
					+ nrolote.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE CRMPRODUCTOS SET idfamiliacotizacion=?, idgrupoproducto=?, idproductostatus=?, calificacion=?, superficie=?, precioxmts=?, precio=?, valorcontado=?, boleto=?, cuotasx36=?, idempresa=?, usuarioact=?, fechaact=? WHERE nrolote=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, idfamiliacotizacion);
					insert.setBigDecimal(2, idgrupoproducto);
					insert.setBigDecimal(3, idproductostatus);
					insert.setString(4, calificacion);
					insert.setDouble(5, superficie.doubleValue());
					insert.setDouble(6, precioxmts.doubleValue());
					insert.setDouble(7, precio.doubleValue());
					insert.setDouble(8, valorcontado.doubleValue());
					insert.setDouble(9, boleto.doubleValue());
					insert.setDouble(10, cuotasx36.doubleValue());
					insert.setBigDecimal(11, idempresa);
					insert.setString(12, usuarioact);
					insert.setTimestamp(13, fechaact);
					insert.setBigDecimal(14, nrolote);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String crmProductosUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log.error("Error excepcion public String crmProductosUpdate(.....)"
					+ ex);
		}
		return salida;
	}

	/**
	 * Metodos para la entidad: crmFamiliaCotizacion Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Fri Aug 03 14:04:30 ART 2007
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getCrmFamiliaCotizacionAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idfamiliacotizacion,familiacotizacion,idempresa,usuarioalt,usuarioact,fechaalt,fechaact"
				+ "  FROM crmFamiliaCotizacion WHERE idempresa = "
				+ idempresa.toString() + "  ORDER BY 2  LIMIT " + limit
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
			log.error("Error SQL en el metodo : getCrmFamiliaCotizacionAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmFamiliaCotizacionAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getCrmFamiliaCotizacionOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idfamiliacotizacion,familiacotizacion,idempresa,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM CRMFAMILIACOTIZACION "
				+ " WHERE (UPPER(idfamiliacotizacion::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(familiacotizacion) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%')  AND idempresa = "
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
					.error("Error SQL en el metodo : getCrmFamiliaCotizacionOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmFamiliaCotizacionOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getCrmFamiliaCotizacionPK(BigDecimal idfamiliacotizacion,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT  idfamiliacotizacion,familiacotizacion,idempresa,usuarioalt,usuarioact,fechaalt,fechaact"
				+ "  FROM CRMFAMILIACOTIZACION WHERE idfamiliacotizacion="
				+ idfamiliacotizacion.toString() + " AND idempresa = "
				+ idempresa.toString() + ";";
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
					.error("Error SQL en el metodo : getCrmFamiliaCotizacionPK( BigDecimal idfamiliacotizacion ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCrmFamiliaCotizacionPK( BigDecimal idfamiliacotizacion )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String crmFamiliaCotizacionDelete(BigDecimal idfamiliacotizacion)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM CRMFAMILIACOTIZACION WHERE idfamiliacotizacion="
				+ idfamiliacotizacion.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM CRMFAMILIACOTIZACION WHERE idfamiliacotizacion="
						+ idfamiliacotizacion.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : crmFamiliaCotizacionDelete( BigDecimal idfamiliacotizacion ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: crmFamiliaCotizacionDelete( BigDecimal idfamiliacotizacion )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String crmFamiliaCotizacionCreate(String familiacotizacion,
			BigDecimal idempresa, String usuarioalt) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (familiacotizacion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: familiacotizacion ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (familiacotizacion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: familiacotizacion ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO CRMFAMILIACOTIZACION(familiacotizacion, idempresa, usuarioalt ) VALUES (?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, familiacotizacion);
				insert.setBigDecimal(2, idempresa);
				insert.setString(3, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String crmFamiliaCotizacionCreate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String crmFamiliaCotizacionCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String crmFamiliaCotizacionCreateOrUpdate(
			BigDecimal idfamiliacotizacion, String familiacotizacion,
			BigDecimal idempresa, String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idfamiliacotizacion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idfamiliacotizacion ";
		if (familiacotizacion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: familiacotizacion ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (familiacotizacion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: familiacotizacion ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM crmFamiliaCotizacion WHERE idfamiliacotizacion = "
					+ idfamiliacotizacion.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE CRMFAMILIACOTIZACION SET familiacotizacion=?, idempresa=?, usuarioact=?, fechaact=? WHERE idfamiliacotizacion=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, familiacotizacion);
					insert.setBigDecimal(2, idempresa);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, idfamiliacotizacion);
				} else {
					String ins = "INSERT INTO CRMFAMILIACOTIZACION(familiacotizacion, idempresa, usuarioalt ) VALUES (?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, familiacotizacion);
					insert.setBigDecimal(2, idempresa);
					insert.setString(3, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String crmFamiliaCotizacionCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String crmFamiliaCotizacionCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String crmFamiliaCotizacionUpdate(BigDecimal idfamiliacotizacion,
			String familiacotizacion, BigDecimal idempresa, String usuarioact)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idfamiliacotizacion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idfamiliacotizacion ";
		if (familiacotizacion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: familiacotizacion ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (familiacotizacion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: familiacotizacion ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM crmFamiliaCotizacion WHERE idfamiliacotizacion = "
					+ idfamiliacotizacion.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE CRMFAMILIACOTIZACION SET familiacotizacion=?, idempresa=?, usuarioact=?, fechaact=? WHERE idfamiliacotizacion=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, familiacotizacion);
					insert.setBigDecimal(2, idempresa);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, idfamiliacotizacion);
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
					.error("Error SQL public String crmFamiliaCotizacionUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String crmFamiliaCotizacionUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// -- resultados de las llamadas

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String crmresultadosllamadosDelete(BigDecimal idresultadollamada,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM crmresultadosllamadas "
				+ " WHERE idresultadollamada = "
				+ idresultadollamada.toString() + " and idempresa = "
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM crmresultadosllamadas "
						+ " WHERE idresultadollamada = "
						+ idresultadollamada.toString() + " and idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : crmresultadosllamadosDelete( BigDecimal idresultadollamada ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: crmresultadosllamadosDelete( BigDecimal idresultadollamada )  "
							+ ex);
		}
		return salida;
	}

	public List getcrmresultadosllamadosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idresultadollamada,resultadollamada,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM crmresultadosllamadas "
				+ " where idempresa= "
				+ idempresa.toString()
				+ " and (idresultadollamada::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " resultadollamada LIKE '%"
				+ ocurrencia
				+ "%') "
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
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
					.error("Error SQL en el metodo : getcrmresultadosllamadosOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getcrmresultadosllamadosOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	public List getcrmresultadosllamadosAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idresultadollamada,resultadollamada,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM crmresultadosllamadas  where idempresa = "
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
			log.error("Error SQL en el metodo : getcrmresultadosllamadosAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getcrmresultadosllamadosAll()  "
							+ ex);
		}
		return vecSalida;
	}

	public String crmresultadosllamadosCreate(String resultadollamada,
			BigDecimal idempresa, String usuarioalt) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (resultadollamada == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: resultadollamada ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (resultadollamada.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: fuente ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO crmresultadosllamadas(resultadollamada , idempresa, usuarioalt ) VALUES (?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, resultadollamada);
				insert.setBigDecimal(2, idempresa);
				insert.setString(3, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String crmresultadosllamadosCreate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String crmresultadosllamadosCreate(.....)"
							+ ex);
		}
		return salida;
	}

	public String crmresultadosllamadosUpdate(BigDecimal idresultadollamada,
			String resultadollamada, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idresultadollamada == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idresultadollamada ";
		if (resultadollamada == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: resultadollamada ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (resultadollamada.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: resultadollamada ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM crmresultadosllamadas "
					+ " WHERE idresultadollamada = "
					+ idresultadollamada.toString() + " and idempresa = "
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
					sql = "UPDATE crmresultadosllamadas SET resultadollamada=?, usuarioact=?, fechaact=? WHERE idresultadollamada=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, resultadollamada);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idresultadollamada);
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
					.error("Error SQL public String crmresultadosllamadosUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String crmresultadosllamadosUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public List getcrmresultadosllamadosPK(BigDecimal idresultadollamada,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idresultadollamada,resultadollamada,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM crmresultadosllamadas "
				+ " WHERE idresultadollamada = "
				+ idresultadollamada.toString()
				+ " and idempresa = "
				+ idempresa.toString();
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
					.error("Error SQL en el metodo : getcrmresultadosllamadosPK( BigDecimal idresultadollamada,BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getcrmresultadosllamadosPK(  BigDecimal idresultadollamada,BigDecimal idempresa  )  "
							+ ex);
		}
		return vecSalida;
	}

	// resultado de cotizaciones

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String crmresultadoscotizacionesDelete(
			BigDecimal idresultadocotizacion, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM crmresultadoscotizaciones "
				+ " WHERE idresultadocotizacion = "
				+ idresultadocotizacion.toString() + " and idempresa = "
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM crmresultadoscotizaciones "
						+ " WHERE idresultadocotizacion = "
						+ idresultadocotizacion.toString()
						+ " and idempresa = " + idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : crmresultadosllamadosDelete( BigDecimal idresultadocotizacion ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: crmresultadosllamadosDelete( BigDecimal idresultadocotizacion )  "
							+ ex);
		}
		return salida;
	}

	public List getcrmresultadoscotizacionesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idresultadocotizacion,resultadocotizacion,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM crmresultadoscotizaciones "
				+ " where idempresa= "
				+ idempresa.toString()
				+ " and (idresultadocotizacion::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " resultadocotizacion LIKE '%"
				+ ocurrencia
				+ "%') "
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
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
					.error("Error SQL en el metodo : getcrmresultadosllamadosOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getcrmresultadosllamadosOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	public List getcrmresultadoscotizacionesAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idresultadocotizacion,resultadocotizacion,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM crmresultadoscotizaciones  where idempresa = "
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
			log.error("Error SQL en el metodo : getcrmresultadosllamadosAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getcrmresultadosllamadosAll()  "
							+ ex);
		}
		return vecSalida;
	}

	public String crmresultadoscotizacionesCreate(String resultadocotizacion,
			BigDecimal idempresa, String usuarioalt) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (resultadocotizacion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: resultadocotizacion ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (resultadocotizacion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: fuente ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO crmresultadoscotizaciones(resultadocotizacion , idempresa, usuarioalt ) VALUES (?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, resultadocotizacion);
				insert.setBigDecimal(2, idempresa);
				insert.setString(3, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String crmresultadosllamadosCreate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String crmresultadosllamadosCreate(.....)"
							+ ex);
		}
		return salida;
	}

	public String crmresultadoscotizacionesUpdate(
			BigDecimal idresultadocotizacion, String resultadocotizacion,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idresultadocotizacion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idresultadocotizacion ";
		if (resultadocotizacion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: resultadocotizacion ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (resultadocotizacion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: resultadocotizacion ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM crmresultadoscotizaciones "
					+ " WHERE idresultadocotizacion = "
					+ idresultadocotizacion.toString() + " and idempresa = "
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
					sql = "UPDATE crmresultadoscotizaciones SET resultadocotizacion=?, usuarioact=?, fechaact=? WHERE idresultadocotizacion=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, resultadocotizacion);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idresultadocotizacion);
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
					.error("Error SQL public String crmresultadosllamadosUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String crmresultadosllamadosUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public List getcrmresultadoscotizacionesPK(
			BigDecimal idresultadocotizacion, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idresultadocotizacion,resultadocotizacion,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM crmresultadoscotizaciones "
				+ " WHERE idresultadocotizacion = "
				+ idresultadocotizacion.toString()
				+ " and idempresa = "
				+ idempresa.toString();
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
					.error("Error SQL en el metodo : getcrmresultadosllamadosPK( BigDecimal idresultadocotizacion,BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getcrmresultadosllamadosPK(  BigDecimal idresultadocotizacion,BigDecimal idempresa  )  "
							+ ex);
		}
		return vecSalida;
	}

}
