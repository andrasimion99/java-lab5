package optional;

public class DuplicateIdException extends Exception {
    public DuplicateIdException(String ex) {
        super("There is a doc with this id: " + ex);
    }
}
