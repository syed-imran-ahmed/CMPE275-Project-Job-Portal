<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="true"%>
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

    <title>Apply Job profile</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>
<div align ="center">
	<div class="thumbnail-container" style="align-content:center">
					<img src="${companyjobposts.company.logo}" width="100" height="100" onerror="this.src='${contextPath}/images/teamwork.png'" />
	</div>
	<div class="container">
		<h1><a href = "${companyjobposts.company.website}">${companyjobposts.company.name}</a></h1>
	</div>
</div>	
<div class="form-signin">

    <form:form modelAttribute="companyjobposts" class="form-signin">
        <h2 align = "left" class="form-signin-heading">Job Description</h2>
        <spring:bind path="jobid">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="hidden" path="jobid" class="form-control"/>
                <form:errors path="jobid"></form:errors>
            </div>
        </spring:bind>
        
        <br>
        <br>
        <h3  align= "left" class="form-signin-heading">Title</h3>
         <spring:bind path="Title">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:textarea  path="Title" class="form-control" readonly="true" />
                <form:errors path="Title"></form:errors>
            </div>
        </spring:bind>
        <br>
        <h3 align= "left" class="form-signin-heading">Description</h3>
        <spring:bind path="Descrip">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:textarea path="Descrip" class="form-control" readonly="true" />
                <form:errors path="Descrip"></form:errors>
            </div>
        </spring:bind>
        <br>
        <h3 align= "left" class="form-signin-heading">Responsibilities</h3>
        <spring:bind path="Resp">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:textarea path="Resp" class="form-control" readonly="true" />
                <form:errors path="Resp"></form:errors>
            </div>
        </spring:bind>
        <br>
        <h3 align= "left" class="form-signin-heading">Location</h3>
        <spring:bind path="Loc">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:textarea type="text" path="Loc" class="form-control" readonly="true" />
                <form:errors path="Loc"></form:errors>
            </div>
        </spring:bind>
        <br>
        <h3 align= "left" class="form-signin-heading">Salary</h3>
        <spring:bind path="Sal">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:textarea type="text" path="Sal" class="form-control" readonly="true" />
                <form:errors path="Sal"></form:errors>
            </div>
        </spring:bind>

    </form:form>
 </div>
 <div  align = "center">
	<c:choose>
		<c:when test= "${companyjobposts.jobposition eq 'Cancelled'}"> 
			<h2  align= "center">Sorry this Job either got Cancelled!</h2>
		</c:when>	
		<c:when test= "${companyjobposts.jobposition eq 'Filled'}">
			<h2  align= "center">Sorry this Job either got Cancelled!</h2>
		</c:when>
		<c:otherwise>
		<tr>
			<td><a href="${contextPath}/applyprofile" class="form-signin">Apply with Profile</a></td>
			<td><a href="${contextPath}/applyresume" class="form-signin">Apply with Resume</a></td>
		</tr>
		</c:otherwise>	
	</c:choose>
</div>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>