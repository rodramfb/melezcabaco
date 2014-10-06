/* 
 javabean para la entidad (Formulario): globalentidadesasociacionesmov
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Wed May 07 11:53:08 CEST 2008 
 
 Para manejar la pagina: globalentidadesasociacionesmovFrm.jsp
 
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

public class BeanAsociarDocumentosPasoDos implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanAsociarDocumentosPasoDos.class);

	private String validar = "";

	private BigDecimal identidadesasociacionesmov = BigDecimal.valueOf(-1);

	private BigDecimal identidadorigen = BigDecimal.valueOf(0);

	private String entidadorigen = "";

	private BigDecimal identidaddestino = BigDecimal.valueOf(0);

	private String entidaddestino = "";

	private BigDecimal pkorigen = BigDecimal.valueOf(0);

	private String docOrigen = "";

	private BigDecimal pkdestino = BigDecimal.valueOf(0);

	private String docDestino = "";

	private String fecha = "";

	private String observacion = "";

	private BigDecimal idempresa;

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanAsociarDocumentosPasoDos() {
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

			General globalentidadesasociacionesmov = Common.getGeneral();
			this.mensaje = globalentidadesasociacionesmov
					.globalentidadesasociacionesmovCreate(this.identidadorigen,
							this.identidaddestino, this.pkorigen,
							this.pkdestino, (java.sql.Date) Common
									.setObjectToStrOrTime(this.fecha,
											"StrToJSDate"), this.observacion,
							this.idempresa, this.usuarioalt);

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	private void getDatosEntidadesAsociables(BigDecimal idEntidad,
			String entidad) {
		try {
			General gral = Common.getGeneral();
			List listEntidad = gral.getGlobalentidadesasociablesPK(idEntidad,
					this.idempresa);
			Iterator iterEntidad = listEntidad.iterator();
			if (iterEntidad.hasNext()) {
				String[] uCampos = (String[]) iterEntidad.next();

				if (entidad.equalsIgnoreCase("O"))
					this.entidadorigen = uCampos[2];
				else if (entidad.equalsIgnoreCase("D"))
					this.entidaddestino = uCampos[2];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosEntidadesAsociables()" + e);
		}
	}

	private void setLabelPage() {

		try {

			getDatosEntidadesAsociables(this.identidadorigen, "O");

			getDatosEntidadesAsociables(this.identidaddestino, "D");

		} catch (Exception e) {
			log.error("setLabelPage: " + e);
		}

	}

	public boolean ejecutarValidacion() {
		try {
			
			General gral = Common.getGeneral();
			
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("asociarDocumentosPasoUno.jsp");
				return true;
			}

			this.setLabelPage();

			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (identidadorigen == null
							|| identidadorigen.longValue() < 1) {
						this.mensaje = "No es posible capturar el tipo de asociacion, por favor intente nuevamente(1). ";
						return false;
					}
					if (identidaddestino == null
							|| identidaddestino.longValue() < 0) {
						this.mensaje = "No es posible capturar el tipo de asociacion, por favor intente nuevamente(2). ";
						return false;
					}
					if (pkorigen == null || pkorigen.longValue() < 1) {
						this.mensaje = "No se puede dejar vacio el campo Registro Origen. ";
						return false;
					}
					if (pkdestino == null || pkdestino.longValue() < 1) {
						this.mensaje = "No se puede dejar vacio el campo Registro a Relacionar ";
						return false;
					}
					if (!Common.isFechaValida(this.fecha)
							|| !Common.isFormatoFecha(this.fecha)) {
						this.mensaje = "Ingrese un valor valido para el campo fecha.";
						return false;
					}

					if(gral.isExisteAsociacion(this.pkorigen, this.pkdestino, this.idempresa)){
						this.mensaje = "Los registros seleccionados ya fueron asociados previamente.";
						return false;
					}
					
					// 2. len 0 para campos nulos
				}
				this.ejecutarSentenciaDML();
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
	public BigDecimal getIdentidadesasociacionesmov() {
		return identidadesasociacionesmov;
	}

	public void setIdentidadesasociacionesmov(
			BigDecimal identidadesasociacionesmov) {
		this.identidadesasociacionesmov = identidadesasociacionesmov;
	}

	public BigDecimal getIdentidadorigen() {
		return identidadorigen;
	}

	public void setIdentidadorigen(BigDecimal identidadorigen) {
		this.identidadorigen = identidadorigen;
	}

	public BigDecimal getIdentidaddestino() {
		return identidaddestino;
	}

	public void setIdentidaddestino(BigDecimal identidaddestino) {
		this.identidaddestino = identidaddestino;
	}

	public String getDocDestino() {
		return docDestino;
	}

	public void setDocDestino(String docDestino) {
		this.docDestino = docDestino;
	}

	public BigDecimal getPkorigen() {
		return pkorigen;
	}

	public void setPkorigen(BigDecimal pkorigen) {
		this.pkorigen = pkorigen;
	}

	public String getDocOrigen() {
		return docOrigen;
	}

	public void setDocOrigen(String docOrigen) {
		this.docOrigen = docOrigen;
	}

	public BigDecimal getPkdestino() {
		return pkdestino;
	}

	public void setPkdestino(BigDecimal pkdestino) {
		this.pkdestino = pkdestino;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
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

	public String getEntidadorigen() {
		return entidadorigen;
	}

	public void setEntidadorigen(String entidadorigen) {
		this.entidadorigen = entidadorigen;
	}

	public String getEntidaddestino() {
		return entidaddestino;
	}

	public void setEntidaddestino(String entidaddestino) {
		this.entidaddestino = entidaddestino;
	}
}
