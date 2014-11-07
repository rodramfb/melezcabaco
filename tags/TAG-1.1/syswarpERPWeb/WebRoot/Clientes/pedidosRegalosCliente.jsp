<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: pedidos_Cabe
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Sep 04 11:02:26 GMT-03:00 2008 
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
Iterator iterPedidos_Cabe   = null;
int totCol = 32; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BPCA"  class="ar.com.syswarp.web.ejb.BeanPedidosRegalosCliente"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BPCA" property="*" />
<%
 BPCA.setResponse(response);
 BPCA.setRequest(request);
 BPCA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 BPCA.ejecutarValidacion();
 String titulo = "PEDIDOS DEL SOCIO/CLIENTE: " + BPCA.getCliente();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script>
 
 function getDetalle(idpedido_cabe, tipopedido){

	 document.frm.idpedido_cabe.value =  idpedido_cabe;
	 document.frm.tipopedido.value =  tipopedido;
	 document.frm.accion.value = 'verdetalle' ;
	 document.frm.submit(); 
 
 }

 function bajarDatos(pedidoPadre){
   if(opener.document.frm.idpedido_regalos_padre){
     opener.document.frm.idpedido_regalos_padre.value = pedidoPadre;
     this.close();
   }  
 }
 
 
 </script>
 
</head>
<%
// titulos para las columnas
tituCol[0] = "Pedido";
tituCol[1] = "idEstado";
tituCol[2] = "Estado";
tituCol[3] = "Cliente";
tituCol[4] = "idsucursal";
tituCol[5] = "Domicilio";
tituCol[6] = "Fecha";
tituCol[7] = "Condicion";
tituCol[8] = "Vendedor";
//tituCol[8] = "Expreso";
tituCol[9] = "Zona";
tituCol[10] = "comision";
tituCol[11] = "OC";
tituCol[12] = "Obs.Armado";
tituCol[13] = "Obs.Entrega";
tituCol[14] = "R1";
tituCol[15] = "R2";
tituCol[16] = "R3";
tituCol[17] = "R4";
tituCol[18] = "B1";
tituCol[19] = "B2";
tituCol[20] = "B3";
tituCol[21] = "Lista";
tituCol[22] = "Moneda";
tituCol[23] = "Cotz.";
tituCol[24] = "Tip.Iva";
tituCol[25] = "Total";
tituCol[26] = "Prioridad";
//tituCol[26] = "Zona";
tituCol[27] = "Dist.";
tituCol[28] = "Condición";
tituCol[29] = "Cuotas";
tituCol[30] = "Origen";
tituCol[31] = "Tipo";

java.util.List Pedidos_Cabe = new java.util.ArrayList();
Pedidos_Cabe= BPCA.getPedidos_CabeList();
iterPedidos_Cabe = Pedidos_Cabe.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="pedidosRegalosCliente.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td colspan="2"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="3%" height="38">&nbsp;</td>
                   <td width="97%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BPCA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BPCA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BPCA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BPCA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BPCA.getTotalRegistros()%>">Todos</option>
                                             </select>                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BPCA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BPCA.getPaginaSeleccion() ){%>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BPCA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
    <td width="2%" >&nbsp;</td>
<%--      <td width="2%" >&nbsp;</td>  --%>
     <td width="2%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="8%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="31%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[6]%>&nbsp;</div></td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[25]%>&nbsp;</div></td>
     <td width="31%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[28]%></td>
     <td width="3%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[29]%></td>
     <td width="3%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[30]%></td>
     <td width="2%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[31]%></td>

     <td width="3%" >&nbsp;</td>
     <td width="3%" >&nbsp;</td>
     <td width="2%" >&nbsp;</td>

  </tr>
   <%int r = 0;
   while(iterPedidos_Cabe.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterPedidos_Cabe.next(); 
      // estos campos hay que setearlos segun la grilla 
      String plantillaImpresionJRXML= "notas_pedido_regalos";
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";
	  //System.out.println("sCampos[1]--->: " + sCampos[1]);
			%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
      <td class="fila-det-border" >
       <% 
        if(Common.setNotNull(sCampos[1]).equals("4")){ %> 
        <img src="../imagenes/default/gnome_tango/actions/down.png" width="18" height="17" onClick="bajarDatos(<%=sCampos[0]%>);" >
        <% 
        } else out.write("&nbsp;"); %>
      </td> 
      <%--       <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/actions/finish.png" title="Detalle" width="18" height="18" onClick="getDetalle(<%=sCampos[0]%>, '<%=sCampos[30]%>');"></td> --%> 
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[2]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[5]%>&nbsp;</td>
      <td class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[6]), "JSTsToStr")%>&nbsp;</div></td>
      <td class="fila-det-border" ><div align="right"><%=Common.getNumeroFormateado(Float.parseFloat(sCampos[25]), 10, 2) %>&nbsp;</div></td>
      <td class="fila-det-border" ><%=str.esNulo(sCampos[28])%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[29]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[30]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[31]%>&nbsp;</td>
      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/actions/document-properties.png" width="18" height="18" onMouseOver="overlib(' <%=!str.esNulo(sCampos[12]).trim().equals("")  ? sCampos[12] :  " SIN OBSERVACIONES "  %>', STICKY, CAPTION, 'OBS. ARMADO: ',TIMEOUT,5000,HAUTO,VAUTO,WIDTH,350);"></div></td>
      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/actions/bookmark_add.png" width="18" height="18"  onMouseOver="overlib(' <%=!str.esNulo(sCampos[13]).trim().equals("")  ? sCampos[13] :  " SIN OBSERVACIONES "  %>', STICKY, CAPTION, 'OBS ENTREGA: ',TIMEOUT,5000,HAUTO,VAUTO,WIDTH,350);"></div></td>
      <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" title="Reimprimir " width="18" height="18" onClick="abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=<%= plantillaImpresionJRXML %>&idpedido_cabe=<%=sCampos[0]%>','pedido',750, 400)"></td>
   </tr>
<%
   }%>
   </table>
   <input name="accion" value="" type="hidden">
   <input name="idpedido_cabe" type="hidden" id="idpedido_cabe" value="">
   <input name="tipopedido" type="hidden" id="tipopedido" value="">
   <input name="idcliente" type="hidden" id="idcliente" value="<%= BPCA.getIdcliente() %>">
   <input name="cliente" type="hidden" id="cliente" value="<%= BPCA.getCliente() %>">
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

