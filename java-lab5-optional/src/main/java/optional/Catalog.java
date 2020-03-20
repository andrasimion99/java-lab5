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

    /**
     * Functia de adaugare verfica mai intai daca daca id-ul documentului pe care vrem sa il adaugam mai este o data in lista de documente
     * Daca id-ul acestuia se gaseste acolo inseamna ca docuemntul a mai fost o data adaugat deoarece id-urile sunt unice per document
     * iar in acest caz se arunca o exceptie DuplicateIdException spunandu-ti ca documentul este duplicat
     * @param doc reprezinta documentul pe care vrem sa il adaugam in lista de documente a catalogului
     * @throws DuplicateIdException
     */
    public void add(Document doc) throws DuplicateIdException {
        for(Document document: documents){
            if(document.getId().equals(doc.getId())){
                throw new DuplicateIdException(document.getId());
            }
        }
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
        return "optional.Catalog{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", documents=" + documents +
                '}';
    }
}
