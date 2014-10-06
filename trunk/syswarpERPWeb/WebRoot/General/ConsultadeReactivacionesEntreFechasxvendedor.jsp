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
		class="ar.com.syswarp.web.ejb.BeanConsultaReactivacionesEntreFechasxVendedor" scope="page" />
	<head>
		<title>Consuta de Movimientos por Articulo y Deposito</title>
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
<link rel = "stylesheet" href = "../imagenes/default/erp-style.css">
		 
		<meta http-equiv="Content-Type"	content="text/html; charset=iso-8859-1">
	</head>
	<BODY>
		<div id="popupcalendar" class="text"></div>
		<%-- EJECUTAR SETEO DE PROPIEDADES --%>
		<jsp:setProperty name="BPF" property="*" />
		<% 
 String titulo = "Total de Reactivaciones por vendedor";
	
		
		
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
		<form action="ConsultadeReactivacionesEntreFechasxvendedor.jsp" method="post" name="frm">
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
								<td class="fila-det-border">&nbsp;								</td>
								<td colspan="3" class="fila-det-border">
									<jsp:getProperty name="BPF" property="mensaje" />
&nbsp;																								</td>
							</tr>
<tr class="fila-det">
								<td width="11%" class="fila-det-border">
									Fecha Desde </td>
								<td width="26%" class="fila-det-border"><table width="88%" border="0">
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
          </table></td>
							  
							  
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
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable"   >
  <tr class="fila-encabezado">
     <td width="75%">Vendedor</td>  
     <td><div align="center">Activaci&oacute;n</div></td>
     <td><div align="center">Cantidad</div></td>
  </tr> 
  <% 
   BigDecimal totalno = new BigDecimal(0); 
   BigDecimal totalsi = new BigDecimal(0); 
   BigDecimal totalgeneral = new BigDecimal(0); 
   String vendedor = "";
   int r = 0;
   while(iterConsulta1.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterConsulta1.next(); 
	    totalgeneral = totalgeneral.add(new BigDecimal( sCampos[1]) ); 
      totalno = sCampos[2].equalsIgnoreCase("NO") ? totalno.add(new BigDecimal( sCampos[1]) ) : totalno.add(new BigDecimal( 0 ) ); 
      totalsi = sCampos[2].equalsIgnoreCase("SI") ? totalsi.add(new BigDecimal( sCampos[1]) ) : totalsi.add(new BigDecimal( 0 ) ); 
      String imagen =""; 

      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";
      if(!vendedor.equalsIgnoreCase(sCampos[0])) {    

   %>
   <tr class="permiso-tres" >
     <td height="3" colspan="3" class="text-catorce" ></td>
   </tr>
      <%
        vendedor = sCampos[0];
      }
    %>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td height="22" class="fila-det-border" >&nbsp;<%=sCampos[0]%></td>
      <td width="15%" class="fila-det-border" ><div align="center"><%=sCampos[2]%></div></td>
      <td width="10%" class="fila-det-border" ><div align="right"><%=sCampos[1]%></div></td>
   </tr>
   <%
   }%>
   <tr class="fila-encabezado">
     <td><div align="right">Total NO </div></td>
     <td><div align="right"></div></td>
     <td><div align="right"><span class="fila-det-border"><%=totalno.toString()%></span></div></td>

   </tr>
   <tr class="fila-encabezado">
     <td><div align="right">Total SI </div></td>
     <td><div align="right"></div></td>
     <td><div align="right"><span class="fila-det-border"><%=totalsi.toString()%></span></div></td>

   </tr>
   <tr class="fila-encabezado">
     <td width="75%"><div align="right">Total General </div></td>  
     <td><div align="center"></div></td>
     <td><div align="right"><span class="fila-det-border"><%=totalgeneral.toString()%></span></div></td>
	 
  </tr>
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

