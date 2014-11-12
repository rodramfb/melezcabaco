<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: bacoRefOperaciones
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Jun 15 11:32:56 ART 2010 
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
String titulo = " SAS - CONTACTOS" ;
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
boolean seleccion=false;

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
 <title>SAS Contacto</title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
 <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script>
function test ()
{
var cant = document.frm.tipo.length;
for(var i=0;i<cant;i++)
	if (document.frm.tipo[i].checked)
	{
	test2(i);
	}
}
 
function test2(posicion){
var estado = false
  if (posicion==0)
  {
	estado = true; 
	document.frm.motivoentrante.disabled = !estado;
	document.frm.canalentrante.disabled = !estado;
	document.frm.motivosaliente.disabled = estado;
	document.frm.canalsaliente.disabled = estado;
  }
  if (posicion==1)
  {
	estado = true; 
	document.frm.motivoentrante.disabled = estado;
	document.frm.canalentrante.disabled = estado;
	document.frm.motivosaliente.disabled = !estado;
	document.frm.canalsaliente.disabled = !estado;
  }
}
 </script>
</head>
<body>

<form action="contactosFrm.jsp" method="post" name="frm">
 <table border="0" cellspacing="0" cellpadding="0" align="center">
	<tr class="text-globales">
              <td colspan="2">&nbsp;<%= titulo %></td>
            </tr>
	<tr class="fila-det">
		<td class="fila-det-border" >Entrante<input name="tipo" type="radio" value="E" onClick="test();test2()"></td>
		<td class="fila-det-border"><div align="right">Saliente
	      <input type="radio" name="tipo" value="S" onClick="test();test2()">
	  </div></td>
	</tr>
	<tr class="fila-det">
	  <td class="fila-det-border"><div align="left">
	    <select name="canalentrante" disabled="disabled">
	      <option value="entranteC" selected="selected">Canal de contacto entrante</option>
	      <option value="email">E-mail</option>
	      <option value="telefono">Telefono</option>
	      <option value="logistica">Log�stica (Novedad)</option>
	      <option value="correoexterno">Correo (externo)</option>
	      <option value="paginaweb">P�gina web</option>
	      <option value="micomercial">Memo Interno Comercial</option>
	      <option value="mitelemarketing">Memo Interno Telemarketing</option>
	      <option value="micobranzas">Memo Interno Cobranzas</option>
	      <option value="mirecepcion">Memo Interno Recepci�n</option>
	      </select>
      </div></td>
		<td class="fila-det-border"><div align="right">
		  <select name="canalsaliente" disabled="disabled">
		    <option value="salienteC" selected="selected">Canal de contacto saliente</option>
		    <option value="emailsaliente">Email</option>
		    <option value="telefonosaliente">Tel�fono</option>
		    <option value="distribucionbaco">Distribuci�n Baco</option>
		    <option value="correosaliente">Correo (externo)</option>
	      </select>
	  </div></td>
	</tr>
	<tr class="fila-det">
		<td class="fila-det-border">
			<select name="motivoentrante" disabled="disabled">
				<option value="motivoE" selected="selected">Motivo de contacto </option>
				<option value="entregaerronea">Reclamo - Entrega erronea</option>
				<option value="noentregacaja">Reclamo - No entrega de caja</option>
				<option value="calidad">Reclamo - Calidad del producto</option>
				<option value="facturacion">Consulta - Facturaci�n</option>
				<option value="regalosempresarios">Consulta - Regalos empresarios</option>
				<option value="wineteam">Consulta - Wine team</option>
				<option value="otros">Consulta - Otros</option>
				<option value="confirmevento">Fidelizaci�n - Confirmaci�n a un evento</option>
				<option value="canjepremio">Fidelizaci�n - Canje de premio</option>
				<option value="asistevento">Fidelizaci�n - Asistencia a un evento</option>
				<option value="datosreferidos">Fidelizaci�n - Datos de referidos</option>
				<option value="encuesta">Fidelizaci�n - Encuesta</option>
				<option value="baja">Gesti�n - Pedido de Baja</option>
				<option value="suspension">Gesti�n - Pedido de Suspensi�n</option>
				<option value="adicionalcambio">Gesti�n - Pedido Adicional / cambio</option>
				<option value="actdatos">Gesti�n - Actualizaci�n de datos personales</option>
			</select>		</td>
		<td class="fila-det-border"><div align="right">
			  <select name="motivosaliente" disabled="disabled">
			    <option value="motivoS" selected="selected">Motivo de contacto</option>
			    <option value="confirmevento">Fidelizaci�n - Confirmaci�n a un evento</option>
			    <option value="canjepremiosaliente">Fidelizaci�n - Canje de premio</option>
			    <option value="datosreferidossalido">Fidelizaci�n - Datos de referidos</option>
			    <option value="encuestasalida">Fidelizaci�n - Encuesta</option>
			    <option value="enviopremio">Fidelizaci�n - Env�o de Premio</option>
			    <option value="combeneficio">Fidelizaci�n - Comunicaci�n de beneficio</option>
			    <option value="comgeneral">Fidelizaci�n - Comunicaci�n general</option>
			    <option value="bajasalida">Retenci�n - Pedido de baja</option>
			    <option value="suspensionsalida">Retenci�n - Pedido de suspensi�n</option>
			    <option value="novelogistica">Gesti�n - Novedad log�stica</option>
			    <option value="vtatelemarketing">Gesti�n - Venta Telemarketing</option>
			    <option value="cobranza">Gesti�n - Cobranza</option>
			    <option value="actdatossalida">Gesti�n - Actualizaci�n de datos personales</option>
			    <option value="otrossalida">Gesti�n - Otros</option>
		      </select>
            </div></td>
	</tr>
	<tr class="fila-det">
	<td class="fila-det-border" colspan="2">
		<textarea name="descripcion" cols="100" class="campo"></textarea>
	</td>
	<tr class="fila-det">
		<td class="fila-det-border" colspan="2" align="center">&nbsp;
			<input name="validar" type="submit" value="Enviar" class="boton"  >
			<input name="volver" type="submit" class="boton" id="volver" value="Volver">
		</td>
	</tr>
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

