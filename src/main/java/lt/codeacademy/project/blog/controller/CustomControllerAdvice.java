package lt.codeacademy.project.blog.controller;

import lt.codeacademy.project.blog.exception.BlogPostFoundException;
import lt.codeacademy.project.blog.exception.CommentFoundException;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditor;

@ControllerAdvice
public class CustomControllerAdvice {
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        PropertyEditor stringTrimmer = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmer);
    }

    @ExceptionHandler(BlogPostFoundException.class)
    public String productNotFound() {
        return "blogPostNotFound";
    }

    @ExceptionHandler(CommentFoundException.class)
    public String commentNotFound() {
        return "commentNotFound";
    }

}
