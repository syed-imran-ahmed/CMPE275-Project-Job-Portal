<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Profile picture</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">

<body>
	<div class="form-signin">
		<h1 class="form-signin-heading">  Upload a Picture</h1>
		
		<form method="POST" action="${contextPath}/upload" enctype="multipart/form-data" class="form-signin">
		    <input type="file" name="file" /><br/><br/>
		    <input type="submit" value="Submit" />
		    <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
		</form>
	</div>
</body>
</html>