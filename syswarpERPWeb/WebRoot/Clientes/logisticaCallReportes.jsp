<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: Cajaferiados
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Aug 01 11:33:07 GMT-03:00 2006 
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
String color_fondo ="fila-det";
String titulo = "INFORMES DE LOGISTICA";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
int totCol = 3; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>

<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 <link rel="stylesheet" href="../imagenes/default/erp-style.css">	

 <link rel="stylesheet" type="text/css" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>
 <script>
 function generarPDF(plantilla){

		var fecha = document.frm.fecha.value;	
		switch(plantilla){
			/* -------------------------------------------------------------------*/	
			case 'logistica_listado_obs_armado':
			case 'logistica_listado_totales_motivos_desc':

         if(trim(fecha) == ''){
           alert('Es necesario seleccionar Período.');
           return false;
         }

				break;

		}
			
    abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML='+plantilla+'&fecha='+fecha, 'pdf', 800, 600);
   
 }
 

 </script> 
</head>
<%
// titulos para las columnas
tituCol[0] = "Codigo";
tituCol[1] = "Feriado";
tituCol[2] = "Fecha feriado";


%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<div id="popupcalendar" class="text"></div>
<table width="95%" border="1" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="239">
<form action="logisticaCallReportes.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr >
    <td width="100%" colspan="10" >
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="56" colspan="5"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr class="fila-det-bold">
                  <td width="138" height="44" class="fila-det-border">PERIODO: (*) </td>
                  <td width="370" colspan="2" class="fila-det-border"><input name="fecha" type="text"  class="campo" id="fecha" onFocus="this.blur()" value="" size="12" maxlength="12" readonly>
                    <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_1', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_1', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_1', 'img_Date_DOWN');showCalendar('frm','fecha','BTN_date_1');return false;"> <img align="absmiddle" border="0" name="BTN_date_1" src="vs/calendar/btn_date_up.gif" width="22" height="17"> </a></td>
                  <td width="112" class="fila-det-border">&nbsp;</td>
                  <td width="306" class="fila-det-border">&nbsp;</td>
                </tr>
                <tr class="fila-det-bold">
                  <td class="fila-det-border"><a class="so-BtnLink" href="javascript:calClick();return false;"
                  onMouseOver="calSwapImg('BTN_date', 'img_Date_OVER',true); "
                  onMouseOut="calSwapImg('BTN_date', 'img_Date_UP',true);"
                  onClick="calSwapImg('BTN_date', 'img_Date_DOWN');showCalendar('frm','fechadesde','BTN_date');return false;">

                  </a></td>
                  <td colspan="2" class="fila-det-border">&nbsp;</td>
                  <td class="fila-det-border">&nbsp;</td>
                  <td class="fila-det-border">&nbsp;</td>
                </tr>
        </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" >
  <tr class="text-catorce" >
     <td colspan="2" >&nbsp;</td>
     </tr> 

   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td width="4%" height="43" class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" onClick="generarPDF('logistica_listado_obs_armado');"></div></td>
     <td width="96%" class="fila-det-border" > Pedidos - Observaciones Armado </td>
   </tr>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td height="41" class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" onClick="generarPDF('logistica_listado_totales_motivos_desc');"></div></td>
     <td class="fila-det-border" >Productos con Descuento 100 % - Motivos </td>
   </tr>
   <tr class="text-catorce" >
     <td colspan="2" >&nbsp;</td>
   </tr>
   </table>
   <input name="accion" value="" type="hidden">
</form>
 </td>
  </tr>
</table>

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

