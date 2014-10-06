<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vClientesLCSASVentasDescuentoAplicadoTotal
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu May 03 14:13:53 ART 2012 
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
String titulo = "SAS - VENTAS DESCUENTOS APLICADOS";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVClientesLCSASVentasDescuentoAplicadoTotal   = null;
int totCol = 2; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVCLCSASVDATA"  class="ar.com.syswarp.web.ejb.BeanVClientesLCSASVentasDescuentoAplicadoTotalAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVCLCSASVDATA" property="*" />
<%
 BVCLCSASVDATA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BVCLCSASVDATA.setResponse(response);
 BVCLCSASVDATA.setRequest(request);
 BVCLCSASVDATA.ejecutarValidacion();
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
tituCol[0] = "Porcentaje %";
tituCol[1] = "Total Socios";
java.util.List VClientesLCSASVentasDescuentoAplicadoTotal = new java.util.ArrayList();
VClientesLCSASVentasDescuentoAplicadoTotal= BVCLCSASVDATA.getVClientesLCSASVentasDescuentoAplicadoTotalList();
iterVClientesLCSASVentasDescuentoAplicadoTotal = VClientesLCSASVentasDescuentoAplicadoTotal.iterator();
Iterator iter;
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="vClientesLCSASVentasDescuentoAplicadoTotalAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="40"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr height="3px" bgcolor="#FFFFFF">
                  <td  height="3px"> </td>
              </tr>
                <tr>
                  <td height="38"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="text-globales">
                      <td width="11%" height="33">Total de registros:</td>
                      <td width="19%">&nbsp;<%=BVCLCSASVDATA.getTotalRegistros() + ""%></td>
                      <td width="10%" >Visualizar:</td>
                      <td width="17%"><select name="limit" >
                          <%for(i=15; i<= 150 ; i+=15){%>
                          <%if(i==BVCLCSASVDATA.getLimit()){%>
                          <option value="<%=i%>" selected><%=i%></option>
                          <%}else{%>
                          <option value="<%=i%>"><%=i%></option>
                          <%}
                                                      if( i >= BVCLCSASVDATA.getTotalRegistros() ) break;
                                                    %>
                          <%}%>
                          <option value="<%= BVCLCSASVDATA.getTotalRegistros()%>">Todos</option>
                        </select>
                      </td>
                      <td width="10%">P&aacute;gina:</td>
                      <td width="21%"><select name="paginaSeleccion" >
                          <%for(i=1; i<= BVCLCSASVDATA.getTotalPaginas(); i++){%>
                          <%if ( i==BVCLCSASVDATA.getPaginaSeleccion() ){%>
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
									  iter = BVCLCSASVDATA.getListMeses().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
                                <option value="<%= datos[0] %>" <%= Integer.parseInt(datos[0] ) == ( BVCLCSASVDATA.getMes() ) ? "selected" : "" %>><%= datos[1] %></option>
                                <%  
									  }%>
                              </select>
                            </span></td>
                            <td width="66%"><span class="fila-det-border">
                              <select name="anio" id="anio" class="campo" style="width:75px" >
                                <option value="-1" selected>Sel.</option>
                                <% 
									      for(int y = BVCLCSASVDATA.getAnioActual() - 10; y< BVCLCSASVDATA.getAnioActual()+10; y++){%>
                                <option value="<%= y %>" <%= y ==  BVCLCSASVDATA.getAnio()  ? "selected" : "" %>><%= y %></option>
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
									  iter = BVCLCSASVDATA.getListTipoCliente().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
                          <option value="<%= datos[0] %>" <%= datos[0].equals( BVCLCSASVDATA.getIdtipoclie().toString() ) ? "selected" : "" %>><%= datos[1] %></option>
                          <%  
									  }%>
                        </select>
                      </span></td>
                      <td>Estado(*):</td>
                      <td><span class="fila-det-border">
                        <select name="idestado" id="idestado" class="campo" style="width:80%" >
                          <option value="-1" >Sel.</option>
                          <% 
									  iter = BVCLCSASVDATA.getListEstados().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();
										//if(datos[0].equals( "1" ))continue;
										%>
                          <option value="<%= datos[0] %>" <%= datos[0].equals( BVCLCSASVDATA.getIdestado().toString() ) ? "selected" : "" %>><%= datos[1] %></option>
                          <%  
									  }%>
                        </select>
                      </span></td>
                      <td class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                    </tr>
                  </table></td>
                </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVCLCSASVDATA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="32%" bgcolor="#CCCCFF" >&nbsp;</td>
     <td width="9%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[0]%></div></td>
     <td width="9%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[1]%></div></td>
     <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
     <td width="46%" bgcolor="#CCCCFF" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
  </tr>
   <%int r = 0;
   while(iterVClientesLCSASVentasDescuentoAplicadoTotal.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVClientesLCSASVentasDescuentoAplicadoTotal.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td width="32%" bgcolor="#CCCCFF" >&nbsp;</td> 
      <td class="fila-det-border" ><div align="right"><%=sCampos[0]%></div></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[1]%></div></td>
      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/actions/edit-find-replace.png" width="18" height="18" onClick="abrirVentana('vClientesLCSASVentasDescuentoAplicadoDetaAbm.jsp?idestado=<%=BVCLCSASVDATA.getIdestado()%>&idtipoclie=<%=BVCLCSASVDATA.getIdtipoclie()%>&anio=<%=BVCLCSASVDATA.getAnio()%>&mes=<%=BVCLCSASVDATA.getMes()%>&porcdesc_apli=<%=sCampos[0]%>&soloLectura=true', 'ventas', 800, 400 );" title="Ver detalle" style="cursor:pointer"></div></td>
      <td width="46%" bgcolor="#CCCCFF" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
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

