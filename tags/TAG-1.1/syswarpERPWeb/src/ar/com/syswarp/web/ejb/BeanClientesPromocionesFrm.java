/* 
   javabean para la entidad (Formulario): clientesPromociones
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Jan 18 09:58:25 ART 2011 
   
   Para manejar la pagina: clientesPromocionesFrm.jsp
      
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

public class BeanClientesPromocionesFrm implements SessionBean, Serializable {

	private SessionContext context;

	static Logger log = Logger.getLogger(BeanClientesPromocionesFrm.class);

	private String validar = "";

	private BigDecimal idpromocion = new BigDecimal(-1);

	private String promocion = "";

	private BigDecimal idtipoclie = new BigDecimal(-1);

	private String fechadesde = "";

	private String fechahasta = "";

	private java.sql.Date fdesde = null;

	private java.sql.Date fhasta = null;

	private String convenio = "";

	private String porc_desc_ci = "";

	// 20110907 - EJV - Mantis 777 -->
	private String porc_desc_ci_react = "";
	// <--

	private String porc_liq = "0";

	private BigDecimal idempresa = new BigDecimal(-1);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private List listTipoClie = new ArrayList();
	// 20110907 - EJV - Mantis 777 -->
	private List listClub = new ArrayList();

	private BigDecimal idclub = new BigDecimal(-1);

	// <--
	public BeanClientesPromocionesFrm() {
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
				this.mensaje = clientes
						.clientesPromocionesCreate(this.promocion,
								this.idtipoclie, this.fdesde, this.fhasta,
								this.convenio,
								new BigDecimal(this.porc_desc_ci),  new BigDecimal(
										this.porc_desc_ci_react),
								new BigDecimal(this.porc_liq), this.idempresa,
								this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clientes
						.clientesPromocionesUpdate(this.idpromocion,
								this.promocion, this.idtipoclie, this.fdesde,
								this.fhasta, this.convenio, new BigDecimal(
										this.porc_desc_ci),  new BigDecimal(
												this.porc_desc_ci_react), new BigDecimal(
										this.porc_liq), this.idempresa,
								this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clientes.clientesPromocionesDelete(
						this.idpromocion, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosClientesPromociones() {
		try {
			Clientes clientes = Common.getClientes();
			List listClientesPromociones = clientes.getClientesPromocionesPK(
					this.idpromocion, this.idempresa);
			Iterator iterClientesPromociones = listClientesPromociones
					.iterator();
			if (iterClientesPromociones.hasNext()) {
				String[] uCampos = (String[]) iterClientesPromociones.next();
				// TODO: Constructores para cada tipo de datos
				this.idpromocion = new BigDecimal(uCampos[0]);
				this.promocion = uCampos[1];
				this.idtipoclie = new BigDecimal(uCampos[2]);
				this.fechadesde = Common.setObjectToStrOrTime(
						java.sql.Date.valueOf(uCampos[3]), "JSDateToStr")
						.toString();
				this.fechahasta = !Common.setNotNull(uCampos[4]).equals("") ? Common
						.setObjectToStrOrTime(
								java.sql.Date.valueOf(uCampos[4]),
								"JSDateToStr").toString()
						: "";
				this.convenio = uCampos[5];
				this.porc_desc_ci = (uCampos[6]);
				// 20110907 - EJV - Mantis 777 -->
				this.porc_desc_ci_react = (uCampos[7]);
				this.idclub = new BigDecimal(uCampos[9]);
				// <--
				this.porc_liq = (uCampos[8]);
				// 20110907 - EJV - Mantis 777 -->
				this.listTipoClie = Common.getClientes().getClientestipoclieXClub(
						250, 0, this.idclub, this.idempresa);
				// <--

			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosClientesPromociones()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {

			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("clientesPromocionesAbm.jsp");
				return true;
			}
			// 20110907 - EJV - Mantis 777 -->
			if(this.idclub.intValue() > 0)
			this.listTipoClie = Common.getClientes().getClientestipoclieXClub(
					250, 0, this.idclub, this.idempresa);
			// <--
			this.listClub = Common.getClientes().getClientesClubAll(100, 0,
					this.idempresa);

			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos

					if (Common.setNotNull(this.promocion).equals("")) {
						this.mensaje = "No se puede dejar vacio el campo promocion ";
						return false;
					}

					if (this.idclub == null || this.idclub.longValue() < 1) {
						this.mensaje = "Es necesario seleccionar club. ";
						return false;
					}

					if (this.idtipoclie == null
							|| this.idtipoclie.longValue() < 1) {
						this.mensaje = "No se puede dejar vacio el campo tipo de cliente ";
						return false;
					}

					if (!Common.isFormatoFecha(this.fechadesde)
							|| !Common.isFechaValida(this.fechadesde)) {
						this.mensaje = "Ingrese fecha desde valida. ";
						return false;
					}

					this.fdesde = (java.sql.Date) Common.setObjectToStrOrTime(
							this.fechadesde, "StrToJSDate");

					if (!Common.setNotNull(this.fechahasta).equals("")) {

						if (!Common.isFormatoFecha(this.fechahasta)
								|| !Common.isFechaValida(this.fechahasta)) {
							this.mensaje = "Fecha hasta invalida. ";
							return false;
						}

						this.fhasta = (java.sql.Date) Common
								.setObjectToStrOrTime(this.fechahasta,
										"StrToJSDate");

						if (this.fdesde.after(fhasta)) {
							this.mensaje = "Fecha desde no puede ser mayor a fecha hasta. ";
							return false;
						}

					}

					if (!Common.esPorcentaje(this.porc_desc_ci)) {
						this.mensaje = "Es necesario ingresr porcentaje descuento CI alta valido. ";
						return false;
					}

					// 20110907 - EJV - Mantis 777 -->

					if (!Common.esPorcentaje(this.porc_desc_ci_react)) {
						this.mensaje = "Es necesario ingresr porcentaje descuento CI reactivacion valido. ";
						return false;
					}

					// <--

					if (!Common.esPorcentaje(this.porc_liq)) {
						this.mensaje = "No se puede dejar vacio el campo porcentaje liquidacion ";
						return false;
					}

				}

				this.ejecutarSentenciaDML();

			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosClientesPromociones();
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
	public BigDecimal getIdpromocion() {
		return idpromocion;
	}

	public void setIdpromocion(BigDecimal idpromocion) {
		this.idpromocion = idpromocion;
	}

	public String getPromocion() {
		return promocion;
	}

	public void setPromocion(String promocion) {
		this.promocion = promocion;
	}

	public BigDecimal getIdtipoclie() {
		return idtipoclie;
	}

	public void setIdtipoclie(BigDecimal idtipoclie) {
		this.idtipoclie = idtipoclie;
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

	public String getConvenio() {
		return convenio;
	}

	public void setConvenio(String convenio) {
		this.convenio = convenio;
	}

	public String getPorc_desc_ci() {
		return porc_desc_ci;
	}

	public void setPorc_desc_ci(String porc_desc_ci) {
		this.porc_desc_ci = porc_desc_ci;
	}

	// 20110907 - EJV - Mantis 777 -->

	public String getPorc_desc_ci_react() {
		return porc_desc_ci_react;
	}

	public void setPorc_desc_ci_react(String porc_desc_ci_react) {
		this.porc_desc_ci_react = porc_desc_ci_react;
	}

	// <--

	public String getPorc_liq() {
		return porc_liq;
	}

	public void setPorc_liq(String porc_liq) {
		this.porc_liq = porc_liq;
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

	// 20110907 - EJV - Mantis 777 -->
	public List getListTipoClie() {
		return listTipoClie;
	}

	public void setListTipoClie(List listTipoClie) {
		this.listTipoClie = listTipoClie;
	}

	public BigDecimal getIdclub() {
		return idclub;
	}

	public void setIdclub(BigDecimal idclub) {
		this.idclub = idclub;
	}

	public List getListClub() {
		return listClub;
	}
	// <--

}
