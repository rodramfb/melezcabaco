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

public class BeanConsultaPedidosPendientesRevisar implements SessionBean,
		Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger
			.getLogger(BeanConsultaPedidosPendientesRevisar.class);

	private String validar = "";

	private int limit = 15;

	private long offset = 0l;
	private String fechadesde = "";
	private String fechahasta = "";

	private BigDecimal idestado = new BigDecimal(0);
	private String estado = "";
	private BigDecimal idtipoclie = new BigDecimal(0);
	private String tipoclie = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private List MovimientosList = new ArrayList();

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanConsultaPedidosPendientesRevisar() {
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
			Clientes Consulta = Common.getClientes();
			if (this.accion.equalsIgnoreCase("consulta"))
				// aca va la sentencia que me devuelva el listado

				if (this.fechadesde == null
						|| this.fechadesde.equalsIgnoreCase(""))
					this.mensaje = "No se puede dejar vacio el campo Fecha de Ingreso";

			if (this.fechahasta == null || this.fechahasta.equalsIgnoreCase(""))
				this.mensaje = "No se puede dejar vacio el campo Fecha Hasta";

			java.sql.Date fdesde = (java.sql.Date) Common.setObjectToStrOrTime(
					this.fechadesde, "StrToJSDate");
			java.sql.Date fhasta = (java.sql.Date) Common.setObjectToStrOrTime(
					this.fechahasta, "StrToJSDate");

			this.MovimientosList = Consulta
					.getCosnultaPedidosPendientesRevisar(fdesde.toString(),
							fhasta.toString(), this.idempresa, this.idestado,
							this.idtipoclie);

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("#"); // no tiene volver
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {

				}

				this.mensaje = "";

				if (this.fechadesde == null
						|| this.fechadesde.equalsIgnoreCase("")) {
					this.mensaje = "No se puede dejar vacio el campo Fecha Desde";
					return false;
				}

				if (this.fechahasta == null
						|| this.fechahasta.equalsIgnoreCase("")) {
					this.mensaje = "No se puede dejar vacio el campo Fecha Hasta";
					return false;
				}

				if (this.estado == null || this.estado.equalsIgnoreCase("")) {
					this.mensaje = "No se puede dejar vacio el campo Estado";
					return false;
				}
				if (this.tipoclie == null || this.tipoclie.equalsIgnoreCase("")) {
					this.mensaje = "No se puede dejar vacio el campo Tipo Cliente";
					return false;
				}

				this.ejecutarSentenciaDML();
			} else {
				/*
				 * if (!this.accion.equalsIgnoreCase("alta")) {
				 * getDatosCajaferiados(); }
				 */
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

	public String getFechadesde() {
		return fechadesde;
	}

	public void setFechadesde(String fechadesde) {
		this.fechadesde = fechadesde;
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

	public String getFechahasta() {
		return fechahasta;
	}

	public void setFechahasta(String fechahasta) {
		this.fechahasta = fechahasta;
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

	public BigDecimal getIdtipoclie() {
		return idtipoclie;
	}

	public void setIdtipoclie(BigDecimal idtipoclie) {
		this.idtipoclie = idtipoclie;
	}

	public String getTipoclie() {
		return tipoclie;
	}

	public void setTipoclie(String tipoclie) {
		this.tipoclie = tipoclie;
	}

}
