/* 
 javabean para la entidad (Formulario): vRemitoInterno
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Fri Feb 16 16:17:58 GMT-03:00 2007 
 
 Para manejar la pagina: vRemitoInternoFrm.jsp
 
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
import java.sql.Timestamp;
import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanVRemitoInternoFrm implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanVRemitoInternoFrm.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private String validar = "";

	private BigDecimal remito_interno = new BigDecimal(0);

	private java.sql.Date fecha;

	private String tipo = "";

	private String codprod = "";

	private String producto = "";

	private BigDecimal coddepo = new BigDecimal(0);;

	private String deposito = "";

	private String direccion = "";

	private Double cantidad = new Double(0);

	private String usuarioalt = "";

	private String usuarioact = "";

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private List listaMovInterno = null;

	public BeanVRemitoInternoFrm() {
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

	public List getDatosVRemitoInterno() {
		List listVRemitoInterno = new ArrayList();
		try {
			Stock vRemitoInterno = Common.getStock();
			listVRemitoInterno = vRemitoInterno.getVRemitoInternoPK(
					this.remito_interno, this.idempresa);
			Iterator iterVRemitoInterno = listVRemitoInterno.iterator();
			if (iterVRemitoInterno.hasNext()) {
				String[] uCampos = (String[]) iterVRemitoInterno.next();
				// TODO: Constructores para cada tipo de datos
				this.remito_interno = new BigDecimal(uCampos[0]);
				this.fecha = java.sql.Date.valueOf(uCampos[1]);
				this.tipo = uCampos[2];
				this.codprod = uCampos[3];
				this.producto = uCampos[4];
				this.coddepo = new BigDecimal(uCampos[5]);
				this.deposito = uCampos[6];
				this.direccion = uCampos[7];
				this.cantidad = new Double(uCampos[8]);

			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosVRemitoInterno()" + e);
		}
		return listVRemitoInterno;
	}

	public boolean ejecutarValidacion() {
		try {

			this.listaMovInterno = getDatosVRemitoInterno();

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
	public BigDecimal getRemito_interno() {
		return remito_interno;
	}

	public void setRemito_interno(BigDecimal remito_interno) {
		this.remito_interno = remito_interno;
	}

	public java.sql.Date getFecha() {
		return fecha;
	}

	public void setFecha(java.sql.Date fecha) {
		this.fecha = fecha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCodprod() {
		return codprod;
	}

	public void setCodprod(String codprod) {
		this.codprod = codprod;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public BigDecimal getCoddepo() {
		return coddepo;
	}

	public void setCoddepo(BigDecimal coddepo) {
		this.coddepo = coddepo;
	}

	public String getDeposito() {
		return deposito;
	}

	public void setDeposito(String deposito) {
		this.deposito = deposito;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
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

	public List getListaMovInterno() {
		return listaMovInterno;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}
}
