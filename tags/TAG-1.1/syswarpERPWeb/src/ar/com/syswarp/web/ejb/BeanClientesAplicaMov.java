/* 
 javabean para la entidad: vlov_Clientes
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Dec 12 14:43:42 GMT-03:00 2006 
 
 Para manejar la pagina: vlov_ClientesAbm.jsp
 
 */
package ar.com.syswarp.web.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import java.util.*;
import java.math.*;

import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanClientesAplicaMov implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanClientesAplicaMov.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private String accion = "";

	private String ocurrencia = "";

	private boolean disabled = false;

	private BigDecimal idcliente = new BigDecimal(-1);

	private String cliente = "";

	private String fechamov = "";

	private String mensaje = "";

	private String usuarioalt = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private HttpSession session;

	private BigDecimal nrointerno_q_can = new BigDecimal(-1);

	private String comprob_q_can = "";

	private BigDecimal importe_q_can = new BigDecimal(0);

	private BigDecimal saldo_q_can = new BigDecimal(0);

	private BigDecimal nrointerno_canc = new BigDecimal(-1);

	private String comprob_canc = "";

	private BigDecimal importe_canc = new BigDecimal(0);

	private BigDecimal saldo_canc = new BigDecimal(0);

	private String fvence_canc = "";

	private String totalaplicado = "0.00";

	private BigDecimal valoraplicado = new BigDecimal(0.00);

	private String confirmar = "";

	private String volver = "";

	public BeanClientesAplicaMov() {
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

			Clientes clientes = Common.getClientes();
			List listComprob = null;
			Iterator iterComprob;
			String[] datos = null;

			this.mensaje = clientes.clientesAplicarComprobante(
					this.nrointerno_canc, this.nrointerno_q_can,
					this.valoraplicado, this.usuarioalt, this.idempresa);

			if (this.mensaje.equalsIgnoreCase("OK")) {

				this.disabled = true;
				this.mensaje = "Documentos aplicados correctamente.";

			}
			//
			listComprob = clientes.getClientesMovCliPK(this.nrointerno_canc,
					this.idempresa);
			iterComprob = listComprob.iterator();
			datos = (String[]) iterComprob.next();

			this.saldo_canc = new BigDecimal(datos[8]);

			listComprob = clientes.getClientesMovCliPK(this.nrointerno_q_can,
					this.idempresa);
			iterComprob = listComprob.iterator();
			datos = (String[]) iterComprob.next();

			this.saldo_q_can = new BigDecimal(datos[8]);
			//

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public boolean ejecutarValidacion() {

		try {

			this.importe_canc = this.importe_canc.setScale(2,
					BigDecimal.ROUND_UP);
			this.importe_q_can = this.importe_q_can.setScale(2,
					BigDecimal.ROUND_UP);
			this.saldo_canc = this.saldo_canc.setScale(2, BigDecimal.ROUND_UP);
			this.saldo_q_can = this.saldo_q_can
					.setScale(2, BigDecimal.ROUND_UP);

			if (!this.confirmar.equals("")) {

				if (this.idcliente.longValue() <= 0) {
					this.mensaje = "Es Necesario Seleccionar Cliente.";
					return false;
				}

				if (this.nrointerno_q_can.longValue() <= 0
						&& this.nrointerno_q_can.longValue() >= -1) {
					this.mensaje = "Es Necesario Seleccionar un Movimiento a Aplicar.";
					return false;
				}

				if (this.nrointerno_canc.longValue() <= 0
						&& this.nrointerno_canc.longValue() >= -1) {
					this.mensaje = "Es necesario seleccionar un Movimiento a cancelar.";
					return false;
				}

				if (!Common.esNumerico(this.totalaplicado)) {
					this.mensaje = "Es necesario ingresar Importe que Aplica valido.";
					return false;
				}

				this.valoraplicado = (new BigDecimal(this.totalaplicado))
						.setScale(2, BigDecimal.ROUND_UP);

				if (this.valoraplicado.doubleValue() <= 0D) {
					this.mensaje = "Importe que Aplica debe ser mayor a cero.";
					return false;
				}

				if (this.saldo_q_can.doubleValue() < this.valoraplicado
						.doubleValue()) {
					this.mensaje = "Importe que Aplica no puede superar al saldo del movimiento que Aplica.";
					return false;
				}

				if (this.saldo_canc.doubleValue() < this.valoraplicado
						.doubleValue()) {
					this.mensaje = "Importe que Aplica no puede superar al saldo del movimiento que Cancela.";
					return false;
				}

				this.ejecutarSentenciaDML();

			}

		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
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

	public long getTotalRegistros() {
		return totalRegistros;
	}

	public void setTotalRegistros(long total) {
		this.totalRegistros = total;
	}

	public long getTotalPaginas() {
		return totalPaginas;
	}

	public void setTotalPaginas(long totalPaginas) {
		this.totalPaginas = totalPaginas;
	}

	public long getPaginaSeleccion() {
		return paginaSeleccion;
	}

	public void setPaginaSeleccion(long paginaSeleccion) {
		this.paginaSeleccion = paginaSeleccion;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getOcurrencia() {
		return ocurrencia;
	}

	public void setOcurrencia(String buscar) {
		this.ocurrencia = buscar;
	}

	public BigDecimal getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(BigDecimal idcliente) {
		this.idcliente = idcliente;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
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

	public boolean isDisabled() {
		return disabled;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public String getConfirmar() {
		return confirmar;
	}

	public void setConfirmar(String confirmar) {
		this.confirmar = confirmar;
	}

	public String getFechamov() {
		return fechamov;
	}

	public void setFechamov(String fechamov) {
		this.fechamov = fechamov;
	}

	public String getUsuarioalt() {
		return usuarioalt;
	}

	public void setUsuarioalt(String usuarioalt) {
		this.usuarioalt = usuarioalt;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public BigDecimal getNrointerno_q_can() {
		return nrointerno_q_can;
	}

	public void setNrointerno_q_can(BigDecimal nrointerno_q_can) {
		this.nrointerno_q_can = nrointerno_q_can;
	}

	public String getComprob_q_can() {
		return comprob_q_can;
	}

	public void setComprob_q_can(String comprob_q_can) {
		this.comprob_q_can = comprob_q_can;
	}

	public BigDecimal getImporte_q_can() {
		return importe_q_can;
	}

	public void setImporte_q_can(BigDecimal importe_q_can) {
		this.importe_q_can = importe_q_can;
	}

	public BigDecimal getSaldo_q_can() {
		return saldo_q_can;
	}

	public void setSaldo_q_can(BigDecimal saldo_q_can) {
		this.saldo_q_can = saldo_q_can;
	}

	public BigDecimal getNrointerno_canc() {
		return nrointerno_canc;
	}

	public void setNrointerno_canc(BigDecimal nrointerno_canc) {
		this.nrointerno_canc = nrointerno_canc;
	}

	public String getComprob_canc() {
		return comprob_canc;
	}

	public void setComprob_canc(String comprob_canc) {
		this.comprob_canc = comprob_canc;
	}

	public BigDecimal getImporte_canc() {
		return importe_canc;
	}

	public void setImporte_canc(BigDecimal importe_canc) {
		this.importe_canc = importe_canc;
	}

	public BigDecimal getSaldo_canc() {
		return saldo_canc;
	}

	public void setSaldo_canc(BigDecimal saldo_canc) {
		this.saldo_canc = saldo_canc;
	}

	public String getFvence_canc() {
		return fvence_canc;
	}

	public void setFvence_canc(String fvence_canc) {
		this.fvence_canc = fvence_canc;
	}

	public String getTotalaplicado() {
		return totalaplicado;
	}

	public void setTotalaplicado(String totalaplicado) {
		this.totalaplicado = totalaplicado;
	}
}
