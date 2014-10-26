<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%
String mensaje = request.getParameter("mensaje");
/*
	Properties prop = System.getProperties();
	Enumeration en = prop.propertyNames();
	while(en.hasMoreElements()){
		String pr = en.nextElement().toString();
		System.out.println( pr + " :" + prop.getProperty(pr));
	}
*/
GeneralBean repo = new GeneralBean();


java.util.List empresas = repo.getGlobalempresasAll();
Iterator iterEmpresas = empresas.iterator();
%>
<html>
	<head>
		<title>:: DELTA - ERP ::</title>

<link rel="shortcut icon" href="favicon.ico">
		<link href="imagenes/default/erp-style.css" rel="stylesheet" type="text/css">
		<script language="JavaScript" src="General/vs/forms/forms.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"></head>
	<body onLoad="document.frm.usuario.focus()">
		<form action="validar.jsp" method="post" name="frm">

			<table align="left">
				<tr>
					<td width="221">
						<table>
							<tr>
								<td class="subtitulo">
									Ingreso al sistema
								</td>
							</tr>
						</table>


						<table class="fila-det">
							<tr>
								<td class="fila-det-border">
									Nombre:
								</td>

								<td class="fila-det-border">
									<input type="text" name="usuario" class="campo">
								</td>
							</tr>
							<tr class="fila-det-border">
								<td class="fila-det-border">
									Clave:
								</td>
								<td class="fila-det-border">
									<input type="password" name="password" class="campo">
							</tr>
							<tr>
								<td class="fila-det-border">
									Empresa:
								</td>

								<td class="fila-det-border">
								   <select name="empresa" >
								   <%while(iterEmpresas.hasNext()){%>
								      <%String[] uCampos = (String[]) iterEmpresas.next();%>								      
								      <option value="<%=uCampos[0]%>"><%=uCampos[1]%></option>
								    <%}%>  
								   </select>	
								</td>
								
							</tr>
							
							<tr>
								<td class="fila-det-border">
									Copyright:
								</td>

								<td class="fila-det-border">
								   <div align="left">Syswarp SR L
		                        <img src="imagenes/default/nuevo.gif" width="16" height="16"></div></td>
								
							</tr>

							<tr>
								<td class="fila-det-border">
									Version:
								</td>

								<td class="fila-det-border">
								   20131105
								</td>
								
							</tr>

							
						</table>
						<table width="100%">
							<tr class="fila-det-bold-rojo">
							  <td>
							    <div align="right">
							      <%if (mensaje != null)
				out.write(mensaje);%>							
							      <input name="ingreso" type="submit" class="boton" value="Ingresar">
						        </div>
						      </td>
						  </tr>
							<tr class="fila-det">
							  <td><div onClick="abrirVentana('General/recuperarClave.jsp', 'recuperaclave', 550, 200)" style="cursor:pointer"><img src="imagenes/default/gnome_tango/actions/gnome-stock-mail-fwd.png" width="15" height="15"> Recuperar clave </div> </td>
							</tr>
						</table>
						<p align="center">&nbsp;</p>
					</td>
				</tr>
		  </table> 
			<table width="901" border="0">
              <tr>
                <td width="895" height="59">&nbsp;</td>
              </tr>
              <tr>
                <td><div align="center">
                  <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="800" height="600">
                    <param name="movie" value="logo-delta.jpg">
                    <param name="quality" value="high">
                    <embed src="logo-delta.jpg" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="800" height="600"></embed>
                  </object>
                </div></td>
              </tr>
            </table>
			<p>&nbsp;</p>
		</form>

	</body>
</html>
