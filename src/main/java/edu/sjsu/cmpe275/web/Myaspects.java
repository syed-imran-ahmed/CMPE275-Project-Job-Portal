package edu.sjsu.cmpe275.web;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import edu.sjsu.cmpe275.model.User;
import edu.sjsu.cmpe275.service.UserService;

@Component
@Aspect
@ControllerAdvice
public class Myaspects extends HandlerInterceptorAdapter{
    @Autowired
    private UserService userService;
    
	@Pointcut("within(edu.sjsu.cmpe275.web.*)")
	protected void controllers() {
	}
	
	@Pointcut("execution(* *.*(..))")
	protected void allMethods() {
	}


	@Before("controllers() && allMethods()")
	public void validateBefore(JoinPoint joinPoint)  throws Exception {

		String method = joinPoint.getSignature().getName();
		String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
		if(!currentUserName.contains("anonymousUser") ){
	        User user = userService.findByUsername(currentUserName);
	        System.out.println(method);
	        List<String> jobseekerDeniedAccess = Arrays.asList("company","companyProfile","companyJob","companyPostJob","companyShowJob",
	        		"viewApplicantList","viewApplicant","saveDecision","scheduleInterview","saveInterview","welcomecmpny","selection");
	        
	        List<String> companyDeniedAccess = Arrays.asList("applyJob","profileget","profilepost","resumeUpload","resumeget","cancelOrRejectApp",
	        		"updateApp","acceptOffer","listInterestedJobs","filterJob","updateStatus","jobseekerget","jobseeker","imageUpload","uploadStatus");
	        

	        if(user.getUsertype().contains("JobSeeker") && jobseekerDeniedAccess.contains(method)){
	        	System.out.println("Im here");
	        	throw new RuntimeException("welcome");
	        }
	        else if(user.getUsertype().contains("Company") && companyDeniedAccess.contains(joinPoint.getSignature().getName())){
	        	throw new Exception("/");
	        }
	        
		}
		
	}
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception ex) {
		ModelAndView model = new ModelAndView("error");
		model.addObject("link", ex.getMessage());
		return model;
	}

}
