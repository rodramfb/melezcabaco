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
		class="ar.com.syswarp.web.ejb.BeanConsultaEstadoentreFechas" scope="page" />
	<head>
		<title>Consuta de Estados</title>
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
 String titulo = "Consulta de Estados";
	
		
		
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
		<form action="ConsultadeEstadoentrefechas.jsp" method="post" name="frm">
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
                            <tr class="fila-det-bold">
                              <td height="30" class="fila-det-border">&nbsp;</td>
                              <td bgcolor="#FFFFCC" class="fila-det-border">Movimiento</td>
                              <td bgcolor="#FFFFCC" class="fila-det-border">&nbsp;</td>
                              <td bgcolor="#FFFFCC" class="fila-det-border">Alta</td>
                            </tr>
                            <tr class="fila-det">
                              <td height="30" class="fila-det-border">Criterio(*)</td>
                              <td bgcolor="#FFFFCC" class="fila-det-border"><label>
                                <input name="criterio" type="radio" value="M" <%= BPF.getCriterio().equalsIgnoreCase("M") ? "checked" : ""  %> >
                              </label></td>
                              <td bgcolor="#FFFFCC" class="fila-det-border">&nbsp;</td>
                              <td bgcolor="#FFFFCC" class="fila-det-border"><input name="criterio" type="radio" value="A" <%= BPF.getCriterio().equalsIgnoreCase("A") ? "checked" : ""  %> ></td>
                            </tr>
                          <tr class="fila-det">
								<td width="14%" class="fila-det-border">
									F.Desde(*)					         </td>
								<td width="33%" class="fila-det-border"><table width="49%" border="0">
                                  <tr class="fila-det-border">
                                    <td width="29%"><input name="fdesde" type="text" class="cal-TextBox"
													id="fdesde" onFocus="this.blur()"
													value="<%=BPF.getFdesde() %>" size="10"
													maxlength="10" readonly>                                    </td>
                                    <td width="71%"><a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onmouseover="calSwapImg('BTN_date_6', 'img_Date_OVER',true); "
													onmouseout="calSwapImg('BTN_date_6', 'img_Date_UP',true);"
													onclick="calSwapImg('BTN_date_6', 'img_Date_DOWN');showCalendar('frm','fdesde','BTN_date_6');return false;"><img
														src="vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_6" width="22" height="17" border="0"
														align="absmiddle"> </a> </td>
                                  </tr>
          </table></td>
							  
							  
								<td width="11%" class="fila-det-border">F.Hasta(*)					         </td>
								<td width="42%" class="fila-det-border"><table width="25%" border="0">
                                  <tr class="fila-det-border">
                                    <td width="12%"><input name="fhasta" type="text" class="cal-TextBox"
													id="fhasta" onFocus="this.blur()"
													value="<%=BPF.getFhasta() %>" size="10"
													maxlength="10" readonly>                                    </td>
                                    <td width="88%"><a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onmouseover="calSwapImg('BTN_date_7', 'img_Date_OVER',true); "
													onmouseout="calSwapImg('BTN_date_7', 'img_Date_UP',true);"
													onclick="calSwapImg('BTN_date_7', 'img_Date_DOWN');showCalendar('frm','fhasta','BTN_date_7');return false;"><img
														src="vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_7" width="22" height="17" border="0"
														align="absmiddle" id="BTN_date_7"> </a> </td>
                                  </tr>
          </table></td>
						  </tr><tr class="fila-det">
						    <td width="14%" height="46" class="fila-det-border">Estado(*)
					        <input name="idestado" type="hidden" id="idestado" value="<%=BPF.getIdestado()%>"></td>
						    <td width="33%" class="fila-det-border"><table width="23%" border="0">
                              <tr class="fila-det-border">
                                <td width="61%" ><input name="estado" type="text" class="campo" id="estado" value="<%=str.esNulo(BPF.getEstado())%>" size="30" readonly></td>
                                <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Clientes/lov_estado.jsp')" style="cursor:pointer"></td>
                              </tr>
                            </table></td>
						    <td width="11%" class="fila-det-border">&nbsp;</td>
						    <td width="42%" class="fila-det-border">&nbsp;						</td>
		                  <tr class="fila-det">
		                    <td height="46" class="fila-det-border">&nbsp;</td>
		                    <td class="fila-det-border"><input name="validar"
											type="submit" value="Consultar" class="boton"></td>
		                    <td class="fila-det-border">&nbsp;</td>
		                    <td class="fila-det-border">&nbsp;</td>
                      </table>
			<input name="primeraCarga" type="hidden" value="false" >
		</form>

<%if (BPF.getAccion().equalsIgnoreCase("consulta")){%>		
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable"   >
  <tr class="fila-encabezado">
    <td colspan="9">Total de Registros: <%=BPF.getTotalregistros()%></td>
  </tr>
  <tr class="fila-encabezado">
    <td width="3%">Tipo</td>
     <td width="4%"><div align="right">Cliente</div></td>  
     <td><div align="center">Razon</div></td>
     <td>Vendedor</td>
     <td><div align="center">Fecha  </div></td>
     <td>Motivo</td>
     <td>Observaciones</td>
     <td width="5%"><div align="center">U.Alta</div></td>
     <td width="9%"><div align="center">F.Alta</div></td>
  </tr>  
   <%int r = 0;
   while(iterConsulta1.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterConsulta1.next(); 
       String imagen ="";      
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" ><%=sCampos[1]%></td>
     <td height="22" class="fila-det-border" ><div align="right"><%=sCampos[2]%></div></td>
      <td width="21%" class="fila-det-border" ><div align="left"><%=sCampos[3]%>&nbsp;</div></td>
      <td width="14%" class="fila-det-border" ><%=sCampos[4]%>&nbsp;</td>
      <td width="6%" class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[5]), "JSDateToStr")%>&nbsp;</div></td>
      <td width="14%" class="fila-det-border" ><%=sCampos[6]%>&nbsp;</td>
      <td width="24%" class="fila-det-border" ><%=sCampos[7]%>&nbsp;</td>
      <td class="fila-det-border" ><div align="center"><%=sCampos[8].toLowerCase()%></div></td>
      <td class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[9]), "JSTsToStrWithHM")%></div></td>
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

