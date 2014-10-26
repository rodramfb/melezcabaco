<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.security.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.naming.directory.*" %>
<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="ar.com.syswarp.api.Common"%>
<%

java.util.Calendar hoy = new java.util.GregorianCalendar();
java.sql.Date  fHoy    = new java.sql.Date( hoy.getTime().getTime() );
java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd'/'MM'/'yyyy" );

String accion    = request.getParameter("accion");
String grabacion = request.getParameter("grabacion");
String codigo    = request.getParameter("codigo");
String titulo    = "Alta de Ejercicio";

String ejercicio = request.getParameter("ejercicio");
String fdesde    = request.getParameter("fidesde");
String fhasta    = request.getParameter("fihasta");
String usuario   = session.getAttribute("usuario").toString();
String ayudalink  = "ayuda.jsp?idayuda=9";   // link a las ayudas en linea de cada punto

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
	if (fdesde==null)     fdesde="";
	if (fhasta==null)     fhasta="";
%>	
<html>
  <head>
	<style>
	 a, A:link, a:visited, a:active
	 	{color: #0000aa; text-decoration: none; font-family: Tahoma, Verdana; font-size: 11px;}
 	A:hover
		{color: #ff0000; text-decoration: none; font-family: Tahoma, Verdana; font-size: 11px;}
  </style>
	<title><%=titulo%></title>	

	<link rel="stylesheet" href="jmc.css" type="text/css">
	<meta name="description" content="Free Cross Browser Javascript DHTML Menu Navigation">
	<meta name="keywords" content="JavaScript menu, DHTML menu, client side menu, dropdown menu, pulldown menu, popup menu, web authoring, scripting, freeware, download, shareware, free software, DHTML, Free Menu, site, navigation, html, web, netscape, explorer, IE, opera, DOM, control, cross browser, support, frames, target, download">
	<link rel="shortcut icon" href="http://www.softcomplex.com/products/tigra_menu/favicon.ico">
	<meta name="robots" content="index,follow">
  <link rel="stylesheet" type="text/css" href="vs/calendar/calendar.css">
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	 
	 <link rel="stylesheet" href="menu.css">
   
	<script language="JavaScript" src="vs/forms/forms.js"></script>
	<script language="JavaScript" src="vs/overlib/overlib.js"></script>
	<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
	
	<script language="JavaScript">
	
// generacion de un javascript dinamico a partir del recordset.
function validar(){  
  if (document.frm.ejercicio.value.length==0){
     alert('No se puede dejar vacia el campo Ejercicio');
     document.frm.ejercicio.focus();
     return false;
}	 

 if (document.frm.fidesde.value.length==0){
    alert("Seleccione la Fecha Desde");
		document.frm.fidesde.focus();
	  return false;		 	 
 }
 
 if (document.frm.fihasta.value.length==0){
    alert("Seleccione la Fecha Hasta");
		document.frm.fihasta.focus();
	  return false;		 	 
 }
 
 //valido que la fecha desde no sea mayor a la fecha hasta
 if (retornaPeriodo(document.frm.fidesde.value)>retornaPeriodo(document.frm.fihasta.value) ) {
    alert("Error: Por Favor verifique la fecha hasta no puede ser mayor a la fecha desde");
		 return false;	
 }
 
  if (document.frm.fihasta.value.length==0){
    alert("Seleccione la Fecha Hasta");
		document.frm.fihasta.focus();	 	 
		return false;
 }
 
	if ( confirm('Atencion:\n Confirma la creacion del nuevo ejercicio?\n (Una vez creado no se podra eliminar)') )
		 document.frm.submit();
	   return true;
}


</script>
<%	
// instancio el contable
Contable repo = null;
General gene =  null;   	    
try{

	General general = Common.getGeneral();
	Contable contable = Common.getContable();

	   if(ejercicio != null && !ejercicio.equals("") && fdesde !=null && !fdesde.equals("") && fhasta !=null && !fhasta.equals("")  ){
	      java.sql.Timestamp  fdes    =  general.StrToTimestampDDMMYYYY(fdesde);
	      java.sql.Timestamp  fhas    =  general.StrToTimestampDDMMYYYY(fhasta);	   
          if(!contable.setEjercicio(Integer.valueOf(ejercicio).intValue(), fdes, fhas, usuario, new BigDecimal(session.getAttribute("empresa").toString() ))){
  			     %><script>alert('Error: no se pudo crear el ejercicio solicitado, por favor verifique');</script><%
				  }
				  else{				   
				     %><script>alert('Se acaba de generar el Ejercicio ' + <%=ejercicio%> + ' en forma correcta');</script><%
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
		<form action="frmAltaEjercicio.jsp" name="frm" method="post">
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
            <td width="11%" height="23" class="fila-det-border" >Ejercicio (*) </td>
            <td width="89%" class="fila-det-border" ><input name="ejercicio" type="text" class="campo" id="ejercicio" size="10" maxlength="10"> 
            </td>
          </tr>
          <tr   class="fila-det"> 
            <td height="23" class="fila-det-border"  >Fecha Desde (*)</td>
            <td width="89%"class="fila-det-border" > 
						  <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fidesde" value="" maxlength="12">
              <a class="so-BtnLink" href="javascript:calClick();return false;" 
							   onmouseover="calSwapImg('BTN_date_0', 'img_Date_OVER',true);" 
								 onmouseout="calSwapImg('BTN_date_0', 'img_Date_UP',true);" 
								 onclick="calSwapImg('BTN_date_0', 'img_Date_DOWN');showCalendar('frm','fidesde','BTN_date_0');return false;"> 
              <img align="absmiddle" border="0" name="BTN_date_0" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>             </td>
          </tr>
          <tr class="fila-det"> 
            <td height="23" class="fila-det-border" >Fecha Hasta (*)</td>
            <td class="fila-det-border" >
						<input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fihasta" value="" maxlength="12">
              <a class="so-BtnLink" href="javascript:calClick();return false;" 
							   onmouseover="calSwapImg('BTN_date_1', 'img_Date_OVER',true);" 
								 onmouseout="calSwapImg('BTN_date_1', 'img_Date_UP',true);" 
								 onclick="calSwapImg('BTN_date_1', 'img_Date_DOWN');showCalendar('frm','fihasta','BTN_date_1');return false;"> 
              <img align="absmiddle" border="0" name="BTN_date_1" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>             </td>
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