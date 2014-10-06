/* 
   javabean para la entidad (Formulario): clientesUsuarioZona
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Sep 07 16:01:30 ART 2010 
   
   Para manejar la pagina: clientesUsuarioZonaFrm.jsp
      
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

public class BeanClientesUsuarioZonaFrm implements SessionBean, Serializable {

	private SessionContext context;

	static Logger log = Logger.getLogger(BeanClientesUsuarioZonaFrm.class);

	private String validar = "";

	private BigDecimal idusuario = new BigDecimal(-1);

	private String usuario = "";

	private BigDecimal idzona = new BigDecimal(-1);

	private String zona = "";

	private BigDecimal idempresa = new BigDecimal(-1);

	private String usuarioalt = "";

	private String usuarioact = "";

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private List listUsuarios = new ArrayList();

	private List listZonas = new ArrayList();

	public BeanClientesUsuarioZonaFrm() {
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
				this.mensaje = clientes.clientesUsuarioZonaCreate(
						this.idusuario, this.idzona, this.idempresa,
						this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clientes.clientesUsuarioZonaDelete(
						this.idusuario, this.idempresa);

			if (this.mensaje.equalsIgnoreCase("OK")) {

				this.mensaje = "Asociacion correcta para USUARIO: "
						+ this.usuario + " - ZONA: " + this.zona;

			}

			this.listUsuarios = Common.getClientes()
					.getClientesUsuarioZonaPendientes(this.idempresa);
			this.listZonas = Common.getClientes()
					.getClientesZonaUsuarioPendientes(this.idempresa);

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosClientesUsuarioZona() {
		try {
			Clientes clientes = Common.getClientes();
			List listClientesUsuarioZona = clientes.getClientesUsuarioZonaPK(
					this.idusuario, this.idempresa);
			Iterator iterClientesUsuarioZona = listClientesUsuarioZona
					.iterator();
			if (iterClientesUsuarioZona.hasNext()) {
				String[] uCampos = (String[]) iterClientesUsuarioZona.next();
				// TODO: Constructores para cada tipo de datos
				this.idusuario = new BigDecimal(uCampos[0]);
				this.idzona = new BigDecimal(uCampos[3]);
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosClientesUsuarioZona()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("clientesUsuarioZonaAbm.jsp");
				return true;
			}

			this.listUsuarios = Common.getClientes()
					.getClientesUsuarioZonaPendientes(this.idempresa);
			this.listZonas = Common.getClientes()
					.getClientesZonaUsuarioPendientes(this.idempresa);

			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {

					if (this.idusuario == null
							|| this.idusuario.longValue() < 1) {

						this.mensaje = "Es necesario seleccionar usuario.";
						return false;

					}

					if (this.idzona == null || this.idzona.longValue() < 1) {

						this.mensaje = "Es necesario seleccionar zona.";
						return false;

					}

					// 1. nulidad de campos
					// 2. len 0 para campos nulos
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

	public BigDecimal getIdzona() {
		return idzona;
	}

	public void setIdzona(BigDecimal idzona) {
		this.idzona = idzona;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
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

	public List getListUsuarios() {
		return listUsuarios;
	}

	public void setListUsuarios(List listUsuarios) {
		this.listUsuarios = listUsuarios;
	}

	public List getListZonas() {
		return listZonas;
	}

	public void setListZonas(List listZonas) {
		this.listZonas = listZonas;
	}

}
