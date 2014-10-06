<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vlov_Clientes
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Dec 12 14:43:42 GMT-03:00 2006 
   Observaciones: 
      .


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.*" %> 
<%@ page import="java.math.*"%> 
<%@ page import="java.sql.*" %> 
<%@ page import="ar.com.syswarp.api.*" %>    
<%@ include file="session.jspf"%>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "INGRESOS DIRECTOS";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVlov_Clientes   = null;
int totCol = 4; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVCA"  class="ar.com.syswarp.web.ejb.BeanCajaIngresosDirectosImpresion"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVCA" property="*" />
<%
 BVCA.setResponse(response);
 BVCA.setRequest(request);
 BVCA.setSession(session);
 BVCA.setUsuarioalt(usuario);
 BVCA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BVCA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
  
 <link rel="stylesheet" href="<%=pathskin%>">
 <link rel="stylesheet" type="text/css" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script> 
 <script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>

</head>
<%
// titulos para las columnas
tituCol[0] = "Cód."; 
tituCol[1] = "Cliente";   
tituCol[2] = "Cód. Cobrador";
tituCol[3] = "Cobrador";
java.util.List Vlov_Clientes = new java.util.ArrayList();


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

Hashtable htIdentificadoresIngresosOK = (Hashtable) session.getAttribute("htIdentificadoresIngresosOK");
Hashtable htIdentificadoresContrapartidaOK = (Hashtable) session.getAttribute("htIdentificadoresContrapartidaOK"); 
Hashtable htMovimientosCancelarOK = (Hashtable) session.getAttribute("htMovimientosCancelarOK");

%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<div id="popupcalendar" class="text"></div>
<form action="CajaIngresosDirectos.jsp" method="POST" name="frm"> 
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr >
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td width="100%"  ></td>
                </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="90%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVCA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVCA" property="mensaje"/></td>
  </tr>
</table>
&nbsp;
<table width="90%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr class="fila-det">
    <td colspan="2" ><%= genNombreEmpresa %>&nbsp;</td>
    <td colspan="2"><%= genCUITEmpresa %></td>
  </tr>
  <tr class="fila-det">
    <td height="21" colspan="2"><%= genDomicilioLegalEmpresa %></td>
    <td colspan="2"><%= genCondicionFiscalEmpresa %></td>
  </tr>
  <tr class="fila-det">
    <td height="18" colspan="2"><%= genLocalidadEmpresa %> - <%= genProvinciaEmpresa %></td>
    <td colspan="2"><%= genTelefonosEmpresa %>&nbsp;</td>
  </tr>
  <tr class="fila-det">
    <td height="18" colspan="2">&nbsp;</td>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr class="subtitulo">
    <td height="18" colspan="2">&nbsp;</td>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr class="fila-det">
    <td width="9%" height="18">Recibi de: </td>
    <td width="33%">&nbsp;</td>
    <td width="14%">Recibo N&deg;:</td>
    <td width="44%"><%= Common.strZero(BVCA.getNrocomprobante().toString(), 8 )%>&nbsp;</td>
  </tr>
  <tr class="fila-det">
    <td height="18">Cuit:</td>
    <td height="18">&nbsp;</td>
    <td>Fecha Recibo: </td>
    <td>&nbsp;</td>
  </tr>
  <tr class="subtitulo">
    <td height="18" colspan="2">&nbsp;</td>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr class="fila-det">
    <td height="18" colspan="2">Son Pesos: </td>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr class="fila-det">
    <td height="18" colspan="2">Son Pesos: <%= BVCA.getTotalCobranza() %></td>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr class="fila-det">
    <td height="18" colspan="2">Valor Dolar: </td>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr class="subtitulo">
    <td height="18" colspan="2">Liquidacion de Pagos </td>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr class="fila-det">
    <td height="18" colspan="2">&nbsp;</td>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr class="fila-det">
    <td height="18" colspan="4">
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <% 
	if(htIdentificadoresIngresosOK != null && !htIdentificadoresIngresosOK.isEmpty()){
		Enumeration en = htIdentificadoresIngresosOK.keys();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			String [] identificadores = (String []) htIdentificadoresIngresosOK.get(key);
			//for(int f=0;f<identificadores.length;f++) System.out.println("POS: " + f + " - (" + identificadores[f]);
						  %>
        <tr class="fila-det" >
          <td width="44%" > <%= identificadores[2] %> -  <%= identificadores[1] %>&nbsp;</td>
          <td width="32%" > <%= identificadores[31].equals("") ? "" : Common.setObjectToStrOrTime( Timestamp.valueOf(identificadores[31]), "JSTsToStr") %>&nbsp;</td>
          <td width="24%" >
            <div align="right"><%= identificadores[28] %>&nbsp;</div></td>
        </tr>
        <% 
		}
	}
	%>

    </table></td>
  </tr>
  <tr class="subtitulo">
    <td height="18" colspan="2">Liquidado de la Siguiente Forma </td>
    <td height="18" colspan="2"></td>
  </tr>
  <tr class="fila-det">
    <td height="18" colspan="2">&nbsp;</td>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr class="fila-det">
    <td height="18" colspan="4"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <% 

	if(htIdentificadoresContrapartidaOK != null && !htIdentificadoresContrapartidaOK.isEmpty()){
		Enumeration en = htIdentificadoresContrapartidaOK.keys();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement(); 
			String [] identificadores = (String []) htIdentificadoresContrapartidaOK.get(key);
			//for(int f=0;f<identificadores.length;f++) System.out.println("POS: " + f + " - ->" + identificadores[f]);

						  %>
        <tr class="fila-det" >
          <td width="34%" > <%= identificadores[2]%> - <%= identificadores[3]%> &nbsp;</td>
          <td width="13%" ><%= identificadores[30] %>&nbsp;</td>
          <td width="18%" ><%= identificadores[31] %>&nbsp;</td>
          <td width="9%" >&nbsp;</td>
          <td width="26%" ><div align="right"><%= identificadores[28] %>&nbsp;</div></td>
        </tr>
        <% 
		}
	}
	%>
	
	        <% 

	if(htMovimientosCancelarOK != null && !htMovimientosCancelarOK.isEmpty()){ 
		Enumeration en = htMovimientosCancelarOK.keys();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			String [] identificadores = (String []) htMovimientosCancelarOK.get(key);
			//for(int f=0;f<identificadores.length;f++) System.out.println("POS: " + f + "  -{" + identificadores[f]);

						  %>
        <tr class="fila-det" >
          <td width="34%" > <%= identificadores[2]%> - <%= identificadores[1]%>&nbsp;</td>
          <td width="13%" ><%= identificadores[30] %>&nbsp;</td>
          <td width="18%" ><%= identificadores[31] %>&nbsp;</td>
          <td width="9%" >&nbsp;</td>
          <td width="26%" ><div align="right"><%= identificadores[28] %>&nbsp;</div></td>
        </tr>
        <% 
		}
	}
	%>
    </table>
		
		</td>
  </tr>
  <tr class="fila-det">
    <td height="18" colspan="2">&nbsp;</td>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr class="fila-det">
    <td height="18" colspan="2">&nbsp;</td>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr class="fila-det">
    <td height="18" colspan="2">Firma .............................................................. </td>
    <td colspan="2">&nbsp;</td>
  </tr>
</table>
<p>&nbsp; </p>

  <input name="nrocomprobante" type="hidden" id="nrocomprobante" value="<%= BVCA.getNrocomprobante() %>">
</form>
  </body>
</html>
<% 
	BVCA.rmSessionHt() ;
 }
catch (Exception ex) {

   java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
   java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
   ex.printStackTrace(pw);
  System.out.println("ERROR (" + pagina + ") : "+ex);   
}

%>


