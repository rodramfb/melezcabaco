<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.security.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.naming.directory.*" %>
<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.regex.*" %>
<%@ page import="ar.com.syswarp.validar.*" %>
<%@ page import="java.sql.*" %>
<%
Strings str = new Strings();
String codigo = str.esNulo(request.getParameter("codigo"));
// variables de formulario
String usuario    = session.getAttribute("usuario").toString();
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
int _nivel      = Integer.valueOf(session.getAttribute("nivelusuario").toString()).intValue();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title> </title>	  
	<link rel="stylesheet" href="jmc.css" type="text/css">
	<meta name="description" content="Free Cross Browser Javascript DHTML Menu Navigation">
	<meta name="keywords" content="JavaScript menu, DHTML menu, client side menu, dropdown menu, pulldown menu, popup menu, web authoring, scripting, freeware, download, shareware, free software, DHTML, Free Menu, site, navigation, html, web, netscape, explorer, IE, opera, DOM, control, cross browser, support, frames, target, download">
	<link rel="shortcut icon" href="http://www.softcomplex.com/products/tigra_menu/favicon.ico">
	<meta name="robots" content="index,follow">
  <link rel="stylesheet" type="text/css" href="vs/calendar/calendar.css">
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	
  <link rel = "stylesheet" href = "<%= pathskin %>">
	
	<script language="JavaScript" src="vs/forms/forms.js"></script>
	<script language="JavaScript" src="vs/overlib/overlib.js"></script>
	<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
   <script>
	 function sendAccion(accion, obj){
	 
	   if(accion.toLowerCase() != "alta"){
			 if(trim(document.frmAsientosTop.codigo.value)==""){
				 alert("Es necesario realizar la busqueda de un asiento para ejecutar una " + accion + ".\n");
				 mOvr(obj,obj.className='fila-det-verde');
				 return false;
			 }
		 }
		 
	   document.frmAsientosTop.accion.value = accion;
	   document.frmAsientosTop.submit();
	 }
	 </script>
</head>

<body>
<form name="frmAsientosTop" method="post" action="frmCONTABLEAsientos.jsp" target="bottomFrameAsientos">
<table width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
  <tr  class="titulo" >
    <td width="33" height="23"  >&nbsp;</td>
    <td colspan="5" >Operaci&oacute;n a Ejecutar </td>
    </tr>
		<tr valign="top"  >
			<td width="33" height="23"  >&nbsp;</td>
			<td width="162" class="fila-det-verde"  ></td>
			<td width="141" class="fila-det-verde" ></td>
			<td width="167" class="fila-det-verde" >&nbsp;</td>
			<td colspan="2" class="fila-det-verde" >&nbsp;</td>
		</tr>
		<tr valign="top"  >
			<td height="23"  >&nbsp;</td>
			<td class="fila-det-verde" onClick="mOvr(this,this.className='fila-det-bold-rojo');mOvr(document.getElementById('baja'),document.getElementById('baja').className='fila-det-verde');mOvr(document.getElementById('modi'),document.getElementById('modi').className='fila-det-verde');sendAccion('Alta', this);" id="alta"><div style="cursor:pointer" >Alta</div></td>
			<td class="fila-det-verde" onClick="mOvr(this,this.className='fila-det-bold-rojo');mOvr(document.getElementById('alta'),document.getElementById('alta').className='fila-det-verde');mOvr(document.getElementById('baja'),document.getElementById('baja').className='fila-det-verde');sendAccion('Modificacion', this);" id="modi"><div style="cursor:pointer" >Modificaci&oacute;n</div></td>
			<td class="fila-det-verde" onClick="mOvr(this,this.className='fila-det-bold-rojo');mOvr(document.getElementById('alta'),document.getElementById('alta').className='fila-det-verde');mOvr(document.getElementById('modi'),document.getElementById('modi').className='fila-det-verde');sendAccion('Baja', this);" id="baja"><div style="cursor:pointer" >Baja</div></td>
			<td width="163" class="fila-det-verde" >
			  Asiento Nro.: 
			  <input name="codigo" type="text" class="campo" id="codigo" size="10" maxlength="18" readonly="yes">			
			</td>
			<td width="321" class="fila-det-verde" ><img src="imagenes/default/gnome_tango/actions/edit-find.png" width="22" height="22" onClick="mostrarLOV('lov_asientos.jsp')" style="cursor:pointer"></td>
		</tr>
</table>
<input type="hidden" value="" name="accion">
</form>
</body>
</html>