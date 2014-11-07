/* 
 javabean para la entidad (Formulario): globalplantas
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Thu Feb 08 12:59:33 GMT-03:00 2007 
 
 Para manejar la pagina: globalplantasFrm.jsp
 
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

public class BeanGlobalplantasFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanGlobalplantasFrm.class);

	private String validar = "";

	private BigDecimal idplanta = BigDecimal.valueOf(-1);

	private String planta = "";

	private String domicilio = "";

	private BigDecimal idlocalidad = BigDecimal.valueOf(0);

	private String localidad = "";

	private String codpostal = "";

	private String telefonos = "";

	private String fax = "";

	private String tareaquedesa = "";

	private String email = "";

	private String esheadquarter = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanGlobalplantasFrm() {
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
			General globalplantas = Common.getGeneral();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = globalplantas.globalplantasCreate(this.planta,
						this.domicilio, this.idlocalidad, this.codpostal,
						this.telefonos, this.fax, this.tareaquedesa,
						this.email, this.esheadquarter, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = globalplantas.globalplantasUpdate(this.idplanta,
						this.planta, this.domicilio, this.idlocalidad,
						this.codpostal, this.telefonos, this.fax,
						this.tareaquedesa, this.email, this.esheadquarter,
						this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = globalplantas.globalplantasDelete(this.idplanta);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosGlobalplantas() {
		try {
			General globalplantas = Common.getGeneral();
			List listGlobalplantas = globalplantas
					.getGlobalplantasPK(this.idplanta);
			Iterator iterGlobalplantas = listGlobalplantas.iterator();
			if (iterGlobalplantas.hasNext()) {
				String[] uCampos = (String[]) iterGlobalplantas.next();
				// TODO: Constructores para cada tipo de datos
				this.idplanta = BigDecimal.valueOf(Long.parseLong(uCampos[0]));
				this.planta = uCampos[1];
				this.domicilio = uCampos[2];
				this.idlocalidad = BigDecimal.valueOf(Long
						.parseLong(uCampos[3]));
				this.localidad = uCampos[4];
				this.codpostal = uCampos[5];
				this.telefonos = uCampos[6];
				this.fax = uCampos[7];
				this.tareaquedesa = uCampos[8];
				this.email = uCampos[9];
				this.esheadquarter = uCampos[10];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosGlobalplantas()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("globalplantasAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (planta == null) {
						this.mensaje = "No se puede dejar vacio el campo planta ";
						return false;
					}
					if (domicilio == null) {
						this.mensaje = "No se puede dejar vacio el campo domicilio ";
						return false;
					}
					if (idlocalidad == null) {
						this.mensaje = "No se puede dejar vacio el campo idlocalidad ";
						return false;
					}
					if (codpostal == null) {
						this.mensaje = "No se puede dejar vacio el campo codpostal ";
						return false;
					}
					if (tareaquedesa == null) {
						this.mensaje = "No se puede dejar vacio el campo tareaquedesa ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (planta.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Planta  ";
						return false;
					}
					if (domicilio.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Domicilio  ";
						return false;
					}
					if (codpostal.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Cod postal  ";
						return false;
					}
					if (tareaquedesa.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Tarea que desarrolla  ";
						return false;
					}
					if (localidad.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Localidad  ";
						return false;
					}

				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosGlobalplantas();
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
	public BigDecimal getIdplanta() {
		return idplanta;
	}

	public void setIdplanta(BigDecimal idplanta) {
		this.idplanta = idplanta;
	}

	public String getPlanta() {
		return planta;
	}

	public void setPlanta(String planta) {
		this.planta = planta;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public BigDecimal getIdlocalidad() {
		return idlocalidad;
	}

	public void setIdlocalidad(BigDecimal idlocalidad) {
		this.idlocalidad = idlocalidad;
	}

	public String getCodpostal() {
		return codpostal;
	}

	public void setCodpostal(String codpostal) {
		this.codpostal = codpostal;
	}

	public String getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(String telefonos) {
		this.telefonos = telefonos;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getTareaquedesa() {
		return tareaquedesa;
	}

	public void setTareaquedesa(String tareaquedesa) {
		this.tareaquedesa = tareaquedesa;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEsheadquarter() {
		return esheadquarter;
	}

	public void setEsheadquarter(String esheadquarter) {
		this.esheadquarter = esheadquarter;
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

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
}
