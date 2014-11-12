/* 
   javabean para la entidad (Formulario): rrhhpersonal
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Apr 22 09:44:28 ACT 2009 
   
   Para manejar la pagina: rrhhpersonalFrm.jsp
      
 */
package ar.com.syswarp.web.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Timestamp;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import java.math.*;
import java.net.IDN;
import java.util.*;
import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanRrhhpersonalFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanRrhhpersonalFrm.class);

	private String validar = "";

	private String legajo = "";

	private String estado = "";

	private String apellido = "";

	private String domicilio = "";

	private BigDecimal puerta = new BigDecimal(0);

	private String piso = "";

	private String departamento = "";

	private String idlocalidad = "";

	private String localidad = "";

	private String idprovincia = "";

	private String postal = "";

	private String telefono = "";

	private String idestadocivil = "";

	private String estadocivil = "";

	private Timestamp fechanac = new Timestamp(Common.initObjectTime());

	private String fechanacStr = Common.initObjectTimeStr();

	private String sexo = "";

	private String idtipodocumento = "";

	private String tipodocumento = "";

	private String nrodocumento = "";

	private String cuil = "";

	private String idpais = "";

	private String pais = "";

	private Timestamp fbaja = new Timestamp(Common.initObjectTime());

	private String fbajaStr = Common.initObjectTimeStr();

	private String idcategoria = "";

	private String categoria = "";

	private String tarea = "";

	private Timestamp fingreso;

	private String fingresoStr = Common.initObjectTimeStr();

	private String idtitulo = "";

	private String titulo = "";

	private String idafjp = "";

	private String afjp = "";

	private String nroafjp = "";

	private String idart = "";

	private String art = "";

	private String nroart = "";

	private Double valor01 = Double.valueOf("0");

	private Double valor02 = Double.valueOf("0");

	private Double valor03 = Double.valueOf("0");

	private Double valor04 = Double.valueOf("0");

	private Double valor05 = Double.valueOf("0");

	private String mensualoquin = "";

	private String idctacont = "";

	private String idctacont2 = "";

	private BigDecimal aniosrecon = BigDecimal.valueOf(0);

	private BigDecimal mesrecon = BigDecimal.valueOf(0);

	private String idobrasocial = "";

	private String obrasocial = "";

	private String jubilado = "";

	private String email = "";

	private String idlocalidadpago = "";

	private String localidadpago = "";

	private String idmodalidadcontrato = "";

	private String modalidadcontrato = "";

	private String idbancodeposito = "";

	private String bancodeposito = "";

	private BigDecimal idempresa = BigDecimal.valueOf(0);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";
	
	private String lista = "";
	
	private BigDecimal idlista = new BigDecimal(-1);

	public BeanRrhhpersonalFrm() {
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
			RRHH rrhhpersonal = Common.getRrhh();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = rrhhpersonal.rrhhpersonalCreate(new BigDecimal(
						this.legajo), this.apellido, this.domicilio,
						this.puerta, this.piso, this.departamento,
						this.idlocalidad, this.idprovincia, this.postal,
						this.telefono, this.idestadocivil, this.fechanac,
						this.sexo, this.idtipodocumento, new BigDecimal(this.nrodocumento),
						this.cuil, this.idpais, this.fbaja, this.idcategoria,
						this.tarea, this.fingreso, this.idtitulo, this.idafjp,
						this.nroafjp, this.idart, this.nroart, this.valor01,
						this.valor02, this.valor03, this.valor04, this.valor05,
						this.mensualoquin, this.idctacont, this.idctacont2,
						this.aniosrecon, this.mesrecon, this.idobrasocial,
						this.jubilado, this.email, this.idlocalidadpago,
						this.idmodalidadcontrato, this.idbancodeposito,
						this.usuarioalt, this.idempresa,this.idlista);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = rrhhpersonal.rrhhpersonalUpdate(new BigDecimal(
						this.legajo), this.apellido, this.domicilio,
						this.puerta, this.piso, this.departamento,
						this.idlocalidad, this.idprovincia, this.postal,
						this.telefono, this.idestadocivil, this.fechanac,
						this.sexo, this.idtipodocumento, new BigDecimal(this.nrodocumento),
						this.cuil, this.idpais, this.fbaja, this.idcategoria,
						this.tarea, this.fingreso, this.idtitulo, this.idafjp,
						this.nroafjp, this.idart, this.nroart, this.valor01,
						this.valor02, this.valor03, this.valor04, this.valor05,
						this.mensualoquin, this.idctacont, this.idctacont2,
						this.aniosrecon, this.mesrecon, this.idobrasocial,
						this.jubilado, this.email, this.idlocalidadpago,
						this.idmodalidadcontrato, this.idbancodeposito,
						this.usuarioact, this.idempresa,this.idlista);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = rrhhpersonal.rrhhpersonalDelete(new BigDecimal(
						this.legajo), this.idempresa);

			if (this.accion.equalsIgnoreCase("alta")
					&& this.mensaje.equalsIgnoreCase("alta correcta")) {
				RequestDispatcher dispatcher = null;
				dispatcher = request
						.getRequestDispatcher("rrhhestadosempleadoFrm.jsp?accion=alta&accionpersonal=alta&&legajo="
								+ legajo + "&validar=");
				dispatcher.forward(request, response);
			}
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosRrhhpersonal() {
		try {
			RRHH rrhhpersonal = Common.getRrhh();
			log.info("legajo: " + legajo);
			List listRrhhpersonal = rrhhpersonal.getRrhhpersonalPK(
					new BigDecimal(this.legajo), this.idempresa);
			Iterator iterRrhhpersonal = listRrhhpersonal.iterator();
			if (iterRrhhpersonal.hasNext()) {
				String[] uCampos = (String[]) iterRrhhpersonal.next();
				// TODO: Constructores para cada tipo de datos
				this.legajo = uCampos[0];
				this.apellido = uCampos[1];
				this.domicilio = uCampos[2];
				this.puerta = new BigDecimal(uCampos[3]);
				this.piso = uCampos[4];
				this.departamento = uCampos[5];
				this.idlocalidad = uCampos[6];
				this.localidad = Common.setNotNull(uCampos[7])
						.equalsIgnoreCase("") ? "" : uCampos[7];
				this.idprovincia = uCampos[8];
				this.postal = uCampos[9];
				this.telefono = uCampos[10];
				this.idestadocivil = uCampos[11];
				this.estadocivil = Common.setNotNull(uCampos[12])
						.equalsIgnoreCase("") ? "" : uCampos[12];
				this.fechanac = Timestamp.valueOf(uCampos[13]);
				this.fechanacStr = Common.setObjectToStrOrTime(fechanac,
						"JSTsToStr").toString();
				this.sexo = uCampos[14];
				this.idtipodocumento = uCampos[15];
				this.tipodocumento = Common.setNotNull(uCampos[16])
						.equalsIgnoreCase("") ? "" : uCampos[16];
				this.nrodocumento = uCampos[17];
				this.cuil = uCampos[18];
				this.idpais = uCampos[19];
				this.pais = Common.setNotNull(uCampos[20]).equalsIgnoreCase("") ? ""
						: uCampos[20];
				/*
				 * this.fbaja = Timestamp.valueOf(uCampos[21]); this.fbajaStr =
				 * Common.setObjectToStrOrTime(fbaja, "JSTsToStr") .toString();
				 */
				this.idcategoria = uCampos[22];
				this.categoria = Common.setNotNull(uCampos[23])
						.equalsIgnoreCase("") ? "" : uCampos[23];
				this.tarea = uCampos[24];
				this.fingreso = Timestamp.valueOf(uCampos[25]);
				this.fingresoStr = Common.setObjectToStrOrTime(fingreso,
						"JSTsToStr").toString();
				this.idtitulo = uCampos[26];
				this.titulo = Common.setNotNull(uCampos[27]).equalsIgnoreCase(
						"") ? "" : uCampos[27];
				this.idafjp = uCampos[28];
				this.afjp = uCampos[29];
				this.nroafjp = uCampos[30];
				this.idart = uCampos[31];
				this.art = Common.setNotNull(uCampos[32]).equalsIgnoreCase("") ? ""
						: uCampos[32];
				this.nroart = uCampos[33];
				this.valor01 = Double.valueOf(uCampos[34]);
				this.valor02 = Double.valueOf(uCampos[35]);
				this.valor03 = Double.valueOf(uCampos[36]);
				this.valor04 = Double.valueOf(uCampos[37]);
				this.valor05 = Double.valueOf(uCampos[38]);
				this.mensualoquin = uCampos[39];
				this.idctacont = Common.setNotNull(uCampos[40])
						.equalsIgnoreCase("") ? "" : uCampos[40];
				this.idctacont2 = Common.setNotNull(uCampos[41])
						.equalsIgnoreCase("") ? "" : uCampos[41];
				this.aniosrecon = BigDecimal.valueOf(Long
						.parseLong(uCampos[42]));
				this.mesrecon = BigDecimal.valueOf(Long.parseLong(uCampos[43]));
				this.idobrasocial = uCampos[44];
				this.obrasocial = Common.setNotNull(uCampos[45])
						.equalsIgnoreCase("") ? "" : uCampos[45];
				this.jubilado = uCampos[46];
				this.email = uCampos[47];
				this.idlocalidadpago = uCampos[48];
				this.localidadpago = Common.setNotNull(uCampos[49])
						.equalsIgnoreCase("") ? "" : uCampos[49];
				this.idmodalidadcontrato = uCampos[50];
				this.modalidadcontrato = Common.setNotNull(uCampos[51])
						.equalsIgnoreCase("") ? "" : uCampos[51];
				this.idbancodeposito = uCampos[52];
				this.bancodeposito = Common.setNotNull(uCampos[53])
						.equalsIgnoreCase("") ? "" : uCampos[53];
				this.idempresa = BigDecimal
						.valueOf(Long.parseLong(uCampos[54]));
				this.idlista = BigDecimal
				.valueOf(Long.parseLong(uCampos[59]));
				this.lista = uCampos[60];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosRrhhpersonal()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("rrhhpersonalAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {

					/*
					 * if (legajo == null) { this.mensaje =
					 * "No puede dejar nulo el campo legajo"; return false; } if
					 * (apellido == null) { this.mensaje =
					 * "No puede dejar nulo el campo apellido"; return false; }
					 * if (domicilio == null) { this.mensaje =
					 * "No puede dejar nulo el campo domicilio"; return false; }
					 * if (idlocalidad == null) { this.mensaje =
					 * "No puede dejar nulo el campo localidad"; return false; }
					 * if (localidad == null) { this.mensaje =
					 * "No puede dejar nulo el campo localidad"; return false; }
					 * if (idestadocivil == null) { this.mensaje =
					 * "No puede dejar nulo el campo estado civil"; return
					 * false; } if (estadocivil == null) { this.mensaje =
					 * "No puede dejar nulo el campo estado civil"; return
					 * false; } if (idtipodocumento == null) { this.mensaje =
					 * "No puede dejar vacio el  tipo de documento"; return
					 * false; } if (nrodocumento == null) { this.mensaje =
					 * "No puede dejar nulo el campo nro. documento"; return
					 * false; } if (categoria == null) { this.mensaje =
					 * "No puede dejar nulo el campo categoria"; return false; }
					 * if (obrasocial == null) { this.mensaje =
					 * "No puede dejar nulo el campo obra social"; return false;
					 * } if (localidadpago == null) { this.mensaje =
					 * "No puede dejar nulo el campo localidadpago"; return
					 * false; } if (modalidadcontrato == null) { this.mensaje =
					 * "No puede dejar nulo el campo localidadpago"; return
					 * false; }
					 */
					// 1. nulidad de campos
					if (legajo.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Legajo  ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (apellido.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Apellido y Nombre  ";
						return false;
					}
					if (domicilio.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Domicilio  ";
						return false;
					}
					if (localidad.equalsIgnoreCase("")) {
						this.mensaje = "No puede dejar vacia la localidad de residencia";
						return false;
					}
					/**
					 * if (Common.esNumerico(idtitulo)) { if (new
					 * BigDecimal(idtitulo).longValue() < 0) { this.mensaje =
					 * "No puede dejar vacio el campo titulo"; return false; } }
					 * if (titulo.equalsIgnoreCase("")) { this.mensaje =
					 * "No puede dejar vacio el campo titulo"; return false; }
					 */
					if (idtipodocumento.equalsIgnoreCase("")) {
						this.mensaje = "Debe ingresar el tipo de documento";
						return false;
					}
					log.info(nrodocumento);
					if(!Common.esNumerico(nrodocumento))
					{
						this.mensaje ="El numero de documento debe ser numerico.";
						return false;
					}
					
					if (new BigDecimal(nrodocumento).longValue() < 1) {
						this.mensaje = "Debe ingresar el numero de documento";
						return false;
					}
					if (nrodocumento.toString().equalsIgnoreCase("")) {
						this.mensaje = "Debe ingresar el nro. de documento";
						return false;
					}
					/*
					 * if (!Common.esNumerico(legajo)) { this.mensaje =
					 * "El campo legajo es numerico."; return false; }
					 */

					// if (new BigDecimal(idlocalidad).longValue() < 0) {
					// this.mensaje = "Debe ingresar la localidad";
					// return false;
					// }
					// if (new BigDecimal(idestadocivil).longValue() < 0) {
					// this.mensaje = "Debe ingresar el estado civil";
					// return false;
					//
					// }

					// if (new BigDecimal(idcategoria).longValue() < 0) {
					// this.mensaje = "Debe ingresar la categoria";
					// return false;
					// }
					/**
					 * if (obrasocial.equalsIgnoreCase("")) { this.mensaje =
					 * "Debe ingresar la obra social"; return false; }
					 */
					// if (new BigDecimal(idlocalidadpago).longValue() < 0) {
					// this.mensaje = "Debe ingresar la localidad de pago";
					// return false;
					// }
					// if (new BigDecimal(idmodalidadcontrato).longValue() < 0)
					// {
					// this.mensaje = "Debe ingresar la modalidad del contrato";
					// return false;
					// }
					//					
					/*
					 * if (!Common.esEntero(this.nrodocumento)) { this.mensaje =
					 * "Ingrese nro. de documento valido."; return false; } if
					 * (!Common.esEntero(this.puerta)) { this.mensaje =
					 * "El campo puerta es numerico."; return false; }
					 */

				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosRrhhpersonal();
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
	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getPostal() {
		return postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Timestamp getFechanac() {
		return fechanac;
	}

	public void setFechanac(Timestamp fechanac) {
		this.fechanac = fechanac;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getCuil() {
		return cuil;
	}

	public void setCuil(String cuil) {
		this.cuil = cuil;
	}

	public Timestamp getFbaja() {
		return fbaja;
	}

	public void setFbaja(Timestamp fbaja) {
		this.fbaja = fbaja;
	}

	public String getTarea() {
		return tarea;
	}

	public void setTarea(String tarea) {
		this.tarea = tarea;
	}

	public Timestamp getFingreso() {
		return fingreso;
	}

	public void setFingreso(Timestamp fingreso) {
		this.fingreso = fingreso;
	}

	public String getNroafjp() {
		return nroafjp;
	}

	public void setNroafjp(String nroafjp) {
		this.nroafjp = nroafjp;
	}

	public String getNroart() {
		return nroart;
	}

	public void setNroart(String nroart) {
		this.nroart = nroart;
	}

	public Double getValor01() {
		return valor01;
	}

	public void setValor01(Double valor01) {
		this.valor01 = valor01;
	}

	public Double getValor02() {
		return valor02;
	}

	public void setValor02(Double valor02) {
		this.valor02 = valor02;
	}

	public Double getValor03() {
		return valor03;
	}

	public void setValor03(Double valor03) {
		this.valor03 = valor03;
	}

	public Double getValor04() {
		return valor04;
	}

	public void setValor04(Double valor04) {
		this.valor04 = valor04;
	}

	public Double getValor05() {
		return valor05;
	}

	public void setValor05(Double valor05) {
		this.valor05 = valor05;
	}

	public String getMensualoquin() {
		return mensualoquin;
	}

	public void setMensualoquin(String mensualoquin) {
		this.mensualoquin = mensualoquin;
	}

	public BigDecimal getAniosrecon() {
		return aniosrecon;
	}

	public void setAniosrecon(BigDecimal aniosrecon) {
		this.aniosrecon = aniosrecon;
	}

	public BigDecimal getMesrecon() {
		return mesrecon;
	}

	public void setMesrecon(BigDecimal mesrecon) {
		this.mesrecon = mesrecon;
	}

	public String getIdlocalidad() {
		return idlocalidad;
	}

	public void setIdlocalidad(String idlocalidad) {
		this.idlocalidad = idlocalidad;
	}

	public String getIdprovincia() {
		return idprovincia;
	}

	public void setIdprovincia(String idprovincia) {
		this.idprovincia = idprovincia;
	}

	public String getIdestadocivil() {
		return idestadocivil;
	}

	public void setIdestadocivil(String idestadocivil) {
		this.idestadocivil = idestadocivil;
	}

	public String getIdtipodocumento() {
		return idtipodocumento;
	}

	public void setIdtipodocumento(String idtipodocumento) {
		this.idtipodocumento = idtipodocumento;
	}

	public String getIdpais() {
		return idpais;
	}

	public void setIdpais(String idpais) {
		this.idpais = idpais;
	}

	public String getIdcategoria() {
		return idcategoria;
	}

	public void setIdcategoria(String idcategoria) {
		this.idcategoria = idcategoria;
	}

	public String getIdtitulo() {
		return idtitulo;
	}

	public void setIdtitulo(String idtitulo) {
		this.idtitulo = idtitulo;
	}

	public String getIdafjp() {
		return idafjp;
	}

	public void setIdafjp(String idafjp) {
		this.idafjp = idafjp;
	}

	public String getIdart() {
		return idart;
	}

	public void setIdart(String idart) {
		this.idart = idart;
	}

	public String getIdctacont() {
		return idctacont;
	}

	public void setIdctacont(String idctacont) {
		this.idctacont = idctacont;
	}

	public String getIdctacont2() {
		return idctacont2;
	}

	public void setIdctacont2(String idctacont2) {
		this.idctacont2 = idctacont2;
	}

	public String getIdobrasocial() {
		return idobrasocial;
	}

	public void setIdobrasocial(String idobrasocial) {
		this.idobrasocial = idobrasocial;
	}

	public String getJubilado() {
		return jubilado;
	}

	public void setJubilado(String jubilado) {
		this.jubilado = jubilado;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public void setFechanacStr(String fechanacStr) {
		this.fechanacStr = fechanacStr;
		this.fechanac = (java.sql.Timestamp) Common.setObjectToStrOrTime(
				fechanacStr, "StrToJSTs");
	}

	public void setFingresoStr(String fingresoStr) {
		this.fingresoStr = fingresoStr;
		this.fingreso = (java.sql.Timestamp) Common.setObjectToStrOrTime(
				fingresoStr, "StrToJSTs");
	}

	public void setFbajaStr(String fbajaStr) {
		this.fbajaStr = fbajaStr;
		this.fbaja = (java.sql.Timestamp) Common.setObjectToStrOrTime(fbajaStr,
				"StrToJSTs");

	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getFechanacStr() {
		return fechanacStr;
	}

	public String getFbajaStr() {
		return fbajaStr;
	}

	public String getFingresoStr() {
		return fingresoStr;
	}

	public String getEstadocivil() {
		return estadocivil;
	}

	public void setEstadocivil(String estadocivil) {
		this.estadocivil = estadocivil;
	}

	public String getTipodocumento() {
		return tipodocumento;
	}

	public void setTipodocumento(String tipodocumento) {
		this.tipodocumento = tipodocumento;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAfjp() {
		return afjp;
	}

	public void setAfjp(String afjp) {
		this.afjp = afjp;
	}

	public String getArt() {
		return art;
	}

	public void setArt(String art) {
		this.art = art;
	}

	public String getObrasocial() {
		return obrasocial;
	}

	public void setObrasocial(String obrasocial) {
		this.obrasocial = obrasocial;
	}

	public String getLegajo() {
		return legajo;
	}

	public void setLegajo(String legajo) {
		this.legajo = legajo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNrodocumento() {
		return nrodocumento;
	}

	public void setNrodocumento(String nrodocumento) {
		this.nrodocumento = nrodocumento;
	}

	public void setPuerta(BigDecimal puerta) {
		this.puerta = puerta;
	}

	public BigDecimal getPuerta() {
		return puerta;
	}

	public String getIdlocalidadpago() {
		return idlocalidadpago;
	}

	public void setIdlocalidadpago(String idlocalidadpago) {
		this.idlocalidadpago = idlocalidadpago;
	}

	public String getLocalidadpago() {
		return localidadpago;
	}

	public void setLocalidadpago(String localidadpago) {
		this.localidadpago = localidadpago;
	}

	public String getIdmodalidadcontrato() {
		return idmodalidadcontrato;
	}

	public void setIdmodalidadcontrato(String idmodalidadcontrato) {
		this.idmodalidadcontrato = idmodalidadcontrato;
	}

	public String getModalidadcontrato() {
		return modalidadcontrato;
	}

	public void setModalidadcontrato(String modalidadcontrato) {
		this.modalidadcontrato = modalidadcontrato;
	}

	public String getIdbancodeposito() {
		return idbancodeposito;
	}

	public void setIdbancodeposito(String idbancodeposito) {
		this.idbancodeposito = idbancodeposito;
	}

	public String getBancodeposito() {
		return bancodeposito;
	}

	public void setBancodeposito(String bancodeposito) {
		this.bancodeposito = bancodeposito;
	}

	public String getLista() {
		return lista;
	}

	public void setLista(String lista) {
		this.lista = lista;
	}

	public BigDecimal getIdlista() {
		return idlista;
	}

	public void setIdlista(BigDecimal idlista) {
		this.idlista = idlista;
	}

	
}
