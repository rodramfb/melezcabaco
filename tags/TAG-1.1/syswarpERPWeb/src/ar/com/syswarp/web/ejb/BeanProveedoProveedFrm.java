package ar.com.syswarp.web.ejb;

/* 
 javabean para la entidad (Formulario): proveedoProveed
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Wed Jul 05 15:38:22 GMT-03:00 2006 

 Para manejar la pagina: proveedoProveedFrm.jsp

 */
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

public class BeanProveedoProveedFrm implements SessionBean, Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanProveedoProveedFrm.class);

	private String validar = "";

	private String idproveedor = "";

	private String razon_social = "";

	private String domicilio = "";

	private BigDecimal idlocalidad = new BigDecimal(0);

	private String localidad = "";

	private BigDecimal idprovincia = BigDecimal.valueOf(0);

	private String provincia = "";

	private String codigo_postal = "";

	private String contacto = "";

	private String telefono = "";

	private String cuit = "";

	private String brutos = "";

	private BigDecimal ctapasivo = BigDecimal.valueOf(0);

	private String ctaactivo1 = "";

	private String ctaactivo2 = "";

	private String ctaactivo3 = "";

	private String ctaactivo4 = "";

	private String ctaiva = "";

	private String ctaretiva = "";

	private String letra_iva = "";

	private BigDecimal ctadocumen = BigDecimal.valueOf(0);

	private String ret_gan = "";

	private String idretencion1 = "";

	private String d_idretencion1 = "";

	private String idretencion2 = "";

	private String d_idretencion2 = "";

	private String idretencion3 = "";

	private String d_idretencion3 = "";

	private String idretencion4 = "";

	private String d_idretencion4 = "";

	private String idretencion5 = "";

	private String d_idretencion5 = "";

	private BigDecimal ctades = BigDecimal.valueOf(0);

	private String stock_fact = "";

	private BigDecimal idcondicionpago = BigDecimal.valueOf(0);

	private String d_idcondicionpago = "";

	private BigDecimal cent1 = BigDecimal.valueOf(0);

	private BigDecimal cent2 = BigDecimal.valueOf(0);

	private BigDecimal cent3 = BigDecimal.valueOf(0);

	private BigDecimal cent4 = BigDecimal.valueOf(0);

	private BigDecimal cents1 = BigDecimal.valueOf(0);

	private BigDecimal cents2 = BigDecimal.valueOf(0);

	private BigDecimal cents3 = BigDecimal.valueOf(0);

	private BigDecimal cents4 = BigDecimal.valueOf(0);

	private String email = "";
	
	private BigDecimal Ejercicio = BigDecimal.valueOf(0);





	private String usuarioalt = "";

	private String usuarioact = "";

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanProveedoProveedFrm() {
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

			BigDecimal ctaactivo_1 = Common.setNotNull(this.ctaactivo1).equals(
					"") ? null : new BigDecimal(this.ctaactivo1);
			BigDecimal ctaactivo_2 = Common.setNotNull(this.ctaactivo2).equals(
					"") ? null : new BigDecimal(this.ctaactivo2);
			BigDecimal ctaactivo_3 = Common.setNotNull(this.ctaactivo3).equals(
					"") ? null : new BigDecimal(this.ctaactivo3);
			BigDecimal ctaactivo_4 = Common.setNotNull(this.ctaactivo4).equals(
					"") ? null : new BigDecimal(this.ctaactivo4);
			BigDecimal cta_iva = Common.setNotNull(this.ctaiva).equals("") ? null
					: new BigDecimal(this.ctaiva);
			BigDecimal cta_retiva = Common.setNotNull(this.ctaretiva)
					.equals("") ? null : new BigDecimal(this.ctaretiva);
			BigDecimal idretencion_1 = Common.setNotNull(this.idretencion1)
					.equals("") ? null : new BigDecimal(this.idretencion1);
			BigDecimal idretencion_2 = Common.setNotNull(this.idretencion2)
					.equals("") ? null : new BigDecimal(this.idretencion2);
			BigDecimal idretencion_3 = Common.setNotNull(this.idretencion3)
					.equals("") ? null : new BigDecimal(this.idretencion3);
			BigDecimal idretencion_4 = Common.setNotNull(this.idretencion4)
					.equals("") ? null : new BigDecimal(this.idretencion4);
			BigDecimal idretencion_5 = Common.setNotNull(this.idretencion5)
					.equals("") ? null : new BigDecimal(this.idretencion5);

			Proveedores proveedoProveed = Common.getProveedores();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = proveedoProveed.proveedoProveedCreate(
						new BigDecimal(this.idproveedor), this.razon_social,
						this.domicilio, this.idlocalidad, this.idprovincia,
						this.codigo_postal, this.contacto, this.telefono,
						this.cuit, this.brutos, this.ctapasivo, ctaactivo_1,
						ctaactivo_2, ctaactivo_3, ctaactivo_4, cta_iva,
						cta_retiva, this.letra_iva, this.ctadocumen,
						this.ret_gan, idretencion_1, idretencion_2,
						idretencion_3, idretencion_4, idretencion_5,
						this.ctades, this.stock_fact, this.idcondicionpago,
						this.cent1, this.cent2, this.cent3, this.cent4,
						this.cents1, this.cents2, this.cents3, this.cents4,
						this.email, this.usuarioalt, this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = proveedoProveed.proveedoProveedUpdate(
						
						
						
						
						
						new BigDecimal(this.idproveedor), this.razon_social,
						this.domicilio, this.idlocalidad, this.idprovincia,
						this.codigo_postal, this.contacto, this.telefono,
						this.cuit, this.brutos, this.ctapasivo, ctaactivo_1,
						ctaactivo_2, ctaactivo_3, ctaactivo_4, cta_iva,
						cta_retiva, this.letra_iva, this.ctadocumen,
						this.ret_gan, idretencion_1, idretencion_2,
						idretencion_3, idretencion_4, idretencion_5,
						this.ctades, this.stock_fact, this.idcondicionpago,
						this.cent1, this.cent2, this.cent3, this.cent4,
						this.cents1, this.cents2, this.cents3, this.cents4,
						this.email, this.usuarioact, this.idempresa);

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosProveedoProveed() {
		try {
			Proveedores proveedoProveed = Common.getProveedores();
			List listProveedoProveed = proveedoProveed.getProveedoProveedPK(
					new BigDecimal(this.idproveedor), this.idempresa);
			Iterator iterProveedoProveed = listProveedoProveed.iterator();
			if (iterProveedoProveed.hasNext()) {
				String[] uCampos = (String[]) iterProveedoProveed.next();
				// TODO: Constructores para cada tipo de datos
				this.idproveedor = uCampos[0];
				this.razon_social = uCampos[1];
				this.domicilio = uCampos[2];
				this.idlocalidad = new BigDecimal(uCampos[3]);
				this.localidad = uCampos[4];
				this.idprovincia = new BigDecimal(uCampos[5]);
				this.provincia = uCampos[6];
				this.codigo_postal = uCampos[7];
				this.contacto = uCampos[8];
				this.telefono = uCampos[9];
				this.cuit = uCampos[10];
				this.brutos = uCampos[11];
				this.ctapasivo = new BigDecimal(uCampos[12]);
				this.ctaactivo1 = Common.setNotNull(uCampos[13]);
				this.ctaactivo2 = Common.setNotNull(uCampos[14]);
				this.ctaactivo3 = Common.setNotNull(uCampos[15]);
				this.ctaactivo4 = Common.setNotNull(uCampos[16]);
				this.ctaiva = Common.setNotNull(uCampos[17]);
				this.ctaretiva = Common.setNotNull(uCampos[18]);
				this.letra_iva = uCampos[19];
				this.ctadocumen = new BigDecimal(uCampos[20]);
				this.ret_gan = uCampos[21];
				this.idretencion1 = Common.setNotNull(uCampos[22]);
				this.d_idretencion1 = Common.setNotNull(uCampos[23]);
				this.idretencion2 = Common.setNotNull(uCampos[24]);
				this.d_idretencion2 = Common.setNotNull(uCampos[25]);
				this.idretencion3 = Common.setNotNull(uCampos[26]);
				this.d_idretencion3 = Common.setNotNull(uCampos[27]);
				this.idretencion4 = Common.setNotNull(uCampos[28]);
				this.d_idretencion4 = Common.setNotNull(uCampos[29]);
				this.idretencion5 = Common.setNotNull(uCampos[30]);
				this.d_idretencion5 = Common.setNotNull(uCampos[31]);
				this.ctades = new BigDecimal(uCampos[32]);
				this.stock_fact = uCampos[33];
				this.idcondicionpago = new BigDecimal(uCampos[34]);
				this.d_idcondicionpago = uCampos[35];
				this.cent1 = new BigDecimal(uCampos[36]);
				this.cent2 = new BigDecimal(uCampos[37]);
				this.cent3 = new BigDecimal(uCampos[38]);
				this.cent4 = new BigDecimal(uCampos[39]);
				this.cents1 = new BigDecimal(uCampos[40]);
				this.cents2 = new BigDecimal(uCampos[41]);
				this.cents3 = new BigDecimal(uCampos[42]);
				this.cents4 = new BigDecimal(uCampos[43]);
				this.email = Common.setNotNull(uCampos[44]);
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosProveedoProveed()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			Proveedores proveedoProveed = Common.getProveedores();
			if (this.accion.equalsIgnoreCase("alta")) {
				if (this.idproveedor.equals(""))
					if (this.validar.equalsIgnoreCase(""))
						this.idproveedor = proveedoProveed
								.getMaximoProveedor(this.idempresa)
								+ "";
				if (Common.setNotNull(this.idproveedor).equals(""))
					this.idproveedor = "1";

			}
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("proveedoProveedAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos

					if (!Common.esEntero(this.idproveedor)) {
						this.mensaje = "Ingreso codigo de proveedor valido.";
						return false;
					}

					if (razon_social == null) {
						this.mensaje = "No se puede dejar vacio el campo razon_social ";
						return false;
					}
					if (domicilio == null) {
						this.mensaje = "No se puede dejar vacio el campo domicilio ";
						return false;
					}

					if (!this.email.equals("")) {
						if (!Common.isValidEmailAddress(this.email)) {
							this.mensaje = "Ingrese una direccion de email valida.";
							return false;
						}
					}

					if (idlocalidad == null) {
						this.mensaje = "No se puede dejar vacio el campo localidad ";
						return false;
					}
					if (idprovincia == null) {
						this.mensaje = "No se puede dejar vacio el campo idprovincia ";
						return false;
					}

					if (ctapasivo == null || ctapasivo.longValue() == 0) {
						this.mensaje = "Es necesario ingresar Cuenta Pasivo";
						return false;
					}

					if (Common.setNotNull(ctaactivo1).equals("")
							|| !Common.esEntero(ctaactivo1)
							|| ctaactivo1.equals("0")) {
						this.mensaje = "Es necesario ingresar Cuenta Contrapartida Uno";
						return false;
					}

					if (!isRetencionOk()) {
						this.mensaje = "No se puede asignar mas de una vez la misma Retencion.";
						return false;
					}
					
					/*
				this.ctapasivo = new BigDecimal(uCampos[12]);
				this.ctaactivo1 = Common.setNotNull(uCampos[13]);
				this.ctaactivo2 = Common.setNotNull(uCampos[14]);
				this.ctaactivo3 = Common.setNotNull(uCampos[15]);
				this.ctaactivo4 = Common.setNotNull(uCampos[16]);
				this.ctaiva = Common.setNotNull(uCampos[17]);
				this.ctaretiva = Common.setNotNull(uCampos[18]);
					 
					 */
					
					
                    //VALIDACION CUENTA CONTABLE
				
			if (this.accion.equalsIgnoreCase("Modificacion")){		
					long salida1 = 0;
					salida1 = proveedoProveed.GetDevuelvoCuentaContable(this.idempresa,this.Ejercicio,this.ctapasivo);
					if (salida1 == 0){
						this.mensaje = "Atencion el campo Cta Pasivo tiene una cuenta asignada Inexistente!";
						return false;
					}
					
					if (!ctaactivo1.equals("")) {
					   long salida2 = 0;
					   salida2 = proveedoProveed.GetDevuelvoCuentaContable2(this.idempresa,this.Ejercicio,this.ctaactivo1);
					   if (salida2 == 0){
						  this.mensaje = "Atencion el campo Cta contrapartida 1 tiene una cuenta asignada Inexistente!";
						  return false;
					   }
					}
					
					if (!ctaactivo2.equals("")) {
					   long salida3 = 0;
					   salida3 = proveedoProveed.GetDevuelvoCuentaContable2(this.idempresa,this.Ejercicio,this.ctaactivo2);
					   if (salida3 == 0){
						  this.mensaje = "Atencion el campo Cta contrapartida 2 tiene una cuenta asignada Inexistente!";
						  return false;
					   }
					}   
					
					if (!ctaactivo3.equals("")) {
					    long salida4 = 0;
					    salida4 = proveedoProveed.GetDevuelvoCuentaContable2(this.idempresa,this.Ejercicio,this.ctaactivo3);
					    if (salida4 == 0){
						   this.mensaje = "Atencion el campo Cta contrapartida 3 tiene una cuenta asignada Inexistente!";
						   return false;
					    }
					}
					if (!ctaactivo4.equals("")) {
					    long salida5 = 0;
					    salida5 = proveedoProveed.GetDevuelvoCuentaContable2(this.idempresa,this.Ejercicio,this.ctaactivo4);
					    if (salida5 == 0){
						   this.mensaje = "Atencion el campo Cta contrapartida 4 tiene una cuenta asignada Inexistente!";
						   return false;
					    }
					}    
					
					if (!ctaiva.equals("")) {
					    long salida6 = 0;
					    salida6 = proveedoProveed.GetDevuelvoCuentaContable2(this.idempresa,this.Ejercicio,this.ctaiva);
					    if (salida6 == 0){
				    		this.mensaje = "Atencion el campo Cta Percepción. IVA tiene una cuenta asignada Inexistente!";
					    	return false;
					    }
					}
					
					if (!ctaretiva.equals("")) {
					    long salida7 = 0;
					    salida7 = proveedoProveed.GetDevuelvoCuentaContable2(this.idempresa,this.Ejercicio,this.ctaretiva);
					    if (salida7 == 0){
						   this.mensaje = "Atencion el campo Cta Retención. de IVA tiene una cuenta asignada Inexistente!";
						   return false;
					    }
					}
					
			}		
					
					
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosProveedoProveed();
				}
			}
		} catch (Exception e) {
			log.error(" ejecutarValidacion(): " + e);
		}
		return true;
	}

	private boolean isRetencionOk() {
		boolean isok = true;
		Hashtable ht = new Hashtable();
		int r = 0;

		if (!Common.setNotNull(this.idretencion1).equals("")) {
			ht.put(this.idretencion1, this.idretencion1);
			r++;
		}
		if (!Common.setNotNull(this.idretencion2).equals("")) {
			ht.put(this.idretencion2, this.idretencion2);
			r++;
		}
		if (!Common.setNotNull(this.idretencion3).equals("")) {
			ht.put(this.idretencion3, this.idretencion3);
			r++;
		}
		if (!Common.setNotNull(this.idretencion4).equals("")) {
			ht.put(this.idretencion4, this.idretencion4);
			r++;
		}
		if (!Common.setNotNull(this.idretencion5).equals("")) {
			ht.put(this.idretencion5, this.idretencion5);
			r++;
		}

		log.info("isRetencionOk: HT=" + ht.size() + " / R=" + r);

		if (r != ht.size())
			isok = false;

		return isok;
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
	public String getIdproveedor() {
		return idproveedor;
	}

	public void setIdproveedor(String idproveedor) {
		this.idproveedor = idproveedor;
	}

	public String getRazon_social() {
		return razon_social;
	}

	public void setRazon_social(String razon_social) {
		this.razon_social = razon_social;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public BigDecimal getIdprovincia() {
		return idprovincia;
	}

	public void setIdprovincia(BigDecimal idprovincia) {
		this.idprovincia = idprovincia;
	}

	public String getCodigo_postal() {
		return codigo_postal;
	}

	public void setCodigo_postal(String codigo_postal) {
		this.codigo_postal = codigo_postal;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public String getBrutos() {
		return brutos;
	}

	public void setBrutos(String brutos) {
		this.brutos = brutos;
	}

	public BigDecimal getCtapasivo() {
		return ctapasivo;
	}

	public void setCtapasivo(BigDecimal ctapasivo) {
		this.ctapasivo = ctapasivo;
	}

	public String getCtaactivo1() {
		return ctaactivo1;
	}

	public void setCtaactivo1(String ctaactivo1) {
		this.ctaactivo1 = ctaactivo1;
	}

	public String getCtaactivo2() {
		return ctaactivo2;
	}

	public void setCtaactivo2(String ctaactivo2) {
		this.ctaactivo2 = ctaactivo2;
	}

	public String getCtaactivo3() {
		return ctaactivo3;
	}

	public void setCtaactivo3(String ctaactivo3) {
		this.ctaactivo3 = ctaactivo3;
	}

	public String getCtaactivo4() {
		return ctaactivo4;
	}

	public void setCtaactivo4(String ctaactivo4) {
		this.ctaactivo4 = ctaactivo4;
	}

	public String getCtaiva() {
		return ctaiva;
	}

	public void setCtaiva(String ctaiva) {
		this.ctaiva = ctaiva;
	}

	public String getCtaretiva() {
		return ctaretiva;
	}

	public void setCtaretiva(String ctaretiva) {
		this.ctaretiva = ctaretiva;
	}

	public String getLetra_iva() {
		return letra_iva;
	}

	public void setLetra_iva(String letra_iva) {
		this.letra_iva = letra_iva;
	}

	public BigDecimal getCtadocumen() {
		return ctadocumen;
	}

	public void setCtadocumen(BigDecimal ctadocumen) {
		this.ctadocumen = ctadocumen;
	}

	public String getRet_gan() {
		return ret_gan;
	}

	public void setRet_gan(String ret_gan) {
		this.ret_gan = ret_gan;
	}

	public String getIdretencion1() {
		return idretencion1;
	}

	public void setIdretencion1(String idretencion1) {
		this.idretencion1 = idretencion1;
	}

	public String getIdretencion2() {
		return idretencion2;
	}

	public void setIdretencion2(String idretencion2) {
		this.idretencion2 = idretencion2;
	}

	public String getIdretencion3() {
		return idretencion3;
	}

	public void setIdretencion3(String idretencion3) {
		this.idretencion3 = idretencion3;
	}

	public String getIdretencion4() {
		return idretencion4;
	}

	public void setIdretencion4(String idretencion4) {
		this.idretencion4 = idretencion4;
	}

	public String getIdretencion5() {
		return idretencion5;
	}

	public void setIdretencion5(String idretencion5) {
		this.idretencion5 = idretencion5;
	}

	public BigDecimal getCtades() {
		return ctades;
	}

	public void setCtades(BigDecimal ctades) {
		this.ctades = ctades;
	}

	public String getStock_fact() {
		return stock_fact;
	}

	public void setStock_fact(String stock_fact) {
		this.stock_fact = stock_fact;
	}

	public BigDecimal getIdcondicionpago() {
		return idcondicionpago;
	}

	public void setIdcondicionpago(BigDecimal idcondicionpago) {
		this.idcondicionpago = idcondicionpago;
	}

	public BigDecimal getCent1() {
		return cent1;
	}

	public void setCent1(BigDecimal cent1) {
		this.cent1 = cent1;
	}

	public BigDecimal getCent2() {
		return cent2;
	}

	public void setCent2(BigDecimal cent2) {
		this.cent2 = cent2;
	}

	public BigDecimal getCent3() {
		return cent3;
	}

	public void setCent3(BigDecimal cent3) {
		this.cent3 = cent3;
	}

	public BigDecimal getCent4() {
		return cent4;
	}

	public void setCent4(BigDecimal cent4) {
		this.cent4 = cent4;
	}

	public BigDecimal getCents1() {
		return cents1;
	}

	public void setCents1(BigDecimal cents1) {
		this.cents1 = cents1;
	}

	public BigDecimal getCents2() {
		return cents2;
	}

	public void setCents2(BigDecimal cents2) {
		this.cents2 = cents2;
	}

	public BigDecimal getCents3() {
		return cents3;
	}

	public void setCents3(BigDecimal cents3) {
		this.cents3 = cents3;
	}

	public BigDecimal getCents4() {
		return cents4;
	}

	public void setCents4(BigDecimal cents4) {
		this.cents4 = cents4;
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

	// para que me levante las descripciones de los lov
	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getD_idretencion1() {
		return d_idretencion1;
	}

	public void setD_idretencion1(String d_idretencion1) {
		this.d_idretencion1 = d_idretencion1;
	}

	public String getD_idretencion2() {
		return d_idretencion2;
	}

	public void setD_idretencion2(String d_idretencion2) {
		this.d_idretencion2 = d_idretencion2;
	}

	public String getD_idretencion3() {
		return d_idretencion3;
	}

	public void setD_idretencion3(String d_idretencion3) {
		this.d_idretencion3 = d_idretencion3;
	}

	public String getD_idretencion4() {
		return d_idretencion4;
	}

	public void setD_idretencion4(String d_idretencion4) {
		this.d_idretencion4 = d_idretencion4;
	}

	public String getD_idretencion5() {
		return d_idretencion5;
	}

	public void setD_idretencion5(String d_idretencion5) {
		this.d_idretencion5 = d_idretencion5;
	}

	public String getD_idcondicionpago() {
		return d_idcondicionpago;
	}

	public void setD_idcondicionpago(String d_idcondicionpago) {
		this.d_idcondicionpago = d_idcondicionpago;
	}

	public BigDecimal getIdlocalidad() {
		return idlocalidad;
	}

	public void setIdlocalidad(BigDecimal idlocalidad) {
		this.idlocalidad = idlocalidad;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public BigDecimal getEjercicio() {
		return Ejercicio;
	}

	public void setEjercicio(BigDecimal ejercicio) {
		Ejercicio = ejercicio;
	}
	

}
