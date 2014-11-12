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
int totCol = 12; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BPAA"  class="ar.com.syswarp.web.ejb.BeanMovInOutArticulosLov"   scope="page"/>
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


function agregar(codigo_st){

  document.frm.codigo_st.value = codigo_st;
	document.frm.accion.value = 'agregar';
	document.frm.submit();
 
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
tituCol[4] = "Real";
tituCol[5] = "U.C.";
tituCol[6] = "Costo";
tituCol[7] = "PP";
tituCol[8] = "Ult. Comp.";
tituCol[9] = "Cta. Comp.";
tituCol[10] = "Es Serializable";
tituCol[11] = "Numero Serie";
java.util.List ProveedoArticulos = new java.util.ArrayList();
ProveedoArticulos= BPAA.getProveedoArticulosList();
iterProveedoArticulos = ProveedoArticulos.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="lov_articulos_stock_in_out.jsp" method="POST" name="frm">

<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr class="textGlobales">
                   <td ><%=titulo%></td>
                </tr>
                <tr>
                   <td width="89%" height="38">
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
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BPAA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=10; i<= 100 ; i+=10){%>
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
                  <td class="text-globales"><div align="center">
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
     <td width="3%" rowspan="2" >&nbsp;</td>
     <td width="5%" rowspan="2" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="5%" rowspan="2" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="65%" rowspan="2" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td colspan="3" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center">Precio</div>
      <div align="right"></div>       <div align="right"></div></td>
     <td width="6%" rowspan="2" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[6]%></div></td>
	 </tr>
  <tr class="fila-encabezado">
    <td width="6%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[4]%></div></td>
    <td width="6%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[5]%></div></td>
    <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[7]%></div></td>
  </tr>
   <%int r = 0;
   while(iterProveedoArticulos.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterProveedoArticulos.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" >
			  <div align="center">
				<%if(!str.esNulo(sCampos[9]).equals("")){%>
				<img src="../imagenes/default/nuevo.gif" width="16" height="16" title="Agregar al pedido ...." onClick="agregar('<%= sCampos[0] %>')">					    
        <%} else{%>		
				<img src="../imagenes/default/gnome_tango/status/stock_dialog-warning.png" width="18" height="18" title="Articulo no posee asociada cuenta de compras .....">		  
        <%} %>				  				
		</div>	  </td>
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[2]%>&nbsp;</td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[4]%></div></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[5]%></div></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[7]%>
        </div>
        <div align="right"></div></td>			
      <td class="fila-det-border" ><div align="right"><%=sCampos[6]%></div></td>	
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
				<td colspan="10"> </td>
			</tr>		
			<tr  class="fila-det-bold">
			 <td width="3%">&nbsp;Sel.</td>
				<td width="6%">&nbsp; Cód.</td>
				<td width="7%">&nbsp; Alias</td>
				<td width="49%">&nbsp; Descripción</td>
			 <td width="28%">D&eacute;posito</td>				
				<td width="7%">&nbsp; Cantidad </td>
			</tr>
			<tr  class="text-globales" height="3">
				<td colspan="10"> </td>
			</tr>		
		<%
	   			Enumeration en = htArticulosInOut.keys();
					while (en.hasMoreElements()) {
						String key = (String) en.nextElement();
						String [] articulos = (String []) htArticulosInOut.get(key);
						/*Hashtable htDepositos = BPAA.getHtDepositos();
						Enumeration enumDepositos = htDepositos.keys();*/

						  %>
		<tr class="fila-det">
			<td class="fila-det-border"><input name="delKey" type="checkbox" id="delKey" value="<%= key %>" class="campo"></td>
			<td class="fila-det-border">&nbsp;<%= articulos[0] %><input  name="keyHashDatosArticulo" type="hidden" value="<%= key %>"></td>
			<td class="fila-det-border">&nbsp;<%= articulos[1] %>  </td>
			<td class="fila-det-border">&nbsp;<%= articulos[2] %></td>
		 <td class="fila-det-border"><select name="deposito" id="deposito" class="campo">
																																<option value="-1">Seleccionar</option>
																																<% 
																											            Iterator iter = BPAA.getListDepositos().iterator();					
																																	while(iter.hasNext()){
																																			String keyDep[] = (String[]) iter.next();
																																			String sel = "";

																																			if(articulos[9].equals(keyDep[0])) sel = "selected";
																																			else sel = "";
																																			
																																			  %>
																													        <option value="<%= keyDep[0] %>" <%= sel %>><%= keyDep[1] %></option>
																																					<%
																																	}
																																																															
																																 %>
																														 </select>		 </td>
			
			<td class="fila-det-border">&nbsp;<input type="text" class="campo" name="cantidad" value="<%= articulos[10] %>" size="10"></td>
		</tr>							
		<%
					}
	  %>
			<tr  class="text-globales" height="3">
				<td colspan="10"> </td>
			</tr>					
			<tr  class="fila-det-bold">
			 <td height="40" colspan="8">
			   <input name="eliminar" type="button" class="boton" id="eliminar" value="Eliminar" onClick="eliminarRegistro();"> 			 <input name="confirmar" type="button" class="boton" id="confirmar" value="Confirma" onClick="confirmarDetalle();"></td>
		  </tr>			
	</table>
<% } %>
   <input name="accion" value="" type="hidden">
	 <input type="hidden" name="codigo_st" value="">
			
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

