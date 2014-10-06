/* 
   javabean para la entidad (Formulario): clientesZonasDepositos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Sep 02 17:40:20 GMT-03:00 2008 
   
   Para manejar la pagina: clientesZonasDepositosFrm.jsp
      
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

public class BeanClientesZonasDepositosFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanClientesZonasDepositosFrm.class);

	private String validar = "";

	private BigDecimal idzona = new BigDecimal(0);

	private String zona = "";

	private String[] codigo_dt = null;

	private BigDecimal idempresa = new BigDecimal(-1);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private List listDepositosZona = new ArrayList();

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanClientesZonasDepositosFrm() {
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
			Clientes clientesZonasDepositos = Common.getClientes();
			if (this.accion.equalsIgnoreCase("depositos"))

				this.mensaje = clientesZonasDepositos
						.clientesZonasDepositosGenerar(this.idzona,
								this.codigo_dt, this.idempresa, this.usuarioalt);
			if(this.mensaje.equalsIgnoreCase("OK")){
				this.mensaje = "Depositos asociados correctamente.";
			}

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosClientesZonasDepositos() {
		try {
			Clientes clientesZonasDepositos = Common.getClientes();
			this.listDepositosZona = clientesZonasDepositos
					.getClientesZonasDepositosOne(this.idzona, this.idempresa);
			Iterator iterClientesZonasDepositos = this.listDepositosZona
					.iterator();

			if (iterClientesZonasDepositos.hasNext()) {
				String[] uCampos = (String[]) iterClientesZonasDepositos.next();
				// TODO: Constructores para cada tipo de datos

			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}

		} catch (Exception e) {
			log.error("getDatosClientesZonasDepositos()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("clienteszonasAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {

				this.ejecutarSentenciaDML();

			}

			getDatosClientesZonasDepositos();

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

	public String[] getCodigo_dt() {
		return codigo_dt;
	}

	public void setCodigo_dt(String[] codigo_dt) {
		this.codigo_dt = codigo_dt;
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

	public List getListDepositosZona() {
		return listDepositosZona;
	}

	public void setListDepositosZona(List listDepositosZona) {
		this.listDepositosZona = listDepositosZona;
	}

}
