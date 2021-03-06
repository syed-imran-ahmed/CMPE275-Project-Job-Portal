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
    <style>
    div.contain {
		    width: 100%;
		    border: 1px solid gray;
		    min-height:520px;
		}

header{
    margin:0;
    padding:1em;
    background-color: DARKSLATEGRAY;
    clear: left;  
    color:white;
    font-size:20;
    text-align: center;
    box-shadow: inset 0 0 20px 0px black;
}

	footer {
	    margin:0;
    	padding:1em;
	    background-color: DARKSLATEGRAY;
	    clear: left;  
	    color:white;
	    font-size:20;
	    text-align: center;
	    box-shadow: inset 0 0 20px 0px black;
		bottom: 0px;
		width : 100%;
	}
	table {
		color: #333; 
		font-family: Helvetica, Arial, sans-serif;
		width: 700px;
		border-collapse:
		collapse; border-spacing: 0;
		}

		td, th { border: 1px solid #CCC; height: 30px; } 
		
		th {
		background: #F3F3F3;
		font-weight: bold; 
		text-align: center; 
		}
		
		td {
		background: #FAFAFA;
		text-align: center; 
		}
	</style>
</head>
<body>
<a href="${contextPath}/welcome">
	<header>
	    <img src="${contextPath}/images/logo.png" alt="hirePeople"/>
	</header>
</a>
<div class="contain">
	<div align = center>
	   <h3 class = "form-signin-heading">List of Applications </h3>
	   <br>
		<c:if test="${not empty appList}">
		<form method="POST" action="${contextPath}/applicationView" class = "form-signin">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<table border="1">
			<tr>
				<th><div style='width: 250px;'><h3>Cancel or Reject</h3></div></th>
				<th><div style='width: 250px;'><h3>Job Title</h3></div></th>
				<th><div style='width: 250px;'><h3>Status</h3></div></th>
			</tr>
			<c:forEach var="app" items="${appList}">
				<tr>
					<c:choose>
						<c:when test="${app.status eq 'Pending' ||app.status eq 'Offered'}">
							<td><div style='width: 250px;'><input type="checkbox" name="id" value="${app.id}"></div></td>
						</c:when>
						<c:otherwise>
							<td><div style='width: 250px;'><h4>NA</h4></div></td>
						</c:otherwise>
					</c:choose>
					<td><div style='width: 250px;'><h4><a href="${contextPath}/applyjob/${app.jobID}"><c:out value="${cmpHM.get(app.jobID)}" /></a></h4></div></td>
					<td><div style='width: 250px;'><h4><c:out value="${app.status}" /></h4></div></td>
				</tr>			
			</c:forEach>
		</table>
		<input type="submit" value="Submit">
		</form>
		</c:if>
		
		
		<div id="pagination">
		
		    <c:url value="${contextPath}/applicationView" var="prev">
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
		                <c:url value="${contextPath}/applicationView" var="url">
		                    <c:param name="page" value="${i.index}"/>
		                </c:url>
		                <a href='<c:out value="${url}" />'>${i.index}</a>
		            </c:otherwise>
		        </c:choose>                
		    </c:forEach>
		    <c:url value="${contextPath}/applicationView" var="next">
		        <c:param name="page" value="${page + 1}"/>
		    </c:url>
		    <c:if test="${page + 1 <= maxPages}">
		        <a href='<c:out value="${next}" />' class="pn next">Next</a>
		    </c:if>
		</div>
</div>
</div>
<footer>CMPE275 Project Team-3</footer>
</body>
</html>