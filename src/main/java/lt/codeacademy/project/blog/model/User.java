package lt.codeacademy.project.blog.model;

import lombok.Getter;
import lombok.Setter;
import lt.codeacademy.project.blog.validator.annotation.Password;
import org.hibernate.annotations.Type;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
@Password
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "VARCHAR(36)", updatable = false)
    @Type(type = "uuid-char")
    private UUID userId;

    @NotNull
    @Size(min = 2, max = 250, message = "{validation.size.name}")
    private String name;

    @NotNull
    @Size(min = 3, max = 250, message = "{validation.size.name}")
    private String surname;

    @NotNull
    @Size(min = 1, max = 250, message = "{validation.size.name}")
    @Column(unique = true)
    private String userName;

    // TODO: 2021-04-23 only digits and +
    @NotNull
    private String phone;

    @NotNull
    @Email(message = "{validation.email}")
    private String email;

    @NotNull
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "{validation.password}")
    private String password;

    @NotNull
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "{password.message}")
    @Transient
    private String repeatPassword;

}
