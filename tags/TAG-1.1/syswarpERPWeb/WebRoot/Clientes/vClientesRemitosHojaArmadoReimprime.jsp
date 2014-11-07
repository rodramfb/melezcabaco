<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vClientesRemitosHojaArmadoReimprime
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Sep 21 15:10:10 ART 2010 
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
String titulo = "REIMPRESION DE HOJA DE ARMADO";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVClientesRemitosHojaArmadoReimprime   = null;
int totCol = 10; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVCRGA"  class="ar.com.syswarp.web.ejb.BeanVClientesRemitosHojaArmadoReimprimeAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVCRGA" property="*" />
<%
 BVCRGA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 BVCRGA.setResponse(response);
 BVCRGA.setRequest(request);
 BVCRGA.ejecutarValidacion();
 
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
//nrohojaarmado,fechahojaarmado,idzona,zona,codigo_dt_ori,descrip_dt_ori,codigo_dt_des,descrip_dt_des,tipo,idempresa
tituCol[0] = "NºHA";
tituCol[1] = "Fecha";
tituCol[2] = "idzona";
tituCol[3] = "Zona";
tituCol[4] = "codigo_dt_ori";
tituCol[5] = "Dep.Origen";
tituCol[6] = "codigo_dt_des";
tituCol[7] = "Dep.Destino";
tituCol[8] = "Tipo";
tituCol[9] = "idempresa";

java.util.List VClientesRemitosHojaArmadoReimprime = new java.util.ArrayList();
VClientesRemitosHojaArmadoReimprime= BVCRGA.getVClientesRemitosHojaArmadoReimprimeList();
iterVClientesRemitosHojaArmadoReimprime = VClientesRemitosHojaArmadoReimprime.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="vClientesRemitosHojaArmadoReimprime.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="28"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="97%" height="38">
                     <table width="100%" border="0">
                        <tr>
                          <td width="19%" height="26" class="text-globales"><div align="right">N&ordm;.H Armado:</div></td>
                          <td width="9%">
                          <input name="ocurrencia" type="text" value="<%=BVCRGA.getOcurrencia()%>" id="ocurrencia" size="10" maxlength="10" style="text-align:right">                           </td>
                          <td width="72%">
                            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td>
                                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                       <tr class="text-globales">
                                         <td width="1%" height="19">&nbsp; </td>
                                         <td width="23%">&nbsp;Total de registros:&nbsp;<%=BVCRGA.getTotalRegistros()%></td>
                                         <td width="11%" >Visualizar:</td>
                                         <td width="11%">
                                            <select name="limit" >
                                               <%for(i=15; i<= 150 ; i+=15){%>
                                                   <%if(i==BVCRGA.getLimit()){%>
                                                       <option value="<%=i%>" selected><%=i%></option>
                                                   <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                   <%}
                                                      if( i >= BVCRGA.getTotalRegistros() ) break;
                                                    %>
                                               <%}%>
                                               <option value="<%= BVCRGA.getTotalRegistros()%>">Todos</option>
                                            </select>                                          </td>
                                         <td width="7%">&nbsp;P&aacute;gina:</td>
                                         <td width="12%">
                                            <select name="paginaSeleccion" >
                                               <%for(i=1; i<= BVCRGA.getTotalPaginas(); i++){%>
                                                   <%if ( i==BVCRGA.getPaginaSeleccion() ){%>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVCRGA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="2" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="2%" >&nbsp;</td>
     <td width="8%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[0]%></div></td>
     <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[1]%></div></td>
     <td width="34%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
     <td width="40%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[7]%></td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[8]%></div></td>
  </tr>
   <%
   int r = 0;
   while(iterVClientesRemitosHojaArmadoReimprime.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVClientesRemitosHojaArmadoReimprime.next(); 
      // estos campos hay que setearlos segun la grilla 
	  String plantilla = sCampos[8].equalsIgnoreCase("N") ? "hoja_armado_frame" : "hoja_armado_reg_frame"; 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";
	  %>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="15" height="15" border="0" style="cursor:pointer" onClick="abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=<%= plantilla %>&nrohojaarmado=<%=sCampos[0]%>', 'hojaarmado', 750, 500);"></div></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[0]%>&nbsp;</div></td>
      <td class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[1]), "JSDateToStr")%></div></td>

      <td class="fila-det-border" ><%=sCampos[5]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[7]%>&nbsp;</td>

      <td class="fila-det-border" ><div align="center"><%=sCampos[8]%></div></td>
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

