<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: globaltiposdocumentos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Mar 28 09:45:19 ART 2008 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%//@ include file="session.jspf"%>
<%@ page import="java.math.BigDecimal" %>
<% 
try{
Strings str = new Strings();
//String pathskin = session.getAttribute("pathskin").toString();
//String pathscript = session.getAttribute("pathscript").toString();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> 
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BRC"  class="ar.com.syswarp.web.ejb.BeanRecuperarClave"   scope="page"/>
<head>
 <title>Recuperar clave</title>
  <title><MMString:LoadString id="insertbar/formsHidden" /></title>
 <meta http-equiv="description" content="mypage">
 <link href="../imagenes/default/erp-style.css" rel="stylesheet" type="text/css">
 
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script></head></head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BRC" property="*" />
 <% 
 String titulo =  "RECUPERAR CLAVE" ;
 BRC.setResponse(response);
 BRC.setRequest(request);
 //BRC.setUsuarioalt( session.getAttribute("usuario").toString() );
 //BRC.setUsuarioact( session.getAttribute("usuario").toString() );
 //BRC.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BRC.ejecutarValidacion();
 %>
<form action="recuperarClave.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BRC.getAccion()%>" >

   <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
     <tr>
       <td>
         <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
            <tr class="text-globales">
              <td>&nbsp;<%= titulo %></td>
            </tr>
       </table> 
            <table width="100%" border="0" cellspacing="2" cellpadding="0" align="center">
              <tr class="fila-det-bold-rojo">
                <td height="33" colspan="2" class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/mail-forward.png" width="22" height="22">
                  <jsp:getProperty name="BRC" property="mensaje"/>                  &nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="30%" height="64" class="fila-det-border">Ingrese E-mail : (*) </td>
                <td width="51%" class="fila-det-border"><input name="email" type="text" class="campo" id="email" value="<%=BRC.getEmail()%>" size="30" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td height="52" class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><input name="validar" type="submit" value="Recuperar" class="boton"></td>
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
  System.out.println("ERROR (recuperarClave.jsp) : "+ex);   
}%>

