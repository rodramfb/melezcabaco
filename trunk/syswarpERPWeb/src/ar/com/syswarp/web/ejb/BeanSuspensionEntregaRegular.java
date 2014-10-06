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

public class BeanSuspensionEntregaRegular implements SessionBean, Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanSuspensionEntregaRegular.class);

	private String validar = "";

	private int limit = 15;
	private long offset = 0l;
	private BigDecimal idcliente = new BigDecimal(-1);
	private String cliente = "";

	private List motivoList = new ArrayList();

	private String idmotsusp = "";
	private String motsusp = "";

	private String anio = "0";
	private String mes = "";
	private String des_mes = "";
	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private List MovimientosList = new ArrayList();

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanSuspensionEntregaRegular() {
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

			this.mensaje = Consulta.SuspensiondeEntregasRegularesCreate(
					this.idcliente, new BigDecimal(this.anio), new BigDecimal(
							this.mes), this.idempresa, this.usuarioalt, Common
							.setNotNull(this.idmotsusp).equals("") ? null
							: new BigDecimal(this.idmotsusp));

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public boolean ejecutarValidacion() {
		try {

			this.motivoList = Common.getClientes()
					.getClientesSuspensionMotivosAll(100, 0, this.idempresa);

			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("#"); // no tiene volver
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				log.info("idcliente: "+idcliente);
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					// 2. len 0 para campos nulos
				}

				if (this.idcliente.longValue()< 1 )
				{
					this.mensaje = "No seleccionó ningun cliente-";
					return false;
				}
				if (Common.setNotNull(this.anio).equals("")) {
					this.mensaje = "No se puede dejar vacio el campo Año";
					return false;
				}

				if (Integer.parseInt(this.anio) < Calendar.getInstance().get(
						Calendar.YEAR)) {
					this.mensaje = "Año debe ser igual o mayor al actual.";
					return false;
				}

				if (Common.setNotNull(this.mes).equals("")) {
					this.mensaje = "No se puede dejar vacio el campo Mes";
					return false;
				}

				if (Integer.parseInt(this.mes) < (Calendar.getInstance().get(
						Calendar.MONTH) + 1)
						&& Integer.parseInt(this.anio) == Calendar
								.getInstance().get(Calendar.YEAR)) {
					this.mensaje = "Mes debe ser igual o mayor al actual.";
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

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getDes_mes() {
		return des_mes;
	}

	public void setDes_mes(String des_mes) {
		this.des_mes = des_mes;
	}

	public BigDecimal getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(BigDecimal idcliente) {
		this.idcliente = idcliente;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public List getMotivoList() {
		return motivoList;
	}

	public void setMotivoList(List motivoList) {
		this.motivoList = motivoList;
	}

	public String getIdmotsusp() {
		return idmotsusp;
	}

	public void setIdmotsusp(String idmotsusp) {
		this.idmotsusp = idmotsusp;
	}

	public String getMotsusp() {
		return motsusp;
	}

	public void setMotsusp(String motsusp) {
		this.motsusp = motsusp;
	}

}
