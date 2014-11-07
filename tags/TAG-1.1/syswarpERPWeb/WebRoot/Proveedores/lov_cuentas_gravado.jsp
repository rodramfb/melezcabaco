<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: lov_cuentas_gravado
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Aug 17 11:09:27 GMT-03:00 2006 
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
String titulo = " CUENTAS GRAVADO";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterLov_cuentas_gravado   = null;
int totCol = 8; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();

%>
<html>
<jsp:useBean id="BLCGA"  class="ar.com.syswarp.web.ejb.BeanLov_cuentas_gravadoAbm"   scope="page"/>


<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BLCGA" property="*" />
<%

 BLCGA.setResponse(response);
 BLCGA.setRequest(request);
 BLCGA.setSession(session);

 BLCGA.setIdejercicio( Integer.parseInt( session.getAttribute("ejercicioActivo").toString() ) );
 
 BLCGA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BLCGA.ejecutarValidacion();

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
tituCol[4] = "ajustable";
tituCol[5] = "resultado";
tituCol[6] = "cent_cost";
tituCol[7] = "cent_cost1";
java.util.List Lov_cuentas_gravado = new java.util.ArrayList();
Lov_cuentas_gravado= BLCGA.getLov_cuentas_gravadoList();
iterLov_cuentas_gravado = Lov_cuentas_gravado.iterator();
/*
 Enumeration e = request.getParameterNames();
 while (e.hasMoreElements()){
   String name = (String)e.nextElement();
   System.out.println("NAME: " + name);
 }
 */
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="lov_cuentas_gravado.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td><%=titulo%></td>
                </tr>
                <tr>
                   <td width="89%" height="38">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BLCGA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">
                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BLCGA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=10; i<= 100 ; i+=10){%>
                                                    <%if(i==BLCGA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BLCGA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BLCGA.getTotalRegistros()%>">Todos</option>
                                             </select>
                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BLCGA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BLCGA.getPaginaSeleccion() ){%>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BLCGA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="3%" >
		 <% if (session.getAttribute("htArticulosConfirmados") == null) {%>
			 <span class="fila-det-bold-rojo">
				 <input name="agregar" id="agregar" value="agregar" type="image" src="../imagenes/default/btn_add_norm.gif"  onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('agregar','','../imagenes/default/btn_add_over.gif',1)" onClick="document.frm.accion.value = this.name">
			 </span>
		 <% }%>			 
		 </td>
     <td width="18%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="33%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="24%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="22%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
    </tr>
   <%int r = 0;
   while(iterLov_cuentas_gravado.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterLov_cuentas_gravado.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" >&nbsp;
			 <% if(sCampos[2].equalsIgnoreCase("s")){ %>
			  <input type="radio" name="idcuenta" value="<%= sCampos[0]%>">
			<% }  %>
			</td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[0]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[1]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[2]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[3]%></td>
    </tr>
<%
   }%>
  </table>

	 <% 
   Hashtable htNetoGravadoConfirma = (Hashtable) session.getAttribute("htNetoGravadoConfirma");
	 if(htNetoGravadoConfirma != null && !htNetoGravadoConfirma.isEmpty() ){
	   %> 	 
		 &nbsp; 
		<table width="100%"  border="0" cellspacing="2" cellpadding="0">
			<tr  class="text-globales" height="6">
				<td colspan="10"> </td>
			</tr>	
		
			<tr  class="fila-det-bold">
			 <td width="6%">&nbsp;Sel.</td>
				<td width="13%">&nbsp; Cuenta.</td>
				<td width="45%">&nbsp; Descripcion </td>
				<td width="17%"> <div align="right">Debe </div></td>
	
			  <td width="19%"><div align="right">Haber</div></td>
			</tr>
		<%
	   			Enumeration en = htNetoGravadoConfirma.keys();
					while (en.hasMoreElements()) {
						String key = (String) en.nextElement();
						String [] asientoNE = (String []) htNetoGravadoConfirma.get(key);
            int tipomov = BLCGA.getTipomov().intValue();
						  %>
		<tr class="fila-det">
			<td class="fila-det-border">&nbsp;
			<% if(session.getAttribute("htArticulosConfirmados") != null) {
					 if(asientoNE[4].equals("asignable")){
			  %>
			<input name="asociar" type="image" id="asociar" onClick="document.frm.accion.value = this.name ;  document.frm.cuentaAsociar.value =  this.value ;"  value="<%= key %>" src="../imagenes/default/gnome_tango/actions/document-properties.png" width="18" height="18" title="Asociar cuenta ...">	
			<% 
					 }
			   }
			   else{%>
			  <input name="delKey" type="checkbox" id="delKey" value="<%= key %>" class="campo">
			<% }%>			</td>
			<td class="fila-det-border">&nbsp;<%= asientoNE[0] %><input  name="keyHashDatosCuenta" type="hidden" value="<%= key %>"></td>
			<td class="fila-det-border">&nbsp;<%= asientoNE[1] %></td>
			<td class="fila-det-border"><div align="right"><%=  tipomov != 3 ? "<input type=\"text\" class=\"campo\" name=\"totalAsiento\" value=\"" + asientoNE[2] + "\" size=\"10\" " +  BLCGA.getReadonlyTA() + "  style=\"text-align:right\" >" : "0" %></div></td>
		  <td class="fila-det-border"><div align="right"><%=  tipomov == 3 ? "<input type=\"text\" class=\"campo\" name=\"totalAsiento\" value=\"" + asientoNE[2] + "\" size=\"10\" " +  BLCGA.getReadonlyTA() + "  style=\"text-align:right\" >" : "0" %></div></td>
		</tr>							
		<%
					}
	  %>
			
			<tr  class="fila-det-bold">
			 <td height="40">&nbsp;</td>
			 <td height="40"><input name="eliminar" type="button" class="boton" id="eliminar" value="Eliminar" onClick="eliminarRegistro();"></td>
			 <td height="40"><input name="confirmar" type="button" class="boton" id="confirmar" value="Confirma" onClick="confirmarDetalle();"></td>
			 <td height="40">&nbsp;</td>
			 <td>&nbsp;</td>
			</tr>			
	</table>
<% } %>


   <input name="accion" value="" type="hidden"> 
   <input name="cuentaAsociar" value="" type="hidden">
   <input name="TNGravado" value="<%= BLCGA.getTNGravado() %>" type="hidden">
	 <input name="flagCuentas" value="lov" type="hidden">
   <input name="tipomov" value="<%=  BLCGA.getTipomov() %>" type="hidden">
	 
	 
		<%  
	if(BLCGA.getAccion().equalsIgnoreCase("confirmar")  
		 && BLCGA.getMensaje().equals("") ){%>
		<script>
			 opener.document.frm.submit();
			 this.close();
			</script>			
	<%  
	}%>
	 
	 
	 
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

