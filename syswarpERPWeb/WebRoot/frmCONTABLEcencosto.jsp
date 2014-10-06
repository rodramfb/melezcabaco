<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.security.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.naming.directory.*" %>
<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ page import="java.math.BigDecimal" %>
<%

String accion = request.getParameter("accion");
String grabacion = request.getParameter("grabacion");
// variables de afuera
String codigo = request.getParameter("codigo");
// variables de formulario
String titulo = "Centro de Costo";


String idcencosto  = request.getParameter("idcencosto");
String descripcion = request.getParameter("descripcion");
String usuario    = session.getAttribute("usuario").toString();


String formulario = "CONTABLEcencosto.jsp";
String action = "frmCONTABLEcencosto.jsp";

// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
int _nivel      = Integer.valueOf(session.getAttribute("nivelusuario").toString()).intValue();

if( _nivel == 0 || _nivel == -1)
      response.sendRedirect("errorPage.jsp?error=Acceso denegado a esta aplicacion"); 
String readonly = "";
if(_nivel == 1) readonly = "readonly='true'";
System.out.println(readonly);

System.out.println("nivel "+ _nivel);

	if (idcencosto==null)  idcencosto="";
	if (descripcion==null) descripcion="";
	
// instancio el contable
Contable repo = null;
try{
   	javax.naming.Context context = new javax.naming.InitialContext();   
   	Object object = context.lookup("Contable");
   	ContableHome sHome = (ContableHome) javax.rmi.PortableRemoteObject.narrow(object, ContableHome.class);
    repo =   sHome.create();   	      
   }
   catch (Exception ex) {
     java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
     java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
     ex.printStackTrace(pw);
  }  


if(grabacion == null && accion.equalsIgnoreCase("modificacion") && codigo != null ){
   java.util.List Centrodecosto = repo.getCenCostoPK(codigo, new BigDecimal(session.getAttribute("empresa").toString() ) ); 
   java.util.Iterator iterCentrodeCosto   = Centrodecosto.iterator();
	 while(iterCentrodeCosto.hasNext()){  
	    String[] sCampos = (String[]) iterCentrodeCosto.next(); 
			idcencosto  = sCampos[0];
			descripcion = sCampos[1];
 }	
}

if(grabacion != null && accion.equalsIgnoreCase("alta")){ 	 
  String respuesta = repo.cenCostoSave(descripcion.toUpperCase(), usuario,new BigDecimal(session.getAttribute("empresa").toString() ) ); 
 	response.sendRedirect(formulario);	 
}

if(grabacion != null && accion.equalsIgnoreCase("modificacion") && codigo != null ){   
   System.out.println("idcencosto: " + idcencosto);
	 System.out.println("descripcion: " + descripcion);
	 
   String respuesta = repo.cenCostoSaveOrUpdate(descripcion.toUpperCase(), usuario, new Integer(codigo),new BigDecimal(session.getAttribute("empresa").toString() ) ); 
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

   if (document.frm.descripcion.value==""){
     alert("no se puede dejar vacio el campo descripcion");
     document.frm.descripcion.focus();
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
function mostrarLOV(pagina) {
   frmLOV = open(pagina,'winLOV','scrollbars=yes,resizable=yes,width=600,height=350,status=yes');
   if (frmLOV.opener == null) {
      frmLOV.opener = self;
   }	
}
	
</script></head>
<link rel = "stylesheet" href = "<%= pathskin %>">
<BODY leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="<%=action%>" name="frm" method="post">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" background="form_empresas_files/tab_shadow.gif">
    <tbody>
       <tr valign="top"> 
        <td class="text-globales" width="39" height="23"></td>
        <td class="text-globales" width="656"> <%=titulo%></td>
        <td class="text-globales" width="290"> <%= accion %></td>
        <td class="text-globales" width="8"></td>
      </tr>
    </tbody>
  </table>
  
  <table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="fila-det">
      <tr>
				<td height="12" class="fila-det-border" >Descripcion&nbsp;(*)</td>
        <td width="88%" class="fila-det-border">
				<input name="descripcion" type="text" class="campo" id="descripcion" size="50" maxlength="50" value="<%=descripcion%>"  <%=readonly%>>
				
				<input type="hidden" name="idempresa" value="<%= new BigDecimal(session.getAttribute("empresa").toString())%>">
				</td>
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
		
  
</table>
		</form>
	</body>
</html>