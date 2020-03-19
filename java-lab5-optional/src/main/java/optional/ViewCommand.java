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
                System.out.println(doc.getLocation() + " este un fisier local");
                try {
                    desktop.open(new File(doc.getLocation()));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } finally {
                if (ok) {
                    System.out.println(doc.getLocation() + " este un fisier extern");
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
