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

    <title>View Applicant Profile</title>

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
<a href="${contextPath}/">
<header>
	    <img src="https://s3-us-west-1.amazonaws.com/cmpe275/images/logo.jpeg" alt="hirePeople"/>
</header>
</a>
<br>
<c:choose>
      <c:when test="${profile}">
			<div style="float:left">
			    <img src="${contextPath}/images/${jobseeker.id}.JPG" width="250" height="250" onerror="this.src='${contextPath}/images/candidate.jpg'"/>
			</div>  
			<div style="float:center">  
			    <form:form method="GET" action="${contextPath}/jobseekerOffer" modelAttribute="jobseeker" class="form-signin">
			        <h2 align="center">View Profile</h2>
			         <br>

			         <spring:bind path="Id">
			            <div class="form-group ${status.error ? 'has-error' : ''}">
			                <form:input type="hidden" path="Id" />
			                <form:errors path="Id"></form:errors>
			            </div>
			        </spring:bind>
			        <h3 align= "left" class="form-signin-heading">First Name</h3>
			        <spring:bind path="Firstname">
			            <div class="form-group ${status.error ? 'has-error' : ''}">
			                <form:textarea  readonly="true" type="text" path="Firstname" class="form-control" />
			                <form:errors path="Firstname"></form:errors>
			            </div>
			        </spring:bind>
			        <h3 align= "left" class="form-signin-heading">Last Name</h3>
			        <spring:bind path="Lastname">
			            <div class="form-group ${status.error ? 'has-error' : ''}">
			                <form:textarea  readonly="true" type="text" path="Lastname" class="form-control"/>
			                <form:errors path="Lastname"></form:errors>
			            </div>
			        </spring:bind>
			        <h3 align= "left" class="form-signin-heading">Introduction</h3>
			        <spring:bind path="Introduction">
			            <div class="form-group ${status.error ? 'has-error' : ''}">
			                <form:textarea  readonly="true" type="text" path="Introduction" class="form-control"/>
			                <form:errors path="Introduction"></form:errors>
			            </div>
			        </spring:bind>
			        <h3 align= "left" class="form-signin-heading">Work Experience</h3>
			        <spring:bind path="Wrk_exp">
			            <div class="form-group ${status.error ? 'has-error' : ''}">
			                <form:textarea  readonly="true" type="text" path="Wrk_exp" class="form-control"/>
			                <form:errors path="Wrk_exp"></form:errors>
			            </div>
			        </spring:bind>
			        <h3 align= "left" class="form-signin-heading">Education</h3>
			        <spring:bind path="Education">
			            <div class="form-group ${status.error ? 'has-error' : ''}">
			                <form:textarea  readonly="true" type="text" path="Education" class="form-control" />
			                <form:errors path="Education"></form:errors>
			            </div>
			        </spring:bind>       
			        <h3 align= "left" class="form-signin-heading">Skills</h3>
			         <spring:bind path="Skills">
			            <div class="form-group ${status.error ? 'has-error' : ''}">
			                <form:textarea  readonly="true" type="text" path="Skills" class="form-control"/>
			                <form:errors path="Skills"></form:errors>
			            </div>
			        </spring:bind> 
   
			     </form:form>
			 </div>  
		</c:when>
        <c:otherwise>
        	<div style="float:left">
			    <img src="${contextPath}/images/candidate.jpg"/>
			</div>  
			<div style="float:center">  
			    <form:form  method="GET" action="${contextPath}/jobseekerOffer" modelAttribute="jsApplication" class="form-signin">
			        <h2 class="form-signin-heading">View Profile</h2>
			        <br>
					<div style="float:left">
						 <h3 class="form-signin-heading">Name : ${jsApplication.jobseekerName} </h3>
						 <h3 class="form-signin-heading">Email : ${jsApplication.jobseekerEmail}</h3>
					</div>
					<br>
					<br>
					<h3 align= "left" class="form-signin-heading">Resume</h3>
					<div class="form-group ${status.error ? 'has-error' : ''}">	
					    <iframe src="http://docs.google.com/gview?url=https://s3-us-west-1.amazonaws.com/cmpe275/resumes/${jsApplication.jobseekerResumeLoc}&embedded=true" style="width:718px; height:700px;"></iframe>
					    <a href="https://s3-us-west-1.amazonaws.com/cmpe275/resumes/${jsApplication.jobseekerResumeLoc}">Download file</a>
					</div>
				</form:form>
			 </div>          
        </c:otherwise>
</c:choose>
<div style= "float:center">
    <form:form method="POST" action="${contextPath}/jobseekerOffer" modelAttribute="jsApplication" class="form-signin">
       
        <spring:bind path="Status">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <h3 align= "left" class="form-signin-heading">
        		<c:choose>
  					<c:when test="${jsApplication.status eq 'Pending'}">
		       			<c:choose>
		  					<c:when test="${jsApplication.status eq 'Offered'}">
				       			<form:radiobutton path="Status" value="Offer" checked="checked" />
				       		</c:when>
				       		<c:otherwise>
				       			<form:radiobutton path="Status" value="Offer"/>
				       		</c:otherwise>
		       			</c:choose>
		       			Offered &nbsp;&nbsp;
		       			<c:choose>
		  					<c:when test="${jsApplication.status eq 'Rejected'}">
				       			<form:radiobutton path="Status" value="Reject" checked="checked" />
				       		</c:when>
				       		<c:otherwise>
				       			<form:radiobutton path="Status" value="Reject"/>
				       		</c:otherwise>
				       </c:choose>
				       Rejected
				       <br>
				    <button style="float:center" class="btn btn-lg btn-primary btn-block" type="submit">Save changes</button>
		       		</c:when>
		       		   
		       		<c:otherwise>
		       		<h3>Status</h3>
		       			<form:textarea  readonly="true" type="text" path="Status" class="form-control"/>
			            <form:errors path="Status"></form:errors>
		       		</c:otherwise>
		       </c:choose>
		       <c:choose>
		       		<c:when test="${jsApplication.status eq 'Pending'}">
		       			<h2 align = center><a href="${contextPath}/interviewSchedule/${jsApplication.id}" class="form-signin">Schedule Interview</a></h2>
		       		</c:when>
		       </c:choose>
		     </h3>
	      </div>
		</spring:bind>
		<spring:bind path="Id">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="hidden" path="Id"/>
                <form:errors path="Id"></form:errors>
            </div>
        </spring:bind>
 	</form:form>
</div> 
 
  <footer>CMPE275 Project Team-3</footer>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
