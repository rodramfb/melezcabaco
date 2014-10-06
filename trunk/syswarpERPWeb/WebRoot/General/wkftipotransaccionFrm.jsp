<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: wkftipotransaccion
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Jul 22 08:59:34 ACT 2009 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*"%>
<%@ page import="ar.com.syswarp.api.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@ page import="java.util.Iterator" %> 
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
<jsp:useBean id="BWF"  class="ar.com.syswarp.web.ejb.BeanWkftipotransaccionFrm"   scope="page"/>
<head>
 <title>FRMWkftipotransaccion.jsp</title>
  <title><MMString:LoadString id="insertbar/formsHidden" /></title>
 <meta http-equiv="description" content="mypage">
 		 <link rel = "stylesheet" href = "<%= pathskin %>">
		<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
		<link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
		<script language="JavaScript" src="vs/forms/forms.js"></script>
		<script language="JavaScript" src="vs/overlib/overlib.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BWF" property="*" />
 <% 
 String titulo = BWF.getAccion().toUpperCase() + " DE Tipo Transacciones" ;
 BWF.setResponse(response);
 BWF.setRequest(request);
 BWF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BWF.setUsuarioact( session.getAttribute("usuario").toString() );
 BWF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BWF.ejecutarValidacion();
 %>
<form action="wkftipotransaccionFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BWF.getAccion()%>" >
<input name="idtipotransaccion" type="hidden" value="<%=BWF.getIdtipotransaccion()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BWF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="16%" class="fila-det-border">&nbsp;Tipo Transaccion : (*) </td>
                <td width="84%" class="fila-det-border"><input name="tipotransaccion" type="text" value="<%=BWF.getTipotransaccion()%>" class="campo" size="50" maxlength="50"  ></td>
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

