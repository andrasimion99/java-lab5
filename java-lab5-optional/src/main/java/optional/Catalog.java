package optional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Catalog implements Serializable {
    private String name;
    private String path;
    private List<Document> documents = new ArrayList<>();

    public Catalog() {
        name = "";
        path = "";
    }

    public Catalog(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public void add(Document doc) throws DuplicateIdException {
        for(Document document: documents){
            if(document.getId().equals(doc.getId())){
                throw new DuplicateIdException(document.getId());
            }
        }
        documents.add(doc);
    }

    public Document findById(String id) {
        return documents.stream().filter(doc -> doc.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return "optional.Catalog{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", documents=" + documents +
                '}';
    }
}
