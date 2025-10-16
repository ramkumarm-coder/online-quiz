package com.rk.online_quiz.entity;

import com.rk.online_quiz.enums.UserRoles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "login_users")
@Check(constraints = "role IN ('ADMIN', 'PARTICIPANT')")
public class LoginUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String username; // email

    @Column(nullable=false)
    private String password;

    @Column
    private String name;

    @Column(nullable=false)
    private boolean active = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private UserRoles role = UserRoles.PARTICIPANT;
}
