/* 
 javabean para la entidad (Formulario): dataSources
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Mon Jul 03 12:29:55 GMT-03:00 2006 
 
 Para manejar la pagina: dataSourcesFrm.jsp
 
 */
package ar.com.syswarp.web.ejb.report;

import java.io.Serializable;
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import java.math.*;
import java.util.*;
import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanDataSourcesFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanDataSourcesFrm.class);

	private String validar = "";

	private BigDecimal iddatasource = BigDecimal.valueOf(-1);

	private String datasource = "";

	private String driver = "";

	private String db_user = "";

	private String db_pass = "";

	private String url = "";

	private String usuarioalt = "";

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanDataSourcesFrm() {
		super();
	}

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

	public void ejecutarSentenciaDML() {
		try {
			Report dataSources = Common.getReport();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = dataSources.dataSourcesCreate(this.datasource,
						this.driver, this.db_user, this.db_pass, this.url,
						this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = dataSources.dataSourcesUpdate(this.iddatasource,
						this.datasource, this.driver, this.db_user,
						this.db_pass, this.url, this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = dataSources.dataSourcesDelete(this.iddatasource);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosDataSources() {
		try {
			Report dataSources = Common.getReport();
			List listDataSources = dataSources
					.getDataSourcesPK(this.iddatasource);
			Iterator iterDataSources = listDataSources.iterator();
			if (iterDataSources.hasNext()) {
				String[] uCampos = (String[]) iterDataSources.next();
				// TODO: Constructores para cada tipo de datos
				this.iddatasource = BigDecimal.valueOf(Long
						.parseLong(uCampos[0]));
				this.datasource = uCampos[1];
				this.driver = uCampos[2];
				this.db_user = uCampos[3];
				this.db_pass = uCampos[4];
				this.url = uCampos[5];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosDataSources()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("dataSourcesAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (datasource == null) {
						this.mensaje = "No se puede dejar vacio el campo datasource ";
						return false;
					}
					if (driver == null) {
						this.mensaje = "No se puede dejar vacio el campo driver ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (datasource.trim().length() == 0) {
						this.mensaje = "No se puede dejar con longitud 0 el campo datasource  ";
						return false;
					}
					if (driver.trim().length() == 0) {
						this.mensaje = "No se puede dejar con longitud 0 el campo driver  ";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosDataSources();
				}
			}
		} catch (Exception e) {
			log.error(" ejecutarValidacion(): " + e);
		}
		return true;
	}

	public String getValidar() {
		return validar;
	}

	public void setValidar(String validar) {
		this.validar = validar;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getVolver() {
		return volver;
	}

	public void setVolver(String volver) {
		this.volver = volver;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	// metodos para cada atributo de la entidad
	public BigDecimal getIddatasource() {
		return iddatasource;
	}

	public void setIddatasource(BigDecimal iddatasource) {
		this.iddatasource = iddatasource;
	}

	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getDb_user() {
		return db_user;
	}

	public void setDb_user(String db_user) {
		this.db_user = db_user;
	}

	public String getDb_pass() {
		return db_pass;
	}

	public void setDb_pass(String db_pass) {
		this.db_pass = db_pass;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsuarioalt() {
		return usuarioalt;
	}

	public void setUsuarioalt(String usuarioalt) {
		this.usuarioalt = usuarioalt;
	}

	public String getUsuarioact() {
		return usuarioact;
	}

	public void setUsuarioact(String usuarioact) {
		this.usuarioact = usuarioact;
	}
}
