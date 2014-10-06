<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: clientesIndicadoresTipos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Apr 29 15:16:19 GMT-03:00 2010 
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
<jsp:useBean id="BCITF"  class="ar.com.syswarp.web.ejb.BeanClientesIndicadoresTiposFrm"   scope="page"/>
<head>
 <title>Indicadores Tipos</title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCITF" property="*" />
 <% 
 String titulo = BCITF.getAccion().toUpperCase() + " DE TIPOS DE INDICADORES" ;
 BCITF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BCITF.setResponse(response);
 BCITF.setRequest(request);
 BCITF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCITF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCITF.ejecutarValidacion();
 %>
<form action="clientesIndicadoresTiposFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCITF.getAccion()%>" >
<input name="idtipoindicador" type="hidden" value="<%=BCITF.getIdtipoindicador()%>" >
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
                <td height="33" class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><jsp:getProperty name="BCITF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="20%" height="35" class="fila-det-border">&nbsp;Tipo Indicador : (*) </td>
                <td width="80%" class="fila-det-border">&nbsp;<input name="tipoindicador" type="text" value="<%=BCITF.getTipoindicador()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td height="40" class="fila-det-border">&nbsp;</td>
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

