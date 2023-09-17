package demo.lab.model;

public enum ManageDocumentEnum {
    BOOK("books", 1);

    ManageDocumentEnum(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public String name;
    public Integer id;
}
