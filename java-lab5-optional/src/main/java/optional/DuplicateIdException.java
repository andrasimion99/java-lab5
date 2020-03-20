package optional;

/**
 * clasa ce reprezinta o exceptie care va fi aruncata in cazul in care vor aparea duplicate in id-uri
 */
public class DuplicateIdException extends Exception {
    public DuplicateIdException(String ex) {
        super("There is a doc with this id: " + ex);
    }
}
