<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: pedidosestados
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Mar 27 11:09:46 GMT-03:00 2007 
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
String titulo = "Consulta de Pedidos - Stock Disponible Inexistente";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterPedidos   = null;
int totCol = 10; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BPA"  class="ar.com.syswarp.web.ejb.BeanConsultaPedidosStockNegativo"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BPA" property="*" />
<%
 BPA.setResponse(response);
 BPA.setRequest(request);
 BPA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BPA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Pedido";
tituCol[1] = "idestedo";
tituCol[2] = "Estado";
tituCol[3] = "idcliente";
tituCol[4] = "Cliente";
tituCol[5] = "Artículo";
tituCol[6] = "Disponible";
tituCol[7] = "Usuario";
tituCol[8] = "Fecha";
tituCol[9] = "Codigo";

java.util.List pedidosList = new java.util.ArrayList();
pedidosList= BPA.getPedidosList();
iterPedidos = pedidosList.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="consultaPedidosStockNegativo.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr class="text-globales">
                   <td class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="89%" height="38">
                     <table width="100%" border="0">
                        <tr>
                          <td width="6%" height="26" class="text-globales">Buscar</td>
                          <td width="22%">
                             <input name="ocurrencia" type="text" value="<%=BPA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                           </td>
                          <td width="72%">
                            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td>
                                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                       <tr class="text-globales">
                                         <td width="1%" height="19">&nbsp; </td>
                                         <td width="23%">&nbsp;Total de registros:&nbsp;<%=BPA.getTotalRegistros()%></td>
                                         <td width="11%" >Visualizar:</td>
                                         <td width="11%">
                                            <select name="limit" >
                                               <%for(i=15; i<= 150 ; i+=15){%>
                                                   <%if(i==BPA.getLimit()){%>
                                                       <option value="<%=i%>" selected><%=i%></option>
                                                   <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                   <%}
                                                      if( i >= BPA.getTotalRegistros() ) break;
                                                    %>
                                               <%}%>
                                               <option value="<%= BPA.getTotalRegistros()%>">Todos</option>
                                            </select>                                          </td>
                                         <td width="7%">&nbsp;P&aacute;gina:</td>
                                         <td width="12%">
                                            <select name="paginaSeleccion" >
                                               <%for(i=1; i<= BPA.getTotalPaginas(); i++){%>
                                                   <%if ( i==BPA.getPaginaSeleccion() ){%>
                                                      <option value="<%=i%>" selected><%=i%></option> 
                                                   <%}else{%>
                                                      <option value="<%=i%>"><%=i%></option>
                                                   <%}%>
                                               <%}%>
                                            </select>                                          </td>
                                         <td width="10%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                                      </tr>
                                   </table>                                 </td>
                              </tr>
                          </table>                        </td>
                     </tr>
                   </table>                </td>
            </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BPA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="2%" ><%=tituCol[0]%></td>
     <td width="10%" ><%=tituCol[2]%></td>
     <td width="45%" ><%=tituCol[4]%></td>
     <td width="12%" ><%=tituCol[5]%></td>
     <td width="11%" ><div align="right"><%=tituCol[6]%></div></td>
     <td width="9%" ><%=tituCol[7]%></td>
     <td width="7%" ><%=tituCol[8]%></td>
     <td width="4%" >&nbsp;</td>
  </tr>
   <%int r = 0;
   while(iterPedidos.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterPedidos.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[2]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[3]%>-<%=sCampos[4]%></td>
      <td class="fila-det-border" ><%=sCampos[5]%></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[6]%></div></td>
      <td class="fila-det-border" ><%=sCampos[7]%></td>
      <td class="fila-det-border" ><%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[8]), "JSTsToStr")%></td>
      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" title="Reimprimir " width="18" height="18" onClick="abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=notas_pedido&idpedido_cabe=<%=sCampos[0]%>','pedidoorden',750, 400)"></div></td>
   </tr>
<%
   }%>
   </table>
   <input name="accion" value="" type="hidden">
   <input name="codigo_st" value="<%= BPA.getCodigo_st() %>" type="hidden">
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

