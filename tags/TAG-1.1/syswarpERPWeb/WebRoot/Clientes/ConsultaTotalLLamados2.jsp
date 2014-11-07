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
<%@ page import="java.math.BigDecimal" %>   
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
		class="ar.com.syswarp.web.ejb.BeanConsultaTotalLLamados2" scope="page" />
	<head>
		<title>Consuta de Movimientos por Articulo y Deposito</title>
<link rel = "stylesheet" href = "../imagenes/default/erp-style.css">
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
 String titulo = "Consulta Total LLamados";
	
		
		
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
		<form action="ConsultaTotalLLamados2.jsp" method="post" name="frm">
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
								<td width="22%" class="fila-det-border">Campa&ntilde;a(*):</td>
								<td width="25%" class="fila-det-border"><table width="100%" border="0">
                                  <tr class="fila-det-border">
								  <input name="idcampania" type="hidden" class="campo" id="idcampania" value="<%=BPF.getIdcampania()%>" size="30" readonly>
								  <%-- <input name="fdesdeStr" type="hidden" class="campo" id="fdesdeStr" value="<%=BPF.getFdesdeStr()%>" size="30" readonly>   --%>
								  <%-- <input name="fhastaStr" type="hidden" class="campo" id="fhastaStr" value="<%=BPF.getFhastaStr()%>" size="30" readonly> --%>	
                                    <td width="61%" ><input name="campania" type="text" class="campo" id="campania" value="<%=BPF.getCampania()%>" size="50" readonly></td>
                                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Clientes/lov_campania.jsp')" style="cursor:pointer"></td>
                                  </tr>
          </table></td>
								<td width="17%" class="fila-det-border">&nbsp;</td>
		  <td width="36%" class="fila-det-border">&nbsp;</td>
						  </tr><tr class="fila-det">
						    <td width="22%" height="28" class="fila-det-border">Fecha Desde(*): </td>
						    <td width="25%" class="fila-det-border"><table width="53%" border="0">
                              <tr class="fila-det-border">
                                <td width="32%"><input name="fdesdeStr" type="text" class="cal-TextBox"
													id="fdesdeStr" onFocus="this.blur()"
													value="<%=BPF.getFdesdeStr() %>" size="12"
													maxlength="12" readonly>
                                </td>
                                <td width="68%"><a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onmouseover="calSwapImg('BTN_date_6', 'img_Date_OVER',true); "
													onmouseout="calSwapImg('BTN_date_6', 'img_Date_UP',true);"
													onclick="calSwapImg('BTN_date_6', 'img_Date_DOWN');showCalendar('frm','fdesdeStr','BTN_date_6');return false;"><img
														src="vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_6" width="22" height="17" border="0"
														align="absmiddle"> </a> </td>
                              </tr>
                </table></td>
						    <td width="17%" class="fila-det-border">Fecha Hasta(*):</td>
						    <td width="36%" class="fila-det-border"><table width="26%" border="0">
                              <tr class="fila-det-border">
                                <td width="32%"><input name="fhastaStr" type="text" class="cal-TextBox"
													id="fhastaStr" onFocus="this.blur()"
													value="<%=BPF.getFhastaStr() %>" size="12"
													maxlength="12" readonly>
                                </td>
                                <td width="68%"><a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onmouseover="calSwapImg('BTN_date_7', 'img_Date_OVER',true); "
													onmouseout="calSwapImg('BTN_date_7', 'img_Date_UP',true);"
													onclick="calSwapImg('BTN_date_7', 'img_Date_DOWN');showCalendar('frm','fhastaStr','BTN_date_7');return false;"><img
														src="vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_7" width="22" height="17" border="0"
														align="absmiddle"> </a> </td>
                              </tr>
                </table></td>
		                  <tr class="fila-det">
		                    <td height="42" class="fila-det-border">&nbsp;</td>
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
     <td width="17%">Usuario</td>  
     <td><div align="left">Descripcion</div></td>
     <td><div align="right">LLamados</div></td>
     <td><div align="right">Socios</div></td>
  </tr> 
   <%int r = 0;
   while(iterConsulta1.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterConsulta1.next(); 
      String imagen ="";
      String decoration =   "";
      if(sCampos[1].equalsIgnoreCase("TOTAL GENERAL")){    
         decoration = "class=\"text-nueve\"";
      }
      else if(sCampos[1].equalsIgnoreCase("TOTAL")){ 
         decoration = "class=\"text-dos-bold\"";
      }
      else{
        if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
        else color_fondo = "fila-det-verde";
        decoration = "onMouseOver=\"mOvr(this,this.className='fila-det-rojo');\" onMouseOut=\"mOut(this,this.className='"+color_fondo+"');\" class=\""+color_fondo+"\"";
      }
      
%>
   <tr <%= decoration %>>
     <td height="22" class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td width="54%" class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>
      <td width="14%" class="fila-det-border" ><div align="right"><%=sCampos[2]%>&nbsp;</div></td>
      <td width="15%" class="fila-det-border" ><div align="right"><%=sCampos[3]%>&nbsp;</div></td>
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

