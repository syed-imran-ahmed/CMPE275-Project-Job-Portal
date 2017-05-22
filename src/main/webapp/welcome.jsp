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

    <title>Welcome</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="container">

    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h2>Welcome ${pageContext.request.userPrincipal.name} | <a onclick="document.forms['logoutForm'].submit()">Logout</a></h2>
		<h4 class="text-right"><a href="${contextPath}/job_seeker">View my profile</a></h4>
	</c:if>
    
	<c:if test="${profileComplete eq true}">	
	<h4 class="text-center"><a href="${contextPath}/search">Search</a></h4>
	<h4 class="text-right"><a href="${contextPath}/applicationView">Check Applications</a></h4>
	<h4 class="text-right"><a href="${contextPath}/listInterested">Check Interested Jobs</a></h4>
	   <h3>List of posted jobs</h3>
		<c:if test="${not empty jobslist}">
			<ul>
			 	<c:forEach var="job" items="${jobslist}">
			 	<div class="thumbnail-container" style="float:left;height:60px;width:60px">
					<img src="${job.company.logo}" width="50" height="50" onerror="this.src='${contextPath}/images/teamwork.png'" />
				</div>
				<div>
					
					<h4 class="text-left"><a href="${contextPath}/applyjob/${job.jobid}"> <b><c:out value="${job.title}" /></b> </a></h4>
					<p style="color:grey"><c:out value="${job.loc}" /></p>
					<c:out value="${job.descrip}" />
				</div>
				<hr>
				</c:forEach>
			</ul>
		</c:if>
		
		
		<div id="pagination">
		
		    <c:url value="/welcome" var="prev">
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
		                <c:url value="/welcome" var="url">
		                    <c:param name="page" value="${i.index}"/>
		                </c:url>
		                <a href='<c:out value="${url}" />'>${i.index}</a>
		            </c:otherwise>
		        </c:choose>                
		    </c:forEach>
		    <c:url value="/welcome" var="next">
		        <c:param name="page" value="${page + 1}"/>
		    </c:url>
		    <c:if test="${page + 1 <= maxPages}">
		        <a href='<c:out value="${next}" />' class="pn next">Next</a>
		    </c:if>
		</div>
	</c:if>
</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
