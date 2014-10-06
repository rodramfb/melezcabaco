<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: produccionRecetas_deta
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Oct 23 12:38:58 GMT-03:00 2006 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.math.*"%> 
<%@ page import="ar.com.syswarp.api.*" %>
<%@ include file="session.jspf"%>
<% 
try{
Strings str = new Strings();
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BPRF"  class="ar.com.syswarp.web.ejb.BeanProduccionrecetas_detaFrm"   scope="page"/>
<head>
 <title>FRMProduccionRecetas_deta.jsp</title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">
 


<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
<script language="JavaScript" src="vs/forms/forms.js"></script>
<script language="JavaScript" src="vs/overlib/overlib.js"></script>
<script language="JavaScript">
function mostrarLOVStock(pagina) {
	frmLOV = open(pagina,'winLOV3','scrollbars=yes,resizable=yes,width=800,height=450,status=yes');
	if (frmLOV.opener == null) 
		frmLOV.opener = self;
}	

</script>

</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BPRF" property="*" />
 <% 
 
 BPRF.setResponse(response);
 BPRF.setRequest(request);
 BPRF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPRF.setUsuarioact( session.getAttribute("usuario").toString() ); 
 String codigo_st_cabe =  request.getParameter("codigo_st_cabe");
 String descrip_st =  request.getParameter("descrip_st");
  
 if(codigo_st_cabe!=null){
   session.setAttribute("codigo_st_cabe",codigo_st_cabe);
   session.setAttribute("descrip_st",descrip_st);
 } 
 codigo_st_cabe =  session.getAttribute("codigo_st_cabe").toString();
 descrip_st     =  session.getAttribute("descrip_st").toString();
 BPRF.setCodigo_st_cabe(codigo_st_cabe);

 BPRF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BPRF.ejecutarValidacion();
 
 
 String titulo = BPRF.getAccion().toUpperCase() + " DE DETALLE DE RECETA DE PRODUCCION - " + descrip_st ;
 %>
<form action="Produccionrecetas_detaFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BPRF.getAccion()%>" >
<input name="codigo_st_cabe" value="<%=codigo_st_cabe%>" type="hidden">
<input name="descrip_st" value="<%=descrip_st%>" type="hidden">

<input name="codigo_st_cabe" type="hidden" value="<%=BPRF.getCodigo_st_cabe()%>" >
   <table width="90%"  border="0" cellspacing="0" cellpadding="0" align="center">
     <tr>
       <td>
         <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
            <tr class="text-globales">
              <td>&nbsp;<%= titulo %></td>
            </tr>
            </table> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr class="fila-det-bold-rojo">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><jsp:getProperty name="BPRF" property="mensaje"/>&nbsp;</td>
              </tr>
              
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Articulo: (*) </td>
                <td class="fila-det-border"><table width="18%" border="0">
                  <tr class="fila-det-border">
                    <td width="30%"><input name="d_codigo_st" type="text" value="<%=BPRF.getD_codigo_st()%>" class="campo" size="15" maxlength="15" readonly="yes"></td>
                    <td width="70%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" <% if (BPRF.getAccion().equalsIgnoreCase("Alta")) {%> onClick="abrirVentana('../Produccion/lov_stock.jsp', 'stock2', 700, 400)" style="cursor:pointer"<% } %>></td>
                    <input name="codigo_st" type="hidden" id="codigo_st" value="<%=BPRF.getCodigo_st()%>">
                  </tr>
                </table></td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
              </tr>              
              
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Tipo: (*) </td>
                <td width="78%" class="fila-det-border"><input name="tipo_rd" type="text" value="<%=BPRF.getTipo_rd()%>" class="campo" size="3" maxlength="3"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Cantidad: (*) </td>
                <td width="78%" class="fila-det-border"><input name="cantidad_rd" type="text" value="<%=BPRF.getCantidad_rd()%>" class="campo" size="18" maxlength="18"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Imprime(S/N): (*) </td>
                
                <td width="78%" class="fila-det-border">
                   <select name="imprime" >
                     <%if(BPRF.getImprime().equalsIgnoreCase("S")){%>
                      <option value="S" selected>Si</option>
                     <%}else{%> 
                      <option value="S">Si</option>
                     <%}%>  

                     <%if(BPRF.getImprime().equalsIgnoreCase("N")){%>
                      <option value="N" selected>No</option>
                     <%}else{%> 
                      <option value="N">No</option>
                     <%}%>                       
                   </select>
                </td>
                
                  
                
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Margen de Error: (*) </td>
                <td width="78%" class="fila-det-border"><input name="margen_error_rd" type="text" value="<%=BPRF.getMargen_error_rd()%>" class="campo" size="18" maxlength="18"  ></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Enviar" class="boton">               
                <input name="volver" type="submit" class="boton" id="volver" value="Volver"></td>
             </tr>
          </table>
        </td>
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
  System.out.println("ERROR (" + pagina + ") : "+ex);   
}%>

