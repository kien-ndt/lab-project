package demo.lab.db.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "books")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public String title;
    public Integer authorId;
    public Integer categoryId;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
}
