<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: 
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Feb 13 09:18:39 GMT-03:00 2007 
   Observaciones: 
      .


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.Iterator" %> 
<%@ page import="java.math.*"%> 
<%@ page import="ar.com.syswarp.api.*" %>
<%@ page import="ar.com.syswarp.ejb.*" %> 
<%@ include file="session.jspf"%>
<%
try{
// captura de variables comunes

Strings str = new Strings();
GeneralBean gb = new GeneralBean();

String color_fondo ="";
String titulo = "CALCULO DE NECESIDAD DE PRODUCCION";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterProduccionEsquemas   = null;
int totCol = 5; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BPCN"  class="ar.com.syswarp.web.ejb.BeanProduccionCalculoNecesidadImprime"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BPCN" property="*" />
<%
 BPCN.setResponse(response);
 BPCN.setRequest(request);
 BPCN.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BPCN.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel = "stylesheet" href = "<%= pathskin %>">
 
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
</head>
<%
// titulos para las columnas
tituCol[0] = "";
tituCol[1] = "";
tituCol[2] = "";
tituCol[3] = "";
tituCol[4] = "";
java.util.List ProduccionEsquemas = new java.util.ArrayList();
ProduccionEsquemas= BPCN.getProduccionEsquemasList();
iterProduccionEsquemas = ProduccionEsquemas.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="produccionCalculoNecesidad.jsp" method="POST" name="frm">
<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0" >
  <tr >
    <td >
			<table width="100%" border="0" cellspacing="0" cellpadding="0" >
				<tr class="fila-det">
					<td >
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td  class="fila-det">&nbsp;
									 </td> 
                </tr>
                <tr>
                  <td width="13%" height="38">									 
									<table width="100%" border="0" cellpadding="0" cellspacing="1">
                     <tr bgcolor="#CCCCCC" >
                      <td colspan="2" height="3"></td>
                     </tr>
                    <tr class="fila-det">
                      <td width="11%"  >Orden Nro. : </td>
                      <td width="85%"><%= BPCN.getIdop() %> </td>
                    </tr>
                    <tr class="fila-det">
                      <td >Estado:</td>
                      <td><%= BPCN.getEstado() %></td>
                    </tr>										
                    <tr class="fila-det">
                      <td >Fecha de Emisi&oacute;n:</td>
                      <td><%= BPCN.getFecha_emision() %></td>
                    </tr>
                    <tr class="fila-det">
                      <td >Fecha Prometida:</td>
                      <td><%= BPCN.getFecha_prometida() %></td>
                    </tr>
                    <tr class="fila-det">
                      <td >Articulo:</td>
                      <td><%= BPCN.getCodigo_st() %> - <%= BPCN.getDescrip_st() %></td>
                    </tr>
                    <tr class="fila-det">
                      <td >Dep&oacute;sito:</td>
                      <td><%= BPCN.getDescrip_dt() %></td>
                    </tr>
                    <tr class="fila-det">
                      <td >Cantidad:</td>
                      <td><%= BPCN.getCantidad() %></td>
                    </tr>
                    <tr class="fila-det">
                      <td >Observaciones:</td>
                      <td><%= BPCN.getObservaciones() %></td>
                    </tr>
                    <tr bgcolor="#CCCCCC" >
                      <td colspan="2" height="3"></td>
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
			 <td class="fila-det-bold-rojo"><jsp:getProperty name="BPCN" property="mensaje"/></td>
			</tr>
		</table>
   <%
	 //idesquema,esquema,codigo,descripcion,tipo,descripciontipo,
	 //6 costo, 7 costo*3 AS costototal,
   //8 cantidadinsume, 9 cantidadinsume*3 AS cantidadtotalinsume, 10 cantiddeposito, 11 cantiddeposito  AS remanente, 1 AS nivel
	 int r = 0;
	 boolean esPrimero = true;
	 BigDecimal costoTotal = new BigDecimal(0);
   while(iterProduccionEsquemas.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterProduccionEsquemas.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
			<table width="100%" border="0" cellspacing="1" cellpadding="1"  >
			<% 
			 if(esPrimero ){
			   esPrimero = false;
			 %>
				<tr class="fila-det">
				  <td   >&nbsp;<%=str.getNivelStr("-", Integer.parseInt(sCampos[12]) ) + " > " + sCampos[0] + " - " + sCampos[1].toUpperCase()%></td>
			  </tr>
			<%} %>
			<% 
			 if( sCampos[4].equalsIgnoreCase("E")){
			 %>
			
				<tr class="fila-det">
				  <td  >&nbsp;<%=str.getNivelStr("-", Integer.parseInt(sCampos[12]) * 2) + " > " + sCampos[2] + " - " + sCampos[3].toUpperCase()%></td>
			  </tr>
			<%
			   continue;
			 } %>
				 <tr > 
						<td class="fila-det-border" >
							<table width="100%"  border="0" cellspacing="1" cellpadding="0">
								<tr class="fila-det-bold"  >
								  <td colspan="2" ><%= str.getNivelStr("-", Integer.parseInt(sCampos[12]) * 2) + " > " +  sCampos[0] %></td>
								  <td colspan="2" ><div align="center"> Costo	$	</div></td>
							    <td colspan="2" ><div align="center">Insume</div></td>
							    <td colspan="2" ><div align="center">Stock</div></td>
						    </tr>
								<tr> 
									<td height="21" class="fila-det"> Tipo: </td>
								  <td height="21" class="fila-det"><%=sCampos[5]%> (<%=sCampos[4]%>) </td>
								  <td width="13%" class="fila-det"><div align="right">Unidad |</div></td>
								  <td width="11%" class="fila-det"><div align="right">Total |</div></td>
								  <td width="10%" class="fila-det"><div align="right">Parcial |</div></td>
								  <td width="8%" class="fila-det"><div align="right">Total |</div></td>
								  <td width="9%" class="fila-det"><div align="right">Existente |</div></td>
								  <td width="12%" class="fila-det"><div align="right">Excedente |</div></td>
							  </tr>
								<tr>
									<td class="fila-det">C&oacute;digo: </td> 
								  <td class="fila-det"><%=sCampos[2]%></td>
								  <td width="13%" class="fila-det"><div align="right"><%=sCampos[6]%>|</div></td>
								  <td width="11%" class="fila-det"><div align="right"><%=sCampos[7]%>|</div></td>
								  <td width="10%" class="fila-det"><div align="right"><%=sCampos[8]%>|</div></td>
								  <td width="8%" class="fila-det"><div align="right"><%=sCampos[9]%>|</div></td>
								  <td width="9%" class="<%=Double.parseDouble(sCampos[10])<0 ? "fila-det-bold-rojo": "fila-det" %>"><div align="right"><%=sCampos[10]%>|</div></td>
								  <td width="12%" class="<%=Double.parseDouble(sCampos[11])<0 ? "fila-det-bold-rojo": "fila-det" %>"><div align="right"><%=sCampos[11]%>|</div></td>
							  </tr>							
								<tr class="fila-det">
									<td width="9%" rowspan="2" class="fila-det">Descripci&oacute;n: </td>
								  <td width="28%" class="fila-det" height="3px"></td> 
								  <td colspan="6" bgcolor="#006666" > </td>
							  </tr>
								<tr class="fila-det">
								  <td colspan="7" class="fila-det"><%=sCampos[3]%> </td>  
						    </tr>
							</table>
						</td>
				 </tr>
		</table>	 
<%
     costoTotal = costoTotal.add(new BigDecimal(sCampos[7])); 
   }
	 if(!esPrimero){
	 %>
				<table width="100%" border="0" cellspacing="1" cellpadding="1">
					 <tr > 
							<td class="fila-det-border" >
								<table width="100%"  border="0" cellspacing="1" cellpadding="0">
									<tr class="fila-det-bold"  >
										<td width="54%" >Costo Total Calculado $ </td>
										<td width="15%" bgcolor="#CCCCCC" ><div align="right"><%=costoTotal%></div></td>
										<td >&nbsp;</td>
									</tr>
								</table>
							</td>
					 </tr>
					 <tr >
					   <td   >&nbsp;</td>
			    </tr>
					 <tr >
					   <td   ><table width="100%"  border="0" cellspacing="0" cellpadding="0">
               <tr class="fila-det">
                 <td>Fecha: <%= BPCN.getFechaImpresion() %></td>
                 <td>Usuario:<%= session.getAttribute("usuario").toString()  %>  </td>
               </tr>
             </table></td>
			    </tr>
					 <tr >
					   <td   >&nbsp;</td>
			    </tr>
				</table>	
<%  
	 }
%>			</td>
	 </tr>
</table> 	 
		<input name="accion" value="" type="hidden">
		<input name="idesquema" value="<%= BPCN.getIdesquema() %>" type="hidden">
		<input name="idop" value="<%= BPCN.getIdop() %>" type="hidden">		
		

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