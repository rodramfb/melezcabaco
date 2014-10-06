<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.security.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.naming.directory.*" %>
<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ page import="java.util.Iterator" %>
<%@ page import="ar.com.syswarp.validar.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>

<%

Strings str = new Strings();
String titulo    = "Renumeración de Asientos";
String ayudalink  = "ayuda.jsp?idayuda=4";  // link a las ayudas en linea de cada punto
String usuario      = session.getAttribute("usuario").toString();
String ejercicioActivo   = session.getAttribute("ejercicioActivo").toString();
String pagina = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1 );
String referente = request.getHeader("referer").substring(request.getHeader("referer").lastIndexOf("/") + 1 );
String aceptar      = str.esNulo( request.getParameter("aceptar") );
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
int _nivel      = Integer.valueOf(session.getAttribute("nivelusuario").toString()).intValue();
if( _nivel == 0 || _nivel == -1)
  response.sendRedirect("errorPage.jsp?error=Acceso denegado a esta aplicacion"); 
String readonly = "";
if(_nivel == 1) readonly = "readonly='true'";

Iterator iterEjercicios   = null;
Iterator IterMeses        = null;

%>	
<html>
  <head>	
	<link rel="stylesheet" href="jmc.css" type="text/css">
	<meta name="description" content="Free Cross Browser Javascript DHTML Menu Navigation">
	<meta name="keywords" content="JavaScript menu, DHTML menu, client side menu, dropdown menu, pulldown menu, popup menu, web authoring, scripting, freeware, download, shareware, free software, DHTML, Free Menu, site, navigation, html, web, netscape, explorer, IE, opera, DOM, control, cross browser, support, frames, target, download">
	<link rel="shortcut icon" href="http://www.softcomplex.com/products/tigra_menu/favicon.ico">
	<meta name="robots" content="index,follow">
  <link rel="stylesheet" type="text/css" href="vs/calendar/calendar.css">
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	
	<link rel="stylesheet" href="menu.css">
   
<script language="JavaScript" src="vs/forms/forms.js"></script>
<script language="JavaScript" src="scripts/overlib/overlib.js"></script>
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
<script language="JavaScript">

	function validar(){ 
		if(confirm("ATENCION: \n\t Esta a punto de renumerar el ejercicio activo.  Confirma ?")){
		  return true;
		}
		else {
		  return false;
		}
	}
	
</script>
<%	
// instancio el contable
Contable repo = null;
General gene =  null;   	    
try{
	// instanciar bean general
	javax.naming.Context context = new javax.naming.InitialContext();
	// INSTANCIAR EL MODULO GENERAL 
	Object objgen = context.lookup("General");
	GeneralHome sGen = (GeneralHome) javax.rmi.PortableRemoteObject.narrow(objgen, GeneralHome.class);
	gene =   sGen.create();   	  
	// INSTANCIAR EL MODULO CONTABLE 
	Object object = context.lookup("Contable");
	ContableHome sHome = (ContableHome) javax.rmi.PortableRemoteObject.narrow(object, ContableHome.class);
	repo =   sHome.create();   
	%>
	<link rel = "stylesheet" href = "<%= pathskin %>">
  </head>
	<BODY leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<div id="popupcalendar" class="text"></div>
	<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
	<form action="frmCONTABLEAsientosRenumera.jsp" name="frm" method="post" onSubmit="javascript: return validar();" >
	<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" >
		<tr>
			<td>		  
				<table width="100%" border="0" cellpadding="0" cellspacing="0" >
					<tr > 
						<td width="39" height="23"  >&nbsp;</td>
						<td width="243"  >&nbsp; </td>
						<td width="432" >&nbsp;</td>
						<td width="8" >&nbsp;</td>
					</tr>
					<tr valign="top" class="text-globales" > 
						<td width="39" height="23"  >&nbsp;</td>
						<td width="243"  ><%=titulo%></td>
						<td>
						  <div align="center">
							<table width="13%" height="22" border="0" align="left" cellpadding="0" cellspacing="0">
								<tr>
									<td width="44%"><a href="javascript:popupHelp('<%=ayudalink%>');" ><img src="imagenes/default/gnome_tango/apps/help.png" width="22" height="22" border="0"></a></td>
									<td width="56%"><div align="left"><a href="javascript:popupPrint()"><img src="imagenes/default/gnome_tango/actions/gtk-print-preview.png" width="22" height="22" border="0"></a></div></td>
								</tr>
							</table>
							</div>
						</td>
						<td><div align="center"></div></td>
					</tr>
			 </table>

				<table width="100%" border="0" cellspacing="0" cellpadding="0" >
					<tr class="fila-det-bold">
					  <td height="29" class="fila-det-border">&nbsp;</td>
				  </tr>
					<tr class="fila-det-bold"> 
						<td width="52%" height="29" class="fila-det-border"><div align="center" class="fila-det-bold">Mediante esta operaci&oacute;n se asignar&aacute; un nuevo n&uacute;mero de asiento </div></td>
				  </tr>
					<tr class="fila-det-bold">
					  <td height="29" class="fila-det-border"><div align="center" class="fila-det-bold">para los asientos correspondientes al ejercicio activo </div></td>
				  </tr>
					<tr class="fila-det-bold">
					  <td height="29" class="fila-det-border"><div align="center" class="fila-det-bold">una vez confirmada la operaci&oacute;n, se perder&aacute; la antigua numeraci&oacute;n. </div></td>
				  </tr>
						    <%
					String  salida = "";
					if(! aceptar.equalsIgnoreCase("")){
						 salida    = repo.setRenumeracionAsiento( Integer.parseInt(ejercicioActivo),new BigDecimal(session.getAttribute("empresa").toString() )  );
							%>
					<tr class="fila-det-bold">
					  <td height="29" class="fila-det-border">
						  <div align="center" class="fila-det-bold-rojo">
									<%= salida %>						
						  </div>
						</td>
				  </tr>
				<%
					}%>					
				</table>
				<table width="100%" height="32" border="0" align="center" cellpadding="0" cellspacing="0" >
					<tr class="text-seis">
						<td width="18%">
						  <div align="center">
						    <p>&nbsp;
					      </p>
						    <p>
						      <%if(_nivel == 2){%>
								  <input name="aceptar" type="submit" class="boton" value="Renumerar" >
						      <%}%>		
							&nbsp;
					      </p>
					  </div></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<%
		
}		
catch (Exception ex) {
	java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
	java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
	ex.printStackTrace(pw);
	System.out.println("Error (" + pagina + "): " + ex);
}  
%>
</form>
</body>
</html>