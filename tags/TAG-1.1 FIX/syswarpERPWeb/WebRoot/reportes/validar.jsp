<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.security.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.naming.directory.*" %>
<%@ page import="java.util.Iterator" %> 
<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ page import="ar.com.syswarp.api.Common"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
String usuario = request.getParameter("usuario");
try{
   // primero guardo el usuario en session
   session.setAttribute("usuario", usuario); 
   
   Report report = Common.getReport();     
   java.util.List variables = new java.util.ArrayList();	 
   variables = report.getVariables();
   Iterator iterVariables   = null;
   iterVariables = variables.iterator();
    while(iterVariables.hasNext()){
      String[] sCampos = ( String[] ) iterVariables.next();   
      String cmp_variable = sCampos[0];
      String cmp_valor    = sCampos[1];
      session.setAttribute(cmp_variable, cmp_valor);      
    }
    response.sendRedirect("asistente1.jsp");   
   }
   catch (Exception ex) {
     java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
     java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
     ex.printStackTrace(pw);
  }  
  
%>

<html>
  <head>  

    <title>ejecutando validaciones</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
  </head>
  
  <body>

  </body>
</html>
