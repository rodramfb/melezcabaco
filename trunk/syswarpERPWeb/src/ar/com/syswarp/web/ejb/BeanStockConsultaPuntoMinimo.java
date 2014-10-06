/* 
 javabean para la entidad (Formulario): Cajaferiados
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Aug 01 11:33:07 GMT-03:00 2006 
 
 Para manejar la pagina: CajaferiadosFrm.jsp
 
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

public class BeanStockConsultaPuntoMinimo implements SessionBean, Serializable {

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanStockConsultaPuntoMinimo.class);

	private String validar = "";

	private long totalRegistros = 0L;

	// deposito desde - hasta
	private BigDecimal iddepositodesde = BigDecimal.valueOf(-1);

	private String depositodesde = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private List MovimientosList = new ArrayList();

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanStockConsultaPuntoMinimo() {
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
		try {

			Stock stock = Common.getStock();

			if (this.accion.equalsIgnoreCase("consulta")) {

				// this.mensaje = "";
				// if (this.descrip_desde_st == null
				// || this.descrip_desde_st.equalsIgnoreCase(""))
				// this.mensaje =
				// "No se puede dejar vacio el campo Articulo Desde";

				// if (this.descrip_hasta_st == null
				// || this.descrip_hasta_st.equalsIgnoreCase(""))
				// this.mensaje =
				// "No se puede dejar vacio el campo Articulo Hasta";

				if (Common.setNotNull(this.depositodesde).equalsIgnoreCase(""))
					this.mensaje = "No se puede dejar vacio el campo Deposito Desde";

				// if (this.depositohasta == null
				// || this.depositohasta.equalsIgnoreCase(""))
				// this.mensaje =
				// "No se puede dejar vacio el campo Deposito Hasta";

				if (this.mensaje.equalsIgnoreCase("")) {

					this.MovimientosList = stock.getArticulosPuntoMinimo(
					// this.codigo_desde_st,
							// this.codigo_hasta_st,
							this.iddepositodesde,
							// this.iddepositohasta,
							this.idempresa);
				}

				this.totalRegistros = this.MovimientosList.size();

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

	public long getTotalRegistros() {
		return totalRegistros;
	}

	public void setTotalRegistros(long totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	public BigDecimal getiddepositodesde() {
		return iddepositodesde;
	}

	public void setiddepositodesde(BigDecimal iddepositodesde) {
		this.iddepositodesde = iddepositodesde;
	}

	public List getMovimientosList() {
		return MovimientosList;
	}

	public void setMovimientosList(List movimientosList) {
		MovimientosList = movimientosList;
	}

	public String getDepositodesde() {
		return depositodesde;
	}

	public void setDepositodesde(String depositodesde) {
		this.depositodesde = depositodesde;
	}
}
