<%@ page contentType="text/html; charset=windows-1252" %>
<%@page language="java" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%> 
<%
 	String resultado = "INGRESAR VALORES"; 
 try {
  
     String ejecutar = request.getParameter("ejecutar");
 	String iinbin = request.getParameter("iinbin");

 	Strings str = new Strings();
 	String idempresa = str.esNulo( session.getAttribute("empresa") + "" );
     if(idempresa.equals("")) idempresa = "1";
 	javax.naming.Context context = new javax.naming.InitialContext();
 	Object object = context.lookup("Clientes");
 	ClientesRemote sHome = (ClientesRemote) javax.rmi.PortableRemoteObject.narrow(object, ClientesRemote.class);
 	Clientes repo = sHome.create();
     System.out.println("PASO PREVIO A getPorcentajeDescuentoIinBin ");
     if(Common.setNotNull(ejecutar).equalsIgnoreCase("SI") ){
 	  float porcentaje = repo.getPorcentajeDescuentoIinBin(iinbin);
       System.out.println("LLAMANDO A getPorcentajeDescuentoIinBin ");
 	  resultado =   "EL valor ingresado (" + iinbin + ") " +  ( porcentaje > 0  ?  " SI " : "NO" ) + "  corresponde a una tarjeta BBVA / "  + porcentaje ; 
       System.out.println("ES TJTA BBVA " + iinbin + ":  " + resultado); 
     }
 }
 catch (Exception e) { 
 		e.printStackTrace(); 
 		System.out.println("Error:" +e.getMessage()); 
 }
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252" />
<title>Documento sin t&iacute;tulo</title>
</head>

<body>
<form id="form1" name="form1" method="post" action="">
  <label></label>
  <label> <br />
  RESULTADO: <%= resultado %><br />
  <br />
  IINBIN
  <input name="iinbin" type="text" id="iinbin" />
  <br />
  <br />
  <input name="ejecutar" type="checkbox" id="ejecutar" value="SI" />
  ejecutar</label>      
  <input type="submit" name="Submit" value="Enviar" />
</form>
</body>
</html>