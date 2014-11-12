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
Iterator iterConsulta2   = null;
Iterator iterConsulta3   = null;

Strings str = new Strings();
String color_fondo ="";
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<%-- INSTANCIAR BEAN --%>
	<jsp:useBean id="BPF"
		class="ar.com.syswarp.web.ejb.BeanStockConsultaFamiliaGrupoDeposito" scope="page" />
	<head>
		<title>Consuta de Existencias por Deposito</title>
		 <link rel = "stylesheet" href = "<%= pathskin %>">
		<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
		<link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
		<script language="JavaScript" src="vs/forms/forms.js"></script>
		<script language="JavaScript" src="vs/overlib/overlib.js"></script>
		
		<script>
		
		
		function limpiarText(){

		   document.frm.d_grupo_st.value = '';
           document.frm.grupo_st.value = '0';
		   
		   document.frm.d_codigo_fm.value = '';
           document.frm.codigo_fm.value = '0';	
		   
		   document.frm.deposito.value = '';
           document.frm.iddeposito.value = '0';			   	   
		
		}
		
		function getFamilias(){

           document.frm.d_grupo_st.value = '';
           document.frm.grupo_st.value = '';		   
		   abrirVentana('../Stock/lov_familia.jsp', 'FAMILIA', 750, 350);
		   
		}
		
		
		function mostrarLOVDETA(pagina) {
	     frmLOV = open(pagina,'winLOV','scrollbars=yes,resizable=yes,width=800,height=450,status=yes');
	     if (frmLOV.opener == null) 
		 frmLOV.opener = self;
         }	
		 
		 function validarfamilia(codigo_fm) {
		    if (trim(codigo_fm) == "" || codigo_fm < 1)
			   alert ('Por favor seleccione la Familia.');
			else 
		      abrirVentana('../Stock/lov_FamiliaGrupo.jsp?codigo_fm='+codigo_fm, 'GRUPO', 750, 350 );
		 }
		 </script>
		 
		<meta http-equiv="Content-Type"	content="text/html; charset=iso-8859-1">
	</head>
	<BODY>
		<div id="popupcalendar" class="text"></div>
		<%-- EJECUTAR SETEO DE PROPIEDADES --%>
		<jsp:setProperty name="BPF" property="*" />
		<% 
 String titulo = "Consulta de Familia y Grupo.";
	
		
		
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
		<form action="stockConsultaFamiliaGrupo.jsp" method="post" name="frm">
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
									Familia: (*)								</td>
								<td width="30%" class="fila-det-border">
									<table width="79%" border="0">
										<tr class="fila-det-border">
											<td width="61%">
												<input name="d_codigo_fm" type="text" class="campo"
													id="d_codigo_fm" value="<%=BPF.getD_codigo_fm() %>" size="30"
													readonly>											</td>
											<td width="39%">
												<img src="../imagenes/default/gnome_tango/actions/filefind.png"
													width="22" height="22"
													onClick="getFamilias();"
													style="cursor:pointer">	<input name="codigo_fm" type="hidden" id="codigo_fm"
												value="<%=BPF.getCodigo_fm() %>">										</td>
										</tr>
		  </table>							  </td>
							  
							  
								<td width="47%" class="fila-det-border">&nbsp;</td>
								<td width="12%" class="fila-det-border">&nbsp;</td>
						  </tr>							
							<tr class="fila-det">
								<td width="11%" class="fila-det-border">
									Grupo: (*)								</td>
								<td width="30%" class="fila-det-border">
									<table width="79%" border="0">
										<tr class="fila-det-border">
											<td width="61%">
												<input name="d_grupo_st" type="text" class="campo"
													id="d_grupo_st" value="<%=BPF.getD_grupo_st() %>" size="30"
													readonly>											</td>
											<td width="39%">
												<img src="../imagenes/default/gnome_tango/actions/filefind.png"
													width="22" height="22"
													onClick="validarfamilia(document.frm.codigo_fm.value)"
													style="cursor:pointer">	<input name="grupo_st" type="hidden" id="grupo_st"
												value="<%=BPF.getGrupo_st() %>">		  								</td>
										</tr>
							  </table>							  </td>  
							  
							  
								<td width="47%" class="fila-det-border">&nbsp;</td>
								<td width="12%" class="fila-det-border">&nbsp;</td>
							</tr>
							<tr class="fila-det">
								<td width="11%" class="fila-det-border">
									Deposito: (*)								</td>
								<td width="30%" class="fila-det-border">
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
													style="cursor:pointer">	<input name="iddeposito" type="hidden" id="iddeposito"
												value="<%=BPF.getIddeposito() %>">										</td>
										</tr>
							  </table>							  </td>
							  
							  
								<td width="47%" class="fila-det-border">&nbsp;</td>
								<td width="12%" class="fila-det-border">&nbsp;</td>
							</tr><tr class="fila-det">
							  <td width="11%" height="46" class="fila-det-border">&nbsp;</td>
						    <td width="30%" class="fila-det-border"><input name="validar"
											type="submit" value="Consultar" class="boton"></td>
							  <td width="47%" class="fila-det-border"><input name="limpiar"
											type="button" class="boton" id="limpiar" value="Limpiar" onClick="limpiarText();"></td>
							  <td width="12%" class="fila-det-border">&nbsp;						</td>
		  </table>
			<input name="primeraCarga" type="hidden" value="false" >
		</form>

<%if (BPF.getAccion().equalsIgnoreCase("consulta")){%>		
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable"   >
  <tr class="fila-encabezado">
     <td width="12%">Deposito</td>  
     <td colspan="2"><div align="center">&nbsp;Articulo</div></td>
     <td width="10%"><div align="right">Disponible</div></td>   
     <td width="10%"><div align="right">Reservado</div></td>
     <td width="10%"><div align="right">Existencia</div></td>
    <td width="20%">&nbsp;Unidad</td>
	 <td width="5%">&nbsp;</td>
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
      <td width="8%" class="fila-det-border" ><%=sCampos[1]%></td>
      <td width="39%" class="fila-det-border" ><div align="left"><%=sCampos[2]%></div></td>
      <td class="fila-det-border" ><div align="right"><%=Common.getGeneral().getNumeroFormateado(Float.parseFloat(sCampos[3]),10, 2)%>&nbsp;</div></td>	
      <td class="fila-det-border" ><div align="right"><%=Common.getGeneral().getNumeroFormateado(Float.parseFloat(sCampos[4]),10, 2)%>&nbsp;</div></td>
      <td class="fila-det-border" ><div align="right"><%=Common.getGeneral().getNumeroFormateado(Float.parseFloat(sCampos[5]),10, 2)%>&nbsp;</div></td>
	  <td class="fila-det-border" ><div align="left">&nbsp;<%=sCampos[6]%></div></td>	
	  <td class="fila-det-border" ><div align="center">
	  <img src="../imagenes/default/gnome_tango/apps/config-users.png" width="18" height="18" title="<%=sCampos[7]%>">
     </div></td></tr>
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

