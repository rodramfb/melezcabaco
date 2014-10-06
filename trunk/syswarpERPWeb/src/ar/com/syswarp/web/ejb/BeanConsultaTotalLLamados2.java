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

public class BeanConsultaTotalLLamados2 implements SessionBean, Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanConsultaTotalLLamados2.class);

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

	private List MovimientosList = new ArrayList();

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanConsultaTotalLLamados2() {
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

				if (campania.trim().length() == 0) {
					this.mensaje = "No se puede dejar vacio el campo Campaña  ";
					return false;
				}
				if (this.fdesdeStr == null
						|| this.fdesdeStr.equalsIgnoreCase("")) {
					this.mensaje = "No se puede dejar vacio el campo Fecha Desde";
					return false;
				}
				if (this.fhastaStr == null
						|| this.fhastaStr.equalsIgnoreCase("")) {
					this.mensaje = "No se puede dejar vacio el campo Fecha Hasta";
					return false;
				}
				
				java.sql.Date fdesde = (java.sql.Date) Common
						.setObjectToStrOrTime(this.fdesdeStr, "StrToJSDate");
				java.sql.Date fhasta = (java.sql.Date) Common
						.setObjectToStrOrTime(this.fhastaStr, "StrToJSDate");
				
				if(fdesde.after(fhasta)){
					this.mensaje = "Fecha Desde debe ser menor o igual a Fecha Hasta";
					return false;
				}

				this.MovimientosList = Common.getClientes().getLlamados4Campania4Resultado(
						this.fdesdeStr.toString(), this.fhastaStr.toString(),
						this.idcampania, this.idempresa);
				
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

	public BigDecimal getIdcampania() {
		return idcampania;
	}

	public void setIdcampania(BigDecimal idcampania) {
		this.idcampania = idcampania;
	}

	public String getFdesdeStr() {
		return fdesdeStr;
	}

	public void setFdesdeStr(String fdesdeStr) {
		this.fdesdeStr = fdesdeStr;
	}

	public String getFhastaStr() {
		return fhastaStr;
	}

	public void setFhastaStr(String fhastaStr) {
		this.fhastaStr = fhastaStr;
	}

	public String getCampania() {
		return campania;
	}

	public void setCampania(String campania) {
		this.campania = campania;
	}
}
