<%@page language="java" %>
<%
response.setHeader("Cache-Control", "no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: rrhhliq_cabe
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jul 26 14:23:25 ART 2012 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.math.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%@ include file="session.jspf"%>
<% 
try{
	Strings str = new Strings();
	String pathskin = session.getAttribute("pathskin").toString();
	String pathscript = session.getAttribute("pathscript").toString();
%>
	<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
	<html>
		<%-- INSTANCIAR BEAN --%>  
		<jsp:useBean id="BRF"  class="ar.com.syswarp.web.ejb.BeanRrhhliq_cabeFrm"   scope="page"/>
		<head>
 			<title>Liquidación de Sueldos y Jornales</title>
 			<meta http-equiv="description" content="DELTA">
 			<link rel="stylesheet" type="text/css" href="<%=pathskin%>">
			<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
			<link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
			<script language="JavaScript" src="vs/forms/forms.js"></script>
			<script language="JavaScript" src="vs/overlib/overlib.js"></script>
		</head>
		<BODY>
			<div id="popupcalendar" class="text"></div>
 			<%-- EJECUTAR SETEO DE PROPIEDADES --%>
 			<jsp:setProperty name="BRF" property="*" />
			<% 
			String titulo = BRF.getAccion().toUpperCase() + " DE Liquidación de Sueldos y Jornales" ;
			BRF.setResponse(response);
			BRF.setRequest(request);
			BRF.setUsuarioalt( session.getAttribute("usuario").toString() );
			BRF.setUsuarioact( session.getAttribute("usuario").toString() );
			BRF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
			BRF.ejecutarValidacion();
			%>
			<form action="rrhhliq_cabeFrm.jsp" method="post" name="frm">
				<input name="accion" type="hidden" value="<%=BRF.getAccion()%>" >
				<input name="idliqcabe" type="hidden" value="<%=BRF.getIdliqcabe()%>" >
				
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
                					<td class="fila-det-border">
                						<jsp:getProperty name="BRF" property="mensaje"/>&nbsp;
                					</td>
              					</tr>
              					<tr class="fila-det">
                					<td width="12%" class="fila-det-border">&nbsp;Nro. recibo : (*) </td>
                					<td width="88%" class="fila-det-border">&nbsp;
                						<input name="nrorecibo" type="text" value="<%=BRF.getNrorecibo()%>" class="campo" size="10" maxlength="7"  >
                					</td>
              					</tr>
              					<tr class="fila-det">
                					<td width="12%" class="fila-det-border">&nbsp;Legajo: (*) </td>
                					<td width="88%" class="fila-det-border">&nbsp;
                						<input name="legajo" type="text" readonly="readonly" value="<%=BRF.getLegajo()%>" class="campo" size="10" maxlength="10">
                						<input name="apellido" type="text" readonly="readonly" value="<%=BRF.getApellido()%>" class="campo" size="25" maxlength="100">
                						<img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../RRHH/lov_personal.jsp')" style="cursor:pointer">
					                </td>
              					</tr>
              					<tr class="fila-det">
                					<td width="12%" class="fila-det-border">&nbsp;Fecha: (*) </td>
                					<td colspan="2" class="fila-det-border" >&nbsp;
                  						<input class="cal-TextBox" onFocus="this.blur()" size="12" readonly="readonly"  type="text" name="fecha" value="<%=BRF.getFecha()%>" maxlength="12">
                  						<a class="so-BtnLink" href="javascript:calClick();return false;" onmouseover="calSwapImg('BTN_date_4', 'img_Date_OVER',true); " onmouseout="calSwapImg('BTN_date_4', 'img_Date_UP',true);" onclick="calSwapImg('BTN_date_4', 'img_Date_DOWN');showCalendar('frm','fecha','BTN_date_4');return false;">
                  							<img align="absmiddle" border="0" name="BTN_date_4" src="vs/calendar/btn_date_up.gif" width="22" height="17">
                  						</a>
                					</td>
              					</tr>
              					<tr class="fila-det">
                					<td width="12%" class="fila-det-border">&nbsp;Año de liquidación: (*) </td>
                					<td width="88%" class="fila-det-border">&nbsp;<select name = "anioliq">
                							<option value="-1">Seleccionar</option>
				                			<%
				                			Iterator iterAnios = BRF.getListAnios().iterator();
				                			while (iterAnios.hasNext())
				                			{
				                				String[] datosAnios = (String[]) iterAnios.next();
				                				%>
				                				<option value ="<%=datosAnios[0]%>" <%=BRF.getAnioliq().longValue() == new BigDecimal(datosAnios[0]).longValue() ? "selected" : "" %>><%=datosAnios[0]%></option>
				                				<%
				                			}
				                			 %> 
                						</select>
                					</td>
              					</tr>
              					<tr class="fila-det">
					                <td width="12%" class="fila-det-border">&nbsp;Mes de liquidación: (*) </td>
					                <td width="88%" class="fila-det-border">&nbsp;<select name = "mesliq">
					                			<option value="-1" >Seleccionar</option>
					                			 <option value="01"  <%= BRF.getMesliq().longValue() == new BigDecimal(1).longValue() ? "Selected" : "" %>>Enero</option>
					                			 <option value="02"  <%= BRF.getMesliq().longValue() == new BigDecimal(2).longValue() ? "Selected" : "" %>>Febrero</option>
					                			 <option value="03"  <%= BRF.getMesliq().longValue() == new BigDecimal(3).longValue() ? "Selected" : "" %>>Marzo</option>
					                			 <option value="04"  <%= BRF.getMesliq().longValue() == new BigDecimal(4).longValue() ? "Selected" : "" %>>Abril</option>
					                			 <option value="05"  <%= BRF.getMesliq().longValue() == new BigDecimal(5).longValue() ? "Selected" : "" %>>Mayo</option>
					                			 <option value="06"  <%= BRF.getMesliq().longValue() == new BigDecimal(6).longValue() ? "Selected" : "" %>>Junio</option>
					                			 <option value="07"  <%= BRF.getMesliq().longValue() == new BigDecimal(7).longValue() ? "Selected" : "" %>>Julio</option>
					                			 <option value="08"  <%= BRF.getMesliq().longValue() == new BigDecimal(8).longValue() ? "Selected" : "" %>>Agosto</option>
					                			 <option value="09"  <%= BRF.getMesliq().longValue() == new BigDecimal(9).longValue() ? "Selected" : "" %>>Septiembre</option>
					                			 <option value="10" <%= BRF.getMesliq().longValue() == new BigDecimal(10).longValue() ? "Selected" : "" %>>Octubre</option>
					                			 <option value="11" <%= BRF.getMesliq().longValue() == new BigDecimal(11).longValue() ? "Selected" : "" %>>Noviembre</option>
					                			 <option value="12" <%= BRF.getMesliq().longValue() == new BigDecimal(12).longValue() ? "Selected" : "" %>>Diciembre</option>
					                	</select>
					                </td>
              					</tr>
              					<tr class="fila-det">
              						<td width="12%" class="fila-det-border">&nbsp;Jornada: (*) </td>
              						<td width="88%" class="fila-det-border">&nbsp;
					                		<select name = "nroquincena">
						                		<option value="-1">Seleccionar</option>
						                		<option value="1" <%=BRF.getNroquincena().longValue() == new BigDecimal(1).longValue() ? "Selected": "" %>>Quincena</option>
						                		<option value="2" <%=BRF.getNroquincena().longValue() == new BigDecimal(2).longValue() ? "Selected": "" %>>Mes completo</option>
						                	</select>
					                </td> 
              					</tr>
              					<tr class="fila-det">
              						<td width="12%" class="fila-det-border">&nbsp;Categoría:  </td>
                					<td width="88%" class="fila-det-border">&nbsp;<input name="idcategoria" type="hidden" value="<%=BRF.getIdcategoria()%>">
                						<input name="categoria" type="text" value="<%=BRF.getCategoria()%>" class="campo" size="25" maxlength="100">
                					</td>
              					</tr>
              					<tr class="fila-det">
                					<td width="12%" class="fila-det-border">&nbsp;Localidad de pago:  </td>
                					<td width="88%" class="fila-det-border">&nbsp;<input name="idlocalidadpago" type="hidden" value="<%=BRF.getIdlocalidadpago()%>">
                						<input name="localidadpago" type="text" readonly="readonly" value="<%=BRF.getLocalidadpago()%>" class="campo" size="25" maxlength="100"  >
                					</td>
              					</tr>
              				<tr class="fila-det">
                				<td width="12%" class="fila-det-border">&nbsp;Banco de depósito:  </td>
                				<td width="88%" class="fila-det-border">&nbsp;<input name="idbancodeposito" type="hidden" value="<%=BRF.getIdbancodeposito()%>" >
                					<input name="bancodeposito" type="text" readonly="readonly" value="<%=BRF.getBancodeposito()%>" class="campo" size="25" maxlength="100"  >
                				</td>
              				</tr>
              				<tr class="fila-det">
                				<td width="12%" class="fila-det-border">&nbsp;Modalidad del Contrato:  </td>
                				<td width="88%" class="fila-det-border">&nbsp;<input name="idmodalidadcontrato" type="hidden" value="<%=BRF.getIdmodalidadcontrato()%>">
                					<input name="modalidadcontrato" type="text" readonly="readonly" value="<%=BRF.getModalidadcontrato()%>" class="campo" size="25" maxlength="100"  >
                				</td>
              				</tr>
              				<tr class="fila-det">
                				<td width="12%" class="fila-det-border">&nbsp;Fecha de depósito: (*) </td>
                				<td colspan="2" class="fila-det-border">&nbsp;<input class="cal-TextBox" onFocus="this.blur()" size="12" readonly="readonly" type="text" name="fechadeposito" value="<%=BRF.getFechadeposito()%>" maxlength="12">
               						<a class="so-BtnLink" href="javascript:calClick();return false;"
               							onmouseover="calSwapImg('BTN_date_12', 'img_Date_OVER',true); "
               							onmouseout="calSwapImg('BTN_date_12', 'img_Date_UP',true);"
               							onclick="calSwapImg('BTN_date_12', 'img_Date_DOWN');showCalendar('frm','fechadeposito','BTN_date_12');return false;">
               							<img align="absmiddle" border="0" name="BTN_date_12" src="vs/calendar/btn_date_up.gif" width="22" height="17">
               						</a>
                				</td>
              				</tr>
              				<tr class="fila-det">
                				<td width="12%" class="fila-det-border">&nbsp;Importe: (*) </td>
                				<td width="88%" class="fila-det-border">&nbsp;<input name="importesueldo" type="text" value="<%=BRF.getImportesueldo()%>" class="campo" size="100" maxlength="100"  ></td>
              				</tr>
              				<tr class="fila-det">
                				<td width="12%" class="fila-det-border">&nbsp;Total Remunerativo: (*) </td>
                				<td width="88%" class="fila-det-border">&nbsp;<input name="totalremunerativo" type="text" value="<%=BRF.getTotalremunerativo()%>" class="campo" size="100" maxlength="100"  ></td>
              				</tr>
              				<tr class="fila-det">
                				<td width="12%" class="fila-det-border">&nbsp;Total no Remunerativo: (*) </td>
                				<td width="88%" class="fila-det-border">&nbsp;<input name="totalnoremunerativo" type="text" value="<%=BRF.getTotalnoremunerativo()%>" class="campo" size="100" maxlength="100"  >
                				</td>
              				</tr>
              				<tr class="fila-det">
                				<td width="12%" class="fila-det-border">&nbsp;Tota Descuentos: (*) </td>
                				<td width="88%" class="fila-det-border">&nbsp;<input name="totaldescuentos" type="text" value="<%=BRF.getTotaldescuentos()%>" class="campo" size="100" maxlength="100"  >
                				</td>
              				</tr>
              				<tr class="fila-det">
                				<td width="12%" class="fila-det-border">&nbsp;Neto a cobrar: (*) </td>
                				<td width="88%" class="fila-det-border">&nbsp;<input name="netoacobrar" type="text" value="<%=BRF.getNetoacobrar()%>" class="campo" size="100" maxlength="100"  >
                				</td>
              				</tr>
              				<tr class="fila-det">
                				<td class="fila-det-border">&nbsp;</td>
                				<td class="fila-det-border">&nbsp;
                					<input name="validar" type="submit" value="Enviar" class="boton">               
                					<input name="volver" type="submit" class="boton" id="volver" value="Volver">
                				</td>
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

