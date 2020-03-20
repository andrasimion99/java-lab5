package optional;

/**
 * interfata ce va fi implementata de comenzile precum list, load, view si html
 */
public interface Command {
    Catalog getC();
    void command(String path);
}
