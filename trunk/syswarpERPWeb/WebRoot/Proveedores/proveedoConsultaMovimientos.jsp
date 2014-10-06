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
		class="ar.com.syswarp.web.ejb.BeanProveedoresInformeMov" scope="page" />
	<head>
		<title>Consuta de Esquema</title>
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
 String titulo = "Consulta de Movimientos.";
	
		
		
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
	<form action="proveedoConsultaMovimientos.jsp" method="post" name="frm">
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
							<td class="fila-det-border">
									<jsp:getProperty name="BPF" property="mensaje" />
&nbsp;</td>
							</tr>
							
							<tr class="fila-det">
								<td class="fila-det-border">Proveedor Desde (*) </td>
								<input name="idproveedordesde" type="hidden" id="idproveedordesde" value="<%=BPF.getIdproveedordesde()%>">
							<td class="fila-det-border">
							<table width="22%" border="0">
							<tr class="fila-det-border">
							<td width="61%" ><input name="dproveedordesde" type="text" class="campo" id="dproveedordesde" value="<%=BPF.getDproveedordesde()%>" size="30" readonly></td>
							<td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Proveedores/lov_proveedores_desde.jsp')" style="cursor:pointer"></td>
				</tr>
</table></td>
</tr>
							<tr class="fila-det">
							<td height="18" class="fila-det-border">Proveedor Hasta (*)
<input name="idproveedorhasta" type="hidden" id="idproveedorhasta" value="<%=BPF.getIdproveedorhasta()%>"></td>
							
							<td class="fila-det-border">
							<table width="22%" border="0">
							<tr class="fila-det-border">
							<td width="61%" ><input name="dproveedorhasta" type="text" class="campo" id="dproveedorhasta" value="<%=BPF.getDproveedorhasta()%>" size="30" readonly></td>
							<td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Proveedores/lov_proveedores_hasta.jsp')" style="cursor:pointer"></td>
							</tr>
							</table>
							</td>
							  
	
				</tr>
<tr class="fila-det">
<td height="28" class="fila-det-border">Fecha desde (*)</td>
<td class="fila-det-border"><input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fechadesde" value="<%=BPF.getFechadesde()%>" maxlength="12">
<a class="so-BtnLink" href="javascript:calClick();return false;"
                  onMouseOver="calSwapImg('BTN_date_7', 'img_Date_OVER',true); "
                  onMouseOut="calSwapImg('BTN_date_7', 'img_Date_UP',true);"
                  onClick="calSwapImg('BTN_date_7', 'img_Date_DOWN');showCalendar('frm','fechadesde','BTN_date_7');return false;"> <img align="absmiddle" border="0" name="BTN_date_7" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a></td>
</tr>
<tr class="fila-det">
<td height="18" class="fila-det-border">Fecha hasta (*)</div></td>
<td class="fila-det-border"><input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fechahasta" value="<%=BPF.getFechahasta()%>" maxlength="12">
<a class="so-BtnLink" href="javascript:calClick();return false;"
                  onMouseOver="calSwapImg('BTN_date_7', 'img_Date_OVER',true); "
                  onMouseOut="calSwapImg('BTN_date_7', 'img_Date_UP',true);"
                  onClick="calSwapImg('BTN_date_7', 'img_Date_DOWN');showCalendar('frm','fechahasta','BTN_date_7');return false;"> <img align="absmiddle" border="0" name="BTN_date_7" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a></td>
</tr>

<td width="15%">
<input name="validar"
					                     type="submit" value="Consultar" class="boton">	</td>


		  </table>
			<input name="primeraCarga" type="hidden" value="false" >
	</form>

<%if (BPF.getAccion().equalsIgnoreCase("consulta")){%>		
   <%int r = 0;
   String curProveedor = "-9999999";
   
    while( iterConsulta1.hasNext() ) {      
      String[] sCampos = (String[]) iterConsulta1.next();  
	  if ( !curProveedor.equalsIgnoreCase(sCampos[0]) ){ // solamente aca imprimo todos los titulos de cabecera
         ++r;
         if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
         else color_fondo = "fila-det-verde";  %>    
       <table width="64%" border="0" cellspacing="1" cellpadding="1" name="rsTable"   >
         <tr class="fila-encabezado">
         <td width="6%">Proveedor</td>      
    </tr>   
        <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
          <td class="fila-det-border" >&nbsp;<%=sCampos[1]%></td>
        </tr>

       <table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" name="rsTable"   >
           <tr class="fila-encabezado">
           <td width="11%">Fecha Movimiento</td>     
           <td width="10%">Tipo Movimiento</td>
           <td width="8%">Comprobante</td>
		   <td width="8%">Debe</td>
		   <td width="12%">Haber</td>
		   <td width="22%">Saldo del Movimiento</td>
		   <td width="12%">Saldo Acomulado</td>
		   <td width="17%">Fecha Vencimiento</td>
         </tr>
 

	 <% } // fin del if de titulos	  
      curProveedor = sCampos[0];	  	  
      ++r;
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";      	  
   %>
   
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>"        scope="col" >
      <td class="fila-det-border"  div align="center">&nbsp;<%=sCampos[2]%></td>
      <td class="fila-det-border"  div align="right">&nbsp;<%=sCampos[3]%></td>
      <td class="fila-det-border"  div align="right">&nbsp;<%=sCampos[4]%></td>
	  <td class="fila-det-border"  div align="right">&nbsp;<%=sCampos[5]%></td>
	  <td class="fila-det-border"  div align="right">&nbsp;<%=sCampos[6]%></td>
	  <td class="fila-det-border"  div align="right">&nbsp;<%=sCampos[7]%></td>
	  <td class="fila-det-border"  div align="right">&nbsp;<%=sCampos[8]%></td>
	  <td class="fila-det-border"  div align="center">&nbsp;<%=sCampos[9]%></td>
   </tr>
   
  
   
    <% 	 
	}  // fin  while%>
   </table>
 <p>&nbsp;</p>
<p>&nbsp;</p>
<p><%} // fin de if principal%>   			
</p>
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

