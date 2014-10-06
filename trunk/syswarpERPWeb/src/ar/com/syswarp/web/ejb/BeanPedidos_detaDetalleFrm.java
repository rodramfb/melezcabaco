/* 
 javabean para la entidad (Formulario): pedidos_deta
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Thu Mar 29 16:53:13 GMT-03:00 2007 
 
 Para manejar la pagina: pedidos_detaFrm.jsp
 
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

public class BeanPedidos_detaDetalleFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanPedidos_detaDetalleFrm.class);

	private String validar = "";

	private BigDecimal idpedido_deta = BigDecimal.valueOf(-1);

	private BigDecimal idpedido_cabe = BigDecimal.valueOf(0);
	
	private BigDecimal idempresa = BigDecimal.valueOf(0);
	

	private String codigo_st = "";

	private Timestamp fecha = new Timestamp(Common.initObjectTime());

	private String fechaStr = Common.initObjectTimeStr();

	private BigDecimal renglon = BigDecimal.valueOf(0);

	private Double precio = Double.valueOf("0");

	private Double saldo = Double.valueOf("0");

	private Double cantidad = Double.valueOf("0");

	private Double bonific = Double.valueOf("0");

	private BigDecimal codigo_md = BigDecimal.valueOf(0);

	private Double cantuni = Double.valueOf("0");

	private BigDecimal codigo_dt = BigDecimal.valueOf(0);

	private String entrega = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanPedidos_detaDetalleFrm() {
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
			Clientes pedidos_deta = Common.getClientes();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = pedidos_deta.pedidos_detaDetalleCreate(
						this.idpedido_cabe, this.codigo_st, this.fecha,
						this.renglon, this.precio, this.saldo, this.cantidad,
						this.bonific, this.codigo_md, this.cantuni,
						this.codigo_dt, this.entrega, this.usuarioalt,this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = pedidos_deta.pedidos_detaDetalleUpdate(
						this.idpedido_deta, this.idpedido_cabe, this.codigo_st,
						this.fecha, this.renglon, this.precio, this.saldo,
						this.cantidad, this.bonific, this.codigo_md,
						this.cantuni, this.codigo_dt, this.entrega,
						this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = pedidos_deta
						.pedidos_detaDetalleDelete(this.idpedido_deta);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosPedidos_deta() {
		try {
			Clientes pedidos_deta = Common.getClientes();
			List listPedidos_deta = pedidos_deta
					.getPedidos_detaDetallePK(this.idpedido_deta);
			Iterator iterPedidos_deta = listPedidos_deta.iterator();
			if (iterPedidos_deta.hasNext()) {
				String[] uCampos = (String[]) iterPedidos_deta.next();
				// TODO: Constructores para cada tipo de datos
				this.idpedido_deta = BigDecimal.valueOf(Long
						.parseLong(uCampos[0]));
				this.idpedido_cabe = BigDecimal.valueOf(Long
						.parseLong(uCampos[1]));
				this.codigo_st = uCampos[2];
				this.fecha = Timestamp.valueOf(uCampos[3]);
				this.fechaStr = Common.setObjectToStrOrTime(fecha, "JSTsToStr")
						.toString();
				this.renglon = BigDecimal.valueOf(Long.parseLong(uCampos[4]));
				this.precio = Double.valueOf(uCampos[5]);
				this.saldo = Double.valueOf(uCampos[6]);
				this.cantidad = Double.valueOf(uCampos[7]);
				this.bonific = Double.valueOf(uCampos[8]);
				this.codigo_md = BigDecimal.valueOf(Long.parseLong(uCampos[9]));
				this.cantuni = Double.valueOf(uCampos[10]);
				this.codigo_dt = BigDecimal
						.valueOf(Long.parseLong(uCampos[11]));
				this.entrega = uCampos[12];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosPedidos_deta()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("pedidos_detaDetalleAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (idpedido_cabe == null) {
						this.mensaje = "No se puede dejar vacio el campo idpedido_cabe ";
						return false;
					}
					if (codigo_st == null) {
						this.mensaje = "No se puede dejar vacio el campo codigo_st ";
						return false;
					}
					if (fecha == null) {
						this.mensaje = "No se puede dejar vacio el campo fecha ";
						return false;
					}
					if (renglon == null) {
						this.mensaje = "No se puede dejar vacio el campo renglon ";
						return false;
					}
					if (precio == null) {
						this.mensaje = "No se puede dejar vacio el campo precio ";
						return false;
					}
					if (saldo == null) {
						this.mensaje = "No se puede dejar vacio el campo saldo ";
						return false;
					}
					if (cantidad == null) {
						this.mensaje = "No se puede dejar vacio el campo cantidad ";
						return false;
					}
					if (bonific == null) {
						this.mensaje = "No se puede dejar vacio el campo bonific ";
						return false;
					}
					if (codigo_md == null) {
						this.mensaje = "No se puede dejar vacio el campo codigo_md ";
						return false;
					}
					if (cantuni == null) {
						this.mensaje = "No se puede dejar vacio el campo cantuni ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (codigo_st.trim().length() == 0) {
						this.mensaje = "No se puede dejar con longitud 0 el campo codigo_st  ";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosPedidos_deta();
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
	public BigDecimal getIdpedido_deta() {
		return idpedido_deta;
	}

	public void setIdpedido_deta(BigDecimal idpedido_deta) {
		this.idpedido_deta = idpedido_deta;
	}

	public BigDecimal getIdpedido_cabe() {
		return idpedido_cabe;
	}

	public void setIdpedido_cabe(BigDecimal idpedido_cabe) {
		this.idpedido_cabe = idpedido_cabe;
	}

	public String getCodigo_st() {
		return codigo_st;
	}

	public void setCodigo_st(String codigo_st) {
		this.codigo_st = codigo_st;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getRenglon() {
		return renglon;
	}

	public void setRenglon(BigDecimal renglon) {
		this.renglon = renglon;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public Double getBonific() {
		return bonific;
	}

	public void setBonific(Double bonific) {
		this.bonific = bonific;
	}

	public BigDecimal getCodigo_md() {
		return codigo_md;
	}

	public void setCodigo_md(BigDecimal codigo_md) {
		this.codigo_md = codigo_md;
	}

	public Double getCantuni() {
		return cantuni;
	}

	public void setCantuni(Double cantuni) {
		this.cantuni = cantuni;
	}

	public BigDecimal getCodigo_dt() {
		return codigo_dt;
	}

	public void setCodigo_dt(BigDecimal codigo_dt) {
		this.codigo_dt = codigo_dt;
	}

	public String getEntrega() {
		return entrega;
	}

	public void setEntrega(String entrega) {
		this.entrega = entrega;
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

	public String getFechaStr() {
		return fechaStr;
	}

	public void setFechaStr(String fechaStr) {
		this.fechaStr = fechaStr;
		this.fecha = (java.sql.Timestamp) Common.setObjectToStrOrTime(fecha,
				"StrToJSTs");
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

}
