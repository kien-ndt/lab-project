package demo.lab.db.entity;

import javax.persistence.*;

@Entity(name = "accounts")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public String email;
    public String password;
}
