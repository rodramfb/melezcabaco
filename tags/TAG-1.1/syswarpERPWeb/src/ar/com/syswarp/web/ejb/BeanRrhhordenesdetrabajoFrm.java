/* 
 javabean para la entidad (Formulario): rrhhordenesdetrabajo
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Fri Apr 04 12:30:21 ART 2008 
 
 Para manejar la pagina: rrhhordenesdetrabajoFrm.jsp
 
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

public class BeanRrhhordenesdetrabajoFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanRrhhordenesdetrabajoFrm.class);

	private String validar = "";

	private BigDecimal idordendetrabajo = BigDecimal.valueOf(-1);

	private BigDecimal idcliente = BigDecimal.valueOf(0);
	
	private String cliente = "";

	private String descripcion = "";

	private Timestamp fechainicio = new Timestamp(Common.initObjectTime());

	private String fechainicioStr = Common.initObjectTimeStr();

	private Timestamp fechaprometida = new Timestamp(Common.initObjectTime());

	private String fechaprometidaStr = Common.initObjectTimeStr();

	private Timestamp fechafinal = new Timestamp(Common.initObjectTime());

	private String fechafinalStr = Common.initObjectTimeStr();

	private BigDecimal horasestimadas = BigDecimal.valueOf(0);

	private Double valorhoracliente = Double.valueOf("0");

	private Double valorhorarecurso = Double.valueOf("0");

	private BigDecimal idestadoot = BigDecimal.valueOf(0);
	
	private String estadoot = "";

	private BigDecimal idempresa;

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanRrhhordenesdetrabajoFrm() {
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
			RRHH rrhhordenesdetrabajo = Common
					.getRrhh();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = rrhhordenesdetrabajo.rrhhordenesdetrabajoCreate(
						this.idcliente, this.descripcion, this.fechainicio,
						this.fechaprometida, this.fechafinal,
						this.horasestimadas, this.valorhoracliente,
						this.valorhorarecurso, this.idestadoot, this.idempresa,
						this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = rrhhordenesdetrabajo.rrhhordenesdetrabajoUpdate(
						this.idordendetrabajo, this.idcliente,
						this.descripcion, this.fechainicio,
						this.fechaprometida, this.fechafinal,
						this.horasestimadas, this.valorhoracliente,
						this.valorhorarecurso, this.idestadoot, this.idempresa,
						this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = rrhhordenesdetrabajo
						.rrhhordenesdetrabajoDelete(this.idordendetrabajo,this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosRrhhordenesdetrabajo() {
		try {
			RRHH rrhhordenesdetrabajo = Common.getRrhh();
			List listRrhhordenesdetrabajo = rrhhordenesdetrabajo
					.getRrhhordenesdetrabajoPK(this.idordendetrabajo,
							this.idempresa);
			Iterator iterRrhhordenesdetrabajo = listRrhhordenesdetrabajo
					.iterator();
			if (iterRrhhordenesdetrabajo.hasNext()) {
				String[] uCampos = (String[]) iterRrhhordenesdetrabajo.next();
				// TODO: Constructores para cada tipo de datos
				this.idordendetrabajo = BigDecimal.valueOf(Long
						.parseLong(uCampos[0]));
				this.idcliente = BigDecimal.valueOf(Long.parseLong(uCampos[1]));
				this.cliente= uCampos[2];
				this.descripcion = uCampos[3];
				this.fechainicio = Timestamp.valueOf(uCampos[4]);
				this.fechainicioStr = Common.setObjectToStrOrTime(fechainicio,
						"JSTsToStr").toString();
				this.fechaprometida = Timestamp.valueOf(uCampos[5]);
				this.fechaprometidaStr = Common.setObjectToStrOrTime(
						fechaprometida, "JSTsToStr").toString();
				this.fechafinal = Timestamp.valueOf(uCampos[6]);
				this.fechafinalStr = Common.setObjectToStrOrTime(fechafinal,
						"JSTsToStr").toString();
				this.horasestimadas = BigDecimal.valueOf(Long
						.parseLong(uCampos[7]));
				this.valorhoracliente = Double.valueOf(uCampos[8]);
				this.valorhorarecurso = Double.valueOf(uCampos[9]);
				this.idestadoot = BigDecimal
						.valueOf(Long.parseLong(uCampos[10]));
				this.estadoot= uCampos[11];
				this.idempresa = BigDecimal
						.valueOf(Long.parseLong(uCampos[12]));
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosRrhhordenesdetrabajo()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("rrhhordenesdetrabajoAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (idcliente == null) {
						this.mensaje = "No se puede dejar vacio el campo idcliente ";
						return false;
					}
					if (descripcion == null) {
						this.mensaje = "No se puede dejar vacio el campo descripcion ";
						return false;
					}
					if (fechainicio == null) {
						this.mensaje = "No se puede dejar vacio el campo fechainicio ";
						return false;
					}
					if (fechaprometida == null) {
						this.mensaje = "No se puede dejar vacio el campo fechaprometida ";
						return false;
					}
					if (fechafinal == null) {
						this.mensaje = "No se puede dejar vacio el campo fechafinal ";
						return false;
					}
					if (horasestimadas == null) {
						this.mensaje = "No se puede dejar vacio el campo horasestimadas ";
						return false;
					}
					if (valorhoracliente == null) {
						this.mensaje = "No se puede dejar vacio el campo valorhoracliente ";
						return false;
					}
					if (valorhorarecurso == null) {
						this.mensaje = "No se puede dejar vacio el campo valorhorarecurso ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (descripcion.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Descripcion  ";
						return false;
					}
					if (estadoot.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Estado ot  ";
						return false;
					}

				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosRrhhordenesdetrabajo();
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
	public BigDecimal getIdordendetrabajo() {
		return idordendetrabajo;
	}

	public void setIdordendetrabajo(BigDecimal idordendetrabajo) {
		this.idordendetrabajo = idordendetrabajo;
	}

	public BigDecimal getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(BigDecimal idcliente) {
		this.idcliente = idcliente;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Timestamp getFechainicio() {
		return fechainicio;
	}

	public void setFechainicioStr(String fechainicioStr) {
		this.fechainicioStr = fechainicioStr;
		this.fechainicio = (java.sql.Timestamp) Common.setObjectToStrOrTime(
				fechainicioStr, "StrToJSTs");
	}

	public Timestamp getFechaprometida() {
		return fechaprometida;
	}

	public void setFechaprometidaStr(String fechaprometidaStr) {
		this.fechaprometidaStr = fechaprometidaStr;
		this.fechaprometida = (java.sql.Timestamp) Common.setObjectToStrOrTime(
				fechaprometidaStr, "StrToJSTs");
	}

	public Timestamp getFechafinal() {
		return fechafinal;
	}

	public void setFechafinalStr(String fechafinalStr) {
		this.fechafinalStr = fechafinalStr;
		this.fechafinal = (java.sql.Timestamp) Common.setObjectToStrOrTime(
				fechafinalStr, "StrToJSTs");
	}

	public BigDecimal getHorasestimadas() {
		return horasestimadas;
	}

	public void setHorasestimadas(BigDecimal horasestimadas) {
		this.horasestimadas = horasestimadas;
	}

	public Double getValorhoracliente() {
		return valorhoracliente;
	}

	public void setValorhoracliente(Double valorhoracliente) {
		this.valorhoracliente = valorhoracliente;
	}

	public Double getValorhorarecurso() {
		return valorhorarecurso;
	}

	public void setValorhorarecurso(Double valorhorarecurso) {
		this.valorhorarecurso = valorhorarecurso;
	}

	public BigDecimal getIdestadoot() {
		return idestadoot;
	}

	public void setIdestadoot(BigDecimal idestadoot) {
		this.idestadoot = idestadoot;
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

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getEstadoot() {
		return estadoot;
	}

	public void setEstadoot(String estadoot) {
		this.estadoot = estadoot;
	}

	public String getFechafinalStr() {
		return fechafinalStr;
	}

	public String getFechainicioStr() {
		return fechainicioStr;
	}

	public String getFechaprometidaStr() {
		return fechaprometidaStr;
	}

	public void setFechafinal(Timestamp fechafinal) {
		this.fechafinal = fechafinal;
	}

	public void setFechainicio(Timestamp fechainicio) {
		this.fechainicio = fechainicio;
	}

	public void setFechaprometida(Timestamp fechaprometida) {
		this.fechaprometida = fechaprometida;
	}
}
