import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class CatalogUtil {
    public static void save(Catalog catalog) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(catalog.getPath()))) {
            oos.writeObject(catalog);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Catalog load(String path) throws InvalidCatalogException {
        Catalog c = new Catalog();
        try {
            FileInputStream file = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(file);
            c = (Catalog) in.readObject();
            in.close();
            file.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            return c;
        }
    }

    public static void view(Document doc) throws IOException, URISyntaxException {
        Desktop desktop = Desktop.getDesktop();
        boolean ok = true;
        try {
            new URL(doc.getLocation());
        } catch (MalformedURLException e) {
            ok = false;
            System.out.println(doc.getLocation() + " este un fisier local");
            desktop.open(new File(doc.getLocation()));
        } finally {
            if (ok) {
                System.out.println(doc.getLocation() + " este un fisier extern");
                desktop.browse(new URI(doc.getLocation()));
            }
        }
    }
}
