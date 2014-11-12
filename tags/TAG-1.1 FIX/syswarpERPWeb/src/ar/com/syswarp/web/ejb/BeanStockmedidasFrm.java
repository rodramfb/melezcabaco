/* 
 javabean para la entidad (Formulario): Stockmedidas
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Mon Sep 04 09:23:33 GMT-03:00 2006 
 
 Para manejar la pagina: StockmedidasFrm.jsp
 
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

public class BeanStockmedidasFrm implements SessionBean, Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanStockmedidasFrm.class);

	private String validar = "";

	private BigDecimal codigo_md = BigDecimal.valueOf(-1);

	private String descrip_md = "";

	private Double cantidad_md = Double.valueOf("0");

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanStockmedidasFrm() {
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
			Stock Stockmedidas = Common.getStock();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = Stockmedidas.StockmedidasCreate(this.descrip_md,
						this.cantidad_md, this.usuarioalt, this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = Stockmedidas.StockmedidasUpdate(this.codigo_md,
						this.descrip_md, this.cantidad_md, this.usuarioact,
						this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = Stockmedidas.StockmedidasDelete(this.codigo_md,
						this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosStockmedidas() {
		try {
			Stock Stockmedidas = Common.getStock();
			List listStockmedidas = Stockmedidas.getStockmedidasPK(
					this.codigo_md, this.idempresa);
			Iterator iterStockmedidas = listStockmedidas.iterator();
			if (iterStockmedidas.hasNext()) {
				String[] uCampos = (String[]) iterStockmedidas.next();
				// TODO: Constructores para cada tipo de datos
				this.codigo_md = BigDecimal.valueOf(Long.parseLong(uCampos[0]));
				this.descrip_md = uCampos[1];
				this.cantidad_md = Double.valueOf(uCampos[2]);
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosStockmedidas()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("StockmedidasAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					// 2. len 0 para campos nulos
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosStockmedidas();
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
	public BigDecimal getCodigo_md() {
		return codigo_md;
	}

	public void setCodigo_md(BigDecimal codigo_md) {
		this.codigo_md = codigo_md;
	}

	public String getDescrip_md() {
		return descrip_md;
	}

	public void setDescrip_md(String descrip_md) {
		this.descrip_md = descrip_md;
	}

	public Double getCantidad_md() {
		return cantidad_md;
	}

	public void setCantidad_md(Double cantidad_md) {
		this.cantidad_md = cantidad_md;
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
}
