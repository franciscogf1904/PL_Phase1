public class TypeError extends RuntimeException {
    public TypeError(String message) {
        super("Type error: " + message);
    }
}
