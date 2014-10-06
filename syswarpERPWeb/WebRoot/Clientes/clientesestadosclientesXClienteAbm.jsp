<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: clientesestadosclientes
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Mar 02 14:15:18 GMT-03:00 2007 
   Observaciones: 
      .


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.Iterator" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%@ page import="java.math.BigDecimal" %>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
//String titulo = "Estados Clientes";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterClientesestadosclientes   = null;
int totCol = 9; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BCA"  class="ar.com.syswarp.web.ejb.BeanClientesestadosclientesXClienteAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BCA" property="*" />
<%

 String titulo = "Detalle : " + BCA.getRazon() ; 

 BCA.setResponse(response);
 BCA.setRequest(request);
 BCA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BCA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
  <link rel="stylesheet" href="../imagenes/default/erp-style.css">
</head>
<%
// titulos para las columnas
tituCol[0] = "Cód.";
tituCol[1] = "C.Clie";
tituCol[2] = "Cliente";
tituCol[3] = "Estado";
tituCol[4] = "Motivo";
tituCol[5] = "F.Desde";
//tituCol[6] = "F.Hasta";
tituCol[6] = "F.Baja";
tituCol[7] = "OBS.";

java.util.List Clientesestadosclientes = new java.util.ArrayList();
Clientesestadosclientes= BCA.getClientesestadosclientesList();
iterClientesestadosclientes = Clientesestadosclientes.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<DIV ID="overDiv" STYLE="position:absolute; visibility:hide; z-index:1;"></DIV>

<form action="clientesestadosclientesXClienteAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td colspan="2"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                   <td width="8%" height="38">
                      <table width="36%" border="0">
                         <tr>
                            <td width="27%">
                               <input name="alta" id="alta" value="alta" type="image" src="../imagenes/default/btn_add_norm.gif"  onMouseOut="MM_swapImgRestore() " onMouseOver="MM_swapImage('alta','','../imagenes/default/btn_add_over.gif',1)" onClick="document.frm.accion.value = this.name" height="20" width="20"> </td>
                            <td width="27%"><input name="baja" id="baja" value="baja" type="image" src="../imagenes/default/gnome_tango/status/image-missing.png"  onMouseOut="MM_swapImgRestore() " onMouseOver="MM_swapImage('baja','','../imagenes/default/gnome_tango/status/image-missing.png',1)" onClick="return confirmarBaja('frm');" height="20" width="20"></td>
                            <td width="27%"><img src="../imagenes/default/gnome_tango/actions/fileopen.png" title="Consulta Cuenta Corriente"  title="Consulta Cuenta Corriente" width="18" height="18" onClick="ventana=abrirVentana('vclientesCtaCtesAbm.jsp?cliente=<%= BCA.getRazon() %>&amp;idcliente=<%= BCA.getIdcliente() %>', 'ctacte', 800, 400)" style="cursor:pointer" /></td>
                            <td width="27%"><img src="../imagenes/default/gnome_tango/apps/gnome-session.png" title="Hist&oacute;rico de pedidos"  title="Hist&oacute;rico de pedidos" width="18" height="18" onClick="ventana=abrirVentana('pedidosHistoricoCliente.jsp?cliente=<%= BCA.getRazon() %>&amp;idcliente=<%= BCA.getIdcliente() %>', 'hist', 800, 400)" style="cursor:pointer" /></td>
                            <td width="27%">&nbsp;</td>
                         </tr>
                      </table>                    </td>
                   <td width="92%">
                      <table width="100%" border="0"> 
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BCA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BCA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BCA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BCA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BCA.getTotalRegistros()%>">Todos</option>
                                             </select>                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BCA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BCA.getPaginaSeleccion() ){%>
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
                <tr >
                  <td colspan="2" class="text-globales"><%=  BCA.isExistePedidoPentiente() ? "<font color=\"#FFFF99\">ATENCION: Este cliente tiene pedidos pendientes.</font>" : "" %></td>
                </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BCA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="3%" >&nbsp;</td>
     <td width="30%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%> </td>
     <td width="33%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[5]%></div></td>
<%--      <td width="8%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[6]%></div></td>
 --%>
     <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[6]%></div></td>
     <td width="6%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"> </td>
     <td width="7%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
   </tr>
   <%
   int r = 0;
   String cclie = "";
   String dclie = "";
   String auxColorFondo = "";
   while(iterClientesestadosclientes.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterClientesestadosclientes.next(); 
  
	  if(!cclie.equals(sCampos[1]) || cclie.equals("")){
	      cclie = sCampos[1]; 

      // estos campos hay que setearlos segun la grilla 
	    
        if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
        else color_fondo = "fila-det-verde";
		auxColorFondo = color_fondo;
     %>
   <tr class="text-dos-bold"   > 
      <td >&nbsp;</td>
      <td colspan="7" ><%= sCampos[1] %> | <%= sCampos[2] %>&nbsp; </td>
    </tr>
	 <%
	  }
	  if (!str.esNulo(sCampos[6]).equals("")){ 
	    auxColorFondo = color_fondo;
	    color_fondo = "permiso-tres";
	  }
	  
	  %>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><table width="100%" border="0" cellspacing="1" cellpadding="0">
          <tr>
            <td width="49%"><div align="center"><%= BCA.getIdestadoclienteactual().longValue() == Long.parseLong(sCampos[0]) ? "<img src=\"../imagenes/default/gnome_tango/actions/gtk-media-record.png\" width=\"10\" height=\"10\">" : ""%></div></td>
            <td width="51%"><input type="radio" name="idestadocliente" value="<%= sCampos[0]%>" <%= !str.esNulo(sCampos[6]).equals("") ? "disabled" :  "" %> ></td>
          </tr>
        </table></td>
      <td class="fila-det-border" ><%=sCampos[3]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[4]%>&nbsp;</td>
      <td class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[5]),  "JSDateToStr")%>&nbsp;</div></td>
<%--       <td class="fila-det-border" ><div align="center"><%= str.esNulo(sCampos[6]).equals("") ? "" : Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[6]),  "JSDateToStr")%>&nbsp;</div></td>
 --%>
      <td class="fila-det-border" ><div align="center"><%= str.esNulo(sCampos[6]).equals("") ? "" : Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[6]),  "JSTsToStr")%>&nbsp;</div></td>
      <td class="fila-det-border" >
	    <div align="center">
	      <a href="javascript:void(0);" onMouseOver="return overlib('<%= str.esNulo(sCampos[7]) %>', STICKY, CAPTION, 'OBSERVACIONES',  LEFT, OFFSETX, 0, OFFSETY, 0, DELAY, 5 )" onMouseOut="nd();">
		  <img src="../imagenes/default/gnome_tango/status/dialog-information.png" width="18" height="18" style="cursor:pointer" border="0">		  </a>		</div>	  </td> 
      <td class="fila-det-border" > 
	    <div align="center">
	      <a href="javascript:void(0);" onMouseOver="return overlib('<%="<strong>Usuario Alta:</strong> " + str.esNulo(sCampos[8]) + "<br><strong>Usuario Modificación:</strong> " + str.esNulo(sCampos[9]) + "<br><strong>Fecha Alta:</strong>" + Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(str.esNulo(sCampos[10])), "JSTsToStrWithHM" )  + "<br><strong>Fecha Modificación:</strong> " + (!str.esNulo(sCampos[11]).equals("")  ?  Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(str.esNulo(sCampos[11])), "JSTsToStrWithHM" )   : ""   )%>  ', STICKY, CAPTION, 'AUDITORIA',  LEFT, OFFSETX, 0, OFFSETY, 0, DELAY, 5 )" onMouseOut="nd();">
		  <img src="../imagenes/default/gnome_tango/apps/config-users.png" width="22" height="22" style="cursor:pointer" border="0">		  </a>		</div>	  </td> 
   </tr>
<%
     color_fondo = auxColorFondo;
   }%>
   </table>
   <input name="accion" value="" type="hidden">
   <input name="idcliente" value="<%= BCA.getIdcliente() %>" type="hidden">
   <input name="razon" value="<%=BCA.getRazon() %>" type="hidden">
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

