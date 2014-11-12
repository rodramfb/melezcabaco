/* 
 javabean para la entidad (Formulario): clientesvendedor
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Nov 14 15:40:40 GMT-03:00 2006 
 
 Para manejar la pagina: clientesvendedorFrm.jsp
 
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

public class BeanClientesvendedorFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanClientesvendedorFrm.class);

	private String validar = "";

	private BigDecimal idvendedor = BigDecimal.valueOf(-1);
	
	private BigDecimal idempresa;

	private String vendedor = "";

	private Double comision1 = Double.valueOf("0");

	private Double comision2 = Double.valueOf("0");

	private String domicilio = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";
	
	private boolean reingresar = false;
	
	private java.sql.Date fechabaja = null;
	
	private BigDecimal sueldobasico = new BigDecimal(-1);
	
	private String tipoliquidacion = "";
	
	private BigDecimal tasadesercion = new BigDecimal(-1);
	
	private BigDecimal valorasociacion = new BigDecimal(-1);
	
	public BeanClientesvendedorFrm() {
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
			Clientes clientesvendedor = Common.getClientes();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = clientesvendedor.clientesvendedorCreate(
						this.vendedor, this.comision1, this.comision2,
						this.domicilio, this.usuarioalt,this.idempresa, this.sueldobasico, this.tipoliquidacion, this.tasadesercion, this.valorasociacion);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clientesvendedor.clientesvendedorUpdate(
						this.idvendedor, this.vendedor, this.comision1,
						this.comision2, this.domicilio, this.usuarioact,this.idempresa,this.reingresar, this.sueldobasico, this.tipoliquidacion, this.tasadesercion, this.valorasociacion);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clientesvendedor
						.clientesvendedorDelete(this.idvendedor,this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosClientesvendedor() {
		try {
			Clientes clientesvendedor = Common.getClientes();
			List listClientesvendedor = clientesvendedor
					.getClientesvendedorPK(this.idvendedor,this.idempresa);
			Iterator iterClientesvendedor = listClientesvendedor.iterator();
			if (iterClientesvendedor.hasNext()) {
				String[] uCampos = (String[]) iterClientesvendedor.next();
				// TODO: Constructores para cada tipo de datos
				this.idvendedor = BigDecimal
						.valueOf(Long.parseLong(uCampos[0]));
				this.vendedor = uCampos[1];
				this.comision1 = Double.valueOf(uCampos[2]);
				this.comision2 = Double.valueOf(uCampos[3]);
				this.domicilio = uCampos[4];
				this.fechabaja = (java.sql.Date)Common.setObjectToStrOrTime(uCampos[9] ,"strToJSDate");
				this.sueldobasico = new BigDecimal(uCampos[10]);
				this.tipoliquidacion = uCampos[11];
				this.tasadesercion = new BigDecimal(uCampos[12]);
				this.valorasociacion = new BigDecimal(uCampos[13]);
				} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosClientesvendedor()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("clientesvendedorAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (vendedor == null) {
						this.mensaje = "No se puede dejar vacio el campo vendedor ";
						return false;
					}
					if (comision1 == null) {
						this.mensaje = "No se puede dejar vacio el campo comision1 ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (vendedor.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Vendedor  ";
						return false;
					}
					if (sueldobasico.longValue() < 1 || sueldobasico.equals(""))
					{
						this.mensaje = "No se puede dejar vacio el campo sueldo basico o con valor menor a 1";
						return false;
					}
					if (tasadesercion.longValue()<0 || tasadesercion.equals(""))
					{
						this.mensaje = "No se puede dejar vacio el campo tasa desercion o con valor menor a 0  ";
						return false;
					}
					if (valorasociacion.longValue()<0 || valorasociacion.equals(""))
					{
						this.mensaje = "No se puede dejar vacio el campo valor asociacion o con valor menor a 0  ";
						return false;	
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosClientesvendedor();
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
	public BigDecimal getIdvendedor() {
		return idvendedor;
	}

	public void setIdvendedor(BigDecimal idvendedor) {
		this.idvendedor = idvendedor;
	}

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

	public Double getComision1() {
		return comision1;
	}

	public void setComision1(Double comision1) {
		this.comision1 = comision1;
	}

	public Double getComision2() {
		return comision2;
	}

	public void setComision2(Double comision2) {
		this.comision2 = comision2;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
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


	public boolean getReingresar() {
		return reingresar;
	}

	public void setReingresar(boolean reingresar) {
		this.reingresar = reingresar;
	}

	public java.sql.Date getFechabaja() {
		return fechabaja;
	}

	public void setFechabaja(java.sql.Date fechabaja) {
		this.fechabaja = fechabaja;
	}

	public BigDecimal getSueldobasico() {
		return sueldobasico;
	}

	public void setSueldobasico(BigDecimal sueldobasico) {
		this.sueldobasico = sueldobasico;
	}

	public String getTipoliquidacion() {
		return tipoliquidacion;
	}

	public void setTipoliquidacion(String tipoliquidacion) {
		this.tipoliquidacion = tipoliquidacion;
	}

	public BigDecimal getTasadesercion() {
		return tasadesercion;
	}

	public void setTasadesercion(BigDecimal tasadesercion) {
		this.tasadesercion = tasadesercion;
	}

	public BigDecimal getValorasociacion() {
		return valorasociacion;
	}

	public void setValorasociacion(BigDecimal valorasociacion) {
		this.valorasociacion = valorasociacion;
	}
	
}
