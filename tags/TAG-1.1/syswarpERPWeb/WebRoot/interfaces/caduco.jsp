<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.Iterator" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ page import="ar.com.syswarp.ejb.*" %> 
<%@ page import="java.math.BigDecimal" %>
<%
String titulo     = "Recepcion de Remitos";
Strings str = new Strings();
String remito     = str.esNulo(request.getParameter("remito"));
String conjunto   = str.esNulo(request.getParameter("conjunto"));
String d_conjunto = str.esNulo(request.getParameter("d_conjunto"));
String idestado   = str.esNulo(request.getParameter("idestado"));
String estado     = str.esNulo(request.getParameter("estado"));
String confirma   = request.getParameter("confirma");
String ejecutar   = str.esNulo(request.getParameter("ejecutar"));
String usuarioalt = str.esNulo( request.getParameter("usuario")  != null ? request.getParameter("usuario")  + "" :   session.getAttribute("usuario") + "");
String usuarioact = str.esNulo(request.getParameter("usuarioact"));
String fechaalt   = str.esNulo(request.getParameter("fechaalt"));
String fechaact   = str.esNulo(request.getParameter("fechaact"));
String mensaje = "";
String [] resultado = new String[3];
String idempresa = str.esNulo(request.getParameter("idempresa"));
%>
<html>
<head>
	<title><%=titulo%></title>	
	<link rel="stylesheet" href="jmc.css" type="text/css">
	<meta name="description" content="Free Cross Browser Javascript DHTML Menu Navigation">
	<meta name="keywords" content="JavaScript menu, DHTML menu, client side menu, dropdown menu, pulldown menu, popup menu, web authoring, scripting, freeware, download, shareware, free software, DHTML, Free Menu, site, navigation, html, web, netscape, explorer, IE, opera, DOM, control, cross browser, support, frames, target, download">
	<link rel="shortcut icon" href="http://www.softcomplex.com/products/tigra_menu/favicon.ico">
	<meta name="robots" content="index,follow">
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	 <script language="JavaScript" src="scripts/forms/forms.js"></script>
	 <script language="JavaScript" src="scripts/overlib/overlib.js"></script>
<link href="../imagenes/default/erp-style.css" rel="stylesheet" type="text/css">
</head>
<BODY leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div> 

<% 
try{ 
 %>

<form action="" name="frm" method="post">

  <table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="fila-det">
    <tbody>
      <tr>
        <td width="23%" height="144" class="fila-det-border"><div align="center" class="text-nueve"> - CADUCO LA SESION. POR FAVOR INGRESE NUEVAMENTE. - </div></td>
      </tr>
    </tbody>
  </table>
  <table width="100%" align="center" cellpadding="1" cellspacing="1" class="color-tabletrim">
    <tbody>
		  <tr class="color-tableheader"> 
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tbody><tr> 
            <td width="0%" height="19"> <div align="center"></div></td>
            <td width="26%"><strong class="titlerev"> </strong></td>
            <td width="5%"><strong class="titlerev"> </strong></td>
            <td width="4%">&nbsp; </td>
            <td width="5%">&nbsp;</td>
            <td width="28%"> </td>
            <td width="5%">&nbsp;</td>
            <td width="27%">&nbsp; </td>
          </tr>
        </tbody>
				</table>
    </tr>
  </tbody>
	</table>
</form>
<% 
}
catch (Exception ex) {
   System.out.println("ERROR - [INTERFACES/RecepcionRemitos]: " + ex); 
  
}%>
</body>
</html>       
