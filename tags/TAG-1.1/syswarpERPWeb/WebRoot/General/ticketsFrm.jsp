<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: tickets
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Sep 20 11:56:06 ART 2012 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.math.*" %>
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
<jsp:useBean id="BTF"  class="ar.com.syswarp.web.ejb.BeanTicketsFrm"   scope="page"/>
<head>
 <title>FRMTickets</title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
<script type="text/javascript">
function verMensaje() {
	<%
	 String mensaje = BTF.getMensaje();
	 System.out.println(mensaje);
	%>
	
}
function mostrarLOV(pagina) {
	frmLOV = open(pagina,'winLOV','scrollbars=yes,resizable=yes,width=600,height=350,status=yes');
	if (frmLOV.opener == null) 
		frmLOV.opener = self;
}	
</script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BTF" property="*" />
 <% 
 String titulo = BTF.getAccion().toUpperCase() + " DE TICKETS" ;
 BTF.setResponse(response);
 BTF.setRequest(request);
 BTF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BTF.setUsuarioact( session.getAttribute("usuario").toString() );
 BTF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BTF.ejecutarValidacion();
 %>
<form action="ticketsFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BTF.getAccion()%>" >
<input name="idticket" type="hidden" value="<%=BTF.getIdticket()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BTF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;Grupo(*):</td>
                <td class="fila-det-border" valign="baseline">&nbsp;
                <input name="grupo" type="text" class="campo" size="50" maxlength="100" value="<%=BTF.getGrupo()%>" readonly="true">
                	<img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('lov_grupo.jsp')" style="cursor:pointer" align="bottom" >
                	<input name="idgrupo" type="hidden" value="<%=BTF.getIdgrupo()%>" class="campo" size="100" maxlength="100"  >
                </td>
                </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;Usuario:</td>
                <td class="fila-det-border">&nbsp; <input name="usuario" type="text" class="campo" size="50" maxlength="100" value="<%=BTF.getUsuario()%>" readonly="true">
                <input name="idusuario" type="hidden" value="<%=BTF.getIdusuario()%>" class="campo" size="100" maxlength="100"  >
                <img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('lov_usuario.jsp')" style="cursor:pointer">
                </td>
              </tr>
			  <tr class="fila-det">
                <td class="fila-det-border">&nbsp;Cliente: (*)</td>
                <td class="fila-det-border">&nbsp;
                <input type="text" name ="cliente" size="50" class="campo" readonly="readonly" value="<%=BTF.getCliente()%>"/>    
                <input name="idcliente" type="hidden" value="<%=BTF.getIdcliente()%>" class="campo" size="100" maxlength="100"  >
                <img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Clientes/lov_clientes.jsp')" style="cursor:pointer">
                </td>
              </tr>
              
			  <tr class="fila-det">
                <td class="fila-det-border">&nbsp;Resumen (*):</td>
                <td class="fila-det-border">&nbsp;
                	<input type="text" name="resumen" class="campo" size="99" maxlength="100" value="<%=BTF.getResumen()%>"/>
                </td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;Descripcion (*): </td>
                <td class="fila-det-border">&nbsp; <textarea rows="3" cols="100" name="descripcion" class="campo" ><%=BTF.getDescripcion()%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;Estado del ticket: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;
                <select name="idticketestado">
                	<option value="-1">Seleccionar</option>
                	<%
                	Iterator iterEstados =   BTF.getListEstadosTicket().iterator();
                	while (iterEstados.hasNext()  )
                	{
                		String[] datosEstados = (String[]) iterEstados.next();
                		%>
                		<option value="<%=datosEstados[0]%>" <%=new BigDecimal(datosEstados[0]).longValue() == BTF.getIdticketestado().longValue() ? "Selected" : ""%> ><%=datosEstados[1]%></option>
                		<%
                	}
                	%>
                </select>
                </td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Enviar" class="boton">               <input name="volver" type="submit" class="boton" id="volver"  value="Volver" ></td>
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

