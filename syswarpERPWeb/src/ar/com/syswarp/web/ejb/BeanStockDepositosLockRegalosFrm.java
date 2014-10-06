/* 
   javabean para la entidad (Formulario): stockDepositosLockRegalos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Nov 24 14:30:23 ART 2010 
   
   Para manejar la pagina: stockDepositosLockRegalosFrm.jsp
      
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

public class BeanStockDepositosLockRegalosFrm implements SessionBean,
		Serializable {

	private SessionContext context;

	static Logger log = Logger
			.getLogger(BeanStockDepositosLockRegalosFrm.class);

	private String validar = "";

	private BigDecimal idlock = new BigDecimal(-1);

	private BigDecimal codigo_dt = new BigDecimal(-1);

	private String descrip_dt = "";

	private String fechadesde = "";

	private String fechahasta = "";

	private java.sql.Date fdesde;

	private java.sql.Date fhasta;

	private String comentarios = "";

	private BigDecimal idempresa = new BigDecimal(-1);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanStockDepositosLockRegalosFrm() {
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

			Stock stock = Common.getStock();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = stock.stockDepositosLockRegalosCreate(
						this.codigo_dt, this.fdesde, this.fhasta,
						this.comentarios, this.idempresa, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = stock.stockDepositosLockRegalosUpdate(
						this.idlock, this.codigo_dt, this.fdesde, this.fhasta,
						this.comentarios, this.idempresa, this.usuarioact);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosStockDepositosLockRegalos() {
		try {
			Stock stock = Common.getStock();
			List listStockDepositosLockRegalos = stock
					.getStockDepositosLockRegalosPK(this.idlock, this.idempresa);
			Iterator iterStockDepositosLockRegalos = listStockDepositosLockRegalos
					.iterator();
			if (iterStockDepositosLockRegalos.hasNext()) {
				String[] uCampos = (String[]) iterStockDepositosLockRegalos
						.next();
				// TODO: Constructores para cada tipo de datos
				this.idlock = new BigDecimal(uCampos[0]);
				this.codigo_dt = new BigDecimal(uCampos[1]);
				this.fechadesde = Common.setObjectToStrOrTime(
						java.sql.Date.valueOf(uCampos[2]), "JSDateToStr")
						.toString();
				this.fechahasta = Common.setObjectToStrOrTime(
						java.sql.Date.valueOf(uCampos[3]), "JSDateToStr")
						.toString();

			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosStockDepositosLockRegalos()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response
						.sendRedirect("stockDepositosLockRegalosAbm.jsp?codigo_dt="
								+ this.codigo_dt
								+ "&descrip_dt="
								+ this.descrip_dt);
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (codigo_dt == null) {
						this.mensaje = "No se puede dejar vacio el campo codigo_dt ";
						return false;
					}
					if (!Common.isFormatoFecha(this.fechadesde)
							|| !Common.isFechaValida(this.fechadesde)) {
						this.mensaje = "No se puede dejar vacio el campo fecha desde. ";
						return false;
					}

					if (!Common.isFormatoFecha(this.fechahasta)
							|| !Common.isFechaValida(this.fechahasta)) {
						this.mensaje = "No se puede dejar vacio el campo fecha hasta. ";
						return false;
					}

					this.fdesde = (java.sql.Date) Common.setObjectToStrOrTime(
							this.fechadesde, "StrToJSDate");
					this.fhasta = (java.sql.Date) Common.setObjectToStrOrTime(
							this.fechahasta, "StrToJSDate");

					if (fdesde.after(fhasta)) {
						this.mensaje = "Fecha desde no puede ser mayor a fecha hasta.";
						return false;
					}

					// 2. len 0 para campos nulos
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosStockDepositosLockRegalos();
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
	public BigDecimal getIdlock() {
		return idlock;
	}

	public void setIdlock(BigDecimal idlock) {
		this.idlock = idlock;
	}

	public BigDecimal getCodigo_dt() {
		return codigo_dt;
	}

	public void setCodigo_dt(BigDecimal codigo_dt) {
		this.codigo_dt = codigo_dt;
	}

	public String getDescrip_dt() {
		return descrip_dt;
	}

	public void setDescrip_dt(String descrip_dt) {
		this.descrip_dt = descrip_dt;
	}

	public String getFechadesde() {
		return fechadesde;
	}

	public void setFechadesde(String fechadesde) {
		this.fechadesde = fechadesde;
	}

	public String getFechahasta() {
		return fechahasta;
	}

	public void setFechahasta(String fechahasta) {
		this.fechahasta = fechahasta;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
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
