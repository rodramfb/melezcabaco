/* 
 javabean para la entidad: clientesClientes
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Feb 12 11:19:57 ART 2008 
 
 Para manejar la pagina: clientesClientesAbm.jsp
 
 */
package ar.com.syswarp.web.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import java.util.*;
import java.math.*;

import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanMenuInteractivo implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanMenuInteractivo.class);

	private BigDecimal idempresa = new BigDecimal(-1);

	private SessionContext context;

	private String validar = "";

	private BigDecimal idcliente = new BigDecimal(-1);

	//20120703 - CAMI - Agregado de clientes para el menu del sas ->
	private List listClientesLlenar = new ArrayList();
	
	private BigDecimal puntosCtaCte = new BigDecimal(-1);
	
	private BigDecimal idprecarga = new BigDecimal(-1);
	
	private List listCliente = new ArrayList();
	
	private Hashtable htTarjetasCliente = new Hashtable();

	private Hashtable htDomiciliosCliente = new Hashtable();
	
	//<--------------------------------------
	private String cliente = "";

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	public BigDecimal codigo_ = new BigDecimal(-1);
	
	// EJV - 20120425 -->

	private boolean primeraCarga = true;

	private String accion = "";

	// <--
	public BeanMenuInteractivo() {
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

	public boolean ejecutarValidacion() {
		RequestDispatcher dispatcher = null;
		Clientes clientesClientes = Common.getClientes();
		try {

			// EJV - 20120425 -->
			// log.info("validar: " + validar);
			// log.info("idcliente: " + idcliente);
			// <--

			//20120703 - CAMI - Agregado de clientes para el menu del sas ->
			if (this.idcliente.longValue() < 0 || this.idcliente == null) {
				this.mensaje = "Es necesario seleccionar cliente para activar panel de actividades.";
				this.htDomiciliosCliente = new Hashtable();
				this.htTarjetasCliente = new Hashtable();

				request.getSession().removeAttribute("htDomiciliosCliente");
				request.getSession().removeAttribute("htTarjetasCliente");
				request.getSession().removeAttribute("idmes");

				request.getSession().setAttribute("htDomiciliosCliente", this.htDomiciliosCliente);
				request.getSession().setAttribute("htTarjetasCliente", this.htTarjetasCliente);
				return false;
			} else {

				// 20120702-CAMI - Agregado de datos del cliente
				// Si hay seleccionado un cliente entonces vamos a obtenerUno.
				Iterator iter;
				Hashtable htMails = new Hashtable();
				
				listClientesLlenar = clientesClientes.getClienteTarjetasCliente(100,
						0, this.idcliente, this.idempresa);

				iter = listClientesLlenar.iterator();

				while (iter.hasNext()) {

					String[] tarjetas = (String[]) iter.next();

					tarjetas = new String[] {
							Common.setNotNull(tarjetas[0]).trim(),
							Common.setNotNull(tarjetas[1]).trim(),
							Common.setNotNull(tarjetas[2]).trim(),
							Common.setNotNull(tarjetas[4]).trim(),
							Common.setNotNull(tarjetas[5]).trim(),
							Common.setNotNull(tarjetas[6]).trim(),
							Common.setNotNull(tarjetas[7]).trim(),
							tarjetas[8] != null ? Common.setObjectToStrOrTime(
									java.sql.Date.valueOf(tarjetas[8]),
									"JSDateToStr")
									+ "" : "",
							Common.setObjectToStrOrTime(
									java.sql.Date.valueOf(tarjetas[9]),
									"JSDateToStr").toString(),
							Common.setNotNull(tarjetas[10]).trim(),
							Common.setNotNull(tarjetas[11]).trim(),
							Common.setNotNull(tarjetas[12]).trim(), "M" };
					// log.info("llena tarjetas ...." + tarjetas[0]);
					this.htTarjetasCliente.put(tarjetas[0], tarjetas);

				}

				request.getSession().setAttribute("htTarjetasCliente",
						this.htTarjetasCliente);

				// TODO:
				listClientesLlenar = clientesClientes
						.getClientesDomiciliosCliente(100, 0, this.idcliente, this.idempresa);

				/*
				 * iddomicilio - idcliente - idtipodomicilio - tipodomicilio -
				 * esdefault - calle - nro - piso - depto - idlocalidad -
				 * localidad - cpa - postal - contacto - cargocontacto -
				 * telefonos - celular - fax - web - idzona - zona - idexpreso -
				 * expreso - idcobrador - cobrador - idvendedor - vendedor
				 * idempresa - usuarioalt - usuarioact - fechaalt - fechaact -
				 * diasauditados -
				 */
				iter = listClientesLlenar.iterator();
				while (iter.hasNext()) {

					Object[] domicilios = (Object[]) iter.next();

					domicilios = new Object[] { domicilios[0], domicilios[2],
							domicilios[3], domicilios[4], domicilios[5],
							domicilios[6], domicilios[7], domicilios[8],
							domicilios[9], domicilios[10], domicilios[11],
							domicilios[12], domicilios[13], domicilios[14],
							domicilios[15], domicilios[16], domicilios[17],
							domicilios[18],
							domicilios[19] == null ? "-1" : domicilios[19],
							domicilios[20] == null ? "" : domicilios[20],
							domicilios[21] == null ? "-1" : domicilios[21],
							domicilios[22] == null ? "" : domicilios[22],
							domicilios[23] == null ? "-1" : domicilios[23],
							domicilios[24] == null ? "" : domicilios[24],
							domicilios[25] == null ? "-1" : domicilios[25],
							domicilios[26] == null ? "" : domicilios[26],
							htMails.get(domicilios[0]), "M",
							domicilios[36] == null ? "-1" : domicilios[36],
							domicilios[39], domicilios[40] };

					//log.info("llena domicilios ...." + domicilios[0]);
					this.htDomiciliosCliente.put(domicilios[0].toString(),
							domicilios);

				}

				request.getSession().setAttribute("htDomiciliosCliente",
						this.htDomiciliosCliente);
				
				puntosCtaCte = clientesClientes.getBacoRefCtaCtePuntosCliente(idcliente, idempresa);
				this.listCliente = clientesClientes
				.getClientesClientesObtenerUno(this.idcliente,
						this.idempresa);
				//<-----------------------------------------------------

			}

		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
	}

	public BigDecimal getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(BigDecimal idcliente) {
		this.idcliente = idcliente;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
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

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getValidar() {
		return validar;
	}

	public void setValidar(String validar) {
		this.validar = validar;
	}

	public BigDecimal getCodigo_() {
		return codigo_;
	}

	public void setCodigo_(BigDecimal codigo_) {
		this.codigo_ = codigo_;
	}

	// EJV - 20120425 -->

	public boolean isPrimeraCarga() {
		return primeraCarga;
	}

	public void setPrimeraCarga(boolean primeraCarga) {
		this.primeraCarga = primeraCarga;
	}

	// <--

	// 20120702 - CAMI - Agregado de muestra de cliente

	public List getListCliente() {
		return listCliente;
	}

	public void setListCliente(List listCliente) {
		this.listCliente = listCliente;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	

	public Hashtable getHtTarjetasCliente() {
		return htTarjetasCliente;
	}

	public void setHtTarjetasCliente(Hashtable htTarjetasCliente) {
		this.htTarjetasCliente = htTarjetasCliente;
	}

	public Hashtable getHtDomiciliosCliente() {
		return htDomiciliosCliente;
	}

	public void setHtDomiciliosCliente(Hashtable htDomiciliosCliente) {
		this.htDomiciliosCliente = htDomiciliosCliente;
	}


	public List getListClientesLlenar() {
		return listClientesLlenar;
	}

	public void setListClientesLlenar(List listClientesLlenar) {
		this.listClientesLlenar = listClientesLlenar;
	}

	public BigDecimal getPuntosCtaCte() {
		return puntosCtaCte;
	}

	public void setPuntosCtaCte(BigDecimal puntosCtaCte) {
		this.puntosCtaCte = puntosCtaCte;
	}

	public BigDecimal getIdprecarga() {
		return idprecarga;
	}

	public void setIdprecarga(BigDecimal idprecarga) {
		this.idprecarga = idprecarga;
	}
	
	// <--

}
