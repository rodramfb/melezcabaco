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
		class="ar.com.syswarp.web.ejb.BeanConsultaVendedoresDetalleAsociaciones" scope="page" />
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
	<span class="fila-det-border">
	<jsp:getProperty name="BPF" property="mensaje" />    
</span>
		<div id="popupcalendar" class="text"></div>
		<%-- EJECUTAR SETEO DE PROPIEDADES --%>
		<jsp:setProperty name="BPF" property="*" />
		<% 
 String titulo = "Detalle de Asociaciones";
	
		
		
 BPF.setResponse(response);
 BPF.setRequest(request);
 // ver esto BPF.setSession(session);
 //BPF.setUsuarioalt( session.getAttribute("usuario").toString() );
 //BPF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BPF.ejecutarValidacion();
 java.util.List Consulta1 = new java.util.ArrayList();

 Consulta1 = BPF.getMovimientosList();
 
 iterConsulta1 = Consulta1.iterator();
 %>
		<form action="ConsultaLiquidacionVendedoresDetalleAsociaciones.jsp" method="post" name="frm">
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
																					
											<td width="50">&nbsp;</td>
											
											<td width="43">
												
										  </td>
											<td width="42">
										
										  </td>
										</tr>
								  </table>
									
								</td>
							</tr>
					  </table>
						<input name="primeraCarga" type="hidden" value="false" >
		</form>

	
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable"   >
  <tr class="fila-encabezado">
     <td width="18%">Socio</td>  
     <td><div align="center">Apellido</div></td>
	 <td><div align="center">Nombre</div></td>
     <td>F.Ingreso</td>
     <td>A.Asoc.</td>
     <td>M.Asoc.</td>
     <td>T.Cuotas</td>
     <td>C.Actual</td>
     <td>Imp.Total</td>
     <td>Imp.Cuota</td>
  </tr> 
  <% BigDecimal totalgeneral = new BigDecimal(0); %> 
  <% BigDecimal totalgeneral2 = new BigDecimal(0); %> 
   <%int r = 0;
   while(iterConsulta1.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterConsulta1.next(); 
	  totalgeneral = totalgeneral.add(new BigDecimal( sCampos[8]) ); 
	  totalgeneral2 = totalgeneral2.add(new BigDecimal( sCampos[9]) ); 
       String imagen ="";      
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td height="22" class="fila-det-border" >&nbsp;<%=sCampos[0]%></td>
      <td width="25%" class="fila-det-border" ><%=sCampos[1]%></td>
	  <td width="7%" class="fila-det-border" ><%=sCampos[2]%></td>
      <td width="7%" class="fila-det-border" ><%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[3]), "JSTsToStr")%></td>
      <td width="7%" class="fila-det-border" ><%=sCampos[4]%></td>
      <td width="7%" class="fila-det-border" ><%=sCampos[5]%></td>
      <td width="7%" class="fila-det-border" ><%=sCampos[6]%></td>
      <td width="7%" class="fila-det-border" ><%=sCampos[7]%></td>
      <td width="7%" class="fila-det-border" ><%=sCampos[8]%></td>
      <td width="8%" class="fila-det-border" ><%=sCampos[9]%></td>
   </tr>
<%
   }%>
   
   <tr class="fila-encabezado">
     <td width="18%">Total Imp.Total</td>  
     <td><div align="right"></div></td>
	 <td><div align="right"></div></td>
	 <td><div align="right"></div></td>
	 <td><div align="right"></div></td>
	 <td><div align="right"></div></td>
	 <td><div align="right"></div></td>
	 <td><div align="right"></div></td>
	 <td><div align="right"></div></td>
	 <td><div align="left"><span class="fila-det-border"><%=totalgeneral.toString()%></span></div></td>
  </tr>
     <tr class="fila-encabezado">
     <td width="18%">Total Imp.Cuota</td>  
     <td><div align="right"></div></td>
	 <td><div align="right"></div></td>
	 <td><div align="right"></div></td>
	 <td><div align="right"></div></td>
	 <td><div align="right"></div></td>
	 <td><div align="right"></div></td>
	 <td><div align="right"></div></td>
	 <td><div align="right"></div></td>
	 <td><div align="left"><span class="fila-det-border"><%=totalgeneral2.toString()%></span></div></td>
  </tr> 
   
   </table>

   

			
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

