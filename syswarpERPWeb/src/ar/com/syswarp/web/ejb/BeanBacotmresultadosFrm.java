/* 
 javabean para la entidad (Formulario): bacotmresultados
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Wed Apr 11 11:16:36 GMT-03:00 2007 
 
 Para manejar la pagina: bacotmresultadosFrm.jsp
 
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

public class BeanBacotmresultadosFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanBacotmresultadosFrm.class);

	private String validar = "";

	private BigDecimal idresultado = BigDecimal.valueOf(-1);

	private BigDecimal idempresa;

	private String resultado = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private String[] idmotivos = new String[] {};

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private List listMotivos = new ArrayList();

	public BeanBacotmresultadosFrm() {
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
			Clientes bacotmresultados = Common.getClientes();

			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = bacotmresultados.bacotmresultadosCreate(
						this.resultado, this.idmotivos, this.idempresa,
						this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = bacotmresultados.bacotmresultadosUpdate(
						this.idresultado, this.resultado, this.idmotivos,
						this.idempresa, this.usuarioact);

			if (Common.esNumerico(this.mensaje)) {

				if (this.accion.equalsIgnoreCase("ALTA")) {
					this.mensaje = "Se generó correctamente el resultado de llamados nro.: "
							+ this.mensaje;
					this.accion = "modificacion";
				} else {
					this.mensaje = "Se actualizó correctamente el resultado de llamados nro.: "
							+ this.mensaje;
				}

			}

			this.listMotivos = Common.getClientes()
					.getResultadosMotivosAsociar(this.idresultado,
							this.idempresa);

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}

	}

	public void getDatosBacotmresultados() {
		try {
			Clientes bacotmresultados = Common.getClientes();
			List listBacotmresultados = bacotmresultados.getBacotmresultadosPK(
					this.idresultado, this.idempresa);
			Iterator iterBacotmresultados = listBacotmresultados.iterator();
			if (iterBacotmresultados.hasNext()) {
				String[] uCampos = (String[]) iterBacotmresultados.next();
				// TODO: Constructores para cada tipo de datos
				this.idresultado = BigDecimal.valueOf(Long
						.parseLong(uCampos[0]));
				this.resultado = uCampos[1];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosBacotmresultados()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {

			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("bacotmresultadosAbm.jsp");
				return true;
			}

			this.listMotivos = Common.getClientes()
					.getResultadosMotivosAsociar(this.idresultado,
							this.idempresa);

			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					// 2. len 0 para campos nulos
					if (resultado.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Resultado  ";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosBacotmresultados();
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
	public BigDecimal getIdresultado() {
		return idresultado;
	}

	public void setIdresultado(BigDecimal idresultado) {
		this.idresultado = idresultado;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
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

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public String[] getIdmotivos() {
		return idmotivos;
	}

	public void setIdmotivos(String[] idmotivos) {
		this.idmotivos = idmotivos;
	}

	public List getListMotivos() {
		return listMotivos;
	}

	public void setListMotivos(List listMotivos) {
		this.listMotivos = listMotivos;
	}

}
