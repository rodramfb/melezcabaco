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
		class="ar.com.syswarp.web.ejb.BeanConsultaSeleccionLLamadas" scope="page" />
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
 String titulo = "Consulta Seleccion LLamadas";
	
		
		
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
		<form action="Consultaseleccionllamadas.jsp" method="post" name="frm">
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
							</tr><tr class="fila-det">
						    <td width="13%" height="22" class="fila-det-border">Campa&ntilde;a
							  <input name="idcampania" type="hidden" id="idcampania" value="<%=BPF.getIdcampania()%>"></td>
						    <td width="87%" class="fila-det-border"><table width="23%" border="0" cellpadding="0" cellspacing="0">
                              <tr class="fila-det-border">
                                <td width="61%" ><input name="campania" type="text" class="campo" id="campania" value="<%=BPF.getCampania()%>" size="30" readonly></td>
                                <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_campania4.jsp', 'campa', 800, 450)" style="cursor:pointer"></td>
                              </tr>
                            </table> </td>
					      <tr class="fila-det">
		                    <td height="22" class="fila-det-border">Cliente
							  <input name="idcliente" type="hidden" id="idcliente" value="<%=BPF.getIdcliente()%>"></td>
		                    <td class="fila-det-border"> <table width="23%" border="0" cellpadding="0" cellspacing="0">
                              <tr class="fila-det-border">
                                <td width="61%" ><input name="cliente" type="text" class="campo" id="cliente" value="<%=BPF.getCliente()%>" size="30" readonly></td>
                                <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_clientes.jsp', 'vendedor', 800, 450)" style="cursor:pointer"></td>
                              </tr>
                            </table></td>
							
							
							
							<tr class="fila-det">
		                    <td height="22" class="fila-det-border">Resultado
							  <input name="idresultado" type="hidden" id="idresultado" value="<%=BPF.getIdresultado()%>"></td>
		                    <td class="fila-det-border"> <table width="23%" border="0" cellpadding="0" cellspacing="0">
                              <tr class="fila-det-border">
                                <td width="61%" ><input name="resultado" type="text" class="campo" id="resultado" value="<%=BPF.getResultado()%>" size="30" readonly></td>
                                <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_resultado.jsp', 'vendedor', 800, 450)" style="cursor:pointer"></td>
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
     <td width="6%">C.llamado</td>  
     <td><div align="center">Cliente</div></td>
	 <td><div align="center">Razon</div></td>
     <td>Fecha de Ingreso </td>
     <td>Fecha Rellamada </td>
     <td>Estado</td>
     <td>Motivo</td>
     <td>Categoria</td>
     <td>Observaciones</td>
     <td>&nbsp;</td>
  </tr> 
  <% BigDecimal totalgeneral = new BigDecimal(0); %> 
   <%int r = 0;
   while(iterConsulta1.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterConsulta1.next(); 
       String imagen ="";      
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td height="22" class="fila-det-border" >&nbsp;<%=sCampos[0]%></td>
      <td width="12%" class="fila-det-border" ><%=sCampos[1]%></td>
	  <td width="18%" class="fila-det-border" ><%=sCampos[2]%></td>
      <td width="15%" class="fila-det-border" ><%=sCampos[3]%></td>
      <td width="14%" class="fila-det-border" ><%=sCampos[4]%></td>
      <td width="6%" class="fila-det-border" ><%=sCampos[5]%></td>
      <td width="5%" class="fila-det-border" ><%=sCampos[6]%></td>
      <td width="8%" class="fila-det-border" ><%=sCampos[7]%></td>
      <td width="10%" class="fila-det-border" ><%=sCampos[8]%></td>
      <td width="6%" class="fila-det-border" >&nbsp;</td>
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

