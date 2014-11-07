<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: contableInfiPlan
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Oct 30 12:11:01 GMT-03:00 2006 
   Observaciones: 
      .


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.*" %> 
<%@ page import="java.math.*"%>
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "BUSQUEDA DE CUENTAS CONTABLES";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterContableInfiPlan   = null;
int totCol = 8; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BCIPA"  class="ar.com.syswarp.web.ejb.BeanContableInfiPlanLov"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BCIPA" property="*" />
<%
 BCIPA.setResponse(response);
 BCIPA.setRequest(request);
 BCIPA.setSession(session);
 // int ejercicioActivo =  Integer.parseInt( (String)session.getAttribute("ejercicioActivo") );
 BCIPA.setEjercicioactivo( Integer.parseInt( session.getAttribute("ejercicioActivo").toString() ) );
 BCIPA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BCIPA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script >
<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function eliminarRegistro(){
  if(confirm("Confirma eliminar selección ?")){
		  document.frm.accion.value = "eliminar";
		  document.frm.submit();
		}
}

function confirmarDetalle(){
  if(confirm("Confirma datos ingresados ?")){
		  document.frm.accion.value = "confirmar";
		  document.frm.submit();
		}
}
//-->
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
tituCol[6] = "cent_cost";
tituCol[7] = "cent_cost1";
java.util.List ContableInfiPlan = new java.util.ArrayList();
ContableInfiPlan= BCIPA.getContableInfiPlanList();
iterContableInfiPlan = ContableInfiPlan.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="lov_contableinfiplan.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr class="text-globales">
                   <td><%=titulo%></td>
                </tr>
                <tr>
                   <td width="89%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BCIPA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">
                           </td>
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
                                                <%for(i=10; i<= 100 ; i+=10){%>
                                                    <%if(i==BCIPA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BCIPA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BCIPA.getTotalRegistros()%>">Todos</option>
                                             </select>
                                          </td>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BCIPA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="5%" >
		   <div align="center">
       <input name="agregar" id="agregar" value="agregar" type="image" src="../imagenes/default/nuevo.gif"  onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('agregar','','../imagenes/default/nuevo.gif',1)" onClick="document.frm.accion.value = this.name">
		  </div>		 </td>
     <td width="13%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="39%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="12%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="12%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
     <td width="9%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
   </tr>
   <%int r = 0;
   while(iterContableInfiPlan.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterContableInfiPlan.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><div align="center">
			  <% if(str.esNulo(sCampos[2]).equalsIgnoreCase("s")){ %>
				<input type="radio" name="idcuenta" value="<%= sCampos[0]%>">
				<% } %> 
			  &nbsp;</div>
			</td> 
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[2]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[3]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[4]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[5]%>&nbsp;</td>
   </tr>
<%
   }%>
  </table>
	 
	 
	 <% 
   Hashtable htCuentas = (Hashtable) session.getAttribute("htCuentas");
	 if(htCuentas != null && !htCuentas.isEmpty() ){
	   %> 	 
		 &nbsp; 
		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr  class="text-globales" height="3">
				<td colspan="4"> </td>
			</tr>		
			<tr  class="fila-det-bold">
			 <td width="5%">&nbsp;Sel.</td>
				<td width="24%">&nbsp; Cód.</td>
				<td width="50%">&nbsp; Descripción </td>
				<td width="21%">Total a Imputar  </td>
		  </tr>
			<tr  class="text-globales" height="3">
				<td colspan="4"> </td>
			</tr>		
		<%
	   			Enumeration en = htCuentas.keys();
					while (en.hasMoreElements()) {
						String key = (String) en.nextElement();
						String [] cuentas = (String []) htCuentas.get(key);
						  %>
		<tr class="fila-det">
			<td class="fila-det-border"><input name="delKey" type="checkbox" id="delKey" value="<%= key %>" class="campo"></td>
			<td class="fila-det-border">&nbsp;<%= cuentas[0] %><input  name="keyHashCuenta" type="hidden" value="<%= key %>"></td>
			<td class="fila-det-border">&nbsp;<%= cuentas[1] %></td>
			<td class="fila-det-border"><input type="text" class="campo" name="totalImputacionCuenta" value="<%= cuentas[2] %>" size="10"></td>
		 </tr>							
		<%
					}
	  %>
			<tr  class="text-globales" height="3">
				<td colspan="4"> </td>
			</tr>					
			<tr  class="fila-det-bold">
			 <td height="40" colspan="4">
			   <input name="eliminar" type="button" class="boton" id="eliminar" value="Eliminar" onClick="eliminarRegistro();"> 			 <input name="confirmar" type="button" class="boton" id="confirmar" value="Confirma" onClick="confirmarDetalle();"></td>
				</tr>			
	</table>
<% } %>
	 			<%  
			if(BCIPA.getAccion().equalsIgnoreCase("confirmar")  
			   && BCIPA.getMensaje().equals("") ){%>
			  <script>
					   opener.document.frm.submit();
							this.close();
				</script>			
			<%  
			}%>
	 
   <input name="accion" value="" type="hidden">
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

