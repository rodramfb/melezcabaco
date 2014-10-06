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
String titulo = "INFORMES DE TESORERIA";
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
<jsp:useBean id="BCA"  class="ar.com.syswarp.web.ejb.BeanCajaCallReportes" scope="page"/>
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
 
    var identificador = document.frm.identificador.value;
    var descripcion_identificador = document.frm.descripcion_identificador.value;
		var propio = document.frm.propio.value;
		var tipo = document.frm.tipo.value;
    var fechadesde = document.frm.fechadesde.value;	
		var fechahasta = document.frm.fechahasta.value;	
		var fecha = document.frm.fecha.value;	
		
		
		switch(plantilla){
			/* -------------------------------------------------------------------*/	
			case 'caja_cheques_cartera':
			
				if(trim(fechahasta) == '') {
				  alert('Ingrese fecha hasta.');
				  return false;
				}
		
				if(trim(identificador)==''){
				  alert('Ingrese identificador.');
				  return false;				
				}
			
			  if(tipo != 'C' || propio == 'S'){
				  alert('El identificador debe ser cheque y de terceros.');
				  return false;
				}
			  
				break;
			/* -------------------------------------------------------------------*/	
			case 'caja_cheques_historico':
			
			  if(!validateFechas()) return false;
			
				if(trim(identificador)==''){
				  alert('Ingrese identificador.');
				  return false;				
				}
			
			  if(tipo != 'C' || propio == 'S'){
				  alert('El identificador debe ser cheque y de terceros.');
				  return false;
				}
				
				break;
			/* -------------------------------------------------------------------*/				
			case 'caja_consulta_bancos':
			
				if(trim(fecha) == '') {
				  alert('Ingrese fecha.');
				  return false;
				}
		
				if(trim(identificador)==''){
				  alert('Ingrese identificador.');
				  return false;				
				}
			
			  if(tipo != 'C' || propio != 'S'){
				  alert('El identificador debe ser banco propio.');
				  return false;
				}
				
				break;
			/* -------------------------------------------------------------------*/				
			case 'caja_efectivo_fecha':
			
				if(trim(fecha) == '') {
				  alert('Ingrese fecha.');
				  return false;
				}
		
				if(trim(identificador)==''){
				  alert('Ingrese identificador.');
				  return false;				
				}
			
			  if(tipo != 'E' || propio != 'N'){
				  alert('El identificador debe ser efectivo.');
				  return false;
				}
				
				break;
			/* -------------------------------------------------------------------*/				
      case 'caja_efectivo_global':
			case 'caja_efectivo_global_saldos':

			  if(!validateFechas()) return false;
			
				if(trim(identificador)==''){
				  alert('Ingrese identificador.');
				  return false;				
				}
			
			  if(tipo != 'E' || propio != 'N'){
				  alert('El identificador debe ser efectivo.');
				  return false;
				}

				break;
			/* -------------------------------------------------------------------*/				
			case 'caja_bancos_cheques_emitidos':
			case 'caja_bancos_saldos_contables':
			case 'caja_bancos_cheques_debitar':
			  if(!validateFechas()) return false;
		
				if(trim(identificador)==''){
				  alert('Ingrese identificador.');
				  return false;				
				}
			
			  if(tipo != 'C' || propio != 'S'){
				  alert('El identificador debe ser banco propio.');
				  return false;
				}

				break;
			/* -------------------------------------------------------------------*/				
			//case 'caja_bancos_saldos_contables':
				// -- codigo
				//break;
			/* -------------------------------------------------------------------*/				
			//case 'caja_bancos_cheques_debitar':
				// -- codigo
				//break;
			/* -------------------------------------------------------------------*/				
			case 'caja_mov_depositos_chq_completo':
			case 'caja_mov_cobranzas_completo':
			case 'caja_mov_otros_completo':	
			case 'caja_mov_pagos_completo':
			case 'caja_carteras_normal_todos':
			case 'caja_subdiario_teso':
			case 'caja_mayor_teso':					

			  if(!validateFechas()) return false;

				break;
			/* -------------------------------------------------------------------*/				
			//case 'caja_mov_cobranzas_completo':
				// -- codigo
				//break;
			/* -------------------------------------------------------------------*/				
			//case 'caja_mov_otros_completo':
				// -- codigo
				//break;
			/* -------------------------------------------------------------------*/				
			//case 'caja_mov_pagos_completo':
				// -- codigo
				//break;
			/* -------------------------------------------------------------------*/				
			//case 'caja_carteras_normal_todos':
				// -- codigo
				//break;
			/* -------------------------------------------------------------------*/				
			//case 'caja_subdiario_teso':
				// -- codigo
				//break;
			/* -------------------------------------------------------------------*/				
			//case 'caja_mayor_teso':
				// -- codigo
				//break;																																
			/* -------------------------------------------------------------------*/																
		}
			
    abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML='+plantilla+'&identificador='+identificador+'&descripcion_identificador='+descripcion_identificador+'&fechadesde='+fechadesde+'&fechahasta='+fechahasta+'&fecha='+fecha, 'pdf', 800, 600);
   
	 //document.frm.submit();
 }
 
 function validateFechas(){
   var fdesde = document.frm.fechadesde.value ;
   var fhasta = document.frm.fechahasta.value ;	 
	 
	 if(trim(fdesde) == '') {
	   alert('Ingrese fecha desde.');
		 return false;
	 }
 
	 if(trim(fhasta) == '') {
	   alert('Ingrese fecha hasta.');
		 return false;
	 } 
	 
	 if(retornaPeriodo(fdesde) > retornaPeriodo(fhasta)){
	   alert('Fecha desde no puede ser mayor a fecha hasta.');
		 return false;	   
	 }
 
   return true;
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
    <td>
<form action="cajaCallReportes.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr >
    <td width="100%" colspan="10" >
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="35" colspan="5"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr class="fila-det-bold">
                  <td width="138" height="25" class="fila-det-border" >Fecha desde </td>
                  <td colspan="2" class="fila-det-border"><input name="fechadesde" type="text"  class="campo" id="fechadesde" onFocus="this.blur()" value="<%=BCA.getFechadesde()%>" size="12" maxlength="12" readonly>
                    <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date', 'img_Date_DOWN');showCalendar('frm','fechadesde','BTN_date');return false;"> <img align="absmiddle" border="0" name="BTN_date" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a></td>
                   <td width="112" class="fila-det-border">Fecha Hasta </td>
                   <td width="306" class="fila-det-border"><input name="fechahasta" type="text"  class="campo" id="fechahasta" onFocus="this.blur()" value="<%=BCA.getFechahasta()%>" size="12" maxlength="12" readonly>
                     <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_0', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_0', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_0', 'img_Date_DOWN');showCalendar('frm','fechahasta','BTN_date_0');return false;"> <img align="absmiddle" border="0" name="BTN_date_0" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a></td>
                </tr>
                <tr class="fila-det-bold">
                  <td height="23" class="fila-det-border">Identificador</td>
                  <td width="255" class="fila-det-border"><input name="descripcion_identificador" type="text" id="descripcion_identificador" size="50" class="campo"  value="<%=BCA.getDescripcion_identificador()%>" ></td>
                  <td width="115" class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="18" height="18" style="cursor:pointer" onClick="abrirVentana('lov_cajaIdentificadores.jsp', 'ide', 750, 300);"></td>
                  <td class="fila-det-border">&nbsp;</td>
                  <td class="fila-det-border">&nbsp;</td>
                </tr>
                <tr class="fila-det-bold">
                  <td height="26" class="fila-det-border">Fecha</td>
                  <td colspan="2" class="fila-det-border"><input name="fecha" type="text"  class="campo" id="fecha" onFocus="this.blur()" value="<%=BCA.getFecha()%>" size="12" maxlength="12" readonly>
                    <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_1', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_1', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_1', 'img_Date_DOWN');showCalendar('frm','fecha','BTN_date_1');return false;"> <img align="absmiddle" border="0" name="BTN_date_1" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a></td>
                  <td class="fila-det-border">&nbsp;</td>
                  <td class="fila-det-border">&nbsp;</td>
                </tr>
                <tr class="fila-det-bold">
                  <td class="fila-det-border"><a class="so-BtnLink" href="javascript:calClick();return false;"
                  onMouseOver="calSwapImg('BTN_date', 'img_Date_OVER',true); "
                  onMouseOut="calSwapImg('BTN_date', 'img_Date_UP',true);"
                  onClick="calSwapImg('BTN_date', 'img_Date_DOWN');showCalendar('frm','fechadesde','BTN_date');return false;">
                    <input name="identificador" type="hidden" id="identificador">
                    <input name="propio" type="hidden" id="propio">
                    <input name="tipo" type="hidden" id="tipo">
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BCA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" >
  <tr class="text-catorce" >
     <td colspan="2" >&nbsp;</td>
     </tr> 

   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td width="4%" class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" onClick="generarPDF('caja_cheques_cartera');"></div></td>
     <td width="96%" class="fila-det-border" >Chques en Cartera </td>
   </tr>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" onClick="generarPDF('caja_cheques_historico');"></div></td>
     <td class="fila-det-border" >Cheques Historico </td>
   </tr>
   <tr class="text-catorce" >
     <td colspan="2" >&nbsp;</td>
   </tr>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" onClick="generarPDF('caja_consulta_bancos');"></div></td>
     <td class="fila-det-border" >Consulta de Bancos </td>
   </tr>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" onClick="generarPDF('caja_efectivo_fecha');"></div></td>
     <td class="fila-det-border" >Efectivo a Fecha </td>
   </tr>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" onClick="generarPDF('caja_efectivo_global');"></div></td>
     <td class="fila-det-border" >Efectivo y Globales </td>
   </tr>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" onClick="generarPDF('caja_efectivo_global_saldos');"></div></td>
     <td class="fila-det-border" >Efectivo y Globales - Saldo </td>
   </tr>
   <tr class="text-catorce" >
     <td colspan="2" >&nbsp;</td>
   </tr>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" onClick="generarPDF('caja_bancos_cheques_emitidos');"></div></td>
     <td class="fila-det-border" >Bancos - Cheques Emitidos </td>
   </tr>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" onClick="generarPDF('caja_bancos_saldos_contables');"></div></td>
     <td class="fila-det-border" >Bancos - Saldos Contables </td>
   </tr>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" onClick="generarPDF('caja_bancos_cheques_debitar');"></div></td>
     <td class="fila-det-border" >Bancos - Cheques a Debitar </td>
   </tr>
   <tr class="text-catorce" >
     <td colspan="2" >&nbsp;</td>
   </tr>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" onClick="generarPDF('caja_mov_depositos_chq_completo');"></div></td>
     <td class="fila-det-border" >Movimientos - Depositos de Cheques </td>
   </tr>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" onClick="generarPDF('caja_mov_cobranzas_completo');"></div></td>
     <td class="fila-det-border" >Movimientos - Cobranzas </td>
   </tr>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" onClick="generarPDF('caja_mov_otros_completo');"></div></td>
     <td class="fila-det-border" >Movimientos - Otros </td>
   </tr>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" onClick="generarPDF('caja_mov_pagos_completo');"></div></td>
     <td class="fila-det-border" >Movimientos - Pagos </td>
   </tr>
   <tr class="text-catorce" >
     <td colspan="2" >&nbsp;</td>
   </tr>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" onClick="generarPDF('caja_carteras_normal_todos');"></div></td>
     <td class="fila-det-border" >Carteras - Informe Normal </td>
   </tr>
   <tr class="text-catorce" >
     <td colspan="2" >&nbsp;</td>
   </tr>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" onClick="generarPDF('caja_subdiario_teso');"></div></td>
     <td class="fila-det-border" >Contable - Informe Subdiario </td>
   </tr>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" onClick="generarPDF('caja_mayor_teso');"></div></td>
     <td class="fila-det-border" >Contable - Mayor </td>
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

