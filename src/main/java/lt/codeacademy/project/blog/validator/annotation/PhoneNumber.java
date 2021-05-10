package lt.codeacademy.project.blog.validator.annotation;


import lt.codeacademy.project.blog.validator.PhoneNumberValidator;
import lt.codeacademy.project.blog.validator.data.PhoneNumberType;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
public @interface PhoneNumber {
    String message() default "{javax.validation.constraint.NotNull.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    PhoneNumberType type() default PhoneNumberType.FULL;
}
