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
		class="ar.com.syswarp.web.ejb.BeanConsultaStock" scope="page" />
	<head>
		<title>Consulta Stock</title>
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
 String titulo = "Consulta Stock";
	
		
		
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
 Consulta2 = BPF.getMovimientosList();
 

 iterConsulta1 = Consulta2.iterator();
 
 %>
		<form action="ConsultasStock.jsp" method="post" name="frm">
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
											<td><%= titulo%></td>
										
									  </td>
										</tr>
								  </table>
									
								
							
					  </table>
						<input name="primeraCarga" type="hidden" value="false" >
		</form>

	

 <!-- detalle -->  
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable"   >
  <tr class="fila-encabezado">  
     <td width="11%">Cod Prod </td>
     <td width="41%">Producto</td>
     <td width="26%">Deposito</td>     
     <td width="8%"><div align="right">Disponible</div></td>
     <td width="7%"><div align="right">Reserva</div></td>
     <td width="7%"><div align="right">Existencia</div></td>
  </tr>
  <% BigDecimal totaldisponible = new BigDecimal(0); %> 
  <% BigDecimal totalreserva = new BigDecimal(0); %>
  <% BigDecimal totalexistencia = new BigDecimal(0); %>
  <%int r = 0;
   while(iterConsulta1.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterConsulta1.next(); 
      totaldisponible = totaldisponible.add(new BigDecimal( sCampos[3]) ); 
	  totalreserva = totalreserva.add(new BigDecimal( sCampos[4]) ); 
	  totalexistencia = totalexistencia.add(new BigDecimal( sCampos[5]) ); 
      String imagen ="icon-truck.gif";
      
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td height="39" class="fila-det-border">&nbsp;<%=sCampos[0]%></td>   
      <td class="fila-det-border">&nbsp;<%=sCampos[1]%></td>  
      <td class="fila-det-border">&nbsp;<%=sCampos[2]%></td> 
      <td class="fila-det-border"><div align="right"><%=sCampos[3]%>
          </div>
      </div></td>     
      <td class="fila-det-border"><div align="right"><%=sCampos[4]%></div></td>
      <td class="fila-det-border"><div align="right"><%=sCampos[5]%></div></td>
  </tr>
   <%
   }%>
  <!-- final -->  
   </table>
    
			
	<table width="100%" border="0" cellpadding="1" cellspacing="1" name="rsTable"   >

  
      <tr class="fila-encabezado">
        <td width="8%">Total Disponible </td>
        <td width="8%"><div align="center"><span class="fila-det-border"><%=totaldisponible.toString()%></span></div></td>
        <td width="10%"><div align="left">Total Reserva </div></td>
		<td width="9%"><div align="right"><span class="fila-det-border"><%=totalreserva.toString()%></span></div></td>
		<td width="10%"><div align="left">Total Existencia </div></td>
        <td width="55%"><span class="fila-det-border"><%=totalexistencia.toString()%></span></td>
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

