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
<jsp:useBean id="BCF"  class="ar.com.syswarp.web.ejb.BeanClientesPresentacionTarjetasRecepcionRechazos"   scope="page"/>
<head>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCF" property="*" />
 <% 
 String titulo =  "RECEPCION DE PRESENTACION DE DEBITOS DE TARJETAS - RECHAZOS" ; 
 BCF.setResponse(response);
 BCF.setRequest(request);
 BCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BCF.ejecutarValidacion();
 %>

 <title>RECHAZOS - PRESENTACION DE TARJETAS</title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">
 <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css"> 
 <script language="JavaScript" src="scripts/forms.js"></script>

 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script>
  
 var indexActualCmb = 0;
   
 function ejecutarRechazo(){
   if(confirm('Esta seguro de rechazar los registros seleccionados?')){
     document.frm.accion.value = 'generarrechazo';
     document.getElementById("generarrechazo").value = 'Procesando ...';
     document.getElementById("generarrechazo").disabled = true;
     document.frm.submit();
   }
 }
 
 function ejecutarPresentacion(){
   document.frm.accion.value = 'presentacion';
   document.getElementById("presentacion").value = 'Procesando ...';
   document.getElementById("presentacion").disabled = true;
   document.frm.submit();
 }
 
function agregar(idmovcuo){
  document.frm.idmovcuo.value = idmovcuo;
  document.frm.accion.value = 'agregar';
  document.frm.submit();
} 
 
 
function eliminarRegistro(idmovcuo){
  if(confirm('Confirma eliminar registro de la selecci�n?')){
    document.frm.idmovcuo.value = idmovcuo;
    document.frm.accion.value = 'eliminar';
    document.frm.submit();
  }
}  

function vaciarLista(){
  if(confirm('Confirma eliminar todos los registros previamente seleccionados')){
    document.frm.accion.value = 'limpiar';
    document.frm.submit();
  }
}  

function reiniciarLista(obj){
  var busqueda = <%= BCF.getListPresentacionCliente().isEmpty() %>;
  if(document.frm.keyHashRechazos && document.frm.keyHashRechazos != null && document.frm.keyHashRechazos != 'undefined' || !busqueda ){
    if(confirm('Esta acci�n limpiar� todos los registros previamente \nseleccionados y/o resultado de consulta. Continua?')){
      document.frm.accion.value = 'limpiar';
      document.frm.submit();
  	  return 1;
    }else{
	  if(obj.type == 'select-one') obj.selectedIndex = indexActualCmb;
	  return 0;
	}
  }
  return 1;
}  

 function generarPDF(plantilla){

	   // var idclub = document.frm.idclub.options[document.frm.idclub.selectedIndex].value;
	   // var idtarjetacredito = document.frm.idtarjetacredito.options[document.frm.idtarjetacredito.selectedIndex].value; 
	   var idclub = document.frm.idclub.value;
	   var idtarjetacredito = document.frm.idtarjetacredito.value; 	
	   var idgeneracion = document.frm.idgeneracion.value; 
	   	   
	   var fecha = document.frm.fechaPresentacion.value;
	   
		switch(plantilla){

			/* -------------------------------------------------------------------*/	
			case 'clientes_debitos_enviados_presentacion':	
                if(trim(fecha)==''){
				  alert('Seleccione per�odo.');
				}else{
				 abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML='+plantilla+'&fecha='+fecha + '&idgeneracion='+idgeneracion, 'pdf', 800, 600);
			    }
				
				break;
				
			/* -------------------------------------------------------------------*/	
			case 'clientes_tarjetas_resultado_presentacion':
			
                if( idclub =='-1'){
				  alert('Seleccione Club.');			    
				}else if( idtarjetacredito =='-1'){
				  alert('Seleccione Tarjeta de Credito.');	
				}else if(trim(fecha)==''){
				  alert('Seleccione fecha.');
				}else{
				 //abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML='+plantilla+'&fecha='+fecha+'&idclub='+idclub+'&idtarjetacredito='+idtarjetacredito, 'pdf', 800, 600);	
				 abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML='+plantilla+'&idgeneracion='+idgeneracion+'&fecha='+fecha+'&idclub='+idclub+'&idtarjetacredito='+idtarjetacredito, 'pdf', 800, 600);					 		
				}
			
				break;
			/* -------------------------------------------------------------------*/	
			case 'clientes_tarjetas_resumen_presentacion':	
                if(trim(fecha)==''){
				  alert('Seleccione per�odo.');
				}else{
				 //abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML='+plantilla+'&fecha='+fecha, 'pdf', 800, 600);
				 abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML='+plantilla+'&fecha='+fecha + '&idgeneracion='+idgeneracion, 'pdf', 800, 600);			 
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
<BODY >
<div id="popupcalendar" class="text"></div>

<form action="clientesPresentacionTarjetasRecepcionRechazos.jsp" method="post" name="frm">
   <input name="accion" type="hidden" value="<%=BCF.getAccion()%>" >
   <input name="idmovcuo" type="hidden" value="-1" >
   <input name="primeraCarga" type="hidden" id="primeraCarga" value="false" >
   <input name="idgeneracion" type="hidden" id="idgeneracion" value="<%=BCF.getIdgeneracion()%>" >   
   <table width="95%"  border="0" cellspacing="0" cellpadding="0" align="center">
     <tr>
       <td>
         <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
            <tr class="text-globales">
              <td>&nbsp;<%= titulo %></td>
            </tr>
         </table> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr class="fila-det-bold-rojo">
                <td height="23" colspan="3" bgcolor="#FFFFCC" class="fila-det-border"><jsp:getProperty name="BCF" property="mensaje"/>                  &nbsp;</td>
              </tr>
              <tr class="fila-det-bold">
                <td height="22" bgcolor="#FFFFCC" class="fila-det-border">Presentaci&oacute;n(*)</td>
                <td height="22" bgcolor="#FFFFCC" class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/clientesTarjetasGeneracionAbm.jsp', 'presentacion', 750, 450)" style="cursor:pointer"></td>
                <td bgcolor="#FFFFCC" class="fila-det-border">
                  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="fila-det-bold">
                      <td width="94%" bgcolor="#FFFFCC"><div align="right">D&eacute;bitos Enviados Presentaci&oacute;n </div></td>
                      <td width="6%" bgcolor="#FFFFCC"><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" onClick="generarPDF('clientes_debitos_enviados_presentacion');" style="cursor:pointer" title="Consultar resumen de la prsentaci&oacute;n ..."></td>
                    </tr>
                  </table>				
				</td>
              </tr>
              <tr class="fila-det-bold">
                <td height="22" bgcolor="#FFFFCC" class="fila-det-border">Club(*)</td>
                <td height="22" bgcolor="#FFFFCC" class="fila-det-border"> 
                  <%-- <select name="idclub" id="idclub" class="campo" style="width:200px" onFocus="indexActualCmb = this.selectedIndex;" onChange="reiniciarLista(this)" >
                    <option value="-1" >Sel.</option>
                    <% 
									  Iterator iter = BCF.getListClub().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
                    <option value="<%= datos[0] %>" <%= datos[0].equals( BCF.getIdclub().toString() ) ? "selected" : "" %>><%= datos[1] %></option>
                    <%  
									  }%>
                  </select> --%>                <input name="club" type="text" class="campo" id="club" value="<%=BCF.getClub()%>" size="35" maxlength="50" readonly >
                  <input name="idclub" type="hidden" id="idclub" value="<%=BCF.getIdclub()%>" ></td>
                <td bgcolor="#FFFFCC" class="fila-det-border"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <tr class="fila-det-bold">
                    <td width="94%" bgcolor="#FFFFCC"><div align="right">Resultado Presentaci&oacute;n</div></td>
                    <td width="6%" bgcolor="#FFFFCC"> <img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" onClick="generarPDF('clientes_tarjetas_resultado_presentacion');" style="cursor:pointer" title="Consultar resultados de la prsentaci&oacute;n ..."> </td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det-bold">
                <td width="30%" height="22" bgcolor="#FFFFCC" class="fila-det-border">Tarjeta Credito: (*) </td>
                <td width="27%" bgcolor="#FFFFCC" class="fila-det-border">
                  <%-- <select name="idtarjetacredito" id="idtarjetacredito" class="campo" style="width:200px" onFocus="indexActualCmb = this.selectedIndex;" onChange="reiniciarLista(this)">
                    <option value="-1">Seleccionar</option>
                    <%
                    iter = BCF.getListTarjetasCredito().iterator();   
                    while(iter.hasNext()){
                     String[] datos = (String[]) iter.next();
                     %>
                    <option value="<%= datos[0] %>" <%=  BCF.getIdtarjetacredito().toString().equals(datos[0]) ? "selected" : "" %>><%= datos[1] %></option>
                    <% 
                   } %>
                </select> --%>
                  <input name="tarjetacredito" type="text" class="campo" id="tarjetacredito" value="<%=BCF.getTarjetacredito()%>" size="35" maxlength="50" readonly >
                  <input name="idtarjetacredito" type="hidden" id="idtarjetacredito" value="<%=BCF.getIdtarjetacredito()%>" ></td>
                <td width="43%" bgcolor="#FFFFCC" class="fila-det-border"><div align="right">
                  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="fila-det-bold">
                      <td width="94%" bgcolor="#FFFFCC"><div align="right">Resumen Presentaci&oacute;n</div></td>
                      <td width="6%" bgcolor="#FFFFCC">  <img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" onClick="generarPDF('clientes_tarjetas_resumen_presentacion');" style="cursor:pointer" title="Consultar resumen de la prsentaci&oacute;n ..."> </td>
                    </tr>
                  </table>
                </div></td>
              </tr>
              <tr class="fila-det-bold">
                <td width="30%" height="29" bgcolor="#FFFFCC" class="fila-det-border">Per&iacute;odo de Presentacion: (*) </td>
                <td width="27%" bgcolor="#FFFFCC" class="fila-det-border"><table width="48%" border="0" cellpadding="0" cellspacing="0">
                  <tr class="fila-det-border">
                    <td width="29%"><input name="fechaPresentacion" type="text" class="campo"
													id="fechaPresentacion" onFocus="this.blur()"
													value="<%=BCF.getFechaPresentacion() %>" size="8"
													maxlength="10" readonly style="text-align:right">                    </td>
                    <td width="71%"><%-- <a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onmouseover="calSwapImg('BTN_date_6', 'img_Date_OVER',true); "
													onmouseout="calSwapImg('BTN_date_6', 'img_Date_UP',true);"
													onclick="if(reiniciarLista(document.frm.fechaPresentacion) == 0) return false; calSwapImg('BTN_date_6', 'img_Date_DOWN');showCalendar('frm','fechaPresentacion','BTN_date_6');return false;"><img
														src="vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_6" width="22" height="17" border="0"
														align="absmiddle"> </a>  --%></td>
                  </tr>
                </table></td>
                <td width="43%" bgcolor="#FFFFCC" class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det-bold">
                <td height="22" bgcolor="#FFFFCC" class="fila-det-border">Cliente:(*) </td>
                <td bgcolor="#FFFFCC" class="fila-det-border"><input name="idcliente" type="text" class="campo" id="idcliente" value="<%=BCF.getIdcliente()%>" size="8" maxlength="10" style="text-align:right" ></td>
                <td bgcolor="#FFFFCC" class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det-bold">
                <td height="28" bgcolor="#FFFFCC" class="fila-det-border"> <input name="presentacion" type="button" class="boton" id="presentacion" value="Buscar Presentaci&oacute;n " onClick="ejecutarPresentacion();"> &nbsp;</td>
                <td bgcolor="#FFFFCC" class="fila-det-border">&nbsp;</td>
                <td bgcolor="#FFFFCC" class="fila-det-border">&nbsp;</td>
              </tr>

              <tr class="subtitulo-dos">
                <td height="28" colspan="3" class="fila-det-border">&nbsp;</td>
              </tr>
			  
			  <% 
			  Iterator it = BCF.getListPresentacionCliente().iterator();
			  if(it.hasNext()){
			   %>
              <tr class="fila-det">
                <td height="45" colspan="3" class="fila-det-border">
				 <table width="100%" border="1" cellspacing="0" cellpadding="0">
                  <tr class="text-dos-bold">
                    <td width="2%" rowspan="2">&nbsp;</td>
                    <td width="45%" rowspan="2">Cliente</td>
                    <td width="19%" rowspan="2"><div align="center">Tarjeta</div></td>
                    <td width="18%" rowspan="2"><div align="center">Comprobante</div></td>
                    <td colspan="2"><div align="center">Importes</div></td>
                  </tr>
                  <tr class="text-dos-bold">
                    <td width="8%"><div align="right">Comprobante</div></td>
                    <td width="8%"><div align="right">Presentaci&oacute;n</div></td>
                  </tr>
               <%  
			     while(it.hasNext()){			   
			       String[] datos = (String[])it.next();%>
                  <tr bgcolor="#CCFF99" class="fila-det">
                    <td bgcolor="#CCFF99"><div align="center"><img src="../imagenes/default/nuevo.gif" width="16" height="16" title="Agregar...." onClick="agregar('<%= datos[0] %>')" style="cursor:pointer"></div></td>
                    <td bgcolor="#CCFF99"><%= datos[3]  + "-" + datos[4] %>&nbsp;</td>
                    <td bgcolor="#CCFF99"><div align="center"><%= datos[5] %>&nbsp;</div></td>
                    <td bgcolor="#CCFF99"><div align="center"><%= datos[6] %>&nbsp;</div></td>
                    <td bgcolor="#CCFF99"><div align="right"><%= Common.visualNumero( datos[7], 2 )  %>&nbsp;</div></td>
                    <td bgcolor="#CCFF99"><div align="right"><%= Common.visualNumero( datos[2], 2 ) %></div></td>
                  </tr>
			  <% 
			      }
				%>
               </table>				</td>
              </tr>
			  <% 
			  } else {
			   %>
              <tr class="fila-det">
                <td height="45" colspan="3" class="fila-det-border">
				  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr bgcolor="#CCFF99" class="fila-det">
                    <td   bgcolor="#CCFF99" class="fila-det-bold-rojo"><jsp:getProperty name="BCF" property="mensajePresntacion"/></td>
                   </tr>		   
			      </table>				</td>
              </tr>			   
				<%
			  }

		     Hashtable htRechazosPresentacion = (Hashtable) session.getAttribute("htRechazosPresentacion");
			 if(htRechazosPresentacion != null && !htRechazosPresentacion.isEmpty()){%> 	 
              <tr class="fila-det">
                <td height="45" colspan="3" class="fila-det-border">
				<table width="100%" border="1" cellspacing="0" cellpadding="0">
                  <tr class="text-dos-bold">
                    <td colspan="6"><table width="100%" border="1" cellspacing="0" cellpadding="0">
                        <tr class="text-dos-bold">
                          <td colspan="2" class="text-cuatro"><div align="right">
                            <input name="generarrechazo" type="button" class="boton" id="generarrechazo" value="  Generar Rechazos  " onClick="ejecutarRechazo();">
                          </div></td>
                        </tr>
                        <tr class="text-dos-bold">
                          <td width="89%" class="text-cuatro"><div align="right">Total de Rechazos </div></td>
                          <td width="11%" class="text-cuatro"><div align="right"><%=  htRechazosPresentacion.size() + ""%>&nbsp;</div></td>
                      </tr>
                        <tr class="text-dos-bold">
                          <td class="text-cuatro"><div align="right">Importe de Rechazos </div></td>
                          <td class="text-cuatro"><div align="right"><%= Common.visualNumero( (BCF.getImporteRechazo() + ""), 2)%>&nbsp;</div></td>
                        </tr>
                      </table>					</td>
                  </tr>
                  <tr class="text-dos-bold">
                    <td width="2%"><img src="../imagenes/default/gnome_tango/actions/edit-clear.png" width="22" height="22" title="Vaciar lista ..." onClick="vaciarLista();" style="cursor:pointer"></td>
                    <td width="44%">Cliente</td>
                    <td width="16%"><div align="center">Tarjeta</div></td>
                    <td width="15%"><div align="center">Comprobante</div></td>
                    <td width="12%"><div align="center">Motivo</div></td>
                    <td width="11%"><div align="right">Importe</div></td>
                  </tr>
                  <%  

				   Enumeration en = htRechazosPresentacion.keys();
				   while (en.hasMoreElements()) {
					String key = (String) en.nextElement();
					String [] recepcionRechazos = (String []) htRechazosPresentacion.get(key);
					String idmotivorechazo = "idmotivorechazo" + key;				  
				  %>				  
				  
                  <tr class="fila-det">
                    <td bgcolor="#FFCC99"><img src="../imagenes/default/gnome_tango/status/gtk-missing-image.png" title="Eliminar registro ..." onClick="eliminarRegistro(<%= recepcionRechazos [0]%>)" style="cursor:pointer"></td>
                    <td bgcolor="#FFCC99"><%= recepcionRechazos [3] + " - " + recepcionRechazos [4]%>
                    <input name="keyHashRechazos" type="hidden" id="keyHashRechazos" value="<%= recepcionRechazos [0]%>" ></td>
                    <td bgcolor="#FFCC99"><div align="center"><%= recepcionRechazos [5]%>&nbsp;</div></td>
                    <td bgcolor="#FFCC99"><div align="center"><%= recepcionRechazos [6]%>&nbsp;</div></td>
                    <td bgcolor="#FFCC99"><div align="center">
                      <select name="idmotivorechazo" id="<%= idmotivorechazo %>" class="campo" style="width:100px" <%//= jsOnChangeDep %>>
                        <option value="-1">Seleccionar</option>
                        <% 
					it = BCF.getListMotivosRechazo().iterator();					
					while(it.hasNext()){
						String keyRechazo[] = (String[]) it.next();
						String sel = "";
						if(recepcionRechazos[9].equals(keyRechazo[0])) sel = "selected";
						else sel = "";
										
										  %>
                        <option value="<%= keyRechazo[0] %>" <%= sel %>><%= keyRechazo[1] %></option>
                        <%
					}
					%>
                      </select>
                    </div></td>
                    <td bgcolor="#FFCC99"><div align="right"><%= Common.visualNumero(recepcionRechazos[2], 2) %>&nbsp;</div></td>
                  </tr>
                  <%  
				   }
				  %>				  
                </table>				</td>
              </tr>
	
			 <%  
			 }
			 %>
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

