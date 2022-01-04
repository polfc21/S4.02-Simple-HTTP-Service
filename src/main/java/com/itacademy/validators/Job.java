package com.itacademy.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = JobTypeValidator.class)
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Job {
    String message() default "Invalid Job Type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
