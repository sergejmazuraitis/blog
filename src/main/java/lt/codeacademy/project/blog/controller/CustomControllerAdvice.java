package lt.codeacademy.project.blog.controller;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditor;

@ControllerAdvice
public class CustomControllerAdvice {
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        PropertyEditor stringTrimmer = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmer);
    }

}
