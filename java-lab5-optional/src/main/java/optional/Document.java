package optional;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Document implements Serializable {
    private String id;
    private String name;
    private String location;
    private Map<String, Object> tags = new HashMap<>();

    public Document() {
        id = "";
        name = "";
        location = "";
    }

    /**
     * Constructorul creeaza un obiect cu parametri respectivi iar pentru location va face mereu verificari daca este un url sau un path legit
     * altfel va arunca o exceptie
     * @param id, reprezinta id-ul documentului creat
     * @param name, reprezinta numele documentului creat
     * @param location, reprezinta locatia documentului creat
     */
    public Document(String id, String name, String location) {
        this.id = id;
        this.name = name;
        try {
            new URL(location).toURI();
        } catch (URISyntaxException e) {
            try {
                Paths.get(location);
            } catch (InvalidPathException ex) {
                System.out.println("The path is invalid");
                ex.printStackTrace();
            }
        } catch (MalformedURLException e) {
            try {
                Paths.get(location);
            } catch (InvalidPathException ex) {
                System.out.println("The path is invalid");
                ex.printStackTrace();
            }
        }
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        try {
            new URL(location).toURI();
        } catch (URISyntaxException e) {
            try {
                Paths.get(location);
            } catch (InvalidPathException ex) {
                System.out.println("The path is invalid");
                ex.printStackTrace();
            }
        } catch (MalformedURLException e) {
            try {
                Paths.get(location);
            } catch (InvalidPathException ex) {
                System.out.println("The path is invalid");
                ex.printStackTrace();
            }
        }
        this.location = location;
    }

    public Map<String, Object> getTags() {
        return tags;
    }

    public void setTags(Map<String, Object> tags) {
        this.tags = tags;
    }

    public void addTag(String key, Object obj) {
        tags.put(key, obj);
    }

    @Override
    public String toString() {
        return "optional.Document{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", tags=" + tags +
                '}';
    }
}
