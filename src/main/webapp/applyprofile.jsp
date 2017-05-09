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

    <title>Submit Application</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>
    <form:form method="POST" action="/applyprofile" modelAttribute="jobseeker" class="form-signin">
        <h2 class="form-signin-heading">Review Profile</h2>
        <img src="${contextPath}/images/${jobseeker.id}.JPG" width="250" height="250"/>
         <br>
         <br>
         <spring:bind path="Id">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="hidden" path="Id" />
                <form:errors path="Id"></form:errors>
            </div>
        </spring:bind>
        <br>
        <h3 align= "left" class="form-signin-heading">First Name</h3>
        <spring:bind path="Firstname">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:textarea  readonly="true" type="text" path="Firstname" class="form-control" />
                <form:errors path="Firstname"></form:errors>
            </div>
        </spring:bind>
		<br>
        <h3 align= "left" class="form-signin-heading">Last Name</h3>
        <spring:bind path="Lastname">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:textarea  readonly="true" type="text" path="Lastname" class="form-control"/>
                <form:errors path="Lastname"></form:errors>
            </div>
        </spring:bind>
		<br>
        <h3 align= "left" class="form-signin-heading">Introduction</h3>
        <spring:bind path="Introduction">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:textarea  readonly="true" type="text" path="Introduction" class="form-control"/>
                <form:errors path="Introduction"></form:errors>
            </div>
        </spring:bind>
        <br>
        <h3 align= "left" class="form-signin-heading">Work Experience</h3>
        <spring:bind path="Wrk_exp">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:textarea  readonly="true" type="text" path="Wrk_exp" class="form-control"/>
                <form:errors path="Wrk_exp"></form:errors>
            </div>
        </spring:bind>
        <br>
        <h3 align= "left" class="form-signin-heading">Education</h3>
        <spring:bind path="Education">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:textarea  readonly="true" type="text" path="Education" class="form-control" />
                <form:errors path="Education"></form:errors>
            </div>
        </spring:bind>       
        <br>
        <h3 align= "left" class="form-signin-heading">Skills</h3>
         <spring:bind path="Skills">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:textarea  readonly="true" type="text" path="Skills" class="form-control"/>
                <form:errors path="Skills"></form:errors>
            </div>
        </spring:bind> 
        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit Application</button>         
     </form:form>
    
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
