package demo.lab.db.entity;

import demo.lab.security.authorization.Role;

import javax.persistence.*;

@Entity(name = "roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public Integer accountId;
    @Enumerated(value = EnumType.STRING)
    public Role role;
}
