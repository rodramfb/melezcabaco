/* 
 javabean para la entidad: Stockfamilias
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Mon Sep 04 09:19:38 GMT-03:00 2006 
 
 Para manejar la pagina: StockfamiliasAbm.jsp
 
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

public class BeanMarketIndex implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanMarketIndex.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 1000;

	private long offset = 0l;

	private List StockfamiliasList = new ArrayList();

	private String ocurrencia = "";

	private BigDecimal codigo_fm = new BigDecimal(-1);

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public BeanMarketIndex() {
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

		Stock Stockfamilias = Common.getStock();
		Properties props = new Properties();

		try {

			props.load(BeanMarketIndex.class
					.getResourceAsStream("market.properties"));
			this.idempresa = new BigDecimal(props.getProperty("market.empresa"));
			
			request.getSession().setAttribute("marketUsuario", props.getProperty("market.usuario"));
			request.getSession().setAttribute("marketEmpresa", this.idempresa);

			if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else {
					buscar = true;
				}
			}

			this.StockfamiliasList = Stockfamilias.getStockfamiliasMarketAll(
					this.limit, this.offset, this.idempresa);

		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
	}

	public List getStockfamiliasList() {
		return StockfamiliasList;
	}

	public void setStockfamiliasList(List StockfamiliasList) {
		this.StockfamiliasList = StockfamiliasList;
	}

	public BigDecimal getCodigo_fm() {
		return codigo_fm;
	}

	public void setCodigo_fm(BigDecimal codigo_fm) {
		this.codigo_fm = codigo_fm;
	}

	public String getMensaje() {
		return mensaje;
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
}
