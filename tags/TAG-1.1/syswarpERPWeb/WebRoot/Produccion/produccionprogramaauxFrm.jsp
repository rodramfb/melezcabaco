<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: produccionprogramaaux
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Dec 13 16:04:32 GMT-03:00 2006 
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
<jsp:useBean id="BPF"  class="ar.com.syswarp.web.ejb.BeanProduccionprogramaauxFrm"   scope="page"/>
<head>
 <title>FRMProduccionprogramaaux.jsp</title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" type="text/css" href="imagenes/default/tnx.css">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BPF" property="*" />
 <% 
 String titulo = BPF.getAccion().toUpperCase() + " DE PRODUCCIONPROGRAMAAUX" ;
 BPF.setResponse(response);
 BPF.setRequest(request);
 BPF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BPF.ejecutarValidacion();
 %>
<form action="produccionprogramaauxFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BPF.getAccion()%>" >
<input name="idopaux" type="hidden" value="<%=BPF.getIdopaux()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BPF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;codigo_st: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="codigo_st" type="text" value="<%=BPF.getCodigo_st()%>" class="campo" size="15" maxlength="15"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;cantidad: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="cantidad" type="text" value="<%=BPF.getCantidad()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;fecha_prom: (*) </td>
                <td colspan="2" class="fila-det-border" >
                  <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fecha_prom" value="<%=BPF.getFecha_prom()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_4', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_4', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_4', 'img_Date_DOWN');showCalendar('frm','fecha_prom','BTN_date_4');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_4" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>
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

