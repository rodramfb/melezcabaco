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

public class BeanStockDescompromiso implements SessionBean, Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanStockDescompromiso.class);

	private String validar = "";
	private String ejecutar = "";

	private String codigo_st = "";
	private String descrip_st = "";
	private BigDecimal iddeposito = BigDecimal.valueOf(-1);
	private String deposito = "";
	private String fechadesde = "";
	private String fechahasta = "";

	private java.sql.Date fecha_fer = new java.sql.Date(Common.initObjectTime());

	private String fecha_ferStr = Common.initObjectTimeStr();

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private List MovimientosList = new ArrayList();

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private BigDecimal reservado = new BigDecimal(0);

	private BigDecimal disponible = new BigDecimal(0);

	private BigDecimal existencia = new BigDecimal(0);

	public BeanStockDescompromiso() {
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
			Stock Consulta = Common.getStock();

			if (this.accion.equalsIgnoreCase("consulta"))
				// aca va la sentencia que me devuelva el listado
				this.mensaje = "";
			if (this.descrip_st == null || this.descrip_st.equalsIgnoreCase(""))
				this.mensaje = "No se puede dejar vacio el campo Articulo";

			if (this.deposito == null || this.deposito.equalsIgnoreCase(""))
				this.mensaje = "No se puede dejar vacio el campo Deposito";

			if (this.mensaje.equalsIgnoreCase("")) {
				this.MovimientosList = Consulta.getArticuloDescompromiso(
						this.codigo_st, this.iddeposito, this.idempresa);
				Iterator iterMov = this.MovimientosList.iterator();
				if (iterMov.hasNext()) {
					String[] sCampos = (String[]) iterMov.next();
					setReservado(new BigDecimal(sCampos[3]));
					setExistencia(new BigDecimal(sCampos[4]));
					setDisponible(new BigDecimal(sCampos[2]));
				} else {
					
					setReservado(new BigDecimal(0));
					setExistencia(new BigDecimal(0));
					setDisponible(new BigDecimal(0));
					
					this.mensaje = "El Artículo " + this.codigo_st
							+ " no existe en el depósito seleccionado: "
							+ this.deposito;
				}
			}

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
			if (!this.ejecutar.equalsIgnoreCase("")) {
				log.info("valor de EJECUTAR:" + this.ejecutar);
				Stock Consulta = Common.getStock();
				log.info("codigo_st: " + this.codigo_st);
				log.info("iddeposito: " + this.iddeposito);
				log.info("idempresa: " + this.idempresa);
				log.info("disponible: " + this.disponible);
				log.info("reservado: " + this.reservado);
				log.info("usuarioact: " + this.usuarioact);

				this.mensaje = Consulta.descomprometerUpdate(this.codigo_st,
						this.iddeposito, this.idempresa, this.disponible,
						this.reservado, this.usuarioact);
			}

			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					// 2. len 0 para campos nulos
				}

				/*
				 * if (!this.ejecutar.equalsIgnoreCase("") ){ Stock Consulta =
				 * Common.getStock();
				 * 
				 * }
				 */
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

	public Date getFecha_fer() {
		return fecha_fer;
	}

	public void setFecha_fer(java.sql.Date fecha_fer) {
		this.fecha_fer = fecha_fer;
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

	public String getFecha_ferStr() {
		return fecha_ferStr;
	}

	public void setFecha_ferStr(String fecha_ferStr) {
		this.fecha_ferStr = fecha_ferStr;
		this.fecha_fer = (java.sql.Date) Common.setObjectToStrOrTime(
				fecha_ferStr, "strToJSDate");
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public String getCodigo_st() {
		return codigo_st;
	}

	public void setCodigo_st(String codigo_st) {
		this.codigo_st = codigo_st;
	}

	public String getDeposito() {
		return deposito;
	}

	public void setDeposito(String deposito) {
		this.deposito = deposito;
	}

	public String getDescrip_st() {
		return descrip_st;
	}

	public void setDescrip_st(String descrip_st) {
		this.descrip_st = descrip_st;
	}

	public String getFechadesde() {
		return fechadesde;
	}

	public void setFechadesde(String fechadesde) {
		this.fechadesde = fechadesde;
	}

	public String getFechahasta() {
		return fechahasta;
	}

	public void setFechahasta(String fechahasta) {
		this.fechahasta = fechahasta;
	}

	public BigDecimal getIddeposito() {
		return iddeposito;
	}

	public void setIddeposito(BigDecimal iddeposito) {
		this.iddeposito = iddeposito;
	}

	public List getMovimientosList() {
		return MovimientosList;
	}

	public void setMovimientosList(List movimientosList) {
		MovimientosList = movimientosList;
	}

	public BigDecimal getReservado() {
		return reservado;
	}

	public void setReservado(BigDecimal reservado) {
		this.reservado = reservado;
	}

	public BigDecimal getDisponible() {
		return disponible;
	}

	public void setDisponible(BigDecimal disponible) {
		this.disponible = disponible;
	}

	public BigDecimal getExistencia() {
		return existencia;
	}

	public void setExistencia(BigDecimal existencia) {
		this.existencia = existencia;
	}

	public String getEjecutar() {
		return ejecutar;
	}

	public void setEjecutar(String ejecutar) {
		this.ejecutar = ejecutar;
	}

}
