/* 
 javabean para la entidad (Formulario): Stockubicacio
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Mon Sep 04 09:24:29 GMT-03:00 2006 
 
 Para manejar la pagina: StockubicacioFrm.jsp
 
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

public class BeanStockubicacioFrm implements SessionBean, Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanStockubicacioFrm.class);

	private String validar = "";

	private BigDecimal codigo_ubi = BigDecimal.valueOf(-1);

	private BigDecimal deposi_ubi = BigDecimal.valueOf(0);

	// descripcion para el lov de deposito
	private String d_deposi_ubi = "";

	private String ubicac_ubi = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanStockubicacioFrm() {
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
			Stock Stockubicacio = Common.getStock();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = Stockubicacio.StockubicacioCreate(
						this.deposi_ubi, this.ubicac_ubi, this.usuarioalt,
						this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = Stockubicacio.StockubicacioUpdate(
						this.codigo_ubi, this.deposi_ubi, this.ubicac_ubi,
						this.usuarioact, this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = Stockubicacio.StockubicacioDelete(
						this.codigo_ubi, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosStockubicacio() {
		try {
			Stock Stockubicacio = Common.getStock();
			List listStockubicacio = Stockubicacio.getStockubicacioPK(
					this.codigo_ubi, this.idempresa);
			Iterator iterStockubicacio = listStockubicacio.iterator();
			if (iterStockubicacio.hasNext()) {
				String[] uCampos = (String[]) iterStockubicacio.next();
				// TODO: Constructores para cada tipo de datos
				this.codigo_ubi = BigDecimal
						.valueOf(Long.parseLong(uCampos[0]));
				this.deposi_ubi = BigDecimal
						.valueOf(Long.parseLong(uCampos[1]));
				this.d_deposi_ubi = uCampos[2];
				this.ubicac_ubi = uCampos[3];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosStockubicacio()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("StockubicacioAbm.jsp");
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
					getDatosStockubicacio();
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
	public BigDecimal getCodigo_ubi() {
		return codigo_ubi;
	}

	public void setCodigo_ubi(BigDecimal codigo_ubi) {
		this.codigo_ubi = codigo_ubi;
	}

	public BigDecimal getDeposi_ubi() {
		return deposi_ubi;
	}

	public void setDeposi_ubi(BigDecimal deposi_ubi) {
		this.deposi_ubi = deposi_ubi;
	}

	public String getUbicac_ubi() {
		return ubicac_ubi;
	}

	public void setUbicac_ubi(String ubicac_ubi) {
		this.ubicac_ubi = ubicac_ubi;
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

	// para que me levante las descripciones de familia
	public String getD_deposi_ubi() {
		return d_deposi_ubi;
	}

	public void setD_deposi_ubi(String d_deposi_ubi) {
		this.d_deposi_ubi = d_deposi_ubi;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

}
