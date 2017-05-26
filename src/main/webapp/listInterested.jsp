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

    <title>Create an account</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>
		div.contain {
		    width: 100%;
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
		
		article {
		    margin-left: 170px;
		    padding: 1em;
		    overflow: hidden;
		    height :100%;
		}
	</style>
</head>
<body>
<a href="${contextPath}/welcome">
	<header>
	    <img src="${contextPath}/images/logo.png" alt="hirePeople"/>
	</header>
</a>
<article>
<div class="contain">
   <h3 align="center">View Interested Job Results</h3>
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
				<form method="POST" action="${contextPath}/interestedremove/${job.jobid}">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<c:set var="jid">${job.jobid}</c:set>
					<c:choose>
						<c:when test = "${interested.get(jid)}">
							<h4 align="left"><button class="btn btn-primary" type="submit">Interested</button></h4>
						</c:when>
						<c:otherwise>
							<h4 align="left"><button class="btn btn-primary" type="submit">Not Interested</button></h4>
						</c:otherwise>
					</c:choose>
				</form>
			</div>
			<hr>
			</c:forEach>
		</ul>
	</c:if>
	</div>  
</article> 
<footer>CMPE275 Project Team-3</footer>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
