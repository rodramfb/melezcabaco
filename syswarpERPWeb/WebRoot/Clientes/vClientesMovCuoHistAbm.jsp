<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vClientesMovCuoHist
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Nov 02 15:06:50 ART 2012 
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
String titulo = "HISTORICO PRESENTACION CUOTAS";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVClientesMovCuoHist   = null;
int totCol = 23; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVCMCHA"  class="ar.com.syswarp.web.ejb.BeanVClientesMovCuoHistAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVCMCHA" property="*" />
<%
 BVCMCHA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 BVCMCHA.setResponse(response);
 BVCMCHA.setRequest(request);
 BVCMCHA.ejecutarValidacion();
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
tituCol[0] = "Log";
tituCol[1] = "idmomentoproceso";
tituCol[2] = "Proceso";
tituCol[3] = "idmovcuo";
tituCol[4] = "numero_cuo";
tituCol[5] = "ninter_cuo";
tituCol[6] = "import_cuo";
tituCol[7] = "venci_cuo";
tituCol[8] = "saldo_cuo";
tituCol[9] = "idcondicion_cuo";
tituCol[10] = "idtarjeta_cuo";
tituCol[11] = "Tarjeta";
tituCol[12] = "NºTjta.";
tituCol[13] = "Periodo";
tituCol[14] = "F.Envio";
tituCol[15] = "F.Ctrl";
tituCol[16] = "Estado";
tituCol[17] = "idclub";
tituCol[18] = "idmotivorechazo";
tituCol[19] = "Rechazo";
tituCol[20] = "NºGen.";
tituCol[21] = "F.Mov";
tituCol[22] = "idempresa";
java.util.List VClientesMovCuoHist = new java.util.ArrayList();
VClientesMovCuoHist= BVCMCHA.getVClientesMovCuoHistList();
iterVClientesMovCuoHist = VClientesMovCuoHist.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="vClientesMovCuoHistAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="30"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="11%" height="38"><table width="100%" border="0">
                    <tr>
                      <td width="72%" height="26"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr class="text-globales">
                                  <td width="1%" height="19">&nbsp;</td>
                                  <td width="23%">&nbsp;Total de registros:&nbsp;<%=BVCMCHA.getTotalRegistros()%></td>
                                  <td width="11%" >Visualizar:</td>
                                  <td width="11%"><select name="limit" >
                                      <%for(i=15; i<= 150 ; i+=15){%>
                                      <%if(i==BVCMCHA.getLimit()){%>
                                      <option value="<%=i%>" selected><%=i%></option>
                                      <%}else{%>
                                      <option value="<%=i%>"><%=i%></option>
                                      <%}
                                                      if( i >= BVCMCHA.getTotalRegistros() ) break;
                                                    %>
                                      <%}%>
                                      <option value="<%= BVCMCHA.getTotalRegistros()%>">Todos</option>
                                    </select>                                  </td>
                                  <td width="7%">&nbsp;P&aacute;gina:</td>
                                  <td width="12%"><select name="paginaSeleccion" >
                                      <%for(i=1; i<= BVCMCHA.getTotalPaginas(); i++){%>
                                      <%if ( i==BVCMCHA.getPaginaSeleccion() ){%>
                                      <option value="<%=i%>" selected><%=i%></option>
                                      <%}else{%>
                                      <option value="<%=i%>"><%=i%></option>
                                      <%}%>
                                      <%}%>
                                    </select>                                  </td>
                                  <td width="10%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVCMCHA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[11]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[12]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[13]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[14]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[15]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[16]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[19]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[20]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[21]%></td>
    </tr>
   <%int r = 0;
   while(iterVClientesMovCuoHist.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVClientesMovCuoHist.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" >&nbsp;<%=sCampos[0]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[2]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[11]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[12]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[13]%></td>
      <td class="fila-det-border" >&nbsp;<%=!Common.setNotNull(sCampos[14]).equals("") ? Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[14]), "JSDateToStr") : ""%></td>
      <td class="fila-det-border" >&nbsp;<%=!Common.setNotNull(sCampos[15]).equals("") ? Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[15]), "JSDateToStr") : ""%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[16]%></td>
      <td class="fila-det-border" >&nbsp;<%=Common.setNotNull(sCampos[19])%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[20]%></td>
      <td class="fila-det-border" >&nbsp;<%=!Common.setNotNull(sCampos[21]).equals("") ? Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[21]), "JSTsToStrWithHM") : ""%></td>
    </tr>
<%
   }%>
  </table>
   <input name="accion" value="" type="hidden">
   <input name="idmovcuo" type="hidden" id="idmovcuo" value="<%= BVCMCHA.getIdmovcuo() %>">
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

