package demo.lab.elasticsearch.document;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Document(indexName = "books")
public class BookDocument {
    @Id
    @Field(type = FieldType.Integer)
    public Integer id;
    @Field(type = FieldType.Text)
    public String title;
    @Field(type = FieldType.Text)
    public String author;
    @Field(type = FieldType.Text)
    public String category;
    @Field(type = FieldType.Date, format = {}, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    public LocalDateTime createdAt;

}
