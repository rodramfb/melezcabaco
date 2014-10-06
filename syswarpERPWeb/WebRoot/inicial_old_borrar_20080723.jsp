<%@ page import="java.util.Collection, java.util.Iterator, java.lang.*, java.util.*, ar.com.syswarp.ejb.*"%>
<%@ page import="java.math.BigDecimal" %>
<html>
<head>
<title>:::::</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%


// por ahora invoco directamente al ejb para 
BigDecimal idusuario = new BigDecimal( session.getAttribute("idusuario").toString() );
String menu = "";
String imagenmenu = "";
String salida = "";

   try{
   	javax.naming.Context context = new javax.naming.InitialContext();
   	// INSTANCIAR EL MODULO GENERAL
   	Object object = context.lookup("General");
   	GeneralHome sHome = (GeneralHome) javax.rmi.PortableRemoteObject.narrow(object, GeneralHome.class);
   	General repo =   sHome.create();   	      
   	if (idusuario != null){

//
       salida = repo.getMenuTreeJSScroll(idusuario);
//
       menu =  repo.getMenuTreeJS(idusuario);
	   imagenmenu = repo.getImagenescustomapprelativepath() + repo.getImagencustommenu();
   	}   	
   	
   }
   catch (Exception ex) {
     java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
     java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
     ex.printStackTrace(pw);
  }  

//System.out.println("MENU \n ----------------------------\n" + menu + " \n _____________________________\n");
%>
<!--
Instructions:
  - You only have to touch in function generateTree()
  - Nodes with other folders inside are created with folderNode(<folder name>)
  - Nodes with documents inside are created with leafNode(<folder name>)
  - Documents are nodes created with generateDocEntry(<new window?>, <doc name>, <doc link>)
  - You cannot have 'mixed' folders (folders with other folders _and_ with documents)
-->
<script LANGUAGE="JavaScript">
<!--  to hide script contents from old browsers

//each node in the tree is an Array with 4+n positions 
//  node[0] is 0/1 when the node is closed/open
//  node[1] is 0/1 when the folder is closed/open
//  node[2] is 1 if the children of the node are documents 
//  node[3] is the name of the folder
//  node[4]...node[4+n] are the n children nodes

// ***************
// Building the data in the tree
function generateTree()
{
var aux1, aux2, aux3, aux4
// nombre de la empresa
foldersTree = folderNode("ERP")
// aca se instancia la funcion hecha con java CEP 	  
 <%
 out.write(menu);	  
 %>
}

// Auxiliary function to build the node
function folderNode(name)
{
var arrayAux
        arrayAux = new Array
        arrayAux[0] = 0
        arrayAux[1] = 0
        arrayAux[2] = 0
        arrayAux[3] = name
        
        return arrayAux
}
function leafNode(name)
{
var arrayAux
        arrayAux = new Array
        arrayAux[0] = 0
        arrayAux[1] = 0
        arrayAux[2] = 1
        arrayAux[3] = name


        
        return arrayAux
}
//this way the generate tree function becomes simpler and less error prone
function appendChild(parent, child)
{
        parent[parent.length] = child
        return child
}
//these are the last entries in the hierarchy, the local and remote links to html documents
function generateDocEntry(icon, docDescription, link, alt, img)
{
var retString =""
var imagen = "doc.gif"
if (img!="")  imagen = img
        if (icon==0)
                retString = "<A href='"+link+"' target=mainFrame><img src='imagenes/"+imagen+"' alt='" + alt + "'"
        else
                retString = "<A href='http://"+link+"' target=_blank><img src='imagenes/link.gif' alt='Abre en una nueva ventana'"
        retString = retString + " border=0></a><td nowrap><font size=1 color='#0000FF' face='verdana, Helvetica'>" + docDescription + "</font>"
        return retString
}

// **********************
// display functions
//redraws the left frame
function redrawTree()
{ 
var doc = treeFrame.window.document  //cep
        //doc.clear()	
//		doc.write('<a href="inicial.jsp" target="_parent"><img src="imagenes/LogoMetroGASTrans.gif" width="130" height="50" border="0" position="absolute" left="0" top="0"></a>')
//		doc.write('<style fprolloverstyle>A:hover {color: #FFCC66}</STYLE><style> a { text-decoration: none } </style><STYLE type=text/css>BODY {	SCROLLBAR-FACE-COLOR: #999999; SCROLLBAR-HIGHLIGHT-COLOR: #000000; SCROLLBAR-SHADOW-COLOR: #000000; SCROLLBAR-3DLIGHT-COLOR: #5e7d96; SCROLLBAR-ARROW-COLOR: #000000; SCROLLBAR-TRACK-COLOR: #5e7d96; SCROLLBAR-DARKSHADOW-COLOR: black}</STYLE>')
		doc.write('<table width="81%" height="69" border="0" align="center" cellpadding="0" cellspacing="0">')
        doc.write('<tr>')
        doc.write('<td><a href="inicial.jsp" target="_parent"><img src="<%= imagenmenu %>" border="0"></a></td>')
        doc.write('</tr>')
        doc.write('</table>')			
        doc.write('<body bgColor="white" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">')     
        redrawNode(foldersTree, doc, 0, 1, "")
        doc.close()
}
//recursive function over the tree structure called by redrawTree
function redrawNode(foldersNode, doc, level, lastNode, leftSide)
{ 
var j=0
var i=0
        doc.write("<table border=0 cellspacing=0 cellpadding=0>")
        doc.write("<tr><td valign = middle nowrap>")
        doc.write(leftSide)
        if (level>0)
                if (lastNode) //the last 'brother' in the children array
                {
                        doc.write("<img src='imagenes/lastnode.gif'>")
                        leftSide = leftSide + "<img src='imagenes/blank.gif'>" 
                }
                else
                {
                        doc.write("<img src='imagenes/node.gif'>")
                   leftSide = leftSide + "<img src='imagenes/vertline.gif'>"
                }
        displayIconAndLabel(foldersNode, doc)
        doc.write("</table>")
        if (foldersNode.length > 4 && foldersNode[0]) //there are sub-nodes and the folder is open
        {
                if (!foldersNode[2]) //for folders with folders
                {
                        level=level+1
                        for (i=4; i<foldersNode.length;i++)
                                if (i==foldersNode.length-1)
                                        redrawNode(foldersNode[i], doc, level, 1, leftSide)
                                else
                                        redrawNode(foldersNode[i], doc, level, 0, leftSide)
                }
                else //for folders with documents
                {
                        for (i=4; i<foldersNode.length;i++)
                        {
                                doc.write("<table border=0 cellspacing=0 cellpadding=0 valign=center>")
                                doc.write("<tr><td nowrap>")
                                doc.write(leftSide)
                                if (i==foldersNode.length - 1)
                                 doc.write("<img src='imagenes/lastnode.gif'>")
                                else
                                        doc.write("<img src='imagenes/node.gif'>")
                                doc.write(foldersNode[i])
                                doc.write("</table>")
                        }
                }
        }
}
//builds the html code to display a folder and its label
function displayIconAndLabel(foldersNode, doc)
{
        doc.write("<A href='javascript:top.openBranch(\"" + foldersNode[3] + "\")'")
        if (foldersNode[1])
        {
                doc.write("onMouseOver='window.status=\"Abrir rama\"; return true'><img src=")
                doc.write("imagenes/openfolder.gif border=noborder></a>")
        }
        else
        {
                doc.write("onMouseOver='window.status=\"Abrir rama\"; return true'><img src=")
                doc.write("imagenes/closedfolder.gif border=noborder></a>")
        }
        doc.write("<td valign=middle align=left nowrap>")
        doc.write("<font size=1 face='verdana, Helvetica'>"+foldersNode[3]+"</font>")
}
//**********************+
// Recursive functions
//when a parent is closed all children also are
function closeFolders(foldersNode)
{
var i=0
        if (!foldersNode[2])
        {
                for (i=4; i< foldersNode.length; i++)
                        closeFolders(foldersNode[i])
        }
        foldersNode[0] = 0
        foldersNode[1] = 0
}
//recursive over the tree structure
//called by openbranch
function clickOnFolderRec(foldersNode, folderName)
{
var i=0
        if (foldersNode[3] == folderName)
        {
                if (foldersNode[0])
                        closeFolders(foldersNode)
                else
                {
                        foldersNode[0] = 1
                        foldersNode[1] = 1
                }
        }
        else
        {
                if (!foldersNode[2])
                        for (i=4; i< foldersNode.length; i++)
                                clickOnFolderRec(foldersNode[i], folderName)
        }
}

// ********************
// Event handlers
//called when the user clicks on a folder
function openBranch(branchName)
{
        clickOnFolderRec(foldersTree, branchName)
        if (branchName=="JavaScript Start folder" && foldersTree[0]==0)
                top.folderFrame.location="window.html"
        timeOutId = setTimeout("redrawTree()",100)
}
//called after this html file is loaded
function initializeTree()
{
        generateTree()
        redrawTree()
}
var foldersTree = 0
var timeOutId = 0
generateTree() //sometimes when the user reloads the document Netscape 3.01 does not trigger the onLoad event (!!)
// end hiding contents from old browsers  -->

/*function funOcultarMenu() {
	var i;
	var doc = mainFrame.window.document
	doc.logoMG.width="130";	
	for (i=25;i>=0;i--) {	
		document.all.frmPrincipal.cols=i+"%,*";
	}
}*/

</script>
</head>
<FRAMESET cols="20%,*"  onLoad='initializeTree()' name="frmPrincipal">
  <FRAME src="window.html" name="treeFrame">
  <FRAME SRC="principal.jsp" name="mainFrame">  
<!--  <FRAME SRC="principal.jsp" name="mainFrame" onMouseOver="funOcultarMenu()">-->
</FRAMESET>
<noframes> 
</noframes> 

</html>

<%= salida %>