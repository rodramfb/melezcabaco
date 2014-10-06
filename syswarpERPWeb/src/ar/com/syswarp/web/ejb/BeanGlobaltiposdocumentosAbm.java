/* 
   javabean para la entidad: globaltiposdocumentos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Mar 28 09:45:19 ART 2008 
   
   Para manejar la pagina: globaltiposdocumentosAbm.jsp
      
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
public class BeanGlobaltiposdocumentosAbm implements SessionBean, Serializable {
   static Logger log = Logger.getLogger(BeanGlobaltiposdocumentosAbm.class);
   private SessionContext context;
   private int limit = 15;
   private long offset = 0l;
   private long totalRegistros = 0l;
   private long totalPaginas = 0l;
   private long paginaSeleccion = 1l;
   private List globaltiposdocumentosList = new ArrayList();
   private String accion = "";
   private String ocurrencia = "";
   private BigDecimal idtipodocumento;
   private String tipodocumento ="";
   private BigDecimal idempresa;
   private String mensaje = "";
   private HttpServletRequest request;
   private HttpServletResponse response;
   boolean buscar = false;
   
public BeanGlobaltiposdocumentosAbm() { super(); }

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
   General globaltiposdocumentos = Common.getGeneral();
   try {
      if (this.accion.equalsIgnoreCase("baja")) {
        	 if (idtipodocumento == null
						|| idtipodocumento.compareTo(new BigDecimal(0)) == 0) {	 
            this.mensaje = "Debe seleccionar un registro a eliminar.";
         } else {
            this.mensaje = globaltiposdocumentos.globaltiposdocumentosDelete(this.idtipodocumento,this.idempresa);
         }
      }
      if (this.accion.equalsIgnoreCase("modificacion")) {
    	  if (idtipodocumento == null
					|| idtipodocumento.compareTo(new BigDecimal(0)) == 0) {
            this.mensaje = "Debe seleccionar un registro a modificar.";
         } else {
            dispatcher = request.getRequestDispatcher("globaltiposdocumentosFrm.jsp");
            dispatcher.forward(request, response);
            return true;
         }
      }
      if (this.accion.equalsIgnoreCase("alta")) {
         dispatcher = request.getRequestDispatcher("globaltiposdocumentosFrm.jsp");
         dispatcher.forward(request, response);
         return true;
      }
      if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
         if (ocurrencia.indexOf("'") >= 0) {
            this.mensaje = "Caracteres invalidos en campo busqueda.";
         } else {
           buscar = true;
         }
      }
      if (buscar) {
         String[] campos = { "idtipodocumento", "tipodocumento" };
         this.totalRegistros = globaltiposdocumentos.getTotalEntidadOcu("globaltiposdocumentos", campos, this.ocurrencia);
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
         this.globaltiposdocumentosList = globaltiposdocumentos.getGlobaltiposdocumentosOcu(this.limit,this.offset, this.ocurrencia,this.idempresa);
         } else {
            this.totalRegistros = globaltiposdocumentos.getTotalEntidad("globaltiposdocumentos");
            this.totalPaginas = (this.totalRegistros / this.limit) + 1; 
            if (this.totalPaginas < this.paginaSeleccion)
               this.paginaSeleccion = this.totalPaginas;
            this.offset = (this.paginaSeleccion - 1) * this.limit;
            if (this.totalRegistros == this.limit) {
               this.offset = 0;
               this.totalPaginas = 1;
            }
            this.globaltiposdocumentosList = globaltiposdocumentos.getGlobaltiposdocumentosAll(this.limit,this.offset,this.idempresa);
         }
         if (this.totalRegistros < 1)
            this.mensaje = "No existen registros.";
  } catch (Exception e) { 
     log.error("ejecutarValidacion()" + e);
  }
  return true;
}

 public int getLimit() { return limit; }
 public void setLimit(int limit) { this.limit = limit; }
 public long getOffset() { return offset; }
 public void setOffset(long offset) { this.offset = offset; }
 public long getTotalRegistros() { return totalRegistros; }
 public void setTotalRegistros(long total) { this.totalRegistros = total; }
 public long getTotalPaginas() { return totalPaginas; }
 public void setTotalPaginas(long totalPaginas) { this.totalPaginas = totalPaginas; }
 public long getPaginaSeleccion() { return paginaSeleccion; }
 public void setPaginaSeleccion(long paginaSeleccion) { this.paginaSeleccion = paginaSeleccion; }
 
 public List getGlobaltiposdocumentosList() { return globaltiposdocumentosList; }
 public void setGlobaltiposdocumentosList(List globaltiposdocumentosList) { this.globaltiposdocumentosList = globaltiposdocumentosList; }
 public String getAccion() { return accion; }
 public void setAccion(String accion) { this.accion = accion; }
 public String getOcurrencia() { return ocurrencia; }
 public void setOcurrencia(String buscar) { this.ocurrencia = buscar; }
 public BigDecimal getIdtipodocumento(){ return idtipodocumento; }
 public void setIdtipodocumento(BigDecimal idtipodocumento){ this.idtipodocumento=idtipodocumento; }
 public String getMensaje() { return mensaje; }
 public void setMensaje(String mensaje) { this.mensaje = mensaje; }
 public HttpServletRequest getRequest() { return request; }
 public void setRequest(HttpServletRequest request) { this.request = request; }
 public HttpServletResponse getResponse() { return response; }
 public void setResponse(HttpServletResponse response) { this.response = response; }

public BigDecimal getIdempresa() {
	return idempresa;
}

public void setIdempresa(BigDecimal idempresa) {
	this.idempresa = idempresa;
}

public String getTipodocumento() {
	return tipodocumento;
}

public void setTipodocumento(String tipodocumento) {
	this.tipodocumento = tipodocumento;
}
}

