/* 
 javabean para la entidad (Formulario): Cajaferiados
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Aug 01 11:33:07 GMT-03:00 2006 
 
 Para manejar la pagina: CajaferiadosFrm.jsp
 
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
import java.math.*;
import java.util.*;

import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanConsultaEstadosActual implements SessionBean,
		Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);
	
	private BigDecimal idcliente;
	
	private String cliente = "";
	
	static Logger log = Logger.getLogger(BeanConsultaEstadosActual.class);

	private String validar = "";

	private int limit = 15;

	private long offset = 0l;

	private long paginaSeleccion = 1l;
	
	private long totalRegistros = 0l;

	private long totalPaginas = 0l;
	
	private String usuarioalt;
	
	private String ocurrencia = "";

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private List MovimientosList = new ArrayList();

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";
	
	boolean buscar = false;

	public BeanConsultaEstadosActual() {
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

/*
	public void ejecutarSentenciaDML() {
		try {
			General Consulta = Common.getGeneral();
	        this.MovimientosList = Consulta.Clientesesstadosbajasuspensionlarga(this.idempresa);
 
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}
*/
	public boolean ejecutarValidacion() {
		   RequestDispatcher dispatcher = null;
		   General Consulta = Common.getGeneral();
		try {
			
		    if (this.accion.equalsIgnoreCase("modificacion")) {
			    if (idcliente == null) {
				    this.mensaje = "Debe seleccionar un registro.";
				} else { 
		    	    dispatcher = request.getRequestDispatcher("consestadoactualFrm.jsp");
		            dispatcher.forward(request, response);
		            return true;
		        }
		     }		
		     if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
		         if (ocurrencia.indexOf("'") >= 0) {
		            this.mensaje = "Caracteres invalidos en campo busqueda.";
		         } else {
		           buscar = true;
		         }
		      }
		    
		    
		      if (buscar) {
		          String[] campos = { "vceh.idcliente", "vceh.razon","vceh.estado","vceh.motivo","cv.vendedor" };
		          this.totalRegistros = Consulta.getTotalEntidadOcuporempresaEstado(campos, this.ocurrencia,this.idempresa);
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
		          this.MovimientosList = Consulta.ClientesesstadosbajasuspensionlargaOcu(this.limit,this.offset,this.idempresa,this.ocurrencia);
		          } else {
		             this.totalRegistros = Consulta.getTotalEntidadporempresaEstado(this.idempresa);
		             this.totalPaginas = (this.totalRegistros / this.limit) + 1; 
		             if (this.totalPaginas < this.paginaSeleccion)
		                this.paginaSeleccion = this.totalPaginas;
		             this.offset = (this.paginaSeleccion - 1) * this.limit;
		             if (this.totalRegistros == this.limit) {
		                this.offset = 0;
		                this.totalPaginas = 1;
		             }
			        this.MovimientosList = Consulta.Clientesesstadosbajasuspensionlarga(this.limit,this.offset,this.idempresa);				
						//this.mensaje = "";
		          }
		          if (this.totalRegistros < 1)
		             this.mensaje = "No existen registros.";
		   } catch (Exception e) { 
		      log.error("ejecutarValidacion()" + e);
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


	public List getMovimientosList() {
		return MovimientosList;
	}

	public void setMovimientosList(List movimientosList) {
		MovimientosList = movimientosList;
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

	public String getOcurrencia() {
		return ocurrencia;
	}

	public void setOcurrencia(String ocurrencia) {
		this.ocurrencia = ocurrencia;
	}

	public long getPaginaSeleccion() {
		return paginaSeleccion;
	}

	public void setPaginaSeleccion(long paginaSeleccion) {
		this.paginaSeleccion = paginaSeleccion;
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


}
