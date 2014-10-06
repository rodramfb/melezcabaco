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
Iterator iterConsulta   = null;

Strings str = new Strings();
String color_fondo ="";
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<%-- INSTANCIAR BEAN --%>
	<jsp:useBean id="BPF"
		class="ar.com.syswarp.web.ejb.BeanStockConsultaPuntoMinimo" scope="page" />
	<head>
		<title>Consuta de Punto Minimo de Stock</title>
		 <link rel = "stylesheet" href = "<%= pathskin %>">
		 <link rel = "stylesheet" href = "../imagenes/default/erp-style.css">
		<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
		<link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
		<script language="JavaScript" src="vs/forms/forms.js"></script>
		<script language="JavaScript" src="vs/overlib/overlib.js"></script>
		
		<script>
		
		  function enviarConsulta(){
		  
		    document.frm.accion.value = 'consulta';
			document.frm.submit();
		  
		  }

		</script>
		 
		<meta http-equiv="Content-Type"	content="text/html; charset=iso-8859-1">
	</head>
	<BODY>
		<div id="popupcalendar" class="text"></div>
		<%-- EJECUTAR SETEO DE PROPIEDADES --%>
		<jsp:setProperty name="BPF" property="*" />
		<% 
 String titulo = "Consuta de Punto Minimo de Stock";
	
		
		
 BPF.setResponse(response);
 BPF.setRequest(request);
 // ver esto BPF.setSession(session);
 BPF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BPF.ejecutarValidacion();
 java.util.List Consulta = new java.util.ArrayList();

 Consulta = BPF.getMovimientosList();
 iterConsulta = Consulta.iterator();
 
 %>
		<form action="stockConsultaPuntoMinimo.jsp" method="post" name="frm">
			<input name="accion" type="hidden" value="">
			
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
								<td width="10%" class="fila-det-border">&nbsp;								</td>
								<td colspan="3" class="fila-det-border">
									<jsp:getProperty name="BPF" property="mensaje" />
&nbsp;																								</td>
							</tr>
							
							
							
							
							
							
							
							
							
							<tr class="fila-det">
							  <td class="fila-det-border"> Dep&oacute;sito  (*) </td>
							  <td width="34%" class="fila-det-border"><table width="56%" border="0">
                                  <tr class="fila-det-border">
                                    <td width="31%"><input name="depositodesde" type="text" class="campo"
													id="depositodesde" value="<%=BPF.getDepositodesde() %>" size="30"
													readonly></td>
                                    <td width="69%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" 
													width="22" height="22"
													style="cursor:pointer"
													onClick="abrirVentana('../Stock/lov_deposito_desde_informe.jsp', 'depo', 750, 350)"><input name="iddepositodesde" type="hidden" id="iddepositodesde"
												value="<%=BPF.getiddepositodesde() %>"> </td>
                                    
                                  </tr>
                              </table></td>
							  <td width="12%" class="fila-det-border"><input name="validar" type="button" value="Consultar" class="boton" onClick="enviarConsulta();"></td>
							  <td width="44%" class="fila-det-border">&nbsp;</td>
						  </tr>
							<%--
						
						
						 <tr class="fila-det">
								<td width="14%" class="fila-det-border">
									Articulo Desde: (*)								</td>
								<td width="38%" class="fila-det-border">
									<table width="82%" border="0">
										<tr class="fila-det-border">
											<td width="61%">
												<input name="descrip_desde_st" type="text" class="campo"
													id="descrip_desde_st" value="<%=BPF.getdescrip_desde_st()%>" size="50"
													readonly>											</td>
											<td width="39%">
												<img src="../imagenes/default/gnome_tango/actions/filefind.png"
													width="22" height="22"
													onClick="mostrarLOV('../Stock/lov_articulo_desde_informe.jsp')"
													style="cursor:pointer">											</td>
											<input name="codigo_desde_st" type="hidden" id="codigo_desde_st"
												value="<%=BPF.getcodigo_desde_st() %>">
										</tr>
					        </table>							  </td>
							  
							  
								<td width="13%" class="fila-det-border">&nbsp;</td>
                                <td width="34%" class="fila-det-border">&nbsp;</td>
							</tr> 
							
							--%>







							<%--
							 <tr class="fila-det">
<td height="28" class="fila-det-border"> Articulo Hasta: (*) </td>
<td class="fila-det-border"><table width="82%" border="0">
<tr class="fila-det-border">
<td width="61%"><input name="descrip_hasta_st" type="text" class="campo"
													id="descrip_hasta_st" value="<%=BPF.getDescrip_hasta_st() %>" size="50"
													readonly></td>
<td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" title="a"
													width="22" height="22"
													style="cursor:pointer"
													onClick="mostrarLOV('../Stock/lov_articulo_hasta_informe.jsp')"> </td>
<input name="codigo_hasta_st" type="hidden" id="codigo_hasta_st"
												value="<%=BPF.getCodigo_hasta_st() %>">
</tr>
</table></td>
<td class="fila-det-border"> Deposito Hasta : (*) </td>
<td class="fila-det-border"><table width="52%" border="0">
<tr class="fila-det-border">
<td width="52%"><input name="depositohasta" type="text" class="campo"
													id="depositohasta" value="<%=BPF.getDepositohasta() %>" size="30"
													readonly></td>
<td width="48%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" 
													width="22" height="22"
													style="cursor:pointer"
													onClick="mostrarLOV('../Stock/lov_deposito_hasta_informe.jsp')"> </td>
<input name="iddepositohasta" type="hidden" id="iddepositohasta"
												value="<%=BPF.getIddepositohasta() %>">
</tr>
</table></td>--%>

						
	<td width="10%"></td>
	</tr> 
 </table>

			<input name="primeraCarga" type="hidden" value="false" >
		</form>

<%if (BPF.getAccion().equalsIgnoreCase("consulta")){%>		
<table width="100%" border="0" cellspacing="1" cellpadding="1" >
  <tr class="text-dos-bold">
    <td colspan="7" >Total Registros: <%= BPF.getTotalRegistros() %></td>
  </tr>
  <tr class="fila-encabezado">  
     <td width="2%"><div align="center"></div></td>
     <td width="4%">Articulo</td>   
     <td width="46%">Descripcion</td>     
     <td width="15%">U.M.</td>
     <td width="20%">Deposito</td>
     <td width="5%" bgcolor="#FFFF99">M&iacute;n..</td>
     <td width="8%">Disponible</td>     
   </tr>
  <%int r = 0;
   while(iterConsulta.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterConsulta.next();       
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>     
	  <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
	    <td class="fila-det-border">
		    <div align="center">
		      <% if (new BigDecimal(sCampos[5]).compareTo(new BigDecimal(0)) < 0 ){ %>
		        <img src="../imagenes/default/gnome_tango/status/gtk-dialog-warning.png" title="Observaciones: Stock Inconsistente"  width="18" height="18">    
		        <%} else {%>
	          <img src="../imagenes/default/gnome_tango/colors.orange.icon.gif"  width="10" height="10" title="Stock por debajo del punto mínimo.">
		        <%}%>	    
	      </div></td>    
        <td class="fila-det-border" ><%=sCampos[0]%></td>      
        <td class="fila-det-border" ><%=sCampos[1]%></td>      
        <td class="fila-det-border" ><%=sCampos[2]%></td>      			
        <td class="fila-det-border" ><%=sCampos[4]%></td>
        <td align="right" bgcolor="#FFFF99" class="fila-det-border" ><%=sCampos[3]%></td>      			      
        <td class="fila-det-border"  align="right" ><%=sCampos[5]%></td>      			      
      </tr>

   <%}
     %>
	 </table>
	 <%
   }
   %>			
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

