<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vClientesRemitosHojaRutaFinalReimprime
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
String titulo = "GENERAR ARCHIVOS ANDREANI - HOJA DE RUTA FINAL";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterclientesRemitosArchivoAndreani   = null;
int totCol = 16; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BCRGAA"  class="ar.com.syswarp.web.ejb.BeanClientesRemitosGeneraArchivoAndreani"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BCRGAA" property="*" />
<%
 BCRGAA.setUsuarioact( session.getAttribute("usuario").toString() );
 BCRGAA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 BCRGAA.setResponse(response);
 BCRGAA.setRequest(request);
 BCRGAA.ejecutarValidacion();
 
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>style.css">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="<%=pathskin%>/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script>
 
 function checkUnckeckAll(){
   var objTextTipo = document.getElementById("marca").firstChild;
   var check = true;
   if(objTextTipo.nodeValue == 'Todos') objTextTipo.nodeValue = 'Ninguno';
   else{
     objTextTipo.nodeValue = 'Todos';
     check=false;
   }
   var obj = document.frm.nrohojarutafinal;
   if(obj){
     if(obj.length) {
       for(var i = 0;i<obj.length;i++)  {
	     if(!obj[i].disabled)
           obj[i].checked = check;
       }
     }
     else{  
       if(!obj.disabled)
	     obj.checked = check;
	 }
   }    
 }
 
 
  function ejecutarGeneracion(){
   document.frm.accion.value = 'generar';
   document.getElementById("generar").value = 'Procesando ...';
   document.getElementById("generar").disabled = true;
   document.frm.submit();
 }
 
 
  window.onload = function() { 
    document.getElementById('marca').onclick =  checkUnckeckAll;
    document.getElementById('generar').onclick = ejecutarGeneracion;
  }
 
 </script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Nro. HRF";
tituCol[1] = "Pallets";
tituCol[2] = "idzona";
tituCol[3] = "Zona";
tituCol[4] = "codigo_dt_ori";
tituCol[5] = "Dep.Origen";
tituCol[6] = "codigo_dt_des";
tituCol[7] = "Dep.Destino";
tituCol[8] = "F.Preconf";
tituCol[9] = "F.Baja";
tituCol[10] = "Tipo";
tituCol[11] = "idempresa";
tituCol[15] = "F.Alta";
java.util.List clientesRemitosArchivoAndreani = new java.util.ArrayList();
clientesRemitosArchivoAndreani= BCRGAA.getClientesRemitosArchivoAndreaniList();
iterclientesRemitosArchivoAndreani = clientesRemitosArchivoAndreani.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="clientesRemitosGeneraArchivoAndreani.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="28" colspan="2"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="10%" height="38"><input name="generar" type="button" id="generar" value="Generar Archivo" class="boton"></td>
                  <td width="90%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BCRGAA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BCRGAA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BCRGAA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BCRGAA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BCRGAA.getTotalRegistros()%>">Todos</option>
                                             </select>                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BCRGAA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BCRGAA.getPaginaSeleccion() ){%>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BCRGAA" property="mensaje"/></td>
  </tr>
  
   <% 
   

   if(!BCRGAA.getArchivoAndreaniZip().equals(""))
   { %>  
  <tr class="text-globales"> 
    <td  >
      Archivo Andreani: <a href="<%= BCRGAA.getArchivoAndreaniZip() %>" target="_blank"><img src="../imagenes/default/gnome_tango/apps/kfm.png" width="20" height="20" border="0" style="cursor:pointer" title="Archivo Compromido"></a>
    </td>
  </tr> 
  <%
   }
   // <--
   %>  
  
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
    <td width="7%" ><div align="center" ><a href="#" id="marca">Todos</a></div></td>
     <td width="8%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[0]%></div></td>
     <td width="7%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[1]%></div></td>
     <td width="14%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="16%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
     <td width="31%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[7]%></td>
     <td width="8%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[8]%></td>
     <td width="8%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[9]%></td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[10]%></td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[15]%></td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
  </tr>
   <%int r = 0;
   while(iterclientesRemitosArchivoAndreani.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterclientesRemitosArchivoAndreani.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><div align="center">
 
        <input name="nrohojarutafinal" type="checkbox" id="nrohojarutafinal" value="<%=sCampos[0]%>" <%=Common.setNotNull(sCampos[12]).equals("") ? "" : "disabled"%>>
 
 
      </div></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[0]%>&nbsp;</div></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[1]%>&nbsp;</div></td>
      <td class="fila-det-border" ><%=sCampos[3]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[5]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[7]%>&nbsp;</td>
      <td class="fila-det-border" ><%=Common.setNotNull(sCampos[8])%>&nbsp;</td>
      <td class="fila-det-border" ><%=Common.setNotNull(sCampos[9])%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[10]%>&nbsp;</td>
      <td class="fila-det-border" ><%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[16]), "JSTsToStr")%>&nbsp;</td>
      <td class="fila-det-border" ><% if(!Common.setNotNull(sCampos[12]).equals("") && !Common.setNotNull(sCampos[12]).equalsIgnoreCase("ARCHIVOXHRF")){ %><a href="<%= sCampos[12] %>" target="_blank"><img src="../imagenes/default/gnome_tango/apps/kfm.png" width="20" height="20" border="0" style="cursor:pointer" title="Archivo Compromido"></a><% }else out.write("&nbsp;");  %></td>
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

