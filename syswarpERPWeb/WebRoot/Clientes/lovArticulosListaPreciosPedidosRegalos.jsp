<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: proveedoArticulos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Aug 02 14:39:14 GMT-03:00 2006 
   Observaciones: 
      .


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.*" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%@ page import="java.math.BigDecimal" %>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "ARTICULOS";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterProveedoArticulos   = null;
int totCol = 17; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BPAA"  class="ar.com.syswarp.web.ejb.BeanLovArticulosListaPreciosPedidosRegalos"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BPAA" property="*" />
<%
 BPAA.setResponse(response);
 BPAA.setRequest(request);
 BPAA.setSession(session);
 BPAA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BPAA.ejecutarValidacion();

%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script >
<!--

function eliminarRegistro(){
  if(confirm("Confirma eliminar selección ?")){
		  document.frm.accion.value = "eliminar";
		  document.frm.submit();
		}
}

function confirmarDetalle(){
  if(confirm("Confirma datos ingresados ?")){
		  document.frm.accion.value = "confirmar";
		  document.frm.submit();
	}
}


function agregar(codigo_st, codigo_dt, paginaSeleccion){

  document.frm.codigo_st.value = codigo_st;
  document.frm.codigo_dt.value = codigo_dt;	
	document.frm.paginaSeleccion.value = paginaSeleccion;	
	document.frm.accion.value = 'agregar';
	document.frm.submit();
 
}

function getCantidadesArticulo(obj, articulo, indice){
  //parent.frames['iFrameCantidades'].document.forms['frmIframeStock'].submit();

  var frmIFrame = parent.frames['iFrameCantidades'].document.forms['frmIframeStock'];
	frmIFrame.articu_sb.value  = articulo;
	frmIFrame.deposi_sb.value  = obj.value;
	frmIFrame.indice.value  = indice;
  frmIFrame.accion.value = 'setexistencias';
  frmIFrame.submit();

}

//-->
</script>
</head>
<%

/*			<!-- codigo_st,alias_st,descrip_st,descri2_st, "
				+ "cost_re_st, cost_uc_st, cost_pp_st,precipp_st, ultcomp_st-->*/
// titulos para las columnas
tituCol[0] = "Cod.";
tituCol[1] = "Alias";
tituCol[2] = "Articulo";
tituCol[3] = "Desc. Alt.";
tituCol[4] = "% Desc.";
tituCol[5] = "Precio";
tituCol[6] = "Precio";
tituCol[7] = "Precio";
tituCol[8] = "Precio";
tituCol[9] = "Cta. Comp.";
tituCol[10] = "Es Serializable";
tituCol[11] = "Numero Serie";
tituCol[12] = "C.Depósito";
tituCol[13] = "Depósito";
tituCol[14] = "Disponible";
tituCol[15] = "Reserva";
tituCol[16] = "idempresa";

java.util.List ProveedoArticulos = new java.util.ArrayList();
ProveedoArticulos= BPAA.getProveedoArticulosList();
iterProveedoArticulos = ProveedoArticulos.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="lovArticulosListaPreciosPedidosRegalos.jsp" method="POST" name="frm"> 

<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr class="textGlobales">
                   <td colspan="3" class="textGlobales"><%=titulo%></td>
                </tr>
                <tr>
                   <td height="38" colspan="3">
                      <table width="100%" border="0" cellpadding="0" cellspacing="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BPAA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">Total de registros:&nbsp;<%=BPAA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=5; i<= 50 ; i+=5){%>
                                                    <%if(i==BPAA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BPAA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BPAA.getTotalRegistros()%>">Todos</option>
                                             </select>                                          </td>
                                          <td width="10%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                                       </tr>
                                    </table>                                 </td>
                              </tr>
                           </table>                        </td>
                     </tr>
                   </table>                </td>
            </tr> 
                <tr>
                  <td width="26%" class="text-globales">Disponible mayor a cero                    </td>
                  <td width="7%" class="text-globales"><input name="conExistencia" type="checkbox" id="conExistencia" value="S" <%= BPAA.getConExistencia().equals("S")  ? "checked"  : "" %>></td>
                  <td width="67%" class="text-globales">
                   <%if(BPAA.getTotalOcurrenciaEnEsquema()>0){%>
                    <a href="javascript:abrirVentana('lovPedidosEsquemasContieneArt.jsp?ocurrencia=<%=BPAA.getOcurrencia()%>', 'esquemas', 800, 450)"><!--img src="../imagenes/default/gnome_tango/categories/applications-other.png" width="20" height="20" border="0"--> Existen <%= BPAA.getTotalOcurrenciaEnEsquema() + "" %> Esquemas que cumplen criterio de busqueda</a>.  
                  <% } %>
                 </td>
                </tr>
                <%
                 if(BPAA.getIdcampacabe().longValue()>0){ %>
                <tr>
                  <td class="text-globales">Solo Art&iacute;culos de Campa&ntilde;a </td>
                  <td class="text-globales"><input name="soloCampania" type="checkbox" id="soloCampania" value="S" <%= BPAA.getSoloCampania().equals("S") ? "checked"  : "" %>></td>
                  <td class="text-globales">&nbsp;</td>
                </tr>
                <%} %>
                <tr>
                  <td height="34" colspan="3" class="text-globales"><div align="center">
                    <input name="paginaSeleccion" type="hidden" value="1">
                    P&aacute;gina: <%= Common.getPaginacion(BPAA.getTotalPaginas(),
		BPAA.getTotalRegistros(), BPAA.getPaginaSeleccion(), BPAA.getLimit() ,
		BPAA.getOffset()) %> </div></td>
                </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo">     <jsp:getProperty name="BPAA" property="mensaje"/>     </td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="2%" >&nbsp;</td>
     <td width="2%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
     <td width="2%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
     <td width="2%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="39%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="30%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[13]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[14]%></div></td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[15]%></div></td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[4]%>&nbsp;</div></td>
     <td width="3%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[5]%>&nbsp;</div></td>
     <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right">Final&nbsp;</div></td>
  </tr>
  
   <%int r = 0;
	 String codigo_stock = "";
   while(iterProveedoArticulos.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterProveedoArticulos.next(); 
      // estos campos hay que setearlos segun la grilla 
			if(!codigo_stock.equals(sCampos[0])){
        codigo_stock =	sCampos[0];		
        if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
        else color_fondo = "fila-det-verde";
			}%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" >
			  <div align="center">
				<%if(!str.esNulo(sCampos[9]).equals("")){%>
				<img src="../imagenes/default/nuevo.gif" width="16" height="16" title="Agregar al pedido ...." onClick="agregar('<%= sCampos[0] %>', '<%= sCampos[12] %>', <%= BPAA.getPaginaSeleccion() %>)">					    
        <%} else{%>		
				<img src="../imagenes/default/gnome_tango/status/stock_dialog-warning.png" width="18" height="18" title="Articulo no posee asociada cuenta de compras .....">		  
        <%} %>				  	 			
		</div>	  </td>
      <td class="fila-det-border" ><p><img src="../imagenes/default/gnome_tango/devices/camera_unmount.png" width="15" height="15" onClick="abrirVentana('../General/globalBlobImagenesAbm.jsp?soloImagen=true&tupla=<%=sCampos[16]%>', 'BLOB', 800, 450)" style="cursor:pointer"></p>      </td>
      <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/mimetypes/deb.png" title="Stock:" width="15" height="15"  onClick="abrirVentana('../Stock/ConsultasStockDepositos.jsp?codigo_st=<%=sCampos[0]%>', 'Stock', 750, 400)"></td>
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[2]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[13]%></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[14]%></div></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[15]%></div></td>
      <td class="fila-det-border" ><div align="right"><%=Common.getNumeroFormateado( Float.parseFloat( sCampos[4] ), 10, 2 )%>&nbsp;</div></td>
      <td class="fila-det-border" ><div align="right"><%=Common.getNumeroFormateado( Float.parseFloat( sCampos[5] ), 10, 2 )%>&nbsp;</div></td>
      <td class="fila-det-border" ><div align="right"><%= Common.getNumeroFormateado(Float.parseFloat(sCampos[5]) -  (  Float.parseFloat(sCampos[5]) * (Float.parseFloat(sCampos[4]) / 100f) ), 10, 2) %>&nbsp;</div></td>
   </tr>
<%
   }%> 
  </table>
	 <% 
   Hashtable htArticulosInOut = (Hashtable) session.getAttribute("htArticulosInOut");
	 if(htArticulosInOut != null && !htArticulosInOut.isEmpty()){
	   %> 	 
		 &nbsp; 
		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr  class="text-globales" height="3">
				<td colspan="8"> </td>
			</tr>		
			<tr  class="fila-det-bold">
			 <td width="3%">&nbsp;Sel.</td>
				<td width="6%">&nbsp; Cód.</td>
				<td width="46%">&nbsp; Descripción</td>
			 <td width="22%">D&eacute;posito</td>				
				<td width="7%"><div align="right">Disponible</div></td>
				<td width="7%"><div align="right">Reserva</div></td>
				<td width="9%"><div align="right">Cantidad </div></td> 
			</tr>
			<tr  class="text-globales" height="3">
				<td colspan="7"></td>
		  </tr>		
		<%
	   			Enumeration en = htArticulosInOut.keys();
					int indice = 0;
					while (en.hasMoreElements()) {
					  
						String key = (String) en.nextElement();
						String [] articulos = (String []) htArticulosInOut.get(key);
						/*Hashtable htDepositos = BPAA.getHtDepositos();
						Enumeration enumDepositos = htDepositos.keys();*/

						  %>
		<tr class="fila-det">
			<td class="fila-det-border"><input name="delKey" type="checkbox" id="delKey" value="<%= key %>" class="campo"></td>
			<td class="fila-det-border">&nbsp;<%= articulos[0] %><input  name="keyHashDatosArticulo" type="hidden" value="<%= key %>"></td>

			<td class="fila-det-border">&nbsp;<%= articulos[2] %></td>
		 <td class="fila-det-border"><%= articulos[22] %><% 
		                                                    /* <select name="deposito" id="deposito" class="campo"   onChange=" getCantidadesArticulo(this, '< %= articulos[0] % >', < %= indice % >);"  >
																																<option value="-1">Seleccionar</option>
																																< % 
																											            Iterator iter = BPAA.getListDepositos().iterator();					
																																	while(iter.hasNext()){
																																			String keyDep[] = (String[]) iter.next();
																																			String sel = "";

																																			if(articulos[9].equals(keyDep[0])) sel = "selected";
																																			else sel = "";
																																			
																																			  % >
																													        <option value="< %= keyDep[0] % >" < %= sel % >>< %= keyDep[1] % ></option>
																																					 < %
																																	}
																																																															
																																 % >
																														 </select>*/
																														  %>		 </td>
			
			<td class="fila-det-border"><div align="right"><%= articulos[7] %>
			  <!--input name="existencia" value="" type="text" id="existencia" size="8" maxlength="10" class="campo" readonly style="text-align:right"-->
			  </div></td>
			<td class="fila-det-border"><div align="right"><%= articulos[8] %>
			  <!--input name="reserva" value="" type="text" id="reserva" size="8" maxlength="10" class="campo" readonly style="text-align:right"-->
			  </div></td>
			<td class="fila-det-border"><div align="right">
			    <input name="cantidad" type="text" class="campo" style="text-align:right" value="<%= articulos[10] %>" size="6" maxlength="6">
	    </div></td>
		</tr>							
		<%			 indice++;
					}
	  %>
			<tr  class="text-globales" height="3">
				<td colspan="7"></td>
		  </tr>					
			<tr  class="fila-det-bold">
			 <td height="40">&nbsp;</td>
			 <td height="40"><input name="eliminar" type="button" class="boton" id="eliminar" value="Eliminar" onClick="eliminarRegistro();"></td>
		   <td height="40">&nbsp; <input name="confirmar" type="button" class="boton" id="confirmar" value="Confirma" onClick="confirmarDetalle();"></td>
		   <td height="40">&nbsp;</td>
		   <td>&nbsp;</td>
		   <td>&nbsp;</td>
		   <td height="40">&nbsp;</td>
			</tr>			
	</table>
<% } %>
   <input name="accion" value="" type="hidden">
   <input name="primeraCarga" value="false" type="hidden">	 
	 <input type="hidden" name="codigo_st" value="">
 	 <input type="hidden" name="codigo_dt" value="">
	 <input type="hidden" name="idlista" value="<%= BPAA.getIdlista() %>">
	 <input name="idzona" type="hidden" id="idzona" value="<%= BPAA.getIdzona() %>">
	 <input name="idcampacabe" type="hidden" id="idcampacabe" value="<%= BPAA.getIdcampacabe() %>">
	 <%  
			if(BPAA.getAccion().equalsIgnoreCase("confirmar")  
			   && BPAA.getMensaje().equals("") ){%>
			  <script>
					   opener.document.frm.submit(); 
						  this.close();
					</script>			
			<%  
			}%>
			
</form>  
<iframe src="iframeGetStockDeposito.jsp" name="iFrameCantidades" 
 width="1px" height="1px"  id="iFrameCantidades" align="middle" style="visibility:hidden">
</iframe>
	
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

