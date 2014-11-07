<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vClientesLCSASVentasSuspensionEr
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue May 08 10:04:49 ART 2012 
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
String titulo = "SAS - VENTAS SUSPENSION ENTEREGA REGULAR";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVClientesLCSASVentasSuspensionEr   = null;
Iterator iter;
int totCol = 9; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVCLCSASVSEA"  class="ar.com.syswarp.web.ejb.BeanVClientesLCSASVentasSuspensionErAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVCLCSASVSEA" property="*" />
<%
 BVCLCSASVSEA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BVCLCSASVSEA.setResponse(response);
 BVCLCSASVSEA.setRequest(request);
 BVCLCSASVSEA.ejecutarValidacion();
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
tituCol[2] = "Pedido";
tituCol[3] = "idmotsusp";
tituCol[4] = "Motivo";
tituCol[5] = "anio";
tituCol[6] = "mes";
tituCol[7] = "idtipoclie";
tituCol[8] = "idempresa";
java.util.List VClientesLCSASVentasSuspensionEr = new java.util.ArrayList();
VClientesLCSASVentasSuspensionEr= BVCLCSASVSEA.getVClientesLCSASVentasSuspensionErList();
iterVClientesLCSASVentasSuspensionEr = VClientesLCSASVentasSuspensionEr.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="vClientesLCSASVentasSuspensionErAbm.jsp" method="POST" name="frm">
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
              <td width="19%">&nbsp;<%=BVCLCSASVSEA.getTotalRegistros() + ""%></td>
              <td width="10%" >Visualizar:</td>
              <td width="17%"><select name="limit" >
                  <%for(i=15; i<= 150 ; i+=15){%>
                  <%if(i==BVCLCSASVSEA.getLimit()){%>
                  <option value="<%=i%>" selected><%=i%></option>
                  <%}else{%>
                  <option value="<%=i%>"><%=i%></option>
                  <%}
                                                      if( i >= BVCLCSASVSEA.getTotalRegistros() ) break;
                                                    %>
                  <%}%>
                  <option value="<%= BVCLCSASVSEA.getTotalRegistros()%>">Todos</option>
                </select>              </td>
              <td width="10%">P&aacute;gina:</td>
              <td width="21%"><select name="paginaSeleccion" >
                  <%for(i=1; i<= BVCLCSASVSEA.getTotalPaginas(); i++){%>
                  <%if ( i==BVCLCSASVSEA.getPaginaSeleccion() ){%>
                  <option value="<%=i%>" selected><%=i%></option>
                  <%}else{%>
                  <option value="<%=i%>"><%=i%></option>
                  <%}%>
                  <%}%>
                </select>              </td>
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
									  iter = BVCLCSASVSEA.getListMeses().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
                        <option value="<%= datos[0] %>" <%= Integer.parseInt(datos[0] ) == ( BVCLCSASVSEA.getMes() ) ? "selected" : "" %>><%= datos[1] %></option>
                        <%  
									  }%>
                      </select>
                    </span></td>
                    <td width="66%"><span class="fila-det-border">
                      <select name="anio" id="anio" class="campo" style="width:75px" >
                        <option value="-1" selected>Sel.</option>
                        <% 
									      for(int y = BVCLCSASVSEA.getAnioActual() - 10; y< BVCLCSASVSEA.getAnioActual()+10; y++){%>
                        <option value="<%= y %>" <%= y ==  BVCLCSASVSEA.getAnio()  ? "selected" : "" %>><%= y %></option>
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
									  iter = BVCLCSASVSEA.getListTipoCliente().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
                  <option value="<%= datos[0] %>" <%= datos[0].equals( BVCLCSASVSEA.getIdtipoclie().toString() ) ? "selected" : "" %>><%= datos[1] %></option>
                  <%  
									  }%>
                </select>
              </span></td>
              <td>&nbsp;</td>
              <td><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
              <td class="text-globales">&nbsp;</td>
            </tr>
        </table></td>
      </tr>
    </table></td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVCLCSASVSEA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="9%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[0]%></div></td>
     <td width="55%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="34%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
     <td width="2%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[2]%></div></td>
    </tr>
   <%int r = 0;
   while(iterVClientesLCSASVentasSuspensionEr.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVClientesLCSASVentasSuspensionEr.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><div align="right"><%=sCampos[0]%></div></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[1]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[4]%></td>
      <td class="fila-det-border" ><%= Common.setNotNull(sCampos[2]).equals("") ? "&nbsp;" : ("<div align=\"right\"><a href=\"javascript: abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=notas_pedido&idpedido_cabe=" + sCampos[2] + "','pedido',750, 400)\">" + sCampos[2] + "</a></div>") %></td>
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

