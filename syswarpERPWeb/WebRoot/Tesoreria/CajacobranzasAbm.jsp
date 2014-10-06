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
<%@ page import="ar.com.syswarp.api.*" %>    
<%@ include file="session.jspf"%>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "COBRANZAS";
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
<jsp:useBean id="BVCA"  class="ar.com.syswarp.web.ejb.BeanCajaCobranzasAbm"   scope="page"/>
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
 <script>
 function omitirCobrador(){
   document.frm.idcobrador.value = document.frm.cobrador.value = ""; 
 }
 
 function seleccionarCliente(){
   abrirVentana('vlov_ClientesAbm.jsp', 'buscar', 700, 400); 
 }
 
 function cambiarCliente(){
   if(confirm("Al cambiar de cliente, perderá toda la información cargada.\n Continúa de todas formas?")){
	   seleccionarCliente();
     window.location = "CajacobranzasAbm.jsp";
	 }
 }
 
 function generarCobranza(){
 
   document.frm.generar.disabled = true;
   document.frm.generar.value = 'Procesando ...';
   document.frm.confirmar.value = 'confirmar';
   document.frm.submit();
 
 }
  
 </script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Cód."; 
tituCol[1] = "Cliente";   
tituCol[2] = "Cód. Cobrador";
tituCol[3] = "Cobrador";
java.util.List Vlov_Clientes = new java.util.ArrayList();
Hashtable htComprobantesOK = (Hashtable) session.getAttribute("htComprobantesOK");
Hashtable htIdentificadoresOK = (Hashtable) session.getAttribute("htIdentificadoresOK");
Hashtable htIdentificadoresIngresosOK = (Hashtable) session.getAttribute("htIdentificadoresIngresosOK");
 
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<div id="popupcalendar" class="text"></div>
<form action="CajacobranzasAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td width="100%"  class="text-globales"><%=titulo%></td>
                </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVCA" property="mensaje"/></td>
  </tr>
</table>
&nbsp;
<table width="98%"  border="1" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
		 <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0"  >
      <tr class="text-dos" height="3" >
        <td height="3" colspan="2" ></td>
      </tr>
      <tr class="text-globales">
        <td width="9%" class="fila-titulo-dos">Cliente</td>
        <td width="91%" > 
			<%if(htComprobantesOK == null || htComprobantesOK.isEmpty() ) {%>
				  <input name="btnbuscacliente" type="button" class="boton" id="btnbuscacliente" style="cursor:pointer" onClick="seleccionarCliente();" value=">>">
			<%}
				else{%>				
				  <input name="btncambiacliente" type="button" class="boton" id="btncambiacliente" style="cursor:pointer" onClick="cambiarCliente();" value="Cambiar">
			<%}%>				</td>
      </tr>
      <tr >
        <td width="9%" class="fila-titulo-dos"><input name="idcliente" type="text" class="campo" id="idcliente" size="10" value="<%= str.esNulo(BVCA.getIdcliente() + "") %>" readonly></td>
        <td width="91%" ><span class="fila-titulo-dos">
          <input name="razon" type="text" class="campo" id="razon" size="80" value="<%= BVCA.getRazon() %>" readonly>
        </span></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" >
			<tr class="text-dos" height="3" >
				<td height="3" colspan="3" ></td>
			</tr>
      <tr class="text-globales">
        <td width="9%" class="fila-titulo-dos">Cobrador</td>
        <td width="58%" >
				 <input name="btnbuscacobrador" type="button" class="boton" id="btnbuscacobrador" style="cursor:pointer" onClick="abrirVentana('lov_clientesCobradoresAbm.jsp', 'buscar', 700, 400)" value=">>">
				 <input name="btnomitecobrador" type="button" class="boton" id="btnomitecobrador" style="cursor:pointer" onClick="omitirCobrador()" value="Ninguno">
		    </td>
        <td width="33%" >&nbsp;</td>
      </tr>
      <tr >
        <td width="9%" class="fila-titulo-dos"><input name="idcobrador" type="text" class="campo" id="idcobrador" size="10" value="<%= str.esNulo(BVCA.getIdcobrador() + "") %>" readonly></td>
        <td colspan="2" ><span class="fila-titulo-dos">
          <input name="cobrador" type="text" class="campo" id="cobrador" size="80" value="<%= BVCA.getCobrador() %>" readonly>
        </span></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0"   >
			<tr class="text-dos" height="3" >
				<td height="3" colspan="2" ></td>
			</tr>		
      <tr class="text-globales">
        <td width="22%" class="fila-titulo-dos">Comprobantes a Aplicar </td>
        <td width="78%" class="fila-titulo-dos">
				<!--img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" style="cursor:pointer" onClick="abrirVentana('lov_clientesMovCliAbm.jsp?cliente=' + document.frm.idcliente.value + '&razon=' + document.frm.razon.value, 'comprobantes', 700, 400)"-->
				<input name="btncomprobantes" type="button" class="boton" id="btncomprobantes" style="cursor:pointer" onClick="abrirVentana('lov_clientesMovCliAbm.jsp?cliente=' + document.frm.idcliente.value + '&razon=' + document.frm.razon.value, 'buscar', 700, 400)" value=">>"></td>
      </tr>
      <tr >
        <td colspan="2" class="fila-titulo-dos">
				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr >  
            <td width="25%" class="fila-titulo-dos">N&deg; Interno </td>
            <td width="25%" class="fila-titulo-dos">Comprobante</td>
            <td width="24%" class="fila-titulo-dos"><div align="right">Importe Original </div></td>
            <td width="26%" class="fila-titulo-dos"><div align="right">A Cobrar </div></td>
          </tr>
          <tr height="3">  
            <td colspan="4" class="text-globales" height="3"> </td>
          </tr>					
					
 <% 
  
	if(htComprobantesOK != null && !htComprobantesOK.isEmpty()){
		Enumeration en = htComprobantesOK.keys();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			String [] comprobantes = (String []) htComprobantesOK.get(key);
			String [] comp = key.split("-");
						  %>		
          <tr class="fila-det" >
            <td width="25%" class="fila-det-border"> <%= comprobantes[0] %>&nbsp;</td>
            <td width="25%" class="fila-det-border"> <%= key.equalsIgnoreCase("ADELANTO") ? key :comp[2] + "-" + Common.strZero( comp[0], 4) + "-" + Common.strZero( comp[1], 8)  %>&nbsp;</td>
            <td width="24%" class="fila-det-border">
              <div align="right"><%= comprobantes[2] %>&nbsp;</div></td>
            <td width="26%" class="fila-det-border">
              <div align="right"><%= comprobantes[1] %>&nbsp;</div></td>
          </tr>
	<% 
		}
	}
	%> 				
				
				</table>
				
			</td>
        </tr>
    </table></td>
  </tr>
  <tr>
    <td>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0"   >
			<tr class="text-dos" height="3" >
				<td height="3" colspan="2" ></td>
			</tr>		
      <tr class="text-globales">
        <td width="22%" class="fila-titulo-dos">Descuentos Concedidos</td>
        <td width="78%" class="fila-titulo-dos">
          <!--img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" style="cursor:pointer" onClick="abrirVentana('lov_clientesMovCliAbm.jsp?cliente=' + document.frm.idcliente.value + '&razon=' + document.frm.razon.value, 'comprobantes', 700, 400)"-->
          <input name="btndescuentos" type="button" class="boton" id="btndescuentos" style="cursor:pointer" onClick="abrirVentana('lov_cajaIdentificadoresAbm.jsp?tipomov=X&propio=N', 'buscar', 700, 400)" value=">>"></td>
      </tr>
      <tr >
        <td colspan="2" class="fila-titulo-dos">
          <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr >
              <td width="64%" class="fila-titulo-dos">Cartera</td>
              <td width="36%" class="fila-titulo-dos"><div align="right">Importe</div></td>
              </tr>
            <tr height="3">
              <td colspan="2" class="text-globales" height="3"> </td>
            </tr>
            <% 
  
	if(htIdentificadoresOK != null && !htIdentificadoresOK.isEmpty()){
		Enumeration en = htIdentificadoresOK.keys();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			String [] identificadores = (String []) htIdentificadoresOK.get(key);
			
			for(int j = 0 ;j<identificadores.length;j++) System.out.println("Posicion [ " + j + "] : " +  identificadores[j]);
						  %>
            <tr class="fila-det" >
              <td width="64%" class="fila-det-border"> <%= identificadores[0] %> - <%= identificadores[1] %>&nbsp;</td>
              <td width="36%" class="fila-det-border">
                <div align="right"><%= identificadores[8] %>&nbsp;</div></td>
              </tr>
            <% 
					
		}
	}
	%>
        </table></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" >
			<tr class="text-dos" height="3" >
				<td height="3" colspan="2" ></td>
			</tr>		
      <tr class="text-globales">
        <td width="54%" class="fila-titulo-dos" >Fecha Real de Cobro </td>
        </tr>
      <tr class="fila-det" >
        <td width="54%" height="29" class="fila-det-border"><span >
          <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fechamov" value="<%//=BCF.getFecha_ferStr()%>" maxlength="12">
          <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date', 'img_Date_DOWN');showCalendar('frm','fechamov','BTN_date');return false;"> 
							<img align="absmiddle" border="0" name="BTN_date" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a></span>
			</td>
        </tr>
    </table></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0"   >
			<tr class="text-dos" height="3" >
				<td height="3" colspan="2" ></td>
			</tr>		
      <tr class="text-globales">
        <td width="22%" class="fila-titulo-dos">Detalle de Ingresos</td>
        <td width="78%" class="fila-titulo-dos">
          <!--img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" style="cursor:pointer" onClick="abrirVentana('lov_clientesMovCliAbm.jsp?cliente=' + document.frm.idcliente.value + '&razon=' + document.frm.razon.value, 'comprobantes', 700, 400)"-->
          <input name="btndescuentos2" type="button" class="boton" id="btndescuentos2" style="cursor:pointer" onClick="abrirVentana('lov_cajaIdentificadoresPropioAbm.jsp?propio=N', 'descuentos', 800, 400)" value=">>"></td>
      </tr>
      <tr >
        <td colspan="2" class="fila-titulo-dos">
          <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr >
              <td width="10%" class="fila-titulo-dos">Ide.</td>
              <td width="9%" class="fila-titulo-dos">CC1</td>
              <td width="8%" class="fila-titulo-dos">CC2</td>
              <td width="11%" class="fila-titulo-dos">N&uacute;mero</td>
              <td width="37%" class="fila-titulo-dos">Detalle</td>
              <td width="13%" class="fila-titulo-dos">Fecha</td>
              <td width="12%" class="fila-titulo-dos"><div align="right">Importe</div></td>
            </tr>
            <tr height="3">
              <td colspan="9" class="text-globales" height="3"> </td>
            </tr>
            <% 

	if(htIdentificadoresIngresosOK != null && !htIdentificadoresIngresosOK.isEmpty()){
		Enumeration en = htIdentificadoresIngresosOK.keys();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			String [] identificadores = (String []) htIdentificadoresIngresosOK.get(key);
			for(int m=0;m<identificadores.length;m++) System.out.println("-{identificadores[" + m + "]: " + identificadores[ m ]);  
						  %>
            <tr class="fila-det" >
              <td width="10%" height="32" class="fila-det-border"> <%= key %> &nbsp;</td>
              <td width="9%" class="fila-det-border"><%= identificadores[20] %>&nbsp; </td>
              <td width="8%" class="fila-det-border"><%= identificadores[21] %>&nbsp;</td>
              <td width="11%" class="fila-det-border"><%= identificadores[30] %>&nbsp;</td>
              <td width="37%" class="fila-det-border"><%= identificadores[31] %>&nbsp;</td>
              <td width="13%" class="fila-det-border">&nbsp;</td>
              <td width="12%" class="fila-det-border">&nbsp;<div align="right"><%= identificadores[28] %></div></td>
            </tr>
            <% 
		}
	}
	%>
        </table></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" >
			<tr class="text-dos" height="3" >
				<td height="3"  ></td>
			</tr>		
		
      <tr class="text-globales">
        <td width="54%" class="fila-titulo-dos" >Observaciones</td>
      </tr>
      <tr class="fila-det" >
        <td width="54%" height="29" class="fila-det-border">
				<textarea name="observaciones" cols="80" rows="2" class="campo" ><%= BVCA.getObservaciones() %></textarea> </td>
      </tr>
    </table></td>
  </tr>
	  <tr>
    <td><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" >
      <tr class="text-dos">
        <td width="91%" >TOTALES</td>
        <td width="9%" >&nbsp;</td>
      </tr>
      <tr class="fila-det" >
        <td width="91%" class="fila-det-border">Total Comprobantes</td>
        <td width="9%"  class="fila-det-border"><div align="right"> <%= BVCA.getTotalComprobantes() %></div></td>
      </tr>
      <tr class="fila-det" >
        <td class="fila-det-border">Total Descuentos </td>
        <td class="fila-det-border"><div align="right"> <%= BVCA.getTotalDescuentos() %></div></td>
      </tr>
      <tr class="fila-det" >
        <td class="fila-det-border">Total Ingresos </td>
        <td class="fila-det-border"><div align="right">  <%= BVCA.getTotalIngresos() %></div></td>
      </tr>
      <tr class="fila-det" >
        <td class="fila-det-border">Total Cobranza </td>
        <td class="fila-det-border"><div align="right"> <%= BVCA.getTotalCobranza() %></div></td>
      </tr>
    </table></td>
  </tr>
	
  <tr>
    <td height="33"><span class="fila-titulo-dos">
      <input name="generar" type="button" class="boton" id="generar" style="cursor:pointer" value="Confirmar Cobranza" onClick="generarCobranza();">
    </span></td>
  </tr>
</table>
<p>&nbsp; </p>
<input name="accion" value="" type="hidden">
<input name="confirmar" value="" type="hidden">
 <input name="primerCarga" value="false" type="hidden">
</form>
  </body>
</html>
<% 
 }
catch (Exception ex) {
   java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
   java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
   ex.printStackTrace(pw);
  System.out.println("ERROR (" + pagina + ") : "+ex);   
}%>

