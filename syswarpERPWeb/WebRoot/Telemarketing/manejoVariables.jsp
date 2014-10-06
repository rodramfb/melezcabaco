<%@ page contentType="text/html; charset=windows-1252" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252" />
<title>Documento sin t&iacute;tulo</title>
</head>
<script>
function getFlashMovieObject(movieName)
{
  if (window.document[movieName]) 
  {
    return window.document[movieName];
  }
  if (navigator.appName.indexOf("Microsoft Internet")==-1)
  {
    if (document.embeds && document.embeds[movieName])
      return document.embeds[movieName]; 
  }
  else // if (navigator.appName.indexOf("Microsoft Internet")!=-1)
  {
    return document.getElementById(movieName);
  }
}
function SendDataToFlashMovie()
{
	var flashMovie=getFlashMovieObject("manejoVariables");
	flashMovie.SetVariable("/:idcliente", document.frm.Data.value);
}

function ReceiveDataFromFlashMovie()
{
	var flashMovie=getFlashMovieObject("manejoVariables");
	var message=flashMovie.GetVariable("/:idcliente");
	document.frm.Data.value=message;
}

</script>
<body>
<form name="frm" method="post">
<table>
	<tr>
		<td>
			<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0" width="300" height="200" id="manejoVariables" align="middle">
				<param name="allowScriptAccess" value="sameDomain" />
				<param name="movie" value="manejoVariables.swf" /><param name="quality" value="high" /><param name="bgcolor" value="#ffffff" />
				<embed src="manejoVariables.swf" quality="high" bgcolor="#ffffff" width="300" height="200" name="manejoVariables" align="middle" allowScriptAccess="sameDomain" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" />
</object>
		</td>
	</tr>
	<tr>
		<td>
			<input type="text" name="Data" size="20" value="Enter message"> 
			<input type="button" value="Send Data" name="SendData" onClick="SendDataToFlashMovie();"> 
			<input type="button" value="Get Data" name="ReceiveData" onClick="ReceiveDataFromFlashMovie();"> 
		</td>
	</tr>
</table>
</form>
</body>
</html>
