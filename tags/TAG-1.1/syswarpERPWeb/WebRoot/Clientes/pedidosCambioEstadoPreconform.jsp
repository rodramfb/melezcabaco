<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: pedidosestados
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Mar 27 11:09:46 GMT-03:00 2007 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="java.util.*" %> 
<%@ page import="java.math.*" %> 
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
<jsp:useBean id="BPF"  class="ar.com.syswarp.web.ejb.BeanPedidosCambioEstadosPreconform"   scope="page"/>
<head>
 <title>FRMPedidosestados.jsp</title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">
 
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BPF" property="*" />
 <%  
 BPF.setResponse(response);
 BPF.setRequest(request);
 BPF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BPF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPF.ejecutarValidacion();

 String titulo =  "PRECONFORMACION - CAMBIO DE ESTADO DEL PEDIDO:" + BPF.getIdpedido() + " / CLIENTE: " + BPF.getIdcliente() + " - " +   BPF.getCliente();

 %>
<form action="pedidosCambioEstadoPreconform.jsp" method="post" name="frm">
  <input name="accion" type="hidden" value="<%=BPF.getAccion()%>" >
  <input name="idpedido" type="hidden" value="<%=BPF.getIdpedido()%>" >
   <input name="tipopedido" type="hidden" id="tipopedido" value="<%=BPF.getTipopedido()%>" >
   <table width="90%"  border="0" cellspacing="0" cellpadding="0" align="center">
     <tr>
       <td>
         <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
            <tr class="text-globales">
              <td><%= titulo %></td>
            </tr>
            <tr class="text-globales">
              <td>&nbsp;</td>
            </tr>
         </table> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr class="fila-det-bold-rojo">
                <td height="30" class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><jsp:getProperty name="BPF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td height="34" class="fila-det-border">Estado Actual: </td>
                <td class="fila-det-border">&nbsp;<%= BPF.getIdestadoanterior()  + " - " + BPF.getEstadoanterior() %></td>
              </tr>
              <tr class="fila-det">
                <td width="16%" height="45" class="fila-det-border">Nuevo Estado: (*) </td>
                <td width="84%" class="fila-det-border">
                <select name="idestadonuevo" id="idestadonuevo" class="campo" style="width:200px" >
                  <option value="-1">Seleccionar</option>
                  <% 
									  Iterator iter = BPF.getListPedidosEstados().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();   
										if(datos[1].equalsIgnoreCase("preimplementacion"))continue;%>
                  <option value="<%= datos[0] %>" <%= datos[0].equals( BPF.getIdestadonuevo().toString()) ? "selected" : "" %>><%= datos[1] %></option>
                  <%  
									  }%>
                </select></td>
              </tr>
              <tr class="fila-det-bold-rojo">
                <td height="30" class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><%= BPF.getBacoAlerta() %>              
                  &nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td height="35" class="fila-det-border">Baco Total Remitos </td>
                <td class="fila-det-border">&nbsp;<%= BPF.getBacoTotalRemitosPedido() %></td>
              </tr>
              <tr class="fila-det">
                <td height="35" class="fila-det-border">Baco Pedidos Remito </td>
                <td class="fila-det-border">&nbsp;<%= BPF.getBacoTotalPedidosRemito() %></td>
              </tr>
              <tr class="fila-det">
                <td height="35" class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;<% if(BPF.isAutorizado()) {%><input name="validar" type="submit" value="Enviar" class="boton">  <% } %>             <input name="volver" type="submit" class="boton" id="volver" value="Volver"></td>
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

