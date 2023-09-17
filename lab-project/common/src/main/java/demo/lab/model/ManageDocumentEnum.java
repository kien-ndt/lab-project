package demo.lab.model;

public enum ManageDocumentEnum {
    BOOK("books", 1);

    ManageDocumentEnum(String entityName, Integer id) {
        this.entityName = entityName;
        this.id = id;
    }

    public String entityName;
    public Integer id;
}
