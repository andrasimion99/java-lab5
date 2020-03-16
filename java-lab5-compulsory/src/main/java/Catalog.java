import javax.print.Doc;
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

    public void add(Document doc) {
        documents.add(doc);
    }

    /**
     *
     * @param id, reprezinta id-ul documentului pe care il caut in catalog
     * @return un obiect de tipul Document daca il gaseste, respectiv, null daca nu exista
     */
    public Document findById(String id) {
        return documents.stream().filter(doc -> doc.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", documents=" + documents +
                '}';
    }
}
