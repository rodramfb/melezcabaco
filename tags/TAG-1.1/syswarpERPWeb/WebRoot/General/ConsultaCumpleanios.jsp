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
		class="ar.com.syswarp.web.ejb.BeanConsultaCumpleanios" scope="page" />
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
 String titulo = "Proceso de Cumpleaños";
	
		
		
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
		<form action="ConsultaCumpleanios.jsp" method="post" name="frm">
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
											
										  <td width="43">&nbsp;</td>
											<td width="42">
										
										  </td>
										</tr>
								  </table>
									
								</td>
							</tr>
					  </table>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr class="fila-det-bold-rojo">
								<td class="fila-det-border">&nbsp;</td>
							  <td class="fila-det-border"><jsp:getProperty name="BPF" property="mensaje" />                              </td>
							</tr>
<tr class="fila-det">
								<td width="13%" class="fila-det-border">A&ntilde;o</td>
								<td width="87%" class="fila-det-border"><input name="anio" type="text" class="campo" id="anio" value="<%=BPF.getAnio()%>" size="9" maxlength="9"  ></td>
						  </tr><tr class="fila-det">
						    <td width="13%" height="22" class="fila-det-border">Mes
							<input name="mes" type="hidden" id="mes" value="<%=BPF.getMes()%>"></td>
						    <td width="87%" class="fila-det-border"><table width="23%" border="0" cellpadding="0" cellspacing="0">
                              <tr class="fila-det-border">
                                <td width="61%" ><input name="des_mes" type="text" class="campo" id="des_mes" value="<%=str.esNulo(BPF.getDes_mes())%>" size="30" readonly></td>
                                <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_meses.jsp', 'meses', 800, 450)" style="cursor:pointer"></td>
                              </tr>
                            </table></td>
					      <tr class="fila-det">
		                      <td height="46" class="fila-det-border">&nbsp;</td>
		                      <td class="fila-det-border"><input name="validar"
											type="submit" value="Consultar" class="boton"></td>
                      </table>
			<input name="primeraCarga" type="hidden" value="false" >
		</form>
        <%if (BPF.getAccion().equalsIgnoreCase("consulta")){ %>		
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable"   > 




  <tr class="fila-encabezado">
     <td width="9%">Cliente</td>  
     <td><div align="center">Razon</div></td>
	 <td><div align="center">Domicilio</div></td>
     <td>Localidad</td>
     <td>Provincia</td>
     <td>Estado</td>
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
      <td width="29%" class="fila-det-border" ><%=sCampos[1]%></td>
	  <td width="21%" class="fila-det-border" ><%=sCampos[2]%></td>
      <td width="14%" class="fila-det-border" ><%=sCampos[3]%></td>
      <td width="12%" class="fila-det-border" ><%=sCampos[4]%></td>
      <td width="15%" class="fila-det-border" ><%=sCampos[5]%></td>
   </tr>
<%
   }%>
      <tr class="fila-encabezado">
     <td width="9%">Total Registros </td>  
     <td><div align="right"></div></td>
	 <td><div align="right"></div></td>
	 <td><div align="left"></div></td>
     <td>&nbsp;</td>
     <td><span class="fila-det-border"><%=r%></span></td>
  </tr>
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

