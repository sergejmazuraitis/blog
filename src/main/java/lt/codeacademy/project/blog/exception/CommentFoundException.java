package lt.codeacademy.project.blog.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
public class CommentFoundException extends RuntimeException{
}
