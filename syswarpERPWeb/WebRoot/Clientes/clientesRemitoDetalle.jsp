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
Iterator iterDetalleRemito   = null;
int totCol = 7; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BPDA"  class="ar.com.syswarp.web.ejb.BeanClientesRemitosDetalle" scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BPDA" property="*" />
<%
 BPDA.setResponse(response);
 BPDA.setRequest(request);
 BPDA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 String titulo = "DETALLE DEL REMITO: " + BPDA.getSucursal() +  "-" + BPDA.getRemitocliente() + " / SOCIO: "  + BPDA.getIdcliente() + " - " + BPDA.getCliente();
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

tituCol[0] = "Pedido";
tituCol[1] = "C.Art.";
tituCol[2] = "Articulo";
tituCol[3] = "Precio";
tituCol[4] = "Cantidad";
tituCol[5] = "idmedida";
tituCol[6] = "idempresa";
java.util.List DetalleRemito = new java.util.ArrayList();
DetalleRemito= BPDA.getListDetalleRemito();
iterDetalleRemito = DetalleRemito.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="clientesRemitoDetalle.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td width="86%"  class="text-globales"><%=titulo%></td>
                   <td width="14%"  class="text-globales"><div align="right"></div></td>
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
             <td colspan="4" >DATOS REMITO </td>
           </tr>
           <tr  >
             <td  class="fila-det-bold">Calle:</td>
             <td class="fila-det">&nbsp;<%=BPDA.getCalle()%></td>
             <td class="fila-det-bold"> C.Postal:</td>
             <td class="fila-det">&nbsp;<%=BPDA.getPostal()%></td>
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
           
           <tr  >
             <td width="9%"  class="fila-det-bold">Bultos:</td>
             <td width="41%" class="fila-det"><%=BPDA.getBultos()%></td>
             <td width="17%" class="fila-det-bold">&nbsp;</td>
             <td width="33%" class="fila-det">&nbsp;</td>
           </tr>
           <tr class="text-dos-bold">
             <td colspan="4" >DETALLE DE ARTICULOS</td>
           </tr>
         </table></td>
       </tr>
       <tr>
         <td><table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
           <tr class="fila-encabezado">
             <td width="7%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
             <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
             <td width="61%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
             <td width="12%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[3]%>&nbsp;</div></td>
             <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[4]%>&nbsp;</div></td>
           </tr>
           <%int r = 0;
   String target = "pedidosHistoricoClienteDetalle.jsp";//;
   if(BPDA.getTipopedido().equalsIgnoreCase("R")) target =  "pedidosRegalosDetalleEntrega.jsp" ;
   while(iterDetalleRemito.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterDetalleRemito.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
           <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
             <td class="fila-det-border" ><div align="center" onClick="abrirVentana('<%= target %>?tipopedido=<%=BPDA.getTipopedido()%>&idpedido_cabe=<%=sCampos[0]%>&idcliente=<%=BPDA.getIdcliente()%>&cliente=<%=BPDA.getCliente()%>','detalle_pedido', 700, 400)" style="cursor:pointer" ><a href="#"><%=sCampos[0]%></a> </div></td>
             <td class="fila-det-border" ><%=sCampos[1]%></td>
             <td class="fila-det-border" ><%=sCampos[2]%>&nbsp;</td>
             <td class="fila-det-border" ><div align="right"><%=Common.getNumeroFormateado(Double.parseDouble(sCampos[3]), 10, 2)%>&nbsp;</div></td>
             <td class="fila-det-border" ><div align="right"><%=Common.getNumeroFormateado(Double.parseDouble(sCampos[4]), 10, 2)%>&nbsp;</div></td>
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

