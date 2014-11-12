<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.Iterator" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ page import="ar.com.syswarp.ejb.*" %> 
<%@ page import="java.math.BigDecimal" %>
<%@ page import="ar.com.syswarp.api.Common"%>
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
String idempresa = str.esNulo( request.getParameter("idempresa")  != null ? request.getParameter("idempresa")  + "" :   session.getAttribute("empresa") + "");;
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

 if( !isCharsInBag( document.frm.remito.value.toLowerCase(), '1234567890.' )|| document.frm.remito.value=="" ){
   alert( 'El campo remito solo admite numéricos y no puede estar vacio.\n 0123456789.' );
   document.frm.remito.focus();
   return false;
 }
 
  if (document.frm.conjunto.value==""){
     alert("Por favor seleccion el conjunto!");
     document.frm.conjunto.focus();
     return false;
  }


  if(!document.frm.confirma[0].checked){
	  if (document.frm.idestado.value=="" || document.frm.idestado.value == "6"){
		 alert("Por favor seleccione estado!");
		 document.frm.estado.focus();
		 return false;
	  }
  }
  else {
    //if(document.frm.idestado.value == "")
      document.frm.estado.value = "";
      document.frm.idestado.value = 6;    
  }

	if ( confirm('Confirma?') ){
	  document.frm.ejecutar.value = "1";
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
	

function limpiarAll(){

  document.frm.remito.value = "";
  document.frm.conjunto.value = "";
  document.frm.d_conjunto.value = "";
  document.frm.estado.value = "";
  document.frm.idestado.value = "";
  
}

	
function mostrarLOV(pagina) {
   frmLOV = open(pagina,'winLOV','scrollbars=yes,resizable=yes,width=600,height=350,status=yes');
   if (frmLOV.opener == null) {
      frmLOV.opener = self;
   }	
}
	
</script>
<link href="../imagenes/default/erp-style.css" rel="stylesheet" type="text/css">
</head>
<BODY leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div> 

<% 
try{ 
    
    BC repo = null;
		System.out.println("RECEPCION: -" + usuarioalt + "- "); 
		if(str.esNulo(usuarioalt).equals("")) response.sendRedirect("caduco.jsp");
    if(ejecutar.equalsIgnoreCase("1")){ 
		if(idempresa.equals("")) idempresa = "1";
		if(usuarioalt.equals("")) usuarioalt = "INTERFAZ";
		else{
     if(usuarioalt.length()>17) usuarioalt = usuarioalt.substring(0, 16); 
		 if (usuarioalt.indexOf(":IF") == -1) usuarioalt += ":IF" ;
		}
		
		BC bc = Common.getBc();
		resultado = bc.InterfaseDbBacoDeltaMovSalida( conjunto, remito,  idestado,  confirma,	 new BigDecimal(idempresa),  usuarioalt);
		for(int j=0;j<3;j++)
		  mensaje += str.esNulo(resultado[j]).equals("") ?  "" : resultado[j] + " <br>";
		
	}		 
 %>

<form action="RecepcionRemitos.jsp" name="frm" method="post"> 
<input name="ejecutar" type="hidden" id="ejecutar" value="0">
<input name="usuario" type="hidden" id="usuario" value="<%= usuarioalt %>">
<input name="idempresa" type="hidden" id="usuario" value="<%= idempresa %>">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" background="form_empresas_files/tab_shadow.gif">
    <tbody>
      <tr valign="top"> 
        <td class="text-globales" width="39" height="23"></td>
        <td class="text-globales" width="656"> <%=titulo%></td> 
        <td class="text-globales" width="8"></td>
      </tr>
    </tbody>
  </table>
  <table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="fila-det">
    <tbody>
		
      <tr>
        <td height="24" class="fila-det-border"><input name="limpiar" type="button" class="boton" id="limpiar" onClick="limpiarAll();" value="Limpiar"></td>
        <td colspan="2" class="fila-det-border" ><div align="left"><span class="fila-det-bold-rojo"><%= mensaje %>&nbsp;</span></div></td>
      </tr>
      <tr> 
         <td width="23%" class="fila-det-border">Remito &nbsp;(*)</td>
      <td colspan="2" class="fila-det-border" >
				 <input name="remito" type="text" class="campo" id="remito" size="18" maxlength="18" value="<%=remito%>"></td>
	  </tr>
			
      <tr>
        <td height="23" class="fila-det-border">Conjunto (*)</td>				   
        <td width="17%" class="fila-det-border" ><input name="d_conjunto" type="text" class="campo" id="d_conjunto" value="<%=d_conjunto%>" size="30" readonly></td>
        <td width="60%" class="fila-det-border" ><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('lov_conjunto.jsp?cmp_codigo=conjunto&cmp_descripcion=d_conjunto')" style="cursor:pointer">
          <input name="conjunto" type="hidden" id="conjunto" value="<%=conjunto%>"></td>
      </tr>
			
      <tr class="fila-det-border">
        <td height="23"class="fila-det-border" >Estados(*)&nbsp;</td>
      <td class="fila-det-border"><input name="estado" type="text" class="campo" id="estado" value="<%=estado%>" size="30" readonly></td>
      <td class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('lov_wapEstados.jsp')" style="cursor:pointer">
        <input name="idestado" type="hidden" id="idestado" value="<%=idestado%>"></td>
      </tr>
		
      <tr class="fila-det-border">
        <td height="23" class="fila-det-border">Operaci&oacute;n(*)&nbsp;</td>
        <td class="fila-det-border">Consultar
        <input name="confirma" type="radio" checked value="null"></td> 
        <td class="fila-det-border">Confirmar
        <input name="confirma" type="radio" value="1"></td>
      </tr>
    </tbody>
  </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="b">
    <tr class="fila-det-verde"> 
      <td height="29" class="fila-det-border">&nbsp;(*) Indica que el campo es obligatorio</td>
    </tr>
  
  </table>   
  <table width="100%" height="32" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr class="text-seis">
    <tr class="text-seis">
					<td width="18%">
          <input type="button" class="boton" name="aceptar" value="Aceptar" onClick="validar();">
        &nbsp;</td>
      <td width="85%"><div align="left">&nbsp; 
            <input type="reset" class="boton" name="Submit2" value="Reset">
            <span class="fila-det-border">            </span></div></td>
    </tr>
  </tbody></table>
  <table width="100%" align="center" cellpadding="1" cellspacing="1" class="color-tabletrim">
    <tbody><tr class="color-tableheader"> 
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
        </tbody></table>
    </tr>
  </tbody></table>
</form>
<% 
}
catch (Exception ex) {
   System.out.println("ERROR - [INTERFACES/RecepcionRemitos]: " + ex);
  
}%>
</body>
</html>       
