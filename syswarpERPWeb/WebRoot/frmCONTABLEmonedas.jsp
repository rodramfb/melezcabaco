<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.security.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.naming.directory.*" %>
<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.math.BigDecimal" %>
<%
java.util.Calendar hoy = new java.util.GregorianCalendar();
java.sql.Date  fHoy    = new java.sql.Date( hoy.getTime().getTime() );
java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd'/'MM'/'yyyy" );

String accion    = request.getParameter("accion");
String grabacion = request.getParameter("grabacion");
String codigo    = request.getParameter("codigo");
String titulo    = "Monedas";

String idmoneda  = request.getParameter("idmoneda");
String moneda    = request.getParameter("moneda");
String hasta_mo  = request.getParameter("hasta_mo");
String ceros_mo  = request.getParameter("ceros_mo");
String detalle   = request.getParameter("detalle");
String usuario   = session.getAttribute("usuario").toString();
String formulario = "CONTABLEmonedas.jsp";
String action = "frmCONTABLEmonedas.jsp";
String readonly = "";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
int _nivel      = Integer.valueOf(session.getAttribute("nivelusuario").toString()).intValue();

if( _nivel == 0 || _nivel == -1)
      response.sendRedirect("errorPage.jsp?error=Acceso denegado a esta aplicacion"); 
if(_nivel == 1) readonly = "readonly='true'";
System.out.println(readonly);
System.out.println("nivel "+ _nivel);

	if (idmoneda==null)  idmoneda="";
	if (moneda==null)    moneda="";
	if (hasta_mo==null)  hasta_mo="";
	if (ceros_mo==null)  ceros_mo="";
	if (detalle==null)   detalle="";
	
	
	
// instancio el contable
Contable repo = null;
General gene =  null;   	 
try{
   	javax.naming.Context context = new javax.naming.InitialContext();   
   	Object object = context.lookup("Contable");
   	ContableHome sHome = (ContableHome) javax.rmi.PortableRemoteObject.narrow(object, ContableHome.class);
    repo =   sHome.create();  
    Object objgen = context.lookup("General");
    GeneralHome sGen = (GeneralHome) javax.rmi.PortableRemoteObject.narrow(objgen, GeneralHome.class);
    gene =   sGen.create();     
   }
   catch (Exception ex) {
     java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
     java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
     ex.printStackTrace(pw);
  }  

if(grabacion == null && accion.equalsIgnoreCase("Modificacion") && codigo != null ){
   java.util.List monedas = repo.getMonedasPK(new Integer(codigo),new BigDecimal(session.getAttribute("empresa").toString() )); 
   java.util.Iterator itermonedas   = monedas.iterator();
	 while(itermonedas.hasNext()){  
	    String[] sCampos = (String[]) itermonedas.next(); 
		  idmoneda  = sCampos[0];
			moneda = sCampos[1];
			hasta_mo =  gene.TimestampToStrDDMMYYYY ( gene.StrToTimestampDDMMYYYYHHMISE( sCampos[2] ) )   ;
			ceros_mo = sCampos[3];
			detalle = sCampos[4];		
	 }	 
}

//grabo el Alta  
if(grabacion != null && accion.equalsIgnoreCase("Alta")){ 
     System.out.println("moneda: " + moneda);
	   System.out.println("hasta_mo: " + hasta_mo);
	   System.out.println("ceros_mo: " + ceros_mo); 
	   System.out.println("detalle: " + detalle); 			
     String respuesta = repo.monedasSave(moneda.toUpperCase(), gene.StrToTimestampDDMMYYYY(hasta_mo), Integer.parseInt(ceros_mo), detalle.toUpperCase(), usuario, new BigDecimal(session.getAttribute("empresa").toString() ));	
	   if(respuesta.equalsIgnoreCase("OK")){
	      %><script>alert('Se dio de Alta Correctamente');</script><% 
		    System.out.println("Respuesta: " + respuesta);
        response.sendRedirect(formulario);	
     }
	   else{				   
		    %><script>alert('<%=respuesta%>');</script><%  
	   }
}    
//grabo la modificacion
if(grabacion != null && accion.equalsIgnoreCase("Modificacion") && codigo != null ){  
   System.out.println("idmoneda: " + idmoneda);
	 System.out.println("moneda: " + moneda);
	 System.out.println("hasta_mo: " + hasta_mo);
	 System.out.println("ceros_mo: " + ceros_mo); 
	 System.out.println("detalle: " + detalle); 
	 System.out.println("usuario: " + usuario);			 
   String respuesta = repo.monedasSaveOrUpdate(Long.parseLong(idmoneda), moneda.toUpperCase(), gene.StrToTimestampDDMMYYYY(hasta_mo), Integer.parseInt(ceros_mo), detalle.toUpperCase(), usuario, new BigDecimal(session.getAttribute("empresa").toString() ));	
	 if(respuesta.equalsIgnoreCase("OK")){
	    %><script>alert('Se Modifico Correctamente');</script><% 
		  System.out.println("Respuesta: " + respuesta);
      response.sendRedirect(formulario);
	 }	  
   else{				   
      %><script>alert('<%=respuesta%>');</script><%  
   }   
}
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
  if (document.frm.moneda.value.length==0){
     alert('No se puede dejar vacia el campo Moneda');
     document.frm.moneda.focus();
     return false;
}	 
if (document.frm.hasta_mo.value.length==0){
    alert("Seleccione la Fecha de duracion hasta");
		document.frm.hasta_mo.focus();
	  return false;		 	 
 }
if( !isCharsInBag( document.frm.ceros_mo.value.toLowerCase(), varEnt )|| document.frm.ceros_mo.value=="" ){
   alert( 'El campo Cantidad de Ceros solo admite numéricos y no puede estar vacio.\n 0123456789.' );
   document.frm.ceros_mo.focus();
   return false;
 }
if (document.frm.detalle.value.length==0){
    alert('No se puede dejar vacia el campo Detalle');
		document.frm.detalle.focus();
	  return false;		 	 
 } 
 if ( confirm('Confirma?') )
		 document.frm.submit();
	   return true;
}
</script>

<link rel = "stylesheet" href = "<%= pathskin %>">
</head>
<BODY leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<div id="popupcalendar" class="text"></div>
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="frmCONTABLEmonedas.jsp" name="frm" method="post">
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
                    <td width="44%"><a href="javascript:popupHelp();" ><img src="imagenes/default/gnome_tango/apps/help.png" width="22" height="22" border="0"></a></td>
                    <td width="56%"><div align="left"><a href="javascript:popupPrint()"><img src="imagenes/default/gnome_tango/actions/gtk-print-preview.png" width="22" height="22" border="0"></a></div></td>
                  </tr>
                </table>
              </div></td>
            <td><div align="center"></div></td>
          </tr>
        </table>
			  <table width="100%" border="0" align="center" cellpadding="1" cellspacing="1"   class="fila-det">
        <input name="idmoneda" type="hidden" class="campo" id="idmoneda" size="20" maxlength="30"  value="<%=idmoneda%>" <%=readonly%>>
				
          <tr   class="fila-det">  
            <td width="15%" height="23" class="fila-det-border" >Moneda (*)</td>
            <td width="85%" class="fila-det-border" >
						<input name="moneda" type="text" class="campo" id="moneda" size="20" maxlength="30" value="<%=moneda%>"  <%=readonly%>>
            </td>
          </tr>
					
          <tr class="fila-det"> 
              <td height="23" class="fila-det-border"  >Duracion Hasta (*)</td>
              <td width="85%"  class="fila-det-border" > 
						     <a class="so-BtnLink" href="javascript:calClick();return false;" 
							   onmouseover="calSwapImg('BTN_date_0', 'img_Date_OVER',true);" 
								 onmouseout="calSwapImg('BTN_date_0', 'img_Date_UP',true);" 
								 onclick="calSwapImg('BTN_date_0', 'img_Date_DOWN');showCalendar('frm','hasta_mo','BTN_date_0');return false;">								  
								 <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="hasta_mo" value="<%= hasta_mo %>" maxlength="12"> 
								 <img align="absmiddle" border="0" name="BTN_date_0" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>
		    </td>
          </tr>	
									
          <tr class="fila-det"> 
            <td height="23" class="fila-det-border" >Cantidad de Ceros (*)</td>
            <td class="fila-det-border" >
						<input name="ceros_mo" type="text" class="campo" id="ceros_mo" size="10" maxlength="3" value="<%=ceros_mo%>"  <%=readonly%>></td>
          </tr>
					
					<tr class="fila-det"> 
            <td height="23" class="fila-det-border" >Detalle (*)</td>
            <td class="fila-det-border" >
						<input name="detalle" type="text" class="campo" id="detalle" size="20" maxlength="30" value="<%=detalle%>"  <%=readonly%>></td>
          </tr>
        </table>
				
			<table width="100%" border="0" cellspacing="0" cellpadding="0" >
				<tr class="fila-det-verde"> 
					  <td height="29" class="fila-det-border">&nbsp;(*) Indica que el campo 
              es obligatorio </td>
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