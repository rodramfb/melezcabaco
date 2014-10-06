<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: bacoRefCatalogo
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Jun 15 15:54:26 ART 2010 
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
String titulo = "CATALOGO - REFERIDOS";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterBacoRefCatalogo   = null;
int totCol = 11; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BBRCA"  class="ar.com.syswarp.web.ejb.BeanBacoRefCatalogoAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BBRCA" property="*" />
<%
 BBRCA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BBRCA.setUsuarioact( session.getAttribute("usuario").toString() );
 BBRCA.setResponse(response);
 BBRCA.setRequest(request);
 BBRCA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>style.css">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="<%=pathskin%>/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Cód.";
tituCol[1] = "Artículo";
tituCol[2] = "Descripción";
tituCol[3] = "Puntaje";
tituCol[4] = "Compr.";
tituCol[5] = "Util.";
tituCol[6] = "F.Desde";
tituCol[7] = "F.Hasta";
tituCol[8] = "F.Baja";
tituCol[9] = "idcatalogocategoria";
tituCol[10] = "Categoria";
java.util.List BacoRefCatalogo = new java.util.ArrayList();
BacoRefCatalogo= BBRCA.getBacoRefCatalogoList();
iterBacoRefCatalogo = BacoRefCatalogo.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="bacoRefCatalogoAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="../imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="35" colspan="2"  class="text-globales"><%=titulo%></td>
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
                           <td width="72%" height="26">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BBRCA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BBRCA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BBRCA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BBRCA.getTotalRegistros()%>">Todos</option>
                                             </select>                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BBRCA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BBRCA.getPaginaSeleccion() ){%>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BBRCA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
    <td >&nbsp;</td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><input name="filtroCodigoSt" type="text" value="<%=BBRCA.getFiltroCodigoSt()%>" id="filtroCodigoSt" size="5" maxlength="15"></td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><span class="fila-det-border">
      <select name="filtroIdcatalogocategoria" id="filtroIdcatalogocategoria" class="campo" style="width:100%" >
        <option value="-1" >Seleccionar</option>
        <% 
					  Iterator iter = BBRCA.getListCatalogoCategoria().iterator();
					  while(iter.hasNext()){
						String [] datos = (String[])iter.next();%>
        <option value="<%= datos[0] %>" <%= datos[0].equals( BBRCA.getFiltroIdcatalogocategoria().toString()) ? "selected" : "" %> lang="<%= datos[2]%>"><%= datos[1] %></option>
        <%  
					  }%>
      </select>
    </span></td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
  </tr>
  <tr class="fila-encabezado">
     <td width="2%" >&nbsp;</td>
     <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="32%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="28%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[10]%></td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[3]%></div></td>
     <td width="6%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[4]%></div></td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[5]%></div></td>
     <td width="6%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[6]%></div></td>
     <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[7]%></div></td>
     <td width="3%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[8]%></div></td>
    </tr>
   <%int r = 0;
   while(iterBacoRefCatalogo.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterBacoRefCatalogo.next(); 
      // estos campos hay que setearlos segun la grilla 
      if(!Common.setNotNull(sCampos[8]).equals("") )color_fondo = "permiso-tres";
      else if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><input type="radio" name="idcatalogo" value="<%= sCampos[0]%>" <%= !Common.setNotNull(sCampos[8]).equals("") ? "disabled" : "" %> ></td>
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[2]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[10]%></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[3]%>&nbsp;</div></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[4]%>&nbsp;</div></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[5]%>&nbsp;</div></td>
      <td class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[6]), "JSDateToStr")%>&nbsp;</div></td>
      <td class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[7]), "JSDateToStr")%>&nbsp;</div></td>
      <td class="fila-det-border" ><div align="center"><%=!Common.setNotNull(sCampos[8]).equals("") ? Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[8]), "JSDateToStr") : "" %>&nbsp;</div></td>
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

