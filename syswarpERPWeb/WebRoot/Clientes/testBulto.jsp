<%@ page contentType="text/html; charset=windows-1252" %>
<%@page language="java" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%> 
<%
 	String resultado = "  "; 
 try {
  
     String ejecutar = request.getParameter("ejecutar");
 	String sucursal = request.getParameter("sucursal");
 	String nroremito = request.getParameter("nroremito");

 	Strings str = new Strings();
 	String idempresa = str.esNulo( session.getAttribute("empresa") + "" );
     if(idempresa.equals("")) idempresa = "1";
 	javax.naming.Context context = new javax.naming.InitialContext();
 	Object object = context.lookup("Clientes");
 	ClientesRemote sHome = (ClientesRemote) javax.rmi.PortableRemoteObject.narrow(object, ClientesRemote.class);
 	Clientes repo = sHome.create();
     System.out.println("PASO PREVIO A getCalculoBulto ");
     if(Common.setNotNull(ejecutar).equalsIgnoreCase("SI") ){
       System.out.println("LLAMANDO A getCalculoBulto ");
 	  
       System.out.println("BULTO SUCURSAL: " + sucursal  ); 
       System.out.println("BULTO REMITO  : " + nroremito  );  
 	  
 	  resultado =  repo.getCalculoBulto(new BigDecimal(sucursal), new BigDecimal(nroremito), new BigDecimal(1)) + "" ;  

       System.out.println("RESULTADO EN BULTOS " + resultado); 
     }
 }
 catch (Exception e) { 
 		e.printStackTrace(); 
 		System.out.println("Error:" +e.getMessage()); 
 }
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252" />
<title>Documento sin t&iacute;tulo</title>
</head>

<body>
<form id="form1" name="form1" method="post" action="">
  <table width="100%" border="1" cellspacing="0" cellpadding="0">
    <tr>
      <td colspan="2">RESULTADO: <%= resultado %> </td>
    </tr>
    <tr>
      <td width="10%">SUCURSAL</td>
      <td width="90%"><input name="sucursal" type="text" id="sucursal" /></td>
    </tr>
    <tr>
      <td>REMITO</td>
      <td><input name="nroremito" type="text" id="nroremito" /></td>
    </tr>
    <tr>
      <td>Confirmar</td>
      <td><input name="ejecutar" type="checkbox" id="ejecutar" value="SI" /></td>
    </tr>
    <tr>
      <td>&nbsp;<input type="submit" name="Submit" value="Enviar" /></td>
      <td>&nbsp;</td>
    </tr>
  </table>

   
    
</form>
</body>
</html>