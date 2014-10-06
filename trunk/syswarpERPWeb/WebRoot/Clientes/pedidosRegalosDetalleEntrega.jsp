<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: pedidos_Deta
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Sep 04 16:29:20 GMT-03:00 2008 
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

// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterPedidos_Deta   = null;
int totCol = 20; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BPDA"  class="ar.com.syswarp.web.ejb.BeanPedidosRegalosDetalleEntrega" scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BPDA" property="*" />
<%
 BPDA.setResponse(response);
 BPDA.setRequest(request);
 BPDA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 String titulo = "DETALLE DE ENTREGA: " + BPDA.getIdpedido_cabe() + " / SOCIO: "  + BPDA.getIdcliente() + " - " + BPDA.getCliente();
 BPDA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
  <link href="../imagenes/default/erp-style.css" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="<%=pathskin%>/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Cód.Deta.";
tituCol[1] = "Pedido";
tituCol[2] = "C.Art.";
tituCol[3] = "Articulo";
tituCol[4] = "Fecha";
tituCol[5] = "Renglon";
tituCol[6] = "Precio";
tituCol[7] = "Saldo";
tituCol[8] = "Cant";
tituCol[9] = "Bonif.";
tituCol[10] = "U.Med.";
tituCol[11] = "cantuni";
tituCol[12] = "Depósito";
tituCol[13] = "Entrega";
tituCol[14] = "Cód. % Sug.";
tituCol[15] = "Cód. % Apl.";
tituCol[16] = "%Sg";
tituCol[17] = "%Ap";
tituCol[18] = "Mot. Desc.";
tituCol[19] = "idempresa";
java.util.List Pedidos_Deta = new java.util.ArrayList();
Pedidos_Deta= BPDA.getPedidos_DetaList();
iterPedidos_Deta = Pedidos_Deta.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="pedidosRegalosDetalleEntrega.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td width="86%"  class="text-globales"><%=titulo%></td>
                   <td width="14%"  class="text-globales"><div align="right">
                    <%--  <input name="volver" type="submit" class="boton" id="volver" value="Volver"> --%>
                   </div></td>
                </tr>
          </table> 
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BPDA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#000000" >
       <tr>
         <td>
				 <table width="100%" border="1" cellpadding="0" cellspacing="0" class="fila-det">
           <tr class="text-dos-bold">
             <td colspan="4" >DATOS DOMICILIO</td>
           </tr>
           <tr  >
             <td width="9%"  class="fila-det-bold">Calle:</td>
             <td width="41%" class="fila-det">&nbsp;<%=BPDA.getCalle()%></td>
             <td width="17%" class="fila-det-bold">CPA / C.Postal:</td>
             <td width="33%" class="fila-det">&nbsp;<%= BPDA.getCpa() +  " / " + BPDA.getPostal()%></td>
           </tr>
           <tr >
             <td class="fila-det-bold"> Nro:</td>
             <td>&nbsp;<%=BPDA.getNro()%></td>
             <td class="fila-det-bold">Contacto:</td>
             <td>&nbsp;<%=BPDA.getContacto()%></td>
           </tr>
           <tr>
             <td class="fila-det-bold">Piso:</td>
             <td>&nbsp;<%=BPDA.getPiso()%></td>
             <td class="fila-det-bold">Localidad  : </td>
             <td>&nbsp;<%=BPDA.getLocalidad()%></td>
           </tr>
           <tr>
             <td class="fila-det-bold">Depto:</td>
             <td>&nbsp;<%=BPDA.getDepto()%></td>
             <td class="fila-det-bold"> Provincia : </td>
             <td>&nbsp;<%=BPDA.getProvincia()%></td>
           </tr>
           <tr>
             <td class="fila-det-bold">Distribuidor:</td>
             <td>&nbsp;<%=BPDA.getZona()%></td>
             <td class="fila-det-bold">Zona</td>
             <td>&nbsp;<%=BPDA.getExpreso()%></td>
           </tr>
           <tr class="text-dos-bold">
             <td colspan="4" >DATOS ENTREGA </td>
           </tr>
           <tr>
             <td class="fila-det-bold">Pedido:</td>
             <td><div align="left" onClick="abrirVentana('pedidosHistoricoClienteDetalle.jsp?tipopedido=R&idpedido_cabe=<%=BPDA.getIdpedido_regalos_cabe()%>&idcliente=<%=BPDA.getIdcliente()%>&cliente=<%=BPDA.getCliente()%>','regalos', 700, 400)" style="cursor:pointer" ><a href="#"><img src="../imagenes/default/gnome_tango/actions/go-last.png" width="15" height="15" border="0">  <%=BPDA.getIdpedido_regalos_cabe()%></a> </div></td>
             <td class="fila-det-bold">&nbsp;</td>
             <td>&nbsp;</td>
           </tr>
           <tr>
             <td class="fila-det-bold">Estado:</td>
             <td>&nbsp;<%=BPDA.getEstado()%></td>
             <td class="fila-det-bold">Prioridad:</td>
             <td>&nbsp;<%=BPDA.getPrioridad()%></td>
           </tr>
           <tr>
             <td class="fila-det-bold">Fecha:</td>
             <td>&nbsp;<%= BPDA.getFechapedido() %><%//=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(BPDA.getFechapedido()), "JSTsToStr")%></td>
             <td class="fila-det-bold">&nbsp;</td>
             <td>&nbsp;</td>
           </tr>
           <tr class="text-dos-bold">
             <td colspan="4" >DETALLE DE ARTICULOS - Total de Registros <%=BPDA.getTotalRegistros()%> </td>
           </tr>
         </table></td>
       </tr>
       <tr>
         <td><table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
           <tr class="fila-encabezado">
             <td width="33%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
             <td width="12%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[10]%></td>
             <td width="15%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[12]%></td>
             <td width="7%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[6]%>&nbsp;</div></td>
             <td width="6%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[8]%>&nbsp;</div></td>
             <td width="6%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[9]%>&nbsp;</div></td>
             <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[16]%>&nbsp;</div></td>
             <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[17]%>&nbsp;</div></td>
             <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[18]%>&nbsp;</div></td>
           </tr>
           <%int r = 0;
   while(iterPedidos_Deta.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterPedidos_Deta.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
           <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
             <td class="fila-det-border" ><%=sCampos[2]%>-<%=sCampos[3].length()>30 ? sCampos[3].substring(0,30) + "...": sCampos[3]%></td>
             <td class="fila-det-border" ><%=sCampos[10].length()>15 ? sCampos[10].substring(0,15) + "...": sCampos[10]%>&nbsp;</td>
             <td class="fila-det-border" ><%=sCampos[12].length()>20 ? sCampos[12].substring(0,20) + "...": sCampos[12]%>&nbsp;</td>
             <td class="fila-det-border" ><div align="right"><%=Common.getNumeroFormateado(Float.parseFloat(sCampos[6]),10,2)%>&nbsp;</div></td>
             <td class="fila-det-border" ><div align="right"><%=Common.getNumeroFormateado(Float.parseFloat(sCampos[8]),10,2)%>&nbsp;</div></td>
             <td class="fila-det-border" ><div align="right"><%=Common.getNumeroFormateado(Float.parseFloat(sCampos[9]),10,2)%>&nbsp;</div></td>
             <td class="fila-det-border" ><div align="right"><%=Common.getNumeroFormateado(Float.parseFloat(sCampos[16]),10,2)%>&nbsp;</div></td>
             <td class="fila-det-border" ><div align="right"><%=Common.getNumeroFormateado(Float.parseFloat(sCampos[17]),10,2)%>&nbsp;</div></td>
             <td class="fila-det-border" ><div align="center"><%=sCampos[18].length()>10 ? sCampos[18].substring(0,10) + "...": sCampos[18]%>&nbsp;</div></td>
           </tr>
           <% 
   }%>
         </table></td>
       </tr>
     </table>
     <input name="accion" value="" type="hidden">
      <input name="idcliente" type="hidden" id="idcliente" value="<%= BPDA.getIdcliente() %>">
      <input name="cliente" type="hidden" id="cliente" value="<%= BPDA.getCliente() %>">
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

