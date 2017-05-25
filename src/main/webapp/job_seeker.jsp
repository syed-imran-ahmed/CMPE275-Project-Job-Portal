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

    <title>View your profile</title>

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
    color:gold;
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

</style>
</head>

<body>
<a href="${contextPath}/welcome">
	<header>
	    <img src="${contextPath}/images/logo.png" alt="hirePeople"/>
	</header>
</a>
<div class="contain">
<div class="form-signin" style= "float:left;width:width/4">
<%
if (session.getAttribute("id") != null && !(session.getAttribute("id")).equals("")) {
%>
<h3 align ="center" class="form-signin-heading">Profile Picture</h3>
<br>

<img class='img-circle' src="https://s3-us-west-1.amazonaws.com/cmpe275/images/${image}.JPG"
 width="250" height="250" onerror="this.src='${contextPath}/images/candidate.jpg'" alt="Upload Image" />
<br>
<%
//session.removeAttribute("pictureuploaded");
}
%>

<h4><a  href="${contextPath}/upload" class="form-group">Upload/Replace profile picture</a><h4>    
</div>
<div style= "float:center">
    <form:form method="POST" action="${contextPath}/job_seeker" modelAttribute="jobseeker" class="form-signin">
        <h2 align ="center" class="form-signin-heading">View/Edit Profile</h2>
         <spring:bind path="Id">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="hidden" path="Id" class="form-control" value = "1010101010101010"
                            autofocus="false"></form:input>
                <form:errors path="Id"></form:errors>
            </div>
        </spring:bind>
        <spring:bind path="Firstname">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="Firstname" class="form-control" placeholder="First Name"
                            autofocus="true"></form:input>
                <form:errors path="Firstname"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="Lastname">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="Lastname" class="form-control" placeholder="Last Name"></form:input>
                <form:errors path="Lastname"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="Introduction">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="Introduction" class="form-control" placeholder="Introduction" ></form:input>
                <form:errors path="Introduction"></form:errors>
            </div>
        </spring:bind>
        
        <spring:bind path="Wrk_exp">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="Wrk_exp" class="form-control" placeholder="Work Experience"></form:input>
                <form:errors path="Wrk_exp"></form:errors>
            </div>
        </spring:bind>
        
        <spring:bind path="Education">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="Education" class="form-control" placeholder="Education"></form:input>
                <form:errors path="Education"></form:errors>
            </div>
        </spring:bind>       
        
         <spring:bind path="Skills">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="Skills" class="form-control" placeholder="Skills"></form:input>
                <form:errors path="Skills"></form:errors>
            </div>
        </spring:bind>    
              	
        <br>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Save changes</button>

      </form:form>

</div>
</div>
   <footer>CMPE275 Project Team-3</footer>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
