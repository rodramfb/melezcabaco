/* 
   javabean para la entidad (Formulario): contableAsietip1
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Sep 15 14:10:45 GMT-03:00 2008 
   
   Para manejar la pagina: contableAsietip1Frm.jsp
      
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

public class BeanLovContableAsietip1SetValues implements SessionBean,
		Serializable {

	private SessionContext context;

	private BigDecimal ejercicio = new BigDecimal(-1);

	static Logger log = Logger
			.getLogger(BeanLovContableAsietip1SetValues.class);

	private String validar = "";

	private BigDecimal codigo = new BigDecimal(-1);

	private String leyenda = "";

	private String idcuenta = "";

	private String cuenta = "";

	private BigDecimal cc1 = new BigDecimal(0);

	private BigDecimal cc2 = new BigDecimal(0);

	private String detalle = "";

	private BigDecimal idempresa = new BigDecimal(-1);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private String agregar = "";

	private String eliminar = "";

	private boolean primeraCarga = true;

	private Hashtable htPredefinidos = new Hashtable();

	private String[] importe = null;

	private String[] tipomov = null;

	private String[] keyHtPredefinidos = null;

	private BigDecimal totaldebe = new BigDecimal(0.00);

	private BigDecimal totalhaber = new BigDecimal(0.00);

	public BeanLovContableAsietip1SetValues() {
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

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosContableAsietip1() {
		try {
			Contable contableAsietip1 = Common.getContable();
			Calendar calendar = Calendar.getInstance();
			List listContableAsietip1 = contableAsietip1.getContableAsietip1PK(
					this.codigo, this.idempresa);
			Iterator iterContableAsietip1 = listContableAsietip1.iterator();
			if (iterContableAsietip1.hasNext()) {
				String[] uCampos = (String[]) iterContableAsietip1.next();
				// TODO: Constructores para cada tipo de datos

				this.codigo = new BigDecimal(uCampos[0]);
				this.leyenda = uCampos[1];

				listContableAsietip1 = contableAsietip1.getContableAsietip2PK(
						this.codigo, this.ejercicio, this.idempresa);

				iterContableAsietip1 = listContableAsietip1.iterator();

				if (iterContableAsietip1.hasNext()) {

					int j = 0;
					while (iterContableAsietip1.hasNext()) {

						calendar.set(Calendar.MILLISECOND, j);

						uCampos = (String[]) iterContableAsietip1.next();

						// a2.codigo,ip.idcuenta,ip.cuenta,a2.detalle,a2.cent_cost,a2.cent_cost1

						this.htPredefinidos.put(
								calendar.getTimeInMillis() + "", new String[] {
										uCampos[1], uCampos[2], uCampos[3],
										"0.00", "0.00", "0.00", "0.00" });

						j++;
					}

				} else {

					this.mensaje = "Imposible recuperar datos para detalle asiento tipo seleccionado.";

				}

			} else {
				this.mensaje = "Imposible recuperar datos para asiento tipo seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosContableAsietip1()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {

			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("lov_contableAsietip1.jsp");
				return true;
			}

			if (this.primeraCarga) {

				this.request.getSession().removeAttribute("htPredefinidos");
				this.request.getSession().setAttribute("htPredefinidos",
						this.htPredefinidos);

				this.getDatosContableAsietip1();

			} else {

				this.htPredefinidos = (Hashtable) request.getSession()
						.getAttribute("htPredefinidos");

				for (int f = 0; f < this.keyHtPredefinidos.length; f++) {

					String[] datos = (String[]) this.htPredefinidos
							.get(this.keyHtPredefinidos[f]);

					if (!Common.esNumerico(this.importe[f]))
						this.importe[f] = "0";

					this.importe[f] = new BigDecimal(this.importe[f]).setScale(
							2, BigDecimal.ROUND_FLOOR).toString();
					this.htPredefinidos
							.put(
									this.keyHtPredefinidos[f],
									new String[] {
											datos[0],
											datos[1],
											datos[2],
											this.importe[f],
											this.tipomov[f].toString(),
											this.tipomov[f].equals("1") ? this.importe[f]
													: "0",
											this.tipomov[f].equals("2") ? this.importe[f]
													: "0" });
				}

				request.getSession().setAttribute("htPredefinidos",
						this.htPredefinidos);

			}

			if (!this.validar.equalsIgnoreCase("")) {

				if (this.leyenda.trim().equalsIgnoreCase("")) {
					this.mensaje = "Ingrese Detalle de Libro Diario.";
					return false;
				}

				if (this.htPredefinidos == null
						|| this.htPredefinidos.isEmpty()) {
					this.mensaje = "Ingrese detalle del asiento";
					return false;
				}

				if (this.htPredefinidos.size() < 2) {
					this.mensaje = "Detalle de tener al menos dos registros.";
					return false;
				}

				if (!this.isDebeHaber(this.htPredefinidos)) {
					this.mensaje = "Asiento mal conformado. Es necesario ingresar Debe y Haber.";
					return false;
				}

				this.totaldebe = Common.getTotalHTArrayByIndex(
						this.htPredefinidos, 5);
				this.totalhaber = Common.getTotalHTArrayByIndex(
						this.htPredefinidos, 6);

				if (this.totaldebe.compareTo(this.totalhaber) != 0) {
					this.mensaje = "El asiento no se encuentra balanceado.";
					return false;
				}

				Hashtable ht = (Hashtable) request.getSession().getAttribute(
						"htAsientos");
				Common.htRellenar(this.htPredefinidos, ht);
				request.getSession().setAttribute("htAsientos", ht);
				request.getSession().removeAttribute("htPredefinidos");

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
	public BigDecimal getCodigo() {
		return codigo;
	}

	public void setCodigo(BigDecimal codigo) {
		this.codigo = codigo;
	}

	public String getLeyenda() {
		return leyenda;
	}

	public void setLeyenda(String leyenda) {
		this.leyenda = leyenda;
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

	public BigDecimal getCc1() {
		return cc1;
	}

	public void setCc1(BigDecimal cc1) {
		this.cc1 = cc1;
	}

	public BigDecimal getCc2() {
		return cc2;
	}

	public void setCc2(BigDecimal cc2) {
		this.cc2 = cc2;
	}

	public String getAgregar() {
		return agregar;
	}

	public void setAgregar(String agregar) {
		this.agregar = agregar;
	}

	public String getEliminar() {
		return eliminar;
	}

	public void setEliminar(String eliminar) {
		this.eliminar = eliminar;
	}

	public boolean isPrimeraCarga() {
		return primeraCarga;
	}

	public void setPrimeraCarga(boolean primeraCarga) {
		this.primeraCarga = primeraCarga;
	}

	public Hashtable getHtPredefinidos() {
		return htPredefinidos;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public BigDecimal getEjercicio() {
		return ejercicio;
	}

	public void setEjercicio(BigDecimal ejercicio) {
		this.ejercicio = ejercicio;
	}

	public String[] getImporte() {
		return importe;
	}

	public void setImporte(String[] importe) {
		this.importe = importe;
	}

	public String[] getTipomov() {
		return tipomov;
	}

	public void setTipomov(String[] tipomov) {
		this.tipomov = tipomov;
	}

	public String[] getKeyHtPredefinidos() {
		return keyHtPredefinidos;
	}

	public void setKeyHtPredefinidos(String[] keyHtPredefinidos) {
		this.keyHtPredefinidos = keyHtPredefinidos;
	}
}
