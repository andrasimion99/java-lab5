package optional;

public class ListCommand implements Command {
    Catalog c = new Catalog();

    public Catalog getC() {
        return c;
    }

    public void setC(Catalog c) {
        this.c = c;
    }

    /**
     * Cu ajutorul comenzii load incarcam in atributul Catalog c un catalog de la path-ul dat ca si parametru
     * Metoda list listeaza(afiseaza) toate informatiile din catalog-ul clasei
     * @param path reprezinta path-ul fisierului din care vom lua informatii si le vom lista
     */
    @Override
    public void command(String path) {
        Command load = new LoadCommand();
        load.command(path);
        this.c = load.getC();
        System.out.println(c);
    }
}
