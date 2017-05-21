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

    <title>Schedule Interview</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
     
</head>

<body>

<h2 align = "left"><a href="${contextPath}/">Home Page</a></h2>

<div style= "float:center">
    <form:form method="POST" action="${contextPath}/interviewSchedule" modelAttribute="interview" class="form-signin">
        <h2 align ="center" class="form-signin-heading">View/Edit Profile</h2>
         <spring:bind path="Id">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="hidden" path="Id" class="form-control"/>
                <form:errors path="Id"></form:errors>
            </div>
        </spring:bind>
        <spring:bind path="CandidateName">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="CandidateName" class="form-control" readonly="true"
                            autofocus="true"></form:input>
                <form:errors path="CandidateName"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="InterviewDate">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="datetime-local" name= "InterviewDate" path="InterviewDate" class="form-control" pattern = "yyyy-MM-dd'T'HH:mm"></form:input>
                <form:errors path="InterviewDate"></form:errors>
            </div>
        </spring:bind>
        
        <spring:bind path="Place">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="Place" class="form-control" placeholder="Place of Interview"></form:input>
                <form:errors path="Place"></form:errors>
            </div>
        </spring:bind>
        
 		<spring:bind path="Status">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="hidden" path="Status" class="form-control" />
                <form:errors path="Status"></form:errors>
            </div>
        </spring:bind>
        <c:choose>
	        <c:when test ="${interview.status ne 'scheduled'}">
        		<button class="btn btn-lg btn-primary btn-block" type="submit">Save changes</button>
			</c:when>
		</c:choose>
      </form:form>
</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
