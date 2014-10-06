<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: rrhhUserPostulante
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Oct 12 14:20:28 ART 2007 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 
%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.math.*" %>
<%//@ include file="session.jspf"%>
<% 
try{
Strings str = new Strings();
String pathskin = "";
String pathscript = "";
List lista = new ArrayList();
Iterator iter = null;
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BRUPF"  class="ar.com.syswarp.web.ejb.BeanRrhhUserPostulanteFrm"   scope="page"/>
<head>
 <title>Formulario de Suscripción</title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" type="text/css" href="sitio/css/import.css">
 <style type="text/css">
<!--
.Estilo1 {color: #990000}
.Estilo3 {color: #FFFFFF}
-->
 </style>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BRUPF" property="*" />
 <% 
 String titulo =  "Formulario de Suscripción".toUpperCase() ;
 String idempresa = session.getAttribute("empresapostulante") +  "" ;
 String idpostulante = session.getAttribute("idpostulante") == null ? "0" : session.getAttribute("idpostulante").toString() ; 
 if(str.esNulo(idempresa).equals("")) idempresa = "1";
 BRUPF.setResponse(response); 
 BRUPF.setRequest(request);
 BRUPF.setUsuarioalt( session.getAttribute("idpostulante") == null ? "WEB" : session.getAttribute("idpostulante").toString() );
 BRUPF.setUsuarioact( session.getAttribute("idpostulante") == null ? "WEB" : session.getAttribute("idpostulante").toString() ); 
 BRUPF.setIdempresa(new BigDecimal(idempresa));
 BRUPF.setIduserpostulante(new BigDecimal(idpostulante));
 BRUPF.ejecutarValidacion(); 
 %>
<form action="rrhhUserPostulanteFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BRUPF.getAccion()%>" >
<input name="iduserpostulante" type="hidden" value="<%=BRUPF.getIduserpostulante()%>" >
   <table width="90%"  border="0" cellspacing="0" cellpadding="0" align="center"> 
     <tr>
       <td>
         <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
            <tr >
              <td>&nbsp;<strong><%= titulo %></strong></td>
            </tr>
         </table> 
            <table width="100%" border="0" cellspacing="5" cellpadding="0" align="center">
              <tr >
                <td ><span class="Estilo1">
                <jsp:getProperty name="BRUPF" property="mensaje"/>
                </span>&nbsp;</td>
              </tr>
              <tr >
                <td width="40%" >
								<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table-border">
                  <tr >
                    <td bgcolor="#5C798A" class="Estilo3" id="indexHalf">DATOS DE REGISTRO </td>
                    <td bgcolor="#5C798A" >&nbsp;</td>
                  </tr>
                  <tr >
                    <td width="40%" bgcolor="#F3F3F3" >&nbsp;Identificaci&oacute;n: (*) </td>
                    <td width="60%" bgcolor="#F3F3F3" >&nbsp;
                        <input name="userpostulante" type="text" value="<%=BRUPF.getUserpostulante()%>"  size="20" maxlength="20"></td>
                  </tr>
                  <tr >
                    <td width="40%" bgcolor="#EAECEB" >&nbsp;Contrase&ntilde;a: (*) </td>
                    <td width="60%" bgcolor="#EAECEB" >&nbsp;
                        <input name="clave" type="password" value="<%=BRUPF.getClave()%>"  size="20" maxlength="20"></td>
                  </tr>
                  <tr >
                    <td bgcolor="#F3F3F3" >&nbsp;Confirmar Contrase&ntilde;a(*) </td>
                    <td bgcolor="#F3F3F3" >&nbsp;
                        <input name="claveconfirma" type="password" value="<%=BRUPF.getClaveconfirma()%>"  size="20" maxlength="20" ></td>
                  </tr>
                  <tr >
                    <td width="40%" bgcolor="#EAECEB" >&nbsp;Apellido: (*) </td>
                    <td width="60%" bgcolor="#EAECEB" >&nbsp;
                        <input name="apellido" type="text" value="<%=BRUPF.getApellido()%>"  size="50" maxlength="50"></td>
                  </tr>
                  <tr >
                    <td width="40%" bgcolor="#F3F3F3" >&nbsp;Nombre: (*) </td>
                    <td width="60%" bgcolor="#F3F3F3" >&nbsp;
                        <input name="nombre" type="text" value="<%=BRUPF.getNombre()%>"  size="50" maxlength="100"></td>
                  </tr>
                  <tr >
                    <td width="40%" bgcolor="#EAECEB" >&nbsp;E-mail: (*) </td>
                    <td width="60%" bgcolor="#EAECEB" >&nbsp;
                        <input name="email" type="text" value="<%=BRUPF.getEmail()%>"  size="50" maxlength="50"></td>
                  </tr>
                  <tr >
                    <td width="40%" bgcolor="#F3F3F3" >&nbsp;Pregunta a responder si olvida su contrase&ntilde;a : (*) </td>
                    <td width="60%" bgcolor="#F3F3F3" >&nbsp;
                        <input name="pregunta" type="text" value="<%=BRUPF.getPregunta()%>"  size="50" maxlength="100"></td>
                  </tr>
                  <tr >
                    <td width="40%" bgcolor="#EAECEB" >&nbsp;Respuesta a la pregunta anterior: (*) </td>
                    <td width="60%" bgcolor="#EAECEB" >&nbsp;
                        <input name="respuesta" type="text" value="<%=BRUPF.getRespuesta()%>"  size="50" maxlength="100"></td>
                  </tr>
                </table>                </td>
              </tr>
              <tr >
                <td >
								<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table-border">
                  <tr >
                    <td bgcolor="#5C798A" class="Estilo3" id="indexHalf">OTROS DATOS PERSONALES </td>
                    <td bgcolor="#5C798A" >&nbsp;</td>
                  </tr>
                  <tr >
                    <td width="40%" bgcolor="#F3F3F3" >&nbsp;Direcci&oacute;n: </td>
                    <td width="60%" bgcolor="#F3F3F3" >&nbsp;
                        <input name="direccion" type="text" value="<%=BRUPF.getDireccion()%>"  size="50" maxlength="50"></td>
                  </tr>
                  <tr >
                    <td width="40%" bgcolor="#EAECEB" >&nbsp;C&oacute;digo Postal: </td>
                    <td width="60%" bgcolor="#EAECEB" >&nbsp;
                        <input name="codigo_postal" type="text" value="<%=BRUPF.getCodigo_postal()%>"  size="10" maxlength="10"></td>
                  </tr>
                  <tr >
                    <td width="40%" bgcolor="#F3F3F3" >&nbsp;Pais: (*) </td>
                    <td width="60%" bgcolor="#F3F3F3" >&nbsp;
                   <select name="idpais" class="selectperfil">
                           <option value="1"   >Argentina</option>
                    </select>										
										</td>
                  </tr>
                  <tr >
                    <td width="40%" bgcolor="#EAECEB" >&nbsp;Provincia: (*) </td>
                    <td width="60%" bgcolor="#EAECEB" >&nbsp;
                        <select name="idprovincia" class="selectperfil">
                          <option value="0">Seleccionar</option>
                          <%
										lista = (List)BRUPF.getListProvincias();
										iter = lista.iterator();
										while(iter.hasNext()){
										  String sel = "";
											String[] datos = (String[]) iter.next();
											if(datos[0].equals(BRUPF.getIdprovincia().toString())) sel = "selected";
											else sel = "";
										  %>
                          <option value="<%=datos[0]%>" <%=sel%> ><%= datos[1] %></option>
                          <%
										} %>
                        </select></td>
                  </tr>
                  <tr >
                    <td width="40%" bgcolor="#F3F3F3" >&nbsp;Localidad: (*) </td>
                    <td width="60%" bgcolor="#F3F3F3" >&nbsp;
                        <input name="idlocalidad" type="text" value="<%=BRUPF.getIdlocalidad()%>"  size="50" maxlength="100"></td>
                  </tr>
                  <tr >
                    <td width="40%" bgcolor="#EAECEB" >&nbsp;D.N.I: </td>
                    <td width="60%" bgcolor="#EAECEB" >&nbsp;
                        <input name="nrodni" type="text" value="<%=BRUPF.getNrodni()%>"  size="50" maxlength="100"></td>
                  </tr>
                  <tr >
                    <td width="40%" bgcolor="#F3F3F3" >&nbsp;Fecha Nacimiento : </td>
                    <td colspan="2" bgcolor="#F3F3F3"  >&nbsp;
                        <input size="12" type="text" name="fechanac" value="<%= BRUPF.getFechanac()  %>" maxlength="10" />
                    (dd/mm/aaaa) </td>
                  </tr>
                  <tr >
                    <td width="40%" bgcolor="#EAECEB" >&nbsp;TE. Particular : </td>
                    <td width="60%" bgcolor="#EAECEB" >&nbsp;
                        <input name="telparticular" type="text" value="<%=BRUPF.getTelparticular()%>"  size="50" maxlength="50"></td>
                  </tr>
                  <tr >
                    <td width="40%" bgcolor="#F3F3F3" >&nbsp;TE. Laboral : </td>
                    <td width="60%" bgcolor="#F3F3F3" >&nbsp;
                        <input name="tellaboral" type="text" value="<%=BRUPF.getTellaboral()%>"  size="50" maxlength="50"></td>
                  </tr>
                  <tr >
                    <td width="40%" bgcolor="#EAECEB" >&nbsp;TE. Celular : </td>
                    <td width="60%" bgcolor="#EAECEB" >&nbsp;
                        <input name="telcelular" type="text" value="<%=BRUPF.getTelcelular()%>"  size="50" maxlength="50"></td>
                  </tr>
                </table></td>
              </tr>
              <tr >
                <td ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table-border">
                  <tr >
                    <td bgcolor="#5C798A" ><div align="center" class="Estilo3">
                      <div align="left">DATOS PERFIL </div>
                    </div></td>
                    <td bgcolor="#5C798A" >&nbsp;</td>
                  </tr>
                  <tr >
                    <td width="40%" bgcolor="#F3F3F3" >&nbsp;E-mail Para Recibir Avisos: (*) </td>
                    <td width="60%" bgcolor="#F3F3F3" >&nbsp;
                        <input name="emailperfil" type="text" value="<%=BRUPF.getEmailperfil()%>"  size="50" maxlength="50"></td>
                  </tr>
                  <tr bgcolor="#EAECEB" >
                    <td width="40%" >&nbsp;Puesto: (*) </td>
                    <td width="60%" >&nbsp;
                        <select name="idpuesto" class="selectperfil"> 
                          <option value="0">Seleccionar</option>
                          <%
										lista = (List)BRUPF.getHtDinamico().get("puesto");
										iter = lista.iterator();
										while(iter.hasNext()){
										  String sel = "";
											String[] datos = (String[]) iter.next();
											if(datos[0].equals(BRUPF.getIdpuesto().toString())) sel = "selected";
											else sel = "";
										  %>
                          <option value="<%=datos[0]%>" <%=sel%> ><%= datos[1] %></option>
                          <%
										} %>
                        </select>                    </td>
                  </tr>
                  <tr >
                    <td width="40%" bgcolor="#F3F3F3" >&nbsp;Lenguaje: </td>
                    <td width="60%" bgcolor="#F3F3F3" >&nbsp;
                        <select name="idlenguaje" class="selectperfil">
                          <option value="0">Seleccionar</option>
                          <%
										lista = (List)BRUPF.getHtDinamico().get("lenguaje");
										iter = lista.iterator();
										while(iter.hasNext()){ 
										  String sel = "";
											String[] datos = (String[]) iter.next();
											if(datos[0].equals(BRUPF.getIdlenguaje().toString())) sel = "selected";
											else sel = "";
										  %>
                          <option value="<%=datos[0]%>" <%=sel%> ><%= datos[1] %></option>
                          <%
										} %>
                        </select>                    </td>
                  </tr>
                  <tr >
                    <td width="40%" bgcolor="#EAECEB" >&nbsp;Hardware: </td>
                    <td width="60%" bgcolor="#EAECEB" >&nbsp;
                        <select name="idhw" class="selectperfil">
                          <option value="0">Seleccionar</option>
                          <%
										lista = (List)BRUPF.getHtDinamico().get("hw");
										iter = lista.iterator();
										while(iter.hasNext()){
										  String sel = "";
											String[] datos = (String[]) iter.next();
											if(datos[0].equals(BRUPF.getIdhw().toString())) sel = "selected";
											else sel = "";
										  %>
                          <option value="<%=datos[0]%>" <%=sel%> ><%= datos[1] %></option>
                          <%
										} %>
                        </select>                    </td>
                  </tr>
                  <tr >
                    <td width="40%" bgcolor="#F3F3F3" >&nbsp;Sistema Operativo : </td>
                    <td width="60%" bgcolor="#F3F3F3" >&nbsp;
                        <select name="idso" class="selectperfil">
                          <option value="0">Seleccionar</option>
                          <%
										lista = (List)BRUPF.getHtDinamico().get("so");
										iter = lista.iterator();
										while(iter.hasNext()){
										  String sel = "";
											String[] datos = (String[]) iter.next();
											if(datos[0].equals(BRUPF.getIdso().toString())) sel = "selected";
											else sel = "";
										  %>
                          <option value="<%=datos[0]%>" <%=sel%> ><%= datos[1] %></option>
                          <%
										} %>
                        </select>                    </td>
                  </tr>
                  <tr >
                    <td width="40%" bgcolor="#EAECEB" >&nbsp;Base de Datos : </td>
                    <td width="60%" bgcolor="#EAECEB" >&nbsp;
                        <select name="iddb" class="selectperfil">
                          <option value="0">Seleccionar</option>
                          <%
										lista = (List)BRUPF.getHtDinamico().get("db");
										iter = lista.iterator();
										while(iter.hasNext()){
										  String sel = "";
											String[] datos = (String[]) iter.next();
											if(datos[0].equals(BRUPF.getIddb().toString())) sel = "selected";
											else sel = "";
										  %>
                          <option value="<%=datos[0]%>" <%=sel%> ><%= datos[1] %></option>
                          <%
										} %>
                        </select>                    </td>
                  </tr>
                  <tr >
                    <td width="40%" bgcolor="#F3F3F3" >&nbsp;Aplicaci&oacute;n: </td>
                    <td width="60%" bgcolor="#F3F3F3" >&nbsp;
                        <select name="idapp" class="selectperfil">
                          <option value="0">Seleccionar</option>
                          <%
										lista = (List)BRUPF.getHtDinamico().get("app");
										iter = lista.iterator();
										while(iter.hasNext()){
										  String sel = "";
											String[] datos = (String[]) iter.next();
											if(datos[0].equals(BRUPF.getIdapp().toString())) sel = "selected";
											else sel = "";
										  %>
                          <option value="<%=datos[0]%>" <%=sel%> ><%= datos[1] %></option>
                          <%
										} %>
                        </select>                    </td>
                  </tr>
                  <tr >
                    <td width="40%" bgcolor="#EAECEB" >&nbsp;Redes: </td>
                    <td width="60%" bgcolor="#EAECEB" >&nbsp;
                        <select name="idred" class="selectperfil">
                          <option value="0" >Seleccionar</option>
                          <%
										lista = (List)BRUPF.getHtDinamico().get("red");
										iter = lista.iterator();
										while(iter.hasNext()){
										  String sel = ""; 
											String[] datos = (String[]) iter.next();
											if(datos[0].equals(BRUPF.getIdred().toString())) sel = "selected";
											else sel = "";
										  %>
                          <option value="<%=datos[0]%>" <%=sel%> ><%= datos[1] %></option>
                          <%
										} %>
                        </select>                    </td>
                  </tr>
                </table></td>
              </tr>
              <tr >
                <td ><div align="center">
                  <input name="validar" type="submit" value="Enviar" >
                </div></td>
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
  System.out.println("ERROR ( + pagina + ) : "+ex);   
}%>

