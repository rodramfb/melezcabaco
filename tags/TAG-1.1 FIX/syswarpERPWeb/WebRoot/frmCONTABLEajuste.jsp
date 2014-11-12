<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.security.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.naming.directory.*" %>
<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ page import="java.util.Iterator" %>
<%@ page import="ar.com.syswarp.validar.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="ar.com.syswarp.api.Common"%>
<%
Strings str = new Strings();
Iterator iterEjercicios   = null;
String accion = request.getParameter("accion");
String grabacion = request.getParameter("grabacion");
// variables de afuera
String codigo = request.getParameter("codigo");
// variables de formulario
String titulo = "Cuentas de Ajuste";

String cod_ajuste = request.getParameter("cod_ajuste");
//String anio       = request.getParameter("anio");
String mes        = request.getParameter("mes");
String indice     = request.getParameter("indice");
String usuario    = session.getAttribute("usuario").toString();
String anio      = str.esNulo( request.getParameter("anio") );

String formulario = "CONTABLEajuste.jsp";
String action     = "frmCONTABLEajuste.jsp";

java.util.List meses = new java.util.ArrayList();

// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
int _nivel      = Integer.valueOf(session.getAttribute("nivelusuario").toString()).intValue();

if( _nivel == 0 || _nivel == -1)
      response.sendRedirect("errorPage.jsp?error=Acceso denegado a esta aplicacion"); 
String readonly = "";
if(_nivel == 1) readonly = "readonly='true'";
System.out.println(readonly);

System.out.println("nivel "+ _nivel);

	if (cod_ajuste==null)  cod_ajuste="";
	if (anio==null)        anio="";
	if (mes==null)         mes="";
	if (indice==null)      indice="";
	
// instancio el contable
java.util.Iterator iterMes=null;
try{

	General general = Common.getGeneral();
	Contable contable = Common.getContable();
   	        
	meses =  general.getGlobalMeses();    
    iterMes = meses.iterator();
	List ejercicioContables = contable.getEjerciciosAll(new BigDecimal(session.getAttribute("empresa").toString() ));
	iterEjercicios = ejercicioContables.iterator();	
		
}	   

   catch (Exception ex) {
     java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
     java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
     ex.printStackTrace(pw);
}  

if(grabacion == null && accion.equalsIgnoreCase("modificacion") && codigo != null ){
   java.util.List Ajustes = contable.getAjustePK(new Integer(codigo), new BigDecimal(session.getAttribute("empresa").toString() )); 
   java.util.Iterator iterAjustes   = Ajustes.iterator();
	 while(iterAjustes.hasNext()){  
	    String[] sCampos = (String[]) iterAjustes.next(); 
			cod_ajuste  = sCampos[0];
			anio = sCampos[1];
			mes = sCampos[2];
			indice = sCampos[3];
	 }	   
}

if(grabacion != null && accion.equalsIgnoreCase("alta")){ 	 
  String respuesta = contable.AjusteSave(Integer.valueOf(anio).intValue(), Integer.valueOf(mes).intValue(), new Float(indice), usuario, new BigDecimal(session.getAttribute("empresa").toString() ));	
	if(!respuesta.equalsIgnoreCase("OK")){
	   %><script>alert('<%=respuesta%>');</script><%  	
	}
	else{				   
		 %><script>alert('Se grabo el centro de costo en forma correcta!');</script><%
		 System.out.println("Respuesta: " + respuesta);
     response.sendRedirect(formulario);
	}
}


if(grabacion != null && accion.equalsIgnoreCase("Modificacion") && codigo != null ){   
   System.out.println("cod_ajuste: " + cod_ajuste);
	 System.out.println("anio: " + anio);
	 System.out.println("mes: " + mes);
	 System.out.println("indice: " + indice); 		 
   String respuesta = contable.AjusteSaveOrUpdate(Integer.valueOf(anio).intValue(), Integer.valueOf(mes).intValue(), new Float(indice), usuario, new Integer(codigo),new BigDecimal(session.getAttribute("empresa").toString() ));
   System.out.println("Respuesta: " + respuesta);
	 response.sendRedirect(formulario);
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
   
   <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	 
	<script language="JavaScript" src="scripts/forms/forms.js"></script>
	<script language="JavaScript" src="scripts/overlib/overlib.js"></script>
  <script language="JavaScript">
// generacion de un javascript dinamico a partir del recordset.
function validar(){
 /*
  if( !isCharsInBag( document.frm.anio.value.toLowerCase(), '1234567890.' )|| document.frm.anio.value=="" ){
   alert( 'El Año solo admite numéricos y no puede estar vacio.\n 0123456789.' );
   document.frm.anio.focus();
   return false;
 }
*/
/*
  if( !isCharsInBag( document.frm.mes.value.toLowerCase(), '1234567890.' )|| document.frm.mes.value=="" ){
   alert( 'El Mes solo admite numericos y no puede estar vacio.\n 0123456789.' );
   document.frm.mes.focus();
   return false;
 }
*/
  if( !isCharsInBag( document.frm.indice.value.toLowerCase(), '1234567890.' )|| document.frm.indice.value=="" ){
   alert( 'El Indice solo admite numericos y no puede estar vacio.\n 0123456789.' );
   document.frm.indice.focus();
   return false;
 }


if (document.frm.anio.value==0){
			 alert('Debe seleccionar año');
			 document.frm.anio.focus();
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
				<tr >
					<td width="10%" height="23" class="fila-det-border" >A&ntilde;o&nbsp;(*)</td>
				<td width="90%" class="fila-det-border" ><select name="anio" class="campo">
<option value="0">Seleccionar</option>
<%                       
							while(iterEjercicios.hasNext()){
								String selected = "";                      
								String[] sCampos = (String[]) iterEjercicios.next(); 
								if(anio.equalsIgnoreCase(sCampos[0])) selected = "selected";
						%>
<option value="<%=sCampos[0]%>" <%=selected%>><%=sCampos[0]%></option>
<%
							}%>
</select></td>
				</tr>
				<tr   class="fila-det">
					<td height="23" class="fila-det-border"  >Mes&nbsp;(*)</td>
					<td class="fila-det-border" >
						<select name="mes" class="campo" id="mes">
						<%while(iterMes.hasNext()){ %>
						<%String[] sCampos = (String[]) iterMes.next(); %>
							 <option value="<%=sCampos[0]%>"><%=sCampos[1]%></option> 
						<%}%>
						</select>					</td>
				</tr>
				<tr class="fila-det">
					<td height="23" class="fila-det-border" >Indice&nbsp;(*) </td>				   
					<td class="fila-det-border" ><input name="indice" type="text" class="campo" id="indice" value="<%=indice%>" size="10" maxlength="10"  <%=readonly%>></td>
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