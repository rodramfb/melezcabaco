/* 
   javabean para la entidad (Formulario): bacoClustersLogistica
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Mar 31 16:05:57 ART 2011 
   
   Para manejar la pagina: bacoClustersLogisticaFrm.jsp
      
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

public class BeanBacoClustersLogisticaFrm implements SessionBean, Serializable {

	private SessionContext context;

	static Logger log = Logger.getLogger(BeanBacoClustersLogisticaFrm.class);

	private String validar = "";

	private BigDecimal idclusterlogistica = new BigDecimal(-1);

	private String clusterlogistica = "";

	private BigDecimal idempresa = new BigDecimal(-1);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanBacoClustersLogisticaFrm() {
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
				this.mensaje = clientes.bacoClustersLogisticaCreate(
						this.clusterlogistica, this.idempresa, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clientes.bacoClustersLogisticaUpdate(
						this.idclusterlogistica, this.clusterlogistica,
						this.idempresa, this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clientes.bacoClustersLogisticaDelete(
						this.idclusterlogistica, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosBacoClustersLogistica() {
		try {
			Clientes clientes = Common.getClientes();
			List listBacoClustersLogistica = clientes
					.getBacoClustersLogisticaPK(this.idclusterlogistica,
							this.idempresa);
			Iterator iterBacoClustersLogistica = listBacoClustersLogistica
					.iterator();
			if (iterBacoClustersLogistica.hasNext()) {
				String[] uCampos = (String[]) iterBacoClustersLogistica.next();
				// TODO: Constructores para cada tipo de datos
				this.idclusterlogistica = new BigDecimal(uCampos[0]);
				this.clusterlogistica = uCampos[1];
				this.idempresa = new BigDecimal(uCampos[2]);
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosBacoClustersLogistica()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("bacoClustersLogisticaAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					// 2. len 0 para campos nulos
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosBacoClustersLogistica();
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
	public BigDecimal getIdclusterlogistica() {
		return idclusterlogistica;
	}

	public void setIdclusterlogistica(BigDecimal idclusterlogistica) {
		this.idclusterlogistica = idclusterlogistica;
	}

	public String getClusterlogistica() {
		return clusterlogistica;
	}

	public void setClusterlogistica(String clusterlogistica) {
		this.clusterlogistica = clusterlogistica;
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
}
