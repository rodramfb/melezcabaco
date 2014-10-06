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
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "Articulos";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterStockstock   = null;
int totCol = 41; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BSA"  class="ar.com.syswarp.web.ejb.BeanStockstockAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BSA" property="*" />
<%
 BSA.setResponse(response);
 BSA.setRequest(request);
 BSA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
  <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>
 <script >

function bajarDatos(codigo_st, d_codigo_st){
	for(var i = 0; i<10;i++)
		if(opener.document.forms[i].codigo_st)
			break;
	opener.document.forms[i].codigo_st.value = codigo_st;
	opener.document.forms[i].d_codigo_st.value = d_codigo_st; 
	window.close();
}
</script>

 
</head>
<%
// titulos para las columnas
tituCol[0] = "Codigo";
tituCol[1] = "Alias";
tituCol[2] = "Descripcion";
tituCol[3] = "Descripcion Corta";
tituCol[4] = "Costos P.P.";
tituCol[5] = "Precio P.P.";
tituCol[6] = "Costo Ultima Compra";
tituCol[7] = "Precio Ultima Compra";
tituCol[8] = "Costo Rep.";
tituCol[9] = "Precio Rep.";
tituCol[10] = "Moneda de compras";
tituCol[11] = "Grupo";
tituCol[12] = "Cantidad Minima";
tituCol[13] = "Unidad Medida";
tituCol[14] = "Bonif.";
tituCol[15] = "Imp. Intern.";
tituCol[16] = "Imp. Cantid.";
tituCol[17] = "C.Cont. Compras";
tituCol[18] = "C.Cont. Ventas";
tituCol[19] = "C.Cont. Ventas2";
tituCol[20] = "C.Cont. Costos";
tituCol[21] = "C.Cont. Ajuste";
tituCol[22] = "Inventario";
tituCol[23] = "Cod.Proveedor Hab";
tituCol[24] = "Cod.Art. Proveedor";
tituCol[25] = "Indice";
tituCol[26] = "Despacho";
tituCol[27] = "Marca";
tituCol[28] = "cafecga_st";
tituCol[29] = "U.Alternativa 1";
tituCol[30] = "U.Alternativa 2";
tituCol[31] = "U.Alternativa 3";
tituCol[32] = "U.Alternativa 4";
tituCol[33] = "Factor 1";
tituCol[34] = "Factor 2";
tituCol[35] = "Factor 3";
tituCol[36] = "Factor 4";
tituCol[37] = "Tipo I.V.A.";
tituCol[38] = "Prod. Venta?";
tituCol[39] = "Prod. Compra?";
tituCol[40] = "Esquema Produccion";
java.util.List Stockstock = new java.util.ArrayList();
Stockstock= BSA.getStockstockList();
iterStockstock = Stockstock.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="lov_stock.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td  class="text-globales"><%=titulo%></td>
                   <td>&nbsp;</td>
                </tr>
                <tr>
                   <td width="11%" height="38">
                   </td>
                   <td width="89%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BSA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">
                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BSA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BSA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BSA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BSA.getTotalRegistros()%>">Todos</option>
                                             </select>
                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BSA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BSA.getPaginaSeleccion() ){%>
                                                       <option value="<%=i%>" selected><%=i%></option> 
                                                    <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                    <%}%>
                                                <%}%>
                                             </select> 
                                          </td>
                                          <td width="10%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                                       </tr>
                                    </table>
                                 </td>
                              </tr>
                           </table>
                        </td>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BSA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="10%" >&nbsp;</td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[6]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[7]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[8]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[9]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[10]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[11]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[12]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[13]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[14]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[15]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[16]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[17]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[18]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[19]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[20]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[21]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[22]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[23]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[24]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[25]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[26]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[27]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[28]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[29]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[30]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[31]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[32]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[33]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[34]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[35]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[36]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[37]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[38]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[39]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[40]%></td>
   </tr>
   <%int r = 0;
   while(iterStockstock.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterStockstock.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
     <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >       
      <td class="fila-det-border" ><img src="../imagenes/default/audit.gif" width="21" height="17" onClick="bajarDatos('<%=sCampos[0]%>', '<%=sCampos[0]%>' )"></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[0]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[1]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[2]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[3]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[4]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[5]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[6]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[7]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[8]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[9]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[10]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[11]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[12]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[13]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[14]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[15]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[16]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[17]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[18]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[19]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[20]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[21]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[22]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[23]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[24]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[25]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[26]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[27]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[28]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[29]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[30]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[31]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[32]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[33]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[34]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[35]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[36]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[37]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[38]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[39]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[40]%></td>
   </tr>
<%
   }%>
   </table>
   <input name="accion" value="" type="hidden">
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

