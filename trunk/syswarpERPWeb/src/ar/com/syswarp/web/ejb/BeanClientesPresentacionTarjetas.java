/* 
 javabean para la entidad (Formulario): clientetarjetascreditomarcas
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Jan 23 19:22:37 GMT-03:00 2007 
 
 Para manejar la pagina: clientetarjetascreditomarcasFrm.jsp
 
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

public class BeanClientesPresentacionTarjetas implements SessionBean,
		Serializable {
	private SessionContext context;

	static Logger log = Logger
			.getLogger(BeanClientesPresentacionTarjetas.class);

	private String validar = "";

	private BigDecimal idtarjetacredito = BigDecimal.valueOf(-1);

	private BigDecimal idempresa;

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private List listTarjetasCredito = new ArrayList();

	private String fechaPresentacion = "";

	private java.sql.Date fpresentacion = null;

	private List listClub = new ArrayList();

	private BigDecimal idclub = new BigDecimal(-1);

	private Hashtable htPathTarjeta = new Hashtable();

	private Hashtable htClubes = new Hashtable();

	public BeanClientesPresentacionTarjetas() {
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

	public void generarPresentacion() {
		try {

			String archivo = "";
			String path = Common.setNotNull(this.htPathTarjeta
					.get(this.idtarjetacredito.toString())
					+ "");
			String club = Common.setNotNull(this.htClubes.get(this.idclub
					.toString())
					+ "");

			switch (this.idtarjetacredito.intValue()) {
			case 1:
				// VISA
				archivo = "VISAMOV-" + this.fpresentacion.toString() + "-"
						+ club + ".txt";
				this.mensaje = Common.getClientes().setPresentacionVISA(
						this.idclub, this.idtarjetacredito, this.fpresentacion,
						archivo, path, this.usuarioalt, this.idempresa);
				break;
			case 2:
				// MASTERCARD
				archivo = "DA168D-" + this.fpresentacion.toString() + "-"
						+ club + ".txt";
				this.mensaje = Common.getClientes().setPresentacionMASTERCARD(
						this.idclub, this.idtarjetacredito, this.fpresentacion,
						archivo, path, this.usuarioalt, this.idempresa);

				break;
			case 3:
				// DINERS
				// 20131106 - cambia el metodo actual de Diners y lo toma first Data por lo que se vuelve igual a Mastercard
				//            dejo el metodo comentado anterior y replico el de master.
				String archivoCtrl = "CONTROL-" + this.fpresentacion.toString()
						+ "-" + club + ".txt";
				archivo = "#ARCHIVO#-" + this.fpresentacion.toString() + "-"
						+ club + ".txt";
				this.mensaje = Common.getClientes().setPresentacionDINERS(
						this.idclub, this.idtarjetacredito, this.fpresentacion,
						archivo, path, this.usuarioalt, this.idempresa);

				break;
			case 4:
				// AMEX
				archivo = "DAC_BACOCL00_SUB-" + this.fpresentacion.toString()
						+ "-" + club + ".txt";
				this.mensaje = Common.getClientes().setPresentacionAMEX(
						this.idclub, this.idtarjetacredito, this.fpresentacion,
						archivo, path, this.usuarioalt, this.idempresa);
				break;

			case 6:
				// CABAL

				archivo = "COPYTAPS-" + this.fpresentacion.toString() + "-"
						+ club + ".txt";
				this.mensaje = Common.getClientes().setPresentacionCABAL(
						this.idclub, this.idtarjetacredito, this.fpresentacion,
						archivo, path, this.usuarioalt, this.idempresa);

				break;
			// -----------------------------------------------
			case 5:
				// VISA ELECTRON
			case 7:
				// FUEGUINA
			default:
				// NO EXISTENTE
				this.mensaje = "No hay proceso definido para la tarjeta seleccionada.";
				break;
			}

		} catch (Exception ex) {

			this.mensaje = "No se pudo ejecutar presentacion.";
			log.error(" generarPresentacion() : " + ex);

		}

		if (this.mensaje.equalsIgnoreCase("OK"))
			this.mensaje = "Presentacion generada correctamente.";
	}

	public boolean ejecutarValidacion() {
		try {

			this.listTarjetasCredito = Common.getClientes()
					.getClientetarjetascreditomarcasAll(250, 0, this.idempresa);

			this.listClub = Common.getClientes().getClientesClubAll(100, 0,
					this.idempresa);

			if (this.accion.equalsIgnoreCase("presentacion")) {

				// 1. nulidad de campos
				// 2. len 0 para campos nulos

				if (this.idclub.intValue() < 1) {
					this.mensaje = "Seleccione club.";
					return false;
				}

				if (this.idtarjetacredito.intValue() < 1) {
					this.mensaje = "Seleccione tarjeta.";
					return false;
				}

				if (!Common.isFormatoFecha(this.fechaPresentacion)
						|| !Common.isFechaValida(this.fechaPresentacion)) {
					this.mensaje = "Fecha de presentacion invalida.";
					return false;
				}

				this.fpresentacion = (java.sql.Date) Common
						.setObjectToStrOrTime(this.fechaPresentacion,
								"StrToJSDate");

				this.setHtPathTarjeta(this.listTarjetasCredito);
				this.setHtClub(this.listClub);

				this.generarPresentacion();

			}

		} catch (Exception e) {
			log.error(" ejecutarValidacion(): " + e);
		}
		return true;
	}

	private void setHtPathTarjeta(List listTjtas) {

		try {

			Iterator it = listTjtas.iterator();
			while (it.hasNext()) {
				String[] datos = (String[]) it.next();
				this.htPathTarjeta.put(datos[0], Common.setNotNull(datos[4]));
			}

		} catch (Exception e) {
			log.error("setHtPathTarjeta: " + e);
		}

	}

	private void setHtClub(List listClubes) {

		try {

			Iterator it = listClubes.iterator();
			while (it.hasNext()) {
				String[] datos = (String[]) it.next();
				this.htClubes.put(datos[0], Common.setNotNull(datos[1]).trim()
						.replaceAll(" ", ""));
			}

		} catch (Exception e) {
			log.error("setHtPathTarjeta: " + e);
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
	public BigDecimal getIdtarjetacredito() {
		return idtarjetacredito;
	}

	public void setIdtarjetacredito(BigDecimal idtarjetacredito) {
		this.idtarjetacredito = idtarjetacredito;
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

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public List getListTarjetasCredito() {
		return listTarjetasCredito;
	}

	public String getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(String fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public BigDecimal getIdclub() {
		return idclub;
	}

	public void setIdclub(BigDecimal idclub) {
		this.idclub = idclub;
	}

	public List getListClub() {
		return listClub;
	}

}
