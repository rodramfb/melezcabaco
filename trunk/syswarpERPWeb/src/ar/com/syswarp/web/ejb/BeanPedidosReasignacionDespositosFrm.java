/* 
   javabean para la entidad (Formulario): pedidosReasignacionDespositos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Feb 04 13:46:55 ART 2011 
   
   Para manejar la pagina: pedidosReasignacionDespositosFrm.jsp
      
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

public class BeanPedidosReasignacionDespositosFrm implements SessionBean,
		Serializable {

	private SessionContext context;

	static Logger log = Logger
			.getLogger(BeanPedidosReasignacionDespositosFrm.class);

	private String validar = "";

	private BigDecimal idreasignaciondeposito = new BigDecimal(-1);

	private BigDecimal codigo_dt = new BigDecimal(-1);

	private String tipo = "";

	private String fechadesde = "";

	private String fechahasta = "";

	private java.sql.Date fdesde = null;

	private java.sql.Date fhasta = null;

	private BigDecimal idempresa = new BigDecimal(-1);;

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private List listDepositos = new ArrayList();

	public BeanPedidosReasignacionDespositosFrm() {
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
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = clientes.pedidosReasignacionDespositosCreate(
						this.codigo_dt, this.tipo, this.fdesde, this.fhasta,
						this.idempresa, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clientes.pedidosReasignacionDespositosUpdate(
						this.idreasignaciondeposito, this.codigo_dt, this.tipo,
						this.fdesde, this.fhasta, this.idempresa,
						this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clientes.pedidosReasignacionDespositosDelete(
						this.idreasignaciondeposito, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosPedidosReasignacionDespositos() {
		try {
			Clientes clientes = Common.getClientes();
			List listPedidosReasignacionDespositos = clientes
					.getPedidosReasignacionDespositosPK(
							this.idreasignaciondeposito, this.idempresa);
			Iterator iterPedidosReasignacionDespositos = listPedidosReasignacionDespositos
					.iterator();
			if (iterPedidosReasignacionDespositos.hasNext()) {
				String[] uCampos = (String[]) iterPedidosReasignacionDespositos
						.next();
				// TODO: Constructores para cada tipo de datos
				this.idreasignaciondeposito = new BigDecimal(uCampos[0]);
				this.codigo_dt = new BigDecimal(uCampos[1]);
				this.tipo = uCampos[2];
				this.fechadesde = Common.setObjectToStrOrTime(
						java.sql.Date.valueOf(uCampos[3]), "JSDateToStr")
						.toString();
				this.fechahasta = Common.setObjectToStrOrTime(
						java.sql.Date.valueOf(uCampos[4]), "JSDateToStr")
						.toString();

			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosPedidosReasignacionDespositos()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("pedidosReasignacionDespositosAbm.jsp");
				return true;
			}

			this.listDepositos = Common.getStock().getStockdepositosAll(1000,
					0, this.idempresa);

			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (this.codigo_dt == null
							|| this.codigo_dt.longValue() < 1) {
						this.mensaje = "Seleccione deposito.";
						return false;
					}
					if (Common.setNotNull(this.tipo).equals("")) {
						this.mensaje = "No se puede dejar vacio el campo tipo ";
						return false;
					}

					if (!Common.isFormatoFecha(this.fechadesde)
							|| !Common.isFechaValida(this.fechadesde)) {
						this.mensaje = "Ingrese fecha desde valida. ";
						return false;
					}

					this.fdesde = (java.sql.Date) Common.setObjectToStrOrTime(
							this.fechadesde, "StrToJSDate");

					if (!Common.isFormatoFecha(this.fechahasta)
							|| !Common.isFechaValida(this.fechahasta)) {
						this.mensaje = "Fecha hasta invalida. ";
						return false;
					}

					this.fhasta = (java.sql.Date) Common.setObjectToStrOrTime(
							this.fechahasta, "StrToJSDate");

					if (this.fdesde.after(fhasta)) {
						this.mensaje = "Fecha desde no puede ser mayor a fecha hasta. ";
						return false;
					}

				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosPedidosReasignacionDespositos();
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

	// metodos para cada atributo de la entidad
	public BigDecimal getIdreasignaciondeposito() {
		return idreasignaciondeposito;
	}

	public void setIdreasignaciondeposito(BigDecimal idreasignaciondeposito) {
		this.idreasignaciondeposito = idreasignaciondeposito;
	}

	public BigDecimal getCodigo_dt() {
		return codigo_dt;
	}

	public void setCodigo_dt(BigDecimal codigo_dt) {
		this.codigo_dt = codigo_dt;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
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

	public List getListDepositos() {
		return listDepositos;
	}

	public void setListDepositos(List listDepositos) {
		this.listDepositos = listDepositos;
	}

}
