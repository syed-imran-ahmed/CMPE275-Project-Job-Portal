package edu.sjsu.cmpe275.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.sjsu.cmpe275.model.Company;


@Component
public class CompanyValidator implements Validator {
	
	

    @Override
    public boolean supports(Class<?> aClass) {
        return Company.class.equals(aClass);
    }

    @Override
    public void validate(Object c, Errors errors) {
        Company company = (Company) c;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "Name", "NotEmpty");
       
     
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "Website", "NotEmpty");
        
        if(!urlValidation(company.getWebsite())) {
        	errors.rejectValue("Website", "Validation.company.website");
        }
        
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "Address", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "Description", "NotEmpty");
        
     
    }
    
    public boolean urlValidation(String url){

		String reg = "^http(s{0,1})://[a-zA-Z0-9_/\\-\\.]+\\.([A-Za-z/]{2,5})[a-zA-Z0-9_/\\&\\?\\=\\-\\.\\~\\%]*";
		 
		Pattern pattern = Pattern.compile(reg);
	
		    Matcher matcher = pattern.matcher(url);
		    return matcher.matches();
		
	}	
	

}
