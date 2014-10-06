<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/*  
   Formulario de carga para la entidad: crmindividuos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jun 14 17:23:23 GMT-03:00 2007 
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
<jsp:useBean id="BCFB"  class="ar.com.syswarp.web.ejb.BeanCrmindividuosxusuarioBuscar"   scope="page"/>
<head>
 <jsp:setProperty name="BCFB" property="*" />
 <title>FRMCrmindividuos.jsp</title>
 <meta http-equiv="description" content="mypage">
  
 <link rel="stylesheet" href="<%=pathskin%>">
  <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>
 <script>
	function validarFiltro(){
		 
			var i = 0;
			var objSeleccion = document.frmBusqueda.seleccion;
			var objCampo = "";
			var filtro = "";
			var totalFechaCotiza = 0;
			var totalFechaLlamada = 0;
			var totalFechaVisita = 0;
			var existeSeleccion = false;
			var paramUrl = "";
			var seleccionados = "" ;
			
			for(i=0;i<objSeleccion.length;i++){
				
				if(objSeleccion[i].checked){
					objCampo = eval("document.frmBusqueda." + objSeleccion[i].value );
					if(trim(objCampo.value) == ""){
						alert("Seleccionó filtrar por " + objSeleccion[i].alt + ", ingrese valor para el criterio.");
						return false;
					}
					
					if(objSeleccion[i].title == "") {		  
						filtro += "ENTITY." + objSeleccion[i].value + "&" + " '" + objCampo.value.toUpperCase() + "' " + "#";
					}else if(objSeleccion[i].title == "como") {		  
						filtro += "ENTITY." + objSeleccion[i].value + "!" + " '" + objCampo.value.toUpperCase() + "%' " + "#";
					}else if(objSeleccion[i].title == "pariente") {	
						eval("parent.document.forms[0]." + objSeleccion[i].value + ".value= document.frmBusqueda." +  objSeleccion[i].value + ".value" ) ;		 
					}else if(objSeleccion[i].title == "fecha"){
						var fecha = (objCampo.value.substring(6) + "-" + objCampo.value.substring(3, 5) + "-" + objCampo.value.substring(0, 2) );
						if(!validarObjFecha(objCampo)){
							return false;
						}		    
						filtro += "ENTITY." + objSeleccion[i].value + "&"+ " '" + fecha + "' " + "#";
					
					}else if(objSeleccion[i].title == "fechaentre"){
						var fecha = (objCampo.value.substring(6) + "-" + objCampo.value.substring(3, 5) + "-" + objCampo.value.substring(0, 2) );
						if(!validarObjFecha(objCampo)){
							return false;
						}
						if(objSeleccion[i].value.indexOf("cotizacion"))totalFechaCotiza +=1;
						if(objSeleccion[i].value.indexOf("llamado"))totalFechaLlamada +=1;
						if(objSeleccion[i].value.indexOf("visita"))totalFechaVisita +=1;
						eval("parent.document.forms[0]." + objSeleccion[i].value + ".value= document.frmBusqueda." +  objSeleccion[i].value + ".value" ) ;		 
					}else{
						filtro += "ENTITY." + objSeleccion[i].value + "&" + objCampo.value.toUpperCase() + "#";
					}
					
					if(!existeSeleccion){
						paramUrl+="?" + objSeleccion[i].value + "=" + objCampo.value.toUpperCase();
					seleccionados = i;
					}else{
						paramUrl+="&" + objSeleccion[i].value + "=" + objCampo.value.toUpperCase();
						seleccionados += ";" + i;
					}
					
					if(objSeleccion[i].value.indexOf("id") == 0) 
						paramUrl+="&" + objSeleccion[i].value.substring(2) + "=" + eval("document.frmBusqueda." +  objSeleccion[i].value.substring(2) + ".value");
					
					existeSeleccion = true;
				}
			}
			
			if(totalFechaCotiza==1){
				alert("Es necesario ingresar fecha cotización desde - hasta.");
				return false;
			}
				
			if(totalFechaLlamada==1){
				alert("Es necesario ingresar fecha llamada desde - hasta.");
				return false;
			}
			
			if(totalFechaVisita==1){
				alert("Es necesario ingresar fecha visita desde - hasta.");
				return false;
			}
		
			
			if(!existeSeleccion){
				alert("Es necesario seleccionar algún filtro.");
				return false;
			}
			
			paramUrl += "&seleccionados=" + seleccionados;
			parent.document.forms[0].paramUrl.value = paramUrl;
			parent.document.forms[0].ocurrencia.value = "OK";
			parent.document.forms[0].filtro.value = filtro;
			parent.document.forms[0].submit();
		 
	}
   
   function setSeleccion(){
     var seleccion = new Array();
	   seleccion = "<%= BCFB.getSeleccionados() %>".split(";");
     for(var i=0;i<seleccion.length&&seleccion[0]!="";i++){
	     document.frmBusqueda.seleccion[seleccion[i]].checked = true;
	   }
   }
   
   function limpiarAll(){
     var objSeleccion = document.frmBusqueda.seleccion;
     for(var i=0;i<objSeleccion.length;i++){
	   objSeleccion[i].checked = false;
	   eval("document.frmBusqueda." + objSeleccion[i].value + ".value = ''" );
	   if(objSeleccion[i].value.indexOf("id") == 0) 
         eval("document.frmBusqueda." + objSeleccion[i].value.substring(2) + ".value = ''" );
	   }
   }
   
   function limpiarUno(obj){
     if(obj.checked == false){
	   obj.checked = false;
	   eval("document.frmBusqueda." + obj.value + ".value = ''" );
	   if(obj.value.indexOf("id") == 0) 
         eval("document.frmBusqueda." + obj.value.substring(2) + ".value = ''" );  
	 	 }   
   }
   
 </script>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>

 <% 
 String titulo =  " Busqueda " ;
 /*
 BCFB.setResponse(response);
 BCFB.setRequest(request);
 BCFB.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCFB.setUsuarioact( session.getAttribute("usuario").toString() );
 BCFB.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));   
 BCFB.ejecutarValidacion();
 */  
 %>
<form action="buscarIndividuosXusuario.jsp" method="post" name="frmBusqueda">
<input name="accion" type="hidden" value="<%=BCFB.getAccion()%>" >
<input name="idindividuos" type="hidden" value="<%=BCFB.getIdindividuos()%>" >

            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr class="fila-det-bold-rojo">
                <td class="fila-det-border"><input type="button" name="Submit" value="Aplicar Filtro" onClick="validarFiltro();" class="boton"></td>
                <td class="fila-det-border">&nbsp;</td> 
                <td class="fila-det-border"><input name="limpiar" type="button" class="boton" id="limpiar" onClick="limpiarAll();" value="Limpiar Todo"></td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td colspan="2" class="fila-det-border"><jsp:getProperty name="BCFB" property="mensaje"/>&nbsp;</td> 
              </tr> 
              <tr class="fila-det">
                <td width="15%" bgcolor="#F3F3F3" class="fila-det-border">Razon nombre:</td>
                <td width="2%" bgcolor="#F3F3F3" class="fila-det-border">
                  
                  <div align="right">
                    <input type="checkbox" name="seleccion" value="razon_nombre" class="campo" alt="Razón Nombre" onClick="limpiarUno(this);" >
                  </div></td> 
                <td bgcolor="#F3F3F3" class="fila-det-border"><input name="razon_nombre" type="text" value="<%=str.esNulo(BCFB.getRazon_nombre())%>" class="campo" size="30" maxlength="100"  ></td>
                <td width="3%" bgcolor="#F3F3F3" class="fila-det-border">&nbsp;</td>
                <td width="15%" bgcolor="#F3F3F3" class="fila-det-border">Deportes: </td>
                <td width="2%" bgcolor="#F3F3F3" class="fila-det-border"><div align="right">
                    <input type="checkbox" name="seleccion" value="deportes" class="campo" alt="Deportes" onClick="limpiarUno(this);" >
                </div></td>
                <td colspan="2" bgcolor="#F3F3F3" class="fila-det-border"><input name="deportes" type="text" value="<%=str.esNulo(BCFB.getDeportes())%>" class="campo" size="30" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">   
                <td width="15%" bgcolor="#EEF5F9" class="fila-det-border">Telefonos particular:  </td>
                <td width="2%" bgcolor="#EEF5F9" class="fila-det-border"><div align="right">
                  <input type="checkbox" name="seleccion" value="telefonos_part" class="campo" alt="Telefonos Particular" onClick="limpiarUno(this);">
                </div></td>
                <td bgcolor="#EEF5F9" class="fila-det-border"><input name="telefonos_part" type="text" class="campo" value="<%=str.esNulo(BCFB.getTelefonos_part())%>" size="30" maxlength="40"></td>
                <td width="3%" bgcolor="#EEF5F9" class="fila-det-border">&nbsp;</td>
                <td width="15%" bgcolor="#EEF5F9" class="fila-det-border">Hobbies: </td>
                <td width="2%" bgcolor="#EEF5F9" class="fila-det-border"><div align="right">
                    <input type="checkbox" name="seleccion" value="hobbies" class="campo" alt="Hobbies" onClick="limpiarUno(this);" >
                </div></td>
                <td colspan="2" bgcolor="#EEF5F9" class="fila-det-border"><input name="hobbies" type="text" value="<%=str.esNulo(BCFB.getHobbies())%>" class="campo" size="30" maxlength="100" ></td>
              </tr>
              <tr class="fila-det">
                <td width="15%" bgcolor="#F3F3F3" class="fila-det-border">Celular:  </td>
                <td width="2%" bgcolor="#F3F3F3" class="fila-det-border"><div align="right">
                  <input type="checkbox" name="seleccion" value="celular" class="campo" alt="Celular" onClick="limpiarUno(this);">
                </div></td>
                <td bgcolor="#F3F3F3" class="fila-det-border"><input name="celular" type="text" value="<%=str.esNulo(BCFB.getCelular())%>" class="campo" size="30" maxlength="50"  ></td>
                <td width="3%" bgcolor="#F3F3F3" class="fila-det-border">&nbsp;</td>
                <td width="15%" bgcolor="#F3F3F3" class="fila-det-border">Actividad social: </td>
                <td width="2%" bgcolor="#F3F3F3" class="fila-det-border"><div align="right">
                    <input type="checkbox" name="seleccion" value="actividad_social" class="campo" alt="Actividad Social" onClick="limpiarUno(this);" >
                </div></td>
                <td colspan="2" bgcolor="#F3F3F3" class="fila-det-border"><input name="actividad_social" type="text" value="<%=str.esNulo(BCFB.getActividad_social())%>" class="campo" size="30" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="15%" bgcolor="#EEF5F9" class="fila-det-border">E-mail:  </td>
                <td width="2%" bgcolor="#EEF5F9" class="fila-det-border"><div align="right">
                  <input type="checkbox" name="seleccion" value="email" class="campo" alt="E-Mail" onClick="limpiarUno(this);" >
                </div></td>
                <td bgcolor="#EEF5F9" class="fila-det-border"><input name="email" type="text" value="<%=str.esNulo(BCFB.getEmail())%>" class="campo" size="30" maxlength="50"  ></td>
                <td width="3%" bgcolor="#EEF5F9" class="fila-det-border">&nbsp;</td>
                <td width="15%" bgcolor="#EEF5F9" class="fila-det-border">Diario lectura: </td>
                <td width="2%" bgcolor="#EEF5F9" class="fila-det-border"><div align="right">
                    <input type="checkbox" name="seleccion" value="diario_lectura" class="campo" alt="Diario de Lectura" onClick="limpiarUno(this);" >
                </div></td>
                <td colspan="2" bgcolor="#EEF5F9" class="fila-det-border"><input name="diario_lectura" type="text" value="<%=str.esNulo(BCFB.getDiario_lectura())%>" class="campo" size="30" maxlength="30"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="15%" bgcolor="#F3F3F3" class="fila-det-border">Domicilio particular:  </td>
                <td width="2%" bgcolor="#F3F3F3" class="fila-det-border"><div align="right">
                  <input type="checkbox" name="seleccion" value="domicilio_part" class="campo" alt="Domicilio Particular" onClick="limpiarUno(this);" >
                </div></td>
                <td bgcolor="#F3F3F3" class="fila-det-border"><input name="domicilio_part" type="text" class="campo" value="<%=str.esNulo(BCFB.getDomicilio_part())%>" size="30" maxlength="70"></td>
                <td width="3%" bgcolor="#F3F3F3" class="fila-det-border">&nbsp;</td>
                <td width="15%" bgcolor="#F3F3F3" class="fila-det-border">Revista lectura: </td>
                <td width="2%" bgcolor="#F3F3F3" class="fila-det-border"><div align="right">
                    <input type="checkbox" name="seleccion" value="revista_lectura" class="campo"  alt="Revista de Lectura" onClick="limpiarUno(this);" >
                </div></td>
                <td colspan="2" bgcolor="#F3F3F3" class="fila-det-border"><input name="revista_lectura" type="text" value="<%=str.esNulo(BCFB.getRevista_lectura())%>" class="campo" size="30" maxlength="30"  ></td>
              </tr>
              <tr class="fila-det">
                <td bgcolor="#EEF5F9" class="fila-det-border">F. Cotizaci&oacute;n Desde: </td>
                <td bgcolor="#EEF5F9" class="fila-det-border"><div align="right">
                    <input name="seleccion" type="checkbox" class="campo" id="seleccion"  value="fechacotizaciondesde" alt="Fecha Cotizaci&oacute;n Desde" onClick="limpiarUno(this);">
                </div></td>
                <td bgcolor="#EEF5F9" class="fila-det-border"><input name="fechacotizaciondesde" type="text" class="cal-TextBox" id="fechacotizaciondesde"  value="<%=BCFB.getFechacotizaciondesde()%>" size="12" maxlength="10" >
                    dd/mm/yyyy</td>
                <td width="3%" bgcolor="#EEF5F9" class="fila-det-border">&nbsp;</td>
              <td bgcolor="#EEF5F9" class="fila-det-border">F. Cotizaci&oacute;n Hasta: </td>
              <td bgcolor="#EEF5F9" class="fila-det-border"><div align="right">
                  <input name="seleccion" type="checkbox" class="campo" id="seleccion"  value="fechacotizacionhasta" alt="Fecha Cotizaci&oacute;n Hasta" onClick="limpiarUno(this);">
              </div></td>
              <td colspan="2" bgcolor="#EEF5F9" class="fila-det-border"><input name="fechacotizacionhasta" type="text" class="cal-TextBox" id="fechacotizacionhasta"  value="<%=BCFB.getFechacotizacionhasta()%>" size="12" maxlength="10" >
                  dd/mm/yyyy</td>
              </tr>
              <tr class="fila-det">
                <td bgcolor="#F3F3F3" class="fila-det-border">F. Llamado Desde: </td>
                <td bgcolor="#F3F3F3" class="fila-det-border"><div align="right">
                    <input name="seleccion" type="checkbox" class="campo" id="seleccion"  value="fechallamadodesde" alt="Fecha Llamado Desde" onClick="limpiarUno(this);">
                </div></td>
                <td bgcolor="#F3F3F3" class="fila-det-border"><input name="fechallamadodesde" type="text" class="cal-TextBox" id="fechallamadodesde"  value="<%=BCFB.getFechallamadodesde()%>" size="12" maxlength="10" >
                    dd/mm/yyyy</td>
                <td width="3%" bgcolor="#F3F3F3" class="fila-det-border">&nbsp;</td>
                <td bgcolor="#F3F3F3" class="fila-det-border">F. Llamado Hasta </td>
                <td bgcolor="#F3F3F3" class="fila-det-border"><div align="right">
                    <input name="seleccion" type="checkbox" class="campo" id="seleccion"  value="fechallamadohasta" alt="Fecha LLamado Hasta" onClick="limpiarUno(this);">
                </div></td>
                <td colspan="2" bgcolor="#F3F3F3" class="fila-det-border"><input name="fechallamadohasta" type="text" class="cal-TextBox" id="fechallamadohasta"  value="<%=BCFB.getFechallamadohasta()%>" size="12" maxlength="10" >
                    dd/mm/yyyy</td>
              </tr>
              <tr class="fila-det">
                <td bgcolor="#EEF5F9" class="fila-det-border">Actividad: </td>
                <td bgcolor="#EEF5F9" class="fila-det-border"><input type="checkbox" name="seleccion" value="actividad" class="campo" alt="Actividad" onClick="limpiarUno(this);" ></td>
                <td bgcolor="#EEF5F9" class="fila-det-border"><input name="actividad" type="text" value="<%=str.esNulo(BCFB.getActividad())%>" class="campo" size="30" maxlength="50"  ></td>
                <td width="3%" bgcolor="#EEF5F9" class="fila-det-border">&nbsp;</td>
                <td width="15%" bgcolor="#EEF5F9" class="fila-det-border">Fuente: </td>
                <td width="2%" bgcolor="#EEF5F9" class="fila-det-border"><div align="right">
                    <input type="checkbox" name="seleccion" value="idfuente" class="campo"  alt="Fuente"  title="numerico" onClick="limpiarUno(this);">
                </div></td>
                <td width="18%" bgcolor="#EEF5F9" class="fila-det-border"><input name="fuente" type="text" class="campo" id="fuente" value="<%=str.esNulo(BCFB.getFuente())%>" size="30" readonly></td>
                <td width="16%" bgcolor="#EEF5F9" class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../CRM/lov_fuente.jsp', 'fuente', 700, 400)" style="cursor:pointer">
                    <input name="idfuente" type="hidden" id="idfuente" value="<%=BCFB.getIdfuente()%>"></td>
              </tr>
              <tr class="fila-det">
                <td bgcolor="#F3F3F3" class="fila-det-border">Empresa: </td>
                <td bgcolor="#F3F3F3" class="fila-det-border"><input type="checkbox" name="seleccion" value="empresa" class="campo" alt="Empresa" onClick="limpiarUno(this);" ></td>
                <td bgcolor="#F3F3F3" class="fila-det-border"><input name="empresa" type="text" value="<%=str.esNulo(BCFB.getEmpresa())%>" class="campo" size="30" maxlength="50"  ></td>
                <td width="3%" bgcolor="#F3F3F3" class="fila-det-border">&nbsp;</td>
                <td bgcolor="#F3F3F3" class="fila-det-border">Clasificacion cliente: </td>
                <td bgcolor="#F3F3F3" class="fila-det-border"><div align="right">
                    <input type="checkbox" name="seleccion" value="idclasificacioncliente" class="campo"  alt="Clasificacion Cliente"  title="numerico" onClick="limpiarUno(this);">
                </div></td>
                <td bgcolor="#F3F3F3" class="fila-det-border"><input name="clasificacioncliente" type="text" class="campo" id="clasificacioncliente" value="<%=str.esNulo(BCFB.getClasificacioncliente())%>" size="30" readonly></td>
                <td bgcolor="#F3F3F3" class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../CRM/lov_clasificacionclientes.jsp', 'clasificacioncliente', 700, 400)" style="cursor:pointer">
                    <input name="idclasificacioncliente" type="hidden" id="idclasificacioncliente" value="<%=BCFB.getIdclasificacioncliente()%>"></td>
              </tr>
              <tr class="fila-det">
                <td bgcolor="#EEF5F9" class="fila-det-border">Profesion: </td>
                <td bgcolor="#EEF5F9" class="fila-det-border"><div align="right">
                    <input type="checkbox" name="seleccion" value="profesion" class="campo" alt="Profesion" onClick="limpiarUno(this);" >
                </div></td>
                <td bgcolor="#EEF5F9" class="fila-det-border"><input name="profesion" type="text" value="<%=str.esNulo(BCFB.getProfesion())%>" class="campo" size="30" maxlength="50"  ></td>
                <td bgcolor="#EEF5F9" class="fila-det-border">&nbsp;</td>
                <td bgcolor="#EEF5F9" class="fila-det-border">Tipo cliente: </td>
                <td bgcolor="#EEF5F9" class="fila-det-border"><div align="right">
                    <input type="checkbox" name="seleccion" value="idtipocliente" class="campo" alt="Tipo de Cliente" title="numerico" onClick="limpiarUno(this);">
                </div></td>
                <td bgcolor="#EEF5F9" class="fila-det-border"><input name="tipocliente" type="text" class="campo" id="tipocliente" value="<%=str.esNulo(BCFB.getTipocliente())%>" size="30" readonly></td>
                <td bgcolor="#EEF5F9" class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../CRM/lov_tiposclientes.jsp', 'tipoclientes', 700, 400)" style="cursor:pointer">
                    <input name="idtipocliente" type="hidden" id="idtipocliente" value="<%=BCFB.getIdtipocliente()%>"></td>
              </tr>
              <tr class="fila-det">
                <td bgcolor="#F3F3F3" class="fila-det-border">F. Nacimiento: </td>
                <td bgcolor="#F3F3F3"   class="fila-det-border" ><div align="right">
                    <input type="checkbox" name="seleccion" value="fechanacimiento" class="campo" alt="Fecha Nacimiento"  title="fecha" onClick="limpiarUno(this);">
                </div></td>
                <td bgcolor="#F3F3F3"   class="fila-det-border" ><input name="fechanacimiento" type="text" class="cal-TextBox" id="fechanacimiento"  value="<%=BCFB.getFechanacimiento()%>" size="12" maxlength="12" >
                    dd/mm/yyyy</td>
                <td bgcolor="#F3F3F3" class="fila-det-border">&nbsp;</td>
                <td bgcolor="#F3F3F3" class="fila-det-border"> Pariente/Acompa&ntilde;ante </td>
                <td bgcolor="#F3F3F3" class="fila-det-border"><input type="checkbox" name="seleccion" value="nombrepariente" class="campo" alt="Nombre Pariente" title="pariente" onClick="limpiarUno(this);" ></td>
                <td bgcolor="#F3F3F3" class="fila-det-border"><input name="nombrepariente" type="text" value="<%=str.esNulo(BCFB.getNombrepariente())%>" class="campo" size="30" maxlength="100"  ></td>
                <td bgcolor="#F3F3F3" class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td bgcolor="#EEF5F9" class="fila-det-border">F. Visita Desde: </td>
                <td bgcolor="#EEF5F9" class="fila-det-border"><div align="right">
                    <input name="seleccion" type="checkbox" class="campo" id="seleccion"  value="fechavisitadesde" alt="Fecha Visita Desde" onClick="limpiarUno(this);" />
                </div></td>
                <td bgcolor="#EEF5F9" class="fila-det-border"><input name="fechavisitadesde" type="text" class="cal-TextBox" id="fechavisitadesde"  value="<%=BCFB.getFechavisitadesde()%>" size="12" maxlength="10">
                dd/mm/yyyy</td>
                <td bgcolor="#EEF5F9" class="fila-det-border">&nbsp;</td>
                <td bgcolor="#EEF5F9" class="fila-det-border">F. Visita Hasta </td> 
                <td bgcolor="#EEF5F9" class="fila-det-border"><div align="right">
                    <input name="seleccion" type="checkbox" class="campo" id="seleccion"  value="fechavisitahasta" alt="Fecha Visita Hasta" onClick="limpiarUno(this);" />
                </div></td>
                <td colspan="2" bgcolor="#EEF5F9" class="fila-det-border"><input name="fechavisitahasta" type="text" class="cal-TextBox" id="fechavisitahasta"  value="<%=BCFB.getFechavisitahasta()%>" size="12" maxlength="10">
                dd/mm/yyyy</td>
              </tr>
              <tr class="fila-det">
                <td bgcolor="#F3F3F3" class="fila-det-border">Datos Vehiculo </td> 
                <td bgcolor="#F3F3F3"   class="fila-det-border" ><input name="seleccion" type="checkbox" class="campo" id="seleccion"  onClick="limpiarUno(this);" value="datosvehiculo" alt="Datos Vehiculo"></td> 
                <td bgcolor="#F3F3F3"   class="fila-det-border" ><input name="datosvehiculo" type="text" class="campo" id="datosvehiculo" value="<%=str.esNulo(BCFB.getDatosvehiculo() )%>" size="30" maxlength="100"  ></td>
                <td bgcolor="#F3F3F3" class="fila-det-border">&nbsp;</td>
                <td bgcolor="#F3F3F3" class="fila-det-border">&nbsp;</td>
                <td bgcolor="#F3F3F3" class="fila-det-border">&nbsp;</td>
                <td bgcolor="#F3F3F3" class="fila-det-border">&nbsp;</td>
                <td bgcolor="#F3F3F3" class="fila-det-border">&nbsp;</td>
              </tr>
          </table>
		  <script>setSeleccion();</script> 

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

