<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.security.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.naming.directory.*" %>
<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ page import="ar.com.syswarp.validar.*" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="ar.com.syswarp.api.Common"%>
<%
Strings str = new Strings();
String accion = request.getParameter("accion");
String grabacion = request.getParameter("grabacion");
// variables de afuera
String codigo = request.getParameter("codigo");
// variables de formulario
String titulo = "Cuentas contables";


String idcuenta     = str.esNulo( request.getParameter("idcuenta") );
String cuenta       = str.esNulo( request.getParameter("cuenta") );
String imputable    = str.esNulo( request.getParameter("imputable") );
String nivel        = str.esNulo( request.getParameter("nivel") );
String ajustable    = str.esNulo( request.getParameter("ajustable") );
String resultado    = str.esNulo( request.getParameter("resultado") );
String cent_cost    = str.esNulo( request.getParameter("cent_cost") );
String d_cent_cost  = str.esNulo( request.getParameter("d_cent_cost") );
String cent_cost1   = str.esNulo( request.getParameter("cent_cost1") );
String d_cent_cost1 = str.esNulo( request.getParameter("d_cent_cost1") );
String usuario      = session.getAttribute("usuario").toString();

String formulario = "CONTABLEccontables.jsp";
String action = "frmCONTABLEccontables.jsp";

String chk_imputable = "false";			 
String chk_ajustable = "false";
String chk_resultado = "false";
String val_imputable = "N";			 
String val_ajustable = "N";
String val_resultado = "N";


// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
int _nivel      = Integer.valueOf(session.getAttribute("nivelusuario").toString()).intValue();
int ejercicioActivo = Integer.valueOf(session.getAttribute("ejercicioActivo").toString()).intValue();

if( _nivel == 0 || _nivel == -1)
      response.sendRedirect("errorPage.jsp?error=Acceso denegado a esta aplicacion"); 
String readonly = "";
String readonly1 = "";


if(_nivel == 1) readonly = "readonly='true'";



    if (idcuenta==null)  idcuenta="";
	if (cuenta==null) cuenta="";
	if (nivel==null)  nivel="";
	if (cent_cost==null)  cent_cost="";
	if (cent_cost==null)  cent_cost="";
	if (cent_cost1==null)  cent_cost1="";
	if (imputable == null) imputable = "N";
	if (ajustable == null) ajustable = "N";
	if (resultado == null) resultado = "N";
	

// instancio el contable
try{
	Contable contable = Common.getContable();
   }
   catch (Exception ex) {
     java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
     java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
     ex.printStackTrace(pw);
  }  

if(grabacion == null && accion.equalsIgnoreCase("Modificacion") && codigo != null ){
   readonly1 = "readonly='false'";  
   java.util.List cuentas = contable.getCuentasPK(ejercicioActivo, new Long(Long.parseLong(codigo)), new BigDecimal(session.getAttribute("empresa").toString() )); 
   java.util.Iterator iterCuentas   = cuentas.iterator();
	 while(iterCuentas.hasNext()){  
	    String[] sCampos = (String[]) iterCuentas.next(); 
			idcuenta  = sCampos[0];
			cuenta = sCampos[1];
			imputable = sCampos[2];
			nivel = sCampos[3];
			ajustable = sCampos[4];
			resultado = sCampos[5];
			
			String var1 ="";
			String var2 ="";
			
			var1 = sCampos[6];
			var2 = sCampos[7];
		   if (var1 == null){ 
		      cent_cost= "";
		   }else{
		      cent_cost = sCampos[6];
		   }
		   if (var2 == null){ 
		      cent_cost1= "";
		   }else{
		      cent_cost1 = sCampos[7];
		   }
		   
		  if (imputable == null) imputable = "N";
	      if (ajustable == null) ajustable = "N";		
		  if (resultado == null) resultado = "N";
			
			// tengo que recuperar valores para las descripciones de centros de costos.
			 if (cent_cost.toUpperCase() != ""){ 
			    java.util.List cc1 = contable.getCenCostoPK(cent_cost.toUpperCase(), new BigDecimal(session.getAttribute("empresa").toString() ));
			    java.util.Iterator icc1   = cc1.iterator();
			    while(icc1.hasNext()){  
			    String[] sCC = (String[]) icc1.next(); 
					d_cent_cost = sCC[1];
			 }
			 }	
			 if (cent_cost1.toUpperCase() != ""){
			    java.util.List cc2 = contable.getCenCostoPK(cent_cost1.toUpperCase(), new BigDecimal(session.getAttribute("empresa").toString() ));
			    java.util.Iterator icc2   = cc2.iterator();
			    while(icc2.hasNext()){  
			    String[] sCC = (String[]) icc2.next(); 
					d_cent_cost1 = sCC[1];
			 }
			 }	
			 
			 
			 

			 
			 // ahora pongo a tiro los checkbox
			 if (imputable.equalsIgnoreCase("S")) {
			     chk_imputable = "true";
			     val_imputable = "S";
			 }
			 if (ajustable.equalsIgnoreCase("S")) {
			    chk_ajustable = "true";
			    val_ajustable = "S";
			 }
			 if (resultado.equalsIgnoreCase("S")){
			    chk_resultado = "true";
			    val_resultado = "S";
			 }   
	 }	
   
}
/**/

//grabo el Alta  
if(grabacion != null && accion.equalsIgnoreCase("Alta")){ 
    readonly1 = "readonly='true'";  
   	if(imputable.equalsIgnoreCase("on"))  imputable  ="S";
   	if(ajustable.equalsIgnoreCase("on"))   ajustable ="S";
   	if(resultado.equalsIgnoreCase("on"))   resultado ="S";
   	
    String respuesta = contable.CuentaSave(ejercicioActivo, new Long(Long.parseLong(idcuenta)), cuenta.toUpperCase(), imputable.toUpperCase(), new Integer(nivel), ajustable.toUpperCase(), resultado.toUpperCase(), cent_cost.toUpperCase() , cent_cost1.toUpperCase(), usuario, new BigDecimal(session.getAttribute("empresa").toString() ));
   
	if(!respuesta.equalsIgnoreCase("OK")){
	   %><script>alert('<%=respuesta%>');</script><%  	
	}
	else{				   
		 %><script>alert('Se grabo la Cuenta en forma correcta!');</script><%
		  response.sendRedirect(formulario);
	}
}


//grabo la modificacion
if(grabacion != null && accion.equalsIgnoreCase("Modificacion") && codigo != null ){  
	
 	if(imputable.equalsIgnoreCase("on"))  imputable  ="S";
   	if(ajustable.equalsIgnoreCase("on"))   ajustable ="S";
   	if(resultado.equalsIgnoreCase("on"))   resultado ="S";	 
	
	 
				 

	 	 
   String respuesta = contable.CuentaSaveOrUpdate(ejercicioActivo, cuenta.toUpperCase(), imputable.toUpperCase(), new Integer(nivel), ajustable.toUpperCase(), resultado.toUpperCase(), cent_cost.toUpperCase() ,cent_cost1.toUpperCase(), usuario, new Long(Long.parseLong(idcuenta)), new BigDecimal(session.getAttribute("empresa").toString() ));
   
  if(!respuesta.equalsIgnoreCase("OK")){
	 %><script>alert('<%=respuesta%>');</script><%  	
  }
  else{				   
     %><script>alert('Se Modifico la Cuenta en forma correcta!');</script><%
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
   
   <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	 
	<script language="JavaScript" src="scripts/forms/forms.js"></script>
	<script language="JavaScript" src="scripts/overlib/overlib.js"></script>
  <script language="JavaScript">

// generacion de un javascript dinamico a partir del recordset.
function validar(){

 if( !isCharsInBag( document.frm.idcuenta.value.toLowerCase(), '1234567890.' )|| document.frm.idcuenta.value=="" ){
   alert( 'El codigo de cuenta solo admite numéricos y no puede estar vacio.\n 0123456789.' );
   document.frm.idcuenta.focus();
   return false;
 }
 
  if (document.frm.cuenta.value==""){
     alert("no se puede dejar vacia la descripcion de la cuenta");
     document.frm.cuenta.focus();
     return false;
  }
 
  if( !isCharsInBag( document.frm.nivel.value.toLowerCase(), '1234567890.' )|| document.frm.nivel.value=="" ){
   alert( 'El nivel solo admite numéricos y no puede estar vacio.\n 0123456789.' );
   document.frm.nivel.focus();
   return false;
 }

  //if( !isCharsInBag( document.frm.cent_cost.value.toLowerCase(), '1234567890.' )|| document.frm.cent_cost.value=="" ){
   //alert( 'El centros de costos 1 solo admite numericos y no puede estar vacio.\n 0123456789.' );
   //document.frm.cent_cost.focus();
   //return false;
 //}

  //if( !isCharsInBag( document.frm.cent_cost1.value.toLowerCase(), '1234567890.' )|| document.frm.cent_cost1.value=="" ){
   //alert( 'El centros de costos 2 solo admite numericos y no puede estar vacio.\n 0123456789.' );
   //document.frm.cent_cost1.focus();
   //return false;
 //}


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
	
</script>
<link rel = "stylesheet" href = "<%= pathskin %>">
</head>
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
    <tbody>
		
      <tr> 
         <td width="23%" class="fila-det-border">C&oacute;digo &nbsp;(*)</td>
         <td width="77%" class="fila-det-border" >
				 <input name="idcuenta" type="text" class="campo" id="idcuenta" size="18" maxlength="18" value="<%=idcuenta%>"  <%=readonly1%>></td>
	  </tr>
			
      <tr>
        <td height="23" class="fila-det-border">Descripcion&nbsp;(*)</td>
        <td class="fila-det-border" >
				<input name="cuenta" type="text" class="campo" id="cuenta" size="50" maxlength="50" value="<%=cuenta%>"  <%=readonly%>></td>
      </tr>
			
      <tr>
        <td height="23" class="fila-det-border">Nivel&nbsp;(*)</td>
        <td class="fila-det-border" >
				<input name="nivel" type="text" class="campo" id="nivel" size="2" maxlength="2" value="<%=nivel%>"  <%=readonly%>></td>
      </tr>
			
      <tr>
        <td height="23" class="fila-det-border">Inputable&nbsp;(*)</td>				   
        <td class="fila-det-border" >
				<input name="imputable" type="checkbox" id="imputable" 
				<%if(val_imputable.equalsIgnoreCase("S")){%>
			   	    value="<%=val_imputable%>" 
				    checked="<%=chk_imputable%>"  
				<%}%>        
			 <%=readonly%>></td>
      </tr>
			
      <tr>
        <td height="23" class="fila-det-border">Ajustable&nbsp;(*)</td>
        <td class="fila-det-border" >
				<input name="ajustable" type="checkbox" id="ajustable" 
				 <%if(val_ajustable.equalsIgnoreCase("S")){%>
				        value="<%=val_ajustable%>" 
				        checked="<%=chk_ajustable%>"
				 <%}%>       
			    <%=readonly%>></td>
      </tr>
      <tr>
        <td height="23" class="fila-det-border">De Resultado&nbsp;(*)</td>
        <td class="fila-det-border" ><input name="resultado" type="checkbox" id="resultado" 
           <%if(val_resultado.equalsIgnoreCase("S")){%>
             value="<%=val_resultado%>" 
             checked="<%=chk_resultado%>"   
           <%}%>        
           <%=readonly%>></td>
      </tr>
			
      <tr class="fila-det-border">
        <td height="23"class="fila-det-border" >Centro de Costos 1(*)&nbsp;</td>
        <td class="fila-det-border">
		  <table width="23%" border="0">
            <tr class="fila-det-border">
              <td width="61%" >
                <input name="d_cent_cost" type="text" class="campo" id="d_cent_cost" value="<%=d_cent_cost%>" size="30" readonly></td>
              <td width="39%"><img src="imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('lov_centro_de_costos1.jsp')" style="cursor:pointer"></td>
              <input name="cent_cost" type="hidden" id="cent_cost" value="<%=cent_cost%>">
            </tr>
          </table></td>
      </tr>
		
      <tr class="fila-det-border">
        <td height="23" class="fila-det-border">Centro de Costos 2(*)&nbsp;</td>
        <td class="fila-det-border"><table width="23%" border="0">
          <tr class="fila-det-border">
            <td width="61%" >
              <input name="d_cent_cost1" type="text" class="campo" id="d_cent_cost1" value="<%=d_cent_cost1%>" size="30" readonly></td>
            <td width="39%"><img src="imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('lov_centro_de_costos2.jsp')" style="cursor:pointer"></td>
            <input name="cent_cost1" type="hidden" id="cent_cost1" value="<%=cent_cost1%>">
          </tr>
        </table></td>
      </tr>
    </tbody>
  </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="b">
    <tr class="fila-det-verde"> 
      <td height="29" class="fila-det-border">&nbsp;(*) Indica que el campo es obligatorio</td>
    </tr>
  
  </table>
  
<input type="hidden" name="grabacion" value="SI">
<input type="hidden" name="accion" value="<%= accion %>">
<input type="hidden" name="codigo" value="<%= codigo %>">
   
  <table width="100%" height="32" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr class="text-seis">
    <tr class="text-seis">
					<td width="18%">
			<%if(_nivel == 2){%>
          <input type="button" class="boton" name="grabar" value="Aceptar" onClick="validar();">
			<%}%>		
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
</body>
</html>