<%@page language="java" %>
<% 
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: RRHHbusquedasLaborales
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Oct 10 16:05:59 ART 2007 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%//@ include file="session.jspf"%>
<%@ page import="java.math.BigDecimal" %>
<% 
try{
Strings str = new Strings();
String titulo =  "DETALLE DE OFERTAS LABORALES" ;
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BRRHHLF"  class="ar.com.syswarp.web.ejb.BeanRRHHbusquedasLaboralesFrm"   scope="page"/>
<head>
<title><%= titulo %> </title>
  <link rel="stylesheet" type="text/css" href="sitio/css/import.css">
	<script language="JavaScript" src="vs/forms/forms.js"></script>
<script language="JavaScript" src="vs/overlib/overlib.js"></script>
<style type="text/css">
<!--
.Estilo1 {color: #FFFFFF}
.Estilo2 {color: #990000}
-->
</style>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BRRHHLF" property="*" /> 
 <%     
 String idempresa = session.getAttribute("empresapostulante") +  "" ;
 if(str.esNulo(idempresa).equals("")) idempresa = "1";
 String idpostulante =  session.getAttribute("idpostulante") == null ? "0" : session.getAttribute("idpostulante").toString() ;
 BRRHHLF.setResponse(response);
 BRRHHLF.setRequest(request);
 BRRHHLF.setUsuarioalt( session.getAttribute("idpostulante") == null ? "WEB" : session.getAttribute("idpostulante").toString() );
 BRRHHLF.setUsuarioact( session.getAttribute("idpostulante") == null ? "WEB" : session.getAttribute("idpostulante").toString() );
 BRRHHLF.setIduserpostulante( new BigDecimal( idpostulante ) );
 BRRHHLF.setIdempresa( new BigDecimal( idempresa ));
 BRRHHLF.ejecutarValidacion();  
 %>
<form action="rrhhOfertasLaboralesDetalle.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BRRHHLF.getAccion()%>" >
<input name="idbusquedalaboral" type="hidden" value="<%=BRRHHLF.getIdbusquedalaboral()%>" >
   <table width="90%"  border="0" cellspacing="0" cellpadding="0" align="center">
     <tr>
       <td>
         <table width="95%"  border="0" cellspacing="0" cellpadding="0" align="center">
            <tr >
              <td><strong>&nbsp;<%= titulo %></strong><span class="Estilo1"><font class="Estilo2">
                <jsp:getProperty name="BRRHHLF" property="mensaje"/>                
              </font></span></td>
            </tr>
            <tr >
              <td class="Estilo2">&nbsp;</td>
            </tr>	
						&nbsp;					
         </table> 
            <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#F3F3F3" class="table-border">
              <tr >
                <td width="23%" bgcolor="#EAECEB" >Referencia: </td>
              <td width="77%" bgcolor="#EAECEB" ><%=BRRHHLF.getReferencia()%></td>
              </tr>
              <tr >
                <td width="23%" >Fecha Inicio: </td>
                <td colspan="2"  ><%=BRRHHLF.getFechabusquedadesdeStr()%>             </td>
              </tr>
              <tr >
                <td width="23%" bgcolor="#EAECEB" >Fecha Fin: </td>
                <td colspan="2" bgcolor="#EAECEB"  ><%=BRRHHLF.getFechabusquedahastaStr()%>                </td>
              </tr>
              <tr >
                <td width="23%" >Seniority: </td>
                <td width="77%" ><%=BRRHHLF.getSeniority()%></td>
              </tr>
              <tr >
                <td width="23%" bgcolor="#EAECEB" >Lugar trabajo:</td>
                <td width="77%" bgcolor="#EAECEB" ><%=BRRHHLF.getLugartrabajo()%></td>
              </tr>
              <tr >
                <td width="23%" >Descripcion proyecto:  </td>
                <td width="77%" ><%=BRRHHLF.getDescripcionproyecto()%></td>
              </tr>
              <tr >
							  <td width="23%" bgcolor="#EAECEB" >Descripcion Tarea:  </td>
                <td width="77%" bgcolor="#EAECEB" ><%=BRRHHLF.getDescripciontarea()%></td>
              </tr>
              <tr >
                <td width="23%" >Skill excluyente:  </td>
                <td width="77%" ><%=BRRHHLF.getSkillexcluyente()%></td>
              </tr>
              <tr >
                <td width="23%" bgcolor="#EAECEB" >Skill deseable:  </td>
                <td width="77%" bgcolor="#EAECEB" ><%=BRRHHLF.getSkilldeseable()%></td></tr>
              <tr >
                <td width="23%" >Idioma:  </td>
                <td width="77%" ><%=BRRHHLF.getIdioma()%></td></tr>
<tr >
                <td width="23%" bgcolor="#EAECEB" >Posibilidad de Renovacion:  </td>
            <td width="77%" bgcolor="#EAECEB" ><%= BRRHHLF.getPosibilidadderenovacion().equalsIgnoreCase("S") ? "SI" : "NO" %></td>
</tr>
<tr >
  <td height="52" bgcolor="ffffff" >&nbsp;</td>
  <td valign="middle" bgcolor="ffffff" ><p align="center">
    <input name="validar" type="submit" id="validar" value="Postularse &gt;" onClick="document.frm.accion.value = 'postular'">
      </p>
    </td>
</tr>
          </table>
       <p align="center">&nbsp;</p></td>
      </tr>
    </table>
   </form>
  </body>
</html> 
<% 
 }
catch (Exception ex) {
   java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
   java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
   ex.printStackTrace(pw);
  System.out.println("ERROR ( + pagina + ) : "+ex);   
}%>

