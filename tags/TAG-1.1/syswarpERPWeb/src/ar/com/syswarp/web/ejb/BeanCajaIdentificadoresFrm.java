/* 
   javabean para la entidad (Formulario): cajaIdentificadores
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Oct 17 10:32:34 GMT-03:00 2008 
   
   Para manejar la pagina: cajaIdentificadoresFrm.jsp
      
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
import ar.com.syswarp.api.Strings;

public class BeanCajaIdentificadoresFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanCajaIdentificadoresFrm.class);

	private String validar = "";

	private BigDecimal ididentificador = new BigDecimal(-1);

	private BigDecimal idtipoidentificador = new BigDecimal(-1);

	private String tipoidentificador = "";

	private String identificador = "";

	private String descripcion = "";

	private BigDecimal cuenta = new BigDecimal(0);

	private BigDecimal idmoneda = new BigDecimal(0);

	private String moneda = "";

	private String modcta = "";

	private String factura = "";

	private String saldo_id = "0.00";

	private String saldo_disp = "0.00";

	private BigDecimal renglones = new BigDecimal(0);

	private BigDecimal ctacaucion = new BigDecimal(0);

	private BigDecimal ctatodoc = new BigDecimal(0);

	private String gerencia = "";

	private String formula = "";

	private BigDecimal cuotas = new BigDecimal(0);

	private BigDecimal presentacion = new BigDecimal(0);

	private BigDecimal ctacaudoc = new BigDecimal(0);

	private String porcentaje = "0.00";

	private BigDecimal ctadtotar = new BigDecimal(0);

	private BigDecimal ctatarjeta = new BigDecimal(0);

	private BigDecimal comhyper = new BigDecimal(0);

	private BigDecimal contador = new BigDecimal(0);

	private String afecomicob = "";

	private String impri_id = "";

	private String subdiventa = "";

	private String idcencosto = "0";

	private String idcencosto1 = "0";

	private String modicent = "";

	private BigDecimal prox_cheq = new BigDecimal(0);

	private BigDecimal prox_reserv = new BigDecimal(0);

	private BigDecimal ulti_cheq = new BigDecimal(0);

	private String modsubcent = "";

	private BigDecimal res_nro = new BigDecimal(0);

	private BigDecimal idempresa = new BigDecimal(-1);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private boolean primeraCarga = true;

	public BeanCajaIdentificadoresFrm() {
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
			Tesoreria cajaIdentificadores = Common.getTesoreria();

			Hashtable htCuotas = (Hashtable) request.getSession().getAttribute(
					"htValorTarC");
			Hashtable htPresentacion = (Hashtable) request.getSession()
					.getAttribute("htValorTarP");

			if (this.accion.equalsIgnoreCase("alta")) {
				this.mensaje = cajaIdentificadores.cajaIdentificadoresCreate(
						this.idtipoidentificador, this.identificador
								.toUpperCase(), this.descripcion, this.cuenta,
						this.idmoneda, this.modcta, this.factura, new Double(
								this.saldo_id), new Double(this.saldo_disp),
						this.renglones, this.ctacaucion, this.ctatodoc,
						this.gerencia, this.formula, this.cuotas,
						this.presentacion, this.ctacaudoc, new Double(
								this.porcentaje), this.ctadtotar,
						this.ctatarjeta, this.comhyper, this.contador,
						this.afecomicob, this.impri_id, this.subdiventa,
						this.idcencosto, this.idcencosto1, this.modicent,
						this.prox_cheq, this.prox_reserv, this.ulti_cheq,
						this.modsubcent, this.res_nro, htCuotas,
						htPresentacion, this.idempresa, this.usuarioalt);

				if (this.mensaje.equalsIgnoreCase("OK")) {
					this.mensaje = "Alta Correcta.";
				}

			} else if (this.accion.equalsIgnoreCase("modificacion")) {
				this.mensaje = cajaIdentificadores.cajaIdentificadoresUpdate(
						this.ididentificador, this.idtipoidentificador,
						this.identificador.toUpperCase(), this.descripcion,
						this.cuenta, this.idmoneda, this.modcta, this.factura,
						new Double(this.saldo_id), new Double(this.saldo_disp),
						this.renglones, this.ctacaucion, this.ctatodoc,
						this.gerencia, this.formula, this.cuotas,
						this.presentacion, this.ctacaudoc, new Double(
								this.porcentaje), this.ctadtotar,
						this.ctatarjeta, this.comhyper, this.contador,
						this.afecomicob, this.impri_id, this.subdiventa,
						this.idcencosto, this.idcencosto1, this.modicent,
						this.prox_cheq, this.prox_reserv, this.ulti_cheq,
						this.modsubcent, this.res_nro, htCuotas,
						htPresentacion, this.idempresa, this.usuarioact);

				if (this.mensaje.equalsIgnoreCase("OK")) {
					this.mensaje = "Actualizacion Correcta.";
				}

			}

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosCajaIdentificadores() {
		try {
			Tesoreria cajaIdentificadores = Common.getTesoreria();
			List listCajaIdentificadores = cajaIdentificadores
					.getCajaIdentificadoresPK(this.ididentificador,
							this.idempresa);
			Iterator iterCajaIdentificadores = listCajaIdentificadores
					.iterator();
			if (iterCajaIdentificadores.hasNext()) {
				String[] uCampos = (String[]) iterCajaIdentificadores.next();
				// TODO: Constructores para cada tipo de datos
				this.ididentificador = new BigDecimal(uCampos[0]);
				this.idtipoidentificador = new BigDecimal(uCampos[1]);
				this.tipoidentificador = uCampos[2];
				this.identificador = uCampos[3];
				this.descripcion = uCampos[4];
				this.cuenta = new BigDecimal(uCampos[5]);
				this.idmoneda = new BigDecimal(uCampos[6]);
				this.moneda = uCampos[7];
				this.modcta = uCampos[8];
				this.factura = uCampos[9];
				this.saldo_id = uCampos[10];
				this.saldo_disp = uCampos[11];
				this.renglones = new BigDecimal(uCampos[12]);
				this.ctacaucion = new BigDecimal(uCampos[13]);
				this.ctatodoc = new BigDecimal(uCampos[14]);
				this.gerencia = (uCampos[15]);
				this.formula = uCampos[16];
				this.cuotas = new BigDecimal(uCampos[17]);
				this.presentacion = new BigDecimal(uCampos[18]);
				this.ctacaudoc = new BigDecimal(uCampos[19]);
				this.porcentaje = uCampos[20];
				this.ctadtotar = new BigDecimal(uCampos[21]);
				this.ctatarjeta = new BigDecimal(uCampos[22]);
				this.comhyper = new BigDecimal(uCampos[23]);
				this.contador = new BigDecimal(uCampos[24]);
				this.afecomicob = uCampos[25];
				this.impri_id = uCampos[26];
				this.subdiventa = uCampos[27];
				this.idcencosto = uCampos[28];
				this.idcencosto1 = uCampos[29];
				this.modicent = uCampos[30];
				this.prox_cheq = new BigDecimal(uCampos[31]);
				this.prox_reserv = new BigDecimal(uCampos[32]);
				this.ulti_cheq = new BigDecimal(uCampos[33]);
				this.modsubcent = uCampos[34];
				this.res_nro = new BigDecimal(uCampos[35]);

				if (this.idtipoidentificador.intValue() == 7) {

					this.getDatosCajaValorTar("C");
					this.getDatosCajaValorTar("P");

				}

			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosCajaIdentificadores()" + e);
		}
	}

	public void getDatosCajaValorTar(String tipo) {
		try {

			Hashtable ht = new Hashtable();
			String[] numero = new String[] {};
			String[] dias = new String[] {};

			Tesoreria cajaValorTar = Common.getTesoreria();
			List listCajaValorTar = cajaValorTar.getCajaValorTarPK(
					this.identificador, tipo, this.idempresa);
			Iterator iterCajaValorTar = listCajaValorTar.iterator();
			if (iterCajaValorTar.hasNext()) {
				String auxnro = "";
				String auxdias = "";
				while (iterCajaValorTar.hasNext()) {
					String[] uCampos = (String[]) iterCajaValorTar.next();
					// TODO: Constructores para cada tipo de datos

					auxnro += uCampos[2] + "-";
					auxdias += uCampos[3] + "-";
				}

				auxnro = auxnro.substring(0, auxnro.lastIndexOf("-"));
				auxdias = auxdias.substring(0, auxdias.lastIndexOf("-"));

				numero = auxnro.split("-");
				dias = auxdias.split("-");

				ht.put("dias", dias);
				ht.put("numero", numero);
				request.getSession().setAttribute("htValorTar" + tipo, ht);

			}

		} catch (Exception e) {
			log.error("getDatosCajaValorTar()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {

			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("cajaIdentificadoresAbm.jsp");
				return true;
			}

			if (isPrimeraCarga()) {

				request.getSession().removeAttribute("htValorTarC");
				request.getSession().removeAttribute("htValorTarP");

				if (this.accion.equalsIgnoreCase("alta")) {
					this.ididentificador = new BigDecimal(-1);
					this.idtipoidentificador = new BigDecimal(-1);
					this.tipoidentificador = "";
					this.identificador = "";
					this.descripcion = "";
					this.cuenta = new BigDecimal(0);
					this.idmoneda = new BigDecimal(-1);
					this.moneda = "";
					this.modcta = "";
					this.factura = "";
					this.saldo_id = "0";
					this.saldo_disp = "0";
					this.renglones = new BigDecimal(0);
					this.ctacaucion = new BigDecimal(0);
					this.ctatodoc = new BigDecimal(0);
					this.gerencia = "";
					this.formula = "";
					this.cuotas = new BigDecimal(0);
					this.presentacion = new BigDecimal(0);
					this.ctacaudoc = new BigDecimal(0);
					this.porcentaje = "0";
					this.ctadtotar = new BigDecimal(0);
					this.ctatarjeta = new BigDecimal(0);
					this.comhyper = new BigDecimal(0);
					this.contador = new BigDecimal(0);
					this.afecomicob = "";
					this.impri_id = "";
					this.subdiventa = "";
					this.idcencosto = "0";
					this.idcencosto1 = "0";
					this.modicent = "";
					this.prox_cheq = new BigDecimal(0);
					this.prox_reserv = new BigDecimal(0);
					this.ulti_cheq = new BigDecimal(0);
					this.modsubcent = "";
					this.res_nro = new BigDecimal(0);
				}

			}

			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (idtipoidentificador == null
							|| idtipoidentificador.longValue() < 1) {
						this.mensaje = "No se puede dejar vacio el campo Tipo identificador. ";
						return false;
					}
					if (identificador == null
							|| identificador.trim().equals("")) {

						this.mensaje = "No se puede dejar vacio el campo identificador. ";
						return false;

					}
					if (descripcion == null || descripcion.trim().equals("")) {
						this.mensaje = "No se puede dejar vacio el campo descripcion. ";
						return false;
					}

					if (this.cuenta.longValue() <= 0) {
						this.mensaje = "Es necesario seleccionar cuenta. ";
						return false;
					}

					if (this.idmoneda.longValue() <= 0) {
						this.mensaje = "Es necesario seleccionar tipo de moneda. ";
						return false;
					}

					if (this.afecomicob.equals("")) {
						this.mensaje = "Es necesario seleccionar si afecta comisiones. ";
						return false;
					}

					if (this.modicent.equals("")) {
						this.mensaje = "Es necesario seleccionar si permite modificar CC. ";
						return false;
					}

					if (this.modicent.equals("S")) {
						if (this.modsubcent.equals("")) {
							this.mensaje = "Es necesario seleccionar si permite modificar CC1. ";
							return false;
						}
					} else
						this.modsubcent = "N";

					// Tipo identificador 6 - Varios
					if (this.idtipoidentificador.intValue() == 6) {

						if (this.modcta.equals("")) {
							this.mensaje = "Es necesario seleccionar si permite modificar cuenta. ";
							return false;
						}

						if (this.modcta.equalsIgnoreCase("S")) {
							this.factura = "N";
						}

						if (this.factura.equals(""))
							this.factura = "N";
					} else {
						this.factura = this.modcta = "N";
					}

					// Tipo identificador 7 - Tarjeta de terceros
					if (this.idtipoidentificador.intValue() == 7) {

						if (this.cuotas.intValue() < 1) {
							this.mensaje = "Nro. cuotas debe ser mayor a cero (0). ";
							return false;
						}

						if (request.getSession().getAttribute("htValorTarC") == null) {
							this.mensaje = "Ingrese dias para detalle de cuotas. ";
							return false;
						}

						String[] diasCuotas = (String[]) ((Hashtable) request
								.getSession().getAttribute("htValorTarC"))
								.get("dias");
						if (diasCuotas.length != cuotas.intValue()) {
							this.mensaje = "Dias definidos no coincide con la cantidad de cuotas.";
							return false;
						}

						if (this.presentacion.intValue() > 0) {
							if (request.getSession()
									.getAttribute("htValorTarP") == null) {
								this.mensaje = "Ingrese dias para detalle de presentacion. ";
								return false;
							}

							String[] diasPresent = (String[]) ((Hashtable) request
									.getSession().getAttribute("htValorTarP"))
									.get("dias");
							if (diasPresent.length != presentacion.intValue()) {
								this.mensaje = "Dias definidos no coincide con la cantidad de presentaciones.";
								return false;
							}
						}

						if (this.ctatarjeta.longValue() < 1) {
							this.mensaje = "Selececcione cuenta tarjeta.";
							return false;

						}

					}

				}

				this.ejecutarSentenciaDML();

			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosCajaIdentificadores();
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
	public BigDecimal getIdidentificador() {
		return ididentificador;
	}

	public void setIdidentificador(BigDecimal ididentificador) {
		this.ididentificador = ididentificador;
	}

	public BigDecimal getIdtipoidentificador() {
		return idtipoidentificador;
	}

	public void setIdtipoidentificador(BigDecimal idtipoidentificador) {
		this.idtipoidentificador = idtipoidentificador;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getCuenta() {
		return cuenta;
	}

	public void setCuenta(BigDecimal cuenta) {
		this.cuenta = cuenta;
	}

	public BigDecimal getIdmoneda() {
		return idmoneda;
	}

	public void setIdmoneda(BigDecimal idmoneda) {
		this.idmoneda = idmoneda;
	}

	public String getModcta() {
		return modcta;
	}

	public void setModcta(String modcta) {
		this.modcta = modcta;
	}

	public String getFactura() {
		return factura;
	}

	public void setFactura(String factura) {
		this.factura = factura;
	}

	public String getSaldo_id() {
		return saldo_id;
	}

	public void setSaldo_id(String saldo_id) {
		this.saldo_id = saldo_id;
	}

	public String getSaldo_disp() {
		return saldo_disp;
	}

	public void setSaldo_disp(String saldo_disp) {
		this.saldo_disp = saldo_disp;
	}

	public BigDecimal getRenglones() {
		return renglones;
	}

	public void setRenglones(BigDecimal renglones) {
		this.renglones = renglones;
	}

	public BigDecimal getCtacaucion() {
		return ctacaucion;
	}

	public void setCtacaucion(BigDecimal ctacaucion) {
		this.ctacaucion = ctacaucion;
	}

	public BigDecimal getCtatodoc() {
		return ctatodoc;
	}

	public void setCtatodoc(BigDecimal ctatodoc) {
		this.ctatodoc = ctatodoc;
	}

	public String getGerencia() {
		return gerencia;
	}

	public void setGerencia(String gerencia) {
		this.gerencia = gerencia;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public BigDecimal getCuotas() {
		return cuotas;
	}

	public void setCuotas(BigDecimal cuotas) {
		this.cuotas = cuotas;
	}

	public BigDecimal getPresentacion() {
		return presentacion;
	}

	public void setPresentacion(BigDecimal presentacion) {
		this.presentacion = presentacion;
	}

	public BigDecimal getCtacaudoc() {
		return ctacaudoc;
	}

	public void setCtacaudoc(BigDecimal ctacaudoc) {
		this.ctacaudoc = ctacaudoc;
	}

	public String getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(String porcentaje) {
		this.porcentaje = porcentaje;
	}

	public BigDecimal getCtadtotar() {
		return ctadtotar;
	}

	public void setCtadtotar(BigDecimal ctadtotar) {
		this.ctadtotar = ctadtotar;
	}

	public BigDecimal getCtatarjeta() {
		return ctatarjeta;
	}

	public void setCtatarjeta(BigDecimal ctatarjeta) {
		this.ctatarjeta = ctatarjeta;
	}

	public BigDecimal getComhyper() {
		return comhyper;
	}

	public void setComhyper(BigDecimal comhyper) {
		this.comhyper = comhyper;
	}

	public BigDecimal getContador() {
		return contador;
	}

	public void setContador(BigDecimal contador) {
		this.contador = contador;
	}

	public String getAfecomicob() {
		return afecomicob;
	}

	public void setAfecomicob(String afecomicob) {
		this.afecomicob = afecomicob;
	}

	public String getImpri_id() {
		return impri_id;
	}

	public void setImpri_id(String impri_id) {
		this.impri_id = impri_id;
	}

	public String getSubdiventa() {
		return subdiventa;
	}

	public void setSubdiventa(String subdiventa) {
		this.subdiventa = subdiventa;
	}

	public String getIdcencosto() {
		return idcencosto;
	}

	public void setIdcencosto(String idcencosto) {
		this.idcencosto = idcencosto;
	}

	public String getIdcencosto1() {
		return idcencosto1;
	}

	public void setIdcencosto1(String idcencosto1) {
		this.idcencosto1 = idcencosto1;
	}

	public String getModicent() {
		return modicent;
	}

	public void setModicent(String modicent) {
		this.modicent = modicent;
	}

	public BigDecimal getProx_cheq() {
		return prox_cheq;
	}

	public void setProx_cheq(BigDecimal prox_cheq) {
		this.prox_cheq = prox_cheq;
	}

	public BigDecimal getProx_reserv() {
		return prox_reserv;
	}

	public void setProx_reserv(BigDecimal prox_reserv) {
		this.prox_reserv = prox_reserv;
	}

	public BigDecimal getUlti_cheq() {
		return ulti_cheq;
	}

	public void setUlti_cheq(BigDecimal ulti_cheq) {
		this.ulti_cheq = ulti_cheq;
	}

	public String getModsubcent() {
		return modsubcent;
	}

	public void setModsubcent(String modsubcent) {
		this.modsubcent = modsubcent;
	}

	public BigDecimal getRes_nro() {
		return res_nro;
	}

	public void setRes_nro(BigDecimal res_nro) {
		this.res_nro = res_nro;
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

	public String getTipoidentificador() {
		return tipoidentificador;
	}

	public void setTipoidentificador(String tipoidentificador) {
		this.tipoidentificador = tipoidentificador;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public boolean isPrimeraCarga() {
		return primeraCarga;
	}

	public void setPrimeraCarga(boolean primeraCarga) {
		this.primeraCarga = primeraCarga;
	}

}
