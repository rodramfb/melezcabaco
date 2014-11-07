/* 
   javabean para la entidad (Formulario): bacotmliquidacionresumen
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Mar 02 15:34:16 ART 2011 
   
   Para manejar la pagina: bacotmliquidacionresumenFrm.jsp
      
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

public class BeanBacotmliquidacionresumenFrm implements SessionBean,
		Serializable {
	private SessionContext context;
	static Logger log = Logger.getLogger(BeanBacotmliquidacionresumenFrm.class);
	private String validar = "";
	private BigDecimal idliquidacion=new BigDecimal(-1);
	private BigDecimal anio=new BigDecimal(-1);
	private BigDecimal mes=new BigDecimal(-1);
	private String usuario="";
	private BigDecimal comision_vc=new BigDecimal(-1);
	private BigDecimal total_vc=new BigDecimal(-1);
	private BigDecimal comision_vv=new BigDecimal(-1);
	private BigDecimal total_vv=new BigDecimal(-1);
	private BigDecimal comision_ve=new BigDecimal(-1);
	private BigDecimal total_ve=new BigDecimal(-1);
	private BigDecimal comision_vc_jf=new BigDecimal(-1);
	private BigDecimal total_vc_jf=new BigDecimal(-1);
	private BigDecimal comision_vv_jf=new BigDecimal(-1);
	private BigDecimal total_vv_jf=new BigDecimal(-1);
	private BigDecimal idempresa=new BigDecimal(-1);
	private String usuarioalt;
	private String usuarioact;
	private String mensaje = "";
	private String volver = "";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String accion = "";

	public BeanBacotmliquidacionresumenFrm() {
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
			General clientes = Common.getGeneral();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = clientes.bacotmliquidacionresumenCreate(
						this.anio, this.mes, this.usuario, this.comision_vc,
						this.total_vc, this.comision_vv, this.total_vv,
						this.comision_ve, this.total_ve, this.comision_vc_jf,
						this.total_vc_jf, this.comision_vv_jf,
						this.total_vv_jf, this.idempresa, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clientes.bacotmliquidacionresumenUpdate(
						this.idliquidacion, this.anio, this.mes, this.usuario,
						this.comision_vc, this.total_vc, this.comision_vv,
						this.total_vv, this.comision_ve, this.total_ve,
						this.comision_vc_jf, this.total_vc_jf,
						this.comision_vv_jf, this.total_vv_jf, this.idempresa,
						this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clientes.bacotmliquidacionresumenDelete(
						this.idliquidacion, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosBacotmliquidacionresumen() {
		try {
			General clientes = Common.getGeneral();
			List listBacotmliquidacionresumen = clientes
					.getBacotmliquidacionresumenPK(this.idliquidacion,
							this.idempresa);
			Iterator iterBacotmliquidacionresumen = listBacotmliquidacionresumen
					.iterator();
			if (iterBacotmliquidacionresumen.hasNext()) {
				String[] uCampos = (String[]) iterBacotmliquidacionresumen
						.next();
				// TODO: Constructores para cada tipo de datos
				this.idliquidacion = new BigDecimal(uCampos[0]);
				this.anio = new BigDecimal(uCampos[1]);
				this.mes = new BigDecimal(uCampos[2]);
				this.usuario = uCampos[3];
				this.comision_vc = new BigDecimal(uCampos[4]);
				this.total_vc = new BigDecimal(uCampos[5]);
				this.comision_vv = new BigDecimal(uCampos[6]);
				this.total_vv = new BigDecimal(uCampos[7]);
				this.comision_ve = new BigDecimal(uCampos[8]);
				this.total_ve = new BigDecimal(uCampos[9]);
				this.comision_vc_jf = new BigDecimal(uCampos[10]);
				this.total_vc_jf = new BigDecimal(uCampos[11]);
				this.comision_vv_jf = new BigDecimal(uCampos[12]);
				this.total_vv_jf = new BigDecimal(uCampos[13]);
				this.idempresa = new BigDecimal(uCampos[14]);
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosBacotmliquidacionresumen()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("bacotmliquidacionresumenAbm.jsp");
				return true;
			}
			
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (anio == null) {
						this.mensaje = "No se puede dejar vacio el campo anio ";
						return false;
					}
					if (mes == null) {
						this.mensaje = "No se puede dejar vacio el campo mes ";
						return false;
					}
					if (usuario == null) {
						this.mensaje = "No se puede dejar vacio el campo usuario ";
						return false;
					}
					if (comision_vc == null) {
						this.mensaje = "No se puede dejar vacio el campo comision_vc ";
						return false;
					}
					if (total_vc == null) {
						this.mensaje = "No se puede dejar vacio el campo total_vc ";
						return false;
					}
					if (comision_vv == null) {
						this.mensaje = "No se puede dejar vacio el campo comision_vv ";
						return false;
					}
					if (total_vv == null) {
						this.mensaje = "No se puede dejar vacio el campo total_vv ";
						return false;
					}
					if (comision_ve == null) {
						this.mensaje = "No se puede dejar vacio el campo comision_ve ";
						return false;
					}
					if (total_ve == null) {
						this.mensaje = "No se puede dejar vacio el campo total_ve ";
						return false;
					}
					if (comision_vc_jf == null) {
						this.mensaje = "No se puede dejar vacio el campo comision_vc_jf ";
						return false;
					}
					if (total_vc_jf == null) {
						this.mensaje = "No se puede dejar vacio el campo total_vc_jf ";
						return false;
					}
					if (comision_vv_jf == null) {
						this.mensaje = "No se puede dejar vacio el campo comision_vv_jf ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (usuario.trim().length() == 0) {
						this.mensaje = "No se puede dejar con longitud 0 el campo usuario  ";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosBacotmliquidacionresumen();
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
	public BigDecimal getIdliquidacion() {
		return idliquidacion;
	}

	public void setIdliquidacion(BigDecimal idliquidacion) {
		this.idliquidacion = idliquidacion;
	}

	public BigDecimal getAnio() {
		return anio;
	}

	public void setAnio(BigDecimal anio) {
		this.anio = anio;
	}

	public BigDecimal getMes() {
		return mes;
	}

	public void setMes(BigDecimal mes) {
		this.mes = mes;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	

	public SessionContext getContext() {
		return context;
	}

	public void setContext(SessionContext context) {
		this.context = context;
	}

	public BigDecimal getComision_vc() {
		return comision_vc;
	}

	public void setComision_vc(BigDecimal comision_vc) {
		this.comision_vc = comision_vc;
	}

	public BigDecimal getTotal_vc() {
		return total_vc;
	}

	public void setTotal_vc(BigDecimal total_vc) {
		this.total_vc = total_vc;
	}

	public BigDecimal getComision_vv() {
		return comision_vv;
	}

	public void setComision_vv(BigDecimal comision_vv) {
		this.comision_vv = comision_vv;
	}

	public BigDecimal getTotal_vv() {
		return total_vv;
	}

	public void setTotal_vv(BigDecimal total_vv) {
		this.total_vv = total_vv;
	}

	public BigDecimal getComision_ve() {
		return comision_ve;
	}

	public void setComision_ve(BigDecimal comision_ve) {
		this.comision_ve = comision_ve;
	}

	public BigDecimal getTotal_ve() {
		return total_ve;
	}

	public void setTotal_ve(BigDecimal total_ve) {
		this.total_ve = total_ve;
	}

	public BigDecimal getComision_vc_jf() {
		return comision_vc_jf;
	}

	public void setComision_vc_jf(BigDecimal comision_vc_jf) {
		this.comision_vc_jf = comision_vc_jf;
	}

	public BigDecimal getTotal_vc_jf() {
		return total_vc_jf;
	}

	public void setTotal_vc_jf(BigDecimal total_vc_jf) {
		this.total_vc_jf = total_vc_jf;
	}

	public BigDecimal getComision_vv_jf() {
		return comision_vv_jf;
	}

	public void setComision_vv_jf(BigDecimal comision_vv_jf) {
		this.comision_vv_jf = comision_vv_jf;
	}

	public BigDecimal getTotal_vv_jf() {
		return total_vv_jf;
	}

	public void setTotal_vv_jf(BigDecimal total_vv_jf) {
		this.total_vv_jf = total_vv_jf;
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
