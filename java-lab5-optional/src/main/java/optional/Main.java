package optional;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

public class Main {
    /**
     * daca nu primim argumente se vor testa functinalitatile normal din CatalogUtil, adica se vor apela metodele save, load si view
     * daca avem mai mult de 2 argumente sau unul singur nu se va executa nimic deoarece nu e un numar necesar de argumente
     * daca avem fix doua argumente se va executa functionalitate in functie de argumentul dat in felul urmator:
     * primul argument reprezinta comanda pe care vrem sa o dam, iar al doilea reprezinta path-ul cu care vom lucra si pe care il verficam mereu sa fie valid, altfel arunca exceptie.
     * daca avem load + path vom apela metoda load din CatalogUtil si vom apela metoda command() din Clasa LoadCommand ce implemeteaza interfata Command
     * daca avem list + path vom apela metoda list din CatalogUtil si vom apela metoda command() din Clasa ListCommand ce implemeteaza interfata Command
     * daca avem view + path vom apela metoda view din CatalogUtil si vom apela metoda command() din Clasa ViewCommand ce implemeteaza interfata Command
     * daca avem html + path vom apela metoda command() din Clasa HtmlReport ce implemeteaza interfata Command si care va deschide un fisier html in browser
     *
     * @param args reprezinta argumentele primite din terminal
     */
    public static void main(String args[]) {
        Main app = new Main();
        if (args.length == 0) {
            app.testCreateSave();
            app.testLoad();
        } else if (args.length == 2) {
            if (args[0].equals("load")) {
                try {
                    Paths.get(args[1]);
                } catch (InvalidPathException e) {
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
                } catch (InvalidPathException e) {
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
                } catch (InvalidPathException e) {
                    System.out.println("The path is invalid");
                    e.printStackTrace();
                }
                app.testView();

                Command view = new ViewCommand();
                view.command(args[1]);
            }
            if (args[0].equals("html")) {
                Catalog catalog = CatalogUtil.load("../java-lab5-optional/src/main/resources/catalog.json");
                Command html = new HtmlReport(catalog);
                html.command(args[1]);
            }
        } else {
            System.out.println("number of args WRONG");
        }
    }

    /**
     * creem cate un obiect de tip Catalog si doua obiecte de tip Document pe care le introducem in catalog
     * testam functia save pentru CatalogUtil
     * verificam duplicatele de Id-uri a ducometelor cand le introduce in catalog
     * daca introduce un obiect de tip Document in acelasi catalog va arunca o exceptie
     */
    private void testCreateSave() {
        Catalog catalog =
                new Catalog("Java Resources", "../java-lab5-optional/src/main/resources/catalog.json");
        Document doc1 = new Document("java1", "Java Course 1",
                "https://profs.info.uaic.ro/~acf/java/slides/en/intro_slide_en.pdf");
        Document doc2 = new Document("java2", "Java Course 2",
                "../java-lab5-optional/src/main/resources/intro_slide_en.pdf");
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

    /**
     * testam functia load care ne va incarca catalogul de la un anumit path intr-un obiect local, afisandu-l ulterior
     */
    private void testLoad() {
        Catalog catalog1 = CatalogUtil.load("../java-lab5-optional/src/main/resources/catalog.json");
        System.out.println(catalog1);
    }

    /**
     * testam functia view care ne va deschide toate documentele dintr-un catalog pe care l-am incarcat dintr-un anumit path
     */
    private void testView() {
        Catalog catalog = CatalogUtil.load("../java-lab5-optional/src/main/resources/catalog.json");
        for (Document doc : catalog.getDocuments()) {
            CatalogUtil.view(doc);
        }
    }
}
