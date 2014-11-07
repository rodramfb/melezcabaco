<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: crmProductos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Aug 03 11:44:52 ART 2007 
   Observaciones: 
      .


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.math.*" %> 
<%@ page import="java.util.*" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "PRODUCTOS";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterCrmProductos   = null;
int totCol = 15; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
Hashtable htStatus = new Hashtable();
htStatus.put("disponible", "#00CC66"); 
htStatus.put("reservado", "#FFFF99"); 
htStatus.put("vendido", "#FF0000"); 
%>
<html>
<jsp:useBean id="BCPA"  class="ar.com.syswarp.web.ejb.BeanCrmProductosAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BCPA" property="*" />
<%
 BCPA.setResponse(response);
 BCPA.setRequest(request);                  
 BCPA.setIdempresa(new BigDecimal( session.getAttribute("empresa").toString() ));    
 BCPA.ejecutarValidacion();
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
tituCol[0] = "Lote"; 
tituCol[1] = "idfamiliacotizacion";
tituCol[2] = "Familia";
tituCol[3] = "idgrupoproducto";
tituCol[4] = "Grupo";
tituCol[5] = "idproductostatus";
tituCol[6] = "Status";
tituCol[7] = "Calif.";
tituCol[8] = "Sup.";
tituCol[9] = "P. x Mts.";
tituCol[10] = "Precio";
tituCol[11] = "Contado";
tituCol[12] = "Boleto";
tituCol[13] = "C. x 36";
tituCol[14] = "idempresa";
java.util.List CrmProductos = new java.util.ArrayList();
CrmProductos= BCPA.getCrmProductosList();
iterCrmProductos = CrmProductos.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="crmProductosAbm.jsp" method="POST" name="frm">
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
                              <input name="ocurrencia" type="text" value="<%=BCPA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BCPA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BCPA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BCPA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BCPA.getTotalRegistros()%>">Todos</option>
                                             </select>                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BCPA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BCPA.getPaginaSeleccion() ){%>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BCPA" property="mensaje"/></td>
  </tr> 
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="4%" rowspan="2" >&nbsp;</td>
     <td colspan="6" valign="bottom" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center">
       <table width="100%" border="1" cellpadding="0" cellspacing="0"  class="fila-det">
         <tr>
           <td width="25%" >STATUS</td>
           <td width="25%" bgcolor="#00CC66" >Disponible</td>
           <td width="25%" bgcolor="#FFFF99" >Reservado</td>
           <td width="25%" bgcolor="#FF0000">Vendido </td>
         </tr>
       </table>
     </div>       <div align="center"></div>       <div align="center"></div>       <div align="center"></div>       <div align="center"></div>       <div align="center"></div></td>
     <td colspan="3" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center">Forma de Pago </div></td>
     <td width="4%" rowspan="2" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
  </tr>
  <tr class="fila-encabezado">
    <td width="7%" valign="bottom" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
    <td width="20%" valign="bottom" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
    <td width="7%" valign="bottom" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[7]%></td>
    <td width="10%" valign="bottom" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[8]%></td>
    <td width="9%" valign="bottom" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[9]%></td>
    <td width="8%" valign="bottom" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[10]%></td>
    <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[11]%></div></td>
    <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[12]%></div></td>
    <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[13]%></div></td>
  </tr>
   <%int r = 0;
   while(iterCrmProductos.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterCrmProductos.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-bold');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><input type="radio" name="nrolote" value="<%= sCampos[0]%>"></td>
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[2]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[7]%>&nbsp;</td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[8]%>&nbsp;</div></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[9]%>&nbsp;</div></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[10]%>&nbsp;</div></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[11]%>&nbsp;</div></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[12]%>&nbsp;</div></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[13]%>&nbsp;</div></td>
      <td class="fila-det-border" ><table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr bgcolor="<%=htStatus.get(sCampos[6].trim().toLowerCase())%>">
    <td>&nbsp;</td>
  </tr>
</table></td>    
   </tr>
   <!--tr height="3" bgcolor="<%=htStatus.get(sCampos[6].trim().toLowerCase())%>">
     <td colspan="11" height="3"  ></td>
    </tr-->
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

