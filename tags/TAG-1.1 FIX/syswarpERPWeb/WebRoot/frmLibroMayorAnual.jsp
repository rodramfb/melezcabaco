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
Iterator iterConsulta2   = null;

Strings str = new Strings();
String color_fondo ="";
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
String idejercicio = session.getAttribute("ejercicioActivo").toString();

%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<%-- INSTANCIAR BEAN --%>
	<jsp:useBean id="BPF"
		class="ar.com.syswarp.web.ejb.BeanContableLibroMayor" scope="page" />
	<head>
		<title>Consulta - Libro Mayor</title>
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
 String titulo = "Consulta - Libro Mayor";
	
		
		
 BPF.setResponse(response);
 BPF.setRequest(request);
 // ver esto BPF.setSession(session);
 BPF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BPF.setIdejercicio(new BigDecimal(idejercicio));
 BPF.ejecutarValidacion();
 java.util.List Consulta2 = new java.util.ArrayList();
 Consulta2 = BPF.getMovimientosList();
 iterConsulta2 = Consulta2.iterator();
 %>
		<form action="frmLibroMayorAnual.jsp" method="post" name="frm">
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
									  </td>
											<td width="50">&nbsp;											</td>
											<td width="43">
												
										  </td>
											<td width="42">
										
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
								<td width="16%" class="fila-det-border">
									Cuenta Contable: (*)								</td>
							  <td width="32%" class="fila-det-border"><table width="79%" border="0">
                                <tr class="fila-det-border">
                                  <td width="61%"><input name="cuenta" type="text" class="campo"
													id="cuenta" value="<%= BPF.getCuenta() %>" size="30"
													readonly>
                                  </td>
                                  <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" 
													width="22" height="22"
													style="cursor:pointer"
													onClick="mostrarLOV('lov_ccontables.jsp')"> </td>
                                  <input name="idcuenta" type="hidden" id="idcuenta"
												value="<%=BPF.getIdcuenta() %>">
                                </tr>
                              </table></td>
							  
							  
								<td width="15%" class="fila-det-border">&nbsp;</td>
							    <td width="37%" class="fila-det-border">&nbsp;</td>
							</tr>
							<tr class="fila-det">
								<td width="16%" class="fila-det-border">							  </td>
								<td width="32%" class="fila-det-border">
									<table width="79%" border="0">
										<tr class="fila-det-border">
											<td width="61%">										  </td>
											<td width="39%">										  </td>
										</tr>
							  </table>							  </td>
							  
							  
					
								<td width="15%" class="fila-det-border">&nbsp;</td>
							  <td width="37%" class="fila-det-border">&nbsp;</td>
							
							
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr class="fila-det">
							  
									
									<td width="13%">
									 <input name="validar"
											type="submit" value="Consultar Movimientos" class="boton">								</td>
							</tr>
						</table>
						
					
				
		  </table>
			<input name="primeraCarga" type="hidden" value="false" >
		</form>

<%if (BPF.getAccion().equalsIgnoreCase("consulta")){%>		
 
 
 
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable"   >
  <tr class="fila-encabezado">  
     <td width="10%">&nbsp;</td>   
     <td width="10%">Fecha</td>
     <td width="10%">Id.Asiento</td>
     <td width="10%">Renglon</td>     
     <td width="20%">Debe</td>
     <td width="20%">Haber</td>
     <td width="20%">Acumulado</td>
   </tr>
  <%int r = 0;
   while(iterConsulta2.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterConsulta2.next(); 
      // estos campos hay que setearlos segun la grilla 
      String imagen ="";
      
      if(sCampos[4].equalsIgnoreCase("entrada"))imagen = "back.png"; else imagen = "next.png";
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" >&nbsp;</td>
      
      <td class="fila-det-border" >&nbsp;<%=sCampos[5]%></td>   
      <td class="fila-det-border" >&nbsp;<%=sCampos[6]%></td>  
       <td class="fila-det-border">&nbsp;<%=sCampos[7]%></td> 
      <td class="fila-det-border">&nbsp;<%=sCampos[8]%></div></td>     
      <td class="fila-det-border">&nbsp;<%=sCampos[9]%></td>
      <td class="fila-det-border">&nbsp;<%=sCampos[9]%></td>

   </tr>
   <%
   }%>
   
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

