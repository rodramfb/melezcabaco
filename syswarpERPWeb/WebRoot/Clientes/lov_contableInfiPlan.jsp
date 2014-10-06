<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: contableInfiPlan
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jun 14 15:59:44 ART 2007 
   Observaciones: 
      .


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.*" %> 
<%@ page import="java.math.*" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "PLAN DE CUENTAS";
// variables de entorno
String pathskin   = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();

// variables de paginacion
int i = 0;
Iterator iterContableInfiPlan   = null;
int totCol = 9; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BCIPA"  class="ar.com.syswarp.web.ejb.BeanContableinfiplan2Abm"   scope="page"/> 
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BCIPA" property="*" />
<%
 
 BCIPA.setResponse(response);
 BCIPA.setRequest(request);
 BCIPA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ) );
 BCIPA.setEjercicio(  new BigDecimal( (String)session.getAttribute("ejercicioActivo") )  );
 BCIPA.ejecutarValidacion();
 
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script>
  function bajarDatos( idcuenta, cuenta ){
     var i = 0;
	 var j = 0;
	 var elementos = new Array();
	 var campos = "<%= BCIPA.getCampos() %>";
	 elementos = campos.split('|');
     for(i=0;i<10;i++){
	   objeto = eval("opener.document.forms[i]." + elementos[0]); 
	   if(objeto){
	     for(j=0;j<elementos.length;j++){
		   objeto = eval("opener.document.forms[i]." + elementos[j]); 
           if(j==0 && objeto) objeto.value = idcuenta;
		   if(j==1 && objeto) objeto.value = cuenta;
		 }
		 break;
	   }
	 }
	 window.close();
   }
 </script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Cuenta";
tituCol[1] = "Descripción";
tituCol[2] = "Imputable";
tituCol[3] = "Nivel";
tituCol[4] = "Ajustable";
tituCol[5] = "Resultado";
tituCol[6] = "CC";
tituCol[7] = "CC1";
java.util.List ContableInfiPlan = new java.util.ArrayList();
ContableInfiPlan= BCIPA.getContableinfiplanList();
iterContableInfiPlan = ContableInfiPlan.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="lov_contableInfiPlan.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td colspan="2"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="11%" height="38">&nbsp;</td>
                   <td width="89%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BCIPA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BCIPA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BCIPA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BCIPA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BCIPA.getTotalRegistros()%>">Todos</option>
                                             </select>                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BCIPA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BCIPA.getPaginaSeleccion() ){%>
                                                       <option value="<%=i%>" selected><%=i%></option> 
                                                    <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                    <%}%>
                                                <%}%>
                                             </select>                                          </td>
                                          <td width="10%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                                       </tr>
                                    </table>                                 </td>
                              </tr>
                           </table>                        </td>
                     </tr>
                   </table>                </td>
            </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BCIPA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="3%" >&nbsp;</td>
     <td width="21%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="51%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="13%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="12%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
   </tr>
   <%int r = 0;
   while(iterContableInfiPlan.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterContableInfiPlan.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" >
	  <% if(str.esNulo(sCampos[2]).equalsIgnoreCase("s")){ %>
	  <img src="../imagenes/default/gnome_tango/actions/down.png" width="18" height="18" onClick="bajarDatos('<%= sCampos[0]%>', '<%= sCampos[1]%>')" border="0">
	  <% }else{ %> &nbsp; <% } %>
	  </td>
      <td class="fila-det-border" ><%= str.getNivelStr ("." , Integer.parseInt( sCampos[3] ) ) + " " + sCampos[0] %>&nbsp; </td>
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[2]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[3]%>&nbsp;</td>
   </tr>
<%
   }%>
   </table>
   <input name="accion" value="" type="hidden">
   <input name="campos" value="<%= BCIPA.getCampos() %>" type="hidden">
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

