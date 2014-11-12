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
<%@ page import="java.sql.*" %>
<%@ page import="java.math.*"%> 
<%@ page import="ar.com.syswarp.api.*" %>    
<%@ include file="session.jspf"%>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "Comprobante de Pagos ";
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
<jsp:useBean id="BCIP"  class="ar.com.syswarp.web.ejb.BeanCajaPagosImpresion"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BCIP" property="*" />
<%
 BCIP.setResponse(response);
 BCIP.setRequest(request);
 BCIP.setSession(session);
 BCIP.setUsuarioalt(usuario);
 BCIP.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BCIP.ejecutarValidacion();
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
Hashtable htMovimientosCancelarOK = (Hashtable) session.getAttribute("htMovimientosCancelarOK");
Hashtable htIdentificaSalidaPagosOK = (Hashtable) session.getAttribute("htIdentificaSalidaPagosOK");
Hashtable htComprobantesProvOK = (Hashtable) session.getAttribute("htComprobantesProvOK");
 
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<div id="popupcalendar" class="text"></div>
<form action="" method="POST" name="frm">
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BCIP" property="mensaje"/></td>
  </tr>
</table>
&nbsp;
<table width="90%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr class="fila-det">
    <td colspan="2" ><%= genNombreEmpresa %>&nbsp;</td>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr class="fila-det">
    <td height="21" colspan="2">Orden de pago N°<%= Common.strZero(BCIP.getNrocomprobante().toString(), 8 )%></td>
    <td colspan="2"><%= BCIP.getFechamov() %></td>
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
    <td width="19%" height="18">Paguese a : </td>
    <td width="28%">&nbsp;<%= BCIP.getRazon() %></td> 
    <td width="13%">Fecha Recibo: </td>
    <td width="40%"><%=  BCIP.getFechamov() %>&nbsp;</td>
  </tr>
  <tr class="fila-det">
    <td height="18">La cantidad de pesos:</td>
    <td height="18">&nbsp;<%= BCIP.getTotalPago() %></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr class="subtitulo">
    <td height="18" colspan="2">Seg&uacute;n el siguiente detalle </td>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr class="fila-det">
    <td height="18" colspan="4">
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		
      <tr class="fila-det" >
        <td width="28%" height="28" > &nbsp;
          Tipo</td>
        <td width="32%" >&nbsp;N&uacute;mero</td>
        <td width="19%" >
          Fecha&nbsp;</td>
        <td width="21%" ><div align="right">Importe</div></td>
      </tr>    		
      <% 
	if(htMovimientosCancelarOK != null && !htMovimientosCancelarOK.isEmpty()){
		Enumeration en = htMovimientosCancelarOK.keys();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			String [] comprobantes = (String []) htMovimientosCancelarOK.get(key);
			String [] comp = key.split("-");
      //for(int z = 0; z<comprobantes.length;z++) System.out.println("MC " + z + ":" + comprobantes[z]);
						  %>
      <tr class="fila-det" >
        <td width="28%" > <%= comprobantes[0] %> - <%= comprobantes[1] %>&nbsp;</td>
        <td width="32%" ><%= comprobantes[0] %></td>
        <td width="19%" ><%=  BCIP.getFechamov() %> &nbsp;</td>
        <td width="21%" ><div align="right"><%= comprobantes[28] %></div></td>
      </tr>
      <% 
		}
	}
	%>
	      <% 
  
	if(htIdentificaSalidaPagosOK != null && !htIdentificaSalidaPagosOK.isEmpty()){
		Enumeration en = htIdentificaSalidaPagosOK.keys();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			String [] identificadores = (String []) htIdentificaSalidaPagosOK.get(key);
			//for(int z = 0; z<identificadores.length;z++) System.out.println("EL " + z + ":" + identificadores[z]);
						  %>
      <tr class="fila-det" >
        <td width="28%"><%= identificadores[0] %> - <%= identificadores[1] %>&nbsp;</td>
        <td width="32%"><%= identificadores[0] %></td>
        <td width="19%"><%=  BCIP.getFechamov() %> </td>
        <td width="21%">
          <div align="right"><%= identificadores[28] %></div></td>
      </tr>
      <% 
					
		}
	}
	%>
	
    </table>
	 </td>
  </tr>
  <tr class="subtitulo">
    <td height="18" colspan="2">En concepto de: </td>
    <td height="18" colspan="2"></td>
  </tr>
  <tr class="fila-det">
    <td height="18" colspan="2">&nbsp;</td>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr class="fila-det">
    <td height="18" colspan="4"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
      <% 

	if(htComprobantesProvOK != null && !htComprobantesProvOK.isEmpty()){
		Enumeration en = htComprobantesProvOK.keys();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			String [] identificadores = (String []) htComprobantesProvOK.get(key);
			//for(int z = 0; z<identificadores.length;z++) System.out.println("CP " + z + ":" + identificadores[z]);
						  %>
      <tr class="fila-det" >
        <td width="28%" > <%= key.equalsIgnoreCase("adelanto") ? "" : identificadores[9] %> &nbsp;</td>
        <td width="32%" ><%= key.equalsIgnoreCase("adelanto") ? key : Common.strZero(identificadores[4], 4)  + " - " + Common.strZero(identificadores[5], 8)%>&nbsp;</td>
        <td width="19%" ><%= !str.esNulo(identificadores[3]).equals("") ? Common.setObjectToStrOrTime (  Timestamp.valueOf(identificadores[3] ), "JSTsToStr") : "" %>&nbsp;</td>
        <td width="21%" >
          <div align="right"><%= identificadores[1] %></div></td>
        </tr>
      <% 
		}
	}
	%>
    </table></td>
    </tr>
  <tr class="fila-det">
    <td height="18" colspan="2">&nbsp;</td>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr class="subtitulo">
    <td height="18" colspan="2">Observaciones</td>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr class="fila-det">
    <td height="18" colspan="4"><%= "" %></td>
    </tr>
  <tr class="fila-det">
    <td height="18" colspan="4">&nbsp;</td>
  </tr>
  <tr class="fila-det">
    <td height="18" colspan="4"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr class="fila-det" >
        <td width="33%" >  &nbsp;
          <div align="center">................................................</div></td>
        <td width="33%" >&nbsp;&nbsp;
          <div align="center">................................................</div></td>
        <td width="33%" >&nbsp;
          <div align="center">................................................</div></td>
        </tr>
      <tr class="fila-det" >
        <td ><div align="center">Confeccion&oacute;</div></td>
        <td ><div align="center">Autoriz&oacute;</div></td>
        <td ><div align="center">Recib&iacute; Conforme </div></td>
      </tr>
    </table></td>
  </tr>
</table>
</form>
  </body>
</html>
<%  
	BCIP.rmSessionHt() ;   
 }
catch (Exception ex) {
	
   java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
   java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
   ex.printStackTrace(pw);
  System.out.println("ERROR (" + pagina + ") : "+ex);   
}

%>


