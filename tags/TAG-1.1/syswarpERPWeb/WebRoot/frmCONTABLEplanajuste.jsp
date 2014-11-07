<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.security.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.naming.directory.*" %>
<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="ar.com.syswarp.api.Common"%>
<%
String accion = request.getParameter("accion");
String grabacion = request.getParameter("grabacion");
// variables de afuera
String codigo = request.getParameter("codigo");
// variables de formulario
String titulo = "Pan de Ajuste";

String cuenta_pl = request.getParameter("cuenta");
String indice_pl = request.getParameter("indice");
String ano = request.getParameter("ano");
String usuario    = session.getAttribute("usuario").toString();

String formulario = "CONTABLEplanajuste.jsp";
String action     = "frmCONTABLEplanajuste.jsp";



// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
int _nivel      = Integer.valueOf(session.getAttribute("nivelusuario").toString()).intValue();

if( _nivel == 0 || _nivel == -1)
      response.sendRedirect("errorPage.jsp?error=Acceso denegado a esta aplicacion"); 
String readonly = "";
if(_nivel == 1) readonly = "readonly='true'";
System.out.println(readonly);

System.out.println("nivel "+ _nivel);

	if (cuenta_pl==null)  cuenta_pl="";
	if (indice_pl==null)  indice_pl="";
  if (ano==null)  ano="";
	
// instancio el contable
java.util.Iterator iterMes=null;
try{
    General general = Common.getGeneral();
    Contable contable = Common.getContable();   	    
}	   

   catch (Exception ex) {
     java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
     java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
     ex.printStackTrace(pw);
}  

if(grabacion != null && accion.equalsIgnoreCase("alta")){ 	 	
   String respuesta = contable.PlanAjusSave(new Long(Long.parseLong(cuenta_pl)), new Long(Long.parseLong(indice_pl)), usuario, new BigDecimal(session.getAttribute("empresa").toString() ));
   System.out.println("cuenta_pl: " + cuenta_pl);
   System.out.println("indice_pl: " + indice_pl); 
   System.out.println("usuario: " + usuario);
	if(!respuesta.equalsIgnoreCase("OK")){
	   %><script>alert('<%=respuesta%>');</script><%  	
	}
	else{				   
		  %><script>alert('Se grabo en forma correcta!');</script><%
	System.out.println("Respuesta: " + respuesta);
       response.sendRedirect(formulario);
	}
}

%>
<html>
  <head>
	<title><%=titulo%></title>	

	<link rel="stylesheet" href="jmc.css" type="text/css">
	<meta name="description" content="Free Cross Browser Javascript DHTML Menu Navigation">
	<meta name="keywords" content="JavaScript menu, DHTML menu, client side menu, dropdown menu, pulldown menu, popup menu, web authoring, scripting, freeware, download, shareware, free software, DHTML, Free Menu, site, navigation, html, web, netscape, explorer, IE, opera, DOM, control, cross browser, support, frames, target, download">
	<link rel="shortcut icon" href="http://www.softcomplex.com/products/tigra_menu/favicon.ico">
	<meta name="robots" content="index,follow">
  <link rel="stylesheet" type="text/css" href="vs/calendar/calendar.css">
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	 
	 <link rel="stylesheet" href="menu.css">
   
   <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	 
	<script language="JavaScript" src="scripts/forms/forms.js"></script>
	<script language="JavaScript" src="scripts/overlib/overlib.js"></script>
	<script language="JavaScript" src="vs/calendar/calendarcode.js"></script><script language="JavaScript">
	
	
// generacion de un javascript dinamico a partir del recordset.
function mostrarLOV(pagina) {
		frmLOV = open(pagina,'winLOV','scrollbars=yes,resizable=yes,width=600,height=350,status=yes');
		if (frmLOV.opener == null) 
			frmLOV.opener = self;
}		
function validar(){
if (document.frm.cuenta.value==""){
   alert("Atencion: No se puede dejar vacio el campo cuenta");
   document.frm.cuenta.focus();
   return false;
}
if (document.frm.indice.value==""){
   alert("Atencion: No se puede dejar vacio el campo Indice");
   document.frm.indice.focus();
   return false;
}
   
if ( confirm('Confirma?') ){
		document.frm.submit();
	} 
	return true;
}

function isCharsInBag (s, bag)
  {
    var i;
    // Search through string's characters one by one.
    // If character is in bag, append to returnString.

    for (i = 0; i < s.length; i++)
    {
        // Check that current character isn't whitespace.
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) {
         if (s.charCodeAt(i) != 10 && s.charCodeAt(i) != 13) return false;
         }
    }
    return true;
  }
</script>
  <link rel = "stylesheet" href = "<%= pathskin %>">
  </head>
	<BODY leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
		<form action="<%=action%>" name="frm" method="post">
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
					<td width="432" ><%= accion %></td>
					<td width="8" >&nbsp;</td>
				</tr>
			</table>
			  <table width="100%" border="0" align="center" cellpadding="1" cellspacing="1"  class="fila-det">
    		  <tr class="fila-det">
				    <td width="12%" height="23" class="fila-det-border"  >Cuenta(*)</td>
				    <td width="11%" class="fila-det-border" ><input type="text" name="cuenta" width="100" class="campo" value="<%=cuenta_pl%>" readonly="yes">
				    <td width="14%" class="fila-det-border" >
					    <img src="imagenes/default/gnome_tango/actions/edit-find.png" width="22" height="22" onClick="mostrarLOV('lov_cuentascontables.jsp')" style="cursor:pointer">
				    </td>
						<td   class="fila-det-border" >&nbsp;</td>						
            <td   class="fila-det-border" >&nbsp;</td>						
			    </tr>	
          <tr class="fila-det"> 
            <td height="23" class="fila-det-border" >Indice&nbsp;(*) </td>
            <td width="11%" class="fila-det-border" >
						  <input name="indice" width="100" type="text" class="campo" id="indice" value="<%=indice_pl%>" readonly="yes">
            </td>
					  <td width="14%" class="fila-det-border" >
						  <input name="ano" width="100" type="text" class="campo" id="ano" value="<%=ano%>" readonly="yes">
					  </td>
            <td width="27%" class="fila-det-border" >
						  <img src="imagenes/default/gnome_tango/actions/edit-find.png" width="22" height="22" onClick="mostrarLOV('lov_ContablePlanAjuste.jsp')" style="cursor:pointer"> 
            </td>
						<td width="36%" class="fila-det-border" >&nbsp;</td>
			    </tr>
        </table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" >
				<tr class="fila-det-verde"> 
					<td height="29" class="fila-det-border">&nbsp;(*) Indica que el campo es obligatorio</td>
				</tr>
			</table>
			
			<input type="hidden" name="grabacion" value="SI">
			<input type="hidden" name="accion" value="<%= accion %>">
			<input type="hidden" name="codigo" value="<%= codigo %>">
			
			<table width="100%" height="32" border="0" align="center" cellpadding="0" cellspacing="0" >
				<tr class="text-seis">
					<td width="18%">
					<%if(_nivel == 2){%>
							<input name="grabar" type="button" class="boton" onClick="validar();" value="Aceptar">
					<%}%>		
						&nbsp;
					</td>
					<td width="82%">&nbsp;
							<input name="Submit2" type="reset" class="boton" value="Reset">
					</td>
				</tr>
			</table>
			<table width="100%" cellpadding="1" cellspacing="1" >
				<tr > 
					<td width="100%" height="21" colspan="7" > 
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr> 
								<td width="0%" height="19">&nbsp; </td>
								<td width="26%"></td>
								<td width="5%"></td>
								<td width="4%">&nbsp; </td>
								<td width="5%">&nbsp;</td>
								<td width="28%"> </td>
								<td width="5%">&nbsp;</td>
								<td width="27%">&nbsp; </td>
							</tr>
						</table>
					</td>
			  </tr>
		  </table>
		</td>
  </tr>
</table>
		</form>
	</body>
</html>