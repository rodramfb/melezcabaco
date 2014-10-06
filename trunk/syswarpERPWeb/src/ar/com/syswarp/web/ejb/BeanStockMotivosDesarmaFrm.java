/* 
 javabean para la entidad (Formulario): stockMotivosDesarma
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Thu Jan 03 14:18:59 ART 2008 
 
 Para manejar la pagina: stockMotivosDesarmaFrm.jsp
 
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

public class BeanStockMotivosDesarmaFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanStockMotivosDesarmaFrm.class);

	private String validar = "";

	private BigDecimal idmotivodesarma = new BigDecimal(-1);

	private String motivodesarma = "";

	private BigDecimal idempresa = new BigDecimal(0);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanStockMotivosDesarmaFrm() {
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
			Stock stockMotivosDesarma = Common.getStock();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = stockMotivosDesarma.stockMotivosDesarmaCreate(
						this.motivodesarma, this.idempresa, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = stockMotivosDesarma.stockMotivosDesarmaUpdate(
						this.idmotivodesarma, this.motivodesarma,
						this.idempresa, this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = stockMotivosDesarma
						.stockMotivosDesarmaDelete(this.idmotivodesarma);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosStockMotivosDesarma() {
		try {
			Stock stockMotivosDesarma = Common.getStock();
			List listStockMotivosDesarma = stockMotivosDesarma
					.getStockMotivosDesarmaPK(this.idmotivodesarma,
							this.idempresa);
			Iterator iterStockMotivosDesarma = listStockMotivosDesarma
					.iterator();
			if (iterStockMotivosDesarma.hasNext()) {
				String[] uCampos = (String[]) iterStockMotivosDesarma.next();
				// TODO: Constructores para cada tipo de datos
				this.idmotivodesarma = BigDecimal.valueOf(Long
						.parseLong(uCampos[0]));
				this.motivodesarma = uCampos[1];
				this.idempresa = BigDecimal.valueOf(Long.parseLong(uCampos[2]));
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosStockMotivosDesarma()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("stockMotivosDesarmaAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					// 2. len 0 para campos nulos

					if (motivodesarma == null) {
						this.mensaje = "No se puede dejar vacio el campo Motivo Desarmado ";
						return false;
					}

				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosStockMotivosDesarma();
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
	public BigDecimal getIdmotivodesarma() {
		return idmotivodesarma;
	}

	public void setIdmotivodesarma(BigDecimal idmotivodesarma) {
		this.idmotivodesarma = idmotivodesarma;
	}

	public String getMotivodesarma() {
		return motivodesarma;
	}

	public void setMotivodesarma(String motivodesarma) {
		this.motivodesarma = motivodesarma;
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
