/* 
 javabean para la entidad (Formulario): globalLocalidades
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Mon Jan 22 15:19:44 GMT-03:00 2007 
 
 Para manejar la pagina: globalLocalidadesFrm.jsp
 
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

public class BeanGlobalLocalidadesFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanGlobalLocalidadesFrm.class);

	private BigDecimal idempresa = new BigDecimal(-1);

	private String validar = "";

	private BigDecimal idlocalidad = BigDecimal.valueOf(-1);

	private String localidad = "";

	private BigDecimal idprovincia = BigDecimal.valueOf(0);

	private String d_idprovincia = "";

	private String cpostal = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private BigDecimal idobsequio = new BigDecimal(-1);

	private String bajaobsequios = "";

	private BigDecimal idtipoiva = new BigDecimal(-1);

	private List listTipoIva = new ArrayList();

	// 20091014 - Migración e implementación. Provisorio. -- >
	private String idlocalidadbaco = "0";

	// < --

	public BeanGlobalLocalidadesFrm() {
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
			String[] resultado;
			Clientes clie = Common.getClientes();
			if (this.accion.equalsIgnoreCase("alta")) {
				resultado = clie.globalLocalidadesCreate(this.localidad,
						this.idprovincia, this.cpostal, GeneralBean.setNull(
								this.idtipoiva, 1), new BigDecimal(
								this.idlocalidadbaco), this.usuarioalt);

				if (Common.setNotNull(resultado[0]).equalsIgnoreCase("OK")) {

					this.mensaje = "Alta Correcta";
					this.idlocalidad = new BigDecimal(resultado[1]);
					// this.accion = "modificacion";

				} else
					this.mensaje = resultado[0];

			} else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clie.globalLocalidadesUpdate(this.idlocalidad,
						this.localidad, this.idprovincia, this.cpostal,
						GeneralBean.setNull(this.idtipoiva, 1), new BigDecimal(
								this.idlocalidadbaco), this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clie.globalLocalidadesDelete(this.idlocalidad);

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosGlobalLocalidades() {
		try {
			Clientes clie = Common.getClientes();
			List listGlobalLocalidades = clie.getGlobalLocalidadesObseqPK(
					this.idlocalidad, this.idempresa);

			Iterator iterGlobalLocalidades = listGlobalLocalidades.iterator();
			if (iterGlobalLocalidades.hasNext()) {
				String[] uCampos = (String[]) iterGlobalLocalidades.next();
				// TODO: Constructores para cada tipo de datos
				this.idlocalidad = BigDecimal.valueOf(Long
						.parseLong(uCampos[0]));
				this.localidad = uCampos[1];
				this.idprovincia = BigDecimal.valueOf(Long
						.parseLong(uCampos[2]));
				this.d_idprovincia = uCampos[3];
				this.cpostal = uCampos[4];
				this.idlocalidadbaco = Common.setNotNull(uCampos[5]).equals("") ? "0"
						: uCampos[5];
				this.idobsequio = new BigDecimal(Common.setNotNull(uCampos[6])
						.equals("") ? "-1" : uCampos[6]);
				this.idtipoiva = new BigDecimal(Common.setNotNull(uCampos[7])
						.equals("") ? "-1" : uCampos[7]);
				
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosGlobalLocalidades()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("globalLocalidadesAbm.jsp");
				return true;
			}

			this.listTipoIva = Common.getClientes().getClientestablaivaAll(100,
					0, this.idempresa);

			if (this.bajaobsequios.equalsIgnoreCase("bajaobsequios")) {
				this.mensaje = Common.getClientes()
						.bacoObsequiosLocalidadDelete(this.idobsequio,
								this.idempresa);

				if (this.mensaje.equalsIgnoreCase("baja correcta")) {

					this.idobsequio = new BigDecimal(-1);

				}
			}

			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (d_idprovincia.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Provincia ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (localidad.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Localidad  ";
						return false;
					}

					if (!Common.esEntero(this.idlocalidadbaco)) {
						this.mensaje = "ID-Localidad Baco debe ser númerico. ";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosGlobalLocalidades();
				}
			}
		} catch (Exception e) {
			log.error(" ejecutarValidacion(): " + e);
		}
		return true;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
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

	public BigDecimal getIdprovincia() {
		return idprovincia;
	}

	public void setIdprovincia(BigDecimal idprovincia) {
		this.idprovincia = idprovincia;
	}

	public String getCpostal() {
		return cpostal;
	}

	public void setCpostal(String cpostal) {
		this.cpostal = cpostal;
	}

	public String getD_idprovincia() {
		return d_idprovincia;
	}

	public void setD_idprovincia(String d_idprovincia) {
		this.d_idprovincia = d_idprovincia;
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

	public BigDecimal getIdobsequio() {
		return idobsequio;
	}

	public void setIdobsequio(BigDecimal idobsequio) {
		this.idobsequio = idobsequio;
	}

	public String getBajaobsequios() {
		return bajaobsequios;
	}

	public void setBajaobsequios(String bajaobsequios) {
		this.bajaobsequios = bajaobsequios;
	}

	public BigDecimal getIdtipoiva() {
		return idtipoiva;
	}

	public void setIdtipoiva(BigDecimal idtipoiva) {
		this.idtipoiva = idtipoiva;
	}

	public List getListTipoIva() {
		return listTipoIva;
	}

	public void setListTipoIva(List listTipoIva) {
		this.listTipoIva = listTipoIva;
	}

	// 20091014 - Migración e implementación. Provisorio. -- >

	public String getIdlocalidadbaco() {
		return idlocalidadbaco;
	}

	public void setIdlocalidadbaco(String idlocalidadbaco) {
		this.idlocalidadbaco = idlocalidadbaco;
	}

	// < --

}
