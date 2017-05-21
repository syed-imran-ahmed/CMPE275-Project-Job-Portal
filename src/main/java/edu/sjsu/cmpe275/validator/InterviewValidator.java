package edu.sjsu.cmpe275.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.sjsu.cmpe275.model.Interview;


@Component
public class InterviewValidator implements Validator{
	@Override
	public boolean supports(Class<?> aClass) {
        return Interview.class.equals(aClass);
    }

	@Override
	public void validate(Object o, Errors errors) {
	      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "CandidateName", "NotEmpty");
	      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "InterviewDate", "NotEmpty");
	      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "Place", "NotEmpty");

    }
}

