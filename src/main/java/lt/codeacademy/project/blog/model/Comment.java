package lt.codeacademy.project.blog.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

//    @NotNull
//    @Size(min = 3,
//            max = 50,
//            message = "{validation.size.name}")
    private String name;

    private Date date = new Date();

    @NotNull
    @Size(min = 1,
            max = 250,
            message = "{validation.size.name}")
    private String content;

//    @Type(type = "uuid-char")
//    private UUID blogPostId;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "blogPost_id", nullable = false)
    private BlogPost blogPost;
}
