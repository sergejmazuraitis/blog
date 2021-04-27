package lt.codeacademy.project.blog.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "blogpost")
public class BlogPost {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "VARCHAR(36)", updatable = false)
    @Type(type = "uuid-char")
    private UUID id;

    @NotNull
    @Size(min = 2,
            max = 50,
            message = "{validation.size.name}")
    private String name;

    @NotNull
    @Size(min = 5,
            max = 250,
            message = "{validation.size.name}")
    private String description;

    @NotNull
    @Size(min = 5,
            max = 10000,
            message = "{validation.size.name}")
    @Column(columnDefinition = "VARCHAR(10000)")
    private String content;

    private Date date = new Date();

    @NotNull
    @Size(min = 5,
            max = 50,
            message = "{validation.size.name}")
    private String category;

    @OneToMany(mappedBy = "blogPost", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Comment> comment;
}
