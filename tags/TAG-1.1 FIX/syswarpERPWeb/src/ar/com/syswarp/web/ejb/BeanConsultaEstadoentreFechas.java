/* 
 javabean para la entidad (Formulario): Cajaferiados
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Aug 01 11:33:07 GMT-03:00 2006 
 
 Para manejar la pagina: 
 
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


import ar.com.syswarp.api.Common;

public class BeanConsultaEstadoentreFechas implements SessionBean, Serializable {
	
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanConsultaEstadoentreFechas.class);

	private String validar = "";

	private int limit = 15;

	private long offset = 0l;

	private String fdesde = "";

	private String fhasta = "";

	private BigDecimal idestado = new BigDecimal(0);

	private String estado = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private List MovimientosList = new ArrayList();

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	// 20110405 - Mantis 699 -->

	private java.sql.Date fechadesde = null;

	private java.sql.Date fechahasta = null;

	private String criterio = "M";

	private long totalregistros = 0L;

	// <--

	public BeanConsultaEstadoentreFechas() {
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

	public void ejecutarQuery() {
		try {

			this.MovimientosList = Common.getGeneral()
					.getClientesConsultadeEstados(this.fechadesde.toString(),
							this.fechahasta.toString(), this.idestado,
							this.criterio, this.idempresa);

			if (this.MovimientosList != null && !this.MovimientosList.isEmpty()) {
				this.totalregistros = (this.MovimientosList.size());
			}else this.mensaje = "No existen registros para el criterio de busqueda.";

		} catch (Exception ex) {
			log.error(" ejecutarQuery() : " + ex);
		}
	}

	public boolean ejecutarValidacion() {
		try {

		
			if (!this.validar.equalsIgnoreCase("")) {

				if (Common.setNotNull(this.criterio).equals("")) {
					this.mensaje = "Seleccione criterio.";
					return false;
				}

				if (!Common.isFormatoFecha(this.fdesde)
						|| !Common.isFechaValida(this.fdesde)) {
					this.mensaje = "Ingrese Fecha Desde valida";
					return false;
				}

				if (!Common.isFormatoFecha(this.fhasta)
						|| !Common.isFechaValida(this.fhasta)) {
					this.mensaje = "Ingrese Fecha Hasta valida";
					return false;
				}

				this.fechadesde = (java.sql.Date) Common.setObjectToStrOrTime(
						this.fdesde, "StrToJSDate");
				this.fechahasta = (java.sql.Date) Common.setObjectToStrOrTime(
						this.fhasta, "StrToJSDate");

				if (this.fechadesde.after(this.fechahasta)) {
					this.mensaje = "Fecha desde debe ser menor a fecha hasta";
					return false;
				}

				if (this.estado == null || this.estado.equalsIgnoreCase("")) {
					this.mensaje = "No se puede dejar vacio el campo Estado";
					return false;
				}

				this.ejecutarQuery();
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

	public List getMovimientosList() {
		return MovimientosList;
	}

	public void setMovimientosList(List movimientosList) {
		MovimientosList = movimientosList;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public String getFdesde() {
		return fdesde;
	}

	public void setFdesde(String fdesde) {
		this.fdesde = fdesde;
	}

	public String getFhasta() {
		return fhasta;
	}

	public void setFhasta(String fhasta) {
		this.fhasta = fhasta;
	}

	public BigDecimal getIdestado() {
		return idestado;
	}

	public void setIdestado(BigDecimal idestado) {
		this.idestado = idestado;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	// 20110405 - Mantis 699 -->

	public String getCriterio() {
		return criterio;
	}

	public void setCriterio(String criterio) {
		this.criterio = criterio;
	}

	public long getTotalregistros() {
		return totalregistros;
	}

	public void setTotalregistros(long totalregistros) {
		this.totalregistros = totalregistros;
	}



	// <--

}
