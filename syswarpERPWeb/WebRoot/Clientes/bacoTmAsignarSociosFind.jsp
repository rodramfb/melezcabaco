<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: 
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Nov 14 14:50:18 GMT-03:00 2006 
   Observaciones: 
      .


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.Iterator" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%@ page import="java.math.BigDecimal" %>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "TELEMARKETING - Asignación de Socios Por Telemarketer ";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterador   = null;
int totCol = 2; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BBTASF"  class="ar.com.syswarp.web.ejb.BeanBacoTmAsignarSociosFind"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BBTASF" property="*" />
<%
 BBTASF.setResponse(response);
 BBTASF.setRequest(request);
 BBTASF.setUsuarioalt( usuario ); 
 BBTASF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BBTASF.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 <link rel="stylesheet" type="text/css" href="<%=pathscript%>/calendar/calendar.css"> 
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>
 <script>

 <% 
  iterador = BBTASF.getListSubOrigen().iterator();
  String salida = ""  ;
  String array = "";
  String suborigen = "";

  salida += "\n/*--------------- Origen / Suborigen ----------------*/\n";   

  while(iterador.hasNext()){
	String[] datos = (String[]) iterador.next();
    if(!suborigen.equalsIgnoreCase(datos[2])){
      array  = "suborigen_" + datos[2];
      salida += "var " + array + " = new Array(); \n";
      suborigen=datos[2];
    }
    salida += array + "['" + datos[0] + "']= '"  +  datos[1] + "'; \n";
  }

  salida += "\n/*--------------- Estados / Motivos ----------------*/\n";   

  iterador = BBTASF.getListMotivos().iterator();
  String motivo = "";
  while(iterador.hasNext()){
	String[] datos = (String[]) iterador.next();
    if(!motivo.equalsIgnoreCase(datos[2])){
      array  = "motivo_" + datos[2];
      salida += "var " + array + " = new Array(); \n";
      motivo=datos[2];
    }
    salida += array + "['" + datos[0] + "']= '"  +  datos[1] + "'; \n";
  }
  salida = "\n" + salida.trim();
  out.write(salida); 



 %>


	function poblarObjList(req_id, valorid,  idObjList, nameArray){
		var lista = document.getElementById(idObjList);
		lista.options.length = 1; 
		if(valorid != '-1'){
			var objArray = eval(nameArray + valorid);
			for (var i in objArray){
				lista.options[lista.options.length] = new Option(   objArray[i]   , i);
				if(i == req_id ){ 
          lista.options[lista.options.length-1].selected=true};
			}
		}
	}

  function getLocalidad(){
   var objProv = document.getElementById('idprovincia');
   var idprovincia = objProv.options[objProv.selectedIndex].value;
   if(idprovincia!='-1')
     abrirVentana('lov_localidadesProvincia.jsp?idprovincia='+idprovincia,'localidades',800, 450);
   else 
     alert('Es necesario seleccionar provincia.');
  }

  function clearLocalidad(){
    document.getElementById('idlocalidad').value = '';
    document.getElementById('localidad').value = '';
  }

  function setAccion(accion){
   document.getElementById('accion').value = accion;
   document.frm.submit();
  }

  window.onload = function () {
    document.getElementById('idprovincia').onchange = clearLocalidad;
		poblarObjList(<%= BBTASF.getIdsuborigen() %>, document.getElementById('idorigen').value, 'idsuborigen', 'suborigen_');
		poblarObjList(<%= BBTASF.getIdmotivo() %>, document.getElementById('idestado').value, 'idmotivo', 'motivo_');

  }  
  function borrarcampania(){
	document.frm.idcampacabe.selectedIndex = -1;	
	document.frm.submit();
  }
  function borrarcampaniaAnterior(){
	document.frm.idcampacabeAnterior.selectedIndex = -1;	
	document.frm.submit();
  }

  

 </script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="popupcalendar" class="text"></div>
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="bacoTmAsignarSociosFind.jsp" method="POST" name="frm">
<table width="100%" border="2" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr >
    <td width="100%" height="24" colspan="10" >
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="44"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                   <td  class="text-globales">
										<table width="100%"  border="1" cellspacing="0" cellpadding="0">
											<tr >
											 <td height="28" class="fila-det-bold-rojo"><jsp:getProperty name="BBTASF" property="mensaje"/></td>
											</tr>
										</table>
									</td>
                </tr>
                <tr>
                  <td width="10%" height="38">
                  <table width="100%" border="1" cellspacing="0" cellpadding="0" class="text-dos-bold">
                    <tr class="subtitulo-tres" >
                      <td height="30" colspan="4" >Datos de Campa&ntilde;a  - Asignaci&oacute;n </td>
                    </tr>
	
					
                    <tr >
                      <td width="19%" height="30" >Campa&ntilde;a:</td>
                      <td width="36%">
                      <select name="idcampacabe" class="campo" id="idcampacabe" <%// EJV - 20100429 - Mantis 530 onChange = "borrarcampaniaAnterior();" %> style="width:120px">
                        <option value="-1">Seleccionar</option>
                        <%
                          iterador= BBTASF.getListCampaniasActivas().iterator(); 
                          while(iterador.hasNext()){
                            String datos[] = (String[]) iterador.next();%>
                            <option value="<%= datos[0] %>" <%= BBTASF.getIdcampacabe().toString().equals(datos[0]) ?  "selected" : "" %>><%= datos[1] %></option>
                        <%}  %>
                      </select></td>
                      <td width="15%" height="30">Telemarketer: </td>
                      <td width="30%"><select name="idtelemark" class="campo" id="idtelemark" style="width:120px">
                          <option value="-1">Seleccionar</option>
                        <%
                          iterador= BBTASF.getListTeleMarketer().iterator(); 
                          while(iterador.hasNext()){
                            String datos[] = (String[]) iterador.next();%>
                            <option value="<%= datos[0] %>" <%= BBTASF.getIdtelemark().toString().equals(datos[0]) ?  "selected" : "" %>><%= datos[4] %></option>
                        <%}  %>
                      </select></td>
                    </tr>
                  </table>
                </td>
              </tr>
                <tr>
                  <td height="38">
                   <table width="100%" border="1" cellspacing="0" cellpadding="0" class="text-dos-bold">
                    <tr class="subtitulo-tres" >
                      <td height="30" colspan="5" >Criterios de Busqueda - Filtros </td>
                    </tr>
                    <tr >
                      <td height="30" >Campa&ntilde;a  No Asignada:</td>
                      <td bgcolor="#FFFFCC"><select name="idcampacabeAnterior" class="campo" id="idcampacabeAnterior" <%// EJV - 20100429 - Mantis 530 onChange = "borrarcampania();" %> style="width:120px" >
                        <option value="-1">Seleccionar</option>
                        <%
                          iterador= BBTASF.getListCampaniasAnteriores().iterator(); 
                          while(iterador.hasNext()){
                            String datos[] = (String[]) iterador.next();%>
                        <option value="<%= datos[0] %>" <%= BBTASF.getIdcampacabeAnterior().toString().equals(datos[0]) ?  "selected" : "" %>><%= datos[1] %></option>
                        <%}  %>
                      </select></td>
                      <td>&nbsp;</td>
                      <td colspan="2" bgcolor="#FFFFCE">&nbsp;</td>
                     </tr>
                    <tr >
                      <td width="28%" height="30" >Provincia:</td>
                      <td width="30%" bgcolor="#FFFFCC">
                        <select name="idprovincia" class="campo" id="idprovincia" style="width:120px">
                          <option value="-1">Seleccionar</option>
                        <%
                          iterador= BBTASF.getListProvincias().iterator(); 
                          while(iterador.hasNext()){
                            String datos[] = (String[]) iterador.next();%>
                            <option value="<%= datos[0] %>" <%= BBTASF.getIdprovincia().toString().equals(datos[0]) ?  "selected" : "" %>><%= datos[1] %></option>
                        <%}  %>
                      </select>                      </td>
                      <td width="22%">Localidad:
                      <input name="idlocalidad" type="hidden" class="campo" id="idlocalidad" value="<%= BBTASF.getIdlocalidad() %>"  readonly></td>
                      <td width="11%" bgcolor="#FFFFCE"><input name="localidad" type="text" class="campo" id="localidad" value="<%= BBTASF.getLocalidad() %>" size="20" maxlength="100"  readonly></td>
                      <td width="9%" bgcolor="#FFFFCE"><img src="../imagenes/default/gnome_tango/actions/find.png" width="22" height="22" onClick="getLocalidad();" style="cursor:pointer"></td>
                    </tr>
                    <tr>
                      <td height="30">Socio Desde: </td>
                      <td bgcolor="#FFFFCC"><input name="idclientedesde" type="text" class="campo" id="idclientedesde" value="<%= BBTASF.getIdclientedesde() %>" size="10" maxlength="10"></td>
                      <td>Socio Hasta: </td>
                      <td colspan="2" bgcolor="#FFFFCE"><input name="idclientehasta" type="text" class="campo" id="idclientehasta" value="<%= BBTASF.getIdclientehasta() %>" size="10" maxlength="10"></td>
                    </tr>
                    <tr>
                      <td height="30">Origen:</td>
                      <td bgcolor="#FFFFCC">
                       <select name="idorigen" class="campo" id="idorigen" onChange="poblarObjList(<%= BBTASF.getIdsuborigen() %>, this.value, 'idsuborigen', 'suborigen_')" style="width:120px">
                          <option value="-1">Seleccionar</option>
                        <%
                          iterador= BBTASF.getListOrigen().iterator(); 
                          while(iterador.hasNext()){
                            String datos[] = (String[]) iterador.next();%>
                            <option value="<%= datos[0] %>" <%= BBTASF.getIdorigen().toString().equals(datos[0]) ?  "selected" : "" %>><%= datos[1] %></option>
                        <%}  %>
                      </select></td>
                      <td>Sub-Origen:</td>
                      <td colspan="2" bgcolor="#FFFFCE"><select name="idsuborigen" class="campo" id="idsuborigen" style="width:120px">
                          <option value="-1">Seleccionar</option>
                      </select></td>
                    </tr>
                    <tr>
                      <td height="30">Estado:</td>
                      <td bgcolor="#FFFFCC">
                       <select name="idestado" class="campo" id="idestado" onChange="poblarObjList(<%= BBTASF.getIdmotivo() %>, this.value, 'idmotivo', 'motivo_');" style="width:120px">
                          <option value="-1">Seleccionar</option>
                        <%
                          iterador= BBTASF.getListEstado().iterator(); 
                          while(iterador.hasNext()){
                            String datos[] = (String[]) iterador.next();%>
                            <option value="<%= datos[0] %>" <%= BBTASF.getIdestado().toString().equals(datos[0]) ?  "selected" : "" %>><%= datos[1] %></option>
                        <%}  %>
                      </select></td>
                      <td>Motivo:</td>
                      <td colspan="2" bgcolor="#FFFFCE"><select name="idmotivo" class="campo" id="idmotivo" style="width:120px">
                          <option value="-1">Seleccionar</option>
                      </select></td>
                    </tr>
                    <tr>
                      <td height="30">Tipo Cliente: </td>
                      <td bgcolor="#FFFFCC"><select name="idtipoclie" class="campo" id="idtipoclie" style="width:120px">
                          <option value="-1">Seleccionar</option>
                        <%
                          iterador= BBTASF.getListTipoCliente().iterator(); 
                          while(iterador.hasNext()){
                            String datos[] = (String[]) iterador.next();%>
                            <option value="<%= datos[0] %>" <%= BBTASF.getIdtipoclie().toString().equals(datos[0]) ?  "selected" : "" %>><%= datos[1] %></option>
                        <%}  %>
                      </select></td>
                      <td>Preferencia:</td>
                      <td colspan="2" bgcolor="#FFFFCE"><select name="idpreferencia" class="campo" id="idpreferencia" style="width:120px">
                          <option value="-1">Seleccionar</option>
                        <%
                          iterador= BBTASF.getListPreferencias().iterator(); 
                          while(iterador.hasNext()){
                            String datos[] = (String[]) iterador.next();%>
                            <option value="<%= datos[0] %>" <%= BBTASF.getIdpreferencia().toString().equals(datos[0]) ?  "selected" : "" %>><%= datos[1] %></option>
                        <%}  %>
                      </select></td>
                    </tr>
                    <tr>
                      <td height="30">Fecha Ingreso Desde </td>
                      <td bgcolor="#FFFFCC"><input name="fechaingresodesde" type="text" class="campo" id="fechaingresodesde" onFocus="this.blur()" value="<%= BBTASF.getFechaingresodesde() %>" size="12" maxlength="12" readonly>
                      <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date', 'img_Date_DOWN');showCalendar('frm','fechaingresodesde','BTN_date');return false;"> <img align="absmiddle" border="0" name="BTN_date" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a> </td>
                      <td>Fecha Ingreso Hasta: </td>
                      <td colspan="2" bgcolor="#FFFFCE"><input name="fechaingresohasta" type="text" class="campo" id="fechaingresohasta" onFocus="this.blur()" value="<%= BBTASF.getFechaingresohasta() %>" size="12" maxlength="12" readonly>
                      <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_1', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_1', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_1', 'img_Date_DOWN');showCalendar('frm','fechaingresohasta','BTN_date_1');return false;"> <img align="absmiddle" border="0" name="BTN_date_1" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a></span></td>
                    </tr>
                    <tr>
                      <td height="30">Categor&iacute;a:</td>
                      <td bgcolor="#FFFFCC"><select name="idcategoria" class="campo" id="idcategoria" style="width:120px">
                          <option value="-1">Seleccionar</option>
                          <%
                          iterador= BBTASF.getListCategoria().iterator(); 
                          while(iterador.hasNext()){
                            String datos[] = (String[]) iterador.next();%>
                          <option value="<%= datos[0] %>" <%= BBTASF.getIdcategoria().toString().equals(datos[0]) ?  "selected" : "" %>><%= datos[1] %></option>
                          <%}  %>
                      </select></td>
                      <td>Limite de Asignaci&oacute;n </td>
                      <td colspan="2" bgcolor="#FFFFCE"><input name="limiteasignacion" type="text" class="campo" id="limiteasignacion" value="<%= BBTASF.getLimiteasignacion() %>" size="10" maxlength="10"></td>
                    </tr>
                    <tr>
                      <td height="30" colspan="2"><div align="center">
                        <input name="consultar" type="button" class="boton" id="consultar" value="Consultar" onClick="setAccion(this.name)">
                      </div></td>
                      <td colspan="3"><div align="center">
                        <input name="asignar" type="button" class="boton" id="asignar" value="Asignar" onClick="setAccion(this.name)">
                      </div></td>
                     </tr>
                  </table></td>
                </tr>
          </table>
      </td>
    </tr>
  </table>

   <input name="accion" value="" type="hidden" id="accion">
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

