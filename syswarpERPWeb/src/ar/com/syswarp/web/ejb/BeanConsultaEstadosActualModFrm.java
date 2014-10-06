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

public class BeanConsultaEstadosActualModFrm implements SessionBean,
		Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanConsultaEstadosActualModFrm.class);

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

	private BigDecimal idreactivacion = new BigDecimal(0);

	private String vendedor = "";

	private String usuarioalt;

	private String razon;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	// BigDecimal primero = new BigDecimal(1);

	private BigDecimal idtipoclie = new BigDecimal(-1);

	private List listClie = new ArrayList();

	private List listPromoActiva = new ArrayList();

	private BigDecimal idpromocion = new BigDecimal(-1);

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanConsultaEstadosActualModFrm() {
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
			log.info("ENTROOOO A EstadoActualinserto...................");
			this.mensaje = clienteestado.EstadoActualinserto(this.idcliente,
					this.porcentaje, this.usuarioalt, this.idempresa,
					this.fechareactivacion, this.idvendedor,
					this.idestadoreactivaciones, this.idpromocion);
			this.mensaje = "Cliente Impactado correctamente!";
			response.sendRedirect("consestadoactualModAbm.jsp");

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosPK() {
		try {
			General clienteestado = Common.getGeneral();
			List listpk = clienteestado.globalUpdatePKMod(this.idreactivacion,
					this.idempresa);
			Iterator iterContableajuste = listpk.iterator();
			if (iterContableajuste.hasNext()) {
				String[] uCampos = (String[]) iterContableajuste.next();
				// TODO: Constructores para cada tipo de datos
				this.idreactivacion = new BigDecimal(uCampos[0]);
				this.porcentaje = new BigDecimal(uCampos[1]);
				this.fechareactivacion = Timestamp.valueOf(uCampos[2]);
				this.fechareactivacionStr = Common.setObjectToStrOrTime(
						fechareactivacion, "JSTsToStr").toString();
				this.idvendedor = new BigDecimal(uCampos[3]);
				this.vendedor = uCampos[4];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosPK()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {

			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("consestadoactualModAbm.jsp");
				return true;
			}

			if (this.validar.equalsIgnoreCase("")) {
				getDatosPK();
			}

			// General clienteestado = Common.getGeneral();
			// razon = clienteestado.DescripcionClientexNumero(this.idcliente,
			// this.idempresa);

			this.listClie = Common.getClientes().getClientesClientesPK(
					this.idcliente, this.idempresa);

			Iterator it = this.listClie.iterator();

			if (it.hasNext()) {

				String[] datos = (String[]) it.next();
				this.razon = datos[1];
				this.idtipoclie = new BigDecimal(datos[18]);

			}

			this.listPromoActiva = Common.getClientes()
					.getPromocionesActivasXTipoClie(this.idtipoclie,
							this.idempresa);

			if (!this.validar.equalsIgnoreCase("")) {

				if (!Common.esPorcentaje(this.porcentaje + "")) {
					this.mensaje = "Debe seleccionar un porcentaje.";
					return false;
				}

				if (Common.setNotNull(this.vendedor).equals("")) {
					this.mensaje = "No se puede dejar vacio el campo Vendedor";
					return false;
				}

				if (!Common.isFormatoFecha(this.fechareactivacionStr)
						|| !Common.isFechaValida(this.fechareactivacionStr)) {
					this.mensaje = "Ingrese fecha de reactivacion.";
					return false;
				}

				this.fechareactivacion = (java.sql.Timestamp) Common
						.setObjectToStrOrTime(fechareactivacionStr, "StrToJSTs");

				java.sql.Date fechaVerificaPromo = new java.sql.Date(
						this.fechareactivacion.getTime());
				int existePromo = Common.getClientes()
						.getExistePromocionActivaEnPeriodo(null,
								this.idtipoclie, fechaVerificaPromo,
								fechaVerificaPromo, this.idempresa);
				if (existePromo < 0) {
					this.mensaje = "No fue posible verificar la existencia de promociones para el tipo de cliente seleccionado";
					return false;
				} else if (existePromo > 0 && this.idpromocion.intValue() < 1) {
					this.mensaje = "Existen promociones activas para el tipo de cliente, es necesario seleccionar una.";
					return false;
				}

				// if (estadoreactivaciones.trim().length() == 0) {
				// this.mensaje =
				// "No se puede dejar vacio el campo Estado Reactivacion";
				// return false;
				// }
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

	public BigDecimal getIdreactivacion() {
		return idreactivacion;
	}

	public void setIdreactivacion(BigDecimal idreactivacion) {
		this.idreactivacion = idreactivacion;
	}

	// 20110912 - EJV - Mantis 789 -->

	public BigDecimal getIdtipoclie() {
		return idtipoclie;
	}

	public void setIdtipoclie(BigDecimal idtipoclie) {
		this.idtipoclie = idtipoclie;
	}

	public BigDecimal getIdpromocion() {
		return idpromocion;
	}

	public void setIdpromocion(BigDecimal idpromocion) {
		this.idpromocion = idpromocion;
	}

	public List getListPromoActiva() {
		return listPromoActiva;
	}

	// <--

}
