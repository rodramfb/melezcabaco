<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: rrhhestadosot
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Apr 04 10:22:27 ART 2008 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%@ include file="session.jspf"%>
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
<jsp:useBean id="BRF"  class="ar.com.syswarp.web.ejb.BeanRrhhestadosotFrm"   scope="page"/>
<head>
 <title>FRMRrhhestadosot.jsp</title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" type="text/css" href="imagenes/default/tnx.css">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script> <link rel="stylesheet" href="<%=pathskin%>">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script></head>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BRF" property="*" />
 <% 
 String titulo = BRF.getAccion().toUpperCase() + " DE Estados ot" ;
 BRF.setResponse(response);
 BRF.setRequest(request);
 BRF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BRF.setUsuarioact( session.getAttribute("usuario").toString() );
 BRF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BRF.ejecutarValidacion();
 %>
<form action="rrhhestadosotFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BRF.getAccion()%>" >
<input name="idestadoot" type="hidden" value="<%=BRF.getIdestadoot()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BRF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Estado ot: (*) </td>
                <td width="88%" class="fila-det-border"><input name="estadoot" type="text" value="<%=BRF.getEstadoot()%>" class="campo" size="50" maxlength="50"  ></td>
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

