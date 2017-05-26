<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

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

    <title>Create Company profile</title>

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
		height:100%;
	}
	</style>
</head>
<body>
<a href="/">
<header>
	    <img src="https://s3-us-west-1.amazonaws.com/cmpe275/images/logo.jpeg" alt="hirePeople"/>
</header>
</a>
<div class="container">

    <form:form method="POST" action="${contextPath}/company" modelAttribute="company" class="form-signin">
        <h2 class="form-signin-heading">Create Company Profile</h2>
         <spring:bind path="Name">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="Name" class="form-control" placeholder = "Company Name"
                            autofocus="true"></form:input>
                <form:errors path="Name"></form:errors>
            </div>
        </spring:bind>
        <spring:bind path="Website">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="Website" class="form-control" placeholder="Website"
                            autofocus="true"></form:input>
                <form:errors path="Website"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="logo">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="url" path="logo" class="form-control" placeholder="Logo image URL"></form:input>
                <form:errors path="logo"></form:errors>
            </div>
        </spring:bind>
        
        <spring:bind path="Address">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:textarea path="Address" class="form-control" placeholder="Address of Headquarters"/>
                <form:errors path="Address"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="Description">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:textarea path="Description" class="form-control" placeholder="Description"/>
                <form:errors path="Description"></form:errors>
            </div>
        </spring:bind>
        
         
        
        <button class="btn btn-lg btn-primary btn-block" type="submit">Save changes</button>
    </form:form>

</div>
<footer>CMPE275 Project Team-3</footer>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>