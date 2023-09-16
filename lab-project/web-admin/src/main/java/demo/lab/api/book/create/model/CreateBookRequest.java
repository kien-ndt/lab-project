package demo.lab.api.book.create.model;

import javax.validation.constraints.NotNull;

public class CreateBookRequest {

    @NotNull
    public String title;
    @NotNull
    public String author;
    @NotNull
    public String category;

}
