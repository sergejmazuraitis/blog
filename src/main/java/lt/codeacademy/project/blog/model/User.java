package lt.codeacademy.project.blog.model;

import lombok.Getter;
import lombok.Setter;
import lt.codeacademy.project.blog.validator.annotation.Password;
import lt.codeacademy.project.blog.validator.annotation.PhoneNumber;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;

@Getter
@Setter
@Password
@Entity
public class User implements UserDetails {

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
    private String username;

    @PhoneNumber(message = "{validation.phone}")
    @NotNull
    private String phone;

    @NotNull
    @Email(message = "{validation.email}")
    private String email;

    @NotNull
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$", message = "{validation.password}")
    private String password;

    @NotNull
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$", message = "{validation.password}")
    @Transient
    private String repeatPassword;

    @ManyToMany
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Comment> comments;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public String getRole() {
        return roles.iterator().next().getName();
    }
}

