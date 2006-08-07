<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Subversion Repository</title>
<style type="text/css" media="all">
    @import "normal.css";
  </style>
</head>
<body>
<center>

<h2>Nifty slogan here</h2>
<form method="get" action="query.jsp">
  <input type="text" name="q" id="q" width="100" /><input type="submit" value="Submit">
</form>

This search uses Lucene and Lucene syntax. The available fields are author: date: message: revision: and content:
Example searches :
<table>
<tr><td>author:foo</td><td>find all commits authored by "foo"</td> </tr>
<tr><td>foo</td><td>find all commits with "foo" somewhere</td> </tr>
</table>
</center>
</body>
</html>