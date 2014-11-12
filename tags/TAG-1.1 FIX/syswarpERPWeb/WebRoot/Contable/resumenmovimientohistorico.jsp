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
		class="ar.com.syswarp.web.ejb.BeanResumenMovhistorico" scope="page" />
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
	<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style></head>
	<BODY>
		<div id="popupcalendar" class="text"></div>
		<%-- EJECUTAR SETEO DE PROPIEDADES --%>
		<jsp:setProperty name="BPF" property="*" />
		<% 
 String titulo = "Resumen de Movimiento Historico";
	
		
		
 BPF.setResponse(response);
 BPF.setRequest(request);
 // ver esto BPF.setSession(session);
 BPF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPF.setEjercicio( new BigDecimal( (String) session.getAttribute("ejercicioActivo") ) ) ; 
 BPF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BPF.ejecutarValidacion();
 java.util.List Consulta1 = new java.util.ArrayList();

 Consulta1 = BPF.getMovimientosList();
 
 iterConsulta1 = Consulta1.iterator();
 %>
		<form action="resumenmovimientohistorico.jsp" method="post" name="frm">
  
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
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable"   >
  <tr class="fila-encabezado">
     <td width="7%" height="22">idcuenta</td>  
     <td><div align="center">cuenta</div></td>
	  <td><div align="center">imputalbe</div></td>
	   <td><div align="center">enero</div></td>
	    <td><div align="center">febrero</div></td>
		 <td><div align="center">marzo</div></td>
		  <td><div align="center">abril</div></td>
		  <td><div align="center">mayo</div></td>
		  <td><div align="center">junio</div></td>
		  <td><div align="center">julio</div></td>
		  <td><div align="center">agosto</div></td>
		  <td><div align="center">septiembre</div></td>
		  <td><div align="center">octubre</div></td>
		  <td><div align="center">noviembre</div></td>
		  <td><div align="center">diciembre</div></td>
		  <td><div align="center">total</div></td>
  </tr> 
  <% BigDecimal totalenero = new BigDecimal(0); %> 
  <% BigDecimal totalfebrero = new BigDecimal(0); %> 
  <% BigDecimal totalmarzo = new BigDecimal(0); %> 
  <% BigDecimal totalabril = new BigDecimal(0); %> 
  <% BigDecimal totalmayo = new BigDecimal(0); %> 
  <% BigDecimal totaljunio = new BigDecimal(0); %> 
  <% BigDecimal totaljulio = new BigDecimal(0); %> 
  <% BigDecimal totalagosto = new BigDecimal(0); %> 
  <% BigDecimal totalseptiembre = new BigDecimal(0); %> 
  <% BigDecimal totaloctubre = new BigDecimal(0); %> 
  <% BigDecimal totalnoviembre = new BigDecimal(0); %> 
  <% BigDecimal totaldiciembre = new BigDecimal(0); %> 
  <% BigDecimal totalSaldo = new BigDecimal(0); %> 
   <%int r = 0;
   while(iterConsulta1.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterConsulta1.next(); 
	  totalenero = totalenero.add(new BigDecimal( sCampos[3]) );   
	  totalfebrero = totalfebrero.add(new BigDecimal( sCampos[4]) );   
	  totalmarzo = totalmarzo.add(new BigDecimal( sCampos[5]) );   
	  totalabril = totalabril.add(new BigDecimal( sCampos[6]) );   
	  totalmayo = totalmayo.add(new BigDecimal( sCampos[7]) );   
	  totaljunio = totaljunio.add(new BigDecimal( sCampos[8]) );   
	  totaljulio = totaljulio.add(new BigDecimal( sCampos[9]) );   
	  totalagosto = totalagosto.add(new BigDecimal( sCampos[10]) );   
	  totalseptiembre = totalseptiembre.add(new BigDecimal( sCampos[11]) );   
	  totaloctubre = totaloctubre.add(new BigDecimal( sCampos[12]) );   
	  totalnoviembre = totalnoviembre.add(new BigDecimal( sCampos[13]) );   
	  totaldiciembre = totaldiciembre.add(new BigDecimal( sCampos[14]) );  
	  totalSaldo = totalSaldo.add(new BigDecimal( sCampos[15]) );   
       String imagen ="";      
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td height="22" class="fila-det-border" >&nbsp;<%=sCampos[0]%></td>
      <td width="21%" class="fila-det-border" ><%=sCampos[1]%> </td>
	  <td width="5%" class="fila-det-border" ><%=sCampos[2]%></td>
	  <td width="5%" class="fila-det-border" ><%=sCampos[3]%> </td>
	  <td width="5%" class="fila-det-border" ><%=sCampos[4]%></td>
	  <td width="7%" class="fila-det-border" ><%=sCampos[5]%></td>
	  <td width="6%" class="fila-det-border" ><%=sCampos[6]%></td>
	  <td width="8%" class="fila-det-border" ><%=sCampos[7]%></td>
	  <td width="3%" class="fila-det-border" ><%=sCampos[8]%></td>
	  <td width="3%" class="fila-det-border" ><%=sCampos[9]%></td>
	  <td width="4%" class="fila-det-border" ><%=sCampos[10]%></td>
	  <td width="6%" class="fila-det-border" ><%=sCampos[11]%></td>
	  <td width="4%" class="fila-det-border" ><%=sCampos[12]%></td>
	  <td width="6%" class="fila-det-border" ><%=sCampos[13]%></td>
	  <td width="5%" class="fila-det-border" ><%=sCampos[14]%></td>
	  <td width="5%" class="fila-det-border" ><%=sCampos[15]%></td> 
  </tr>
  <%
   }%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td height="22" class="fila-det-border" >Totales:</td>
     <td class="fila-det-border" >&nbsp;</td>
     <td class="fila-det-border" >&nbsp;</td>
     <td class="fila-det-border" ><%=totalenero.toString()%></td>
     <td class="fila-det-border" ><%=totalfebrero.toString()%></td>
     <td class="fila-det-border" ><%=totalmarzo.toString()%></td>
     <td class="fila-det-border" ><%=totalabril.toString()%></td>
     <td class="fila-det-border" ><%=totalmayo.toString()%></td>
     <td class="fila-det-border" ><%=totaljunio.toString()%></td>
     <td class="fila-det-border" ><%=totaljulio.toString()%></td>
     <td class="fila-det-border" ><%=totalagosto.toString()%></td>
     <td class="fila-det-border" ><%=totalseptiembre.toString()%></td>
     <td class="fila-det-border" ><%=totaloctubre.toString()%></td>
     <td class="fila-det-border" ><%=totalnoviembre.toString()%></td>
     <td class="fila-det-border" ><%=totaldiciembre.toString()%></td>
     <td class="fila-det-border" ><%=totalSaldo.toString()%></td>
   </tr>

   </table>

    <p>&nbsp;</p>
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

