<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: clientesclientes
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Dec 11 15:30:21 GMT-03:00 2006 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%@ include file="session.jspf"%>
<%@ page import="java.math.*"%>
<%@ page import="java.math.BigDecimal" %>
<% 
try{
Strings str = new Strings();
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BCF"  class="ar.com.syswarp.web.ejb.BeanClientesClientesFrm"   scope="page"/>
<head>
 <title>FRMClientesclientes.jsp</title>
 <title><MMString:LoadString id="insertbar/formsHidden" /></title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">
 
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script></head>

</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCF" property="*" />
 <% 
 String titulo = BCF.getAccion().toUpperCase() + "Detalle del Cliente" ;
 BCF.setResponse(response);
 BCF.setRequest(request);
 BCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BCF.ejecutarValidacion();
 %>
<form action="clientesclientesDetalleFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCF.getAccion()%>" >

   <table width="90%"  border="0" cellspacing="0" cellpadding="0" align="center">
     <tr>
       <td>
         <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
            <tr class="text-globales">
              <td>&nbsp;<%= titulo %></td>
            </tr>
         </table> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr class="fila-det-bold-rojo">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><jsp:getProperty name="BCF" property="mensaje"/>&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
              </tr>
			  <tr class="fila-det">
                <td width="17%" class="fila-det-border">Codigo:  </td>
                <td width="32%" class="fila-det-border"><input name="idcliente" type="text" class="campo" id="idcliente" value="<%=BCF.getIdcliente()%>" size="18" maxlength="18"  <%= !BCF.getAccion().equalsIgnoreCase("alta") ? "readonly" : "" %> ></td>
                <td width="24%" class="fila-det-border">&nbsp;</td>
                <td width="27%" class="fila-det-border">&nbsp;</td>
			  </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Razon Social: (*) </td>
                <td width="32%" class="fila-det-border"> <input name="razon" type="text" value="<%=BCF.getRazon()%>" class="campo" size="35" maxlength="100" readonly="SI"  > </td>
                <td width="24%" class="fila-det-border">Nro Cuit: (*) </td>
                <td width="27%" class="fila-det-border"><input name="cuit" type="text" value="<%//=BCF.getCuit()%>" class="campo" size="18" maxlength="18" readonly="SI"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Domicilio: (*) </td>
                <td width="32%" class="fila-det-border"><input name="domicilio" type="text" value="<%//=BCF.getDomicilio()%>" class="campo" size="35" maxlength="70" readonly="SI"  ></td>
                <td width="24%" class="fila-det-border">Nro de Ingresos Brutos: </td>
                <td width="27%" class="fila-det-border"><input name="brutos" type="text" value="<%=BCF.getBrutos()%>" class="campo" size="18" maxlength="18" readonly="SI"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Localidad: (*) </td>
                <td width="32%" class="fila-det-border"><table width="23%" border="0">
                  <tr class="fila-det-border">
                    <td width="61%" >
                      <input name="localidad" type="text" class="campo" id="localidad" value="<%=BCF.getLocalidad()%>" size="30" readonly></td>
                    <td width="39%">&nbsp;</td>
                    <input name="idlocalidad" type="hidden" id="idlocalidad" value="<%=BCF.getIdlocalidad()%>">
                    <input name="idprovincia" type="hidden" id="idprovincia" value= "idprovincia">
                    <input name="provincia" type="hidden" id="provincia" value= "provincia">
                    
                  </tr>
                </table></td>
                <td width="24%" class="fila-det-border">Tipo de I.V.A.: (*) </td>
                <td width="27%" class="fila-det-border"><table width="23%" border="0">
                  <tr class="fila-det-border">
                    <td width="61%" >
                      <input name="tipoiva" type="text" class="campo" id="tipoiva2" value="<%=BCF.getTipoiva()%>" size="30" readonly></td>
                    <td width="39%">&nbsp;</td>
                    <input name="idtipoiva" type="hidden" id="idtipoiva2" value="<%=BCF.getIdtipoiva()%>">
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Codigo postal:  </td>
                <td width="32%" class="fila-det-border"><input name="postal" type="text" value="<%=BCF.getPostal()%>" class="campo" size="10" maxlength="10" readonly="SI"  ></td>
                <td width="24%" class="fila-det-border">Vendedor Asignado: </td>
                <td width="27%" class="fila-det-border"><table width="23%" border="0">
                  <tr class="fila-det-border">
                    <td width="61%" >
                      <input name="vendedor" type="text" class="campo" id="vendedor" value="<%=str.esNulo(BCF.getVendedor())%>" size="30" readonly></td>
                    <td width="39%">&nbsp;</td>
                    <input name="idvendedor" type="hidden" id="idvendedor" value="<%=BCF.getIdvendedor()%>">
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Condicion de Pago: (*) </td>
                <td width="32%" class="fila-det-border"><table width="23%" border="0">
                    <tr class="fila-det-border">
                      <td width="61%" >
                      <input name="condicion" type="text" class="campo" id="condicion" value="<%=BCF.getCondicion()%>" size="30" readonly></td>
                      <td width="39%">&nbsp;</td>
                      <input name="idcondicion" type="hidden" id="idcondicion" value="<%=BCF.getIdcondicion()%>">
                    </tr>
                  </table></td>
                <td width="24%" class="fila-det-border">Nombre del Contacto: (*) </td>
                <td width="27%" class="fila-det-border"><input name="contacto" type="text" value="<%=BCF.getContacto()%>" class="campo" size="35" maxlength="50" readonly="SI"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Descuento 1:  </td>
                <td width="32%" class="fila-det-border"><input name="descuento1" type="text" value="<%=BCF.getDescuento1()%>" class="campo" size="18" maxlength="18" readonly="SI"  ></td>
                <td width="24%" class="fila-det-border">Cargo del contacto en la empresa:</td>
                <td width="27%" class="fila-det-border"><input name="cargo" type="text" value="<%//=BCF.getCargo()%>" class="campo" size="35" maxlength="50" readonly="SI"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Descuento 2:  </td>
                <td width="32%" class="fila-det-border"><input name="descuento2" type="text" value="<%=BCF.getDescuento2()%>" class="campo" size="18" maxlength="18" readonly="SI"  ></td>
                <td width="24%" class="fila-det-border">Cuenta Contable Neto: </td>
                <td width="27%" class="fila-det-border"><table width="23%" border="0">
                  <tr class="fila-det-border">
                    <td width="61%" >
                      <input name="idctaneto" type="text" class="campo" id="idctaneto" value="<%=BCF.getIdctaneto()%>" size="30" readonly></td>
                    <td width="39%">&nbsp;</td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Descuento 3:  </td>
                <td width="32%" class="fila-det-border"><input name="descuento3" type="text" value="<%=BCF.getDescuento3()%>" class="campo" size="18" maxlength="18" readonly="SI"  ></td>
                <td width="24%" class="fila-det-border">Moneda de Facturacion: (*) </td>
                <td width="27%" class="fila-det-border"><table width="23%" border="0">
                  <tr class="fila-det-border">
                    <td width="61%" >
                      <input name="moneda" type="text" class="campo" id="moneda2" value="<%=BCF.getMoneda()%>" size="30" readonly></td>
                    <td width="39%">&nbsp;</td>
                    <input name="idmoneda" type="hidden" id="idmoneda2" value="<%=BCF.getIdmoneda()%>">
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Lista de Precios:  </td>
                <td width="32%" class="fila-det-border"><table width="23%" border="0">
                    <tr class="fila-det-border">
                      <td width="61%" >
                      <input name="lista" type="text" class="campo" id="lista" value="<%=str.esNulo(BCF.getLista())%>" size="30" readonly></td>
                      <td width="39%">&nbsp;</td>
                      <input name="idlista" type="hidden" id="idlista" value="<%=BCF.getIdlista()%>">
                    </tr>
                  </table></td>
                <td width="24%" class="fila-det-border">Telefono:</td>
                <td width="27%" class="fila-det-border"><input name="telefonos" type="text" value="<%=BCF.getTelefonos()%>" class="campo" size="35" maxlength="50" readonly="SI"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Distribuidor:  </td>
                <td width="32%" class="fila-det-border"><table width="23%" border="0">
                    <tr class="fila-det-border">
                      <td width="61%" >
                        <input name="zona" type="text" class="campo" id="zona" value="<%=str.esNulo(BCF.getZona())%>" size="30" readonly></td>
                      <td width="39%">&nbsp;</td>
                      <input name="idzona" type="hidden" id="idzona" value="<%=BCF.getIdzona()%>">
                    </tr>
                  </table></td>
                <td width="24%" class="fila-det-border">Fax: </td>
                <td width="27%" class="fila-det-border"><input name="fax" type="text" value="<%=BCF.getFax()%>" class="campo" size="35" maxlength="50" readonly="SI"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Tipo de cliente:  </td>
                <td width="32%" class="fila-det-border"><table width="23%" border="0">
                    <tr class="fila-det-border">
                      <td width="61%" >
                      <input name="tipoclie" type="text" class="campo" id="tipoclie" value="<%=str.esNulo(BCF.getTipoclie())%>" size="30" readonly></td>
                      <td width="39%">&nbsp;</td>
                      <input name="idtipoclie" type="hidden" id="idtipoclie" value="<%=BCF.getIdtipoclie()%>">
                    </tr>
                  </table></td>
                <td width="24%" class="fila-det-border">E-mail: </td>
                <td width="27%" class="fila-det-border"><input name="email" type="text" value="<%=BCF.getEmail()%>" class="campo" size="35" maxlength="50" readonly="SI"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Zona:  </td>
                <td width="32%" class="fila-det-border"><table width="23%" border="0">
                    <tr class="fila-det-border">
                      <td width="61%" >
                      <input name="expreso" type="text" class="campo" id="expreso" value="<%=str.esNulo(BCF.getExpreso())%>" size="30" readonly></td>
                      <td width="39%">&nbsp;</td>
                      <input name="idexpreso" type="hidden" id="idexpreso" value="<%=BCF.getIdexpreso()%>">
                    </tr>
                  </table></td>
                <td width="24%" class="fila-det-border">Web: </td>
                <td width="27%" class="fila-det-border"><input name="web" type="text" value="<%=BCF.getWeb()%>" class="campo" size="35" maxlength="50" readonly="SI"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Observaciones:  </td>
                <td width="32%" class="fila-det-border"><textarea name="observacion" readonly="readonly" cols="30" rows="2" class="campo"><%=str.esNulo(BCF.getObservacion())%></textarea></td>
                <td width="24%" class="fila-det-border">Tipo de Comprobante : </td>
                <td width="27%" class="fila-det-border"><table width="23%" border="0">
                  <tr class="fila-det-border">
                    <td width="61%" >
                      <input name="tipocomp" type="text" class="campo" id="tipocomp" value="<%=str.esNulo(BCF.getTipocomp())%>" size="30" readonly></td>
                    <td width="39%">&nbsp;</td>
                    <input name="idtipocomp" type="hidden" id="idtipocomp" value="<%=BCF.getIdtipocomp()%>">
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Cobrador:  </td>
                <td width="32%" class="fila-det-border"><table width="23%" border="0">
                  <tr class="fila-det-border">
                    <td width="61%" >
                    <input name="cobrador" type="text" class="campo" id="cobrador" value="<%=str.esNulo(BCF.getCobrador())%>" size="30" readonly></td>
                    <td width="39%">&nbsp;</td>
                    <input name="idcobrador" type="hidden" id="idcobrador" value="<%=BCF.getIdcobrador()%>">
                  </tr>
                </table></td>
                <td width="24%" class="fila-det-border">Autorizado: (*) </td>
                <td width="27%" class="fila-det-border"><select name="autorizado" disabled="disabled" id="select"  >
                  <option value="S" <%= BCF.getAutorizado().equalsIgnoreCase("S") ? "selected" : "" %> >SI</option>
                  <option value="N" <%= BCF.getAutorizado().equalsIgnoreCase("N") ? "selected" : "" %> >NO</option>
                </select></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Limite de credito:  </td>
                <td width="32%" class="fila-det-border"><input name="lcredito" type="text" value="<%=BCF.getLcredito()%>" class="campo" size="18" maxlength="18" readonly="SI"  ></td>
                <td width="24%" class="fila-det-border">Categoria de Credito: (*) </td>
                <td width="27%" class="fila-det-border"><table width="23%" border="0">
                  <tr class="fila-det-border">
                    <td width="61%" >
                      <input name="credcate" type="text" class="campo" id="credcate2" value="<%=BCF.getCredcate()%>" size="30" readonly></td>
                    <td width="39%">&nbsp;</td>
                    <input name="idcredcate" type="hidden" id="idcredcate2" value="<%=BCF.getIdcredcate()%>">
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
              </tr>
          </table>
       </td>
      </tr>
    </table>
   </form>
  </body>
</html> 
<% 
 }
catch (Exception ex) {
   java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
   java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
   ex.printStackTrace(pw);
  System.out.println("ERROR (" + pagina + ") : "+ex);   
}%>

