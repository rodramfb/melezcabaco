<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: RRHHbusquedasLaborales
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Oct 10 16:05:59 ART 2007 
   Observaciones: 
      . 


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.Iterator" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%//@ include file="session.jspf"%>
<%@ page import="java.math.BigDecimal" %>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "Ofertas Laborales".toUpperCase();
// variables de entorno
String pathskin = "";
String pathscript = "";
// variables de paginacion
int i = 0;
Iterator iterRRHHbusquedasLaborales   = null;
int totCol = 12; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("idpostulante") == null ? "WEB" : session.getAttribute("idpostulante").toString();
%>
<html>
<jsp:useBean id="BRRHHLA"  class="ar.com.syswarp.web.ejb.BeanRRHHOfertasLaborales"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BRRHHLA" property="*" />
<%
 String idempresa = session.getAttribute("empresapostulante") +  "" ;
 if(str.esNulo(idempresa).equals("")) idempresa = "1";
 BRRHHLA.setResponse(response);
 BRRHHLA.setRequest(request); 
 BRRHHLA.setIdempresa( new BigDecimal( idempresa ));
 BRRHHLA.ejecutarValidacion();
%>
<head>
<title><%=titulo%> </title>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
  <link rel="stylesheet" type="text/css" href="sitio/css/import.css">

<script type="text/JavaScript">
<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}
//-->
</script>
<style type="text/css">
<!--
.Estilo1 {color: #990000}
.Estilo2 {color: #FFFFFF}
-->
</style>
</head>
<%
// titulos para las columnas
tituCol[0] = "Codigo";
tituCol[1] = "Referencia";
tituCol[2] = "Fecha";
tituCol[3] = "Cierre";
tituCol[4] = "Seniority";
tituCol[5] = "Lugar trabajo";
tituCol[6] = "Descripcion proyecto";
tituCol[7] = "Descripcion tarea"; 
tituCol[8] = "Skill Excuyente";
tituCol[9] = "Skill Deseable";
tituCol[10] = "Idioma";
tituCol[11] = "Posibilidad de Renovacion";

java.util.List RRHHbusquedasLaborales = new java.util.ArrayList();
RRHHbusquedasLaborales= BRRHHLA.getRRHHbusquedasLaboralesList();
iterRRHHbusquedasLaborales = RRHHbusquedasLaborales.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" > 
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>


<form action="rrhhOfertasLaborales.jsp" method="POST" name="frm">
   <table width="90%"  border="0" cellspacing="0" cellpadding="0" align="center"> 
  <tr>
    <td>
<table width="100%" border="0" cellspacing="0" cellpadding="0" >
  <tr  >
    <td width="100%" height="24" colspan="10" >
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td><strong><%=titulo%></strong><span class="Estilo1">&nbsp;</span></td>
                </tr> 
                <tr>
                  <td >
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="48%">
                              <input name="ocurrencia" type="text" value="<%=BRRHHLA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">
                           <input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                           <td width="46%"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>Total de ofertas:&nbsp;<%=BRRHHLA.getTotalRegistros()%></td>
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
   <td height="36" class="Estilo1"><jsp:getProperty name="BRRHHLA" property="mensaje"/></td>
  </tr>
</table>
<table width="99%" border="0"  cellpadding="0" cellspacing="1" class="table-border"  >
  <tr >
     <td width="10%" bgcolor="#5C798A" >&nbsp;</td>
     <td width="15%" bgcolor="#5C798A" ><div align="center" class="Estilo2">
       <div align="center"><%=tituCol[1]%></div>
     </div></td>
     <td width="62%" bgcolor="#5C798A" ><div align="center" class="Estilo2">
       <div align="left"><%=tituCol[7]%></div>
     </div></td>
     <td width="13%" bgcolor="#5C798A" ><div align="left" class="Estilo2">
       <div align="center"><%=tituCol[3]%></div>
     </div></td> 
    </tr>
   <%int r = 0;
   while(iterRRHHbusquedasLaborales.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterRRHHbusquedasLaborales.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("#F3F3F3")) color_fondo = "#EAECEB"; 
      else color_fondo = "#F3F3F3";%>
   <tr  bgcolor="<%= color_fondo %>"> 
      <td  ><div align="center"><a href="rrhhOfertasLaboralesDetalle.jsp?idbusquedalaboral=<%= sCampos[0] %>&accion=consulta"> <%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[2]), "JSTsToStr")%> </a></div></td>
      <td  ><div align="center"><%=sCampos[1]%></div></td>
      <td  ><%=sCampos[7]%>&nbsp;</td>
      <td  ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[3]), "JSTsToStr")%></div></td>
    </tr>
<%
   }%>
</table>
    </td>
  </tr>
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
  System.out.println("ERROR (  + pagina +  ) : "+ex);   
}%>

