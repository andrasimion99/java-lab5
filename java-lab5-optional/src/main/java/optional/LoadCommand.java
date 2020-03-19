package optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LoadCommand implements Command {
    Catalog c = new Catalog();

    public Catalog getC() {
        return c;
    }

    public void setC(Catalog c) {
        this.c = c;
    }

    @Override
    public void command(String path) {
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
                try {
                    c.add(document);
                } catch (DuplicateIdException e) {
                    e.printStackTrace();
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
