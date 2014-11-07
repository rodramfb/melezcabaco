<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.security.*" %> 
<%@ page import="javax.naming.*" %> 
<%@ page import="javax.naming.directory.*" %>
<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.Timestamp" %>


<%@ page import="ar.com.syswarp.api.*" %>
<%@ include file="session.jspf"%>
<%@ page import="java.math.BigDecimal" %>



<%

try{

//System.out.println("< PASO 0 > ");

String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();

//System.out.println("< PASO 0.1 > ");

String titulo  = "MODULO CONTABLE";
String usuario                    = session.getAttribute("usuario").toString();
String ejercicioActivo            = session.getAttribute("ejercicioActivo").toString();

//System.out.println("< PASO 0.2 > ");

String fechaEjercicioActivoDesde  = session.getAttribute("fechaEjercicioActivoDesde").toString();
String fechaEjercicioActivoHasta  = session.getAttribute("fechaEjercicioActivoHasta").toString();

//System.out.println("< PASO 0.3 > ");

String numerofacturaA             = session.getAttribute("numerofacturaA").toString();
String numerofacturaB             = session.getAttribute("numerofacturaB").toString();

//System.out.println("< PASO 0.4 > ");

String numerofacturaC             = session.getAttribute("numerofacturaC").toString();
String numerorecibo               = session.getAttribute("numerorecibo").toString();

//System.out.println("< PASO 0.5 > ");

String numeroremitos1             = session.getAttribute("numeroremitos1").toString();
String numeroremitos2             = session.getAttribute("numeroremitos2").toString();

//System.out.println("< PASO 0.6 > ");

String numeroremitos3             = session.getAttribute("numeroremitos3").toString();
String numeroremitos4             = session.getAttribute("numeroremitos4").toString();

//System.out.println("< PASO 0.7 > ");

String nombrePuesto               = session.getAttribute("nombrePuesto").toString();
BigDecimal idempresa              = new BigDecimal(session.getAttribute("empresa").toString());           

//System.out.println("< PASO 0.8 > ");

String fechaActual = Common.initObjectTimeStr();
String tesoFechaCaja = Common.getGeneral().getValorSetupVariablesNoStatic("tesoFechaCaja",  idempresa );
String idcolor = "1";

//System.out.println("< PASO 1 > ");


String cookieName = "deltacolor";
Cookie cookies [] = request.getCookies ();
Cookie myCookie = null;
if (cookies != null){
   for (int i = 0; i < cookies.length; i++) {
      if (cookies [i].getName().equals (cookieName)){
          myCookie = cookies[i];
          break;
        }
      }
}

//System.out.println("< PASO 2 > ");

if  (myCookie != null) {
   idcolor = myCookie.getValue().toString().trim();
}

if (!idcolor.equals("1")){
   if (idcolor.equals("2")){ // azul       
      session.setAttribute("pathskin","/syswarpERPWeb/imagenes/default/erp-style-azul.css" );      
   } 
   if (idcolor.equals("3")){ // rojo      
      session.setAttribute("pathskin","/syswarpERPWeb/imagenes/default/erp-style-rojo.css" );      
   } 
   if (idcolor.equals("4")){ // gris      
      session.setAttribute("pathskin","/syswarpERPWeb/imagenes/default/erp-style-gris.css" );      
   } 
   if (idcolor.equals("5")){ // naranja      
      session.setAttribute("pathskin","/syswarpERPWeb/imagenes/default/erp-style-naranja.css" );      
   } 
   if (idcolor.equals("6")){ // purpura      
      session.setAttribute("pathskin","/syswarpERPWeb/imagenes/default/erp-style-purpura.css" );      
   } 
   if (idcolor.equals("7")){ // celeste      
      session.setAttribute("pathskin","/syswarpERPWeb/imagenes/default/erp-style-celeste.css" );      
   } 
   
   
   
   
   pathskin = session.getAttribute("pathskin").toString(); 
}

//System.out.println("< PASO 3 > ");

if (request.getParameter("idcolor") != null ){
   idcolor = request.getParameter("idcolor").toString();
   if (idcolor.equals("1")){ // verde (default)      
      session.setAttribute("pathskin","/syswarpERPWeb/imagenes/default/erp-style.css" );      
   } 
   if (idcolor.equals("2")){ // azul
      session.setAttribute("pathskin","/syswarpERPWeb/imagenes/default/erp-style-azul.css" );      
   } 
   if (idcolor.equals("3")){ // rojo      
      session.setAttribute("pathskin","/syswarpERPWeb/imagenes/default/erp-style-rojo.css" );      
   } 
   if (idcolor.equals("4")){ // gris      
      session.setAttribute("pathskin","/syswarpERPWeb/imagenes/default/erp-style-gris.css" );      
   } 
   if (idcolor.equals("5")){ // naranja      
      session.setAttribute("pathskin","/syswarpERPWeb/imagenes/default/erp-style-naranja.css" );      
   } 
   if (idcolor.equals("6")){ // purpura      
      session.setAttribute("pathskin","/syswarpERPWeb/imagenes/default/erp-style-purpura.css" );      
   } 
   if (idcolor.equals("7")){ // celeste      
      session.setAttribute("pathskin","/syswarpERPWeb/imagenes/default/erp-style-celeste.css" );      
   } 
   Cookie cookie = new Cookie ("deltacolor",idcolor);
   cookie.setMaxAge(365 * 24 * 60 * 60);
   response.addCookie(cookie);

   pathskin = session.getAttribute("pathskin").toString();
}

//System.out.println("< PASO 4 > ");

%>
<html>
<head>
<title></title>
<link href="<%= pathskin  %>" rel="stylesheet" type="text/css">
</head>
<body>
<table width="95%"  border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td>
			<table width="95%" border="0" cellpadding="0" cellspacing="0" align="center">
				<tr class="titulo" > 
					<td width="236" height="35"  ><p> DELTA </p>				  </td>
					<td width="91" >&nbsp;</td>
					<td width="505" align="left" ><table width="16%" border="0" align="right" cellpadding="0" cellspacing="4">
                      <tr>
                        <td width="11%"><a href="principal.jsp?idcolor=1"><img src="imagenes/default/gnome_tango/colors.green.icon.gif" alt="Verde" width="15" height="15" border="0"></a></td>
                        <td width="24%"><a href="principal.jsp?idcolor=2"><img src="imagenes/default/gnome_tango/colors.blue.icon.gif" alt="Azul" width="15" height="15" border="0"></a></td>
                        <td width="13%"><a href="principal.jsp?idcolor=3"><img src="imagenes/default/gnome_tango/colors.red.icon.gif" alt="Rojo" width="15" height="15" border="0"></a></td>
                        <td width="13%"><a href="principal.jsp?idcolor=4"><img src="imagenes/default/gnome_tango/colors.gray.icon.gif" alt="Gris" width="15" height="15" border="0"></a></td>
                        <td width="13%"><a href="principal.jsp?idcolor=5"><img src="imagenes/default/gnome_tango/colors.orange.icon.gif" alt="Naranja" width="15" height="15" border="0"></a></td>
                        <td width="13%"><a href="principal.jsp?idcolor=6"><img src="imagenes/default/gnome_tango/colors.purple.icon.gif" alt="Purpura" width="15" height="15" border="0"></a></td>
                        <td width="13%"><a href="principal.jsp?idcolor=7"><img src="imagenes/default/gnome_tango/colors.sugar.icon.gif" alt="Celeste" width="15" height="15" border="0"></a></td>
                      </tr>
                  </table></td>
				</tr>
			
				<tr valign="top" class="titulo" >
				  <td height="23"  >&nbsp;</td>
				  <td>&nbsp;</td>
				  <td><a href="General/usuariosFrm.jsp"></a>
				    <table width="197" border="0" align="right" cellpadding="0" cellspacing="2">
                      <tr class="fila-det-bold">
                        <td width="175" align="right"><span class="fila-det-border">Cambiar Contraseña</span></td>
                        <td width="22" align="left"><a href="General/usuariosFrm.jsp"><img src="imagenes/default/gnome_tango/actions/lock.png" alt="Cambiar Clave" width="22" height="22" border="0"></a></td>
                      </tr>
                    </table></td>
			  </tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" > 
				<tr class="fila-det-bold">
					<td width="254" height="26" class="fila-det-border">Usuario:</td>
					<td width="566" class="fila-det-border">&nbsp;<%=usuario.toUpperCase()%></td>
					
				</tr>
				
				<%if(!fechaActual.equals(tesoFechaCaja)){%>
				<tr class="fila-det-border"> 
				  <td height="38" colspan="2" class="fila-det-border"> 				    
			    <div  class="fila-det-bold-rojo">La fecha de caja no coincide con la del día, si desea trabajar con la caja al día, por favor modifique dicha fecha. </div></td>
			  </tr>					
				<%}%>
				
				<tr class="fila-det-bold">
				  <td height="38" class="fila-det-border">Fecha Actual: </td>
				  <td class="fila-det-border">&nbsp;<%=fechaActual%> </td>
			  </tr>				
				<tr class="fila-det-bold">
				  <td height="38" class="fila-det-border">Fecha de Caja: </td>
				  <td class="fila-det-border">&nbsp;<%=tesoFechaCaja%> </td>
			  </tr>
				<tr class="fila-det-bold">
					<td height="34" class="fila-det-border">Ejercicio Activo:</td>
					<td class="fila-det-border">&nbsp;<%=ejercicioActivo%></td>
				</tr>
				<tr class="fila-det-bold">
					<td height="38" class="fila-det-border">Vigencia desde:</td>
					<td class="fila-det-border">&nbsp;<%=Common.setObjectToStrOrTime(Timestamp.valueOf(fechaEjercicioActivoDesde), "JSTsToStr")%></td>
				</tr>
				<tr class="fila-det-bold">
					<td height="36" class="fila-det-border">Vigencia desde:</td>
					<td class="fila-det-border">&nbsp;<%=Common.setObjectToStrOrTime(Timestamp.valueOf(fechaEjercicioActivoHasta), "JSTsToStr")%></td>
				</tr>

				<tr class="fila-det-bold">
					<td height="36" class="fila-det-border">Puesto Asignado al usuario:</td>
					<td class="fila-det-border">&nbsp;<%=nombrePuesto%></td>
				</tr>
				
				<tr class="fila-det-bold">
					<td height="36" class="fila-det-border">Proximo Numero de Factura A:</td>
					<td class="fila-det-border">&nbsp;<%=numerofacturaA%></td>
				</tr>
				<tr class="fila-det-bold">
					<td height="36" class="fila-det-border">Proximo Numero de Factura B:</td>
					<td class="fila-det-border">&nbsp;<%=numerofacturaB%></td>
				</tr>
				<tr class="fila-det-bold">
					<td height="36" class="fila-det-border">Proximo Numero de Factura C:</td>
					<td class="fila-det-border">&nbsp;<%=numerofacturaC%></td>
				</tr>
				
				<tr class="fila-det-bold">
					<td height="36" class="fila-det-border">Proximo Numero de Recibo:</td>
					<td class="fila-det-border">&nbsp;<%=numerorecibo%></td>
				</tr>

				<tr class="fila-det-bold">
					<td height="36" class="fila-det-border">Proximo Numero de Remito 1:</td>
					<td class="fila-det-border">&nbsp;<%=numeroremitos1%></td>
				</tr>
				<tr class="fila-det-bold">
					<td height="36" class="fila-det-border">Proximo Numero de Remito 2:</td>
					<td class="fila-det-border">&nbsp;<%=numeroremitos2%></td>
				</tr>
				<tr class="fila-det-bold">
					<td height="36" class="fila-det-border">Proximo Numero de Remito 3:</td>
					<td class="fila-det-border">&nbsp;<%=numeroremitos3%></td>
				</tr>
				<tr class="fila-det-bold">
					<td height="36" class="fila-det-border">Proximo Numero de Remito 4:</td>
					<td class="fila-det-border">&nbsp;<%=numeroremitos4%></td>
				</tr>
			</table>		
			
			
			
		</td>
  </tr>
</table>

</body>
</html>
<% 
}catch (Exception e){

  System.out.println("ERROR-principal.jsp: " + e);

}

 %>