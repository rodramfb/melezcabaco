<%@page language="java" %>
<% 
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: 
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Nov 14 14:50:17 GMT-03:00 2006 
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
<%@ page import="java.util.*" %>
<% 
try{
Strings str = new Strings();
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
%>
<!--DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"-->
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BCF"  class="ar.com.syswarp.web.ejb.BeanBacoErGenerar"   scope="page"/>
<head>
 <title>GENERAR ENTREGAS REGULARES</title>
 <link rel = "stylesheet" href = "<%= pathskin %>">
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 
<script type="text/javascript">
var peticion = null;
var objMensaje = null;



function inicializa_xhr() {
  if (window.XMLHttpRequest) {
    return new XMLHttpRequest();  
  } else if (window.ActiveXObject) {
    return new ActiveXObject("Microsoft.XMLHTTP"); 
  } 
}


function autocompleta() {
   objMensaje.firstChild.nodeValue = 'Generando Entregas Regulares.  Pror favor Espere. ';
   document.getElementById("prog").style.visibility = 'visible';
   document.getElementById("validar").disabled = true;
    var validar = 'OK';
     peticion = inicializa_xhr();
       peticion.onreadystatechange = function() { 
        if(peticion.readyState == 4) {
          if(peticion.status == 200) {
            sugerencias = eval('('+peticion.responseText+')');
            if(sugerencias.length != 0) {
              objMensaje.firstChild.nodeValue = sugerencias;
              document.getElementById("validar").disabled = false;
              document.getElementById("prog").style.visibility = 'hidden';
            }
          }
        }
      };

      peticion.open('POST', 'bacoErEjecutar.jsp?nocache='+Math.random(), true);
      peticion.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
      peticion.send('validar='+encodeURIComponent(validar) + '&anio=' + document.getElementById('anio').value + '&mes=' + document.getElementById('mes').value);
   
 }

function consultarLog(){
  var anio =  document.getElementById('anio').value;
  var mes =  document.getElementById('mes').value;
  if(anio < 1 || mes < 1 )
    objMensaje.firstChild.nodeValue = "Es necesario seleccionar mes/anio para consultar log.";
  else
    abrirVentana('pedidosEntregaRegularLogAbm.jsp?anio=' + anio + '&idmes='  + mes , 'log', 800, 450);
}


function consultarNecesidad(){
  var anio =  document.getElementById('anio').value;
  var mes =  document.getElementById('mes').value;
  if(anio < 1 || mes < 1 )
    objMensaje.firstChild.nodeValue = "Es necesario seleccionar mes/anio para consultar necesidad.";
  else
    abrirVentana('bacoErNecesidad.jsp?anio=' + anio + '&idmes='  + mes , 'necesidad', 800, 450);
//abrirVentana('bacoErNecesidad.jsp?anio=' + document.getElementById('anio').value + '&idmes='  + document.getElementById('mes').value, 'log', 800, 450);

}

window.onload = function() {
  // Crear elemento de tipo <div> para mostrar las sugerencias del servidor
  document.getElementById("validar").onclick = autocompleta;
  objMensaje = document.getElementById('msj') ;
}

</script>




</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCF" property="*" />
 <% 
 String titulo = "GENERAR ENTREGAS REGULARES" ;
 BCF.setResponse(response);
 BCF.setRequest(request);
 BCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BCF.ejecutarValidacion();
 %>
<form action="bacoErGenerar.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCF.getAccion()%>" >

   <table width="90%"  border="0" cellspacing="0" cellpadding="0" align="center">
     <tr>
       <td height="234">
         <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
            <tr class="text-globales">
              <td height="35">&nbsp;<%= titulo %></td>
            </tr> 
         </table> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" id="T1">
              <tr class="fila-det-bold-rojo">
                <td height="29" class="fila-det-border">&nbsp;</td>
                <td height="29" class="fila-det-border" id="msj"> <jsp:getProperty name="BCF" property="mensaje"/>                
                &nbsp;</td>
              </tr>
              <tr class="fila-det" >
                <td height="42" class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="fila-det">
                    <tr>
                      <td width="35%"><img src="../imagenes/default/gnome_tango/actions/progress.gif" name="prog" width="220" height="19" id="prog" style="visibility:hidden"></td>
                      <td width="33%"><img src="../imagenes/default/gnome_tango/actions/appointment.png" width="22" height="22" style="cursor:pointer" onClick="consultarLog();"> Consultar log. </td>
                      <td width="32%"><img src="../imagenes/default/gnome_tango/categories/package_office.png" width="22" height="22" style="cursor:pointer"  onClick="consultarNecesidad();">Consultar Necesidad </td>
                    </tr>
                  </table></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" height="65" class="fila-det-border">Mes / Anio: (*) </td>
                <td width="88%" class="fila-det-border">
                  <select name="mes" class="campo" id="mes">
                    <option value="-1">Seleccionar</option>
                   <%  
                     Iterator it = BCF.getMesesList().iterator();
                     while(it.hasNext()){
                       String[] meses= (String[])it.next(); %>
                    <option value="<%= meses[0] %>"><%= meses[1] %></option>
                   <%}  %>
                  </select>
                  <select name="anio" class="campo" id="anio">
                  <option value="-1">Seleccionar</option>
                  <option value="<%=  BCF.getAnio() %>"><%=  BCF.getAnio() %></option>
                  <option value="<%=  BCF.getAnio()+1 %>"><%=  BCF.getAnio()+1 %></option>
                </select>                </td>
              </tr>
              <tr class="fila-det">
                <td height="51" class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;<input name="validar" type="button" value="Generar " class="boton" id="validar" ></td>
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

