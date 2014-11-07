<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: pedidos_Regalos_Entregas_Cabe
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Nov 13 10:00:59 GMT-03:00 2009 
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

// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterPedidos_Regalos_Entregas_Cabe   = null;
int totCol = 26; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BPRECA"  class="ar.com.syswarp.web.ejb.BeanPedidosRegalosEntregasAdministrar"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BPRECA" property="*" />
<%
 String titulo = "Administrar Entregas Pedido Regalos Empresarios Nro.: " + BPRECA.getIdpedido_regalos_cabe();
 BPRECA.setUsuarioact( session.getAttribute("usuario").toString() );
 BPRECA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));   
 BPRECA.setResponse(response);
 BPRECA.setRequest(request);
 BPRECA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>style.css">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script>
  function callOverlib(leyenda){
  //  overlib(leyenda, STICKY, CAPTION, 'MAS INFO',TIMEOUT,5000,HAUTO,VAUTO,WIDTH,350,BGCOLOR, '#DBDEEE', CAPCOLOR, '#FF0000');
    overlib(leyenda, STICKY, CAPTION, 'MAS INFO',TIMEOUT,5000,HAUTO,FIXY ,0 ,WIDTH,350,BGCOLOR, '#9999CC', CAPCOLOR, '#FF0000'); 
  }

  function getDetalle(identrega){

    abrirVentana('pedidos_Regalos_Entregas_Deta.jsp?idpedido_regalos_entrega_cabe='+identrega, 'detalle', 750, 350);

  }
  

function setEntregaEstado (idpedido_regalos_entrega_cabe, idestadoanterior){

   document.frm.idpedido_regalos_entrega_cabe.value = idpedido_regalos_entrega_cabe;
   document.frm.idestadoanterior.value = idestadoanterior;

}

 </script>
</head>
<%
// titulos para las columnas
tituCol[0] = "N°Ent.";
tituCol[1] = "Cód.Regalo";
tituCol[2] = "IdEstado";
tituCol[3] = "Estado";
tituCol[4] = "IdSucursal";
tituCol[5] = "Fecha";
tituCol[6] = "idsucuclie";
tituCol[7] = "idexpreso";
tituCol[8] = "Obs.armado";
tituCol[9] = "Obs.entrega";
tituCol[10] = "IdExpreso";
tituCol[11] = "IdZona";
tituCol[12] = "Calle";
tituCol[13] = "Nro";
tituCol[14] = "Piso";
tituCol[15] = "Depto";
tituCol[16] = "CPA";
tituCol[17] = "CP";
tituCol[18] = "Contacto";
tituCol[19] = "Cargo";
tituCol[20] = "Localidad";
tituCol[21] = "Provincia";
tituCol[22] = "TE";
tituCol[23] = "Celular";
tituCol[24] = "Fax";
tituCol[25] = "Web";





java.util.List Pedidos_Regalos_Entregas_Cabe = new java.util.ArrayList();
Pedidos_Regalos_Entregas_Cabe= BPRECA.getPedidos_Regalos_Entregas_CabeList();
iterPedidos_Regalos_Entregas_Cabe = Pedidos_Regalos_Entregas_Cabe.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="pedidosRegalosEntregasAdministrar.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim> 
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="39" colspan="2"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                   <td colspan="2"  class="text-globales"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                     <tr class="text-globales">
                       <td height="3" colspan="5"><hr color="#FFFFFF"> </td>
                     </tr>
                     <tr class="text-globales">
                       <td width="13%" height="19">Cliente:</td>
                       <td width="32%"><span class="fila-det-border"><%=BPRECA.getIdcliente() + " - " + BPRECA.getRazon()%></span></td>
                       <td width="11%" class="fila-det-border">Estado:</td>
                       <td width="20%" class="fila-det-border"><%=BPRECA.getEstadopedido_ppal() + "" %></td>
                       <td width="24%" class="fila-det-border"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                         <tr class="text-globales">
                             <td width="5%"><img src="../imagenes/default/gnome_tango/status/info.png" width="22" height="22" onMouseOver="callOverlib('<%=BPRECA.getObsentregapedido_ppal()%>')" style="cursor:pointer"></td>
                           <td width="91%"> Obs. Entrega</td>
                         </tr>
                         </table></td>
                     </tr>
                     <tr class="text-globales">
                       <td height="19">Fecha Pedido: </td>
                       <td><span class="fila-det-border"><%=BPRECA.getFechapedido_ppal() + ""%></span></td>
                       <td class="fila-det-border" >Prioridad:</td>
                       <td class="fila-det-border" ><%=BPRECA.getPrioridadpedido_ppal() + ""%>&nbsp;</td>
                       <td class="fila-det-border" ><table width="100%" border="0" cellspacing="0" cellpadding="0">
                         <tr class="text-globales">
                           <td width="5%"><img src="../imagenes/default/gnome_tango/status/info.png" width="22" height="22" onMouseOver="callOverlib('<%=BPRECA.getObsarmadopedido_ppal()%>')" style="cursor:pointer"></td>
                           <td width="88%">Obs. Armado </td>
                         </tr>
                       </table></td>
                     </tr>
                   </table></td>
                </tr>
                <tr>
                   <td width="11%" height="38">
                      <table width="36%" border="0">
                         <tr>
                            <td width="27%">
                               <input name="alta" id="alta" value="alta" type="image" src="../imagenes/default/btn_add_norm.gif"  onMouseOut="MM_swapImgRestore() " onMouseOver="MM_swapImage('alta','','../imagenes/default/btn_add_over.gif',1)" onClick="document.frm.accion.value = this.name">                            </td>
                            <td width="27%">
                               <%-- <input name="modificacion" id="modificacion" value="modificacion" type="image" src="../imagenes/default/btn_edit_norm.gif"  onMouseOut="MM_swapImgRestore() " onMouseOver="MM_swapImage('modificacion','','../imagenes/default/btn_edit_over.gif',1)" onClick="document.frm.accion.value = this.name"> --%>                            </td>
                            <td width="27%">
                                <input name="baja" id="baja" value="baja" type="image" src="../imagenes/default/btn_remove_norm.gif"  onMouseOut="MM_swapImgRestore() " onMouseOver="MM_swapImage('baja','','../imagenes/default/btn_remove_over.gif',1)" onClick="return confirmarBaja('frm');"> 
                                                           
                           </td>
                         </tr>
                      </table>                   </td>
                   <td width="89%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BPRECA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BPRECA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BPRECA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BPRECA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BPRECA.getTotalRegistros()%>">Todos</option>
                                             </select>                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BPRECA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BPRECA.getPaginaSeleccion() ){%>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BPRECA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="2%" >&nbsp;</td>
     <td width="2%" >&nbsp;</td>
     <td width="2%" >&nbsp;</td>
     <td width="2%" >&nbsp;</td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[0]%></div></td>
     <td width="8%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
     <td width="75%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">Direcci&oacute;n</td>
    </tr>
   <%int r = 0;
   Hashtable htRestringido = new Hashtable();
   htRestringido.put("1", "1");
   htRestringido.put("2", "2");
   htRestringido.put("4", "4");
   while(iterPedidos_Regalos_Entregas_Cabe.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterPedidos_Regalos_Entregas_Cabe.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";
      String masInfo = "";
      String paramJS= "";
      for(int m=0;m<tituCol.length; m++) {
        if(htRestringido.containsKey(m + "")) continue;
        masInfo += "<strong>" + tituCol[m] + ": </strong>" + Common.setNotNull(sCampos[m]) + "<br>";
      }
 
%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><input type="radio" name="idpedidoRegalosEntregaCabe" value="<%= sCampos[0]%>" <%= !sCampos[2].equalsIgnoreCase("1")? "disabled"  : ""%> onClick="setEntregaEstado(<%= sCampos[0]%>, <%= sCampos[2]%>)"></td>
      <td class="fila-det-border" >
        <img src="../imagenes/default/gnome_tango/apps/pdf.jpg" title="Reimprimir " width="18" height="18" onClick="abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=notas_pedido_regalos_entregas&idpedido_cabe=<%=sCampos[0]%>','entrega',750, 400)">
      </td>
      <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/status/dialog-information.png" width="18" height="18" onClick="callOverlib('<%=masInfo%>')"  title="Click para ver m&aacute;s info."></td>
      <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/mimetypes/gnome-mime-application-vnd.lotus-1-2-3.png" width="18" height="18" title="Ver Detalle" onClick="getDetalle(<%=sCampos[0]%>)" style="cursor:pointer"></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[0]%>&nbsp;</div></td> 
      <td class="fila-det-border" ><%=sCampos[3]%>&nbsp;</td>
      <td class="fila-det-border" ><%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[5]), "JSTsToStr")%>&nbsp;</td>
      <td class="fila-det-border" >[<%=tituCol[18]%> : <%=sCampos[18]%>] - [<%=tituCol[12]%> : <%=sCampos[12]%>] - [<%=tituCol[13]%> : <%=sCampos[13]%>] - [<%=tituCol[14]%> : <%=sCampos[14]%>] - [<%=tituCol[15]%> : <%=sCampos[15]%>] - [<%=tituCol[17]%> : <%=sCampos[17]%>]&nbsp;</td>
    </tr>
<%
   }%>
  </table>
   <input name="accion" value="" type="hidden">
   <input name="idpedido_regalos_cabe" type="hidden" id="idpedido_regalos_cabe" value="<%= BPRECA.getIdpedido_regalos_cabe() %>">
   <input name="idpedido_regalos_entrega_cabe" id="idpedido_regalos_entrega_cabe" value="" type="hidden">
   <input name="idestadoanterior" type="hidden" id="idestadoanterior" value="">
	  
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

