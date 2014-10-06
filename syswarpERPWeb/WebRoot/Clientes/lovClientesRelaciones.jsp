<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: clientesRelaciones
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Feb 12 16:17:06 GMT-03:00 2010 
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
String titulo = "CLIENTES RELACIONADOS";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterClientesRelaciones   = null;
int totCol = 6; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BCRA"  class="ar.com.syswarp.web.ejb.BeanClientesRelacionesAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BCRA" property="*" />
<%
 BCRA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BCRA.setResponse(response);
 BCRA.setRequest(request);
 BCRA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <%--<link rel="stylesheet" href="<%=pathskin%>style.css"> --%>
 <link rel="stylesheet" href="../imagenes/default/erp-style.css"> 
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="<%=pathskin%>/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script>

  function callOpenerBajarDatos(idclienterelacionar, razonrelacionar){

    opener.bajarDatos(idclienterelacionar, razonrelacionar);
    window.close();

  }

 </script>
</head>
<%
// titulos para las columnas
/*
idrelacion,idclienteroot, ro.razon AS razonroot, idclientebranch, br.razon AS razonbranch, 1 as tipo
*/
tituCol[0] = "Cód.";
tituCol[1] = "idclienteRoot";
tituCol[2] = "Razón Principal";
tituCol[3] = "IdClienteBranch";
tituCol[4] = "Razón Asociada";
tituCol[5] = "Tipo";
java.util.List ClientesRelaciones = new java.util.ArrayList();
ClientesRelaciones= BCRA.getClientesRelacionesList();
iterClientesRelaciones = ClientesRelaciones.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="lovClientesRelaciones.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="30" colspan="2"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="2%" height="38">&nbsp;</td>
                   <td width="98%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BCRA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BCRA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BCRA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BCRA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BCRA.getTotalRegistros()%>">Todos</option>
                                             </select>                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BCRA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BCRA.getPaginaSeleccion() ){%>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BCRA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="2%" >&nbsp;</td>
     <td width="2%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="46%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="46%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
     <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
  </tr>
   <%int r = 0;
   while(iterClientesRelaciones.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterClientesRelaciones.next(); 
      String imagen = sCampos[5].equalsIgnoreCase("1") ? "../imagenes/default/gnome_tango/colors.green.icon.gif" : "../imagenes/default/gnome_tango/colors.red.icon.gif";
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";
      String idclienterelacionar = "";
      String razonrelacionar = "";
      if(!sCampos[1].equals(BCRA.getIdcliente().toString())){
        idclienterelacionar = sCampos[1];
        razonrelacionar = sCampos[2];
      }
      else{
        idclienterelacionar = sCampos[3];
        razonrelacionar = sCampos[4];
      }

     %>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/actions/down.png" width="18" height="18" onClick="callOpenerBajarDatos(<%= idclienterelacionar %>, '<%= razonrelacionar %>')"></div></td>
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="<%= !sCampos[1].equals(BCRA.getIdcliente().toString()) ? "fila-det-border" : "text-catorce" %>" ><%=sCampos[1]%> - <%=sCampos[2]%>&nbsp;- <%=sCampos[7]%></td>
      <td class="<%= sCampos[1].equals(BCRA.getIdcliente().toString()) ? "fila-det-border" : "text-catorce" %>" ><%=sCampos[3]%> - <%=sCampos[4]%>&nbsp;- <%=sCampos[9]%></td>
      <td class="fila-det-border" ><div align="center"><img src="<%= imagen %>" width="15" height="15"></div></td>
   </tr>
<%
   }%>
  </table>
   <input name="accion" value="" type="hidden">
   <input name="idcliente" type="hidden" id="idcliente" value="<%=BCRA.getIdcliente()%>">
   <input name="idclienteroot" type="hidden" id="idclienteroot" value="<%=BCRA.getIdcliente()%>">
   <input name="razon" type="hidden" id="idcliente" value="<%=BCRA.getRazon()%>">
   <input name="razonroot" type="hidden" id="idclienteroot" value="<%=BCRA.getRazon()%>">
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

