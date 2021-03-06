<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: rrhhothoras
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Apr 07 15:54:04 ART 2008 
   Observaciones: 
      .


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.Iterator" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.sql.Timestamp" %> 

<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "Horas x usuario";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterRrhhothoras   = null;
int totCol = 10; // cantidad de columnas 
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BRA"  class="ar.com.syswarp.web.ejb.BeanRrhhothorasXusuarioAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BRA" property="*" />
<%
 BRA.setResponse(response);
 BRA.setRequest(request);
 BRA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BRA.setUsuario(session.getAttribute("usuario").toString());
 BRA.ejecutarValidacion();
%>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
</head>

<%
// titulos para las columnas
tituCol[0] = "Orden Trabajo";
tituCol[1] = "Detalle";
tituCol[2] = "Fecha";
tituCol[3] = "Entrada";
tituCol[4] = "Salida";
tituCol[5] = "Entrada";
tituCol[6] = "Salida";
tituCol[7] = "Total";
java.util.List Rrhhothoras = new java.util.ArrayList();
Rrhhothoras= BRA.getRrhhothorasList();
iterRrhhothoras = Rrhhothoras.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="rrhhothorasxusuarioAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="46"  class="text-globales"><%=titulo%></td>
                   <td><table width="42%" height="30" border="0">
<tr class="fila-det-border">
<tr class="text-globales">
<td width="42%" >Orden de Trabajo </td>
<td width="39%"><input name="ordendetrabajo" type="text" class="campo" id="ordendetrabajo" value="<%=BRA.getOrdendetrabajo()%>" size="30" readonly></td>
<td width="19%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../RRHH/lov_ordentrabajo2.jsp')" style="cursor:pointer"></td>
<input name="idordendetrabajo" type="hidden" id="idordendetrabajo" value="<%=BRA.getIdordendetrabajo()%>">
</tr>
</table></td>
                </tr>
                <tr>
                <td width="13%" height="38"><table width="36%" border="0">
<tr>
<td width="27%"><input name="alta" id="alta" value="alta" type="image" src="../imagenes/default/btn_add_norm.gif"  onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('alta','','../imagenes/default/btn_add_over.gif',1)" onClick="document.frm.accion.value = this.name">
</td>
<td width="27%"><input name="modificacion" id="modificacion" value="modificacion" type="image" src="../imagenes/default/btn_edit_norm.gif"  onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('modificacion','','../imagenes/default/btn_edit_over.gif',1)" onClick="document.frm.accion.value = this.name">
</td>
<td width="27%"><input name="baja" id="baja" value="baja" type="image" src="../imagenes/default/btn_remove_norm.gif"  onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('baja','','../imagenes/default/btn_remove_over.gif',1)" onClick="return confirmarBaja('frm');">
</td>
</tr>
</table></td>
                   <td width="87%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <p>
<input name="ocurrencia" type="text" value="<%=BRA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">
                           </p></td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BRA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BRA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BRA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BRA.getTotalRegistros()%>">Todos</option>
                                             </select>
                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BRA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BRA.getPaginaSeleccion() ){%>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BRA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="4%" >&nbsp;</td>
     <td width="15%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="51%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="6%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[6]%></td>
	 <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[7]%></td>
</tr>
   <%int r = 0;
   Timestamp totalHoras = null;
   while(iterRrhhothoras.hasNext()){
      ++r;	  
      String[] sCampos = (String[]) iterRrhhothoras.next(); 
      
	  //totalHoras.     //.valueOf(sCampos[14]);
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><input type="radio" name="idothoras" value="<%= sCampos[0]%>"></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[2]%></td>
	  <td class="fila-det-border" >&nbsp;<%=sCampos[3]%></td>
	  <td class="fila-det-border" >&nbsp;<%=Common.setObjectToStrOrTime(Timestamp.valueOf(sCampos[4]),"JSTstoStr")%></td>
      <td class="fila-det-border" >&nbsp;<%=Common.setObjectToStrOrTime(Timestamp.valueOf(sCampos[5]),"JSTstoStrOnlyHour")%>:<%=Common.setObjectToStrOrTime(Timestamp.valueOf(sCampos[5]),"JSTstoStrOnlyMin")%></td>
      <td class="fila-det-border" >&nbsp;<%=Common.setObjectToStrOrTime(Timestamp.valueOf(sCampos[6]),"JSTstoStrOnlyHour")%>:<%=Common.setObjectToStrOrTime(Timestamp.valueOf(sCampos[6]),"JSTstoStrOnlyMin")%></td>	  
	  <td class="fila-det-border" >&nbsp;<%=Common.setObjectToStrOrTime(Timestamp.valueOf(sCampos[7]),"JSTstoStrOnlyHour")%>:<%=Common.setObjectToStrOrTime(Timestamp.valueOf(sCampos[7]),"JSTstoStrOnlyMin")%></td>	  
	  <td class="fila-det-border" >&nbsp;<%=Common.setObjectToStrOrTime(Timestamp.valueOf(sCampos[8]),"JSTstoStrOnlyHour")%>:<%=Common.setObjectToStrOrTime(Timestamp.valueOf(sCampos[8]),"JSTstoStrOnlyMin")%></td>
	  <td class="fila-det-border" >&nbsp;<%=sCampos[14]%></td>
   </tr>
<%
   }%>
</table>

<tr class="fila-det-border">
<tr class="text-globales">
<td width="23%">&nbsp;Total de Horas:&nbsp;<%=BRA.getTotalHoras()%></td>
</tr>
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

