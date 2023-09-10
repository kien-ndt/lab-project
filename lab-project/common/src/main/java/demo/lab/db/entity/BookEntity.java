package demo.lab.db.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "books")
@Table(name = "books")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public String code;
    public String title;
    public String author;
    public Integer yearOfPublic;
    public String publisher;
    public String imgUrl;
    public LocalDateTime updatedAt;
}
