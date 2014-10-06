/* 
 javabean para la entidad (Formulario): clienteslistas
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Mon Dec 11 15:17:54 GMT-03:00 2006 
 
 Para manejar la pagina: clienteslistasFrm.jsp
 
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

public class BeanClienteslistasFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanClienteslistasFrm.class);

	private String validar = "";

	private BigDecimal idlista = BigDecimal.valueOf(-1);

	private String descri_lis = "";

	private BigDecimal idmoneda = BigDecimal.valueOf(0);
	private BigDecimal idempresa;

	private String moneda = "";

	private String coniva_lis = "";

	private String concuo_lis = "";

	private BigDecimal decima_lis = BigDecimal.valueOf(0);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanClienteslistasFrm() {
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
			Clientes clienteslistas = Common.getClientes();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = clienteslistas.clienteslistasCreate(
						this.descri_lis, this.idmoneda, this.coniva_lis,
						this.concuo_lis, this.decima_lis, this.usuarioalt,this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clienteslistas.clienteslistasUpdate(
						this.idlista, this.descri_lis, this.idmoneda,
						this.coniva_lis, this.concuo_lis, this.decima_lis,
						this.usuarioact,this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clienteslistas
						.clienteslistasDelete(this.idlista,this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosClienteslistas() {
		try {
			Clientes clienteslistas = Common.getClientes();
			List listClienteslistas = clienteslistas
					.getClienteslistasPK(this.idlista,this.idempresa);
			Iterator iterClienteslistas = listClienteslistas.iterator();
			if (iterClienteslistas.hasNext()) {
				String[] uCampos = (String[]) iterClienteslistas.next();
				// TODO: Constructores para cada tipo de datos
				this.idlista = BigDecimal.valueOf(Long.parseLong(uCampos[0]));
				this.descri_lis = uCampos[1];
				this.idmoneda = BigDecimal.valueOf(Long.parseLong(uCampos[2]));
				this.moneda = uCampos[3];
				this.coniva_lis = uCampos[4];
				this.concuo_lis = uCampos[5];
				this.decima_lis = BigDecimal
						.valueOf(Long.parseLong(uCampos[6]));
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosClienteslistas()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("clienteslistasAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (descri_lis == null) {
						this.mensaje = "No se puede dejar vacio el campo Descripcion ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (descri_lis.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Descripcion  ";
						return false;
					}

					if (moneda == null) {
						this.mensaje = "No se puede dejar vacio el campo Moneda ";
						return false;
					}

					if (moneda.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Moneda  ";
						return false;
					}

				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosClienteslistas();
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
	public BigDecimal getIdlista() {
		return idlista;
	}

	public void setIdlista(BigDecimal idlista) {
		this.idlista = idlista;
	}

	public String getDescri_lis() {
		return descri_lis;
	}

	public void setDescri_lis(String descri_lis) {
		this.descri_lis = descri_lis;
	}

	public BigDecimal getIdmoneda() {
		return idmoneda;
	}

	public void setIdmoneda(BigDecimal idmoneda) {
		this.idmoneda = idmoneda;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public String getConiva_lis() {
		return coniva_lis;
	}

	public void setConiva_lis(String coniva_lis) {
		this.coniva_lis = coniva_lis;
	}

	public String getConcuo_lis() {
		return concuo_lis;
	}

	public void setConcuo_lis(String concuo_lis) {
		this.concuo_lis = concuo_lis;
	}

	public BigDecimal getDecima_lis() {
		return decima_lis;
	}

	public void setDecima_lis(BigDecimal decima_lis) {
		this.decima_lis = decima_lis;
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
}
