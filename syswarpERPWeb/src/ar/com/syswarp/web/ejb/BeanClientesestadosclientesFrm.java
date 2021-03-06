/* 
 javabean para la entidad (Formulario): clientesestadosclientes
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Fri Mar 02 14:15:19 GMT-03:00 2007 
 
 Para manejar la pagina: clientesestadosclientesFrm.jsp
 
 */
package ar.com.syswarp.web.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Timestamp;

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

public class BeanClientesestadosclientesFrm implements SessionBean,
		Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanClientesestadosclientesFrm.class);

	private String validar = "";

	private BigDecimal idestadocliente = BigDecimal.valueOf(-1);

	private BigDecimal idcliente = BigDecimal.valueOf(0);

	private BigDecimal idempresa;

	private String cliente = "";

	private BigDecimal idestado = BigDecimal.valueOf(0);

	private String estado = "";

	private BigDecimal idmotivo = new BigDecimal(-1);

	private String motivo = "";

	private String fechadesde = "";

	private java.sql.Date fdesde = null;

	private String fechahasta = "";

	private java.sql.Date fhasta = null;

	private String fbaja = "";

	private Timestamp fechabaja = null;

	private String observaciones = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanClientesestadosclientesFrm() {
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

			Clientes clientesestadosclientes = Common.getClientes();
			if (this.accion.equalsIgnoreCase("alta")){
				this.mensaje = clientesestadosclientes
						.clientesestadosclientesCreate(this.idcliente,
								this.idestado, this.idmotivo, this.fdesde,
								this.fhasta, this.fechabaja,
								this.observaciones, this.usuarioalt,
								this.idempresa);
		
			}
			
			/*
			 * else if (this.accion.equalsIgnoreCase("modificacion"))
			 * this.mensaje = clientesestadosclientes
			 * .clientesestadosclientesUpdate(this.idestadocliente,
			 * this.idcliente, this.idestado, this.idmotivo, this.fdesde,
			 * this.fhasta, this.fechabaja, this.observaciones, this.usuarioact,
			 * this.idempresa); else if (this.accion.equalsIgnoreCase("baja"))
			 * this.mensaje = clientesestadosclientes
			 * .clientesestadosclientesDelete(this.idestadocliente,
			 * this.idempresa);
			 */
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosClientesestadosclientes() {
		try {
			Clientes clientesestadosclientes = Common.getClientes();
			List listClientesestadosclientes = clientesestadosclientes
					.getClientesestadosclientesPK(this.idestadocliente,
							this.idempresa);
			Iterator iterClientesestadosclientes = listClientesestadosclientes
					.iterator();
			if (iterClientesestadosclientes.hasNext()) {
				String[] uCampos = (String[]) iterClientesestadosclientes
						.next();

				/*
				 * 
				 * CEC.idestadocliente,CLI.razon,CE.estado,CM.motivo,CEC.fechadesde
				 * , CEC.fechahasta,CEC.fbaja,CEC.observaciones," +
				 * CEC.usuarioalt,CEC.usuarioact,CEC.fechaalt,CEC.fechaact
				 */

				// TODO: Constructores para cada tipo de datos
				this.idestadocliente = BigDecimal.valueOf(Long
						.parseLong(uCampos[0]));
				this.idcliente = new BigDecimal(uCampos[1]);
				this.cliente = uCampos[2];
				this.idestado = new BigDecimal(uCampos[3]);
				this.estado = uCampos[4];
				this.idmotivo = new BigDecimal(uCampos[5]);
				this.motivo = uCampos[6];
				this.fechadesde = (String) Common.setObjectToStrOrTime(
						java.sql.Date.valueOf(uCampos[7]), "JSDateToStr");
				this.fechahasta = (String) Common.setObjectToStrOrTime(
						java.sql.Date.valueOf(uCampos[8]), "JSDateToStr");
				this.fbaja = (String) Common.setObjectToStrOrTime(
						uCampos[9] != null ? Timestamp.valueOf(uCampos[9])
								: null, "JSTsToStr");

				this.observaciones = uCampos[10];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosClientesestadosclientes()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("clientesestadosclientesAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (idcliente == null) {
						this.mensaje = "No se puede dejar vacio el campo idcliente ";
						return false;
					}
					if (idestado == null) {
						this.mensaje = "No se puede dejar vacio el campo idestado ";
						return false;
					}

					if (fechadesde == null) {
						this.mensaje = "No se puede dejar vacio el campo fechadesde ";
						return false;
					}

					if (fechahasta == null) {
						this.mensaje = "No se puede dejar vacio el campo fechahasta ";
						return false;
					}

					if (!Common.isFormatoFecha(this.fechadesde)
							|| !Common.isFechaValida(this.fechadesde)) {
						this.mensaje = "Fecha desde invalida.";
						return false;

					}

					if (!Common.isFormatoFecha(this.fechahasta)
							|| !Common.isFechaValida(this.fechahasta)) {
						this.mensaje = "Fecha hasta invalida.";
						return false;

					}

					this.fdesde = (java.sql.Date) Common.setObjectToStrOrTime(
							this.fechadesde, "StrToJSDate");
					this.fhasta = (java.sql.Date) Common.setObjectToStrOrTime(
							this.fechahasta, "StrToJSDate");
					this.fechabaja = this.fbaja == null
							|| this.fbaja.trim().equals("") ? null
							: (Timestamp) Common.setObjectToStrOrTime(
									this.fbaja, "StrToJSTs");

					if (this.fdesde.after(this.fhasta)) {
						this.mensaje = "Fecha desde no puede ser mayor a fecha hasta.";
						return false;
					}

					if (cliente.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Cliente  ";
						return false;
					}
					if (estado.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Estado  ";
						return false;
					}
					if (estado.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Estado  ";
						return false;
					}

					// 2. len 0 para campos nulos
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosClientesestadosclientes();
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
	public BigDecimal getIdestadocliente() {
		return idestadocliente;
	}

	public void setIdestadocliente(BigDecimal idestadocliente) {
		this.idestadocliente = idestadocliente;
	}

	public BigDecimal getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(BigDecimal idcliente) {
		this.idcliente = idcliente;
	}

	public BigDecimal getIdestado() {
		return idestado;
	}

	public void setIdestado(BigDecimal idestado) {
		this.idestado = idestado;
	}

	public BigDecimal getIdmotivo() {
		return idmotivo;
	}

	public void setIdmotivo(BigDecimal idmotivo) {
		this.idmotivo = idmotivo;
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

	public String getFbaja() {
		return fbaja;
	}

	public void setFbaja(String fbaja) {
		this.fbaja = fbaja;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
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

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}
}
