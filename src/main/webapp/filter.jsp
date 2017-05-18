<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">

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
<body>
 <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
        <h2>Welcome ${pageContext.request.userPrincipal.name} | <a onclick="document.forms['logoutForm'].submit()">Logout</a></h2>        
    </c:if>
    
<div style="margin-left:25%">
<div class="w3-container">

   
     	<form id="search" action="${contextPath}/search" method="GET" > 
     	<h2 class="form-heading">Search</h2>
	        <div class="form-group ${error != null ? 'has-error' : ''}"> 
	        	<input name="q" class="form-control" tabindex="1" onfocus="if (this.value=='search') this.value = ''" type="text" maxlength="80" size="28" value="search">
	        	<button class="btn btn-lg btn-primary btn-block" type="submit">Search</button> 
	        </div> 
        </form> 
    

   <h3>List of posted jobs [Open]</h3>
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
	<h3>List of posted jobs [Expired]</h3>
	<c:if test="${not empty Filledlist}">
		<ul>
		 	<c:forEach var="filled" items="${Filledlist}">
		 	<div class="thumbnail-container" style="float:left;height:60px;width:60px">
				<img src="${filled.company.logo}" width="50" height="50" onerror="this.src='${contextPath}/images/teamwork.png'" />
			</div>
			<div>
				
				<h4 class="text-left"><a href="${contextPath}/postjob/${filled.jobid}"> <b><c:out value="${filled.title}" /></b> </a></h4>
				<p style="color:grey"><c:out value="${filled.loc}" /></p>
				<c:out value="${filled.descrip}" />
			</div>
			<hr>
			</c:forEach>
		</ul>
	</c:if>
	</div>  
	</div> 
	<div class="left">
		<form action="${contextPath}/filter" method="GET">
		<h3>Locations</h3>
		<ul>
		 	<c:forEach var="facet" items="${filter}">
			<div>
				
				<input type="checkbox" name="checkboxName" value="${facet.value}"><c:out value="${facet.value}" />(<c:out value="${facet.count}" />)
			</div>
			<hr>
			</c:forEach>
		</ul>
		<h3>Title</h3>
		<ul>
		 	<c:forEach var="jobtitle" items="${title}">
			<div>
				
				<input type="checkbox" name="checkboxTitle" value="${jobtitle.value}"><c:out value="${jobtitle.value}" />
			</div>
			<hr>
			</c:forEach>
		</ul>
		<h3>Salary Range</h3>
		<ul>
		 	<c:forEach var="job_sal" items="${salar}">
			<div>
				
				<input type="checkbox" name="checkboxSal" value="${job_sal.value}"><c:out value= "$'${job_sal.value}'" />(<c:out value="${job_sal.count}" />)
			</div>
			<hr>
			</c:forEach>
		</ul>
		<h3>Company</h3>
		<ul>
		 	<c:forEach var="comp" items="${company}">
			<div>
				
				<input type="checkbox" name="checkboxComp" value="${comp.value}"><c:out value="${comp.value}" />
			</div>
			<hr>
			</c:forEach>
		</ul>
		<button class="btn btn-lg btn-primary btn-block" type="submit">Filter</button> 
	</form>
<div id="pagination">

    <c:url value="${contextPath}/welcome" var="prev">
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
                <c:url value="${contextPath}/welcome" var="url">
                    <c:param name="page" value="${i.index}"/>
                </c:url>
                <a href='<c:out value="${url}" />'>${i.index}</a>
            </c:otherwise>
        </c:choose>                
    </c:forEach>
    <c:url value="${contextPath}/welcome" var="next">
        <c:param name="page" value="${page + 1}"/>
    </c:url>
    <c:if test="${page + 1 <= maxPages}">
        <a href='<c:out value="${next}" />' class="pn next">Next</a>
    </c:if>
</div>

</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
