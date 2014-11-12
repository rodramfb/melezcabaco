<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: contableplanajus
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Jan 22 09:55:13 ART 2008 
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
<jsp:useBean id="BCF"  class="ar.com.syswarp.web.ejb.BeanContableplanajusFrm"   scope="page"/>
<head>
 <title>FRMContableplanajus.jsp</title>
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
 String titulo = BCF.getAccion().toUpperCase() + " DE Plan ajuste" ;
 BCF.setResponse(response);
 BCF.setRequest(request);
 BCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BCF.setIdejercicio( new BigDecimal( session.getAttribute("ejercicioActivo").toString() ));
 BCF.ejecutarValidacion();
 %>
<form action="contableplanajusFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCF.getAccion()%>" >

   <table width="90%"  border="0" cellspacing="0" cellpadding="0" align="center">
     <tr>
       <td>
         <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
            <tr class="text-globales">
              <td>&nbsp;<%= titulo %></td>
            </tr>
       </table> 
            <table width="100%" border="0" align="center" cellpadding="1" cellspacing="1"  class="fila-det">
    		  <tr class="fila-det">
				    <td width="10%" height="23" class="fila-det-border"  >Cuenta(*)</td>
		      <td width="13%" class="fila-det-border" >
					<input type="text" name="cuenta_pl" width="100" class="campo" value="<%=BCF.getCuenta_pl()%>" readonly="yes">
                    <input name="d_cuenta_pl" type="hidden" id="d_cuenta_pl" value="<%=BCF.getD_cuenta_pl()%>">
<td width="10%" class="fila-det-border" ><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick=              "mostrarLOV('../Contable/lov_cuentas.jsp')" style="cursor:pointer"></td>
						<td   class="fila-det-border" >&nbsp;</td>						
            <td   class="fila-det-border" >&nbsp;</td>						
		    </tr>	
          <tr class="fila-det"> 
            <td height="23" class="fila-det-border" >Indice&nbsp;(*) </td>
            <td width="13%" class="fila-det-border" >
						  <input name="indice_pl" width="100" type="text" class="campo" id="indice" value="<%=BCF.getIndice_pl()%>" readonly="yes">
                          <input name="d_indice_pl" type="hidden" id="d_indice_pl" value="<%=BCF.getD_indice_pl()%>">
          </td>
					  <td width="10%" class="fila-det-border" >
						  <input name="ano" width="100" type="text" class="campo" id="ano" value="<%=BCF.getAno()%>" readonly="yes">
						  <input name="d_ano" type="hidden" id="d_ano" value="<%=BCF.getD_ano()%>">
		  </td>
            <td width="31%" class="fila-det-border" ><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Contable/lov_ajuste.jsp')" style="cursor:pointer"></td>
						<td width="36%" class="fila-det-border" >&nbsp;</td>
		    </tr>
				<tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><input name="validar" type="submit" value="Enviar" class="boton"></td>
				<td class="fila-det-border"><input name="volver" type="submit" class="boton" id="volver" value="Volver"></td>
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

