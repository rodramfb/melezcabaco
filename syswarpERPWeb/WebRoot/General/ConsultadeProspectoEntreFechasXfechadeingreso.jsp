<%@page language="java"%>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: pedidos_cabe
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Jan 02 09:51:28 GMT-03:00 2007 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias 


*/ 

%>
<%@ page import="javax.servlet.http.*"%>
<%@ page import="ar.com.syswarp.api.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@ page import="java.util.Iterator" %> 
<%@ include file="session.jspf"%>
<% 
try{
int i = 0;

Iterator iterConsulta1   = null;
Iterator iter   = null;

Strings str = new Strings();
String color_fondo ="";
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<%-- INSTANCIAR BEAN --%>
	<jsp:useBean id="BPF"
		class="ar.com.syswarp.web.ejb.BeanConsultadeProspectoEntrefechasXfechaingreso" scope="page" />
	<head>
		<title>Consuta de Movimientos por Articulo y Deposito</title>
		 <link rel = "stylesheet" href = "<%= pathskin %>">
		<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
		<link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
		<script language="JavaScript" src="vs/forms/forms.js"></script>
		<script language="JavaScript" src="vs/overlib/overlib.js"></script>
		
<script>

  function checkUncheck(onOff){
     var i = 0;
     for(i=0;i<document.frm.elements.length;i++){
        if(document.frm.elements[i].type == 'checkbox' && !document.frm.elements[i].disabled){
          document.frm.elements[i].checked = onOff;
        }
      }    
  }


	function callGenerarFichasSeleccion() {
     var i = 0;
     var ok = false;
     var clientesToCollection = "";
     for(i=0;i<document.frm.elements.length;i++){
        if(document.frm.elements[i].type == 'checkbox' && !document.frm.elements[i].disabled){
          if(document.frm.elements[i].checked){
            ok = true; 
            clientesToCollection+=document.frm.elements[i].value + "-";
          } 
        }
      }
   
     if(ok)       
       abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=clientes_precarga_ficha_asocia&tipo=NUEVO&clientesToCollection=' + clientesToCollection, 'ficha', 750, 500);
     else
       alert('Debe seleccionar al menos un Prospecto.');
  }	
</script>
		 
		<meta http-equiv="Content-Type"	content="text/html; charset=iso-8859-1">
	</head>
	<BODY>
		<div id="popupcalendar" class="text"></div>
		<%-- EJECUTAR SETEO DE PROPIEDADES --%>
		<jsp:setProperty name="BPF" property="*" />
		<% 
 String titulo = "Consulta de Fecha de Ingreso de Prospecto";
	
		
		
 BPF.setResponse(response);
 BPF.setRequest(request);
 // ver esto BPF.setSession(session);
 BPF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BPF.ejecutarValidacion();
 java.util.List Consulta1 = new java.util.ArrayList();

 Consulta1 = BPF.getMovimientosList();
  
 iterConsulta1 = Consulta1.iterator(); 
 %>
		<form action="ConsultadeProspectoEntreFechasXfechadeingreso.jsp" method="post" name="frm">
			<input name="accion" type="hidden" value="consulta">
			
            <table width="350%" border="0" cellspacing="0" cellpadding="0" align="center">
							<tr class="text-globales">
								<td>
									<table width="100%" border="0"  cellpadding="0"
										cellspacing="0" class="text-globales">
										<tr>
										  <td width="367"><%= titulo %>&nbsp;</td>
																					
											<td width="50">&nbsp;											</td>
											<td width="43">
												
										  </td>
											<td width="42">
										
										  </td>
										</tr>
								  </table>
									
								</td>
							</tr>
		  </table>

						<table width="350%" border="0" cellspacing="0" cellpadding="0">
							<tr class="fila-det-bold-rojo">
								<td class="fila-det-border">&nbsp;</td>
								<td colspan="3" class="fila-det-border">
									<jsp:getProperty name="BPF" property="mensaje" />&nbsp;</td>
							</tr>
              <tr class="fila-det">
								<td width="4%" class="fila-det-border">Fecha Desde: (*) </td> 
								<td width="5%" class="fila-det-border">
                <table width="56%" border="0">
                                  <tr class="fila-det-border">
                                    <td width="13%"><input name="fechadesde" type="text" class="cal-TextBox"
													id="fechadesde" onFocus="this.blur()"
													value="<%=BPF.getFechadesde() %>" size="12"
													maxlength="12" readonly>                                    </td>
                                    <td width="87%"><a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onmouseover="calSwapImg('BTN_date_6', 'img_Date_OVER',true); "
													onmouseout="calSwapImg('BTN_date_6', 'img_Date_UP',true);"
													onclick="calSwapImg('BTN_date_6', 'img_Date_DOWN');showCalendar('frm','fechadesde','BTN_date_6');return false;"><img
														src="vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_6" width="22" height="17" border="0"
														align="absmiddle"> </a> </td>
                                  </tr>
                </table>                </td>
								<td width="4%" class="fila-det-border">Fecha Hasta:(*) </td>
								<td width="87%" class="fila-det-border"><table width="9%" border="0">
                                  <tr class="fila-det-border">
                                    <td width="12%"><input name="fechahasta" type="text" class="cal-TextBox"
													id="fechahasta" onFocus="this.blur()"
													value="<%=BPF.getFechahasta() %>" size="12"
													maxlength="12" readonly>                                    </td>
                                    <td width="88%"><a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onmouseover="calSwapImg('BTN_date_7', 'img_Date_OVER',true); "
													onmouseout="calSwapImg('BTN_date_7', 'img_Date_UP',true);"
													onclick="calSwapImg('BTN_date_7', 'img_Date_DOWN');showCalendar('frm','fechahasta','BTN_date_7');return false;"><img
														src="vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_7" width="22" height="17" border="0"
														align="absmiddle" id="BTN_date_7"> </a> </td>
                                  </tr>
          </table></td>
						  </tr>
              <tr class="fila-det">
                <td height="26" class="fila-det-border">Tipo de Cliente:  </td>
                <td class="fila-det-border"><select name="idtipoclie" id="idtipoclie" class="campo" style="width:200px" >
                  <option value="-1">Seleccionar</option>
                  <%
                   iter = BPF.getListTipoClie().iterator();   
                    while(iter.hasNext()){
                     String[] datos = (String[]) iter.next();
                     %>
                  <option value="<%= datos[0] %>" <%=  BPF.getIdtipoclie().toString().equals(datos[0]) ? "selected" : "" %>><%= datos[1] %></option>
                  <% 
                   } %>
                </select></td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
              <tr class="fila-det">
			    <td width="4%" height="46" class="fila-det-border">&nbsp;</td>
			    <td width="5%" class="fila-det-border"><input name="validar"
											type="submit" value="Consultar" class="boton"></td>
			    <td width="4%" class="fila-det-border">&nbsp;</td>
			    <td width="87%" class="fila-det-border">&nbsp;						</td>
		  </table>
			<input name="primeraCarga" type="hidden" value="false" >


<%if (BPF.getAccion().equalsIgnoreCase("consulta")){%>		
<table width="350%" border="0" cellpadding="1" cellspacing="2" name="rsTable"   >
  <tr class="text-globales">
    <td colspan="24" ><table width="35%" border="0" cellspacing="2" cellpadding="0">
        <tr class="text-globales">
          <td width="26%"><a href="#">
            <div onClick="checkUncheck(true)" >Todos</div>
          </a></td>
          <td width="32%"><a href="#">
            <div onClick="checkUncheck(false)" > Ninguno</div>
          </a></td>
          <td width="42%"><span><a href="#"> 
            <div onClick="callGenerarFichasSeleccion( )" >  Imprimir Selecci&oacute;n</div></a></span></td>
        </tr>
      </table></td>
    </tr>
  <tr class="fila-encabezado">
    <td ><div align="center"></div></td> 
     <td ><div align="center"></div></td>
     <td ><div align="center">Club</div></td>
     <td ><div align="right">C&oacute;digo Precarga</div></td>  
     <td>Nuevo o Reactivaci&oacute;n </td>
    <td>Razon</td>
     <td><div align="center">Fecha Nacimiento </div></td>
     <td>Tipo. Documento </td>
     <td><div align="right">N&deg; Doc. </div></td>
     <td><div align="center">Fecha Ingreso</div></td>
     <td>Vendedor</td>
     <td><div align="right">C&oacute;digo Cliente </div></td>
     <td>Origen</td>
     <td>Sub Origen</td>
     <td>Estado Precarga</td>
     <td><div align="center">Fecha Alta </div></td>
     <td>Promoci&oacute;n</td>
     <td>Preferencia</td>
     <td><div align="center">Sucursal Facturaci&oacute;n </div></td>
     <td><div align="right">Cuenta</div></td>
     <td>Condici&oacute;n</td>
     <td>Lista de Precios </td>
     <td>Tipo IVA </td>
  </tr> 
   <%int r = 0;
   while(iterConsulta1.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterConsulta1.next(); 
       String imagen ="";      
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";   
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td  class="fila-det-border" ><div align="center">
       <input name="clientesToCollection" type="checkbox" id="clientesToCollection" value="<%=Common.setNotNull(sCampos[8])%>" <%=Common.setNotNull(sCampos[8]).equals("") ? "disabled" : ""%>>
     </div></td>
     <td  class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" style="cursor:pointer" <%=Common.setNotNull(sCampos[8]).equals("") ? "onClick=\"alert('Prospecto no posee Número de Cliente asignado.')\"" : "onClick=\"abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=clientes_precarga_ficha_asocia&clientesToCollection=" + sCampos[8]  +"&tipo=NUEVO', 'ficha', 750, 500)\" "%> ></div></td>
     <td  class="fila-det-border" ><div align="center"><img src="<%=sCampos[16]%>" title="<%=sCampos[15]%>"></div></td>
     <td  class="fila-det-border" ><div align="right"><%=Common.setNotNull(sCampos[0])%></div></td>
     <td class="fila-det-border" >&nbsp;<%=Common.setNotNull(sCampos[1])%></td>
     <td class="fila-det-border" >&nbsp;<%=Common.setNotNull(sCampos[2])%></td>
     <td class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[3]), "JSTsToStr")%></div></td>
     <td class="fila-det-border" >&nbsp;<%=Common.setNotNull(sCampos[4])%></td>
     <td class="fila-det-border" ><div align="right"><%=Common.setNotNull(sCampos[5])%></div></td>
     <td class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[6]), "JSTsToStr")%></div></td>
     <td class="fila-det-border" >&nbsp;<%=Common.setNotNull(sCampos[7])%></td>
     <td class="fila-det-border" ><div align="right"><%=Common.setNotNull(sCampos[8])%></div></td>
     <td class="fila-det-border" >&nbsp;<%=Common.setNotNull(sCampos[9])%></td>
     <td class="fila-det-border" >&nbsp;<%=Common.setNotNull(sCampos[10])%></td>
     <td class="fila-det-border" >&nbsp;<%=Common.setNotNull(sCampos[12])%></td>
     <td class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[13]), "JSTsToStr")%></div></td>
     <td class="fila-det-border" >&nbsp;<%=Common.setNotNull(sCampos[18])%></td>
     <td class="fila-det-border" >&nbsp;<%=Common.setNotNull(sCampos[21])%></td>
     <td class="fila-det-border" ><div align="center"><%=Common.setNotNull(sCampos[19])%></div></td>
     <td class="fila-det-border" ><div align="right"><%= !Common.setNotNull( sCampos[24]).equals("") ?  (   Common.setNotNull(sCampos[24]) + "-" + Common.setNotNull(sCampos[25])  ) : "<img src=\"../imagenes/default/gnome_tango/status/dialog-error.png\"   title=\"Cuenta no corresponde al ejercicio activo o no asignada\" height=\"15\" width=\"15\">" %></div></td>
     <td class="fila-det-border" >&nbsp;<%=Common.setNotNull(sCampos[28])%></td>
     <td class="fila-det-border" >&nbsp;<%=Common.setNotNull(sCampos[23])%></td>
     <td class="fila-det-border" >&nbsp;<%=Common.setNotNull(sCampos[11])%></td>
   </tr>
   <%
   }%>
  </table>

   
<%}%>   
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

