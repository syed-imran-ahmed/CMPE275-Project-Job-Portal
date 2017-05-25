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

    <title>Profile picture</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
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
	<h3 align = "left">&#8678;<a href="${contextPath}/job_seeker">Back</a></h3>
		<div class="form-signin">
			<h1 class="form-signin-heading">  Upload a Picture</h1>
			
			<form method="POST" action="${contextPath}/upload" enctype="multipart/form-data" class="form-signin">
			    <input type="file" name="file" /><br/><br/>
			    <input type="submit" value="Submit" />
			    <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
			</form>
		</div>

</div>
<footer>CMPE275 Project Team-3</footer>
</body>
</html>