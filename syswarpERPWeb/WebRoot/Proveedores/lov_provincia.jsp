<%@page import="javax.servlet.http.*"%>
<%@page import="java.security.*"%>
<%@page import="javax.naming.*"%>
<%@page import="javax.servlet.http.*"%>
<%@page import="java.security.*"%>
<%@page import="javax.naming.*"%>
<%@page import="javax.naming.directory.*"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.math.*" %>
<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ page import="ar.com.syswarp.api.Common"%>

<%		
String idprovincia   = request.getParameter("idprovincia");
String d_idprovincia   = request.getParameter("d_idprovincia");
String pathskin = "../imagenes/default/"; // cambiar
String color_fondo ="";	

if (idprovincia == null) idprovincia = "";
if (idprovincia.compareTo("0") == 0){
    idprovincia = "";
}     
%>



<html>
<head>
<title>B&uacute;squeda de provincias</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="scripts/forms/forms.js"></script>
<script language="JavaScript" src="scripts/overlib/overlib.js"></script>
<script language="JavaScript">


function validarCampos() {
	if (document.forms[0].idprovincia.value == "") {
		alert("Debe ingresarse una provincia a buscar");	
		return false;
	}
	if (document.forms[0].idprovincia.value != "") {
	}
	document.forms[0].submit();
}

function passBack(idprovincia, d_idprovincia) {
  var i = 0;
   for(i=0;i<10;i++)
     if(opener.document.forms[i].idprovincia)
       break;    
   opener.document.forms[i].idprovincia.value = idprovincia;
   opener.document.forms[i].d_idprovincia.value = d_idprovincia;
   close();
}

function Find_OnClick() {
   document.forms[0].submit();
}
</script>




<link rel="stylesheet" href="jmc.css" type="text/css">
<link href="../imagenes/default/erp-style.css" rel="stylesheet" type="text/css">
</head>

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0">

<form action="lov_provincia.jsp">

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0">
  <p align="left" class=text-seis>B&uacute;squeda de Provincia</p>
<hr>

 

  <table width="59%" border="0" cellspacing="0" cellpadding="0" height="23">
    <tr> 
		  <td height="39" width="26%"> 
        <div class=text-seis>Provincia</div>
      </td>
      <td width="52%" height="39"> 
			  <input name="idprovincia" type="text"  value="<%=idprovincia%>" size="25">
      </td>
      <td height="39" width="3%"></td>
      <td height="39" width="19%"> 
        <input type="button" name="buscar" value="Buscar" onClick="validarCampos()">
      </td>
    </tr>
  </table>
	
	
<%  
if (!idprovincia.equals("")) {
   java.util.List Prov   =  new ArrayList();
   Iterator iterProvincia=null;
   int totReg  = 0;
   try {
   		Proveedores proveedores = Common.getProveedores();
		if (!idprovincia.equals("")) {    
	      Prov =  proveedores.getProveedoProvinciasOcuLOV(idprovincia);
      	iterProvincia = Prov.iterator();      
   	    totReg = Prov.size();
		}		     
   }
   catch (Exception ex) {
     java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
     java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
     ex.printStackTrace(pw);
  }  


	boolean existenReg = false;
	boolean esPrimero = true;
	while ( iterProvincia.hasNext() ) {
  if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
  else color_fondo = "fila-det-verde"; 
   		String[] sCampos = (String[]) iterProvincia.next(); 
   		String cmp_codigo = sCampos[0] ;
   		String cmp_descripcion = sCampos[1] ;
   		existenReg = true;
		if (esPrimero) {
			esPrimero = false; %>
  <table width="100%"  cellPadding=1 cellSpacing=1 class=color-tabletrim >
    <tr class=color-columnheaders> 
      <td class="fila-encabezado">
      <td width="9%" height="13" class="fila-encabezado">Codigo</font></td>
      <td width="88%" height="13" class="fila-encabezado">Provincia</td>
    </tr>
    <% } %>
    	<tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td width="3%" class="fila-det-border" ><a href="javascript:passBack('<%=cmp_codigo%>', '<%=cmp_descripcion%>')"><img src="../imagenes/default/lupa.gif" width="21" height="17" border="0"></a></td>
      <td width="9%" class="fila-det-border" ><%=cmp_codigo%>&nbsp;</td>
      <td width="88%" class="fila-det-border" ><%=cmp_descripcion%>&nbsp;</td>    </tr>
    <%	}
    if (!existenReg) { %>
    <p class=error>No existen registros para la Búsqueda</p>
    <%	}
			else { %>
  </table>
		<%	} 
	
		}

	%>
	<input type="hidden" name="idprovincia" value="<%=idprovincia%>">
    
</form>

</body>
</html>