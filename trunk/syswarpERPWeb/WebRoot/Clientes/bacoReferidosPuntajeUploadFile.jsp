<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: globalImagenes
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Mar 10 10:51:29 ART 2008 
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
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
String mensaje = str.esNulo(request.getParameter("mensaje"));
String estadoUpload = str.esNulo(request.getParameter("estadoUpload")).equals("") ?  "false" : request.getParameter("estadoUpload");
String path = str.esNulo(request.getParameter("path"));
String tipoArchivo = str.esNulo(  request.getParameter("tipoArchivo")  );
%>

<HTML>
<HEAD>
<TITLE>Carga de Archivos</TITLE> 
</HEAD> 
 <link rel="stylesheet" href="<%=pathskin%>"> 
 <!--BORRAR-->
 <link rel="stylesheet" href="../imagenes/default/erp-style.css"> 
 <link rel="stylesheet" href="vs/calendar/calendar.css"> 
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>
 <script>
 function bajarPath(){
   var estadoUpload = <%= estadoUpload %>;   
	 if(estadoUpload && opener.document.frm.patharchivo){
	   opener.document.frm.patharchivo.value = '<%= path %>';
     opener.document.frm.archivo.value = '<%= estadoUpload.equalsIgnoreCase("true") ? path.substring( path.lastIndexOf("/") + 1 ) : ""   %>';
     opener.document.frm.impactaTmp.value = 'true';
	 }	 
 }
 </script>
 
 
<BODY onLoad="bajarPath();"> 


<form method="POST" enctype="multipart/form-data" action="../servlet/UploadFicheroReferidosPuntaje?modulo=Clientes&tipoArchivo=<%= tipoArchivo %>"> 
      <table width="100%"  border="1" cellpadding="0" cellspacing="0">
		
        <tr> 
          <td height="38"  class="text-globales">CARGA DE ARCHIVOS </td> 
        </tr>
        <tr> 
          <td height="25" ><span class="fila-det-bold-rojo"><%= mensaje %></span> </td> 
        </tr>					
        <tr> 
          <td width="75%" height="35" >   
        <input name="fichero" type="file"  class="campo">
        <input name="submit" type="submit" value="Aceptar" class="campo">          </tr>
  </table> 
</form>

</BODY>
</HTML>
<% 
 }
catch (Exception ex) {
   java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
   java.io.PrintWriter pw = new java.io.PrintWriter(cw,true); 
   ex.printStackTrace(pw);
  System.out.println("ERROR (  uploadFile.jsp ) : "+ex);   
}%>