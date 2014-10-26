<%@ page import="java.util.Collection, java.util.Iterator, java.lang.*, java.util.*, ar.com.syswarp.ejb.*"%>
<%@ page import="java.math.BigDecimal" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<STYLE type=text/css>
A:link{ 
	TEXT-DECORATION: none ;
	COLOR: #000000 ;
}
A:visited{ 
	TEXT-DECORATION: none ;
	COLOR: #000000 	;
}
A:hover{ 
	TEXT-DECORATION: none ;
	COLOR: #000000 	;
}
A:active{ 
	TEXT-DECORATION: none ;
	COLOR: #000000;
}

BODY {
	SCROLLBAR-FACE-COLOR: #999999; 
	SCROLLBAR-HIGHLIGHT-COLOR: #000000; 
	SCROLLBAR-SHADOW-COLOR: #000000; 
	SCROLLBAR-3DLIGHT-COLOR: #5e7d96; 
	SCROLLBAR-ARROW-COLOR: #000000; 
	SCROLLBAR-TRACK-COLOR: #5e7d96; 
	SCROLLBAR-DARKSHADOW-COLOR: black
}
</STYLE>
<%

// por ahora invoco directamente al ejb para 
BigDecimal idusuario = null;
String menu = "";
String imagenmenu = "";
String salida = "";

try {

	idusuario = new BigDecimal( session.getAttribute("idusuario").toString() );
	GeneralBean repo = new GeneralBean();
  	   	      
	if (idusuario != null) {
		menu = repo.getMenuTreeJSScroll(idusuario);
		//menu =  repo.getMenuTreeJS(idusuario);
		imagenmenu = repo.getImagenescustomapprelativepath() + repo.getImagencustommenu();
	}

	//System.out.println("MENU \n ----------------------------\n" + menu + " \n _____________________________\n");
%>

<script src="vs/scripts/arbol/detect_browser.js"></script>
<script src="vs/scripts/arbol/fun_arbol_menu.js"></script>
<script>

	USETEXTLINKS = 1  
	STARTALLOPEN = 0
	HIGHLIGHT = 1
	PRESERVESTATE = 0
	GLOBALTARGET = "R"

	// GENERA LA RAIZ
	// EJV:20080722 Funcion gFld modificada para manejo dinamico de imagenes 
	foldersTree = gFld(" - ERP  ", "javascript:parent.op();", "folder_root2.png", "folder_root2.png")

	<%= menu %>

	foldersTree.treeID = "L1"  
	foldersTree.xID = "bigtree"

</script>

<title>TREE</title>
</head>

<body bgColor="white" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" > 
	<SCRIPT>
	var doc = this.window.document
	doc.write('<table width="81%" height="69" border="0" align="center" cellpadding="0" cellspacing="0">');
	doc.write('  <tr>');
	doc.write('    <td><a href="inicial.jsp" target="_parent"><img src="<%= imagenmenu %>" border="0"></a></td>');
	doc.write('  </tr>');
	doc.write('</table>');			
	initializeDocument();
	</SCRIPT>
</body>

<% 
} catch (Exception ex) {
	java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
	java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
	ex.printStackTrace(pw);
}  
%>

</html>
