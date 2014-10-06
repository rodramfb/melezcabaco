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

public class BeanStockConsultaPedidosStockReserva implements SessionBean,
		Serializable {

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger
			.getLogger(BeanStockConsultaPedidosStockReserva.class);

	private String validar = "";

	private String articulodesde = "";

	private String descrip_st = "";

	private String codigo_dt = "";

	private String descrip_dt = "";

	private String fechaDesde = "";

	private String fechaHasta = "";

	private java.sql.Date fDesde = null;

	private java.sql.Date fHasta = null;

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private List MovimientosList = new ArrayList();

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	// EJV - 20110103 - Mantis 651 -->

	private String tipopedido = "";

	// <--

	public BeanStockConsultaPedidosStockReserva() {
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

			Stock stock = Common.getStock();

			this.MovimientosList = stock.getConsultaPedidosStockReserva(
					this.articulodesde, this.codigo_dt, this.fDesde,
					this.fHasta, this.tipopedido, this.idempresa);

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}

	}

	public boolean ejecutarValidacion() {
		try {

			if (this.accion.equalsIgnoreCase("consulta")) {
				// aca va la sentencia que me devuelva el listado

				if (Common.setNotNull(this.articulodesde).equalsIgnoreCase("")) {
					this.mensaje = "Seleccione artículo a consultar.";
					return false;
				}

				if (this.codigo_dt.equalsIgnoreCase("")) {
					this.mensaje = "Seleccione deposito.";
					return false;
				}

				if (!Common.setNotNull(this.fechaDesde).equals("")
						|| !Common.setNotNull(this.fechaHasta).equals("")) {

					if (!Common.isFormatoFecha(this.fechaDesde)
							|| !Common.isFechaValida(this.fechaDesde)) {
						this.mensaje = "Ingrese fecha desde valida.";
						return false;
					}

					if (!Common.isFormatoFecha(this.fechaHasta)
							|| !Common.isFechaValida(this.fechaHasta)) {
						this.mensaje = "Ingrese fecha hasta valida.";
						return false;
					}

					this.fDesde = (java.sql.Date) Common.setObjectToStrOrTime(
							this.fechaDesde, "StrToJSDate");
					this.fHasta = (java.sql.Date) Common.setObjectToStrOrTime(
							this.fechaHasta, "StrToJSDate");

					if (this.fHasta.before(this.fDesde)) {

						this.mensaje = "Fecha hasta debe ser menor o igual a fecha desde.";
						return false;

					}
				}
				
				if(Common.setNotNull(tipopedido).equalsIgnoreCase("")){
					this.mensaje = "Seleccione tipo pedido";
					return false;
				}

				this.ejecutarSentenciaDML();

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

	public String getArticulodesde() {
		return articulodesde;
	}

	public void setArticulodesde(String articulodesde) {
		this.articulodesde = articulodesde;
	}

	public String getDescrip_st() {
		return descrip_st;
	}

	public void setDescrip_st(String descrip_st) {
		this.descrip_st = descrip_st;
	}

	public String getCodigo_dt() {
		return codigo_dt;
	}

	public void setCodigo_dt(String codigo_dt) {
		this.codigo_dt = codigo_dt;
	}

	public String getDescrip_dt() {
		return descrip_dt;
	}

	public void setDescrip_dt(String descrip_dt) {
		this.descrip_dt = descrip_dt;
	}

	public String getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public String getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public String getTipopedido() {
		return tipopedido;
	}

	public void setTipopedido(String tipopedido) {
		this.tipopedido = tipopedido;
	}

}
