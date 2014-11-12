<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: marketRegistro
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Mar 14 11:34:39 ART 2008 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.math.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%@ include file="session.jspf"%>
<% 
try{
	Strings str = new Strings();
	String pathskin = "vs/market.css";
	String pathscript = "scripts";

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BMRF"  class="ar.com.syswarp.web.ejb.BeanMarketRegistroFrm"   scope="page"/>
<head>
 <title></title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BMRF" property="*" />
 <% 
 String titulo = "REGISTRO" ;
 BMRF.setResponse(response);
 BMRF.setRequest(request);
 BMRF.setUsuarioalt( session.getAttribute("marketUsuario").toString() );
 BMRF.setUsuarioact( session.getAttribute("marketUsuario").toString() );
 BMRF.setIdempresa( new BigDecimal( session.getAttribute("marketEmpresa").toString() ));
 BMRF.ejecutarValidacion();
 %>
<html>
	 <link rel="stylesheet" type="text/css" href="vs/Stylesheet.css" />
		<body>
		<table>
			<tr>
				<td class="descripcion"><jsp:include page="index.jsp" ="true"/></td>
				<td>
					<form action="marketRegistroFrm.jsp" method="post" name="frmIngresar">
					<input name="accion" type="hidden" value="ingresar" >
					<input name="idcliente" type="hidden" value="<%=BMRF.getIdcliente()%>" >						
					
					<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="fila-det">
						<tr>
							<td width="20%" >&nbsp;</td> 
							<td class="mensaje"><%= BMRF.getAccion().equalsIgnoreCase("ingresar") ?  BMRF.getMensaje() : "" %>&nbsp;</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td class="fila-head">&iquest;Est&aacute; ya registrado como cliente?</td>
						</tr>
						<tr>
							<td >E-mail: (*) </td>
							<td width="88%" >&nbsp;<input name="email" type="text" value="<%=BMRF.getAccion().equalsIgnoreCase("ingresar") ? BMRF.getEmail() : "" %>" class="campo" size="50" maxlength="50"  ></td>
						</tr>
						<tr >
							<td width="12%" height="34" >Pass: (*) </td>
							<td width="88%" >&nbsp;<input name="pass" type="password" value="<%=BMRF.getAccion().equalsIgnoreCase("ingresar") ? BMRF.getPass() : "" %>" class="campo" size="50" maxlength="50"  ></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;<input name="ingresar" type="submit" class="boton" id="ingresar" value="Ingresar" onClick=" document.frmIngresar.accion.value = this.name"></td>
						</tr>
					</table>
				</form>
				</td>
				<td>
					<p>&nbsp;</p>
					<form action="marketRegistroFrm.jsp" method="post" name="frmRegistrar">
					<input name="accion" type="hidden" value="registrar" >
					<input name="idcliente" type="hidden" value="<%=BMRF.getIdcliente()%>" >						
					<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center"  class="fila-det">
						<tr>
							<td width="20%" >&nbsp;</td>
							<td class="mensaje"><%= BMRF.getAccion().equalsIgnoreCase("registrar") ?  BMRF.getMensaje() : "" %>            
							&nbsp;</td>
						</tr>
						<tr>
							<td >&nbsp;</td>
							<td  class="fila-head">&iquest;No est&aacute; a&uacute;n registrado como cliente?</td>
						</tr>
						<tr >
							<td  >E-mail: (*) </td>
							<td width="88%" >&nbsp;
					<input name="email" type="text" value="<%= BMRF.getAccion().equalsIgnoreCase("registrar") ?  BMRF.getEmail() : "" %>" class="campo" size="50" maxlength="50"  ></td>
						</tr>
						<tr >
							<td width="12%" height="34" >Pass: (*) </td>
							<td width="88%" >&nbsp;
							<input name="pass" type="password" value="<%=BMRF.getAccion().equalsIgnoreCase("registrar") ?  BMRF.getPass() : ""%>" class="campo" size="50" maxlength="50"  ></td>
						</tr>
						<tr >
							<td height="34" >Confirmar Pass: (*) </td>
							<td >&nbsp;
							<input name="passverifica" type="password" value="<%= BMRF.getAccion().equalsIgnoreCase("registrar") ? BMRF.getPassverifica() : "" %>" class="campo" size="50" maxlength="50"  ></td>
						</tr>
						<tr >
							<td >&nbsp;</td>
							<td >&nbsp;
							<input name="registrar" type="submit" class="boton" id="registrar" value="Registrarme"></td>
						</tr>
					</table>  
					
					</form>	
				</td>
				<td>	       
					<p>&nbsp;</p>
					<p>&nbsp;</p>
					<form action="marketRegistroFrm.jsp" method="post" name="frmRecuperar">
					<input name="accion" type="hidden" value="recuperar" >
					<input name="idcliente" type="hidden" value="<%=BMRF.getIdcliente()%>" >
					<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center"  class="fila-det" >
						<tr >
							<td width="20%" >&nbsp;</td>
							<td class="mensaje"><%= BMRF.getAccion().equalsIgnoreCase("recuperar") ?  BMRF.getMensaje() : "" %>       
							&nbsp;</td>
						</tr>
						<tr >
							<td  >&nbsp;</td>
							<td  class="fila-head">&iquest;Olvid&oacute; su clave?</td>
						</tr>
						<tr >
							<td  >E-mail: (*) </td>
							<td width="88%" >&nbsp;
							<input name="email" type="text" value="<%= BMRF.getAccion().equalsIgnoreCase("recuperar") ? BMRF.getEmail() : "" %>" class="campo" size="50" maxlength="50"  ></td>
						</tr>
						
						<tr >
							<td >&nbsp;</td>
							<td >&nbsp;
							<input name="recuperar" type="submit" class="boton" id="recuperar" value="Recuperar Pass"></td>
						</tr>
					</table>     
					</form>
					</td>
				</td>
			</tr>
		</table>
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