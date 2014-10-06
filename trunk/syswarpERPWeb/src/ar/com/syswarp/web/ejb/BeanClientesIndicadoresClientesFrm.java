/* 
   javabean para la entidad (Formulario): clientesIndicadoresClientes
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Apr 30 15:18:45 GMT-03:00 2010 
   
   Para manejar la pagina: clientesIndicadoresClientesFrm.jsp
      
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

public class BeanClientesIndicadoresClientesFrm implements SessionBean,
		Serializable {

	private SessionContext context;

	static Logger log = Logger
			.getLogger(BeanClientesIndicadoresClientesFrm.class);

	private String validar = "";

	private BigDecimal iddato = new BigDecimal(-1);

	private BigDecimal idcliente = new BigDecimal(-1);

	private String cliente = "";

	private BigDecimal idindicador = new BigDecimal(-1);

	private String valor = "";

	private BigDecimal idempresa = new BigDecimal(-1);

	private String usuarioalt = "";

	private String usuarioact = "";

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "alta";

	private List listIndicadoresClientes = new ArrayList();

	// --

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List clientesIndicadoresClientesList = new ArrayList();

	private String ocurrencia = "";

	private boolean buscar = false;

	// --
	public BeanClientesIndicadoresClientesFrm() {
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
				this.mensaje = clientes.clientesIndicadoresClientesCreate(
						this.idcliente, this.idindicador, new BigDecimal(
								this.valor), this.idempresa, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clientes.clientesIndicadoresClientesUpdate(
						this.iddato, this.idcliente, this.idindicador,
						new BigDecimal(this.valor), this.idempresa,
						this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja")) {
				this.mensaje = clientes.clientesIndicadoresClientesDelete(
						this.iddato, this.idempresa);

				this.reiniciaData();

			}

			this.getListIndicadoresCliente();

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosClientesIndicadoresClientes() {
		try {
			Clientes clientes = Common.getClientes();
			List listClientesIndicadoresClientes = clientes
					.getClientesIndicadoresClientesPK(this.iddato,
							this.idempresa);
			Iterator iterClientesIndicadoresClientes = listClientesIndicadoresClientes
					.iterator();
			if (iterClientesIndicadoresClientes.hasNext()) {
				String[] uCampos = (String[]) iterClientesIndicadoresClientes
						.next();
				// TODO: Constructores para cada tipo de datos
				this.iddato = new BigDecimal(uCampos[0]);
				this.idcliente = new BigDecimal(uCampos[1]);
				this.cliente = uCampos[2];
				this.idindicador = new BigDecimal(uCampos[3]);
				this.valor = uCampos[5];
				this.idempresa = new BigDecimal(uCampos[6]);
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosClientesIndicadoresClientes()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("clientesIndicadoresClientesAbm.jsp");
				return true;
			}

			this.listIndicadoresClientes = Common.getClientes()
					.getClientesIndicadoresManualesAll(3000, 0, this.idempresa);

			if (this.accion.equalsIgnoreCase("recarga")) {

				this.reiniciaData();

			}

			if (!this.validar.equalsIgnoreCase("")) {

				this.getListIndicadoresCliente();

				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (this.idcliente == null
							|| this.idcliente.longValue() < 0) {
						this.mensaje = "Es necesario seleccionar cliente. ";
						return false;
					}
					if (this.idindicador == null
							|| this.idindicador.longValue() < 0) {
						this.mensaje = "No se puede dejar vacio el campo indicador. ";
						return false;
					}
					// 2. len 0 para campos nulos

					if (!Common.esNumerico(Common.setNotNull(this.valor))) {
						this.mensaje = "Ingrese un dato numérico válido para valor. ";
						return false;

					}
					
					
					if(Integer.parseInt(this.valor)<0){
						this.mensaje = "El campo valor debe ser mayor a cero. ";
						return false;					
						
					}
					
					
				}

				this.ejecutarSentenciaDML();

			} else {

				if (this.idcliente == null || this.idcliente.longValue() < 0) {
					this.mensaje = "Es necesario seleccionar cliente. ";
				}

				if (this.accion.equalsIgnoreCase("modificacion")) {
					getDatosClientesIndicadoresClientes();
				} else if (this.accion.equalsIgnoreCase("baja"))
					this.ejecutarSentenciaDML();

				this.getListIndicadoresCliente();
			}

		} catch (Exception e) {
			log.error(" ejecutarValidacion(): " + e);
		}
		return true;
	}

	private void reiniciaData() {
		
		this.iddato = new BigDecimal(0);
		this.idindicador = new BigDecimal(0);
		this.valor = "";
		this.accion = "alta";

	}

	private void getListIndicadoresCliente() {

		try {

			if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else {
					buscar = true;
				}
			}

			Clientes clientes = Common.getClientes();
			String filtro = " WHERE idcliente = " + idcliente;
			if (buscar) {
				String[] campos = { "iddato", "idcliente" };
				this.totalRegistros = clientes.getTotalEntidadFiltro(
						"clientesIndicadoresClientes", filtro, this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				if (this.totalRegistros == this.limit)
					this.offset = 0;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.clientesIndicadoresClientesList = clientes
						.getClientesIndicadoresXClienteOcu(this.limit,
								this.offset, this.idcliente, this.ocurrencia,
								this.idempresa);
			} else {
				this.totalRegistros = clientes.getTotalEntidadFiltro(
						"clientesIndicadoresClientes", filtro, this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.clientesIndicadoresClientesList = clientes
						.getClientesIndicadoresXClienteAll(this.limit,
								this.offset, this.idcliente, this.idempresa);
			}
			if (this.totalRegistros < 1 && this.mensaje.equals(""))
				this.mensaje = "No existen registros.";

		} catch (Exception e) {

			log.error("getListCliente(): " + e);

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
	public BigDecimal getIddato() {
		return iddato;
	}

	public void setIddato(BigDecimal iddato) {
		this.iddato = iddato;
	}

	public BigDecimal getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(BigDecimal idcliente) {
		this.idcliente = idcliente;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public BigDecimal getIdindicador() {
		return idindicador;
	}

	public void setIdindicador(BigDecimal idindicador) {
		this.idindicador = idindicador;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
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

	public List getListIndicadoresClientes() {
		return listIndicadoresClientes;
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

	public void setTotalRegistros(long totalRegistros) {
		this.totalRegistros = totalRegistros;
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

	public List getClientesIndicadoresClientesList() {
		return clientesIndicadoresClientesList;
	}

	public void setClientesIndicadoresClientesList(
			List clientesIndicadoresClientesList) {
		this.clientesIndicadoresClientesList = clientesIndicadoresClientesList;
	}

	public String getOcurrencia() {
		return ocurrencia;
	}

	public void setOcurrencia(String ocurrencia) {
		this.ocurrencia = ocurrencia;
	}

	// --

	// --

}
