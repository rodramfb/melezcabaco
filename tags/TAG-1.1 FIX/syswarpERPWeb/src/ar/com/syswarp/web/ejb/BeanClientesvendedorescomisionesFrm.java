/* 
   javabean para la entidad (Formulario): clientesvendedorescomisiones
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Oct 26 10:25:12 ART 2012 
   
   Para manejar la pagina: clientesvendedorescomisionesFrm.jsp
      
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

public class BeanClientesvendedorescomisionesFrm implements SessionBean,
		Serializable {
	private SessionContext context;
	static Logger log = Logger
			.getLogger(BeanClientesvendedorescomisionesFrm.class);
	private String validar = "";

	private int idcomision = -1;

	private String descripcion = "";

	private String cantsociodesde = "";

	private String cantsociohasta = "";

	private String porcdeserdesde = "";

	private String porcdeserhasta = "";

	private String valorasociacion = "";

	private String porccartera = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private String tipo = "V";

	private BigDecimal idempresa = new BigDecimal(-1);

	public BeanClientesvendedorescomisionesFrm() {
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
				this.mensaje = clientes.clientesvendedorescomisionesCreate(
						this.descripcion, new BigDecimal(this.cantsociodesde).doubleValue(),
						new BigDecimal(this.cantsociohasta).doubleValue(), new BigDecimal(
								this.porcdeserdesde).doubleValue(), new BigDecimal(
								this.porcdeserhasta).doubleValue(), new BigDecimal(
								this.valorasociacion).doubleValue(), new BigDecimal(
								this.porccartera).doubleValue(), this.usuarioalt, this.tipo);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clientes.clientesvendedorescomisionesUpdate(
						this.idcomision, this.descripcion, new BigDecimal(
								this.cantsociodesde).doubleValue(), new BigDecimal(
								this.cantsociohasta).doubleValue(), new BigDecimal(
								this.porcdeserdesde).doubleValue(), new BigDecimal(
								this.porcdeserhasta).doubleValue(), new BigDecimal(
								this.valorasociacion).doubleValue(), new BigDecimal(
								this.porccartera).doubleValue(), this.usuarioact, this.tipo);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clientes
						.clientesvendedorescomisionesDelete(this.idcomision);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosClientesvendedorescomisiones() {
		try {
			Clientes clientes = Common.getClientes();
			List listClientesvendedorescomisiones = clientes
					.getClientesvendedorescomisionesPK(this.idcomision);
			Iterator iterClientesvendedorescomisiones = listClientesvendedorescomisiones
					.iterator();
			if (iterClientesvendedorescomisiones.hasNext()) {
				String[] uCampos = (String[]) iterClientesvendedorescomisiones
						.next();
				// TODO: Constructores para cada tipo de datos
				this.idcomision = Integer.parseInt(uCampos[0]);
				this.descripcion = uCampos[1];
				this.cantsociodesde = uCampos[2];
				this.cantsociohasta = uCampos[3];
				this.porcdeserdesde = uCampos[4];
				this.porcdeserhasta = uCampos[5];
				this.valorasociacion = uCampos[6];
				this.porccartera = uCampos[7];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosClientesvendedorescomisiones()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("clientesvendedorescomisionesAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					if (descripcion.trim().length() == 0) {
						this.mensaje = "No puede dejar vacio el campo descripcion  ";
						return false;
					}
					if (cantsociodesde.equalsIgnoreCase(""))
					{
						this.mensaje ="No puede dejar vacio el campo socio desde";
						return false;
					}
					if (!Common.esNumerico(cantsociodesde)) {
						this.mensaje   = "Ingrese numeros validos para el socio desde";
						return false;
					}
					if (cantsociohasta.equalsIgnoreCase(""))
					{
						this.mensaje  ="No puede dejar vacio el campo socio hasta";
						return false;
					}
					if (!Common.esNumerico(cantsociohasta)) {
						this.mensaje   = "Ingrese numeros validos para el socio hasta";
						return false;
					}
					if (porcdeserdesde.equalsIgnoreCase(""))
					{
						this.mensaje ="No puede dejar vacio el campo % desde";
						return false;
					}
					if (!Common.esNumerico(porcdeserdesde)) {
						this.mensaje   = "Ingrese numeros validos para el % desde";
						return false;
					}
					if(porcdeserhasta.equalsIgnoreCase(""))
					{
						this.mensaje ="No puede dejar vacio el campo % hasta";
						return false;
					}
					if (!Common.esNumerico(porcdeserhasta)) {
						this.mensaje   = "Ingrese numeros validos para el % hasta";
						return false;
					}
					if (valorasociacion.equalsIgnoreCase(""))
					{
						this.mensaje ="No puede dejar vacio el campo valor de asociacion";
						return false;
					}
					if (!Common.esNumerico(valorasociacion)) {
						this.mensaje   = "Ingrese numeros validos para el valor de la asociacion";
						return false;
					}
					if (porccartera.equalsIgnoreCase(""))
					{
						this.mensaje ="No puede dejar vacio el % de cartera";
						return false;
					}
					if (!Common.esNumerico(porccartera)) {
						this.mensaje   = "Ingrese numeros validos para el % de cartera";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosClientesvendedorescomisiones();
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
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCantsociodesde() {
		return cantsociodesde;
	}

	public void setCantsociodesde(String cantsociodesde) {
		this.cantsociodesde = cantsociodesde;
	}

	public String getCantsociohasta() {
		return cantsociohasta;
	}

	public void setCantsociohasta(String cantsociohasta) {
		this.cantsociohasta = cantsociohasta;
	}

	public String getPorcdeserdesde() {
		return porcdeserdesde;
	}

	public void setPorcdeserdesde(String porcdeserdesde) {
		this.porcdeserdesde = porcdeserdesde;
	}

	public String getPorcdeserhasta() {
		return porcdeserhasta;
	}

	public void setPorcdeserhasta(String porcdeserhasta) {
		this.porcdeserhasta = porcdeserhasta;
	}

	public String getValorasociacion() {
		return valorasociacion;
	}

	public void setValorasociacion(String valorasociacion) {
		this.valorasociacion = valorasociacion;
	}

	public String getPorccartera() {
		return porccartera;
	}

	public void setPorccartera(String porccartera) {
		this.porccartera = porccartera;
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

	public int getIdcomision() {
		return idcomision;
	}

	public void setIdcomision(int idcomision) {
		this.idcomision = idcomision;
	}

}
