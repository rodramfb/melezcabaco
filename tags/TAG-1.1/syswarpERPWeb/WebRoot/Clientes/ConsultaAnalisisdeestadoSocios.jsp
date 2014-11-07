<%@page language="java"%>
<%@page import="java.sql.ResultSetMetaData"%>
<%@ page import="ar.com.syswarp.ejb.*"%>
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
   
Strings str = new Strings();
String color_fondo ="";
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<%-- INSTANCIAR BEAN --%>
	<jsp:useBean id="BPF"
		class="ar.com.syswarp.web.ejb.BeanConsultaAnalisisdeestadoporaniodeingreso" scope="page" />
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
		 
		<meta http-equiv="Content-Type"	content="text/html; charset=iso-8859-1">
	</head>
	<BODY>
		<div id="popupcalendar" class="text"></div>
		<%-- EJECUTAR SETEO DE PROPIEDADES --%>
		<jsp:setProperty name="BPF" property="*" />
		<% 
 String titulo = "Consulta Analisis de Estado de Socios";
	
		
		
 BPF.setResponse(response);
 BPF.setRequest(request);
 // ver esto BPF.setSession(session);
 //BPF.setUsuarioalt( session.getAttribute("usuario").toString() );
 //BPF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BPF.ejecutarValidacion();
 
 java.util.List Consulta1 = new java.util.ArrayList();
 Consulta1 = BPF.getMovimientosList();
 Iterator iterConsulta1 = Consulta1.iterator();
 java.sql.ResultSet Consulta2 ;
 Consulta2 = BPF.getMovimientosList2();
 %>
	<form action="ConsultaAnalisisdeestadoSocios.jsp" method="post" name="frm">
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
								<td height="19" class="fila-det-border">&nbsp;								</td>
								<td colspan="3" class="fila-det-border">
									<jsp:getProperty name="BPF" property="mensaje" />
&nbsp;																								</td>
							</tr>
<tr class="fila-det">
								<td width="16%" class="fila-det-border">Fecha Desde </td>
								<td width="27%" class="fila-det-border"><table width="47%" border="0" cellpadding="0" cellspacing="0">
                                  <tr class="fila-det-border">
                                    <td width="32%"><input name="fechadesde" type="text" class="cal-TextBox"
													id="fechadesde" onFocus="this.blur()"
													value="<%=BPF.getFechadesde() %>" size="12"
													maxlength="12" readonly>                                    </td>
                                    <td width="68%"><a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onmouseover="calSwapImg('BTN_date_6', 'img_Date_OVER',true); "
													onmouseout="calSwapImg('BTN_date_6', 'img_Date_UP',true);"
													onclick="calSwapImg('BTN_date_6', 'img_Date_DOWN');showCalendar('frm','fechadesde','BTN_date_6');return false;"><img
														src="vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_6" width="22" height="17" border="0"
														align="absmiddle"> </a> </td>
                                  </tr>
          </table></td>
          <td width="16%" class="fila-det-border">Fecha Hasta</td>
		                        <td width="41%" class="fila-det-border"><table width="29%" border="0" cellpadding="0" cellspacing="0">
                                  <tr class="fila-det-border">
                                    <td width="32%"><input name="fechahasta" type="text" class="cal-TextBox"
													id="fechahasta" onFocus="this.blur()"
													value="<%=BPF.getFechahasta() %>" size="12"
													maxlength="12" readonly>                                    </td>
                                    <td width="68%"><a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onmouseover="calSwapImg('BTN_date_7', 'img_Date_OVER',true); "
													onmouseout="calSwapImg('BTN_date_7', 'img_Date_UP',true);"
													onclick="calSwapImg('BTN_date_7', 'img_Date_DOWN');showCalendar('frm','fechahasta','BTN_date_7');return false;"><img
														src="vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_7" width="22" height="17" border="0"
														align="absmiddle"> </a> </td>
                                  </tr>
          </table></td>
</tr>
<tr class="fila-det">
  <td class="fila-det-border">Estado
  <input name="idestado" type="hidden" id="idestado" value="<%=BPF.getIdestado()%>"></td>
  <td class="fila-det-border"><table width="23%" border="0" cellpadding="0" cellspacing="0">
    <tr class="fila-det-border">
      <td width="61%" ><input name="estado" type="text" class="campo" id="estado" value="<%=str.esNulo(BPF.getEstado())%>" size="30" readonly></td>
      <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_estado.jsp', 'estados', 800, 450)" style="cursor:pointer"></td>
    </tr>
  </table></td>
  <td class="fila-det-border">&nbsp;</td>
  <td class="fila-det-border">&nbsp;</td>
</tr>
<tr class="fila-det">
  <td class="fila-det-border">Reporte</td>
  <td class="fila-det-border"><select name="reporte" id="reporte"  class="campo">
    <option value="-1" <%= BPF.getReporte() %> >Seleccionar </option>
    <option value="1" <%= BPF.getReporte().equals("1") ? "selected" : "" %> >Por año de ingreso</option>
    <option value="2" <%= BPF.getReporte().equals("2") ? "selected" : ""%> >Por distribuidor y motivo de baja</option>
    <option value="3" <%= BPF.getReporte().equals("3") ? "selected" : ""%> >Por vendedor y motivo de baja </option>	
    <option value="4" <%= BPF.getReporte().equals("4") ? "selected" : ""%> >Por vendedor </option>	
	<option value="5" <%= BPF.getReporte().equals("5") ? "selected" : ""%> >Por provincia y motivo </option>
    </select></td>
  <td class="fila-det-border">&nbsp;</td>
  <td class="fila-det-border">&nbsp;</td>
</tr>
<tr class="fila-det">
  <td class="fila-det-border">&nbsp;</td>
  <td class="fila-det-border"><input name="validar"
											type="submit" value="Consultar" class="boton"></td>
  <td class="fila-det-border">&nbsp;</td>
  <td class="fila-det-border">&nbsp;</td>
</tr>
					  </table>
	</form>

<%if (BPF.getAccion().equalsIgnoreCase("consulta") && BPF.getReporte().equalsIgnoreCase("1")){%>	
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable"   > 
  <tr class="fila-encabezado">
     <td width="20%">A&ntilde;o de Ingreso </td>  
     <td><div align="left">Socios</div></td>
  </tr> 
   <%int r = 0;
   while(iterConsulta1.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterConsulta1.next(); 
       String imagen ="";      
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td height="22" class="fila-det-border" >&nbsp;<%=sCampos[0]%></td>
      <td width="80%" class="fila-det-border" ><%=sCampos[1]%></td>
   </tr>
<%
   }%>
   </table>

   
<%}%>   




<%if (BPF.getAccion().equalsIgnoreCase("consulta") && BPF.getReporte().equalsIgnoreCase("4")){%>	
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable"   > 
  <tr class="fila-encabezado">
     <td width="20%">Vendedor</td>  
     <td><div align="left">Socios</div></td>
  </tr> 
   <%int r = 0;
   while(iterConsulta1.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterConsulta1.next(); 
       String imagen ="";      
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td height="22" class="fila-det-border" >&nbsp;<%=sCampos[0]%></td>
      <td width="80%" class="fila-det-border" ><%=sCampos[1]%></td>
   </tr>
<%
   }%>
   </table>

   
<%}%>   


<%if (BPF.getAccion().equalsIgnoreCase("consulta") && BPF.getReporte().equalsIgnoreCase("3")){%>	
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable"   >
   <%int r = 0;
   boolean hasTitulo = false;
   while(Consulta2.next()){
      ++r;     
      if (!hasTitulo){
        hasTitulo = true;
        java.sql.ResultSetMetaData rm = Consulta2.getMetaData();
                        
        %>
        <tr class="fila-encabezado">
          <%for (int zz=1; zz < rm.getColumnCount()+1; zz++){ %>
              <td width="12%"><%= rm.getColumnName(zz) %></td>
          <%}%>        
       </tr>        
        <%
      }
       
      String imagen ="";      
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <%java.sql.ResultSetMetaData rm = Consulta2.getMetaData();     %> 
     <%for (int zz=1; zz < rm.getColumnCount()+1; zz++){
       String valor = Consulta2.getString(rm.getColumnName(zz));
       if (valor==null) valor="--";
     %>
       <td height="22" class="fila-det-border" ><%=valor%></td>       
     <%}%>  
   </tr>
   
 <%}%>
 </table>
 
  <%}%>
 
 <%if (BPF.getAccion().equalsIgnoreCase("consulta") && BPF.getReporte().equalsIgnoreCase("2")){%>	
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable"   >
   <%int r = 0;
   boolean hasTitulo = false;
   while(Consulta2.next()){
      ++r;     
      if (!hasTitulo){
        hasTitulo = true;
        java.sql.ResultSetMetaData rm = Consulta2.getMetaData();
                        
        %>
        <tr class="fila-encabezado">
          <%for (int zz=1; zz < rm.getColumnCount()+1; zz++){ %>
              <td width="12%"><%= rm.getColumnName(zz) %></td>
          <%}%>        
       </tr>        
        <%
      }
       
      String imagen ="";      
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <%java.sql.ResultSetMetaData rm = Consulta2.getMetaData();     %> 
     <%for (int zz=1; zz < rm.getColumnCount()+1; zz++){
       String valor = Consulta2.getString(rm.getColumnName(zz));
       if (valor==null) valor="--";
     %>
       <td height="22" class="fila-det-border" ><%=valor%></td>       
     <%}%>  
   </tr>
   
 <%}%>
 </table>
 
  <%}%>
 
 <%if (BPF.getAccion().equalsIgnoreCase("consulta") && BPF.getReporte().equalsIgnoreCase("5")){%>	
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable"   >
   <%int r = 0;
   boolean hasTitulo = false;
   while(Consulta2.next()){
      ++r;     
      if (!hasTitulo){
        hasTitulo = true;
        java.sql.ResultSetMetaData rm = Consulta2.getMetaData();
                        
        %>
        <tr class="fila-encabezado">
          <%for (int zz=1; zz < rm.getColumnCount()+1; zz++){ %>
              <td width="12%"><%= rm.getColumnName(zz) %></td>
          <%}%>        
       </tr>        
        <%
      }
       
      String imagen ="";      
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <%java.sql.ResultSetMetaData rm = Consulta2.getMetaData();     %> 
     <%for (int zz=1; zz < rm.getColumnCount()+1; zz++){
       String valor = Consulta2.getString(rm.getColumnName(zz));
       if (valor==null) valor="--";
     %>
       <td height="22" class="fila-det-border" ><%=valor%></td>       
     <%}%>  
   </tr>
   
 <%}%>
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

