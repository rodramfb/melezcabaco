<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vclientesCtaCtes
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Mar 25 08:50:26 GYT 2009 
   Observaciones: 
      .


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.*" %> 
<%@ page import="java.math.*" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%@ page import="java.math.BigDecimal" %>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "Consulta de Cta. Cte. Cliente: ";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVclientesCtaCtes   = null;
int totCol = 11; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVCCA"  class="ar.com.syswarp.web.ejb.BeanVclientesCtaCtesAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVCCA" property="*" />
<%
 BVCCA.setResponse(response);
 BVCCA.setRequest(request);
 BVCCA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BVCCA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>style.css">
 <link rel="stylesheet" href="../imagenes/default/erp-style.css">

 <script language="JavaScript" src="<%=pathskin%>/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
</head>
<%
// titulos para las columnas
tituCol[0] = "idcliente";
tituCol[1] = "razon";
tituCol[2] = "Fecha";
tituCol[3] = "Tipo";
tituCol[4] = "idtipomov";
tituCol[5] = "Comprobante";
tituCol[6] = "Debe";
tituCol[7] = "Haber";
tituCol[8] = "Saldo";
tituCol[9] = "F.Vto";
tituCol[10] = "nrointerno";
java.util.List VclientesCtaCtes = new java.util.ArrayList();
VclientesCtaCtes= BVCCA.getVclientesCtaCtesList();
iterVclientesCtaCtes = VclientesCtaCtes.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="vclientesCtaCtesAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td  class="text-globales"><%=titulo + BVCCA.getIdcliente() + " - " + BVCCA.getCliente() %></td>
                </tr>
                <tr>
                   <td  class="text-globales"><hr color="#FFFFFF"></td>
                </tr>
                <tr>
                  <td width="89%" height="38">
                     <table width="100%" border="0">
                        <tr>
                          <td width="8%" height="26" class="text-globales">Buscar</td>
                          <td width="20%">
                             <input name="ocurrencia" type="text" value="<%=BVCCA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                           </td>
                          <td width="72%">
                            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td>
                                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                       <tr class="text-globales">
                                         <td width="20%" height="19">&nbsp;Total de registros:&nbsp;<%= BVCCA.getTotalRegistros() + "" %></td>
                                         <td width="13%" >Visualizar:</td>
                                         <td width="12%">
                                            <select name="limit" >
                                               <%for(i=15; i<= 150 ; i+=15){%>
                                                   <%if(i==BVCCA.getLimit()){%>
                                                       <option value="<%=i%>" selected><%=i%></option>
                                                   <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                   <%}
                                                      if( i >= BVCCA.getTotalRegistros() ) break;
                                                    %>
                                               <%}%>
                                               <option value="<%= BVCCA.getTotalRegistros()%>">Todos</option>
                                            </select>                                          </td>
                                         <td width="8%">&nbsp;P&aacute;gina:</td>
                                         <td width="13%">
                                            <select name="paginaSeleccion" >
                                               <%for(i=1; i<= BVCCA.getTotalPaginas(); i++){%>
                                                   <%if ( i==BVCCA.getPaginaSeleccion() ){%>
                                                      <option value="<%=i%>" selected><%=i%></option> 
                                                   <%}else{%>
                                                      <option value="<%=i%>"><%=i%></option>
                                                   <%}%>
                                               <%}%>
                                            </select>                                          </td>
                                         <td width="11%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                                      </tr>
                                   </table>                                 </td>
                              </tr>
                          </table>                        </td>
                     </tr>
                        <tr class="text-globales">
                          <td height="26" >Pendiente: </td>
                          <td><input name="historico" type="radio" value="P" <%=  BVCCA.getHistorico().equals("P") ? "checked" : "" %>></td>
                          <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr class="text-globales">
                              <td width="20%" height="19">&nbsp;</td>
                              <td width="13%" > Todo:</td>
                              <td width="12%"><input name="historico" type="radio" value="H" <%=  BVCCA.getHistorico().equals("H") ? "checked" : "" %>></td>
                              <td width="8%">&nbsp;</td>
                              <td width="13%">&nbsp;</td>
                              <td width="11%" class="text-globales">&nbsp;</td>
                            </tr>
                          </table></td>
                        </tr>
                   </table>                </td>
            </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVCCA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="14%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="22%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
     <td width="15%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[8]%></div></td>
     <td width="15%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[6]%></div></td>
     <td width="15%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[7]%></div></td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
  </tr>
   <%int r = 0;
   while(iterVclientesCtaCtes.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVclientesCtaCtes.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><%=Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[2]), "JSDateToStr")%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[3]%>-<%=sCampos[5]%></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[8]%></div></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[6]%></div></td> 
      <td class="fila-det-border" ><div align="right"><%=sCampos[7]%></div></td>
      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/calc.png" title="Asiento contable del movimiento." width="18" height="18"  onClick="ventana=abrirVentana('vclientesAsientosAbm.jsp?nrointerno=<%= sCampos[10] %>&comprobante=<%=sCampos[3] + "-" +sCampos[5]%>', 'asie', 600, 300)" style="cursor:pointer"></div></td>
   </tr>
<%
   }%>

   <tr class="text-dos-bold" > 
      <td colspan="2" >Saldo Documentado </td>
      <td ><div align="right"><%=BVCCA.getSaldo_total()%></div></td>
      <td colspan="3" ></td> 
    </tr>
   </table>

   <input name="accion" value="" type="hidden">
   <input name="idcliente" value="<%=  BVCCA.getIdcliente() %>" type="hidden">
   <input name="cliente" value="<%=  BVCCA.getCliente() %>" type="hidden">
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


