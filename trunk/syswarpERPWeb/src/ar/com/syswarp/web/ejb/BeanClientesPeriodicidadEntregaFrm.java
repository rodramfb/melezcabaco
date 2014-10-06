/* 
   javabean para la entidad (Formulario): clientesPeriodicidadEntrega
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jul 31 12:17:09 GMT-03:00 2008 
   
   Para manejar la pagina: clientesPeriodicidadEntregaFrm.jsp
      
 */
package ar.com.syswarp.web.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import java.math.*;
import java.util.*;
import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanClientesPeriodicidadEntregaFrm implements SessionBean,
		Serializable {
	private SessionContext context;
	static Logger log = Logger
			.getLogger(BeanClientesPeriodicidadEntregaFrm.class);

	private String validar = "";

	private BigDecimal idcliente = new BigDecimal(-1);

	private String[] idmes = new String[] {};

	private BigDecimal idempresa = new BigDecimal(0);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private List listClientesPeriodicidadEntrega = new ArrayList();

	public BeanClientesPeriodicidadEntregaFrm() {
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
			Clientes clientesPeriodicidadEntrega = Common.getClientes();

			if (this.accion.equalsIgnoreCase("alta")) {

				request.getSession().setAttribute("idmes", this.idmes);
				this.mensaje = "Perioricidad Generada Correctamente.";
				
			} else if (this.accion.equalsIgnoreCase("modificacion")) {

				this.mensaje = clientesPeriodicidadEntrega
						.clientesPeriodicidadEntregaGenerar(idcliente, idmes,
								idempresa, usuarioact);
				
				if (this.mensaje.equalsIgnoreCase("OK"))
					this.mensaje = "Perioricidad Generada Correctamente.";

			}

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosClientesPeriodicidadEntrega() {
		try {
			Clientes clientesPeriodicidadEntrega = Common.getClientes();
			List listClientesPeriodicidadEntrega = clientesPeriodicidadEntrega
					.getClientesPeriodicidadEntregaOne(this.idcliente,
							this.idempresa);
			Iterator iterClientesPeriodicidadEntrega = listClientesPeriodicidadEntrega
					.iterator();
			if (iterClientesPeriodicidadEntrega.hasNext()) {
				String[] uCampos = (String[]) iterClientesPeriodicidadEntrega
						.next();
				// TODO: Constructores para cada tipo de datos
				// this.idcliente = new BigDecimal(uCampos[0]);
				// this.idmes = new BigDecimal(uCampos[1]);

			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosClientesPeriodicidadEntrega()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {

			Clientes clientesPeriodicidadEntrega = Common.getClientes();

			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("clientesPeriodicidadEntregaAbm.jsp");
				return true;
			}

			if (!this.validar.equalsIgnoreCase("")) {

				if (this.idmes == null || this.idmes.length == 0) {
					this.mensaje = "Seleccione por lo menos un mes de entrega.";
					// return false;
				} else
					this.ejecutarSentenciaDML();

			}

			this.listClientesPeriodicidadEntrega = clientesPeriodicidadEntrega
					.getClientesPeriodicidadEntregaOne(this.idcliente,
							this.idempresa);

			if (this.request.getSession().getAttribute("idmes") != null
					&& this.accion.equalsIgnoreCase("alta")) {

				Hashtable htMesSelected = new Hashtable();
				this.idmes = (String[]) request.getSession().getAttribute(
						"idmes");
				for (int j = 0; j < this.idmes.length; j++) {
					htMesSelected.put(idmes[j], idmes[j]);
				}

				for (int f = 0; f < this.listClientesPeriodicidadEntrega.size(); f++) {
					String[] datos = (String[]) this.listClientesPeriodicidadEntrega
							.get(f);
					if (htMesSelected.containsKey(datos[0])) {
						datos[2] = "1";
						this.listClientesPeriodicidadEntrega.set(f, datos);
					}
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
	public BigDecimal getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(BigDecimal idcliente) {
		this.idcliente = idcliente;
	}

	public String[] getIdmes() {
		return idmes;
	}

	public void setIdmes(String[] idmes) {
		this.idmes = idmes;
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

	public List getListClientesPeriodicidadEntrega() {
		return listClientesPeriodicidadEntrega;
	}

}
