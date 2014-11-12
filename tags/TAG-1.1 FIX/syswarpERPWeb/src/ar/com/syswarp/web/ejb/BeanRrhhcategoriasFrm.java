/* 
 javabean para la entidad (Formulario): rrhhcategorias
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Thu Dec 14 15:41:58 GMT-03:00 2006 
 
 Para manejar la pagina: rrhhcategoriasFrm.jsp
 
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

public class BeanRrhhcategoriasFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanRrhhcategoriasFrm.class);

	private String validar = "";

	private BigDecimal idcategoria = BigDecimal.valueOf(-1);

	private BigDecimal idempresa;

	private String categoria = "";

	private String hs = "";

	private String hs1 = "";

	private String hs2 = "";

	private String hs3 = "";

	private String hs4 = "";

	private String quinmens = "";

	private String basico = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanRrhhcategoriasFrm() {
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
			RRHH rrhhcategorias = Common.getRrhh();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = rrhhcategorias.rrhhcategoriasCreate(
						this.categoria, new Double(this.hs), new Double(
								this.hs1), new Double(this.hs2), new Double(
								this.hs3), new Double(this.hs4), this.quinmens,
						new BigDecimal(this.basico), this.usuarioalt,
						this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = rrhhcategorias.rrhhcategoriasUpdate(
						this.idcategoria, this.categoria, new Double(this.hs),
						new Double(this.hs1), new Double(this.hs2), new Double(
								this.hs3), new Double(this.hs4), this.quinmens,
						new BigDecimal(this.basico), this.usuarioact,
						this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = rrhhcategorias.rrhhcategoriasDelete(
						this.idcategoria, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosRrhhcategorias() {
		try {
			RRHH rrhhcategorias = Common.getRrhh();
			List listRrhhcategorias = rrhhcategorias.getRrhhcategoriasPK(
					this.idcategoria, this.idempresa);
			Iterator iterRrhhcategorias = listRrhhcategorias.iterator();
			if (iterRrhhcategorias.hasNext()) {
				String[] uCampos = (String[]) iterRrhhcategorias.next();
				// TODO: Constructores para cada tipo de datos
				this.idcategoria = BigDecimal.valueOf(Long
						.parseLong(uCampos[0]));
				this.categoria = uCampos[1];
				this.hs = uCampos[2];
				this.hs1 = uCampos[3];
				this.hs2 = uCampos[4];
				this.hs3 = uCampos[5];
				this.hs4 = uCampos[6];
				this.quinmens = uCampos[7];
				this.basico = uCampos[8];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosRrhhcategorias()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("rrhhcategoriasAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (categoria.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Categoria  ";
						return false;
					}

					if (!Common.esNumerico(hs)) {
						this.mensaje = "Ingrese valores validos para V.Hora. ";
						return false;
					}
					if (!Common.esNumerico(hs1)) {
						this.mensaje = "Ingrese valores validos para V.Hora 1. ";
						return false;
					}
					if (!Common.esNumerico(hs2)) {
						this.mensaje = "Ingrese valores validos para V.Hora 2. ";
						return false;
					}
					if (!Common.esNumerico(hs3)) {
						this.mensaje = "Ingrese valores validos para V.Hora 3. ";
						return false;
					}
					if (!Common.esNumerico(hs4)) {
						this.mensaje = "Ingrese valores validos para V.Hora 4. ";
						return false;
					}
					if (!Common.esNumerico(basico)) {
						this.mensaje = "Ingrese valores validos para basico. ";
						return false;
					}

					if (Double.parseDouble(hs)<=0d) {
						this.mensaje = "El importe del valor hora debe ser mayor a cero";
						return false;
					}
					if (Double.parseDouble(hs1)<=0d) {
						this.mensaje = "El importe del V.Hora 1  debe ser mayor a cero";
						return false;
					}
					if (Double.parseDouble(hs2)<=0d) {
						this.mensaje = "El importe del V.Hora 2  debe ser mayor a cero";
						return false;
					}
					if (Double.parseDouble(hs3)<=0d) {
						this.mensaje = "El importe del V.Hora 3  debe ser mayor a cero";
						return false;
					}
					if (Double.parseDouble(hs4)<=0d) {
						this.mensaje = "El importe del V.Hora 4  debe ser mayor a cero";
						return false;
					}					
					if (Double.parseDouble(basico)<=0d) {
						this.mensaje = "El importe del Basico  debe ser mayor a cero";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosRrhhcategorias();
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
	public BigDecimal getIdcategoria() {
		return idcategoria;
	}

	public void setIdcategoria(BigDecimal idcategoria) {
		this.idcategoria = idcategoria;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getQuinmens() {
		return quinmens;
	}

	public void setQuinmens(String quinmens) {
		this.quinmens = quinmens;
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

	public void setHs(String hs) {
		this.hs = hs;
	}

	public void setHs1(String hs1) {
		this.hs1 = hs1;
	}

	public void setHs2(String hs2) {
		this.hs2 = hs2;
	}

	public String getHs() {
		return hs;
	}

	public String getHs1() {
		return hs1;
	}

	public String getHs2() {
		return hs2;
	}

	public String getHs3() {
		return hs3;
	}

	public String getHs4() {
		return hs4;
	}

	public void setHs3(String hs3) {
		this.hs3 = hs3;
	}

	public void setHs4(String hs4) {
		this.hs4 = hs4;
	}

	public String getBasico() {
		return basico;
	}

	public void setBasico(String basico) {
		this.basico = basico;
	}

}
