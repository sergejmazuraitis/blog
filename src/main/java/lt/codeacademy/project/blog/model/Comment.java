package lt.codeacademy.project.blog.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "VARCHAR(36)", updatable = false)
    @Type(type = "uuid-char")
    private UUID commentId;

    private String name;

    @CreatedDate
    private Date date = new Date();

    private String content;

    @Type(type = "uuid-char")
    private UUID blogPostId;
}
