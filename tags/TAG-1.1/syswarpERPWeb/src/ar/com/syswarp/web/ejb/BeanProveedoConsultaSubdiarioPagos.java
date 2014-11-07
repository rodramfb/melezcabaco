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

public class BeanProveedoConsultaSubdiarioPagos implements SessionBean, Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanProveedoConsultaSubdiarioPagos.class);

	private String validar = "";    
    

    private String fechadesde ="";
    private String fechahasta ="";
   
	private java.sql.Date fecha_fer = new java.sql.Date(Common.initObjectTime());

	private String fecha_ferStr = Common.initObjectTimeStr();

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private List SaldoAnteriorList = new ArrayList();
	private List MovimientosList = new ArrayList();
	private List SaldoFinalList = new ArrayList();
	
	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	//20110818 - CAMI - Infomes de compras ------------->
	
	private boolean flag = true;
	
	private int totCol = 0;
	
	//<--------------------------------------------------
	
	public BeanProveedoConsultaSubdiarioPagos() {
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
			Proveedores     Consulta     = Common.getProveedores();
			
			if (this.accion.equalsIgnoreCase("consulta"))
				// aca va la sentencia que me devuelva el listado
				
				 this.mensaje = "";
			
			    if (this.fechadesde == null || this.fechadesde.equalsIgnoreCase("")) 
				    this.mensaje = "No se puede dejar vacio el campo Fecha Desde";

			    if (this.fechahasta == null || this.fechahasta.equalsIgnoreCase("")) 
				    this.mensaje = "No se puede dejar vacio el campo Fecha Hasta";
			    
			    if (this.mensaje.equalsIgnoreCase("") ){
	//		    	this.SaldoAnteriorList = Consulta.getMovArticuloDepositoSaldoAnterior(this.codigo_st, this.iddeposito, this.fechadesde, this.fechahasta, idempresa);
			    	this.MovimientosList   = Consulta.getProveedoresSubdiarioPagos( this.fechadesde, this.fechahasta, idempresa);
	//		    	this.SaldoFinalList    = Consulta.getMovArticuloDepositoSaldoFinal(this.codigo_st, this.iddeposito, this.fechadesde, this.fechahasta, idempresa);
			    	//20110818 - CAMI - Informes de compras ------------->
			    	if (this.MovimientosList.size()< 1 )
			    	{
			    		
			    		this.flag = false;
			    		
			    	}
			    	if (this.flag )
			    	{
			    		General general  =Common.getGeneral();
			    		
			    		general.setArchivo(this.MovimientosList, new BigDecimal(totCol),"ProveedoresSubdiarioPagos");
			    		}
			    	//<----------------------------------------------
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
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					// 2. len 0 para campos nulos
				}
				this.ejecutarSentenciaDML();
			} else {
				/*
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosCajaferiados();
				}
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


	public List getSaldoAnteriorList() {
		return SaldoAnteriorList;
	}

	public void setSaldoAnteriorList(List saldoAnteriorList) {
		SaldoAnteriorList = saldoAnteriorList;
	}

	public List getMovimientosList() {
		return MovimientosList;
	}

	public void setMovimientosList(List movimientosList) {
		MovimientosList = movimientosList;
	}

	public List getSaldoFinalList() {
		return SaldoFinalList;
	}

	public void setSaldoFinalList(List saldoFinalList) {
		SaldoFinalList = saldoFinalList;
	}
	//20110818- CAMI- Infomes de compras ----------------->
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public int getTotCol() {
		return totCol;
	}

	public void setTotCol(int totCol) {
		this.totCol = totCol;
	}
//<---------------------------------------------

}
