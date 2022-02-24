package com.uniovi.sdi.validators;

import com.uniovi.sdi.entities.*;
import com.uniovi.sdi.services.MarksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MarksValidator implements Validator {
    @Autowired
    private MarksService marksService;
    @Override
    public boolean supports(Class<?> aClass) {
        return Mark.class.equals(aClass);
    }
    @Override
    public void validate(Object target, Errors errors) {
        Mark m = (Mark) target;

        if (m.getDescription().length() < 20){
            errors.rejectValue("description", "Error.mark.description.length");
        }
        if (m.getScore() > 10 || m.getScore() < 0){
            errors.rejectValue("score", "Error.mark.score.range");
        }
    }
}
