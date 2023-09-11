package demo.lab.db.entity;

import demo.lab.security.authorization.Role;

import javax.persistence.*;

@Entity(name = "accounts")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public String email;
    public String password;
    @Enumerated(value = EnumType.STRING)
    public Role role;
}
