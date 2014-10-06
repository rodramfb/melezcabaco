<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: clientesClientes
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Feb 12 11:19:57 ART 2008 
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
String titulo = "PROSPECTOS PENDIENTES ";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterClientesClientes   = null;
int totCol = 24; // cantidad de columnas
String[] tituCol = new String[totCol]; 
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BCCA"  class="ar.com.syswarp.web.ejb.BeanClientesPrecargaActivarAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BCCA" property="*" />
<%
 BCCA.setUsuarioalt(usuario);
 BCCA.setResponse(response);
 BCCA.setRequest(request);
 BCCA.setIdempresa(new BigDecimal(session.getAttribute("empresa").toString()));
 BCCA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="scripts/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js">
 </script>
 <script>
 
 function confirmarPasaje(indice){ 
   if(confirm("Confirma conversión a Cliente?") ){
      document.frm.accion.value = "pasaje";
	  if(document.frm.idprecarga.length)
	    document.frm.idprecarga[indice].checked = true;
	  else
   	    document.frm.idprecarga.checked = true;
        document.frm.submit();
   }   
 }
 
 
 
 </script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Cód."; 
tituCol[1] = "Razón Social";
tituCol[2] = "id tipo. Doc.";
tituCol[3] = "T. Doc.";
tituCol[4] = "Documento";
tituCol[5] = "brutos";
tituCol[6] = "idtipoiva";
tituCol[7] = "idvendedor";
tituCol[9] = "idcondicion";
tituCol[9] = "descuento1";
tituCol[10] = "descuento2";
tituCol[11] = "descuento3";
tituCol[12] = "Cuenta";
tituCol[13] = "idmoneda";
tituCol[14] = "idlista";
tituCol[15] = "idtipoclie";
tituCol[16] = "observacion";
tituCol[17] = "lcredito";
tituCol[18] = "idtipocomp"; 
tituCol[19] = "autorizado";
tituCol[20] = "idcredcate";
tituCol[21] = "idempresa";
tituCol[22] = "Convertir en Cliente";
tituCol[23] = "CLUB";

java.util.List ClientesClientes = new java.util.ArrayList();
ClientesClientes= BCCA.getClientesClientesList();
iterClientesClientes = ClientesClientes.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="clientesPrecargaActivarAbm.jsp" method="POST" name="frm" >
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
                              <input name="ocurrencia" type="text" value="<%=BCCA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BCCA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BCCA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BCCA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BCCA.getTotalRegistros()%>">Todos</option>
                                             </select>                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BCCA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BCCA.getPaginaSeleccion() ){%>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BCCA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
    <td width="3%" >&nbsp;</td>
    <td width="9%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[23]%></div></td>
    <td width="9%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
    <td width="30%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
    <td width="15%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[12]%></td>
    <td width="15%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
    <td width="16%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
    <td width="12%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[22]%></div></td>
  </tr>
  <%int r = 0;
   while(iterClientesClientes.hasNext()){
      
      String[] sCampos = (String[]) iterClientesClientes.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
  <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
    <td class="fila-det-border" ><input type="radio" name="idprecarga" value="<%= sCampos[0]%>"></td>
    <td class="fila-det-border" ><div align="center"><img src="<%=sCampos[44]%>" title="<%=sCampos[43]%>"></div></td> 
    <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
    <td class="fila-det-border" ><%=sCampos[1]%></td>
    <td class="fila-det-border" ><%=sCampos[12]%>&nbsp;</td>
    <td class="fila-det-border" ><%=sCampos[3]%>&nbsp;</td>
    <td class="fila-det-border" ><%=sCampos[4]%>&nbsp;</td>
	<td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/actions/go-next.png"   width="18" height="18" onClick="confirmarPasaje('<%=r%>' )"   title="Activar Prospecto"></div></td>
  </tr>
  <%
     r++;
   }%>
</table>
<input name="accion" value="" type="hidden">
<input name="referer" value="activar" type="hidden">
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

