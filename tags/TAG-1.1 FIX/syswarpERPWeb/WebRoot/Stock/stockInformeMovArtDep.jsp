<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vMovArtFecha
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Nov 14 09:20:44 GMT-03:00 2006 
   Observaciones: 
      .
*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.math.*"%>
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "Movimientos por Articulo y por deposito.";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
String idempresa  = session.getAttribute("empresa").toString();
%>
<html>

<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 <link rel="stylesheet" type="text/css" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>
 
 <script>
   function validaEmpresaLov(pagina){
   var idempresainforme = document.frm.idempresainforme.value;
	   if(idempresainforme == "" ){
	     alert("Es necesario seleccionar empresa.");
	   }
	   else{
	     abreVentana(pagina + "?idempresainforme=" + idempresainforme, 750, 300 );
	   }
   }
 </script>
 
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="popupcalendar" class="text"></div>
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="stockInformeMovArtDep.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td width="100%" height="57"  class="text-globales"><%=titulo%></td>
                </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
    <td class="fila-det-bold-rojo"><p>&nbsp;</p>
      </td>
  </tr>
</table>

<table width="100%" border="0" cellspacing="1" cellpadding="1"  >
  <tr  class="fila-det" scope="col" >
    <td width="14%" class="fila-det-border" >Empresa (*)</td>
    <td width="32%" class="fila-det-border" ><input name="empresa" type="text" id="empresa" value="<%//= BVMAFA.getFecha_ms() %>" size="50" readonly></td>
    <td width="54%" class="fila-det-border" ><input name="idempresainforme" type="hidden" id="idempresainforme">&nbsp;</td>
  </tr>
  <tr  class="fila-det" scope="col" >
    <td class="fila-det-border" >Articulo (*)</td>
    <td class="fila-det-border" ><input size="50" readonly type="text" name="descrip_st" value="<%//= BVMAFA.getFecha_ms() %>"></td>
    <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="validaEmpresaLov('../Stock/lov_articulo_informe.jsp')" style="cursor:pointer">
      <input name="codigo_st" type="hidden" id="codigo_st">&nbsp;</td>
  </tr>
  <tr  class="fila-det" scope="col" >
    <td class="fila-det-border" >Deposito (*)</td>
    <td class="fila-det-border" ><input  size="50" readonly type="text" name="deposito" value="<%//= BVMAFA.getFecha_ms() %>"></td>
    <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="validaEmpresaLov('../Stock/lov_deposito_informe.jsp')" style="cursor:pointer">
      <input name="iddeposito" type="hidden" id="iddeposito">&nbsp;</td>
  </tr>
  <tr  class="fila-det" scope="col" >
    <td class="fila-det-border" >Fecha Desde (*) </td>
    <td class="fila-det-border" ><input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fecha_ms_desde" value="<%//= BVMAFA.getFecha_ms() %>" maxlength="12">
      <a class="so-BtnLink" href="javascript:calClick();return false;" 
											 onMouseOver="calSwapImg('BTN_date_1', 'img_Date_OVER',true);" 
											 onMouseOut="calSwapImg('BTN_date_1', 'img_Date_UP',true);" 
											 onClick="calSwapImg('BTN_date_1', 'img_Date_DOWN');showCalendar('frm','fecha_ms','BTN_date_1');return false;"> <img align="absmiddle" border="0" name="BTN_date_1" src="vs/calendar/btn_date_up.gif" width="22" height="17"> </a></td>
    <td class="fila-det-border" >&nbsp;</td>
  </tr>
  <tr  class="fila-det" scope="col" >
    <td class="fila-det-border" >Fecha Hasta  (*) </td>
    <td class="fila-det-border" ><input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fecha_ms_hasta" value="<%//= BVMAFA.getFecha_ms() %>" maxlength="12">
      <a class="so-BtnLink" href="javascript:calClick();return false;" 
											 onMouseOver="calSwapImg('BTN_date_12', 'img_Date_OVER',true);" 
											 onMouseOut="calSwapImg('BTN_date_12', 'img_Date_UP',true);" 
											 onClick="calSwapImg('BTN_date_12', 'img_Date_DOWN');showCalendar('frm','fecha_ms_hasta','BTN_date_12');return false;"> <img align="absmiddle" border="0" name="BTN_date_12" src="vs/calendar/btn_date_up.gif" width="22" height="17"> </a></td>
    <td class="fila-det-border" >&nbsp;</td>
  </tr>
</table>
<p>&nbsp; </p>
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

