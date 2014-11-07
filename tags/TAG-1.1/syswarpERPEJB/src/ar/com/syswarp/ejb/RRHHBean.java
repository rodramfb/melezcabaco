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
import java.text.SimpleDateFormat;

import org.omg.CORBA.Current;
import org.postgresql.*; //import javax.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.*;
import org.postgresql.jdbc2.TimestampUtils;

import com.sun.org.apache.bcel.internal.generic.NEW;

import bsh.EvalError;
import bsh.Interpreter;

import sun.security.action.GetBooleanAction;
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
 * 
 * Below are the xdoclet-related tags needed for this EJB.
 * 
 * @ejb.bean name="Clientes" display-name="Name for Clientes"
 *           description="Description for Clientes" jndi-name="ejb/Clientes"
 *           type="Stateless" view-type="remote"
 */
@Stateless
public class RRHHBean implements RRHH {

	/** The session context */
	private SessionContext context;

	GeneralBean gb = new GeneralBean();

	/* conexion a la base de datos */

	private static Connection dbconn;;

	static Logger log = Logger.getLogger(RRHHBean.class);

	private Connection conexion;

	private Properties props;

	private String url;

	private String clase;

	private String usuario;

	private String clave;
	
	private static Interpreter i;

	public RRHHBean() {
		super();
		try {
			props = new Properties();
			props.load(RRHHBean.class.getResourceAsStream("system.properties"));

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

	public RRHHBean(Connection dbconn) {
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

	public static boolean esNumerico(String num) throws EJBException {
		try {

			Pattern patron = Pattern.compile("[^0-9.]");
			Matcher compara;

			compara = patron.matcher(num);
			boolean invalido = compara.find();

			if (invalido) {
				// Asegurar que solo ingresen valores numericos sin casteo
				// ALFABETICO
				log
						.info("Verificacion para casteo con valores alfabeticos Float - Double - Long (F-D-L)");
				return false;
			} else {
				Double.parseDouble(num);
				return true;
			}

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
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
				+ "       WHERE idempresa = " + idempresa.toString();
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
		 *             
		 *             
		 */
		long total = 0l;
		ResultSet rsSalida = null;
		String cQuery = "SELECT count(1)AS total FROM " + entidad + " WHERE ";
		String like = "";
		int len = campos.length;

		try {
			for (int i = 0; i < len; i++) {
				like += " UPPER(" + campos[i] + "::VARCHAR) LIKE '%"
						+ ocurrencia.toUpperCase() + "%' ";
				if (i + 1 < len)
					like += " OR ";
			}
			cQuery += "(" + like + " ) AND idempresa = " + idempresa.toString();
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
		String cQuery = "SELECT count(1)AS total FROM " + entidad
				+ " WHERE idempresa = " + idempresa + " ";
		String filtro = "";
		int len = campos.length;

		try {
			for (int i = 0; i < len; i++) {
				filtro += campos[i] + " = " + ocurrencia[i] + " ";
				if (i + 1 < len)
					filtro += " AND ";
			}
			cQuery += filtro;
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

	// RRHH
	// ACTIVIDADES
	// para todo (ordena por el segundo campo por defecto)
	public List getRrhhactividadAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idactividad,actividad,usuarioalt,usuarioact,fechaalt,fechaact FROM RRHHACTIVIDAD where idempresa = "
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
			log.error("Error SQL en el metodo : getRrhhactividad() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhactividad()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getRrhhactividadOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idactividad,actividad,usuarioalt,usuarioact,fechaalt,fechaact FROM RRHHACTIVIDAD "
				+ " where idempresa= "
				+ idempresa.toString()
				+ " and (idactividad::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " UPPER(actividad) LIKE '%"
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
					.error("Error SQL en el metodo : getRrhhactividadOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhactividadOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	
	public static String evaluacionLogica(String formula) throws EvalError {
		String salida = "";
		i.eval(formula);
		// salida =
		salida = (String) i.get(formula);
		return salida;

	}
	
	// por primary key (primer campo por defecto)

	public List getRrhhactividadPK(BigDecimal idactividad, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idactividad,actividad,usuarioalt,usuarioact,fechaalt,fechaact FROM RRHHACTIVIDAD WHERE idactividad="
				+ idactividad.toString()
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
					.error("Error SQL en el metodo : getRrhhactividadPK( BigDecimal idactividad ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhactividadPK( BigDecimal idactividad )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String rrhhactividadDelete(BigDecimal idactividad,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM RRHHACTIVIDAD WHERE idactividad = "
				+ idactividad.toString() + " and idempresa = "
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM RRHHACTIVIDAD WHERE idactividad = "
						+ idactividad.toString() + " and idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : rrhhactividadDelete( BigDecimal idactividad ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: rrhhactividadDelete( BigDecimal idactividad )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String rrhhactividadCreate(String actividad, String usuarioalt,
			BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (actividad == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: actividad ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (actividad.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Actividad ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO RRHHACTIVIDAD(actividad, usuarioalt,idempresa ) VALUES (?,?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, actividad);
				insert.setString(2, usuarioalt);
				insert.setBigDecimal(3, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String rrhhactividadCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhactividadCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String rrhhactividadCreateOrUpdate(BigDecimal idactividad,
			String actividad, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idactividad == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idactividad ";
		if (actividad == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: Actividad ";

		// 2. sin nada desde la pagina
		if (actividad.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Actividad ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhactividad WHERE idactividad = "
					+ idactividad.toString()
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
					sql = "UPDATE RRHHACTIVIDAD SET actividad=?, usuarioact=?, fechaact=? WHERE idactividad=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, actividad);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idactividad);
					insert.setBigDecimal(5, idempresa);
				} else {
					String ins = "INSERT INTO RRHHACTIVIDAD(actividad, usuarioalt,idempresa ) VALUES (?,?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, actividad);
					insert.setString(2, usuarioalt);
					insert.setBigDecimal(3, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String rrhhactividadCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhactividadCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String rrhhactividadUpdate(BigDecimal idactividad, String actividad,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idactividad == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idactividad ";
		if (actividad == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: Actividad ";

		// 2. sin nada desde la pagina
		if (actividad.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Actividad ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhactividad WHERE idactividad = "
					+ idactividad.toString()
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
					sql = "UPDATE RRHHACTIVIDAD SET actividad=?, usuarioact=?, fechaact=? WHERE idactividad=? and idempresa =?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, actividad);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idactividad);
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
			log.error("Error SQL public String rrhhactividadUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String rrhhactividadUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// AFJP
	// para todo (ordena por el segundo campo por defecto)
	public List getRrhhafjpAll(long limit, long offset, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idafjp,afjp,expediente,usuarioalt,usuarioact,fechaalt,fechaact FROM RRHHAFJP  where idempresa = "
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
			log.error("Error SQL en el metodo : getRrhhafjpAll() "
					+ sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception: en el metodo: getRrhhafjpAll()  "
					+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getRrhhafjpOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idafjp,afjp,expediente,usuarioalt,usuarioact,fechaalt,fechaact FROM RRHHAFJP "
				+ " where idempresa = "
				+ idempresa.toString()
				+ " and (idafjp::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " UPPER(afjp) LIKE '%"
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
					.error("Error SQL en el metodo : getRrhhafjpOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhafjpOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getRrhhafjpPK(BigDecimal idafjp, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idafjp,afjp,expediente,usuarioalt,usuarioact,fechaalt,fechaact FROM RRHHAFJP WHERE idafjp = "
				+ idafjp.toString()
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
					.error("Error SQL en el metodo : getRrhhafjpPK( BigDecimal idafjp ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhafjpPK( BigDecimal idafjp )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String rrhhafjpDelete(BigDecimal idafjp, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM RRHHAFJP WHERE idafjp = "
				+ idafjp.toString() + " and idempresa = "
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM RRHHAFJP WHERE idafjp = "
						+ idafjp.toString() + " and idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : rrhhafjpDelete( BigDecimal idafjp ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: rrhhafjpDelete( BigDecimal idafjp )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String rrhhafjpCreate(String afjp, String expediente,
			String usuarioalt, BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (afjp == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: Afjp ";
		if (expediente == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: Expediente ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (afjp.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Afjp ";
		if (expediente.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Expediente ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO RRHHAFJP(afjp, expediente, usuarioalt,idempresa ) VALUES (?,?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, afjp);
				insert.setString(2, expediente);
				insert.setString(3, usuarioalt);
				insert.setBigDecimal(4, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String rrhhafjpCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error excepcion public String rrhhafjpCreate(.....)"
					+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String rrhhafjpCreateOrUpdate(BigDecimal idafjp, String afjp,
			String expediente, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idafjp == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idafjp ";
		if (afjp == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: Afjp ";
		if (expediente == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: Expediente ";

		// 2. sin nada desde la pagina
		if (afjp.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Afjp ";
		if (expediente.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Expediente ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhafjp WHERE idafjp = "
					+ idafjp.toString() + " and idempresa = "
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
					sql = "UPDATE RRHHAFJP SET afjp=?, expediente=?, usuarioact=?, fechaact=? WHERE idafjp=? and idempresa =?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, afjp);
					insert.setString(2, expediente);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, idafjp);
					insert.setBigDecimal(6, idempresa);
				} else {
					String ins = "INSERT INTO RRHHAFJP(afjp, expediente, usuarioalt,idempresa ) VALUES (?,?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, afjp);
					insert.setString(2, expediente);
					insert.setString(3, usuarioalt);
					insert.setBigDecimal(4, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String rrhhafjpCreateOrUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhafjpCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String rrhhafjpUpdate(BigDecimal idafjp, String afjp,
			String expediente, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idafjp == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idafjp ";
		if (afjp == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: Afjp ";
		if (expediente == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: Expediente ";

		// 2. sin nada desde la pagina
		if (afjp.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Afjp ";
		if (expediente.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Expediente ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhafjp WHERE idafjp = "
					+ idafjp.toString() + " and idempresa = "
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
					sql = "UPDATE RRHHAFJP SET afjp=?, expediente=?, usuarioact=?, fechaact=? WHERE idafjp=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, afjp);
					insert.setString(2, expediente);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, idafjp);
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
			log.error("Error SQL public String rrhhafjpUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log.error("Error excepcion public String rrhhafjpUpdate(.....)"
					+ ex);
		}
		return salida;
	}

	// CATEGORIAS
	// para todo (ordena por el segundo campo por defecto)
	public List getRrhhcategoriasAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idcategoria,categoria,hs,hs1,hs2,hs3,hs4,quinmens,basico,usuarioalt,usuarioact,fechaalt,fechaact FROM RRHHCATEGORIAS where idempresa = "
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
			log.error("Error SQL en el metodo : getRrhhcategoriasAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhcategoriasAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getRrhhcategoriasOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idcategoria,categoria,hs,hs1,hs2,hs3,hs4,quinmens,basico,usuarioalt,usuarioact,fechaalt,fechaact FROM RRHHCATEGORIAS "
				+ " where idempresa= "
				+ idempresa.toString()
				+ " and (idcategoria::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " UPPER(categoria) LIKE '%"
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
					.error("Error SQL en el metodo : getRrhhcategoriasOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhcategoriasOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getRrhhcategoriasPK(BigDecimal idcategoria, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idcategoria,categoria,hs,hs1,hs2,hs3,hs4,quinmens,basico,usuarioalt,usuarioact,fechaalt,fechaact FROM RRHHCATEGORIAS WHERE idcategoria="
				+ idcategoria.toString()
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
					.error("Error SQL en el metodo : getRrhhcategoriasPK( BigDecimal idcategoria ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhcategoriasPK( BigDecimal idcategoria )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String rrhhcategoriasDelete(BigDecimal idcategoria,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM RRHHCATEGORIAS WHERE idcategoria="
				+ idcategoria.toString() + " and idempresa = "
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM RRHHCATEGORIAS WHERE idcategoria="
						+ idcategoria.toString() + " and idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : rrhhcategoriasDelete( BigDecimal idcategoria ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: rrhhcategoriasDelete( BigDecimal idcategoria )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String rrhhcategoriasCreate(String categoria, Double hs, Double hs1,
			Double hs2, Double hs3, Double hs4, String quinmens,
			BigDecimal basico, String usuarioalt, BigDecimal idempresa)
			throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (categoria == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: categoria ";
		if (hs == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: hs ";
		if (hs1 == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: hs1 ";
		if (hs2 == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: hs2 ";
		if (hs3 == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: hs3 ";
		if (hs4 == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: hs4 ";
		if (quinmens == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: quinmens ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (categoria.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Categoria ";

		if (quinmens.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Quinmens ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO RRHHCATEGORIAS(categoria, hs, hs1, hs2, hs3, hs4, quinmens,basico,  usuarioalt,idempresa ) VALUES (?,?,?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, categoria);
				insert.setDouble(2, hs.doubleValue());
				insert.setDouble(3, hs1.doubleValue());
				insert.setDouble(4, hs2.doubleValue());
				insert.setDouble(5, hs3.doubleValue());
				insert.setDouble(6, hs4.doubleValue());
				insert.setString(7, quinmens);
				insert.setBigDecimal(8, basico);
				insert.setString(9, usuarioalt);
				insert.setBigDecimal(10, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String rrhhcategoriasCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhcategoriasCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String rrhhcategoriasCreateOrUpdate(BigDecimal idcategoria,
			String categoria, Double hs, Double hs1, Double hs2, Double hs3,
			Double hs4, String quinmens, BigDecimal basico, String usuarioact,
			BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idcategoria == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idcategoria ";
		if (categoria == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: categoria ";
		if (hs == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: hs ";
		if (hs1 == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: hs1 ";
		if (hs2 == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: hs2 ";
		if (hs3 == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: hs3 ";
		if (hs4 == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: hs4 ";
		if (quinmens == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: quinmens ";

		// 2. sin nada desde la pagina
		if (categoria.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: categoria ";
		if (quinmens.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: quinmens ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhcategorias WHERE idcategoria = "
					+ idcategoria.toString()
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
					sql = "UPDATE RRHHCATEGORIAS SET categoria=?, hs=?, hs1=?, hs2=?, hs3=?, hs4=?, quinmens=?,basico = ?, usuarioact=?, fechaact=? WHERE idcategoria=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, categoria);
					insert.setDouble(2, hs.doubleValue());
					insert.setDouble(3, hs1.doubleValue());
					insert.setDouble(4, hs2.doubleValue());
					insert.setDouble(5, hs3.doubleValue());
					insert.setDouble(6, hs4.doubleValue());
					insert.setString(7, quinmens);
					insert.setBigDecimal(8, basico);
					insert.setString(9, usuarioact);
					insert.setTimestamp(10, fechaact);
					insert.setBigDecimal(11, idcategoria);
					insert.setBigDecimal(12, idempresa);
				} else {
					String ins = "INSERT INTO RRHHCATEGORIAS(categoria, hs, hs1, hs2, hs3, hs4, quinmens, basico, usuarioalt,idempresa ) VALUES (?,?,?, ?, ?, ?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, categoria);
					insert.setDouble(2, hs.doubleValue());
					insert.setDouble(3, hs1.doubleValue());
					insert.setDouble(4, hs2.doubleValue());
					insert.setDouble(5, hs3.doubleValue());
					insert.setDouble(6, hs4.doubleValue());
					insert.setString(7, quinmens);
					insert.setBigDecimal(8, basico);
					insert.setString(9, usuarioalt);
					insert.setBigDecimal(10, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String rrhhcategoriasCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhcategoriasCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String rrhhcategoriasUpdate(BigDecimal idcategoria,
			String categoria, Double hs, Double hs1, Double hs2, Double hs3,
			Double hs4, String quinmens, BigDecimal basico, String usuarioact,
			BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idcategoria == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idcategoria ";
		if (categoria == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: categoria ";
		if (hs == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: hs ";
		if (hs1 == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: hs1 ";
		if (hs2 == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: hs2 ";
		if (hs3 == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: hs3 ";
		if (hs4 == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: hs4 ";
		if (quinmens == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: quinmens ";

		// 2. sin nada desde la pagina
		if (categoria.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: categoria ";
		if (quinmens.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: quinmens ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhcategorias WHERE idcategoria = "
					+ idcategoria.toString()
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
					sql = "UPDATE RRHHCATEGORIAS SET categoria=?, hs=?, hs1=?, hs2=?, hs3=?, hs4=?, quinmens=?, basico =?, usuarioact=?, fechaact=? WHERE idcategoria=? and idempresa =?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, categoria);
					insert.setDouble(2, hs.doubleValue());
					insert.setDouble(3, hs1.doubleValue());
					insert.setDouble(4, hs2.doubleValue());
					insert.setDouble(5, hs3.doubleValue());
					insert.setDouble(6, hs4.doubleValue());
					insert.setString(7, quinmens);
					insert.setBigDecimal(8, basico);
					insert.setString(9, usuarioact);
					insert.setTimestamp(10, fechaact);
					insert.setBigDecimal(11, idcategoria);
					insert.setBigDecimal(12, idempresa);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String rrhhcategoriasUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String rrhhcategoriasUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// CONCEPTOS
	// para todo (ordena por el segundo campo por defecto)
	public List getRrhhconceptosAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " select "
				+ " rrhhconceptos.idconcepto, "
				+ " rrhhconceptos.concepto,"
				+ " rrhhconceptos.imprime,"
				+ " rrhhconceptos.formula,"
				+ " rrhhconceptos.idctacont,"
				+ " rrhhtipoconcepto.tipoconcepto,"
				+ " rrhhtipocantidadconcepto.tipocantidadconcepto,"
				+ " rrhhconceptos.valor,"
				+ " rrhhconceptos.usuarioalt,"
				+ " rrhhconceptos.usuarioact,"
				+ " rrhhconceptos.fechaalt,"
				+ " rrhhconceptos.fechaact"
				+ " from "
				+ " rrhhconceptos"
				+ " left join rrhhtipoconcepto on rrhhtipoconcepto.idtipoconcepto = rrhhconceptos.idtipoconcepto "
				+ " left join rrhhtipocantidadconcepto on rrhhtipocantidadconcepto.idtipocantidadconcepto = rrhhconceptos.idtipoconcepto "
				+ " where rrhhconceptos.idempresa = " + idempresa.toString()
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
			log.error("Error SQL en el metodo : getRrhhconceptosAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhconceptosAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getRrhhconceptosOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " select "
				+ " rrhhconceptos.idconcepto, "
				+ " rrhhconceptos.concepto,"
				+ " rrhhconceptos.imprime,"
				+ " rrhhconceptos.formula,"
				+ " rrhhconceptos.idctacont,"
				+ " rrhhtipoconcepto.tipoconcepto,"
				+ " rrhhtipocantidadconcepto.tipocantidadconcepto,"
				+ " rrhhconceptos.valor,"
				+ " rrhhconceptos.usuarioalt,"
				+ " rrhhconceptos.usuarioact,"
				+ " rrhhconceptos.fechaalt,"
				+ " rrhhconceptos.fechaact"
				+ " from "
				+ " rrhhconceptos"
				+ " left join rrhhtipoconcepto on rrhhtipoconcepto.idtipoconcepto = rrhhconceptos.idtipoconcepto "
				+ " left join rrhhtipocantidadconcepto on rrhhtipocantidadconcepto.idtipocantidadconcepto = rrhhconceptos.idtipoconcepto "
				+ " where rrhhconceptos.idempresa = " + idempresa.toString()
				+ " and (rrhhconceptos.idconcepto::VARCHAR LIKE '%"
				+ ocurrencia + "%' OR "
				+ " UPPER(rrhhconceptos.concepto) LIKE '%"
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
					.error("Error SQL en el metodo : getRrhhconceptosOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhconceptosOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getRrhhconceptosPK(BigDecimal idconcepto, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " select "
				+ " rrhhconceptos.idconcepto, "
				+ " rrhhconceptos.concepto,"
				+ " rrhhconceptos.imprime,"
				+ " rrhhconceptos.formula,"
				+ " rrhhconceptos.idctacont,"
				+ " rrhhtipoconcepto.idtipoconcepto,"
				+ " rrhhtipoconcepto.tipoconcepto,"
				+ " rrhhtipocantidadconcepto.idtipocantidadconcepto,"
				+ " rrhhtipocantidadconcepto.tipocantidadconcepto,"
				+ " rrhhconceptos.valor,"
				+ " rrhhconceptos.usuarioalt,"
				+ " rrhhconceptos.usuarioact,"
				+ " rrhhconceptos.fechaalt,"
				+ " rrhhconceptos.fechaact"
				+ " from "
				+ " rrhhconceptos"
				+ " left join rrhhtipoconcepto on rrhhtipoconcepto.idtipoconcepto = rrhhconceptos.idtipoconcepto "
				+ " left join rrhhtipocantidadconcepto on rrhhtipocantidadconcepto.idtipocantidadconcepto = rrhhconceptos.idtipoconcepto "
				+ " where rrhhconceptos.idconcepto = " + idconcepto.toString()
				+ " and rrhhconceptos.idempresa = " + idempresa.toString();
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
					.error("Error SQL en el metodo : getRrhhconceptosPK( BigDecimal idconcepto ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhconceptosPK( BigDecimal idconcepto )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String rrhhconceptosDelete(BigDecimal idconcepto,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM RRHHCONCEPTOS WHERE idconcepto="
				+ idconcepto.toString() + " and idempresa = "
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM RRHHCONCEPTOS WHERE idconcepto="
						+ idconcepto.toString() + " and idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : rrhhconceptosDelete( BigDecimal idconcepto ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: rrhhconceptosDelete( BigDecimal idconcepto )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String rrhhconceptosCreate(String concepto, String imprime,
			String formula, BigDecimal idctacont, BigDecimal idtipoconcepto,
			BigDecimal idtipocantidadconcepto, Double valor, String usuarioalt,
			BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (concepto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: concepto ";
		if (imprime == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: imprime ";
		if (formula == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: formula ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (concepto.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: concepto ";
		if (imprime.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: imprime ";
		if (formula.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: formula ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO RRHHCONCEPTOS(concepto, imprime, formula, idctacont,idtipoconcepto,idtipocantidadconcepto,valor, usuarioalt,idempresa ) VALUES (?,?, ?, ?, ?, ?,?,?,?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, concepto);
				insert.setString(2, imprime);
				insert.setString(3, formula);
				insert.setBigDecimal(4, idctacont);
				insert.setBigDecimal(5, idtipoconcepto);
				insert.setBigDecimal(6, idtipocantidadconcepto);
				insert.setDouble(7, valor.doubleValue());
				insert.setString(8, usuarioalt);
				insert.setBigDecimal(9, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}

		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String rrhhconceptosCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhconceptosCreate(.....)"
							+ ex);
		}
		return salida;
	}

	public String rrhhconceptosUpdate(BigDecimal idconcepto, String concepto,
			String imprime, String formula, BigDecimal idctacont,
			BigDecimal idtipoconcepto, BigDecimal idtipocantidadconcepto,
			Double valor, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idconcepto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idconcepto ";
		if (concepto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: concepto ";
		if (imprime == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: imprime ";
		if (formula == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: formula ";

		// 2. sin nada desde la pagina
		if (concepto.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: concepto ";
		if (imprime.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: imprime ";
		if (formula.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: formula ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhconceptos WHERE idconcepto = "
					+ idconcepto.toString()
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
					sql = "UPDATE RRHHCONCEPTOS SET concepto=?, imprime=?, formula=?, idctacont=?,idtipoconcepto=?,idtipocantidadconcepto=?,valor=?, usuarioact=?, fechaact=? WHERE idconcepto=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, concepto);
					insert.setString(2, imprime);
					insert.setString(3, formula);
					insert.setBigDecimal(4, idctacont);
					insert.setBigDecimal(5, idtipoconcepto);
					insert.setBigDecimal(6, idtipocantidadconcepto);
					insert.setDouble(7, valor.doubleValue());
					insert.setString(8, usuarioact);
					insert.setTimestamp(9, fechaact);
					insert.setBigDecimal(10, idconcepto);
					insert.setBigDecimal(11, idempresa);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String rrhhconceptosUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String rrhhconceptosUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// SALARIOS FAMILIARES
	// para todo (ordena por el segundo campo por defecto)
	public List getRrhhcodsfAll(long limit, long offset, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idcodsf,codsf,variable,usuarioalt,usuarioact,fechaalt,fechaact FROM RRHHCODSF where idempresa = "
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
			log.error("Error SQL en el metodo : getRrhhcodsfAll() "
					+ sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception: en el metodo: getRrhhcodsfAll()  "
					+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getRrhhcodsfOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idcodsf,codsf,variable,usuarioalt,usuarioact,fechaalt,fechaact FROM RRHHCODSF "
				+ " where idempresa= "
				+ idempresa.toString()
				+ " and (idcodsf::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " UPPER(codsf) LIKE '%"
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
					.error("Error SQL en el metodo : getRrhhcodsfOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhcodsfOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getRrhhcodsfPK(BigDecimal idcodsf, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idcodsf,codsf,variable,usuarioalt,usuarioact,fechaalt,fechaact FROM RRHHCODSF WHERE idcodsf="
				+ idcodsf.toString()
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
					.error("Error SQL en el metodo : getRrhhcodsfPK( BigDecimal idcodsf ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhcodsfPK( BigDecimal idcodsf )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String rrhhcodsfDelete(BigDecimal idcodsf, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM RRHHCODSF WHERE idcodsf="
				+ idcodsf.toString() + " and idempresa = "
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM RRHHCODSF WHERE idcodsf="
						+ idcodsf.toString() + " and idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : rrhhcodsfDelete( BigDecimal idcodsf ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: rrhhcodsfDelete( BigDecimal idcodsf )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String rrhhcodsfCreate(String codsf, String variable,
			String usuarioalt, BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (codsf == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: Cod sf ";
		if (variable == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: Variable ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (codsf.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Cod sf ";
		if (variable.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Variable ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO RRHHCODSF(codsf, variable, usuarioalt,idempresa ) VALUES (?,?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, codsf);
				insert.setString(2, variable);
				insert.setString(3, usuarioalt);
				insert.setBigDecimal(4, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String rrhhcodsfCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error excepcion public String rrhhcodsfCreate(.....)"
					+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String rrhhcodsfCreateOrUpdate(BigDecimal idcodsf, String codsf,
			String variable, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idcodsf == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idcodsf ";
		if (codsf == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: Cod sf ";
		if (variable == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: Variable ";

		// 2. sin nada desde la pagina
		if (codsf.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Cod sf ";
		if (variable.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Variable ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhcodsf WHERE idcodsf = "
					+ idcodsf.toString() + " and idempresa = "
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
					sql = "UPDATE RRHHCODSF SET codsf=?, variable=?, usuarioact=?, fechaact=? WHERE idcodsf=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, codsf);
					insert.setString(2, variable);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, idcodsf);
					insert.setBigDecimal(6, idempresa);
				} else {
					String ins = "INSERT INTO RRHHCODSF(codsf, variable, usuarioalt,idempresa ) VALUES (?,?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, codsf);
					insert.setString(2, variable);
					insert.setString(3, usuarioalt);
					insert.setBigDecimal(4, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String rrhhcodsfCreateOrUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhcodsfCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String rrhhcodsfUpdate(BigDecimal idcodsf, String codsf,
			String variable, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idcodsf == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idcodsf ";
		if (codsf == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: Cod sf ";
		if (variable == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: Variable ";

		// 2. sin nada desde la pagina
		if (codsf.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Cod sf ";
		if (variable.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Variable ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhcodsf WHERE idcodsf = "
					+ idcodsf.toString() + " and idempresa = "
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
					sql = "UPDATE RRHHCODSF SET codsf=?, variable=?, usuarioact=?, fechaact=? WHERE idcodsf=? and idempresa =?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, codsf);
					insert.setString(2, variable);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, idcodsf);
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
			log.error("Error SQL public String rrhhcodsfUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log.error("Error excepcion public String rrhhcodsfUpdate(.....)"
					+ ex);
		}
		return salida;
	}

	// tipo documento
	public List getRrhhtipodocumentoAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idtipodocumento,tipodocumento,usuarioalt,usuarioact,fechaalt,fechaact FROM RRHHTIPODOCUMENTO where idempresa = "
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
			log.error("Error SQL en el metodo : getRrhhtipodocumentoAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhtipodocumentoAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getRrhhtipodocumentoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idtipodocumento,tipodocumento,usuarioalt,usuarioact,fechaalt,fechaact FROM RRHHTIPODOCUMENTO "
				+ " where idempresa= "
				+ idempresa.toString()
				+ " and (idtipodocumento::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " UPPER(tipodocumento) LIKE '%"
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
					.error("Error SQL en el metodo : getRrhhtipodocumentoOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhtipodocumentoOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getRrhhtipodocumentoPK(BigDecimal idtipodocumento,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idtipodocumento,tipodocumento,usuarioalt,usuarioact,fechaalt,fechaact FROM RRHHTIPODOCUMENTO WHERE idtipodocumento="
				+ idtipodocumento.toString()
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
					.error("Error SQL en el metodo : getRrhhtipodocumentoPK( BigDecimal idtipodocumento ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhtipodocumentoPK( BigDecimal idtipodocumento )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String rrhhtipodocumentoDelete(BigDecimal idtipodocumento,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM RRHHTIPODOCUMENTO WHERE idtipodocumento="
				+ idtipodocumento.toString()
				+ " and idempresa = "
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM RRHHTIPODOCUMENTO WHERE idtipodocumento="
						+ idtipodocumento.toString() + " and idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : rrhhtipodocumentoDelete( BigDecimal idtipodocumento ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: rrhhtipodocumentoDelete( BigDecimal idtipodocumento )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String rrhhtipodocumentoCreate(String tipodocumento,
			String usuarioalt, BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (tipodocumento == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipodocumento ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (tipodocumento.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipodocumento ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO RRHHTIPODOCUMENTO(tipodocumento, usuarioalt,idempresa ) VALUES (?,?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, tipodocumento);
				insert.setString(2, usuarioalt);
				insert.setBigDecimal(3, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String rrhhtipodocumentoCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhtipodocumentoCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String rrhhtipodocumentoCreateOrUpdate(BigDecimal idtipodocumento,
			String tipodocumento, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idtipodocumento == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipodocumento ";
		if (tipodocumento == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipodocumento ";

		// 2. sin nada desde la pagina
		if (tipodocumento.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipodocumento ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhtipodocumento WHERE idtipodocumento = "
					+ idtipodocumento.toString()
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
					sql = "UPDATE RRHHTIPODOCUMENTO SET tipodocumento=?, usuarioact=?, fechaact=? WHERE idtipodocumento=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, tipodocumento);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idtipodocumento);
					insert.setBigDecimal(5, idempresa);
				} else {
					String ins = "INSERT INTO RRHHTIPODOCUMENTO(tipodocumento, usuarioalt,idempresa ) VALUES (?,?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, tipodocumento);
					insert.setString(2, usuarioalt);
					insert.setBigDecimal(3, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String rrhhtipodocumentoCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhtipodocumentoCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String rrhhtipodocumentoUpdate(BigDecimal idtipodocumento,
			String tipodocumento, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idtipodocumento == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipodocumento ";
		if (tipodocumento == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipodocumento ";

		// 2. sin nada desde la pagina
		if (tipodocumento.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipodocumento ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhtipodocumento WHERE idtipodocumento = "
					+ idtipodocumento.toString()
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
					sql = "UPDATE RRHHTIPODOCUMENTO SET tipodocumento=?, usuarioact=?, fechaact=? WHERE idtipodocumento=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, tipodocumento);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idtipodocumento);
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
			log.error("Error SQL public String rrhhtipodocumentoUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String rrhhtipodocumentoUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// estado civil
	public List getRrhhestadocivilAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idestadocivil,estadocivil,usuarioalt,usuarioact,fechaalt,fechaact FROM RRHHESTADOCIVIL where idempresa = "
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
			log.error("Error SQL en el metodo : getRrhhestadocivilAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhestadocivilAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getRrhhestadocivilOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idestadocivil,estadocivil,usuarioalt,usuarioact,fechaalt,fechaact FROM RRHHESTADOCIVIL "
				+ " where idempresa= "
				+ idempresa.toString()
				+ " and (idestadocivil::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " UPPER(estadocivil) LIKE '%"
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
					.error("Error SQL en el metodo : getRrhhestadocivilOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhestadocivilOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getRrhhestadocivilPK(BigDecimal idestadocivil,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idestadocivil,estadocivil,usuarioalt,usuarioact,fechaalt,fechaact FROM RRHHESTADOCIVIL WHERE idestadocivil="
				+ idestadocivil.toString()
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
					.error("Error SQL en el metodo : getRrhhestadocivilPK( BigDecimal idestadocivil ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhestadocivilPK( BigDecimal idestadocivil )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String rrhhestadocivilDelete(BigDecimal idestadocivil,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM RRHHESTADOCIVIL WHERE idestadocivil="
				+ idestadocivil.toString() + " and idempresa = "
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM RRHHESTADOCIVIL WHERE idestadocivil="
						+ idestadocivil.toString() + " and idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : rrhhestadocivilDelete( BigDecimal idestadocivil ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: rrhhestadocivilDelete( BigDecimal idestadocivil )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String rrhhestadocivilCreate(String estadocivil, String usuarioalt,
			BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (estadocivil == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: estadocivil ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (estadocivil.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: estadocivil ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO RRHHESTADOCIVIL(estadocivil, usuarioalt,idempresa ) VALUES (?,?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, estadocivil);
				insert.setString(2, usuarioalt);
				insert.setBigDecimal(3, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String rrhhestadocivilCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhestadocivilCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String rrhhestadocivilCreateOrUpdate(BigDecimal idestadocivil,
			String estadocivil, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idestadocivil == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idestadocivil ";
		if (estadocivil == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: estadocivil ";

		// 2. sin nada desde la pagina
		if (estadocivil.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: estadocivil ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhestadocivil WHERE idestadocivil = "
					+ idestadocivil.toString()
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
					sql = "UPDATE RRHHESTADOCIVIL SET estadocivil=?, usuarioact=?, fechaact=? WHERE idestadocivil=? and idempresa =?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, estadocivil);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idestadocivil);
					insert.setBigDecimal(5, idempresa);
				} else {
					String ins = "INSERT INTO RRHHESTADOCIVIL(estadocivil, usuarioalt,idempresa ) VALUES (?,?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, estadocivil);
					insert.setString(2, usuarioalt);
					insert.setBigDecimal(3, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String rrhhestadocivilCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhestadocivilCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String rrhhestadocivilUpdate(BigDecimal idestadocivil,
			String estadocivil, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idestadocivil == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idestadocivil ";
		if (estadocivil == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: estadocivil ";

		// 2. sin nada desde la pagina
		if (estadocivil.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: estadocivil ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhestadocivil WHERE idestadocivil = "
					+ idestadocivil.toString()
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
					sql = "UPDATE RRHHESTADOCIVIL SET estadocivil=?, usuarioact=?, fechaact=? WHERE idestadocivil=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, estadocivil);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idestadocivil);
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
			log.error("Error SQL public String rrhhestadocivilUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String rrhhestadocivilUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// titulo
	public List getRrhhtituloAll(long limit, long offset, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idtitulo,titulo,usuarioalt,usuarioact,fechaalt,fechaact FROM RRHHTITULO where idempresa = "
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
			log.error("Error SQL en el metodo : getRrhhtituloAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhtituloAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getRrhhtituloOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idtitulo,titulo,usuarioalt,usuarioact,fechaalt,fechaact FROM RRHHTITULO "
				+ " where idempresa= "
				+ idempresa.toString()
				+ " and (idtitulo::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " UPPER(titulo) LIKE '%"
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
					.error("Error SQL en el metodo : getRrhhtituloOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhtituloOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getRrhhtituloPK(BigDecimal idtitulo, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idtitulo,titulo,usuarioalt,usuarioact,fechaalt,fechaact FROM RRHHTITULO "
				+ " WHERE idtitulo = "
				+ idtitulo.toString()
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
					.error("Error SQL en el metodo : getRrhhtituloPK( BigDecimal idtitulo ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhtituloPK( BigDecimal idtitulo )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)
	public String rrhhtituloDelete(BigDecimal idtitulo, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM RRHHTITULO " + " WHERE idtitulo = "
				+ idtitulo.toString() + " and idempresa = "
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM RRHHTITULO " + " WHERE idtitulo = "
						+ idtitulo.toString() + " and idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : rrhhtituloDelete( BigDecimal idtitulo ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: rrhhtituloDelete( BigDecimal idtitulo )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String rrhhtituloCreate(String titulo, String usuarioalt,
			BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (titulo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: titulo ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (titulo.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: titulo ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO RRHHTITULO(titulo, usuarioalt, idempresa ) VALUES (?,?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, titulo);
				insert.setString(2, usuarioalt);
				insert.setBigDecimal(3, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String rrhhtituloCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error excepcion public String rrhhtituloCreate(.....)"
					+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String rrhhtituloCreateOrUpdate(BigDecimal idtitulo, String titulo,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idtitulo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtitulo ";
		if (titulo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: titulo ";

		// 2. sin nada desde la pagina
		if (titulo.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: titulo ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhtitulo "
					+ " WHERE idtitulo = " + idtitulo.toString()
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
					sql = "UPDATE RRHHTITULO SET titulo=?, usuarioact=?, fechaact=? WHERE idtitulo=? and idempresa =?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, titulo);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idtitulo);
					insert.setBigDecimal(5, idempresa);
				} else {
					String ins = "INSERT INTO RRHHTITULO(titulo, usuarioalt,idempresa ) VALUES (?,?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, titulo);
					insert.setString(2, usuarioalt);
					insert.setBigDecimal(3, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String rrhhtituloCreateOrUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhtituloCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String rrhhtituloUpdate(BigDecimal idtitulo, String titulo,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idtitulo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtitulo ";
		if (titulo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: titulo ";

		// 2. sin nada desde la pagina
		if (titulo.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: titulo ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhtitulo "
					+ " WHERE idtitulo = " + idtitulo.toString()
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
					sql = "UPDATE RRHHTITULO SET titulo=?, usuarioact=?, fechaact=? WHERE idtitulo=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, titulo);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idtitulo);
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
			log.error("Error SQL public String rrhhtituloUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log.error("Error excepcion public String rrhhtituloUpdate(.....)"
					+ ex);
		}
		return salida;
	}

	// obra social
	public List getRrhhobrasocialAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idobrasocial,obrasocial,usuarioalt,usuarioact,fechaalt,fechaact FROM RRHHOBRASOCIAL where idempresa = "
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
			log.error("Error SQL en el metodo : getRrhhobrasocialAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhobrasocialAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)
	public List getRrhhobrasocialOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idobrasocial,obrasocial,usuarioalt,usuarioact,fechaalt,fechaact FROM RRHHOBRASOCIAL "
				+ " where idempresa= "
				+ idempresa.toString()
				+ " and (idobrasocial::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " UPPER(obrasocial) LIKE '%"
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
					.error("Error SQL en el metodo : getRrhhobrasocialOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhobrasocialOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getRrhhobrasocialPK(BigDecimal idobrasocial,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idobrasocial,obrasocial,usuarioalt,usuarioact,fechaalt,fechaact FROM RRHHOBRASOCIAL "
				+ " WHERE idobrasocial = "
				+ idobrasocial.toString()
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
					.error("Error SQL en el metodo : getRrhhobrasocialPK( BigDecimal idobrasocial ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhobrasocialPK( BigDecimal idobrasocial )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String rrhhobrasocialDelete(BigDecimal idobrasocial,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM RRHHOBRASOCIAL "
				+ " WHERE idobrasocial = " + idobrasocial.toString()
				+ " and idempresa = " + idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM RRHHOBRASOCIAL "
						+ " WHERE idobrasocial = " + idobrasocial.toString()
						+ " and idempresa = " + idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : rrhhobrasocialDelete( BigDecimal idobrasocial ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: rrhhobrasocialDelete( BigDecimal idobrasocial )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String rrhhobrasocialCreate(String obrasocial, String usuarioalt,
			BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (obrasocial == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: obrasocial ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (obrasocial.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: obrasocial ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO RRHHOBRASOCIAL(obrasocial, usuarioalt,idempresa ) VALUES (?,?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, obrasocial);
				insert.setString(2, usuarioalt);
				insert.setBigDecimal(3, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String rrhhobrasocialCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhobrasocialCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String rrhhobrasocialCreateOrUpdate(BigDecimal idobrasocial,
			String obrasocial, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idobrasocial == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idobrasocial ";
		if (obrasocial == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: obrasocial ";

		// 2. sin nada desde la pagina
		if (obrasocial.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: obrasocial ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhobrasocial "
					+ " WHERE idobrasocial = " + idobrasocial.toString()
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
					sql = "UPDATE RRHHOBRASOCIAL SET obrasocial=?, usuarioact=?, fechaact=? WHERE idobrasocial=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, obrasocial);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idobrasocial);
					insert.setBigDecimal(5, idempresa);
				} else {
					String ins = "INSERT INTO RRHHOBRASOCIAL(obrasocial, usuarioalt,idempresa ) VALUES (?,?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, obrasocial);
					insert.setString(2, usuarioalt);
					insert.setBigDecimal(3, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String rrhhobrasocialCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhobrasocialCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String rrhhobrasocialUpdate(BigDecimal idobrasocial,
			String obrasocial, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idobrasocial == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idobrasocial ";
		if (obrasocial == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: obrasocial ";

		// 2. sin nada desde la pagina
		if (obrasocial.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: obrasocial ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhobrasocial "
					+ " WHERE idobrasocial = " + idobrasocial.toString()
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
					sql = "UPDATE RRHHOBRASOCIAL SET obrasocial=?, usuarioact=?, fechaact=? WHERE idobrasocial=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, obrasocial);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idobrasocial);
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
			log.error("Error SQL public String rrhhobrasocialUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String rrhhobrasocialUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// total All RRHHACTIVIDAD
	public long getTotalrrhhactividadAll(BigDecimal idempresa)
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
		String cQuery = "SELECT count(1)AS total FROM  RRHHACTIVIDAD where idempresa = "
				+ idempresa.toString();
		;
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				total = rsSalida.getLong("total");
			} else {
				log
						.warn("getTotalrrhhactividadAll()- Error al recuperar total: ");
			}
		} catch (SQLException sqlException) {
			log.error("getTotalrrhhactividadAll()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log
					.error("getTotalrrhhactividadAll()- Salida por exception: "
							+ ex);
		}
		return total;
	}

	// total Ocu RRHHACTIVIDAD
	public long getTotalrrhhactividadOcu(BigDecimal idempresa, String[] campos,
			String ocurrencia) throws EJBException {
		/**
		 * Entidad: RRHHACTIVIDAD
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer usuario por ocurrencia.
		 */
		long total = 0l;
		ResultSet rsSalida = null;
		String cQuery = "SELECT count(1)AS total FROM RRHHACTIVIDAD WHERE idempresa = "
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
				log
						.warn("getTotalrrhhactividadOcu()- Error al recuperar total: ");
			}
		} catch (SQLException sqlException) {
			log.error("getTotalrrhhactividadOcu()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log
					.error("getTotalrrhhactividadOcu()- Salida por exception: "
							+ ex);
		}
		return total;
	}

	// total All rrhhafjp
	public long getTotalrrhhafjpAll(BigDecimal idempresa) throws EJBException {

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
		String cQuery = "SELECT count(1)AS total FROM  rrhhafjp where idempresa = "
				+ idempresa.toString();
		;
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				total = rsSalida.getLong("total");
			} else {
				log.warn("getTotalrrhhafjpAll()- Error al recuperar total: ");
			}
		} catch (SQLException sqlException) {
			log.error("getTotalrrhhafjpAll()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getTotalrrhhafjpAll()- Salida por exception: " + ex);
		}
		return total;
	}

	// total Ocu rrhhafjp
	public long getTotalrrhhafjpOcu(BigDecimal idempresa, String[] campos,
			String ocurrencia) throws EJBException {
		/**
		 * Entidad: rrhhafjp
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer usuario por ocurrencia.
		 */
		long total = 0l;
		ResultSet rsSalida = null;
		String cQuery = "SELECT count(1)AS total FROM rrhhafjp WHERE idempresa = "
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
				log.warn("getTotalrrhhafjpOcu()- Error al recuperar total: ");
			}
		} catch (SQLException sqlException) {
			log.error("getTotalrrhhafjpOcu()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getTotalrrhhafjpOcu()- Salida por exception: " + ex);
		}
		return total;
	}

	// total All rrhhcategorias
	public long getTotalcategoriasAll(BigDecimal idempresa) throws EJBException {

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
		String cQuery = "SELECT count(1)AS total FROM  rrhhcategorias where idempresa = "
				+ idempresa.toString();
		;
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				total = rsSalida.getLong("total");
			} else {
				log.warn("getTotalcategoriasAll()- Error al recuperar total: ");
			}
		} catch (SQLException sqlException) {
			log.error("getTotalcategoriasAll()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getTotalcategoriasAll()- Salida por exception: " + ex);
		}
		return total;
	}

	// total Ocu rrhhcategorias
	public long getTotalcategoriasOcu(BigDecimal idempresa, String[] campos,
			String ocurrencia) throws EJBException {
		/**
		 * Entidad: rrhhcategorias
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer usuario por ocurrencia.
		 */
		long total = 0l;
		ResultSet rsSalida = null;
		String cQuery = "SELECT count(1)AS total FROM rrhhcategorias WHERE idempresa = "
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
				log.warn("getTotalcategoriasOcu()- Error al recuperar total: ");
			}
		} catch (SQLException sqlException) {
			log.error("getTotalcategoriasOcu()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getTotalcategoriasOcu()- Salida por exception: " + ex);
		}
		return total;
	}

	// total All rrhhcodsf
	public long getTotalcodsfAll(BigDecimal idempresa) throws EJBException {

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
		String cQuery = "SELECT count(1)AS total FROM  rrhhcodsf where idempresa = "
				+ idempresa.toString();
		;
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				total = rsSalida.getLong("total");
			} else {
				log.warn("getTotalcodsfAll()- Error al recuperar total: ");
			}
		} catch (SQLException sqlException) {
			log.error("getTotalcodsfAll()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getTotalcodsfAll()- Salida por exception: " + ex);
		}
		return total;
	}

	// total Ocu rrhhcodsf
	public long getTotalcodsfOcu(BigDecimal idempresa, String[] campos,
			String ocurrencia) throws EJBException {
		/**
		 * Entidad: rrhhcodsf
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer usuario por ocurrencia.
		 */
		long total = 0l;
		ResultSet rsSalida = null;
		String cQuery = "SELECT count(1)AS total FROM rrhhcodsf WHERE idempresa = "
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
				log.warn("getTotalcodsfOcu()- Error al recuperar total: ");
			}
		} catch (SQLException sqlException) {
			log.error("getTotalcodsfOcu()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getTotalcodsfOcu()- Salida por exception: " + ex);
		}
		return total;
	}

	// total All rrhhconceptos
	public long getTotalconceptosAll(BigDecimal idempresa) throws EJBException {

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
		String cQuery = "SELECT count(1)AS total FROM  rrhhconceptos where idempresa = "
				+ idempresa.toString();
		;
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				total = rsSalida.getLong("total");
			} else {
				log.warn("getTotalconceptosAll()- Error al recuperar total: ");
			}
		} catch (SQLException sqlException) {
			log.error("getTotalconceptosAll()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getTotalconceptosAll()- Salida por exception: " + ex);
		}
		return total;
	}

	// total Ocu rrhhconceptos
	public long getTotalconceptosfOcu(BigDecimal idempresa, String[] campos,
			String ocurrencia) throws EJBException {
		/**
		 * Entidad: rrhhconceptos
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer usuario por ocurrencia.
		 */
		long total = 0l;
		ResultSet rsSalida = null;
		String cQuery = "SELECT count(1)AS total FROM rrhhconceptos WHERE idempresa = "
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
				log.warn("getTotalconceptosfOcu()- Error al recuperar total: ");
			}
		} catch (SQLException sqlException) {
			log.error("getTotalconceptosfOcu()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getTotalconceptosfOcu()- Salida por exception: " + ex);
		}
		return total;
	}

	// total All rrhhestadocivil
	public long getTotalestadocivilAll(BigDecimal idempresa)
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
		String cQuery = "SELECT count(1)AS total FROM  rrhhestadocivil where idempresa = "
				+ idempresa.toString();
		;
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				total = rsSalida.getLong("total");
			} else {
				log
						.warn("getTotalestadocivilAll()- Error al recuperar total: ");
			}
		} catch (SQLException sqlException) {
			log.error("getTotalestadocivilAll()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getTotalestadocivilAll()- Salida por exception: " + ex);
		}
		return total;
	}

	// total Ocu rrhhestadocivil
	public long getTotalestadocivilOcu(BigDecimal idempresa, String[] campos,
			String ocurrencia) throws EJBException {
		/**
		 * Entidad: rrhhestadocivil
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer usuario por ocurrencia.
		 */
		long total = 0l;
		ResultSet rsSalida = null;
		String cQuery = "SELECT count(1)AS total FROM rrhhestadocivil WHERE idempresa = "
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
				log
						.warn("getTotalestadocivilOcu()- Error al recuperar total: ");
			}
		} catch (SQLException sqlException) {
			log.error("getTotalestadocivilOcu()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getTotalestadocivilOcu()- Salida por exception: " + ex);
		}
		return total;
	}

	// total All rrhhobrasocial
	public long getTotalobrasocialAll(BigDecimal idempresa) throws EJBException {

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
		String cQuery = "SELECT count(1)AS total FROM  rrhhobrasocial where idempresa = "
				+ idempresa.toString();
		;
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				total = rsSalida.getLong("total");
			} else {
				log.warn("getTotalobrasocialAll()- Error al recuperar total: ");
			}
		} catch (SQLException sqlException) {
			log.error("getTotalobrasocialAll()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getTotalobrasocialAll()- Salida por exception: " + ex);
		}
		return total;
	}

	// total Ocu rrhhobrasocial
	public long getTotalobrasocialOcu(BigDecimal idempresa, String[] campos,
			String ocurrencia) throws EJBException {
		/**
		 * Entidad: rrhhobrasocial
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer usuario por ocurrencia.
		 */
		long total = 0l;
		ResultSet rsSalida = null;
		String cQuery = "SELECT count(1)AS total FROM rrhhobrasocial WHERE idempresa = "
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
				log.warn("getTotalobrasocialOcu()- Error al recuperar total: ");
			}
		} catch (SQLException sqlException) {
			log.error("getTotalobrasocialOcu()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getTotalobrasocialOcu()- Salida por exception: " + ex);
		}
		return total;
	}

	// total All rrhhtipodocumento
	public long getTotaltipodocumentoAll(BigDecimal idempresa)
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
		String cQuery = "SELECT count(1)AS total FROM  rrhhtipodocumento where idempresa = "
				+ idempresa.toString();
		;
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				total = rsSalida.getLong("total");
			} else {
				log
						.warn("getTotaltipodocumentoAll()- Error al recuperar total: ");
			}
		} catch (SQLException sqlException) {
			log.error("getTotaltipodocumentoAll()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log
					.error("getTotaltipodocumentoAll()- Salida por exception: "
							+ ex);
		}
		return total;
	}

	// total Ocu rrhhtipodocumento
	public long getTotaltipodocumentoOcu(BigDecimal idempresa, String[] campos,
			String ocurrencia) throws EJBException {
		/**
		 * Entidad: rrhhtipodocumento
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer usuario por ocurrencia.
		 */
		long total = 0l;
		ResultSet rsSalida = null;
		String cQuery = "SELECT count(1)AS total FROM rrhhtipodocumento WHERE idempresa = "
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
				log
						.warn("getTotaltipodocumentoOcu()- Error al recuperar total: ");
			}
		} catch (SQLException sqlException) {
			log.error("getTotaltipodocumentoOcu()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log
					.error("getTotaltipodocumentoOcu()- Salida por exception: "
							+ ex);
		}
		return total;
	}

	// total All rrhhtitulo
	public long getTotaltituloAll(BigDecimal idempresa) throws EJBException {

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
		String cQuery = "SELECT count(1)AS total FROM  rrhhtitulo where idempresa = "
				+ idempresa.toString();
		;
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				total = rsSalida.getLong("total");
			} else {
				log.warn("getTotaltituloAll()- Error al recuperar total: ");
			}
		} catch (SQLException sqlException) {
			log.error("getTotaltituloAll()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getTotaltituloAll()- Salida por exception: " + ex);
		}
		return total;
	}

	// total Ocu rrhhtitulo
	public long getTotaltituloOcu(BigDecimal idempresa, String[] campos,
			String ocurrencia) throws EJBException {
		/**
		 * Entidad: rrhhtitulo
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer usuario por ocurrencia.
		 */
		long total = 0l;
		ResultSet rsSalida = null;
		String cQuery = "SELECT count(1)AS total FROM rrhhtitulo WHERE idempresa = "
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
				log.warn("getTotaltituloOcu()- Error al recuperar total: ");
			}
		} catch (SQLException sqlException) {
			log.error("getTotaltituloOcu()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getTotaltituloOcu()- Salida por exception: " + ex);
		}
		return total;
	}

	// busqueda laborales
	public List getRRHHbusquedasLaboralesActivas(BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idbusquedalaboral,referencia,fechabusquedadesde,fechabusquedahasta,"
				+ "       seniority,lugartrabajo,descripcionproyecto,descripciontarea,skillexcluyente,"
				+ "       skilldeseable,idioma,posibilidadderenovacion "
				+ "  FROM RRHHBUSQUEDASLABORALES  "
				+ " WHERE idempresa = "
				+ idempresa.toString()
				+ " AND current_date BETWEEN  fechabusquedadesde AND fechabusquedahasta ORDER BY 1 DESC   ;";
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
					.error("Error SQL en el metodo : getRRHHbusquedasLaboralesAtivas() "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRRHHbusquedasLaboralesAtivas()  "
							+ ex);
		}
		return vecSalida;
	}

	// busqueda laborales
	public List getRRHHbusquedasLaboralesActivasOcu(String filtro,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idbusquedalaboral,referencia,fechabusquedadesde,fechabusquedahasta,"
				+ "       seniority,lugartrabajo,descripcionproyecto,descripciontarea,skillexcluyente,"
				+ "       skilldeseable,idioma,posibilidadderenovacion "
				+ "  FROM RRHHBUSQUEDASLABORALES  "
				+ " WHERE idempresa = "
				+ idempresa.toString()
				+ " AND current_date BETWEEN  fechabusquedadesde AND fechabusquedahasta "
				+ filtro + " ORDER BY 1 DESC   ;";
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
					.error("Error SQL en el metodo : getRRHHbusquedasLaboralesAtivas() "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRRHHbusquedasLaboralesAtivas()  "
							+ ex);
		}
		return vecSalida;
	}

	public List getRRHHbusquedasLaboralesAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idbusquedalaboral,referencia,fechabusquedadesde,fechabusquedahasta,seniority,lugartrabajo,descripcionproyecto,descripciontarea,skillexcluyente,skilldeseable,idioma,posibilidadderenovacion FROM RRHHBUSQUEDASLABORALES  "
				+ " where idempresa = "
				+ idempresa.toString()
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
					.error("Error SQL en el metodo : getRRHHbusquedasLaboralesAll() "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRRHHbusquedasLaboralesAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getRRHHbusquedasLaboralesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idbusquedalaboral,referencia,fechabusquedadesde,fechabusquedahasta,seniority,lugartrabajo,descripcionproyecto,descripciontarea,skillexcluyente,skilldeseable,idioma,posibilidadderenovacion FROM RRHHBUSQUEDASLABORALES "
				+ " where idempresa= "
				+ idempresa.toString()
				+ " and (idbusquedalaboral::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " referencia LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " TO_CHAR(fechabusquedadesde, 'dd/mm/yyyy') LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " TO_CHAR(fechabusquedahasta, 'dd/mm/yyyy') LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " seniority LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " lugartrabajo LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " descripcionproyecto LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " descripciontarea LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " skillexcluyente LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " skilldeseable LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " idioma LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " UPPER(posibilidadderenovacion) LIKE '%"
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
					.error("Error SQL en el metodo : getRRHHbusquedasLaboralesOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRRHHbusquedasLaboralesOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getRRHHbusquedasLaboralesPK(BigDecimal idbusquedalaboral,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idbusquedalaboral,referencia,fechabusquedadesde,fechabusquedahasta,seniority,lugartrabajo,descripcionproyecto,descripciontarea,skillexcluyente,skilldeseable,idioma,posibilidadderenovacion FROM RRHHBUSQUEDASLABORALES "
				+ " WHERE idbusquedalaboral = "
				+ idbusquedalaboral.toString()
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
					.error("Error SQL en el metodo : getRRHHbusquedasLaboralesPK( BigDecimal idbusquedalaboral ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRRHHbusquedasLaboralesPK( BigDecimal idbusquedalaboral )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String RRHHbusquedasLaboralesDelete(BigDecimal idbusquedalaboral,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM RRHHBUSQUEDASLABORALES WHERE idbusquedalaboral="
				+ idbusquedalaboral.toString()
				+ " and idempresa = "
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM RRHHBUSQUEDASLABORALES WHERE idbusquedalaboral="
						+ idbusquedalaboral.toString()
						+ " and idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : RRHHbusquedasLaboralesDelete( BigDecimal idbusquedalaboral ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: RRHHbusquedasLaboralesDelete( BigDecimal idbusquedalaboral )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String RRHHbusquedasLaboralesCreate(String referencia,
			Timestamp fechabusquedadesde, Timestamp fechabusquedahasta,
			String seniority, String lugartrabajo, String descripcionproyecto,
			String descripciontarea, String skillexcluyente,
			String skilldeseable, String idioma,
			String posibilidadderenovacion, String usuarioalt,
			BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (referencia == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: referencia ";
		if (fechabusquedadesde == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechabusquedadesde ";
		if (fechabusquedahasta == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechabusquedahasta ";
		if (seniority == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: seniority ";
		// 2. sin nada desde la pagina
		if (referencia.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: referencia ";
		if (seniority.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: seniority ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO RRHHBUSQUEDASLABORALES(referencia, fechabusquedadesde, fechabusquedahasta, seniority, lugartrabajo, descripcionproyecto, descripciontarea, skillexcluyente, skilldeseable,idioma,posibilidadderenovacion,usuarioalt,idempresa ) VALUES (?,?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, referencia);
				insert.setTimestamp(2, fechabusquedadesde);
				insert.setTimestamp(3, fechabusquedahasta);
				insert.setString(4, seniority);
				insert.setString(5, lugartrabajo);
				insert.setString(6, descripcionproyecto);
				insert.setString(7, descripciontarea);
				insert.setString(8, skillexcluyente);
				insert.setString(9, skilldeseable);
				insert.setString(10, idioma);
				insert.setString(11, posibilidadderenovacion);
				insert.setString(12, usuarioalt);
				insert.setBigDecimal(13, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String RRHHbusquedasLaboralesCreate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String RRHHbusquedasLaboralesCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String RRHHbusquedasLaboralesCreateOrUpdate(
			BigDecimal idbusquedalaboral, String referencia,
			Timestamp fechabusquedadesde, Timestamp fechabusquedahasta,
			String seniority, String lugartrabajo, String descripcionproyecto,
			String descripciontarea, String skillexcluyente,
			String skilldeseable, String idioma,
			String posibilidadderenovacion, String usuarioact,
			BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idbusquedalaboral == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idbusquedalaboral ";
		if (referencia == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: referencia ";
		if (fechabusquedadesde == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechabusquedadesde ";
		if (fechabusquedahasta == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechabusquedahasta ";
		if (seniority == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: seniority ";

		// 2. sin nada desde la pagina
		if (referencia.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: referencia ";
		if (seniority.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: seniority ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM RRHHbusquedasLaborales WHERE idbusquedalaboral = "
					+ idbusquedalaboral.toString()
					+ " idempresa = "
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
					sql = "UPDATE RRHHBUSQUEDASLABORALES SET referencia=?, fechabusquedadesde=?, fechabusquedahasta=?, seniority=?, lugartrabajo=?, descripcionproyecto=?, descripciontarea=?, skillexcluyente=?, skilldeseable=?, idioma=?, posibilidadderenovacion=?, fechaact=?, usuarioact=?,idempresa=? WHERE idbusquedalaboral=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, referencia);
					insert.setTimestamp(2, fechabusquedadesde);
					insert.setTimestamp(3, fechabusquedahasta);
					insert.setString(4, seniority);
					insert.setString(5, lugartrabajo);
					insert.setString(6, descripcionproyecto);
					insert.setString(7, descripciontarea);
					insert.setString(8, skillexcluyente);
					insert.setString(9, skilldeseable);
					insert.setString(10, idioma);
					insert.setString(11, posibilidadderenovacion);
					insert.setBigDecimal(12, idbusquedalaboral);
					insert.setTimestamp(13, fechaact);
					insert.setString(14, usuarioact);
					insert.setBigDecimal(15, idempresa);

				} else {
					String ins = "INSERT INTO RRHHBUSQUEDASLABORALES(referencia, fechabusquedadesde, fechabusquedahasta, seniority, lugartrabajo, descripcionproyecto, descripciontarea, skillexcluyenteskilldeseable,idioma,posibilidadderenovacion,idbusquedalaboral, usuarioalt,idempresa ) VALUES (?,?,?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, referencia);
					insert.setTimestamp(2, fechabusquedadesde);
					insert.setTimestamp(3, fechabusquedahasta);
					insert.setString(4, seniority);
					insert.setString(5, lugartrabajo);
					insert.setString(6, descripcionproyecto);
					insert.setString(7, descripciontarea);
					insert.setString(8, skillexcluyente);
					insert.setString(9, skilldeseable);
					insert.setString(10, idioma);
					insert.setString(11, posibilidadderenovacion);
					insert.setBigDecimal(12, idbusquedalaboral);
					insert.setString(13, usuarioalt);
					insert.setBigDecimal(14, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String RRHHbusquedasLaboralesCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String RRHHbusquedasLaboralesCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String RRHHbusquedasLaboralesUpdate(BigDecimal idbusquedalaboral,
			String referencia, Timestamp fechabusquedadesde,
			Timestamp fechabusquedahasta, String seniority,
			String lugartrabajo, String descripcionproyecto,
			String descripciontarea, String skillexcluyente,
			String skilldeseable, String idioma,
			String posibilidadderenovacion, String usuarioact,
			BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idbusquedalaboral == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idbusquedalaboral ";
		if (referencia == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: referencia ";
		if (fechabusquedadesde == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechabusquedadesde ";
		if (fechabusquedahasta == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechabusquedahasta ";
		if (seniority == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: seniority ";

		// 2. sin nada desde la pagina
		if (referencia.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: referencia ";
		if (seniority.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: seniority ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM RRHHbusquedasLaborales WHERE idbusquedalaboral = "
					+ idbusquedalaboral.toString()
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
					sql = "UPDATE RRHHBUSQUEDASLABORALES SET referencia=?, fechabusquedadesde=?, fechabusquedahasta=?, seniority=?, lugartrabajo=?, descripcionproyecto=?, descripciontarea=?, skillexcluyente=?, skilldeseable=?, idioma=?,posibilidadderenovacion=?,fechaact=?, usuarioact=? WHERE idbusquedalaboral=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, referencia);
					insert.setTimestamp(2, fechabusquedadesde);
					insert.setTimestamp(3, fechabusquedahasta);
					insert.setString(4, seniority);
					insert.setString(5, lugartrabajo);
					insert.setString(6, descripcionproyecto);
					insert.setString(7, descripciontarea);
					insert.setString(8, skillexcluyente);
					insert.setString(9, skilldeseable);
					insert.setString(10, idioma);
					insert.setString(11, posibilidadderenovacion);
					insert.setTimestamp(12, fechaact);
					insert.setString(13, usuarioact);
					insert.setBigDecimal(14, idbusquedalaboral);
					insert.setBigDecimal(15, idempresa);
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
					.error("Error SQL public String RRHHbusquedasLaboralesUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String RRHHbusquedasLaboralesUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * Metodos para la entidad: rrhhUserPostulante Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Fri Oct 12 14:20:27 ART 2007
	 * 
	 */
	// para todo (ordena por el segundo campo por defecto)
	public List getRrhhUserPostulanteAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT iduserpostulante,userpostulante,clave,apellido,nombre,email,pregunta,respuesta,direccion,"
				+ "       codigo_postal,idpais,idprovincia,idlocalidad,nrodni,fechanac,telparticular,tellaboral,"
				+ "       telcelular,emailperfil,idpuesto,idlenguaje,idhw,idso,iddb,idapp,idred,idempresa,"
				+ "       usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM RRHHUSERPOSTULANTE WHERE idempresa = "
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
			log.error("Error SQL en el metodo : getRrhhUserPostulanteAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhUserPostulanteAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getRrhhUserPostulanteOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT iduserpostulante,userpostulante,clave,apellido,nombre,email,pregunta,respuesta,"
				+ "       direccion,codigo_postal,idpais,idprovincia,idlocalidad,nrodni,fechanac,telparticular,"
				+ "       tellaboral,telcelular,emailperfil,idpuesto,idlenguaje,idhw,idso,iddb,idapp,idred,"
				+ "       idempresa,usuarioalt,usuarioact,fechaalt,fechaact"
				+ "  FROM RRHHUSERPOSTULANTE WHERE (UPPER(USERPOSTULANTE) LIKE '%"
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
					.error("Error SQL en el metodo : getRrhhUserPostulanteOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhUserPostulanteOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getRrhhUserPostulantePK(BigDecimal iduserpostulante,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT iduserpostulante,userpostulante,clave,apellido,nombre,email,pregunta,"
				+ "       respuesta,direccion,codigo_postal,idpais,idprovincia,idlocalidad,nrodni,"
				+ "       fechanac,telparticular,tellaboral,telcelular,emailperfil,idpuesto,idlenguaje,"
				+ "       idhw,idso,iddb,idapp,idred,idempresa,usuarioalt,usuarioact,fechaalt,fechaact"
				+ "  FROM RRHHUSERPOSTULANTE WHERE iduserpostulante="
				+ iduserpostulante.toString() + " AND idempresa = "
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
					.error("Error SQL en el metodo : getRrhhUserPostulantePK( BigDecimal iduserpostulante ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhUserPostulantePK( BigDecimal iduserpostulante )  "
							+ ex);
		}
		return vecSalida;
	}

	public List getRrhhUserPostulanteDatosCvPK(BigDecimal iduserpostulante,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "" + "SELECT archivocv, fechacv "
				+ "  FROM RRHHUSERPOSTULANTE WHERE iduserpostulante="
				+ iduserpostulante.toString() + " AND idempresa = "
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
					.error("Error SQL en el metodo : getRrhhUserPostulanteDatosCvPK( BigDecimal iduserpostulante ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhUserPostulanteDatosCvPK( BigDecimal iduserpostulante )  "
							+ ex);
		}
		return vecSalida;
	}

	public List validarRrhhUserPostulante(String userpostulante, String clave,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT iduserpostulante,userpostulante,clave,apellido,nombre,email,pregunta,"
				+ "       respuesta,direccion,codigo_postal,idpais,idprovincia,idlocalidad,nrodni,"
				+ "       fechanac,telparticular,tellaboral,telcelular,emailperfil,idpuesto,idlenguaje,"
				+ "       idhw,idso,iddb,idapp,idred,idempresa,usuarioalt,usuarioact,fechaalt,fechaact"
				+ "  FROM RRHHUSERPOSTULANTE WHERE userpostulante='"
				+ userpostulante.toLowerCase() + "' AND clave = '"
				+ clave.toLowerCase() + "' AND idempresa = "
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
					.error("Error SQL en el metodo : validarRrhhUserPostulante( ... ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: validarRrhhUserPostulante( ... )  "
							+ ex);
		}
		return vecSalida;
	}

	public boolean isExisteRrhhUserPostulante(BigDecimal iduserpostulante,
			String userpostulante, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		boolean existe = true;
		String cQuery = "" + "SELECT count(1) as existe "
				+ "  FROM RRHHUSERPOSTULANTE WHERE iduserpostulante <> "
				+ iduserpostulante.toString() + " AND userpostulante = '"
				+ userpostulante.toLowerCase() + "' AND idempresa = "
				+ idempresa.toString() + " ;";

		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);

			if (rsSalida != null) {

				if (rsSalida.next()) {
					existe = rsSalida.getDouble("existe") < 1 ? false : true;
				}

			} else {
				log
						.warn("isExisteRrhhUserPostulante: NO se puede validar existencia !!!");
			}

		} catch (SQLException sqlException) {
			log
					.error("Error SQL en el metodo : isExisteRrhhUserPostulante( ... ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: isExisteRrhhUserPostulante( ... )  "
							+ ex);
		}
		return existe;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String rrhhUserPostulanteDelete(BigDecimal iduserpostulante)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM RRHHUSERPOSTULANTE WHERE iduserpostulante="
				+ iduserpostulante.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM RRHHUSERPOSTULANTE WHERE iduserpostulante="
						+ iduserpostulante.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : rrhhUserPostulanteDelete( BigDecimal iduserpostulante ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: rrhhUserPostulanteDelete( BigDecimal iduserpostulante )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String rrhhUserPostulanteCreate(String userpostulante, String clave,
			String apellido, String nombre, String email, String pregunta,
			String respuesta, String direccion, String codigo_postal,
			BigDecimal idpais, BigDecimal idprovincia, String idlocalidad,
			BigDecimal nrodni, java.sql.Date fechanac, String telparticular,
			String tellaboral, String telcelular, String emailperfil,
			BigDecimal idpuesto, BigDecimal idlenguaje, BigDecimal idhw,
			BigDecimal idso, BigDecimal iddb, BigDecimal idapp,
			BigDecimal idred, BigDecimal idempresa, String usuarioalt)
			throws EJBException {
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (userpostulante == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: userpostulante ";
		if (clave == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: clave ";
		if (apellido == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: apellido ";
		if (nombre == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nombre ";
		if (email == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: email ";
		if (pregunta == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: pregunta ";
		if (respuesta == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: respuesta ";
		if (idpais == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idpais ";
		if (idprovincia == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idprovincia ";
		if (idlocalidad == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idlocalidad ";
		if (emailperfil == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: emailperfil ";
		if (idpuesto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idpuesto ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (userpostulante.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: userpostulante ";
		if (clave.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: clave ";
		if (apellido.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: apellido ";
		if (nombre.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: nombre ";
		if (email.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: email ";
		if (pregunta.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: pregunta ";
		if (respuesta.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: respuesta ";
		if (emailperfil.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: emailperfil ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		try {
			if (salida.equalsIgnoreCase("OK")) {
				String ins = "INSERT INTO RRHHUSERPOSTULANTE(userpostulante, clave, apellido, nombre, email, pregunta, respuesta, direccion, codigo_postal, idpais, idprovincia, idlocalidad, nrodni, fechanac, telparticular, tellaboral, telcelular, emailperfil, idpuesto, idlenguaje, idhw, idso, iddb, idapp, idred, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, userpostulante);
				insert.setString(2, clave);
				insert.setString(3, apellido);
				insert.setString(4, nombre);
				insert.setString(5, email);
				insert.setString(6, pregunta);
				insert.setString(7, respuesta);
				insert.setString(8, direccion);
				insert.setString(9, codigo_postal);
				insert.setBigDecimal(10, idpais);
				insert.setBigDecimal(11, idprovincia);
				insert.setString(12, idlocalidad);
				insert.setBigDecimal(13, nrodni);
				insert.setDate(14, fechanac);
				insert.setString(15, telparticular);
				insert.setString(16, tellaboral);
				insert.setString(17, telcelular);
				insert.setString(18, emailperfil);
				insert.setBigDecimal(19, idpuesto);
				insert.setBigDecimal(20, idlenguaje);
				insert.setBigDecimal(21, idhw);
				insert.setBigDecimal(22, idso);
				insert.setBigDecimal(23, iddb);
				insert.setBigDecimal(24, idapp);
				insert.setBigDecimal(25, idred);
				insert.setBigDecimal(26, idempresa);
				insert.setString(27, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = GeneralBean.getValorSequencia(
							"seq_rrhhuserpostulante", dbconn).toString();
				else
					salida = "Error.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String rrhhUserPostulanteCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhUserPostulanteCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String rrhhUserPostulanteCreateOrUpdate(BigDecimal iduserpostulante,
			String userpostulante, String clave, String apellido,
			String nombre, String email, String pregunta, String respuesta,
			String direccion, String codigo_postal, BigDecimal idpais,
			BigDecimal idprovincia, String idlocalidad, BigDecimal nrodni,
			java.sql.Date fechanac, String telparticular, String tellaboral,
			String telcelular, String emailperfil, BigDecimal idpuesto,
			BigDecimal idlenguaje, BigDecimal idhw, BigDecimal idso,
			BigDecimal iddb, BigDecimal idapp, BigDecimal idred,
			BigDecimal idempresa, String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (iduserpostulante == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: iduserpostulante ";
		if (userpostulante == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: userpostulante ";
		if (clave == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: clave ";
		if (apellido == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: apellido ";
		if (nombre == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nombre ";
		if (email == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: email ";
		if (pregunta == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: pregunta ";
		if (respuesta == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: respuesta ";
		if (idpais == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idpais ";
		if (idprovincia == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idprovincia ";
		if (idlocalidad == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idlocalidad ";
		if (emailperfil == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: emailperfil ";
		if (idpuesto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idpuesto ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (userpostulante.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: userpostulante ";
		if (clave.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: clave ";
		if (apellido.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: apellido ";
		if (nombre.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: nombre ";
		if (email.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: email ";
		if (pregunta.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: pregunta ";
		if (respuesta.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: respuesta ";
		if (emailperfil.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: emailperfil ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhUserPostulante WHERE iduserpostulante = "
					+ iduserpostulante.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE RRHHUSERPOSTULANTE SET userpostulante=?, clave=?, apellido=?, nombre=?, email=?, pregunta=?, respuesta=?, direccion=?, codigo_postal=?, idpais=?, idprovincia=?, idlocalidad=?, nrodni=?, fechanac=?, telparticular=?, tellaboral=?, telcelular=?, emailperfil=?, idpuesto=?, idlenguaje=?, idhw=?, idso=?, iddb=?, idapp=?, idred=?, idempresa=?, usuarioact=?, fechaact=? WHERE iduserpostulante=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, userpostulante);
					insert.setString(2, clave);
					insert.setString(3, apellido);
					insert.setString(4, nombre);
					insert.setString(5, email);
					insert.setString(6, pregunta);
					insert.setString(7, respuesta);
					insert.setString(8, direccion);
					insert.setString(9, codigo_postal);
					insert.setBigDecimal(10, idpais);
					insert.setBigDecimal(11, idprovincia);
					insert.setString(12, idlocalidad);
					insert.setBigDecimal(13, nrodni);
					insert.setDate(14, fechanac);
					insert.setString(15, telparticular);
					insert.setString(16, tellaboral);
					insert.setString(17, telcelular);
					insert.setString(18, emailperfil);
					insert.setBigDecimal(19, idpuesto);
					insert.setBigDecimal(20, idlenguaje);
					insert.setBigDecimal(21, idhw);
					insert.setBigDecimal(22, idso);
					insert.setBigDecimal(23, iddb);
					insert.setBigDecimal(24, idapp);
					insert.setBigDecimal(25, idred);
					insert.setBigDecimal(26, idempresa);
					insert.setString(27, usuarioact);
					insert.setTimestamp(28, fechaact);
					insert.setBigDecimal(29, iduserpostulante);
				} else {
					String ins = "INSERT INTO RRHHUSERPOSTULANTE(userpostulante, clave, apellido, nombre, email, pregunta, respuesta, direccion, codigo_postal, idpais, idprovincia, idlocalidad, nrodni, fechanac, telparticular, tellaboral, telcelular, emailperfil, idpuesto, idlenguaje, idhw, idso, iddb, idapp, idred, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, userpostulante);
					insert.setString(2, clave);
					insert.setString(3, apellido);
					insert.setString(4, nombre);
					insert.setString(5, email);
					insert.setString(6, pregunta);
					insert.setString(7, respuesta);
					insert.setString(8, direccion);
					insert.setString(9, codigo_postal);
					insert.setBigDecimal(10, idpais);
					insert.setBigDecimal(11, idprovincia);
					insert.setString(12, idlocalidad);
					insert.setBigDecimal(13, nrodni);
					insert.setDate(14, fechanac);
					insert.setString(15, telparticular);
					insert.setString(16, tellaboral);
					insert.setString(17, telcelular);
					insert.setString(18, emailperfil);
					insert.setBigDecimal(19, idpuesto);
					insert.setBigDecimal(20, idlenguaje);
					insert.setBigDecimal(21, idhw);
					insert.setBigDecimal(22, idso);
					insert.setBigDecimal(23, iddb);
					insert.setBigDecimal(24, idapp);
					insert.setBigDecimal(25, idred);
					insert.setBigDecimal(26, idempresa);
					insert.setString(27, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String rrhhUserPostulanteCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhUserPostulanteCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String rrhhUserPostulanteUpdate(BigDecimal iduserpostulante,
			String userpostulante, String clave, String apellido,
			String nombre, String email, String pregunta, String respuesta,
			String direccion, String codigo_postal, BigDecimal idpais,
			BigDecimal idprovincia, String idlocalidad, BigDecimal nrodni,
			java.sql.Date fechanac, String telparticular, String tellaboral,
			String telcelular, String emailperfil, BigDecimal idpuesto,
			BigDecimal idlenguaje, BigDecimal idhw, BigDecimal idso,
			BigDecimal iddb, BigDecimal idapp, BigDecimal idred,
			BigDecimal idempresa, String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (iduserpostulante == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: iduserpostulante ";
		if (userpostulante == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: userpostulante ";
		if (clave == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: clave ";
		if (apellido == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: apellido ";
		if (nombre == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nombre ";
		if (email == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: email ";
		if (pregunta == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: pregunta ";
		if (respuesta == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: respuesta ";
		if (idpais == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idpais ";
		if (idprovincia == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idprovincia ";
		if (idlocalidad == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idlocalidad ";
		if (emailperfil == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: emailperfil ";
		if (idpuesto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idpuesto ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (userpostulante.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: userpostulante ";
		if (clave.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: clave ";
		if (apellido.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: apellido ";
		if (nombre.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: nombre ";
		if (email.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: email ";
		if (pregunta.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: pregunta ";
		if (respuesta.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: respuesta ";
		if (emailperfil.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: emailperfil ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhUserPostulante WHERE iduserpostulante = "
					+ iduserpostulante.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE RRHHUSERPOSTULANTE SET userpostulante=?, clave=?, apellido=?, nombre=?, email=?, pregunta=?, respuesta=?, direccion=?, codigo_postal=?, idpais=?, idprovincia=?, idlocalidad=?, nrodni=?, fechanac=?, telparticular=?, tellaboral=?, telcelular=?, emailperfil=?, idpuesto=?, idlenguaje=?, idhw=?, idso=?, iddb=?, idapp=?, idred=?, idempresa=?, usuarioact=?, fechaact=? WHERE iduserpostulante=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, userpostulante);
					insert.setString(2, clave);
					insert.setString(3, apellido);
					insert.setString(4, nombre);
					insert.setString(5, email);
					insert.setString(6, pregunta);
					insert.setString(7, respuesta);
					insert.setString(8, direccion);
					insert.setString(9, codigo_postal);
					insert.setBigDecimal(10, idpais);
					insert.setBigDecimal(11, idprovincia);
					insert.setString(12, idlocalidad);
					insert.setBigDecimal(13, nrodni);
					insert.setDate(14, fechanac);
					insert.setString(15, telparticular);
					insert.setString(16, tellaboral);
					insert.setString(17, telcelular);
					insert.setString(18, emailperfil);
					insert.setBigDecimal(19, idpuesto);
					insert.setBigDecimal(20, idlenguaje);
					insert.setBigDecimal(21, idhw);
					insert.setBigDecimal(22, idso);
					insert.setBigDecimal(23, iddb);
					insert.setBigDecimal(24, idapp);
					insert.setBigDecimal(25, idred);
					insert.setBigDecimal(26, idempresa);
					insert.setString(27, usuarioact);
					insert.setTimestamp(28, fechaact);
					insert.setBigDecimal(29, iduserpostulante);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String rrhhUserPostulanteUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String rrhhUserPostulanteUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String rrhhUserPostulanteUpdateCV(BigDecimal iduserpostulante,
			String archivocv, BigDecimal idempresa, String usuarioact)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (iduserpostulante == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: iduserpostulante ";

		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		if (archivocv == null || archivocv.trim().equalsIgnoreCase(""))
			salida = "Error: No se puede dejar sin datos (nulo) el campo: archivo ";

		// fin validaciones

		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhUserPostulante WHERE iduserpostulante = "
					+ iduserpostulante.toString()
					+ " AND idempresa="
					+ idempresa;
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (salida.equalsIgnoreCase("OK")) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE RRHHUSERPOSTULANTE SET archivocv=?, fechacv=?, usuarioact=?, fechaact=? WHERE iduserpostulante=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, archivocv);
					insert.setDate(2, new java.sql.Date(hoy.getTimeInMillis()));
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, iduserpostulante);
					insert.setBigDecimal(6, idempresa);
				}

				int i = insert.executeUpdate();
				if (i != 1)
					salida = "Fall� actualizaci�n de CV.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error SQL public String rrhhUserPostulanteUpdateCV(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String rrhhUserPostulanteUpdateCV(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * Metodos para la entidad: RrhhBbLlPuestos Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Fri Oct 12 14:20:27 ART 2007
	 */

	public List getRrhhBbLlPuestosAll(BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idpuesto, puesto,idempresa,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM rrhhbbllpuestos WHERE idempresa = "
				+ idempresa.toString() + "  ORDER BY 2  ;";
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
			log.error("Error SQL en el metodo : getRrhhBbLlPuestosAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhBbLlPuestosAll()  "
							+ ex);
		}
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: RrhhBbLlApp Copyrigth(r) sysWarp S.R.L. Fecha de
	 * creacion: Fri Oct 12 14:20:27 ART 2007
	 */

	public List getRrhhBbLlAppAll(BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idapp, app,idempresa,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM rrhhbbllapp WHERE idempresa = "
				+ idempresa.toString() + "  ORDER BY 2  ;";
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
			log.error("Error SQL en el metodo : getRrhhBbLlAppAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhBbLlAppAll()  "
							+ ex);
		}
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: RrhhBbLlDb Copyrigth(r) sysWarp S.R.L. Fecha de
	 * creacion: Fri Oct 12 14:20:27 ART 2007
	 */
	public List getRrhhBbLlDbAll(BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT iddb, db,idempresa,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM rrhhbblldb WHERE idempresa = " + idempresa.toString()
				+ "  ORDER BY 2  ;";
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
			log.error("Error SQL en el metodo : getRrhhBbLlDbAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhBbLlDbAll()  "
							+ ex);
		}
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: RrhhBbLlLenguaje Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Fri Oct 12 14:20:27 ART 2007
	 */
	public List getRrhhBbLlLenguajeAll(BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idlenguaje, lenguaje,idempresa,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM rrhhbblllenguaje WHERE idempresa = "
				+ idempresa.toString() + "  ORDER BY 2  ;";
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
			log.error("Error SQL en el metodo : getRrhhBbLlLenguajeAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhBbLlLenguajeAll()  "
							+ ex);
		}
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: RrhhBbLlHw Copyrigth(r) sysWarp S.R.L. Fecha de
	 * creacion: Fri Oct 12 14:20:27 ART 2007
	 */
	public List getRrhhBbLlHwAll(BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idhw, hw,idempresa,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM rrhhbbllhw WHERE idempresa = " + idempresa.toString()
				+ "  ORDER BY 2  ;";
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
			log.error("Error SQL en el metodo : getRrhhBbLlHwAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhBbLlHwAll()  "
							+ ex);
		}
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: RrhhBbLlRed Copyrigth(r) sysWarp S.R.L. Fecha de
	 * creacion: Fri Oct 12 14:20:27 ART 2007
	 */
	public List getRrhhBbLlRedAll(BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idred, red,idempresa,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM rrhhbbllred WHERE idempresa = "
				+ idempresa.toString() + "  ORDER BY 2  ;";
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
			log.error("Error SQL en el metodo : getRrhhBbLlRedAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhBbLlRedAll()  "
							+ ex);
		}
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: RrhhBbLlSo Copyrigth(r) sysWarp S.R.L. Fecha de
	 * creacion: Fri Oct 12 14:20:27 ART 2007
	 */
	public List getRrhhBbLlSoAll(BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idso, so,idempresa,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM rrhhbbllso WHERE idempresa = " + idempresa.toString()
				+ "  ORDER BY 2  ;";
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
			log.error("Error SQL en el metodo : getRrhhBbLlSoAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhBbLlSoAll()  "
							+ ex);
		}
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: rrhhPostulaciones Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Wed Oct 17 11:16:21 ART 2007
	 */

	// por primary key (primer campo por defecto)
	public boolean isExisteRrhhPostulaciones(BigDecimal iduserpostulante,
			BigDecimal idbusquedalaboral, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;

		String cQuery = "SELECT COUNT(1) AS existe FROM RRHHPOSTULACIONES WHERE iduserpostulante = "
				+ iduserpostulante.toString()
				+ " AND idbusquedalaboral = "
				+ idbusquedalaboral.toString()
				+ " AND idempresa = "
				+ idempresa.toString() + ";";
		boolean existe = true;

		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida != null) {
				if (rsSalida.next()) {
					existe = rsSalida.getDouble("existe") < 1 ? false : true;

				}
			} else {
				log
						.warn("isExisteRrhhPostulaciones: imposible verificar existencia.");
			}
		} catch (SQLException sqlException) {
			log
					.error("Error SQL en el metodo : isExisteRrhhPostulaciones(... ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: isExisteRrhhPostulaciones( ... )  "
							+ ex);
		}

		return existe;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String rrhhPostulacionesCreate(BigDecimal iduserpostulante,
			BigDecimal idbusquedalaboral, BigDecimal idstatus,
			BigDecimal idempresa, String usuarioalt) throws EJBException {
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idbusquedalaboral == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idbusquedalaboral ";
		if (idstatus == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idstatus ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		try {
			if (salida.equalsIgnoreCase("OK")) {
				String ins = "INSERT INTO RRHHPOSTULACIONES(iduserpostulante, idbusquedalaboral, idstatus, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, iduserpostulante);
				insert.setBigDecimal(2, idbusquedalaboral);
				insert.setBigDecimal(3, idstatus);
				insert.setBigDecimal(4, idempresa);
				insert.setString(5, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "OK";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String rrhhPostulacionesCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhPostulacionesCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// estados sot
	// para todo (ordena por el segundo campo por defecto)
	public List getRrhhestadosotAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idestadoot,estadoot,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM RRHHESTADOSOT "
				+ " WHERE idempresa = "
				+ idempresa.toString()
				+ "  ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
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
			log.error("Error SQL en el metodo : getRrhhestadosotAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhestadosotAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getRrhhestadosotOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idestadoot,estadoot,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM RRHHESTADOSOT "
				+ " where idempresa= "
				+ idempresa.toString()
				+ " and ((idestadoot::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase()
				+ "%' OR "
				+ " UPPER(estadoot) LIKE '%"
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
					.error("Error SQL en el metodo : getRrhhestadosotOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhestadosotOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getRrhhestadosotPK(BigDecimal idestadoot, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idestadoot,estadoot,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM RRHHESTADOSOT "
				+ " WHERE idestadoot="
				+ idestadoot.toString()
				+ " AND idempresa = " + idempresa.toString() + ";";
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
					.error("Error SQL en el metodo : getRrhhestadosotPK( BigDecimal idestadoot ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhestadosotPK( BigDecimal idestadoot )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String rrhhestadosotDelete(BigDecimal idestadoot,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM RRHHESTADOSOT " + " WHERE idestadoot = "
				+ idestadoot.toString() + " and idempresa = "
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM RRHHESTADOSOT " + "WHERE idestadoot="
						+ idestadoot.toString() + " and idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : rrhhestadosotDelete( BigDecimal idestadoot ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: rrhhestadosotDelete( BigDecimal idestadoot )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String rrhhestadosotCreate(String estadoot, BigDecimal idempresa,
			String usuarioalt) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (estadoot == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: estadoot ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (estadoot.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: estado ot ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO RRHHESTADOSOT(estadoot, idempresa, usuarioalt ) VALUES (?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, estadoot);
				insert.setBigDecimal(2, idempresa);
				insert.setString(3, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String rrhhestadosotCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhestadosotCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String rrhhestadosotCreateOrUpdate(BigDecimal idestadoot,
			String estadoot, BigDecimal idempresa, String usuarioact)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idestadoot == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idestadoot ";
		if (estadoot == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: estadoot ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (estadoot.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: estadoot ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhestadosot WHERE idestadoot = "
					+ idestadoot.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE RRHHESTADOSOT SET estadoot=?, idempresa=?, usuarioact=?, fechaact=? WHERE idestadoot=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, estadoot);
					insert.setBigDecimal(2, idempresa);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, idestadoot);
				} else {
					String ins = "INSERT INTO RRHHESTADOSOT(estadoot, idempresa, usuarioalt ) VALUES (?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, estadoot);
					insert.setBigDecimal(2, idempresa);
					insert.setString(3, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String rrhhestadosotCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhestadosotCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String rrhhestadosotUpdate(BigDecimal idestadoot, String estadoot,
			BigDecimal idempresa, String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idestadoot == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idestadoot ";
		if (estadoot == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: estadoot ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (estadoot.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: estado ot ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhestadosot WHERE idestadoot = "
					+ idestadoot.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE RRHHESTADOSOT SET estadoot=?, idempresa=?, usuarioact=?, fechaact=? WHERE idestadoot=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, estadoot);
					insert.setBigDecimal(2, idempresa);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, idestadoot);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String rrhhestadosotUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String rrhhestadosotUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// ordenes de trabajo
	// para todo (ordena por el segundo campo por defecto)
	public List getRrhhordenesdetrabajoAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT RRHHORDENESDETRABAJO.idordendetrabajo,clientesclientes.razon,RRHHORDENESDETRABAJO.descripcion,RRHHORDENESDETRABAJO.fechainicio,RRHHORDENESDETRABAJO.fechaprometida,RRHHORDENESDETRABAJO.fechafinal,RRHHORDENESDETRABAJO.horasestimadas,RRHHORDENESDETRABAJO.valorhoracliente,RRHHORDENESDETRABAJO.valorhorarecurso,rrhhestadosot.estadoot,RRHHORDENESDETRABAJO.idempresa,RRHHORDENESDETRABAJO.usuarioalt,RRHHORDENESDETRABAJO.usuarioact,RRHHORDENESDETRABAJO.fechaalt , RRHHORDENESDETRABAJO.fechaact FROM RRHHORDENESDETRABAJO,clientesclientes,rrhhestadosot "
				+ " WHERE clientesclientes.idcliente = RRHHORDENESDETRABAJO.idcliente "
				+ " and clientesclientes.idempresa = RRHHORDENESDETRABAJO.idempresa"
				+ " and rrhhestadosot.idestadoot = RRHHORDENESDETRABAJO.idestadoot "
				+ " and rrhhestadosot.idempresa = RRHHORDENESDETRABAJO.idempresa"
				+ " and RRHHORDENESDETRABAJO.idempresa = "
				+ idempresa.toString()
				+ "  ORDER BY 2  LIMIT "
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
			log.error("Error SQL en el metodo : getRrhhordenesdetrabajoAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhordenesdetrabajoAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getRrhhordenesdetrabajoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT RRHHORDENESDETRABAJO.idordendetrabajo,clientesclientes.razon,RRHHORDENESDETRABAJO.descripcion,RRHHORDENESDETRABAJO.fechainicio,RRHHORDENESDETRABAJO.fechaprometida,RRHHORDENESDETRABAJO.fechafinal,RRHHORDENESDETRABAJO.horasestimadas,RRHHORDENESDETRABAJO.valorhoracliente,RRHHORDENESDETRABAJO.valorhorarecurso,rrhhestadosot.estadoot,RRHHORDENESDETRABAJO.idempresa,RRHHORDENESDETRABAJO.usuarioalt,RRHHORDENESDETRABAJO.usuarioact,RRHHORDENESDETRABAJO.fechaalt , RRHHORDENESDETRABAJO.fechaact FROM RRHHORDENESDETRABAJO,clientesclientes,rrhhestadosot "
				+ " WHERE clientesclientes.idcliente = RRHHORDENESDETRABAJO.idcliente "
				+ " and clientesclientes.idempresa = RRHHORDENESDETRABAJO.idempresa"
				+ " and rrhhestadosot.idestadoot = RRHHORDENESDETRABAJO.idestadoot "
				+ " and rrhhestadosot.idempresa = RRHHORDENESDETRABAJO.idempresa"
				+ " and RRHHORDENESDETRABAJO.idempresa = "
				+ idempresa.toString()
				+ " and ((idordendetrabajo::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase()
				+ "%' OR "
				+ " UPPER(descripcion) LIKE '%"
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
					.error("Error SQL en el metodo : getRrhhordenesdetrabajoOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhordenesdetrabajoOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getRrhhordenesdetrabajoPK(BigDecimal idordendetrabajo,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT RRHHORDENESDETRABAJO.idordendetrabajo,RRHHORDENESDETRABAJO.idcliente,clientesclientes.razon,RRHHORDENESDETRABAJO.descripcion,RRHHORDENESDETRABAJO.fechainicio,RRHHORDENESDETRABAJO.fechaprometida,RRHHORDENESDETRABAJO.fechafinal,RRHHORDENESDETRABAJO.horasestimadas,RRHHORDENESDETRABAJO.valorhoracliente,RRHHORDENESDETRABAJO.valorhorarecurso,RRHHORDENESDETRABAJO.idestadoot,rrhhestadosot.estadoot,RRHHORDENESDETRABAJO.idempresa,RRHHORDENESDETRABAJO.usuarioalt,RRHHORDENESDETRABAJO.usuarioact,RRHHORDENESDETRABAJO.fechaalt,RRHHORDENESDETRABAJO.fechaact FROM RRHHORDENESDETRABAJO,clientesclientes,rrhhestadosot "
				+ " WHERE clientesclientes.idcliente = RRHHORDENESDETRABAJO.idcliente "
				+ " and clientesclientes.idempresa = RRHHORDENESDETRABAJO.idempresa "
				+ " and rrhhestadosot.idestadoot = RRHHORDENESDETRABAJO.idestadoot "
				+ " and rrhhestadosot.idempresa = RRHHORDENESDETRABAJO.idempresa "
				+ " and RRHHORDENESDETRABAJO.idordendetrabajo = "
				+ idordendetrabajo.toString()
				+ " AND RRHHORDENESDETRABAJO.idempresa = "
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
					.error("Error SQL en el metodo : getRrhhordenesdetrabajoPK( BigDecimal idordendetrabajo ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhordenesdetrabajoPK( BigDecimal idordendetrabajo )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String rrhhordenesdetrabajoDelete(BigDecimal idordendetrabajo,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM RRHHORDENESDETRABAJO "
				+ " WHERE idordendetrabajo=" + idordendetrabajo.toString()
				+ " and idempresa = " + idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM RRHHORDENESDETRABAJO "
						+ " WHERE idordendetrabajo="
						+ idordendetrabajo.toString() + " and idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : rrhhordenesdetrabajoDelete( BigDecimal idordendetrabajo ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: rrhhordenesdetrabajoDelete( BigDecimal idordendetrabajo )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String rrhhordenesdetrabajoCreate(BigDecimal idcliente,
			String descripcion, Timestamp fechainicio,
			Timestamp fechaprometida, Timestamp fechafinal,
			BigDecimal horasestimadas, Double valorhoracliente,
			Double valorhorarecurso, BigDecimal idestadoot,
			BigDecimal idempresa, String usuarioalt) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idcliente == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idcliente ";
		if (descripcion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: descripcion ";
		if (fechainicio == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechainicio ";
		if (fechaprometida == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechaprometida ";
		if (fechafinal == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechafinal ";
		if (horasestimadas == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: horasestimadas ";
		if (valorhoracliente == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: valorhoracliente ";
		if (valorhorarecurso == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: valorhorarecurso ";
		if (idestadoot == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idestadoot ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
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
				String ins = "INSERT INTO RRHHORDENESDETRABAJO(idcliente, descripcion, fechainicio, fechaprometida, fechafinal, horasestimadas, valorhoracliente, valorhorarecurso, idestadoot, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, idcliente);
				insert.setString(2, descripcion);
				insert.setTimestamp(3, fechainicio);
				insert.setTimestamp(4, fechaprometida);
				insert.setTimestamp(5, fechafinal);
				insert.setBigDecimal(6, horasestimadas);
				insert.setDouble(7, valorhoracliente.doubleValue());
				insert.setDouble(8, valorhorarecurso.doubleValue());
				insert.setBigDecimal(9, idestadoot);
				insert.setBigDecimal(10, idempresa);
				insert.setString(11, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String rrhhordenesdetrabajoCreate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhordenesdetrabajoCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String rrhhordenesdetrabajoCreateOrUpdate(
			BigDecimal idordendetrabajo, BigDecimal idcliente,
			String descripcion, Timestamp fechainicio,
			Timestamp fechaprometida, Timestamp fechafinal,
			BigDecimal horasestimadas, Double valorhoracliente,
			Double valorhorarecurso, BigDecimal idestadoot,
			BigDecimal idempresa, String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idordendetrabajo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idordendetrabajo ";
		if (idcliente == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idcliente ";
		if (descripcion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: descripcion ";
		if (fechainicio == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechainicio ";
		if (fechaprometida == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechaprometida ";
		if (fechafinal == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechafinal ";
		if (horasestimadas == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: horasestimadas ";
		if (valorhoracliente == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: valorhoracliente ";
		if (valorhorarecurso == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: valorhorarecurso ";
		if (idestadoot == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idestadoot ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (descripcion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: descripcion ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhordenesdetrabajo WHERE idordendetrabajo = "
					+ idordendetrabajo.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE RRHHORDENESDETRABAJO SET idcliente=?, descripcion=?, fechainicio=?, fechaprometida=?, fechafinal=?, horasestimadas=?, valorhoracliente=?, valorhorarecurso=?, idestadoot=?, idempresa=?, usuarioact=?, fechaact=? WHERE idordendetrabajo=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, idcliente);
					insert.setString(2, descripcion);
					insert.setTimestamp(3, fechainicio);
					insert.setTimestamp(4, fechaprometida);
					insert.setTimestamp(5, fechafinal);
					insert.setBigDecimal(6, horasestimadas);
					insert.setDouble(7, valorhoracliente.doubleValue());
					insert.setDouble(8, valorhorarecurso.doubleValue());
					insert.setBigDecimal(9, idestadoot);
					insert.setBigDecimal(10, idempresa);
					insert.setString(11, usuarioact);
					insert.setTimestamp(12, fechaact);
					insert.setBigDecimal(13, idordendetrabajo);
				} else {
					String ins = "INSERT INTO RRHHORDENESDETRABAJO(idcliente, descripcion, fechainicio, fechaprometida, fechafinal, horasestimadas, valorhoracliente, valorhorarecurso, idestadoot, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setBigDecimal(1, idcliente);
					insert.setString(2, descripcion);
					insert.setTimestamp(3, fechainicio);
					insert.setTimestamp(4, fechaprometida);
					insert.setTimestamp(5, fechafinal);
					insert.setBigDecimal(6, horasestimadas);
					insert.setDouble(7, valorhoracliente.doubleValue());
					insert.setDouble(8, valorhorarecurso.doubleValue());
					insert.setBigDecimal(9, idestadoot);
					insert.setBigDecimal(10, idempresa);
					insert.setString(11, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String rrhhordenesdetrabajoCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhordenesdetrabajoCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String rrhhordenesdetrabajoUpdate(BigDecimal idordendetrabajo,
			BigDecimal idcliente, String descripcion, Timestamp fechainicio,
			Timestamp fechaprometida, Timestamp fechafinal,
			BigDecimal horasestimadas, Double valorhoracliente,
			Double valorhorarecurso, BigDecimal idestadoot,
			BigDecimal idempresa, String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idordendetrabajo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idordendetrabajo ";
		if (idcliente == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idcliente ";
		if (descripcion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: descripcion ";
		if (fechainicio == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechainicio ";
		if (fechaprometida == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechaprometida ";
		if (fechafinal == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechafinal ";
		if (horasestimadas == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: horasestimadas ";
		if (valorhoracliente == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: valorhoracliente ";
		if (valorhorarecurso == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: valorhorarecurso ";
		if (idestadoot == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idestadoot ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (descripcion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: descripcion ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhordenesdetrabajo WHERE idordendetrabajo = "
					+ idordendetrabajo.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE RRHHORDENESDETRABAJO SET idcliente=?, descripcion=?, fechainicio=?, fechaprometida=?, fechafinal=?, horasestimadas=?, valorhoracliente=?, valorhorarecurso=?, idestadoot=?, idempresa=?, usuarioact=?, fechaact=? WHERE idordendetrabajo=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, idcliente);
					insert.setString(2, descripcion);
					insert.setTimestamp(3, fechainicio);
					insert.setTimestamp(4, fechaprometida);
					insert.setTimestamp(5, fechafinal);
					insert.setBigDecimal(6, horasestimadas);
					insert.setDouble(7, valorhoracliente.doubleValue());
					insert.setDouble(8, valorhorarecurso.doubleValue());
					insert.setBigDecimal(9, idestadoot);
					insert.setBigDecimal(10, idempresa);
					insert.setString(11, usuarioact);
					insert.setTimestamp(12, fechaact);
					insert.setBigDecimal(13, idordendetrabajo);
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
					.error("Error SQL public String rrhhordenesdetrabajoUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String rrhhordenesdetrabajoUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// lov
	public List getRrhhestadosotLovAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idestadoot, estadoot, usuarioalt, usuarioact, fechaalt, fechaact  FROM  rrhhestadosot where idempresa = "
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
			log.error("Error SQL en el metodo : getRrhhestadosotLovAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhestadosotLovAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getRrhhestadosotLovOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idestadoot, estadoot, usuarioalt, usuarioact, fechaalt, fechaact  FROM  rrhhestadosot "
				+ " where idempresa= "
				+ idempresa.toString()
				+ " and (idestadoot::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " UPPER(estadoot) LIKE '%"
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
					.error("Error SQL en el metodo : getRrhhestadosotLovOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhestadosotLovOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// ot horas
	// para todo (ordena por el segundo campo por defecto)
	public List getRrhhothorasAll(long limit, long offset, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT RRHHOTHORAS.idothoras,RRHHOTHORAS.idordendetrabajo,rrhhordenesdetrabajo.descripcion,RRHHOTHORAS.detalle,RRHHOTHORAS.fecha,RRHHOTHORAS.fechaentrada1,RRHHOTHORAS.fechasalida1,RRHHOTHORAS.fechaentrada2,RRHHOTHORAS.fechasalida2,RRHHOTHORAS.idempresa,RRHHOTHORAS.usuarioalt,RRHHOTHORAS.usuarioact,RRHHOTHORAS.fechaalt,RRHHOTHORAS.fechaact, to_char(((fechasalida1 - fechaentrada1) + (fechasalida2 - fechaentrada2)), 'hh24:mi')   FROM RRHHOTHORAS,rrhhordenesdetrabajo "
				+ " WHERE rrhhordenesdetrabajo.idordendetrabajo = RRHHOTHORAS.idordendetrabajo "
				+ " and rrhhordenesdetrabajo.idempresa = RRHHOTHORAS.idempresa "
				+ "and RRHHOTHORAS.idempresa = "
				+ idempresa.toString()
				+ "  ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
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
			log.error("Error SQL en el metodo : getRrhhothorasAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhothorasAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getRrhhothorasOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT RRHHOTHORAS.idothoras,RRHHOTHORAS.idordendetrabajo,rrhhordenesdetrabajo.descripcion,RRHHOTHORAS.detalle,RRHHOTHORAS.fecha,RRHHOTHORAS.fechaentrada1,RRHHOTHORAS.fechasalida1,RRHHOTHORAS.fechaentrada2,RRHHOTHORAS.fechasalida2,RRHHOTHORAS.idempresa,RRHHOTHORAS.usuarioalt,RRHHOTHORAS.usuarioact,RRHHOTHORAS.fechaalt,RRHHOTHORAS.fechaact ,to_char(((fechasalida1 - fechaentrada1) + (fechasalida2 - fechaentrada2)), 'hh24:mi')   FROM RRHHOTHORAS,rrhhordenesdetrabajo "
				+ " WHERE rrhhordenesdetrabajo.idordendetrabajo = RRHHOTHORAS.idordendetrabajo "
				+ " and rrhhordenesdetrabajo.idempresa = RRHHOTHORAS.idempresa "
				+ " and RRHHOTHORAS.idempresa = "
				+ idempresa.toString()
				+ " and ((RRHHOTHORAS.idothoras::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase()
				+ "%' OR "
				+ " (RRHHOTHORAS.idordendetrabajo::VARCHAR) LIKE '%"
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
					.error("Error SQL en el metodo : getRrhhothorasOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhothorasOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getRrhhothorasPK(BigDecimal idothoras, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT RRHHOTHORAS.idothoras,RRHHOTHORAS.idordendetrabajo,rrhhordenesdetrabajo.descripcion,RRHHOTHORAS.detalle,RRHHOTHORAS.fecha,RRHHOTHORAS.fechaentrada1,RRHHOTHORAS.fechasalida1,RRHHOTHORAS.fechaentrada2,RRHHOTHORAS.fechasalida2,RRHHOTHORAS.idempresa,RRHHOTHORAS.usuarioalt,RRHHOTHORAS.usuarioact,RRHHOTHORAS.fechaalt,RRHHOTHORAS.fechaact FROM RRHHOTHORAS,rrhhordenesdetrabajo "
				+ " WHERE rrhhordenesdetrabajo.idordendetrabajo = RRHHOTHORAS.idordendetrabajo "
				+ " and rrhhordenesdetrabajo.idempresa = RRHHOTHORAS.idempresa "
				+ " and RRHHOTHORAS.idothoras = "
				+ idothoras.toString()
				+ " AND RRHHOTHORAS.idempresa = " + idempresa.toString() + ";";
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
					.error("Error SQL en el metodo : getRrhhothorasPK( BigDecimal idothoras ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhothorasPK( BigDecimal idothoras )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String rrhhothorasDelete(BigDecimal idothoras, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM RRHHOTHORAS " + " WHERE idothoras="
				+ idothoras.toString() + " and idempresa = "
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM RRHHOTHORAS " + " WHERE idothoras= "
						+ idothoras.toString() + " and idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : rrhhothorasDelete( BigDecimal idothoras ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: rrhhothorasDelete( BigDecimal idothoras )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String rrhhothorasCreate(BigDecimal idordendetrabajo,
			String detalle, Timestamp fecha, Timestamp fechaentrada1,
			Timestamp fechasalida1, Timestamp fechaentrada2,
			Timestamp fechasalida2, BigDecimal idempresa, String usuarioalt)
			throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idordendetrabajo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idordendetrabajo ";
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
				String ins = "INSERT INTO RRHHOTHORAS(idordendetrabajo,detalle, fecha, fechaentrada1, fechasalida1, fechaentrada2, fechasalida2, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, idordendetrabajo);
				insert.setString(2, detalle);
				insert.setTimestamp(3, fecha);
				insert.setTimestamp(4, fechaentrada1);
				insert.setTimestamp(5, fechasalida1);
				insert.setTimestamp(6, fechaentrada2);
				insert.setTimestamp(7, fechasalida2);
				insert.setBigDecimal(8, idempresa);
				insert.setString(9, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String rrhhothorasCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error excepcion public String rrhhothorasCreate(.....)"
					+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria
	/*
	 * public String rrhhothorasCreateOrUpdate(BigDecimal idothoras, String
	 * detalle, BigDecimal idordendetrabajo, Timestamp fecha, Timestamp
	 * fechaentrada1, Timestamp fechasalida1, Timestamp fechaentrada2, Timestamp
	 * fechasalida2, BigDecimal idempresa, String usuarioact) throws
	 * EJBException { Calendar hoy = new GregorianCalendar(); Timestamp fechaact
	 * = new Timestamp(hoy.getTime().getTime()); String salida = "NOOK"; //
	 * validaciones de datos: // 1. nulidad de campos if (idothoras == null)
	 * salida =
	 * "Error: No se puede dejar sin datos (nulo) el campo: idothoras "; if
	 * (idordendetrabajo == null) salida = "Error: No se puede dejar sin datos
	 * (nulo) el campo: idordendetrabajo "; if (idempresa == null) salida =
	 * "Error: No se puede dejar sin datos (nulo) el campo: idempresa "; // 2.
	 * sin nada desde la pagina // fin validaciones boolean bError = true; if
	 * (salida.equalsIgnoreCase("NOOK")) bError = false; try { ResultSet
	 * rsSalida = null; String cQuery = "SELECT COUNT(*) FROM rrhhothoras WHERE
	 * idothoras = " + idothoras.toString(); Statement statement =
	 * dbconn.createStatement(); rsSalida = statement.executeQuery(cQuery); int
	 * total = 0; if (rsSalida != null && rsSalida.next()) total =
	 * rsSalida.getInt(1); PreparedStatement insert = null; String sql = ""; if
	 * (!bError) { if (total > 0) { // si existe hago update sql = "UPDATE
	 * RRHHOTHORAS SET idordendetrabajo=?,detalle=?, fecha=?, fechaentrada1=?,
	 * fechasalida1=?, fechaentrada2=?, fechasalida2=?, idempresa=?,
	 * usuarioact=?, fechaact=? WHERE idothoras=?;"; insert =
	 * dbconn.prepareStatement(sql); insert.setBigDecimal(1, idordendetrabajo);
	 * insert.setString(2, detalle); insert.setTimestamp(3, fecha);
	 * insert.setTimestamp(4, fechaentrada1); insert.setTimestamp(5,
	 * fechasalida1); insert.setTimestamp(6, fechaentrada2);
	 * insert.setTimestamp(7, fechasalida2); insert.setBigDecimal(8, idempresa);
	 * insert.setString(9, usuarioact); insert.setTimestamp(10, fechaact);
	 * insert.setBigDecimal(11, idothoras); } else { String ins = "INSERT INTO
	 * RRHHOTHORAS(idordendetrabajo,detalle, fecha, fechaentrada1, fechasalida1,
	 * fechaentrada2, fechasalida2, idempresa, usuarioalt ) VALUES (?, ?, ?, ?,
	 * ?, ?, ?, ?)"; insert = dbconn.prepareStatement(ins); // seteo de campos:
	 * String usuarioalt = usuarioact; // esta variable va a // proposito
	 * insert.setBigDecimal(1, idordendetrabajo); insert.setString(2, detalle);
	 * insert.setTimestamp(3, fecha); insert.setTimestamp(4, fechaentrada1);
	 * insert.setTimestamp(5, fechasalida1); insert.setTimestamp(6,
	 * fechaentrada2); insert.setTimestamp(7, fechasalida2);
	 * insert.setBigDecimal(8, idempresa); insert.setString(9, usuarioalt); }
	 * insert.executeUpdate(); salida = "Alta Correcta."; } } catch
	 * (SQLException sqlException) { salida = "Imposible dar de alta el
	 * registro."; log .error("Error SQL public String
	 * rrhhothorasCreateOrUpdate(.....)" + sqlException); } catch (Exception ex)
	 * { salida = "Imposible dar de alta el registro."; log .error("Error
	 * excepcion public String rrhhothorasCreateOrUpdate(.....)" + ex); } return
	 * salida; }
	 */
	public String rrhhothorasUpdate(BigDecimal idothoras,
			BigDecimal idordendetrabajo, String detalle, Timestamp fecha,
			Timestamp fechaentrada1, Timestamp fechasalida1,
			Timestamp fechaentrada2, Timestamp fechasalida2,
			BigDecimal idempresa, String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idothoras == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idothoras ";
		if (idordendetrabajo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idordendetrabajo ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhothoras WHERE idothoras = "
					+ idothoras.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE RRHHOTHORAS SET idordendetrabajo=?,detalle=?, fecha=?, fechaentrada1=?, fechasalida1=?, fechaentrada2=?, fechasalida2=?, idempresa=?, usuarioact=?, fechaact=? WHERE idothoras=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, idordendetrabajo);
					insert.setString(2, detalle);
					insert.setTimestamp(3, fecha);
					insert.setTimestamp(4, fechaentrada1);
					insert.setTimestamp(5, fechasalida1);
					insert.setTimestamp(6, fechaentrada2);
					insert.setTimestamp(7, fechasalida2);
					insert.setBigDecimal(8, idempresa);
					insert.setString(9, usuarioact);
					insert.setTimestamp(10, fechaact);
					insert.setBigDecimal(11, idothoras);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String rrhhothorasUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log.error("Error excepcion public String rrhhothorasUpdate(.....)"
					+ ex);
		}
		return salida;
	}

	// lov Orden trabajo

	public List getRrhhordentrabajoLovAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idordendetrabajo, descripcion, usuarioalt, usuarioact, fechaalt, fechaact  FROM  rrhhordenesdetrabajo where idempresa = "
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
			log.error("Error SQL en el metodo : getRrhhordentrabajoLovAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhordentrabajoLovAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getRrhhordentrabajoLovOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idordendetrabajo, descripcion, usuarioalt, usuarioact, fechaalt, fechaact  FROM  rrhhordenesdetrabajo "
				+ " where idempresa= "
				+ idempresa.toString()
				+ " and (idordendetrabajo::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " UPPER(descripcion) LIKE '%"
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
					.error("Error SQL en el metodo : getRrhhordentrabajoLovOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhordentrabajoLovOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// ot horas
	// para todo (ordena por el segundo campo por defecto)
	public List getRrhhothorasXUsusarioAll(long limit, long offset,
			BigDecimal idempresa, String usuario, BigDecimal idordendetrabajo)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT RRHHOTHORAS.idothoras,RRHHOTHORAS.idordendetrabajo,rrhhordenesdetrabajo.descripcion,RRHHOTHORAS.detalle,RRHHOTHORAS.fecha,RRHHOTHORAS.fechaentrada1,RRHHOTHORAS.fechasalida1,RRHHOTHORAS.fechaentrada2,RRHHOTHORAS.fechasalida2,RRHHOTHORAS.idempresa,RRHHOTHORAS.usuarioalt,RRHHOTHORAS.usuarioact,RRHHOTHORAS.fechaalt,RRHHOTHORAS.fechaact ,to_char(((fechasalida1 - fechaentrada1) + (fechasalida2 - fechaentrada2)), 'hh24:mi')   FROM RRHHOTHORAS,rrhhordenesdetrabajo  "
				+ " WHERE rrhhordenesdetrabajo.idordendetrabajo = RRHHOTHORAS.idordendetrabajo "
				+ " and rrhhordenesdetrabajo.idempresa = RRHHOTHORAS.idempresa "
				+ " and RRHHOTHORAS.idempresa = "
				+ idempresa.toString()
				+ " and RRHHOTHORAS.idordendetrabajo = "
				+ idordendetrabajo.toString()
				+ " and RRHHOTHORAS.usuarioalt = '"
				+ usuario.toString()
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
			log.error("Error SQL en el metodo : getRrhhothorasXUsusarioAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhothorasXUsusarioAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getRrhhothorasXUsusarioOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa, String usuario,
			BigDecimal idordendetrabajo) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT RRHHOTHORAS.idothoras,RRHHOTHORAS.idordendetrabajo,rrhhordenesdetrabajo.descripcion,RRHHOTHORAS.detalle,RRHHOTHORAS.fecha,RRHHOTHORAS.fechaentrada1,RRHHOTHORAS.fechasalida1,RRHHOTHORAS.fechaentrada2,RRHHOTHORAS.fechasalida2,RRHHOTHORAS.idempresa,RRHHOTHORAS.usuarioalt,RRHHOTHORAS.usuarioact,RRHHOTHORAS.fechaalt,RRHHOTHORAS.fechaact, to_char(((fechasalida1 - fechaentrada1) + (fechasalida2 - fechaentrada2)), 'hh24:mi')   FROM RRHHOTHORAS,rrhhordenesdetrabajo  "
				+ " WHERE rrhhordenesdetrabajo.idordendetrabajo = RRHHOTHORAS.idordendetrabajo "
				+ " and rrhhordenesdetrabajo.idempresa = RRHHOTHORAS.idempresa "
				+ " and RRHHOTHORAS.idempresa = "
				+ idempresa.toString()
				+ " and RRHHOTHORAS.idordendetrabajo = "
				+ idordendetrabajo.toString()
				+ " and RRHHOTHORAS.usuarioalt = '"
				+ usuario.toString()
				+ "' and (UPPER(rrhhordenesdetrabajo.descripcion) LIKE '%"
				+ ocurrencia.toUpperCase()
				+ "%' OR "
				+ " UPPER(RRHHOTHORAS.detalle) LIKE '%"
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
					.error("Error SQL en el metodo : getRrhhothorasXUsusarioOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhothorasXUsusarioOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	public long getTotalEntidadXUsuario(String entidad, BigDecimal idempresa,
			String usuario, BigDecimal idordendetrabajo) throws EJBException {

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
				+ " WHERE idordendetrabajo = " + idordendetrabajo.toString()
				+ " and idempresa = " + idempresa.toString()
				+ " and  usuarioalt = '" + usuario.toString() + "'";
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

	public long getTotalEntidadOcuXUsuario(String entidad, String[] campos,
			String ocurrencia, BigDecimal idempresa, String usuario,
			BigDecimal idordendetrabajo) throws EJBException {

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
				like += " UPPER(" + campos[i] + "::VARCHAR) LIKE '%"
						+ ocurrencia.toUpperCase() + "%' ";
				if (i + 1 < len)
					like += " OR ";
			}
			cQuery += "(" + like + " ) AND idempresa = " + idempresa.toString()
					+ " and  idordendetrabajo = " + idordendetrabajo.toString()
					+ " and  usuarioalt = '" + usuario.toString() + "'";
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

	public List getTotalGeneralAll(BigDecimal idempresa, String usuario,
			BigDecimal idordendetrabajo) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "select to_char( sum( ((fechasalida1 - fechaentrada1) + (fechasalida2 - fechaentrada2)))  , 'hh24:mi') "
				+ " FROM RRHHOTHORAS "
				+ " where RRHHOTHORAS.usuarioalt = '"
				+ usuario.toString()
				+ "' and RRHHOTHORAS.idordendetrabajo = "
				+ idordendetrabajo.toString()
				+ " and RRHHOTHORAS.idempresa = "
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
			log.error("Error SQL en el metodo : getTotalGeneralAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getTotalGeneralAll()  "
							+ ex);
		}
		return vecSalida;
	}

	public List getTotalGeneralOcu(String ocurrencia, BigDecimal idempresa,
			String usuario, BigDecimal idordendetrabajo) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "select to_char( sum( ((fechasalida1 - fechaentrada1) + (fechasalida2 - fechaentrada2)))  , 'hh24:mi') "
				+ " FROM vhorasxusuarios "
				+ " where idempresa = "
				+ idempresa.toString()
				+ " and idordendetrabajo = "
				+ idordendetrabajo.toString()
				+ " and usuarioalt = '"
				+ usuario.toString()
				+ "' and (UPPER(descripcion) LIKE '%"
				+ ocurrencia.toUpperCase()
				+ "%' OR "
				+ " UPPER(detalle) LIKE '%" + ocurrencia.toUpperCase() + "%') ";
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
					.error("Error SQL en el metodo : getTotalGeneralOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getTotalGeneralOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// ot por usuario
	public List getRrhhotxusuarioAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT RRHHOTXUSUARIO.idcodigo,rrhhordenesdetrabajo.descripcion,RRHHOTXUSUARIO.idusuario,RRHHOTXUSUARIO.idempresa,RRHHOTXUSUARIO.usuarioalt,RRHHOTXUSUARIO.usuarioact,RRHHOTXUSUARIO.fechaalt,RRHHOTXUSUARIO.fechaact "
				+ " FROM RRHHOTXUSUARIO,rrhhordenesdetrabajo "
				+ " where rrhhordenesdetrabajo.idordendetrabajo = RRHHOTXUSUARIO.idordendetrabajo "
				+ " and rrhhordenesdetrabajo.idempresa = RRHHOTXUSUARIO.idempresa "
				+ " and RRHHOTXUSUARIO.idempresa = "
				+ idempresa.toString()
				+ "  ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
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
			log.error("Error SQL en el metodo : getRrhhotxusuarioAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhotxusuarioAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getRrhhotxusuarioOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT RRHHOTXUSUARIO.idcodigo,rrhhordenesdetrabajo.descripcion,RRHHOTXUSUARIO.idusuario,RRHHOTXUSUARIO.idempresa,RRHHOTXUSUARIO.usuarioalt,RRHHOTXUSUARIO.usuarioact,RRHHOTXUSUARIO.fechaalt,RRHHOTXUSUARIO.fechaact "
				+ " FROM RRHHOTXUSUARIO,rrhhordenesdetrabajo "
				+ " where rrhhordenesdetrabajo.idordendetrabajo = RRHHOTXUSUARIO.idordendetrabajo "
				+ " and rrhhordenesdetrabajo.idempresa = RRHHOTXUSUARIO.idempresa "
				+ " and RRHHOTXUSUARIO.idempresa = "
				+ idempresa.toString()
				+ " and (UPPER(rrhhordenesdetrabajo.descripcion) LIKE '%"
				+ ocurrencia.toUpperCase()
				+ "%' OR "
				+ " UPPER(globalusuarios.usuario) LIKE '%"
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
					.error("Error SQL en el metodo : getRrhhotxusuarioOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhotxusuarioOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getRrhhotxusuarioPK(BigDecimal idcodigo, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT RRHHOTXUSUARIO.idcodigo,RRHHOTXUSUARIO.idordendetrabajo,rrhhordenesdetrabajo.descripcion,RRHHOTXUSUARIO.idusuario,RRHHOTXUSUARIO.idempresa,RRHHOTXUSUARIO.usuarioalt,RRHHOTXUSUARIO.usuarioact,RRHHOTXUSUARIO.fechaalt,RRHHOTXUSUARIO.fechaact "
				+ " FROM RRHHOTXUSUARIO,rrhhordenesdetrabajo "
				+ " where rrhhordenesdetrabajo.idordendetrabajo = RRHHOTXUSUARIO.idordendetrabajo "
				+ " and rrhhordenesdetrabajo.idempresa = RRHHOTXUSUARIO.idempresa "
				+ " and RRHHOTXUSUARIO.idcodigo="
				+ idcodigo.toString()
				+ " AND RRHHOTXUSUARIO.idempresa = "
				+ idempresa.toString()
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
					.error("Error SQL en el metodo : getRrhhotxusuarioPK( BigDecimal idcodigo ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhotxusuarioPK( BigDecimal idcodigo )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String rrhhotxusuarioDelete(BigDecimal idcodigo, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM RRHHOTXUSUARIO " + " WHERE idcodigo = "
				+ idcodigo.toString() + " and idempresa = "
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM RRHHOTXUSUARIO " + " WHERE idcodigo = "
						+ idcodigo.toString() + " and idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : rrhhotxusuarioDelete( BigDecimal idcodigo ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: rrhhotxusuarioDelete( BigDecimal idcodigo )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String rrhhotxusuarioCreate(BigDecimal idordendetrabajo,
			String idusuario, BigDecimal idempresa, String usuarioalt)
			throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idordendetrabajo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idordendetrabajo ";
		if (idusuario == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idusuario ";
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
				String ins = "INSERT INTO RRHHOTXUSUARIO(idordendetrabajo, idusuario, idempresa, usuarioalt ) VALUES (?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, idordendetrabajo);
				insert.setString(2, idusuario);
				insert.setBigDecimal(3, idempresa);
				insert.setString(4, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String rrhhotxusuarioCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhotxusuarioCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String rrhhotxusuarioCreateOrUpdate(BigDecimal idcodigo,
			BigDecimal idordendetrabajo, String idusuario,
			BigDecimal idempresa, String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idcodigo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idcodigo ";
		if (idordendetrabajo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idordendetrabajo ";
		if (idusuario == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idusuario ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhotxusuario WHERE idcodigo = "
					+ idcodigo.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE RRHHOTXUSUARIO SET idordendetrabajo=?, idusuario=?, idempresa=?, usuarioact=?, fechaact=? WHERE idcodigo=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, idordendetrabajo);
					insert.setString(2, idusuario);
					insert.setBigDecimal(3, idempresa);
					insert.setString(4, usuarioact);
					insert.setTimestamp(5, fechaact);
					insert.setBigDecimal(6, idcodigo);
				} else {
					String ins = "INSERT INTO RRHHOTXUSUARIO(idordendetrabajo, idusuario, idempresa, usuarioalt ) VALUES (?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setBigDecimal(1, idordendetrabajo);
					insert.setString(2, idusuario);
					insert.setBigDecimal(3, idempresa);
					insert.setString(4, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String rrhhotxusuarioCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhotxusuarioCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String rrhhotxusuarioUpdate(BigDecimal idcodigo,
			BigDecimal idordendetrabajo, String idusuario,
			BigDecimal idempresa, String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idcodigo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idcodigo ";
		if (idordendetrabajo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idordendetrabajo ";
		if (idusuario == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idusuario ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhotxusuario WHERE idcodigo = "
					+ idcodigo.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE RRHHOTXUSUARIO SET idordendetrabajo=?, idusuario=?, idempresa=?, usuarioact=?, fechaact=? WHERE idcodigo=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, idordendetrabajo);
					insert.setString(2, idusuario);
					insert.setBigDecimal(3, idempresa);
					insert.setString(4, usuarioact);
					insert.setTimestamp(5, fechaact);
					insert.setBigDecimal(6, idcodigo);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String rrhhotxusuarioUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String rrhhotxusuarioUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public BigDecimal getRrhhotxfiltroxOrdenTrabajo(String usuario)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "select max(rrhhotxusuario.idordendetrabajo) "
				+ " from rrhhotxusuario,rrhhordenesdetrabajo "
				+ " where "
				+ " rrhhordenesdetrabajo.idordendetrabajo = rrhhotxusuario.idordendetrabajo "
				+ " and rrhhordenesdetrabajo.idempresa = rrhhotxusuario.idempresa "
				+ " and rrhhotxusuario.idusuario = '" + usuario.toString()
				+ "'";
		BigDecimal horasusuario = new BigDecimal(0);
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			ResultSetMetaData md = rsSalida.getMetaData();
			if (rsSalida != null) {
				if (rsSalida.next()) {
					horasusuario = rsSalida.getBigDecimal(1);
				}
			}
		} catch (SQLException sqlException) {
			log
					.error("Error SQL en el metodo : getRrhhotxfiltroxOrdenTrabajo() "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhotxfiltroxOrdenTrabajo()  "
							+ ex);
		}
		return horasusuario;
	}

	public List getRrhhOrdenXusuarioLovAll(long limit, long offset,
			BigDecimal idempresa, String usuario) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " select rrhhordenesdetrabajo.idordendetrabajo,rrhhordenesdetrabajo.descripcion "
				+ " from "
				+ " rrhhotxusuario, "
				+ " rrhhordenesdetrabajo "
				+ " where "
				+ " rrhhordenesdetrabajo.idordendetrabajo = rrhhotxusuario.idordendetrabajo "
				+ " and rrhhordenesdetrabajo.idempresa = rrhhotxusuario.idempresa "
				+ " and rrhhotxusuario.idempresa = "
				+ idempresa.toString()
				+ " and rrhhotxusuario.idusuario = '"
				+ usuario.toString()
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
			log.error("Error SQL en el metodo : getRrhhOrdenXusuarioLovAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhOrdenXusuarioLovAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getRrhhOrdenXusuarioLovOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa, String usuario)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = " select rrhhordenesdetrabajo.idordendetrabajo,rrhhordenesdetrabajo.descripcion "
				+ " from "
				+ " rrhhotxusuario, "
				+ " rrhhordenesdetrabajo "
				+ " where "
				+ " rrhhordenesdetrabajo.idordendetrabajo = rrhhotxusuario.idordendetrabajo "
				+ " and rrhhordenesdetrabajo.idempresa = rrhhotxusuario.idempresa "
				+ " and rrhhotxusuario.idempresa = "
				+ idempresa.toString()
				+ " and rrhhotxusuario.idusuario = '"
				+ usuario.toString()
				+ "' and (rrhhordenesdetrabajo.idordendetrabajo LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " UPPER(rrhhordenesdetrabajo.descripcion) LIKE '%"
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
					.error("Error SQL en el metodo : getRrhhOrdenXusuarioLovOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhOrdenXusuarioLovOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	public long getTotalEntidadOrdenxusuario(String entidad,
			BigDecimal idempresa, String usuario) throws EJBException {

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
				+ "       WHERE idempresa = " + idempresa.toString()
				+ " and idusuario = '" + usuario.toString() + "'";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				total = rsSalida.getLong("total");
			} else {
				log
						.warn("getTotalEntidadOrdenxusuario()- Error al recuperar total: "
								+ entidad);
			}
		} catch (SQLException sqlException) {
			log.error("getTotalEntidadOrdenxusuario()- Error SQL: "
					+ sqlException);
		} catch (Exception ex) {
			log.error("getTotalEntidadOrdenxusuario()- Salida por exception: "
					+ ex);
		}
		return total;
	}

	public long getTotalEntidadOrdenOcuxusuario(String entidad,
			String[] campos, String ocurrencia, BigDecimal idempresa,
			String usuario) throws EJBException {

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
				like += " UPPER(" + campos[i] + "::VARCHAR) LIKE '%"
						+ ocurrencia.toUpperCase() + "%' ";
				if (i + 1 < len)
					like += " OR ";
			}
			cQuery += "(" + like + " ) AND idempresa = " + idempresa.toString()
					+ " and idusuario = '" + usuario.toString() + "'";
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				total = rsSalida.getLong("total");
			} else {
				log
						.warn("getTotalEntidadOrdenOcuxusuario()- Error al recuperar total: "
								+ entidad);
			}
		} catch (SQLException sqlException) {
			log.error("getTotalEntidadOrdenOcuxusuario()- Error SQL: "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("getTotalEntidadOrdenOcuxusuario()- Salida por exception: "
							+ ex);
		}
		return total;
	}

	// zona trabajo
	// para todo (ordena por el segundo campo por defecto)
	public List getRrhhzonatrabajoAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idzonatrabajo,zonatrabajo,usuarioalt,usuarioact,fechaalt,fechaact,idempresa FROM RRHHZONATRABAJO WHERE idempresa = "
				+ idempresa.toString()
				+ "  ORDER BY 2  LIMIT "
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
			log.error("Error SQL en el metodo : getRrhhzonatrabajoAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhzonatrabajoAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getRrhhzonatrabajoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idzonatrabajo,zonatrabajo,usuarioalt,usuarioact,fechaalt,fechaact,idempresa FROM RRHHZONATRABAJO WHERE (UPPER(ZONATRABAJO) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%')  AND idempresa = "
				+ idempresa.toString()
				+ " ORDER BY 2  LIMIT "
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
					.error("Error SQL en el metodo : getRrhhzonatrabajoOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhzonatrabajoOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getRrhhzonatrabajoPK(BigDecimal idzonatrabajo,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idzonatrabajo,zonatrabajo,usuarioalt,usuarioact,fechaalt,fechaact,idempresa FROM RRHHZONATRABAJO WHERE idzonatrabajo="
				+ idzonatrabajo.toString()
				+ " AND idempresa = "
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
					.error("Error SQL en el metodo : getRrhhzonatrabajoPK( BigDecimal idzonatrabajo ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhzonatrabajoPK( BigDecimal idzonatrabajo )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String rrhhzonatrabajoDelete(BigDecimal idzonatrabajo,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM RRHHZONATRABAJO "
				+ " WHERE idzonatrabajo = " + idzonatrabajo.toString()
				+ " and idempresa = " + idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM RRHHZONATRABAJO "
						+ " WHERE idzonatrabajo=" + idzonatrabajo.toString()
						+ " and idempresa = " + idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : rrhhzonatrabajoDelete( BigDecimal idzonatrabajo ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: rrhhzonatrabajoDelete( BigDecimal idzonatrabajo )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String rrhhzonatrabajoCreate(String zonatrabajo, String usuarioalt,
			BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (zonatrabajo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: zonatrabajo ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// if(fechaalt == null )
		// salida = "Error: No se puede dejar sin datos (nulo) el campo:
		// fechaalt ";
		// 2. sin nada desde la pagina
		if (zonatrabajo.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: zonatrabajo ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO RRHHZONATRABAJO(zonatrabajo, usuarioalt, idempresa ) VALUES (?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, zonatrabajo);
				insert.setString(2, usuarioalt);
				insert.setBigDecimal(3, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String rrhhzonatrabajoCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhzonatrabajoCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String rrhhzonatrabajoCreateOrUpdate(BigDecimal idzonatrabajo,
			String zonatrabajo, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idzonatrabajo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idzonatrabajo ";
		if (zonatrabajo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: zonatrabajo ";

		// 2. sin nada desde la pagina
		if (zonatrabajo.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: zonatrabajo ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhzonatrabajo WHERE idzonatrabajo = "
					+ idzonatrabajo.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE RRHHZONATRABAJO SET zonatrabajo=?, usuarioact=?, fechaact=?idempresa=? WHERE idzonatrabajo=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, zonatrabajo);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idempresa);
					insert.setBigDecimal(5, idzonatrabajo);
				} else {
					String ins = "INSERT INTO RRHHZONATRABAJO(zonatrabajo, usuarioalt, fechaalt ) VALUES (?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, zonatrabajo);
					insert.setString(2, usuarioalt);
					// insert.setTimestamp(3,fechaalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String rrhhzonatrabajoCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhzonatrabajoCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String rrhhzonatrabajoUpdate(BigDecimal idzonatrabajo,
			String zonatrabajo, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idzonatrabajo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idzonatrabajo ";
		if (zonatrabajo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: zonatrabajo ";

		// 2. sin nada desde la pagina
		if (zonatrabajo.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: zonatrabajo ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhzonatrabajo "
					+ " WHERE idzonatrabajo = " + idzonatrabajo.toString()
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
					sql = "UPDATE RRHHZONATRABAJO SET zonatrabajo=?, usuarioact=?, fechaact=? WHERE idzonatrabajo=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, zonatrabajo);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idzonatrabajo);
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
			log.error("Error SQL public String rrhhzonatrabajoUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String rrhhzonatrabajoUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// art
	// para todo (ordena por el segundo campo por defecto)
	public List getRrhhartAll(long limit, long offset, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idart,art,usuarioalt,usuarioact,fechaalt,fechaact,idempresa FROM RRHHART WHERE idempresa = "
				+ idempresa.toString()
				+ "  ORDER BY 2  LIMIT "
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
			log.error("Error SQL en el metodo : getRrhhartAll() "
					+ sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception: en el metodo: getRrhhartAll()  "
					+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getRrhhartOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idart,art,usuarioalt,usuarioact,fechaalt,fechaact,idempresa FROM RRHHART "
				+ " where idempresa= "
				+ idempresa.toString()
				+ " and (idart::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " UPPER(art) LIKE '%"
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
					.error("Error SQL en el metodo : getRrhhartOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhartOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getRrhhartPK(BigDecimal idart, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idart,art,usuarioalt,usuarioact,fechaalt,fechaact,idempresa FROM RRHHART WHERE idart="
				+ idart.toString()
				+ " AND idempresa = "
				+ idempresa.toString()
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
					.error("Error SQL en el metodo : getRrhhartPK( BigDecimal idart ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhartPK( BigDecimal idart )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String rrhhartDelete(BigDecimal idart, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM RRHHART " + " WHERE idart = "
				+ idart.toString() + " and idempresa = " + idempresa;
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM RRHHART " + " WHERE idart="
						+ idart.toString() + " and idempresa = " + idempresa;
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : rrhhartDelete( BigDecimal idart ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: rrhhartDelete( BigDecimal idart )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String rrhhartCreate(String art, String usuarioalt,
			BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (art == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: art ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// if(fechaalt == null )
		// salida = "Error: No se puede dejar sin datos (nulo) el campo:
		// fechaalt ";
		// 2. sin nada desde la pagina
		if (art.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: art ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO RRHHART(art, usuarioalt,idempresa ) VALUES (?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, art);
				insert.setString(2, usuarioalt);
				insert.setBigDecimal(3, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String rrhhartCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhartCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String rrhhartCreateOrUpdate(BigDecimal idart, String art,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idart == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idart ";
		if (art == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: art ";

		// 2. sin nada desde la pagina
		if (art.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: art ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhart WHERE idart = "
					+ idart.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE RRHHART SET art=?, usuarioact=?, fechaact=?idempresa=? WHERE idart=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, art);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idempresa);
					insert.setBigDecimal(5, idart);
				} else {
					String ins = "INSERT INTO RRHHART(art, usuarioalt, fechaalt ) VALUES (?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, art);
					insert.setString(2, usuarioalt);
					// insert.setTimestamp(3,fechaalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String rrhhartCreateOrUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhartCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String rrhhartUpdate(BigDecimal idart, String art,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idart == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idart ";
		if (art == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: art ";

		// 2. sin nada desde la pagina
		if (art.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: art ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhart " + " WHERE idart = "
					+ idart.toString() + " and idempresa = " + idempresa;
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE RRHHART SET art=?, usuarioact=?, fechaact=? WHERE idart=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, art);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idart);
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
			log.error("Error SQL public String rrhhartUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String rrhhartUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// personal
	// para todo (ordena por el segundo campo por defecto)
	public List getRrhhpersonalAll(long limit, long offset, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		List vecSalida = new ArrayList();
		ResultSet rsFiltro = null;
		// 2012-07-17 - CAMI - Aplicacion de filtro para recuperar el personal
		// sin incluir los dados de baja. Test
		try {
			String cQuery = "Select pe.legajo,pe.apellido,pe.domicilio, pe.esempleado  "
					+ " FROM "
					+ " RRHHPERSONAL pe"
					+ " LEFT JOIN globallocalidades lo ON pe.idlocalidad = lo.idlocalidad "
					+ " LEFT JOIN globalprovincias pr ON pe.idprovincia = pr.idprovincia "
					+ " LEFT JOIN rrhhestadocivil ec ON pe.idestadocivil = ec.idestadocivil  and pe.idempresa = ec.idempresa"
					+ " LEFT JOIN globaltiposdocumentos gd ON pe.idtipodocumento = gd.idtipodocumento  and pe.idempresa = gd.idempresa"
					+ " LEFT JOIN globalpaises gp ON pe.idpais = gp.idpais"
					+ " LEFT JOIN rrhhcategorias gc ON pe.idcategoria = gc.idcategoria  and pe.idempresa = gc.idempresa"
					+ " LEFT JOIN rrhhtitulo gt ON pe.idtitulo = gt.idtitulo  and pe.idempresa = gt.idempresa"
					+ " LEFT JOIN rrhhafjp ga ON pe.idafjp = ga.idafjp  and pe.idempresa = ga.idempresa"
					+ " LEFT JOIN rrhhart gar ON pe.idart = gar.idart  and pe.idempresa = gar.idempresa"
					+ " LEFT JOIN rrhhobrasocial gos ON  pe.idobrasocial = gos.idobrasocial  and pe.idempresa = gos.idempresa"
					+ " LEFT JOIN globallocalidades locpago ON pe.idlocalidadpago = locpago.idlocalidad "
					+ " LEFT JOIN rrhhmodalidadcontrato mod ON pe.idmodalidadcontrato = mod.idmodalidadcontrato and pe.idempresa = mod.idempresa "
					+ " LEFT JOIN cajaidentificadores cmt ON pe.idbancodeposito = cmt.ididentificador and pe.idempresa = cmt.idempresa "
					+ "Where pe.idempresa = "
					+ idempresa.toString()
					+ " order by 2 limit " + limit + " offset " + offset + ";";
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
			log.error("Error SQL en el metodo : getRrhhpersonalAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhpersonalAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getRrhhpersonalOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT pe.legajo, pe.apellido,"
				+ " pe.domicilio,pe.esempleado , pe.idempresa  FROM "
				+ " RRHHPERSONAL pe  where pe.idempresa = "
				+ idempresa.toString() + " and (legajo::VARCHAR = '"
				+ ocurrencia + "' OR " + " UPPER(apellido) LIKE '%"
				+ ocurrencia.toUpperCase() + "%'  )  ORDER BY 2  LIMIT "
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
					.error("Error SQL en el metodo : getRrhhpersonalOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhpersonalOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getRrhhpersonalPK(BigDecimal legajo, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT"
				+ " pe.legajo,"
				+ " pe.apellido,"
				+ " pe.domicilio,"
				+ " pe.puerta,"
				+ " pe.piso,"
				+ " pe.departamento,"
				+ " pe.idlocalidad,"
				+ " lo.localidad,"
				+ " pe.idprovincia,"
				+ " pe.postal,"
				+ " pe.telefono,"
				+ " pe.idestadocivil,"
				+ " ec.estadocivil,"
				+ " pe.fechanac,"
				+ " pe.sexo,"
				+ " pe.idtipodocumento,"
				+ " gd.tipodocumento,"
				+ " pe.nrodocumento,"
				+ " pe.cuil,"
				+ " pe.idpais,"
				+ " gp.pais,"
				+ " pe.fbaja,"
				+ " pe.idcategoria,"
				+ " gc.categoria,"
				+ " pe.tarea,"
				+ " pe.fingreso,"
				+ " pe.idtitulo,"
				+ " gt.titulo,"
				+ " pe.idafjp,"
				+ " ga.afjp,"
				+ " pe.nroafjp,"
				+ " pe.idart,"
				+ " gar.art,"
				+ " pe.nroart,"
				+ " pe.valor01,"
				+ " pe.valor02,"
				+ " pe.valor03,"
				+ " pe.valor04,"
				+ " pe.valor05,"
				+ " pe.mensualoquin,"
				+ " pe.idctacont,"
				+ " pe.idctacont2,"
				+ " pe.aniosrecon,"
				+ " pe.mesrecon,"
				+ " pe.idobrasocial,"
				+ " gos.obrasocial,"
				+ " pe.jubilado,"
				+ " pe.email,"
				+ " pe.idlocalidadpago , "
				+ " locpago.localidad,"
				+ " pe.idmodalidadcontrato,"
				+ " mod.modalidadcontrato,"
				+ " pe.idbancodeposito,"
				+ " cmt.descripcion, "
				+ " pe.idempresa,"
				+ " pe.usuarioalt,"
				+ " pe.usuarioact,"
				+ " pe.fechaalt,"
				+ " pe.fechaact,"
				+ " pe.idlistaconcepto, "
				+ " lis.lista "
				+ " FROM "
				+ " RRHHPERSONAL pe"
				+ " LEFT JOIN globallocalidades lo ON pe.idlocalidad = lo.idlocalidad "
				+ " LEFT JOIN globalprovincias pr ON pe.idprovincia = pr.idprovincia "
				+ " LEFT JOIN rrhhestadocivil ec ON pe.idestadocivil = ec.idestadocivil  and pe.idempresa = ec.idempresa"
				+ " LEFT JOIN globaltiposdocumentos gd ON pe.idtipodocumento = gd.idtipodocumento  and pe.idempresa = gd.idempresa"
				+ " LEFT JOIN globalpaises gp ON pe.idpais = gp.idpais"
				+ " LEFT JOIN rrhhcategorias gc ON pe.idcategoria = gc.idcategoria  and pe.idempresa = gc.idempresa"
				+ " LEFT JOIN rrhhtitulo gt ON pe.idtitulo = gt.idtitulo  and pe.idempresa = gt.idempresa"
				+ " LEFT JOIN rrhhafjp ga ON pe.idafjp = ga.idafjp  and pe.idempresa = ga.idempresa"
				+ " LEFT JOIN rrhhart gar ON pe.idart = gar.idart  and pe.idempresa = gar.idempresa"
				+ " LEFT JOIN rrhhobrasocial gos ON  pe.idobrasocial = gos.idobrasocial  and pe.idempresa = gos.idempresa"
				+ " LEFT JOIN globallocalidades locpago ON pe.idlocalidadpago = locpago.idlocalidad "
				+ " LEFT JOIN rrhhmodalidadcontrato mod ON pe.idmodalidadcontrato = mod.idmodalidadcontrato and pe.idempresa = mod.idempresa "
				+ " LEFT JOIN cajaidentificadores cmt ON pe.idbancodeposito = cmt.ididentificador and pe.idempresa = cmt.idempresa "
				+ " INNER JOIN rrhhlista lis on pe.idlistaconcepto = lis.idlista and  pe.idempresa = lis.idempresa"
				+ " WHERE pe.legajo=" + legajo.toString()
				+ " AND pe.idempresa = " + idempresa.toString() + ";";
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
					.error("Error SQL en el metodo : getRrhhpersonalPK( BigDecimal legajo ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhpersonalPK( BigDecimal legajo )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)
	public String rrhhpersonalDelete(BigDecimal legajo, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM RRHHPERSONAL WHERE legajo="
				+ legajo.toString();

		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				// 20120713 - CAMI - Baja de personal
				// la baja es logica, no fisica. Se registra con la fbaja. Si es
				// nula existe, si tiene una fecha ya se fue.
				cQuery = "Update RRHHPERSONAL set fbaja = current_timestamp , esempleado = false WHERE legajo="
						+ legajo.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : rrhhpersonalDelete( BigDecimal legajo ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: rrhhpersonalDelete( BigDecimal legajo )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String rrhhpersonalCreate(BigDecimal legajo, String apellido,
			String domicilio, BigDecimal puerta, String piso,
			String departamento, String idlocalidad, String idprovincia,
			String postal, String telefono, String idestadocivil,
			Timestamp fechanac, String sexo, String idtipodocumento,
			BigDecimal nrodocumento, String cuil, String idpais,
			Timestamp fbaja, String idcategoria, String tarea,
			Timestamp fingreso, String idtitulo, String idafjp, String nroafjp,
			String idart, String nroart, Double valor01, Double valor02,
			Double valor03, Double valor04, Double valor05,
			String mensualoquin, String idctacont, String idctacont2,
			BigDecimal aniosrecon, BigDecimal mesrecon, String idobrasocial,
			String jubilado, String email, String idlocalidadpago,
			String idmodalidadcontrato, String idbancodeposito,
			String usuarioalt, BigDecimal idempresa, BigDecimal idlista) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (apellido == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: apellido ";
		if (domicilio == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: domicilio ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (apellido.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: apellido ";
		if (domicilio.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: domicilio ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO RRHHPERSONAL(legajo,apellido, domicilio, puerta, piso, departamento, idlocalidad, idprovincia, postal, telefono, idestadocivil, fechanac, sexo, idtipodocumento, nrodocumento, cuil, idpais, fbaja, idcategoria, tarea, fingreso, idtitulo, idafjp, nroafjp, idart, nroart, valor01, valor02, valor03, valor04, valor05, mensualoquin, idctacont, idctacont2, aniosrecon, mesrecon, idobrasocial, jubilado, email,idlocalidadpago,idmodalidadcontrato,idbancodeposito, usuarioalt,idempresa,esempleado ,idlistaconcepto) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, legajo);
				insert.setString(2, apellido);
				insert.setString(3, domicilio);
				insert.setBigDecimal(4, puerta);
				insert.setString(5, piso);
				insert.setString(6, departamento);
				if (idlocalidad != null && !idlocalidad.equalsIgnoreCase("")) {
					insert.setBigDecimal(7, new BigDecimal(idlocalidad));
				} else {
					insert.setBigDecimal(7, null);
				}
				if (idprovincia != null && !idprovincia.equalsIgnoreCase("")) {
					insert.setBigDecimal(8, new BigDecimal(idprovincia));
				} else {
					insert.setBigDecimal(8, null);
				}
				if (postal.equalsIgnoreCase("")
						|| postal.equalsIgnoreCase("null")) {
					insert.setString(9, null);
				} else {
					insert.setString(9, postal);
				}
				insert.setString(10, telefono);
				if (idestadocivil != null
						&& !idestadocivil.equalsIgnoreCase("")) {
					insert.setBigDecimal(11, new BigDecimal(idestadocivil));
				} else {
					insert.setBigDecimal(11, null);
				}
				insert.setTimestamp(12, fechanac);
				insert.setString(13, sexo);
				if (idtipodocumento != null
						&& !idtipodocumento.equalsIgnoreCase("")) {
					insert.setBigDecimal(14, new BigDecimal(idtipodocumento));
				} else {
					insert.setBigDecimal(14, null);
				}
				insert.setBigDecimal(15, nrodocumento);
				insert.setString(16, cuil);
				if (idpais != null && !idpais.equalsIgnoreCase("")) {
					insert.setBigDecimal(17, new BigDecimal(idpais));
				} else {
					insert.setBigDecimal(17, null);
				}
				insert.setTimestamp(18, fbaja);
				if (idcategoria != null && !idcategoria.equalsIgnoreCase("")) {
					insert.setBigDecimal(19, new BigDecimal(idcategoria));
				} else {
					insert.setBigDecimal(19, null);
				}
				insert.setString(20, tarea);
				insert.setTimestamp(21, fingreso);
				if (idtitulo != null && !idtitulo.equalsIgnoreCase("")) {
					insert.setBigDecimal(22, new BigDecimal(idtitulo));
				} else {
					insert.setBigDecimal(22, null);
				}
				if (idafjp != null && !idafjp.equalsIgnoreCase("")) {
					insert.setBigDecimal(23, new BigDecimal(idafjp));
				} else {
					insert.setBigDecimal(23, null);
				}
				insert.setString(24, nroafjp);
				if (idart != null && !idart.equalsIgnoreCase("")) {
					insert.setBigDecimal(25, new BigDecimal(idart));
				} else {
					insert.setBigDecimal(25, null);
				}
				insert.setString(26, nroart);
				insert.setDouble(27, valor01.doubleValue());
				insert.setDouble(28, valor02.doubleValue());
				insert.setDouble(29, valor03.doubleValue());
				insert.setDouble(30, valor04.doubleValue());
				insert.setDouble(31, valor05.doubleValue());
				insert.setString(32, mensualoquin);
				if (idctacont != null && !idctacont.equalsIgnoreCase("")) {
					insert.setBigDecimal(33, new BigDecimal(idctacont));
				} else {
					insert.setBigDecimal(33, null);
				}
				if (idctacont2 != null && !idctacont2.equalsIgnoreCase("")) {
					insert.setBigDecimal(34, new BigDecimal(idctacont2));
				} else {
					insert.setBigDecimal(34, null);
				}
				insert.setBigDecimal(35, aniosrecon);
				insert.setBigDecimal(36, mesrecon);
				if (idobrasocial != null && !idobrasocial.equalsIgnoreCase("")) {
					insert.setBigDecimal(37, new BigDecimal(idobrasocial));
				} else {
					insert.setBigDecimal(37, null);
				}
				insert.setString(38, jubilado);
				insert.setString(39, email);

				if (idlocalidadpago != null
						&& !idlocalidadpago.equalsIgnoreCase("")) {
					insert.setBigDecimal(40, new BigDecimal(idlocalidadpago));
				} else {
					insert.setBigDecimal(40, null);
				}

				if (idmodalidadcontrato != null
						&& !idmodalidadcontrato.equalsIgnoreCase("")) {
					insert.setBigDecimal(41,
							new BigDecimal(idmodalidadcontrato));
				} else {
					insert.setBigDecimal(41, null);
				}
				if (idbancodeposito != null
						&& !idbancodeposito.equalsIgnoreCase("")) {
					insert.setBigDecimal(42, new BigDecimal(idbancodeposito));
				} else {
					insert.setBigDecimal(42, null);
				}

				insert.setString(43, usuarioalt);
				insert.setBigDecimal(44, idempresa);
				boolean esempleado = true;
				insert.setBoolean(45, esempleado);
				insert.setBigDecimal(46, idlista);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String rrhhpersonalCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error excepcion public String rrhhpersonalCreate(.....)"
					+ ex);
		}
		return salida;
	}

	public String rrhhpersonalUpdate(BigDecimal legajo, String apellido,
			String domicilio, BigDecimal puerta, String piso,
			String departamento, String idlocalidad, String idprovincia,
			String postal, String telefono, String idestadocivil,
			Timestamp fechanac, String sexo, String idtipodocumento,
			BigDecimal nrodocumento, String cuil, String idpais,
			Timestamp fbaja, String idcategoria, String tarea,
			Timestamp fingreso, String idtitulo, String idafjp, String nroafjp,
			String idart, String nroart, Double valor01, Double valor02,
			Double valor03, Double valor04, Double valor05,
			String mensualoquin, String idctacont, String idctacont2,
			BigDecimal aniosrecon, BigDecimal mesrecon, String idobrasocial,
			String jubilado, String email, String idlocalidadpago,
			String idmodalidadcontrato, String idbancodeposito,
			String usuarioact, BigDecimal idempresa, BigDecimal idlista) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (legajo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: legajo ";
		if (apellido == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: apellido ";
		if (domicilio == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: domicilio ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (apellido.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: apellido ";
		if (domicilio.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: domicilio ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhpersonal "
					+ " WHERE legajo = " + legajo.toString()
					+ " and idempresa = " + idempresa;
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE RRHHPERSONAL SET apellido=?, domicilio=?, puerta=?, piso=?, departamento=?, idlocalidad=?, idprovincia=?, postal=?, telefono=?, idestadocivil=?, fechanac=?, sexo=?, idtipodocumento=?, nrodocumento=?, cuil=?, idpais=?, fbaja=?, idcategoria=?, tarea=?, fingreso=?, idtitulo=?, idafjp=?, nroafjp=?, idart=?, nroart=?, valor01=?, valor02=?, valor03=?, valor04=?, valor05=?, mensualoquin=?, idctacont=?, idctacont2=?, aniosrecon=?, mesrecon=?, idobrasocial=?, jubilado=?, email=?,idlocalidadpago=?,idmodalidadcontrato=?,idbancodeposito=? , usuarioact=?, fechaact=?, idlistaconcepto=? WHERE legajo=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, apellido);
					insert.setString(2, domicilio);
					insert.setBigDecimal(3, puerta);
					insert.setString(4, piso);
					insert.setString(5, departamento);
					if (idlocalidad != null
							&& !idlocalidad.equalsIgnoreCase("null")) {
						insert.setBigDecimal(6, new BigDecimal(idlocalidad));
					} else {
						insert.setBigDecimal(6, null);
					}
					if (idprovincia != null
							&& !idprovincia.equalsIgnoreCase("null")) {
						insert.setBigDecimal(7, new BigDecimal(idprovincia));
					} else {
						insert.setBigDecimal(7, null);
					}
					if (postal.equalsIgnoreCase("")
							|| postal.equalsIgnoreCase("null")) {
						insert.setString(8, null);
					} else {
						insert.setString(8, postal);
					}
					insert.setString(9, telefono);
					if (idestadocivil != null
							&& !idestadocivil.equalsIgnoreCase("null")) {
						insert.setBigDecimal(10, new BigDecimal(idestadocivil));
					} else {
						insert.setBigDecimal(10, null);
					}
					insert.setTimestamp(11, fechanac);
					insert.setString(12, sexo);
					if (idtipodocumento != null
							&& !idtipodocumento.equalsIgnoreCase("null")) {
						insert.setBigDecimal(13,
								new BigDecimal(idtipodocumento));
					} else {
						insert.setBigDecimal(13, null);
					}
					insert.setBigDecimal(14, nrodocumento);
					insert.setString(15, cuil);
					if (idpais != null && !idpais.equalsIgnoreCase("null")) {
						insert.setBigDecimal(16, new BigDecimal(idpais));
					} else {
						insert.setBigDecimal(16, null);
					}
					insert.setTimestamp(17, fbaja);
					if (idcategoria != null
							&& !idcategoria.equalsIgnoreCase("null")) {
						insert.setBigDecimal(18, new BigDecimal(idcategoria));
					} else {
						insert.setBigDecimal(18, null);
					}
					insert.setString(19, tarea);
					insert.setTimestamp(20, fingreso);
					if (idtitulo != null && !idtitulo.equalsIgnoreCase("null")) {
						insert.setBigDecimal(21, new BigDecimal(idtitulo));
					} else {
						insert.setBigDecimal(21, null);
					}
					if (idafjp != null && !idafjp.equalsIgnoreCase("null")
							&& !idafjp.equalsIgnoreCase("")) {
						insert.setBigDecimal(22, new BigDecimal(idafjp));
					} else {
						insert.setBigDecimal(22, null);
					}
					insert.setString(23, nroafjp);
					if (idart != null && !idart.equalsIgnoreCase("null")) {
						insert.setBigDecimal(24, new BigDecimal(idart));
					} else {
						insert.setBigDecimal(24, null);
					}
					insert.setString(25, nroart);
					insert.setDouble(26, valor01.doubleValue());
					insert.setDouble(27, valor02.doubleValue());
					insert.setDouble(28, valor03.doubleValue());
					insert.setDouble(29, valor04.doubleValue());
					insert.setDouble(30, valor05.doubleValue());
					insert.setString(31, mensualoquin);
					if (idctacont != null
							&& !idctacont.equalsIgnoreCase("null")
							&& !idctacont.equalsIgnoreCase("")) {
						insert.setBigDecimal(32, new BigDecimal(idctacont));
					} else {
						insert.setBigDecimal(32, null);
					}
					if (idctacont2 != null
							&& !idctacont2.equalsIgnoreCase("null")
							&& !idctacont2.equalsIgnoreCase("")) {
						insert.setBigDecimal(33, new BigDecimal(idctacont2));
					} else {
						insert.setBigDecimal(33, null);
					}
					insert.setBigDecimal(34, aniosrecon);
					insert.setBigDecimal(35, mesrecon);
					if (idobrasocial != null
							&& !idobrasocial.equalsIgnoreCase("null")) {
						insert.setBigDecimal(36, new BigDecimal(idobrasocial));
					} else {
						insert.setBigDecimal(36, null);
					}
					insert.setString(37, jubilado);
					insert.setString(38, email);

					if (idlocalidadpago != null
							&& !idlocalidadpago.equalsIgnoreCase("null")) {
						insert.setBigDecimal(39,
								new BigDecimal(idlocalidadpago));
					} else {
						insert.setBigDecimal(39, null);
					}

					if (idmodalidadcontrato != null
							&& !idmodalidadcontrato.equalsIgnoreCase("null")) {
						insert.setBigDecimal(40, new BigDecimal(
								idmodalidadcontrato));
					} else {
						insert.setBigDecimal(40, null);
					}

					if (idbancodeposito != null
							&& !idbancodeposito.equalsIgnoreCase("null")) {
						insert.setBigDecimal(41,
								new BigDecimal(idbancodeposito));
					} else {
						insert.setBigDecimal(41, null);
					}

					insert.setString(42, usuarioact);
					insert.setTimestamp(43, fechaact);
					insert.setBigDecimal(44, idlista);
					insert.setBigDecimal(45, legajo);
					insert.setBigDecimal(46, idempresa);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String rrhhpersonalUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log.error("Error excepcion public String rrhhpersonalUpdate(.....)"
					+ ex);
		}
		return salida;
	}

	// para todo (ordena por el segundo campo por defecto)
	public List getRrrhhtokenkizerAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idtokenkizer,tokenkizer,parametro,descripcion,usuarioalt,usuarioact,fechaalt,fechaact,idempresa FROM RRRHHTOKENKIZER WHERE idempresa = "
				+ idempresa.toString()
				+ "  ORDER BY 2  LIMIT "
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
			log.error("Error SQL en el metodo : getRrrhhtokenkizerAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrrhhtokenkizerAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getRrrhhtokenkizerOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idtokenkizer,tokenkizer,parametro,descripcion,usuarioalt,usuarioact,fechaalt,fechaact,idempresa "
				+ " FROM RRRHHTOKENKIZER "
				+ " where idempresa= "
				+ idempresa.toString()
				+ " and (idtokenkizer::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " UPPER(tokenkizer) LIKE '%"
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
					.error("Error SQL en el metodo : getRrrhhtokenkizerOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrrhhtokenkizerOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getRrrhhtokenkizerPK(BigDecimal idtokenkizer,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idtokenkizer,tokenkizer,parametro,descripcion,usuarioalt,usuarioact,fechaalt,fechaact,idempresa FROM RRRHHTOKENKIZER WHERE idtokenkizer="
				+ idtokenkizer.toString()
				+ " AND idempresa = "
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
					.error("Error SQL en el metodo : getRrrhhtokenkizerPK( BigDecimal idtokenkizer ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrrhhtokenkizerPK( BigDecimal idtokenkizer )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String rrrhhtokenkizerDelete(BigDecimal idtokenkizer,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM RRRHHTOKENKIZER "
				+ " WHERE idtokenkizer = " + idtokenkizer.toString()
				+ " and idempresa = " + idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM RRRHHTOKENKIZER "
						+ " WHERE idtokenkizer = " + idtokenkizer.toString()
						+ " and idempresa = " + idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : rrrhhtokenkizerDelete( BigDecimal idtokenkizer ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: rrrhhtokenkizerDelete( BigDecimal idtokenkizer )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String rrrhhtokenkizerCreate(String tokenkizer, String parametro,
			String descripcion, String usuarioalt, BigDecimal idempresa)
			throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (tokenkizer == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tokenkizer ";
		if (descripcion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: descripcion ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (tokenkizer.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tokenkizer ";
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
				String ins = "INSERT INTO RRRHHTOKENKIZER(tokenkizer, parametro, descripcion, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, tokenkizer);
				insert.setString(2, parametro);
				insert.setString(3, descripcion);
				insert.setString(4, usuarioalt);
				insert.setBigDecimal(5, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String rrrhhtokenkizerCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrrhhtokenkizerCreate(.....)"
							+ ex);
		}
		return salida;
	}

	public String rrrhhtokenkizerUpdate(BigDecimal idtokenkizer,
			String tokenkizer, String parametro, String descripcion,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idtokenkizer == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtokenkizer ";
		if (tokenkizer == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tokenkizer ";
		if (descripcion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: descripcion ";

		// 2. sin nada desde la pagina
		if (tokenkizer.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tokenkizer ";
		if (descripcion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: descripcion ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrrhhtokenkizer WHERE idtokenkizer = "
					+ idtokenkizer.toString()
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
					sql = "UPDATE RRRHHTOKENKIZER SET tokenkizer=?, parametro=?, descripcion=?, usuarioact=?, fechaact=? WHERE idtokenkizer=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, tokenkizer);
					insert.setString(2, parametro);
					insert.setString(3, descripcion);
					insert.setString(4, usuarioact);
					insert.setTimestamp(5, fechaact);
					insert.setBigDecimal(6, idtokenkizer);
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
			log.error("Error SQL public String rrrhhtokenkizerUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String rrrhhtokenkizerUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// lov tipo concepto
	public List getRrhhtipoconceptoLovAll(long limit, long offset)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idtipoconcepto, tipoconcepto FROM  rrhhtipoconcepto "
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
			log.error("Error SQL en el metodo : getRrhhtipoconceptoLovAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhtipoconceptoLovAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getRrhhtipoconceptoLovOcu(long limit, long offset,
			String ocurrencia) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idtipoconcepto, tipoconcepto  FROM  rrhhtipoconcepto "
				+ " where (idtipoconcepto::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " UPPER(tipoconcepto) LIKE '%"
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
					.error("Error SQL en el metodo : getRrhhestadosotLovOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhestadosotLovOcu(String ocurrencia)  "
							+ ex);
		}
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
				log
						.warn("getTotalEntidadSinEmpresa()- Error al recuperar total: "
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

	public long getTotalEntidadSinEmpresaOcu(String entidad, String[] campos,
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
		String cQuery = "SELECT count(1)AS total FROM " + entidad + " WHERE ";
		String like = "";
		int len = campos.length;

		try {
			for (int i = 0; i < len; i++) {
				like += " UPPER(" + campos[i] + "::VARCHAR) LIKE '%"
						+ ocurrencia.toUpperCase() + "%' ";
				if (i + 1 < len)
					like += " OR ";
			}
			cQuery += "(" + like + " )";
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				total = rsSalida.getLong("total");
			} else {
				log
						.warn("getTotalEntidadSinEmpresaOcu()- Error al recuperar total: "
								+ entidad);
			}
		} catch (SQLException sqlException) {
			log.error("getTotalEntidadSinEmpresaOcu()- Error SQL: "
					+ sqlException);
		} catch (Exception ex) {
			log.error("getTotalEntidadSinEmpresaOcu()- Salida por exception: "
					+ ex);
		}
		return total;
	}

	// lov tipo concepto
	public List getRrhhtipocantidadconceptoLovAll(long limit, long offset)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idtipocantidadconcepto, tipocantidadconcepto FROM  rrhhtipocantidadconcepto "
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
					.error("Error SQL en el metodo : getRrhhtipocantidadconceptoLovAll() "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhtipocantidadconceptoLovAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getRrhhtipocantidadconceptoLovOcu(long limit, long offset,
			String ocurrencia) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idtipocantidadconcepto, tipocantidadconcepto  FROM  rrhhtipocantidadconcepto "
				+ " where (idtipocantidadconcepto::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " UPPER(tipocantidadconcepto) LIKE '%"
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
					.error("Error SQL en el metodo : getRrhhestadosotLovOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhestadosotLovOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: rrhhConceptosXPersonal Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Fri Aug 21 13:51:25 ART 2009
	 */

	public String callRrhhConceptosXPersonalCreate(String[] idconcepto,
			BigDecimal legajo, BigDecimal idempresa, String usuarioalt)
			throws EJBException, SQLException {
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idconcepto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idconcepto ";
		if (legajo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: legajo ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";

		// 2. sin nada desde la pagina
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		dbconn.setAutoCommit(false);

		try {

			for (int f = 0; f < idconcepto.length
					&& salida.equalsIgnoreCase("OK"); f++) {
				BigDecimal idconc = new BigDecimal(idconcepto[f]);
				salida = rrhhConceptosXPersonalCreate(idconc, legajo,
						idempresa, usuarioalt);
			}

		} catch (Exception ex) {
			salida = "(EX)Imposible dar de alta concepto - legajo.";
			log
					.error("Error excepcion public String callRrhhConceptosXPersonalCreate(.....)"
							+ ex);
		}

		if (salida.equalsIgnoreCase("OK")) {
			salida = "Alta de conceptos efecuada correctamente.";
			dbconn.commit();
		} else
			dbconn.rollback();

		dbconn.setAutoCommit(true);

		return salida;

	}

	public String callRrhhConceptosXPersonalDelete(String[] idconcepto,
			BigDecimal legajo, BigDecimal idempresa) throws EJBException,
			SQLException {
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idconcepto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idconcepto ";
		if (legajo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: legajo ";

		dbconn.setAutoCommit(false);

		try {

			for (int f = 0; f < idconcepto.length
					&& salida.equalsIgnoreCase("OK"); f++) {
				BigDecimal idconc = new BigDecimal(idconcepto[f]);
				salida = rrhhConceptosXPersonalDelete(idconc, legajo, idempresa);
			}

		} catch (Exception ex) {
			salida = "(EX)Imposible dar de alta concepto - legajo.";
			log
					.error("Error excepcion public String callRrhhConceptosXPersonalDelete(.....)"
							+ ex);
		}

		if (salida.equalsIgnoreCase("OK")) {
			salida = "Concepto/s eliminado/s correctamente.";
			dbconn.commit();
		} else
			dbconn.rollback();

		dbconn.setAutoCommit(true);

		return salida;

	}

	public String rrhhConceptosXPersonalCreate(BigDecimal idconcepto,
			BigDecimal legajo, BigDecimal idempresa, String usuarioalt)
			throws EJBException {
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idconcepto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idconcepto ";
		if (legajo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: legajo ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";

		// 2. sin nada desde la pagina
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		try {
			if (salida.equalsIgnoreCase("OK")) {
				String ins = "INSERT INTO rrhhconceptosxpersonal(idconcepto, legajo, idempresa, usuarioalt) VALUES (?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, idconcepto);
				insert.setBigDecimal(2, legajo);
				insert.setBigDecimal(3, idempresa);
				insert.setString(4, usuarioalt);

				int n = insert.executeUpdate();
				if (n != 1)
					salida = "Error al generar conccepto [" + idconcepto
							+ "] para el legajo [" + legajo + "].";
			}
		} catch (SQLException sqlException) {
			salida = "(ESQL):Imposible dar de alta concepto - legajo.";
			log
					.error("Error SQL public String rrhhConceptosXPersonalCreate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "(EX)Imposible dar de alta concepto - legajo.";
			log
					.error("Error excepcion public String rrhhConceptosXPersonalCreate(.....)"
							+ ex);
		}
		return salida;
	}

	public List getRrhhConceptosXPersonalPK(BigDecimal legajo,
			String asociados, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery =

		"SELECT co.idconcepto,co.concepto,co.imprime,co.formula,co.idctacont, "
				+ "       co.idtipoconcepto,tc.tipoconcepto,co.idtipocantidadconcepto, cc.tipocantidadconcepto "
				+ "  FROM rrhhconceptos co "
				+ "       INNER JOIN rrhhtipoconcepto tc ON co.idtipoconcepto = tc.idtipoconcepto "
				+ "       INNER JOIN rrhhtipocantidadconcepto cc ON co.idtipocantidadconcepto = cc.idtipocantidadconcepto "
				+ " WHERE co.idconcepto "
				+ (asociados.equalsIgnoreCase("S") ? "IN" : " NOT IN") + "  ( "
				+ "         SELECT idconcepto "
				+ "           FROM rrhhconceptosxpersonal "
				+ "          WHERE legajo = " + legajo
				+ ") AND co.idempresa = " + idempresa.toString() + ";";

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
					.error("Error SQL en el metodo : getRrhhConceptosXPersonalPK( BigDecimal idconcepto ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhConceptosXPersonalPK( BigDecimal idconcepto )  "
							+ ex);
		}
		return vecSalida;
	}

	public String rrhhConceptosXPersonalDelete(BigDecimal idconcepto,
			BigDecimal legajo, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM rrhhconceptosxpersonal WHERE idconcepto="
				+ idconcepto.toString()
				+ " AND legajo="
				+ legajo.toString()
				+ " AND idempresa=" + idempresa.toString();
		String salida = "OK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM rrhhconceptosxpersonal WHERE idconcepto="
						+ idconcepto.toString().toString() + " AND legajo="
						+ legajo.toString() + " AND idempresa="
						+ idempresa.toString();
				statement.execute(cQuery);

			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar concepto.";
			log
					.error("Error SQL en el metodo : rrhhConceptosXPersonalDelete( BigDecimal idconcepto, BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar concepto.";
			log
					.error("Salida por exception: en el metodo: rrhhConceptosXPersonalDelete( BigDecimal idconcepto, BigDecimal idempresa )  "
							+ ex);
		}
		return salida;
	}

	public String rrhhConceptosXPersonalReplicar(BigDecimal legajo,
			BigDecimal legajoReplicar, BigDecimal idempresa, String usuarioalt)
			throws EJBException, SQLException {
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos

		if (legajo == null || legajo.longValue() < 1)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: legajo ";
		if (legajoReplicar == null || legajoReplicar.longValue() < 1)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: legajo a copiar ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";

		// 2. sin nada desde la pagina
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		dbconn.setAutoCommit(false);

		try {

			String ins = ""
					+ "INSERT INTO rrhhconceptosxpersonal(idconcepto, legajo, idempresa, usuarioalt) "
					+ "SELECT idconcepto, ?, idempresa, ? "
					+ "  FROM rrhhconceptosxpersonal "
					+ " WHERE legajo = ? "
					+ "   AND idempresa = ? "
					+ "   AND idconcepto NOT IN ( SELECT idconcepto "
					+ "                            FROM rrhhconceptosxpersonal "
					+ "                           WHERE legajo = ? "
					+ "                             AND idempresa = ? )";

			PreparedStatement insert = dbconn.prepareStatement(ins);

			insert.setBigDecimal(1, legajo);
			insert.setString(2, usuarioalt);
			insert.setBigDecimal(3, legajoReplicar);
			insert.setBigDecimal(4, idempresa);
			insert.setBigDecimal(5, legajo);
			insert.setBigDecimal(6, idempresa);

			int n = insert.executeUpdate();

		} catch (Exception ex) {
			salida = "(EX)Imposible dar de alta concepto - legajo.";
			log
					.error("Error excepcion public String callRrhhConceptosXPersonalCreate(.....)"
							+ ex);
		}

		if (salida.equalsIgnoreCase("OK")) {
			salida = "Copia de conceptos efecuada correctamente.";
			dbconn.commit();
		} else
			dbconn.rollback();

		dbconn.setAutoCommit(true);

		return salida;

	}

	// modalidad de contrato
	public List getRrhhmodalidaddecontratoAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idmodalidadcontrato,modalidadcontrato,basico,usuarioalt,usuarioact,fechaalt,fechaact FROM rrhhmodalidadcontrato where idempresa = "
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
					.error("Error SQL en el metodo : getRrhhmodalidaddecontratoAll() "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhmodalidaddecontratoAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getRrhhmodalidaddecontratoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idmodalidadcontrato,modalidadcontrato,usuarioalt,usuarioact,fechaalt,fechaact FROM rrhhmodalidadcontrato "
				+ " where idempresa= "
				+ idempresa.toString()
				+ " and (idmodalidadcontrato::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " UPPER(modalidadcontrato) LIKE '%"
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
					.error("Error SQL en el metodo : getRrhhmodalidaddecontratoOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhmodalidaddecontratoOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getRrhhmodalidaddecontratoPK(BigDecimal idmodalidadcontrato,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idmodalidadcontrato,modalidadcontrato,usuarioalt,usuarioact,fechaalt,fechaact FROM rrhhmodalidadcontrato WHERE idmodalidadcontrato="
				+ idmodalidadcontrato.toString()
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
					.error("Error SQL en el metodo : getRrhhmodalidaddecontratoPK( BigDecimal idactividad ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhmodalidaddecontratoPK( BigDecimal idactividad )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String getRrhhmodalidaddecontratoDelete(
			BigDecimal idmodalidadcontrato, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM rrhhmodalidadcontrato WHERE idmodalidadcontrato = "
				+ idmodalidadcontrato.toString()
				+ " and idempresa = "
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM rrhhmodalidadcontrato WHERE idmodalidadcontrato = "
						+ idmodalidadcontrato.toString()
						+ " and idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : getRrhhmodalidaddecontratoDelete( BigDecimal idmodalidadcontrato ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: getRrhhmodalidaddecontratoDelete( BigDecimal idmodalidadcontrato )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String getRrhhmodalidaddecontratoCreate(String modalidadcontrato,
			String usuarioalt, BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (modalidadcontrato == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: modalidadcontrato ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (modalidadcontrato.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Modalidad de Contrato ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO rrhhmodalidadcontrato(modalidadcontrato, basico, usuarioalt,idempresa ) VALUES (?,?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, modalidadcontrato);
				insert.setString(2, usuarioalt);
				insert.setBigDecimal(3, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String getRrhhmodalidaddecontratoCreate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String getRrhhmodalidaddecontratoCreate(.....)"
							+ ex);
		}
		return salida;
	}

	public String getRrhhmodalidaddecontratoUpdate(
			BigDecimal idmodalidadcontrato, String modalidadcontrato,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idmodalidadcontrato == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idmodalidadcontrato ";
		if (modalidadcontrato == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: modalidadcontrato ";

		// 2. sin nada desde la pagina
		if (modalidadcontrato.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Modalidad de Contrato ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhmodalidadcontrato WHERE idmodalidadcontrato = "
					+ idmodalidadcontrato.toString()
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
					sql = "UPDATE rrhhmodalidadcontrato SET modalidadcontrato=?, usuarioact=?, fechaact=? WHERE idmodalidadcontrato=? and idempresa =?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, modalidadcontrato);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idmodalidadcontrato);
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
					.error("Error SQL public String getRrhhmodalidaddecontratoUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String getRrhhmodalidaddecontratoUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public List getRrhhcajaidentificadoresLovAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT ididentificador, descripcion, usuarioalt, usuarioact, fechaalt, fechaact  FROM  cajaidentificadores where idempresa = "
				+ idempresa.toString()
				+ " and idtipoidentificador = 1 "
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
					.error("Error SQL en el metodo : getRrhhcajaidentificadoresLovAll() "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhcajaidentificadoresLovAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getRrhhcajaidentificadoresLovOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT ididentificador, descripcion, usuarioalt, usuarioact, fechaalt, fechaact  FROM  cajaidentificadores "
				+ " where idempresa= "
				+ idempresa.toString()
				+ " and (ididentificador::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " UPPER(descripcion) LIKE '%"
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
					.error("Error SQL en el metodo : getRrhhcajaidentificadoresLovOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhcajaidentificadoresLovOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	/*
	 * Metodos para la entidad: rrhhformulas Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Wed Jul 04 17:02:24 ART 2012
	 */
	// para todo (ordena por el segundo campo por defecto)
	public List getRrhhformulasAll(long limit, long offset, BigDecimal idempresa)
			throws EJBException {
		String cQuery = "SELECT idformula,formula,descripcion,sql,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM RRHHFORMULAS WHERE idempresa = "
				+ idempresa.toString()
				+ "  ORDER BY 2  LIMIT "
				+ limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = new ArrayList();
		try {
			vecSalida = getLista(cQuery);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhformulasAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getRrhhformulasOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		String cQuery = "SELECT  idformula,formula,descripcion,sql,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM RRHHFORMULAS WHERE (UPPER(FORMULA) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' or (idformula::varchar) like '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%')  AND idempresa = "
				+ idempresa.toString()
				+ " ORDER BY 2  LIMIT "
				+ limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = new ArrayList();
		try {
			vecSalida = getLista(cQuery);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhformulasOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getRrhhformulasPK(BigDecimal idformula, BigDecimal idempresa)
			throws EJBException {
		String cQuery = "SELECT  idformula,formula,descripcion,sql,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM RRHHFORMULAS WHERE idformula="
				+ idformula.toString()
				+ " AND idempresa = "
				+ idempresa.toString() + ";";
		List vecSalida = new ArrayList();
		try {
			vecSalida = getLista(cQuery);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhformulasPK( BigDecimal idformula )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String rrhhformulasDelete(BigDecimal idformula, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM RRHHFORMULAS WHERE idformula="
				+ idformula.toString() + " AND idempresa="
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM RRHHFORMULAS WHERE idformula="
						+ idformula.toString() + " AND idempresa="
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : rrhhformulasDelete( BigDecimal idformula, BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: rrhhformulasDelete( BigDecimal idformula, BigDecimal idempresa )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String rrhhformulasCreate(String formula, String descripcion,
			String sql, BigDecimal idempresa, String usuarioalt)
			throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (formula == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: formula ";
		if (descripcion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: descripcion ";
		if (sql == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: sql ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (formula.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: formula ";
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

				String ins = "INSERT INTO RRHHFORMULAS(formula, descripcion, sql, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, formula);
				insert.setString(2, descripcion);
				insert.setString(3, sql);
				insert.setBigDecimal(4, idempresa);
				insert.setString(5, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String rrhhformulasCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error excepcion public String rrhhformulasCreate(.....)"
					+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String rrhhformulasCreateOrUpdate(BigDecimal idformula,
			String formula, String descripcion, String sql,
			BigDecimal idempresa, String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idformula == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idformula ";
		if (formula == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: formula ";
		if (descripcion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: descripcion ";
		if (sql == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: sql ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (formula.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: formula ";
		if (descripcion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: descripcion ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhformulas WHERE idformula = "
					+ idformula.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sqlQuery = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sqlQuery = "UPDATE RRHHFORMULAS SET formula=?, descripcion=?, sql=?, idempresa=?, usuarioact=?, fechaact=? WHERE idformula=?;";
					insert = dbconn.prepareStatement(sqlQuery);
					insert.setString(1, formula);
					insert.setString(2, descripcion);
					insert.setString(3, sql);
					insert.setBigDecimal(4, idempresa);
					insert.setString(5, usuarioact);
					insert.setTimestamp(6, fechaact);
					insert.setBigDecimal(7, idformula);
				} else {
					String ins = "INSERT INTO RRHHFORMULAS(formula, descripcion, sql, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact;
					insert.setString(1, formula);
					insert.setString(2, descripcion);
					insert.setString(3, sql);
					insert.setBigDecimal(4, idempresa);
					insert.setString(5, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String rrhhformulasCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhformulasCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String rrhhformulasUpdate(BigDecimal idformula, String formula,
			String descripcion, String sql, BigDecimal idempresa,
			String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idformula == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idformula ";
		if (formula == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: formula ";
		if (descripcion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: descripcion ";
		if (sql == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: sql ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (formula.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: formula ";
		if (descripcion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: descripcion ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhformulas WHERE idformula = "
					+ idformula.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sqlQuery = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sqlQuery = "UPDATE RRHHFORMULAS SET formula=?, descripcion=?, sql=?, idempresa=?, usuarioact=?, fechaact=? WHERE idformula=?;";
					insert = dbconn.prepareStatement(sqlQuery);
					insert.setString(1, formula);
					insert.setString(2, descripcion);
					insert.setString(3, sql);
					insert.setBigDecimal(4, idempresa);
					insert.setString(5, usuarioact);
					insert.setTimestamp(6, fechaact);
					insert.setBigDecimal(7, idformula);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String rrhhformulasUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log.error("Error excepcion public String rrhhformulasUpdate(.....)"
					+ ex);
		}
		return salida;
	}

	/*
	 * Metodos para la entidad: rrhhmotivo Copyrigth(r) sysWarp S.R.L. Fecha de
	 * creacion: Mon Jul 16 10:03:59 ART 2012
	 */
	// para todo (ordena por el segundo campo por defecto)
	public List getRrhhmotivoAll(long limit, long offset, BigDecimal idempresa)
			throws EJBException {
		String cQuery = "SELECT idmotivo,motivo,fechaalt,usuarioalt,usuarioact,fechaact,idempresa FROM RRHHMOTIVO WHERE idempresa = "
				+ idempresa.toString()
				+ "  ORDER BY 2  LIMIT "
				+ limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = new ArrayList();
		try {
			vecSalida = getLista(cQuery);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhmotivoAll()  "
							+ ex);
		}
		return vecSalida;
	}

	public List getRrhhmotivoAll(boolean esempleado, BigDecimal idempresa)
			throws EJBException {
		String cQuery = "SELECT idmotivo,motivo,fechaalt,usuarioalt,usuarioact,fechaact,idempresa FROM RRHHMOTIVO WHERE idempresa = "
				+ idempresa.toString() + " and esempleado = " + esempleado;
		List vecSalida = new ArrayList();
		try {
			vecSalida = getLista(cQuery);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhmotivoAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getRrhhmotivoOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		String cQuery = "SELECT  idmotivo,motivo,fechaalt,usuarioalt,usuarioact,fechaact,idempresa FROM RRHHMOTIVO WHERE (UPPER(MOTIVO) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' or idmotivo::varchar like '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%')  AND idempresa = "
				+ idempresa.toString()
				+ " ORDER BY 2  LIMIT "
				+ limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = new ArrayList();
		try {
			vecSalida = getLista(cQuery);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhmotivoOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getRrhhmotivoPK(BigDecimal idmotivo, BigDecimal idempresa)
			throws EJBException {
		String cQuery = "SELECT  idmotivo,motivo,esempleado,fechaalt,usuarioalt,usuarioact,fechaact,idempresa FROM RRHHMOTIVO WHERE idmotivo="
				+ idmotivo.toString()
				+ " AND idempresa = "
				+ idempresa.toString() + ";";
		List vecSalida = new ArrayList();
		try {
			vecSalida = getLista(cQuery);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhmotivoPK( BigDecimal idmotivo)  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String rrhhmotivoDelete(BigDecimal idmotivo, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM RRHHMOTIVO WHERE idmotivo="
				+ idmotivo.toString() + " AND idempresa="
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM RRHHMOTIVO WHERE idmotivo="
						+ idmotivo.toString().toString() + " AND idempresa="
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : rrhhmotivoDelete( BigDecimal idmotivo, BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: rrhhmotivoDelete( BigDecimal idmotivo, BigDecimal idempresa )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String rrhhmotivoCreate(String motivo, boolean esempleado,
			String usuarioalt, BigDecimal idempresa) throws EJBException {
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
				String ins = "INSERT INTO RRHHMOTIVO(motivo,esempleado, usuarioalt,idempresa ) VALUES (?, ?, ? , ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, motivo);
				insert.setBoolean(2, esempleado);
				insert.setString(3, usuarioalt);
				insert.setBigDecimal(4, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String rrhhmotivoCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error excepcion public String rrhhmotivoCreate(.....)"
					+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String rrhhmotivoCreateOrUpdate(BigDecimal idmotivo, String motivo,
			boolean esempleado, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idmotivo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idmotivo";

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhmotivo WHERE idmotivobaja = "
					+ idmotivo.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE RRHHMOTIVO SET motivo=?, usuarioact=?, fechaact=?, idempresa=?, esempleado=? WHERE idmotivo=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, motivo);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idempresa);
					insert.setBoolean(5, esempleado);
					insert.setBigDecimal(6, idmotivo);
				} else {
					String ins = "INSERT INTO RRHHMOTIVO(motivo,  usuarioalt,idempresa , esempleado) VALUES (?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact;
					insert.setString(1, motivo);
					insert.setString(2, usuarioalt);
					insert.setBigDecimal(3, idempresa);
					insert.setBoolean(4, esempleado);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String rrhhmotivoCreateOrUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhmotivoCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String rrhhmotivoUpdate(BigDecimal idmotivo, String motivo,
			boolean esempleado, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idmotivo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idmotivobaja ";

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhmotivo WHERE idmotivo= "
					+ idmotivo.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE RRHHMOTIVO SET motivo=?, usuarioact=?, fechaact=?,idempresa=? , esempleado=? WHERE idmotivo=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, motivo);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idempresa);
					insert.setBoolean(5, esempleado);
					insert.setBigDecimal(6, idmotivo);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String rrhhmotivoUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log.error("Error excepcion public String rrhhmotivoUpdate(.....)"
					+ ex);
		}
		return salida;
	}

	/*
	 * Metodos para la entidad: rrhhrazonesmotivobaja Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Mon Jul 16 11:20:19 ART 2012
	 */
	// para todo (ordena por el segundo campo por defecto)
	public List getRrhhrazonesAll(long limit, long offset, BigDecimal idempresa)
			throws EJBException {
		String cQuery = "Select raz.idrazon,raz.razon,raz.idmotivo, mot.motivo, raz.idempresa from rrhhrazones raz inner join rrhhmotivo mot on (raz.idmotivo = mot.idmotivo and raz.idempresa = mot.idempresa ) where raz.idempresa = "
				+ idempresa.toString()
				+ "  ORDER BY 2  LIMIT "
				+ limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = new ArrayList();
		try {
			vecSalida = getLista(cQuery);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhrazonesAll()  "
							+ ex);
		}
		return vecSalida;
	}

	public List getRrhhrazonesAll(BigDecimal idmotivo, BigDecimal idempresa)
			throws EJBException {
		String cQuery = "Select raz.idrazon,raz.razon,raz.idmotivo, mot.motivo, raz.idempresa from rrhhrazones raz inner join rrhhmotivo mot on (raz.idmotivo = mot.idmotivo and raz.idempresa = mot.idempresa ) where raz.idempresa = "
				+ idempresa.toString()
				+ " and raz.idmotivo = "
				+ idmotivo.toString() + ";";
		List vecSalida = new ArrayList();
		try {
			vecSalida = getLista(cQuery);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhrazonesAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getRrhhrazonesOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		String cQuery = "Select raz.idrazon,raz.razon,raz.idmotivo, mot.motivo, raz.idempresa from rrhhrazones raz inner join rrhhmotivo mot on (raz.idmotivo = mot.idmotivo and raz.idempresa = mot.idempresa ) where  (UPPER(raz.RAZON) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' or UPPER(mot.motivo) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' or raz.idrazon::varchar like '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' or raz.idmotivo::varchar like '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' )  AND raz.idempresa = "
				+ idempresa.toString()
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = new ArrayList();
		try {
			vecSalida = getLista(cQuery);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhrazonesOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getRrhhrazonesPK(BigDecimal idrazon, BigDecimal idempresa)
			throws EJBException {
		String cQuery = "SELECT  idrazon,razon,idmotivo,usuarioalt,fechaalt,usuarioact,fechaact,idempresa FROM RRHHRAZONES WHERE idrazon="
				+ idrazon.toString()
				+ " AND idempresa = "
				+ idempresa.toString() + ";";
		List vecSalida = new ArrayList();
		try {
			vecSalida = getLista(cQuery);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhrazonesPK( BigDecimal idrazon)  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String rrhhrazonesDelete(BigDecimal idrazon, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM RRHHRAZONES WHERE idrazon="
				+ idrazon.toString() + " AND idempresa=" + idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM RRHHRAZONES WHERE idrazon="
						+ idrazon.toString().toString() + " AND idempresa="
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : rrhhrazonesDelete( BigDecimal idrazon, BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: rrhhrazonesDelete( BigDecimal idrazon, BigDecimal idempresa )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String rrhhrazonesCreate(String razon, BigDecimal idmotivo,
			String usuarioalt, BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idmotivo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idmotivo";
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
				String ins = "INSERT INTO RRHHRAZONES(razon, idmotivo, usuarioalt, idempresa ) VALUES (?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, razon);
				insert.setBigDecimal(2, idmotivo);
				insert.setString(3, usuarioalt);
				insert.setBigDecimal(4, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String rrhhrazonesCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error excepcion public String rrhhrazonesCreate(.....)"
					+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String rrhhrazonesCreateOrUpdate(BigDecimal idrazon, String razon,
			BigDecimal idmotivo, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idrazon == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idrazon";
		if (idmotivo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idmotivo";

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhrazones WHERE idrazonbaja = "
					+ idrazon.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE RRHHRAZONES SET razon=?, idmotivo=?, usuarioact=?, fechaact=?,idempresa=? WHERE idrazon=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, razon);
					insert.setBigDecimal(2, idmotivo);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, idempresa);
					insert.setBigDecimal(6, idrazon);
				} else {
					String ins = "INSERT INTO RRHHRAZONES(razon, idmotivo, usuarioalt, idempresa ) VALUES (?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact;
					insert.setString(1, razon);
					insert.setBigDecimal(2, idmotivo);
					insert.setString(3, usuarioalt);
					insert.setBigDecimal(4, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String rrhhrazonesCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhrazonesCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String rrhhrazonesUpdate(BigDecimal idrazon, String razon,
			BigDecimal idmotivo, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idrazon == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idrazon ";
		if (idmotivo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idmotivo ";

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhrazones WHERE idrazon = "
					+ idrazon.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE RRHHRAZONES SET razon=?, idmotivo=?, usuarioact=?, fechaact=?,idempresa=? WHERE idrazon=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, razon);
					insert.setBigDecimal(2, idmotivo);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, idempresa);
					insert.setBigDecimal(6, idrazon);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String rrhhrazonesUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log.error("Error excepcion public String rrhhrazonesUpdate(.....)"
					+ ex);
		}
		return salida;
	}

	/*
	 * Metodos para la entidad: rrhhestadosempleado Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Thu Jul 19 12:24:44 ART 2012
	 */
	// para todo (ordena por el segundo campo por defecto)
	public List getRrhhestadosempleadoAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		String cQuery = "SELECT idestadoempleado,legajo,apellido,esempleado,idmotivo,idrazon,observacion,fecha,usuarioalt,fechaalt,usuarioact,fechaact,idempresa FROM RRHHESTADOSEMPLEADO WHERE idempresa = "
				+ idempresa.toString()
				+ "  ORDER BY 2  LIMIT "
				+ limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = new ArrayList();
		try {
			vecSalida = getLista(cQuery);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhestadosempleadoAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getRrhhestadosempleadoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		String cQuery = "SELECT  idestadoempleado,legajo,apellido,esempleado,idmotivo,idrazon,observacion,fecha,usuarioalt,fechaalt,usuarioact,fechaact,idempresa FROM RRHHESTADOSEMPLEADO WHERE (UPPER(LEGAJO) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%')  AND idempresa = "
				+ idempresa.toString()
				+ " ORDER BY 2  LIMIT "
				+ limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = new ArrayList();
		try {
			vecSalida = getLista(cQuery);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhestadosempleadoOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getRrhhestadosempleadoPK(BigDecimal idestadoempleado,
			BigDecimal idempresa) throws EJBException {
		String cQuery = "SELECT  idestadoempleado,legajo,apellido,esempleado,idmotivo,idrazon,observacion,fecha,usuarioalt,fechaalt,usuarioact,fechaact,idempresa FROM RRHHESTADOSEMPLEADO WHERE idestadoempleado="
				+ idestadoempleado.toString()
				+ " AND idempresa = "
				+ idempresa.toString() + ";";
		List vecSalida = new ArrayList();
		try {
			vecSalida = getLista(cQuery);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhestadosempleadoPK( BigDecimal idestadoempleado )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String rrhhestadosempleadoDelete(BigDecimal idestadoempleado,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM RRHHESTADOSEMPLEADO WHERE idestadoempleado="
				+ idestadoempleado.toString()
				+ " AND idempresa="
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM RRHHESTADOSEMPLEADO WHERE idestadoempleado="
						+ idestadoempleado.toString().toString()
						+ " AND idempresa=" + idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : rrhhestadosempleadoDelete( BigDecimal idestadoempleado, BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: rrhhestadosempleadoDelete( BigDecimal idestadoempleado, BigDecimal idempresa )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String rrhhestadosempleadoCreate(BigDecimal legajo, String apellido,
			boolean esempleado, BigDecimal idmotivo, BigDecimal idrazon,
			String observacion, Timestamp fecha, String usuarioalt,
			BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (legajo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: legajo ";
		if (idmotivo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idmotivo ";
		if (idrazon == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idrazon ";
		/**
		 * if (fecha == null) salida =
		 * "Error: No se puede dejar sin datos (nulo) el campo: fecha ";
		 */
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechaalt ";
		// 2. sin nada desde la pagina
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {

				String ins = "INSERT INTO RRHHESTADOSEMPLEADO(legajo, apellido, esempleado, idmotivo, idrazon, observacion, fecha, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, legajo);
				insert.setString(2, apellido);
				insert.setBoolean(3, esempleado);
				insert.setBigDecimal(4, idmotivo);
				insert.setBigDecimal(5, idrazon);
				insert.setString(6, observacion);
				insert.setTimestamp(7,
						new Timestamp(System.currentTimeMillis()));
				insert.setString(8, usuarioalt);
				insert.setBigDecimal(9, idempresa);
				int n = insert.executeUpdate();

				// rrhhpersonalDelete(legajo, idempresa);

				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String rrhhestadosempleadoCreate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhestadosempleadoCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String rrhhestadosempleadoCreateOrUpdate(
			BigDecimal idestadoempleado, BigDecimal legajo, String apellido,
			boolean esempleado, BigDecimal idmotivo, BigDecimal idrazon,
			String observacion, Timestamp fecha, String usuarioact,
			BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idestadoempleado == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idestadoempleado ";
		if (legajo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: legajo ";
		if (idmotivo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idmotivo ";
		if (idrazon == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idrazon ";
		if (fecha == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fecha ";

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhestadosempleado WHERE idestadoempleado = "
					+ idestadoempleado.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE RRHHESTADOSEMPLEADO SET legajo=?, apellido=?, esempleado=?, idmotivo=?, idrazon=?, observacion=?, fecha=?, usuarioact=?, fechaact=?,idempresa=? WHERE idestadoempleado=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, legajo);
					insert.setString(2, apellido);
					insert.setBoolean(3, esempleado);
					insert.setBigDecimal(4, idmotivo);
					insert.setBigDecimal(5, idrazon);
					insert.setString(6, observacion);
					insert.setTimestamp(7, fecha);
					insert.setString(8, usuarioact);
					insert.setTimestamp(9, fechaact);
					insert.setBigDecimal(10, idempresa);
					insert.setBigDecimal(11, idestadoempleado);
				} else {
					String ins = "INSERT INTO RRHHESTADOSEMPLEADO(legajo, apellido, esempleado, idmotivo, idrazon, observacion, fecha, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact;
					insert.setBigDecimal(1, legajo);
					insert.setString(2, apellido);
					insert.setBoolean(3, esempleado);
					insert.setBigDecimal(4, idmotivo);
					insert.setBigDecimal(5, idrazon);
					insert.setString(6, observacion);
					insert.setTimestamp(7, fecha);
					insert.setString(8, usuarioalt);
					insert.setBigDecimal(9, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String rrhhestadosempleadoCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhestadosempleadoCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String rrhhestadosempleadoUpdate(BigDecimal idestadoempleado,
			BigDecimal legajo, String apellido, boolean esempleado,
			BigDecimal idmotivo, BigDecimal idrazon, String observacion,
			Timestamp fecha, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idestadoempleado == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idestadoempleado ";
		if (legajo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: legajo ";
		if (idmotivo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idmotivo ";
		if (idrazon == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idrazon ";
		if (fecha == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fecha ";

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhestadosempleado WHERE idestadoempleado = "
					+ idestadoempleado.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE RRHHESTADOSEMPLEADO SET legajo=?, apellido=?, esempleado=?, idmotivo=?, idrazon=?, observacion=?, fecha=?, usuarioact=?, fechaact=?, idempresa=? WHERE idestadoempleado=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, legajo);
					insert.setString(2, apellido);
					insert.setBoolean(3, esempleado);
					insert.setBigDecimal(4, idmotivo);
					insert.setBigDecimal(5, idrazon);
					insert.setString(6, observacion);
					insert.setTimestamp(7, fecha);
					insert.setString(8, usuarioact);
					insert.setTimestamp(9, fechaact);
					insert.setBigDecimal(10, idempresa);
					insert.setBigDecimal(11, idestadoempleado);
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
					.error("Error SQL public String rrhhestadosempleadoUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String rrhhestadosempleadoUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public List getRrhhListNoEmpleadosAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		String cQuery = "Select estados.legajo,estados.apellido,estados.fecha,estados.idmotivo,mot.motivo,estados.idrazon,raz.razon ,estados.observacion, estados.esempleado "
				+ " from rrhhestadosempleado estados "
				+ " inner join rrhhmotivo mot on (estados.idmotivo = mot.idmotivo and estados.idempresa = mot.idempresa)"
				+ " inner join rrhhrazones raz on (estados.idrazon = raz.idrazon and estados.idempresa = raz.idempresa)"
				+ " WHERE estados.idempresa = "
				+ idempresa.toString()
				+ "  ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = new ArrayList();
		try {
			vecSalida = getLista(cQuery);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhestadosempleadoPK( BigDecimal idestadoempleado )  "
							+ ex);
		}
		return vecSalida;
	}

	public List getRrhhListNoEmpleadosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		String cQuery = "Select estados.legajo,estados.apellido,estados.fecha,estados.idmotivo,mot.motivo,estados.idrazon,raz.razon ,estados.observacion, estados.esempleado "
				+ " from rrhhestadosempleado estados "
				+ " inner join rrhhmotivo mot on (estados.idmotivo = mot.idmotivo and estados.idempresa = mot.idempresa)"
				+ " inner join rrhhrazones raz on (estados.idrazon = raz.idrazon and estados.idempresa = raz.idempresa)"
				+ " Where estados.idempresa = "
				+ idempresa.toString()
				+ " and legajo::varchar like ('%"
				+ ocurrencia.trim()
				+ "%') or upper(apellido) like ('%"
				+ ocurrencia.toUpperCase().trim()
				+ "%') ORDER BY 2  LIMIT "
				+ limit + " OFFSET  " + offset + ";";
		List vecSalida = new ArrayList();
		try {
			vecSalida = getLista(cQuery);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhestadosempleadoPK( BigDecimal idestadoempleado )  "
							+ ex);
		}
		return vecSalida;
	}

	public boolean esEmpleadoActual(BigDecimal legajo, BigDecimal idempresa)
			throws EJBException {

		// es empleado actual ? SI
		boolean salida = true;
		ResultSet rsSalida = null;
		String cQuery = "Select esempleado from rrhhpersonal where legajo = "
				+ legajo.toString() + " and idempresa = "
				+ idempresa.toString();
		Statement statement;
		try {
			statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida != null && rsSalida.next())
				salida = rsSalida.getBoolean(1);

			log.info("salida: " + salida);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.info("esEmpleadoActual: " + e);
		}

		return salida;
	}

	public List empleadosAFecha(BigDecimal idempresa) throws EJBException {
		String cQuery = "Select per.legajo,per.apellido,per.domicilio"
				+ " from rrhhpersonal per " + "Where per.idempresa = "
				+ idempresa.toString() + " and per.esempleado = true";
		log.info("cQuery:" + cQuery);
		List vecSalida = new ArrayList();
		try {
			vecSalida = getLista(cQuery);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhestadosempleadoPK( BigDecimal idestadoempleado )  "
							+ ex);
		}
		return vecSalida;
	}

	// 2012-07-26 -CAMI- RRHH : LIQUIDACIÓN de SUELDOS --->

	/**
	 * public boolean evaluacionLogica(String formula) throws EJBException {
	 * boolean salida = false; // i.eval(formula); //salida =
	 * (Boolean)i.get("bar"); return salida;
	 * 
	 * }
	 */

	public float evaluacion(String formula) throws EvalError {
		float salida = 0f;
		Interpreter i = new Interpreter();
		i.eval("bar = " + formula);
		salida = new Float(i.get("bar").toString()).floatValue();
		return salida;
	}

	public String evaluarCondicion(String formula) {
		String salida = "nada";
		log.info("formula: " + formula);
		try {
			Interpreter i = new Interpreter();

			log.info("bar = " + formula);
			i.eval("bar = " + formula);
			salida = i.get("bar").toString();
		} catch (EvalError ex) {
			log.error("salida por excepcion  evaluarCondicion(String)" + ex);
		}
		return salida;
	}

	// prototipos de metodos para evaluacion (ex token ring)

	// fin de metodos de evaluacion

	public void setIdLegajo(BigDecimal idLegajo) throws EJBException {
		// this.idLegajo = idLegajo;
		// this.i.set("idLegajo", this.idLegajo);
		// a partir de aca tengo que relacion todo lo que sea posible contra un
		// legajo
		// this.i.set("sueldoMinimo", 25.35);

	}

	public BigDecimal getIdLegajo() {
		// return idLegajo;
		return new BigDecimal(1);
	}

	/*
	 * Metodos para la entidad: rrhhliq_cabe Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Thu Jul 26 14:23:24 ART 2012
	 */
	// para todo (ordena por el segundo campo por defecto)
	public List getRrhhliq_cabeAll(long limit, long offset, BigDecimal idempresa)
			throws EJBException {
		String cQuery = "SELECT  liq.idliqcabe,liq.nrorecibo,liq.legajo, per.apellido, liq.fecha, liq.anioliq,liq.mesliq,liq.nroquincena, "
				+ "liq.idcategoria,cat.categoria,liq.idlocalidadpago, loc.localidad,liq.idbancodeposito, caja.descripcion,liq.idmodalidadcontrato,mod.modalidadcontrato, "
				+ "liq.fechadeposito,liq.importesueldo,liq.totalremunerativo,liq.totalnoremunerativo, "
				+ "liq.totaldescuentos,liq.netoacobrar,liq.usuarioalt,liq.usuarioact,liq.fechaalt,liq.fechaact,liq.idempresa "
				+ "FROM RRHHLIQ_CABE liq "
				+ "inner join rrhhpersonal per on (liq.legajo = per.legajo and liq.idempresa = per.idempresa) "
				+ "inner join rrhhcategorias cat on (liq.idcategoria = cat.idcategoria and liq.idempresa= cat.idempresa) "
				+ "inner join globallocalidades loc on (liq.idlocalidadpago = loc.idlocalidad) "
				+ "inner join cajaidentificadores caja on (liq.idbancodeposito = caja.ididentificador and liq.idempresa = caja.idempresa) "
				+ "inner join rrhhmodalidadcontrato mod on (liq.idmodalidadcontrato = mod.idmodalidadcontrato and liq.idempresa = mod.idempresa) "
				+ "WHERE liq.idempresa = "
				+ idempresa.toString()
				+ "  ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = new ArrayList();
		try {
			vecSalida = getLista(cQuery);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhliq_cabeAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getRrhhliq_cabeOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		String cQuery = "SELECT  liq.idliqcabe,liq.nrorecibo,liq.legajo, per.apellido, liq.fecha, liq.anioliq,liq.mesliq,liq.nroquincena, "
				+ "liq.idcategoria,cat.categoria,liq.idlocalidadpago, loc.localidad,liq.idbancodeposito, caja.descripcion,liq.idmodalidadcontrato,mod.modalidadcontrato, "
				+ "liq.fechadeposito,liq.importesueldo,liq.totalremunerativo,liq.totalnoremunerativo, "
				+ "liq.totaldescuentos,liq.netoacobrar,liq.usuarioalt,liq.usuarioact,liq.fechaalt,liq.fechaact,liq.idempresa "
				+ "FROM RRHHLIQ_CABE liq "
				+ "inner join rrhhpersonal per on (liq.legajo = per.legajo and liq.idempresa = per.idempresa) "
				+ "inner join rrhhcategorias cat on (liq.idcategoria = cat.idcategoria and liq.idempresa= cat.idempresa) "
				+ "inner join globallocalidades loc on (liq.idlocalidadpago = loc.idlocalidad) "
				+ "inner join cajaidentificadores caja on (liq.idbancodeposito = caja.ididentificador and liq.idempresa = caja.idempresa) "
				+ "inner join rrhhmodalidadcontrato mod on (liq.idmodalidadcontrato = mod.idmodalidadcontrato and liq.idempresa = mod.idempresa) "
				+ " WHERE (liq.NRORECIBO::varchar LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' or liq.legajo::varchar like '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' )  AND liq.idempresa = "
				+ idempresa.toString()
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = new ArrayList();
		try {
			vecSalida = getLista(cQuery);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhliq_cabeOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getRrhhliq_cabePK(BigDecimal idliqcabe, BigDecimal idempresa)
			throws EJBException {
		String cQuery = "SELECT  liq.idliqcabe,liq.nrorecibo,liq.legajo, per.apellido, liq.fecha, liq.anioliq,liq.mesliq,liq.nroquincena, "
				+ "liq.idcategoria,cat.categoria,liq.idlocalidadpago, loc.localidad,liq.idbancodeposito, caja.descripcion,liq.idmodalidadcontrato,mod.modalidadcontrato, "
				+ "liq.fechadeposito,liq.importesueldo,liq.totalremunerativo,liq.totalnoremunerativo, "
				+ "liq.totaldescuentos,liq.netoacobrar,liq.usuarioalt,liq.usuarioact,liq.fechaalt,liq.fechaact,liq.idempresa "
				+ "FROM RRHHLIQ_CABE liq "
				+ "inner join rrhhpersonal per on (liq.legajo = per.legajo and liq.idempresa = per.idempresa) "
				+ "inner join rrhhcategorias cat on (liq.idcategoria = cat.idcategoria and liq.idempresa= cat.idempresa) "
				+ "inner join globallocalidades loc on (liq.idlocalidadpago = loc.idlocalidad) "
				+ "inner join cajaidentificadores caja on (liq.idbancodeposito = caja.ididentificador and liq.idempresa = caja.idempresa) "
				+ "inner join rrhhmodalidadcontrato mod on (liq.idmodalidadcontrato = mod.idmodalidadcontrato and liq.idempresa = mod.idempresa) "
				+ "WHERE liq.idliqcabe= "
				+ idliqcabe.toString()
				+ " AND liq.idempresa = " + idempresa.toString() + ";";
		List vecSalida = new ArrayList();
		try {
			vecSalida = getLista(cQuery);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhliq_cabePK( BigDecimal idliqcabe )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String rrhhliq_cabeDelete(BigDecimal idliqcabe, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM RRHHLIQ_CABE WHERE idliqcabe="
				+ idliqcabe.toString() + " AND idempresa="
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM RRHHLIQ_CABE WHERE idliqcabe="
						+ idliqcabe.toString().toString() + " AND idempresa="
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : rrhhliq_cabeDelete( BigDecimal idliqcabe, BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: rrhhliq_cabeDelete( BigDecimal idliqcabe, BigDecimal idempresa )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String rrhhliq_cabeCreate(BigDecimal nrorecibo, BigDecimal legajo,
			java.sql.Date fecha, int anioliq, int mesliq,
			BigDecimal nroquincena, BigDecimal idcategoria,
			BigDecimal idlocalidadpago, BigDecimal idbancodeposito,
			BigDecimal idmodalidadcontrato, java.sql.Date fechadeposito,
			double importesueldo, double totalremunerativo,
			double totalnoremunerativo, double totaldescuentos,
			double netoacobrar, String usuarioalt, BigDecimal idempresa)
			throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (nrorecibo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nrorecibo ";
		if (legajo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: legajo ";
		if (fecha == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fecha ";
		if (nroquincena == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nroquincena ";
		if (fechadeposito == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechadeposito ";
		/**
		 * if (importesueldo == null) salida =
		 * "Error: No se puede dejar sin datos (nulo) el campo: importesueldo ";
		 * if (totalremunerativo == null) salida =
		 * "Error: No se puede dejar sin datos (nulo) el campo: totalremunerativo "
		 * ; if (totalnoremunerativo == null) salida =
		 * "Error: No se puede dejar sin datos (nulo) el campo: totalnoremunerativo "
		 * ; if (totaldescuentos == null) salida =
		 * "Error: No se puede dejar sin datos (nulo) el campo: totaldescuentos "
		 * ; if (netoacobrar == null) salida =
		 * "Error: No se puede dejar sin datos (nulo) el campo: netoacobrar ";
		 * if (usuarioalt == null) salida =
		 * "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt "; if
		 * (fechaalt == null) salida =
		 * "Error: No se puede dejar sin datos (nulo) el campo: fechaalt ";
		 */
		// 2. sin nada desde la pagina
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO RRHHLIQ_CABE(nrorecibo, legajo, fecha, anioliq, mesliq, nroquincena, idcategoria, idlocalidadpago, idbancodeposito, idmodalidadcontrato, fechadeposito, importesueldo, totalremunerativo, totalnoremunerativo, totaldescuentos, netoacobrar, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, nrorecibo);
				insert.setBigDecimal(2, legajo);
				insert.setDate(3, fecha);
				insert.setInt(4, anioliq);
				insert.setInt(5, mesliq);
				insert.setBigDecimal(6, nroquincena);
				insert.setBigDecimal(7, idcategoria);
				insert.setBigDecimal(8, idlocalidadpago);
				insert.setBigDecimal(9, idbancodeposito);
				insert.setBigDecimal(10, idmodalidadcontrato);
				insert.setDate(11, fechadeposito);
				insert.setDouble(12, importesueldo);
				insert.setDouble(13, totalremunerativo);
				insert.setDouble(14, totalnoremunerativo);
				insert.setDouble(15, totaldescuentos);
				insert.setDouble(16, netoacobrar);
				insert.setString(17, usuarioalt);
				insert.setBigDecimal(18, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String rrhhliq_cabeCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error excepcion public String rrhhliq_cabeCreate(.....)"
					+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String rrhhliq_cabeCreateOrUpdate(BigDecimal idliqcabe,
			BigDecimal nrorecibo, BigDecimal legajo, Date fecha,
			BigDecimal anioliq, BigDecimal mesliq, BigDecimal nroquincena,
			BigDecimal idcategoria, BigDecimal idlocalidadpago,
			BigDecimal idbancodeposito, BigDecimal idmodalidadcontrato,
			Date fechadeposito, double importesueldo, double totalremunerativo,
			double totalnoremunerativo, double totaldescuentos,
			double netoacobrar, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idliqcabe == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idliqcabe ";
		if (nrorecibo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nrorecibo ";
		if (legajo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: legajo ";
		if (fecha == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fecha ";
		if (anioliq == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: anioliq ";
		if (mesliq == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: mesliq ";
		if (nroquincena == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nroquincena ";
		if (fechadeposito == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechadeposito ";
		if (importesueldo == 0d)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: importesueldo ";
		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhliq_cabe WHERE idliqcabe = "
					+ idliqcabe.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE RRHHLIQ_CABE SET nrorecibo=?, legajo=?, fecha=?, anioliq=?, mesliq=?, nroquincena=?, idcategoria=?, idlocalidadpago=?, idbancodeposito=?, idmodalidadcontrato=?, fechadeposito=?, importesueldo=?, totalremunerativo=?, totalnoremunerativo=?, totaldescuentos=?, netoacobrar=?, usuarioact=?, fechaact=?, idempresa=? WHERE idliqcabe=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, nrorecibo);
					insert.setBigDecimal(2, legajo);
					insert.setDate(3, fecha);
					insert.setBigDecimal(4, anioliq);
					insert.setBigDecimal(5, mesliq);
					insert.setBigDecimal(6, nroquincena);
					insert.setBigDecimal(7, idcategoria);
					insert.setBigDecimal(8, idlocalidadpago);
					insert.setBigDecimal(9, idbancodeposito);
					insert.setBigDecimal(10, idmodalidadcontrato);
					insert.setDate(11, fechadeposito);
					insert.setDouble(12, importesueldo);
					insert.setDouble(13, totalremunerativo);
					insert.setDouble(14, totalnoremunerativo);
					insert.setDouble(15, totaldescuentos);
					insert.setDouble(16, netoacobrar);
					insert.setString(17, usuarioact);
					insert.setTimestamp(18, fechaact);
					insert.setBigDecimal(19, idempresa);
					insert.setBigDecimal(20, idliqcabe);
				} else {
					String ins = "INSERT INTO RRHHLIQ_CABE(nrorecibo, legajo, fecha, anioliq, mesliq, nroquincena, idcategoria, idlocalidadpago, idbancodeposito, idmodalidadcontrato, fechadeposito, importesueldo, totalremunerativo, totalnoremunerativo, totaldescuentos, netoacobrar, usuarioalt,  idempresa ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact;
					insert.setBigDecimal(1, nrorecibo);
					insert.setBigDecimal(2, legajo);
					insert.setDate(3, fecha);
					insert.setBigDecimal(4, anioliq);
					insert.setBigDecimal(5, mesliq);
					insert.setBigDecimal(6, nroquincena);
					insert.setBigDecimal(7, idcategoria);
					insert.setBigDecimal(8, idlocalidadpago);
					insert.setBigDecimal(9, idbancodeposito);
					insert.setBigDecimal(10, idmodalidadcontrato);
					insert.setDate(11, fechadeposito);
					insert.setDouble(12, importesueldo);
					insert.setDouble(13, totalremunerativo);
					insert.setDouble(14, totalnoremunerativo);
					insert.setDouble(15, totaldescuentos);
					insert.setDouble(16, netoacobrar);
					insert.setString(17, usuarioalt);
					insert.setBigDecimal(18, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String rrhhliq_cabeCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhliq_cabeCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String rrhhliq_cabeUpdate(BigDecimal idliqcabe,
			BigDecimal nrorecibo, BigDecimal legajo, java.sql.Date fecha,
			BigDecimal anioliq, BigDecimal mesliq, BigDecimal nroquincena,
			BigDecimal idcategoria, BigDecimal idlocalidadpago,
			BigDecimal idbancodeposito, BigDecimal idmodalidadcontrato,
			java.sql.Date fechadeposito, double importesueldo,
			double totalremunerativo, double totalnoremunerativo,
			double totaldescuentos, double netoacobrar, String usuarioact,
			BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idliqcabe == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idliqcabe ";
		if (nrorecibo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nrorecibo ";
		if (legajo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: legajo ";
		if (fecha == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fecha ";
		if (anioliq == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: anioliq ";
		if (mesliq == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: mesliq ";
		if (nroquincena == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nroquincena ";
		if (fechadeposito == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechadeposito ";
		/**
		 * if (importesueldo == null) salida =
		 * "Error: No se puede dejar sin datos (nulo) el campo: importesueldo ";
		 * if (totalremunerativo == null) salida =
		 * "Error: No se puede dejar sin datos (nulo) el campo: totalremunerativo "
		 * ; if (totalnoremunerativo == null) salida =
		 * "Error: No se puede dejar sin datos (nulo) el campo: totalnoremunerativo "
		 * ; if (totaldescuentos == null) salida =
		 * "Error: No se puede dejar sin datos (nulo) el campo: totaldescuentos "
		 * ; if (netoacobrar == null) salida =
		 * "Error: No se puede dejar sin datos (nulo) el campo: netoacobrar ";
		 */

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhliq_cabe WHERE idliqcabe = "
					+ idliqcabe.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE RRHHLIQ_CABE SET nrorecibo=?, legajo=?, fecha=?, anioliq=?, mesliq=?, nroquincena=?, idcategoria=?, idlocalidadpago=?, idbancodeposito=?, idmodalidadcontrato=?, fechadeposito=?, importesueldo=?, totalremunerativo=?, totalnoremunerativo=?, totaldescuentos=?, netoacobrar=?, usuarioact=?, fechaact=?, idempresa=? WHERE idliqcabe=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, nrorecibo);
					insert.setBigDecimal(2, legajo);
					insert.setDate(3, fecha);
					insert.setBigDecimal(4, anioliq);
					insert.setBigDecimal(5, mesliq);
					insert.setBigDecimal(6, nroquincena);
					insert.setBigDecimal(7, idcategoria);
					insert.setBigDecimal(8, idlocalidadpago);
					insert.setBigDecimal(9, idbancodeposito);
					insert.setBigDecimal(10, idmodalidadcontrato);
					insert.setDate(11, fechadeposito);
					insert.setDouble(12, importesueldo);
					insert.setDouble(13, totalremunerativo);
					insert.setDouble(14, totalnoremunerativo);
					insert.setDouble(15, totaldescuentos);
					insert.setDouble(16, netoacobrar);
					insert.setString(17, usuarioact);
					insert.setTimestamp(18, fechaact);
					insert.setBigDecimal(19, idempresa);
					insert.setBigDecimal(20, idliqcabe);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String rrhhliq_cabeUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log.error("Error excepcion public String rrhhliq_cabeUpdate(.....)"
					+ ex);
		}
		return salida;
	}

	public List getPersonalLovList(long limit, long offset, BigDecimal idempresa)
			throws EJBException {
		String cQuery = " Select  per.legajo,per.apellido,per.domicilio, per.idlocalidadpago, loc.localidad, per.idcategoria, cat.categoria,"
				+ " per.idmodalidadcontrato , mod.modalidadcontrato,per.idbancodeposito, ban.descripcion, per.mensualoquin"
				+ " from rrhhpersonal per"
				+ " left join globallocalidades loc on (per.idlocalidadpago = loc.idlocalidad)"
				+ " left join rrhhcategorias cat on (per.idcategoria = cat.idcategoria and per.idempresa = cat.idempresa)"
				+ " left join rrhhmodalidadcontrato mod on (per.idmodalidadcontrato = mod.idmodalidadcontrato and per.idempresa = mod.idempresa)"
				+ " left join cajaidentificadores ban on (per.idbancodeposito = ban.ididentificador and per.idempresa = ban.idempresa)"
				+ " Where per.idempresa = "
				+ idempresa.toString()
				+ " and per.esempleado = true "
				+ " ORDER BY 1  LIMIT "
				+ limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = new ArrayList();
		try {
			vecSalida = getLista(cQuery);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhliq_cabeOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	public List getPersonalList(BigDecimal legajo, BigDecimal idempresa)
			throws EJBException {
		String cQuery = " Select  coalesce (idlocalidadpago,-1) as idlocalidadpago, coalesce (idcategoria,-1)as idcategoria,"
				+ "coalesce (idmodalidadcontrato ,-1) as idmodalidadcontrato,coalesce (idbancodeposito ,-1) as idbancodeposito, mensualoquin "
				+ " from rrhhpersonal"
				+ " Where idempresa = "
				+ idempresa.toString()
				+ " and esempleado = true"
				+ " and legajo = " + legajo.toString();
		log.info(cQuery);
		List vecSalida = new ArrayList();
		try {
			vecSalida = getLista(cQuery);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhliq_cabeOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	public static BigDecimal getContador(BigDecimal idcontador,
			BigDecimal idempresa, Connection conn) throws EJBException {
		BigDecimal contador = BigDecimal.valueOf(-1);
		ResultSet rsSalida = null;
		try {
			String cQuery = "SELECT valor FROM globalcontadores WHERE idcontador ="
					+ idcontador.toString()
					+ " AND idempresa = "
					+ idempresa.toString();
			log.info(cQuery);
			Statement statement = conn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				contador = rsSalida.getBigDecimal("valor");

				cQuery = "UPDATE globalcontadores SET valor = (valor + 1) "
						+ "WHERE idcontador =" + idcontador.toString()
						+ " AND idempresa=" + idempresa.toString();

				int i = statement.executeUpdate(cQuery);
				if (i == 0) {
					contador = BigDecimal.valueOf(-1);
					log.warn("getContador(" + idcontador
							+ ") - Error al actualizar proximo nro contador. ");
				}

			} else {
				log.warn("getContador(" + idcontador
						+ ") - Error al recuperar proximo nro contador. ");
			}
		} catch (SQLException sqlException) {
			log.error("getContador(" + idcontador + ")- Error SQL: "
					+ sqlException);
		} catch (Exception ex) {
			log.error("getContador(" + idcontador + ")- Salida por exception: "
					+ ex);
		}
		return contador;
	}

	public BigDecimal getIdContador(String nombre, BigDecimal idempresa)
			throws EJBException {
		BigDecimal idcontador = new BigDecimal(-1);
		ResultSet rsSalida = null;
		String cQuery = "Select idcontador from globalcontadores where contador= '"
				+ nombre + "' and idempresa = " + idempresa.toString();
		try {

			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida != null && rsSalida.next()) {
				idcontador = rsSalida.getBigDecimal("idcontador");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return idcontador;
	}

	public List getAniosLiquidacion() throws EJBException {
		String cQuery = " Select distinct anioliq from rrhhliq_cabe"
				+ " union "
				+ " Select extract (year from  current_date)"
				+ " union "
				+ " Select extract (year from  (current_date+interval '1 year')) order by 1 ";
		List vecSalida = new ArrayList();
		try {
			vecSalida = getLista(cQuery);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getAniosLiquidacion(BigDecimal idempresa) "
							+ ex);
		}
		return vecSalida;
	}

	public List getLegajos(BigDecimal desdeLegajo, BigDecimal hastaLegajo,
			BigDecimal idempresa) throws EJBException {
		String cQuery = " Select legajo from rrhhpersonal where legajo between "
				+ desdeLegajo.toString()
				+ " and "
				+ hastaLegajo.toString()
				+ " and idempresa =" + idempresa.toString() + " order by 1 ";
		log.info(cQuery);
		List listLegajos = new ArrayList();
		listLegajos = getLista(cQuery);
		return listLegajos;

	}

	public List getConceptosXLegajo(BigDecimal legajo, BigDecimal idempresa)
			throws EJBException {

		String cQuery = " Select con.concepto, con.valor, con.formula from rrhhconceptosxpersonal cxp"
				+ " inner join rrhhconceptos con on (cxp.idconcepto = con.idconcepto and cxp.idempresa = con.idempresa)"
				+ " where cxp.legajo = "
				+ legajo.toString()
				+ " and cxp.idempresa = " + idempresa.toString();
		List listLegajos = new ArrayList();
		listLegajos = getLista(cQuery);
		return listLegajos;
	}

	public String getFormula(String descripcion, BigDecimal idempresa)
			throws EJBException, SQLException {
		String formula = "";
		ResultSet rsSalida = null;
		String cQuery = "Select sql from rrhhformulas where upper(formula) = '"
				+ descripcion.trim().toUpperCase() + "' and  idempresa = "
				+ idempresa.toString();
		log.info(cQuery);
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida != null && rsSalida.next()) {
				formula = rsSalida.getString("sql");
			}
		} catch (Exception e) {
			log.error("getFormula()");
		}
		return formula;
	}

	public String ejecutarFormula(String cQuery, BigDecimal legajo,
			BigDecimal idempresa) throws EJBException, SQLException {
		ResultSet rsSalida = null;

		cQuery = cQuery.replace("#legajo#", legajo.toString());
		cQuery = cQuery.replace("#idempresa#", idempresa.toString());
		String salida = "";
		log.info(cQuery);
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida != null && rsSalida.next()) {
				salida = rsSalida.getString(1);
			}
		} catch (Exception e) {
			log.error("ejecutarFormula()");
		}
		return salida;
	}

	public String ejecutarFormula(String cQuery) throws EJBException,
			SQLException {
		ResultSet rsSalida = null;
		String salida = "";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida != null && rsSalida.next()) {
				salida = rsSalida.getString(1);
			}
		} catch (Exception e) {
			log.error("ejecutarFormula()");
		}
		return salida;
	}

	public float getValor(String descripcion, BigDecimal legajo,
			BigDecimal idempresa) throws EJBException {
		float valor = 0f;
		ResultSet rsSalida = null;
		String cQuery = "Select valor from rrhhconceptos con inner join "
				+ " rrhhconceptosxpersonal cxp on (con.idconcepto = cxp.idconcepto and con.idempresa = cxp.idempresa)"
				+ " where upper(con.concepto) like '%"
				+ descripcion.toUpperCase().trim() + "%' and cxp.legajo = "
				+ legajo.toString() + " and cxp.idempresa = "
				+ idempresa.toString();
		log.info(cQuery);
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida != null && rsSalida.next()) {
				valor = rsSalida.getFloat("valor");
			}
		} catch (Exception e) {
			log.error("getValor()");
		}
		return valor;

	}

	public String LiquidacionSueldoXLegajo(BigDecimal desdeLegajo,
			BigDecimal hastaLegajo, String usuarioalt, BigDecimal idempresa)
			throws EJBException {
		String salida = "NOOK";
		boolean continuar = false;
		String mensualoquin = "";
		Calendar cal = new GregorianCalendar();
		java.util.Date utilDate = new java.util.Date(); // fecha actual
		long lnMilisegundos = utilDate.getTime();
		java.sql.Date fecha = new java.sql.Date(lnMilisegundos);
		BigDecimal legajo = new BigDecimal(-1);
		BigDecimal nrorecibo = new BigDecimal(-1);
		BigDecimal nroquincena = new BigDecimal(1);
		int anioliq = Calendar.getInstance().get(Calendar.YEAR);
		int mesliq = Calendar.getInstance().get(Calendar.MONTH);
		BigDecimal idcategoria = new BigDecimal(-1);
		BigDecimal idlocalidadpago = new BigDecimal(-1);
		BigDecimal idmodalidadcontrato = new BigDecimal(-1);
		BigDecimal idbancodeposito = new BigDecimal(-1);
		BigDecimal idcontador = new BigDecimal(-1);
		float antiguedad = 0f;
		float presentismo = 0f;
		float totRemunerativo = 0f;
		float totNoRemunerativo = 0f;
		float totDescuento = 0f;
		float presentismoAcuerdo = 0f;
		float junioOnce = 0f;
		String formula = "";
		int cantidad = 0;
		try {
			Iterator iterlegajos = getLegajos(desdeLegajo, hastaLegajo,
					idempresa).iterator();
			// mientras haya legajos que iterar
			while (iterlegajos.hasNext()) {
				// generamos un numero de recibo nuevo y unico
				idcontador = getIdContador("nrorecibo", idempresa);
				nrorecibo = getContador(idcontador, idempresa, dbconn);
				String[] datosLegajo = (String[]) iterlegajos.next();
				legajo = new BigDecimal(datosLegajo[0]);
				Iterator iterPersonal = getPersonalList(legajo, idempresa)
						.iterator();
				while (iterPersonal.hasNext()) {
					String[] datosPersonal = (String[]) iterPersonal.next();
					idlocalidadpago = new BigDecimal(datosPersonal[0]);
					idcategoria = new BigDecimal(datosPersonal[1]);
					idmodalidadcontrato = new BigDecimal(datosPersonal[2]);
					idbancodeposito = new BigDecimal(datosPersonal[3]);
					mensualoquin = datosPersonal[4];
				}

				if (mensualoquin.equalsIgnoreCase("M")) {
					continuar = true;
					nroquincena = new BigDecimal(2);
					// vamos a traer todas las descripciones y los valores de
					// los conceptos asociados al legajo que sean remunerativos

					formula = getFormula("SueldoBasico", idempresa);
					formula += " Where idcategoria = " + idcategoria.toString()
							+ " and idempresa=" + idempresa.toString();
					formula = ejecutarFormula(formula);
					float sueldo = evaluacion(formula);
					/**
					 * antiguedad = getValor("antiguedad", legajo, idempresa);
					 * antiguedad = sueldo * antiguedad / 100; antiguedad =
					 * Float.parseFloat(GeneralBean
					 * .getNumeroFormateado(antiguedad, 10, 2));
					 * log.info("antiguedad: " + antiguedad); presentismo =
					 * getValor("presentismo", legajo, idempresa); presentismo =
					 * sue*ldo * presentismo / 100; presentismo =
					 * Float.parseFloat(GeneralBean
					 * .getNumeroFormateado(presentismo, 10, 2));
					 * log.info("presentismo: " + presentismo);
					 */
					GeneralBean g = new GeneralBean();
					totRemunerativo = sueldo + antiguedad + presentismo;
					totRemunerativo = Float.parseFloat(g
							.getNumeroFormateado(totRemunerativo, 10, 2));
					log.info("totRemunerativo: " + totRemunerativo);
					// Calculo total de haberes remunerativos
					if (totRemunerativo > 0f) {
						continuar = true;
					} else {
						continuar = false;
					}
					if (continuar) {

						// Calculo de haberes no remunerativos
						// acuerdos mes año
						
						junioOnce = getValor("acuerdo junio 2011", legajo,
								idempresa);
						junioOnce = sueldo * junioOnce;
						junioOnce = Float.parseFloat(g
								.getNumeroFormateado(junioOnce, 10, 2));
						// presentismoAcuerdo = SUM(acuerdo s)* 8,33/100
						totRemunerativo = presentismoAcuerdo + junioOnce;
						if (totNoRemunerativo > 0f) {
							continuar = true;
						} else {
							continuar = false;

							/*
							 * if (continuar) {// Calculo total de haberes no //
							 * remunerativos{ formula =
							 * getFormula("basicocategoria".trim()
							 * .toUpperCase(), idempresa); float remunerativo =
							 * evaluacion(ejecutarFormula( formula, legajo,
							 * idempresa)); // Llamamos a insertar cabecera if
							 * (remunerativo > 0f) { continuar = true; } else {
							 * continuar = false; }
							 * 
							 * if (continuar) {
							 * 
							 * formula = getFormula("jubilacion".trim()
							 * .toUpperCase(), idempresa); float retenciones =
							 * evaluacion(ejecutarFormula( formula, legajo,
							 * idempresa)); if (retenciones > 0f) { continuar =
							 * true; } else { continuar = false; } if
							 * (continuar) {
							 * 
							 * salida = rrhhliq_cabeCreate(nrorecibo, legajo,
							 * fecha, anioliq, mesliq, nroquincena, idcategoria,
							 * idlocalidadpago, idbancodeposito,
							 * idmodalidadcontrato, fecha, importe,
							 * remunerativo, noremunerativo, retenciones,
							 * importe + 1000, usuarioalt, idempresa); if
							 * (!salida.equalsIgnoreCase("NOOK")) { cantidad++;
							 * } } } }
							 */
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("LiquidacionSueldoXLegajo (...)" + e);
		}

		return salida;

	}

	public int getCalculoAntiguedad(BigDecimal legajo, BigDecimal idempresa)
			throws EJBException {
		int salida = -1;
		ResultSet rsSalida = null;
		String cQuery = "Select fechahasta,fechadesde from rrhhestadosempelado where legajo = "
				+ legajo + " and idempresa = " + idempresa;
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida != null && rsSalida.next()) {

			}
		} catch (Exception e) {
			log.error("getCalculoAntiguedad(...)");
		}
		return salida;

	}

	public List ObtenerPersonalXPeriodoAll(BigDecimal anioliq,
			BigDecimal mesliq, BigDecimal idempresa,long limit, long offset) throws EJBException {
		String cQuery = "Select liq.legajo, personal.apellido, liq.netoacobrar from rrhhliq_cabe liq"
				+ " inner join rrhhpersonal personal on (personal.legajo = liq.legajo and personal.idempresa = liq.idempresa)"
				+ " where liq.anioliq = "
				+ anioliq.toString()
				+ " and liq.mesliq = "
				+ mesliq.toString()
				+ " and liq.idempresa = "
				+ idempresa.toString()
				+ "  order by legajo asc LIMIT "
				+ limit
				+ " OFFSET  " + offset + ";";
		List listLegajos = new ArrayList();
		listLegajos = getLista(cQuery);
		return listLegajos;
	}

	public List ObtenerPersonalXPeriodoOcu(BigDecimal anioliq,
			BigDecimal mesliq, long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		String cQuery = "Select liq.legajo, personal.apellido, liq.netoacobrar from rrhhliq_cabe liq"
				+ " inner join rrhhpersonal personal on (personal.legajo = liq.legajo and personal.idempresa = liq.idempresa)"
				+ " where liq.anioliq = "
				+ anioliq.toString()
				+ " and liq.mesliq = "
				+ mesliq.toString()
				+ " and liq.idempresa = "
				+ idempresa.toString()
				+ " and upper(personal.apellido) like ('%"
				+ ocurrencia.trim().toUpperCase()
				+ "%')"
				+ "  order by legajo asc LIMIT "
				+ limit
				+ " OFFSET  " + offset + ";";
		List listLegajos = new ArrayList();
		listLegajos = getLista(cQuery);
		return listLegajos;
	}
	/**
	 * 
	 * 
	 * */
	public boolean liquidacionSueldoEmpleados(String[] args,
			BigDecimal idempresa) throws EJBException {
		float resultadoFinal = 0f;
		BigDecimal ANIO = new BigDecimal(-1);
		BigDecimal MES = new BigDecimal(-1);
		boolean salida = false;
		try {

			// armo periodos
			if (args.length == 2) {
				ANIO = new BigDecimal(args[0]);
				MES = new BigDecimal(args[1]);
			} else {
				ANIO = getCurAnio();
				MES = getCurMes();
			}
			// log.info("Inicia liquidacion de vendedores para el periodo: "+
			// ANIO + "/" + MES);
			// paso 1: Eliminar periodos que ya fueron procesados
			delPeriodoLiquidacion(dbconn, ANIO, MES, idempresa);
			// paso 2: Recupero la nomina de Personal que voy a Liquidar.
			ResultSet rsPersonal = getRrhhpersonalAll(dbconn, idempresa);
			while (rsPersonal.next()) {
				log.info("Procesando Vendedor: "
						+ rsPersonal.getString("legajo") + " "
						+ rsPersonal.getString("apellido"));
				BigDecimal contadorRecibo = getIdContador("nrorecibo",
						idempresa);
				BigDecimal nrorecibo = getContador(contadorRecibo, idempresa,
						dbconn);

				// grabo cabecera de liquidacion
				/*
                 
                */
				rrhhliq_cabeCreate(dbconn, nrorecibo, rsPersonal
						.getBigDecimal("legajo"), ANIO.intValue(), MES
						.intValue(), new BigDecimal(0), rsPersonal
						.getBigDecimal("idcategoria"), rsPersonal
						.getBigDecimal("idlocalidadpago"), rsPersonal
						.getBigDecimal("idbancodeposito"), rsPersonal
						.getBigDecimal("idmodalidadcontrato"), 0, 0, 0, 0, 0,
						"LiquidacionPersonal", idempresa);
				// -- todo: En cabecera grabar totales una vez que haga toda la
				// movida del detalle.
				BigDecimal idcabecera = getCurIdCabecera();
				// --> todo: grabar detalle a partir de la creacion de conceptos
				// claros.
				// --> Quizas convenga hacer la liquidacion de cartera.

				// --> recupero los conceptos asociados a cada uno de los
				// legajos
				ResultSet rsConceptosLegajo = getRrhhConceptos4Personal(dbconn,
						rsPersonal.getBigDecimal("legajo"), idempresa);
				while (rsConceptosLegajo.next()) {
					BigDecimal idconcepto = rsConceptosLegajo
							.getBigDecimal("idconcepto");
					ResultSet rsConcepto = getRrhhConceptosPK(dbconn,
							idconcepto, idempresa);
					while (rsConcepto.next()) {
						String formulaConcepto = rsConcepto
								.getString("formula");
						formulaConcepto = conceptoExecuteToString(
								formulaConcepto, idempresa, idconcepto, ANIO,
								MES);
						// a esta instancia llego con formula concepto lista
						// para ser evaluada
						resultadoFinal = evaluacion(formulaConcepto);
						/*
						 * 1;"CONCEPTO REMUNERATIVO"
						 * 2;"CONCEPTO NO REMUNERATIVO" 3;"DESCUENTOS"
						 * 4;"CANTIDADES"
						 */
						double totalRemunerativo = 0d;
						double totalNoRemunerativo = 0d;
						double totalDescuentos = 0d;
						double totalCantidad = 1d; // @todo: ver si lo puedo
													// aplicar a la cantidad

						BigDecimal idtipoconcepto = rsConcepto
								.getBigDecimal("idtipoconcepto");
						if (idtipoconcepto.longValue() == 1) {
							totalRemunerativo = resultadoFinal;
						}
						if (idtipoconcepto.longValue() == 2) {
							totalNoRemunerativo = resultadoFinal;

						}
						if (idtipoconcepto.longValue() == 3) {
							totalDescuentos = resultadoFinal;
						}
						if (idtipoconcepto.longValue() == 4) {
							totalCantidad = resultadoFinal;
						}

						rrhhliq_detaCreate(
								dbconn,
								idcabecera,
								idconcepto,
								rsConcepto.getString("imprime"),
								rsConcepto.getBigDecimal("idtipoconcepto"),
								rsConcepto
										.getBigDecimal("idtipocantidadconcepto"),
								totalCantidad, totalRemunerativo,
								totalNoRemunerativo, totalDescuentos,
								"LiquidacionPersonal", idempresa);
					}
					rsConcepto = null;

				}
				rrhhliq_cabeUpdateTotales(dbconn, idcabecera);
				rsConceptosLegajo = null;
				salida = true;
			}
			
		} catch (Exception ex) {
			log
					.error("Fallo de proceso principal de liquidacion de vendedores "
							+ ex);
			salida = false;
		}
		return salida;
	}

	public static void delPeriodoLiquidacion(Connection conn,
			BigDecimal anioliquidacion, BigDecimal mesliquidacion,
			BigDecimal idempresa) {
		String sqlDelete1 = "delete from RRHHLIQ_CABE where anioliq = ? and mesliq = ? and idempresa = ?; ";
		// log.info("eliminando periodo previo");
		try {
			PreparedStatement delete = conn.prepareStatement(sqlDelete1);
			delete.setBigDecimal(1, anioliquidacion);
			delete.setBigDecimal(2, mesliquidacion);
			delete.setBigDecimal(3, idempresa);
			int x = delete.executeUpdate();
		} catch (SQLException ex) {
			log.error("delPeriodoLiquidacion(Connection conn) " + ex);
		}
	}

	public static ResultSet getRrhhpersonalAll(Connection conn,
			BigDecimal idempresa) {
		ResultSet rsSalida = null;
		try {
			String cQuery = "Select *  FROM RRHHPERSONAL Where idempresa = "
					+ idempresa.toString() + "  and fbaja is null "
					+ " order by 1;";

			Statement statement = conn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
		} catch (SQLException sqlException) {
			log.error("Error SQL en el metodo : getRrhhpersonalAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhpersonalAll()  "
							+ ex);
		}
		return rsSalida;
	}

	public static ResultSet getRrhhConceptos4Personal(Connection conn,
			BigDecimal legajo, BigDecimal idempresa) {
		ResultSet rsSalida = null;
		ResultSet preSalida = null;
		BigDecimal idlista = new BigDecimal(-1);
		try {
			// lista
			// Select idconceptos from rrhhlistasconceptos where idlista =
			String preQuery = "Select idlista from rrhhpersonal where legajo = "
					+ legajo.toString();
			Statement prestatement = conn.createStatement();
			preSalida = prestatement.executeQuery(preQuery);
			while (preSalida.next() && preSalida != null) {
				idlista = preSalida.getBigDecimal("idlista");
			}
			String cQuery = "Select idconcepto  FROM rrhhlistaconceptos Where idlista = "
					+ idlista.toString()
					+ " and idempresa = "
					+ idempresa.toString() + " order by 1 ;";
			Statement statement = conn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
		} catch (SQLException sqlException) {
			log.error("Error SQL en el metodo : getRrhhConceptos4Personal() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhConceptos4Personal()  "
							+ ex);
		}
		return rsSalida;
	}

	/*
	 * 
	 * 
	 * *
	 */
	public static ResultSet getRrhhConceptosPK(Connection conn,
			BigDecimal idconcepto, BigDecimal idempresa) {
		ResultSet rsSalida = null;
		try {
			String cQuery = "Select *  " + " FROM " + " rrhhconceptos  "
					+ "Where idconcepto = " + idconcepto.toString()
					+ " and idempresa = " + idempresa.toString()
					+ " order by idtipoconcepto asc,idconcepto asc";
			Statement statement = conn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
		} catch (SQLException sqlException) {
			log.error("Error SQL en el metodo : getRrhhConceptosPK() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhConceptosPK()  "
							+ ex);
		}
		return rsSalida;
	}

	public static String getRrhhFormulaPK(String formula, BigDecimal idempresa) {
		ResultSet rsSalida = null;
		String salida = "";
		try {
			String cQuery = "Select *  " + " FROM " + " rrhhformulas  "
					+ "Where formula = '" + formula + "' and idempresa = "
					+ idempresa.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				salida = rsSalida.getString("sql");
			}
		} catch (SQLException sqlException) {
			log.error("Error SQL en el metodo : getRrhhFormulaPK() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhFormulaPK()  "
							+ ex);
		}
		return salida;
	}

	public static String rrhhliq_cabeCreate(
			Connection conn,
			BigDecimal nrorecibo,
			BigDecimal legajo,
			// java.sql.Date fecha,
			int anioliq, int mesliq, BigDecimal nroquincena,
			BigDecimal idcategoria, BigDecimal idlocalidadpago,
			BigDecimal idbancodeposito,
			BigDecimal idmodalidadcontrato,
			// java.sql.Date fechadeposito,
			double importesueldo, double totalremunerativo,
			double totalnoremunerativo, double totaldescuentos,
			double netoacobrar, String usuarioalt, BigDecimal idempresa) {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos

		if (nrorecibo == null) {
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nrorecibo ";
		}
		if (legajo == null) {
			salida = "Error: No se puede dejar sin datos (nulo) el campo: legajo ";
		}
		/*
		 * if (fecha == null) { salida =
		 * "Error: No se puede dejar sin datos (nulo) el campo: fecha "; }
		 */
		if (nroquincena == null) {
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nroquincena ";
		}
		/*
		 * if (fechadeposito == null) { salida =
		 * "Error: No se puede dejar sin datos (nulo) el campo: fechadeposito ";
		 * }
		 */
		/**
		 * if (importesueldo == null) salida = "Error: No se puede dejar sin
		 * datos (nulo) el campo: importesueldo "; if (totalremunerativo ==
		 * null) salida = "Error: No se puede dejar sin datos (nulo) el campo:
		 * totalremunerativo " ; if (totalnoremunerativo == null) salida =
		 * "Error: No se puede dejar sin datos (nulo) el campo:
		 * totalnoremunerativo " ; if (totaldescuentos == null) salida = "Error:
		 * No se puede dejar sin datos (nulo) el campo: totaldescuentos " ; if
		 * (netoacobrar == null) salida = "Error: No se puede dejar sin datos
		 * (nulo) el campo: netoacobrar "; if (usuarioalt == null) salida =
		 * "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt "; if
		 * (fechaalt == null) salida = "Error: No se puede dejar sin datos
		 * (nulo) el campo: fechaalt ";
		 */
		// 2. sin nada desde la pagina
		if (usuarioalt.equalsIgnoreCase("")) {
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";
		}

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK")) {
			bError = false;
		}
		try {
			if (!bError) {
				String ins = "INSERT INTO RRHHLIQ_CABE(nrorecibo, legajo, fecha, anioliq, mesliq, nroquincena, idcategoria, idlocalidadpago, idbancodeposito, idmodalidadcontrato, fechadeposito, importesueldo, totalremunerativo, totalnoremunerativo, totaldescuentos, netoacobrar, usuarioalt, idempresa ) VALUES (?, ?, current_date, ?, ?, ?, ?, ?, ?, ?, current_date, ?, ?, ?, ?, ?, ?,?)";
				PreparedStatement insert = conn.prepareStatement(ins);
				// seteo de campos:

				insert.setBigDecimal(1, nrorecibo);
				insert.setBigDecimal(2, legajo);
				// insert.setDate(3, fecha);
				insert.setInt(3, anioliq);
				insert.setInt(4, mesliq);
				insert.setBigDecimal(5, nroquincena);
				insert.setBigDecimal(6, idcategoria);
				insert.setBigDecimal(7, idlocalidadpago);
				insert.setBigDecimal(8, idbancodeposito);
				insert.setBigDecimal(9, idmodalidadcontrato);
				// insert.setDate(11, fechadeposito);
				insert.setDouble(10, importesueldo);
				insert.setDouble(11, totalremunerativo);
				insert.setDouble(12, totalnoremunerativo);
				insert.setDouble(13, totaldescuentos);
				insert.setDouble(14, netoacobrar);
				insert.setString(15, usuarioalt);
				insert.setBigDecimal(16, idempresa);

				/**
				 * insert.setBigDecimal(1, nrorecibo); insert.setBigDecimal(2,
				 * legajo); // insert.setDate(3, fecha); insert.setInt(3,
				 * anioliq); insert.setInt(4, mesliq); insert.setBigDecimal(5,
				 * nroquincena); insert.setBigDecimal(6, idcategoria);
				 * insert.setBigDecimal(7, idlocalidadpago);
				 * insert.setBigDecimal(8, idbancodeposito);
				 * insert.setBigDecimal(9, idmodalidadcontrato); //
				 * insert.setDate(10, fechadeposito); insert.setDouble(10,
				 * importesueldo); insert.setDouble(11, totalremunerativo);
				 * insert.setDouble(12, totalnoremunerativo);
				 * insert.setDouble(13, totaldescuentos); insert.setDouble(14,
				 * netoacobrar); insert.setString(15, usuarioalt);
				 * insert.setBigDecimal(16, idempresa);
				 */
				int n = insert.executeUpdate();
				if (n == 1) {
					salida = "Alta Correcta";
				}
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String rrhhliq_cabeCreate(.....) "
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error excepcion public String rrhhliq_cabeCreate(.....)"
					+ ex);
		}
		return salida;
	}

	public static String rrhhliq_cabeUpdateTotales(Connection conn,
			BigDecimal idliqcabe) {
		String salida = "NOOK";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK")) {
			bError = false;
		}
		try {

			String cQuery = ""
					+ " select  "
					+ " sum(valorremunerativo) as valorremunerativo, "
					+ " sum(valornoremunerativo) as valornoremunerativo, "
					+ " sum(valordescuentos) as valordescuentos, "
					+ " (sum(valorremunerativo) + sum(valornoremunerativo)) - sum(valordescuentos) as netoacobrar  "
					+ " from  " + " rrhhliq_deta " + " where  "
					+ " idliqcabe = " + idliqcabe.toString();
			Statement statement = conn.createStatement();
			ResultSet rsSalida = statement.executeQuery(cQuery);
			double totalremunerativo = 0d;
			double totalnoremunerativo = 0d;
			double totaldescuentos = 0d;
			double netoacobrar = 0d;
			if (rsSalida.next()) {
				totalremunerativo = rsSalida.getDouble("valorremunerativo");
				totalnoremunerativo = rsSalida.getDouble("valornoremunerativo");
				totaldescuentos = rsSalida.getDouble("valordescuentos");
				netoacobrar = rsSalida.getDouble("netoacobrar");
			}

			if (!bError) {
				String ins = "UPDATE RRHHLIQ_CABE SET totalremunerativo=?, totalnoremunerativo=?, totaldescuentos=?, netoacobrar=? where  idliqcabe =? ";
				PreparedStatement insert = conn.prepareStatement(ins);
				// seteo de campos:
				insert.setDouble(1, totalremunerativo);
				insert.setDouble(2, totalnoremunerativo);
				insert.setDouble(3, totaldescuentos);
				insert.setDouble(4, netoacobrar);
				insert.setBigDecimal(5, idliqcabe);
				int n = insert.executeUpdate();
				if (n == 1) {
					salida = "Modificacion Correcta";
				}
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String rrhhliq_cabeUpdateTotales(.....) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhliq_cabeUpdateTotales(.....)"
							+ ex);
		}
		return salida;
	}

	public static String rrhhliq_detaCreate(Connection conn,
			BigDecimal idliqcabe, BigDecimal idconcepto, String imprime,
			BigDecimal idtipoconcepto, BigDecimal idtipocantidadconcepto,
			double cantidad, double valorremunerativo,
			double valornoremunerativo, double valordescuentos,
			String usuarioalt, BigDecimal idempresa) {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idliqcabe == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idliqcabe ";
		if (idconcepto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idconcepto ";
		if (imprime == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: imprime ";
		if (idtipoconcepto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipoconcepto ";
		if (idtipocantidadconcepto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipocantidadconcepto ";
		if (cantidad == 0d)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cantidad ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (imprime.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: imprime ";
		
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO RRHHLIQ_DETA(idliqcabe, idconcepto, imprime, idtipoconcepto, idtipocantidadconcepto, cantidad, valorremunerativo, valornoremunerativo, valordescuentos, usuarioalt, idempresa) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = conn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, idliqcabe);
				insert.setBigDecimal(2, idconcepto);
				insert.setString(3, imprime);
				insert.setBigDecimal(4, idtipoconcepto);
				insert.setBigDecimal(5, idtipocantidadconcepto);
				insert.setDouble(6, cantidad);
				insert.setDouble(7, valorremunerativo);
				insert.setDouble(8, valornoremunerativo);
				insert.setDouble(9, valordescuentos);
				insert.setString(10, usuarioalt);
				insert.setBigDecimal(11, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String rrhhliq_detaCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error excepcion public String rrhhliq_detaCreate(.....)"
					+ ex);
		}
		return salida;
	}

	public static String conceptoExecuteToString(String concepto,
			BigDecimal idempresa, BigDecimal legajo, BigDecimal anio,
			BigDecimal mes) {

		try {

			// En esta instancia la variable concepto tiene el nombre de la
			// formula.
			// En setformulas.... reemplazo todos los ## con los parametros
			// correspondientes.
			// Teoricamente, concepto,tendria que estar igual,ya que es el
			// nombre de la formula.
			concepto = setFormulasWithVariables(concepto, legajo, anio, mes,
					idempresa);
			Statement statement = dbconn.createStatement();
			ResultSet rsSalida = statement
					.executeQuery("select * from rrhhformulas where idempresa = 1 order by 1 ");
			while (rsSalida.next()) {
				String formula = rsSalida.getString("formula");
				// Si en concepto entrecuenta la cadena formula entra.
				if (concepto.indexOf(formula) != -1) {
					String valor = getRrhhFormulaPK(formula, idempresa);
					valor = formulaExecuteToString(valor);
					concepto = concepto.replace(formula, valor);

				}
			}
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: conceptoExecuteToString( String concepto)  "
							+ ex);
		}
		return concepto;
	}

	public static String formulaExecuteToString(String formula) {
		String salida = "";
		try {
			formula = setFormulasWithVariables(formula);
			Statement statement = dbconn.createStatement();
			ResultSet rsSalida = statement.executeQuery(formula);
			if (rsSalida.next()) {
				salida = rsSalida.getString(1);
			}
		} catch (SQLException sqlException) {
			log.error("Error SQL en el metodo : formulaExecuteToString() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: formulaExecuteToString( String formula)  "
							+ ex);
		}
		return salida;
	}

	private static String setFormulasWithVariables(String formula) {
		String salida = "";
		salida = formula;
		try {
			String legajo = evaluacionLogica("legajo");
			salida = salida.replace("#legajo#", "1");
			salida = salida.replace("#idempresa#", "1");
			salida = salida.replace("#anio#", "2011");
			salida = salida.replace("#mes#", "05");
		} catch (Exception ex) {
			log.error("Error en setFormulasWithVariables :" + ex);
		}
		return salida;

	}

	public static String setFormulasWithVariables(String formula,
			BigDecimal legajo, BigDecimal anio, BigDecimal mes,
			BigDecimal idempresa) {
		String salida = "";
		salida = formula;
		try {
			salida = salida.replace("#legajo#", legajo.toString());
			salida = salida.replace("#idempresa#", idempresa.toString());
			salida = salida.replace("#anio#", anio.toString());
			salida = salida.replace("#mes#", mes.toString());

		} catch (Exception ex) {
			log.error("Error en setFormulasWithVariables :" + ex);
		}
		return salida;

	}

	public static void setLegajo(BigDecimal legajo) throws EvalError {
		Interpreter i = new Interpreter();
		i.set("legajo", legajo);
	}

	public static void setIdempresa(BigDecimal idempresa) throws EvalError {
		Interpreter i = new Interpreter();
		i.set("idempresa", idempresa);
	}

	public static void setAnio_liquidacion(BigDecimal anio_liquidacion)
			throws EvalError {
		Interpreter i = new Interpreter();
		i.set("anio_liquidacion", anio_liquidacion);
	}

	public static void setMes_liquidacion(BigDecimal mes_liquidacion)
			throws EvalError {
		Interpreter i = new Interpreter();
		i.set("mes_liquidacion", mes_liquidacion);
	}

	public static BigDecimal getCurAnio() {
		BigDecimal salida = new BigDecimal(0);
		try {

			String cQuery = "select date_part('year', current_date) as anio; ";
			Statement statement = dbconn.createStatement();
			ResultSet rssalida = statement.executeQuery(cQuery);
			while (rssalida.next()) {
				salida = rssalida.getBigDecimal("anio");
			}
		} catch (Exception ex) {
			log.error("getCurAnio(Connection conn) / Error: " + ex);
		}
		return salida;
	}

	public static java.sql.Date getCurFecha() {
		java.sql.Date salida = null;
		try {
			String cQuery = "select current_date::date as fecha; ";
			Statement statement = dbconn.createStatement();
			ResultSet rssalida = statement.executeQuery(cQuery);
			while (rssalida.next()) {
				salida = rssalida.getDate("fecha");
			}
		} catch (Exception ex) {
			log.error("getCurFecha(Connection conn) / Error: " + ex);
		}
		return salida;
	}

	public static BigDecimal getCurMes() {
		BigDecimal salida = new BigDecimal(0);
		try {
			String cQuery = "select date_part('month', current_date) as mes; ";
			Statement statement = dbconn.createStatement();
			ResultSet rssalida = statement.executeQuery(cQuery);
			while (rssalida.next()) {
				salida = rssalida.getBigDecimal("mes");
			}
		} catch (Exception ex) {
			log.error("getCurMes(Connection conn) / Error: " + ex);
		}
		return salida;
	}

	public static BigDecimal getCurIdCabecera() {
		ResultSet rsSalida = null;
		BigDecimal salida = new BigDecimal(0);
		try {
			String cQuery = " select currval('seq_rrhhliq_cabe'::regclass) as idcabecera ";
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				salida = rsSalida.getBigDecimal("idcabecera");
			}
		} catch (SQLException sqlException) {
			log.error("Error SQL en el metodo : getCurIdCabecera() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCurIdCabecera()  "
							+ ex);
		}
		return salida;
	}

	public boolean pruebaDeEvaluacion(String formulaConcepto,
			BigDecimal idempresa) throws EJBException {
		boolean salida = false;
		// No puede haber formulas que llamen a otras formular porque seria tan
		// recursivo como que un concepto llame a otro concepto.
		// Es preferible que se trabaje con la formula directa
		formulaConcepto = conceptoExecuteToString(formulaConcepto, idempresa);
		try {
			float resul = evaluacion(formulaConcepto);
			if (resul > 0) {
				salida = true;
			}

		} catch (Exception ex) {
			log.error("prueba de Evaluacion : " + ex);
		}
		return salida;

	}

	public String conceptoExecuteToString(String concepto, BigDecimal idempresa)
			throws EJBException {

		try {
			BigDecimal anio = new BigDecimal(2012);
			BigDecimal mes = new BigDecimal(12);
			BigDecimal legajo = new BigDecimal(1);
			concepto = setFormulasWithVariables(concepto, legajo, idempresa,
					anio, mes);
			Statement statement = dbconn.createStatement();
			ResultSet rsSalida = statement
					.executeQuery("select * from rrhhformulas where idempresa = "
							+ idempresa.toString());
			while (rsSalida.next()) {
				String formula = rsSalida.getString("formula");

				if (concepto.indexOf(formula) != -1) {
					String valor = getRrhhFormulaPK(formula, idempresa);
					valor = formulaExecuteToString(valor);
					concepto = concepto.replace(formula, valor);
				}
			}
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: conceptoExecuteToString( String concepto)  "
							+ ex);
		}
		return concepto;
	}

	/*
	 * Metodos para la entidad: rrhhlista Copyrigth(r) sysWarp S.R.L. Fecha de
	 * creacion: Mon Nov 12 11:08:23 ART 2012
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getRrhhlistaAll(long limit, long offset, BigDecimal idempresa)
			throws EJBException {
		String cQuery = "SELECT idlista,lista,usuarioalt,usuarioact,fechaalt,fechaact,idempresa FROM RRHHLISTA WHERE idempresa = "
				+ idempresa.toString()
				+ "  ORDER BY 2  LIMIT "
				+ limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = new ArrayList();
		try {
			vecSalida = getLista(cQuery);
		} catch (Exception ex) {
			log.error("Salida por exception: en el metodo: getRrhhlistaAll()  "
					+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getRrhhlistaOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		String cQuery = "SELECT  idlista,lista,usuarioalt,usuarioact,fechaalt,fechaact,idempresa FROM RRHHLISTA WHERE (UPPER(LISTA) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%')  AND idempresa = "
				+ idempresa.toString()
				+ " ORDER BY 2  LIMIT "
				+ limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = new ArrayList();
		try {
			vecSalida = getLista(cQuery);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhlistaOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getRrhhlistaPK(BigDecimal idlista, BigDecimal idempresa)
			throws EJBException {
		String cQuery = "SELECT  idlista,lista,usuarioalt,usuarioact,fechaalt,fechaact,idempresa FROM RRHHLISTA WHERE idlista="
				+ idlista.toString()
				+ " AND idempresa = "
				+ idempresa.toString() + ";";
		List vecSalida = new ArrayList();
		try {
			vecSalida = getLista(cQuery);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhlistaPK( BigDecimal idlista )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String rrhhlistaDelete(BigDecimal idlista, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM RRHHLISTA WHERE idlista="
				+ idlista.toString() + " AND idempresa=" + idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM RRHHLISTA WHERE idlista="
						+ idlista.toString().toString() + " AND idempresa="
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : rrhhlistaDelete( BigDecimal idlista, BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: rrhhlistaDelete( BigDecimal idlista, BigDecimal idempresa )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String rrhhlistaCreate(String lista, String usuarioalt,
			BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaalt = new Timestamp(hoy.getTime().getTime());
		if (lista == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: lista ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		if (fechaalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechaalt ";
		// 2. sin nada desde la pagina
		if (lista.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: lista ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO RRHHLISTA(lista, usuarioalt, fechaalt ,idempresa) VALUES (?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, lista);
				insert.setString(2, usuarioalt);
				insert.setTimestamp(3, fechaalt);
				insert.setBigDecimal(4, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String rrhhlistaCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error excepcion public String rrhhlistaCreate(.....)"
					+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String rrhhlistaCreateOrUpdate(BigDecimal idlista, String lista,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idlista == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idlista ";
		if (lista == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: lista ";

		// 2. sin nada desde la pagina
		if (lista.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: lista ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhlista WHERE idlista = "
					+ idlista.toString() + " and idempresa = "
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
					sql = "UPDATE RRHHLISTA SET lista=?, usuarioact=?, fechaact=?,idempresa=? WHERE idlista=? and idempresa = ?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, lista);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idempresa);
					insert.setBigDecimal(5, idlista);
					insert.setBigDecimal(6, idempresa);
				} else {
					String ins = "INSERT INTO RRHHLISTA(lista, usuarioalt, fechaalt , idempresa) VALUES (?, ?, ? , ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact;
					insert.setString(1, lista);
					insert.setString(2, usuarioalt);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String rrhhlistaCreateOrUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhlistaCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String rrhhlistaUpdate(BigDecimal idlista, String lista,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idlista == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idlista ";
		if (lista == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: lista ";

		// 2. sin nada desde la pagina
		if (lista.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: lista ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhlista WHERE idlista = "
					+ idlista.toString() + " and idempresa = "
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
					sql = "UPDATE RRHHLISTA SET lista=?, usuarioact=?, fechaact=?, idempresa=? WHERE idlista=? and idempresa =?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, lista);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idempresa);
					insert.setBigDecimal(5, idlista);
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
			log.error("Error SQL public String rrhhlistaUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log.error("Error excepcion public String rrhhlistaUpdate(.....)"
					+ ex);
		}
		return salida;
	}

	/*
	 * Metodos para la entidad: rrhhlistaconceptos Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Mon Nov 12 12:04:58 ART 2012
	 */
	// para todo (ordena por el segundo campo por defecto)
	public List getRrhhlistaconceptosAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		String cQuery = "SELECT lxc.idlistaconcepto, lxc.idlista, lis.lista, lxc.idconcepto, con.concepto from rrhhlistaconceptos lxc"
				+ " inner join rrhhlista lis on (lxc.idlista = lis.idlista and lxc.idempresa = lis.idempresa)"
				+ " inner join rrhhconceptos con on (lxc.idconcepto = con.idconcepto and lxc.idempresa = con.idempresa) "
				+ " WHERE lxc.idempresa = "
				+ idempresa.toString()
				+ "  ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = new ArrayList();
		try {
			vecSalida = getLista(cQuery);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhlistaconceptosAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getRrhhlistaconceptosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		String cQuery = "SELECT lxc.idlistaconcepto, lxc.idlista, lis.lista, lxc.idconcepto, con.concepto from rrhhlistaconceptos lxc"
				+ " inner join rrhhlista lis on (lxc.idlista = lis.idlista and lxc.idempresa = lis.idempresa)"
				+ " inner join rrhhconceptos con on (lxc.idconcepto = con.idconcepto and lxc.idempresa = con.idempresa) "
				+ " WHERE (UPPER(lis.lista) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%')  AND lxc.idempresa = "
				+ idempresa.toString()
				+ " ORDER BY 2  LIMIT "
				+ limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = new ArrayList();
		try {
			vecSalida = getLista(cQuery);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhlistaconceptosOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getRrhhlistaconceptosPK(BigDecimal idlistaconcepto,
			BigDecimal idempresa) throws EJBException {
		String cQuery = "SELECT lxc.idlistaconcepto,  lxc.idlista, lis.lista, lxc.idconcepto, con.concepto from rrhhlistaconceptos lxc"
				+ " inner join rrhhlista lis on (lxc.idlista = lis.idlista and lxc.idempresa = lis.idempresa)"
				+ " inner join rrhhconceptos con on (lxc.idconcepto = con.idconcepto and lxc.idempresa = con.idempresa) "
				+ " WHERE lxc.ididlistaconcepto, lxc.idempresa = "
				+ idempresa.toString() + ";";
		List vecSalida = new ArrayList();
		try {
			vecSalida = getLista(cQuery);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getRrhhlistaconceptosPK( BigDecimal idlistaconcepto )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String rrhhlistaconceptosDelete(BigDecimal idlistaconcepto,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM RRHHLISTACONCEPTOS WHERE idlistaconcepto="
				+ idlistaconcepto.toString()
				+ " AND idempresa="
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM RRHHLISTACONCEPTOS WHERE idlistaconcepto="
						+ idlistaconcepto.toString().toString()
						+ " AND idempresa=" + idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : rrhhlistaconceptosDelete( BigDecimal idlistaconcepto, BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: rrhhlistaconceptosDelete( BigDecimal idlistaconcepto, BigDecimal idempresa )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String rrhhlistaconceptosCreate(BigDecimal idlista,
			BigDecimal idconcepto, String usuarioalt, BigDecimal idempresa)
			throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaalt = new Timestamp(hoy.getTime().getTime());
		if (idlista == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idlista ";
		if (idconcepto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idconcepto ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		if (fechaalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechaalt ";
		// 2. sin nada desde la pagina
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO RRHHLISTACONCEPTOS(idlista, idconcepto, usuarioalt, fechaalt, idempresa ) VALUES (?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, idlista);
				insert.setBigDecimal(2, idconcepto);
				insert.setString(3, usuarioalt);
				insert.setTimestamp(4, fechaalt);
				insert.setBigDecimal(5, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String rrhhlistaconceptosCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhlistaconceptosCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String rrhhlistaconceptosCreateOrUpdate(BigDecimal idlistaconcepto,
			BigDecimal idlista, BigDecimal idconcepto, String usuarioact,
			BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idlistaconcepto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idlistaconcepto ";
		if (idlista == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idlista ";
		if (idconcepto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idconcepto ";

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhlistaconceptos WHERE idlistaconcepto = "
					+ idlistaconcepto.toString()
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
					sql = "UPDATE RRHHLISTACONCEPTOS SET idlista=?, idconcepto=?, usuarioact=?, fechaact=?, idempresa=? WHERE idlistaconcepto=? and idempresa = ?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, idlista);
					insert.setBigDecimal(2, idconcepto);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, idempresa);
					insert.setBigDecimal(6, idlistaconcepto);
					insert.setBigDecimal(7, idempresa);
				} else {
					String ins = "INSERT INTO RRHHLISTACONCEPTOS(idlista, idconcepto, usuarioalt, fechaalt , idempresa) VALUES (?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact;
					insert.setBigDecimal(1, idlista);
					insert.setBigDecimal(2, idconcepto);
					insert.setString(3, usuarioalt);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String rrhhlistaconceptosCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String rrhhlistaconceptosCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String rrhhlistaconceptosUpdate(BigDecimal idlistaconcepto,
			BigDecimal idlista, BigDecimal idconcepto, String usuarioact,
			BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idlistaconcepto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idlistaconcepto ";
		if (idlista == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idlista ";
		if (idconcepto == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idconcepto ";

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM rrhhlistaconceptos WHERE idlistaconcepto = "
					+ idlistaconcepto.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE RRHHLISTACONCEPTOS SET idlista=?, idconcepto=?, usuarioact=?, fechaact=?,idempresa=? WHERE idlistaconcepto=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, idlista);
					insert.setBigDecimal(2, idconcepto);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, idempresa);
					insert.setBigDecimal(6, idlistaconcepto);
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
			log.error("Error SQL public String rrhhlistaconceptosUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String rrhhlistaconceptosUpdate(.....)"
							+ ex);
		}
		return salida;
	}
}
