/* 
   javabean para la entidad (Formulario): pedidosVentasEspeciales
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jan 27 11:07:27 ART 2011 
   
   Para manejar la pagina: pedidosVentasEspecialesFrm.jsp
      
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

public class BeanPedidosVentasEspecialesFrm implements SessionBean,
		Serializable {

	private SessionContext context;

	static Logger log = Logger.getLogger(BeanPedidosVentasEspecialesFrm.class);

	private String validar = "";

	private BigDecimal idventaespecial = new BigDecimal(-1);

	private String ventaespecial = "";

	private String fechadesde = "";

	private String fechahasta = "";

	private java.sql.Date fdesde = null;

	private java.sql.Date fhasta = null;

	private BigDecimal idempresa = new BigDecimal(-1);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanPedidosVentasEspecialesFrm() {
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
				this.mensaje = clientes.pedidosVentasEspecialesCreate(
						this.ventaespecial, this.fdesde, this.fhasta,
						this.idempresa, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clientes.pedidosVentasEspecialesUpdate(
						this.idventaespecial, this.ventaespecial, this.fdesde,
						this.fhasta, this.idempresa, this.usuarioact);

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosPedidosVentasEspeciales() {
		try {
			Clientes clientes = Common.getClientes();
			List listPedidosVentasEspeciales = clientes
					.getPedidosVentasEspecialesPK(this.idventaespecial,
							this.idempresa);
			Iterator iterPedidosVentasEspeciales = listPedidosVentasEspeciales
					.iterator();
			if (iterPedidosVentasEspeciales.hasNext()) {
				String[] uCampos = (String[]) iterPedidosVentasEspeciales
						.next();
				// TODO: Constructores para cada tipo de datos
				this.idventaespecial = new BigDecimal(uCampos[0]);
				this.ventaespecial = uCampos[1];
				this.fechadesde = Common.setObjectToStrOrTime(
						java.sql.Date.valueOf(uCampos[2]), "JSDateToStr")
						.toString();
				this.fechahasta = Common.setObjectToStrOrTime(
						java.sql.Date.valueOf(uCampos[3]), "JSDateToStr")
						.toString();
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosPedidosVentasEspeciales()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {

			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("pedidosVentasEspecialesAbm.jsp");
				return true;
			}

			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (ventaespecial == null) {
						this.mensaje = "No se puede dejar vacio el campo ventaespecial ";
						return false;
					}
					if (fechadesde == null) {
						this.mensaje = "No se puede dejar vacio el campo fechadesde ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (ventaespecial.trim().length() == 0) {
						this.mensaje = "No se puede dejar con longitud 0 el campo ventaespecial  ";
						return false;
					}

					if (!Common.isFormatoFecha(this.fechadesde)
							|| !Common.isFechaValida(this.fechadesde)) {
						this.mensaje = "Ingrese fecha desde valida. ";
						return false;
					}

					this.fdesde = (java.sql.Date) Common.setObjectToStrOrTime(
							this.fechadesde, "StrToJSDate");

					if (!Common.isFormatoFecha(this.fechahasta)
							|| !Common.isFechaValida(this.fechahasta)) {
						this.mensaje = "Fecha hasta invalida. ";
						return false;
					}

					this.fhasta = (java.sql.Date) Common.setObjectToStrOrTime(
							this.fechahasta, "StrToJSDate");

					if (this.fdesde.after(fhasta)) {
						this.mensaje = "Fecha desde no puede ser mayor a fecha hasta. ";
						return false;
					}

				}

				this.ejecutarSentenciaDML();

			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosPedidosVentasEspeciales();
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
	public BigDecimal getIdventaespecial() {
		return idventaespecial;
	}

	public void setIdventaespecial(BigDecimal idventaespecial) {
		this.idventaespecial = idventaespecial;
	}

	public String getVentaespecial() {
		return ventaespecial;
	}

	public void setVentaespecial(String ventaespecial) {
		this.ventaespecial = ventaespecial;
	}

	public String getFechadesde() {
		return fechadesde;
	}

	public void setFechadesde(String fechadesde) {
		this.fechadesde = fechadesde;
	}

	public String getFechahasta() {
		return fechahasta;
	}

	public void setFechahasta(String fechahasta) {
		this.fechahasta = fechahasta;
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
