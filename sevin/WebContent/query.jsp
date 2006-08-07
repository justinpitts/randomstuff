<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Query Results for ${param.q}</title>
<style type="text/css" media="all">
    @import "normal.css";
  </style>
  <script type="text/javascript"></script>
</head>
<body>
<p />
<p />
<p />
<p />
<jsp:useBean id="webquery" class="com.randomhumans.sevin.Query" scope="page">
  <jsp:setProperty name="webquery" property="query" param="q"/>
</jsp:useBean>
<center>
<form id="tableForm" method="get" action="revisiondetails.jsp">
<input type="hidden" name="revision" />
<table class="highlighter" border="1px" bordercolor="black">
  <tr bgcolor="cyan"><td>Revision</td><td>Author</td><td>Date</td><td>Message</td></tr>
<c:forEach var="doc" items="${webquery.results}">
  <tr 
    onclick="document.forms['tableForm'].submit()" 
    onmouseover="document.forms['tableForm'].elements['revision'].value = '${doc.revision}'">
  <td>${doc.revision}</td><td>${doc.author}</td><td>${doc.date}</td><td>${doc.message}</td></tr>
</c:forEach>
</table> 
</form>
<p />
<p />
<p />
<p />
<form id="search" method="get" action="query.jsp">Try another search
  <input type="text" name="q" id="q" width="100" />&nbsp;<input type="submit" value="Submit">
</form>
</center>
</body>
</html>