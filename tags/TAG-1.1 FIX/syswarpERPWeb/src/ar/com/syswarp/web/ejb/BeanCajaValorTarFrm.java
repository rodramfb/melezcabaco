/* 
   javabean para la entidad (Formulario): cajaValorTar
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Oct 21 10:01:01 GMT-03:00 2008 
   
   Para manejar la pagina: cajaValorTarFrm.jsp
      
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

public class BeanCajaValorTarFrm implements SessionBean, Serializable {

	private SessionContext context;

	static Logger log = Logger.getLogger(BeanCajaValorTarFrm.class);

	private String validar = "";

	private String valor_id;

	private String tipo = "";

	private String[] numero = null;

	private String[] dias = null;

	private BigDecimal idempresa = new BigDecimal(-1);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private BigDecimal cuotasPresentacion = new BigDecimal(-1);

	private boolean primeraCarga = true;

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanCajaValorTarFrm() {
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

	public void setValuesSession() {
		try {

			Hashtable ht = new Hashtable();
			ht.put("dias", this.dias);
			ht.put("numero", this.numero);
			request.getSession().setAttribute("htValorTar" + this.tipo, ht);

			this.mensaje = "Datos guardados correctamente.";

		} catch (Exception ex) {
			log.error(" setValuesSession() : " + ex);
		}
	}

	public void getDatosCajaValorTar() {
		try {

			Tesoreria cajaValorTar = Common.getTesoreria();
			List listCajaValorTar = cajaValorTar.getCajaValorTarPK(
					this.valor_id, this.tipo, this.idempresa);
			Iterator iterCajaValorTar = listCajaValorTar.iterator();
			if (iterCajaValorTar.hasNext()) {
				String auxnro = "";
				String auxdias = "";
				int j = 0;
				while (iterCajaValorTar.hasNext()) {

					String[] uCampos = (String[]) iterCajaValorTar.next();
					// TODO: Constructores para cada tipo de datos

					auxnro += uCampos[2] + "-";
					auxdias += uCampos[3] + "-";

					j++;

				}

				int l = j;
				for (int k = (this.cuotasPresentacion.intValue() - j); k > 0; k--) {
					l++;
					auxnro += l + "-";
					auxdias +=  "0-";
				}

				auxnro = auxnro.substring(0, auxnro.lastIndexOf("-"));
				auxdias = auxdias.substring(0, auxdias.lastIndexOf("-"));

				this.numero = auxnro.split("-");
				this.dias = auxdias.split("-");

			} else {
				this.inicializarArreglos();
			}

		} catch (Exception e) {
			log.error("getDatosCajaValorTar()" + e);
		}
	}

	public void inicializarArreglos() {

		this.numero = new String[this.cuotasPresentacion.intValue()];
		this.dias = new String[this.cuotasPresentacion.intValue()];

		for (int r = 0; r < this.cuotasPresentacion.intValue(); r++) {
			this.dias[r] = "";
			this.numero[r] = (r + 1) + "";
		}

	}

	public boolean ejecutarValidacion() {
		try {

			if (isPrimeraCarga() && this.accion.equalsIgnoreCase("alta")) {

				this.inicializarArreglos();

			}

			if (!this.validar.equalsIgnoreCase("")) {

				if (!this.accion.equalsIgnoreCase("baja")) {

					for (int r = 0; r < this.dias.length; r++) {

						if (!Common.esEntero(dias[r])) {
							this.mensaje = "Ingrese valores numericos.";
							return false;
						}

						if (Integer.parseInt(dias[r]) < 1) {
							this.mensaje = "Debe ingresar valores mayores a cero.";
							return false;
						}

						dias[r] = dias[r].trim();
					}

				}

				this.setValuesSession();

			} else {

				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosCajaValorTar();
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
	public String getValor_id() {
		return valor_id;
	}

	public void setValor_id(String valor_id) {
		this.valor_id = valor_id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String[] getNumero() {
		return numero;
	}

	public void setNumero(String[] numero) {
		this.numero = numero;
	}

	public String[] getDias() {
		return dias;
	}

	public void setDias(String[] dias) {
		this.dias = dias;
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

	public BigDecimal getCuotasPresentacion() {
		return cuotasPresentacion;
	}

	public void setCuotasPresentacion(BigDecimal cuotasPresentacion) {
		this.cuotasPresentacion = cuotasPresentacion;
	}

	public boolean isPrimeraCarga() {
		return primeraCarga;
	}

	public void setPrimeraCarga(boolean primeraCarga) {
		this.primeraCarga = primeraCarga;
	}

}
