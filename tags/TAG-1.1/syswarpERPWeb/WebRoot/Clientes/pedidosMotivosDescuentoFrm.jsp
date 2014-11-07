<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: pedidosMotivosDescuento
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Mar 11 08:31:00 GYT 2009 
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
<jsp:useBean id="BPMDF"  class="ar.com.syswarp.web.ejb.BeanPedidosMotivosDescuentoFrm"   scope="page"/>
<head>
 <title>FRMPedidosMotivosDescuento</title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BPMDF" property="*" />
 <% 
 String titulo = BPMDF.getAccion().toUpperCase() + " DE MOTIVOS DE DESCUENTO" ;
 BPMDF.setResponse(response);
 BPMDF.setRequest(request);
 BPMDF.setEjercicio( new BigDecimal( (String) session.getAttribute("ejercicioActivo") ) ) ; 
 BPMDF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BPMDF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPMDF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPMDF.ejecutarValidacion();
 %>
<form action="pedidosMotivosDescuentoFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BPMDF.getAccion()%>" >
<input name="idmotivodescuento" type="hidden" value="<%=BPMDF.getIdmotivodescuento()%>" >
   <table width="95%"  border="0" cellspacing="0" cellpadding="0" align="center">
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
                <td colspan="2" class="fila-det-border"><jsp:getProperty name="BPMDF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="14%" height="29" class="fila-det-border">&nbsp;Motivo: (*) </td>
                <td colspan="2" class="fila-det-border">&nbsp;<input name="motivodescuento" type="text" value="<%=BPMDF.getMotivodescuento()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="14%" height="30" class="fila-det-border">&nbsp;Cuenta: (*) </td>
                <td width="46%" class="fila-det-border">&nbsp;<input name="idcuenta" type="text" value="<%=BPMDF.getIdcuenta()%>" class="campo" size="15" maxlength="20" readonly>
                <input name="cuenta" type="text" class="campo" id="cuenta" value="<%=BPMDF.getCuenta()%>" size="40" maxlength="100"  readonly></td>
                <td width="40%" class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" style="cursor:pointer" onClick="abrirVentana('../Contable/lov_ccontables.jsp', 'infiplan', 750, 450);"></td>
              </tr>
              <tr class="fila-det">
                <td height="34" class="fila-det-border">&nbsp;</td>
                <td colspan="2" class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Enviar" class="boton">               <input name="volver" type="submit" class="boton" id="volver" value="Volver"></td>
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

