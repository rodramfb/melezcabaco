/* 
   javabean para la entidad (Formulario): clientesAnexoLocalidades
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Dec 23 14:59:21 GMT-03:00 2008 
   
   Para manejar la pagina: clientesAnexoLocalidadesFrm.jsp
      
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

public class BeanClientesAnexoLocalidadesFrm implements SessionBean,
		Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanClientesAnexoLocalidadesFrm.class);

	private String validar = "";

	private BigDecimal idanexolocalidad = new BigDecimal(-1);

	private BigDecimal idexpresozona = new BigDecimal(-1);

	private String zona = "";

	private String expreso = "";

	private BigDecimal idlocalidad = new BigDecimal(-1);

	private String localidad = "";

	private Double codtfbasica = Double.valueOf("0");

	private String codtfctdo = "";

	private String tarand1bulto = "";

	private String tarandexc = "";

	private String tarsoc1bulto = "";

	private String tarsocexc = "";

	private String cabeoinflu = "";

	private String norteosur = "";

	private BigDecimal idempresa = new BigDecimal(-1);

	private String usuarioalt = "";

	private String usuarioact = "";

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	// 20091014 - Migración e implementación. Provisorio. -- >

	private String idexpresobaco = "0";

	private String iddistribuidorbaco = "0";

	// < --

	public BeanClientesAnexoLocalidadesFrm() {
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
			Clientes clientesAnexoLocalidades = Common.getClientes();
			if (this.accion.equalsIgnoreCase("alta")) {
				this.mensaje = clientesAnexoLocalidades
						.clientesAnexoLocalidadesCreate(this.idexpresozona,
								this.idlocalidad, this.codtfbasica,
								this.codtfctdo, new Double(this.tarand1bulto),
								new Double(this.tarandexc), new Double(
										this.tarsoc1bulto), new Double(
										this.tarsocexc), this.cabeoinflu,
								this.norteosur, new BigDecimal(
										this.idexpresobaco), new BigDecimal(
										this.iddistribuidorbaco),
								this.idempresa, this.usuarioalt);
			} else if (this.accion.equalsIgnoreCase("modificacion")) {
				this.mensaje = clientesAnexoLocalidades
						.clientesAnexoLocalidadesUpdate(this.idanexolocalidad,
								this.idexpresozona, this.idlocalidad,
								this.codtfbasica, this.codtfctdo, new Double(
										this.tarand1bulto), new Double(
										this.tarandexc), new Double(
										this.tarsoc1bulto), new Double(
										this.tarsocexc), this.cabeoinflu,
								this.norteosur, new BigDecimal(
										this.idexpresobaco), new BigDecimal(
										this.iddistribuidorbaco),
								this.idempresa, this.usuarioact);
			}
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosClientesAnexoLocalidades() {
		try {
			Clientes clientesAnexoLocalidades = Common.getClientes();
			List listClientesAnexoLocalidades = clientesAnexoLocalidades
					.getClientesAnexoLocalidadesPK(this.idanexolocalidad,
							this.idempresa);
			Iterator iterClientesAnexoLocalidades = listClientesAnexoLocalidades
					.iterator();
			if (iterClientesAnexoLocalidades.hasNext()) {
				String[] uCampos = (String[]) iterClientesAnexoLocalidades
						.next();
				// TODO: Constructores para cada tipo de datos
				this.idanexolocalidad = new BigDecimal(uCampos[0]);
				this.idexpresozona = new BigDecimal(uCampos[1]);
				this.expreso = uCampos[2];
				this.zona = uCampos[3];
				this.idlocalidad = new BigDecimal(uCampos[4]);
				this.localidad = uCampos[5];
				this.codtfbasica = Double.valueOf(uCampos[6]);
				this.codtfctdo = uCampos[7];
				this.tarand1bulto = uCampos[8];
				this.tarandexc = uCampos[9];
				this.tarsoc1bulto = uCampos[10];
				this.tarsocexc = uCampos[11];
				this.cabeoinflu = uCampos[12];
				this.norteosur = uCampos[13];
				this.idexpresobaco = Common.setNotNull(uCampos[14]).equals("") ? "0"
						: uCampos[14];
				this.iddistribuidorbaco = Common.setNotNull(uCampos[15])
						.equals("") ? "0" : uCampos[15];

			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosClientesAnexoLocalidades()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("clientesAnexoLocalidadesAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {

				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos

					if (idexpresozona == null || idexpresozona.longValue() < 1) {
						this.mensaje = "Seleccione Expreso / Zona. ";
						return false;
					}

					if (idlocalidad == null || idlocalidad.longValue() < 1) {
						this.mensaje = "No se puede dejar vacio el campo Localidad ";
						return false;
					}

					/*
					 * if (!Common.esNumerico(codtfbasica)) { this.mensaje =
					 * "No se puede dejar vacio el campo Cï¿½digo Tarifa Basica "
					 * ; return false; }
					 * 
					 * if (Common.setNotNull(codtfctdo).equals("")) {
					 * this.mensaje =
					 * "No se puede dejar vacio el campo Tarifa Contado  ";
					 * return false; }
					 */
					if (!Common.esNumerico(tarand1bulto)) {
						this.mensaje = "Ingrese valores numericos validos para el campo Tarifa Bulto ";
						return false;
					}

					if (!Common.esNumerico(tarandexc)) {
						this.mensaje = "No se puede dejar vacio el campo Tarifa Exceso ";
						return false;
					}

					if (!Common.esNumerico(tarsoc1bulto)) {
						this.mensaje = "Ingrese valores numericos validos para el campo Tarifa Socio Bulto  ";
						return false;
					}

					if (!Common.esNumerico(tarsocexc)) {
						this.mensaje = "Ingrese valores numericos validos para el campo Tarifa Socio Excedente ";
						return false;
					}

					if (Common.setNotNull(cabeoinflu).equals("")) {
						this.mensaje = "Seleccione Cabecera/Influencia ";
						return false;
					}

					if (Common.setNotNull(this.norteosur).equals("")) {
						this.mensaje = "Seleccione Norte/Sur ";
						return false;
					}

					if (!Common.esEntero(this.idexpresobaco)) {
						this.mensaje = "ID-Expreso Baco debe ser númerico. ";
						return false;
					}

					if (!Common.esEntero(this.iddistribuidorbaco)) {
						this.mensaje = "ID-Distribuidor Baco debe ser númerico. ";
						return false;
					}

				}

				this.ejecutarSentenciaDML();

			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosClientesAnexoLocalidades();
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
	public BigDecimal getIdanexolocalidad() {
		return idanexolocalidad;
	}

	public void setIdanexolocalidad(BigDecimal idanexolocalidad) {
		this.idanexolocalidad = idanexolocalidad;
	}

	public BigDecimal getIdexpresozona() {
		return idexpresozona;
	}

	public void setIdexpresozona(BigDecimal idexpresozona) {
		this.idexpresozona = idexpresozona;
	}

	public BigDecimal getIdlocalidad() {
		return idlocalidad;
	}

	public void setIdlocalidad(BigDecimal idlocalidad) {
		this.idlocalidad = idlocalidad;
	}

	public String getCodtfctdo() {
		return codtfctdo;
	}

	public void setCodtfctdo(String codtfctdo) {
		this.codtfctdo = codtfctdo;
	}

	public String getTarand1bulto() {
		return tarand1bulto;
	}

	public void setTarand1bulto(String tarand1bulto) {
		this.tarand1bulto = tarand1bulto;
	}

	public String getTarandexc() {
		return tarandexc;
	}

	public void setTarandexc(String tarandexc) {
		this.tarandexc = tarandexc;
	}

	public String getTarsoc1bulto() {
		return tarsoc1bulto;
	}

	public void setTarsoc1bulto(String tarsoc1bulto) {
		this.tarsoc1bulto = tarsoc1bulto;
	}

	public String getTarsocexc() {
		return tarsocexc;
	}

	public void setTarsocexc(String tarsocexc) {
		this.tarsocexc = tarsocexc;
	}

	public String getCabeoinflu() {
		return cabeoinflu;
	}

	public void setCabeoinflu(String cabeoinflu) {
		this.cabeoinflu = cabeoinflu;
	}

	public String getNorteosur() {
		return norteosur;
	}

	public void setNorteosur(String norteosur) {
		this.norteosur = norteosur;
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

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public String getExpreso() {
		return expreso;
	}

	public void setExpreso(String expreso) {
		this.expreso = expreso;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public Double getCodtfbasica() {
		return codtfbasica;
	}

	public void setCodtfbasica(Double codtfbasica) {
		this.codtfbasica = codtfbasica;
	}

	// 20091014 - Migración e implementación. Provisorio. -- >

	public String getIdexpresobaco() {
		return idexpresobaco;
	}

	public void setIdexpresobaco(String idexpresobaco) {
		this.idexpresobaco = idexpresobaco;
	}

	public String getIddistribuidorbaco() {
		return iddistribuidorbaco;
	}

	public void setIddistribuidorbaco(String iddistribuidorbaco) {
		this.iddistribuidorbaco = iddistribuidorbaco;
	}

	// < --

}
