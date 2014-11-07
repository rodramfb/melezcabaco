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

Strings str = new Strings();
String color_fondo ="";
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<%-- INSTANCIAR BEAN --%>
	<jsp:useBean id="BPF"
		class="ar.com.syswarp.web.ejb.BeanConsultaSASIngresodeFichas" scope="page" />
	<head>
		<title>Consuta de Movimientos por Articulo y Deposito</title>
		 <link rel = "stylesheet" href = "<%= pathskin %>">
<link rel = "stylesheet" href = "../imagenes/default/erp-style.css">

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
	<BODY>
		<div id="popupcalendar" class="text"></div>
		<%-- EJECUTAR SETEO DE PROPIEDADES --%>
		<jsp:setProperty name="BPF" property="*" />
		<% 
 String titulo = "Consulta sas - Ingreso de fichas ";
	
		
		
 BPF.setResponse(response);
 BPF.setRequest(request);
 BPF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BPF.ejecutarValidacion();
 java.util.List Consulta1 = new java.util.ArrayList();
 Consulta1 = BPF.getMovimientosList();
 iterConsulta1 = Consulta1.iterator();
 %>
		<form action="Consultasasingresofichas.jsp" method="post" name="frm">
			<input name="accion" type="hidden" value="consulta">
			
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
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

						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr class="fila-det-bold-rojo">
								<td class="fila-det-border">&nbsp;</td>
								<td colspan="3" class="fila-det-border">
									<jsp:getProperty name="BPF" property="mensaje" />&nbsp;</td>
							</tr>
              <tr class="fila-det">
								<td width="11%" class="fila-det-border">Fecha Desde </td>
								<td width="26%" class="fila-det-border">
                <table width="88%" border="0">
                                  <tr class="fila-det-border">
                                    <td width="29%"><input name="fechadesde" type="text" class="cal-TextBox"
													id="fechadesde" onFocus="this.blur()"
													value="<%=BPF.getFechadesde() %>" size="12"
													maxlength="12" readonly>
                                    </td>
                                    <td width="71%"><a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onmouseover="calSwapImg('BTN_date_6', 'img_Date_OVER',true); "
													onmouseout="calSwapImg('BTN_date_6', 'img_Date_UP',true);"
													onclick="calSwapImg('BTN_date_6', 'img_Date_DOWN');showCalendar('frm','fechadesde','BTN_date_6');return false;"><img
														src="vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_6" width="22" height="17" border="0"
														align="absmiddle"> </a> </td>
                                  </tr>
                </table>
                </td>
								<td width="11%" class="fila-det-border">Fecha Hasta </td>
								<td width="52%" class="fila-det-border"><table width="19%" border="0">
                                  <tr class="fila-det-border">
                                    <td width="12%"><input name="fechahasta" type="text" class="cal-TextBox"
													id="fechahasta" onFocus="this.blur()"
													value="<%=BPF.getFechahasta() %>" size="12"
													maxlength="12" readonly>
                                    </td>
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
						  </tr><tr class="fila-det">
						    <td width="11%" height="46" class="fila-det-border">&nbsp;</td>
						    <td width="26%" class="fila-det-border"><input name="validar"
											type="submit" value="Consultar" class="boton"></td>
						    <td width="11%" class="fila-det-border">&nbsp;</td>
						    <td width="52%" class="fila-det-border">&nbsp;						</td>
		  </table>
			<input name="primeraCarga" type="hidden" value="false" >
		</form>

<%if (BPF.getAccion().equalsIgnoreCase("consulta")){%>		 
<table width="100%" border="0" cellspacing="2" cellpadding="1" name="rsTable"   >
  <tr class="fila-encabezado">
     <td colspan="2" bgcolor="#F0F0FF" ><div align="center">Prospecto</div></td>  
    <td colspan="5" bgcolor="#FFFFCC"><div align="center">Cliente</div></td>
    <td width="2%" rowspan="2">      </td> 
  </tr>
  <tr class="fila-encabezado">
    <td width="7%" bgcolor="#F0F0FF" ><div align="right">Nro.</div></td>
    <td width="7%" bgcolor="#F0F0FF"><div align="center">Fecha</div></td>
    <td width="8%" bgcolor="#FFFFCC"><div align="right">Nro. </div></td>
    <td width="47%" bgcolor="#FFFFCC">Apellido y Nombre</td>
    <td width="10%" bgcolor="#FFFFCC"><div align="center">Activaci&oacute;n</div></td>
    <td width="9%" bgcolor="#FFFFCC">Estado</td>
    <td width="10%" bgcolor="#FFFFCC">Tipo</td>
  </tr> 
   <%int r = 0;
   while(iterConsulta1.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterConsulta1.next(); 
       String imagen ="";      
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td  class="fila-det-border" ><div align="right"><%=Common.setNotNull(sCampos[0])%></div></td>
     <td class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[1]), "JSTsToStr")%></div></td>
     <td class="fila-det-border" ><div align="right"><%=Common.setNotNull(sCampos[2])%></div></td>
     <td class="fila-det-border" >&nbsp;<%=Common.setNotNull(sCampos[5])%></td>
     <td class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[3]), "JSTsToStr")%></div></td>
     <td class="fila-det-border" >&nbsp;<%=Common.setNotNull(sCampos[4])%></td>
     <td class="fila-det-border" ><%=Common.setNotNull(sCampos[7])%></td>
     <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/gnome-session.png" title="Hist&oacute;rico de pedidos" width="20" height="20" onClick="ventana=abrirVentana('../Clientes/pedidosHistoricoCliente.jsp?cliente=<%=Common.setNotNull(sCampos[5])%>&idcliente=<%=Common.setNotNull(sCampos[2])%>', 'hist', 800, 400)" style="cursor:pointer"></div></td>
   </tr>
   <%
   }%>
  </table>

   
<%}%>   
			
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

