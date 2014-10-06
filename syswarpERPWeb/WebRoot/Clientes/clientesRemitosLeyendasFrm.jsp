<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: clientesRemitosLeyendas
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Dec 10 09:15:59 GMT-03:00 2009 
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
<jsp:useBean id="BCRLF"  class="ar.com.syswarp.web.ejb.BeanClientesRemitosLeyendasFrm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BCRLF" property="*" />
<head>
 <title></title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>

 <script>

   function limpiarDatos(valor){

			switch (valor) {

				 case 'L':
						 document.frm.idprovincia.value = "-1";
						 document.frm.provincia.value = "";
						break;
				 case 'P':
						 document.frm.idlocalidad.value = "-1";
						 document.frm.localidad.value = "";
						break;
					default:
						 document.frm.idprovincia.value = "-1";
						 document.frm.provincia.value = "";
						 document.frm.idlocalidad.value = "-1";
						 document.frm.localidad.value = "";
						break;

			}
     
   }

  function callLocalidad(){

      if(!validarAccion())
       alert('Operacion no permitida, solo es posible modificar el campo leyenda o período.');
     else if(document.frm.criterio[getIndex()].value == 'L')
       abrirVentana('../Clientes/lov_localidades.jsp', 'localidad', 750, 550);
     else 
       alert('Es necesario seleccionar criterio \'Localidad\'');

  }


  function callProvincia(){
  
     if(!validarAccion())
       alert('Operacion no permitida, solo es posible modificar el campo leyenda o período.');
     else if(document.frm.criterio[getIndex()].value == 'P')
       abrirVentana('../Clientes/lov_provincia.jsp', 'localidad', 750, 550);
     else 
       alert('Es necesario seleccionar criterio \'Provincia\'');

  }

  function getIndex(){
    var i=0;

    for (i=0;i<document.frm.criterio.length;i++){
      if(document.frm.criterio[i].checked == true ) 
        break;
    }

    return i;

  }

 function validarAccion(){
   var accion = '<%= BCRLF.getAccion().toLowerCase() %>';
   if(accion != 'alta') 
     return false;
   else
     return true;
 }

 </script>

</head>
<BODY >
<div id="popupcalendar" class="text"></div>

 <% 
 String titulo = BCRLF.getAccion().toUpperCase() + " DE LEYENDAS DE REMITOS DE CLIENTES POR PERIODO." ;
 BCRLF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BCRLF.setResponse(response);
 BCRLF.setRequest(request);
 BCRLF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCRLF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCRLF.ejecutarValidacion();
 %>
<form action="clientesRemitosLeyendasFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCRLF.getAccion()%>" >
<input name="idleyenda" type="hidden" value="<%=BCRLF.getIdleyenda()%>" >
<table width="95%"  border="0" cellspacing="0" cellpadding="0" align="center">
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
                <td class="fila-det-border"><jsp:getProperty name="BCRLF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td height="35" class="fila-det-border">Criterio(*): </td>
                <td class="fila-det-border">
                 <table width="50%" border="0" cellspacing="0" cellpadding="0" class="fila-det">
                  <tr>
                    <td width="8%">Todo</td>
                    <td width="17%"><input name="criterio" type="radio" value="T" onClick="limpiarDatos(this.value)" <%= BCRLF.getCriterio().equalsIgnoreCase("T") ?  "checked" : "" %>></td>
                    <td width="15%"><div align="right">Localidad                    </div></td>
                    <td width="26%"><div align="left">
                      <input name="criterio" type="radio" value="L" onClick="limpiarDatos(this.value)" <%= BCRLF.getCriterio().equalsIgnoreCase("L") ?  "checked" : "" %> <%= !BCRLF.getAccion().toLowerCase().equals("alta") ? "disabled"  : "" %>>
                    </div></td>
                    <td width="15%"><div align="right">Provincia</div></td>
                    <td width="19%"><div align="left">
                      <input name="criterio" type="radio" value="P" onClick="limpiarDatos(this.value)" <%= BCRLF.getCriterio().equalsIgnoreCase("P") ?  "checked" : "" %> <%= !BCRLF.getAccion().toLowerCase().equals("alta") ? "disabled"  : "" %> title="Seleccionando el criterio Provincia se generará un registro por cada localidad que pertenezca a la Provincia elegida.">
                    </div></td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td height="35" class="fila-det-border">Club(*): </td>
                <td class="fila-det-border"><select name="idclub" id="idclub" class="campo" style="width:75px" >
                  <option value="-1" >Sel.</option>
                  <% 
									  Iterator iter = BCRLF.getListClub().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
                  <option value="<%= datos[0] %>" <%= datos[0].equals( BCRLF.getIdclub().toString() ) ? "selected" : "" %>><%= datos[1] %></option>
                  <%  
									  }%>
                </select></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" height="35" class="fila-det-border">&nbsp;Periodo: (*) </td>
                <td width="78%" class="fila-det-border">
                  <select name="anio" class="campo" id="anio">
                    <option value="-1">Seleccionar</option>
                    <% 
                    for(int z = BCRLF.getAnioactual(); z<=BCRLF.getAnioactual()+1 ; z++){ %>
                    <option value="<%=  z %>" <%= BCRLF.getAnio().intValue() == z ? "selected" : "" %>><%=  z %></option>
                    <%
                    }  %>
                  </select>&nbsp;
                  <select name="idmes" class="campo" id="idmes">
                    <option value="-1">Seleccionar</option>
                    <%  
                     iter = BCRLF.getMesesList().iterator();
                     while(iter.hasNext()){
                       String[] meses= (String[])iter.next(); %>
                    <option value="<%= meses[0] %>" <%=BCRLF.getIdmes().intValue() == Integer.parseInt(meses[0] )? "selected" : "" %>><%= meses[1] %></option>
                    <%}  %>
                  </select>
&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="22%" height="35" class="fila-det-border">Localidad: (*) 
                  <input name="idlocalidad" type="hidden" value="<%=BCRLF.getIdlocalidad()%>"  ></td>
                <td width="78%" class="fila-det-border"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <tr class="fila-det-border">
                    <td width="33%" ><input name="localidad" type="text" class="campo" id="localidad" value="<%=BCRLF.getLocalidad()%>" size="50" readonly></td>
                    <td width="17%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="callLocalidad()" style="cursor:pointer"></td>
                    <td width="50%" class="fila-det">CP: 
                    <input name="cpostal" id="cpostal" type="text" value="<%=BCRLF.getCpostal()%>" readonly style="border:none" class="campo"></td>
                  </tr>
                </table>                </td>
              </tr>
              <tr class="fila-det">
                <td height="35" class="fila-det-border">Provincia:<input name="idprovincia" type="hidden" class="campo" id="idprovincia" value="<%=BCRLF.getIdprovincia()%>" ></td>
                <td class="fila-det-border"><table width="36%" border="0" cellpadding="0" cellspacing="0">
                  <tr class="fila-det-border">
                    <td width="33%" ><input name="provincia" type="text" class="campo" id="provincia" value="<%=BCRLF.getProvincia()%>" size="50" readonly></td>
                    <td width="3%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="callProvincia()" style="cursor:pointer"></td>
                  </tr>
                  
                </table></td>
              </tr>
              
              <tr class="fila-det">
                <td width="22%" height="50" class="fila-det-border">Leyenda: (*) </td>
                <td width="78%" class="fila-det-border"><textarea name="leyenda" cols="70" rows="2" class="campo"><%=BCRLF.getLeyenda()%></textarea>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td height="69" class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Enviar" class="boton">               <input name="volver" type="submit" class="boton" id="volver" value="Volver"></td>
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

