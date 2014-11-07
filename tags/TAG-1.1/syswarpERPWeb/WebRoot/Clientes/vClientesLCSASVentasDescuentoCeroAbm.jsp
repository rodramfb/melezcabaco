<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vClientesLCSASVentasDescuentoCero
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu May 03 09:14:32 ART 2012 
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
String titulo = "SAS - VENTAS SIN DESCUENTO ";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVClientesLCSASVentasDescuentoCero   = null;
int totCol = 5; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVCLCSASVDCA"  class="ar.com.syswarp.web.ejb.BeanVClientesLCSASVentasDescuentoCeroAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVCLCSASVDCA" property="*" />
<%
 BVCLCSASVDCA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BVCLCSASVDCA.setResponse(response);
 BVCLCSASVDCA.setRequest(request);
 BVCLCSASVDCA.ejecutarValidacion();
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
tituCol[2] = "Unidades";
tituCol[3] = "idestado";
tituCol[4] = "Estado";
java.util.List VClientesLCSASVentasDescuentoCero = new java.util.ArrayList();
VClientesLCSASVentasDescuentoCero= BVCLCSASVDCA.getVClientesLCSASVentasDescuentoCeroList();
iterVClientesLCSASVentasDescuentoCero = VClientesLCSASVentasDescuentoCero.iterator();
Iterator iter;
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="vClientesLCSASVentasDescuentoCeroAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="40"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr height="3px" >
                   <td height="3px" bgcolor="#FFFFFF"></td>
                </tr>				
                <tr>
                  <td width="11%" height="38"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="text-globales">
                      <td width="11%" height="33">Total de registros:</td>
                      <td width="19%">&nbsp;<%=BVCLCSASVDCA.getTotalRegistros() + ""%></td>
                      <td width="10%" >Visualizar:</td>
                      <td width="17%"><select name="limit" >
                          <%for(i=15; i<= 150 ; i+=15){%>
                          <%if(i==BVCLCSASVDCA.getLimit()){%>
                          <option value="<%=i%>" selected><%=i%></option>
                          <%}else{%>
                          <option value="<%=i%>"><%=i%></option>
                          <%}
                                                      if( i >= BVCLCSASVDCA.getTotalRegistros() ) break;
                                                    %>
                          <%}%>
                          <option value="<%= BVCLCSASVDCA.getTotalRegistros()%>">Todos</option>
                        </select>
                      </td>
                      <td width="10%">P&aacute;gina:</td>
                      <td width="21%"><select name="paginaSeleccion" >
                          <%for(i=1; i<= BVCLCSASVDCA.getTotalPaginas(); i++){%>
                          <%if ( i==BVCLCSASVDCA.getPaginaSeleccion() ){%>
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
									  iter = BVCLCSASVDCA.getListMeses().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
                                <option value="<%= datos[0] %>" <%= Integer.parseInt(datos[0] ) == ( BVCLCSASVDCA.getMes() ) ? "selected" : "" %>><%= datos[1] %></option>
                                <%  
									  }%>
                              </select>
                            </span></td>
                            <td width="66%"><span class="fila-det-border">
                              <select name="anio" id="anio" class="campo" style="width:75px" >
                                <option value="-1" selected>Sel.</option>
                                <% 
									      for(int y = BVCLCSASVDCA.getAnioActual() - 10; y< BVCLCSASVDCA.getAnioActual()+10; y++){%>
                                <option value="<%= y %>" <%= y ==  BVCLCSASVDCA.getAnio()  ? "selected" : "" %>><%= y %></option>
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
									  iter = BVCLCSASVDCA.getListTipoCliente().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
                          <option value="<%= datos[0] %>" <%= datos[0].equals( BVCLCSASVDCA.getIdtipoclie().toString() ) ? "selected" : "" %>><%= datos[1] %></option>
                          <%  
									  }%>
                        </select>
                      </span></td>
                      <td>Estado(*):</td>
                      <td><span class="fila-det-border">
                        <select name="idestado" id="idestado" class="campo" style="width:80%" >
                          <option value="-1" >Sel.</option>
                          <% 
									  iter = BVCLCSASVDCA.getListEstados().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();
										//if(datos[0].equals( "1" ))continue;
										%>
                          <option value="<%= datos[0] %>" <%= datos[0].equals( BVCLCSASVDCA.getIdestado().toString() ) ? "selected" : "" %>><%= datos[1] %></option>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVCLCSASVDCA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[0]%></div></td>
     <td width="56%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[2]%></div></td>
     <td width="25%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
     <td width="3%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
  </tr>
   <%int r = 0;
   while(iterVClientesLCSASVentasDescuentoCero.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVClientesLCSASVentasDescuentoCero.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><div align="right"><%=sCampos[0]%></div></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[1]%></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[2]%></div></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[4]%></td>
      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/actions/edit-find-replace.png" width="18" height="18" onClick="abrirVentana('vClientesLCSASVentasRevisarAbm.jsp?idcliente=<%=sCampos[0]%>&cliente=<%=sCampos[1]%>&idestado=<%=BVCLCSASVDCA.getIdestado()%>&idtipoclie=<%=BVCLCSASVDCA.getIdtipoclie()%>&anio=<%=BVCLCSASVDCA.getAnio()%>&mes=<%=BVCLCSASVDCA.getMes()%>', 'ventas', 800, 400 );" title="Ver detalle" style="cursor:pointer"></div></td>
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

