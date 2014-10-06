<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: marketRegistroDireccion
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Mar 14 14:48:01 ART 2008 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.math.*" %>
<%@ page import="ar.com.syswarp.api.*" %>

<% 
try{ 
	Strings str = new Strings();
	String pathskin = "vs/market.css";
	String pathscript = "scripts";
	
	String mensaje = !str.esNulo(session.getAttribute("mensaje") + "" ).equals("") ? str.esNulo(session.getAttribute("mensaje")+ "" )  : str.esNulo(request.getParameter("mensaje"));
	
	
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <title></title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" type="text/css" href="<%= pathskin %>">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="24%" height="101">&nbsp;</td>
    <td width="54%" class="mensaje"><div align="center"><%= mensaje %></div></td>
    <td width="22%">&nbsp;</td>
  </tr>
  <tr>
  	<td width="24%" height="101">&nbsp;</td>
	<td width="54%"><div align="center">
	  <form action="index.jsp" method="post" name="frmvolver"><input type="submit" value="volver" class="boton"/></form>
	</div></td>
  </tr>
</table>

</BODY>
</html> 
<% 
   session.removeAttribute("mensaje");
 }
catch (Exception ex) {
   java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
   java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
   ex.printStackTrace(pw);
  System.out.println("ERROR (  marketAviso.jsp ) : "+ex);   
}%>

