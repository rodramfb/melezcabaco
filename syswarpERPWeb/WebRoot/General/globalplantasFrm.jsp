<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: globalplantas
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Feb 08 12:59:32 GMT-03:00 2007 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
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
<jsp:useBean id="BGF"  class="ar.com.syswarp.web.ejb.BeanGlobalplantasFrm"   scope="page"/>
<head>
 <title>FRMGlobalplantas.jsp</title>
  <title><MMString:LoadString id="insertbar/formsHidden" /></title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">
 
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script></head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BGF" property="*" />
 <% 
 String titulo = BGF.getAccion().toUpperCase() + " DE Plantas" ;
 BGF.setResponse(response);
 BGF.setRequest(request);
 BGF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BGF.setUsuarioact( session.getAttribute("usuario").toString() );
 BGF.ejecutarValidacion();
 %>
<form action="globalplantasFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BGF.getAccion()%>" >
<input name="idplanta" type="hidden" value="<%=BGF.getIdplanta()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BGF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Planta: (*) </td>
                <td width="83%" class="fila-det-border"><input name="planta" type="text" value="<%=BGF.getPlanta()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Domicilio: (*) </td>
                <td width="83%" class="fila-det-border"><input name="domicilio" type="text" value="<%=BGF.getDomicilio()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Localidad: (*) </td>
                <td width="83%" class="fila-det-border"><table width="23%" border="0">
                  <tr class="fila-det-border">
                    <td width="61%" >
                      <input name="localidad" type="text" class="campo" id="localidad" value="<%=BGF.getLocalidad()%>" size="30" readonly></td>
                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../General/lov_localidades2.jsp')" style="cursor:pointer"></td>
                    <input name="idlocalidad" type="hidden" id="idlocalidad" value="<%=BGF.getIdlocalidad()%>">
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Cod postal: (*) </td>
                <td width="83%" class="fila-det-border"><input name="codpostal" type="text" value="<%=BGF.getCodpostal()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Telefonos:  </td>
                <td width="83%" class="fila-det-border"><input name="telefonos" type="text" value="<%=BGF.getTelefonos()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Fax:  </td>
                <td width="83%" class="fila-det-border"><input name="fax" type="text" value="<%=BGF.getFax()%>" class="campo" size="30" maxlength="30"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Tarea que desarrolla: (*) </td>
                <td width="83%" class="fila-det-border"><input name="tareaquedesa" type="text" value="<%=BGF.getTareaquedesa()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">E-mail:  </td>
                <td width="83%" class="fila-det-border"><input name="email" type="text" value="<%=BGF.getEmail()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Es head quarter:  </td>
                <td width="83%" class="fila-det-border">
                  <select name="esheadquarter" id="esheadquarter"  >
                    <option value="S" <%=BGF.getEsheadquarter().equalsIgnoreCase("S") ? "selected" : "" %> >SI</option>
                    <option value="N" <%=BGF.getEsheadquarter().equalsIgnoreCase("N") ? "selected" : "" %> >NO</option>
                  </select></td>
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

