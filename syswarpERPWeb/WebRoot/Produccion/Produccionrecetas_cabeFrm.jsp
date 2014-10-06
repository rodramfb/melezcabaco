<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: produccionRecetas_cabe
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Oct 20 11:54:15 GMT-03:00 2006 
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
<jsp:useBean id="BPRF"  class="ar.com.syswarp.web.ejb.BeanProduccionrecetas_cabeFrm"   scope="page"/>
<head>
 <title>FRMProduccionRecetas_cabe.jsp</title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">
 


<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
<script language="JavaScript" src="vs/forms/forms.js"></script>
<script language="JavaScript" src="vs/overlib/overlib.js"></script>
<script language="JavaScript">
function mostrarLOVStock(pagina) {
	frmLOV = open(pagina,'winLOV','scrollbars=yes,resizable=yes,width=800,height=450,status=yes');
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
 String titulo = BPRF.getAccion().toUpperCase() + " DE RECETAS DE PRODUCCION" ;
 BPRF.setResponse(response);
 BPRF.setRequest(request);
 BPRF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPRF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPRF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BPRF.ejecutarValidacion();
 %>
<form action="Produccionrecetas_cabeFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BPRF.getAccion()%>" >
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
                <td class="fila-det-border"> 
								<table width="23%" border="0">
                    <tr class="fila-det-border">
                      <td width="30%"><input name="d_codigo_st" type="text" value="<%=BPRF.getD_codigo_st()%>" class="campo" size="15" maxlength="15" readonly="yes"></td>
                      <td width="70%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Produccion/lov_stock.jsp')" style="cursor:pointer"></td>
					  <input name="codigo_st" type="hidden" id="codigo_st" value="<%=BPRF.getCodigo_st()%>">
                    </tr>
                  </table></td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
              </tr>              
              
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Receta: (*) </td>
                <td width="78%" class="fila-det-border"><input name="receta" type="text" value="<%=BPRF.getReceta()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">U.Medida: (*) </td>                
                <td class="fila-det-border"> 
								<table width="22%" border="0">
                    <tr class="fila-det-border">
                      <td width="30%"><input name="d_codigo_md" type="text" class="campo" id="d_codigo_md" value="<%=BPRF.getD_codigo_md()%>" size="15" maxlength="15" readonly="yes"></td>
                      <td width="70%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Produccion/lov_medida.jsp')" style="cursor:pointer"></td>
					  <input name="codigo_md" type="hidden" id="codigo_md" value="<%=BPRF.getCodigo_md()%>">
                    </tr>
                  </table></td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
              </tr>              
              
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Enviar" class="boton">               <input name="volver" type="submit" class="boton" id="volver" value="Volver"></td>
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

