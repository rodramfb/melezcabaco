/* 
 javabean para la entidad (Formulario): rrhhothoras
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Mon Apr 07 15:54:04 ART 2008 
 
 Para manejar la pagina: rrhhothorasFrm.jsp
 
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

public class BeanRrhhothorasXusuarioFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanRrhhothorasXusuarioFrm.class);

	private String validar = "";

	private BigDecimal idothoras = BigDecimal.valueOf(-1);

	private BigDecimal idordendetrabajo = BigDecimal.valueOf(0);

	private String ordendetrabajo = "";

	private String detalle = "";

	private String fecha = Common.initObjectTimeStr();

	// private String fechaStr = Common.initObjectTimeStr();

	private Timestamp fechaentrada1 = null;

	private Timestamp fechasalida1 = null;

	private Timestamp fechaentrada2 = null;

	private Timestamp fechasalida2 = null;

	private BigDecimal idempresa;

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private String horaentrada1 = "";

	private String minutoentrada1 = "";

	private String horasalida1 = "";

	private String minutosalida1 = "";

	private String horaentrada2 = "";

	private String minutoentrada2 = "";

	private String horasalida2 = "";

	private String minutosalida2 = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanRrhhothorasXusuarioFrm() {
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
			RRHH rrhhothoras = Common.getRrhh();
			// this.fechaentrada1 =
			// rrhhothoras.SeteoFecha(this.fecha,this.horaentrada1,minutoentrada1);

			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = rrhhothoras.rrhhothorasCreate(
						this.idordendetrabajo, this.detalle, (Timestamp) Common
								.setObjectToStrOrTime(this.fecha, "StrToJSts"),
						this.fechaentrada1, this.fechasalida1,
						this.fechaentrada2, this.fechasalida2, this.idempresa,
						this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = rrhhothoras.rrhhothorasUpdate(this.idothoras,
						this.idordendetrabajo, this.detalle, (Timestamp) Common
								.setObjectToStrOrTime(this.fecha, "StrToJSts"),
						this.fechaentrada1, this.fechasalida1,
						this.fechaentrada2, this.fechasalida2, this.idempresa,
						this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = rrhhothoras.rrhhothorasDelete(this.idothoras,
						this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosRrhhothoras() {
		try {
			RRHH rrhhothoras = Common.getRrhh();
			List listRrhhothoras = rrhhothoras.getRrhhothorasPK(this.idothoras,
					this.idempresa);
			Iterator iterRrhhothoras = listRrhhothoras.iterator();
			if (iterRrhhothoras.hasNext()) {
				String[] uCampos = (String[]) iterRrhhothoras.next();
				// TODO: Constructores para cada tipo de datos
				this.idothoras = BigDecimal.valueOf(Long.parseLong(uCampos[0]));
				this.idordendetrabajo = BigDecimal.valueOf(Long
						.parseLong(uCampos[1]));
				this.ordendetrabajo = uCampos[2];
				this.detalle = uCampos[3];
				this.fecha = (String) Common.setObjectToStrOrTime(
						Timestamp.valueOf(uCampos[4]), "JSTsToStr");
			

				this.horaentrada1 = (String) Common.setObjectToStrOrTime(
						Timestamp.valueOf(uCampos[5]), "JSTsToStrOnlyHour");
				this.minutoentrada1 = (String) Common.setObjectToStrOrTime(
						Timestamp.valueOf(uCampos[5]), "JSTsToStrOnlyMin");

				this.horasalida1 = (String) Common.setObjectToStrOrTime(
						Timestamp.valueOf(uCampos[6]), "JSTsToStrOnlyHour");
				this.minutosalida1 = (String) Common.setObjectToStrOrTime(
						Timestamp.valueOf(uCampos[6]), "JSTsToStrOnlyMin");
				
				this.horaentrada2 = (String) Common.setObjectToStrOrTime(
						Timestamp.valueOf(uCampos[7]), "JSTsToStrOnlyHour");
				this.minutoentrada2 = (String) Common.setObjectToStrOrTime(
						Timestamp.valueOf(uCampos[7]), "JSTsToStrOnlyMin");

				this.horasalida2 = (String) Common.setObjectToStrOrTime(
						Timestamp.valueOf(uCampos[8]), "JSTsToStrOnlyHour");
				this.minutosalida2 = (String) Common.setObjectToStrOrTime(
						Timestamp.valueOf(uCampos[8]), "JSTsToStrOnlyMin");

				this.idempresa = BigDecimal.valueOf(Long.parseLong(uCampos[9]));
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosRrhhothoras()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("rrhhothorasxusuarioAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (idordendetrabajo == null) {
						this.mensaje = "No se puede dejar vacio el campo idordendetrabajo ";
						return false;
					}

					if (detalle.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Detalle ";
						return false;
					}

					// TODO: VALIDACION PROVISORIA !!!
					// ------------------------------------
					// -- UNO

					if (!this.isHoraValida(this.horaentrada1)) {
						this.mensaje = "Hora entrada 1 invalida !!";
						return false;
					}

					if (!this.isMinutoValido(this.minutoentrada1)) {
						this.mensaje = "Minuto entrada 1 invalida !!";
						return false;
					}

					if (!this.isHoraValida(this.horasalida1)) {
						this.mensaje = "Hora salida 1 invalida !!";
						return false;
					}

					if (!this.isMinutoValido(this.minutosalida1)) {
						this.mensaje = "Minuto salida 1 invalida !!";
						return false;
					}
					// --DOS

					if (!this.isHoraValida(this.horaentrada2)) {
						this.mensaje = "Hora entrada 2 invalida !!";
						return false;
					}

					if (!this.isMinutoValido(this.minutoentrada2)) {
						this.mensaje = "Minuto entrada 2 invalida !!";
						return false;
					}

					if (!this.isHoraValida(this.horasalida2)) {
						this.mensaje = "Hora salida 2 invalida !!";
						return false;
					}

					if (!this.isMinutoValido(this.minutosalida2)) {
						this.mensaje = "Minuto salida 2 invalida !!";
						return false;
					}

					// -------------------------------------------

					this.fechaentrada1 = this.setFecha(this.fecha,
							this.horaentrada1, this.minutoentrada1);
					this.fechasalida1 = this.setFecha(this.fecha,
							this.horasalida1, this.minutosalida1);

					this.fechaentrada2 = this.setFecha(this.fecha,
							this.horaentrada2, this.minutoentrada2);
					this.fechasalida2 = this.setFecha(this.fecha,
							this.horasalida2, this.minutosalida2);

					if (this.fechaentrada1.after(fechasalida1)) {
						this.mensaje = "Hora entrada 1 debe ser menor a hora salida 1.";
						return false;
					}

					if (this.fechaentrada2.before(fechasalida1)) {
						this.mensaje = "Hora entrada 2 debe ser mayor a hora salida 1.";
						return false;
					}

					if (this.fechaentrada2.after(fechasalida2)) {
						this.mensaje = "Hora entrada 2 debe ser menor a hora salida 2.";
						return false;
					}

					// 2. len 0 para campos nulos
				}

				this.ejecutarSentenciaDML();

			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosRrhhothoras();
				}
			}
		} catch (Exception e) {
			log.error(" ejecutarValidacion(): " + e);
		}
		return true;
	}

	private Timestamp setFecha(String fecha, String hora, String minuto) {

		Timestamp ts = null;
		String fhm = fecha + " " + hora + ":" + minuto;

		try {

			ts = (Timestamp) Common
					.setObjectToStrOrTime(fhm, "strToJSTsWithHM");

		} catch (Exception e) {
			log.error("setFecha(...):" + e);
		}

		return ts;
	}

	private boolean isMinutoValido(String minuto) {

		boolean retorno = true;

		try {

			if (!Common.esEntero(minuto)) {
				retorno = false;
			} else if (!Common.isValorEnRango(Long.parseLong(minuto), 0L, 59L)) {
				retorno = false;
			}

		} catch (Exception e) {
			log.error("isMinutoValido: " + e);
		}

		return retorno;
	}

	private boolean isHoraValida(String hora) {

		boolean retorno = true;

		try {

			if (!Common.esEntero(hora)) {
				retorno = false;
			} else if (!Common.isValorEnRango(Long.parseLong(hora), 0L, 23L)) {
				retorno = false;
			}

		} catch (Exception e) {
			log.error("isHoraValida: " + e);
		}

		return retorno;
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
	public BigDecimal getIdothoras() {
		return idothoras;
	}

	public void setIdothoras(BigDecimal idothoras) {
		this.idothoras = idothoras;
	}

	public BigDecimal getIdordendetrabajo() {
		return idordendetrabajo;
	}

	public void setIdordendetrabajo(BigDecimal idordendetrabajo) {
		this.idordendetrabajo = idordendetrabajo;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Timestamp getFechaentrada1() {
		return fechaentrada1;
	}

	public void setFechaentrada1(Timestamp fechaentrada1) {
		this.fechaentrada1 = fechaentrada1;
	}

	public Timestamp getFechasalida1() {
		return fechasalida1;
	}

	public void setFechasalida1(Timestamp fechasalida1) {
		this.fechasalida1 = fechasalida1;
	}

	public Timestamp getFechaentrada2() {
		return fechaentrada2;
	}

	public void setFechaentrada2(Timestamp fechaentrada2) {
		this.fechaentrada2 = fechaentrada2;
	}

	public Timestamp getFechasalida2() {
		return fechasalida2;
	}

	public void setFechasalida2(Timestamp fechasalida2) {
		this.fechasalida2 = fechasalida2;
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

	public String getOrdendetrabajo() {
		return ordendetrabajo;
	}

	public void setOrdendetrabajo(String ordendetrabajo) {
		this.ordendetrabajo = ordendetrabajo;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public String getHoraentrada1() {
		return horaentrada1;
	}

	public void setHoraentrada1(String horaentrada1) {
		this.horaentrada1 = horaentrada1;
	}

	public String getHoraentrada2() {
		return horaentrada2;
	}

	public void setHoraentrada2(String horaentrada2) {
		this.horaentrada2 = horaentrada2;
	}

	public String getHorasalida1() {
		return horasalida1;
	}

	public void setHorasalida1(String horasalida1) {
		this.horasalida1 = horasalida1;
	}

	public String getHorasalida2() {
		return horasalida2;
	}

	public void setHorasalida2(String horasalida2) {
		this.horasalida2 = horasalida2;
	}

	public String getMinutoentrada1() {
		return minutoentrada1;
	}

	public void setMinutoentrada1(String minutoentrada1) {
		this.minutoentrada1 = minutoentrada1;
	}

	public String getMinutoentrada2() {
		return minutoentrada2;
	}

	public void setMinutoentrada2(String minutoentrada2) {
		this.minutoentrada2 = minutoentrada2;
	}

	public String getMinutosalida1() {
		return minutosalida1;
	}

	public void setMinutosalida1(String minutosalida1) {
		this.minutosalida1 = minutosalida1;
	}

	public String getMinutosalida2() {
		return minutosalida2;
	}

	public void setMinutosalida2(String minutosalida2) {
		this.minutosalida2 = minutosalida2;
	}
}
