/* 
   javabean para la entidad (Formulario): rrhhpersonal
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Apr 22 09:44:28 ACT 2009 
   
   Para manejar la pagina: rrhhpersonalFrm.jsp
      
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

public class BeanRrhhPersonalConceptosAsocia implements SessionBean,
		Serializable {

	private SessionContext context;

	static Logger log = Logger.getLogger(BeanRrhhPersonalConceptosAsocia.class);

	private String validar = "";

	private String legajo = "";

	private BigDecimal legajoReplicar = new BigDecimal(-1);

	private String apellido = "";

	private String domicilio = "";

	private BigDecimal idempresa = BigDecimal.valueOf(0);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private List listConceptosAsociados = new ArrayList();

	private List listConceptosSinAsociar = new ArrayList();

	private String[] idconcepto_asoc = null;

	private String[] idconcepto_pend = null;

	public BeanRrhhPersonalConceptosAsocia() {
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

			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = Common.getRrhh()
						.callRrhhConceptosXPersonalCreate(this.idconcepto_pend,
								new BigDecimal(this.legajo), this.idempresa,
								this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("eliminar"))
				this.mensaje = Common.getRrhh()
						.callRrhhConceptosXPersonalDelete(this.idconcepto_asoc,
								new BigDecimal(this.legajo), this.idempresa);
			else if (this.accion.equalsIgnoreCase("replicar"))
				this.mensaje = Common.getRrhh().rrhhConceptosXPersonalReplicar(
						new BigDecimal(this.legajo), this.legajoReplicar,
						this.idempresa, this.usuarioalt);

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosRrhhpersonal() {
		try {

			RRHH rrhhpersonal = Common.getRrhh();
			List listRrhhpersonal = rrhhpersonal.getRrhhpersonalPK(
					new BigDecimal(this.legajo), this.idempresa);
			Iterator iterRrhhpersonal = listRrhhpersonal.iterator();
			if (iterRrhhpersonal.hasNext()) {
				String[] uCampos = (String[]) iterRrhhpersonal.next();
				// TODO: Constructores para cada tipo de datos
				this.legajo = uCampos[0];
				this.apellido = uCampos[1];
				this.domicilio = uCampos[2];

			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosRrhhpersonal()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {

			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("rrhhpersonalAbm.jsp");
				return true;
			}

			this.getDatosRrhhpersonal();

			if (!this.accion.equalsIgnoreCase("")) {

				if (legajo.trim().length() == 0) {
					this.mensaje = "No se puede dejar vacio el campo Legajo";

				} else if (this.accion.equalsIgnoreCase("eliminar")) {

					if (this.idconcepto_asoc == null) {
						this.mensaje = "Seleccione al menos un concepto a eliminar.";

					}

				} else if (this.accion.equalsIgnoreCase("alta")) {

					if (this.idconcepto_pend == null) {
						this.mensaje = "Seleccione al menos un concepto para asociar.";

					}

				} else if (this.accion.equalsIgnoreCase("replicar")) {

					if (this.legajoReplicar == null
							|| this.legajoReplicar.longValue() < 1) {
						this.mensaje = "No hay definido legajo desde el cuál efectuar la copia de conceptos.";

					} else if (this.legajo.equals(this.legajoReplicar
							.toString())) {
						this.mensaje = "No es posible seleccionar un mismo legajo como origen y destino, para replicar conceptos.";

					}

				}

				if (this.mensaje.equals(""))
					this.ejecutarSentenciaDML();

			}

			this.listConceptosAsociados = Common.getRrhh()
					.getRrhhConceptosXPersonalPK(new BigDecimal(legajo), "S",
							idempresa);

			this.listConceptosSinAsociar = Common.getRrhh()
					.getRrhhConceptosXPersonalPK(new BigDecimal(legajo), "N",
							idempresa);

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
	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
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

	public String getLegajo() {
		return legajo;
	}

	public void setLegajo(String legajo) {
		this.legajo = legajo;
	}

	public BigDecimal getLegajoReplicar() {
		return legajoReplicar;
	}

	public void setLegajoReplicar(BigDecimal legajoReplicar) {
		this.legajoReplicar = legajoReplicar;
	}

	public List getListConceptosAsociados() {
		return listConceptosAsociados;
	}

	public void setListConceptosAsociados(List listConceptosAsociados) {
		this.listConceptosAsociados = listConceptosAsociados;
	}

	public List getListConceptosSinAsociar() {
		return listConceptosSinAsociar;
	}

	public void setListConceptosSinAsociar(List listConceptosSinAsociar) {
		this.listConceptosSinAsociar = listConceptosSinAsociar;
	}

	public String[] getIdconcepto_asoc() {
		return idconcepto_asoc;
	}

	public void setIdconcepto_asoc(String[] idconcepto_asoc) {
		this.idconcepto_asoc = idconcepto_asoc;
	}

	public String[] getIdconcepto_pend() {
		return idconcepto_pend;
	}

	public void setIdconcepto_pend(String[] idconcepto_pend) {
		this.idconcepto_pend = idconcepto_pend;
	}

}
