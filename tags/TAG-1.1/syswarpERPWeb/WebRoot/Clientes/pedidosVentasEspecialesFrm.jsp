<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: pedidosVentasEspeciales
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jan 27 11:07:27 ART 2011 
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
<jsp:useBean id="BPVEF"  class="ar.com.syswarp.web.ejb.BeanPedidosVentasEspecialesFrm"   scope="page"/>
<head>
 <title>FRMPedidosVentasEspeciales</title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
 <link rel="stylesheet" type="text/css" href="scripts/calendar/calendar.css">
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BPVEF" property="*" />
 <% 
 String titulo = BPVEF.getAccion().toUpperCase() + " DE VENTAS ESPECIALES DE PEDIDOS" ;
 BPVEF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BPVEF.setResponse(response);
 BPVEF.setRequest(request);
 BPVEF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPVEF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPVEF.ejecutarValidacion();
 %>
<form action="pedidosVentasEspecialesFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BPVEF.getAccion()%>" >
<input name="idventaespecial" type="hidden" value="<%=BPVEF.getIdventaespecial()%>" >
   <table width="90%"  border="0" cellspacing="0" cellpadding="0" align="center">
     <tr>
       <td>
         <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
            <tr class="text-globales">
              <td height="24">&nbsp;<%= titulo %></td>
            </tr>
         </table> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr class="fila-det-bold-rojo">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><jsp:getProperty name="BPVEF" property="mensaje"/>&nbsp;</td> 
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Descripci&oacute;n: (*) </td>
                <td width="88%" class="fila-det-border"><input name="ventaespecial" type="text" value="<%=BPVEF.getVentaespecial()%>" class="campo" size="40" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Fecha desde: (*) </td>
                <td colspan="2" class="fila-det-border" >
                  <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fechadesde" value="<%=BPVEF.getFechadesde()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_3', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_3', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_3', 'img_Date_DOWN');showCalendar('frm','fechadesde','BTN_date_3');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_3" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>                </td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Fecha hasta: (*) </td>
                <td colspan="2" class="fila-det-border" >
                  <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fechahasta" value="<%=BPVEF.getFechahasta()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_4', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_4', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_4', 'img_Date_DOWN');showCalendar('frm','fechahasta','BTN_date_4');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_4" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>                </td>
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

