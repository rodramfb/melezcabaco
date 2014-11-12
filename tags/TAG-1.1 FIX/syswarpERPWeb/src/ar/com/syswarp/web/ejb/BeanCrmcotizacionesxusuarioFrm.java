/* 
 javabean para la entidad (Formulario): crmcotizaciones
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Jun 19 12:25:24 GMT-03:00 2007 
 
 Para manejar la pagina: crmcotizacionesFrm.jsp
 
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

public class BeanCrmcotizacionesxusuarioFrm implements SessionBean,
		Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanCrmcotizacionesxusuarioFrm.class);

	private String validar = "";

	private BigDecimal idcotizacion = BigDecimal.valueOf(-1);

	private BigDecimal idusuario = BigDecimal.valueOf(0);

	private String usuario = "";

	private BigDecimal nrolote = BigDecimal.valueOf(0);

	private BigDecimal idtipocotizacion = BigDecimal.valueOf(0);

	private String idindividuos = "";

	private String razon_nombre = "";

	private String tipocotizacion = "";

	private String idtipofinanciacion = "";

	private String tipofinanciacion = "";

	private Double superficie = Double.valueOf("0");

	private BigDecimal codigo_md = BigDecimal.valueOf(0);

	private String descrip_md = "";

	private Double valor_unitario = Double.valueOf("0");

	private Double valor_total = Double.valueOf("0");

	private Double precio_contado = Double.valueOf("0");

	private Double precio_financiado = Double.valueOf("0");

	private BigDecimal idempresa;

	private BigDecimal idresultadocotizacion = new BigDecimal(-1);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private List listResultadoCotizacion = null;

	public BeanCrmcotizacionesxusuarioFrm() {
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

			CRM crmcotizaciones = Common.getCrm();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = crmcotizaciones.crmcotizacionesCreate(
						this.idusuario, this.nrolote, this.idtipocotizacion,
						this.idtipofinanciacion, this.superficie,
						this.codigo_md, this.valor_unitario, this.valor_total,
						this.precio_contado, this.precio_financiado,
						this.idempresa, this.usuarioalt, this.idindividuos,
						this.idresultadocotizacion);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = crmcotizaciones.crmcotizacionesUpdate(
						this.idcotizacion, this.idusuario, this.nrolote,
						this.idtipocotizacion, this.idtipofinanciacion,
						this.superficie, this.codigo_md, this.valor_unitario,
						this.valor_total, this.precio_contado,
						this.precio_financiado, this.idempresa,
						this.usuarioact, this.idindividuos,
						this.idresultadocotizacion);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = crmcotizaciones.crmcotizacionesDelete(
						this.idcotizacion, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosCrmcotizaciones() {
		try {
			CRM crmcotizaciones = Common.getCrm();

			List listCrmcotizaciones = crmcotizaciones.getCrmcotizacionesPK(
					this.idcotizacion, this.idempresa);
			Iterator iterCrmcotizaciones = listCrmcotizaciones.iterator();
			if (iterCrmcotizaciones.hasNext()) {
				String[] uCampos = (String[]) iterCrmcotizaciones.next();
				// TODO: Constructores para cada tipo de datos
				this.idcotizacion = BigDecimal.valueOf(Long
						.parseLong(uCampos[0]));
				this.idusuario = BigDecimal.valueOf(Long.parseLong(uCampos[1]));
				this.usuario = uCampos[2];
				this.nrolote = BigDecimal.valueOf(Long.parseLong(uCampos[3]));
				this.idtipocotizacion = BigDecimal.valueOf(Long
						.parseLong(uCampos[4]));
				this.tipocotizacion = uCampos[5];
				this.idtipofinanciacion = uCampos[6];
				this.tipofinanciacion = uCampos[7];
				this.superficie = Double.valueOf(uCampos[8]);
				this.codigo_md = BigDecimal.valueOf(Long.parseLong(uCampos[9]));
				this.descrip_md = uCampos[10];
				this.valor_unitario = Double.valueOf(uCampos[11]);
				this.valor_total = Double.valueOf(uCampos[12]);
				this.precio_contado = Double.valueOf(uCampos[13]);
				this.precio_financiado = Double.valueOf(uCampos[14]);
				this.idresultadocotizacion = new BigDecimal(
						uCampos[18] != null ? uCampos[16] : "-1");
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosCrmcotizaciones()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			CRM crmcotizaciones = Common.getCrm();
			this.listResultadoCotizacion = crmcotizaciones
					.getcrmresultadoscotizacionesAll(100, 0, this.idempresa);

			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("crmcotizacionesxusuarioAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (idusuario == null) {
						this.mensaje = "No se puede dejar vacio el campo idusuario ";
						return false;
					}
					if (nrolote == null) {
						this.mensaje = "No se puede dejar vacio el campo nrolote ";
						return false;
					}
					if (idtipocotizacion == null) {
						this.mensaje = "No se puede dejar vacio el campo tipo cotizacion ";
						return false;
					}
					if (superficie == null) {
						this.mensaje = "No se puede dejar vacio el campo superficie ";
						return false;
					}
					if (codigo_md == null) {
						this.mensaje = "No se puede dejar vacio el campo medida  ";
						return false;
					}
					if (valor_unitario == null) {
						this.mensaje = "No se puede dejar vacio el campo valor unitario ";
						return false;
					}
					if (valor_total == null) {
						this.mensaje = "No se puede dejar vacio el campo valor total ";
						return false;
					}
					if (usuario.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Usuario  ";
						return false;
					}

					if (tipocotizacion.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Tipo cotizacion  ";
						return false;
					}
					if (descrip_md.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Medida  ";
						return false;
					}

					if (this.idresultadocotizacion == null
							|| this.idresultadocotizacion.longValue() < 0) {
						this.mensaje = "No se puede dejar vacio el campo Resultado de la Cotización.  ";
						return false;
					}

					// 2. len 0 para campos nulos
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosCrmcotizaciones();
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
	public BigDecimal getIdcotizacion() {
		return idcotizacion;
	}

	public void setIdcotizacion(BigDecimal idcotizacion) {
		this.idcotizacion = idcotizacion;
	}

	public BigDecimal getNrolote() {
		return nrolote;
	}

	public void setNrolote(BigDecimal nrolote) {
		this.nrolote = nrolote;
	}

	public BigDecimal getIdtipocotizacion() {
		return idtipocotizacion;
	}

	public void setIdtipocotizacion(BigDecimal idtipocotizacion) {
		this.idtipocotizacion = idtipocotizacion;
	}

	public String getIdtipofinanciacion() {
		return idtipofinanciacion;
	}

	public void setIdtipofinanciacion(String idtipofinanciacion) {
		this.idtipofinanciacion = idtipofinanciacion;
	}

	public Double getSuperficie() {
		return superficie;
	}

	public void setSuperficie(Double superficie) {
		this.superficie = superficie;
	}

	public BigDecimal getCodigo_md() {
		return codigo_md;
	}

	public void setCodigo_md(BigDecimal codigo_md) {
		this.codigo_md = codigo_md;
	}

	public Double getValor_unitario() {
		return valor_unitario;
	}

	public void setValor_unitario(Double valor_unitario) {
		this.valor_unitario = valor_unitario;
	}

	public Double getValor_total() {
		return valor_total;
	}

	public void setValor_total(Double valor_total) {
		this.valor_total = valor_total;
	}

	public Double getPrecio_contado() {
		return precio_contado;
	}

	public void setPrecio_contado(Double precio_contado) {
		this.precio_contado = precio_contado;
	}

	public Double getPrecio_financiado() {
		return precio_financiado;
	}

	public void setPrecio_financiado(Double precio_financiado) {
		this.precio_financiado = precio_financiado;
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

	public String getTipocotizacion() {
		return tipocotizacion;
	}

	public void setTipocotizacion(String tipocotizacion) {
		this.tipocotizacion = tipocotizacion;
	}

	public String getTipofinanciacion() {
		return tipofinanciacion;
	}

	public void setTipofinanciacion(String tipofinanciacion) {
		this.tipofinanciacion = tipofinanciacion;
	}

	public String getDescrip_md() {
		return descrip_md;
	}

	public void setDescrip_md(String descrip_md) {
		this.descrip_md = descrip_md;
	}

	public String getIdindividuos() {
		return idindividuos;
	}

	public void setIdindividuos(String idindividuos) {
		this.idindividuos = idindividuos;
	}

	public String getRazon_nombre() {
		return razon_nombre;
	}

	public void setRazon_nombre(String razon_nombre) {
		this.razon_nombre = razon_nombre;
	}

	public BigDecimal getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(BigDecimal idusuario) {
		this.idusuario = idusuario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public BigDecimal getIdresultadocotizacion() {
		return idresultadocotizacion;
	}

	public void setIdresultadocotizacion(BigDecimal idresultadocotizacion) {
		this.idresultadocotizacion = idresultadocotizacion;
	}

	public List getListResultadoCotizacion() {
		return listResultadoCotizacion;
	}

	public void setListResultadoCotizacion(List listResultadoCotizacion) {
		this.listResultadoCotizacion = listResultadoCotizacion;
	}
}
