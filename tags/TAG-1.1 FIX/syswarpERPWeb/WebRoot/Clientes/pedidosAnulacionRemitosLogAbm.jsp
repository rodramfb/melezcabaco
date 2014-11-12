<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: pedidosAnulacionRemitosLog
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Feb 23 17:03:04 GMT-03:00 2010 
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
String titulo = "REMITOS ANULADOS - DESVINCULADOS";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterPedidosAnulacionRemitosLog   = null;
int totCol = 9; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BPARLA"  class="ar.com.syswarp.web.ejb.BeanPedidosAnulacionRemitosLogAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BPARLA" property="*" />
<%
 BPARLA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BPARLA.setResponse(response);
 BPARLA.setRequest(request);
 BPARLA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>style.css">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="<%=pathskin%>/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script>
<!--
function setTipoPedido(obj){
  var tipopedido = '<%=BPARLA.getTipopedido()%>';
  if(obj.value != tipopedido){
    document.frm.accion.value = '';
    //document.getElementById("consolidar").disabled = true;
    document.frm.submit();
  } 

}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}
//-->
</script>

</head>
<%
// titulos para las columnas
/*
lg.idlog, lg.idtransaccion, lg.idpedido, cr.nrosucursal, cr.nroremitocliente, "
				+ "       cl.idcliente, cl.razon, lg.usuarioalt, lg.fechaalt
*/
tituCol[0] = "idlog";
tituCol[1] = "Trans.";
tituCol[2] = "Pedido";
tituCol[3] = "Sucursal";
tituCol[4] = "Comprobante";
tituCol[5] = "IdCliente";
tituCol[6] = "Cliente";
tituCol[7] = "Usuario";
tituCol[8] = "Fecha";
java.util.List PedidosAnulacionRemitosLog = new java.util.ArrayList();
PedidosAnulacionRemitosLog= BPARLA.getPedidosAnulacionRemitosLogList();
iterPedidosAnulacionRemitosLog = PedidosAnulacionRemitosLog.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="pedidosAnulacionRemitosLogAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="31"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="98%" height="38">
                     <table width="100%" border="0">
                        <tr>
                          <td width="5%" class="text-globales"><table width="36%" border="0">
                            <tr>
                              <td width="27%"><input name="alta" id="alta" value="alta" type="image" src="../imagenes/default/btn_add_norm.gif"  onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('alta','','../imagenes/default/btn_add_over.gif',1)" onClick="document.frm.accion.value = this.name">
                              </td>
                              <td width="27%">&nbsp;</td>
                              <td width="27%">&nbsp;</td>
                            </tr>
                          </table></td>
                          <td width="13%" height="26" class="text-globales">Buscar</td>
                          <td width="18%">
                             <input name="ocurrencia" type="text" value="<%=BPARLA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                           </td>
                          <td width="64%">
                            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td>
                                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                       <tr class="text-globales">
                                         <td width="23%" height="19">&nbsp;Total de registros:&nbsp;<%=BPARLA.getTotalRegistros()+""%></td>
                                         <td width="11%" >Visualizar:</td>
                                         <td width="11%">
                                            <select name="limit" >
                                               <%for(i=15; i<= 150 ; i+=15){%>
                                                   <%if(i==BPARLA.getLimit()){%>
                                                       <option value="<%=i%>" selected><%=i%></option>
                                                   <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                   <%}
                                                      if( i >= BPARLA.getTotalRegistros() ) break;
                                                    %>
                                               <%}%>
                                               <option value="<%= BPARLA.getTotalRegistros()%>">Todos</option>
                                            </select>                                          </td>
                                         <td width="7%">&nbsp;P&aacute;gina:</td>
                                         <td width="12%">
                                            <select name="paginaSeleccion" >
                                               <%for(i=1; i<= BPARLA.getTotalPaginas(); i++){%>
                                                   <%if ( i==BPARLA.getPaginaSeleccion() ){%>
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
                        <tr>
                          <td class="text-globales">&nbsp;</td>
                          <td height="26" class="text-globales">Pedido Normal</td>
                          <td><span class="fila-det-border">
                            <input name="tipopedido" type="radio" class="campo" value="N" <%= BPARLA.getTipopedido().equalsIgnoreCase("N") ? "checked" : "" %> onClick="setTipoPedido(this);" >
                          </span></td>
                          <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr class="text-globales">
                              <td width="12%"><span class="fila-det-border">R.Empresario
                              </span></td>
                              <td width="88%"><span class="fila-det-border">
                                <input name="tipopedido" type="radio" class="campo" value="R" <%= BPARLA.getTipopedido().equalsIgnoreCase("R") ? "checked" : "" %> onClick="setTipoPedido(this);">
                              </span></td>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BPARLA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="3%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
     <td width="44%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[6]%></td>
     <td width="20%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[7]%></td>
     <td width="17%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[8]%></td>
  </tr>
   <%int r = 0;
   String transaccion = "";
   while(iterPedidosAnulacionRemitosLog.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterPedidosAnulacionRemitosLog.next(); 
      // estos campos hay que setearlos segun la grilla
      if(!transaccion.equals(sCampos[1])) {
        if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
        else color_fondo = "fila-det-verde";
        transaccion = sCampos[1];
      }
%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[2]%>&nbsp;</td>
      <td class="fila-det-border" ><%=Common.strZero(sCampos[3], 4)%>-<%=Common.strZero(sCampos[4], 8)%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[5]%>-<%=sCampos[6]%></td>
      <td class="fila-det-border" ><%=sCampos[7]%></td>
      <td class="fila-det-border" ><%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[8]), "JSTsToStrWithHM")%></td>
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

