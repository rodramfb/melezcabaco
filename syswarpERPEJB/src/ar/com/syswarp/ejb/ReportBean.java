package ar.com.syswarp.ejb;

import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.ejb.CreateException;
import javax.ejb.Stateless;

import java.sql.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperRunManager;
import org.apache.log4j.*;

//jfreechart
import org.jfree.chart.*;

import org.jfree.data.category.*;
import org.jfree.data.xy.*;
import org.jfree.data.general.*;

import org.jfree.chart.plot.*;
import java.math.*;

/*
 */

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
public class ReportBean implements Report {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The session context */
	private SessionContext context;

	/* conexion a la base de datos */

	private Connection dbconn;;

	private Connection jrdbconn;;

	static Logger log = Logger.getLogger(ReportBean.class);

	private Connection conexion;

	private Properties props;

	private String url;

	private String clase;

	private String usuario;

	private String clave;

	private Connection jrconexion;

	private String jrurl;

	private String jrclase;

	private String jrusuario;

	private String jrclave;

	private String path;

	private String jrpaquete;

	private String jrxmlpath;

	private String clientesRemitosPath;

	private String repositorioDocsPath;

	public ReportBean() {
		super();
		try {
			props = new Properties();
			props.load(ReportBean.class
					.getResourceAsStream("system.properties"));

			url = props.getProperty("rconn.url").trim();
			clase = props.getProperty("rconn.clase").trim();
			usuario = props.getProperty("rconn.usuario").trim();
			clave = props.getProperty("rconn.clave").trim();
			path = props.getProperty("reportes.path");

			// para jasperreport
			jrurl = props.getProperty("conn.url").trim();
			jrclase = props.getProperty("conn.clase").trim();
			jrusuario = props.getProperty("conn.usuario").trim();
			jrclave = props.getProperty("conn.clave").trim();
			jrpaquete = props.getProperty("impresion.paquete").trim();
			jrxmlpath = props.getProperty("impresion.path").trim();

			clientesRemitosPath = props.getProperty("clientes.remitos.path")
					.trim();

			repositorioDocsPath = props.getProperty("repositorio.docs.path")
					.trim();

			Class.forName(clase);
			conexion = DriverManager.getConnection(url, usuario, clave);
			this.dbconn = conexion;

			Class.forName(jrclase);
			jrconexion = DriverManager.getConnection(jrurl, jrusuario, jrclave);
			this.jrdbconn = jrconexion;

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

	public ReportBean(Connection dbconn) {
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

	public SessionContext getSessionContext() throws EJBException {
		return context;
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
	 * Reglas de negocio reporting:
	 * 
	 * @ejb.interface-method view-type = "remote"
	 * 
	 * @throws EJBException
	 *             Thrown if method fails due to system-level error. Entidades
	 *             que intervienen: *
	 */

	public String prueba() {
		return "Hola Bean Reporting";
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

	// -- metodos para la utilizacion de openreports, jasper report ireport
	public byte[] getOpenReport(String reportName, Map parameters) {
		byte[] salida = new byte[0];
		log.info("Inicializa: getOpenReport");
		try {
			ResultSet rsReport = null;
			String librerias = props.getProperty("reportes.librerias").trim();

			System.setProperty("jasper.reports.compile.class.path", librerias
					+ jrpaquete + System.getProperty("path.separator")
					+ librerias);
			System.setProperty("jasper.reports.compile.temp", jrxmlpath);
			JasperCompileManager.compileReportToFile(jrxmlpath + reportName
					+ ".jrxml");
			System.out.println("levantando reporte:...\n");
			File reportFile = new File(jrxmlpath + reportName + ".jasper");
			salida = JasperRunManager.runReportToPdf(reportFile.getPath(),
					parameters, jrdbconn);

		} catch (JRException e) {
			log.error("Error: metodo getOpenReport ..." + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error : metodo getOpenReport ..." + e.getMessage());
		}

		return salida;

	}

	public byte[] getOpenReportImpresiones(String reportName, Map parameters) {
		byte[] salida = new byte[0];
		log.info("Inicializa: getOpenReportImpresiones");
		try {
			// jasperreports-2.0.1.jar
			// jasperreports-1.3.2.jar

			String librerias = props.getProperty("impresion.librerias").trim();

			if (!parameters.containsKey("subreport_dir"))
				parameters.put("subreport_dir", jrxmlpath);

			log.info("path.separator: " + System.getProperty("path.separator"));
			System.setProperty("jasper.reports.compile.class.path", librerias
					+ jrpaquete + System.getProperty("path.separator")
					+ librerias);
			System.setProperty("jasper.reports.compile.temp", jrxmlpath);
			JasperCompileManager.compileReportToFile(jrxmlpath + reportName
					+ ".jrxml");
			log.info("levantando reporte:...");

			File reportFile = new File(jrxmlpath + reportName + ".jasper");

			salida = JasperRunManager.runReportToPdf(reportFile.getPath(),
					parameters, jrdbconn);
			// agregado 30/11/2009
			reportFile.deleteOnExit();

		} catch (JRException e) {
			log.error("JRException: metodo getOpenReportImpresiones ..." + e);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error : metodo getOpenReportImpresiones ..." + e);
		}

		return salida;

	}

	// Escribir archivo en disco

	public String saveByteToFile(byte[] byteToFile, String pathFile)
			throws EJBException {
		String salida = "OK";

		try {

			OutputStream ostream = new FileOutputStream(pathFile);
			ostream.write(byteToFile);
			ostream.close();

		} catch (Exception e) {
			salida = "Error escribiendo archivo en disco: " + pathFile;
			log
					.error("Error saveByteToFile(byte[] byteToFile, String pathFile): "
							+ e);
		}

		return salida;
	}

	// FIN -- metodos para la utilizacion de openreports, jasper report ireport
	// listar todos los reportes
	public List getReportesUsuario(long idusuario) throws EJBException {
		/**
		 * Entidad: Reportes
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer todos los reportes
		 */
		ResultSet rsSalida = null;
		String cQuery = " SELECT DISTINCT r.* " + " FROM usuarios u "
				+ " INNER JOIN grupo_usuarios gu ON u.idusuario = gu.idusuario"
				+ " INNER JOIN grupo_reportes gr ON gu.idgrupo = gr.idgrupo"
				+ " INNER JOIN reportes r ON r.idreporte = gr.idreporte"
				+ " INNER JOIN grupos g ON gu.idgrupo = g.idgrupo"
				+ " WHERE u.idusuario = " + idusuario + " ORDER BY r.reporte";
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

	public List getReportesPK(long idReporte) throws EJBException {
		/**
		 * Entidad: Reportes
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer todos los reportes
		 */
		ResultSet rsSalida = null;
		String cQuery = "Select * from reportes where idreporte = " + idReporte;
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

	public List getParametrosPK(String parametro) throws EJBException {
		/**
		 * Entidad: Reportes
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer todos los reportes
		 */
		ResultSet rsSalida = null;
		String cQuery = "select * from parametros where upper(parametro) = '"
				+ parametro + "'";
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

	// -------------------------------------------------------------------------------------------------
	// resolver una conexion a partir de un datasource. (tengo que devolver un
	// rs asi no reparto clases de db por todo el proyecto).
	public ResultSet getConnectionDS(Integer idDatasource, String qry) {
		Connection cn = null;
		ResultSet rs = null;
		try {
			Statement statement = dbconn.createStatement();
			ResultSet rsSalida = null;
			String cQuery = "Select * from datasources where iddatasource = "
					+ idDatasource.toString();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida != null && rsSalida.next()) {
				String _url = rsSalida.getString("url");
				String _driver = rsSalida.getString("driver");
				String _dbuser = rsSalida.getString("db_user");
				String _dbpass = rsSalida.getString("db_pass");
				log.info("resolviendo conexion: " + _url);
				Class.forName(_driver);
				cn = DriverManager.getConnection(_url, _dbuser, _dbpass);
				Statement st = cn.createStatement();
				rs = st.executeQuery(qry);
			}
		} catch (SQLException sqlException) {
			log.error("Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception: " + ex);
		}
		return rs;
	}

	public boolean hasTablasReportes(Integer idReporte) {
		ResultSet rsSalida = null;
		try {
			Statement statement = dbconn.createStatement();
			String cQuery = "select count(*) as total from  reportes_tablas where idreporte = "
					+ idReporte;
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida != null && rsSalida.next()) {
				int total = rsSalida.getInt("total");
				if (total > 0)
					return true;
			}
			return false;
		} catch (SQLException sqlException) {
			log.error("Error SQL: " + sqlException);
			return false;
		} catch (Exception ex) {
			log.error("Salida por exception: " + ex);
			return false;
		}

	}

	// metodo para resolver las tablas que tengo en un reporte:
	public ResultSet getTablasReportes(Integer idReporte) {
		ResultSet rsSalida = null;
		try {
			Statement statement = dbconn.createStatement();
			String cQuery = "select rt.idreporte,rt.imprime_titulos, r.reporte, r.comentario, t.idtabla, t.tabla, t.query_carga, t.query_consulta, t.iddatasource from  reportes_tablas rt,  reportes r,  tablas t where rt.idreporte = "
					+ idReporte
					+ " and rt.idreporte = r.idreporte and rt.idtabla = t.idtabla order by rt.orden";
			rsSalida = statement.executeQuery(cQuery);
		} catch (SQLException sqlException) {
			log.error("Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception: " + ex);
		}
		return rsSalida;
	}

	// metodo para resolver si tengo graficos en una tabla
	public boolean hasGraficosTablas(Integer idTabla) {
		ResultSet rsSalida = null;
		try {
			Statement statement = dbconn.createStatement();
			String cQuery = "select count(*) as total from tablas_graficos  where  idtabla = "
					+ idTabla;
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida != null && rsSalida.next()) {
				int total = rsSalida.getInt("total");
				if (total > 0)
					return true;
			}
			return false;
		} catch (SQLException sqlException) {
			log.error("Error SQL getGraficosTablas( Integer idTabla ): "
					+ sqlException);
			return false;
		} catch (Exception ex) {
			log
					.error("Salida por exception getGraficosTablas( Integer idTabla ): "
							+ ex);
			return false;
		}

	}

	// metodo para resolver los graficos que tengo en una tabla
	public ResultSet getGraficosTablas(Integer idTabla) {
		ResultSet rsSalida = null;
		try {
			Statement statement = dbconn.createStatement();
			String cQuery = "select tg.idgrafico,tg.idtabla, tg.imprime_titulos, t.tabla,g.grafico,g.query_consulta,g.idtipografico,ttg.tipografico,g.iddatasource, g.query_consulta2, g.query_consulta3 from tablas_graficos tg, tablas t, grafico g, tipo_grafico ttg where   tg.idtabla = "
					+ idTabla
					+ " and tg.idtabla = t.idtabla  and tg.idgrafico = g.idgrafico  and g.idtipografico = ttg.idtipografico order by tg.orden";
			rsSalida = statement.executeQuery(cQuery);
		} catch (SQLException sqlException) {
			log.error("Error SQL getGraficosTablas( Integer idTabla ): "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception getGraficosTablas( Integer idTabla ): "
							+ ex);
		}
		return rsSalida;
	}

	/*
	 * 
	 * query: Select * from parametros where upper(parametro)
	 * IN('#CHASTA#','#CDESDE#') order by orden, idparametro
	 */

	// recibir parametros y devolver una lista ordenada y listo
	public List getParametros(Hashtable parametros) {
		ResultSet rsSalida = null;
		String cQuery = "Select * from parametros ";
		List vecSalida = new ArrayList();
		try {
			Enumeration enume = parametros.keys();
			if (enume.hasMoreElements()) {
				cQuery += " where upper(parametro) IN(";
				while (enume.hasMoreElements()) {
					String parametro = (String) enume.nextElement();
					cQuery += "'" + parametro.toUpperCase() + "'";
					if (enume.hasMoreElements()) {
						cQuery += ",";
					}
				}
				cQuery += ") order by orden, idparametro";
				System.out.println("query: " + cQuery);
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
			}
		} catch (SQLException sqlException) {
			log.error("Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception: " + ex);
		}
		return vecSalida;
	}

	// extraer los parametros que hay en un query y ponerlos dentro de un
	// Hastable
	public Hashtable getParametros(String query) {
		Hashtable ht = new Hashtable();
		try {
			int i = 0;
			while (i < query.length()) {
				String o = query.substring(i, i + 1);
				if (o.equalsIgnoreCase("#")) {
					String parName = "";
					do {
						parName += query.substring(i, i + 1);
						i++;
						o = query.substring(i, i + 1);
					} while (!o.equalsIgnoreCase("#") && i < query.length());
					parName += "#";
					ht.put(parName.toUpperCase(), "");
				}
				i++;
			}
		} catch (Exception ex) {
			log.error("Salida por exception: " + ex);
		}
		return ht;
	}

	// a partir de un hashtable con los parametros ya seteados, devuelve un
	// query conformado.
	public String setQueryParametros(String query, Hashtable parametros) {
		String salida = query;
		String outString = "";
		try {
			Enumeration enume = parametros.keys();
			while (enume.hasMoreElements()) {
				String values = (String) enume.nextElement();
				// tengo que ir a la tabla de parametros a resolver el tipo de
				// datos
				String qry = "select * from parametros where upper(parametro) = '"
						+ values.toUpperCase() + "'";
				Statement statement = dbconn.createStatement();
				ResultSet rsSalida = statement.executeQuery(qry);
				System.out.println("variable path" + path);
				if (rsSalida != null && rsSalida.next()) {
					int tipoParametro = rsSalida.getInt("idtipoparametro");
					String reemplazo = "";
					/**
					 * tipos ---------- 1. query 2. numerico 3. fecha 4.
					 * alfanumerico
					 */
					reemplazo = parametros.get(values).toString().trim();
					if (tipoParametro == 3 || tipoParametro == 4)
						reemplazo = "'"
								+ parametros.get(values).toString().trim()
								+ "'";
					salida = salida.toUpperCase();
					String param = values.toUpperCase().trim();
					Pattern p = Pattern.compile(param);
					Matcher m = p.matcher(salida);
					outString = m.replaceAll(reemplazo);
					salida = outString;
				}
				if (rsSalida != null)
					rsSalida.close();
			}
		} catch (Exception ex) {
			log
					.error("Salida por exception setQueryParametros(String query, Hashtable parametros ): "
							+ ex);
		}
		if (outString.equals(""))
			outString = query;
		log.info("query         : " + query);
		log.info("query depurado: " + outString);
		return outString;
	}

	public List getVariables() throws EJBException {
		/**
		 * se repite del general bean aproposito. Entidad: SetupVariables
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer todas las variables del sistema para
		 *             setearlas en session
		 */
		ResultSet rsSalida = null;
		String cQuery = "Select variable, valor, descripcion, usuarioalt, usuarioact, fechaalt, fechaact from setupvariables";
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

	public String par2var(String parametro) {
		String salida = parametro.toLowerCase();
		salida = salida.substring(2);
		salida = salida.substring(1, salida.length() - 1);
		return salida;
	}

	// rutinas de graficos

	// rutinas de graficos

	public String GenerarGraficosXY(String nombreArchivo,
			String tituloPrincipal, ResultSet rsDatos) {
		/*
		 * Utilidad : Generar graficos Autor : Carlos Enrique Perez Atenti: El
		 * nombre del archivo siempre debe ser jpg los XYSeries deben ser un
		 * query de dos posiciones donde va primero las y y despues las x en
		 * ambos casos los valores deben ser de tipo double. todo: poner
		 * dinamicos la cantidad de posiciones del resultset poder elegir el
		 * tipo de graficos
		 */
		String salida = null;
		try {
			XYSeries series = new XYSeries(tituloPrincipal);
			ResultSetMetaData md = rsDatos.getMetaData();
			while (rsDatos.next()) {
				series.add(rsDatos.getDouble(1), rsDatos.getDouble(2));
			}
			// series.add(1995, 0.5);
			XYDataset dataset = new XYSeriesCollection(series);
			JFreeChart chart = ChartFactory.createXYAreaChart(tituloPrincipal,
					md.getColumnName(1), md.getColumnName(2), dataset,
					org.jfree.chart.plot.PlotOrientation.VERTICAL, true, false,
					false);
			salida = nombreArchivo;
			ChartUtilities.saveChartAsJPEG(new java.io.File(path
					+ nombreArchivo), chart, 320, 240);
		} catch (Exception exc) {
			System.out.println("Error realizando la imagen a disco");
		}

		return salida;
	}

	public String GenerarGraficosBar3D(String nombreArchivo,
			String tituloPrincipal, ResultSet rsDatos) {
		/*
		 * Utilidad : Generar graficos Autor : Carlos Enrique Perez Atenti: El
		 * nombre del archivo siempre debe ser jpg el dataset debe un query de
		 * tres posiciones (double, string, string) todo: poner dinamicos la
		 * cantidad de posiciones del resultset poder elegir el tipo de graficos
		 */
		String salida = null;
		try {
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			ResultSetMetaData md = rsDatos.getMetaData();
			while (rsDatos.next()) {
				dataset.addValue(rsDatos.getDouble(1), rsDatos.getString(2),
						rsDatos.getString(3));
			}
			JFreeChart chart = ChartFactory.createBarChart3D(tituloPrincipal,
					md.getColumnName(3), md.getColumnName(1), dataset,
					org.jfree.chart.plot.PlotOrientation.VERTICAL, false,
					false, false);
			salida = nombreArchivo;

			ChartUtilities.saveChartAsJPEG(new java.io.File(path
					+ nombreArchivo), chart, 320, 240);
		} catch (Exception exc) {
			System.out.println("Error realizando la imagen a disco");
		}

		return salida;
	}

	public String GenerarGraficosBar3D(String nombreArchivo,
			String tituloPrincipal, ResultSet rsDatos, int largo, int alto) {
		/*
		 * Utilidad : Generar graficos Autor : Carlos Enrique Perez Atenti: El
		 * nombre del archivo siempre debe ser jpg el dataset debe un query de
		 * tres posiciones (double, string, string) todo: poner dinamicos la
		 * cantidad de posiciones del resultset poder elegir el tipo de graficos
		 */
		String salida = null;
		try {
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			ResultSetMetaData md = rsDatos.getMetaData();
			while (rsDatos.next()) {
				dataset.addValue(rsDatos.getDouble(1), rsDatos.getString(2),
						rsDatos.getString(3));
			}
			JFreeChart chart = ChartFactory.createBarChart3D(tituloPrincipal,
					md.getColumnName(3), md.getColumnName(1), dataset,
					org.jfree.chart.plot.PlotOrientation.VERTICAL, false,
					false, false);
			salida = nombreArchivo;

			ChartUtilities.saveChartAsJPEG(new java.io.File(path
					+ nombreArchivo), chart, largo, alto);
		} catch (Exception exc) {
			System.out.println("Error realizando la imagen a disco");
		}

		return salida;
	}

	public String GenerarGraficosPie3D(String nombreArchivo,
			String tituloPrincipal, ResultSet rsDatos) {
		/*
		 * Utilidad : Generar graficos Autor : Carlos Enrique Perez Atenti: El
		 * nombre del archivo siempre debe ser jpg el dataset debe un query de
		 * tres posiciones (double, string, string) todo: poner dinamicos la
		 * cantidad de posiciones del resultset poder elegir el tipo de graficos
		 */
		String salida = null;
		try {
			DefaultPieDataset dataset = new DefaultPieDataset();
			while (rsDatos.next()) {
				dataset.setValue(rsDatos.getString(1), new Double(rsDatos
						.getDouble(2)));
			}

			/*
			 * JFreeChart chart = ChartFactory.createPieChart3D(
			 * tituloPrincipal, dataset, true, true, false );
			 * 
			 * PiePlot3D plot = ( PiePlot3D )chart.getPlot();
			 * //plot.setForegroundAlpha( 0.6f );
			 */

			// org.jfree.chart.JFreeChart chart =
			// org.jfree.chart.ChartFactory.createPieChart(tituloPrincipal,
			// dataset, true, true, false);
			JFreeChart chart = ChartFactory.createPieChart(tituloPrincipal,
					dataset, true, true, false);

			PiePlot plot = (PiePlot) chart.getPlot();

			// setSectionPaint(int section, java.awt.Paint paint)

			// plot.setForegroundAlpha(0.3f);
			// plot.setExplodePercent(1, 0.50);
			plot.setStartAngle(45);
			salida = nombreArchivo;

			ChartUtilities.saveChartAsJPEG(new java.io.File(path
					+ nombreArchivo), chart, 320, 240);

		} catch (Exception exc) {
			System.out.println("Error realizando la imagen a disco:" + exc);
		}

		return salida;
	}

	public String GenerarGraficosPie3D(String nombreArchivo,
			String tituloPrincipal, ResultSet rsDatos, int alto, int largo) {
		/*
		 * Utilidad : Generar graficos Autor : Carlos Enrique Perez Atenti: El
		 * nombre del archivo siempre debe ser jpg el dataset debe un query de
		 * tres posiciones (double, string, string) todo: poner dinamicos la
		 * cantidad de posiciones del resultset poder elegir el tipo de graficos
		 */
		String salida = null;
		try {
			DefaultPieDataset dataset = new DefaultPieDataset();
			while (rsDatos.next()) {
				dataset.setValue(rsDatos.getString(1), new Double(rsDatos
						.getDouble(2)));
			}

			/*
			 * JFreeChart chart = ChartFactory.createPieChart3D(
			 * tituloPrincipal, dataset, true, true, false );
			 * 
			 * PiePlot3D plot = ( PiePlot3D )chart.getPlot();
			 * //plot.setForegroundAlpha( 0.6f );
			 */

			// org.jfree.chart.JFreeChart chart =
			// org.jfree.chart.ChartFactory.createPieChart(tituloPrincipal,
			// dataset, true, true, false);
			JFreeChart chart = ChartFactory.createPieChart(tituloPrincipal,
					dataset, true, true, false);

			PiePlot plot = (PiePlot) chart.getPlot();

			// setSectionPaint(int section, java.awt.Paint paint)

			// plot.setForegroundAlpha(0.3f);
			// plot.setExplodePercent(1, 0.50);
			plot.setStartAngle(45);
			salida = nombreArchivo;

			ChartUtilities.saveChartAsJPEG(new java.io.File(path
					+ nombreArchivo), chart, largo, alto);

		} catch (Exception exc) {
			System.out.println("Error realizando la imagen a disco:" + exc);
		}

		return salida;
	}

	public String GenerarGraficosTimes(String nombreArchivo,
			String tituloPrincipal, ResultSet rsDatos) {
		/*
		 * Utilidad : Generar graficos Autor : Carlos Enrique Perez Atenti: El
		 * nombre del archivo siempre debe ser jpg los XYSeries deben ser un
		 * query de dos posiciones donde va primero las y y despues las x en
		 * ambos casos los valores deben ser de tipo double. todo: poner
		 * dinamicos la cantidad de posiciones del resultset poder elegir el
		 * tipo de graficos
		 */
		String salida = null;
		try {
			XYSeries series = new XYSeries(tituloPrincipal);
			ResultSetMetaData md = rsDatos.getMetaData();
			while (rsDatos.next()) {
				series.add(rsDatos.getDouble(1), rsDatos.getDouble(2));
			}
			// series.add(1995, 0.5);
			XYDataset dataset = new XYSeriesCollection(series);
			// JFreeChart chart = ChartFactory.createXYAreaChart(
			JFreeChart chart = ChartFactory.createTimeSeriesChart(
					tituloPrincipal, md.getColumnName(1), md.getColumnName(2),
					dataset, true, false, false);
			salida = nombreArchivo;
			ChartUtilities.saveChartAsJPEG(new java.io.File(path
					+ nombreArchivo), chart, 320, 240);
		} catch (Exception exc) {
			System.out.println("Error realizando la imagen a disco");
		}

		return salida;
	}

	// -- fin de rutina de graficos.

	public String usuarioCreate(String usuario, String clave,
			int administrador, String email, String usuarioalt)
			throws EJBException {
		PreparedStatement insert = null;
		String mensaje = "";
		String tableName = "usuarios";
		try {

			insert = dbconn.prepareStatement("INSERT INTO " + tableName
					+ "(usuario, clave, administrador, email, usuarioalt)"
					+ " VALUES(?,?,?,?,? )");
			insert.setString(1, usuario);
			insert.setString(2, clave);
			insert.setInt(3, administrador);
			insert.setString(4, email);
			insert.setString(5, usuarioalt);
			int n = insert.executeUpdate();
			if (n == 1)
				mensaje = "ALTA CORRECTA.";

		} catch (Exception e) {
			log.error("usuarioCreate(): " + e);
			mensaje = "Error al ejecutar alta de usuario.";
		}
		return mensaje;
	}

	public String usuarioUpdate(long idusuario, String usuario, String clave,
			int administrador, String email, String usuarioact)
			throws EJBException {
		PreparedStatement insert = null;
		String mensaje = "";
		String tableName = "usuarios";
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		try {

			insert = dbconn
					.prepareStatement("UPDATE "
							+ tableName
							+ " SET usuario=?, clave=?, administrador=?, email=?, usuarioact=?, fechaact=? "
							+ " WHERE idusuario=?");
			insert.setString(1, usuario);
			insert.setString(2, clave);
			insert.setInt(3, administrador);
			insert.setString(4, email);
			insert.setString(5, usuarioact);
			insert.setTimestamp(6, fechaact);
			insert.setLong(7, idusuario);
			int n = insert.executeUpdate();
			if (n == 1)
				mensaje = "MODIFICACION CORRECTA.";
			if (n == 0)
				mensaje = "USUARIO INEXISTENTE.";

		} catch (Exception e) {
			log.error("usuarioUpdate(): " + e);
			mensaje = "Error al ejecutar modificaci�n de usuario: " + usuario;
		}
		return mensaje;
	}

	public String usuarioDelete(long idusuario) throws EJBException {
		PreparedStatement insert = null;
		String mensaje = "";
		String tableName = "usuarios";
		try {

			insert = dbconn.prepareStatement("DELETE FROM " + tableName
					+ " WHERE idusuario=?");

			insert.setLong(1, idusuario);
			int n = insert.executeUpdate();
			if (n == 1)
				mensaje = "BAJA CORRECTA.";
			if (n == 0)
				mensaje = "USUARIO INEXISTENTE.";

		} catch (Exception e) {
			log.error("usuarioDelete(): " + e);
			mensaje = "Error al eliminar  usuario. ";
		}
		return mensaje;
	}

	public List getUsuario(long idUsuario) throws EJBException {
		/**
		 * Entidad: Usuarios
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer usuario por id.
		 */
		ResultSet rsSalida = null;
		String cQuery = "SELECT idusuario, usuario, clave, administrador, email "
				+ " FROM usuarios " + "WHERE idusuario = " + idUsuario;
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
			log.error("getUsuario()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getUsuario()- Salida por exception: " + ex);
		}
		return vecSalida;
	}

	public List getValidarUsuario(String usuario, String password)
			throws EJBException {
		/**
		 * Entidad: Usuarios
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer usuario por id.
		 */
		ResultSet rsSalida = null;
		String cQuery = "SELECT idusuario, usuario, clave, administrador, email "
				+ " FROM usuarios "
				+ "WHERE usuario = '"
				+ usuario
				+ "' AND clave = '" + password + "'";
		List vecSalida = new ArrayList();
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			ResultSetMetaData md = rsSalida.getMetaData();
			if (rsSalida.next()) {

				int totCampos = md.getColumnCount() - 1;
				String[] sSalida = new String[totCampos + 1];
				int i = 0;
				while (i <= totCampos) {
					sSalida[i] = rsSalida.getString(++i);
				}
				vecSalida.add(sSalida);
			}

		} catch (SQLException sqlException) {
			log.error("getValidarUsuario()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getValidarUsuario()- Salida por exception: " + ex);
		}
		return vecSalida;
	}

	public List getUsuariosAll() throws EJBException {
		/**
		 * Entidad: Usuarios
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer usuarios.
		 */
		ResultSet rsSalida = null;
		String cQuery = "SELECT idusuario, usuario, clave, administrador, email "
				+ " FROM usuarios ORDER BY usuario ";
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
			log.error("getUsuariosAll()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getUsuariosAll()- Salida por exception: " + ex);
		}
		return vecSalida;
	}

	public List getUsuariosAll(int limit, long offset) throws EJBException {
		/**
		 * Entidad: Usuarios
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer usuarios.
		 */
		ResultSet rsSalida = null;
		String cQuery = ""
				+ " SELECT idusuario, usuario, clave, administrador, email "
				+ "   FROM usuarios " + " ORDER BY usuario" + " LIMIT " + limit
				+ " OFFSET " + offset;
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
			log.error("getUsuariosAll()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getUsuariosAll()- Salida por exception: " + ex);
		}
		return vecSalida;
	}

	public List getUsuariosOcu(int limit, long offset, String ocurrencia)
			throws EJBException {
		/**
		 * Entidad: Usuarios
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer usuario por ocurrencia.
		 */
		ResultSet rsSalida = null;
		String cQuery = "SELECT idusuario, usuario, clave, administrador, email "
				+ " FROM usuarios "
				+ " WHERE idusuario::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR UPPER(usuario) LIKE '%"
				+ ocurrencia.toUpperCase()
				+ "%'"
				+ " ORDER BY usuario "
				+ " LIMIT " + limit + " OFFSET " + offset;
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
			log.error("getUsuariosOcu()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getUsuariosOcu()- Salida por exception: " + ex);
		}
		return vecSalida;
	}

	public List getGrupo(long idGrupo) throws EJBException {
		/**
		 * Entidad: Grupos
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer grupo por id.
		 */
		ResultSet rsSalida = null;
		String cQuery = "SELECT idgrupo, grupo, descripcion " + " FROM grupos "
				+ "WHERE idgrupo = " + idGrupo;
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
			log.error("getGrupo()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getGrupo()- Salida por exception: " + ex);
		}
		return vecSalida;
	}

	public List getGruposAll(int limit, long offset) throws EJBException {
		/**
		 * Entidad: Grupos
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer grupos.
		 */
		ResultSet rsSalida = null;
		String cQuery = "" + " SELECT idgrupo, grupo, descripcion "
				+ "   FROM grupos " + " ORDER BY grupo" + " LIMIT " + limit
				+ " OFFSET " + offset;
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
			log.error("getGruposAll()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getGruposAll()- Salida por exception: " + ex);
		}
		return vecSalida;
	}

	public List getGruposOcu(int limit, long offset, String ocurrencia)
			throws EJBException {
		/**
		 * Entidad: Grupos
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer grupo por ocurrencia.
		 */
		ResultSet rsSalida = null;
		String cQuery = "SELECT idgrupo, grupo, descripcion " + " FROM grupos "
				+ " WHERE idgrupo::VARCHAR LIKE '%" + ocurrencia
				+ "%' OR UPPER(grupo) LIKE '%" + ocurrencia.toUpperCase()
				+ "%'" + " ORDER BY grupo " + " LIMIT " + limit + " OFFSET "
				+ offset;
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
			log.error("getGruposOcu()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getGruposOcu()- Salida por exception: " + ex);
		}
		return vecSalida;
	}

	public String grupoDelete(long idgrupo) throws EJBException {
		PreparedStatement insert = null;
		String mensaje = "";
		String tableName = "grupos";
		try {

			insert = dbconn.prepareStatement("DELETE FROM " + tableName
					+ " WHERE idgrupo=?");

			insert.setLong(1, idgrupo);
			int n = insert.executeUpdate();
			if (n == 1)
				mensaje = "BAJA CORRECTA.";
			if (n == 0)
				mensaje = "GRUPO INEXISTENTE.";

		} catch (Exception e) {
			log.error("grupoDelete(): " + e);
			mensaje = "Error al eliminar  grupo. ";
		}
		return mensaje;
	}

	public String grupoUpdate(long idgrupo, String grupo, String descripcion,
			String usuarioact) throws EJBException {
		PreparedStatement insert = null;
		String mensaje = "";
		String tableName = "grupos";
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		try {

			insert = dbconn.prepareStatement("UPDATE " + tableName
					+ " SET grupo=?, descripcion=?, usuarioact=?, fechaact=?  "
					+ " WHERE idgrupo=?");
			insert.setString(1, grupo);
			insert.setString(2, descripcion);
			insert.setString(3, usuarioact);
			insert.setTimestamp(4, fechaact);
			insert.setLong(5, idgrupo);
			int n = insert.executeUpdate();
			if (n == 1)
				mensaje = "MODIFICACION CORRECTA.";
			if (n == 0)
				mensaje = "GRUPO INEXISTENTE.";

		} catch (Exception e) {
			log.error("grupoUpdate(): " + e);
			mensaje = "Error al ejecutar modificaci�n de grupo: " + grupo;
		}
		return mensaje;
	}

	public String grupoCreate(String grupo, String descripcion,
			String usuarioalt) throws EJBException {
		PreparedStatement insert = null;
		String mensaje = "";
		String tableName = "grupos";
		try {

			insert = dbconn.prepareStatement("INSERT INTO " + tableName
					+ "(grupo, descripcion, usuarioalt)" + " VALUES(?,?,? )");
			insert.setString(1, grupo);
			insert.setString(2, descripcion);
			insert.setString(3, usuarioalt);

			int n = insert.executeUpdate();
			if (n == 1)
				mensaje = "ALTA CORRECTA.";

		} catch (Exception e) {
			log.error("grupoCreate(): " + e);
			mensaje = "Error al ejecutar alta de grupo.";
		}
		return mensaje;
	}

	public List getUsuarioGruposAll(int limit, long offset, long idusuario)
			throws EJBException {
		/**
		 * Entidad: Grupos X Usuario
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer grupos.
		 */
		ResultSet rsSalida = null;
		String cQuery = ""
				+ " SELECT idgrupo, grupo, descripcion "
				+ "   FROM grupos  "
				+ " WHERE idgrupo  IN (SELECT idgrupo FROM grupo_usuarios WHERE idusuario =  "
				+ idusuario + " )" + " ORDER BY grupo" + " LIMIT " + limit
				+ " OFFSET " + offset;
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
			log.error("getUsuarioGruposAll()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getUsuarioGruposAll()- Salida por exception: " + ex);
		}
		return vecSalida;
	}

	public List getGruposAsociar(int limit, long offset, long idusuario)
			throws EJBException {
		/**
		 * Entidad: Grupos X Usuario
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer grupos.
		 */
		ResultSet rsSalida = null;
		String cQuery = ""
				+ " SELECT idgrupo, grupo, descripcion "
				+ "   FROM grupos  "
				+ " WHERE idgrupo NOT  IN (SELECT idgrupo FROM grupo_usuarios WHERE idusuario =  "
				+ idusuario + " )" + " ORDER BY grupo" + " LIMIT " + limit
				+ " OFFSET " + offset;
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
			log.error("getGruposAsociar()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getGruposAsociar()- Salida por exception: " + ex);
		}
		return vecSalida;
	}

	public String usuarioGrupoDelete(long idusuario, long idgrupo)
			throws EJBException {
		PreparedStatement insert = null;
		String mensaje = "";
		String tableName = "grupo_usuarios";
		try {

			insert = dbconn.prepareStatement("DELETE FROM " + tableName
					+ " WHERE idgrupo=? AND idusuario=?");

			insert.setLong(1, idgrupo);
			insert.setLong(2, idusuario);

			int n = insert.executeUpdate();
			if (n == 1)
				mensaje = "BAJA CORRECTA.";
			if (n == 0)
				mensaje = "RELACION GRUPO-USUARIO INEXISTENTE.";

		} catch (Exception e) {
			log.error("usuarioGrupoDelete(): " + e);
			mensaje = "Error al eliminar  relacion grupo-usuario. ";
		}
		return mensaje;
	}

	public String usuarioGrupoCreate(long idusuario, long[] idgrupos,
			String usuarioalt) throws EJBException, SQLException {
		Statement insert = null;
		String mensaje = "";
		String tableName = "grupo_usuarios";
		try {

			dbconn.setAutoCommit(false);
			insert = dbconn.createStatement();
			for (int i = 0; i < idgrupos.length; i++) {
				insert.addBatch("INSERT INTO " + tableName
						+ "(idgrupo, idusuario, usuarioalt)" + " VALUES("
						+ idgrupos[i] + "," + idusuario + ", '" + usuarioalt
						+ "')");
			}
			insert.executeBatch();
			mensaje = "ALTA CORRECTA.";
			dbconn.commit();
			dbconn.setAutoCommit(true);
		} catch (SQLException eSQL) {
			dbconn.rollback();
			dbconn.setAutoCommit(true);
			log.error("usuarioGrupoCreate(): " + eSQL);
			log.error("usuarioGrupoCreate() next-Ex: "
					+ eSQL.getNextException());
			mensaje = "Error sql al ejecutar alta de grupo.";
		} catch (Exception e) {
			dbconn.rollback();
			dbconn.setAutoCommit(true);
			log.error("usuarioGrupoCreate(): " + e);
			mensaje = "Error al ejecutar alta de grupo.";
		}

		return mensaje;
	}

	public String reportesDelete(long idreporte) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM REPORTES WHERE idreporte=" + idreporte;
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM REPORTES WHERE idreporte=" + idreporte;
				statement.execute(cQuery);
				salida = "BAJA CORRECTA.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Error al ejecutar baja de reporte.";
			log
					.error("Error SQL en el metodo : reportesDelete( long idreporte ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Error al ejecutar baja de reporte.";
			log
					.error("Salida por exception: en el metodo: reportesDelete( long idreporte )  "
							+ ex);

		}
		return salida;
	}

	public String reportesCreate(String reporte, String comentario,
			String skin, String usuarioalt) throws EJBException {
		PreparedStatement insert = null;
		String mensaje = "";
		String tableName = "reportes";
		try {

			insert = dbconn.prepareStatement("INSERT INTO " + tableName
					+ "(reporte, comentario, skin, usuarioalt)"
					+ " VALUES(?,?,?,? )");
			insert.setString(1, reporte);
			insert.setString(2, comentario);
			insert.setString(3, skin);
			insert.setString(4, usuarioalt);
			int n = insert.executeUpdate();
			if (n == 1)
				mensaje = "ALTA CORRECTA.";

		} catch (Exception e) {
			log.error("reportesCreate(): " + e);
			mensaje = "Error al ejecutar alta de reporte.";
		}
		return mensaje;
	}

	public String reportesUpdate(long idreporte, String reporte,
			String comentario, String skin, String usuarioact)
			throws EJBException {
		PreparedStatement insert = null;
		String mensaje = "";
		String tableName = "reportes";
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		try {

			insert = dbconn
					.prepareStatement("UPDATE "
							+ tableName
							+ " SET reporte=?, comentario=? , skin=?, usuarioact=?, fechaact=? "
							+ " WHERE idreporte=?");
			insert.setString(1, reporte);
			insert.setString(2, comentario);
			insert.setString(3, skin);
			insert.setString(4, usuarioact);
			insert.setTimestamp(5, fechaact);
			insert.setLong(6, idreporte);
			int n = insert.executeUpdate();
			if (n == 1)
				mensaje = "MODIFICACION CORRECTA.";
			if (n == 0)
				mensaje = "REPORTE INEXISTENTE.";

		} catch (Exception e) {
			log.error("grupoUpdate(): " + e);
			mensaje = "Error al ejecutar modificaci�n de reporte: " + reporte;
		}
		return mensaje;
	}

	public List getReportesAll(int limit, long offset) throws EJBException {
		/**
		 * Entidad: Reportes
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer reportes.
		 */
		ResultSet rsSalida = null;
		String cQuery = "" + " SELECT idreporte, reporte, comentario, skin "
				+ "   FROM reportes " + " ORDER BY reporte" + " LIMIT " + limit
				+ " OFFSET " + offset;
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
			log.error("getReportesAll()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getReportesAll()- Salida por exception: " + ex);
		}
		return vecSalida;
	}

	public List getReportesOcu(int limit, long offset, String ocurrencia)
			throws EJBException {
		/**
		 * Entidad: Reportes
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer reporte por ocurrencia.
		 */
		ResultSet rsSalida = null;
		String cQuery = "SELECT idreporte, reporte, comentario, skin "
				+ " FROM reportes " + " WHERE idreporte::VARCHAR LIKE '%"
				+ ocurrencia + "%' OR UPPER(reporte) LIKE '%"
				+ ocurrencia.toUpperCase() + "%'" + " ORDER BY reporte "
				+ " LIMIT " + limit + " OFFSET " + offset;
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
			log.error("getReportesOcu()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getReportesOcu()- Salida por exception: " + ex);
		}
		return vecSalida;
	}

	public String tablasDelete(long idtabla) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM tablas WHERE idtabla=" + idtabla;
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM tablas WHERE idtabla=" + idtabla;
				statement.execute(cQuery);
				salida = "BAJA CORRECTA.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Error al ejecutar baja de tablas.";
			log.error("Error SQL en el metodo : tablasDelete( long idtabla ) "
					+ sqlException);
		} catch (Exception ex) {
			salida = "Error al ejecutar baja de tablas.";
			log
					.error("Salida por exception: en el metodo: tablasDelete( long idtabla )  "
							+ ex);

		}
		return salida;
	}

	public String tablasCreate(String tabla, String query_carga,
			String query_consulta, long iddatasource, String usuarioalt)
			throws EJBException {
		PreparedStatement insert = null;
		String mensaje = "";
		String tableName = "tablas";
		try {

			insert = dbconn
					.prepareStatement("INSERT INTO "
							+ tableName
							+ "(tabla, query_carga, query_consulta, iddatasource, usuarioalt)"
							+ " VALUES(?,?,?,?,? )");
			insert.setString(1, tabla);
			insert.setString(2, query_carga);
			insert.setString(3, query_consulta);
			insert.setLong(4, iddatasource);
			insert.setString(5, usuarioalt);

			int n = insert.executeUpdate();
			if (n == 1)
				mensaje = "ALTA CORRECTA.";

		} catch (Exception e) {
			log.error("tablasCreate(): " + e);
			mensaje = "Error al ejecutar alta de tabla.";
		}
		return mensaje;
	}

	public String tablasUpdate(long idtabla, String tabla, String query_carga,
			String query_consulta, long iddatasource, String usuarioact)
			throws EJBException {
		PreparedStatement insert = null;
		String mensaje = "";
		String tableName = "tablas";
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		try {

			insert = dbconn
					.prepareStatement("UPDATE "
							+ tableName
							+ " SET tabla=?,  query_carga=?, query_consulta=?, iddatasource=?, usuarioact=?, fechaact=? "
							+ " WHERE idtabla=?");
			insert.setString(1, tabla);
			insert.setString(2, query_carga);
			insert.setString(3, query_consulta);
			insert.setLong(4, iddatasource);
			insert.setString(5, usuarioact);
			insert.setTimestamp(6, fechaact);
			insert.setLong(7, idtabla);
			int n = insert.executeUpdate();
			if (n == 1)
				mensaje = "MODIFICACION CORRECTA.";
			if (n == 0)
				mensaje = "TABLA INEXISTENTE.";

		} catch (Exception e) {
			log.error("tablasUpdate(): " + e);
			mensaje = "Error al ejecutar modificaci�n de tabla: " + tabla;
		}
		return mensaje;
	}

	public List getTablasPK(long idTabla) throws EJBException {
		/**
		 * Entidad: TABLAS
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer tabla por id.
		 */
		ResultSet rsSalida = null;
		String cQuery = "SELECT idtabla, tabla, query_carga, query_consulta, iddatasource "
				+ " FROM tablas " + "WHERE idtabla = " + idTabla;
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
			log.error("getTablasPK()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getTablasPK()- Salida por exception: " + ex);
		}
		return vecSalida;
	}

	// LLENA COMBO - Metodo Sobrecargado
	public List getTablasAll() throws EJBException {
		/**
		 * Entidad: tablas
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer tablas.
		 */
		ResultSet rsSalida = null;
		String cQuery = "SELECT idtabla, tabla "
				+ " FROM tablas ORDER BY tabla ";
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
			log.error("getTablasAll()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getTablasAll()- Salida por exception: " + ex);
		}
		return vecSalida;
	}

	public List getTablasAll(int limit, long offset) throws EJBException {
		/**
		 * Entidad: Tablas
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer tablas.
		 */
		ResultSet rsSalida = null;
		String cQuery = ""
				+ " SELECT idtabla, tabla, query_carga, query_consulta, iddatasource "
				+ "   FROM tablas " + " ORDER BY tabla" + " LIMIT " + limit
				+ " OFFSET " + offset;
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
			log.error("getTablasAll()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getTablasAll()- Salida por exception: " + ex);
		}
		return vecSalida;
	}

	public List getTablasOcu(int limit, long offset, String ocurrencia)
			throws EJBException {
		/**
		 * Entidad: Tablas
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer tablas por ocurrencia.
		 */
		ResultSet rsSalida = null;
		String cQuery = "SELECT idtabla, tabla, query_carga, query_consulta, iddatasource  "
				+ " FROM tablas "
				+ " WHERE idtabla::VARCHAR LIKE '%"
				+ ocurrencia
				+ "%' OR UPPER(tabla) LIKE '%"
				+ ocurrencia.toUpperCase()
				+ "%'"
				+ " ORDER BY tabla "
				+ " LIMIT " + limit + " OFFSET " + offset;
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
			log.error("getTablasOcu()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getTablasOcu()- Salida por exception: " + ex);
		}
		return vecSalida;
	}

	// DATASOURCES
	// ////////////////////////////////////////////////////

	// LLENA COMBO - Metodo Sobrecargado.
	public List getDatasourcesAll() throws EJBException {
		/**
		 * Entidad: Datasources
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer datasources.
		 */
		ResultSet rsSalida = null;
		String cQuery = "SELECT iddatasource, datasource, driver, db_user, db_pass "
				+ " FROM datasources ORDER BY datasource ";
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
			log.error("getDatasourcesAll()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getDatasourcesAll()- Salida por exception: " + ex);
		}
		return vecSalida;
	}

	// para todo (ordena por el segundo campo por defecto)
	public List getDataSourcesAll(long limit, long offset) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT iddatasource,datasource,driver,db_user,db_pass,url,usuarioalt,usuarioact,fechaalt,fechaact FROM DATASOURCES ORDER BY 2  LIMIT "
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
			log.error("Error SQL en el metodo : getDataSources() "
					+ sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception: en el metodo: getDataSources()  "
					+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getDataSourcesOcu(long limit, long offset, String ocurrencia)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  iddatasource,datasource,driver,db_user,db_pass,url,usuarioalt,usuarioact,fechaalt,fechaact FROM DATASOURCES WHERE UPPER(DATASOURCE) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' ORDER BY 2  LIMIT "
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
					.error("Error SQL en el metodo : getDataSources(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getDataSources(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getDataSourcesPK(BigDecimal iddatasource) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  iddatasource,datasource,driver,db_user,db_pass,url,usuarioalt,usuarioact,fechaalt,fechaact FROM DATASOURCES WHERE iddatasource="
				+ iddatasource.toString();
		;
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
					.error("Error SQL en el metodo : getDataSourcesPK( BigDecimal iddatasource ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getDataSourcesPK( BigDecimal iddatasource )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String dataSourcesDelete(BigDecimal iddatasource)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM DATASOURCES WHERE iddatasource="
				+ iddatasource.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM DATASOURCES WHERE iddatasource="
						+ iddatasource.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : dataSourcesDelete( BigDecimal iddatasource ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: dataSourcesDelete( BigDecimal iddatasource )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String dataSourcesCreate(String datasource, String driver,
			String db_user, String db_pass, String url, String usuarioalt)
			throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (datasource == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: datasource ";
		if (driver == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: driver ";
		if (url == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: url ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (datasource.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: datasource ";
		if (driver.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: driver ";
		if (url.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: url ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO DATASOURCES(datasource, driver, db_user, db_pass, url, usuarioalt ) VALUES (?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, datasource);
				insert.setString(2, driver);
				insert.setString(3, db_user);
				insert.setString(4, db_pass);
				insert.setString(5, url);
				insert.setString(6, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String dataSourcesCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error excepcion public String dataSourcesCreate(.....)"
					+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String dataSourcesCreateOrUpdate(BigDecimal iddatasource,
			String datasource, String driver, String db_user, String db_pass,
			String url, String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (iddatasource == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: iddatasource ";
		if (datasource == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: datasource ";
		if (driver == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: driver ";
		if (url == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: url ";

		// 2. sin nada desde la pagina
		if (datasource.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: datasource ";
		if (driver.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: driver ";
		if (url.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: url ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM dataSources WHERE iddatasource = "
					+ iddatasource.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE DATASOURCES SET datasource=?, driver=?, db_user=?, db_pass=?, url=?, usuarioact=?, fechaact=? WHERE iddatasource=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, datasource);
					insert.setString(2, driver);
					insert.setString(3, db_user);
					insert.setString(4, db_pass);
					insert.setString(5, url);
					insert.setString(6, usuarioact);
					insert.setTimestamp(7, fechaact);
					insert.setBigDecimal(8, iddatasource);
				} else {
					String ins = "INSERT INTO DATASOURCES(datasource, driver, db_user, db_pass, url, usuarioalt ) VALUES (?, ?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, datasource);
					insert.setString(2, driver);
					insert.setString(3, db_user);
					insert.setString(4, db_pass);
					insert.setString(5, url);
					insert.setString(6, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String dataSourcesCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String dataSourcesCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String dataSourcesUpdate(BigDecimal iddatasource, String datasource,
			String driver, String db_user, String db_pass, String url,
			String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (iddatasource == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: iddatasource ";
		if (datasource == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: datasource ";
		if (driver == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: driver ";
		if (url == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: url ";

		// 2. sin nada desde la pagina
		if (datasource.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: datasource ";
		if (driver.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: driver ";
		if (url.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: url ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM dataSources WHERE iddatasource = "
					+ iddatasource.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE DATASOURCES SET datasource=?, driver=?, db_user=?, db_pass=?, url=?, usuarioact=?, fechaact=? WHERE iddatasource=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, datasource);
					insert.setString(2, driver);
					insert.setString(3, db_user);
					insert.setString(4, db_pass);
					insert.setString(5, url);
					insert.setString(6, usuarioact);
					insert.setTimestamp(7, fechaact);
					insert.setBigDecimal(8, iddatasource);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String dataSourcesUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log.error("Error excepcion public String dataSourcesUpdate(.....)"
					+ ex);
		}
		return salida;
	}

	// ////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////
	// CAMPOS

	/**
	 * Metodos para la entidad: campos Copyrigth(r) sysWarp S.R.L. Fecha de
	 * creacion: Tue Jul 04 15:46:54 GMT-03:00 2006
	 * 
	 */
	// para todo (ordena por el segundo campo por defecto)
	public List getCamposAll(long limit, long offset) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idcampo,campo,idtabla,titulo,orden,clase_css,lenght_col,comentario,usuarioalt,usuarioact,fechaalt,fechaact FROM CAMPOS ORDER BY 2  LIMIT "
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
			log.error("Error SQL en el metodo : getCampos() " + sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception: en el metodo: getCampos()  " + ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getCamposOcu(long limit, long offset, String ocurrencia)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idcampo,campo,idtabla,titulo,orden,clase_css,lenght_col,comentario,usuarioalt,usuarioact,fechaalt,fechaact FROM CAMPOS WHERE UPPER(CAMPO) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' ORDER BY 2  LIMIT "
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
			log.error("Error SQL en el metodo : getCampos(String ocurrencia) "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCampos(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getCamposPK(BigDecimal idcampo) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idcampo,campo,idtabla,titulo,orden,clase_css,lenght_col,comentario,usuarioalt,usuarioact,fechaalt,fechaact FROM CAMPOS WHERE idcampo="
				+ idcampo.toString();
		;
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
					.error("Error SQL en el metodo : getCamposPK( BigDecimal idcampo ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getCamposPK( BigDecimal idcampo )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String camposDelete(BigDecimal idcampo) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM CAMPOS WHERE idcampo="
				+ idcampo.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM CAMPOS WHERE idcampo="
						+ idcampo.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : camposDelete( BigDecimal idcampo ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: camposDelete( BigDecimal idcampo )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String camposCreate(String campo, BigDecimal idtabla, String titulo,
			BigDecimal orden, String clase_css, String lenght_col,
			String comentario, String usuarioalt) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (campo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: campo ";
		if (idtabla == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtabla ";
		if (titulo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: titulo ";
		if (orden == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: orden ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (campo.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: campo ";
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
				String ins = "INSERT INTO CAMPOS(campo, idtabla, titulo, orden, clase_css, lenght_col, comentario, usuarioalt ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, campo);
				insert.setBigDecimal(2, idtabla);
				insert.setString(3, titulo);
				insert.setBigDecimal(4, orden);
				insert.setString(5, clase_css);
				insert.setString(6, lenght_col);
				insert.setString(7, comentario);
				insert.setString(8, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String camposCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error excepcion public String camposCreate(.....)" + ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String camposCreateOrUpdate(BigDecimal idcampo, String campo,
			BigDecimal idtabla, String titulo, BigDecimal orden,
			String clase_css, String lenght_col, String comentario,
			String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idcampo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idcampo ";
		if (campo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: campo ";
		if (idtabla == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtabla ";
		if (titulo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: titulo ";
		if (orden == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: orden ";

		// 2. sin nada desde la pagina
		if (campo.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: campo ";
		if (titulo.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: titulo ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM campos WHERE idcampo = "
					+ idcampo.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE CAMPOS SET campo=?, idtabla=?, titulo=?, orden=?, clase_css=?, lenght_col=?, comentario=?, usuarioact=?, fechaact=? WHERE idcampo=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, campo);
					insert.setBigDecimal(2, idtabla);
					insert.setString(3, titulo);
					insert.setBigDecimal(4, orden);
					insert.setString(5, clase_css);
					insert.setString(6, lenght_col);
					insert.setString(7, comentario);
					insert.setString(8, usuarioact);
					insert.setTimestamp(9, fechaact);
					insert.setBigDecimal(10, idcampo);
				} else {
					String ins = "INSERT INTO CAMPOS(campo, idtabla, titulo, orden, clase_css, lenght_col, comentario, usuarioalt ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, campo);
					insert.setBigDecimal(2, idtabla);
					insert.setString(3, titulo);
					insert.setBigDecimal(4, orden);
					insert.setString(5, clase_css);
					insert.setString(6, lenght_col);
					insert.setString(7, comentario);
					insert.setString(8, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String camposCreateOrUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String camposCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String camposUpdate(BigDecimal idcampo, String campo,
			BigDecimal idtabla, String titulo, BigDecimal orden,
			String clase_css, String lenght_col, String comentario,
			String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idcampo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idcampo ";
		if (campo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: campo ";
		if (idtabla == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtabla ";
		if (titulo == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: titulo ";
		if (orden == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: orden ";

		// 2. sin nada desde la pagina
		if (campo.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: campo ";
		if (titulo.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: titulo ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM campos WHERE idcampo = "
					+ idcampo.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE CAMPOS SET campo=?, idtabla=?, titulo=?, orden=?, clase_css=?, lenght_col=?, comentario=?, usuarioact=?, fechaact=? WHERE idcampo=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, campo);
					insert.setBigDecimal(2, idtabla);
					insert.setString(3, titulo);
					insert.setBigDecimal(4, orden);
					insert.setString(5, clase_css);
					insert.setString(6, lenght_col);
					insert.setString(7, comentario);
					insert.setString(8, usuarioact);
					insert.setTimestamp(9, fechaact);
					insert.setBigDecimal(10, idcampo);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String camposUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log.error("Error excepcion public String camposUpdate(.....)" + ex);
		}
		return salida;
	}

	// /////////////////////////////////////////////////////
	// GRAFICOS

	// para todo (ordena por el segundo campo por defecto)
	public List getGraficoAll(long limit, long offset) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idgrafico,grafico,query_consulta,idtipografico,iddatasource,usuarioalt,usuarioact,fechaalt,fechaact, query_consulta2, query_consulta3 FROM GRAFICO ORDER BY 2  LIMIT "
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
			log.error("Error SQL en el metodo : getGrafico() " + sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getGrafico()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getGraficoOcu(long limit, long offset, String ocurrencia)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idgrafico,grafico,query_consulta,idtipografico,iddatasource,usuarioalt,usuarioact,fechaalt,fechaact, query_consulta2, query_consulta3 FROM GRAFICO WHERE UPPER(GRAFICO) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' ORDER BY 2  LIMIT "
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
			log.error("Error SQL en el metodo : getGrafico(String ocurrencia) "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getGrafico(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getGraficoPK(BigDecimal idgrafico) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idgrafico,grafico,query_consulta,idtipografico,iddatasource,usuarioalt,usuarioact,fechaalt,fechaact, query_consulta2, query_consulta3 FROM GRAFICO WHERE idgrafico="
				+ idgrafico.toString();
		;
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
					.error("Error SQL en el metodo : getGraficoPK( BigDecimal idgrafico ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getGraficoPK( BigDecimal idgrafico )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String graficoDelete(BigDecimal idgrafico) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM GRAFICO WHERE idgrafico="
				+ idgrafico.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM GRAFICO WHERE idgrafico="
						+ idgrafico.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : graficoDelete( BigDecimal idgrafico ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: graficoDelete( BigDecimal idgrafico )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String graficoCreate(String grafico, String query_consulta,
			BigDecimal idtipografico, BigDecimal iddatasource, String usuarioalt)
			throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (grafico == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: grafico ";
		if (idtipografico == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipografico ";
		if (iddatasource == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: iddatasource ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (grafico.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: grafico ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO GRAFICO(grafico, query_consulta, idtipografico, iddatasource, usuarioalt ) VALUES (?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, grafico);
				insert.setString(2, query_consulta);
				insert.setBigDecimal(3, idtipografico);
				insert.setBigDecimal(4, iddatasource);
				insert.setString(5, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String graficoCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String graficoCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String graficoCreateOrUpdate(BigDecimal idgrafico, String grafico,
			String query_consulta, BigDecimal idtipografico,
			BigDecimal iddatasource, String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idgrafico == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idgrafico ";
		if (grafico == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: grafico ";
		if (idtipografico == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipografico ";
		if (iddatasource == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: iddatasource ";

		// 2. sin nada desde la pagina
		if (grafico.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: grafico ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM grafico WHERE idgrafico = "
					+ idgrafico.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE GRAFICO SET grafico=?, query_consulta=?, idtipografico=?, iddatasource=?, usuarioact=?, fechaact=? WHERE idgrafico=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, grafico);
					insert.setString(2, query_consulta);
					insert.setBigDecimal(3, idtipografico);
					insert.setBigDecimal(4, iddatasource);
					insert.setString(5, usuarioact);
					insert.setTimestamp(6, fechaact);
					insert.setBigDecimal(7, idgrafico);
				} else {
					String ins = "INSERT INTO GRAFICO(grafico, query_consulta, idtipografico, iddatasource, usuarioalt ) VALUES (?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, grafico);
					insert.setString(2, query_consulta);
					insert.setBigDecimal(3, idtipografico);
					insert.setBigDecimal(4, iddatasource);
					insert.setString(5, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String graficoCreateOrUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String graficoCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String graficoUpdate(BigDecimal idgrafico, String grafico,
			String query_consulta, BigDecimal idtipografico,
			BigDecimal iddatasource, String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idgrafico == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idgrafico ";
		if (grafico == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: grafico ";
		if (idtipografico == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipografico ";
		if (iddatasource == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: iddatasource ";

		// 2. sin nada desde la pagina
		if (grafico.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: grafico ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM grafico WHERE idgrafico = "
					+ idgrafico.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE GRAFICO SET grafico=?, query_consulta=?, idtipografico=?, iddatasource=?, usuarioact=?, fechaact=? WHERE idgrafico=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, grafico);
					insert.setString(2, query_consulta);
					insert.setBigDecimal(3, idtipografico);
					insert.setBigDecimal(4, iddatasource);
					insert.setString(5, usuarioact);
					insert.setTimestamp(6, fechaact);
					insert.setBigDecimal(7, idgrafico);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String graficoUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String graficoUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * Metodos para la entidad: parametros Copyrigth(r) sysWarp S.R.L. Fecha de
	 * creacion: Mon Jul 03 17:43:39 GMT-03:00 2006
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getParametrosAll(long limit, long offset) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idparametro,parametro,descripcion,idtipoparametro,validacion_query,iddatasource,obligatorio,orden,usuarioalt,usuarioact,fechaalt,fechaact FROM PARAMETROS ORDER BY 2  LIMIT "
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
			log.error("Error SQL en el metodo : getParametros() "
					+ sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception: en el metodo: getParametros()  "
					+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getParametrosOcu(long limit, long offset, String ocurrencia)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idparametro,parametro,descripcion,idtipoparametro,validacion_query,iddatasource,obligatorio,orden,usuarioalt,usuarioact,fechaalt,fechaact FROM PARAMETROS WHERE UPPER(PARAMETRO) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' ORDER BY 2  LIMIT "
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
					.error("Error SQL en el metodo : getParametros(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getParametros(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getParametrosPK(BigDecimal idparametro) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idparametro,parametro,descripcion,idtipoparametro,validacion_query,iddatasource,obligatorio,orden,usuarioalt,usuarioact,fechaalt,fechaact FROM PARAMETROS WHERE idparametro="
				+ idparametro.toString();
		;
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
					.error("Error SQL en el metodo : getParametrosPK( BigDecimal idparametro ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getParametrosPK( BigDecimal idparametro )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String parametrosDelete(BigDecimal idparametro) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM PARAMETROS WHERE idparametro="
				+ idparametro.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM PARAMETROS WHERE idparametro="
						+ idparametro.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : parametrosDelete( BigDecimal idparametro ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: parametrosDelete( BigDecimal idparametro )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String parametrosCreate(String parametro, String descripcion,
			BigDecimal idtipoparametro, String validacion_query,
			BigDecimal iddatasource, String obligatorio, BigDecimal orden,
			String usuarioalt) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (parametro == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: parametro ";
		if (descripcion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: descripcion ";
		if (idtipoparametro == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipoparametro ";
		if (iddatasource == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: iddatasource ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (parametro.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: parametro ";
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
				String ins = "INSERT INTO PARAMETROS(parametro, descripcion, idtipoparametro, validacion_query, iddatasource, obligatorio, orden, usuarioalt ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, parametro);
				insert.setString(2, descripcion);
				insert.setBigDecimal(3, idtipoparametro);
				insert.setString(4, validacion_query);
				insert.setBigDecimal(5, iddatasource);
				insert.setBoolean(6, obligatorio.equalsIgnoreCase("s"));
				insert.setBigDecimal(7, orden);
				insert.setString(8, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String parametrosCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error excepcion public String parametrosCreate(.....)"
					+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String parametrosCreateOrUpdate(BigDecimal idparametro,
			String parametro, String descripcion, BigDecimal idtipoparametro,
			String validacion_query, BigDecimal iddatasource,
			String obligatorio, BigDecimal orden, String usuarioact)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idparametro == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idparametro ";
		if (parametro == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: parametro ";
		if (descripcion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: descripcion ";
		if (idtipoparametro == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipoparametro ";
		if (iddatasource == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: iddatasource ";

		// 2. sin nada desde la pagina
		if (parametro.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: parametro ";
		if (descripcion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: descripcion ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM parametros WHERE idparametro = "
					+ idparametro.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE PARAMETROS SET parametro=?, descripcion=?, idtipoparametro=?, validacion_query=?, iddatasource=?, obligatorio=?, orden=?, usuarioact=?, fechaact=? WHERE idparametro=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, parametro);
					insert.setString(2, descripcion);
					insert.setBigDecimal(3, idtipoparametro);
					insert.setString(4, validacion_query);
					insert.setBigDecimal(5, iddatasource);
					insert.setBoolean(6, obligatorio.equalsIgnoreCase("s"));
					insert.setBigDecimal(7, orden);
					insert.setString(8, usuarioact);
					insert.setTimestamp(9, fechaact);
					insert.setBigDecimal(10, idparametro);
				} else {
					String ins = "INSERT INTO PARAMETROS(parametro, descripcion, idtipoparametro, validacion_query, iddatasource, obligatorio, orden, usuarioalt ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, parametro);
					insert.setString(2, descripcion);
					insert.setBigDecimal(3, idtipoparametro);
					insert.setString(4, validacion_query);
					insert.setBigDecimal(5, iddatasource);
					insert.setBoolean(6, obligatorio.equalsIgnoreCase("s"));
					insert.setBigDecimal(7, orden);
					insert.setString(8, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String parametrosCreateOrUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String parametrosCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String parametrosUpdate(BigDecimal idparametro, String parametro,
			String descripcion, BigDecimal idtipoparametro,
			String validacion_query, BigDecimal iddatasource,
			String obligatorio, BigDecimal orden, String usuarioact)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idparametro == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idparametro ";
		if (parametro == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: parametro ";
		if (descripcion == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: descripcion ";
		if (idtipoparametro == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipoparametro ";
		if (iddatasource == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: iddatasource ";

		// 2. sin nada desde la pagina
		if (parametro.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: parametro ";
		if (descripcion.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: descripcion ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM parametros WHERE idparametro = "
					+ idparametro.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE PARAMETROS SET parametro=?, descripcion=?, idtipoparametro=?, validacion_query=?, iddatasource=?, obligatorio=?, orden=?, usuarioact=?, fechaact=? WHERE idparametro=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, parametro);
					insert.setString(2, descripcion);
					insert.setBigDecimal(3, idtipoparametro);
					insert.setString(4, validacion_query);
					insert.setBigDecimal(5, iddatasource);
					insert.setBoolean(6, obligatorio.equalsIgnoreCase("s"));
					insert.setBigDecimal(7, orden);
					insert.setString(8, usuarioact);
					insert.setTimestamp(9, fechaact);
					insert.setBigDecimal(10, idparametro);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String parametrosUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log.error("Error excepcion public String parametrosUpdate(.....)"
					+ ex);
		}
		return salida;
	}

	/**
	 * Metodos para la entidad: tipo_grafico Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Tue Jul 04 14:21:40 GMT-03:00 2006
	 * 
	 */

	// LLENA COMBO
	public List getTipoGraficosAll() throws EJBException {
		/**
		 * Entidad: tipo_grafico
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer tipos de graficos.
		 */
		ResultSet rsSalida = null;
		String cQuery = "SELECT idtipografico, tipografico "
				+ " FROM tipo_grafico ORDER BY tipografico ";
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
			log.error("getTipoGraficosAll()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getTipoGraficosAll()- Salida por exception: " + ex);
		}
		return vecSalida;
	}

	// para todo (ordena por el segundo campo por defecto)
	public List getTipo_graficoAll(long limit, long offset) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idtipografico,tipografico,usuarioalt,usuarioact,fechaalt,fechaact FROM TIPO_GRAFICO ORDER BY 2  LIMIT "
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
			log.error("Error SQL en el metodo : getTipo_grafico() "
					+ sqlException);
		} catch (Exception ex) {
			log.error("Salida por exception: en el metodo: getTipo_grafico()  "
					+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getTipo_graficoOcu(long limit, long offset, String ocurrencia)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idtipografico,tipografico,usuarioalt,usuarioact,fechaalt,fechaact FROM TIPO_GRAFICO WHERE UPPER(TIPOGRAFICO) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' ORDER BY 2  LIMIT "
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
					.error("Error SQL en el metodo : getTipo_grafico(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getTipo_grafico(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getTipo_graficoPK(BigDecimal idtipografico) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idtipografico,tipografico,usuarioalt,usuarioact,fechaalt,fechaact FROM TIPO_GRAFICO WHERE idtipografico="
				+ idtipografico.toString();
		;
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
					.error("Error SQL en el metodo : getTipo_graficoPK( BigDecimal idtipografico ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getTipo_graficoPK( BigDecimal idtipografico )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String tipo_graficoDelete(BigDecimal idtipografico)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM TIPO_GRAFICO WHERE idtipografico="
				+ idtipografico.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM TIPO_GRAFICO WHERE idtipografico="
						+ idtipografico.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : tipo_graficoDelete( BigDecimal idtipografico ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: tipo_graficoDelete( BigDecimal idtipografico )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String tipo_graficoCreate(String tipografico, String usuarioalt)
			throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (tipografico == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipografico ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (tipografico.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipografico ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO TIPO_GRAFICO(tipografico, usuarioalt ) VALUES (?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, tipografico);
				insert.setString(2, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String tipo_graficoCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error excepcion public String tipo_graficoCreate(.....)"
					+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String tipo_graficoCreateOrUpdate(BigDecimal idtipografico,
			String tipografico, String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idtipografico == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipografico ";
		if (tipografico == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipografico ";

		// 2. sin nada desde la pagina
		if (tipografico.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipografico ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM tipo_grafico WHERE idtipografico = "
					+ idtipografico.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE TIPO_GRAFICO SET tipografico=?, usuarioact=?, fechaact=? WHERE idtipografico=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, tipografico);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idtipografico);
				} else {
					String ins = "INSERT INTO TIPO_GRAFICO(tipografico, usuarioalt ) VALUES (?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, tipografico);
					insert.setString(2, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String tipo_graficoCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String tipo_graficoCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String tipo_graficoUpdate(BigDecimal idtipografico,
			String tipografico, String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idtipografico == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipografico ";
		if (tipografico == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipografico ";

		// 2. sin nada desde la pagina
		if (tipografico.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipografico ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM tipo_grafico WHERE idtipografico = "
					+ idtipografico.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE TIPO_GRAFICO SET tipografico=?, usuarioact=?, fechaact=? WHERE idtipografico=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, tipografico);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idtipografico);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String tipo_graficoUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log.error("Error excepcion public String tipo_graficoUpdate(.....)"
					+ ex);
		}
		return salida;
	}

	/** FIN TIPO GRAFICO */

	/**
	 * Metodos para la entidad: tipo_parametro Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Tue Jul 04 14:47:30 GMT-03:00 2006
	 */
	// para todo (ordena por el segundo campo por defecto)
	// LLENA COMBO
	public List getTipoParametrosAll() throws EJBException {
		/**
		 * Entidad: tipo_parametro
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer tipos de parametros.
		 */
		ResultSet rsSalida = null;
		String cQuery = "SELECT idtipoparametro, tipoparametro "
				+ " FROM tipo_parametro ORDER BY tipoparametro ";
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
			log.error("getTipoParametrosAll()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getTipoParametrosAll()- Salida por exception: " + ex);
		}
		return vecSalida;
	}

	public List getTipo_parametroAll(long limit, long offset)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idtipoparametro,tipoparametro,usuarioalt,usuarioact,fechaalt,fechaact FROM TIPO_PARAMETRO ORDER BY 2  LIMIT "
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
			log.error("Error SQL en el metodo : getTipo_parametro() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getTipo_parametro()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getTipo_parametroOcu(long limit, long offset, String ocurrencia)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idtipoparametro,tipoparametro,usuarioalt,usuarioact,fechaalt,fechaact FROM TIPO_PARAMETRO WHERE UPPER(TIPOPARAMETRO) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' ORDER BY 2  LIMIT "
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
					.error("Error SQL en el metodo : getTipo_parametro(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getTipo_parametro(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getTipo_parametroPK(BigDecimal idtipoparametro)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idtipoparametro,tipoparametro,usuarioalt,usuarioact,fechaalt,fechaact FROM TIPO_PARAMETRO WHERE idtipoparametro="
				+ idtipoparametro.toString();
		;
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
					.error("Error SQL en el metodo : getTipo_parametroPK( BigDecimal idtipoparametro ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getTipo_parametroPK( BigDecimal idtipoparametro )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String tipo_parametroDelete(BigDecimal idtipoparametro)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM TIPO_PARAMETRO WHERE idtipoparametro="
				+ idtipoparametro.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM TIPO_PARAMETRO WHERE idtipoparametro="
						+ idtipoparametro.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : tipo_parametroDelete( BigDecimal idtipoparametro ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: tipo_parametroDelete( BigDecimal idtipoparametro )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String tipo_parametroCreate(String tipoparametro, String usuarioalt)
			throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (tipoparametro == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipoparametro ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (tipoparametro.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipoparametro ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO TIPO_PARAMETRO(tipoparametro, usuarioalt ) VALUES (?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, tipoparametro);
				insert.setString(2, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String tipo_parametroCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String tipo_parametroCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String tipo_parametroCreateOrUpdate(BigDecimal idtipoparametro,
			String tipoparametro, String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idtipoparametro == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipoparametro ";
		if (tipoparametro == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipoparametro ";

		// 2. sin nada desde la pagina
		if (tipoparametro.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipoparametro ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM tipo_parametro WHERE idtipoparametro = "
					+ idtipoparametro.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE TIPO_PARAMETRO SET tipoparametro=?, usuarioact=?, fechaact=? WHERE idtipoparametro=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, tipoparametro);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idtipoparametro);
				} else {
					String ins = "INSERT INTO TIPO_PARAMETRO(tipoparametro, usuarioalt ) VALUES (?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, tipoparametro);
					insert.setString(2, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String tipo_parametroCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String tipo_parametroCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String tipo_parametroUpdate(BigDecimal idtipoparametro,
			String tipoparametro, String usuarioact) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idtipoparametro == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idtipoparametro ";
		if (tipoparametro == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: tipoparametro ";

		// 2. sin nada desde la pagina
		if (tipoparametro.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: tipoparametro ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM tipo_parametro WHERE idtipoparametro = "
					+ idtipoparametro.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE TIPO_PARAMETRO SET tipoparametro=?, usuarioact=?, fechaact=? WHERE idtipoparametro=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, tipoparametro);
					insert.setString(2, usuarioact);
					insert.setTimestamp(3, fechaact);
					insert.setBigDecimal(4, idtipoparametro);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String tipo_parametroUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String tipo_parametroUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	/**
	 * Metodos para la entidad: setupVariables Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Tue Jul 04 15:26:53 GMT-03:00 2006
	 */
	// para todo (ordena por el segundo campo por defecto)
	public List getSetupVariablesAll(long limit, long offset)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT idvariable,variable,valor,descripcion,usuarioalt,usuarioact,fechaalt,fechaact FROM SETUPVARIABLES ORDER BY 2  LIMIT "
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
			log.error("Error SQL en el metodo : getSetupVariables() "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getSetupVariables()  "
							+ ex);
		}
		return vecSalida;
	}

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getSetupVariablesOcu(long limit, long offset, String ocurrencia)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idvariable,variable,valor,descripcion,usuarioalt,usuarioact,fechaalt,fechaact FROM SETUPVARIABLES WHERE UPPER(VARIABLE) LIKE '%"
				+ ocurrencia.toUpperCase().trim()
				+ "%' ORDER BY 2  LIMIT "
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
					.error("Error SQL en el metodo : getSetupVariables(String ocurrencia) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getSetupVariables(String ocurrencia)  "
							+ ex);
		}
		return vecSalida;
	}

	// por primary key (primer campo por defecto)

	public List getSetupVariablesPK(BigDecimal idvariable) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT  idvariable,variable,valor,descripcion,usuarioalt,usuarioact,fechaalt,fechaact FROM SETUPVARIABLES WHERE idvariable="
				+ idvariable.toString();
		;
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
					.error("Error SQL en el metodo : getSetupVariablesPK( BigDecimal idvariable ) "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("Salida por exception: en el metodo: getSetupVariablesPK( BigDecimal idvariable )  "
							+ ex);
		}
		return vecSalida;
	}

	// ELIMINACION DE UN REGISTRO por primary key (primer campo por defecto)

	public String setupVariablesDelete(BigDecimal idvariable)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "SELECT * FROM SETUPVARIABLES WHERE idvariable="
				+ idvariable.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE FROM SETUPVARIABLES WHERE idvariable="
						+ idvariable.toString();
				statement.execute(cQuery);
				salida = "Baja Correcta.";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Error SQL en el metodo : setupVariablesDelete( BigDecimal idvariable ) "
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible eliminar el registro.";
			log
					.error("Salida por exception: en el metodo: setupVariablesDelete( BigDecimal idvariable )  "
							+ ex);
		}
		return salida;
	}

	// grabacion de un nuevo registro NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria solo usuarioalt

	public String setupVariablesCreate(String variable, String valor,
			String descripcion, String usuarioalt) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (variable == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: variable ";
		if (valor == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: valor ";
		if (usuarioalt == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: usuarioalt ";
		// 2. sin nada desde la pagina
		if (variable.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: variable ";
		if (valor.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: valor ";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: usuarioalt ";

		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			if (!bError) {
				String ins = "INSERT INTO SETUPVARIABLES(variable, valor, descripcion, usuarioalt ) VALUES (?, ?, ?, ?)";
				PreparedStatement insert = dbconn.prepareStatement(ins);
				// seteo de campos:
				insert.setString(1, variable);
				insert.setString(2, valor);
				insert.setString(3, descripcion);
				insert.setString(4, usuarioalt);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "Alta Correcta";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log.error("Error SQL public String setupVariablesCreate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String setupVariablesCreate(.....)"
							+ ex);
		}
		return salida;
	}

	// actualizacion de un registro por PK NOTA: no se tiene en cuenta el primer
	// registro por PK y los datos de auditoria

	public String setupVariablesCreateOrUpdate(BigDecimal idvariable,
			String variable, String valor, String descripcion, String usuarioact)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idvariable == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idvariable ";
		if (variable == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: variable ";
		if (valor == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: valor ";

		// 2. sin nada desde la pagina
		if (variable.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: variable ";
		if (valor.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: valor ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM setupVariables WHERE idvariable = "
					+ idvariable.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE SETUPVARIABLES SET variable=?, valor=?, descripcion=?, usuarioact=?, fechaact=? WHERE idvariable=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, variable);
					insert.setString(2, valor);
					insert.setString(3, descripcion);
					insert.setString(4, usuarioact);
					insert.setTimestamp(5, fechaact);
					insert.setBigDecimal(6, idvariable);
				} else {
					String ins = "INSERT INTO SETUPVARIABLES(variable, valor, descripcion, usuarioalt ) VALUES (?, ?, ?, ?)";
					insert = dbconn.prepareStatement(ins);
					// seteo de campos:
					String usuarioalt = usuarioact; // esta variable va a
					// proposito
					insert.setString(1, variable);
					insert.setString(2, valor);
					insert.setString(3, descripcion);
					insert.setString(4, usuarioalt);
				}
				insert.executeUpdate();
				salida = "Alta Correcta.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error SQL public String setupVariablesCreateOrUpdate(.....)"
							+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible dar de alta el registro.";
			log
					.error("Error excepcion public String setupVariablesCreateOrUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	public String setupVariablesUpdate(BigDecimal idvariable, String variable,
			String valor, String descripcion, String usuarioact)
			throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fechaact = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		// 1. nulidad de campos
		if (idvariable == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: idvariable ";
		if (variable == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: variable ";
		if (valor == null)
			salida = "Error: No se puede dejar sin datos (nulo) el campo: valor ";

		// 2. sin nada desde la pagina
		if (variable.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: variable ";
		if (valor.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el campo: valor ";
		// fin validaciones
		boolean bError = true;
		if (salida.equalsIgnoreCase("NOOK"))
			bError = false;
		try {
			ResultSet rsSalida = null;
			String cQuery = "SELECT COUNT(*) FROM setupVariables WHERE idvariable = "
					+ idvariable.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			String sql = "";
			if (!bError) {
				if (total > 0) { // si existe hago update
					sql = "UPDATE SETUPVARIABLES SET variable=?, valor=?, descripcion=?, usuarioact=?, fechaact=? WHERE idvariable=?;";
					insert = dbconn.prepareStatement(sql);
					insert.setString(1, variable);
					insert.setString(2, valor);
					insert.setString(3, descripcion);
					insert.setString(4, usuarioact);
					insert.setTimestamp(5, fechaact);
					insert.setBigDecimal(6, idvariable);
				}

				int i = insert.executeUpdate();
				if (i > 0)
					salida = "Actualizacion Correcta";
				else
					salida = "Imposible actualizar el registro.";
			}
		} catch (SQLException sqlException) {
			salida = "Imposible actualizar el registro.";
			log.error("Error SQL public String setupVariablesUpdate(.....)"
					+ sqlException);
		} catch (Exception ex) {
			salida = "Imposible actualizar el registro.";
			log
					.error("Error excepcion public String setupVariablesUpdate(.....)"
							+ ex);
		}
		return salida;
	}

	// REPORTES-TABLAS
	//

	public List getReportesTablasAll(int limit, long offset,
			BigDecimal idreporte) throws EJBException {
		/**
		 * Entidad: Grupos X Usuario
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer grupos.
		 */
		ResultSet rsSalida = null;
		String cQuery = ""
				+ " select idtabla, tabla, query_carga, query_consulta, iddatasource  "
				+ "   FROM tablas  "
				+ " WHERE idtabla  IN (SELECT idtabla FROM reportes_tablas WHERE idreporte =  "
				+ idreporte + " )" + " ORDER BY tabla" + " LIMIT " + limit
				+ " OFFSET " + offset;
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
			log.error("getReportesTablasAll()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getReportesTablasAll()- Salida por exception: " + ex);
		}
		return vecSalida;
	}

	public List getReportesTablasAsociar(int limit, long offset,
			BigDecimal idreporte) throws EJBException {
		/**
		 * Entidad: Grupos X Usuario
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer grupos.
		 */
		ResultSet rsSalida = null;
		String cQuery = ""
				+ " select idtabla, tabla, query_carga, query_consulta, iddatasource  "
				+ "   FROM tablas  "
				+ " WHERE idtabla NOT IN (SELECT idtabla FROM reportes_tablas WHERE idreporte =  "
				+ idreporte + " )" + " ORDER BY tabla" + " LIMIT " + limit
				+ " OFFSET " + offset;
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
			log.error("getReportesTablasAsociar()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log
					.error("getReportesTablasAsociar()- Salida por exception: "
							+ ex);
		}
		return vecSalida;
	}

	public String reporteTablaDelete(BigDecimal idreporte, BigDecimal idtabla)
			throws EJBException {
		PreparedStatement insert = null;
		String mensaje = "";
		String tableName = "reportes_tablas";
		try {

			insert = dbconn.prepareStatement("DELETE FROM " + tableName
					+ " WHERE idreporte=? AND idtabla=?");

			insert.setLong(1, idreporte.longValue());
			insert.setLong(2, idtabla.longValue());

			int n = insert.executeUpdate();
			if (n == 1)
				mensaje = "BAJA CORRECTA.";
			if (n == 0)
				mensaje = "RELACION REPORTE-TABLA INEXISTENTE.";

		} catch (Exception e) {
			log.error("reporteTablaDelete(): " + e);
			mensaje = "Error al eliminar  relacion reporte-tabla. ";
		}
		return mensaje;
	}

	public String reporteTablaCreate(BigDecimal idreporte, long[] idtablas,
			String usuarioalt) throws EJBException, SQLException {
		Statement insert = null;
		String mensaje = "";
		String tableName = "reportes_tablas";

		try {

			dbconn.setAutoCommit(false);
			insert = dbconn.createStatement();
			for (int i = 0; i < idtablas.length; i++) {
				insert.addBatch("INSERT INTO " + tableName
						+ "(idreporte, idtabla, orden, usuarioalt)"
						+ " VALUES(" + idreporte + "," + idtablas[i] + ",1 , '"
						+ usuarioalt + "')");
			}
			insert.executeBatch();
			mensaje = "ALTA CORRECTA.";
			dbconn.commit();
			dbconn.setAutoCommit(true);
		} catch (SQLException eSQL) {
			dbconn.rollback();
			dbconn.setAutoCommit(true);
			log.error("reporteTablaCreate(): " + eSQL);
			log.error("reporteTablaCreate() next-Ex: "
					+ eSQL.getNextException());
			mensaje = "Error al ejecutar asociacion reporte-tabla..";
		} catch (Exception e) {
			dbconn.rollback();
			dbconn.setAutoCommit(true);
			log.error("reporteTablaCreate(): " + e);
			mensaje = "Error al ejecutar asociacion reporte-tabla.";
		}

		return mensaje;
	}

	// FIN REPORTES-TABLAS

	// GRUPOS-REPORTES
	//

	public List getGruposReportesAll(int limit, long offset, BigDecimal idgrupo)
			throws EJBException {
		/**
		 * Entidad: Reportes X Grupo
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer reportes.
		 */
		ResultSet rsSalida = null;
		String cQuery = ""
				+ " select idreporte, reporte, comentario, skin  "
				+ "   FROM reportes  "
				+ " WHERE idreporte  IN (SELECT idreporte FROM grupo_reportes WHERE idgrupo =  "
				+ idgrupo + " )" + " ORDER BY reporte" + " LIMIT " + limit
				+ " OFFSET " + offset;
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
					.error("getGruposReportesAll(int limit, long offset,	BigDecimal idgrupo)- Error SQL: "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("getGruposReportesAll(int limit, long offset,	BigDecimal idgrupo)- Salida por exception: "
							+ ex);
		}
		return vecSalida;
	}

	public List getGruposReportesAsociar(int limit, long offset,
			BigDecimal idgrupo) throws EJBException {
		/**
		 * Entidad: Reportes X Grupo
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer reportes.
		 */
		ResultSet rsSalida = null;
		String cQuery = ""
				+ " select idreporte, reporte, comentario, skin  "
				+ "   FROM reportes  "
				+ " WHERE idreporte  NOT IN (SELECT idreporte FROM grupo_reportes WHERE idgrupo =  "
				+ idgrupo + " )" + " ORDER BY reporte" + " LIMIT " + limit
				+ " OFFSET " + offset;
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
			log.error("getGruposReportesAsociar()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log
					.error("getGruposReportesAsociar()- Salida por exception: "
							+ ex);
		}
		return vecSalida;
	}

	public String grupoReporteDelete(BigDecimal idgrupo, BigDecimal idreporte)
			throws EJBException {
		PreparedStatement insert = null;
		String mensaje = "";
		String tableName = "grupo_reportes";
		try {

			insert = dbconn.prepareStatement("DELETE FROM " + tableName
					+ " WHERE idgrupo=? AND idreporte=?");

			insert.setLong(1, idgrupo.longValue());
			insert.setLong(2, idreporte.longValue());

			int n = insert.executeUpdate();
			if (n == 1)
				mensaje = "BAJA CORRECTA.";
			if (n == 0)
				mensaje = "RELACION GRUPO-REPORTE INEXISTENTE.";

		} catch (Exception e) {
			log.error("grupoReporteDelete(): " + e);
			mensaje = "Error al eliminar  relacion grupo-reporte. ";
		}
		return mensaje;
	}

	public String grupoReporteCreate(BigDecimal idgrupo, long[] idreportes,
			String usuarioalt) throws EJBException, SQLException {
		Statement insert = null;
		String mensaje = "";
		String tableName = "grupo_reportes";

		try {

			dbconn.setAutoCommit(false);
			insert = dbconn.createStatement();
			for (int i = 0; i < idreportes.length; i++) {
				insert.addBatch("INSERT INTO " + tableName
						+ "(idgrupo, idreporte,  usuarioalt)" + " VALUES("
						+ idgrupo + "," + idreportes[i] + ", '" + usuarioalt
						+ "')");
			}
			insert.executeBatch();
			mensaje = "ALTA CORRECTA.";
			dbconn.commit();
			dbconn.setAutoCommit(true);
		} catch (SQLException eSQL) {
			dbconn.rollback();
			dbconn.setAutoCommit(true);
			log.error("grupoReporteCreate(): " + eSQL);
			log.error("grupoReporteCreate() next-Ex: "
					+ eSQL.getNextException());
			mensaje = "Error al ejecutar asociacion grupo-reporte..";
		} catch (Exception e) {
			dbconn.rollback();
			dbconn.setAutoCommit(true);
			log.error("reporteTablaCreate(): " + e);
			mensaje = "Error al ejecutar asociacion grupo-reporte.";
		}

		return mensaje;
	}

	// FIN GRUPOS-REPORTES

	// TABLAS - GRAFICOS
	//

	public List getTablasGraficosAll(int limit, long offset, BigDecimal idtabla)
			throws EJBException {
		/**
		 * Entidad: Graficos X Tabla
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer graficos.
		 */
		ResultSet rsSalida = null;
		String cQuery = ""
				+ " select idgrafico, grafico, query_consulta, idtipografico, iddatasource  "
				+ "   FROM grafico  "
				+ " WHERE idgrafico  IN (SELECT idgrafico FROM tablas_graficos WHERE idtabla =  "
				+ idtabla + " )" + " ORDER BY grafico" + " LIMIT " + limit
				+ " OFFSET " + offset;
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
					.error("getTablasGraficosAll(int limit, long offset,	BigDecimal idgrafico)- Error SQL: "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("getTablasGraficosAll(int limit, long offset,	BigDecimal idgrafico)- Salida por exception: "
							+ ex);
		}
		return vecSalida;
	}

	public List getTablasGraficosAsociar(int limit, long offset,
			BigDecimal idtabla) throws EJBException {
		/**
		 * Entidad: Reportes X Grupo
		 * 
		 * @ejb.interface-method view-type = "remote"
		 * @throws SQLException
		 *             Thrown if method fails due to system-level error.
		 *             Utilidad : traer reportes.
		 */
		ResultSet rsSalida = null;
		String cQuery = ""
				+ " select idgrafico, grafico, query_consulta, idtipografico, iddatasource  "
				+ "   FROM grafico  "
				+ " WHERE idgrafico NOT IN (SELECT idgrafico FROM tablas_graficos WHERE idtabla =  "
				+ idtabla + " )" + " ORDER BY grafico" + " LIMIT " + limit
				+ " OFFSET " + offset;
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
			log.error("getTablasGraficosAsociar()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log
					.error("getTablasGraficosAsociar()- Salida por exception: "
							+ ex);
		}
		return vecSalida;
	}

	public String tablaGraficoDelete(BigDecimal idtabla, BigDecimal idgrafico)
			throws EJBException {
		PreparedStatement insert = null;
		String mensaje = "";
		String tableName = "tablas_graficos";
		try {

			insert = dbconn.prepareStatement("DELETE FROM " + tableName
					+ " WHERE idtabla=? AND idgrafico=?");

			insert.setLong(1, idtabla.longValue());
			insert.setLong(2, idgrafico.longValue());

			int n = insert.executeUpdate();
			if (n == 1)
				mensaje = "BAJA CORRECTA.";
			if (n == 0)
				mensaje = "RELACION TABLA-GRAFICO INEXISTENTE.";

		} catch (Exception e) {
			log.error("tablaGraficoDelete(): " + e);
			mensaje = "Error al eliminar  relacion tabla-grafico. ";
		}
		return mensaje;
	}

	public String tablaGraficoCreate(BigDecimal idtabla, long[] idgraficos,
			String usuarioalt) throws EJBException, SQLException {
		Statement insert = null;
		String mensaje = "";
		String tableName = "tablas_graficos";

		try {

			dbconn.setAutoCommit(false);
			insert = dbconn.createStatement();
			for (int i = 0; i < idgraficos.length; i++) {
				insert.addBatch("INSERT INTO " + tableName
						+ "(idtabla, idgrafico, orden, usuarioalt)"
						+ " VALUES(" + idtabla + "," + idgraficos[i] + ", 1, '"
						+ usuarioalt + "')");
			}
			insert.executeBatch();
			mensaje = "ALTA CORRECTA.";
			dbconn.commit();
			dbconn.setAutoCommit(true);
		} catch (SQLException eSQL) {
			dbconn.rollback();
			dbconn.setAutoCommit(true);
			log.error("tablaGraficoCreate(): " + eSQL);
			log.error("tablaGraficoCreate() next-Ex: "
					+ eSQL.getNextException());
			mensaje = "Error al ejecutar asociacion tabla-grafico.";
		} catch (Exception e) {
			dbconn.rollback();
			dbconn.setAutoCommit(true);
			log.error("tablaGraficoCreate(): " + e);
			mensaje = "Error al ejecutar asociacion tabla-grafico.";
		}

		return mensaje;
	}

	// FIN TABLAS - GRAFICOS

	// FIN GRUPOS-REPORTES

	public long getTotalEntidad(String entidad) throws EJBException {

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
			log.error("getTotalEntidad()- Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("getTotalEntidad()- Salida por exception: " + ex);
		}
		return total;
	}

	public long getTotalEntidadOcu(String entidad, String[] campos,
			String ocurrencia) throws EJBException {

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
			cQuery += like;
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
			String[] ocurrencia) throws EJBException {

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

	public String getClientesRemitosPath() throws EJBException {
		return clientesRemitosPath;
	}

	public String getRepositorioDocsPath() throws EJBException {
		return repositorioDocsPath;
	}

}