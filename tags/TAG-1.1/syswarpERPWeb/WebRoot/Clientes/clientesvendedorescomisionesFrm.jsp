<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: clientesvendedorescomisiones
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Oct 26 10:25:12 ART 2012 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.math.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%@ include file="session.jspf"%>
<% 
try{
Strings str = new Strings();
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BCF"  class="ar.com.syswarp.web.ejb.BeanClientesvendedorescomisionesFrm"   scope="page"/>
<head>
 <title>FRMClientesvendedorescomisiones</title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCF" property="*" />
 <% 
 String titulo = BCF.getAccion().toUpperCase() + " DE CLIENTESVENDEDORESCOMISIONES" ;
 BCF.setResponse(response);
 BCF.setRequest(request);
 BCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCF.ejecutarValidacion();
 %>
<form action="clientesvendedorescomisionesFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCF.getAccion()%>" >
<input name="idcomision" type="hidden" value="<%=BCF.getIdcomision()%>" >
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
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;Descripción: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="descripcion" type="text" value="<%=BCF.getDescripcion()%>" class="campo" size="25" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;Socio desde: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="cantsociodesde" type="text" value="<%=BCF.getCantsociodesde()%>" class="campo" size="5" maxlength="10" ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;Socio hasta: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="cantsociohasta" type="text" value="<%=BCF.getCantsociohasta()%>" class="campo" size="5" maxlength="10"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;% Desde: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="porcdeserdesde" type="text" value="<%=BCF.getPorcdeserdesde()%>" class="campo" size="5" maxlength="10"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;% Hasta: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="porcdeserhasta" type="text" value="<%=BCF.getPorcdeserhasta()%>" class="campo" size="5" maxlength="10" ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;Valor asociacion: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="valorasociacion" type="text" value="<%=BCF.getValorasociacion()%>" class="campo" size="5" maxlength="10" ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;% Cartera: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="porccartera" type="text" value="<%=BCF.getPorccartera()%>" class="campo" size="5" maxlength="10""  ></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Enviar" class="boton">               <input name="volver" type="submit" class="boton" id="volver" value="Volver"></td>
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

