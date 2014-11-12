/* 
 javabean para la entidad (Formulario): Cajaferiados
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Aug 01 11:33:07 GMT-03:00 2006 
 
 Para manejar la pagina: CajaferiadosFrm.jsp
 
 */
package ar.com.syswarp.web.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Timestamp;

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

public class BeanProveedoresComprasporProveedoresMov implements SessionBean, Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanProveedoresComprasporProveedoresMov.class);

	private String validar = "";

	private String idproveedordesde = "";

	private String dproveedordesde = "";

	private String idproveedorhasta = "";

	private String dproveedorhasta = "";

	private String fechadesde = Common.initObjectTimeStr();


	private String fechahasta = Common.initObjectTimeStr();


	private String usuarioalt;

	
	private int limit = 15;

	private long offset = 0l;
	
	
	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private List MovimientosList = new ArrayList();

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanProveedoresComprasporProveedoresMov() {
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
			Proveedores Consulta = Common.getProveedores();
			if (this.accion.equalsIgnoreCase("consulta"))
				// aca va la sentencia que me devuelva el listado

				this.mensaje = "";

			if (this.idproveedordesde == null
					|| this.idproveedordesde.equalsIgnoreCase(""))
				this.mensaje = "No se puede dejar vacio el campo Proveedor Desde";

			if (this.idproveedorhasta == null
					|| this.idproveedorhasta.equalsIgnoreCase(""))
				this.mensaje = "No se puede dejar vacio el campo Proveedor Hasta";
			
			if (this.fechadesde == null
					|| this.fechadesde.equalsIgnoreCase(""))
				this.mensaje = "No se puede dejar vacio el campo Fecha Desde";

			if (this.fechahasta == null
					|| this.fechahasta.equalsIgnoreCase(""))
				this.mensaje = "No se puede dejar vacio el campo Fecha Hasta";
			

			if (this.mensaje.equalsIgnoreCase("")) {
				this.MovimientosList = Consulta.getProveedoInformeComprasporProveedores(
						this.limit, this.offset, this.idproveedordesde,this.idproveedorhasta,(Timestamp) Common.setObjectToStrOrTime(
								this.fechadesde, "StrToJSTs"),(Timestamp) Common.setObjectToStrOrTime(
										this.fechahasta, "StrToJSTs"),this.idempresa);
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


	public String getDproveedordesde() {
		return dproveedordesde;
	}

	public void setDproveedordesde(String dproveedordesde) {
		this.dproveedordesde = dproveedordesde;
	}

	public String getDproveedorhasta() {
		return dproveedorhasta;
	}

	public void setDproveedorhasta(String dproveedorhasta) {
		this.dproveedorhasta = dproveedorhasta;
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

	public String getIdproveedordesde() {
		return idproveedordesde;
	}

	public void setIdproveedordesde(String idproveedordesde) {
		this.idproveedordesde = idproveedordesde;
	}

	public String getIdproveedorhasta() {
		return idproveedorhasta;
	}

	public void setIdproveedorhasta(String idproveedorhasta) {
		this.idproveedorhasta = idproveedorhasta;
	}


}
