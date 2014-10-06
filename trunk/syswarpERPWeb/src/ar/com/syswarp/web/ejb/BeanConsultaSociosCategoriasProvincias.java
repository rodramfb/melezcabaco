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

public class BeanConsultaSociosCategoriasProvincias implements SessionBean,
		Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanConsultaSociosCategoriasProvincias.class);

	private String validar = "";

	private int limit = 15;

	private long offset = 0l;

    private BigDecimal idcampania = new BigDecimal(0);
    
    private String campania = "";
    
    
    private String fdesdeStr = "";
	private String fhastaStr = "";
	
	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private java.sql.ResultSet MovimientosList;
	private java.sql.ResultSet MovimientosListTotales;
	private java.sql.ResultSet MovimientosListTotalesDS;
	/**
	 * 
	 */
	private java.sql.ResultSet MovimientosListTotalesDS1;
	//private java.sql.ResultSet MovimientosListTotalesDS2;
	//private java.sql.ResultSet MovimientosListTotalesDS3;

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanConsultaSociosCategoriasProvincias() {
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
              this.MovimientosList = Consulta.getClientesCategoriaProvincia(
					this.idcampania,this.idempresa);
			
			
              this.MovimientosListTotales = Consulta.getClientesCategoriaProvinciaTotal(
					this.idcampania,this.idempresa);
               
              this.MovimientosListTotalesDS = Consulta.getClientesCategoriaProvinciaDS(
  					this.idcampania,this.idempresa);
              

              this.MovimientosListTotalesDS1 = Consulta.getClientesSociosAsignadosTotalesDS1(
    					this.idcampania,this.idempresa);
/*
              this.MovimientosListTotalesDS2 = Consulta.getClientesSociosAsignadosTotalesDS2(
    					this.idcampania,this.idempresa);

              this.MovimientosListTotalesDS3 = Consulta.getClientesSociosAsignadosTotalesDS3(
  					this.idcampania,this.idempresa);
             */
            
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
				
			    if(campania.trim().length() == 0  ){
			        this.mensaje = "No se puede dejar vacio el campo Campaña  ";
			        return false;
			     }
				
				this.mensaje = "";


				
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


	public java.sql.ResultSet getMovimientosList() {
		return MovimientosList;
	}

	public void setMovimientosList(java.sql.ResultSet movimientosList) {
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



	public BigDecimal getIdcampania() {
		return idcampania;
	}

	public void setIdcampania(BigDecimal idcampania) {
		this.idcampania = idcampania;
	}

	public String getCampania() {
		return campania;
	}

	public void setCampania(String campania) {
		this.campania = campania;
	}

	public String getFhastaStr() {
		return fhastaStr;
	}

	public void setFhastaStr(String fhastaStr) {
		this.fhastaStr = fhastaStr;
	}

	public String getFdesdeStr() {
		return fdesdeStr;
	}

	public void setFdesdeStr(String fdesdeStr) {
		this.fdesdeStr = fdesdeStr;
	}

	public java.sql.ResultSet getMovimientosListTotales() {
		return MovimientosListTotales;
	}

	public void setMovimientosListTotales(java.sql.ResultSet movimientosListTotales) {
		MovimientosListTotales = movimientosListTotales;
	}
/*
	public java.sql.ResultSet getMovimientosListTotalesDS() {
		return MovimientosListTotalesDS;
	}

	public void setMovimientosListTotalesDS(
			java.sql.ResultSet movimientosListTotalesDS) {
		MovimientosListTotalesDS = movimientosListTotalesDS;
	}

	public java.sql.ResultSet getMovimientosListTotalesDS1() {
		return MovimientosListTotalesDS1;
	}

	public void setMovimientosListTotalesDS1(
			java.sql.ResultSet movimientosListTotalesDS1) {
		MovimientosListTotalesDS1 = movimientosListTotalesDS1;
	}

	public java.sql.ResultSet getMovimientosListTotalesDS2() {
		return MovimientosListTotalesDS2;
	}

	public void setMovimientosListTotalesDS2(
			java.sql.ResultSet movimientosListTotalesDS2) {
		MovimientosListTotalesDS2 = movimientosListTotalesDS2;
	}

	public java.sql.ResultSet getMovimientosListTotalesDS3() {
		return MovimientosListTotalesDS3;
	}

	public void setMovimientosListTotalesDS3(
			java.sql.ResultSet movimientosListTotalesDS3) {
		MovimientosListTotalesDS3 = movimientosListTotalesDS3;
	}
*/

	public java.sql.ResultSet getMovimientosListTotalesDS() {
		return MovimientosListTotalesDS;
	}

	public void setMovimientosListTotalesDS(
			java.sql.ResultSet movimientosListTotalesDS) {
		MovimientosListTotalesDS = movimientosListTotalesDS;
	}

	public java.sql.ResultSet getMovimientosListTotalesDS1() {
		return MovimientosListTotalesDS1;
	}

	public void setMovimientosListTotalesDS1(
			java.sql.ResultSet movimientosListTotalesDS1) {
		MovimientosListTotalesDS1 = movimientosListTotalesDS1;
	}	
}
