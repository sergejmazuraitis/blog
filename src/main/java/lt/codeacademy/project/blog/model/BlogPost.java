package lt.codeacademy.project.blog.model;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "BLOGPOST")
public class BlogPost {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "VARCHAR(36)", updatable = false)
    @Type(type = "uuid-char")
    private UUID id;

    private String name;

    private String description;

    @Column(columnDefinition = "VARCHAR(1000)")
    private String content;

    private Date date;

    private String category;

    @OneToMany(mappedBy = "blogPost", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Comment> comment;
}
