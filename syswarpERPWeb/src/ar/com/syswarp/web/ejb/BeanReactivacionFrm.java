/* 
 javabean para la entidad (Formulario): clientesvendedor
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Nov 14 15:40:40 GMT-03:00 2006 
 
 Para manejar la pagina: clientesvendedorFrm.jsp
 
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

public class BeanReactivacionFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanReactivacionFrm.class);

	private String validar = "";

	private BigDecimal idempresa;

	private BigDecimal idestado;

	private BigDecimal idmotivo;

	private String observaciones;

	private BigDecimal porcentaje = BigDecimal.valueOf(0);

	private BigDecimal idcliente = BigDecimal.valueOf(0);

	private Timestamp fechareactivacion = new Timestamp(Common.initObjectTime());

	private String fechareactivacionStr = Common.initObjectTimeStr();

	private BigDecimal idvendedor = BigDecimal.valueOf(0);

	private BigDecimal idestadoreactivaciones = BigDecimal.valueOf(0);

	private String estadoreactivaciones = "";

	private String vendedor = "";

	private String usuarioalt;

	private String razon;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	// BigDecimal primero = new BigDecimal(1);

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanReactivacionFrm() {
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
			General clienteestado = Common.getGeneral();
			// this.mensaje = clienteestado.EstadoActualInsert(this.idcliente,
			// this.idestado, this.idmotivo, this.observaciones,
			// this.usuarioalt, this.idempresa);

			// if (this.mensaje.equalsIgnoreCase("OK")) {
			log
					.info("ejecuto el metodo EstadoActualinserto......................");
			this.mensaje = clienteestado.EstadoActualinserto(this.idcliente,
					this.porcentaje, this.usuarioalt, this.idempresa,
					this.fechareactivacion, this.idvendedor,
					this.idestadoreactivaciones,
					// 20110912 - EJV - Mantis 789 - Aparentemente este BEAN no
					// se utiliza, solo agrego idpromocion con valor NULL para
					// que compile sin errores. -- >
					null
			// <--
					);
			this.mensaje = "Cliente Impactado correctamente!";
			response.sendRedirect("consestadoactualAbm.jsp");
			// }

			// if (this.mensaje.equalsIgnoreCase("OK")) {
			// this.mensaje = clienteestado.EstadoActualUpdate(this.idcliente,
			// this.idempresa);
			// }
			// if (this.mensaje.equalsIgnoreCase("OK")) {
			// this.mensaje =
			// clienteestado.globalUpdateVendedor(this.idcliente,this.idvendedor,this.idempresa);
			// this.mensaje = "Cliente Impactado correctamente!";
			// yaimpacto = "1";
			// }

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosPK() {
		try {
			General clienteestado = Common.getGeneral();
			List listpk = clienteestado.globalUpdatePK(this.idcliente,
					this.idempresa);
			Iterator iterContableajuste = listpk.iterator();
			if (iterContableajuste.hasNext()) {
				String[] uCampos = (String[]) iterContableajuste.next();
				// TODO: Constructores para cada tipo de datos
				this.idvendedor = new BigDecimal(uCampos[0]);
				this.vendedor = uCampos[1];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosPK()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (this.validar.equalsIgnoreCase("")) {
				getDatosPK();
			}
			General clienteestado = Common.getGeneral();
			razon = clienteestado.DescripcionClientexNumero(this.idcliente,
					this.idempresa);
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("consestadoactualAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (this.porcentaje.intValue() < 1) {
					this.mensaje = "Debe seleccionar un porcentaje.";
					return false;
				}
				if (vendedor.trim().length() == 0) {
					this.mensaje = "No se puede dejar vacio el campo Vendedor";
					return false;
				}

				if (estadoreactivaciones.trim().length() == 0) {
					this.mensaje = "No se puede dejar vacio el campo Estado Reactivacion";
					return false;
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

	public BigDecimal getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}

	public BigDecimal getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(BigDecimal idcliente) {
		this.idcliente = idcliente;
	}

	public String getRazon() {
		return razon;
	}

	public void setRazon(String razon) {
		this.razon = razon;
	}

	public String getFechareactivacionStr() {
		return fechareactivacionStr;
	}

	public void setFechareactivacionStr(String fechareactivacionStr) {
		this.fechareactivacionStr = fechareactivacionStr;
		this.fechareactivacion = (java.sql.Timestamp) Common
				.setObjectToStrOrTime(fechareactivacionStr, "StrToJSTs");
	}

	public BigDecimal getIdvendedor() {
		return idvendedor;
	}

	public void setIdvendedor(BigDecimal idvendedor) {
		this.idvendedor = idvendedor;
	}

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

	public BigDecimal getIdestadoreactivaciones() {
		return idestadoreactivaciones;
	}

	public void setIdestadoreactivaciones(BigDecimal idestadoreactivaciones) {
		this.idestadoreactivaciones = idestadoreactivaciones;
	}

	public String getEstadoreactivaciones() {
		return estadoreactivaciones;
	}

	public void setEstadoreactivaciones(String estadoreactivaciones) {
		this.estadoreactivaciones = estadoreactivaciones;
	}

}
