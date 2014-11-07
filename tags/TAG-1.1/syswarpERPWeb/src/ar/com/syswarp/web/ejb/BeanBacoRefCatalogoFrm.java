/* 
   javabean para la entidad (Formulario): bacoRefCatalogo
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Jun 15 15:54:26 ART 2010 
   
   Para manejar la pagina: bacoRefCatalogoFrm.jsp
      
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

public class BeanBacoRefCatalogoFrm implements SessionBean, Serializable {

	private SessionContext context;

	static Logger log = Logger.getLogger(BeanBacoRefCatalogoFrm.class);

	private String validar = "";

	private BigDecimal idcatalogo = new BigDecimal(-1);

	private String codigo_st = "";

	private String descrip_st = "";

	private String puntaje = "";

	private String comprometido = "";

	private String utilizado = "";

	private String fechadesde = "";

	private String fechahasta = "";

	private String fechabaja = "";

	private BigDecimal idempresa = new BigDecimal(-1);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	// 201203015 - EJV - Mantis 702 -->

	private BigDecimal idcatalogocategoria = new BigDecimal(-1);

	private List listCatalogoCategoria = new ArrayList();

	// <--

	public BeanBacoRefCatalogoFrm() {
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
				this.mensaje = clientes.bacoRefCatalogoCreate(this.codigo_st,
						this.descrip_st, new BigDecimal(this.puntaje),
						new BigDecimal(this.comprometido), new BigDecimal(
								this.utilizado), (java.sql.Date) Common
								.setObjectToStrOrTime(this.fechadesde,
										"StrToJSDate"), (java.sql.Date) Common
								.setObjectToStrOrTime(this.fechahasta,
										"StrToJSDate"), null,
						this.idcatalogocategoria, this.idempresa,
						this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clientes.bacoRefCatalogoUpdate(this.idcatalogo,
						this.codigo_st, this.descrip_st, new BigDecimal(
								this.puntaje),
						new BigDecimal(this.comprometido), new BigDecimal(
								this.utilizado), (java.sql.Date) Common
								.setObjectToStrOrTime(this.fechadesde,
										"StrToJSDate"), (java.sql.Date) Common
								.setObjectToStrOrTime(this.fechahasta,
										"StrToJSDate"), null,
						this.idcatalogocategoria, this.idempresa,
						this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clientes.bacoRefCatalogoDeleteLogico(
						this.idcatalogo, this.idempresa, this.usuarioact);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosBacoRefCatalogo() {
		try {
			Clientes clientes = Common.getClientes();
			List listBacoRefCatalogo = clientes.getBacoRefCatalogoPK(
					this.idcatalogo, this.idempresa);
			Iterator iterBacoRefCatalogo = listBacoRefCatalogo.iterator();
			if (iterBacoRefCatalogo.hasNext()) {
				String[] uCampos = (String[]) iterBacoRefCatalogo.next();
				// TODO: Constructores para cada tipo de datos
				this.idcatalogo = new BigDecimal(uCampos[0]);
				this.codigo_st = uCampos[1];
				this.descrip_st = uCampos[2];
				this.puntaje = uCampos[3];
				this.comprometido = uCampos[4];
				this.utilizado = uCampos[5];
				this.fechadesde = (String) Common.setObjectToStrOrTime(
						java.sql.Date.valueOf(uCampos[6]), "JSDateToStr");
				this.fechahasta = (String) Common.setObjectToStrOrTime(
						java.sql.Date.valueOf(uCampos[7]), "JSDateToStr");
				this.fechabaja = uCampos[8];
				this.idcatalogocategoria = new BigDecimal(uCampos[9]);
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosBacoRefCatalogo()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("bacoRefCatalogoAbm.jsp");
				return true;
			}

			this.listCatalogoCategoria = Common.getClientes()
					.getBacoRefCatalogoCategoriaAll(500, 0, this.idempresa);

			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos

					if (this.idcatalogocategoria.intValue() < 1) {

						this.mensaje = "Es necesario asignar categoria valida. ";
						return false;

					}

					if (!Common.esNumerico(Common.setNotNull(this.puntaje))) {
						this.mensaje = "Ingrese un valor numérico válido para  puntaje ";
						return false;
					}
					if (!Common
							.esNumerico(Common.setNotNull(this.comprometido))) {
						this.mensaje = "Ingrese un valor numérico válido para  comprometido ";
						return false;
					}
					if (!Common.esNumerico(Common.setNotNull(this.utilizado))) {
						this.mensaje = "Ingrese un valor numérico válido para utilizado ";
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
					getDatosBacoRefCatalogo();
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
	public BigDecimal getIdcatalogo() {
		return idcatalogo;
	}

	public void setIdcatalogo(BigDecimal idcatalogo) {
		this.idcatalogo = idcatalogo;
	}

	public String getCodigo_st() {
		return codigo_st;
	}

	public void setCodigo_st(String codigo_st) {
		this.codigo_st = codigo_st;
	}

	public String getDescrip_st() {
		return descrip_st;
	}

	public void setDescrip_st(String descrip_st) {
		this.descrip_st = descrip_st;
	}

	public String getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(String puntaje) {
		this.puntaje = puntaje;
	}

	public String getComprometido() {
		return comprometido;
	}

	public void setComprometido(String comprometido) {
		this.comprometido = comprometido;
	}

	public String getUtilizado() {
		return utilizado;
	}

	public void setUtilizado(String utilizado) {
		this.utilizado = utilizado;
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

	// 201203015 - EJV - Mantis 702 -->
	public BigDecimal getIdcatalogocategoria() {
		return idcatalogocategoria;
	}

	public void setIdcatalogocategoria(BigDecimal idcatalogocategoria) {
		this.idcatalogocategoria = idcatalogocategoria;
	}

	public List getListCatalogoCategoria() {
		return listCatalogoCategoria;
	}
	// <--

}
