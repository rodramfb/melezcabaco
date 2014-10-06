/* 
 javabean para la entidad (Formulario): crmProductos
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Fri Aug 03 11:44:52 ART 2007 
 
 Para manejar la pagina: crmProductosFrm.jsp
 
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

public class BeanCrmProductosFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanCrmProductosFrm.class);

	private String validar = "";

	private String calcular = "";

	/**
	 * @see: getProximoNrolote - Recupera el maximo nrolote + 1
	 */
	private String nrolote = "0000000000";

	private BigDecimal idfamiliacotizacion = new BigDecimal(-1);

	private Hashtable htFamiliaCotizacion = null;

	private BigDecimal idgrupoproducto = new BigDecimal(-1);

	private Hashtable htGrupoProducto = null;

	private BigDecimal idproductostatus = new BigDecimal(-1);

	private Hashtable htProductoStatus = null;

	private String calificacion = "";

	private String superficie = "0.00";

	private String precioxmts = "0.00";

	private BigDecimal valorunidadsup = new BigDecimal(0.0);

	private String precio = "0.00";

	private String valorcontado = "0.00";

	private String boleto = "0.00";

	private String cuotasx36 = "0.00";

	private BigDecimal idempresa = new BigDecimal(-1);

	private String usuarioalt = "";

	private String usuarioact = "";

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanCrmProductosFrm() {
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
			CRM crmProductos = Common.getCrm();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = crmProductos.crmProductosCreate(new BigDecimal(
						this.nrolote), this.idfamiliacotizacion,
						this.idgrupoproducto, this.idproductostatus,
						this.calificacion, new Double(this.superficie),
						new Double(this.precioxmts), new Double(this.precio),
						new Double(this.valorcontado), new Double(this.boleto),
						new Double(this.cuotasx36), this.idempresa,
						this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = crmProductos.crmProductosUpdate(new BigDecimal(
						this.nrolote), this.idfamiliacotizacion,
						this.idgrupoproducto, this.idproductostatus,
						this.calificacion, new Double(this.superficie),
						new Double(this.precioxmts), new Double(this.precio),
						new Double(this.valorcontado), new Double(this.boleto),
						new Double(this.cuotasx36), this.idempresa,
						this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = crmProductos.crmProductosDelete(new BigDecimal(
						this.nrolote));
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosCrmProductos() {
		try {
			CRM crmProductos = Common.getCrm();
			List listCrmProductos = crmProductos.getCrmProductosPK(
					new BigDecimal(this.nrolote), this.idempresa);
			Iterator iterCrmProductos = listCrmProductos.iterator();
			if (iterCrmProductos.hasNext()) {
				String[] uCampos = (String[]) iterCrmProductos.next();
				// TODO: Constructores para cada tipo de datos
				this.nrolote = uCampos[0];
				this.idfamiliacotizacion = new BigDecimal(uCampos[1]);
				this.idgrupoproducto = new BigDecimal(uCampos[2]);
				this.idproductostatus = new BigDecimal(uCampos[3]);
				this.calificacion = uCampos[4];
				this.superficie = uCampos[5];
				this.precioxmts = uCampos[6];
				this.precio = uCampos[7];
				this.valorcontado = uCampos[8];
				this.boleto = uCampos[9];
				this.cuotasx36 = uCampos[10];
				this.idempresa = new BigDecimal(uCampos[11]);
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosCrmProductos()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {

			CRM crm = Common.getCrm();

			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("crmProductosAbm.jsp");
				return true;
			}

			this.htFamiliaCotizacion = Common.getHashEntidadCrm(
					"getCrmtiposcotizacionesAll", new Class[] { long.class,
							long.class, BigDecimal.class }, new Object[] {
							new Long(100), new Long(0), this.idempresa });

			this.htGrupoProducto = Common.getHashEntidadCrm(
					"getCrmGruposProductosAll", new Class[] { long.class,
							long.class, BigDecimal.class }, new Object[] {
							new Long(100), new Long(0), this.idempresa });

			this.htProductoStatus = Common.getHashEntidadCrm(
					"getCrmProductosStatusAll", new Class[] { long.class,
							long.class, BigDecimal.class }, new Object[] {
							new Long(100), new Long(0), this.idempresa });

			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos

					if (nrolote == null) {
						this.mensaje = "No se puede dejar vacio el campo calificacion ";
						return false;
					}

					if (!Common.esNumerico(nrolote)) {
						this.mensaje = "Lote debe ser un valor numérico. ";
						return false;
					}

					if (idgrupoproducto == null
							|| idgrupoproducto.longValue() < 0) {
						this.mensaje = "Seleccione grupo producto";
						return false;
					}

					if (idproductostatus == null
							|| idproductostatus.longValue() < 0) {
						this.mensaje = "Seleccione grupo status.";
						return false;
					}

					if (calificacion == null) {
						this.mensaje = "No se puede dejar vacio el campo calificacion ";
						return false;
					}

					if (superficie == null) {
						this.mensaje = "No se puede dejar vacio el campo superficie ";
						return false;
					}

					if (!Common.esNumerico(superficie)) {
						this.mensaje = "Superficie debe ser un valor numérico. ";
						return false;
					}

					if (Double.parseDouble(superficie) <= 0) {
						this.mensaje = "Superficie debe ser mayor a cero (0). ";
						return false;
					}

					if (precioxmts == null) {
						this.mensaje = "No se puede dejar vacio el campo precioxmts ";
						return false;
					}

					if (!Common.esNumerico(precioxmts)) {
						this.mensaje = "Precio x mts. debe ser un valor numérico. ";
						return false;
					}

					if (precio == null) {
						this.mensaje = "No se puede dejar vacio el campo precio ";
						return false;
					}

					if (!Common.esNumerico(precio)) {
						this.mensaje = "Precio debe ser un valor numérico. ";
						return false;
					}

					if (valorcontado == null) {
						this.mensaje = "No se puede dejar vacio el campo valorcontado ";
						return false;
					}

					if (!Common.esNumerico(valorcontado)) {
						this.mensaje = "Valor Contado debe ser un valor numérico. ";
						return false;
					}

					if (boleto == null) {
						this.mensaje = "No se puede dejar vacio el campo boleto ";
						return false;
					}

					if (!Common.esNumerico(boleto)) {
						this.mensaje = "Boleto debe ser un valor numérico. ";
						return false;
					}

					if (!this.calcularImportes()) {
						return false;
					}

				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosCrmProductos();
				}
			}

			if (this.idfamiliacotizacion != null
					&& this.idfamiliacotizacion.longValue() > 0)
				this.valorunidadsup = crm.getCrmTCValorUnidaSup(
						this.idfamiliacotizacion, this.idempresa);

			if (!this.calcular.equals("")) {
				this.calcularImportes();
			}

		} catch (Exception e) {
			log.error(" ejecutarValidacion(): " + e);
		}
		return true;
	}

	private boolean calcularImportes() {

		boolean fOk = true;

		try {
			CRM crm = Common.getCrm();
			if (this.idfamiliacotizacion == null
					|| this.idfamiliacotizacion.longValue() < 0) {
				this.mensaje = "Seleccione familia cotización.";
				fOk = false;

			} else {

				this.valorunidadsup = crm.getCrmTCValorUnidaSup(
						this.idfamiliacotizacion, this.idempresa);

				if (Double.parseDouble(this.precioxmts) == 0)
					this.precioxmts = this.valorunidadsup.toString();

				this.precio = ((new BigDecimal(this.superficie)
						.multiply(new BigDecimal(this.precioxmts))).setScale(2,
						BigDecimal.ROUND_UP)).toString();

				this.valorcontado = ((new BigDecimal(this.precio)
						.multiply(new BigDecimal(0.9))).setScale(2,
						BigDecimal.ROUND_UP)).toString();

				this.boleto = ((new BigDecimal(this.precio)
						.multiply(new BigDecimal(0.3))).setScale(2,
						BigDecimal.ROUND_UP)).toString();

				this.cuotasx36 = (((new BigDecimal(this.precio)
						.subtract(new BigDecimal(this.boleto))).divide(
						new BigDecimal(36), 2))
						.setScale(2, BigDecimal.ROUND_UP)).toString();

			}

		} catch (Exception e) {
			fOk = false;
			log.error("calcularImportes():" + e);
		}

		return fOk;
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
	public String getNrolote() {
		return nrolote;
	}

	public void setNrolote(String nrolote) {
		this.nrolote = nrolote;
	}

	public BigDecimal getIdfamiliacotizacion() {
		return idfamiliacotizacion;
	}

	public void setIdfamiliacotizacion(BigDecimal idfamiliacotizacion) {
		this.idfamiliacotizacion = idfamiliacotizacion;
	}

	public BigDecimal getIdgrupoproducto() {
		return idgrupoproducto;
	}

	public void setIdgrupoproducto(BigDecimal idgrupoproducto) {
		this.idgrupoproducto = idgrupoproducto;
	}

	public BigDecimal getIdproductostatus() {
		return idproductostatus;
	}

	public void setIdproductostatus(BigDecimal idproductostatus) {
		this.idproductostatus = idproductostatus;
	}

	public String getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(String calificacion) {
		this.calificacion = calificacion;
	}

	public String getSuperficie() {
		return superficie;
	}

	public void setSuperficie(String superficie) {
		this.superficie = superficie;
	}

	public String getPrecioxmts() {
		return precioxmts;
	}

	public void setPrecioxmts(String precioxmts) {
		this.precioxmts = precioxmts;
	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}

	public String getValorcontado() {
		return valorcontado;
	}

	public void setValorcontado(String valorcontado) {
		this.valorcontado = valorcontado;
	}

	public String getBoleto() {
		return boleto;
	}

	public void setBoleto(String boleto) {
		this.boleto = boleto;
	}

	public String getCuotasx36() {
		return cuotasx36;
	}

	public void setCuotasx36(String cuotasx36) {
		this.cuotasx36 = cuotasx36;
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

	public Hashtable getHtFamiliaCotizacion() {
		return htFamiliaCotizacion;
	}

	public void setHtFamiliaCotizacion(Hashtable htFamiliaCotizacion) {
		this.htFamiliaCotizacion = htFamiliaCotizacion;
	}

	public Hashtable getHtGrupoProducto() {
		return htGrupoProducto;
	}

	public void setHtGrupoProducto(Hashtable htGrupoProducto) {
		this.htGrupoProducto = htGrupoProducto;
	}

	public Hashtable getHtProductoStatus() {
		return htProductoStatus;
	}

	public void setHtProductoStatus(Hashtable htProductoStatus) {
		this.htProductoStatus = htProductoStatus;
	}

	public String getCalcular() {
		return calcular;
	}

	public void setCalcular(String calcular) {
		this.calcular = calcular;
	}

	public BigDecimal getValorunidadsup() {
		return valorunidadsup;
	}

	public void setValorunidadsup(BigDecimal valorunidadsup) {
		this.valorunidadsup = valorunidadsup;
	}
}
