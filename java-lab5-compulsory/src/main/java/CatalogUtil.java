import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class CatalogUtil {
    /**
     *Cream un nou fisier extern folosind serializarea cu ObjectOutputStream care ia ca si paramteru un alt stream ce ia datele unui path dat
     * Scriem in fisierul respectiv folosing metoda writeObject
     * @param catalog, reprezinta catalogul pe care vrem sa il salvam
     * @throws IOException, aruncam mai departe exceptia pentru I/O stream-uri
     */
    public static void save(Catalog catalog) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(catalog.getPath()))) {
            oos.writeObject(catalog);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * cu FileInputStream obtinem octetii dintr-un fisier extern dat ca parametru
     * cream un ObjectInputStream din care citim datale in format binar si le stocam intr-un obiect local de tip Catalog
     * @param path, path-ul fisierului din care vreau sa iau informatii
     * @return returnam catalogul cu informatiile aferente
     * @throws InvalidCatalogException aruncam mai departe daca apare exceptie
     */
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

    /**
     * Folosindu-ne de clasa Desktop incercam sa facem un obiect nou de tip URL
     * daca aceasta arunca exceptie inseamna ca nu este valid si incercam sa il deschidem prin metoda open care primeste ca paramteru
     * un obiect de tip File la path-ul dat
     * daca nu a aruncat exceptie inseamna ca este un URL valid si incercam sa il deschidem cu metoda browse care primeste ca parametru
     * un obiect de tip URI a path-ul mentionat
     * @param doc obiectul de tip Documet pe care vrem sa il deschidem
     * @throws IOException exceptie pe care o aruncam mai departe
     * @throws URISyntaxException exceptie pe care o aruncam mai departe
     */
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
