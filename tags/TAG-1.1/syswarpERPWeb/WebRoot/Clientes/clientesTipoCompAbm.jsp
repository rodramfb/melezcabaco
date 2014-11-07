<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: clientesTipoComp
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jun 14 10:37:10 ART 2007 
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
String titulo = "TIPO DE COMPROBANTES";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterClientesTipoComp   = null;
int totCol = 47; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BCTCA"  class="ar.com.syswarp.web.ejb.BeanClientesTipoCompAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BCTCA" property="*" />
<%
 BCTCA.setResponse(response);
 BCTCA.setRequest(request);
 BCTCA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));   
 BCTCA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Cód.";
tituCol[1] = "Tipo Mov.";
tituCol[2] = "Descripción";
tituCol[3] = "Cuotas";
tituCol[4] = "Venta Al Contado";
tituCol[5] = "Mueve Stock";
tituCol[6] = "Modific Precio de Venta";
tituCol[7] = "Imprime Remito";
tituCol[8] = "Afecta Comisiones";
tituCol[9] = "Imprime Comprobante";
tituCol[10] = "Cantidad de Bonificaciones";
tituCol[11] = "Modifica Moneda Comprobante";
tituCol[12] = "Afecta Ing. B.";
tituCol[13] = "Contador Asociado";
tituCol[14] = "Cuenta Cont. IVA (R.Inscrip.)";
tituCol[15] = "Cuenta Cont. IVA (R. NO Inscrip.)";
tituCol[16] = "Cuenta Cont. N. Grav.";
tituCol[17] = "Cuenta Cont. N. Exento.";
tituCol[18] = "Ranking de Clientes";
tituCol[19] = "Modifica Transportista";
tituCol[20] = "Bonificacion por Art.";
tituCol[21] = "Remito despacho";
tituCol[22] = "Modifica Cond. Venta.";
tituCol[23] = "Recargo Uno";
tituCol[24] = "Formula Recargo Uno";
tituCol[25] = "C. Cont. Recargo Uno";
tituCol[26] = "Afecta Imp. Internos";
tituCol[27] = "Va al Neto Gravado";
tituCol[28] = "Recargo Dos";
tituCol[29] = "Formula Recargo Dos";
tituCol[30] = "C. Cont. Recargo Dos";
tituCol[31] = "Afecta Imp. Internos";
tituCol[32] = "Va al Neto Gravado";
tituCol[33] = "Recargo Tres";
tituCol[34] = "Formula Recargo Tres";
tituCol[35] = "C. Cont. Recargo Tres";
tituCol[36] = "Afecta Imp. Internos";
tituCol[37] = "Va al Neto Gravado";
tituCol[38] = "Recargo Cuatro";
tituCol[39] = "Formula Recargo Cuatro";
tituCol[40] = "C. Cont. Recargo Cuatro";
tituCol[41] = "Afecta Imp. Internos";
tituCol[42] = "Va al Neto Gravado";
tituCol[43] = "CC. Uno";
tituCol[44] = "CC. Dos";
tituCol[45] = "IVA Segun Art.";
tituCol[46] = "Jasper Asociado (path)";
java.util.List ClientesTipoComp = new java.util.ArrayList();
ClientesTipoComp= BCTCA.getClientesTipoCompList();
iterClientesTipoComp = ClientesTipoComp.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="clientesTipoCompAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td colspan="2"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                   <td width="11%" height="38">
                      <table width="36%" border="0">
                         <tr>
                            <td width="27%">
                               <input name="alta" id="alta" value="alta" type="image" src="../imagenes/default/btn_add_norm.gif"  onMouseOut="MM_swapImgRestore() " onMouseOver="MM_swapImage('alta','','../imagenes/default/btn_add_over.gif',1)" onClick="document.frm.accion.value = this.name">                            </td>
                            <td width="27%">
                               <input name="modificacion" id="modificacion" value="modificacion" type="image" src="../imagenes/default/btn_edit_norm.gif"  onMouseOut="MM_swapImgRestore() " onMouseOver="MM_swapImage('modificacion','','../imagenes/default/btn_edit_over.gif',1)" onClick="document.frm.accion.value = this.name">                            </td>
                            <td width="27%">
                               <input name="baja" id="baja" value="baja" type="image" src="../imagenes/default/btn_remove_norm.gif"  onMouseOut="MM_swapImgRestore() " onMouseOver="MM_swapImage('baja','','../imagenes/default/btn_remove_over.gif',1)" onClick="return confirmarBaja('frm');">                            </td>
                         </tr>
                      </table>                   </td>
                   <td width="89%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BCTCA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BCTCA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" class="campo">
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BCTCA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BCTCA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BCTCA.getTotalRegistros()%>">Todos</option>
                                             </select>                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion"  class="campo">
                                                <%for(i=1; i<= BCTCA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BCTCA.getPaginaSeleccion() ){%>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BCTCA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="3%" >&nbsp;</td>
     <td width="12%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="19%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="40%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="22%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
	 <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
   </tr>
   <%
   int r = 0;
   while(iterClientesTipoComp.hasNext()){
      ++r;
	  String masInfo = "";
      String[] sCampos = (String[]) iterClientesTipoComp.next(); 
	  for (i=4;i<=46;i++){
	    masInfo += tituCol[i] + ": " + str.esNulo(sCampos[i]).trim() + "\n";
	  }

      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><input type="radio" name="idtipocomp" value="<%= sCampos[0]%>"></td>
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%= BCTCA.getHtTipoMov().get(sCampos[1]) %>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[2]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[3]%>&nbsp;</td>
	  <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/status/dialog-information.png" width="18" height="18" style="cursor:pointer" border="0" title="<%=masInfo%>"></td>

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

