/* 
   javabean para la entidad (Formulario): bacoRefCtaCte
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jun 17 12:34:05 ART 2010 
   
   Para manejar la pagina: bacoRefCtaCteFrm.jsp
      
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

public class BeanBacoRefCtaCteFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanBacoRefCtaCteFrm.class);

	private String validar = "";

	private BigDecimal idctacte = new BigDecimal(-1);

	private BigDecimal idcliente = new BigDecimal(-1);

	private String cliente = "";

	private BigDecimal idoperacion = new BigDecimal(-1);

	private BigDecimal idreferido = new BigDecimal(0);

	private String puntos = "";

	private String auxPuntos = "";

	private String fecha = "";

	private String observaciones = "";

	private String signo = "";

	private BigDecimal idempresa = new BigDecimal(-1);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private List listOperaciones = new ArrayList();

	public BeanBacoRefCtaCteFrm() {
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

			this.fecha = Common.initObjectTimeStr();
			Clientes clientes = Common.getClientes();

			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = clientes.bacoRefCtaCteCreate(this.idcliente,
						this.idoperacion, this.idreferido, new BigDecimal(
								this.auxPuntos),
						(java.sql.Date) Common.setObjectToStrOrTime(this.fecha,
								"StrToJSDate"), this.observaciones, null,
						this.idempresa, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clientes
						.bacoRefCtaCteUpdate(this.idctacte, this.idcliente,
								this.idoperacion, this.idreferido,
								new BigDecimal(this.auxPuntos),
								(java.sql.Date) Common.setObjectToStrOrTime(
										this.fecha, "StrToJSDate"),
								this.idempresa, this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clientes.bacoRefCtaCteDelete(this.idctacte,
						this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosBacoRefCtaCte() {
		try {
			Clientes clientes = Common.getClientes();
			List listBacoRefCtaCte = clientes.getBacoRefCtaCtePK(this.idctacte,
					this.idempresa);
			Iterator iterBacoRefCtaCte = listBacoRefCtaCte.iterator();
			if (iterBacoRefCtaCte.hasNext()) {
				String[] uCampos = (String[]) iterBacoRefCtaCte.next();
				// TODO: Constructores para cada tipo de datos
				this.idctacte = new BigDecimal(uCampos[0]);
				this.idcliente = new BigDecimal(uCampos[1]);
				this.idoperacion = new BigDecimal(uCampos[2]);
				this.idreferido = new BigDecimal(uCampos[3]);
				this.puntos = uCampos[4];
				this.fecha = uCampos[5];
				// this.idempresa = new BigDecimal(uCampos[6]);
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosBacoRefCtaCte()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("bacoRefCtaCteAbm.jsp?idcliente="
						+ this.idcliente + "&cliente=" + this.cliente);
				return true;
			}

			this.listOperaciones = Common.getClientes()
					.getBacoRefOperacionesXProceso("man", this.idempresa);

			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (idcliente == null || idcliente.longValue() < 1) {
						this.mensaje = "No se puede dejar vacio el campo idcliente ";
						return false;
					}
					if (idoperacion == null || idoperacion.longValue() < 1) {
						this.mensaje = "No se puede dejar vacio el campo idoperacion ";
						return false;
					}
					if (!Common.esNumerico(this.puntos)) {
						this.mensaje = "Ingrese un valor numerico valido para puntos. ";
						return false;
					}

					if (this.signo.equals("")) {
						this.mensaje = "La operacion seleccionada no esta definida correctamente, no es posible determinar si suma o resta.";
						return false;
					}

					this.puntos = (new BigDecimal(this.puntos).abs())
							.toString();
					this.auxPuntos = this.signo + this.puntos;

					// 2. len 0 para campos nulos
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosBacoRefCtaCte();
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
	public BigDecimal getIdctacte() {
		return idctacte;
	}

	public void setIdctacte(BigDecimal idctacte) {
		this.idctacte = idctacte;
	}

	public BigDecimal getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(BigDecimal idcliente) {
		this.idcliente = idcliente;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public BigDecimal getIdoperacion() {
		return idoperacion;
	}

	public void setIdoperacion(BigDecimal idoperacion) {
		this.idoperacion = idoperacion;
	}

	public BigDecimal getIdreferido() {
		return idreferido;
	}

	public void setIdreferido(BigDecimal idreferido) {
		this.idreferido = idreferido;
	}

	public String getPuntos() {
		return puntos;
	}

	public void setPuntos(String puntos) {
		this.puntos = puntos;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getSigno() {
		return signo;
	}

	public void setSigno(String signo) {
		this.signo = signo;
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

	public List getListOperaciones() {
		return listOperaciones;
	}

	public void setListOperaciones(List listOperaciones) {
		this.listOperaciones = listOperaciones;
	}

}
