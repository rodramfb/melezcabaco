/* 
   javabean para la entidad (Formulario): stockStockBis
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Aug 28 17:41:57 GMT-03:00 2008 
   
   Para manejar la pagina: stockStockBisFrm.jsp
      
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

public class BeanIframeGetStockDeposito implements SessionBean, Serializable {
	private SessionContext context;
	static Logger log = Logger.getLogger(BeanIframeGetStockDeposito.class);

	private String validar = "";

	private String articu_sb = "";

	private BigDecimal deposi_sb = new BigDecimal(0);

	private BigDecimal canti_sb = new BigDecimal(0);

	private BigDecimal pedid_sb = new BigDecimal(0);

	private BigDecimal remanente = new BigDecimal(0);

	private BigDecimal idempresa = new BigDecimal(-1);
	
	private BigDecimal indice = new BigDecimal(0);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanIframeGetStockDeposito() {
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

	public void getDatosStockStockBis() {
		try {
			Clientes stockStockBis = Common.getClientes();
			List listStockStockBis = stockStockBis.getStockDepositoArticulo(
					this.articu_sb, this.deposi_sb, this.idempresa);
			Iterator iterStockStockBis = listStockStockBis.iterator();
			if (iterStockStockBis.hasNext()) {
				String[] uCampos = (String[]) iterStockStockBis.next();
				// TODO: Constructores para cada tipo de datos

				this.canti_sb = new BigDecimal(uCampos[0]);
				this.pedid_sb = new BigDecimal(uCampos[1]);
				this.remanente = new BigDecimal(uCampos[2]);

			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosStockStockBis()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {

			if (this.accion.equalsIgnoreCase("setexistencias"))
				getDatosStockStockBis();

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
	public String getArticu_sb() {
		return articu_sb;
	}

	public void setArticu_sb(String articu_sb) {
		this.articu_sb = articu_sb;
	}

	public BigDecimal getDeposi_sb() {
		return deposi_sb;
	}

	public void setDeposi_sb(BigDecimal deposi_sb) {
		this.deposi_sb = deposi_sb;
	}

	public BigDecimal getCanti_sb() {
		return canti_sb;
	}

	public void setCanti_sb(BigDecimal canti_sb) {
		this.canti_sb = canti_sb;
	}

	public BigDecimal getPedid_sb() {
		return pedid_sb;
	}

	public void setPedid_sb(BigDecimal pedid_sb) {
		this.pedid_sb = pedid_sb;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
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

	public BigDecimal getRemanente() {
		return remanente;
	}

	public void setRemanente(BigDecimal remanente) {
		this.remanente = remanente;
	}

	public BigDecimal getIndice() {
		return indice;
	}

	public void setIndice(BigDecimal indice) {
		this.indice = indice;
	}
}
