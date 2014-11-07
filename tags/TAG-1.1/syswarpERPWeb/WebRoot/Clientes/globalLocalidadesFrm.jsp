<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: globalLocalidades
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Jan 22 15:19:44 GMT-03:00 2007 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%@ page import="java.math.*" %>  
<%@ page import="java.util.*" %>   
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
<jsp:useBean id="BGLF"  class="ar.com.syswarp.web.ejb.BeanGlobalLocalidadesFrm"   scope="page"/>
<head>
 <title>FRMGlobalLocalidades.jsp</title>
  <title><MMString:LoadString id="insertbar/formsHidden" /></title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">
<%-- <link rel="stylesheet" href="../imagenes/default/erp-style.css"> --%>

 
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>

 <script>

   function asociarObsequios(accion){ 

     document.frm.action = 'bacoObsequiosLocalidadFrm.jsp';
     document.frm.accion.value = accion;
     document.frm.submit();

   }

   function eliminarObsequios(){ 
     if(confirm('Confirma eliminar obsequio asociado a localidad ?')){
       document.frm.bajaobsequios.value = 'bajaobsequios';
       document.frm.submit();
     }
   } 

 </script>

</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BGLF" property="*" />
 <% 
 String titulo = BGLF.getAccion().toUpperCase() + " DE LOCALIDADES" ;
 BGLF.setResponse(response);
 BGLF.setRequest(request);
 BGLF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 BGLF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BGLF.setUsuarioact( session.getAttribute("usuario").toString() );
 BGLF.ejecutarValidacion();
 %>
<form action="globalLocalidadesFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BGLF.getAccion()%>" >
<input name="idlocalidad" type="hidden" value="<%=BGLF.getIdlocalidad()%>" >
   <table width="98%"  border="0" cellspacing="0" cellpadding="0" align="center">
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
                <td width="88%" class="fila-det-border"><jsp:getProperty name="BGLF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="12%" height="29" class="fila-det-border">Localidad: (*) </td>
                <td class="fila-det-border"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="31%"><input name="localidad" type="text" value="<%=BGLF.getLocalidad()%>" class="campo" size="50" maxlength="50"  ></td>
                      <td width="69%">

                       <% if(BGLF.getIdlocalidad().longValue() > 0){ %>
                       <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                       <%   if(BGLF.getIdobsequio().longValue() < 0){ %>
                        <tr>
                          <td width="3%" colspan="2" class="fila-det"> 
                           <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="3%" class="fila-det"><a href="javascript:asociarObsequios( 'alta' )"><img src="../imagenes/default/nuevo.gif" width="16" height="16" title="Nuevo obsequio por localidad" border="0"></a></td>
                              <td width="97%" class="fila-det"><a href="javascript:asociarObsequios( 'alta' )">Asociar Obsequio</a></td>
                            </tr>
                          </table></td>
                        </tr>
                       <%   }else{ %>                        
                         <tr>
                          <td width="1%" class="fila-det">
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td width="7%" class="fila-det"><a href="javascript:asociarObsequios( 'modificacion' )"><img src="../imagenes/default/gnome_tango/mimetypes/application-certificate.png" width="18" height="18"  title="Modificar obsequio por localidad" border="0"></a></td>
                                <td width="93%" class="fila-det"><a href="javascript:asociarObsequios( 'modificacion' )">Modificar Obsequio</a></td>
                              </tr>
                            </table></td>
                          <td width="2%" class="fila-det">
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td width="7%" class="fila-det"><a href="javascript:eliminarObsequios( 'eliminarobsequios' )"><img src="../imagenes/default/gnome_tango/emblems/emblem-unreadable.png" width="18" height="18"  title="Modificar obsequio por localidad" border="0"></a></td>
                                <td width="93%" class="fila-det"><a href="javascript:eliminarObsequios( 'eliminarobsequios' )">Eliminar Obsequio</a></td>
                              </tr>
                            </table></td>
                         </tr>
                       <%   } %> 
                      </table>
                       <% } else out.print("&nbsp;");%>                     </td>
                    </tr>
                  </table></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" height="30" class="fila-det-border">Provincia: (*) 
                <input name="idprovincia" type="hidden" id="idprovincia" value="<%=BGLF.getIdprovincia()%>"></td>
                <td class="fila-det-border"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="21%"><input name="d_idprovincia" type="text" class="campo" id="d_idprovincia" value="<%=BGLF.getD_idprovincia()%>" size="30" readonly></td>
                      <td width="79%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Clientes/lov_provincia.jsp')" style="cursor:pointer"> </td>
                    </tr>
                  </table></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" height="31" class="fila-det-border">Codigo Postal(*):  </td>
                <td class="fila-det-border"><input name="cpostal" type="text" value="<%=BGLF.getCpostal()%>" class="campo" size="20" maxlength="20"  ></td>
              </tr>
              <tr class="fila-det">
                <td height="28" class="fila-det-border">Tipo IVA: </td>
                <td class="fila-det-border"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="12%">
                      <select name="idtipoiva" id="idtipoiva" class="campo" style="width:100px" >
                        <option value="-1">Seleccionar</option>
                        <% 
									  Iterator iter = BGLF.getListTipoIva().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
                        <option value="<%= datos[0] %>" <%= datos[0].equals( BGLF.getIdtipoiva().toString()) ? "selected" : "" %>><%= datos[1] %></option>
                        <%  
									  }%>
                      </select>
                      </td>
                      <td width="88%"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr >
                            <td width="7%" class="fila-det-rojo">ATENCIÓN: </td>
                            <td width="93%" class="fila-det-rojo"> Solo asigne valor a este atributo en el caso que este seguro y corresponda. </td>
                          </tr>
                          
                        </table></td>
                  </tr>
                    
                  </table></td>
              </tr>
              <tr class="fila-det">
                <td height="28" class="fila-det-border">ID-Localidad Baco </td>
                <td class="fila-det-border"><input name="idlocalidadbaco" type="text" class="campo" id="idlocalidadbaco" value="<%=BGLF.getIdlocalidadbaco()%>" size="20" maxlength="20"  ></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Enviar" class="boton">               <input name="volver" type="submit" class="boton" id="volver" value="Volver"></td>
             </tr>
          </table>  
       </td>
     </tr>
  </table> 
   <input name="idobsequio" type="hidden" id="idobsequio" value="<%= BGLF.getIdobsequio() %>">
   <input name="bajaobsequios" type="hidden" id="bajaobsequios" value="">
   <input name="referente" type="hidden" id="referente" value="globalLocalidadesFrm.jsp?idlocalidad=<%=  BGLF.getIdlocalidad()%>&accion=modificacion">
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

