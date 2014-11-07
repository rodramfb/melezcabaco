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
		class="ar.com.syswarp.web.ejb.BeanConsultaControlDisponible" scope="page" />
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
 String titulo = "Consulta de Stock disponible (negativo)";		
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
		<form action="ConsultaControlDisponible.jsp" method="post" name="frm">
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
								<td width="11%" class="fila-det-border">&nbsp;								</td>
								<td width="89%" class="fila-det-border">
									<jsp:getProperty name="BPF" property="mensaje" />
&nbsp;																								</td>
							</tr>
					  </table>
		</form>

		
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable"   >
  <tr class="fila-encabezado">
     <td width="6%">Producto</td>  
     <td><div align="center">Descripcion</div></td>
	 <td><div align="right">Dispon</div></td>
     <td>Deposito</td>
     <td><div align="center">U.M</div></td>
     <td>Esqu.</td>
     <td><div align="center">O.C.</div></td>
     <td><div align="center">Ped.</div></td>
     <td><div align="center">Stock</div></td>
     <td><div align="center">Op.Pend</div></td>
  </tr> 
   <%int r = 0;
   while(iterConsulta1.hasNext()){
      ++r; 
      String[] sCampos = (String[]) iterConsulta1.next(); 
       String imagen ="";      
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td height="22" class="fila-det-border" ><%=sCampos[0]%></td>
      <td width="31%" class="fila-det-border" ><%=sCampos[1]%></td>
	  <td width="7%" class="fila-det-border" ><div align="right"><%=sCampos[2]%></div></td>
      <td width="20%" class="fila-det-border" ><%=sCampos[3]%></td>
      <td width="10%" class="fila-det-border" ><%=sCampos[4]%></td>
	  
	  
	  
      <td width="4%" class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/categories/redhat-system_settings.png" title="Esquema:" width="22" height="22"  onClick="abrirVentana('../Clientes/lovPedidosEsquemasContieneArt.jsp?ocurrencia=<%=sCampos[0]%>', 'Esquemas', 750, 400)">
        <%-- <img src="../imagenes/default/gnome_tango/categories/redhat-system_settings.png" title="Esquema:" width="22" height="22"  onClick="abrirVentana('ConsultasEsquema.jsp?codigo_st=<%=sCampos[0]%>', 'Esquemas', 750, 400)"> --%>
      </div>        </td>
	  
	  
	  
	  
     <td width="3%" class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/accessories-text-editor.png" title="Orden de Compra:" width="22" height="22"  onClick="abrirVentana('ConsultasArribosOCAprobadasporproducto.jsp?producto=<%=sCampos[0]%>', 'Orden de Compra', 750, 400)"> </div></td>
	  

	  
	  
      <td width="5%" class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/accessories-calculator.png" title="Pedidos" width="22" height="22"onClick="abrirVentana('../Clientes/consultaPedidosStockNegativo.jsp?codigo_st=<%= sCampos[0] %>', 'Pedidos', 750, 400)"></div></td>	  
	  
	  

	  
	  
       <td width="5%" class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/mimetypes/deb.png" title="Stock:" width="22" height="22"  onClick="abrirVentana('ConsultasStock.jsp?codigo_st=<%=sCampos[0]%>', 'Stock', 750, 400)"></div></td>	  
	  
	  
	  
      <td width="9%" class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/mimetypes/vcalendar.png" title="Orden Produccion:" width="22" height="22"  onClick="abrirVentana('ConsultasOrdenProduccion.jsp?codigo_st=<%=sCampos[0]%>', 'OrdenProduccion', 750, 400)"></div></td>	  
   </tr>
   <%}%> 
   </table>

 <p>&nbsp;   </p>
 <p>
   <input name="produco" value="<%= BPF.getProducto() %>" type="hidden">  
   
   
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

