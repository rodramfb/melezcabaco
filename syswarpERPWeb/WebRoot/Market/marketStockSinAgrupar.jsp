<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: Stockstock
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Sep 04 09:21:33 GMT-03:00 2006 
   Observaciones: 
      .


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.Iterator" %> 
<%@ page import="java.math.*" %>
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "Articulos";
// variables de entorno
String pathskin = "vs/market.css";
String pathscript = "scripts";
// variables de paginacion
int i = 0;
Iterator iterStockstock   = null;
int totCol = 11; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("marketUsuario").toString();
%>
<jsp:useBean id="BMSXG"  class="ar.com.syswarp.web.ejb.BeanMarketStockSinAgrupar"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BMSXG" property="*" />
<%
 BMSXG.setResponse(response);
 BMSXG.setRequest(request);
 BMSXG.setIdempresa( new BigDecimal( session.getAttribute("marketEmpresa").toString() ));
 BMSXG.ejecutarValidacion();

// titulos para las columnas
tituCol[0] = "Codigo";//codigo_st
tituCol[1] = "Alias";//alias_st
tituCol[2] = "Descripcion";//descrip_st
tituCol[3] = "Descripcion Corta";//descri2_st
tituCol[4] = "Precio de Lista";// Precio de la lista asignada.
tituCol[5] = "Precio P.P.";//precipp_st
tituCol[6] = "Costo Ultima Compra";//cost_uc_st
tituCol[7] = "Precio Ultima Compra";//ultcomp_st
tituCol[8] = "Costo Rep.";//cost_re_st
tituCol[9] = "Precio Rep.";//reposic_st
tituCol[10] = "Codigo de Moneda";//nom_com_st

java.util.List Stockstock = new java.util.ArrayList();
Stockstock= BMSXG.getStockstockList();
iterStockstock = Stockstock.iterator();
%>

<script type="text/javascript" src="vs/lightbox/js/prototype.js"></script>
<script type="text/javascript" src="vs/lightbox/js/scriptaculous.js?load=effects,builder"></script>
<script type="text/javascript" src="vs/lightbox/js/lightbox.js"></script>
<link rel="stylesheet" href="vs/lightbox/css/lightbox.css" type="text/css" media="screen" />


 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <link rel="stylesheet" type="text/css" href="vs/Stylesheet.css" />
 <script>
 function sendToDetalle(codigo_st){
   document.frmStockXGrupo.codigo_st.value = codigo_st;
   document.frmStockXGrupo.action = "marketStockDetalle.jsp";
	 document.frmStockXGrupo.submit();
 }
 </script>


	
		<form action="marketStockSinAgrupar.jsp" method="POST" name="frmStockXGrupo">

			
			<table width="90%"  cellspacing="0" cellpadding="0" class="descripcion">
				<tr >
					<td class="fila-head" width="28%" >P&aacute;gina:
					  <select name="paginaSeleccion" class="combo-size-var">
              <%for(i=1; i<= BMSXG.getTotalPaginas(); i++){%>
              <%if ( i==BMSXG.getPaginaSeleccion() ){%>
              <option value="<%=i%>" selected><%=i%></option>
              <%}else{%>
              <option value="<%=i%>"><%=i%></option>
              <%}%>
              <%}%>
            </select></td>
					<td width="22%" ><input name = "cmpBuscar" id="cmpBuscar" type="text" value="<%= BMSXG.getCmpBuscar() %>"/> </td>
					<td width="40%" class="fila-head"><input name="ir" type="submit" id="ir" value="  >>  " class="boton" /></td>
				  <td width="22%" class="fila-head">Total de registros:</td>
				  <td width="10%" class="fila-head"><%= BMSXG. getTotalRegistros( ) %></td>
				</tr>
				<tr >
				  <td colspan="4" class="mensaje"><jsp:getProperty name="BMSXG" property="mensaje"/>   
			      <input type="hidden" name="codigo_fm" id="codigo_fm" value="<%= BMSXG.getCodigo_fm() %>" />					       
						<input name="codigo_gr" type="hidden" id="codigo_gr" value="<%=BMSXG.getCodigo_gr()%>" />
						<input name="cmpBuscar" type="hidden" id="cmpBuscar" value="<%=BMSXG.getCmpBuscar()%>" />
						<input name="codigo_st" type="hidden" id="codigo_st" value="" />
						&nbsp;
				  </td>
				</tr>
			</table>
			<table width="90%"  class="descripcion">
				<tr >
					
					 <td width="18%"><% 
 }
catch (Exception ex) {
   java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
   java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
   ex.printStackTrace(pw);
  System.out.println("ERROR (" + pagina + ") : "+ex);   
}%>
					   <%=tituCol[0]%></td>
					 <td width="58%"><%=tituCol[2]%></td>
					 <td width="18%"><%=tituCol[4]%></td>
			         <td width="5%">&nbsp;</td>
			  </tr>
				 <%
				 while(iterStockstock.hasNext()){
						String[] sCampos = (String[]) iterStockstock.next();%>
				 <tr > 
						
						<td class ="alt"><%=sCampos[0]%></td>
						<td ><a href="javascript:sendToDetalle('<%=sCampos[0]%>')" class="link"><%=sCampos[2]%></a>&nbsp;</td>
						<td class="alt"><%=sCampos[4]%></div></td>
						<% 
						 String imagenPath =""; 
						 if (sCampos[13]==null){ 
						         imagenPath = "../imagenes/default/gnome_tango/devices/imagen-no-disponible.jpg";
						      }   
						    else {
						    imagenPath ="../imagenes/general/" + sCampos[13];
						    }    
						%>
						
				        <td class="fila-det"><div align="right"><a href="<%=imagenPath%>" rel="lightbox"><img src="../imagenes/default/gnome_tango/devices/camera-photo.png" alt="Imagen" width="22" height="22" border="0" /></a></div></td>
				 </tr>
			<%
				 }%>
			  </table>	
	 </form>

