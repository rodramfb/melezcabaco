<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: bacoRefPrepro
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Jun 16 10:12:03 ART 2010 
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
<jsp:useBean id="BBRPF"  class="ar.com.syswarp.web.ejb.BeanBacoRefPreproFrm"   scope="page"/>
<head>
 <title></title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <script>
 function setReferenteFuente(idreferente){
   
   //if(idreferente == 0 || idreferente == ''){
     document.frm.idvendedor.value = '';
	 document.frm.vendedor.value = '';
   //}
    
   document.frm.idreferente.value = idreferente;
   document.frm.referente.value = '';
 
 }
 
 function validarFuente(){
   var idreferenteFuente = document.frm.idfuente.options[document.frm.idfuente.selectedIndex].lang;
   idreferenteFuente = idreferenteFuente == null || idreferenteFuente == '' ? -1 : idreferenteFuente;
   if(idreferenteFuente < 0) alert('Para poder seleccionar un referente es necesario seleccionar previamente fuente.');
   else if(idreferenteFuente > 0 ) alert('La fuente seleccionada ya posee un referente asociado por default, no es posible modificarlo.')
   else abrirVentana('../Clientes/lov_clientes.jsp', 'clie', 700, 400)   
 }
 
 function callBuscarProvincia (){
   document.frm.idlocalidad.value = -1;
   document.frm.localidad.value = '';
   abrirVentana('../Clientes/lov_provincia.jsp', 'prov', 700, 400) ;
 }
 
 function asignarVendedor(){
   var idreferenteFuente = document.frm.idfuente.options[document.frm.idfuente.selectedIndex].lang;
   idreferenteFuente = idreferenteFuente == null || idreferenteFuente == '' ? -1 : idreferenteFuente;
   if(idreferenteFuente < 0) alert('Para poder seleccionar un vendedor es necesario seleccionar previamente fuente.');
   else if(idreferenteFuente == 0) alert('No es posible asignar vendedor manualmente, \nel mismo corresponde al asociado al referente y se asigna automáticamente.');
   else abrirVentana('../Clientes/lov_vendedor.jsp', 'vende', 700, 400)
 
 }
 
 </script> 


</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BBRPF" property="*" />
 <% 
 String titulo = BBRPF.getAccion().toUpperCase() + " DE PREPOSPECTOS - REFERIDOS" ;
 BBRPF.setResponse(response);
 BBRPF.setRequest(request);
 BBRPF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BBRPF.setUsuarioact( session.getAttribute("usuario").toString() );
 BBRPF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BBRPF.ejecutarValidacion();
 %>
<form action="bacoRefPreproFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BBRPF.getAccion()%>" >
<input name="idpreprospecto" type="hidden" value="<%=BBRPF.getIdpreprospecto()%>" >
<input name="primeraCarga" type="hidden" id="primeraCarga" value="false" >
<input name="asignaVendedor" value="S" type="hidden">
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
                <td class="fila-det-border"><jsp:getProperty name="BBRPF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Nombre: (*) </td>
                <td width="83%" class="fila-det-border"><input name="nombre" type="text" value="<%=BBRPF.getNombre()%>" class="campo" size="35" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Apellido: (*) </td>
                <td width="83%" class="fila-det-border"><input name="apellido" type="text" value="<%=BBRPF.getApellido()%>" class="campo" size="35" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Fuente: (*) </td>
                <td class="fila-det-border">
                  <select name="idfuente" id="idfuente" class="campo" style="width:200px" onChange="setReferenteFuente(this.options[this.selectedIndex].lang)">
                      <option value="-1" >Seleccionar</option>
                      <% 
					  Iterator iter = BBRPF.getListFuente().iterator();
					  while(iter.hasNext()){
						String [] datos = (String[])iter.next();%>
                      <option value="<%= datos[0] %>" <%= datos[0].equals( BBRPF.getIdfuente().toString()) ? "selected" : "" %> lang="<%= datos[2]%>"><%= datos[1] %></option>
                      <%  
					  }%>
                  </select>				</td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Referente: (*) </td>
                <td width="83%" class="fila-det-border"><table width="35%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="60"><input name="idreferente" type="text" value="<%=BBRPF.getIdreferente()%>" class="campo" size="10" maxlength="100" readonly></td>
                    <td width="222"><input name="referente" type="text" class="campo" id="referente" value="<%=BBRPF.getReferente()%>" size="37" maxlength="100" readonly></td>
                    <td width="57"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="validarFuente()" style="cursor:pointer"> </td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Vendedor: (*) </td>
                <td width="83%" class="fila-det-border"><table width="50%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="62%"><input name="vendedor" type="text" class="campo" id="vendedor" value="<%=BBRPF.getVendedor()%>" size="50" maxlength="10" readonly ></td>
                      <td width="38%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="asignarVendedor()" style="cursor:pointer"> <input name="idvendedor" type="hidden" value="<%=BBRPF.getIdvendedor()%>" class="campo" size="10" maxlength="10"  ></td>
                    </tr>
                  </table></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Fecha: (*) </td>
                <td colspan="2" class="fila-det-border" >
                  <input class="cal-TextBox" onFocus="this.blur()" size="10" readonly type="text" name="fecha" value="<%=BBRPF.getFecha()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_7', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_7', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_7', 'img_Date_DOWN');showCalendar('frm','fecha','BTN_date_7');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_7" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>                </td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Telefono:  </td>
                <td width="83%" class="fila-det-border"><input name="telefono" type="text" value="<%=BBRPF.getTelefono()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Celular:  </td>
                <td width="83%" class="fila-det-border"><input name="celular" type="text" value="<%=BBRPF.getCelular()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Email:  </td>
                <td width="83%" class="fila-det-border"><input name="email" type="text" value="<%=BBRPF.getEmail()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Provincia:  </td>
                <td width="83%" class="fila-det-border"><table width="50%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="62%"><input name="provincia" type="text" class="campo" id="provincia" value="<%=BBRPF.getProvincia()%>" size="50" maxlength="50" readonly ></td>
                      <td width="38%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="callBuscarProvincia();" style="cursor:pointer" /> <input name="idprovincia" type="hidden" value="<%=BBRPF.getIdprovincia()%>" class="campo" size="10" maxlength="10" readonly></td>
                    </tr>
                  </table></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Localidad:  </td>
                <td width="83%" class="fila-det-border"><table width="50%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="62%"><input name="localidad" type="text" class="campo" id="localidad" value="<%=BBRPF.getLocalidad()%>" size="50" maxlength="50" readonly></td>
                      <td width="38%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_localidades.jsp', 'loca', 700, 400)" style="cursor:pointer" /> <input name="idlocalidad" type="hidden" value="<%=BBRPF.getIdlocalidad()%>" class="campo" size="10" maxlength="10" readonly></td>
                    </tr>
                  </table></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Estado:</td>
                <td class="fila-det-border">
				<select name="idrefestado" id="idrefestado" class="campo" style="width:200px" onChange="document.frm.submit()">
                  <option value="-1" >Seleccionar</option>
                  <% 
					  iter = BBRPF.getListRefEstados().iterator();
					  while(iter.hasNext()){
						String [] datos = (String[])iter.next();%>
                  <option value="<%= datos[0] %>" <%= datos[0].equals( BBRPF.getIdrefestado().toString()) ? "selected" : "" %> lang="<%= datos[2]%>"><%= datos[1] %></option>
                  <%  
					  }%>
                </select></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Sub Estado:</td>
                <td class="fila-det-border"><select name="idrefsubestado" id="idrefsubestado" class="campo" style="width:200px" >
                  <option value="-1" >Seleccionar</option>
                  <% 
					  iter = BBRPF.getListRefSubEstados().iterator();
					  while(iter.hasNext()){
						String [] datos = (String[])iter.next();%>
                  <option value="<%= datos[0] %>" <%= datos[0].equals( BBRPF.getIdrefsubestado().toString()) ? "selected" : "" %> lang="<%= datos[2]%>"><%= datos[1] %></option>
                  <%  
					  }%>
                </select></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Observaciones:  </td>
                <td width="83%" class="fila-det-border">
				<textarea name="observaciones" cols="70" rows="3" class="campo"><%=BBRPF.getObservaciones()%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Procesado: (*) </td>
                <td width="83%" class="fila-det-border"><%= BBRPF.getProcesado().intValue()!= 1 ? "NO" : "SI" %><input name="procesado" type="hidden" value="<%=BBRPF.getProcesado()%>" class="campo" size="5" maxlength="5"  ></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><input name="validar" type="submit" value="Enviar" class="boton" <%= BBRPF.getProcesado().toString().equals("0") ? "" : "disabled" %>>               <input name="volver" type="submit" class="boton" id="volver" value="Volver"></td>
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

