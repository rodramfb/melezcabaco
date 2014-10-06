/* 
   javabean para la entidad (Formulario): clientesRemitosLeyendas
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Dec 10 09:15:59 GMT-03:00 2009 
   
   Para manejar la pagina: clientesRemitosLeyendasFrm.jsp
      
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

public class BeanClientesRemitosLeyendasFrm implements SessionBean,
		Serializable {

	private SessionContext context;

	static Logger log = Logger.getLogger(BeanClientesRemitosLeyendasFrm.class);

	private String validar = "";

	private BigDecimal idleyenda = new BigDecimal(-1);

	private int anioactual = Calendar.getInstance().get(Calendar.YEAR);

	private BigDecimal anio = new BigDecimal(-1);

	private BigDecimal idmes = new BigDecimal(-1);

	private BigDecimal idlocalidad = new BigDecimal(-1);

	private String localidad = "";

	private String cpostal = "";

	private BigDecimal idprovincia = new BigDecimal(-1);

	private String provincia = "";

	private String leyenda = "";

	private BigDecimal idempresa = new BigDecimal(-1);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private List mesesList = new ArrayList();

	private String criterio = "T";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	// EJV - Mantis 733 - 20110722 -->

	private List listClub = new ArrayList();

	private BigDecimal idclub = new BigDecimal(-1);

	// <--

	public BeanClientesRemitosLeyendasFrm() {
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
			Clientes clientesRemitosLeyendas = Common.getClientes();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = clientesRemitosLeyendas
						.clientesRemitosLeyendasCreate(this.criterio,
								this.anio, this.idmes, this.idlocalidad,
								this.idprovincia, this.leyenda, this.idclub,
								this.idempresa, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clientesRemitosLeyendas
						.clientesRemitosLeyendasUpdate(this.idleyenda,
								this.anio, this.idmes, this.idlocalidad,
								this.leyenda, this.idclub, this.idempresa,
								this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clientesRemitosLeyendas
						.clientesRemitosLeyendasDelete(this.idleyenda,
								this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosClientesRemitosLeyendas() {
		try {
			Clientes clientesRemitosLeyendas = Common.getClientes();
			List listClientesRemitosLeyendas = clientesRemitosLeyendas
					.getClientesRemitosLeyendasPK(this.idleyenda,
							this.idempresa);
			Iterator iterClientesRemitosLeyendas = listClientesRemitosLeyendas
					.iterator();
			if (iterClientesRemitosLeyendas.hasNext()) {
				String[] uCampos = (String[]) iterClientesRemitosLeyendas
						.next();
				// TODO: Constructores para cada tipo de datos

				/*
				 * "  ly.idleyenda, ly.anio, ly.idmes, me.mes, COALESCE(lo.idlocalidad, -1), "
				 * +
				 * "  COALESCE(lo.localidad, 'TODAS'), lo.cpostal, COALESCE(pr.idprovincia, -1), "
				 * + "  COALESCE(pr.provincia, 'TODAS'), ly.leyenda,"
				 */

				this.idleyenda = new BigDecimal(uCampos[0]);
				this.anio = new BigDecimal(uCampos[1]);
				this.idmes = new BigDecimal(uCampos[2]);
				this.idlocalidad = new BigDecimal(uCampos[4]);
				this.localidad = uCampos[5];
				this.cpostal = Common.setNotNull(uCampos[6]);
				this.idprovincia = new BigDecimal(uCampos[7]);
				this.provincia = uCampos[8];
				this.leyenda = uCampos[9];
				this.idclub = new BigDecimal(uCampos[10]);

			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosClientesRemitosLeyendas()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {

			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("clientesRemitosLeyendasAbm.jsp");
				return true;
			}

			this.mesesList = Common.getGeneral().getGlobalMeses();

			// EJV - Mantis 734 - 20110720 -->
			this.listClub = Common.getClientes().getClientesClubAll(100, 0,
					this.idempresa);
			// <--

			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos

					if (Common.setNotNull(this.criterio).equals("")) {
						this.mensaje = "Seleccione criterio de asignacion de leyenda. ";
						return false;
					}

					if (this.anio == null || this.anio.longValue() < 1900) {
						this.mensaje = "Seleccione  periodo valido. ";
						return false;
					}
					if (this.idmes == null || this.idmes.longValue() < 1) {
						this.mensaje = "Seleccione mes periodo valido. ";
						return false;
					}

					if (this.idclub == null || this.idclub.longValue() < 1) {
						this.mensaje = "Seleccione club al que corresponde la leyenda. ";
						return false;
					}

					if (this.criterio.equalsIgnoreCase("L")) {
						if (this.idlocalidad == null
								|| this.idlocalidad.longValue() < 1) {
							this.mensaje = "Seleccione localidad valida. ";
							return false;
						}

						if (Common.setNotNull(this.cpostal).equals("")) {
							this.mensaje = "CP invalido, es necesario corregirlo. ";
							return false;
						}

					} else if (this.criterio.equalsIgnoreCase("P")) {
						if (this.idprovincia == null
								|| this.idprovincia.longValue() < 1) {
							this.mensaje = "Seleccione provincia valida. ";
							return false;
						}
					}

					if (Common.setNotNull(this.leyenda).equals("")
							|| this.leyenda.trim().length() < 30) {
						this.mensaje = "Ingrese leyenda valida, la misma debe tener al menos 30 caracteres. ";
						return false;
					}

					// 2. len 0 para campos nulos
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosClientesRemitosLeyendas();
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
	public BigDecimal getIdleyenda() {
		return idleyenda;
	}

	public void setIdleyenda(BigDecimal idleyenda) {
		this.idleyenda = idleyenda;
	}

	public int getAnioactual() {
		return anioactual;
	}

	public void setAnioactual(int anioactual) {
		this.anioactual = anioactual;
	}

	public BigDecimal getAnio() {
		return anio;
	}

	public void setAnio(BigDecimal anio) {
		this.anio = anio;
	}

	public BigDecimal getIdmes() {
		return idmes;
	}

	public void setIdmes(BigDecimal idmes) {
		this.idmes = idmes;
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

	public String getCpostal() {
		return cpostal;
	}

	public void setCpostal(String cpostal) {
		this.cpostal = cpostal;
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

	public String getLeyenda() {
		return leyenda;
	}

	public void setLeyenda(String leyenda) {
		this.leyenda = leyenda;
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

	public List getMesesList() {
		return mesesList;
	}

	public void setMesesList(List mesesList) {
		this.mesesList = mesesList;
	}

	public String getCriterio() {
		return criterio;
	}

	public void setCriterio(String criterio) {
		this.criterio = criterio;
	}

	// EJV - Mantis 734 - 20110720 -->

	public BigDecimal getIdclub() {
		return idclub;
	}

	public void setIdclub(BigDecimal idclub) {
		this.idclub = idclub;
	}

	public List getListClub() {
		return listClub;
	}

	// <--

}
