<%@page language="java"%>
<%@page import="java.sql.ResultSetMetaData"%>
<%@ page import="ar.com.syswarp.ejb.*"%>
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
<%@ page import="java.math.BigDecimal" %>
<% 
try{
int i = 0;


Report rep = new ReportBean();				



Strings str = new Strings();
String color_fondo ="";
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<%-- INSTANCIAR BEAN --%>
	<jsp:useBean id="BPF"
		class="ar.com.syswarp.web.ejb.BeanConsultaSociosAsignados" scope="page" />
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
 String titulo = "Consulta Socios Asignados";
	
		
		
 BPF.setResponse(response);
 BPF.setRequest(request);
 // ver esto BPF.setSession(session);
 BPF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BPF.ejecutarValidacion();
 java.sql.ResultSet Consulta1 ;
 java.sql.ResultSet Consulta2 ;
 java.sql.ResultSet Consulta3 ;
 java.sql.ResultSet Consulta4 ;
 java.sql.ResultSet Consulta5 ;
 java.sql.ResultSet Consulta6 ;

 Consulta1 = BPF.getMovimientosList();
 Consulta2 = BPF.getMovimientosListTotales();
 Consulta3 = BPF.getMovimientosListTotalesDS(); // grafico de tortas
 Consulta4 = BPF.getMovimientosListTotalesDS1(); // grafico de barras
 Consulta5 = BPF.getMovimientosListTotalesDS2(); // grafico de tortas x tm
 Consulta6 = BPF.getMovimientosListTotalesDS3(); // grafico de barras x tm
 
 %>
	<form action="ConsultaSocioAsignados.jsp" method="post" name="frm">
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
								<td width="17%" class="fila-det-border">Campa&ntilde;a
								  <input name="idcampania" type="hidden" class="campo" id="idcampania" value="<%=BPF.getIdcampania()%>" size="30" readonly>
								  
								  
								  <input name="fdesdeStr" type="hidden" class="campo" id="fdesdeStr" value="<%=BPF.getFdesdeStr()%>" size="30" readonly>
								  
								  <input name="fhastaStr" type="hidden" class="campo" id="fhastaStr" value="<%=BPF.getFhastaStr()%>" size="30" readonly>								  </td>
								<td width="20%" class="fila-det-border"><table width="23%" border="0">
                                  <tr class="fila-det-border">
                                    <td width="61%" ><input name="campania" type="text" class="campo" id="campania" value="<%=BPF.getCampania()%>" size="30" readonly></td>
                                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Clientes/lov_campania.jsp')" style="cursor:pointer"></td>
                                  </tr>
                                </table></td>
		  <td width="10%" class="fila-det-border"><input name="validar"
											type="submit" value="Consultar" class="boton"></td>
		  <td width="53%" class="fila-det-border">&nbsp;</td>
</tr>
					  </table>
			<input name="primeraCarga" type="hidden" value="false" >
	</form>

<%if (BPF.getAccion().equalsIgnoreCase("consulta")){%>		
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable"   >
   <%int r = 0;
   boolean hasTitulo = false;
   while(Consulta1.next()){
      ++r;     
      if (!hasTitulo){
        hasTitulo = true;
        java.sql.ResultSetMetaData rm = Consulta1.getMetaData();
                        
        %>
        <tr class="fila-encabezado">
          <%for (int zz=1; zz < rm.getColumnCount()+1; zz++){ %>
              <td width="12%"><%= rm.getColumnName(zz) %></td>
          <%}%>        
       </tr>        
        <%
      }
       
      String imagen ="";      
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <%java.sql.ResultSetMetaData rm = Consulta1.getMetaData();     %> 
     <%for (int zz=1; zz < rm.getColumnCount()+1; zz++){
       String valor = Consulta1.getString(rm.getColumnName(zz));
       if (valor==null) valor="--";
     %>
       <td height="22" class="fila-det-border" ><%=valor%></td>       
     <%}%>  
   </tr>
 <%}%>
   
   <% java.sql.ResultSetMetaData rm = Consulta2.getMetaData();
     if (Consulta2.next()){ 
   %>
   <tr  class="text-dos-bold" >
     <%for (int zz=1; zz < rm.getColumnCount()+1; zz++){
       String valor = Consulta2.getString(rm.getColumnName(zz));
       if (valor==null) valor="--";
     %>
       <td height="22" ><%=valor%></td>
      <%}%>        
     <%}%>   
   
   </tr>
   <tr>
     <td>          
          <div align="center"></div></td>
   </tr>
   </table>
   
   
<table width="100%" border="0" cellspacing="10" cellpadding="0">
  <tr>
    <td><div align="center"><img src="../reportes/reportes/<%= rep.GenerarGraficosPie3D("ConsultaSociosAsignadosPie.jpg","% de Asignacion por Categoria",Consulta3)%>"></div></td>
    <td><div align="center"><img src="../reportes/reportes/<%= rep.GenerarGraficosBar3D("ConsultaSociosAsignados3d.jpg","Totales por Categoria",Consulta4)%>"></div></td>
  </tr>
  <tr>
    <td><div align="center"><img src="../reportes/reportes/<%= rep.GenerarGraficosPie3D("ConsultaSociosAsignadosPieXTM.jpg","% de Asignacion por Telemarketer",Consulta5)%>"></div></td>
    <td><div align="center"><img src="../reportes/reportes/<%= rep.GenerarGraficosBar3D("ConsultaSociosAsignados3dXtm.jpg","Totales por Telemarketer",Consulta6)%>"></div></td>
  </tr>
</table>



<p>&nbsp;  </p>
<p>
  <%}

%>   
  
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

