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
Iterator iterConsulta2   = null;
Iterator iterConsulta3   = null;

Strings str = new Strings();
String color_fondo ="";
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<%-- INSTANCIAR BEAN --%>
	<jsp:useBean id="BPF"
		class="ar.com.syswarp.web.ejb.BeanStockConsultaMovimientosDeposito" scope="page" />
	<head>
		<title>Consuta de Movimientos por Deposito</title>
		 <link rel = "stylesheet" href = "<%= pathskin %>">
		<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
		<link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
		<script language="JavaScript" src="vs/forms/forms.js"></script>
		<script language="JavaScript" src="../vs/scripts/overlib.js"></script>
		
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
		<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>		
		<%-- EJECUTAR SETEO DE PROPIEDADES --%>
		<jsp:setProperty name="BPF" property="*" />
		<% 
 String titulo = "Consulta de Movimientos por Deposito.";
	
		
		
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
		<form action="stockConsultaMovimientosDeposito.jsp" method="post" name="frm">
			<input name="accion" type="hidden" value="consulta">
			

						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							align="center">
							<tr class="text-globales">
								<td>
									<table width="100%" border="0"  cellpadding="0"
										cellspacing="0" class="text-globales">
										<tr>
											<td width="367" class="fila-det-border"><%= titulo %>&nbsp;</td>
											<td width="50" class="fila-det-border">&nbsp;/td>
											<td width="43" class="fila-det-border">&nbsp; </td>
											<td width="42" class="fila-det-border">&nbsp;</td>
										</tr>
								  </table>
									
								</td>
							</tr>
					  </table>
					  <table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr class="fila-det-bold-rojo">
								<td >&nbsp;</td>
								<td colspan="3" class="fila-det-border"><jsp:getProperty name="BPF" property="mensaje" /></td>
							</tr>
							<tr class="fila-det">
								<td width="11%" class="fila-det-border">Deposito: (*)</td>
								<td width="37%" class="fila-det-border">
									<table width="79%" border="0">
										<tr class="fila-det-border">
											<td width="61%"><input name="deposito" type="text" class="campo" id="deposito" value="<%=BPF.getDeposito() %>" size="30" readonly>											                                            </td>
											<td width="39%"> 
												<img src="../imagenes/default/gnome_tango/actions/filefind.png"
													width="22" height="22"
													onClick="mostrarLOV('../Stock/lov_deposito_informe.jsp')"
													style="cursor:pointer">											
                                                <input name="iddeposito" type="hidden" id="iddeposito" value="<%=BPF.getIddeposito() %>">													
										  </td>
										</tr>
							  </table>								
							  </td>
 							  <td width="13%" class="fila-det-border">Fecha Desde (*)</td>
								<td width="39%" class="fila-det-border">
									<table width="35%" border="0">
										<tr class="fila-det-border">
											<td width="50%">
												<input name="fechadesde" type="text" class="cal-TextBox"
													id="fechadesde" onFocus="this.blur()"
													value="<%=BPF.getFechadesde() %>" size="12"
													maxlength="12" readonly>
											</td>
											<td width="50%">
												<a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onmouseover="calSwapImg('BTN_date_6', 'img_Date_OVER',true); "
													onmouseout="calSwapImg('BTN_date_6', 'img_Date_UP',true);"
													onclick="calSwapImg('BTN_date_6', 'img_Date_DOWN');showCalendar('frm','fechadesde','BTN_date_6');return false;"><img
														src="vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_6" width="22" height="17" border="0"
														align="absmiddle">	
																									</a>											
											</td>
										</tr>
							        </table>								
							  </td>
							</tr>
							<tr class="fila-det">
								<td width="11%" class="fila-det-border">&nbsp;
								</td>
								<td width="13%" class="fila-det-border">
									Fecha Hasta (*)								</td>
								<td width="39%" class="fila-det-border">
									<table width="35%" border="0">
										<tr >
											<td width="50%" >
												<input name="fechahasta" type="text" class="cal-TextBox"
													id="fechahasta" onFocus="this.blur()"
													value="<%=BPF.getFechahasta() %>" size="12"
													maxlength="12" readonly>											</td>
											<td width="50%" >
												<a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onmouseover="calSwapImg('BTN_date_2', 'img_Date_OVER',true); "
													onmouseout="calSwapImg('BTN_date_2', 'img_Date_UP',true);"
													onclick="calSwapImg('BTN_date_2', 'img_Date_DOWN');showCalendar('frm','fechahasta','BTN_date_2');return false;"><img
														src="vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_2" width="22" height="17" border="0"
														align="absmiddle">	
																									</a>											</td>
											
										</tr>
							  </table
						></td>
							
						<td class="fila-det-border">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr class="fila-det">
							  
									
									<td width="13%">
									 <input name="validar"
											type="submit" value="Consultar Movimientos" class="boton">
									
								</td>
							</tr>
						</table>
						
					</td>
				</tr>
		  </table>
		  
		  
		  
		  
			<input name="primeraCarga" type="hidden" value="false" >
		</form>

<%if (BPF.getAccion().equalsIgnoreCase("consulta")){%>		
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable"   >
  <tr class="fila-encabezado">
     <td width="5%">&nbsp;</td>  
     <td width="10%">Fecha</td>     
     <td width="10%">Tipo</td>
     <td width="10%">C.Articulo</td>
     <td width="35%">Articulo</td>
     <td width="10%">Cantidad</td>
     <td width="10%">U.Responsable</td>
		 <td width="10%">Comprobante</td>
     
   </tr>
   <%int r = 0;
   while(iterConsulta1.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterConsulta1.next(); 
       String imagen ="";      
      if(sCampos[1].equalsIgnoreCase("entrada"))imagen = "back.png"; else imagen = "next.png";
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
      <td class="fila-det-border" >
          <img src="../imagenes/default/gnome_tango/actions/<%=imagen%>" onMouseOver="mostrarMasInfo('Observaciones: <%=Common.setNotNull(sCampos[7])%>')"   width="22" height="22">      </td>   
      <td class="fila-det-border" >&nbsp;<%=sCampos[0]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[1]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[2]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[3]%></td>
      <td class="fila-det-border" ><div align="right">&nbsp;<%=sCampos[4]%></div></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[5].toUpperCase() %></td>
			<td class="fila-det-border" >&nbsp;<%=sCampos[6]%></td>
      
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

