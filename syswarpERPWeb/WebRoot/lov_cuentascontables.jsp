<%@page import="javax.servlet.http.*"%>
<%@page import="java.security.*"%>
<%@page import="javax.naming.*"%>
<%@page import="javax.naming.directory.*"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ page import="ar.com.syswarp.validar.*" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="ar.com.syswarp.api.Common"%>
<%
  Strings str = new Strings();
    String cContable   ="";
	String cContable2   = request.getParameter("cContable2");	
	String opcion   = str.esNulo(request.getParameter("opcion"));		
	String id   = str.esNulo(request.getParameter("id"));		
	if (cContable2 == null) cContable2 = "";
	//String pathskin = "imagenes/default/"; // cambiar
String pathskin = session.getAttribute("pathskin").toString();	
	int ejercicioActivo =  Integer.parseInt( (String)session.getAttribute("ejercicioActivo") );
%>
<html>
<head>
<title>B&uacute;squeda de centros de costos</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript">
function validarCampos() {

	if (document.forms[0].cContable2.value == "") {
		alert("Debe ingresarse una Cuenta a buscar");	
		return false;
	}
	if (document.forms[0].cContable2.value != "") {
	}
	document.forms[0].submit();
}
function passBack(cContable, d_cContable) {  
  var opcion = "<%= opcion %>";
  var id     =  "<%= id %>";
  if(opcion == "byId"){
    opener.document.getElementById(id).value = cContable ;
		id = (parseInt(id)+1) + "" ;
    opener.document.getElementById(id).value = d_cContable ;		
  }
	else{
		var i = 0;
		 for(i=0;i<10;i++)
			 if(opener.document.forms[i].cuenta)
				 break;    
		 opener.document.forms[i].cuenta.value = cContable; 
	}
   close();
}
function Find_OnClick() {
   document.forms[0].submit();
}
</script>
<script language="JavaScript" src="vs/forms/forms.js"></script>

<link rel = "stylesheet" href = "<%= pathskin %>">
</head>

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0">
<p align="left" class=text-seis>B&uacute;squeda de Cuentas Contables </p>
<hr>
<form action="lov_cuentascontables.jsp">
  <table width="59%" border="0" cellspacing="0" cellpadding="0" height="23">
    <tr> 
      <td height="39" width="24%"> 
        <div class=text-seis>Cuenta </div>
      </td>
      <td width="54%" height="39"> 
        <input name="cContable2" type="text" class="campo" value="<%=cContable%>" size="25">
      </td>
      <td height="39" width="3%"></td>
      <td height="39" width="19%"> 
        <input name="buscar" type="button" class="boton" onClick="validarCampos()" value="Buscar">
      </td>
    </tr>
  </table>
<%  
if (!cContable2.equals("*")) {
   java.util.List cuentasContables =  new ArrayList();
   Iterator iterCuentasContables=null;
   int totReg  = 0;
   try{
   	javax.naming.Context context = new javax.naming.InitialContext();
   	// INSTANCIAR EL MODULO CONTABLE 
	Contable contable = Common.getContable();   	      
   	cuentasContables =  contable.getCuentasOcu(ejercicioActivo, cContable2, new BigDecimal(session.getAttribute("empresa").toString() ));
   	iterCuentasContables = cuentasContables.iterator();      
   	totReg = cuentasContables.size();     
   }
   catch (Exception ex) {
     java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
     java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
     ex.printStackTrace(pw);
  }  


	boolean existenReg = false;
	boolean esPrimero = true;
	String color_fondo = "";
	while ( iterCuentasContables.hasNext() ) {
	if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
  else color_fondo = "fila-det-verde"; 		
   		String[] sCampos = (String[]) iterCuentasContables.next(); 
   		String cmp_codigo = sCampos[0] ;
   		String cmp_descripcion = sCampos[1] ;
   		String cmp_imputable = sCampos[2] ;
			int cmp_nivel =  Integer.parseInt( sCampos[3] ) ;
   		existenReg = true;
		if (esPrimero) {
			esPrimero = false; %>
  <table width="100%"  cellPadding=0 cellSpacing=0  border="0">
    <tr class=fila-encabezado> 
      <td width="4%" height="13">&nbsp;</td>
      <td width="10%" height="13">C�digo</td>
      <td width="38%" height="13">Cuenta</td>
      <td width="15%">Imputable</td>
      <td width="33%">Nivel</td>
    </tr>
    <% } %>
		 <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="row" > 
      <td width="5%" class="fila-det-border" >
			  <% if(str.esNulo(cmp_imputable).equalsIgnoreCase("s")){ %>
			  <a href="javascript:passBack('<%=cmp_codigo%>', '<%=cmp_descripcion%>')">
				<img src="imagenes/default/lupa.gif" width="21" height="17" border="0">
				</a>
				<% } %>
				&nbsp;
			</td>
      <td width="20%" class="fila-det-border" ><%=cmp_codigo%>&nbsp;</td>
      <td width="40%" class="fila-det-border" ><%=str.getNivelStr ("-" , cmp_nivel) + " " + cmp_descripcion%>&nbsp;</td>
      <td width="15%" class="fila-det-border" ><%=cmp_imputable%>&nbsp;</td>
      <td width="20%" class="fila-det-border" ><%=cmp_nivel%>&nbsp;</td>
	  </tr> 
    <%	}
				if (!existenReg) { %>
				<p class=text-nueve >No existen registros para la B�squeda</p>
				<%	
				}
				else { %>
  </table>
		<%	} 
		}
	%>
	<input type="hidden" name="id" value="<%=id%>">
	<input type="hidden" name="opcion" value="<%=opcion%>">
	<input type="hidden" name="cContable" value="<%=cContable%>">
</form>

</body>
</html>