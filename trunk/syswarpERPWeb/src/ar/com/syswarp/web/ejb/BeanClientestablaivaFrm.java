/* 
 javabean para la entidad (Formulario): clientestablaiva
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Nov 14 15:07:14 GMT-03:00 2006 
 
 Para manejar la pagina: clientestablaivaFrm.jsp
 
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

public class BeanClientestablaivaFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanClientestablaivaFrm.class);

	private String validar = "";

	private BigDecimal idtipoiva = BigDecimal.valueOf(-1);
	
	private BigDecimal idempresa;

	private String tipoiva = "";

	private Double porcent1 = Double.valueOf("0");

	private String descrimina = "";

	private String desglosa = "";

	private Double porcent2 = Double.valueOf("0");

	private String letra = "";

	private BigDecimal ctapromo = BigDecimal.valueOf(0);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanClientestablaivaFrm() {
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
			Clientes clientestablaiva = Common.getClientes();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = clientestablaiva.clientestablaivaCreate(
						this.tipoiva, this.porcent1, this.descrimina,
						this.desglosa, this.porcent2, this.letra,
						this.ctapromo, this.usuarioalt,this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clientestablaiva.clientestablaivaUpdate(
						this.idtipoiva, this.tipoiva, this.porcent1,
						this.descrimina, this.desglosa, this.porcent2,
						this.letra, this.ctapromo, this.usuarioact,this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clientestablaiva
						.clientestablaivaDelete(this.idtipoiva,this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosClientestablaiva() {
		try {
			Clientes clientestablaiva = Common.getClientes();
			List listClientestablaiva = clientestablaiva
					.getClientestablaivaPK(this.idtipoiva,this.idempresa);
			Iterator iterClientestablaiva = listClientestablaiva.iterator();
			if (iterClientestablaiva.hasNext()) {
				String[] uCampos = (String[]) iterClientestablaiva.next();
				// TODO: Constructores para cada tipo de datos
				this.idtipoiva = BigDecimal.valueOf(Long.parseLong(uCampos[0]));
				this.tipoiva = uCampos[1];
				this.porcent1 = Double.valueOf(uCampos[2]);
				this.descrimina = uCampos[3];
				this.desglosa = uCampos[4];
				this.porcent2 = Double.valueOf(uCampos[5]);
				this.letra = uCampos[6];
				this.ctapromo = BigDecimal.valueOf(Long.parseLong(uCampos[7]));
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosClientestablaiva()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("clientestablaivaAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (tipoiva == null) {
						this.mensaje = "No se puede dejar vacio el campo Tipo iva ";
						return false;
					}
					if (porcent1 == null) {
						this.mensaje = "No se puede dejar vacio el campo Porcentaje 1 ";
						return false;
					}
					if (descrimina == null) {
						this.mensaje = "No se puede dejar vacio el campo Descrimina ";
						return false;
					}
					if (desglosa == null) {
						this.mensaje = "No se puede dejar vacio el campo Desglosa ";
						return false;
					}
					if (porcent2 == null) {
						this.mensaje = "No se puede dejar vacio el campo Porcentaje 2 ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (tipoiva.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Tipo iva ";
						return false;
					}
					if (descrimina.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Descrimina ";
						return false;
					}
					if (desglosa.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Desglosa ";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosClientestablaiva();
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
	public BigDecimal getIdtipoiva() {
		return idtipoiva;
	}

	public void setIdtipoiva(BigDecimal idtipoiva) {
		this.idtipoiva = idtipoiva;
	}

	public String getTipoiva() {
		return tipoiva;
	}

	public void setTipoiva(String tipoiva) {
		this.tipoiva = tipoiva;
	}

	public Double getPorcent1() {
		return porcent1;
	}

	public void setPorcent1(Double porcent1) {
		this.porcent1 = porcent1;
	}

	public String getDescrimina() {
		return descrimina;
	}

	public void setDescrimina(String descrimina) {
		this.descrimina = descrimina;
	}

	public String getDesglosa() {
		return desglosa;
	}

	public void setDesglosa(String desglosa) {
		this.desglosa = desglosa;
	}

	public Double getPorcent2() {
		return porcent2;
	}

	public void setPorcent2(Double porcent2) {
		this.porcent2 = porcent2;
	}

	public String getLetra() {
		return letra;
	}

	public void setLetra(String letra) {
		this.letra = letra;
	}

	public BigDecimal getCtapromo() {
		return ctapromo;
	}

	public void setCtapromo(BigDecimal ctapromo) {
		this.ctapromo = ctapromo;
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

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}
}
