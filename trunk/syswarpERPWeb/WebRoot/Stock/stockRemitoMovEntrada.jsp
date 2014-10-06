<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: proveedoArticulos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Aug 02 14:39:14 GMT-03:00 2006 
   Observaciones: 
      .
*/ 
%>
<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.*" %> 
<%@ page import="java.math.*"%>
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterProveedoArticulos   = null;
int totCol = 1; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
String genNombreEmpresa = session.getAttribute("genNombreEmpresa").toString();
String genCondicionFiscalEmpresa = session.getAttribute("genCondicionFiscalEmpresa").toString();
String genCUITEmpresa = session.getAttribute("genCUITEmpresa").toString();
String genDomicilioLegalEmpresa = session.getAttribute("genDomicilioLegalEmpresa").toString();
String genClaveDNRPEmpresa = session.getAttribute("genClaveDNRPEmpresa").toString();
String genLocalidadEmpresa = session.getAttribute("genLocalidadEmpresa").toString();
String genProvinciaEmpresa = session.getAttribute("genProvinciaEmpresa").toString();
String genNombreCompletoEmpresa = session.getAttribute("genNombreCompletoEmpresa").toString();
String genActividadEmpresa = session.getAttribute("genActividadEmpresa").toString();
String genTelefonosEmpresa = session.getAttribute("genTelefonosEmpresa").toString();
%>
<html>
<head>
<title>REMITO INTERNO</title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 <link rel="stylesheet" type="text/css" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>
 <jsp:useBean id="BSMER "  class="ar.com.syswarp.web.ejb.BeanStockMovEntradaRemito"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<!--jsp:setProperty name="BSMER" property="*" /-->
</head>
<%
BSMER.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
tituCol[0] = "";
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="popupcalendar" class="text"></div>
<form action="" method="POST" name="frm">
&nbsp;
<table width="90%" height="426"  border="0" align="center" cellpadding="0" cellspacing="0">
	<tr class="fila-det">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
	<tr class="fila-det">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
	<tr class="fila-det">
    <td width="35%" height="27" ><%= genNombreEmpresa %>&nbsp;</td>
    <td width="65%">FECHA : <%= str.esNulo(request.getParameter("fechamov")) %> </td>
  </tr>		
  <tr class="fila-det">
    <td height="25">&nbsp;</td>
    <td>Movimiento de Entrada N&ordm; :  <%= str.esNulo(request.getParameter("nint_ms_an")) %></td>
  </tr>

  <tr class="fila-det">
    <td height="21">&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr class="fila-det">
    <td height="19">&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr class="fila-det">
    <td height="19">&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr class="fila-det">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr class="fila-det">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr class="fila-det">
    <td colspan="2">
		  <table width="90%"  border="0" align="center" cellpadding="0" cellspacing="0">
				<tr class="fila-det">
					<td width="16%">Articulo</td>
					<td width="50%">Descripci&oacute;n</td>
					<td width="11%">Cantidad</td>
					<td width="9%">Dep&oacute;sito</td>
				</tr>
				<% 
			 Hashtable htArticulosInOutOK = (Hashtable) session.getAttribute("htArticulosInOutOK");
			 if(htArticulosInOutOK != null && !htArticulosInOutOK.isEmpty());
			 Vector vecSort = new Vector(htArticulosInOutOK.keySet());
			 Collections.sort(vecSort);
			 Enumeration en = vecSort.elements();
				while(en.hasMoreElements()){
					String key =  (String )en.nextElement() ;
					String[] datos =  (String[] )htArticulosInOutOK.get(key);
					//for(int m=0;m<datos.length;m++) System.out.println("POSICION " + m +": " + datos[m]);
			 %>
				<tr class="fila-det">
					<td height="34"><%= datos[0] %></td>
					<td>&nbsp;<%= datos[2] %> </td>
					<td>&nbsp;<%= datos[10] %>  </td>
					<td>&nbsp;<%= datos[9] %> </td>   
				</tr>
			 <%   
				}
				%>
      </table>
		</td>
    </tr>
  <tr class="fila-det">
    <td colspan="2"><table width="90%"  border="0" align="center" cellpadding="0" cellspacing="0">
      <tr class="fila-det">
        <td width="15%">&nbsp;</td>
        <td width="85%">&nbsp;</td>
        </tr>

      <tr class="fila-det">
        <td height="34">Observaciones</td>
        <td>&nbsp;<%= "" %></td>
        </tr>
    </table></td>
    </tr>
  <tr class="fila-det">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr class="fila-det">
    <td>&nbsp;</td>
    <td><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr class="fila-det">
        <td width="22%">Autoriz&oacute;</td>
        <td width="78%">........................................ </td>
      </tr>
    </table></td>
  </tr>
  <tr class="fila-det">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>	
  <tr class="fila-det">
    <td>&nbsp;</td>
    <td>
		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr class="fila-det">
        <td width="22%">Firma</td>
        <td width="78%">........................................ </td>
      </tr>
    </table></td>
  </tr>		
  <tr class="fila-det">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr class="fila-det">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr class="fila-det">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>			
</table>
</form>
</body>
</html>
<% 
session.removeAttribute("htArticulosMovimiento");

 }
catch (Exception ex) {
   java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
   java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
   ex.printStackTrace(pw);
  System.out.println("ERROR (" + pagina + ") : "+ex);    
}%>
