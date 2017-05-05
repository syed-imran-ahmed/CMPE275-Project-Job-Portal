<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 
<html>
<head>
 
<style type="text/css">
body {
	background-image: url('https://crunchify.com/bg.png');
}
</style>
 
<title>Spring MVC: How to Access ModelMap Values in a JSP? -
	Crunchify.com</title>
<meta rel="author" href="https://crunchify.com">
</head>
 
<body>
	<div align="center">
		<br>
		<h1>${heading}</h1>
<%-- 		<h2>${result1}</h2> --%>
	<%-- 	<br>
		<h3>${result2}</h3> --%>
	</div>
	<table>
	<td>ID</td><td>Title</td><td>Description</td><td>Resp</td><td>Location</td><td>Salary</td>
	<c:forEach items="${result1}" var="current">
        <tr>
        
          <td><c:out value="${current.jobid}" /><td>
          <td><c:out value="${current.title}" /><td>
		<td><c:out value="${current.descrip}" /><td>
          <td><c:out value="${current.resp}" /><td>
          <td><c:out value="${current.loc}" /><td>
          <td><c:out value="${current.sal}" /><td>
        </tr>
      </c:forEach>
</table>
</body>
 
</html>