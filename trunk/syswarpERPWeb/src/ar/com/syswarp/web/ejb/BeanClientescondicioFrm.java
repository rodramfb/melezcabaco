/* 
 javabean para la entidad (Formulario): clientescondicio
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Nov 14 15:31:19 GMT-03:00 2006 
 
 Para manejar la pagina: clientescondicioFrm.jsp
 
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

public class BeanClientescondicioFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanClientescondicioFrm.class);

	private String validar = "";

	private BigDecimal idcondicion = BigDecimal.valueOf(-1);

	private BigDecimal idempresa;

	private String condicion = "";

	private BigDecimal cant_dias = BigDecimal.valueOf(0);

	private BigDecimal cuotas = BigDecimal.valueOf(0);

	private BigDecimal lapso = BigDecimal.valueOf(0);

	private String fac_cred = "";

	// 20120705 - EJV - Mantis 843 -->

	private String ctanetoclie = "";

	private String ctanetoclieDescripcion = "";

	// <--

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanClientescondicioFrm() {
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
			Clientes clientescondicio = Common.getClientes();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = clientescondicio.clientescondicioCreate(
						this.condicion, this.cant_dias, this.cuotas,
						this.lapso, this.fac_cred, new BigDecimal(
								this.ctanetoclie), this.usuarioalt,
						this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clientescondicio.clientescondicioUpdate(
						this.idcondicion, this.condicion, this.cant_dias,
						this.cuotas, this.lapso, this.fac_cred, new BigDecimal(
								this.ctanetoclie), this.usuarioact,
						this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clientescondicio.clientescondicioDelete(
						this.idcondicion, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosClientescondicio() {
		try {
			Clientes clientescondicio = Common.getClientes();
			List listClientescondicio = clientescondicio.getClientescondicioPK(
					this.idcondicion, this.idempresa);
			Iterator iterClientescondicio = listClientescondicio.iterator();
			if (iterClientescondicio.hasNext()) {
				String[] uCampos = (String[]) iterClientescondicio.next();
				// TODO: Constructores para cada tipo de datos
				this.idcondicion = BigDecimal.valueOf(Long
						.parseLong(uCampos[0]));
				this.condicion = uCampos[1];
				this.cant_dias = BigDecimal.valueOf(Long.parseLong(uCampos[2]));
				this.cuotas = BigDecimal.valueOf(Long.parseLong(uCampos[3]));
				this.lapso = BigDecimal.valueOf(Long.parseLong(uCampos[4]));
				this.fac_cred = uCampos[5];
				this.ctanetoclie = uCampos[6];
				this.ctanetoclieDescripcion = uCampos[7];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosClientescondicio()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("clientescondicioAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (condicion == null) {
						this.mensaje = "No se puede dejar vacio el campo condicion ";
						return false;
					}
					if (cant_dias == null) {
						this.mensaje = "No se puede dejar vacio el campo cant_dias ";
						return false;
					}
					if (cuotas == null) {
						this.mensaje = "No se puede dejar vacio el campo cuotas ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (condicion.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo condicion";
						return false;
					}

					if (!Common.esEntero(this.ctanetoclie)
							|| Long.parseLong(this.ctanetoclie) < 1) {
						this.mensaje = "Es necesario ingresar Cta. Neto Clientes Activa .";
						return false;
					}

				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosClientescondicio();
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
	public BigDecimal getIdcondicion() {
		return idcondicion;
	}

	public void setIdcondicion(BigDecimal idcondicion) {
		this.idcondicion = idcondicion;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public BigDecimal getCant_dias() {
		return cant_dias;
	}

	public void setCant_dias(BigDecimal cant_dias) {
		this.cant_dias = cant_dias;
	}

	public BigDecimal getCuotas() {
		return cuotas;
	}

	public void setCuotas(BigDecimal cuotas) {
		this.cuotas = cuotas;
	}

	public BigDecimal getLapso() {
		return lapso;
	}

	public void setLapso(BigDecimal lapso) {
		this.lapso = lapso;
	}

	public String getFac_cred() {
		return fac_cred;
	}

	public void setFac_cred(String fac_cred) {
		this.fac_cred = fac_cred;
	}

	// 20120705 - EJV - Mantis 843 -->

	public String getCtanetoclie() {
		return ctanetoclie;
	}

	public void setCtanetoclie(String ctanetoclie) {
		this.ctanetoclie = ctanetoclie;
	}

	public String getCtanetoclieDescripcion() {
		return ctanetoclieDescripcion;
	}

	public void setCtanetoclieDescripcion(String ctanetoclieDescripcion) {
		this.ctanetoclieDescripcion = ctanetoclieDescripcion;
	}

	// <--

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

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}
}
