<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: lov_cuentas_exento
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
String titulo = " CUENTAS EXENTO";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterLov_cuentas_exento   = null;
int totCol = 8; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
int ejercicioActivo = Integer.valueOf(session.getAttribute("ejercicioActivo").toString()).intValue();

%>
<html>
<jsp:useBean id="BLCEA"  class="ar.com.syswarp.web.ejb.BeanLov_cuentas_exentoAbm"   scope="page"/>


<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BLCEA" property="*" />
<%

 BLCEA.setResponse(response);
 BLCEA.setRequest(request);
 BLCEA.setSession(session);
 BLCEA.setIdejercicio( Integer.valueOf(session.getAttribute("ejercicioActivo").toString()).intValue());
 BLCEA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BLCEA.ejecutarValidacion();

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
java.util.List Lov_cuentas_exento = new java.util.ArrayList();
Lov_cuentas_exento= BLCEA.getLov_cuentas_exentoList();
iterLov_cuentas_exento = Lov_cuentas_exento.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="lov_cuentas_exento.jsp" method="POST" name="frm">
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
                              <input name="ocurrencia" type="text" value="<%=BLCEA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">
                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BLCEA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=10; i<= 100 ; i+=10){%>
                                                    <%if(i==BLCEA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BLCEA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BLCEA.getTotalRegistros()%>">Todos</option>
                                             </select>
                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BLCEA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BLCEA.getPaginaSeleccion() ){%>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BLCEA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="3%" ><span class="fila-det-bold-rojo">
       <input name="agregar" id="agregar" value="agregar" type="image" src="../imagenes/default/btn_add_norm.gif"  onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('agregar','','../imagenes/default/btn_add_over.gif',1)" onClick="document.frm.accion.value = this.name">
     </span></td>
     <td width="18%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="33%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="24%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="22%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
    </tr>
   <%int r = 0;
   while(iterLov_cuentas_exento.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterLov_cuentas_exento.next(); 
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
   Hashtable htNetoExentoConfirma = (Hashtable) session.getAttribute("htNetoExentoConfirma");
	 if(htNetoExentoConfirma != null && !htNetoExentoConfirma.isEmpty() ){
	   %> 	 
		 &nbsp; 
		<table width="100%"  border="0" cellspacing="2" cellpadding="0">
			<tr  class="text-globales" height="6">
				<td colspan="10"> </td>
			</tr>			
			<tr  class="fila-det-bold">
			 <td width="3%">&nbsp;Sel.</td>
				<td width="14%">&nbsp; Cuenta.</td>
				<td width="49%">&nbsp; Descripcion </td>
				<td width="16%"> <div align="right">Debe </div></td>
	
			  <td width="18%"><div align="right">Haber</div></td>
			</tr>
		<%
	   			Enumeration en = htNetoExentoConfirma.keys();
					while (en.hasMoreElements()) {
						String key = (String) en.nextElement();
						String [] asientoNE = (String []) htNetoExentoConfirma.get(key);
            int tipomov = BLCEA.getTipomov().intValue(); 
						  %>
		<tr class="fila-det">
			<td class="fila-det-border"><input name="delKey" type="checkbox" id="delKey" value="<%= key %>" class="campo"></td>
			<td class="fila-det-border">&nbsp;<%= asientoNE[0] %><input  name="keyHashDatosCuenta" type="hidden" value="<%= key %>" ></td>
			<td class="fila-det-border">&nbsp;<%= asientoNE[1] %></td>
			<td class="fila-det-border"><div align="right"><%=   tipomov != 3 ? "<input type=\"text\" class=\"campo\" name=\"totalAsiento\" value=\"" + asientoNE[2] + "\" size=\"10\" style=\"text-align:right\">"   : "0"  %></div></td>
		  <td class="fila-det-border"><div align="right"><%=   tipomov == 3 ? "<input type=\"text\" class=\"campo\" name=\"totalAsiento\" value=\"" + asientoNE[2] + "\" size=\"10\" style=\"text-align:right\">"   : "0"  %></div></td>
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
   <input name="TNExento" value="<%= BLCEA.getTNExento() %>" type="hidden">
	 <input name="flagCuentas" value="lov" type="hidden">
   <input name="tipomov" value="<%=  BLCEA.getTipomov() %>" type="hidden">	  
	 
		<%  
	if(BLCEA.getAccion().equalsIgnoreCase("confirmar")    
		 && BLCEA.getMensaje().equals("") ){%>
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

