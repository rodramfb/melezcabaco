<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: pedidosReasignacionDespositos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Feb 04 13:46:55 ART 2011 
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
<jsp:useBean id="BPRDF"  class="ar.com.syswarp.web.ejb.BeanPedidosReasignacionDespositosFrm"   scope="page"/>
<head>
 <title>FRMPedidosReasignacionDespositos</title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
 <link rel="stylesheet" type="text/css" href="scripts/calendar/calendar.css">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BPRDF" property="*" />
 <% 
 String titulo = BPRDF.getAccion().toUpperCase() + " DE PEDIDOSREASIGNACIONDESPOSITOS" ;
 BPRDF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 BPRDF.setResponse(response);
 BPRDF.setRequest(request);
 BPRDF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPRDF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPRDF.ejecutarValidacion();
 %>
<form action="pedidosReasignacionDespositosFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BPRDF.getAccion()%>" >
<input name="idreasignaciondeposito" type="hidden" value="<%=BPRDF.getIdreasignaciondeposito()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BPRDF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Dep&oacute;sito: (*) </td>
                <td width="88%" class="fila-det-border"><select name="codigo_dt" id="codigo_dt" class="campo" style="width:175px" >
                  <option value="" >Sel.</option>
                  <% 
									  Iterator iter = BPRDF.getListDepositos().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
                  <option value="<%= datos[0] %>" <%= datos[0].equals( BPRDF.getCodigo_dt().toString() ) ? "selected" : "" %>><%= datos[1] %></option>
                  <%  
									  }%>
                </select></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Tipo: (*) </td>
                <td width="88%" class="fila-det-border">
				<select name="tipo" id="tipo" class="campo" style="width:175px" >
                  <option value="" >Sel.</option>
                  <option value="N" <%= BPRDF.getTipo().equalsIgnoreCase( "N" ) ? "selected" : "" %>>Normal</option>
                  <option value="R" <%= BPRDF.getTipo().equalsIgnoreCase( "R" ) ? "selected" : "" %>>Regalos</option>				  
                 </select>
				</td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">F.Desde: (*) </td>
                <td colspan="2" class="fila-det-border" >
                  <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fechadesde" value="<%=BPRDF.getFechadesde()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_4', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_4', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_4', 'img_Date_DOWN');showCalendar('frm','fechadesde','BTN_date_4');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_4" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>                </td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">F.Hasta: (*) </td>
                <td colspan="2" class="fila-det-border" >
                  <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fechahasta" value="<%=BPRDF.getFechahasta()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_5', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_5', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_5', 'img_Date_DOWN');showCalendar('frm','fechahasta','BTN_date_5');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_5" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>                </td>
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

