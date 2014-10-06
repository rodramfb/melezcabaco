<%@page import="javax.servlet.http.*"%>
<%@page import="java.security.*"%>
<%@page import="javax.naming.*"%>
<%@page import="javax.naming.directory.*"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ page import="ar.com.syswarp.validar.*" %>
<%@ page import="java.math.BigDecimal" %>
<%
Strings str = new Strings();
String asiento   = request.getParameter("asiento");	
if (asiento == null) asiento = "";
//String pathskin = "imagenes/default/"; // cambiar
String pathskin = session.getAttribute("pathskin").toString(); 	
int ejercicioActivo =  Integer.parseInt( (String)session.getAttribute("ejercicioActivo") );
%>
<html>
<head>
<title>B&uacute;squeda de asientos</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript">
function validarCampos() {

	if (document.forms[0].asiento.value == "") {
		alert("Debe ingresarse un asiento a buscar");	
		return false;
	}

	document.forms[0].submit();
}
function passBack(asiento, d_cContable) {  
  var i = 0;
   for(i=0;i<10;i++)
     if(opener.document.forms[i].codigo)
       break;    
   opener.document.forms[i].codigo.value = asiento; 
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
<p align="left" class=text-seis>B&uacute;squeda de Asientos </p>
<hr>
<form action="lov_asientos.jsp">
  <table width="59%" border="0" cellspacing="0" cellpadding="0" height="23">
    <tr> 
      <td height="39" width="24%"> 
        <div class=text-seis>Asiento</div>
      </td>
      <td width="54%" height="39"> 
        <input name="asiento" type="text" class="campo" value="<%=asiento%>" size="25">
      </td>
      <td height="39" width="3%"></td>
      <td height="39" width="19%"> 
        <input name="buscar" type="button" class="boton" onClick="validarCampos()" value="Buscar">
      </td>
    </tr>
  </table>
<%  
if (!asiento.equals("")) {
   java.util.List Asientos =  new ArrayList();
   Iterator iterAsientos=null;
   int totReg  = 0;
   try{ 
   	javax.naming.Context context = new javax.naming.InitialContext();
   	// INSTANCIAR EL MODULO CONTABLE 
   	Object object = context.lookup("Contable");
   	ContableHome sHome = (ContableHome) javax.rmi.PortableRemoteObject.narrow(object, ContableHome.class);
   	Contable repo =   sHome.create(); 
   	
    Object objgen = context.lookup("General");
    GeneralHome sGen = (GeneralHome) javax.rmi.PortableRemoteObject.narrow(objgen, GeneralHome.class);
    General gene =   sGen.create();        
   	
   	  	      
   	Asientos =  repo.getAsientosOcu(ejercicioActivo, asiento, new BigDecimal(session.getAttribute("empresa").toString() )); 
   	iterAsientos = Asientos.iterator();      
   	totReg = Asientos.size();     



	boolean existenReg = false;
	boolean esPrimero = true;
	String color_fondo = "";
	String flagAsiento = "";
	while ( iterAsientos.hasNext() ) {
   		String[] sCampos = (String[]) iterAsientos.next(); 
   		String cmp_nroasiento = sCampos[0] ;
   		String cmp_renglon = sCampos[1] ;
   		String cmp_tipomov = sCampos[2] ;
			String cmp_fecha =   gene.TimestampToStrDDMMYYYY ( gene.StrToTimestampDDMMYYYYHHMISE( sCampos[3] ) )  ;//sCampos[3]  ; // 
			String cmp_detalle =   sCampos[4]  ;
			String cmp_leyenda =   sCampos[5]  ;
			String cmp_cuenta =   sCampos[6]  ;
			String cmp_importe =   sCampos[7]  ;
   		existenReg = true;

     // mov.asiento, mov.renglon, mov.tipomov,mov.fecha,
     // mov.detalle, ley.leyenda, mov.cuenta   		
		
		if (esPrimero) {
			esPrimero = false; %>
  <table width="100%"  cellPadding=0 cellSpacing=0  border="0">
    <tr class=fila-encabezado> 
      <td width="4%" height="13">&nbsp;</td>
      <td width="14%" height="13">Cuenta</td>
      <td width="8%">Renglon</td>
      <td width="15%">Tipo </td>
      <td width="28%" height="13">Detalle libro mayor </td>
      <td width="31%">Importe</td>
    </tr>
    <% } %>
		
    <%  
		  
			if (!flagAsiento.equals(cmp_nroasiento) ){
			   //if(color_fondo.equalsIgnoreCase("fila-det-verde"))color_fondo = "fila-det";
				 //else color_fondo = "fila-det-verde"; 
         flagAsiento = cmp_nroasiento;
				 color_fondo = "fila-det-bold";
  %>
	
		 <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="row" > 
      <td width="4%" class="fila-det-border" ><div align="center"><a href="javascript:passBack('<%=cmp_nroasiento%>', '<%=cmp_renglon%>')"><img src="imagenes/default/gnome_tango/actions/down.png" width="18" height="18" border="0"> </a> &nbsp; </div></td>
      <td width="14%" class="fila-det-border" >Nro. Asiento: <%=cmp_nroasiento%>&nbsp;</td>
      <td colspan="3" class="fila-det-border" >Detalle Diario:&nbsp;<%=cmp_leyenda%></td>
      <td width="31%" class="fila-det-border" >Fecha: <%=cmp_fecha%></td>
	  </tr> 	
	<%
		}
		 color_fondo = "fila-det-verde";
		%>		
		 <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="row" > 
      <td width="4%" class="fila-det-border" >&nbsp;
			  <a href="javascript:passBack('<%=cmp_nroasiento%>', '<%=cmp_renglon%>')">
			  </a>
			</td>
      <td width="14%" class="fila-det-border" ><%= cmp_cuenta %>&nbsp;</td>
      <td width="8%" class="fila-det-border" ><%=cmp_renglon%>&nbsp;</td>
      <td width="15%" class="fila-det-border" ><%= cmp_tipomov.equalsIgnoreCase("1")? "Debe" : "Haber" %>&nbsp;</td>
      <td width="28%" class="fila-det-border" ><%= cmp_detalle %>&nbsp;</td>
      <td width="31%" class="fila-det-border" ><%= cmp_importe %></td> 
	  </tr> 
    <%	}
				if (!existenReg) { %>
				<p class=text-nueve >No existen registros para la Búsqueda</p>
				<%	
				}
				else { %>
  </table>
		<%	} 
		
		
   }
   catch (Exception ex) {
     java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
     java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
     ex.printStackTrace(pw);
  }  		
		
		
}
	%>
	<input type="hidden" name="asiento" value="<%=asiento%>">
</form>

</body>
</html>