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
<%@ page import="java.math.*" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "EGRESO DE SERIES Y/O DESPACHOS PARA ARTICULO: ";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
String stockMostrarPrecios = session.getAttribute("stockMostrarPrecios").toString();

// variables de paginacion
int i = 0;
Iterator iterProveedoArticulos   = null;
int totCol = 10; // cantidad de columnas
String[] tituCol = new String[totCol];

tituCol[0] = "Cód.";
tituCol[1] = "Mov.Stock";
tituCol[2] = "codigo_St";
tituCol[3] = "codigo_dt";
tituCol[4] = "Serie";
tituCol[5] = "Despacho";
tituCol[6] = "Existencia";
tituCol[7] = "Reserva";
tituCol[8] = "F.Ingreso";
tituCol[9] = "F.Salida";


String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
String jsEvaluar  = "if(true);\n";
%>
<html>
<jsp:useBean id="BSSD"  class="ar.com.syswarp.web.ejb.BeanStockSerieDespachoSalida"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BSSD" property="*" />
<%
 titulo +=  BSSD.getCodigo_st() + " - " + BSSD.getDescrip_st();
 BSSD.setResponse(response);
 BSSD.setRequest(request);
 BSSD.setSession(session);
 BSSD.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BSSD.ejecutarValidacion();
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

function agregar(idseriedespacho, nroserie, nrodespacho){

  var validaCantOut =   <%= ( BSSD.getDespa_st().equalsIgnoreCase("S") && BSSD.getId_indi_st().equalsIgnoreCase("N") ) %>;

  if(validaCantOut)
    document.frm.cantdespacho.value = document.getElementById('cantout' + idseriedespacho).value;

  document.frm.idseriedespacho.value = idseriedespacho;
  document.frm.nrodespacho.value = nrodespacho;
  document.frm.nroserie.value = nroserie;
	document.frm.accion.value = 'agregar';
	document.frm.submit();

  return true; 

}

window.onload = function (){
  var accion = '<%=BSSD.getAccion().toLowerCase()%>';
  var mensaje = '<%= BSSD.getMensaje().toLowerCase() %>';
 
  if((accion == 'confirmar' && mensaje == '') || accion == 'eliminar'){
    opener.document.getElementById('<%=BSSD.getIdcantidad()%>').value = <%=BSSD.getCantidadIngresada()%>;
    if(accion == 'confirmar'){
      opener.document.frm.submit();
      this.close();
    }
  }
 
  eval(document.frm.evaluar.value);

}

//-->
</script>
</head>
<%

 
// titulos para las columnas


java.util.List ProveedoArticulos = new java.util.ArrayList();
iterProveedoArticulos = ProveedoArticulos.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="lov_stockSerieDespachoSalida.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr class="text-globales">
                   <td height="34" ><%=titulo%></td>
                </tr>
                <tr>
                   <td width="89%" height="38"> 
                      <table width="100%" border="0">
                         <tr>
                           <td width="72%" height="26">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td><table width="100%" border="0">
                                     <tr>
                                       <td width="6%" height="26" class="text-globales">Buscar</td>
                                       <td width="22%"><input name="ocurrencia" type="text" value="<%=BSSD.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">
                                       </td>
                                       <td width="72%"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                           <tr>
                                             <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                                                 <tr class="text-globales">
                                                   <td width="1%" height="19">&nbsp;</td>
                                                   <td width="23%">&nbsp;Total de registros:&nbsp;<%=BSSD.getTotalRegistros()%></td>
                                                   <td width="11%" >Visualizar:</td>
                                                   <td width="11%"><select name="limit" >
                                                       <%for(i=10; i<= 100 ; i+=10){%>
                                                       <%if(i==BSSD.getLimit()){%>
                                                       <option value="<%=i%>" selected><%=i%></option>
                                                       <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                       <%}
                                                      if( i >= BSSD.getTotalRegistros() ) break;
                                                    %>
                                                       <%}%>
                                                       <option value="<%= BSSD.getTotalRegistros()%>">Todos</option>
                                                     </select>
                                                   </td>
                                                   <td width="7%">&nbsp;P&aacute;gina:</td>
                                                   <td width="12%">
                                                      <select name="paginaSeleccion" >
                                                       <%for(i=1; i<= BSSD.getTotalPaginas(); i++){%>
                                                       <%if ( i==BSSD.getPaginaSeleccion() ){%>
                                                       <option value="<%=i%>" selected><%=i%></option>
                                                       <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                       <%}%>
                                                       <%}%>
                                                     </select>
                                                   </td>
                                                   <td width="10%" class="text-globales"><input name="ir2" type="submit" class="boton" id="ir2" value="  >>  "></td>
                                                 </tr>
                                             </table></td>
                                           </tr>
                                       </table></td>
                                     </tr>
                                   </table></td>
                              </tr>
                           </table>                        </td>
                     </tr>
                   </table>
                </td>
            </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo">     <jsp:getProperty name="BSSD" property="mensaje"/>     </td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >

   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td width="3%" class="fila-det-border" ><table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
        <tr class="fila-det-bold-rojo">
          <td width="24%" height="33" class="fila-det-border"><table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
            <tr class="fila-encabezado">
              <td width="2%" >&nbsp;</td>
              <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[0]%></div></td>
              <td width="23%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
              <td width="19%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
              <td width="22%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[8]%></td>
              <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[6]%></div></td>
              <td width="8%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[7]%></div></td>
              <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center">Cant.Desp.</div></td>
              </tr>
            <%int s = 0;
	 java.util.List StockSerieDespacho = new java.util.ArrayList();
	 StockSerieDespacho= BSSD.getStockSerieDespachoList();
	 Iterator iterStockSerieDespacho = StockSerieDespacho.iterator();
   while(iterStockSerieDespacho.hasNext()){
      ++s;
      String[] sCampos = (String[]) iterStockSerieDespacho.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
            <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
              <td class="fila-det-border" >
                <%if((Long.parseLong(sCampos[6]) - Long.parseLong(sCampos[7])) > 0){%>
                  <img src="../imagenes/default/nuevo.gif" width="16" height="16" title="Agregar...." onClick="agregar('<%= sCampos[0] %>', '<%=Common.setNotNull(sCampos[4])%>', '<%=Common.setNotNull(sCampos[5])%>')" style="cursor:pointer">
                <%} else{%>
                  <img src="../imagenes/default/gnome_tango/emblems/emblem-important.png" width="16" height="16" title="Cantidad disponible insuficiente.">
                <%} %>
              </td>
              <td class="fila-det-border" ><div align="center"><%=sCampos[0]%></div></td>
              <td class="fila-det-border" ><%=Common.setNotNull(sCampos[4])%>&nbsp;</td>
              <td class="fila-det-border" ><%=Common.setNotNull(sCampos[5])%>&nbsp;</td>
              <td class="fila-det-border" ><%=Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[8]), "JSDateToStr" )%></td>
              <td class="fila-det-border" ><div align="right"><%=sCampos[6]%>&nbsp;</div></td>
              <td class="fila-det-border" ><div align="right"><%=sCampos[7]%>&nbsp;</div></td>
              <td class="fila-det-border" ><div align="center">
                <input name="cantout" type="text" class="campo" id="cantout<%=sCampos[0]%>" value="0" size="6" maxlength="10" style="text-align:right" <%= BSSD.getDespa_st().equalsIgnoreCase("S") && BSSD.getId_indi_st().equalsIgnoreCase("N") ? "" : "disabled" %>>
              </div></td>
              </tr>
            <%
   }%>
          </table></td>
          </tr>

      </table></td>
    </tr>
  </table>
						<%--
						if(BSSD.getDespa_st().equalsIgnoreCase("S")){ %>
						< % 
							if(BSSD.getId_indi_st().equalsIgnoreCase("N")){ %>
						< %
							} 
						}--%>
            <input name="nrodespacho" type="hidden" class="campo" id="nrodespacho" value="<%=BSSD.getNrodespacho()%>" size="30" maxlength="50">
            <input name="nroserie" type="hidden" class="campo" id="nroserie" value="<%=BSSD.getNroserie()%>" size="30" maxlength="50">
            <input name="cantdespacho" type="hidden" class="campo" id="cantdespacho" value="0" size="30" maxlength="10">
            </td>
	 <% 
   Hashtable htArticulosSerieDespacho = (Hashtable) session.getAttribute("htArticulosSerieDespacho");
	 if(htArticulosSerieDespacho != null && 
      !htArticulosSerieDespacho.isEmpty() &&  
      htArticulosSerieDespacho.containsKey( BSSD.getRelacionHash() )){
	   %> 	 
		 &nbsp; 
		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr  class="text-globales" height="3">
				<td colspan="4"><table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr class="text-globales">
            <td width="43%">&nbsp;Total de registros seleccionados:&nbsp;<%=BSSD.getTotalRegistrosMatriz()%></td>
            <td width="8%" >Visualizar:</td>
            <td width="16%"><select name="limitMatriz" >
                <%for(i=10; i<= 100 ; i+=10){%>
                <%if(i==BSSD.getLimitMatriz()){%>
                <option value="<%=i%>" selected><%=i%></option>
                <%}else{%>
                <option value="<%=i%>"><%=i%></option>
                <%}
                                                      if( i >= BSSD.getTotalRegistrosMatriz() ) break;
                                                    %>
                <%}%>
                <option value="<%= BSSD.getTotalRegistrosMatriz()%>">Todos</option>
            </select>            </td>
            <td width="13%">&nbsp;P&aacute;gina:</td>
            <td width="6%"><select name="paginaSeleccionMatriz" >
                <%for(i=1; i<= BSSD.getTotalPaginasMatriz(); i++){%>
                <%if ( i==BSSD.getPaginaSeleccionMatriz() ){%>
                <option value="<%=i%>" selected><%=i%></option>
                <%}else{%>
                <option value="<%=i%>"><%=i%></option>
                <%}%>
                <%}%>
            </select>            </td>
            <td width="14%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
          </tr>
                </table></td>
		  </tr>		
			<tr  class="fila-det-bold">
			 <td width="5%">&nbsp;Sel.</td>
				<td width="15%">&nbsp; Serie </td>
				<td width="39%">&nbsp; Despacho </td>
		    <td width="41%">Cantidad</td>
			</tr>
			<tr  class="text-globales" height="3">
				<td>&nbsp;</td>
			  <td>&nbsp;</td>
			  <td>&nbsp;</td>
		    <td>&nbsp;</td>
			</tr>		
		<%
					String [][] serieDespacho = (String [][]) htArticulosSerieDespacho.get(BSSD.getRelacionHash());
					for (int m=BSSD.getOffsetMatriz(), r=0;serieDespacho!=null&&m<serieDespacho.length&&r<BSSD.getLimitMatriz();m++,r++) {
            jsEvaluar+= "if(document.frm.cantout"+serieDespacho[m][3]+") document.frm.cantout"+serieDespacho[m][3]+".value =  "+serieDespacho[m][2] + ";\n";
            //jsEvaluar+= "alert("+serieDespacho[m][0]+");\n";
						  %>
		<tr class="fila-det">
			<td class="fila-det-border"><input name="delKey" type="checkbox" id="delKey" value="<%= m %>" class="campo">
		  <input  name="keyHashDatosArticulo" type="hidden" value="<%= m %>"></td>
			<td class="fila-det-border">&nbsp;<%= serieDespacho[m][0] %>  </td>
			<td class="fila-det-border">&nbsp;<%= serieDespacho[m][1] %></td>
		  <td class="fila-det-border"><%= serieDespacho[m][2] %></td>
		</tr>							
		<%
					}
	  %>
			<tr  class="text-globales" height="3"> 
				<td>&nbsp;</td>
			  <td>&nbsp;</td>
			  <td>&nbsp;</td>
		    <td>&nbsp;</td>
			</tr>			
		
			<tr  class="fila-det-bold">
			 <td height="40"><input name="eliminar" type="button" class="boton" id="eliminar" value="Eliminar" onClick="eliminarRegistro();"></td>
		   <td height="40">&nbsp;</td>
		   <td height="40"><input name="confirmar" type="button" class="boton" id="confirmar" value="Confirma" onClick="confirmarDetalle();"></td> 
	     <td>&nbsp;</td>
			</tr>			
	</table>
<% } %>
 		  <input type="hidden" name="accion" value="" >
      <input name="codigo_st" type="hidden" id="codigo_st" value="<%=BSSD.getCodigo_st() %>" >
      <input name="descrip_st" type="hidden" id="descrip_st" value="<%=BSSD.getDescrip_st()%>" > 
       <input name="codigo_dt" type="hidden" id="codigo_dt" value="<%=BSSD.getCodigo_dt()%>"  >  
 		  <input type="hidden" name="relacionHash" id="relacionHash"  value="<%=BSSD.getRelacionHash()%>">
 		  <input name="id_indi_st" type="hidden" id="id_indi_st" value="<%=BSSD.getId_indi_st()%>" > 
			<input name="despa_st" type="hidden" id="despa_st" value="<%=BSSD.getDespa_st()%>" >
			<input name="idcantidad" type="hidden" id="idcantidad" value="<%=BSSD.getIdcantidad()%>" >  
      <input name="idseriedespacho" type="hidden" value="<%=BSSD.getIdseriedespacho()%>">
      <input name="evaluar" type="hidden" value="<%= jsEvaluar %>">
<%--  
			if(BSSD.getAccion().equalsIgnoreCase("confirmar")  
			   && BSSD.getMensaje().equals("") ){%>
			  <script>
					      //opener.document.frm.submit(); 
                opener.document.getElementById('< %=BSSD.getIdcantidad()%>').value = < %=BSSD.getCantidadIngresada()%>;
								this.close();
					</script>			
			< %  
			}--%>
			
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