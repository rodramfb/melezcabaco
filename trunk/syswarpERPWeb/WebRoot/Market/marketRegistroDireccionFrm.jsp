<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: marketRegistroDireccion
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Mar 14 14:48:01 ART 2008 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.math.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%@ include file="session.jspf"%>
<% 
try{ 
	Strings str = new Strings();
	String pathskin = "vs/market.css";
	String pathscript = "scripts";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BMRDF"  class="ar.com.syswarp.web.ejb.BeanMarketRegistroDireccionFrm"   scope="page"/>
<head>
 <title></title>
 <link rel="stylesheet"  href="<%=pathskin%>" /> 
 <link rel="stylesheet"  href="vs/market.css" /> 
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BMRDF" property="*" />
 <% 
 String titulo = " REGISTRO " ;
 BMRDF.setResponse(response);
 BMRDF.setRequest(request);
 BMRDF.setUsuarioalt( session.getAttribute("marketUsuario").toString() );
 BMRDF.setUsuarioact( session.getAttribute("marketUsuario").toString() );
 BMRDF.setIdempresa( new BigDecimal( session.getAttribute("marketEmpresa").toString() ));
 BMRDF.ejecutarValidacion();
 %>
<form action="marketRegistroDireccionFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BMRDF.getAccion()%>" >
<input name="idcliente" type="hidden" value="<%=BMRDF.getIdcliente()%>" >
   <table width="90%"  border="0" cellspacing="0" cellpadding="0" align="center">
     <tr>
       <td>
         <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
            <tr class="fila-head">
              <td>&nbsp;<%= titulo %></td>
            </tr>
         </table> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr >
                <td >&nbsp;</td>
                <td class="mensaje"><jsp:getProperty name="BMRDF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det">Nombre: (*) </td>
                <td width="74%" class="fila-det"><input name="nombreFact" type="text" value="<%=BMRDF.getNombreFact()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det">Apellido: (*) </td>
                <td width="74%" class="fila-det"><input name="apellidoFact" type="text" class="campo" id="apellidoFact" value="<%=BMRDF.getApellidoFact()%>" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det">Empresa: (*) </td>
                <td width="74%" class="fila-det"><input name="empresaFact" type="text" class="campo" id="empresaFact" value="<%=BMRDF.getEmpresaFact()%>" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det">Direccion: (*) </td>
                <td width="74%" class="fila-det"><input name="direccionFact" type="text" class="campo" id="direccionFact" value="<%=BMRDF.getDireccionFact()%>" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det">Ciudad: (*) </td>
                <td width="74%" class="fila-det"><input name="ciudadFact" type="text" class="campo" id="ciudadFact" value="<%=BMRDF.getCiudadFact()%>" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det">Provincia / Estado: (*) </td>
                <td width="74%" class="fila-det"><input name="provinciaestadoFact" type="text" class="campo" id="provinciaestadoFact" value="<%=BMRDF.getProvinciaestadoFact()%>" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det">Codigo Postal: (*) </td>
                <td width="74%" class="fila-det"><input name="codigopostalFact" type="text" class="campo" id="codigopostalFact" value="<%=BMRDF.getCodigopostalFact()%>" size="10" maxlength="10"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det">Pais: (*) </td>
                <td width="74%" class="fila-det"><input name="paisFact" type="text" class="campo" id="paisFact" value="<%=BMRDF.getPaisFact()%>" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det">Telefono: (*) </td>
                <td width="74%" class="fila-det"><input name="telefonoFact" type="text" class="campo" id="telefonoFact" value="<%=BMRDF.getTelefonoFact()%>" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det">Fax:  </td>
                <td width="74%" class="fila-det"><input name="faxFact" type="text" class="campo" id="faxFact" value="<%=BMRDF.getFaxFact()%>" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det">Comentarios</td>
                <td class="fila-det"><textarea name="comentarios" cols="60" rows="3" id="comentarios"><%=BMRDF.getComentarios()%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det">Mensaje a incluir en el envio. </td>
                <td class="fila-det"><textarea name="obsentrega" cols="60" rows="3" id="obsentrega"><%=BMRDF.getObsentrega()%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det">&nbsp;</td>
                <td class="fila-det">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det">&nbsp;</td>
                <td class="fila-head">Direcci&oacute;n de Env&iacute;o </td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det">&nbsp;</td>
                <td class="fila-det">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det">Igual a la direccion de Facturacion </td>
                <td class="fila-det"><input name="mismadireccion" type="hidden" id="mismadireccion" value="<%=BMRDF.isMismadireccion()%>" >
								 <input name="checkbox" type="checkbox"  onClick="document.frm.mismadireccion.value = this.checked" <%=BMRDF.isMismadireccion() ? "checked": ""%>>
								</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det">&nbsp;</td>
                <td class="fila-det">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det">Nombre: (*) </td>
                <td class="fila-det"><input name="nombreEnv" type="text" value="<%=BMRDF.getNombreEnv()%>" class="campo" size="50" maxlength="50"></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det">Apellido: (*) </td>
                <td class="fila-det"><input name="apellidoEnv" type="text" class="campo" id="apellidoEnv" value="<%=BMRDF.getApellidoEnv()%>" size="50" maxlength="50"></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det">Empresa: (*) </td>
                <td class="fila-det"><input name="empresaEnv" type="text" class="campo" id="empresaEnv" value="<%=BMRDF.getEmpresaEnv()%>" size="50" maxlength="50"></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det">Direccion: (*) </td>
                <td class="fila-det"><input name="direccionEnv" type="text" class="campo" id="direccionEnv" value="<%=BMRDF.getDireccionEnv()%>" size="50" maxlength="50"></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det">Ciudad: (*) </td>
                <td class="fila-det"><input name="ciudadEnv" type="text" class="campo" id="ciudadEnv" value="<%=BMRDF.getCiudadEnv()%>" size="50" maxlength="50"></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det">Provincia / Estado: (*) </td>
                <td class="fila-det"><input name="provinciaestadoEnv" type="text" class="campo" id="provinciaestadoEnv" value="<%=BMRDF.getProvinciaestadoEnv()%>" size="50" maxlength="50"></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det">Codigo Postal: (*) </td>
                <td class="fila-det"><input name="codigopostalEnv" type="text" class="campo" id="codigopostalEnv" value="<%=BMRDF.getCodigopostalEnv()%>" size="10" maxlength="10"></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det">Pais: (*) </td>
                <td class="fila-det"><input name="paisEnv" type="text" class="campo" id="paisEnv" value="<%=BMRDF.getPaisEnv()%>" size="50" maxlength="50"></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det">Telefono: (*) </td>
                <td class="fila-det"><input name="telefonoEnv" type="text" class="campo" id="telefonoEnv" value="<%=BMRDF.getTelefonoEnv()%>" size="50" maxlength="50"></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det">Fax: </td>
                <td class="fila-det"><input name="faxEnv" type="text" class="campo" id="faxEnv" value="<%=BMRDF.getFaxEnv()%>" size="50" maxlength="50"></td>
              </tr>
              
              <tr class="fila-det">
                <td class="fila-det">&nbsp;</td>
                <td class="fila-det">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det">&nbsp;</td>
                <td class="fila-det">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det">&nbsp;</td>
                <td class="fila-det">&nbsp;<input name="validar" type="submit" value="Coninuar" class="boton"></td>
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

