/* 
 javabean para la entidad (Formulario): clientetarjetascreditomarcas
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Jan 23 19:22:37 GMT-03:00 2007 
 
 Para manejar la pagina: clientetarjetascreditomarcasFrm.jsp
 
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

public class BeanClientetarjetascreditomarcasFrm implements SessionBean,
		Serializable {
	private SessionContext context;

	static Logger log = Logger
			.getLogger(BeanClientetarjetascreditomarcasFrm.class);

	private String validar = "";

	private BigDecimal idtarjetacredito = BigDecimal.valueOf(-1);
	
	private BigDecimal coddigitovermarca = BigDecimal.valueOf(0);
	
	private BigDecimal idempresa ;

	private String tarjetacredito = "";

	private String formato = "";

	private String metodoasociado = "";

	private String filtroarchivo = "";

	private String formulavalidacion = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanClientetarjetascreditomarcasFrm() {
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

	public BigDecimal getCoddigitovermarca() {
		return coddigitovermarca;
	}

	public void setCoddigitovermarca(BigDecimal coddigitovermarca) {
		this.coddigitovermarca = coddigitovermarca;
	}

	public void ejecutarSentenciaDML() {
		try {
			Clientes clientetarjetascreditomarcas = Common.getClientes();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = clientetarjetascreditomarcas
						.clientetarjetascreditomarcasCreate(
								this.tarjetacredito, this.formato,
								this.metodoasociado, this.filtroarchivo,
								this.formulavalidacion, this.usuarioalt,this.idempresa,this.coddigitovermarca);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clientetarjetascreditomarcas
						.clientetarjetascreditomarcasUpdate(
								this.idtarjetacredito, this.tarjetacredito,
								this.formato, this.metodoasociado,
								this.filtroarchivo, this.formulavalidacion,
								this.usuarioact,this.idempresa, this.coddigitovermarca);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clientetarjetascreditomarcas
						.clientetarjetascreditomarcasDelete(this.idtarjetacredito,this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosClientetarjetascreditomarcas() {
		try {
			Clientes clientetarjetascreditomarcas = Common.getClientes();
			List listClientetarjetascreditomarcas = clientetarjetascreditomarcas
					.getClientetarjetascreditomarcasPK(this.idtarjetacredito,this.idempresa);
			Iterator iterClientetarjetascreditomarcas = listClientetarjetascreditomarcas
					.iterator();
			if (iterClientetarjetascreditomarcas.hasNext()) {
				String[] uCampos = (String[]) iterClientetarjetascreditomarcas
						.next();
				// TODO: Constructores para cada tipo de datos
				this.idtarjetacredito = BigDecimal.valueOf(Long
						.parseLong(uCampos[0]));
				this.tarjetacredito = uCampos[1];
				this.formato = uCampos[2];
				this.metodoasociado = uCampos[3];
				this.filtroarchivo = uCampos[4];
				this.formulavalidacion = uCampos[5];
				this.coddigitovermarca = BigDecimal.valueOf(Long
						.parseLong(uCampos[6]));
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosClientetarjetascreditomarcas()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("clientetarjetascreditomarcasAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (tarjetacredito == null) {
						this.mensaje = "No se puede dejar vacio el campo tarjetacredito ";
						return false;
					}
					if (formato == null) {
						this.mensaje = "No se puede dejar vacio el campo formato ";
						return false;
					}
					if (metodoasociado == null) {
						this.mensaje = "No se puede dejar vacio el campo metodoasociado ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (tarjetacredito.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Tarjeta Credito  ";
						return false;
					}
					if (formato.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Formato  ";
						return false;
					}
					if (metodoasociado.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Metodo Asociado  ";
						return false;
					}
					if (filtroarchivo.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Filtro Archivo  ";
						return false;
					}
					if (formulavalidacion.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Formula Validacion  ";
						return false;
					}
					
					if (coddigitovermarca == null) {
						this.mensaje = "No se puede dejar vacio el campo Codigo digito verificador de la marca ";
						return false;
					}
					

				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosClientetarjetascreditomarcas();
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
	public BigDecimal getIdtarjetacredito() {
		return idtarjetacredito;
	}

	public void setIdtarjetacredito(BigDecimal idtarjetacredito) {
		this.idtarjetacredito = idtarjetacredito;
	}

	public String getTarjetacredito() {
		return tarjetacredito;
	}

	public void setTarjetacredito(String tarjetacredito) {
		this.tarjetacredito = tarjetacredito;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public String getMetodoasociado() {
		return metodoasociado;
	}

	public void setMetodoasociado(String metodoasociado) {
		this.metodoasociado = metodoasociado;
	}

	public String getFiltroarchivo() {
		return filtroarchivo;
	}

	public void setFiltroarchivo(String filtroarchivo) {
		this.filtroarchivo = filtroarchivo;
	}

	public String getFormulavalidacion() {
		return formulavalidacion;
	}

	public void setFormulavalidacion(String formulavalidacion) {
		this.formulavalidacion = formulavalidacion;
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
