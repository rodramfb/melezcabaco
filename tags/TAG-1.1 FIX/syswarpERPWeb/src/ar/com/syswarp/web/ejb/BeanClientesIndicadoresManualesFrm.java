/* 
   javabean para la entidad (Formulario): clientesIndicadoresManuales
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Apr 30 10:30:20 GMT-03:00 2010 
   
   Para manejar la pagina: clientesIndicadoresManualesFrm.jsp
      
 */
package ar.com.syswarp.web.ejb;

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

public class BeanClientesIndicadoresManualesFrm implements SessionBean,
		Serializable {

	private SessionContext context;

	static Logger log = Logger
			.getLogger(BeanClientesIndicadoresManualesFrm.class);

	private String validar = "";

	private BigDecimal idindicador = new BigDecimal(-1);

	private String indicador = "";

	private BigDecimal idtipoindicador = new BigDecimal(-1);

	private String queryseleccion = "";

	private BigDecimal idempresa = new BigDecimal(-1);

	private String usuarioalt = "";

	private String usuarioact = "";

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private List listIndicadoresTipos = new ArrayList();

	public BeanClientesIndicadoresManualesFrm() {
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
			Clientes clientes = Common.getClientes();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = clientes.clientesIndicadoresManualesCreate(
						this.indicador, this.idtipoindicador,
						this.queryseleccion, this.idempresa, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clientes.clientesIndicadoresManualesUpdate(
						this.idindicador, this.indicador, this.idtipoindicador,
						this.queryseleccion, this.idempresa, this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clientes.clientesIndicadoresManualesDelete(
						this.idindicador, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosClientesIndicadoresManuales() {
		try {
			Clientes clientes = Common.getClientes();
			List listClientesIndicadoresManuales = clientes
					.getClientesIndicadoresManualesPK(this.idindicador,
							this.idempresa);
			Iterator iterClientesIndicadoresManuales = listClientesIndicadoresManuales
					.iterator();
			if (iterClientesIndicadoresManuales.hasNext()) {
				String[] uCampos = (String[]) iterClientesIndicadoresManuales
						.next();
				// TODO: Constructores para cada tipo de datos
				this.idindicador = new BigDecimal(uCampos[0]);
				this.indicador = uCampos[1];
				this.idtipoindicador = new BigDecimal(uCampos[2]);
				this.queryseleccion = uCampos[3];
				this.idempresa = new BigDecimal(uCampos[4]);
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosClientesIndicadoresManuales()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("clientesIndicadoresManualesAbm.jsp");
				return true;
			}

			this.listIndicadoresTipos = Common.getClientes()
					.getClientesIndicadoresTiposAll(300, 0, this.idempresa);

			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (indicador == null) {
						this.mensaje = "No se puede dejar vacio el campo indicador ";
						return false;
					}
					if (idtipoindicador == null || idtipoindicador.longValue()<0) {
						this.mensaje = "No se puede dejar vacio el campo idtipoindicador ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (indicador.trim().length() == 0) {
						this.mensaje = "No se puede dejar con longitud 0 el campo indicador  ";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosClientesIndicadoresManuales();
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
	public BigDecimal getIdindicador() {
		return idindicador;
	}

	public void setIdindicador(BigDecimal idindicador) {
		this.idindicador = idindicador;
	}

	public String getIndicador() {
		return indicador;
	}

	public void setIndicador(String indicador) {
		this.indicador = indicador;
	}

	public BigDecimal getIdtipoindicador() {
		return idtipoindicador;
	}

	public void setIdtipoindicador(BigDecimal idtipoindicador) {
		this.idtipoindicador = idtipoindicador;
	}

	public String getQueryseleccion() {
		return queryseleccion;
	}

	public void setQueryseleccion(String queryseleccion) {
		this.queryseleccion = queryseleccion;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
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

	public List getListIndicadoresTipos() {
		return listIndicadoresTipos;
	}

}
