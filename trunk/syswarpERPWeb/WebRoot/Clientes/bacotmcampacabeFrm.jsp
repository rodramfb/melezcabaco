<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: bacotmcampacabe
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Apr 03 14:45:20 GMT-03:00 2007 
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
<jsp:useBean id="BBF"  class="ar.com.syswarp.web.ejb.BeanBacotmcampacabeFrm"   scope="page"/>
<head>
 <title>FRMBacotmcampacabe.jsp</title>
 <meta http-equiv="description" content="mypage">
  <link rel="stylesheet" href="<%=pathskin%>">
 
 <link rel="stylesheet" type="text/css" href="<%=pathscript%>/calendar/calendar.css">
<script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>
</head>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BBF" property="*" />
 <% 
 String titulo = BBF.getAccion().toUpperCase() + " DE Telemarketing Campaña Cabecera" ;
 BBF.setResponse(response);
 BBF.setRequest(request);
 BBF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BBF.setUsuarioact( session.getAttribute("usuario").toString() );
 BBF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BBF.ejecutarValidacion();
 %>
<form action="bacotmcampacabeFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BBF.getAccion()%>" >
<input name="idcampacabe" type="hidden" value="<%=BBF.getIdcampacabe()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BBF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Campa&ntilde;a cabecera: (*) </td>
              <td width="83%" class="fila-det-border"><input name="campacabe" type="text" value="<%=BBF.getCampacabe()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Fecha desde: (*) </td>
                <td colspan="2" class="fila-det-border" >
                  <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fdesdeStr" value="<%=BBF.getFdesdeStr()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_3', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_3', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_3', 'img_Date_DOWN');showCalendar('frm','fdesdeStr','BTN_date_3');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_3" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>
                </td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Fecha hasta: (*) </td>
                <td colspan="2" class="fila-det-border" >
                  <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fhastaStr" value="<%=BBF.getFhastaStr()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_4', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_4', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_4', 'img_Date_DOWN');showCalendar('frm','fhastaStr','BTN_date_4');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_4" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>
                </td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Descuento: (*) </td>
                <td width="83%" class="fila-det-border"><input name="descuento" type="text" value="<%=BBF.getDescuento()%>" class="campo" size="18" maxlength="18"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Total: (*) </td>
                <td width="83%" class="fila-det-border"><input name="total" type="text" value="<%=BBF.getTotal()%>" class="campo" size="18" maxlength="18"  ></td>
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

