package br.edu.ciriguela.config.user;

import br.edu.ciriguela.config.AbstractEntity;
import br.edu.ciriguela.config.role.Role;
import com.fasterxml.jackson.annotation.JsonProperty;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.util.Collection;

@MappedSuperclass
public class User extends AbstractEntity<Long> {

	@Email(message = "Email should be valid")
    @Column(unique = true, nullable = false)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private boolean enabled;

    @ManyToMany
    @JoinTable
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Role> roles;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}
