package lt.codeacademy.project.blog.model;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "Comment")
public class Comment {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "VARCHAR(36)", updatable = false)
    @Type(type = "uuid-char")
    private UUID id;

    private String name;

    private Date date;

    private String content;

//    private BlogPost post;
}
