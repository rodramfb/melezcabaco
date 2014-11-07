<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vClientesRemitosHojaRutaFinalPreconf
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Sep 20 10:43:23 ART 2010 
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
String titulo = "PRECONFORMACION DE REMITOS POR HOJA DE RUTA FINAL";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVClientesRemitosHojaRutaFinalPreconf   = null;
int totCol = 10; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVCRHRFPA"  class="ar.com.syswarp.web.ejb.BeanVClientesRemitosHojaRutaFinalPreconf"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVCRHRFPA" property="*" />
<%
 BVCRHRFPA.setUsuarioact(  usuario );  
 BVCRHRFPA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 BVCRHRFPA.setResponse(response);
 BVCRHRFPA.setRequest(request);
 BVCRHRFPA.ejecutarValidacion();
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
         obj[i].checked = check;
       }
     }
     else  
       obj.checked = check;
   }    
 }
 
  function ejecutarPreconformacion(){
   if(confirm('Confirma cambio de estado para remitos asociados a las Hojas de Rutas Seleccionadas?')){
     document.frm.accion.value = 'asignarestadopreconformacion';
     document.getElementById("asignarestadopreconformacion").value = 'Procesando ...';
     document.getElementById("asignarestadopreconformacion").disabled = true;
     document.frm.submit();
   }
 }
  window.onload = function() { 
    document.getElementById('marca').onclick =  checkUnckeckAll; 
	document.getElementById('asignarestadopreconformacion').onclick = ejecutarPreconformacion;
  }
 </script>
</head>
<%
// titulos para las columnas

// nrohojarutafinal, nropallets, fechahojarutafinal, idzona, zona, idexpreso, expreso, fechapreconf, fechabaja,

tituCol[0] = "NºHRF";
tituCol[1] = "Pallets";
tituCol[2] = "Fecha HRF";
tituCol[3] = "idzona";
tituCol[4] = "Zona";
tituCol[5] = "idexpreso";
tituCol[6] = "Expreso";
tituCol[7] = "F.Preconf";
tituCol[8] = "F.Baja";
tituCol[9] = "idempresa";
java.util.List VClientesRemitosHojaRutaFinalPreconf = new java.util.ArrayList();
VClientesRemitosHojaRutaFinalPreconf= BVCRHRFPA.getVClientesRemitosHojaRutaFinalPreconfList();
iterVClientesRemitosHojaRutaFinalPreconf = VClientesRemitosHojaRutaFinalPreconf.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="vClientesRemitosHojaRutaFinalPreconf.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="../imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="40" colspan="2"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="20%" height="38"><div align="center">
                    <input name="asignarestadopreconformacion" type="button" class="boton" id="asignarestadopreconformacion" value="Preconformar">
                  </div></td>
                   <td width="80%"> 
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BVCRHRFPA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BVCRHRFPA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BVCRHRFPA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BVCRHRFPA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BVCRHRFPA.getTotalRegistros()%>">Todos</option>
                                             </select>                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BVCRHRFPA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BVCRHRFPA.getPaginaSeleccion() ){%>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVCRHRFPA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="2" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[0]%></div></td>
     <td width="8%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[1]%></div></td>
     <td width="14%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[2]%></div></td>
     <td width="28%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
     <td width="26%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[6]%></td>
     <td width="13%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[7]%></td>
     <td width="6%" ><div align="center" ><a href="#" id="marca">Todos</a></div></td>
  </tr>
   <%int r = 0;
   while(iterVClientesRemitosHojaRutaFinalPreconf.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVClientesRemitosHojaRutaFinalPreconf.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><div align="right">&nbsp;<%=sCampos[0]%></div></td>
      <td class="fila-det-border" ><div align="right">&nbsp;<%=sCampos[1]%></div></td>
      <td class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[2]), "JSDateToStr")%> </div></td>
      <td class="fila-det-border" ><%=sCampos[4]%>&nbsp;</td>
      <td class="fila-det-border" ><%=Common.setNotNull(sCampos[6])%>&nbsp;</td>
      <td class="fila-det-border" ><%=Common.setNotNull(sCampos[7])%>&nbsp;</td>
      <td class="fila-det-border" ><div align="center">
        <input name="nrohojarutafinal" type="checkbox" id="nrohojarutafinal" value="<%=sCampos[0]%>">
      </div></td>
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

