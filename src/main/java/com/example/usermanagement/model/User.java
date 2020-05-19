package com.example.usermanagement.model;

import lombok.Data;
import org.hibernate.annotations.NaturalId;
import com.example.usermanagement.model.audit.DateAudit;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
public class User extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max=40)
    private String fullName;

    @NotBlank
    @Size(max=40)
    private String fullBusinessTitle;

    @NotBlank
    @Size(max=40)
    private String address;

    @NaturalId
    @NotBlank
    @Size(max=50)
    @Email
    private String email;

    @NotBlank
    @Size(max=100)
    private String password;

    private String phone;

    private String workingAddress;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user",
    cascade = CascadeType.ALL,
    fetch = FetchType.EAGER,
    orphanRemoval = true)
    private List<Basket> baskets = new ArrayList<>();

    public User(){

    }

    public User(String name, String username, String email, String password){
        this.name =  name;
        this.username = username;
        this.password =password;
        this.email= email;
    }
}
