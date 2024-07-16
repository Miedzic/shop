package com.miedzic.shop.domain.dao;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Audited
public class User extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    @NotAudited
    private String password;
    private boolean premium;
    @ManyToMany
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    // zapobiegnięcie potencjalnemu zapętlaniu toStringa/Equals/Hashcode spowodowany adnotacją @Data
    @JoinTable(name = "user_role", inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;
    private String confirmationToken;
}


