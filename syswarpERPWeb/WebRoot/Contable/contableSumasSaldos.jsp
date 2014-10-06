<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: Balance de sumas y saldos de cuatro columnas.
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Nov 14 14:50:18 GMT-03:00 2006 
   Observaciones: 
      .


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.*" %> 
<%@ page import="ar.com.syswarp.ejb.*" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%@ page import="java.math.*" %>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "Balance de Sumas y Saldos de Cuatro Columnas";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iter   = null;
int totCol = 2; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
String clasePos = "texto-saldo-positivo";
String claseNeg = "texto-saldo-negativo";
%>
<html>
<jsp:useBean id="BCA"  class="ar.com.syswarp.web.ejb.BeanContableSumasSaldosRPT"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BCA" property="*" />
<%
 BCA.setResponse(response);
 BCA.setRequest(request);
 BCA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BCA.ejecutarValidacion();
 java.util.List listMeses = new java.util.ArrayList();
 listMeses= BCA.getListMeses();
 Iterator IterMeses;
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="contableSumasSaldos.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0"  align="center">
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="30"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr bgcolor="#FFFFFF">
                   <td height="2px"></td>
                </tr>								
                <tr>
                  <td width="11%" height="38">
									<table width="100%" border="0" align="center">
                    <tr>
                      <td height="26" class="text-globales">Ejercicio(*):</td>
                      <td><span class="fila-det-border">
                        <select name="anio" class="campo">
                          <option value="0">Seleccionar</option>
                          <%   
													  iter = BCA.getListEjercicios().iterator();                    
														while(iter.hasNext()){
															String selected = "";                      
															String[] sCampos = (String[]) iter.next(); 
															if(BCA.getAnio() == Integer.parseInt(sCampos[0])) selected = "selected";%>
                          <option value="<%=sCampos[0]%>" <%=selected%>><%=sCampos[0]%></option>
                          <%
														}%>
                        </select>
                      </span></td>
                    </tr>
                    <tr>
                      <td height="26" class="text-globales">Mes Desde(*) : </td>
                      <td><span class="fila-det-border">
                        <select name="mesDesde" class="campo">
                          <option value="0">Seleccionar</option>
                          <%
                IterMeses = listMeses.iterator(); 
								while(IterMeses.hasNext()){
									String selected = "";                      
									String[] sCampos = (String[]) IterMeses.next(); 
									if(BCA.getMesDesde() == Integer.parseInt(sCampos[0]))  selected = "selected";
							%>
                          <option value="<%=sCampos[0]%>" <%=selected%>><%=sCampos[1]%></option>
                          <%
								}%>
                        </select>
                      </span></td>
                    </tr>
                    <tr>
                      <td width="9%" height="26" class="text-globales">Mes Hasta(*) : </td>
                      <td width="91%"><span class="fila-det-border">
                        <select name="mesHasta" class="campo">
                          <%  IterMeses = listMeses.iterator();  %>
                          <option value="0">Seleccionar</option>
                          <%
								while(IterMeses.hasNext()){
									String selected = "";                      
									String[] sCampos = (String[]) IterMeses.next(); 
									if(BCA.getMesHasta() == Integer.parseInt(sCampos[0])) selected = "selected";
							%>
                          <option value="<%=sCampos[0]%>" <%=selected%>><%=sCampos[1]%></option>
                          <%
								}%>
                        </select>
                      </span></td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                      <td height="2" colspan="2"></td>
                    </tr>
                    <tr>
                      <td height="26" colspan="2" class="text-globales"><input name="validar" type="submit" class="boton" id="validar" value="  Buscar"></td>
                    </tr>
                  </table></td>
              </tr>
        </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BCA" property="mensaje"/></td>
  </tr>
</table>
<%

//	if( !anio.equalsIgnoreCase("") &&  
//	    !mesDesde.equalsIgnoreCase("") &&  
//			!mesHasta.equalsIgnoreCase("") ){

		List saldoCuentas    = BCA.getListSaldoCuentas();
		iter   = saldoCuentas.iterator();
		boolean existenReg = false;
		boolean esPrimero  = true;
		String esImputable = "";
		while ( iter.hasNext() ) {
			String[] sCampos = (String[]) iter.next(); 
			String idcuenta  =  str.esNulo(sCampos[0]);
			String cuenta    =  str.esNulo(sCampos[1]);
			String imputable =  str.esNulo(sCampos[2]);
			String nivel     =  str.esNulo(sCampos[3]);
			String resultado =  str.esNulo(sCampos[4]);			
			String saldoanterior    =  str.esNulo(sCampos[5]);			
			String sumadebe   =  str.esNulo(sCampos[6]);
			String sumahaber   =  str.esNulo(sCampos[7]);			
			String saldoactual  =  str.esNulo(sCampos[8]); 
      String bgcolor = "";
			if (imputable.equalsIgnoreCase("n")) bgcolor = "#F1FAF9";
      else bgcolor = "";
      if(!esImputable.equalsIgnoreCase(imputable)){
			  if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
			  else color_fondo = "fila-det-verde"; 
				esImputable = imputable;
			}
			existenReg = true;
			if (esPrimero) {
				 esPrimero = false; %>
<table width="100%"  cellPadding=0 cellSpacing=0  border="0" align="center">
  <tr class=fila-encabezado>
    <td width="11%" height="13">Nro. Cuenta</td>
    <td width="36%">Cuenta</td>
    <td width="11%"><div align="right">Saldo Anterior </div></td>
    <td width="11%"><div align="right">Suma Debe</div></td>
    <td width="11%"><div align="right">Suma Haber</div></td>
    <td width="14%"><div align="right">Saldo Actual </div></td>
  </tr>
  <%
			} 

			 %>
  <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" >
    <td width="11%" height="13" class="fila-det-border" bgcolor="<%= bgcolor %>" ><%= idcuenta %></td>
    <td width="36%" class="fila-det-border" bgcolor="<%= bgcolor %>">&nbsp;<%= cuenta %></td>

    <% //if (imputable.equalsIgnoreCase("s")) {%>
      <td width="11%" class="fila-det-border" bgcolor="<%= bgcolor %>"><div align="right" class="<%= GeneralBean.colorSaldo(saldoanterior, clasePos, claseNeg) %>"> <%= Common.getNumeroFormateado(Float.parseFloat(saldoanterior) , 10, 2)%></div></td>
      <td width="11%" class="fila-det-border" bgcolor="<%= bgcolor %>"><div align="right" class="<%= GeneralBean.colorSaldo(sumadebe, clasePos, claseNeg) %>"><%= Common.getNumeroFormateado(Float.parseFloat(sumadebe), 10, 2) %> </div></td>
      <td width="11%" class="fila-det-border" bgcolor="<%= bgcolor %>"><div align="right" class="<%= GeneralBean.colorSaldo(sumahaber, clasePos, claseNeg) %>"> <%= Common.getNumeroFormateado(Float.parseFloat(sumahaber), 10, 2) %> </div></td>
      <td width="14%" class="fila-det-border" bgcolor="<%= bgcolor %>"><div align="right" class="<%= GeneralBean.colorSaldo(saldoactual, clasePos, claseNeg) %>"> <%= Common.getNumeroFormateado(Float.parseFloat(saldoactual), 10, 2) %></div></td>
    <%//} else { % >
      //<td width="15%" class="fila-det-border" ><div align="right" class="< %=  clasePos % >">&nbsp;*****</div></td>
      //<td width="15%" class="fila-det-border" ><div align="right" class="< %=  clasePos % >" >&nbsp;</div></td>
      //<td width="15%" class="fila-det-border" ><div align="right" class="< %= GeneralBean.colorSaldo(saldodebe, clasePos, claseNeg) % > ">&nbsp; </div></td>
      //<td width="15%" class="fila-det-border" ><div align="right" class="< %= GeneralBean.colorSaldo(saldohaber, clasePos, claseNeg) % >">&nbsp;</div></td>

    //< %//} %>
  </tr>

  <%
			  
			if (imputable.equalsIgnoreCase("nN")) {			
			 %>
  <tr class=fila-encabezado>
    <td colspan="7" height="3"></td>
  </tr>
  <% 
			}
			 %>



  <%
		}
		%>
</table>
<%
//	}

%>

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

