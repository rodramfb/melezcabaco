/* 
 javabean para la entidad (Formulario): campos
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Jul 04 15:46:55 GMT-03:00 2006 
 
 Para manejar la pagina: camposFrm.jsp
 
 */
package ar.com.syswarp.web.ejb.report;

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

public class BeanCamposFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanCamposFrm.class);

	private String validar = "";

	private BigDecimal idcampo = BigDecimal.valueOf(-1);

	private String campo = "";

	private BigDecimal idtabla = BigDecimal.valueOf(-1);

	private String titulo = "";

	private BigDecimal orden = BigDecimal.valueOf(0);

	private String clase_css = "";

	private String lenght_col = "";

	private String comentario = "";

	private String usuarioalt = "";

	private String usuarioact = "";

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private List tablasList = new ArrayList();

	private Hashtable htTablas = new Hashtable();

	private Iterator iterTablas = null;

	public BeanCamposFrm() {
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
			Report reporting = Common.getReport();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = reporting.camposCreate(this.campo, this.idtabla,
						this.titulo, this.orden, this.clase_css,
						this.lenght_col, this.comentario, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = reporting.camposUpdate(this.idcampo, this.campo,
						this.idtabla, this.titulo, this.orden, this.clase_css,
						this.lenght_col, this.comentario, this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = reporting.camposDelete(this.idcampo);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosCampos() {
		try {
			Report reporting = Common.getReport();
			List listCampos = reporting.getCamposPK(this.idcampo);
			Iterator iterCampos = listCampos.iterator();
			if (iterCampos.hasNext()) {
				String[] uCampos = (String[]) iterCampos.next();
				// TODO: Constructores para cada tipo de datos
				this.idcampo = BigDecimal.valueOf(Long.parseLong(uCampos[0]));
				this.campo = uCampos[1];
				this.idtabla = BigDecimal.valueOf(Long.parseLong(uCampos[2]));
				this.titulo = uCampos[3];
				this.orden = BigDecimal.valueOf(Long.parseLong(uCampos[4]));
				this.clase_css = uCampos[5];
				this.lenght_col = uCampos[6];
				this.comentario = uCampos[7];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosCampos()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		Report reporting = Common.getReport();
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("camposAbm.jsp");
				return true;
			}

			this.tablasList = reporting.getTablasAll();
			this.iterTablas = this.tablasList.iterator();
			while (iterTablas.hasNext()) {
				String[] tCampos = (String[]) iterTablas.next();
				this.htTablas.put(tCampos[0], tCampos[1]);
			}

			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (campo == null) {
						this.mensaje = "No se puede dejar vacio el campo campo ";
						return false;
					}
					if (idtabla == null) {
						this.mensaje = "No se puede dejar vacio el campo idtabla ";
						return false;
					}
					if (titulo == null) {
						this.mensaje = "No se puede dejar vacio el campo titulo ";
						return false;
					}
					if (orden == null) {
						this.mensaje = "No se puede dejar vacio el campo orden ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (campo.trim().length() == 0) {
						this.mensaje = "No se puede dejar con longitud 0 el campo campo  ";
						return false;
					}
					if (titulo.trim().length() == 0) {
						this.mensaje = "No se puede dejar con longitud 0 el campo titulo  ";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosCampos();
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
	public BigDecimal getIdcampo() {
		return idcampo;
	}

	public void setIdcampo(BigDecimal idcampo) {
		this.idcampo = idcampo;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public BigDecimal getIdtabla() {
		return idtabla;
	}

	public void setIdtabla(BigDecimal idtabla) {
		this.idtabla = idtabla;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public BigDecimal getOrden() {
		return orden;
	}

	public void setOrden(BigDecimal orden) {
		this.orden = orden;
	}

	public String getClase_css() {
		return clase_css;
	}

	public void setClase_css(String clase_css) {
		this.clase_css = clase_css;
	}

	public String getLenght_col() {
		return lenght_col;
	}

	public void setLenght_col(String lenght_col) {
		this.lenght_col = lenght_col;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
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

	public Hashtable getHtTablas() {
		return htTablas;
	}

	public void setHtTablas(Hashtable htTablas) {
		this.htTablas = htTablas;
	}
}
