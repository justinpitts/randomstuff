<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Revision details for revision # ${param.revision}</title>
<style type="text/css" media="all">
    @import "normal.css";
  </style>
</head>
<body>

<jsp:useBean id="details" class="com.randomhumans.sevin.RevisionDetails" scope="page">
  <jsp:setProperty name="details" property="revision" param="revision"/>
</jsp:useBean>
<form>
<center>
  <table>
    <tr><td>Author</td> <td>${details.author}</td> </tr>
    <tr><td>Date</td>   <td>${details.date}</td></tr><tr />
    <tr><td>Message</td></tr>
    <tr><td>${details.message}</td></tr>
    <c:forEach var="file" items="${details.changedPaths}">
      <tr><td>change</td><td>${file.type }</td><td><a href="${file.path }" target="blank" >${file.path}</a></td></tr>
    </c:forEach>
  </table>  
</center>
</form>

</body>
</html>