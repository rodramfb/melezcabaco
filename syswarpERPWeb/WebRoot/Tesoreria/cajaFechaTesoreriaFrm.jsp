<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: Cajaclearing
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Aug 01 11:36:49 GMT-03:00 2006 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.math.*"%> 
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
<jsp:useBean id="BCFTF"  class="ar.com.syswarp.web.ejb.BeanCajaFechaTesoreriaFrm" scope="page"/>
<head>
 <title>FECHA DE TESORERIA</title>
 <link rel="stylesheet" type="text/css" href="<%=pathscript%>/calendar/calendar.css">
 <link rel="stylesheet" href="<%=pathskin%>">
 
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCFTF" property="*" />
 <% 
 String titulo = BCFTF.getAccion().toUpperCase() + " DE FECHA DE CAJA" ;
 BCFTF.setResponse(response);
 BCFTF.setRequest(request);
 BCFTF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCFTF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCFTF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BCFTF.ejecutarValidacion();
 %>
<form action="cajaFechaTesoreriaFrm.jsp" method="post" name="frm">
	<input name="accion" type="hidden" value="<%=BCFTF.getAccion()%>" >
	<input name="fechaTesoValorActual" type="hidden" value="<%= BCFTF.getFechaTesoValorActual() %>" >
   <table width="90%"  border="0" cellspacing="0" cellpadding="0" align="center">
     <tr>
       <td height="154">
         <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
            <tr class="text-globales">
              <td>&nbsp;<%= titulo %></td>
            </tr>
         </table> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr class="fila-det-bold-rojo">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><jsp:getProperty name="BCFTF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det-bold">
                <td height="21" class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">ATENCION: Una vez modificada la fecha de tesoreria no podr&aacute; ser vuelta atr&aacute;s. </td>
              </tr>
              <tr class="fila-det">
                <td width="24%" height="21" class="fila-det-border">Fecha de Caja Actual :  </td>
                <td width="76%" class="fila-det-border">&nbsp;<%= BCFTF.getFechaTesoValorActual() %></td> 
              </tr>
              <tr class="fila-det">
                <td width="24%" class="fila-det-border">Fecha Nueva (*):  </td>
                <td width="76%" class="fila-det-border"><input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="valor" value="<%=BCFTF.getValor()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date', 'img_Date_DOWN');showCalendar('frm','valor','BTN_date');return false;"> <img align="absmiddle" border="0" name="BTN_date" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a></td>
              </tr>
              <tr class="fila-det">
                <td height="36" class="fila-det-border">&nbsp;</td>
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


