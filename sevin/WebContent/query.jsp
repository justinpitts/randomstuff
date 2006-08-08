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
  <script type="text/javascript">
  function setSort(sortField)
  {
    document.forms['postBack'].elements['sortField'].value = sortField;
    document.forms['postBack'].submit();
  }
  
  function setRevision(revision)
  {
    document.forms['tableForm'].elements['revision'].value = revision;
  }
  </script>
</head>
<body>
<p />
<p />
<p />
<p />
<jsp:useBean id="webquery" class="com.randomhumans.sevin.Query" scope="page">
  <jsp:setProperty name="webquery" property="query" param="q"/>
  <jsp:setProperty name="webquery" property="sortField" param="sortField" />
</jsp:useBean>
<center>
<form id="postBack" method="get" action="query.jsp">
<input type="hidden" name="q" value="${param.q}"/>
<input type="hidden" name="sortField" />
</form>
<form id="tableForm" method="get" action="revisiondetails.jsp">
<input type="hidden" name="revision" />
<table class="highlighter" border="1px" bordercolor="black">
  <tr bgcolor="cyan"><td onclick="setSort('revision')">Revision</td><td onclick="setSort('author')" >Author</td><td onclick="setSort('date')">Date</td><td>Message</td></tr>
<c:forEach var="doc" items="${webquery.results}">
  <tr 
    onclick="document.forms['tableForm'].submit()" 
    onmouseover="setRevision(${doc.revision})">
  <td>${doc.revision}</td><td>${doc.author}</td><td>${doc.date}</td><td>${doc.message}</td></tr>
</c:forEach>
</table> 
</form>
<p />
<p />
<p />
<p />
<form id="search" method="get" action="query.jsp">Try another search
  <input type="text" name="q" value="${param.q}" id="q" width="100" />&nbsp;<input type="submit" value="Submit">
</form>
</center>
</body>
</html>