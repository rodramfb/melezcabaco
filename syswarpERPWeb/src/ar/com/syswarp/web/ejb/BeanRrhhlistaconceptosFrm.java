/* 
   javabean para la entidad (Formulario): rrhhlistaconceptos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Nov 12 12:04:58 ART 2012 
   
   Para manejar la pagina: rrhhlistaconceptosFrm.jsp
      
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

public class BeanRrhhlistaconceptosFrm implements SessionBean, Serializable {
	private SessionContext context;
	static Logger log = Logger.getLogger(BeanRrhhlistaconceptosFrm.class);
	
	private String validar = "";
	
	private BigDecimal idlistaconcepto = new BigDecimal(-1);
	
	private BigDecimal idlista= new BigDecimal(-1);
	
	private String lista = "";
	
	private BigDecimal idconcepto= new BigDecimal(-1);
	
	private String concepto = "";
	
	private String usuarioalt ="";
	
	private String usuarioact ="";
	
	private String mensaje = "";
	
	private String volver = "";
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	private String accion = "";
	
	private BigDecimal idempresa = new BigDecimal(-1);

	public BeanRrhhlistaconceptosFrm() {
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
			RRHH rrhh = Common.getRrhh();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = rrhh.rrhhlistaconceptosCreate(this.idlista,
						this.idconcepto, this.usuarioalt, this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = rrhh.rrhhlistaconceptosUpdate(
						this.idlistaconcepto, this.idlista, this.idconcepto,
						this.usuarioact,this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = rrhh.rrhhlistaconceptosDelete(
						this.idlistaconcepto, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosRrhhlistaconceptos() {
		try {
			RRHH rrhh = Common.getRrhh();
			List listRrhhlistaconceptos = rrhh.getRrhhlistaconceptosPK(
					this.idlistaconcepto, this.idempresa);
			Iterator iterRrhhlistaconceptos = listRrhhlistaconceptos.iterator();
			if (iterRrhhlistaconceptos.hasNext()) {
				String[] uCampos = (String[]) iterRrhhlistaconceptos.next();
				// TODO: Constructores para cada tipo de datos
				this.idlistaconcepto = new BigDecimal(uCampos[0]);
				this.idlista = new BigDecimal(uCampos[1]);
				this.idconcepto = new BigDecimal(uCampos[2]);
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosRrhhlistaconceptos()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("rrhhlistaconceptosAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (idlista.longValue()<1)
					{
						this.mensaje = "Debe seleccionar alguna lista.";
						return false;
					}
					if (idconcepto.longValue()<0)
					{
						this.mensaje ="Debe seleccionar algun concepto ";
						return false;
					}
					// 2. len 0 para campos nulos
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosRrhhlistaconceptos();
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
	public BigDecimal getIdlistaconcepto() {
		return idlistaconcepto;
	}

	public void setIdlistaconcepto(BigDecimal idlistaconcepto) {
		this.idlistaconcepto = idlistaconcepto;
	}

	public BigDecimal getIdlista() {
		return idlista;
	}

	public void setIdlista(BigDecimal idlista) {
		this.idlista = idlista;
	}

	public BigDecimal getIdconcepto() {
		return idconcepto;
	}

	public void setIdconcepto(BigDecimal idconcepto) {
		this.idconcepto = idconcepto;
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

	public String getLista() {
		return lista;
	}

	public void setLista(String lista) {
		this.lista = lista;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	
	
}
