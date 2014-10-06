<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: contableinfiplan2007
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jan 17 10:55:58 ART 2008 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%@ include file="session.jspf"%>
<%@ page import="java.math.BigDecimal" %>
<% 
try{
Strings str = new Strings();
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BCF"  class="ar.com.syswarp.web.ejb.BeanContableinfiplannFrm"   scope="page"/>
<head>
 <title>FRMContableinfiplan.jsp</title>
 <meta http-equiv="description" content="mypage">
  <link rel="stylesheet" href="<%=pathskin%>">
 
 <link rel="stylesheet" type="text/css" href="<%=pathscript%>/calendar/calendar.css">
<script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>
<script language="JavaScript" src="<%=pathscript%>/forms.js"></script> 
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCF" property="*" />
 <% 
 String titulo = BCF.getAccion().toUpperCase() + " DE Contable" ;
 String accion = request.getParameter("accion");
 String grabacion = request.getParameter("grabacion");
 String readonly = "";
 BCF.setResponse(response);
 BCF.setRequest(request);
 BCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BCF.setIdejercicio( new BigDecimal( session.getAttribute("ejercicioActivo").toString() ));
 BCF.ejecutarValidacion();

if(grabacion == null && accion.equalsIgnoreCase("Modificacion") ){
   readonly = "readonly='false'";  
}

if(grabacion != null && accion.equalsIgnoreCase("Alta")){ 
   readonly = "readonly='true'";  
}


 %>
 
 
 
 
 
<form action="contableinfiplanFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCF.getAccion()%>" >

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
                <td class="fila-det-border"><jsp:getProperty name="BCF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Codigo: (*) </td>
                <td width="88%" class="fila-det-border"><input name="idcuenta" type="text" class="campo" id="idcuenta" value="<%=BCF.getIdcuenta()%>"<%=readonly%> size="9" maxlength="9"  ></td>
              </tr>
			  <tr class="fila-det">
                <td width="12%" class="fila-det-border">Descripcion: (*) </td>
                <td width="88%" class="fila-det-border"><input name="cuenta" type="text" value="<%=BCF.getCuenta()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Imputable: (*) </td>
                <td width="88%" class="fila-det-border"><select name="imputable" title="Este campo le permitir&aacute; listar (en futuras versiones) los art&iacute;culos referentes a un tipo de inventario. " id="imputable"  >
<option value="S" <%= BCF.getImputable().equalsIgnoreCase("S") ? "selected" : "" %> >SI</option>
<option value="N" <%= BCF.getImputable().equalsIgnoreCase("N") ? "selected" : "" %> >NO</option>
</select></td>
</tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Nivel: (*) </td>
                <td width="88%" class="fila-det-border"><input name="nivel" type="text" value="<%=BCF.getNivel()%>" class="campo" size="2" maxlength="2"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Ajustable: (*) </td>
              <td width="88%" class="fila-det-border"><select name="ajustable" title="Este campo le permitir&aacute; listar (en futuras versiones) los art&iacute;culos referentes a un tipo de inventario. " id="ajustable"  >
<option value="S" <%= BCF.getAjustable().equalsIgnoreCase("S") ? "selected" : "" %> >SI</option>
<option value="N" <%= BCF.getAjustable().equalsIgnoreCase("N") ? "selected" : "" %> >NO</option>
</select></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Resultado: (*) </td>
              <td width="88%" class="fila-det-border"><select name="resultado" title="Este campo le permitir&aacute; listar (en futuras versiones) los art&iacute;culos referentes a un tipo de inventario. " id="resultado"  >
<option value="S" <%= BCF.getResultado().equalsIgnoreCase("S") ? "selected" : "" %> >SI</option>
<option value="N" <%= BCF.getResultado().equalsIgnoreCase("N") ? "selected" : "" %> >NO</option>
</select></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Cent_cost:  </td>
              <td width="88%" class="fila-det-border"><table width="23%" border="0">
<tr class="fila-det-border">
<td width="61%" ><input name="d_cent_cost" type="text" class="campo" id="d_cent_cost" value="<%=BCF.getD_cent_cost()%>" size="30" readonly></td>
<td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Contable/lov_centro_de_costos1.jsp')" style="cursor:pointer"></td>
<input name="cent_cost" type="hidden" id="cent_cost" value="<%=BCF.getCent_cost()%>">
</tr>
</table></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Cent_cost1:  </td>
              <td width="88%" class="fila-det-border"><table width="23%" border="0">
<tr class="fila-det-border">
<td width="61%" ><input name="d_cent_cost1" type="text" class="campo" id="d_cent_cost1" value="<%=BCF.getD_cent_cost1()%>" size="30" readonly></td>
<td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Contable/lov_centro_de_costos2.jsp')" style="cursor:pointer"></td>
<input name="cent_cost1" type="hidden" id="cent_cost1" value="<%=BCF.getCent_cost1()%>">
</tr>
</table></td>
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

