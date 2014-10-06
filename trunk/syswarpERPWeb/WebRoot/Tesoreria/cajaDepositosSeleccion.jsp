<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: globalentidadesasociables
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue May 06 14:29:25 CEST 2008 
   Observaciones: 
      .


*/ 
%>
<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.Iterator" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%@ page import="java.math.BigDecimal" %>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "DEPOSITOS";
String mensaje = "Está trabajando con la caja al día.";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
BigDecimal idempresa =   new BigDecimal( session.getAttribute("empresa").toString() ); 
// variables de paginacion
String fechaActual = Common.initObjectTimeStr();
String tesoFechaCaja = Common.getGeneral().getValorSetupVariablesNoStatic("tesoFechaCaja",  idempresa );
if(!fechaActual.equals(tesoFechaCaja)) 
  mensaje = "La fecha de caja no coincide con la del día, si desea trabajar con la caja al día, por favor modifique dicha fecha.";

%>
<html>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<head>
<title><%=titulo%></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 <link rel="stylesheet" href="../imagenes/default/erp-style.css">
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script type="text/JavaScript">

 function enviarFormulario(destino){
   
	 document.frm.action = destino;
	 document.frm.submit();
 
 }

 </script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" class="text-globales">
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="" method="POST" name="frm" target="bottomDepositosFrame">
<input name="tesoFechaCaja" type="hidden" value="<%= tesoFechaCaja %>">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="25"  class="text-globales"><%=titulo%> </td>
                </tr>
                <tr>
                   <td height="3"  class="permiso-cinco"> </td>
                </tr>
                <tr>
                  <td width="12%" height="38"> 
									<table width="100%" border="0">
                    <tr>
                      <td width="22%" height="43" class="text-globales"> Tipo de Dep&oacute;sito a Efectuar: </td>
                      <td width="25%" class="text-globales">
					  <select name="limit" class="campo" onChange="enviarFormulario(this.value)">
                          <option value="cajaDepositosIndex.jsp" selected>Seleccionar</option>
                          <option value="cajaDepositosEfectivo.jsp" >Efectivo</option>
                          <option value="cajaDepositosCheque.jsp">Cheques</option>
                          <option value="cajaDepositosIndex.jsp">Documentos</option>
                          <option value="cajaDepositosIndex.jsp">Tarjetas</option>
                      </select></td>
                      <td width="15%" class="text-globales">Fecha de Caja: </td>
                      <td width="14%" class="text-globales"><%=tesoFechaCaja%></td>
                      <td width="13%" class="text-globales">Fecha Actual:</td>
                      <td width="11%" class="text-globales"><%=fechaActual%></td>
                    </tr>
                  </table>
									</td>
                </tr>
								<tr > 
									<td  > <font class="text-globales"><%= mensaje %></font>  </td>
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

