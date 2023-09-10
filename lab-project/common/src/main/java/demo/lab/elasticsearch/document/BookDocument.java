package demo.lab.elasticsearch.document;


import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "books")
public class BookDocument {
    @Id
    @Field(type = FieldType.Integer)
    public Integer id;
    @Field(type = FieldType.Text)
    public String code;
    @Field(type = FieldType.Text)
    public String title;
    @Field(type = FieldType.Text)
    public String author;
    @Field(type = FieldType.Integer)
    public Integer yearOfPublic;
    @Field(type = FieldType.Text)
    public String publisher;
    @Field(type = FieldType.Text)
    public String imgUrl;

}
