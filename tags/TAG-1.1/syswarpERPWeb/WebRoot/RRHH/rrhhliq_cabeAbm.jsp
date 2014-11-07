<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: rrhhliq_cabe
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jul 26 14:23:25 ART 2012 
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
String titulo = "Liquidación de sueldos y jornales";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterRrhhliq_cabe   = null;
int totCol = 18; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BRA"  class="ar.com.syswarp.web.ejb.BeanRrhhliq_cabeAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BRA" property="*" />
<%
 BRA.setResponse(response);
 BRA.setRequest(request);
 BRA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BRA.ejecutarValidacion();
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
tituCol[1] = "Nro. recibo";
tituCol[2] = "Legajo";
tituCol[3] = "Fecha";
tituCol[4] = "Periodo";
tituCol[6] = "Quincena";
tituCol[7] = "Categoria";
tituCol[8] = "Localidad dep.";
tituCol[9] = "Banco dep.";
tituCol[10] = "Modalidad contrato";
tituCol[11] = "Fecha dep.";
tituCol[12] = "Importe";
tituCol[13] = "Total Remunerativo";
tituCol[14] = "Total No Remunerativo";
tituCol[15] = "Descuentos";
tituCol[16] = "Neto a cobrar";
java.util.List Rrhhliq_cabe = new java.util.ArrayList();
Rrhhliq_cabe= BRA.getRrhhliq_cabeList();
iterRrhhliq_cabe = Rrhhliq_cabe.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="rrhhliq_cabeAbm.jsp" method="POST" name="frm">
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
                            <td width="27%">
                               <input name="alta" id="alta" value="alta" type="image" src="../imagenes/default/btn_add_norm.gif"  onMouseOut="MM_swapImgRestore() " onMouseOver="MM_swapImage('alta','','../imagenes/default/btn_add_over.gif',1)" onClick="document.frm.accion.value = this.name">
                            </td>
                            <td width="27%">
                               <input name="modificacion" id="modificacion" value="modificacion" type="image" src="../imagenes/default/btn_edit_norm.gif"  onMouseOut="MM_swapImgRestore() " onMouseOver="MM_swapImage('modificacion','','../imagenes/default/btn_edit_over.gif',1)" onClick="document.frm.accion.value = this.name">
                            </td>
                            <td width="27%">
                               <input name="baja" id="baja" value="baja" type="image" src="../imagenes/default/btn_remove_norm.gif"  onMouseOut="MM_swapImgRestore() " onMouseOver="MM_swapImage('baja','','../imagenes/default/btn_remove_over.gif',1)" onClick="return confirmarBaja('frm');"> 
                            </td>
                         </tr>
                      </table>
                   </td>
                   <td width="89%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BRA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">
                           </td>
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
                                                        <option value="<%=i%>" selected="selected"><%=i%></option>
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
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
	<td colspan="7">
	</td>
	<td colspan="5" align="center">
		Detalles del cobro
	</td>
	<td colspan="5" align="center">
		Importes haberes
	</td>
  </tr>
  <tr class="fila-encabezado">
  	 <td >&nbsp;</td>
     <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
     <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[6]%></td>
     <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[7]%></td>
     <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[8]%></td>
     <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[9]%></td>
     <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[10]%></td>
     <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[11]%></td>
     <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[12]%></td>
     <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[13]%></td>
     <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[14]%></td>
     <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[15]%></td>
     <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[16]%></td>
   </tr>
   <%int r = 0;
   while(iterRrhhliq_cabe.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterRrhhliq_cabe.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><input type="radio" name="idliqcabe" value="<%= sCampos[0]%>"></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[0]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[1]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[2]%> - <%=sCampos[3]%></td>
      <td class="fila-det-border" >&nbsp;<%=Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[4]) , "JSDateTOStr").toString()%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[6]%>/<%=sCampos[5]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[7]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[9]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[11]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[13]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[15]%></td>
      <td class="fila-det-border" >&nbsp;<%=Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[16]) , "JSDateTOStr").toString()%></td>
      <td class="fila-det-border" >&nbsp;<div align="right"><%=sCampos[17]%></div></td>
      <td class="fila-det-border" >&nbsp;<div align="right"><%=sCampos[18]%></div></td>
      <td class="fila-det-border" >&nbsp;<div align="right"><%=sCampos[19]%></div></td>
      <td class="fila-det-border" >&nbsp;<div align="right"><%=sCampos[20]%></div></td>
      <td class="fila-det-border" >&nbsp;<div align="right"><%=sCampos[21]%></div></td>
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

