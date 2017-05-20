<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>View Applications</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div align = center>
	   <h3 class = "form-signin-heading">List of Applications </h3>
	   <br>
		<c:if test="${not empty appList}">
		<form method="GET" action="${contextPath}/jobApplications" class = "form-signin">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<table border="1">
			<tr>
				<th><div style='width: 250px;'><h3>Id</h3></div></th>
				<th><div style='width: 250px;'><h3>Jobseeker Name</h3></div></th>
				<th><div style='width: 250px;'><h3>Status</h3></div></th>
			</tr>
			<c:forEach var="app" items="${appList}">
				<tr>
					<td><div style='width: 250px;'><h4><a href="${contextPath}/jobseekerOffer/${jobid}/${app.jobseekerID}"><c:out value="${app.jobseekerID}" /></a></h4></div></td>
					<td><div style='width: 250px;'><h4><c:out value="${app.jobseekerName}" /></h4></div></td>
					<td><div style='width: 250px;'><h4><c:out value="${app.status}" /></h4></div></td>
				</tr>			
			</c:forEach>
		</table>
		</form>
		</c:if>
		
		
		<div id="pagination">
		
		    <c:url value="${contextPath}/jobApplications" var="prev">
		        <c:param name="page" value="${page-1}"/>
		    </c:url>
		    <c:if test="${page > 1}">
		        <a href="<c:out value="${prev}" />" class="pn prev">Prev</a>
		    </c:if>
		
		    <c:forEach begin="1" end="${maxPages}" step="1" varStatus="i">
		        <c:choose>
		            <c:when test="${page == i.index}">
		                <span>${i.index}</span>
		            </c:when>
		            <c:otherwise>
		                <c:url value="${contextPath}/jobApplications" var="url">
		                    <c:param name="page" value="${i.index}"/>
		                </c:url>
		                <a href='<c:out value="${url}" />'>${i.index}</a>
		            </c:otherwise>
		        </c:choose>                
		    </c:forEach>
		    <c:url value="${contextPath}/jobApplications" var="next">
		        <c:param name="page" value="${page + 1}"/>
		    </c:url>
		    <c:if test="${page + 1 <= maxPages}">
		        <a href='<c:out value="${next}" />' class="pn next">Next</a>
		    </c:if>
		</div>
</div>
  <h2 align = left><a href="${contextPath}/postjob/${jobid}" class="form-signin">Back</a></h2>
</body>
</html>