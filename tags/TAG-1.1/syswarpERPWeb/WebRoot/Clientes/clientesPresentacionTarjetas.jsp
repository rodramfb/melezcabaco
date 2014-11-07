<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: clientetarjetascreditomarcas
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Jan 23 19:22:37 GMT-03:00 2007 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%@ include file="session.jspf"%>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.*" %>
<% 
try{
Strings str = new Strings();
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BCF"  class="ar.com.syswarp.web.ejb.BeanClientesPresentacionTarjetas"   scope="page"/>
<head>
 <title>PRESENTACION DE TARJETAS</title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">
 <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css"> 
 <script language="JavaScript" src="scripts/forms.js"></script>

 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script>
 
 function generarPDF(plantilla){

	   var idclub = document.frm.idclub.options[document.frm.idclub.selectedIndex].value;
	   var idtarjetacredito = document.frm.idtarjetacredito.options[document.frm.idtarjetacredito.selectedIndex].value; 
	   var fecha = document.frm.fechaPresentacion.value;
	   
		switch(plantilla){
			/* -------------------------------------------------------------------*/	 
			case 'clientes_debitos_enviar':
			
                if( idclub =='-1'){
				  alert('Seleccione Club.');			    
				}else if( idtarjetacredito =='-1'){
				  alert('Seleccione Tarjeta de Credito.');	
				}else if(trim(fecha)==''){
				  alert('Seleccione fecha.');
				}else{
				 abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML='+plantilla+'&fecha='+fecha+'&idclub='+idclub+'&idtarjetacredito='+idtarjetacredito, 'pdf', 800, 600);			
				}
			
				break;
			/* -------------------------------------------------------------------*/	

		}

 } 
 
 
 function ejecutarPresentacion(){
   document.frm.accion.value = 'presentacion';
   document.getElementById("presentacion").value = 'Procesando ...';
   document.getElementById("presentacion").disabled = true;
   document.frm.submit();
 }
 
 
 </script>

</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCF" property="*" />
 <% 
 String titulo =  "GENERAR PRESENTACION DE DEBITOS DE TARJETAS " ; 
 BCF.setResponse(response);
 BCF.setRequest(request);
 BCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BCF.ejecutarValidacion();
 %>
<form action="clientesPresentacionTarjetas.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCF.getAccion()%>" >
   <table width="90%"  border="0" cellspacing="0" cellpadding="0" align="center">
     <tr>
       <td>
         <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
            <tr class="text-globales">
              <td>&nbsp;<%= titulo %></td>
            </tr>
         </table> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr class="fila-det-bold-rojo">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><jsp:getProperty name="BCF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td height="30" class="fila-det-border">Ver Archivos </td>
                <td height="30" class="fila-det-border"><img src="../imagenes/default/gnome_tango/apps/kfm.png" width="22" height="22" onClick="abrirVentana('clientesPresentacionArchivos.jsp?idtarjetacredito=' + document.frm.idtarjetacredito.options[document.frm.idtarjetacredito.selectedIndex].value + '&tarjetacredito=' + document.frm.idtarjetacredito.options[document.frm.idtarjetacredito.selectedIndex].text, 'files', 750, 450)" style="cursor:pointer"></td>
              </tr>
              <tr class="fila-det">
                <td height="30" class="fila-det-border">Club(*)</td>
                <td height="30" class="fila-det-border"> 
                  <select name="idclub" id="idclub" class="campo" style="width:200px"  >
                    <option value="-1" >Sel.</option>
                    <% 
									  Iterator iter = BCF.getListClub().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
                    <option value="<%= datos[0] %>" <%= datos[0].equals( BCF.getIdclub().toString() ) ? "selected" : "" %>><%= datos[1] %></option>
                    <%  
									  }%>
                  </select>                </td>
              </tr>
              <tr class="fila-det">
                <td width="35%" height="34" class="fila-det-border">Tarjeta Credito: (*) </td>
                <td width="65%" class="fila-det-border">
                  <select name="idtarjetacredito" id="idtarjetacredito" class="campo" style="width:200px" >
                    <option value="-1">Seleccionar</option>
                    <%
                    iter = BCF.getListTarjetasCredito().iterator();   
                    while(iter.hasNext()){
                     String[] datos = (String[]) iter.next();
                     %>
                    <option value="<%= datos[0] %>" <%=  BCF.getIdtarjetacredito().toString().equals(datos[0]) ? "selected" : "" %>><%= datos[1] %></option>
                    <% 
                   } %>
                </select></td>
              </tr>
              <tr class="fila-det">
                <td height="45" class="fila-det-border">Per&iacute;odo de Presentacion: (*) (AAAA-MM) </td>
                <td class="fila-det-border"><table width="21%" border="0" cellpadding="0" cellspacing="0">
                  <tr class="fila-det-border">
                    <td width="29%"><input name="fechaPresentacion" type="text" class="cal-TextBox"
													id="fechaPresentacion" onFocus="this.blur()"
													value="<%=BCF.getFechaPresentacion() %>" size="12"
													maxlength="10" readonly>                    </td>
                    <td width="71%"><a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onmouseover="calSwapImg('BTN_date_6', 'img_Date_OVER',true); "
													onmouseout="calSwapImg('BTN_date_6', 'img_Date_UP',true);"
													onclick="calSwapImg('BTN_date_6', 'img_Date_DOWN');showCalendar('frm','fechaPresentacion','BTN_date_6');return false;"><img
														src="vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_6" width="22" height="17" border="0"
														align="absmiddle"> </a> </td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td height="30" class="fila-det-border">Control de D&eacute;bitos </td>
                <td height="30" class="fila-det-border"><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" onClick="generarPDF('clientes_debitos_enviar');" style="cursor:pointer" title="Control de debitos a enviar ..."></td>
              </tr>
              <tr class="fila-det">
                <td height="45" class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;<input name="presentacion" type="button" class="boton" id="presentacion" value="Generar Presentaci&oacute;n" onClick="ejecutarPresentacion();"></td>
             </tr>
          </table>
       </td>
      </tr>
    </table>
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

