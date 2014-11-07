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

public class BeanStockConsultaFamiliaGrupoDeposito implements SessionBean,
		Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger
			.getLogger(BeanStockConsultaFamiliaGrupoDeposito.class);

	private String validar = "";

	private BigDecimal codigo_fm = new BigDecimal(0);

	private String d_codigo_fm = "";

	private BigDecimal grupo_st = new BigDecimal(0);

	private String d_grupo_st = "";

	private BigDecimal iddeposito = new BigDecimal(0);

	private String deposito = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private List MovimientosList = new ArrayList();

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanStockConsultaFamiliaGrupoDeposito() {
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
			Stock Consulta = Common.getStock();

			if (this.accion.equalsIgnoreCase("consulta"))
				// aca va la sentencia que me devuelva el listado

				this.mensaje = "";

			if (this.d_codigo_fm == null
					|| this.d_codigo_fm.equalsIgnoreCase(""))
				this.mensaje = "No se puede dejar vacio el campo Familia";

			if (this.d_codigo_fm.equalsIgnoreCase("")
					&& this.d_grupo_st.equalsIgnoreCase("")
					&& !this.deposito.equalsIgnoreCase(""))
				this.mensaje = "Si selecciona un Deposito debe seleccionar una Familia y un Grupo ";

			if (!this.d_codigo_fm.equalsIgnoreCase("")
					&& this.d_grupo_st.equalsIgnoreCase("")
					&& !this.deposito.equalsIgnoreCase(""))
				this.mensaje = "Si selecciona un Deposito debe seleccionar una Familia y un Grupo ";

			if (this.d_codigo_fm.equalsIgnoreCase("")
					&& !this.d_grupo_st.equalsIgnoreCase("")
					&& !this.deposito.equalsIgnoreCase(""))
				this.mensaje = "Si selecciona un Deposito debe seleccionar una Familia y un Grupo ";

			if (this.mensaje.equalsIgnoreCase("")) {
				this.MovimientosList = Consulta
						.getCosnultaFamiliaGrupoDeposito(this.codigo_fm,
								this.grupo_st, this.iddeposito, this.idempresa);
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

	public String getDeposito() {
		return deposito;
	}

	public void setDeposito(String deposito) {
		this.deposito = deposito;
	}

	public BigDecimal getIddeposito() {
		return iddeposito;
	}

	public void setIddeposito(BigDecimal iddeposito) {
		this.iddeposito = iddeposito;
	}

	public List getMovimientosList() {
		return MovimientosList;
	}

	public void setMovimientosList(List movimientosList) {
		MovimientosList = movimientosList;
	}

	public BigDecimal getCodigo_fm() {
		return codigo_fm;
	}

	public void setCodigo_fm(BigDecimal codigo_fm) {
		this.codigo_fm = codigo_fm;
	}

	public BigDecimal getGrupo_st() {
		return grupo_st;
	}

	public void setGrupo_st(BigDecimal grupo_st) {
		this.grupo_st = grupo_st;
	}

	public String getD_grupo_st() {
		return d_grupo_st;
	}

	public void setD_grupo_st(String d_grupo_st) {
		this.d_grupo_st = d_grupo_st;
	}

	public String getD_codigo_fm() {
		return d_codigo_fm;
	}

	public void setD_codigo_fm(String d_codigo_fm) {
		this.d_codigo_fm = d_codigo_fm;
	}

}
