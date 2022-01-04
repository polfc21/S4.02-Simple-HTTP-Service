package com.itacademy.validators;

import com.itacademy.model.JobType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class JobTypeValidator implements ConstraintValidator<Job, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return JobType.existsJob(value);
    }
}
