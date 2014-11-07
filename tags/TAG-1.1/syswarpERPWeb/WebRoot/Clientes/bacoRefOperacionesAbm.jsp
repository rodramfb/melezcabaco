<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: bacoRefOperaciones
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Jun 15 11:32:56 ART 2010 
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
String titulo = "REFERIDOS - OPERACIONES";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterBacoRefOperaciones   = null;
int totCol = 10; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BBROA"  class="ar.com.syswarp.web.ejb.BeanBacoRefOperacionesAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BBROA" property="*" />
<%
 BBROA.setResponse(response);
 BBROA.setRequest(request);
 BBROA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));   
 BBROA.setUsuarioact( session.getAttribute("usuario").toString() );
 BBROA.ejecutarValidacion();
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
tituCol[1] = "Operación";
tituCol[2] = "Descripción";
tituCol[3] = "Puntaje";
tituCol[4] = "Tipo";
tituCol[5] = "Signo";
tituCol[6] = "F.Desde";
tituCol[7] = "F.Hasta";
tituCol[8] = "F.Baja";
tituCol[9] = "idempresa";
java.util.List BacoRefOperaciones = new java.util.ArrayList();
BacoRefOperaciones= BBROA.getBacoRefOperacionesList();
iterBacoRefOperaciones = BacoRefOperaciones.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="bacoRefOperacionesAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="../imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="30" colspan="2"  class="text-globales"><%=titulo%></td>
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
                      </table>                  </td>
                   <td width="89%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BBROA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BBROA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BBROA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BBROA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BBROA.getTotalRegistros()%>">Todos</option>
                                             </select>                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BBROA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BBROA.getPaginaSeleccion() ){%>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BBROA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="2%" >&nbsp;</td>
     <td width="3%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="35%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="12%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[3]%></div></td>
     <td width="9%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
     <td width="6%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[5]%></div></td>
     <td width="8%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[6]%></div></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[7]%></div></td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[8]%></div></td>
    </tr>
   <%int r = 0;
   while(iterBacoRefOperaciones.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterBacoRefOperaciones.next(); 
      // estos campos hay que setearlos segun la grilla 
	  if(!Common.setNotNull(sCampos[8]).equals("") )color_fondo = "permiso-tres";
      else if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><input type="radio" name="idoperacion" value="<%= sCampos[0]%>" <%= !Common.setNotNull(sCampos[8]).equals("") ? "disabled" : "" %>></td>
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[2]%>&nbsp;</td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[3]%>&nbsp;</div></td>
      <td class="fila-det-border" ><%=sCampos[4]%>&nbsp;</td>
      <td class="fila-det-border" ><div align="center"><%=sCampos[5]%>&nbsp;</div></td>
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

