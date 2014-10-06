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
		class="ar.com.syswarp.web.ejb.BeanStockConsultaMovimientosArticuloDeposito" scope="page" />
	<head>
		<title>Consuta de Movimientos por Articulo y Deposito</title>
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
 String titulo = "Consulta de Movimientos por Articulo y por Deposito.";
	
		
		
 BPF.setResponse(response);
 BPF.setRequest(request);
 // ver esto BPF.setSession(session);
 BPF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BPF.ejecutarValidacion();
 java.util.List Consulta1 = new java.util.ArrayList();
 java.util.List Consulta2 = new java.util.ArrayList();
 java.util.List Consulta3 = new java.util.ArrayList();
 Consulta1 = BPF.getSaldoAnteriorList();
 Consulta2 = BPF.getMovimientosList();
 Consulta3 = BPF.getSaldoFinalList();
 
 iterConsulta1 = Consulta1.iterator();
 iterConsulta2 = Consulta2.iterator();
 iterConsulta3 = Consulta3.iterator();
 %>
		<form action="stockConsultaMovimientosArticuloDeposito.jsp" method="post" name="frm">
			<input name="accion" type="hidden" value="consulta">
			

						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							align="center">
							<tr class="text-globales">
								<td>
									<table width="100%" border="0"  cellpadding="0"
										cellspacing="0" class="text-globales">
										<tr>
											<td width="367"><%= titulo %>&nbsp;</td>
											<td width="50">&nbsp;</td>
											<td width="43"></td>
											<td width="42"></td>
										</tr>
								  </table>
									
								</td>
							</tr>
					  </table>
					  
					  
					  
					  
					  
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr class="fila-det-bold-rojo">
								<td class="fila-det-border">&nbsp;</td>
								<td colspan="3" class="fila-det-border"><jsp:getProperty name="BPF" property="mensaje" /></td>
							</tr>
							<tr class="fila-det">
								<td width="11%" class="fila-det-border">
									Articulo: (*)								
								</td>
								<td width="37%" class="fila-det-border">
								<table width="79%" border="0">
								  <tr class="fila-det-border">
									<td width="61%">
												<input name="descrip_st" type="text" class="campo"
													id="descrip_st" value="<%=BPF.getDescrip_st()%>" size="30"
												readonly>											
									</td>
									<td width="39%">
												<img src="../imagenes/default/gnome_tango/actions/filefind.png"
													width="22" height="22"
													onClick="mostrarLOV('../Stock/lov_articulo_informe.jsp')"
													style="cursor:pointer">
													<input name="codigo_st" type="hidden" id="codigo_st" value="<%=BPF.getCodigo_st() %>">
									</td>
								 </tr>
							   </table>								
							</td>
							<td width="13%" class="fila-det-border">
									Fecha Desde (*)								
							</td>
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
								<td width="11%" class="fila-det-border">
									Deposito: (*)								
								</td>
								<td width="37%" class="fila-det-border">
									<table width="79%" border="0">
										<tr class="fila-det-border">
											<td width="61%">
												<input name="deposito" type="text" class="campo"
													id="deposito" value="<%=BPF.getDeposito() %>" size="30"
													readonly>
											</td>
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
								<td width="13%" class="fila-det-border">
									Fecha Hasta (*)								
								</td>
								<td width="39%" class="fila-det-border">
									<table width="35%" border="0">
										<tr class="fila-det-border">
											<td width="50%">
												<input name="fechahasta" type="text" class="cal-TextBox"
													id="fechahasta" onFocus="this.blur()"
													value="<%=BPF.getFechahasta() %>" size="12"
													maxlength="12" readonly>											
											</td>
											<td width="50%">
												<a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onmouseover="calSwapImg('BTN_date_2', 'img_Date_OVER',true); "
													onmouseout="calSwapImg('BTN_date_2', 'img_Date_UP',true);"
													onclick="calSwapImg('BTN_date_2', 'img_Date_DOWN');showCalendar('frm','fechahasta','BTN_date_2');return false;"><img
														src="vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_2" width="22" height="17" border="0"
														align="absmiddle">	
																									</a>											
									        </td>
										</tr>
							         </table>								
							     </td>
					 
							
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
    
     <td width="25%">Concepto</td>
     <td width="75%">Valor</td>
   </tr>
   <%int r = 0;
   while(iterConsulta1.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterConsulta1.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
      
      <td class="fila-det-border" >&nbsp;<%=sCampos[0]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[1]%></td>
   </tr>
<%
   }%>
   </table>
 <!-- detalle -->  
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable"   >
  <tr class="fila-encabezado">  
     <td width="5%">&nbsp;</td>   
     <td width="10%">Fecha</td>
     <td width="15%"><div align="right">Cantidad</div></td>
     <td width="35%">Tipo</td>
     <td width="55%">Usuario Responsable</td>
		 <td width="10%">Comprobante</td>
   </tr>
  <%r = 0;
   while(iterConsulta2.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterConsulta2.next(); 
      // estos campos hay que setearlos segun la grilla 
      String imagen ="";
      
      if(sCampos[2].equalsIgnoreCase("entrada"))imagen = "back.png"; else imagen = "next.png";
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" >
          <img src="../imagenes/default/gnome_tango/actions/<%=imagen%>" onMouseOver="mostrarMasInfo('Observaciones: <%=Common.setNotNull(sCampos[5])%>')"  width="22" height="22">      </td>
      
      <td class="fila-det-border" >&nbsp;<%=sCampos[0]%></td>      
      <td class="fila-det-border" ><div align="right">&nbsp;<%=sCampos[1]%></div></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[2]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[3]%></td>
			<td class="fila-det-border" >&nbsp;<%=sCampos[4]%></td>
   </tr>
   <%
   }%>
</table>
  <!-- final -->  
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable"   >
  <tr class="fila-encabezado">     
      <td width="22%">Concepto</td>
      <td width="75%">Valor</td>
   </tr>
  <%r = 0;
   while(iterConsulta3.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterConsulta3.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" >&nbsp;<%=sCampos[0]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[1]%></td>
   </tr>
   <%
   }%>
   
<%}%>   
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

