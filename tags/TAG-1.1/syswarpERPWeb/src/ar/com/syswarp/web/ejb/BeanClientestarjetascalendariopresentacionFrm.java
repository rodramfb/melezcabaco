/* 
   javabean para la entidad (Formulario): clientestarjetascalendariopresentacion
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Jul 23 15:17:01 ART 2008 
   
   Para manejar la pagina: clientestarjetascalendariopresentacionFrm.jsp
      
 */
package ar.com.syswarp.web.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Timestamp;

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

public class BeanClientestarjetascalendariopresentacionFrm implements
		SessionBean, Serializable {

	private SessionContext context;

	static Logger log = Logger
			.getLogger(BeanClientestarjetascalendariopresentacionFrm.class);

	private String validar = "";

	private BigDecimal codigo = BigDecimal.valueOf(-1);

	private BigDecimal idtarjetacredito = BigDecimal.valueOf(0);

	private String tarjetacredito = "";

	private BigDecimal anio = BigDecimal.valueOf(0);

	private BigDecimal mes = BigDecimal.valueOf(0);

	private String des_mes = "";

	private Timestamp fpresentaciondesde = null;

	private String fpresentaciondesdeStr = "";

	private Timestamp fpresentacionhasta = null;

	private String fpresentacionhastaStr = "";

	private BigDecimal idempresa;

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanClientestarjetascalendariopresentacionFrm() {
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
			Clientes clientestarjetascalendariopresentacion = Common
					.getClientes();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = clientestarjetascalendariopresentacion
						.clientestarjetascalendariopresentacionCreate(
								this.idtarjetacredito, this.anio, this.mes,
								this.fpresentaciondesde,
								this.fpresentacionhasta, this.idempresa,
								this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clientestarjetascalendariopresentacion
						.clientestarjetascalendariopresentacionUpdate(
								this.codigo, this.idtarjetacredito, this.anio,
								this.mes, this.fpresentaciondesde,
								this.fpresentacionhasta, this.idempresa,
								this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clientestarjetascalendariopresentacion
						.clientestarjetascalendariopresentacionDelete(
								this.codigo, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosClientestarjetascalendariopresentacion() {
		try {
			Clientes clientestarjetascalendariopresentacion = Common
					.getClientes();
			List listClientestarjetascalendariopresentacion = clientestarjetascalendariopresentacion
					.getClientestarjetascalendariopresentacionPK(this.codigo,
							this.idempresa);
			Iterator iterClientestarjetascalendariopresentacion = listClientestarjetascalendariopresentacion
					.iterator();
			if (iterClientestarjetascalendariopresentacion.hasNext()) {
				String[] uCampos = (String[]) iterClientestarjetascalendariopresentacion
						.next();
				// TODO: Constructores para cada tipo de datos
				this.codigo = BigDecimal.valueOf(Long.parseLong(uCampos[0]));
				this.idtarjetacredito = BigDecimal.valueOf(Long
						.parseLong(uCampos[1]));
				this.tarjetacredito = uCampos[2];
				this.anio = BigDecimal.valueOf(Long.parseLong(uCampos[3]));
				this.mes = BigDecimal.valueOf(Long.parseLong(uCampos[4]));
				this.des_mes = uCampos[5];
				this.fpresentaciondesde = Timestamp.valueOf(uCampos[6]);
				this.fpresentaciondesdeStr = Common.setObjectToStrOrTime(
						fpresentaciondesde, "JSTsToStr").toString();
				this.fpresentacionhasta = Timestamp.valueOf(uCampos[7]);
				this.fpresentacionhastaStr = Common.setObjectToStrOrTime(
						fpresentacionhasta, "JSTsToStr").toString();
				this.idempresa = BigDecimal.valueOf(Long.parseLong(uCampos[8]));
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosClientestarjetascalendariopresentacion()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response
						.sendRedirect("clientestarjetascalendariopresentacionAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (idtarjetacredito == null) {
						this.mensaje = "No se puede dejar vacio el campo idtarjetacredito ";
						return false;
					}
					if (anio == null) {
						this.mensaje = "No se puede dejar vacio el campo anio ";
						return false;
					}
					if (mes == null) {
						this.mensaje = "No se puede dejar vacio el campo mes ";
						return false;
					}

					if (tarjetacredito.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Tarjeta de Credito  ";
						return false;
					}

					if (anio.intValue() < Calendar.getInstance().get(
							Calendar.YEAR)) {
						this.mensaje = "Ano no puede ser menor al actual. ";
						return false;
					}

					if (des_mes.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Mes  ";
						return false;
					}

					this.fpresentaciondesde = (java.sql.Timestamp) Common
							.setObjectToStrOrTime(this.fpresentaciondesdeStr,
									"StrToJSTs");
					this.fpresentacionhasta = (java.sql.Timestamp) Common
							.setObjectToStrOrTime(this.fpresentacionhastaStr,
									"StrToJSTs");

					if (this.fpresentaciondesde == null) {
						this.mensaje = "Ingrese fecha desde.";
						return false;
					}

					if (this.fpresentacionhasta == null) {
						this.mensaje = "Ingrese fecha hasta.";
						return false;
					}
					
					
					Calendar cal = new GregorianCalendar();
				    cal.set(Calendar.HOUR_OF_DAY, 0);
				    cal.set(Calendar.MINUTE, 0);
				    cal.set(Calendar.SECOND, 0);
				    cal.set(Calendar.MILLISECOND, 0);

				    
					if( new Date (this.fpresentaciondesde.getTime()).before( cal.getTime())){
						this.mensaje = "Fecha desde no puede ser menor a fecha actual.";
						return false;
					}
					
					if (this.fpresentaciondesde.after(this.fpresentacionhasta)) {
						this.mensaje = "Fecha desde no puede ser mayor a fecha hasta.";
						return false;
					}

					// 2. len 0 para campos nulos
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosClientestarjetascalendariopresentacion();
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
	public BigDecimal getCodigo() {
		return codigo;
	}

	public void setCodigo(BigDecimal codigo) {
		this.codigo = codigo;
	}

	public BigDecimal getIdtarjetacredito() {
		return idtarjetacredito;
	}

	public void setIdtarjetacredito(BigDecimal idtarjetacredito) {
		this.idtarjetacredito = idtarjetacredito;
	}

	public BigDecimal getAnio() {
		return anio;
	}

	public void setAnio(BigDecimal anio) {
		this.anio = anio;
	}

	public BigDecimal getMes() {
		return mes;
	}

	public void setMes(BigDecimal mes) {
		this.mes = mes;
	}

	public Timestamp getFpresentaciondesde() {
		return fpresentaciondesde;
	}

	public void setFpresentaciondesde(Timestamp fpresentaciondesde) {
		this.fpresentaciondesde = fpresentaciondesde;
	}

	public Timestamp getFpresentacionhasta() {
		return fpresentacionhasta;
	}

	public void setFpresentacionhasta(Timestamp fpresentacionhasta) {
		this.fpresentacionhasta = fpresentacionhasta;
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

	public void setFpresentaciondesdeStr(String fpresentaciondesdeStr) {
		this.fpresentaciondesdeStr = fpresentaciondesdeStr;

	}

	public void setFpresentacionhastaStr(String fpresentacionhastaStr) {
		this.fpresentacionhastaStr = fpresentacionhastaStr;
	}

	public String getTarjetacredito() {
		return tarjetacredito;
	}

	public void setTarjetacredito(String tarjetacredito) {
		this.tarjetacredito = tarjetacredito;
	}

	public String getDes_mes() {
		return des_mes;
	}

	public void setDes_mes(String des_mes) {
		this.des_mes = des_mes;
	}

	public String getFpresentaciondesdeStr() {
		return fpresentaciondesdeStr;
	}

	public String getFpresentacionhastaStr() {
		return fpresentacionhastaStr;
	}

}
