<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: Cajaferiados
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Aug 01 11:33:07 GMT-03:00 2006 
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
<jsp:useBean id="BCF"  class="ar.com.syswarp.web.ejb.BeanCajaferiadosFrm"   scope="page"/>
<head>
 <title>FRMCajaferiados.jsp</title>
 <meta http-equiv="description" content="mypage">
  <link rel="stylesheet" href="<%=pathskin%>">
 
 <link rel="stylesheet" type="text/css" href="<%=pathscript%>/calendar/calendar.css">
<script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCF" property="*" />
 <% 
 String titulo = BCF.getAccion().toUpperCase() + " DE Feriados" ;
 BCF.setResponse(response);
 BCF.setRequest(request);
 BCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BCF.ejecutarValidacion();
 %>
<form action="CajaferiadosFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCF.getAccion()%>" >
<input name="idferiado" type="hidden" value="<%=BCF.getIdferiado()%>" >
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
                <td width="15%" class="fila-det-border">Feriado: (*) </td>
                <td width="85%" class="fila-det-border"><input name="feriado" type="text" value="<%=BCF.getFeriado()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="15%" class="fila-det-border">Fecha feriado: (*) </td>
                <td colspan="2" class="fila-det-border" >
                  <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fecha_ferStr" value="<%=BCF.getFecha_ferStr()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_3', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_3', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_3', 'img_Date_DOWN');showCalendar('frm','fecha_ferStr','BTN_date_3');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_3" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>
                </td>
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

