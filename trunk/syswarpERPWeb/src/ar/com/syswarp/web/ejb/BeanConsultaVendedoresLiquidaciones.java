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

public class BeanConsultaVendedoresLiquidaciones implements SessionBean,
		Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);
	
	static Logger log = Logger.getLogger(BeanConsultaVendedoresLiquidaciones.class);

	private String validar = "";

	private int limit = 15;

	private long offset = 0l;
	private String anio = "";
	private BigDecimal mes = new BigDecimal(0);
   	private String des_mes = "";
   	private BigDecimal idvendedor = new BigDecimal(0);
   	private String vendedor = "";
	
	private String usuarioalt;

	private String usuarioact;
	
	private BigDecimal entro = new BigDecimal(0);

	private String mensaje = "";

	private String volver = "";

	private List MovimientosList = new ArrayList();

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanConsultaVendedoresLiquidaciones() {
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


			if (this.anio == null || this.anio.equalsIgnoreCase(""))
				this.mensaje = "No se puede dejar vacio el campo Año";
				
			if (this.des_mes == null || this.des_mes.equalsIgnoreCase(""))
		    	this.mensaje = "No se puede dejar vacio el campo Mes";
				
				
			if (this.vendedor == null || this.vendedor.equalsIgnoreCase(""))
		    	this.mensaje = "No se puede dejar vacio el campo Vendedor";	


			this.MovimientosList = Consulta.getCosnultaVendedoresResumenVendedores(this.anio, this.mes,this.idvendedor,this.idempresa);
			entro = new BigDecimal(1);
 
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
				
				this.mensaje = "";

				if (this.anio == null || this.anio.equalsIgnoreCase("")){
					this.mensaje = "No se puede dejar vacio el campo Año";
    				return false;
				}
    				
				if (this.des_mes == null || this.des_mes.equalsIgnoreCase("")){
			    	this.mensaje = "No se puede dejar vacio el campo Mes";
			    	return false;
					
				}
			    	
				if (this.vendedor == null || this.vendedor.equalsIgnoreCase("")){
			    	this.mensaje = "No se puede dejar vacio el campo Vendedor";	
			    	return false;
				}	
				
				if (!Common.esEntero(this.anio)) {
					this.mensaje = "El campo año es numerico";
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

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public BigDecimal getMes() {
		return mes;
	}

	public void setMes(BigDecimal mes) {
		this.mes = mes;
	}

	public String getDes_mes() {
		return des_mes;
	}

	public void setDes_mes(String des_mes) {
		this.des_mes = des_mes;
	}

	public BigDecimal getIdvendedor() {
		return idvendedor;
	}

	public void setIdvendedor(BigDecimal idvendedor) {
		this.idvendedor = idvendedor;
	}

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

	public BigDecimal getEntro() {
		return entro;
	}

	public void setEntro(BigDecimal entro) {
		this.entro = entro;
	}




}
