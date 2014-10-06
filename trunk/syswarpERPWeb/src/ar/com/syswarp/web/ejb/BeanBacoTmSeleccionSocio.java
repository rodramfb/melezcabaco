/* 
 javabean para la entidad: clienteszonas
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Nov 14 14:50:18 GMT-03:00 2006 
 
 Para manejar la pagina: clienteszonasAbm.jsp
 
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

public class BeanBacoTmSeleccionSocio implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanBacoTmSeleccionSocio.class);

	private SessionContext context;

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private String validar = "";

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List listSeleccionSocio = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idtelemark = new BigDecimal(0);

	private BigDecimal idcampacabe = new BigDecimal(0);

	private List listCampaniasActivas = new ArrayList();

	private BigDecimal idresultado = new BigDecimal(0);

	private String usuarioalt = "";

	private String campacabe = "";

	private BigDecimal idempresa;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private List listidresultado = new ArrayList();

	boolean buscar = false;

	private String buscarsocio = "";

	public BeanBacoTmSeleccionSocio() {
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
		try {
			// ArrayList aceptados = new ArrayList();
			// aceptados.add(0, "All");
			this.listidresultado = Common.getClientes().getBacotmresultadosAll(
					100, 0, this.idempresa);
			//String entidad = "";
			//Clientes clienteszonas = Common.getClientes();
			this.listCampaniasActivas = Common.getClientes()
					.getClientesCampaiaLovAll(100, 0, this.idempresa);

			// busqueda por socio
			if (this.accion.equalsIgnoreCase("buscar")) {
				if (idcampacabe.longValue() < 0) {
					this.mensaje = "No se puede dejar vacio el campo Campaña  ";
					return false;
				}
				
				if(Common.setNotNull(this.buscarsocio).equals("") || this.buscarsocio.trim().length() < 3){
					this.mensaje = "Ingrese criterio de busqueda de al menos tres caracteres.";
					return false;
				}
				
				General bacotmbuscarcliente = Common.getGeneral();
				this.listSeleccionSocio = bacotmbuscarcliente
						.getGestionTmBusquedaporCliente(this.buscarsocio.replaceAll("'", "_"),
								this.idtelemark, this.idcampacabe,
								this.idempresa);

			}

			if (this.accion.equalsIgnoreCase("consultauno")) {
				if (idcampacabe.longValue() < 0) {
					this.mensaje = "No se puede dejar vacio el campo Campaña  ";
					return false;
				}
				if (idcampacabe.longValue() > 0) {
					General bacotmcampacabe = Common.getGeneral();
					this.listSeleccionSocio = bacotmcampacabe
							.getGestionTmCampania(this.idtelemark,
									this.idcampacabe, this.idempresa);
				}
			}
			if (this.accion.equalsIgnoreCase("consultados")) {
				if (idcampacabe.longValue() < 0) {
					this.mensaje = "No se puede dejar vacio el campo Campaña  ";
					return false;
				}
				if (idresultado.longValue() < 0) {
					this.mensaje = "No se puede dejar vacio el campo Resultado  ";
					return false;
				}
				if (idresultado.longValue() > 0) {
					if (idcampacabe.longValue() > 0) {
						// EJV - 20091013 - Mantis 429
						// NO CORRESPONDE AQUI !! --- > aca va si es por
						// campaña y
						// por resultado <---
						// if(idresultado.longValue() == 12 ) {
						if (idresultado.longValue() != 12) {
							General bacotmcampacaberesultado = Common
									.getGeneral();
							this.listSeleccionSocio = bacotmcampacaberesultado
									.getGestionTmCampaniaResultado(
											this.idtelemark, this.idcampacabe,
											this.idresultado, this.idempresa,
											this.usuarioalt);
						} else {
							// AHORA SI !! ... ACA va si es por campaña y por
							// resultado
							General bacotmcampacaberesultado = Common
									.getGeneral();
							this.listSeleccionSocio = bacotmcampacaberesultado
									.getGestionTmCampaniaResultado2(
											this.idtelemark, this.idcampacabe,
											this.idresultado, this.idempresa,
											this.usuarioalt);
						}

					}
				}
			}

			// if (this.totalRegistros < 1)
			// this.mensaje = "No existen registros.";
		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public long getTotalRegistros() {
		return totalRegistros;
	}

	public void setTotalRegistros(long total) {
		this.totalRegistros = total;
	}

	public long getTotalPaginas() {
		return totalPaginas;
	}

	public void setTotalPaginas(long totalPaginas) {
		this.totalPaginas = totalPaginas;
	}

	public long getPaginaSeleccion() {
		return paginaSeleccion;
	}

	public void setPaginaSeleccion(long paginaSeleccion) {
		this.paginaSeleccion = paginaSeleccion;
	}

	public List getListSeleccionSocio() {
		return listSeleccionSocio;
	}

	public void setListSeleccionSocio(List listSeleccionSocio) {
		this.listSeleccionSocio = listSeleccionSocio;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getOcurrencia() {
		return ocurrencia;
	}

	public void setOcurrencia(String buscar) {
		this.ocurrencia = buscar;
	}

	public BigDecimal getIdtelemark() {
		return idtelemark;
	}

	public void setIdtelemark(BigDecimal idtelemark) {
		this.idtelemark = idtelemark;
	}

	public BigDecimal getIdcampacabe() {
		return idcampacabe;
	}

	public void setIdcampacabe(BigDecimal idcampacabe) {
		this.idcampacabe = idcampacabe;
	}

	public String getCampacabe() {
		return campacabe;
	}

	public void setCampacabe(String campacabe) {
		this.campacabe = campacabe;
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

	public BigDecimal getIdresultado() {
		return idresultado;
	}

	public void setIdresultado(BigDecimal idresultado) {
		this.idresultado = idresultado;
	}

	public List getListidresultado() {
		return listidresultado;
	}

	public void setListidresultado(List listidresultado) {
		this.listidresultado = listidresultado;
	}

	public String getValidar() {
		return validar;
	}

	public void setValidar(String validar) {
		this.validar = validar;
	}

	public List getListCampaniasActivas() {
		return listCampaniasActivas;
	}

	public void setListCampaniasActivas(List listCampaniasActivas) {
		this.listCampaniasActivas = listCampaniasActivas;
	}

	public String getUsuarioalt() {
		return usuarioalt;
	}

	public void setUsuarioalt(String usuarioalt) {
		this.usuarioalt = usuarioalt;
	}

	public String getBuscarsocio() {
		return buscarsocio;
	}

	public void setBuscarsocio(String buscarsocio) {
		this.buscarsocio = buscarsocio;
	}

}
