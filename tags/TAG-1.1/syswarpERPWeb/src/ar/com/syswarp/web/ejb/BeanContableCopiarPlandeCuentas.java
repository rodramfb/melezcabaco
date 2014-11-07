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

public class BeanContableCopiarPlandeCuentas implements SessionBean,
		Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanContableCopiarPlandeCuentas.class);

	private String validar = "";

	private int limit = 15;

	private long offset = 0l;

	private String ejercicioOrigen = "";

	private String ejercicioDestino = "";

	private long totalRegistros = 0l;

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private List MovimientosList = new ArrayList();

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanContableCopiarPlandeCuentas() {
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
			Contable contable = Common.getContable();

			this.mensaje = contable.contableCopiarPlanCuentas(new BigDecimal(
					this.ejercicioOrigen),
					new BigDecimal(this.ejercicioDestino), this.idempresa);

			if (this.mensaje.equalsIgnoreCase("OK")) {
				this.mensaje = "Copia de Plan de Cuentas correcta.";
			}

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public boolean ejecutarValidacion() {
		try {

			Contable contable = Common.getContable();
			String filtro = "";
			String entidad = "contableinfiplan";

			if (!this.validar.equalsIgnoreCase("")) {

				if (!Common.esEntero(this.ejercicioOrigen)) {
					this.mensaje = "No se puede dejar vacio el campo Ejercicio Desde.";
					return false;
				}

				if (!Common.esEntero(this.ejercicioDestino)) {
					this.mensaje = "No se puede dejar vacio el campo Ejercicio Hasta.";
					return false;
				}
				
				if(this.ejercicioDestino.equals(this.ejercicioOrigen)){
					this.mensaje = "Ejercicio Origen y Destino no pueden ser el mismo.";
					return false;
				}

				// controlo que en el ejercicio desde tenga datos!
				filtro = " WHERE ejercicio = " + ejercicioOrigen.toString();
				this.totalRegistros = contable.getTotalEntidadFiltro(entidad,
						filtro, idempresa);

				if (this.totalRegistros < 1) {
					this.mensaje = "No existe plan de cuentas para el ejercicio origen: "
							+ ejercicioOrigen;
					return false;
				}

				// controlo que en el ejercicio hasta no tenga datos!
				filtro = " WHERE ejercicio = " + ejercicioDestino.toString();
				this.totalRegistros = contable.getTotalEntidadFiltro(entidad,
						filtro, idempresa);

				if (this.totalRegistros > 0) {
					this.mensaje = "Ya existe plan de cuentas para el ejercicio destino: "
							+ ejercicioDestino;
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

	public String getEjercicioOrigen() {
		return ejercicioOrigen;
	}

	public void setEjercicioOrigen(String ejercicioOrigen) {
		this.ejercicioOrigen = ejercicioOrigen;
	}

	public String getEjercicioDestino() {
		return ejercicioDestino;
	}

	public void setEjercicioDestino(String ejercicioDestino) {
		this.ejercicioDestino = ejercicioDestino;
	}

}
