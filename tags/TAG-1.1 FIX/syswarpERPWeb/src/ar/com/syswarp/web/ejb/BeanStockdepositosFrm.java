/* 
 javabean para la entidad (Formulario): Stockdepositos
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Mon Sep 04 09:24:05 GMT-03:00 2006 
 
 Para manejar la pagina: StockdepositosFrm.jsp
 
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

public class BeanStockdepositosFrm implements SessionBean, Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanStockdepositosFrm.class);

	private String validar = "";

	private BigDecimal codigo_dt = BigDecimal.valueOf(-1);

	private String descrip_dt = "";

	private String direc_dt = "";

	private String factura_dt = "";

	private BigDecimal idlocalidad = new BigDecimal(-1);

	private String localidad = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public BeanStockdepositosFrm() {
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
			Stock Stockdepositos = Common.getStock();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = Stockdepositos.StockdepositosCreate(
						this.descrip_dt, this.direc_dt, this.factura_dt,
						this.idlocalidad, this.usuarioalt, this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = Stockdepositos.StockdepositosUpdate(
						this.codigo_dt, this.descrip_dt, this.direc_dt,
						this.factura_dt, this.idlocalidad, this.usuarioact,
						this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = Stockdepositos.StockdepositosDelete(
						this.codigo_dt, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosStockdepositos() {
		try {
			Stock Stockdepositos = Common.getStock();
			List listStockdepositos = Stockdepositos.getStockdepositosPK(
					this.codigo_dt, this.idempresa);
			Iterator iterStockdepositos = listStockdepositos.iterator();
			if (iterStockdepositos.hasNext()) {
				String[] uCampos = (String[]) iterStockdepositos.next();
				// TODO: Constructores para cada tipo de datos
				this.codigo_dt = BigDecimal.valueOf(Long.parseLong(uCampos[0]));
				this.descrip_dt = uCampos[1];
				this.direc_dt = uCampos[2];
				this.factura_dt = uCampos[3];
				this.idlocalidad = new BigDecimal(uCampos[4]);
				this.localidad = uCampos[5];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosStockdepositos()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("StockdepositosAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (descrip_dt == null) {
						this.mensaje = "No se puede dejar vacio el campo descrip_dt ";
						return false;
					}

					if (this.idlocalidad == null
							|| this.idlocalidad.longValue() < 0) {
						this.mensaje = "Es necesario seleccionar localidad. ";
						return false;
					}

				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosStockdepositos();
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
	public BigDecimal getCodigo_dt() {
		return codigo_dt;
	}

	public void setCodigo_dt(BigDecimal codigo_dt) {
		this.codigo_dt = codigo_dt;
	}

	public String getDescrip_dt() {
		return descrip_dt;
	}

	public void setDescrip_dt(String descrip_dt) {
		this.descrip_dt = descrip_dt;
	}

	public String getDirec_dt() {
		return direc_dt;
	}

	public void setDirec_dt(String direc_dt) {
		this.direc_dt = direc_dt;
	}

	public String getFactura_dt() {
		return factura_dt;
	}

	public void setFactura_dt(String factura_dt) {
		this.factura_dt = factura_dt;
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

	public BigDecimal getIdlocalidad() {
		return idlocalidad;
	}

	public void setIdlocalidad(BigDecimal idlocalidad) {
		this.idlocalidad = idlocalidad;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
}
