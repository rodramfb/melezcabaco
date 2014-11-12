<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: proveedoArticulos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Aug 02 14:39:14 GMT-03:00 2006 
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
String titulo = "Cuentas Contables";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterProveedoArticulos   = null;
int totCol = 7; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
String ejercicio  = session.getAttribute("ejercicioActivo").toString();

%>
<html>
<jsp:useBean id="BPAA"  class="ar.com.syswarp.web.ejb.BeanProveedoCtasContablesAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BPAA" property="*" />
<%
 
 BPAA.setResponse(response);
 BPAA.setRequest(request);
 BPAA.setSession(session);
 BPAA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BPAA.setEjercicio(  Integer.parseInt(session.getAttribute("ejercicioActivo").toString() ) ) ; 
 BPAA.ejecutarValidacion();
 
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script >
<!--

var indexCmbPlantilla = -1;

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


function agregarRegistro(idcuenta){

  document.frm.idcuenta.value = idcuenta;
  // document.frm.codigo_st.value = idcuenta;
  document.frm.accion.value = 'agregar';
  document.frm.submit();

}


function asignarConceptosPlantilla(cmbPlantilla){

  if(confirm('Cualquier dato cargado o asignado previamente será eliminado!!\nConfirma asignar conceptos de plantilla:' + cmbPlantilla.options[cmbPlantilla.selectedIndex].text + ' ? ')){

  document.frm.accion.value = 'asignarplantilla';
  document.frm.submit();  
  
  }else{
    document.frm.idplantilla.selectedIndex = indexCmbPlantilla;
  }

}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}


window.onload = function (){
  indexCmbPlantilla = document.frm.idplantilla.selectedIndex;
}

//-->
</script>
</head>
<%

/*			<!-- codigo_st,alias_st,descrip_st,descri2_st, "
				+ "cost_re_st, cost_uc_st, cost_pp_st,precipp_st, ultcomp_st-->*/
// titulos para las columnas
tituCol[0] = "Cod.";
tituCol[1] = "Cuenta";
tituCol[2] = "Imp";
tituCol[3] = "Nivel";
tituCol[4] = "Ajustable";
tituCol[5] = "Resultado";
java.util.List ProveedoArticulos = new java.util.ArrayList();
ProveedoArticulos= BPAA.getProveedoArticulosList();
iterProveedoArticulos = ProveedoArticulos.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="lov_cuentas_contables.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                   <td width="89%" height="38">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BPAA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">
                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BPAA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=10; i<= 100 ; i+=10){%>
                                                    <%if(i==BPAA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BPAA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BPAA.getTotalRegistros()%>">Todos</option>
                                             </select>
                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BPAA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BPAA.getPaginaSeleccion() ){%>
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
                         <tr>
                           <td height="26" class="text-globales">Plantilla</td>
                           <td><span class="fila-det-border">
                             <select name="idplantilla" id="idplantilla" class="campo" onChange="asignarConceptosPlantilla(this);">
                               <option value="-1">Seleccionar</option>
                               <% 
						Iterator iter = BPAA.getListPlantilla().iterator();
						int j = 0;
						while(iter.hasNext())
						{

							String datos[] = (String[]) iter.next();
							
					%>
                               <option value="<%= datos[0] %>" <%= BPAA.getIdplantilla().toString().equalsIgnoreCase(datos[0]) ? "selected" : "" %>><%= datos[1] %></option>
                               <%	}%>
                             </select>
                           </span></td>
                           <td>&nbsp;</td>
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
   <td class="fila-det-bold-rojo">     <jsp:getProperty name="BPAA" property="mensaje"/>     </td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="2%" >&nbsp;</td>
     <td width="18%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="46%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="8%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="9%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="8%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
     <td width="9%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
   </tr>
   <%int r = 0;
   while(iterProveedoArticulos.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterProveedoArticulos.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";
	  String patron = sCampos[3].equals("1") ? " " : " . ";
	  %>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" >
        <div align="center">
          <% 
	  if(Common.setNotNull(sCampos[2]).equalsIgnoreCase("S")){ %>
          <img src="../imagenes/default/nuevo.gif" width="16" height="16" onClick="agregarRegistro('<%=sCampos[0]%>')" title="Agregar registro!"> 
          <% 
	  }else{
	   %>
          <img src="../imagenes/default/gnome_tango/colors.gray.icon.gif" width="8" height="8" title="Cuenta no imputable!">
          <% 
	   } %>
        </div></td>
      <td class="fila-det-border" ><span><%=Common.getNivelStr( patron , Integer.parseInt(sCampos[3])    ) + sCampos[0] %></span></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[1]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[2]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[3]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[4]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[5]%></td>		
   </tr>
<%
   }%>
  </table>

<%! 



 %>

		 &nbsp; 
		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr  class="text-globales" height="3">
				<td colspan="10"> </td>
			</tr>		
			
 


			<tr  class="fila-det-bold">
			 <td width="3%">&nbsp;Sel.</td>
				<td width="4%"><div align="right">Cód.</div></td>
				<td width="62%">&nbsp;Cuenta</td>
			    <td width="17%">&nbsp;Tipo</td>				
				<td width="14%"><div align="right">Importe</div></td>
			</tr>
			<tr  class="text-globales" height="3">
				<td colspan="10"> </td>
			</tr>	
			
	 <% 
			
	//Lleno mi hashtable htPlanesContables con la var de sesion #htPlanesContables
   Hashtable htPlanesContables = (Hashtable) session.getAttribute("htPlanesContables");
   if(htPlanesContables != null && !htPlanesContables.isEmpty()){
	   %> 				
				
		<%
	   			Enumeration en = htPlanesContables.keys();
					while (en.hasMoreElements()) {
						String key = (String) en.nextElement();
						String [] planesContables = (String []) htPlanesContables.get(key);
						/*
						Hashtable htDepositos = BPAA.getHtDepositos();
						Enumeration enumDepositos = htDepositos.keys();*/

						  %>
		<tr class="fila-det">
			<td class="fila-det-border"><input name="delKey" type="checkbox" id="delKey" value="<%= key %>" class="campo"></td>
			<td class="fila-det-border"><div align="right"><%= planesContables[0] %>
		      <input  name="keyHashDatosPlanesContables" type="hidden" value="<%= key %>">
	      </div></td>
			<td class="fila-det-border">&nbsp;<%= planesContables[1] %></td>
		 	<td class="fila-det-border">
				<select name="tipo" id="tipo" class="campo">
					<option value="-1">Seleccionar</option>
					<% 
						iter = BPAA.getListTipificacion().iterator();
						j = 0;
						while(iter.hasNext())
						{

							String keyTipo[] = (String[]) iter.next();
							
					%>
					<option value="<%= keyTipo[0] %>" <%= planesContables[9].equalsIgnoreCase(keyTipo[0]) ? "selected" : "" %>><%= keyTipo[1] %></option>
					
					<%	}%>
				</select>
			</td>
			<td class="fila-det-border">&nbsp;<input type="text" class="campo" name="precio" value="<%=planesContables[11]%>" size="10"></td>
		</tr>							
				<%}%>
		<tr  class="text-globales" height="3">
			<td colspan="10"> </td>
		</tr>					
<% } %>
		<tr class="fila-det-bold">
			<td height="40" colspan="8">
				<input name="eliminar" type="button" class="boton" id="eliminar" value="Eliminar" onClick="eliminarRegistro();">
				<input name="confirmar" type="button" class="boton" id="confirmar" value="Confirma" onClick="confirmarDetalle();">			</td>
		</tr>			
	</table>

	<input name="accion" value="" type="hidden">
	<!--input type="hidden" name="codigo_st" id="codigo_st" value=""-->
	<input type="hidden" name="idcuenta" id="idcuenta" value="">
	<%
	if(BPAA.getAccion().equalsIgnoreCase("confirmar") && BPAA.getMensaje().equals("") ){%>
		<script>
			opener.document.frm.submit();
			this.close();
		</script>			
	<%}%>
</form>
</body>
</html>
<% 
}catch (Exception ex) {
   java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
   java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
   ex.printStackTrace(pw);
  System.out.println("ERROR (" + pagina + ") : "+ex);   
}%>

