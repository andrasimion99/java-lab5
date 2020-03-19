package optional;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HtmlReport implements Command {
    Catalog c;

    public HtmlReport(Catalog c) {
        this.c = c;
    }

    @Override
    public Catalog getC() {
        return c;
    }

    public void setC(Catalog c) {
        this.c = c;
    }

    @Override
    public void command(String path) {
        StringBuilder html = new StringBuilder();
        html.append("<!doctype html>\n");
        html.append("<html lang='en'>\n");

        html.append("<head>\n");
        html.append("<meta charset='utf-8'>\n");
        html.append("<title>optional.Catalog</title>\n");
        html.append("</head>\n\n");

        html.append("<body>\n");
        html.append("<h1>optional.Catalog</h1>\n");
        html.append("<p>Nume catalog:" + c.getName() + "</p>\n");
        html.append("<p>Path catalog:" + c.getPath() + "</p>\n");
        html.append("<p>Documents: </p>\n");
        html.append("<ul>\n");
        for (Document document : c.getDocuments()) {
            System.out.println(document);
            html.append("<li>" + document + "</li>\n");
        }
        html.append("</ul>\n");
        html.append("</body>\n\n");

        html.append("</html>");
        try (FileWriter fout = new FileWriter(path)){
            String htmlString = html.toString();
            fout.write(htmlString);
            Desktop desktop = Desktop.getDesktop();
            desktop.open(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
