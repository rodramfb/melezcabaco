/* 
   javabean para la entidad (Formulario): bacoRefPrepro
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Jun 16 10:12:03 ART 2010 
   
   Para manejar la pagina: bacoRefPreproFrm.jsp
      
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

public class BeanBacoRefPreproFrm implements SessionBean, Serializable {

	private SessionContext context;

	static Logger log = Logger.getLogger(BeanBacoRefPreproFrm.class);

	private String validar = "";

	private BigDecimal idpreprospecto = new BigDecimal(-1);

	private String nombre = "";

	private String apellido = "";

	private String idreferente = "";

	private String referente = "";

	private BigDecimal idvendedor = new BigDecimal(-1);

	private String vendedor = "";

	private BigDecimal idfuente = new BigDecimal(-1);

	private List listFuente = new ArrayList();

	private String fecha = "";

	private String telefono = "";

	private String celular = "";

	private String email = "";

	private BigDecimal idprovincia = new BigDecimal(-1);

	private String provincia = "";

	private BigDecimal idlocalidad = new BigDecimal(-1);

	private String localidad = "";

	private String observaciones = "";

	private BigDecimal procesado = new BigDecimal(0);

	private BigDecimal idempresa = new BigDecimal(-1);

	private String usuarioalt = "";

	private String usuarioact = "";

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private Hashtable htFuenteReferente = new Hashtable();

	// 20120313 - EJV - Mantis 702 -->

	private BigDecimal idrefestado = new BigDecimal(-1);

	private BigDecimal idrefsubestado = new BigDecimal(-1);

	private List listRefEstados = new ArrayList();

	private List listRefSubEstados = new ArrayList();

	// 20120416 - EJV

	private String refestado = "";

	private String refsubestado = "";

	private boolean primeraCarga = true;

	// <--

	public BeanBacoRefPreproFrm() {
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

			Clientes clientes = Common.getClientes();

			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = clientes.bacoRefPreproCreate(this.nombre,
						this.apellido, new BigDecimal(this.idreferente),
						this.idvendedor, this.idfuente,
						(java.sql.Date) Common.setObjectToStrOrTime(this.fecha,
								"StrToJSDate"), this.telefono, this.celular,
						this.email, GeneralBean.setNull(this.idprovincia, 0),
						GeneralBean.setNull(this.idlocalidad, 0),
						this.observaciones, this.procesado,
						// 20120314 - EJV - Mantis 702 -->
						GeneralBean.setNull(this.idrefestado, 0), GeneralBean
								.setNull(this.idrefsubestado, 0),
						// <--
						this.idempresa, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clientes.bacoRefPreproUpdate(
						this.idpreprospecto, this.nombre, this.apellido,
						new BigDecimal(this.idreferente), this.idvendedor,
						this.idfuente,
						(java.sql.Date) Common.setObjectToStrOrTime(this.fecha,
								"StrToJSDate"), this.telefono, this.celular,
						this.email, GeneralBean.setNull(this.idprovincia, 0),
						GeneralBean.setNull(this.idlocalidad, 0),
						this.observaciones, this.procesado,
						// 20120314 - EJV - Mantis 702 -->
						GeneralBean.setNull(this.idrefestado, 0), GeneralBean
								.setNull(this.idrefsubestado, 0),
						// <--
						this.idempresa, this.usuarioact);

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosBacoRefPrepro() {
		try {
			Clientes clientes = Common.getClientes();
			List listBacoRefPrepro = clientes.getBacoRefPreproPK(
					this.idpreprospecto, this.idempresa);
			Iterator iterBacoRefPrepro = listBacoRefPrepro.iterator();
			if (iterBacoRefPrepro.hasNext()) {
				String[] uCampos = (String[]) iterBacoRefPrepro.next();
				// TODO: Constructores para cada tipo de datos

				// for (int x = 0; x < uCampos.length; x++) {
				// log.info("uCampos[" + x + "]: " + uCampos[x]);
				// }

				this.idpreprospecto = new BigDecimal(uCampos[0]);
				this.nombre = uCampos[1];
				this.apellido = uCampos[2];
				this.idreferente = uCampos[3];
				this.referente = uCampos[4];
				this.idvendedor = new BigDecimal(uCampos[5]);
				this.vendedor = uCampos[6];
				this.idfuente = new BigDecimal(uCampos[7]);
				// this.fuente = uCampos[8];
				this.fecha = (String) Common.setObjectToStrOrTime(java.sql.Date
						.valueOf(uCampos[9]), "JSDateToStr");
				this.telefono = Common.setNotNull(uCampos[10]);
				this.celular = Common.setNotNull(uCampos[11]);
				this.email = Common.setNotNull(uCampos[12]);
				this.idprovincia = new BigDecimal(!Common.setNotNull(
						uCampos[13]).equals("") ? uCampos[13] : "-1");
				this.provincia = Common.setNotNull(uCampos[14]);
				this.idlocalidad = new BigDecimal(!Common.setNotNull(
						uCampos[15]).equals("") ? uCampos[15] : "-1");
				this.localidad = Common.setNotNull(uCampos[16]);
				this.observaciones = uCampos[17];
				this.procesado = new BigDecimal(!Common.setNotNull(uCampos[18])
						.equals("") ? uCampos[18] : "0");
				// 20120314 - EJV - Mantis 702 -->
				this.idrefestado = new BigDecimal(!Common.setNotNull(
						uCampos[19]).equals("") ? uCampos[19] : "-1");
				this.idrefsubestado = new BigDecimal(!Common.setNotNull(
						uCampos[20]).equals("") ? uCampos[20] : "-1");
				// 20120416
				this.refestado = Common.setNotNull(uCampos[21]);
				this.refsubestado = Common.setNotNull(uCampos[21]);

				// <--

			} else {

				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";

			}
		} catch (Exception e) {
			log.error("getDatosBacoRefPrepro()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("bacoRefPreproAbm.jsp");
				return true;
			}

			this.listFuente = Common.getClientes().getBacoRefFuentesAll(500, 0,
					this.idempresa);
			// 20120314 - EJV - Mantis 702 -->
			this.listRefEstados = Common.getClientes().getBacoRefEstadosAll(
					500, 0, this.idempresa);

			this.listRefSubEstados = Common.getClientes()
					.getBacoRefSubEstadosAll(500, 0, this.idrefestado,
							this.idempresa);
			// <--
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos

					if (Common.setNotNull(this.nombre).equals("")) {
						this.mensaje = "No se puede dejar vacio el campo nombre ";
						return false;
					}

					if (Common.setNotNull(this.apellido).equals("")) {
						this.mensaje = "No se puede dejar vacio el campo apellido ";
						return false;
					}

					if (this.idfuente == null || this.idfuente.longValue() < 1) {
						this.mensaje = "Seleccione fuente. ";
						return false;
					}

					if (!Common.esEntero(this.idreferente)
							|| Long.parseLong(this.idreferente) < 1) {
						this.mensaje = "Ingrese referente válido";
						return false;
					}

					this.setHtFuente();

					if (this.htFuenteReferente.get(this.idfuente.toString())
							.equals("0")
							&& Long.parseLong(this.idreferente) == 0) {
						this.mensaje = "Es necesario seleccionar un referente, ya que la fuente seleccionada no posee ninguno asociado.";
						return false;
					}

					if (!this.htFuenteReferente.get(this.idfuente.toString())
							.equals("0")
							&& Long.parseLong(this.idreferente) == 0) {
						this.mensaje = "Por favor verifique los datos ingresados, la fuente seleccionada tiene un referente asociado, pero el mismo no fue asignado.";
						return false;
					}

					if (this.idvendedor == null
							|| this.idvendedor.longValue() < 1) {
						this.mensaje = "No se puede dejar vacio el campo vendedor ";
						return false;
					}

					if (!Common.isFormatoFecha(this.fecha)
							|| !Common.isFechaValida(this.fecha)) {
						this.mensaje = "Ingrese fecha válida.";
						return false;
					}

					if (Common.setNotNull(this.celular).equals("")
							&& Common.setNotNull(this.telefono).equals("")) {
						this.mensaje = "Es necesario ingresar télefono o celular.";
						return false;

					}

					if (!Common.setNotNull(this.email).equals("")
							&& !Common.isValidEmailAddress(this.email)) {
						this.mensaje = "Ingrese un email válido.";
						return false;
					}

					// 20120314 - EJV - Mantis 702 -->
					if (this.idrefestado == null
							|| this.idrefestado.longValue() < 1) {
						this.mensaje = "No se puede dejar vacio el campo estado ";
						return false;
					}

					// <--

				}

				this.ejecutarSentenciaDML();

			} else {

				if (!this.accion.equalsIgnoreCase("alta")
						&& this.isPrimeraCarga()) {
					getDatosBacoRefPrepro();
					this.listRefSubEstados = Common.getClientes()
							.getBacoRefSubEstadosAll(500, 0, this.idrefestado,
									this.idempresa);
				}
			}
		} catch (Exception e) {
			log.error(" ejecutarValidacion(): " + e);
		}
		return true;
	}

	private void setHtFuente() {
		try {

			Iterator it = this.listFuente.iterator();
			while (it.hasNext()) {
				String[] datos = (String[]) it.next();
				this.htFuenteReferente.put(datos[0], datos[2]);
			}

		} catch (Exception e) {
			log.error("setHtFuente: " + e);
		}

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
	public BigDecimal getIdpreprospecto() {
		return idpreprospecto;
	}

	public void setIdpreprospecto(BigDecimal idpreprospecto) {
		this.idpreprospecto = idpreprospecto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getIdreferente() {
		return idreferente;
	}

	public void setIdreferente(String idreferente) {
		this.idreferente = idreferente;
	}

	public String getReferente() {
		return referente;
	}

	public void setReferente(String referente) {
		this.referente = referente;
	}

	public BigDecimal getIdvendedor() {
		return idvendedor;
	}

	public void setIdvendedor(BigDecimal idvendedor) {
		this.idvendedor = idvendedor;
	}

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

	public BigDecimal getIdfuente() {
		return idfuente;
	}

	public List getListFuente() {
		return listFuente;
	}

	public void setIdfuente(BigDecimal idfuente) {
		this.idfuente = idfuente;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getIdprovincia() {
		return idprovincia;
	}

	public void setIdprovincia(BigDecimal idprovincia) {
		this.idprovincia = idprovincia;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public BigDecimal getIdlocalidad() {
		return idlocalidad;
	}

	public void setIdlocalidad(BigDecimal idlocalidad) {
		this.idlocalidad = idlocalidad;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public BigDecimal getProcesado() {
		return procesado;
	}

	public void setProcesado(BigDecimal procesado) {
		this.procesado = procesado;
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

	// 20120313 - EJV - Mantis 702 -->

	public BigDecimal getIdrefestado() {
		return idrefestado;
	}

	public void setIdrefestado(BigDecimal idrefestado) {
		this.idrefestado = idrefestado;
	}

	public BigDecimal getIdrefsubestado() {
		return idrefsubestado;
	}

	public void setIdrefsubestado(BigDecimal idrefsubestado) {
		this.idrefsubestado = idrefsubestado;
	}

	public List getListRefEstados() {
		return listRefEstados;
	}

	public List getListRefSubEstados() {
		return listRefSubEstados;
	}

	public boolean isPrimeraCarga() {
		return primeraCarga;
	}

	public void setPrimeraCarga(boolean primeraCarga) {
		this.primeraCarga = primeraCarga;
	}

	public String getRefestado() {
		return refestado;
	}

	public void setRefestado(String refestado) {
		this.refestado = refestado;
	}

	public String getRefsubestado() {
		return refsubestado;
	}

	public void setRefsubestado(String refsubestado) {
		this.refsubestado = refsubestado;
	}


	
	// <--

}
