/* 
 javabean para la entidad (Formulario): crmllamados
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Jun 19 11:15:59 GMT-03:00 2007 
 
 Para manejar la pagina: crmllamadosFrm.jsp
 
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

public class BeanCrmllamadosxusuarioFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanCrmllamadosxusuarioFrm.class);

	private String validar = "";

	private BigDecimal idllamado = BigDecimal.valueOf(-1);

	private BigDecimal idtipollamada = BigDecimal.valueOf(0);

	private String tipollamada = "";

	private BigDecimal idusuario = BigDecimal.valueOf(0);

	private String usuario = "";

	private String idindividuos = "";

	private String razon_nombre = "";

	private String idfamiliar = "";

	private String nombre = "";

	private Timestamp fechallamada = new Timestamp(Common.initObjectTime());

	private String fechallamadaStr = Common.initObjectTimeStr();

	private String obseravaciones = "";

	private BigDecimal idempresa;

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private BigDecimal idresultadollamada = new BigDecimal(-1);

	private String fecharellamada = "";

	private List listResultadoLlamada = null;

	private List listTipoLlamados = null;

	public BeanCrmllamadosxusuarioFrm() {
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
			CRM crmllamados = Common.getCrm();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = crmllamados.crmllamadosCreate(
						this.idtipollamada, this.idusuario, this.idindividuos,
						this.idfamiliar, this.fechallamada,
						this.obseravaciones, this.idresultadollamada,
						(Timestamp) Common.setObjectToStrOrTime(
								this.fecharellamada, "StrToJSTs"),
						this.idempresa, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = crmllamados.crmllamadosUpdate(this.idllamado,
						this.idtipollamada, this.idusuario, this.idindividuos,
						this.idfamiliar, this.fechallamada,
						this.obseravaciones, this.idresultadollamada,
						(Timestamp) Common.setObjectToStrOrTime(
								this.fecharellamada, "StrToJSTs"),
						this.usuarioact, this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = crmllamados.crmllamadosDelete(this.idllamado,
						this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosCrmllamados() {
		try {
			CRM crmllamados = Common.getCrm();
			List listCrmllamados = crmllamados.getCrmllamadosPK(this.idllamado,
					this.idempresa);
			Iterator iterCrmllamados = listCrmllamados.iterator();
			if (iterCrmllamados.hasNext()) {
				String[] uCampos = (String[]) iterCrmllamados.next();
				// TODO: Constructores para cada tipo de datos
				this.idllamado = BigDecimal.valueOf(Long.parseLong(uCampos[0]));
				this.idtipollamada = BigDecimal.valueOf(Long
						.parseLong(uCampos[1]));
				this.tipollamada = uCampos[2];
				this.idusuario = BigDecimal.valueOf(Long.parseLong(uCampos[3]));
				this.usuario = uCampos[4];
				this.idindividuos = uCampos[5];
				this.razon_nombre = uCampos[6];
				this.idfamiliar = uCampos[7];
				this.nombre = uCampos[8];

				this.fechallamada = Timestamp.valueOf(uCampos[9]);
				this.fechallamadaStr = Common.setObjectToStrOrTime(
						fechallamada, "JSTsToStr").toString();
				this.obseravaciones = uCampos[10];
				this.idresultadollamada = new BigDecimal(uCampos[11]);

				this.fecharellamada = uCampos[13] != null ? Common
						.setObjectToStrOrTime(Timestamp.valueOf(uCampos[13]),
								"JSTsToStr").toString() : "";

			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosCrmllamados()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {

			CRM crmllamados = Common.getCrm();

			this.listTipoLlamados = crmllamados.getCrmtiposllamadasAll(100, 0,
					this.idempresa);

			this.listResultadoLlamada = crmllamados
					.getcrmresultadosllamadosAll(100, 0, this.idempresa);

			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("crmllamadosxusuarioAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (idtipollamada == null || idtipollamada.longValue() < 0) {
						this.mensaje = "No se puede dejar vacio el campo idtipollamada ";
						return false;
					}
					if (idusuario == null) {
						this.mensaje = "No se puede dejar vacio el campo idusuario ";
						return false;
					}
					if (fechallamada == null) {
						this.mensaje = "No se puede dejar vacio el campo fechallamada ";
						return false;
					}

					/*
					 * if (usuario.trim().length() == 0) { this.mensaje = "No se
					 * puede dejar vacio el campo Usuario "; return false; }
					 */

					if (idresultadollamada == null
							|| idresultadollamada.longValue() < 0) {
						this.mensaje = "No se puede dejar vacio el campo resultado de la llamada  ";
						return false;

					}

					// 2. len 0 para campos nulos
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosCrmllamados();
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
	public BigDecimal getIdllamado() {
		return idllamado;
	}

	public void setIdllamado(BigDecimal idllamado) {
		this.idllamado = idllamado;
	}

	public BigDecimal getIdtipollamada() {
		return idtipollamada;
	}

	public void setIdtipollamada(BigDecimal idtipollamada) {
		this.idtipollamada = idtipollamada;
	}

	public String getIdindividuos() {
		return idindividuos;
	}

	public void setIdindividuos(String idindividuos) {
		this.idindividuos = idindividuos;
	}

	public String getIdfamiliar() {
		return idfamiliar;
	}

	public void setIdfamiliar(String idfamiliar) {
		this.idfamiliar = idfamiliar;
	}

	public Timestamp getFechallamada() {
		return fechallamada;
	}

	public void setFechallamada(Timestamp fechallamada) {
		this.fechallamada = fechallamada;
	}

	public String getObseravaciones() {
		return obseravaciones;
	}

	public void setObseravaciones(String obseravaciones) {
		this.obseravaciones = obseravaciones;
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

	public String getFechallamadaStr() {
		return fechallamadaStr;
	}

	public void setFechallamadaStr(String fechallamadaStr) {
		this.fechallamadaStr = fechallamadaStr;
		this.fechallamada = (java.sql.Timestamp) Common.setObjectToStrOrTime(
				fechallamadaStr, "StrToJSTs");
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRazon_nombre() {
		return razon_nombre;
	}

	public void setRazon_nombre(String razon_nombre) {
		this.razon_nombre = razon_nombre;
	}

	public String getTipollamada() {
		return tipollamada;
	}

	public void setTipollamada(String tipollamada) {
		this.tipollamada = tipollamada;
	}

	public BigDecimal getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(BigDecimal idusuario) {
		this.idusuario = idusuario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public BigDecimal getIdresultadollamada() {
		return idresultadollamada;
	}

	public void setIdresultadollamada(BigDecimal idresultadollamada) {
		this.idresultadollamada = idresultadollamada;
	}

	public List getListResultadoLlamada() {
		return listResultadoLlamada;
	}

	public void setListResultadoLlamada(List listResultadoLlamada) {
		this.listResultadoLlamada = listResultadoLlamada;
	}

	public String getFecharellamada() {
		return fecharellamada;
	}

	public void setFecharellamada(String fecharellamada) {
		this.fecharellamada = fecharellamada;
	}

	public List getListTipoLlamados() {
		return listTipoLlamados;
	}

	public void setListTipoLlamados(List listTipoLlamados) {
		this.listTipoLlamados = listTipoLlamados;
	}

}
