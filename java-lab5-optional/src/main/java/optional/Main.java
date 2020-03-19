package optional;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

public class Main {
    public static void main(String args[]){
        Main app = new Main();
        if (args.length == 0) {
            app.testCreateSave();
            app.testLoad();
        } else if (args.length == 2) {
            if (args[0].equals("load")) {
                try {
                    Paths.get(args[1]);
                } catch (InvalidPathException e){
                    System.out.println("The path is invalid");
                    e.printStackTrace();
                }
                CatalogUtil.load(args[1]);

                Command load = new LoadCommand();
                load.command(args[1]);
            }
            if (args[0].equals("list")) {
                try {
                    Paths.get(args[1]);
                } catch (InvalidPathException e){
                    System.out.println("The path is invalid");
                    e.printStackTrace();
                }
                CatalogUtil.list(args[1]);
                Command list = new ListCommand();
                list.command(args[1]);
            }
            if (args[0].equals("view")) {
                try {
                    Paths.get(args[1]);
                } catch (InvalidPathException e){
                    System.out.println("The path is invalid");
                    e.printStackTrace();
                }
                try {
                    app.testView();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                Command view = new ViewCommand();
                view.command(args[1]);
            }
            if(args[0].equals("html")){
                Catalog catalog = CatalogUtil.load("d:/java/catalog.json");
                Command html = new HtmlReport(catalog);
                html.command(args[1]);
            }
        } else {
            System.out.println("too many args");
        }
    }

    private void testCreateSave() {
        Catalog catalog =
                new Catalog("Java Resources", "d:/java/catalog.json");
        Document doc1 = new Document("java1", "Java Course 1",
                "https://profs.info.uaic.ro/~acf/java/slides/en/intro_slide_en.pdf");
        Document doc2 = new Document("java2", "Java Course 2",
                "E:\\facultate\\Anul II\\Anul II\\Semestrul II\\java\\intro_slide_en.pdf");
        doc1.addTag("type", "Slides");
        try {
            catalog.add(doc1);
        } catch (DuplicateIdException e) {
            e.printStackTrace();
        }
        try {
            catalog.add(doc2);
        } catch (DuplicateIdException e) {
            e.printStackTrace();
        }
        CatalogUtil.save(catalog);
    }

    private void testLoad() {
        Catalog catalog1 = CatalogUtil.load("d:/java/catalog.json");
        System.out.println(catalog1);
    }

    private void testView() throws IOException, URISyntaxException {
        Catalog catalog = CatalogUtil.load("d:/java/catalog.json");
        for(Document doc:catalog.getDocuments()){
            CatalogUtil.view(doc);
        }
    }
}
