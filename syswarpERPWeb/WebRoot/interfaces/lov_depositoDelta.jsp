<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: globalLocalidades
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Oct 27 16:35:02 GMT-03:00 2006 
   Observaciones: 
      .


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.Iterator" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ page import="java.math.*"%>
<%//@ include file="session.jspf"%>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "Depositos";
// variables de entorno

// variables de paginacion
int i = 0;
Iterator iterdepositos  = null;
int totCol = 3; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";

%>
<html>
<jsp:useBean id="BFFF"  class="ar.com.syswarp.web.ejb.BeanStockDepositosLov"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BFFF" property="*" />
<%
 String idempresa = str.esNulo(request.getParameter("idempresa"));
 if(idempresa.equals("")) idempresa = "1";
 BFFF.setResponse(response);
 BFFF.setRequest(request);
 BFFF.setIdempresa( new BigDecimal( idempresa )); 
 BFFF.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link href="../imagenes/default/erp-style.css" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script >
function bajarDatos(deposi_ubi, d_deposi_ubi){
	for(var i = 0; i<10;i++)
	  // || opener.document.forms[i].codigo_dt 
		// Agregado EJV 20071214 llamada desde otra pagina
		if(opener.document.forms[i].deposi_ubi || opener.document.forms[i].codigo_dt)
			break;
	if(opener.document.forms[i].deposi_ubi)	{	
	  opener.document.forms[i].deposi_ubi.value = deposi_ubi;
	  opener.document.forms[i].d_deposi_ubi.value = d_deposi_ubi; 
  // Agregado EJV 20071214 llamada desde otra pagina
	}else if(opener.document.forms[i].codigo_dt)	{	
	  opener.document.forms[i].codigo_dt.value = deposi_ubi;
	  opener.document.forms[i].descrip_dt.value = d_deposi_ubi; 
	}
	window.close();
}
 </script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Codigo";
tituCol[1] = "Deposito";

java.util.List deposito = new java.util.ArrayList();
deposito = BFFF.getStockdepositosList();  
iterdepositos = deposito.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" > 
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="lov_depositoDelta.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr class="text-globales">
                   <td colspan="2"  ><%=titulo%></td>
                </tr>
                <tr>
                   <td width="3%" height="38">&nbsp;                  </td>
                   <td width="97%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BFFF.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">
                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BFFF.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BFFF.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BFFF.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BFFF.getTotalRegistros()%>">Todos</option>
                                             </select>
                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BFFF.getTotalPaginas(); i++){%>
                                                    <%if ( i==BFFF.getPaginaSeleccion() ){%>
                                                       <option value="<%=i%>" selected><%=i%></option> 
                                                    <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                    <%}%>
                                                <%}%>
                                             </select> 
                                          </td>
                                          <td width="10%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                                       </tr>
                                    </table>
                                 </td>
                              </tr>
                           </table>
                        </td>
                     </tr>
                   </table>
                </td>
            </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BFFF" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="2%" >&nbsp;</td>
     <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="40%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
   </tr>
   <%int r = 0;
   while(iterdepositos.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterdepositos.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><img src="../imagenes/default/audit.gif" width="21" height="17" onClick="bajarDatos('<%=sCampos[0]%>', '<%=sCampos[1]%>' )"></td>
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>
   </tr>
<%
   }%>
   </table>
   <input name="accion" value="" type="hidden">
	 <input name="idempresa" type="hidden" id="idempresa" value="<%= idempresa %>">	 	 
   </form>
  </body>
</html>
<% 
 }
catch (Exception ex) {
   java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
   java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
   ex.printStackTrace(pw);
  System.out.println("ERROR ( INTERFACES lov_depositoDelta.jsp) : "+ex);   
}%>

