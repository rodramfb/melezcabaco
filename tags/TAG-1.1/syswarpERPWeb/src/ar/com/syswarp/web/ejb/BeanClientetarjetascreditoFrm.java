/* 
 javabean para la entidad (Formulario): clientetarjetascredito
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Jan 23 19:21:13 GMT-03:00 2007 
 
 Para manejar la pagina: clientetarjetascreditoFrm.jsp
 
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

public class BeanClientetarjetascreditoFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanClientetarjetascreditoFrm.class);

	private String validar = "";

	private BigDecimal idtarjeta = BigDecimal.valueOf(-1);

	private BigDecimal idtarjetacredito = BigDecimal.valueOf(0);

	private String tarjetacredito = "";

	private BigDecimal idcliente = BigDecimal.valueOf(0);
	
	private BigDecimal idempresa;

	private String cliente = "";

	private BigDecimal idtipotarjeta = BigDecimal.valueOf(0);

	private String tipotarjeta = "";

	private String nrotarjeta = "";

	private String nrocontrol = "";

	private Timestamp fecha_emision = new Timestamp(Common.initObjectTime());

	private String fecha_emisionStr = Common.initObjectTimeStr();

	private Timestamp fecha_vencimiento = new Timestamp(Common.initObjectTime());

	private String fecha_vencimientoStr = Common.initObjectTimeStr();

	private String titular = "";

	private BigDecimal orden = BigDecimal.valueOf(0);

	private String activa = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanClientetarjetascreditoFrm() {
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
			Clientes clientetarjetascredito = Common.getClientes();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = clientetarjetascredito
						.clienteTarjetasCreditoCreate(this.idtarjetacredito,
								this.idcliente, this.idtipotarjeta,
								this.nrotarjeta, this.nrocontrol,
								this.fecha_emision, this.fecha_vencimiento,
								this.titular, this.orden, this.activa,this.idempresa,
								this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clientetarjetascredito
						.clienteTarjetasCreditoUpdate(this.idtarjeta,
								this.idtarjetacredito, this.idcliente,
								this.idtipotarjeta, this.nrotarjeta,
								this.nrocontrol, this.fecha_emision,
								this.fecha_vencimiento, this.titular,
								this.orden, this.activa,this.idempresa, this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clientetarjetascredito
						.clienteTarjetasCreditoDelete(this.idtarjeta,this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosClientetarjetascredito() {
		try {
			Clientes clientetarjetascredito = Common.getClientes();
			List listClientetarjetascredito = clientetarjetascredito
					.getClienteTarjetasCreditoPK(this.idtarjeta,this.idempresa);
			Iterator iterClientetarjetascredito = listClientetarjetascredito
					.iterator();
			if (iterClientetarjetascredito.hasNext()) {
				String[] uCampos = (String[]) iterClientetarjetascredito.next();
				// TODO: Constructores para cada tipo de datos
				this.idtarjeta = BigDecimal.valueOf(Long.parseLong(uCampos[0]));
				this.idtarjetacredito = BigDecimal.valueOf(Long
						.parseLong(uCampos[1]));
				this.tarjetacredito = uCampos[2];
				this.idcliente = BigDecimal.valueOf(Long.parseLong(uCampos[3]));
				this.cliente = uCampos[4];
				this.idtipotarjeta = BigDecimal.valueOf(Long
						.parseLong(uCampos[5]));
				this.tipotarjeta = uCampos[6];
				this.nrotarjeta = uCampos[7];
				this.nrocontrol = uCampos[8];
				this.fecha_emision = Timestamp.valueOf(uCampos[9]);
				this.fecha_emisionStr = Common.setObjectToStrOrTime(
						fecha_emision, "JSTsToStr").toString();
				this.fecha_vencimiento = Timestamp.valueOf(uCampos[10]);
				this.fecha_vencimientoStr = Common.setObjectToStrOrTime(
						fecha_vencimiento, "JSTsToStr").toString();
				this.titular = uCampos[11];
				this.orden = BigDecimal.valueOf(Long.parseLong(uCampos[12]));
				this.activa = uCampos[13];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosClientetarjetascredito()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("clientetarjetascreditoAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos

					if (tarjetacredito.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Tarjeta de Credito  ";
						return false;
					}
					if (cliente.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Cliente  ";
						return false;
					}

					if (tipotarjeta.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Tipo Tarjeta  ";
						return false;
					}

					// 2. len 0 para campos nulos
					if (nrotarjeta.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Nº Tarjeta  ";
						return false;
					}
					if (nrocontrol.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Nº Control  ";
						return false;
					}
					if (titular.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Titular  ";
						return false;
					}

					if (orden == null) {
						this.mensaje = "No se puede dejar vacio el campo Orden  ";
						return false;
					}

				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosClientetarjetascredito();
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
	public BigDecimal getIdtarjeta() {
		return idtarjeta;
	}

	public void setIdtarjeta(BigDecimal idtarjeta) {
		this.idtarjeta = idtarjeta;
	}

	public BigDecimal getIdtarjetacredito() {
		return idtarjetacredito;
	}

	public void setIdtarjetacredito(BigDecimal idtarjetacredito) {
		this.idtarjetacredito = idtarjetacredito;
	}

	public BigDecimal getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(BigDecimal idcliente) {
		this.idcliente = idcliente;
	}

	public BigDecimal getIdtipotarjeta() {
		return idtipotarjeta;
	}

	public void setIdtipotarjeta(BigDecimal idtipotarjeta) {
		this.idtipotarjeta = idtipotarjeta;
	}

	public String getNrotarjeta() {
		return nrotarjeta;
	}

	public void setNrotarjeta(String nrotarjeta) {
		this.nrotarjeta = nrotarjeta;
	}

	public String getNrocontrol() {
		return nrocontrol;
	}

	public void setNrocontrol(String nrocontrol) {
		this.nrocontrol = nrocontrol;
	}

	public Timestamp getFecha_emision() {
		return fecha_emision;
	}

	public void setFecha_emision(Timestamp fecha_emision) {
		this.fecha_emision = fecha_emision;
	}

	public Timestamp getFecha_vencimiento() {
		return fecha_vencimiento;
	}

	public void setFecha_vencimiento(Timestamp fecha_vencimiento) {
		this.fecha_vencimiento = fecha_vencimiento;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public BigDecimal getOrden() {
		return orden;
	}

	public void setOrden(BigDecimal orden) {
		this.orden = orden;
	}

	public String getActiva() {
		return activa;
	}

	public void setActiva(String activa) {
		this.activa = activa;
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

	public String getFecha_vencimientoStr() {
		return fecha_vencimientoStr;
	}

	public void setFecha_vencimientoStr(String fecha_vencimientoStr) {
		this.fecha_vencimientoStr = fecha_vencimientoStr;
		this.fecha_vencimiento = (java.sql.Timestamp) Common
				.setObjectToStrOrTime(fecha_vencimientoStr, "StrToJSTs");
	}

	public String getTarjetacredito() {
		return tarjetacredito;
	}

	public void setTarjetacredito(String tarjetacredito) {
		this.tarjetacredito = tarjetacredito;
	}

	public String getTipotarjeta() {
		return tipotarjeta;
	}

	public void setTipotarjeta(String tipotarjeta) {
		this.tipotarjeta = tipotarjeta;
	}

	public String getFecha_emisionStr() {
		return fecha_emisionStr;
	}

	public void setFecha_emisionStr(String fecha_emisionStr) {
		this.fecha_emisionStr = fecha_emisionStr;
		this.fecha_emision = (java.sql.Timestamp) Common.setObjectToStrOrTime(
				fecha_emisionStr, "StrToJSTs");
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}
}
