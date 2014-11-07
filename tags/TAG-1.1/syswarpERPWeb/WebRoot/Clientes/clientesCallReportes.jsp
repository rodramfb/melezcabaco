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
String titulo = "INFORME DE DEUDA DE CLIENTES";
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
<jsp:useBean id="BCA"  class="ar.com.syswarp.web.ejb.BeanClientesCallReportes" scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BCA" property="*" />
<%
 BCA.setResponse(response);
 BCA.setRequest(request);
 BCA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BCA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 <link rel="stylesheet" href="../imagenes/default/erp-style.css">	

 <link rel="stylesheet" type="text/css" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script> 
 <script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>
 <script>
 function generarPDF(plantilla){

       var anio = 0;
	   var mes = 0;
	   var idcondicion = document.frm.idcondicion.value;
	   var fecha = document.frm.fecha.value;
	   
		switch(plantilla){
			/* -------------------------------------------------------------------*/	
			case 'clientes_movimientos_saldo':
			
			    if(trim(fecha)==''){
				  alert('Ingrese periodo mm/aaaa.');
				}else if(trim(idcondicion)==''){
				  alert('Ingrese condicion.');
				}else{
			     anio = fecha.substring(6); 
			     mes = fecha.substring(3, 5);	
				 abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML='+plantilla+'&anio='+anio+'&mes='+mes+'&idcondicion='+idcondicion, 'pdf', 800, 600);			
				}
			
				break;
			/* -------------------------------------------------------------------*/	
		}
			
    

 }
 
 function validateFechas(){
   return true;
 }

 </script> 
</head>
<%
// titulos para las columnas
tituCol[0] = " ";
tituCol[1] = " ";
tituCol[2] = "  ";


%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<div id="popupcalendar" class="text"></div>
<table width="95%" border="1" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
<form action="clientesCallReportes.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr >
    <td width="100%" colspan="10" >
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="35" colspan="5"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr class="fila-det-bold">
                  <td width="138" height="25" class="fila-det-border" >Per&iacute;odo </td>
                  <td colspan="2" class="fila-det-border"><input name="fecha" type="text"  class="campo" id="fecha" onFocus="this.blur()" value="<%=BCA.getFecha()%>" size="12" maxlength="12" readonly>
                    <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date', 'img_Date_DOWN');showCalendar('frm','fecha','BTN_date');return false;"> <img align="absmiddle" border="0" name="BTN_date" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a></td>
                   <td width="112" class="fila-det-border">&nbsp;</td>
                   <td width="306" class="fila-det-border">&nbsp;</td>
                </tr>
                <tr class="fila-det-bold">
                  <td height="23" class="fila-det-border">Condici&oacute;n</td>
                  <td width="255" class="fila-det-border"><input name="condicion" type="text" id="condicion" size="50" class="campo"  value="<%=BCA.getCondicion()%>" ></td>
                  <td width="115" class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="18" height="18" style="cursor:pointer" onClick="abrirVentana('lov_condventa.jsp', 'ide', 750, 300);"></td>
                  <td class="fila-det-border"><input name="idcondicion" type="hidden" id="idcondicion" value="">&nbsp;</td>
                  <td class="fila-det-border">&nbsp;</td>
                </tr>
                <tr class="fila-det-bold">
                  <td class="fila-det-border"><a class="so-BtnLink" href="javascript:calClick();return false;"
                  onMouseOver="calSwapImg('BTN_date', 'img_Date_OVER',true); "
                  onMouseOut="calSwapImg('BTN_date', 'img_Date_UP',true);"
                  onClick="calSwapImg('BTN_date', 'img_Date_DOWN');showCalendar('frm','fechadesde','BTN_date');return false;"></a></td>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BCA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" >
  <tr class="text-catorce" >
     <td colspan="2" >&nbsp;</td>
     </tr> 

   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td width="4%" class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" onClick="generarPDF('clientes_movimientos_saldo');"></div></td>
     <td width="96%" class="fila-det-border" >Deuda de Socios</td>
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

