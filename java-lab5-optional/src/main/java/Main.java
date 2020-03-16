import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String args[]) throws IOException, URISyntaxException, InvalidCatalogException {
        Main app = new Main();
        app.testCreateSave();
        app.testLoadView();
    }

    private void testCreateSave() throws IOException, URISyntaxException {
        Catalog catalog =
                new Catalog("Java Resources", "d:/java/catalog.ser");
        Document doc1 = new Document("java1", "Java Course 1",
                "https://profs.info.uaic.ro/~acf/java/slides/en/intro_slide_en.pdf");
        Document doc2 = new Document("java2", "Java Course 1",
                "E:\\facultate\\Anul II\\Anul II\\Semestrul II\\java\\intro_slide_en.pdf");
        doc1.addTag("type", "Slides");
        catalog.add(doc1);
        catalog.add(doc2);
        CatalogUtil.save(catalog);
    }

    private void testLoadView() throws IOException, URISyntaxException, InvalidCatalogException {
        Catalog catalog = CatalogUtil.load("d:/java/catalog.ser");
        Document doc1 = catalog.findById("java1");
        Document doc2 = catalog.findById("java2");
        CatalogUtil.view(doc1);
        CatalogUtil.view(doc2);
    }
}
