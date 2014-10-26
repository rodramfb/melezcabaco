  <!--

Copy and paste the following where you want your map search control to appear. Do not place it within the <head> ... </head> section of your page unless you plan on relocating the <div id="mapsearch">Loading...</div> element out of this chunk of code. <!-- ++Begin Map Search Control Wizard Generated Code++ -->
  <!--
  // Created with a Google AJAX Search Wizard
  // http://code.google.com/apis/ajaxsearch/wizards.html
  -->

  <!--
  // The Following div element will end up holding the map search control.
  // You can place this anywhere on your page
  -->
<%@ page import="javax.servlet.http.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.naming.directory.*" %>
<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.regex.*" %>
<%//@ page import="ar.com.syswarp.validar.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%@ page import="ar.com.syswarp.api.Common"%>
 <%
 	// INSTANCIAR EL MODULO clientes
     String idDomicilio    = request.getParameter("iddomicilio");
     String idempresa    = request.getParameter("idempresa");
     System.out.println("iddomicilio " + idDomicilio );
     Clientes repo = null;

		Clientes clientes = Common.getClientes();
     String titulo    = clientes.getGoogleMapTitulo(new BigDecimal(idempresa),new BigDecimal(idDomicilio));  
     String domicilio = clientes.getGoogleMapDomicilio(new BigDecimal(idempresa),new BigDecimal(idDomicilio));
     String key = clientes.getGmapKeyID().trim();
     System.out.println(key);
 %> 
  
  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>Consulta de domicilios de clientes a travez de Google Map</title>
    <script src="http://maps.google.com/maps?file=api&v=2&key=<%=key%>"
    type="text/javascript"></script>
  <script src="http://www.google.com/uds/api?file=uds.js&v=1.0&source=uds-msw&key=<%=key%>"
    type="text/javascript"></script>
  <style type="text/css">
    @import url("http://www.google.com/uds/css/gsearch.css");
  </style>

  <!-- Map Search Control and Stylesheet -->
  <script type="text/javascript">
    window._uds_msw_donotrepair = true;
  </script>
  <script src="http://www.google.com/uds/solutions/mapsearch/gsmapsearch.js?mode=new"
    type="text/javascript"></script>
  <style type="text/css">
    @import url("http://www.google.com/uds/solutions/mapsearch/gsmapsearch.css");
  </style>

  <style type="text/css">
    .gsmsc-mapDiv {
      height : 350px;
    }

    .gsmsc-idleMapDiv {
      height : 350px;
    }

    #mapsearch {
      width : 500px;
      margin: 10px;
      padding: 4px;
    }
  </style>
  <script type="text/javascript">
    function LoadMapSearchControl() {

      var options = {
            zoomControl : GSmapSearchControl.ZOOM_CONTROL_ENABLE_ALL,
            title : "<%=titulo%>",
            url : "http://www.google.com/corporate/index.html",
            idleMapZoom : GSmapSearchControl.ACTIVE_MAP_ZOOM,
            activeMapZoom : GSmapSearchControl.ACTIVE_MAP_ZOOM
            }

      new GSmapSearchControl(
            document.getElementById("mapsearch"),
            "<%=domicilio%>",
            options
            );

    }
    // arrange for this function to be called during body.onload
    // event processing
    GSearch.setOnLoadCallback(LoadMapSearchControl);
  </script>
  </head>

 
  <div id="mapsearch">
    <span style="color:#676767;font-size:11px;margin:10px;padding:4px;">Loading...</span>
  </div>

  </body>
</html><%=domicilio%>

  <!-- Maps Api, Ajax Search Api and Stylesheet
  // Note: If you are already using the Maps API then do not include it again
  //       If you are already using the AJAX Search API, then do not include it
  //       or its stylesheet again
  //
  // The Key Embedded in the following script tags is designed to work with
  // the following site:
  // http://www.syswarp.com.ar
  -->
<!-- ++End Map Search Control Wizard Generated Code++ -->
            