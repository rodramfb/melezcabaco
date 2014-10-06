/* 
 javabean para la entidad (Formulario): clientesClieSuc
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Wed Feb 07 10:47:33 GMT-03:00 2007 
 
 Para manejar la pagina: clientesClieSucFrm.jsp
 
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

public class BeanClientesClieSucFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanClientesClieSucFrm.class);

	private String validar = "";

	private BigDecimal idsucursal = BigDecimal.valueOf(-1);

	private BigDecimal idcliente = BigDecimal.valueOf(0);

	private BigDecimal idempresa ;
	
	private String cliente = "";

	private BigDecimal idtiposucursal = BigDecimal.valueOf(0);

	private String tiposucursal = "";

	private String nombre_suc = "";

	private String domici_suc = "";

	private String telefo_suc = "";

	private String idlocalidad = "";

	private String localidad = "";

	private String idvendedor = "";

	private String vendedor = "";

	private String idcobrador = "";

	private String cobrador = "";

	private String idexpreso = "";

	private String expreso = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanClientesClieSucFrm() {
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
			Clientes clientesClieSuc = Common.getClientes();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = clientesClieSuc.clientesClieSucCreate(
						this.idcliente, this.idtiposucursal, this.nombre_suc,
						this.domici_suc, this.telefo_suc, this.idlocalidad,
						this.idvendedor, this.idcobrador, this.idexpreso,
						this.usuarioalt,this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clientesClieSuc.clientesClieSucUpdate(
						this.idsucursal, this.idcliente, this.idtiposucursal,
						this.nombre_suc, this.domici_suc, this.telefo_suc,
						this.idlocalidad, this.idvendedor, this.idcobrador,
						this.idexpreso, this.usuarioact,this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clientesClieSuc
						.clientesClieSucDelete(this.idsucursal,this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosClientesClieSuc() {
		try {
			Clientes clientesClieSuc = Common.getClientes();
			List listClientesClieSuc = clientesClieSuc
					.getClientesClieSucPK(this.idsucursal,this.idempresa);
			Iterator iterClientesClieSuc = listClientesClieSuc.iterator();
			if (iterClientesClieSuc.hasNext()) {
				String[] uCampos = (String[]) iterClientesClieSuc.next();
				// TODO: Constructores para cada tipo de datos
				this.idsucursal = BigDecimal
						.valueOf(Long.parseLong(uCampos[0]));
				this.idcliente = BigDecimal.valueOf(Long.parseLong(uCampos[1]));
				this.cliente = uCampos[2];
				this.idtiposucursal = BigDecimal.valueOf(Long
						.parseLong(uCampos[3]));
				this.tiposucursal = uCampos[4];
				this.nombre_suc = uCampos[5];
				this.domici_suc = uCampos[6];
				this.telefo_suc = uCampos[7];
				this.idlocalidad = uCampos[8];
				this.localidad = uCampos[9];
				this.idvendedor = uCampos[10];
				this.vendedor = uCampos[11];
				this.idcobrador = uCampos[12];
				this.cobrador = uCampos[13];
				this.idexpreso = uCampos[14];
				this.expreso = uCampos[15];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosClientesClieSuc()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("clientesClieSucAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (idcliente == null) {
						this.mensaje = "No se puede dejar vacio el campo idcliente ";
						return false;
					}
					if (idtiposucursal == null) {
						this.mensaje = "No se puede dejar vacio el campo idtiposucursal ";
						return false;
					}

					if (cliente.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Cliente ";
						return false;
					}
					if (tiposucursal.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Tipo Sucursal ";
						return false;
					}

					// 2. len 0 para campos nulos
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosClientesClieSuc();
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
	public BigDecimal getIdsucursal() {
		return idsucursal;
	}

	public void setIdsucursal(BigDecimal idsucursal) {
		this.idsucursal = idsucursal;
	}

	public BigDecimal getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(BigDecimal idcliente) {
		this.idcliente = idcliente;
	}

	public BigDecimal getIdtiposucursal() {
		return idtiposucursal;
	}

	public void setIdtiposucursal(BigDecimal idtiposucursal) {
		this.idtiposucursal = idtiposucursal;
	}

	public String getNombre_suc() {
		return nombre_suc;
	}

	public void setNombre_suc(String nombre_suc) {
		this.nombre_suc = nombre_suc;
	}

	public String getDomici_suc() {
		return domici_suc;
	}

	public void setDomici_suc(String domici_suc) {
		this.domici_suc = domici_suc;
	}

	public String getTelefo_suc() {
		return telefo_suc;
	}

	public void setTelefo_suc(String telefo_suc) {
		this.telefo_suc = telefo_suc;
	}

	public String getIdlocalidad() {
		return idlocalidad;
	}

	public void setIdlocalidad(String idlocalidad) {
		this.idlocalidad = idlocalidad;
	}

	public String getIdvendedor() {
		return idvendedor;
	}

	public void setIdvendedor(String idvendedor) {
		this.idvendedor = idvendedor;
	}

	public String getIdcobrador() {
		return idcobrador;
	}

	public void setIdcobrador(String idcobrador) {
		this.idcobrador = idcobrador;
	}

	public String getIdexpreso() {
		return idexpreso;
	}

	public void setIdexpreso(String idexpreso) {
		this.idexpreso = idexpreso;
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

	public String getCobrador() {
		return cobrador;
	}

	public void setCobrador(String cobrador) {
		this.cobrador = cobrador;
	}

	public SessionContext getContext() {
		return context;
	}

	public void setContext(SessionContext context) {
		this.context = context;
	}

	public String getExpreso() {
		return expreso;
	}

	public void setExpreso(String expreso) {
		this.expreso = expreso;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getTiposucursal() {
		return tiposucursal;
	}

	public void setTiposucursal(String tiposucursal) {
		this.tiposucursal = tiposucursal;
	}

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}
}
