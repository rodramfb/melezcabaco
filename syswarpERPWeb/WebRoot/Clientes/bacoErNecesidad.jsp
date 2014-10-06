<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: 
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Nov 14 14:50:18 GMT-03:00 2006 
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
String titulo = "Necesidad Entregas Regulares";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterNecesidad   = null;
int totCol = 9; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BCA"  class="ar.com.syswarp.web.ejb.BeanBacoErNecesidad" scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BCA" property="*" />
<%
 BCA.setResponse(response);
 BCA.setRequest(request);
 BCA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BCA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 <link rel="stylesheet" href="../imagenes/default/erp-style.css">
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script type="text/JavaScript">
<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}
//-->
 </script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Codigo";
tituCol[1] = "Depósito";
tituCol[2] = "codiigo_st";
tituCol[3] = "Articulo";
tituCol[4] = "Necesidad";
tituCol[5] = "Generado";
tituCol[6] = "Disponible";
tituCol[7] = "Reserva";
tituCol[8] = "Preferencia";
java.util.List Necesidad = new java.util.ArrayList();
Necesidad= BCA.getNecesidadList();
iterNecesidad = Necesidad.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="bacoErNecesidad.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td width="11%" height="60"  class="text-globales"><%=titulo%></td>
                </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BCA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="2" cellpadding="1"   >
  <tr class="fila-encabezado">
    <td colspan="3" >&nbsp;</td>
    <td colspan="4" ><div align="center">Cantidades</div></td>
    </tr>
  <tr class="fila-encabezado">
     <td width="3%" ><%=tituCol[0]%></td>
     <td width="22%" ><%=tituCol[1]%></td>
     <td width="48%" ><%=tituCol[3]%></td>
     <td width="13%" ><div align="right"><%=tituCol[4]%></div></td>
     <td width="6%" ><div align="right"><%=tituCol[5]%></div></td>
     <td width="5%" ><div align="right"><%=tituCol[6]%></div></td>
     <td width="3%" ><div align="right"><%=tituCol[7]%></div></td>
  </tr>
   <%int r = 0;
   //System.out.println("ANTES DEL ITER: " );
   String preferencia = "";
   while(iterNecesidad.hasNext()){
      ++r;
	  //System.out.println("ITERANDO: " + r);
      String[] sCampos = (String[]) iterNecesidad.next(); 
      // estos campos hay que setearlos segun la grilla 
      if(!preferencia.equalsIgnoreCase (sCampos[8])){
        if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
        else color_fondo = "fila-det-verde";
        preferencia = sCampos[8]; 


  %>
   <tr class="text-dos-bold" > 
      <td colspan="7"  >Preferencia:  
        <%= preferencia.toUpperCase() %>
      </td>
    </tr>
<%
      }
%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[2]%> - <%=sCampos[3]%></td>
      <td bgcolor="#F0F0FF" class="fila-det-border" ><div align="right"><%=sCampos[4]%></div></td>
      <td bgcolor="#F0F0FF" class="fila-det-border" ><div align="right"><%=sCampos[5]%></div></td>
      <td bgcolor="#FFFFCC" class="fila-det-border" ><div align="right"><%=sCampos[6]%></div></td>
      <td bgcolor="#FFFFCC" class="fila-det-border" ><div align="right"><%=sCampos[7]%></div></td>
   </tr>
<%
   }%>
   </table>
     <input name="anio" value="<%= BCA.getAnio() %>" type="hidden">
     <input name="idmes" value="<%= BCA.getIdmes() %>" type="hidden">
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

