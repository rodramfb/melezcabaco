<%@ page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
%>
<%@ page import="javax.servlet.http.*"%>
<%@ page import="ar.com.syswarp.api.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@ page import="java.util.Iterator" %> 
<%@ include file="session.jspf"%>
<%@ page import="java.math.BigDecimal" %>
<%  
try {
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <%--  
  INSTANCIAR BEAN
	--%>  
  <jsp:useBean id="BUF"  class="ar.com.syswarp.web.ejb.BeanConsultaEstadosActualModFrm" scope="page"/>
	<head>
		<title>Solicitud de Reactivacion</title>
		 <link rel = "stylesheet" href = "<%= pathskin %>">
		<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
		<link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
		<script language="JavaScript" src="vs/forms/forms.js"></script>
		<script language="JavaScript" src="vs/overlib/overlib.js"></script>
		
		<script>
		function mostrarLOVDETA(pagina) {
	     frmLOV = open(pagina,'winLOV','scrollbars=yes,resizable=yes,width=800,height=450,status=yes');
	     if (frmLOV.opener == null) 
		 frmLOV.opener = self;
         }	
		 </script>
		 
		<meta http-equiv="Content-Type"	content="text/html; charset=iso-8859-1">
	</head>
  <body>
  <%--  
  EJECUTAR SETEO DE PROPIEDADES
	--%>
	
  <div id="popupcalendar" class="text"></div>	
  <jsp:setProperty name="BUF" property="*" />  	

  <%
	String titulo = BUF.getAccion().toUpperCase() + " DE Solicitud de Reactivacion";  
    BUF.setResponse(response);    
	BUF.setRequest(request); 	
	BUF.setUsuarioalt( session.getAttribute("usuario").toString() );
	BUF.setUsuarioact( session.getAttribute("usuario").toString() );
	BUF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
    BUF.ejecutarValidacion();
	%>

	<form action="consestadoactualModFrm.jsp" method="post" name="frm">
	<input name="accion" type="hidden" value="<%=BUF.getAccion()%>" > 
	<input name="idreactivacion" type="hidden" value="<%=BUF.getIdreactivacion()%>" >	
<table width="90%"  border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td>
			<table width="100%"  border="0" cellspacing="0" cellpadding="0">
				<tr class="text-globales">
					<td  class="text-globales"><%=BUF.getIdcliente() + " - " + BUF.getRazon() %></td>
				</tr>
			</table>
			<table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr  class="fila-det-bold-rojo">
				<td  class="fila-det-border">&nbsp;</td>
				<td  class="fila-det-border">&nbsp;
					<jsp:getProperty name="BUF" property="mensaje"/></td>
			</tr>
			<tr class="fila-det">
				<td width="21%" class="fila-det-border">Cliente</td>
				<td width="79%" class="fila-det-border">&lt;%=BUF.getVendedor()%&gt;</td>
			</tr>	
			<tr class="fila-det">
				<td width="21%" class="fila-det-border">Porcentaje (*) </td>
				<td width="79%" class="fila-det-border"><select name="porcentaje" id="porcentaje"  class="campo"  >
                  <option value="0"  >Seleccionar</option>
                  <option value="50" <%= BUF.getPorcentaje().intValue()== 50   ? "selected" : "" %> >50</option>
                  <option value="100" <%= BUF.getPorcentaje().intValue()== 100 ? "selected" : "" %> >100</option>
              </select></td>
			</tr>
			<tr  class="fila-det">                                                                     
				<td width="21%" class="fila-det-border">Fecha de Reactivacion (*) </td>
			    <td width="79%" class="fila-det-border"><table width="17%" border="0">
                  <tr class="fila-det-border">
                    <td width="13%"><input name="fechareactivacionStr" type="text" class="cal-TextBox"
													id="fechareactivacionStr" onFocus="this.blur()"
													value="<%=BUF.getFechareactivacionStr() %>" size="12"
													maxlength="12" readonly>                    </td>
                    <td width="87%"><a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onmouseover="calSwapImg('BTN_date_6', 'img_Date_OVER',true); "
													onmouseout="calSwapImg('BTN_date_6', 'img_Date_UP',true);"
													onclick="calSwapImg('BTN_date_6', 'img_Date_DOWN');showCalendar('frm','fechareactivacionStr','BTN_date_6');return false;"><img
														src="vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_6" width="22" height="17" border="0"
														align="absmiddle"> </a> </td>
                  </tr>
              </table></td>
			</tr>
			<tr  class="fila-det">
				<td width="21%" class="fila-det-border">Vendedor (*) </td>
				<input name="idvendedor" type="hidden" id="idvendedor" value="<%=BUF.getIdvendedor()%>">
				<td width="79%" class="fila-det-border"><table width="23%" border="0" cellpadding="0" cellspacing="0">
                  <tr class="fila-det-border">
                    <td width="61%" ><input name="vendedor" type="text" class="campo" id="vendedor" value="<%=BUF.getVendedor()%>" size="30" readonly></td>
                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_vendedor.jsp', 'tclie', 800, 450)" style="cursor:pointer"></td>
                  </tr>   
                </table></td>
			</tr>			
			<tr  class="fila-det">
				<td  class="fila-det-border">&nbsp;</td>
			  <td  class="fila-det-border"><input name="validar" type="submit" value="Enviar" class="boton">
		      <input name="volver" type="submit" class="boton" id="volver" value="Volver"></td>
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
  } 
%>