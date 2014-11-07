<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: proveedoArticulos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Aug 02 14:39:14 GMT-03:00 2006 
   Observaciones: 
      .


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.*" %> 
<%@ page import="java.math.*"%>
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
// variables de entorno

String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();

int i = 0;
int j = 0;

String comprobante = request.getParameter("comprobante");
String tipo = request.getParameter("tipo");
String plantillaImpresionJRXML = request.getParameter("plantillaImpresionJRXML");
String titulo = "Comprobantes de Movimientos de Tesoreria ";
String nombreParametro = "";

String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
String idempresa  = session.getAttribute("empresa").toString();
%>
<html>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 <link rel="stylesheet" type="text/css" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="popupcalendar" class="text"></div>
<form action="" method="POST" name="frm">

  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td height="47"  class="text-globales"><%=titulo%></td>
    </tr>
  </table>
   &nbsp; 
  <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr class="fila-encabezado">
    <td width="18%">Comprobante</td>
    <td width="25%">&nbsp;PDF </td>
    <td width="57%">&nbsp;</td>
  </tr>
  <% 
   String link = "../reportes/jasper/generaPDF.jsp?comprobante=" + comprobante + "&plantillaImpresionJRXML=" + plantillaImpresionJRXML + "&idempresa=" + idempresa ;   
    %>
  <tr class="fila-det" >
    <td class="fila-det-border" >&nbsp;</td>
    <td class="fila-det-border" ><div onClick="abrirVentana('<%= link %>', '', 800, 600)">
      <div align="justify"><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" border="0" style="cursor:pointer"></div>
    </div></td>
    <td class="fila-det-border" >&nbsp;</td>
  </tr >
</table>

	
</form> 
</body>
</html>
<% 

 }
catch (Exception ex) {
  System.out.println("ERROR (" + pagina + ") : "+ex);    
}%>