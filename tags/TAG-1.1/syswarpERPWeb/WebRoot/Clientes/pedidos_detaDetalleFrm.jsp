<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: pedidos_deta
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Mar 29 16:53:13 GMT-03:00 2007 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
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
<jsp:useBean id="BPF"  class="ar.com.syswarp.web.ejb.BeanPedidos_detaDetalleFrm"   scope="page"/>
<head>
 <title>FRMPedidos_deta.jsp</title>
  <meta http-equiv="description" content="mypage">
  
 <link rel="stylesheet" href="<%=pathskin%>">
  <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
<script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BPF" property="*" />
 <% 
 String titulo = BPF.getAccion().toUpperCase() + " DE Pedidos Detalle" ;
 BPF.setResponse(response);
 BPF.setRequest(request);
 BPF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPF.ejecutarValidacion();
 %>
<form action="pedidos_detaDetalleFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BPF.getAccion()%>" >
<input name="idpedido_deta" type="hidden" value="<%=BPF.getIdpedido_deta()%>" >
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
			  <input name="idpedido_cabe" type="hidden" id="idpedido_cabe" value="<%=BPF.getIdpedido_cabe()%>">
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">codigo_st: (*) </td>
                <td width="88%" class="fila-det-border"><input name="codigo_st" type="text" value="<%=BPF.getCodigo_st()%>" class="campo" size="15" maxlength="15"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">fecha: (*) </td>
                <td colspan="2" class="fila-det-border" >
                  <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fechaStr" value="<%=BPF.getFechaStr()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_4', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_4', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_4', 'img_Date_DOWN');showCalendar('frm','fechaStr','BTN_date_4');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_4" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>                </td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">renglon: (*) </td>
                <td width="88%" class="fila-det-border"><input name="renglon" type="text" value="<%=BPF.getRenglon()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">precio: (*) </td>
                <td width="88%" class="fila-det-border"><input name="precio" type="text" value="<%=BPF.getPrecio()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">saldo: (*) </td>
                <td width="88%" class="fila-det-border"><input name="saldo" type="text" value="<%=BPF.getSaldo()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">cantidad: (*) </td>
                <td width="88%" class="fila-det-border"><input name="cantidad" type="text" value="<%=BPF.getCantidad()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">bonific: (*) </td>
                <td width="88%" class="fila-det-border"><input name="bonific" type="text" value="<%=BPF.getBonific()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">codigo_md: (*) </td>
                <td width="88%" class="fila-det-border"><input name="codigo_md" type="text" value="<%=BPF.getCodigo_md()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;cantuni: (*) </td>
                <td width="88%" class="fila-det-border"><input name="cantuni" type="text" value="<%=BPF.getCantuni()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;codigo_dt: (*) </td>
                <td width="88%" class="fila-det-border"><input name="codigo_dt" type="text" value="<%=BPF.getCodigo_dt()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;entrega: (*) </td>
                <td width="88%" class="fila-det-border"><input name="entrega" type="text" value="<%=BPF.getEntrega()%>" class="campo" size="1" maxlength="1"  ></td>
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

