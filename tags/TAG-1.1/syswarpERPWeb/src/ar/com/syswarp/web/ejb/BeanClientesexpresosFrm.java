/* 
 javabean para la entidad (Formulario): clientesexpresos
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Nov 14 15:48:52 GMT-03:00 2006 
 
 Para manejar la pagina: clientesexpresosFrm.jsp
 
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

public class BeanClientesexpresosFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanClientesexpresosFrm.class);

	private String validar = "";

	private BigDecimal idexpreso = BigDecimal.valueOf(-1);

	private BigDecimal idempresa = BigDecimal.valueOf(-1);

	private String expreso = "";

	private String cuit = "";

	private String direccion = "";

	private BigDecimal idlocalidad = BigDecimal.valueOf(0);

	private String localidad = "";

	private BigDecimal idtipoiva = BigDecimal.valueOf(0);

	private String tipoiva = "";

	private Double valor_seguro = Double.valueOf("0");

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private BigDecimal codigo_dt = new BigDecimal(-1);

	private String report_hojaarmado = "";

	private String report_hojarutafinal = "";

	private String report_hojarutafinal_reg = "";

	private List listDepositos = new ArrayList();

	// 20120215 - EJV - Mantis 817 -- >

	private BigDecimal idmedidabulto = new BigDecimal(-1);

	private String calculaflete = "";

	private String discriminaflete = "";

	private String valorapagar1bulto = "0.0";

	private String valorapagarexcedente = "0.0";

	private String calculaiva = "";
	
	private List listMedidas = new ArrayList();

	// <--

	public BeanClientesexpresosFrm() {
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
			Clientes clientesexpresos = Common.getClientes();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = clientesexpresos.clientesexpresosCreate(
						this.expreso, this.cuit, this.direccion,
						this.idlocalidad, this.idtipoiva, this.valor_seguro,
						this.report_hojaarmado, this.report_hojarutafinal,
						this.codigo_dt, this.report_hojarutafinal_reg,

						this.idmedidabulto, this.calculaflete,
						this.discriminaflete, new BigDecimal(
								this.valorapagar1bulto), new BigDecimal(
								this.valorapagarexcedente), this.calculaiva,

						this.usuarioalt, this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clientesexpresos.clientesexpresosUpdate(
						this.idexpreso, this.expreso, this.cuit,
						this.direccion, this.idlocalidad, this.idtipoiva,
						this.valor_seguro, this.report_hojaarmado,
						this.report_hojarutafinal, this.codigo_dt,
						this.report_hojarutafinal_reg,

						this.idmedidabulto, this.calculaflete,
						this.discriminaflete, new BigDecimal(
								this.valorapagar1bulto), new BigDecimal(
								this.valorapagarexcedente), this.calculaiva,

						this.usuarioact, this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clientesexpresos.clientesexpresosDelete(
						this.idexpreso, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosClientesexpresos() {
		try {
			Clientes clientesexpresos = Common.getClientes();
			List listClientesexpresos = clientesexpresos.getClientesexpresosPK(
					this.idexpreso, this.idempresa);
			Iterator iterClientesexpresos = listClientesexpresos.iterator();
			if (iterClientesexpresos.hasNext()) {
				String[] uCampos = (String[]) iterClientesexpresos.next();
				// TODO: Constructores para cada tipo de datos
				this.idexpreso = new BigDecimal(uCampos[0]);
				this.expreso = uCampos[1];
				this.cuit = uCampos[2];
				this.direccion = uCampos[3];
				this.idlocalidad = new BigDecimal(uCampos[4]);
				this.localidad = uCampos[5];
				this.idtipoiva = new BigDecimal(uCampos[6]);
				this.tipoiva = uCampos[7];
				this.valor_seguro = new Double(uCampos[8]);
				this.report_hojaarmado = uCampos[9];
				this.report_hojarutafinal = uCampos[10];
				this.codigo_dt = new BigDecimal(uCampos[11]);
				this.report_hojarutafinal_reg = uCampos[12];
				// 20120215 - EJV - Mantis 817 -- >
				this.idmedidabulto = new BigDecimal(uCampos[13]);
				this.calculaflete = uCampos[14];
				this.discriminaflete = uCampos[15];
				this.valorapagar1bulto = uCampos[16];
				this.valorapagarexcedente = uCampos[17];
				this.calculaiva = uCampos[18];
				// <--
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosClientesexpresos()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("clientesexpresosAbm.jsp");
				return true;
			}

			this.listDepositos = Common.getStock().getStockdepositosAll(300, 0,
					this.idempresa);

			this.listMedidas = Common.getStock().getStockmedidasAll(300, 0, this.idempresa);
			
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (Common.setNotNull(expreso).equals("")) {
						this.mensaje = "No se puede dejar vacio el campo expreso ";
						return false;
					}

					if (codigo_dt == null || codigo_dt.longValue() < 0) {
						this.mensaje = "No se puede dejar vacio el campo deposito ";
						return false;
					}

					if (cuit == null) {
						this.mensaje = "No se puede dejar vacio el campo cuit ";
						return false;
					}
					if (direccion == null) {
						this.mensaje = "No se puede dejar vacio el campo direccion ";
						return false;
					}
					if (idlocalidad == null) {
						this.mensaje = "No se puede dejar vacio el campo idlocalidad ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (expreso.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Expreso  ";
						return false;
					}
					if (cuit.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Cuit  ";
						return false;
					}
					if (direccion.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Direccion  ";
						return false;
					}

					if (localidad.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Localidad  ";
						return false;
					}

					if (tipoiva.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Tipo iva  ";
						return false;
					}

					if (Common.setNotNull(report_hojaarmado).equals("")) {
						this.mensaje = "No se puede dejar vacio el campo Report Hoja Armado  ";
						return false;
					}

					if (Common.setNotNull(report_hojarutafinal).equals("")) {
						this.mensaje = "No se puede dejar vacio el campo Report Hoja Armado  ";
						return false;
					}

					if (this.idmedidabulto == null
							|| this.idmedidabulto.intValue() < 1) {
						this.mensaje = "Es necesario seleccionar unidad de medida.  ";
						return false;
					}

					if (Common.setNotNull(this.calculaflete).equals("")) {
						this.mensaje = "Es necesario seleccionar Calcula Flete  ";
						return false;
					}

					if (Common.setNotNull(this.discriminaflete).equals("")) {
						this.mensaje = "Es necesario seleccionar Discrimina Flete  ";
						return false;
					}

					if (!Common.esNumerico(this.valorapagar1bulto)) {
						this.mensaje = "Ingrese valores validos para Valor Primer Bulto  ";
						return false;
					}

					if (!Common.esNumerico(this.valorapagarexcedente)) {
						this.mensaje = "Ingrese valores validos para Valor Bulto Excedente ";
						return false;
					}

					if (Common.setNotNull(this.calculaiva).equals("")) {
						this.mensaje = "Es necesario seleccionar Calcula Iva  ";
						return false;
					}

				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosClientesexpresos();
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
	public BigDecimal getIdexpreso() {
		return idexpreso;
	}

	public void setIdexpreso(BigDecimal idexpreso) {
		this.idexpreso = idexpreso;
	}

	public String getExpreso() {
		return expreso;
	}

	public void setExpreso(String expreso) {
		this.expreso = expreso;
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public BigDecimal getIdlocalidad() {
		return idlocalidad;
	}

	public void setIdlocalidad(BigDecimal idlocalidad) {
		this.idlocalidad = idlocalidad;
	}

	public BigDecimal getIdtipoiva() {
		return idtipoiva;
	}

	public void setIdtipoiva(BigDecimal idtipoiva) {
		this.idtipoiva = idtipoiva;
	}

	public Double getValor_seguro() {
		return valor_seguro;
	}

	public void setValor_seguro(Double valor_seguro) {
		this.valor_seguro = valor_seguro;
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

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getTipoiva() {
		return tipoiva;
	}

	public void setTipoiva(String tipoiva) {
		this.tipoiva = tipoiva;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public BigDecimal getCodigo_dt() {
		return codigo_dt;
	}

	public void setCodigo_dt(BigDecimal codigo_dt) {
		this.codigo_dt = codigo_dt;
	}

	public String getReport_hojarutafinal() {
		return report_hojarutafinal;
	}

	public void setReport_hojarutafinal(String report_hojarutafinal) {
		this.report_hojarutafinal = report_hojarutafinal;
	}

	public String getReport_hojarutafinal_reg() {
		return report_hojarutafinal_reg;
	}

	public void setReport_hojarutafinal_reg(String report_hojarutafinal_reg) {
		this.report_hojarutafinal_reg = report_hojarutafinal_reg;
	}

	public List getListDepositos() {
		return listDepositos;
	}

	public void setListDepositos(List listDepositos) {
		this.listDepositos = listDepositos;
	}

	public String getReport_hojaarmado() {
		return report_hojaarmado;
	}

	public void setReport_hojaarmado(String report_hojaarmado) {
		this.report_hojaarmado = report_hojaarmado;
	}

	// 20120215 - EJV - Mantis 817 -- >

	public BigDecimal getIdmedidabulto() {
		return idmedidabulto;
	}

	public void setIdmedidabulto(BigDecimal idmedidabulto) {
		this.idmedidabulto = idmedidabulto;
	}

	public String getCalculaflete() {
		return calculaflete;
	}

	public void setCalculaflete(String calculaflete) {
		this.calculaflete = calculaflete;
	}

	public String getDiscriminaflete() {
		return discriminaflete;
	}

	public void setDiscriminaflete(String discriminaflete) {
		this.discriminaflete = discriminaflete;
	}

	public String getValorapagar1bulto() {
		return valorapagar1bulto;
	}

	public void setValorapagar1bulto(String valorapagar1bulto) {
		this.valorapagar1bulto = valorapagar1bulto;
	}

	public String getValorapagarexcedente() {
		return valorapagarexcedente;
	}

	public void setValorapagarexcedente(String valorapagarexcedente) {
		this.valorapagarexcedente = valorapagarexcedente;
	}

	public String getCalculaiva() {
		return calculaiva;
	}

	public void setCalculaiva(String calculaiva) {
		this.calculaiva = calculaiva;
	}

	public List getListMedidas() {
		return listMedidas;
	}

		
	// <--


}
