/* 
   javabean para la entidad (Formulario): bacoRefOperaciones
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Jun 15 11:32:56 ART 2010 
   
   Para manejar la pagina: bacoRefOperacionesFrm.jsp
      
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

public class BeanBacoRefOperacionesFrm implements SessionBean, Serializable {

	private SessionContext context;

	static Logger log = Logger.getLogger(BeanBacoRefOperacionesFrm.class);

	private String validar = "";

	private BigDecimal idoperacion = new BigDecimal(-1);

	private String operacion = "";

	private String descripcion = "";

	private String puntaje = "";

	private String tipo = "";

	private String signo = "";

	private String fechadesde = "";

	private String fechahasta = "";

	private String fechabaja = "";

	private String proceso = "";

	private BigDecimal idempresa = new BigDecimal(-1);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private List listOperaciones = new ArrayList();

	public BeanBacoRefOperacionesFrm() {
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
				this.mensaje = clientes.bacoRefOperacionesCreate(
						this.operacion, this.descripcion, new BigDecimal(
								this.puntaje), this.tipo, this.signo,
						(java.sql.Date) Common.setObjectToStrOrTime(
								this.fechadesde, "StrToJSDate"),
						(java.sql.Date) Common.setObjectToStrOrTime(
								this.fechahasta, "StrToJSDate"), null,
						this.proceso, this.idempresa, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clientes.bacoRefOperacionesUpdate(
						this.idoperacion, this.operacion, this.descripcion,
						new BigDecimal(this.puntaje), this.tipo, this.signo,
						(java.sql.Date) Common.setObjectToStrOrTime(
								this.fechadesde, "StrToJSDate"),
						(java.sql.Date) Common.setObjectToStrOrTime(
								this.fechahasta, "StrToJSDate"), null,
						this.proceso, this.idempresa, this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clientes.bacoRefOperacionesDeleteLogico(
						idoperacion, this.idempresa, this.usuarioact);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosBacoRefOperaciones() {
		try {
			Clientes clientes = Common.getClientes();
			List listBacoRefOperaciones = clientes.getBacoRefOperacionesPK(
					this.idoperacion, this.idempresa);
			Iterator iterBacoRefOperaciones = listBacoRefOperaciones.iterator();
			if (iterBacoRefOperaciones.hasNext()) {
				String[] uCampos = (String[]) iterBacoRefOperaciones.next();
				// TODO: Constructores para cada tipo de datos
				this.idoperacion = new BigDecimal(uCampos[0]);
				this.operacion = uCampos[1];
				this.descripcion = uCampos[2];
				this.puntaje = uCampos[3];
				this.tipo = uCampos[4];
				this.signo = uCampos[5];
				this.fechadesde = (String) Common.setObjectToStrOrTime(
						java.sql.Date.valueOf(uCampos[6]), "JSDateToStr");
				this.fechahasta = (String) Common.setObjectToStrOrTime(
						java.sql.Date.valueOf(uCampos[7]), "JSDateToStr");
				this.fechabaja = !Common.setNotNull(uCampos[8]).equals("") ? (String) Common
						.setObjectToStrOrTime(
								java.sql.Date.valueOf(uCampos[8]),
								"JSDateToStr")
						: "";
				this.proceso = uCampos[9];

			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosBacoRefOperaciones()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("bacoRefOperacionesAbm.jsp");
				return true;
			}

			this.listOperaciones = Common.getClientes()
					.getBacoRefTipoOperacionesAll(100, 0, this.idempresa);

			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos

					if (Common.setNotNull(this.operacion).equals("")) {
						this.mensaje = "No se puede dejar vacio el campo operación. ";
						return false;
					}

					if (Common.setNotNull(this.descripcion).equals("")) {
						this.mensaje = "No se puede dejar vacio el campo descipción. ";
						return false;
					}

					if (!Common.esNumerico(this.puntaje)) {
						this.mensaje = "Ingrese un valor númerico válido para puntaje. ";
						return false;
					}

					if (Common.setNotNull(this.tipo).equals("")) {
						this.mensaje = "No se puede dejar vacio el campo tipo. ";
						return false;
					}

					if (Common.setNotNull(this.signo).equals("")) {
						this.mensaje = "No se puede dejar vacio el campo acción. ";
						return false;
					}

					if (Common.setNotNull(this.proceso).equals("")) {
						this.mensaje = "Selecciones tipo de proceso. ";
						return false;
					}

					if (!Common.isFormatoFecha(this.fechadesde)
							|| !Common.isFechaValida(this.fechadesde)) {
						this.mensaje = "Ingrese fecha desde válida. ";
						return false;
					}
					if (!Common.isFormatoFecha(this.fechahasta)
							|| !Common.isFechaValida(this.fechahasta)) {
						this.mensaje = "Ingrese fecha hasta válida. ";
						return false;
					}

					java.sql.Date fdesde = (java.sql.Date) Common
							.setObjectToStrOrTime(this.fechadesde,
									"StrToJSDate");
					java.sql.Date fhasta = (java.sql.Date) Common
							.setObjectToStrOrTime(this.fechahasta,
									"StrToJSDate");

					if (fdesde.after(fhasta)) {
						this.mensaje = "Fecha desde debe ser mayor o igual a fecha hasta.";
						return false;
					}

				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosBacoRefOperaciones();
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
	public BigDecimal getIdoperacion() {
		return idoperacion;
	}

	public void setIdoperacion(BigDecimal idoperacion) {
		this.idoperacion = idoperacion;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(String puntaje) {
		this.puntaje = puntaje;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getSigno() {
		return signo;
	}

	public void setSigno(String signo) {
		this.signo = signo;
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

	public String getFechabaja() {
		return fechabaja;
	}

	public void setFechabaja(String fechabaja) {
		this.fechabaja = fechabaja;
	}

	public String getProceso() {
		return proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
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

	public List getListOperaciones() {
		return listOperaciones;
	}

}
