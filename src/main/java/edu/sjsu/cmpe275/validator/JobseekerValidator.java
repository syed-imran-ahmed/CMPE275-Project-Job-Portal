package edu.sjsu.cmpe275.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.sjsu.cmpe275.model.JobSeeker;

@Component
public class JobseekerValidator implements Validator{

	    @Override
	    public boolean supports(Class<?> aClass) {
	        return JobSeeker.class.equals(aClass);
	    }

	    @Override
	    public void validate(Object o, Errors errors) {
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "Firstname", "NotEmpty");
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "Lastname", "NotEmpty");
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "Wrk_exp", "NotEmpty");
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "Education", "NotEmpty");
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "Skills", "NotEmpty");

	    }
}
