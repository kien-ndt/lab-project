package demo.lab.api.book.update.model;

import javax.validation.constraints.NotNull;

public class UpdateBookRequest {
    @NotNull
    public Integer id;
    @NotNull
    public String title;
    @NotNull
    public String author;
    @NotNull
    public String category;

}
