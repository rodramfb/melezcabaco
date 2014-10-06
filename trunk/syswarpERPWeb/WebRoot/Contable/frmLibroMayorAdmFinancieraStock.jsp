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
Iterator iterConsulta2   = null;

Strings str = new Strings();
String color_fondo ="";
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
String idejercicio = session.getAttribute("ejercicioActivo").toString();

%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<%-- INSTANCIAR BEAN --%>
	<jsp:useBean id="BPF"
		class="ar.com.syswarp.web.ejb.BeanStockLibroMayorBienesdecambio" scope="page" />
	<head>
		<title>Consulta - Libro Mayor</title>
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
	<BODY>
		<div id="popupcalendar" class="text"></div>
		<%-- EJECUTAR SETEO DE PROPIEDADES --%>
		<jsp:setProperty name="BPF" property="*" />
		<% 
 String titulo = "Consulta - Libro Mayor de Bienes de Cambio(Sub Contabilidad)";
	
		
		
 BPF.setResponse(response);
 BPF.setRequest(request);
 // ver esto BPF.setSession(session);
 BPF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BPF.setIdejercicio(new BigDecimal(idejercicio));
 BPF.ejecutarValidacion();
 java.util.List Consulta2 = new java.util.ArrayList();
 Consulta2 = BPF.getMovimientosList();
 iterConsulta2 = Consulta2.iterator();
 %>
		<form action="frmLibroMayorAdmFinancieraStock.jsp" method="post" name="frm">
			<input name="accion" type="hidden" value="consulta">
			
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				align="center">
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							align="center">
							<tr class="text-globales">
								<td>
									<table width="100%" border="0"  cellpadding="0"
										cellspacing="0" class="text-globales">
										<tr>
											<td width="367"><%= titulo %>&nbsp;</td>
									  </td>
											<td width="50">&nbsp;											</td>
											<td width="43">
												
										  </td>
											<td width="42">
										
										  </td>
										</tr>
								  </table>
									
						  
							
					  </table>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr class="fila-det-bold-rojo">
								<td class="fila-det-border">&nbsp;								</td>
								<td class="fila-det-border">
									<jsp:getProperty name="BPF" property="mensaje" />
&nbsp;																								</td>
							</tr>
							<tr class="fila-det">
                <td class="fila-det-border"> Cuenta Contable Desde: (*)                </td>
							  <td class="fila-det-border"><table width="79%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="fila-det-border">
                      <td width="56%"><input name="idcuenta_desde" type="text" id="idcuenta_desde" class="campo"
												value="<%=BPF.getIdcuenta_desde() %>" size="12" maxlength="12" readonly>
                      <input name="cuenta_desde" type="text" class="campo"
													id="cuenta_desde" value="<%= BPF.getCuenta_desde() %>" size="30"
													readonly>                      </td>
                      <td width="44%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" 
													width="22" height="22"
													style="cursor:pointer"
													onClick="abrirVentana('../Clientes/lov_contableInfiPlan.jsp?campos=idcuenta_desde|cuenta_desde', 'cuentas', 800, 450)"></td>
                    </tr>
                </table></td>
						  </tr>
							<tr class="fila-det">
							  <td class="fila-det-border">Cuenta Contable Hasta: (*) </td>
							  <td class="fila-det-border"><table width="79%" border="0" cellpadding="0" cellspacing="0">
                  <tr class="fila-det-border">
                    <td width="56%"><input name="idcuenta_hasta" type="text" id="idcuenta_hasta" class="campo"
												value="<%=BPF.getIdcuenta_hasta() %>" size="12" maxlength="12" readonly>
                        <input name="cuenta_hasta" type="text" class="campo"
													id="cuenta_hasta" value="<%= BPF.getCuenta_hasta() %>" size="30"
													readonly>                    </td>
                    <td width="44%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" 
													width="22" height="22"
													style="cursor:pointer"
													onClick="abrirVentana('../Clientes/lov_contableInfiPlan.jsp?campos=idcuenta_hasta|cuenta_hasta', 'cuentas', 800, 450)"> </td>
                  </tr>
                </table></td>
						  </tr>
							<tr class="fila-det">
							  <td class="fila-det-border">Fecha Desde:(*) </td>
							  <td class="fila-det-border"><input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fecha_desde" value="<%= BPF.getFecha_desde()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;" 
					 onmouseover="calSwapImg('BTN_date_0', 'img_Date_OVER',true);" 
					 onmouseout="calSwapImg('BTN_date_0', 'img_Date_UP',true);" 
					 onclick="calSwapImg('BTN_date_0', 'img_Date_DOWN');showCalendar('frm','fecha_desde','BTN_date_0');return false;"> <img align="absmiddle" border="0" name="BTN_date_0" src="vs/calendar/btn_date_up.gif" width="22" height="17"> </a></td>
						  </tr>
							<tr class="fila-det">
							  <td class="fila-det-border">Fecha Hasta:(*) </td>
							  <td class="fila-det-border"><input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fecha_hasta" value="<%= BPF.getFecha_hasta()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;" 
					 onmouseover="calSwapImg('BTN_date_1', 'img_Date_OVER',true);" 
					 onmouseout="calSwapImg('BTN_date_1', 'img_Date_UP',true);" 
					 onclick="calSwapImg('BTN_date_1', 'img_Date_DOWN');showCalendar('frm','fecha_hasta','BTN_date_1');return false;"> <img align="absmiddle" border="0" name="BTN_date_1" src="vs/calendar/btn_date_up.gif" width="22" height="17"> </a> </td>
							</tr>
							<tr class="fila-det">
								<td width="19%" class="fila-det-border">							  </td>
								<td width="81%" class="fila-det-border">							  </td>
							  
							  
					
							
							
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr class="fila-det">
							  
									
									<td width="13%">
									 <input name="validar"
											type="submit" value="Ejecutar Consulta" class="boton">	  							</td>
							</tr>
						</table>
		  </table>
			<input name="primeraCarga" type="hidden" value="false" >
		</form>

<%if (BPF.getAccion().equalsIgnoreCase("consulta")){%>		
 
 
 
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable"   >
  <tr class="fila-encabezado">  
       
     <td width="5%"><div align="center">Fecha</div></td>
     <td width="5%"><div align="right">Nro.Asiento</div></td>
     <!--td width="5%"><div align="right">dsdsaRenglon</div></td-->     
     <td width="15%"><div align="right">Debe</div></td>
     <td width="15%"><div align="right">Haber</div></td>
     <td width="15%"><div align="right">Acumulado</div></td>
	 <td width="40%">Detalle</td> 
  </tr>
 
  <%int r = 0;
   String cuenta = "";

   BigDecimal subTotalDebe = new BigDecimal(0);
   BigDecimal subTotalHaber = new BigDecimal(0);
   BigDecimal subTotalAcumulado =new BigDecimal(0);

   BigDecimal totalDebe = new BigDecimal(0);
   BigDecimal totalHaber = new BigDecimal(0);

   while(iterConsulta2.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterConsulta2.next(); 
      // estos campos hay que setearlos segun la grilla 
      String imagen ="";
      if(sCampos[4].equalsIgnoreCase("entrada"))imagen = "back.png"; else imagen = "next.png";
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";

		 totalDebe=totalDebe.add(new BigDecimal(sCampos[9]));
		 totalHaber= totalHaber.add(new BigDecimal(sCampos[10]));

      if(!cuenta.equalsIgnoreCase(sCampos[0])){
	       if(!cuenta.equalsIgnoreCase("")){

					%>
				
				 <tr class="permiso-dos" > 
						<td colspan="2" class="text-cinco"><div align="right"></div></td>   
						<td class="text-cinco" ><div align="right">&nbsp;<%=subTotalDebe%></div></td>     
						<td class="text-cinco" ><div align="right">&nbsp;<%=subTotalHaber%></div></td>
						<td class="text-cinco" ><div align="right">&nbsp;<%=subTotalAcumulado%></div></td>
						<td >&nbsp;</td>      
				 </tr>
				
				<%
	       } 
          
					subTotalDebe=new BigDecimal(0);
					subTotalHaber=new BigDecimal(0);
					subTotalAcumulado=new BigDecimal(0);

          cuenta = sCampos[0];
          String detalle = "SALDO INICIAL";
          String saldoinicial =  sCampos[13].equalsIgnoreCase("SALDOINICIAL") ? sCampos[15] : "0.00";
         %>
			 <tr class="text-dos-bold"  > 
					<td colspan="6">CUENTA: <%= sCampos[0] + " - " + sCampos[1] %>&nbsp;</td>
       </tr>
			 <tr > 
					<td colspan="5" class="text-cinco"><div align="right" >&nbsp;<%=saldoinicial%></div></td>
					<td class="text-cinco">&nbsp;<%=detalle%></td>      
			 </tr>
      <%

           if(sCampos[13].equalsIgnoreCase("SALDOINICIAL")){
					   subTotalDebe=subTotalDebe.add(new BigDecimal(sCampos[9]));
					   subTotalHaber= subTotalHaber.add(new BigDecimal(sCampos[10]));
					   subTotalAcumulado=(new BigDecimal(sCampos[15]));
             continue;
           } 
         }
      %> 
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border"><div align="center">&nbsp;<%=Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[6]), "JSDateToStr")%></div></td>   
      <td class="fila-det-border" ><div align="right">&nbsp;<%=sCampos[7]%></div></td>  
       <!--td class="fila-det-border"><div align="right">dsds&nbsp;<%=sCampos[8]%></div></td--> 
      <td class="fila-det-border"><div align="right">&nbsp;<%=sCampos[9]%></div></td>     
      <td class="fila-det-border"><div align="right">&nbsp;<%=sCampos[10]%></div></td>
      <td class="fila-det-border"><div align="right">&nbsp;<%=sCampos[15]%></div></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[13]%></td>      
   </tr>
   <%
			subTotalDebe=subTotalDebe.add(new BigDecimal(sCampos[9]));
			subTotalHaber= subTotalHaber.add(new BigDecimal(sCampos[10]));
			subTotalAcumulado=(new BigDecimal(sCampos[15]));

   }

   if(r!=0){
   %>
	 <tr class="permiso-dos" > 
			<td colspan="2" class="text-cinco"><div align="right"></div></td>   
			<td class="text-cinco"><div align="right">&nbsp;<%=subTotalDebe%></div></td>     
			<td class="text-cinco"><div align="right">&nbsp;<%=subTotalHaber%></div></td>
			<td class="text-cinco"><div align="right">&nbsp;<%=subTotalAcumulado%></div></td>
			<td >&nbsp;</td>      
	 </tr>
	 <tr class="permiso-uno" height="3"> 
		 <td colspan="6" class="permiso-uno" height="3"></td>   
  </tr>
	 <tr class="permiso-dos" > 
		 <td colspan="2" class="text-cinco"><div align="right" >TOTALES</div></td>   
			<td class="text-cinco"><div align="right">&nbsp;<%=totalDebe%></div></td>     
		 <td class="text-cinco"><div align="right">&nbsp;<%=totalHaber%></div></td>
		 <td class="text-cinco"><div align="right">&nbsp;<%=totalDebe.subtract(totalHaber)%></div></td>
			<td >&nbsp;</td>      
	 </tr>
  <%
   }  %> 
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

