<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: globalusuarios
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Jan 23 13:30:47 GMT-03:00 2007 
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
<jsp:useBean id="BGF"  class="ar.com.syswarp.web.ejb.BeanAsistenteparaAltadeUsuariosFrm"   scope="page"/>
<head>
 <title>FRMGlobalusuarios.jsp</title>
  <title><MMString:LoadString id="insertbar/formsHidden" /></title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">
 
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script></head>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BGF" property="*" />
 <% 
 String titulo = BGF.getAccion().toUpperCase() + " DE Usuarios" ;
 BGF.setResponse(response);
 BGF.setRequest(request);
 BGF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BGF.setUsuarioact( session.getAttribute("usuario").toString() );
 BGF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BGF.ejecutarValidacion();
 %>
<form action="asistente1Frm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BGF.getAccion()%>" >
<input name="idusuario" id="idusuario" type="hidden" value="<%=BGF.getIdusuario()%>" > 
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
                <td width="12%" class="fila-det-border">Usuario: (*) </td>
                <td width="88%" class="fila-det-border"><input name="usuario" type="text" value="<%=BGF.getUsuario()%>" class="campo" size="20" maxlength="20"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Clave: (*) </td>
                <td width="88%" class="fila-det-border"><input name="clave" type="text" value="<%=BGF.getClave()%>" class="campo" size="30" maxlength="30"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">E-mail: (*) </td>
                <td width="88%" class="fila-det-border"><input name="email" type="text" value="<%=BGF.getEmail()%>" class="campo" size="70" maxlength="70"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Nombre: (*) </td>
                <td width="88%" class="fila-det-border"><input name="nombre" type="text" value="<%=BGF.getNombre()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Habilitado: (*) </td>
                <td width="88%" class="fila-det-border"><select name="habilitado" id="habilitado"  >
                  <option value="S" <%= BGF.getHabilitado().equalsIgnoreCase("S") ? "selected" : "" %> >SI</option>
                  <option value="N" <%= BGF.getHabilitado().equalsIgnoreCase("N") ? "selected" : "" %> >NO</option>
                </select></td>
              </tr>
			  <tr class="fila-det">
                <td width="17%" class="fila-det-border">Puesto:  </td>
                <td width="32%" class="fila-det-border"><table width="23%" border="0">
                    <tr class="fila-det-border">
                      <td width="61%" >
                      <input name="puesto" type="text" class="campo" id="puesto" value="<%=str.esNulo(BGF.getPuesto())%>" size="30" readonly></td>
                      <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../General/lov_puesto.jsp')" style="cursor:pointer"></td>
                      <input name="idpuesto" type="hidden" id="idpuesto" value="<%=BGF.getIdpuesto()%>">
                    </tr>
                  </table></td>
                
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;
                	<input name="validar" type="submit" value="Enviar" class="boton">
					<input name="siguiente" type="submit" class="boton" id="siguiente" value="siguiente">
				</td>
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
  System.out.println(String.format("Exception: %s", ex.getMessage()));
  ex.printStackTrace();
}%>

