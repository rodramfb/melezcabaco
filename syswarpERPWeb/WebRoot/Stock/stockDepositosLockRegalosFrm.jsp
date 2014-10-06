<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: stockDepositosLockRegalos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Nov 24 14:30:23 ART 2010 
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
<jsp:useBean id="BSDLRF"  class="ar.com.syswarp.web.ejb.BeanStockDepositosLockRegalosFrm"   scope="page"/>
<head>
 <title>FRMStockDepositosLockRegalos</title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
<link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BSDLRF" property="*" />
 <% 
 String titulo = BSDLRF.getAccion().toUpperCase() + " DE BLOQUEOS PARA DEPOSITO : " + BSDLRF.getDescrip_dt();
 BSDLRF.setResponse(response);
 BSDLRF.setRequest(request);
 BSDLRF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BSDLRF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BSDLRF.setUsuarioact( session.getAttribute("usuario").toString() );
 BSDLRF.ejecutarValidacion();
 %>
<form action="stockDepositosLockRegalosFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BSDLRF.getAccion()%>" >
<input name="idlock" type="hidden" value="<%=BSDLRF.getIdlock()%>" >
   <span class="fila-det-border">
   <input name="codigo_dt" type="hidden" value="<%=BSDLRF.getCodigo_dt()%>" class="campo" size="100" maxlength="100"  >
   <input name="descrip_dt" type="hidden" class="campo" id="descrip_dt" value="<%=BSDLRF.getDescrip_dt()%>" size="100" maxlength="100"  >
   </span>
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
                <td class="fila-det-border"><jsp:getProperty name="BSDLRF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="16%" class="fila-det-border">&nbsp;</td>
                <td width="84%" class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="16%" class="fila-det-border">Fecha Desde: (*) </td>
                <td colspan="2" class="fila-det-border" >
                  <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fechadesde" value="<%=BSDLRF.getFechadesde()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_3', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_3', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_3', 'img_Date_DOWN');showCalendar('frm','fechadesde','BTN_date_3');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_3" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>                </td>
              </tr>
              <tr class="fila-det">
                <td width="16%" class="fila-det-border">Fecha Hasta : (*) </td>
                <td colspan="2" class="fila-det-border" >
                  <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fechahasta" value="<%=BSDLRF.getFechahasta()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_4', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_4', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_4', 'img_Date_DOWN');showCalendar('frm','fechahasta','BTN_date_4');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_4" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>                </td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Comentarios:</td>
                <td class="fila-det-border"><textarea name="comentarios" cols="40" rows="2" class="campo" id="comentarios"><%=BSDLRF.getComentarios()%></textarea></td>
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

