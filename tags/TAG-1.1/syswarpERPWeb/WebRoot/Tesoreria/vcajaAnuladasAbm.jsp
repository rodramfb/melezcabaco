<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vcajaAnuladas
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Jun 10 10:04:09 GYT 2009 
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
String titulo = "COMPROBANTES ANULADOS: PAGOS / COBRANZAS ";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVcajaAnuladas   = null;
int totCol = 11; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVAA"  class="ar.com.syswarp.web.ejb.BeanVcajaAnuladasAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVAA" property="*" />
<%
 BVAA.setIdempresa(new BigDecimal(session.getAttribute("empresa").toString())); 
 BVAA.setResponse(response);
 BVAA.setRequest(request);
 BVAA.ejecutarValidacion();
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
    overlib(leyenda, STICKY, CAPTION, 'DATOS DE AUDITORIA ',TIMEOUT,5000,HAUTO,VAUTO,WIDTH,350);
  }

 </script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Tipo";
tituCol[1] = "Comprobante";
tituCol[2] = "comprob_an";
tituCol[3] = "Anulado";
tituCol[4] = "Importe";
tituCol[5] = "sucursa_an";
tituCol[6] = "idclipro_an";
tituCol[7] = "Razoón Social";
tituCol[8] = "cuit_an";
tituCol[9] = "tipo_an";
tituCol[10] = "Audit";
java.util.List VcajaAnuladas = new java.util.ArrayList();
VcajaAnuladas= BVAA.getVcajaAnuladasList();
iterVcajaAnuladas = VcajaAnuladas.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="vcajaAnuladasAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="33" colspan="2"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="2%" height="38">&nbsp;</td>
                   <td width="98%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="11%" height="26" class="text-globales">Buscar</td>
                           <td width="21%">
                              <input name="ocurrencia" type="text" value="<%=BVAA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                           </td>
                           <td width="68%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BVAA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BVAA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BVAA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BVAA.getTotalRegistros()%>">Todos</option>
                                             </select>                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BVAA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BVAA.getPaginaSeleccion() ){%>
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
                         <tr>
                           <td height="26" class="text-globales">Tipo Comprobante </td>
                           <td>
                           <select name="tipomov_an" id="tipomov_an" class="campo">
                             <option value="">Todos</option>
                             <option value="COB" <%=  BVAA.getTipomov_an().equals("COB") ? "selected" : "" %>>Cobranzas - Ingresos Directos</option>
                             <option value="PAG" <%=  BVAA.getTipomov_an().equals("PAG") ? "selected" : "" %>>Pagos - Egresos Directos</option>
                           </select>
                           </td>
                           <td>&nbsp;</td>
                         </tr>
                   </table>                </td>
            </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVAA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="13%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[4]%></div></td>
     <td width="54%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[7]%></td>
     <td width="14%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[10]%></div></td>
   </tr>
   <%int r = 0;
   while(iterVcajaAnuladas.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVcajaAnuladas.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[4]%>&nbsp;</div></td>
      <td class="fila-det-border" ><%=sCampos[7]%>&nbsp;[<%=sCampos[6]%>]</td>
      <td class="fila-det-border" ><%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[3]), "JSTsToStr")%>&nbsp;</td>
      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/config-users.png" width="18" height="18" onMouseOver="callOverlib('Usuario: <%=sCampos[11]%> <br>Fecha: <%=sCampos[13]%>')"></div></td>
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

