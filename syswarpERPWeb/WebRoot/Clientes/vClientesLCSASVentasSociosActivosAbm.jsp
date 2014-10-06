<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vClientesLCSASVentasSociosActivos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed May 09 09:43:07 ART 2012 
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
String titulo = "SAS - VENTAS SOCIOS ACTIVOS";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVClientesLCSASVentasSociosActivos   = null;
Iterator iter;
int totCol = 13; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVCLCSASVSAA"  class="ar.com.syswarp.web.ejb.BeanVClientesLCSASVentasSociosActivosAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVCLCSASVSAA" property="*" />
<%
 BVCLCSASVSAA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BVCLCSASVSAA.setResponse(response);
 BVCLCSASVSAA.setRequest(request);
 BVCLCSASVSAA.ejecutarValidacion();
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
tituCol[1] = "Razón";
tituCol[2] = "Pediod";
tituCol[3] = "Año";
tituCol[4] = "Mes";
tituCol[5] = "idtipoclie";
tituCol[6] = "idestado";
tituCol[7] = "Estado";
tituCol[8] = "Fecha";
tituCol[9] = "idmotivo";
tituCol[10] = "Motivo";
tituCol[11] = "idestadopedido";
tituCol[12] = "idempresa";
java.util.List VClientesLCSASVentasSociosActivos = new java.util.ArrayList();
VClientesLCSASVentasSociosActivos= BVCLCSASVSAA.getVClientesLCSASVentasSociosActivosList();
iterVClientesLCSASVentasSociosActivos = VClientesLCSASVentasSociosActivos.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="vClientesLCSASVentasSociosActivosAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="40"  class="text-globales"><%=titulo%></td>
      </tr>
      <tr height="3px" bgcolor="#FFFFFF">
        <td  height="3px"></td>
      </tr>
      <tr>
        <td height="38"><table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr class="text-globales">
              <td width="11%" height="33">Total de registros:</td>
              <td width="19%">&nbsp;<%=BVCLCSASVSAA.getTotalRegistros() + ""%></td>
              <td width="10%" >Visualizar:</td>
              <td width="17%"><select name="limit" >
                  <%for(i=15; i<= 150 ; i+=15){%>
                  <%if(i==BVCLCSASVSAA.getLimit()){%>
                  <option value="<%=i%>" selected><%=i%></option>
                  <%}else{%>
                  <option value="<%=i%>"><%=i%></option>
                  <%}
                                                      if( i >= BVCLCSASVSAA.getTotalRegistros() ) break;
                                                    %>
                  <%}%>
                  <option value="<%= BVCLCSASVSAA.getTotalRegistros()%>">Todos</option>
                </select>
              </td>
              <td width="10%">P&aacute;gina:</td>
              <td width="21%"><select name="paginaSeleccion" >
                  <%for(i=1; i<= BVCLCSASVSAA.getTotalPaginas(); i++){%>
                  <%if ( i==BVCLCSASVSAA.getPaginaSeleccion() ){%>
                  <option value="<%=i%>" selected><%=i%></option>
                  <%}else{%>
                  <option value="<%=i%>"><%=i%></option>
                  <%}%>
                  <%}%>
                </select>
              </td>
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
									  iter = BVCLCSASVSAA.getListMeses().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
                        <option value="<%= datos[0] %>" <%= Integer.parseInt(datos[0] ) == ( BVCLCSASVSAA.getMes() ) ? "selected" : "" %>><%= datos[1] %></option>
                        <%  
									  }%>
                      </select>
                    </span></td>
                    <td width="66%"><span class="fila-det-border">
                      <select name="anio" id="anio" class="campo" style="width:75px" >
                        <option value="-1" selected>Sel.</option>
                        <% 
									      for(int y = BVCLCSASVSAA.getAnioActual() - 10; y< BVCLCSASVSAA.getAnioActual()+10; y++){%>
                        <option value="<%= y %>" <%= y ==  BVCLCSASVSAA.getAnio()  ? "selected" : "" %>><%= y %></option>
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
									  iter = BVCLCSASVSAA.getListTipoCliente().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
                  <option value="<%= datos[0] %>" <%= datos[0].equals( BVCLCSASVSAA.getIdtipoclie().toString() ) ? "selected" : "" %>><%= datos[1] %></option>
                  <%  
									  }%>
                </select>
              </span></td>
              <td>Estado(*):</td>
              <td><span class="fila-det-border">
                <select name="idestado" id="idestado" class="campo" style="width:80%" >
                  <option value="-1" >Sel.</option>
                  <% 
									  iter = BVCLCSASVSAA.getListEstados().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();
										//if(datos[0].equals( "1" ))continue;
										%>
                  <option value="<%= datos[0] %>" <%= datos[0].equals( BVCLCSASVSAA.getIdestado().toString() ) ? "selected" : "" %>><%= datos[1] %></option>
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
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVCLCSASVSAA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="2%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[0]%></div></td>
     <td width="21%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="16%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[7]%></td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[8]%></div></td>
     <td width="51%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[10]%></td>
     <td width="1%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
     <td width="2%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
     <td width="2%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
  </tr>
   <%int r = 0;
   while(iterVClientesLCSASVentasSociosActivos.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVClientesLCSASVentasSociosActivos.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><div align="right"><%=sCampos[0]%></div></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[1]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[7]%></td>
      <td class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[8]), "JSDateToStr")%></div></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[10]%></td>
      <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/apps/date.png" width="20" height="20" onClick="abrirVentana('../Clientes/clientesPeriodicidadEntregaFrm.jsp?idcliente=<%=sCampos[0]%>&accion=consulta', 'periodicidad', 800, 450)" style="cursor:pointer"></td>
      <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/status/gtk-missing-image.png" title="Suspensi&oacute;n de Entregas Regulares" width="20" height="20" onClick="abrirVentana('Suspensionentregaregular.jsp?cliente=<%= sCampos[1] %>&idcliente=<%= sCampos[0] %>', 'suspension', 800, 400)" style="cursor:pointer"></td>
      <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/apps/gnome-session.png" title="Hist&oacute;rico de pedidos" width="20" height="20" onClick="abrirVentana('pedidosHistoricoCliente.jsp?cliente=<%= sCampos[1] %>&idcliente=<%= sCampos[0] %>', 'hist', 800, 400)" style="cursor:pointer"></td>
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

