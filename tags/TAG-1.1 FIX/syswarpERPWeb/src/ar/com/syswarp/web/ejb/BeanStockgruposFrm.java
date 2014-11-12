/* 
 javabean para la entidad (Formulario): Stockgrupos
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Mon Sep 04 09:20:45 GMT-03:00 2006 
 
 Para manejar la pagina: StockgruposFrm.jsp
 
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

public class BeanStockgruposFrm implements SessionBean, Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanStockgruposFrm.class);

	private String validar = "";

	private BigDecimal codigo_gr = BigDecimal.valueOf(-1);

	private String descrip_gr = "";

	private String codigo_fm = "";

	// descripcion para el lov de familia
	private String d_codigo_fm = "";

	private String codigo_gr_pa = "";

	// descripcion para el lov de grupo
	private String d_codigo_gr_pa = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public BeanStockgruposFrm() {
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
			Stock Stockgrupos = Common.getStock();
			if (this.accion.equalsIgnoreCase("alta")) {
				this.mensaje = Stockgrupos.StockgruposCreate(this.descrip_gr,
						this.codigo_fm, this.codigo_gr_pa, this.usuarioalt,
						this.idempresa);
			} else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = Stockgrupos.StockgruposUpdate(this.codigo_gr,
						this.descrip_gr, this.codigo_fm, this.codigo_gr_pa,
						this.usuarioact, this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = Stockgrupos.StockgruposDelete(this.codigo_gr,
						this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosStockgrupos() {
		try {
			Stock Stockgrupos = Common.getStock();
			log.info("codigo_gr:" + codigo_gr);
			List listStockgrupos = Stockgrupos.getStockgruposPK(this.codigo_gr,
					this.idempresa);
			Iterator iterStockgrupos = listStockgrupos.iterator();

			if (iterStockgrupos.hasNext()) {
				String[] uCampos = (String[]) iterStockgrupos.next();
				// TODO: Constructores para cada tipo de datos
				this.codigo_gr = BigDecimal.valueOf(Long.parseLong(uCampos[0]));
				this.descrip_gr = uCampos[1];
				this.codigo_fm = uCampos[2];
				this.d_codigo_fm = uCampos[3];
				this.codigo_gr_pa = uCampos[4];
				this.d_codigo_gr_pa = uCampos[5];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosStockgrupos()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("StockgruposAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					if (this.accion.equalsIgnoreCase("Alta")) {
						if (codigo_fm == "" && codigo_gr_pa == "") {
							this.mensaje = "Error Por favor seleccione la Familia o el Codigo Padre!";
							return false;
						}
						if (!codigo_fm.equals("") && !codigo_gr_pa.equals("")) {
							this.mensaje = "Error No se puede cargar la Familia y el Codigo Padre!";
							return false;
						}
						if (!codigo_gr_pa.equals("")
								&& codigo_gr.compareTo(new BigDecimal(
										codigo_gr_pa)) == 0) {
							this.mensaje = "Error No se puede cargar el Grupo Padre con el mismo Grupo!";
							return false;
						}
					}
					if (this.accion.equalsIgnoreCase("Modificacion")) {
						if (codigo_fm == "null" && codigo_gr_pa == "null") {
							this.mensaje = "Error Por favor seleccione la Familia o el Codigo Padre!";
							return false;
						}
						if (!codigo_fm.equals("null")
								&& !codigo_gr_pa.equals("null")) {
							this.mensaje = "Error No se puede cargar la Familia y el Codigo Padre!";
							return false;
						}
						if (!codigo_gr_pa.equals("null")
								&& codigo_gr.compareTo(new BigDecimal(
										codigo_gr_pa)) == 0) {
							this.mensaje = "Error No se puede cargar el Grupo Padre con el mismo Grupo!";
							return false;
						}
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosStockgrupos();
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
	public BigDecimal getCodigo_gr() {
		return codigo_gr;
	}

	public void setCodigo_gr(BigDecimal codigo_gr) {
		this.codigo_gr = codigo_gr;
	}

	public String getDescrip_gr() {
		return descrip_gr;
	}

	public void setDescrip_gr(String descrip_gr) {
		this.descrip_gr = descrip_gr;
	}

	public String getCodigo_fm() {
		return codigo_fm;
	}

	public void setCodigo_fm(String codigo_fm) {
		this.codigo_fm = codigo_fm;
	}

	public String getCodigo_gr_pa() {
		return codigo_gr_pa;
	}

	public void setCodigo_gr_pa(String codigo_gr_pa) {
		this.codigo_gr_pa = codigo_gr_pa;
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

	// para que me levante las descripciones de familia
	public String getD_codigo_fm() {
		return d_codigo_fm;
	}

	public void setD_codigo_fm(String d_codigo_fm) {
		this.d_codigo_fm = d_codigo_fm;
	}

	// para que me levante las descripciones de grupos
	public String getD_codigo_gr_pa() {
		return d_codigo_gr_pa;
	}

	public void setD_codigo_gr_pa(String d_codigo_gr_pa) {
		this.d_codigo_gr_pa = d_codigo_gr_pa;
	}

}
