/* 
   javabean para la entidad (Formulario): contableLeyendas
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Sep 10 11:42:24 GMT-03:00 2008 
   
   Para manejar la pagina: contableLeyendasFrm.jsp
      
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

public class BeanContableAsientosFrm implements SessionBean, Serializable {
	private SessionContext context;
	static Logger log = Logger.getLogger(BeanContableAsientosFrm.class);

	private String validar = "";

	private String fecha = "";

	private java.sql.Timestamp fechaasiento = null;

	private BigDecimal idasiento = new BigDecimal(-1);

	private BigDecimal ejercicio = new BigDecimal(-1);

	private String leyenda = "";

	private BigDecimal nroasiento = new BigDecimal(-1);

	private String tipo_asiento = "";

	private BigDecimal asiento_nuevo = new BigDecimal(-1);

	private BigDecimal idempresa = new BigDecimal(-1);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private java.sql.Timestamp fechaEjercicioActivoDesde = null;

	private java.sql.Timestamp fechaEjercicioActivoHasta = null;

	private boolean primeraCarga = true;

	private Hashtable htAsientos = new Hashtable();

	private String agregar = "";

	// REGISTRO

	private String idcuenta = "";

	private String cuenta = "";

	private String detalle = "";

	private String importe = "";

	private BigDecimal tipomov = new BigDecimal(-1);

	//

	private String[] delKey = null;

	private String eliminar = "";

	private BigDecimal totaldebe = new BigDecimal(0);

	private BigDecimal totalhaber = new BigDecimal(0);

	public BeanContableAsientosFrm() {
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
			Contable contableLeyendas = Common.getContable();

			if (this.accion.equalsIgnoreCase("alta")) {
				this.mensaje = contableLeyendas.contableGenerarAsiento(
						this.idasiento, this.ejercicio, this.fechaasiento,
						this.leyenda, this.tipo_asiento, this.asiento_nuevo,
						this.htAsientos, this.usuarioalt, this.idempresa);

				if (Common.esNumerico(this.mensaje)) {
					this.mensaje = "Se generó el asiento nro.: " + this.mensaje;
					this.htAsientos = new Hashtable();
					reiniciarHt();
				}

			} else if (this.accion.equalsIgnoreCase("modificacion")) {

				this.mensaje = contableLeyendas.contableActualizarAsiento(
						this.idasiento, this.ejercicio, this.fechaasiento,
						this.leyenda, this.tipo_asiento, this.asiento_nuevo,
						this.htAsientos, this.usuarioalt, this.idempresa);

				if (Common.esNumerico(this.mensaje)) {
					this.mensaje = "Asiento nro.: " + this.mensaje
							+ " modificado correctamente.";
				}
			}
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosContableAsientos() {
		try {
			Calendar calendar = Calendar.getInstance();
			Contable contable = Common.getContable();
			List listContableAsientos = contable.getContableAsientosPK(
					this.idasiento, this.ejercicio, this.idempresa);
			Iterator iterContableAsientos = listContableAsientos.iterator();
			if (iterContableAsientos.hasNext()) {
				String[] uCampos = (String[]) iterContableAsientos.next();
				// TODO: Constructores para cada tipo de datos

				this.idasiento = new BigDecimal(uCampos[0]);
				this.ejercicio = new BigDecimal(uCampos[1]);
				this.fecha = Common.setObjectToStrOrTime(
						java.sql.Timestamp.valueOf(uCampos[2]), "JSTsToStr")
						.toString();
				this.leyenda = uCampos[3];
				this.nroasiento = new BigDecimal(uCampos[4]);
				this.tipo_asiento = uCampos[5];
				this.asiento_nuevo = new BigDecimal(uCampos[6]);

				listContableAsientos = contable.getContableInfimovPK(
						this.idasiento, this.ejercicio, this.idempresa);
				iterContableAsientos = listContableAsientos.iterator();

				if (iterContableAsientos.hasNext()) {

					while (iterContableAsientos.hasNext()) {
						calendar.set(Calendar.MILLISECOND, Integer
								.parseInt(uCampos[1]));
						uCampos = (String[]) iterContableAsientos.next();
						// im.idasiento,im.renglon,ip.idcuenta,ip.cuenta,im.tipomov,
						// im.importe,im.detalle,im.asie_nue,im.cent_cost,im.cent_cost1

						String importe_asiento = new BigDecimal(uCampos[5])
								.setScale(2, BigDecimal.ROUND_FLOOR).toString();

						this.htAsientos
								.put(
										calendar.getTimeInMillis() + "",
										new String[] {
												uCampos[2],
												uCampos[3],
												uCampos[6],
												importe_asiento,
												uCampos[4],
												uCampos[4].equals("1") ? importe_asiento
														: "0",
												uCampos[4].equals("2") ? importe_asiento
														: "0" });

					}

				} else {
					this.mensaje = "Imposible recuperar datos detalle para el asiento seleccionado.";
				}

			} else {
				this.mensaje = "Imposible recuperar datos para el asiento seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosContableLeyendas()" + e);
		}
	}

	private void reiniciarHt() {
		request.getSession().removeAttribute("htAsientos");
		request.getSession().setAttribute("htAsientos", this.htAsientos);

	}

	public boolean ejecutarValidacion() {
		try {

			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("contableAsientosAbm.jsp");
				return true;
			}

			if (isPrimeraCarga()) {

				if (this.accion.equalsIgnoreCase("modificacion")) {
					// TODO: llenar HT con datos del asiento.
					getDatosContableAsientos();
				}

				this.reiniciarHt();

			} else {

				this.htAsientos = (Hashtable) request.getSession()
						.getAttribute("htAsientos");

				if (!this.eliminar.equals("")) {
					if (this.delKey == null) {
						this.mensaje = "Seleccione un artículo a eliminar.";
					} else {
						for (int i = 0; i < this.delKey.length; i++) {
							htAsientos.remove(this.delKey[i]);
						}
					}
				}

				if (!this.agregar.equals("")) {

					if (this.idcuenta.equals("")) {

						this.mensaje = "Ingrese nro. cuenta valida.";

					} else if (this.detalle.equals("")) {

						this.mensaje = "Ingrese detalle libro mayor.";

					} else if (!Common.esNumerico(this.importe)) {

						this.mensaje = "Ingrese importe valido.";

					} else if (this.tipomov.intValue() < 1) {

						this.mensaje = "Seleccione tipo de registro (Debe / Haber).";

					} else {

						this.importe = new BigDecimal(this.importe).setScale(2,
								BigDecimal.ROUND_FLOOR).toString();
						this.htAsientos.put(Calendar.getInstance()
								.getTimeInMillis()
								+ "", new String[] {
								this.idcuenta,
								this.cuenta,
								this.detalle,
								this.importe,
								this.tipomov.toString(),
								this.tipomov.intValue() == 1 ? this.importe
										: "0",
								this.tipomov.intValue() == 2 ? this.importe
										: "0" });

					}

				}

			}

			this.totaldebe = Common.getTotalHTArrayByIndex(this.htAsientos, 5);
			this.totalhaber = Common.getTotalHTArrayByIndex(this.htAsientos, 6);

			if (!this.validar.equalsIgnoreCase("")) {

				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos

					if (!Common.isFormatoFecha(this.fecha)
							|| !Common.isFechaValida(fecha)) {
						this.mensaje = "Ingrese fecha válida. ";
						return false;
					}

					this.fechaasiento = (java.sql.Timestamp) Common
							.setObjectToStrOrTime(this.fecha, "StrToJSTs");

					if (fechaasiento.before(this.fechaEjercicioActivoDesde)
							|| fechaasiento
									.after(this.fechaEjercicioActivoHasta)) {
						this.mensaje = "Fecha debe pertenecer al periodo:"
								+ Common.setObjectToStrOrTime(
										this.fechaEjercicioActivoDesde,
										"JSTsToStr")
								+ " - "
								+ Common.setObjectToStrOrTime(
										this.fechaEjercicioActivoHasta,
										"JSTsToStr");
						return false;
					}

					if (this.leyenda.trim().equals("")) {
						this.mensaje = "Ingrese detalle libro diario. ";
						return false;
					}

					if (this.htAsientos == null || this.htAsientos.isEmpty()) {
						this.mensaje = "Ingrese registros del asiento. ";
						return false;
					}

					if (!isDebeHaber(this.htAsientos)) {
						this.mensaje = "Asiento mal conformado. Es necesario ingresar Debe y Haber.";
						return false;
					}

					if (this.totaldebe.compareTo(this.totalhaber) != 0) {
						this.mensaje = "El asiento no se encuentra balanceado.";
						return false;
					}

					// 2. len 0 para campos nulos
				}

				this.ejecutarSentenciaDML();

			}

		} catch (Exception e) {
			log.error(" ejecutarValidacion(): " + e);
		}
		return true;
	}

	private boolean isDebeHaber(Hashtable ht) {
		boolean isdebe = false;
		boolean ishaber = false;
		boolean isdebehaber = false;
		Enumeration en;

		try {

			en = ht.keys();
			while (en.hasMoreElements()) {
				String[] datos = (String[]) ht.get(en.nextElement());
				if (datos[4].equals("1"))
					isdebe = true;
				if (datos[4].equals("2"))
					ishaber = true;
				if (isdebe && ishaber) {
					isdebehaber = true;
					break;
				}
			}

		} catch (Exception e) {
			log.error("isDebeHaber: " + e);
		}

		return isdebehaber;
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
	public BigDecimal getIdasiento() {
		return idasiento;
	}

	public void setIdasiento(BigDecimal idasiento) {
		this.idasiento = idasiento;
	}

	public BigDecimal getEjercicio() {
		return ejercicio;
	}

	public void setEjercicio(BigDecimal ejercicio) {
		this.ejercicio = ejercicio;
	}

	public String getLeyenda() {
		return leyenda;
	}

	public void setLeyenda(String leyenda) {
		this.leyenda = leyenda;
	}

	public BigDecimal getNroasiento() {
		return nroasiento;
	}

	public void setNroasiento(BigDecimal nroasiento) {
		this.nroasiento = nroasiento;
	}

	public String getTipo_asiento() {
		return tipo_asiento;
	}

	public void setTipo_asiento(String tipo_asiento) {
		this.tipo_asiento = tipo_asiento;
	}

	public BigDecimal getAsiento_nuevo() {
		return asiento_nuevo;
	}

	public void setAsiento_nuevo(BigDecimal asiento_nuevo) {
		this.asiento_nuevo = asiento_nuevo;
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

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public java.sql.Timestamp getFechaEjercicioActivoDesde() {
		return fechaEjercicioActivoDesde;
	}

	public void setFechaEjercicioActivoDesde(
			java.sql.Timestamp fechaEjercicioActivoDesde) {
		this.fechaEjercicioActivoDesde = fechaEjercicioActivoDesde;
	}

	public java.sql.Timestamp getFechaEjercicioActivoHasta() {
		return fechaEjercicioActivoHasta;
	}

	public void setFechaEjercicioActivoHasta(
			java.sql.Timestamp fechaEjercicioActivoHasta) {
		this.fechaEjercicioActivoHasta = fechaEjercicioActivoHasta;
	}

	public boolean isPrimeraCarga() {
		return primeraCarga;
	}

	public void setPrimeraCarga(boolean primeraCarga) {
		this.primeraCarga = primeraCarga;
	}

	public Hashtable getHtAsientos() {
		return htAsientos;
	}

	public String getAgregar() {
		return agregar;
	}

	public void setAgregar(String agregar) {
		this.agregar = agregar;
	}

	public String getIdcuenta() {
		return idcuenta;
	}

	public void setIdcuenta(String idcuenta) {
		this.idcuenta = idcuenta;
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public String getImporte() {
		return importe;
	}

	public void setImporte(String importe) {
		this.importe = importe;
	}

	public BigDecimal getTipomov() {
		return tipomov;
	}

	public void setTipomov(BigDecimal tipomov) {
		this.tipomov = tipomov;
	}

	public String[] getDelKey() {
		return delKey;
	}

	public void setDelKey(String[] delKey) {
		this.delKey = delKey;
	}

	public String getEliminar() {
		return eliminar;
	}

	public void setEliminar(String eliminar) {
		this.eliminar = eliminar;
	}

	public BigDecimal getTotaldebe() {
		return totaldebe;
	}

	public void setTotaldebe(BigDecimal totaldebe) {
		this.totaldebe = totaldebe;
	}

	public BigDecimal getTotalhaber() {
		return totalhaber;
	}

	public void setTotalhaber(BigDecimal totalhaber) {
		this.totalhaber = totalhaber;
	}
}
