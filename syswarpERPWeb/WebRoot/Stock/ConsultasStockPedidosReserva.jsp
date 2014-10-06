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
 String idempresa = session.getAttribute("empresa").toString() ;
%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<%-- INSTANCIAR BEAN --%>
	<jsp:useBean id="BPF"
		class="ar.com.syswarp.web.ejb.BeanStockConsultaPedidosStockReserva" scope="page" />
	<head>
		<title>CONSULTA DE PEDIDOS INVOLUCRADOS EN EL STOCK RESERVA </title>
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
		 
		 
	function setTipoPedido(obj){
	  var tipopedido = '<%=BPF.getTipopedido()%>';
	  if(obj.value != tipopedido){
		document.frm.accion.value = '';
		document.getElementById("validar").disabled = true;
		document.frm.submit();
	  } 
	
	}		 
		 
		 </script>
		 
		<meta http-equiv="Content-Type"	content="text/html; charset=iso-8859-1">
	</head>
	<BODY>
		<div id="popupcalendar" class="text"></div>
		<%-- EJECUTAR SETEO DE PROPIEDADES --%>
		<jsp:setProperty name="BPF" property="*" />
		<% 
 String titulo = "CONSULTA DE PEDIDOS INVOLUCRADOS EN EL STOCK RESERVA ";
	
		
		
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
		<form action="ConsultasStockPedidosReserva.jsp" method="post" name="frm">
		<input name="accion" type="hidden" value="consulta">
<%--			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				align="center">
				<tr> 
				  <td>
				    <table width="100%" border="0" cellspacing="0" cellpadding="0"
							align="center">
				      <tr class="text-globales">
				      <td>--%>
				      <table width="100%" border="0"  cellpadding="0"
										cellspacing="0" class="text-globales">
				        <tr>
				          <td><%= titulo%></td>
					    </td>
				      </tr>
			        </table>
				    </table>
				    <table width="100%" border="0" cellspacing="0" cellpadding="0">
				      <tr class="fila-det-bold-rojo">
				        <td class="fila-det-border">&nbsp;								</td>
					      <td colspan="3" class="fila-det-border">
						      <jsp:getProperty name="BPF" property="mensaje" />
				        &nbsp;																								</td>
				      </tr>
				      
				      <tr class="fila-det">
				        <td width="12%" class="fila-det-border">
				          Articulo: (*)								</td>
					      <td width="22%" class="fila-det-border">
						      <table width="89%" border="0" cellpadding="0" cellspacing="0">
						          <tr class="fila-det-border">
							          <td width="15%">
							              <input name="articulodesde" type="text" class="campo"
													id="articulodesde" value="<%=BPF.getArticulodesde()%>" size="7" maxlength="10"
													readonly>											</td>
								      <td width="56%"><input name="descrip_st" type="text" class="campo"
													id="descrip_st" value="<%=BPF.getDescrip_st()%>" size="30" maxlength="100"
													readonly></td>
			                <td width="29%"><img src="../imagenes/default/gnome_tango/actions/filefind.png"
													width="22" height="22"
													onClick="abrirVentana('../Stock/lov_articulo.jsp', 'articulo', 750, 400)"
													style="cursor:pointer"> </td>
					          </tr>
		            </table>							  </td>
							      
							  
					      <td width="11%" class="fila-det-border">&nbsp;</td>
					      <td width="55%" class="fila-det-border"><input name="validar" id="validar"
											type="submit" value="Consultar" class="boton"></td>
			          </tr>
				      <tr class="fila-det">
				        <td class="fila-det-border"> Dep&oacute;sito: (*) </td>
				        <td class="fila-det-border"><table width="88%" border="0" cellpadding="0" cellspacing="0">
                            <tr class="fila-det-border">
                              <td width="15%"><input name="codigo_dt" type="text" class="campo"
													id="codigo_dt" value="<%=BPF.getCodigo_dt()%>" size="7" maxlength="10"
													readonly>                              </td>
                              <td width="56%"><input name="descrip_dt" type="text" class="campo"
													id="descrip_dt" value="<%=BPF.getDescrip_dt()%>" size="30" maxlength="100"
													readonly></td>
                              <td width="29%"><img src="../imagenes/default/gnome_tango/actions/filefind.png"
													width="22" height="22"
													onClick="abrirVentana('../Stock/lov_deposito.jsp', 'deposito', 750, 400)"
													style="cursor:pointer"> </td>
                            </tr>
                        </table></td>
				        <td class="fila-det-border">&nbsp;</td>
				        <td class="fila-det-border">&nbsp;</td>
		              </tr>
				      <tr class="fila-det">
				        <td class="fila-det-border">F. Desde:</td>
				        <td class="fila-det-border"><input name="fechaDesde" type="text" class="cal-TextBox" id="fechaDesde" onFocus="this.blur()" value="<%=BPF.getFechaDesde()%>" size="10" maxlength="10" readonly>
                          <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onMouseOver="calSwapImg('BTN_date_7', 'img_Date_OVER',true); "
                  onMouseOut="calSwapImg('BTN_date_7', 'img_Date_UP',true);"
                  onClick="calSwapImg('BTN_date_7', 'img_Date_DOWN');showCalendar('frm','fechaDesde','BTN_date_7');return false;"> <img align="absmiddle" border="0" name="BTN_date_7" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a></td>
				        <td class="fila-det-border">F. Hasta:</td>
				        <td class="fila-det-border"><input name="fechaHasta" type="text" class="cal-TextBox" id="fechaHasta" onFocus="this.blur()" value="<%=BPF.getFechaHasta()%>" size="10" maxlength="10" readonly>
                          <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onMouseOver="calSwapImg('BTN_date_77', 'img_Date_OVER',true); "
                  onMouseOut="calSwapImg('BTN_date_77', 'img_Date_UP',true);"
                  onClick="calSwapImg('BTN_date_77', 'img_Date_DOWN');showCalendar('frm','fechaHasta','BTN_date_77');return false;"> <img align="absmiddle" border="0" name="BTN_date_77" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a></td>
			          </tr>
				      <tr class="fila-det">
				        <td class="fila-det-border">Pedido Normal                        </td>
				        <td class="fila-det-border"><input name="tipopedido" type="radio" class="campo" value="N" <%= BPF.getTipopedido().equalsIgnoreCase("N") ? "checked" : "" %> onClick="setTipoPedido(this);" ></td>
				        <td class="fila-det-border">R.Empresario                        </td>
				        <td class="fila-det-border"><input name="tipopedido" type="radio" class="campo" value="R" <%= BPF.getTipopedido().equalsIgnoreCase("R") ? "checked" : "" %> onClick="setTipoPedido(this);"></td>
			          </tr>
            </table>
				    <input name="primeraCarga" type="hidden" value="false" >
				  
	</form>

	

 <!-- detalle -->
 <%if (BPF.getAccion().equalsIgnoreCase("consulta")){%>
 <table width="100%" border="0" align="center" cellpadding="0" cellspacing="2" name="rsTable"   >

   <tr class="text-dos-bold">
     <td colspan="10" valign="top">DETALLE PARA ARTICULO: <%=BPF.getArticulodesde() + "-" + BPF.getDescrip_st() %></td>
   </tr>
   



   <tr class="fila-encabezado">
     <td colspan="3" valign="top"><div align="center">Pedido</div></td>
     <td colspan="3"><div align="center">Cantidades</div></td>
     <td colspan="2" valign="top"><div align="center">Hoja Armado </div></td>
     <td rowspan="2" valign="top">Dep&oacute;sito</td>
     <td rowspan="2"><div align="center"></div></td>
   </tr>
   <tr class="fila-encabezado">
     <td width="4%" valign="top"><div align="right">Nro.</div></td>
     <td valign="top"><div align="center">Estado</div></td>
     <td valign="top"><div align="center">Fecha</div></td>
     <td><div align="right">Pedido</div></td>
     <td><div align="right">En Stock </div></td>
     <td><div align="right">Reserva</div></td>
     <td valign="top"><div align="right">Nro.</div></td>
     <td valign="top"><div align="center">Fecha</div></td>
   </tr>
   <%int r = 0;
   while(iterConsulta1.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterConsulta1.next(); 
       String imagen ="";      
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td height="22" class="fila-det-border" ><div align="right"><%=sCampos[0]%></div></td>
     <td width="5%" class="fila-det-border" ><div align="center"><%=sCampos[12]%></div></td>
     <td width="5%" class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[1]), "JSTSToStr")%></div></td>
     <td width="5%" bgcolor="#F0E1C6" class="fila-det-border" ><div align="right"><%=sCampos[4]%></div></td>
     <td width="7%" bgcolor="#F0F0FF" class="fila-det-border" ><div align="right"><%=sCampos[5]%></div></td>
     <td width="9%" bgcolor="#FFFFCC" class="fila-det-border" ><div align="right"><%=sCampos[6]%></div></td>
     <td width="9%" class="fila-det-border" ><div align="right"><%=Common.setNotNull(sCampos[13])%></div></td>
     <td width="10%" class="fila-det-border" ><div align="center"><%= !Common.setNotNull(sCampos[14]).equals("") ? Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[14]), "JSDateToStr") : "&nbsp;" %></div></td>
     <td width="44%" class="fila-det-border" ><%=sCampos[7]%></td>
     <td width="2%" class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/mimetypes/gnome-mime-application-vnd.sun.xml.calc.png" title="VER PEDIDO <%= sCampos[0] %>" width="18" height="18"onClick="abrirVentana('../Clientes/pedidosHistoricoClienteDetalle.jsp?idpedido_cabe=<%= sCampos[0] %>&idcliente=<%=sCampos[9]%>&cliente=<%=sCampos[10]%>&tipopedido=<%= sCampos[8] %>', 'Pedidos', 750, 400)"></div></td>
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

