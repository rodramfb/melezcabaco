<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: bacoTmLlamados
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Mar 12 08:58:18 GYT 2009 
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
<jsp:useBean id="BBTLF"  class="ar.com.syswarp.web.ejb.BeanBacoTmLlamadosFrm"   scope="page"/>
<head>
 <title>FRMBacoTmLlamados</title>
 <meta http-equiv="description" content="DELTA">
	<link rel = "stylesheet" href = "<%= pathskin %>">
	<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
	<link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
	<script language="JavaScript" src="vs/forms/forms.js"></script>
	<script language="JavaScript" src="../vs/scripts/overlib.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BBTLF" property="*" />
 <% 
 String titulo = BBTLF.getAccion().toUpperCase() + " DE LLAMADOS DE TELEMARKETING" ;
 BBTLF.setResponse(response);
 BBTLF.setRequest(request);
 BBTLF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BBTLF.setUsuarioact( session.getAttribute("usuario").toString() );
 BBTLF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BBTLF.ejecutarValidacion();

 %>
<script>

var resultadosValidos = new Array();

<% 
  Iterator iter = BBTLF.getListMotivo().iterator();
  String salida = ""  ;
  String array = "";
  String resultado = "";

  while(iter.hasNext()){
	String[] datos = (String[]) iter.next();
    if(!resultado.equalsIgnoreCase(datos[2])){
      array  = "resultado_" + datos[2];
      salida += "var " + array + " = new Array(); \n";
      resultado=datos[2];
      salida += "resultadosValidos.push('" + datos[2]+ "'); \n";
    }
    salida += array + "['" + datos[0] + "']= '"  +  datos[1] + "'; \n";

  }
  out.write(salida); 
 %>

function poblarMotivos(resultado){
  var idmotivo = '<%=BBTLF.getIdmotivo()%>';
  var lista = document.getElementById("idmotivo");
  lista.options.length = 1; 
  if(resultado != '-1'){
    if (isResultadoValido(resultado) ){
      //alert("EXISTE!") ;
      var objArray = eval("resultado_" + resultado);
      for (var i in objArray){
        lista.options[lista.options.length] = new Option(   objArray[i]   , i);
        if(i == idmotivo) lista.options[lista.options.length-1].selected=true; 
      }
    } //else  alert("NO EXISTE!") ;
  }
}

function isResultadoValido( idresultado ){
  for(var r = 0; r < resultadosValidos.length ; r++){
    if (idresultado == resultadosValidos[r]){
      //alert(idresultado + ' / ' + resultadosValidos[r] );
      return true;
    }
  }
  return false;
}

window.onload = function() {
  var objResultado = document.getElementById("idresultado");
  var idresultado = objResultado.options[objResultado.selectedIndex].value ; 
  if(idresultado > 0){
    poblarMotivos(idresultado);
  }
}

</script>


<form action="bacoTmLlamadosFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BBTLF.getAccion()%>" >
<input name="idllamado" type="hidden" value="<%=BBTLF.getIdllamado()%>" >
   <span class="fila-det-border">
   <input name="idcliente" type="hidden" value="<%=BBTLF.getIdcliente()%>" class="campo" size="100" maxlength="100"  >
   <input name="cliente" type="hidden" class="campo" id="cliente" value="<%=BBTLF.getCliente()%>" size="100" maxlength="100"  >
   <input name="campacabe" type="hidden" class="campo" id="campacabe" value="<%=BBTLF.getCampacabe()%>" size="100" maxlength="100"  >
   <input name="idcampacabe" type="hidden" class="campo" id="idcampacabe" value="<%=BBTLF.getIdcampacabe()%>" size="100" maxlength="100"  >
   </span>
   <table width="95%"  border="0" cellspacing="0" cellpadding="0" align="center">
     <tr>
       <td>
         <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
            <tr class="text-globales">
              <td colspan="2"><%= titulo %></td>
            </tr>
            <tr class="text-globales">
              <td colspan="2"><hr color="#FFFFFF"></td>
            </tr>
            <tr class="text-globales">
              <td width="47%">Cliente: <%= !BBTLF.getCliente().equals("") ? BBTLF.getIdcliente() + " - "  + BBTLF.getCliente() : "" %>  </td>
              <td width="53%">Campa&ntilde;a: <%= !BBTLF.getCampacabe().equals("") ?  BBTLF.getIdcampacabe() + " - " +  BBTLF.getCampacabe() : "" %></td>
            </tr>
         </table> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr class="fila-det-bold-rojo">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det-bold-rojo">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><jsp:getProperty name="BBTLF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="20%" class="fila-det-border">&nbsp;Receptor Llamada :  </td>
                <td width="80%" class="fila-det-border"><input name="sociotelefono" type="text" value="<%=BBTLF.getSociotelefono()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="20%" class="fila-det-border">&nbsp;Resultado: (*) </td>
                <td width="80%" class="fila-det-border">
                <select name="idresultado" id="idresultado" onChange="poblarMotivos(this.value)" class="campo">
                    <option value="-1">Seleccionar</option>
                  <%
                  iter = BBTLF.getListResultado().iterator();  
                  while(iter.hasNext()){
                    String[] datos = (String[]) iter.next(); 
                  %>                 
                    <option value="<%= datos[0] %>" <%=BBTLF.getIdresultado().toString().equals(datos[0]) ? "selected" : ""%>><%= datos[1] %></option>
                  <% 
                  }
                   %> 
                  </select>                </td>
              </tr>
              <tr class="fila-det">
                <td width="20%" class="fila-det-border">&nbsp;Motivo:  </td>
                <td width="80%" class="fila-det-border">
                  <select name="idmotivo" id="idmotivo"  class="campo">
                    <option value="-1">Seleccionar</option>
                  </select>                </td>
              </tr>
              <tr class="fila-det">
                <td width="20%" class="fila-det-border">&nbsp;Observaciones:  </td>
                <td width="80%" class="fila-det-border"><textarea name="observaciones" cols="70" rows="3" class="campo"><%=BBTLF.getObservaciones()%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td width="20%" class="fila-det-border">Fecha Nueva Llamada:  </td>
                <td colspan="2" class="fila-det-border" >
                   <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fecharellamada" value="<%=BBTLF.getFecharellamada()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_9', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_9', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_9', 'img_Date_DOWN');showCalendar('frm','fecharellamada','BTN_date_9');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_9" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a> hh:               
                  <select name="hora" id="hora"  class="campo">
                    <option value="-1" selected >--</option>
                    <% 
                    for(int h=0;h<24;h++){ 
                      String hs =   h < 10 ?  "0" + h : h + "" ;%> 
                    <option value="<%= hs %>" <%= Integer.parseInt(BBTLF.getHora()) == h ? "selected" : "" %> ><%= hs %></option>
                    <% 
                    }%>
                  </select>
                  mi
                  :                  
                  <select name="minutos" id="minutos"  class="campo">
                    <option value="00" >00</option>
                  <%  
                    for(int m=0;m<60;m+=5){ 
                      String mi =   m < 10 ?  "0" + m : m + "" ;%> 
                    <option value="<%= mi %>" <%= Integer.parseInt(BBTLF.getMinutos()) == m ? "selected" : "" %> ><%= mi %></option>
                    <% 
                    }%>
                  </select>
                </td>
              </tr>
              <tr class="fila-det">
                <td width="20%" class="fila-det-border">&nbsp;</td>
                <td width="80%" class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Enviar" class="boton">               <input name="cerrar" type="button" class="boton" id="cerrar" value="Cerrar" onClick="window.close()"></td>
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

