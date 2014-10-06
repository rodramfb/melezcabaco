<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.security.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.naming.directory.*" %>
<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.math.BigDecimal" %>
<%
String grabacion = request.getParameter("grabacion");
String titulo    = "Activar Ejercicio Contable";
String ayudalink  = "ayuda.jsp?idayuda=9";   // link a las ayudas en linea de cada punto
String ejercicio           = request.getParameter("ejercicio");
String usuario             = session.getAttribute("usuario").toString();

// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
int _nivel      = Integer.valueOf(session.getAttribute("nivelusuario").toString()).intValue();

if( _nivel == 0 || _nivel == -1)
      response.sendRedirect("errorPage.jsp?error=Acceso denegado a esta aplicacion"); 
String readonly = "";
if(_nivel == 1) readonly = "readonly='true'";
System.out.println(readonly);

System.out.println("nivel "+ _nivel);

	if (ejercicio==null)  ejercicio="";
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
   
	<script language="JavaScript" src="scripts/forms/forms.js"></script>
	<script language="JavaScript" src="vs/forms/forms.js"></script>
	<script language="JavaScript" src="scripts/overlib/overlib.js"></script>
	<script language="JavaScript" src="vs/calendar/calendarcode.js"></script><script language="JavaScript">


// generacion de un javascript dinamico a partir del recordset.

function validar(){  
  if (document.frm.ejercicio.value.length==0){
     alert('No se puede dejar vacia el campo Ejercicio');
     document.frm.ejercicio.focus();
     return false;
}	 
  
	if (confirm('Atencion:\n Confirma la Activacion del Ejercicio Contable?') )
	   document.frm.submit();
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
				
	
	
	if(ejercicio != null && !ejercicio.equals("") ){
	   String rta = repo.activarEjercicio(Integer.valueOf(ejercicio).intValue(),new BigDecimal(session.getAttribute("empresa").toString() )); 
		 if (rta.equalsIgnoreCase("OK")){ 
		    %><script>alert('Se acaba de Activar el Ejercicio en forma correcta');</script><%
	   }
		 else{				   
			  %><script>alert('<%=rta%>');</script><%		
		 }
	   }  	  
	}
	
					       
   catch (Exception ex) {
     java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
     java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
     ex.printStackTrace(pw);
}  
%>

  <link rel = "stylesheet" href = "<%= pathskin %>">
  </head>
	<BODY leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	
<div id="popupcalendar" class="text"></div>
	<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
		<form action="frmActivarEjercicioContable.jsp" name="frm" method="post">
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
            <td><div align="center">
                <table width="13%" height="22" border="0" align="left" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="44%"><a href="javascript:popupHelp('<%=ayudalink%>');" ><img src="imagenes/default/gnome_tango/apps/help.png" width="22" height="22" border="0"></a></td>
                    <td width="56%"><div align="left"><a href="javascript:popupPrint()"><img src="imagenes/default/gnome_tango/actions/gtk-print-preview.png" width="22" height="22" border="0"></a></div></td>
                  </tr>
                </table>
              </div></td>
            <td><div align="center"></div></td>
          </tr>
        </table>
			  <table width="100%" border="0" align="center" cellpadding="1" cellspacing="1"  class="fila-det">
          <tr > 
            <td width="10%" height="23" class="fila-det-border" >Ejercicio (*) </td>
            <td width="90%" class="fila-det-border" >
			<input name="ejercicio" type="text" class="campo" id="ejercicio" size="10" maxlength="10"> 
            </td>
          </tr>
        </table>
			 <table width="100%" border="0" cellspacing="0" cellpadding="0" >
				<tr class="fila-det-verde"> 
					<td height="29" class="fila-det-border">&nbsp;(*) Indica que el campo es obligatorio</td>
				</tr>
			</table>
			<input type="hidden" name="grabacion" value="SI">			
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