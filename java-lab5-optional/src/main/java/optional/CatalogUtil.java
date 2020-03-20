package optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * clasa util ce va implementa functionalitati pentru programul principal
 */
public class CatalogUtil {
    /**
     * Folosim Clasa FileWriter pentru a crea fisierul la path-ul specificat si pentru scrie caratere in acesta
     * Pentru a scrie fisierul in text plain am ales formatul JSON
     * Pentru a stoca informatiile in format JSON am folosit clasa JSONObject
     * De asemenea, pentru stocarea documentelor am folosit JSONArray in care am plasat mai mult obiecte de tipul JSONObject reprezentand un document cu informatiile aferente.
     * Fiecare obiect poate avea si o list de taguri de tip "cheie": "valoare", asa ca pnetru acest camp am creat din nou un JSONObject in care am plasat informatiile despre taguri.
     * La final obiectul meu ce va fi plasat sub forma: {
     *     catalogName: value,
     *     catalogPath: value,
     *     catalogDocs: [
     *     {
     *         Id:value,
     *         name:value,
     *         location:value,
     *         tags: {
     *             tag1:value,
     *             tag2:value
     *         }
     *     },
     *     {
     *         Id:value,
     *         name:value,
     *         location:value,
     *         tags: {
     *              tag1:value,
     *              tag2:value
     *          }
     *     }
     *     ]
     * }
     *
     * @param catalog reprezinta catalogul pe care vrem sa il salvam in path-ul acestuia(catalog.getPath())
     */
    public static void save(Catalog catalog) {
        try (FileWriter fout = new FileWriter(catalog.getPath())) {
            JSONObject obj = new JSONObject();
            obj.put("catalogName", catalog.getName());
            obj.put("catalogPath", catalog.getPath());

            JSONArray docsArray = new JSONArray();
            for (Document doc : catalog.getDocuments()) {
                JSONObject docObj = new JSONObject();
                JSONObject tagsObj = new JSONObject();
                docObj.put("id", doc.getId());
                docObj.put("name", doc.getName());
                docObj.put("location", doc.getLocation());

                doc.getTags().forEach((key, value) -> {
                    tagsObj.put(key, value);
                });
                docObj.put("tags", tagsObj);
                docsArray.add(docObj);
            }
            obj.put("catalogDocs", docsArray);
            fout.write(obj.toJSONString());
        } catch (IOException e) {
            System.out.println("There was a problem with the file.");
            e.printStackTrace();
        }
    }

    /**
     * Am folosit FileReader pentru a citi date de la path-ul dat ca si parametru
     * Am pus intr-un obiect de tip Object fisierul parsat cu ajutorul metodei parse() din clasa JSONParser pentru a lua continutul
     * Am convertit obiectul meu cu informatii din fisier intr-un obiect de tip JSONObject pentru a putea accesa valorile campurilor create cand am dat save
     * Am creat un obiect local de tip Catalog in care am sa setez toate atributele in functie de ce informatii extrag din fisier.
     * Arunca exceptii daca path-ul nu este valid sau daca exista erori la parsare sau in fisiere.
     * @param path reprezinta path-ul de la care vom extage informatiile si le vom pune in obiectul nostru de tip Catalog
     * @return un obiect de tip catalog pe care l-am construit cu informatiile extrase din path-ul fisierului dat ca si paramteru
     */
    public static Catalog load(String path) {
        Catalog c = new Catalog();
        JSONParser parser = new JSONParser();
        try {
            FileReader file = new FileReader(path);
            Object obj = parser.parse(file);
            JSONObject jsonObject = (JSONObject) obj;
            c.setName((String) jsonObject.get("catalogName"));
            c.setPath((String) jsonObject.get("catalogPath"));
            JSONArray list = (JSONArray) jsonObject.get("catalogDocs");
            for (int i = 0; i < list.size(); i++) {
                JSONObject doc = (JSONObject) list.get(i);
                Document document = new Document((String) doc.get("id"), (String) doc.get("name"), (String) doc.get("location"));

                JSONObject tagsList = (JSONObject) doc.get("tags");
                tagsList.forEach((key, value) -> {
                    document.addTag((String) key, value);
                });
                c.add(document);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return c;
        }
    }

    /**
     * Metoda list listeaza(afiseaza) toate informatiile dintr-un catalog incarcat de la path-ul fisierului dat ca si parametru
     * @param path reprezinta path-ul fisierului din care vom lua informatii si le vom lista
     */
    public static void list(String path) {
        Catalog c = new Catalog();
        c = load(path);
        System.out.println(c);
    }

    /**
     * Folosindu-ne de clasa Desktop incercam sa facem un obiect nou de tip URL
     * daca aceasta arunca exceptie inseamna ca nu este valid si incercam sa il deschidem prin metoda open care primeste ca paramteru
     * un obiect de tip File la path-ul dat
     * daca nu a aruncat exceptie inseamna ca este un URL valid si incercam sa il deschidem cu metoda browse care primeste ca parametru
     * un obiect de tip URI a path-ul mentionat
     * Metoda face verficari in caz ca url-ul sau path-ul dat sunt invalide si arunca exceptii
     * @param doc reprezinta documentul pe care vom vrea sa il deschide in browser
     */
    public static void view(Document doc) {
        Desktop desktop = Desktop.getDesktop();

        boolean ok = true;
        try {
            new URL(doc.getLocation());
        } catch (MalformedURLException e) {
            ok = false;
            System.out.println(doc.getLocation() + " este un fisier local");
            try {
                desktop.open(new File(doc.getLocation()));
            } catch (IOException ex) {
                System.out.println("The path is invalid.");
                ex.printStackTrace();
            }
        } finally {
            if (ok) {
                System.out.println(doc.getLocation() + " este un fisier extern");
                try {
                    desktop.browse(new URI(doc.getLocation()));
                } catch (IOException e) {
                    System.out.println("The url is invalid.");
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    System.out.println("The url is invalid.");
                    e.printStackTrace();
                }
            }
        }
    }
}
