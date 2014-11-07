/* 
   javabean para la entidad (Formulario): bacoTmLlamados
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Mar 12 08:58:18 GYT 2009 
   
   Para manejar la pagina: bacoTmLlamadosFrm.jsp
      
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

public class BeanBacoTmLlamadosFrm implements SessionBean, Serializable {

	private SessionContext context;

	static Logger log = Logger.getLogger(BeanBacoTmLlamadosFrm.class);

	private String validar = "";

	private BigDecimal idllamado = new BigDecimal(-1);

	private BigDecimal idcliente = new BigDecimal(-1);

	private String cliente = "";

	private BigDecimal idcampacabe = new BigDecimal(-1);

	private String campacabe = "";

	private String sociotelefono = "";
	
	
	private String hora = "-1";
	private String minutos = "-1";



	private BigDecimal idpedidoasoc = new BigDecimal(-1);

	private BigDecimal idresultado = new BigDecimal(-1);

	private BigDecimal idmotivo = new BigDecimal(-1);

	private String observaciones = "";

	private String fecharellamada = "";

	private BigDecimal idempresa = new BigDecimal(-1);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private List listResultado = new ArrayList();

	private List listMotivo = new ArrayList();

	public BeanBacoTmLlamadosFrm() {
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
			Clientes bacoTmLlamados = Common.getClientes();
			if (this.accion.equalsIgnoreCase("alta"))
				this.idcampacabe  = this.idcampacabe;			        
			    String  fechaHora = null;
			    if (this.fecharellamada != null && !this.fecharellamada.trim().equalsIgnoreCase(""))
			    	  fechaHora = this.fecharellamada + " " + this.hora + ":" + this.minutos;
				this.mensaje = bacoTmLlamados.bacoTmLlamadosCreate(GeneralBean
						.setNull(this.idcliente, 0), GeneralBean.setNull(
						this.idcampacabe, 0), this.sociotelefono, GeneralBean
						.setNull(this.idpedidoasoc, 0), GeneralBean.setNull(
						this.idresultado, 0), GeneralBean.setNull(
						this.idmotivo, 0), this.observaciones,
						(java.sql.Timestamp) Common.setObjectToStrOrTime( fechaHora, "strToJSTsWithHM") ,
						this.idempresa, this.usuarioalt);
                        
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosBacoTmLlamados() {
		try {
			Clientes bacoTmLlamados = Common.getClientes();
			List listBacoTmLlamados = bacoTmLlamados.getBacoTmLlamadosPK(
					this.idllamado, this.idempresa);
			Iterator iterBacoTmLlamados = listBacoTmLlamados.iterator();
			if (iterBacoTmLlamados.hasNext()) {
				String[] uCampos = (String[]) iterBacoTmLlamados.next();
				// TODO: Constructores para cada tipo de datos
				this.idllamado = new BigDecimal(uCampos[0]);
				this.idcliente = new BigDecimal(uCampos[1]);
				this.idcampacabe = new BigDecimal(uCampos[2]);
				this.sociotelefono = uCampos[3];
				this.idpedidoasoc = new BigDecimal(uCampos[4]);
				this.idresultado = new BigDecimal(uCampos[5]);
				this.idmotivo = new BigDecimal(uCampos[6]);
				this.observaciones = uCampos[7];
				this.fecharellamada = (String) Common.setObjectToStrOrTime(
						uCampos[8] == null ? null : java.sql.Date
								.valueOf(uCampos[8]), "JSDateToStr");
				this.idempresa = new BigDecimal(uCampos[9]);
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosBacoTmLlamados()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {

			this.sociotelefono = Common.setNotNull(this.sociotelefono).equals(
					"") ? this.cliente : this.sociotelefono;

			this.listResultado = Common.getClientes().getBacotmresultadosAll(
					100, 0, this.idempresa);

			this.listMotivo = Common.getClientes()
					.getBacoTmResultadosMotivosAll(this.idempresa);

			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {

					// 1. nulidad de campos
					if (idcliente == null || idcliente.longValue() < 0) {
						this.mensaje = "Es necesario seleccionar cliente.";
						return false;
					}

					/*
					if (idcampacabe == null || idcampacabe.longValue() < 0) {
						this.mensaje = "Es necesario que haya una campa�a definida. ";
						return false;
					}
                    */

					if (idresultado == null || idresultado.longValue() < 0) {
						this.mensaje = "No se puede dejar vacio el campo resultado ";
						return false;
					}
					
					if(idresultado.longValue() == 12){
						
						if(this.fecharellamada.equals("")){
							
							this.mensaje = "Es necesario ingresar fecha de rellamada.";
							return false;
							
						}
						
						if(this.hora.equals("-1") || this.minutos.equals("-1")){
							
							this.mensaje = "Es necesario ingresar hora de rellamada.";
							return false;
							
						}
						
						
					}

					// 2. len 0 para campos nulos
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosBacoTmLlamados();
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
	public BigDecimal getIdllamado() {
		return idllamado;
	}

	public void setIdllamado(BigDecimal idllamado) {
		this.idllamado = idllamado;
	}

	public BigDecimal getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(BigDecimal idcliente) {
		this.idcliente = idcliente;
	}

	public BigDecimal getIdcampacabe() {
		return idcampacabe;
	}

	public void setIdcampacabe(BigDecimal idcampacabe) {
		this.idcampacabe = idcampacabe;
	}

	public String getSociotelefono() {
		return sociotelefono;
	}

	public void setSociotelefono(String sociotelefono) {
		this.sociotelefono = sociotelefono;
	}

	public BigDecimal getIdpedidoasoc() {
		return idpedidoasoc;
	}

	public void setIdpedidoasoc(BigDecimal idpedidoasoc) {
		this.idpedidoasoc = idpedidoasoc;
	}

	public BigDecimal getIdresultado() {
		return idresultado;
	}

	public void setIdresultado(BigDecimal idresultado) {
		this.idresultado = idresultado;
	}

	public BigDecimal getIdmotivo() {
		return idmotivo;
	}

	public void setIdmotivo(BigDecimal idmotivo) {
		this.idmotivo = idmotivo;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getFecharellamada() {
		return fecharellamada;
	}

	public void setFecharellamada(String fecharellamada) {
		this.fecharellamada = fecharellamada;
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

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getCampacabe() {
		return campacabe;
	}

	public void setCampacabe(String campacabe) {
		this.campacabe = campacabe;
	}

	public List getListResultado() {
		return listResultado;
	}

	public void setListResultado(List listResultado) {
		this.listResultado = listResultado;
	}

	public List getListMotivo() {
		return listMotivo;
	}

	public void setListMotivo(List listMotivo) {
		this.listMotivo = listMotivo;
	}
	
	
	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getMinutos() {
		return minutos;
	}

	public void setMinutos(String minutos) {
		this.minutos = minutos;
	}
}
