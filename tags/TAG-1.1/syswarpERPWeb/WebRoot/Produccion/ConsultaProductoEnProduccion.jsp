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
		class="ar.com.syswarp.web.ejb.BeanProduccionConsultoEnProduccion" scope="page" />
	<head>
		<title>Consulta de Ordenes de Produccion para el articulo </title>
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
 String titulo = "Consulta de Ordenes de Produccion ";
	
		
		
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
		<form action="ConsultaProductoEnProduccion.jsp" method="post" name="frm">
		<input name="accion" type="hidden" value="consulta">
<%--			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				align="center">
				<tr> 
				  <td>
				    <table width="100%" border="0" cellspacing="0" cellpadding="0"
							align="center">
				      <tr class="text-globales">
				      <td>--%>
				      <table width="100%" border="0"  cellpadding="0"
										cellspacing="0" class="text-globales">
				        <tr>
				          <td><%= titulo%></td>
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
				        <td width="7%" class="fila-det-border">
				          Articulo: (*)								</td>
					      <td width="41%" class="fila-det-border">
						      <table width="79%" border="0">
						          <tr class="fila-det-border">
							          <td width="23%">
							              <input name="articulodesde" type="text" class="campo"
													id="articulodesde" value="<%=BPF.getArticulodesde()%>" size="10" maxlength="10"
													readonly>											</td>
								      <td width="48%"><input name="descrip_st" type="text" class="campo"
													id="descrip_st" value="<%=BPF.getDescrip_st()%>" size="40" maxlength="100"
													readonly></td>
			                <td width="29%"><img src="../imagenes/default/gnome_tango/actions/filefind.png"
													width="22" height="22"
													onClick="abrirVentana('../Stock/lov_articulo.jsp', 'articulo', 750, 400)"
													style="cursor:pointer"> </td>
					          </tr>
		            </table>							  </td>
							      
							  
					      <td width="13%" class="fila-det-border"><input name="validar"
											type="submit" value="Consultar" class="boton"></td>
					      <td width="39%" class="fila-det-border">&nbsp;</td>
				      </tr>
            </table>
				    <input name="primeraCarga" type="hidden" value="false" >
				  
	</form>

	

 <!-- detalle -->
 <%if (BPF.getAccion().equalsIgnoreCase("consulta")){%>
 <table width="100%" border="0" align="center" cellpadding="0" cellspacing="2" name="rsTable"   >

   <tr class="text-dos-bold">
     <td colspan="12" valign="top">DETALLE PARA ARTICULO: <%=BPF.getArticulodesde() + "-" + BPF.getDescrip_st() %></td>
   </tr>
   


   <tr class="text-dos-bold">
     <td class="fila-det-border"><div align="right">idOP</div></td>
	 <td class="fila-det-border"><div align="right">Esquema</div></td>
	 <td class="fila-det-border"><div align="center">Tipo</div></td>
	 <td class="fila-det-border"><div align="right">Calc.</div></td>
	 <td class="fila-det-border"><div align="right">Real.</div></td>
	 <td class="fila-det-border"><div align="right">Stock</div></td>
	 <td class="fila-det-border"><div align="center">F.Orden</div></td>
	 <td class="fila-det-border"><div align="right">idCliente</div></td>
	 <td class="fila-det-border"><div align="center">Cliente</div></td>
	 <td class="fila-det-border"><div align="right">Estim.</div></td>
	 <td class="fila-det-border"><div align="center">F.Prom.</div></td>
 	 <td class="fila-det-border"><div align="right">F.Emis.</div></td>     
   </tr>
 
   
   <%int r = 0;
   while(iterConsulta1.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterConsulta1.next(); 
       String imagen ="";      
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
   <td class="fila-det-border"><div align="right"><%=sCampos[0]%></div></td>
	 <td class="fila-det-border"><div align="right"><%=sCampos[1]%></div></td>
	 <td class="fila-det-border"><div align="center"><%=sCampos[2]%></div></td>
	 <td class="fila-det-border"><div align="right"><%=sCampos[3]%></div></td>
	 <td class="fila-det-border"><div align="right"><%=sCampos[4]%></div></td>
	 <td class="fila-det-border"><div align="right"><%=sCampos[5]%></div></td>
	 <td class="fila-det-border"><div align="center"><%=sCampos[6]%></div></td>
	 <td class="fila-det-border"><div align="right"><%=sCampos[7]%></div></td>
	 <td class="fila-det-border"><%=sCampos[8]%></td>
	 <td class="fila-det-border"><div align="right"><%=sCampos[10]%></div></td>
	 <td class="fila-det-border"><div align="center"><%=sCampos[11]%></div></td>
 	 <td class="fila-det-border"><div align="right"><%=sCampos[12]%></div></td>
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

