package ar.com.syswarp.ejb;

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
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

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
public class BCBean implements BC {

	/** The session context */
	// private SessionContext context;
	GeneralBean gb = new GeneralBean();

	/* conexion a la base de datos */

	private Connection dbconn;

	static Logger log = Logger.getLogger(BCBean.class);

	// -- conexion al sistema core
	private Connection conexion;

	private Properties props;

	private String url;

	private String clase;

	private String usuario;

	private String clave;

	// -- conexion al sistema antiguo
	private Connection sconexion;

	private String surl;

	private String sclase;

	private String susuario;

	private String sclave;

	private Connection sdbconn;

	public BCBean() {
		super();
		try {
			props = new Properties();
			props.load(BCBean.class.getResourceAsStream("system.properties"));

			// conexion al sistema core
			url = props.getProperty("conn.url").trim();
			clase = props.getProperty("conn.clase").trim();
			usuario = props.getProperty("conn.usuario").trim();
			clave = props.getProperty("conn.clave").trim();
			Class.forName(clase);
			conexion = DriverManager.getConnection(url, usuario, clave);

			// conexion al sistema historico
			surl = props.getProperty("sconn.url").trim();
			sclase = props.getProperty("sconn.clase").trim();
			susuario = props.getProperty("sconn.usuario").trim();
			sclave = props.getProperty("sconn.clave").trim();
			Class.forName(sclase);
			sconexion = DriverManager.getConnection(surl, susuario, sclave);

			this.dbconn = conexion;
			this.sdbconn = sconexion;

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

	public BCBean(Connection dbconn) {
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

	public String holabean() {
		return "hola Bean BC";
	}

	/*
	 * parametros del sp @operacion Varchar(1)='A', -- ok, va por defecto I
	 * 
	 * @idMovPediCabe numeric(18) = NULL , se saca @idCampaCabe numeric(18) =
	 * NULL , -- por parametro @idSocio numeric(18) = NULL , -- por parametro
	 * 
	 * @fechapedi datetime = null , -- por parametro: Atencion ! STRING
	 * 
	 * @entregara varchar(50) = NULL , @idMesEntrega numeric(18) = NULL ,
	 * 
	 * @obsRemito varchar(100) = NULL , @obsCobranza varchar(100) = NULL ,
	 * 
	 * @obsProducto varchar(100) = NULL , @usuarioalt varchar(20) = NULL ,
	 * 
	 * @usuarioact varchar(20) = NULL, @idprovincia numeric(18) = null,
	 * 
	 * @idlocalidad numeric(18) = null, @totalbruto numeric(18,2)= null,
	 * 
	 * @adicdesc numeric(18,2)= null, @totalpedido numeric(18,2)= null,
	 * 
	 * @entregaren varchar(100) = NULL // datos del detalle operacion no va
	 * 
	 * @idMovPediDeta numeric(18) = NULL , -- deta no hace falta porque siempre
	 * inserto @idMovPediCabe numeric(18) = NULL , -- cabecera lo tengo de
	 * obtenerlo en la insercion de cabecera o bien viene como parametro
	 * 
	 * @idproducto varchar(20) = NULL , @cantidad numeric(18,2) = NULL ,
	 * 
	 * @usuarioalt varchar(20) = NULL , @usuarioact varchar(20) = NULL,
	 * 
	 * @descuento numeric(18,2) = null, @idMesEntrega numeric = null
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

	public String setPedidoTM(String operacion, BigDecimal idcampania,
			BigDecimal idsocio, String fechapedi, String entregara,
			BigDecimal idmesentrega, String obsremito, String obscobranza,
			String obsproducto, String usuarioalt, String usuarioact,
			BigDecimal idprovincia, BigDecimal idlocalidad,
			BigDecimal totalbruto, BigDecimal adicdesc, BigDecimal totalpedido,
			String entregaren, String idproductoD, BigDecimal cantidad,
			BigDecimal descuento, BigDecimal mesentrega) throws EJBException {
		String salida = "NOOK";
		String param = "";
		String paramD = "";
		boolean bError = false;
		try {
			param += operacion != null ? "'" + operacion + "'," : "NULL,";
			param += idcampania != null ? idcampania.toString() + "," : "NULL,";
			param += idsocio != null ? idsocio.toString() + "," : "NULL,";
			param += fechapedi != null ? "'" + fechapedi + "'," : "NULL,";
			param += entregara != null ? "'" + entregara + "'," : "NULL";
			param += idmesentrega != null ? "" + idmesentrega.toString() + ","
					: "NULL,";
			param += obsremito != null ? "'" + obsremito + "'," : "NULL,";
			param += obscobranza != null ? "'" + obscobranza + "'," : "NULL,";
			param += obsproducto != null ? "'" + obsproducto + "'," : "NULL,";
			param += usuarioalt != null ? "'" + usuarioalt + "'," : "NULL,";
			param += usuarioact != null ? "'" + usuarioact + "'," : "NULL,";
			param += idprovincia != null ? "" + idprovincia.toString() + ","
					: "NULL,";
			param += idlocalidad != null ? "" + idlocalidad.toString() + ","
					: "NULL,";
			param += totalbruto != null ? "" + totalbruto.toString() + ","
					: "NULL,";
			param += adicdesc != null ? "" + adicdesc.toString() + ","
					: "NULL,";
			param += totalpedido != null ? "" + totalpedido.toString() + ","
					: "NULL,";
			param += entregaren != null ? "'" + entregaren + "'" : "NULL,";
			BigDecimal curidcabecera = new BigDecimal(0);
			ResultSet rsCabecera = getTransaccion("sptmmovpedicabe", param);
			if (rsCabecera != null && rsCabecera.next()) {
				curidcabecera = rsCabecera.getBigDecimal("resultado");
			} else {
				bError = true;
			}
			if (!bError) {
				paramD += "'I',";
				paramD += "NULL,";
				paramD += curidcabecera.toString() + ",";
				paramD += idproductoD != null ? "'" + idproductoD + "',"
						: "NULL,";
				paramD += cantidad.toString() + ",";
				paramD += usuarioalt != null ? "'" + usuarioalt + "',"
						: "NULL,";
				paramD += usuarioact != null ? "'" + usuarioact + "',"
						: "NULL,";
				paramD += descuento != null ? descuento.toString() + ","
						: "NULL,";
				paramD += mesentrega != null ? mesentrega.toString() + ""
						: "NULL,";
				ResultSet rsDetalle = getTransaccion("sptmmovpedideta", paramD);
				if (rsDetalle != null && rsDetalle.next()) {
					salida = "OK";
				}
			}
		} catch (Exception ex) {
			log.error("ERROR: setPedidoCabeTM(..)");
			ex.printStackTrace();
		}

		return salida;
	}

	/*
	 * 
	 * cabecera : sp :sptmmovpedicabe parametros: "'I', NULL, "+idcampania+","+
	 * idsocio + ",null,operacion
	 * '"+entregara+"',"+idmesentrega+",'"+obsremito+"','"+obscobranza+"','"+obsproducto+"','"
	 * + usuario + "',NULL,"+idprovincia+","+idlocalidad+","+ totbruto + ","+
	 * descuento + "," + totgeneral+",'"+entregaren+"'";
	 * 
	 * detalle sp : sptmmovpedideta param = "'I', NULL, "+idPediCabe+",'"+
	 * rsFrmCampa.getString(2) + "',"+ var +",'" + usuario + "',null," +
	 * descuento + "," + idmesentrega ;
	 */

	// -- para ejecutar stored procedures: por si acaso es publico
	public ResultSet getTransaccion(String Sp, String Param)
			throws SQLException, EJBException {
		try {
			Statement statement = sdbconn.createStatement();
			statement.executeQuery("{call " + Sp + "(" + Param + ")}");
			return statement.getResultSet();
		} catch (SQLException sex) {
			log.error("falla_getTransaccion (SQL)" + " sp: " + Sp + " pr:"
					+ Param);
			sex.printStackTrace();
			return null;
		} catch (Exception ex) {
			log.error("falla_getTransaccion " + " sp: " + Sp + " pr:" + Param);
			ex.printStackTrace();
			return null;
		}
	}

	public static ResultSet getTransaccion(String Sp, String Param,
			Connection conn) throws SQLException, EJBException {
		try {
			Statement statement = conn.createStatement();
			statement.executeQuery("{call " + Sp + "(" + Param + ")}");
			return statement.getResultSet();
		} catch (SQLException sex) {
			log
					.error("(SQLE) getTransaccion (String Sp, String Param, Connection conn)"
							+ " sp: " + Sp + " pr:" + Param);
			sex.printStackTrace();
			return null;
		} catch (Exception ex) {
			log
					.error("(EX) getTransaccion (String Sp, String Param, Connection conn)"
							+ " sp: " + Sp + " pr:" + Param);
			ex.printStackTrace();
			return null;
		}
	}

	public BigDecimal getIdDepositoDelta(BigDecimal idUsuario) {
		/*
		 * Objetivo: Traer el numero de deposito de delta que se debe actualizar
		 * a partir de un id de usuario que viene del DB_BACO.
		 * 
		 * Funcionalidad: A partir del usuario logueado en las precormaciones
		 * saber que deposito del delta se debe tocar.
		 * 
		 * T.Trans: Mixta Autor: Carlos Enrique Perez Fecha: 28/08/2007 entidad
		 * : INTERFACEstockDEPOSITOS
		 */
		BigDecimal salida = new BigDecimal(0);
		try {
			ResultSet rsBACO = getTransaccion("spINTERFACEstockDEPOSITOSone",
					"" + idUsuario.toString());
			if (rsBACO != null) {
				while (rsBACO.next()) {
					salida = rsBACO.getBigDecimal("idDepositoDelta");
				}
			}
		} catch (Exception ex) {
			log.error("ERROR: getIdDepositoDelta(..)");
			ex.printStackTrace();
		}
		return salida;
	}

	public BigDecimal getIdDepositoDeltaXConjunto(BigDecimal cconjunto) {
		/*
		 * Objetivo: recuperar iddepositodelta a partir de un cconjunto BACO.
		 */
		BigDecimal salida = new BigDecimal(0);
		try {
			ResultSet rsBACO = getTransaccion(
					"spInterfacesDepositoDeltaConjuntoOne", ""
							+ cconjunto.toString());
			if (rsBACO != null) {
				while (rsBACO.next()) {
					salida = rsBACO.getBigDecimal("iddepositodelta");
				}
			}
		} catch (Exception ex) {
			log.error("ERROR: getIdDepositoDeltaXConjunto(..)");
			ex.printStackTrace();
		}
		return salida;
	}

	/**
	 * 20071108 - EJV - LOG DE TRANSACCIONES - GENERA TANTO PARA FINALIZACION
	 * EXITOSA COMO CON ERROR.
	 * 
	 */

	public String interFacesLogStatusCreate(BigDecimal idtransaccion,
			String descripcion, String metodobc, String status, String app,
			BigDecimal idempresa, String usuarioalt) throws EJBException {
		String salida = "OK";
		Connection connLog = null;
		// validaciones de datos:
		// 1. nulidad de campos
		if (idtransaccion == null)
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
						+ "INSERT INTO INTERFACESLOGSTATUS"
						+ "        (idtransaccion, descripcion, metodobc, status, app, idempresa, usuarioalt ) "
						+ " VALUES (?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = connLog.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, idtransaccion);
				insert.setString(2, descripcion);
				insert.setString(3, metodobc);
				insert.setString(4, status);
				insert.setString(5, app);
				insert.setBigDecimal(6, idempresa);
				insert.setString(7, usuarioalt);
				int n = insert.executeUpdate();
				if (n != 1)
					salida = "Error generando log interfaces ... ";

				connLog.close();

			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String interFacesLogStatusCreate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String interFacesLogStatusCreate(.....)"
							+ ex);
		}

		connLog = null;
		return salida;

	}

	public static String interFacesLogStatusCreateStatic(
			BigDecimal idtransaccion, String descripcion, String metodobc,
			String status, String app, BigDecimal idempresa, String usuarioalt,
			Connection pgconn) throws EJBException {
		String salida = "OK";
		// Connection connLog = null;
		// validaciones de datos:
		// 1. nulidad de campos
		if (idtransaccion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtransaccion ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
		// 2. sin nada desde la pagina

		// fin validaciones

		try {

			// Properties props = new Properties();
			// props.load(BCBean.class.getResourceAsStream("system.properties"));
			// // conexion al sistema core
			// String url = props.getProperty("conn.url").trim();
			// String clase = props.getProperty("conn.clase").trim();
			// String usuario = props.getProperty("conn.usuario").trim();
			// String clave = props.getProperty("conn.clave").trim();
			// Class.forName(clase);

			// Es necesario crear una coneccion independiente, para manejar
			// transaccion tambien independiente. La misma se crea cada vez que
			// es llamado el metodo que genera datos de log.
			// connLog = DriverManager.getConnection(url, usuario, clave);
			if (salida.equalsIgnoreCase("OK")) {
				String ins = ""
						+ "INSERT INTO INTERFACESLOGSTATUS"
						+ "        (idtransaccion, descripcion, metodobc, status, app, idempresa, usuarioalt ) "
						+ " VALUES (?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = pgconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, idtransaccion);
				insert.setString(2, descripcion);
				insert.setString(3, metodobc);
				insert.setString(4, status);
				insert.setString(5, app);
				insert.setBigDecimal(6, idempresa);
				insert.setString(7, usuarioalt);
				int n = insert.executeUpdate();
				if (n != 1)
					salida = "Error generando log interfaces ... ";

				// pgconn.close();

			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String interFacesLogStatusCreateStatic(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String interFacesLogStatusCreateStatic(.....)"
							+ ex);
		}

		// connLog = null;
		return salida;

	}

	/**
	 * --INTERFAZ de stock DELTA con db_baco: PRECONFORMACION DE REMITOS ......
	 * --27/08/2007 ...........................................................
	 */

	public String[] InterfaseDbBacoDeltaMovSalida(String conjunto,
			String remito, String idestado, String confirma,
			BigDecimal idempresa, String usuarioalt) throws EJBException,
			SQLException {
		String[] resultado = new String[3];
		String[] resultadoDelta = new String[2];
		String salida = "OK";
		String param = "";
		String conformadoRechazado = "";
		BigDecimal idtransaccion = null;
		// Inicializo en OK

		try {

			sdbconn.setAutoCommit(false);
			dbconn.setAutoCommit(false);

			log.info("");
			log.info("INICIA:  InterfaseDbBacoDeltaMovSalida");
			ResultSet rsInterface = null;
			ResultSet rsConuntoDepositoDelta = null;
			// BigDecimal idusuariobaco = new BigDecimal(-1);
			BigDecimal iddepositodelta = new BigDecimal(-1);

			BigDecimal codigo_anexo = null;
			String razon_social = null;
			String domicilio = null;
			String codigo_postal = null;
			BigDecimal idlocalidad = null;
			BigDecimal idprovincia = null;
			String cuit = null;
			String iibb = null;
			Timestamp fechamov = null;
			BigDecimal remito_ms = null;
			String tipomov = null;
			BigDecimal sucursal = null;
			String tipo = null;
			boolean remitopendiente = true;
			String observaciones = "";
			int estadoWapAnterior = -1;

			String codigo_st = "";
			Hashtable htArticulos = new Hashtable();
			Hashtable htCuentas = null;

			int ejercicioActivo = 0;

			Statement statment = sdbconn.createStatement();

			String cQuery = "";

			idtransaccion = GeneralBean.getNextValorSequencia(
					"seq_loginterfaces", dbconn);

			// Por default siempre es invalido,
			// solamente se setea en false cuando el remito ejecuta una
			// transaccion exitosa, y mueve stock
			boolean isInvalidoBaco = true;

			String tmov = idestado.equals("1") ? " OUT (WAP ESTADO 1 - Entrega) "
					: idestado.equals("5") ? " IN (WAP ESTADO 5 - Limpieza) "
							: "* WAP ESTADO = " + idestado + " *";

			// Recupera idusuario para el conjuto seleccionado

			param = conjunto + ", " + remito + ", " + idestado + ", "
					+ confirma;

			log.info("PARAM: " + param);
			rsInterface = getTransaccion("spValidarRemitoExpresoWAPint", param);

			if (rsInterface != null && rsInterface.next()) {

				resultado[0] = "" + rsInterface.getString(2);
				isInvalidoBaco = rsInterface.getDouble(1) < 1 ? false : true;
				conformadoRechazado = rsInterface.getString(3);
				estadoWapAnterior = rsInterface.getInt(4);

			} else {
				salida = resultado[0] = "BACO: Imposible Validar Remito Expreso WAP.";

				interFacesLogStatusCreate(idtransaccion,
						"spValidarRemitoExpresoWAPint , " + param,
						"InterfaseDbBacoDeltaMovSalida", "ERROR", "",
						idempresa, usuarioalt);
			}

			log.info("isInvalidoBaco:" + isInvalidoBaco);
			log.info("conformadoRechazado:" + conformadoRechazado);

			// 20091005 - EJV
			// if (!isInvalidoBaco && (confirma != null && confirma.equals("1"))
			// && (idestado.equals("1") || idestado.equals("5"))) {// 0

			if (!isInvalidoBaco && (confirma != null && confirma.equals("1"))
					&& (!idestado.equals("4"))) {// 0

				// Recupera datos de cliente
				cQuery = "SELECT TOP 1 ing.csocio, ing.xapellido, ing.xnombre, dbo.MySocioDomicilioEntrega(ing.csocio) AS domicilio,"
						+ "            dbo.MySocioCodigoPostalEntrega(ing.csocio) AS codpostal, dbo.MySocioLocalidadCodigoEntrega(ing.csocio) AS idlocalidad,"
						+ "            ing.cprovincia, ing.xcuit, '' AS iibb, getDate()AS fechamov, dv.xnremito AS remito_ms, '' AS tipomov, 0 AS sucursal,'' AS tipo   "
						+ "       FROM ingreso_socios ing "
						+ "            INNER JOIN dv dv ON ing.csocio = dv.csocio"
						+ "      WHERE dv.xnremito = '" + remito + "'";

				rsInterface = statment.executeQuery(cQuery);
				if (rsInterface != null && rsInterface.next()) {// 30
					log.info("[INTFS(B --> D)  " + tmov + "] 30");
					codigo_anexo = rsInterface.getBigDecimal("csocio");
					razon_social = rsInterface.getString("xapellido") + " "
							+ rsInterface.getString("xnombre");
					domicilio = rsInterface.getString("domicilio");
					codigo_postal = rsInterface.getString("codpostal");
					idlocalidad = rsInterface.getBigDecimal("idlocalidad");
					idprovincia = rsInterface.getBigDecimal("cprovincia");
					cuit = rsInterface.getString("xcuit");
					iibb = rsInterface.getString("iibb");
					fechamov = rsInterface.getTimestamp("fechamov");
					remito_ms = rsInterface.getBigDecimal("remito_ms");
					tipomov = rsInterface.getString("tipomov");
					sucursal = rsInterface.getBigDecimal("sucursal");
					tipo = rsInterface.getString("tipo");
					ejercicioActivo = getEjercicioActivo(idempresa);

					observaciones = "INTERFAZ-BACO: remito " + remito;
					/*
					 * 20071712 EJV -- Agrupar por producto, Delta no permite
					 * mismo producto/deposito para un mismo remito.
					 * 
					 * cQuery = "" + "SELECT p.xiom, dv.xcantidad, dv.cexpreso,
					 * c.id_usuario, dv.ccodigo " + " FROM dv dv " + " INNER
					 * JOIN vproductos p ON dv.xiom = p.tipo AND dv.ciom =
					 * p.ciom " + " INNER JOIN abm_conjuntos_distribuidores cd
					 * ON dv.cexpreso = cd.cexpreso " + " INNER JOIN
					 * abm_conjuntos c ON cd.cconjunto = c.ccodigo " + " WHERE
					 * dv.xnremito = '" + remito + "'";
					 */
					cQuery = ""
							+ "SELECT p.xiom, SUM(dv.xcantidad) AS xcantidad, dv.cexpreso, c.id_usuario  "
							+ "  FROM dv dv "
							+ "       INNER JOIN vproductos p ON dv.xiom = p.tipo AND dv.ciom = p.ciom "
							+ "       INNER JOIN abm_conjuntos_distribuidores cd ON dv.cexpreso = cd.cexpreso "
							+ "       INNER JOIN abm_conjuntos c ON cd.cconjunto = c.ccodigo "
							+ " WHERE dv.xnremito = '" + remito + "'"
							+ " GROUP BY p.xiom, dv.cexpreso, c.id_usuario ";

					rsInterface = statment.executeQuery(cQuery);
					if (rsInterface != null) {// 40
						log.info("[INTFS(B --> D)  " + tmov + "] 40");
						while (rsInterface.next()) {// 50
							log.info("[INTFS(B --> D)  " + tmov + "] 50");
							codigo_st = rsInterface.getString("xiom");

							rsConuntoDepositoDelta = getTransaccion(
									"spInterfacesDepositoDeltaConjuntoOne",
									conjunto.toString());

							if (rsConuntoDepositoDelta != null
									&& rsConuntoDepositoDelta.next()) {// 60
								log.info("[INTFS(B --> D)  " + tmov + "] 60");
								iddepositodelta = rsConuntoDepositoDelta
										.getBigDecimal("iddepositodelta");
								// Datos de articulo delta ...
								List listArt = getProveedoArticulosPK(
										codigo_st, idempresa);

								if (!listArt.isEmpty()) {// 70
									log.info("[INTFS(B --> D)  " + tmov
											+ "] 70");
									String[] datos = (String[]) listArt.get(0);

									datos[9] = iddepositodelta.toString();
									datos[10] = rsInterface
											.getString("xcantidad");
									datos[11] = gb.getNumeroFormateado((Float
											.parseFloat(datos[10]) * Float
											.parseFloat(datos[5])), 10, 2);

									log.info("======================");
									for (int R = 0; R < datos.length; R++) {
										log.info("Posicion " + R + ": "
												+ datos[R]);
									}
									log.info("**********************");
									/*
									 * EJV 20071217 htArticulos.put(rsInterface
									 * .getString("ccodigo"), datos);
									 */
									htArticulos.put(rsInterface
											.getString("xiom"), datos);

								}// 70
								else {
									salida += "ERROR[70]: Imposible recuperar datos del articulo DELTA."
											+ codigo_st;
								}// 70
							}// 60
							else {
								// TODO: OJO 20 y 60 repiten busqueda
								salida += "ERROR[60]: Imposible recuperar deposito DELTA para conjunto BACO.";
							}// 60
						}// 50
					}// 40
					else {
						salida = "ERROR[40]: Imposible recuperar datos remito BACO.";
					}// 40
				}// 30
				else {
					salida = "ERROR[30]: Imposible recuperar datos del cliente BACO.";
				}// 30

				/*
				 * FINALIZA MOVIMIENTO BACO
				 * 
				 * INICIA MOVIMIENTO DELTA
				 */

				log.info("ANTES DE INICIAR TRANASCCION DELTA: " + salida);

				if (salida.equalsIgnoreCase("OK")) {// 100
					// MANTIS 402: interfases despachos

					log.info("idestado: " + idestado);

					if (idestado.equals("1")) {

						log.info("CALL { stockMovSalidaWAPEntregadoCreate } ");
						resultadoDelta = stockMovSalidaWAPEntregadoCreate(
								codigo_anexo, razon_social, domicilio,
								codigo_postal, idlocalidad, idprovincia, cuit,
								iibb, fechamov, remito_ms, tipomov, sucursal,
								tipo, remitopendiente, ejercicioActivo,
								observaciones, htArticulos, htCuentas,
								usuarioalt, idempresa);

						salida = resultadoDelta[0];

						resultado[1] = "D-S: " + resultadoDelta[0];
						resultado[2] = "D-S: " + resultadoDelta[1];
						// MANTIS 402: interfases despachos
					} else if (idestado.equals("2") || idestado.equals("3")) {

						log
								.info("CALL { stockMovStockRes2DispWAPRechazoCambioCreate }");
						resultadoDelta = stockMovStockRes2DispWAPRechazoCambioCreate(
								htArticulos, usuarioalt, idempresa);

						salida = resultadoDelta[0];

						resultado[1] = "D-S: " + resultadoDelta[0];
						resultado[2] = "D-S: " + resultadoDelta[1];
						// MANTIS 402: interfases despachos
					} else if (idestado.equals("5")
							&& conformadoRechazado.equalsIgnoreCase("S")
							&& estadoWapAnterior == 1) {
						// PREGUNTAR POR WAP ESTADO ANTERIOR ES REDUNDANTE, ES
						// DOBLE CONTROL. BASTA CON PREGUNTAR SOLO POR
						// CONFORMADO/RECHAZADO.
						log
								.info("CALL { stockMovEntradaWAPLimpiaEntregaCreate }");
						resultadoDelta = stockMovEntradaWAPLimpiaEntregaCreate(
								codigo_anexo, razon_social, domicilio,
								codigo_postal, idlocalidad, idprovincia, cuit,
								iibb, fechamov, remito_ms, tipomov, sucursal,
								tipo, remitopendiente, ejercicioActivo,
								observaciones, htArticulos, htCuentas,
								usuarioalt, idempresa);

						salida = resultadoDelta[0];

						resultado[1] = "D-E: " + resultadoDelta[0];
						resultado[2] = "D-E: " + resultadoDelta[1];

						// MANTIS 402: interfases despachos
					} else if (idestado.equals("5")
							&& conformadoRechazado.equalsIgnoreCase("N")
							&& (estadoWapAnterior == 2 || estadoWapAnterior == 3)) {

						// CONFORMADO/RECHAZADO.
						log
								.info("CALL { stockMovEntradaWAPLimpiaRechazoCambioCreate }");
						resultadoDelta = stockMovEntradaWAPLimpiaRechazoCambioCreate(
								htArticulos, usuarioalt, idempresa);

						salida = resultadoDelta[0];

						resultado[1] = "D-E: " + resultadoDelta[0];
						resultado[2] = "D-E: " + resultadoDelta[1];

					} else {
						resultadoDelta[0] = salida;
						resultadoDelta[1] = " * --- ESTADO PRECONFORMACION NO AFECTA STOCK -- * ";
					}

					if (salida == null)
						salida = "";

					interFacesLogStatusCreate(idtransaccion, param + " / TIPO "
							+ tmov + " / " + resultadoDelta[0] + " - "
							+ resultadoDelta[1],
							"InterfaseDbBacoDeltaMovSalida", !salida
									.equalsIgnoreCase("OK") ? "ERROR" : "OK",
							"", idempresa, usuarioalt);

				} else {// 100

					resultado[1] = "DELTA 100: No se ejecut� transacci�n. ";
					interFacesLogStatusCreate(idtransaccion, param + " / "
							+ salida, "InterfaseDbBacoDeltaMovSalida", "ERROR",
							"", idempresa, usuarioalt);

				}// 100

			}// 0
			else {

				interFacesLogStatusCreate(idtransaccion, param + " / " + tmov
						+ "/ ", "InterfaseDbBacoDeltaMovSalida", "INFO", "",
						idempresa, usuarioalt);
			}// 0

		} catch (Exception e) {

			salida = "Excepci�n en INTERFAZ BACO - DELTA.";
			log.error("InterfaseDbBacoDeltaMovSalida: " + e);
			interFacesLogStatusCreate(idtransaccion, param + " / " + salida
					+ " { " + e + " }", "InterfaseDbBacoDeltaMovSalida",
					"ERROR", "", idempresa, usuarioalt);
		}

		if (!salida.equalsIgnoreCase("OK")) {
			sdbconn.rollback();
			dbconn.rollback();
		} else {
			sdbconn.commit();
			dbconn.commit();
		}

		log.info("FINALIZA:  InterfaseDbBacoDeltaMovSalida");
		log.info("");

		sdbconn.setAutoCommit(true);
		dbconn.setAutoCommit(true);

		return resultado;
	}

	/**
	 * --INTERFAZ de stock DELTA con db_baco: DESPACHO PARA SOCIOS..............
	 * --04/10/2007.............................................................
	 */

	public String[] InterfaseDbBacoDeltaDespachoSocios(String conjuntoOrigen,
			String conjuntoDestino, Hashtable htSeleccion,
			String observaciones, String usuarioalt, BigDecimal idempresa)
			throws EJBException, SQLException {
		String[] resultado = new String[3];
		String[] resultadoDelta = new String[2];
		String salida = "OK";

		Timestamp fechamov = new Timestamp(Calendar.getInstance()
				.getTimeInMillis());
		String tipomovs_ms = "S";
		BigDecimal sucursal = new BigDecimal(0);
		Hashtable htArticulos = new Hashtable();
		String ciom = "";
		String xiom = "";
		String codigo_st = "";

		String origenDelta = "";
		String destinoDelta = "";

		BigDecimal idtransaccion = null;

		ResultSet rsBaco = null;
		log.info("INICIA InterfaseDbBacoDeltaDespachoSocios");
		try {

			sdbconn.setAutoCommit(false);
			dbconn.setAutoCommit(false);

			/*
			 * stockMovInternoCreate(Timestamp fechamov, String tipomov,
			 * BigDecimal num_cnt, BigDecimal sucursal, String tipo, String
			 * observaciones, Hashtable htArticulos, String usuarioalt,
			 * BigDecimal idempresa)
			 */
			idtransaccion = GeneralBean.getNextValorSequencia(
					"seq_loginterfaces", dbconn);

			rsBaco = getTransaccion("spInterfacesDepositoDeltaConjuntoOne",
					conjuntoOrigen);

			if (rsBaco != null && rsBaco.next()) {

				log.info("PASO 1 ");

				origenDelta = rsBaco.getString("iddepositodelta");

				rsBaco = getTransaccion("spInterfacesDepositoDeltaConjuntoOne",
						conjuntoDestino);

				if (rsBaco != null && rsBaco.next()) {

					log.info("PASO 2");

					destinoDelta = rsBaco.getString("iddepositodelta");

					Enumeration en = htSeleccion.keys();

					while (en.hasMoreElements()) {

						String clave = en.nextElement().toString();

						log.info("PASO 3:  ");

						String[] im_xiom_ciom = clave.split("#");

						xiom = im_xiom_ciom[0];
						ciom = im_xiom_ciom[1];
						codigo_st = im_xiom_ciom[2];

						log.info("xiom:" + xiom);
						log.info("ciom:" + ciom);
						log.info("codigo_st:" + codigo_st);

						List listaArt = getStockArticulosPK(codigo_st,
								idempresa);
						Iterator iterArt = listaArt.iterator();
						if (iterArt.hasNext()) {

							while (iterArt.hasNext()) {
								log.info("PASO 4 ");
								String[] datosArt = (String[]) iterArt.next();
								String cantidad = htSeleccion.get(clave)
										.toString();

								datosArt[4] = htSeleccion.get(clave).toString();
								datosArt[5] = origenDelta;
								datosArt[6] = destinoDelta;

								String param = conjuntoOrigen + ", "
										+ conjuntoDestino + ", '" + xiom
										+ "', " + ciom + ", " + cantidad;
								log.info("PARAM: " + param);

								rsBaco = getTransaccion(
										"spInterfecesDespachoSocioStockConjunto",
										param);

								if (rsBaco == null || !rsBaco.next()) {
									salida += " [FALLO: " + codigo_st + "]";
								}

								htArticulos.put(codigo_st, datosArt);

							}

						} else {
							salida = "Error: NO EXISTE  " + codigo_st
									+ " en DELTA.";
						}

					}

				} else
					salida = "Error al recuperar equivalencia Delta para conjunto destino:"
							+ conjuntoDestino;

			} else
				salida = "Error al recuperar equivalencia Delta para conjunto origen:"
						+ conjuntoOrigen;

			String status = salida == null || !salida.equalsIgnoreCase("OK") ? "ERROR"
					: salida;

			interFacesLogStatusCreate(idtransaccion, "BACO: " + salida
					+ "/ Conjunto Origen: " + conjuntoOrigen
					+ "/ Conjunto Destino: " + conjuntoDestino,
					"InterfaseDbBacoDeltaDespachoSocios", status, "",
					idempresa, usuarioalt);

			if (salida.equalsIgnoreCase("OK")) {

				resultadoDelta = stockMovInternoCreateDespachosSociosYAndreani(
						fechamov, tipomovs_ms, new BigDecimal(0), sucursal,
						null, "INTF-DESP-SOCIOS.", htArticulos, usuarioalt,
						idempresa);

				salida = resultadoDelta[0] == null ? "ERROR"
						: resultadoDelta[0];

				status = !salida.equalsIgnoreCase("OK") ? "ERROR"
						: resultadoDelta[0];

				interFacesLogStatusCreate(idtransaccion, "DELTA: "
						+ resultadoDelta[0] + " / " + resultadoDelta[1]
						+ "/ Conjunto Origen: " + conjuntoOrigen
						+ "/ Conjunto Destino: " + conjuntoDestino,
						"InterfaseDbBacoDeltaDespachoSocios", status, "",
						idempresa, usuarioalt);

			}

		} catch (Exception e) {

			salida = "Excepcion al realizar transaccion.";
			log.error("InterfaseDbBacoDeltaDespachoSocios" + e);
			interFacesLogStatusCreate(idtransaccion, "EXCEPTION: " + e
					+ "/ Conjunto Origen: " + conjuntoOrigen
					+ "/ Conjunto Destino: " + conjuntoDestino,
					"InterfaseDbBacoDeltaDespachoSocios", "ERROR", "",
					idempresa, usuarioalt);
		}

		if (!salida.equalsIgnoreCase("OK")) {
			sdbconn.rollback();
			dbconn.rollback();
		} else {
			sdbconn.commit();
			dbconn.commit();
		}

		sdbconn.setAutoCommit(true);
		dbconn.setAutoCommit(true);

		resultado[0] = "BACO : " + salida;
		resultado[1] = "DELTA: " + resultadoDelta[0];
		resultado[2] = "DELTA: " + resultadoDelta[1];

		log.info("FINAL InterfaseDbBacoDeltaDespachoSocios");
		return resultado;

	}

	/**
	 * --INTERFAZ de stock DELTA con db_baco: DESPACHO PARA STOCK...............
	 * --05/10/2007.............................................................
	 */

	public String[] InterfaseDbBacoDeltaDespachoStock(Timestamp fechamov,
			String tipomov, BigDecimal num_cnt, BigDecimal sucursal,
			String tipo, String observaciones, Hashtable htArticulos,
			String usuarioalt, BigDecimal idempresa) throws EJBException,
			SQLException {

		String[] resultado = new String[3];
		String[] resultadoDelta = new String[2];
		String salida = "OK";
		String status = "OK";
		String error = "";
		Enumeration en;
		String conjuntoOrigen = "";
		String conjuntoDestino = "";
		String tipoim = "";
		String ciom = "";
		BigDecimal idtransaccion = null;
		String resultadoBaco = "";

		ResultSet rsBaco = null;
		String param = "";
		log.info("INICIA: InterfaseDbBacoDeltaDespachoStock");
		try {

			sdbconn.setAutoCommit(false);
			dbconn.setAutoCommit(false);

			idtransaccion = GeneralBean.getNextValorSequencia(
					"seq_loginterfaces", dbconn);

			en = htArticulos.keys();

			while (en.hasMoreElements()) {
				String clave = en.nextElement().toString();
				String[] datos = (String[]) htArticulos.get(clave);

				String xiom = datos[0];
				String cantidad = datos[4];
				String origenDelta = datos[5];
				String destinoDelta = datos[6];

				// datos[0] = codigo_st
				// datos[1] = alias_st
				// datos[2] = descrip_st
				// datos[3] = descri2_st
				// datos[4] = this.cantidad[i];
				// datos[5] = this.origen[j];
				// datos[6] = this.destino[j];
				// datos[7] = cost_uc_st

				rsBaco = getTransaccion("spInterfacesConjuntoDepositoDeltaOne",
						origenDelta);
				if (rsBaco != null && rsBaco.next()) {
					conjuntoOrigen = rsBaco.getString("cconjuntobaco");

					rsBaco = getTransaccion(
							"spInterfacesConjuntoDepositoDeltaOne",
							destinoDelta);
					if (rsBaco != null && rsBaco.next()) {
						conjuntoDestino = rsBaco.getString("cconjuntobaco");

						rsBaco = getTransaccion(
								"spInterfacesVProductosXiomOne ", "'" + xiom
										+ "'");
						if (rsBaco != null && rsBaco.next()) {

							tipoim = rsBaco.getString("tipo");
							ciom = rsBaco.getString("ciom");

							param = conjuntoOrigen + ", " + conjuntoDestino
									+ ",'" + tipoim + "', " + ciom + ", "
									+ cantidad;

							log.info("PARAM:" + param);
							rsBaco = getTransaccion(
									"spInterfecesDespachoStockConjunto", param);

							if (rsBaco != null && rsBaco.next()) {

								resultadoBaco += "(" + rsBaco.getString(1)
										+ "-" + rsBaco.getString(2) + ")";

							} else {
								salida += "[E4: " + xiom + ciom + "]";
								error += "spInterfecesDespachoStockConjunto "
										+ param + " / Error al actualizar: "
										+ xiom + " - " + ciom;
								log.warn(error);
								status = "ERROR";

							}

						} else {
							salida += "[E3: " + xiom + " - " + ciom + "]";
							error += "Error al recuperar datos articulo baco: "
									+ xiom + " - " + ciom;
							log.warn(error);
							status = "ERROR";
						}

					} else {
						salida += "[E2: " + destinoDelta + " - " + xiom + " - "
								+ ciom + "]";
						error += "Error al recuperar equivalente conjunto baco destino para deposito delta: "
								+ destinoDelta + " - " + xiom;
						log.warn(error);
						status = "ERROR";
					}

				} else {
					salida += "[E1: " + origenDelta + " - " + xiom + " - "
							+ ciom + "]";
					error += "Error al recuperar equivalente conjunto baco origen para deposito delta:"
							+ origenDelta + " - " + xiom;
					log.warn(error);
					status = "ERROR";
				}

			}

			interFacesLogStatusCreate(idtransaccion, "BACO / " + error,
					"InterfaseDbBacoDeltaDespachoStock", status, "", idempresa,
					usuarioalt);

			if (salida.equalsIgnoreCase("OK")) {

				resultadoDelta = stockMovInternoCreate(fechamov, tipomov,
						num_cnt, sucursal, tipo, "INTF-DESP-STOCK.",
						htArticulos, usuarioalt, idempresa);

				salida = resultadoDelta[0] == null ? "ERROR"
						: resultadoDelta[0];

				status = !salida.equalsIgnoreCase("OK") ? "ERROR" : "OK";

				interFacesLogStatusCreate(idtransaccion, "DELTA / "
						+ resultadoDelta[0] + " - " + resultadoDelta[1],
						"InterfaseDbBacoDeltaDespachoStock", status, "",
						idempresa, usuarioalt);
			}

		} catch (Exception e) {
			salida = "E0: BACO-DELTA:";
			interFacesLogStatusCreate(idtransaccion, "EXCEPTION / " + e,
					"InterfaseDbBacoDeltaDespachoStock", "ERROR", "app",
					idempresa, usuarioalt);
			log.error("InterfaseDbBacoDeltaDespachoStock(..):" + e);
		}

		if (!salida.equalsIgnoreCase("OK")) {
			resultadoBaco = salida;
			sdbconn.rollback();
			dbconn.rollback();
		} else {
			sdbconn.commit();
			dbconn.commit();
		}

		resultado[0] = "BACO:" + resultadoBaco;
		resultado[1] = "DELTA:" + resultadoDelta[0];
		resultado[2] = "DELTA:" + resultadoDelta[1];

		sdbconn.setAutoCommit(true);
		dbconn.setAutoCommit(true);

		log.info("FINAL: InterfaseDbBacoDeltaDespachoStock");
		return resultado;
	}

	/**
	 * --INTERFAZ de stock DELTA con db_baco: DESPACHO PARA ANDREANI............
	 * --03/06/2008.............................................................
	 */

	public String[] InterfaseDbBacoDeltaDespachoAndreani(String conjuntoOrigen,
			String conjuntoDestino, String hrDesde, String hrHasta,
			Hashtable htSeleccion, String observaciones, String usuarioalt,
			BigDecimal idempresa) throws EJBException, SQLException {
		String[] resultado = new String[3];
		String[] resultadoDelta = new String[2];
		String salida = "OK";

		Timestamp fechamov = new Timestamp(Calendar.getInstance()
				.getTimeInMillis());
		String tipomovs_ms = "S";
		BigDecimal sucursal = new BigDecimal(0);
		Hashtable htArticulos = new Hashtable();
		String ciom = "";
		String xiom = "";
		String codigo_st = "";

		String origenDelta = "";
		String destinoDelta = "";

		BigDecimal idtransaccion = null;
		boolean impactaranexodv = false;

		ResultSet rsBaco = null;
		log.info("INICIA InterfaseDbBacoDeltaDespachoAndreani ---****----");
		try {

			sdbconn.setAutoCommit(false);
			dbconn.setAutoCommit(false);

			/*
			 * stockMovInternoCreate(Timestamp fechamov, String tipomov,
			 * BigDecimal num_cnt, BigDecimal sucursal, String tipo, String
			 * observaciones, Hashtable htArticulos, String usuarioalt,
			 * BigDecimal idempresa)
			 */
			idtransaccion = GeneralBean.getNextValorSequencia(
					"seq_loginterfaces", dbconn);

			rsBaco = getTransaccion("spInterfacesDepositoDeltaConjuntoOne",
					conjuntoOrigen);

			if (rsBaco != null && rsBaco.next()) {

				log.info("PASO 1 ");

				origenDelta = rsBaco.getString("iddepositodelta");

				rsBaco = getTransaccion("spInterfacesDepositoDeltaConjuntoOne",
						conjuntoDestino);

				if (rsBaco != null && rsBaco.next()) {

					log.info("PASO 2");

					destinoDelta = rsBaco.getString("iddepositodelta");

					Enumeration en = htSeleccion.keys();

					while (en.hasMoreElements()) {

						String clave = en.nextElement().toString();

						log.info("PASO 3:  ");

						String[] im_xiom_ciom = clave.split("#");

						xiom = im_xiom_ciom[0];
						ciom = im_xiom_ciom[1];
						codigo_st = im_xiom_ciom[2];

						log.info("xiom:" + xiom);
						log.info("ciom:" + ciom);
						log.info("codigo_st:" + codigo_st);

						List listaArt = getStockArticulosPK(codigo_st,
								idempresa);
						Iterator iterArt = listaArt.iterator();
						if (iterArt.hasNext()) {

							impactaranexodv = true;

							while (iterArt.hasNext()) {
								log.info("PASO 4 ");
								String[] datosArt = (String[]) iterArt.next();
								String cantidad = htSeleccion.get(clave)
										.toString();

								datosArt[4] = htSeleccion.get(clave).toString();
								datosArt[5] = origenDelta;
								datosArt[6] = destinoDelta;

								String param = conjuntoOrigen + ", "
										+ conjuntoDestino + ", '" + xiom
										+ "', " + ciom + ", " + cantidad;
								log.info("PARAM: " + param);

								rsBaco = getTransaccion(
										"spInterfecesDespachoSocioStockConjunto",
										param);

								if (rsBaco == null || !rsBaco.next()) {

									if (salida.equalsIgnoreCase("OK"))
										salida = "";
									salida += " [FALLO(1): " + codigo_st + "]";

								}

								// spInterfacesAnexoDvPuntoControl 28, 2000,
								// 4000, 39, 866, 'I'

								/**
								 * 
								 * 20080630 - POR DEFINICION INCOMPLETA ... Se
								 * actualizaba remitos por producto en anexo dv
								 * .. Mal ... se debe hacer por Hoja de ruta
								 * 
								 * 
								 * param = conjuntoDestino + ", " + hrDesde +
								 * ", " + hrHasta + ", " + cantidad + ", '" +
								 * xiom + "'," + ciom ;
								 * 
								 * log.info("spInterfacesAnexoDvPuntoControl " +
								 * param);
								 * 
								 * rsBaco = getTransaccion(
								 * "spInterfacesAnexoDvPuntoControl", param);
								 * 
								 * if (rsBaco != null && rsBaco.next()) { if
								 * (!rsBaco.getString(1).equalsIgnoreCase(
								 * "OK")) salida += " [FALLO(2): " +
								 * rsBaco.getString(1) + " - " + codigo_st +
								 * "]"; } else salida += " [FALLO(3): " +
								 * codigo_st + "]";
								 * 
								 */

								htArticulos.put(codigo_st, datosArt);

							}

						} else {
							salida += "Error: NO EXISTE  " + codigo_st
									+ " en DELTA.";
						}

					}

					if (salida.equalsIgnoreCase("OK") && impactaranexodv) {
						String param = hrDesde + ", " + hrHasta;
						rsBaco = getTransaccion(
								"spInterfacesAnexoDvPuntoControl", param);

						if (rsBaco != null && rsBaco.next()) {
							if (!rsBaco.getString(1).equalsIgnoreCase("OK"))
								salida += " [ FALLO ACTUALIZACION ANEXODV ]: "
										+ param + " / " + rsBaco.getString(1);
						}

					}

				} else
					salida = "Error al recuperar equivalencia Delta para conjunto destino:"
							+ conjuntoDestino;

			} else
				salida = "Error al recuperar equivalencia Delta para conjunto origen:"
						+ conjuntoOrigen;

			String status = salida == null || !salida.equalsIgnoreCase("OK") ? "ERROR"
					: salida;

			interFacesLogStatusCreate(idtransaccion, "BACO: " + salida
					+ "/ Conjunto Origen: " + conjuntoOrigen
					+ "/ Conjunto Destino: " + conjuntoDestino,
					"InterfaseDbBacoDeltaDespachoAndreani", status, "",
					idempresa, usuarioalt);

			if (salida.equalsIgnoreCase("OK")) {

				resultadoDelta = stockMovInternoCreateDespachosSociosYAndreani(
						fechamov, tipomovs_ms, new BigDecimal(0), sucursal,
						null, "INTF-DESP-ANDREANI.", htArticulos, usuarioalt,
						idempresa);

				salida = resultadoDelta[0] == null ? "ERROR"
						: resultadoDelta[0];

				status = !salida.equalsIgnoreCase("OK") ? "ERROR"
						: resultadoDelta[0];

				interFacesLogStatusCreate(idtransaccion,
						"DELTA: resultadoDelta[0] " + resultadoDelta[0]
								+ " / resultadoDelta[1] " + resultadoDelta[1]
								+ "/ Conjunto Origen: " + conjuntoOrigen
								+ "/ Conjunto Destino: " + conjuntoDestino,
						"InterfaseDbBacoDeltaDespachoAndreani", status, "",
						idempresa, usuarioalt);

			}

		} catch (Exception e) {

			salida = "Excepcion al realizar transaccion.";
			log.error("InterfaseDbBacoDeltaDespachoAndreani" + e);
			interFacesLogStatusCreate(idtransaccion, "EXCEPTION: " + e
					+ "/ Conjunto Origen: " + conjuntoOrigen
					+ "/ Conjunto Destino: " + conjuntoDestino,
					"InterfaseDbBacoDeltaDespachoAndreani", "ERROR", "",
					idempresa, usuarioalt);
		}

		if (!salida.equalsIgnoreCase("OK")) {

			sdbconn.rollback();
			dbconn.rollback();
		} else {

			sdbconn.commit();
			dbconn.commit();

		}

		sdbconn.setAutoCommit(true);
		dbconn.setAutoCommit(true);

		resultado[0] = "BACO : " + salida;
		resultado[1] = "DELTA: " + resultadoDelta[0];
		resultado[2] = "DELTA: " + resultadoDelta[1];

		log.info("FINAL InterfaseDbBacoDeltaDespachoAndreani");
		return resultado;

	}

	/**
	 * --INTERFAZ de stock DELTA con db_baco: DESPACHO PARA STOCK --19/11/2008
	 */

	public String[] InterfaseDbBacoDeltaTransfDepAreas(BigDecimal idareaorigen,
			BigDecimal idareadestino, Timestamp fechamov, String tipomov,
			BigDecimal num_cnt, BigDecimal sucursal, String tipo,
			String observaciones, Hashtable htArticulos, String usuarioalt,
			BigDecimal idempresa) throws EJBException, SQLException {

		String[] resultado = new String[4];
		String[] resultadoDelta = new String[] { "", "" };
		String salida = "OK";
		String status = "OK";
		String error = "";
		Enumeration en;
		// String conjuntoOrigen = "";
		// String conjuntoDestino = "";
		// String tipoim = "";
		String ciom = "";
		BigDecimal idtransaccion = null;
		BigDecimal idusuariobaco = new BigDecimal(-1);

		String resultadoBaco = "";

		ResultSet rsBaco = null;
		String param = "";
		log.info("INICIA: InterfaseDbBacoDeltaTransfDepAreas");
		try {

			idtransaccion = GeneralBean.getNextValorSequencia(
					"seq_loginterfaces", dbconn);

			sdbconn.setAutoCommit(false);
			dbconn.setAutoCommit(false);

			// usuarialt + ":IF" es utilizado para saber que la transaccion se
			// genero a partir de las interfaces

			param = "'Z', NULL, NULL, NULL, NULL, '"
					+ usuarioalt.replaceAll(":IF", "") + "', NULL";
			rsBaco = getTransaccion("spUsuarios", param);

			if (rsBaco != null && rsBaco.next()) {

				idusuariobaco = rsBaco.getBigDecimal(2);

				en = htArticulos.keys();

				while (en.hasMoreElements()) {
					String clave = en.nextElement().toString();
					String[] datos = (String[]) htArticulos.get(clave);

					String xiom = datos[0];
					String cantidad = datos[4];

					// datos[0] = codigo_st
					// datos[1] = alias_st
					// datos[2] = descrip_st
					// datos[3] = descri2_st
					// datos[4] = this.cantidad[i];
					// datos[5] = this.origen[j];
					// datos[6] = this.destino[j];
					// datos[7] = cost_uc_st

					// @pXiom VARCHAR(15),
					// @pCantidad NUMERIC(18),
					// @pAreaOrigen NUMERIC(18),
					// @pAreaDestino NUMERIC(18),
					// @pUsuario NUMERIC(18)

					param = "'" + xiom + "', " + cantidad + ", " + idareaorigen
							+ ", " + idareadestino + ", " + idusuariobaco;

					rsBaco = getTransaccion("spInterfacesTransferenciasAreas",
							param);

					if (rsBaco != null && rsBaco.next()) {

						salida = rsBaco.getString(1);

						if (!salida.equalsIgnoreCase("OK")) {
							status = "ERROR";
							break;
						}

					} else {
						status = "ERROR";
						salida = "BACO:  Error inesperado al mover de area el articulo: "
								+ xiom;
						break;

					}

				}

				resultadoBaco = salida;

				interFacesLogStatusCreate(idtransaccion, "BACO / " + salida,
						"InterfaseDbBacoDeltaTransfDepAreas", status, "",
						idempresa, usuarioalt);

				if (salida.equalsIgnoreCase("OK")) {

					resultadoDelta = stockMovInternoCreate(fechamov, tipomov,
							num_cnt, sucursal, tipo,
							"INTF-TRANSFIERE-DEP-AREAS.", htArticulos,
							usuarioalt, idempresa);

					salida = resultadoDelta[0] == null ? "ERROR"
							: resultadoDelta[0];

					status = !salida.equalsIgnoreCase("OK") ? "ERROR" : "OK";

					interFacesLogStatusCreate(idtransaccion, "DELTA / "
							+ resultadoDelta[0] + " - " + resultadoDelta[1],
							"InterfaseDbBacoDeltaTransfDepAreas", status, "",
							idempresa, usuarioalt);
				}

			} else {
				salida = resultadoBaco = "BACO - Imposible recuperar usuario :  "
						+ usuarioalt;

				interFacesLogStatusCreate(idtransaccion, "BACO / " + salida,
						"InterfaseDbBacoDeltaTransfDepAreas", "ERROR", "",
						idempresa, usuarioalt);

			}

		} catch (Exception e) {
			salida = resultado[0] = "E0: BACO-DELTA:";
			interFacesLogStatusCreate(idtransaccion, "EXCEPTION / " + e,
					"InterfaseDbBacoDeltaTransfDepAreas", "ERROR", "app",
					idempresa, usuarioalt);
			log.error("InterfaseDbBacoDeltaTransfDepAreas(..):" + e);
		}

		if (!salida.equalsIgnoreCase("OK")) {
			resultado[3] = "ABORTA";
			sdbconn.rollback();
			dbconn.rollback();
		} else {
			resultado[3] = "OK";
			sdbconn.commit();
			dbconn.commit();
		}

		resultado[0] = "SB / " + resultadoBaco;
		resultado[1] = "DELTA:" + resultadoDelta[0];
		resultado[2] = "DELTA:" + resultadoDelta[1];

		sdbconn.setAutoCommit(true);
		dbconn.setAutoCommit(true);

		log.info("FINAL: InterfaseDbBacoDeltaTransfDepAreas");
		return resultado;
	}

	/**
	 * -- MOVIMIENTOS DE SALIDA BEGIN .......................................
	 * Metodo para la entidad: stockhis - stockstockbis - stockmovstock -
	 * stockcontstock - stockanexos - stockremitos; Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Thu Aug 23 16:55:00 GMT-03:00 2006
	 * 
	 */

	public String[] stockMovSalidaCreate(BigDecimal codigo_anexo,
			String razon_social, String domicilio, String codigo_postal,
			BigDecimal idlocalidad, BigDecimal idprovincia, String cuit,
			String iibb, Timestamp fechamov, BigDecimal remito_ms,
			String tipomov, BigDecimal sucursal, String tipo,
			boolean remitopendiente, int ejercicioactivo, String observaciones,
			Hashtable htArticulos, Hashtable htCuentas, String usuarioalt,
			BigDecimal idempresa) throws EJBException, SQLException {

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
		BigDecimal idcontadorcomprobante = getIdcontadorComprobante(usuarioalt,
				idempresa);

		sucu_ms = GeneralBean.getSucursalComprobante(idcontadorcomprobante,
				idempresa, dbconn);
		if (idcontadorcomprobante == null
				|| idcontadorcomprobante.longValue() < 0) {
			salida = "El idcontador es incorrecto - no esta asociado o hubo un error al recuperarlo.";
		} else if (sucu_ms == null || sucu_ms.longValue() < 0) {
			salida = "Verificar sucursal definida para el contador de remitos del puesto.";
		}
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
			//

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
							idempresa, dbconn);
					comprob_ms = nrointerno_ms;
					remito_interno_ms = GeneralBean.getContador(
							idcontadorcomprobante, idempresa, dbconn);

					salida = stockAnexosCreate(nrointerno_ms, codigo_anexo,
							razon_social, domicilio, codigo_postal,
							idlocalidad, idprovincia, cuit, iibb, usuarioalt,
							idempresa);

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
											centr1_cs, centr2_cs, usuarioalt,
											idempresa);
								} else {
									salida = stockContStockImporteUpdate(
											nrointerno_ms, cuenta_cs,
											importe_cs, idempresa);
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
										observaciones, sucu_ms,
										remito_interno_ms, usuarioalt,
										idempresa);
							} else {

								salida = stockMovStockCantidadUpdate(
										nrointerno_ms, articulo, cantArtMov,
										idempresa);
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
									new BigDecimal("0"), usuarioalt, idempresa);

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
									usuarioalt, idempresa);
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
													centr2_cs, usuarioalt,
													idempresa);
										} else {
											salida = stockContStockImporteUpdate(
													nrointerno_ms, cuenta_cs,
													importe_cs, idempresa);
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
										codigo_rm, null, sucursal, usuarioalt,
										idempresa);
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
			log.error("stockMovSalidaCreate(): " + e);
		}
		if (!salida.equalsIgnoreCase("OK"))
			dbconn.rollback();

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

	// 20091002 - EJV ---- >

	// Estado 1 - Entregado
	// MANTIS 402: interfases despachos
	public String[] stockMovSalidaWAPEntregadoCreate(BigDecimal codigo_anexo,
			String razon_social, String domicilio, String codigo_postal,
			BigDecimal idlocalidad, BigDecimal idprovincia, String cuit,
			String iibb, Timestamp fechamov, BigDecimal remito_ms,
			String tipomov, BigDecimal sucursal, String tipo,
			boolean remitopendiente, int ejercicioactivo, String observaciones,
			Hashtable htArticulos, Hashtable htCuentas, String usuarioalt,
			BigDecimal idempresa) throws EJBException, SQLException {

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
		BigDecimal idcontadorcomprobante = getIdcontadorComprobante(usuarioalt,
				idempresa);

		sucu_ms = GeneralBean.getSucursalComprobante(idcontadorcomprobante,
				idempresa, dbconn);
		if (idcontadorcomprobante == null
				|| idcontadorcomprobante.longValue() < 0) {
			salida = "El idcontador es incorrecto - no esta asociado o hubo un error al recuperarlo.";
		} else if (sucu_ms == null || sucu_ms.longValue() < 0) {
			salida = "Verificar sucursal definida para el contador de remitos del puesto.";
		}
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
			//

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
							idempresa, dbconn);
					comprob_ms = nrointerno_ms;
					remito_interno_ms = GeneralBean.getContador(
							idcontadorcomprobante, idempresa, dbconn);

					salida = stockAnexosCreate(nrointerno_ms, codigo_anexo,
							razon_social, domicilio, codigo_postal,
							idlocalidad, idprovincia, cuit, iibb, usuarioalt,
							idempresa);

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
											articulo, origen, idempresa, dbconn)
									.compareTo(cantArtMov)) < 0) {
								salida = "Existencia en reserva de articulo "
										+ articulo
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
											centr1_cs, centr2_cs, usuarioalt,
											idempresa);
								} else {
									salida = stockContStockImporteUpdate(
											nrointerno_ms, cuenta_cs,
											importe_cs, idempresa);
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
										observaciones, sucu_ms,
										remito_interno_ms, usuarioalt,
										idempresa);
							} else {

								salida = stockMovStockCantidadUpdate(
										nrointerno_ms, articulo, cantArtMov,
										idempresa);
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
									cantArtMov.negate(), usuarioalt, idempresa);

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
									usuarioalt, idempresa);
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
													centr2_cs, usuarioalt,
													idempresa);
										} else {
											salida = stockContStockImporteUpdate(
													nrointerno_ms, cuenta_cs,
													importe_cs, idempresa);
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
										codigo_rm, null, sucursal, usuarioalt,
										idempresa);
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
		if (!salida.equalsIgnoreCase("OK"))
			dbconn.rollback();

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

	// Estado 2 - Rechazado / 3 - Cambio

	// MANTIS 402: interfases despachos
	public String[] stockMovStockRes2DispWAPRechazoCambioCreate(
			Hashtable htArticulos, String usuarioalt, BigDecimal idempresa)
			throws EJBException, SQLException {

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
							articulo, origen, idempresa, dbconn)
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
							cantArtMov, "", "", cantArtMov.negate(),
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

	/** -- MOVIMIENTOS DE SALIDA END */

	/**
	 * -- MOVIMIENTOS DE ENTRADA BEGIN.........................................
	 * Metodo para la entidad: stockhis - stockstockbis - stockmovstock -
	 * stockcontstock - stockanexos - stockremitos; Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Thu Aug 23 16:55:00 GMT-03:00 2006
	 * 
	 */

	public String[] stockMovEntradaCreate(BigDecimal codigo_anexo,
			String razon_social, String domicilio, String codigo_postal,
			BigDecimal idlocalidad, BigDecimal idprovincia, String cuit,
			String iibb, Timestamp fechamov, BigDecimal remito_ms,
			String tipomov, BigDecimal sucursal, String tipo,
			boolean remitopendiente, int ejercicioactivo, String observaciones,
			Hashtable htArticulos, Hashtable htCuentas, String usuarioalt,
			BigDecimal idempresa) throws EJBException, SQLException {

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
								dbconn));
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
			BigDecimal idcontadorcomprobante = getIdcontadorComprobante(
					usuarioalt, idempresa);

			sucu_ms = GeneralBean.getSucursalComprobante(idcontadorcomprobante,
					idempresa, dbconn);
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
							idempresa, dbconn);
					comprob_ms = nrointerno_ms;
					salida = stockAnexosCreate(nrointerno_ms, codigo_anexo,
							razon_social, domicilio, codigo_postal,
							idlocalidad, idprovincia, cuit, iibb, usuarioalt,
							idempresa);
					remito_interno_ms = GeneralBean.getContador(
							idcontadorcomprobante, idempresa, dbconn);

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
											centr1_cs, centr2_cs, usuarioalt,
											idempresa);
								} else {
									salida = stockContStockImporteUpdate(
											nrointerno_ms, cuenta_cs,
											importe_cs, idempresa);
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
										observaciones, sucu_ms,
										remito_interno_ms, usuarioalt,
										idempresa);
							} else {

								salida = stockMovStockCantidadUpdate(
										nrointerno_ms, articulo, cantArtMov,
										idempresa);
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
										new BigDecimal("0"), usuarioalt,
										idempresa);
							} else {
								salida = stockStockBisCreate(articulo, destino,
										cantArtMov, "", "",
										new BigDecimal("0"), usuarioalt,
										idempresa);
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
									usuarioalt, idempresa);

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
													centr2_cs, usuarioalt,
													idempresa);
										} else {
											salida = stockContStockImporteUpdate(
													nrointerno_ms, cuenta_cs,
													importe_cs, idempresa);
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
										codigo_rm, null, sucursal, usuarioalt,
										idempresa);
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
			salida = "E-1000: Ocurri� Excepci�n Mientras Se Actualizaba Stock."
					+ "{" + e + "}";
			resultado[0] = salida;
			log.error("stockMovEntradaCreate(): " + e);
		}
		if (!salida.equalsIgnoreCase("OK"))
			dbconn.rollback();
		//
		//
		//
		// dbconn.setAutoCommit(true);
		//
		//
		//
		return resultado;
	}

	// 20091002 - EJV --- >
	// MANTIS 402: interfases despachos

	public String[] stockMovEntradaWAPLimpiaEntregaCreate(
			BigDecimal codigo_anexo, String razon_social, String domicilio,
			String codigo_postal, BigDecimal idlocalidad,
			BigDecimal idprovincia, String cuit, String iibb,
			Timestamp fechamov, BigDecimal remito_ms, String tipomov,
			BigDecimal sucursal, String tipo, boolean remitopendiente,
			int ejercicioactivo, String observaciones, Hashtable htArticulos,
			Hashtable htCuentas, String usuarioalt, BigDecimal idempresa)
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
								dbconn));
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
			BigDecimal idcontadorcomprobante = getIdcontadorComprobante(
					usuarioalt, idempresa);

			sucu_ms = GeneralBean.getSucursalComprobante(idcontadorcomprobante,
					idempresa, dbconn);
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
							idempresa, dbconn);
					comprob_ms = nrointerno_ms;
					salida = stockAnexosCreate(nrointerno_ms, codigo_anexo,
							razon_social, domicilio, codigo_postal,
							idlocalidad, idprovincia, cuit, iibb, usuarioalt,
							idempresa);
					remito_interno_ms = GeneralBean.getContador(
							idcontadorcomprobante, idempresa, dbconn);

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
											centr1_cs, centr2_cs, usuarioalt,
											idempresa);
								} else {
									salida = stockContStockImporteUpdate(
											nrointerno_ms, cuenta_cs,
											importe_cs, idempresa);
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
										observaciones, sucu_ms,
										remito_interno_ms, usuarioalt,
										idempresa);
							} else {

								salida = stockMovStockCantidadUpdate(
										nrointerno_ms, articulo, cantArtMov,
										idempresa);
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

								salida = stockStockBisPedid_sbUpdate(articulo,
										destino, new BigDecimal("0"), "", "",
										cantArtMov, usuarioalt, idempresa);
							} else {
								salida = stockStockBisCreate(articulo, destino,
										new BigDecimal("0"), "", "",
										cantArtMov, usuarioalt, idempresa);
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
									usuarioalt, idempresa);

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
													centr2_cs, usuarioalt,
													idempresa);
										} else {
											salida = stockContStockImporteUpdate(
													nrointerno_ms, cuenta_cs,
													importe_cs, idempresa);
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
										codigo_rm, null, sucursal, usuarioalt,
										idempresa);
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
			salida = "E-1000: Ocurri� Excepci�n Mientras Se Actualizaba Stock."
					+ "{" + e + "}";
			resultado[0] = salida;
			log.error("stockMovEntradaWAPLimpiaEntregaCreate(): " + e);
		}
		if (!salida.equalsIgnoreCase("OK"))
			dbconn.rollback();
		//
		//
		//
		// dbconn.setAutoCommit(true);
		//
		//
		//
		return resultado;
	}

	public String[] stockMovEntradaWAPLimpiaRechazoCambioCreate(
			Hashtable htArticulos, String usuarioalt, BigDecimal idempresa)
			throws EJBException, SQLException {
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
							origen, idempresa, dbconn)) {
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
							cantArtMov.negate(), "", "", cantArtMov,
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

	// 20091002 - EJV < ---

	/** -- MOVIMIENTOS DE ENTRADA END */

	/**
	 * Metodos para la entidad: stockAnexos Copyrigth(r) sysWarp S.R.L. Fecha de
	 * creacion: Wed Nov 01 10:52:18 GMT-03:00 2006
	 */

	/** -- INICIA CAMBIOS DE DEPOSITO -- */

	/**
	 * Metodo para la entidad: stockstock - stockhis - stockstockbis -
	 * stockmovstock; Copyrigth(r) sysWarp S.R.L. Fecha de creacion: Thu Aug 23
	 * 16:55:00 GMT-03:00 2006
	 * 
	 */

	public String[] stockMovInternoCreate(Timestamp fechamov, String tipomov,
			BigDecimal num_cnt, BigDecimal sucursal, String tipo,
			String observaciones, Hashtable htArticulos, String usuarioalt,
			BigDecimal idempresa) throws EJBException, SQLException {
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

		log.info("htArticulos" + htArticulos);
		log.info("htArticulosImagen" + htArticulosImagen);

		try {
			// TODO: REALIZAR TODAS LAS VALIDACIONES
			// Pasos del movimiento ...
			// 1.0 - Generar movimiento salida .... stockmovstock
			// 1.1 - Generar movimiento entrada .... stockmovstock
			// 2.0 - Generar movimiento entrada .... stockbis
			// 2.1 - Generar movimiento salida .... stockbis
			// 3.0 - Generar movimiento historico .... stockhis
			// 3.1 - Generar movimiento historico .... stockhis

			//
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
								dbconn));
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
			BigDecimal idcontadorcomprobante = getIdcontadorComprobante(
					usuarioalt, idempresa);

			sucu_ms = GeneralBean.getSucursalComprobante(idcontadorcomprobante,
					idempresa, dbconn);

			if (idcontadorcomprobante == null
					|| idcontadorcomprobante.longValue() < 0) {
				salida = "El idcontador es incorrecto - no esta asociado o hubo un error al recuperarlo.";
			} else if (sucu_ms == null || sucu_ms.longValue() < 0) {
				salida = "Verificar sucursal definida para el contador de remitos del puesto.";
			}
			/**/

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
								sucursal, tipo, idempresa, dbconn);

					if (remito_ms.compareTo(new BigDecimal(-1)) > 0) {

						while (en.hasMoreElements()) {
							if (isFirst) {
								comprob_ms = GeneralBean.getContador(
										new BigDecimal(4), idempresa, dbconn);

								remito_interno_ms = GeneralBean.getContador(
										idcontadorcomprobante, idempresa,
										dbconn);
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
									origen, idempresa, dbconn)) {
								tipomov_ms = "S";
								cantArtDep = GeneralBean
										.getCantidadArticuloDeposito(articulo,
												origen, idempresa, dbconn);
								// CEP: 25/01/2007 : primer arreglo
								if ((cantArtDep.doubleValue() >= cantArtMov
										.doubleValue())
										|| GeneralBean.hasStockNegativo(
												idempresa, dbconn)) {

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
											new BigDecimal(5), idempresa,
											dbconn);
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
											sucu_ms, remito_interno_ms,
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
													idempresa, dbconn);

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
											"", new BigDecimal("0"),
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
											cantArtMov, "", "", "", usuarioalt,
											idempresa);
									if (!salida.equalsIgnoreCase("OK"))
										break;
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
											new BigDecimal(5), idempresa,
											dbconn);
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
											sucu_ms, remito_interno_ms,
											usuarioalt, idempresa);
									if (!salida.equalsIgnoreCase("OK"))
										break;
									// nrointerno_ms =
									// getValorSequencia("seq_stockmovstock");
									// 20061101 - nrointerno_ms permite
									// duplicados
									// Actualiza el destino ... o lo crea

									if (GeneralBean.getExisteArticuloDeposito(
											articulo, destino, idempresa,
											dbconn))

										/*
										 * ACTUALIZA ... articu_sb : deposi_sb :
										 * canti_sb : serie_sb : despa_sb :
										 * pedid_sb : usuarioalt : usuarioact :
										 * fechaalt : fechaact :
										 */
										salida = stockStockBisUpdate(articulo,
												destino, cantArtMov, "", "",
												new BigDecimal("0"),
												usuarioalt, idempresa);
									else
										// CREA ...
										salida = stockStockBisCreate(articulo,
												destino, cantArtMov, "", "",
												new BigDecimal("0"),
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
											cantArtMov, "", "", "", usuarioalt,
											idempresa);
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
						salida = "Error al recuperar N� comprobante.";
					}

					resultado[0] = salida;
					resultado[1] = resultado[1] != null ? resultado[1] + "-"
							+ comprob_ms.toString() : comprob_ms.toString();
				}//
			}
		} catch (Exception e) {
			// TODO: handle exception
			salida = "E-1000: Ocurrio Excepcion Mientras Se Actualizaba Stock.";
			resultado[0] = salida;
			log.error("stockMovInternoCreate(): " + e);
		}
		if (!salida.equalsIgnoreCase("OK"))
			dbconn.rollback();
		else
			htArticulos = null;

		htArticulosImagen = null;

		//
		//
		// dbconn.setAutoCommit(true);
		//
		//
		resultado[0] = salida;
		return resultado;
	}

	/** -- FINALIZA CAMBIOS DE DEPOSITO -- */

	// 20091001 - EJV ------>
	/** -- INICIA CAMBIOS DE DEPOSITO -- */

	/**
	 * Metodo para la entidad: stockstock - stockhis - stockstockbis -
	 * stockmovstock; Copyrigth(r) sysWarp S.R.L. Fecha de creacion: Thu Aug 23
	 * 16:55:00 GMT-03:00 2006
	 * 
	 */

	// MANTIS 402: interfases despachos
	public String[] stockMovInternoCreateDespachosSociosYAndreani(
			Timestamp fechamov, String tipomov, BigDecimal num_cnt,
			BigDecimal sucursal, String tipo, String observaciones,
			Hashtable htArticulos, String usuarioalt, BigDecimal idempresa)
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

		log.info("htArticulos" + htArticulos);
		log.info("htArticulosImagen" + htArticulosImagen);

		try {
			// TODO: REALIZAR TODAS LAS VALIDACIONES
			// Pasos del movimiento ...
			// 1.0 - Generar movimiento salida .... stockmovstock
			// 1.1 - Generar movimiento entrada .... stockmovstock
			// 2.0 - Generar movimiento entrada .... stockbis
			// 2.1 - Generar movimiento salida .... stockbis
			// 3.0 - Generar movimiento historico .... stockhis
			// 3.1 - Generar movimiento historico .... stockhis

			//
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
								dbconn));
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
			BigDecimal idcontadorcomprobante = getIdcontadorComprobante(
					usuarioalt, idempresa);

			sucu_ms = GeneralBean.getSucursalComprobante(idcontadorcomprobante,
					idempresa, dbconn);

			if (idcontadorcomprobante == null
					|| idcontadorcomprobante.longValue() < 0) {
				salida = "El idcontador es incorrecto - no esta asociado o hubo un error al recuperarlo.";
			} else if (sucu_ms == null || sucu_ms.longValue() < 0) {
				salida = "Verificar sucursal definida para el contador de remitos del puesto.";
			}
			/**/

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
								sucursal, tipo, idempresa, dbconn);

					if (remito_ms.compareTo(new BigDecimal(-1)) > 0) {

						while (en.hasMoreElements()) {
							if (isFirst) {
								comprob_ms = GeneralBean.getContador(
										new BigDecimal(4), idempresa, dbconn);

								remito_interno_ms = GeneralBean.getContador(
										idcontadorcomprobante, idempresa,
										dbconn);
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
									origen, idempresa, dbconn)) {
								tipomov_ms = "S";
								cantArtDep = GeneralBean
										.getCantidadReservaArticuloDeposito(
												articulo, origen, idempresa,
												dbconn);
								// CEP: 25/01/2007 : primer arreglo
								// if ((cantArtDep.doubleValue() >= cantArtMov
								// .doubleValue())
								// || GeneralBean.hasStockNegativo(
								// idempresa, dbconn)) {
								//									

								// 20091001 - EJV - La reserva no puede
								// manejarse en negativo.
								if ((cantArtDep.doubleValue() >= cantArtMov
										.doubleValue())) {

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
											new BigDecimal(5), idempresa,
											dbconn);
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
											sucu_ms, remito_interno_ms,
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
													idempresa, dbconn);

									// Actualiza stockbis
									// Descuenta del origen

									/*
									 * articu_sb : deposi_sb : canti_sb :
									 * serie_sb : despa_sb : pedid_sb :
									 * usuarioalt : usuarioact : fechaalt :
									 * fechaact :
									 */

									// log.info("ANTES DE stockStockBisPedid_sbUpdate");
									salida = stockStockBisPedid_sbUpdate(
											articulo, origen, new BigDecimal(
													"0"), "", "", cantArtMov
													.negate(), usuarioalt,
											idempresa);

									// log.info("DESPUES DE stockStockBisPedid_sbUpdate (salida): "
									// + salida);

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
											cantArtMov, "", "", "", usuarioalt,
											idempresa);
									if (!salida.equalsIgnoreCase("OK"))
										break;
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
											new BigDecimal(5), idempresa,
											dbconn);
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
											sucu_ms, remito_interno_ms,
											usuarioalt, idempresa);

									if (!salida.equalsIgnoreCase("OK"))
										break;
									// nrointerno_ms =
									// getValorSequencia("seq_stockmovstock");
									// 20061101 - nrointerno_ms permite
									// duplicados
									// Actualiza el destino ... o lo crea

									if (GeneralBean.getExisteArticuloDeposito(
											articulo, destino, idempresa,
											dbconn)) {

										/*
										 * ACTUALIZA ... articu_sb : deposi_sb :
										 * canti_sb : serie_sb : despa_sb :
										 * pedid_sb : usuarioalt : usuarioact :
										 * fechaalt : fechaact :
										 */
										salida = stockStockBisUpdate(articulo,
												destino, cantArtMov, "", "",
												new BigDecimal("0"),
												usuarioalt, idempresa);
									} else {
										// CREA ...
										salida = stockStockBisCreate(articulo,
												destino, cantArtMov, "", "",
												new BigDecimal("0"),
												usuarioalt, idempresa);

									}

									if (!salida.equalsIgnoreCase("OK"))
										break;

									/*
									 * nromov_sh : articu_sh : deposi_sh :
									 * serie_sh : despa_sh : canti_sh :
									 * estamp1_sh : estamp2_sh : aduana_sh :
									 * usuarioalt : usuarioact : fechaalt :
									 * fechaact :
									 */

									// 20091001 - EJV ---->
									String aux[] = stockStockBisCantPediCreateUpdate(
											articulo, destino, cantArtMov,
											usuarioalt, idempresa, dbconn);

									salida = aux[0];

									if (!salida.equalsIgnoreCase("OK"))
										break;

									// 20091001 - EJV <----

									salida = stockHisCreate(nrointerno_ms,
											articulo, destino, "", "",
											cantArtMov, "", "", "", usuarioalt,
											idempresa);

									if (!salida.equalsIgnoreCase("OK"))
										break;

								} else {
									salida = "Cantidad en reserva insuficiente para articulo "
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
						salida = "Error al recuperar N� comprobante.";
					}

					resultado[0] = salida;
					resultado[1] = resultado[1] != null ? resultado[1] + "-"
							+ comprob_ms.toString() : comprob_ms.toString();
				}//
			}
		} catch (Exception e) {
			// TODO: handle exception
			salida = "E-1000: Ocurrio Excepcion Mientras Se Actualizaba Stock.";
			resultado[0] = salida;
			log.error("stockMovInternoCreateDespachosSociosYAndreani(): " + e);
		}
		if (!salida.equalsIgnoreCase("OK"))
			dbconn.rollback();
		else
			htArticulos = null;

		htArticulosImagen = null;

		//
		//
		// dbconn.setAutoCommit(true);
		//
		//
		resultado[0] = salida;
		return resultado;
	}

	/** -- FINALIZA CAMBIOS DE DEPOSITO -- */

	// 20091001 - EJV <------
	// grabacion de un nuevo registro
	public String stockAnexosCreate(BigDecimal nint_ms_an,
			BigDecimal codigo_anexo, String razon_social, String domicilio,
			String codigo_postal, BigDecimal idlocalidad,
			BigDecimal idprovincia, String cuit, String iibb,
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
				PreparedStatement insert = dbconn.prepareStatement(ins);
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
	public String stockContStockCreate(BigDecimal nint_ms_cs,
			BigDecimal cuenta_cs, BigDecimal importe_cs, String tipo_cs,
			String sistema_cs, BigDecimal centr1_cs, BigDecimal centr2_cs,
			String usuarioalt, BigDecimal idempresa) throws EJBException {
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
				PreparedStatement insert = dbconn.prepareStatement(ins);
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

	public String stockContStockImporteUpdate(BigDecimal nint_ms_cs,
			BigDecimal cuenta_cs, BigDecimal importe_cs, BigDecimal idempresa)
			throws EJBException {
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
				insert = dbconn.prepareStatement(sql);

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

	public String stockMovStockCreate(BigDecimal nrointerno_ms,
			String sistema_ms, String tipomov_ms, BigDecimal comprob_ms,
			Timestamp fechamov, String articu_ms, BigDecimal canti_ms,
			BigDecimal moneda_ms, Double cambio_ms, Double venta_ms,
			BigDecimal costo_ms, String tipoaux_ms, String destino_ms,
			Double comis_ms, BigDecimal remito_ms, Double impint_ms,
			Double impifl_ms, Double impica_ms, Double prelis_ms,
			BigDecimal unidad_ms, Double merma_ms, Double saldo_ms,
			BigDecimal medida_ms, String observaciones, BigDecimal sucu_ms,
			BigDecimal remito_interno_ms, String usuarioalt,
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
				qDML += " unidad_ms,merma_ms,saldo_ms,medida_ms,observaciones,sucu_ms, remito_interno_ms, usuarioalt,idempresa)";
				qDML += " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				PreparedStatement statement = dbconn.prepareStatement(qDML);
				// seteo de campos:
				statement.setBigDecimal(1, nrointerno_ms);
				statement.setString(2, "S");
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
				statement.setBigDecimal(15, remito_ms);
				statement.setBigDecimal(16, new BigDecimal("0"));
				statement.setBigDecimal(17, new BigDecimal("0"));
				statement.setBigDecimal(18, new BigDecimal("0"));
				statement.setBigDecimal(19, new BigDecimal("0"));
				statement.setBigDecimal(20, new BigDecimal("0"));
				statement.setBigDecimal(21, new BigDecimal("0"));
				statement.setBigDecimal(22, new BigDecimal("0"));
				statement.setBigDecimal(23, new BigDecimal("0"));
				statement.setString(24, observaciones);
				statement.setBigDecimal(25, sucu_ms);
				statement.setBigDecimal(26, remito_interno_ms);
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

	public String stockMovStockCantidadUpdate(BigDecimal nrointerno_ms,
			String articu_ms, BigDecimal canti_ms, BigDecimal idempresa)
			throws EJBException {
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
				insert = dbconn.prepareStatement(sql);
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

	// 20091001 - EJV --->

	/*
	 * TODO: 20091001 - EJV REPLICADO EN ClientesBean (Original) :
	 * ClientesBean.stockStockBisCantPediCreateUpdate
	 */

	public String[] stockStockBisCantPediCreateUpdate(String articu_sb,
			BigDecimal deposi_sb, BigDecimal pedid_sb, String usuarioact,
			BigDecimal idempresa, Connection conn) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String[] resultado = new String[] { "", "" };
		String salida = "OK";
		String alerta = "";
		String qDML = "";
		BigDecimal totalExistencia = new BigDecimal(0);
		BigDecimal totalRemananente = new BigDecimal(0);
		List listDep = new ArrayList();
		String[] datosDep = new String[] { "", "", "", "" };
		// validaciones de datos:
		// 1. nulidad de campos
		if (articu_sb == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: articu_sb ";
		if (deposi_sb == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: deposi_sb ";
		if (pedid_sb == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: pedid_sb ";

		// 2. sin nada desde la pagina
		if (articu_sb.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: articu_sb ";
		// fin validaciones

		try {

			PreparedStatement statement = null;
			listDep = getStockDepositosPk(deposi_sb, idempresa);
			if (!listDep.isEmpty())
				datosDep = (String[]) listDep.get(0);

			totalExistencia = GeneralBean.getCantidadArticuloDeposito(
					articu_sb, deposi_sb, idempresa, conn);
			totalRemananente = totalExistencia.subtract(pedid_sb);

			if (!GeneralBean.hasStockNegativo(idempresa, dbconn)
			// && totalRemananente.longValue() >= 0)!!!! WHAT IT THIS???
					&& totalRemananente.longValue() < 0)
				salida = "Cantidad disponible existente insuficiente: "
						+ totalExistencia + " para art." + articu_sb
						+ " en dep. " + datosDep[1];

			if (salida.equalsIgnoreCase("OK")) {

				if (!GeneralBean.getExisteArticuloDeposito(articu_sb,
						deposi_sb, idempresa, conn)) {

					qDML = "INSERT INTO stockstockbis (articu_sb,deposi_sb,canti_sb,serie_sb,despa_sb,pedid_sb,usuarioalt, idempresa)";
					qDML += " VALUES (?,?,?,?,?,?,?,?)";
					statement = dbconn.prepareStatement(qDML);
					statement.clearParameters();
					statement.setString(1, articu_sb);
					statement.setBigDecimal(2, deposi_sb);
					statement.setBigDecimal(3, pedid_sb.negate());
					statement.setString(4, null);
					statement.setString(5, null);
					statement.setBigDecimal(6, pedid_sb);
					statement.setString(7, usuarioact);
					statement.setBigDecimal(8, idempresa);

					alerta = "#[Articulo:(" + articu_sb + ") - Deposito:("
							+ deposi_sb + "-" + datosDep[1] + ")].";

				} else {

					if (totalRemananente.compareTo(new BigDecimal(0)) < 0) {
						alerta = "#[Articulo:(" + articu_sb + ")-Deposito:("
								+ deposi_sb + "-" + datosDep[1] + ")].";
					}

					qDML = " UPDATE stockstockbis ";
					qDML += "   SET canti_sb=(canti_sb+?),pedid_sb=(pedid_sb+?),usuarioact=?,fechaact=?";
					qDML += " WHERE articu_sb=? AND deposi_sb=?  AND idempresa=?";
					statement = conn.prepareStatement(qDML);
					statement.clearParameters();

					statement.clearParameters();
					statement.setBigDecimal(1, pedid_sb.negate());
					statement.setBigDecimal(2, pedid_sb);
					statement.setString(3, usuarioact);
					statement.setTimestamp(4, fechaact);
					statement.setString(5, articu_sb);
					statement.setBigDecimal(6, deposi_sb);
					statement.setBigDecimal(7, idempresa);

				}

				int i = statement.executeUpdate();

				if (i != 1) {
					salida = "Fallo Actutualizacion para Articulo ("
							+ articu_sb + "), en deposito (" + deposi_sb + "-"
							+ datosDep[1] + ").";
				}
			}

		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error SQL public String stockStockBisCantPediCreateUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String stockStockBisCantPediCreateUpdate(.....)"
							+ ex);
		}

		resultado = new String[] { salida, alerta };
		return resultado;
	}

	/*
	 * TODO: 20091001 - EJV REPLICADO EN ClientesBean (Original) :
	 * ClientesBean.getStockDepositosPk
	 */

	public List getStockDepositosPk(BigDecimal codigo_dt, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT dt.codigo_dt,dt.descrip_dt,dt.direc_dt,dt.factura_dt "
				+ "  FROM stockdepositos dt " + " WHERE dt.idempresa = "
				+ idempresa.toString() + "   AND dt.codigo_dt = "
				+ codigo_dt.toString();

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
			log.error("Error SQL en el metodo : getStockDepositosPk(...) "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getStockDepositosPk(...)  "
							+ ex);
		}
		return vecSalida;
	}

	public String stockStockBisPedid_sbUpdate(String articu_sb,
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
				qDML += " WHERE articu_sb=? AND deposi_sb=?  AND idempresa=?";
				statement = dbconn.prepareStatement(qDML);
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

	// 20091001 - EJV <---

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

	public String stockHisCreate(BigDecimal nromov_sh, String articu_sh,
			BigDecimal deposi_sh, String serie_sh, String despa_sh,
			BigDecimal canti_sh, String estamp1_sh, String estamp2_sh,
			String aduana_sh, String usuarioalt, BigDecimal idempresa)
			throws EJBException {
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

	// grabacion de un nuevo registro
	public String stockRemitosCreate(BigDecimal nint_ms_rm,
			BigDecimal remito_rm, Timestamp fecha_rm, String tipo_rm,
			String tipomov_rm, BigDecimal codigo_rm, String marcado_rm,
			BigDecimal sucurs_rm, String usuarioalt, BigDecimal idempresa)
			throws EJBException {
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
				PreparedStatement insert = dbconn.prepareStatement(ins);
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

	// por primary key (primer campo por defecto)

	public List getProveedoArticulosPK(String codigo_st, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT codigo_st, alias_st, descrip_st, descri2_st, "
				+ "       cost_re_st, cost_uc_st, cost_pp_st, precipp_st, ultcomp_st, "
				+ "       -1 as deposito, 0 as cantidad, 0 as total, cuencom_st, unimed_st, "
				+ "       cuenven_st, cuenve2_st, cuencos_st"
				+ "  FROM STOCKSTOCK " + " WHERE codigo_st='" + codigo_st
				+ "'  AND idempresa = " + idempresa.toString();

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
					.error("Error SQL en el metodo : getProveedoArticulosPK( String codigo_st ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getProveedoArticulosPK( String codigo_st )  "
							+ ex);
		}
		return vecSalida;
	}

	public String stockStockBisCreate(String articu_sb, BigDecimal deposi_sb,
			BigDecimal canti_sb, String serie_sb, String despa_sb,
			BigDecimal pedid_sb, String usuarioalt, BigDecimal idempresa)
			throws EJBException {
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

	public List getStockArticulosPK(String codigo_st, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT codigo_st,alias_st,descrip_st,descri2_st, "
				+ "       0 as cantidad, -1 as origen, -1 as destino, cost_uc_st  "
				+ "  FROM STOCKSTOCK " + " WHERE codigo_st='" + codigo_st
				+ "' AND idempresa = " + idempresa.toString();

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
					.error("Error SQL en el metodo : getStockArticulosPK( String codigo_st ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getStockArticulosPK( String codigo_st )  "
							+ ex);
		}
		return vecSalida;
	}

	// --------------------------------------------------------------------------

	public BigDecimal getPuestoUsuario(String usuario, BigDecimal idempresa)
			throws EJBException {
		/**
		 * Entidad: General Global Usuarios
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer todos los Meses existentes
		 */
		BigDecimal idpuestousuario = new BigDecimal(-1);
		ResultSet rsSalida = null;
		String cQuery = "" + "SELECT idpuesto " + "  FROM globalusuarios "
				+ " WHERE LOWER(usuario) = LOWER('" + usuario
				+ "') AND idempresa = " + idempresa.toString();
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);

			if (rsSalida == null) {
				log.warn("Error al recuperar puesto usuario.");
			} else if (rsSalida.next()) {
				idpuestousuario = rsSalida.getBigDecimal(1);
				if (idpuestousuario == null)
					idpuestousuario = new BigDecimal(-1);
			} else {
				log.warn("Puesto usuario inexistente.");
			}
		} catch (SQLException sqlException) {
			log.error("Error SQL getPuestoUsuario: " + sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception getPuestoUsuario: " + ex);
		}
		return idpuestousuario;
	}

	// --------------------------------------------------------------------------

	public BigDecimal getContadorPuesto(BigDecimal idpuesto, String contador,
			BigDecimal idempresa) throws EJBException {
		/**
		 * Entidad: General Global Puestos
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer todos los Meses existentes
		 */
		BigDecimal contadorpuesto = new BigDecimal(-1);
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT idpuesto, puesto, idplanta, idconta_facturasa, idconta_facturasb, idconta_facturasc, "
				+ "       idconta_recibos, idconta_remitos1,idconta_remitos2, idconta_remitos3,idconta_remitos4,"
				+ "       idempresa, usuarioalt, usuarioact, fechaalt, fechaact "
				+ "  FROM globalpuestos " + " WHERE idpuesto = "
				+ idpuesto.toString() + " AND idempresa = "
				+ idempresa.toString();

		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);

			if (rsSalida == null) {
				log.warn("Error al recuperar contador puesto.");
			} else if (rsSalida.next()) {
				contadorpuesto = rsSalida.getBigDecimal(contador);
				if (contadorpuesto == null)
					contadorpuesto = new BigDecimal(-1);
			} else {
				log.warn("No existe puesto.");
			}

		} catch (SQLException sqlException) {
			log.error("Error SQL getContadorPuesto: " + sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception getContadorPuesto: " + ex);
		}
		return contadorpuesto;
	}

	// --------------------------------------------------------------------------

	public String[] getContadorSucursal(BigDecimal idcontador,
			BigDecimal idempresa) throws EJBException {
		/**
		 * Entidad: General Global Contadores
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer todos los Meses existentes
		 */
		ResultSet rsSalida = null;
		String salida = "OK";
		String[] resultado = new String[3];
		String cQuery = ""

				+ "SELECT idcontador, contador, valor, descripcion, nrosucursal, "
				+ "       idempresa,  usuarioalt, usuarioact, fechaalt, fechaact  "
				+ "  FROM globalcontadores " + " WHERE idcontador = "
				+ idcontador.toString() + " AND idempresa = "
				+ idempresa.toString();

		try {

			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);

			if (rsSalida == null) {

				salida = "Imposible recuperar sucursal - remito.";

			} else if (rsSalida.next()) {

				resultado[1] = rsSalida.getString("nrosucursal");

				if (resultado[1] == null) {
					resultado[1] = "-1";
					salida = "Sucursal no definida para el contador.";

				}

				resultado[2] = rsSalida.getString("valor");

			} else {
				salida = "Contador inexistente.";
			}

		} catch (SQLException sqlException) {
			salida = "Error SQL al capturar contador - sucursal.";
			log.error("Error SQL getContadorSucursal: " + sqlException);
		} catch (Exception ex) {
			salida = "Excepcion al capturar contador - sucursal.";
			log.error("Salida por exception getContadorSucursal: " + ex);
		}

		resultado[0] = salida;

		return resultado;
	}

	// --------------------------------------------------------------------------

	public BigDecimal getIdcontadorComprobante(String usuario,
			BigDecimal idempresa) throws EJBException {

		String[] resultado = null;
		BigDecimal idpuestousuario = new BigDecimal(-1);
		BigDecimal contadorpuesto = new BigDecimal(-1);

		try {

			if (usuario == null)
				usuario = "";

			usuario = usuario.toLowerCase().replaceAll(":if", "");

			if (usuario.toLowerCase().indexOf("wap") > -1) {
				usuario = "wap";
			}

			idpuestousuario = getPuestoUsuario(usuario, idempresa);

			if (idpuestousuario.doubleValue() < 0) {
				log.warn("Imposible capturar puesto usuario.");
			} else {

				contadorpuesto = getContadorPuesto(idpuestousuario,
						"idconta_remitos1", idempresa);

				if (contadorpuesto.doubleValue() < 0) {
					log.warn("Imposible capturar contador puesto.");
				}

			}

		} catch (Exception e) {
			// log.warn( "Error al conformar datos comprobante - sucursal." );
			log.error("getIdcontadorComprobante: " + e);
		}

		return contadorpuesto;
	}

	// //////
	// por primary key (primer campo por defecto)

	public ResultSet getStockBisXDepostito(BigDecimal deposi_sb,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT articu_sb, canti_sb::numeric(18), deposi_sb, pedid_sb::numeric(18) "
				+ "  FROM stockstockbis    " + " WHERE  deposi_sb="
				+ deposi_sb.toString() + "   AND  idempresa = "
				+ idempresa.toString();

		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);

		} catch (SQLException sqlException) {
			log
					.error("Error SQL en el metodo : getStockBisXDepostito( BigDecimal codigo_dt ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getStockBisXDepostito( BigDecimal codigo_dt )  "
							+ ex);
		}
		return rsSalida;
	}

	public ResultSet callSpInterfacesStockConjuntoTmp(BigDecimal cconjunto,
			BigDecimal idempresa) throws EJBException, SQLException {

		ResultSet rsSalida = null;
		BigDecimal iddepositodelta = new BigDecimal(0);
		sdbconn.setAutoCommit(false);

		try {

			String query = "";
			Statement statment = sdbconn.createStatement();
			query = ""
					+ "CREATE TABLE #tmpStockDelta "
					+ "           (articu_sb VARCHAR(15) COLLATE SQL_Latin1_General_CP1_CI_AI , "
					+ "            canti_sb NUMERIC(18) , deposi_sb NUMERIC(18), cconjunto NUMERIC(18), pedid_sb NUMERIC(18) "
					+ "            );";

			iddepositodelta = getIdDepositoDeltaXConjunto(cconjunto);
			ResultSet rsStockDelta = getStockBisXDepostito(iddepositodelta,
					idempresa);

			while (rsStockDelta != null && rsStockDelta.next()) {
				query += "\n INSERT INTO #tmpStockDelta VALUES('"
						+ rsStockDelta.getString("articu_sb") + "', "
						+ rsStockDelta.getString("canti_sb") + ", "
						+ rsStockDelta.getString("deposi_sb") + ", "
						+ cconjunto + ", " + rsStockDelta.getString("pedid_sb")
						+ ");";
			}

			statment.execute(query);
			rsSalida = getTransaccion("spInterfacesStockConjuntoTMP", cconjunto
					.toString());

			statment.execute("DROP TABLE #tmpStockDelta;");

		} catch (Exception e) {
			sdbconn.rollback();
			log.error("callSpInterfacesStockConjuntoTmp(...): " + e);
		}

		sdbconn.commit();
		sdbconn.setAutoCommit(true);
		return rsSalida;
	}

	/**
	 * ========================================================================
	 * ==============
	 * ============================================================
	 * ==========================
	 * 
	 * @Comenteario: DESARMADO DE ESQUEMAS / MIX
	 * @Fecha: 20081114
	 * @Autor: EJV
	 * 
	 */

	public String[] callStockMovDesarmadoProductosEsquema(BigDecimal idesquema,
			String codigo_st, BigDecimal codigo_dt, BigDecimal cantidad,
			BigDecimal idmotivodesarma, String observaciones, int recursivo,
			int ejercicioactivo, BigDecimal idcontadorcomprobante,
			String sistema_ms, BigDecimal idarea, BigDecimal idempresa,
			String usuarioalt) throws EJBException, SQLException {

		String salida = "";
		String[] resultado = new String[] { "", "" };
		String[] nrosinternos = null;
		String param = "";
		BigDecimal idtransacciondesarmado = new BigDecimal(-1);
		BigDecimal ciom = new BigDecimal(0);
		BigDecimal idusuariobaco = new BigDecimal(-1);
		ResultSet rs = null;
		String status = "OK";
		BigDecimal idtransaccion = new BigDecimal(-1);

		String descripcionLog = "";

		dbconn.setAutoCommit(false);
		sdbconn.setAutoCommit(false);
		observaciones = "[INTERFACES]" + observaciones;

		try {

			descripcionLog = "DELTA: idesquema=" + idesquema + ", codigo_st="
					+ codigo_st + ", codigo_dt=" + codigo_dt + ", cantidad="
					+ cantidad + ",idmotivodesarma=" + idmotivodesarma
					+ ", observaciones=" + observaciones + ", recursivo="
					+ recursivo + ", ejercicioactivo=" + ejercicioactivo
					+ ", idcontadorcomprobante=" + idcontadorcomprobante
					+ ", sistema_ms=" + sistema_ms + ", idarea=" + idarea
					+ ", " + idempresa + ", " + usuarioalt;

			descripcionLog += " </-  -/> BACO: ciom=" + ciom + ", cantidad="
					+ cantidad + ", idarea=" + idarea + ", idusuariobaco="
					+ idusuariobaco + ", idmotivodesarma=" + idmotivodesarma;

			idtransaccion = GeneralBean.getNextValorSequencia(
					"seq_loginterfaces", dbconn);

			//

			ejercicioactivo = getEjercicioActivo(idempresa);

			//

			param = "'Z', NULL, NULL, NULL, NULL, '" + usuarioalt + "', NULL";
			rs = getTransaccion("spUsuarios", param);

			if (rs != null && rs.next()) {

				idusuariobaco = rs.getBigDecimal(2);

				rs = getTransaccion("spInterfacesVProductosXiomOne", "'"
						+ codigo_st + "'");

				if (rs != null && rs.next()) {

					ciom = rs.getBigDecimal(2);

					param = ciom + ", " + cantidad + ", " + idarea + ", "
							+ idusuariobaco + ", " + idmotivodesarma;

					rs = getTransaccion("spInterfacesImpdescomMix ", param);

					if (rs != null && rs.next()) {

						salida = resultado[0] = rs.getString(1);

						if (salida.equalsIgnoreCase("OK")) {

							if (usuarioalt.length() > 17)
								usuarioalt = usuarioalt.substring(0, 16);
							if (usuarioalt.indexOf(":IF") == -1)
								usuarioalt += ":IF";

							resultado = stockMovDesarmadoProductosEsquema(
									idesquema, codigo_st, codigo_dt, cantidad,
									idmotivodesarma, observaciones, recursivo,
									ejercicioactivo, idcontadorcomprobante,
									sistema_ms, 1, idempresa, usuarioalt);

							salida = resultado[0];
							nrosinternos = resultado[1].split("-");

							if (salida.equalsIgnoreCase("OK")) {

								idtransacciondesarmado = GeneralBean
										.getNextValorSequencia(
												"seq_idtransacciondesarmado",
												dbconn);

								stockDesarmadoLogCreate(idtransacciondesarmado,
										idesquema, nrosinternos, recursivo,
										cantidad, idmotivodesarma, idempresa,
										usuarioalt);

							}

						}

					} else
						salida = resultado[0] = "BACO-Error inesperado al desarmar en sistema BACO.";

				} else
					salida = resultado[0] = "BACO-Imposible recuperas CIOM para articulo:  "
							+ codigo_st;
			} else
				salida = resultado[0] = "BACO-Imposible recuperar usuario :  "
						+ usuarioalt;

		} catch (Exception e) {
			salida = "Error inesperado en callStockMovDesarmadoProductosEsquema.";
			log.error("callStockMovDesarmadoProductosEsquema: " + e);
		}

		if (!salida.equalsIgnoreCase("OK")) {
			status = "ERROR";
			dbconn.rollback();
			sdbconn.rollback();
		} else {
			dbconn.commit();
			sdbconn.commit();
		}

		interFacesLogStatusCreate(idtransaccion, salida + "  ///  "
				+ descripcionLog, "callStockMovDesarmadoProductosEsquema",
				status, "desarmaDeltaEsquemaBacoAreas.jsp", idempresa,
				usuarioalt);

		dbconn.setAutoCommit(true);
		sdbconn.setAutoCommit(true);

		return resultado;
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

						resultado = stockMovSalidaCreate(codigo_anexo,
								razon_social, domicilio, codigo_postal,
								idlocalidad, idprovincia, cuit,
								iibb,
								// sistema_ms,
								fechamov, remito_ms, tipomov, sucursal, tipo,
								remitopendiente, ejercicioactivo, obs,
								htArticulos, htCuentas,
								// idcontadorcomprobante, handAutocommit,
								usuarioalt, idempresa);

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
								resultado = stockMovEntradaCreate(codigo_anexo,
										razon_social, domicilio, codigo_postal,
										idlocalidad, idprovincia, cuit,
										iibb,
										// sistema_ms,
										fechamov, remito_ms, tipomov, sucursal,
										tipo, remitopendiente, ejercicioactivo,
										obs, htArticulos, htCuentas,
										// idcontadorcomprobante,
										// handAutocommit,
										usuarioalt, idempresa);

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
			log.info("stockMovDesarmadoProductosEsquema(...):" + e);
		}

		log.info("FINALIZA NIVEL: " + nivel);

		retorno[0] = salida;
		retorno[1] = nrosinternos;

		return retorno;
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

	/*-----------------------------------------------------*/

	public List getStockEsquemasAnidados(BigDecimal idesquema,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;

		String cQuery = "SELECT ed.codigo_st as idesquema, ed.cantidad "
				+ "        FROM produccionesquemas_deta ed  "
				+ "       WHERE ed.idesquema = " + idesquema.toString()
				+ "         AND ed.reutiliza = 'S' AND ed.tipo = 'E'  "
				+ "         AND ed.idempresa = " + idempresa.toString();

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
					.error("Error SQL en el metodo : getStockEsquemasAnidados( ... ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getStockEsquemasAnidados( ... )  "
							+ ex);
		}
		return vecSalida;
	}

	/**
	 * */

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

	public List getStockEsquemaArticulosDesarma(String codigo_st,
			BigDecimal idesquema, boolean isproductofinal, BigDecimal idempresa)
			throws EJBException {

		ResultSet rsSalida = null;

		String operador = isproductofinal ? " = " : " <> ";
		String cQuery = ""
				+ "SELECT st.codigo_st, st.alias_st, st.descrip_st, st.descri2_st, "
				+ "       st.cost_re_st, st.cost_uc_st, st.cost_pp_st, st.precipp_st, st.ultcomp_st, "
				+ "       -1 as deposito, 0 as cantidad, 0 as total, st.cuencom_st, st.unimed_st, "
				+ "       st.cuenven_st, st.cuenve2_st, st.cuencos_st, ed.tipo, "
				// -------------------------------------------------------------------------------
				// 20100128 - EJV
				// ------------------------------------------------------------------------------>
				+ "       st.id_indi_st, st.despa_st,"
				// <------------------------------------------------------------------------------
				+ "       ed.tipo, ed.cantidad as cantxesquema "
				+ "  FROM STOCKSTOCK st "
				+ "       INNER JOIN produccionesquemas_deta ed ON st.codigo_st = ed.codigo_st"
				+ "              AND st.idempresa = ed.idempresa AND ed.reutiliza = 'S' "
				+ " WHERE st.codigo_st " + operador + " '" + codigo_st
				+ "'  AND st.idempresa = " + idempresa.toString()
				+ "   AND ed.idesquema = " + idesquema.toString();

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
					.error("Error SQL en el metodo : getStockEsquemaArticulosDesarma( ... ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getStockEsquemaArticulosDesarma( ... )  "
							+ ex);
		}
		return vecSalida;
	}

	/*-----------------------------------------------------*/

	public List getStockEsquemaArtEsqDesarma(BigDecimal idesquema,
			BigDecimal idempresa) throws EJBException {

		ResultSet rsSalida = null;
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
				+ "       INNER JOIN produccionesquemas_deta eds ON eq.idesquema = eds.codigo_st "
				+ "              AND eq.idempresa = eds.idempresa AND eds.reutiliza = 'S' AND eq.entsal = 'P' AND eds.tipo = 'E' "
				+ "       INNER JOIN stockstock st ON st.codigo_st = eq.codigo_st "
				+ "              AND st.idempresa = eq.idempresa AND eq.reutiliza = 'S' "
				+ " WHERE st.idempresa = " + idempresa.toString()
				+ "   AND eds.idesquema = " + idesquema.toString()
				+ " ORDER BY tipo ";

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
					.error("Error SQL en el metodo : getStockEsquemaArticulosDesarma( ... ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getStockEsquemaArticulosDesarma( ... )  "
							+ ex);
		}
		return vecSalida;
	}

	//

	public List getStockDepostiosTransf(String codigo_dt_IN,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ " SELECT codigo_dt,descrip_dt,direc_dt,factura_dt,usuarioalt,usuarioact,fechaalt,fechaact "
				+ "  FROM STOCKDEPOSITOS WHERE codigo_dt IN (" + codigo_dt_IN
				+ ") AND idempresa = " + idempresa.toString() + "  ORDER BY 2;";
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
			log.error("Error SQL en el metodo : getStockDepostiosTransf() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getStockDepostiosTransf()  "
							+ ex);
		}
		return vecSalida;
	}

	/***************************************************************************
	 * 
	 * PRODUCCION
	 * 
	 * 
	 **************************************************************************/

	// para todo (ordena por el segundo campo por defecto)
	public List getINTFMovProduAll(long limit, long offset, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
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
				+ "       INNER JOIN interfacesopregalos i ON mp.idop = i.idop AND mp.idempresa=i.idempresa "
				+ " WHERE mp.idempresa = " + idempresa.toString()
				+ " ORDER BY 1 LIMIT " + limit + " OFFSET  " + offset + ";";
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
			log.error("Error SQL en el metodo : getINTFMovProduAll() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getINTFMovProduAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getINTFMovProduOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
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
				+ "       INNER JOIN interfacesopregalos i ON mp.idop = i.idop AND mp.idempresa=i.idempresa "
				+ " WHERE ((mp.idop::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(st.codigo_st) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%') AND st.idempresa = "
				+ idempresa.toString() + " ORDER BY 1 LIMIT " + limit
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
					.error("Error SQL en el metodo : getINTFMovProduOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getINTFMovProduOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)
	public String intfMovProduDelete(BigDecimal idop, BigDecimal idempresa)
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
					.error("Error SQL en el metodo : intfMovProduDelete( BigDecimal idop, BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: intfMovProduDelete( BigDecimal idop, BigDecimal idempresa )  "
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

	/**
	 * Metodos para la entidad: produccionMovProdu Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Wed Feb 21 13:30:16 GMT-03:00 2007
	 */

	public String intfOrdenProdCreate(BigDecimal idesquema,
			BigDecimal idcliente, BigDecimal cantre_op, BigDecimal cantest_op,
			java.sql.Date fecha_prometida, java.sql.Date fecha_emision,
			String observaciones, String codigo_st, BigDecimal idcontador,
			BigDecimal nrointerno, String usuarioalt, BigDecimal idempresa)
			throws EJBException, SQLException {
		String salida = "OK";
		BigDecimal idop = null;
		try {
			dbconn.setAutoCommit(false);

			salida = produccionMovProduCreate(idesquema, idcliente, cantre_op,
					cantest_op, fecha_prometida, fecha_emision, observaciones,
					codigo_st, idcontador, nrointerno, usuarioalt, idempresa);

			if (salida.equalsIgnoreCase("OK")) {

				idop = GeneralBean.getValorSequencia("seq_produccionmovprodu",
						dbconn);

				if (idop != null) {

					salida = produccionOrdenProdDetaCreate(idesquema, idop,
							cantest_op, new Timestamp(fecha_emision.getTime()),
							usuarioalt, idempresa);

					if (salida.equalsIgnoreCase("OK")) {

						salida = interfacesOpRegalosCreate(idop, idempresa,
								usuarioalt);

					}

				} else
					salida = "Error al recuperar Id. Orden de Producci�n.";

			}

		} catch (Exception e) {
			log.error("intfOrdenProdCreate(...)" + e);
		}

		if (!salida.equalsIgnoreCase("OK")) {
			dbconn.rollback();
		} else
			salida = idop.toString();

		dbconn.setAutoCommit(true);
		return salida;

	}

	public String intfOrdenProdUpdate(BigDecimal idop, BigDecimal idesquema,
			BigDecimal idcliente, BigDecimal cantre_op, BigDecimal cantest_op,
			java.sql.Date fecha_prometida, java.sql.Date fecha_emision,
			String observaciones, String codigo_st, BigDecimal idcontador,
			BigDecimal nrointerno, String usuarioalt, BigDecimal idempresa)
			throws EJBException, SQLException {
		String salida = "OK";

		try {
			dbconn.setAutoCommit(false);

			// log.info("Es mismo esquema: " + isMismoEsquemaOP(idop,
			// idesquema));
			if (!isOrdenProdAnulada(idop, idempresa)) {

				if (!isMismoEsquemaOP(idop, idesquema, idempresa)) {

					salida = produccionMovProdu_DetaDelete(idop, idempresa);

					if (salida.equalsIgnoreCase("OK")) {

						salida = produccionOrdenProdDetaCreate(idesquema, idop,
								cantest_op, new Timestamp(fecha_emision
										.getTime()), usuarioalt, idempresa);
					} else
						salida = "Error al actualizar detalle de  Orden de Produccion.";

				}
				if (salida.equalsIgnoreCase("OK")) {
					salida = intfMovProduUpdate(idop, idesquema, idcliente,
							cantre_op, cantest_op, fecha_prometida,
							fecha_emision, observaciones, codigo_st,
							idcontador, nrointerno, usuarioalt, idempresa);
				}

			} else {
				salida = "No se puede modificar la orden de produccion, la misma ya fue procesada.";
			}

		} catch (Exception e) {
			log.error("intfOrdenProdUpdate(...)" + e);
		}

		if (!salida.equalsIgnoreCase("OK")) {
			dbconn.rollback();
		}

		dbconn.setAutoCommit(true);
		return salida;

	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String produccionMovProduCreate(BigDecimal idesquema,
			BigDecimal idcliente, BigDecimal cantre_op, BigDecimal cantest_op,
			java.sql.Date fecha_prometida, java.sql.Date fecha_emision,
			String observaciones, String codigo_st, BigDecimal idcontador,
			BigDecimal nrointerno, String usuarioalt, BigDecimal idempresa)
			throws EJBException {
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
				String ins = "INSERT INTO PRODUCCIONMOVPRODU(idesquema, idcliente, cantre_op, cantest_op, fecha_prometida, fecha_emision, observaciones, codigo_st, idcontador, nrointerno, usuarioalt, idempresa ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
				insert.setString(11, usuarioalt);
				insert.setBigDecimal(12, idempresa);
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

	public String intfMovProduCreateOrUpdate(BigDecimal idop,
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
					.error("Error SQL public String intfMovProduCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String intfMovProduCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
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

	public String intfMovProduUpdate(BigDecimal idop, BigDecimal idesquema,
			BigDecimal idcliente, BigDecimal cantre_op, BigDecimal cantest_op,
			java.sql.Date fecha_prometida, java.sql.Date fecha_emision,
			String observaciones, String codigo_st, BigDecimal idcontador,
			BigDecimal nrointerno, String usuarioact, BigDecimal idempresa)
			throws EJBException {
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
			log.error("Error SQL public String intfMovProduUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log.error("Error excepcion public String intfMovProduUpdate(.....)"
					+ ex);
		}
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

	public List getINTFMovProduPK(BigDecimal idop, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT mp.idop,mp.idesquema,ec.esquema,mp.idcliente,mp.cantre_op,mp.cantest_op,mp.fecha_prometida,"
				+ "       mp.fecha_emision,mp.observaciones,mp.codigo_st,sk.descrip_st,mp.idcontador,mp.nrointerno,ed.codigo_dt,"
				+ "       mp.usuarioalt,mp.usuarioact,mp.fechaalt,mp.fechaact "
				+ "  FROM produccionmovprodu mp  "
				+ "       INNER JOIN produccionesquemas_cabe ec ON mp.idesquema = ec.idesquema AND mp.idempresa = ec.idempresa "
				+ "       INNER JOIN interfacesopregalos i ON mp.idop = i.idop AND mp.idempresa=i.idempresa "
				+ "       INNER JOIN produccionesquemas_deta ed ON ec.idesquema = ed.idesquema AND ec.idempresa = ed.idempresa AND ed.entsal = 'P' and ed.tipo = 'A'"
				+ "       INNER JOIN stockstock sk ON ed.codigo_st = sk.codigo_st AND ed.idempresa = sk.idempresa "
				+ " WHERE mp.idop=" + idop.toString() + " AND mp.idempresa = "
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
					.error("Error SQL en el metodo : getINTFMovProduPK( BigDecimal idop, BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getINTFMovProduPK( BigDecimal idop, BigDecimal idempresa )  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getINTFMovProdu_DetaPK(BigDecimal idop, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT mpd.idop,mpd.renglon,mpd.tipo,mpd.codigo,ae.descripcion,mpd.cantidad_cal,mpd.cantidad_rea,mpd.cantidad_stb,"
				+ "       mpd.margen,mpd.entsal,mpd.codigo_dt,sd.descrip_dt,mpd.edita,mpd.fechaaltaorden,mpd.stockbis,mpd.abrcer,mpd.improd,"
				+ "       mpd.usuarioalt,mpd.usuarioact,mpd.fechaalt,mpd.fechaact "
				+ "  FROM produccionmovprodu_deta mpd"
				+ "       INNER JOIN varticulosesquemas ae ON mpd.codigo = ae.codigo AND mpd.idempresa=ae.idempresa  AND mpd.tipo = ae.tipo "
				+ "       INNER JOIN stockdepositos sd ON mpd.codigo_dt = sd.codigo_dt AND mpd.idempresa=sd.idempresa "
				+ "       INNER JOIN interfacesopregalos i ON mpd.idop = i.idop AND mpd.idempresa=i.idempresa "
				+ " WHERE mpd.idop=" + idop.toString() + " AND mpd.idempresa="
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
					.error("Error SQL en el metodo : getINTFMovProdu_DetaPK( BigDecimal idop , BigDecimal idempresa) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getINTFMovProdu_DetaPK( BigDecimal idop , BigDecimal idempresa)  "
							+ ex);
		}
		return vecSalida;
	}

	public List getINTFMovProduPendientesAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT mp.idop,mp.idesquema,mp.idcliente,mp.cantre_op,mp.cantest_op,mp.fecha_prometida,mp.fecha_emision,"
				+ "       mp.observaciones,mp.codigo_st,st.descrip_st,mp.idcontador,mp.nrointerno,mp.usuarioalt,mp.usuarioact,mp.fechaalt,mp.fechaact "
				+ "  FROM produccionmovprodu mp "
				+ "       INNER JOIN interfacesopregalos i ON mp.idop = i.idop AND mp.idempresa=i.idempresa "
				+ "       INNER JOIN stockstock st ON mp.codigo_st = st.codigo_st AND st.idempresa = mp.idempresa "
				+ "WHERE mp.cantre_op < mp.cantest_op AND fbaja IS NULL AND mp.idempresa = "
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
					.error("Error SQL en el metodo : getINTFMovProduPendientesAll() "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getINTFMovProduPendientesAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getINTFMovProduPendientesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT mp.idop,mp.idesquema,mp.idcliente,mp.cantre_op,mp.cantest_op,mp.fecha_prometida,mp.fecha_emision,"
				+ "       mp.observaciones,mp.codigo_st,st.descrip_st,mp.idcontador,mp.nrointerno,mp.usuarioalt,mp.usuarioact,mp.fechaalt,mp.fechaact "
				+ "  FROM produccionmovprodu mp "
				+ "       INNER JOIN interfacesopregalos i ON mp.idop = i.idop AND mp.idempresa=i.idempresa "
				+ "       INNER JOIN stockstock st ON mp.codigo_st = st.codigo_st AND mp.idempresa = st.idempresa "
				+ " WHERE ((idop::VARCHAR) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' OR UPPER(mp.codigo_st) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' ) AND cantre_op < cantest_op AND fbaja IS NULL AND mp.idempresa = "
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
					.error("Error SQL en el metodo : getINTFMovProduPendientesOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getINTFMovProduPendientesOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	public String prodINTFGenerarExplosionOP(String[] idop,
			String[] cantidadparcial, String usuarioalt, BigDecimal idempresa)
			throws EJBException, SQLException {
		String salida = "OK";
		int i = 0;
		Object obj = null;
		BigDecimal nrointerno_ms = null;

		BigDecimal idordenproduccion = null;
		String articulo = "";
		String mix = "";
		BigDecimal deposito = null;
		BigDecimal cantidadMov = null;
		BigDecimal cantidadInsume = null;
		BigDecimal cantidadEstimada = null;
		BigDecimal cantidadParcialRealizada = null;

		BigDecimal idarea = new BigDecimal(62);
		String param = "";
		BigDecimal ciom = new BigDecimal(0);
		BigDecimal idusuariobaco = new BigDecimal(-1);
		String usuarioBaco = usuarioalt;
		ResultSet rs = null;
		String status = "OK";
		BigDecimal idtransaccion = new BigDecimal(-1);

		BigDecimal cantidadArtDep = new BigDecimal(0);
		boolean existeArticuloDeposito = false;

		idtransaccion = GeneralBean.getNextValorSequencia("seq_loginterfaces",
				dbconn);

		try {
			dbconn.setAutoCommit(false);
			sdbconn.setAutoCommit(false);

			if (usuarioalt.length() > 17)
				usuarioalt = usuarioalt.substring(0, 16);
			if (usuarioalt.indexOf(":IF") == -1)
				usuarioalt += ":IF";

			for (i = 0; i < idop.length; i++) {
				idordenproduccion = new BigDecimal(idop[i]);
				List listaOP = getProduccionMovProduPK(idordenproduccion,
						idempresa);
				Iterator iterOP = listaOP.iterator();
				if (iterOP.hasNext()) {
					String[] datosOP = (String[]) iterOP.next();

					articulo = mix = datosOP[9];
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
							cantidadMov, 1, idempresa);

					if (obj instanceof List) {

						log.info("prodINTFGenerarExplosionOP: INSTANCIA LIST");

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
											"PRODUCCION-[INTERFACES]",
											usuarioalt, idempresa);

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

						log
								.info("prodINTFGenerarExplosionOP: INSTANCIA STRING ");

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

			status = !salida.equalsIgnoreCase("OK") ? "ERROR" : "OK";

			String data = "";
			for (int r = 0; r < idop.length; r++) {
				data += " / OP: " + idop[r] + "Cant:" + cantidadparcial[r];
			}

			interFacesLogStatusCreate(idtransaccion, "DELTA: " + salida + data,
					"prodINTFGenerarExplosionOP", status,
					"ordenesProdExplosion.jsp", idempresa, usuarioalt);

			/**/

			//
			if (salida.equalsIgnoreCase("OK")) {

				log.info("Actualizar MSSQL 1 ");

				param = "'Z', NULL, NULL, NULL, NULL, '" + usuarioBaco
						+ "', NULL";
				rs = getTransaccion("spUsuarios", param);

				if (rs != null && rs.next()) {

					log.info("Actualizar MSSQL 2 ");

					idusuariobaco = rs.getBigDecimal(2);

					rs = getTransaccion("spInterfacesVProductosXiomOne", "'"
							+ mix + "'");

					if (rs != null && rs.next()) {

						log.info("Actualizar MSSQL 3 ");

						ciom = rs.getBigDecimal(2);

						param = ciom + ", " + cantidadMov + ", " + idarea
								+ ", " + idusuariobaco;

						rs = getTransaccion("spInterfacesArmarMix", param);

						if (rs != null && rs.next()) {

							log.info("Actualizar MSSQL 4 ");

							salida = rs.getString(1);
						} else {

							log.info("Actualizar MSSQL 5 ");

							salida = "Error desconocido al ejecutar spInterfacesArmarMix.";
						}

					} else
						salida = "BACO-Imposible recuperas CIOM para articulo:  "
								+ mix;
				} else
					salida = "BACO-Imposible recuperar usuario :  "
							+ usuarioalt;

				status = !salida.equalsIgnoreCase("OK") ? "ERROR" : "OK";

				interFacesLogStatusCreate(idtransaccion, "BACO: " + salida
						+ " / " + param, "prodINTFGenerarExplosionOP", status,
						"ordenesProdExplosion.jsp", idempresa, usuarioalt);

				log.info("Actualizar MSSQL 6 ");

			}

			/**/

		} catch (Exception e) {

			salida = "Imposible generar explosion de ordenes de proudccion.";

			interFacesLogStatusCreate(idtransaccion, "DELTA/BACO: " + salida
					+ " / " + e, "prodINTFGenerarExplosionOP", "ERROR",
					"ordenesProdExplosion.jsp", idempresa, usuarioalt);

			log.error("prodINTFGenerarExplosionOP(): " + e);

		}

		if (!salida.equalsIgnoreCase("OK")) {
			dbconn.rollback();
			sdbconn.rollback();
		} else {
			dbconn.commit();
			sdbconn.commit();
		}

		dbconn.setAutoCommit(true);
		sdbconn.setAutoCommit(true);

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

	public List getProduccionMovProduPK(BigDecimal idop, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT mp.idop,mp.idesquema,ec.esquema,mp.idcliente,mp.cantre_op,mp.cantest_op,mp.fecha_prometida,"
				+ "       mp.fecha_emision,mp.observaciones,mp.codigo_st,sk.descrip_st,mp.idcontador,mp.nrointerno,ed.codigo_dt,"
				+ "       mp.usuarioalt,mp.usuarioact,mp.fechaalt,mp.fechaact "
				+ "  FROM produccionmovprodu mp  "
				+ "       INNER JOIN produccionesquemas_cabe ec ON mp.idesquema = ec.idesquema AND mp.idempresa = ec.idempresa "
				+ "       INNER JOIN produccionesquemas_deta ed ON ec.idesquema = ed.idesquema AND ec.idempresa = ed.idempresa AND ed.entsal = 'P' and ed.tipo = 'A'"
				+ "       INNER JOIN stockstock sk ON ed.codigo_st = sk.codigo_st AND ed.idempresa = sk.idempresa "
				+ " WHERE mp.idop=" + idop.toString() + " AND mp.idempresa = "
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
					.error("Error SQL en el metodo : getProduccionMovProduPK( BigDecimal idop, BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getProduccionMovProduPK( BigDecimal idop, BigDecimal idempresa )  "
							+ ex);
		}
		return vecSalida;
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

	/**
	 * Metodo:getEstadoStockEsquema Autor: EJV. DATE:20060221.
	 * Descripcion:Metodo de validacion de existencias en stock de articulos que
	 * conforman un esquema. El mismo tiene impacto en la 'EXPLOSION' de una
	 * orden de produccion.
	 * 
	 */

	public Object getEstadoStockEsquema(BigDecimal idesquema,
			BigDecimal cantidad, int nivel, BigDecimal idempresa)
			throws EJBException {
		String salida = "";
		Object obj = null;
		List listaEsquema = new ArrayList();
		Iterator iterEsquema;
		try {

			listaEsquema = getRecursividadEsquema(idesquema, cantidad, nivel,
					idempresa);
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
	 * Metodos para la entidad: vCalculoNecesidadTotales Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Mon Feb 19 17:09:56 GMT-03:00 2007.
	 * Descripcion: devuelve para un esquema todos los items que lo componen,
	 * recuperando de forma anidada los datos que correspondan a esquemas que
	 * compongan al esquema en cuestion.
	 */

	public List getRecursividadEsquema(BigDecimal idesquema,
			BigDecimal cantidad, int nivel, BigDecimal idempresa)
			throws EJBException {
		List auxList = new ArrayList();
		List retornoList = new ArrayList();
		List recursivaList = new ArrayList();
		Iterator auxIter;
		try {
			auxList = getVCalculoNecesidadTotalesPK(idesquema, cantidad,
					new BigDecimal(nivel), idempresa);
			auxIter = auxList.iterator();
			while (auxIter.hasNext()) {
				String[] datos = (String[]) auxIter.next();
				retornoList.add(datos);
				if (datos[4].equalsIgnoreCase("E")) {
					recursivaList = getRecursividadEsquema(new BigDecimal(
							datos[2]), cantidad, ++nivel, idempresa);
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
	 * Metodos para la entidad: interfacesOpRegalos Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Thu Dec 04 11:20:14 GMT-03:00 2008
	 * 
	 */

	public String interfacesOpRegalosCreate(BigDecimal idop,
			BigDecimal idempresa, String usuarioalt) throws EJBException {
		String salida = "OK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
		// 2. sin nada desde la pagina

		// fin validaciones

		try {
			if (salida.equalsIgnoreCase("OK")) {
				String ins = "INSERT INTO INTERFACESOPREGALOS(idop, idempresa, usuarioalt ) VALUES (?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, idop);
				insert.setBigDecimal(2, idempresa);
				insert.setString(3, usuarioalt);
				int n = insert.executeUpdate();
				if (n != 1)
					salida = "Imposible generar OP REGALOS - Interfaces";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String interfacesOpRegalosCreate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String interfacesOpRegalosCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// -----------------------------------------------------------------------

	public String[] callINTFAnulaOrdenProduccion(BigDecimal idop,
			BigDecimal idesquema, String codigo_st, BigDecimal codigo_dt,
			BigDecimal cantidad, BigDecimal idmotivodesarma,
			String observaciones, int recursivo, int ejercicioactivo,
			BigDecimal idcontadorcomprobante, String sistema_ms,
			BigDecimal idempresa, String usuarioalt) throws EJBException,
			SQLException {

		String salida = "";
		String[] resultado = new String[] { "", "" };
		String[] nrosinternos = null;
		BigDecimal idtransacciondesarmado = new BigDecimal(-1);
		/*
		 * **********************************************************************
		 * ******** HARCODE AREA = 62 / "REGALOS PARA ARMADO DE ORDENES "
		 * *******
		 * ***************************************************************
		 * ********
		 */
		BigDecimal idarea = new BigDecimal(62);

		String param = "";
		ResultSet rs = null;
		BigDecimal idusuariobaco = new BigDecimal(0);
		BigDecimal ciom = new BigDecimal(0);

		BigDecimal idtransaccion = new BigDecimal(-1);
		String status = "OK";

		idtransaccion = GeneralBean.getNextValorSequencia("seq_loginterfaces",
				dbconn);
		dbconn.setAutoCommit(false);
		sdbconn.setAutoCommit(false);

		String descripcionLog = " idop:" + idop + "-  idesquema:" + idesquema
				+ "-  codigo_st:" + codigo_st + "-  codigo_dt:" + codigo_dt
				+ "-  cantidad:" + cantidad + "-  idmotivodesarma:"
				+ idmotivodesarma + "-  observaciones:" + observaciones
				+ "-  recursivo:" + recursivo + "-  ejercicioactivo:"
				+ ejercicioactivo + "-  idcontadorcomprobante:"
				+ idcontadorcomprobante + "-  sistema_ms:" + sistema_ms
				+ "-  idempresa:" + idempresa + "-  usuarioalt:" + usuarioalt;

		try {

			/*---------------------------------------------------------*/

			param = "'Z', NULL, NULL, NULL, NULL, '" + usuarioalt + "', NULL";
			rs = getTransaccion("spUsuarios", param);

			if (rs != null && rs.next()) {

				idusuariobaco = rs.getBigDecimal(2);

				rs = getTransaccion("spInterfacesVProductosXiomOne", "'"
						+ codigo_st + "'");

				if (rs != null && rs.next()) {

					ciom = rs.getBigDecimal(2);

					param = ciom + ", " + cantidad + ", " + idarea + ", "
							+ idusuariobaco + ", " + idmotivodesarma;

					rs = getTransaccion("spInterfacesImpdescomMix ", param);

					if (rs != null && rs.next()) {

						salida = resultado[0] = rs.getString(1);

					} else {
						salida = resultado[0] = "BACO-Error inesperado al desarmar en sistema BACO.";
						resultado[1] = "-1";
					}
				} else {
					salida = resultado[0] = "BACO-Imposible recuperas CIOM para articulo:  "
							+ codigo_st;
					resultado[1] = "-1";
				}
			} else {
				salida = resultado[0] = "BACO-Imposible recuperar usuario :  "
						+ usuarioalt;
				resultado[1] = "-1";
			}

			status = salida.equalsIgnoreCase("OK") ? "OK" : "ERROR";
			interFacesLogStatusCreate(idtransaccion, "BACO: " + salida + " / "
					+ param + " / " + descripcionLog,
					"callINTFAnulaOrdenProduccion", status,
					"ordenesProdAnular.jsp", idempresa, usuarioalt);

			/*---------------------------------------------------------*/

			if (salida.equalsIgnoreCase("OK")) {

				if (usuarioalt.length() > 17)
					usuarioalt = usuarioalt.substring(0, 16);
				if (usuarioalt.indexOf(":IF") == -1)
					usuarioalt += ":IF";

				salida = ProduccionBean.produccionMovProduAnulaOp(idop,
						idempresa, usuarioalt, dbconn);

				if (salida.equalsIgnoreCase("OK")) {

					if (cantidad.longValue() > 0) {

						resultado = stockMovDesarmadoProductosEsquema(
								idesquema, codigo_st, codigo_dt, cantidad,
								idmotivodesarma, observaciones, recursivo,
								ejercicioactivo, idcontadorcomprobante,
								sistema_ms, 1, idempresa, usuarioalt);

						salida = resultado[0];
						nrosinternos = resultado[1].split("-");

						if (salida.equalsIgnoreCase("OK")) {

							idtransacciondesarmado = GeneralBean
									.getNextValorSequencia(
											"seq_idtransacciondesarmado",
											dbconn);

							stockDesarmadoLogCreate(idtransacciondesarmado,
									idesquema, nrosinternos, recursivo,
									cantidad, idmotivodesarma, idempresa,
									usuarioalt);
						}

					} else {
						resultado = new String[] { salida, "-1" };
					}

				} else {
					resultado = new String[] { salida, "-1" };
				}

				status = salida.equalsIgnoreCase("OK") ? "OK" : "ERROR";
				interFacesLogStatusCreate(idtransaccion, "DELTA: " + salida
						+ " / " + descripcionLog,
						"callINTFAnulaOrdenProduccion", status,
						"ordenesProdAnular.jsp", idempresa, usuarioalt);

			}

		} catch (Exception e) {
			interFacesLogStatusCreate(idtransaccion, "DELTA/BACO: " + e + " / "
					+ descripcionLog, "callINTFAnulaOrdenProduccion", "ERROR",
					"ordenesProdAnular.jsp", idempresa, usuarioalt);
			salida = "Error inesperado en callINTFAnulaOrdenProduccion.";
			log.error("callINTFAnulaOrdenProduccion: " + e);
		}

		if (!salida.equalsIgnoreCase("OK")) {
			dbconn.rollback();
			sdbconn.rollback();
		} else {
			dbconn.commit();
			sdbconn.commit();
		}

		dbconn.setAutoCommit(true);
		sdbconn.setAutoCommit(true);

		return resultado;
	}

	public List getClientesDomiciliosPK(BigDecimal iddomicilio,
			BigDecimal idempresa, Connection pgconn) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT d.iddomicilio,d.idcliente,d.esdefault,d.calle,d.nro,d.piso,d.depto,l.idlocalidad,l.localidad,"
				+ "       d.cpa,l.cpostal,d.contacto,d.cargocontacto,d.telefonos,d.celular,d.fax,d.web,p.idprovincia, p.provincia,"
				+ "       COALESCE(l.idlocalidadbaco, 0) AS idlocalidadbaco, "
				+ "       COALESCE(ax.idexpresobaco, 0) AS idexpresobaco, "
				+ "       COALESCE(ax.iddistribuidorbaco, 0) AS iddistribuidorbaco,"
				+ "       d.idempresa,d.usuarioalt,d.usuarioact,d.fechaalt,d.fechaact"
				+ "  FROM clientesdomicilios d "
				+ "        INNER JOIN clientesanexolocalidades ax ON d.idanexolocalidad = ax.idanexolocalidad "
				+ "        INNER JOIN globallocalidades l ON ax.idlocalidad = l.idlocalidad "
				+ "        INNER JOIN globalprovincias p ON l.idprovincia = p.idprovincia "
				+ " WHERE d.iddomicilio=" + iddomicilio.toString()
				+ "   AND d.idempresa = " + idempresa.toString() + ";";
		List vecSalida = new ArrayList();
		try {

			Statement statement = pgconn.createStatement();
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

			closeResultset(rsSalida);

		} catch (SQLException sqlException) {
			log
					.error("Error SQL en el metodo : getClientesDomiciliosPK( BigDecimal iddomicilio ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getClientesDomiciliosPK( BigDecimal iddomicilio )  "
							+ ex);
		}
		return vecSalida;
	}

	public String InterFacesGenerarPedido(BigDecimal idpedidoDelta,
			BigDecimal idcampacabeDelta, BigDecimal idcliente,
			BigDecimal idsucuclie, Timestamp fechapedido,
			BigDecimal idcondicion, String obsarmado, String obsentrega,
			BigDecimal totaliva, BigDecimal idprioridad, BigDecimal idzona,
			BigDecimal idtarjeta, BigDecimal cuotas, String origenpedido,
			BigDecimal total, BigDecimal cotizacion, Hashtable htArticulos,
			String usuarioalt, BigDecimal idempresa, Properties props)
			throws EJBException, SQLException {

		String salida = "OK";
		String param = "";
		String sp = "";
		BigDecimal idcampaniaBaco = new BigDecimal(-1);
		BigDecimal idpedidoBaco = new BigDecimal(-1);
		ResultSet rsCabeceraBaco = null;
		ResultSet rsDetalleBaco = null;
		Iterator iter;
		BigDecimal idtransaccion = null;
		// Properties props = new Properties();
		Connection mssqlConn = null;
		Connection pgConn = null;
		log.info("INICIA GENERAR PEDIDO EN BACO --- >");

		try {

			// props.load(BCBean.class.getResourceAsStream("system.properties"));
			// conexion al sistema historico
			String msurl = props.getProperty("sconn.url").trim();
			String msclase = props.getProperty("sconn.clase").trim();
			String susuario = props.getProperty("sconn.usuario").trim();
			String sclave = props.getProperty("sconn.clave").trim();
			Class.forName(msclase);
			mssqlConn = DriverManager.getConnection(msurl, susuario, sclave);

			String pgurl = props.getProperty("conn.url").trim();
			String pgclase = props.getProperty("conn.clase").trim();
			String pgusuario = props.getProperty("conn.usuario").trim();
			String pgclave = props.getProperty("conn.clave").trim();
			Class.forName(pgclase);
			pgConn = DriverManager.getConnection(pgurl, pgusuario, pgclave);

			// log.info("pgConn: " + pgConn);

			if (mssqlConn == null)
				throw new Exception(
						"No fue posible crear una conexion a MSSQL.");
			if (pgConn == null)
				throw new Exception(
						"No fue posible crear una conexion a POSTGRES.");

			mssqlConn.setAutoCommit(false);

			idtransaccion = GeneralBean.getNextValorSequencia(
					"seq_loginterfaces", pgConn);

			idcampaniaBaco = getSetCampaniaActivaBaco(idempresa, usuarioalt,
					mssqlConn, pgConn);

			String entregara = "";
			int idmesentrega = 0;
			String obsremito = "";
			String obscobranza = "";
			String obsproducto = "";
			String usuario = "";
			BigDecimal idprovincia = new BigDecimal(0);
			BigDecimal idlocalidad = null;
			BigDecimal cexpreso = null;
			BigDecimal cdistribuidor = null;
			String cpostal = "0";
			BigDecimal totalbruto = new BigDecimal(0);
			double descuento = 0.00;
			BigDecimal totalgeneral = new BigDecimal(0);
			String entregaren = "";
			BigDecimal corden = cotizacion != null
					&& cotizacion.longValue() > 1 ? cotizacion : null;

			Calendar cal = new GregorianCalendar();
			Enumeration en = htArticulos.keys();

			if (cuotas != null && cuotas.intValue() > 1) {
				obscobranza += " TOTAL DE CUOTAS: " + cuotas;
			}

			iter = getClientesDomiciliosPK(idsucuclie, idempresa, pgConn)
					.iterator();

			if (iter != null && iter.hasNext()) {

				cal.setTimeInMillis(fechapedido.getTime());

				String[] datos = (String[]) iter.next();

				// for(int x=0;x<datos.length;x++) log.info(" datos["+x+"]:"
				// +datos[x]);

				// 20090925 - EJV
				// Se comenta para que el sp ejecutado en baco asigne expreso y
				// distribuidor
				// entregara = datos[11];

				// 20091102 - EJV
				// MANTIS: 448
				entregara = datos[11];
				idmesentrega = cal.get(Calendar.MONTH) + 1;
				obsremito = obsentrega;
				// obscobranza = "";
				obsproducto = obsarmado;
				usuario = usuarioalt;

				// idprovincia = new BigDecimal(datos[17]);

				// Datos recuperados desde Delta
				idlocalidad = new BigDecimal(datos[19]);
				cexpreso = new BigDecimal(datos[20]);
				cdistribuidor = new BigDecimal(datos[21]);

				cpostal = datos[10] == null ? "0" : datos[10];
				totalbruto = total;
				descuento = 0.0;
				totalgeneral = totaliva;

				// EJV - 20091104 / MANTIS 448
				// entregaren = datos[3] + " " + datos[4] + " " + datos[5] + " "
				// + datos[6] + " " + datos[8];

				// d.calle, d.nro, d.piso, d.depto

				// EJV - 20091116 / MANTIS 459

				entregaren = datos[3] + "  ";
				entregaren += (datos[4] != null && !datos[4].trim().equals("")
						&& !datos[4].trim().equals("0") ? datos[4] : "")
						+ " ";
				entregaren += datos[5] != null && !datos[5].trim().equals("") ? "PISO "
						+ datos[5] + " "
						: " ";
				entregaren += datos[6] != null && !datos[6].trim().equals("") ? "DEP "
						+ datos[6] + " "
						: " ";

				sp = "splocalidades";
				param = " 'L', null, " + idlocalidad;

				ResultSet rsAux = getTransaccion(sp, param, mssqlConn);

				if (rsAux != null && rsAux.next()) {

					idprovincia = rsAux.getBigDecimal("cprovincia");

				}

				closeResultset(rsAux);

				/**
				 * 
				 * INICIA PEDIDO POS TELEMARKETING
				 * 
				 * */

				if (idcampacabeDelta != null
						&& idcampacabeDelta.longValue() > 0) {

					log.info(" *** MOVIMIENTO X TMK *** ");

					// param = "'I', NULL, "+idcampania+","+ idsocio +
					// ",null, '"+entregara+"',"+idmesentrega+",'"+obsremito+"','"+obscobranza+"','"+obsproducto+"','"
					// + usuario + "',NULL,"+idprovincia+","+idlocalidad+","+
					// totbruto +
					// ","+ descuento + "," + totgeneral+",'"+entregaren+"'";
					// sptmmovpedicabe I

					sp = "sptmmovpedicabe";
					param = "'I', NULL, " + idcampaniaBaco + ", " + idcliente
							+ ", NULL , '" + entregara + "', " + idmesentrega
							+ ", '" + obsremito + "',  '" + obscobranza
							+ "', '" + obsproducto + "', '" + usuario
							+ "', NULL, " + idprovincia + ", " + idlocalidad
							+ ", " + totalbruto + ", " + descuento + ", "
							+ totalgeneral + ", '" + entregaren + "', "
							+ idpedidoDelta;

					log.info(" * TM - CABECERA INS * ");
					log.info("sp   : " + sp);
					log.info("param: " + param);

					rsCabeceraBaco = getTransaccion(sp, param, mssqlConn);

					if (rsCabeceraBaco != null && rsCabeceraBaco.next()) {

						// -------------------------------------------------------------------------------------------------
						// GRABAR DETALLE DE PEDIDO
						// TODO: ver que viene en caso de error ...

						idpedidoBaco = rsCabeceraBaco.getBigDecimal(1);

						while (en.hasMoreElements()) {

							String key = (String) en.nextElement();
							String[] datosArticulo = (String[]) htArticulos
									.get(key);
							String codigo_st = datosArticulo[0];
							BigDecimal cantidad = new BigDecimal(
									datosArticulo[10]);
							BigDecimal porcdesc_apli = new BigDecimal(
									datosArticulo[20]);

							// EJV - 20091119 - MANTIS 468 --- >

							BigDecimal precio = new BigDecimal(datosArticulo[5])
									.setScale(3, BigDecimal.ROUND_CEILING);
							String gravadoExento = datosArticulo[25];
							BigDecimal ivaAplicado = new BigDecimal(
									datosArticulo[26]).setScale(3,
									BigDecimal.ROUND_CEILING);

							BigDecimal precioUnitarioConIvaDelta = precio;
							BigDecimal factor = new BigDecimal(1 + (ivaAplicado
									.doubleValue() / 100)).setScale(3,
									BigDecimal.ROUND_CEILING);

							// TODO: PENDIENTE - Posiblemente (casi confirmado)
							// que NO aplica
							// if (gravadoExento.equalsIgnoreCase("G")) {

							precioUnitarioConIvaDelta = (precio
									.multiply(factor)).setScale(3,
									BigDecimal.ROUND_CEILING);
							// precioUnitarioConIvaDelta.setScale(3,
							// BigDecimal.ROUND_CEILING);

							// }

							// < ---

							sp = "spInterfacesVProductosXiomOne";
							param = "'" + codigo_st + "'";

							ResultSet rsTipo = getTransaccion(sp, param,
									mssqlConn);
							String tipo = "";

							if (rsTipo != null) {
								if (rsTipo.next()) {
									tipo = rsTipo.getString("tipo");

									// param = "'I', NULL, "+idPediCabe+",'"+
									// rsFrmCampa.getString(2) +
									// "',"+ var +",'" + usuario + "',null," +
									// descuento
									// + "," +
									// idmesentrega ;
									// sptmmovpedideta I

									sp = "sptmmovpedideta";
									param = "'I', NULL, " + idpedidoBaco
											+ ", '" + tipo + "-" + codigo_st
											+ "', " + cantidad + ",'"
											+ usuarioalt + "', NULL, "
											+ porcdesc_apli + ", "
											+ idmesentrega + ", "
											+ precioUnitarioConIvaDelta;
									rsDetalleBaco = getTransaccion(sp, param,
											mssqlConn);

									log.info(" * TM - DETALLE * ");
									log.info("sp   : " + sp);
									log.info("param: " + param);

									if (rsDetalleBaco != null
											&& rsDetalleBaco.next()) {

										closeResultset(rsDetalleBaco);
										closeResultset(rsCabeceraBaco);
										closeResultset(rsTipo);

									} else {
										salida = "Error(102): Imposible ejecutar detalle sptmmovpedideta para: articulo - "
												+ codigo_st
												+ " / pedido delta: "
												+ idpedidoDelta
												+ " / pedido baco: "
												+ idpedidoBaco;
										log.info(salida);
										break;
									}

								} else {
									salida = "Error(101): Imposible recuperar tipo para articulo - "
											+ codigo_st
											+ " / pedido delta: "
											+ idpedidoDelta
											+ " / pedido baco: " + idpedidoBaco;
									log.info(salida);
									break;
								}

							} else {
								salida = "Error(100): Imposible recuperar tipo para articulo - "
										+ codigo_st
										+ " / pedido delta: "
										+ idpedidoDelta
										+ " / pedido baco: "
										+ idpedidoBaco;
								log.info(salida);
								break;
							}

						}

						// TODO: Preguntar si grabo detalle sin error.

						// -------------------------------------------------------------------------------------------------
						// param =
						// "'U', "+idPediCabe+",NULL ,NULL, NULL,'"+entregara+"',"+idmesentrega+",'"+obsremito+"','"+obscobranza+"','"+obsproducto+"',NULL,'"
						// + usuario + "',"+idprovincia+","+idlocalidad+","+
						// toti +
						// ","+
						// descuento + "," + granTotal+",'"+entregaren+"'";
						// sptmmovpedicabe U
						// -------------------------------------------------------------------------------------------------

						if (salida.equalsIgnoreCase("OK")) {

							sp = "sptmmovpedicabe";
							param = "'U'," + idpedidoBaco
									+ ", NULL, NULL, NULL, '" + entregara
									+ "', " + idmesentrega + ", '" + obsremito
									+ "',  '" + obscobranza + "', '"
									+ obsproducto + "', NULL, '" + usuario
									+ "', " + idprovincia + ", " + idlocalidad
									+ ", " + totalbruto + ", " + descuento
									+ "," + totalgeneral + ", '" + entregaren
									+ "', NULL, " + cdistribuidor + ", "
									+ cexpreso + ", " + cpostal;

							log.info(" * TM - CABECERA UPD * ");
							log.info("sp   : " + sp);
							log.info("param: " + param);

							rsCabeceraBaco = getTransaccion(sp, param,
									mssqlConn);

							if (rsCabeceraBaco != null && rsCabeceraBaco.next()) {

								closeResultset(rsCabeceraBaco);

								log.info(salida);

							}

						}

					} else {

						salida = "Error(100): Imposible generar cabecera para pedido DELTA - "
								+ idpedidoDelta
								+ " / pedido baco: "
								+ idpedidoBaco;
						log.info(salida);
					}

					/**
					 * 
					 * FIN PEDIDOS POR TELEMARKETING.
					 * 
					 * */

				} else {

					/**
					 * 
					 * INICIA PEDIDO POS OTRO AREA - SAS
					 * 
					 * */

					log.info(" *** MOVIMIENTO X SAS *** ");

					Hashtable htMeses = new Hashtable();

					ResultSet rsAuxiliar = getTransaccion("spglobalmeses",
							"'A'", mssqlConn);

					while (rsAuxiliar.next()) {
						htMeses.put(rsAuxiliar.getString("codigo"), rsAuxiliar
								.getObject("descripcion"));

					}

					closeResultset(rsAuxiliar);

					log.info(" *** RECORRER DETALLE *** ");
					while (en.hasMoreElements()) {

						String key = (String) en.nextElement();
						String[] datosArticulo = (String[]) htArticulos
								.get(key);
						String codigo_st = datosArticulo[0];
						BigDecimal cantidad = new BigDecimal(datosArticulo[10]);
						BigDecimal porcdesc_apli = new BigDecimal(
								datosArticulo[20]);
						String tipo = "";
						BigDecimal totalconiva = (new BigDecimal(
								datosArticulo[11])).add(new BigDecimal(
								datosArticulo[27]));
						int ciom = -1;
						// Posibles valores A - D - N
						// Harcode en N por el momento
						String xadicodesc = porcdesc_apli.intValue() > 0 ? "D"
								: "N";

						sp = "spInterfacesVProductosXiomOne";
						param = "'" + codigo_st + "'";
						ResultSet rsTipo = getTransaccion(sp, param, mssqlConn);

						log.info(" * SAS: Recuperar tipo * ");
						log.info("sp   : " + sp);
						log.info("param: " + param);

						if (rsTipo != null) {
							if (rsTipo.next()) {
								tipo = rsTipo.getString("tipo");
								ciom = rsTipo.getInt("ciom");

								// Harcode transferencia desde STOCK DISPONIBLE
								// a STOCK EN ARMADO
								int areaorigen = 3;
								int areadestino = 4;

								sp = "spUsuarios";
								param = "'Z', NULL, NULL, NULL, NULL, '"
										+ usuarioalt.replaceAll(":IF", "")
										+ "', NULL";
								BigDecimal idusuariobaco = new BigDecimal(0);
								rsAuxiliar = getTransaccion(sp, param,
										mssqlConn);

								if (rsAuxiliar != null && rsAuxiliar.next()) {

									idusuariobaco = rsAuxiliar.getBigDecimal(2);
									closeResultset(rsAuxiliar);

								}

								sp = "spInterfacesTransferenciasAreas";
								param = "'" + codigo_st + "', " + cantidad
										+ ", " + areaorigen + ", "
										+ areadestino + "," + idusuariobaco;

								log.info(" * SAS: Transferencia * ");
								log.info("sp   : " + sp);
								log.info("param: " + param);

								ResultSet rsTransferencia = getTransaccion(sp,
										param, mssqlConn);

								// **
								if (rsTransferencia != null
										&& rsTransferencia.next()) {

									String resultado = rsTransferencia
											.getString(1);

									// log.info("cpostal: " + cpostal);

									if (resultado.equalsIgnoreCase("OK")) {

										sp = "sptelemark_ingreso_ventas";
										param = "'I', NULL, "
												+ idcampaniaBaco
												+ ", "
												+ idcliente
												+ ", '"
												+ tipo
												+ "', "
												+ ciom
												+ ", "
												+ cantidad
												+ ", '"
												+ xadicodesc
												+ "', "
												+ porcdesc_apli
												+ ", "
												+ totalconiva
												+ ", '"
												+ entregara
												+ "', '"
												+ entregaren
												+ "', "
												+ idlocalidad
												+ ", '"
												+ htMeses
														.get(idmesentrega + "")
												+ "', '" + obsentrega + "', '"
												+ obscobranza + "', '"
												+ obsarmado + "'," + corden
												+ " , 0, '" + idusuariobaco
												+ "', " + cpostal + ", "
												+ idpedidoDelta + ", "
												+ cdistribuidor + ", "
												+ cexpreso;

										log
												.info(" * SAS: INGRESO VENTAS TELEMARK * ");
										log.info("sp   : " + sp);
										log.info("param: " + param);

										rsDetalleBaco = getTransaccion(sp,
												param, mssqlConn);

										if (rsDetalleBaco != null
												&& rsDetalleBaco.next()) {

											sp = "spjobtelemark";
											param = "";

											log
													.info(" * SAS: Generara ingreso ventas * ");
											log.info("sp   : " + sp);
											log.info("param: " + param);

											rsDetalleBaco = getTransaccion(sp,
													param, mssqlConn);

											if (rsDetalleBaco != null
													&& rsDetalleBaco.next()) {

												log.info("@@ERROR: "
														+ rsDetalleBaco
																.getString(1));

												closeResultset(rsDetalleBaco);
												closeResultset(rsTransferencia);
												closeResultset(rsCabeceraBaco);

											} else {
												salida = "Error(6): ejecutando JOB - Telemark"
														+ " / pedido delta: "
														+ idpedidoDelta
														+ " / pedido baco: "
														+ idpedidoBaco;
												log.info(salida);
												break;
											}

										} else {
											salida = "Error(5): No fue posible generar movimiento en TELEMARK_INGRESO_VENTAS"
													+ " / pedido delta: "
													+ idpedidoDelta;
											log.info(salida);
											break;
										}

										// --
									} else {
										salida = "Error(4): No fue posible generar movimiento de TRANSFERENCIA: "
												+ resultado
												+ " / pedido delta: "
												+ idpedidoDelta
												+ " / pedido baco: "
												+ idpedidoBaco;
										log.info(salida);
										break;
									}

								} else {
									salida = "Error(3): No fue posible generar movimiento de TRANSFERENCIA."
											+ " / pedido delta: "
											+ idpedidoDelta
											+ " / pedido baco: " + idpedidoBaco;
									log.info(salida);
									break;

								}
								// **

							} else {
								salida = "Error(2): No fue posible recuperar tipo para producto: "
										+ codigo_st
										+ " / pedido delta: "
										+ idpedidoDelta
										+ " / pedido baco: "
										+ idpedidoBaco;
								log.info(salida);
								break;
							}

						} else {
							salida = "Error(1): No fue posible recuperar tipo para producto: "
									+ codigo_st
									+ " / pedido delta: "
									+ idpedidoDelta
									+ " / pedido baco: "
									+ idpedidoBaco;
							log.info(salida);
							break;
						}

					}

					/**
					 * 
					 * FIN PEDIDO POR OTRA AREA - SAS
					 * 
					 * */

				}

			} else {
				salida = "Error(0): No fue posible recuperar domicilio para cliente: "
						+ idcliente
						+ " / pedido delta: "
						+ idpedidoDelta
						+ " / pedido baco: " + idpedidoBaco;
				log.warn(salida);
			}

		} catch (Exception e) {

			salida = "ERROR(EX): " + e;
			log.error("InterFacesGenerarPedido : " + e);

		}
		if (salida.equalsIgnoreCase("OK")) {
			interFacesLogStatusCreateStatic(idtransaccion, salida,
					"InterFacesGenerarPedido", "OK",
					"CAPTURA DE PEDIDOS - DELTA / PEDIDO: " + idpedidoDelta
							+ " / PEDIDO BACO: " + idpedidoBaco, idempresa,
					usuarioalt, pgConn);

			// ACA VA COMMMMMMMMMMIIIIIIIIIIIIIITTTTTTTTTTT !!!!

			mssqlConn.commit();

		} else {
			interFacesLogStatusCreateStatic(idtransaccion, salida,
					"InterFacesGenerarPedido", "ERROR",
					"CAPTURA DE PEDIDOS - DELTA / PEDIDO: " + idpedidoDelta
							+ " / PEDIDO BACO: " + idpedidoBaco, idempresa,
					usuarioalt, pgConn);
			mssqlConn.rollback();

		}

		if (mssqlConn != null) {
			mssqlConn.setAutoCommit(true);
			mssqlConn.close();
		}

		if (pgConn != null) {
			pgConn.close();
		}

		log.info("FINALIZA GENERAR PEDIDO EN BACO < ---");

		return salida;

	}

	public static List getPedidosDomiciliosEntregaPK(BigDecimal iddomicilio,
			BigDecimal idempresa, Connection pgconn) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = ""
				+ "SELECT d.iddomicilio,d.idcliente, 'S' AS esdefault,d.calle,d.nro,d.piso,d.depto,l.idlocalidad,l.localidad,"
				+ "       d.cpa,l.cpostal,d.contacto,d.cargocontacto,d.telefonos,d.celular,d.fax,d.web,p.idprovincia, p.provincia,"
				+ "       COALESCE(l.idlocalidadbaco, 0) AS idlocalidadbaco, "
				+ "       COALESCE(ax.idexpresobaco, 0) AS idexpresobaco, "
				+ "       COALESCE(ax.iddistribuidorbaco, 0) AS iddistribuidorbaco,"
				+ "       d.idempresa,d.usuarioalt,d.usuarioact,d.fechaalt,d.fechaact"
				+ "  FROM pedidosdomiciliosentrega d "
				+ "        INNER JOIN clientesanexolocalidades ax ON d.idanexolocalidad = ax.idanexolocalidad "
				+ "        INNER JOIN globallocalidades l ON ax.idlocalidad = l.idlocalidad "
				+ "        INNER JOIN globalprovincias p ON l.idprovincia = p.idprovincia "
				+ " WHERE d.iddomicilio=" + iddomicilio.toString()
				+ "   AND d.idempresa = " + idempresa.toString() + ";";
		List vecSalida = new ArrayList();
		try {

			Statement statement = pgconn.createStatement();
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

			closeResultset(rsSalida);

		} catch (SQLException sqlException) {
			log
					.error("Error SQL en el metodo : getPedidosDomiciliosEntregaPK( BigDecimal iddomicilio ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getPedidosDomiciliosEntregaPK( BigDecimal iddomicilio )  "
							+ ex);
		}
		return vecSalida;
	}

	public static String InterFacesGenerarEntregasRegalos(
			BigDecimal idpedidoDelta, BigDecimal idcampacabeDelta,
			BigDecimal idcliente, BigDecimal idsucuclie, Timestamp fechapedido,
			BigDecimal idcondicion, String obsarmado, String obsentrega,
			BigDecimal totaliva, BigDecimal idprioridad, BigDecimal idzona,
			BigDecimal idtarjeta, BigDecimal cuotas, String origenpedido,
			BigDecimal total, BigDecimal cotizacion, Hashtable htArticulos,
			String usuarioalt, BigDecimal idempresa, Properties props)
			throws EJBException, SQLException {

		String salida = "OK";
		String param = "";
		String sp = "";
		BigDecimal idcampaniaBaco = new BigDecimal(-1);
		BigDecimal idpedidoBaco = new BigDecimal(-1);
		ResultSet rsCabeceraBaco = null;
		ResultSet rsDetalleBaco = null;
		Iterator iter;
		BigDecimal idtransaccion = null;
		// Properties props = new Properties();
		Connection mssqlConn = null;
		Connection pgConn = null;
		log.info("INICIA GENERAR ENTREGA REGALOS EN BACO --- >");

		try {

			// props.load(BCBean.class.getResourceAsStream("system.properties"));
			// conexion al sistema historico
			String msurl = props.getProperty("sconn.url").trim();
			String msclase = props.getProperty("sconn.clase").trim();
			String susuario = props.getProperty("sconn.usuario").trim();
			String sclave = props.getProperty("sconn.clave").trim();
			Class.forName(msclase);
			mssqlConn = DriverManager.getConnection(msurl, susuario, sclave);

			String pgurl = props.getProperty("conn.url").trim();
			String pgclase = props.getProperty("conn.clase").trim();
			String pgusuario = props.getProperty("conn.usuario").trim();
			String pgclave = props.getProperty("conn.clave").trim();
			Class.forName(pgclase);
			pgConn = DriverManager.getConnection(pgurl, pgusuario, pgclave);

			// log.info("pgConn: " + pgConn);

			if (mssqlConn == null)
				throw new Exception(
						"No fue posible crear una conexion a MSSQL.");
			if (pgConn == null)
				throw new Exception(
						"No fue posible crear una conexion a POSTGRES.");

			mssqlConn.setAutoCommit(false);

			idtransaccion = GeneralBean.getNextValorSequencia(
					"seq_loginterfaces", pgConn);

			idcampaniaBaco = getSetCampaniaActivaBaco(idempresa, usuarioalt,
					mssqlConn, pgConn);

			String entregara = "";
			int idmesentrega = 0;
			String obsremito = "";
			String obscobranza = "";
			String obsproducto = "";
			String usuario = "";
			BigDecimal idprovincia = new BigDecimal(0);
			BigDecimal idlocalidad = null;
			BigDecimal cexpreso = null;
			BigDecimal cdistribuidor = null;
			String cpostal = "0";
			BigDecimal totalbruto = new BigDecimal(0);
			double descuento = 0.00;
			BigDecimal totalgeneral = new BigDecimal(0);
			String entregaren = "";
			BigDecimal corden = cotizacion != null
					&& cotizacion.longValue() > 1 ? cotizacion : null;

			Calendar cal = new GregorianCalendar();
			Enumeration en = htArticulos.keys();

			if (cuotas != null && cuotas.intValue() > 1) {
				obscobranza += " TOTAL DE CUOTAS: " + cuotas;
			}

			iter = getPedidosDomiciliosEntregaPK(idsucuclie, idempresa, pgConn)
					.iterator();

			if (iter != null && iter.hasNext()) {

				cal.setTimeInMillis(fechapedido.getTime());

				String[] datos = (String[]) iter.next();

				// for(int x=0;x<datos.length;x++) log.info(" datos["+x+"]:"
				// +datos[x]);

				// 20090925 - EJV
				// Se comenta para que el sp ejecutado en baco asigne expreso y
				// distribuidor
				// entregara = datos[11];

				// 20091102 - EJV
				// MANTIS: 448
				entregara = datos[11];
				idmesentrega = cal.get(Calendar.MONTH) + 1;
				obsremito = obsentrega;
				// obscobranza = "";
				obsproducto = obsarmado;
				usuario = usuarioalt;

				// idprovincia = new BigDecimal(datos[17]);

				// Datos recuperados desde Delta
				idlocalidad = new BigDecimal(datos[19]);
				cexpreso = new BigDecimal(datos[20]);
				cdistribuidor = new BigDecimal(datos[21]);

				cpostal = datos[10] == null ? "0" : datos[10];
				totalbruto = total;
				descuento = 0.0;
				totalgeneral = totaliva;

				// EJV - 20091104 / MANTIS 448
				// entregaren = datos[3] + " " + datos[4] + " " + datos[5] + " "
				// + datos[6] + " " + datos[8];

				// d.calle, d.nro, d.piso, d.depto

				// EJV - 20091116 / MANTIS 459

				entregaren = datos[3] + "  ";
				entregaren += (datos[4] != null && !datos[4].trim().equals("")
						&& !datos[4].trim().equals("0") ? datos[4] : "")
						+ " ";
				entregaren += datos[5] != null && !datos[5].trim().equals("") ? "PISO "
						+ datos[5] + " "
						: " ";
				entregaren += datos[6] != null && !datos[6].trim().equals("") ? "DEP "
						+ datos[6] + " "
						: " ";

				sp = "splocalidades";
				param = " 'L', null, " + idlocalidad;

				ResultSet rsAux = getTransaccion(sp, param, mssqlConn);

				if (rsAux != null && rsAux.next()) {

					idprovincia = rsAux.getBigDecimal("cprovincia");

				}

				closeResultset(rsAux);

				/**
				 * 
				 * INICIA ENTREGA POS TELEMARKETING
				 * 
				 * */

				if (idcampacabeDelta != null
						&& idcampacabeDelta.longValue() > 0) {

					/*
					 * 
					 * ######## NO ENTRA NUNCA EN ESTA CONDICION ########
					 */

					/**
					 * 
					 * FIN ENTREGA POR TELEMARKETING.
					 * 
					 * */

				} else {

					/**
					 * 
					 * INICIA ENTREGA POS OTRO AREA - SAS
					 * 
					 * */

					log.info(" *** MOVIMIENTO X SAS *** ");

					Hashtable htMeses = new Hashtable();

					ResultSet rsAuxiliar = getTransaccion("spglobalmeses",
							"'A'", mssqlConn);

					while (rsAuxiliar.next()) {
						htMeses.put(rsAuxiliar.getString("codigo"), rsAuxiliar
								.getObject("descripcion"));

					}

					closeResultset(rsAuxiliar);

					log.info(" *** RECORRER DETALLE *** ");
					while (en.hasMoreElements()) {

						String key = (String) en.nextElement();
						String[] datosArticulo = (String[]) htArticulos
								.get(key);
						String codigo_st = datosArticulo[0];
						BigDecimal cantidad = new BigDecimal(datosArticulo[10]);
						BigDecimal porcdesc_apli = new BigDecimal(
								datosArticulo[20]);
						String tipo = "";
						BigDecimal totalconiva = (new BigDecimal(
								datosArticulo[11])).add(new BigDecimal(
								datosArticulo[27]));
						int ciom = -1;
						// Posibles valores A - D - N
						// Harcode en N por el momento
						String xadicodesc = porcdesc_apli.intValue() > 0 ? "D"
								: "N";

						sp = "spInterfacesVProductosXiomOne";
						param = "'" + codigo_st + "'";
						ResultSet rsTipo = getTransaccion(sp, param, mssqlConn);

						log.info(" * SAS: Recuperar tipo * ");
						log.info("sp   : " + sp);
						log.info("param: " + param);

						if (rsTipo != null) {
							if (rsTipo.next()) {
								tipo = rsTipo.getString("tipo");
								ciom = rsTipo.getInt("ciom");

								// Harcode transferencia desde STOCK DISPONIBLE
								// a STOCK EN ARMADO
								int areaorigen = 3;
								int areadestino = 4;

								sp = "spUsuarios";
								param = "'Z', NULL, NULL, NULL, NULL, '"
										+ usuarioalt.replaceAll(":IF", "")
										+ "', NULL";
								BigDecimal idusuariobaco = new BigDecimal(0);
								rsAuxiliar = getTransaccion(sp, param,
										mssqlConn);

								if (rsAuxiliar != null && rsAuxiliar.next()) {

									idusuariobaco = rsAuxiliar.getBigDecimal(2);
									closeResultset(rsAuxiliar);

								}

								sp = "spInterfacesTransferenciasAreas";
								param = "'" + codigo_st + "', " + cantidad
										+ ", " + areaorigen + ", "
										+ areadestino + "," + idusuariobaco;

								log.info(" * SAS: Transferencia * ");
								log.info("sp   : " + sp);
								log.info("param: " + param);

								ResultSet rsTransferencia = getTransaccion(sp,
										param, mssqlConn);

								// **
								if (rsTransferencia != null
										&& rsTransferencia.next()) {

									String resultado = rsTransferencia
											.getString(1);

									// log.info("cpostal: " + cpostal);

									if (resultado.equalsIgnoreCase("OK")) {

										sp = "sptelemark_ingreso_ventas";
										param = "'I', NULL, "
												+ idcampaniaBaco
												+ ", "
												+ idcliente
												+ ", '"
												+ tipo
												+ "', "
												+ ciom
												+ ", "
												+ cantidad
												+ ", '"
												+ xadicodesc
												+ "', "
												+ porcdesc_apli
												+ ", "
												+ totalconiva
												+ ", '"
												+ entregara
												+ "', '"
												+ entregaren
												+ "', "
												+ idlocalidad
												+ ", '"
												+ htMeses
														.get(idmesentrega + "")
												+ "', '" + obsentrega + "', '"
												+ obscobranza + "', '"
												+ obsarmado + "'," + corden
												+ " , 0, '" + idusuariobaco
												+ "', " + cpostal + ", "
												+ idpedidoDelta + ", "
												+ cdistribuidor + ", "
												+ cexpreso;

										log
												.info(" * SAS: INGRESO VENTAS TELEMARK * ");
										log.info("sp   : " + sp);
										log.info("param: " + param);

										rsDetalleBaco = getTransaccion(sp,
												param, mssqlConn);

										if (rsDetalleBaco != null
												&& rsDetalleBaco.next()) {

											sp = "spjobtelemark";
											param = "";

											log
													.info(" * SAS: Generara ingreso ventas * ");
											log.info("sp   : " + sp);
											log.info("param: " + param);

											rsDetalleBaco = getTransaccion(sp,
													param, mssqlConn);

											if (rsDetalleBaco != null
													&& rsDetalleBaco.next()) {

												log.info("@@ERROR: "
														+ rsDetalleBaco
																.getString(1));

												closeResultset(rsDetalleBaco);
												closeResultset(rsTransferencia);
												closeResultset(rsCabeceraBaco);

											} else {
												salida = "Error(6): ejecutando JOB - Telemark"
														+ " / pedido delta: "
														+ idpedidoDelta
														+ " / pedido baco: "
														+ idpedidoBaco;
												log.info(salida);
												break;
											}

										} else {
											salida = "Error(5): No fue posible generar movimiento en TELEMARK_INGRESO_VENTAS"
													+ " / pedido delta: "
													+ idpedidoDelta;
											log.info(salida);
											break;
										}

										// --
									} else {
										salida = "Error(4): No fue posible generar movimiento de TRANSFERENCIA: "
												+ resultado
												+ " / pedido delta: "
												+ idpedidoDelta
												+ " / pedido baco: "
												+ idpedidoBaco;
										log.info(salida);
										break;
									}

								} else {
									salida = "Error(3): No fue posible generar movimiento de TRANSFERENCIA."
											+ " / pedido delta: "
											+ idpedidoDelta
											+ " / pedido baco: " + idpedidoBaco;
									log.info(salida);
									break;

								}
								// **

							} else {
								salida = "Error(2): No fue posible recuperar tipo para producto: "
										+ codigo_st
										+ " / pedido delta: "
										+ idpedidoDelta
										+ " / pedido baco: "
										+ idpedidoBaco;
								log.info(salida);
								break;
							}

						} else {
							salida = "Error(1): No fue posible recuperar tipo para producto: "
									+ codigo_st
									+ " / pedido delta: "
									+ idpedidoDelta
									+ " / pedido baco: "
									+ idpedidoBaco;
							log.info(salida);
							break;
						}

					}

					/**
					 * 
					 * FIN ENTREGA POR OTRA AREA - SAS
					 * 
					 * */

				}

			} else {
				salida = "Error(0): No fue posible recuperar domicilio para cliente: "
						+ idcliente
						+ " / pedido delta: "
						+ idpedidoDelta
						+ " / pedido baco: " + idpedidoBaco;
				log.warn(salida);
			}

		} catch (Exception e) {

			salida = "ERROR(EX): " + e;
			log.error("InterFacesGenerarPedido : " + e);

		}
		if (salida.equalsIgnoreCase("OK")) {
			interFacesLogStatusCreateStatic(idtransaccion, salida,
					"InterFacesGenerarPedido", "OK",
					"GENERACION ENTREGAS - DELTA / PEDIDO: " + idpedidoDelta
							+ " / PEDIDO BACO: " + idpedidoBaco, idempresa,
					usuarioalt, pgConn);

			// ACA VA COMMMMMMMMMMIIIIIIIIIIIIIITTTTTTTTTTT !!!!

			mssqlConn.commit();

		} else {
			interFacesLogStatusCreateStatic(idtransaccion, salida,
					"InterFacesGenerarPedido", "ERROR",
					"GENERACION ENTREGAS - DELTA / PEDIDO: " + idpedidoDelta
							+ " / PEDIDO BACO: " + idpedidoBaco, idempresa,
					usuarioalt, pgConn);
			mssqlConn.rollback();

		}

		if (mssqlConn != null) {
			mssqlConn.setAutoCommit(true);
			mssqlConn.close();
		}

		if (pgConn != null) {
			pgConn.close();
		}

		log.info("FINALIZA GENERAR ENTREGA REGALOS EN BACO < ---");

		return salida;

	}

	public static BigDecimal getSetCampaniaActivaBaco(BigDecimal idempresa,
			String usuarioalt, Connection msconn, Connection pgconn)
			throws EJBException, SQLException {
		BigDecimal idcampaniabaco = new BigDecimal(-999);

		ResultSet rsCampaniaDelta = null;
		ResultSet rsCampaniaBaco = null;
		Statement stms = msconn.createStatement();
		Statement stpg = pgconn.createStatement();
		String msjLog = "";

		try {

			// SQL: Recupera campa�a activa BACO
			String cQuery = ""
					+ "SELECT idcabecampa, cabecampa, fdesde, fhasta, descuento, total"
					+ " FROM tmcampacabe "
					+ " WHERE fhasta = (SELECT MAX (fhasta) FROM tmcampacabe) AND fhasta > GETDATE()";
			rsCampaniaDelta = null;
			rsCampaniaBaco = stms.executeQuery(cQuery);

			if (rsCampaniaBaco != null) {

				// Recupera la campa�a activa, si esta no existe la genera desde
				// DELTA.
				if (rsCampaniaBaco.next()) {

					idcampaniabaco = rsCampaniaBaco
							.getBigDecimal("idcabecampa");

				} else {

					// SQL: Recupera campa�a activa DELTA
					cQuery = ""
							+ "SELECT idcampacabe, campacabe, to_char(fdesde::date, 'dd/mm/yyyy') AS fdesde, "
							+ "       to_char(fhasta::date, 'dd/mm/yyyy') AS fhasta, descuento, total, "
							+ "       idempresa, usuarioalt, usuarioact, fechaalt, fechaact "
							+ " FROM bacotmcampacabe "
							+ " WHERE idempresa = "
							+ idempresa
							+ "   AND fhasta::date = (SELECT MAX(fhasta::DATE)  "
							+ "                         FROM bacotmcampacabe "
							+ "                        WHERE fhasta::DATE >= CURRENT_DATE AND idempresa = "
							+ idempresa + "           )";

					rsCampaniaDelta = stpg.executeQuery(cQuery);

					if (rsCampaniaDelta != null) {

						if (rsCampaniaDelta.next()) {

							log.info("rsCampaniaDelta NEXT, existe campania.");

							BigDecimal idcampaniaDelta = rsCampaniaDelta
									.getBigDecimal("idcampacabe");
							String param = "'"
									+ rsCampaniaDelta.getString("campacabe")
									+ " - DELTA" + "', '"
									+ rsCampaniaDelta.getString("fdesde")
									+ "', '"
									+ rsCampaniaDelta.getString("fhasta")
									+ "',"
									+ rsCampaniaDelta.getString("descuento")
									+ "," + rsCampaniaDelta.getString("total")
									+ ",'" + usuarioalt + "', + GETDATE() ";
							// SQL:Genera campa�a activa BACO - cabecera
							cQuery = "INSERT INTO tmcampacabe(cabecampa, fdesde,fhasta,descuento,total,usuarioalt,fechaalt) "
									+ "    VALUES (" + param + ")";

							int f = stms.executeUpdate(cQuery);

							if (f == 1) {
								// SQL: Recupera id de campa�a activa BACO
								cQuery = "SELECT idcabecampa FROM tmcampacabe WHERE cabecampa = '"
										+ rsCampaniaDelta
												.getString("campacabe")
										+ " - DELTA"
										+ "' AND fhasta = '"
										+ rsCampaniaDelta.getString("fhasta")
										+ "'";

								rsCampaniaBaco = stms.executeQuery(cQuery);
								idcampaniabaco = new BigDecimal(-998);
								if (rsCampaniaBaco != null
										&& rsCampaniaBaco.next())
									idcampaniabaco = rsCampaniaBaco
											.getBigDecimal("idcabecampa");
								// SQL: Recupera detalle de campa�a activa DELTA
								cQuery = ""
										+ "SELECT cd.idcampadeta, cd.idcampacabe, cd.codigo_st, st.descrip_st,"
										+ "       cd.observaciones, cd.stock_estimado "
										+ "  FROM bacotmcampadeta cd "
										+ "       INNER JOIN stockstock st ON cd.codigo_st = st.codigo_st AND cd.idempresa = st.idempresa "
										+ " WHERE cd.idcampacabe = "
										+ idcampaniaDelta
										+ " AND cd.idempresa = " + idempresa;

								rsCampaniaDelta = stpg.executeQuery(cQuery);
								while (rsCampaniaDelta.next()) {

									param = ""
											+ idcampaniabaco
											+ ", '"
											+ rsCampaniaDelta
													.getString("codigo_st")
											+ "', '"
											+ rsCampaniaDelta
													.getString("descrip_st")
											+ "', '"
											+ rsCampaniaDelta
													.getString("observaciones")
											+ "', "
											+ rsCampaniaDelta
													.getString("stock_estimado")
											+ ", '" + usuarioalt
											+ "', GETDATE() ";
									// SQL: Genera detalle de campa�a activa
									// BACO
									cQuery = " INSERT INTO tmcampadeta"
											+ "           (idCabeCampa,idProducto,producto,observaciones,stock_estimado,usuarioalt, fechaalt)"
											+ "    VALUES (" + param + ")";

									int j = stms.executeUpdate(cQuery);

									if (j != 1) {
										msjLog = "ERROR: No se pudo GENERAR DETALLE CAMPANIA BACO, COPIA DESDE DELTA.";
										break;
									}

								}

							} else
								msjLog = "ERROR: No se pudo GENERAR CABECERA CAMPANIA BACO, COPIA DESDE DELTA.";

						} else
							msjLog = "ERROR: No existe campania DELTA ACTIVA, RS NO NEXT.";

					} else
						msjLog = "ERROR: No se pudo Recuperar CAMPANIA DELTA, RS NULO.";
				}

			} else
				msjLog = "ERROR: No se pudo Recuperar CAMPANIA BACO, RS NULO.";

		} catch (Exception e) {
			msjLog += msjLog + "/" + e;
			log.error("getSetCampaniaActivaBaco(): " + e);
		}

		if (!msjLog.equals("")) {
			BigDecimal idtransaccion = GeneralBean.getNextValorSequencia(
					"seq_loginterfaces", pgconn);
			interFacesLogStatusCreateStatic(idtransaccion, msjLog,
					"getSetCampaniaActivaBaco()", "ERROR", "PEDIDOS",
					idempresa, usuarioalt, pgconn);
		}

		closeResultset(rsCampaniaBaco);
		closeResultset(rsCampaniaDelta);

		return idcampaniabaco;

	}

	public static String[] interfacesAnularPedidoBaco(
			BigDecimal idpedidocabedelta, String usuarioalt,
			BigDecimal idempresa, Properties props) throws RemoteException,
			SQLException {

		String salida = "OK";
		String alerta = "";
		String sp = "";
		String param = "";
		ResultSet rsAnular;
		Connection mssqlConn = null;

		try {

			String msurl = props.getProperty("sconn.url").trim();
			String msclase = props.getProperty("sconn.clase").trim();
			String susuario = props.getProperty("sconn.usuario").trim();
			String sclave = props.getProperty("sconn.clave").trim();
			Class.forName(msclase);
			mssqlConn = DriverManager.getConnection(msurl, susuario, sclave);
			if (mssqlConn == null)
				throw new Exception(
						"No fue posible crear una conexion a MSSQL.");

			mssqlConn.setAutoCommit(false);

			sp = "spInterfacesAnularPedido";
			param = idpedidocabedelta.toString();

			rsAnular = getTransaccion(sp, param, mssqlConn);

			if (rsAnular != null && rsAnular.next()) {
				salida = rsAnular.getString(1);
				alerta = rsAnular.getString(2);
			} else
				salida = "No fue posible ejecutar metodo MSSQL o el mismo fallo.";

			closeResultset(rsAnular);

		} catch (Exception e) {
			salida = "(EX) Error al anular pedido en BACO: " + e;
			log.error("interfacesAnularPedidoBaco(): " + e);
		}

		if (salida.equalsIgnoreCase("OK"))
			mssqlConn.commit();
		else
			mssqlConn.rollback();

		if (mssqlConn != null) {
			mssqlConn.setAutoCommit(true);
			mssqlConn.close();
		}

		return new String[] { salida, alerta };
	}

	public static String[] interfacesAnularPedidoBacoPreconformacion(
			BigDecimal idpedidocabedelta, String usuarioalt,
			BigDecimal idempresa, Properties props) throws RemoteException,
			SQLException {

		String salida = "OK";
		String alerta = "";
		String sp = "";
		String param = "";
		ResultSet rsAnular;
		Connection mssqlConn = null;
		BigDecimal id_usuario_ctrl = new BigDecimal(-1);

		try {

			String msurl = props.getProperty("sconn.url").trim();
			String msclase = props.getProperty("sconn.clase").trim();
			String susuario = props.getProperty("sconn.usuario").trim();
			String sclave = props.getProperty("sconn.clave").trim();
			Class.forName(msclase);
			mssqlConn = DriverManager.getConnection(msurl, susuario, sclave);
			if (mssqlConn == null)
				throw new Exception(
						"No fue posible crear una conexion a MSSQL.");

			mssqlConn.setAutoCommit(false);

			param = "'Z', NULL, NULL, NULL, NULL, '"
					+ usuarioalt.replaceAll(":IF", "") + "', NULL";
			rsAnular = getTransaccion("spUsuarios", param, mssqlConn);

			if (rsAnular != null && rsAnular.next()) {

				id_usuario_ctrl = rsAnular.getBigDecimal(2);

				sp = "spInterfacesAnularPedidoPreconformacion";
				param = idpedidocabedelta.toString() + ", " + id_usuario_ctrl;

				rsAnular = getTransaccion(sp, param, mssqlConn);

				if (rsAnular != null && rsAnular.next()) {
					salida = rsAnular.getString(1);
					alerta = rsAnular.getString(2);
				} else
					salida = "(P2-InvalidCallSP)No fue posible ejecutar metodo MSSQL o el mismo fallo.";
			} else
				salida = "(P1-InvalidUser)No fue posible ejecutar metodo MSSQL o el mismo fallo.";

			closeResultset(rsAnular);

		} catch (Exception e) {
			salida = "(EX) Error al anular pedido en BACO: " + e;
			log.error("interfacesAnularPedidoBacoPreconformacion(): " + e);
		}

		if (salida.equalsIgnoreCase("OK"))
			mssqlConn.commit();
		else
			mssqlConn.rollback();

		if (mssqlConn != null) {
			mssqlConn.setAutoCommit(true);
			mssqlConn.close();
		}

		return new String[] { salida, alerta };
	}

	public static String interfacesPreconformacionTotalPedidosRemito(
			String xnremito, Properties props) throws EJBException,
			SQLException {

		Connection mssqlConn = null;
		ResultSet rs = null;
		String salida = "0";

		try {

			String msurl = props.getProperty("sconn.url").trim();
			String msclase = props.getProperty("sconn.clase").trim();
			String susuario = props.getProperty("sconn.usuario").trim();
			String sclave = props.getProperty("sconn.clave").trim();
			Class.forName(msclase);
			mssqlConn = DriverManager.getConnection(msurl, susuario, sclave);
			if (mssqlConn == null)
				throw new Exception(
						"No fue posible crear una conexion a MSSQL.");

			Statement stms = mssqlConn.createStatement();
			String cQuery = ""
					+ "SELECT dbo.fuInterfacesAnularPedidoPrecTotalPedidos('"
					+ xnremito + "')";

			rs = stms.executeQuery(cQuery);

			if (rs != null && rs.next()) {
				salida = rs.getString(1);
			}

			log.info("TOTAL PEDIDOS REMITO: " + salida);

		} catch (Exception e) {

			log.error("interfacesPreconformacionTotalPedidosRemito(): " + e);

		}

		closeResultset(rs);
		mssqlConn.close();

		return salida;
	}

	public static void closeResultset(ResultSet rs) {

		try {

			if (rs != null)
				rs.close();

		} catch (SQLException sqle) {

			log.info("(SQLE)closeResultset: " + sqle);

		} catch (Exception e) {

			log.info("(E)closeResultset: " + e);
		}

	}

	// Metodos para indicadores de Marketing
	public List getClientesindicadoresTiposAll(BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM clientesindicadorestipos WHERE idempresa = "
				+ idempresa.toString() + "  ORDER BY 2;";
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
					.error("Error SQL en el metodo : getClientesindicadoresmanualesAll() "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getClientesindicadoresmanualesAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para todo (ordena por el segundo campo por defecto)
	public List getClientesindicadoresmanualesAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idindicador,idtipoindicador,indicador,queryseleccion,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM CLIENTESINDICADORESMANUALES WHERE idempresa = "
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
			log
					.error("Error SQL en el metodo : getClientesindicadoresmanualesAll() "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getClientesindicadoresmanualesAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getClientesindicadoresmanualesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idindicador,idtipoindicador,indicador,queryseleccion,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM CLIENTESINDICADORESMANUALES WHERE (UPPER(IDTIPOINDICADOR) LIKE '%"
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
					.error("Error SQL en el metodo : getClientesindicadoresmanualesOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getClientesindicadoresmanualesOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getClientesindicadoresmanualesPK(BigDecimal idindicador,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idindicador,idtipoindicador,indicador,queryseleccion,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM CLIENTESINDICADORESMANUALES WHERE idindicador="
				+ idindicador.toString()
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
					.error("Error SQL en el metodo : getClientesindicadoresmanualesPK( BigDecimal idindicador ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getClientesindicadoresmanualesPK( BigDecimal idindicador )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String clientesindicadoresmanualesDelete(BigDecimal idindicador,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM CLIENTESINDICADORESMANUALES WHERE idindicador="
				+ idindicador.toString()
				+ " AND idempresa="
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM CLIENTESINDICADORESMANUALES WHERE idindicador="
						+ idindicador.toString().toString()
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
					.error("Error SQL en el metodo : clientesindicadoresmanualesDelete( BigDecimal idindicador, BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: clientesindicadoresmanualesDelete( BigDecimal idindicador, BigDecimal idempresa )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String clientesindicadoresmanualesCreate(BigDecimal idtipoindicador,
			String indicador, String queryseleccion, BigDecimal idempresa,
			String usuarioalt) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idtipoindicador == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipoindicador ";
		if (indicador == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: indicador ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (indicador.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: indicador ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO CLIENTESINDICADORESMANUALES(idtipoindicador, indicador, queryseleccion, idempresa, usuarioalt ) VALUES (?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setBigDecimal(1, idtipoindicador);
				insert.setString(2, indicador);
				insert.setString(3, queryseleccion);
				insert.setBigDecimal(4, idempresa);
				insert.setString(5, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String clientesindicadoresmanualesCreate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String clientesindicadoresmanualesCreate(.....)"
							+ ex);
		}
		return salida;
	}

	public String clientesindicadoresmanualesUpdate(BigDecimal idindicador,
			BigDecimal idtipoindicador, String indicador,
			String queryseleccion, BigDecimal idempresa, String usuarioact)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idindicador == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idindicador ";
		if (idtipoindicador == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipoindicador ";
		if (indicador == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: indicador ";
		if (idempresa == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idempresa ";

		// 2. sin nada desde la pagina
		if (indicador.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: indicador ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM clientesindicadoresmanuales WHERE idindicador = "
					+ idindicador.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE CLIENTESINDICADORESMANUALES SET idtipoindicador=?, indicador=?, queryseleccion=?, idempresa=?, usuarioact=?, fechaact=? WHERE idindicador=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setBigDecimal(1, idtipoindicador);
					insert.setString(2, indicador);
					insert.setString(3, queryseleccion);
					insert.setBigDecimal(4, idempresa);
					insert.setString(5, usuarioact);
					insert.setTimestamp(6, fechaact);
					insert.setBigDecimal(7, idindicador);
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
					.error("Error SQL public String clientesindicadoresmanualesUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String clientesindicadoresmanualesUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * FECHA:20100326..........................................................
	 * AUTOR:EJV...............................................................
	 * MOTIVO:MEJORAR GENERADOR................................................
	 * OBSERVACIONES:ESTOS METODOS PUEDEN SER BORRADOS, SOLO SON PRUEBAS PARA..
	 * .............. MEJORAR EL GENEADOR......................................
	 * 
	 * 
	 * */

	// para todo (ordena por el segundo campo por defecto)
	public List get_borrar_clientesEstadosAll(long limit, long offset,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idestado,estado,fechasn,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM _BORRAR_CLIENTESESTADOS WHERE idempresa = "
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
			log
					.error("Error SQL en el metodo : get_borrar_clientesEstadosAll() "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: get_borrar_clientesEstadosAll()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List get_borrar_clientesEstadosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idestado,estado,fechasn,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM _BORRAR_CLIENTESESTADOS WHERE (UPPER(ESTADO) LIKE '%"
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
					.error("Error SQL en el metodo : get_borrar_clientesEstadosOcu(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: get_borrar_clientesEstadosOcu(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List get_borrar_clientesEstadosPK(BigDecimal idestado,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idestado,estado,fechasn,idempresa,usuarioalt,usuarioact,fechaalt,fechaact FROM _BORRAR_CLIENTESESTADOS WHERE idestado="
				+ idestado.toString()
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
					.error("Error SQL en el metodo : get_borrar_clientesEstadosPK( BigDecimal idestado ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: get_borrar_clientesEstadosPK( BigDecimal idestado )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String _borrar_clientesEstadosDelete(BigDecimal idestado,
			BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM _BORRAR_CLIENTESESTADOS WHERE idestado="
				+ idestado.toString() + " AND idempresa="
				+ idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM _BORRAR_CLIENTESESTADOS WHERE idestado="
						+ idestado.toString().toString() + " AND idempresa="
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : _borrar_clientesEstadosDelete( BigDecimal idestado, BigDecimal idempresa ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: _borrar_clientesEstadosDelete( BigDecimal idestado, BigDecimal idempresa )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String _borrar_clientesEstadosCreate(String estado, String fechasn,
			BigDecimal idempresa, String usuarioalt) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		// 2. sin nada desde la pagina

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO _BORRAR_CLIENTESESTADOS(estado, fechasn, idempresa, usuarioalt ) VALUES (?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, estado);
				insert.setString(2, fechasn);
				insert.setBigDecimal(3, idempresa);
				insert.setString(4, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String _borrar_clientesEstadosCreate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String _borrar_clientesEstadosCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String _borrar_clientesEstadosCreateOrUpdate(BigDecimal idestado,
			String estado, String fechasn, BigDecimal idempresa,
			String usuarioact) throws EJBException {
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
			String cQuery = "SELECT COUNT(*) FROM _borrar_clientesEstados WHERE idestado = "
					+ idestado.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE _BORRAR_CLIENTESESTADOS SET estado=?, fechasn=?, idempresa=?, usuarioact=?, fechaact=? WHERE idestado=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, estado);
					insert.setString(2, fechasn);
					insert.setBigDecimal(3, idempresa);
					insert.setString(4, usuarioact);
					insert.setTimestamp(5, fechaact);
					insert.setBigDecimal(6, idestado);
				} else {
					String ins = "INSERT INTO _BORRAR_CLIENTESESTADOS(estado, fechasn, idempresa, usuarioalt ) VALUES (?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, estado);
					insert.setString(2, fechasn);
					insert.setBigDecimal(3, idempresa);
					insert.setString(4, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String _borrar_clientesEstadosCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String _borrar_clientesEstadosCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String _borrar_clientesEstadosUpdate(BigDecimal idestado,
			String estado, String fechasn, BigDecimal idempresa,
			String usuarioact) throws EJBException {
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
			String cQuery = "SELECT COUNT(*) FROM _borrar_clientesEstados WHERE idestado = "
					+ idestado.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE _BORRAR_CLIENTESESTADOS SET estado=?, fechasn=?, idempresa=?, usuarioact=?, fechaact=? WHERE idestado=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, estado);
					insert.setString(2, fechasn);
					insert.setBigDecimal(3, idempresa);
					insert.setString(4, usuarioact);
					insert.setTimestamp(5, fechaact);
					insert.setBigDecimal(6, idestado);
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
					.error("Error SQL public String _borrar_clientesEstadosUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String _borrar_clientesEstadosUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// ------------------------------------------------------------------------
	// ------------------------------------------------------------------------
	// ------------------------------------------------------------------------

}
