/* 
 javabean para la entidad (Formulario): globalpuestos
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Thu Feb 08 12:58:14 GMT-03:00 2007 
 
 Para manejar la pagina: globalpuestosFrm.jsp
 
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

public class BeanGlobalpuestosFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanGlobalpuestosFrm.class);

	private String validar = "";

	private BigDecimal idpuesto = BigDecimal.valueOf(-1);

	private String puesto = "";

	private BigDecimal idplanta = BigDecimal.valueOf(0);
	
	private BigDecimal idempresa ;

	private String planta = "";

	private String idconta_facturasa = "";

	private String conta_facturasa = "";

	private String idconta_facturasb = "";

	private String conta_facturasb = "";

	private String idconta_facturasc = "";

	private String conta_facturasc = "";

	private String idconta_recibos = "";

	private String conta_recibos = "";

	private String idconta_remitos1 = "";

	private String conta_remitos1 = "";

	private String idconta_remitos2 = "";

	private String conta_remitos2 = "";

	private String idconta_remitos3 = "";

	private String conta_remitos3 = "";

	private String idconta_remitos4 = "";

	private String conta_remitos4 = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanGlobalpuestosFrm() {
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
			General globalpuestos = Common.getGeneral();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = globalpuestos.globalpuestosCreate(this.puesto,
						this.idplanta, this.idconta_facturasa,
						this.idconta_facturasb, this.idconta_facturasc,
						this.idconta_recibos, this.idconta_remitos1,
						this.idconta_remitos2, this.idconta_remitos3,
						this.idconta_remitos4, this.usuarioalt,this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = globalpuestos.globalpuestosUpdate(this.idpuesto,
						this.puesto, this.idplanta, this.idconta_facturasa,
						this.idconta_facturasb, this.idconta_facturasc,
						this.idconta_recibos, this.idconta_remitos1,
						this.idconta_remitos2, this.idconta_remitos3,
						this.idconta_remitos4, this.usuarioact,this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = globalpuestos.globalpuestosDelete(this.idpuesto,this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosGlobalpuestos() {
		try {
			General globalpuestos = Common.getGeneral();
			List listGlobalpuestos = globalpuestos
					.getGlobalpuestosPK(this.idpuesto,this.idempresa);
			Iterator iterGlobalpuestos = listGlobalpuestos.iterator();
			if (iterGlobalpuestos.hasNext()) {
				String[] uCampos = (String[]) iterGlobalpuestos.next();
				// TODO: Constructores para cada tipo de datos
				this.idpuesto = BigDecimal.valueOf(Long.parseLong(uCampos[0]));
				this.puesto = uCampos[1];
				this.idplanta = BigDecimal.valueOf(Long.parseLong(uCampos[2]));
				this.planta = uCampos[3];
				this.idconta_facturasa = uCampos[4];
				this.conta_facturasa = uCampos[5];
				this.idconta_facturasb = uCampos[6];
				this.conta_facturasb = uCampos[7];
				this.idconta_facturasc = uCampos[8];
				this.conta_facturasc = uCampos[9];
				this.idconta_recibos = uCampos[10];
				this.conta_recibos = uCampos[11];
				this.idconta_remitos1 = uCampos[12];
				this.conta_remitos1 = uCampos[13];
				this.idconta_remitos2 = uCampos[14];
				this.conta_remitos2 = uCampos[15];
				this.idconta_remitos3 = uCampos[16];
				this.conta_remitos3 = uCampos[17];
				this.idconta_remitos4 = uCampos[18];
				this.conta_remitos4 = uCampos[19];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosGlobalpuestos()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("globalpuestosAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (puesto == null) {
						this.mensaje = "No se puede dejar vacio el campo puesto ";
						return false;
					}
					if (idplanta == null) {
						this.mensaje = "No se puede dejar vacio el campo idplanta ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (puesto.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Puesto  ";
						return false;
					}
					if (planta.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Planta  ";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosGlobalpuestos();
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
	public BigDecimal getIdpuesto() {
		return idpuesto;
	}

	public void setIdpuesto(BigDecimal idpuesto) {
		this.idpuesto = idpuesto;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public BigDecimal getIdplanta() {
		return idplanta;
	}

	public void setIdplanta(BigDecimal idplanta) {
		this.idplanta = idplanta;
	}

	public String getConta_facturasa() {
		return conta_facturasa;
	}

	public void setConta_facturasa(String conta_facturasa) {
		this.conta_facturasa = conta_facturasa;
	}

	public String getConta_facturasb() {
		return conta_facturasb;
	}

	public void setConta_facturasb(String conta_facturasb) {
		this.conta_facturasb = conta_facturasb;
	}

	public String getConta_facturasc() {
		return conta_facturasc;
	}

	public void setConta_facturasc(String conta_facturasc) {
		this.conta_facturasc = conta_facturasc;
	}

	public String getConta_recibos() {
		return conta_recibos;
	}

	public void setConta_recibos(String conta_recibos) {
		this.conta_recibos = conta_recibos;
	}

	public String getConta_remitos1() {
		return conta_remitos1;
	}

	public void setConta_remitos1(String conta_remitos1) {
		this.conta_remitos1 = conta_remitos1;
	}

	public String getConta_remitos2() {
		return conta_remitos2;
	}

	public void setConta_remitos2(String conta_remitos2) {
		this.conta_remitos2 = conta_remitos2;
	}

	public String getConta_remitos3() {
		return conta_remitos3;
	}

	public void setConta_remitos3(String conta_remitos3) {
		this.conta_remitos3 = conta_remitos3;
	}

	public String getConta_remitos4() {
		return conta_remitos4;
	}

	public void setConta_remitos4(String conta_remitos4) {
		this.conta_remitos4 = conta_remitos4;
	}

	public String getIdconta_facturasa() {
		return idconta_facturasa;
	}

	public void setIdconta_facturasa(String idconta_facturasa) {
		this.idconta_facturasa = idconta_facturasa;
	}

	public String getIdconta_facturasb() {
		return idconta_facturasb;
	}

	public void setIdconta_facturasb(String idconta_facturasb) {
		this.idconta_facturasb = idconta_facturasb;
	}

	public String getIdconta_facturasc() {
		return idconta_facturasc;
	}

	public void setIdconta_facturasc(String idconta_facturasc) {
		this.idconta_facturasc = idconta_facturasc;
	}

	public String getIdconta_recibos() {
		return idconta_recibos;
	}

	public void setIdconta_recibos(String idconta_recibos) {
		this.idconta_recibos = idconta_recibos;
	}

	public String getIdconta_remitos1() {
		return idconta_remitos1;
	}

	public void setIdconta_remitos1(String idconta_remitos1) {
		this.idconta_remitos1 = idconta_remitos1;
	}

	public String getIdconta_remitos2() {
		return idconta_remitos2;
	}

	public void setIdconta_remitos2(String idconta_remitos2) {
		this.idconta_remitos2 = idconta_remitos2;
	}

	public String getIdconta_remitos3() {
		return idconta_remitos3;
	}

	public void setIdconta_remitos3(String idconta_remitos3) {
		this.idconta_remitos3 = idconta_remitos3;
	}

	public String getIdconta_remitos4() {
		return idconta_remitos4;
	}

	public void setIdconta_remitos4(String idconta_remitos4) {
		this.idconta_remitos4 = idconta_remitos4;
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

	public String getPlanta() {
		return planta;
	}

	public void setPlanta(String planta) {
		this.planta = planta;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}
}
