<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vClientesLCSASVentasRevisar
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Apr 26 13:48:48 ART 2012 
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
Iterator iterVClientesLCSASVentasRevisar   = null;
int totCol = 16; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVCLCSASVRA"  class="ar.com.syswarp.web.ejb.BeanVClientesLCSASVentasRevisarAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVCLCSASVRA" property="*" />
<%
 String titulo = "SAS - VENTAS A REVISAR" + (  BVCLCSASVRA.getIdcliente().longValue() > 0  ? ( ": " + BVCLCSASVRA.getIdcliente() + " - " +  BVCLCSASVRA.getCliente() ) : ""  );
 BVCLCSASVRA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BVCLCSASVRA.setResponse(response);
 BVCLCSASVRA.setRequest(request);
 BVCLCSASVRA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>style.css">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="<%=pathskin%>/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script>
 

 
 
 window.onload = function(){
 
   if(document.frm.idcliente.value != "-1"){ 
 
     document.frm.mes.onfocus =  function(){ document.frm.mes.blur(); }; 
     document.frm.anio.onfocus =  function(){ document.frm.anio.blur(); }; 
     document.frm.idestado.onfocus =  function(){ document.frm.idestado.blur(); }; 
     document.frm.idtipoclie.onfocus =  function(){ document.frm.idtipoclie.blur(); }; 

   }
   
 }
 
 
 </script>
 
</head>
<%
// titulos para las columnas
tituCol[0] = "Cód.";
tituCol[1] = "Razón";
tituCol[2] = "idestado";
tituCol[3] = "Actual";
tituCol[4] = "Fecha";
tituCol[5] = "idmotivo";
tituCol[6] = "Motivo";
tituCol[7] = "idcategoriasocio";
tituCol[8] = "Categoria";
tituCol[9] = "Fecha";
tituCol[10] = "Origen";
tituCol[11] = "Nro.º";
tituCol[12] = "Obs.Armado";
tituCol[13] = "obs.Entrega";
tituCol[14] = "idtipoclie";
tituCol[15] = "U.Alta";
java.util.List VClientesLCSASVentasRevisar = new java.util.ArrayList();
VClientesLCSASVentasRevisar= BVCLCSASVRA.getVClientesLCSASVentasRevisarList();
iterVClientesLCSASVentasRevisar = VClientesLCSASVentasRevisar.iterator();
Iterator iter;
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="vClientesLCSASVentasRevisarAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="40"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="11%" height="38"><table width="100%" border="0">
                    <tr>
                      <td width="72%" height="71"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr class="text-globales">
                                  <td width="11%" height="33">Total de registros:</td>
                                  <td width="19%">&nbsp;<%=BVCLCSASVRA.getTotalRegistros() + ""%></td>
                                  <td width="10%" >Visualizar:</td>
                                  <td width="17%"><select name="limit" >
                                      <%for(i=15; i<= 150 ; i+=15){%>
                                      <%if(i==BVCLCSASVRA.getLimit()){%>
                                      <option value="<%=i%>" selected><%=i%></option>
                                      <%}else{%>
                                      <option value="<%=i%>"><%=i%></option>
                                      <%}
                                                      if( i >= BVCLCSASVRA.getTotalRegistros() ) break;
                                                    %>
                                      <%}%>
                                      <option value="<%= BVCLCSASVRA.getTotalRegistros()%>">Todos</option>
                                    </select>                                  </td>
                                  <td width="10%">P&aacute;gina:</td>
                                  <td width="21%"><select name="paginaSeleccion" >
                                      <%for(i=1; i<= BVCLCSASVRA.getTotalPaginas(); i++){%>
                                      <%if ( i==BVCLCSASVRA.getPaginaSeleccion() ){%>
                                      <option value="<%=i%>" selected><%=i%></option>
                                      <%}else{%>
                                      <option value="<%=i%>"><%=i%></option>
                                      <%}%>
                                      <%}%>
                                    </select>                                  </td>
                                  <td width="12%" class="text-globales">&nbsp;</td>
                                </tr>
                                <tr class="text-globales">
                                  <td height="32">Periodo(*):</td>
                                  <td><table width="66%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                      <td width="34%"><span class="fila-det-border">
                                        <select name="mes" id="mes" class="campo" style="width:75px" >
                                          <option value="-1" selected>Sel.</option>
                                          <% 
									  iter = BVCLCSASVRA.getListMeses().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
                                          <option value="<%= datos[0] %>" <%= Integer.parseInt(datos[0] ) == ( BVCLCSASVRA.getMes() ) ? "selected" : "" %>><%= datos[1] %></option>
                                          <%  
									  }%>
                                        </select>
                                      </span></td>
                                      <td width="66%"><span class="fila-det-border">
                                        <select name="anio" id="anio" class="campo" style="width:75px" >
                                          <option value="-1" selected>Sel.</option>
                                          <% 
									      for(int y = BVCLCSASVRA.getAnioActual() - 10; y< BVCLCSASVRA.getAnioActual()+10; y++){%>
                                          <option value="<%= y %>" <%= y ==  BVCLCSASVRA.getAnio()  ? "selected" : "" %>><%= y %></option>
                                          <%  
									      }%>
                                        </select>
                                      </span></td>
                                    </tr>
                                  </table></td>
                                  <td >Tipo Cliente(*):</td>
                                  <td><span class="fila-det-border">
                                    <select name="idtipoclie" id="idtipoclie" class="campo" style="width:80%" >
                                      <option value="-1" selected>Sel.</option>
                                      <% 
									  iter = BVCLCSASVRA.getListTipoCliente().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
                                      <option value="<%= datos[0] %>" <%= datos[0].equals( BVCLCSASVRA.getIdtipoclie().toString() ) ? "selected" : "" %>><%= datos[1] %></option>
                                      <%  
									  }%>
                                    </select>
                                  </span></td>
                                  <td>Estado(*):</td>
                                  <td><span class="fila-det-border">
                                    <select name="idestado" id="idestado" class="campo" style="width:80%" >
                                      <option value="-1" >Sel.</option>
                                      <% 
									  iter = BVCLCSASVRA.getListEstados().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();
										if( datos[0].equals( "1" ) && BVCLCSASVRA.getIdcliente().longValue() < 1 )continue;
										%>
                                      <option value="<%= datos[0] %>" <%= datos[0].equals( BVCLCSASVRA.getIdestado().toString() ) ? "selected" : "" %>><%= datos[1] %></option>
                                      <%  
									  }%>
                                    </select>
                                  </span></td>
                                  <td class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                                </tr>
                            </table></td>
                          </tr>
                      </table></td>
                    </tr>
                  </table></td>
              </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVCLCSASVRA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="text-dos-bold">
    <td height="27" colspan="3" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center">Cliente</div></td>
    <td colspan="3" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center">Estado</div></td>
    <td colspan="4" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center">Pedido</div>      <div align="center"></div></td>
    </tr>
  <tr class="text-dos">
    <td width="2%" height="30" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[0]%></div></td>
     <td width="18%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="21%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[8]%></td>
     <td width="8%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="6%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[4]%></div></td>
     <td width="22%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[6]%></td>
     <td width="8%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[15]%></td>
     <td width="7%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[10]%></td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[11]%></div></td>
     <td width="3%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"></div></td>
  </tr>
   <%int r = 0;
   while(iterVClientesLCSASVentasRevisar.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVClientesLCSASVentasRevisar.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td bgcolor="#EDDBFF" class="fila-det-border" ><div align="right"><%=sCampos[0]%></div></td> 
      <td bgcolor="#EDDBFF" class="fila-det-border" >&nbsp;<%=sCampos[1]%></td>
      <td bgcolor="#EDDBFF" class="fila-det-border" >&nbsp;<%=sCampos[8]%></td>
      <td bgcolor="#FFDDBB" class="fila-det-border" >&nbsp;<%=sCampos[3]%></td>
      <td bgcolor="#FFDDBB" class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[4]), "JSDateToStr")%></div></td>
      <td bgcolor="#FFDDBB" class="fila-det-border" >&nbsp;<%=sCampos[6]%></td>
      <td bgcolor="#DBE1F5" class="fila-det-border" >&nbsp;<%=sCampos[15]%></td>
      <td bgcolor="#DBE1F5" class="fila-det-border" >&nbsp;<%=sCampos[10]%></td>
      <td bgcolor="#DBE1F5" class="fila-det-border" ><div align="right"><a href="javascript: abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=notas_pedido&idpedido_cabe=<%=sCampos[11]%>','pedido',750, 400)"><%=sCampos[11]%></a></div></td>
      <td bgcolor="#DBE1F5" class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/actions/gtk-refresh.png" width="22" height="22" style="cursor:pointer" title="Actualizar Estado del Pedido N&uacute;mero: <%=sCampos[0]%>" onClick="abrirVentana('pedidosCambioEstadoFrm.jsp?idpedido=<%=sCampos[11]%>&tipopedido=N&accion=cambiarestado','pedido',750, 400)"></div></td>
   </tr>
<%
   }%>
  </table>
   <input name="accion" value="" type="hidden">
   <input name="idcliente" type="hidden" id="idcliente" value="<%= BVCLCSASVRA.getIdcliente() %>">
   <input name="cliente" type="hidden" id="cliente" value="<%= BVCLCSASVRA.getCliente() %>">
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

