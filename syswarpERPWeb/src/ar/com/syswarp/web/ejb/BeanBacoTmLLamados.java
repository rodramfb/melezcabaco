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

public class BeanBacoTmLLamados implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanBacoTmLLamados.class);

	private String validar = "";

	private BigDecimal idcampacabe = BigDecimal.valueOf(0);
	
	private BigDecimal idcliente = BigDecimal.valueOf(0);
	
	private String cliente ="";

	private String campacabe = "";

	
	private java.sql.Timestamp fdesde; //= new java.sql.Timestamp(Common.initObjectTime());

	private String fdesdeStr =""; //= Common.initObjectTimeStr();

	private java.sql.Timestamp fhasta; //= new java.sql.Timestamp(Common.initObjectTime());

	private String fhastaStr =""; //= Common.initObjectTimeStr();


	private BigDecimal idresultado = BigDecimal.valueOf(0);

	private String resultado = "";

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanBacoTmLLamados() {
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

	/*
	 * public void ejecutarSentenciaDML() { try { Tesoreria Cajaferiados =
	 * Common.getTesoreria(); if (this.accion.equalsIgnoreCase("alta"))
	 * this.mensaje = Cajaferiados.CajaferiadosCreate(this.feriado,
	 * this.fecha_fer, this.usuarioalt); else if
	 * (this.accion.equalsIgnoreCase("modificacion")) this.mensaje =
	 * Cajaferiados.CajaferiadosUpdate(this.idferiado, this.feriado,
	 * this.fecha_fer, this.usuarioact); else if
	 * (this.accion.equalsIgnoreCase("baja")) this.mensaje =
	 * Cajaferiados.CajaferiadosDelete(this.idferiado); } catch (Exception ex) {
	 * log.error(" ejecutarSentenciaDML() : " + ex); } }
	 */
	/*
	 * public void getDatosCajaferiados() { try { Tesoreria Cajaferiados =
	 * Common.getTesoreria(); List listCajaferiados = Cajaferiados
	 * .getCajaferiadosPK(this.idferiado); Iterator iterCajaferiados =
	 * listCajaferiados.iterator(); if (iterCajaferiados.hasNext()) { String[]
	 * uCampos = (String[]) iterCajaferiados.next(); // TODO: Constructores para
	 * cada tipo de datos this.idferiado =
	 * BigDecimal.valueOf(Long.parseLong(uCampos[0])); this.feriado =
	 * uCampos[1]; this.fecha_fer = java.sql.Date.valueOf(uCampos[2]);
	 * this.fecha_ferStr = Common.setObjectToStrOrTime(fecha_fer,
	 * "JSDateToStr").toString(); } else { this.mensaje = "Imposible recuperar
	 * datos para el registro seleccionado."; } } catch (Exception e) {
	 * log.error("getDatosCajaferiados()" + e); } }
	 */
	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("CajaferiadosAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					// 2. len 0 para campos nulos
				}
				// this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					// getDatosCajaferiados();
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

	public String getFdesdeStr() {
		return fdesdeStr;
	}

	public void setFdesdeStr(String fdesdeStr) {
		this.fdesdeStr = fdesdeStr;
		this.fdesde = (java.sql.Timestamp) Common.setObjectToStrOrTime(
				fdesdeStr, "StrToJSTs");
	}


	public String getCampacabe() {
		return campacabe;
	}

	public void setCampacabe(String campacabe) {
		this.campacabe = campacabe;
	}

	public java.sql.Timestamp getFdesde() {
		return fdesde;
	}

	public void setFdesde(java.sql.Timestamp fdesde) {
		this.fdesde = fdesde;
	}

	public java.sql.Timestamp getFhasta() {
		return fhasta;
	}

	public void setFhasta(java.sql.Timestamp fhasta) {
		this.fhasta = fhasta;
	}



	public BigDecimal getIdcampacabe() {
		return idcampacabe;
	}

	public void setIdcampacabe(BigDecimal idcampacabe) {
		this.idcampacabe = idcampacabe;
	}

	public BigDecimal getIdresultado() {
		return idresultado;
	}

	public void setIdresultado(BigDecimal idresultado) {
		this.idresultado = idresultado;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public String getFhastaStr() {
		return fhastaStr;
	}

	public void setFhastaStr(String fhastaStr) {
		this.fhastaStr = fhastaStr;
		this.fhasta = (java.sql.Timestamp) Common.setObjectToStrOrTime(
				fhastaStr, "StrToJSTs");
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public BigDecimal getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(BigDecimal idcliente) {
		this.idcliente = idcliente;
	}
}
