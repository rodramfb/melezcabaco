package ar.com.syswarp.web.ejb;

/* 
 javabean para la entidad (Formulario): proveedoProveed
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Wed Jul 05 15:38:22 GMT-03:00 2006 

 Para manejar la pagina: proveedoProveedFrm.jsp

 */
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

public class BeanProveedoconceptoscontablesFrm implements SessionBean, Serializable {
	private SessionContext context;



	private BigDecimal idempresa = new BigDecimal(-1);
	static Logger log = Logger.getLogger(BeanProveedoconceptoscontablesFrm.class);

	private String validar = "";

	private BigDecimal idconcepto = new BigDecimal(-1);
	private BigDecimal idcuenta = new BigDecimal(0);
	private String cuenta = "";
	private BigDecimal orden = new BigDecimal(0);
	private String letra = "";
	private BigDecimal idtipocomp = new BigDecimal(0);
	private String tipocomp = "";
	private String tipo = "";

	private String usuarioalt = "";

	private String usuarioact = "";

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanProveedoconceptoscontablesFrm() {
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
			Proveedores proveedoProveed = Common.getProveedores();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = proveedoProveed.ProveedoConceptoscontablesCreate(
						this.idcuenta, this.orden,this.letra,this.idtipocomp,this.tipo,this.usuarioalt,idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = proveedoProveed.ProveedoConceptoscontablesUpdate(this.idconcepto,this.idcuenta,this.orden,this.letra,this.idtipocomp,this.tipo,this.usuarioact,this.idempresa);

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosProveedoProveed() {
		try {
			Proveedores proveedoProveed = Common.getProveedores();
			List listProveedoProveed = proveedoProveed.getProveedoConceptoscontablesPK(
					this.idconcepto, this.idempresa);
			Iterator iterProveedoProveed = listProveedoProveed.iterator();
			if (iterProveedoProveed.hasNext()) {
				String[] uCampos = (String[]) iterProveedoProveed.next();
				// TODO: Constructores para cada tipo de datos
				this.idconcepto = BigDecimal.valueOf(Long.parseLong(uCampos[0]));
				this.idcuenta = BigDecimal.valueOf(Long.parseLong(uCampos[1]));
				this.orden = BigDecimal.valueOf(Long.parseLong(uCampos[2]));
				this.letra = uCampos[3];
				this.idtipocomp = BigDecimal.valueOf(Long.parseLong(uCampos[4]));
				this.tipocomp = uCampos[5];
				this.tipo = uCampos[6];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosProveedoProveed()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("proveedoconceptoscontablesAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (this.idcuenta == null
							|| this.idcuenta.longValue() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Cuenta Contable.  ";
						return false;
					}
					if (this.orden == null
							|| this.orden.longValue() < 0) {
						this.mensaje = "No se puede dejar vacio el campo Orden.  ";
						return false;
					}
					
					if (letra.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Letra  ";
						return false;
					}
					if (this.idtipocomp == null
							|| this.idtipocomp.longValue() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Tipo Comprobante.  ";
						return false;
					}
					if (tipo.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Tipo  ";
						return false;
					}

				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosProveedoProveed();
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
		
	public BigDecimal getIdconcepto() {
		return idconcepto;
	}

	public void setIdconcepto(BigDecimal idconcepto) {
		this.idconcepto = idconcepto;
	}

	public BigDecimal getIdcuenta() {
		return idcuenta;
	}

	public void setIdcuenta(BigDecimal idcuenta) {
		this.idcuenta = idcuenta;
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public BigDecimal getOrden() {
		return orden;
	}

	public void setOrden(BigDecimal orden) {
		this.orden = orden;
	}

	public String getLetra() {
		return letra;
	}

	public void setLetra(String letra) {
		this.letra = letra;
	}

	public BigDecimal getIdtipocomp() {
		return idtipocomp;
	}

	public void setIdtipocomp(BigDecimal idtipocomp) {
		this.idtipocomp = idtipocomp;
	}

	public String getTipocomp() {
		return tipocomp;
	}

	public void setTipocomp(String tipocomp) {
		this.tipocomp = tipocomp;
	}
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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
}
