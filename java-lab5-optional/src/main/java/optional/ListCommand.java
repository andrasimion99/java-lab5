package optional;

public class ListCommand implements Command {
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
        System.out.println(c);
    }
}
