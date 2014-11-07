/* 
   javabean para la entidad (Formulario): stockiva
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Aug 10 13:02:55 ACT 2009 
   
   Para manejar la pagina: stockivaFrm.jsp
      
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

public class BeanNavegacionForm implements SessionBean, Serializable {
	
	private SessionContext context;
	
	static Logger log = Logger.getLogger(BeanNavegacionForm.class);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 1l;

	private long paginaSeleccion = 1l;

	private String ocurrencia = "";

	public BeanNavegacionForm() {
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

	public String getHtmlHiddenValues() {
		String salida = "";

		try {
			
			salida = "";
			

		} catch (Exception e) {
			// TODO: handle exception
		}

		return salida;
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

	public String getOcurrencia() {
		return ocurrencia;
	}

	public void setOcurrencia(String ocurrencia) {
		this.ocurrencia = ocurrencia;
	}

}
