package lt.codeacademy.project.blog.validator;

import lt.codeacademy.project.blog.model.User;
import lt.codeacademy.project.blog.validator.annotation.Password;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, User> {
    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        return user.getPassword() != null && user.getRepeatPassword() != null && user.getPassword().equals(user.getRepeatPassword());
    }
}
