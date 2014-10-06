/* 
 javabean para la entidad (Formulario): tablas
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Wed Jun 28 16:03:13 GMT-03:00 2006 
 
 Para manejar la pagina: tablasFrm.jsp
 
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
import java.util.*;

import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanTablasFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanTablasFrm.class);

	private String validar = "";

	private long idtabla = -1l;

	private String tabla = "";

	private String query_carga = "";

	private String query_consulta = "";

	private long iddatasource = 0;

	private String mensaje = "";

	private String volver = "";

	private List datasourceList = new ArrayList();

	private Hashtable htDT = new Hashtable();

	private Iterator iterDT = null;

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String usuarioalt = "";

	private String usuarioact = "";

	private String accion = "";

	public BeanTablasFrm() {
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
			Report reporting = Common.getReport();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = reporting.tablasCreate(this.tabla,
						this.query_carga, this.query_consulta,
						this.iddatasource, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = reporting.tablasUpdate(this.idtabla, this.tabla,
						this.query_carga, this.query_consulta,
						this.iddatasource, this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = reporting.tablasDelete(this.idtabla);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosTablas() {
		try {
			Report reporting = Common.getReport();
			List listTablas = reporting.getTablasPK(this.idtabla);
			Iterator iterTablas = listTablas.iterator();
			if (iterTablas.hasNext()) {
				String[] uCampos = (String[]) iterTablas.next();
				// TODO: Constructores para cada tipo de datos
				this.idtabla = Long.parseLong(uCampos[0]);
				this.tabla = uCampos[1];
				this.query_carga = uCampos[2];
				this.query_consulta = uCampos[3];
				this.iddatasource = Long.parseLong(uCampos[4]);
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosUsuario()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {

			Report reporting = Common.getReport();
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("tablasAbm.jsp");
				return true;
			}

			this.datasourceList = reporting.getDatasourcesAll();
			this.iterDT = this.datasourceList.iterator();
			while (iterDT.hasNext()) {
				String[] dtCampos = (String[]) iterDT.next();
				this.htDT.put(dtCampos[0], dtCampos[1]);
			}

			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					if (this.tabla.trim().equals("")) {
						this.mensaje = "Ingrese tabla.";
						return false;
					}

					if (this.query_consulta.trim().equals("")) {
						this.mensaje = "Ingrese consulta.";
						return false;
					}
					if (this.query_carga.indexOf("'") >= 0) {
						this.mensaje = "Caracteres inv�lidos en campo consulta carga.";
						return false;
					}
					if (this.query_consulta.indexOf("'") >= 0) {
						this.mensaje = "Caracteres inv�lidos en campo consulta.";
						return false;
					}

					if (this.iddatasource == 0) {
						this.mensaje = "Ingrese datasource asociado a la consulta.";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosTablas();
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

	public long getIddatasource() {
		return iddatasource;
	}

	public void setIddatasource(long iddatasource) {
		this.iddatasource = iddatasource;
	}

	public long getIdtabla() {
		return idtabla;
	}

	public void setIdtabla(long idtabla) {
		this.idtabla = idtabla;
	}

	public String getQuery_carga() {
		return query_carga;
	}

	public void setQuery_carga(String query_carga) {
		this.query_carga = query_carga;
	}

	public String getQuery_consulta() {
		return query_consulta;
	}

	public void setQuery_consulta(String query_consulta) {
		this.query_consulta = query_consulta;
	}

	public String getTabla() {
		return tabla;
	}

	public void setTabla(String tabla) {
		this.tabla = tabla;
	}

	public Hashtable getHtDT() {
		return htDT;
	}

	public void setHtDT(Hashtable htDT) {
		this.htDT = htDT;
	}

	public String getUsuarioact() {
		return usuarioact;
	}

	public void setUsuarioact(String usuarioact) {
		this.usuarioact = usuarioact;
	}

	public String getUsuarioalt() {
		return usuarioalt;
	}

	public void setUsuarioalt(String usuarioalt) {
		this.usuarioalt = usuarioalt;
	}

}
