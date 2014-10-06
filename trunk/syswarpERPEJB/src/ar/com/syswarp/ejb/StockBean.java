package ar.com.syswarp.ejb;

import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.ejb.CreateException;
import javax.ejb.Stateless;

import java.io.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import org.apache.log4j.*;

import sun.java2d.loops.DrawGlyphListAA.General;

import bsh.This;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
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
 * @ejb.bean name="Stock" display-name="Name for Stock" description="Description
 *           for Stock" jndi-name="ejb/Stock" type="Stateless"
 *           view-type="remote"
 */
@Stateless
public class StockBean implements Stock {

	/** The session context */
	private SessionContext context;

	GeneralBean gb = new GeneralBean();

	/* conexion a la base de datos */

	private Connection dbconn;;

	static Logger log = Logger.getLogger(StockBean.class);

	private Connection conexion;

	private Properties props;

	private String url;

	private String clase;

	private String usuario;

	private String clave;

	private String marketIdlista;

	private String blobSqlPath;

	public StockBean() {
		super();
		try {
			props = new Properties();
			props
					.load(StockBean.class
							.getResourceAsStream("system.properties"));

			url = props.getProperty("conn.url").trim();
			clase = props.getProperty("conn.clase").trim();
			usuario = props.getProperty("conn.usuario").trim();
			clave = props.getProperty("conn.clave").trim();
			marketIdlista = props.getProperty("market.idlista").trim();
			blobSqlPath = props.getProperty("blob.sql.path").trim();

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

	public StockBean(Connection dbconn) {
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

	public String getMarketIdlista() throws EJBException {
		return marketIdlista;
	}

	/**
	 * Reglas de negocio Stock:
	 * 
	 * @ejb.interface-method view-type = "remote"
	 * 
	 * @throws EJBException
	 *             Thrown if method fails due to system-level error. Entidades
	 *             que intervienen: Stock
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
		String cQuery = "SELECT count(3)AS total FROM " + entidad + " "
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

	public long getTotalEntidadFitroOcu(String entidad, String filtro,
			String[] campos, String ocurrencia, BigDecimal idempresa)
			throws EJBException {

		long total = 0l;
		ResultSet rsSalida = null;
		String cQuery = "SELECT count(1)AS total FROM " + entidad + "  "
				+ filtro + " and ";
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

	// private List getLista(String query) throws EJBException {
	private List getLista(String query) {
		List vecSalida = new ArrayList();
		try {
			Statement statement = dbconn.createStatement();
			ResultSet rsSalida = statement.executeQuery(query);
			ResultSetMetaData md = rsSalida.getMetaData();
			// log.info("A-METODO LLAMADA:" + getCallingMethodName());

			// GeneralBean.getCallingMethodName(0);

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
					+ GeneralBean.getCallingMethodName(0) + " : "
					+ sqlException);
			// throw new EJBException(
			// "getLista() - SQLException:  -- >: throw new EJBException ");
		} catch (Exception ex) {
			log.error("(EX) getLista(String query) - INVOCADO POR "
					+ GeneralBean.getCallingMethodName(0) + " : " + ex);

			// throw new EJBException(
			// "getLista() - Exception:  -- >: throw new EJBException ");
		}
		return vecSalida;
	}

	// STOCK FAMILIAS
	// para todo (ordena por el segundo campo por defecto)
	public List getStockfamiliasAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {

		String cQuery = ""
				+ "SELECT codigo_fm,descrip_fm,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM STOCKFAMILIAS WHERE idempresa = " + idempresa
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para todo (ordena por el segundo campo por defecto)
	public List getStockfamiliasMarketAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT codigo_fm,descrip_fm,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM STOCKFAMILIAS "
				+ " WHERE codigo_fm IN ( "
				+ "               SELECT DISTINCT g.codigo_fm "
				+ "                 FROM stockgrupos g "
				+ "                      INNER JOIN stockstock s ON g.codigo_gr = s.grupo_st"
				+ "                             AND g.idempresa = s.idempresa "
				+ "                      INNER JOIN clienteslistasdeprecios l ON s.codigo_st = l.codigo_st "
				+ "                             AND l.idlista = "
				+ marketIdlista
				+ "                             AND s.idempresa = l.idempresa AND l.idempresa = "
				+ idempresa + "  ) AND idempresa = " + idempresa
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getStockfamiliasOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT  codigo_fm,descrip_fm,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM STOCKFAMILIAS " + " where idempresa= "
				+ idempresa.toString() + " and (codigo_fm::VARCHAR LIKE '%"
				+ ocurrencia + "%' OR " + " UPPER(descrip_fm) LIKE '%"
				+ ocurrencia.toUpperCase() + "%') " + " ORDER BY 2";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getStockfamiliasPK(BigDecimal codigo_fm, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT  codigo_fm,descrip_fm,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM STOCKFAMILIAS WHERE codigo_fm="
				+ codigo_fm.toString() + " AND idempresa = " + idempresa;
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String StockfamiliasDelete(BigDecimal codigo_fm, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM STOCKFAMILIAS WHERE codigo_fm="
				+ codigo_fm.toString() + " AND idempresa = " + idempresa;
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM STOCKFAMILIAS WHERE codigo_fm="
						+ codigo_fm.toString() + " AND idempresa = "
						+ idempresa;
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : StockfamiliasDelete( BigDecimal codigo_fm ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: StockfamiliasDelete( BigDecimal codigo_fm )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String StockfamiliasCreate(String descrip_fm, String usuarioalt,
			BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (descrip_fm == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: Descripcion ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (descrip_fm.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Descripcion ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO STOCKFAMILIAS(descrip_fm, usuarioalt, idempresa ) VALUES (?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, descrip_fm);
				insert.setString(2, usuarioalt);
				insert.setBigDecimal(3, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String StockfamiliasCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String StockfamiliasCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String StockfamiliasCreateOrUpdate(BigDecimal codigo_fm,
			String descrip_fm, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (codigo_fm == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_fm ";
		if (descrip_fm == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: Descripcion ";

		// 2. sin nada desde la pagina
		if (descrip_fm.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Descripcion ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM Stockfamilias WHERE codigo_fm = "
					+ codigo_fm.toString() + " AND idempresa = " + idempresa;
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE STOCKFAMILIAS SET descrip_fm=?, usuarioact=?, fechaact=? WHERE codigo_fm=? AND idempresa = ?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, descrip_fm);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, codigo_fm);
					insert.setBigDecimal(5, idempresa);
				} else {
					String ins = "INSERT INTO STOCKFAMILIAS(descrip_fm, usuarioalt, idempresa ) VALUES (?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, descrip_fm);
					insert.setString(2, usuarioalt);
					insert.setBigDecimal(3, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String StockfamiliasCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String StockfamiliasCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String StockfamiliasUpdate(BigDecimal codigo_fm, String descrip_fm,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (codigo_fm == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_fm ";
		if (descrip_fm == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: Descripcion ";

		// 2. sin nada desde la pagina
		if (descrip_fm.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Descripcion ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM Stockfamilias WHERE codigo_fm = "
					+ codigo_fm.toString() + " AND idempresa = " + idempresa;
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE STOCKFAMILIAS SET descrip_fm=?, usuarioact=?, fechaact=? WHERE codigo_fm=? AND idempresa = ?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, descrip_fm);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, codigo_fm);
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
			log.error("Error SQL public String StockfamiliasUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String StockfamiliasUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// STOCK GRUPOS
	// para todo (ordena por el segundo campo por defecto)
	public List getStockgruposAll(long limit, long offset, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT s.codigo_gr, s.descrip_gr, sf.descrip_fm, st.descrip_gr,"
				+ "       s.usuarioalt, s.usuarioact, s.fechaalt, s.fechaact "
				+ "  FROM STOCKGRUPOS s "
				+ "       LEFT JOIN stockfamilias sf ON s.codigo_fm = sf.codigo_fm AND s.idempresa = sf.idempresa  "
				+ "       LEFT JOIN STOCKGRUPOS ST ON s.codigo_gr_pa = st.codigo_gr AND s.idempresa = st.idempresa "
				+ " WHERE s.idempresa = " + idempresa + " ORDER BY 2  LIMIT "
				+ limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getStockgruposOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT s.codigo_gr, s.descrip_gr, sf.descrip_fm, st.descrip_gr,"
				+ "       s.usuarioalt, s.usuarioact, s.fechaalt, s.fechaact "
				+ "  FROM STOCKGRUPOS s "
				+ "       LEFT JOIN stockfamilias sf on s.codigo_fm = sf.codigo_fm AND s.idempresa = sf.idempresa  "
				+ "       LEFT JOIN STOCKGRUPOS ST on s.codigo_gr_pa = st.codigo_gr AND s.idempresa = st.idempresa "
				+ " where s.idempresa= " + idempresa.toString()
				+ " and (s.codigo_gr::VARCHAR LIKE '%" + ocurrencia + "%' OR "
				+ " UPPER(s.descrip_gr) LIKE '%" + ocurrencia.toUpperCase()
				+ "%') " + " ORDER BY 2";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getStockgruposPK(BigDecimal codigo_gr, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT s.codigo_gr, s.descrip_gr, s.codigo_fm, sf.descrip_fm,s.codigo_gr_pa, st.descrip_gr,s.usuarioalt, s.usuarioact, s.fechaalt, s.fechaact"
				+ "  FROM STOCKGRUPOS s "
				+ "       LEFT JOIN stockfamilias sf ON s.codigo_fm = sf.codigo_fm  AND s.idempresa = sf.idempresa "
				+ "       LEFT JOIN STOCKGRUPOS ST ON s.codigo_gr_pa = st.codigo_gr AND s.idempresa = st.idempresa  "
				+ " WHERE s.codigo_gr =" + codigo_gr.toString()
				+ "   AND s.idempresa = " + idempresa;

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// Grupos pertenecientes a una familia.

	public List getStockGruposXFamilia(BigDecimal codigo_fm,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT codigo_gr, descrip_gr, codigo_fm, usuarioalt, usuarioact, fechaalt, fechaact "
				+ "  FROM STOCKGRUPOS " + " WHERE idempresa = "
				+ idempresa.toString() + " AND codigo_fm = "
				+ codigo_fm.toString() + " ORDER BY 2  ;";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String StockgruposDelete(BigDecimal codigo_gr, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM STOCKGRUPOS WHERE codigo_gr="
				+ codigo_gr.toString() + " AND idempresa = "
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM STOCKGRUPOS WHERE codigo_gr="
						+ codigo_gr.toString() + " AND idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : StockgruposDelete( BigDecimal codigo_gr ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: StockgruposDelete( BigDecimal codigo_gr )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String StockgruposCreate(String descrip_gr, String codigo_fm,
			String codigo_gr_pa, String usuarioalt, BigDecimal idempresa)
			throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (descrip_gr == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: descrip_gr ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (descrip_gr.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Descripcion ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO STOCKGRUPOS(descrip_gr, codigo_fm, codigo_gr_pa, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, descrip_gr);
				if (codigo_fm != "" && !codigo_fm.equalsIgnoreCase("")) {
					insert.setBigDecimal(2, new BigDecimal(codigo_fm));
				} else {
					insert.setBigDecimal(2, null);
				}
				if (codigo_gr_pa != "" && !codigo_gr_pa.equalsIgnoreCase("")) {
					insert.setBigDecimal(3, new BigDecimal(codigo_gr_pa));
				} else {
					insert.setBigDecimal(3, null);
				}
				insert.setString(4, usuarioalt);
				insert.setBigDecimal(5, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String StockgruposCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error excepcion public String StockgruposCreate(.....)"
					+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String StockgruposCreateOrUpdate(BigDecimal codigo_gr,
			String descrip_gr, String codigo_fm, String codigo_gr_pa,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (codigo_gr == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_gr ";
		if (descrip_gr == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: descrip_gr ";

		// 2. sin nada desde la pagina
		if (descrip_gr.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Descripcion ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM Stockgrupos WHERE codigo_gr = "
					+ codigo_gr.toString()
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
					sql = "UPDATE STOCKGRUPOS SET descrip_gr=?, codigo_fm=?, codigo_gr_pa=?, usuarioact=?, fechaact=? WHERE codigo_gr=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, descrip_gr);
					if (codigo_fm != null
							&& !codigo_fm.equalsIgnoreCase("null")) {
						insert.setBigDecimal(2, new BigDecimal(codigo_fm));
					} else {
						insert.setBigDecimal(2, null);
					}
					if (codigo_gr_pa != null
							&& !codigo_gr_pa.equalsIgnoreCase("null")) {
						insert.setBigDecimal(3, new BigDecimal(codigo_gr_pa));
					} else {
						insert.setBigDecimal(3, null);
					}
					insert.setString(4, usuarioact);
					insert.setTimestamp(5, fechaact);
					insert.setBigDecimal(6, codigo_gr);
					insert.setBigDecimal(7, idempresa);
				} else {
					String ins = "INSERT INTO STOCKGRUPOS(descrip_gr, codigo_fm, codigo_gr_pa, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, descrip_gr);
					if (codigo_fm != "" && !codigo_fm.equalsIgnoreCase("")) {
						insert.setBigDecimal(2, new BigDecimal(codigo_fm));
					} else {
						insert.setBigDecimal(2, null);
					}
					if (codigo_gr_pa != ""
							&& !codigo_gr_pa.equalsIgnoreCase("")) {
						insert.setBigDecimal(3, new BigDecimal(codigo_gr_pa));
					} else {
						insert.setBigDecimal(3, null);
					}
					insert.setString(4, usuarioalt);
					insert.setBigDecimal(5, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String StockgruposCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String StockgruposCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String StockgruposUpdate(BigDecimal codigo_gr, String descrip_gr,
			String codigo_fm, String codigo_gr_pa, String usuarioact,
			BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (codigo_gr == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_gr ";
		if (descrip_gr == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: descrip_gr ";

		// 2. sin nada desde la pagina
		if (descrip_gr.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Descripcion ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM Stockgrupos WHERE codigo_gr = "
					+ codigo_gr.toString() + " AND idempresa = " + idempresa;
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE STOCKGRUPOS SET descrip_gr=?, codigo_fm=?, codigo_gr_pa=?, usuarioact=?, fechaact=? WHERE codigo_gr=? AND idempresa = ?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, descrip_gr);
					if (codigo_fm != null
							&& !codigo_fm.equalsIgnoreCase("null")) {
						insert.setBigDecimal(2, new BigDecimal(codigo_fm));
					} else {
						insert.setBigDecimal(2, null);
					}
					if (codigo_gr_pa != null
							&& !codigo_gr_pa.equalsIgnoreCase("null")) {
						insert.setBigDecimal(3, new BigDecimal(codigo_gr_pa));
					} else {
						insert.setBigDecimal(3, null);
					}
					insert.setString(4, usuarioact);
					insert.setTimestamp(5, fechaact);
					insert.setBigDecimal(6, codigo_gr);
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
			log.error("Error SQL public String StockgruposUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log.error("Error excepcion public String StockgruposUpdate(.....)"
					+ ex);
		}
		return salida;
	}

	// ESTO SE HIZO PARA TRAER EL LOV DE LAS FAMILIAS
	public List getFamiliasLOV(String ocurrencia, BigDecimal idempresa)
			throws EJBException {
		String cQuery = "SELECT * FROM stockfamilias WHERE UPPER(descrip_fm) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' "
				+ " AND idempresa = "
				+ idempresa.toString() + " ORDER BY codigo_fm";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ESTO SE HIZO PARA TRAER EL LOV DE LOS GRUPOS
	public List getGruposLOV(String ocurrencia, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM stockgrupos WHERE UPPER(descrip_gr) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' AND idempresa="
				+ idempresa.toString() + " ORDER BY codigo_gr";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// STOCK UBICACION
	// para todo (ordena por el segundo campo por defecto)
	public List getStockubicacioAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT su.codigo_ubi, sd.descrip_dt, su.ubicac_ubi, "
				+ "       su.usuarioalt, su.usuarioact, "
				+ "       su.fechaalt, su.fechaact "
				+ "  FROM STOCKUBICACIO su "
				+ "       INNER JOIN  stockdepositos sd ON sd.codigo_dt = su.deposi_ubi  AND sd.idempresa = su.idempresa "
				+ " WHERE su.idempresa =  " + idempresa.toString()
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getStockubicacioOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT su.codigo_ubi, sd.descrip_dt, su.ubicac_ubi, su.usuarioalt, su.usuarioact, su.fechaalt, su.fechaact "
				+ "  FROM STOCKUBICACIO su "
				+ "       INNER JOIN stockdepositos sd ON sd.codigo_dt = su.deposi_ubi AND sd.idempresa = su.idempresa"
				+ " where su.idempresa= " + idempresa.toString()
				+ " and (su.codigo_ubi::VARCHAR LIKE '%" + ocurrencia
				+ "%' OR " + " UPPER(su.ubicac_ubi) LIKE '%"
				+ ocurrencia.toUpperCase() + "%') " + " ORDER BY 2";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getStockubicacioPK(BigDecimal codigo_ubi, BigDecimal idempesa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT su.codigo_ubi, su.deposi_ubi, sd.descrip_dt, su.ubicac_ubi, su.usuarioalt, su.usuarioact, su.fechaalt, su.fechaact "
				+ "  FROM STOCKUBICACIO su "
				+ "       INNER JOIN stockdepositos sd ON sd.codigo_dt = su.deposi_ubi AND sd.idempresa =  su.idempresa "
				+ " WHERE su.codigo_ubi=" + codigo_ubi.toString()
				+ "   AND su.idempresa = " + idempesa.toString();
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String StockubicacioDelete(BigDecimal codigo_ubi,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM STOCKUBICACIO WHERE codigo_ubi="
				+ codigo_ubi.toString() + " AND idempresa = "
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM STOCKUBICACIO WHERE codigo_ubi="
						+ codigo_ubi.toString() + " AND idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : StockubicacioDelete( BigDecimal codigo_ubi ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: StockubicacioDelete( BigDecimal codigo_ubi )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String StockubicacioCreate(BigDecimal deposi_ubi, String ubicac_ubi,
			String usuarioalt, BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (deposi_ubi == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: deposi_ubi ";
		if (ubicac_ubi == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: ubicac_ubi ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (ubicac_ubi.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Ubicacion ";
		if (deposi_ubi.compareTo(new BigDecimal(0)) == 0)
			salida = "Error: No se puede dejar vacio el campo: Deposito";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO STOCKUBICACIO(deposi_ubi, ubicac_ubi, usuarioalt, idempresa ) VALUES (?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, deposi_ubi);
				insert.setString(2, ubicac_ubi);
				insert.setString(3, usuarioalt);
				insert.setBigDecimal(4, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String StockubicacioCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String StockubicacioCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String StockubicacioCreateOrUpdate(BigDecimal codigo_ubi,
			BigDecimal deposi_ubi, String ubicac_ubi, String usuarioact,
			BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (codigo_ubi == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_ubi ";
		if (deposi_ubi == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: deposi_ubi ";
		if (ubicac_ubi == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: ubicac_ubi ";

		// 2. sin nada desde la pagina
		if (ubicac_ubi.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Ubicacion ";
		if (deposi_ubi.compareTo(new BigDecimal(0)) == 0)
			salida = "Error: No se puede dejar vacio el campo: Deposito";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM Stockubicacio WHERE codigo_ubi = "
					+ codigo_ubi.toString()
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
					sql = "UPDATE STOCKUBICACIO SET deposi_ubi=?, ubicac_ubi=?, usuarioact=?, fechaact=? WHERE codigo_ubi=? AND idempresa = ?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, deposi_ubi);
					insert.setString(2, ubicac_ubi);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, codigo_ubi);
					insert.setBigDecimal(6, idempresa);
				} else {
					String ins = "INSERT INTO STOCKUBICACIO(deposi_ubi, ubicac_ubi, usuarioalt, idempresa ) VALUES (?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setBigDecimal(1, deposi_ubi);
					insert.setString(2, ubicac_ubi);
					insert.setString(3, usuarioalt);
					insert.setBigDecimal(4, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String StockubicacioCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String StockubicacioCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String StockubicacioUpdate(BigDecimal codigo_ubi,
			BigDecimal deposi_ubi, String ubicac_ubi, String usuarioact,
			BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (codigo_ubi == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_ubi ";
		if (deposi_ubi == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: deposi_ubi ";
		if (ubicac_ubi == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: ubicac_ubi ";

		// 2. sin nada desde la pagina
		if (ubicac_ubi.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Ubicacion ";
		if (deposi_ubi.compareTo(new BigDecimal(0)) == 0)
			salida = "Error: No se puede dejar vacio el campo: Deposito";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM Stockubicacio WHERE codigo_ubi = "
					+ codigo_ubi.toString()
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
					sql = "UPDATE STOCKUBICACIO SET deposi_ubi=?, ubicac_ubi=?, usuarioact=?, fechaact=? WHERE codigo_ubi=? AND idempresa = ?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, deposi_ubi);
					insert.setString(2, ubicac_ubi);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, codigo_ubi);
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
			log.error("Error SQL public String StockubicacioUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String StockubicacioUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// ESTO SE HIZO PARA TRAER EL LOV DE LOS DEPOSITOS
	public List getDepositoLOV(String ocurrencia, BigDecimal idempresa)
			throws EJBException {
		String cQuery = "SELECT * FROM stockdepositos WHERE (UPPER(descrip_dt) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%') AND idempresa =  "
				+ idempresa.toString() + " ORDER BY codigo_dt";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// STOCK MEDIDAS
	// para todo (ordena por el segundo campo por defecto)
	public List getStockmedidasAll(long limit, long offset, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT codigo_md,descrip_md,cantidad_md,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM STOCKMEDIDAS WHERE idempresa ="
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
			log.error("Error SQL en el metodo : getStockmedidas() "
					+ sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception: en el metodo: getStockmedidas()  "
					+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getStockmedidasOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT codigo_md,descrip_md,cantidad_md,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM STOCKMEDIDAS " + " where idempresa= "
				+ idempresa.toString() + " and (codigo_md::VARCHAR LIKE '%"
				+ ocurrencia + "%' OR " + " UPPER(descrip_md) LIKE '%"
				+ ocurrencia.toUpperCase() + "%') " + " ORDER BY 2";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getStockmedidasPK(BigDecimal codigo_md, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT codigo_md,descrip_md,cantidad_md,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM STOCKMEDIDAS WHERE codigo_md=" + codigo_md.toString()
				+ " AND idempresa = " + idempresa.toString();
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String StockmedidasDelete(BigDecimal codigo_md, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM STOCKMEDIDAS WHERE codigo_md="
				+ codigo_md.toString() + " AND idempresa = "
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM STOCKMEDIDAS WHERE codigo_md="
						+ codigo_md.toString() + " AND idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : StockmedidasDelete( BigDecimal codigo_md, BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: StockmedidasDelete( BigDecimal codigo_md, BigDecimal idempresa )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String StockmedidasCreate(String descrip_md, Double cantidad_md,
			String usuarioalt, BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (descrip_md == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: descrip_md ";
		if (cantidad_md == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: Cantidad ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (descrip_md.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Descripcion ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO STOCKMEDIDAS(descrip_md, cantidad_md, usuarioalt, idempresa ) VALUES (?, ?, ?, ? )";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, descrip_md);
				insert.setDouble(2, cantidad_md.doubleValue());
				insert.setString(3, usuarioalt);
				insert.setBigDecimal(4, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String StockmedidasCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error excepcion public String StockmedidasCreate(.....)"
					+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String StockmedidasCreateOrUpdate(BigDecimal codigo_md,
			String descrip_md, Double cantidad_md, String usuarioact,
			BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (codigo_md == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_md ";
		if (descrip_md == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: descrip_md ";
		if (cantidad_md == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: Cantidad ";

		// 2. sin nada desde la pagina
		if (descrip_md.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Descripcion ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM Stockmedidas WHERE codigo_md = "
					+ codigo_md.toString()
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
					sql = "UPDATE STOCKMEDIDAS SET descrip_md=?, cantidad_md=?, usuarioact=?, fechaact=? WHERE codigo_md=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, descrip_md);
					insert.setDouble(2, cantidad_md.doubleValue());
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, codigo_md);
					insert.setBigDecimal(6, idempresa);
				} else {
					String ins = "INSERT INTO STOCKMEDIDAS(descrip_md, cantidad_md, usuarioalt, idempresa ) VALUES (?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, descrip_md);
					insert.setDouble(2, cantidad_md.doubleValue());
					insert.setString(3, usuarioalt);
					insert.setBigDecimal(4, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String StockmedidasCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String StockmedidasCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String StockmedidasUpdate(BigDecimal codigo_md, String descrip_md,
			Double cantidad_md, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (codigo_md == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_md ";
		if (descrip_md == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: descrip_md ";
		if (cantidad_md == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: Cantidad ";

		// 2. sin nada desde la pagina
		if (descrip_md.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Descripcion ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM Stockmedidas WHERE codigo_md = "
					+ codigo_md.toString()
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
					sql = "UPDATE STOCKMEDIDAS SET descrip_md=?, cantidad_md=?, usuarioact=?, fechaact=? WHERE codigo_md=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, descrip_md);
					insert.setDouble(2, cantidad_md.doubleValue());
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, codigo_md);
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
			log.error("Error SQL public String StockmedidasUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log.error("Error excepcion public String StockmedidasUpdate(.....)"
					+ ex);
		}
		return salida;
	}

	// STOCK DEPOSITOS
	// para todo (ordena por el segundo campo por defecto)
	public List getStockdepositosAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT sd.codigo_dt, sd.descrip_dt, sd.direc_dt, sd.factura_dt, gl.idlocalidad, gl.localidad, "
				+ "       sd.usuarioalt, sd.usuarioact, sd.fechaalt, sd.fechaact "
				+ "  FROM STOCKDEPOSITOS sd INNER JOIN globallocalidades gl ON sd.idlocalidad = gl.idlocalidad"
				+ " WHERE sd.idempresa = " + idempresa.toString()
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getStockdepositosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT sd.codigo_dt, sd.descrip_dt, sd.direc_dt, sd.factura_dt, gl.idlocalidad, gl.localidad, "
				+ "       sd.usuarioalt, sd.usuarioact, sd.fechaalt,sd.fechaact "
				+ "  FROM STOCKDEPOSITOS sd INNER JOIN globallocalidades gl ON sd.idlocalidad = gl.idlocalidad "
				+ " WHERE sd.idempresa= " + idempresa.toString()
				+ "   AND (sd.codigo_dt::VARCHAR LIKE '%" + ocurrencia + "%' "
				+ "    OR UPPER(sd.descrip_dt) LIKE '%"
				+ ocurrencia.toUpperCase() + "%') " + " ORDER BY 2";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getStockdepositosPK(BigDecimal codigo_dt, BigDecimal idempresa)
			throws EJBException {

		String cQuery = ""
				+ "SELECT sd.codigo_dt, sd.descrip_dt, sd.direc_dt, sd.factura_dt, gl.idlocalidad, gl.localidad, "
				+ "       sd.usuarioalt, sd.usuarioact, sd.fechaalt, sd.fechaact "
				+ "  FROM STOCKDEPOSITOS sd INNER JOIN globallocalidades gl ON sd.idlocalidad = gl.idlocalidad "
				+ " WHERE sd.codigo_dt=" + codigo_dt.toString()
				+ "   AND sd.idempresa = " + idempresa.toString();

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String StockdepositosDelete(BigDecimal codigo_dt,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM STOCKDEPOSITOS WHERE codigo_dt="
				+ codigo_dt.toString() + " AND idempresa = "
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM STOCKDEPOSITOS WHERE codigo_dt="
						+ codigo_dt.toString() + " AND idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : StockdepositosDelete( BigDecimal codigo_dt ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: StockdepositosDelete( BigDecimal codigo_dt )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String StockdepositosCreate(String descrip_dt, String direc_dt,
			String factura_dt, BigDecimal idlocalidad, String usuarioalt,
			BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (descrip_dt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: descrip_dt ";
		if (direc_dt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: direc_dt ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (factura_dt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Factura ";
		if (descrip_dt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: descripcion ";
		if (direc_dt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: direccion ";

		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = ""
						+ "INSERT INTO STOCKDEPOSITOS(descrip_dt, direc_dt, factura_dt, idlocalidad, usuarioalt, idempresa ) "
						+ "                   VALUES (?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, descrip_dt);
				insert.setString(2, direc_dt);
				insert.setString(3, factura_dt);
				insert.setBigDecimal(4, idlocalidad);
				insert.setString(5, usuarioalt);
				insert.setBigDecimal(6, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String StockdepositosCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String StockdepositosCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String StockdepositosCreateOrUpdate(BigDecimal codigo_dt,
			String descrip_dt, String direc_dt, String factura_dt,
			BigDecimal idlocalidad, String usuarioact, BigDecimal idempresa)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (codigo_dt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_dt ";
		if (descrip_dt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: descrip_dt ";
		if (direc_dt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: direc_dt ";

		// 2. sin nada desde la pagina
		if (factura_dt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Factura ";
		if (descrip_dt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: descripcion ";
		if (direc_dt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: direccion ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM Stockdepositos WHERE codigo_dt = "
					+ codigo_dt.toString()
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
					sql = "UPDATE STOCKDEPOSITOS SET descrip_dt=?, direc_dt=?, factura_dt=?, idlocalidad=?, usuarioact=?, fechaact=? WHERE codigo_dt=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, descrip_dt);
					insert.setString(2, direc_dt);
					insert.setString(3, factura_dt);
					insert.setBigDecimal(4, idlocalidad);
					insert.setString(5, usuarioact);
					insert.setTimestamp(6, fechaact);
					insert.setBigDecimal(7, codigo_dt);
					insert.setBigDecimal(8, idempresa);
				} else {
					String ins = "INSERT INTO STOCKDEPOSITOS(descrip_dt, direc_dt, factura_dt, idlocalidad, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, descrip_dt);
					insert.setString(2, direc_dt);
					insert.setString(3, factura_dt);
					insert.setBigDecimal(4, idlocalidad);
					insert.setString(5, usuarioalt);
					insert.setBigDecimal(6, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String StockdepositosCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String StockdepositosCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String StockdepositosUpdate(BigDecimal codigo_dt, String descrip_dt,
			String direc_dt, String factura_dt, BigDecimal idlocalidad,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (codigo_dt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_dt ";
		if (descrip_dt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: descrip_dt ";
		if (direc_dt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: direc_dt ";

		// 2. sin nada desde la pagina
		if (factura_dt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Factura ";
		if (descrip_dt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: descripcion ";
		if (direc_dt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: direccion ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM Stockdepositos WHERE codigo_dt = "
					+ codigo_dt.toString()
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
					sql = "UPDATE STOCKDEPOSITOS SET descrip_dt=?, direc_dt=?, factura_dt=?, idlocalidad=?, usuarioact=?, fechaact=? WHERE codigo_dt=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, descrip_dt);
					insert.setString(2, direc_dt);
					insert.setString(3, factura_dt);
					insert.setBigDecimal(4, idlocalidad);
					insert.setString(5, usuarioact);
					insert.setTimestamp(6, fechaact);
					insert.setBigDecimal(7, codigo_dt);
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
			log.error("Error SQL public String StockdepositosUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String StockdepositosUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// STOCK STOCK
	// para todo (ordena por el segundo campo por defecto)
	public List getStockstockAll(long limit, long offset, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT STST.codigo_st, STST.alias_st, STST.descrip_st, STST.descri2_st, STST.cost_pp_st, "
				+ "       STST.precipp_st, STST.cost_uc_st, STST.ultcomp_st, STST.cost_re_st, STST.reposic_st, "
				+ "       STST.nom_com_st, GLM.moneda, STST.grupo_st, SG.descrip_gr, STST.cantmin_st, "
				+ "       STST.unimed_st, UM.descrip_md, STST.bonific_st, STST.impint_st, "
				+ "       STST.impcant_st, STST.cuencom_st, STST.cuenven_st, STST.cuenve2_st, "
				+ "       STST.cuencos_st, STST.cuenaju_st, STST.inventa_st, STST.proveed_st, "
				+ "       PRV.razon_social, STST.provart_st, STST.id_indi_st, STST.despa_st, "
				+ "       STST.marca_st, STST.cafecga_st, STST.unialt1_st, STM1.descrip_md, "
				+ "       STST.unialt2_st, STM2.descrip_md, STST.unialt3_st, STM3.descrip_md, "
				+ "       STST.unialt4_st, STM4.descrip_md, STST.factor1_st, STST.factor2_st, "
				+ "       STST.factor3_st, STST.factor4_st, STST.tipoiva_st, CTI.descripcion,STST.venta_st, "
				+ "       STST.compra_st, STST.esquema_st, "
				+ "       STST.usuarioalt, STST.usuarioact, STST.fechaalt, STST.fechaact, bl.tupla AS tupla "
				+ "  FROM STOCKSTOCK STST  "
				+ "       LEFT JOIN globalmonedas GLM ON (STST.nom_com_st = GLM.idmoneda)  "
				+ "       LEFT JOIN proveedoproveed PRV ON (STST.proveed_st = PRV.idproveedor AND STST.idempresa = PRV.idempresa )  "
				+ "       LEFT JOIN stockmedidas STM1 ON (STST.unialt1_st  = STM1.codigo_md AND STST.idempresa = STM1.idempresa )  "
				+ "       LEFT JOIN stockmedidas STM2 ON (STST.unialt2_st  = STM2.codigo_md AND STST.idempresa = STM2.idempresa )  "
				+ "       LEFT JOIN stockmedidas STM3 ON (STST.unialt3_st  = STM3.codigo_md AND STST.idempresa = STM3.idempresa )  "
				+ "       LEFT JOIN stockmedidas STM4 ON (STST.unialt4_st  = STM4.codigo_md AND STST.idempresa = STM4.idempresa )  "
				+ "       LEFT JOIN stockgrupos SG ON (STST.grupo_st  = SG.codigo_gr AND STST.idempresa = SG.idempresa )"
				+ "       LEFT JOIN stockiva CTI ON (STST.tipoiva_st  = CTI.idstockiva  AND STST.idempresa = CTI.idempresa ) "
				+ "       LEFT JOIN stockmedidas UM ON (STST.unimed_st  = UM.codigo_md AND STST.idempresa = UM.idempresa ) "

				+ "       LEFT JOIN (SELECT DISTINCT tupla, idempresa FROM  globalblobimagenes ) bl ON STST.oid = bl.tupla AND STST.idempresa = bl.idempresa "

				+ " WHERE STST.idempresa =  " + idempresa.toString()
				+ " ORDER BY 1, 3  LIMIT " + limit + " OFFSET  " + offset + ";";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getStockstockOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT STST.codigo_st, STST.alias_st, STST.descrip_st, STST.descri2_st, STST.cost_pp_st, "
				+ "       STST.precipp_st, STST.cost_uc_st, STST.ultcomp_st, STST.cost_re_st, STST.reposic_st, "
				+ "       STST.nom_com_st, GLM.moneda, STST.grupo_st, SG.descrip_gr, STST.cantmin_st, "
				+ "       STST.unimed_st, UM.descrip_md, STST.bonific_st, STST.impint_st, "
				+ "       STST.impcant_st, STST.cuencom_st, STST.cuenven_st, STST.cuenve2_st, "
				+ "       STST.cuencos_st, STST.cuenaju_st, STST.inventa_st, STST.proveed_st, "
				+ "       PRV.razon_social, STST.provart_st, STST.id_indi_st, STST.despa_st, "
				+ "       STST.marca_st, STST.cafecga_st, STST.unialt1_st, STM1.descrip_md, "
				+ "       STST.unialt2_st, STM2.descrip_md, STST.unialt3_st, STM3.descrip_md, "
				+ "       STST.unialt4_st, STM4.descrip_md, STST.factor1_st, STST.factor2_st, "
				+ "       STST.factor3_st, STST.factor4_st, STST.tipoiva_st, CTI.descripcion,STST.venta_st, "
				+ "       STST.compra_st, STST.esquema_st,"
				+ "       STST.usuarioalt, STST.usuarioact, STST.fechaalt, STST.fechaact, bl.tupla AS tupla "
				+ "  FROM STOCKSTOCK STST  "
				+ "       LEFT JOIN globalmonedas GLM ON (STST.nom_com_st = GLM.idmoneda)  "
				+ "       LEFT JOIN proveedoproveed PRV ON (STST.proveed_st = PRV.idproveedor AND STST.idempresa = PRV.idempresa )  "
				+ "       LEFT JOIN stockmedidas STM1 ON (STST.unialt1_st  = STM1.codigo_md AND STST.idempresa = STM1.idempresa )  "
				+ "       LEFT JOIN stockmedidas STM2 ON (STST.unialt2_st  = STM2.codigo_md AND STST.idempresa = STM2.idempresa )  "
				+ "       LEFT JOIN stockmedidas STM3 ON (STST.unialt3_st  = STM3.codigo_md AND STST.idempresa = STM3.idempresa )  "
				+ "       LEFT JOIN stockmedidas STM4 ON (STST.unialt4_st  = STM4.codigo_md AND STST.idempresa = STM4.idempresa )  "
				+ "       LEFT JOIN stockgrupos SG ON (STST.grupo_st  = SG.codigo_gr AND STST.idempresa = SG.idempresa )"
				+ "       LEFT JOIN stockiva CTI ON (STST.tipoiva_st  = CTI.idstockiva  AND STST.idempresa = CTI.idempresa ) "
				+ "       LEFT JOIN stockmedidas UM ON (STST.unimed_st  = UM.codigo_md AND STST.idempresa = UM.idempresa ) "

				+ "       LEFT JOIN (SELECT DISTINCT tupla, idempresa FROM  globalblobimagenes ) bl ON STST.oid = bl.tupla AND STST.idempresa = bl.idempresa "

				+ " WHERE (UPPER(STST.alias_st) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(STST.descrip_st) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(SG.descrip_gr) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR (codigo_st::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(STST.descri2_st) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%') AND STST.idempresa = "
				+ idempresa.toString() + " ORDER BY 1, 3  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getStockstockPK(String codigo_st, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT STST.codigo_st, STST.alias_st, STST.descrip_st, STST.descri2_st, STST.cost_pp_st, "
				+ "       STST.precipp_st, STST.cost_uc_st, STST.ultcomp_st, STST.cost_re_st, STST.reposic_st,"
				+ "       STST.nom_com_st, GLM.moneda, STST.grupo_st, SG.descrip_gr, STST.cantmin_st, "
				+ "       STST.unimed_st, UM.descrip_md, STST.bonific_st, STST.impint_st, STST.impcant_st, "
				+ "       STST.cuencom_st, STST.cuenven_st, STST.cuenve2_st, STST.cuencos_st, STST.cuenaju_st, "
				+ "       STST.inventa_st, STST.proveed_st, PRV.razon_social, STST.provart_st, STST.id_indi_st, "
				+ "       STST.despa_st, STST.marca_st, STST.cafecga_st, STST.unialt1_st, STM1.descrip_md, "
				+ "       STST.unialt2_st, STM2.descrip_md, STST.unialt3_st, STM3.descrip_md, STST.unialt4_st, "
				+ "       STM4.descrip_md, STST.factor1_st, STST.factor2_st, STST.factor3_st, STST.factor4_st, "
				+ "       STST.tipoiva_st, CTI.descripcion,STST.venta_st, STST.compra_st, STST.esquema_st, "
				+ "       STST.usuarioalt, STST.usuarioact, STST.fechaalt, STST.fechaact, STST.kilaje, STST.oid AS tupla "
				+ "  FROM STOCKSTOCK STST  "
				+ "       LEFT JOIN globalmonedas GLM ON (STST.nom_com_st = GLM.idmoneda)  "
				+ "       LEFT JOIN proveedoproveed PRV ON (STST.proveed_st = PRV.idproveedor AND STST.idempresa = PRV.idempresa )  "
				+ "       LEFT JOIN stockmedidas STM1 ON (STST.unialt1_st  = STM1.codigo_md AND STST.idempresa = STM1.idempresa )  "
				+ "       LEFT JOIN stockmedidas STM2 ON (STST.unialt2_st  = STM2.codigo_md AND STST.idempresa = STM2.idempresa )  "
				+ "       LEFT JOIN stockmedidas STM3 ON (STST.unialt3_st  = STM3.codigo_md AND STST.idempresa = STM3.idempresa )  "
				+ "       LEFT JOIN stockmedidas STM4 ON (STST.unialt4_st  = STM4.codigo_md AND STST.idempresa = STM4.idempresa )  "
				+ "       LEFT JOIN stockgrupos SG ON (STST.grupo_st  = SG.codigo_gr  AND STST.idempresa = SG.idempresa )"
				+ "       LEFT JOIN stockiva CTI ON (STST.tipoiva_st  = CTI.idstockiva  AND STST.idempresa = CTI.idempresa ) "
				+ "       LEFT JOIN stockmedidas UM ON (STST.unimed_st  = UM.codigo_md AND STST.idempresa = UM.idempresa ) "
				+ "  WHERE STST.codigo_st='" + codigo_st.toString()
				+ "' AND STST.idempresa = " + idempresa.toString();
		List vecSalida = getLista(cQuery);
		return vecSalida;
	} // por primary key (primer campo por defecto)

	public List getStockStockMarketPK(String codigo_st, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT STST.codigo_st, STST.alias_st, STST.descrip_st, STST.descri2_st, STST.cost_pp_st, "
				+ "       l.precio, STST.cost_uc_st, STST.ultcomp_st, STST.cost_re_st, STST.reposic_st,"
				+ "       STST.nom_com_st, GLM.moneda, STST.grupo_st, SG.descrip_gr, STST.cantmin_st, "
				+ "       STST.unimed_st, UM.descrip_md, STST.bonific_st, STST.impint_st, STST.impcant_st, "
				+ "       STST.cuencom_st, STST.cuenven_st, STST.cuenve2_st, STST.cuencos_st, STST.cuenaju_st, "
				+ "       STST.inventa_st, STST.proveed_st, PRV.razon_social, STST.provart_st, STST.id_indi_st, "
				+ "       STST.despa_st, STST.marca_st, STST.cafecga_st, STST.unialt1_st, STM1.descrip_md, "
				+ "       STST.unialt2_st, STM2.descrip_md, STST.unialt3_st, STM3.descrip_md, STST.unialt4_st, "
				+ "       STM4.descrip_md, STST.factor1_st, STST.factor2_st, STST.factor3_st, STST.factor4_st, "
				+ "       STST.tipoiva_st, CTI.tipoiva,STST.venta_st, STST.compra_st, STST.esquema_st, "
				+ "       STST.usuarioalt, STST.usuarioact, STST.fechaalt, STST.fechaact "
				+ "  FROM STOCKSTOCK STST  "
				+ "       INNER JOIN clienteslistasdeprecios l ON STST.codigo_st = l.codigo_st AND STST.idempresa = l.idempresa "
				+ "   AND l.idlista = "
				+ marketIdlista
				+ "       LEFT JOIN globalmonedas GLM ON (STST.nom_com_st = GLM.idmoneda)  "
				+ "       LEFT JOIN proveedoproveed PRV ON (STST.proveed_st = PRV.idproveedor AND STST.idempresa = PRV.idempresa )  "
				+ "       LEFT JOIN stockmedidas STM1 ON (STST.unialt1_st  = STM1.codigo_md AND STST.idempresa = STM1.idempresa )  "
				+ "       LEFT JOIN stockmedidas STM2 ON (STST.unialt2_st  = STM2.codigo_md AND STST.idempresa = STM2.idempresa )  "
				+ "       LEFT JOIN stockmedidas STM3 ON (STST.unialt3_st  = STM3.codigo_md AND STST.idempresa = STM3.idempresa )  "
				+ "       LEFT JOIN stockmedidas STM4 ON (STST.unialt4_st  = STM4.codigo_md AND STST.idempresa = STM4.idempresa )  "
				+ "       LEFT JOIN stockgrupos SG ON (STST.grupo_st  = SG.codigo_gr  AND STST.idempresa = SG.idempresa )"
				+ "       LEFT JOIN clientestablaiva CTI ON (STST.tipoiva_st  = CTI.idtipoiva  AND STST.idempresa = CTI.idempresa ) "
				+ "       LEFT JOIN stockmedidas UM ON (STST.unimed_st  = UM.codigo_md AND STST.idempresa = UM.idempresa ) "
				+ "  WHERE STST.codigo_st='" + codigo_st.toString()
				+ "' AND STST.idempresa = " + idempresa.toString();
		List vecSalida = getLista(cQuery);
		return vecSalida;
	} // por primary key (primer campo por defecto)

	// por primary key (primer campo por defecto)
	public List getStockArticulosPK(String codigo_st, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT codigo_st,alias_st,descrip_st,descri2_st, "
				+ "       0 as cantidad, -1 as origen, -1 as destino, cost_uc_st, "
				+ "       id_indi_st AS esserializable, despa_st AS aceptadespacho"
				+ "  FROM STOCKSTOCK " + " WHERE codigo_st='" + codigo_st
				+ "' AND idempresa = " + idempresa.toString();

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// Recupera todos los articulos para un determinado grupo.
	public List getStockStockXGrupo(long limit, long offset,
			BigDecimal grupo_st, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT st.codigo_st, st.alias_st, st.descrip_st, st.descri2_st, "
				+ "       l.precio::numeric(18, 2), l.precio::numeric(18, 2), "
				+ "       st.cost_uc_st::numeric(18, 2), st.ultcomp_st::numeric(18, 2), "
				+ "       st.cost_re_st::numeric(18, 2), st.reposic_st, st.nom_com_st, st.grupo_st,  "
				+ "       LO_EXPORT(img.trama, "
				+ blobSqlPath
				+ " || nombre), img.nombre "
				+ "  FROM STOCKSTOCK st"
				+ "       INNER JOIN clienteslistasdeprecios l ON st.codigo_st = l.codigo_st "
				+ "              AND l.idlista =  "
				+ marketIdlista
				+ " AND st.idempresa = l.idempresa"
				+ "       LEFT  JOIN  globalblobimagenes img ON (st.oid = img.tupla)"
				+ " WHERE st.grupo_st= " + grupo_st + "   AND st.idempresa = "
				+ idempresa.toString() + " ORDER BY 3 LIMIT " + limit
				+ " OFFSET " + offset;

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// Recupera todos los articulos que cumplen un criterio de busqueda.
	public List getStockStockMarketOcu(long limit, long offset,
			String ocurrencia, BigDecimal grupo_st, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT st.codigo_st, st.alias_st, st.descrip_st, st.descri2_st, "
				+ "       l.precio::numeric(18, 2), l.precio::numeric(18, 2), "
				+ "       st.cost_uc_st::numeric(18, 2), st.ultcomp_st::numeric(18, 2), "
				+ "       st.cost_re_st::numeric(18, 2), st.reposic_st, st.nom_com_st, st.grupo_st,  "
				+ "       LO_EXPORT(img.trama, "
				+ blobSqlPath
				+ " || nombre), img.nombre "
				+ "  FROM STOCKSTOCK st"
				+ "       INNER JOIN clienteslistasdeprecios l ON st.codigo_st = l.codigo_st AND st.idempresa = l.idempresa AND l.idlista =  "
				+ marketIdlista
				+ "       LEFT  JOIN  globalblobimagenes img ON (st.oid = img.tupla)"
				+ " WHERE ((st.codigo_st::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase()
				+ "%'  OR UPPER(st.descrip_st) LIKE '%"
				+ ocurrencia.toUpperCase() + "%' )" + " and st.grupo_st= "
				+ grupo_st + "  AND st.idempresa = " + idempresa.toString()
				+ "  ORDER BY 3 LIMIT " + limit + " OFFSET " + offset;

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String StockstockDelete(String codigo_st, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM STOCKSTOCK WHERE codigo_st = " + "'"
				+ codigo_st.toString() + "' AND idempresa = "
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM STOCKSTOCK WHERE codigo_st= " + "'"
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
					.error("Error SQL en el metodo : StockstockDelete( String codigo_st ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: StockstockDelete( String codigo_st )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String StockstockCreate(String codigo_st, String alias_st,
			String descrip_st, String descri2_st, Double cost_pp_st,
			Double precipp_st, Double cost_uc_st, Double ultcomp_st,
			Double cost_re_st, Double reposic_st, String nom_com_st,
			String grupo_st, Double cantmin_st, String unimed_st,
			Double bonific_st, Double impint_st, Double impcant_st,
			String cuencom_st, String cuenven_st, String cuenve2_st,
			String cuencos_st, String cuenaju_st, String inventa_st,
			String proveed_st, String provart_st, String id_indi_st,
			String despa_st, String marca_st, Double cafecga_st,
			String unialt1_st, String unialt2_st, String unialt3_st,
			String unialt4_st, Double factor1_st, Double factor2_st,
			Double factor3_st, Double factor4_st, String tipoiva_st,
			String venta_st, String compra_st, BigDecimal esquema_st,
			String usuarioalt, BigDecimal idempresa, Double kilaje)
			throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (codigo_st == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo ";
		if (codigo_st.indexOf(' ') != -1)
			salida = "Error: No se pueden dejar espacios el campo: codigo ";
		if (descrip_st == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: descrip_st ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";

		if (unimed_st == null)
			salida = "Error: No se puede dejar sin datos (nulo) la unidad de medida ";

		// 2. sin nada desde la pagina
		if (descrip_st.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: descrip_st ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				// 18/10/2007
				/*
				 * CEP: guardo con mayusculas codigo y alias a proposito
				 */

				codigo_st = codigo_st.toUpperCase();
				alias_st = alias_st.toUpperCase();
				String ins = ""
						+ "INSERT INTO STOCKSTOCK"
						+ "            (codigo_st, alias_st, descrip_st, descri2_st, cost_pp_st, precipp_st, cost_uc_st, ultcomp_st, cost_re_st, reposic_st, nom_com_st, grupo_st, cantmin_st, unimed_st, bonific_st, impint_st, impcant_st, cuencom_st, cuenven_st, cuenve2_st, cuencos_st, cuenaju_st, inventa_st, proveed_st, provart_st, id_indi_st, despa_st, marca_st, cafecga_st, unialt1_st, unialt2_st, unialt3_st, unialt4_st, factor1_st, factor2_st, factor3_st, factor4_st, tipoiva_st, venta_st, compra_st, esquema_st, usuarioalt, idempresa ,kilaje ) "
						+ "     VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, codigo_st);
				insert.setString(2, alias_st);
				insert.setString(3, descrip_st);
				insert.setString(4, descri2_st);
				insert.setDouble(5, cost_pp_st.doubleValue());
				insert.setDouble(6, precipp_st.doubleValue());
				insert.setDouble(7, cost_uc_st.doubleValue());
				insert.setDouble(8, ultcomp_st.doubleValue());
				insert.setDouble(9, cost_re_st.doubleValue());
				insert.setDouble(10, reposic_st.doubleValue());
				if (nom_com_st != "" && !nom_com_st.equalsIgnoreCase("")) {
					insert.setBigDecimal(11, new BigDecimal(nom_com_st));
				} else {
					insert.setBigDecimal(11, null);
				}
				if (grupo_st != "" && !grupo_st.equalsIgnoreCase("")) {
					insert.setBigDecimal(12, new BigDecimal(grupo_st));
				} else {
					insert.setBigDecimal(12, null);
				}
				insert.setDouble(13, cantmin_st.doubleValue());
				if (unimed_st != "" && !unimed_st.equalsIgnoreCase("")) {
					insert.setBigDecimal(14, new BigDecimal(unimed_st));
				} else {
					insert.setBigDecimal(14, null);
				}
				insert.setDouble(15, bonific_st.doubleValue());
				insert.setDouble(16, impint_st.doubleValue());
				insert.setDouble(17, impcant_st.doubleValue());
				if (cuencom_st != "" && !cuencom_st.equalsIgnoreCase("null")) {
					insert.setBigDecimal(18, new BigDecimal(cuencom_st));
				} else {
					insert.setBigDecimal(18, null);
				}
				if (cuenven_st != "" && !cuenven_st.equalsIgnoreCase("")) {
					insert.setBigDecimal(19, new BigDecimal(cuenven_st));
				} else {
					insert.setBigDecimal(19, null);
				}
				if (cuenve2_st != "" && !cuenve2_st.equalsIgnoreCase("")) {
					insert.setBigDecimal(20, new BigDecimal(cuenve2_st));
				} else {
					insert.setBigDecimal(20, null);
				}
				if (cuencos_st != "" && !cuencos_st.equalsIgnoreCase("")) {
					insert.setBigDecimal(21, new BigDecimal(cuencos_st));
				} else {
					insert.setBigDecimal(21, null);
				}
				if (cuenaju_st != "" && !cuenaju_st.equalsIgnoreCase("")) {
					insert.setBigDecimal(22, new BigDecimal(cuenaju_st));
				} else {
					insert.setBigDecimal(22, null);
				}
				insert.setString(23, inventa_st);
				if (proveed_st != "" && !proveed_st.equalsIgnoreCase("")) {
					insert.setBigDecimal(24, new BigDecimal(proveed_st));
				} else {
					insert.setBigDecimal(24, null);
				}
				insert.setString(25, provart_st);
				insert.setString(26, id_indi_st);
				insert.setString(27, despa_st);
				insert.setString(28, marca_st);
				insert.setDouble(29, cafecga_st.doubleValue());
				if (unialt1_st != "" && !unialt1_st.equalsIgnoreCase("")) {
					insert.setBigDecimal(30, new BigDecimal(unialt1_st));
				} else {
					insert.setBigDecimal(30, null);
				}
				if (unialt2_st != "" && !unialt2_st.equalsIgnoreCase("")) {
					insert.setBigDecimal(31, new BigDecimal(unialt2_st));
				} else {
					insert.setBigDecimal(31, null);
				}
				if (unialt3_st != "" && !unialt3_st.equalsIgnoreCase("")) {
					insert.setBigDecimal(32, new BigDecimal(unialt3_st));
				} else {
					insert.setBigDecimal(32, null);
				}
				if (unialt4_st != "" && !unialt4_st.equalsIgnoreCase("")) {
					insert.setBigDecimal(33, new BigDecimal(unialt4_st));
				} else {
					insert.setBigDecimal(33, null);
				}
				insert.setDouble(34, factor1_st.doubleValue());
				insert.setDouble(35, factor2_st.doubleValue());
				insert.setDouble(36, factor3_st.doubleValue());
				insert.setDouble(37, factor4_st.doubleValue());
				if (tipoiva_st != "" && !tipoiva_st.equalsIgnoreCase("")) {
					insert.setBigDecimal(38, new BigDecimal(tipoiva_st));
				} else {
					insert.setBigDecimal(38, null);
				}
				insert.setString(39, venta_st);
				insert.setString(40, compra_st);
				insert.setBigDecimal(41, esquema_st);
				insert.setString(42, usuarioalt);
				insert.setBigDecimal(43, idempresa);
				insert.setDouble(44, kilaje.doubleValue());
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String StockstockCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error excepcion public String StockstockCreate(.....)"
					+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String StockstockCreateOrUpdate(String codigo_st, String alias_st,
			String descrip_st, String descri2_st, Double cost_pp_st,
			Double precipp_st, Double cost_uc_st, Double ultcomp_st,
			Double cost_re_st, Double reposic_st, String nom_com_st,
			String grupo_st, Double cantmin_st, String unimed_st,
			Double bonific_st, Double impint_st, Double impcant_st,
			String cuencom_st, String cuenven_st, String cuenve2_st,
			String cuencos_st, String cuenaju_st, String inventa_st,
			String proveed_st, String provart_st, String id_indi_st,
			String despa_st, String marca_st, Double cafecga_st,
			String unialt1_st, String unialt2_st, String unialt3_st,
			String unialt4_st, Double factor1_st, Double factor2_st,
			Double factor3_st, Double factor4_st, String tipoiva_st,
			String venta_st, String compra_st, BigDecimal esquema_st,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (codigo_st == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_st ";

		if (codigo_st.indexOf(' ') != -1)
			salida = "Error: No se pueden dejar espacios el campo: codigo ";

		if (descrip_st == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: descrip_st ";

		// 2. sin nada desde la pagina
		if (codigo_st.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: codigo_st ";
		if (descrip_st.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: descrip_st ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM Stockstock WHERE codigo_st = "
					+ "'"
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
					sql = "UPDATE STOCKSTOCK SET alias_st=?, descrip_st=?, descri2_st=?, cost_pp_st=?, precipp_st=?, cost_uc_st=?, ultcomp_st=?, cost_re_st=?, reposic_st=?, nom_com_st=?, grupo_st=?, cantmin_st=?, unimed_st=?, bonific_st=?, impint_st=?, impcant_st=?, cuencom_st=?, cuenven_st=?, cuenve2_st=?, cuencos_st=?, cuenaju_st=?, inventa_st=?, proveed_st=?, provart_st=?, id_indi_st=?, despa_st=?, marca_st=?, cafecga_st=?, unialt1_st=?, unialt2_st=?, unialt3_st=?, unialt4_st=?, factor1_st=?, factor2_st=?, factor3_st=?, factor4_st=?, tipoiva_st=?, venta_st=?, compra_st=?, esquema_st=?, usuarioact=?, fechaact=? WHERE codigo_st=? AND idempresa =?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, alias_st);
					insert.setString(2, descrip_st);
					insert.setString(3, descri2_st);
					insert.setDouble(4, cost_pp_st.doubleValue());
					insert.setDouble(5, precipp_st.doubleValue());
					insert.setDouble(6, cost_uc_st.doubleValue());
					insert.setDouble(7, ultcomp_st.doubleValue());
					insert.setDouble(8, cost_re_st.doubleValue());
					insert.setDouble(9, reposic_st.doubleValue());
					if (nom_com_st != null
							&& !nom_com_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(10, new BigDecimal(nom_com_st));
					} else {
						insert.setBigDecimal(10, null);
					}
					if (grupo_st != null && !grupo_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(11, new BigDecimal(grupo_st));
					} else {
						insert.setBigDecimal(11, null);
					}
					insert.setDouble(12, cantmin_st.doubleValue());
					if (unimed_st != null
							&& !unimed_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(13, new BigDecimal(unimed_st));
					} else {
						insert.setBigDecimal(13, null);
					}
					insert.setDouble(14, bonific_st.doubleValue());
					insert.setDouble(15, impint_st.doubleValue());
					insert.setDouble(16, impcant_st.doubleValue());
					if (cuencom_st != null
							&& !cuencom_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(17, new BigDecimal(cuencom_st));
					} else {
						insert.setBigDecimal(17, null);
					}
					if (cuenven_st != null
							&& !cuenven_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(18, new BigDecimal(cuenven_st));
					} else {
						insert.setBigDecimal(18, null);
					}
					if (cuenve2_st != null
							&& !cuenve2_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(19, new BigDecimal(cuenve2_st));
					} else {
						insert.setBigDecimal(19, null);
					}
					if (cuencos_st != null
							&& !cuencos_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(20, new BigDecimal(cuencos_st));
					} else {
						insert.setBigDecimal(20, null);
					}
					if (cuenaju_st != null
							&& !cuenaju_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(21, new BigDecimal(cuenaju_st));
					} else {
						insert.setBigDecimal(21, null);
					}
					insert.setString(22, inventa_st);
					if (proveed_st != null
							&& !proveed_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(23, new BigDecimal(proveed_st));
					} else {
						insert.setBigDecimal(23, null);
					}
					insert.setString(24, provart_st);
					insert.setString(25, id_indi_st);
					insert.setString(26, despa_st);
					insert.setString(27, marca_st);
					insert.setDouble(28, cafecga_st.doubleValue());
					if (unialt1_st != null
							&& !unialt1_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(29, new BigDecimal(unialt1_st));
					} else {
						insert.setBigDecimal(29, null);
					}
					if (unialt2_st != null
							&& !unialt2_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(30, new BigDecimal(unialt2_st));
					} else {
						insert.setBigDecimal(30, null);
					}
					if (unialt3_st != null
							&& !unialt3_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(31, new BigDecimal(unialt3_st));
					} else {
						insert.setBigDecimal(31, null);
					}
					if (unialt4_st != null
							&& !unialt4_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(32, new BigDecimal(unialt4_st));
					} else {
						insert.setBigDecimal(32, null);
					}
					insert.setDouble(33, factor1_st.doubleValue());
					insert.setDouble(34, factor2_st.doubleValue());
					insert.setDouble(35, factor3_st.doubleValue());
					insert.setDouble(36, factor4_st.doubleValue());
					if (tipoiva_st != null
							&& !tipoiva_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(37, new BigDecimal(tipoiva_st));
					} else {
						insert.setBigDecimal(37, null);
					}
					insert.setString(38, venta_st);
					insert.setString(39, compra_st);
					insert.setBigDecimal(40, esquema_st);
					insert.setString(41, usuarioact);
					insert.setTimestamp(42, fechaact);
					insert.setString(43, codigo_st);
					insert.setBigDecimal(44, idempresa);
				} else {
					String ins = "INSERT INTO STOCKSTOCK(codigo_st, alias_st, descrip_st, descri2_st, cost_pp_st, precipp_st, cost_uc_st, ultcomp_st, cost_re_st, reposic_st, nom_com_st, grupo_st, cantmin_st, unimed_st, bonific_st, impint_st, impcant_st, cuencom_st, cuenven_st, cuenve2_st, cuencos_st, cuenaju_st, inventa_st, proveed_st, provart_st, id_indi_st, despa_st, marca_st, cafecga_st, unialt1_st, unialt2_st, unialt3_st, unialt4_st, factor1_st, factor2_st, factor3_st, factor4_st, tipoiva_st, venta_st, compra_st, esquema_st, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, codigo_st);
					insert.setString(2, alias_st);
					insert.setString(3, descrip_st);
					insert.setString(4, descri2_st);
					insert.setDouble(5, cost_pp_st.doubleValue());
					insert.setDouble(6, precipp_st.doubleValue());
					insert.setDouble(7, cost_uc_st.doubleValue());
					insert.setDouble(8, ultcomp_st.doubleValue());
					insert.setDouble(9, cost_re_st.doubleValue());
					insert.setDouble(10, reposic_st.doubleValue());
					if (nom_com_st != null
							&& !nom_com_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(11, new BigDecimal(nom_com_st));
					} else {
						insert.setBigDecimal(11, null);
					}
					if (grupo_st != null && !grupo_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(12, new BigDecimal(grupo_st));
					} else {
						insert.setBigDecimal(12, null);
					}
					insert.setDouble(13, cantmin_st.doubleValue());
					if (unimed_st != null
							&& !unimed_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(14, new BigDecimal(unimed_st));
					} else {
						insert.setBigDecimal(14, null);
					}
					insert.setDouble(15, bonific_st.doubleValue());
					insert.setDouble(16, impint_st.doubleValue());
					insert.setDouble(17, impcant_st.doubleValue());
					if (cuencom_st != null
							&& !cuencom_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(18, new BigDecimal(cuencom_st));
					} else {
						insert.setBigDecimal(18, null);
					}
					if (cuenven_st != null
							&& !cuenven_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(19, new BigDecimal(cuenven_st));
					} else {
						insert.setBigDecimal(19, null);
					}
					if (cuenve2_st != null
							&& !cuenve2_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(20, new BigDecimal(cuenve2_st));
					} else {
						insert.setBigDecimal(20, null);
					}
					if (cuencos_st != null
							&& !cuencos_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(21, new BigDecimal(cuencos_st));
					} else {
						insert.setBigDecimal(21, null);
					}
					if (cuenaju_st != null
							&& !cuenaju_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(22, new BigDecimal(cuenaju_st));
					} else {
						insert.setBigDecimal(22, null);
					}
					insert.setString(23, inventa_st);
					if (proveed_st != null
							&& !proveed_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(24, new BigDecimal(proveed_st));
					} else {
						insert.setBigDecimal(24, null);
					}
					insert.setString(25, provart_st);
					insert.setString(26, id_indi_st);
					insert.setString(27, despa_st);
					insert.setString(28, marca_st);
					insert.setDouble(29, cafecga_st.doubleValue());
					if (unialt1_st != null
							&& !unialt1_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(30, new BigDecimal(unialt1_st));
					} else {
						insert.setBigDecimal(30, null);
					}
					if (unialt2_st != null
							&& !unialt2_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(31, new BigDecimal(unialt2_st));
					} else {
						insert.setBigDecimal(31, null);
					}
					if (unialt3_st != null
							&& !unialt3_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(32, new BigDecimal(unialt3_st));
					} else {
						insert.setBigDecimal(32, null);
					}
					if (unialt4_st != null
							&& !unialt4_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(33, new BigDecimal(unialt4_st));
					} else {
						insert.setBigDecimal(33, null);
					}
					insert.setDouble(34, factor1_st.doubleValue());
					insert.setDouble(35, factor2_st.doubleValue());
					insert.setDouble(36, factor3_st.doubleValue());
					insert.setDouble(37, factor4_st.doubleValue());
					if (tipoiva_st != null
							&& !tipoiva_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(38, new BigDecimal(tipoiva_st));
					} else {
						insert.setBigDecimal(38, null);
					}
					insert.setString(39, venta_st);
					insert.setString(40, compra_st);
					insert.setBigDecimal(41, esquema_st);
					insert.setString(42, usuarioalt);
					insert.setBigDecimal(43, idempresa);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String StockstockCreateOrUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String StockstockCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String StockstockUpdate(String codigo_st, String alias_st,
			String descrip_st, String descri2_st, Double cost_pp_st,
			Double precipp_st, Double cost_uc_st, Double ultcomp_st,
			Double cost_re_st, Double reposic_st, String nom_com_st,
			String grupo_st, Double cantmin_st, String unimed_st,
			Double bonific_st, Double impint_st, Double impcant_st,
			String cuencom_st, String cuenven_st, String cuenve2_st,
			String cuencos_st, String cuenaju_st, String inventa_st,
			String proveed_st, String provart_st, String id_indi_st,
			String despa_st, String marca_st, Double cafecga_st,
			String unialt1_st, String unialt2_st, String unialt3_st,
			String unialt4_st, Double factor1_st, Double factor2_st,
			Double factor3_st, Double factor4_st, String tipoiva_st,
			String venta_st, String compra_st, BigDecimal esquema_st,
			String usuarioact, BigDecimal idempresa, Double kilaje)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos

		if (codigo_st == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_st ";
		if (descrip_st == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: descrip_st ";

		// 2. sin nada desde la pagina
		if (codigo_st.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: codigo_st ";
		if (descrip_st.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: descrip_st ";

		// if (nom_com_st.compareTo(new BigDecimal(0)) == 0)
		// nom_com_st = "";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM Stockstock WHERE codigo_st = "
					+ "'"
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
					codigo_st = codigo_st.toUpperCase();
					alias_st = alias_st.toUpperCase();
					sql = "UPDATE STOCKSTOCK SET alias_st=?, descrip_st=?, descri2_st=?, cost_pp_st=?, precipp_st=?, cost_uc_st=?, ultcomp_st=?, cost_re_st=?, reposic_st=?, nom_com_st=?, grupo_st=?, cantmin_st=?, unimed_st=?, bonific_st=?, impint_st=?, impcant_st=?, cuencom_st=?, cuenven_st=?, cuenve2_st=?, cuencos_st=?, cuenaju_st=?, inventa_st=?, proveed_st=?, provart_st=?, id_indi_st=?, despa_st=?, marca_st=?, cafecga_st=?, unialt1_st=?, unialt2_st=?, unialt3_st=?, unialt4_st=?, factor1_st=?, factor2_st=?, factor3_st=?, factor4_st=?, tipoiva_st=?, venta_st=?, compra_st=?, esquema_st=?, usuarioact=?, fechaact=?, kilaje=?  WHERE codigo_st=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, alias_st);
					insert.setString(2, descrip_st);
					insert.setString(3, descri2_st);
					insert.setDouble(4, cost_pp_st.doubleValue());
					insert.setDouble(5, precipp_st.doubleValue());
					insert.setDouble(6, cost_uc_st.doubleValue());
					insert.setDouble(7, ultcomp_st.doubleValue());
					insert.setDouble(8, cost_re_st.doubleValue());
					insert.setDouble(9, reposic_st.doubleValue());
					if (nom_com_st != null
							&& !nom_com_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(10, new BigDecimal(nom_com_st));
					} else {
						insert.setBigDecimal(10, null);
					}
					if (grupo_st != null && !grupo_st.equalsIgnoreCase("")) {
						insert.setBigDecimal(11, new BigDecimal(grupo_st));
					} else {
						insert.setBigDecimal(11, null);
					}
					insert.setDouble(12, cantmin_st.doubleValue());
					if (unimed_st != null
							&& !unimed_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(13, new BigDecimal(unimed_st));
					} else {
						insert.setBigDecimal(13, null);
					}
					insert.setDouble(14, bonific_st.doubleValue());
					insert.setDouble(15, impint_st.doubleValue());
					insert.setDouble(16, impcant_st.doubleValue());
					if (cuencom_st != null && !cuencom_st.equalsIgnoreCase("")) {
						insert.setBigDecimal(17, new BigDecimal(cuencom_st));
					} else {
						insert.setBigDecimal(17, null);
					}
					if (cuenven_st != null && !cuenven_st.equalsIgnoreCase("")) {
						insert.setBigDecimal(18, new BigDecimal(cuenven_st));
					} else {
						insert.setBigDecimal(18, null);
					}
					if (cuenve2_st != null && !cuenve2_st.equalsIgnoreCase("")) {
						insert.setBigDecimal(19, new BigDecimal(cuenve2_st));
					} else {
						insert.setBigDecimal(19, null);
					}
					if (cuencos_st != null && !cuencos_st.equalsIgnoreCase("")) {
						insert.setBigDecimal(20, new BigDecimal(cuencos_st));
					} else {
						insert.setBigDecimal(20, null);
					}
					if (cuenaju_st != null && !cuenaju_st.equalsIgnoreCase("")) {
						insert.setBigDecimal(21, new BigDecimal(cuenaju_st));
					} else {
						insert.setBigDecimal(21, null);
					}
					insert.setString(22, inventa_st);
					if (proveed_st != null
							&& !proveed_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(23, new BigDecimal(proveed_st));
					} else {
						insert.setBigDecimal(23, null);
					}
					insert.setString(24, provart_st);
					insert.setString(25, id_indi_st);
					insert.setString(26, despa_st);
					insert.setString(27, marca_st);
					insert.setDouble(28, cafecga_st.doubleValue());
					if (unialt1_st != null
							&& !unialt1_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(29, new BigDecimal(unialt1_st));
					} else {
						insert.setBigDecimal(29, null);
					}
					if (unialt2_st != null
							&& !unialt2_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(30, new BigDecimal(unialt2_st));
					} else {
						insert.setBigDecimal(30, null);
					}
					if (unialt3_st != null
							&& !unialt3_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(31, new BigDecimal(unialt3_st));
					} else {
						insert.setBigDecimal(31, null);
					}
					if (unialt4_st != null
							&& !unialt4_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(32, new BigDecimal(unialt4_st));
					} else {
						insert.setBigDecimal(32, null);
					}
					insert.setDouble(33, factor1_st.doubleValue());
					insert.setDouble(34, factor2_st.doubleValue());
					insert.setDouble(35, factor3_st.doubleValue());
					insert.setDouble(36, factor4_st.doubleValue());
					if (tipoiva_st != null
							&& !tipoiva_st.equalsIgnoreCase("null")) {
						insert.setBigDecimal(37, new BigDecimal(tipoiva_st));
					} else {
						insert.setBigDecimal(37, null);
					}
					insert.setString(38, venta_st);
					insert.setString(39, compra_st);
					insert.setBigDecimal(40, esquema_st);
					insert.setString(41, usuarioact);
					insert.setTimestamp(42, fechaact);
					insert.setDouble(43, kilaje.doubleValue());

					insert.setString(44, codigo_st);
					insert.setBigDecimal(45, idempresa);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String StockstockUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log.error("Error excepcion public String StockstockUpdate(.....)"
					+ ex);
		}
		return salida;
	}

	// ESTO SE HIZO PARA TRAER EL LOV DE MEDIDAS
	public List getMedidasLOV(String ocurrencia, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT * FROM stockmedidas WHERE (UPPER(descrip_md) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%') AND idempresa= "
				+ idempresa.toString() + " ORDER BY codigo_md";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ESTO SE HIZO PARA TRAER LAS MONEDAS
	public List getMonedasLOV(String ocurrencia, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT * FROM globalmonedas WHERE (UPPER(moneda) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%') AND idempresa= "
				+ idempresa.toString() + " ORDER BY idmoneda";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ESTO SE HIZO PARA TRAER LOS PROVEEDORES
	public List getProveedoresLOV(String ocurrencia, BigDecimal idempresa)
			throws EJBException {
		String cQuery = "SELECT * FROM proveedoproveed WHERE (UPPER(razon_social) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%') AND idempresa =  "
				+ idempresa.toString() + " ORDER BY idproveedor";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// Verifica la cantidad de un determinado articulo en un deposito en
	// particular.
	// TODO: UNIFICAR 20061020 EJV
	// ATENCION !!!!:
	// --------------------------------------------------------------------
	// Metodos:...........................................................................
	// getExisteArticuloDeposito -
	// .......................................................
	// getValorSecuencia
	// -----------------------------------------------------------------------------------
	// duplicado en ProveedoresBean, solucion provisoria, para poder
	// generar transaccion con una sola coneccion.
	/*
	 * public boolean getExisteArticuloDeposito(String articulo, BigDecimal
	 * deposito, BigDecimal idempresa) throws EJBException { PreparedStatement
	 * statement; String qExiste = "SELECT count(1) FROM stockstockbis WHERE
	 * articu_sb=? AND deposi_sb=? AND idempresa=?"; ResultSet rsExiste; boolean
	 * existe = false; try { statement = dbconn.prepareStatement(qExiste);
	 * statement.setString(1, articulo); statement.setBigDecimal(2, deposito);
	 * statement.setBigDecimal(3, idempresa); rsExiste =
	 * statement.executeQuery(); if (rsExiste.next()) { if
	 * (rsExiste.getBigDecimal(1).compareTo(new BigDecimal("0")) > 0) existe =
	 * true; } } catch (Exception e) { log
	 * .error("getExisteArticuloDeposito(String articulo, BigDecimal deposito,
	 * BigDecimal idempresa): " + e); }
	 * 
	 * return existe; }
	 */
	// TODO: UNIFICAR 20061020 EJV
	// ATENCION !!!!:
	// --------------------------------------------------------------------
	// Metodos:...........................................................................
	// getExisteArticuloDeposito -
	// .......................................................
	// getValorSecuencia
	// -----------------------------------------------------------------------------------
	// duplicado en ProveedoresBean, solucion provisoria, para poder
	// generar transaccion con una sola coneccion.
	/*
	 * public BigDecimal getValorSequencia(String sequencia) throws EJBException
	 * { PreparedStatement statement; String qSeq =
	 * "select currval(?::regclass)"; ResultSet rsSeq; BigDecimal id = null; try
	 * { statement = dbconn.prepareStatement(qSeq); statement.setString(1,
	 * sequencia); rsSeq = statement.executeQuery(); if (rsSeq.next()) { id =
	 * rsSeq.getBigDecimal(1); } } catch (Exception e) {
	 * log.error("getValorSequencia(String sequencia): " + e); }
	 * 
	 * return id; }
	 */
	// --
	/**
	 * Metodos para la entidad: vstockTotalDeposito Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Tue Oct 24 16:08:01 GMT-03:00 2006
	 */
	// para todo (ordena por el segundo campo por defecto)
	public List getVstockTotalDepositoAll(long limit, long offset,
			BigDecimal idempresa, String usuario) throws EJBException {
		String cQuery = ""
				+ "SELECT codigo_st,descrip_st,codigo_dt,descrip_dt,disponible,reservado, existencia  "
				+ "  FROM VSTOCKTOTALDEPOSITO where idempresa = "
				+ idempresa.toString();
		if (hasDepositosAsociados(usuario, idempresa)) {
			cQuery += " and codigo_dt in "
					+ getQueryDepositosAsociados(usuario, idempresa);
		}
		cQuery += " ORDER BY 1  LIMIT " + limit + " OFFSET  " + offset + ";";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getVstockTotalDepositoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa, String usuario)
			throws EJBException {
		String cQuery = ""
				+ "SELECT  codigo_st,descrip_st,codigo_dt,descrip_dt,disponible,reservado, existencia"
				+ "  FROM VSTOCKTOTALDEPOSITO "
				+ " WHERE ((codigo_st::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(descrip_st) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%') AND idempresa = "
				+ idempresa.toString();
		if (hasDepositosAsociados(usuario, idempresa)) {
			cQuery += " and codigo_dt in "
					+ getQueryDepositosAsociados(usuario, idempresa);
		}

		cQuery += " ORDER BY 1  LIMIT " + limit + " OFFSET  " + offset + ";";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getVstockTotalDepositoPK(String codigo_st, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT  codigo_st,descrip_st,codigo_dt,descrip_dt,disponible,reservado, existencia "
				+ "  FROM VSTOCKTOTALDEPOSITO WHERE codigo_st='" + codigo_st
				+ "' AND idempresa=" + idempresa.toString();

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public String[] callStockMovInternoCreate(Timestamp fechamov,
			String tipomov, BigDecimal num_cnt, BigDecimal sucursal,
			String tipo, String observaciones, Hashtable htArticulos,
			BigDecimal idcontadorcomprobante, Hashtable htSerieDespacho,
			String usuarioalt, BigDecimal idempresa) throws EJBException,
			SQLException {

		String[] resultado = null;

		dbconn.setAutoCommit(false);

		try {

			resultado = stockMovInternoCreate(fechamov, tipomov, num_cnt,
					sucursal, tipo, observaciones, htArticulos,
					idcontadorcomprobante, htSerieDespacho, dbconn, usuarioalt,
					idempresa);

		} catch (Exception e) {

			log.error("callStockMovInternoCreate(): " + e);

		}

		if (resultado[0] != null && resultado[0].equalsIgnoreCase("OK")) {
			dbconn.commit();
		} else {
			dbconn.rollback();
		}

		dbconn.setAutoCommit(true);
		return resultado;

	}

	/**
	 * Metodo para la entidad: stockstock - stockhis - stockstockbis -
	 * stockmovstock; Copyrigth(r) sysWarp S.R.L. Fecha de creacion: Thu Aug 23
	 * 16:55:00 GMT-03:00 2006
	 * 
	 */

	public  String[] stockMovInternoCreate(Timestamp fechamov,
			String tipomov, BigDecimal num_cnt, BigDecimal sucursal,
			String tipo, String observaciones, Hashtable htArticulos,
			BigDecimal idcontadorcomprobante, Hashtable htSerieDespacho,
			Connection conn, String usuarioalt, BigDecimal idempresa)
			throws EJBException, SQLException {
		/*
		 * Observaciones : CEP 25/01/2007 cambios para poder pasar stock en
		 * negativo. Solicitud oscura.
		 */

		String salida = "OK";
		BigDecimal comprob_ms = BigDecimal.valueOf(-1);
		BigDecimal nrointerno_ms = BigDecimal.valueOf(-1);
		Enumeration en;
		String tipomov_ms = ""; // TODO
		String sistema_ms = "S"; // TODO
		java.util.Date date = new java.util.Date();
		BigDecimal cantArtDep;
		BigDecimal remito_ms = new BigDecimal(0);
		boolean isFirst;
		/*
		 * 20070604 EJV Lineas detalle por Remito.
		 */
		int maxLineasXremito = 0;
		int indice = 0;
		Hashtable htArticulosImagen = new Hashtable(htArticulos);
		Hashtable htArticulosPaginados = null;
		String[] resultado = new String[2];

		/*
		 * 20071203 EJV Comprobantes internos - sucursal
		 */

		BigDecimal remito_interno_ms = new BigDecimal(-1);
		BigDecimal sucu_ms = new BigDecimal(-1);

		// EJV 20090316
		BigDecimal canti_sb = new BigDecimal(0);
		BigDecimal pedid_sb = new BigDecimal(0);

		Calendar cal = new GregorianCalendar();

		log.info("htArticulos" + htArticulos);
		log.info("htArticulosImagen" + htArticulosImagen);
		log.info("tipomov: " + tipomov);
		try {
			// TODO: REALIZAR TODAS LAS VALIDACIONES
			// Pasos del movimiento ...
			// 1.0 - Generar movimiento salida .... stockmovstock
			// 1.1 - Generar movimiento entrada .... stockmovstock
			// 2.0 - Generar movimiento entrada .... stockbis
			// 2.1 - Generar movimiento salida .... stockbis
			// 3.0 - Generar movimiento historico .... stockhis
			// 3.1 - Generar movimiento historico .... stockhis

			// EJV - 20090316
			// dbconn.setAutoCommit(false);

			/*
			 * 20070604 EJV Lineas detalle por Remito.
			 */
			try {
				maxLineasXremito = Integer.parseInt(GeneralBean
						.getValorSetupVariables("maxLineasXremito", idempresa,
								conn));
			} catch (Exception e) {
				salida = "Imposible recuperar lineas detalle remito.";
				log.error("maxLineasXremito: " + e);
			}
			/*
			 * */

			sucu_ms = GeneralBean.getSucursalComprobante(idcontadorcomprobante,
					idempresa, conn);

			if (sucu_ms == null || sucu_ms.longValue() < 0) {
				salida = "Verificar sucursal definida para el contador de remitos del puesto.";
			}

			if (htArticulos != null && !htArticulos.isEmpty()) {
				/*
				 * 20070604 EJV Lineas detalle por Remito.
				 */
				while (salida.equalsIgnoreCase("OK")) {
					indice = 0;
					htArticulosPaginados = new Hashtable();
					Enumeration enu = htArticulosImagen.keys();
					while (enu.hasMoreElements() && indice < maxLineasXremito) {
						Object elemento = enu.nextElement();
						htArticulosPaginados.put(elemento, htArticulosImagen
								.get(elemento));
						htArticulosImagen.remove(elemento);
						indice++;
					}

					if (htArticulosPaginados.isEmpty())
						break;
					en = htArticulosPaginados.keys();

					/*
					 * */

					comprob_ms = new BigDecimal(-1);
					nrointerno_ms = new BigDecimal(-1);
					tipomov_ms = ""; // TODO
					isFirst = true;
					remito_ms = new BigDecimal(0);

					/*
					 * TODO:
					 * .......................................................
					 * Previsto para recuperar contador de comprobante ...
					 * (REMITOS ). momentaneamente validar contra tipomov (RO:
					 * Remito Oficial), es necesario analizar.
					 */

					if (tipomov != null && tipomov.equalsIgnoreCase("RO"))
						remito_ms = GeneralBean.getContadorComprobante(num_cnt,
								sucursal, tipo, idempresa, conn);

					if (remito_ms.compareTo(new BigDecimal(-1)) > 0) {

						while (en.hasMoreElements()) {

							if (isFirst) {

								comprob_ms = GeneralBean.getContador(
										new BigDecimal(4), idempresa, conn);

								remito_interno_ms = GeneralBean.getContador(
										idcontadorcomprobante, idempresa, conn);

							}
							isFirst = false;
							String key = (String) en.nextElement();
							String[] datosArticulo = (String[]) htArticulosPaginados
									.get(key);
							String articulo = datosArticulo[0];
							BigDecimal cantArtMov = new BigDecimal(
									datosArticulo[4]);
							BigDecimal origen = new BigDecimal(datosArticulo[5]);
							BigDecimal destino = new BigDecimal(
									datosArticulo[6]);
							BigDecimal costo = new BigDecimal(datosArticulo[7]);

							String serializable = datosArticulo[8];
							String despacho = datosArticulo[9];

							/*
							 * 0 codigo_st: 1 alias_st: 2 descrip_st: 3
							 * descri2_st: 4 cantidad: 5 origen: 6 destino: 7
							 * cost_uc_st:
							 */

							// RECUPERAR DATOS ARTICULO
							// List listaArticulo = getStockstockPK(articulo);
							// String[] das = (String[]) listaArticulo
							// .get(0);
							/*
							 * MODIFICA ORIGEN
							 */

							if (GeneralBean.getExisteArticuloDeposito(articulo,
									origen, idempresa, conn)) {
								tipomov_ms = "S";
								cantArtDep = GeneralBean
										.getCantidadArticuloDeposito(articulo,
												origen, idempresa, conn);
								// CEP: 25/01/2007 : primer arreglo
								if ((cantArtDep.doubleValue() >= cantArtMov
										.doubleValue())
										|| GeneralBean.hasStockNegativo(
												idempresa, conn)) {

									/*
									 * nrointerno_ms : sistema_ms : tipomov_ms :
									 * comprob_ms : fecha_ms : articu_ms :
									 * canti_ms : moneda_ms : cambio_ms :
									 * venta_ms : costo_ms : tipoaux_ms :
									 * destino_ms : comis_ms : remito_ms :
									 * impint_ms : impifl_ms : impica_ms :
									 * prelis_ms : unidad_ms : merma_ms :
									 * saldo_ms : medida_ms : observaciones :
									 * usuarioalt : usuarioact : fechaalt :
									 * fechaact :
									 */

									// nrointerno_ms =
									// getValorSequencia("seq_stockmovstock");
									// 20061101 - nrointerno_ms permite
									// duplicados
									nrointerno_ms = GeneralBean.getContador(
											new BigDecimal(5), idempresa, conn);
									salida = stockMovStockCreate(nrointerno_ms,
											sistema_ms, tipomov_ms, comprob_ms,
											fechamov, articulo, cantArtMov,
											new BigDecimal("1"),
											new Double("1"), new Double("0"),
											costo, "C", "", new Double("0"),
											remito_ms, new Double("0"),
											new Double("0"), new Double("0"),
											new Double("0"),
											new BigDecimal("0"),
											new Double("0"), new Double("0"),
											new BigDecimal("0"), observaciones,
											remito_interno_ms, sucu_ms, conn,
											usuarioalt, idempresa);

									if (!salida.equalsIgnoreCase("OK"))
										break;

									// nrointerno_ms =
									// getValorSequencia("seq_stockmovstock");
									// 20061101 - nrointerno_ms permite
									// duplicados

									cantArtDep = GeneralBean
											.getCantidadArticuloDeposito(
													articulo, origen,
													idempresa, conn);

									// Actualiza stockbis
									// Descuenta del origen

									/*
									 * articu_sb : deposi_sb : canti_sb :
									 * serie_sb : despa_sb : pedid_sb :
									 * usuarioalt : usuarioact : fechaalt :
									 * fechaact :
									 */

									salida = stockStockBisUpdate(articulo,
											origen, cantArtMov.negate(), "",
											"", new BigDecimal("0"), conn,
											usuarioalt, idempresa);

									if (!salida.equalsIgnoreCase("OK"))
										break;
									//

									/*
									 * nromov_sh : articu_sh : deposi_sh :
									 * serie_sh : despa_sh : canti_sh :
									 * estamp1_sh : estamp2_sh : aduana_sh :
									 * usuarioalt : usuarioact : fechaalt :
									 * fechaact :
									 */

									salida = stockHisCreate(nrointerno_ms,
											articulo, origen, "", "",
											cantArtMov, "", "", "", conn,
											usuarioalt, idempresa);

									if (!salida.equalsIgnoreCase("OK"))
										break;

									// 20100120 - EJV Generar egreso en
									// stockseriedeposito

									// ejvseriedespacho
									// ---------------------------------------------------------------------
									// 20100125 - EJV -->
									// ---------------------------------------------------------------------
									if (serializable.equalsIgnoreCase("S")
											|| despacho.equalsIgnoreCase("S")) {

										String[][] serieDespacho = (String[][]) htSerieDespacho
												.get(key);
										log.info("1");
										salida = stockSerieDespachoSalida(
												nrointerno_ms, articulo,
												origen, serializable, despacho,
												serieDespacho, conn, idempresa,
												usuarioalt);

										if (!salida.equalsIgnoreCase("OK"))
											break;

									}

									// ---------------------------------------------------------------------
									// 20100125 - EJV <--
									// ---------------------------------------------------------------------

									/*
									 * ==========================================
									 * ============= MODIFICA DESTINO
									 * ============
									 * ==============================
									 * =============
									 */

									tipomov_ms = "E";

									/*
									 * nrointerno_ms : sistema_ms : tipomov_ms :
									 * comprob_ms : fecha_ms : articu_ms :
									 * canti_ms : moneda_ms : cambio_ms :
									 * venta_ms : costo_ms : tipoaux_ms :
									 * destino_ms : comis_ms : remito_ms :
									 * impint_ms : impifl_ms : impica_ms :
									 * prelis_ms : unidad_ms : merma_ms :
									 * saldo_ms : medida_ms : observaciones :
									 * usuarioalt : usuarioact : fechaalt :
									 * fechaact :
									 */

									// nrointerno_ms =
									// getValorSequencia("seq_stockmovstock");
									// 20061101 - nrointerno_ms permite
									// duplicados
									nrointerno_ms = GeneralBean.getContador(
											new BigDecimal(5), idempresa, conn);
									salida = stockMovStockCreate(nrointerno_ms,
											sistema_ms, tipomov_ms, comprob_ms,
											fechamov, articulo, cantArtMov,
											new BigDecimal("1"),
											new Double("1"), new Double("0"),
											costo, "C", "", new Double("0"),
											remito_ms, new Double("0"),
											new Double("0"), new Double("0"),
											new Double("0"),
											new BigDecimal("0"),
											new Double("0"), new Double("0"),
											new BigDecimal("0"), observaciones,
											remito_interno_ms, sucu_ms, conn,
											usuarioalt, idempresa);
									if (!salida.equalsIgnoreCase("OK"))
										break;
									// nrointerno_ms =
									// getValorSequencia("seq_stockmovstock");
									// 20061101 - nrointerno_ms permite
									// duplicados
									// Actualiza el destino ... o lo crea

									if (GeneralBean.getExisteArticuloDeposito(
											articulo, destino, idempresa, conn))

										/*
										 * ACTUALIZA ... articu_sb : deposi_sb :
										 * canti_sb : serie_sb : despa_sb :
										 * pedid_sb : usuarioalt : usuarioact :
										 * fechaalt : fechaact :
										 */
										salida = stockStockBisUpdate(articulo,
												destino, cantArtMov, "", "",
												new BigDecimal("0"), conn,
												usuarioalt, idempresa);
									else

										// CREA ...
										salida = stockStockBisCreate(articulo,
												destino, cantArtMov, "", "",
												new BigDecimal("0"), conn,
												usuarioalt, idempresa);

									if (!salida.equalsIgnoreCase("OK"))
										break;
									/*
									 * nromov_sh : articu_sh : deposi_sh :
									 * serie_sh : despa_sh : canti_sh :
									 * estamp1_sh : estamp2_sh : aduana_sh :
									 * usuarioalt : usuarioact : fechaalt :
									 * fechaact :
									 */

									salida = stockHisCreate(nrointerno_ms,
											articulo, destino, "", "",
											cantArtMov, "", "", "", conn,
											usuarioalt, idempresa);
									if (!salida.equalsIgnoreCase("OK"))
										break;

									// 20100126 - EJV Generar ingreso en
									// stockseriedeposito

									// SERIES y DESPACHOS
									// 20100118 - EJV -->
									if (serializable.equalsIgnoreCase("S")
											|| despacho.equalsIgnoreCase("S")) {

										String[][] serieDespacho = (String[][]) htSerieDespacho
												.get(key);

										salida = stockSerieDespachoEntrada(
												nrointerno_ms, articulo,
												destino, serializable,
												despacho, serieDespacho, conn,
												idempresa, usuarioalt);

									}
									// 20100118 - EJV <--

									if (!salida.equalsIgnoreCase("OK"))
										break;

								} else {
									salida = "Cantidad insuficiente para articulo "
											+ articulo
											+ " en deposito origen ( "
											+ origen
											+ " ).";
									break;
								}

							} else {
								salida = "No existe stock en deposito ( "
										+ origen + " ) para articulo: "
										+ articulo;
								break;
							}
						}
					} else {
						salida = "Error al recuperar Nro. comprobante.";
					}

					resultado[0] = salida;
					resultado[1] = resultado[1] != null ? resultado[1] + "-"
							+ comprob_ms.toString() : comprob_ms.toString();
				}//
			}
		} catch (Exception e) {
			// TODO: handle exception
			salida = "E-1000: Ocurrio Excepcion Mientras Se Actualizaba Stock.";
			log.error("stockMovInternoCreate(): " + e);
		}
		if (!salida.equalsIgnoreCase("OK")) {
			// EJV - 20090316
			// dbconn.rollback();
		} else {
			htArticulos = null;
		}

		htArticulosImagen = null;

		// EJV - 20090316
		// dbconn.setAutoCommit(true);

		return resultado;
	}

	/**
	 * IMPLEMENTACION DE MOVIMIENTOS POR DEFINICION DE ESQUEMAS ...............
	 * FECHA: 20071214 ........................................................
	 * AUTOR: EJV .............................................................
	 * 
	 */

	public String[] callStockMovDesarmadoProductosEsquema(BigDecimal idesquema,
			String codigo_st, BigDecimal codigo_dt, BigDecimal cantidad,
			BigDecimal idmotivodesarma, String observaciones, int recursivo,
			int ejercicioactivo, BigDecimal idcontadorcomprobante,
			String sistema_ms, BigDecimal idempresa, String usuarioalt)
			throws EJBException, SQLException {

		String salida = "";
		String[] resultado = null;
		String[] nrosinternos = null;
		BigDecimal idtransacciondesarmado = new BigDecimal(-1);

		dbconn.setAutoCommit(false);

		try {

			resultado = stockMovDesarmadoProductosEsquema(idesquema, codigo_st,
					codigo_dt, cantidad, idmotivodesarma, observaciones,
					recursivo, ejercicioactivo, idcontadorcomprobante,
					sistema_ms, 1, idempresa, usuarioalt);

			salida = resultado[0];
			nrosinternos = resultado[1].split("-");

			if (salida.equalsIgnoreCase("OK")) {

				idtransacciondesarmado = GeneralBean.getNextValorSequencia(
						"seq_idtransacciondesarmado", dbconn);

				stockDesarmadoLogCreate(idtransacciondesarmado, idesquema,
						nrosinternos, recursivo, cantidad, idmotivodesarma,
						idempresa, usuarioalt);

			}

		} catch (Exception e) {
			salida = "Error inesperado en callStockMovDesarmadoProductosEsquema.";
			log.error("callStockMovDesarmadoProductosEsquema: " + e);
		}

		if (!salida.equalsIgnoreCase("OK"))
			dbconn.rollback();
		else
			dbconn.commit();

		dbconn.setAutoCommit(true);

		return resultado;
	}

	// -----------------------------------------------------------------------

	public String[] callAnulaOrdenProduccion(BigDecimal idop,
			BigDecimal idesquema, String codigo_st, BigDecimal codigo_dt,
			BigDecimal cantidad, BigDecimal idmotivodesarma,
			String observaciones, int recursivo, int ejercicioactivo,
			BigDecimal idcontadorcomprobante, String sistema_ms,
			BigDecimal idempresa, String usuarioalt) throws EJBException,
			SQLException {

		String salida = "";
		String[] resultado = null;
		String[] nrosinternos = null;
		BigDecimal idtransacciondesarmado = new BigDecimal(-1);

		dbconn.setAutoCommit(false);

		try {

			salida = ProduccionBean.produccionMovProduAnulaOp(idop, idempresa,
					usuarioalt, dbconn);

			if (salida.equalsIgnoreCase("OK")) {

				if (cantidad.longValue() > 0) {

					resultado = stockMovDesarmadoProductosEsquema(idesquema,
							codigo_st, codigo_dt, cantidad, idmotivodesarma,
							observaciones, recursivo, ejercicioactivo,
							idcontadorcomprobante, sistema_ms, 1, idempresa,
							usuarioalt);

					salida = resultado[0];
					nrosinternos = resultado[1].split("-");

					if (salida.equalsIgnoreCase("OK")) {

						idtransacciondesarmado = GeneralBean
								.getNextValorSequencia(
										"seq_idtransacciondesarmado", dbconn);

						stockDesarmadoLogCreate(idtransacciondesarmado,
								idesquema, nrosinternos, recursivo, cantidad,
								idmotivodesarma, idempresa, usuarioalt);
					}

				} else {
					resultado = new String[] { salida, "-1" };
				}

			} else {
				resultado = new String[] { salida, "-1" };
			}

		} catch (Exception e) {
			salida = "Error inesperado en callAnulaOrdenProduccion.";
			log.error("callAnulaOrdenProduccion: " + e);
		}

		if (!salida.equalsIgnoreCase("OK"))
			dbconn.rollback();
		else
			dbconn.commit();

		dbconn.setAutoCommit(true);

		return resultado;
	}

	/**
	 * */

	public String stockDesarmadoLogCreate(BigDecimal idtransacciondesarmado,
			BigDecimal idesquema, String[] nrosinternos, int recursivo,
			BigDecimal cantidad, BigDecimal idmotivodesarma,
			BigDecimal idempresa, String usuarioalt) throws EJBException,
			SQLException {
		String salida = "OK";
		BigDecimal nrointerno_ms = new BigDecimal(0);
		Connection connLog = null;
		// validaciones de datos:
		// 1. nulidad de campos
		if (idtransacciondesarmado == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtransaccion ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
		// 2. sin nada desde la pagina

		// fin validaciones

		try {

			// Es necesario crear una coneccion independiente, para manejar
			// transaccion tambien independiente. La misma se crea cada vez que
			// es llamado el metodo que genera datos de log.
			connLog = DriverManager.getConnection(url, usuario, clave);
			if (salida.equalsIgnoreCase("OK")) {
				String ins = ""
						+ "INSERT INTO stockdesarmadolog"
						+ "        (idtransacciondesarmado, idesquema, nrointerno_ms, recursivo, cantidad, idmotivodesarma, idempresa, usuarioalt ) "
						+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = connLog.prepareStatement(ins);
				// seteo de campos:
				for (int j = 0; j < nrosinternos.length; j++) {
					nrointerno_ms = new BigDecimal(nrosinternos[j]);
					insert.setBigDecimal(1, idtransacciondesarmado);
					insert.setBigDecimal(2, idesquema);
					insert.setBigDecimal(3, nrointerno_ms);
					insert.setInt(4, recursivo);
					insert.setBigDecimal(5, cantidad);
					insert.setBigDecimal(6, idmotivodesarma);
					insert.setBigDecimal(7, idempresa);
					insert.setString(8, usuarioalt);
					int n = insert.executeUpdate();
					if (n != 1) {
						salida = "Error generando log desarmado ... ";

					}
				}

			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String stockDesarmadoLogCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String stockDesarmadoLogCreate(.....)"
							+ ex);
		}
		connLog.close();
		connLog = null;
		return salida;

	}

	/**
	 * */

	public String[] stockMovDesarmadoProductosEsquema(BigDecimal idesquema,
			String codigo_st, BigDecimal codigo_dt, BigDecimal cantidad,
			BigDecimal idmotivodesarma, String observaciones, int recursivo,
			int ejercicioactivo, BigDecimal idcontadorcomprobante,
			String sistema_ms, int nivel, BigDecimal idempresa,
			String usuarioalt) throws EJBException, SQLException {

		String salida = "OK";
		String nrosinternos = "";
		String[] retorno = new String[3];
		// dbconn.setAutoCommit(false);

		try {

			BigDecimal codigo_anexo = new BigDecimal(-1);
			String razon_social = "DESARMADO";
			String domicilio = "DESARMADO";
			String codigo_postal = "0000";
			BigDecimal idlocalidad = new BigDecimal(-1);
			BigDecimal idprovincia = new BigDecimal(-1);
			String cuit = "";
			String iibb = "";
			Timestamp fechamov = new Timestamp(Calendar.getInstance()
					.getTimeInMillis());
			BigDecimal remito_ms = new BigDecimal(-1);
			String tipomov = "S";
			BigDecimal sucursal = new BigDecimal(-1);
			String tipo = "O";
			boolean remitopendiente = true;

			String[] resultado = new String[3];
			List listArticulos = null;
			Iterator iterListArticulos;
			String[] datosArticulo = null;
			// int ejercicioactivo = 0;
			// String observaciones = "";
			Hashtable htArticulos = new Hashtable();
			Hashtable htCuentas = new Hashtable();

			// BigDecimal idcontadorcomprobante = new BigDecimal(-1);
			boolean handAutocommit = false;
			String obs = "[D-N:(" + nivel + ")]" + observaciones;

			log.info("INICIA NIVEL: " + nivel);

			if (GeneralBean.getCantidadArticuloDeposito(codigo_st, codigo_dt,
					idempresa, dbconn).compareTo(cantidad) >= 0) {

				if (recursivo == 1) {

					listArticulos = getStockEsquemasAnidados(idesquema,
							idempresa);

					if (listArticulos != null && !listArticulos.isEmpty()) {

						iterListArticulos = listArticulos.iterator();
						while (iterListArticulos.hasNext()) {

							datosArticulo = (String[]) iterListArticulos.next();
							BigDecimal idesquemaRecur = new BigDecimal(
									datosArticulo[0]);
							BigDecimal cantidadRecur = new BigDecimal(
									datosArticulo[1]).multiply(cantidad);
							String codigo_stRecur = getCodigoStProdFinalEsquema(
									idesquemaRecur, idempresa);

							/*
							 * PASO 1 - LLAMADA RECURSION.
							 */
							resultado = stockMovDesarmadoProductosEsquema(
									idesquemaRecur, codigo_stRecur, codigo_dt,
									cantidadRecur, idmotivodesarma,
									observaciones, recursivo, ejercicioactivo,
									idcontadorcomprobante, sistema_ms,
									nivel + 1, idempresa, usuarioalt);

							salida = resultado[0];

							if (salida.equalsIgnoreCase("OK"))
								break;

							nrosinternos = nrosinternos.equals("") ? resultado[1]
									: nrosinternos + "-" + resultado[1];
						}

					}

					listArticulos = null;
					datosArticulo = null;
					htArticulos = new Hashtable();

				}

				if (salida.equalsIgnoreCase("OK")) {

					listArticulos = getStockEsquemaArticulosDesarma(codigo_st,
							idesquema, true, idempresa);

					if (listArticulos != null && !listArticulos.isEmpty()) {

						datosArticulo = (String[]) listArticulos.get(0);

						datosArticulo[9] = codigo_dt.toString();
						datosArticulo[10] = cantidad.toString();
						datosArticulo[11] = gb.getNumeroFormateado((Float
								.parseFloat(datosArticulo[10]) * Float
								.parseFloat(datosArticulo[5])), 10, 2);

						htArticulos.put(datosArticulo[0], datosArticulo);

						// TODO: 20100121 - EJV Se fuerza el valor del ht para
						// que no haya error de compilacion. Se aplica a la
						// llamada de movimientos de salida y a la llamada de
						// movimientos de entrada. Es necesario desarrollar
						// logica para desarmado.

						Hashtable htSerieDespacho = new Hashtable();

						resultado = stockMovSalidaCreate(codigo_anexo,
								razon_social, domicilio, codigo_postal,
								idlocalidad, idprovincia, cuit, iibb,
								sistema_ms, fechamov, remito_ms, tipomov,
								sucursal, tipo, remitopendiente,
								ejercicioactivo, obs, htArticulos, htCuentas,
								idcontadorcomprobante, handAutocommit,
								htSerieDespacho, usuarioalt, idempresa);

						if (resultado[0].equalsIgnoreCase("OK")) {

							nrosinternos = nrosinternos.equals("") ? resultado[1]
									: nrosinternos + "-" + resultado[1];

							listArticulos = null;
							datosArticulo = null;
							htArticulos = new Hashtable();

							listArticulos = getStockEsquemaArtEsqDesarma(
									idesquema, idempresa);

							if (listArticulos != null
									&& !listArticulos.isEmpty()) {

								iterListArticulos = listArticulos.iterator();
								while (iterListArticulos.hasNext()) {
									datosArticulo = (String[]) iterListArticulos
											.next();
									/*
									 * Si recursivo vale 1 y el articulo es el
									 * correspondiente a un producto final de
									 * algun esquema anidado no se ejecuta
									 * movimiento, esta accion se realizo en el
									 * paso anterior "PASO 1 - LLAMADA
									 * RECURSION". Sino, si recursivo vale 0, se
									 * trata a dicho "ESQUEMA" como un articulo
									 * mas.
									 */
									if (recursivo == 1
											&& datosArticulo[17]
													.equalsIgnoreCase("E"))
										continue;
									datosArticulo[9] = codigo_dt.toString();
									datosArticulo[10] = (cantidad
											.multiply(new BigDecimal(
													datosArticulo[18])))
											.toString();
									datosArticulo[11] = gb
											.getNumeroFormateado(
													(Float
															.parseFloat(datosArticulo[10]) * Float
															.parseFloat(datosArticulo[5])),
													10, 2);
									htArticulos.put(datosArticulo[0],
											datosArticulo);

								}

								// Hashtable htSerieDespacho = new Hashtable();

								resultado = stockMovEntradaCreate(codigo_anexo,
										razon_social, domicilio, codigo_postal,
										idlocalidad, idprovincia, cuit, iibb,
										sistema_ms, fechamov, remito_ms,
										tipomov, sucursal, tipo,
										remitopendiente, ejercicioactivo, obs,
										htArticulos, htCuentas,
										idcontadorcomprobante, handAutocommit,
										htSerieDespacho, usuarioalt, idempresa);

								salida = resultado[0];

								if (salida.equalsIgnoreCase("OK"))
									nrosinternos = nrosinternos.equals("") ? resultado[1]
											: nrosinternos + "-" + resultado[1];

							} else
								salida = "No se puede desarmar, no hay articulos reutilizables.(NIVEL: "
										+ nivel + ")";

						} else
							salida = resultado[0] + "(NIVEL: " + nivel + ")";

					} else
						salida = "Imposible capturar datos articulo a desarmar.(NIVEL: "
								+ nivel + ")";

				} else
					// es redundante salida ya tiene asignado este valor.
					salida = resultado[0];

			} else
				salida = "Imposible desarmar, cantidad insuficiente.(NIVEL: "
						+ nivel + ")";

		} catch (Exception e) {

			salida = "Error al desarmar esquema. (NIVEL: " + nivel + ")";
			log.error("stockMovDesarmadoProductosEsquema(...):" + e);
		}

		log.info("FINALIZA NIVEL: " + nivel);

		retorno[0] = salida;
		retorno[1] = nrosinternos;

		return retorno;
	}

	/**
	 * */

	public List getStockEsquemaArticulosDesarma(String codigo_st,
			BigDecimal idesquema, boolean isproductofinal, BigDecimal idempresa)
			throws EJBException {

		String operador = isproductofinal ? " = " : " <> ";
		String cQuery = ""
				+ "SELECT st.codigo_st, st.alias_st, st.descrip_st, st.descri2_st, "
				+ "       st.cost_re_st, st.cost_uc_st, st.cost_pp_st, st.precipp_st, st.ultcomp_st, "
				+ "       -1 as deposito, 0 as cantidad, 0 as total, st.cuencom_st, st.unimed_st, "
				+ "       st.cuenven_st, st.cuenve2_st, st.cuencos_st,"
				// -------------------------------------------------------------------------------
				// 20100128 - EJV
				// --------------------------------------------------------------->
				+ "       st.id_indi_st, st.despa_st,"
				// <------------------------------------------------------------------------------
				+ "       ed.tipo, ed.cantidad as cantxesquema "
				+ "  FROM STOCKSTOCK st "
				+ "       INNER JOIN produccionesquemas_deta ed ON st.codigo_st = ed.codigo_st"
				+ "              AND st.idempresa = ed.idempresa AND ed.reutiliza = 'S' "
				+ " WHERE st.codigo_st " + operador + " '" + codigo_st
				+ "'  AND st.idempresa = " + idempresa.toString()
				+ "   AND ed.idesquema = " + idesquema.toString();

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/*-----------------------------------------------------*/

	public List getStockEsquemaArtEsqDesarma(BigDecimal idesquema,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT st.codigo_st, st.alias_st, st.descrip_st, st.descri2_st, "
				+ "       st.cost_re_st, st.cost_uc_st, st.cost_pp_st, st.precipp_st, st.ultcomp_st, "
				+ "       -1 as deposito, 0 as cantidad, 0 as total, st.cuencom_st, st.unimed_st, "
				+ "       st.cuenven_st, st.cuenve2_st, st.cuencos_st, ed.tipo, ed.cantidad as cantxesquema "
				+ "  FROM stockstock st "
				+ "       INNER JOIN produccionesquemas_deta ed ON st.codigo_st = ed.codigo_st "
				+ "              AND st.idempresa = ed.idempresa AND ed.reutiliza = 'S' AND ed.tipo <> 'E'  AND ed.entsal <> 'P'"
				+ " WHERE st.idempresa = "
				+ idempresa.toString()
				+ "   AND ed.idesquema = "
				+ idesquema.toString()
				+ " UNION "
				+ "SELECT st.codigo_st, st.alias_st, st.descrip_st, st.descri2_st, "
				+ "       st.cost_re_st, st.cost_uc_st, st.cost_pp_st, st.precipp_st, st.ultcomp_st, "
				+ "       -1 as deposito, 0 as cantidad, 0 as total, st.cuencom_st, st.unimed_st, "
				+ "       st.cuenven_st, st.cuenve2_st, st.cuencos_st, eds.tipo, eds.cantidad as cantxesquema "
				+ "  FROM produccionesquemas_deta eq "
				// 20090128 eq.idesquema = eds.codigo_st ???
				// + " INNER JOIN produccionesquemas_deta eds ON eq.idesquema =
				// eds.codigo_st "
				+ "       INNER JOIN produccionesquemas_deta eds ON eq.codigo_st = eds.codigo_st "
				+ "              AND eq.idempresa = eds.idempresa AND eds.reutiliza = 'S' AND eq.entsal = 'P' AND eds.tipo = 'E' "
				+ "       INNER JOIN stockstock st ON st.codigo_st = eq.codigo_st "
				+ "              AND st.idempresa = eq.idempresa AND eq.reutiliza = 'S' "
				+ " WHERE st.idempresa = " + idempresa.toString()
				+ "   AND eds.idesquema = " + idesquema.toString()
				+ " ORDER BY tipo ";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/*-----------------------------------------------------*/

	public String getCodigoStProdFinalEsquema(BigDecimal idesquema,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;

		String cQuery = "SELECT ed.codigo_st FROM produccionesquemas_deta ed "
				+ "       WHERE ed.idesquema = "
				+ idesquema.toString()
				+ "         AND ed.reutiliza = 'S' AND ed.entsal = 'P' AND ed.idempresa = "
				+ idempresa.toString();

		String codigo_st = "-1";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);

			if (rsSalida != null) {
				if (rsSalida.next()) {
					codigo_st = rsSalida.getString(1);

				}
			}

		} catch (SQLException sqlException) {
			log
					.error("Error SQL en el metodo : getCodigoStProdFinalEsquema( ... ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCodigoStProdFinalEsquema( ... )  "
							+ ex);
		}
		return codigo_st;
	}

	/*-----------------------------------------------------*/

	public List getStockEsquemasAnidados(BigDecimal idesquema,
			BigDecimal idempresa) throws EJBException {

		String cQuery = "SELECT ed.codigo_st as idesquema, ed.cantidad "
				+ "        FROM produccionesquemas_deta ed  "
				+ "       WHERE ed.idesquema = " + idesquema.toString()
				+ "         AND ed.reutiliza = 'S' AND ed.tipo = 'E'  "
				+ "         AND ed.idempresa = " + idempresa.toString();

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * */

	/**
	 * -- MOVIMIENTOS DE ENTRADA BEGIN.........................................
	 * Metodo para la entidad: stockhis - stockstockbis - stockmovstock -
	 * stockcontstock - stockanexos - stockremitos; Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Thu Aug 23 16:55:00 GMT-03:00 2006
	 * ------------------------------------------------------------------------
	 * Modificacion: handAutocomit ::::::::::::::::::::::::::::::::::::::::::::
	 * Fecha: 20071214 ........................................................
	 * Autor: EJV .............................................................
	 * Motivo: No replicar el codigo del metodo y poder manejar la transaccion
	 * ....... desde afuera....................................................
	 * ------------------------------------------------------------------------
	 * Modificacion: htSerieDespacho ::::::::::::::::::::::::::::::::::::::::::
	 * Fecha: 20100118 ........................................................
	 * Autor: EJV .............................................................
	 * Motivo: Implementar art�culos serializados y con despacho de aduana.....
	 */

	public String[] stockMovEntradaCreate(BigDecimal codigo_anexo,
			String razon_social, String domicilio, String codigo_postal,
			BigDecimal idlocalidad, BigDecimal idprovincia, String cuit,
			String iibb, String sistema_ms, Timestamp fechamov,
			BigDecimal remito_ms, String tipomov, BigDecimal sucursal,
			String tipo, boolean remitopendiente, int ejercicioactivo,
			String observaciones, Hashtable htArticulos, Hashtable htCuentas,
			BigDecimal idcontadorcomprobante, boolean handAutocommit,
			Hashtable htSerieDespacho, String usuarioalt, BigDecimal idempresa)
			throws EJBException, SQLException {

		String salida = "OK";
		BigDecimal comprob_ms = BigDecimal.valueOf(-1);
		BigDecimal nrointerno_ms = BigDecimal.valueOf(-1);
		Enumeration en;
		String tipomov_ms = "E"; // TODO
		// 20071227 - EJV Viene por parametro
		// String sistema_ms = "S"; // TODO
		BigDecimal cuenta_cs = new BigDecimal(0);
		BigDecimal importe_cs = new BigDecimal(0);
		String tipo_cs = "D";
		String sistema_cs = "M";
		BigDecimal centr1_cs = new BigDecimal(0);
		BigDecimal centr2_cs = new BigDecimal(0);
		BigDecimal totalDebe = new BigDecimal(0);
		BigDecimal totalHaber = new BigDecimal(0);
		/*
		 * 20070604 EJV Lineas detalle por Remito.
		 */
		int maxLineasXremito = 0;
		int indice = 0;
		Hashtable htArticulosImagen = new Hashtable();
		Hashtable htArticulosPaginados = null;
		String[] resultado = new String[2];

		/*
		 * 20071203 EJV Comprobantes internos - sucursal
		 */

		BigDecimal remito_interno_ms = new BigDecimal(-1);
		BigDecimal sucu_ms = new BigDecimal(-1);

		/**/

		Calendar cal = new GregorianCalendar();

		try {
			// TODO: REALIZAR TODAS LAS VALIDACIONES
			// Pasos del movimiento ...
			// 0.0 - Generar anexo .... stockanexos
			// 1.0 - Generar movimiento entrada .... stockmovstock
			// 2.0 - Actualizar entrada stock .... stockbis
			// 3.0 - Generar movimiento historico .... stockhis
			// 4.0 - Generar movimiento contable .... stockcontstock
			// 4.1 - Generar remito pendiente .... stockremitos
			if (handAutocommit)
				dbconn.setAutoCommit(false);

			/*
			 * 20070604 EJV Lineas detalle por Remito.
			 */
			try {
				maxLineasXremito = Integer.parseInt(GeneralBean
						.getValorSetupVariables("maxLineasXremito", idempresa,
								dbconn));
			} catch (Exception e) {
				salida = "Imposible recuperar lineas detalle remito.";
				log.error("maxLineasXremito: " + e);
			}
			/*
			 * */

			sucu_ms = GeneralBean.getSucursalComprobante(idcontadorcomprobante,
					idempresa, dbconn);

			if (sucu_ms == null || sucu_ms.longValue() < 0) {
				salida = "Verificar sucursal definida para el contador de remitos del puesto.";
			}

			if (htArticulos != null && !htArticulos.isEmpty()) {
				/*
				 * 20070604 EJV Lineas detalle por Remito.
				 */

				Enumeration enaux = htArticulos.keys();
				while (enaux.hasMoreElements()) {
					Object elemento = enaux.nextElement();
					htArticulosImagen.put(elemento, htArticulos.get(elemento));
				}

				while (salida.equalsIgnoreCase("OK")) {

					comprob_ms = BigDecimal.valueOf(-1);
					nrointerno_ms = BigDecimal.valueOf(-1);
					tipomov_ms = "E"; // TODO
					// 20071227 - EJV Viene por parametro
					// sistema_ms = "S"; // TODO
					cuenta_cs = new BigDecimal(0);
					importe_cs = new BigDecimal(0);
					tipo_cs = "D";
					sistema_cs = "M";
					centr1_cs = new BigDecimal(0);
					centr2_cs = new BigDecimal(0);
					totalDebe = new BigDecimal(0);
					totalHaber = new BigDecimal(0);

					indice = 0;
					htArticulosPaginados = new Hashtable();
					Enumeration enu = htArticulosImagen.keys();
					while (enu.hasMoreElements() && indice < maxLineasXremito) {
						Object elemento = enu.nextElement();
						htArticulosPaginados.put(elemento, htArticulosImagen
								.get(elemento));
						htArticulosImagen.remove(elemento);
						indice++;
					}

					if (htArticulosPaginados.isEmpty())
						break;
					en = htArticulosPaginados.keys();
					/*
					 * */
					// en = htArticulos.keys();
					// nrointerno_ms = getValorSequencia("seq_stockmovstock");
					// 20061101 - nrointerno_ms permite duplicados
					nrointerno_ms = GeneralBean.getContador(new BigDecimal(5),
							idempresa, dbconn);
					comprob_ms = nrointerno_ms;

					remito_interno_ms = GeneralBean.getContador(
							idcontadorcomprobante, idempresa, dbconn);

					salida = stockAnexosCreate(nrointerno_ms, codigo_anexo,
							razon_social, domicilio, codigo_postal,
							idlocalidad, idprovincia, cuit, iibb, dbconn,
							usuarioalt, idempresa);

					if (salida.equalsIgnoreCase("OK")) {

						Hashtable listaMS = new Hashtable();
						Hashtable listaSCS = new Hashtable();

						while (en.hasMoreElements()) {

							String key = (String) en.nextElement();
							String[] datosArticulo = (String[]) htArticulosPaginados
									.get(key);

							// for (int x = 0; x < datosArticulo.length; x++) {
							// log.info("datosArticulo[" + x + "]: "
							// + datosArticulo[x]);
							// }

							String articulo = datosArticulo[0];

							BigDecimal cantArtMov = new BigDecimal(
									datosArticulo[10]);

							BigDecimal destino = new BigDecimal(
									datosArticulo[9]);

							BigDecimal costo = new BigDecimal(datosArticulo[4]);

							String serializable = datosArticulo[17];
							String despacho = datosArticulo[18];

							importe_cs = new BigDecimal(datosArticulo[11]);

							cuenta_cs = new BigDecimal(datosArticulo[12]);

							if (!GeneralBean.isExisteCtaImputable(cuenta_cs,
									ejercicioactivo, idempresa, dbconn)) {
								salida = "La cuenta " + cuenta_cs
										+ " asociada al articulo " + articulo
										+ " no existe o no es imputable.";
								break;
							}

							/**
							 * @COMENTARIO: 20081104 - EJV. Corregir generacion
							 *              de asiento inconsistente. Agregar:
							 *              if (!remitopendiente).
							 * 
							 */

							if (!remitopendiente) {

								totalDebe = totalDebe.add(importe_cs);

								if (!listaSCS.containsKey(cuenta_cs)) {
									salida = stockContStockCreate(
											nrointerno_ms, cuenta_cs,
											importe_cs, tipo_cs, sistema_cs,
											centr1_cs, centr2_cs, dbconn,
											usuarioalt, idempresa);
								} else {
									salida = stockContStockImporteUpdate(
											nrointerno_ms, cuenta_cs,
											importe_cs, dbconn, idempresa);
								}

								if (!salida.equalsIgnoreCase("OK"))
									break;

							}

							// FIN 20081104

							/*
							 * INICIA ACTUALIZACION DE STOCK ----------------
							 * nrointerno_ms : sistema_ms : tipomov_ms :
							 * comprob_ms : fecha_ms : articu_ms : canti_ms :
							 * moneda_ms : cambio_ms : venta_ms : costo_ms :
							 * tipoaux_ms : destino_ms : comis_ms : remito_ms :
							 * impint_ms : impifl_ms : impica_ms : prelis_ms :
							 * unidad_ms : merma_ms : saldo_ms : medida_ms :
							 * observaciones : usuarioalt : usuarioact :
							 * fechaalt : fechaact :
							 */

							if (!listaMS.containsKey(articulo)) {
								salida = stockMovStockCreate(nrointerno_ms,
										sistema_ms, tipomov_ms, comprob_ms,
										fechamov, articulo, cantArtMov,
										new BigDecimal("1"), new Double("1"),
										new Double("0"), costo, sistema_cs,
										tipo, new Double("0"), remito_ms,
										new Double("0"), new Double("0"),
										new Double("0"), new Double("0"),
										new BigDecimal("0"), new Double("0"),
										new Double("0"), new BigDecimal("0"),
										observaciones, remito_interno_ms,
										sucu_ms, dbconn, usuarioalt, idempresa);
							} else {

								salida = stockMovStockCantidadUpdate(
										nrointerno_ms, articulo, cantArtMov,
										dbconn, idempresa);
							}

							if (!salida.equalsIgnoreCase("OK"))
								break;

							/*
							 * @Actualiza stockbis articu_sb : deposi_sb :
							 * canti_sb : serie_sb : despa_sb : pedid_sb :
							 * usuarioalt : usuarioact : fechaalt : fechaact :
							 */

							if (GeneralBean.getExisteArticuloDeposito(articulo,
									destino, idempresa, dbconn)) {

								salida = stockStockBisUpdate(articulo, destino,
										cantArtMov, "", "",
										new BigDecimal("0"), dbconn,
										usuarioalt, idempresa);
							} else {
								salida = stockStockBisCreate(articulo, destino,
										cantArtMov, "", "",
										new BigDecimal("0"), dbconn,
										usuarioalt, idempresa);
							}

							if (!salida.equalsIgnoreCase("OK"))
								break;

							/*
							 * nromov_sh : articu_sh : deposi_sh : serie_sh :
							 * despa_sh : canti_sh : estamp1_sh : estamp2_sh :
							 * aduana_sh : usuarioalt : usuarioact : fechaalt :
							 * fechaact :
							 */

							salida = stockHisCreate(nrointerno_ms, articulo,
									destino, "", "", cantArtMov, "", "", "",
									dbconn, usuarioalt, idempresa);

							if (!salida.equalsIgnoreCase("OK"))
								break;

							// SERIES y DESPACHOS
							// 20100118 - EJV -->
							if (serializable.equalsIgnoreCase("S")
									|| despacho.equalsIgnoreCase("S")) {

								String[][] serieDespacho = (String[][]) htSerieDespacho
										.get(key);

								salida = stockSerieDespachoEntrada(
										nrointerno_ms, articulo, destino,
										serializable, despacho, serieDespacho,
										dbconn, idempresa, usuarioalt);

							}
							// 20100118 - EJV <--

							if (!salida.equalsIgnoreCase("OK"))
								break;

							listaMS.put(articulo, articulo);
							listaSCS.put(cuenta_cs, cuenta_cs);

						}

						listaMS = null;
						listaSCS.clear();

						if (salida.equalsIgnoreCase("OK")) {
							if (!remitopendiente) {
								// Generar imputacion
								if (htCuentas != null && !htCuentas.isEmpty()) {
									en = htCuentas.keys();
									tipo_cs = "H";
									while (en.hasMoreElements()) {

										String clave = (String) en
												.nextElement();
										String[] datosImputacion = (String[]) htCuentas
												.get(clave);
										cuenta_cs = new BigDecimal(
												datosImputacion[0]);
										importe_cs = new BigDecimal(
												datosImputacion[2]);

										if (!listaSCS.containsKey(cuenta_cs)) {
											salida = stockContStockCreate(
													nrointerno_ms, cuenta_cs,
													importe_cs, tipo_cs,
													sistema_cs, centr1_cs,
													centr2_cs, dbconn,
													usuarioalt, idempresa);
										} else {
											salida = stockContStockImporteUpdate(
													nrointerno_ms, cuenta_cs,
													importe_cs, dbconn,
													idempresa);
										}

										if (!salida.equalsIgnoreCase("OK"))
											break;

										listaSCS.put(cuenta_cs, cuenta_cs);
										totalHaber = totalHaber.add(importe_cs);

									}

									if (totalDebe.compareTo(totalHaber) != 0) {
										salida = "El movimiento contable no esta balanceado: [D = "
												+ totalDebe
												+ " - H = "
												+ totalHaber + "]";
									}

								} else {
									salida = "Transaccion abortada, no existe imputacion.";
								}
							} else {
								// generar remito pendiente ....
								BigDecimal codigo_rm = codigo_anexo;
								salida = stockRemitosCreate(nrointerno_ms,
										remito_ms, fechamov, tipo, tipomov_ms,
										codigo_rm, null, sucursal, dbconn,
										usuarioalt, idempresa);
							}
						}
					}

					resultado[0] = salida;
					resultado[1] = resultado[1] != null ? resultado[1] + "-"
							+ nrointerno_ms.toString() : nrointerno_ms
							.toString();

				}// EJV 20070604
			}
		} catch (Exception e) {
			// TODO: handle exception
			salida = "E-1001: Ocurrio una Excepcion Mientras Se Actualizaba Stock.";
			log.error("stockMovEntradaCreate(): " + e);
		}
		if (!salida.equalsIgnoreCase("OK"))
			dbconn.rollback();

		if (handAutocommit)
			dbconn.setAutoCommit(true);

		return resultado;
	}

	public static String stockSerieDespachoEntrada(BigDecimal nrointerno_ms,
			String articulo, BigDecimal destino, String serializable,
			String despacho, String[][] serieDespacho, Connection conn,
			BigDecimal idempresa, String usuarioalt) throws EJBException {

		String salida = "OK";
		Calendar cal = new GregorianCalendar();

		// log.info("stockSerieDespachoEntrada -- > encapsulado ");

		try {

			// String[][] serieDespacho = (String[][]) htSerieDespacho
			// .get(key);
			java.sql.Date fechaactual = new java.sql.Date(cal.getTimeInMillis());

			/*
			 * EJV - 20100127..................................................
			 * TODO::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
			 * TODO::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
			 * TODO::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
			 * TODO: .........................................................
			 * Bloque if (serieDespacho == null) {...} es necesario hasta que se
			 * defina acciones a ejecutar desde desarmado/exlosion de OP.
			 * Tambien es necesario definir l�gica a implementar en el progrma
			 * de producci�n.
			 * TODO::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
			 * TODO::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
			 * TODO::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
			 */

			if (serieDespacho == null) {

				log.warn("Loggin forzado por : serieDespacho == null");

			} else if (serializable.equalsIgnoreCase("S")) {

				for (int k = 0; salida.equalsIgnoreCase("OK")
						&& serieDespacho != null && k < serieDespacho.length; k++) {

					if (!existeStockSerieDespachoNroserie(serieDespacho[k][0],
							articulo, conn, idempresa)) {

						salida = stockSerieDespachoCreate(nrointerno_ms,
								articulo, destino, serieDespacho[k][0],
								serieDespacho[k][1], new BigDecimal(1),
								new BigDecimal(0), fechaactual, null, conn,
								idempresa, usuarioalt);
						log.info(salida);

					} else
						salida = "Error: el nro. de serie "
								+ serieDespacho[k][0]
								+ ", ya existe en stock para el articulo: "
								+ articulo;

				}

			} else if (despacho.equalsIgnoreCase("S")) {

				for (int k = 0; salida.equalsIgnoreCase("OK")
						&& serieDespacho != null && k < serieDespacho.length; k++) {

					BigDecimal cantidad = new BigDecimal(0);
					BigDecimal cantreserva = new BigDecimal(0);
					// idseriedespacho,nromov_sd,codigo_st,codigo_dt,nroserie,nrodespacho,
					// cantidad,cantreserva,fechain,fechaout,
					List listDespacho = getStockSerieDespachoNrodespacho(
							serieDespacho[k][1], articulo, destino, conn,
							idempresa);

					if (!listDespacho.isEmpty()) {
						if (listDespacho.size() == 1) {

							// Actualizar - recuperar

							String[] datosDespacho = (String[]) listDespacho
									.get(0);
							BigDecimal idseriedespacho = new BigDecimal(
									datosDespacho[0]);
							cantidad = new BigDecimal(datosDespacho[6]);
							cantreserva = new BigDecimal(datosDespacho[7]);

							salida = stockSerieDespachoFechaOutUpdate(
									idseriedespacho, fechaactual, conn,
									idempresa, usuarioalt);

						} else
							salida = "Error: existe mas de un registro abierto para el despacho: "
									+ serieDespacho[k][1];
					}

					if (!salida.equalsIgnoreCase("OK"))
						break;

					cantidad = cantidad
							.add(new BigDecimal(serieDespacho[k][2]));

					salida = stockSerieDespachoCreate(nrointerno_ms, articulo,
							destino, serieDespacho[k][0], serieDespacho[k][1],
							cantidad, cantreserva, fechaactual, null, conn,
							idempresa, usuarioalt);

				}

			}

		} catch (Exception e) {

			salida = "(EX) Error al intentar realizar moviento de entrada Serie/Despacho.";
			log.error("(EX) stockSerieDespachoEntrada (): " + e);

		}

		return salida;

	}

	/** -- MOVIMIENTOS DE ENTRADA END */

	/**
	 * -- MOVIMIENTOS DE SALIDA BEGIN .......................................
	 * Metodo para la entidad: stockhis - stockstockbis - stockmovstock -
	 * stockcontstock - stockanexos - stockremitos; Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Thu Aug 23 16:55:00 GMT-03:00 2006 Modificacion:
	 * handAutocomit :::::::::::::::::::::::::::::::::::::::::::: Fecha:
	 * 20071214 ........................................................ Autor:
	 * EJV ............................................................. Motivo:
	 * No replicar el codigo del metodo y poder manejar la transaccion .......
	 * desde afuera....................................................
	 */

	public String[] stockMovSalidaCreate(BigDecimal codigo_anexo,
			String razon_social, String domicilio, String codigo_postal,
			BigDecimal idlocalidad, BigDecimal idprovincia, String cuit,
			String iibb, String sistema_ms, Timestamp fechamov,
			BigDecimal remito_ms, String tipomov, BigDecimal sucursal,
			String tipo, boolean remitopendiente, int ejercicioactivo,
			String observaciones, Hashtable htArticulos, Hashtable htCuentas,
			BigDecimal idcontadorcomprobante, boolean handAutocommit,
			Hashtable htSerieDespacho, String usuarioalt, BigDecimal idempresa)
			throws EJBException, SQLException {

		String salida = "OK";
		BigDecimal comprob_ms = BigDecimal.valueOf(-1);
		BigDecimal nrointerno_ms = BigDecimal.valueOf(-1);
		Enumeration en;
		String tipomov_ms = "S"; // TODO
		// 20071227 - EJV - Viene por parametro
		// String sistema_ms = "S"; // TODO
		BigDecimal cuenta_cs = new BigDecimal(0);
		BigDecimal importe_cs = new BigDecimal(0);
		String tipo_cs = "H";
		String sistema_cs = "M";
		BigDecimal centr1_cs = new BigDecimal(0);
		BigDecimal centr2_cs = new BigDecimal(0);

		BigDecimal totalDebe = new BigDecimal(0);
		BigDecimal totalHaber = new BigDecimal(0);

		/*
		 * 20070604 EJV Lineas detalle por Remito.
		 */
		int maxLineasXremito = 0;
		int indice = 0;
		Hashtable htArticulosImagen = htArticulos;
		Hashtable htArticulosPaginados = null;
		String[] resultado = new String[2];

		/*
		 * 20071203 EJV Comprobantes internos - sucursal
		 */

		BigDecimal remito_interno_ms = new BigDecimal(-1);
		BigDecimal sucu_ms = new BigDecimal(-1);

		/**/

		Calendar cal = new GregorianCalendar();

		try {
			// TODO: REALIZAR TODAS LAS VALIDACIONES
			// Pasos del movimiento ...
			// 0.0 - Generar anexo .... stockanexos
			// 1.0 - Generar movimiento entrada .... stockmovstock
			// 2.0 - Actualizar entrada stock .... stockbis
			// 3.0 - Generar movimiento historico .... stockhis
			// 4.0 - Generar movimiento contable .... stockcontstock
			// 4.1 - Generar remito pendiente .... stockremitos
			if (handAutocommit)
				dbconn.setAutoCommit(false);

			/*
			 * 20070604 EJV Lineas detalle por Remito.
			 */
			try {
				maxLineasXremito = Integer.parseInt(GeneralBean
						.getValorSetupVariables("maxLineasXremito", idempresa,
								dbconn));
			} catch (Exception e) {
				salida = "Imposible recuperar lineas detalle remito.";
				log.error("maxLineasXremito: " + e);
			}
			/*
			 * */

			sucu_ms = GeneralBean.getSucursalComprobante(idcontadorcomprobante,
					idempresa, dbconn);

			if (sucu_ms == null || sucu_ms.longValue() < 0) {
				salida = "Verificar sucursal definida para el contador de remitos del puesto.";
			}

			if (htArticulos != null && !htArticulos.isEmpty()) {

				while (salida.equalsIgnoreCase("OK")) {

					comprob_ms = BigDecimal.valueOf(-1);
					nrointerno_ms = BigDecimal.valueOf(-1);
					tipomov_ms = "S"; // TODO
					// 20071227 - EJV - Viene por parametro
					// sistema_ms = "S"; // TODO
					cuenta_cs = new BigDecimal(0);
					importe_cs = new BigDecimal(0);
					tipo_cs = "H";
					sistema_cs = "M";
					centr1_cs = new BigDecimal(0);
					centr2_cs = new BigDecimal(0);
					totalDebe = new BigDecimal(0);
					totalHaber = new BigDecimal(0);

					indice = 0;
					htArticulosPaginados = new Hashtable();
					Enumeration enu = htArticulosImagen.keys();
					while (enu.hasMoreElements() && indice < maxLineasXremito) {
						Object elemento = enu.nextElement();
						htArticulosPaginados.put(elemento, htArticulosImagen
								.get(elemento));
						htArticulosImagen.remove(elemento);
						indice++;
					}

					if (htArticulosPaginados.isEmpty())
						break;
					en = htArticulosPaginados.keys();
					/*
					 * */
					// en = htArticulos.keys();
					/*
					 * 20070604 EJV Lineas detalle por Remito.
					 */

					// nrointerno_ms = getValorSequencia("seq_stockmovstock");
					// 20061101 - nrointerno_ms permite duplicados
					nrointerno_ms = GeneralBean.getContador(new BigDecimal(5),
							idempresa, dbconn);
					comprob_ms = nrointerno_ms;
					remito_interno_ms = GeneralBean.getContador(
							idcontadorcomprobante, idempresa, dbconn);

					salida = stockAnexosCreate(nrointerno_ms, codigo_anexo,
							razon_social, domicilio, codigo_postal,
							idlocalidad, idprovincia, cuit, iibb, dbconn,
							usuarioalt, idempresa);

					if (salida.equalsIgnoreCase("OK")) {

						Hashtable listaMS = new Hashtable();
						Hashtable listaSCS = new Hashtable();

						while (en.hasMoreElements()) {

							String key = (String) en.nextElement();
							String[] datosArticulo = (String[]) htArticulosPaginados
									.get(key);
							String articulo = datosArticulo[0];
							BigDecimal cantArtMov = new BigDecimal(
									datosArticulo[10]);
							BigDecimal origen = new BigDecimal(datosArticulo[9]);
							BigDecimal costo = new BigDecimal(datosArticulo[4]);

							String serializable = datosArticulo[17];
							String despacho = datosArticulo[18];

							importe_cs = new BigDecimal(datosArticulo[11]);
							cuenta_cs = new BigDecimal(datosArticulo[12]);

							if ((GeneralBean.getCantidadArticuloDeposito(
									articulo, origen, idempresa, dbconn)
									.compareTo(cantArtMov)) < 0
									&& !GeneralBean.hasStockNegativo(idempresa,
											dbconn)) {
								salida = "Existencia de articulo " + articulo
										+ " insuficiente en deposito " + origen
										+ ".";
								break;
							}

							if (!GeneralBean.isExisteCtaImputable(cuenta_cs,
									ejercicioactivo, idempresa, dbconn)) {
								salida = "La cuenta " + cuenta_cs
										+ " asociada al articulo " + articulo
										+ " no existe o no es imputable.";
								break;
							}

							/**
							 * @COMENTARIO: 20081104 - EJV. Corregir generacion
							 *              de asiento inconsistente. Agregar:
							 *              if (!remitopendiente).
							 * 
							 */

							if (!remitopendiente) {

								totalDebe = totalDebe.add(importe_cs);

								if (!listaSCS.containsKey(cuenta_cs)) {
									salida = stockContStockCreate(
											nrointerno_ms, cuenta_cs,
											importe_cs, tipo_cs, sistema_cs,
											centr1_cs, centr2_cs, dbconn,
											usuarioalt, idempresa);
								} else {
									salida = stockContStockImporteUpdate(
											nrointerno_ms, cuenta_cs,
											importe_cs, dbconn, idempresa);
								}

							}

							// FIN 20081104

							if (!salida.equalsIgnoreCase("OK"))
								break;

							/*
							 * INICIA ACTUALIZACION DE STOCK ----------------
							 * nrointerno_ms : sistema_ms : tipomov_ms :
							 * comprob_ms : fecha_ms : articu_ms : canti_ms :
							 * moneda_ms : cambio_ms : venta_ms : costo_ms :
							 * tipoaux_ms : destino_ms : comis_ms : remito_ms :
							 * impint_ms : impifl_ms : impica_ms : prelis_ms :
							 * unidad_ms : merma_ms : saldo_ms : medida_ms :
							 * observaciones : usuarioalt : usuarioact :
							 * fechaalt : fechaact :
							 */

							if (!listaMS.containsKey(articulo)) {
								salida = stockMovStockCreate(nrointerno_ms,
										sistema_ms, tipomov_ms, comprob_ms,
										fechamov, articulo, cantArtMov,
										new BigDecimal("1"), new Double("1"),
										new Double("0"), costo, sistema_cs,
										tipo, new Double("0"), remito_ms,
										new Double("0"), new Double("0"),
										new Double("0"), new Double("0"),
										new BigDecimal("0"), new Double("0"),
										new Double("0"), new BigDecimal("0"),
										observaciones, remito_interno_ms,
										sucu_ms, dbconn, usuarioalt, idempresa);
							} else {

								salida = stockMovStockCantidadUpdate(
										nrointerno_ms, articulo, cantArtMov,
										dbconn, idempresa);
							}

							if (!salida.equalsIgnoreCase("OK"))
								break;

							/*
							 * @Actualiza stockbis articu_sb : deposi_sb :
							 * canti_sb : serie_sb : despa_sb : pedid_sb :
							 * usuarioalt : usuarioact : fechaalt : fechaact :
							 */

							salida = stockStockBisUpdate(articulo, origen,
									cantArtMov.negate(), "", "",
									new BigDecimal("0"), dbconn, usuarioalt,
									idempresa);

							if (!salida.equalsIgnoreCase("OK"))
								break;

							/*
							 * nromov_sh : articu_sh : deposi_sh : serie_sh :
							 * despa_sh : canti_sh : estamp1_sh : estamp2_sh :
							 * aduana_sh : usuarioalt : usuarioact : fechaalt :
							 * fechaact :
							 */

							salida = stockHisCreate(nrointerno_ms, articulo,
									origen, "", "", cantArtMov, "", "", "",
									dbconn, usuarioalt, idempresa);

							if (!salida.equalsIgnoreCase("OK"))
								break;

							// ejvseriedespacho
							// ---------------------------------------------------------------------
							// 20100121 - EJV -->
							// ---------------------------------------------------------------------

							if (serializable.equalsIgnoreCase("S")
									|| despacho.equalsIgnoreCase("S")) {

								String[][] serieDespacho = (String[][]) htSerieDespacho
										.get(key);
								log.info("2");
								salida = stockSerieDespachoSalida(
										nrointerno_ms, articulo, origen,
										serializable, despacho, serieDespacho,
										dbconn, idempresa, usuarioalt);
								log.info(salida);
								if (!salida.equalsIgnoreCase("OK"))
									break;

							}

							// ---------------------------------------------------------------------
							// 20100121 - EJV <--
							// ---------------------------------------------------------------------

							listaMS.put(articulo, articulo);
							listaSCS.put(cuenta_cs, cuenta_cs);

						}

						listaMS = null;
						listaSCS.clear();

						if (salida.equalsIgnoreCase("OK")) {
							if (!remitopendiente) {
								// Generar imputacion
								if (htCuentas != null && !htCuentas.isEmpty()) {
									en = htCuentas.keys();
									tipo_cs = "D";
									while (en.hasMoreElements()) {

										String clave = (String) en
												.nextElement();
										String[] datosImputacion = (String[]) htCuentas
												.get(clave);
										cuenta_cs = new BigDecimal(
												datosImputacion[0]);
										importe_cs = new BigDecimal(
												datosImputacion[2]);

										if (!listaSCS.containsKey(cuenta_cs)) {
											salida = stockContStockCreate(
													nrointerno_ms, cuenta_cs,
													importe_cs, tipo_cs,
													sistema_cs, centr1_cs,
													centr2_cs, dbconn,
													usuarioalt, idempresa);
										} else {
											salida = stockContStockImporteUpdate(
													nrointerno_ms, cuenta_cs,
													importe_cs, dbconn,
													idempresa);
										}

										if (!salida.equalsIgnoreCase("OK"))
											break;

										listaSCS.put(cuenta_cs, cuenta_cs);
										totalHaber = totalHaber.add(importe_cs);

									}

									if (totalDebe.compareTo(totalHaber) != 0) {
										salida = "El movimiento contable no esta balanceado: [D = "
												+ totalDebe
												+ " - H = "
												+ totalHaber + "]";
									}

								} else {
									salida = "Transaccion abortada, no existe imputacion.";
								}
							} else {
								// generar remito pendiente ....
								BigDecimal codigo_rm = codigo_anexo;
								salida = stockRemitosCreate(nrointerno_ms,
										remito_ms, fechamov, tipo, tipomov_ms,
										codigo_rm, null, sucursal, dbconn,
										usuarioalt, idempresa);
							}
						}
					}

					/*
					 * 20070604 EJV Lineas detalle por Remito.
					 */
					resultado[0] = salida;
					resultado[1] = resultado[1] != null ? resultado[1] + "-"
							+ nrointerno_ms.toString() : nrointerno_ms
							.toString();
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
			salida = "E-1002: Ocurrio Excepcion Mientras Se Actualizaba Stock.";
			log.error("stockMovSalidaCreate(): " + e);
		}
		if (!salida.equalsIgnoreCase("OK"))
			dbconn.rollback();

		if (handAutocommit)
			dbconn.setAutoCommit(true);

		return resultado;
	}

	public static String stockSerieDespachoSalida(BigDecimal nrointerno_ms,
			String articulo, BigDecimal origen, String serializable,
			String despacho, String[][] serieDespacho, Connection conn,
			BigDecimal idempresa, String usuarioalt) throws EJBException {

		String salida = "OK";
		Calendar cal = new GregorianCalendar();

		// log.info("stockSerieDespachoSalida -- > encapsulado ");

		try {

			// String[][] serieDespacho = (String[][]) htSerieDespacho
			// .get(key);
			java.sql.Date fechaactual = new java.sql.Date(cal.getTimeInMillis());

			/*
			 * EJV - 20100127..................................................
			 * TODO::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
			 * TODO::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
			 * TODO::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
			 * TODO: .........................................................
			 * Bloque if (serieDespacho == null) {...} es necesario hasta que se
			 * defina acciones a ejecutar desde desarmado/exlosion de OP.
			 * Tambien es necesario definir l�gica a implementar en el progrma
			 * de producci�n.
			 * TODO::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
			 * TODO::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
			 * TODO::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
			 */

			log.info("stockSerieDespachoSalida: " + serieDespacho);

			if (serieDespacho == null) {

				log.warn("Loggin forzado por : serieDespacho == null");

			} else if (serializable.equalsIgnoreCase("S")) {

				for (int j = 0; salida.equalsIgnoreCase("OK")
						&& j < serieDespacho.length; j++) {

					BigDecimal idseriedespacho = new BigDecimal(
							serieDespacho[j][3]);
					// idseriedespacho,nromov_sd,codigo_st,codigo_dt,
					// nroserie,nrodespacho,cantidad,cantreserva,fechain,fechaout
					List listSerie = getStockSerieDespachoStaticPK(
							idseriedespacho, conn, idempresa);
					if (!listSerie.isEmpty()) {

						if (listSerie.size() == 1) {

							String datos[] = (String[]) listSerie.get(0);

							int cantidadreserva = Integer.parseInt(datos[7]);
							String fechaout = datos[9];

							if (cantidadreserva != 0)
								salida = "El articulo con nro. de serie "
										+ datos[4]
										+ " se encuentra reservado, posiblemente modificado en otra sesion.";
							else if (fechaout != null)
								salida = "El articulo con nro. de serie "
										+ datos[4]
										+ " tiene fecha de salida, posiblemente modificado en otra sesion.";
							else {

								//

								salida = stockSerieDespachoFechaOutUpdate(
										idseriedespacho, fechaactual, conn,
										idempresa, usuarioalt);

								if (!salida.equalsIgnoreCase("OK"))
									break;

								salida = stockSerieDespachoCreate(
										nrointerno_ms, articulo, origen,
										serieDespacho[j][0],
										serieDespacho[j][1], new BigDecimal(1),
										new BigDecimal(0), fechaactual,
										fechaactual, conn, idempresa,
										usuarioalt);
								if (!salida.equalsIgnoreCase("OK"))
									break;

								// TODO:
								// [QUITAR]-ESTO-ESTA-PARA-REALIZAR-PRUEBAS
								// TODO:
								// [QUITAR]-ESTO-ESTA-PARA-REALIZAR-PRUEBAS
								// TODO:
								// [QUITAR]-ESTO-ESTA-PARA-REALIZAR-PRUEBAS
								// ----
								// if (salida
								// .equalsIgnoreCase("OK"))
								// salida =
								// "Fuerzo la salida para que haga un ROLLBACK SERIE ...";
							}

						} else {
							salida = "Error: Mas de un registro para id-SERIE-despacho?: "
									+ idseriedespacho;
						}

					} else {
						salida = "Error: no fue posible recuperar datos para id-SERIE-despacho: "
								+ idseriedespacho;
					}
				}

			} else if (despacho.equalsIgnoreCase("S")) {

				for (int j = 0; salida.equalsIgnoreCase("OK")
						&& j < serieDespacho.length; j++) {
					BigDecimal idseriedespacho = new BigDecimal(
							serieDespacho[j][3]);
					// idseriedespacho,nromov_sd,codigo_st,codigo_dt,
					// nroserie,nrodespacho,cantidad,cantreserva,fechain,fechaout
					List listSerie = getStockSerieDespachoStaticPK(
							idseriedespacho, conn, idempresa);
					if (!listSerie.isEmpty()) {

						if (listSerie.size() == 1) {

							String datos[] = (String[]) listSerie.get(0);

							long cantidad = Long.parseLong(datos[6]);

							long cantidadreserva = Long.parseLong(datos[7]);

							BigDecimal cantidadMov = new BigDecimal(
									serieDespacho[j][2]);

							String fechaout = datos[9];

							if ((cantidad - cantidadreserva) < 1)
								salida = "Despacho "
										+ datos[5]
										+ " de articulo  "
										+ datos[2]
										+ " reservado en su total, posiblemente modificado en otra sesion.";
							else if ((cantidad - cantidadreserva) < cantidadMov
									.longValue())
								salida = "Despacho "
										+ datos[5]
										+ " de articulo "
										+ datos[2]
										+ " con disponible menor al que intenta mover.";
							else if (fechaout != null)
								salida = "El despacho "
										+ datos[5]
										+ " de articulo "
										+ datos[2]
										+ " tiene fecha de salida, posiblemente modificado en otra sesion.";
							else {

								if (!salida.equalsIgnoreCase("OK"))
									break;

								salida = stockSerieDespachoFechaOutUpdate(
										idseriedespacho, fechaactual, conn,
										idempresa, usuarioalt);

								if (!salida.equalsIgnoreCase("OK"))
									break;

								salida = stockSerieDespachoCreate(
										nrointerno_ms, articulo, origen,
										serieDespacho[j][0],
										serieDespacho[j][1], new BigDecimal(
												serieDespacho[j][2]),
										new BigDecimal(cantidadreserva),
										fechaactual, fechaactual, conn,
										idempresa, usuarioalt);
								if (!salida.equalsIgnoreCase("OK"))
									break;

								salida = stockSerieDespachoCreate(
										nrointerno_ms,
										articulo,
										origen,
										serieDespacho[j][0],
										serieDespacho[j][1],
										new BigDecimal(
												cantidad
														- Long
																.parseLong(serieDespacho[j][2])),
										new BigDecimal(cantidadreserva),
										fechaactual, null, conn, idempresa,
										usuarioalt);
								if (!salida.equalsIgnoreCase("OK"))
									break;

								// TODO:
								// [QUITAR]-ESTO-ESTA-PARA-REALIZAR-PRUEBAS
								// TODO:
								// [QUITAR]-ESTO-ESTA-PARA-REALIZAR-PRUEBAS
								// TODO:
								// [QUITAR]-ESTO-ESTA-PARA-REALIZAR-PRUEBAS
								// ------
								// if (salida
								// .equalsIgnoreCase("OK"))
								// salida =
								// "Fuerzo la salida para que haga un ROLLBACK DESPACHO ...";

							}

						} else {
							salida = "Error: Mas de un registro para id-serie-DESPACHO?: "
									+ idseriedespacho;
						}

					} else {
						salida = "Error: no fue posible recuperar datos para id-serie-DESPACHO: "
								+ idseriedespacho;
					}

				}

			}

		} catch (Exception e) {

			salida = "(EX) Error al intentar realizar moviento de salida Serie/Despacho.";
			log.error("(EX) stockSerieDespachoSalida (): " + e);

		}

		return salida;

	}

	/** -- MOVIMIENTOS DE SALIDA END */

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt
	/**
	 * TODO: REPLICADO EN ClientesBean .....................................
	 * stockMovStockCantidadUpdate ..........................................
	 * stockMovStockCantidadUpdate ..........................................
	 * stockContStockImporteUpdate ..........................................
	 * stockMovStockCreate ..................................................
	 * EJV - 20070824 - .....................................................
	 * COMENTARIOS: analizar la posibilidad de concentrar ...................
	 * metodo en una clase, recibiendo como nuevo parametro la coneccion, y .
	 * haciendo estatico al mismo. ..........................................
	 */

	public static String stockMovStockCreate(BigDecimal nrointerno_ms,
			String sistema_ms, String tipomov_ms, BigDecimal comprob_ms,
			Timestamp fechamov, String articu_ms, BigDecimal canti_ms,
			BigDecimal moneda_ms, Double cambio_ms, Double venta_ms,
			BigDecimal costo_ms, String tipoaux_ms, String destino_ms,
			Double comis_ms, BigDecimal remito_ms, Double impint_ms,
			Double impifl_ms, Double impica_ms, Double prelis_ms,
			BigDecimal unidad_ms, Double merma_ms, Double saldo_ms,
			BigDecimal medida_ms, String observaciones,
			BigDecimal remito_interno_ms, BigDecimal sucu_ms, Connection conn,
			String usuarioalt, BigDecimal idempresa) throws EJBException {
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
				qDML += " unidad_ms,merma_ms,saldo_ms,medida_ms,observaciones,remito_interno_ms,sucu_ms,usuarioalt,idempresa)";
				qDML += " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				PreparedStatement statement = conn.prepareStatement(qDML);
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
				statement.setBigDecimal(25, remito_interno_ms);
				statement.setBigDecimal(26, sucu_ms);
				statement.setString(27, usuarioalt);
				statement.setBigDecimal(28, idempresa);
				int n = statement.executeUpdate();
				if (n != 1)
					salida = "Imposible generar movimiento de stock.";
			}

		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String stockMovStockCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String stockMovStockCreate(.....)"
							+ ex);
		}
		log.info("stockMovStockCreate: " + salida);
		return salida;
	}

	public static String stockMovStockCantidadUpdate(BigDecimal nrointerno_ms,
			String articu_ms, BigDecimal canti_ms, Connection conn,
			BigDecimal idempresa) throws EJBException {
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (nrointerno_ms == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nrointerno_ms ";
		if (articu_ms == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: articu_ms ";
		if (canti_ms == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: canti_ms ";
		// fin validaciones

		try {
			PreparedStatement insert = null;
			String sql = "";
			if (salida.equalsIgnoreCase("OK")) {

				sql = ""
						+ "UPDATE stockmovstock SET   canti_ms = canti_ms + (?) "
						+ " WHERE articu_ms=? AND  nrointerno_ms=?  AND  comprob_ms=? AND idempresa=?;";
				insert = conn.prepareStatement(sql);
				insert.setBigDecimal(1, canti_ms);
				insert.setString(2, articu_ms);
				insert.setBigDecimal(3, nrointerno_ms);
				insert.setBigDecimal(4, nrointerno_ms);
				insert.setBigDecimal(5, idempresa);

				int i = insert.executeUpdate();
				if (i != 1)
					salida = "Imposible actualizar stock para articulo: "
							+ articu_ms;
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String stockMovStockUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String stockMovStockUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * TODO: EJV - 20071116 METODO DUPLICADO EN PROVEEDORES. VER DE UNIFICAR !!!
	 * 
	 */

	public static String stockStockBisUpdate(String articu_sb,
			BigDecimal deposi_sb, BigDecimal canti_sb, String serie_sb,
			String despa_sb, BigDecimal pedid_sb, Connection conn,
			String usuarioact, BigDecimal idempresa) throws EJBException {
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
				qDML += "   SET canti_sb=( canti_sb +( ? )),serie_sb=?,despa_sb=?,pedid_sb = pedid_sb + ?,usuarioact=?,fechaact=?";
				qDML += " WHERE articu_sb=? AND deposi_sb=?  AND idempresa=?";
				statement = conn.prepareStatement(qDML);
				statement.clearParameters();

				statement.clearParameters();
				statement.setBigDecimal(1, canti_sb);
				statement.setString(2, null);
				statement.setString(3, null);
				statement.setBigDecimal(4, pedid_sb);
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

	public static String stockStockBisCreate(String articu_sb,
			BigDecimal deposi_sb, BigDecimal canti_sb, String serie_sb,
			String despa_sb, BigDecimal pedid_sb, Connection conn,
			String usuarioalt, BigDecimal idempresa) throws EJBException {
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
				statement = conn.prepareStatement(qDML);
				statement.clearParameters();
				statement.setString(1, articu_sb);
				statement.setBigDecimal(2, deposi_sb);
				statement.setBigDecimal(3, canti_sb);
				statement.setString(4, null);
				statement.setString(5, null);
				statement.setBigDecimal(6, pedid_sb);
				statement.setString(7, usuarioalt);
				statement.setBigDecimal(8, idempresa);
				n = statement.executeUpdate();
				if (n != 1)
					salida = "Imposible actualizar cantidad en dep�sito.";

			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String stockStockBisCreate(.....)"
					+ sqlException);
			n = -2;
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String stockStockBisCreate(.....)"
							+ ex);

		}
		log.info("stockStockBisCreate:" + salida);
		return salida;
	}

	// EJV - 20090624
	public static String stockStockBisCantidadesUpdate(String articu_sb,
			BigDecimal deposi_sb, BigDecimal canti_sb, BigDecimal pedid_sb,
			Connection conn, String usuarioact, BigDecimal idempresa)
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
		if (pedid_sb == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: canti_sb ";

		// 2. sin nada desde la pagina
		if (articu_sb.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: articu_sb ";
		// fin validaciones

		try {

			PreparedStatement statement = null;
			if (salida.equalsIgnoreCase("OK")) {

				qDML = "UPDATE stockstockbis ";
				qDML += "   SET canti_sb=( canti_sb +( ? )),pedid_sb = (pedid_sb + (?)),usuarioact=?,fechaact=?";
				qDML += " WHERE articu_sb=? AND deposi_sb=?  AND idempresa=?";
				statement = conn.prepareStatement(qDML);
				statement.clearParameters();

				statement.clearParameters();
				statement.setBigDecimal(1, canti_sb);
				statement.setBigDecimal(2, pedid_sb);
				statement.setString(3, usuarioact);
				statement.setTimestamp(4, fechaact);
				statement.setString(5, articu_sb);
				statement.setBigDecimal(6, deposi_sb);
				statement.setBigDecimal(7, idempresa);

				int i = statement.executeUpdate();
				if (i != 1) {
					salida = "Articulo (" + articu_sb
							+ ") inexistente en deposito (" + deposi_sb + ").";
				}
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error SQL public String stockStockBisCantidadesUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String stockStockBisCantidadesUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public static String stockHisCreate(BigDecimal nromov_sh, String articu_sh,
			BigDecimal deposi_sh, String serie_sh, String despa_sh,
			BigDecimal canti_sh, String estamp1_sh, String estamp2_sh,
			String aduana_sh, Connection conn, String usuarioalt,
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
				qDML += " canti_sh,estamp1_sh,estamp2_sh,aduana_sh,usuarioalt, idempresa) ";
				qDML += " VALUES (?,?,?,?,?,?,?,?,?,?,?)";

				statement = conn.prepareStatement(qDML);
				statement.clearParameters();
				statement.setBigDecimal(1, nromov_sh);
				statement.setString(2, articu_sh);
				statement.setBigDecimal(3, deposi_sh);
				statement.setString(4, serie_sh);
				statement.setString(5, despa_sh);
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
			log.error("Error SQL public String stockHisCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error excepcion public String stockHisCreate(.....)"
					+ ex);
		}
		log.info("stockHisCreate:" + salida);
		return salida;
	}

	// --

	/**
	 * Metodos para la entidad: lov_vanexosclieproveedo Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Fri Oct 27 14:24:30 GMT-03:00 2006
	 */
	// para todo (ordena por el segundo campo por defecto)
	public List getLov_vanexosclieproveedoAll(long limit, long offset,
			String tipo, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT codigo_anexo,razon_social,idlocalidad,localidad,domicilio,"
				+ "       codigo_postal,idprovincia,provincia,cuit,iibb,tipo "
				+ "  FROM LOV_VANEXOSCLIEPROVEEDO WHERE idempresa= "
				+ idempresa.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getLov_vanexosclieproveedoOcu(long limit, long offset,
			String tipo, String ocurrencia, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT codigo_anexo,razon_social,idlocalidad,localidad,domicilio,"
				+ "       codigo_postal,idprovincia,provincia,cuit,iibb,tipo "
				+ "  FROM LOV_VANEXOSCLIEPROVEEDO WHERE ((UPPER(codigo_anexo::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(RAZON_SOCIAL) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%') ) AND idempresa= "
				+ idempresa.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getLov_vanexosclieproveedoPK(BigDecimal codigo_anexo,
			String tipo, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT codigo_anexo,razon_social,idlocalidad,localidad,domicilio,"
				+ "       codigo_postal,idprovincia,provincia,cuit,iibb,tipo "
				+ "  FROM LOV_VANEXOSCLIEPROVEEDO WHERE codigo_anexo="
				+ codigo_anexo.toString() + " AND idempresa="
				+ idempresa.toString();

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: globalLocalidades Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Fri Oct 27 16:35:02 GMT-03:00 2006
	 */
	// para todo (ordena por el segundo campo por defecto)
	public List getGlobalLocalidadesAll(long limit, long offset)
			throws EJBException {
		String cQuery = ""
				+ "SELECT l.idlocalidad, l.localidad, p.idprovincia, p.provincia, l.cpostal,"
				+ "       l.usuarioalt, l.usuarioact, l.fechaalt, l.fechaact "
				+ "  FROM globallocalidades l INNER JOIN globalprovincias p ON l.idprovincia = p.idprovincia"
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getGlobalLocalidadesOcu(long limit, long offset,
			String ocurrencia) throws EJBException {
		String cQuery = ""
				+ "SELECT l.idlocalidad, l.localidad, p.idprovincia, p.provincia, l.cpostal,"
				+ "       l.usuarioalt, l.usuarioact, l.fechaalt, l.fechaact "
				+ "  FROM globallocalidades l INNER JOIN globalprovincias p ON l.idprovincia = p.idprovincia"
				+ " where (l.IDLOCALIDAD::VARCHAR LIKE '%" + ocurrencia
				+ "%' OR " + " UPPER(l.LOCALIDAD) LIKE '%"
				+ ocurrencia.toUpperCase() + "%' OR "
				+ " UPPER(l.cpostal) LIKE '%" + ocurrencia.toUpperCase()
				+ "%') " + " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset
				+ ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: contableInfiPlanYYYY Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Mon Oct 30 12:11:00 GMT-03:00 2006
	 * 
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getContableInfiPlanAll(long limit, long offset,
			int anoejercicio, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT idcuenta,cuenta,inputable,nivel,ajustable,resultado,"
				+ "       cent_cost1,cent_cost2,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM CONTABLEINFIPLAN WHERE idempresa="
				+ idempresa.toString() + " AND ejercicio=" + anoejercicio
				+ "ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getContableInfiPlanOcu(long limit, long offset,
			int ejercicioactivo, String ocurrencia, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT idcuenta,cuenta,inputable,nivel,ajustable,resultado,"
				+ "       cent_cost1,cent_cost2,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM CONTABLEINFIPLAN WHERE (UPPER(CUENTA) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR (IDCUENTA::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%') AND idempresa="
				+ idempresa.toString() + " AND ejercicio=" + ejercicioactivo
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getContableInfiPlanPK(BigDecimal idcuenta, int ejercicioactivo,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idcuenta,cuenta,inputable,nivel,ajustable,resultado,"
				+ "       cent_cost1,cent_cost2,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM CONTABLEINFIPLAN " + " WHERE idcuenta="
				+ idcuenta.toString() + " AND idempresa="
				+ idempresa.toString() + " AND ejercicio=" + ejercicioactivo;

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: stockAnexos Copyrigth(r) sysWarp S.R.L. Fecha de
	 * creacion: Wed Nov 01 10:52:18 GMT-03:00 2006
	 */

	// grabacion de un nuevo registro
	public static String stockAnexosCreate(BigDecimal nint_ms_an,
			BigDecimal codigo_anexo, String razon_social, String domicilio,
			String codigo_postal, BigDecimal idlocalidad,
			BigDecimal idprovincia, String cuit, String iibb, Connection conn,
			String usuarioalt, BigDecimal idempresa) throws EJBException {
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (codigo_anexo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_anexo ";
		if (razon_social == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: razon_social ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (razon_social.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: razon_social ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones

		try {
			if (salida.equalsIgnoreCase("OK")) {
				String ins = ""
						+ "INSERT INTO STOCKANEXOS(nint_ms_an, codigo_anexo, razon_social, domicilio, codigo_postal, idlocalidad, idprovincia, cuit, iibb, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = conn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, nint_ms_an);
				insert.setBigDecimal(2, codigo_anexo);
				insert.setString(3, razon_social);
				insert.setString(4, domicilio);
				insert.setString(5, codigo_postal);
				insert.setBigDecimal(6, idlocalidad);
				insert.setBigDecimal(7, idprovincia);
				insert.setString(8, cuit);
				insert.setString(9, iibb);
				insert.setString(10, usuarioalt);
				insert.setBigDecimal(11, idempresa);
				int n = insert.executeUpdate();
				if (n != 1)
					salida = "Imposible generar anexo.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String stockAnexosCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error excepcion public String stockAnexosCreate(.....)"
					+ ex);
		}
		return salida;
	}

	public List getStockAnexosNint(BigDecimal nint_ms_an, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT sa.idanexo, sa.nint_ms_an, sa.codigo_anexo, sa.razon_social, sa.domicilio,sa.codigo_postal, sa.idlocalidad, lo.localidad, "
				+ "       sa.idprovincia, pr.provincia, sa.cuit, sa.iibb, sa.usuarioalt, sa.usuarioact, sa.fechaalt, sa.fechaact "
				+ "  FROM stockanexos sa "
				+ "       INNER JOIN globallocalidades lo ON sa.idlocalidad = lo.idlocalidad "
				+ "       INNER JOIN globalprovincias pr  ON sa.idprovincia = pr.idprovincia  "
				+ "WHERE sa.nint_ms_an=" + nint_ms_an.toString()
				+ "  AND sa.idempresa=" + idempresa.toString();

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: stockContStock Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Wed Nov 01 11:08:52 GMT-03:00 2006
	 */

	/**
	 * TODO: stockContStockCreate - REPLICADO EN ClientesBean ...............
	 * EJV - 20070824 - .....................................................
	 * COMENTARIOS: analizar la posibilidad de concentrar ...................
	 * metodo en una clase, recibiendo como nuevo parametro la coneccion, y .
	 * haciendo estatico al mismo. ..........................................
	 * Metodos para la entidad: stockContStock Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Wed Nov 01 11:08:52 GMT-03:00 2006
	 */

	// grabacion de un nuevo registro
	public static String stockContStockCreate(BigDecimal nint_ms_cs,
			BigDecimal cuenta_cs, BigDecimal importe_cs, String tipo_cs,
			String sistema_cs, BigDecimal centr1_cs, BigDecimal centr2_cs,
			Connection conn, String usuarioalt, BigDecimal idempresa)
			throws EJBException {
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (cuenta_cs == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cuenta_cs ";
		if (importe_cs == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: importe_cs ";
		if (tipo_cs == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipo_cs ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (tipo_cs.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipo_cs ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones

		try {
			if (salida.equalsIgnoreCase("OK")) {
				String ins = "INSERT INTO STOCKCONTSTOCK(nint_ms_cs, cuenta_cs, importe_cs, tipo_cs, sistema_cs, centr1_cs, centr2_cs, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = conn.prepareStatement(ins);
				// seteo de campos:

				insert.setBigDecimal(1, nint_ms_cs);
				insert.setBigDecimal(2, cuenta_cs);
				insert.setBigDecimal(3, importe_cs);
				insert.setString(4, tipo_cs);
				insert.setString(5, sistema_cs);
				insert.setBigDecimal(6, centr1_cs);
				insert.setBigDecimal(7, centr2_cs);
				insert.setString(8, usuarioalt);
				insert.setBigDecimal(9, idempresa);
				int n = insert.executeUpdate();
				if (n != 1)
					salida = "Imposible generar datos contables.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String stockContStockCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String stockContStockCreate(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * TODO: stockContStockImporteUpdate - REPLICADO EN ClientesBean ........
	 * EJV - 20070824 - .....................................................
	 * COMENTARIOS: analizar la posibilidad de concentrar ...................
	 * metodo en una clase, recibiendo como nuevo parametro la coneccion, y .
	 * haciendo estatico al mismo. ..........................................
	 * Metodos para la entidad: stockContStock Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Wed Nov 01 11:08:52 GMT-03:00 2006
	 */

	public static String stockContStockImporteUpdate(BigDecimal nint_ms_cs,
			BigDecimal cuenta_cs, BigDecimal importe_cs, Connection conn,
			BigDecimal idempresa) throws EJBException {
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (nint_ms_cs == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nrointerno ";
		if (cuenta_cs == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cuenta_cs ";
		if (importe_cs == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: importe_cs ";

		try {
			PreparedStatement insert = null;
			String sql = "";
			if (salida.equalsIgnoreCase("OK")) { // si existe hago update
				sql = ""
						+ "UPDATE STOCKCONTSTOCK "
						+ "    SET  importe_cs = (importe_cs + ?)  "
						+ "  WHERE nint_ms_cs=? AND cuenta_cs=? AND idempresa=?;";
				insert = conn.prepareStatement(sql);

				insert.setBigDecimal(1, importe_cs);
				insert.setBigDecimal(2, nint_ms_cs);
				insert.setBigDecimal(3, cuenta_cs);
				insert.setBigDecimal(4, idempresa);
			}
			int i = insert.executeUpdate();
			if (i != 1)
				salida = "Imposible actualizar el total cuenta.";
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error SQL public String stockContStockImporteUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String stockContStockImporteUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * Metodos para la entidad: stockRemitos Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Wed Nov 01 11:58:18 GMT-03:00 2006
	 */

	// grabacion de un nuevo registro
	public static String stockRemitosCreate(BigDecimal nint_ms_rm,
			BigDecimal remito_rm, Timestamp fecha_rm, String tipo_rm,
			String tipomov_rm, BigDecimal codigo_rm, String marcado_rm,
			BigDecimal sucurs_rm, Connection conn, String usuarioalt,
			BigDecimal idempresa) throws EJBException {
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (remito_rm == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: remito_rm ";
		if (codigo_rm == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_rm ";
		if (sucurs_rm == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: sucurs_rm ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones

		try {
			if (salida.equalsIgnoreCase("OK")) {
				String ins = "INSERT INTO STOCKREMITOS(nint_ms_rm, remito_rm, fecha_rm, tipo_rm, tipomov_rm, codigo_rm, marcado_rm, sucurs_rm, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = conn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, nint_ms_rm);
				insert.setBigDecimal(2, remito_rm);
				insert.setTimestamp(3, fecha_rm);
				insert.setString(4, tipo_rm);
				insert.setString(5, tipomov_rm);
				insert.setBigDecimal(6, codigo_rm);
				insert.setString(7, marcado_rm);
				insert.setBigDecimal(8, sucurs_rm);
				insert.setString(9, usuarioalt);
				insert.setBigDecimal(10, idempresa);
				int n = insert.executeUpdate();
				if (n != 1)
					salida = "Imposible generar remitos.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String stockRemitosCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error excepcion public String stockRemitosCreate(.....)"
					+ ex);
		}
		return salida;
	}

	/**
	 * Metodos para la entidad: vStockValorizado Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Mon Nov 13 12:17:43 GMT-03:00 2006
	 */
	// para todo (ordena por el segundo campo por defecto)
	public List getVStockValorizadoAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT codigo_st,descrip_st,disponible, reservado, existencia,costo, valor "
				+ "  FROM VSTOCKVALORIZADO WHERE idempresa = "
				+ idempresa.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getVStockValorizadoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		String cQuery = ""

				+ "SELECT  codigo_st,descrip_st,disponible, reservado, existencia,costo, valor "
				+ "  FROM VSTOCKVALORIZADO WHERE "
				+ " (UPPER(CODIGO_ST) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%' "
				+ "    OR UPPER(DESCRIP_ST) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%') AND idempresa = "
				+ idempresa.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: vMovArtFecha Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Tue Nov 14 09:20:44 GMT-03:00 2006
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getVMovArtFechaAll(java.sql.Date fecha_ms, long limit,
			long offset, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ " SELECT articu_ms, articulo, sum(canti_ms)as cantidad "
				+ "   FROM VMOVARTFECHA  WHERE fecha_ms <= '" + fecha_ms
				+ "'::date  AND idempresa = " + idempresa.toString()
				+ "  GROUP BY articu_ms, articulo ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public long getTotalStockAgrupadoFecha(java.sql.Date fecha_ms,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		long total = 0;
		String cQuery = " SELECT count(distinct articu_ms) "
				+ "  FROM VMOVARTFECHA  WHERE fecha_ms <= '" + fecha_ms
				+ "'::date  AND idempresa = " + idempresa.toString() + ";";

		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);

			if (rsSalida.next()) {
				total = rsSalida.getLong(1);
			}
		} catch (SQLException sqlException) {
			log.error("Error SQL en el metodo : getTotalStockAgrupadoFecha() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getTotalStockAgrupadoFecha()  "
							+ ex);
		}
		return total;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getVMovArtFechaOcu(long limit, long offset,
			java.sql.Date fecha_ms, String ocurrencia, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT articu_ms, articulo, sum(canti_ms)as cantidad "
				+ "  FROM VMOVARTFECHA " + " WHERE fecha_ms <= '" + fecha_ms
				+ "'::date AND (UPPER(ARTICU_MS) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%'  OR UPPER(ARTICULO) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%') AND idempresa = "
				+ idempresa.toString()
				+ " GROUP BY articu_ms, articulo ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public long getTotalStockAgrupadoFechaOcu(java.sql.Date fecha_ms,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		long total = 0;
		String cQuery = " SELECT count(distinct articu_ms) "
				+ "  FROM VMOVARTFECHA  WHERE fecha_ms <= '" + fecha_ms
				+ "'::date AND (UPPER(ARTICU_MS) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%'  OR UPPER(ARTICULO) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%') AND idempresa = "
				+ idempresa.toString() + " GROUP BY articu_ms;";

		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);

			if (rsSalida.next()) {
				total = rsSalida.getLong(1);
			}
		} catch (SQLException sqlException) {
			log.error("Error SQL en el metodo : getTotalStockAgrupadoFecha() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getTotalStockAgrupadoFecha()  "
							+ ex);
		}
		return total;
	}

	/**
	 * Metodos para la entidad: vTotalArticuloDepositoFecha Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Tue Nov 14 09:20:44 GMT-03:00 2006
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getVTotalArtDepositoFechaAll(java.sql.Date fecha_ms,
			long limit, long offset, BigDecimal idempresa, String usuario)
			throws EJBException {
		String cQuery = ""
				+ " SELECT articu_ms, articulo,  SUM(canti_sh) as cantidad, descrip_dt "
				+ "  FROM vTotalArticuloDepositoFecha  WHERE fecha_ms <= '"
				+ fecha_ms + "'::date AND idempresa = " + idempresa.toString();

		if (hasDepositosAsociados(usuario, idempresa)) {
			cQuery += " and codigo_dt in "
					+ getQueryDepositosAsociados(usuario, idempresa);
		}

		cQuery += " GROUP BY articu_ms, articulo, descrip_dt ORDER BY 2  LIMIT "
				+ limit + " OFFSET  " + offset + ";";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public long getTotalStockDepositoAgrupadoFecha(java.sql.Date fecha_ms,
			BigDecimal idempresa, String usuario) throws EJBException {
		ResultSet rsSalida = null;
		long total = 0;
		String cQuery = " SELECT count(distinct articu_ms||descrip_dt) "
				+ "  FROM vTotalArticuloDepositoFecha  WHERE fecha_ms <= '"
				+ fecha_ms + "'::date  AND idempresa = " + idempresa.toString()
				+ "";
		if (hasDepositosAsociados(usuario, idempresa)) {
			cQuery += " and codigo_dt in "
					+ getQueryDepositosAsociados(usuario, idempresa);
		}

		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);

			if (rsSalida.next()) {
				total = rsSalida.getLong(1);
			}
		} catch (SQLException sqlException) {
			log
					.error("Error SQL en el metodo : getTotalStockDepositoAgrupadoFecha() "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getTotalStockDepositoAgrupadoFecha()  "
							+ ex);
		}
		return total;
	}

	public List getVTotalArtDepositoFechaOcu(long limit, long offset,
			java.sql.Date fecha_ms, String ocurrencia, BigDecimal idempresa,
			String usuario) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ " SELECT articu_ms, articulo,  SUM(canti_sh) as cantidad, descrip_dt "
				+ "  FROM vTotalArticuloDepositoFecha "
				+ " WHERE fecha_ms <= '" + fecha_ms
				+ "'::date AND idempresa = " + idempresa.toString();
		if (hasDepositosAsociados(usuario, idempresa)) {
			cQuery += " and codigo_dt in "
					+ getQueryDepositosAsociados(usuario, idempresa);
		}
		cQuery += " AND (UPPER(ARTICU_MS) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%'  OR UPPER(ARTICULO) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%') GROUP BY articu_ms, articulo, descrip_dt ORDER BY 2  LIMIT "
				+ limit + " OFFSET  " + offset + ";";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public long getTotalStockAgrupadoDepositoFechaOcu(java.sql.Date fecha_ms,
			String ocurrencia, BigDecimal idempresa, String usuario)
			throws EJBException {
		ResultSet rsSalida = null;
		long total = 0;
		String cQuery = " "
				+ " SELECT count(distinct articu_ms || descrip_dt) AS TOTAL "
				+ "           FROM vTotalArticuloDepositoFecha  "
				+ "          WHERE fecha_ms <= '" + fecha_ms
				+ "'::date AND idempresa=" + idempresa.toString();
		if (hasDepositosAsociados(usuario, idempresa)) {
			cQuery += " and codigo_dt in "
					+ getQueryDepositosAsociados(usuario, idempresa);
		}

		cQuery += "            AND (UPPER(ARTICU_MS) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%'           OR UPPER(ARTICULO) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%') ;";

		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);

			if (rsSalida.next()) {
				total = rsSalida.getLong(1);
			}
		} catch (SQLException sqlException) {
			log
					.error("Error SQL en el metodo : getTotalStockAgrupadoDepositoFechaOcu() "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getTotalStockAgrupadoDepositoFechaOcu()  "
							+ ex);
		}
		return total;
	}

	// ***********************************
	public List getProveedoresAll(long limit, long offset, BigDecimal idempresa)
			throws EJBException {
		String cQuery = "SELECT idproveedor,razon_social FROM proveedoproveed WHERE idempresa= "
				+ idempresa.toString()
				+ " ORDER BY 2  LIMIT "
				+ limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getProveedoresOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		String cQuery = "SELECT idproveedor,razon_social FROM proveedoproveed "
				+ " where idempresa= " + idempresa.toString()
				+ " and (idproveedor::VARCHAR LIKE '%" + ocurrencia + "%' OR "
				+ " UPPER(razon_social) LIKE '%" + ocurrencia.toUpperCase()
				+ "%') " + " ORDER BY 2";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getStockfamiliasLovAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT  codigo_fm,descrip_fm,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM STOCKFAMILIAS WHERE idempresa= "
				+ idempresa.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getStockfamiliasLovOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  codigo_fm,descrip_fm,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM STOCKFAMILIAS "
				+ " where idempresa= "
				+ idempresa.toString()
				+ " and (codigo_fm::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " UPPER(descrip_fm) LIKE '%"
				+ ocurrencia.toUpperCase() + "%') " + " ORDER BY 2";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getStockgruposLovAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT codigo_gr,descrip_gr,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "FROM stockgrupos  WHERE idempresa = "
				+ idempresa.toString()
				+ " ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getStockgruposLovOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT  codigo_gr,descrip_gr,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM stockgrupos " + " where idempresa= "
				+ idempresa.toString() + " and (codigo_gr::VARCHAR LIKE '%"
				+ ocurrencia + "%' OR " + " UPPER(descrip_gr) LIKE '%"
				+ ocurrencia.toUpperCase() + "%') " + " ORDER BY 2";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getStockdepositosLovAll(long limit, long offset,
			BigDecimal idempresa, String usuario) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT  codigo_dt,descrip_dt,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM stockdepositos WHERE idempresa = "
				+ idempresa.toString();

		if (hasDepositosAsociados(usuario, idempresa)) {
			cQuery += " and codigo_dt in "
					+ getQueryDepositosAsociados(usuario, idempresa);
		}

		cQuery += " ORDER BY 1  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getStockdepositosLovOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa, String usuario)
			throws EJBException {
		ResultSet rsSalida = null;

		String cQuery = ""
				+ "SELECT  codigo_dt,descrip_dt,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM stockdepositos " + " where idempresa= "
				+ idempresa.toString();
		cQuery += " and (codigo_dt::VARCHAR LIKE '%" + ocurrencia + "%' OR "
				+ " UPPER(descrip_dt) LIKE '%" + ocurrencia.toUpperCase()
				+ "%') ";
		if (hasDepositosAsociados(usuario, idempresa)) {
			cQuery += " and codigo_dt in "
					+ getQueryDepositosAsociados(usuario, idempresa);
		}
		cQuery += " ORDER BY 1";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: vRemitoInterno Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Fri Feb 16 16:20:02 GMT-03:00 2007
	 */
	// por primary key (primer campo por defecto)
	public List getVRemitoInternoPK(BigDecimal remito_interno,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT remito_interno,fecha,tipo,codprod,producto,coddepo,deposito,"
				+ "       direccion,cantidad,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM VREMITOINTERNO WHERE remito_interno="
				+ remito_interno.toString() + " AND idempresa = "
				+ idempresa.toString();

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getReImprimeCambioDepositoAll(long limit, long offset,
			String user, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT DISTINCT o.comprob_ms, o.fecha AS fecha, "
				+ "       o.coddepo AS idorigen, o.deposito AS origen, "
				+ "       d.coddepo AS iddestino, d.deposito AS destino "
				+ "  FROM vremitointerno o "
				+ "       INNER JOIN vremitointerno d ON o.comprob_ms = d.comprob_ms "
				+ "              AND o.codprod = d.codprod "
				+ "              AND o.idempresa = d.idempresa "
				+ "              AND d.tipo = 'ENTRADA' "
				+ " WHERE o.tipoaux_ms = 'C' "
				+ "   AND o.tipo = 'SALIDA' AND o.idempresa = "
				+ idempresa.toString()
				+ (hasDepositosAsociados(user, idempresa) ? (" AND ( o.coddepo IN  "
						+ getQueryDepositosAsociados(user, idempresa)
						+ "or d.coddepo IN " + getQueryDepositosAsociados(user,
						idempresa))
						+ ")"
						: "") + " ORDER BY 1 desc LIMIT " + limit + " OFFSET "
				+ offset;

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getReImprimeCambioDepositoOcu(long limit, long offset,
			String user, String ocurrencia, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT DISTINCT o.comprob_ms, o.fecha AS fecha, "
				+ "       o.coddepo AS idorigen, o.deposito AS origen, "
				+ "       d.coddepo AS iddestino, d.deposito AS destino "
				+ "  FROM vremitointerno o "
				+ "       INNER JOIN vremitointerno d ON o.comprob_ms = d.comprob_ms "
				+ "              AND o.codprod = d.codprod "
				+ "              AND o.idempresa = d.idempresa "
				+ "              AND d.tipo = 'ENTRADA' "
				+ " WHERE o.tipoaux_ms = 'C' "
				+ "   AND o.tipo = 'SALIDA' "// AND o.idempresa = "
				// + idempresa.toString()
				+ (hasDepositosAsociados(user, idempresa) ? (" AND o.coddepo IN  " + getQueryDepositosAsociados(
						user, idempresa))
						: "")

				+ " and ((o.comprob_ms::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR TO_CHAR(o.fecha, 'dd/mm/yyyy') LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(o.deposito) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(d.deposito) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%') AND o.idempresa = "
				+ idempresa.toString() + " ORDER BY 1  LIMIT " + limit
				+ " OFFSET  " + offset + ";";

		// + " AND o.comprob_ms LIKE '" + ocurrencia
		// + "%' ORDER BY 1 desc LIMIT " + limit + " OFFSET " + offset;

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getReImprimeMovEntradaSalidaAll(long limit, long offset,
			String inout, String user, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT DISTINCT remito_interno, sucu_ms, remito_interno_ms, fecha , coddepo , deposito   "
				+ "  FROM VREMITOINTERNO "
				+ " WHERE tipo = '"
				+ inout.toUpperCase()
				+ "' "
				+ "   AND (tipoaux_ms IS NULL OR tipoaux_ms <> 'C')	"
				+ "   AND idempresa = "
				+ idempresa.toString()
				+ (hasDepositosAsociados(user, idempresa) ? (" AND coddepo IN  " + getQueryDepositosAsociados(
						user, idempresa))
						: "") + " ORDER BY 1 desc  LIMIT " + limit + " OFFSET "
				+ offset;

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getReImprimeMovEntradaSalidaOcu(long limit, long offset,
			String inout, String user, String ocurrencia, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT DISTINCT remito_interno, sucu_ms, remito_interno_ms, fecha , coddepo , deposito  "
				+ "  FROM VREMITOINTERNO "
				+ " WHERE tipo = '"
				+ inout.toUpperCase()
				+ "' AND remito_interno::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%'"

				// + " or (UPPER(deposito) LIKE '%"
				// + ocurrencia.toUpperCase().trim()+ "%')"

				+ "   AND (tipoaux_ms IS NULL OR tipoaux_ms <> 'C')	"
				+ "   AND idempresa = "
				+ idempresa.toString()
				+ (hasDepositosAsociados(user, idempresa) ? (" AND coddepo IN  " + getQueryDepositosAsociados(
						user, idempresa))
						: "") + " ORDER BY 1 desc LIMIT " + limit + " OFFSET "
				+ offset;

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// total All globallocalidades
	public long gettotalgloballocalidadesAll() throws EJBException {

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
		String cQuery = "SELECT count(1)AS total FROM  globallocalidades ";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				total = rsSalida.getLong("total");
			} else {
				log
						.warn("getgloballocalidadesAll()- Error al recuperar total: ");
			}
		} catch (SQLException sqlException) {
			log.error("getgloballocalidadesAll()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getgloballocalidadesAll()- Salida por exception: " + ex);
		}
		return total;
	}

	// total Ocu globallocalidades
	public long gettotalgloballocalidadesOcu(String[] campos, String ocurrencia)
			throws EJBException {
		/**
		 * Entidad: globallocalidades
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer usuario por ocurrencia.
		 */
		long total = 0l;
		ResultSet rsSalida = null;
		String cQuery = "SELECT count(1)AS total FROM globallocalidades ";
		String like = "";
		int len = campos.length;
		try {
			for (int i = 0; i < len; i++) {
				like += " UPPER(" + campos[i] + "::VARCHAR) LIKE '%"
						+ ocurrencia.toUpperCase() + "%' ";
				if (i + 1 < len)
					like += " OR ";
			}
			cQuery += len > 0 ? " where (" + like + ")" : "";
			// cQuery += "(" + like + " )";
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				total = rsSalida.getLong("total");
			} else {
				log
						.warn("gettotalgloballocalidadesOcu()- Error al recuperar total: ");
			}
		} catch (SQLException sqlException) {
			log.error("gettotalgloballocalidadesOcu()- Error SQL: "
					+ sqlException);
		} catch (Exception ex) {
			log.error("gettotalgloballocalidadesOcu()- Salida por exception: "
					+ ex);
		}
		return total;
	}

	// -- informes

	public List getMovArticuloDepositoSaldoAnterior(String codigo_st,
			BigDecimal iddeposito, String fechadesde, String fechahasta,
			BigDecimal idempresa, String usuario) throws EJBException {
		/*
		 * Objetivo: Traer el saldo anterior a la fecha desde para un producto
		 * en el informe solicitado
		 */

		String cQuery = "" + "select 'Saldo Anterior'::text as concepto,"
				+ "coalesce(sum(cantidad),0) as total "
				+ " from vMovStockArticulosDepostitoCantidades "
				+ " where fechamovimiento < to_date('" + fechadesde
				+ "','DD/MM/YYYY')  " + " and cproducto = '" + codigo_st
				+ "' and cdeposito = " + iddeposito.toString()
				+ " and idempresa = " + idempresa.toString() + "";

		// modificacion para extranet de distribuidores
		if (hasDepositosAsociados(usuario, idempresa)) {
			cQuery += " and cdeposito in "
					+ getQueryDepositosAsociados(usuario, idempresa);
		}

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getExistencias4Deposito(BigDecimal iddeposito,
			BigDecimal idempresa) throws EJBException {
		/*
		 * Objetivo: Traer el saldo anterior a la fecha desde para un producto
		 * en el informe solicitado 03/03/2009: cep: cambio el filtro y pongo
		 * que tenga existencia a proposito
		 */

		String cQuery = ""
				+ "SELECT codigo_st, descrip_st, disponible::numeric(18,2) as disponible, reservado::numeric(18,2) as reservado, existencia::numeric(18,2) as existencia, descrip_md FROM vstocktotaldeposito "
				+ " WHERE existencia <> 0 AND idempresa = "
				+ idempresa.toString() + " AND codigo_dt = "
				+ iddeposito.toString() + " ORDER BY 1 ";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getMovArticuloDepositoDetalle(String codigo_st,
			BigDecimal iddeposito, String fechadesde, String fechahasta,
			BigDecimal idempresa, String usuario) throws EJBException {
		/*
		 * Objetivo: Traer el saldo anterior a la fecha desde para un producto
		 * en el informe solicitado
		 */
		ResultSet rsSalida = null;
		String cQuery = ""
				+ " select to_char(fechamovimiento,'dd/mm/yyyy') as fecha ,cantidad,upper(tipo) as tipo, upper(usuarioresponsable) as usuarioresponsable, comprob_ms, observaciones "
				+ " from vMovStockArticulosDepostitoCantidades  "
				+ " where fechamovimiento " + "  between to_date('"
				+ fechadesde + "','DD/MM/YYYY') and to_date('" + fechahasta
				+ "','DD/MM/YYYY') " + " and cproducto = '" + codigo_st
				+ "' and cdeposito = " + iddeposito.toString()
				+ " and idempresa = " + idempresa.toString();
		if (hasDepositosAsociados(usuario, idempresa)) {
			cQuery += " and cdeposito in "
					+ getQueryDepositosAsociados(usuario, idempresa);
		}
		cQuery += " order by fechamovimiento " + "";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getArticulosPuntoMinimo(
	// String codigo_desde_st,
			// String codigo_hasta_st,
			BigDecimal iddepositodesde,
			// BigDecimal iddepositohasta,
			BigDecimal idempresa) throws EJBException {
		/*
		 * Objetivo: Traer el una consulta con aquellos productos que estan
		 * debajo del punto minimo de stock
		 */
		String cQuery = ""
				+ " SELECT codigo, producto, medida, puntominimo, deposito, cantidaddeposito "
				+ "    FROM vstockpuntominimo                "
				+ "   WHERE idempresa = " + idempresa.toString()
				+ "       AND codigodeposito = " + iddepositodesde + ";"
		// +"  WHERE idempresa = " + idempresa.toString() +
		// " AND codigo BETWEEN '" + codigo_desde_st + "' and '" +
		// codigo_hasta_st + "'" + " AND codigodeposito BETWEEN " +
		// iddepositodesde + " AND " + iddepositohasta + ";"
		;

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getMovArticuloDepositoSaldoFinal(String codigo_st,
			BigDecimal iddeposito, String fechadesde, String fechahasta,
			BigDecimal idempresa, String usuario) throws EJBException {
		/*
		 * Objetivo: Traer el saldo anterior a la fecha desde para un producto
		 * en el informe solicitado
		 */
		ResultSet rsSalida = null;
		String cQuery = "" + " select 'Saldo Final'::text as concepto,  "
				+ "  coalesce(sum(cantidad),0) as total  "
				+ "  from vMovStockArticulosDepostitoCantidades  "
				+ "  where fechamovimiento <= to_date('" + fechahasta
				+ "','DD/MM/YYYY')   " + "  and cproducto = '" + codigo_st
				+ "' and cdeposito = " + iddeposito.toString()
				+ " and idempresa = " + idempresa.toString() + " " + "  ";
		if (hasDepositosAsociados(usuario, idempresa)) {
			cQuery += " and cdeposito in "
					+ getQueryDepositosAsociados(usuario, idempresa);
		}
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// -- informe 2

	public List getMovDepositoDetalle(BigDecimal iddeposito, String fechadesde,
			String fechahasta, BigDecimal idempresa, String usuario)
			throws EJBException {
		/*
		 * Objetivo: Traer el saldo anterior a la fecha desde para un producto
		 * en el informe solicitado
		 */
		ResultSet rsSalida = null;
		String cQuery = ""
				+ " select to_char(fechamovimiento,'dd/mm/yyyy') as fecha, tipo, cproducto, producto, cantidad, usuarioresponsable, comprob_ms, observaciones "
				+ " from vMovStockArticulosDepostitoCantidades  "
				+ " where fechamovimiento " + "  between to_date('"
				+ fechadesde + "','DD/MM/YYYY') and to_date('" + fechahasta
				+ "','DD/MM/YYYY') " + "  and cdeposito = "
				+ iddeposito.toString() + " and idempresa = "
				+ idempresa.toString();
		if (hasDepositosAsociados(usuario, idempresa)) {
			cQuery += " and cdeposito in "
					+ getQueryDepositosAsociados(usuario, idempresa);
		}

		cQuery += " order by fechamovimiento,comprob_ms " + "";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// -- informe 3 -- igual al 1 pero sin deposito

	public List getMovArticuloSaldoAnterior(String codigo_st,
			String fechadesde, String fechahasta, BigDecimal idempresa)
			throws EJBException {
		/*
		 * Objetivo: Traer el saldo anterior a la fecha desde para un producto
		 * en el informe solicitado
		 */
		String cQuery = "" + "select 'Saldo Anterior'::text as concepto,"
				+ "coalesce(sum(cantidad),0) as total "
				+ " from vMovStockArticulosDepostitoCantidades "
				+ " where fechamovimiento < to_date('" + fechadesde
				+ "','DD/MM/YYYY')  " + " and cproducto = '" + codigo_st
				+ "' and idempresa = " + idempresa.toString() + "";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getMovArticuloDetalle(String codigo_st, String fechadesde,
			String fechahasta, BigDecimal idempresa) throws EJBException {
		/*
		 * Objetivo: Traer el saldo anterior a la fecha desde para un producto
		 * en el informe solicitado
		 */
		String cQuery = ""
				+ " select to_char(fechamovimiento,'dd/mm/yyyy') as fecha , cdeposito, deposito ,cantidad,upper(tipo) as tipo, upper(usuarioresponsable) as usuarioresponsable, comprob_ms, observaciones  "
				+ " from vMovStockArticulosDepostitoCantidades  "
				+ " where fechamovimiento " + "  between to_date('"
				+ fechadesde + "','DD/MM/YYYY') and to_date('" + fechahasta
				+ "','DD/MM/YYYY') " + " and cproducto = '" + codigo_st
				+ "' and idempresa = " + idempresa.toString()
				+ " order by fechamovimiento " + "";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getMovArticuloSaldoFinal(String codigo_st, String fechadesde,
			String fechahasta, BigDecimal idempresa) throws EJBException {
		/*
		 * Objetivo: Traer el saldo anterior a la fecha desde para un producto
		 * en el informe solicitado
		 */
		String cQuery = "" + " select 'Saldo Final'::text as concepto,  "
				+ "  coalesce(sum(cantidad),0) as total  "
				+ "  from vMovStockArticulosDepostitoCantidades  "
				+ "  where fechamovimiento <= to_date('" + fechahasta
				+ "','DD/MM/YYYY')   " + "  and cproducto = '" + codigo_st
				+ "'  and idempresa = " + idempresa.toString() + " " + "  ";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: stockMotivosDesarma Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Mon Dec 17 13:21:38 ART 2007
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getStockMotivosDesarmaAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "" + ""
				+ "SELECT idmotivodesarma,motivodesarma, idempresa,"
				+ "       usuarioalt,usuarioact,fechaalt,fechaact"
				+ "  FROM STOCKMOTIVOSDESARMA WHERE idempresa = "
				+ idempresa.toString() + "  ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getStockMotivosDesarmaOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		String cQuery = "SELECT  idmotivodesarma,motivodesarma,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM STOCKMOTIVOSDESARMA WHERE ((IDMOTIVODESARMA::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(MOTIVODESARMA) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%')  AND idempresa = "
				+ idempresa.toString()
				+ " ORDER BY 2  LIMIT "
				+ limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getStockMotivosDesarmaPK(BigDecimal idmotivodesarma,
			BigDecimal idempresa) throws EJBException {
		String cQuery = "SELECT  idmotivodesarma,motivodesarma,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM STOCKMOTIVOSDESARMA WHERE idmotivodesarma="
				+ idmotivodesarma.toString()
				+ " AND idempresa = "
				+ idempresa.toString() + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String stockMotivosDesarmaDelete(BigDecimal idmotivodesarma)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM STOCKMOTIVOSDESARMA WHERE idmotivodesarma="
				+ idmotivodesarma.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM STOCKMOTIVOSDESARMA WHERE idmotivodesarma="
						+ idmotivodesarma.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : stockMotivosDesarmaDelete( BigDecimal idmotivodesarma ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: stockMotivosDesarmaDelete( BigDecimal idmotivodesarma )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String stockMotivosDesarmaCreate(String motivodesarma,
			BigDecimal idempresa, String usuarioalt) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (motivodesarma == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: motivodesarma ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (motivodesarma.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: Motivo Desarmado ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO STOCKMOTIVOSDESARMA(motivodesarma, idempresa, usuarioalt ) VALUES (?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, motivodesarma);
				insert.setBigDecimal(2, idempresa);
				insert.setString(3, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String stockMotivosDesarmaCreate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String stockMotivosDesarmaCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String stockMotivosDesarmaCreateOrUpdate(BigDecimal idmotivodesarma,
			String motivodesarma, BigDecimal idempresa, String usuarioact)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idmotivodesarma == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idmotivodesarma ";
		if (motivodesarma == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: motivodesarma ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (motivodesarma.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: motivodesarma ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM stockMotivosDesarma WHERE idmotivodesarma = "
					+ idmotivodesarma.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE STOCKMOTIVOSDESARMA SET motivodesarma=?, idempresa=?, usuarioact=?, fechaact=? WHERE idmotivodesarma=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, motivodesarma);
					insert.setBigDecimal(2, idempresa);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, idmotivodesarma);
				} else {
					String ins = "INSERT INTO STOCKMOTIVOSDESARMA(motivodesarma, idempresa, usuarioalt ) VALUES (?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, motivodesarma);
					insert.setBigDecimal(2, idempresa);
					insert.setString(3, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String stockMotivosDesarmaCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String stockMotivosDesarmaCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String stockMotivosDesarmaUpdate(BigDecimal idmotivodesarma,
			String motivodesarma, BigDecimal idempresa, String usuarioact)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idmotivodesarma == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idmotivodesarma ";
		if (motivodesarma == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: motivodesarma ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (motivodesarma.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: motivodesarma ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM stockMotivosDesarma WHERE idmotivodesarma = "
					+ idmotivodesarma.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE STOCKMOTIVOSDESARMA SET motivodesarma=?, idempresa=?, usuarioact=?, fechaact=? WHERE idmotivodesarma=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, motivodesarma);
					insert.setBigDecimal(2, idempresa);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, idmotivodesarma);
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
					.error("Error SQL public String stockMotivosDesarmaUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String stockMotivosDesarmaUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * Metodos para la entidad: stockDesarmadoLog Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Wed Dec 26 14:49:56 ART 2007
	 */

	public List getStockDesarmadoLogAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT dl.idtransacciondesarmado, dl.idesquema, ec.esquema, dl.nrointerno_ms, dl.recursivo, dl.cantidad, "
				+ "       dl.idmotivodesarma, md.motivodesarma, ms.articu_ms, ms.canti_ms,  "
				+ "       dl.idempresa, dl.usuarioalt, dl.usuarioact, dl.fechaalt, dl.fechaact "
				+ "  FROM stockdesarmadolog dl "
				+ "       INNER JOIN produccionesquemas_cabe ec ON dl.idesquema = ec.idesquema AND dl.idempresa = ec.idempresa "
				+ "       INNER JOIN stockmotivosdesarma md ON dl.idmotivodesarma = md.idmotivodesarma AND dl.idempresa = md.idempresa "
				+ "       LEFT  JOIN stockmovstock ms ON dl.nrointerno_ms = ms.nrointerno_ms AND dl.idempresa = ms.idempresa "
				+ " WHERE dl.idempresa = " + idempresa.toString()
				+ " ORDER BY 1  LIMIT " + limit + " OFFSET  " + offset + ";";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**/

	public List getStockDesarmadoLogFecha(long limit, long offset,
			Timestamp fechadesde, Timestamp fechahasta, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT dl.idtransacciondesarmado, dl.idesquema, ec.esquema, dl.nrointerno_ms, dl.recursivo, dl.cantidad,"
				+ "       dl.idmotivodesarma, md.motivodesarma, ms.articu_ms, ms.canti_ms,  "
				+ "       dl.idempresa, dl.usuarioalt, dl.usuarioact, dl.fechaalt, dl.fechaact "
				+ "  FROM stockdesarmadolog dl "
				+ "       INNER JOIN produccionesquemas_cabe ec ON dl.idesquema = ec.idesquema AND dl.idempresa = ec.idempresa"
				+ "       INNER JOIN stockmotivosdesarma md ON dl.idmotivodesarma = md.idmotivodesarma AND dl.idempresa = md.idempresa "
				+ "       LEFT  JOIN stockmovstock ms ON dl.nrointerno_ms = ms.nrointerno_ms AND dl.idempresa = ms.idempresa "
				+ " WHERE dl.idempresa = " + idempresa.toString()
				+ " AND dl.fechaalt BETWEEN '" + fechadesde + "' AND '"
				+ fechahasta + "' ORDER BY 1  LIMIT " + limit + " OFFSET  "
				+ offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// carrito de compras
	// para todo (ordena por el segundo campo por defecto)
	public List getMarketformasdepagoAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		String cQuery = " SELECT idformapago,formapago,leyenda,enviodatos,idempresa,usuarioalt,usuarioact,fechaalt,fechaact "
				+ " FROM MARKETFORMASDEPAGO WHERE idempresa = "
				+ idempresa.toString()
				+ "  ORDER BY 2  LIMIT "
				+ limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getMarketformasdepagoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		String cQuery = "SELECT  idformapago,formapago,leyenda,enviodatos,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM MARKETFORMASDEPAGO "
				+ " WHERE ((idformapago::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(formapago) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%') AND idempresa = "
				+ idempresa.toString()
				+ " ORDER BY 1  LIMIT "
				+ limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getMarketformasdepagoPK(BigDecimal idformapago,
			BigDecimal idempresa) throws EJBException {
		String cQuery = "SELECT  idformapago,formapago,leyenda,enviodatos,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM MARKETFORMASDEPAGO WHERE idformapago="
				+ idformapago.toString()
				+ " AND idempresa = "
				+ idempresa.toString() + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String marketformasdepagoDelete(BigDecimal idformapago,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM MARKETFORMASDEPAGO"
				+ " WHERE idformapago=" + idformapago.toString()
				+ " and idempresa = " + idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM MARKETFORMASDEPAGO "
						+ " WHERE idformapago=" + idformapago.toString()
						+ " and idempresa = " + idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : marketformasdepagoDelete( BigDecimal idformapago ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: marketformasdepagoDelete( BigDecimal idformapago )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String marketformasdepagoCreate(String formapago, String leyenda,
			String enviodatos, BigDecimal idempresa, String usuarioalt)
			throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (formapago == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: formapago ";
		if (leyenda == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: leyenda ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (formapago.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: formapago ";
		if (leyenda.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: leyenda ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO MARKETFORMASDEPAGO(formapago, leyenda, enviodatos, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, formapago);
				insert.setString(2, leyenda);
				insert.setString(3, enviodatos);
				insert.setBigDecimal(4, idempresa);
				insert.setString(5, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String marketformasdepagoCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String marketformasdepagoCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String marketformasdepagoCreateOrUpdate(BigDecimal idformapago,
			String formapago, String leyenda, String enviodatos,
			BigDecimal idempresa, String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idformapago == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idformapago ";
		if (formapago == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: formapago ";
		if (leyenda == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: leyenda ";

		// 2. sin nada desde la pagina
		if (formapago.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: formapago ";
		if (leyenda.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: leyenda ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM marketformasdepago WHERE idformapago = "
					+ idformapago.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE MARKETFORMASDEPAGO SET formapago=?, leyenda=?, enviodatos=?, idempresa=?, usuarioact=?, fechaact=? WHERE idformapago=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, formapago);
					insert.setString(2, leyenda);
					insert.setString(3, enviodatos);
					insert.setBigDecimal(4, idempresa);
					insert.setString(5, usuarioact);
					insert.setTimestamp(6, fechaact);
					insert.setBigDecimal(7, idformapago);
				} else {
					String ins = "INSERT INTO MARKETFORMASDEPAGO(formapago, leyenda, enviodatos, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, formapago);
					insert.setString(2, leyenda);
					insert.setString(3, enviodatos);
					insert.setBigDecimal(4, idempresa);
					insert.setString(5, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String marketformasdepagoCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String marketformasdepagoCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String marketformasdepagoUpdate(BigDecimal idformapago,
			String formapago, String leyenda, String enviodatos,
			BigDecimal idempresa, String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idformapago == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idformapago ";
		if (formapago == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: formapago ";
		if (leyenda == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: leyenda ";

		// 2. sin nada desde la pagina
		if (formapago.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: formapago ";
		if (leyenda.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: leyenda ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM marketformasdepago WHERE idformapago = "
					+ idformapago.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE MARKETFORMASDEPAGO SET formapago=?, leyenda=?, enviodatos=?, idempresa=?, usuarioact=?, fechaact=? WHERE idformapago=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, formapago);
					insert.setString(2, leyenda);
					insert.setString(3, enviodatos);
					insert.setBigDecimal(4, idempresa);
					insert.setString(5, usuarioact);
					insert.setTimestamp(6, fechaact);
					insert.setBigDecimal(7, idformapago);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String marketformasdepagoUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String marketformasdepagoUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// depositos
	public List getMarketmarketdepositosAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		String cQuery = " SELECT MARKETMARKETDEPOSITOS.idmarketdeposito,stockdepositos.descrip_dt,MARKETMARKETDEPOSITOS.idempresa,MARKETMARKETDEPOSITOS.usuarioalt,MARKETMARKETDEPOSITOS.usuarioact,MARKETMARKETDEPOSITOS.fechaalt,MARKETMARKETDEPOSITOS.fechaact FROM MARKETMARKETDEPOSITOS,stockdepositos where stockdepositos.codigo_dt = MARKETMARKETDEPOSITOS.codigo_dt and stockdepositos.idempresa  = MARKETMARKETDEPOSITOS.idempresa and MARKETMARKETDEPOSITOS.idempresa = "
				+ idempresa.toString()
				+ "  ORDER BY 2  LIMIT "
				+ limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getMarketmarketdepositosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		String cQuery = " SELECT MARKETMARKETDEPOSITOS.idmarketdeposito,stockdepositos.descrip_dt,MARKETMARKETDEPOSITOS.idempresa,MARKETMARKETDEPOSITOS.usuarioalt,MARKETMARKETDEPOSITOS.usuarioact,MARKETMARKETDEPOSITOS.fechaalt,MARKETMARKETDEPOSITOS.fechaact FROM MARKETMARKETDEPOSITOS,stockdepositos where stockdepositos.codigo_dt = MARKETMARKETDEPOSITOS.codigo_dt and stockdepositos.idempresa  = MARKETMARKETDEPOSITOS.idempresa"
				+ " and ((MARKETMARKETDEPOSITOS.idmarketdeposito::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(stockdepositos.descrip_dt) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%') AND MARKETMARKETDEPOSITOS.idempresa = "
				+ idempresa.toString()
				+ " ORDER BY 1  LIMIT "
				+ limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getMarketmarketdepositosPK(BigDecimal idmarketdeposito,
			BigDecimal idempresa) throws EJBException {
		String cQuery = "SELECT MARKETMARKETDEPOSITOS.idmarketdeposito,MARKETMARKETDEPOSITOS.codigo_dt,stockdepositos.descrip_dt,MARKETMARKETDEPOSITOS.idempresa,MARKETMARKETDEPOSITOS.usuarioalt,MARKETMARKETDEPOSITOS.usuarioact,MARKETMARKETDEPOSITOS.fechaalt,MARKETMARKETDEPOSITOS.fechaact FROM MARKETMARKETDEPOSITOS,stockdepositos where stockdepositos.codigo_dt = MARKETMARKETDEPOSITOS.codigo_dt and stockdepositos.idempresa  = MARKETMARKETDEPOSITOS.idempresa "
				+ " and MARKETMARKETDEPOSITOS.idmarketdeposito ="
				+ idmarketdeposito.toString()
				+ " AND MARKETMARKETDEPOSITOS.idempresa = "
				+ idempresa.toString() + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String MarketmarketdepositosDelete(BigDecimal idmarketdeposito,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM MARKETMARKETDEPOSITOS "
				+ " WHERE idmarketdeposito =" + idmarketdeposito.toString()
				+ " and idempresa = " + idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM MARKETMARKETDEPOSITOS "
						+ " WHERE idmarketdeposito="
						+ idmarketdeposito.toString() + " and idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : MarketmarketdepositosDelete( BigDecimal idmarketdeposito ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: MarketmarketdepositosDelete( BigDecimal idmarketdeposito )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String MarketmarketdepositosCreate(BigDecimal codigo_dt,
			BigDecimal idempresa, String usuarioalt) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (codigo_dt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_dt ";
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
				String ins = "INSERT INTO MARKETMARKETDEPOSITOS(codigo_dt, idempresa, usuarioalt ) VALUES (?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, codigo_dt);
				insert.setBigDecimal(2, idempresa);
				insert.setString(3, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String MarketmarketdepositosCreate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String MarketmarketdepositosCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String MarketmarketdepositosCreateOrUpdate(
			BigDecimal idmarketdeposito, BigDecimal codigo_dt,
			BigDecimal idempresa, String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idmarketdeposito == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idmarketdeposito ";
		if (codigo_dt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_dt ";

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM Marketmarketdepositos WHERE idmarketdeposito = "
					+ idmarketdeposito.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE MARKETMARKETDEPOSITOS SET codigo_dt=?, idempresa=?, usuarioact=?, fechaact=? WHERE idmarketdeposito=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, codigo_dt);
					insert.setBigDecimal(2, idempresa);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, idmarketdeposito);
				} else {
					String ins = "INSERT INTO MARKETMARKETDEPOSITOS(codigo_dt, idempresa, usuarioalt ) VALUES (?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setBigDecimal(1, codigo_dt);
					insert.setBigDecimal(2, idempresa);
					insert.setString(3, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String MarketmarketdepositosCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String MarketmarketdepositosCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String MarketmarketdepositosUpdate(BigDecimal idmarketdeposito,
			BigDecimal codigo_dt, BigDecimal idempresa, String usuarioact)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idmarketdeposito == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idmarketdeposito ";
		if (codigo_dt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_dt ";

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM Marketmarketdepositos WHERE idmarketdeposito = "
					+ idmarketdeposito.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE MARKETMARKETDEPOSITOS SET codigo_dt=?, idempresa=?, usuarioact=?, fechaact=? WHERE idmarketdeposito=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, codigo_dt);
					insert.setBigDecimal(2, idempresa);
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, idmarketdeposito);
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
					.error("Error SQL public String MarketmarketdepositosUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String MarketmarketdepositosUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * Metodo GENERA Registro: marketRegistro - marketRegistroDireccion
	 * Copyrigth(r) sysWarp S.R.L. Fecha de creacion: Fri Mar 14 11:34:38 ART
	 * 2008
	 */

	public String marketCreateRegistroAndPedido(BigDecimal idcliente,
			String email, String pass, String activo, BigDecimal total,
			BigDecimal idformadepago, String comentarios, String obsentrega,
			Hashtable htDireccion, Hashtable htCarrito, BigDecimal idempresa,
			String usuarioalt) throws EJBException, SQLException {

		String salida = "OK";
		BigDecimal idestado = new BigDecimal(-1);
		BigDecimal idpedicabe = new BigDecimal(-1);

		dbconn.setAutoCommit(false);

		try {

			if (idcliente == null || idcliente.longValue() < 1) {

				idcliente = getMarketRegistroIdCliente(email, pass, idempresa);

				if (idcliente == null || idcliente.longValue() < 1) {

					salida = marketRegistroCreate(email, pass, activo,
							idempresa, usuarioalt);
					idcliente = GeneralBean.getValorSequencia(
							"seq_marketregistro", dbconn);
				}

			}

			if (idcliente.compareTo(new BigDecimal(0)) != 1) {
				salida = "No se pudo recuperar cliente.";
			}

			if (salida.equalsIgnoreCase("OK")) {// Pedido

				if (htCarrito != null && !htCarrito.isEmpty()) {// Carrito lleno

					salida = marketPedidosCabeCreate(idcliente, total,
							idestado, idformadepago, comentarios, obsentrega,
							idempresa, usuarioalt);

					if (salida.equalsIgnoreCase("OK")) {

						idpedicabe = GeneralBean.getValorSequencia(
								"seq_marketpedicabe", dbconn);

						Enumeration en = htCarrito.keys();
						while (en.hasMoreElements()) {
							if (!salida.equalsIgnoreCase("OK"))
								break;
							String[] datos = (String[]) htCarrito.get(en
									.nextElement());
							// codigo_st, alias_st, descrip_st,
							// precipp_st, "1", precipp_st
							salida = marketPedidosDetaCreate(idpedicabe,
									datos[0], new BigDecimal(datos[4]),
									new BigDecimal(datos[3]), idempresa,
									usuarioalt);

						}

					}

					if (salida.equalsIgnoreCase("OK")) {

						String nombre = "";
						String apellido = "";
						String empresa = "";
						String direccion = "";
						String ciudad = "";
						String provinciaestado = "";
						String codigopostal = "";
						String pais = "";
						String telefono = "";
						String fax = "";

						nombre = htDireccion.get("nombreFact").toString();
						apellido = htDireccion.get("apellidoFact").toString();
						empresa = htDireccion.get("empresaFact").toString();
						direccion = htDireccion.get("direccionFact").toString();
						ciudad = htDireccion.get("ciudadFact").toString();
						provinciaestado = htDireccion
								.get("provinciaestadoFact").toString();
						codigopostal = htDireccion.get("codigopostalFact")
								.toString();
						pais = htDireccion.get("paisFact").toString();
						telefono = htDireccion.get("telefonoFact").toString();
						fax = htDireccion.get("faxFact").toString();

						salida = marketRegistroDireccionCreate(idcliente,
								nombre, apellido, empresa, direccion, ciudad,
								provinciaestado, codigopostal, pais, telefono,
								fax, "F", idpedicabe, idempresa, usuarioalt);

						if (salida.equalsIgnoreCase("OK")) {

							nombre = htDireccion.get("nombreEnv").toString();
							apellido = htDireccion.get("apellidoEnv")
									.toString();
							empresa = htDireccion.get("empresaEnv").toString();
							direccion = htDireccion.get("direccionEnv")
									.toString();
							ciudad = htDireccion.get("ciudadEnv").toString();
							provinciaestado = htDireccion.get(
									"provinciaestadoEnv").toString();
							codigopostal = htDireccion.get("codigopostalEnv")
									.toString();
							pais = htDireccion.get("paisEnv").toString();
							telefono = htDireccion.get("telefonoEnv")
									.toString();
							fax = htDireccion.get("faxEnv").toString();

							salida = marketRegistroDireccionCreate(idcliente,
									nombre, apellido, empresa, direccion,
									ciudad, provinciaestado, codigopostal,
									pais, telefono, fax, "E", idpedicabe,
									idempresa, usuarioalt);

						}

					}

				}// Carrito lleno
				else
					salida = "No se puede generar pedido, no hay productos en el carrito.";
			}// Pedido

		} catch (Exception e) {
			salida = "No se pudo generar pedido, por favor intente mas tarde.";
			log.error("marketCreateRegistroAndPedido: " + e);
		}

		if (!salida.equalsIgnoreCase("OK")) {
			dbconn.rollback();
		} else {
			salida = idpedicabe.toString();
			dbconn.commit();

		}

		dbconn.setAutoCommit(true);
		return salida;
	}

	/**
	 * Metodo ACTUALIZA DIRECCION - GENERA PEDIDO: marketRegistro -
	 * marketRegistroDireccion Copyrigth(r) sysWarp S.R.L. Fecha de creacion:
	 * Fri Mar 14 11:34:38 ART 2008
	 */

	public String marketUpdRegistroMakePedido(BigDecimal idcliente,
			String email, String pass, String activo, BigDecimal total,
			String comentarios, String obsentrega, Hashtable htDireccion,
			Hashtable htCarrito, BigDecimal idempresa, String usuarioalt)
			throws EJBException, SQLException {

		String salida = "OK";
		BigDecimal idformadepago = new BigDecimal(-1);
		BigDecimal idestado = new BigDecimal(-1);
		BigDecimal idpedicabe = new BigDecimal(-1);
		BigDecimal idregistrodireccion = new BigDecimal(-1);

		dbconn.setAutoCommit(false);

		try {

			String nombre = "";
			String apellido = "";
			String empresa = "";
			String direccion = "";
			String ciudad = "";
			String provinciaestado = "";
			String codigopostal = "";
			String pais = "";
			String telefono = "";
			String fax = "";

			nombre = htDireccion.get("nombreFact").toString();
			apellido = htDireccion.get("apellidoFact").toString();
			empresa = htDireccion.get("empresaFact").toString();
			direccion = htDireccion.get("direccionFact").toString();
			ciudad = htDireccion.get("ciudadFact").toString();
			provinciaestado = htDireccion.get("provinciaestadoFact").toString();
			codigopostal = htDireccion.get("codigopostalFact").toString();
			pais = htDireccion.get("paisFact").toString();
			telefono = htDireccion.get("telefonoFact").toString();
			fax = htDireccion.get("faxFact").toString();

			idregistrodireccion = getIdMarketRegistroDireccion(idcliente, "F",
					idempresa);
			salida = marketRegistroDireccionUpdate(idregistrodireccion,
					idcliente, nombre, apellido, empresa, direccion, ciudad,
					provinciaestado, codigopostal, pais, telefono, fax, "F",
					idempresa, usuarioalt);

			if (salida.equalsIgnoreCase("OK")) {

				nombre = htDireccion.get("nombreEnv").toString();
				apellido = htDireccion.get("apellidoEnv").toString();
				empresa = htDireccion.get("empresaEnv").toString();
				direccion = htDireccion.get("direccionEnv").toString();
				ciudad = htDireccion.get("ciudadEnv").toString();
				provinciaestado = htDireccion.get("provinciaestadoEnv")
						.toString();
				codigopostal = htDireccion.get("codigopostalEnv").toString();
				pais = htDireccion.get("paisEnv").toString();
				telefono = htDireccion.get("telefonoEnv").toString();
				fax = htDireccion.get("faxEnv").toString();

				idregistrodireccion = getIdMarketRegistroDireccion(idcliente,
						"E", idempresa);
				salida = marketRegistroDireccionUpdate(idregistrodireccion,
						idcliente, nombre, apellido, empresa, direccion,
						ciudad, provinciaestado, codigopostal, pais, telefono,
						fax, "E", idempresa, usuarioalt);

				if (salida.equalsIgnoreCase("OK")) {

					if (htCarrito != null && !htCarrito.isEmpty()) {

						salida = marketPedidosCabeCreate(idcliente, total,
								idestado, idformadepago, comentarios,
								obsentrega, idempresa, usuarioalt);

						if (salida.equalsIgnoreCase("OK")) {

							idpedicabe = GeneralBean.getValorSequencia(
									"seq_marketpedicabe", dbconn);

							Enumeration en = htCarrito.keys();
							while (en.hasMoreElements()) {
								if (!salida.equalsIgnoreCase("OK"))
									break;
								String[] datos = (String[]) htCarrito.get(en
										.nextElement());
								// codigo_st, alias_st, descrip_st,
								// precipp_st, "1", precipp_st
								salida = marketPedidosDetaCreate(idpedicabe,
										datos[0], new BigDecimal(datos[4]),
										new BigDecimal(datos[3]), idempresa,
										usuarioalt);

							}

						}

					}

				}

			}

		} catch (Exception e) {
			salida = "No se pudo generar pedido, por favor intente mas tarde.";
			log.error("marketUpdRegistroMakePedido: " + e);
		}

		if (!salida.equalsIgnoreCase("OK")) {
			dbconn.rollback();
		} else {
			salida = idpedicabe.toString();
			dbconn.commit();

		}

		dbconn.setAutoCommit(true);
		return salida;
	}

	/**
	 * Metodos para la entidad: marketRegistro Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Fri Mar 14 11:34:38 ART 2008
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getMarketRegistroAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT idcliente,email,pass,activo,idempresa,usuarioalt,usuarioact,fechaalt,fechaact"
				+ "  FROM MARKETREGISTRO WHERE idempresa = "
				+ idempresa.toString() + "  ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getMarketRegistroOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		String cQuery = "SELECT  idcliente,email,pass,activo,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM MARKETREGISTRO WHERE (UPPER(EMAIL) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%')  AND idempresa = "
				+ idempresa.toString()
				+ " ORDER BY 2  LIMIT "
				+ limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getMarketRegistroPK(BigDecimal idcliente, BigDecimal idempresa)
			throws EJBException {
		String cQuery = "SELECT idcliente,email,pass,activo,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM MARKETREGISTRO WHERE idcliente="
				+ idcliente.toString()
				+ " AND idempresa = "
				+ idempresa.toString() + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// Validar usuario y password
	public boolean isMarketRegistroValido(String email, String pass,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		boolean registrovalido = false;
		String cQuery = ""
				+ "SELECT COUNT(idcliente) as cant FROM MARKETREGISTRO WHERE email= '"
				+ email.toString().toLowerCase() + "' AND pass='"
				+ pass.toString().toLowerCase() + "' AND idempresa = "
				+ idempresa.toString();
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);

			if (rsSalida != null && rsSalida.next()) {
				registrovalido = rsSalida.getInt("cant") == 1 ? true : false;
			}

		} catch (SQLException sqlException) {
			log.error("Error SQL en el metodo : isMarketRegistroValido( ... ) "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: isMarketRegistroValido( ... )  "
							+ ex);
		}
		return registrovalido;
	}

	// Verificar existencia de usuario
	public boolean isMarketRegistroExistente(String email, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		boolean registroexiste = false;
		String cQuery = ""
				+ "SELECT COUNT(idcliente) as cant FROM MARKETREGISTRO WHERE email= '"
				+ email.toString().toLowerCase() + "' AND idempresa = "
				+ idempresa.toString();
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida != null) {
				if (rsSalida.next()) {
					registroexiste = rsSalida.getInt("cant") == 1 ? true
							: false;
				}
			}

		} catch (SQLException sqlException) {
			log
					.error("Error SQL en el metodo : isMarketRegistroExistente( ... ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: isMarketRegistroExistente( ... )  "
							+ ex);
		}
		return registroexiste;
	}

	// Recuperar idcliente.
	public BigDecimal getMarketRegistroIdCliente(String email, String pass,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		BigDecimal idcliente = new BigDecimal(-1);
		String cQuery = ""
				+ "SELECT idcliente FROM MARKETREGISTRO WHERE email= ? AND pass = ? AND idempresa = ? ";

		try {
			PreparedStatement pstatement = dbconn.prepareStatement(cQuery);
			pstatement.setString(1, email.toLowerCase());
			pstatement.setString(2, pass.toLowerCase());
			pstatement.setBigDecimal(3, idempresa);
			rsSalida = pstatement.executeQuery();
			if (rsSalida != null) {
				if (rsSalida.next()) {
					idcliente = rsSalida.getBigDecimal(1);
				}
			}

		} catch (SQLException sqlException) {
			log
					.error("Error SQL en el metodo : isMarketRegistroExistente( ... ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: isMarketRegistroExistente( ... )  "
							+ ex);
		}
		return idcliente;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)
	public String marketRegistroDelete(BigDecimal idcliente)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM MARKETREGISTRO WHERE idcliente="
				+ idcliente.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM MARKETREGISTRO WHERE idcliente="
						+ idcliente.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : marketRegistroDelete( BigDecimal idcliente ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: marketRegistroDelete( BigDecimal idcliente )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String marketRegistroCreate(String email, String pass,
			String activo, BigDecimal idempresa, String usuarioalt)
			throws EJBException {
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (email == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: email ";
		if (pass == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: pass ";
		if (activo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: activo ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (email.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: email ";
		if (pass.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: pass ";
		if (activo.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: activo ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones

		try {
			if (salida.equalsIgnoreCase("OK")) {
				String ins = "INSERT INTO MARKETREGISTRO(email, pass, activo, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, email.toLowerCase());
				insert.setString(2, pass.toLowerCase());
				insert.setString(3, activo);
				insert.setBigDecimal(4, idempresa);
				insert.setString(5, usuarioalt);
				int n = insert.executeUpdate();
				if (n != 1)
					salida = "Imposible generar registracion (0).";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible generar registracion (1).";
			log.error("Error SQL public String marketRegistroCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible generar registracion (2).";
			log
					.error("Error excepcion public String marketRegistroCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String marketRegistroCreateOrUpdate(BigDecimal idcliente,
			String email, String pass, String activo, BigDecimal idempresa,
			String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idcliente == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idcliente ";
		if (email == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: email ";
		if (pass == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: pass ";
		if (activo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: activo ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (email.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: email ";
		if (pass.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: pass ";
		if (activo.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: activo ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM marketRegistro WHERE idcliente = "
					+ idcliente.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE MARKETREGISTRO SET email=?, pass=?, activo=?, idempresa=?, usuarioact=?, fechaact=? WHERE idcliente=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, email);
					insert.setString(2, pass);
					insert.setString(3, activo);
					insert.setBigDecimal(4, idempresa);
					insert.setString(5, usuarioact);
					insert.setTimestamp(6, fechaact);
					insert.setBigDecimal(7, idcliente);
				} else {
					String ins = "INSERT INTO MARKETREGISTRO(email, pass, activo, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, email);
					insert.setString(2, pass);
					insert.setString(3, activo);
					insert.setBigDecimal(4, idempresa);
					insert.setString(5, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String marketRegistroCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String marketRegistroCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String marketRegistroUpdate(BigDecimal idcliente, String email,
			String pass, String activo, BigDecimal idempresa, String usuarioact)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idcliente == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idcliente ";
		if (email == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: email ";
		if (pass == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: pass ";
		if (activo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: activo ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (email.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: email ";
		if (pass.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: pass ";
		if (activo.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: activo ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM marketRegistro WHERE idcliente = "
					+ idcliente.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE MARKETREGISTRO SET email=?, pass=?, activo=?, idempresa=?, usuarioact=?, fechaact=? WHERE idcliente=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, email);
					insert.setString(2, pass);
					insert.setString(3, activo);
					insert.setBigDecimal(4, idempresa);
					insert.setString(5, usuarioact);
					insert.setTimestamp(6, fechaact);
					insert.setBigDecimal(7, idcliente);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String marketRegistroUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String marketRegistroUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * Metodos para la entidad: marketRegistroDireccion Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Fri Mar 14 14:48:00 ART 2008
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getMarketRegistroDireccionAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idcliente,nombre,apellido,empresa,direccion,ciudad,provinciaestado,codigopostal,pais,telefono,fax,tipodireccion,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM MARKETREGISTRODIRECCION WHERE idempresa = "
				+ idempresa.toString()
				+ "  ORDER BY 2  LIMIT "
				+ limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getMarketRegistroDireccionOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		String cQuery = "SELECT  idcliente,nombre,apellido,empresa,direccion,ciudad,provinciaestado,codigopostal,pais,telefono,fax,tipodireccion,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM MARKETREGISTRODIRECCION WHERE (UPPER(NOMBRE) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%')  AND idempresa = "
				+ idempresa.toString()
				+ " ORDER BY 2  LIMIT "
				+ limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getMarketRegistroDireccionPK(BigDecimal idcliente,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT idcliente,nombre,apellido,empresa,direccion,ciudad,provinciaestado,codigopostal,"
				+ "       pais,telefono,fax,tipodireccion,idempresa,usuarioalt,usuarioact,fechaalt,fechaact"
				+ "  FROM MARKETREGISTRODIRECCION WHERE idcliente="
				+ idcliente.toString() + " AND idempresa = "
				+ idempresa.toString() + ";";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getMarketRegistroLastDireccion(BigDecimal idcliente,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT idcliente,nombre,apellido,empresa,direccion,ciudad,provinciaestado,codigopostal,"
				+ "       pais,telefono,fax,tipodireccion,idempresa,usuarioalt,usuarioact,fechaalt,fechaact"
				+ "  FROM MARKETREGISTRODIRECCION WHERE idcliente="
				+ idcliente.toString() + " AND idempresa = "
				+ idempresa.toString()
				+ " AND idpedicabe = (SELECT max(idpedicabe) "
				+ "                     FROM marketpedidoscabe "
				+ "                    WHERE idcliente = "
				+ idcliente.toString()
				+ "                      AND idempresa ="
				+ idempresa.toString() + ");";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getMarketRegistroDireccionPedido(BigDecimal idcliente,
			BigDecimal idpedicabe, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idcliente,nombre,apellido,empresa,direccion,ciudad,provinciaestado,codigopostal,"
				+ "       pais,telefono,fax,tipodireccion,idempresa,usuarioalt,usuarioact,fechaalt,fechaact"
				+ "  FROM MARKETREGISTRODIRECCION WHERE idcliente="
				+ idcliente.toString() + " AND idpedicabe = "
				+ idpedicabe.toString() + "  AND idempresa = "
				+ idempresa.toString() + ";";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public BigDecimal getIdMarketRegistroDireccion(BigDecimal idcliente,
			String tipodireccion, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		BigDecimal idmarketregistro = new BigDecimal(-1);
		String cQuery = "" + "SELECT idregistrodireccion "
				+ "  FROM MARKETREGISTRODIRECCION WHERE idcliente="
				+ idcliente.toString() + " AND tipodireccion = '"
				+ tipodireccion.toUpperCase() + "' AND idempresa = "
				+ idempresa.toString() + ";";

		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);

			if (rsSalida.next()) {
				idmarketregistro = rsSalida.getBigDecimal(1);
			}

		} catch (SQLException sqlException) {
			log
					.error("Error SQL en el metodo : getIdMarketRegistroDireccion( ... ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getIdMarketRegistroDireccion( ... )  "
							+ ex);
		}
		return idmarketregistro;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String marketRegistroDireccionDelete(BigDecimal idcliente)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM MARKETREGISTRODIRECCION WHERE idcliente="
				+ idcliente.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM MARKETREGISTRODIRECCION WHERE idcliente="
						+ idcliente.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : marketRegistroDireccionDelete( BigDecimal idcliente ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: marketRegistroDireccionDelete( BigDecimal idcliente )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String marketRegistroDireccionCreate(BigDecimal idcliente,
			String nombre, String apellido, String empresa, String direccion,
			String ciudad, String provinciaestado, String codigopostal,
			String pais, String telefono, String fax, String tipodireccion,
			BigDecimal idpedicabe, BigDecimal idempresa, String usuarioalt)
			throws EJBException {
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (nombre == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nombre ";
		if (apellido == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: apellido ";
		if (empresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: empresa ";
		if (direccion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: direccion ";
		if (ciudad == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: ciudad ";
		if (provinciaestado == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: provinciaestado ";
		if (codigopostal == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigopostal ";
		if (pais == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: pais ";
		if (telefono == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: telefono ";
		if (fax == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fax ";
		if (tipodireccion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipodireccion ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (nombre.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: nombre ";
		if (apellido.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: apellido ";
		if (empresa.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: empresa ";
		if (direccion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: direccion ";
		if (ciudad.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: ciudad ";
		if (provinciaestado.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: provinciaestado ";
		if (codigopostal.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: codigopostal ";
		if (pais.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: pais ";
		if (telefono.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: telefono ";
		if (fax.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: fax ";
		if (tipodireccion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipodireccion ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("OK"))
			bError = false;
		try {
			if (!bError) {
				String ins = ""
						+ "INSERT INTO MARKETREGISTRODIRECCION"
						+ "(idcliente, nombre, apellido, empresa, direccion, ciudad, provinciaestado, codigopostal, pais, telefono, fax, tipodireccion, idpedicabe, idempresa, usuarioalt ) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, idcliente);
				insert.setString(2, nombre);
				insert.setString(3, apellido);
				insert.setString(4, empresa);
				insert.setString(5, direccion);
				insert.setString(6, ciudad);
				insert.setString(7, provinciaestado);
				insert.setString(8, codigopostal);
				insert.setString(9, pais);
				insert.setString(10, telefono);
				insert.setString(11, fax);
				insert.setString(12, tipodireccion);
				insert.setBigDecimal(13, idpedicabe);
				insert.setBigDecimal(14, idempresa);
				insert.setString(15, usuarioalt);
				int n = insert.executeUpdate();
				if (n != 1)
					salida = "Imposible generar domicilio del registro (0): "
							+ tipodireccion;
			}
		} catch (SQLException sqlException) {
			salida = "Imposible generar domicilio del registro (1): "
					+ tipodireccion;
			log
					.error("Error SQL public String marketRegistroDireccionCreate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible generar domicilio del registro (2): "
					+ tipodireccion;
			log
					.error("Error excepcion public String marketRegistroDireccionCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String marketRegistroDireccionCreateOrUpdate(BigDecimal idcliente,
			String nombre, String apellido, String empresa, String direccion,
			String ciudad, String provinciaestado, String codigopostal,
			String pais, String telefono, String fax, String tipodireccion,
			BigDecimal idempresa, String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idcliente == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idcliente ";
		if (nombre == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nombre ";
		if (apellido == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: apellido ";
		if (empresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: empresa ";
		if (direccion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: direccion ";
		if (ciudad == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: ciudad ";
		if (provinciaestado == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: provinciaestado ";
		if (codigopostal == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigopostal ";
		if (pais == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: pais ";
		if (telefono == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: telefono ";
		if (fax == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fax ";
		if (tipodireccion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipodireccion ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (nombre.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: nombre ";
		if (apellido.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: apellido ";
		if (empresa.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: empresa ";
		if (direccion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: direccion ";
		if (ciudad.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: ciudad ";
		if (provinciaestado.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: provinciaestado ";
		if (codigopostal.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: codigopostal ";
		if (pais.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: pais ";
		if (telefono.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: telefono ";
		if (fax.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: fax ";
		if (tipodireccion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipodireccion ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM marketRegistroDireccion WHERE idcliente = "
					+ idcliente.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE MARKETREGISTRODIRECCION SET nombre=?, apellido=?, empresa=?, direccion=?, ciudad=?, provinciaestado=?, codigopostal=?, pais=?, telefono=?, fax=?, tipodireccion=?, idempresa=?, usuarioact=?, fechaact=? WHERE idcliente=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, nombre);
					insert.setString(2, apellido);
					insert.setString(3, empresa);
					insert.setString(4, direccion);
					insert.setString(5, ciudad);
					insert.setString(6, provinciaestado);
					insert.setString(7, codigopostal);
					insert.setString(8, pais);
					insert.setString(9, telefono);
					insert.setString(10, fax);
					insert.setString(11, tipodireccion);
					insert.setBigDecimal(12, idempresa);
					insert.setString(13, usuarioact);
					insert.setTimestamp(14, fechaact);
					insert.setBigDecimal(15, idcliente);
				} else {
					String ins = "INSERT INTO MARKETREGISTRODIRECCION(nombre, apellido, empresa, direccion, ciudad, provinciaestado, codigopostal, pais, telefono, fax, tipodireccion, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, nombre);
					insert.setString(2, apellido);
					insert.setString(3, empresa);
					insert.setString(4, direccion);
					insert.setString(5, ciudad);
					insert.setString(6, provinciaestado);
					insert.setString(7, codigopostal);
					insert.setString(8, pais);
					insert.setString(9, telefono);
					insert.setString(10, fax);
					insert.setString(11, tipodireccion);
					insert.setBigDecimal(12, idempresa);
					insert.setString(13, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String marketRegistroDireccionCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String marketRegistroDireccionCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String marketRegistroDireccionUpdate(BigDecimal idregistrodireccion,
			BigDecimal idcliente, String nombre, String apellido,
			String empresa, String direccion, String ciudad,
			String provinciaestado, String codigopostal, String pais,
			String telefono, String fax, String tipodireccion,
			BigDecimal idempresa, String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idcliente == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idcliente ";
		if (nombre == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nombre ";
		if (apellido == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: apellido ";
		if (empresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: empresa ";
		if (direccion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: direccion ";
		if (ciudad == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: ciudad ";
		if (provinciaestado == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: provinciaestado ";
		if (codigopostal == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigopostal ";
		if (pais == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: pais ";
		if (telefono == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: telefono ";
		if (fax == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fax ";
		if (tipodireccion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipodireccion ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (nombre.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: nombre ";
		if (apellido.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: apellido ";
		if (empresa.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: empresa ";
		if (direccion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: direccion ";
		if (ciudad.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: ciudad ";
		if (provinciaestado.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: provinciaestado ";
		if (codigopostal.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: codigopostal ";
		if (pais.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: pais ";
		if (telefono.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: telefono ";
		if (fax.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: fax ";
		if (tipodireccion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipodireccion ";
		// fin validaciones

		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM marketRegistroDireccion WHERE idregistrodireccion = "
					+ idregistrodireccion.toString()
					+ "  AND idempresa = "
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
							+ "UPDATE MARKETREGISTRODIRECCION "
							+ "   SET nombre=?, apellido=?, empresa=?, direccion=?, ciudad=?, provinciaestado=?, codigopostal=?, pais=?, telefono=?, fax=?, tipodireccion=?, idempresa=?, usuarioact=?, fechaact=? "
							+ " WHERE idregistrodireccion=? AND idempresa=? ;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, nombre);
					insert.setString(2, apellido);
					insert.setString(3, empresa);
					insert.setString(4, direccion);
					insert.setString(5, ciudad);
					insert.setString(6, provinciaestado);
					insert.setString(7, codigopostal);
					insert.setString(8, pais);
					insert.setString(9, telefono);
					insert.setString(10, fax);
					insert.setString(11, tipodireccion);
					insert.setBigDecimal(12, idempresa);
					insert.setString(13, usuarioact);
					insert.setTimestamp(14, fechaact);
					insert.setBigDecimal(15, idregistrodireccion);
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
			log
					.error("Error SQL public String marketRegistroDireccionUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String marketRegistroDireccionUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * Metodos para la entidad: marketPedidosCabe Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Mon Mar 17 10:58:30 ART 2008
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getMarketPedidosCabeAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idpedicabe,idcliente,total,idestado,idformapago,comentarios,obsentrega,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM MARKETPEDIDOSCABEWHERE idempresa = "
				+ idempresa.toString()
				+ "  ORDER BY 2  LIMIT "
				+ limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getMarketPedidosCabeOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idpedicabe,idcliente,total,idestado,idformapago,comentarios,obsentrega,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM MARKETPEDIDOSCABE WHERE ((IDCLIENTE::VARCHAR) LIKE '%"
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
					.error("Error SQL en el metodo : getMarketPedidosCabeOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getMarketPedidosCabeOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getMarketPedidosCabePK(BigDecimal idpedicabe,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT idpedicabe,idcliente,total,idestado,idformapago,comentarios,obsentrega,idempresa,"
				+ "       usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM MARKETPEDIDOSCABE WHERE idpedicabe="
				+ idpedicabe.toString() + " AND idempresa = "
				+ idempresa.toString() + ";";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String marketPedidosCabeDelete(BigDecimal idpedicabe)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM MARKETPEDIDOSCABE WHERE idpedicabe="
				+ idpedicabe.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM MARKETPEDIDOSCABE WHERE idpedicabe="
						+ idpedicabe.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : marketPedidosCabeDelete( BigDecimal idpedicabe ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: marketPedidosCabeDelete( BigDecimal idpedicabe )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String marketPedidosCabeCreate(BigDecimal idcliente,
			BigDecimal total, BigDecimal idestado, BigDecimal idformapago,
			String comentarios, String obsentrega, BigDecimal idempresa,
			String usuarioalt) throws EJBException {
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idcliente == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idcliente ";
		if (total == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: total ";
		if (idestado == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idestado ";
		if (idformapago == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idformapago ";
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
				String ins = "INSERT INTO MARKETPEDIDOSCABE(idcliente, total, idestado, idformapago, comentarios, obsentrega, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, idcliente);
				insert.setBigDecimal(2, total);
				insert.setBigDecimal(3, idestado);
				insert.setBigDecimal(4, idformapago);
				insert.setString(5, comentarios);
				insert.setString(6, obsentrega);
				insert.setBigDecimal(7, idempresa);
				insert.setString(8, usuarioalt);
				int n = insert.executeUpdate();
				if (n != 1)
					salida = "No fue posible generear pedido (1).";
			}
		} catch (SQLException sqlException) {
			salida = "No fue posible generear pedido (1.1).";
			log.error("Error SQL public String marketPedidosCabeCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "No fue posible generear pedido (1.2).";
			log
					.error("Error excepcion public String marketPedidosCabeCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String marketPedidosCabeCreateOrUpdate(BigDecimal idpedicabe,
			BigDecimal idcliente, Double total, BigDecimal idestado,
			BigDecimal idformapago, String comentarios, String obsentrega,
			BigDecimal idempresa, String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idpedicabe == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idpedicabe ";
		if (idcliente == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idcliente ";
		if (total == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: total ";
		if (idestado == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idestado ";
		if (idformapago == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idformapago ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM marketPedidosCabe WHERE idpedicabe = "
					+ idpedicabe.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int tot = 0;
			if (rsSalida != null && rsSalida.next())
				tot = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (tot > 0) { // si existe hago update
					sql = "UPDATE MARKETPEDIDOSCABE SET idcliente=?, total=?, idestado=?, idformapago=?, comentarios=?, obsentrega=?, idempresa=?, usuarioact=?, fechaact=? WHERE idpedicabe=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, idcliente);
					insert.setDouble(2, total.doubleValue());
					insert.setBigDecimal(3, idestado);
					insert.setBigDecimal(4, idformapago);
					insert.setString(5, comentarios);
					insert.setString(6, obsentrega);
					insert.setBigDecimal(7, idempresa);
					insert.setString(8, usuarioact);
					insert.setTimestamp(9, fechaact);
					insert.setBigDecimal(10, idpedicabe);
				} else {
					String ins = "INSERT INTO MARKETPEDIDOSCABE(idcliente, total, idestado, idformapago, comentarios, obsentrega, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setBigDecimal(1, idcliente);
					insert.setDouble(2, total.doubleValue());
					insert.setBigDecimal(3, idestado);
					insert.setBigDecimal(4, idformapago);
					insert.setString(5, comentarios);
					insert.setString(6, obsentrega);
					insert.setBigDecimal(7, idempresa);
					insert.setString(8, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String marketPedidosCabeCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String marketPedidosCabeCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String marketPedidosCabeUpdate(BigDecimal idpedicabe,
			BigDecimal idcliente, Double total, BigDecimal idestado,
			BigDecimal idformapago, String comentarios, String obsentrega,
			BigDecimal idempresa, String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idpedicabe == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idpedicabe ";
		if (idcliente == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idcliente ";
		if (total == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: total ";
		if (idestado == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idestado ";
		if (idformapago == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idformapago ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM marketPedidosCabe WHERE idpedicabe = "
					+ idpedicabe.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int tot = 0;
			if (rsSalida != null && rsSalida.next())
				tot = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (tot > 0) { // si existe hago update
					sql = "UPDATE MARKETPEDIDOSCABE SET idcliente=?, total=?, idestado=?, idformapago=?, comentarios=?, obsentrega=?, idempresa=?, usuarioact=?, fechaact=? WHERE idpedicabe=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, idcliente);
					insert.setDouble(2, total.doubleValue());
					insert.setBigDecimal(3, idestado);
					insert.setBigDecimal(4, idformapago);
					insert.setString(5, comentarios);
					insert.setString(6, obsentrega);
					insert.setBigDecimal(7, idempresa);
					insert.setString(8, usuarioact);
					insert.setTimestamp(9, fechaact);
					insert.setBigDecimal(10, idpedicabe);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String marketPedidosCabeUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String marketPedidosCabeUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// Cambia el estado de un pedido.

	public String marketPedidosCabeSetEstado(BigDecimal idpedicabe,
			BigDecimal idestado, BigDecimal idempresa, String usuarioact)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idpedicabe == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idpedicabe ";
		if (idestado == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idestado ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		// fin validaciones

		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM marketPedidosCabe WHERE idpedicabe = "
					+ idpedicabe.toString()
					+ " AND idempresa = "
					+ idempresa.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int tot = 0;
			if (rsSalida != null && rsSalida.next())
				tot = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (salida.equalsIgnoreCase("OK")) {
				if (tot > 0) { // si existe hago update
					sql = ""
							+ "UPDATE MARKETPEDIDOSCABE "
							+ "   SET idestado=?, usuarioact=?, fechaact=? WHERE idpedicabe=? AND idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, idestado);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idpedicabe);
					insert.setBigDecimal(5, idempresa);
				}

				int i = insert.executeUpdate();
				if (i < 1)
					salida = "No se pudo cambiar el estado del pedido.";

			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar estado pedido(1).";
			log
					.error("Error SQL public String marketPedidosCabeSetEstado(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar estado pedido(2).";
			log
					.error("Error excepcion public String marketPedidosCabeSetEstado(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * Metodos para la entidad: marketPedidosDeta Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Mon Mar 17 10:59:11 ART 2008
	 */
	// para todo (ordena por el segundo campo por defecto)
	public List getMarketPedidosDetaAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		String cQuery = "SELECT idpedideta,idpedicabe,codigo_st,cantidad,precio,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM MARKETPEDIDOSDETAWHERE idempresa = "
				+ idempresa.toString()
				+ "  ORDER BY 2  LIMIT "
				+ limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getMarketPedidosDetaOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		String cQuery = "SELECT  idpedideta,idpedicabe,codigo_st,cantidad,precio,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM MARKETPEDIDOSDETA WHERE ((IDPEDICABE::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%')  AND idempresa = "
				+ idempresa.toString()
				+ " ORDER BY 2  LIMIT "
				+ limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getMarketPedidosDetaPK(BigDecimal idpedideta,
			BigDecimal idempresa) throws EJBException {
		String cQuery = "SELECT  idpedideta,idpedicabe,codigo_st,cantidad,precio,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM MARKETPEDIDOSDETA WHERE idpedideta="
				+ idpedideta.toString()
				+ " AND idempresa = "
				+ idempresa.toString() + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// Detalle para un pedido

	public List getMarketPedidosDetaXPedido(BigDecimal idpedicabe,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT pd.idpedideta,pd.idpedicabe,pd.codigo_st, st.descrip_st, st.descri2_st, pd.cantidad,pd.precio,pd.idempresa,"
				+ "       pd.usuarioalt,pd.usuarioact,pd.fechaalt,pd.fechaact"
				+ "  FROM marketpedidosdeta pd "
				+ "       INNER JOIN stockstock st ON pd.codigo_st = st.codigo_st AND pd.idempresa = st.idempresa"
				+ " WHERE pd.idpedicabe=" + idpedicabe.toString()
				+ " AND pd.idempresa = " + idempresa.toString() + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String marketPedidosDetaDelete(BigDecimal idpedideta)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM MARKETPEDIDOSDETA WHERE idpedideta="
				+ idpedideta.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM MARKETPEDIDOSDETA WHERE idpedideta="
						+ idpedideta.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : marketPedidosDetaDelete( BigDecimal idpedideta ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: marketPedidosDetaDelete( BigDecimal idpedideta )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String marketPedidosDetaCreate(BigDecimal idpedicabe,
			String codigo_st, BigDecimal cantidad, BigDecimal precio,
			BigDecimal idempresa, String usuarioalt) throws EJBException {
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idpedicabe == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idpedicabe ";
		if (codigo_st == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_st ";
		if (cantidad == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cantidad ";
		if (precio == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: precio ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
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
				String ins = "INSERT INTO MARKETPEDIDOSDETA(idpedicabe, codigo_st, cantidad, precio, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, idpedicabe);
				insert.setString(2, codigo_st);
				insert.setBigDecimal(3, cantidad);
				insert.setBigDecimal(4, precio);
				insert.setBigDecimal(5, idempresa);
				insert.setString(6, usuarioalt);
				int n = insert.executeUpdate();
				if (n != 1)
					salida = "Error al generar pedido (2.1).";
			}
		} catch (SQLException sqlException) {
			salida = "Error al generar pedido (2.2).";
			log.error("Error SQL public String marketPedidosDetaCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Error al generar pedido (2.3).";
			log
					.error("Error excepcion public String marketPedidosDetaCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String marketPedidosDetaCreateOrUpdate(BigDecimal idpedideta,
			BigDecimal idpedicabe, String codigo_st, BigDecimal cantidad,
			Double precio, BigDecimal idempresa, String usuarioact)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idpedideta == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idpedideta ";
		if (idpedicabe == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idpedicabe ";
		if (codigo_st == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_st ";
		if (cantidad == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cantidad ";
		if (precio == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: precio ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (codigo_st.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: codigo_st ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM marketPedidosDeta WHERE idpedideta = "
					+ idpedideta.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE MARKETPEDIDOSDETA SET idpedicabe=?, codigo_st=?, cantidad=?, precio=?, idempresa=?, usuarioact=?, fechaact=? WHERE idpedideta=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, idpedicabe);
					insert.setString(2, codigo_st);
					insert.setBigDecimal(3, cantidad);
					insert.setDouble(4, precio.doubleValue());
					insert.setBigDecimal(5, idempresa);
					insert.setString(6, usuarioact);
					insert.setTimestamp(7, fechaact);
					insert.setBigDecimal(8, idpedideta);
				} else {
					String ins = "INSERT INTO MARKETPEDIDOSDETA(idpedicabe, codigo_st, cantidad, precio, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setBigDecimal(1, idpedicabe);
					insert.setString(2, codigo_st);
					insert.setBigDecimal(3, cantidad);
					insert.setDouble(4, precio.doubleValue());
					insert.setBigDecimal(5, idempresa);
					insert.setString(6, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String marketPedidosDetaCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String marketPedidosDetaCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String marketPedidosDetaUpdate(BigDecimal idpedideta,
			BigDecimal idpedicabe, String codigo_st, BigDecimal cantidad,
			Double precio, BigDecimal idempresa, String usuarioact)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idpedideta == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idpedideta ";
		if (idpedicabe == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idpedicabe ";
		if (codigo_st == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_st ";
		if (cantidad == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cantidad ";
		if (precio == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: precio ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (codigo_st.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: codigo_st ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM marketPedidosDeta WHERE idpedideta = "
					+ idpedideta.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE MARKETPEDIDOSDETA SET idpedicabe=?, codigo_st=?, cantidad=?, precio=?, idempresa=?, usuarioact=?, fechaact=? WHERE idpedideta=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, idpedicabe);
					insert.setString(2, codigo_st);
					insert.setBigDecimal(3, cantidad);
					insert.setDouble(4, precio.doubleValue());
					insert.setBigDecimal(5, idempresa);
					insert.setString(6, usuarioact);
					insert.setTimestamp(7, fechaact);
					insert.setBigDecimal(8, idpedideta);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String marketPedidosDetaUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String marketPedidosDetaUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	/*
	 * metodos para resolver filtros por depositos para aquellos usuarios que
	 * tengan asociacion a los mismos
	 */

	public boolean hasDepositosAsociados(String usuario, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		boolean salida = false;
		String cQuery = ""
				+ "SELECT count(DEP.*) as total "
				+ " FROM globalusuariosdepositos DEP "
				+ " WHERE "
				+ " idempresa = "
				+ idempresa
				+ " and idusuario in (select idusuario from globalusuarios where lower(usuario) ='"
				+ usuario.toLowerCase() + "' and idempresa = " + idempresa
				+ ") " + "";

		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			while (rsSalida.next()) {
				int totReg = rsSalida.getInt("total");
				if (totReg > 0)
					salida = true;
			}
		} catch (SQLException sqlException) {
			log.error("Error SQL en el metodo : hasDepositosAsociados( ... ) "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: hasDepositosAsociados( ... )  "
							+ ex);
		}
		return salida;
	}

	public String getQueryDepositosAsociados(String usuario,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;

		String salida = "";
		// accion devolver un conjunto para el query con los depositos asociados

		String cQuery = ""
				+ "SELECT codigo_dt "
				+ " FROM globalusuariosdepositos DEP "
				+ " WHERE "
				+ " idempresa = "
				+ idempresa
				+ " and idusuario in (select idusuario from globalusuarios where lower(usuario) ='"
				+ usuario.toLowerCase() + "' and idempresa = " + idempresa
				+ ") " + "";

		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			ResultSetMetaData md = rsSalida.getMetaData();
			salida += "(";
			if (rsSalida.next()) {
				while (true) {
					salida += rsSalida.getString("codigo_dt");
					if (rsSalida.next()) {
						salida += ",";
					} else
						break;
				}
			}
			salida += ")";
		} catch (SQLException sqlException) {
			log
					.error("Error SQL en el metodo : getQueryDepositosAsociados( ... ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getQueryDepositosAsociados( ... )  "
							+ ex);
		}
		return salida;
	}

	public List getCosnultaFamiliaGrupoDeposito(BigDecimal codigo_fm,
			BigDecimal grupo_st, BigDecimal iddeposito, BigDecimal idempresa)
			throws EJBException {
		/*
		 * Objetivo: Traer el saldo anterior a la fecha desde para un producto
		 * en el informe solicitado
		 */

		String cQuery = "";
		// selecciono familia,grupo,deposito
		if (codigo_fm.longValue() != 0 && grupo_st.longValue() != 0
				&& iddeposito.longValue() != 0) {
			cQuery = ""
					+ "SELECT deposito,codarticulo,articulo,disponible, reservado, existencia ,medida,usuarioresponsable"
					+ "  FROM vstockxfamiliagrupodeposito"
					+ " WHERE codfamilia = " + codigo_fm.toString()
					+ "   AND codgrupo = " + grupo_st.toString()
					+ "   AND coddeposito = " + iddeposito.toString()
					+ "   AND idempresa = " + idempresa.toString()
					+ " ORDER BY 1 ";
		}
		// selecciono familia,grupo
		if (codigo_fm.longValue() != 0 && grupo_st.longValue() != 0
				&& iddeposito.longValue() == 0) {
			cQuery = ""
					+ "SELECT deposito,codarticulo,articulo,cantidad,medida,usuarioresponsable FROM vstockxfamiliagrupodeposito"
					+ " WHERE codfamilia = " + codigo_fm.toString()
					+ "   AND codgrupo = " + grupo_st.toString()
					+ "   AND idempresa = " + idempresa.toString()
					+ " ORDER BY 1 ";
		}
		// selecciono familia
		if (codigo_fm.longValue() != 0 && grupo_st.longValue() == 0
				&& iddeposito.longValue() == 0) {
			cQuery = ""
					+ "SELECT deposito,codarticulo,articulo,cantidad,medida,usuarioresponsable"
					+ "  FROM vstockxfamiliagrupodeposito"
					+ " WHERE codfamilia = " + codigo_fm.toString()
					+ "   AND idempresa = " + idempresa.toString()
					+ " ORDER BY 1 ";
		}

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getStockFamiliasGruposAll(long limit, long offset,
			BigDecimal codigo_fm, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT s.codigo_gr, s.descrip_gr,s.usuarioalt, s.usuarioact, s.fechaalt, s.fechaact "
				+ "  FROM STOCKGRUPOS s  WHERE s.idempresa = "
				+ idempresa.toString() + "   AND s.codigo_fm = "
				+ codigo_fm.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getStockFamiliasGruposOcu(long limit, long offset,
			String ocurrencia, BigDecimal codigo_fm, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT s.codigo_gr, s.descrip_gr,s.usuarioalt, s.usuarioact, s.fechaalt, s.fechaact "
				+ "  FROM STOCKGRUPOS s WHERE s.idempresa = "
				+ idempresa.toString() + "   AND s.codigo_fm = "
				+ codigo_fm.toString() + " AND (s.codigo_gr::VARCHAR LIKE '%"
				+ ocurrencia + "%' OR " + " UPPER(s.descrip_gr) LIKE '%"
				+ ocurrencia.toUpperCase() + "%') " + " ORDER BY 2";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getDepositoxFechaAll(java.sql.Date fecha_ms, long limit,
			long offset, BigDecimal idempresa, String usuario,
			BigDecimal codigo_dt) throws EJBException {
		String cQuery = ""
				+ " SELECT articu_ms, articulo,  SUM(canti_sh) as cantidad, descrip_dt "
				+ "  FROM vTotalArticuloDepositoFecha2  WHERE fecha_ms <= '"
				+ fecha_ms + "'::date AND idempresa = " + idempresa.toString()
				+ " and codigo_dt = " + codigo_dt.toString();
		if (hasDepositosAsociados(usuario, idempresa)) {
			cQuery += " and codigo_dt in "
					+ getQueryDepositosAsociados(usuario, idempresa);
		}

		cQuery += " GROUP BY articu_ms, articulo, descrip_dt ORDER BY 2  LIMIT "
				+ limit + " OFFSET  " + offset + ";";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getDepositoFechaOcu(long limit, long offset,
			java.sql.Date fecha_ms, String ocurrencia, BigDecimal idempresa,
			String usuario, BigDecimal codigo_dt) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ " SELECT articu_ms, articulo,  SUM(canti_sh) as cantidad, descrip_dt "
				+ "  FROM vTotalArticuloDepositoFecha2 "
				+ " WHERE fecha_ms <= '" + fecha_ms
				+ "'::date AND idempresa = " + idempresa.toString()
				+ " and codigo_dt = " + codigo_dt.toString();
		if (hasDepositosAsociados(usuario, idempresa)) {
			cQuery += " and codigo_dt in "
					+ getQueryDepositosAsociados(usuario, idempresa);
		}
		cQuery += " AND (UPPER(ARTICU_MS) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%'  OR UPPER(ARTICULO) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%') GROUP BY articu_ms, articulo, descrip_dt ORDER BY 2  LIMIT "
				+ limit + " OFFSET  " + offset + ";";

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public long getTotalStockDepositoFecha(java.sql.Date fecha_ms,
			BigDecimal idempresa, String usuario, BigDecimal codigo_dt)
			throws EJBException {
		ResultSet rsSalida = null;
		long total = 0;
		String cQuery = " SELECT count(distinct articu_ms||descrip_dt) "
				+ "  FROM vTotalArticuloDepositoFecha2  WHERE fecha_ms <= '"
				+ fecha_ms + "'::date  AND idempresa = " + idempresa.toString()
				+ " and codigo_dt = " + codigo_dt.toString();
		if (hasDepositosAsociados(usuario, idempresa)) {
			cQuery += " and codigo_dt in "
					+ getQueryDepositosAsociados(usuario, idempresa);
		}

		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);

			if (rsSalida.next()) {
				total = rsSalida.getLong(1);
			}
		} catch (SQLException sqlException) {
			log.error("Error SQL en el metodo : getTotalStockDepositoFecha() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getTotalStockDepositoFecha()  "
							+ ex);
		}
		return total;
	}

	public long getTotalStocDepositoFechaOcu(java.sql.Date fecha_ms,
			String ocurrencia, BigDecimal idempresa, String usuario,
			BigDecimal codigo_dt) throws EJBException {
		ResultSet rsSalida = null;
		long total = 0;
		String cQuery = " "
				+ " SELECT count(distinct articu_ms || descrip_dt) AS TOTAL "
				+ "           FROM vTotalArticuloDepositoFecha2  "
				+ "          WHERE fecha_ms <= '" + fecha_ms
				+ "'::date AND idempresa=" + idempresa.toString()
				+ " and codigo_dt = " + codigo_dt.toString();
		if (hasDepositosAsociados(usuario, idempresa)) {
			cQuery += " and codigo_dt in "
					+ getQueryDepositosAsociados(usuario, idempresa);
		}

		cQuery += "            AND (UPPER(ARTICU_MS) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%'           OR UPPER(ARTICULO) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%') ;";

		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);

			if (rsSalida.next()) {
				total = rsSalida.getLong(1);
			}
		} catch (SQLException sqlException) {
			log
					.error("Error SQL en el metodo : getTotalStocDepositoFechaOcu() "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getTotalStocDepositoFechaOcu()  "
							+ ex);
		}
		return total;
	}

	// Stock Iva
	// para todo (ordena por el segundo campo por defecto)
	public List getStockivaAll(long limit, long offset, BigDecimal idempresa)
			throws EJBException {
		String cQuery = "SELECT idstockiva,descripcion,porcentaje,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM STOCKIVA WHERE idempresa = "
				+ idempresa.toString()
				+ "  ORDER BY 2  LIMIT "
				+ limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getStockivaOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idstockiva,descripcion,porcentaje,idempresa,usuarioalt,usuarioact,fechaalt,fechaact from stockiva"
				+ " where idempresa= "
				+ idempresa.toString()
				+ " and (idstockiva::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR "
				+ " UPPER(descripcion) LIKE '%"
				+ ocurrencia.toUpperCase()
				+ "%') " + " ORDER BY 2";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getStockivaPK(BigDecimal idstockiva, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idstockiva,descripcion,porcentaje,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM STOCKIVA WHERE idstockiva="
				+ idstockiva.toString()
				+ " AND idempresa = "
				+ idempresa.toString() + ";";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String stockivaDelete(BigDecimal idstockiva, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM STOCKIVA " + " WHERE idstockiva = "
				+ idstockiva.toString() + " and idempresa = "
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM STOCKIVA " + " WHERE idstockiva="
						+ idstockiva.toString() + " and idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : stockivaDelete( BigDecimal idstockiva ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: stockivaDelete( BigDecimal idstockiva )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String stockivaCreate(String descripcion, Double porcentaje,
			BigDecimal idempresa, String usuarioalt) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (descripcion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: descripcion ";
		if (porcentaje == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: porcentaje ";
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
				String ins = "INSERT INTO STOCKIVA(descripcion, porcentaje, idempresa, usuarioalt ) VALUES (?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, descripcion);
				insert.setDouble(2, porcentaje.doubleValue());
				insert.setBigDecimal(3, idempresa);
				insert.setString(4, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String stockivaCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error excepcion public String stockivaCreate(.....)"
					+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria
	/*
	 * public String stockivaCreateOrUpdate(BigDecimal idstockiva, String
	 * descripcion, Double porcentaje, BigDecimal idempresa, String usuarioact)
	 * throws EJBException { Calendar hoy = new GregorianCalendar(); Timestamp
	 * fechaact = new Timestamp(hoy.getTime().getTime()); String salida =
	 * "NOOK"; // validaciones de datos: // 1. nulidad de campos if(idstockiva
	 * == null ) salida =
	 * "Error: No se puede dejar sin datos (nulo) el campo: idstockiva ";
	 * if(descripcion == null ) salida =
	 * "Error: No se puede dejar sin datos (nulo) el campo: descripcion ";
	 * if(porcentaje == null ) salida =
	 * "Error: No se puede dejar sin datos (nulo) el campo: porcentaje ";
	 * if(idempresa == null ) salida =
	 * "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
	 * 
	 * // 2. sin nada desde la pagina if(descripcion.equalsIgnoreCase(""))
	 * salida = "Error: No se puede dejar vacio el campo: descripcion "; // fin
	 * validaciones boolean bError=true; if(salida.equalsIgnoreCase("NOOK"))
	 * bError = false; try { ResultSet rsSalida = null; String cQuery =
	 * "SELECT COUNT(*) FROM stockiva WHERE idstockiva = " +
	 * idstockiva.toString(); Statement statement = dbconn.createStatement();
	 * rsSalida = statement.executeQuery(cQuery); int total = 0; if (rsSalida !=
	 * null && rsSalida.next()) total = rsSalida.getInt(1); PreparedStatement
	 * insert = null; String sql = ""; if(!bError){ if (total > 0) { // si
	 * existe hago updatesql=
	 * "UPDATE STOCKIVA SET descripcion=?, porcentaje=?, idempresa=?, usuarioact=?, fechaact=? WHERE idstockiva=?;"
	 * ; insert = dbconn.prepareStatement(sql); insert.setString(1,descripcion);
	 * insert.setDouble(2,porcentaje.doubleValue());
	 * insert.setBigDecimal(3,idempresa); insert.setString(4,usuarioact);
	 * insert.setTimestamp(5,fechaact); insert.setBigDecimal(6,idstockiva); }
	 * else { String ins =
	 * "INSERT INTO STOCKIVA(descripcion, porcentaje, idempresa, usuarioalt ) VALUES (?, ?, ?, ?)"
	 * ; insert = dbconn.prepareStatement(ins); //seteo de campos: String
	 * usuarioalt=usuarioact; // esta variable va a proposito
	 * insert.setString(1,descripcion);
	 * insert.setDouble(2,porcentaje.doubleValue());
	 * insert.setBigDecimal(3,idempresa); insert.setString(4,usuarioalt); }
	 * insert.executeUpdate(); salida = "Alta Correcta."; } } catch
	 * (SQLException sqlException) { salida =
	 * "Imposible dar de alta el registro.";
	 * log.error("Error SQL public String stockivaCreateOrUpdate(.....)" +
	 * sqlException); } catch (Exception ex) { salida =
	 * "Imposible dar de alta el registro.";
	 * log.error("Error excepcion public String stockivaCreateOrUpdate(.....)" +
	 * ex); } return salida; }
	 */
	public String stockivaUpdate(BigDecimal idstockiva, String descripcion,
			Double porcentaje, BigDecimal idempresa, String usuarioact)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idstockiva == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idstockiva ";
		if (descripcion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: descripcion ";
		if (porcentaje == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: porcentaje ";
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
			String cQuery = "SELECT COUNT(*) FROM stockiva WHERE idstockiva = "
					+ idstockiva.toString() + " and idempresa =" + idempresa;
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE STOCKIVA SET descripcion=?, porcentaje=?, usuarioact=?, fechaact=? WHERE idstockiva=? and idempresa=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, descripcion);
					insert.setDouble(2, porcentaje.doubleValue());
					insert.setString(3, usuarioact);
					insert.setTimestamp(4, fechaact);
					insert.setBigDecimal(5, idstockiva);
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
			log.error("Error SQL public String stockivaUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log.error("Error excepcion public String stockivaUpdate(.....)"
					+ ex);
		}
		return salida;
	}

	// Consulta de Control de Disponible
	public List getConsultaControlDisponible(BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ " select "
				+ " sb.articu_sb, "
				+ " ss.descrip_st, "
				+ " sb.canti_sb::numeric(18,2) as canti_sb, "
				+ " sd.descrip_dt, "
				+ " sm.descrip_md, "
				+ " sd.codigo_dt "
				+ " from "
				+ " stockstockbis sb "
				+ " inner join stockstock ss on sb.articu_sb = ss.codigo_st and sb.idempresa = ss.idempresa "
				+ " inner join stockdepositos sd on sb.deposi_sb = sd.codigo_dt and sd.idempresa = ss.idempresa "
				+ " inner join stockmedidas sm on ss.unimed_st = sm.codigo_md and ss.idempresa = sm.idempresa "
				+ " where " + " sb.canti_sb < 0 " + " and sb.idempresa = "
				+ idempresa.toString() + " order by 2 ";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// Consulta de Orden de Compra por producto
	public List getConsultaOrdendeCompraporProducto(BigDecimal idempresa,
			String producto) throws EJBException {
		String cQuery = ""
				+ " select  occabe.id_oc_cabe as orden,  estados.estadooc as  estado,  occabe.idproveedor, "
				+ " proveed.razon_social,  to_char(occabe.fechaoc,'dd/mm/yyyy') as fechaoc, to_char(occabe.fecha_entrega_prevista,'dd/mm/yyyy') as fecha_entrega_prevista,  depositos.descrip_dt as deposito, "
				+ " occabe.observaciones,  occabe.idempresa,  occabe.idestadooc as idestado,  occabe.codigo_dt as coddeposito,proveedo_oc_deta.codigo_st  "
				+ " from  proveedo_oc_cabe occabe "
				+ " join proveedo_oc_estados estados on(occabe.idestadooc=estados.idestadooc and occabe.idempresa=estados.idempresa) "
				+ " join proveedoproveed proveed on (occabe.idproveedor = proveed.idproveedor and occabe.idempresa=proveed.idempresa) "
				+ " join stockdepositos depositos on ( depositos.codigo_dt = occabe.codigo_dt and occabe.idempresa=depositos.idempresa ) "
				+ " join proveedo_oc_deta on occabe.id_oc_cabe = proveedo_oc_deta.id_oc_cabe and occabe.idempresa = proveedo_oc_deta.idempresa "
				+ " where  occabe.idestadooc <> 1 "
				+ " and occabe.fecha_entrega_prevista is not null "
				+ " and estados.idestadooc = 2 " + " and occabe.idempresa = "
				+ idempresa.toString() + " and proveedo_oc_deta.codigo_st = "
				+ "'" + producto.toString() + "'"
				+ " order by occabe.fecha_entrega_prevista ";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// consulta esquema
	public List getConsultaEsquema(String codigo_st, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ " select "
				+ "  depo.descrip_dt,"
				+ "  esqued.codigo_st, "
				+ "  stock.descrip_st, "
				+ "  sb.canti_sb::numeric(18,2) as disponible, "
				+ "  sb.pedid_sb::numeric(18,2) as reserva,"
				+ "  (sb.canti_sb + sb.pedid_sb)::numeric(18,2) as existencia "
				+ " from "
				+ "  produccionesquemas_deta  esqueD "
				+ "  join stockdepositos depo on ( esqued.codigo_dt = depo.codigo_dt and esqued.idempresa = depo.idempresa ) "
				+ "  join stockstock    stock on ( esqued.codigo_st = stock.codigo_st and esqued.idempresa = stock.idempresa ) "
				+ "  join stockstockbis sb    on ( esqued.codigo_st = sb.articu_sb and esqued.codigo_dt = sb.deposi_sb and esqued.idempresa = sb.idempresa) "
				+ " where " + " esqued.entsal = 'P' "
				+ " and esqued.codigo_st = " + "'" + codigo_st.toString() + "'"
				+ " and esqued.idempresa = " + idempresa.toString();
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getConsultaStock(String codigo_st, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ " select "
				+ " sb.articu_sb, "
				+ " stock.descrip_st,"
				+ " depo.descrip_dt,"
				+ " sb.canti_sb::numeric(18,2) as disponible,"
				+ " sb.pedid_sb::numeric(18,2) as reserva,"
				+ " (sb.canti_sb + sb.pedid_sb)::numeric(18,2) as existencia "
				+ " from "
				+ " stockstockbis sb "
				+ " join stockstock    stock on ( sb.articu_sb = stock.codigo_st and sb.idempresa = stock.idempresa ) "
				+ " join stockdepositos depo on ( sb.deposi_sb = depo.codigo_dt and sb.idempresa = depo.idempresa ) "
				+ " where sb.articu_sb = " + "'" + codigo_st.toString() + "'"
				+ " and sb.idempresa = " + idempresa.toString()
				// 20091020 - EJV - MANTIS: 0000436
				// + " and (sb.canti_sb + sb.pedid_sb) > 0 ";
				+ " and (sb.canti_sb) > 0 ";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// orden produccion
	public List getConsultaOrdenProduccion(String codigo_st,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT mp.idop,mp.idesquema,mp.idcliente,mp.cantre_op,mp.cantest_op,mp.fecha_prometida,mp.fecha_emision, "
				+ "       mp.observaciones,mp.codigo_st,st.descrip_st,mp.idcontador,mp.nrointerno, "
				+ "       CASE  WHEN fbaja IS NOT NULL  "
				+ "             THEN 'ANULADA' "
				+ "             ELSE  "
				+ "             CASE  "
				+ "                  WHEN mp.cantre_op = 0 THEN 'PENDIENTE'  "
				+ "                  WHEN mp.cantre_op < mp.cantest_op THEN 'EN PROCESO'  "
				+ "                  WHEN mp.cantre_op >= mp.cantest_op THEN 'FINALIZADA'  "
				+ "             END "
				+ "       END AS estado,  "
				+ "       mp.usuarioalt,mp.usuarioact,mp.fechaalt,mp.fechaact  "
				+ "  FROM produccionmovprodu mp  "
				+ "       INNER JOIN stockstock st ON mp.codigo_st = st.codigo_st AND mp.idempresa = st.idempresa "
				+ "       INNER JOIN produccionesquemas_deta ed ON st.codigo_st = ed.codigo_st AND ed.entsal = 'P' AND st.idempresa = ed.idempresa "
				+ " WHERE mp.idempresa = "
				+ idempresa.toString()
				+ "   AND mp.fbaja IS NULL "
				+ "   AND mp.cantre_op < mp.cantest_op "
				+ "   AND UPPER(ed.codigo_st) = '"
				+ codigo_st.toUpperCase()
				+ "'"
				+ "   AND mp.idop NOT IN (SELECT idop FROM interfacesopregalos WHERE idempresa = "
				+ idempresa.toString() + " ) " + " ORDER BY 1	 ";

		/*
		 * 
		 * stock.codigo_st, stock.descrip_st, (cantest_op - cantre_op
		 * )::numeric(18,2) as cantidad_pendiente,
		 * TO_CHAR(prod.fecha_prometida,'dd/mm/yyyy') as fechaprometida,
		 * TO_CHAR(prod.fecha_emision,'dd/mm/yyyy') as fechaemision
		 */

		/*
		 * 
		 * + " select " + " stock.codigo_st, " + " stock.descrip_st, " +
		 * " (cantest_op - cantre_op )::numeric(18,2) as cantidad_pendiente, " +
		 * " to_char(prod.fecha_prometida,'dd/mm/yyyy') as fechaprometida, " +
		 * " to_char(prod.fecha_emision,'dd/mm/yyyy') as fechaemision " +
		 * " from " + "  produccionesquemas_deta  esqueD  " +
		 * "   join stockstock    stock on ( esqued.codigo_st = stock.codigo_st and esqued.idempresa = stock.idempresa ) "
		 * +
		 * "  join produccionmovprodu prod on (esqueD.idesquema = prod.idesquema and esqued.idempresa = prod.idempresa) "
		 * + " where " + " esqued.entsal = 'P' " + " and esqued.codigo_st = " +
		 * "'" + codigo_st.toString() + "'" + " and esqued.idempresa = " +
		 * idempresa.toString() + " and (cantest_op - cantre_op) > 0  "
		 */

		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// stock depositos
	public List getConsultaStockDepositos(String codigo_st, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ " select "
				+ " sb.articu_sb, "
				+ " stock.descrip_st,"
				+ " depo.descrip_dt,"
				+ " sb.canti_sb::numeric(18,2) as disponible,"
				+ " sb.pedid_sb::numeric(18,2) as reserva,"
				+ " (sb.canti_sb + sb.pedid_sb)::numeric(18,2) as existencia "
				+ " from "
				+ " stockstockbis sb "
				+ " join stockstock    stock on ( sb.articu_sb = stock.codigo_st and sb.idempresa = stock.idempresa ) "
				+ " join stockdepositos depo on ( sb.deposi_sb = depo.codigo_dt and sb.idempresa = depo.idempresa ) "
				+ " where sb.articu_sb = " + "'" + codigo_st.toString() + "'"
				+ " and sb.idempresa = " + idempresa.toString()
				+ " and (sb.canti_sb + sb.pedid_sb) > 0 "
				+ " and depo.codigo_dt = 52 ";
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	// BIENES DE CAMBIO - CONSULTA DE PEDIDOS INVOLUCRADOS EN EL STOCK RESERVA
	public List getConsultaPedidosStockReserva(String codigo_st,
			String codigo_dt, java.sql.Date fdesde, java.sql.Date fhasta,
			String tipopedido, BigDecimal idempresa) throws EJBException {
		String cQuery = "";

		if (tipopedido.equalsIgnoreCase("N"))
			cQuery = " SELECT deta.idpedido_cabe,"
					+ "        cabe.fechapedido,"
					+ "        deta.codigo_st,"
					+ "        stock.descrip_st as articulo, "
					+ "        deta.cantidad::numeric(18), "
					+ "        deta.cantidad_sb, "
					+ "        deta.compromiso_sb,"
					+ "        depo.descrip_dt as deposito, "
					+ "        'N' AS tipopedido, "
					+ "        cabe.idcliente, "
					+ "        cl.razon, "
					+ "        es.idestado, "
					+ "        es.estado , hr.nrohojaarmado, hr.fechahojaarmado "
					+ "   FROM pedidos_deta deta "
					+ "        INNER JOIN stockstock stock on ( deta.codigo_st = stock.codigo_st AND deta.idempresa = stock.idempresa ) "
					+ "        INNER JOIN stockdepositos depo ON ( deta.codigo_dt = depo.codigo_dt AND deta.idempresa = depo.idempresa ) "
					+ "        INNER JOIN pedidos_cabe cabe ON (deta.idpedido_cabe = cabe.idpedido_cabe AND deta.idempresa = cabe.idempresa) "
					+ "        INNER JOIN ( "
					+ " 			   					SELECT pc.idpedido_cabe, cr.nrohojaarmado, cr.fechahojaarmado, CASE WHEN cr.nrohojarutafinal IS NOT NULL THEN 1 ELSE 0 END AS tienehr, pc.idempresa "
					+ "		 			   			  FROM pedidos_cabe pc "
					+ " 					   			       INNER JOIN pedidos_deta pd ON pc.idpedido_cabe = pd.idpedido_cabe AND pc.idempresa = pd.idempresa "
					+ " 			   					        LEFT JOIN clientesremitos cr ON pd.idremitocliente = cr.idremitocliente AND pd.idempresa = cr.idempresa "
					// --> 20110603 - EJV - Mantis 716
					+ "                                WHERE COALESCE(cr.idestado, 1) = 1"
					// <--
					+ " 			   					 GROUP BY  pc.idpedido_cabe, cr.nrohojaarmado, cr.fechahojaarmado, CASE WHEN cr.nrohojarutafinal IS NOT NULL THEN 1 ELSE 0 END, pc.idempresa "
					+ " 			                 )hr ON (deta.idpedido_cabe = hr.idpedido_cabe  AND hr.tienehr = 0  AND deta.idempresa = hr.idempresa) "
					+ "        INNER JOIN pedidosestados es ON (cabe.idestado = es.idestado  AND cabe.idempresa = es.idempresa) "
					+ "        INNER JOIN clientesclientes cl ON (cabe.idcliente = cl.idcliente  AND cabe.idempresa = cl.idempresa) "
					+ "  WHERE deta.codigo_st = '"
					+ codigo_st
					+ "'     "
					+
					// --> 20110603 - EJV - Mantis 716
					"AND  hr.tienehr = 0 "
					// <--
					+ "AND depo.codigo_dt = "
					+ codigo_dt
					+ "       AND cabe.idestado IN (1, 2, 3) "

					+ (fdesde != null && fhasta != null ? (" AND cabe.fechapedido::DATE BETWEEN '"
							+ fdesde + "'::DATE AND '" + fhasta + "'::DATE ")
							: "")

					+ "       AND deta.idempresa = " + idempresa
					+ " ORDER BY 3, 1 ";

		else

			// EJV - 20110103 - Mantis 651 -->

			cQuery = "SELECT deta.idpedido_regalos_cabe, cabe.fechapedido, deta.codigo_st, "
					+ "             stock.descrip_st as articulo, deta.cantidad::numeric(18), "
					+ "             deta.cantidad_sb, deta.compromiso_sb, depo.descrip_dt as deposito, "
					+ "             'R' AS tipopedido, cabe.idcliente, cl.razon, es.idestado,         "
					+ "             es.estado , hr.nrohojaarmado, hr.fechahojaarmado "
					+ "  FROM pedidos_regalos_deta deta  "
					+ "           INNER JOIN stockstock stock on ( deta.codigo_st = stock.codigo_st AND deta.idempresa = stock.idempresa ) "
					+ "           INNER JOIN stockdepositos depo ON ( deta.codigo_dt = depo.codigo_dt AND deta.idempresa = depo.idempresa ) "
					+ "           INNER JOIN pedidos_regalos_cabe cabe ON (deta.idpedido_regalos_cabe = cabe.idpedido_regalos_cabe AND deta.idempresa = cabe.idempresa "
					+ "                     AND (cabe.idpedido_regalos_padre IS NULL OR cabe.idpedido_regalos_cabe = cabe.idpedido_regalos_padre )) "
					+ "           INNER JOIN ( "
					+ "       		SELECT pc.idpedido_regalos_cabe, cr.nrohojaarmado, cr.fechahojaarmado,"
					+ "       		       CASE WHEN cr.nrohojarutafinal IS NOT NULL THEN 1 ELSE 0 END AS tienehr, pc.idempresa "
					+ "       		  FROM pedidos_regalos_cabe pc "
					+ "       		       INNER JOIN pedidos_regalos_deta pd ON pc.idpedido_regalos_cabe = pd.idpedido_regalos_cabe AND pc.idempresa = pd.idempresa "
					+ "       		       LEFT JOIN clientesremitos cr ON pd.idremitocliente = cr.idremitocliente AND pd.idempresa = cr.idempresa  "
					// --> 20110603 - EJV - Mantis 716
					+ "                 WHERE COALESCE(cr.idestado, 1) = 1"
					// <--
					+ "       		 GROUP BY  pc.idpedido_regalos_cabe, cr.nrohojaarmado, cr.fechahojaarmado, CASE WHEN cr.nrohojarutafinal IS NOT NULL THEN 1 ELSE 0 END, pc.idempresa "
					+ "       		 )hr ON (deta.idpedido_regalos_cabe = hr.idpedido_regalos_cabe  AND hr.tienehr = 0  AND deta.idempresa = hr.idempresa)  "
					+ "           INNER JOIN pedidosregalosestados es ON (cabe.idestado = es.idestado  AND cabe.idempresa = es.idempresa)  "
					+ "           INNER JOIN clientesclientes cl ON (cabe.idcliente = cl.idcliente  AND cabe.idempresa = cl.idempresa) "
					+ "  WHERE deta.codigo_st = ' "
					+ codigo_st
					+ "'    "
					+
					// --> 20110603 - EJV - Mantis 716
					"   AND  hr.tienehr = 0 "
					// <--
					+ "  AND depo.codigo_dt = "
					+ codigo_dt
					// --> 20110603 - EJV - Mantis 716
					// + "       AND cabe.idestado IN (1, 2, 3, 4) "
					+ "       AND cabe.idestado IN (1, 2, 3) "
					// <--
					+ (fdesde != null && fhasta != null ? (" AND cabe.fechapedido::DATE BETWEEN '"
							+ fdesde + "'::DATE AND '" + fhasta + "'::DATE ")
							: "")

					+ "       AND deta.idempresa = "
					+ idempresa
					+ " ORDER BY 3, 1 ";

		// <--

		// log.info("QUERY: " + cQuery);
		List vecSalida = getLista(cQuery);
		return vecSalida;

	}

	/**
	 * Metodos para la entidad: stockSerieDespacho Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Tue Jan 19 14:13:10 GMT-03:00 2010
	 */

	public List getStockSerieDespachoAll(long limit, long offset,
			String codigo_st, BigDecimal codigo_dt, String id_indi_st,
			String despa_st, BigDecimal idempresa) throws EJBException {

		String cQuery = "";

		if (id_indi_st.equalsIgnoreCase("S"))

			cQuery = "SELECT idseriedespacho,nromov_sd,codigo_st,codigo_dt,nroserie,nrodespacho,"
					+ "       cantidad,cantreserva,fechain,fechaout,"
					+ "       idempresa,usuarioalt,usuarioact,fechaalt,fechaact"
					+ "  FROM stockseriedespacho "
					+ " WHERE codigo_st = '"
					+ codigo_st.toUpperCase()
					+ "' AND codigo_dt = "
					+ codigo_dt
					+ "  AND fechaout IS NULL AND nroserie IS NOT NULL AND idempresa = "
					+ idempresa.toString()
					+ "  ORDER BY 2  LIMIT "
					+ limit
					+ " OFFSET  " + offset + ";";

		else if (despa_st.equalsIgnoreCase("S"))

			cQuery = "SELECT idseriedespacho,nromov_sd,codigo_st,codigo_dt,nroserie,nrodespacho,"
					+ "       cantidad,cantreserva,fechain,fechaout,"
					+ "       idempresa,usuarioalt,usuarioact,fechaalt,fechaact"
					+ "  FROM stockseriedespacho"
					+ " WHERE codigo_st = '"
					+ codigo_st.toUpperCase()
					+ "' AND codigo_dt = "
					+ codigo_dt
					+ "  AND fechaout IS NULL AND nrodespacho IS NOT NULL AND idempresa = "
					+ idempresa.toString()
					+ "  ORDER BY 2  LIMIT "
					+ limit
					+ " OFFSET  " + offset + ";";

		List vecSalida = getLista(cQuery);

		return vecSalida;

	}

	public List getStockSerieDespachoOcu(long limit, long offset,
			String codigo_st, BigDecimal codigo_dt, String id_indi_st,
			String despa_st, String ocurrencia, BigDecimal idempresa)
			throws EJBException {

		String cQuery = "";
		if (id_indi_st.equalsIgnoreCase("S"))

			cQuery = "SELECT idseriedespacho,nromov_sd,codigo_st,codigo_dt,nroserie,nrodespacho,cantidad,"
					+ "       cantreserva,fechain,fechaout,idempresa,usuarioalt,usuarioact,fechaalt,fechaact "
					+ "  FROM stockseriedespacho"
					+ " WHERE (UPPER(nroserie) LIKE '%"
					+ ocurrencia.toUpperCase().trim()
					+ "%')  AND codigo_st = '"
					+ codigo_st.toUpperCase()
					+ "' AND codigo_dt = "
					+ codigo_dt
					+ "  AND fechaout IS NULL AND nroserie IS NOT NULL AND idempresa = "
					+ idempresa.toString()
					+ " ORDER BY 2  LIMIT "
					+ limit
					+ " OFFSET  " + offset + ";";

		else if (despa_st.equalsIgnoreCase("S"))

			cQuery = "SELECT idseriedespacho,nromov_sd,codigo_st,codigo_dt,nroserie,nrodespacho,cantidad,"
					+ "       cantreserva,fechain,fechaout,idempresa,usuarioalt,usuarioact,fechaalt,fechaact "
					+ "  FROM stockseriedespacho"
					+ " WHERE (UPPER(nrodespacho) LIKE '%"
					+ ocurrencia.toUpperCase().trim()
					+ "%')  AND codigo_st = '"
					+ codigo_st.toUpperCase()
					+ "' AND codigo_dt = "
					+ codigo_dt
					+ "  AND fechaout IS NULL AND nrodespacho IS NOT NULL AND idempresa = "
					+ idempresa.toString()
					+ " ORDER BY 2  LIMIT "
					+ limit
					+ " OFFSET  " + offset + ";";

		List vecSalida = getLista(cQuery);

		return vecSalida;

	}

	// para descomprometer
	public List getArticuloDescompromiso(String articulo, BigDecimal deposito,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "select articu_sb,deposi_sb, canti_sb::numeric(18) as disponible, pedid_sb as reservado, canti_sb::numeric(18) + pedid_sb::numeric(18) as existencia "
				+ " from stockstockbis where upper(articu_sb) = '"
				+ articulo.toUpperCase() + "' and deposi_sb = " + deposito
				+ " and idempresa = " + idempresa.toString();

		List vecSalida = getLista(cQuery);

		return vecSalida;
	}

	public String descomprometerUpdate(String articulo, BigDecimal deposito,
			BigDecimal idempresa, BigDecimal disponible, BigDecimal reservado,
			String usuarioact) throws EJBException {
		String salida = "OK";
		BigDecimal ant_reservado = new BigDecimal(0);
		BigDecimal ant_disponible = new BigDecimal(0);
		BigDecimal ant_existencia = new BigDecimal(0);
		try {
			List datos = getArticuloDescompromiso(articulo, deposito, idempresa);
			Iterator iterMov = datos.iterator();
			while (iterMov.hasNext()) {
				String[] sCampos = (String[]) iterMov.next();
				ant_reservado = new BigDecimal(sCampos[3]);
				ant_disponible = new BigDecimal(sCampos[2]);
				ant_existencia = new BigDecimal(sCampos[4]);
			}
			// validaciones antes de hacer el update
			/*
			 * 1. que la suma del disponible mas reservado = existencia anterior
			 * 2. NO PUEDO VALIDAR que sean > 0 3. que lo que pone en disponible
			 * sea <= reservado anterior.
			 */
			BigDecimal existencia = new BigDecimal(0);
			existencia = disponible.add(reservado);
			log.info("existencia " + existencia);
			if (existencia.compareTo(ant_existencia) != 0) {
				salida = "Error: Los calculos de existencia y reserva difieren de las cantidades anteriores. Por favor verifique";
			}

			if (disponible.compareTo(ant_reservado) > 0) {
				salida = "Error: Esta intentando colocar una existencia superior a la que tenia reservada. Por favor verifique ";
			}

			if (salida.equalsIgnoreCase("OK")) {
				PreparedStatement insert = null;
				String sql = "update stockstockbis set pedid_sb=? , canti_sb=?, usuarioact=? where  articu_sb = ? and deposi_sb =? and idempresa =?";
				insert = dbconn.prepareStatement(sql);
				insert.setBigDecimal(1, reservado);
				insert.setBigDecimal(2, disponible);
				insert.setString(3, usuarioact);
				insert.setString(4, articulo);
				insert.setBigDecimal(5, deposito);
				insert.setBigDecimal(6, idempresa);
				int i = insert.executeUpdate();
				salida = "Actualizacion Correcta";
			}
		} catch (SQLException sqlException) {
			log.error("(SQLE) descomprometerUpdate(): " + sqlException);

		} catch (Exception ex) {
			log.error("(EX) descomprometerUpdate():" + ex);

		}

		return salida;
	}

	public List getStockSerieDespachoPK(BigDecimal idseriedespacho,
			BigDecimal idempresa) throws EJBException {

		String cQuery = ""
				+ "SELECT idseriedespacho,nromov_sd,codigo_st,codigo_dt,nroserie,nrodespacho,cantidad,cantreserva,fechain,fechaout,"
				+ "       idempresa,usuarioalt,usuarioact,fechaalt,fechaact"
				+ "  FROM stockseriedespacho " + " WHERE idseriedespacho="
				+ idseriedespacho.toString() + " AND idempresa = "
				+ idempresa.toString() + ";";
		List vecSalida = getLista(cQuery);

		return vecSalida;
	}

	public static List getStockSerieDespachoStaticPK(
			BigDecimal idseriedespacho, Connection conn, BigDecimal idempresa)
			throws EJBException {

		String cQuery = ""
				+ "SELECT idseriedespacho,nromov_sd,codigo_st,codigo_dt,nroserie,nrodespacho,cantidad,cantreserva,fechain,fechaout,"
				+ "       idempresa,usuarioalt,usuarioact,fechaalt,fechaact"
				+ "  FROM stockseriedespacho " + " WHERE idseriedespacho="
				+ idseriedespacho.toString() + " AND idempresa = "
				+ idempresa.toString() + ";";
		List vecSalida = new ArrayList();

		try {
			Statement statement = conn.createStatement();
			ResultSet rsSalida = statement.executeQuery(cQuery);
			ResultSetMetaData md = rsSalida.getMetaData();
			// log.info("A-METODO LLAMADA:" + getCallingMethodName());

			// GeneralBean.getCallingMethodName(0);

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
					.error("(SQLE) getStockSerieDespachoStaticPK(): "
							+ sqlException);

		} catch (Exception ex) {
			log.error("(EX) getStockSerieDespachoStaticPK():" + ex);

		}

		return vecSalida;
	}

	public static List getStockSerieDespachoNrodespacho(String nrodespacho,
			String codigo_st, BigDecimal codigo_dt, Connection conn,
			BigDecimal idempresa) throws EJBException {

		String cQuery = ""
				+ "SELECT idseriedespacho,nromov_sd,codigo_st,codigo_dt,nroserie,nrodespacho,cantidad,cantreserva,fechain,fechaout,"
				+ "       idempresa,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM stockseriedespacho " + " WHERE nrodespacho='"
				+ nrodespacho.toUpperCase() + "' AND codigo_st = '"
				+ codigo_st.toUpperCase() + "' AND codigo_dt = "
				+ codigo_dt.toString()
				+ "  AND fechaout IS NULL AND idempresa = "
				+ idempresa.toString() + ";";

		List vecSalida = new ArrayList();

		try {
			Statement statement = conn.createStatement();
			ResultSet rsSalida = statement.executeQuery(cQuery);
			ResultSetMetaData md = rsSalida.getMetaData();
			// log.info("A-METODO LLAMADA:" + getCallingMethodName());

			// GeneralBean.getCallingMethodName(0);

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
					.error("(SQLE) getStockSerieDespachoStaticPK(): "
							+ sqlException);

		} catch (Exception ex) {
			log.error("(EX) getStockSerieDespachoStaticPK():" + ex);

		}

		return vecSalida;

	}

	public static boolean existeStockSerieDespachoNroserie(String nroserie,
			String codigo_st, Connection conn, BigDecimal idempresa)
			throws EJBException {
		PreparedStatement statement;
		String qExiste = ""
				+ "SELECT count(1) FROM stockseriedespacho "
				+ " WHERE nroserie=? AND codigo_st=? AND idempresa=? AND fechaout IS NULL;";
		ResultSet rsExiste;
		boolean existe = false;
		try {
			statement = conn.prepareStatement(qExiste);
			statement.setString(1, nroserie.toUpperCase());
			statement.setString(2, codigo_st.toUpperCase());
			statement.setBigDecimal(3, idempresa);
			rsExiste = statement.executeQuery();
			if (rsExiste.next()) {
				if (rsExiste.getBigDecimal(1).compareTo(new BigDecimal("0")) > 0)
					existe = true;
			}

		} catch (Exception e) {
			log
					.error("existeStockSerieDespachoNroserie(String articulo, BigDecimal deposito): "
							+ e);
		}

		return existe;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt
	public static String stockSerieDespachoCreate(BigDecimal nromov_sd,
			String codigo_st, BigDecimal codigo_dt, String nroserie,
			String nrodespacho, BigDecimal cantidad, BigDecimal cantreserva,
			java.sql.Date fechain, java.sql.Date fechaout, Connection conn,
			BigDecimal idempresa, String usuarioalt) throws EJBException {
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (nromov_sd == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: nromov_sd ";
		if (codigo_st == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_st ";
		if (codigo_dt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_dt ";
		if (cantidad == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cantidad ";
		if (cantreserva == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: cantreserva ";
		if (fechain == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechain ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
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
				String ins = ""
						+ "INSERT INTO stockseriedespacho"
						+ "           (nromov_sd, codigo_st, codigo_dt, nroserie, nrodespacho, cantidad, cantreserva, fechain, fechaout, idempresa, usuarioalt ) "
						+ "    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = conn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, nromov_sd);
				insert.setString(2, codigo_st);
				insert.setBigDecimal(3, codigo_dt);
				insert.setString(4, GeneralBean.setNull(nroserie));
				insert.setString(5, GeneralBean.setNull(nrodespacho));
				insert.setBigDecimal(6, cantidad);
				insert.setBigDecimal(7, cantreserva);
				insert.setDate(8, fechain);
				insert.setDate(9, fechaout);
				insert.setBigDecimal(10, idempresa);
				insert.setString(11, usuarioalt);
				int n = insert.executeUpdate();
				if (n != 1)
					salida = "No se pudo generar registro para stock serie / despacho: "
							+ codigo_st;
			}
		} catch (SQLException sqlException) {
			salida = "(SQLE) Imposible generar registro para stock  serie / despacho: "
					+ codigo_st;
			log.error("Error SQL public String stockSerieDespachoCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "(EX) Imposible generar registro para stock  serie / despacho: "
					+ codigo_st;
			log
					.error("Error excepcion public String stockSerieDespachoCreate(.....)"
							+ ex);
		}

		return salida;

	}

	public static String stockSerieDespachoFechaOutUpdate(
			BigDecimal idseriedespacho, java.sql.Date fechaout,
			Connection conn, BigDecimal idempresa, String usuarioact)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idseriedespacho == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idseriedespacho ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// fin validaciones

		try {

			PreparedStatement insert = null;
			String sql = "";
			if (salida.equalsIgnoreCase("OK")) {
				sql = "UPDATE STOCKSERIEDESPACHO SET fechaout=?, idempresa=?, usuarioact=?, fechaact=? WHERE idseriedespacho=?;";
				insert = conn.prepareStatement(sql);
				insert.setDate(1, fechaout);
				insert.setBigDecimal(2, idempresa);
				insert.setString(3, usuarioact);
				insert.setTimestamp(4, fechaact);
				insert.setBigDecimal(5, idseriedespacho);

				int i = insert.executeUpdate();
				if (i != 1)
					salida = "No se pudo actualizar fecha out para idseriedespacho: "
							+ idseriedespacho;

			}
		} catch (SQLException sqlException) {
			salida = "(SQLE) Imposible actualizar fecha out para idseriedespacho: "
					+ idseriedespacho;
			log.error("Error SQL public String stockSerieDespachoUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "(EX) Imposible actualizar fecha out para idseriedespacho: "
					+ idseriedespacho;
			log
					.error("Error excepcion public String stockSerieDespachoUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * EJV - 20100910 - CONFORMACION DE REMITOS -->
	 * */

	public static String[] stockMovSalidaWAPEntregadoCreate(
			BigDecimal codigo_anexo, String razon_social, String domicilio,
			String codigo_postal, BigDecimal idlocalidad,
			BigDecimal idprovincia, String cuit, String iibb,
			Timestamp fechamov, BigDecimal remito_ms, String tipomov,
			BigDecimal sucursal, String tipo, boolean remitopendiente,
			int ejercicioactivo, String observaciones, Hashtable htArticulos,
			Hashtable htCuentas, BigDecimal idcontadorcomprobante,
			Connection conn, String usuarioalt, BigDecimal idempresa)
			throws EJBException, SQLException {

		String salida = "OK";
		BigDecimal comprob_ms = BigDecimal.valueOf(-1);
		BigDecimal nrointerno_ms = BigDecimal.valueOf(-1);
		Enumeration en;
		String tipomov_ms = "S"; // TODO
		String sistema_ms = "S"; // TODO
		BigDecimal cuenta_cs = new BigDecimal(0);
		BigDecimal importe_cs = new BigDecimal(0);
		String tipo_cs = "H";
		String sistema_cs = "M";
		BigDecimal centr1_cs = new BigDecimal(0);
		BigDecimal centr2_cs = new BigDecimal(0);

		BigDecimal totalDebe = new BigDecimal(0);
		BigDecimal totalHaber = new BigDecimal(0);

		/*
		 * 20070604 EJV Lineas detalle por Remito.
		 */
		int maxLineasXremito = 0;
		int indice = 0;
		Hashtable htArticulosImagen = htArticulos;
		Hashtable htArticulosPaginados = null;
		String[] resultado = new String[2];
		/**/

		/*
		 * */

		/*
		 * 20071203 EJV Comprobantes internos - sucursal
		 */

		BigDecimal remito_interno_ms = new BigDecimal(-1);
		BigDecimal sucu_ms = new BigDecimal(-1);
		// BigDecimal idcontadorcomprobante =
		// getIdcontadorComprobante(usuarioalt, idempresa);

		sucu_ms = GeneralBean.getSucursalComprobante(idcontadorcomprobante,
				idempresa, conn);
		if (idcontadorcomprobante == null
				|| idcontadorcomprobante.longValue() < 0) {
			salida = "El idcontador es incorrecto - no esta asociado o hubo un error al recuperarlo.";
		} else if (sucu_ms == null || sucu_ms.longValue() < 0) {
			salida = "Verificar sucursal definida para el contador de remitos del puesto.";
		}
		/**/

		log.info("INICIANDO ---- > stockMovSalidaWAPEntregadoCreate ..."
				+ salida);

		try {
			// TODO: REALIZAR TODAS LAS VALIDACIONES
			// Pasos del movimiento ...
			// 0.0 - Generar anexo .... stockanexos
			// 1.0 - Generar movimiento entrada .... stockmovstock
			// 2.0 - Actualizar entrada stock .... stockbis
			// 3.0 - Generar movimiento historico .... stockhis
			// 4.0 - Generar movimiento contable .... stockcontstock
			// 4.1 - Generar remito pendiente .... stockremitos

			//
			//
			// dbconn.setAutoCommit(false);
			//
			//		
			//

			/*
			 * 20070604 EJV Lineas detalle por Remito.
			 */
			try {
				maxLineasXremito = Integer.parseInt(GeneralBean
						.getValorSetupVariables("maxLineasXremito", idempresa,
								conn));
			} catch (Exception e) {
				salida = "Imposible recuperar lineas detalle remito.";
				log.error("maxLineasXremito: " + e);
			}
			/*
			 * */

			if (htArticulos != null && !htArticulos.isEmpty()) {

				while (salida.equalsIgnoreCase("OK")) {

					comprob_ms = BigDecimal.valueOf(-1);
					nrointerno_ms = BigDecimal.valueOf(-1);
					tipomov_ms = "S"; // TODO
					sistema_ms = "S"; // TODO
					cuenta_cs = new BigDecimal(0);
					importe_cs = new BigDecimal(0);
					tipo_cs = "H";
					sistema_cs = "M";
					centr1_cs = new BigDecimal(0);
					centr2_cs = new BigDecimal(0);
					totalDebe = new BigDecimal(0);
					totalHaber = new BigDecimal(0);

					indice = 0;
					htArticulosPaginados = new Hashtable();
					Enumeration enu = htArticulosImagen.keys();
					while (enu.hasMoreElements() && indice < maxLineasXremito) {
						Object elemento = enu.nextElement();
						htArticulosPaginados.put(elemento, htArticulosImagen
								.get(elemento));
						htArticulosImagen.remove(elemento);
						indice++;
					}

					if (htArticulosPaginados.isEmpty())
						break;
					en = htArticulosPaginados.keys();
					/*
					 * */
					// en = htArticulos.keys();
					/*
					 * 20070604 EJV Lineas detalle por Remito.
					 */

					// nrointerno_ms = getValorSequencia("seq_stockmovstock");
					// 20061101 - nrointerno_ms permite duplicados
					nrointerno_ms = GeneralBean.getContador(new BigDecimal(5),
							idempresa, conn);
					comprob_ms = nrointerno_ms;
					remito_interno_ms = GeneralBean.getContador(
							idcontadorcomprobante, idempresa, conn);

					//
					log
							.info("idcontadorcomprobante : "
									+ idcontadorcomprobante);
					log.info("remito_interno_m s: " + remito_interno_ms);
					log.info("sucu_ms : " + sucu_ms);
					//

					salida = stockAnexosCreate(nrointerno_ms, codigo_anexo,
							razon_social, domicilio, codigo_postal,
							idlocalidad, idprovincia, cuit, iibb, conn,
							usuarioalt, idempresa);

					if (salida.equalsIgnoreCase("OK")) {

						Hashtable listaMS = new Hashtable();
						Hashtable listaSCS = new Hashtable();

						while (en.hasMoreElements()) {

							String key = (String) en.nextElement();
							String[] datosArticulo = (String[]) htArticulosPaginados
									.get(key);
							String articulo = datosArticulo[0];
							BigDecimal cantArtMov = new BigDecimal(
									datosArticulo[10]);
							BigDecimal origen = new BigDecimal(datosArticulo[9]);
							BigDecimal costo = new BigDecimal(datosArticulo[4]);

							importe_cs = new BigDecimal(datosArticulo[11]);
							cuenta_cs = new BigDecimal(datosArticulo[12]);

							// 20091002 - EJV - La reserva no puede
							// manejarse en negativo.

							// if
							// ((GeneralBean.getCantidadReservaArticuloDeposito(
							// articulo, origen, idempresa, dbconn)
							// .compareTo(cantArtMov)) < 0
							// && !GeneralBean.hasStockNegativo(idempresa,
							// dbconn)) {

							if ((GeneralBean
									.getCantidadReservaArticuloDeposito(
											articulo, origen, idempresa, conn)
									.compareTo(cantArtMov)) < 0) {
								salida = "Existencia en reserva de articulo "
										+ articulo
										+ " insuficiente en deposito " + origen
										+ ".";
								break;
							}

							if (!GeneralBean.isExisteCtaImputable(cuenta_cs,
									ejercicioactivo, idempresa, conn)) {
								salida = "La cuenta " + cuenta_cs
										+ " asociada al articulo " + articulo
										+ " no existe o no es imputable.";
								break;
							}

							/**
							 * @COMENTARIO: 20081104 - EJV. Corregir generacion
							 *              de asiento inconsistente. Agregar:
							 *              if (!remitopendiente).
							 * 
							 */

							if (!remitopendiente) {

								totalDebe = totalDebe.add(importe_cs);

								if (!listaSCS.containsKey(cuenta_cs)) {
									salida = stockContStockCreate(
											nrointerno_ms, cuenta_cs,
											importe_cs, tipo_cs, sistema_cs,
											centr1_cs, centr2_cs, conn,
											usuarioalt, idempresa);
								} else {
									salida = stockContStockImporteUpdate(
											nrointerno_ms, cuenta_cs,
											importe_cs, conn, idempresa);
								}

							}
							// FIN 20081104

							if (!salida.equalsIgnoreCase("OK"))
								break;

							/*
							 * INICIA ACTUALIZACION DE STOCK ----------------
							 * nrointerno_ms : sistema_ms : tipomov_ms :
							 * comprob_ms : fecha_ms : articu_ms : canti_ms :
							 * moneda_ms : cambio_ms : venta_ms : costo_ms :
							 * tipoaux_ms : destino_ms : comis_ms : remito_ms :
							 * impint_ms : impifl_ms : impica_ms : prelis_ms :
							 * unidad_ms : merma_ms : saldo_ms : medida_ms :
							 * observaciones : usuarioalt : usuarioact :
							 * fechaalt : fechaact :
							 */

							if (!listaMS.containsKey(articulo)) {
								salida = stockMovStockCreate(nrointerno_ms,
										sistema_ms, tipomov_ms, comprob_ms,
										fechamov, articulo, cantArtMov,
										new BigDecimal("1"), new Double("1"),
										new Double("0"), costo, sistema_cs,
										tipo, new Double("0"), remito_ms,
										new Double("0"), new Double("0"),
										new Double("0"), new Double("0"),
										new BigDecimal("0"), new Double("0"),
										new Double("0"), new BigDecimal("0"),
										observaciones, remito_interno_ms,
										sucu_ms, conn, usuarioalt, idempresa);
							} else {

								salida = stockMovStockCantidadUpdate(
										nrointerno_ms, articulo, cantArtMov,
										conn, idempresa);
							}

							if (!salida.equalsIgnoreCase("OK"))
								break;

							/*
							 * @Actualiza stockbis articu_sb : deposi_sb :
							 * canti_sb : serie_sb : despa_sb : pedid_sb :
							 * usuarioalt : usuarioact : fechaalt : fechaact :
							 */

							salida = stockStockBisPedid_sbUpdate(articulo,
									origen, new BigDecimal("0"), "", "",
									cantArtMov.negate(), conn, usuarioalt,
									idempresa);

							if (!salida.equalsIgnoreCase("OK"))
								break;

							/*
							 * nromov_sh : articu_sh : deposi_sh : serie_sh :
							 * despa_sh : canti_sh : estamp1_sh : estamp2_sh :
							 * aduana_sh : usuarioalt : usuarioact : fechaalt :
							 * fechaact :
							 */

							salida = stockHisCreate(nrointerno_ms, articulo,
									origen, "", "", cantArtMov, "", "", "",
									conn, usuarioalt, idempresa);
							if (!salida.equalsIgnoreCase("OK"))
								break;

							listaMS.put(articulo, articulo);
							listaSCS.put(cuenta_cs, cuenta_cs);

						}

						listaMS = null;
						listaSCS.clear();

						if (salida.equalsIgnoreCase("OK")) {
							if (!remitopendiente) {
								// Generar imputacion
								if (htCuentas != null && !htCuentas.isEmpty()) {
									en = htCuentas.keys();
									tipo_cs = "D";
									while (en.hasMoreElements()) {

										String clave = (String) en
												.nextElement();
										String[] datosImputacion = (String[]) htCuentas
												.get(clave);
										cuenta_cs = new BigDecimal(
												datosImputacion[0]);
										importe_cs = new BigDecimal(
												datosImputacion[2]);

										if (!listaSCS.containsKey(cuenta_cs)) {
											salida = stockContStockCreate(
													nrointerno_ms, cuenta_cs,
													importe_cs, tipo_cs,
													sistema_cs, centr1_cs,
													centr2_cs, conn,
													usuarioalt, idempresa);
										} else {
											salida = stockContStockImporteUpdate(
													nrointerno_ms, cuenta_cs,
													importe_cs, conn, idempresa);
										}

										if (!salida.equalsIgnoreCase("OK"))
											break;

										listaSCS.put(cuenta_cs, cuenta_cs);
										totalHaber = totalHaber.add(importe_cs);

									}

									if (totalDebe.compareTo(totalHaber) != 0) {
										salida = "El movimiento contable no esta balanceado: [D = "
												+ totalDebe
												+ " - H = "
												+ totalHaber + "]";
									}

								} else {
									salida = "Transaccion abortada, no existe imputacion.";
								}
							} else {
								// generar remito pendiente ....
								BigDecimal codigo_rm = codigo_anexo;
								salida = stockRemitosCreate(nrointerno_ms,
										remito_ms, fechamov, tipo, tipomov_ms,
										codigo_rm, null, sucursal, conn,
										usuarioalt, idempresa);
							}
						}
					}

					/*
					 * 20070604 EJV Lineas detalle por Remito.
					 */
					resultado[0] = salida;
					resultado[1] = resultado[1] != null ? resultado[1] + "-"
							+ nrointerno_ms.toString() : nrointerno_ms
							.toString();
				}

			}

		} catch (Exception e) {
			// TODO: handle exception
			salida = "E-1000: Ocurrio Excepcion Mientras Se Actualizaba Stock."
					+ "{" + e + "}";
			resultado[0] = salida;
			log.error("stockMovSalidaWAPEntregadoCreate(): " + e);
		}

		// if (!salida.equalsIgnoreCase("OK"))
		// dbconn.rollback();

		//
		//
		//
		// dbconn.setAutoCommit(true);
		//
		//
		//
		resultado[0] = salida;

		return resultado;

	}

	public static String[] stockMovEntradaWAPLimpiaEntregaCreate(
			BigDecimal codigo_anexo, String razon_social, String domicilio,
			String codigo_postal, BigDecimal idlocalidad,
			BigDecimal idprovincia, String cuit, String iibb,
			Timestamp fechamov, BigDecimal remito_ms, String tipomov,
			BigDecimal sucursal, String tipo, boolean remitopendiente,
			int ejercicioactivo, String observaciones, Hashtable htArticulos,
			Hashtable htCuentas, BigDecimal idcontadorcomprobante,
			Connection conn, String usuarioalt, BigDecimal idempresa)
			throws EJBException, SQLException {

		String salida = "OK";
		BigDecimal comprob_ms = BigDecimal.valueOf(-1);
		BigDecimal nrointerno_ms = BigDecimal.valueOf(-1);
		Enumeration en;
		String tipomov_ms = "E"; // TODO
		String sistema_ms = "S"; // TODO
		BigDecimal cuenta_cs = new BigDecimal(0);
		BigDecimal importe_cs = new BigDecimal(0);
		String tipo_cs = "D";
		String sistema_cs = "M";
		BigDecimal centr1_cs = new BigDecimal(0);
		BigDecimal centr2_cs = new BigDecimal(0);
		BigDecimal totalDebe = new BigDecimal(0);
		BigDecimal totalHaber = new BigDecimal(0);
		/*
		 * 20070604 EJV Lineas detalle por Remito.
		 */
		int maxLineasXremito = 0;
		int indice = 0;
		Hashtable htArticulosImagen = htArticulos;
		Hashtable htArticulosPaginados = null;
		String[] resultado = new String[2];

		/**/
		try {
			// TODO: REALIZAR TODAS LAS VALIDACIONES
			// Pasos del movimiento ...
			// 0.0 - Generar anexo .... stockanexos
			// 1.0 - Generar movimiento entrada .... stockmovstock
			// 2.0 - Actualizar entrada stock .... stockbis
			// 3.0 - Generar movimiento historico .... stockhis
			// 4.0 - Generar movimiento contable .... stockcontstock
			// 4.1 - Generar remito pendiente .... stockremitos

			//
			//
			// dbconn.setAutoCommit(false);
			//
			//

			/*
			 * 20070604 EJV Lineas detalle por Remito.
			 */
			try {
				maxLineasXremito = Integer.parseInt(GeneralBean
						.getValorSetupVariables("maxLineasXremito", idempresa,
								conn));
			} catch (Exception e) {
				salida = "Imposible recuperar lineas detalle remito.";
				log.error("maxLineasXremito: " + e);
			}
			/*
			 * */

			/*
			 * 20071203 EJV Comprobantes internos - sucursal
			 */

			BigDecimal remito_interno_ms = new BigDecimal(-1);
			BigDecimal sucu_ms = new BigDecimal(-1);
			// BigDecimal idcontadorcomprobante = getIdcontadorComprobante(
			// usuarioalt, idempresa);

			sucu_ms = GeneralBean.getSucursalComprobante(idcontadorcomprobante,
					idempresa, conn);
			if (idcontadorcomprobante == null
					|| idcontadorcomprobante.longValue() < 0) {
				salida = "El idcontador es incorrecto - no esta asociado o hubo un error al recuperarlo.";
				resultado[0] = salida;
			} else if (sucu_ms == null || sucu_ms.longValue() < 0) {
				salida = "Verificar sucursal definida para el contador de remitos del puesto.";
				resultado[0] = salida;
			}
			/**/

			if (htArticulos != null && !htArticulos.isEmpty()) {
				/*
				 * 20070604 EJV Lineas detalle por Remito.
				 */
				while (salida.equalsIgnoreCase("OK")) {

					comprob_ms = BigDecimal.valueOf(-1);
					nrointerno_ms = BigDecimal.valueOf(-1);
					tipomov_ms = "E"; // TODO
					sistema_ms = "S"; // TODO
					cuenta_cs = new BigDecimal(0);
					importe_cs = new BigDecimal(0);
					tipo_cs = "D";
					sistema_cs = "M";
					centr1_cs = new BigDecimal(0);
					centr2_cs = new BigDecimal(0);
					totalDebe = new BigDecimal(0);
					totalHaber = new BigDecimal(0);

					indice = 0;
					htArticulosPaginados = new Hashtable();
					Enumeration enu = htArticulosImagen.keys();
					while (enu.hasMoreElements() && indice < maxLineasXremito) {
						Object elemento = enu.nextElement();
						htArticulosPaginados.put(elemento, htArticulosImagen
								.get(elemento));
						htArticulosImagen.remove(elemento);
						indice++;
					}

					if (htArticulosPaginados.isEmpty())
						break;
					en = htArticulosPaginados.keys();
					/*
					 * */
					// en = htArticulos.keys();
					// nrointerno_ms = getValorSequencia("seq_stockmovstock");
					// 20061101 - nrointerno_ms permite duplicados
					nrointerno_ms = GeneralBean.getContador(new BigDecimal(5),
							idempresa, conn);
					comprob_ms = nrointerno_ms;
					salida = stockAnexosCreate(nrointerno_ms, codigo_anexo,
							razon_social, domicilio, codigo_postal,
							idlocalidad, idprovincia, cuit, iibb, conn,
							usuarioalt, idempresa);
					remito_interno_ms = GeneralBean.getContador(
							idcontadorcomprobante, idempresa, conn);

					if (salida.equalsIgnoreCase("OK")) {

						Hashtable listaMS = new Hashtable();
						Hashtable listaSCS = new Hashtable();

						while (en.hasMoreElements()) {

							String key = (String) en.nextElement();
							String[] datosArticulo = (String[]) htArticulosPaginados
									.get(key);

							String articulo = datosArticulo[0];

							BigDecimal cantArtMov = new BigDecimal(
									datosArticulo[10]);

							BigDecimal destino = new BigDecimal(
									datosArticulo[9]);

							BigDecimal costo = new BigDecimal(datosArticulo[4]);

							importe_cs = new BigDecimal(datosArticulo[11]);

							cuenta_cs = new BigDecimal(datosArticulo[12]);

							if (!GeneralBean.isExisteCtaImputable(cuenta_cs,
									ejercicioactivo, idempresa, conn)) {
								salida = "La cuenta " + cuenta_cs
										+ " asociada al articulo " + articulo
										+ " no existe o no es imputable.";
								break;
							}

							/**
							 * @COMENTARIO: 20081104 - EJV. Corregir generacion
							 *              de asiento inconsistente. Agregar:
							 *              if (!remitopendiente).
							 * 
							 */

							if (!remitopendiente) {

								totalDebe = totalDebe.add(importe_cs);

								if (!listaSCS.containsKey(cuenta_cs)) {
									salida = stockContStockCreate(
											nrointerno_ms, cuenta_cs,
											importe_cs, tipo_cs, sistema_cs,
											centr1_cs, centr2_cs, conn,
											usuarioalt, idempresa);
								} else {
									salida = stockContStockImporteUpdate(
											nrointerno_ms, cuenta_cs,
											importe_cs, conn, idempresa);
								}

							}

							// FIN 20081104

							if (!salida.equalsIgnoreCase("OK"))
								break;

							/*
							 * INICIA ACTUALIZACION DE STOCK ----------------
							 * nrointerno_ms : sistema_ms : tipomov_ms :
							 * comprob_ms : fecha_ms : articu_ms : canti_ms :
							 * moneda_ms : cambio_ms : venta_ms : costo_ms :
							 * tipoaux_ms : destino_ms : comis_ms : remito_ms :
							 * impint_ms : impifl_ms : impica_ms : prelis_ms :
							 * unidad_ms : merma_ms : saldo_ms : medida_ms :
							 * observaciones : usuarioalt : usuarioact :
							 * fechaalt : fechaact :
							 */

							if (!listaMS.containsKey(articulo)) {
								salida = stockMovStockCreate(nrointerno_ms,
										sistema_ms, tipomov_ms, comprob_ms,
										fechamov, articulo, cantArtMov,
										new BigDecimal("1"), new Double("1"),
										new Double("0"), costo, sistema_cs,
										tipo, new Double("0"), remito_ms,
										new Double("0"), new Double("0"),
										new Double("0"), new Double("0"),
										new BigDecimal("0"), new Double("0"),
										new Double("0"), new BigDecimal("0"),
										observaciones, remito_interno_ms,
										sucu_ms, conn, usuarioalt, idempresa);
							} else {

								salida = stockMovStockCantidadUpdate(
										nrointerno_ms, articulo, cantArtMov,
										conn, idempresa);
							}

							if (!salida.equalsIgnoreCase("OK"))
								break;

							/*
							 * @Actualiza stockbis articu_sb : deposi_sb :
							 * canti_sb : serie_sb : despa_sb : pedid_sb :
							 * usuarioalt : usuarioact : fechaalt : fechaact :
							 */

							if (GeneralBean.getExisteArticuloDeposito(articulo,
									destino, idempresa, conn)) {

								salida = stockStockBisPedid_sbUpdate(articulo,
										destino, new BigDecimal("0"), "", "",
										cantArtMov, conn, usuarioalt, idempresa);
							} else {
								salida = stockStockBisCreate(articulo, destino,
										new BigDecimal("0"), "", "",
										cantArtMov, conn, usuarioalt, idempresa);
							}

							if (!salida.equalsIgnoreCase("OK"))
								break;

							/*
							 * nromov_sh : articu_sh : deposi_sh : serie_sh :
							 * despa_sh : canti_sh : estamp1_sh : estamp2_sh :
							 * aduana_sh : usuarioalt : usuarioact : fechaalt :
							 * fechaact :
							 */

							salida = stockHisCreate(nrointerno_ms, articulo,
									destino, "", "", cantArtMov, "", "", "",
									conn, usuarioalt, idempresa);

							if (!salida.equalsIgnoreCase("OK"))
								break;

							listaMS.put(articulo, articulo);
							listaSCS.put(cuenta_cs, cuenta_cs);

						}

						listaMS = null;
						listaSCS.clear();

						if (salida.equalsIgnoreCase("OK")) {
							if (!remitopendiente) {
								// Generar imputacion
								if (htCuentas != null && !htCuentas.isEmpty()) {
									en = htCuentas.keys();
									tipo_cs = "H";
									while (en.hasMoreElements()) {

										String clave = (String) en
												.nextElement();
										String[] datosImputacion = (String[]) htCuentas
												.get(clave);
										cuenta_cs = new BigDecimal(
												datosImputacion[0]);
										importe_cs = new BigDecimal(
												datosImputacion[2]);

										if (!listaSCS.containsKey(cuenta_cs)) {
											salida = stockContStockCreate(
													nrointerno_ms, cuenta_cs,
													importe_cs, tipo_cs,
													sistema_cs, centr1_cs,
													centr2_cs, conn,
													usuarioalt, idempresa);
										} else {
											salida = stockContStockImporteUpdate(
													nrointerno_ms, cuenta_cs,
													importe_cs, conn, idempresa);
										}

										if (!salida.equalsIgnoreCase("OK"))
											break;

										listaSCS.put(cuenta_cs, cuenta_cs);
										totalHaber = totalHaber.add(importe_cs);

									}

									if (totalDebe.compareTo(totalHaber) != 0) {
										salida = "El movimiento contable no esta balanceado: [D = "
												+ totalDebe
												+ " - H = "
												+ totalHaber + "]";
									}

								} else {
									salida = "Transaccion abortada, no existe imputacion.";
								}
							} else {
								// generar remito pendiente ....
								BigDecimal codigo_rm = codigo_anexo;
								salida = stockRemitosCreate(nrointerno_ms,
										remito_ms, fechamov, tipo, tipomov_ms,
										codigo_rm, null, sucursal, conn,
										usuarioalt, idempresa);
							}
						}
					}

					resultado[0] = salida;
					resultado[1] = resultado[1] != null ? resultado[1] + "-"
							+ nrointerno_ms.toString() : nrointerno_ms
							.toString();

				}// EJV 20070604
			}
		} catch (Exception e) {
			// TODO: handle exception
			salida = "E-1000: Ocurrio Excepcion Mientras Se Actualizaba Stock."
					+ "{" + e + "}";
			resultado[0] = salida;
			log.error("stockMovEntradaWAPLimpiaEntregaCreate(): " + e);
		}
		//
		//
		//
		// dbconn.setAutoCommit(true);
		//
		//
		//
		return resultado;

	}

	public static String[] stockMovStockRes2DispWAPRechazoCambioCreate(
			Hashtable htArticulos, Connection conn, String usuarioalt,
			BigDecimal idempresa) throws EJBException, SQLException {

		String salida = "OK";
		Enumeration en;
		String[] resultado = new String[2];

		// Mover cantidades de Reservado a disponible desde el mismo deposito.

		try {

			if (htArticulos != null && !htArticulos.isEmpty()) {
				en = htArticulos.keys();
				while (en.hasMoreElements() && salida.equalsIgnoreCase("OK")) {

					String key = (String) en.nextElement();
					String[] datosArticulo = (String[]) htArticulos.get(key);
					String articulo = datosArticulo[0];
					BigDecimal cantArtMov = new BigDecimal(datosArticulo[10]);
					BigDecimal origen = new BigDecimal(datosArticulo[9]);

					// 20091002 - EJV - La reserva no puede
					// manejarse en negativo.

					// if
					// ((GeneralBean.getCantidadReservaArticuloDeposito(
					// articulo, origen, idempresa, dbconn)
					// .compareTo(cantArtMov)) < 0
					// && !GeneralBean.hasStockNegativo(idempresa,
					// dbconn)) {

					if ((GeneralBean.getCantidadReservaArticuloDeposito(
							articulo, origen, idempresa, conn)
							.compareTo(cantArtMov)) < 0) {
						salida = "Existencia en reserva de articulo "
								+ articulo + " insuficiente en deposito "
								+ origen + ".";
						break;
					}

					// FIN 20081104

					/*
					 * @Actualiza stockbis articu_sb : deposi_sb : canti_sb :
					 * serie_sb : despa_sb : pedid_sb : usuarioalt : usuarioact
					 * : fechaalt : fechaact :
					 */

					salida = stockStockBisPedid_sbUpdate(articulo, origen,
							cantArtMov, "", "", cantArtMov.negate(), conn,
							usuarioalt, idempresa);

					if (!salida.equalsIgnoreCase("OK"))
						break;

					/*
					 * 20070604 EJV Lineas detalle por Remito.
					 */
					resultado[0] = salida;
					resultado[1] = "0";
				}

			}

		} catch (Exception e) {
			// TODO: handle exception
			salida = "E-1000: Ocurrio Excepcion Mientras Se Actualizaba Stock."
					+ "{" + e + "}";
			resultado[0] = salida;
			log.error("stockMovStockRes2DispWAPRechazoCambioCreate(): " + e);
		}

		resultado[0] = salida;
		return resultado;
	}

	public static String[] stockMovEntradaWAPLimpiaRechazoCambioCreate(
			Hashtable htArticulos, Connection conn, String usuarioalt,
			BigDecimal idempresa) throws EJBException, SQLException {
		// MANTIS 402: interfases despachos
		String salida = "OK";
		Enumeration en;
		String[] resultado = new String[2];

		// Mover cantidades de Reservado a disponible desde el mismo deposito.

		try {

			if (htArticulos != null && !htArticulos.isEmpty()) {
				en = htArticulos.keys();
				while (en.hasMoreElements() && salida.equalsIgnoreCase("OK")) {

					String key = (String) en.nextElement();
					String[] datosArticulo = (String[]) htArticulos.get(key);
					String articulo = datosArticulo[0];
					BigDecimal cantArtMov = new BigDecimal(datosArticulo[10]);
					BigDecimal origen = new BigDecimal(datosArticulo[9]);

					// 20091002 - EJV - La reserva no puede
					// manejarse en negativo.

					// if
					// ((GeneralBean.getCantidadReservaArticuloDeposito(
					// articulo, origen, idempresa, dbconn)
					// .compareTo(cantArtMov)) < 0
					// && !GeneralBean.hasStockNegativo(idempresa,
					// dbconn)) {

					if (!GeneralBean.getExisteArticuloDeposito(articulo,
							origen, idempresa, conn)) {
						salida = "No existe articulo " + articulo
								+ " en deposito " + origen + ".";
						break;
					}

					// FIN 20081104

					/*
					 * @Actualiza stockbis articu_sb : deposi_sb : canti_sb :
					 * serie_sb : despa_sb : pedid_sb : usuarioalt : usuarioact
					 * : fechaalt : fechaact :
					 */

					salida = stockStockBisPedid_sbUpdate(articulo, origen,
							cantArtMov.negate(), "", "", cantArtMov, conn,
							usuarioalt, idempresa);

					if (!salida.equalsIgnoreCase("OK"))
						break;

					/*
					 * 20070604 EJV Lineas detalle por Remito.
					 */
					resultado[0] = salida;
					resultado[1] = "0";
				}

			}

		} catch (Exception e) {
			// TODO: handle exception
			salida = "E-1000: Ocurrio Excepcion Mientras Se Actualizaba Stock."
					+ "{" + e + "}";
			resultado[0] = salida;
			log.error("stockMovEntradaWAPLimpiaRechazoCambioCreate(): " + e);
		}

		resultado[0] = salida;
		return resultado;
	}

	public static String stockStockBisPedid_sbUpdate(String articu_sb,
			BigDecimal deposi_sb, BigDecimal canti_sb, String serie_sb,
			String despa_sb, BigDecimal pedid_sb, Connection conn,
			String usuarioact, BigDecimal idempresa) throws EJBException {
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
				statement = conn.prepareStatement(qDML);
				statement.clearParameters();

				statement.clearParameters();
				statement.setBigDecimal(1, canti_sb);
				statement.setString(2, null);
				statement.setString(3, null);
				statement.setBigDecimal(4, pedid_sb);
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
			salida = "(SQLE) Imposible acrualizar Articulo (" + articu_sb
					+ ")  en deposito (" + deposi_sb + ")..";
			log
					.error("Error SQL public String stockStockBisPedid_sbUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "(EX) Imposible acrualizar Articulo (" + articu_sb
					+ ")  en deposito (" + deposi_sb + ")..";
			log
					.error("Error excepcion public String stockStockBisPedid_sbUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * CONFORMACION DE REMITOS <--
	 * */

	// recupera stock sin agrupar según la ocurrencia
	public List getStockStockMarketSinAgruparOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT st.codigo_st, st.alias_st, st.descrip_st, st.descri2_st, "
				+ "       l.precio::numeric(18, 2), l.precio::numeric(18, 2), "
				+ "       st.cost_uc_st::numeric(18, 2), st.ultcomp_st::numeric(18, 2), "
				+ "       st.cost_re_st::numeric(18, 2), st.reposic_st, st.nom_com_st, st.grupo_st,  "
				+ "       LO_EXPORT(img.trama, "
				+ blobSqlPath
				+ " || nombre), img.nombre "
				+ "  FROM STOCKSTOCK st"
				+ "       INNER JOIN clienteslistasdeprecios l ON st.codigo_st = l.codigo_st AND st.idempresa = l.idempresa AND l.idlista =  "
				+ marketIdlista
				+ "       LEFT  JOIN  globalblobimagenes img ON (st.oid = img.tupla)"
				+ " WHERE ((st.codigo_st::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase()
				+ "%'  OR UPPER(st.descrip_st) LIKE '%"
				+ ocurrencia.toUpperCase() + "%' )" + " AND st.idempresa = "
				+ idempresa.toString() + "  ORDER BY 3 LIMIT " + limit
				+ " OFFSET " + offset;
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	public List getStockStockSinAgrupar(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT st.codigo_st, st.alias_st, st.descrip_st, st.descri2_st, "
				+ "       l.precio::numeric(18, 2), l.precio::numeric(18, 2), "
				+ "       st.cost_uc_st::numeric(18, 2), st.ultcomp_st::numeric(18, 2), "
				+ "       st.cost_re_st::numeric(18, 2), st.reposic_st, st.nom_com_st, st.grupo_st,  "
				+ "       LO_EXPORT(img.trama, "
				+ blobSqlPath
				+ " || nombre), img.nombre "
				+ "  FROM STOCKSTOCK st"
				+ "       INNER JOIN clienteslistasdeprecios l ON st.codigo_st = l.codigo_st "
				+ "              AND l.idlista =  "
				+ marketIdlista
				+ " AND st.idempresa = l.idempresa"
				+ "       LEFT  JOIN  globalblobimagenes img ON (st.oid = img.tupla)"
				+ " WHERE st.idempresa = " + idempresa.toString()
				+ " ORDER BY 3 LIMIT " + limit + " OFFSET " + offset;
		List vecSalida = getLista(cQuery);
		return vecSalida;
	}

	/**
	 * Metodos para la entidad: stockDepositosLockRegalos Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Wed Nov 24 14:30:23 ART 2010
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getStockDepositosLockRegalosAll(long limit, long offset,
			BigDecimal codigo_dt, BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ " SELECT idlock,codigo_dt,fechadesde,fechahasta,comentarios,fechabaja,idempresa,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "    FROM stockdepositoslockregalos WHERE codigo_dt = "
				+ codigo_dt + " AND idempresa = " + idempresa.toString()
				+ "  ORDER BY 2  LIMIT " + limit + " OFFSET  " + offset + ";";
		List vecSalida = new ArrayList();
		try {
			vecSalida = getLista(cQuery);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getStockDepositosLockRegalosAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getStockDepositosLockRegalosOcu(long limit, long offset,
			BigDecimal codigo_dt, String ocurrencia, BigDecimal idempresa)
			throws EJBException {
		String cQuery = ""
				+ "SELECT  idlock,codigo_dt,fechadesde,fechahasta,comentarios,fechabaja,idempresa,usuarioalt,usuarioact,fechaalt,fechaact"
				+ "   FROM stockdepositoslockregalos WHERE (UPPER(CODIGO_DT) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%')  AND idempresa = "
				+ idempresa.toString() + " ORDER BY 2  LIMIT " + limit
				+ " OFFSET  " + offset + ";";
		List vecSalida = new ArrayList();
		try {
			vecSalida = getLista(cQuery);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getStockDepositosLockRegalosOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getStockDepositosLockRegalosPK(BigDecimal idlock,
			BigDecimal idempresa) throws EJBException {
		String cQuery = ""
				+ "SELECT  idlock,codigo_dt,fechadesde,fechahasta,comentarios,fechabaja,idempresa,usuarioalt,usuarioact,fechaalt,fechaact"
				+ "   FROM stockdepositoslockregalos WHERE idlock="
				+ idlock.toString() + " AND idempresa = "
				+ idempresa.toString() + ";";
		List vecSalida = new ArrayList();
		try {
			vecSalida = getLista(cQuery);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getStockDepositosLockRegalosPK( Long idlock )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String stockDepositosLockRegalosDelete(BigDecimal idlock,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM STOCKDEPOSITOSLOCKREGALOS WHERE idlock="
				+ idlock.toString() + " AND fechabaja IS NULL  AND idempresa="
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {

				PreparedStatement insert = null;

				String sql = ""
						+ "UPDATE stockdepositoslockregalos"
						+ "     SET   fechabaja=current_timestamp, usuarioact=?, fechaact=current_timestamp"
						+ " WHERE idlock=? AND  idempresa=? AND fechabaja IS NULL ;";
				insert = dbconn.prepareStatement(sql);

				insert.setString(1, usuarioact);
				insert.setBigDecimal(2, idlock);
				insert.setBigDecimal(3, idempresa);

				int i = insert.executeUpdate();
				if (i == 1)
					salida = "Baja Correcta";
				else
					salida = "Error inesperado.";

			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : stockDepositosLockRegalosDelete( Long idlock, BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: stockDepositosLockRegalosDelete( Long idlock, BigDecimal idempresa )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String stockDepositosLockRegalosCreate(BigDecimal codigo_dt,
			java.sql.Date fechadesde, java.sql.Date fechahasta,
			String comentarios, BigDecimal idempresa, String usuarioalt)
			throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (codigo_dt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_dt ";
		if (fechadesde == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechadesde ";
		if (fechahasta == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechahasta ";
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
				String ins = "INSERT INTO STOCKDEPOSITOSLOCKREGALOS(codigo_dt, fechadesde, fechahasta, comentarios, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, codigo_dt);
				insert.setDate(2, fechadesde);
				insert.setDate(3, fechahasta);
				insert.setString(4, comentarios);
				insert.setBigDecimal(5, idempresa);
				insert.setString(6, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String stockDepositosLockRegalosCreate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String stockDepositosLockRegalosCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String stockDepositosLockRegalosCreateOrUpdate(BigDecimal idlock,
			BigDecimal codigo_dt, java.sql.Date fechadesde,
			java.sql.Date fechahasta, BigDecimal idempresa, String usuarioact)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idlock == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idlock ";
		if (codigo_dt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_dt ";
		if (fechadesde == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechadesde ";
		if (fechahasta == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechahasta ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM stockDepositosLockRegalos WHERE idlock = "
					+ idlock.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE STOCKDEPOSITOSLOCKREGALOS SET codigo_dt=?, fechadesde=?, fechahasta=?, idempresa=?, usuarioact=?, fechaact=? WHERE idlock=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, codigo_dt);
					insert.setDate(2, fechadesde);
					insert.setDate(3, fechahasta);
					insert.setBigDecimal(4, idempresa);
					insert.setString(5, usuarioact);
					insert.setTimestamp(6, fechaact);
					insert.setBigDecimal(7, idlock);
				} else {
					String ins = "INSERT INTO STOCKDEPOSITOSLOCKREGALOS(codigo_dt, fechadesde, fechahasta, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setBigDecimal(1, codigo_dt);
					insert.setDate(2, fechadesde);
					insert.setDate(3, fechahasta);
					insert.setBigDecimal(4, idempresa);
					insert.setString(5, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String stockDepositosLockRegalosCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String stockDepositosLockRegalosCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String stockDepositosLockRegalosUpdate(BigDecimal idlock,
			BigDecimal codigo_dt, java.sql.Date fechadesde,
			java.sql.Date fechahasta, String comentarios, BigDecimal idempresa,
			String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idlock == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idlock ";
		if (codigo_dt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: codigo_dt ";
		if (fechadesde == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechadesde ";
		if (fechahasta == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: fechahasta ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM stockDepositosLockRegalos WHERE idlock = "
					+ idlock.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE STOCKDEPOSITOSLOCKREGALOS SET codigo_dt=?, fechadesde=?, fechahasta=?, comentarios=?, idempresa=?, usuarioact=?, fechaact=? WHERE idlock=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, codigo_dt);
					insert.setDate(2, fechadesde);
					insert.setDate(3, fechahasta);
					insert.setString(4, comentarios);
					insert.setBigDecimal(5, idempresa);
					insert.setString(6, usuarioact);
					insert.setTimestamp(7, fechaact);
					insert.setBigDecimal(8, idlock);
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
					.error("Error SQL public String stockDepositosLockRegalosUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String stockDepositosLockRegalosUpdate(.....)"
							+ ex);
		}
		return salida;
	}

}
