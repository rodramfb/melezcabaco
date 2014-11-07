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
<head>
 <title></title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">
<script language="JavaScript" src="<%=pathscript%>/forms.js"></script> 
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>


</head>
<BODY  topmargin="0" leftmargin="0" rightmargin="0">
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <% 
 String titulo =  " IMPRIMIR DEPOSITOS " ;
 String depositos = str.esNulo(request.getParameter("depositos")); 
 String tipoDeposito = str.esNulo(request.getParameter("tipoDeposito"));
 String plantillaImpresionJRXML="";
 if(tipoDeposito.equalsIgnoreCase("efectivo")){
   plantillaImpresionJRXML="depositos_efectivo";
   
    %>
  &nbsp;
  <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
     <tr>
       <td>
            <table width="70%" border="0" cellspacing="0" cellpadding="0" align="center"> 
 		      <tr class="text-globales">
			    <td class="fila-det-border">DEPOSITO</td>
			    <td class="fila-det-border">IMPRIMIR</td>
		      </tr>
			  <tr class="fila-det">
                <td width="23%" height="41" class="fila-det-border">Nro.: <%= depositos %> </td>
                <td width="77%" class="fila-det-border">&nbsp;<img src="../imagenes/default/gnome_tango/apps/pdf.jpg" style="cursor:pointer" onClick="abrirVentana('../reportes/jasper/generaPDF.jsp?comprob_mt=<%= depositos %>&plantillaImpresionJRXML=<%= plantillaImpresionJRXML %>');"></td>
              </tr>
          </table> 
       </td>
     </tr> 
  </table>  	
	<% 
 }
 else if(tipoDeposito.equalsIgnoreCase("cheque")){
   plantillaImpresionJRXML="depositos_cheques_frame";
     %>
  &nbsp;
  <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
     <tr>
       <td>
            <table width="70%" border="0" cellspacing="0" cellpadding="0" align="center"> 
 		      <tr class="text-globales">
			    <td class="fila-det-border">DEPOSITOS</td>
			    <td class="fila-det-border">IMPRIMIR</td>
		      </tr>
			  <tr class="fila-det">
                <td width="23%" height="41" class="fila-det-border">Nros.: <%= depositos %> </td>
                <td width="77%" class="fila-det-border">&nbsp;<img src="../imagenes/default/gnome_tango/apps/pdf.jpg" style="cursor:pointer" onClick="abrirVentana('../reportes/jasper/generaPDF.jsp?depositosToCollection=<%= depositos %>&plantillaImpresionJRXML=<%= plantillaImpresionJRXML %>'  );"></td>
              </tr>
          </table> 
       </td>
     </tr>
  </table>  	
	 <%
  
 }
 else{
   plantillaImpresionJRXML="depositos_efectivo";
   
   
 }
 %>

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

