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
	      <option value="logistica">Logística (Novedad)</option>
	      <option value="correoexterno">Correo (externo)</option>
	      <option value="paginaweb">Página web</option>
	      <option value="micomercial">Memo Interno Comercial</option>
	      <option value="mitelemarketing">Memo Interno Telemarketing</option>
	      <option value="micobranzas">Memo Interno Cobranzas</option>
	      <option value="mirecepcion">Memo Interno Recepción</option>
	      </select>
      </div></td>
		<td class="fila-det-border"><div align="right">
		  <select name="canalsaliente" disabled="disabled">
		    <option value="salienteC" selected="selected">Canal de contacto saliente</option>
		    <option value="emailsaliente">Email</option>
		    <option value="telefonosaliente">Teléfono</option>
		    <option value="distribucionbaco">Distribución Baco</option>
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
				<option value="facturacion">Consulta - Facturación</option>
				<option value="regalosempresarios">Consulta - Regalos empresarios</option>
				<option value="wineteam">Consulta - Wine team</option>
				<option value="otros">Consulta - Otros</option>
				<option value="confirmevento">Fidelización - Confirmación a un evento</option>
				<option value="canjepremio">Fidelización - Canje de premio</option>
				<option value="asistevento">Fidelización - Asistencia a un evento</option>
				<option value="datosreferidos">Fidelización - Datos de referidos</option>
				<option value="encuesta">Fidelización - Encuesta</option>
				<option value="baja">Gestión - Pedido de Baja</option>
				<option value="suspension">Gestión - Pedido de Suspensión</option>
				<option value="adicionalcambio">Gestión - Pedido Adicional / cambio</option>
				<option value="actdatos">Gestión - Actualización de datos personales</option>
			</select>		</td>
		<td class="fila-det-border"><div align="right">
			  <select name="motivosaliente" disabled="disabled">
			    <option value="motivoS" selected="selected">Motivo de contacto</option>
			    <option value="confirmevento">Fidelización - Confirmación a un evento</option>
			    <option value="canjepremiosaliente">Fidelización - Canje de premio</option>
			    <option value="datosreferidossalido">Fidelización - Datos de referidos</option>
			    <option value="encuestasalida">Fidelización - Encuesta</option>
			    <option value="enviopremio">Fidelización - Envío de Premio</option>
			    <option value="combeneficio">Fidelización - Comunicación de beneficio</option>
			    <option value="comgeneral">Fidelización - Comunicación general</option>
			    <option value="bajasalida">Retención - Pedido de baja</option>
			    <option value="suspensionsalida">Retención - Pedido de suspensión</option>
			    <option value="novelogistica">Gestión - Novedad logística</option>
			    <option value="vtatelemarketing">Gestión - Venta Telemarketing</option>
			    <option value="cobranza">Gestión - Cobranza</option>
			    <option value="actdatossalida">Gestión - Actualización de datos personales</option>
			    <option value="otrossalida">Gestión - Otros</option>
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

