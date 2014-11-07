<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: Inspecciones
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Jul 19 14:39:38 GMT-03:00 2006 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias

*/ 
%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%@ include file="session.jspf"%>
<%@ page import="java.util.*" %>
<%@ page import="java.math.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.web.ejb.*" %>
<% 
try{
Strings str = new Strings();
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
String sel = "";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  

<head>
 <title></title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>

<script>
function imprimeComprobante(){
	var app = navigator.appName.toLowerCase();
	window.parent.frames['cajaArribaFrame'].focus();
	if(app.indexOf("microsoft")!= "-1")
		window.print(); 
	else if(app.indexOf("netscape")!= "-1")
		window.parent.frames['cajaAbajoFrame'].print();
}


</script>
</head>
<BODY >
<table width="90%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr class="fila-det">
    <td width="19%" height="44" >&nbsp;</td>
    <td width="31%" >
      <input name="imprimir" type="button" class="boton" id="imprimir" value="Imprimir Comprobante" onClick="imprimeComprobante()">
</td>
    <td width="28%" >&nbsp;</td>
    <td width="22%"  >&nbsp;</td>
  </tr>
</table>
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