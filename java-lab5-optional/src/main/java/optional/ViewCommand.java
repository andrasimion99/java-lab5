package optional;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class ViewCommand implements Command {
    Catalog c = new Catalog();

    public Catalog getC() {
        return c;
    }

    public void setC(Catalog c) {
        this.c = c;
    }

    /**
     * Cu ajutorul comenzii load incarcam in atributul Catalog c un catalog de la path-ul dat ca si parametru
     * Dupa parcurgem lista de documente a catalogului si incercam sa le deschidem pe toate in browser astfel:
     * Folosindu-ne de clasa Desktop incercam sa facem un obiect nou de tip URL
     * daca aceasta arunca exceptie inseamna ca nu este valid si incercam sa il deschidem prin metoda open care primeste ca paramteru
     * un obiect de tip File la path-ul dat
     * daca nu a aruncat exceptie inseamna ca este un URL valid si incercam sa il deschidem cu metoda browse care primeste ca parametru
     * un obiect de tip URI a path-ul mentionat
     * Metoda face verficari in caz ca url-ul sau path-ul dat sunt invalide si arunca exceptii
     * @param path path in care ne vom uita pentru documente pentru a le deschide in browser
     */
    @Override
    public void command(String path) {
        Command load = new LoadCommand();
        load.command(path);
        this.c = load.getC();
        for (Document doc : c.getDocuments()) {
            Desktop desktop = Desktop.getDesktop();

            boolean ok = true;
            try {
                new URL(doc.getLocation());
            } catch (MalformedURLException e) {
                ok = false;
                System.out.println(doc.getLocation() + " este un fisier cu path local");
                try {
                    desktop.open(new File(doc.getLocation()));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } finally {
                if (ok) {
                    System.out.println(doc.getLocation() + " este un fisier cu url extern");
                    try {
                        desktop.browse(new URI(doc.getLocation()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
