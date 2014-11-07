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

public class BeanContableAsietip1Frm implements SessionBean, Serializable {

	private SessionContext context;

	private BigDecimal ejercicio = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanContableAsietip1Frm.class);

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

	private Hashtable htDetalle = new Hashtable();

	private String[] delKey = null;

	public BeanContableAsietip1Frm() {
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
			Contable contableAsietip1 = Common.getContable();
			if (!this.accion.equalsIgnoreCase("baja")) {
				this.mensaje = contableAsietip1.contableAsientosTipoCreate(
						this.codigo, this.leyenda, this.htDetalle,
						this.idempresa, this.usuarioalt);

				if (this.mensaje.equalsIgnoreCase("OK")) {
					this.mensaje = "Asiento tipo generado correctmente.";

				}
			}

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosContableAsietip1() {
		try {
			Contable contableAsietip1 = Common.getContable();
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

					while (iterContableAsietip1.hasNext()) {

						uCampos = (String[]) iterContableAsietip1.next();

						this.htDetalle.put(uCampos[1], new String[] {
								uCampos[1], uCampos[2], uCampos[3], uCampos[4],
								uCampos[5] });

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
				response.sendRedirect("contableAsietip1Abm.jsp");
				return true;
			}

			if (this.primeraCarga) {

				this.request.getSession().removeAttribute("htDetalle");
				this.request.getSession().setAttribute("htDetalle",
						this.htDetalle);

				if (this.accion.equalsIgnoreCase("modificacion")) {

					getDatosContableAsietip1();

				}

			} else {

				this.htDetalle = (Hashtable) request.getSession().getAttribute(
						"htDetalle");

				if (!this.agregar.equals("")) {

					if (this.idcuenta.equals(""))
						this.mensaje = "Es necesario seleccionar Cuenta.";
					else if (this.detalle.trim().equals(""))
						this.mensaje = "Es necesario ingresar Detalle Libro Mayor";
					else {

						this.htDetalle.put(this.idcuenta, new String[] {
								this.idcuenta, this.cuenta, this.detalle,
								this.cc1.toString(), this.cc2.toString() });

					}

				}

				if (!this.eliminar.equals("")) {

					if (this.delKey == null) {
						this.mensaje = "Seleccione un registro a eliminar.";
					} else {
						for (int i = 0; i < this.delKey.length; i++)
							this.htDetalle.remove(this.delKey[i]);
					}

				}

				request.getSession().setAttribute("htDetalle", this.htDetalle);

			}

			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {

					if (this.leyenda.trim().equalsIgnoreCase("")) {
						this.mensaje = "Ingrese Detalle de Libro Diario.";
						return false;
					}

					if (this.htDetalle == null || this.htDetalle.isEmpty()) {
						this.mensaje = "Ingrese detalle del asiento";
						return false;
					}

					if (this.htDetalle.size() < 2) {
						this.mensaje = "Detalle de tener al menos dos registros.";
						return false;
					}

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

	public Hashtable getHtDetalle() {
		return htDetalle;
	}

	public String[] getDelKey() {
		return delKey;
	}

	public void setDelKey(String[] delKey) {
		this.delKey = delKey;
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
}
