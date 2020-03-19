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

public class CatalogUtil {
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

    public static void list(String path) {
        Catalog c = new Catalog();
        c = load(path);
        System.out.println(c);
    }

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
