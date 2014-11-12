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
<jsp:useBean id="BVCA"  class="ar.com.syswarp.web.ejb.BeanCajaCobranzasIngresoDirectoAbm"   scope="page"/>
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
 </script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Cód."; 
tituCol[1] = "Cliente";   
tituCol[2] = "Cód. Cobrador";
tituCol[3] = "Cobrador";
java.util.List Vlov_Clientes = new java.util.ArrayList();

Hashtable htIdentificadoresIngresosOK = (Hashtable) session.getAttribute("htIdentificadoresIngresosOK");
Hashtable htIdentificadoresContrapartidaOK = (Hashtable) session.getAttribute("htIdentificadoresContrapartidaOK"); 
Hashtable htMovimientosCancelarOK = (Hashtable) session.getAttribute("htMovimientosCancelarOK");

%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<div id="popupcalendar" class="text"></div>
<form action="CajaIngresosDirectos.jsp" method="POST" name="frm"> 
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
    <td><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0"   >
        <tr class="text-dos" height="3" >
          <td height="3" colspan="2" ></td>
        </tr>
        <tr class="text-globales">
          <td width="22%" class="fila-titulo-dos">Detalle de Ingresos</td>
          <td width="78%" class="fila-titulo-dos">
            <!--img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" style="cursor:pointer" onClick="abrirVentana('lov_clientesMovCliAbm.jsp?cliente=' + document.frm.idcliente.value + '&razon=' + document.frm.razon.value, 'comprobantes', 700, 400)"-->
            <input name="btndescuentos2" type="button" class="boton" id="btndescuentos2" style="cursor:pointer" onClick="abrirVentana('lov_cajaIdentificadoresPropioAbm.jsp?propio=N', 'descuentos', 800, 600)" value=">>"></td>
        </tr>
        <tr >
          <td colspan="2" class="fila-titulo-dos">
            <table width="100%" border="1" align="center" cellpadding="0" cellspacing="0">
              <tr >
                <td width="13%" class="fila-titulo-dos">Ide.</td>
                <td width="7%" class="fila-titulo-dos">N&uacute;mero</td>
                <td width="30%" class="fila-titulo-dos">Detalle</td>
                <td width="32%" class="fila-titulo-dos">Cuenta</td>
                <td width="10%" class="fila-titulo-dos">Fecha</td>
                <td width="8%" class="fila-titulo-dos"><div align="right">Importe</div></td>
              </tr>
              <tr height="3">
                <td colspan="6" class="text-globales" height="3"> </td>
              </tr>
              <% 

	if(htIdentificadoresIngresosOK != null && !htIdentificadoresIngresosOK.isEmpty()){
		Enumeration en = htIdentificadoresIngresosOK.keys();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			String [] identificadores = (String []) htIdentificadoresIngresosOK.get(key);
			String nro_cuenta = (identificadores[4] + "-" + identificadores[34]);	
			//for(int m=0;m<identificadores.length;m++)
			//  System.out.println(" ~( IDENTIFICADORES[" + m + "]: " + identificadores[m]);		
						  %>
              <tr class="fila-det" >
                <td width="13%" height="32" class="fila-det-border"> <%= identificadores[2] %> - <%= identificadores[1] %>  </td>
                <td width="7%" class="fila-det-border"><%= identificadores[30] %>&nbsp; </td>
                <td width="30%" class="fila-det-border"><%= identificadores[31] %>&nbsp;</td>
                <td width="32%" class="fila-det-border"><%= nro_cuenta.length() > 50 ? nro_cuenta.substring(0, 50  ) : nro_cuenta  %></td>
                <td width="10%" class="fila-det-border">&nbsp;</td>
                <td width="8%" class="fila-det-border">&nbsp;
                    <div align="right"><%= identificadores[28] %></div></td>
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
    <td>&nbsp;		</td>
  </tr>
  <tr>
    <td>
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0"   >
        <tr class="text-dos" height="3" >
          <td height="3" colspan="2" ></td>
        </tr>
        <tr class="text-globales">
          <td width="22%" class="fila-titulo-dos">Contrapartida</td>
          <td width="78%" class="fila-titulo-dos">
            <!--img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" style="cursor:pointer" onClick="abrirVentana('lov_clientesMovCliAbm.jsp?cliente=' + document.frm.idcliente.value + '&razon=' + document.frm.razon.value, 'comprobantes', 700, 400)"-->
            <input name="btncontrapartida" type="button" class="boton" id="btncontrapartida" style="cursor:pointer" onClick="abrirVentana('lov_cajaIdentificadoresContrapartidaAbm.jsp?propio=N', 'descuentos', 800, 400)" value=">>">
          </td>
        </tr>
        <tr >
          <td colspan="2" class="fila-titulo-dos">
            <table width="100%" border="1" align="center" cellpadding="0" cellspacing="0">
              <tr >
                <td width="13%" class="fila-titulo-dos">Ide.</td>
                <td width="7%" class="fila-titulo-dos">N&uacute;mero</td>
                <td width="30%" class="fila-titulo-dos">Detalle</td>
                <td width="32%" class="fila-titulo-dos">Cuenta</td>
                <td width="10%" class="fila-titulo-dos">Fecha</td>
                <td width="8%" class="fila-titulo-dos"><div align="right">Importe</div></td>
              </tr>
              <tr height="3">
                <td class="text-globales" height="3"> </td>
                <td class="text-globales" height="3"></td>
                <td class="text-globales" height="3"></td>
                <td class="text-globales"></td>
                <td class="text-globales" height="3"></td>
                <td class="text-globales" height="3"></td>
              </tr>
              <% 

	if(htIdentificadoresContrapartidaOK != null && !htIdentificadoresContrapartidaOK.isEmpty()){
		Enumeration en = htIdentificadoresContrapartidaOK.keys();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			String [] identificadores = (String []) htIdentificadoresContrapartidaOK.get(key);
			String nro_cuenta = (identificadores[4] + "-" + identificadores[37]);	
						  %>
              <tr class="fila-det" >
                <td height="32" class="fila-det-border"> <%= identificadores[2]  %> - <%= identificadores[1] %></td>
                <td class="fila-det-border"><%= identificadores[30] %>&nbsp;</td> 
                <td class="fila-det-border"><%= identificadores[31] %>&nbsp;</td>
                <td class="fila-det-border"><%= nro_cuenta.length() > 40 ? nro_cuenta.substring(0, 40  ) : nro_cuenta  %></td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;
                    <div align="right"><%= identificadores[28] %></div></td> 
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
			String [] movimientos = (String []) htMovimientosCancelarOK.get(key);
			//for(int m=0;m<movimientos.length;m++)
			//  System.out.println(" ~( movimientos[" + m + "]: " + movimientos[m]);	
						  %>
              <tr class="fila-det" >
                <td height="32" class="fila-det-border"> <%= movimientos[30]  %> - <%= movimientos[1] %> &nbsp;</td>
                <td class="fila-det-border"><%= movimientos[30] %>&nbsp;</td>
                <td class="fila-det-border"><%= movimientos[31] %>&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;
                    <div align="right"><%= movimientos[28] %></div></td> 
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
    <td>&nbsp;</td>
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
        <td class="fila-det-border">Total Ingresos </td>
        <td class="fila-det-border"><div align="right">  <%= BVCA.getTotalIngresos() %></div></td>
      </tr>

      <tr class="fila-det" >
        <td class="fila-det-border">Total Contrapartida </td>
        <td class="fila-det-border"><div align="right"> <%= BVCA.getTotalContrapartida() %></div></td>
      </tr>

      <tr class="fila-det" >
        <td class="fila-det-border">Total Cobranza </td>
        <td class="fila-det-border"><div align="right"> <%= BVCA.getTotalCobranza() %></div></td>
      </tr>
    </table></td>
  </tr>
	
  <tr>
    <td height="33"><span class="fila-titulo-dos">
      <input name="confirmar" type="submit" class="boton" id="confirmar" style="cursor:pointer" value="Confirmar Ingreso Directo" >
    </span></td>
  </tr>
</table>
<p>&nbsp; </p>
<input name="accion" value="" type="hidden">
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


