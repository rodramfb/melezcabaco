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
<%@ page import="ar.com.syswarp.ejb.*" %> 
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
GeneralBean gb = new GeneralBean();

Strings str = new Strings();
String color_fondo ="";
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<%-- INSTANCIAR BEAN --%>
	<jsp:useBean id="BPF"
		class="ar.com.syswarp.web.ejb.BeanStockConsultaExistenciasDeposito" scope="page" />
	<head>
		<title>Consuta de Existencias por Deposito</title>
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
 String titulo = "Consulta de Existencias por Deposito.";
	
		
		
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
		<form action="stockConsultaExistenciasDeposito.jsp" method="post" name="frm">
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
									Deposito: (*)								</td>
								<td width="37%" class="fila-det-border">
									<table width="79%" border="0">
										<tr class="fila-det-border">
											<td width="61%">
												<input name="deposito" type="text" class="campo"
													id="deposito" value="<%=BPF.getDeposito() %>" size="30"
													readonly>											</td>
											<td width="39%">
												<img src="../imagenes/default/gnome_tango/actions/filefind.png"
													width="22" height="22"
													onClick="mostrarLOV('../Stock/lov_deposito_informe.jsp')"
													style="cursor:pointer">											</td>
											<input name="iddeposito" type="hidden" id="iddeposito"
												value="<%=BPF.getIddeposito() %>">
										</tr>
							  </table>							  </td>
							  
							  
								<td width="13%" class="fila-det-border">&nbsp;</td>
								<td width="39%" class="fila-det-border">&nbsp;</td>
							</tr>
							<tr class="fila-det">
								<td width="11%" class="fila-det-border">							  </td>
								<td width="37%" class="fila-det-border">
									<table width="79%" border="0">
										<tr class="fila-det-border">
											<td width="61%">										  </td>
											<td width="39%">										  </td>
										</tr>
							  </table>							  </td>
							  
							  
					
								<td width="13%" class="fila-det-border">&nbsp;</td>
								<td width="39%" class="fila-det-border">&nbsp;</td>
							
							
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr class="fila-det">
							  
									
									<td width="13%">
									 <input name="validar"
											type="submit" value="Consultar Existencias" class="boton">								</td>
							</tr>
						</table>
					
				
		  </table>
			<input name="primeraCarga" type="hidden" value="false" >
		</form>

<%if (BPF.getAccion().equalsIgnoreCase("consulta")){%>		
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable"   >
  <tr class="fila-encabezado">
      
     <td width="10%">Articulo</td>     
     <td width="41%">Descripcion</td>
     <td width="12%"><div align="right">Disponible</div></td>
     <td width="12%"><div align="right">Reservado</div></td>
     <td width="12%"><div align="right">Existencia</div></td>
    <td width="17%"><div align="right">Unidad</div></td>

     
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
      <td class="fila-det-border" >&nbsp;<%=sCampos[1]%></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[2]%></div></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[3]%></div></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[4]%></div></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[5]%></div></td>			
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

