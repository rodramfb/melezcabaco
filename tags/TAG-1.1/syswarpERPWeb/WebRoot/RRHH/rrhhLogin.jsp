<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.math.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:useBean id="BRRHHUPV"  class="ar.com.syswarp.web.ejb.BeanRrhhUserPostulanteValidar"   scope="page"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
 <link rel="stylesheet" type="text/css" href="sitio/css/import.css">   
 <style type="text/css">
<!--
.Estilo1 {color: #990000}
.Estilo3 {color: #FFFFFF}
-->
 </style>
<title>Documento sin t&iacute;tulo</title> 
</head>

<body>
<jsp:setProperty name="BRRHHUPV" property="*" /> 
<%
 BRRHHUPV.setResponse(response); 
 BRRHHUPV.setRequest(request);
 BRRHHUPV.setSession(session);
 BRRHHUPV.setUsuarioalt( "WEB" );
 BRRHHUPV.setUsuarioact( "WEB" ); 
 BRRHHUPV.setIdempresa(new BigDecimal("1"));
 BRRHHUPV.ejecutarValidacion(); 

  %>		
	     <form action="rrhhLogin.jsp" method="post">	 
        <table width="57%"  border="0" align="center" cellpadding="0" cellspacing="0">
          <tr valign="middle" >
	          <td height="500" align="center">
						<table width="90%" border="0" cellpadding="0" cellspacing="0" class="table-border"> 
              <tr>
                <td height="57" colspan="3"><div align="center"><strong>Ingreso de Usuarios Registrados </strong></div>
								<span class="Estilo1">
                <jsp:getProperty name="BRRHHUPV" property="mensaje"/>
                </span>
								</td>
              </tr>
              <tr>
                <td width="22%" height="57" bgcolor="#EAECEB"><strong>Usuario</strong></td>
                <td width="31%" bgcolor="#EAECEB"><input name="uservalidar" type="text" id="uservalidar" value="<%= BRRHHUPV.getUservalidar() %>" /></td>
                <td width="47%" rowspan="2" bgcolor="#EAECEB"><div align="center"><img src="sitio/images/project.jpg" width="170" height="72" /></div></td>
              </tr>
              <tr>
                <td height="53" bgcolor="#EAECEB"><strong>Contrase&ntilde;a</strong></td>
                <td height="53" bgcolor="#EAECEB"><input name="contrasenavalidar" type="password" id="contrasenavalidar" value="<%= BRRHHUPV.getContrasenavalidar() %>"/></td> 
              </tr>
              <tr>
                <td height="53" bgcolor="#F3F3F3">&nbsp;</td>
                <td height="53" bgcolor="#F3F3F3"><input name="validar" type="submit" id="validar" value="Ingresar &gt;" /></td>
                <td bgcolor="#F3F3F3"><div align="center"><a href="index.jsp">VOLVER</a></div></td>
              </tr>
            </table>
		       </td>
          </tr>
        </table>
				</form>
 </body>
</html>
