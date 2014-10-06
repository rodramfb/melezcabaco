/* 
   javabean para la entidad (Formulario): bacoObsequiosEsquema
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Nov 06 11:36:23 GMT-03:00 2009 
   
   Para manejar la pagina: bacoObsequiosEsquemaFrm.jsp
      
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

public class BeanBacoObsequiosEsquemaFrm implements SessionBean, Serializable {

	private SessionContext context;

	static Logger log = Logger.getLogger(BeanBacoObsequiosEsquemaFrm.class);

	private String validar = "";

	private BigDecimal idesquema = new BigDecimal(-1);

	private int anioactual = Calendar.getInstance().get(Calendar.YEAR);

	private BigDecimal anio = new BigDecimal(-1);

	private BigDecimal idmes = new BigDecimal(-1);

	private String mes = "";

	private BigDecimal idtipoobsequio = new BigDecimal(-1);

	private String tipoobsequio = "";

	private String codigo_st = "";

	private String descrip_st = "";

	private BigDecimal codigo_dt = new BigDecimal(-1);

	private String descrip_dt = "";

	private String cantidad = "";

	private String cartaoregalo = "";

	private BigDecimal idempresa = new BigDecimal(-1);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private List mesesList = new ArrayList();

	private List listTipoObsequios = new ArrayList();

	public BeanBacoObsequiosEsquemaFrm() {
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
			Clientes bacoObsequiosEsquema = Common.getClientes();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = bacoObsequiosEsquema.bacoObsequiosEsquemaCreate(
						this.anio, this.idmes, this.idtipoobsequio,
						this.codigo_st, this.codigo_dt, new BigDecimal(
								this.cantidad), this.cartaoregalo,
						this.idempresa, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = bacoObsequiosEsquema.bacoObsequiosEsquemaUpdate(
						this.idesquema, this.anio, this.idmes,
						this.idtipoobsequio, this.codigo_st, this.codigo_dt,
						new BigDecimal(this.cantidad), this.cartaoregalo,
						this.idempresa, this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = bacoObsequiosEsquema.bacoObsequiosEsquemaDelete(
						this.idesquema, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosBacoObsequiosEsquema() {
		try {
			Clientes bacoObsequiosEsquema = Common.getClientes();
			List listBacoObsequiosEsquema = bacoObsequiosEsquema
					.getBacoObsequiosEsquemaPK(this.idesquema, this.idempresa);
			Iterator iterBacoObsequiosEsquema = listBacoObsequiosEsquema
					.iterator();
			if (iterBacoObsequiosEsquema.hasNext()) {
				String[] uCampos = (String[]) iterBacoObsequiosEsquema.next();
				// TODO: Constructores para cada tipo de datos
				this.idesquema = new BigDecimal(uCampos[0]);
				this.anio = new BigDecimal(uCampos[1]);
				this.idmes = new BigDecimal(uCampos[2]);
				this.mes = uCampos[3];
				this.idtipoobsequio = new BigDecimal(uCampos[4]);
				this.tipoobsequio = uCampos[5];
				this.codigo_st = uCampos[6];
				this.descrip_st = uCampos[7];
				this.codigo_dt = new BigDecimal(uCampos[8]);
				this.descrip_dt = uCampos[9];
				this.cantidad = uCampos[10];
				this.cartaoregalo = uCampos[11];

			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosBacoObsequiosEsquema()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("bacoObsequiosEsquemaAbm.jsp");
				return true;
			}

			this.mesesList = Common.getGeneral().getGlobalMeses();
			this.listTipoObsequios = Common.getClientes()
					.getBacoTipoObsequiosAll( 2500, 0, this.idempresa);

			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (anio == null || anio.longValue() < 1) {
						this.mensaje = "Ingrese ano valido. ";
						return false;
					}
					if (idmes == null || idmes.longValue() < 1) {
						this.mensaje = "Ingrese mes valido. ";
						return false;
					}
					if (idtipoobsequio == null
							|| idtipoobsequio.longValue() < 1) {
						this.mensaje = "Ingrese tipo de obsequio valido ";
						return false;
					}
					if (Common.setNotNull(codigo_st).equals("")) {
						this.mensaje = "Ingrese un articulo valido. ";
						return false;
					}
					if (codigo_dt == null || codigo_dt.longValue() < 1) {
						this.mensaje = "Ingrese un deposito valido. ";
						return false;
					}

					if (!Common.esEntero(cantidad)
							|| Integer.parseInt(cantidad) < 1) {
						this.mensaje = "Ingrese cantidad valida.";
						return false;
					}
					
					if (Common.setNotNull(cartaoregalo).equals("")) {
						this.mensaje = "Ingrese carta/regalo. ";
						return false;
					}

				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosBacoObsequiosEsquema();
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
	public BigDecimal getIdesquema() {
		return idesquema;
	}

	public void setIdesquema(BigDecimal idesquema) {
		this.idesquema = idesquema;
	}

	public BigDecimal getAnio() {
		return anio;
	}

	public void setAnio(BigDecimal anio) {
		this.anio = anio;
	}

	public BigDecimal getIdmes() {
		return idmes;
	}

	public void setIdmes(BigDecimal idmes) {
		this.idmes = idmes;
	}

	public BigDecimal getIdtipoobsequio() {
		return idtipoobsequio;
	}

	public void setIdtipoobsequio(BigDecimal idtipoobsequio) {
		this.idtipoobsequio = idtipoobsequio;
	}

	public String getCodigo_st() {
		return codigo_st;
	}

	public void setCodigo_st(String codigo_st) {
		this.codigo_st = codigo_st;
	}

	public BigDecimal getCodigo_dt() {
		return codigo_dt;
	}

	public void setCodigo_dt(BigDecimal codigo_dt) {
		this.codigo_dt = codigo_dt;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public String getCartaoregalo() {
		return cartaoregalo;
	}

	public void setCartaoregalo(String cartaoregalo) {
		this.cartaoregalo = cartaoregalo;
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

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getTipoobsequio() {
		return tipoobsequio;
	}

	public void setTipoobsequio(String tipoobsequio) {
		this.tipoobsequio = tipoobsequio;
	}

	public String getDescrip_st() {
		return descrip_st;
	}

	public void setDescrip_st(String descrip_st) {
		this.descrip_st = descrip_st;
	}

	public String getDescrip_dt() {
		return descrip_dt;
	}

	public void setDescrip_dt(String descrip_dt) {
		this.descrip_dt = descrip_dt;
	}

	public List getMesesList() {
		return mesesList;
	}

	public void setMesesList(List mesesList) {
		this.mesesList = mesesList;
	}

	public List getListTipoObsequios() {
		return listTipoObsequios;
	}

	public void setListTipoObsequios(List listTipoObsequios) {
		this.listTipoObsequios = listTipoObsequios;
	}

	public int getAnioactual() {
		return anioactual;
	}

	public void setAnioactual(int anioactual) {
		this.anioactual = anioactual;
	}

}
