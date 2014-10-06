/* 
 javabean para la entidad (Formulario): globalContadores
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Wed Jan 24 11:42:37 GMT-03:00 2007 
 
 Para manejar la pagina: globalContadoresFrm.jsp
 
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

public class BeanGlobalContadoresFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanGlobalContadoresFrm.class);

	private String validar = "";

	private BigDecimal idcontador = BigDecimal.valueOf(-1);
	
	private BigDecimal idempresa ;

	private String contador = "";

	private BigDecimal valor = BigDecimal.valueOf(0);

	private String descripcion = "";

	private String usuarioalt;

	private String usuarioact;

	private BigDecimal nrosucursal = BigDecimal.valueOf(0);

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanGlobalContadoresFrm() {
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
			General globalContadores = Common.getGeneral();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = globalContadores.globalContadoresCreate(
						this.contador, this.valor, this.descripcion,this.nrosucursal,this.idempresa,
						this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = globalContadores.globalContadoresUpdate(
						this.idcontador, this.contador, this.valor,
						this.descripcion,this.nrosucursal ,this.usuarioact,this.idempresa );
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = globalContadores
						.globalContadoresDelete(this.idcontador,this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosGlobalContadores() {
		try {
			General globalContadores = Common.getGeneral();
			List listGlobalContadores = globalContadores
					.getGlobalContadoresPK(this.idcontador,this.idempresa);
			Iterator iterGlobalContadores = listGlobalContadores.iterator();
			if (iterGlobalContadores.hasNext()) {
				String[] uCampos = (String[]) iterGlobalContadores.next();
				// TODO: Constructores para cada tipo de datos
				this.idcontador = BigDecimal
						.valueOf(Long.parseLong(uCampos[0]));
				this.contador = uCampos[1];
				this.valor = BigDecimal.valueOf(Long.parseLong(uCampos[2]));
				this.descripcion = uCampos[3];
				this.nrosucursal = new BigDecimal( (uCampos[4] == null ? "0" : uCampos[4]) );
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosGlobalContadores()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("globalContadoresAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (contador == null) {
						this.mensaje = "No se puede dejar vacio el campo contador ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (contador.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Contador  ";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosGlobalContadores();
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
	public BigDecimal getIdcontador() {
		return idcontador;
	}

	public void setIdcontador(BigDecimal idcontador) {
		this.idcontador = idcontador;
	}

	public String getContador() {
		return contador;
	}

	public void setContador(String contador) {
		this.contador = contador;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public BigDecimal getNrosucursal() {
		return nrosucursal;
	}

	public void setNrosucursal(BigDecimal nrosucursal) {
		this.nrosucursal = nrosucursal;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}
}
