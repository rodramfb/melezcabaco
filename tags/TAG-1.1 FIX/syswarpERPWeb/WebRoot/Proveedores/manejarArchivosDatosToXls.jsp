<%
response.setContentType("application/vnd.ms-excel ");
String fileName = session.getAttribute("archivo") + ".xls";
response.setHeader("Content-disposition","inline; filename=" + session.getAttribute("archivo"));
String fileTest = request.getParameter("file");
%>
<jsp:include page="<%= fileTest %>" flush="true" /> 
