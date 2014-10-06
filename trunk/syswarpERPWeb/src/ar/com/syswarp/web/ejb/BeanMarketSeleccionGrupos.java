/* 
 javabean para la entidad: Stockgrupos
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Mon Sep 04 09:20:45 GMT-03:00 2006 
 
 Para manejar la pagina: StockgruposAbm.jsp
 
 */
package ar.com.syswarp.web.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import java.util.*;
import java.math.*;

import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanMarketSeleccionGrupos implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanMarketSeleccionGrupos.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private List StockgruposList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal codigo_gr = new BigDecimal(-1);

	private BigDecimal codigo_fm = new BigDecimal(-1);

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	public BeanMarketSeleccionGrupos() {
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

	public boolean ejecutarValidacion() {
		Stock Stockgrupos = Common.getStock();
		try {

		
			
			this.StockgruposList = Stockgrupos.getStockGruposXFamilia(
					this.codigo_fm, this.idempresa);

			if (this.accion.equalsIgnoreCase("buscarStockXGrupo")) {
				if (this.codigo_gr == null
						|| this.codigo_gr.compareTo(new BigDecimal(0)) < 1) {
					this.mensaje = "Por favor seleccione grupo, e intente nuevamente.";
					return false;
				}
			}

		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
	}

	public List getStockgruposList() {
		return StockgruposList;
	}

	public void setStockgruposList(List StockgruposList) {
		this.StockgruposList = StockgruposList;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getOcurrencia() {
		return ocurrencia;
	}

	public void setOcurrencia(String buscar) {
		this.ocurrencia = buscar;
	}

	public BigDecimal getCodigo_gr() {
		return codigo_gr;
	}

	public void setCodigo_gr(BigDecimal codigo_gr) {
		this.codigo_gr = codigo_gr;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
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

	public BigDecimal getCodigo_fm() {
		return codigo_fm;
	}

	public void setCodigo_fm(BigDecimal codigo_fm) {
		this.codigo_fm = codigo_fm;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

}
