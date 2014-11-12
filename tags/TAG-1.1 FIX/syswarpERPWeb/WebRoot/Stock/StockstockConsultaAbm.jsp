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
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();

//System.out.println("tipo " + tipo );
// variables de paginacion
int i = 0;
Iterator iterStockstock   = null;
int totCol = 52; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BSA"  class="ar.com.syswarp.web.ejb.BeanStockstockConsultaAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BSA" property="*" />
<%
 BSA.setResponse(response);
 BSA.setRequest(request);
 BSA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BSA.ejecutarValidacion();
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
tituCol[0] = "Codigo";//codigo_st
tituCol[1] = "Alias";//alias_st
tituCol[2] = "Descripcion";//descrip_st
tituCol[3] = "Descripcion Corta";//descri2_st
tituCol[4] = "Costos P.P.";//cost_pp_st
tituCol[5] = "Precio P.P.";//precipp_st
tituCol[6] = "Costo Ultima Compra";//cost_uc_st
tituCol[7] = "Precio Ultima Compra";//ultcomp_st
tituCol[8] = "Costo Rep.";//cost_re_st
tituCol[9] = "Precio Rep.";//reposic_st
tituCol[10] = "Codigo de Moneda";//nom_com_st
tituCol[11] = "Moneda de compras";// moneda
tituCol[12] = "Codigo Grupo";//grupo_st
tituCol[13] = "Grupo";//descrip_gr
tituCol[14] = "Cantidad Minima";//cantmin_st
tituCol[15] = "Codigo Medida"; //unimed_st
tituCol[16] = "Medida";//descrip_md
tituCol[17] = "Bonif.";//bonific_st
tituCol[18] = "Imp. Intern.";//impint_st
tituCol[19] = "Imp. Cantid.";//impcant_st
tituCol[20] = "C.Cont. Compras";//cuencom_st
tituCol[21] = "C.Cont. Ventas";//cuenven_st
tituCol[22] = "C.Cont. Ventas2";//cuenve2_st
tituCol[23] = "C.Cont. Costos";//cuencos_st
tituCol[24] = "C.Cont. Ajuste";//cuenaju_st
tituCol[25] = "Inventario";//inventa_st
tituCol[26] = "Cod.Proveedor Hab";//proveed_st
tituCol[27] = "Proveedor Hab";//razon_social
tituCol[28] = "Cod.Art. Proveedor";//provart_st
tituCol[29] = "Indice";//id_indi_st
tituCol[30] = "Despacho";//despa_st
tituCol[31] = "Marca";//marca_st
tituCol[32] = "cafecga_st";//cafecga_st
tituCol[33] = "Cod U.Alternativa 1";//unialt1_st
tituCol[34] = "Alternativa 1";//descrip_md
tituCol[35] = "U.Alternativa 2";//unialt2_st
tituCol[36] = "Alternativa 2";//descrip_md
tituCol[37] = "U.Alternativa 3";//unialt3_st
tituCol[38] = "Alternativa 3";//descrip_md
tituCol[39] = "U.Alternativa 4";//unialt4_st
tituCol[40] = "Alternativa 4";//descrip_md
tituCol[41] = "Factor 1";//factor1_st
tituCol[42] = "Factor 2";//factor2_st
tituCol[43] = "Factor 3";//factor3_st
tituCol[44] = "Factor 4";//factor4_st
tituCol[45] = "Codigo Tipo I.V.A.";//tipoiva_st
tituCol[46] = "Tipo I.V.A.";//tipoiva_st
tituCol[47] = "Prod. Venta?";//venta_st
tituCol[48] = "Prod. Compra?";//compra_st
tituCol[49] = "Esquema Produccion";//esquema_st
tituCol[50] = "Es Serializable";//esquema_st
tituCol[51] = "Numero de Serie";//esquema_st
java.util.List Stockstock = new java.util.ArrayList();
Stockstock= BSA.getStockstockList();
iterStockstock = Stockstock.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="StockstockConsultaAbm.jsp" method="POST" name="frm">
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
                      <table width="36%" border="0">
                         <tr>
                            <td width="27%">&nbsp;</td>
                            <td width="27%">
                               <input name="modificacion" id="modificacion" value="modificacion" type="image" src="../imagenes/default/btn_edit_norm.gif"  onMouseOut="MM_swapImgRestore() " onMouseOver="MM_swapImage('modificacion','','../imagenes/default/btn_edit_over.gif',1)" onClick="document.frm.accion.value = this.name">
                            </td>
                            <td width="27%">&nbsp;</td>
                         </tr>
                      </table>
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
	 <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[41]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[42]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[43]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[44]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[45]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[46]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[47]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[48]%></td>
	 <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[49]%></td>
	 <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[50]%></td>
	 <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[51]%></td>
   </tr>
   <%int r = 0;
   while(iterStockstock.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterStockstock.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
 
      <td class="fila-det-border" >
           <input type="radio" name="codigo_st" value="<%= sCampos[0]%>">
      </td>

      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[0])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[1])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[2])%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[3]%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[4])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[5])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[6])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[7])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[8])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[9])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[10])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[11])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[12])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[13])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[14])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[15])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[16])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[17])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[18])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[19])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[20])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[21])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[22])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[23])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[24])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[25])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[26])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[27])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[28])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[29])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[30])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[31])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[32])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[33])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[34])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[35])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[36])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[37])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[38])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[39])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[40])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[41])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[42])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[43])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[44])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[45])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[46])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[47])%></td>
      <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[48])%></td>	
	  <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[49])%></td>	
	  <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[50])%></td>	
	  <td class="fila-det-border" >&nbsp;<%=str.esNulo(sCampos[51])%></td>	
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

