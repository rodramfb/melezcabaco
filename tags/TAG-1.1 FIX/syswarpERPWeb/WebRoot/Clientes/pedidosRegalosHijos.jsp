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
int totCol = 41; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BPCA"  class="ar.com.syswarp.web.ejb.BeanPedidosRegalosHijos"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BPCA" property="*" />
<%
 BPCA.setResponse(response);
 BPCA.setRequest(request);
 BPCA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 BPCA.ejecutarValidacion();
 String titulo = "PEDIDOS DE REGALOS HIJOS" ;
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

 function bajarDatos(idpedido_regalos_cabe, idesquema, esquema, codigo_st, descrip_st, cantest_op, fecha_prometida){
   if(opener.document.frm.idpedido_regalos_cabe){
     opener.document.frm.idpedido_regalos_cabe.value = idpedido_regalos_cabe;
	 opener.document.frm.idesquema.value = idesquema;
	 opener.document.frm.esquema.value = esquema;
	 opener.document.frm.codigo_st.value = codigo_st;
	 opener.document.frm.descrip_st.value = descrip_st;
	 opener.document.frm.cantest_op.value = cantest_op; 
	 opener.document.frm.fecha_prometida.value = fecha_prometida; 
	 
     this.close();
   }  
 }
 
 
 </script>
 
</head>
<%


/*







*/ 

// titulos para las columnas
tituCol[0] = "Pedido";
tituCol[1] = "Padre";
tituCol[2] = "idEstado";
tituCol[3] = "Estado";
tituCol[4] = "Invalido";
tituCol[5] = "Cant.";
tituCol[6] = "idEsquema";
tituCol[7] = "Esquema";
tituCol[8] = "Obs.";
tituCol[9] = "Art.";
tituCol[10] = "Desc.Art.";
tituCol[11] = "idCliente";
tituCol[12] = "Cliente";
tituCol[13] = "idsucursal";
tituCol[14] = "Domicilio";
tituCol[15] = "Fecha";
tituCol[16] = "Condicion";
tituCol[17] = "Vendedor";
tituCol[18] = "Zona";
tituCol[19] = "comision";
tituCol[20] = "OC";
tituCol[21] = "Obs.Armado";
tituCol[22] = "Obs.Entrega";
tituCol[23] = "R1";
tituCol[24] = "R2";
tituCol[25] = "R3";
tituCol[26] = "R4";
tituCol[27] = "B1";
tituCol[28] = "B2";
tituCol[29] = "B3";
tituCol[30] = "Lista";
tituCol[31] = "Moneda";
tituCol[32] = "Cotz.";
tituCol[33] = "Tip.Iva";
tituCol[34] = "Total";
tituCol[35] = "Prioridad";
tituCol[36] = "Dist.";
tituCol[37] = "Condición";
tituCol[38] = "Cuotas";
tituCol[39] = "Origen";
tituCol[40] = "Tipo";

java.util.List Pedidos_Cabe = new java.util.ArrayList();
Pedidos_Cabe= BPCA.getPedidos_CabeList();
iterPedidos_Cabe = Pedidos_Cabe.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="pedidosRegalosHijos.jsp" method="POST" name="frm">
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
    <td width="3%" >&nbsp;</td>
<%--      <td width="2%" >&nbsp;</td>  --%>
     <td width="3%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="3%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="6%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="42%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[7]%></td>
	      <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[9]%></td>
     <td width="30%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[14]%></td>
     <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[15]%></td>
     <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
     <td width="3%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>

     <td width="2%" >&nbsp;</td>
  </tr>
   <%int r = 0;
   while(iterPedidos_Cabe.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterPedidos_Cabe.next(); 
      // estos campos hay que setearlos segun la grilla 
      String plantillaImpresionJRXML= "notas_pedido_regalos";
	  String fechaPrometida = Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[15]), "JSTsToStr") + "" ;
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";
	  //System.out.println("sCampos[1]--->: " + sCampos[1]);
	  String datos="";
	  datos+= "<strong>" + tituCol[7] + ": </strong>" + sCampos[7] + "<br>";
	  datos+= "<strong>" + tituCol[8] + ": </strong>" + sCampos[8] + "<br>";
	  datos+= "<strong>" + tituCol[9] + ": </strong>" + sCampos[9] + "<br>";
      datos+= "<strong>" + tituCol[10] + ": </strong>" + sCampos[10] + "<br>";
	  %>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
      <td class="fila-det-border" >
	  <% 
        if(Common.setNotNull(sCampos[4]).equals("S")){ %> 
        <img src="../imagenes/default/gnome_tango/status/gtk-dialog-warning.png" width="18" height="18" title="Pedido hijo inválido, posee mas de un registro de detalle">
       <% 
	    //20101125
        //}else if(Common.setNotNull(sCampos[2]).equals("1")){
		}else if(Common.setNotNull(sCampos[2]).equals("3")){
		 %> 
        <img src="../imagenes/default/gnome_tango/actions/down.png" width="18" height="17" onClick="bajarDatos(<%=sCampos[0]%>, <%=sCampos[6]%>, '<%=sCampos[7]%>', '<%=sCampos[9]%>', '<%=sCampos[10]%>', <%=sCampos[5]%>, '<%=fechaPrometida%>');" >
        <% 
        } else out.write("&nbsp;"); %>      </td> 
      <%--       <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/actions/finish.png" title="Detalle" width="18" height="18" onClick="getDetalle(<%=sCampos[0]%>, '<%=sCampos[30]%>');"></td> --%> 
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[3]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[7].length() < 30 ?  sCampos[7] : sCampos[7].substring(0, 30) %>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[9]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[14]%>&nbsp;</td>
      <td class="fila-det-border" ><%=fechaPrometida%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[5]%>&nbsp;</td>
      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/status/dialog-information.png" width="18" height="18" onMouseOver="overlib(' <%= datos  %>', STICKY, CAPTION, 'INFO: ',TIMEOUT,5000,HAUTO,VAUTO,WIDTH,350);"></div></td>
      <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" title="Reimprimir " width="18" height="18" onClick="abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=<%= plantillaImpresionJRXML %>&idpedido_cabe=<%=sCampos[0]%>','pedido',750, 400)"></td>
  </tr>

   
<%
   }%>
   </table>
   <input name="accion" value="" type="hidden">
   <input name="idpedido_cabe" type="hidden" id="idpedido_cabe" value="">
   <input name="tipopedido" type="hidden" id="tipopedido" value="">
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

